<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html lang="ko-KR">
	 <head>
        <title><t:insertAttribute name="titleAttr" ignore="true" defaultValue="kt 엠모바일"/> </title>
        <meta http-equiv="content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta http-equiv="Cache-Control" content="no-cache"/> 
        <meta http-equiv="Expires" content="0"/> 
        <meta http-equiv="Pragma" content="no-cache"/> 
        <t:insertAttribute name="metaAttr" ignore="true"/>
<!--         <script type="text/javascript" src="/js/mcp/json2.js"></script> -->
<!--         <script type="text/javascript" src="/js/mcp/jquery-1.11.1.min.js"></script> -->
<!-- 	    <script type="text/javascript" src="/js/mcp/jquery.placeholder.js"></script> -->
<!-- 	    <script type="text/javascript" src="/js/mcp/common.js"></script> -->
<!-- 	    <script type="text/javascript" src="/js/mcp/dataFormConfig.js"></script> -->
<!--         <script type="text/javascript" src="/js/mcp/webReady.js"></script> -->
	    <script type="text/javascript" src="/js/mcp/jqueryCommon.js"></script>

	    <script type="text/javascript">
// 	    if (!fn_mobileCheck()) {
			if (navigator.userAgent.indexOf('MSIE') != -1)
				var detectIEregexp = /MSIE (\d+\.\d+);/ //test for MSIE x.x
			else // if no "MSIE" string in userAgent
				var detectIEregexp = /Trident.*rv[ :]*(\d+\.\d+)/ //test for rv:x.x or rv x.x where Trident string exists

			if (detectIEregexp.test(navigator.userAgent)){ //if some form of IE
				var ieversion=new Number(RegExp.$1); // capture x.x portion and store as a number

				if (ieversion>=8) {
					document.write('<link rel="stylesheet" type="text/css" href="/css/mcp/common_ie.css?version=3.30">');
					document.write('<link rel="stylesheet" type="text/css" href="/css/mcp/style_ie.css?version=3.30">');
				} else {
					document.write('<link rel="stylesheet" type="text/css" href="/css/mcp/common_ie.css?version=3.30">');
					document.write('<link rel="stylesheet" type="text/css" href="/css/mcp/style_ie.css?version=3.30">');
				}
			} else {
				document.write('<link rel="stylesheet" type="text/css" href="/css/mcp/common_new.css?version=3.30">');
				document.write('<link rel="stylesheet" type="text/css" href="/css/mcp/style_new2.css?version=3.30">');
			}
// 		} else {
// 			document.write('<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />');
// 			document.write('<link rel="stylesheet" type="text/css" href="/resources/css/mobile/common.css?version=3.30">');
// 			document.write('<link rel="stylesheet" type="text/css" href="/resources/css/mobile/style.css?version=3.30">');
// 		}
	    </script>

<!--         <script type="text/javascript" src="/js/mcp/googleAnalyti.js"></script> -->
        <t:insertAttribute name="scriptHeaderAttr" ignore="true"/>
<%-- 		<%@ include file="/WEB-INF/jsp/layout/mcp/GDNScript.jsp" %> --%>
    </head>
<body>
	 <t:insertAttribute name="contentAttr"/>
</body>
</html>
