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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <link rel="stylesheet" type="text/css" href="/resources/css/portal/styles.css" />

    <title><t:insertAttribute name="titleAttr" ignore="true" defaultValue="${titleNm }"/> </title>


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

        <t:insertAttribute name="mncpGnbAttr"/>

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
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=19.12.03"></script>
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

    <script type="text/javascript">

        if (navigator.userAgent.indexOf('MSIE') != -1)
            var detectIEregexp = /MSIE (\d+\.\d+);/ //test for MSIE x.x
        else // if no "MSIE" string in userAgent
            var detectIEregexp = /Trident.*rv[ :]*(\d+\.\d+)/ //test for rv:x.x or rv x.x where Trident string exists

        if (detectIEregexp.test(navigator.userAgent)){ //if some form of IE
            var ieversion=new Number(RegExp.$1); // capture x.x portion and store as a number

            if (ieversion>=8) {
                document.write('<link rel="stylesheet" type="text/css" href="/resources/css/front/common_ie.css">');
                document.write('<link rel="stylesheet" type="text/css" href="/resources/css/front/style_ie.css">');
            } else {
                document.write('<link rel="stylesheet" type="text/css" href="/resources/css/front/common_ie.css">');
                document.write('<link rel="stylesheet" type="text/css" href="/resources/css/front/style_ie.css">');
            }
        } else {
            document.write('<link rel="stylesheet" type="text/css" href="/resources/css/front/common_new.css">');
            document.write('<link rel="stylesheet" type="text/css" href="/resources/css/front/style_new2.css">');
        }
    </script>

    <link rel="stylesheet" type="text/css" href="/resources/css/portal/styles.css" />
    <%@ include file="/WEB-INF/views/layouts/analyticsscript.jsp" %>
</body>

</html>
