
$(document).ready(function (){

	$("#errorStartDate").val(new Date().format("yyyy-MM-dd"));
	$("#errorEndDate").val(new Date().format("yyyy-MM-dd"));

	$(".numOnly").keyup(function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
	});

	// 저장버튼
	$("#saveBtn").click(function(){

		var chk = isChk();
		if(!chk){
			return false;
		}

		var ncn = $("#ctn option:selected").val();
		var errorStartDate = $("#errorStartDate").val().replace(/[^0-9]/gi, "");
		var errorEndDate = $("#errorEndDate").val().replace(/[^0-9]/gi, "");
		var errorTypeCd = $("input:radio[name=errorTypeCd]:checked").val();
		var errorVoice = ajaxCommon.isNullNvl($("#errorVoice:checked").val(),"N");
		var errorCall = ajaxCommon.isNullNvl($("#errorCall:checked").val(),"N");
		var errorSms = ajaxCommon.isNullNvl($("#errorSms:checked").val(),"N");
		var errorWifi = ajaxCommon.isNullNvl($("#errorWifi:checked").val(),"N");
		var errorData = ajaxCommon.isNullNvl($("#errorData:checked").val(),"N");
		var errorLocTypeCd = $("input:radio[name=errorLocTypeCd]:checked").val();
		var cstmrPost = ajaxCommon.isNullNvl($("#cstmrPost").val(),"");
		var cstmrAddr = ajaxCommon.isNullNvl($("#cstmrAddr").val(),"");
		var cstmrAddrDtl = ajaxCommon.isNullNvl($("#cstmrAddrDtl").val(),"");
		var regNm = $("#regNm").val();
		var regMobileNo = $("#regMobileNo1").val()+$("#regMobileNo2").val()+$("#regMobileNo3").val();


		var varData = ajaxCommon.getSerializedData({
			ncn : ncn,
			errorStartDate : errorStartDate,
			errorEndDate : errorEndDate,
			errorTypeCd : errorTypeCd,
			errorVoice : errorVoice,
			errorCall : errorCall,
			errorSms : errorSms,
			errorWifi : errorWifi,
			errorData : errorData,
			errorLocTypeCd : errorLocTypeCd,
			cstmrPost : cstmrPost,
			cstmrAddr : cstmrAddr,
			cstmrAddrDtl : cstmrAddrDtl,
			regNm : regNm,
			regMobileNo : regMobileNo

	    });

		ajaxCommon.getItem({
		    id:'callSaveAjax'
		    ,cache:false
		    ,async:false
		    ,url:'/mypage/callSaveAjax.do'
		    ,data: varData
		    ,dataType:"json"
		},function(data){
			var returnCode = data.returnCode;
			var message = data.message;
			if(returnCode!="00"){
				MCP.alert(message);
				return false;
			} else {
				MCP.alert("통화품질 불량접수가 완료되었습니다.<br/>신청하신 내용 확인 후 고객센터에서 익일(영업일 기준)까지 전화드리겠습니다.",function(){
					location.reload();
				});

				return false;
			}

		});
	});


});

function select(){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/mypage/callASView.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}


function isChk(){

	// 발생일시
	var errorStartDate = ajaxCommon.isNullNvl($("#errorStartDate").val(),"");
	if(errorStartDate==""){
		MCP.alert("발생 시작일을 선택해 주세요.");
		return false;
	}
	var errorEndDate = ajaxCommon.isNullNvl($("#errorEndDate").val(),"");
	if(errorEndDate==""){
		MCP.alert("발생 종료일을 선택해 주세요.");
		return false;
	}

	// 날짜 체크
	var secToday = 1000*60*60*24;
	var today = new Date().getTime(); // 25일
	var cpStart = new Date($("#errorStartDate").val()).getTime(); // 26일
	var cpEnd = new Date($("#errorEndDate").val()).getTime();

	var strBefore = (cpStart-today)/secToday;
	var endBefore = (cpEnd-today)/secToday;
	var between = (cpEnd-cpStart)/secToday;
	var day = new Date().format("yyyy-MM-dd");


	if(strBefore > 1){
		MCP.alert("시작일을 확인해 주세요.");
		$("#errorStartDate").val(day);
		return false;
	}
	if(endBefore > 1){
		MCP.alert("종료일을 확인해 주세요.");
		$("#errorEndDate").val(day);
		return false;
	}


	if(between < 0){
		MCP.alert("종료일이 시작일 보다 이전일 수 없습니다. 확인해 주세요.");
		$("#errorStartDate").val(day);
		$("#errorEndDate").val(day);
		return false;
	}


	// 증상
	var boxChk = false;
	$(".errorChk").each(function(){
		var chk = $(this).is(':checked');
		if(chk){
			boxChk = true;
			return;
		}
	});

	if(!boxChk){
		MCP.alert("불량증상을 체크해 주세요.");
		return false;
	}

	// 증상발생지
	var errorLocTypeCd = $("input:radio[name=errorLocTypeCd]:checked").val();
	if(errorLocTypeCd=="02"){
		var cstmrPost = ajaxCommon.isNullNvl($("#cstmrPost").val(),"");
		if(cstmrPost==""){
			MCP.alert("주소를 검색해 주세요.");
			return false;
		}
	}

	// 신청자 정보
	var regNm = ajaxCommon.isNullNvl($("#regNm").val(),"");
	if(regNm ==""){
		MCP.alert("신청자 이름 작성해 주세요",function(){
			$("#regNm").focus();
		});
		return false;
	}
	var regMobileNo = ajaxCommon.isNullNvl($("#regMobileNo1").val(),"")+ajaxCommon.isNullNvl($("#regMobileNo2").val(),"")+ajaxCommon.isNullNvl($("#regMobileNo3").val(),"");
	if(regMobileNo.length !=11){
		MCP.alert("신청자 연락처를 작성해 주세요",function(){
			$("#regMobileNo1").focus();
		});
		return false;
	}

	return true;


}

function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){
	$("#cstmrPost").val(zipNo);//도로명주소
	$("#cstmrAddr").val(roadAddrPart1);//상세주소
	$("#cstmrAddrDtl").val(addrDetail);//우편번호

}
