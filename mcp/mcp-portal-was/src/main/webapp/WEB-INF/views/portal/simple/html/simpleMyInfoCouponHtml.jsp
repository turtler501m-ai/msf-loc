<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un"
	uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
		<c:if test="${!empty coupInfoList}">
		<ul class="c-list-coupon">
			<c:forEach items="${coupInfoList}" var="items">
			<li class="c-list-coupon__item">
				<div class="c-card c-card--coupon">
					<span class="c-card__title">${items.coupNm}</span>
					<span class="c-card__text u-co-gray-7">
					<span class="c-hidden">사용기간</span>
					<fmt:parseDate var="useStrtDt" value="${items.useStrtDt}" pattern="yyyyMMddHHmmss" />
						<fmt:formatDate value="${useStrtDt}" pattern="yyyy.MM.dd" /> ~
						<fmt:parseDate var="useEndDt" value="${items.useEndDt}" pattern="yyyyMMddHHmmss" />
						<fmt:formatDate value="${useEndDt}" pattern="yyyy.MM.dd" /></span>
				</div>
			</li>
			</c:forEach>
		</ul>
		</c:if>
		<c:if test="${!empty mPortalcouponList}">
		<ul class="c-list-coupon">
			<c:forEach items="${mPortalcouponList}" var="mItems">
			<li class="c-list-coupon__item">
				<div class="c-card c-card--coupon">
					<span class="c-card__title">${mItems.coupnCtgNm}</span>
					<span class="c-card__text u-co-gray-7">
					<span class="c-hidden">사용기간</span>
					${mItems.pstngStartDate} ~ ${mItems.pstngEndDate}</span>
				</div>
			</li>
			</c:forEach>
		</ul>
		</c:if>
		<c:if test="${empty coupInfoList and empty mPortalcouponList}">
		<div class="c-nodata">
			<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
			<p class="c-nodata__text">조회 결과가 없습니다.</p>
		</div>
		</c:if>