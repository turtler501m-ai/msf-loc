$(document).ready(function (){

	$("#tab1").click(function(){

		ajaxCommon.createForm({
			method:"post"
				,action:"/mypage/chargeView01.do"
		});
		ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
		ajaxCommon.formSubmit();
	});
});

function select(){

	ajaxCommon.createForm({
		method:"post"
			,action:"/mypage/chargeView03.do"
	});
	ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
	ajaxCommon.formSubmit();
}
