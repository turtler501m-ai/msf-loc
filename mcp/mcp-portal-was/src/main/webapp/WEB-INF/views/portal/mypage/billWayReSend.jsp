<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />
<t:insertDefinition name="layoutMainDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/portal/mypage/billWayReSend.js?version=${jsver}"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="main-content">
			<div class="ly-page--title">
				<h2 class="c-page--title">요금명세서</h2>
			</div>
			<div class="c-tabs c-tabs--type1">
				<div class="c-tabs__list">
					<a class="c-tabs__button is-active" id="Fbill01" href="javascript:;" aria-current="page">명세서 재발송</a>
					<a class="c-tabs__button" id="Fbill02" href="javascript:;">명세서 정보변경</a>
				</div>
			</div>
			<div class="c-tabs__panel u-block">
				<div class="c-row c-row--lg">
					<h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--48">조회할 회선을 선택해 주세요.</h3>
					<%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp" %>
					<hr class="c-hr c-hr--title">
					<h4 class="c-heading c-heading--fs16 u-mt--64 u-mb--16">요금 명세(최근 6개월)</h4>
					<div class="c-table u-mt--24">
						<table>
							<caption>잔여할부금액, 잔여개월, 만료예정일로 구성된 휴대폰 단말 대금 정보</caption>
							<colgroup>
								<col>
								<col>
							</colgroup>
							<thead>
								<tr>
									<th scope="col">청구월(명세서 발행월)</th>
									<th scope="col">명세서 재발송(이메일/MMS)</th>
								</tr>
							</thead>
							<tbody>
							<c:choose>
								<c:when test="${outSendInfoListlDto ne null and !empty outSendInfoListlDto}">
									<c:forEach items="${outSendInfoListlDto}" var="outSendInfoListlDto">
										<tr>
											<td>
												<fmt:parseDate value="${outSendInfoListlDto.thisMonth}" pattern="yyyyMM" var="thisMonth" />
												<fmt:formatDate value="${thisMonth}" pattern="yyyy년 MM월"/>
											</td>
											<td>
												<button class="c-text c-text--underline u-co-sub-4 pop" thisMonth="${outSendInfoListlDto.thisMonth}">
													<span class="c-hidden">명세서 재발송 신청 팝업 열기</span>선택
												</button>
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
					<b class="c-flex c-text c-text--fs15 u-mt--48">
						<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
						<span class="u-ml--4">알려드립니다</span>
					</b>
					<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
						<li class="c-text-list__item u-co-gray">명세서 재발행은 이메일 또는 MMS로만 신청이 가능하며, 고객센터를 통해서도 신청하실 수 있습니다.</li>
						<li class="c-text-list__item u-co-gray">명세서를 받지 못한 경우 확인해 주세요</li>
						<ul class="c-text-list c-bullet c-bullet--number">
							<li class="c-text-list__item u-co-gray">이메일 수신 선택 시, 이메일 주소를 잘못 입력한 경우</li>
							<li class="c-text-list__item u-co-gray">네트워크 장애로 인해 발송실패</li>
							<li class="c-text-list__item u-co-gray">메일 저장공간이 부족한 경우</li>
							<li class="c-text-list__item u-co-gray">수신거부 목록에 KT.COM, OLLEH.COM이 등록된 경우</li>
						</ul>
						<li class="c-text-list__item u-co-gray">명세서 재발행은 최근 6개월까지만 발송가능합니다.</li>
					</ul>
				</div>
			</div>
		</div>

	</t:putAttribute>

</t:insertDefinition>