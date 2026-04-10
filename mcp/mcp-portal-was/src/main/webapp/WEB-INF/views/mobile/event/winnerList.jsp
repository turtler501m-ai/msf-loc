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
        <script>
            var pageObj = {
            	searchValue:""
            }
            var pageNo = parseInt(1,10);
            var totalPageCount = parseInt($("#totalPageCount").val(),10);
            var totalCount = parseInt($("#totalCount").val(),10);

            $(document).ready(function(){

            	$("#tab1").click(function() {
                    $("#eventStatus").val('ing');
                    if($('#eventBranch').val() == 'E'){
                    	   location.href = "/m/event/eventList.do";

                    }else{
                        location.href = "/m/event/cprtEventBoardList.do";

                    }
                });

                $("#tab2").click(function() {
                    $("#eventStatus").val('end');
                    if($('#eventBranch').val() == 'E'){
                        location.href = "/m/event/eventBoardEndList.do";

                    }else{
                        location.href = "/m/event/cprtEventBoardEndList.do";

                    }
                });

                $("#tab3").click(function() {
                    $("#eventStatus").val('win');
                    //moreView(pageNo);
                });

            	$("#searchBtn").click(function(){
                	$('#searchYn').val('Y');
            		moreView(pageNo);
            	});

            	moreView(pageNo);
            });

            function moreView(pageNo){

                 var recordCount = 10 ;

            	 var varData = ajaxCommon.getSerializedData({
            		 pageNo:pageNo
                     ,sbstCtg:$("#sbstCtg").val()
                     ,searchValue:$.trim($("#searchValue").val())
                     ,recordCount:recordCount
                     ,searchYn:$('#searchYn').val()
                     ,eventBranch: $('#eventBranch').val()
                 });

            	 pageObj.searchValue = $.trim($("#searchValue").val());

            	 ajaxCommon.getItem({
                     id:'accountCheck'
                     ,cache:false
                     ,url:'/m/event/winnerListAjax.do'
                     ,data: varData
                     ,dataType:"json"
                     ,async: false
                 }
                 ,function(data){
                	 if(data != 0){
                         var html="";
                         var list = data.winnerList;
                         totalPageCount = data.pageInfoBean.totalPageCount;
                         totalCount = data.pageInfoBean.totalCount;

                         if(list.length==0){
                        	 html+='<div class="c-nodata">';
                        	 html+='    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
                        	 html+='    <p class="c-noadat__text">검색 결과가 없습니다.</p>';
                        	 html+='</div>';

                        	 $("#more_viewDiv").hide();
                        	 $("#listArea").remove();
                        	 $("#tabA3-panel").html(html);
                         }else{
                        	 if(pageNo >= 1){
                                 for(var i=0; i<list.length ; i++){
                                	 var eventStartDt = list[i].eventStartDt;
                                     var eventEndDt = list[i].eventEndDt;
                                     var cretDt = new Date(list[i].cretDt);
                                     html += " <li class='c-list__item'>";
                                     html += "   <a onclick=goView('"+list[i].ntcartSeq+"') class='c-list__anchor';>";
                                     html += "         <strong class='c-list__title c-text-ellipsis'>" + list[i].pwnrSubject + "</strong>";
                                     html += "         <div class='c-list__sub'>";
                                     html += "           <span>" + eventStartDt + " ~ " + eventEndDt + "</span>";
                                     html += "           <span class='tit'>발표</span>";
                                     html += "           <span>" + cretDt.format("yyyy.MM.dd") + "</span>";
                                     html += "         </div>";
                                     html += "     </a>";
                                     html += " </li>";
                                 }
                             }
                        	 $("#listArea").append(html);
                         }
                         $("#more_viewDiv").css('visibility','visible');
                         $("#total_page").html(replaceToStr(totalCount));
                         $("#current_page").html(replaceToStr($("#listArea .c-list__item").length));

                         if (totalCount == $("#listArea .c-list__item").length) {
                             $("#more_viewDiv").hide();
                         }
                     }
                 });
    	    }

            function goView(ntcartSeq){
            	var action = "";
                if($('#eventBranch').val() == 'E'){
                	action = "/m/event/winnerDetail.do"
                }else{
                	action = "/m/event/cprtEventWinnerDetail.do"
                }

                var url = action + "?ntcartSeq=" + ntcartSeq + "&pageNo=" + $("#pageNo").val() + "&eventBranch=" + $('#eventBranch').val();
            	location.href = url;
            };


            var bntMormView = function () {
            	pageNo++;
                if (totalPageCount >= pageNo ) {
                    moreView(pageNo);
                }
            }

            //이번달로 이동
            function goWinView(url) {
                ajaxCommon.createForm({
                        method:"post"
                        ,action: url
                });
                ajaxCommon.formSubmit();
            };

    	</script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
    <div id="content">
		<div class="content_main">
			<input type="hidden" name="chkChoice" id="chkChoice"/>
			<input type="hidden" id="eventBranch" name="eventBranch" value="${eventBranch}"/>
			<input type="hidden" id="ntcartSeq" name="ntcartSeq" value="0"/>
			<input type="hidden" id="pageNo" name="pageNo"  value="${empty pageInfoBean.pageNo?1:pageInfoBean.pageNo}" />
			<input type="hidden" id="initPageNo" name="initPageNo"  value="${pageInfoBean.pageNo}" />
			<input type="hidden" id="sbstCtg" name="sbstCtg" value="${empty searchDto.sbstCtg?'J':searchDto.sbstCtg}"/>
			<input type="hidden" id="totalPageCount" name="totalPageCount" value="${pageInfoBean.totalPageCount}"/>
			<input type="hidden" id="totalCount" name="totalCount" value="${pageInfoBean.totalCount}"/>
			<input type="hidden" id="searchYn" name="searchYn" value="${empty searchDto.searchYn ? 'N' : searchDto.searchYn}"/>

		    <div id="tab_using3">
		    	<div class="ly-content">
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
                        <div class="c-tabs__panel" id="tabA2-panel"> </div>
                        <div class="c-tabs__panel" id="tabA3-panel">
                            <!-- 당첨자 확인 목록-->
                            <ul class="c-list c-list--type1" id="listArea">

		    		        </ul>
		    		    </div>
		    		    <div id="more_viewDiv" class="c-button-wrap" style="visibility: hidden;">
		    		    	<button id="more_view" onclick="javascript:bntMormView();" class="c-button c-button--full" >더보기<span class="c-button__sub"> (<span id="current_page"></span>/<span id="total_page"></span>)</span>
		    		            <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
		    	            </button>
		                </div>
		    	    </div>
		    	</div>
		    </div>
		</div>
	</div>
    </t:putAttribute>
</t:insertDefinition>
