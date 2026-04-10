<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<script type="text/javascript" src="/resources/js/personal/mobile/pAddSvcViewPop.js?version=25.07.22"></script>

<div class="c-modal__content">

	<input type="hidden" name="rateAdsvcCd" id="rateAdsvcCd" value="${rateAdsvcCd}"/>
	<input type="hidden" name="ncn" id="ncn" value="${ncn}"/>
	<input type="hidden" name="ftrNewParam" id="ftrNewParam" value=""/>

	<div class="c-modal__header">
		<h1 class="c-modal__title" id="serv-apply-title">부가서비스 신청</h1>
		<button class="c-button" data-dialog-close>
			<i class="c-icon c-icon--close" aria-hidden="true"></i>
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>

	<div class="c-modal__body">
		<h3 class="c-heading c-heading--type2 u-mt--24 u-mb--12">선택 부가서비스 내역</h3>
		<div class="c-table">
			<table>
				<caption>선택 부가서비스 영역</caption>
				<colgroup>
					<col style="width: 50%">
					<col style="width: 50%">
				</colgroup>
				<thead>
				<tr>
					<th scope="col">신청 부가서비스 내역</th>
					<th scope="col">이용요금(VAT)포함</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td class="u-ta-center">${rateAdsvcNm}</td>
					<c:choose>
						<c:when test="${empty baseVatAmt}">
							<td class="u-ta-center">무료</td>
						</c:when>
						<c:otherwise>
							<td class="u-ta-center">${baseVatAmt}</td>
						</c:otherwise>
					</c:choose>
				</tr>
				</tbody>
			</table>
		</div>
		<hr class="c-hr c-hr--type2">
		<b class="c-text c-text--type3">
			<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
		</b>
		<ul class="c-text-list c-bullet c-bullet--dot">
			<li class="c-text-list__item u-co-gray">변경하신 부가서비스는 즉시 적용 됩니다.</li>
			<li class="c-text-list__item u-co-gray">변경되는 내용을 모두 확인하시고 확인 혹은 취소를 선택해주세요.</li>
			<li class="c-text-list__item u-co-gray">부가서비스 월 중 신청/해지 시 해당월 월정액 및 무료제공 혜택은 각각 일할 적용 됩니다.</li>
			<li class="c-text-list__item u-co-gray">해지 시 재가입 또는 즉시 재가입이 불가능한 부가서비스가 있으므로 주의 바랍니다.</li>
			<li class="c-text-list__item u-co-gray">부가서비스 별로 실제 적용시점이 다른 경우가 있으니 신청/해지 전 확인하시기 바랍니다.</li>
		</ul>
		<div class="c-button-wrap u-mt--48">
			<button class="c-button c-button--full c-button--gray" data-dialog-close>
				취소
				<span class="c-hidden">팝업닫기</span>
			</button>
			<button class="c-button c-button--full c-button--primary" onclick="btn_reg();">확인</button>
		</div>
	</div>
</div>
