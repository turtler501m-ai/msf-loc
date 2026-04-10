$(document).ready(function() {

	$("#viewList").click(function(){

		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/cs/csNoticeList.do"
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
	    method:"get"
	    ,action:"/cs/boardView.do"
	});
	var pageNo = $("#pageNo").val();
	var searchNm = $("#searchNm").val().trim();

	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("boardSeq",boardSeq);
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.formSubmit();
}