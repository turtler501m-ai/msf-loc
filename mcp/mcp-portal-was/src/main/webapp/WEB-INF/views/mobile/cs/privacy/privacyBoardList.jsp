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
        <script type="text/javascript" src="/resources/js/mobile/cs/privacy/privacyBoardList.js"></script>

    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <input type="hidden" id="tabIndex" name="tabIndex" value="${tabIndex}">

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
                                    <button class="c-tabs__button midTab" data-active-scroll id="tabE${status.count}" data-tab-header="#tabB${status.count}-panel" dtlCd="${tabMenuList.dtlCd}">${tabMenuList.dtlCdNm}</button>
                                </c:forEach>
                            </c:if>
                            <!-- <button class="c-tabs__button" data-tab-header="#tabB2-panel">최신피해예방정보</button>
                            <button class="c-tabs__button" data-tab-header="#tabB3-panel">피해사례별 예방법</button> -->

                        </div>
                        <div class="c-tabs__panel" id="tabB1-panel">
                            <input type="hidden" id="pageNo" name="pageNo" value="${pageInfoBean.pageNo}">
                            <input type="hidden" id="sbstCtg" name="sbstCtg" value="${boardDto.sbstCtg}">
                            <input type="hidden" id="totalCount" name="totalCount" value="${totalCount}">
                            <input type="hidden" id="boardSeq" name="boardSeq" value="${boardDto.boardSeq}">


                            <div class="c-form c-form--search">
                                <div class="c-form__input">
                                    <input class="c-input" type="text" placeholder="검색어를 입력해주세요" id="searchNm" title="검색어 입력" value="${boardDto.searchNm}" onkeypress="if(event.keyCode==13) {searchBtn(); return false;}" maxlength="20">
                                    <button class="c-button c-button--search-bk" onclick="searchBtn(); return false;">
                                        <span class="c-hidden">검색어</span>
                                        <i class="c-icon c-icon--search-bk" aria-hidden="true"></i>
                                    </button>
                                </div>
                            </div>
                            <c:choose>
                                <c:when test="${privacyBoardList ne null and !empty privacyBoardList}">
                                    <ul class="c-list c-list--type1">
                                        <c:forEach var="privacyBoardList" items="${privacyBoardList}" varStatus="status">
                                            <c:choose>
                                                <c:when test="${pageInfoBean.pageNo*10 >= status.count}"><li class="c-list__item list" boardSeq="${privacyBoardList.boardSeq}"></c:when>
                                                <c:otherwise><li class="c-list__item list" style="display:none;" boardSeq="${privacyBoardList.boardSeq}"></c:otherwise>
                                            </c:choose>
                                                <a class="c-list__anchor" href="javascript:goDetail('${privacyBoardList.boardSeq}')">
                                                    <strong class="c-list__title c-text-ellipsis">
                                                        <c:if test="${privacyBoardList.notiYn eq 'Y'}"><span class="u-co-red">[공지]</span></c:if>
                                                        ${privacyBoardList.boardSubject}
                                                    </strong>
                                                    <span class="c-list__sub">${privacyBoardList.charDt}</span>
                                                </a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <c:if test="${totalCount > 10}">
                                        <div class="c-button-wrap">
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
                        <div class="c-tabs__panel" id="tabB2-panel"></div>
                        <div class="c-tabs__panel" id="tabB3-panel"></div>
                        <div class="c-tabs__panel" id="tabB4-panel"></div>
                    </div>
                </div>
                <div class="c-tabs__panel" id="tabA2-panel"></div>
            </div>
        </div>


    </t:putAttribute>




</t:insertDefinition>

