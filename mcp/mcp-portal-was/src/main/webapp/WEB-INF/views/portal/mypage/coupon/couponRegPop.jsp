<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<script type="text/javascript" src="/resources/js/portal/mypage/coupon/couponRegPop.js"></script>

<div class="c-modal__content">
	<div class="c-modal__header">
		<h2 class="c-modal__title" id="modalCouponAddTitle">쿠폰 등록</h2>
		<button class="c-button" data-dialog-close id="popClose">
			<i class="c-icon c-icon--close" aria-hidden="true"></i>
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body">
		<div class="c-form c-form--type2">
			<label class="c-label c-hidden" for="coupSerialNo">쿠폰번호</label>
			<div class="c-input-wrap">
				<input class="c-input c-input--type2" id="coupSerialNo" name="coupSerialNo" type="text" placeholder="발급된 쿠폰번호를 입력해주세요." maxlength="20" autocomplete="off">
				<button class="c-button c-button-round--md c-button--primary u-ml--8" id="copNumSrchBtn" disabled="disabled">조회</button>
			</div>
		</div>
		<p class="c-bullet c-bullet--dot u-co-gray u-mt--12">유효기간이 지난 쿠폰의 경우 등록되지 않습니다.</p>
		<div id="divArea"></div>
	</div>
	<div class="c-modal__footer">
		<div class="c-button-wrap u-mt--0">
			<button class="c-button c-button--lg c-button--primary c-button--w460" 	disabled="disabled" id="regBtn">등록</button>
		</div>
	</div>
</div>
<input type="hidden" id="ncn" name="ncn" value="${ncn}">
<input type="hidden" id="mobileNo" name="mobileNo" value="${mobileNo}">
<input type="hidden" id="regChk" name="" value="">
