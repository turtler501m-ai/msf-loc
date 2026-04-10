$(document).ready(function() {

	viewDesc();

	$("#moreBtn").click(function(){
		var pageNo = Number(ajaxCommon.isNullNvl($("#pageNo").val(),1))+1;
		var totalCount = $("#totalCount").val();
		$("#pageNo").val(pageNo);
		var viewLi = pageNo*10;
		$(".list:lt("+viewLi+")").show();
		var dataTotal = $(".list").length;
		if(totalCount <= viewLi){
			$("#listCount").text(totalCount);
			$("#moreBtn").hide();
		} else {
			$("#listCount").text(viewLi);
			$("#moreBtn").show();
		}

	});

	$("#stpltCtgCd").change(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/policy/ktPolicy.do"
		});
		var pageNo = 1;
		var stpltCtgCd = $("#stpltCtgCd option:selected").val();
		ajaxCommon.attachHiddenElement("pageNo",pageNo);
		ajaxCommon.attachHiddenElement("stpltCtgCd",stpltCtgCd);
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

function viewDesc(){

	var pageNo = Number(ajaxCommon.isNullNvl($("#pageNo").val(),1));
	var totalCount = $("#totalCount").val();
	var viewLi = pageNo*10;
	$(".list:lt("+viewLi+")").show();
	var dataTotal = $(".list").length;
	if(totalCount <= viewLi){
		$("#listCount").text(totalCount);
		$("#moreBtn").hide();
	} else {
		$("#listCount").text(viewLi);
		$("#moreBtn").show();
	}

	var paramStpltSeq = ajaxCommon.isNullNvl($("#paramStpltSeq").val(),"");
	if(paramStpltSeq !=""){
		$(".list").each(function(){
			var stpltSeq = $(this).attr("stpltSeq");
			if(paramStpltSeq==stpltSeq){
				$(this).find("a").focus();
			}
		});
	}
}

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


function goDetail(stpltSeq) {

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/policy/mKtPolicyView.do"
	});
	var pageNo = $("#pageNo").val();
	var stpltCtgCd = $("#stpltCtgCd option:selected").val();

	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("stpltSeq",stpltSeq);
	ajaxCommon.attachHiddenElement("paramStpltCtgCd",stpltCtgCd);
	ajaxCommon.formSubmit();

};

window.onpopstate = function(event) {
	event.preventDefault();
	closeView();
}
