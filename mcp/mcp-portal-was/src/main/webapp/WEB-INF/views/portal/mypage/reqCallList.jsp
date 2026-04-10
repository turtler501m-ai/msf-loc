<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<un:useConstants var="PhoneConstant" className="com.ktmmobile.mcp.phone.constant.PhoneConstant" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">통화내역열람 신청</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
    <script type="text/javascript" src="/resources/js/common/validator.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
    <script type="text/javascript" src="/resources/js/portal/mypage/reqCallList.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <form name="selfFrm" id="selfFrm" action="/mypage/reqCallList.do" method="post">
        <input type="hidden" name="ncn" value="" >
    </form>
    <input type="hidden" id="isAuth" name="isAuth">
    <input type="hidden" id="isPassAuth" name="isPassAuth">
    <input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">

    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">통화내역열람 신청</h2>
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
                    <p>신청정보 확인 후 고객님께 전화 드립니다<br/>(영업일 D+1일 이내)
                    </p>
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
      <div class="c-row c-row--lg u-mt--60">
        <h3 class="c-heading c-heading--fs20 c-heading--regular">조회할 회선을 선택해 주세요.</h3>
        <%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp" %>
        <hr class="c-hr c-hr--title">

        <h4 class="c-group--head" id="inpNameCertiTitle">실명 및 본인인증</h4>
        <div class="c-form-group" role="group" aira-labelledby="inpNameCertiTitle">
          <div class="c-form-row c-col2 u-mt--0">
            <div class="c-form-field">
              <div class="c-form__input ">
                <input class="c-input" id="cstmrName" name="cstmrName" type="text" placeholder="이름 입력" value=""  maxlength="60">
                <label class="c-form__label" for="cstmrName">이름</label>
              </div>
            </div>

            <div class="c-form-field _isForeigner" style="display:none;">
              <div class="c-form__input-group">
                <div class="c-form__input c-form__input-division">
                  <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn01" name="cstmrForeignerRrn01" type="text" autocomplete="off" maxlength="6" placeholder="외국인등록번호 앞자리" title="외국인등록번호 앞자리" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,0,6)}" onkeyup="nextFocus(this, 6, 'cstmrForeignerRrn02');" >
                  <span>-</span>
                  <input class="c-input--div2 onlyNum" id="cstmrForeignerRrn02" name="cstmrForeignerRrn02" type="password" autocomplete="new-password" maxlength="7" placeholder="외국인등록번호 뒷자리" title="외국인등록번호 뒷자리" value="${fn:substring(AppformReq.cstmrForeignerRrnDesc,6,13)}" >
                  <label class="c-form__label" for="cstmrForeignerRrn01">외국인 등록번호</label>
                </div>
              </div>
            </div>

            <div class="c-form-field _isDefault" >
              <div class="c-form__input-group">
                <div class="c-form__input c-form__input-division">
                 <!-- 주민등록번호 입력 부분 -->
                  <input class="c-input--div2 onlyNum" id="cstmrNativeRrn01" type="text" autocomplete="false" maxlength="6" placeholder="주민등록번호 앞자리" title="주민등록번호 앞자리" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,0,6)}" onkeyup="nextFocus(this, 6, 'cstmrNativeRrn02');" >
                  <span>-</span>
                  <input class="c-input--div2 onlyNum" id="cstmrNativeRrn02" type="password" autocomplete="new-password" maxlength="7" placeholder="주민등록번호 뒷자리" title="주민등록번호 뒷자리" value="${fn:substring(AppformReq.cstmrNativeRrnDesc,6,13)}" >
                  <label class="c-form__label" for="cstmrNativeRrn01">주민등록번호</label>
                </div>
              </div>
            </div>

          </div>
        </div>

        <!-- 본인인증 방법 선택 -->
        <jsp:include page="/WEB-INF/views/portal/popup/niceAuthForm.jsp">
            <jsp:param name="controlYn" value="N"/>
            <jsp:param name="reqAuth" value="CNATX"/>
        </jsp:include>

        <div class="c-form-wrap u-mt--48" id="requestForm" style="display: none;">
        <div class="c-form-wrap u-mt--48">
           <div class="c-form-group" role="group" aira-labelledby="periodTitle">
             <h5 class="c-group--head" id="periodTitle">신청기간 선택</h5>
             <div class="c-form-row c-col2">
              <div class="c-form-field c-form-field--datepicker">
                <div class="c-form__input has-value">
                  <input class="c-input flatpickr flatpickr-input" name="startDate" id="startDate" type="text" placeholder="YYYY-MM-DD" readonly="readonly">
                  <label class="c-form__label" for="startDate">시작일</label>
                  <span class="c-button">
                    <span class="c-hidden">날짜선택</span>
                    <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                  </span>
                </div>
              </div>
              <div class="c-form-field c-form-field--datepicker">
                <div class="c-form__input" >
                  <input class="c-input flatpickr flatpickr-input" name="endDate" id="endDate" type="text" placeholder="YYYY-MM-DD" readonly="readonly">
                  <label class="c-form__label" for="endDate">종료일</label>
                  <span class="c-button">
                    <span class="c-hidden">날짜선택</span>
                    <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="c-form-wrap u-mt--48">
           <div class="c-form-group" role="group" aira-labelledby="readlistTitle">
             <h4 class="c-group--head" id="readlistTitle">열람항목(국내/국제 중복선택 가능)</h4>
           </div>
           <div class="c-table u-mt--16">
             <table>
               <caption>국내/국제 열람항목으로 구성된 열람항목 정보</caption>
               <colgroup>
                 <col style="width: 17%">
                 <col>
               </colgroup>
               <tbody>
                 <tr>
                   <th>
                     <input class="c-checkbox c-checkbox--type3" id="callType01" type="checkbox" checked>
                         <label class="c-label" for="callType01">국내</label></th>
                   <td class="u-ta-left">
                     <input class="c-radio" name="rdType" id="rdType01" value="01" type="radio" checked>
                     <label class="c-label u-ml--8" for="rdType01">음성</label>
                     <input class="c-radio" name="rdType" id="rdType02" value="02" type="radio">
                     <label class="c-label" for="rdType02">문자</label>
                     <input class="c-radio" name="rdType" id="rdType03" value="03" type="radio">
                     <label class="c-label" for="rdType03">데이터</label>
                     <input class="c-radio" name="rdType" id="rdType04" value="04" type="radio">
                     <label class="c-label" for="rdType04">음성+문자</label>
                     <input class="c-radio" name="rdType" id="rdType05" value="05" type="radio">
                     <label class="c-label" for="rdType05">음성+문자+데이터</label>
                   </td>
                 </tr>
                 <tr>
                   <th>
                      <input class="c-checkbox c-checkbox--type3" id="callType02" type="checkbox">
                       <label class="c-label" for="callType02">국제</label>
                   <td class="u-ta-left">
                     <input class="c-radio" name="radio_b" id="rdType06" value="06" type="radio">
                      <label class="c-label u-ml--8" for="rdType06">국제전화</label>
                   </td>
                 </tr>
               </tbody>
             </table>
           </div>
        </div>
        <div class="c-form-wrap u-mt--48">
           <div class="c-form-group" role="group" aira-labelledby="ReasonTitle">
             <h4 class="c-group--head" id="ReasonTitle">신청사유</h4>
           </div>
           <div class="c-checktype-row">
             <input class="c-radio" name="rdReason" id="radio_01" value="CL" type="radio" checked>
             <label class="c-label" for="radio_01">핸드폰 분실</label>
             <input class="c-radio" name="rdReason" id="radio_02" value="PC" type="radio" >
             <label class="c-label" for="radio_02">사용요금 확인</label>
             <input class="c-radio" name="rdReason" id="radio_03" value="NC" type="radio" >
             <label class="c-label" for="radio_03">발신번호 확인</label>
             <input class="c-radio" name="rdReason" id="radio_04" value="PS" type="radio" >
             <label class="c-label" for="radio_04">통화요금 지원요청용</label>
             <input class="c-radio" name="rdReason" id="radio_05" value="GR" type="radio" >
             <label class="c-label" for="radio_05">수사기관 요청</label>
           </div>
           <div class="c-checktype-row">
             <input class="c-radio" name="rdReason" id="radio_06" value="HC" type="radio" >
             <label class="c-label" for="radio_06">가출 확인용</label>
             <input class="c-radio" name="rdReason" id="radio_07" value="PE" type="radio" >
             <label class="c-label" for="radio_07">기타 개인사유</label>
           </div>
        </div>
        <div class="c-form-wrap u-mt--48">
           <div class="c-form-group" role="group" aira-labelledby="receiveTitle">
             <h4 class="c-group--head" id="receiveTitle">수신방법</h4>
           </div>
           <div class="c-checktype-row">
             <input class="c-radio" name="rdRecvType" id="rdFx" value="FX" type="radio" checked>
             <label class="c-label" for="rdFx">FAX</label>
             <input class="c-radio" name="rdRecvType" id="rdPt" value="PT" type="radio" >
             <label class="c-label" for="rdPt">등기발송</label>
           </div>
           <p class="c-bullet c-bullet--fyr u-co-gray">통화내역 서류가 많을 경우 수신방법이 변경될 수 있습니다.<br/>자세한 내용은 고객센터 상담원이 전화드려 도움드리겠습니다.</p>
        </div>
        <div class="c-form-group u-mt--48" role="group" aira-labelledby="FaxTitle" id="fax">
           <h5 class="c-group--head" id="FaxTitle">FAX 번호(지역번호 포함)</h5>
           <div class="c-form-row c-col2 u-width--460">
             <div class="c-form-field">
             <div class="c-form__input-group u-mt--0">
               <div class="c-form__input c-form__input-division">
                 <input class="c-input--div3 onlyNum" id="fax1" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="팩스 첫자리">
                 <span>-</span>
                 <input class="c-input--div3 onlyNum" id="fax2" type="text" maxlength="4" pattern="[0-9]" placeholder="숫자입력" title="팩스 중간자리">
                 <span>-</span>
                 <input class="c-input--div3 onlyNum" id="fax3" type="text" maxlength="4" pattern="[0-9]" placeholder="숫자입력" title="팩스 뒷자리">
                 <label class="c-form__label" for="fax">FAX 번호(지역번호 포함)</label>
               </div>
             </div>
           </div>
           </div>
        </div>
        <div class="c-form-wrap u-mt--48" id="Address" style="display: none;">
           <div class="c-form-group" role="group" aira-labelledby="inputAddressHead">
             <h5 class="c-group--head" id="inputAddressHead">등기 수령하실 주소</h5>
             <div class="c-form-row c-col2">
               <div class="c-form">
                 <div class="c-input-wrap u-mt--0">
                   <label class="c-form__label c-hidden" for="cstmrPost">우편번호</label>
                   <input class="c-input" id="cstmrPost" type="text" placeholder="우편번호" value="" readonly>
                   <button id="btnAddr" class="c-button c-button--sm c-button--underline _btnAddr" addrFlag="1">우편번호찾기</button>
                 </div>
               </div>
               <div class="c-form">
                 <div class="c-input-wrap u-mt--0">
                   <label class="c-form__label c-hidden" for="cstmrAddr">주소</label>
                   <input class="c-input" id="cstmrAddr" type="text" placeholder="주소" value="" readonly >
                 </div>
               </div>
             </div>
             <div class="c-form u-mt--16">
               <label class="c-label c-hidden" for="cstmrAddrDtl">상세주소</label>
               <input class="c-input" id="cstmrAddrDtl" type="text" placeholder="상세주소" value="" name="cstmrAddrDtl" readonly>
             </div>
           </div>
         </div>
         <div class="c-form-wrap u-mt--48">
            <div class="c-form-group" role="group" aira-labelledby="inpRecieverPhoneTitle">
              <h4 class="c-group--head c-flex--jfy-start" id="inpRecieverPhoneTitle">연락 가능 연락처(필수기재)</h4>
            </div>
            <div class="c-form-row c-col2 u-width--460">
              <div class="c-form-field">
                <div class="c-form__input-group u-mt--0">
                  <div class="c-form__input c-form__input-division">
                    <input class="c-input--div3 onlyNum" id="cstmrReceiveTelFn" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="휴대폰 첫자리" onkeyup="nextFocus(this, 3, 'cstmrReceiveTelMn');">
                    <span>-</span>
                    <input class="c-input--div3 onlyNum" id="cstmrReceiveTelMn" type="text" maxlength="4" pattern="[0-9]" placeholder="숫자입력" title="휴대폰 중간자리" onkeyup="nextFocus(this, 4, 'cstmrReceiveTelRn');">
                    <span>-</span>
                    <input class="c-input--div3 onlyNum" id="cstmrReceiveTelRn" type="text" maxlength="4" pattern="[0-9]" placeholder="숫자입력" title="휴대폰 뒷자리">
                    <label class="c-form__label" for="inpRecieverPhone">연락 가능 연락처(필수기재)</label>
                  </div>
                </div>
              </div>
            </div>
         </div>
         <div class="c-form-wrap u-mt--48">
            <div class="c-form-group" role="group" aira-labelledby="etclistTitle">
              <h4 class="c-group--head c-flex--jfy-start" id="etclistTitle">기타사항</h4>
            </div>
            <div class="c-textarea-wrap">
              <label class="c-form__label c-hidden" for="etcMemo">기타사항 내용 입력</label>
              <textarea class="c-textarea" placeholder="기타사항을 입력해주세요." id="etcMemo"></textarea>
              <div class="c-textarea-wrap__sub">
                <span class="c-hidden">입력 된 글자 수/최대 입력 글자 수</span>
                <span id="textAreaSbstSize" maxlength="300">0/300자</span>
              </div>
            </div>
           </div>
            <div class="c-form-wrap u-mt--48">
            <h4 class="c-form__title">약관동의</h4>
            <div class="c-agree c-agree--type2">
              <div class="c-agree__item">
                <input class="c-checkbox" id="chkAgreeAll" type="checkbox" name="chkAgree">
                <label class="c-label" for="chkAgreeAll">개인정보 수집이용동의 (필수)</label>
              </div>
              <div class="c-agree__inside">
              	<ul class="c-text-list c-bullet c-bullet--number">
                    <li class="c-text-list__item u-mt--0">항목 : FAX번호 및 배송주소</li>
                    <li class="c-text-list__item">수집 및 이용목적 : 통화내역열람 자료 제공</li>
                    <li class="c-text-list__item">보유 기간 : 고객님에게 자료를 제공한 당일 지체없이 파기됩니다.</li>
                </ul>
                <p class="u-fw--bold u-mt--12">※고객님은 해당 사항에 동의를 거부하실 수 있으며, 동의 거부 시 서비스 이용이 불가합니다.</p>
               </div>
             </div>
          </div>

        <b class="c-flex c-text c-text--fs15 u-mt--48">
          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
          <span class="u-ml--4">주요 안내사항</span>
        </b>
           <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
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

       <div class="c-button-wrap u-mt--56">
         <button class="c-button c-button--lg c-button--white c-button--w220" id="btnReqCancel">취소</button>
         <button class="c-button c-button--lg c-button--primary c-button--w220" id="btnReqCall" disabled>신청</button>
       </div>
    </div>
    </div>

</t:putAttribute>
</t:insertDefinition>
