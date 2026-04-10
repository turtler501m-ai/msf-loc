 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<div class="c-modal" id="eventPop" role="dialog" tabindex="-1" aria-describedby="#invite-event-title">
  <script type="text/javascript" src="/resources/js/mobile/event/eventDetail.js?version=2025-09-02"></script>
  <input type="hidden" name="ntcartSeq" value="${eventDetail.ntcartSeq}">
  <div class="c-modal__dialog c-modal__dialog--full" role="document">
    <div class="c-modal__content">
      <div class="c-modal__header">
        <h1 class="c-modal__title" id="eventPopTitle">${eventPopTitle}</h1>
        <button class="c-button" data-dialog-close>
          <i class="c-icon c-icon--close" aria-hidden="true"></i>
          <span class="c-hidden">팝업닫기</span>
        </button>
      </div>
      <div class="c-modal__body">
        <div class="c-board c-board--type2">
          <div class="c-board__head">
            <strong class="c-board__title" id="eventTitla">${eventDetail.ntcartSubject}</strong>
            <div class="c-board__sub">
              <span>
                      <fmt:parseDate value="${eventDetail.eventStartDt}" var="stDt" pattern="yyyy-MM-dd HH:mm:ss"/>
                    <fmt:formatDate value="${stDt}" pattern="20yy.MM.dd"/>
                    ~
                    <fmt:parseDate value="${eventDetail.eventEndDt}" var="endDt" pattern="yyyy-MM-dd HH:mm:ss"/>
                    <fmt:formatDate value="${endDt}" pattern="20yy.MM.dd"/>
              </span>
            </div>
          </div>
          <div class="c-board__content pd-0">
            <article class="c-editor c-expand">
              <%-- <img src="${eventDetail.mobileListImgNm}" alt="${eventDetail.imgDesc}"> --%>
              ${eventDetail.ntcartSbst2}
            </article>
          </div>
        </div>
      </div>
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



 <div class="c-modal c-modal--event-summary" id="eventSummaryModal" role="dialog" tabindex="-1" aria-describedby="eventSummaryTitle">
   <div class="c-modal__dialog c-modal__dialog--full" role="document">
     <div class="c-modal__content">
       <div class="c-modal__header">
         <h1 class="c-modal__title" id="eventSummaryTitle">혜택 요약<span>08.01 ~ 08.15</span></h1>
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
         <button class="c-button c-button--full c-button--primary" data-dialog-close="">닫기</button>
       </div>
     </div>
   </div>
 </div>