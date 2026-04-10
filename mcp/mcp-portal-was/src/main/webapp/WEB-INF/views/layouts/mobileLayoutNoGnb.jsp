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
    <link href="/resources/css/mobile/styles.css" rel="stylesheet" />

    <title><t:insertAttribute name="titleAttr" ignore="true" defaultValue="${titleNm }"/> | kt M모바일 공식 다이렉트몰 </title>
    <link rel="icon" href="/favorites_icon03.png" type="image/x-icon">


</head>

<body>
<c:if test = "${serverNm ne 'REAL'}">
<h1 style="background-color: red;position: fixed;width: 100%;z-index: 99;font-size:11px;">=== ${serverNm } / ${menuInfo.floatingShowYn},${menuInfo.floatingTipSbst},${menuInfo.chatbotTipSbst} ===</h1>
</c:if>

    <input type="hidden" id="phoneOs" value="${nmcp:getPhoneOs()}"/>

    <div class="ly-wrap">

        <div id="skipNav">
            <a class="skip-link" href="#main-content">본문 바로가기</a>
        </div>

        <t:insertAttribute name="contentAttr"/>

    </div>

    <t:insertAttribute name="popLayerAttr" ignore="true"/>

    <t:insertAttribute name="mainBannerAttr" ignore="true"/>

    <t:insertAttribute name="scriptHeaderAttr" ignore="true"/>
    <%@ include file="/WEB-INF/views/layouts/analyticsscript.jsp" %>
</body>

</html>
