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
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
		<!-- <script src="/resources/js/portal/vendor/flatpickr.min.js"></script> -->
		<script type="text/javascript" src="/resources/js/portal/mypage/pause/lostView.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page--title">
				<h2 class="c-page--title">분실신고</h2>
			</div>
			<div class="c-tabs c-tabs--type1">
				<div class="c-tabs__list">
					<a class="c-tabs__button is-active" id="tab1" href="#tab1" aria-current="page">분실신고</a>
					<a class="c-tabs__button" id="tab2" href="/mypage/lostCnlView.do">분실신고 해제</a>
				</div>
			</div>
			<div class="c-tabs__panel u-block">
				<div>
					<div class="c-row c-row--lg">
						<div class="c-heading-wrap">
							<h3 class="c-heading c-heading--fs20">조회할 회선을 선택해 주세요.</h3>
							<%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp" %>
						</div>
						<hr class="c-hr c-hr--title">
						<div class="c-form-wrap">
							<div class="c-form-group" role="group" aria-labelledby="formGroupA">
								<h3 class="c-group--head" id="formGroupA">분실 휴대폰 정보 등록</h3>
								<div class="c-form-row c-col2">
									<div class="c-form-field">
										<div class="c-form__input has-value">
											<input class="c-input flatpickr" id="lostDate" type="text" placeholder="YYYY-MM-DD" value="${toDay}" readonly>
											<label class="c-form__label" for="lostDate">분실신고 날짜</label>
										</div>
									</div>
									<div class="c-form-field">
										<div class="c-form__input u-mt--0 has-value">
											<input class="c-input" id="lostName" type="text" placeholder="고객명 입력" value="${searchVO.userName}" readonly>
											<label class="c-form__label" for="lostName">고객명</label>
										</div>
									</div>
								</div>
								<div class="c-form-row c-col2 u-mt--16">
									<div class="c-form-field">
										<div class="c-form__select u-mt--0">
											<select class="c-select" id="loseLocation" name="loseLocation">
												<option value="N" selected="selected">국내분실</option>
												<option value="Y">해외분실</option>
											</select>
											<label class="c-form__label" for="loseLocation">분실지역</label>
										</div>
									</div>
									<div class="c-form-field">
										<div class="c-form__select u-mt--0">
											<select class="c-select" id="loseType" name="loseType">
												<option value="A" selected="selected">모두분실(휴대폰+USIM)</option>
												<option value="H">휴대폰분실</option>
												<option value="U">USIM분실</option>
											</select>
											<label class="c-form__label" for="loseType">분실유형</label>
										</div>
									</div>
								</div>
								<div class="c-textarea-wrap">
									<label class="c-form__label c-hidden" for="loseCont">분실 내용 입력</label>
									<textarea class="c-textarea" placeholder="분실 내용을 입력해주세요. 분실 내용은 40자를 초과 할 수 없습니다." maxlength="40" id="loseCont" name="loseCont"></textarea>
									<div class="c-textarea-wrap__sub">
										<span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span> <span id="textAreaSbstSize">0/40</span>
									</div>
								</div>
								<div class="c-form-row c-col2 u-mt--16">
									<div class="c-form-field">
										<div class="c-form__select u-mt--0">
											<select class="c-select" id="guideYn" name="guideYn">
												<option value="Y" selected="selected">발신정지(착신가능)</option>
												<option value="N">발착신정지</option>
												<option value="C" disabled="disabled">정지없음</option>
											</select>
											<label class="c-form__label" for="guideYn">고객요청사항</label>
										</div>
									</div>
									<div class="c-form-field">
										<div class="c-form__input u-mt--0">
											<input class="c-input onlyNum" id="cntcTlphNo" name="cntcTlphNo" maxlength="11" type="number" placeholder="'-'없이 입력">
											<label class="c-form__label" for="cntcTlphNo">연락 가능한 전화번호</label>
										</div>
									</div>
								</div>
								<div class="c-form u-width--460">
									<div class="c-form-field">
										<div class="c-form__input has-safe">
											<input class="c-input onlyNum" id="strPwdInsert" type="password" placeholder="비밀번호를 숫자만 입력(4~8자)" aria-invalid="false" aria-describedby="msg1" name="strPwdInsert" maxlength="8">
											<label class="c-form__label" for="strPwdInsert">분실신고 해제 비밀번호</label>
										</div>
										<p class="c-form__text" id="msg1" style="display:none;"></p>
									</div>
								</div>
							</div>
						</div>
						<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
							<li class="c-text-list__item u-co-gray">해외에서 휴대폰 분실 후에 바로 분실 신고를 하지 않으시면, 타인 사용으로 로밍 요금이 과다하게 청구될 수 있으니 주의 바랍니다.</li>
							<li class="c-text-list__item u-co-gray">가입 된 약정유형에 따라 정지 기간만큼 약정기간이 연장되고, 요금할인은 일할 계산되어 제공됩니다.
								<span class="c-bullet c-bullet--fyr u-co-sub-4">단말기 할부구매고객은 정지와 상관없이 매 월 할부금이 청구 됩니다.</span>
							</li>
							<li class="c-text-list__item u-co-gray">일시/분실 정지 시 기본료, 기본제공량이 일할 계산되며 제공 혜택 초과 시 추가 요금이 발생됩니다.</li>
						</ul>
						<div class="c-button-wrap u-mt--56">
							<button class="c-button c-button--lg c-button--primary c-button--w460" id="lostBtn" disabled="disabled">분실신고</button>
						</div>
						<h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
							<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
							<span class="u-ml--4">알려드립니다</span>
						</h3>
						<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
							<li class="c-text-list__item u-co-gray">분실정지 횟수는 월 10회로 제한되며, 1회 7일까지 발신정지 가능합니다. (7일 이후 통보 없이 자동 발착신 정지로 변경됨)</li>
							<li class="c-text-list__item u-co-gray">분실정지는 일시 정지 가능 기간(년 최대 180일)에 포함되지 않으며, 분실로 인한 발착신 정지가 180일 이상 유지될 경우 이용중단 및 직권해지 될 수 있습니다.</li>
							<li class="c-text-list__item u-co-gray">분실복구 신청을 하신 후에는 반드시 휴대폰의 전원을 껐다 켜야 분실복구가 완료되어 휴대폰 사용이 가능합니다.</li>
							<li class="c-text-list__item u-co-gray">발신만 정지 할 경우, 수신자 부담 전화를 받으면 요금이 발생될 수 있습니다.</li>
							<li class="c-text-list__item u-co-gray">분실 정지 시, <span class="u-co-red">월 3,850원</span>(부가세 포함)의 정지기본요금이 발생합니다.</li>
						</ul>
					</div>
				</div>
			</div>
		</div>

	</t:putAttribute>
</t:insertDefinition>