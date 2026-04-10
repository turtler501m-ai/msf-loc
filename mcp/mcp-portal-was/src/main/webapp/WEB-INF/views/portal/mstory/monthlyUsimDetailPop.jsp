 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>

  <script src="../../resources/js/portal/vendor/swiper.min.js"></script>

<div class="c-modal__content">
  <div class="c-modal__header">
    <h2 class="c-modal__title" id="mstorage_pop_title">월간 M</h2>
    <c:if test="${'new' eq key }">
    <button class="c-button" data-dialog-close>
      <i class="c-icon c-icon--close" aria-hidden="true"></i>
      <span class="c-hidden">팝업닫기</span>
    </button>
    </c:if>
  </div>
  <div class="c-modal__body u-pt--32">
    <div class="m-storage-detail">
      <div class="m-storage-detail__title">
        <div class="m-storage-detail__title-txt">[${mstoryListDto.mstoryDto.mm}월] ${mstoryListDto.mstoryDto.ntcartTitle}</div>
        <div class="m-storage-detail__title-info">
          <div class="m-storage-detail__title-date">
          	<fmt:parseDate value="${mstoryListDto.mstoryDto.ntcartDate}" var="postDt" pattern="yyyyMMdd"/>
			<fmt:formatDate value="${postDt}" pattern="yyyy.MM.dd"/>
          </div>
          <div class="m-storage-detail__title-view">조회 | ${mstoryListDto.mstoryDto.hitCnt}</div>
        </div>
      </div>
      <div class="m-storage-detail__content">
        <div class="m-storage-detail__swiper">
          <div class="swiper-container">
            <div class="swiper-wrapper">
            	<c:forEach var="list" items="${mstoryListDto.mstoryAttDtoList}" varStatus="status">
					<div class="swiper-slide">
						<img src="${list.imgNm}" alt="${list.imgDesc}">
					</div>
				</c:forEach>

            </div>
          </div>
          <button class="swiper-button-next" type="button" tabindex="-1"></button>
          <button class="swiper-button-prev" type="button" tabindex="-1"></button>
          <div class="swiper-pagination" aria-hidden="true" tabindex="-1"></div>
        </div>
      </div>
      <div class="m-storage-detail__pager">
      	<c:forEach var="list" items="${mstoryListDto.mstoryDetailList}" varStatus="status">
      		<c:if test="${list.mm eq mstoryListDto.mstoryDto.mm}">
				<c:set var="mon" value="${status.index}"/>
				<c:set var="len" value="${fn:length(mstoryListDto.mstoryDetailList)}"/>
				<c:if test="${mon ne 0 }">
					<a class="m-storage-prev" href="#n" role="button" onclick="setNewPopData(${mstoryListDto.mstoryDetailList[mon - 1].ntcartSeq})">
			          <i class="c-icon c-icon--triangle-start" aria-hidden="true"></i>이전호<div class="c-hidden">보기</div>
			        </a>
		        </c:if>
		        <c:if test="${mon ne (len - 1)}">
			        <a class="m-storage-next u-ml--40" href="#n" role="button" onclick="setNewPopData(${mstoryListDto.mstoryDetailList[mon + 1].ntcartSeq})">
			         다음호<div class="c-hidden">보기</div>
			          <i class="c-icon c-icon--triangle-end" aria-hidden="true"></i>
			        </a>
		        </c:if>
			</c:if>
      	</c:forEach>
      	
      </div>
    </div>
  </div>
</div>