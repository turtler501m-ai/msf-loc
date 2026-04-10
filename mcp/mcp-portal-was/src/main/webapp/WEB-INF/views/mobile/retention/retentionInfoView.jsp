<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<div class="c-modal__content">
	<div class="c-modal__header">
		<h1 class="c-modal__title" id="request-info-title">재약정/우수기변
			신청 안내</h1>
		<button class="c-button" data-dialog-close>
			<i class="c-icon c-icon--close" aria-hidden="true"></i> <span
				class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body">
		<ul class="c-step-list">
			<li class="c-step-list__step">
				<dl>
					<dt class="u-mt--12">
						<strong class="c-heading c-heading--type7 u-fw--medium">요금 할인 이어가기!</strong>
						<p class="c-text c-text--type3 u-mt--4 u-co-gray-9">최초 약정 기간 이상 사용한 고객이시라면</p>
					</dt>
					<dd>
						<img src="/resources/images/mobile/common/ico_sale_tag.svg" alt="">
						<p class="c-text c-text--type4 u-mt--8 u-co-sub-4">무료통화 30분 X 6개월</p>
						<div class="c-button-wrap">
							<a class="c-button c-button--full c-button--mint c-button--h48" href="/m/mypage/reSpnsrPlcyDc.do">요금할인 재약정 신청하기</a>
						</div>
					</dd>
				</dl>
			</li>
			<li class="c-step-list__step">
				<dl>
					<dt class="u-mt--12">
						<strong class="c-heading c-heading--type7 u-fw--medium">새 폰 바꾸기!</strong>
						<p class="c-text c-text--type3 u-mt--4 u-co-gray-9">kt M모바일 21개월 이상 사용한 고객이시라면</p>
					</dt>
					<dd>
						<img src="/resources/images/mobile/common/ico_new_phone.svg" alt="">
						<p class="c-text c-text--type4 u-mt--8 u-co-sub-4">무료통화 30분 X 6개월</p>
						<div class="c-button-wrap">
							<a class="c-button c-button--full c-button--mint c-button--h48" href="/m/product/phone/phoneList.do">휴대폰 보러가기</a>
						</div>
					</dd>
				</dl>
			</li>
		</ul>
		<h3 class="c-heading c-heading--type3 u-mt--0 u-mb--0">문의전화</h3>
		<div class="c-button-wrap c-button-wrap--column u-mt--24">
			<a class="c-button c-button--md c-button--white" href="tel:114"> 
				<i class="c-icon c-icon--mint-mobile" aria-hidden="true"></i> 
				<span>kt M모바일 휴대폰</span> 
				<span class="c-button__sub">114
					<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
				</span>
			</a> 
			<a class="c-button c-button--md c-button--white" href="tel:1899-5000"> 
				<i class="c-icon c-icon--mint-call" aria-hidden="true"></i> 
				<span>타사 휴대폰 및 유선전화</span> 
				<span class="c-button__sub">1899-5000
					<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
				</span>
			</a>
		</div>
		<p class="c-bullet c-bullet--dot u-co-gray u-mt--16">고객센터 운영시간: 09~12시/13~18시</p>
		<p class="c-bullet c-bullet--dot u-co-gray">재약정/우수기변 혜택은 변경될 수 있습니다.</p>
	</div>
</div>
