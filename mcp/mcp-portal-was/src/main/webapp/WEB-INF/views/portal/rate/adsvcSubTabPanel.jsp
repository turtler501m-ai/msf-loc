<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
 
 <%-- 부가서비스 서브탭 --%>
 <c:if test="${fn:length(buttonXmlList) > 1}">
 	<button id="subTab_${status.count}" role="tab" aria-selected="true" class="c-tabs__button is-active" onclick="subAllTabClick('${buttonRateAdsvcCtgCd}', '전체')">전체</button>
 	<c:forEach var="button" items="${buttonXmlList}" varStatus="status">
 		<button id="subTab_${status.count}" role="tab" aria-selected="false" class="c-tabs__button" onclick="subTabClick('${button.rateAdsvcCtgCd}', '${button.rateAdsvcCtgNm}')">${button.rateAdsvcCtgNm}</button>
	</c:forEach>	
 </c:if>
 	