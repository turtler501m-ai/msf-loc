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
		<script type="text/javascript" src="/resources/js/mobile/policy/mKtPolicyList.js"></script>
	</t:putAttribute>
	<t:putAttribute name="contentAttr">

		<input type="hidden" id="pageNo" name="pageNo" value="${pageInfoBean.pageNo}">
		<input type="hidden" id="userAgent" name="userAgent" value="${header['User-Agent']}">
		<input type="hidden" id="totalCount" name="totalCount" value="${totalCount}">
		<input type="hidden" id="paramStpltSeq" name="paramStpltSeq" value="${paramStpltSeq}">



		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					정책 및 약관
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-tabs c-tabs--type3" data-tab-active="1">
				<div class="c-tabs__list c-expand u-pt--24" data-tab-list="">
					<button class="c-tabs__button" data-tab-header="#tabB1-panel" data-active-scroll onclick="location.href = '/m/policy/privacyList.do'">개인정보 처리방침</button>
					<button class="c-tabs__button" data-tab-header="#tabB2-panel" data-active-scroll onclick="location.href = '/m/policy/ktPolicy.do'">이용약관</button>
					<button class="c-tabs__button" data-tab-header="#tabB3-panel" data-active-scroll onclick="location.href = '/m/policy/policyGuideView.do'">이용약관 주요설명</button>
					<button class="c-tabs__button" data-tab-header="#tabB4-panel" data-active-scroll onclick="location.href = '/m/policy/youngList.do'">청소년 보호 정책</button>
				</div>
				<div class="c-tabs__panel" id="tabB1-panel"></div>
				<div class="c-tabs__panel" id="tabB2-panel">
					<select class="c-select" id="stpltCtgCd">
						<option value="00">전체</option>
						<c:forEach var="policyCategory" items="${policyCategory}">
							<c:if test="${policyCategory.DTL_CD ne 06 && policyCategory.DTL_CD ne 05}">
								<option value="${policyCategory.DTL_CD}"
									<c:if test="${policyDto.paramStpltCtgCd eq policyCategory.DTL_CD or policyDto.stpltCtgCd eq policyCategory.DTL_CD}">selected = "selected"</c:if>>${policyCategory.DTL_CD_NM}</option>
							</c:if>
						</c:forEach>
					</select>

					<c:choose>
						<c:when test="${policyDtoList ne null and !empty policyDtoList}">
							<ul class="c-list c-list--type1 c-list--download u-mt--12">
								<c:forEach var="policyDtoList" items="${policyDtoList}" varStatus="status">
									<c:choose>
										<c:when test="${pageInfoBean.pageNo*10 >= status.count}"><li class="c-list__item list" stpltSeq="${policyDtoList.stpltSeq}"></c:when>
										<c:otherwise><li class="c-list__item list" style="display:none;" stpltSeq="${policyDtoList.stpltSeq}"></c:otherwise>
									</c:choose>
										<a class="c-list__anchor" href="javascript:goDetail('${policyDtoList.stpltSeq}')">
											<span class="c-list__desc c-text-ellipsis u-mt--0">${policyDtoList.stpltCtgNm}</span>
											<strong class="c-list__title c-text-ellipsis u-mt--8">${policyDtoList.stpltTitle}</strong>
											<span class="c-list__sub">${policyDtoList.cretDt}</span>
											<a class="c-button" href="javascript:fileDownLoad('${policyDtoList.attSeq}','2',fileDownCallBack,'${policyDtoList.stpltBasSeq}');">
												<i class="c-icon c-icon--download" aria-hidden="true"></i>
											</a>
										</a>
									</li>
								</c:forEach>
							</ul>
							<c:if test="${totalCount > 10}">
								<div class="c-button-wrap">
									<button class="c-button c-button--full" id="moreBtn">더보기
										<span class="c-button__sub" id="pageNav">
											<span id="listCount">10</span>/<span id="totalCount">${totalCount}</span>
										</span>
										<i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
									</button>
								</div>
							</c:if>
						</c:when>
						<c:otherwise>
							<div class="c-nodata">
								<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
								<p class="c-nodata__text">등록된 게시물이 없습니다.</p>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="c-tabs__panel" id="tabB3-panel"></div>
				<div class="c-tabs__panel" id="tabB4-panel"></div>
			</div>
		</div>
	</t:putAttribute>
</t:insertDefinition>