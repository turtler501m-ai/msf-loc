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
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
		<script type="text/javascript" src="/resources/js/mobile/mypage/pause/lostView.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					분실신고
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-tabs c-tabs--type2" data-tab-active="0">
				<div class="c-tabs__list c-expand sticky-header" data-tab-list="">
					<button class="c-tabs__button" data-tab-header="#tabB1-panel">분실신고</button>
					<button class="c-tabs__button" data-tab-header="#tabB2-panel" id="lostCnlBtn">분실신고 해제</button>
				</div>
				<div class="c-tabs__panel" id="tabB1-panel">
					<h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
					<%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>
					<div class="c-form u-mt--40">
						<div class="c-form__title u-mt--0 u-fw--medium">분실 휴대폰 정보 등록</div>
						<div class="c-form__input">
							<input class="c-input" id="lostDate" type="date" placeholder="분실신고 날짜" max="9999-12-31" value="${toDay}" readonly>
							<label class="c-form__label" for="lostDate">분실신고 날짜</label>
						</div>
						<div class="c-form__input">
							<input class="c-input" id="lostName" type="text" placeholder="고객명" value="${searchVO.userName}" readonly>
							<label class="c-form__label" for="lostName">고객명</label>
						</div>

						<div class="c-form__select has-value">
							<select class="c-select" id="loseLocation">
								<option value="N" selected="selected">국내분실</option>
								<option value="Y">해외분실</option>
							</select>
							<label class="c-form__label">분실지역</label>
						</div>

						<div class="c-form__select has-value">
							<select class="c-select" id="loseType">
								<option value="A" selected="selected">모두분실(휴대폰+USIM)</option>
								<option value="H">휴대폰분실</option>
								<option value="U">USIM분실</option>
							</select>
							<label class="c-form__label">분실유형</label>
						</div>

						<div class="c-form__input">
							<input class="c-input onlyNum" type="number" placeholder="연락 가능한 전화번호" id="cntcTlphNo" name="cntcTlphNo" maxlength="11">
							<label class="c-form__label" for="cntcTlphNo">연락 가능한 전화번호</label>
						</div>

						<div class="c-textarea-wrap">
							<textarea class="c-textarea" placeholder="분실 내용을 입력해주세요. 분실 내용은 40자를 초과할 수 없습니다." maxlength="40" id="loseCont" name="loseCont"></textarea>
							<div class="c-textarea-wrap__sub">
								<span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span> <span id="textAreaSbstSize">0/40</span>
							</div>
						</div>

						<div class="c-form__select has-value">
							<select class="c-select" id="guideYn">
								<option value="Y" selected="selected">발신정지(착신가능)</option>
								<option value="N">발착신정지</option>
								<option value="C" disabled="disabled">정지없음</option>
							</select>
							<label class="c-form__label">고객요청사항</label>
						</div>

						<div class="c-form__input">
							<input class="c-input onlyNum" type="password" placeholder="비밀번호를 숫자만 입력(4~8자)" id="strPwdInsert" name="strPwdInsert" maxlength="8">
							<label class="c-form__label" for="strPwdInsert">분실신고 해제 비밀번호</label>
						</div>
					</div>

					<hr class="c-hr c-hr--type2">
					<ul class="c-text-list c-bullet c-bullet--dot">
						<li class="c-text-list__item u-co-gray">해외에서 휴대폰 분실 후에 바로 분실 신고를 하지 않으시면, 타인 사용으로 로밍 요금이 과다하게 청구될 수 있으니 주의 바랍니다.</li>
						<li class="c-text-list__item u-co-gray">가입 된 약정유형에 따라 정지 기간만큼 약정기간이 연장되고, 요금할인은 일할 계산되어 제공됩니다.</li>
						<li class="c-text-list__item u-co-gray">단말기 할부구매고객은 정지와 상관없이 매 월 할부금이 청구 됩니다.</li>
						<li class="c-text-list__item u-co-gray">일시 /분실 정지 시 기본료, 기본제공량이 일할 계산되며 제공 혜택 초과 시 추가 요금이 발생됩니다.</li>
					</ul>

					<div class="c-button-wrap">
						<button class="c-button c-button--full c-button--primary" id="lostBtn" disabled="disabled">분실신고</button>
					</div>

					<hr class="c-hr c-hr--type2">
					<b class="c-text c-text--type3">
						<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
					</b>
					<ul class="c-text-list c-bullet c-bullet--dot">
						<li class="c-text-list__item u-co-gray">분실정지 횟수는 월 10회로 제한되며,	1회 7일까지 발신정지 가능합니다. (7일 이후 통보 없이 자동 발착신 정지로 변경됨)</li>
						<li class="c-text-list__item u-co-gray">분실정지는 일시 정지 가능 기간(년 최대 180일)에 포함되지 않으며, 분실로 인한 발착신 정지가 180일 이상 유지될 경우 이용중단 및 직권해지 될 수 있습니다.</li>
						<li class="c-text-list__item u-co-gray">분실복구 신청을 하신 후에는 반드시 휴대폰의 전원을 껐다 켜야 분실복구가 완료되어 휴대폰 사용이 가능합니다.</li>
						<li class="c-text-list__item u-co-gray">발신만 정지 할 경우, 수신자 부담 전화를 받으면 요금이 발생될 수 있습니다.</li>
						<li class="c-text-list__item u-co-gray">분실 정지 시, <span class="u-co-red">월 3,850원</span>(부가세 포함)의 정지기본요금이 발생합니다.</li>
					</ul>
				</div>
				<div class="c-tabs__panel" id="tabB2-panel"></div>
			</div>
		</div>

	</t:putAttribute>
</t:insertDefinition>