$(document).ready(function() {

	$("#viewList").click(function(){

		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/policy/ktPolicy.do"
		});
		var pageNo = $("#pageNo").val();
		var stpltSeq = $("#stpltSeq").val();
		var paramStpltCtgCd = $("#paramStpltCtgCd").val();
		ajaxCommon.attachHiddenElement("pageNo",pageNo);
		ajaxCommon.attachHiddenElement("paramStpltCtgCd",paramStpltCtgCd);
		ajaxCommon.formSubmit();
	});

});

var fileDownCallBack = function(stpltBasSeq) {
    var varData = ajaxCommon.getSerializedData({
        boardSeq : stpltBasSeq
        ,fileDownFlag : "STPLT"
    });
    ajaxCommon.getItem({
        id:'reqFormDownloadCountUpdate'
        ,cache:false
        ,url:'/cs/reqFormDownloadCountUpdateAjax.do'
        ,data: varData
        ,dataType:"json"
    },function(){});
};

function goNextDetail(stpltSeq){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/policy/ktPolicyView.do"
	});
	var pageNo = $("#pageNo").val();
	var paramStpltCtgCd = $("#paramStpltCtgCd").val();

	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("stpltSeq",stpltSeq);
	ajaxCommon.attachHiddenElement("paramStpltCtgCd",paramStpltCtgCd);
	ajaxCommon.formSubmit();
}

