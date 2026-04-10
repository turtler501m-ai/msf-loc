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
        <script type="text/javascript" src="/resources/js/common/validator.js?version=22.08.18"></script>
        <script type="text/javascript" src="/resources/js/portal/mypage/realTimePaymentView.js?version=23-08-09"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
     <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">즉시납부</h2>
      </div>
      <div class="c-row c-row--lg">
       <input type="hidden" id="payMentMoney" name="payMentMoney" value="${payMentMoney}"/>
       <input type="hidden" id="contractNum" name="contractNum" value="${contractNum}"/>
       <!-- <input type="hidden" id="payCard" name="payCard"  class="paySelect" value="C"/> -->

        <!--<hr class="c-hr c-hr--title">-->
        <div class="c-form-wrap">


            <div class="c-form-group" role="group" aira-labelledby="radPayMethodHead">
                <h4 class="c-group--head" id="radPayMethodHead">납부방법 선택</h4>
                <div class="c-checktype-row c-col4">
                    <input class="c-radio c-radio--button c-radio--button--type2 paySelect" id="payCard" type="radio" value="C" name="radPayMethod" checked >
                    <label class="c-label" for="payCard">
                        <i class="c-icon c-icon--credit-card" aria-hidden="true"></i>
                        <span>신용/체크카드</span>
                    </label>
                    <c:if test="${isPayTransfer eq'Y'}" >
                        <input class="c-radio c-radio--button c-radio--button--type2 paySelect" id="payTransfer" type="radio"  value="D"  name="radPayMethod">
                        <label class="c-label" for="payTransfer">
                            <i class="c-icon c-icon--transfer" aria-hidden="true"></i>
                            <span>계좌이체</span>
                        </label>
                    </c:if>
                    <input class="c-radio c-radio--button c-radio--button--type2 paySelect" id="payKakao" type="radio"  value="K"  name="radPayMethod">
                    <label class="c-label" for="payKakao">
                        <i class="c-icon c-icon--kakaopay" aria-hidden="true"></i>
                        <span>카카오 페이</span>
                    </label>
                    <input class="c-radio c-radio--button c-radio--button--type2 paySelect" id="payPayco" type="radio" value="P" name="radPayMethod">
                    <label class="c-label" for="payPayco">
                        <i class="c-icon c-icon--payco" aria-hidden="true"></i>
                        <span>페이코</span>
                    </label>
                    <input class="c-radio c-radio--button c-radio--button--type2 paySelect" id="payNaver" type="radio"  value="N"  name="radPayMethod">
                    <label class="c-label" for="payNaver">
                        <i class="c-icon c-icon--naverpay" aria-hidden="true"></i>
                        <span>네이버페이</span>
                    </label>
                </div>
            </div>






          <div class="c-form-group u-mt--16 payCard" role="group" aira-labelledby="inpPayInfoTitle">
            <h4 class="c-group--head c-hidden" id="inpPayInfoTitle">납부 정보 입력</h4>
            <div class="c-form-row c-col2">
              <div class="c-form-field">
                <h4 class="c-form__title">납부정보 입력</h4>
                <div class="c-form__input u-mt--0">
                  <input class="c-input numOnly inputPay" id="inpCardnum" type="text" placeholder="'-'없이 입력" maxlength="16" value="">
                  <label class="c-form__label" for="inpCardnum">카드번호</label>
                </div>
              </div>
              <div class="c-form-field">
                <h4 class="c-form__title">신용카드 유효기간</h4>
                <div class="c-form-row c-col2">
                  <div class="c-form__select u-mt--0 u-width--220">
                    <select class="c-select inputPay" id="reqCardMmTmp" name="reqCardMmTmp">
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
                    <label class="c-form__label" for="reqCardMmTmp">월(MM)</label>
                  </div>
                  <div class="c-form__select u-mt--0 u-width--220">
                     <c:set var="now" value="<%=new java.util.Date()%>" />
                    <c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyyMM" /></c:set>
                    <input type="hidden" name="sysYear" id="sysYear" value="${sysYear}"/>
                    <select class="c-select inputPay" id="reqCardYyTmp" name="reqCardYyTmp">
                      <option value="">년(YYYY)</option>
                          <c:set var="y" value="${nowY}" />
                        <c:forEach begin="0" end="10" var="item">
                            <option value="${y+item}">${y+item}년</option>
                        </c:forEach>
                    </select>
                    <label class="c-form__label" for="reqCardYyTmp">년(YYYY)</label>
                  </div>
                </div>
              </div>
            </div>
            <div class="c-form-row c-col2 u-mt--32">
              <div class="c-form">
                <div class="c-input-wrap">
                  <label class="c-label" for="cardPwd">카드비밀번호 (앞 두자리)</label>
                  <input class="c-input u-width--70 numOnly inputPay" id="cardPwd"type="password" maxlength="2">
                  <span class="mask u-ml--12"></span>
                  <span class="mask u-ml--4"></span>
                </div>
              </div>
              <div class="c-form-field">
                <h4 class="c-form__title">할부기간<span class="c-bullet c-bullet--fyr u-ml--8 u-co-sub-4">5만원 이상인 경우 선택 가능</span>
                </h4>
                <div class="c-form__select u-mt--0">
                  <select class="c-select inputPay" id="payments">
                      <option value="0">일시불</option>
                   <c:forEach begin="2" end="12" var="item">
                       <fmt:formatNumber var="no" minIntegerDigits="2" value="${item}" type="number"/>
                        <option value="${no}">${no}개월</option>
                    </c:forEach>
                  </select>
                  <label class="c-form__label" for="payments">할부기간 선택</label>
                </div>
              </div>
            </div>
          </div>
        </div>
        <hr class="c-hr c-hr--basic u-mt--48 payCard">
        <div class="c-chk-wrap u-mt--16 payCard">
          <input class="c-checkbox" id="chkAgree" type="checkbox" id="chkAgree"  name="chkAgree">
          <label class="c-label" for="chkAgree">본인(예금주)은 납부해야 할 요금에 대해 위 카드 번호에서 인출(결제)되는 것에 동의합니다.</label>
        </div>
        <div class="c-button-wrap u-mt--56 payCard">
          <!-- 비활성화 시 .is-disabled 클래스 추가-->
          <a class="c-button c-button--lg c-button--gray u-width--220" href="javascript:void(0);" onclick="btn_cancel();">취소</a>
          <button class="c-button c-button--lg c-button--primary u-width--220 is-disabled" id="btn_payReg" type="button" onclick="btn_payReg('C')">즉시납부</button>
        </div>

        <!-- 계좌이체 -->
        <div class="c-form-group u-mt--32 payTransfer" role="group" aira-labelledby="inpPayTransTitle" style="display: none;">
          <h4 class="c-group--head c-hidden" id="inpPayTransTitle">납부 정보 입력</h4>
          <h4 class="c-form__title">납부정보 입력</h4>
          <div class="c-form-row c-col2">
            <div class="c-form-field">
              <div class="c-form__select u-mt--0">
                <select class="c-select inputPay" id="blBankCode" name="blBankCode">
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
                <label class="c-form__label" for="blBankCode">은행명</label>
              </div>
            </div>
            <div class="c-form-field">
              <div class="c-form__input u-mt--0">
                <input class="c-input numOnly inputPay" id="bankAcctNo" maxlength="40" type="text" placeholder="'-'없이 입력" value="">
                <label class="c-form__label" for="bankAcctNo">계좌번호</label>
              </div>
            </div>
          </div>
        </div>
        <hr class="c-hr c-hr--basic u-mt--48 payTransfer" style="display: none;">
	    <div class="c-chk-wrap u-mt--16 payTransfer" style="display: none;" >
	      <input class="c-checkbox" id="chkAgree2" type="checkbox" id="chkAgree2"  name="chkAgree2">
	      <label class="c-label" for="chkAgree2">본인(예금주)은 납부해야 할 요금에 대해 위 계좌에서 인출되는 것에 동의합니다.</label>
	    </div>
	    <div class="c-button-wrap u-mt--56 payTransfer" style="display: none;">
	      <!-- 비활성화 시 .is-disabled 클래스 추가-->
	      <a class="c-button c-button--lg c-button--gray u-width--220" href="javascript:void(0);" onclick="btn_cancel();">취소</a>
	      <button class="c-button c-button--lg c-button--primary u-width--220 is-disabled" type="button" id="btnTransfer"  onclick="btn_payReg('D')" >즉시납부</button>
	    </div>

         <!-- 카카오 -->
         <p class="c-text c-text--fs18 u-ta-center u-mt--56 payKakao" style="display: none;">
                          전송된 SMS 내의 링크를 통해<br/>
          <b>카카오톡</b>또는<b>카카오페이 앱</b>을 실행하여
          <br/>
          <b>요금을 즉시 납부</b>할 수 있는 서비스입니다.
        </p>
        <div class="c-box c-box--type1 u-mt--40 payKakao" style="display: none;">
          <ul class="c-text-list c-bullet c-bullet--dot">
            <li class="c-text-list__item u-co-gray">카카오톡 또는 카카오페이 앱을 먼저 설치하신 후 이용해주세요.</li>
            <li class="c-text-list__item u-co-gray">카카오페이 납부는 카카오페이 머니로만 납부 가능합니다.</li>
          </ul>
        </div>
        <div class="c-button-wrap u-mt--56 payKakao" style="display: none;">
          <!-- 비활성화 시 .is-disabled 클래스 추가-->
          <a class="c-button c-button--lg c-button--gray u-width--220" href="javascript:void(0);" onclick="btn_cancel();">취소</a>
          <button class="c-button c-button--lg c-button--primary u-width--220" type="button" onclick="btn_payReg('KA')">SMS 전송</button>
        </div>
        <!--  페이코  -->
        <p class="c-text c-text--fs18 u-ta-center u-mt--56 payPayco" style="display: none;">
                        전송된 SMS 내의 링크를 통해<br/>
          <b>페이코 앱</b>을 실행하여
          <br>
          <b>요금을 즉시 납부</b>할 수 있는 서비스입니다.
        </p>
        <div class="c-box c-box--type1 u-mt--40 payPayco" style="display: none;">
          <p class="c-bullet c-bullet--dot u-co-gray">페이코 앱을 먼저 설치하신 후 이용해주세요.</p>
          <p class="c-bullet c-bullet--dot u-co-gray">페이코 납부는 페이코 포인트로만 납부 가능합니다.</p>
        </div>
        <div class="c-button-wrap u-mt--56 payPayco" style="display: none;">
          <!-- 비활성화 시 .is-disabled 클래스 추가-->
          <a class="c-button c-button--lg c-button--gray u-width--220" href="javascript:void(0);" onclick="btn_cancel();">취소</a>
          <button class="c-button c-button--lg c-button--primary u-width--220" type="button"  onclick="btn_payReg('PY')">SMS 전송</button>
        </div>
         <p class="c-text c-text--fs18 u-ta-center u-mt--56 payNaver" style="display: none;">
                          전송된 SMS 내의 링크를 통해<br/>
          <b>네이버페이 앱을</b>을 실행하여
          <br>
          <b>요금을 즉시 납부</b>할 수 있는 서비스입니다.
         </p>
         <div class="c-box c-box--type1 u-mt--40 payNaver" style="display: none;">
            <p class="c-bullet c-bullet--dot u-co-gray">네이버 페이 앱을 먼저 설치하신 후 이용해주세요.</p>
            <p class="c-bullet c-bullet--dot u-co-gray">네이버페이 납부는 네이버페이 포인트로만 납부 가능합니다.</p>
         </div>
         <div class="c-button-wrap u-mt--56 payNaver" style="display: none;">
          <!-- 비활성화 시 .is-disabled 클래스 추가-->
          <a class="c-button c-button--lg c-button--gray u-width--220" href="javascript:void(0);" onclick="btn_cancel();">취소</a>
          <button class="c-button c-button--lg c-button--primary u-width--220" type="button"onclick="btn_payReg('NP');">SMS 전송</button>
        </div>

        <div class="c-notice-wrap">
            <h5 class="c-notice__title">
                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                <span>알려드립니다</span>
            </h5>
            <ul class="c-notice__list">
                <li>자동납부(계좌/카드)를 이용하시는 상태에서 이번 달 청구 금액 납부 시 이중 납부가 될 수 있으므로 납부일 및 계좌/카드 거래 내역 확인 후 이용하시기 바랍니다.</li>
                <li>실시간 계좌이체는 07:00~23:00에 이용 가능하며, 은행별 서비스 시간에 따라 이용이 불가할 수 있습니다.<br />- 납부 가능 은행 : 기업은행, 국민은행, 농협, 우리은행, SC제일은행, 신한은행, KEB하나은행, 우체국</li>
                <li>실시간 계좌이체는 명의자와 납부자가 동일한 경우에만 이용이 가능합니다.</li>
            </ul>
        </div>

      </div>
    </div>
    </t:putAttribute>
</t:insertDefinition>
