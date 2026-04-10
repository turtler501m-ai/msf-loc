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
           <script type="text/javascript" src="/resources/js/mobile/mypage/reqJoinForm.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/appForm/appFormOcr.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <form name="selfFrm" id="selfFrm" action="/m/mypage/reqJoinForm.do" method="post">
        <input type="hidden" name="ncn" value="" >
    </form>
    <input type="hidden" id="isAuth" name="isAuth">
    <input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">

    <div class="ly-content" id="main-content">
        <div class="ly-page-sticky">
            <h2 class="ly-page__head">가입신청서 출력요청<button class="header-clone__gnb"></button></h2>
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
                        <p>신청정보 확인 후<br/>선택하신 수신방법으로 전송</p>
                      </li>
                      <li class="callhistory-guide__item">
                        <strong><span class="callhistory-guide__step">3</span>고객센터 → 고객님</strong>
                        <p>FAX 또는 등기</br>발송완료 안내문자 발송</p>
                      </li>
                </ul>
            </div>
        </div>
        <h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
        <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>

        <!-- 본인인증 방법 선택 -->
        <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
            <jsp:param name="controlYn" value="N"/>
            <jsp:param name="reqAuth" value="CNAT"/>
        </jsp:include>

        <div class="c-form-wrap" id="requestForm" style="display: none;">
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
        </div>
        <div class="c-button-wrap">
              <button class="c-button c-button--full c-button--primary" id="btnRequest" disabled>출력요청</button>
        </div>
    </div>
    </t:putAttribute>
</t:insertDefinition>