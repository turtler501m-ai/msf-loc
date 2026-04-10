<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<script type="text/javascript" src="/resources/js/personal/portal/pAddSvcViewPop.js?version=25.06.10"></script>

<div class="c-modal__content">

  <input type="hidden" name="rateAdsvcCd" id="rateAdsvcCd" value="${rateAdsvcCd}"/>
  <input type="hidden" name="ncn" id="ncn" value="${ncn}"/>
  <input type="hidden" name="ftrNewParam" id="ftrNewParam" value=""/>
  <input type="hidden" name="baseVatAmt" id="baseVatAmt" value="${baseVatAmt}"/>

  <c:set var="today" value="<%=new java.util.Date()%>" />
  <c:set var="nowDate"><fmt:formatDate value="${today}" pattern="yyyy.MM.dd" /></c:set>
  <input type="hidden" name="now" id="now" value="<c:out value="${nowDate}"/>"/>
  <input type="hidden" name="totalPoint" id="totalPoint" value="${custPoint.totRemainPoint}"/>
  <input type="hidden" name="couponPrice" id="couponPrice" value=""/>

  <div class="c-modal__header">
    <h2 class="c-modal__title" id="modalSMSTitle">부가서비스 신청</h2>
    <button class="c-button" data-dialog-close>
      <i class="c-icon c-icon--close" aria-hidden="true"></i>
      <span class="c-hidden">팝업닫기</span>
    </button>
  </div>

  <div class="c-modal__body">
    <h3 class="c-heading c-heading--fs20 c-heading--regular">선택 부가서비스 내역</h3>
    <div class="c-table u-mt--32">
      <table>
        <caption>신청 부가서비스 내역, 이용요금(VAT)포함으로 구성된 선택 부가서비스 내역 정보</caption>
        <colgroup>
          <col style="width: 50%">
          <col>
        </colgroup>
        <thead>
        <tr>
          <th scope="col">신청 부가서비스 내역</th>
          <th scope="col">이용요금(VAT)포함</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>요금할인</td>
          <td>1,000~20,000원</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="u-flex-between u-mt--48">
      <h3 class="c-heading c-heading--fs20 c-heading--regular">포인트 현황</h3>
      <span class="c-text c-text--fs13 u-co-gray">
        <fmt:parseDate value="${nowData}" var="startDt" pattern="yyyyMMdd"/>
        <fmt:formatDate value="${startDt}" pattern="yyyy.MM.dd"/> 기준
      </span>
    </div>
    <div class="point-box">
      <span class="point-box__title">
        <i class="c-icon c-icon--my-point" aria-hidden="true"></i>My 포인트
      </span>
      <span class="point-box__text u-co-sub-4">
        <c:choose>
          <c:when test="${!empty custPoint.totRemainPoint}">
            <fmt:formatNumber value="${custPoint.totRemainPoint}" type="number"/>
          </c:when>
          <c:otherwise>
            0
          </c:otherwise>
        </c:choose>
        P</span>
    </div>
    <h3 class="c-heading c-heading--fs20 c-heading--regular">할인금액</h3>
    <div class="c-form u-mt--32">
      <div class="c-input-wrap">
        <label class="c-label c-hidden" for="pointAmt">할인금액 입력</label>
        <input class="c-input numOnly" id="pointAmt" name="pointAmt" type="text" placeholder="최소 1,000P (500P단위 입력)" maxlength="10">
      </div>
    </div>
    <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
      <li class="c-text-list__item u-co-gray">요금할인 서비스는 보유한 포인트를 사용하여 익월 요금을 할인 받는 서비스입니다.</li>
      <li class="c-text-list__item u-co-gray">후불 요금제에 한정하여 신청 가능합니다.</li>
      <li class="c-text-list__item u-co-gray">할인 금액은 부가세 별도 금액으로 입력 해주세요.</li>
    </ul>
    <hr class="c-hr c-hr--basic u-mt--32">
    <b class="c-flex c-text c-text--fs15 u-mt--24">
      <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
      <span class="u-ml--4">알려드립니다</span>
    </b>
    <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
      <li class="c-text-list__item u-co-gray">변경하신 부가서비스는 즉시 적용 됩니다.</li>
      <li class="c-text-list__item u-co-gray">변경되는 내용을 모두 확인하시고 확인 혹은 취소를 선택해주세요.</li>
      <li class="c-text-list__item u-co-gray">부가서비스 월 중 신청/해지 시 해당월 월정액 및 무료제공 혜택은 각각 일할 적용 됩니다.</li>
      <li class="c-text-list__item u-co-gray">해지 시 재가입 또는 즉시 재가입이 불가능한 부가서비스가 있으므로 주의 바랍니다.</li>
      <li class="c-text-list__item u-co-gray">부가서비스 별로 실제 적용시점이 다른 경우가 있으니 신청/해지 전 확인하시기 바랍니다.</li>
    </ul>
  </div>
  <div class="c-modal__footer">
    <div class="c-button-wrap">
      <button class="c-button c-button--lg c-button--gray u-width--220" data-dialog-close>취소</button>
      <button class="c-button c-button--lg c-button--primary u-width--220" onclick="btn_pointReg();">확인</button>
    </div>
  </div>
</div>