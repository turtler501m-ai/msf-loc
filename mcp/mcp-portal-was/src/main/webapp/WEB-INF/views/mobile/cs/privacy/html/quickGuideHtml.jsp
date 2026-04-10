<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="platFormCd" value="${nmcp:getPlatFormCd()}" />
<c:choose>
	<c:when test="${platFormCd eq 'A'}">
		<h3 class="quick-guide__tit">보이스 피싱 예방</h3>
		<div class="embed-youtube">
		    <iframe src="https://www.youtube.com/embed/hI4CGX0o_Lo" title="보이스 피싱 예방 가이드 동영상" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"></iframe>
		</div>
		<h3 class="quick-guide__tit">스마트폰 결제 피해 예방</h3>
		<div class="embed-youtube">
		    <iframe src="https://www.youtube.com/embed/IYfFQHuz7sY" title="스마트폰 결제 피해 예방 가이드 동영상" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"></iframe>
		</div>
	</c:when>
	<c:otherwise>
		<h3 class="quick-guide__tit">보이스 피싱 예방</h3>
		<div class="embed-youtube">
		    <iframe src="https://www.youtube.com/embed/hI4CGX0o_Lo" title="보이스 피싱 예방 가이드 동영상" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"></iframe>
		</div>
		<h3 class="quick-guide__tit">스마트폰 결제 피해 예방</h3>
		<div class="embed-youtube">
		    <iframe src="https://www.youtube.com/embed/IYfFQHuz7sY" title="스마트폰 결제 피해 예방 가이드 동영상" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"></iframe>
		</div>
	</c:otherwise>
</c:choose>