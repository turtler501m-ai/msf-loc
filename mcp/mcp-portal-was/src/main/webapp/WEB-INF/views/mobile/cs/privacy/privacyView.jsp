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
		<script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
		<script type="text/javascript" src="/resources/js/mobile/cs/privacy/privacyView.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					이용자 피해예방 가이드
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-tabs c-tabs--type2">
				<div class="c-tabs__list c-expand sticky-header" data-tab-list="">
					<button class="c-tabs__button" data-tab-header="#tabA1-panel">피해예방 정보</button>
					<button class="c-tabs__button" data-tab-header="#tabA2-panel" id="tab2">피해예방 가이드</button>
				</div>
				<div class="c-tabs__panel" id="tabA1-panel">
					<div class="c-tabs c-tabs--type3" data-tab-active="0">
						<div class="c-tabs__list c-expand" data-tab-list="">
							<c:if test="${tabMenuList ne null and !empty tabMenuList}">
								<c:forEach items="${tabMenuList}" var="tabMenuList" varStatus="status">
									<button class="c-tabs__button midTab" data-tab-header="#tabB${status.count}-panel" cdGroupId="UI0031" dtlCd="${tabMenuList.dtlCd}">${tabMenuList.dtlCdNm}</button>
								</c:forEach>
							</c:if>
						</div>
						<div class="c-tabs__panel" id="tabB1-panel">

							<div class="c-board">
								<strong class="c-board__title">${boardDto.boardSubject}</strong>
								<span class="c-board__sub">
									<i class="c-icon c-icon--calendar" aria-hidden="true"></i>
									${boardDto.charDt}
								</span>
								<div class="c-board__content">
									<article class="c-editor">${boardDto.boardContents}</article>
								</div>
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
												<i class="c-icon c-icon--triangle-left" aria-hidden="true"></i>
												<span>이전글</span>
											</a>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="${notiDto.nextSeq ne 0 && notiDto.nextSeq ne null}">
											<a class="c-button" href="javascript:goNextDetail('${notiDto.nextSeq}')"> <span>다음글</span>
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
							</div>
							<div class="c-button-wrap">
								<a href="javascript:;" class="c-button c-button--full c-button--white" id="viewList">목록보기</a>
							</div>
							<input type="hidden" id="boardCtgSeq" name="boardCtgSeq" value="91">
							<input type="hidden" id="searchNm" name="searchNm" value="${searchDto.searchNm}" />
							<input type="hidden" id="pageNo" name="pageNo" value="${searchDto.pageNo}" />
							<input type="hidden" id="sbstCtg" name="sbstCtg" value="${searchDto.sbstCtg}" />
							<input type="hidden" id="boardSeq" name="boardSeq" value="${searchDto.boardSeq}">
						</div>
						<div class="c-tabs__panel" id="tabB2-panel"></div>
						<div class="c-tabs__panel" id="tabB3-panel"></div>
					</div>
				</div>
				<div class="c-tabs__panel" id="tabA2-panel"></div>
			</div>
		</div>



	</t:putAttribute>

</t:insertDefinition>

