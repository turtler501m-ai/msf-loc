<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un"
	uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
 
<c:forEach var="button" items="${buttonXmlList}" varStatus="status">
<!-- 첫번째 버튼  is-active 추가-->
	<c:choose>
		<c:when test="${status.count == 1}">
			<button id="subTab_${status.count}" data-rateAdsvcCtgCd="${button.rateAdsvcCtgCd}" class="c-tabs__button is-active" onclick="subTabClick('${button.rateAdsvcCtgCd}')">${button.rateAdsvcCtgNm}</button>	
		</c:when>
		<c:otherwise>
			<button id="subTab_${status.count}" data-rateAdsvcCtgCd="${button.rateAdsvcCtgCd}" class="c-tabs__button" onclick="subTabClick('${button.rateAdsvcCtgCd}')">${button.rateAdsvcCtgNm}</button>
		</c:otherwise>
	</c:choose>
</c:forEach>

<input type="hidden" name="upRateAdsvcCtgCd" id="upRateAdsvcCtgCd" value="${upRateAdsvcCtgCd}">
	