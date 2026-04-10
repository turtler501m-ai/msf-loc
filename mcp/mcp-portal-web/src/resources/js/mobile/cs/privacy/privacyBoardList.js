$(document).ready(function(){

//	initView();
//	privacyBoardListAjax(1);
	viewDesc();
	privacyEtcBoardListAjax($(".midTab").eq(1).attr("dtlCd"),1);
	privacyEtcBoardListAjax($(".midTab").eq(2).attr("dtlCd"),2);
	privacyEtcBoardListAjax($(".midTab").eq(3).attr("dtlCd"),3);

	var tabIndex = $("#tabIndex").val();

	if(tabIndex != "") {
		setTimeout(function(){
			$("#tabE"+ tabIndex).trigger("click");
		},300);
	}


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


	$("#tab2").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/cs/privacyGuideList.do"
		});
		ajaxCommon.formSubmit();
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

	var boardSeq = ajaxCommon.isNullNvl($("#boardSeq").val(),"");
	if(boardSeq !=""){
		$(".list").each(function(){
			var seq = $(this).attr("boardSeq");
			if(seq==boardSeq){
				$(this).find("a").focus();
			}
		});
	}
}

//var privacyBoardListAjax = function(pageNo) {
//
//	var searchNm = $('#searchNm').val();
//	var pageNo = pageNo;
//	var sbstCtg = $("#sbstCtg").val();
//
//	var varData = ajaxCommon.getSerializedData({
//		pageNo : pageNo,
//		searchNm : searchNm,
//		sbstCtg : sbstCtg
//	});
//
//	ajaxCommon.getItem({
//		id:'privacyListAjax'
//		,cache:false
//		,url:'/m/cs/privacyListAjax.do'
//		,data: varData
//		,dataType:"json"
//		,async:false
//	},function(jsonObj){
//
//		var totalCount = jsonObj.totalCount; // 총갯수
//		var privacyBoardList = jsonObj.privacyBoardList; // 조회데이터
//		var listCount = jsonObj.listCount; // 페이징포함 곱한 갯수
//		var pageNo = jsonObj.pageNo;
//		$("#pageNo").val(pageNo);
//		viewDesc(totalCount,privacyBoardList,listCount);
//	});
//
//};

//var viewDesc = function(totalCount,privacyBoardList,listCount){
//
//	var htmlArea = "";
//	if(privacyBoardList !=null && privacyBoardList.length>0){
//
//		var boardSeq =  "";
//		var boardSubject =  "";
//		var boardWriteDt =  "";
//		var notiYn =  "";
//		var charDt = "";
//
//		for( var i=0; i<privacyBoardList.length; i++ ){
//
//			boardSeq = privacyBoardList[i].boardSeq;
//			boardSubject = privacyBoardList[i].boardSubject;
//			cretDt = privacyBoardList[i].cretDt;
//			notiYn = privacyBoardList[i].notiYn;
//			charDt = privacyBoardList[i].charDt;
//
//			htmlArea +="<li class='c-list__item'>";
//			htmlArea +="	<a class='c-list__anchor' href='javascript:goDetail("+boardSeq+")'>";
//			htmlArea +="		<strong class='c-list__title c-text-ellipsis'>"
//			if(notiYn=="Y"){
//				htmlArea +="			<span class='u-co-red'>[공지]</span>";
//			}
//			htmlArea +=boardSubject;
//			htmlArea +="		</strong>";
//			htmlArea +="		<span class='c-list__sub'>"+charDt+"</span>";
//			htmlArea +="	</a>";
//			htmlArea +="</li>";
//		}
//
//		$("#dataArea").append(htmlArea);
//		$("#listCount").text(listCount);
//		$("#totalCount").text(totalCount);
//		$("#dataArea").show();
//		$(".c-nodata").hide();
//		if(listCount < totalCount){
//			$("#addBtnArea").show();
//		} else {
//			$("#addBtnArea").hide();
//		}
//	} else {
//		initView();
//	}
//}

//function initView(){
//	var sbstCtg = $(".midTab").eq(0).attr("dtlCd");
//	$("#sbstCtg").val(sbstCtg);
//
//	$("#dataArea").html("");
//	$("#listCount").text("0"); // 현재 노출
//	$("#totalCount").text("0");
//	$("#dataArea,#addBtnArea").hide();
//	$(".c-nodata").hide();
//}

function goDetail(boardSeq) {

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/cs/privacyView.do"
	});
	var pageNo = $("#pageNo").val();
	var searchNm = $("#searchNm").val().trim();
	var sbstCtg = $("#sbstCtg").val();

	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.attachHiddenElement("boardSeq",boardSeq);
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.attachHiddenElement("sbstCtg",sbstCtg);
	ajaxCommon.formSubmit();
};

function searchBtn() {

	var searchNm = $("#searchNm").val();
	var sbstCtg = $("#sbstCtg").val();
	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/m/cs/privacyBoardList.do"
	});

	ajaxCommon.attachHiddenElement("pageNo",1);
	ajaxCommon.attachHiddenElement("searchNm",searchNm);
	ajaxCommon.attachHiddenElement("sbstCtg",sbstCtg);
	ajaxCommon.formSubmit();
}

var privacyEtcBoardListAjax = function(sbstCtg,tab) {

	var varData = ajaxCommon.getSerializedData({
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
			// 피해사례 스와이퍼
		    var swipeGuideWrap = new Swiper('#swipeGuideWrap', {
		      speed: 300,
		      initialSlide: 0,
		      observer: true,
		     observeParents: true,
		      loop: true,
		      effect: 'slide',
		      spaceBetween: 24,
		      slidesPerView: 'auto',
		      centeredSlides: true,
		    });
		} else if(tab=="3"){
			$("#tabB4-panel").html(jsonObj);
		}
	});

};
