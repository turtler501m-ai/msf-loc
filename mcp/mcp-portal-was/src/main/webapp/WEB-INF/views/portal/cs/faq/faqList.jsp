<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">자주묻는 질문</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/cs/faq/faqList.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">자주묻는 질문</h2>
            </div>
            <div class="c-row c-row--lg">
                <div class="c-form c-form--search u-mt--0 u-ta-center">
                    <div class="c-form__input">
                        <input class="c-input" type="text" placeholder="검색어를 입력해주세요" id="searchNm" name="searchNm" value="${boardDto.searchNm}" onkeypress="if(event.keyCode==13) {searchBtn();}" maxlength="20">
                        <label class="c-form__label c-hidden" for="searchNm">검색어 입력</label>
                        <button class="c-button c-button--reset">
                            <span class="c-hidden">초기화</span>
                            <i class="c-icon c-icon--reset" aria-hidden="true"></i>
                        </button>
                        <button class="c-button c-button-round--sm c-button--primary" onclick="searchBtn();">검색</button>
                    </div>
                </div>
                <div class="c-filter">
                    <div class="c-filter__inner">
                        <button class="c-button c-button-round--sm c-button--white ${boardDto.sbstCtg eq '00'?'is-active':''} selMenu" sbstCtg="00">전체</button>
                        <button	class="c-button c-button-round--sm c-button--white ${boardDto.sbstCtg eq '100'?'is-active':''} selMenu" sbstCtg="100">TOP10</button>
                        <c:set var="codeList" value="${nmcp:getCodeList('FAQB')}" />
                        <c:if test="${codeList ne null and !empty codeList}">
                            <c:forEach items="${codeList}" var="list">
                                <button class="c-button c-button-round--sm c-button--white ${boardDto.sbstCtg eq list.dtlCd?'is-active':''} selMenu" sbstCtg="${list.dtlCd}">${list.dtlCdNm}</button>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>

                <c:choose>
                    <c:when test="${(faqTopList ne null and !empty faqTopList) or resultList ne null and !empty resultList}">
                        <div class="c-accordion c-accordion--type2 u-mt--40" data-ui-accordion>
                            <ul class="c-accordion__box">
                                <c:if test="${faqTopList ne null and !empty faqTopList and pageInfoBean.pageNo eq 1}">
                                    <c:forEach var="faqTopList" items="${faqTopList}" varStatus="status">
                                        <li class="c-accordion__item">
                                            <div class="c-accordion__head">
                                                <c:choose>
                                                    <c:when test="${status.count<10}"><span class="question-label">0${status.count}<span class="c-hidden">질문</span></span></c:when>
                                                    <c:otherwise><span class="question-label">${status.count}<span class="c-hidden">질문</span></span></c:otherwise>
                                                </c:choose>
                                                <strong class="c-accordion__title">${faqTopList.boardSubject}</strong>
                                                <div class="c-accordion__info">
                                                    <span>조회</span> <span class="faqTopCtn">${faqTopList.boardHitCnt}</span>
                                                </div>
                                                <button class="acc_headerA${status.count} runtime-data-insert c-accordion__button faq" type="button" aria-expanded="false" aria-controls="acc_contentA${status.count}" boardSeq="${faqTopList.boardSeq}">
                                                    <span class="c-hidden">${faqTopList.boardSubject}</span>
                                                </button>
                                            </div>
                                            <div class="c-accordion__panel expand" id="acc_contentA${status.count}" aria-labelledby="acc_headerA${status.count}">
                                                <div class="c-accordion__inside">
                                                    <span class="box-prefix">A</span>
                                                    <div class="box-content">${faqTopList.boardContents}</div>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${resultList ne null and !empty resultList}">
                                    <c:forEach var="resultList" items="${resultList}" varStatus="status">
                                        <li class="c-accordion__item">
                                            <div class="c-accordion__head">
                                                <span class="question-label">Q<span class="c-hidden">질문</span></span>
                                                <strong class="c-accordion__title">${resultList.boardSubject}</strong>
                                                <div class="c-accordion__info">
                                                    <span>조회</span> <span>${resultList.boardHitCnt}</span>
                                                </div>
                                                <button class="acc_headerB${status.count} runtime-data-insert c-accordion__button faq" type="button" aria-expanded="false" aria-controls="acc_contentB${status.count}" boardSeq="${resultList.boardSeq}">
                                                    <span class="c-hidden">${resultList.boardSubject}</span>
                                                </button>
                                            </div>
                                            <div class="c-accordion__panel expand" id="acc_contentB${status.count}" aria-labelledby="acc_headerB${status.count}">
                                                <div class="c-accordion__inside">
                                                    <span class="box-prefix">A</span>
                                                    <div class="box-content">${resultList.boardContents}</div>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </c:if>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise><p class="nodata--type2">검색결과가 없습니다.</p></c:otherwise>
                </c:choose>
                <c:if test="${totalCount > 10}">
                    <nav class="c-paging" aria-label="검색결과">
                        <nmcp:pageInfo pageInfoBean="${pageInfoBean}" function="goPaging" />
                    </nav>
                </c:if>
            </div>

            <div class="ly-content--wrap u-mt--64 u-mb--64 u-bk--sky-blue">
                <div class="c-row c-row--lg">
                    <img src="/resources/images/portal/content/img_customer_banner.png" alt="찾으시는 결과가 없다면? - HELP 버튼을 선택해 챗봇과 1:1 상담문의 서비스를 이용해보세요!">
                </div>
            </div>
        </div>

<input type="hidden" id="pageNo" name="pageNo" value="${pageInfoBean.pageNo}">
    </t:putAttribute>
</t:insertDefinition>
