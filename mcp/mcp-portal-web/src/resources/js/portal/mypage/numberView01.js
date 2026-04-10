
$(document).ready(function (){

});

function select(){
	ajaxCommon.createForm({
		method:"post"
		,action:"/mypage/numberView01.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}

function authSms(){
	var chk = $("input[name=chkCertiAgree]:checked").is(":checked");
	if(!chk){
		MCP.alert("휴대폰 번호 변경 본인인증 동의 하시기 바랍니다. ");
		return;
	}

	var parameterData = ajaxCommon.getSerializedData({
		menuType : 'phoneNumChange'
		, phoneNum : $("#ctn option:selected").text()
		, contractNum :$("#ctn option:selected").val()
	});
	openPage('pullPopupByPost','/sms/smsAuthInfoPop.do',parameterData);
}

function btn_reg(){
	var certifyYn = $("#certifyYn").val();
	var ncn = $("#ctn option:selected").val();
	if(certifyYn=="Y"){
		ajaxCommon.createForm({
			method:"post"
			,action:"/mypage/numberView02.do"
		});
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	} else {
		return false;
	}
}

