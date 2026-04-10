<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">휴면회원 해제</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/portal/login/dormantUserView.js?version=23-11-22"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.placeholder.js"></script>
        <script type="text/javascript" src="/resources/js/portal/popup/diAuth.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">휴면회원 해제</h2>
            </div>
            <!--[2021-01-05] divider 수정-->
            <div class="c-row c-row--xlg c-row--divider-top"></div>
            <div class="c-row c-row--md-join">
				<h3 class="u-fs-24 u-fw--bold u-ta-center u-mt--64">
	            	회원님의 아이디는 현재 휴면 상태입니다.
	          	</h3>
	          	<p class="u-co-gray u-ta-center u-mt--16">
	          		회원님은 로그인 한 지 1년 이상 지나 아이디가 휴면 상태로 전환되었습니다.<br />
					휴면 해제를 위한 본인 인증 후 서비스를 정상적으로 이용하실 수 있습니다.
				</p>
                <div class="c-button-wrap u-mt--48">
	              <button class="c-button c-button-round--md c-button--mint c-button--w460" onclick="pop_nice();">휴대폰 인증</button>
	            </div>
                <ul class="c-text-list c-bullet c-bullet--dot u-mt--48">
                    <li class="c-text-list__item u-co-gray">본인인증 시 제공되는 정보는 해당 인증기관에서 직접 수집하므로 인증 이외의 용도로 이용 또는 저장되지 않습니다.</li>
                </ul>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
