<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/mobile/appForm/eSimView.js"></script>
        <script type="text/javascript" src="/resourc/mobile/appForm/appFormOcr.js?version=24.01.11"></script>
	</t:putAttribute>
	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					휴대폰 EID 등록
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<h4 class="c-heading c-heading--type5">휴대폰정보 입력</h4>
			<hr class="c-hr c-hr--type2">
			<div class="c-box c-box--type1 u-pa--16">
				<ul class="c-text-list c-bullet c-bullet--dot">
					<li class="c-text-list__item u-co-gray u-mt--0">eSIM 개통을 위해서는 EID등록이 선행되어야 하며, IMEI 및 32자리 EID 캡쳐화면 이미지 업로드가 필요합니다.</li>
					<li class="c-text-list__item u-co-gray">
						이미지 업로드 시 자동으로 입력됩니다.<a class="c-text-link--bluegreen" data-dialog-trigger="#uploadguide-dialog">이미지 가이드 보기</a>
					</li>
				</ul>
			</div>
			<div class="c-form u-mt--32">
				<!-- <div class="c-form__input-group is-readonly">
					<div class="c-form__input c-form__input-division">
						<input class="c-input--div" type="text" id="test_01" name="test_01" placeholder="휴대폰 모델명" readonly>
						<label class="c-form__label" for="test_01">휴대폰 모델명</label>
					</div>
				</div> -->
				<div class="c-form__input-group is-readonly">
					<div class="c-form__input c-form__input-division">
						<input class="c-input--div" type="text" id="eid" name="eid" placeholder="EID" readonly>
						<label class="c-form__label" for="eid">EID</label>
					</div>
				</div>
				<div class="c-form__input-group is-readonly">
					<div class="c-form__input c-form__input-division">
						<input class="c-input--div" type="text" id="imei1" name="imei1" placeholder="IMEI1" readonly>
						<label class="c-form__label" for="eid">IMEI1</label>
					</div>
				</div>
				<div class="c-form__input-group is-readonly">
					<div class="c-form__input c-form__input-division">
						<input class="c-input--div" type="text" id="imei2" name="imei2" placeholder="IMEI2" readonly>
						<label class="c-form__label" for="eid">IMEI2</label>
					</div>
				</div>
			</div>
			<div class="c-button-wrap u-mt--32">
				<label for="image" class="c-button c-button--full c-button--mint">이미지 등록</label>
				<input type="file" id="image" name="image" class="c-hidden ocrImg" accept="image/jpg, image/png, image/jpeg, image/tif, image/tiff, image/bmp">
			</div>
			<div class="c-button-wrap">
				<button class="c-button c-button--gray c-button--full" onclick="location.href='/m/appForm/eSimInfo.do'">취소</button>
				<button class="c-button c-button--full c-button--primary" id="nextStep">다음</button>
			</div>
		</div>

		<div class="c-modal" id="esim-check-modal" role="dialog" tabindex="-1" aria-describedby="esim-check-modal__title">
			<div class="c-modal__dialog c-modal__dialog--full" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<h1 class="c-modal__title" id="esim-check-modal__title">eSIM 사용 슬롯 확인</h1>
						<button class="c-button" data-dialog-close>
							<i class="c-icon c-icon--close" aria-hidden="true"></i>
							<span class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body">
						<ul class="c-text-list c-bullet c-bullet--dot">
							<li class="c-text-list__item u-co-red u-mt--0">휴대폰 정보가 올바른지 반드시 확인하시기 바랍니다.</li>
							<li class="c-text-list__item u-co-gray">입력정보가 다를 경우 취소 후 이미지를 재 등록하시기 바랍니다.</li>
						</ul>
						<div class="c-table">
							<table>
								<caption>구분, IMEI로 구성된 eSIM 정보</caption>
								<colgroup>
									<col width="20%">
									<col>
								</colgroup>
								<thead>
									<tr>
										<th scope="col">구분</th>
										<th scope="col">IMEI</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>EID</td>
										<td id="eidTxt">2</td>
									</tr>
									<tr>
										<td>IMEI1</td>
										<td id="imei1Txt">2</td>
									</tr>
									<tr>
										<td>IMEI2</td>
										<td id="imei2Txt">2</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="c-text-box c-text-box--red u-mt--24" id="errTxt" style="display:none;"></div>

						<!--
						<div class="c-form">
							<span class="c-form__title" id="PhoneModelHead">휴대폰 모델명 선택</span>
							<div class="c-form__select">
								<select class="c-select" id="PhoneNm">
									<option value="">휴대폰 선택</option>
									<option value="애플">애플</option>
									<option value="삼성">삼성</option>
									<option value="LG">LG</option>
								</select>
								<label class="c-form__label" for="PhoneNm">휴대폰 선택</label>
							</div>
						</div>
						<div class="c-form">
							<div class="c-form__select">
								<select class="c-select" id="PhoneModel">
									<option value="">모델명 선택</option>
									<option value="iPhone 13">iPhone 13</option>
									<option value="iPhone 12">iPhone 12</option>
								</select>
								<label class="c-form__label" for="PhoneModel">모델명 선택</label>
							</div>
						</div> -->

						<div class="c-button-wrap">
							<button class="c-button c-button--full c-button--gray" id="reSetBtn">다시 등록하기</button>
							<button class="c-button c-button--full c-button--primary" data-dialog-close id="setBtn">확인</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="c-modal" id="uploadguide-dialog" role="dialog" tabindex="-1" aria-describedby="#uploadguide-title">
			<div class="c-modal__dialog c-modal__dialog--full" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<h1 class="c-modal__title" id="uploadguide-title">이미지 업로드 가이드</h1>
						<button class="c-button" data-dialog-close>
							<i class="c-icon c-icon--close" aria-hidden="true"></i>
							<span class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body">
						<div class="esim-guide-box__pop">
							<div class="esim-guide__list">
								<span class="Number-label">1</span> 통화창에 *#06# 입력
							</div>
							<img src="/resources/images/mobile/esim/eSIM_guide_01.png" alt="1.통화창에 *#06#입력 가이드 이미지">
						</div>
						<div class="esim-guide-space__pop">
							<i class="c-icon c-icon--triangle_bluegreen-bottom" aria-hidden="true"></i>
						</div>
						<div class="esim-guide-box__pop u-mt--0">
							<div class="esim-guide__list">
								<span class="Number-label">2</span> 이미지 캡쳐본 업로드
							</div>
							<img src="/resources/images/mobile/esim/eSIM_guide_02.png" alt="2.화면 캡쳐 정보의 가이드 이미지">
						</div>
						<div class="c-box c-box--type1 u-pa--16 u-mt--24">
							<ul class="c-text-list c-bullet c-bullet--dot">
								<li class="c-text-list__item u-co-gray u-mt--0">이미지 업로드 시 각 항목이 자동으로 입력됩니다.</li>
							</ul>
						</div>
						<div class="c-button-wrap">
							<button class="c-button c-button--full c-button--primary" data-dialog-close>확인</button>
						</div>
					</div>
				</div>
			</div>
		</div>
<input type="hidden" id="uploadPhoneSrlNo" name="uploadPhoneSrlNo" value="">
	</t:putAttribute>
</t:insertDefinition>