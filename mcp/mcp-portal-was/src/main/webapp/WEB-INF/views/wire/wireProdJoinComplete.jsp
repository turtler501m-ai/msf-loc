<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutWirePopupDefault">
	
    <t:putAttribute name="scriptHeaderAttr">
    	<script type="text/javascript" src="/resources/js/common/validator.js"></script>
    	<script type="text/javascript" src="/resources/js/wire/wireProdJoinForm.js"></script>
    </t:putAttribute>
    
    <t:putAttribute name="contentAttr">
		<!-- 신청하기 -->
	<div class="dim-layer blkPopup" style="display:block; width:720px; height: 820px">
		<div id="joinLayer" class="pop-layer" style="left:0;top:0;">
        	<div class="pop-container">
	            <div class="pop-conts">
	
		           	<div class="popup_top2">
		           		<h4 class="popup_title_h4" id="popupTitle">간편가입상담</h4>
		            </div>
		            
		            <div class="popup_content" style="padding-bottom: 15px;padding-top: 15px;  width : 665px;">
		                <form id="joinFrm"> 
		                <input type="hidden" id="wireProdCd" value="${wireProdCd}"/>
		                <input type="hidden" id="pJoinProdCorp" value="${pJoinProdCorp}"/>
		                <input type="hidden" id="pJoinProdCd" value="${pJoinProdCd}"/>
		                <input type="hidden" id="pJoinProdDtlSeq" value="${pJoinProdDtlSeq}"/>   		
						<!-- popContents -->
						<div class="popContents">
							<!-- simpleJoin -->
							<div class="simpleJoin">
								<form id="frm">
								<!-- lineTable -->
								<div class="lineTable">
									<table summary="신청내용의 관심상품, 성명, 연락처, 메모, 보안문자에 관한 입력 표 입니다.">
										<caption>신청내용</caption>
										<colgroup>
											<col width="100%">
										</colgroup>
										<tbody>
											<tr>
												<th class="vaT" style=" height: 600px;">
													<label for="business" style="text-align: center;">
														<span style="font-size:27px; display: block; margin-left: auto; margin-right: auto; top: 50%; transform: translateY(250%);">
<!-- 														<span class="completeTxt"> -->
															간편가입 상담신청에 감사 드립니다. <br/>
															고객센터(1899-0851)에서 연락드릴 예정입니다.<br/>
    														고객님의 관심에 다시 한 번 감사 드립니다.<br/>
														</span>
													</label>
												</th>
											</tr>
										</tbody>
									</table>
								</div>
								<!-- //lineTable -->
								</form>
							</div>
							<!-- //simpleJoin -->
						</div>
						<!-- //popContents -->
						</form>
		
		             </div>
		
		         </div>
	         </div>
     	</div>
     	</div>
     	<!-- 신청하기 -->
     	</t:putAttribute>
</t:insertDefinition>