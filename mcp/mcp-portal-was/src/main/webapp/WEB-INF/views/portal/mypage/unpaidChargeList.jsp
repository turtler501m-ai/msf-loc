 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">즉시납부</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script src="/resources/js/portal/mypage/unpaidChargeList.js?version=23-11-13"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/jquery.number.min.js"></script>
        <script type="text/javascript" src="/resources/js/portal/popup/diAuth.js"></script>
        <script type="text/javascript"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

      <input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">

        <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">즉시납부</h2>
      </div>
      <div class="c-row c-row--lg">
        <h3 class="c-heading c-heading--fs20 c-heading--regular">조회할 회선을 선택해 주세요.</h3>
        <%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp" %>

        <hr class="c-hr c-hr--title u-mb--16">
        <div class="total-charge" id="totalCntView1" style="display: none;">
          <dl class="total-charge__price">
            <dt class="total-charge__title">납부 가능 금액</dt>
            <dd class="total-charge__text" id="totalCnt">
            </dd>
            <input type="hidden" name="totalCnt"  id="totalCntReal" value="0">
          </dl>
        </div>

        <h4 class="c-heading c-heading--fs16 u-mt--32" id="totalCntView2" style="display: none;">납부가능 요금 내역</h4>
        <input type="hidden" name="customerType" id="customerType" value="${customerType}"/>

        <c:if test="${'Y' eq customerType}">
          <div class="c-nodata" id="customerTypeNoData" >
           <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
           <p class="c-nodata__text">개인고객만 납부 가능합니다.</p>
         </div><!-- //-->
        </c:if>
        <div class="c-nodata unpaidNoDataView" style="display: none;" >
           <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
           <p class="c-nodata__text">조회 결과가 없습니다.</p>
         </div><!-- //-->
        <div class="c-table u-mt--16 chargeView1" id="realtimePayHtml" style="display: none;">
          <table>
            <caption>청구월, 요금으로 구성된 납부가능 요금 내역 정보</caption>
            <colgroup>
              <col style="width: 50%">
              <col>
            </colgroup>
            <thead>
              <tr>
                <th scope="col">청구월</th>
                <th scope="col">요금</th>
              </tr>
            </thead>
            <tbody id="realtimePayView">

            </tbody>
          </table>
        </div>
        <div class="c-form u-width--460 u-mt--48 chargeView1" style="display:none;">
          <div class="u-flex-between">
            <label class="c-label" for="pay">납부 금액 입력</label>
            <div class="c-checktype-row u-mt--0">
                <input class="c-checkbox c-checkbox--type3 " id="chkAll" name="chkAll" type="checkbox">
                <label class="c-label u-mr--0" for="chkAll">전액 납부</label>
            </div>
          </div>
          <div class="c-input-wrap u-mt--0">
            <input class="c-input numOnly" id="pay" name="pay"  type="text" placeholder="숫자만 입력" maxlength="10" value="">
          </div>
        </div>

        <div class="c-box c-box--type1 u-mt--16 chargeView1" style="display:none;">
            <ul class="c-text-list c-bullet c-bullet--dot">
                <li class="c-text-list__item u-co-gray">납부 가능한 요금 확인 후 납부하실 금액을 입력 해 주세요.</li>
                <li class="c-text-list__item u-co-gray">본인인증 후 납부 수단을 선택하실 수 있습니다.<br/>- 납부 가능 수단 : 신용/체크카드, 계좌이체, 카카오페이, 페이코, 네이버페이</li>
            </ul>
        </div>

        <div class="c-form-group u-mt--48 chargeView1" role="group" aira-labelledby="radCertiTypeTitle" style="display:none;">
          <h4 class="c-group--head" id="radCertiTypeTitle">본인인증</h4>
          <div class="c-button-wrap u-mt--16">
              <button class="c-button c-button--check c-button--mint" type="button"  onclick="auth_phone();">
                <i class="c-icon c-icon--phone" aria-hidden="true"></i>
                <span>휴대폰</span>
              </button>
              <button class="c-button c-button--check c-button--mint" type="button" onclick="auth_credit();">
                <i class="c-icon c-icon--card" aria-hidden="true"></i>
                <span>신용카드</span>
              </button>
              <button class="c-button c-button--check c-button--mint" type="button"  onclick="auth_ipin();">
                <i class="c-icon c-icon--ipin" aria-hidden="true"></i>
                <span>아이핀</span>
              </button>
          </div>
        </div>

        <div class="c-notice-wrap">
            <h5 class="c-notice__title">
                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                <span>알려드립니다</span>
            </h5>
            <ul class="c-notice__list">
                <li>자동납부(계좌/카드)를 이용하시는 상태에서 이번 달 청구 금액 납부 시 이중 납부가 될 수 있으므로 납부일 및 계좌/카드 거래 내역 확인 후 이용하시기 바랍니다.</li>
                <li>실시간 계좌이체는 07:00~23:00에 이용 가능하며, 은행별 서비스 시간에 따라 이용이 불가할 수 있습니다.<br />- 납부 가능 은행 : 기업은행, 국민은행, 농협, 우리은행, 제일은행, 신한은행, 하나은행</li>
                <li>실시간 계좌이체는 명의자와 납부자가 동일한 경우에만 이용이 가능합니다.</li>
                <li>국내 발급 카드로만 결제 가능합니다.</li>
                 <li>이중 납부된 경우, 자동납부는 취소가 불가능하며 즉시납부된 건만 취소가 가능합니다.</li>
            </ul>
        </div>

      </div>
    </div>
    </t:putAttribute>
</t:insertDefinition>
