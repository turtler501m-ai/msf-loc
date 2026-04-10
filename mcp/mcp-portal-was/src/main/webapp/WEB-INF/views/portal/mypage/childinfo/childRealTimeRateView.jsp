<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<script type="text/javascript" src="/resources/js/portal/mypage/childinfo/childRealTimeRateView.js"></script>

<div class="c-modal__content" >
  <div class="c-modal__header">
    <h2 class="c-modal__title" id="modalJoinInfoTitle">실시간 요금조회</h2>
    <button class="c-button no-print" data-dialog-close="">
      <i class="c-icon c-icon--close" aria-hidden="true"></i>
      <span class="c-hidden">팝업닫기</span>
    </button>
  </div>
  <div class="c-modal__body" id="cmodalbody">
    <div class="c-tabs c-tabs--type1">
      <div class="c-tabs__list">
        <a class="c-tabs__button" id="tab1" href="javascript:;" aria-current="page">월별금액 조회</a>
        <a class="c-tabs__button is-active" id="tab2" href="javascript:;">실시간요금 조회</a>
      </div>
    </div>
    <div class="c-tabs__panel u-block">
      <div class="c-row c-row--lg">
        <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--48">${childInfo.name}님</h3>
        <%@ include file="/WEB-INF/views/portal/mypage/childinfo/childCommCtn.jsp"%>
        <hr class="c-hr c-hr--title">
        <strong class="u-block c-heading c-heading--fs24 c-heading--regular u-mt--48 u-ta-center" id="rateNm">
          이용중인 요금제는<br><b>${rateNm}</b>입니다.
        </strong>
        <div class="pay-summary">
          <div class="pay-summary__chart">
            <div class="c-indicator" id="useDay"></div>
            <div class="pay-summary__chart-range" id="useTotalDay">
              <b class="start-date">1일</b>
            </div>
          </div>
          <div class="pay-summary__price">
            <span class="c-hidden">1일부터 오늘까지 사용한 누적 이용</span>
            <span class="c-text c-text--fs17 u-mr--20">실시간요금</span>
            <span class="c-text c-text--fs15 u-co-red" id="sumAmt"></span>
          </div>
        </div>
        <hr class="c-hr c-hr--title">
        <div class="u-flex-between u-mt--48" id="searchTime">
          <h4 class="c-heading c-heading--fs16">요금상세내역</h4>
        </div>

        <div class="pay-detail pay-detail--type2 u-mt--16" id="realFareVOList"></div>
        <div class="c-nodata" style="display: none;">
          <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
          <p class="c-nodata__text">조회결과가 없습니다.</p>
        </div>

        <b class="c-flex c-text c-text--fs15 u-mt--48">
          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
          <span class="u-ml--4">알려드립니다</span>
        </b>
        <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
          <li class="c-text-list__item u-co-gray">실시간 요금조회는 정확한 금액이 아니며, 청구서에 표기되는 금액과 다를 수 있으므로 참고용으로 확인 부탁 드립니다.</li>
          <li class="c-text-list__item u-co-gray">국제 로밍 이용료 실시간 요금 조회는 해외 통신 사업자의 사정상 1일 ~ 2일 이상 차이가 날 수 있습니다.</li>
          <li class="c-text-list__item u-co-gray">현재 사용요금은 조회 반영 시점에 따라 최종요금과 다소 차이가 있을 수 있습니다.</li>
          <li class="c-text-list__item u-co-gray">월 중 번호변경 시 잔여가입비와 핸드폰 분납금은 변경 된 번호로 조회 됩니다.</li>
          <li class="c-text-list__item u-co-gray">요금제 및 부가서비스에 포함되는 각종 무료 제공 혜택(음성/데이터/문자 등)은 청구항목으로 표기되나, 실제 청구 시 에는 요금할인액으로(-)처리되어 부가되지 않을 수 있습니다.</li>
          <li class="c-text-list__item u-co-gray">요금제/부가서비스/데이터 상품 월중 신청 시 해당 월 요금제 월정액 및 제공 혜택은 각각 일할 적용됩니다.(무선인터넷 1MB = 1,024kb)</li>
          <li class="c-text-list__item u-co-gray">당월 명의변경 고객님은 가입일(변경일)부터의 요금으로 산정됩니다.</li>
          <li class="c-text-list__item u-co-gray">실시간 요금은 익월 10일 이후부터 납부가 가능합니다.</li>
        </ul>
      </div>
    </div>
  </div>
</div>