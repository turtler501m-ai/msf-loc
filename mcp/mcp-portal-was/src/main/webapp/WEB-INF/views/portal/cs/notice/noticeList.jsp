<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">공지사항</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/cs/notice/noticeList.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">공지사항</h2>
            </div>
            <div class="c-row c-row--lg">

                <div class="c-form c-form--search u-mt--0 u-ta-center">
                    <div class="c-form__input">
                        <input class="c-input" type="text" placeholder="검색어를 입력해주세요" id="searchNm" name="searchNm" value="${boardDto.searchNm}" onkeypress="if( event.keyCode==13 ){searchBtn();}" maxlength="20">
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
                            <c:if test="${noticeList ne null and !empty noticeList}">
                                <c:forEach var="noticeList" items="${noticeList}">
                                    <tr>
                                        <td><span class="u-co-red">[공지]</span></td>
                                        <td>
                                            <a href='javascript:goDetail("${noticeList.boardSeq}")' > <strong class="title c-text-ellipsis">${noticeList.boardSubject}</strong></a>
                                        </td>
                                        <td>${noticeList.boardWriteDt}</td>
                                    </tr>
                                </c:forEach>
                            </c:if>

                            <c:choose>
                                <c:when test="${resultList ne null and !empty resultList}">
                                    <c:forEach var="resultList" items="${resultList}">
                                        <tr>
                                            <td>${resultList.boardNum}</td>
                                            <td>
                                                <a href='javascript:goDetail("${resultList.boardSeq}")' >
                                                    <strong class="title c-text-ellipsis">${resultList.boardSubject}</strong>
                                                </a>
                                            </td>
                                            <td>${resultList.boardWriteDt}</td>
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

<input type="hidden" id="pageNo" name="pageNo" value="${pageInfoBean.pageNo}">


    </t:putAttribute>
</t:insertDefinition>
