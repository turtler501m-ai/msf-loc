
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un"
	uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />
<un:useConstants var="Constants"
	className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript"
			src="/resources/js/mobile/mypage/reView.js?ver=${jsver}"></script>
	</t:putAttribute>
	<t:putAttribute name="contentAttr">
	
		<div class="ly-content" id="main-content">
	      <div class="ly-page-sticky">
	        <h2 class="ly-page__head">사용후기<button class="header-clone__gnb"></button>
	        </h2>
	      </div>
	      <div class="c-flex c-flex--jfy-between u-mt--32">
	        <span class="fs-20">내가 쓴 사용후기</span>
	        <span>
	          <a class="c-button c-button--sm u-co-sub-4 u-pr--0" href="/m/requestReView/requestReviewForm.do">사용후기 작성하기</a>
	          <i class="c-icon c-icon--arrow-mint" aria-hidden="true"></i>
	        </span>
	      </div>
	      <hr class="c-hr c-hr--type2 u-mt--16">
	      <!--[2021-12-16] nodata-->
	      <div class="review-container">
	        <ul class="review" id="listBody">
	         	
	        </ul>
	      </div>
	    </div>
		<!-- [START]-->
		<input type="hidden" name="pageNo" id="pageNo" value="0"/>




	</t:putAttribute>
</t:insertDefinition>