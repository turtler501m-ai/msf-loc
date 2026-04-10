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
		<script type="text/javascript" src="/resources/js/mobile/mypage/pause/pauseComplete.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					일시정지
					<button class="header-clone__gnb"></button>
				</h2>
			</div>

			<div class="complete">
				<div class="c-icon c-icon--complete" aria-hidden="true">
					<span></span>
				</div>
				<c:choose>
					<c:when test="${gubun eq 'S'}">
						<p class="complete__heading">고객님의 휴대폰 <b>일시정지</b> <br> <b>신청</b>이 <b>완료</b>되었습니다.	</p>
					</c:when>
					<c:otherwise>
						<p class="complete__heading">고객님의 휴대폰 <b>일시정지</b> <br> <b>해제</b>가 <b>완료</b>되었습니다.</p>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="info-box u-mt--24">
				<dl>
					<dt>일시정지 신청일</dt>
					<c:choose>
						<c:when test="${gubun eq 'S'}"><dd>${toDay}</dd></c:when>
						<c:otherwise><dd>${subStatusDate}</dd></c:otherwise>
					</c:choose>
				</dl>
				<dl>
					<dt>고객명</dt>
					<dd>${searchVO.userName}</dd>
				</dl>
				<dl>
					<dt>휴대폰번호</dt>
					<dd>${searchVO.ctn}</dd>
				</dl>
				<dl>
					<dt>정지유형</dt>
					<dd>${reasonDoc}</dd>
				</dl>
				<c:if test="${gubun eq 'S'}">
					<dl>
						<dt>일시정지 기간</dt>
						<dd>${startDate} ~ ${endDate}</dd>
					</dl>
				</c:if>

			</div>
			<div class="c-button-wrap u-mt--48">
				<button class="c-button c-button--full c-button--primary" id="completeBtn">확인</button>
			</div>
			<hr class="c-hr c-hr--type2 u-mt--40">

			<b class="c-text c-text--type3">
				<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
			</b>
			<c:choose>
				<c:when test="${gubun eq 'S'}">
					<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
						<li class="c-text-list__item u-co-gray">일시정지 시 <span class="u-co-red">월 3,850원</span>(부가세 포함/정지일에 따라 일할 계산)의 기본요금이 발생합니다.</li>
						<li class="c-text-list__item u-co-gray">발신정지는 30일만 가능하며, 30일 이후에는 착/발신 정지로 자동 전환됩니다.</li>
						<li class="c-text-list__item u-co-gray">일시정지 기간 종료 시 휴대폰 이용 중 상태로 자동 복구되며(문자안내), 일시정지 이전 사용 요금제 기준으로 이용요금이 부과됩니다.</li>
						<li class="c-text-list__item u-co-gray">분실정지 일수는 일시정지 신청가능일수(90일)에 포함되지 않습니다.</li>
						<li class="c-text-list__item u-co-gray">단말기 분할상환 금액이 남은 경우 일시정지 기간에도 청구됩니다.</li>
						<li class="c-text-list__item u-co-gray">군입대, 장기해외체류 등 특별한 경우에 해당할 경우 최대 3년까지 일시정지가 가능합니다.</li>
						<li class="c-text-list__item u-co-gray">장기 일시정지의 경우 고객센터(1899-5000)를 통해 신청을 부탁드립니다.</li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
						<li class="c-text-list__item u-co-gray">일시정지 기간 종료/해제 후에는 휴대폰 이용 중 상태로 자동 복구되며(문자 안내),일시정지 이전 사용 요금제 기준으로 이용요금이 부과됩니다.</li>
						<li class="c-text-list__item u-co-gray">발신정지는 30일만 가능하며, 30일 이후에는 착/발신정지로 전환됩니다</li>
						<li class="c-text-list__item u-co-gray">분실정지 일수는 일시정지 신청가능일수(90일에 포함되지 않습니다.</li>
					</ul>
				</c:otherwise>
			</c:choose>
		</div>
<input type="hidden" id="ncn" name="ncn" value="${ncn}">
	</t:putAttribute>
</t:insertDefinition>