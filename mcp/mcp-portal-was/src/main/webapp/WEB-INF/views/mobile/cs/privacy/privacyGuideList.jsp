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

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/mobile/cs/privacy/privacyGuideList.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

	<input type="hidden" id="boardCtgSeq" name="boardCtgSeq" value="92">

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					이용자 피해예방 가이드
					<button class="header-clone__gnb"></button>
				</h2>
			</div>

			<div class="c-tabs c-tabs--type2" data-tab-active="1">
				<div class="c-tabs__list c-expand sticky-header" data-tab-list="">
					<button class="c-tabs__button" data-tab-header="#tabA1-panel" id="tab1">피해예방 정보</button>
					<button class="c-tabs__button" data-tab-header="#tabA2-panel">피해예방 가이드</button>
				</div>
				<div class="c-tabs__panel" id="tabA1-panel"></div>
				<div class="c-tabs__panel" id="tabA2-panel">

					<div class="c-tabs c-tabs--type3" data-tab-active="">
						<div class="c-tabs__list c-expand" data-tab-list="">
							<c:if test="${tabMenuList ne null and !empty tabMenuList}">
								<c:forEach items="${tabMenuList}" var="tabMenuList" varStatus="status">
									<button class="c-tabs__button midTab" data-active-scroll data-tab-header="#tabB${status.count}-panel" dtlCd="${tabMenuList.dtlCd}">${tabMenuList.dtlCdNm}</button>
								</c:forEach>
							</c:if>

<!-- 						<button class="c-tabs__button" data-tab-header="#tabB1-panel">동영상으로 보기</button>
							<button class="c-tabs__button" data-tab-header="#tabB2-panel">웹툰으로 보기</button>
							<button class="c-tabs__button" data-tab-header="#tabB3-panel">피해예방 교육</button> -->
						</div>
						<div class="c-tabs__panel" id="tabB1-panel"></div>
						<div class="c-tabs__panel" id="tabB2-panel"></div>
						<div class="c-tabs__panel" id="tabB3-panel"></div>
						<div class="c-tabs__panel" id="tabB4-panel"></div>
					</div>
				</div>
			</div>
		</div>


	</t:putAttribute>




</t:insertDefinition>

