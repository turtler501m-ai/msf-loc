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
    <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="trans-point-title">포인트 교환</h1>
          <button class="c-button" data-dialog-close onclick="parent.location.reload();">
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <input type="hidden" id="pageNo" name="pageNo" value="${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}">
	    <input type="hidden" id="listCount" name="listCount" value="${listCount}">
	    <input type="hidden" id="maxPage" name="maxPage" value="${maxPage}">
        <div class="c-modal__body">
          <div class="c-item u-mt--24">
            <div class="c-item__title flex-type--between">포인트 현황<span class="c-text c-text--type4 u-ml--auto u-co-gray-7">${formatedNow} 기준</span>
            </div>
            <div class="banner-balloon u-mt--12">
              <p>
                <span class="c-text c-text--type5">My 포인트</span>
                <br>
                <b class="c-text c-text--type7" id="totRemainPoint" data-totRemainPoint="${custPoint.totRemainPoint}">
                <c:choose>
                	<c:when test="${empty custPoint}">0P</c:when>
                	<c:otherwise>
                		<fmt:formatNumber value="${custPoint.totRemainPoint}" pattern="#,###"/>P
                	</c:otherwise>
                </c:choose>
                </b>
                <img class="deco-img" src="/resources/images/mobile/content/img_bolloon_banner_05.png" alt="">
              </p>
            </div>
          </div>
          <h3 class="c-heading c-heading--type2 u-mt--64 u-mb--0">상품목록</h3>
          <c:choose>
			
			<c:when test="${empty custPoint}">
				<div class="c-nodata">
					<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
					<p class="c-nodata__text">조회 결과가 없습니다.</p>
				</div>
			</c:when>
			<c:otherwise>
				<div id="contents"></div>
			</c:otherwise>
			</c:choose>
          <div class="c-button-wrap" id="moreList" onclick="selectCouponList();">
            <button class="c-button c-button--full">더보기<span class="c-button__sub" id="pageCnt">00/000</span>
              <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
            </button>
          </div>
          </div>
          <div class="c-modal__footer">
          <div class="c-button-wrap u-mt--48">
            <button class="c-button c-button--full c-button--primary" onclick="exchange();">교환</button>
          </div>
        </div>
        </div>
      </div>
		<script>
      	$(document).ready(function(){
      		$('#moreList').css('display','none');
          	//
          	var v_totRemainPoint = $("#totRemainPoint").attr("data-totRemainPoint"); 
          	$.ajax({
    			id: 'pointGiftRegList',
    			url: '/m/mypage/pointGiftRegList.do?point='+v_totRemainPoint,
    			type: 'get',
    			data: '',
    			dataType: "text",
    			async: false,
    			success: function(resp) {
    				giftContentsCallback(resp);
    			}
    		});
      	});
      </script>

