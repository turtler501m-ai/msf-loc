<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<% response.setStatus(HttpServletResponse.SC_OK); %>
<c:choose>
	<c:when test="${nmcp:getPlatFormCd() eq 'P'}">
		<script>
			location.href='/error/error.do';
		</script>
	</c:when>
	<c:otherwise>
		<script>
			location.href='/m/error/error.do';
		</script>
	</c:otherwise>
</c:choose>
	