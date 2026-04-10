<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<div class="c-modal__content">
    <div class="c-modal__header">
        <h2 class="c-modal__title" id="failPopTitle">${ErrorTitle}</h2>
        <button class="c-button" id="failPopClose" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
        </button>
    </div>
    <div class="c-modal__body u-ta-center">
        ${ErrorMsg}
        <div class="c-button-wrap u-mt--56" data-dialog-close>
            <button class="c-button c-button--lg c-button--primary u-width--460">확인</button>
        </div>
    </div>
</div>