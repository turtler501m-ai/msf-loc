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
	<script type="text/javascript" src="/resources/js/mobile/cs/notice/noticeView.js"></script>
	<script type="text/javascript">

		var pageNo = $("#pageNo").val();
		var searchNm = $("#searchNm").val().trim();
		var boardSeq = $("#boardSeq").val();
		var state = { 'pageNo': pageNo,'searchNm':searchNm,'boardSeq':boardSeq};

		history.pushState(null, null,location.href);
		window.onpopstate = function (event){
			location.href = "/m/cs/noticeBoardList.do";
  		 }
	</script>
</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="detail-content">
			<div class="ly-page-sticky" >
				<h2 class="ly-page__head">
					공지사항
					<button class="header-clone__gnb"></button>
				</h2>
			</div>

			<div class="c-board">
				<strong class="c-board__title">${viewResult.boardSubject}</strong>
				<span class="c-board__sub">
					<i class="c-icon c-icon--calendar" aria-hidden="true"></i>
					${viewResult.boardWriteDt}
				</span>
				<div class="c-board__content">
					<article class="c-editor">${viewResult.boardContents}</article>
				</div>
				<!-- 고정게시물 상세일 경우 비노출-->
				<%-- <c:if test="${notiDto.notiYn eq 'N'}"> --%>
					<div class="c-board__nav">
						<c:choose>
							<c:when test="${notiDto.preSeq ne 0 && notiDto.preSeq ne null}">
								<a class="c-button" href="javascript:goNextDetail('${notiDto.preSeq}')">
									<i class="c-icon c-icon--triangle-left" aria-hidden="true"></i>
									<span>이전글</span>
								</a>
							</c:when>
							<c:otherwise>
								<a class="c-button is-disabled">
									<i class="c-icon c-icon--triangle-left" aria-hidden="true"></i> <span>이전글</span>
								</a>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${notiDto.nextSeq ne 0 && notiDto.nextSeq ne null}">
								<a class="c-button" href="javascript:goNextDetail('${notiDto.nextSeq}')">
									<span>다음글</span>
									<i class="c-icon c-icon--triangle-right" aria-hidden="true"></i>
								</a>
							</c:when>
							<c:otherwise>
								<a class="c-button is-disabled"> <span>다음글</span>
									<i class="c-icon c-icon--triangle-right" aria-hidden="true"></i>
								</a>
							</c:otherwise>
						</c:choose>
					</div>
				<%-- </c:if> --%>
				<!-- //-->
			</div>
			<div class="c-button-wrap">
				<a href="javascript:;" class="c-button c-button--full c-button--white" id="viewList">목록보기
					<div class="c-tabs__panel" id="tabB2-panel"></div>
					<div class="c-tabs__panel" id="tabB3-panel"></div>
				</a>
			</div>
		</div>

<input type="hidden" id="searchNm" name="searchNm" value="${boardDto.searchNm}" />
<input type="hidden" id="pageNo" name="pageNo" value="${boardDto.pageNo}" />
<input type="hidden" id="boardSeq" name="boardSeq" value="${boardDto.boardSeq}" />

	</t:putAttribute>
</t:insertDefinition>