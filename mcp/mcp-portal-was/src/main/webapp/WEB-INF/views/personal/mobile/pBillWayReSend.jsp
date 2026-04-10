<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutNoGnbFotter">

	<t:putAttribute name="titleAttr">요금명세서</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/personal/mobile/pBillWayReSend.js?version=25.06.10"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">개인화 URL (요금명세서)<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-tabs c-tabs--type2" data-tab-active="0">
				<div class="c-tabs__list c-expand sticky-header" data-tab-list="">
					<button class="c-tabs__button" data-tab-header="#Fbill01-panel" href="javascript:;" >명세서 재발송</button>
					<button class="c-tabs__button" data-tab-header="#Fbill02-panel" href="javascript:;" id="Fbill02">명세서 정보변경</button>
				</div>
				<div class="c-tabs__panel" id="Fbill01-panel">
					<%@ include file="/WEB-INF/views/personal/mobile/pCommCtn.jsp" %>
					<h4 class="c-heading c-heading--type2 u-mb--12">요금 명세(최근 6개월)</h4>
					<div class="c-table u-mb--24">
						<table>
							<caption>선택 부가서비스 영역</caption>
							<colgroup>
								<col>
								<col>
							</colgroup>
							<thead>
							<tr>
								<th>청구월<br />(명세서 발행월)</th>
								<th>명세서 재발송<br />(이메일/MMS)</th>
							</tr>
							</thead>
							<tbody>
							<c:choose>
								<c:when test="${outSendInfoListlDto ne null and !empty outSendInfoListlDto}">
									<c:forEach items="${outSendInfoListlDto}" var="outSendInfoListlDto" >
										<tr>
											<td>
												<fmt:parseDate value="${outSendInfoListlDto.thisMonth}" pattern="yyyyMM" var="thisMonth" />
												<fmt:formatDate value="${thisMonth}" pattern="yyyy년 MM월"/>
											</td>
											<td>
												<button class="c-button c-button--underline c-button--sm fs-13 u-co-sub-4 pop" thisMonth="${outSendInfoListlDto.thisMonth}">선택</button>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="2">조회된 명세서가 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
					</div>
					<b class="c-text c-text--type3 u-mt--12">
						<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
					</b>
					<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
						<li class="c-text-list__item u-co-gray">명세서 재발행은 이메일 또는 MMS로만 신청이 가능하며, 고객센터를 통해서도 신청하실 수 있습니다.</li>
						<li class="c-text-list__item u-co-gray">
							명세서를 받지 못한 경우 확인해 주세요
							<ul class="c-text-list c-bullet c-bullet--number">
								<li class="c-text-list__item">이메일 수신 선택 시, 이메일 주소를 잘못 입력한 경우</li>
								<li class="c-text-list__item u-co-gray">네트워크 장애로 인해 발송실패</li>
								<li class="c-text-list__item u-co-gray">메일 저장공간이 부족한 경우</li>
								<li class="c-text-list__item u-co-gray">수신거부 목록에 KT.COM, OLLEH.COM이 등록된 경우</li>
							</ul>
						</li>
						<li class="c-text-list__item u-co-gray">명세서 재발행은 최근 6개월까지만 발송가능합니다.</li>
					</ul>
				</div>
				<div class="c-tabs__panel" id="Fbill02-panel"></div>
			</div>
		</div>
	</t:putAttribute>
</t:insertDefinition>
