<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="menuInfo" value="${nmcp:getCurrentMenuInfo()}" />
<c:set var="nonAdDescription" value="${nmcp:getCodeNm('Constant','nonAdDescription')}" />


<meta name="description" content="${nonAdDescription}">
<meta name="keywords" content="${nmcp:getCodeDesc('MetaKeywords','01')}">

<meta property="og:type" content="website">
<meta property="og:title" content="kt M모바일 다이렉트몰">
<meta property="og:image" content="https://${header['host']}/ktMmobile_og.png" />
<meta property="og:description" content="압도적 1등 알뜰폰">
<meta property="og:site_name" content="ktmmobile">
