<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<div class="c-modal__content">
	<div class="c-modal__header">
		<h2 class="c-modal__title" id="modalPromotionTitle">약정할인 프로그램 및 할인반환금 안내</h2>
		<button class="c-button" data-dialog-close>
			<i class="c-icon c-icon--close" aria-hidden="true"></i> 
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body">${rateAgreeDto.docContent}</div>
</div>
