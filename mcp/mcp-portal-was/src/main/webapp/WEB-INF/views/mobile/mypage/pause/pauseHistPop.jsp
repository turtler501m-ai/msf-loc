<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<div class="c-modal__content">
	<div class="c-modal__header">
		<h1 class="c-modal__title" id="explain-title">일시정지 이용 내역</h1>
		<button class="c-button" data-dialog-close>
			<i class="c-icon c-icon--close" aria-hidden="true"></i>
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>

	<div class="c-modal__body">
		<div class="c-table c-table--plr-8 u-mt--32">
			<p class="c-table-title u-co-black fs-16 u-fw--medium">휴대폰 일시정지 내역</p>
			<c:choose>
				<c:when test="${suspenPosHisList ne null and !empty suspenPosHisList}">
					<table>
						<caption>휴대폰 일시정지 내역</caption>
						<colgroup>
							<col style="width: 23%">
							<col style="width: 23%">
							<col style="width: 26%">
							<col style="width: 18%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">시작일</th>
								<th scope="col">종료일</th>
								<th scope="col">구분</th>
								<th scope="col">정지일수</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${suspenPosHisList}" var="suspenPosHisList">
							<tr>
								<td class="u-ta-center">${suspenPosHisList.remainSusCnt}</td>
								<td class="u-ta-center">${suspenPosHisList.colSusDays}</td>
								<td class="u-ta-center">${suspenPosHisList.csaActivityRsnDesc}</td>
								<td class="u-ta-center">${suspenPosHisList.remainOgDays}</td>
							</tr>
						</c:forEach>

						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<div class="c-nodata">
						<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
						<p class="c-noadat__text">조회 결과가 없습니다.</p>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
			<li class="c-text-list__item u-co-gray u-mt--0">내역조회는 최근 1년간 최대 100건까지만 가능합니다.</li>
		</ul>
	</div>
</div>