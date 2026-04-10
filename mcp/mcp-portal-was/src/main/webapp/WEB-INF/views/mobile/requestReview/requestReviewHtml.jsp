<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />
<script type="text/javascript" src="/resources/js/mobile/review/requestReviewHtml.js"></script>

<div class="tab_review">
	<div class="review_title">
		<c:if test="${resDto.viewLimitYn eq 'Y'}"><!-- 어드민에서등록된 %이상일떄만 노출토록 -->
			<h4>
				<span class="red">${resDto.percent}%</span>의 고객님이 <span class="red">추천</span>합니다!
			</h4>
		</c:if>
	</div>
	<div class="grey_space" id="space" style="display:none;"></div>
	<div class="review_detail">
		<div id="requestReview_board">

		</div>
		<button type="button" class="width_full" id="moreBtn" style="display:none; font-size:11px; border-top:1px solid #d3d3d3; border-bottom:1px solid #d3d3d3; border-left:none; border-right:none; background:#dddddd; color:#333; box-sizing:border-box; padding:6px 20px; height:34px;">
		<span class="textarea" style="padding-right:15px; background:url(/resources/images_bak/mobile/icon_moreview.png) no-repeat 100% 3px; background-size:9px auto;">
			더보기 (<span id="reViewCurrent"></span>/<span id="reViewTotal"></span>)
		</span>
	</button>
	</div>
	<input type="hidden" id="reViewPageNo" name="reViewPageNo" value="1" />
	<input type="hidden" id="reqBuyType" name="reqBuyType" value="${reqBuyType}" />
</div>
