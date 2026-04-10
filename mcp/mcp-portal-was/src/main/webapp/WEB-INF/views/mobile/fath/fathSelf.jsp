<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutNoGnbFotter">

	<t:putAttribute name="titleAttr">셀프안면인증 URL</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.12.19"></script>
		<script type="text/javascript" src="/resources/js/mobile/fath/fathSelf.js?version=26.03.03"></script>
		<script type="text/javascript" src="/resources/js/appForm/validateMsg.js?version=25.12.19"></script>
		<script type="text/javascript" src="/resources/js/common/validator.js?version=25.12.19"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<input type="hidden" id="resNo" name="resNo" value="${resNo}"/>
		<input type="hidden" id="operType" name="operType" value="${operType}"/>
		<input type="hidden" id="isFathTxnRetv" name="isFathTxnRetv">

		<div class="ly-content" id="main-content">

			<div class="ly-page-sticky">
				<h2 class="ly-page__head">셀프안면인증 본인확인<button class="header-clone__gnb"></button>
				</h2>
			</div>

			<div class="c-form _isFathCert">
				<button id="btnFathUrlRqt" class="c-button c-button--h48 c-button--mint c-button--full u-mt--32">안면인증 URL 받기</button>
				<div class="c-button-wra u-mt--32">
					<button id="btnFathTxnRetv" class="c-button c-button--h48 c-button--mint c-button--full" disabled>안면인증 결과 확인</button>
					<button id="btnFathSkip" class="c-button c-button--h48 c-button--gray c-button--full u-mt--8" onclick="requestFathSkip();" disabled>안면인증 SKIP</button>
				</div>
			</div>

			

		</div>
	</t:putAttribute>
</t:insertDefinition>