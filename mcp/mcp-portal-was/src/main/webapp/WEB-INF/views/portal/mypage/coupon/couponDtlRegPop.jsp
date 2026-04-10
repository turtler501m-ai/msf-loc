<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<script type="text/javascript" src="/resources/js/portal/mypage/coupon/couponDtlRegPop.js"></script>

<div class="c-modal__content">
	<div class="c-modal__header">
		<h2 class="c-modal__title" id="modalCouponAddTitle">쿠폰 등록</h2>
		<button class="c-button" data-dialog-close id="dtlPopClose">
			<i class="c-icon c-icon--close" aria-hidden="true"></i>
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body">
		<div class="c-form c-form--type2">
			<label class="c-label c-hidden" for="coupSerialNo">쿠폰번호</label>
			<div class="c-input-wrap">
				<input class="c-input c-input--type2" id="dtlCoupSerialNo" name="dtlCoupSerialNo" type="text" placeholder="발급된 쿠폰번호를 입력해주세요." value="${coupSerialNo}" readonly="readonly">
				<button class="c-button c-button-round--md c-button--primary u-ml--8" disabled="disabled">조회</button>
			</div>
		</div>
		<p class="c-bullet c-bullet--dot u-co-gray u-mt--12">유효기간이 지난 쿠폰의 경우 등록되지 않습니다.</p>
		<c:set var="dtlRegChk" value=""/>
		<c:choose>
			<c:when test="${coupInfoList ne null and !empty coupInfoList}">
			<c:set var="dtlRegChk" value="Y"/>
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
								<td>${coupInfoList[0].coupNm}</td>
							</tr>
							<tr>
								<th scope="row">회선번호</th>
								<td>${mobileNo}</td>
							</tr>
							<tr>
								<th scope="row">등록 유효기간</th>
								<fmt:parseDate var="useStrtDt" value="${coupInfoList[0].useStrtDt}" pattern="yyyyMMddHHmmss" />
								<fmt:parseDate var="useEndDt" value="${coupInfoList[0].useEndDt}" pattern="yyyyMMddHHmmss" />
								<td>
									<fmt:formatDate value="${useStrtDt}" pattern="yyyy.MM.dd" />~<fmt:formatDate value="${useEndDt}" pattern="yyyy.MM.dd" />
								</td>
							</tr>
							<tr>
								<th scope="row">사용가능 기간</th>
								<td>신청 즉시</td>
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
			<button class="c-button c-button--lg c-button--primary c-button--w460" 	id="dtlRegBtn"<c:if test="${dtlRegChk ne 'Y'}">disabled="disabled"</c:if>>등록</button>
		</div>
	</div>
</div>
<input type="hidden" id="dtlNcn" name="dtlNcn" value="${ncn}">
