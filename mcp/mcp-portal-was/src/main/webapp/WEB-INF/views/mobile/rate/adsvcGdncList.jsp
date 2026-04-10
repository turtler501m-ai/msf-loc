<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%-- 부가서비스 화면 --%>
<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/common/validationCheck.js"></script>
		<script type="text/javascript" src="/resources/js/bluebird.core.js"></script>
		<script type="text/javascript" src="/resources/js/mobile/rate/adsvcGdncList.js"></script>
		<script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="main-content">
	    	<div class="ly-page-sticky">
	        	<h2 class="ly-page__head">부가서비스<button class="header-clone__gnb"></button></h2>
	      	</div>
	      	
	      	<div class="c-filter c-filter--accordion c-expand">
				<button class="c-filter--accordion__button">
					<div class="c-hidden">필터 펼치기</div>
				</button>
				<%-- 메인 탭 --%>
				<div id="mainTab" class="c-filter__inner">
				</div>
			</div>
			<div class="c-form u-mt--10" style="display:flex; justify-content:space-between;">
			  <div id="totalNum" class="c-form__title c-form u-mt--10 u-ml--8">
			  </div>
			  <div class="c-form-group" role="group" aira-labelledby="chk_gh003">
			    <div class="c-checktype-row">
			      <input class="c-checkbox c-checkbox--type3" id="chkFree" type="checkbox">
			      <label class="c-label" for="chkFree">무료만 보기</label>
			    </div>
			    <div class="c-checktype-row">
			      <input class="c-checkbox c-checkbox--type3" id="chkSelf" type="checkbox">
			      <label class="c-label" for="chkSelf">셀프가입 가능</label>
			    </div>
			  </div>
			</div>
			<div id="listPanel" class="c-row c-row--lg">
			</div>
	    </div>

	    <%-- main 탭 구분값 --%>
	    <input type="hidden" id="mainTabRateAdsvcCtgCd" name="mainTabRateAdsvcCtgCd">
	    <input type="hidden" id="paramRateAdsvcCtgCd" name="paramRateAdsvcCtgCd" value="${paramRateAdsvcCtgCd}">
	    <input type="hidden" id="paramRateAdsvcGdncSeq" name="paramRateAdsvcGdncSeq" value="${paramRateAdsvcGdncSeq}">
	</t:putAttribute>

</t:insertDefinition>
