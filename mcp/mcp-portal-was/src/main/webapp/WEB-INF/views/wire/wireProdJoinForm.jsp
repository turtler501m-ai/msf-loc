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
	<div class="dim-layer blkPopup" style="display:block; width:740; overflow-y:auto">
		<div id="joinLayer" class="pop-layer" style="left:0;top:0;">
        	<div class="pop-container">
	            <div class="pop-conts">
	
		           	<div class="popup_top2">
		           		<h4 class="popup_title_h4" id="popupTitle">간편가입상담</h4>
		            </div>
		            
		            <div class="popup_content" style="padding-bottom: 15px;padding-top: 15px;">
		                <form id="joinFrm"> 
		                <input type="hidden" id="wireProdCd" value="${pWireProdCd}"/>
		                <input type="hidden" id="pJoinProdCorp" value="${pJoinProdCorp}"/>
		                <input type="hidden" id="pJoinProdCd" value="${pJoinProdCd}"/>
		                <input type="hidden" id="pJoinProdDtlSeq" value="${pJoinProdDtlSeq}"/>   		
						<!-- popContents -->
						<div class="popContents">
							<!-- simpleJoin -->
							<div class="simpleJoin">
								<div style="background: #f9f9f9;border: 1px solid #e4e4e4;padding: 10px 30px;">
								   <span>가입신청을 위한 간단한 정보를 입력해주시면, <br>
									해피콜 상담을 통해 희망 상품과 다양한 혜택에 대한 상세 안내를 도와드리겠습니다.<br>
									신청/상담 외 목적으로 활용되지 않습니다.
									</span>
								</div>
								<p class="subjectTxt">
									<b>신청내용</b>
									<span id="joinAmtDesc">(3년 약정 기준)</span>
								</p>
								<form id="frm">
								<!-- lineTable -->
								<div class="lineTable">
									<table summary="신청내용의 관심상품, 성명, 연락처, 메모, 보안문자에 관한 입력 표 입니다.">
										<caption>신청내용</caption>
										<colgroup>
											<col width="25%">
											<col width="">
										</colgroup>
										<tbody>
											<tr>
												<th scope="row" rowspan="2" class="vaT">
													<label for="business">
														<span>
															<span class="compulsory">*</span> 
															관심상품
														</span>
													</label>
												</th>
												<td>
													<div class="wishPdt">
														<span class="comm_sel">
															<label for="joinCorp" class="hidden">사업자</label>
															<c:set var="wireProdCorpList" value="${nmcp:getCodeList(Constants.WIRE_PROD_CORP)}" />
															<select id="joinCorp" name="joinCorp">
																<option value="">- 사업자 -</option>
																<c:forEach items="${wireProdCorpList}" var="item">
	                         										<option value="${item.dtlCd}">${item.dtlCdNm }</option>
	                       										</c:forEach>
															</select>
														</span>
		
														<span class="comm_sel">
															<label for="joinProdCd" class="hidden">관심상품</label>
															<select id="joinProdCd" name="joinProdCd">
																<option>- 관심상품 -</option>
															</select>
														</span>
		
														<span class="comm_sel">
															<label for="joinProdDtlSeq" class="hidden">상품명</label>
															<select id="joinProdDtlSeq" name="joinProdDtlSeq">
																<option>- 상품명 -</option>
															</select>
														</span>
													</div>
												</td>
											</tr>
											
											<tr>
												<td id="joinProdInfo">													
													<div class="wishPdtBox">
														<div class="pdtInfo" id="joinSpeed">
															<p class="pdtTxt">
																<b>인터넷 속도</b>
															</p>
															<strong class="addTxt">1GBbps</strong>
														</div>
		
														<div class="pdtInfo" id="joinChn">
															<p class="pdtTxt">
																<b>TV채널 수</b>
															</p>
															<strong class="addTxt">총250개</strong>
														</div>
		
														<div class="pdtInfo" id="joinAmt">
															<p class="pdtTxt">
																<b>월 납부금액</b>
																<span>(VAT포함)</span>
															</p>
															<strong class="addTxt">34,000원</strong>
														</div>
		
														<div class="pdtInfo lastChd" id="joinFreebies">
															<p class="pdtTxt">
																<b>사은품</b>
															</p>
															<strong class="addTxt">10만원</strong>
														</div>
														
													</div>
													
												</td>
											</tr>
		
											<tr>
												<th scope="row">
													<label for="usrNm">
														<span>
															<span class="compulsory">*</span> 
															성명
														</span>
													</label>
												</th>
												<td>
													<div class="nameGrp">
														<input type="text" class="inputTxt" id="counselNm" name="counselNm" maxlength="15" title="이름 입력칸">
													</div>
												</td>
											</tr>
		
											<tr>
												<th scope="row">
													<label for="usrPhone01">
														<span>
															<span class="compulsory">*</span> 
															연락처
														</span>
													</label>
												</th>
												<td>
													<div class="usrPhoneGrp">
														<input type="text" class="inputTxt onlyNum" id="cstmrTelFn" name="cstmrTelFn" maxlength="3" title="전화번호 첫번째 입력칸">
														<span>-</span>
														<input type="text" class="inputTxt onlyNum" id="cstmrTelMn" name="cstmrTelMn" maxlength="4" title="전화번호 가운데 입력칸">
														<span>-</span>
														<input type="text" class="inputTxt onlyNum" id="cstmrTelRn" name="cstmrTelRn" maxlength="4"  title="전화번호 마지막 입력칸">
													</div>
												</td>
											</tr>
		
											<tr>
												<th scope="row">
													<label for="counselMemo">
														<span>
															<!-- <span class="compulsory">*</span>  -->
															메모
														</span>
													</label>
												</th>
												<td>
													<div class="memoTxtArea">
														<textarea name="counselMemo" id="counselMemo" class="txtarea" cols="30" rows="2" maxlength="2000" placeholder="원활한 상담을 위해 미리 전달하실 내용이 있으시면 자유롭게 기재해 주세요.&#13;&#10;ex) 평일 오후 2시~5시 사이에 연락 부탁드려요."></textarea>
													</div>
												</td>
											</tr>
		
											<tr>
												<th scope="row">
													<label for="secretInp">
														<span>
															<span class="compulsory">*</span> 
															보안문자
														</span>
													</label>
												</th>
												<td>
													<div class="secretBox">
														<div class="secretFgr" id="catpcha">
											
														</div>
														<div class="secretBtn">
															<a href="#" id="soundOnKor"><img src="/resources/images/wire/btn_sound.png" alt="음성듣기"></a>
															<a href="#" id="reLoad" class="lastEl"><img src="/resources/images/wire/btn_refresh.png" alt="새로고침"></a>
														</div>
														<div class="secretInpGrp">
															<input type="text" class="inputTxt" id="secureChr" name="secureChr" maxlength="6" title="보안문자 입력칸" value="" placeholder="보안문자 입력">
														</div>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<!-- //lineTable -->
								</form>
		
								<p class="subjectTxt">
									<b>개인정보 수집/이용 동의</b>
								</p>
		
								<!-- lineTable -->
								<div class="lineTable type2">
									<table summary="개인정보 수집/이용 동의의 목적, 항목, 보유기간에 관한 표 입니다.">
										<caption>개인정보 수집/이용 동의</caption>
										<colgroup>
											<col width="33%">
											<col width="33%">
											<col width="33%">
										</colgroup>
										<thead>
											<tr>
												<th scope="row">목적</th>
												<th scope="row">항목</th>
												<th scope="row">보유기간</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>상품 신청 및 상담</td>
												<td>신청인(대리인)의 성명,<br> 연락처, 신청상품</td>
												<td>수집 및 이용목적 달성 시<br> 24시간 이내 파기</td>
											</tr>
										</tbody>
									</table>
								</div>
								<!-- //lineTable -->
		
								<p class="tabBtmMsg">
									<span class="check">
										<input type="checkbox" id="acceptTxt" name="acceptTxt">
										<label for="acceptTxt">(필수)본인은 상기 내용에 대하여 내용을 읽어보았으며 이에 동의합니다.</label>
									</span>
								</p>
		
								<div class="popBtnCt">
									<a href="#" id="layerJoinBtn" class="btnRed">신청</a>
								</div>
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