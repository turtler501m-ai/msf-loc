<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutWireDefault">
	
    <t:putAttribute name="scriptHeaderAttr">
        
            <script type="text/javascript">
		        $(document).ready(function() {
		           
		        })
		        
		        function goList() {
					$('#frm').attr('action','/wire/winnerList.do');
					$("#frm").submit();
		        }

		     </script>
    </t:putAttribute>
    
    <t:putAttribute name="contentAttr">	
   	    <div class="contentsWrap subPage">
   			<div class="pageWrap">		
		    	<form name="frm" id="frm" method="post" action="">
					<input type="hidden" name="ntcartSeq" id="ntcartSeq" value="${searchDto.ntcartSeq}"/>
					<input type="hidden" name="pwnrSeq" id="pwnrSeq" value="${searchDto.pwnrSeq}"/>
					<input type="hidden" name="pageNo" id="pageNo" value="${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}"/>
					<input type="hidden" name="searchValue" id="searchValue" value="${searchDto.searchValue}"/>
				</form>
		    
		    	<div id="container">
			        <div class="background">
			            <div class="viewport">
			                <div class="event_view" style="margin-top:30px;">
			                    <h2 class="event_title">
			                        <span>이벤트</span>
			                    </h2>
			                    <div class="padding_30">
			                  
			                    <h3 class="hidden" id="detailTit"></h3>
			                    <div class="eventTitle">
			                    	<div class="title">${winnerDto.pwnrSubject}</div>
			                    	<div class="period">
			                    		이벤트 기간 : ${winnerDto.eventStartDt} ~ ${winnerDto.eventEndDt}
			                    		<c:if test="${winnerDto.dDay < 0}">(종료)</c:if> <br/>
			                         	 당첨자 발표 : <fmt:formatDate value="${winnerDto.cretDt}" pattern="yyyy.MM.dd"/>
			                    	</div>
			                    </div>
			                    <div class="eventList border_top_black border_bottom_d3">
			                    	<div class="content"></div>
			                    	${winnerDto.pwnrSbst}
			                    </div>
			                    
			                    <div class="text_align_right margin_top_30"><button type="button" class="btn_right_white" onclick="goList();" title="목록">목록</button></div>
			                    
			                    </div>
			                </div>
			            </div>
			        </div>
			    </div>
	    	</div>
	    </div>	
        
    </t:putAttribute>
</t:insertDefinition>