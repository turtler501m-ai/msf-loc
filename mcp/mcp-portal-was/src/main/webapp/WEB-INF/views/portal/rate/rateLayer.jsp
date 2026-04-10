<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<div class="c-modal__content c-modal--xlg">
    <div class="c-modal__header">
        <h2 class="c-modal__title" id="modalProductTitle">${gdncBasVo.rateAdsvcNm}</h2>
        <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
        </button>
    </div>
    <div class="c-modal__body">

        <!-- 요금제 설명 -->
        <div class="product-detail">
            <div class="product-detail__content">
                <ul class="product-summary">

                    <%-- 데이터 --%>
                    <c:if test="${!empty rateAdsvcVo.bnfitDataItemImgNm}">
                        <li class="product-summary__item">
                            <div class="product-summary__image">
                                <img src="/resources/images/portal/common/ico_payment_data_gray.svg" alt="" aria-hidden="true">
                            </div>
                            <span class="product-summary__text">
                                <c:choose>
                                    <c:when test="${!empty rateAdsvcVo.bnfitData}">
                                         <span class="max-data-label">
                                             <c:if test="${!empty rateAdsvcVo.maxDataDelivery}"><em>${rateAdsvcVo.maxDataDelivery}</em></c:if>
                                        </span>
                                                ${rateAdsvcVo.bnfitData}
                                        <c:if test="${!empty rateAdsvcVo.promotionBnfitData}"><span class="product-summary__sub">${rateAdsvcVo.promotionBnfitData}</span></c:if>
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </span>
                        </li>
                    </c:if>

                    <%-- 전화 --%>
                    <c:if test="${!empty rateAdsvcVo.bnfitVoiceItemImgNm}">
                        <li class="product-summary__item">
                            <div class="product-summary__image">
                                <img src="/resources/images/portal/common/ico_payment_call_gray.svg" alt="" aria-hidden="true">
                            </div>
                            <span class="product-summary__text">
                                <c:choose>
                                    <c:when test="${!empty rateAdsvcVo.bnfitVoice}">
                                        ${rateAdsvcVo.bnfitVoice}
                                        <c:if test="${!empty rateAdsvcVo.promotionBnfitVoice}"><span class="product-summary__sub">${rateAdsvcVo.promotionBnfitVoice}</span></c:if>
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </span>
                        </li>
                    </c:if>
                    <%-- 문자 --%>
                    <c:if test="${!empty rateAdsvcVo.bnfitSmsItemImgNm}">
                        <li class="product-summary__item">
                            <div class="product-summary__image">
                                <img src="/resources/images/portal/common/ico_payment_sms_gray.svg" alt="" aria-hidden="true">
                            </div>
                            <span class="product-summary__text">
                            <c:choose>
                                <c:when test="${!empty rateAdsvcVo.bnfitSms}">
                                    ${rateAdsvcVo.bnfitSms}
                                    <c:if test="${!empty rateAdsvcVo.promotionBnfitSms}"><span class="product-summary__sub">${rateAdsvcVo.promotionBnfitSms}</span></c:if>
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </span>
                        </li>
                    </c:if>

                    <%-- WIFI --%>
                    <c:if test="${!empty rateAdsvcVo.bnfitWifiItemImgNm}">
                        <li class="product-summary__item">
                            <div class="product-summary__image">
                                <img src="/resources/images/portal/common/ico_payment_wifi_gray.svg" alt="" aria-hidden="true">
                            </div>
                            <span class="product-summary__text">
                            <c:choose>
                                <c:when test="${!empty rateAdsvcVo.bnfitWifi}">${rateAdsvcVo.bnfitWifi}</c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </span>
                        </li>
                    </c:if>

                    <%-- 제휴혜택 노출문구 (RATEBE11) --%>
                    <c:if test="${!empty rateAdsvcVo.bnfitList}">
                        <c:forEach var="bnfit" items="${rateAdsvcVo.bnfitList}">
                            <li class="product-summary__item">
                                <div class="product-summary__image">
                                    <img src="/resources/images/portal/common/ico_payment_use_gray.svg" alt="" aria-hidden="true">
                                </div>
                                <span class="product-summary__text">${bnfit.rateAdsvcItemDesc}
                                    <c:if test="${!empty bnfit.rateAdsvcItemApdDesc}"><span class="product-summary__sub">${bnfit.rateAdsvcItemApdDesc}</span></c:if>
                              </span>
                            </li>
                        </c:forEach>
                    </c:if>
                </ul>
                <div class="product-detail__bottom">
                    <div class="product-detail__price-wrap">
                        <div class="product-detail__price">
                            <span class="product-detail__price__title">월 기본료(VAT 포함)</span>
                            <c:choose>
                                <c:when test="${empty rateAdsvcGdncBasDTO.rateDiscntAmtVatDesc and empty rateAdsvcGdncBasDTO.seniorDiscntAmtVatDesc and empty rateAdsvcGdncBasDTO.seniorDiscntAmtVatDesc and empty gdncBasVo.promotionAmtVatDesc}">
                                    <span class="u-td">${gdncBasVo.mmBasAmtVatDesc} 원</span>
                                </c:when>
                                <c:otherwise><span><em class="u-td-line-through">${gdncBasVo.mmBasAmtVatDesc}</em> <em>원</em></span></c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="product-detail__price-wrap">
                        <c:if test="${!empty rateAdsvcGdncBasDTO.contractAmtVatDesc}">
                            <div class="product-detail__price">
                                <span class="product-detail__price__title u-co-black">약정시 기본료(VAT 포함)</span>
                                <span class="u-co-red"><b>${rateAdsvcGdncBasDTO.contractAmtVatDesc}</b><em>원</em></span>
                            </div>
                        </c:if>
                        <c:if test="${!empty rateAdsvcGdncBasDTO.rateDiscntAmtVatDesc}">
                            <div class="product-detail__price">
                                <span class="product-detail__price__title u-co-black">요금할인 시 기본료(VAT 포함)</span>
                                <span class="u-co-red"><b>${rateAdsvcGdncBasDTO.rateDiscntAmtVatDesc}</b><em>원</em></span>
                            </div>
                        </c:if>
                        <c:if test="${!empty rateAdsvcGdncBasDTO.seniorDiscntAmtVatDesc}">
                            <div class="product-detail__price">
                                <span class="product-detail__price__title u-co-black">시니어 할인 시 기본료(VAT 포함)</span>
                                <span class="u-co-red"><b>${rateAdsvcGdncBasDTO.seniorDiscntAmtVatDesc}</b><em>원</em></span>
                            </div>
                        </c:if>
                        <c:if test="${!empty gdncBasVo.promotionAmtVatDesc}">
                            <div class="product-detail__price">
                                <span class="product-detail__price__title u-co-black">프로모션 요금 (VAT 포함)</span>
                                <span class="u-co-red"><b>${gdncBasVo.promotionAmtVatDesc}</b><em>원</em></span>
                            </div>
                        </c:if>
                    </div>

                </div>
            </div>
        </div>




        <%-- 요금제부가서비스안내링크상세 --%>
        <c:if test="${'N' ne rateAdsvcCtgBasDTO.btnDisplayYn}">
            <c:if test="${!empty rateAdsvcVo.gdncLinkDtlList}">
                <div class="c-button-wrap">
                    <c:forEach var="gdncLinkDtl" items="${rateAdsvcVo.gdncLinkDtlList}" varStatus="status">
                        <a href="javascript:void(0);" onclick="location.href=getReplaceUrl('${gdncLinkDtl.linkUrl}');" class="c-button c-button-round--md c-button--mint u-width--300 u-fs-18">${gdncLinkDtl.linkNm}</a>
                    </c:forEach>
                </div>
            </c:if>
        </c:if>
        <%-- 상세안내, 사용후기 탭 --%>
        <div class="c-tabs c-tabs--type2" data-ui-tab>
            <div class="c-tabs__list" role="tablist">
                <button class="c-tabs__button" id="tab1" role="tab" aria-controls="tabC1panel" aria-selected="false" tabindex="-1">상세 안내</button>
                <button class="c-tabs__button" id="tab2" role="tab" aria-controls="tabC2panel" aria-selected="false" tabindex="-1">사용 후기</button>
            </div>
        </div>
        <%-- 상세 안내 (RATE101 외) --%>
        <div class="c-tabs__panel" id="tabC1panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1">

            <div class='c-product--box'>
                <c:if test="${!empty rateAdsvcVo.gdncDtlBasList}">
                    <c:forEach var="gdncDtlBas" items="${rateAdsvcVo.gdncDtlBasList}">
                        ${gdncDtlBas.rateAdsvcItemSbst}
                    </c:forEach>
                </c:if>

                <c:if test="${!empty rateAdsvcVo.gdncDtlList}">
                    <c:forEach var="gdncDtl" items="${rateAdsvcVo.gdncDtlList}" varStatus="status">
                        <c:choose>
                            <c:when test="${gdncDtl.fontStyleCd eq 'FOLD'}">
                                <!-- 접기기능 내용 영역 시작 -->
                                <div class="c-accordion-wrap">
                                    <div class="c-accordion c-accordion--type6" data-ui-accordion="">
                                        <div class="c-accordion__box">
                                            <div class="c-accordion__item">
                                                <div class="c-accordion__head">
                                                    <strong class="c-accordion__title" id="rate_detail__header_${status.index}">${gdncDtl.rateAdsvcItemNm}</strong>
                                                    <button class="c-accordion__button" type="button" aria-expanded="true" aria-controls="rate_detail_content_${status.index}">
                                                        <span class="c-hidden">상세열기</span>
                                                    </button>
                                                </div>
                                                <div class="c-accordion__panel expand" id="rate_detail_content_${status.index}" aria-labelledby="rate_detail__header_${status.index}" aria-hidden="false">
                                                    <div class="c-accordion__inside">${gdncDtl.rateAdsvcItemSbst}</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <!-- 접기기능 내용 영역 시작 -->
                            </c:when>
                            <c:otherwise>
                                <h4 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">${gdncDtl.rateAdsvcItemNm}</h4>
                                ${gdncDtl.rateAdsvcItemSbst}
                            </c:otherwise>
                        </c:choose>


                    </c:forEach>
                   </c:if>
            </div>
        </div>
        <%-- 사용 후기 --%>
        <div class="c-tabs__panel" id="tabC2panel" role="tabpanel" aria-labelledby="tab2" tabindex="-1">
            <div>
                <%-- 데이터 없는 경우 --%>
                <c:if test="${empty requestReviewList}">
                    <div class="c-nodata">
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-nodata__text">조회 결과가 없습니다.</p>
                    </div>
                </c:if>

                <%-- 데이터 있는 경우 --%>
                <div class="c-accordion review u-bt--0" id="accordionReview" data-ui-accordion>
                    <ul id="requestReviewList" class="c-accordion__box">
                        <c:if test="${!empty requestReviewList}">
                               <c:forEach var="requestReview" items="${requestReviewList}" varStatus="status">
                                <li class="c-accordion__item">
                                    <div class="c-accordion__head">
                                        <div class="review__label-wrap">
                                            <c:if test="${'1' eq requestReview.commendYn}">
                                                <span class="c-text-label c-text-label--mint-type2">추천</span>
                                            </c:if>
                                            <c:if test="${'1' eq requestReview.ntfYn}">
                                                <span class="c-text-label c-text-label--red">BEST</span>
                                            </c:if>
                                        </div>
                                        <ul class="review__info">
                                            <li class="review__info__item">
                                                <span class="sr-only">작성자</span>${requestReview.mkRegNm}
                                            </li>
                                            <li class="review__info__item">
                                                <span class="sr-only">작성일</span>${requestReview.sysRdateDd}
                                            </li>
                                        </ul>
                                        <strong class="review__title">${requestReview.eventNm}</strong>

                                         <c:if test="${!empty requestReview.reviewQuestionList}">
                                              <ul class="review-summary">
                                                <c:forEach var="reviewQuestion" items="${requestReview.reviewQuestionList}">
                                                   <li class="review-summary__item">
                                                      <div class="review-summary__question">${reviewQuestion.questionMm}</div>
                                                      <div class="review-summary__answer">${reviewQuestion.answerDesc}</div>
                                                   </li>
                                                </c:forEach>
                                              </ul>
                                         </c:if>

                                        <button class="runtime-data-insert c-accordion__button" id="acc_header_c${status.count}" type="button" aria-expanded="false" aria-controls="acc_content_c${status.count}" href="javascript:void(0);" onclick="increaseHit(this, ${requestReview.requestKey}, ${requestReview.reviewId}); return false;">
                                            <span class="c-hidden">유심 후기/바로배송 유심 후기 이벤트 내용 전체보기</span>
                                        </button>
                                    </div>
                                    <div class="c-accordion__panel expand" id="acc_content_c${status.count}" aria-labelledby="acc_header_c${status.count}">
                                        <div class="c-accordion__inside">
                                            <div>
                                                <div class="review__content">
                                                    <%-- 데이터 있을 경우 노출 --%>
                                                    <%-- 썸네일 이미지 --%>
                                                    <c:if test="${!empty requestReview.reviewImgList}">
                                                        <c:set var="reviewImgCtn" value="${fn:length(requestReview.reviewImgList)}"/>
                                                        <c:forEach var="reviewImg" items="${requestReview.reviewImgList}" begin="0" end="0" step="1">
                                                            <div class="review__preview">
                                                                <img src="${reviewImg.filePathNm}" alt="후기사진" onerror="this.src='/resources/images/portal/content/img_review_noimage.png'">
                                                                <c:if test="${reviewImgCtn > 1}">
                                                                    <span class="review__image__label">
                                                                        <span class="sr-only">썸네일사진 외 추가로 등록된 후기사진 개수</span>+${reviewImgCtn-1}
                                                                    </span>
                                                                </c:if>
                                                            </div>
                                                        </c:forEach>
                                                    </c:if>

                                                    <pre><p class="review__text">${requestReview.reviewSbst}</p></pre>
                                                    <c:if test="${!empty requestReview.snsInfo}">
                                                        <a class="review__button" href="${requestReview.snsInfo}" target="_blank" title="새창">SNS 공유 사용후기 보기</a>
                                                    </c:if>
                                                    <%-- 이미지가 있을 경우 노출 --%>
                                                    <c:if test="${!empty requestReview.reviewImgList}">
                                                        <ul class="review__images">
                                                            <c:forEach var="reviewImg" items="${requestReview.reviewImgList}">
                                                            <li class="review__images__item">
                                                                <div class="review__image">
                                                                    <img src="${reviewImg.filePathNm}" alt="" aria-hidden="true" onerror="this.src='/resources/images/portal/content/img_review_noimage.png'">
                                                                </div>
                                                                <c:if test="${!empty reviewImg.fileAlt}">
                                                                    <p class="c-bullet c-bullet--dot u-co-gray">${reviewImg.fileAlt}</p>
                                                                </c:if>
                                                            </li>
                                                            </c:forEach>
                                                        </ul>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                           </c:if>
                    </ul>
                </div>
                <nav class="c-paging" aria-label="검색결과">
                    <c:if test="${total > 10}">
                        <div id="moreList" class="c-button-wrap u-mt--8">
                            <button id="btnHide" class="c-button c-button--full" href="javascript:void(0);" onclick="goNextPage(); return false;">
                                더보기

                                <span id="pagingCnt" class="c-button__sub"><fmt:formatNumber value="${listCount}" type="number"/>/<fmt:formatNumber value="${total}" type="number"/></span>
                                <!-- span id="pagingCnt" class="c-button__sub">${listCount}/${total}</span -->
                                <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>

                                <span id="pageNo" style="display: none;">1</span>
                                <span id="maxPage" style="display: none;">${maxPage}</span>
                                <span id="listCount" style="display: none;">${listCount}</span>
                                <span id="total" style="display: none;">${total}</span>
                                <span id="rateCd" style="display: none;">${requestReviewDto.rateCd}</span>
                            </button>
                        </div>
                    </c:if>
                </nav>
            </div>
        </div>
    </div>
</div>
<script>
$(".c-tabs__button").on("keydown", function (e) {
      const $tabs = $(".c-tabs__button");
      const currentIndex = $tabs.index(this);
      let newIndex = currentIndex;

      if (e.key === "ArrowRight") {
        newIndex = (currentIndex + 1) % $tabs.length;
      } else if (e.key === "ArrowLeft") {
        newIndex = (currentIndex - 1 + $tabs.length) % $tabs.length;
      } else {
        return;
      }

      e.preventDefault();

      // 기존 탭 초기화
      $tabs
        .removeClass("is-active")
        .attr("aria-selected", "false")
        .attr("tabindex", "-1");

      // 새 탭 활성화
      $tabs.eq(newIndex)
        .addClass("is-active")
        .attr("aria-selected", "true")
        .attr("tabindex", "0")
        .focus();
    });


function goNextPage() {
    let pageNo = parseInt($('#pageNo').html());
    let maxPage = $('#maxPage').html();
    if (parseInt(pageNo) + 1 > parseInt(maxPage)) {
        alert('마지막 페이지입니다.');
        return;
    }

    $('#pageNo').html(parseInt($('#pageNo').html()) + 1);

    goPageAjax();
}

var count = 10;
// 사용후기 목록 조회
function goPageAjax() {
    var pageNo = $('#pageNo').html();
    var rateCd = $('#rateCd').html();
    var varData = ajaxCommon.getSerializedData({
        pageNo : pageNo,
        rateCd : rateCd
    });

    $.ajax({
        id : 'faqGetPagingAjax',
        type : 'POST',
        cache : false,
        url : '/rate/requestReviewGetPagingAjax.do',
        data : varData,
        dataType : "json",
        success : function(data) {
            $('#btnHide').show();

            let innerHTML = "";
            let v_requestReviewList = data.requestReviewDtoList;

            $.each(v_requestReviewList, function(idx, item) {
                let v_reviewImgList = item.reviewImgList
                let v_eventNm = item.eventNm;
                let v_regNm = item.mkRegNm;
                let v_sysRdateDd = item.sysRdateDd;
                let v_reviewSbst = item.reviewSbst;
                let v_requestKey = item.requestKey;
                let v_reviewId = item.reviewId;
                let v_commendYn = ajaxCommon.isNullNvl(item.commendYn, "");
                let v_ntfYn = ajaxCommon.isNullNvl(item.ntfYn, "");

                innerHTML += "<li class='c-accordion__item'>";
                innerHTML += "    <div class='c-accordion__head'>";
                innerHTML += "        <div class='review__label-wrap'>";
                if(v_commendYn == "1") {
                    innerHTML += "            <span class='c-text-label c-text-label--mint-type2'>추천</span>";
                }
                if(v_ntfYn == "1") {
                    innerHTML += "            <span class='c-text-label c-text-label--red'>BEST</span>";
                }
                innerHTML += "        </div>";
                innerHTML += "        <ul class='review__info'>";
                innerHTML += "            <li class='review__info__item'><span class='sr-only'>작성자</span>"+v_regNm+"</li>";
                innerHTML += "            <li class='review__info__item'><span class='sr-only'>작성일</span>"+v_sysRdateDd+"</li>";
                innerHTML += "        </ul>";
                innerHTML += "        <strong class='review__title'>"+v_eventNm+"</strong>";
                innerHTML += "        <button class='runtime-data-insert c-accordion__button' id='acc_header_c"+idx+"' type='button' aria-expanded='false' aria-controls='acc_content_c"+count+"' onclick='increaseHit(this, "+v_requestKey+", "+item.reviewId+"); return false;'>";
                innerHTML += "            <span class='c-hidden'>유심 후기/바로배송 유심 후기 이벤트 내용 전체보기</span>";
                innerHTML += "        </button>";
                innerHTML += "    </div>";
                innerHTML += "    <div class='c-accordion__panel expand' id='acc_content_c"+count+"' aria-labelledby='acc_header_c"+count+"'>";
                innerHTML += "        <div class='c-accordion__inside'>";
                innerHTML += "            <div>";
                innerHTML += "                <div class='review__content'>";
                innerHTML += "                    <p class='review__text'>"+v_reviewSbst+"</p>";
                if(v_reviewImgList != null) {
                    innerHTML += "                        <ul class='review__images'>";
                    $.each(v_reviewImgList, function(index, value) {
                        let v_filePathNm = value.filePathNm;
                        innerHTML += "                            <li class='review__images__item'>";
                        innerHTML += "                                <div class='review__image'>";
                        innerHTML += "                                    <img src='"+v_filePathNm+"' alt='' aria-hidden='true'>";
                        innerHTML += "                                </div>";
                        innerHTML += "                            </li>";
                    });
                    innerHTML += "                        </ul>";
                }
                innerHTML += "                </div>";
                innerHTML += "            </div>";
                innerHTML += "        </div>";
                innerHTML += "    </div>";
                innerHTML += "</li>";

                count += 1;
            });

            $('#requestReviewList').append(innerHTML);

            /* 더보기에 게시글 개수 setting */
            if ($('#total').html() < 10) {
                $('#btnHide').hide();
            }
            $("#pagingCnt").html(numberWithCommas(count) + "/" + numberWithCommas($('#total').html()));
            if (parseInt(count) >= parseInt($('#total').html())) {
                $('#moreList').hide();
            }
        }
    });
};

</script>
