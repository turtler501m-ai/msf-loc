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
			<c:forEach items="${coupInfoList}" var="items">
			<div class="c-card c-coupon u-mt--12">
				<div class="c-card__box u-mt--0">
					<div class="c-pad__box">
						<div class="c-text c-text--type2 u-fw--medium">
							<span>${items.coupNm}</span>
						</div>
						<div class="c-card__price-box u-mt--4">
							<span class="c-text c-text--type4 u-co-gray-7">
							<fmt:parseDate var="useStrtDt" value="${items.useStrtDt}" pattern="yyyyMMddHHmmss" />
							<fmt:formatDate value="${useStrtDt}" pattern="yyyy.MM.dd" /> ~
							<fmt:parseDate var="useEndDt" value="${items.useEndDt}" pattern="yyyyMMddHHmmss" />
							<fmt:formatDate value="${useEndDt}" pattern="yyyy.MM.dd" /></span>
						</div>
					</div>
				</div>
			</div>
	        </c:forEach>
        </c:if>
        <c:if test="${!empty mPortalcouponList}">
			<c:forEach items="${mPortalcouponList}" var="mItems">
			<div class="c-card c-coupon u-mt--12">
				<div class="c-card__box u-mt--0">
					<div class="c-pad__box">
						<div class="c-text c-text--type2 u-fw--medium">
							<span>${mItems.coupnCtgNm}</span>
						</div>
						<div class="c-card__price-box u-mt--4">
							<span class="c-text c-text--type4 u-co-gray-7">
							${mItems.pstngStartDate} ~ ${mItems.pstngEndDate}</span>
						</div>
					</div>
				</div>
			</div>
	        </c:forEach>
        </c:if>
		<c:if test="${empty coupInfoList and empty mPortalcouponList}">
			<div class="c-nodata u">
				<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
					<p class="c-nodata__text">조회 결과가 없습니다.</p>
			</div>
		</c:if>

