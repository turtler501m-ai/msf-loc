<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutNoGnbFotter">

	<t:putAttribute name="titleAttr">즉시납부</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<script type="text/javascript">
      $(document).ready(function(){
        $(".footer-info").siblings("button").hide();
      });
		</script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<form name="frm" id="frm" method="post" action="/m/personal/unpaidChargeList.do">
			<input type="hidden" id="ncn" name="ncn" value="${ncn}"/>
		</form>

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">개인화 URL (즉시납부)<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="complete">
				<div class="c-icon c-icon--complete" aria-hidden="true">
					<span></span>
				</div>
				<p class="complete__heading">
					<b>요금</b>이 정상적으로<br>
					<b>납부</b>되었습니다.
				</p>
			</div>
			<h3 class="c-heading c-heading--type2 u-mt--60 u-mb--12">즉시납부 내역</h3>
			<div class="info-box">
				<dl>
					<dt>납부금액</dt>
					<dd>${payMentMoney}원</dd>
				</dl>
				<dl>
					<dt>납부정보</dt>
					<c:choose>
						<c:when test="${rmnyChId eq 'C'}">
							<dd>신용/체크카드</dd>
						</c:when>
						<c:when test="${rmnyChId eq 'K'}">
							<dd>카카오페이</dd>
						</c:when>
						<c:when test="${rmnyChId eq 'P'}">
							<dd>페이코</dd>
						</c:when>
						<c:when test="${rmnyChId eq 'N'}">
							<dd>네이버</dd>
						</c:when>
						<c:when test="${rmnyChId eq 'D'}">
							<dd>계좌이체</dd>
						</c:when>
					</c:choose>
				</dl>
			</div>
			<div class="c-button-wrap u-mt--48 u-mb--40">
				<button class="c-button c-button--full c-button--primary" onclick="$('#frm').submit();">확인</button>
			</div>
		</div>
	</t:putAttribute>
</t:insertDefinition>