<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<c:catch var="exceptjsp">
    <c:set var="serverNm" value="${nmcp:getPropertiesVal('SERVER_NAME')}" />
    <c:set var="menuInfo" value="${nmcp:getCurrentMenuInfo()}" />
    <c:set var="menuInfoList" value="${nmcp:getMenuInfoList()}" />
    <c:set var="jsver" value="${nmcp:getJsver()}" />
    <c:set var="titleNm" value="${nmcp:getCurrentMenuName()}" />
    <c:set var="isIgnoredScript" value="${nmcp:hasIgnoredScriptSession()}" />
    <c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />

    <c:if test="${exceptjsp != null}">
        <input type="hidden" id="execptjsp" value="${exceptjsp.message}">
    </c:if>
</c:catch>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes,maximum-scale=5.0, minimum-scale=1.0, viewport-fit=cover" >
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="No-Cache">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <t:insertAttribute name="metaTagAttr" ignore="true" defaultValue="/WEB-INF/views/layouts/metaTagDeafault.jsp"/>

    <title>
        <c:if test="${titleNm eq '메인'}"><t:insertAttribute name="titleAttr" ignore="true"/>압도적 1등 알뜰폰 | kt M모바일 공식 다이렉트몰</c:if>
        <c:if test="${titleNm ne '메인'}"><t:insertAttribute name="titleAttr" ignore="true" defaultValue="${titleNm} "/> | kt M모바일 공식 다이렉트몰</c:if>
    </title>

    <c:if test = "${serverNm eq 'REAL' && !isIgnoredScript }">
        <%@ include file="/WEB-INF/views/layouts/googleTagScript.jsp" %>
        <t:insertAttribute name="googleTagScript" ignore="true"/>
    </c:if>

	<link rel="stylesheet" href="/resources/css/aos/aos.css"  />
    <link href="/resources/css/mobile/styles.css?version=${Constants.CSS_VERSION}" rel="stylesheet" />
    <link rel="icon" href="/favorites_icon03.png" type="image/x-icon">

    <script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery-migrate-1.3.0.js"></script>
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/jqueryCommon.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/mobile/common.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/mobile/chatbot.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/mobile/popup/maskingPop.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/popup/aiRecommendPop.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/aos.js"></script>

</head>

<body>
<!-- 연관검색어 처리  -->
<span itemscope="" itemtype="http://schema.org/Organization" class="c-hidden">
    <link itemprop="url" href="https://www.ktmmobile.com">
    <a itemprop="sameAs" href="https://cafe.naver.com/ktmmobile20">네이버카페</a>
    <a itemprop="sameAs" href="https://blog.naver.com/ktmmobile20">네이버</a>
    <a itemprop="sameAs" href="https://www.facebook.com/ktmmobile.official/">facebook</a>
    <a itemprop="sameAs" href="https://www.instagram.com/kt_m_mobile/">instagram</a>
    <a itemprop="sameAs" href="https://www.youtube.com/channel/UCSvOg4rSu395lPz6uMqBekw">youtube</a>
    <a itemprop="sameAs" href="https://tv.naver.com/ktmmobile">네이버TV</a>
</span>
<c:if test = "${serverNm ne 'REAL'}">
    <input type="hidden" id="serverNm" name="serverNm" value="${serverNm}" />
<h1 style="background-color: red;position: fixed;width: 100%;z-index: 99;font-size:11px;">=== ${serverNm } / ${menuInfo.floatingShowYn},${menuInfo.floatingTipSbst},${menuInfo.chatbotTipSbst} === [[[${titleNm}]]]</h1>
</c:if>

<c:catch var="exceptjsp">
    <input type="hidden" id="gnbMCode" value="${nmcp:getGnbMenuCode()}" />
    <input type="hidden" id="phoneOs" value="${nmcp:getPhoneOs()}"/>

    <c:if test="${exceptjsp != null}">
        <input type="hidden" id="execptjsp2" value="${exceptjsp.message}">
    </c:if>
</c:catch>
    <input type="hidden" id="maskingSession" name="maskingSession" value="${maskingSession}">

    <c:set var="AILandingUrl" value="${nmcp:getCodeNmDto('AISetting', 'landing')}" />
    <input type="hidden" id="AILandingUrl" value="${AILandingUrl.expnsnStrVal1}">

    <div class="ly-wrap">

        <div id="skipNav">
            <a class="skip-link" href="#main-content">본문 바로가기</a>
        </div>

        <t:insertAttribute name="mainTopBannerAttr" ignore="true"/>

        <t:insertAttribute name="mncpGnbAttr" ignore="true" />

        <t:insertAttribute name="contentAttr" ignore="true" />

        <div id="divFloating" class="floating-event-banner"></div>
        <script>
            /* 플로팅 배너 스크롤 기능 */
            let lastScrollY = window.scrollY;
            const target = document.querySelector('#divFloating');

            window.addEventListener('scroll', () => {
                const currentScrollY = window.scrollY;

                if (currentScrollY < lastScrollY) {
                    target.classList.remove('hidden');
                } else if (currentScrollY > lastScrollY) {
                    target.classList.add('hidden');
                }

                lastScrollY = currentScrollY;
            });

            /* 플로팅 배너 버튼 3개일 떄 클래스 추가 */
            document.addEventListener('DOMContentLoaded', function () {
                const buttonGroup = document.querySelector('.floating-event-banner__button-group');
                if (!buttonGroup) return;

                function updateClass() {
                    const buttons = buttonGroup.querySelectorAll('.floating-event-banner__button');
                    if (buttons.length === 3) {
                        buttonGroup.classList.add('is-btn-3');
                    } else {
                        buttonGroup.classList.remove('is-btn-3');
                    }
                }

                updateClass();

                const observer = new MutationObserver(updateClass);
                observer.observe(buttonGroup, {
                    childList: true,
                    subtree: true,
                });
            });
        </script>

        <t:insertAttribute name="mncpFooterAttr" ignore="true"/>
    </div>

<c:if test="${not empty menuInfo }">
<c:if test="${not empty menuInfoList }">

    <c:if test = "${not empty menuInfo.floatingShowYn}">
    <c:if test = "${menuInfo.floatingShowYn eq 'Y'}">
        <c:set var="floatingTipSbst" value="무엇이든 물어보세요." />
        <c:if test = "${not empty menuInfo.floatingTipSbst}">
            <c:set var="floatingTipSbst" value="${menuInfo.floatingTipSbst}" />
        </c:if>
        <c:set var="chatbotTipSbst" value="무엇이든 물어보세요." />
        <c:if test = "${not empty menuInfo.chatbotTipSbst}">
            <c:set var="chatbotTipSbst" value="${menuInfo.chatbotTipSbst}" />
        </c:if>

        <nav class="c-fab">
            <input class="c-fab__open" id="c-fab__open" type="checkbox" href="javascript:;">

            <c:if test="${not empty menuInfo.aiRecoShowYn and menuInfo.aiRecoShowYn eq 'Y'}">
                <button type="button" class="c-fab__ai" onclick="aiRecommendPopOpen();">
                    <i class="c-icon c-icon--ai-recomd" aria-hidden="true"></i>
                </button>
            </c:if>
            <label class="c-fab__open--btn" for="c-fab__open">
                <span class="lines line-1"></span>
                <span class="lines line-2"></span>
                <span class="lines line-3"></span>
                <span class="c-fab__open--txt">${floatingTipSbst }</span>
            </label>

    <c:forEach items="${menuInfoList}" var="infoList" varStatus="vsdepth">
        <c:if test = "${infoList.urlAtribValCd eq 'FLOATFAQ' && infoList.atribUseYn eq 'Y'}">
            <a class="c-fab__item faq" href="/m/cs/faqList.do">
                <img src="/resources/images/mobile/common/ico_faq.svg" alt="faq">
            </a>
        </c:if>
    </c:forEach>

    <c:forEach items="${menuInfoList}" var="infoList" varStatus="vsdepth">
        <c:if test = "${infoList.urlAtribValCd eq 'FLOATCONSL' && infoList.atribUseYn eq 'Y'}">
            <a class="c-fab__item help" href="/m/cs/csInquiryInt.do">
                <img src="/resources/images/mobile/common/ico_onevsone.svg" alt="1:1">
            </a>
        </c:if>
    </c:forEach>

    <c:forEach items="${menuInfoList}" var="infoList" varStatus="vsdepth">
        <c:if test = "${infoList.urlAtribValCd eq 'FLOATCHAT' && infoList.atribUseYn eq 'Y'}">
            <a class="c-fab__item chat" href="javascript:chatbotTalkOpen();">
                <img src="/resources/images/public/icon/ico_chat.svg" alt="챗봇">
            </a>
        </c:if>
    </c:forEach>

            <button type="button" class="c-fab__delete">
                <div class="c-hidden">help 삭제</div>
                <i class="c-icon c-icon--close-black" aria-hidden="true"></i>
            </button>
            <div class="fab-dim"></div>
        </nav>
    </c:if>
    </c:if>

    <%--  AI 추천 플로팅만 노출--%>
    <c:if test="${empty menuInfo.floatingShowYn or menuInfo.floatingShowYn ne 'Y'}">
        <c:if test="${not empty menuInfo.aiRecoShowYn and menuInfo.aiRecoShowYn eq 'Y'}">
            <nav class="c-fab">
                <button type="button" class="c-fab__ai" onclick="aiRecommendPopOpen();" style="position: absolute;">
                    <i class="c-icon c-icon--ai-recomd" aria-hidden="true"></i>
                </button>
                <button type="button" class="c-fab__delete">
                    <div class="c-hidden">help 삭제</div>
                    <i class="c-icon c-icon--close-black" aria-hidden="true"></i>
                </button>
            </nav>
        </c:if>
    </c:if>
</c:if>
</c:if>
	<nav class="c-top">
      <a href="#skipNav" title="페이지 상단으로 이동하기">
        <i class="c-icon c-icon--arrow-type2" aria-hidden="true"></i>
      </a>
    </nav>
    <t:insertAttribute name="popLayerAttr" ignore="true"/>

    <div class="c-modal" id="modalArs" role="dialog" tabindex="-1" aria-describedby="#modalArsTitle">
        <div class="c-modal__dialog c-modal__dialog--full" role="document" id="pullModalContents">
        </div>
    </div>

    <div class="c-modal" id="modalArs2nd" role="dialog" tabindex="-1" aria-describedby="#modalArs2ndTitle">
        <div class="c-modal__dialog c-modal__dialog--full" role="document" id="pullModal2ndContents">
        </div>
    </div>

    <div class="c-modal" id="modalTerms" role="dialog" tabindex="-1" aria-describedby="#modalTermsTitle">
     <div class="c-modal__dialog c-modal__dialog--full" role="document">
         <div class="c-modal__content">
           <div class="c-modal__header">
             <h1 class="c-modal__title" id="terms-title"></h1>
             <button class="c-button" data-dialog-close>
               <i class="c-icon c-icon--close" aria-hidden="true"></i>
               <span class="c-hidden">팝업닫기</span>
             </button>
           </div>
           <div class="c-modal__body" id="termsModalContents"></div>
           <div class="c-modal__footer">
             <button class="c-button c-button--full c-button--primary" data-dialog-close onclick="targetTermsAgree();">동의 후 닫기</button>
           </div>
         </div>
     </div>
    </div>

    <!-- 마케팅 수신동의 팝업 시작 -->
    <input type="hidden" id="platFormCd" value="${platFormCd}">
    <div class="c-modal" id="mktModalTerms" role="dialog" tabindex="-1" aria-describedby="#mktModalTermsTitle">
     <div class="c-modal__dialog c-modal__dialog--full" role="document">
         <div class="c-modal__content">
           <div class="c-modal__header">
             <h1 class="c-modal__title" id="mkt-terms-title"></h1>
             <button class="c-button" data-dialog-close>
               <i class="c-icon c-icon--close" aria-hidden="true"></i>
               <span class="c-hidden">팝업닫기</span>
             </button>
           </div>
           <div class="c-modal__body" id="mktTermsModalContents"></div>
           <div class="c-modal__footer">
             <button class="c-button c-button--full c-button--primary" data-dialog-close onclick="mktTargetTermsAgree();">동의 후 닫기</button>
           </div>
         </div>
     </div>
    </div>
    <!-- 마케팅 수신동의 팝업 끝-->

    <!-- 컨텐츠 소개  -->
    <div class="c-modal" id="modalInfoTerms" role="dialog" tabindex="-1" aria-describedby="#modalTermsTitle">
     <div class="c-modal__dialog c-modal__dialog--full" role="document">
         <div class="c-modal__content">
           <div class="c-modal__header">
             <h1 class="c-modal__title" id="content-title"></h1>
             <button class="c-button" data-dialog-close>
               <i class="c-icon c-icon--close" aria-hidden="true"></i>
               <span class="c-hidden">팝업닫기</span>
             </button>
           </div>
           <div class="c-modal__body" id="contentmModalContents"></div>
         </div>
     </div>
    </div>

    <t:insertAttribute name="mainBannerAttr" ignore="true"/>
    <!-- [END] -->

    <!-- [START]  팝업 -->
    <div class="c-modal c-modal--bs" id="main_bottom_banner" data-dialog-destroy="true" role="dialog" tabindex="-1" aria-describedby="#main_bottom_banner_desc" aria-labelledby="#main_bottom_banner_title">
        <div class="c-modal__dialog main-bs" role="document">
            <div class="c-modal__content">
                <div class="c-modal__header sr-only">
                    <h1 class="c-modal__title" id="main_bottom_banner_title">팝업 이름</h1>
                </div>
                <div id="main_bottom_banner_desc" class="c-modal__body main-bottom-banner">
                    <div class="swiper-container loading">
                        <div id="popupList" class="swiper-wrapper">
                        </div>
                    </div>
                       <div class="swiper-controls-wrap">
                        <div class="swiper-controls">
                            <div class="swiper-pagination pagination-num"></div>
                        </div>
                    </div>
                </div>
                <div class="main-bs__footer">
                    <a class="c-text c-text--type7" id="today_stop" href="#n">오늘 하루 그만보기</a>
                    <a class="c-text c-text--type3 u-fw--bold" href="#n" data-dialog-close>닫기</a>
                </div>
            </div>
        </div>
    </div>
    <!-- [END]  팝업 -->

    <!-- 에디터 팝업 -->
    <div class="c-modal c-modal--editor" id="modalEditor" role="dialog" aria-describedby="modalEditorTitle" >
        <div class="c-modal__dialog c-modal__dialog--full" role="document">
            <div class="c-modal__content u-bg--transparent">
                <div class="c-modal__header sr-only">
                    <h1 class="c-modal__title" id="modalEditorTitle">이벤트 배너 모아보기</h1>
                </div>
                <div class="main-bs__footer u-bg--transparent c-flex--jfy-center">
                    <a class="u-block" data-dialog-close>
                        <i class="c-icon c-icon--close-ty2" aria-hidden="true"></i>
                    </a>
                </div>
                <div class="c-modal__body" id="editorDiv">
                    <!-- 에디터 영역 -->
                </div>
            </div>
        </div>
    </div>
    <!-- 에디터 팝업 끝 -->
    <!-- [START]  일회성 팝업 -->
    <div class="c-modal c-modal--bs" id="modalOneTime" data-dialog-destroy="true" role="dialog" tabindex="-1" aria-describedby="#modalOneTimeTitleDesc" aria-labelledby="#modalOneTimeTitle">
        <div class="c-modal__dialog main-bs" role="document">
            <div class="c-modal__content">
                <div class="c-modal__header sr-only">
                    <h1 class="c-modal__title" id="modalOneTimeTitle">팝업 이름</h1>
                </div>
                <div id="modalOneTimeTitleDesc" class="c-modal__body main-bottom-banner">
                    <div class="swiper-container loading">
                        <div id="oneTimeDiv" class="swiper-wrapper">
                        </div>
                    </div>
                </div>
                <div class="main-bs__footer" style="justify-content: center">
                    <a class="c-text c-text--type3 u-fw--bold" href="#n" data-dialog-close>닫기</a>
                </div>
            </div>
        </div>
    </div>
    <!-- 일회성 팝업 끝 -->

    <!-- 일회성 에디터 팝업 -->
    <div class="c-modal c-modal--editor" id="modalOneTimeEditor" role="dialog" aria-labelledby="modalOneTimeEditorTitle">
        <div class="c-modal__dialog c-modal__dialog--full" role="document">
            <div class="c-modal__content u-bg--transparent">
                <div class="c-modal__header sr-only">
                    <h1 class="c-modal__title" id="modalOneTimeEditorTitle">이벤트 배너 모아보기</h1>
                </div>
                <div class="main-bs__footer u-bg--transparent c-flex--jfy-center">
                    <a class="u-block" data-dialog-close>
                        <i class="c-icon c-icon--close-ty2" aria-hidden="true"></i>
                    </a>
                </div>
                <div class="c-modal__body" id="oneTimeEditorDiv">
                    <!-- 에디터 영역 -->
                </div>
            </div>
        </div>
    </div>
    <!-- 일회성 에디터 팝업 끝 -->
    <!-- 마케팅 수신 동의 팝업 -->
    <form id="mktInputForm" name="mktInputForm" method="post">
        <input type="hidden" name="mktAgree" id="MKTTERMSMEM04" value2="mktPersonalInfoCollectAgree" />
        <input type="hidden" name="mktAgree" id="MKTTERMSMEM03" value2="mktClausePriAdFlag" />
        <input type="hidden" name="mktAgree" id="MKTTERMSMEM05" value2="mktOthersTrnsAgree" />
        <input type="hidden" name="mktAgree" id="mktTargetTerms" value=""/>
    </form>
    <div class="c-modal c-modal--bs" id="mkt_bottom_banner" data-dialog-destroy="true" role="dialog" tabindex="2" aria-describedby="#mkt_bottom_banner_desc" aria-labelledby="#mkt_bottom_banner_title">
        <div class="c-modal__dialog main-bs" role="document">
            <div class="c-modal__content u-bg--white">
                <div class="c-modal__header ">
                    <h2 class="c-modal__title" id="mkt_bottom_banner_title">KT M모바일만의 혜택을 놓치지 마세요!</h2>
                </div>
                <div id="mkt_bottom_banner_desc" class="c-modal__body main-bottom-banner">
                    <div class="c-agree u-mt--0 u-bt--0 u-ml--12 u-mr--12 " >
                        <input class="c-checkbox" id="checkAll" type="checkbox">
                        <label class="c-label u-mb--12" for="checkAll">모두 동의하기 (일괄 동의)</label>
                        <div class=" u-bg--gray-1 u-pa--4">
                            <c:set var="termsMemList" value="${nmcp:getCodeList(Constants.TERMS_MEM_CD)}" />
                            <c:forEach var="item" items="${termsMemList}" varStatus="status">
                                <c:if test="${'선택' eq item.expnsnStrVal1}">
                                        <div class="c-agree__item">
                                            <input class="c-checkbox c-checkbox--type2 hidden _agree" id="mktTerms${status.index}" inheritance="MKT${item.expnsnStrVal2}" name="mktTerms" data-dtl-cd="MKT${item.dtlCd}" type="checkbox"  href="javascript:void(0);" onclick="mktSetChkbox(this)">
                                            <label class="c-label" id="mktTerms${status.index}label" for="mktTerms${status.index}">${item.dtlCdNm}<span class="u-co-gray">(${item.expnsnStrVal1})</span></label>
                                            <button class="c-button c-button--xsm" onclick="mktViewTerms('MKT${item.dtlCd}', 'cdGroupId1=${Constants.TERMS_MEM_CD}&cdGroupId2=${item.dtlCd}');">
                                                <span class="c-hidden">${item.dtlCdNm} 상세 팝업보기</span>
                                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                                            </button>
                                        </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="main-bs__footer" style="flex-direction:column">
                    <a class="c-button c-button--full c-button--primary is-disabled" id="mktAgree" href="#n" >동의하기</a>
                    <a class="u-co-gray u-td-underline u-mt--12 fs-12" id="mkt_week_stop" href="#n">일주일 동안 보지 않기 </a>
                </div>
            </div>
        </div>
    </div>
    <form id="appPushForm" name="appPushForm" method="post">
        <input type="hidden" name="sendYn" id="sendYn" />
    </form>
    <div class="c-modal c-modal--bs app-push-modal" id="appPush_bottom_banner" data-dialog-destroy="true" role="dialog" tabindex="2" aria-describedby="#appPush_bottom_banner_desc" aria-labelledby="#appPush_bottom_banner_title">
	    <div class="c-modal__dialog main-bs" role="document">
	        <div class="c-modal__content u-bg--white">
	            <div class="c-modal__header ">
	                <h2 class="c-modal__title" id="appPush_bottom_banner_title"><i class="c-icon c-icon--bell" aria-hidden="true"></i><p>M모바일 <b>혜택 알림</b> 켜보세요!</p></h2>
	            </div>
	            <div id="appPush_bottom_banner_desc" class="c-modal__body main-bottom-banner">
	                <ul class="app-push-list">
	                    <li class="app-push-list__item">
	                        <div class="app-push-list__icon">
	                            <i class="c-icon c-icon--event-news" aria-hidden="true"></i>
	                        </div>
	                        <b>이벤트/혜택</b>
	                        <p>소식 알림</p>
	                    </li>
	                    <li class="app-push-list__item">
	                        <div class="app-push-list__icon">
	                            <i class="c-icon c-icon--coupon-new" aria-hidden="true"></i>
	                        </div>
	                        <b>새로운 M쿠폰</b>
	                        <p>혜택 알림</p>
	                    </li>
	                    <li class="app-push-list__item">
	                        <div class="app-push-list__icon">
	                            <i class="c-icon c-icon--rate-new" aria-hidden="true"></i>
	                        </div>
	                        <b>새로운 요금제</b>
	                        <p>출시 알림</p>
	                    </li>
	                </ul>
	            </div>
	            <div class="c-button-wrap">
	                <button class="c-button c-button--h48 c-button--full c-button--gray" id="appPush_Week_stop">일주일동안 보지 않기 </button>
	                <button class="c-button c-button--h48 c-button--full c-button--primary"  id="appPushAgree" data-dialog-close>광고성 정보(PUSH) 수신동의</button>
	            </div>
	        </div>
	    </div>
	</div>

    <!-- 마케팅 수신 동의 팝업 끝-->
    <!-- 마스킹 해제 후 세션 만료 시  -->
    <div class="c-modal is-active maskingSessionPop" role="dialog" style="display: none;">
	  <div class="c-modal__dialog c-modal__dialog--full" role="document">
	    <div class="c-modal__content masking-session" >
	        <div class="c-modal__body">
	            <p>고객님의 소중한 개인정보 보호를 위해<br /><b class="u-co-red">가려진(*) 정보는 10분간 제공됩니다.</b></p>
	               <p class="u-mt--24">가려진 정보 해제를 원하실 경우<br />본인인증 재시도 부탁드립니다.</p>
	        </div>
	        <div class="c-modal__footer">
	          <div class="c-button-wrap">
	            <a class="c-button c-button--full c-button--gray" href="/m/mypage/myinfoView.do" title="마스킹 해제 페이지 바로가기">마스킹 해제</a>
	            <button onclick="maskingOkBtn()" class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
	          </div>
	        </div>
	    </div>
	    </div>
    </div>
    <div style="position: fixed; width: 100%; height: 100%; left: 0px; top: 0px; background-color: rgba(0, 0, 0, 0.7); z-index: 101; display: none;" class="fadeIn maskingSessionPop"></div>

    <script type="text/javascript" src="//wcs.naver.net/wcslog.js"></script>    <!-- naver DA script  통계 -->
    <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/ktm.ui.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/appCommonNew.js?ver=${jsver}"></script>

    <t:insertAttribute name="scriptHeaderAttr" ignore="true"/>

    <script type="text/javascript" src="/resources/js/nmcpCommon.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/mobile/nmcpCommonComponent.js?ver=${jsver}"></script>

    <script>
        $(document).ready(function(){
            setTimeout(function(){}, 500);

            // popup 목록 조회
            displayPopupList();

            // 앱환경에서만 팝업 표출
            if($("#platFormCd").val() == "A"){
                displayMktPopup();
            }
        });

        let MCP = new NmcpCommonComponent();

        // popup 목록 갯수
        let popupListCnt = 0;

        var mainBannerObject = null;
        document.addEventListener("UILoaded", function() {
            //
        });

        // 팝업 표시
        function displayPopupList() {
            let popupShowUrl = $(location).attr('pathname');

            //파라메터 값 까지 매칭 처리
            if (popupShowUrl.includes("/event/eventDetail.do")) {
                popupShowUrl += $(location).attr('search');
            }

            var varData = ajaxCommon.getSerializedData({
                popupShowUrl : popupShowUrl
            });

            ajaxCommon.getItem({
                id : 'popupListAjax'
                , cache : false
                , async : false
                , url : '/m/getPopupListAjax.do'
                , data : varData
                , dataType : "json"
            }
            ,function(result){
                let popupHtml = "";
                let returnCode = result.returnCode;
                let message = result.returnCode;
                let popupList = result.result;
                popupListCnt = popupList.length;

                if(returnCode == "00") {

                    let onTimeHtml = "";
                    let editorPopupHtml = "";
                    let floatingHtml = "";

                    for(var index=0; index < popupList.length ; index++) {
                        let popupTemp = popupList[index];
                        var me = null;
                        if("" == onTimeHtml && popupTemp.usageType == 'O'){
                            if(popupTemp.contentType == 'EDITOR') {
                                onTimeHtml = popupTemp.popupSbst;
                                $("#oneTimeEditorDiv").html(onTimeHtml);
                                me = document.querySelector('#modalOneTimeEditor');
                            } else {
                                let v_popupSubject = ajaxCommon.isNullNvl(popupTemp.popupSubject, "");
                                let v_filePathNm = ajaxCommon.isNullNvl(popupTemp.filePathNm, "");
                                let v_popupUrl = ajaxCommon.isNullNvl(popupTemp.popupUrl, "");

                                onTimeHtml += "<div class='swiper-slide'>";
                                onTimeHtml += "    <div class='u-img-full'>";
                                onTimeHtml += "        <a href='"+v_popupUrl+"' class='modal-event__anchor'>";
                                onTimeHtml += "            <img src='"+v_filePathNm+"' alt='"+v_popupSubject+"' >";
                                onTimeHtml += "        </a>";
                                onTimeHtml += "    </div>";
                                onTimeHtml += "</div>";
                                $("#oneTimeDiv").html(onTimeHtml);
                                me = document.querySelector('#modalOneTime');
                            }
                            oneTimeBanner(me);

                        } else if ("" == floatingHtml &&  popupTemp.usageType == 'F' ) {
                            floatingHtml = popupTemp.popupSbst;
                            //화면에 표시
                            $("#divFloating").html(floatingHtml);

                        } else if ("" == editorPopupHtml  && popupTemp.contentType == 'EDITOR') {
                            //팝업이 1개이면서 contentType이 'EDITOR'이면 에디터 내용을 사용하는 팝업임
                            editorPopupHtml = popupTemp.popupSbst;

                            //화면에 표시
                            $("#editorDiv").html(editorPopupHtml);
                            //팝업 실행
                            var me = document.querySelector('#modalEditor');
                            var editModal = KTM.Dialog.getInstance(me) ? KTM.Dialog.getInstance(me) : new KTM.Dialog(me, {destroy: true});
                            editModal.open();

                        } else if (popupTemp.usageType != 'O' &&  popupTemp.usageType != 'F' && popupTemp.contentType == 'IMAGE') {
                            let v_popupSubject = ajaxCommon.isNullNvl(popupTemp.popupSubject, "");
                            let v_filePathNm = ajaxCommon.isNullNvl(popupTemp.filePathNm, "");
                            let v_popupUrl = ajaxCommon.isNullNvl(popupTemp.popupUrl, "");

                            popupHtml += "<div class='swiper-slide'>";
                            popupHtml += "    <div class='u-img-full'>";
                            popupHtml += "        <a href='" + v_popupUrl + "' class='u-block'>";
                            popupHtml += "            <img src='" + v_filePathNm + "' alt='" + v_popupSubject + "'>";
                            popupHtml += "        </a>";
                            popupHtml += "    </div>";
                            popupHtml += "</div>";
                            popupListCnt++;

                            if(index == 98) {
                                return false;
                            }
                        }
                    }

                    if (popupHtml != "") {
                        // 화면에 표시
                        $("#popupList").html(popupHtml);
                        // 팝업 실행
                        if(popMaingetCookie($(location).attr('pathname')) != "done") {
                            if(popupListCnt > 0) {
                                mainBottomBanner();
                            }
                        }
                    }


                } else {
                    MCP.alert(message);
                }
            });
        }

        function displayMktPopup(){
            //  마케팅 수신 동의 팝업
            ajaxCommon.getItem({
                    id : 'getMktAgrInfoAjax'
                    , cache : false
                    , async : false
                    , url : '/m/mypage/getMktAgrInfoAjax.do'
                    , dataType : "json"
            }
            ,function(result){
                let returnCode = result.returnCode;
                let mktPersonalInfoCollectAgree = result.mktPersonalInfoCollectAgree;
                let mktClausePriAdFlag = result.mktClausePriAdFlag;
                let mktOthersTrnsAgree = result.mktOthersTrnsAgree;

                if(returnCode == "00") {
                    $("#MKTTERMSMEM04").val(mktPersonalInfoCollectAgree);
                    $("#MKTTERMSMEM03").val(mktClausePriAdFlag);
                    $("#MKTTERMSMEM05").val(mktOthersTrnsAgree);

                    // 마케팅 수신 동의 popup
                    mktInitChecks();
                    mktClausePriAdFlag = ajaxCommon.isNullNvl(mktClausePriAdFlag, "");

                    //  마케팅 수신 동의팝업 노출
                    if (mktClausePriAdFlag != "" &&
                        mktClausePriAdFlag != "Y" &&
                        popMaingetCookie("mktPopup") != "Y") {
                        mktBottomBanner();
                    }
                }
            });

            //  appPush 수신 동의 팝업
            ajaxCommon.getItem({
                    id : 'getSendYnInfoAjax'
                    , cache : false
                    , async : false
                    , url : '/m/app/getSendYnInfoAjax.do'
                    , dataType : "json"
            }
            ,function(result){
                let returnCode = result.returnCode;
                let sendYn = result.sendYn;

                if(returnCode == "00") {
                    $("#sendYn").val(sendYn);
                    // 마케팅 수신 동의 popup
                    mktInitChecks();
                    sendYn = ajaxCommon.isNullNvl(sendYn, "");
                    var mktClausePriAdFlag = ajaxCommon.isNullNvl($("#MKTTERMSMEM03").val());

                    //  마케팅 수신 동의팝업 노출
                    if (mktClausePriAdFlag == "Y" &&
                        sendYn != "" &&
                        sendYn != "Y" &&
                        popMaingetCookie("appPushPopup") != "Y") {
                        appPushBottomBanner();
                    }
                }
            });
        }

         // 메인 하단배너 있을 때 처리
        function mainBottomBanner() {
          // 하단 배너 초기화
          var bottomBannerElement = document.querySelector("#main_bottom_banner");
          var bottomBanner = new KTM.Dialog(bottomBannerElement);
          var swiperTarget = document.querySelector("#main_bottom_banner .swiper-container");
          bottomBanner.open();

          KTM.imageLoadCheck(swiperTarget, function() {
              swiperTarget.classList.remove('loading');
          })
          // 하단 배너 속의 swiper 초기화
          var swiperMainBanner = new Swiper(swiperTarget, {
            observeParents: true,
            observer: true,
            autoHeight: true,
            loop: $("#main_bottom_banner .swiper-slide").length > 1,
            autoplay: {
              delay: 3000,
              disableOnInteraction: false,
            },
            pagination: {
                el: '#main_bottom_banner .swiper-pagination',
                type: "fraction",
                renderFraction: function (currentClass, totalClass) {
                    return '<span class="cur ' + currentClass + '"></span><span class="total ' + totalClass + '"></span>';
                   },
            }
          });

          // 하단 배너 오늘 하루 그만보기 버튼 처리
          var todayStopButton = document.querySelector("#today_stop");
          todayStopButton.addEventListener("click", function click(e) {
            e.preventDefault();
            todayStopButton.removeEventListener("click", click);
            //alert("오늘 하루 그만보기 클릭");
            // 배너속의 swiper 삭제
            swiperMainBanner.destroy();
            // 쿠키생성
            setCookie($(location).attr('pathname'), "done", 1);
            // 배너 닫기
            bottomBanner.close();
          });
        }

         // 쿠키 생성
        function setCookie(name,value,expiredays) {
            var todayDate = new Date();
            todayDate.setDate(todayDate.getDate() + expiredays);
            document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
        }

        // 쿠키 조회
        function popMaingetCookie(name) {
            var nameOfCookie = name + "=";
            var x = 0

            while ( x <= document.cookie.length ) {
                var y = (x+nameOfCookie.length);
                if ( document.cookie.substring( x, y ) == nameOfCookie ) {
                    if ( (endOfCookie=document.cookie.indexOf( ";",y )) == -1 )
                        endOfCookie = document.cookie.length;
                    return unescape( document.cookie.substring(y, endOfCookie ) );
                }
                x = document.cookie.indexOf( " ", x ) + 1;
                if ( x == 0 )
                    break;
            }
            return "";
        }

        function getReplaceUrl(targetUrl){
            if(location.href.indexOf('/m/') > - 1){
                return '/m' + targetUrl;
            }else{
                return targetUrl;
            }
        }

        function getContentAjax(targetId, param) {
            $.ajax({
                url: '/getContentAjax.do',
                type: 'GET',
                dataType: 'json',
                data: param,
                async: false,
                success: function(obj) {
                    if(obj.resultCd == '0000'){
                        var inHtml = unescapeHtml(obj.data.docContent);
                        $('#' + targetId).html(inHtml);
                    }else{
                        $('#' + targetId).html('조회된 데이터가 없습니다.');
                    }
                }
            });
        }

        function oneTimeBanner(me) {
            var oneTimeModal = new KTM.Dialog(me);
            oneTimeModal.open();
            var swiperTarget = document.querySelector("#modalOneTimeTitleDesc .swiper-container");
            swiperTarget.classList.remove('loading');
        }

        function mktBottomBanner() {
            var me = document.querySelector('#mkt_bottom_banner');
            var mktBottomBanner = new KTM.Dialog(me);
            mktBottomBanner.open();

            // 하단 배너 일주일 동안 보지 않기 버튼 처리
            var mktWeekStopButton = document.querySelector("#mkt_week_stop");
            mktWeekStopButton.addEventListener("click", function click(e) {
                e.preventDefault();
                mktWeekStopButton.removeEventListener("click", click);
                // 쿠키생성
                setCookie("mktPopup", "Y", 7);
                // 배너 닫기
                mktBottomBanner.close();
            });

            // 하단 배너 동의하기 버튼 처리
            var mktAgree = document.querySelector("#mktAgree");
            mktAgree.addEventListener("click", function click(e) {
                if(!$("#mktPersonalInfoCollectAgree").is(':checked')) {
                    MCP.alert('개인정보 수집 및 이용 동의를 해주세요.', function (){
                        $('#mktPersonalInfoCollectAgree').focus();
                    });
                    return;
                }
                if(!$("#mktClausePriAdFlag").is(':checked')) {
                    MCP.alert('정보/광고 수신 동의를 해주세요.', function (){
                        $('#mktClausePriAdFlag').focus();
                    });
                    return;
                }

                e.preventDefault();
                mktAgree.removeEventListener("click", click);
                mktAgreeUpdate();
                // 쿠키생성
                setCookie("mktPopup", "Y", 1);
                mktBottomBanner.close();
            });
        }

        function mktAgreeUpdate(){
            var varData = ajaxCommon.getSerializedData({
                personalInfoCollectAgree:$("#mktPersonalInfoCollectAgree").is(':checked') ? 'Y' : 'N'
                ,clausePriAdFlag:$("#mktClausePriAdFlag").is(':checked') ? 'Y' : 'N'
                ,othersTrnsAgree:$("#mktOthersTrnsAgree").is(':checked') ? 'Y' : 'N'
                ,mktAgreeUpdate : 'Y'
            });
            ajaxCommon.getItem({
                id:'userUpdateAjax'
                ,cache:false
                ,url:'/m/mypage/userUpdateAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(data){
                var todayDate = new Date().format("yyyy-MM-dd");

                var msg = "";
                if(data.personalInfoCollectAgree == "Y"){
                    msg = "[개인정보 수집 및 이용동의] </br>동의일자 (" + todayDate + ")</br>"  ;
                }
                if(data.clausePriAdFlag == "Y"){
                    msg += "</br>[정보/광고 수신동의] </br>동의일자 (" + todayDate + ")</br>";
                }
                if(data.othersTrnsAgree == "Y"){
                    msg += "</br>[제 3자 제공 동의 수신] </br>동의일자 (" + todayDate + ")</br>" ;
                }

                if(data.RESULT_CODE == "00000"){
                    MCP.alert(msg, function () {
                        let sendYn = ajaxCommon.isNullNvl($("#sendYn").val(), "");

                        if(data.clausePriAdFlag == "Y" &&
                            sendYn != "" &&
                            sendYn != "Y") {
                            appPushBottomBanner();
                        }
                    });
                } else {
                    MCP.alert(data.RESULT_MSG);
                }
            });
        }

        function appPushBottomBanner() {
            var me = document.querySelector('#appPush_bottom_banner');
            var appPushBottomBanner = new KTM.Dialog(me);
            appPushBottomBanner.open();

            // 하단 배너 일주일 동안 보지 않기 버튼 처리
            var appPushWeekStopButton = document.querySelector("#appPush_Week_stop");
            appPushWeekStopButton.addEventListener("click", function click(e) {
                e.preventDefault();
                appPushWeekStopButton.removeEventListener("click", click);
                // 쿠키생성
                setCookie("appPushPopup", "Y", 7);
                // 배너 닫기
                appPushBottomBanner.close();
            });

            // 하단 배너 동의하기 버튼 처리
            var appPushAgree = document.querySelector("#appPushAgree");
            appPushAgree.addEventListener("click", function click(e) {
                e.preventDefault();
                appPushAgree.removeEventListener("click", click);
                appPushAgreeUpdate();
            });
        }

        function appPushAgreeUpdate(){
            var varData = ajaxCommon.getSerializedData({
                sendYn : 'Y'
                ,sendYnReferer: 'appPushAgrPop'
            });
            ajaxCommon.getItem({
                    id:'userUpdateAjax'
                    ,cache:false
                    ,url:'/m/set/updateAppSetAjax.do'
                    ,data: varData
                    ,dataType:"json"
            }
            ,function(data){
                    var todayDate = new Date().format("yyyy-MM-dd");
                    var msg = "광고성 정보(PUSH)에 수신동의 하셨습니다. </br> " +
                              "동의일자 (" + todayDate + ")";

                    if(data.CODE == "0000"){
                        MCP.alert(msg);
                    } else {
                        MCP.alert(data.ERRORDESC);
                    }
            });
        }

        var mktParentCheckId = [];
        $('#checkAll').on('click', function (){
            if($(this).prop('checked')){
                $('input[name=mktTerms]').prop('checked', 'checked');
                $('input[name=mktTerms]').addClass('btn_agree_active');
                $('input[name=mktTerms]').removeClass('btn_agree');
                $('#mktAgree').removeClass('is-disabled');
            }else{
                $('input[name=mktTerms]').removeProp('checked');
                $('input[name=mktTerms]').addClass('btn_agree');
                $('input[name=mktTerms]').removeClass('btn_agree_active');
                $('#mktAgree').addClass('is-disabled');
            }
        });
        function mktViewTerms(target, param) {
            $('#mktTargetTerms').val(target);
            openPage('mktTermsPop','/termsPop.do',param);
        }
        function mktSetChkbox(data) {
            var cnt = 0;

            if (data != undefined ) {
                var checkPossible = document.querySelector('#' + data.id).checked;
                if (mktParentCheckId.indexOf($(data).attr('data-dtl-cd')) != -1 && !checkPossible) {
                    document.getElementsByName('mktTerms').forEach(function (params) {
                        if (mktParentCheckId.indexOf($(params).attr('inheritance')) != -1) {
                            params.checked = checkPossible;
                        }
                    });
                    data.checked = checkPossible;

                } else if (mktParentCheckId.indexOf($(data).attr('inheritance')) != -1 && checkPossible) {
                    document.getElementsByName('mktTerms').forEach(function(params) {
                        if ($(data).attr('inheritance') == $(params).attr('data-dtl-cd')) {
                            if (!$(params).is(':checked')) {
                                MCP.alert("고객 혜택 제공을 위한 개인정보 수집 및 이동 동의가 필요합니다.");
                                data.checked = false;
                            }
                        }
                    });
                }
                if (checkPossible == false) {
                    $('#checkAll').prop('checked', false);
                }
            }
            if($("#mktPersonalInfoCollectAgree").is(':checked') && $("#mktClausePriAdFlag").is(':checked')) {
                $('#mktAgree').removeClass('is-disabled');
            }else{
                $('#mktAgree').addClass('is-disabled');
            }
            var mktTerms = document.getElementsByName('mktTerms');
            if (mktTerms.length == $('input[name=mktTerms]:checked').length) {
                $('#checkAll').prop('checked', true)
            }

        }
        var mktInitChecks = function () {
            document.getElementsByName('mktAgree').forEach(function (agreeData) {
                document.getElementsByName('mktTerms').forEach(function(params) {
                    if (agreeData.id == $(params).attr('data-dtl-cd')) {
                        params.checked = 'Y' == agreeData.value ? true : false;
                        $('#'+params.id+'label').attr('for', $(agreeData).attr('value2'));
                        $(params).attr('id', $(agreeData).attr('value2'));

                        if ( $(params).attr('inheritance') != '') {
                            if (mktParentCheckId.indexOf($(params).attr('inheritance')) == -1) {
                                mktParentCheckId.push($(params).attr('inheritance'));
                            }
                        }
                    }
                })
            });

            if($("#mktPersonalInfoCollectAgree").is(':checked') && $("#mktClausePriAdFlag").is(':checked')) {
                $('#mktAgree').removeClass('is-disabled');
            }else{
                $('#mktAgree').addClass('is-disabled');
            }
            var mktTerms = document.getElementsByName('mktTerms');
            if (mktTerms.length == $('input[name=mktTerms]:checked').length) {
                $('#checkAll').prop('checked', true)
            }
        }

        var mktTargetTermsAgree = function () {
            var id = '';
            var mktTerms = document.getElementsByName('mktTerms');
            mktTerms.forEach(function (param) {
                if ($(param).attr('data-dtl-cd') == $('#mktTargetTerms').val()) {
                    id = param.id;
                }
            })
            if ($('#'+id).attr('inheritance') != 'MKT') {
                mktTerms.forEach(function (param) {
                    if ($(param).attr('data-dtl-cd') == $('#'+id).attr('inheritance')) {
                        if ( !$('#'+param.id).is(':checked')) {
                            MCP.alert("고객 혜택 제공을 위한 개인정보 수집 및 이동 동의가 필요합니다.");
                            param.checked = false;
                        } else {
                            $('#'+id).prop('checked', true);
                        }
                    }
                });
            } else {
                $('#'+id).prop('checked', true);
            }

            if($("#mktPersonalInfoCollectAgree").is(':checked') && $("#mktClausePriAdFlag").is(':checked')) {
                $('#mktAgree').removeClass('is-disabled');
            }else{
                $('#mktAgree').addClass('is-disabled');
            }
            if (mktTerms.length == $('input[name=mktTerms]:checked').length) {
                $('#checkAll').prop('checked', true)
            }

        }
    </script>
    <script>
        $( document ).ready( function() {

            $(".c-fab__delete").click(function(){
                $(".c-fab").addClass("is-delet");
            });

        } );
    </script>
<c:if test = "${serverNm eq 'REAL' && !isIgnoredScript }">
    <%@ include file="/WEB-INF/views/layouts/exAdAnalyticsScript.jsp" %>
    <%@ include file="/WEB-INF/views/layouts/analyticsscript.jsp" %>
</c:if>


<t:insertAttribute name="bottomScript" ignore="true"/>


</body>

</html>
