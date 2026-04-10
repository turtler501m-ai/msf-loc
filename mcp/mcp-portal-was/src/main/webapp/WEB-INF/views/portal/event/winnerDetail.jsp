<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" var="toDay" pattern="yyyy.MM.dd" />
<c:set var="imgpath" value="${pageContext.request.contextPath}/resources/images"/>
<t:insertDefinition name="layoutGnbDefault">
<t:putAttribute name="contentAttr">
<input type="hidden" id="redirectUrl" name="redirectUrl" value="${requestScope['javax.servlet.forward.request_uri']}?ntcartSeq=${param.ntcartSeq}&pwnrSeq=${param.pwnrSeq}&pageNo=${param.pageNo}&searchValue=${param.searchValue}">
<input type="hidden" name="pwnrSeq" id="pwnrSeq" value="${searchDto.pwnrSeq}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}"/>
<input type="hidden" name="searchValue" id="searchValue" value="${searchDto.searchValue}"/>
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
      <div class="c-tabs c-tabs--type1" data-ui-tab data-tab-active="1">
        <div class="c-tabs__list" role="tablist">
          <button class="c-tabs__button" id="tab1" role="tab" aria-controls="tabB1panel" aria-selected="false" tabindex="-1">진행중 이벤트</button>
          <button class="c-tabs__button" id="tab3" role="tab" aria-controls="tabB3panel" aria-selected="false" tabindex="-1">당첨자 확인</button>
        </div>
      </div>
      <div class="c-tabs__panel" id="tabB1panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1"></div>
      <div class="c-tabs__panel" id="tabB2panel" role="tabpanel" aria-labelledby="tab2" tabindex="-1"></div>
      <div class="c-tabs__panel" id="tabB3panel" role="tabpanel" aria-labelledby="tab3" tabindex="-1">
        <div>
          <div class="c-row c-row--lg">
            <div class="c-board">
              <div class="c-board__head">
                <strong class="c-board__title">
                  <span class="c-board__title__sub"></span>${winnerDto.pwnrSubject}
                </strong>
                <div class="c-board__sub">
                  <span class="c-board__sub__item">
                    <span class="c-board__sub__title">이벤트 기간</span>${winnerDto.eventStartDt}~${winnerDto.eventEndDt}
                  </span>
                  <span class="c-board__sub__item">
                    <span class="c-board__sub__title">당첨자 발표</span><fmt:formatDate value="${winnerDto.cretDt}" pattern="yyyy.MM.dd"/>
                  </span>
                </div>
              </div>
              <div class="c-board__content">
                <article class="c-editor">
                  <!--  에디터 내용-->${winnerDto.pwnrSbst}
                </article>
              </div>
            </div>
            <div class="c-button-wrap u-mt--56">
              <c:if test="${eventBranch eq 'E'}">
                <a href="javascript:;" class="c-button c-button--lg c-button--white c-button--w460" onclick="winnerList('/event/winnerList.do');">목록보기</a>
              </c:if>
              <c:if test="${eventBranch eq 'J'}">
                <a href="javascript:;" class="c-button c-button--lg c-button--white c-button--w460" onclick="winnerList('/event/cprtEventWinnerList.do');">목록보기</a>
              </c:if>
            </div>
          </div>
        </div>
      </div>
    </div>
</t:putAttribute>
<t:putAttribute name="scriptHeaderAttr">
<script type="text/javascript">
var pageNo = parseInt($("#pageNo").val(),10);


$(document).ready(function () {

    $("#tab1").click(function() {
        if($('#eventBranch').val() == 'E'){
            goEventView("/event/eventBoardList.do");
        }else{
            goEventView("/event/cprtEventBoardList.do");
        }
    });

    $("#tab2").click(function() {
        if($('#eventBranch').val() == 'E'){
            goEventView("/event/eventBoardEndList.do");
        }else{
            goEventView("/event/cprtEventBoardEndList.do");
        }
    });

    $("#tab3").click(function() {
        if($('#eventBranch').val() == 'E'){
            goEventView("/event/winnerList.do");
        }else{
            goEventView("/event/cprtEventWinnerList.do");
        }
    });

	setTimeout(function(){
		$('#subbody_loading').hide();
	}, 200);

});

function goEventView(url) {
    ajaxCommon.createForm({
            method:"post"
            ,action: url
    });
    ajaxCommon.formSubmit();
};

function winnerList(action){
    ajaxCommon.createForm({
        method:"post"
        ,action: action
    });
    ajaxCommon.attachHiddenElement("pageNo",pageNo);
    ajaxCommon.formSubmit();
};

</script>

</t:putAttribute>

</t:insertDefinition>