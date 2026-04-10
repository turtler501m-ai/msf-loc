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
    	
    	<script type="text/javascript" src="/resources/js/common/validator.js"></script>
    	<script type="text/javascript" src="/resources/js/wire/wireProdList.js"></script>
    	
		<c:choose>
			<c:when test="${wireProdCd eq 'CCTV01'}">
				<t:putAttribute name="titleAttr"> CCTV - kt M모바일  </t:putAttribute>
				<script>var menuCode = "WR0401";</script>
			</c:when>
			<c:when test="${wireProdCd eq 'CB01'}">
				<t:putAttribute name="titleAttr"> 결합상품 - kt M모바일  </t:putAttribute>
				<script>var menuCode = "WR0201";</script>
			</c:when>
			<c:when test="${wireProdCd eq 'AD01'}">
				<t:putAttribute name="titleAttr"> 부가서비스 - kt M모바일  </t:putAttribute>
				<script>var menuCode = "WR0601";</script>
			</c:when>
			<c:when test="${wireProdCd eq 'ITTV01'}">
				<t:putAttribute name="titleAttr"> 인터넷/TV - kt M모바일  </t:putAttribute>
				<script>var menuCode = "WR0301";</script>
			</c:when>
		</c:choose>
			 	
    	<script type="text/javascript">
	    
		</script>
    </t:putAttribute>
    
    <t:putAttribute name="contentAttr">	
  	  <div class="contentsWrap subPage">
    		<input type="hidden" id="wireProdCd" value="${wireProdCd}"/> 	

			<c:if test="${not empty topRollBannerDtoList}">
				<!-- 배너슬라이드 -->
				<div class="wireBnr">				
					<div id='slider' class='swipe'>				  
						<div class='swipe-wrap'>				
							<c:forEach items="${topRollBannerDtoList}" var="topRollBanner">
								<div class="clri">
									<c:choose>
										<c:when test="${not empty topRollBanner.rebuildLinkl}">
											<c:set var="target">parent.location.href='${topRollBanner.rebuildLinkl}'</c:set>						
											<c:if test="${topRollBanner.linkTarget eq 'Y'}">
												<c:set var="target">window.open('${topRollBanner.rebuildLinkl}')</c:set>	
											</c:if>
											<div class="clri" style="width:100%;height:200px;background:url(${topRollBanner.bannImg});background-position-x: 50%; background-position-y: 50%;background-repeat: no-repeat; cursor:pointer" onclick="${target}"><span class="hidden">${topRollBanner.imgDesc}</span></div>
										</c:when>
										<c:otherwise>
											<div class="clri" style="width:100%;height:200px;background:url(${topRollBanner.bannImg});background-position-x: 50%; background-position-y: 50%;background-repeat: no-repeat"><span class="hidden">${topRollBanner.imgDesc}</span></div>
										</c:otherwise>
									</c:choose>
								</div>
							</c:forEach>
						</div>
	
						<c:if test="${fn:length(topRollBannerDtoList) > 1}">
							<div class="pageGrp">
							    <span class="banner_index">
									<c:forEach items="${topRollBannerDtoList}" var="topRollBanner" varStatus="status">
										<button type="button" class="btn_index <c:if test="${status.first}">active</c:if>" id="banner_${status.count}">${status.count}번째 배너</button>
									</c:forEach>
								</span>
								<button type="button" class="btn_stop" style="cursor: Default">정지</button>
								<button type="button" class="btn_start" style="display:none;cursor: Default">재생</button>
							</div>
						</c:if>
						
					</div>
				</div>		
				<!-- //배너슬라이드 -->
			</c:if>
			
			<div class="pageWrap">
				<!-- 결합상품 -->
				<div class="lineBox">
	
					<div class="titleLn">
						<p class="titleTxt">
							<c:set var="titleDefaultTxt">결합으로 가입하시면 더욱 큰 할인을 받으실 수 있습니다.</c:set>
							<c:if test="${wireProdCd eq 'CB01'}">
								<b>결합상품(TV + 인터넷)</b>
								<span class="margin_left_8">${titleDefaultTxt}</span>
							</c:if>
							<c:if test="${wireProdCd eq 'CCTV01'}">
								<b>CCTV</b>
								<span class="margin_left_8">${titleDefaultTxt}</span>							
							</c:if>
							<c:if test="${wireProdCd eq 'ITTV01'}">
								<b>인터넷/TV</b>
								<span class="margin_left_8">${titleDefaultTxt}</span>						
							</c:if>
							<c:if test="${wireProdCd eq 'AD01'}">
								<b>부가 서비스</b>						
							</c:if>
						</p>
						
						<c:if test="${not empty wireProdContDto.wireProdPcSbst}">
							<a href="#" class="btnAllView">전체상품 한눈에 보기</a>
						</c:if>
					
					</div>
	
					<!-- listContents -->
					<div class="listContents">
						<!-- tabMenu -->
						<div class="tabMenu">
							<a href="javascript:void(0);" class="tbaBtn01 on" corpGubun="">전체</a>
							<c:choose>
								<c:when test="${wireProdCd eq 'CCTV01'}">
									<a href="javascript:void(0);" class="tbaBtn04" corpGubun="KTT">kt telecop</a>
								</c:when>
								<c:otherwise>
									<a href="javascript:void(0);" class="tbaBtn03" corpGubun="SKY">skylife</a>
								</c:otherwise>
							</c:choose>
							<a href="javascript:void(0);" class="tbaBtn02" corpGubun="KT">kt</a>
						</div>
						<!-- //tabMenu -->
						
						<c:if test="${wireProdCd ne 'AD01'}">
							<span class="check topR">
								<input type="checkbox" id="pdtCompare" name="pdtCompare">
								<label for="pdtCompare">비교하여 보기</label>
							</span>
						</c:if>
						
						<!-- listBox -->
						<div class="listBox">
							
							<div class="listSort">
								<a href="javascript:void(0);" class="btnSort01 on" sortGubun="rcm"><span>추천 순</span></a>
								<a href="javascript:void(0);" class="btnSort02" sortGubun="amtL"><span>금액 낮은 순</span></a>
								<a href="javascript:void(0);" class="btnSort03" sortGubun="amtH"><span>금액 높은 순</span></a>
								<a href="javascript:void(0);" class="btnSort04" sortGubun="chn"><span>채널 많은 순</span></a>
								<a href="javascript:void(0);" class="btnSort05" sortGubun="spd"><span>속도 빠른 순</span></a>
							</div>
	
							<!-- listSection -->
							<ul class="listSection" id="prodList">
							
							</ul>
							<!-- //listSection -->
						</div>
						<!-- //listBox -->
	
					</div>
					<!-- //listContents -->
				</div>
				<!-- //결합상품 -->
			</div>

		</div>
   		
	<!-- 레이어 팝업 -->
    <div class="dim-layer">
    	<div class="dimBg"></div>
	     	
     	<!-- 전체 상품 한눈에 보기 -->    
		<div id="allViewLayer" class="pop-layer_red" style="display: block; left:0; top:0;">
			<div class="pop-container">
				<div class="red_top">
					<h4 class="red_title ftSize">${wireProdContDto.wireProdTitle}</h4>
				</div>
	
				<div class="popup_content">				
					${wireProdContDto.wireProdPcSbst}    
				</div>
			</div>
			<a href="#" class="popup_cancel">
				<img src="/resources/images/wire/btn_red_x.png" alt="닫기"/>
			</a>
		</div>
		
		<!-- 전체 상품 한눈에 보기 -->
		
		<!-- 상세 보기 -->
		<div id="detailViewLayer" class="pop-layer_red" style="display: block; left:0; top:0;">
			<div class="pop-container">
				<div class="red_top">
					<h4 class="red_title" id="detailViewLeftTitle">skylife</h4>
					<span class="rTitle" id="detailViewRightTitle">인터넷 단독상품</span>
				</div>
	
				<div class="popup_content" id="detailViewContent">				
				
				</div>
			</div>
			<a href="#" class="popup_cancel">
				<img src="/resources/images/wire/btn_red_x.png" alt="닫기"/>
			</a>
		</div>
		<!-- 상세 보기 -->
	
	</div>
	<!-- 레이어 팝업 -->
	
	
	

	


    </t:putAttribute>
</t:insertDefinition>