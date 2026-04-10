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
    	<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>		
		<!-- 페이스북 Open Graph Tag -->
		<meta content="1694564987490429" property="fb:app_id">
		<meta content="website" property="og:type" />
		<meta content="${eventDto.ntcartSubject}" property="og:title" />
		<meta content="https://www.ktmmobile.com/wire/eventDetail.do?ntcartSeq=${eventDto.ntcartSeq}" property="og:url" />
		<meta content="${eventDto.snsSbst}" property="og:description" />
		<meta content="https://www.ktmmobile.com${eventDto.listImg}" property="og:image" />
		<!--// 페이스북 Open Graph Tag -->
		<!-- twitter 관련 메타테그 -->
		<meta name="twitter:url" content="https://www.ktmmobile.com/wire/eventDetail.do?ntcartSeq=${eventDto.ntcartSeq}" />  <!-- 트위터 카드를 사용하는 표시하고싶은URL -->
		<meta name="twitter:title" content="${eventDto.ntcartSubject}" />  <!-- 트위터 카드에 나타날 웹 사이트의 제목 -->
		<meta name="twitter:description" content="${eventDto.snsSbst}" />  <!-- 트위터 카드에 나타날 요약 설명 -->
		<meta name="twitter:site" content="kt M mobile" />  <!-- 트위터 카드에 사이트 배포자 트위터아이디 -->
		<meta name="twitter:creator" content="kt M mobile" />  <!-- 트위터 카드에 배포자 트위터아이디 -->
        
        <script type="text/javascript">
       		
        	var menuCode= "WR0701";
	        $(document).ready(function() {
	        	
	        	 $("a._Detail").click(function() {
	 	            var ntcartSeq = $(this).attr("ntcartSeq");
	 	            $("#ntcartSeq").val(ntcartSeq);

	 	            $("form[name='frm']").attr("action", "/wire/eventDetail.do");
	 	            $("form[name='frm']").submit();
	 	        });
	           
	        });
	        
	        function goList() {
	        	$('#frm').attr('action','/wire/eventList.do');
	        	$("#frm").submit();
	        }
	        
	        var winnerDetail = function() {
	        	$("#frm").attr("action", "/wire/winnerDetail.do");
	        	$("#frm").submit();
	        }		  
	        
	        function facebook_share(){
	        	var url = "https://www.ktmmobile.com/wire/eventDetail.do?ntcartSeq=${eventDto.ntcartSeq}&sbstCtg=${eventDto.sbstCtg}&pageNo=1";
	        	var image = "https://www.ktmmobile.com${eventDto.listImg}";
	        	var snsUrl = "https://www.facebook.com/sharer/sharer.php?u="+encodeURIComponent(url)

	        	var popup= window.open(snsUrl, "_snsPopupWindow", "width=500, height=500, menubar=yes, status=yes, toolbar=yes, resizable=yes");
	        	popup.focus();
	        }

	        function twitter_share(){
	        	var url = "https://www.ktmmobile.com/wire/eventDetail.do?ntcartSeq=${eventDto.ntcartSeq}&sbstCtg=${eventDto.sbstCtg}&pageNo=1";
	        	var snsUrl = "https://twitter.com/home?status="+encodeURIComponent('${eventDto.ntcartSubjectRepace}')+"&url="+ url;
	        	var popup = window.open(snsUrl, "_snsPopupWindow", "width=500, height=500, menubar=yes, status=yes, toolbar=yes, resizable=yes");
	        	popup.focus();
	        }

	        Kakao.init('1a4c3982100206d74948a22ec2bb85d8');
	        function kakaostory_share(){
	        	var title = '${eventDto.snsSbst}';
	            title = title.substring(0,100);

	            title = title.replace(/\&(amp;)?/ig, ""); // 카스인경우 &amp; 등록시 글자 짤림

	        	var url = "https://www.ktmmobile.com/wire/eventDetail.do?ntcartSeq=${eventDto.ntcartSeq}&sbstCtg=${eventDto.sbstCtg}&pageNo=1";
	        	var snsUrl = "https://story.kakao.com/share?url="+encodeURIComponent(url);

	        	var popup= window.open(snsUrl, "_snsPopupWindow", "width=500, height=500, menubar=yes, status=yes, toolbar=yes, resizable=yes");
	        	popup.focus();
	        }
	        
	        function openSimpleJoinForm(joinProdCorp, joinProdCd, joinProdDtlSeq){

	    		window.open("","wireProdJoinForm","width=740,height=750,scrollbars=yes");

	            ajaxCommon.createForm({
	                method:"post"
	               ,action:"/wire/wireProdJoinForm.do" 
	               ,target :"wireProdJoinForm" 
	             });
	            
	            ajaxCommon.attachHiddenElement("joinProdCorp",joinProdCorp);
	            ajaxCommon.attachHiddenElement("joinProdCd",joinProdCd);
	            ajaxCommon.attachHiddenElement("joinProdDtlSeq",joinProdDtlSeq);
	            ajaxCommon.formSubmit();
	        }
	        
	     </script>
    </t:putAttribute>
    
    <t:putAttribute name="contentAttr">
     <div class="contentsWrap subPage">
    		<div class="pageWrap">	
		   	<form name="frm" id="frm" method="post" action="">
				<input type="hidden" name="pageNo" id="pageNo" value="${pageInfoBean.pageNo}"/>
				<input type="hidden" name="sbstCtg" id="sbstCtg" value="${eventDto.sbstCtg}"/>
				<input type="hidden" name="ntcartSeq" id="ntcartSeq" value="${eventDto.ntcartSeq}"/>
				<input type="hidden" name="winnerYn" id="winnerYn" value="${eventDto.winnerYn}"/>		<!-- 이벤트의 해당 당첨자 발표가 있을경우만 값이 있음 -->
				<input type="hidden" name="searchValue" id="searchValue" value="${searchDto.searchValue}"/>
				<input type="hidden" name="ntcartSbst" id="ntcartSbst" value="${searchDto.searchValue}"/>
				<input type="hidden" name="searchYn" id="searchYn" value="${searchDto.searchYn}"/>	
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
		                    	<div class="title">${eventDto.ntcartSubject}</div>
		                    	<div class="period">
		                    		
		                    		${eventDto.eventStartDt} ~ 
                                         ${eventDto.eventEndDt}
                                    <c:if test="${elist.dDay >= 0}">(D-${elist.dDay})</c:if>
                                    <c:if test="${elist.dDay < 0}">(종료)</c:if>
                                     &nbsp;&nbsp;|&nbsp;&nbsp; 조회 <fmt:formatNumber value="${eventDto.ntcartHitCnt}" pattern="#,###" />
									
									
									<c:if test="${!empty eventDto.winnerYn}">
		                    			<button type="button" class="btn_right_black margin_left_20" onclick="winnerDetail();">당첨자 발표</button>
		                    		</c:if>
		
		                    	</div>
		                   		<div class="sns" style="margin-top: -40px;">
		                   			<a href="javascript:facebook_share();" title="새창-facebook">
										<img src="/resources/images/front/icon_facebook.png"  id="facebook" alt="facebook-새창">
									</a>
									<a href="javascript:twitter_share();"  title="새창-twitter">
										<img src="/resources/images/front/icon_twitter.png"  id="twitter" alt="twitter-새창">
									</a>
									<a href="javascript:kakaostory_share();" title="새창-kakaostory">
										<img src="/resources/images/front/icon_kakaoStory.png"  alt="kakaostory-새창">
									</a>
								</div>
		                  		</div>
		
		                    <div class="eventList border_top_black" style="margin-top:0px">
		                    	<div>${eventDto.ntcartSbst}</div>
		                    </div>
							
							<c:if test="${not empty planBasDto }">
								<c:forEach items="${planBasDto}" var="pb" >	<!-- 기획전내의 소제목 리스트 -->
		                   			<c:if test="${not empty pb.prodList}">
										<div class="event_item">${pb.plnTitle}</div>
										<div class="list">
											<c:forEach items="${pb.prodList}" var="pl">	<!-- 기획전 소제목내의 제품리스트 -->
												<a href="/product/phone/phoneView.do?prodId=${pl.plnProdId}">
													<div class="item" >
														<div class="img">
															<img src="${pl.imgPath}" alt="${pl.prodNm}">
														</div>
					
														<div class="title">
															${pl.prodNm}
														</div>
					
														<div class="script">
															<span>${pl.plnShowWrdn1}</span>
														</div>
													</div>
												</a>
											</c:forEach>	<!--// 기획전 소제목내의 제품리스트 -->
										</div>	<!-- //class='list' -->
									</c:if>
								</c:forEach>	<!--// 기획전내의 소제목 리스트 -->
							</c:if>
							
							<div class="event_notice border_bottom_d3">${eventDto.plnContent}</div>
								<div class="board_list_table">
									<table>
										<colgroup>
											<col width="80px" />
											<col />
											<col width="100px" />
										</colgroup>
		
										<c:if test="${eventDto.preSeq ne ''}">
											<tr>
												<td class="text_align_center">이전 <img src="/resources/images/front/icon_board_prev.png" alt="이전"/></td>
												<td class="subject"><a href="javascript:void(0)" class="_Detail" ntcartSeq="${eventDto.preSeq}" >${eventDto.preSubject}</a></td>
												<td><fmt:formatDate value="${eventDto.preDt}" pattern="yyyy.MM.dd"/></td>
											</tr>
										</c:if>
										<c:if test="${eventDto.nextSeq ne ''}">
											<tr>
												<td class="text_align_center">다음 <img src="/resources/images/front/icon_board_next.png" alt="다음"/></td>
												<td class="subject"><a href="javascript:void(0)" class="_Detail"  ntcartSeq="${eventDto.nextSeq}" >${eventDto.nextSubject}</a></td>
												<td><fmt:formatDate value="${eventDto.nextDt}" pattern="yyyy.MM.dd"/></td>
											</tr>
										</c:if>
									</table>
								</div>
		               		<div class="text_align_right"><button type="button" class="btn_right_white " onclick="goList();">목록</button></div>
		              		</div>
		               </div>
		           </div>
		       </div>
		   </div>
    </div>
    </div>
    </t:putAttribute>
</t:insertDefinition>