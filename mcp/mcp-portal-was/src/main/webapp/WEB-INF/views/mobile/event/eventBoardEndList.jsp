<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mobile/event/eventBoardEndList.js"></script>
        <script type="text/javascript" src="../../resources/js/lodash.js"></script>
    </t:putAttribute>
<t:putAttribute name="contentAttr">
    <input type="hidden" name="serverName" id="serverName" value="${serverName}"/>
    <input type="hidden" name="tabSelect" id="tabSelect" value="${NmcpEventBoardBas.sbstCtg}"/>
    <input type="hidden" name="eventStatus" id="eventStatus" value="end"/>
	<input type="hidden" id="pageNo" name="pageNo"  value="${empty pageInfoBean.pageNo ? 1 :pageInfoBean.pageNo}" />
	<input type="hidden" id="initPageNo" name="initPageNo"  value="${pageInfoBean.pageNo}" />
	<input type="hidden" id="totalPageCount" name="totalPageCount" value="${pageInfoBean.totalPageCount}"/>
    <input type="hidden" id="totalCount" name="totalCount" value="${pageInfoBean.totalCount}"/>
	<input type="hidden" id="eventBranch" name="eventBranch" value="${eventBranch}"/>


    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <c:if test="${eventBranch eq 'E'}">
            <h2 class="ly-page__head">이번달 이벤트
        </c:if>
        <c:if test="${eventBranch eq 'J'}">
            <h2 class="ly-page__head">제휴 이벤트
        </c:if>
            <button class="header-clone__gnb"></button>
            </h2>
      </div>
      <div class="c-tabs c-tabs--type2" data-tab-active="1">
	   <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
	       <button class="c-tabs__button" data-tab-header="#tabA1-panel" id="tab1">진행중</button>
	       <button class="c-tabs__button" data-tab-header="#tabA3-panel" id="tab3">당첨자 확인</button>
	   </div>
	   <div class="c-tabs__panel" id="tabA1-panel"> </div>
	   <div class="c-tabs__panel" id="tabA2-panel">
	      <!-- 종료 이벤트 목록-->
	      <ul class="event-list" id="listArea2">

	      </ul>
	      <div class="c-button-wrap" id="more_viewDiv2" style="visibility: hidden;">
              <button id="more_view" class="c-button c-button--full" onclick="bntMormView()">더보기<span class="c-button__sub">
                  <span id="current_page2"></span>/<span id="total_page2"></span></span>
                  <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
              </button>
          </div>
	   </div>
	   <div class="c-tabs__panel" id="tabA3-panel"> </div>
	  </div>
      <div id="spaViewLayer" style="display: none"></div>
      </div>

</t:putAttribute>
</t:insertDefinition>
