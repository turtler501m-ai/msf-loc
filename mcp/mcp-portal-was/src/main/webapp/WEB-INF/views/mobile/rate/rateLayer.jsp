<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />

<div class="c-modal__content">
    <div class="c-modal__header">
        <h1 class="c-modal__title" id="search-info-title">${gdncBasVo.rateAdsvcNm}</h1>
        <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i> <span class="c-hidden">팝업닫기</span>
        </button>
    </div>
    <div class="c-modal__body">
        <div>
            <ul class="payment-info">
                <li class="payment-info__item">
                    <img src="/resources/images/mobile/common/ico_payment_data_gray.svg" alt="">
                    <span class="c-hidden">데이터</span>
                    <div>
                    <c:choose>
                        <c:when test="${!empty rateAdsvcVo.bnfitData}">
                            <span class="max-data-label">
                               <c:if test="${!empty rateAdsvcVo.maxDataDelivery}"><em>${rateAdsvcVo.maxDataDelivery}</em></c:if>
                            </span>
                                     ${rateAdsvcVo.bnfitData}
                            <c:if test="${!empty rateAdsvcVo.promotionBnfitData}"><span class="product-summary__sub">(${rateAdsvcVo.promotionBnfitData})</span></c:if>
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                    </div>
                </li>
                <li class="payment-info__item">
                    <img src="/resources/images/mobile/common/ico_payment_call_gray.svg" alt="">
                    <div>
                    <c:choose>
                        <c:when test="${!empty rateAdsvcVo.bnfitVoice}">
                            ${rateAdsvcVo.bnfitVoice}
                            <c:if test="${!empty rateAdsvcVo.promotionBnfitVoice}"><span class="product-summary__sub">(${rateAdsvcVo.promotionBnfitVoice})</span></c:if>
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                    </div>
                </li>
                <li class="payment-info__item">
                    <img src="/resources/images/mobile/common/ico_payment_sms_gray.svg" alt="">
                    <span class="c-hidden">문자</span>
                    <div>
                    <c:choose>
                        <c:when test="${!empty rateAdsvcVo.bnfitSms}">
                            ${rateAdsvcVo.bnfitSms}
                            <c:if test="${!empty rateAdsvcVo.promotionBnfitSms}"><span class="product-summary__sub">(${rateAdsvcVo.promotionBnfitSms})</span></c:if>
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                    </div>
                </li>
                <li class="payment-info__item">
                    <img src="/resources/images/mobile/common/ico_payment_wifi_gray.svg" alt="">
                    <span class="c-hidden">와이파이</span>
                    <c:choose>
                        <c:when test="${!empty rateAdsvcVo.bnfitWifi}">${rateAdsvcVo.bnfitWifi}</c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </li>

                <%-- 제휴혜택 노출문구 (RATEBE11) --%>
                <c:if test="${!empty rateAdsvcVo.bnfitList}">
                    <c:forEach var="bnfit" items="${rateAdsvcVo.bnfitList}">
                        <li class="payment-info__item">
                            <img src="/resources/images/mobile/common/ico_payment_use_gray.svg" alt="">
                            <span class="c-hidden">사은품</span>
                                ${bnfit.rateAdsvcItemDesc}
                            <c:if test="${!empty bnfit.rateAdsvcItemApdDesc}"><span class="product-summary__sub">(${bnfit.rateAdsvcItemApdDesc})</span></c:if>
                        </li>
                    </c:forEach>
                </c:if>
            </ul>
            <hr class="c-hr c-hr--type2">
            <div class="payment-price">
                <dl>
                    <dt>기본료 (VAT 포함)</dt>
                    <c:choose>
                        <c:when test="${empty rateAdsvcGdncBasDTO.rateDiscntAmtVatDesc and empty rateAdsvcGdncBasDTO.seniorDiscntAmtVatDesc and
                            empty rateAdsvcGdncBasDTO.seniorDiscntAmtVatDesc and empty gdncBasVo.promotionAmtVatDesc}">
                            <dd class="u-td">${gdncBasVo.mmBasAmtVatDesc} 원</dd>
                        </c:when>
                        <c:otherwise>
                            <dd class="u-td-line-through">${gdncBasVo.mmBasAmtVatDesc} 원</dd>
                        </c:otherwise>
                    </c:choose>
                </dl>
                <c:if test="${!empty rateAdsvcGdncBasDTO.contractAmtVatDesc}">
                    <dl>
                        <dt class="u-co-black"><b>약정시 기본료(VAT 포함)</b></dt>
                        <dd class="u-co-mint"><b>${rateAdsvcGdncBasDTO.contractAmtVatDesc}</b> <span>원</span></dd>
                    </dl>
                </c:if>
                <c:if test="${!empty rateAdsvcGdncBasDTO.rateDiscntAmtVatDesc}">
                    <dl>
                        <dt class="u-co-black"><b>요금할인 시 기본료(VAT 포함)</b></dt>
                        <dd class="u-co-mint"><b>${rateAdsvcGdncBasDTO.rateDiscntAmtVatDesc}</b> <span>원</span></dd>
                    </dl>
                </c:if>
                <c:if test="${!empty rateAdsvcGdncBasDTO.seniorDiscntAmtVatDesc}">
                    <dl>
                        <dt class="u-co-black"><b>시니어 할인 시 기본료(VAT 포함)</b></dt>
                        <dd class="u-co-mint"><b>${rateAdsvcGdncBasDTO.seniorDiscntAmtVatDesc}</b> <span>원</span></dd>
                    </dl>
                </c:if>
                <c:if test="${!empty gdncBasVo.promotionAmtVatDesc}">
                    <dl>
                        <dt class="u-co-black"><b>프로모션 요금 (VAT 포함)</b></dt>
                        <dd class="u-co-mint"><b>${gdncBasVo.promotionAmtVatDesc}</b> <span>원</span></dd>
                    </dl>
                </c:if>
            </div>



            <%-- 요금제부가서비스안내링크상세 --%>
            <c:if test="${'N' ne rateAdsvcCtgBasDTO.btnDisplayYn}">
                <c:if test="${!empty rateAdsvcVo.gdncLinkDtlList}">
                    <div class="c-button-wrap c-button-wrap--column">
                        <c:forEach var="gdncLinkDtl" items="${rateAdsvcVo.gdncLinkDtlList}" varStatus="status">
                            <c:choose>
                                <c:when test="${status.count == 1}">
                                    <a href="javascript:void(0);" onclick="location.href=getReplaceUrl('${gdncLinkDtl.linkUrl}');" class="c-button c-button--full c-button--mint--type2">${gdncLinkDtl.linkNm}</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="javascript:void(0);" onclick="location.href=getReplaceUrl('${gdncLinkDtl.linkUrl}');" class="c-button c-button--full c-button--mint">${gdncLinkDtl.linkNm}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </c:if>
            </c:if>


            <div class="c-tabs c-tabs--type3">
                <%-- 상세안내, 사용후기 탭 --%>
                <div class="c-tabs__list u-ta-left c-expand" data-tab-list="">
                    <button class="c-tabs__button" data-tab-header="#tabD1-panel">상세안내</button>
                    <button class="c-tabs__button" data-tab-header="#tabD2-panel">사용후기</button>
                </div>
                <%-- 상세 안내 (RATE101 외) --%>
                <div class="c-tabs__panel" id="tabD1-panel">

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
                                            <!-- 1번째 접기 내용 -->
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
                                        <h4 >${gdncDtl.rateAdsvcItemNm}</h4>
                                        ${gdncDtl.rateAdsvcItemSbst}
                                    </c:otherwise>
                                </c:choose>


                            </c:forEach>

                        </c:if>
                    </div>
                </div>
                <%-- 사용 후기 --%>
                <div class="c-tabs__panel" id="tabD2-panel">
                    <c:if test="${empty requestReviewList}">
                        <div class="c-nodata">
                            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                            <p class="c-nodata__text">조회 결과가 없습니다.</p>
                        </div>
                    </c:if>
                    <ul id="requestReviewList" class="review">
                        <c:if test="${!empty requestReviewList}">
                            <c:forEach var="requestReview" items="${requestReviewList}" varStatus="status">
                                <li class="review__item">
                                    <div class="label-wrap">
                                        <c:if test="${'1' eq requestReview.commendYn}">
                                            <span class="c-text-label c-text-label--mint-type1">추천</span>
                                        </c:if>
                                        <c:if test="${'1' eq requestReview.ntfYn}">
                                            <span class="c-text-label c-text-label--red">BEST</span>
                                        </c:if>
                                    </div>
                                    <h3 class="review__title">${requestReview.eventNm}</h3>
                                    <div class="review__info">

                                        <c:choose>
                                            <c:when test="${!empty requestReview.mkRegNm}">
                                                <span class="review__user">${requestReview.mkRegNm}</span>
                                                <span class="review__date">${requestReview.sysRdateDd}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="review__user">${requestReview.sysRdateDd}</span>
                                            </c:otherwise>
                                        </c:choose>

                                    </div>

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

                                    <div class="review__content fs-13 c-text-ellipsis--type3" id="content_${status.count}">
                                            <pre>${requestReview.reviewSbst}</pre>
                                        <c:if test="${!empty requestReview.snsInfo}">
                                            <p class="u-mt--8">
                                                <c:if test = "${platFormCd eq 'A'}">
                                                    <a class="review__button" href="javascript:appOutSideOpen('${requestReview.snsInfo}');" title="새창열림">SNS 공유 사용후기 보기</a>
                                                </c:if>
                                                <c:if test = "${platFormCd ne 'A'}">
                                                    <a class="review__button" href="${requestReview.snsInfo}" target="_blank" title="새창열림">SNS 공유 사용후기 보기</a>
                                                </c:if>
                                            </p>
                                        </c:if>
                                    </div>
                                        <%-- #content_01 은 컨텐츠 ID와 반드시 매칭 되어야 합니다. --%>
                                    <c:choose>
                                        <c:when test="${!empty requestReview.reviewImgList}">
                                            <c:set var="datToggleTarget" value="#content_img_${status.count}|#content_${status.count}" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="datToggleTarget" value="#content_${status.count}" />
                                        </c:otherwise>
                                    </c:choose>

                                    <c:if test="${!empty requestReview.reviewImgList}">
                                        <c:set var="reviewImgCtn" value="${fn:length(requestReview.reviewImgList)}"/>
                                        <div class="review__image" id="content_img_${status.count}">
                                            <c:if test="${reviewImgCtn > 1}">
                                                <span class="c-text-label c-text-label--darkgray img-count">+${reviewImgCtn-1}</span>
                                            </c:if>
                                            <c:forEach var="reviewImg" items="${requestReview.reviewImgList}">
                                                <div class="review__image__item">
                                                    <img src="${pageContext.request.contextPath}${reviewImg.filePathNm}" alt="${reviewImg.fileAlt}" title="${reviewImg.fileAlt}" onerror="this.src='/resources/images/mobile/content/img_review_noimage.png'">
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </c:if>

                                    <div class="review__button-wrap" data-toggle-class="is-active" data-toggle-target="${datToggleTarget}">
                                        <button type="button" aria-expanded="false" onclick="increaseHit(this, ${requestReview.requestKey}, ${requestReview.reviewId}); return false;">
                                            <i class="c-icon c-icon--arrow-down-bold" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                    <c:if test="${total > 10}">
                        <div id="moreList" class="c-button-wrap u-mt--8">
                            <button id="btnHide" class="c-button c-button--full" onclick="goNextPage(); return false;">
                                더보기
                                <span id="pagingCnt" class="c-button__sub"><fmt:formatNumber value="${listCount}" type="number"/>/<fmt:formatNumber value="${total}" type="number"/></span>
                                <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>

                                <span id="pageNo" style="display: none;">1</span>
                                <span id="maxPage" style="display: none;">${maxPage}</span>
                                <span id="listCount" style="display: none;">${listCount}</span>
                                <span id="total" style="display: none;">${total}</span>
                                <span id="rateCd" style="display: none;">${requestReviewDto.rateCd}</span>
                            </button>
                        </div>
                    </c:if>
                </div>






            </div>

        </div>


    </div>
</div>

<script>

// 사용후기 더보기
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
        url : '/m/rate/requestReviewGetPagingAjax.do',
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
                let v_snsInfo = ajaxCommon.isNullNvl(item.snsInfo, "");
                //let v_platFormCd = v_platFormCd;

                innerHTML += "<li class='review__item'>";
                innerHTML += "    <div class='label-wrap'>";
                if(v_commendYn == "1") {
                    innerHTML += "        <span class='c-text-label c-text-label--mint-type1'>추천</span>";
                }
                if(v_ntfYn == "1") {
                    innerHTML += "        <span class='c-text-label c-text-label--red'>BEST</span>";
                }
                innerHTML += "    </div>";
                innerHTML += "    <h3 class='review__title'>"+v_eventNm+"</h3>";
                innerHTML += "    <div class='review__info'>";
                innerHTML += "        <span class='review__user'>"+v_regNm+"</span>";
                innerHTML += "        <span class='review__date'>"+v_sysRdateDd+"</span>";
                innerHTML += "    </div>";

                if(v_reviewImgList != null && v_reviewImgList != "") {
                    $.each(v_reviewImgList, function(index, value) {
                        let v_filePathNm = value.filePathNm;
                        innerHTML += "          <div class='review__image__item' id='content_img_"+(count+1)+"'>";
                        innerHTML += "                  <img src='"+v_filePathNm+"' alt=''>";
                        innerHTML += "          </div>";
                    });
                }
                innerHTML += "    <div class='review__content fs-13 c-text-ellipsis--type3' id='content_"+(count+1)+"'>";
                innerHTML +=          v_reviewSbst;

                if(v_snsInfo != "") {
                    innerHTML += "            <p class='u-mt--8'>";
                    if(platFormCd == "A") {
                        innerHTML += "                <a class='fs-14 c-text-link--blue u-ml--0 u-mb--2' href='javascript:appOutSideOpen('"+v_snsInfo+"');' title='새창열림'>SNS 공유 사용후기 자세히 보기</a>";
                    } else if(platFormCd != "A") {
                        innerHTML += "                 <a class='fs-14 c-text-link--blue u-ml--0 u-mb--2' href='"+v_snsInfo+"' target='_blank' title='새창열림'>SNS 공유 사용후기 자세히 보기</a>";
                    }
                    innerHTML += "           </p>";
                }
                innerHTML += "    </div>";

                let dataToggleTarget = "";
                if(v_reviewImgList != null && v_reviewImgList != "") {
                    dataToggleTarget = "#content_img_"+(count+1)+"|#content_"+(count+1);
                } else {
                    dataToggleTarget = "#content_"+(count+1);
                }
                innerHTML += "    <div class='review__button-wrap' data-toggle-class='is-active' data-toggle-target='"+dataToggleTarget+"'>";
                innerHTML += "        <button type='button' aria-expanded='false' onclick='increaseHit(this,"+v_requestKey+", "+v_reviewId+"); return false;'>";
                innerHTML += "            <i class='c-icon c-icon--arrow-down-bold' aria-hidden='true'></i>";
                innerHTML += "        </button>";
                innerHTML += "    </div>";
                innerHTML += "</li>";

                count += 1;
            });

            $('#requestReviewList').append(innerHTML);
            MCP.init();

            /* 더보기에 게시글 개수 setting */
            if ($('#total').html() < 10) {
                $('#btnHide').hide();
            }
            $("#pagingCnt").html(count + "/" + $('#total').html());
            if (parseInt(count) >= parseInt($('#total').html())) {
                $('#moreList').hide();
            }
        }
    });
};

</script>
