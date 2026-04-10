<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutWireDefault">
	
    <t:putAttribute name="scriptHeaderAttr">
    		
    		<script type="text/javascript" src="/resources/js/common/validator.js"></script>
    		<script type="text/javascript" src="/resources/js/wire/wireMain.js"></script>	
            
            <script type="text/javascript">
		    		        	
		    </script>
    </t:putAttribute>
    
    <t:putAttribute name="contentAttr">		
    	<div class="contentsWrap">
			<!-- 배너슬라이드 -->
			<div class="wireBnr">				
				<div id='slider' class='swipe'>		
						  
					<div class='swipe-wrap'>				
						<c:forEach items="${topRollBannerDtoList}" var="topRollBanner">
							<c:choose>
								<c:when test="${not empty topRollBanner.rebuildLinkl}">
									<c:set var="target">parent.location.href='${topRollBanner.rebuildLinkl}'</c:set>						
									<c:if test="${topRollBanner.linkTarget eq 'Y'}">
										<c:set var="target">window.open('${topRollBanner.rebuildLinkl}')</c:set>	
									</c:if>
									<div class="clri" style="width:100%;height:362px;background:url(${topRollBanner.bannImg});background-position-x: 50%; background-position-y: 50%;background-repeat: no-repeat; cursor:pointer" onclick="${target}"><span class="hidden">${topRollBanner.imgDesc}</span></div>
								</c:when>
								<c:otherwise>
									<div class="clri" style="width:100%;height:362px;background:url(${topRollBanner.bannImg});background-position-x: 50%; background-position-y: 50%;background-repeat: no-repeat"><span class="hidden">${topRollBanner.imgDesc}</span></div>
								</c:otherwise>
							</c:choose>	
						</c:forEach>
					</div>
					<a class="arrow_left" href="javascript:void(0);" >이전 배너 보기</a>
					<a class="arrow_right" href="javascript:void(0);" >다음 배너 보기</a>
					<div class="pageGrp">
					    <span class="banner_index">
							<c:forEach items="${topRollBannerDtoList}" var="topRollBanner" varStatus="status">
								<button type="button" class="btn_index <c:if test="${status.first}">active</c:if>" id="banner_${status.count}">${status.count}번째 배너</button>
							</c:forEach>
						</span>
						<button type="button" class="btn_stop" style="cursor: Default">정지</button>
						<button type="button" class="btn_start" style="display:none;cursor: Default">재생</button>
					</div>
				</div>
			</div>

			<!-- wireMdContnt -->
			<div class="wireMdContnt">
				<h2 class="wireTitle hidden">결합 / 인터넷 / TV 원하시는 상품을 선택 해 보세요</h2>

				<div class="wirePdtList">
					<%-- 배너가 여러개 등록되어도 3개만 노출됨 --%>
					<c:set var="doneLoop" value="false"/> 
					<c:forEach items="${recomBannerDtoList}" var="recomBanner" varStatus="status">
						<c:if test="${not doneLoop}"> 
							<a href="${recomBanner.rebuildLinkl}" ${recomBanner.newTarget} <c:if test="${status.last or status.count == 3}">class="lastCld"</c:if>><img src="${recomBanner.bannImg}" alt="${recomBanner.imgDesc}"></a>
							<c:if test="${status.count == 3}"> 
								<c:set var="doneLoop" value="true"/>
							</c:if>
						</c:if>
					</c:forEach>
				</div>

				<div class="wireMainAd">
					<div class="imgAd">
						<%-- 배너가 여러개 등록되어도 한개만 노출됨 --%>
						<c:set var="evtBnLoop" value="false"/> 
						<c:forEach items="${eventBannerDtoList}" var="eventBanner" varStatus="status">
							<c:if test="${not evtBnLoop}"> 	
								<a href="${eventBanner.rebuildLinkl}" ${eventBanner.newTarget}><img src="${eventBanner.bannImg}" alt="${eventBanner.imgDesc}"></a>
								<c:if test="${status.count == 1}"> 
									<c:set var="evtBnLoop" value="true"/>
								</c:if>
							</c:if>
						</c:forEach>
					</div>

					<div class="wireContact">
						<p class="contactTitle">가입문의</p>

						<div class="contactInfo">
							<p class="tellN">
								<b>1899 - 0851</b>
								<span>(월~금 09:00~18:00)</span>
							</p>
							<p class="contactMsg">
								21년 2월 3일 이전 접수된 상담 문의만 가능<br>
                                    ※ 신규 가입 문의 : skylife 고객센터(1588-9944)
							</p>

							<a href="#" class="joinBtn hidden"><span>가입상담 신청하기</span></a>
						</div>
					</div>
				</div>
			</div>
			<!-- //wireMdContnt -->
			
			<!-- wireBtmContnt -->
			<div class="wireBtmContnt">
				<div class="wireJoinStep">
					<h2 class="wireTitle">
						상품가입 및 설치 안내 
						<!-- <span class="addMsg">(상품가입 및 설치 안내입니다.)</span> -->
					</h2>

					<ul class="stepList">
						<li>
							<strong class="stepListTitle">상품 선택하기</strong>
							<div class="stepListSmry wireStep01">
								필요한 상품<br>
								(인터넷, TV, 전화) 선택,<br>
								통신사, 사은품 선택
							</div>
						</li>
						<li>
							<strong class="stepListTitle">가입 신청하기</strong>
							<div class="stepListSmry wireStep02">
								가입상담 신청하기 클릭<br>
								또는<br>
								1899-0851 로 신청
							</div>
						</li>
						<li>
							<strong class="stepListTitle">해피콜 후 전산등록</strong>
							<div class="stepListSmry wireStep03">
								전담 상담원과<br>
								통화 후<br>
								신청서 전산 등록 완료
							</div>
						</li>
						<li>
							<strong class="stepListTitle">기사 방문 설치</strong>
							<div class="stepListSmry wireStep04">
								고객님 지역의<br>
								설치 기사가 해피콜 후<br>
								방문하여 설치
							</div>
						</li>
						<li class="lastCld">
							<strong class="stepListTitle">사은품 수령</strong>
							<div class="stepListSmry wireStep05">
								개통완료후<br>
								정해진 날짜에<br>
								사은품 증정
							</div>
						</li>
					</ul>					
				</div>
			</div>
			<!-- //wireBtmContnt -->			
		</div>	
		
  			 	   
    </t:putAttribute>
</t:insertDefinition>