$(document).ready(function() {

	$("#viewList").click(function(){

		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/policy/privacyList.do"
		});
		var pageNo = 1;
		ajaxCommon.attachHiddenElement("pageNo",pageNo);
		ajaxCommon.formSubmit();

	});

});

function goNextDetail(stpltSeq){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/policy/privacyView.do"
	});
	var pageNo = $("#pageNo").val();
	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("stpltSeq",stpltSeq);
	ajaxCommon.formSubmit();

}