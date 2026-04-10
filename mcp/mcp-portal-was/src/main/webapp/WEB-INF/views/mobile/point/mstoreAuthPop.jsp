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

<script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
<script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
<script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
<script type="text/javascript" src="/resources/js/common/validator.js"></script>
<script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
<script type="text/javascript" src="/resources/js/mobile/point/mstoreAuthPop.js"></script>

<input type="hidden" id="isAuth" name="isAuth">
<input type="hidden" id="defaultAuth" name="defaultAuth" value= "C">

<div class="c-modal__content">
    <div class="c-modal__header">
        <h2 class="c-modal__title" id="modalArsTitle">M마켓 본인인증</h2>
        <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" id="closePopup" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
        </button>
    </div>
    <div class="c-modal__body">
        <!-- 본인인증 방법 선택 -->
        <jsp:include page="/WEB-INF/views/mobile/popup/niceAuthForm.jsp">
            <jsp:param name="controlYn" value="N"/>
            <jsp:param name="reqAuth" value="CNAT"/>
        </jsp:include>
    </div>
</div>

