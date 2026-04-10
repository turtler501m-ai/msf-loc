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
		<script src="/resources/js/portal/vendor/flatpickr.min.js"></script>
		<script type="text/javascript" src="/resources/js/portal/mypage/pause/pauseApply.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page--title">
				<h2 class="c-page--title">일시정지</h2>
			</div>
			<div class="c-row c-row--lg">
				<h3 class="c-hidden">일시정지 신청</h3>
				<div class="c-form-wrap">
					<div class="c-form-row c-col2">
						<div class="c-form-field">
							<div class="c-form__input u-mt--0 has-value">
								<input class="c-input" id="inpName" type="text" placeholder="고객명 입력" value="${searchVO.userName}" readonly>
								<label class="c-form__label" for="inpName">고객명</label>
							</div>
						</div>
						<div class="c-form-field u-width--460">
							<div class="c-form__input-group is-readonly u-mt--0">
								<div class="c-form__input c-form__input-division has-value">
									<input class="c-input--div3" id="inpPhoneNum1" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰번호 첫자리" value="${fn:substring(searchVO.ctn,0,3)}" readonly>
									<span>-</span>
									<input class="c-input--div3" id="inpPhoneNum2" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 중간자리" value="${fn:substring(searchVO.ctn,4,8)}" readonly>
									<span>-</span>
									<input class="c-input--div3" id="inpPhoneNum3" type="text" maxlength="4" placeholder="숫자입력" title="휴대폰번호 뒷자리" value="${fn:substring(searchVO.ctn,9,13)}" readonly>
									<label class="c-form__label" for="inpPhoneNum">휴대폰번호</label>
									<input type="hidden" id="ncn" name="ncn" value="${searchVO.ncn}">
								</div>
							</div>
						</div>
					</div>
					<div class="c-form-group u-mt--48" role="group" aira-labelledby="formGroupA">
						<a href="#n" role="button" data-tpbox="#pauseTooltip" aria-describedby="#pauseTooltip__title">
							<span class="c-group--head u-inline-block u-mb--0" id="formGroupA">일시정지 기간 설정</span>
							<i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
						</a>
						<div class="c-tooltip-box is-active" id="pauseTooltip" role="tooltip" style="left: 100px">
							<div class="c-tooltip-box__title c-hidden" id="pauseTooltip__title">일시정지 기간 설정 상세설명</div>
							<div class="c-tooltip-box__content">
								<ul class="c-text-list c-bullet c-bullet--dot">
									<li class="c-text-list__item">일시정지는 1회 최대90일, 연2회신청 가능합니다.</li>
									<li class="c-text-list__item">일시정지 시작일을 오늘 이후 일자로 신청할 경우 일시정지 기간 수정 및 해제는 고객센터를 통해서만 가능합니다.</li>
									<li class="c-text-list__item">고객센터를 통한 일시정지는 복구는 명의자 본인만 가능합니다.(미성년자일 경우 법정대리인 가능)</li>
									<li class="c-text-list__item">일시정지 시 월정액은 3,850원(VAT포함)입니다.</li>
								</ul>
							</div>
						</div>
						<div class="c-form-row c-col2">
							<div class="c-form-field c-form-field--datepicker">
								<div class="c-form__input">
									<input class="c-input flatpickr" id="cpStartDt" type="text" placeholder="YYYY-MM-DD" readonly value="${toDay}">
									<label class="c-form__label" for="cpStartDt">시작일</label>
									<span class="c-button c-button--icon">
										<span class="c-hidden">날짜선택</span>
										<i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
									</span>
								</div>
							</div>
							<div class="c-form-field c-form-field--datepicker">
								<div class="c-form__input">
									<input class="c-input flatpickr" id="cpEndDt" type="text" placeholder="YYYY-MM-DD" readonly value="${endDay}">
									<label class="c-form__label" for="inpEndDate">종료일</label>
									<span class="c-button c-button--icon"> <span class="c-hidden">날짜선택</span>
										<i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="c-form-group u-mt--48" role="group" aira-labelledby="formGroupC">
						<span class="c-group--head" id="formGroupC">정지유형</span>
						<div class="c-checktype-row">
							<input class="c-radio" id="reasonCode1" type="radio" name="reasonCode" checked value="01">
							<label class="c-label" for="reasonCode1">발신정지<span class="c-form__sub">(착신가능)</span></label>
							<input class="c-radio" id="reasonCode2" type="radio" name="reasonCode" value="03">
							<label class="c-label" for="reasonCode2">착발신정지</label>
						</div>
					</div>
					<div class="c-form-group u-mt--48" role="group" aira-labelledby="formGroupB">
						<div class="c-group--head" id="formGroupB">
							<span>일시정지 해제 비밀번호<span class="c-form__sub">(선택)</span></span>
						</div>
						<div class="c-form-row c-col2 u-width--460">
							<div class="c-form-field">
								<!-- 에러 발생 시 .has-error(aria-invalid="true") validation 통과 시 .has-safe(aria-invalid="false") 클래스를 사용-->
								<div class="c-form__input">
									<input class="c-input onlyNum" id="cpPwdInsert" type="password" placeholder="비밀번호를 입력(4~8자)" aria-invalid="true" aria-describedby="msg1" maxlength="8">
									<label class="c-form__label" for="cpPwdInsert">비밀번호</label>
								</div>
								<p class="c-form__text" id="msg1" style="display:none;"></p>
							</div>
						</div>
						<div class="c-form">
							<div class="c-input-wrap u-mt--48 u-width--460">
								<label class="c-label" for="userMemo">메모</label>
								<input class="c-input" type="text" placeholder="메모를 입력해주세요." id="userMemo" name="userMemo">
							</div>
						</div>
					</div>
				</div>
				<div class="c-button-wrap u-mt--56">
					<a class="c-button c-button--lg c-button--gray u-width--220" href="javascript:;" id="canBtn">취소</a>
					<button class="c-button c-button--lg c-button--primary u-width--220" id="apply">신청</button>
				</div>
				<h3 class="c-flex c-heading c-heading--fs15 u-mt--48">
					<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
					<span class="u-ml--4">알려드립니다</span>
				</h3>
			<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
				<li class="c-text-list__item u-co-gray">일시정지 시 <span class="u-co-red">월 3,850원</span>(부가세 포함/정지일에 따라 일할 계산)의 기본요금이 발생합니다.</li>
          		<li class="c-text-list__item u-co-gray">발신정지는 30일만 가능하며, 30일 이후에는 착/발신 정지로 자동 전환됩니다.</li>
          		<li class="c-text-list__item u-co-gray">일시정지 기간 종료 시 휴대폰 이용 중 상태로 자동 복구되며(문자 안내), 일시정지 이전 사용 요금제 기준으로 이용요금이 부과됩니다.</li>
          		<li class="c-text-list__item u-co-gray">분실정지 일수는 일시정지 신청가능일수(90일)에 포함되지 않습니다.</li>
          		<li class="c-text-list__item u-co-gray">단말기 분할상환 금액이 남은 경우 일시정지 기간에도 청구됩니다.</li>
          		<li class="c-text-list__item u-co-gray">군입대, 장기해외체류 등 특별한 경우에 해당할 경우 최대 3년까지 일시정지가 가능합니다. 장기 일시정지의 경우 고객센터(1899-5000)를 통해 신청을 부탁드립니다.</li>
        	</ul>
		</div>
	</div>
	</t:putAttribute>
</t:insertDefinition>