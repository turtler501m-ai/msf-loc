$(document).ready(function() {

	$("#viewList").click(function(){

		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/cs/noticeBoardList.do"
		});
		var pageNo = $("#pageNo").val();
		var searchNm = $("#searchNm").val().trim();
		var boardSeq = $("#boardSeq").val();
		ajaxCommon.attachHiddenElement("pageNo",pageNo);
		ajaxCommon.attachHiddenElement("searchNm",searchNm);
		ajaxCommon.attachHiddenElement("boardSeq",boardSeq);
		ajaxCommon.formSubmit();
	});

});



function goNextDetail(boardSeq){

	ajaxCommon.createForm({
	    method:"get"
	    ,action:"/m/cs/boardView.do"
	});
	var pageNo = $("#pageNo").val();
	var searchNm = $("#searchNm").val().trim();

	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("boardSeq",boardSeq);
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.formSubmit();

}