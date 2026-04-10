<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<html>
	<head>
		<meta charset="UTF-8">
		<title>Mstore SSO ERROR PAGE</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<link rel="icon" href="/favorites_icon03.png" type="image/x-icon">
		<!-- 모바일/포탈 구분 -->
		<c:choose>
			<c:when test="${isMobile eq 'Y'}">
				<link rel="stylesheet" type="text/css" href="/resources/css/mobile/styles.css?version=${Constants.CSS_VERSION}" />
			</c:when>
			<c:otherwise>
				<link rel="stylesheet" type="text/css" href="/resources/css/portal/styles.css?version=${Constants.CSS_VERSION}" />
			</c:otherwise>
		</c:choose>

		<script>
			document.addEventListener("DOMContentLoaded", () => {
				// 메인으로 가기 버튼 location
				var isMobile  = '${isMobile}';
				var mainLocation = (isMobile === 'Y') ? "/m/main.do" : "/main.do";
				document.querySelector("#goMain").setAttribute("href", mainLocation);
			});
		</script>
	</head>

	<body>

		<!-- 모바일/포탈 구분 CSS -->
		<c:set var="buttonStyle" value="${isMobile eq 'Y' ? 'c-button--full' : 'c-button--lg c-button--w460'}"></c:set>
		<c:set var="h3Style" value="${isMobile eq 'Y' ? 'u-fs-18 u-mt--32' : 'c-heading c-heading--fs24 u-mt--36'}"></c:set>
		<c:set var="pStyle" value="${isMobile eq 'Y' ? 'u-fw--bold u-fs-46 u-fs-36 u-mb--32' : 'u-fw--bold u-fs-68'}"></c:set>
		<c:set var="topMargin" value="${isMobile eq 'Y' ? 'u-mt--10' : 'u-mt--70'}"></c:set>
		<c:set var="hrStyle" value="${isMobile eq 'Y' ? '' : 'u-mt--36'}"></c:set>

		<div class="ly-content ${topMargin}" id="main-content">
			<div class="c-row c-row--lg">
				<div class="u-ta-center">
					<h3 class="${h3Style}">kt M모바일 고객을 위한 멤버십 쇼핑몰</h3>
					<p class="${pStyle}">M Store</p>
					<hr class="${hrStyle}">

					<strong class="c-heading c-heading--fs24 u-block u-mt--48 u-fw--bold">
						처리 중 문제가 발생했습니다.
					</strong>

					<div class="c-box c-box--type1 u-mt--24">
						<p class="c-text c-text--fs17 u-co-gray u-fw--bold">[상세정보]</p>
						<p class="u-co-red u-mt--16 u-fw--medium">서비스 이용에 불편을 드려 죄송합니다.</p>
					</div>
					<p class="c-text c-text--fs17 u-co-gray u-mt--24">${errorMsg}</p>
				</div>
				<div class="c-button-wrap u-mt--40">
					<a class="c-button c-button--primary ${buttonStyle}" href="/main.do" id="goMain">메인화면으로 이동</a>
				</div>
			</div>
		</div>
	</body>

</html>


