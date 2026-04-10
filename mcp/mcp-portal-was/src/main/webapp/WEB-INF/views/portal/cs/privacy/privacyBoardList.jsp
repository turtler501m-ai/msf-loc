<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">이용자 피해예방 가이드 </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/cs/privacy/privacyBoardList.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <input type="hidden" id="pageNo" name="pageNo"	value="${pageInfoBean.pageNo}">
        <input type="hidden" id="sbstCtg" name="sbstCtg" value="${boardDto.sbstCtg}">
        <input type="hidden" id="tabIndex" name="tabIndex" value="${tabIndex}">

        <div class="ly-loading" id="subbody_loading">
            <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
        </div>

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">이용자 피해예방 가이드</h2>
            </div>
            <div class="c-row c-row--xlg">
                <div class="c-tabs c-tabs--type1">
                    <div class="c-tabs__list">
                        <a class="c-tabs__button is-active" href="/cs/privacyBoardList.do" role="tab" aria-selected="true">피해예방 정보</a>
                        <a class="c-tabs__button" href="/cs/privacyGuideList.do" role="tab" aria-selected="false">피해예방 가이드</a>
                    </div>
                </div>
                <div class="c-tabs__panel u-block">
                    <div class="c-tabs c-tabs--type2 c-expand">
                        <div class="c-tabs__list" role="tablist">
                            <c:if test="${tabMenuList ne null and !empty tabMenuList}">
                                <c:forEach items="${tabMenuList}" var="tabMenuList" varStatus="status">
                                    <button class="c-tabs__button midTab" id="tabE${status.count}" role="tab" aria-controls="tabF${status.count}panel" aria-selected="false" tabindex="-1" dtlCd="${tabMenuList.dtlCd}">${tabMenuList.dtlCdNm}</button>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                    <div class="c-tabs__panel" id="tabF1panel" role="tabpanel" aria-labelledby="tabE1" tabindex="-1">
                        <div class="c-row c-row--lg">
                            <div class="c-form c-form--search u-mt--64 u-mb--24">
                                <div class="c-form__input">
                                    <input class="c-input" placeholder="검색어를 입력해주세요" id="searchNm" title="검색어 입력" value="${boardDto.searchNm}" onkeypress="if(event.keyCode==13) {searchBtn();}" maxlength="20">
                                    <label class="c-form__label c-hidden" for="searchNm">검색어 입력</label>
                                    <button class="c-button c-button--reset">
                                        <span class="c-hidden">초기화</span>
                                        <i class="c-icon c-icon--reset" aria-hidden="true"></i>
                                    </button>
                                    <button class="c-button c-button-round--sm c-button--primary" onclick="searchBtn();">검색</button>
                                </div>
                            </div>

                            <div class="c-table c-table--list">
                                <table>
                                    <caption>번호, 제목, 등록일을 포함한 공지사항</caption>
                                    <colgroup>
                                        <col style="width: 98px">
                                        <col>
                                        <col style="width: 116px">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th scope="col">번호</th>
                                        <th scope="col">제목</th>
                                        <th scope="col">등록일</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:choose>
                                        <c:when test="${privacyBoardList ne null and !empty privacyBoardList}">
                                            <c:forEach var="privacyBoardList" items="${privacyBoardList}">
                                                <tr>
                                                    <td>${privacyBoardList.rnum}</td>
                                                    <td>
                                                        <a href='javascript:goDetail("${privacyBoardList.boardSeq}")' >
                                                            <strong class="title c-text-ellipsis">${privacyBoardList.boardSubject}</strong>
                                                        </a>
                                                    </td>
                                                    <td>${privacyBoardList.charDt}</td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="3">
                                                    <p class="nodata">검색결과가 없습니다.</p>
                                                </td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                    </tbody>
                                </table>
                            </div>
                            <c:if test="${totalCount > 10}">
                                <nav class="c-paging" aria-label="검색결과">
                                    <nmcp:pageInfo pageInfoBean="${pageInfoBean}" function="goPaging" />
                                </nav>
                            </c:if>
                        </div>
                    </div>
                    <div class="c-tabs__panel" id="tabF2panel" role="tabpanel" aria-labelledby="tabE2" tabindex="-1"></div>
                    <div class="c-tabs__panel" id="tabF3panel" role="tabpanel" aria-labelledby="tabE3" tabindex="-1"></div>
                    <div class="c-tabs__panel" id="tabF4panel" role="tabpanel" aria-labelledby="tabE4" tabindex="-1"></div>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>
