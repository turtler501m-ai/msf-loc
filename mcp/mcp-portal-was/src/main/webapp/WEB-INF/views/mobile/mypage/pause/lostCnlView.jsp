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
		<script type="text/javascript" src="/resources/js/mobile/mypage/pause/lostCnlView.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					분실신고
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-tabs c-tabs--type2" data-tab-active="1">
				<div class="c-tabs__list c-expand sticky-header" data-tab-list="">
					<button class="c-tabs__button" data-tab-header="#tabB1-panel" id="lostBtn">분실신고</button>
					<button class="c-tabs__button" data-tab-header="#tabB2-panel">분실신고 해제</button>
				</div>
				<div class="c-tabs__panel" id="tabB1-panel"></div>
				<div class="c-tabs__panel" id="tabB2-panel">
					<h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
					<%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>

					<div class="c-table c-table--plr-16 u-mt--40">
						<p class="c-table-title u-co-black fs-16 u-fw--medium">분실신고 접수 현황</p>
						<c:choose>
							<c:when test="${runMode eq 'U'}">
								<table class="u-mb--16">
									<caption>분실신고 접수 현황</caption>
									<colgroup>
										<col style="width: 29%">
										<col style="width: 71%">
									</colgroup>
									<tbody>
										<tr>
											<th class="u-ta-left" scope="row">휴대폰 번호</th>
											<td class="u-ta-left" id="cntcTlphNo">${searchVO.ctn}</td>
										</tr>
										<%-- <tr>
											<th class="u-ta-left" scope="row">접수 일자</th>
											<td class="u-ta-left">-</td>
										</tr> --%>
										<tr>
											<th class="u-ta-left" scope="row">신고 해제</th>
											<td class="u-ta-left u-pa--12">
												<div class="c-input-wrap u-mt--0">
													<input class="c-input onlyNum" type="password" id="strPwdNumInsert" name="strPwdNumInsert" value="" placeholder="분실신고 해제 비밀번호 입력" maxlength="8">
													<button class="c-button c-button--underline c-button--sm u-co-mint" href="javascript:;" id="cnlApplyBtn" disabled="disabled">확인</button>
												</div>
											</td>
										</tr>
									</tbody>
								</table>
							</c:when>
							<c:otherwise>
								<div class="c-nodata">
									<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
									<p class="c-noadat__text">조회 결과가 없습니다.</p>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<hr class="c-hr c-hr--type2">
					<b class="c-text c-text--type3">
						<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
					</b>
					<ul class="c-text-list c-bullet c-bullet--dot">
						<li class="c-text-list__item u-co-gray">분실신고 해제 비밀번호를 분실하신 경우 고객센터를 통해서 해제가 가능합니다.</li>
					</ul>
				</div>
			</div>
		</div>
<input type="hidden" id="userName" name="userName" value="${searchVO.userName}">
<input type="hidden" id="ncn" name="ncn" value="${ncn}">
	</t:putAttribute>
</t:insertDefinition>