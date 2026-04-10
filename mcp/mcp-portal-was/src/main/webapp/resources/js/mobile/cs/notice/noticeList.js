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

});

function viewDesc(){

	var pageNo = Number(ajaxCommon.isNullNvl($("#pageNo").val(),1));
	var totalCount = $("#totalCount").val();
	var viewLi = pageNo*10;
	$(".list:lt("+viewLi+")").show();
	var dataTotal = 0;
	try{
		dataTotal = $(".list").length;
	} catch(e){ dataTotal = 0;}

	if(totalCount <= viewLi){
		$("#listCount").text(totalCount);
		$("#moreBtn").hide();
	} else {
		$("#listCount").text(viewLi);
		$("#moreBtn").show();
	}

	var boardSeq = ajaxCommon.isNullNvl($("#boardSeq").val(),"");
	if(boardSeq !="" && boardSeq !="0"){
		$(".list").each(function(){
			var seq = $(this).attr("boardSeq");
			if(seq==boardSeq){
				$(this).find("a").focus();
			}
		});
	}
}

function searchBtn() {

	var searchNm = $("#searchNm").val();
	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/cs/noticeBoardList.do"
	});

	ajaxCommon.attachHiddenElement("pageNo",1);
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.formSubmit();
}

function goDetail(boardSeq) {

	ajaxCommon.createForm({
	    method:"get"
	    ,action:"/m/cs/boardView.do"
	});
	var pageNo = $("#pageNo").val();
	var searchNm = $("#searchNm").val().trim();
	$("#boardSeq").val(boardSeq);
	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("boardSeq",boardSeq);
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.formSubmit();
};

window.onpopstate = function(event) {
	event.preventDefault();
	closeView();
}