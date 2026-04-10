<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<script type="text/javascript" src="/resources/js/mobile/telCounsel/telCounselView.js"></script>
<div class="c-modal__content">
	<div class="c-modal__header">
		<h1 class="c-modal__title" id="join-counseling-title">법인 가입 상담</h1>
		<button class="c-button" data-dialog-close id="telCounselCloseBtn">
			<i class="c-icon c-icon--close" aria-hidden="true"></i> 
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body">
		<div class="u-mt--24">
			<i class="c-icon c-icon--add-service-gray" aria-hidden="true"></i>
			<h3 class="c-heading c-heading--type1 u-mt--24 u-fw--medium">
				법인 고객님들의 <br>가입을 편리하게 도와드립니다!
			</h3>
			<hr class="c-hr c-hr--type2 u-mt--0">
			<p class="c-text c-text--type2 u-co-gray">
				아래에 회사 법인명, 연락처, 사업자 등록번호 등 귀하 법인의 기본 정보를 남겨주시면 저희 상담원이 전화를 드려 가입
				진행을 도와드리겠습니다. <br>(수집 정보는 상담 외 가입 진행 시 법인 신용등급 평가 등에 사용 될 수
				있습니다.)
			</p>
			<p class="c-bullet c-bullet--fyr u-co-sub-4 u-mt--24">가입을 안하실 경우
				수집된 정보는 1년 후 자동 파기됩니다.</p>
			<div class="c-form u-mt--48">
				<span class="c-form__title sr-only" id="inpInfo">상담정보 입력</span>
				<div class="c-form__group" role="group" aria-labelledby="inpInfo">
					<div class="c-form__input input has-value">
						<input class="c-input" id="counselNm" type="text" name="counselNm"  placeholder="법인명 입력" maxlength="30" title="법인명을" onkeyup="nextChk();">
						
						<label class="c-form__label" for="counselNm">법인명</label>
					</div>
					<div class="c-form__input input has-value">
						<input class="c-input" id="juridicalNumber" type="number" name="juridicalNumber" pattern="d*" placeholder="'-'없이 입력" maxlength="10" title="사업자 등록번호를" onkeyup="nextChk();"> 
						<label class="c-form__label" for="juridicalNumber">사업자 등록번호</label>
					</div>
					<div class="c-form__input input has-value">
						<input class="c-input" id="agentNm" type="text"  name="agentNm" placeholder="대표자(대리인) 이름 입력" maxlength="30" title="대표자(대리인) 이름을" onkeyup="nextChk();"> 
						<label class="c-form__label" for="agentNm">대표자(대리인) 이름</label>
					</div>
					<div class="c-form__input input has-value">
						<input class="c-input" id="mobileNo" type="number" name="mobileNo"  maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" title="연락처를" onkeyup="nextChk();">
						 <label class="c-form__label" for="mobileNo">연락처</label>
					</div>
					<div class="c-form__select has-value">
						<select class="c-select" id ="operType" name="operType" disabled >
							<option>선택</option>
							<option selected>유심요금제</option>
						</select> 
						<label class="c-form__label">가입유형</label>
					</div>
					<div class="c-form__select has value" style="display:none;">
					<select class="c-select" id ="counselCtg" name="counselCtg" disabled >
							<option value="02">유심요금제</option>
						</select> 
						<label class="c-form__label">가입유형</label>
					</div>
				</div>
			</div>
			<div class="c-form">
				<span class="c-form__title" id="inpSecurity">보안문자 입력</span>
				<div class="c-form__group" role="group" aria-labelledby="inpSecurity">
					<p class="c-text c-text--type3 u-co-gray">아래 보안문자 이미지의 숫자를 보이는 대로 입력해주세요.</p>
					<div class="security-box" id="catpcha" align="center">보안문자 영역</div>
					<div class="u-ta-center">
						<button class="c-button c-button--sm c-button--white u-mr--8" id="soundOnKor">음성듣기</button>
						<button>
							<i class="c-icon c-icon--refresh" aria-hidden="true" id="reLoad"></i>
						</button>
					</div>
					<div class="c-input-wrap u-mt--16">
						<input class="c-input" type="number" placeholder="보안문자 입력" id="answer" name="answer" maxlength="5" title="보안문자를" onkeyup="nextChk();">
					</div> 
				</div>
			</div>
			<div class="c-accordion c-accordion--type5 u-mt--40">
				<div class="c-accordion__box" id="accordion5">
					<div class="c-accordion__item">
						<div class="c-accordion__head">
							<div class="c-accordion__check">
								<input class="c-checkbox c-checkbox--type5" id="chkDS"
									type="checkbox" name="chkDS" onchange="nextChk();">
									 <label class="c-label" for="chkDS"> 개인정보 수집 및 이용에 대한 동의 
									 <span class="u-co-gray">(필수)</span>
								</label>
							</div>
							<button class="c-accordion__button u-ta-right" type="button"
								aria-expanded="false" data-acc-header="#chkDSP1" id="chkDS1">
								<div class="c-accordion__trigger"></div>
							</button>
						</div>
						<div class="c-accordion__panel expand" id="chkDSP1">
							<div class="c-accordion__inside">
								<ul class="c-text-list c-bullet c-bullet--number">
									<li class="c-text-list__item u-co-gray">수집 이용의 목적
										<p class="c-bullet c-bullet--dot u-co-gray">서비스 상품 및 가입안내,
											법인 신용평가 등급 등의 가입 상담을 위한 정보 제공</p>
									</li>
									<li class="c-text-list__item u-co-gray">수집항목
										<p class="c-bullet c-bullet--dot u-co-gray">법인명, 사업자 등록번호,
											대표자(대리인) 이름, 연락처, 가입 유형</p>
									</li>
									<li class="c-text-list__item u-co-gray">이용 및 보유기간
										<p class="c-bullet c-bullet--dot u-co-gray">동의를 통한 수집 일시
											기준 1년 후 자동 파기</p>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<b class="c-text c-text--type3 u-block u-mt--32"> <i
				class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
			</b>
			<p class="c-text c-text--type3 u-co-gray u-mt--16">아래 서류가 구비되어 있을
				경우 더욱 원활한 상담과 빠른 가입이 진행됩니다.</p>
			<p class="c-bullet c-bullet--dot u-co-gray">법인인감증명, 사업자등록증, 대표자
				혹은 대리인 신분증 사본(대리인일 경우 대리인 신분증 및 재직증명서), 표준 재무재표 (최근 3개년), 납부내역증명 (최근
				3개월)</p>
			<div class="c-button-wrap u-mt--48">
				<button class="c-button c-button--full c-button--primary is-disabled" id="telCounselConfirm" onclick="telCounselAjax();">법인 가입 상담하기</button>
			</div>
		</div>
	</div>
</div>