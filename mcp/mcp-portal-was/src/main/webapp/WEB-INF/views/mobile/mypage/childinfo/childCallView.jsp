<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="/resources/js/jquery.number.min.js"></script>
<script type="text/javascript" src="/resources/js/mobile/mypage/childinfo/childCallView.js"></script>

<div class="c-modal__content">
  <div class="c-modal__header">
    <h2 class="c-modal__title" id="ChangeProductTitle">이용량 조회</h2>
    <button class="c-button" data-dialog-close>
      <i class="c-icon c-icon--close" aria-hidden="true"></i>
      <span class="c-hidden">팝업닫기</span>
    </button>
  </div>

  <div class="c-modal__body">
    <h3 class="c-heading c-heading--type7 u-mt--32">${childInfo.name}님</h3>
    <%@ include file="/WEB-INF/views/mobile/mypage/childinfo/childCommCtn.jsp" %>
    <div class="banner-balloon u-mt--40 u-mb--24" style="min-height: auto !important;">
      <p>
        <span class="c-text c-text--type5">이용중인 요금제</span> <br>
        <b class="c-text c-text--type7" id="strSvcNameSms">${strSvcNameSms}</b>
        <img class="deco-img" src="/resources/images/mobile/content/img_bolloon_banner_03.png" alt="">
      </p>
    </div>
    <ul class="payment-extra-info u-mt--4 u-mb--32 u-plr--20">
      <li>
        <i class="c-icon c-icon--payment-data" aria-hidden="true"></i>
        <span class="c-text c-text--type7" id="rateAdsvcLteDesc">${mcpFarPriceDto.rateAdsvcLteDesc}</span>
      </li>
      <li>
        <i class="c-icon c-icon--payment-call" aria-hidden="true"></i>
        <span class="c-text c-text--type7" id="rateAdsvcCallDesc">${mcpFarPriceDto.rateAdsvcCallDesc}</span>
      </li>
      <li>
        <i class="c-icon c-icon--payment-sms" aria-hidden="true"></i>
        <span class="c-text c-text--type7" id="rateAdsvcSmsDesc">${mcpFarPriceDto.rateAdsvcSmsDesc}</span>
      </li>
    </ul>
    <hr class="c-hr c-hr--type3 c-expand">
    <div class="c-box u-mt--40">
      <div class="c-box__title flex-type--between">실시간 이용량</div>
      <div class="c-form u-width--60p">
        <fmt:formatDate value="${toMonth}" pattern="yyyyMM" var="startDate" />
        <fmt:parseDate value="${startDate}" pattern="yyyyMMdd" var="parseDateOne" />
        <fmt:formatDate value="${parseDateOne}" pattern="yyyy.MM.dd" var="resultDate" />
        <fmt:formatDate value="${toMonth}" pattern="yyyy.MM.dd" var="resultDateTwo" />
        <fmt:formatDate value="${toMonth}" pattern="dd" var="today" />
        <input type="hidden" name="useMonth" value="${useMonth}" />
        <select class="c-select c-select--type3" id="datalist">
          <c:forEach items="${todayList}" var="datalist">
            <c:set var="dataMonth" value="${fn:replace(fn:substring(datalist,0,7), '.','')}" />
            <option value="${dataMonth}" <c:if test="${useMonth eq dataMonth}">selected</c:if>>${datalist}</option>
          </c:forEach>
        </select>
      </div>

      <div class="u-ta-center u-mt--40" id="useTimeSvcLimit" style="display: none;">
        <i class="c-icon c-icon--notice-sm" aria-hidden="true"></i>
        <p class="c-text c-text--type3 u-mt--16">
          안정적인 서비스를 위해 이용량 조회는 <br> <b>120분 내 20회</b>로 제한하고 있습니다. <br>잠시 후 이용해 주시기 바랍니다.
        </p>
      </div>

      <div class="c-nodata" style="display: none;">
        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
        <p class="c-noadat__text">이용량 조회 결과가 없습니다.</p>
      </div>

      <!-- 사용량 -->
      <div class="callInfo" id="dataInfo"></div>
      <!-- 사용량 -->

      <!-- 음성/영상 -->
      <div class="callInfo" id="voiceInfo"></div>
      <!-- 음성/영상 -->

      <!-- 문자 -->
      <div class="callInfo" id="smsInfo"></div>
      <!-- 문자 -->

      <button class="c-button c-button--full c-button--white u-mt--20" url="/m/mypage/childRealTimeRateView.do" title="실시간 요금조회 이동하기" onclick="changeDetailPopup(this);">실시간 요금조회</button>

      <hr class="c-hr c-hr--type3 u-mt--40">
      <h3 class="c-heading c-heading--type6">
        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
        <span class="u-ml--4">알려드립니다.</span>
      </h3>
      <ul class="c-text-list c-bullet c-bullet--dot u-mb--32 u-mt--16">
        <li class="c-text-list__item u-co-red">기본제공량 모두 소진 후 초과 사용량은 조회가 불가하며 초과사용 금액은 실시간 요금조회에서 확인 부탁드립니다.</li>
        <li class="c-text-list__item u-co-gray">요금제/부가서비스/데이터상품에 대한 요금제 및 제공 혜택은 신청월의 사용기간에 따라 일할 적용됩니다.</li>
        <li class="c-text-list__item u-co-gray">용량집계에 소요되는 시간 때문에 실제
          사용시점과 다소 차이가 있을 수 있으므로, 사용량은 참고용으로 이용해주시기 바랍니다. 기본 제공량 외 추가 사용요금,
          국제전화 요금, 소액결제 금액 등을 포함한 최종금액은 요금 청구작업 후 확정되오니, 다음달 사용요금을 함께 확인하시기
          바랍니다.
        </li>
      </ul>
    </div>
  </div>
</div>