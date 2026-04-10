
//상담예약 결과보기
var initreViewData = function (pageNo) {

	var varData = ajaxCommon.getSerializedData({
		pageNo: pageNo
	});

	ajaxCommon.getItem({
		id: 'bookingDataAjax'
		, cache: false
		, url: '/m/cs/bookingDataAjax.do'
		, data: varData
		, dataType: "json"
		, async: false
	}, function (jsonObj) {

		var resultCd= (!jsonObj || !jsonObj.RESULT_CODE) ? "FAIL" :jsonObj.RESULT_CODE;
		var resultMsg= (!jsonObj || !jsonObj.RESULT_MSG) ? "오류가 발생했습니다." : jsonObj.RESULT_MSG;

		if(resultCd=="0002"){
			MCP.alert(resultMsg,function (){location.href= '/m/loginForm.do';});
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

	var htmlArea = "";

	if (!bookingList || bookingList.length == 0) {
		$("#myBookingHist").hide();
		$("#liDataArea").html("");
		$(".c-nodata").show();
		$("#listCount").text(0); // 현재 노출
		$("#totalCount").text(0);
		$("#addBtnArea").hide();
		$(".masking-badge-wrap").hide();
	} else {

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
					ansClass = "is-registerd";
				} else if (csStatCd == "진행중") {
					ansClass = "is-proceeded";
				} else {
					ansClass = "is-answered";
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
				htmlArea += "		<div class='c-accordion__head " + ansClass + "' data-acc-header>";
				htmlArea += "			<span class='p-prefix'>" + csStatCd + "</span>";
				htmlArea += "			<button class='c-accordion__button' type='button' aria-expanded='false'>";
				htmlArea += "				<div class='c-accordion__title'>";
				htmlArea += "					<div class='c-text-ellipsis'>" + csSubject + "</div>";
				htmlArea += "					<div class='info-category'>" + vocDtl + "</div>";
				htmlArea += "					<div class='info-line'>";
				htmlArea += "						<span class='info-item  u-ml--0'>";
				htmlArea += "							<span>등록일시</span>";
				htmlArea += "							 <span class='with-div'>" + regstDt + "</span></br>";
				htmlArea += "						</span>";
				htmlArea += "						<span class='info-item  u-ml--0'>";
				htmlArea += "							<span>예약일시</span>";
				htmlArea += "							 <span class='with-div'>" + csResDt + "&nbsp;(" + csResTm + ")</span></br>";
				htmlArea += "						</span>";
				htmlArea += "						<span class='info-item  u-ml--0'>";
				htmlArea += "							<span>문의번호</span>";
				htmlArea += "							<span class='with-div'>" + unSvcNo + "</span>";
				htmlArea += "						</span>";
				htmlArea += "					</div>";
				htmlArea += "				</div>";
				htmlArea += "			</button>";
				htmlArea += "		</div>";
				htmlArea += "		<div class='c-accordion__panel c-expand expand'>";
				htmlArea += "			<div class='c-accordion__inside box-question u-pl--38'>";
				htmlArea += "				<div class='box-prefix box-prefix--type1'>Q</div>";
				htmlArea += "				<div class='box-content'>" + csContent + "</div>";
				htmlArea += "			</div>";
				htmlArea += "		</div>";
				htmlArea += "	</li>";
			}
			$("#liDataArea").append(htmlArea);
			$("#myBookingHist").show();
			$(".c-nodata").hide();
			$("#listCount").text(listCount); // 현재 노출
			$("#totalCount").text(totalCount);
			if (listCount < totalCount) {
				$("#addBtnArea").show();
			} else {
				$("#addBtnArea").hide();
			}
			$(".masking-badge-wrap").show();
		}
		var accEl = $("#myBookingHist")[0];
		var accIns = KTM.Accordion.getInstance(accEl).update();
	}
}


$(document).ready(function() {

	pageNo = $("#pageNo").val();

	initreViewData(pageNo);



//예약신청으로 이동
	$("#prvTab").click(function () {
		location.href = "/m/cs/csInquiryBooking.do";
	});



	// 더보기
	$("#moreBtn").click(function(){
		var listCount = Number($("#listCount").text());
		var totalCount = Number($("#totalCount").text());
		if(listCount >= totalCount){
			alert("마지막 페이지 입니다.");
			return false;
		} else {
			var pageNo = Number(ajaxCommon.isNullNvl($("#pageNo").val(),"1"));
			initreViewData(pageNo+1);
			$("#pageNo").val(pageNo+1);
			$(window).scrollTop($("#moreBtn").offset().top-800);
		}
	});



});

