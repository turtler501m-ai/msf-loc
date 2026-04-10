$(document).ready(function(){

	$("#tab1").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/mypage/couponListOut.do"
		});
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	});
	
	$("#tabM").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/mypage/couponList.do"
		});
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	});

});

function select(){
	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/mypage/couponUsedDataList.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}