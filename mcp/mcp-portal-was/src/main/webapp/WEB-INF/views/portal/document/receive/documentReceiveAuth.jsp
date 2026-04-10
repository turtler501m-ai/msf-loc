<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutNoGnbDefault">

	<t:putAttribute name="titleAttr">서류 등록</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.09.30"></script>
		<script type="text/javascript" src="/resources/js/portal/document/receive/documentReceiveAuth.js?version=25.09.30"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
        <input type="hidden" id="docRcvId" value="<c:out value='${docRcvId}'/>" />
        <input type="hidden" id="rcvUrlId" value="<c:out value='${rcvUrlId}'/>" />

		<div class="ly-content" id="main-content">
			<div class="ly-page--title">
				<h2 class="c-page--title">서류 등록</h2>
			</div>
            <div class="c-row c-row--lg">
                <div class="c-form-wrap u-width--460 u-margin-auto">
                    <div class="c-form-group" role="group" aria-labelledby="formGroupA">
                        <div class="c-form">
                            <div class="c-input-wrap">
                                <input class="c-input" id="maskedMobileNo" name="maskedMobileNo" type="text" placeholder="휴대폰 번호" aria-invalid="true" maxlength="11" value="<c:out value='${maskedMobileNo}'/>" aria-describedby="phoneChk" style="margin-top: 0px;" disabled>
                                <label class="c-form__label c-hidden" for="maskedMobileNo">휴대폰 번호 입력</label>
                            </div>
                        </div>

                        <div class="c-form">
                            <div class="c-input-wrap">
                                <input class="c-input numOnly" id="otp" type="text" placeholder="인증번호" maxlength="6">
                                <label class="c-form__label c-hidden" for="otp">인증번호 입력</label>
                                <button type="button" class="c-button c-button--sm c-button--underline" id="resendBtn" onclick="confirmToResendNewOtp();">인증번호 재전송</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="c-button-wrap c-button-wrap--column u-mt--52">
                    <button type="button" class="c-button c-button--lg c-button--primary u-width--460" id="authenticateBtn" onclick="authenticateOtp();">확인</button>
                </div>
            </div>
		</div>
	</t:putAttribute>
</t:insertDefinition>