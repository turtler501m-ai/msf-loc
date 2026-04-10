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
		<h2 class="c-modal__title" id="modalStopTitle">일시정지 이용 내역</h2>
		<button class="c-button" data-dialog-close>
			<i class="c-icon c-icon--close" aria-hidden="true"></i> <span
				class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body">
		<h3 class="c-heading c-heading--fs20">휴대폰 일시정지 내역</h3>
		<div class="c-table u-mt--32">
			<table>
				<caption>시작일, 종료일, 구분, 정지일수 정보를 포함한 휴대폰 일시정지 내역</caption>
				<colgroup>
					<col style="width: 25%">
					<col style="width: 25%">
					<col>
					<col style="width: 25%">
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
					<c:choose>
						<c:when test="${suspenPosHisList ne null and !empty suspenPosHisList}">
							<c:forEach items="${suspenPosHisList}" var="suspenPosHisList">
								<tr>
									<td>${suspenPosHisList.remainSusCnt}</td>
									<td>${suspenPosHisList.colSusDays}</td>
									<td>${suspenPosHisList.csaActivityRsnDesc}</td>
									<td>${suspenPosHisList.remainOgDays}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="4">조회 결과가 없습니다.</td>
							</tr>
							</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<div class="c-bullet c-bullet--fyr u-co-gray u-mt--16">내역조회는 최근 1년간 최대 100건까지만 가능합니다.</div>
	</div>
</div>