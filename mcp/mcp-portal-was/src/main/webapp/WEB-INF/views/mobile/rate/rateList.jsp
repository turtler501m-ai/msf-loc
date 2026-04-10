<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />

<t:insertDefinition name="mlayoutDefaultTitle">
    <t:putAttribute name="titleAttr">유심 요금제</t:putAttribute>
    <t:putAttribute name="contentAttr">

        <c:set var="AIRecommendUrl" value="${nmcp:getCodeByDtlCdNm('AIRecommendUrl', requestScope['javax.servlet.forward.request_uri'])}" />
        <input type="hidden" id="isAIRecommendUrl" value="${empty AIRecommendUrl ? 'N': 'Y'}">

        <input type="hidden" id ="eventRatePop" value="${eventRatePop}" />
        <input type="hidden" id ="eventRateSeq" value="${eventRateSeq}" />
        <input type="hidden" id ="eventRateCd" value="${eventRateCd}" />

        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">요금제<button class="header-clone__gnb"></button></h2>
              </div>

              <div class="c-tabs c-tabs--type2" data-ignore="true">
                  <%-- 메인 탭 --%>
                <div id="mainTab" class="c-tabs__list c-expand sticky-header">
                </div>
                <div id="tabA1-panel" class="c-tabs__panel">
                       <div class="c-tabs c-tabs--type3" data-ignore="true">
                           <%-- 서브 탭 --%>
                           <div id="subTabPanel" class="c-tabs__list c-expand">
                           </div>

                           <div id="divDraw">
                               <%--<div id="listPanel1" class="c-tabs__panel ">
                                   <div class="c-accordion c-accordion--type1 blue-inside u-mb--24">
                                       <ul class="c-accordion__box" id="accordion1">

                                       </ul>
                                   </div>
                               </div>--%>
                           </div>


                           <div class="c-button-wrap c-button-wrap--column">
                               <button class="c-button c-button--md c-button--white" onclick="javascript:openPage('pullPopup', '/m/rate/rateAgreeView.do', '');">
                                   <i class="c-icon c-icon--guide" aria-hidden="true"></i>
                                   <span>약정할인 프로그램 및 할인반환금</span>
                               </button>
                           </div>
                           <div class="payment-guide-box">
                               <i class="c-icon c-icon--payment-guide" aria-hidden="true"></i>
                               <p class="c-text c-text--type3 u-mt--16">
                                   kt M모바일만의
                                   <br>실속있는 다양한 상품들을 만나보세요
                               </p>
                               <div class="c-button-wrap">
                                   <a class="c-button c-button--full c-button--sm" href="/m/appForm/appFormDesignView.do">유심 상품 가입</a>
                                   <a class="c-button c-button--full c-button--sm" href="/m/product/phone/phoneList.do">휴대폰 결합상품 가입</a>
                               </div>
                           </div>
                       </div>
                </div>
              </div>
        </div>

        <!-- 요금제 비교함 버튼 -->
        <div class="rate-btn-wrap">
            <div class="rate-btn">
                <c:if test="${empty AIRecommendUrl}">
                    <button id="btnCompareView" >
                        요금제 비교함<i class="c-icon c-icon--comparison" aria-hidden="true"></i>
                        <span class="rate-btn__cnt cnt_none">0</span>
                    </button>
                </c:if>
                <c:if test="${not empty AIRecommendUrl}">
                    <button onclick="aiRecommendPopOpen();">
                        <i class="c-icon c-icon--ai-symbol u-ml--0 u-mr--8" aria-hidden="true"></i>AI 추천
                    </button>
                </c:if>
            </div>
        </div>

    </t:putAttribute>
    <t:putAttribute name="popLayerAttr">
        <!-- 요금제 비교함 팝업  -->
        <div class="c-modal rate-modal" id="rateCompareModal" role="dialog" tabindex="-1" aria-describedby="rateCompareTitle">
            <div class="c-modal__dialog c-modal__dialog--full" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h1 class="c-modal__title" id="rateCompareTitle">요금제 비교함</h1>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body">
                        <div class="rate-filter-wrap">
                            <div class="btn-sort-wrap">
                                <button type="button" class="btn-sort" data-order-value="XML_CHARGE" >가격</button>
                                <button type="button" class="btn-sort" data-order-value="XML_DATA" >데이터</button>
                                <button type="button" class="btn-sort" data-order-value="XML_VOICE" >통화</button>
                                <button type="button" class="btn-sort" data-order-value="CHARGE_NM" >이름</button>
                            </div>
                        </div>
                        <!-- 요금제 영역 시작 -->
                        <div class="rate-content">
                            <ul class="rate-content__list">
                                <!-- 리스트  -->

                                <!-- 리스트 끝 -->
                            </ul>
                        </div>
                    </div>
                    <div class="c-modal__footer">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--gray c-button--full u-width--120" data-dialog-close>닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 요금제 비교함 팝업 끝  -->




    </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script src="/resources/js/bluebird.core.js"></script>
        <script type="text/javascript" src="/resources/js/bluebird.core.js"></script>
        <script type="text/javascript" src="/resources/js/common/validationCheck.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/rate/rateList.js?version=2026-02-03"></script>
        <script type="text/javascript">
            var v_ctgCdParam1 = '<c:out value="${rateAdsvcCtgBasDTO.ctgCdParam1}"/>';
            var v_ctgCdParam2 = '<c:out value="${rateAdsvcCtgBasDTO.ctgCdParam2}"/>';
            var v_ctgCdParam3 = '<c:out value="${rateAdsvcCtgBasDTO.ctgCdParam3}"/>';
            var v_platFormCd = '<c:out value="${platFormCd}"/>';

            // 공통 : 아래 스크립트 추가
            window.onpopstate = function(event) {
                event.preventDefault();
                closeView();
            }
        </script>
    </t:putAttribute>
</t:insertDefinition>
