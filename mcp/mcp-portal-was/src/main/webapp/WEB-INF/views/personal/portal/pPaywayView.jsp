<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutNoGnbDefault">

  <t:putAttribute name="titleAttr">납부방법 변경</t:putAttribute>

  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/common/validator.js?version=25.06.10"></script>
    <script type="text/javascript" src="/resources/js/personal/portal/pPaywayView.js?version=25.06.10"></script>
    <script type="text/javascript" src="/resources/js/personal/personalAuth.js?version=25.06.10"></script>
  </t:putAttribute>

  <t:putAttribute name="contentAttr">

    <input type="hidden" id="pageType"  name="pageType"   value="${pageType}">
    <input type="hidden" id="payMethod" name="payMethod"  value="${payMethod}">
    <input type="hidden" id="payBizrCd" name="payBizrCd"  value="${payBizrCd}">
    <input type="hidden" id="ncn"       name="ncn"        value="${ncn}">
    <input type="hidden" id="isAuth"    name="isAuth"     value="">

    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">개인화 URL (납부방법 변경)</h2>
      </div>
      <div class="c-row c-row--lg">
        <div class="c-form-wrap">
          <div class="c-form-group" role="group" aira-labelledby="radPayMethodHead">
            <h4 class="c-group--head u-fs-20" id="radPayMethodHead">납부방법 선택</h4>
            <div class="c-checktype-row c-col5">
              <input class="c-radio c-radio--button c-radio--button--type2" id="reqPayType1" type="radio" name="reqPayType" value="D" checked="checked">
              <label class="c-label" for="reqPayType1">
                <i class="c-icon c-icon--account" aria-hidden="true"></i>
                <span>계좌 자동이체</span>
              </label>
              <input class="c-radio c-radio--button c-radio--button--type2" id="reqPayType2" type="radio" name="reqPayType" value="C">
              <label class="c-label" for="reqPayType2">
                <i class="c-icon c-icon--card" aria-hidden="true"></i>
                <span>신용카드 자동납부</span>
              </label>
              <input class="c-radio c-radio--button c-radio--button--type2" id="reqPayType3" type="radio" name="reqPayType" value="R">
              <label class="c-label" for="reqPayType3">
                <i class="c-icon c-icon--giro" aria-hidden="true"></i>
                <span>지로납부</span>
              </label>
              <input class="c-radio c-radio--button c-radio--button--type2" id="reqPayType4" type="radio" name="reqPayType" value="K">
              <label class="c-label" for="reqPayType4">
                <i class="c-icon c-icon--kakaopay" aria-hidden="true"></i>
                <span>카카오 페이</span>
              </label>
              <input class="c-radio c-radio--button c-radio--button--type2" id="reqPayType5" type="radio" name="reqPayType" value="P">
              <label class="c-label" for="reqPayType5">
                <i class="c-icon c-icon--payco" aria-hidden="true"></i>
                <span>페이코</span>
              </label>
            </div>
          </div>

          <!-- 계좌 -->
          <div id="bank">
            <div class="c-form-group u-mt--48" role="group" aria-labelledby="inpbankInfoHead">
              <h4 class="c-group--head u-fs-20" id="inpbankInfoHead">계좌 정보 입력</h4>
              <div class="c-form-row c-col2">
                <div class="c-form-field">
                  <div class="c-form__select u-mt--0">
                    <c:set var="bankList" value="${nmcp:getCodeList('BNK')}" />
                    <select class="c-select" id="blBankCode">
                      <option value="">은행명</option>
                      <c:if test="${bankList ne null and !empty bankList}">
                        <c:forEach items="${bankList}" var="bankList" >
                          <option value="${bankList.expnsnStrVal1}">${bankList.dtlCdNm}</option>
                        </c:forEach>
                      </c:if>
                    </select>
                    <label class="c-form__label" for="blBankCode">은행명</label>
                  </div>
                </div>
                <div class="c-form-field">
                  <div class="c-form__input u-mt--0">
                    <input class="c-input numOnly" id="bankAcctNo" name="bankAcctNo" type="text" maxlength='20' placeholder="'-'없이 입력">
                    <label class="c-form__label" for="bankAcctNo">계좌번호</label>
                  </div>
                </div>
              </div>
            </div>
            <div class="c-box c-box--type1 c-box--bullet u-mt--20">
              <ul class="c-text-list c-bullet c-bullet--dot">
                <li class="c-text-list__item">명의자 본인 명의의 계좌로만 변경 가능합니다.</li>
                <li class="c-text-list__item">납부방법은 고객센터(가입휴대폰에서 114)를 통해서도 변경 가능합니다.</li>
              </ul>
            </div>
            <h4 class="c-form__title u-fs-20 u-mt--48">납부 기준일</h4>
            <div class="c-form-field u-width--460">
              <div class="c-form__select">
                <select class="c-select" id="cycleDueDay" name="cycleDueDay">
                  <option value="">기준일 선택</option>
                  <option value="18">매월18일</option>
                  <option value="21">매월21일</option>
                  <option value="25">매월25일</option>
                </select>
                <label class="c-form__label" for="cycleDueDay">기준일 선택</label>
              </div>
            </div>
            <div class="c-button-wrap u-mt--40">
              <button class="c-button c-button-round--md c-button--mint c-button--w460" id="bankCheckBtn">계좌번호 유효성 체크</button>
            </div>
          </div>

          <!-- 카드 -->
          <div id="card" style="display:none;">
            <div class="c-form-group u-mt--48" role="group" aira-labelledby="inpPayInfoTitle">
              <h4 class="c-group--head u-fs-20" id="inpPayInfoTitle">납부 정보 입력</h4>
              <div class="c-form-row c-col2">
                <div class="c-form-field">
                  <div class="c-form__select u-mt--0">
                    <c:set var="cardList" value="${nmcp:getCodeList('CARD')}" />
                    <select class="c-select" id="cardCode">
                      <option value="">카드사명</option>
                      <c:if test="${cardList ne null and !empty cardList}">
                        <c:forEach items="${cardList}" var="cardList" >
                          <option value="${cardList.dtlCd}">${cardList.dtlCdNm}</option>
                        </c:forEach>
                      </c:if>
                    </select>
                    <label class="c-form__label" for="cardCode">카드사명</label>
                  </div>
                </div>
                <div class="c-form-field">
                  <div class="c-form__input u-mt--0">
                    <input class="c-input numOnly" id="creditCardNo" name="creditCardNo" maxlength="16" type="number" placeholder="'-'없이 입력">
                    <label class="c-form__label" for="creditCardNo">카드번호</label>
                  </div>
                </div>
              </div>
            </div>

            <h4 class="c-form__title u-fs-20 u-mt--48">납부 기준일</h4>
            <div class="c-form-field u-width--460">
              <div class="c-form__select">
                <select class="c-select" id="cardCycleDueDay" name="cardCycleDueDay">
                  <option value="">기준일 선택</option>
                  <option value="01">1회차(매월11일경)</option>
                  <option value="02">2회차(매월20일경)</option>
                </select>
                <label class="c-form__label" for="cardCycleDueDay">기준일 선택</label>
              </div>
            </div>

            <div class="c-form-group u-mt--48" role="group" aria-labelledby="inpcardInfoHead">
              <h3 class="c-group--head u-fs-20" id="inpcardInfoHead">신용카드 유효기간</h3>
              <div class="c-form-row c-col2">
                <div class="c-form-field">
                  <div class="c-form__select u-mt--0">
                    <select class="c-select" id="cardMM">
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
                    <label class="c-form__label" for="cardMM">MM</label>
                  </div>
                </div>
                <div class="c-form-field">
                  <div class="c-form__select u-mt--0">
                    <select class="c-select" id="cardYY">
                      <option value="">년(YYYY)</option>
                      <c:set var="y" value="${nowY}" />
                      <c:forEach begin="0" end="10" var="item">
                        <option value="${y+item}">${y+item}년</option>
                      </c:forEach>
                    </select>
                    <label class="c-form__label" for="cardYY">YY</label>
                  </div>
                </div>
              </div>
            </div>
            <div class="c-button-wrap u-mt--40">
              <button class="c-button c-button-round--md c-button--mint c-button--w460" id="cardCheckBtn">신용카드 유효성 체크</button>
            </div>
          </div>

          <!-- 지로 -->
          <div id="paper" style="display:none;">
            <div class="c-form-group u-mt--48" role="group" aira-labelledby="inpPaperPayInfoTitle">
              <h4 class="c-group--head u-fs-20" id="inpPaperPayInfoTitle">납부 정보 입력</h4>
              <div class="c-form-row c-col2">
                <div class="c-form">
                  <div class="c-input-wrap">
                    <label class="c-form__label c-hidden" for="adrZip">우편번호</label>
                    <input class="c-input" id="adrZip" name="adrZip" type="text" placeholder="우편번호" value="${zipCode}" readonly>
                    <button class="c-button c-button--sm c-button--underline" onclick="openPage('pullPopup', '/addPopup.do','','1');">우편번호찾기</button>
                  </div>
                </div>
                <div class="c-form">
                  <div class="c-input-wrap">
                    <label class="c-form__label c-hidden" for="adrPrimaryLn">주소</label>
                    <input class="c-input" id="adrPrimaryLn" name="adrPrimaryLn" type="text" placeholder="주소" value="${pAddr}" readonly>
                  </div>
                </div>
              </div>
              <div class="c-form u-mt--16">
                <label class="c-label c-hidden" for="adrSecondaryLn">상세주소</label>
                <input class="c-input" id="adrSecondaryLn" name="adrSecondaryLn" type="text" placeholder="상세주소" readonly value="${sAddr}">
              </div>
            </div>
            <h4 class="c-form__title u-fs-20 u-mt--48">납부 기준일</h4>
            <div class="c-form-field u-width--460">
              <div class="c-form__select">
                <select class="c-select" id="giroCycleDueDay" name="giroCycleDueDay">
                  <option value="">기준일 선택</option>
                  <option value="25">매월25일</option>
                  <option value="27">매월27일</option>
                </select>
                <label class="c-form__label" for="giroCycleDueDay">기준일 선택</label>
              </div>
            </div>
          </div>

          <!-- 본인인증 -->
          <div id="auth">
            <h4 class="c-form__title u-fs-20 u-mt--48">본인인증 방법 선택</h4>
            <div class="c-button-wrap">
              <button  class="c-button--center _btnNiceAuthBut" data-online-auth-type="M">
                <i class="c-icon c-icon--phone" aria-hidden="true"></i>휴대폰 인증하기
              </button>
              <button class="c-button--center _btnNiceAuthBut" data-online-auth-type="C">
                <i class="c-icon c-icon--card" aria-hidden="true"></i>신용카드 인증하기
              </button>
            </div>
            <div class="c-box c-box--type1 c-box--bullet u-mt--20">
              <ul class="c-text-list c-bullet c-bullet--dot">
                <li class="c-text-list__item">고객님의 본인명의(미성년자의 경우 법정 대리인)의 국내발행 신용카드 정보를 입력해주세요.(체크카드 인증불가)</li>
                <li class="c-text-list__item">신용카드 비밀번호 3회 이상 오류 시 해당카드로 인증을받으실 수 없으니 유의하시기 바랍니다.</li>
              </ul>
            </div>
            <div class="c-accordion c-accordion--type1 payway" data-ui-accordion>
              <div class="c-accordion__box">
                <div class="c-accordion__item">
                  <div class="c-accordion__head">
                    <button class="runtime-data-insert c-accordion__button" id="acc_headerA1" type="button" aria-expanded="false" aria-controls="acc_contentA1">
                      <span class="c-hidden">납부방법 변경 본인인증 절차 동의 상세열기</span>
                    </button>
                    <div class="c-accordion__check">
                      <input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree">
                      <label class="c-label" for="chkAgree">납부방법 변경 본인인증 절차 동의</label>
                    </div>
                  </div>
                  <div class="c-accordion__panel expand" id="acc_contentA1" aria-labelledby="acc_headerA1">
                    <div class="c-accordion__inside">
                      <p>안전한 서비스 이용 및 고객님의 개인정보 보호를 위해 본인 인증을 받으신 고객분만 이용이 가능합니다. 서비스 이용을 위해서 본인확인 절차를 진행해 주세요.</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="c-button-wrap u-mt--56">
              <a class="c-button c-button--lg c-button--gray u-width--220 cancelBtn" href="javascript:void(0);">취소</a>
              <button class="c-button c-button--lg c-button--primary u-width--220 changeBtn">납부방법 변경</button>
            </div>
          </div>

          <!-- 카카오 -->
          <div id="kakao" style="display: none;">
            <h4 class="c-heading c-heading--fs20 u-mt--48">카카오페이 자동납부 신청방법</h4>
            <ul class="c-step-list c-step-list--type3 col-3">
              <li class="c-step-list__item">
                <span class="c-step-list__label">STEP 1</span>
                <strong class="c-step-list__title">SMS전송</strong>
                <i class="c-icon c-icon--send" aria-hidden="true"></i>
                <div class="c-button-wrap u-mt--24">
                  <a class="c-button c-button--md c-button--gray cancelBtn" href="javascript:void(0);">취소</a>
                  <button class="c-button c-button--md c-button--mint smsPayBtn">SMS 전송</button>
                </div>
                <p class="c-bullet c-bullet--fyr u-co-mint u-mt--32">전송된 SMS내의 링크를 통해 카카오페이 자동 납부신청이 가능합니다.</p>
              </li>
              <li class="c-step-list__item u-pb--32">
                <span class="c-step-list__label">STEP 2</span>
                <strong class="c-step-list__title"> 카카오톡 or 카카오페이로 <br>자동납부 신청하기</strong>
                <ul class="app-step">
                  <li class="app-step__item"><span class="app-step__step">1</span>
                    <strong class="app-step__title">카카오톡 APP 내 더보기 ▸ 카카오페이</strong>
                    <div class="app-step__image" aria-hidden="true">
                      <img src="/resources/images/portal/content/img_kakaopay_01.png" alt="">
                    </div>
                  </li>
                  <li class="app-step__item"><span class="app-step__step">2</span>
                    <strong class="app-step__title">카카오페이 내 전체 ▸ 청구서</strong>
                    <div class="app-step__image" aria-hidden="true">
                      <img src="/resources/images/portal/content/img_kakaopay_02.png" alt="">
                    </div>
                  </li>
                  <li class="app-step__item"><span class="app-step__step">3</span>
                    <strong class="app-step__title">청구서 ▸ 자동납부신청</strong>
                    <div class="app-step__image" aria-hidden="true">
                      <img src="/resources/images/portal/content/img_kakaopay_03.png" alt="">
                    </div>
                  </li>
                  <li class="app-step__item"><span class="app-step__step">4</span>
                    <strong class="app-step__title">자동납부 신청관리 ▸ KT 선택</strong>
                    <div class="app-step__image" aria-hidden="true">
                      <img src="/resources/images/portal/content/img_kakaopay_04.png" alt="">
                    </div>
                  </li>
                  <li class="app-step__item"><span class="app-step__step">5</span>
                    <strong class="app-step__title">청구 정보 확인 후 신청하기</strong>
                    <div class="app-step__image" aria-hidden="true">
                      <img src="/resources/images/portal/content/img_kakaopay_05.png" alt="">
                    </div>
                  </li>
                  <li class="app-step__item"><span class="app-step__step">6</span>
                    <strong class="app-step__title">카카오페이 머니 등록하기</strong>
                    <div class="app-step__image" aria-hidden="true">
                      <img src="/resources/images/portal/content/img_kakaopay_06.png" alt="">
                    </div>
                  </li>
                </ul>
                <hr class="c-hr c-hr--basic">
                <ul class="c-text-list c-bullet c-bullet--dot u-ta-left">
                  <li class="c-text-list__item">카카오페이로 자동납부 신청하기
                    <p class="c-bullet c-bullet--hyphen u-co-gray u-ml--0">카카오페이 APP ▸ 더보기 ▸ 청구서 ▸ 카카오톡 APP 내 청구서 ▸ 자동납부신청</p>
                  </li>
                </ul>
              </li>
            </ul>
          </div>

          <!-- 페이코 -->
          <div id="payCo" style="display: none;">
            <h4 class="c-heading c-heading--fs20 u-mt--48">페이코 자동납부 신청방법</h4>
            <ul class="c-step-list c-step-list--type3 col-4">
              <li class="c-step-list__item">
                <span class="c-step-list__label">STEP 1</span>
                <strong class="c-step-list__title">SMS전송</strong>
                <i class="c-icon c-icon--send" aria-hidden="true"></i>
                <div class="c-button-wrap u-mt--24">
                  <a class="c-button c-button--md c-button--gray cancelBtn" href="javascript:void(0);">취소</a>
                  <button class="c-button c-button--md c-button--mint smsPayBtn">SMS 전송</button>
                </div>
                <p class="c-bullet c-bullet--fyr u-co-mint u-mt--32">전송된 SMS내의 링크를 통해 페이코 자동 납부신청이 가능합니다.</p>
              </li>
              <li class="c-step-list__item u-pb--8">
                <span class="c-step-list__label">STEP 2</span>
                <strong class="c-step-list__title">페이코로 자동납부 신청하기</strong>
                <ul class="app-step">
                  <li class="app-step__item"><span class="app-step__step">1</span>
                    <strong class="app-step__title"> 페이코 APP ▸ 라이프 ▸ 라이프 더보기 <br>▸ 전자문서함 ▸ KT 자동납부	</strong>
                    <div class="app-step__image" aria-hidden="true">
                      <img src="/resources/images/portal/content/img_payco_01.png" alt="">
                    </div>
                  </li>
                  <li class="app-step__item"><span class="app-step__step">2</span>
                    <strong class="app-step__title">청구정보 조회 및 선택</strong>
                    <div class="app-step__image" aria-hidden="true">
                      <img src="/resources/images/portal/content/img_payco_02.png" alt="">
                    </div>
                  </li>
                  <li class="app-step__item"><span class="app-step__step">3</span>
                    <strong class="app-step__title">자동납부수단(포인트)등록</strong>
                    <div class="app-step__image" aria-hidden="true">
                      <img src="/resources/images/portal/content/img_payco_03.png" alt="">
                    </div>
                  </li>
                  <li class="app-step__item"><span class="app-step__step">4</span>
                    <strong class="app-step__title">신청완료</strong>
                    <div class="app-step__image" aria-hidden="true">
                      <img src="/resources/images/portal/content/img_payco_04.png" alt="">
                    </div>
                  </li>
                </ul>
              </li>
            </ul>
          </div>
        </div>
        <b class="c-flex c-text c-text--fs15 u-mt--64">
          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
          <span class="u-ml--4">알려드립니다</span>
        </b>
        <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
          <li class="c-text-list__item u-co-gray">자동납부(계좌/카드)를 이용하시는 경우, 당월 청구금액 납부 시 이중 납부가 될 수 있으니 납부일 및 계좌/카드 거래내역 확인 후 이용하시기 바랍니다.</li>
          <li class="c-text-list__item u-co-gray">입금확인 일자는 은행,신용카드 등 에서 케이티 엠모바일에 납부사실을 통보한 일자로, 납부하신 날짜와 입금확인 일자가 다소 차이가 날 수 있습니다.</li>
          <li class="c-text-list__item u-co-gray">납부사실 통보에 소요되는 시간이 각 은행과 신용카드 기관별로 차이가 있기 때문에, 입금확인 일자가 서로 다를 수 있습니다.</li>
          <li class="c-text-list__item u-co-gray">미납 요금계에 마이너스(-)로 표기된 경우는 지난달 과납 등으로 발생된 금액입니다.</li>
          <li class="c-text-list__item u-co-gray">상세내역은 최근 6개월까지 요금내역만 제공됩니다.</li>
          <li class="c-text-list__item u-co-gray">미납요금을 납부한 고객일지라도, 명세서 상에 미납요금이 표기되어 있을 수 있습니다. (명세서 상 미납요금은 청구시점의 미납요금이 표기된 것으로 현 시점의 미납요금과 상이할 수 있음)</li>
          <li class="c-text-list__item u-co-gray">납부변경 적용일 동일한 납부방법으로 변경 시 : 최소 3일 전까지 변경시 당월적용(변경 요청 시점이 은행, 카드사로 당월 납부요청이 지난 후 에는 변경한 납부방법이 다음달로 예약)</li>
          <li class="c-text-list__item u-co-gray">다른 납부방법으로 변경 시 : 익월적용</li>
        </ul>
      </div>
    </div>
  </t:putAttribute>
</t:insertDefinition>