<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<t:insertDefinition name="layoutGnbDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/bluebird.core.js"></script>
		<script type="text/javascript" src="/resources/js/portal/vendor/swiper.min.js"></script>
		<script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
		<script type="text/javascript" src="/resources/js/common/validationCheck.js"></script>
		<script type="text/javascript" src="/resources/js/sns.js"></script>
		<script type="text/javascript" src="/resources/js/portal/rate/roamingList.js"></script>
	</t:putAttribute>


	<t:putAttribute name="contentAttr">
		<div class="ly-loading" id="subbody_loading">
			<img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
		</div>

		<div class="ly-content" id="main-content">
			<form name="frm" id="frm" method="post" action="/loginForm.do">
				<input type="hidden" name="uri" id="uri" value="/rate/roamingList.do"/>
				<input type="hidden" name="userDivisionYn" id="userDivisionYn" value="02"/>
			</form>
			<div class="ly-page--title">
				<h2 class="c-page--title">해외 로밍</h2>
			</div>
			<div class="c-row c-row--lg">
				<div class="c-filter">
					메인 탭
					<div id="mainTab" class="c-filter__inner">
					</div>
					<div class="c-form-wrap" style="display:flex; justify-content:space-between;">
						<div id="totalNum" class="c-form__title u-ml--32">
						</div>
						<div class="c-form-group" role="group" aira-labelledby="chk_gh003">
							<div class="c-checktype-row">
								<input class="c-checkbox c-checkbox--type3" id="chkSelf" type="checkbox">
								<label class="c-label u-mr--24" for="chkSelf">셀프가입 가능</label>
							</div>
						</div>
					</div>
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
