<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/common/validator.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
           <script type="text/javascript" src="/resources/js/mobile/mypage/reqCallList.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/appForm/appFormOcr.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <form name="selfFrm" id="selfFrm" action="/m/mypage/reqCallList.do" method="post">
        <input type="hidden" name="ncn" value="" >
    </form>
    <input type="hidden" id="isAuth" name="isAuth">
    <input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">

    <div class="ly-content" id="main-content">
        <div class="ly-page-sticky">
            <h2 class="ly-page__head">통화내역열람 신청<button class="header-clone__gnb"></button></h2>
         </div>
          <div class="callhistory-info">
              <div class="callhistory-info__list">
                <h3>통화내역열람이란?</h3>
                <ul>
                  <li>청구요금 확인을 위해 최근 1년이내 고객님이 발신한 번호, 통화일시/시간, 요금을 확인할 수 있습니다.</li>
                </ul>
                <p class="c-bullet c-bullet--fyr u-co-gray">통화내역 조회는 요금 확인용으로 제공되며, 고객님의 개인정보 보호를 위해 발신한 상대 전화번호의 일부 자리수는 표시되지 않습니다.</p>
            </div>
            <div class="callhistory-guide">
                <h3>신청절차</h3>
                <ul class="callhistory-guide__list">
                    <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">1</span>고객님</strong>
                        <p>통화내역열람 신청서 작성</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">2</span>고객센터</strong>
                        <p>신청정보 확인 후 고객님께 전화드립니다<br/>(영업일 D+1일 이내)</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">3</span>고객센터 → 고객님</strong>
                        <p>SMS 인증번호 요청</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">4</span>고객센터 → 고객님</strong>
                        <p>통화내역서류<br/>FAX 또는 등기로 송부</p>
                      </li>
                </ul>
            </div>
        </div>
        <h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
        <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>
        <div class="c-form">
              <span class="c-form__title" id="spOnlineAuthType">실명 및 본인인증</span>
              <div class="c-form__input">
                <input class="c-input" id="cstmrName" name="cstmrName" type="text" placeholder="이름 입력" value="" maxlength="60">
                <label class="c-form__label" for="cstmrName">이름</label>
              </div>
        </div>
        <div class="c-form-field _isDefault">
              <div class="c-form__input-group">
                <div class="c-form__input c-form__input-division">
                      <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" autocomplete="off" type="tel" maxlength="6" value="" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');">
                         <span>-</span>
                         <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" autocomplete="off" type="password" maxlength="7" value="" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리">
                         <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                   </div>
              </div>
        </div>

        <!-- 본인인증 방법 선택 -->
        <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
            <jsp:param name="controlYn" value="N"/>
            <jsp:param name="reqAuth" value="CNAT"/>
        </jsp:include>

        <div class="c-form-wrap" id="requestForm" style="display: none;">
            <div class="c-form">
                <span class="c-form__title">신청기간 선택</span>
                <div class="c-form c-form--datepicker">
                  <div class="c-form__input">
                    <input class="c-input date-input" id="startDate" type="text" placeholder="YYYY-MM-DD" title="시작일 입력" readonly>
                    <label class="c-form__label" for="startDate">시작일</label>
                    <button class="c-button">
                      <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                    </button>
                  </div>
                </div>
                <div class="c-form c-form--datepicker">
                  <div class="c-form__input">
                    <input class="c-input date-input" id="endDate" type="text" placeholder="YYYY-MM-DD" title="종료일 입력" readonly>
                    <label class="c-form__label" for="endDate">종료일</label>
                    <button class="c-button">
                      <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                    </button>
                  </div>
                </div>
            </div>
            <div class="c-form">
                <span class="c-form__title">열람항목(국내/국제 중복선택 가능)</span>
                <div class="c-table c-table--row">
                  <table>
                    <caption>국내/국제 열람항목으로 구성된 열람항목 정보</caption>
                    <colgroup>
                      <col style="width: 30%">
                      <col>
                    </colgroup>
                    <tbody>
                      <tr>
                        <th>
                          <input class="c-checkbox" id="callType01" type="checkbox" name="callType01" checked>
                          <label class="c-label u-mr--0" for="callType01">국내</label>
                        </th>
                        <td class="u-ta-left">
                             <div class="c-check-wrap c-check-wrap--column">
                               <input class="c-radio" id="rdType01" type="radio" value="01" name="rdType" checked>
                               <label class="c-label" for="rdType01">음성</label>
                               <input class="c-radio" id="rdType02" type="radio" value="02" name="rdType">
                               <label class="c-label" for="rdType02">문자</label>
                               <input class="c-radio" id="rdType03" type="radio" value="03" name="rdType">
                               <label class="c-label" for="rdType03">데이터</label>
                               <input class="c-radio" id="rdType04" type="radio" value="04" name="rdType">
                               <label class="c-label" for="rdType04">음성+문자</label>
                               <input class="c-radio" id="rdType05" type="radio" value="05" name="rdType">
                               <label class="c-label" for="rdType05">음성+문자+데이터</label>
                          </div>
                        </td>
                      </tr>
                      <tr>
                        <th>
                          <input class="c-checkbox" id="callType02" type="checkbox" name="callType02">
                          <label class="c-label u-mr--0" for="callType02">국제</label>
                        </th>
                        <td class="u-ta-left">
                             <div class="c-check-wrap c-check-wrap--column">
                               <input class="c-radio" id="rdType06" type="radio" name="rdType06">
                               <label class="c-label" for="rdType06">국제전화</label>
                          </div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              <div class="c-form">
                <span class="c-form__title">신청사유</span>
                <div class="c-check-wrap c-check-wrap--column">
                     <input class="c-radio" id="radio_01" type="radio" value="CL" name="rdReason" checked>
                     <label class="c-label" for="radio_01">핸드폰 분실</label>
                     <input class="c-radio" id="radio_02" type="radio" value="PC" name="rdReason">
                     <label class="c-label" for="radio_02">사용요금 확인</label>
                     <input class="c-radio" id="radio_03" type="radio" value="NC" name="rdReason">
                     <label class="c-label" for="radio_03">발신번호 확인용</label>
                     <input class="c-radio" id="radio_04" type="radio" value="PS" name="rdReason">
                     <label class="c-label" for="radio_04">통화요금 지원요청용</label>
                     <input class="c-radio" id="radio_05" type="radio" value="GR" name="rdReason">
                     <label class="c-label" for="radio_05">수사기관 요청</label>
                     <input class="c-radio" id="radio_06" type="radio" value="HC" name="rdReason">
                     <label class="c-label" for="radio_06">가출 확인용</label>
                     <input class="c-radio" id="radio_07" type="radio" value="PE" name="rdReason">
                     <label class="c-label" for="radio_07">기타 개인사유</label>
                </div>
              </div>
              <div class="c-form">
                <span class="c-form__title">수신방법</span>
                <div class="c-check-wrap">
                  <div class="c-chk-wrap">
                    <input class="c-radio c-radio--button" id="rdFx" value="FX" type="radio" name="rdRecvType" checked>
                    <label class="c-label" for="rdFx">FAX</label>
                  </div>
                  <div class="c-chk-wrap">
                    <input class="c-radio c-radio--button" id="rdPt" value="PT" type="radio" name="rdRecvType">
                    <label class="c-label" for="rdPt">등기발송</label>
                  </div>
                </div>
                <p class="c-bullet c-bullet--fyr u-co-gray u-mt--12">통화내역 서류가 많을 경우 수신방법이 변경될 수 있습니다.<br/>자세한 내용은 고객센터 상담원이 전화드려 도움드리겠습니다.</p>
            </div>
            <div class="c-form" id="fax">
                <span class="c-form__title">FAX 번호(지역번호 포함)</span>
                <div class="c-form__input u-mt--0">
                  <input class="c-input onlyNum" id="faxNum" type="tel" name="" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" value="">
                  <label class="c-form__label" for="faxNum">FAX 번호(지역번호 포함)</label>
                </div>
            </div>
            <div class="c-form" id="Address" style="display: none;">
                <span class="c-form__title">등기 수령하실 주소</span>
                <div class="c-form__group" role="group" aria-labeledby="inpG">
                  <div class="c-input-wrap u-mt--0">
                    <input id="cstmrPost" class="c-input" type="text" placeholder="우편번호" title="우편번호 입력" value="" readonly="">
                    <button class="c-button c-button--sm c-button--underline _btnAddr" addrflag="1">우편번호 찾기</button>
                  </div>
                  <input id="cstmrAddr" class="c-input" type="text" placeholder="주소 입력" title="주소 입력" value="" readonly="">
                  <input id="cstmrAddrDtl" class="c-input" type="text" placeholder="상세 주소 입력" title="상세 주소 입력" value="" readonly="">
                </div>
            </div>
            <div class="c-form">
                <span class="c-form__title">연락 가능 연락처(필수기재)</span>
                <div class="c-form__input u-mt--0">
                  <input class="c-input onlyNum" id=cstmrReceiveTel type="tel" name="" maxlength="11" pattern="[0-9]*" placeholder="'-'없이 입력" value="">
                  <label class="c-form__label" for="cstmrReceiveTel">연락 가능 연락처(필수기재)</label>
                </div>
            </div>
            <div class="c-form">
                <span class="c-form__title">기타사항</span>
                <div class="c-textarea-wrap u-mt--0">
                  <textarea class="c-textarea u-ta-left inner-form" placeholder="기타사항 내용을 입력해주세요." maxlength="300" id="etcMemo"></textarea>
                  <div class="c-textarea-wrap__sub">
                    <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span>
                    <span id="textAreaSbstSize">0/300</span>
                  </div>
                </div>
            </div>
            <div class="c-form">
                <div class="c-agree u-mt--32">
                  <input class="c-checkbox" id="chkAgreeAll" type="checkbox" name="chkAgree">
                  <label class="c-label" for="chkAgreeAll">개인정보 수집이용 동의(필수)</label>
                  <ul class="c-text-list c-bullet c-bullet--number">
                      <li class="c-text-list__item">항목 : FAX번호 및 배송주소</li>
                      <li class="c-text-list__item">수집 및 이용목적 : 통화내역열람 자료 제공</li>
                      <li class="c-text-list__item">보유 기간 : 고객님에게 자료를 제공한 당일 지체없이 파기됩니다.</li>
                  </ul>
                  <p class="u-fw--bold u-mt--12">※고객님은 해당 사항에 동의를 거부하실 수 있으며, 동의 거부 시 서비스 이용이 불가합니다.</p>
                </div>
            </div>
            <hr class="c-hr c-hr--type2 u-mt--40 u-mb--40">
              <h3 class="c-heading c-heading--type6">
                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                <span class="u-ml--4">알려드립니다.</span>
              </h3>
            <ul class="c-text-list c-bullet c-bullet--dot">
                 <li class="c-text-list__item u-co-gray">통화내역열람은 정보통신망 이용촉진 및 정보보호 등에 관한법률에 의거하여 제공되고 있습니다.</li>
                 <li class="c-text-list__item u-co-gray">통화내역열람은 청구요금 확인을 목적으로 발행되며, 법적효력을 나타내지 않습니다.</li>
                 <li class="c-text-list__item u-co-gray">발신번호는 일부 *표기되어 발송됩니다.</li>
                 <li class="c-text-list__item u-co-gray">통화내역열람 신청 시 접수일로부터 1일이내(영업일) 고객님께 해피콜을 진행하고, 해피콜 시 SMS인증 후 통화내역열람서류 발급이 가능합니다.</li>
                 <li class="c-text-list__item u-co-gray">선불, 법인, 미성년자분들께서는 고객센터를 통해 접수 부탁드립니다.</li>
                 <li class="c-text-list__item u-co-gray">A고객님이 B고객님의 투넘버 번호로 발신 후 A고객님께서 통화내역 열람 시 B고객님의 원번호로 표기됨</li>
                 <li class="c-text-list__item u-co-gray">착신전환 : A가 B에게 전화해서 C에게 연결된 경우 B가 통화내역열람하면 C로 발신 된 내역 조회</li>
                 <li class="c-text-list__item u-co-gray">mVoIP로 발신된 내역을 통화내역열람으로 번호확인이 되지 않습니다.</li>
                 <li class="c-text-list__item u-co-gray">무과금 특수번호는 통화내역 열람 시 발신내역이 확인되지 않습니다.</li>
                 <li class="c-text-list__item u-co-gray">조금 더 세부적인 문의사항은 고객센터 (114/무료 또는 1899-5000/유료)로 문의부탁드립니다.</li>
            </ul>
        </div>
        <div class="c-button-wrap">
          <button class="c-button c-button--gray c-button--lg u-width--120" id="btnReqCancel">취소</button>
          <button class="c-button c-button--full c-button--primary" id="btnReqCall" disabled>신청</button>
        </div>
    </div>



    </t:putAttribute>
</t:insertDefinition>