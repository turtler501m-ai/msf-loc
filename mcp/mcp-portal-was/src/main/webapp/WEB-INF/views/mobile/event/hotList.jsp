
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script>
        $(document).ready(function(){
        	
        	$("#tabSelect").change(function(){
				if($(this).val()=="0"){
					$(location).attr("href", "/m/event/hotList.do");
        		}else if($(this).val()=="1"){
        			$(location).attr("href", "/m/event/eventList.do");	
        		}else if($(this).val()=="2"){
        			$(location).attr("href", "/m/event/winnerList.do");
        		}
        	});
        	
        	$("#hotMore").click(function(){
	    		$(location).attr("href", "/m/event/eventList.do?chkChoice=eg");
	    	});
	    	
	    	$("#jehuMore").click(function(){		
	    		$(location).attr("href", "/m/event/eventList.do?chkChoice=je");
	    	});
	    	
	    	$(".openlink").click(function() {
	    		$thisarea = $(this);
	    		$thisarea.hide();
	    		$thisarea.siblings(".tag_winner_box").show();
	    	});
	    	
	    	$(".closelink").click(function() {
	    		$thisarea = $(this).parent();
	    		$thisarea.hide();
	    		$thisarea.siblings(".openlink").show();
	    	});
        });
    	</script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
     <div id="content">
<!-- 		<div class="content_top">			 -->
<!-- 			<div class="mobile_tab"> -->
<!--                 <div class="margin_lf_15"> -->
<!-- 					<select name="tabSelect" id="tabSelect" class="select_medium width_full"> -->
<!-- 						<option value="0">진행중인 이벤트</option> -->
<!-- 						<option value="1">이벤트</option> -->
<!-- 						<option value="2">당첨자발표</option> -->
<!-- 					</select> -->
<!--                 </div> -->
<!--             </div> -->
<!-- 		</div> -->
		<div class="content_main">
			<div id="tab_using1">
				<div class="ing_list event_list ">
					<div class="select_area">
						<select name="tabSelect" id="tabSelect" class="select_medium width_full">
							<option value="0">HOT 이벤트</option>
							<option value="1">이벤트</option>
							<option value="2">당첨자발표</option>
						</select>
					</div>					
					<div class="category_title">
						이벤트/기획전
						<button type="button" class="btn_title_shortcut" id="hotMore">더보기</button>
					</div>
					<div class="list">
						<div class="list_item">
							<c:forEach items="${hotList}" var="hList">
								<c:if test="${hList.dDay >= 0}">
									<div class="item_image">
										<a href="/m/event/eventDetail.do?ntcartSeq=${hList.ntcartSeq}&sbstCtg=${hList.sbstCtg}&selectEvt=H"><img src="${hList.listImg}"/></a>
										<c:if test="${not empty hList.winnerYn}">
											<div class="tag_winner_box" style="display:none">
												<a href="/m/event/winnerDetail.do?ntcartSeq=${hList.ntcartSeq}&pwnrSeq=${hList.winnerYn}&pageNo=1">당첨자 발표 ></a>
												<img src="/resources/images/mobile/btn_event_winner_cancel.png" class="closelink" />
											</div>
											<img src="/resources/images/mobile/btn_event_winner.png" class="openlink" />	
										</c:if>
									</div>
									<div class="item_title">
										${hList.ntcartSubject}
<%-- 										<span><span class="text_color_red"><c:if test="${hList.dDay >= 0}">(D-${hList.dDay})</c:if><c:if test="${hList.dDay < 0}">(종료)</c:if></span></span> --%>
									</div>
									<div class="item_regdate">
										<fmt:parseDate value="${hList.eventStartDt}" var="startdt" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${startdt}" pattern="yyyy.MM.dd"/> ~ <fmt:parseDate value="${hList.eventEndDt}" var="enddt" pattern="yyyy-MM-dd HH:mm:ss"/><fmt:formatDate value="${enddt}" pattern="yyyy.MM.dd"/> 
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
					<div class="grey_space"></div>
					<div class="category_title">
						제휴
						<button type="button" class="btn_title_shortcut" id="jehuMore">더보기</button>
					</div>
					<div class="list">
						<div class="list_item">
							<c:forEach items="${jehuList}" var="jList">
								<c:if test="${jList.dDay >= 0}">
									<div class="item_image">
										<a href="/m/event/eventDetail.do?ntcartSeq=${jList.ntcartSeq}&sbstCtg=${jList.sbstCtg}"><img src="${jList.listImg}"/></a>
									</div>
									<div class="item_title">
										${jList.ntcartSubject}
<%-- 										<span><span class="text_color_red"><c:if test="${jList.dDay >= 0}">(D-${jList.dDay})</c:if><c:if test="${jList.dDay < 0}">(종료)</c:if></span></span> --%>
									</div>
									<div class="item_regdate">
										<fmt:parseDate value="${jList.eventStartDt}" var="startdt" pattern="yyyy.MM.dd"/><fmt:formatDate value="${startdt}" pattern="yyyy.MM.dd"/> ~ <fmt:parseDate value="${jList.eventEndDt}" var="enddt" pattern="yyyy.MM.dd"/><fmt:formatDate value="${enddt}" pattern="yyyy.MM.dd"/>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>		
	</div>  
    </t:putAttribute>
</t:insertDefinition>
