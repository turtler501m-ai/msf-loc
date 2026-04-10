$(document).ready(function() {

	$("#viewList").click(function(){

		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/policy/ktPolicy.do"
		});
		var pageNo = $("#pageNo").val();
		var paramStpltCtgCd = $("#paramStpltCtgCd").val();
		var stpltSeq = $("#stpltSeq").val();
		ajaxCommon.attachHiddenElement("pageNo",pageNo);
		ajaxCommon.attachHiddenElement("paramStpltCtgCd",paramStpltCtgCd);
		ajaxCommon.attachHiddenElement("stpltSeq",stpltSeq);
		ajaxCommon.formSubmit();

	});

	$(document).on("click",".down",function(){

		var boardSeq = $(this).attr("stpltBasSeq");

		var varData = ajaxCommon.getSerializedData({
	        boardSeq : boardSeq,
	        fileDownFlag : "STPLT"
	    });
	    ajaxCommon.getItem({
	        id:'reqFormDownloadCountUpdate'
	        ,cache:false
	        ,url:'/cs/reqFormDownloadCountUpdateAjax.do'
	        ,data: varData
	        ,dataType:"json"
	    },function(){});

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
	    ,action:"/m/policy/mKtPolicyView.do"
	});
	var pageNo = $("#pageNo").val();
	var paramStpltCtgCd = $("#paramStpltCtgCd").val();
	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("paramStpltCtgCd",paramStpltCtgCd);
	ajaxCommon.attachHiddenElement("stpltSeq",stpltSeq);
	ajaxCommon.formSubmit();

}