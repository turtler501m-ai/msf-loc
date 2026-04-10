<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutWireDefault">
	
    <t:putAttribute name="scriptHeaderAttr">
    	   	
    	<script type="text/javascript">
	    	 $(document).ready(function () {
	
	             $('input, text').placeholder();
	
	             $(".privacyCont").html($("#contents").val());
			 	      
	 	   		if ($("#isDetail").val()) {
	 	   			detailTitleMakerFn("[정책 및 약관]개인정보처리방침 > 상세보기");
	 	   		} else {
	 	   			detailTitleMakerFn("[정책 및 약관]개인정보처리방침");
	 	   		}
	
	         });
	
	         /* 개인정보 페이지 이동 */
	    		var pageInfo = function(pageNo){
	    			if (pageNo == '' || pageNo == null) {
	    				pageNo = 1;
	 			}
	
	    			$("#pageNo").val(pageNo);
	    			$('#infoForm').attr('action','/wire/privacyList.do');
	    			$("#infoForm").submit();
	    		}
	
	 		/* 개인정보 상세보기 - 조회수 증가 후 이동 */
	    		function goUpdate(stpltSeq,pageNo){
	
	    			var varData = ajaxCommon.getSerializedData({
	    				stpltSeq : stpltSeq
	    		    });
	
	    			ajaxCommon.getItem({
	    				id : 'hitplus',
	    				cache : false,
	    				url : '/policy/hitplusAjax.do',
	    				data : varData
	    			}, function() {
	    				$("#stpltSeq").val(stpltSeq);
	    				$("#pageNo2").val(pageNo);
	    				$(".pageNo").val(pageNo);
	    				$('#boardFrm').attr('action','/wire/privacyView.do');
	    				$("#boardFrm").submit();
	    			});
	
	    	   	}
	
	 		function goNext(seq) {
	 			if (seq == '' || seq == null) {
	 				return;
	 			}
	
	 			var pageNo = '${pageNo}';
	
	 			if (pageNo == '' || pageNo == null) {
	    				pageNo = 1;
	 			}
	
	 			location.href = "/wire/privacyView.do?pageNo=" + pageNo + "&stpltSeq=" + seq;
	 		}

	    
		</script>
    </t:putAttribute>
    
    <t:putAttribute name="contentAttr">	
      <input type="hidden" id="isDetail" value="${isDetail}">
  	  <div class="contentsWrap subPage">
  	  	<div class="pageWrap">
		
			<div id="container">
			
		        <div class="background" style="padding-top: 30px;">
		            <div class="viewport">
		                <div class="product_view">
		                	<h2 class="title_2depth">정책 및 약관
					          <span class="subtitle">케이티 엠모바일은 고객님의 정보를 보호하기 위한 최선의 노력을 다하고 있습니다.</span>
					        </h2>
		                	<div class="policy" style="padding-top: 5px;">
		
							   <!-- 이용약관 리스트 -->
							   <div id="tab_using1"></div>
		
					       <form id="infoForm" name="infoForm"  method="post">
								<c:if test="${stpltSeq eq null}">
		
			                   	<input type="hidden" id="pageNo" name="pageNo" value="${pageInfoBean.pageNo}">
		
						        <!-- 개인정보 처리 방침 리스트 -->
								<div id="tab_using2">
		
			                       	<div class="board_list_table">
				                        <table summary="번호,제목,등록일로 이루어진 정보 테이블">
		
				                        	<caption class="mypage_title_sub text_align_left">
				                        		<span class="search_box">
		
												</span>
				                        	</caption>
		
				                        	<colgroup>
									            <col width="100px" />
									            <col />
									            <col width="150px" />
									            <col width="150px" />
									        </colgroup>
		
				                        	<thead id="privaTit">
												<tr>
													<th scope="col">번호</th>
													<th scope="col">제목</th>
													<th scope="col">등록일</th>
												</tr>
				                        	</thead>
		
				                        	<tbody>
				                        		<!-- 개인 정보 게시판 리스트 -->
				                        		<c:forEach var="privacyItem" items="${privacyList}" varStatus="status">
					                        		<tr >
					                        			<td>${pageInfoBean.totalCount - ((pageInfoBean.pageNo-1) * pageInfoBean.recordCount) - status.index}</td>
					                        			<td class="subject">
					                        			  <a href="javascript:void(0)" onclick="javascript:goUpdate('${privacyItem.stpltSeq}','${pageInfoBean.pageNo}');"  >${privacyItem.stpltTitle} </a>
					                        			</td>
					                        			<td>${privacyItem.cretDt}</td>
					                        		</tr>
					                        	</c:forEach>
		
					                        	<!-- 게시물이 없을때 -->
								           		<c:if  test="${privacyListSize eq 0}">
									           		<tr style="cursor:auto" class="no_list">
									           			<td colspan="3">등록된 게시물이 없습니다.</td>
									           		</tr>
								           		</c:if>
				                        	</tbody>
				                        </table>
				                    </div>
		
									<!-- 페이징 -->
									<div class="board_list_table_paging">
										<nmcp:pageInfo pageInfoBean="${pageInfoBean}"	function="pageInfo" />
									</div>
								</div>
								</c:if>
		
								<c:if test="${isDetail}">
			 						<!-- 개인정보 처리 상세보기 -->
				                    <div class="tab_using33">
										<div class="policy_Detail">
		                                    ${viewResult.stpltSbst}
		
											<div class="board_list_table">
												<table>
													<colgroup>
														<col width="10%" />
														<col />
														<col width="10%" />
													</colgroup>
														<c:choose>
															<c:when test="${viewResult.preSeq ne null}">
																<tr>
																	<td class="text_align_center">이전<img src="/resources/images/front/icon_board_prev.png" alt="이전"/></td>
																	<td><a href="javascript:void(0);" onclick="javascript:goNext('${viewResult.preSeq}');">${viewResult.preTitle}</a></td>
																	<td>${viewResult.preCretDt}</td>
																</tr>
															</c:when>
															<c:otherwise></c:otherwise>
														</c:choose>
		
														<c:choose>
															<c:when test="${viewResult.nextSeq ne null}">
																<tr >
																	<td class="text_align_center">다음<img src="/resources/images/front/icon_board_next.png" alt="다음"/></td>
																	<td><a href="javascript:void(0);" onclick="javascript:goNext('${viewResult.nextSeq}');">${viewResult.nextTitle}</a></td>
																	<td>${viewResult.nextCretDt}</td>
																</tr>
															</c:when>
															<c:otherwise></c:otherwise>
														</c:choose>
												</table>
												<button type="button" class="btn_right_white float_right" onclick="pageInfo('${pageNo}');">목록</button>
											</div>
										</div>
									</div>
								</c:if>
							</form>
		
							<form id="boardFrm" name="boardFrm"  method="post">
			                  	 <input type="hidden" id="stpltSeq" name="stpltSeq" value="${stpltSeq}">
			                  	 <input type="hidden" id="pageNo2" name="pageNo" class="pageNo" value="${pageInfoBean.pageNo}">
							</form>
							</div>
		                </div>
		            </div>
		        </div>
		  
		    </div> 	  		

		</div>
      </div>



    </t:putAttribute>
</t:insertDefinition>