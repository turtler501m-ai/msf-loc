<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="layoutMainDefault">
	<t:putAttribute name="scriptHeaderAttr">
 		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/portal/mstory/snsList.js"></script>



	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="main-content">
	      <div class="ly-page--title">
	        <h2 class="c-page--title">M Story</h2>
	      </div>
	      <div class="m-storage">
	        <div class="c-tabs c-tabs--type1" data-ui-tab data-tab-active="1" hidden>
	          <div class="c-tabs__list" role="tablist">
	            <button class="c-tabs__button" id="monthlyM" role="tab" aria-controls="tab1_panel" aria-selected="false" tabindex="-1">월간 M</button>
	            <button class="c-tabs__button" id="snsList" role="tab" aria-controls="tab2_panel" aria-selected="false" tabindex="-1">고객소통</button>
	          </div>
	        </div>
	        <div class="c-tabs__panel no-bottom-spacing" id="tab1_panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
	          <!-- <div>월간 M(ET1003010100)</div> -->
	          <input type="hidden" value="${status}" id="status" />
	        </div>
	        <div class="c-tabs__panel" id="tab2_panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
	          <div class="c-row c-row--lg">
	            <div class="p c-text c-text--fs24 u-fw--bold u-ta-center">kt M모바일과 함께하는 스마트한 통신생활!</div>
	            <div class="p c-text c-text--fs17 u-ta-center u-co-gray-9 u-mt--16">공식 SNS 채널을 통해 kt M모바일만의 새로운 소식을 지금 바로 만나보세요!</div>
	            <ul class="customer-items">
	            	<c:forEach var="list" items="${snsList}">
						<li class="customer-items__item">
			                <a ${list.pcLinkUrl != '' && list.pcLinkUrl!= null ? '' : 'hidden' } href="javascript:void(0);" onclick="window.open('about:blank').location.href='${list.pcLinkUrl}'" title="${list.ntcartTitle} 바로가기 새창열림">
				                <div class="customer-items__item-img">
				                  <img src="${list.pcListImgNm}" alt="${list.ntcartTitle}" onerror="this.src='/resources/images/portal/content/img_review_noimage.png'" style="width: 28.6875rem;height: 16.75rem;">
				                </div>
								<c:choose>
									<c:when test="${list.snsCntpntCd == 'NAVERBLOG'}">
										<div class="customer-items__item-link">
						                  <i class="c-icon c-icon--naver-blog" aria-hidden="true"></i>
						                  <i class="c-icon c-icon--anchor" aria-hidden="true"></i>
						                </div>
									</c:when>
									<c:when test="${list.snsCntpntCd == 'YOUTUBE'}">
										<div class="customer-items__item-link">
						                  <i class="c-icon c-icon--youtube" aria-hidden="true"></i>
						                  <i class="c-icon c-icon--anchor" aria-hidden="true"></i>
						                </div>
									</c:when>
									<c:when test="${list.snsCntpntCd == 'INSTAGRAM'}">
										<div class="customer-items__item-link">
						                  <i class="c-icon c-icon--insta" aria-hidden="true"></i>
						                  <i class="c-icon c-icon--anchor" aria-hidden="true"></i>
						                </div>
									</c:when>
									<c:when test="${list.snsCntpntCd == 'FACEBOOK'}">
										<div class="customer-items__item-link">
						                  <i class="c-icon c-icon--facebook-type3" aria-hidden="true"></i>
						                  <i class="c-icon c-icon--anchor" aria-hidden="true"></i>
						                </div>
									</c:when>
								</c:choose>
								<div class="customer-items__item-title">${list.ntcartTitle}
									<div class="customer-items__item-date">
										<fmt:parseDate value="${list.ntcartDate}" var="regDt" pattern="yyyyMMdd"/>
										<fmt:formatDate value="${regDt}" pattern="yyyy.MM.dd"/>
										<%-- <fmt:parseDate value="${list.pstngStartDate}" var="stDt" pattern="yyyyMMddHHmmss"/>
										<fmt:formatDate value="${stDt}" pattern="yyyy.MM.dd"/> ~
										<fmt:parseDate value="${list.pstngEndDate}" var="endDt" pattern="yyyyMMddHHmmss"/>
										<fmt:formatDate value="${endDt}" pattern="yyyy.MM.dd"/> --%>
									</div>
			                    </div>
		                    </a>
		                </li>
            	    </c:forEach>
	            </ul>
	          </div>
	        </div>
	      </div>
	    </div>

	</t:putAttribute>

</t:insertDefinition>