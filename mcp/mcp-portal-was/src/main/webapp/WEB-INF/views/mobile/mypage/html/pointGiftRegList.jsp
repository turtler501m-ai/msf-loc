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
		<c:if test="${empty couponList}">
          <div class="c-nodata">
            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
            <p class="c-nodata__text">조회 결과가 없습니다.</p>
          </div>
         </c:if>
          <ul class="c-list c-list--goods">
          <c:if test="${!empty couponList}">
			<c:forEach items="${couponList}" var="couponList">
            <li class="c-list__item">
              <div class="c-card c-coupon">
                <div class="c-card__box">
                  <div class="c-chk-wrap">
                  <c:choose>
					<c:when test="${couponList.coupnQnty == 0}">
						<input class="c-checkbox c-checkbox--type4" id="${couponList.coupnCtgCd}" type="radio" name="coupnCtgCd" exchPoint="${couponList.exchPoint}" coupnCtgNm="${couponList.coupnCtgNm}" disabled>
						
					</c:when>
					<c:when test="${totRemainPoint lt couponList.exchPoint}">
						<input class="c-checkbox c-checkbox--type4" id="${couponList.coupnCtgCd}" type="radio" name="coupnCtgCd" exchPoint="${couponList.exchPoint}" coupnCtgNm="${couponList.coupnCtgNm}" disabled>
					</c:when>
					<c:otherwise>
						<input class="c-checkbox c-checkbox--type4" id="${couponList.coupnCtgCd}" type="radio" name="coupnCtgCd" exchPoint="${couponList.exchPoint}" coupnCtgNm="${couponList.coupnCtgNm}">
					</c:otherwise>
			</c:choose>
                    <label class="c-card__label" for="${couponList.coupnCtgCd}">
                      <span class="c-hidden">선택</span>
                    </label>
                  </div>
                  <div class="c-pad__box">
                    <div class="c-text c-text--type3 u-fw--medium">${couponList.coupnCtgNm}</div>
                    <div class="c-card__price-box">
                      <span>
                        <i class="c-icon c-icon--point-star" aria-hidden="true"></i>
                      </span>
                      <span class="c-text c-text--type3 u-co-mint">
                        <b id="remainPointPopup"><fmt:formatNumber value="${couponList.exchPoint}" pattern="#,###"/>P</b>
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </li>
           </c:forEach>
           </c:if>
         </ul>
<script>
$('#pageCnt').html('${listCount}/${pageInfoBean.totalCount }');
if(${listCount} >= ${pageInfoBean.totalCount}){
	$('#moreList').css('display','none');
}else{
	$('#moreList').css('display','block');
	
}
$('#pageNo').val('${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}');

</script>
          


