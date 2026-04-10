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
		<script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
		<script type="text/javascript" src="/resources/js/portal/mypage/pause/pauseInfo.js?version=26-02-10"></script>
		<script type="text/javascript" src="/resources/js/portal/popup/diAuth.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">

		<div class="ly-content" id="main-content">
			<div class="ly-page--title">
				<h2 class="c-page--title">일시정지</h2>
			</div>
			<div class="c-row c-row--lg">
				<h3 class="c-heading c-heading--fs20 c-heading--regular">조회할 회선을 선택해 주세요.</h3>
				<%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp" %>
				<hr class="c-hr c-hr--title">
				<a href="#n" role="button" data-tpbox="#pauseTooltip" aria-describedby="#pauseTooltip__title">
					<h3 class="c-heading c-heading--fs16 u-inline-block">일시정지 이용/잔여 내역</h3>
					<i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
				</a>
				<div class="c-tooltip-box is-active" id="pauseTooltip" role="tooltip" style="left: 100px">
					<div class="c-tooltip-box__title c-hidden" id="pauseTooltip__title">일시정지 이용/잔여 내역 상세설명</div>
					<div class="c-tooltip-box__title">연간 일시정지 가능일수</div>
					<div class="c-tooltip-box__content">연 2회 신청가능</div>
					<div class="c-tooltip-box__title">1회 일시정지 가능일수</div>
					<div class="c-tooltip-box__content">1회 최대 90일</div>
					<div class="c-tooltip-box__title">발신정지 가능일수</div>
					<div class="c-tooltip-box__content">30일만 가능하며, 30일 이후에는 착발신 정지로 전환됩니다.</div>
				</div>
				<div class="c-table u-mt--16">
					<table>
						<caption>일시정지 횟수 이용/잔여, 일시정지 일수 이용/잔여, 일시정지 종료 예정일를 포함한 일시정지 이용/잔여 내역</caption>
						<colgroup>
							<col style="width: 33%">
							<col style="width: 33%">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th scope="col">일시정지 횟수 이용/잔여</th>
								<th scope="col">일시정지 일수 이용/잔여</th>
								<th scope="col">일시정지 종료 예정일</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${susCnt}회 / ${remainSusCnt}회</td>
								<td>${susDays}일 / ${180-solSusDays}일</td>
								<td>
									<c:choose>
										<c:when test="${subStatus eq 'A'}">회선 사용 중</c:when>
										<c:otherwise>${expectSpDate}</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="c-button-wrap u-mt--40">
					<button class="c-button c-button-round--md c-button--mint u-width--460" type="button" id="btnSuspenHis">일시정지 이력조회</button>
				</div>
				<h3 class="c-heading c-heading--fs16 u-mt--48">일시정지 신청/해제 본인인증 동의</h3>
				<div class="c-agree u-mt--16">
					<div class="c-agree__item">
						<div class="c-agree__inner">
							<input class="c-checkbox" id="chkA" type="checkbox" name="chkA">
							<label class="c-label" for="chkA">본인인증 절차 동의</label>
						</div>
					</div>
				</div>
				<div class="c-box c-box--type1 u-mt--20">
					<p class="c-bullet c-bullet--dot u-co-gray">명의자 본인 확인을 위해서 본인인증이 필요합니다. 안전한 서비스 이용 및 고객님의 개인정보 보호를 위해 본인인증을 받으신 고객분만 이용이 가능합니다. 일시정지 이용을 위해서 본인확인 절차를 진행해주세요.</p>
				</div>
				<!-- 선택상태 활성화 시 클래스 .is-active 추가-->
				<h3 class="c-heading c-heading--fs16 u-mt--48">본인인증</h3>
				<div class="c-button-wrap u-mt--16">
					<button class="c-button c-button--check c-button--mint" id="auth_phone">
						<i class="c-icon c-icon--phone" aria-hidden="true"></i>
						<span>휴대폰</span>
					</button>
					<button class="c-button c-button--check c-button--mint" id="auth_credit">
						<i class="c-icon c-icon--card" aria-hidden="true"></i>
						<span>신용카드</span>
					</button>
					<button class="c-button c-button--check c-button--mint" id="auth_ipin">
						<i class="c-icon c-icon--ipin" aria-hidden="true"></i>
						<span>아이핀</span>
					</button>
				</div>
				<h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
					<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
					<span class="u-ml--4">알려드립니다</span>
				</h3>
				<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
					<li class="c-text-list__item u-co-gray">일시정지 시 <span class="u-co-red">월 3,850원</span>(부가세 포함/정지일에 따라 일할 계산)의 기본요금이 발생합니다.</li>
					<li class="c-text-list__item u-co-gray">일시정지 신청 후 일시정지 자동연장 시 명세서를 통하여 안내를 드리며, 일시정지 기간 만료 시 문자를 통해 안내해 드립니다.</li>
					<li class="c-text-list__item u-co-gray">사용중인 스폰서 유형에 따라 일시 정지 기간만큼 약정기간이 연장 적용되며, 일시정지 시 요금할인/월정액(사용중인 요금제의 기본료 및 정지 사용료)/무료 제공량은 일할계산되어 제공됩니다.</li>
				</ul>
			</div>
		</div>

<input type="hidden" id="subStatus" name="subStatus" value="${subStatus}">
<input type="hidden" id="ctnStatus" name="ctnStatus" value=""/>
<input type="hidden" id="rsltInd" name="rsltInd" value=""/>
<input type="hidden" id="ncn" name="ncn" value="${ncn}"/><!-- 뒤로가기용필드 -->


	</t:putAttribute>
</t:insertDefinition>