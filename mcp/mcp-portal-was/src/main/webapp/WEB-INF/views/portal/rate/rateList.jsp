<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<t:insertDefinition name="layoutGnbDefaultTitle">
    <t:putAttribute name="titleAttr">요금제 소개</t:putAttribute>
    <t:putAttribute name="contentAttr">

    <c:set var="AIRecommendUrl" value="${nmcp:getCodeByDtlCdNm('AIRecommendUrl', requestScope['javax.servlet.forward.request_uri'])}" />
    <input type="hidden" id="isAIRecommendUrl" value="${empty AIRecommendUrl ? 'N': 'Y'}">

    <input type="hidden" id ="eventRatePop" value="${eventRatePop}" />
    <input type="hidden" id ="eventRateSeq" value="${eventRateSeq}" />
     <input type="hidden" id ="eventRateCd" value="${eventRateCd}" />

        <div class="ly-loading" id="subbody_loading">
            <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
        </div>

        <div class="ly-content rate-page" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">요금제</h2>
            </div>
            <div class="c-tabs c-tabs--type1">
                <%-- 메인 탭 --%>
                <div id="mainTab" class="c-tabs__list" role="tablist">
                </div>
            </div>
            <div id="rateListTabPanel" class="is-active" role="tabpanel">
                <h3 id="rateListTabLabel" class="sr-only"></h3>
                <div>
                    <div class="c-row c-row--xlg" id="divDraw">
                        <%-- 서브 탭 --%>
                        <div class="c-tabs c-tabs--type2 c-tabs--type3" id="divSubMain">
                            <div id="subTabPanel" class="c-tabs__list" role="tablist"></div>
                        </div>


                        <%-- 목록 --%>
                        <%--<div id="listPanel" class="is-active">
                            <span id="tab2Label" class="c-hidden"></span>
                            <div>
                                <div class="c-row c-row--lg">
                                    <div class="c-accordion product" id="accordionproductA" data-ui-accordion="">
                                        <ul id="listPanelUl" class="c-accordion__box"></ul>
                                    </div>
                                </div>
                            </div>
                        </div>--%>
                    </div>
                </div>
            </div>

            <!-- 요금제 비교함 버튼 -->
            <div class="rate-btn-wrap" >
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


            <%-- 약정할인 프로그램 및 할인반환금 안내 --%>
            <div class="c-button-wrap u-mt--48">
                  <button class="c-button c-button--underline" type="button" href="javascript:void(0);" onclick="javascript:openPage('pullPopup', '/rate/rateAgreeView.do', '');">약정할인 프로그램 및 할인반환금 안내</button>
            </div>

            <div class="c-row c-row--lg">
                <div class="c-box c-box--border u-mt--64">
                    <p class="c-text u-fs-26 u-fw--medium">kt M모바일만의 실속 있는 다양한 상품들을 만나보세요</p>
                    <div class="c-button-wrap c-button-wrap--left u-mt--24">
                        <a class="c-button c-button-round--md c-button--mint u-width--220" href="/appForm/appFormDesignView.do">유심 상품 가입</a> <a
                            class="c-button c-button-round--md c-button--mint u-width--220" href="/product/phone/phoneList.do">휴대폰 결합상품 가입</a>
                    </div>
                    <img class="c-box__image" src="/resources/images/portal/content/img_shop.png" alt="" aria-hidden="true">
                </div>
            </div>


        </div>
    </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script src="/resources/js/bluebird.core.js"></script>
        <script src="/resources/js/portal/vendor/swiper.min.js"></script>
        <script type="text/javascript" src="/resources/js/common/validationCheck.js"></script>
        <script type="text/javascript" src="/resources/js/sns.js"></script>
        <script type="text/javascript" src="/resources/js/portal/rate/rateList.js?version=2026-02-03"></script>
        <script>
        var v_ctgCdParam1 = '<c:out value="${rateAdsvcCtgBasDTO.ctgCdParam1}"/>';
        var v_ctgCdParam2 = '<c:out value="${rateAdsvcCtgBasDTO.ctgCdParam2}"/>';
        var v_ctgCdParam3 = '<c:out value="${rateAdsvcCtgBasDTO.ctgCdParam3}"/>';
        </script>
    </t:putAttribute>
    <t:putAttribute name="popLayerAttr">

        <!-- 요금제 비교함 팝업 시작 -->
        <div class="c-modal c-modal--xlg rate-modal" id="rateCompareModal" role="dialog" aria-labelledby="rateCompareTitle">
            <div class="c-modal__dialog" role="document">
                <div class="c-modal__content">
                    <div class="c-modal__header">
                        <h2 class="c-modal__title" id="rateCompareTitle">요금제 비교함</h2>
                        <button class="c-button" data-dialog-close>
                            <i class="c-icon c-icon--close" aria-hidden="true"></i>
                            <span class="c-hidden">팝업닫기</span>
                        </button>
                    </div>
                    <div class="c-modal__body c-modal__body--full">
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

                        </div>
                        <!-- 요금제 영역 끝 -->
                    </div>
                    <div class="c-modal__footer u-flex-center">
                        <div class="u-box--w460 c-button-wrap">
                            <button class="c-button c-button--full c-button--gray" id="cancel" data-dialog-close>닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 요금제 비교함 팝업 끝 -->



    </t:putAttribute>
</t:insertDefinition>