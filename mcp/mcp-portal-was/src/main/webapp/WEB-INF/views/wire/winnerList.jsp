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
            	var pageObj = {
            		$listTb:null
            		,$divPaging:null
            		,pageNo:1
            		,searchValue:""
            	}
            	
		        $(document).ready(function() {
		            
		            $("#searchValue").keyup(function(event){
		           	 if (event.keyCode == 13) {
		           		 $('#btnSearch').trigger('click');
		                }
		           });

		           $("#btnSearch").click(function(){
		           	pageObj.searchValue = $.trim($("#searchValue").val());
		           	getList(1);
		           });



		           $(document).on("click","#listTb a",function(){
		           	var ntcartSeq = $(this).attr("ntcartSeq") ;
		           	var pwnrSeq =$(this).attr("pwnrSeq");


		           	ajaxCommon.createForm({
		                   method:"get"
		                   ,action:"/wire/winnerDetail.do"
		                });

		               ajaxCommon.attachHiddenElement("ntcartSeq",ntcartSeq);
		               ajaxCommon.attachHiddenElement("pwnrSeq",pwnrSeq);
		               ajaxCommon.attachHiddenElement("pageNo",pageObj.pageNo);
		               ajaxCommon.attachHiddenElement("searchValue",pageObj.searchValue);
		               ajaxCommon.formSubmit();

		           });


		           //페이징 Click
		           $(document).on("click","._pageWrap",function(){
		           	var pageNo = parseInt($(this).attr("pageNo"),10) ;
		           	getList(pageNo);
		           });
		           
		           
		           pageObj.$listTb =  $("#listTb");
		           pageObj.$divPaging =  $("#divPaging");
		           pageObj.pageNo =  parseInt($("#pageNo").val(),10) ;
		           pageObj.searchValue = $.trim($("#searchValue").val());
		           getList(pageObj.pageNo); 
		            
		        });
		        
		        var getList = function(pageNo){
        	        pageObj.pageNo = pageNo

        	        if (pageObj.searchValue == "검색어를 입력해주세요.") {
        	        	pageObj.searchValue = "";
        	        }

        	        var varData = ajaxCommon.getSerializedData({
        	            pageNo:pageObj.pageNo
        	            , searchValue:pageObj.searchValue
        	            , recordCount:10
        	            , sbstCtg : $("#sbstCtg").val()
        	        });

        	        ajaxCommon.getItem({
        	            id:'getList'
        	            ,cache:false
        	            ,url:'/m/event/winnerListAjax.do'
        	            ,data: varData
        	            ,dataType:"json"
        	         }
        	         ,function(jsonObj){
        	        	 var listData = jsonObj.winnerList;
        	        	 var pageInfo = jsonObj.pageInfoBean;

        	        	 pageObj.$listTb.empty();
        	        	 pageObj.$divPaging.empty();

        	             if(listData!=undefined && listData.length > 0 ){
        	            	 for (var i=0 ; i < listData.length ; i++) {
        		            	 var listHtml = getRowTemplate(listData[i],pageInfo,i)  ;
        		                 if (listHtml != "") {
        		                	 pageObj.$listTb.append(listHtml);
        		                 }
        	            	 }

        	            	 ajaxCommon.setPaging(pageObj.$divPaging,pageInfo);
        	             } else {
        	            	 pageObj.$listTb.append("<tr><td colspan='4'>등록된 게시물이 없습니다.</td></tr>");
        	             }
        	         });
        	    };
        	    
        	    var getRowTemplate = function(obj,pageInfo,index){
        	        var arr =[];
        	        var eventStartDt = obj.eventStartDt;
        	        var eventEndDt = obj.eventEndDt;
        	        var cretDt = new Date(obj.cretDt);

        	        var cnt = pageInfo.totalCount - ((pageInfo.pageNo-1) * pageInfo.recordCount) - index ;

        	        arr.push("<tr> \n");
        	        arr.push("  <td>"+cnt+"</td>\n");
        	        arr.push("  <td class='subject'><a href='javascript:void(0)' ntcartSeq='"+obj.ntcartSeq+"' pwnrSeq='"+obj.pwnrSeq+"' >"+obj.pwnrSubject+"</a></td>\n");
        	        arr.push("  <td>"+ eventStartDt + " ~ " + eventEndDt +"</td>\n");
        	        arr.push("  <td>"+ cretDt.format("yyyy.MM.dd") + "</td>\n");
        	        arr.push("</tr>\n");


        	        return arr.join('');
        	    }
		        
		     </script>
    </t:putAttribute>
    
    <t:putAttribute name="contentAttr">
    	<div class="contentsWrap subPage">
   			<div class="pageWrap">
		    	<div id="container">
			        <div class="background">
			            <div class="viewport">
			                <div class="event_view" style="margin-top:30px;">
			
			                	<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
			                	<input type="hidden" name="sbstCtg" id="sbstCtg" value="${Constants.WIRE_EVENT_SBSTCTG_CD}"/>
			                    <h2 class="event_title">
			                        <span>이벤트</span>
			                        <div class="search_box float_right">
			                            <input type="text" title="검색어 입력 " class="height_34px" name="searchValue" id="searchValue" value="${searchValue}" placeholder="검색어를 입력해주세요."   />
			                            <button onclick="javascript:void(0)" id="btnSearch" type="button" class="btn_search" title="검색">검색</button>
			                        </div>
			                    </h2>
			                    <div class="padding_30">
			                   
			                        <!-- 당첨자 발표 시작 -->
			                        <div id="tab_using3">
			                        	<h3 class="hidden" id="detailTit">이벤트 당첨자 발표</h3>
			                            <div class="winner_list">
			                                <div class="board_list_table">
			                                    <div class="mypage_title_sub">
			                                        <div class="clear_both"></div>
			                                    </div>
			
			                                    <table cellspacing="0" cellpadding="0" summary="번호,제목,이벤트기간,당첨자발표일로 이루어진 당첨자 발표 테이블">
			                                        <caption class="hidden">당첨자 발표 테이블입니다.</caption>
			                                        <thead>
			                                            <tr>
			                                                <th scope="col">번호</th>
			                                                <th scope="col">제목</th>
			                                                <th scope="col">이벤트 기간</th>
			                                                <th scope="col">당첨자 발표일</th>
			                                            </tr>
			                                        </thead>
			                                        <tbody id="listTb">
														<tr><td colspan='4'>등록된 게시물이 없습니다.</td></tr>
			                                        </tbody>
			                                    </table>
			                                </div>
			                                <div class="board_list_table_paging" id="divPaging">
			
			                                </div>
			                            </div>
			                            <!-- 당첨자 발표 종료 -->
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