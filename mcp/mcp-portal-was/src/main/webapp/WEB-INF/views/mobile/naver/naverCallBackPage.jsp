<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"/></script>
		<script type="text/javascript" src="/resources/js/jqueryCommon.js"/></script>
		<script type="text/javascript" src="/resources/js/mobiel/naver/naverCallBackPage.js"></script>
		<title>네이버로그인 완료</title>
	</head>
	<body>
		<input type="text" id="authResult" name="authResult" value="mobile:${authResult}"/>
		<input type="text" id="errMsg" name="errMsg" value="${errMsg}"/>
	</body>

</html>