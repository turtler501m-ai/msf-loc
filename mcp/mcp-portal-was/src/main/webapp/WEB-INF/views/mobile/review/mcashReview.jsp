<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<un:useConstants var="Constants"
                 className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/review/mcashReview.js"></script>

        <style>
            /*(+버튼) 열기 클릭 시 총 이미지 개수 안보이게 처리 */
            .review__image.is-active .review__image__label{
                display: none;
            }
            /*(+버튼) 닫기 시 총 이미지 개수 보이게 처리 */
            .review__image .review__image__label{
                display: block;
            }

            /*(+버튼) 닫기 시 첫번째 이미지만 보이게 처리 */
            .review__image .review__image__item {
                display: none;
            }

            .review__image .review__image__item:first-child {
                display: block;
            }
        </style>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
            <input type="hidden" id=reviewVal value="${reviewVal}"/>
            <input type="hidden" id="platFormCd" value="${nmcp:getPlatFormCd()}" />
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">M쇼핑할인 사용후기<button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="c-tabs c-tabs--type2">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button type="button" class="c-tabs__button" onclick="javascript:location.href='/m/mcash/review/mcashReview.do';" data-tab-header="#tabA1-panel">사용후기 보기</button>
                    <button type="button" class="c-tabs__button" onclick="goReviewForm()">후기 작성하기</button>
                </div>
                <div class="c-tabs__panel" id="tabA1-panel">
                    <div class="swiper-container swiper-event-banner c-expand u-mt--32" id="swiperCardBanner" >
                        <div class="swiper-wrapper">

                        </div>
                    </div>

                    <div class="c-form">
                        <div class="c-form__title flex-type">후기 골라보기</div>

                        <select class="c-select" title="이벤트 선택" id="selReviewType" name="selReviewType">
                            <option value="">전체보기(${eventTotal})</option>
                            <c:forEach var="nmcpMainCdDtlDtoList" items="${nmcpMainCdDtlDtoList}">
                                <option value="${nmcpMainCdDtlDtoList.dtlCd}">${nmcpMainCdDtlDtoList.dtlCdNm}(${nmcpMainCdDtlDtoList.eventCdCtn})</option>
                            </c:forEach>
                        </select>
                        <div class="u-ta-right  u-mt--16">
                            <c:if test="${nmcp:hasLoginUserSessionBean() and checkUserType eq 'Y'}">
                                <input class="c-checkbox" id="chkMyReview" type="checkbox" name="chkMy">
                                <label class="c-label u-pl--28 u-mr--8" for="chkMyReview">내 후기</label>
                            </c:if>
                            <input class="c-checkbox" id="chkMyReviewBest" type="checkbox" name="reviewFilter">
                            <label class="c-label u-pl--28 u-mr--8" for="chkMyReviewBest">우수 후기</label>
                            <input class="c-checkbox" id="chkMyReviewSns" type="checkbox" name="reviewFilter">
                            <label class="c-label u-pl--28 u-mr--8" for="chkMyReviewSns">SNS 공유 후기</label>
                        </div>
                    </div>
                    <div class="u-mt--36" id="reviewBody">

                        <ul class="review" id="mcashReviewBoard">

                        </ul>
                    </div>
                    <div class="c-button-wrap">
                        <button class="c-button c-button--full" id="moreBtn">더보기<span class="c-button__sub"><span id="reviewCurrent"></span>/<span id="reviewTotal"></span></span>
                            <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="pageNo" name="pageNo" value="1"/>
        </div><!-- [START]-->
        <script src="../../resources/js/mobile/vendor/swiper.min.js"></script>

    </t:putAttribute>
</t:insertDefinition>