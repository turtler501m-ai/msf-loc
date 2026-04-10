<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%
	long timestamp = 7;		// script 변경시 증가 (cache 방지용)
%>
<script src="<c:url value='/js/mvno_footer.js?' /><%= timestamp %>"></script>