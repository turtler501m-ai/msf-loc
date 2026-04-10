<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<script type="text/javascript" src="/resources/js/portal/mypage/childinfo/childCallView.js"></script>

<div class="c-modal__content" >
  <div class="c-modal__header">
    <h2 class="c-modal__title" id="modalJoinInfoTitle">이용량 조회</h2>
    <button class="c-button no-print" data-dialog-close="">
      <i class="c-icon c-icon--close" aria-hidden="true"></i>
      <span class="c-hidden">팝업닫기</span>
    </button>
  </div>
  <div class="c-modal__body" id="cmodalbody">
    <div class="c-row c-row--lg">
      <h3 class="c-heading c-heading--fs20 c-heading--regular">${childInfo.name}님</h3>
      <%@ include file="/WEB-INF/views/portal/mypage/childinfo/childCommCtn.jsp" %>
      <hr class="c-hr c-hr--title">
      <strong class="u-block c-heading c-heading--fs24 c-heading--regular u-mt--48 u-ta-center">이용중인 요금제는 <br> <b>${strSvcNameSms}</b>입니다.</strong>
      <div class="total-charge u-mt--24 u-bd--0">
        <div class="total-charge__panel">
          <ul class="product-summary">
            <li class="product-summary__item">
              <i class="c-icon c-icon--payment-data" aria-hidden="true"></i>
              <span class="product-summary__text">${mcpFarPriceDto.rateAdsvcLteDesc}</span>
            </li>
            <li class="product-summary__item">
              <i class="c-icon c-icon--payment-call" aria-hidden="true"></i>
              <span class="product-summary__text">${mcpFarPriceDto.rateAdsvcCallDesc}</span>
            </li>
            <li class="product-summary__item">
              <i class="c-icon c-icon--payment-sms" aria-hidden="true"></i>
              <span class="product-summary__text">${mcpFarPriceDto.rateAdsvcSmsDesc}</span>
            </li>
          </ul>
        </div>
      </div>

      <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">실시간 이용량</h3>
      <div class="c-select-search">
        <div class="c-form--line">
          <label class="c-label c-hidden" for="datalist">이용량 월 선택</label>
          <fmt:formatDate value="${toMonth}" pattern="yyyyMM" var="startDate" />
          <fmt:parseDate value="${startDate}" pattern="yyyyMMdd" var="parseDateOne" />
          <fmt:formatDate value="${parseDateOne}" pattern="yyyy.MM.dd" var="resultDate" />
          <fmt:formatDate value="${toMonth}" pattern="yyyy.MM.dd" var="resultDateTwo" />
          <fmt:formatDate value="${toMonth}" pattern="dd" var="today" />
          <input type="hidden" name="useMonth" value="${useMonth}" />
          <select class="c-select" id="datalist" name="datalist">
            <c:forEach items="${todayList}" var="datalist">
              <c:set var="dataMonth" value="${fn:replace(fn:substring(datalist,0,7), '.','')}" />
              <option value="${dataMonth}" <c:if test="${useMonth eq dataMonth}">selected</c:if>>${datalist}</option>
            </c:forEach>
          </select>
        </div>
        <button class="c-button c-button-round--xsm c-button--primary u-ml--10" type="button" onclick="btn_search();">조회</button>
      </div>
      <hr class="c-hr c-hr--title">

      <div class="u-ta-center u-mt--48" id="useTimeSvcLimit" style="display: none;">
        <i class="c-icon c-icon--notice-sm" aria-hidden="true"></i>
        <p class="c-text c-text--fs16 u-mt--16">
          안정적인 서비스를 위해 이용량 조회는 <b>120분 내 20회</b>로 제한하고 있습니다. <br>잠시 후 이용해 주시기 바랍니다.
        </p>
      </div>

      <div class="c-nodata" style="display: none;">
        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
        <p class="c-nodata__text">이용량 조회 결과가 없습니다.</p>
      </div>

      <div id="callInfo">
        <ul class="usage-chart u-mt--64"></ul>
        <div id="dataInfo"></div>
        <div id="voiceInfo"></div>
        <div id="smsInfo"></div>
      </div>

      <div class="c-button-wrap u-mt--56">
        <a class="c-button c-button--lg c-button--white c-button--w460" url="/mypage/childRealTimeRateView.do" title="실시간 요금 페이지로 이동하기" onclick="changeDetailPopup(this)">실시간 요금조회</a>
      </div>

      <b class="c-flex c-text c-text--fs15 u-mt--48">
        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
        <span class="u-ml--4">알려드립니다</span>
      </b>
      <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
        <li class="c-text-list__item u-co-red">기본제공량 모두 소진 후 초과 사용량은 조회가 불가하며 초과사용 금액은 실시간 요금조회에서 확인 부탁드립니다.</li>
        <li class="c-text-list__item u-co-gray">요금제/부가서비스/데이터상품에 대한 요금제 및 제공 혜택은 신청월의 사용기간에 따라 일할 적용됩니다.</li>
        <li class="c-text-list__item u-co-gray">용량집계에 소요되는 시간 때문에 실제 사용시점과 다소 차이가 있을 수 있으므로, 이용량은 참고용으로 이용해주시기 바랍니다. 기본 제공량 외 추가 사용요금,
          국제전화 요금, 소액결제 금액 등을 포함한 최종금액은 요금 청구작업 후 확정되오니, 다음달 사용요금을 함께 확인하시기 바랍니다.
        </li>
      </ul>
    </div>
  </div>
</div>