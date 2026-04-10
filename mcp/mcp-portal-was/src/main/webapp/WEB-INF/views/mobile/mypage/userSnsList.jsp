<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un"
    uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<un:useConstants var="Constants"
    className="com.ktmmobile.mcp.common.constants.Constants" />

<c:set var="serverNm" value="${nmcp:getPropertiesVal('SERVER_NAME')}" />
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
        <script type="text/javascript"
            src="/resources/js/mobile/mypage/userSnsList.js"></script>
        <script type="text/javascript" src="/resources/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
        <script type="text/javascript" src="/resources/js/naver.js"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
        <input type="hidden" id="clientId" name="clientId" value="<spring:eval expression="@propertiesForJsp['NAVER_CLIENT_ID_NEW']" />"  >
        <input type="hidden" id="serviceUrl" name="serviceUrl" value="${nmcp:getServerInfo()}"  >
        <input type="hidden" id="pcCallbackUrl" name="mobileCallbackUrl" value="${nmcp:getServerInfo()}<spring:eval expression="@propertiesForJsp['NEARO_MOBILE_LOGIN_CALLBACK_URL']" />"  >

    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">소셜 로그인 관리<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <h4 class="c-heading c-heading--type2 u-mt--32 u-mb--12">소셜 계정 연결 설정</h4>
      <div class="info-box">
        <dl class="c-flex u-pt--12 u-pb--12">
          <dt>네이버</dt>
          <dd>
            <div class="c-chk-wrap">
              <input class="c-checkbox c-checkbox--switch" id="swtA1" type="checkbox" name="swtA">
              <label class="c-label" for="swtA1"> </label>
            </div>
            <div id="naver_id_login" class="hidden" style="display:none;"></div>
          </dd>
        </dl>
        <dl>
          <dt>카카오</dt>
          <dd>
            <div class="c-chk-wrap">
              <input class="c-checkbox c-checkbox--switch" id="swtA3" type="checkbox" name="swtA" >
              <label class="c-label" for="swtA3"> </label>
            </div>
          </dd>
        </dl>
        <c:if test = "${platFormCd eq 'A'}">
            <dl>
              <dt>애플</dt>
              <dd>
                <div class="c-chk-wrap">
                  <input class="c-checkbox c-checkbox--switch" id="swtA4" type="checkbox" name="swtA" >
                  <label class="c-label" for="swtA4"> </label>
                </div>
              </dd>
            </dl>
        </c:if>
      </div>
    </div>
  </div><!-- [START]-->




    </t:putAttribute>
</t:insertDefinition>