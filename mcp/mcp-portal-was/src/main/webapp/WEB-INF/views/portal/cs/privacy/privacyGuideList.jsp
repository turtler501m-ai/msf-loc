<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">이용자 피해예방 가이드</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/cs/privacy/privacyGuideList.js?ver=${jsver}"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

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
                        <a class="c-tabs__button" id="tab1" href="/cs/privacyBoardList.do" role="tab" aria-selected="false">피해예방 정보</a>
                        <a class="c-tabs__button is-active" id="tab2" href="/cs/privacyGuideList.do" role="tab" aria-selected="true">피해예방 가이드</a>
                    </div>
                </div>
                <div class="c-tabs__panel u-block" >
                    <div class="c-tabs c-tabs--type2">
                        <div class="c-tabs__list" role="tablist">
                            <c:if test="${tabMenuList ne null and !empty tabMenuList}">
                                <c:forEach items="${tabMenuList}" var="tabMenuList" varStatus="status">
                                    <button class="c-tabs__button midTab" id="tabE${status.count}" role="tab" aria-controls="tabF${status.count}panel" aria-selected="false" tabindex="-1" dtlCd="${tabMenuList.dtlCd}">${tabMenuList.dtlCdNm}</button>
                                </c:forEach>
                            </c:if>
                            <!-- <button class="c-tabs__button" id="tab11" role="tab" aria-controls="tabD11panel" aria-selected="false" tabindex="-1">동영상으로 보기</button>
                            <button class="c-tabs__button" id="tab22" role="tab" aria-controls="tabD22panel" aria-selected="false" tabindex="-1">웹툰으로 보기</button>
                            <button class="c-tabs__button" id="tab33" role="tab" aria-controls="tabD33panel" aria-selected="false" tabindex="-1">피해예방교육</button> -->
                        </div>
                    </div>
                    <div class="c-tabs__panel" id="tabF1panel" role="tabpanel" aria-labelledby="tabE1" tabindex="-1"></div>
                    <div class="c-tabs__panel" id="tabF2panel" role="tabpanel" aria-labelledby="tabE2" tabindex="-1"></div>
                    <div class="c-tabs__panel" id="tabF3panel" role="tabpanel" aria-labelledby="tabE3" tabindex="-1"></div>
                    <div class="c-tabs__panel" id="tabF4panel" role="tabpanel" aria-labelledby="tabE4" tabindex="-1"></div>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>
