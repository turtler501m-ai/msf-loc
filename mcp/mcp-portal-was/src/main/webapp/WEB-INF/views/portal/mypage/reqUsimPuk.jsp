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

    <t:putAttribute name="titleAttr">유심PUK번호 열람신청</t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/portal/vendor/flatpickr.min.js"></script>
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
    <script type="text/javascript" src="/resources/js/common/validator.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
    <script type="text/javascript" src="/resources/js/portal/mypage/reqUsimPuk.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <form name="selfFrm" id="selfFrm" action="/mypage/reqUsimPuk.do" method="post">
        <input type="hidden" name="ncn" value="" >
    </form>
    <input type="hidden" id="isAuth" name="isAuth">
    <input type="hidden" id="contractNum" name="contractNum" value="${searchVO.contractNum}">

    <div class="ly-content" id="main-content">
        <div class="ly-page--title">
            <h2 class="c-page--title">유심PUK번호 열람신청</h2>
        </div>
        <div class="callhistory-info">
            <div class="callhistory-info__list">
                <h3>유심PUK번호 열람신청이란?</h3>
                <ul>
                	<li>PUK는 USIM의 PIN을 초기화 할 수 있는 8자리의 비밀번호입니다.</li>
                </ul>
                <ul>
                    <li>PIN을 3회 연속 잘못입력하면 PUK입력화면으로 넘어가며 PUK를 10회 잘못입력할 경우 USIM이 영구사용 불가하게 되어 새로 구매 후 변경하셔야합니다.</li>
                </ul>
                <p class="c-bullet c-bullet--fyr u-co-gray">PIN은 UISM분실시에 USIM에 담긴 개인정보 보호를 위해 설정하는 USIM 비밀번호이며, 핸드폰에서 PIN을 설정하시면 USIM분실시에도 타인이 사용할 수 없어 안전합니다.</p>
                <p class="c-bullet c-bullet--fyr u-co-gray">PIN 비밀번호는 숫자 4자리이며, 처음 설정하는 경우 ‘0000’을 입력하시면 됩니다.</p>
                <ul>
                    <li>PUK번호는 타인에게 노출되지 않게 각별한 관리 부탁드립니다.</li>
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

           	<div class="c-button-wrap u-mt--56">
             	<button class="c-button c-button--lg c-button--primary c-button--w220" id="btnRequest" >열람신청</button>
           	</div>
        </div>
    </div>

    </t:putAttribute>
</t:insertDefinition>
