<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="serverNm" value="${nmcp:getPropertiesVal('SERVER_NAME')}" />
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />
<c:set var="jsver" value="${nmcp:getJsver()}" />
<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/login/loginForm.js?ver=${jsver}"></script>
        <script type="text/javascript" src="/resources/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
        <script type="text/javascript" src="/resources/js/naver.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/popup/diAuth.js"></script>
        <c:if test = "${recaptchaPlatform eq 'Y'}">
            <script src="https://www.google.com/recaptcha/api.js?render=6LctwxopAAAAAMtpxTvHOWe5EKPy9YgAep_DgRns"></script>
            <script type="text/javascript" src="/resources/js/common/recaptchaLogin.js"></script>
        </c:if>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
            <input type="hidden" id="clientId" name="clientId" value="<spring:eval expression="@propertiesForJsp['NAVER_CLIENT_ID_NEW']" />"  >
            <input type="hidden" id="serviceUrl" name="serviceUrl" value="${nmcp:getServerInfo()}"  >
            <input type="hidden" id="pcCallbackUrl" name="mobileCallbackUrl" value="${nmcp:getServerInfo()}<spring:eval expression="@propertiesForJsp['NEARO_MOBILE_LOGIN_CALLBACK_URL']" />"  >

            <div class="ly-content" id="main-content">
                <form id="mainForm" name="mainForm" method="POST">
                    <!-- App 전용 -->
                    <input type="hidden" name="platFormCd" id="platFormCd" value="${nmcp:getPlatFormCd()}"/>
                    <input type="hidden" name="uuid" id="uuid"/>
                    <input type="hidden" name="token" id="token"/>
                    <input type="hidden" name="uri" id="uri" value="${uri}" />
                    <input type="hidden" id="lastSnsLogin" value="${LastSnsLogin}"/>
                    <input type="hidden" name="loginDivCd" id="loginDivCd" value="ID"/>
                    <input type="hidden" name="recaptchaToken" id="recaptchaToken" value=""/>
                    <input type="hidden" name="loginAuto" id="loginAuto" value="N"/>
                    <div class="login">
                        <p class="login__title_m">
                            <span> <img src="/resources/images/mobile/common/img_logo_kt.png" alt="KT">가 만든 </span> <b>압도적 1등</b>알뜰폰
                        </p>
                        <input class="c-input" type="text" id="userId" name="userId" placeholder="아이디" title="아이디 입력" onkeyup="nextCheck();" maxlength="20">
                        <input class="c-input" type="password" id="passWord" name="passWord" placeholder="비밀번호" title="비밀번호 입력" onkeyup="nextCheck();" maxlength="20">
                        <div class="c-checkbox-wrap">
                            <input class="c-checkbox" id="idSave" type="checkbox" name="idSave">
                            <label class="c-label" for="idSave">아이디 저장</label>
                        </div>
                        <div class="c-button-wrap">
                            <button type="button" class="c-button c-button--full c-button--primary is-disabled" onclick="goLogin(); return false;" id="loginBtn">로그인</button>
                        </div>
                        <h3 class="u-fw--medium u-ta-center u-mt--48">
                            - SNS 계정으로 로그인 -
                        </h3>
                        <div class="login-sns_m u-mt--20">
                            <div class="login-sns__item">
                                <a class="c-button" href="javascript:void(0);" onclick="loginWithKakao();"> <span class="c-hidden">카카오</span>
                                    <i class="c-icon c-icon--kakao" aria-hidden="true"></i>
                                </a>
                                <div class="c-balloon c-balloon--center" id="kakaoToolTip" style="display: none;">마지막 로그인한 계정</div>
                            </div>
                           <!--  <div class="login-sns__item">
                                <a class="c-button" href="javascript:void(0);" onclick="loginWithFacebook();"> <span class="c-hidden">페이스북</span>
                                    <i class="c-icon c-icon--facebook" aria-hidden="true"></i>
                                </a>
                                <div class="c-balloon c-balloon--center" id="facebookToolTip" style="display: none;">마지막 로그인한 계정</div>
                            </div> -->
                            <div class="login-sns__item">
                                <a class="c-button" href="javascript:void(0);" onclick="loginWithNaver();"> <span class="c-hidden">네이버</span>
                                    <i class="c-icon c-icon--naver" aria-hidden="true"></i>
                                </a>
                                <div id="naver_id_login" class="hidden" style="display:none;"></div>
                                <div class="c-balloon c-balloon--center" id="naverToolTip" style="display: none;">마지막 로그인한 계정</div>
                            </div>
                        </div>
                        <div class="c-button-wrap">
                            <a class="c-button c-button--xsm" href="/m/findPassword.do">아이디 찾기</a>
                            <a class="c-button c-button--xsm" href="/m/findPassword.do?tabId=p">비밀번호 찾기</a>
                            <a class="c-button c-button--xsm" href="/m/join/fstPage.do"> <b>회원가입</b></a>
                        </div>
                    </div>
                </form>
            </div>
    </t:putAttribute>
</t:insertDefinition>