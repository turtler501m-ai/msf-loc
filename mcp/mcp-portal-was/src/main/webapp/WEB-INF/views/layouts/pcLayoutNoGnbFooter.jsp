<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="serverNm" value="${nmcp:getPropertiesVal('SERVER_NAME')}" />
<c:set var="titleNm" value="${nmcp:getCurrentMenuName()}" />
<c:set var="menuInfo" value="${nmcp:getCurrentMenuInfo()}" />
<c:set var="jsver" value="${nmcp:getJsver()}" />
<c:set var="isIgnoredScript" value="${false}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <meta property="og:image" content="https://${header['host']}/ktMmobile_og.png" />
    <link rel="stylesheet" type="text/css" href="/resources/css/portal/styles.css" />

    <title><t:insertAttribute name="titleAttr" ignore="true" defaultValue="${titleNm}"/> | kt M모바일 공식 다이렉트몰</title>
    <link rel="icon" href="/favorites_icon03.png" type="image/x-icon">


</head>
<body>
<c:if test = "${serverNm ne 'REAL'}">

<h1 style="background-color: red;position: fixed;left: 0;top: 0;width: 100%;z-index: 99;font-size: 11px;">=== ${serverNm } / ${menuInfo.floatingShowYn},${menuInfo.floatingTipSbst},${menuInfo.chatbotTipSbst}, testLogin=${nmcp:getTestLoginSession()},testLoginId=${nmcp:getTestLoginIdSession()} === </h1>
</c:if>

    <c:set var="AILandingUrl" value="${nmcp:getCodeNmDto('AISetting', 'landing')}" />
    <input type="hidden" id="AILandingUrl" value="${AILandingUrl.expnsnStrVal1}">

    <div class="ly-wrap">
        <div id="skipNav">
          <a class="skip-link" href="#main-content">본문 바로가기</a>
        </div>

        <t:insertAttribute name="contentAttr"/>

        <t:insertAttribute name="mncpFooterAttr" ignore="true"/>

    </div>

    <div class="c-modal c-modal--xlg" id="modalArs" role="dialog" tabindex="-1" aria-describedby="#modalArsTitle">
        <div class="c-modal__dialog" role="document" id="pullModalContents"></div>
    </div>

    <div class="c-modal c-modal--xlg" id="modalArs2nd" role="dialog" tabindex="-1" aria-describedby="#modalArs2ndTitle">
        <div class="c-modal__dialog c-modal__dialog--full" role="document" id="pullModal2ndContents">
        </div>
    </div>

    <script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/resources/js/portal/ktm.ui.js?ver=${jsver}"></script>

    <t:insertAttribute name="scriptHeaderAttr" ignore="true"/>

    <script type="text/javascript" src="/resources/js/nmcpCommon.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/portal/nmcpCommonComponent.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/jqueryCommon.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/common.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/portal/vendor/swiper.min.js"></script>
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/chatbot.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/portal/popup/aiRecommendPop.js?ver=${jsver}"></script>

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


    <div class="c-modal c-modal--md" id="modalTerms" role="dialog" tabindex="-1" aria-describedby="#modalTermsTitle">
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
    <div class="c-modal c-modal--xlg" id="modalInfoTerms" role="dialog" tabindex="-1" aria-describedby="#modalTermsTitle">
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
     <div class="c-modal c-modal--md" id="modalMain" role="dialog" aria-labelledby="#modalMainTitle">
        <div class="c-modal__dialog" role="document">
              <div class="c-modal__content modal-event">
                <div class="c-modal__body">
                      <h2 class="c-hidden" id="modalMainTitle">이벤트 목록</h2>
                      <div class="swiper-container">
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
                      <button href="javascript:void(0);" onclick="todayStop();" class="c-button" data-dialog-close>오늘 하루 그만보기</button>
                      <button class="c-button" data-dialog-close>닫기</button>
                </div>
              </div>
        </div>
      </div>
      <!-- [END]  팝업 -->

      <script>
         // popup 목록 갯수
        let popupListCnt = 0;
        // popup 목록 조회
        //displayPopupList();

        document.addEventListener('UILoaded', function() {
            // 메인 바텀배너 오픈
            if(popMaingetCookie($(location).attr('pathname')) != "done") {
                if(popupListCnt > 0) {
                    mainBottomBanner();
                }
            }
        });

         // 팝업 표시
        function displayPopupList() {
            var varData = ajaxCommon.getSerializedData({
                 popupShowUrl : $(location).attr('pathname')
            });

            ajaxCommon.getItem({
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
                popupListCnt = popupList.length;

                if(returnCode == "00") {
                    if(popupList.length > 0) {
                        $.each(popupList, function(index, value) {
                            let v_popupSubject = ajaxCommon.isNullNvl(value.popupSubject, "");
                            let v_filePathNm = ajaxCommon.isNullNvl(value.filePathNm, "");
                            let v_popupUrl = ajaxCommon.isNullNvl(value.popupUrl, "");

                            popupHtml += "<div class='swiper-slide'>";
                            popupHtml += "    <a href='"+v_popupUrl+"' class='modal-event__anchor'>";
                            popupHtml += "        <div class='modal-event__image'>";
                            popupHtml += "  	      <img src='"+v_filePathNm+"' alt='"+v_popupSubject+"'>";
                            popupHtml += "  	  </div>";
                            popupHtml += "    </a>";
                              popupHtml += "</div>";

                            if(index == 98) {
                                return false;
                            }
                        });
                        // 화면에 표시
                        $("#popupList").html(popupHtml);
                    }
                } else {
                    MCP.alert(message);
                }
            });
        }

         // 메인 하단배너 있을 때 처리
        function mainBottomBanner() {
            var el = document.querySelector('#modalMain');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);

            modal.open();
            document.body.style.overflowY = 'scroll';
            el.addEventListener(KTM.Dialog.EVENT.OPENED, function(event) {


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

                  modalEventSwiper = KTM.swiperA11y('#modalMain .swiper-container',modalPagination);

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

<c:if test = "${serverNm eq 'REAL' && !isIgnoredScript }">
    <%@ include file="/WEB-INF/views/layouts/analyticsscript.jsp" %>
</c:if>
</body>
</html>
