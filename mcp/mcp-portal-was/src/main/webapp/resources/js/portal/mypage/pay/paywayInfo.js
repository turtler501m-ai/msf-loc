
$(document).ready(function (){

	$("#certBtn").click(function(){

		var chkAgree = $("input:checkbox[name='chkAgree']").is(":checked");
		if(chkAgree){
			var parameterData = ajaxCommon.getSerializedData({
				menuType : 'charge'
				, phoneNum 	 : $("#ctn option:selected").text()
				, contractNum :$("#ctn option:selected").val()
			});
			openPage('pullPopupByPost','/sms/smsAuthInfoPop.do',parameterData);
		} else {
			MCP.alert("본인인증 절차에 동의해 주세요.");
			return false;
		}
	});

	$("#modalArs").addClass("payway-info");

});

function btn_reg(){
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.createForm({
		method:"post"
		,action:"/mypage/paywayView.do"
	});

	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}

function select(){
	ajaxCommon.createForm({
		method:"post"
		,action:"/mypage/chargeView05.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}

var openVirtualAccountListPop = () => {
	var parameterData = ajaxCommon.getSerializedData({
		ncn : $("#ctn option:selected").val()
	});

	openPage('pullPopupByPost', '/mypage/virtualAccountListPop.do', parameterData);
}