<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="serverNm" value="${nmcp:getPropertiesVal('SERVER_NAME')}" />
<c:set var="titleNm" value="${nmcp:getCurrentMenuName()}" />
<c:set var="menuInfo" value="${nmcp:getCurrentMenuInfo()}" />
<c:set var="jsver" value="${nmcp:getJsver()}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes,maximum-scale=5.0, minimum-scale=1.0, viewport-fit=cover" >
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="Expires" content="-1">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="No-Cache">
    <meta property="og:image" content="https://${header['host']}/ktMmobile_og.png" />
    <link href="/resources/css/mobile/styles.css" rel="stylesheet" />

    <title><t:insertAttribute name="titleAttr" ignore="true" defaultValue="${titleNm }"/> | kt M모바일 공식 다이렉트몰 </title>
    <link rel="icon" href="/favorites_icon03.png" type="image/x-icon">

    <script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery-migrate-1.3.0.js"></script>
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/jqueryCommon.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/mobile/common.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/mobile/chatbot.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/mobile/popup/aiRecommendPop.js?ver=${jsver}"></script>

</head>

<body>
<c:if test = "${serverNm ne 'REAL'}">
<h1 style="background-color: red;position: fixed;width: 100%;z-index: 99;font-size:11px;">=== ${serverNm } / ${menuInfo.floatingShowYn},${menuInfo.floatingTipSbst},${menuInfo.chatbotTipSbst} ===</h1>
</c:if>

    <input type="hidden" id="gnbMCode" value="${nmcp:getGnbMenuCode()}" />
    <input type="hidden" id="phoneOs" value="${nmcp:getPhoneOs()}"/>

    <c:set var="AILandingUrl" value="${nmcp:getCodeNmDto('AISetting', 'landing')}" />
    <input type="hidden" id="AILandingUrl" value="${AILandingUrl.expnsnStrVal1}">

    <div class="ly-wrap">

        <div id="skipNav">
            <a class="skip-link" href="#main-content">본문 바로가기</a>
        </div>

        <t:insertAttribute name="mainTopBannerAttr" ignore="true"/>

        <t:insertAttribute name="contentAttr"/>
    </div>

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
             <button class="c-button c-button--full c-button--primary" data-dialog-close href="javascript:void(0);" onclick="targetTermsAgree();">동의 후 닫기</button>
           </div>
         </div>
     </div>
    </div>
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
                </div>
                <div class="main-bs__footer">
                    <a class="c-text c-text--type7" id="today_stop" href="#n">오늘 하루 그만보기</a>
                    <a class="c-text c-text--type3 u-fw--bold" href="#n" data-dialog-close>닫기</a>
                </div>
            </div>
        </div>
    </div>
    <!-- [END]  팝업 -->
    <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/ktm.ui.js?ver=${jsver}"></script>
    <script type="text/javascript" src="/resources/js/appCommonNew.js?ver=${jsver}"></script>

    <t:insertAttribute name="scriptHeaderAttr" ignore="true"/>

    <script type="text/javascript" src="/resources/js/nmcpCommon.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/nmcpCommonComponent.js"></script>

    <script>
        let MCP = new NmcpCommonComponent();

        // popup 목록 갯수
        let popupListCnt = 0;
        // popup 목록 조회
        //displayPopupList();

        var mainBannerObject = null;
        document.addEventListener("UILoaded", function() {
            //
        });



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
            autoplay: {
              delay: 3000,
              disableOnInteraction: false,
            },
            on: {
              init: function() {
                // 배너 우측상단 페이징 처리
                var paginations = document.querySelectorAll("#main_bottom_banner .pagination-num");
                [].forEach.call(paginations, function(el, i) {
                  var total = paginations.length;
                  var cur = i + 1;
                  el.innerHTML = '<span class="cur">' + cur + '</span><span class="total">' + total + "</span>";
                });
              },
            },
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
    </script>
<c:if test = "${serverNm eq 'REAL' }">
    <%@ include file="/WEB-INF/views/layouts/analyticsscript.jsp" %>
</c:if>

</body>

</html>
