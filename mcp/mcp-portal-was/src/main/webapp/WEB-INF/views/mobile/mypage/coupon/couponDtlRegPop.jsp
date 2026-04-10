<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<script type="text/javascript" src="/resources/js/mobile/mypage/coupon/couponDtlRegPop.js"></script>

<div class="c-modal__content">
	<div class="c-modal__header">
		<h1 class="c-modal__title" id="coupon-regis-title">쿠폰등록</h1>
		<button class="c-button" data-dialog-close id="dtlPopClose">
			<i class="c-icon c-icon--close" aria-hidden="true"></i>
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body">
		<div class="c-form">
			<div class="c-form__input u-mt--32">
				<input class="c-input" id="dtlCoupSerialNo" name="dtlCoupSerialNo" type="text" placeholder="발급된 쿠폰번호를 입력해주세요." value="${coupSerialNo}" readonly="readonly">
				<label class="c-form__label" for="dtlCoupSerialNo">쿠폰 번호</label>
				<button class="c-button c-button--sm c-button--underline" disabled="disabled">조회</button>
			</div>
		</div>
		<c:set var="dtlRegChk" value=""/>
		<c:choose>
			<c:when test="${coupInfoList ne null and !empty coupInfoList}">
			<c:set var="dtlRegChk" value="Y"/>
				<div class="c-table c-table--row u-mt--40">
					<table>
						<caption>쿠폰등록 열람</caption>
						<colgroup>
							<col style="width: 32%">
							<col style="width: 68%">
						</colgroup>
						<tbody>
							<tr>
								<th class="u-ta-left" scope="row">쿠폰명</th>
								<td class="u-ta-left">${coupInfoList[0].coupNm}</td>
							</tr>
							<tr>
								<th class="u-ta-left" scope="row">회선 번호</th>
								<td class="u-ta-left">${mobileNo}</td>
							</tr>
							<tr>
								<th class="u-ta-left" scope="row">등록 유효기간</th>
								<fmt:parseDate var="useStrtDt" value="${coupInfoList[0].useStrtDt}" pattern="yyyyMMddHHmmss" />
								<fmt:parseDate var="useEndDt" value="${coupInfoList[0].useEndDt}" pattern="yyyyMMddHHmmss" />
								<td class="u-ta-left">
									<fmt:formatDate value="${useStrtDt}" pattern="yyyy.MM.dd" />~<fmt:formatDate value="${useEndDt}" pattern="yyyy.MM.dd" />
								</td>
							</tr>
							<tr>
								<th class="u-ta-left" scope="row">사용가능 기간</th>
								<td class="u-ta-left">신청 즉시</td>
							</tr>
						</tbody>
					</table>
				</div>
			</c:when>
			<c:otherwise>
				<div class="c-nodata">
					<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
					<p class="c-noadat__text">조회 결과가 없습니다.</p>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="c-button-wrap u-mt--48">
			<button class="c-button c-button--full c-button--primary" id="dtlRegBtn"<c:if test="${dtlRegChk ne 'Y'}">disabled="disabled"</c:if>>등록</button>
		</div>
		<hr class="c-hr c-hr--type2 u-mt--40">
		<ul class="c-text-list c-bullet c-bullet--dot">
			<li class="c-text-list__item u-co-gray u-mt--0">유효기간이 지난 쿠폰의 경우 등록되지 않습니다.</li>
		</ul>
	</div>
</div>
<input type="hidden" id="dtlNcn" name="dtlNcn" value="${ncn}">