<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<div class="c-modal__content">
	<div class="c-modal__header">
		<!-- [2022-01-14] h2 태그 추가-->
		<h2 class="c-modal__title" id="ars-menu-_title">ARS 메뉴 안내</h2>
		<button class="c-button" data-dialog-close>
			<i class="c-icon c-icon--close" aria-hidden="true"></i> <span
				class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body service-guide">
		<div class="ars-menu-wrap">
			<p class="c-text c-text--fs20">ARS 서비스 및 단축번호를 알려드립니다.</p>
			<h3 class="c-heading c-heading--fs17 c-heading--bold">비가입자 고객님</h3>
			<ul class="c-list c-list--type1">
				<li class="c-list__item">
				    <strong class="c-list__title">분실접수</strong>
					<div class="c-list__row">
						<span class="c-list__num">1</span>
					</div>
				</li>
				<li class="c-list__item">
                    <strong class="c-list__title">가입문의</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">2</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">가입신청 취소</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">3</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">해피콜 요청</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">4</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">개통유심 변경</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">5</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">가입번호 조회</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">6</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">상담사 연결</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">0</span>
                    </div>
                </li>
			</ul>
			<h3 class="c-heading c-heading--fs17 c-heading--bold">가입자 고객님</h3>
			<h4 class="c-heading c-heading--fs15 c-heading--bold">요금조회 납부</h4>
			<ul class="c-list c-list--type1">
				<li class="c-list__item">
				    <strong class="c-list__title">청구요금 조회</strong>
					<div class="c-list__row">
						<span class="c-list__num">1</span>
						<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
						<span class="c-list__num">1</span>
					</div>
				</li>

				<li class="c-list__item">
                    <strong class="c-list__title">실시간 요금 조회</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">1</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">2</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">요금 즉시납부</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">1</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">3</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">납부방법 조회 및 변경</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">1</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">4</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">명세서 조회 및 변경</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">1</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">5</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">명세서 재발행</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">1</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">6</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">각종 내역서 발급</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">1</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">7</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">상담사 연결</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">0</span>
                    </div>
                </li>
			</ul>
			<h4 class="c-heading c-heading--fs15 c-heading--bold">요금제, 부가서비스, 사용량 조회</h4>
			<ul class="c-list c-list--type1">
				<li class="c-list__item">
                    <strong class="c-list__title">요금제 변경</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">2</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">1</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">부가서비스 신청 및 해지</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">2</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">2</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">사용량 조회</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">2</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">3</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">상담사 연결</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">0</span>
                    </div>
                </li>
			</ul>
			<h4 class="c-heading c-heading--fs15 c-heading--bold">소액결제, 번호변경</h4>
			<ul class="c-list c-list--type1">
				<li class="c-list__item">
                    <strong class="c-list__title">소액결제 내역조회</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">3</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">1</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">소액결제 한도변경</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">3</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">2</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">번호변경</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">3</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">3</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">상담사 연결</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">0</span>
                    </div>
                </li>
			</ul>
			<h4 class="c-heading c-heading--fs15 c-heading--bold">휴대폰 분실, 일시정지</h4>
			<ul class="c-list c-list--type1">
				<li class="c-list__item">
                    <strong class="c-list__title">분실정지 접수</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">4</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">1</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">분실정지 복구</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">4</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">2</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">일시정지 신청</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">4</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">3</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">일시정지 복구</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">4</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">4</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">상담사 연결</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">0</span>
                    </div>
                </li>
			</ul>
			<h4 class="c-heading c-heading--fs15 c-heading--bold">가입방법, 개통 조회 및 변경</h4>
			<ul class="c-list c-list--type1">
				<li class="c-list__item">
                    <strong class="c-list__title">해피콜 요청</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">5</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">1</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">개통유심 변경</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">5</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">2</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">개통 번호 문의</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">5</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">3</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">가입신청 취소</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">5</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">4</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">가입방법 문의</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">5</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">5</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">번호이동 문의</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">5</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">6</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">기타가입 문의</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">5</span>
                        <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                        <span class="c-list__num">7</span>
                    </div>
                </li>
                <li class="c-list__item">
                    <strong class="c-list__title">상담사 연결</strong>
                    <div class="c-list__row">
                        <span class="c-list__num">0</span>
                    </div>
                </li>
			</ul>
		</div>
	</div>
</div>
