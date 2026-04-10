<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />
<c:choose>
	<c:when test="${privacyEtcBoardList ne null and !empty privacyEtcBoardList}">
		<ul class="c-list c-list--type1">
			<c:forEach var="privacyEtcBoardList" items="${privacyEtcBoardList}">
				<li class="c-list__item">
					<c:choose>
						<c:when test="${platFormCd eq 'A'}">
							<a class="c-list__anchor c-list__anchor--icon" href="javascript:appOutSideOpen('${privacyEtcBoardList.boardContents}');">
						</c:when>
						<c:otherwise>
							<a class="c-list__anchor c-list__anchor--icon" href="javascript:openPage('outLink','${privacyEtcBoardList.boardContents}','');">
						</c:otherwise>
					</c:choose>


						<strong class="c-list__title">${privacyEtcBoardList.boardSubject}</strong>
					</a>
				</li>
			</c:forEach>
		</ul>
	</c:when>
	<c:otherwise>
		<div class="c-nodata">
			<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
			<p class="c-noadat__text">일치하는 검색 결과가 없습니다.</p>
		</div>
	</c:otherwise>
</c:choose>
<p class="c-bullet c-bullet--dot">출처</p>
<ul class="c-text-list c-bullet c-bullet--hyphen">
	<li class="c-text-list__item u-co-gray">방송통신이용자포털(http://www.wiseuser.go.kr)</li>
	<li class="c-text-list__item u-co-gray">네이버 생활보안 (http://tv.naver.com/nvlifesec)</li>
	<li class="c-text-list__item u-co-gray">방송통신위원회 유튜브(https://www.youtube.com/user/KCCwith)</li>
</ul>