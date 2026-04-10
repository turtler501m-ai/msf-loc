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
		<script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
		<script type="text/javascript" src="/resources/js/mobile/mypage/pause/pauseInfo.js?version=26-02-10"></script>
		<script type="text/javascript" src="/resources/js/mobile/popup/diAuth.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					일시정지
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
			<%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>
			<div class="c-form">
				<div class="c-form__group c-box" role="group" aria-labelledby="inpG">
					<a class="c-form__title u-mt--40" data-tpbox="#example_tp1">
						일시정지 이용/잔여 내역<i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
					</a>
					<div class="c-tooltip-box u-mt--0" id="example_tp1" style="margin-left: -0.75rem">
						<div class="c-tooltip-box__title"></div>
						<div class="c-tooltip-box__content">
							<ul class="c-text-list c-bullet c-bullet--dot">
								<li class="c-text-list__item u-co-gray">연간 일시정지 가능일수</li>
								<li class="c-text-list__item u-co-gray">- 연 2회 신청가능</li>
								<li class="c-text-list__item u-co-gray">1회 일시정지 가능일수</li>
								<li class="c-text-list__item u-co-gray">- 1회 최대90일</li>
								<li class="c-text-list__item u-co-gray">발신정지 가능일수</li>
								<li class="c-text-list__item u-co-gray">- 30일만 가능하며, 30일 이후에는 착발신 정지로 전환됩니다.</li>
							</ul>
						</div>
						<button class="c-tooltip-box__close" data-tpbox-close>
							<span class="c-hidden">툴팁닫기</span>
						</button>
					</div>
					<div class="info-box">
						<dl>
							<dt>일시정지 횟수 이용/잔여</dt>
							<dd>
								<span class="u-co-mint">${susCnt}회</span>/<span>${remainSusCnt}회</span>
							</dd>
						</dl>
						<dl>
							<dt>일시정지 일수 이용/잔여</dt>
							<dd>
								<span class="u-co-mint">${susDays}일</span>/<span>${180-solSusDays}일</span>
							</dd>
						</dl>
						<dl>
							<dt>일시정지 종료 예정일</dt>
							<c:choose>
								<c:when test="${subStatus eq 'A'}"><dd>회선 사용 중</dd></c:when>
								<c:otherwise><dd>${expectSpDate}</dd></c:otherwise>
							</c:choose>
						</dl>
					</div>

					<button class="c-button c-button--lg c-button--mint c-button--full u-mt--32" id="btnSuspenHis">일시정지 이력조회</button>
					<span class="c-form__title u-mt--40 u-mb--0">일시정지 신청 / 해제 본인인증 동의</span>
					<hr class="c-hr c-hr--type2 u-mt--16">
					<input class="c-checkbox" id="chkA" type="checkbox" name="chkA">
					<label class="c-label fs-16 u-fw-normal" for="chkA">본인인증 절차 동의</label>
					<div class="fs-14 u-mt--24">
						<p>명의자 본인 확인을 위해서 본인인증이 필요합니다.</p>
						<p>안전한 서비스 이용 및 고객님의 개인정보 보호를 위해 본인 인증을 받으신 고객분만 이용이 가능합니다. 일시정지 이용을 위해서 본인확인 절차를 진행해주세요.</p>
					</div>
					<span class="c-form__title u-mt--40">본인인증</span>
					<div class="c-check-wrap">
						<div class="c-chk-wrap">
							<input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="auth_phone" type="radio" name="radCertify">
							<label class="c-label" for="auth_phone">
								<i class="c-icon c-icon--phone" aria-hidden="true"></i>휴대폰
							</label>
						</div>
						<div class="c-chk-wrap">
							<input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="auth_credit" type="radio" name="radCertify">
							<label class="c-label" for="auth_credit">
								<i class="c-icon c-icon--card" aria-hidden="true"></i>신용카드
							</label>
						</div>
						<div class="c-chk-wrap">
							<input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="auth_ipin" type="radio" name="radCertify">
							<label class="c-label" for="auth_ipin">
								<i class="c-icon c-icon--ipin" aria-hidden="true"></i>아이핀
							</label>
						</div>
					</div>
				</div>
			</div>

			<hr class="c-hr c-hr--type2">
			<ul class="c-text-list c-bullet c-bullet--dot">
				<li class="c-text-list__item u-co-gray">일시정지 시 <span class="u-co-red">월 3,850원</span>(부가세 포함/정지일에 따라 일할 계산)의 기본요금이 발생합니다.</li>
				<li class="c-text-list__item u-co-gray">일시정지 신청 후 일시정지 자동연장 시 명세서를 통하여 안내를 드리며, 일시정지 기간 만료 시 문자를 통해 안내해 드립니다.</li>
				<li class="c-text-list__item u-co-gray">사용중인 스폰서 유형에 따라 일시 정지 기간만큼 약정기간이 연장 적용되며, 일시정지 시 요금할인/월정액(사용중인 요금제의 기본료 및 정지 사용료)/무료 제공량은 일할계산되어 제공됩니다.</li>
			</ul>
		</div>
<input type="hidden" id="subStatus" name="subStatus" value="${subStatus}">
<input type="hidden" id="ctnStatus" name="ctnStatus" value=""/>
<input type="hidden" id="rsltInd" name="rsltInd" value=""/>
	</t:putAttribute>
</t:insertDefinition>