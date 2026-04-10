<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutNoGnbFotter">

	<t:putAttribute name="titleAttr">서류 등록</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.09.30"></script>
		<script type="text/javascript" src="/resources/js/mobile/document/receive/documentReceiveAuth.js?version=25.09.30"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
        <input type="hidden" id="docRcvId" value="<c:out value='${docRcvId}'/>" />
        <input type="hidden" id="rcvUrlId" value="<c:out value='${rcvUrlId}'/>" />

        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">서류 등록<button class="header-clone__gnb"></button>
                </h2>
            </div>

            <div class="c-form u-mt--40">
                <div class="c-form-group" role="group" aria-labelledby="formGroupA">
                    <div class="c-input-wrap">
                        <input class="c-input" type="text" id="maskedMobileNo" name="maskedMobileNo" placeholder="휴대폰 번호" title="휴대폰 번호 입력" maxlength="11" value="<c:out value='${maskedMobileNo}'/>" style="margin-top: 0px;" disabled>
                    </div>

                    <div class="c-input-wrap">
                        <input class="c-input numOnly" id="otp" placeholder="인증번호" maxlength="6" title="인증번호 입력">
                        <button type="button" class="c-button c-button--sm c-button--underline" id="resendBtn" onclick="confirmToResendNewOtp();">인증번호 재전송</button>
                    </div>
                </div>
            </div>

            <div class="c-button-wrap">
                <button class="c-button c-button--full c-button--primary" type="button" id="authenticateBtn" onclick="authenticateOtp();">확인</button>
            </div>
        </div>
	</t:putAttribute>
</t:insertDefinition>