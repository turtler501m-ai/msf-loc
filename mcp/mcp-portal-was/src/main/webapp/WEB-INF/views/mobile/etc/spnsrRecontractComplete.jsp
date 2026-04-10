<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="mlayoutDefault">

	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					요금할인 재약정
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="complete">
				<div class="c-icon c-icon--complete" aria-hidden="true">
					<span></span>
				</div>
				<p class="complete__heading">
					<b>요금할인 재약정</b>이 <br> <b>완료</b>되었습니다.
				</p>
			</div>
			<div class="info-box u-mt--40">
				<dl>
					<dt>고객명</dt>
					<dd>${mcpUserCntrMngDto.subLinkName}</dd>
				</dl>
				<dl>
					<dt>휴대폰번호</dt>
					<dd>${mcpUserCntrMngDto.cntrMobileNo}</dd>
				</dl>
				<dl>
					<dt>요금제</dt>
					<dd>${mcpUserCntrMngDto.rateNm}</dd>
				</dl>
				<dl>
					<dt>약정 시작일</dt>
					<dd>
						<fmt:parseDate value="${moscSdsInfo.engtAplyStDate}" pattern="yyyyMMdd" var="engtAplyStDateTmp" />
						<fmt:formatDate value="${engtAplyStDateTmp}" pattern="yyyy.MM.dd" var="engtAplyStDate" />
						${engtAplyStDate}
					</dd>
				</dl>
				<dl>
					<dt>약정 만료일</dt>
					<dd>
						<fmt:parseDate value="${moscSdsInfo.engtExpirPamDate}" pattern="yyyyMMdd" var="engtExpirPamDateTmp" />
						<fmt:formatDate value="${engtExpirPamDateTmp}" pattern="yyyy.MM.dd" var="engtExpirPamDate" />
						${engtExpirPamDate}
					</dd>
				</dl>
				<dl>
					<dt>약정개월</dt>
					<dd>${moscSdsInfo.engtPerdMonsNum} 개월</dd>
				</dl>
				<dl>
					<dt>월 할인 금액</dt>
					<dd><fmt:formatNumber value="${dcSuprtAmtVat}" type="number"/>원</dd>
				</dl>
			</div>
			<div class="c-button-wrap">
				<a class="c-button c-button--full c-button--primary" href="/m/main.do">확인</a>
			</div>
			<hr class="c-hr c-hr--type2">
			<b class="c-text c-text--type3">
				<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
			</b>
			<ul class="c-text-list c-bullet c-bullet--dot">
				<li class="c-text-list__item u-co-gray">정지기간은 약정기간에 포함되며, 정지기간 중에는 요금할인이 제공되지 않습니다.</li>
				<li class="c-text-list__item u-co-gray">약정 기간 내 해지 시 위약금이 발생됩니다.</li>
				<li class="c-text-list__item u-co-gray">재약정은 당월 말일까지 약정 철회가 가능합니다. 상세내용은 고객센터(1899-5000)로 문의 바랍니다.</li>
			</ul>
		</div>

	</t:putAttribute>
</t:insertDefinition>