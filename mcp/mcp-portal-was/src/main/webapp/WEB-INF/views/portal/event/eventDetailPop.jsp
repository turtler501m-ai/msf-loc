<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>


<div class="c-modal c-modal--event-pop" id="eventPop" role="dialog" aria-labelledby="#modalCardEventTitle">
  <script type="text/javascript" src="/resources/js/portal/event/eventDetail.js?version=2025-09-02"></script>
  <input type="hidden" name="ntcartSeq" value="${eventDetail.ntcartSeq}">

  <div class="c-modal__dialog" role="document">
	  <div class="c-modal__content">
	    <div class="c-modal__header">
	      <h2 class="c-modal__title" id="eventPopTitle">${eventPopTitle}</h2>
	     <button class="c-button" data-dialog-close>
	       <i class="c-icon c-icon--close" aria-hidden="true"></i>
	       <span class="c-hidden">팝업닫기</span>
	     </button>
	   </div>
	   <div class="c-modal__body">
	     <!-- 이벤트 상세 내용 불러오기-->
	     <div class="c-board c-board--type2">
	       <div class="c-board__head">
	         <strong class="c-board__title">
	           <span class="c-board__title__sub" id="eventTitle">${eventDetail.ntcartSubject}</span>
	         </strong>
	         <div class="c-board__sub">
	           <span class="c-board__sub__item">
	             <span class="sr-only">기간</span>
	             <fmt:parseDate value="${eventDetail.eventStartDt}" var="stDt" pattern="yyyy-MM-dd HH:mm:ss"/>
			<fmt:formatDate value="${stDt}" pattern="20yy.MM.dd"/>
			~
			<fmt:parseDate value="${eventDetail.eventEndDt}" var="endDt" pattern="yyyy-MM-dd HH:mm:ss"/>
			<fmt:formatDate value="${endDt}" pattern="20yy.MM.dd"/>
	           </span>
	         </div>
	       </div>
	       <div class="c-board__content">
	         <article class="c-editor">
	           <!--  에디터 내용-->
	           <%-- <img src="${eventDetail.listImg}" alt="${eventDetail.imgDesc}"> --%>
	           ${eventDetail.ntcartSbst}
	         </article>
	       </div>
	      </div>
	      <div id="divEventSummary" class="event-summary-button" style="display:none;">
		    <div class="event-summary-button__wrap">
		      <button data-dialog-trigger="#eventSummaryModal">
		        <i class="c-icon c-icon--event-summary" aria-hidden="true"></i>혜택 요약 보기
		      </button>
		    </div>
		  </div>
	    </div>
	  </div>
  </div>
</div>


<div class="c-modal c-modal--md c-modal--event-summary" id="eventSummaryModal" role="dialog" aria-labelledby="eventSummaryTitle" aria-modal="true" style="z-index: 105;">
  <div class="c-modal__dialog" role="document">
    <div class="c-modal__content">
      <div class="c-modal__header">
        <h2 class="c-modal__title" id="eventSummaryTitle">혜택 요약<span>08.01 ~ 08.15</span></h2>
        <button class="c-button" data-dialog-close="">
          <i class="c-icon c-icon--close" aria-hidden="true"></i>
          <span class="c-hidden">팝업닫기</span>
        </button>
      </div>
      <div class="c-modal__body">
        <ul class="event-summary__list">





        </ul>
      </div>
      <div class="c-modal__footer">
        <div class="c-button-wrap">
          <button class="c-button c-button--lg c-button--primary c-button--w340" data-dialog-close="">닫기</button>
        </div>
      </div>
    </div>
  </div>
</div>