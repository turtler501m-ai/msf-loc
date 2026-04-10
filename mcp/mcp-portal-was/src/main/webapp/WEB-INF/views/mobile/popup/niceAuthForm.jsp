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
    <script defer type="text/javascript" src="/resources/js/mobile/popup/niceAuth.js?version=24.05.21"></script>
  </c:if>

  <%-- 네이버인증 관련 사전준비 --%>
  <c:forEach items="${simpleAuthList}" var="auth">
    <c:if test="${auth.dtlCd eq 'N' && spIndex ne '2'}">
      <script defer type="text/javascript" src="/resources/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
      <script defer type="text/javascript" src="/resources/js/naver.js"></script>

      <input type="hidden" id="clientId" name="clientId" value="<spring:eval expression="@propertiesForJsp['NAVER_CLIENT_ID_NEW']"/>">
      <input type="hidden" id="serviceUrl" name="serviceUrl" value="${nmcp:getServerInfo()}">
      <input type="hidden" id="pcCallbackUrl" name="pcCallbackUrl" value="${nmcp:getServerInfo()}<spring:eval expression="@propertiesForJsp['NEARO_MOBILE_CALLBACK_URL']"/>">
      <div id="naver_id_login" class="hidden" style="display:none;"></div>
    </c:if>
  </c:forEach>


  <%-- 본인인증 종류 --%>
  <div class="c-form" id="authFrm">
    <span class="c-form__title--type2" id="spOnlineAuthType${spIndex}">본인인증 방법 선택</span>
    <div class="c-check-wrap c-check-wrap--column">

    <c:forEach items="${simpleAuthList}" var="auth" varStatus="status">
      <c:set var="authCnt" value="${status.count}"/>
      <c:if test="${spIndex eq '2'}"><c:set var="authCnt" value="${fn:length(simpleAuthList)+status.count}"/></c:if>
      <div class="c-chk-wrap">
        <input class="c-radio c-radio--button c-radio--button--icon" id="onlineAuthType${authCnt}" value="${auth.dtlCd}" type="radio" name="onlineAuthType${spIndex}">
        <label class="c-label u-ta-left" for="onlineAuthType${authCnt}">
          <i class="c-icon c-icon--card" aria-hidden="true"></i>${auth.dtlCdNm}
        </label>
      </div>
    </c:forEach>

    </div>
  </div>

  <%-- 본인인증 설명 문구 --%>
  <c:forEach items="${simpleAuthList}" var="auth" varStatus="status">
  <div class="c-form _onlineAuthType${spIndex}" data-value="${auth.dtlCd}" style="display:none">
    <ul class="c-text-list c-bullet c-bullet--dot c-bullet--type2">
      <c:choose>
        <c:when test="${auth.dtlCd eq 'C'}">
          <li class='c-text-list__item'>고객님의 본인명의(미성년자의 경우 법정 대리인)의 국내발행 신용카드 정보를 입력해주세요.(체크카드 인증불가)</li>
          <li class='c-text-list__item'>신용카드 비밀번호 3회 이상 오류 시 해당카드로 인증을 받으실 수 없으니 유의하시기 바랍니다.</li>
        </c:when>
        <c:when test="${auth.dtlCd eq 'N'}">
          <li class='c-text-list__item'>네이버 앱에 인증을 진행하는 사용자의 ID가 로그인되어 있어야 인증 진행이 가능합니다.</li>
          <li class='c-text-list__item'>네이버인증서는 발급 과정에서 본인명의의 휴대폰인증이 필요하며, 아이폰5S/갤럭시S5, Note4 이상에서 지원됩니다.(iOS10, 안드로이드6 이상)</li>
          <li class='c-text-list__item'>네이버 인증이 정상적으로 진행되지 않으실 경우 네이버인증서를 재발급 받으신 후 이용이 가능합니다.</li>
        </c:when>
        <c:when test="${auth.dtlCd eq 'A'}">
          <li class='c-text-list__item'>PASS 인증서 발급 과정에서 본인명의의 휴대폰인증이 필요합니다.</li>
          <li class='c-text-list__item'>PASS 인증서는 PASS 앱 설치 후 이용 가능합니다.</li>
        </c:when>
        <c:when test="${auth.dtlCd eq 'X'}">
          <li class='c-text-list__item'>은행 및 한국전자인증, 한국정보인증에서 발급된 범용 공인증서(유료)만 사용 가능합니다.</li>
          <li class='c-text-list__item'>체크카드는 인증이 불가합니다.</li>
        </c:when>
        <c:when test="${auth.dtlCd eq 'T'}">
          <li class='c-text-list__item'>toss 인증서 발급 과정에서 본인명의의 휴대폰인증이 필요합니다.</li>
          <li class='c-text-list__item'>toss 인증서는 toss 앱 설치 후 이용이 가능합니다.</li>
        </c:when>
        <c:when test="${auth.dtlCd eq 'K'}">
          <li class='c-text-list__item'>카카오 본인인증 발급 과정에서 본인명의의 휴대폰인증이 필요합니다.</li>
          <li class='c-text-list__item'>카카오 본인인증 발급 인증서는 카카오톡 앱 설치 후 이용이 가능합니다.</li>
        </c:when>
      </c:choose>
    </ul>
    <div class="c-button-wrap u-mt--40">
      <button id="btnNiceAuth${auth.dtlCd}${spIndex}" class="c-button c-button--full c-button--h48 c-button--mint _btnNiceAuth${spIndex}">${auth.expnsnStrVal1}</button>
    </div>
  </div>
  </c:forEach>

  <%-- 상담원 전화도움 설명 문구 --%>
  <%--
  <c:if test="${helpDesc eq 'Y'}">
  <div class="c-form _isDefault _isTeen">
    <div class="c-box c-box--type6">
      <input class="c-checkbox" id="authCheckNone01${spIndex}" type="checkbox" name="authCheckNone${spIndex}">
      <label class="c-label u-fs-14 u-co-gray-9 top-check" for="authCheckNone01${spIndex}">본인인증이 어려울 경우 신청서 작성 후 상담원이 전화드려 가입을 도와드립니다.<span class="u-co-gray">(선택)</span>
        <ul class="c-text-list c-bullet c-bullet--fyr">
          <li class="c-text-list__item u-co-gray-9 u-fs-14">전화상담 가입 시 셀프개통 추가 혜택은 제공이 불가합니다.</li>
          <li class="c-text-list__item u-co-gray-9 u-fs-14">전화상담 가입 시 내국인 신청만 가능합니다. (Only Koreans can apply when signing up throungh telephone consultation.)</li>
        </ul>
      </label>
    </div>
  </div>
  </c:if>
  --%>

</c:if>