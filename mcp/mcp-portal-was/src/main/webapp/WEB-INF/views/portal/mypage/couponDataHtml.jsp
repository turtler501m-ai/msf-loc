<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
 
<%-- 쿠폰 목록 --%>
<c:set var="couponCtn" value="${fn:length(coupInfoList)}"/>

<c:choose>
	<c:when test="${coupInfoList !=null and !empty coupInfoList}">
		<c:forEach var="coupInfo" items="${coupInfoList}" varStatus="status">
			<div id="dtlView" class="c-card c-card--coupon" coupSerialNo="${coupInfo.coupSerialNo}">
				<span class="c-card__title">
					<c:choose>
				 		<c:when test="${'01' eq coupInfo.coupTypeCd}">요금할인쿠폰</c:when>
				 		<c:when test="${'02' eq coupInfo.coupTypeCd}">서비스이용쿠폰</c:when>
				 		<c:when test="${'05' eq coupInfo.coupTypeCd}">컨텐츠/상품쿠폰</c:when>
				 		<c:when test="${'06' eq coupInfo.coupTypeCd}">마켓쿠폰</c:when>
				 		<c:when test="${'08' eq coupInfo.coupTypeCd}">올레마켓 포인트 쿠폰</c:when>
				 		<c:when test="${'09' eq coupInfo.coupTypeCd}">TV가입 적립쿠폰</c:when>
				 		<c:when test="${'10' eq coupInfo.coupTypeCd}">TV배포 적립쿠폰	</c:when>
				 		<c:otherwise>-</c:otherwise>
				 	</c:choose>
				</span>
				<div class="c-form c-form--line">
					<label class="c-label c-hidden" for="selA">쿠폰 종류 선택</label> 
					<select class="c-select u-fs-13">
						<option label="${coupInfo.coupNm}" value="${coupInfo.coupSerialNo}">${coupInfo.coupNm}</option>
					</select>
				</div>
				<div class="c-card__bottom">
					<span class="c-card__sub"> 
						<span class="c-hidden">사용기간</span>
						<fmt:parseDate var="useStrtDt" value="${coupInfo.useStrtDt}" pattern="yyyyMMddHHmmss" />
						<fmt:parseDate var="useEndDt" value="${coupInfo.useEndDt}" pattern="yyyyMMddHHmmss" />
						<fmt:formatDate value="${useStrtDt}" pattern="yyyy.MM.dd" />~<fmt:formatDate value="${useEndDt}" pattern="yyyy.MM.dd" />
					</span>
					<button id="deRegBtn_${status.count}" class="c-button c-button-round--xxsm c-button--primary" data-coupSerialNo="${coupInfo.coupSerialNo}">사용</button>
				</div>
			</div>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="c-nodata">
        	<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
          	<p class="c-nodata__text">등록 가능한 쿠폰이 없습니다.</p>
        </div>
		<%--
		<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
		<p class="c-nodata__text">등록 가능한 쿠폰이 없습니다.</p>
		 --%>
	</c:otherwise>
</c:choose>
