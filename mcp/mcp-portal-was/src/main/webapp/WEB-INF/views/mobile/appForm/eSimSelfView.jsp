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
	<t:putAttribute name="googleTagScript">
		<!-- Event snippet for 가입신청시작_MO conversion page -->
		<script>
			gtag('event', 'conversion', {'send_to': 'AW-862149999/qD77CK_jxLYZEO-6jZsD'});
		</script>
	</t:putAttribute>
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/mobile/appForm/eSimSelfView.js?version=23-11-17"></script>
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
			<div class="c-button-wrap c-button-wrap--right _card u-mt--16" >
	            <button class="c-button _ocrEidRecord" data-type="eid">
	            	<span class="c-text c-text--type3 u-co-sub-4">스캔하기</span>
	            	<i class="c-icon c-icon--scan" aria-hidden="true"></i>
	            </button>
	        </div>
			<div class="c-form u-mt--16">
				<div class="c-form__input-group is-readonly">
					<div class="c-form__input c-form__input-division">
						<input class="c-input--div" type="text" id="modelNm" name="modelNm" placeholder="휴대폰 모델명" readonly>
						<label class="c-form__label" for="modelNm">휴대폰 모델명</label>
					</div>
				</div>
				<div class="c-form__input-group is-readonly">
					<div class="c-form__input c-form__input-division">
						<input class="c-input--div" type="text" id="eid" name="eid" placeholder="EID" readonly>
						<label class="c-form__label" for="eid">EID</label>
					</div>
				</div>
				<div class="c-form__input-group is-readonly">
					<div class="c-form__input c-form__input-division">
						<input class="c-input--div" type="text" id="imei1" name="imei1" placeholder="IMEI1" readonly>
						<label class="c-form__label" for="imei1">IMEI1</label>
					</div>
				</div>
				<div class="c-form__input-group is-readonly">
					<div class="c-form__input c-form__input-division">
						<input class="c-input--div" type="text" id="imei2" name="imei2" placeholder="IMEI2" readonly>
						<label class="c-form__label" for="imei2">IMEI2</label>
					</div>
				</div>
			</div>
			<div class="c-button-wrap u-mt--32">
				<label for="image" class="c-button c-button--full c-button--mint">이미지 등록</label>
				<input type="file" id="image" name="image" class="c-hidden ocrImg" accept="image/jpg, image/png, image/jpeg, image/tif, image/tiff, image/bmp">
			</div>

			<div id="authArea" style="display:none;">
				<h4 class="c-heading c-heading--type5">명의자 확인</h4>
				<hr class="c-hr c-hr--type2">
				<div class="c-form">
					<div class="c-form__input ">
						<input class="c-input" id="cstmrName" name="cstmrName"type="text" placeholder="이름 입력" value="" maxlength="60">
						<label class="c-form__label" for="cstmrName">이름</label>
					</div>
				</div>
				<div class="c-form-field">
					<div class="c-form__input-group">
						<div class="c-form__input c-form__input-division">
							<input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" name="cstmrNativeRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" >
							<span>-</span>
							<input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" name="cstmrNativeRrn02" autocomplete="off" type="password" maxlength="7" value="" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리">
							<label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
						</div>
					</div>
				</div>
				<div class="c-form">
					<ul class="c-text-list c-bullet c-bullet--dot">
						<li class="c-text-list__item u-co-gray">eSIM 추가 개통을 원하실 경우 개통하시려는 기기에서 사용중인 휴대폰번호로 인증이 필요합니다.</li>
					</ul>
				</div>
				<div class="c-button-wrap">
					<button id="btnSmsAuth" class="c-button c-button--full c-button--mint">휴대폰 인증</button>
				</div>
			</div>

			<div id="phoneArea" style="display:none;">
				<h4 class="c-heading c-heading--type5">휴대폰 모델명 선택</h4>
				<hr class="c-hr c-hr--type2">
				<div class="c-form">
					<div class="c-form__select">
						<select class="c-select" id="phoneNm">
							<option value="">휴대폰 선택</option>
							<c:forEach items="${nmcp:getCodeList('MK0001')}" var="phoneNmList">
								<option value="${phoneNmList.dtlCd}">${phoneNmList.dtlCdNm}</option>
							</c:forEach>
						</select>
						<label class="c-form__label" for="phoneNm">휴대폰 선택</label>
					</div>
				</div>
				<div class="c-form">
					<div class="c-form__select">
						<select class="c-select" id="phoneModel"></select>
						<label class="c-form__label" for="phoneModel">모델명 선택</label>
					</div>
				</div>
			</div>

			<div class="c-button-wrap">
				<button class="c-button c-button--gray c-button--full" onclick="location.href='/m/appForm/eSimInfom.do'">취소</button>
				<button class="c-button c-button--full c-button--primary" onclick="javascript:;" id="nextStep">다음</button>
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
						<div class="esim-slot-box">
							<ul class="esim-slot-box__list">
								<li class="u-co-red">휴대폰 정보가 올바른지 반드시 확인하시기 바랍니다.</li>
								<li>이미지에서 추출된 숫자가 정확하지 않으면 <span>다시 촬영해서 업로드</span> 시도 해 주시거나 고객님께서 <span>직접 해당 번호를 입력</span> 해 주십시오.</li>
							</ul>
						</div>
						<div class="c-table esim-slot">
							<table>
								<caption>구분, IMEI로 구성된 eSIM 정보</caption>
								<colgroup>
									<col width="15%">
									<col>
								</colgroup>
								<thead>
									<tr>
										<th scope="col" class="u-pl--10">구분</th>
										<th scope="col" class="u-ta-center">IMEI</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="u-pl--10">EID</td>
										<td>
											<input type="text" class="c-input numOnly" id="eidTxt" name="eidTxt" value="" maxlength="32">
										</td>
									</tr>
									<tr>
										<td class="u-pl--10">IMEI1</td>
										<td>
											<input type="text" class="c-input numOnly" id="imei1Txt" name="imei1Txt" value="" maxlength="15">
										</td>
									</tr>
									<tr>
										<td class="u-pl--10">IMEI2</td>
										<td>
											<input type="text" class="c-input numOnly" id="imei2Txt" name="imei2Txt" value="" maxlength="15">
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="esim-slot-box__err" id="errTxt" style="display:none;"></div>
						<div class="c-button-wrap">
							<button class="c-button c-button--full c-button--gray" data-dialog-close id="reSetBtn">다시 등록하기</button>
							<button class="c-button c-button--full c-button--primary" id="setBtn">확인</button>
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


		<!-- esim 정보 체크 -->
		<div class="c-modal" id="esimInfoCheck" role="dialog" aria-describedby="esimInfoCheck_title">
			<div class="c-modal__dialog" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<h1 class="c-modal__title" id="esimInfoCheck_title">EID/IMEI 정보를 확인해주세요.</h1>
						<button class="c-button" data-dialog-close >
							<i class="c-icon c-icon--close" aria-hidden="true"></i>
							<span class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body u-pt--0">
						<div class="c-box c-box--type1 u-pa--16">
							<ul class="c-text-list c-bullet c-bullet--dot">
								<li class="c-text-list__item u-mt--0">입력되지 않은 항목이 있을 경우 <span class="u-co-red">[다시 촬영하기]</span>를 통해 다시 촬영해 주세요.</li>
								<li class="c-text-list__item">직접 입력을 원하실 경우 [확인]을 눌러주세요.</li>
							</ul>
						</div>
						<div class="esim-join-wrap u-mt--0">
							<div class="esim-product-box">
								<div class="esim-product__item">
									<span class="esim-product__lable--iphone u-mb--8">EID</span>
									<div class="esim-product__model">
										<b class="u-fs-14" id="layerEid" >-</b>
									</div>
								</div>
								<div class="esim-product__item">
									<span class="esim-product__lable--iphone u-mb--8">IMEI1</span>
									<div class="esim-product__model">
										<b id="layerImei1" >-</b>
									</div>
								</div>
								<div class="esim-product__item">
									<span class="esim-product__lable--iphone u-mb--8">IMEI2</span>
									<div class="esim-product__model">
										<b id="layerImei2" >-</b>
									</div>
								</div>
							</div>
						</div>
						<div class="c-button-wrap">
							<button class="c-button c-button--full c-button--gray" id="btnLayerConfirm" >확인</button>
							<button class="c-button c-button--full c-button--primary _ocrEidRecord"  data-type="retry" >다시 촬영하기</button>
						</div>
					</div>
				</div>
			</div>
		</div>




<input type="hidden" id="uploadPhoneSrlNo" name="uploadPhoneSrlNo" value="">
<input type="hidden" id="modelId" name="modelId" value="">
<input type="hidden" id="intmSrlNo" name="intmSrlNo" value="">
<input type="hidden" id="resultCode" name="resultCode" value="">
<input type="hidden" id="moveTlcmIndCd" name="moveTlcmIndCd" value="">
<input type="hidden" id="moveCmncGnrtIndCd" name="moveCmncGnrtIndCd" value="">
<input type="hidden" id="moveCd" name="moveCd" value="">
	</t:putAttribute>
</t:insertDefinition>