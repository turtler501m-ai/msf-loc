<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutNoGnbDefault">

	<t:putAttribute name="titleAttr">로밍 신청/해지</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<style>
      .tab-height{
        line-height: 2.25rem !important;}
		</style>
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.07.22"></script>
		<script type="text/javascript" src="/resources/js/personal/portal/pRoamingView.js?version=25.07.22"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<input type="hidden" id="underAge" value="${underAge}">
		<input type="hidden" id="initTab" value="${tab}">

		<div class="ly-content" id="main-content">
			<div class="ly-page--title">
				<h2 class="c-page--title">개인화 URL (로밍 신청/해지)</h2>
			</div>

			<div class="c-tabs c-tabs--type1">
				<div class="c-tabs__list" role="tablist">
					<button class="c-tabs__button tab-height" id="generalTab1">조회/변경<br>(부가서비스)</button>
					<button class="c-tabs__button tab-height" id="generalTab2">신청<br>(부가서비스)</button>
					<button class="c-tabs__button tab-height" id="tab1" role="tab" aria-controls="tabB1panel" aria-selected="true">조회/변경<br>(로밍)</button>
					<button class="c-tabs__button tab-height" id="tab2" role="tab" aria-controls="tabB2panel" aria-selected="false">신청<br>(로밍)</button>
				</div>
			</div>

			<div class="c-row c-row--lg u-mt--64">
				<%@ include file="/WEB-INF/views/personal/portal/pCommCtn.jsp" %>
				<hr class="c-hr c-hr--title">
			</div>

			<div class="c-tabs__panel" id="tabB1panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
				<div class="c-row c-row--lg">
					<div class="c-nodata noAddSvcList" style="display: none;">
						<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
						<p class="c-nodata__text">이용중인 로밍이 없습니다.</p>
					</div>
					<div class="c-table" id="addSvcData">
						<table>
							<caption>상품명, 이용요금 (VAT포함), 해지로 구성된 현재 사용중인 해외로밍 정보</caption>
							<colgroup>
								<col style="width: 33.33%">
								<col style="width: 33.33%">
								<col>
							</colgroup>
							<thead>
							<tr>
								<th scope="col">상품명</th>
								<th scope="col">이용요금 (VAT포함)</th>
								<th scope="col">해지</th>
							</tr>
							</thead>
							<tbody id="addSvcList">
							</tbody>
						</table>
					</div>
					<b class="c-flex c-text c-text--fs15 u-mt--48">
						<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
						<span class="u-ml--4">알려드립니다</span>
					</b>
					<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
						<li class="c-text-list__item u-co-gray">로밍 데이터 함께ON 서비스 가입자의 경우 본인 회선의 신청 내역만 조회가 가능합니다.</li>
						<li class="c-text-list__item u-co-gray">로밍 상품의 변경은 신청 기간 이전에만 가능하며 신청 기간 이후에는 해지만 가능합니다.</li>
						<li class="c-text-list__item u-co-gray">로밍 데이터 함께ON 서브회선을 등록한 고객님은 서브회선의 부가서비스를 먼저 해지하셔야 대표회선의 변경/해지가 가능합니다.</li>
						<li class="c-text-list__item u-co-gray">부가서비스 신청과 변경은 한건씩 신청 가능합니다.</li>
					</ul>
				</div>
			</div>

			<div class="c-tabs__panel" id="tabB2panel" role="tabpanel" aria-labelledby="tab2" tabindex="-1">
				<div class="c-row c-row--lg">
					<h4 class="c-heading c-heading--fs20">
						<b>가입 가능한 해외 로밍</b>
					</h4>
					<div class="c-nodata noDataFree" style="display: none;">
						<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
						<p class="c-nodata__text">신청 가능한 해외 로밍이 없습니다.</p>
					</div>
					<div class="c-accordion c-accordion--type4 extra-service u-mt--24 roamingDataYn" data-ui-accordion>
						<ul class="c-accordion__box roamingList">
						</ul>
					</div>
					<div class="c-button-wrap u-mt--56">
						<button class="c-button c-button--lg c-button--primary u-width--460" id="btn_regSvc" type="button" onclick="btn_regSvc();">로밍 신청</button>
					</div>
					<b class="c-flex c-text c-text--fs15 u-mt--48">
						<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
						<span class="u-ml--4">알려드립니다</span>
					</b>
					<ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
						<li class="c-text-list__item u-co-gray">데이터/음성 로밍 차단 상품에 가입되어 있으면 해당 상품을 먼저 해지하셔야 합니다.</li>
						<li class="c-text-list__item u-co-gray">로밍 데이터 함께ON 서비스 신청 시 대표 번호 고객님이 서브회선 고객님을 사전 등록했더라도, 서브회선 고객님이 로밍 데이터 함께ON 서브 상품에 별도로 가입해야 무료 적용이 가능합니다.</li>
						<li class="c-text-list__item u-co-gray">부가서비스 신청과 변경은 한건씩 신청 가능합니다.</li>
					</ul>
				</div>
			</div>
		</div>
	</t:putAttribute>

	<t:putAttribute name="popLayerAttr">
		<!-- 로밍 해지 팝업  -->
		<div class="c-modal c-modal--lg850" id="divRegSvcCan" role="dialog" aria-labelledby="divRegSvcCanTitle">
			<div class="c-modal__dialog" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<div class="c-modal__title" id="divRegSvcCanTitle">로밍 해지</div>
						<button class="c-button" data-dialog-close>
							<i class="c-icon c-icon--close" aria-hidden="true"></i>
							<span class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body"></div>
					<div class="c-modal__footer">
						<div class="c-button-wrap">
							<button class="c-button c-button--full c-button--gray u-width--220" data-dialog-close>취소</button>
							<button id="btnPrdSvcCan" class="c-button c-button--lg c-button--primary u-width--220" data-dialog-close>해지</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</t:putAttribute>

</t:insertDefinition>