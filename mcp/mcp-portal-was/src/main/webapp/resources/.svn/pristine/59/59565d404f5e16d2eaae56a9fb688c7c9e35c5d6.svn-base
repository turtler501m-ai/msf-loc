﻿$(document).ready(function() {

	privacyEtcBoardListAjax($(".midTab").eq(1).attr("dtlCd"),1);
	privacyEtcBoardListAjax($(".midTab").eq(2).attr("dtlCd"),2);

	$("#viewList").click(function(){

		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/cs/privacyBoardList.do"
		});
		var pageNo = $("#pageNo").val();
		var searchNm = $("#searchNm").val().trim();
		var boardSeq = $("#boardSeq").val();
		ajaxCommon.attachHiddenElement("boardSeq",boardSeq);
		ajaxCommon.attachHiddenElement("pageNo",pageNo);
		ajaxCommon.attachHiddenElement("searchNm",searchNm);
		ajaxCommon.formSubmit();

	});

});

function goNextDetail(boardSeq){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/cs/privacyView.do"
	});
	var pageNo = $("#pageNo").val();
	var searchNm = $("#searchNm").val().trim();
	var sbstCtg = $("#sbstCtg").val();

	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.attachHiddenElement("sbstCtg",sbstCtg);
	ajaxCommon.attachHiddenElement("boardSeq",boardSeq);
	ajaxCommon.formSubmit();
}

var privacyEtcBoardListAjax = function(sbstCtg,tab) {

	var boardCtgSeq = $("#boardCtgSeq").val();
	var varData = ajaxCommon.getSerializedData({
		boardCtgSeq : boardCtgSeq,
		sbstCtg : sbstCtg,
		rownum : tab
	}); // 있는 필드 쓰자

	ajaxCommon.getItem({
		id:'privacyEtcListAjax'
		,cache:false
		,url:'/m/cs/privacyEtcHtml.do'
		,data: varData
		,dataType:"html"
		,async:false
	},function(jsonObj){

		if(tab=="1"){
			$("#tabB2-panel").html(jsonObj);
		} else if(tab=="2"){
			$("#tabB3-panel").html(jsonObj);
		}
	});

};