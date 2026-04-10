<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="jsver" value="${nmcp:getJsver()}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="ko">
	 <head>
        <title><t:insertAttribute name="titleAttr" ignore="true" defaultValue="kt M모바일"/></title>
        <meta http-equiv="content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta http-equiv="Cache-Control" content="no-cache"/>
        <meta http-equiv="Expires" content="0"/>
        <meta http-equiv="Pragma" content="no-cache"/>

        <meta name="description" content="TV/인터넷도 업계 최저가 / 다양한 추가할인, 사은품 혜택까지!" />
		<meta name="keywords" content="TV/인터넷도 업계 최저가 / 다양한 추가할인, 사은품 혜택까지!" />
		<meta name="author" content="kt M mobile" />
		<meta name="robots" content="noindex">

        <script type="text/javascript" src="/resources/js/json2.js"></script>
        <script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
	    <script type="text/javascript" src="/resources/js/jquery.placeholder.js"></script>
	    <script type="text/javascript" src="/resources/js/common.js?ver=${jsver}"></script>
	    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=19.01.03"></script>
	    <script type="text/javascript" src="/resources/js/wire/jqueryCommon.js?ver=${jsver}"></script>
	    <script type="text/javascript" src="/resources/js/jquery.number.min.js"></script>
	    <script type="text/javascript" src="/resources/js/wire/wirePopup.js?ver=${jsver}"></script>
		<script type="text/javascript" src="/resources/js/swipe.js"></script>
		<script type="text/javascript" src="/resources/js/jquery.bxslider.min.js"></script>
		<script type="text/javascript" src="/resources/js/owl.carousel.js"></script>
		<script type="text/javascript" src=	"/resources/js/wire/wire.js?ver=${jsver}"></script>


	    <script type="text/javascript">



		    if (navigator.userAgent.indexOf('MSIE') != -1)
		    	var detectIEregexp = /MSIE (\d+\.\d+);/ //test for MSIE x.x
		    else // if no "MSIE" string in userAgent
		    	var detectIEregexp = /Trident.*rv[ :]*(\d+\.\d+)/ //test for rv:x.x or rv x.x where Trident string exists

		    if (detectIEregexp.test(navigator.userAgent)){ //if some form of IE
		    	var ieversion=new Number(RegExp.$1); // capture x.x portion and store as a number

		    	if (ieversion>=8) {
		    		document.write('<link rel="stylesheet" type="text/css" href="/resources/wire/css/common_ie.css">');
		    		document.write('<link rel="stylesheet" type="text/css" href="/resources/wire/css/style_ie.css">');
		    	} else {
		    		document.write('<link rel="stylesheet" type="text/css" href="/resources/wire/css/common_ie.css">');
		    		document.write('<link rel="stylesheet" type="text/css" href="/resources/wire/css/style_ie.css">');
		    	}
		    } else {
		    	document.write('<link rel="stylesheet" type="text/css" href="/resources/wire/css/common_new.css">');
		    	document.write('<link rel="stylesheet" type="text/css" href="/resources/wire/css/style_new2.css">');
		    }
	    </script>

		<link rel="stylesheet" type="text/css" href="/resources/wire/css/main_new.css?version=17.11.22">
		 <link rel="stylesheet" type="text/css" href="/resources/direct/css/ktmmobile.css">

		<!-- 추가 -->
		<link rel="stylesheet" type="text/css" href="/resources/wire/css/wire.css">

		<!-- //추가 -->

		<!--[if (gte IE 6)&(lte IE 8)]>
    		<script type="text/javascript" src="/resources/js/selectivizr-min.js"></script>
		<![endif]-->

        <t:insertAttribute name="scriptHeaderAttr" ignore="true"/>
		<%@ include file="/WEB-INF/views/layouts/wireGDNScript.jsp" %>
    </head>
		<body>

			<!-- 스킵 네비게이션 -->
			<div id="skip_to_container">
				<a href="#wireHeader" >주메뉴 바로가기</a>
				<a href="#wireContents" >본문 바로가기</a>
				<a href="#wireFooter" >푸터 바로가기</a>
			</div>
			<!-- //스킵 네비게이션 -->

			<div id="wireContainer">

				<t:insertAttribute name="mncpWireGnbAttr"/>

				<div id="wireContents">
					<t:insertAttribute name="contentAttr"/>
				</div>

			    <t:insertAttribute name="mncpWireFooterAttr"/>

			    <t:insertAttribute name="popLayerAttr" ignore="true"/>

			</div>
		</body>
	</html>
