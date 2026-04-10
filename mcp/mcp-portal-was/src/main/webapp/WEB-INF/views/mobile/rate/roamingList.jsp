<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<%-- 부가서비스 화면 --%>
<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/common/validationCheck.js"></script>
		<script type="text/javascript" src="/resources/js/bluebird.core.js"></script>
		<script type="text/javascript" src="/resources/js/mobile/rate/roamingList.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div class="ly-content" id="main-content">
			<form name="frm" id="frm" method="post" action="/m/loginForm.do">
				<input type="hidden" name="uri" id="uri" value="/m/rate/roamingList.do"/>
				<input type="hidden" name="userDivisionYn" id="userDivisionYn" value="02"/>
			</form>
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					해외로밍<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="c-filter c-filter--accordion c-expand">
				<button class="c-filter--accordion__button">
					<div class="c-hidden">필터 펼치기</div>
				</button>
				<div id="mainTab" class="c-filter__inner">
				</div>
			</div>
			<div class="c-form-wrap" style="display:flex; justify-content:space-between;">
				<div id="totalNum" class="c-form__title u-ml--8 u-mt--8">
				</div>
				<div class="c-checktype-row u-mt--8">
					<input class="c-checkbox c-checkbox--type3" id="chkSelf" type="checkbox">
					<label class="c-label u-mr--24" for="chkSelf">셀프가입 가능</label>
				</div>
			</div>

				<%-- 목록 --%>
			<div id="listPanel" class="c-row c-row--lg">
			</div>
		</div>

		<%-- main 탭 구분값 --%>
		<input type="hidden" id="mainTabRateAdsvcCtgCd" name="mainTabRateAdsvcCtgCd">
		<input type="hidden" id="paramRateAdsvcCtgCd" name="paramRateAdsvcCtgCd" value="${paramRateAdsvcCtgCd}">
		<input type="hidden" id="paramRateAdsvcGdncSeq" name="paramRateAdsvcGdncSeq" value="${paramRateAdsvcGdncSeq}">
	</t:putAttribute>

</t:insertDefinition>
