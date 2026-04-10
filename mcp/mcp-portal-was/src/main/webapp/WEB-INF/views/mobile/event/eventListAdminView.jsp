<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="titleAttr">
        <c:if test="${eventBranch eq 'E'}">
            이번달 이벤트
        </c:if>
        <c:if test="${eventBranch eq 'J'}">
            제휴 이벤트
        </c:if>
    </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mobile/event/eventListAdminView.js?ver=${jsver}"></script>
        <script type="text/javascript" src="../../resources/js/lodash.js"></script>

    </t:putAttribute>
<t:putAttribute name="contentAttr">
    <input type="hidden" name="serverName" id="serverName" value="${serverName}"/>
    <input type="hidden" name="tabSelect" id="tabSelect" value="${NmcpEventBoardBas.sbstCtg}"/>
    <input type="hidden" name="eventStatus" id="eventStatus" value="ing"/>
    <input type="hidden" id="pageNo" name="pageNo"  value="${empty pageInfoBean.pageNo ? 1 :pageInfoBean.pageNo}" />
    <input type="hidden" id="initPageNo" name="initPageNo"  value="${pageInfoBean.pageNo}" />
    <input type="hidden" id="totalPageCount" name="totalPageCount"/>
    <input type="hidden" id="adminEventDate" name="adminEventDate" value="${adminEventDate}"/>
    <input type="hidden" id="eventBranch" name="eventBranch" value="${eventBranch}"/>
    <input type="hidden" id=category name="category" value="${category}"/>

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
        <div id="contentLayer">
           <div class="c-tabs c-tabs--type2">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                  <button class="c-tabs__button" data-tab-header="#tabA1-panel" id="tab1">진행중 이벤트</button>
                </div>
                <div class="c-tabs__panel" id="tabA1-panel">
                    <!-- 진행중 이벤트 목록-->
                    <div class="event-filter">
                      <div class="c-filter c-filter--accordion c-expand">
                        <button class="c-filter--accordion__button">
                            <div class="c-hidden">필터 펼치기</div>
                        </button>
                        <div class="c-filter__inner" id="mainTab">
                           <button class="c-button c-button--sm c-button--white is-active" id="categroyAll" onclick="mainTabAllClick();">전체</button>
                           <c:forEach items="${eventCategoryList}" var="item">
                               <c:if test="${item.expnsnStrVal1 eq 'Y'}">
                                   <button class="c-button c-button--sm c-button--white" onclick="mainTabClick(this);" eventCategory="${item.dtlCd}">${item.dtlCdNm}</button>
                               </c:if>
                           </c:forEach>
                        </div>
                    </div>
                  </div>
                  <ul class="event-list" id="listArea1">

                  </ul>
                </div>
                <div class="c-tabs__panel" id="tabA3-panel"> </div>
          </div>
      </div>
      <div id="spaViewLayer" style="display: none"></div>
      </div>

</t:putAttribute>
</t:insertDefinition>
