<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<c:set var="jsver" value="${nmcp:getJsver()}" />

<t:insertDefinition name="layoutGnbDefault">

<t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/portal/event/winnerList.js?ver=${jsver}"></script>

    <script>

    function goView(ntcartSeq){
        var action = "/event/cprtEventWinnerDetail.do";
        if($('#eventBranch').val() == 'E'){
            action = "/event/winnerDetail.do";
        }

    	var url = action + "?ntcartSeq=" + ntcartSeq + "&pageNo=" + $("#pageNo").val() + "&eventBranch=" + $('#eventBranch').val();
    	location.href = url;
    };

    </script>
</t:putAttribute>

<t:putAttribute name="contentAttr">
    <input type="hidden" id="eventBranch" name="eventBranch" value="${eventBranch}"/>
    <input type="hidden" id="totalPageCount" name="totalPageCount"/>
    <input type="hidden" name="chkChoice" id="chkChoice"/>
    <input type="hidden" name="pageNo" id="pageNo" value="${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}"/>

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
          <button class="c-tabs__button is-active" id="tab3" role="tab" aria-selected="true">당첨자 확인</button>
        </div>
      </div>
      <div class="c-tabs__panel u-block" id="ajax_tab_panel" tabindex="0">
        <h3 class="c-hidden" id="content_heading">당첨자 확인</h3>
          <div class="c-row c-row--lg">
            <div class="c-table c-table--list">
              <table>
                <caption>번호, 제목, 이벤트기간, 발표일을 포함한 당첨자 확인 목록</caption><!-- [2022-01-12] col width값 수정-->
                <colgroup>
                  <col style="width: 10%">
                  <col>
                  <col style="width: 22%">
                  <col style="width: 13%">
                </colgroup>
                <thead>
                  <tr>
                    <th scope="col">번호</th>
                    <th scope="col">제목</th>
                    <th scope="col">이벤트 기간</th>
                    <th scope="col">발표일</th>
                  </tr>
                </thead>
                <!-- 당첨자 확인 목록-->
                <tbody id="listArea">

                </tbody>
              </table>
            </div><!-- 페이징 영역-->
            <div id="paging"></div>
          </div>
      </div>
    </div>
</t:putAttribute>

</t:insertDefinition>