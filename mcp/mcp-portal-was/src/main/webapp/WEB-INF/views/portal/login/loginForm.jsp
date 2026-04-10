<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="serverNm" value="${nmcp:getPropertiesVal('SERVER_NAME')}" />
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />
<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr"> 로그인</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/login/loginForm.js"></script>
        <script type="text/javascript" src="/resources/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
        <script type="text/javascript" src="/resources/js/naver.js"></script>
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/portal/popup/diAuth.js"></script>
        <c:if test = "${recaptchaPlatform eq 'Y'}">
            <script src="https://www.google.com/recaptcha/api.js?render=6LctwxopAAAAAMtpxTvHOWe5EKPy9YgAep_DgRns"></script>
            <script type="text/javascript" src="/resources/js/common/recaptchaLogin.js"></script>
        </c:if>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="clientId" name="clientId" value="<spring:eval expression="@propertiesForJsp['NAVER_CLIENT_ID_NEW']" />"  >
        <input type="hidden" id="serviceUrl" name="serviceUrl" value="${nmcp:getServerInfo()}"  >
        <input type="hidden" id="pcCallbackUrl" name="pcCallbackUrl" value="${nmcp:getServerInfo()}<spring:eval expression="@propertiesForJsp['NEARO_PC_LOGIN_CALLBACK_URL']" />"  >

        <div class="ly-content" id="main-content">
            <!--[2021-01-04] 로그인 타이틀 마크업 구조 변경-->
            <div class="ly-page--title fixed-login">
                <h2 class="c-page--title">
                    <span class="login-slogan">
                        <img class="text-logo" src="/resources/images/portal/common/ico_kt_logo.svg" alt="kt">가 만든 압도적 1등 알뜰폰
                    </span> <strong>로그인</strong>
                </h2>
            </div>
            <!--[2021-01-05] divider 수정-->
            <div class="c-row c-row--xlg c-row--divider-top"></div>
            <div class="c-row c-row--sm">
                <h3 class="c-heading--fs22 c-header--bold u-mt--56 u-mb--48">
                    아이디 / 비밀번호를 <br>입력해주세요.
                </h3>
                <form id="mainForm" name="mainForm" method="POST">
                    <input type="hidden" name="uri" id="uri" value="${uri}" />
                    <input type="hidden" id="lastSnsLogin" value="${LastSnsLogin}"/>
                    <input type="hidden" name="loginDivCd" id="loginDivCd" value="ID"/>
                    <input type="hidden" name="recaptchaToken" id="recaptchaToken" value=""/>
                    <div class="c-form">
                        <label class="c-label c-hidden" for="userId">아이디 입력</label>
                        <input class="c-input" id="userId" name="userId" type="text" placeholder="아이디" onkeyup="nextCheck();" maxlength="20">
                    </div>
                    <div class="c-form">
                        <label class="c-label c-hidden" for="passWord">비밀번호 입력</label>
                        <input class="c-input" id="passWord" name="passWord" type="password" placeholder="비밀번호" onkeyup="nextCheck();" maxlength="20">
                    </div>
                    <div class="c-form">
                        <input class="c-checkbox" id="idSave" type="checkbox" name="idSave">
                        <label class="c-label" for="idSave">아이디 저장</label>
                    </div>
                </form>
                <div class="c-button-wrap u-mt--56">
                    <button  type="button" class="c-button c-button--full c-button--primary" disabled onclick="goLogin(); return false;" id="loginBtn">로그인</button>
                </div>
                <div class="bottom-block u-mt--40">
                    <!-- [2021-12-23] 컬러, 간격 관련 클래스 추가-->
                    <div class="c-button-wrap">
                        <a class="c-button c-text--fs15 u-co-gray u-m--0" href="/findPassword.do">아이디 찾기</a> <span class="c-v-split"></span>
                        <a class="c-button c-text--fs15 u-co-gray u-m--0" href="/findPassword.do?tabId=p">비밀번호 찾기</a> <span class="c-v-split"></span>
                        <a class="c-button c-text--fs15 u-co-black u-m--0" href="/join/fstPage.do"> <b>회원가입</b></a>
                    </div>
                </div>
                <h3 class="u-fs-17 u-fw--medium u-ta-center u-mt--64">
                    - SNS 계정으로 로그인 -
                </h3>
                <div class="login-sns u-mt--24">
                  <!--   <div class="login-sns__item">
                        <a class="c-button" href="javascript:void(0);" onclick="loginWithFacebook();">
                            <img src="/resources/images/portal/common/ico_sns_facebook.svg" alt="페이스북-새창열림">
                        </a>
                        <div class="c-balloon c-balloon--center" id="facebookToolTip" style="display: none;">마지막 로그인한 계정</div>
                    </div> -->
                    <div class="login-sns__item">
                        <a class="c-button" href="javascript:void(0);" onclick="loginWithKakao();">
                            <img src="/resources/images/portal/common/ico_sns_kakao.svg" alt="카카오-새창열림">
                        </a>
                        <div class="c-balloon c-balloon--center" id="kakaoToolTip" style="display: none;">마지막 로그인한 계정</div>
                    </div>
                    <div class="login-sns__item">
                        <a class="c-button" href="javascript:void(0);" onclick="loginWithNaver();">
                            <img src="/resources/images/portal/common/ico_sns_naver.svg" alt="네이버-새창열림">
                        </a>
                        <div id="naver_id_login" class="hidden" style="display:none;"></div>
                        <div class="c-balloon c-balloon--center" id="naverToolTip" style="display: none;">마지막 로그인한 계정</div>
                    </div>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>
