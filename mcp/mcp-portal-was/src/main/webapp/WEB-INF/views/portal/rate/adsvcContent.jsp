<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<%-- 부가서비스 상세내용 --%>
<div class="c-extraservice--box">
	<c:if test="${!empty contentXmlList}">
		<ul>	
			<c:forEach var="content" items="${contentXmlList}" varStatus="status">
				<li>${content.rateAdsvcItemNm}
					${content.rateAdsvcItemSbst}
				</li>
			</c:forEach>
		</ul>
	</c:if>
	<c:if test="${empty contentXmlList}">
		<ul>
			<li>상세정보가 없습니다.</li>
		</ul>	
	</c:if>
	<c:if test="${(prodInfo.selfYn eq 'Y') && (btnYn eq 'Y')}">
		<div style="display: flex; justify-content: flex-end;">
			<button id="adsvcJoin${prodInfo.rateAdsvcGdncSeq}" class="c-button c-button--sm c-button--white c-button-round--sm u-mt--12" onclick="adsvcJoinPop('${prodInfo.rateAdsvcGdncSeq}')">부가서비스 신청</button>	
		</div>
	</c:if>
</div>