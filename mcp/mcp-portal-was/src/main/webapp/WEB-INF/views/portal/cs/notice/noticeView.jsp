<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">공지사항</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/cs/notice/noticeView.js"></script>
        <script type="text/javascript">
            var pageNo = $("#pageNo").val();
            var searchNm = $("#searchNm").val().trim();

            var state = { 'pageNo': pageNo,'searchNm':searchNm};
            history.pushState(state, null,"/cs/csNoticeList.do");
        </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

<input type="hidden" id="pageNo" name="pageNo" value="${boardDto.pageNo}">
<input type="hidden" id="searchNm" name="searchNm" value="${boardDto.searchNm}" />
<input type="hidden" id="boardSeq" name="boardSeq" value="${boardDto.boardSeq}">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">공지사항</h2>
            </div>
            <div class="c-row c-row--lg">
                <div class="c-board">
                    <div class="c-board__head">
                        <strong class="c-board__title">
                            ${viewResult.boardSubject}
                        </strong>
                        <span class="c-board__sub">
                            <span class="c-hidden">등록일</span>${viewResult.boardWriteDt}
                        </span>
                    </div>
                    <div class="c-board__content">
                        <article class="c-editor">
                            ${viewResult.boardContents}
                        </article>
                    </div>

                    <c:if test="${viewResult.notiYn ne 'Y'}">
                        <div class="c-board__nav">
                            <c:choose>
                                <c:when test="${notiDto.preSeq ne 0 && notiDto.preSeq ne null}">
                                    <a class="c-button" href="javascript:goNextDetail('${notiDto.preSeq}')">
                                        <i class="c-icon c-icon--triangle-left" aria-hidden="true"></i>
                                        <span>이전</span>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a class="c-button is-disabled" href="javascript:;">
                                        <i class="c-icon c-icon--triangle-left" aria-hidden="true"></i>
                                        <span>이전</span>
                                    </a>
                                </c:otherwise>
                            </c:choose>

                            <c:choose>
                                <c:when test="${notiDto.nextSeq ne 0 && notiDto.nextSeq ne null}">
                                    <a class="c-button" href="javascript:goNextDetail('${notiDto.nextSeq}')">
                                        <span>다음</span>
                                        <i class="c-icon c-icon--triangle-right" aria-hidden="true"></i>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a class="c-button is-disabled" href="javascript:;">
                                        <span>다음</span>
                                        <i class="c-icon c-icon--triangle-right" aria-hidden="true"></i>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:if>
                </div>

                <div class="c-button-wrap u-mt--56">
                    <a class="c-button c-button--lg c-button--white c-button--w460" href="javascript:;" id="viewList">목록보기</a>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>
