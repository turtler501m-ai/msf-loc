$(document).ready(function (){

	// 숫자만 입력가능1
	$(".numOnly").keyup(function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
	});


});


//약관동의
function btnRegDtl(param){
	$('#targetTerms').val('Y');
	openPage('termsPop','/termsPop.do',param);
}

function targetTermsAgree() {
	if($('#targetTerms').val() == 'Y'){
		$('#chkAgree').prop('checked', 'checked');
	}

	chkEvent();

 }

//ceheckbox 이벤트
function chkEvent(){
	var chk = $("input[name=chkAgree]:checked").val();

	if(chk){
		$("#btnReg").removeClass("is-disabled");
	} else if(!chk){
		$('#targetTerms').val('');
		$("#btnReg").addClass("is-disabled");

	}

}

//취소
function btn_cancel(){
	ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/content/myShareDataView.do"
	});

	ajaxCommon.attachHiddenElement("ncn",$("#contractNum").val());
	ajaxCommon.formSubmit();
}

//함께쓰기
function btn_reg(){
	let chk = $("input[name=chkAgree]:checked").val();

	if(!chk){
		MCP.alert("함께쓰기 신청을 위한 필수 확인사항 동의 하시기 바랍니다. ");
		return;
	}

	var parameterData = ajaxCommon.getSerializedData({
		 menuType	 : 'shareData'
		,searchCtn 	 :$("#mskReqSvcNo").val()
		,reqSvcNo 	 :$("#reqSvcNo").val()
		,contractNum : $("#contractNum").val()
		,retvGubunCd :$("#retvGubunCd").val()
		,ncn : $("#ncn").val()
	});
	openPage('pullPopupByPost','/m/sms/smsOtpAuthInfoPop.do',parameterData);
	

}