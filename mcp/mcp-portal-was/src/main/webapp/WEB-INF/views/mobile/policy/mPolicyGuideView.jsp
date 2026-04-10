<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">

	</t:putAttribute>
	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					정책 및 약관
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-tabs c-tabs--type3" data-tab-active="2">
				<div class="c-tabs__list c-expand u-pt--24" data-tab-list="">
					<button class="c-tabs__button" data-tab-header="#tabB1-panel" data-active-scroll onclick="location.href = '/m/policy/privacyList.do'">개인정보 처리방침</button>
					<button class="c-tabs__button" data-tab-header="#tabB2-panel" data-active-scroll onclick="location.href = '/m/policy/ktPolicy.do'">이용약관</button>
					<button class="c-tabs__button" data-tab-header="#tabB3-panel" data-active-scroll onclick="location.href = '/m/policy/policyGuideView.do'">이용약관 주요설명</button>
					<button class="c-tabs__button" data-tab-header="#tabB4-panel" data-active-scroll onclick="location.href = '/m/policy/youngList.do'">청소년 보호 정책</button>
				</div>
				<div class="c-tabs__panel" id="tabB1-panel"></div>
				<div class="c-tabs__panel" id="tabB2-panel"></div>
				<div class="c-tabs__panel" id="tabB3-panel">
					<c:choose>
						<c:when test="${policyDtoList ne null and !empty policyDtoList}">
							<c:forEach var="policyDtoList" items="${policyDtoList}">
								<h3 class="c-heading c-heading--type2 u-fs-20">
									<b>${policyDtoList.stpltTitle}</b>
								</h3>
								${policyDtoList.stpltSbst}
							</c:forEach>
						</c:when>
						<c:otherwise>
							내용없음
						</c:otherwise>
					</c:choose>
				</div>
				<div class="c-tabs__panel" id="tabB4-panel"></div>
			</div>
		</div>
	</t:putAttribute>
</t:insertDefinition>