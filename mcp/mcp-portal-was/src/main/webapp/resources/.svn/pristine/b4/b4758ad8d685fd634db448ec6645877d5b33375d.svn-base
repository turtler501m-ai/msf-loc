$(document).ready(function() {


});

function goPaging(pageNo){

	var stpltCtgCd = $("#stpltCtgCd option:selected").val();

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/policy/ktPolicy.do"
	});
	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("stpltCtgCd",stpltCtgCd);
	ajaxCommon.formSubmit();
}


function searchBtn() {

	goPaging(1);
}

function goDetail(stpltSeq) {

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/policy/ktPolicyView.do"
	});
	var pageNo = $("#pageNo").val();
	var stpltCtgCd = $("#stpltCtgCd option:selected").val();
	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("paramStpltCtgCd",stpltCtgCd);
	ajaxCommon.attachHiddenElement("stpltSeq",stpltSeq);
	ajaxCommon.formSubmit();
};

var fileDownCallBack = function(boardSeq) {
		var varData = ajaxCommon.getSerializedData({
        boardSeq : boardSeq
        ,fileDownFlag:"STPLT"
    });
    ajaxCommon.getItem({
        id:'reqFormDownloadCountUpdate'
        ,cache:false
        ,url:'/cs/reqFormDownloadCountUpdateAjax.do'
        ,data: varData
        ,dataType:"json"
    },function(){});
};
