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
		<script type="text/javascript" src="/resources/js/mobile/cs/reqForm/reqFormList.js"></script>
	</t:putAttribute>
	<t:putAttribute name="contentAttr">

<input type="hidden" name="pageNo" id="pageNo" value="${boardDto.pageNo}" />

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					신청서식
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-form c-form--search">
				<div class="c-form__input">
					<input class="c-input" type="text" placeholder="검색어를 입력해주세요" id="searchNm" name="searchNm" title="검색어 입력" value="${boardDto.searchNm}" onkeypress="if( event.keyCode==13 ){searchBtn();}" maxlength="20">
					<button class="c-button c-button--search-bk" onclick="searchBtn(); return false;">
						<span class="c-hidden">검색어</span>
						<i class="c-icon c-icon--search-bk" aria-hidden="true"></i>
					</button>
				</div>
			</div>
			<!-- 고정 게시물-->
			<ul class="c-list c-list--type1 c-list--download">
				<c:if test="${noticeList ne null and !empty noticeList}">
					<c:forEach items="${noticeList}" var="noticeList">
						<li class="c-list__item">
							<strong class="c-list__title c-text-ellipsis">
								<span class="u-co-red">[공지]</span>${noticeList.boardSubject}
							</strong>
							<span class="c-list__desc c-text-ellipsis">${noticeList.boardContents}</span>
							<span class="c-list__sub">${noticeList.cretDt}</span>
							<a class="c-button"	href="javascript:fileDownLoad('${noticeList.attSeq}','1',fileDownCallBack,'${noticeList.boardSeq}');">
								<i class="c-icon c-icon--download" aria-hidden="true"></i>
							</a>

						</li>
					</c:forEach>
				</c:if>
			</ul>
			<!-- //-->

			<!-- case1 : 데이터 있을 경우-->
			<ul class="c-list c-list--type1 c-list--download" id="liDataArea">
			</ul>

			<div class="c-button-wrap" style="display:none;" id="addBtnArea">
				<button class="c-button c-button--full" id="moreBtn">
					더보기 <span class="c-button__sub" id="pageNav"><span id="listCount"></span>/<span id="totalCount"></span></span>
					<i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
				</button>
			</div>

			<div class="c-nodata" style="display:none;">
				<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
				<p class="c-noadat__text">일치하는 검색 결과가 없습니다.</p>
			</div>
			<!-- //-->
		</div>

	</t:putAttribute>
</t:insertDefinition>