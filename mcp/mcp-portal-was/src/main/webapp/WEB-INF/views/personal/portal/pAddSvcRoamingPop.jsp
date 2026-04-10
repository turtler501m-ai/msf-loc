<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<script src="/resources/js/portal/vendor/flatpickr.min.js"></script>
<script type="text/javascript" src="/resources/js/personal/portal/pAddSvcViewPop.js?version=25.06.10"></script>

<div class="c-modal__content">

  <input type="hidden" name="rateAdsvcCd" id="rateAdsvcCd" value="${rateAdsvcCd}"/>
  <input type="hidden" name="ncn" id="ncn" value="${ncn}"/>
  <input type="hidden" name="ftrNewParam" id="ftrNewParam" value=""/>
  <input type="hidden" name="baseVatAmt" id="baseVatAmt" value="${baseVatAmt}"/>
  <input type="hidden" name="now" id="now" value="${nowData}"/>
  <input type="hidden" name="stDt" id="stDt" value="<c:out value="${stDt}"/>"/>
  <input type="hidden" name="etDt" id="etDt" value="<c:out value="${etDt}"/>"/>
  <c:set var="now" value="<%=new java.util.Date()%>" />
  <c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
  <input type="hidden" name="todayTime" id="todayTime" value="<fmt:formatDate value="${now}" pattern="HH"/>"/>

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
          <td>(신)로밍 하루종일 ON</td>
          <td>${baseVatAmt} / 1일</td>
        </tr>
        </tbody>
      </table>
    </div>
    <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--48">
      이용기간 설정<span class="c-text c-text--fs14 u-co-gray-8 u-ml--4">(한국시간 기준)</span>
    </h3>
    <p class="c-text c-text--fs17 u-co-sub-4 u-mt--12">신청한 시간부터 24시간 동안 적용</p>
    <div class="c-form-wrap c-form-wrap--time u-mt--16">
      <div class="c-form-group" role="group" aira-labelledby="chkValUsimTitle">
        <h3 class="c-group--head c-hidden" id="chkValUsimTitle"></h3>
        <div class="c-form-row c-col2 u-mt--0">
          <div class="c-form">
            <div class="c-input-wrap u-width--320">
              <input class="c-input flatpickr-input" id="range" type="text" placeholder="시작일-종료일" title="시작일-종료일 입력">
              <span class="c-button c-button--icon c-button--calendar" type="button" title="날짜입력창 버튼" tabindex="-1">
                <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
              </span>
            </div>
          </div>
          <div class="c-form">
            <label class="c-label c-hidden" for="timeSel">시작시간선택</label>
            <select class="c-select c-select--time" id="timeSel" name="timeSel">
              <option label="시작시간선택" disabled selected>시작시간선택</option>
              <c:forEach var="i"  begin="0" end="23">
                <option value="${i>9?i:'0'}${i>9?'':i}">${i>9?i:'0'}${i>9?'':i}시</option>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
    </div>

    <p class="c-bullet c-bullet--dot u-co-gray u-mt--12">서비스 신청/변경이 완료되면 문자 메시지가 발송되오니 확인하여 주세요.</p>
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
      <button class="c-button c-button--lg c-button--primary u-width--220" type="button" onclick="btn_roamReg();">확인</button>
    </div>
  </div>
</div>

<script>
  var today = $("#now").val();
  var etDt = $("#etDt").val();

  KTM.datePicker('#range.flatpickr-input', {
     mode: 'range'
    ,dateFormat: "Y-m-d"
    ,conjunction:"~"
    ,minDate: new Date(today.substring(0,4) + "-" + today.substring(4,6) + '-' + today.substring(6,8))
    ,maxDate: new Date(etDt.substring(0,4) + "-" + etDt.substring(4,6) + '-' + etDt.substring(6,8))
  });
</script>