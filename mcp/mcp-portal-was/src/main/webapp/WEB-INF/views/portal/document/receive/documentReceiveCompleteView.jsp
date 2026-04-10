<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutNoGnbDefault">

	<t:putAttribute name="titleAttr">서류 등록</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.09.30"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page--title">
				<h2 class="c-page--title">서류 등록</h2>
			</div>
            <div class="c-row c-row--md">
                <div class="c-form-wraps" style="background-color: #f0f0f0; border-radius: 1rem">
                    <div class="c-form" style="padding: 2.5rem;">
                        <p class="c-text c-text--fs22 c-text--regular">서류가 정상적으로 등록되었습니다.</p>
                        <p class="c-text c-text--fs22 c-text--regular">접수 건이 많을 경우 처리에 시간이 다소 소요될 수 있습니다.</p>
                    </div>
                </div>
            </div>
		</div>
	</t:putAttribute>
</t:insertDefinition>