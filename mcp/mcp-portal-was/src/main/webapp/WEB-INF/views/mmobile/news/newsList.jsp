<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<c:set var="imgpath" value="${pageContext.request.contextPath}/resources/images"/>
<t:insertDefinition name="layoutCompanyDefault">

<t:putAttribute name="scriptHeaderAttr">


<script type="text/javascript">

var menuCode= "SM0802";
var defaultStr = "";

$(document).ready(function () {

    $("#schBtn").click(function(){	//검색
    	$("#searchValue").val($("#tmpSearchValue").val());
    	$('input[name=searchYn]').val('Y');
    	$("input[name='pageNo']").val('1');
    	$("form[name='newsListForm']").submit();
    });

	//엔터처리
	$("#tmpSearchValue").keydown(function (key) {
	    if (key.keyCode == 13) {
	    	$("#schBtn").trigger("click");
	    }
	});

	$(document).on("click", ".btn_stop", function(){
		$(".btn_stop").css("display","none");
		$(".btn_start").css("display","");
		$(".btn_start").focus();
		myBannerSwipe.stop();
	});

	$(document).on("click", ".btn_start", function(){
		$(".btn_start").css("display","none");
		$(".btn_stop").css("display","");
		$(".btn_stop").focus();
		myBannerSwipe.begin();
	});

	setTimeout(function(){
		$('#subbody_loading').hide();
    }, 200);

});

var newsListSubmit = function(pageNo) {
	$("input[name='pageNo']").val(pageNo);
	$("form[name='newsListForm']").submit();
};

var goNewsDetail = function(newsDatSeq, noti) {
	$("#notiYn").val(noti);

	if(defaultStr == $('input[name=searchValue]').val()) {
		$('input[name=searchValue]').val('');
	}

	$("input[name='pageNo']").val();
	$("#newsDatSeq").val(newsDatSeq);
	$("form[name='newsListForm']").attr("action", "/mmobile/newsDetail.do");
	$("form[name='newsListForm']").submit();
}

</script>

</t:putAttribute>

<t:putAttribute name="contentAttr">

	<div id="subbody_loading" style="display:flex; width: 100%; height: calc(100vh - 130px); box-sizing: border-box; background: rgb(255, 255, 255); text-align: center; position: relative; align-items: center; justify-content: center; z-index: 10;">
		<img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
	</div>

<form id="newsListForm" name="newsListForm" method="POST" action="/mmobile/newsDataList.do">
<input type="hidden" id="pageNo" name="pageNo" value="${newsDataBasDto.pageNo}"/>
<input type="hidden" id="newsDatSeq" name="newsDatSeq" value="${newsDataBasDto.newsDatSeq}"/>
<input type="hidden" name="searchValue" id="searchValue" value="${searchDto.searchValue}"/>
<input type="hidden" id="notiYn" name="notiYn" value="${newsDataBasDto.notiYn}"/>
<input type="hidden" id="searchYn" name="searchYn" value="${newsDataBasDto.searchYn == null or newsDataBasDto.searchYn == '' ? 'N' : newsDataBasDto.searchYn}"/>
     <div id="container" >
        <div class="background">
            <div class="viewport">
                <div class="news_list">
                    <h2 class="news_title">
                        <span>보도자료</span><span class="subtitle">다양한 매체에서 소개된 엠모바일 뉴스를 만나실 수 있습니다.</span>
                        <span class="float_right"><input title="검색" type="text" id="tmpSearchValue" value="${searchDto.searchValue}" placeholder="검색어를 입력하세요" /><button type="button" class="btn_search" id="schBtn" title="검색">검색</button></span>
                    </h2>

                    <div class="other_list">
                    	<div id="newsBanner" style="display:none;">
                    	<c:if test="${not empty newsNotiList}">
			                <div class="banner">
			                    <div class="banner_image">
			                        <div id='banner_slider' class='swipe'>
			                            <div class='swipe-wrap'>
			                            <c:forEach items="${newsNotiList}" var="nd" varStatus="s">
			                                <div class="item_news">
			                                	<c:if test="${empty nd.linkUrlAdr}">
			                                			<a href="javascript:goNewsDetail('${nd.newsDatSeq}','Y')">
			                                	</c:if>
			                                	<c:if test="${not empty nd.linkUrlAdr}">
			                                		<c:if test="${nd.linkTarget eq 'N'}">
			                                			<a href="window.open('${nd.linkUrlAdr}');" title="새창">
			                                		</c:if>
			                                		<c:if test="${nd.linkTarget eq 'T'}">
			                                			<a href="${nd.linkUrlAdr}">
			                                		</c:if>
			                                	</c:if>
			                                		<c:if test="${not empty nd.newsDatImg}">
			                                			<div class="img"><img src="${nd.notiImgPath}" alt="${nd.notiImgDesc}"/></div>
			                                		</c:if>
			                                	<div class="text">
				                                	<div class="tit">
				                                		${nd.newsDatSubject}
								                    </div>
								                    <div class="script">
								                    	 ${nd.newsLineSbst}
								                    </div>
								                    <div class="date">
									                    작성일  <fmt:formatDate value="${nd.cretDt}" pattern="yyyy.MM.dd"/>
								                    </div>
												</div>
												</a>
			                                </div>
										</c:forEach>
			                            </div>

			                        </div>
			                    </div>
			                    <span class="banner_index">
			                    <c:forEach items="${newsNotiList}" var="nd2" varStatus="s2">
			                    	<button type="button" class="btn_index <c:if test='${s2.count == 1}'>active</c:if>" id="banner_${s2.count}" title="banner_${s2.count}"></button>
			                    </c:forEach>

			                    </span>
			                    <button type="button" class="btn_stop" title="stop"></button>
			                    <button type="button" class="btn_start" style="display:none" title="start"></button>
			                </div>
					    </div>
						</c:if>
						<c:if test="${empty newsNotiList}">
						</c:if>
                    	<table>
                    		<colgroup>
                    			<col width="200px" />
                    			<col />
                    		</colgroup>

						<c:forEach items="${newsDataList}" var="nd3">
							<tr class="item" >
                          	<c:if test="${nd3.newsDatImg ne null}">
                    			<td class="img">

                    				<c:if test="${nd3.linkUrlAdr eq null}">
	                          			<a href="javascript:javascript:goNewsDetail('${nd3.newsDatSeq}','N');">
		                          	</c:if>
		                          	<c:if test="${nd3.linkUrlAdr ne null}">
		                          		<c:choose>
			                          		<c:when test="${nd3.linkTarget eq 'N'}">
			                          			<a href="javascript:window.open('${nd3.linkUrlAdr}');" title="새창">
			                          		</c:when>
			                          		<c:when test="${nd3.linkTarget eq 'T'}">
			                          			<a href="${nd3.linkUrlAdr}">
			                          		</c:when>
			                          		<c:otherwise><a href="javascript:void(0)"></c:otherwise>

		                          		</c:choose>
		                          	</c:if>
                    					<img src="${nd3.newsDatImg}" alt="${nd3.imgDesc}" onerror="this.src='/resources/images/front/board_default_image.jpg'">
                    				</a>
                    			</td>
                    			<td class="txt">
                    		</c:if>
                    		<c:if test="${nd3.newsDatImg eq null}">
                    			<td class="txt" colspan="2">
                    		</c:if>
                   			<c:if test="${nd3.linkUrlAdr eq null}">
                         			<a href="javascript:javascript:goNewsDetail('${nd3.newsDatSeq}','N');">
                          	</c:if>
                          	<c:if test="${nd3.linkUrlAdr ne null}">
                          		<c:choose>
	                          		<c:when test="${nd3.linkTarget eq 'N'}">
	                          			<a href="javascript:window.open('${nd3.linkUrlAdr}');" title="새창">
	                          		</c:when>
	                          		<c:when test="${nd3.linkTarget eq 'T'}">
	                          			<a href="${nd3.linkUrlAdr}">
	                          		</c:when>
	                          		<c:otherwise><a href="javascript:void(0)"></c:otherwise>
                          		</c:choose>
                          	</c:if>
	                    				<div class="tit">
	                    					<!-- <img src="/resources/images/front/hotPC.png" alt="hot" class="hot" /> -->
						                    ${nd3.newsDatSubject}
						                    <c:if test="${nd3.linkUrlAdr ne null}"><img src="/resources/images/front/icon_target_blank.png" alt="새창 열기"/></c:if>	<!-- 새창으로링크 이미지 -->
					                    </div>
					                    <div class="script">
					                    	<c:set var="subSbst" value="${nd3.newsLineSbst}"/>	<!-- 그냥 fn:length를 걸었더니 html코드가 찍혀서 선처리함 -->
											<c:choose>
												<c:when test="${fn:length(subSbst) > 191}">
													${fn:substring(subSbst,0,190) } . . .
												</c:when>
												<c:otherwise>
													${subSbst}
												</c:otherwise>
											</c:choose>
					                    </div>
				                    </a>
				                    <div class="date">
					                    작성일  <fmt:formatDate value="${nd3.cretDt}" pattern="yyyy.MM.dd"/>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;조회 ${nd3.newsDatHitCnt}
				                	</div>
                    			</td>
                    		</tr>
                    	</c:forEach>
						<c:if test="${empty newsDataList}">
                    		<tr class="item">
                    			<td class="nodata" colspan="2">
                    				<img src="/resources/images/front/icon_nolist.png" alt="일치하는 검색결과가 없습니다."/>
									<div class="desc">일치하는 검색결과가 없습니다.</div>
                    			</td>
                    		</tr>
                    	</c:if>
                    	</table>

						<nav class="c-paging" aria-label="검색결과">
							<nmcp:pageInfo pageInfoBean="${pageInfoBean}" function="newsListSubmit" />
						</nav>
<%--
                    	<div class="board_list_table_paging">
							<nmcp:pageInfo pageInfoBean="${pageInfoBean}" function="newsListSubmit"/>
						</div>
 --%>
                    </div>
                </div>
            </div>
        </div>
    </div>


</form>
</t:putAttribute>

</t:insertDefinition>