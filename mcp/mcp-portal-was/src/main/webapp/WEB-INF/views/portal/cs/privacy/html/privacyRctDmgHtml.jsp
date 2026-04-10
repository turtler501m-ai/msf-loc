<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<div class="c-row c-row--lg">
	<c:choose>
		<c:when test="${privacyEtcBoardList ne null and !empty privacyEtcBoardList}">
			<ul class="c-list c-list--type1">
				<c:forEach var="privacyEtcBoardList" items="${privacyEtcBoardList}">
					<li class="c-list__item">
						<strong class="c-list__title">${privacyEtcBoardList.boardSubject}</strong>
						<a class="c-list__anchor" href="${privacyEtcBoardList.boardContents}" target="_blank" title="${privacyEtcBoardList.boardSubject} 새창열림">바로가기
							<i class="c-icon c-icon--anchor" aria-hidden="true"></i>
						</a>
					</li>
				</c:forEach>
			</ul>
		</c:when>
		<c:otherwise>
			<div class="c-nodata">
				<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
				<p class="c-nodata__text">검색 결과가 없습니다.</p>
			</div>
		</c:otherwise>
	</c:choose>
</div>