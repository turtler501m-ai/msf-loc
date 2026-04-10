

$(document).ready(function (){

	$("#lostCnlBtn").click(function(){

		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/mypage/lostCnlView.do"
		});
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	});

	$("#loseType").change(function(){
		var val = $(this).val();
		$("#guideYn option").eq(0).prop("selected",true);
		if(val=="H"){
			$("#guideYn option").eq(2).prop("disabled",false);
		} else {
			$("#guideYn option").eq(2).prop("disabled",true);
		}
	});

	// 문의 내용 글자막기
	$("#loseCont").keyup(function(){
		var content = $(this).val();
		if(content.length > 40) {
			MCP.alert("글자수는 40자로 이내로 제한됩니다.",function(){
				$(this).val($(this).val().substring(0, 40));
			});

		}
        $("#textAreaSbstSize").html($(this).val().length + '/40자');
	});

	$("#lostBtn").click(function(){

		if(!validatorCheck()){
			return false;
		}

		if(!confirm("분실신고를 하시겠습니까?")){
    		return false;
		}

		var ncn = $("#ctn option:selected").val();
		var varData = ajaxCommon.getSerializedData({
			ncn : ncn
	    });

		ajaxCommon.getItem({
	        id:'pcsLostInfoAjax'
	        ,cache:false
	        ,url:'/mypage/pcsLostInfoAjax.do'
	        ,data: varData
	        ,dataType:"json"
	        ,async:false
	    }
	    ,function(jsonObj){

	    	if(jsonObj.returnCode == "00") {
	    		var custNm = $("#lostName").val();
	    		var cntcTlphNo = $("#cntcTlphNo").val();
	    		var guideYnTxt = $("#guideYn option:selected").text();
	    		var conTect = "아래사항을 확인 후  접수 버튼을 선택하시면 분실신고가 완료됩니다." +
	    							"\n고객명 :"+custNm+"" +
	    							"\n연락 가능한 연락처 :"+cntcTlphNo+"" +
	    							"\n고객요청 사항:"+guideYnTxt;

	    		if(confirm(conTect)){
	    			apply();
	    		}

           	} else {
           		MCP.alert(jsonObj.message);
           		return false;
           	}

	    });

	});

	/////////////////////////////버튼 활성화////////////////////////////////////
	$("#cntcTlphNo,#strPwdInsert").keyup(function(){
		var isChk = false;
		var cntcTlphNo = $("#cntcTlphNo").val();
		var strReg = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
		if(strReg.test(cntcTlphNo)){
			isChk = true;
		}
		var strPwdInsert = ajaxCommon.isNullNvl($("#strPwdInsert").val(),"");
		if(cntcTlphNo !="" && strPwdInsert !="" && strPwdInsert.length > 3 && isChk==true){
			$("#lostBtn").prop("disabled",false);
		} else {
			$("#lostBtn").prop("disabled",true);
		}
	});


});

function apply(){


	var ncn = $("#ctn option:selected").val();
	var loseType = $("#loseType option:selected").val();
	var guideYn = $("#guideYn option:selected").val();
	var cntcTlphNo = $("#cntcTlphNo").val();
	var loseCont = $("#loseCont").val();
	var loseLocation = $("#loseLocation option:selected").val();
	var strPwdInsert = $("#strPwdInsert").val();

	var varData = ajaxCommon.getSerializedData({
		ncn : ncn,
		loseType : loseType,
		guideYn : guideYn,
		cntcTlphNo : cntcTlphNo,
		loseCont : loseCont,
		loseLocation : loseLocation,
		strPwdInsert : strPwdInsert
    });

	ajaxCommon.getItem({
        id:'pcsLostChgAjax'
        ,cache:false
        ,url:'/mypage/pcsLostChgAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    }
    ,function(jsonObj){

    	if(jsonObj.returnCode == "00") {
       		MCP.alert("분실신고 신청이 정상적으로 접수 되었습니다.",function(){
       			KTM.LoadingSpinner.show();
       			setTimeout(function(){
           			location.reload();
                }, 500);
       		});
       	} else {
       		MCP.alert(jsonObj.message);

       	}

    });
}



function validatorCheck(){

	var cntcTlphNo = $("#cntcTlphNo").val();
	var strReg = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
	if(!strReg.test(cntcTlphNo)){
		MCP.alert("핸드폰번호 입력해 주세요.",function(){
			$("#cntcTlphNo").focus();
		});
		return false;
	}

	var strPwdInsert = ajaxCommon.isNullNvl($("#strPwdInsert").val(),"");
//	var strPwdInsertCert = ajaxCommon.isNullNvl($("#strPwdInsertCert").val(),"");
	if(strPwdInsert==""){
		MCP.alert("분실신고 해제 비밀번호를 입력해 주세요.",function(){
			$("#strPwdInsert").focus();
		});
		return false;
	} else {
		if(strPwdInsert.length < 4){
			MCP.alert("비밀번호는 4~8자리 숫자입니다.",function(){
				$("#strPwdInsert").focus();
			});
			return false;
		}
	}

	return true;
}


function select(){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/mypage/lostView.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}

