<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="serverNm" value="${nmcp:getPropertiesVal('SERVER_NAME')}" />
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />

<t:insertDefinition name="mlayoutDefault">

	<t:putAttribute name="scriptHeaderAttr">

	<script>
		KTM.LoadingSpinner.show();

		getLoginToken();
	</script>	

	</t:putAttribute>
	
	<t:putAttribute name="contentAttr">
	<input type="hidden" name="platFormCd" id="platFormCd" value="${nmcp:getPlatFormCd()}"/>
	<input type="hidden" id="phoneOs" value="${nmcp:getPhoneOs()}"/>
	<input type="hidden" name="uuid" id="uuid"/>

	<div id="main_loading" style="display:flex; width: 100%; height: calc(100vh - 130px); box-sizing: border-box; background: rgb(255, 255, 255); text-align: center; position: relative; align-items: center; justify-content: center; z-index: 10;">
	</div>

	</t:putAttribute>
	
</t:insertDefinition>