<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"/></script>
		<script type="text/javascript" src="/resources/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
		<script type="text/javascript" src="/resources/js/jqueryCommon.js"/></script>
        <script type="text/javascript" src="/resources/js/mobile/naver/nearoCallBackLogin.js"></script>
        
		<title>네이버로그인-네아로연동</title>
	</head>
	<body>
		<input type="hidden" id="clientId" name="clientId" value="<spring:eval expression="@propertiesForJsp['NAVER_CLIENT_ID_NEW']" />">
		<input type="hidden" id="nearoCallBackUrl" name="nearoCallBackUrl" value="${nmcp:getServerInfo()}<spring:eval expression="@propertiesForJsp['NEARO_MOBILE_LOGIN_CALLBACK_URL']" />"/>
		
	</body>

</html>