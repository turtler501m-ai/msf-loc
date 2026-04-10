<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">

	<t:putAttribute name="titleAttr">휴대폰 안심보험</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<script src="/resources/js/mobile/content/insrView.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<!-- 이 부분에 관리자페이지에서 입력한 html이 들어감 -->
		</div>

		<!-- 로그인 여부 확인 -->
		<input type="hidden" name="loginYn" value="${loginYn}"/>
	</t:putAttribute>
	
</t:insertDefinition>