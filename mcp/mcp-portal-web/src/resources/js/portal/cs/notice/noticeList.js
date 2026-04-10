$(document).ready(function() {


});

function goPaging(pageNo){

	var searchNm = $("#searchNm").val();

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/cs/csNoticeList.do"
	});
	var searchNm = $("#searchNm").val().trim();

	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.formSubmit();

}


function searchBtn() {

	goPaging(1);
}

function goDetail(boardSeq) {

	ajaxCommon.createForm({
	    method:"get"
	    ,action:"/cs/boardView.do"
	});
	var pageNo = $("#pageNo").val();
	var searchNm = $("#searchNm").val().trim();
	$("#boardSeq").val(boardSeq);
	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("boardSeq",boardSeq);
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.formSubmit();
};
