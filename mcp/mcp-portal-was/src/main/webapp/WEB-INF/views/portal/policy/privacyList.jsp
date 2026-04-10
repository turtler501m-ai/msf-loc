<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="jsver" value="${nmcp:getJsver()}" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">정책 및 약관 </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
       <script type="text/javascript" src="/resources/js/portal/policy/privacyList.js?ver=${jsver}"></script>
    <script>
    $(document).ready(function(){
        setTimeout(function(){
            $('#subbody_loading').hide();
        }, 200);
    });
    </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <input type="hidden" id="pageNo" name="pageNo" value="${pageInfoBean.pageNo}">

        <div id="subbody_loading" style="display:flex; width: 100%; height: calc(100vh - 130px); box-sizing: border-box; background: rgb(255, 255, 255); text-align: center; position: relative; align-items: center; justify-content: center; z-index: 10;">
            <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
        </div>

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">정책 및 약관</h2>
            </div>
            <div class="c-row c-row--xlg">
                <div class="c-tabs c-tabs--type2 col-4 c-expand u-mt--m56" data-ui-tab>
                    <div class="c-tabs__list" role="tablist">
                        <button class="c-tabs__button" id="tab1" role="tab" aria-controls="tabD1panel" aria-selected="false" tabindex="-1" onclick="location.href = '/policy/privacyList.do'">개인정보 처리방침</button>
                        <button class="c-tabs__button" id="tab2" role="tab" aria-controls="tabD2panel" aria-selected="false" tabindex="-1" onclick="location.href = '/policy/ktPolicy.do'">이용약관</button>
                        <button class="c-tabs__button" id="tab3" role="tab" aria-controls="tabD3panel" aria-selected="false" tabindex="-1" onclick="location.href = '/policy/policyGuideView.do'" >이용약관 주요설명</button>
                        <button class="c-tabs__button" id="tab4" role="tab" aria-controls="tabD4panel" aria-selected="false" tabindex="-1" onclick="location.href = '/policy/youngList.do'" >청소년 보호 정책</button>
                    </div>
                </div>
                <div class="c-tabs__panel u-block" id="tabD1panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
                    <div class="c-row c-row--lg">
                        <div class="c-table c-table--list">
                            <table>
                                <caption>번호, 제목, 등록일을 포함한 개인정보 처리방침</caption>
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
                                    <c:when test="${policyDtoList ne null and !empty policyDtoList}">
                                        <c:forEach var="policyDtoList" items="${policyDtoList}">
                                            <tr>
                                                <td>${policyDtoList.rowNum}</td>
                                                <td>
                                                    <a href='javascript:goDetail("${policyDtoList.stpltSeq}")' >
                                                        <strong class="title c-text-ellipsis">${policyDtoList.stpltTitle}</strong>
                                                    </a>
                                                </td>
                                                <td>${policyDtoList.cretDt}</td>
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
                <div class="c-tabs__panel" id="tabD2panel" role="tabpanel" aria-labelledby="tab2" tabindex="-1"></div>
                <div class="c-tabs__panel" id="tabD3panel" role="tabpanel" aria-labelledby="tab3" tabindex="-1"></div>
                <div class="c-tabs__panel" id="tabD4panel" role="tabpanel" aria-labelledby="tab4" tabindex="-1"></div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>
