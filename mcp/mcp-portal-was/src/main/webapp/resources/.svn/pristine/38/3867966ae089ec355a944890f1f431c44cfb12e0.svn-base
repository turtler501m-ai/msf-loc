
$(document).ready(function (){


	$("#btnMovePage").click(function(){
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.createForm({
			method:"post"
			,action:"/m/mypage/paywayView.do"
		});

		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();

	});

	$("#modalArs").addClass("payway-info");



});


function select(){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/mypage/chargeView05.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}

var openVirtualAccountListPop = () => {
	var parameterData = ajaxCommon.getSerializedData({
		ncn : $("#ctn option:selected").val()
	});

	openPage('pullPopupByPost', '/m/mypage/virtualAccountListPop.do', parameterData);
}