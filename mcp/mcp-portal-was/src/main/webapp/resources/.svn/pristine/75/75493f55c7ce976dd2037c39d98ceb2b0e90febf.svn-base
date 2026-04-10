$(document).ready(function (){

	$("#ncn").on("change",function(){
		callViewList('ctn');
	});

	$("#datalist").on("change",function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/mypage/callView01.do"
		});

		ajaxCommon.attachHiddenElement("useMonth", $("#datalist option:selected").val());
		ajaxCommon.attachHiddenElement("ncn", $("#ncn option:selected").val());
		ajaxCommon.formSubmit();
	});

	$(".link").click(function(){
		KTM.LoadingSpinner.show();
		var url = $(this).attr("url");
		ajaxCommon.createForm({
		    method : "post"
		    ,action : url
		});
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	});
});

function select(){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/mypage/callView01.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}