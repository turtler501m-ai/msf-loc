<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<script src="/resources/js/portal/mypage/regServicePop.js?version=22-05-30"></script>
<script type="text/javascript" src="/resources/js/portal/cs/inquiryBooking/bookingTimePop.js"></script>

<div class="c-modal__content">

  <div class="c-modal__header">
    <h2 class="c-modal__title" id="ChangeProductTitle">상담시간 선택</h2>
    <button class="c-button" data-dialog-close>
      <i class="c-icon c-icon--close" aria-hidden="true"></i>
      <span class="c-hidden">팝업닫기</span>
    </button>
  </div>

  <div class="c-modal__body">
    <!-- 모바일/포탈 구분 -->
    <c:set var="bookingRadioStyle" value="${mobileYN eq 'Y' ? '-0.5rem' : '0rem'}"></c:set>
    <c:set var="bookingButtonStyle" value="${mobileYN eq 'Y' ? 'c-button--full' : 'c-button--lg c-button--w220'}"></c:set>

    <div class="c-table c-table--product">
      <table>
        <caption>테이블 정보</caption>
        <colgroup>
          <col style="width: 50%">
          <col/>
        </colgroup>
        <thead>
        <tr>
          <th scope="col">시간대</th>
          <th scope="col">선택</th>
        </tr>
        </thead>
        <tbody id="timeListBody">
              <c:forEach var="time" items="${timeList}" varStatus="status">
                  <tr>
                    <td>${time.csResTmNm}</td>
                    <td>
                      <c:if test="${time.remainYn eq 'Y'}">
                        <input class="c-radio" id="${time.csResTmNm}" name="timeList" type="radio" name="radPayment" value="${time.csResTm}">
                        <label class="c-label"  for="${time.csResTmNm}" style="margin-right: ${bookingRadioStyle} !important;"><span class="c-hidden">선택</span></label>
                      </c:if>
                      <c:if test="${time.remainYn ne 'Y'}">-</c:if>
                    </td>
                  </tr>
              </c:forEach>
        </tbody>
      </table>
    </div>
  </div>

  <div class="c-modal__footer c-button-wrap u-mt--48">
    <button class="c-button c-button--primary ${bookingButtonStyle}" id="checkBtn" onclick="btnCheck()" data-dialog-close disabled>선택</button>
  </div>

</div>
