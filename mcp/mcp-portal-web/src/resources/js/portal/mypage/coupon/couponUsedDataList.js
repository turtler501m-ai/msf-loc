$(document).ready(function(){

	$("#tab1").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/mypage/couponListOut.do"
		});
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	});


});

function select(){
	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/mypage/couponUsedDataList.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}