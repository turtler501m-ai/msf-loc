<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<c:set var="jsver" value="${nmcp:getJsver()}" />

<t:insertDefinition name="layoutGnbDefault">
    <%-- <t:putAttribute name="scriptMetaAttr">
        <meta name="description" content="전국 홈플러스 & Express에서 유심 구매하고 2만 포인트 받자!" />
        <meta name="keywords" content="전국 홈플러스 & Express에서 유심 구매하고 2만 포인트 받자!" />
    </t:putAttribute> --%>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/event/eventBoardEndList.js?ver=${jsver}"></script>
        <script type="text/javascript" src="/resources/js/lodash.js?ver=${jsver}"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <!-- 이벤트 시작 -->
    <input type="hidden" name="sbstCtg" id="sbstCtg" value="${NmcpEventBoardBas.sbstCtg}"/>
    <input type="hidden" name="ntcartSbst" id="ntcartSbst" value="${NmcpEventBoardBas.ntcartSbst}"/>
    <input type="hidden" name="eventStatus" id="eventStatus" value="end"/>
    <input type="hidden" name="pageNo" id="pageNo" value="${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}"/>
    <input type="hidden" id="totalPageCount" name="totalPageCount"/>
    <input type="hidden" id="eventBranch" name="eventBranch" value="${eventBranch}"/>

	<div class="ly-loading" id="subbody_loading">
		<img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
	</div>

    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <c:if test="${eventBranch eq 'E'}">
            <h2 class="c-page--title">이번달 이벤트</h2>
        </c:if>
        <c:if test="${eventBranch eq 'J'}">
            <h2 class="c-page--title">제휴 이벤트</h2>
        </c:if>
      </div>
      <div class="c-tabs c-tabs--type1">
        <div class="c-tabs__list" id="tab_ex01" role="tablist">
          <button class="c-tabs__button" id="tab1" role="tab" aria-selected="false">진행중 이벤트</button>
          <button class="c-tabs__button" id="tab3" role="tab" aria-selected="false">당첨자 확인</button>
        </div>
      </div>
      <div class="c-tabs__panel u-block" id="ajax_tab_panel" tabindex="0">
          <h3 class="c-hidden" id="content_heading">종료된 이벤트</h3>
          <div class="tab-inner" id="noDataEnd">
            <!-- 종료 이벤트 목록-->
            <ul class="event-list" id="listArea2">

            </ul>
            <!-- 페이징 영역-->
            <nav id="paging">
            </nav>
          </div>
      </div>
    </div>

<!-- 이벤트 종료 -->
</t:putAttribute>

</t:insertDefinition>