<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/mobile/cs/notice/noticeList.js"></script>
	</t:putAttribute>
	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="main-content">

			<input type="hidden" id="boardSeq" name="boardSeq" value="${boardDto.boardSeq}">
			<input type="hidden" id="boardCtgSeq" name="boardCtgSeq" value="51" />
			<input type="hidden" id="pageNo" name="pageNo" value="${pageInfoBean.pageNo}">
			<input type="hidden" id="totalCount" name="totalCount" value="${totalCount}">

			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					공지사항
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div id="contentLayer">
				<div class="c-form c-form--search">
					<div class="c-form__input">
						<input class="c-input" type="text" placeholder="검색어를 입력해주세요" id="searchNm" title="검색어 입력" value="${boardDto.searchNm}" onkeypress="if(event.keyCode==13) {searchBtn(); return false;}" maxlength="20">
						<button class="c-button c-button--search-bk" onclick="searchBtn(); return false;">
							<span class="c-hidden">검색어</span>
							<i class="c-icon c-icon--search-bk" aria-hidden="true"></i>
						</button>
					</div>
				</div>

				<c:if test="${noticeList ne null and !empty noticeList}">
					<ul class="c-list c-list--type1" id="topAataArea">
						<c:forEach items="${noticeList}" var="topBoardList">
							<li class='c-list__item'>
								<a class='c-list__anchor' href='javascript:goDetail("${topBoardList.boardSeq}")'>
									<strong class='c-list__title c-text-ellipsis'>
										<span class='u-co-red'>[공지]</span>${topBoardList.boardSubject}
									</strong>
									<span class='c-list__sub'>${topBoardList.cretDt}</span>
								</a>
							</li>
						</c:forEach>
					</ul>
				</c:if>


				<c:choose>
					<c:when test="${resultList ne null and !empty resultList}">
					<ul class="c-list c-list--type1">
						<c:forEach var="resultList" items="${resultList}" varStatus="status">
							<c:choose>
								<c:when test="${pageInfoBean.pageNo*10 >= status.count}"><li class="c-list__item list" boardSeq="${resultList.boardSeq}"></c:when>
								<c:otherwise><li class="c-list__item list" style="display:none;" boardSeq="${resultList.boardSeq}"></c:otherwise>
							</c:choose>
								<a class="c-list__anchor" href="javascript:goDetail('${resultList.boardSeq}')">
									<strong class="c-list__title c-text-ellipsis">${resultList.boardSubject}</strong>
									<span class="c-list__sub">${resultList.boardWriteDt}</span>
								</a>
							</li>
						</c:forEach>
					</ul>
					<c:if test="${totalCount > 10}">
						<div class="c-button-wrap" >
							<button class="c-button c-button--full" id="moreBtn">더보기
								<span class="c-button__sub" id="pageNav">
									<span id="listCount">10</span>/<span id="totalCount">${totalCount}</span>
								</span>
								<i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
							</button>
						</div>
					</c:if>
					</c:when>
					<c:otherwise>
						<div class="c-nodata">
							<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
							<p class="c-noadat__text">일치하는 검색 결과가 없습니다.</p>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

	</t:putAttribute>
</t:insertDefinition>