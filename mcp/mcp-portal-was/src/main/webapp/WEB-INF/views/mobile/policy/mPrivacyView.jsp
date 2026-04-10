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
		<script type="text/javascript" src="/resources/js/mobile/policy/mPrivacyView.js"></script>
	</t:putAttribute>
	<t:putAttribute name="contentAttr">

		<input type="hidden" id="pageNo" name="pageNo" value="${policyDto.pageNo}">
		<input type="hidden" id="stpltSeq" name="stpltSeq" value="${viewDto.stpltSeq}">

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					정책 및 약관
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-tabs c-tabs--type3" data-tab-active="0">
				<div class="c-tabs__list c-expand u-pt--24" data-tab-list="">
					<button class="c-tabs__button" data-tab-header="#tabB1-panel" data-active-scroll id="tab1" onclick="location.href = '/m/policy/privacyList.do'" >개인정보 처리방침</button>
					<button class="c-tabs__button" data-tab-header="#tabB2-panel" data-active-scroll id="tab2" onclick="location.href = '/m/policy/ktPolicy.do'" >이용약관</button>
					<button class="c-tabs__button" data-tab-header="#tabB3-panel" data-active-scroll id="tab3" onclick="location.href = '/m/policy/policyGuideView.do'" >이용약관 주요설명</button>
					<button class="c-tabs__button" data-tab-header="#tabB4-panel" data-active-scroll id="tab4" onclick="location.href = '/m/policy/youngList.do'" >청소년 보호 정책</button>
				</div>
				<div class="c-tabs__panel" id="tabB1-panel">
					<div class="c-board">
						<strong class="c-board__title policy">${viewDto.stpltTitle}</strong>
						<span class="c-board__sub">${viewDto.cretDt}</span>
						<div class="c-board__content">${viewDto.stpltSbst}</div>
						<div class="c-board__nav">
							<c:choose>
								<c:when test="${viewDto.preSeq ne null and viewDto.preSeq ne 0}">
									<a class="c-button" href="javascript:goNextDetail('${viewDto.preSeq}')">
										<i class="c-icon c-icon--triangle-left" aria-hidden="true"></i> <span>이전글</span>
									</a>
								</c:when>
								<c:otherwise>
									<a class="c-button is-disabled">
										<i class="c-icon c-icon--triangle-left" aria-hidden="true"></i> <span>이전글</span>
									</a>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when
									test="${viewDto.nextSeq ne null and viewDto.nextSeq ne 0}">
									<a class="c-button" href="javascript:goNextDetail('${viewDto.nextSeq}')"> <span>다음글</span>
										<i class="c-icon c-icon--triangle-right" aria-hidden="true"></i>
									</a>
								</c:when>
								<c:otherwise>
									<a class="c-button is-disabled"> <span>다음글</span>
										<i class="c-icon c-icon--triangle-right" aria-hidden="true"></i>
									</a>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="c-button-wrap">
						<a href="javascript:;" class="c-button c-button--full c-button--white" id="viewList">이전 개인정보처리방침 보기
							<div class="c-tabs__panel" id="tabB2-panel"></div>
							<div class="c-tabs__panel" id="tabB3-panel"></div>
						</a>
					</div>
				</div>
				<div class="c-tabs__panel" id="tabB2-panel"></div>
				<div class="c-tabs__panel" id="tabB3-panel"></div>
				<div class="c-tabs__panel" id="tabB4-panel"></div>
			</div>
		</div>
	</t:putAttribute>
</t:insertDefinition>