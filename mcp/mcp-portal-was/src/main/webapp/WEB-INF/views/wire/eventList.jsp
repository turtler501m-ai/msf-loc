<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutWireDefault">
	<t:putAttribute name="titleAttr"> 이벤트 - kt M모바일  </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        
        <script type="text/javascript">

            var menuCode= "WR0701";
        	$(document).ready(function() {
        	
	        	//이벤트 선택
	        	$("a._Detail").click(function() {
	                var ntcartSeq = $(this).attr("ntcartSeq");
	                var linkUrlAdr = $(this).attr("linkUrlAdr");
	                var linkTarget = $(this).attr("linkTarget");
	
	                if (linkUrlAdr == null || linkUrlAdr == "") {
	                    ajaxCommon.createForm({
	                        method:"get"
	                        ,action:"/wire/eventDetail.do"
	                     });
	
	                    ajaxCommon.attachHiddenElement("ntcartSeq", ntcartSeq);
	                    ajaxCommon.attachHiddenElement("pageNo", $("#pageNo").val());		                   
	                    ajaxCommon.attachHiddenElement("searchValue", $.trim($("#ntcartSbst").val()));
	                    ajaxCommon.formSubmit();
	                } else {
	                    if (linkTarget == "Y") {
	                        ajaxCommon.createForm({
	                            method:"post"
	                            ,action:linkUrlAdr
	                        });
	                    } else {
	                        ajaxCommon.createForm({
	                            method:"post"
	                            ,action:linkUrlAdr
	                            ,target:"_blank"
	                        });
	                    }
	                    ajaxCommon.formSubmit();
	                }
	            }); 	
	        	
	        	//검색
	        	$("#searchBtn").click(function() {
	                ajaxCommon.createForm({
	                    method:"get"
	                    ,action:"/wire/eventList.do"
	                 });
	
	                ajaxCommon.attachHiddenElement("pageNo",1);		               
	                ajaxCommon.attachHiddenElement("ntcartSbst",$.trim($("#tmpSearchValue").val()));
	                ajaxCommon.formSubmit();
	            });
	        	
	        	//검색 엔터
	       		$("#tmpSearchValue").keydown(function (key) {
	       	    	if (key.keyCode == 13) {
	       	        	$("#searchBtn").trigger("click");
	       	        }
	       	    });
	        	
	        	//당첨자 발표
	       		$(".btn_show_winner").click(function() {
	       	        $thisarea = $(this).parent();
	       	        $thisarea.hide();
	       	        $thisarea.siblings(".go_winner").show();
	       	    });
	
	       	    $(".btn_show_title").click(function() {
	       	        $thisarea = $(this).parent();
	       	        $thisarea.hide();
	       	        $thisarea.siblings(".event_content").show();
	       	    });
	       	    
	       	 
        	
     		});
        	
       		var goPage = function(pageNo) {

       	    	ajaxCommon.createForm({
       	      	  method:"get"
       	       	 ,action:"/wire/eventList.do"
       	    	 });

	       	    ajaxCommon.attachHiddenElement("pageNo",pageNo);	      
	       	    ajaxCommon.attachHiddenElement("ntcartSbst",$.trim($("#ntcartSbst").val()));
	       	    ajaxCommon.formSubmit();
	       	    
       		};
    	</script>
    </t:putAttribute>
    
    <t:putAttribute name="contentAttr">		
       <div class="contentsWrap subPage" style="padding-bottom: 50px;">
    		<div class="pageWrap">
       <input type="hidden" name="pageNo" id="pageNo" value="${PageInfoBean.pageNo}"/>   
       <input type="hidden" name="ntcartSbst" id="ntcartSbst" value="${NmcpEventBoardBas.ntcartSbst}"/>
       
        
        <div id="container" style="padding-bottom:0px">
            <div class="background">
                <div class="viewport">
                    <div class="event_view" style="margin-top:30px;">
                        <h2 class="event_title">
                            <span>이벤트</span>
                            <div class="search_box float_right">
                                <input type="text" class="height_34px" title="검색어 입력" id="tmpSearchValue" value="${NmcpEventBoardBas.ntcartSbst}" placeholder="검색어를 입력해주세요." /><button id="searchBtn" type="button" class="btn_search" title="검색">검색</button>
                            </div>
                        </h2>

                        <div class="padding_30">
                            
                            <div id="tab_using2">
                            	<h3 class="hidden" id="detailTit"></h3>
                                <div class="event_list">
                                    <c:if test="${not empty EventList}">
                                        <div class="eventList">
                                            <ul>
                                                <c:forEach items="${EventList}" var="elist" varStatus="se">
                                                    <c:if test="${not se.first and se.index%3 eq 0}">
                                                        <div class="eventList">
                                                            <ul>
                                                    </c:if>
                                                    <li>
                                                        <a href="javascript:void(0)" class="_Detail" ntcartSeq="${elist.ntcartSeq}" linkUrlAdr="${elist.linkUrlAdr}" linkTarget="${elist.linkTarget}" style="display:inline-block;" >
                                                            <div class="event_image">
                                                                <img src="${elist.listImg}" alt="${elist.imgDesc}" onerror="this.src='/resources/images/front/board_default_image.jpg'"/>
                                                                 <c:if test="${not elist.eventEndDtToAfterDate}">
                                                                    <!-- 이벤트 종료시 이미지 -->
                                                                    <div class="end_img"><span class="hidden">이벤트 종료</span></div>
                                                                    <!-- 이벤트 종료시 이미지 -->
                                                                 </c:if>
                                                            </div>
                                                        </a>

                                                        <div class="event_content">
                                                            <div class="eventtitle text_align_center padding_bottom_5">
                                                                <a href="javascript:void(0)" class="_Detail" ntcartSeq="${elist.ntcartSeq}" linkUrlAdr="${elist.linkUrlAdr}" linkTarget="${elist.linkTarget}" style="display:inline-block;" >
                                                                    ${elist.ntcartSubject}
                                                                </a>
                                                            </div>
                                                            <div class="calendar">
                                                                <fmt:formatDate value="${elist.eventStartDt}" pattern="yyyy.MM.dd"/> ~
                                                                <fmt:formatDate value="${elist.eventEndDt}" pattern="yyyy.MM.dd"/>
                                                                &nbsp;&nbsp;|&nbsp;&nbsp; 조회 <fmt:formatNumber value="${elist.ntcartHitCnt}" pattern="#,###" />
                                                            </div>
                                                             <c:if test="${not empty elist.winnerYn}">
                                                                <img src="/resources/images/front/btn_event_winner.png" alt="당첨자발표" class="btn_show_winner" />
                                                            </c:if>
                                                        </div>
                                                        <c:if test="${not empty elist.winnerYn}">
                                                            <div class="go_winner" style="display: none;">
                                                                <a href="/wire/winnerDetail.do?ntcartSeq=${elist.ntcartSeq}&pwnrSeq=${elist.winnerYn}&pageNo=1" style="display:inline-block;">
                                                                           당첨자 발표 >
                                                                </a>
                                                                <img src="/resources/images/front/btn_event_winner.png" alt="당첨자보기" class="btn_show_title" />
                                                            </div>
                                                        </c:if>
                                                    </li>

                                                    <c:if test="${not se.last and se.index%3 eq 2}">
                                                            </ul>
                                                        </div>
                                                    </c:if>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </c:if>
                                    
                                    <c:if test="${empty EventList}">
                                        <div class="eventList">
                                            <div class="no_list">
                                                <img src="/resources/images/front/icon_nolist.png" alt="진행중인 이벤트가 없습니다." />
                                                <div class="desc">진행중인 이벤트가 없습니다.</div>
                                            </div>
                                        </div>
                                    </c:if>
                                    
                                    <div class="board_list_table_paging margin_top_30">
                                        <nmcp:pageInfo pageInfoBean="${PageInfoBean}" function="goPage"/>
                                    </div>
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
        </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>