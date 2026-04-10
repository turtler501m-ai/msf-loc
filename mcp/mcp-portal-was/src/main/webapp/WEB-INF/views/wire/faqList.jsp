<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutWireDefault">
	<t:putAttribute name="titleAttr"> FAQ - kt M모바일  </t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
    	<script type="text/javascript">
    	var menuCode= "WR0801";

    	var pageObj = {
                 pageNo:1
                 ,sbstCtg:""
                 ,searchNm:""
                 ,$listTb:null
                 ,$divPaging:null
            }

            $(document).ready(function () {
            	
            	pageObj.$listTb= $('#listTb') ;
                pageObj.$divPaging= $('#divPaging') ;
                
                //검색 버튼    
                $("#btnSearch").click(function() {
                	pageObj.searchNm = $("#searchNm").val();
                    pageObj.pageNo = 1 ;
                    getList();
                });
                
                //검색 엔터
                $("#searchNm").keydown(function (key) {
                    if (key.keyCode == 13) {
                        $("#btnSearch").trigger("click");
                    }
                });
                
                //페이징 Click
                $(document).on("click","._pageWrap",function(){
                    var pageNo = parseInt($(this).attr("pageNo"),10) ;
                    pageObj.pageNo = pageNo ;
                    getList();
                });
                
            	//탭 선택
            	$("#tabMenu1 > a").click(function() {	
            		pageObj.searchNm = "";
            	    $("#searchNm").val("");
            		var sbstCtg = $("> div",this).attr("sbstCtg");
                    pageObj.sbstCtg = sbstCtg;
                    
                	$("#tabMenu1 > a > div").removeClass("active");
            		$("> div", this).addClass("active");		
            		$("#detailTit").text($("> div", this).text());	
            		
            		pageObj.pageNo = 1 ;
            	    getList();        
            	}); 
            		
                var getList = function() {
                	
                    var varData = ajaxCommon.getSerializedData({
                        pageNo:pageObj.pageNo
                        ,sbstCtg:pageObj.sbstCtg
                        ,boardCtgSeq:BOARD_CTG.WIRE_FAQ
                        ,searchNm:pageObj.searchNm
                    });

                    ajaxCommon.getItem({
                        id:'getSellUsimList'
                        ,cache:false
                        ,url:'/cs/getBoardListAjax.do'
                        ,data: varData
                        ,dataType:"json"
                    }
                    ,function(jsonObj){
                        pageObj.$listTb.empty();
                        var listData = jsonObj.DATA_LIST;
                        var pageInfo = jsonObj.PAGE_OBJ;

                        if (listData == undefined || listData.length == 0 ) {
                            var listHtml ;
                            if (pageObj.searchNm == "") {
                                listHtml="<tr><td style='text-align:center;'>등록된 게시물이 없습니다.</td></tr>";
                            } else {
                                listHtml="<tr><td style='text-align:center;'>일치하는 검색결과가 없습니다.</td></tr>";
                            }
                            pageObj.$listTb.append(listHtml);
                        } else {

                            if(listData!=undefined && listData.length > 0 ){
                                for (var i=0 ; i < listData.length ; i++) {
                                    var listHtml = getRowTemplate(listData[i],pageInfo ,i);
                                    pageObj.$listTb.append(listHtml);
                                }
                                ajaxCommon.setPaging(pageObj.$divPaging,pageInfo);
                            }
                        }

                        var titleTxt = $(".common_tab").find(".active span").text();      	   
                    });
                 };
                 
                 var getRowTemplate = function(obj,pageObj,indexCnt){
                     var arr =[];
                     arr.push("<tr class='question'>\n");
                     arr.push("    <td class='title'>Q</td>\n");
                     arr.push("    <td>\n");
                     arr.push("        <a href='javascript:void(0)' boardSeq='"+ obj.boardSeq+"'  class='_detail'  >"+ obj.boardSubject+"</a>\n");
                     arr.push("    </td>\n");
                     arr.push("</tr>\n");
                     arr.push("<tr class='answer' boardSeq='"+ obj.boardSeq+"' style='display:none;' >\n");
                     arr.push("  <td class='title'>A</td>\n");
                     arr.push("  <td class='answerContent' style='padding-right: 30px'></td>\n");
                     arr.push("</tr>\n");              
                     return arr.join('');
                 }
                 
                 $(document).on("click","._detail",function(){
                     var boardSeq = $(this).attr("boardSeq");
                     var $answer =$(".answer[boardSeq='"+boardSeq+"']") ;

                     if($answer.css("display")=='none'){
                         $('.answer').hide();
                         $('.question').removeClass('selected');

                         $(this).parent().addClass('selected');

                         var varData = ajaxCommon.getSerializedData({
                             boardSeq:boardSeq
                         });

                         ajaxCommon.getItem({
                             id : 'Answer',
                             cache : false,
                             url : '/cs/faqGetAnswer.do',
                             data : varData,
                             dataType : "json"
                         }, function(data) {
                                 $answer.show();
                                 $answer.find(".answerContent").html(data.answer);
                             }
                         );

                     } else {
                         $('.question').removeClass('selected');
                         $('.answer').hide();
                     }
                 });
                 
                 
                 getList();
                
            })

		</script>
    </t:putAttribute>
    
    <t:putAttribute name="contentAttr">		
    	
    	<div class="contentsWrap subPage">
    		<div class="pageWrap">
		    
		    
		    	<div id="container">
		        	<div class="customerservice">
						<h3 class="cs_title">
							<span id="topMenuTitleSpan">자주 묻는 질문 (FAQ)</span><span class="subtitle"></span>
		                    <span class="float_right"><input class="height_34px" title="검색" type="text" id="searchNm" value="" placeholder="검색어를 입력해 주세요."><button type="button" title="검색" id="btnSearch" class="btn_search">검색</button></span>
						</h3>
						
						<div class="cs_faq">
							<div class="category">
								<div class="common_tab">
				                	<div class="tab" id="tabMenu1">	                            
			 	                    	<c:set var="sbstCtgList" value="${nmcp:getCodeList(Constants.GROUP_CODE_WIRE_CTG)}" />	 	                    
					                    <fmt:parseNumber var="widthSize" value="${100/(fn:length(sbstCtgList)+1)}" integerOnly="true"/>               
					                    <a href="javascript:void(0);" ><div class="item_first width_${widthSize} active" sbstCtg=""><span>전체</span></div></a>
					                    <c:forEach items="${sbstCtgList}" var="sbstCtg" >
					                    	<a href="javascript:void(0);" ><div class="item_others width_${widthSize}" sbstCtg="${sbstCtg.dtlCd}"><span >${sbstCtg.dtlCdNm}</span></div></a>		                         
					                    </c:forEach>	                    	
			                        </div>
			                   	</div>
							 </div> 
							
							<h3 class="hidden" id="detailTit">전체</h3>
							
							<div class="faqlist">
								<table id="faqMainTable">
									<colgroup>
										<col width="90px">
										<col>
									</colgroup>
									<tbody id="listTb">				
																
									</tbody>
								</table>
							</div>
								
							<!-- 페이징 S-->
							<div class="board_list_table_paging">
								<div id="divPaging" >
							
								</div>
							</div>
							<!-- 페이징 E-->
								
						</div>
					</div>
		   	  	</div>
		   	  	
	   	  	</div>
   	  	
   	  	
    	</div>
    	    	
    </t:putAttribute>
</t:insertDefinition>