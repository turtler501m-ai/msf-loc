<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%
	long timestamp = 5;		// script 변경시 증가 (cache 방지용)
%>
<!-- Library -->
<link rel="stylesheet" type="text/css" href="<c:url value='/lib/dhtmlx/codebase/dhtmlx.css?' /><%= timestamp %>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/mvno.css?' /><%= timestamp %>"/>
<script type="text/javascript">
	var ROOT = "<c:url value='/' />" ;		// Client 에서 ContextRoot 활용 
</script>   

<script src="<c:url value='/lib/jquery/jquery-1.9.1.min.js' />"></script>
<script src="<c:url value='/lib/dhtmlx/codebase/dhtmlx.min.js' />"></script>
<script src="<c:url value='/js/mvno.js?'/><%= timestamp %>" ></script>
<script src="<c:url value='/js/mvno_etc.js?'/><%= timestamp %>" ></script>
<!-- Library  -->
