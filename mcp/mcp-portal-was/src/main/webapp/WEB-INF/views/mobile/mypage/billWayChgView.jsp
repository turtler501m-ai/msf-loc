<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/mobile/mypage/billWayChgView.js?version=${jsver}"></script>
	</t:putAttribute>
	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					요금명세서
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-tabs c-tabs--type2" data-tab-active="1">
				<div class="c-tabs__list c-expand sticky-header" data-tab-list="">
					<button class="c-tabs__button" data-tab-header="#Fbill01-panel" id="Fbill01">명세서 재발송</button>
					<button class="c-tabs__button" data-tab-header="#Fbill02-panel" href="javascript:;" id="Fbill02">명세서 정보변경</button>
				</div>
				<div class="c-tabs__panel" id="Fbill01-panel"></div>
				<div class="c-tabs__panel" id="Fbill02-panel">
					<h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
		            <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>
					<h4 class="c-heading c-heading--type2 u-mb--12">이용중인 명세서 정보</h4>
					<div class="info-box">

					</div>
					<h4 class="c-heading c-heading--type2 u-mb--12">변경 유형 선택</h4>
					<div class="c-form">
						<select class="c-select" id="selCategory">
							<option value="MB">모바일(MMS)</option>
							<option value="CB">이메일</option>
							<option value="LX">우편</option>
						</select>
					</div>
					<div class="category-view u-hide" id="cateMobile">
						<div class="c-banner-box c-box u-mt--40">
							<p class="c-text c-text--type3 u-fw-normal">
								모바일(MMS) 선택 시 <br>각 회선으로 명세서가 발송됩니다.
							</p>
							<img class="deco-img" src="/resources/images/mobile/content/img_bill.png" alt="">
						</div>
					</div>
					<div class="category-view u-hide" id="cateEmail">
						<div class="c-form">
							<div class="c-form__title u-fw-normal">이메일 주소</div>
							<input class="c-input" type="text" placeholder="예: ktm@ktm.com" title="이메일 주소 입력" id="emailAdr" name="emailAdr" maxlength="50">
						</div>
					</div>
					<div class="category-view u-hide" id="catePost">
						<div class="c-form">
							<span class="c-form__title u-fw-normal" id="inpG">명세서 주소</span>
							<div class="c-form__group" role="group" aria-labelledby="inpG">
								<div class="c-input-wrap u-mt--0">
									<input class="c-input" type="text" placeholder="우편번호"	title="우편번호 입력" readonly="readonly" id="postNo">
									<button class="c-button c-button--sm c-button--underline" onclick="openPage('pullPopup', '/m/addPopup.do');">우편번호 찾기</button>
								</div>
								<input class="c-input" type="text" placeholder="주소 입력" title="주소 입력" readonly id="address1">
								<input class="c-input" type="text" placeholder="상세 주소 입력" title="상세 주소 입력" readonly id="address2">
							</div>
						</div>
					</div>
					<div class="c-button-wrap u-mt--48">
						<button class="c-button c-button--full c-button--primary" onclick="changeSubmit();">변경</button>
					</div>
				</div>
			</div>
		</div>

	</t:putAttribute>
</t:insertDefinition>