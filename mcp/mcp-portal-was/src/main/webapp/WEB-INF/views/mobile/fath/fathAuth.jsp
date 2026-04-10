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
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.06.10"></script>
		<script type="text/javascript" src="/resources/js/mobile/fath/fathAuth.js?version=25.12.19"></script>
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

			<c:if test="${viewType eq '0000'}">
				<div class="c-form u-mt--40">
					<span class="c-form__title">가입정보 확인</span>
					<%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
				</div>

				<div class="c-button-wrap">
					<button class="c-button c-button--full c-button--primary" type="button" id="btn_regFath" disabled="disabled" onclick="btn_regFath();">확인</button>
				</div>

				<h3 class="c-heading c-heading--type2 u-mb--12">유의사항</h3>
				<ul class="c-text-list c-bullet c-bullet--fyr">
					<li class="c-text-list__item u-co-gray">안면인증 고객의 이름과 연락처를 입력해주세요.</li>
					<li class="c-text-list__item u-co-gray">SMS(단문메시지)는 시스템 사정에 따라 다소 지연 될 수 있습니다.</li>
					<li class="c-text-list__item u-co-gray">인증번호는 114로 발송 되오니 문자 수신이 되지 않는 경우 해당번호의 단말기 스팸 설정 여부 및 스팸편지함을 확인해주세요.</li>
				</ul>

			</c:if>

			<c:if test="${viewType eq '0001'}">
				<div class="error u-ta-center">
					<i aria-hidden="true">
						<img src="/resources/images/mobile/content/img_error.png" alt="">
					</i>
					<strong class="error__title">
						<b>사이트 이용에<br>불편을 드려 죄송합니다.</b>
					</strong>
					<p class="error__text">
						URL이 존재하지 않습니다. 문자로 수신받은 URL로 다시 이용부탁드립니다.
					</p>
					<div class="c-button-wrap">
						<a class="c-button c-button--full c-button--primary" href="/m/main.do">홈페이지로 이동</a>
					</div>
				</div>
			</c:if>
			
			<c:if test="${viewType eq '0002'}">
				<div class="error u-ta-center">
					<i aria-hidden="true">
						<img src="/resources/images/mobile/content/img_error.png" alt="">
					</i>
					<strong class="error__title">
						<b>사이트 이용에<br>불편을 드려 죄송합니다.</b>
					</strong>
					<p class="error__text">
						해당 URL은 만료된 URL입니다. 고객센터를 통해 재발급요청 부탁드립니다.
					</p>
					<div class="c-button-wrap">
						<a class="c-button c-button--full c-button--primary" href="/m/main.do">홈페이지로 이동</a>
					</div>
				</div>
			</c:if>

			<c:if test="${viewType eq '0003'}">
				<div class="error u-ta-center">
					<i aria-hidden="true">
						<img src="/resources/images/mobile/content/img_error.png" alt="">
					</i>
					<strong class="error__title">
						<b>사이트 이용에<br>불편을 드려 죄송합니다.</b>
					</strong>
					<p class="error__text">
						안면인증은 21시까지만 이용 가능합니다. 다음날 이용 부탁드립니다.
					</p>
					<div class="c-button-wrap">
						<a class="c-button c-button--full c-button--primary" href="/m/main.do">홈페이지로 이동</a>
					</div>
				</div>
			</c:if>

		</div>
	</t:putAttribute>
</t:insertDefinition>