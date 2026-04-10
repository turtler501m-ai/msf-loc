<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/common/validator.js?version=22.08.18"></script>
        <script type="text/javascript" src="/resources/js/mobile/mypage/realTimePaymentView.js?version=23-08-10"></script>
    </t:putAttribute>

 <t:putAttribute name="contentAttr">
     <div id="content">
    <div class="ly-wrap">
    <div class="ly-content" id="main-content">
    <div class="ly-page-sticky">
        <h2 class="ly-page__head">즉시납부<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <div class="c-form u-mt--32">
        <div class="c-form__select has-value">
          <select class="c-select" id="paySelect" >
            <option value="C" selected>신용/체크카드</option>
              <c:if test="${isPayTransfer eq'Y'}" >
                  <option value="D">계좌이체</option>
              </c:if>

              <option value="K">카카오페이</option>
              <option value="P">페이코</option>
              <option value="N">네이버페이</option>
          </select>
          <label class="c-form__label">납부방법 선택</label>
        </div>
      </div>
      <input type="hidden" id="payMentMoney" name="payMentMoney" value="${payMentMoney}"/>
      <input type="hidden" id="contractNum" name="contractNum" value="${contractNum}"/>

      <!-- 신용카드 -->
      <div class="c-form payCard">
        <span class="c-form__title" id="inpG">납부 정보 입력</span>
        <div class="c-form__group" role="group" aria-labelledby="inpG">
          <div class="c-form__input">
            <input class="c-input inputPay numOnly" id="inpCardnum" type="number" placeholder="카드번호" maxlength="16">
            <label class="c-form__label" for="inpCardnum">카드번호</label>
          </div>
        </div>
      </div>
      <div class="c-form payCard">
        <div class="c-form__title">신용카드 유효기간</div>
        <div class="c-select-wrap">
          <select class="c-select inputPay" id="reqCardMmTmp" name="reqCardMmTmp" title="카드유효기간 월 선택">
            <option value="">월(MM)</option>
            <option value="01">1월</option>
            <option value="02">2월</option>
            <option value="03">3월</option>
            <option value="04">4월</option>
            <option value="05">5월</option>
            <option value="06">6월</option>
            <option value="07">7월</option>
            <option value="08">8월</option>
            <option value="09">9월</option>
            <option value="10">10월</option>
            <option value="11">11월</option>
            <option value="12">12월</option>
        </select>
        <c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />
        <input type="hidden" name="platFormCd" id="platFormCd" value="${platFormCd}"/>

        <c:set var="now" value="<%=new java.util.Date()%>" />
        <c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyyMM" /></c:set>
        <input type="hidden" name="sysYear" id="sysYear" value="${sysYear}"/>
        <select class="c-select inputPay" id="reqCardYyTmp" name="reqCardYyTmp" title="카드유효기간 년 선택">
            <option value="">년(YYYY)</option>
            <c:set var="y" value="${nowY}" />
            <c:forEach begin="0" end="10" var="item">
                <option value="${y+item}">${y+item}년</option>
            </c:forEach>
        </select>
        </div>
      </div>
      <div class="c-form payCard">
        <div class="c-form__title" id="inpG">카드비밀번호(앞 두자리)</div>
        <div class="c-form__group u-flex-start-center" role="group" aria-labelledby="inpG">
          <input class="c-input u-ta-center u-width--80  inputPay numOnly" type="password" maxlength="2" id="cardPwd">
          <span class="c-text u-ml--16">●</span>
          <span class="c-text u-ml--16">●</span>
        </div>
      </div>
      <div class="c-form payCard">
        <div class="c-form__title">할부기간</div>
        <select class="c-select inputPay" id="payments">
          <option value="0">일시불</option>
          <c:forEach begin="2" end="12" var="item">
              <fmt:formatNumber var="no" minIntegerDigits="2" value="${item}" type="number"/>
                <option value="${no}">${no}개월</option>
            </c:forEach>
        </select>
        <p class="c-bullet c-bullet--dot u-co-gray">5만원 이상인 경우 선택 가능</p>
      </div>
      <div class="c-agree payCard">
        <input class="c-checkbox c-checkbox--type2" id="chkAgree" type="checkbox" name="chkAgree">
        <label class="c-label" for="chkAgree">본인(예금주)은 납부해야 할 요금에 대해
          <br>위 카드 번호에서 인출(결제)되는 것에 동의합니다.
        </label>
      </div>
      <div class="c-button-wrap u-mt--48 u-mb--40 payCard">
        <button class="c-button c-button--full c-button--gray" onclick="btn_cancel();">취소</button>
        <button class="c-button c-button--full c-button--primary is-disabled" id="btn_payReg" onclick="btn_payReg('C')">즉시납부</button>
      </div>

      <!-- 계좌이체 -->
      <div class="c-form payTransfer" style="display: none;">
        <span class="c-form__title" id="idPayTranfer">납부 정보 입력</span>
        <div class="c-form__group" role="group" aria-labelledby="idPayTranfer">
          <div class="c-form__select inputPay">
	        <select class="c-select" id="blBankCode" name="blBankCode">
	            <option value="">은행명</option>
                <option value="0030000">기업은행</option>
                <option value="0040000">국민은행</option>
                <option value="0110000">농협</option>
                <option value="0200000">우리은행</option>
                <option value="0230000">SC제일은행</option>
                <option value="0880000">신한은행</option>
                <option value="0810000">KEB하나은행</option>
                <option value="0710000">우체국</option>
	        </select>
	        <label class="c-form__label">은행명</label>
	      </div>
          <div class="c-form__input">
            <input class="c-input inputPay numOnly" id="bankAcctNo" type="number" placeholder="'-'없이 입력">
            <label class="c-form__label" for="bankAcctNo">계좌번호</label>
          </div>
        </div>
      </div>
	  <div class="c-agree payTransfer" style="display: none;">
        <input class="c-checkbox c-checkbox--type2" id="chkAgree2" type="checkbox" name="chkAgree2">
        <label class="c-label" for="chkAgree2">본인(예금주)은 납부해야 할 요금에 대해 위 계좌에서 인출되는 것에 동의합니다.</label>
      </div>
      <div class="c-button-wrap u-mt--48 u-mb--40 payTransfer" style="display: none;">
        <button class="c-button c-button--full c-button--gray" onclick="btn_cancel();">취소</button>
        <button class="c-button c-button--full c-button--primary is-disabled" id="btnTransfer" onclick="btn_payReg('D')">즉시납부</button>
      </div>

      <!-- 카카오페이 -->
      <div class="complete payKakao">
        <div class="c-icon c-icon--pay-kakao" aria-hidden="true">
          <span></span>
        </div>
        <p class="complete__heading">
                         전송된 SMS 내의 링크를 통해<br/>
          <b>카카오톡</b>또는
          <br>
          <b>카카오페이 앱</b>을 실행하여
        </p>
        <p class="c-text c-text--type2 u-co-gray u-mt--12">요금을 즉시 납부할 수 있는 서비스 입니다.</p>
      </div>
      <hr class="c-hr c-hr--type2 payKakao">
      <ul class="c-text-list c-bullet c-bullet--dot payKakao">
        <li class="c-text-list__item u-co-gray">카카오톡 또는 카카오페이 앱을 먼저 설치하신 후 이용해주세요.</li>
        <li class="c-text-list__item u-co-gray">카카오페이 납부는 카카오페이 머니로만 납부 가능합니다.</li>
      </ul>
      <div class="c-button-wrap u-mt--48 u-mb--40 payKakao">
        <button class="c-button c-button--full c-button--gray" onclick="btn_cancel();">취소</button>
        <button class="c-button c-button--full c-button--primary" onclick="btn_payReg('KA')">SMS 전송</button>
      </div>
      <!-- 페이코 -->
       <div class="complete payPayco">
        <div class="c-icon c-icon--pay-payco" aria-hidden="true">
          <span></span>
        </div>
        <p class="complete__heading">
                         전송된 SMS 내의 링크를 통해<br/>
          <b>페이코 앱</b>을 실행하여
        </p>
        <p class="c-text c-text--type2 u-co-gray u-mt--12">요금을 즉시 납부할 수 있는 서비스 입니다.</p>
      </div>
      <hr class="c-hr c-hr--type2 payPayco">
      <ul class="c-text-list c-bullet c-bullet--dot payPayco">
          <li class="c-text-list__item u-co-gray">페이코 앱을 먼저 설치하신 후 이용해주세요.</li>
          <li class="c-text-list__item u-co-gray">페이코 납부는 페이코 포인트로만 납부 가능합니다.</li>
      </ul>
      <div class="c-button-wrap u-mt--48 u-mb--40 payPayco">
        <button class="c-button c-button--full c-button--gray" onclick="btn_cancel();">취소</button>
        <button class="c-button c-button--full c-button--primary" onclick="btn_payReg('PY')">SMS 전송</button>
      </div>
      <!-- 네이버 -->
      <div class="complete payNaver">
        <div class="c-icon c-icon--pay-naver" aria-hidden="true">
          <span></span>
        </div>
        <p class="complete__heading">
                         전송된 SMS 내의 링크를 통해<br/>
          <b>네이버페이 앱</b>을 실행하여
        </p>
        <p class="c-text c-text--type2 u-co-gray u-mt--12">요금을 즉시 납부할 수 있는 서비스 입니다.</p>
      </div>
      <hr class="c-hr c-hr--type2 payNaver">
      <ul class="c-text-list c-bullet c-bullet--dot payNaver">
           <li class="c-text-list__item u-co-gray">네이버페이 앱을 먼저 설치하신 후 이용해주세요.</li>
           <li class="c-text-list__item u-co-gray">네이버페이 납부는 네이버페이 포인트로만 납부 가능합니다.</li>
      </ul>
      <div class="c-button-wrap u-mt--48 u-mb--40 payNaver">
        <button class="c-button c-button--full c-button--gray" onclick="btn_cancel();">취소</button>
        <button class="c-button c-button--full c-button--primary" onclick="btn_payReg('NP')">SMS 전송</button>
      </div>

      <div class="c-notice-wrap">
	    <hr>
	    <h5 class="c-notice__title">
	        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
	        <span>알려드립니다</span>
	    </h5>
	    <ul class="c-notice__list">
	        <li>자동납부(계좌/카드)를 이용하시는 상태에서 이번 달 청구 금액 납부 시 이중 납부가 될 수 있으므로 납부일 및 계좌/카드 거래 내역 확인 후 이용하시기 바랍니다.</li>
            <li>실시간 계좌이체는 07:00~23:00에 이용 가능하며, 은행별 서비스 시간에 따라 이용이 불가할 수 있습니다.<br />- 납부 가능 은행 : 기업은행, 국민은행, 농협, 우리은행, SC제일은행, 신한은행, KEB하나은행, 우체국 </li>
            <li>실시간 계좌이체는 명의자와 납부자가 동일한 경우에만 이용이 가능합니다.</li>
	    </ul>
	  </div>

     </div>
   </div>
</div>
</t:putAttribute>
</t:insertDefinition>