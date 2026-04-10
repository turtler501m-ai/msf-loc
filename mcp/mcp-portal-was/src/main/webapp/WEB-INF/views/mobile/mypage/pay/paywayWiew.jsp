<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/mypage/pay/paywayView.js?version=24.10.17"></script>
        <script type="text/javascript" src="/resources/js/mobile/popup/diAuth.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                        납부방법 변경
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="c-form">
                <span class="c-form__title u-fs-20 u-mt--28">납부방법 선택 </span>
                <div class="c-form__select has-value u-mt--0">
                    <select class="c-select"  id="reqPayType" name="reqPayType">
                        <option value="D" selected="selected">은행계좌 자동이체</option>
                        <option value="C">신용카드 자동납부</option>
                        <option value="R">지로납부</option>
                        <option value="K">카카오페이</option>
                        <option value="P">페이코</option>
                    </select>
                    <label class="c-form__label">납부방법 선택</label>
                </div>
            </div>
            <!-- 은행계좌 자동이체 -->
            <div id="bank">
                <div class="c-form">
                    <span class="c-form__title u-fs-20 u-mt--40" id="inpGp2">계좌 정보 입력</span>
                    <div class="c-form__group" role="group" aria-labelledby="inpGp2">
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
                            <label class="c-form__label">은행명</label>
                        </div>
                        <div class="c-form__input">
                            <input class="c-input numOnly" id="bankAcctNo" name="bankAcctNo" maxlength='20' type="tel" pattern="[0-9]*" placeholder="'-'없이 입력">
                            <label class="c-form__label" for="bankAcctNo">계좌번호</label>
                        </div>
                    </div>
                    <ul class="c-text-list c-bullet c-bullet--dot u-fs-14 u-mt--15">
                        <li class="c-text-list__item u-co-gray">명의자 본인 명의의 계좌로만 변경 가능합니다.</li>
                        <li class="c-text-list__item u-co-gray">납부방법은 고객센터(가입휴대폰에서 114)를 통해서도 변경 가능합니다.</li>
                        <li class="c-text-list__item u-co-gray">등록하신 자동이체로 결제된 내역은 취소되지 않습니다.</li>
                    </ul>
                </div>
                <div class="c-form">
                   <div class="c-form__title u-fs-20 u-mt--40">납부 기준일</div>
                   <div class="c-form__select u-mt--0">
                       <select class="c-select" id="cycleDueDay">
                           <option value="">기준일 선택</option>
                           <option value="18">매월18일</option>
                           <option value="21">매월21일</option>
                           <option value="25">매월25일</option>
                       </select>
                       <label class="c-form__label">기준일 선택</label>
                   </div>
                </div>
                <div class="c-button-wrap u-mt--36">
                    <button class="c-button c-button--full c-button--mint c-button--h48" id="bankCheckBtn">계좌번호 유효성 체크</button>
                </div>
            </div>
            <!-- 은행계좌 자동이체 -->

            <!-- 신용카드 자동납부 -->
            <div id="card" style="display:none;">
                <div class="c-form">
                    <span class="c-form__title u-fs-20" id="inpG">납부 정보 입력</span>
                    <div class="c-form__group" role="group" aria-labelledby="inpG">
                        <div class="c-form__select has-value">
                        <c:set var="cardList" value="${nmcp:getCodeList('CARD')}" />
                            <select class="c-select" id="cardCode">
                                <option value="">카드사명</option>
                                <c:if test="${cardList ne null and !empty cardList}">
                                    <c:forEach items="${cardList}" var="cardList" >
                                           <option value="${cardList.dtlCd}">${cardList.dtlCdNm}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                            <label class="c-form__label">카드사명</label>
                        </div>
                        <div class="c-form__input">
                            <input class="c-input numOnly" id="creditCardNo" name="creditCardNo" maxlength="16" type="tel"  pattern="[0-9]*" placeholder="'-'없이 입력" >
                            <label class="c-form__label" for="creditCardNo">카드번호</label>
                        </div>
                    </div>
                </div>

              <div class="c-form">
                   <div class="c-form__title u-fs-20 u-mt--40">납부 기준일</div>
                   <div class="c-form__select u-mt--0">
                       <select class="c-select" id="cardCycleDueDay">
                           <option value="">기준일 선택</option>
                           <option value="01">1회차(매월11일경)</option>
                           <option value="02">2회차(매월20일경)</option>
                       </select>
                       <label class="c-form__label">기준일 선택</label>
                   </div>
                </div>

                <div class="c-form">
                    <div class="c-form__title u-fs-20">신용카드 유효기간</div>
                    <div class="c-select-wrap">
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
                        <select class="c-select" id="cardYY">
                            <option value="">년(YYYY)</option>
                            <c:set var="y" value="${nowY}" />
                            <c:forEach begin="0" end="10" var="item">
                                <option value="${y+item}">${y+item}년</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="c-button-wrap">
                        <button class="c-button c-button--full c-button--mint c-button--h48" id="cardCheckBtn">신용카드 유효성 체크</button>
                    </div>
                </div>
            </div>
            <!-- 신용카드 자동납부 -->

            <!-- 지로납부 -->
            <div id="paper" style="display:none;">
                <div class="c-form">
                    <span class="c-form__title u-fs-20" id="inpG">납부 정보 입력</span>
                    <div class="c-form__group" role="group" aria-labelledby="inpG">
                        <div class="c-input-wrap u-mt--0">
                            <input class="c-input" type="text" placeholder="우편번호" title="우편번호 입력" readonly id="adrZip" name="adrZip" value="${zipCode}">
                            <button class="c-button c-button--sm c-button--underline" onclick="openPage('pullPopup', '/m/addPopup.do');">우편번호 찾기</button>
                        </div>
                        <input class="c-input" type="text" placeholder="주소 입력" title="주소 입력" readonly id="adrPrimaryLn" name="adrPrimaryLn" value="${pAddr}">
                        <input class="c-input" type="text" placeholder="상세 주소 입력" title="상세 주소 입력" readonly id="adrSecondaryLn" name="adrSecondaryLn" value="${sAddr}">
                    </div>
                </div>
                <div class="c-form">
                    <div class="c-form__title u-fs-20">납부 기준일</div>
                    <div class="c-form__select u-mt--0">
                        <select class="c-select" id="giroCycleDueDay">
                            <option value="">기준일 선택</option>
                            <option value="25">매월25일</option>
                            <option value="27">매월27일</option>
                        </select>
                        <label class="c-form__label">기준일 선택</label>
                    </div>
                </div>
            </div>
            <!-- 지로납부 -->

            <!-- 본인인증 영역 -->
            <div id="auth">
                <div class="c-form">

                    <script defer type="text/javascript" src="/resources/js/mobile/popup/niceAuth.js?version=24.10.17"></script>

                   <div class="c-form__title u-fs-20 u-mt--48">본인인증 방법 선택</div>
                   <div class="c-button-wrap u-mt--0">
                      <button class="c-button--center _btnNiceAuthBut"  data-online-auth-type="M" ><i class="c-icon c-icon--phone" aria-hidden="true"></i>휴대폰<br />인증하기</button>
                      <button class="c-button--center _btnNiceAuthBut"  data-online-auth-type="C" ><i class="c-icon c-icon--card" aria-hidden="true"></i>신용카드<br />인증하기</button>
                   </div>
                   <ul class="c-text-list c-bullet c-bullet--dot u-fs-14 u-mt--16">
                        <li class="c-text-list__item u-co-gray">고객님의 본인명의(미성년자의 경우 법정 대리인)의 국내 발행 신용카드 정보를 입력해주세요.(체크카드 인증불가)</li>
                        <li class="c-text-list__item u-co-gray">신용카드 비밀번호 3회 이상 오류 시 해당카드로 인증을받으실 수 없으니 유의하시기 바랍니다.</li>
                    </ul>
                    <input class="c-checkbox" type="checkbox" id="chkAgree" name="chkAgree">
                      <label class="c-label u-fs-16 u-mt--32" for="chkAgree">납부방법 변경 본인인증 절차 동의</label>
                    <ul class="c-text-list c-bullet c-bullet--dot u-fs-14 u-mt--16">
                        <li class="c-text-list__item u-co-gray"><span class="u-co-gray-9">안전한 서비스 이용 및 고객님의 개인정보 보호를 위해 본인 인증을 받으신 고객분만 이용이 가능합니다. 서비스 이용을 위해서 본인확인 절차를 진행해 주세요.</span></li>
                    </ul>
                    <div class="c-button-wrap u-mt--48 u-mb--40">
                        <button class="c-button c-button--full c-button--gray cancelBtn">취소</button>
                        <button class="c-button c-button--full c-button--primary changeBtn">납부방법 변경</button>
                    </div>
                </div>
            </div>

            <!-- 카카오 페이 -->
            <div id="kakao" style="display:none;">
                <h3 class="c-heading c-heading--type2 u-fs-20">카카오페이 자동납부 신청방법</h3>
                <ul class="c-step-list u-mb--32">
                    <li class="c-step-list__step">
                        <dl>
                            <dt>SMS전송</dt>
                            <dd>
                                <img src="/resources/images/mobile/common/ico_sms_send.svg" alt="">
                                <div class="c-button-wrap">
                                    <button class="c-button c-button--full c-button--gray c-button--h48 cancelBtn">취소</button>
                                    <button class="c-button c-button--full c-button--mint c-button--h48 smsPayBtn">SMS 전송</button>
                                </div>
                                <p class="c-bullet c-bullet--fyr u-co-mint u-mt--16 u-ta-left">전송된 SMS내의 링크를 통해 카카오페이 자동 납부 신청이 가능합니다.</p>
                            </dd>
                        </dl>
                    </li>
                    <li class="c-step-list__step">
                        <dl>
                            <dt>카카오톡 or 카카오페이로 <br>자동납부 신청하기</dt>
                            <dd class="u-ta-left">
                                <div class="c-step-list__image u-mt--40">
                                    <img src="/resources/images/mobile/content/img_step_kakaopay.png" alt="">
                                </div>
                                <hr class="c-hr c-hr--type2">
                                <p class="c-bullet c-bullet--dot">카카오페이로 자동납부 신청하기</p>
                                <p class="c-bullet c-bullet--hyphen u-co-gray">카카오페이 APP ▸ 더보기 ▸ 청구서 ▸ 카카오톡 APP 내 청구서 ▸ 자동납부신청</p>
                            </dd>
                        </dl>
                    </li>
                </ul>
            </div>
            <!-- 카카오 페이 -->

            <!-- 페이코 -->
            <div id="payCo" style="display:none;">
                <h3 class="c-heading c-heading--type2">페이코 자동납부 신청방법</h3>
                <ul class="c-step-list u-mb--32">
                    <li class="c-step-list__step">
                        <dl>
                            <dt>SMS전송</dt>
                            <dd>
                                <img src="/resources/images/mobile/common/ico_sms_send.svg" alt="">
                                <div class="c-button-wrap">
                                    <button class="c-button c-button--full c-button--gray c-button--h48 cancelBtn">취소</button>
                                    <button class="c-button c-button--full c-button--mint c-button--h48 smsPayBtn">SMS 전송</button>
                                </div>
                                <p class="c-bullet c-bullet--fyr u-co-mint u-mt--16 u-ta-left">전송된 SMS내의 링크를 통해 카카오페이 자동 납부 신청이 가능합니다.</p>
                            </dd>
                        </dl>
                    </li>
                    <li class="c-step-list__step">
                        <dl>
                            <dt>페이코로 자동납부 신청하기</dt>
                            <dd class="u-ta-left">
                                <div class="c-step-list__image u-mt--40">
                                    <img src="/resources/images/mobile/content/img_step_payco.png" alt="">
                                </div>
                            </dd>
                        </dl>
                    </li>
                </ul>
            </div>
            <!-- 페이코 -->

            <b class="c-text c-text--type3 u-mt--24">
                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
            </b>
            <ul class="c-text-list c-bullet c-bullet--dot u-mt--16 u-mb--32">
                <li class="c-text-list__item u-co-gray">자동납부(계좌/카드)를 이용하시는 경우, 당월 청구금액 납부 시 이중 납부가 될 수 있으니납부일 및 계좌/카드 거래내역 확인 후 이용하시기 바랍니다.</li>
                <li class="c-text-list__item u-co-gray">입금확인 일자는 은행,신용카드 등 에서 kt M모바일에 납부사실을 통보한 일자로, 납부하신 날짜와 입금확인 일자가 다소 차이가 날 수 있습니다.</li>
                <li class="c-text-list__item u-co-gray">납부사실 통보에 소요되는 시간이 각 은행과 신용카드 기관별로 차이가 있기 때문에, 입금확인 일자가 서로 다를 수 있습니다.</li>
                <li class="c-text-list__item u-co-gray">미납 요금계에 마이너스(-)로 표기된 경우는 지난달 과납 등으로 발생된 금액입니다.</li>
                <li class="c-text-list__item u-co-gray">상세내역은 최근 6개월까지 요금내역만 제공됩니다.</li>
                <li class="c-text-list__item u-co-gray">미납요금을 납부한 고객일지라도, 명세서 상에 미납요금이 표기되어 있을 수 있습니다. (명세서 상 미납요금은 청구시점의 미납요금이 표기된 것으로 현 시점의 미납요금과 상이할 수 있음)</li>
                <li class="c-text-list__item u-co-gray">납부변경 적용일 동일한 납부방법으로 변경 시 : 최소 3일 전까지 변경시 당월적용(변경 요청 시점이 은행, 카드사로 당월 납부요청이 지난 후 에는 변경한 납부방법이 다음달로 예약)</li>
                <li class="c-text-list__item u-co-gray">다른 납부방법으로 변경 시 : 익월적용</li>
            </ul>
        </div>
        <input type="hidden" id="payMethod" name="payMethod" value="${payMethod}">
        <input type="hidden" id="payBizrCd" name="payBizrCd" value="${payBizrCd}">
        <input type="hidden" id="isAuth" name="isAuth" value="${isAuth}">
        <input type="hidden" id="ncn" name="ncn" value="${ncn}">
        <input type="hidden" id="contractNum" name="contractNum" value="${contractNum}">
    </t:putAttribute>
</t:insertDefinition>