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

    <t:putAttribute name="titleAttr">가입신청서 출력요청 </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
    <script type="text/javascript" src="/resources/js/common/validator.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
    <script type="text/javascript" src="/resources/js/portal/mypage/reqJoinForm.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <form name="selfFrm" id="selfFrm" action="/mypage/reqJoinForm.do" method="post">
        <input type="hidden" name="ncn" value="" >
    </form>
    <input type="hidden" id="isAuth" name="isAuth">
    <input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">

    <div class="ly-content" id="main-content">
        <div class="ly-page--title">
          <h2 class="c-page--title">가입신청서 출력요청</h2>
        </div>
        <div class="callhistory-info">
            <div class="callhistory-info__list">
                <h3>가입신청서 출력요청이란?</h3>
                <ul>
                      <li>가입 당시 작성하신 내용 확인을 원하시는 고객님께서 선택하신 수단으로 서류를 전송해드리는 서비스입니다.</li>
                </ul>
                <p class="c-bullet c-bullet--fyr u-co-gray">가입신청서에 요금할인 금액은 표기되지 않거나 다를 수 있습니다.</p>
            </div>
              <div class="callhistory-guide">
                <h3>신청절차</h3>
                <ul class="callhistory-guide__list">
                    <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">1</span>고객님</strong>
                        <p>가입신청서 출력요청</p>
                    </li>
                    <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">2</span>고객센터</strong>
                        <p>신청정보 확인 후<br>선택하신 수신방법으로 전송	</p>
                    </li>
                    <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">3</span>고객센터 → 고객님</strong>
                        <p>FAX 또는 등기<br>발송완료 안내문자 발송</p>
                    </li>
                </ul>
              </div>
        </div>
          <div class="c-row c-row--lg u-mt--60">
            <h3 class="c-heading c-heading--fs20 c-heading--regular">조회할 회선을 선택해 주세요.</h3>
            <%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp" %>

            <hr class="c-hr c-hr--title">

            <!-- 본인인증 방법 선택 -->
            <jsp:include page="/WEB-INF/views/portal/popup/niceAuthForm.jsp">
                <jsp:param name="controlYn" value="N"/>
                <jsp:param name="reqAuth" value="CNATX"/>
            </jsp:include>

            <div class="c-form-wrap u-mt--48" id="requestForm" style="display: none;">
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
                </div>
                <div class="c-form-group u-mt--48" role="group" aira-labelledby="FaxTitle" id="fax">
                    <h5 class="c-group--head" id="FaxTitle">FAX 번호(지역번호 포함)</h5>
                    <div class="c-form-row c-col2 u-width--460">
                        <div class="c-form-field">
                            <div class="c-form__input-group u-mt--0">
                                <div class="c-form__input c-form__input-division">
                                    <input class="c-input--div3 onlyNum" id="fax1" type="text" maxlength="3" pattern="[0-9]" placeholder="숫자입력" title="팩스 첫자리" onkeyup="nextFocus(this, 3, 'fax2');">
                                    <span>-</span> <input class="c-input--div3 onlyNum" id="fax2" type="text" maxlength="4" pattern="[0-9]" placeholder="숫자입력"	title="팩스 중간자리" onkeyup="nextFocus(this, 4, 'fax3');">
                                    <span>-</span> <input class="c-input--div3" id="fax3" type="text" maxlength="4" pattern="[0-9]" placeholder="숫자입력"	title="팩스 뒷자리"> <label class="c-form__label" for="fax">FAX 번호(지역번호 포함)</label>
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
            </div>
           <div class="c-button-wrap u-mt--56">
             <button class="c-button c-button--lg c-button--primary c-button--w220" id="btnRequest" disabled>출력요청</button>
           </div>
        </div>
    </div>

    </t:putAttribute>
</t:insertDefinition>
