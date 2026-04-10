var viewNo = 1;
$(document).ready(function(){

	//상담예약신청 이동
	$("#tab1").click(function(){
		location.href="/cs/csInquiryBooking.do";
	});

	// 페이지 리스트 가져오기
	var pageNo = $("#pageNo").val();
	initreViewData(pageNo);

});

//상담 예약 결과보기
var initreViewData = function (pageNo) {

	if(!pageNo){pageNo = "1";}

	var varData = ajaxCommon.getSerializedData({
		pageNo: pageNo
	});

	ajaxCommon.getItem({
		id: 'bookingDataAjax'
		, cache: false
		, url: '/cs/bookingDataAjax.do'
		, data: varData
		, dataType: "json"
		, async: false
	}, function (jsonObj) {

		var resultCd= (!jsonObj || !jsonObj.RESULT_CODE) ? "FAIL" :jsonObj.RESULT_CODE;
		var resultMsg= (!jsonObj || !jsonObj.RESULT_MSG) ? "오류가 발생했습니다." : jsonObj.RESULT_MSG;

		if(resultCd=="0002"){
			MCP.alert(resultMsg,function (){location.href='/loginForm.do';});
			return false;
		}

		if(resultCd != AJAX_RESULT_CODE.SUCCESS){
			MCP.alert(resultMsg);
			return false;
		}

		var listCount = jsonObj.listCount;
		var totalCount = jsonObj.totalCount;
		var bookingList = jsonObj.bookingList;

		ajaxCommon.setPortalPaging($("#paging"), jsonObj.pageInfoBean);

		viewDesc(totalCount, bookingList, listCount);
	});

};



var viewDesc = function (totalCount, bookingList, listCount) {


	$("#myBookingHist").html('');
	var htmlArea = "";

	if (bookingList != null && bookingList.length > 0) {

		var csStatCd = "";
		var ansClass = "";
		var csSubject = "";
		var csContent = "";
		var vocDtl = "";
		var unSvcNo = "";
		var csResDt = "";
		var csResTm = "";
		var regstDt = "";

		for (var i = 0; i < bookingList.length; i++) {

			csStatCd = bookingList[i].csStatCd;
			if (csStatCd == "미처리") {
				ansClass = "gray-type1";
			} else if (csStatCd == "진행중") {
				ansClass = "mint";
			} else {
				ansClass = "mint-type2";
			}
			csSubject = bookingList[i].csSubject;
			csContent = bookingList[i].csContent;
			vocDtl = bookingList[i].vocDtl;
			csResDt = bookingList[i].csResDt;
			csResTm = bookingList[i].csResTm;
			regstDt = bookingList[i].regstDt;
			csContent = ajaxCommon.isNullNvl(bookingList[i].csContent, "");
			unSvcNo = bookingList[i].unSvcNo;

			htmlArea += "	<li class='c-accordion__item'>";
			htmlArea += "		<div class='c-accordion__head'>";
			htmlArea += "			<span class='c-accordion__text' style='width: 11rem;'>";
			htmlArea += "			 	<span class='c-hidden'>문의유형</span>"+vocDtl;
			htmlArea += "			 </span>";
			htmlArea += "			 <strong class='c-accordion__title' style=\"width: 17rem; !important;\"'>";
			htmlArea += "					<span class='c-hidden'>제목</span>"+csSubject;
			htmlArea += "			 </strong>";
			htmlArea += "			 <span class='c-accordion__num'>";
			htmlArea += "					<span class='c-hidden'>회선번호</span>"+unSvcNo;
			htmlArea += "			 </span>";
			htmlArea += "			 <span class='c-accordion__date'>";
			htmlArea += "					<span class='c-hidden'>접수일</span><span id='regstDt'>"+regstDt+"</span>";
			htmlArea += "			 </span>";
			htmlArea += "			 <span class='c-accordion__date'>";
			htmlArea += "					<span class='c-hidden'>상담 예약일</span><span id='csResDt'>"+csResDt+"<br>";
			htmlArea += "			 ("+csResTm+")</span>";
			htmlArea += "			 </span>";
			htmlArea += "			 <span class='c-accordion__state'>";
			htmlArea += "					<span class='c-hidden'>처리결과</span>";
			htmlArea += "					<span class='c-text-label c-text-label--"+ansClass+"'id='csResDt'>"+csStatCd+"</span>";
			htmlArea += "			 </span>";

			htmlArea += '<button class="acc_headerA'+viewNo+'runtime-data-insert c-accordion__button" type="button"  aria-expanded="false" aria-controls="acc_contentA'+viewNo+'">';
			htmlArea += '<span class="c-hidden">상세열기</span>'
			htmlArea += '</button>';
			htmlArea += '</div>';
			htmlArea += '<div class="c-accordion__panel expand" id="acc_contentA'+viewNo+'" aria-labelledby="acc_headerA'+viewNo+'">';
			htmlArea += '	<div class="c-accordion__inside question" style="padding-left: 11rem!important;">';
			htmlArea += "			<span class='box-prefix'>Q</span>";
			htmlArea += '			<div class="box-content">'+csContent+'</div>';
			htmlArea += '	</div>';
			htmlArea += '</div>';
			htmlArea += '</li>';

			viewNo ++;
		}

		window.scrollTo(0,0);

		$("#myBookingHist").append(htmlArea);
		$(".c-nodata").hide();

		if(totalCount > 10){
			$("#paging").show();
		}else{
			$("#paging").hide();
		}

		$(".masking-badge-wrap").show();

		window.KTM.initialize();
	} else {
		htmlArea += "<div class='c-nodata' id='nodata'>";
		htmlArea +=		"<i class='c-icon c-icon--nodata' aria-hidden='true'></i>";
		htmlArea +=		"<p class='c-nodata__text'>조회 결과가 없습니다.</p>";
		htmlArea +=	"</div>";
		$("#myBookingHist").append(htmlArea);
		$("#paging").hide();
		$(".masking-badge-wrap").hide();
	}

}

//페이징 이동
$(document).on("click", ".c-paging a", function(){

	if($(this).attr("pageNo")){
		initreViewData($(this).attr("pageNo"));

	}
});


