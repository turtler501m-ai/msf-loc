;

var interval ;
var clauseScroll;

var msg = {


		 "BLANK" : "인자값 오류가 발생 하였습니다."
		, "NUSE" : "현재 사용중인 고객이 아닙니다."
		, "OVER" : "사은품 신청 가능 기간을 초과하였습니다."
		, "DOUBL" : "이미 신청하신 이력이 있습니다."
		, "ERROR" : "고객조회에 실패했습니다."
		, "NPROM" : "사은품 선택 프로모션 대상자가 아닙니다."
		, "APPSUCC" : "신청이 완료됬습니다."
		, "APPFAIL" : "신청에 실패했습니다."
		, "CERT" : "휴대폰 인증 정보가 없습니다."
		, "CLICK" : "동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다."
		, "END" : "종료된 프로모션 입니다."
		, "FAIL" : "고객조회에 실패했습니다."
		, "STEP01" : "인증 정보가 일치하지 않습니다. 이 메시지가 계속되면 처음부터 다시 시도해 주세요."
		, "STEP02" : "필수 단계가 누락됐습니다. 처음부터 다시 시도해 주세요."
}


$(document).ready(function(){

	$("#custNm,#phoneNum,#certifySms").val("");

	// 숫자만 입력가능1
	$(".numOnly").keyup(function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
	});

	// 숫자만 입력가능2
	$(".numOnly").blur(function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
	});


	$("#certBtn").click(function(){

		var certifyYn = $("#certify").val();
		if(certifyYn!="Y"){
			MCP.alert("본인인증을 진행해 주세요.",function(){
				$("#custNm").focus();
			});
			return false;
		}

		var clausePriOfferFlag = $("#clausePriOfferFlag").is(":checked");
		if(!clausePriOfferFlag){
			MCP.alert("개인정보의 제공동의에 체크해주세요.",function(){
				$("#clausePriOfferFlag").focus();
			});
			return false;
		}
		var chkA1 = $("#chkA1").is(":checked");
		if(!chkA1){
			MCP.alert("개인정보 수집동의에 체크해주세요.",function(){
				$("#chkA1").focus();
			});
			return false;
		}

		var prmtId = $("#prmtId").val();
		var custNm = ajaxCommon.isNullNvl($("#custNm").val(),"");
		var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
		var certifySms = ajaxCommon.isNullNvl($("#certifySms").val(),"");
		var varData = ajaxCommon.getSerializedData({
			name:$.trim(custNm)
			,phone:$.trim(phoneNum)
			,prmtId : prmtId
	    });

	    ajaxCommon.getItem({
	        id:'userSmsAjax'
	        ,cache:false
	        ,url:'/m/gift/userSmsAjax.do'
	        ,data: varData
	        ,dataType:"json"
	    }
	    ,function(data){
	    	var resCode = ajaxCommon.isNullNvl(data.resCode,"FAIL");
    		if( resCode == "SUCCESS" ){
    			$("#supplyArea").show();
    		} else{
    			MCP.alert(msg[resCode]);
    			return false;
    		}

	    });

	});

	$("#btnApply").click(function(){

		var userName = $("#custNm").val();
		var phoneNum = $("#phoneNum").val();
		var moveMobileFn = $("#moveMobileFn").val();
		var moveMobileMn = ajaxCommon.isNullNvl($("#moveMobileMn").val(),"");
		var moveMobileRn = ajaxCommon.isNullNvl($("#moveMobileRn").val(),"");
		if(moveMobileMn=="" || moveMobileRn==""){
			MCP.alert("사은품 받는분 연락처를 입력해 주세요.",function(){
				$("#moveMobileFn").focus();
			});
			return false;
		}

		var cstmrPost = ajaxCommon.isNullNvl($("#cstmrPost").val(),"");
		var cstmrAddr = ajaxCommon.isNullNvl($("#cstmrAddr").val(),"");
		var cstmrAddrDtl = ajaxCommon.isNullNvl($("#cstmrAddrDtl").val(),"");
		if(cstmrPost=="" || cstmrAddr==""){
			MCP.alert("사은품 받으실 주소를 검색해 주세요.");
			return false;
		}

		if(cstmrAddrDtl==""){
			MCP.alert("사은품 받으실 상세주소를 작성해 주세요.");
			return false;
		}

		var giftObj = $("input:checkbox[name=gift]:checked");
		var prdtIdArry = new Array();
		if(giftObj.length==0){
			MCP.alert("사은품을 선택해 주세요.");
			return false;
		} else {
			giftObj.each(function(){
				var prdtId = $(this).val();
				prdtIdArry.push(prdtId);
			});
		}

		var prmtId = $("#prmtId").val();
		var varData = ajaxCommon.getSerializedData({
			prdtIdArry : prdtIdArry
			, name : $.trim(userName)
			, phone : $.trim(phoneNum)
			, telFn1 : moveMobileFn
			, telMn1 : moveMobileMn
			, telRn1 : moveMobileRn
			, post : cstmrPost
			, addr : cstmrAddr
			, addrDtl : cstmrAddrDtl
			, prmtId : prmtId
	    });

	    ajaxCommon.getItem({
	        id:'giftSaveAjax'
	        ,cache:false
	        ,url:'/m/gift/giftSaveAjax.do'
	        ,data: varData
	        ,dataType:"json"
	    }
	    ,function(data){

	    	var resCode = ajaxCommon.isNullNvl(data.resCode,"APPFAIL");
    		if( resCode =="APPSUCC" ){
    			MCP.alert(msg[resCode],function(){
    				ajaxCommon.createForm({
        			    method:"post"
        			    ,action:"/m/gift/giftPromotionComplete.do"
        			});
        			var prmtId = $("#prmtId").val();
        			ajaxCommon.attachHiddenElement("prmtId",prmtId);
        			ajaxCommon.formSubmit();
    			});

    		} else{
    			MCP.alert(msg[resCode]);
    			return false;
    		}

	    });


	});

	$("#complBtn").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/gift/giftPromotion.do"
		});
		var prmtId = $("#prmtId").val();
		ajaxCommon.attachHiddenElement("prmtId",prmtId);
		ajaxCommon.formSubmit();
	});


	// 사은품 선택
	$("input:checkbox[name=gift]").click(function(){
		var giftObj = $("input:checkbox[name=gift]:checked");
		var limitAmt = Number(ajaxCommon.isNullNvl($("#amountLimit").val(),0));
		var selAmt = 0;
		giftObj.each(function(){
			var price = Number($(this).attr("price"));
			selAmt += price;
		});
		if(limitAmt < selAmt){
			giftObj.each(function(){
				$(this).prop("checked",false);
			});
			alert("신청 가능한 시은품을 다시 확인해주세요.");
			return false;
		}
	});

});

function btnRegDtl(param){
	openPage('termsPop','/termsPop.do',param);
}


/* 주소 setting */
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
    $("#cstmrPost").val(zipNo);
    $("#cstmrAddr").val(roadAddrPart1);
    $("#cstmrAddrDtl").val(roadAddrPart2 + " " +addrDetail);
}
