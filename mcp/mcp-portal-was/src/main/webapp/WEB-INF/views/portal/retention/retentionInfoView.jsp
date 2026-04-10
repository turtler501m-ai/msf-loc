<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<div class="c-modal__content">
  <div class="c-modal__header">
    <h2 class="c-modal__title" id="find-cvs-modal__title">재약정/우수기변 신청 안내</h2>
    <button class="c-button" data-dialog-close>
      <i class="c-icon c-icon--close" aria-hidden="true"></i>
      <span class="c-hidden">팝업닫기</span>
    </button>
  </div>
  <div class="c-modal__body">
    <ul class="c-step-list request-guide">
      <li class="c-step-list__item">
        <span class="c-step-list__label">STEP 1</span>
        <strong class="c-step-list__title">요금 할인 이어가기!</strong>
        <p class="c-step-list__text u-co-gray">최초 약정 기간 이상 사용한 고객이시라면</p>
        <i class="c-icon c-icon--sale" aria-hidden="true"></i>
        <p class="c-step-list__text u-co-sub-4">무료통화 30분 X 6개월</p>
        <div class="c-button-wrap u-mt--24">
          <a class="c-button c-button-round--md c-button--mint c-button--w460" href="/mypage/reSpnsrPlcyDc.do">요금할인 재약정 신청하기</a>
        </div>
      </li>
      <li class="c-step-list__item">
        <span class="c-step-list__label">STEP 2</span>
        <strong class="c-step-list__title">새 폰 바꾸기!</strong>
        <p class="c-step-list__text u-co-gray">kt M모바일 21개월 이상 사용한 고객이시라면</p>
        <i class="c-icon c-icon--new-phone" aria-hidden="true"></i>
        <p class="c-step-list__text u-co-sub-4">무료통화 30분 X 6개월</p>
        <div class="c-button-wrap u-mt--24">
          <a class="c-button c-button-round--md c-button--mint c-button--w460" href="/product/phone/phoneList.do">휴대폰 보러가기</a>
        </div>
      </li>
    </ul>
    <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">문의전화</h3>
    <div class="customer-service customer-service--type2 u-mt--24">
      <div class="customer-service__item">
        <i class="c-icon c-icon--ars-phone" aria-hidden="true"></i>
        <strong class="customer-service__title">kt M모바일 휴대폰</strong>
        <div class="customer-service__info">
          <b class="c-text c-text--fs22 u-co-point-4">114</b>
        </div>
      </div>
      <div class="customer-service__item">
        <i class="c-icon c-icon--ars-tel" aria-hidden="true"></i>
        <strong class="customer-service__title">타사 휴대폰 및 유선전화</strong>
        <div class="customer-service__info">
          <b class="c-text c-text--fs22 u-co-point-3">1899-5000</b>
        </div>
      </div>
    </div>
    <p class="c-bullet c-bullet--dot u-co-gray u-mt--16">고객센터 운영시간: 09~12시/13~18시</p>
    <p class="c-bullet c-bullet--fyr u-co-gray">재약정/우수기변 혜택은 변경될 수 있습니다.</p>
  </div>
</div>
