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

    <c:if test="${exceptjsp != null}">
        <input type="hidden" id="execptjsp" value="${exceptjsp.message}">
    </c:if>
</c:catch>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <t:insertAttribute name="metaTagAttr" ignore="true" defaultValue="/WEB-INF/views/layouts/metaTagDeafault.jsp"/>

	<link rel="stylesheet" href="/resources/css/aos/aos.css"  />
    <link rel="stylesheet" type="text/css" href="/resources/css/portal/styles.css?version=${Constants.CSS_VERSION}" />
    <title>
        <c:if test="${titleNm eq '메인'}"><t:insertAttribute name="titleAttr" ignore="true"/>압도적 1등 알뜰폰 | kt M모바일 공식 다이렉트몰</c:if>
        <c:if test="${titleNm ne '메인'}"><t:insertAttribute name="titleAttr" ignore="true" defaultValue="${titleNm} "/> | kt M모바일 공식 다이렉트몰</c:if>
    </title>

    <c:if test = "${serverNm eq 'REAL' && !isIgnoredScript }">
        <%@ include file="/WEB-INF/views/layouts/googleTagScript.jsp" %>
        <t:insertAttribute name="googleTagScript" ignore="true"/>
    </c:if>


    <link rel="icon" href="/favorites_icon03.png" type="image/x-icon">


</head>
<body>

<div id="skipNav">
  <a class="skip-link" href="#main-content">본문 바로가기</a>
</div>

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
<input type="hidden" id="isIgnoredScript" name="isIgnoredScript" value="${isIgnoredScript}" />

<h1 style="background-color: red;position: fixed;left: 0;top: 0;width: 100%;z-index: 99;font-size: 11px;">=== ${serverNm } / ${menuInfo.floatingShowYn},${menuInfo.floatingTipSbst},${menuInfo.chatbotTipSbst}, testLogin=${nmcp:getTestLoginSession()},testLoginId=${nmcp:getTestLoginIdSession()} === [[[${titleNm}]]]</h1>


</c:if>

<c:catch var="exceptjsp">
    <input type="hidden" id="gnbMCode" value="${nmcp:getGnbMenuCode()}" />

    <c:if test="${exceptjsp != null}">
        <input type="hidden" id="execptjsp2" value="${exceptjsp.message}">
    </c:if>
</c:catch>
    <input type="hidden" id="maskingSession" name="maskingSession" value="${maskingSession}">

    <c:set var="AILandingUrl" value="${nmcp:getCodeNmDto('AISetting', 'landing')}" />
    <input type="hidden" id="AILandingUrl" value="${AILandingUrl.expnsnStrVal1}">

    <div class="ly-wrap">

        <t:insertAttribute name="mainTopBannerAttr" ignore="true"/>

        <t:insertAttribute name="mncpGnbAttr" ignore="true"/>

        <t:insertAttribute name="contentAttr" ignore="true"/>


        <!--  플로팅 팝업  -->
        <div id="divFloating" class="floating-event-banner">
        </div>
        <script>
            var fnSetEventListenerFloating = function() {
                let lastScrollY = window.scrollY;
                const target = document.querySelector('.floating-event-banner');

                window.addEventListener('scroll', () => {
                    const currentScrollY = window.scrollY;
                    if (currentScrollY < lastScrollY) {
                        target.classList.remove('hidden');
                    } else if (currentScrollY > lastScrollY) {
                        target.classList.add('hidden');
                    }

                    lastScrollY = currentScrollY;
                });


                const bannerWrap = document.querySelector('.floating-event-banner-wrap');
                if (!bannerWrap) {
                    console.warn('찾지 못함: .floating-event-banner-wrap');
                } else {
                    const apply = () => {
                        const y = window.scrollX || window.pageXOffset || 0;
                        // 성능/호환: translate3d 사용
                        bannerWrap.style.transform = "translate3d(-"+y+"px, 0, 0)";
                    };
                    window.addEventListener('scroll', apply, { passive: true });
                    window.addEventListener('resize', apply);
                    apply(); // 초기 1회 적용
                }
            }


        </script>
        <!--  플로팅 팝업 끝 -->

<c:if test="${not empty menuInfo }">
<c:if test="${not empty menuInfoList }">

    <c:if test = "${not empty menuInfo.floatingShowYn}">
      <c:if test = "${menuInfo.floatingShowYn eq 'Y'}">
        <c:set var="floatingTipSbst" value="" />
        <c:if test = "${not empty menuInfo.floatingTipSbst}">
            <c:set var="floatingTipSbst" value="${menuInfo.floatingTipSbst}" />
        </c:if>
        <c:set var="chatbotTipSbst" value="무엇이든 물어보세요." />
        <c:if test = "${not empty menuInfo.chatbotTipSbst}">
            <c:set var="chatbotTipSbst" value="${menuInfo.chatbotTipSbst}" />
        </c:if>

        <nav class="c-fab is-active">
          <c:if test="${not empty menuInfo.aiRecoShowYn and menuInfo.aiRecoShowYn eq 'Y'}">
            <button type="button" class="c-fab__ai" onclick="aiRecommendPopOpen();">
              <i class="c-icon c-icon--ai-recomd" aria-hidden="true"></i>
              <span class="sr-only">AI 추천 새창 열기</span>
	          </button>
          </c:if>

          <button class="c-fab__open" type="button">
            <span class="sr-only">help 열기</span>
            <span class="c-fab__open--txt">${floatingTipSbst }</span>
          </button>
          <button class="c-fab__close" type="button">
            <span class="sr-only">help 닫기</span>
            <span class="lines line-1"></span>
            <span class="lines line-2"></span>
            <span class="lines line-3"> </span>
          </button>


          <c:forEach items="${menuInfoList}" var="infoList" varStatus="vsdepth">
              <c:if test = "${infoList.urlAtribValCd eq 'FLOATFAQ' && infoList.atribUseYn eq 'Y'}">
                <a class="c-fab__item faq" href="/cs/faqList.do">
                  <img src="/resources/images/portal/common/ico_faq.svg" alt="faq로 이동">
                </a>
              </c:if>
          </c:forEach>

          <c:forEach items="${menuInfoList}" var="infoList" varStatus="vsdepth">
              <c:if test = "${infoList.urlAtribValCd eq 'FLOATCONSL' && infoList.atribUseYn eq 'Y'}">
                <a class="c-fab__item help" href="/cs/csInquiryInt.do">
                  <img src="/resources/images/portal/common/ico_onevsone.svg" alt="1:1 문의로 이동">
                </a>
              </c:if>
          </c:forEach>

          <c:forEach items="${menuInfoList}" var="infoList" varStatus="vsdepth">
              <c:if test = "${infoList.urlAtribValCd eq 'FLOATCHAT' && infoList.atribUseYn eq 'Y'}">
                <a class="c-fab__item chat" href="javascript:chatbotTalkOpen();">
                  <img src="/resources/images/public/icon/ico_chat.svg" alt="챗봇 새창열림">
                </a>
              </c:if>
          </c:forEach>

          <button type="button" class="c-fab__delete">
            <div class="c-hidden">help 삭제</div>
            <i class="c-icon c-icon--close-black" aria-hidden="true"></i>
          </button>
        </nav>
      </c:if>
    </c:if>

    <%--AI 추천 플로팅만 노출--%>
    <c:if test="${empty menuInfo.floatingShowYn or menuInfo.floatingShowYn ne 'Y'}">
      <c:if test="${not empty menuInfo.aiRecoShowYn and menuInfo.aiRecoShowYn eq 'Y'}">
        <nav class="c-fab is-active">
          <button type="button" class="c-fab__ai u-po-abs" onclick="aiRecommendPopOpen();">
            <i class="c-icon c-icon--ai-recomd" aria-hidden="true"></i>
            <span class="sr-only">AI 추천 새창 열기</span>
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
		<nav class="c-top is-active">
          <a href="#skipNav" title="페이지 상단으로 이동하기">
            <i class="c-icon c-icon--arrow-type2" aria-hidden="true"></i>
          </a>
        </nav>
        <t:insertAttribute name="mncpFooterAttr" ignore="true"/>
    </div>

    <div class="c-modal c-modal--xlg" id="modalArs" role="dialog" tabindex="-1" aria-describedby="#modalArsTitle">
        <div class="c-modal__dialog" role="document" id="pullModalContents"></div>
    </div>

    <div class="c-modal c-modal--xlg" id="modalArs2nd" role="dialog" tabindex="-1" aria-describedby="#modalArs2ndTitle">
        <div class="c-modal__dialog c-modal__dialog--full" role="document" id="pullModal2ndContents">
        </div>
    </div>

    <!-- 마스킹 해제 후 세션 만료 시  -->
    <div class="c-modal c-modal--xlg maskingSessionPop" role="dialog" aria-describedby="modal_alert_title" style="display: none;">
		<div class="c-modal__dialog" role="document">
	     	<div class="c-modal__content masking-session">
		        <div class="c-modal__body">
	                <p class="u-fs-20">고객님의 소중한 개인정보 보호를 위해<br /><b class="u-co-red">가려진(*) 정보는 10분간 제공됩니다.</b></p>
	                <p class="u-mt--24">가려진 정보 해제를 원하실 경우 본인인증 재시도 부탁드립니다.</p>
		        </div>
		        <div class="c-modal__footer">
		           <div class="c-button-wrap">
		             <a class="c-button c-button--lg c-button--gray u-width--220" href="/mypage/myinfoView.do" title="마스킹 해제 페이지 바로가기">마스킹 해제 바로가기</a>
		             <button onclick="maskingOkBtn()" class="c-button c-button--lg c-button--primary u-width--220" data-dialog-close>확인</button>
		           </div>
		        </div>
	     	</div>
    	</div>
    </div>
    <div style="position: fixed; width: 100%; height: 100%; left: 0px; top: 0px; background-color: rgba(0, 0, 0, 0.6); z-index: 101; display: none;" class="fadeIn maskingSessionPop"></div>

    <script type="text/javascript" src="//wcs.naver.net/wcslog.js"></script>    <!-- naver DA script  통계 -->
    <script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/resources/js/portal/ktm.ui.js?ver=${jsver}"></script>


    <t:insertAttribute name="scriptHeaderAttr" ignore="true"/>

    <script type="text/javascript" src="/resources/js/nmcpCommon.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/portal/nmcpCommonComponent.js"></script>
    <script type="text/javascript" src="/resources/js/jqueryCommon.js?ver=2025-11-12"></script>
    <script type="text/javascript" src="/resources/js/common.js"></script>
    <script type="text/javascript" src="/resources/js/portal/vendor/swiper.min.js"></script>
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/chatbot.js"></script>
    <script type="text/javascript" src="/resources/js/portal/popup/maskingPop.js"></script>
    <script type="text/javascript" src="/resources/js/portal/popup/aiRecommendPop.js"></script>
    <script type="text/javascript" src="/resources/js/aos.js"></script>

<script>
        let MCP = new NmcpCommonComponent();

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
    </script>

    <t:insertAttribute name="popLayerAttr" ignore="true"/>


    <div class="c-modal c-modal--md" id="modalTerms" role="dialog" tabindex="-1" aria-describedby="modalTermsTitle">
        <div class="c-modal__dialog" role="document">
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
    <div class="c-modal c-modal--xlg" id="modalInfoTerms" role="dialog" tabindex="-1" aria-describedby="modalTermsTitle">
        <div class="c-modal__dialog" role="document">
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

     <!-- [START]  팝업 -->
     <div class="c-modal c-modal--md" id="modalMain" role="dialog" aria-labelledby="modalMainTitle">
        <div class="c-modal__dialog" role="document">
              <div class="c-modal__content modal-event">
                <div class="c-modal__body">
                      <h2 class="c-hidden" id="modalMainTitle">이벤트 목록</h2>
                      <div class="swiper-container loading">
                        <div id="popupList" class="swiper-wrapper">
                        </div>
                      </div>
                      <button class="modal-event__button modal-event__button--prev" type="button">
                        <i class="c-icon c-icon--prev" aria-hidden="true"></i>
                        <span class="c-hidden">이전</span>
                      </button>
                      <button class="modal-event__button modal-event__button--next" type="button">
                        <i class="c-icon c-icon--next" aria-hidden="true"></i>
                        <span class="c-hidden">다음</span>
                      </button>
                      <div class="modal-event__control">
                        <button class="modal-event__button modal-event__button--play is-disabled" type="button">
                              <i class="c-icon c-icon--play" aria-hidden="true"></i>
                              <span class="c-hidden">자동재생 시작</span>
                        </button>
                        <button class="modal-event__button modal-event__button--pause" type="button">
                              <i class="c-icon c-icon--stop" aria-hidden="true"></i>
                              <span class="c-hidden">자동재생 정지</span>
                        </button>
                        <div class="modal-event__pagination"></div>
                      </div>
                </div>
                <div class="c-modal__footer">
                      <button onclick="todayStop();" class="c-button" data-dialog-close>오늘 하루 그만보기</button>
                      <button class="c-button" data-dialog-close>닫기</button>
                </div>
              </div>
        </div>
      </div>
      <!-- [END]  팝업 -->

    <!-- 에디터 팝업 -->
    <div class="c-modal c-modal--editor" id="modalEditor" role="dialog" aria-labelledby="modalEditorTitle">
        <div class="c-modal__dialog" role="document">
            <div class="main-bs__footer">
                <button class="u-block" data-dialog-close>
                    <i class="c-icon c-icon--close-ty2" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                </button>
            </div>
            <div class="c-modal__content u-bg--transparent">
                <div class="c-modal__header c-hidden">
                    <h2 class="c-modal__title" id="modalEditorTitle">이벤트 배너 모아보기</h2>
                </div>
                <div class="c-modal__body" id="editorDiv">
                    <!-- 에디터 영역 -->
                </div>
            </div>
        </div>
    </div>
    <!-- 에디터 팝업 끝 -->

    <!-- [START]  일회성 팝업 -->
    <div class="c-modal c-modal--md" id="modalOneTime" role="dialog" aria-labelledby="modalOneTimeTitle">
        <div class="c-modal__dialog" role="document">
            <div class="c-modal__content modal-event">
                <div class="c-modal__body">
                    <h2 class="c-hidden" id="modalOneTimeTitle">이벤트 목록</h2>
                    <div class="swiper-container ">
                        <div id="oneTimeDiv" class="swiper-wrapper">
                        </div>
                    </div>
                </div>
                <div class="c-modal__footer">
                    <button class="c-button c-button--full" data-dialog-close>닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 일회성 팝업 끝 -->

    <!-- 일회성 에디터 팝업 -->
    <div class="c-modal c-modal--editor" id="modalOneTimeEditor" role="dialog" aria-labelledby="modalOneTimeEditorTitle">
        <div class="c-modal__dialog" role="document">
            <div class="main-bs__footer">
                <button class="u-block" data-dialog-close>
                    <i class="c-icon c-icon--close-ty2" aria-hidden="true"></i>
                    <span class="c-hidden">팝업닫기</span>
                </button>
            </div>
            <div class="c-modal__content u-bg--transparent">
                <div class="c-modal__header c-hidden">
                    <h2 class="c-modal__title" id="modalOneTimeEditorTitle">이벤트 배너 모아보기</h2>
                </div>
                <div class="c-modal__body" id="oneTimeEditorDiv">
                    <!-- 일회성 에디터 영역 -->
                </div>
            </div>
        </div>
    </div>
    <!-- 일회성 에디터 팝업 끝 -->
<script>
        $(document).ready(function(){
            setTimeout(function(){}, 500);

            // popup 목록 조회
            displayPopupList();

//            $('#subbody_loading').hide();
        });

         // popup 목록 갯수
        let popupListCnt = 0;

        document.addEventListener('UILoaded', function() {
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

            ajaxCommon.getItemNoLoading({
                id : 'popupListAjax'
                , cache : false
                , async : false
                , url : '/getPopupListAjax.do'
                , data : varData
                , dataType : "json"
            }
            ,function(result){
                let popupHtml = "";
                let returnCode = result.returnCode;
                let message = result.returnCode;
                let popupList = result.result;

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
                                onTimeHtml += "    <a href='"+v_popupUrl+"' class='modal-event__anchor'>";
                                onTimeHtml += "        <div class='modal-event__image'>";
                                onTimeHtml += "            <img src='"+v_filePathNm+"' alt='"+v_popupSubject+"'>";
                                onTimeHtml += "        </div>";
                                onTimeHtml += "    </a>";
                                onTimeHtml += "</div>";
                                $("#oneTimeDiv").html(onTimeHtml);
                                me = document.querySelector('#modalOneTime');
                            }
                            var oneTimeModal = KTM.Dialog.getInstance(me) ? KTM.Dialog.getInstance(me) : new KTM.Dialog(me, {destroy: true});
                            oneTimeModal.open();
                        } else if ("" == floatingHtml &&  popupTemp.usageType == 'F' ) {
                            floatingHtml = popupTemp.popupSbst;
                            //화면에 표시
                            $("#divFloating").html(floatingHtml);

                            //이벤트 등록
                            fnSetEventListenerFloating();

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
                            popupHtml += "    <a href='"+v_popupUrl+"' class='modal-event__anchor'>";
                            popupHtml += "        <div class='modal-event__image'>";
                            popupHtml += "            <img src='"+v_filePathNm+"' alt='"+v_popupSubject+"'>";
                            popupHtml += "        </div>";
                            popupHtml += "    </a>";
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
        // 팝업 슬라이드 tabindex 삭제
        setTimeout(function(){
          $('.modal-event .swiper-slide').removeAttr('tabindex');
        },1000);

         // 메인 하단배너 있을 때 처리
        function mainBottomBanner() {
            var el = document.querySelector('#modalMain');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el, {destroy: true});
            var container = document.querySelector('#modalMain .swiper-container');
            var imgLoadCheck = function (callback) {
                var imgs = container.querySelectorAll('img');
                if (imgs.length === 0) {
                  callback();
                  return;
                }
                var check = setInterval(function () {
                  var loadedCount = 0;
                  [].forEach.call(imgs, function (img) {
                      //console.log(img, img.complete)
                    if (img.complete) {
                      loadedCount++;
                      if (loadedCount >= imgs.length) {
                        clearInterval(check);
                        callback();
                      }
                    }
                  });
                }, 10);
              };

            modal.open();
            document.body.style.overflowY = 'scroll';
            el.addEventListener(KTM.Dialog.EVENT.CLOSED, function close(){
                setTimeout(function(){
                     var resizeEvent = window.document.createEvent('UIEvents');
                     resizeEvent.initUIEvent('resize', true, false, window, 0);
                     window.dispatchEvent(resizeEvent);
                }, 450);
            });
            el.addEventListener(KTM.Dialog.EVENT.OPENED, function(event) {
                imgLoadCheck(function () {
                    container.classList.remove('loading');
                });

                var modalPagination = {
                    effect: 'fade',
                    autoplay: {
                        delay: 4000,
                        disableOnInteraction: false,
                    },
                    navigation: {
                        nextEl: '.modal-event__button--next',
                        prevEl: '.modal-event__button--prev',
                    },
                    pagination: {
                        el: '.modal-event__pagination',
                        type: 'fraction',
                        formatFractionCurrent: function(number) {
                            return ('0' + number).slice(-2);
                        },
                        formatFractionTotal: function(number) {
                            return ('0' + number).slice(-2);
                        },
                    },
                }

                var swiperSlide = $('#modalMain .swiper-slide');

                if (swiperSlide.length > 1) {
                    modalPagination.loop = true;
                }

                modalEventSwiper = KTM.swiperA11y(container,modalPagination);

                var btnPlay = el.querySelector('.modal-event__button--play');
                var btnStop = el.querySelector('.modal-event__button--pause');

                btnStop.addEventListener('click', function() {
                   modalEventSwiper.autoplay.stop();
                   btnPlay.classList.remove('is-disabled');
                   btnStop.classList.add('is-disabled');
                });

                btnPlay.addEventListener('click', function() {
                   modalEventSwiper.autoplay.start();
                   btnStop.classList.remove('is-disabled');
                   btnPlay.classList.add('is-disabled');
                });
            });
            el.addEventListener(KTM.Dialog.EVENT.CLOSE, function(event) {
                if(modalEventSwiper) {
                    modalEventSwiper.destroy();
                    modalEventSwiper = null;
                }
            })
         }

        function todayStop() {
            // 쿠키생성
            setCookie($(location).attr('pathname'), "done", 1);
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
    </script>
    <script>
        $( document ).ready( function() {

            $(".c-fab__open--txt:parent").css("display", "block");

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
