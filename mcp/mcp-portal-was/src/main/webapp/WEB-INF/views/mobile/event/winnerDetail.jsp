<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <!--
        <script>
            function goList(){
            	$("#mWinnerDetail").submit();
            }
    	</script>
    	<script type="text/javascript"  src="${pageContext.request.contextPath}/resources/js/mobile/event/winnerDetail.js"></script>
    	 -->
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
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
      </div><!-- [2021-11-19] 탭활성화 파라미터 업데이트 -->
      <div class="c-tabs c-tabs--type2" data-tab-active="1">
        <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
          <button class="c-tabs__button" data-tab-header="#tabA1-panel" id="tab1">진행중</button>
          <button class="c-tabs__button" data-tab-header="#tabA3-panel" id="tab3">당첨자 확인</button>
        </div>
        <div class="c-tabs__panel" id="tabA1-panel"> </div>
        <div class="c-tabs__panel" id="tabA2-panel"> </div>
        <div class="c-tabs__panel" id="tabA3-panel">
          <!-- 진행중 이벤트 상세-->
          <div class="c-board c-board--type2">
            <div class="c-board__item">
              <strong class="c-board__title"> ${winnerDto.pwnrSubject}</strong>
              <div class="c-board__sub">
                <span>${winnerDto.eventStartDt} ~ ${winnerDto.eventEndDt}</span>
                <span class="tit">발표</span>
                <span><fmt:formatDate value="${winnerDto.cretDt}" pattern="yyyy.MM.dd"/></span>
              </div>
            </div>
            <div class="c-board__content">
              <article class="c-editor">
                ${winnerDto.pwnrSbst}
              </article>
            </div>
          </div>
        </div>
        <div class="c-button-wrap">
          <c:if test="${eventBranch eq 'E'}">
            <a href="javascript:;" class="c-button c-button--full c-button--white" onclick="showList('/m/event/winnerList.do');">목록보기</a>
          </c:if>
          <c:if test="${eventBranch eq 'J'}">
            <a href="javascript:;" class="c-button c-button--full c-button--white" onclick="showList('/m/event/cprtEventWinnerList.do');">목록보기</a>
          </c:if>
        </div>
      </div>
    </div>

    </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script>
            $(document).ready(function(){
                $("#tab1").click(function() {
                    if($('#eventBranch').val() == 'E'){
                        goWinView("/m/event/eventList.do");
                    }else{
                        goWinView("/m/event/cprtEventBoardList.do");
                    }
                });

                $("#tab2").click(function() {
                    if($('#eventBranch').val() == 'E'){
                        goWinView("/m/event/eventBoardEndList.do");
                    }else{
                        goWinView("/m/event/cprtEventBoardEndList.do");
                    }
                });

                $("#tab3").click(function() {
                	if($('#eventBranch').val() == 'E'){
                        goWinView("/m/event/winnerList.do");
                    }else{
                        goWinView("/m/event/cprtEventWinnerList.do");
                    }
                });
            });

            function goWinView(url) {
                ajaxCommon.createForm({
                        method:"post"
                        ,action: url
                });
                ajaxCommon.formSubmit();
            };
        </script>
    </t:putAttribute>
</t:insertDefinition>
