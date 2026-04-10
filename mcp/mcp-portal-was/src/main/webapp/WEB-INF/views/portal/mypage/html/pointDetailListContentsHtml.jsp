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
	<c:if test="${!empty pointDetailList}">
		<c:forEach items="${pointDetailList}" var="pointDetailList">
		
			<li class="c-list__item u-flex-between">
				<div class="c-list__inner">
					<strong class="c-list__title cntrMobileNo">mobileNo</strong>
					<span class="c-list__text">${pointDetailList.pointTxnDtlRsnDesc}</span>
					<span class="c-list__sub">${pointDetailList.pointTrtDt}</span>
				</div>
				<span class="c-list__point">
					<c:if test="${pointDetailList.pointTrtCd=='U'}">
						<span class="c-text-label c-text-label--orange-type2">사용</span>
						<span class="c-list__text u-co-point-4">-<fmt:formatNumber value="${pointDetailList.point}" pattern="#,###"/>P</span>
					</c:if>
					<c:if test="${pointDetailList.pointTrtCd=='S'}">
						<span class="c-text-label c-text-label--blue">적립</span>
						<span class="c-list__text u-co-blue">+<fmt:formatNumber value="${pointDetailList.point}" pattern="#,###"/>P</span>
					</c:if>
					<c:if test="${pointDetailList.pointTrtCd=='E'}">
						<span class="c-text-label c-text-label--darkgray">소멸</span>
						<span class="c-list__text u-co-sub-1">-<fmt:formatNumber value="${pointDetailList.point}" pattern="#,###"/>P</span>
					</c:if>
				</span>
			</li>
		</c:forEach>
	</c:if>
	<c:if test="${empty pointDetailList}">
		<div class="c-nodata u">
			<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
			<p class="c-noadat__text">조회 결과가 없습니다.</p>
		</div>
	</c:if>
<script>
if(${listCount} >= ${pageInfoBean.totalCount}){
	$('#moreList').css('display','none');
}else{
	$('#moreList').css('display','block');
	$('#pageCnt').html('${listCount}/${pageInfoBean.totalCount }');
}
$('#pageNo').val('${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}');

</script>