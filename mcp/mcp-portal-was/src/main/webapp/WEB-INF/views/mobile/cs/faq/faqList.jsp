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
		<script type="text/javascript" src="/resources/js/mobile/cs/faq/faqList.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

	<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}" />

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					자주묻는 질문<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<!-- 검색영역 -->
			<div class="c-form c-form--search">
				<div class="c-form__input">
					<input class="c-input" id="searchNm" name="searchNm" type="text" placeholder="검색어를 입력해주세요" title="검색어 입력" value="${boardDto.searchNm}" onkeypress="if(event.keyCode==13) {searchBtn(); return false;}" maxlength="20">
					<button class="c-button c-button--search-bk" onclick="searchBtn(); return false;">
						<span class="c-hidden">검색어</span>
						<i class="c-icon c-icon--search-bk" aria-hidden="true"></i>
					</button>
				</div>
			</div>

			<!-- 카테고리영역 -->
			<div class="c-filter c-filter--accordion c-expand">
				<button class="c-filter--accordion__button">
					<div class="c-hidden">필터 펼치기</div>
				</button>
				<div class="c-filter__inner">
					<c:set var="codeList" value="${nmcp:getCodeList('FAQB')}" />
					<button class="c-button c-button--sm c-button--white ${boardDto.sbstCtg eq '00'?'is-active':''} selMenu" sbstCtg="00">전체</button>
					<button class="c-button c-button--sm c-button--white ${boardDto.sbstCtg eq '100'?'is-active':''} selMenu" sbstCtg="100">TOP10</button>
					<c:forEach items="${codeList}" var="list">
						<button class="c-button c-button--sm c-button--white ${boardDto.sbstCtg eq list.dtlCd?'is-active':''} selMenu" sbstCtg="${list.dtlCd}">${list.dtlCdNm}</button>
					</c:forEach>
				</div>
			</div>

			<h3 class='c-heading c-heading--type3' id="title"></h3>
			<c:if test="${faqTopList ne null and !empty faqTopList}">
				<div class='c-accordion c-accordion--type1 faq-accordion'>
					<ul class="c-accordion__box auto-numbering">
						<c:forEach var="faqTopList" items="${faqTopList}">
							<li class="c-accordion__item top10" >
					            <div class="c-accordion__head" data-acc-header>
					              <button class="c-accordion__button faq" type="button" aria-expanded="false" boardSeq="${faqTopList.boardSeq}">
					                <div class="c-accordion__title">${faqTopList.boardSubject}
					                  <div class="info-line">
					                    <span class="info-item">
					                      <span>조회</span>
					                      <span class="with-div faqTopCtn">${faqTopList.boardHitCnt}</span>
					                    </span>
					                  </div>
					                </div>
					              </button>
					            </div>
					            <div class="c-accordion__panel c-expand expand">
					              <div class="c-accordion__inside box-answer">
					                <div class="box-prefix">A</div>
					                <div class="box-content">${faqTopList.boardContents}</div>
					              </div>
					            </div>
					          </li>
						</c:forEach>
					</ul>
				</div>
			</c:if>

			<div id="dataArea">
				<div class='c-accordion c-accordion--type1 faq-accordion' id="accordion_runtime_update">
					<ul class='c-accordion__box' id="liDataArea">

					</ul>
				</div>
				<div class="c-button-wrap" style="display:none;" id="addBtnArea">
					<button class="c-button c-button--full" id="moreBtn">
						더보기 <span class="c-button__sub" id="pageNav"><span id="listCount"></span>/<span id="totalCount"></span></span>
						<i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
					</button>
				</div>
			</div>

			<div class="c-nodata" style="display:none;">
				<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
				<p class="c-noadat__text">일치하는 검색 결과가 없습니다.</p>
			</div>
		</div>

	</t:putAttribute>
</t:insertDefinition>