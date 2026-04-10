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
		<script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js?version=25.06.10"></script>
		<script type="text/javascript" src="/resources/js/personal/personalAuth.js"></script>
		<script type="text/javascript" src="/resources/js/personal/mobile/pUnpaidChargeList.js?version=25.06.10"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<input type="hidden" id="pageType" name="pageType" value="${pageType}"/>

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">개인화 URL (즉시납부)<button class="header-clone__gnb"></button>
				</h2>
			</div>

			<%@ include file="/WEB-INF/views/personal/mobile/pCommCtn.jsp" %>

			<div class="banner-balloon u-mt--32" id="totalCntView1" style="display: none;">
				<p>
					<span class="c-text c-text--type5">납부 가능 금액</span>
					<br>
					<b class="c-text c-text--type7" id="totalCnt"></b>
					<input type="hidden" name="totalCnt"  id="totalCntReal" value="0">
				</p>
				<img class="deco-img" src="/resources/images/mobile/content/img_bolloon_banner_04.png" alt="">
			</div>
			<h4 class="c-heading c-heading--type2 u-mt--64 u-mb--12" id="totalCntView2" style="display: none;">납부가능 요금 내역</h4>
			<input type="hidden" name="customerType" id="customerType" value="${customerType}"/>

			<c:if test="${'Y' eq customerType}">
				<div class="c-nodata u-mt--32" id="customerTypeNoData">
					<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
					<p class="c-noadat__text">개인고객만 납부 가능합니다.</p>
				</div>
			</c:if>
			<div class="c-nodata unpaidNoDataView" style="display: none;">
				<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
				<p class="c-noadat__text">조회 결과가 없습니다.</p>
			</div>
			<div class="c-table" id="realtimePayHtml" style="display: none;">
				<table>
					<caption>납부가능 요금 내역</caption>
					<colgroup>
						<col style="width: 50%">
						<col style="width: 50%">
					</colgroup>
					<thead>
					<tr>
						<th scope="col">청구월</th>
						<th scope="col">요금</th>
					</tr>
					</thead>
					<tbody id="realtimePayView">
					</tbody>
				</table>
			</div>
			<div class="c-title-wrap c-title-wrap--flex chargeView1" style="display:none;">
				<h4 class="c-heading c-heading--type2">납부 금액 입력</h4>
				<div class="c-form">
					<input class="c-checkbox c-checkbox--type3" id="chkAll" type="checkbox" name="chkAll">
					<label class="c-label u-mr--0 u-co-gray-7" for="chkAll">전액 납부</label>
				</div>
			</div>
			<div class="c-form chargeView1" style="display:none;">
				<input class="c-input numOnly" type="tel" id="pay" name="pay" placeholder="숫자만 입력" maxlength="10">
			</div>
			<ul class="c-text-list c-bullet c-bullet--dot u-mt--16 chargeView1" style="display:none;">
				<li class="c-text-list__item u-co-gray">납부 가능한 요금 확인 후 납부하실 금액을 입력 해 주세요.</li>
				<li class="c-text-list__item u-co-gray">본인인증 후 납부 수단을 선택하실 수 있습니다.<br/>- 납부 가능 수단 : 신용/체크카드, 계좌이체, 카카오페이, 페이코, 네이버페이</li>
			</ul>
			<h4 class="c-heading c-heading--type2 u-mb--12 chargeView1" style="display:none;">본인인증</h4>
			<div class="c-form chargeView1" style="display:none;">
				<div class="c-check-wrap">
					<div class="c-chk-wrap">
						<input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="auth_phone" type="radio" name="radCertify">
						<label class="c-label" for="auth_phone">
							<i class="c-icon c-icon--phone" aria-hidden="true"></i>휴대폰
							<br>
						</label>
					</div>
					<div class="c-chk-wrap">
						<input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="auth_credit" type="radio" name="radCertify">
						<label class="c-label" for="auth_credit">
							<i class="c-icon c-icon--card" aria-hidden="true"></i>신용카드
							<br>
						</label>
					</div>
					<div class="c-chk-wrap">
						<input class="c-radio c-radio--button c-radio--button--type2 c-radio--button--icon2" id="auth_ipin" type="radio" name="radCertify">
						<label class="c-label" for="auth_ipin">
							<i class="c-icon c-icon--ipin" aria-hidden="true"></i>아이핀
							<br>
						</label>
					</div>
				</div>
			</div>
			<div class="c-notice-wrap">
				<hr>
				<h5 class="c-notice__title">
					<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
					<span>알려드립니다</span>
				</h5>
				<ul class="c-notice__list">
					<li>자동납부(계좌/카드)를 이용하시는 상태에서 이번 달 청구 금액 납부 시 이중 납부가 될 수 있으므로 납부일 및 계좌/카드 거래 내역 확인 후 이용하시기 바랍니다.</li>
					<li>실시간 계좌이체는 07:00~23:00에 이용 가능하며, 은행별 서비스 시간에 따라 이용이 불가할 수 있습니다.<br />- 납부 가능 은행 : 기업은행, 국민은행, 농협, 우리은행, 제일은행, 신한은행, 하나은행</li>
					<li>실시간 계좌이체는 명의자와 납부자가 동일한 경우에만 이용이 가능합니다.</li>
					<li>국내 발급 카드로만 결제 가능합니다.</li>
				</ul>
			</div>
		</div>
	</t:putAttribute>
</t:insertDefinition>