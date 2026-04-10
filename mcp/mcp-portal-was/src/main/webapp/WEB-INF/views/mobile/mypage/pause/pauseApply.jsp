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
		<script type="text/javascript" src="/resources/js/mobile/mypage/pause/pauseApply.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					일시정지
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<h3 class="c-heading c-heading--type5 u-mt--32">일시정지 신청</h3>
			<hr class="c-hr c-hr--type2 u-mb--0">
			<div class="c-form u-mt--40">
				<div class="c-form__title sr-only u-mt--0 u-mb--0">고객정보</div>
				<div class="c-form__input">
					<input class="c-input" id="inpName" type="text" placeholder="고객명" value="${searchVO.userName}" disabled>
					<label class="c-form__label" for="inpName">고객명</label>
				</div>
				<div class="c-form__input">
					<input class="c-input" id="inpPhoneNum" type="text" placeholder="휴대폰번호" value="${searchVO.ctn}" disabled>
					<input type="hidden" id="ncn" name="ncn"  value="${searchVO.ncn}">
					<label class="c-form__label" for="ncn">휴대폰번호</label>
				</div>
			</div>
			<div class="c-form">
				<div class="c-form__title u-block">
					<a data-tpbox="#tip1">일시정지 기간 설정
						<i class="c-icon c-icon--tooltip" aria-hidden="true"></i>
					</a>
					<div class="c-tooltip-box" id="tip1">
						<div class="c-tooltip-box__title" id="inpPeriodG">일시정지 기간 설정</div>
						<div class="c-tooltip-box__content">
							<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
								<li class="c-text-list__item u-co-gray">일시정지는 1회 최대90일, 연2회 신청 가능합니다.</li>
								<li class="c-text-list__item u-co-gray">일시정지 시작일을 오늘 이후 일자로 신청할 경우 일시정지 기간 수정 및 해제는 고객센터를 통해서만 가능합니다.</li>
								<li class="c-text-list__item u-co-gray">고객센터를 통한 일시정지는 복구는 명의자 본인만 가능합니다.(미성년자일 경우 법정대리인 가능)</li>
								<li class="c-text-list__item u-co-gray">일시정지 시 월정액은 3,850원(VAT포함)입니다.</li>
							</ul>
						</div>
						<button class="c-tooltip-box__close" data-tpbox-close>
							<span class="c-hidden">툴팁닫기</span>
						</button>
					</div>
				</div>

				<div class="c-input-wrap">
					<input class="c-input" type="date" placeholder="시작일" title="날짜입력" id="cpStartDt" max="9999-12-31" value="${toDay}">
					<button class="c-button c-button--calendar">
						<i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
					</button>
				</div>

				<div class="c-input-wrap">
					<input class="c-input" type="date" placeholder="종료일" title="날짜입력" id="cpEndDt" max="9999-12-31" value="${endDay}">
					<button class="c-button c-button--calendar">
						<i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
					</button>
				</div>
			</div>
			<div class="c-form u-mt--40">
				<div class="c-form__title sr-only u-mt--0 u-mb--0">정지유형</div>
				<div class="c-form__select has-value">
					<select class="c-select" id="reasonCode">
						<option value="01">발신정지(착신가능)</option>
						<option value="03">착발신정지</option>
					</select>
					<label class="c-form__label">정지유형</label>
				</div>
			</div>
			<div class="c-form">
				<div class="c-form__title u-mt--40">
					일시정지 해제 비밀번호<span class="fs-14 u-fw--regular u-co-gray">(선택)</span>
				</div>
				<input class="c-input onlyNum" type="password" placeholder="비밀번호를 입력(4~8자)" title="비밀번호 입력" id="cpPwdInsert" maxlength="8">
			</div>
			<div class="c-form u-mt--48">
            	메모<span class="fs-14 u-fw--regular u-co-gray">(선택)</span>
	            <div class="c-textarea-wrap">
	              <textarea class="c-textarea" id="userMemo" name="userMemo" placeholder="메모내용은 40자를 초과 할 수 없습니다" maxlength="40"></textarea>
	            </div>
	          </div>

			<div class="c-button-wrap u-mt--48">
				<button class="c-button c-button--full c-button--gray" id="canBtn">취소</button>
				<button class="c-button c-button--full c-button--primary" id="apply">신청</button>
			</div>
			<hr class="c-hr c-hr--type2 u-mt--40">
			<b class="c-text c-text--type3">
				<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
			</b>
			<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
				<li class="c-text-list__item u-co-gray">일시정지 시 <span class="u-co-red">월 3,850원</span>(부가세 포함/정지일에 따라 일할 계산)의 기본요금이 발생합니다.</li>
				<li class="c-text-list__item u-co-gray">발신정지는 30일만 가능하며, 30일 이후에는 착/발신 정지로 자동 전환됩니다.</li>
				<li class="c-text-list__item u-co-gray">일시정지 기가 종료 시 휴대폰 이용 중 상태로 자동 복구되며(문자안내), 일시정지 이전 사용 요금제 기준으로 이용요금이 부과됩니다.</li>
				<li class="c-text-list__item u-co-gray">분실정지 일수는 일시정지 신청가능일수(90일)에 포함되지 않습니다.</li>
				<li class="c-text-list__item u-co-gray">단말기 분할상환 금액이 남은 경우 일시정지 기간에도 청구됩니다.</li>
				<li class="c-text-list__item u-co-gray">군입대, 장기해외체류 등 특별한 경우에 해당할 경우 최대 3년까지 일시정지가 가능합니다.</li>
				<li class="c-text-list__item u-co-gray">장기 일시정지의 경우 고객센터(1899-5000)를 통해 신청을 부탁드립니다.</li>
			</ul>
		</div>

	</t:putAttribute>
</t:insertDefinition>