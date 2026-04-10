<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<script type="text/javascript" src="/resources/js/mobile/mypage/coupon/couponRegPop.js"></script>

<div class="c-modal__content">
	<div class="c-modal__header">
		<h1 class="c-modal__title" id="coupon-regis-title">쿠폰등록</h1>
		<button class="c-button" data-dialog-close id="popClose">
			<i class="c-icon c-icon--close" aria-hidden="true"></i>
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body">
		<div class="c-form">
			<div class="c-form__input u-mt--32">
				<input class="c-input" id="coupSerialNo" name="coupSerialNo" type="text" placeholder="쿠폰번호(숫자만)를 입력해 주세요." maxlength="20" autocomplete="off">
				<label class="c-form__label" for="coupSerialNo">쿠폰 번호</label>
				<button class="c-button c-button--sm c-button--underline" id="copNumSrchBtn" disabled="disabled">조회</button>
			</div>
		</div>
		<div id="divArea"></div>
		<div class="c-button-wrap u-mt--48">
			<button class="c-button c-button--full c-button--primary" disabled="disabled" id="regBtn">등록</button>
		</div>
		<hr class="c-hr c-hr--type2 u-mt--40">
		<ul class="c-text-list c-bullet c-bullet--dot">
			<li class="c-text-list__item u-co-gray u-mt--0">유효기간이 지난 쿠폰의 경우 등록되지 않습니다.</li>
		</ul>
	</div>
</div>
<input type="hidden" id="ncn" name="ncn" value="${ncn}">
<input type="hidden" id="mobileNo" name="mobileNo" value="${mobileNo}">
<input type="hidden" id="regChk" name="" value="">
