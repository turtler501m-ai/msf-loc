<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<div class="c-modal__dialog" role="document">	
	<div class="c-modal__content">
		<div class="c-modal__header" style="padding:3rem 3rem 3rem; font-size:1.3rem; font-weight:700;">
			<h2 class="c-modal__title" id="mbershipModalTitle">자세히 보기</h2>
			<button class="c-button" data-dialog-close>
				<i class="c-icon c-icon--close" aria-hidden="true"></i>
				<span class="c-hidden">팝업닫기</span>
			</button>
		</div>
		<div class="c-modal__body" style="padding:0rem 0rem 0rem;">
			<c:choose>
				<c:when test="${mbDtlDto ne null and !empty mbDtlDto}">
					${mbDtlDto.imgContentPc}
				</c:when>			
				<c:otherwise>
					<div class="c-nodata">
						<i class="c-icon c-icon--nodata-coupon" aria-hidden="true"></i>
						<p class="c-nodata__text">잘못된 접근입니다.</p>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>