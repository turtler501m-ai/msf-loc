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
			<ul class="webtoon-view">
				<c:forEach var="privacyEtcBoardList" items="${privacyEtcBoardList}">
					<li class="webtoon-view__item">
						<div class="c-list c-list--type1">
							<div class="c-list__item c-list__item--icon">
								<i class="c-list__image" aria-hidden="true">
									<img src="${privacyEtcBoardList.imgNm}" alt="">
								</i>
								<a class="c-list__anchor" href="${privacyEtcBoardList.boardContents}" target="_blank" title="${privacyEtcBoardList.boardSubject} 새창 열림">
									<strong class="c-list__title">${privacyEtcBoardList.boardSubject}</strong>
									<span class="c-hidden">바로가기</span>
									<i class="c-icon c-icon--anchor" aria-hidden="true"></i>
								</a>
							</div>
						</div>
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
	<p class="c-text c-text--fs16 u-mt--48 u-fw--bold">출처</p>
	<ul class="c-text-list c-bullet c-bullet--dot">
		<li class="c-text-list__item u-co-gray">방송통신이용자포털(http://www.wiseuser.go.kr)</li>
	</ul>
</div>
