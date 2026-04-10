<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<c:set var="controlYn" value="${fn:escapeXml(param.controlYn)}"/>
<c:set var="reqAuth" value="${fn:escapeXml(param.reqAuth)}"/>
<c:set var="spIndex" value="${fn:escapeXml(param.spIndex)}"/>
<c:set var="helpDesc" value="${fn:escapeXml(param.helpDesc)}"/>

<c:set var="simpleAuthList" value="${nmcp:getSimpleAuthList(controlYn, reqAuth)}" />

<c:if test="${not empty simpleAuthList}">

  <%-- 본인인증 관련 스크립트 --%>
  <c:if test="${spIndex ne '2'}">
    <script defer type="text/javascript" src="/resources/js/portal/popup/niceAuth.js?version=24.05.21"></script>
  </c:if>

  <%-- 네이버인증 관련 사전준비 --%>
  <c:forEach items="${simpleAuthList}" var="auth">
    <c:if test="${auth.dtlCd eq 'N' && spIndex ne '2'}">
      <script defer type="text/javascript" src="/resources/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
      <script defer type="text/javascript" src="/resources/js/naver.js"></script>

      <input type="hidden" id="clientId" name="clientId" value="<spring:eval expression="@propertiesForJsp['NAVER_CLIENT_ID_NEW']"/>">
      <input type="hidden" id="serviceUrl" name="serviceUrl" value="${nmcp:getServerInfo()}">
      <input type="hidden" id="pcCallbackUrl" name="pcCallbackUrl" value="${nmcp:getServerInfo()}<spring:eval expression="@propertiesForJsp['NEARO_PC_CALLBACK_URL']"/>">
      <div id="naver_id_login" class="hidden" style="display:none;"></div>
    </c:if>
  </c:forEach>


  <div class="c-form-wrap" id="authFrm">

    <%-- 본인인증 종류 --%>
    <div class="c-form-group certification-chkwrap" role="group" aira-labelledby="radCertiTitle${spIndex}">
      <h3 class="c-form__title--type2" id="radCertiTitle${spIndex}">본인인증 방법 선택</h3>
      <div class="c-checktype-row c-col${fn:length(simpleAuthList)} u-mt--0">

      <c:forEach items="${simpleAuthList}" var="auth" varStatus="status">
        <c:set var="authCnt" value="${status.count}"/>
        <c:if test="${spIndex eq '2'}"><c:set var="authCnt" value="${fn:length(simpleAuthList)+status.count}"/></c:if>
        <input class="c-radio c-radio--button" id="onlineAuthType${authCnt}" value="${auth.dtlCd}" type="radio" data-btext="${auth.expnsnStrVal1}" name="onlineAuthType${spIndex}">
        <label class="c-label" for="onlineAuthType${authCnt}">${auth.dtlCdNm}</label>
      </c:forEach>

      </div>
    </div>

    <%-- 본인인증 설명 문구 --%>
    <div class="c-box c-box--type1 c-box--bullet" style="display:none">
      <ul class="c-text-list c-bullet c-bullet--dot" id="ulOnlineAuthDesc${spIndex}">
        <li class="c-text-list__item">고객님의 본인명의(미성년자의 경우 법정 대리인)의 국내발행 신용카드 정보를 입력해주세요.(체크카드 인증불가)</li>
        <li class="c-text-list__item">신용카드 비밀번호 3회 이상 오류 시 해당카드로 인증을 받으실 수 없으니 유의하시기 바랍니다.</li>
      </ul>
    </div>

    <%-- 범용인증 관련 추가 요소 --%>
    <c:forEach items="${simpleAuthList}" var="auth">
      <c:if test="${auth.dtlCd eq 'X'}">
      <div class="c-box c-box--type1 c-box--bullet" id="issuedBank${spIndex}" style="display:none">
        <ul class="c-text-list c-bullet c-bullet--dot">
          <li class="c-text-list__item">은행 및 한국전자인증, 한국정보인증에서 발급된 범용 공인증서(유료)만 사용 가능합니다.</li>
          <li class="c-text-list__item">체크카드는 인증이 불가합니다.</li>
        </ul>
        <p class="c-bullet c-bullet--fyr u-fs-16 u-co-gray-9 u-mt--8">범용인증서 발급받기</p>
        <div class="bank-list-wrap u-mt--12">
          <a class="c-button c-button--sm c-button--white" href="https://obank.kbstar.com/quics?page=C018872" title="새창" target="_blank">
            <img src="/resources/images/portal/content/btn-bank-kb.png" alt="국민은행">
          </a>
          <a class="c-button c-button--sm c-button--white" href="https://www.kebhana.com/certify/index.do" title="새창" target="_blank">
            <img src="/resources/images/portal/content/btn-bank-keb.png" alt="하나은행">
          </a>
          <a class="c-button c-button--sm c-button--white" href="https://spib.wooribank.com/pib/Dream?withyou=CTCER0001&fromSite=pib" title="새창" target="_blank">
            <img src="/resources/images/portal/content/btn-bank-woori.png" alt="우리은행">
          </a>
          <a class="c-button c-button--sm c-button--white" href="https://bank.shinhan.com/index.jsp#200101000000" title="새창" target="_blank">
            <img src="/resources/images/portal/content/btn-bank-shinhan.png" alt="신한은행">
          </a>
          <a class="c-button c-button--sm c-button--white" href="https://mybank.ibk.co.kr/uib/jsp/guest/cer/cer10/cer1000/CCER100000_i.jsp?APLY_EFNC_MENU_ID=P1401000000&SCRE_ID=CCER100000_i&MENU_DIV=GNB" title="새창" target="_blank">
            <img src="/resources/images/portal/content/btn-bank-ibk.png" alt="기업은행">
          </a>
          <a class="c-button c-button--sm c-button--white" href="https://www.citibank.co.kr/CerIsncIndv0100.act" title="새창" target="_blank">
            <img src="/resources/images/portal/content/btn-bank-citi.png" alt="시티은행">
          </a>
          <a class="c-button c-button--sm c-button--white" href="https://open.standardchartered.co.kr/service/cert/ctMain?menuid=OIB09010000000000&secureData=&langCode=KR&ptfrm=HIN.KOR.INTROPC.banking8" title="새창" target="_blank">
            <img src="/resources/images/portal/content/btn-bank-sc.png" alt="제일은행">
          </a>
        </div>
      </div>
      </c:if>
    </c:forEach>

    <%-- 상담원 전화도움 설명 문구 --%>
    <%--
    <c:if test="${helpDesc eq 'Y'}">
    <div class="c-form-wrap _isDefault _isTeen">
      <div class="c-form-group" role="group" aira-labelledby="chkCallTitle${spIndex}">
        <h3 class="c-group--head c-hidden" id="chkCallTitle${spIndex}">전화상담</h3>
        <div class="c-agree">
          <div class="c-agree__item">
            <input class="c-checkbox" id="authCheckNone01${spIndex}" type="checkbox" name="authCheckNone${spIndex}">
            <label class="c-label" for="authCheckNone01${spIndex}">
              <p class="c-text c-text--fs16 u-co-gray">본인인증이 어려울 경우 신청서 작성 후 상담원이 전화드려 가입을 도와드립니다.(선택)
                <br>※ 전화상담 가입 시 셀프개통 추가 혜택은 제공이 불가합니다.
                <br>※ 전화상담 가입 시 내국인 신청만 가능합니다. (Only Koreans can apply when signing up throungh telephone consultation.)
              </p>
            </label>
          </div>
        </div>
      </div>
    </div>
    </c:if>
    --%>

    <%-- 본인인증 버튼 --%>
    <div class="c-button-wrap u-mt--40" style="display:none">
      <button class="c-button c-button--md c-button--mint c-button--w460" id="btnNiceAuth${spIndex}" title="새창열림">신용카드 인증</button>
    </div>

  </div>
</c:if>