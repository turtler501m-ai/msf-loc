$(document).ready(function() {

	$("#viewList").click(function(){

		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/cs/privacyBoardList.do"
		});
		var pageNo = $("#pageNo").val();
		var searchNm = $("#searchNm").val().trim();

		ajaxCommon.attachHiddenElement("pageNo",pageNo);
		ajaxCommon.attachHiddenElement("searchNm",searchNm);
		ajaxCommon.formSubmit();

	});

});

function goNextDetail(boardSeq){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/cs/privacyView.do"
	});
	var pageNo = $("#pageNo").val();
	var searchNm = $("#searchNm").val().trim();
	var sbstCtg = $("#sbstCtg").val();

	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("boardSeq",boardSeq);
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.attachHiddenElement("sbstCtg",sbstCtg);
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