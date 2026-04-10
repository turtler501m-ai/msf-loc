<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="serverNm" value="${nmcp:getPropertiesVal('SERVER_NAME')}" />
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />
<t:insertDefinition name="layoutMainDefault">
    <t:putAttribute name="scriptHeaderAttr">
         <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/portal/mypage/userSnsList.js"></script>
        <script type="text/javascript" src="/resources/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
        <script type="text/javascript" src="/resources/js/naver.js"></script>

    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="clientId" name="clientId" value="<spring:eval expression="@propertiesForJsp['NAVER_CLIENT_ID_NEW']" />"  >
        <input type="hidden" id="serviceUrl" name="serviceUrl" value="${nmcp:getServerInfo()}"  >
        <input type="hidden" id="pcCallbackUrl" name="pcCallbackUrl" value="${nmcp:getServerInfo()}<spring:eval expression="@propertiesForJsp['NEARO_PC_LOGIN_CALLBACK_URL']" />"  >
        <div class="ly-content" id="main-content">
          <div class="ly-page--title">
            <h2 class="c-page--title">소셜로그인 관리</h2>
          </div>
          <div class="c-row c-row--lg">
            <div class="c-form-wrap">
              <div class="c-form-group" role="group" aira-labelledby="formGroupA">
                <h3 class="c-group--head u-fs-20 u-fw--regular" id="formGroupA">소셜 계정 연결 설정</h3>
                <div class="sns">
                  <div class="sns__item">
                    <button class="c-button c-button--switch" id="switch1">
                      <i class="c-icon c-icon--naver" aria-hidden="true"></i>
                      <span class="c-button--switch__text">네이버</span>
                    </button>
                    <div id="naver_id_login" class="hidden" style="display:none;"></div>
                  </div>
                  <div class="sns__item">
                    <button class="c-button c-button--switch" id="switch3" >
                      <i class="c-icon c-icon--kakao" aria-hidden="true"></i>
                      <span class="c-button--switch__text">카카오</span>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
    </t:putAttribute>

</t:insertDefinition>