<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="layoutGnbDefault">

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/portal/mypage/pause/lostCnlView.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page--title">
				<h2 class="c-page--title">분실신고</h2>
			</div>
			<div class="c-tabs c-tabs--type1">
				<div class="c-tabs__list">
					<a class="c-tabs__button" id="tab1" href="/mypage/lostView.do">분실신고</a>
					<a class="c-tabs__button is-active" id="tab2" href="#tab2" aria-current="page">분실신고 해제</a>
				</div>
			</div>
			<div class="c-tabs__panel u-block">
				<div>
					<div class="c-row c-row--lg">
						<h3 class="c-heading c-heading--fs20 c-heading--regular">조회할 회선을 선택해 주세요.</h3>
						<%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp" %>
						<hr class="c-hr c-hr--title">
						<h3 class="c-heading c-heading--fs16 u-mb--16">분실신고 접수 현황</h3>
						<c:choose>
							<c:when test="${runMode eq 'U'}">
								<div class="c-table">
									<table>
										<caption>휴대폰번호, 접수일자, 신고해제를 포함한 분실신고 접수 현황 표</caption>
										<colgroup>
											<col style="width: 18%">
											<col>
										</colgroup>
										<tbody class="c-table__left">
											<tr>
												<th scope="row">휴대폰 번호</th>
												<td id="cntcTlphNo">${searchVO.ctn}</td>
											</tr>
											<%-- <tr>
												<th scope="row">접수 일자</th>
												<td>-</td>
											</tr> --%>
											<tr>
												<th scope="row">신고 해제</th>
												<td>
													<div class="c-form" style="width: 16.875rem;">
  														<div class="c-input-wrap u-mt--0">
															<input class="c-input onlyNum" id="strPwdNumInsert" name="strPwdNumInsert" type="password" placeholder="분실신고 해제 비밀번호 입력" maxlength="8">
															<label class="c-hidden" for="strPwdNumInsert">분실신고 해제 비밀번호 입력</label>
															<button class="c-button c-button--underline c-button--sm u-co-sub-4" id="cnlApplyBtn" disabled="disabled">확인</button>
														</div>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</c:when>
							<c:otherwise>
								<div class="c-nodata">
									<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
									<p class="c-nodata__text">분실신고 내역이 없습니다.</p>
								</div>
							</c:otherwise>
						</c:choose>
						<h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
							<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
							<span class="u-ml--4">알려드립니다</span>
						</h3>
						<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
							<li class="c-text-list__item u-co-gray">분실신고 해제 비밀번호를 분실하신 경우 고객센터를 통해서 해제가 가능합니다.</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" id="userName" name="userName" value="${searchVO.userName}">
		<input type="hidden" id="ncn" name="ncn" value="${ncn}">
	</t:putAttribute>
</t:insertDefinition>