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
			              <div>
			                <strong class="c-list__title cntrMobileNo">mobileNo</strong>
			                <span class="c-list__desc">${pointDetailList.pointTxnDtlRsnDesc}</span>
			                <span class="c-list__sub">${pointDetailList.pointTrtDt}</span>
			              </div>
			              <div class="u-ta-right">
			              	<c:if test="${pointDetailList.pointTrtCd=='U'}">
			                <span class="c-text-label c-text-label--red">사용</span>
			                <br>
			                <b class="c-list__point u-co-red fs-18">-<fmt:formatNumber value="${pointDetailList.point}" pattern="#,###"/>P</b>
			               	</c:if>
			               	<c:if test="${pointDetailList.pointTrtCd=='S'}">
			                <span class="c-text-label c-text-label--mint-type1">적립</span>
			                <br>
			                <b class="c-list__point u-co-mint fs-18">+<fmt:formatNumber value="${pointDetailList.point}" pattern="#,###"/>P</b>
			               	</c:if>
			               	<c:if test="${pointDetailList.pointTrtCd=='E'}">
			                <span class="c-text-label c-text-label--gray-type1">소멸</span>
			                <br>
			                <b class="c-list__point u-co-sub-1 fs-18">-<fmt:formatNumber value="${pointDetailList.point}" pattern="#,###"/>P</b>
			               	</c:if>
			                
			              </div>
			            </li>
			        </c:forEach>
		        </c:if>
		        <c:if test="${empty pointDetailList}">
			        <div class="c-nodata u">
			            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
			            <p class="c-nodata__text">조회 결과가 없습니다.</p>
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

          
          

