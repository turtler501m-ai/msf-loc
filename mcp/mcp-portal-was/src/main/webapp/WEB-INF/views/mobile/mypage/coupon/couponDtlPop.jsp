<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<h2 class="c-modal__title" id="modalCouponAddTitle">쿠폰 상세</h2>
		<button class="c-button" data-dialog-close id="dtlPopClose">
			<i class="c-icon c-icon--close" aria-hidden="true"></i>
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body">
		<c:choose>
			<c:when test="${mbDtlDto ne null and !empty mbDtlDto}">
				<div class="c-form c-form--type2">
					<div class="c-input-wrap">
						<c:choose>
							<c:when test="${mbDtlDto.smsSndPosblYn eq 'N'}">
								<input class="c-input c-input--type2" id="dtlCoupSerialNo" name="dtlCoupSerialNo" type="text" value="${mbDtlDto.coupnNo}" readonly="readonly">
							</c:when>
							<c:otherwise>
								<p>회선번호로 개별 발송되는 상품입니다.<br/>상품은 발급일자 기준 일주일 이내 발송됩니다.</p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			
				<div class="c-table u-mt--32">
					<table>
						<caption>쿠폰명, 회선 번호, 등록 유효기간, 사용가능 기간을 포함한 쿠폰 정보</caption>
						<colgroup>
							<col style="width: 33%">
							<col>
						</colgroup>
						<tbody class="c-table__left">
							<tr>
								<th scope="row">쿠폰명</th>
								<td>${mbDtlDto.coupnCtgNm}</td>
							</tr>
							<tr>
								<th scope="row">회선번호</th>
								<td>${mbDtlDto.smsRcvNo}</td>
							</tr>
							<tr>
								<th scope="row">등록 유효기간</th>
								<td>${mbDtlDto.pstngStartDate} ~ ${mbDtlDto.pstngEndDate}</td>
							</tr>
							<tr>
								<th scope="row">사용 가능기간</th>
								<td>${mbDtlDto.avPrd}</td>
							</tr>
							<tr>
								<th scope="row">발급일자</th>
								<td>${mbDtlDto.coupnGiveDate}</td>
							</tr>
							<tr>
								<th scope="row">사용처</th>
								<td>${mbDtlDto.usePlc} <c:if test="${mbDtlDto.linkPc ne null and !empty mbDtlDto.linkPc}"><a href="${mbDtlDto.linkPc}" target="_blank">(${mbDtlDto.linkPc})</a></c:if></td>
							</tr>
						</tbody>
					</table>
				</div>
			</c:when>
			
			<c:otherwise>
				<div class="c-nodata">
					<i class="c-icon c-icon--nodata-coupon" aria-hidden="true"></i>
					<p class="c-nodata__text">유효하지 않은 쿠폰번호 입니다.</p>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="c-modal__footer">
		<div class="c-button-wrap u-mt--0">
			<button class="c-button c-button--full c-button--primary" id="dtlRegBtn">확인</button>
		</div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	
	$("#dtlRegBtn").click(function(){
		$('.c-icon--close').trigger('click');
	});
});
</script>