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
		<script type="text/javascript" src="/resources/js/mobile/etc/giftPromotion.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">

			<input type="hidden" id="prmtId" name="prmtId" value="${prmtId}" />
			<input type="hidden" id="amountLimit" name="amountLimit" value="${promotionDto.amountLimit}">

			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					사은품 신청
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="u-img-full">
				<img src="/resources/images/mobile/etc/giftPromotion/banner_giftpromotion.png" alt="사은품을 신청하요">
			</div>
			<div class="c-item c-item--type1 product-info-box u-mt--12">
				<span class="c-text c-text--type3">사은품 배송을 위해서는 서비스 제공을 위한 동의 및 본인확인이 필요합니다. 내용을 확인하시고 동의하여 주시기 바랍니다.</span>
			</div>
			<div class="c-form">
				<div class="c-agree">
					<!-- <input class="c-checkbox" id="clausePriOfferFlag1" type="checkbox" name="clausePriOfferFlag1">
					<label class="c-label" for="clausePriOfferFlag1"> 사은품 배송을 위한 필수 확인사항 </label> -->
					<div class="c-agree__item">
						<input class="c-checkbox c-checkbox--type2" id="clausePriOfferFlag" type="checkbox" name="clausePriOfferFlag">
						<label class="c-label" for="clausePriOfferFlag">(필수)개인정보의 제공 동의</label>
						<button class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=FormEtcCla&cdGroupId2=GiftPromotion');">
							<span class="c-hidden">상세보기</span>
							<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
						</button>
					</div>
				</div>
			</div>

			<div class="c-form">
				<span class="c-form__title">본인인증</span>
				<%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
				<div class="c-agree">
					<div class="c-agree__item">
						<input class="c-checkbox" id="chkA1" type="checkbox" name="chkA1">
						<label class="c-label" for="chkA1">개인정보 수집이용 동의</label>
						<button class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=TERMSINF&cdGroupId2=TERMSINF01');">
							<span class="c-hidden">상세보기</span>
							<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
						</button>
					</div>
				</div>
				<div class="c-button-wrap">
					<button class="c-button c-button--full c-button--primary" id="certBtn" href="javascript:;">확인</button>
				</div>
			</div>

		<div id="supplyArea" style="display: none;">
			<div class="c-form">
		        <div class="c-form__title">연락처</div>
				    <div class="c-form__input-group">
				      <div class="c-form__input c-form__input-division">
				        <input class="c-input--div3 numOnly" id="moveMobileFn" type="tel" placeholder="010" maxlength="3">
				        <span>-</span>
				        <input class="c-input--div3 numOnly" id="moveMobileMn" type="tel" placeholder="1234" maxlength="4">
				        <span>-</span>
				        <input class="c-input--div3 numOnly" id="moveMobileRn" type="tel" placeholder="5678" maxlength="4">
				        <label class="c-form__label" for="moveMobileFn">연락처</label>
				    </div>
			    </div>
		    </div>
			<div class="c-form">
				<span class="c-form__title">배송주소</span>
				<div class="c-form__group" role="group" aria-labelledby="inpG">
					<div class="c-input-wrap">
						<input class="c-input" type="text" placeholder="우편번호" title="우편번호 입력" readonly id="cstmrPost">
						<button class="c-button c-button--sm c-button--underline" onclick="openPage('pullPopup', '/m/addPopup.do');">우편번호 찾기</button>
					</div>
					<input class="c-input" type="text" placeholder="주소 입력" title="주소 입력" value="" readonly id="cstmrAddr">
					<input class="c-input" type="text" placeholder="상세 주소 입력" title="상세 주소 입력" value="" readonly id="cstmrAddrDtl">
				</div>
			</div>

			<div class="c-form">
				<span class="c-form__title" id="radCardType">사은품 선택</span>
				<c:if test="${promotionDto ne null}">
					<p class="c-text c-heading--type6">${promotionDto.prmtText}</p>
				</c:if>
				<c:choose>
					<c:when test="${giftDtoList ne null and !empty giftDtoList}">
						<div class="c-check-wrap c-check-wrap--column">
							<c:forEach var="giftDtoList" items="${giftDtoList}" varStatus="status">
								<input class="c-checkbox c-radio--button c-checkbox--button--type5" id="chkSS${status.count}" type="checkbox" name="gift"  value="${giftDtoList.prdtId}" price="${giftDtoList.outUnitPric}" >
								<label class="c-label" for="chkSS${status.count}">
									<img src="${giftDtoList.webUrl}" alt="${giftDtoList.prdtNm}"><br/>${giftDtoList.prdtNm}
								</label>
							</c:forEach>
						</div>
						<div>
							<div class="c-button-wrap">
								<button class="c-button c-button--full c-button--primary" id="btnApply">사은품 신청</button>
							</div>
							<div class="c-item c-item--type1 product-info-box u-mt--12">
								<span class="c-text c-text--type3">실물 상품의 경우 잘못된 배송 주소 입력 시 재발송 및 반품이 불가하오니 반드시 배송주소를 정확히 입력해 주시기 바랍니다.</span>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="c-nodata">
							<div class="c-icon c-icon--notice" aria-hidden="true"></div>
							<p class="c-nodata__text">신청 가능한 사은품이 없습니다</p>
							<div class="c-button-wrap">
								<button class="c-button c-button--full c-button--primary" id="complBtn">완료</button>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

	</t:putAttribute>
</t:insertDefinition>