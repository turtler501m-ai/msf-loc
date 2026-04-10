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
			<input type="hidden" name="svcCntrNo" id="svcCntrNo" value="" >
			<input type="hidden" name="rateSoc" id="rateSoc" value="" >

			<div class="ly-page-sticky">
				<h2 class="ly-page__head">
					요금할인 재약정
					<button class="header-clone__gnb"></button>
				</h2>
			</div>

			<div class="c-box c-box--type3">
				<strong class="c-box c-box--type3__title">요금할인 재약정이란?</strong>
				<p class="c-box c-box--type3__text">약정 기간이 만료된 고객님을 대상으로 약정을 재 가입하실 경우 단말 할인 지원금 대신 그에 상응하는 요금할인 혜택을 받으실 수 있는 서비스입니다.</p>
				<div class="c-box c-box--type3__image">
					<img src="/resources/images/mobile/content/guide_info_app.png" alt="">
				</div>
			</div>
			<div class="c-form">
				<%@ include file="/WEB-INF/views/mobile/mypage/sendCertSms.jsp"%>
				<div class="c-agree">
					<div class="c-agree__item">
						<input class="c-checkbox" id="chkA1" type="checkbox" name="chkA">
						<label class="c-label" for="chkA1">개인정보 수집이용 동의</label>
						<button class="c-button c-button--xsm" onclick="btnRegDtl('cdGroupId1=TERMSINF&cdGroupId2=TERMSINF01');">
							<span class="c-hidden">상세보기</span>
							<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
						</button>
					</div>
				</div>
				<div class="c-button-wrap">
					<button class="c-button c-button--full c-button--primary" id="applyBtn" href="javascript:;">확인</button>
				</div>
				<div class="c-form">
					<span class="c-form__title">요금제</span>
					<div class="c-item c-item--type1 product-info-box">
						<strong class="c-item__title" id="rateNm">-</strong>
					</div>
				</div>

				<div class="c-form" id="trInstnom1">
					<span class="c-form__title">약정기간</span>
					<div class="c-check-wrap" role="group" aria-labelledby="radOpenType">
						<div class="c-chk-wrap">
							<input class="c-radio c-radio--button" id="instNom1" type="radio" name="instNom" value="12">
							<label class="c-label" for="instNom1">12개월</label>
						</div>
						<div class="c-chk-wrap">
							<input class="c-radio c-radio--button" id="instNom2" type="radio" name="instNom" checked value="24">
							<label class="c-label" for="instNom2">24개월</label>
						</div>
					</div>
				</div>

				<div class="c-form" id="trInstnom2" style="display:none;">
					<span class="c-form__title">약정기간</span>
					<div class="c-item c-item--type1 product-info-box">
						<strong class="c-item__title" id="trInstnomTxt">-</strong>
					</div>
				</div>

				<div class="c-button-wrap" id="btnSvcPreChkArea">
					<button class="c-button c-button--full c-button--primary" id="btnSvcPreChk" disabled="disabled">약정가능여부 조회</button>
				</div>
				<div class="c-button-wrap" style="display:none" id="btnReSvcPreChkArea">
					<button class="c-button c-button--full c-button--primary" id="btnReSvcPreChk">약정조건 재설정</button>
				</div>
			</div>

			<div class="c-form" id="divIntro">
				<span class="c-form__title">조회결과</span>
				<div class="c-item c-item--type1 product-info-box" id="tbodyId">
					<strong class="c-item__title">약정 기간을 선택하시고,<br />약정 가능 여부 조회 버튼을 클릭하시면 자동 계산됩니다.</strong>
				</div>
			</div>

			<div class="c-form" id="divPlcyDcInfo" style="display:none;">
				<span class="c-form__title">조회결과</span>
				<div class="c-item c-item--type1 product-info-box">
					<strong class="c-item__title">요금할인 재약정 대상 고객</strong>
					<ul class="c-text-list c-bullet c-bullet--dot">
						<li class="c-text-list__item u-co-gray">현재 단말기를 24개월 이상 사용하여 약정이 만료된 경우</li>
						<li class="c-text-list__item u-co-gray">요금할인 재약정 가입 가능 요금제 사용 고객</li>
					</ul>
				</div>
			</div>

			<div class="c-form" id="divResult" style="display:none;">
				<span class="c-form__title">조회결과</span>
				<div class="c-table">
					<table>
						<caption>재약정 할인금액</caption>
						<colgroup>
							<col>
						</colgroup>
						<thead>
							<tr>
								<th scope="col">재약정 할인금액</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="u-ta-center" id="totalVatPriceDC"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
					<li class="c-text-list__item u-co-gray">재 약정 시 약정기간은 중도 변경이 불가합니다.</li>
					<li class="c-text-list__item u-co-gray">프로모션 할인을 받으시는 경우, 프로모션 조건에 따라 보여지는 금액이 상이할 수 있습니다.</li>
					<li class="c-text-list__item u-co-gray">요금제에 따라 재약정 요금할인이 변경됩니다.</li>
					<li class="c-text-list__item u-co-gray">약정기간 내 해지 할 경우 위약금이 부과될 수 있습니다.</li>
					<li class="c-text-list__item u-co-gray">요금할인 재약정을 위해서는 서비스 제공을 위한 동의 및 본인확인이 필요합니다. 내용을 확인하시고 동의하여 주시기 바랍니다.</li>
					<li class="c-text-list__item u-co-gray">사은품은 익월 말까지 회선 사용 고객에 한해 제공 됩니다.</li>
				</ul>

				<div class="c-form-wrap u-mt--48">
					<span class="c-form__title">약관동의</span>
					<div class="c-agree">
						<div class="c-agree__item">
							<div class="c-agree__inner">
								<input class="c-checkbox" id="chkAgree" type="checkbox" name="chkAgree">
								<label class="c-label" for="chkAgree">개인정보 수집이용 동의</label>
							</div>
							<button class="c-button c-button--xsm" href="javascript:void(0);" onclick="btnRegDtl('cdGroupId1=FormEtcCla&rePlcyClause=TERMPLCY01');">
								<span class="c-hidden">상세보기(새창)</span>
								<i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
							</button>
						</div>
					</div>
				</div>

				<c:if test="${presentList ne null and !empty presentList}">
					<div class="c-form">
				        <span class="c-form__title">사은품 선택</span>
				        <div class="c-check-wrap c-check-wrap--column">

					        <c:forEach items="${presentList}" var="item" varStatus="eachIndex">
					        	<input class="c-radio c-radio--button c-radio--button--type2" id="prizeType0${eachIndex.count}" type="radio" name="prizeType" value="${item.dtlCd}"
					        		<c:if test="${eachIndex.index eq 0}">checked="checked"</c:if>
					        	>
				                <label class="c-label" for="prizeType0${eachIndex.count}">${item.dtlCdNm}</label>
					        </c:forEach>
			            </div>
			        </div>
				</c:if>

				<div class="c-button-wrap">
					<button class="c-button c-button--full c-button--primary" id="btnReqReg">재약정 가입</button>
				</div>
				<div class="c-form">
					<div class="c-item c-item--type1 product-info-box u-mt--16">
						<strong class="c-item__title">※ 사은품은 익월 말까지 회선 사용 고객에 한해 제공 됩니다.</strong>
					</div>
				</div>
			</div>
		</div>

	</t:putAttribute>
</t:insertDefinition>