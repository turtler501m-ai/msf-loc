<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="No-Cache">
<title><tiles:getAsString name="title" /> @ <%=request.getServerName()%></title>
<tiles:insertAttribute name="header" />
</head> 
<body style="margin:0px">
	<div style="width:100%;height:50px;font-size:24px;font-family:Verdana;background:#99ccff;color:white;border-bottom:solid 1px #6699cc">
		<tiles:insertAttribute name="head" />
	</div>
	<div style="height:600px;">
		<tiles:insertAttribute name="body" />
	</div> 
	<div style="width:100%;height:20px;background:#99ccff;color:white;">
		<tiles:insertAttribute name="foot" />
	</div>
</body>
</html>