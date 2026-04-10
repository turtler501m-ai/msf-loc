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
		<script type="text/javascript" src="/resources/js/mobile/etc/spnsrRecontract.js"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					사은품 신청
					<button class="header-clone__gnb"></button>
				</h2>
			</div>
			<div class="u-img-full">
				<img src="/resources/images/mobile/etc/giftPromotion/banner_giftcomplete.png" alt="사은품 신청이 완료되었습니다">
			</div>
			<div class="product-info-box u-mt--12">
				<ul class="c-text-list c-bullet c-bullet--dot">
					<li class="c-text-list__item u-co-gray">신청하신 사은품 내역은 수정/변경이 불가합니다.</li>
					<li class="c-text-list__item u-co-gray">사은품을 선택하셨더라도 개통 후 익월 말까지 요금제를 변경/해지/정지 하실 경우 제공 대상에서 제외될 수 있습니다.</li>
					<li class="c-text-list__item u-co-gray">선택하신 사은품은 개통 후 익월 말 일괄 발송됩니다.</li>
				</ul>
			</div>
			<div class="c-button-wrap">
				<button class="c-button c-button--full c-button--primary" onclick="location.href ='/m/gift/giftPromotion.do?prmtId=${prmtId}'">확인</button>
			</div>
		</div>

	</t:putAttribute>
</t:insertDefinition>