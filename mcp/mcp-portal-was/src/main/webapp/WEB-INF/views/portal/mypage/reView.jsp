<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />
<t:insertDefinition name="layoutMainDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript" src="/resources/js/portal/mypage/reView.js?ver=${jsver}"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mobile/vendor/swiper.min.js?ver=${jsver}"></script>

	</t:putAttribute>

	<t:putAttribute name="contentAttr">
		<div id="subbody_loading" style="display:flex; width: 100%; height: calc(100vh - 130px); box-sizing: border-box; background: rgb(255, 255, 255); text-align: center; position: relative; align-items: center; justify-content: center; z-index: 10;">
			<img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
		</div>
		<div class="ly-content" id="main-content">
	      <div class="ly-page--title">
	        <h2 class="c-page--title">사용후기</h2>
	      </div>
	      <div class="c-row c-row--lg">
	        <div class="c-heading-wrap">
	          <p class="c-heading c-heading--fs20">사용후기를 작성해 보세요</p>
	          <div class="c-heading-wrap__sub">
	            <a class="c-button c-button-round--sm c-button--white" href="/requestReView/requestReviewForm.do">사용후기 작성하기</a>
	          </div>
	        </div>
	        <hr class="c-hr c-hr--title u-mb--0"><!-- 데이터 있는 경우-->
	        <div class="c-accordion review u-mt--0 u-bt--0" id="accordionReview" data-ui-accordion>
	          <ul class="c-accordion__box" id="listBody">

	          </ul>
	        </div>
	        <div id="paging">

	        </div><!-- 데이터 없는 경우-->

	      </div>
	    </div>
	    <input type="hidden" name="pageNo" id="pageNo" value="0"/>
		<!-- 레이어 팝업 -->
	</t:putAttribute>

</t:insertDefinition>