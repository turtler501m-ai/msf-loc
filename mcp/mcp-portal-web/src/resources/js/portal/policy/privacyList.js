$(document).ready(function() {


});

function goPaging(pageNo){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/policy/privacyList.do"
	});
	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.formSubmit();
}


function searchBtn() {

	goPaging(1);
}

function goDetail(stpltSeq) {

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/policy/privacyView.do"
	});
	var pageNo = $("#pageNo").val();
	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("stpltSeq",stpltSeq);
	ajaxCommon.formSubmit();
};
