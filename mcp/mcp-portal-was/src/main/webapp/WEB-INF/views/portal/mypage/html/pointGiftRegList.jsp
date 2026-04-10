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


<div class="c-checktype-coupon">
	<c:if test="${empty couponList}">
		<div class="c-nodata">
			<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
			<p class="c-noadat__text">조회 결과가 없습니다.</p>
		</div>
	</c:if>
	<c:if test="${!empty couponList}">
		<c:forEach items="${couponList}" var="couponList" varStatus="status">
			<div class="c-form">
			<c:choose>
					<c:when test="${couponList.coupnQnty == 0}">
						<%-- <input class="c-radio" id="${couponList.coupnCtgCd}" type="radio" name="coupnCtgCd" exchPoint="${couponList.exchPoint}" coupnCtgNm="${couponList.coupnCtgNm}" disabled> --%>
					</c:when>
					<c:when test="${totRemainPoint lt couponList.exchPoint}">
						<%-- <input class="c-radio" id="${couponList.coupnCtgCd}" type="radio" name="coupnCtgCd" exchPoint="${couponList.exchPoint}" coupnCtgNm="${couponList.coupnCtgNm}" disabled> --%>
					</c:when>
					<c:otherwise>
						<input class="c-radio" id="${couponList.coupnCtgCd}" type="radio" name="coupnCtgCd" exchPoint="${couponList.exchPoint}" coupnCtgNm="${couponList.coupnCtgNm}">
					</c:otherwise>
			</c:choose>
				<label class="c-label" for="${couponList.coupnCtgCd}">
					<div class="c-card c-card--coupon">
						<span class="c-card__title">${couponList.coupnCtgNm}</span>
						<span class="c-card__text u-co-blue">
							<i class="c-icon c-icon--my-point" aria-hidden="true"></i>
							<b id="remainPointPopup"><fmt:formatNumber value="${couponList.exchPoint}" pattern="#,###"/>P</b>
						</span>
					</div>
				</label>
			</div>
		</c:forEach>
	</c:if>

</div>
<script>
	$('#pageCnt').html('${listCount}/${pageInfoBean.totalCount }');

if( ${listCount} >= ${pageInfoBean.totalCount}){
	$('#moreList').css('display','none');
} else {
	$('#moreList').css('display','block');
}
$('#pageNo').val('${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}');

</script>
