<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutNoGnbFotter">

	<t:putAttribute name="titleAttr">로밍 신청/해지</t:putAttribute>

	<t:putAttribute name="scriptHeaderAttr">
		<style>
      .tab-style{
        line-height: 1.3rem !important;
        font-size: 0.725rem !important;}

      .roaming-info__sub{
        bottom: 1rem;
        right: 1rem;
        color: #707070;
        font-size: 0.75rem;
      }
		</style>
		<script type="text/javascript" src="/resources/js/common/dataFormConfig.js?version=25.07.22"></script>
		<script type="text/javascript" src="/resources/js/personal/mobile/pRoamingView.js?version=25.07.22"></script>
	</t:putAttribute>

	<t:putAttribute name="contentAttr">

		<input type="hidden" id="underAge" value="${underAge}">
		<input type="hidden" id="initTab" value="${tab}">

		<div class="ly-content" id="main-content">
			<div class="ly-page-sticky">
				<h2 class="ly-page__head">개인화 URL (로밍 신청/해지)<button class="header-clone__gnb"></button>
				</h2>
			</div>

			<div class="c-tabs c-tabs--type2" data-tab-active="0">
				<div class="c-tabs__list c-expand sticky-header" data-tab-list="">
					<button class="c-tabs__button tab-style" id="generalTab1" onclick="goRegServiceView('1')">조회/변경<br>(부가서비스)</button>
					<button class="c-tabs__button tab-style" id="generalTab2" onclick="goRegServiceView('2')">신청<br>(부가서비스)</button>
					<button class="c-tabs__button tab-style" id="tab1" data-tab-header="#tabB1-panel" onclick="btn_tab('1')">조회/변경<br>(로밍)</button>
					<button class="c-tabs__button tab-style" id="tab2" data-tab-header="#tabB2-panel" onclick="btn_tab('2')">신청<br>(로밍)</button>
				</div>
				<hr class="c-hr c-hr--type3 c-expand">
				<%@ include file="/WEB-INF/views/personal/mobile/pCommCtn.jsp" %>

				<!-- 로밍 조회/변경 -->
				<div class="c-tabs__panel border-none" id="tabB1-panel">
					<div class="c-nodata regSvcYn" style="display: none;">
						<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
						<p class="c-noadat__text">이용중인 로밍이 없습니다.</p>
					</div>
					<div class="c-table c-table--plr-8 regDataYn">
						<table>
							<caption>상품명, 이용요금 (VAT포함), 해지로 구성된 현재 사용중인 해외로밍 정보</caption>
							<colgroup>
								<col style="width: 50%">
								<col style="width: 28%">
								<col style="width: 22%">
							</colgroup>
							<thead>
							<tr>
								<th scope="col">상품명</th>
								<th scope="col">
									<p>이용요금</p>
									<p>(VAT포함)</p>
								</th>
								<th scope="col">해지</th>
							</tr>
							</thead>
							<tbody id="addSvcList"></tbody>
						</table>
						<hr class="c-hr c-hr--type2">
						<b class="c-text c-text--type3">
							<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
						</b>
						<ul class="c-text-list c-bullet c-bullet--dot">
							<li class="c-text-list__item u-co-gray">로밍 데이터 함께ON 서비스 가입자의 경우 본인 회선의 신청 내역만 조회가 가능합니다.</li>
							<li class="c-text-list__item u-co-gray">로밍 상품의 변경은 신청 기간 이전에만 가능하며 신청 기간 이후에는 해지만 가능합니다.</li>
							<li class="c-text-list__item u-co-gray">로밍 데이터 함께ON 서브회선을 등록한 고객님은 서브회선의 부가서비스를 먼저 해지하셔야 대표회선의 변경/해지가 가능합니다.</li>
							<li class="c-text-list__item u-co-gray">부가서비스 신청과 변경은 한건씩 신청 가능합니다.</li>
						</ul>
					</div>
				</div>

				<!-- 로밍 신청 -->
				<div class="c-tabs__panel border-none" id="tabB2-panel">
					<h4 class="c-heading c-heading--type3 u-mt--40 u-mb--32">가입 가능한 해외 로밍</h4>
					<div class="c-nodata noDataFree" style="display: none;">
						<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
						<p class="c-noadat__text">신청 가능한 해외 로밍이 없습니다.</p>
					</div>
					<div class="c-accordion c-accordion--type3 roamingDataYn">
						<ul data-acc-observer class="c-accordion__box acco-border chargeAccordion roamingList"></ul>
					</div>
					<div class="u-mt--48 u-mb--40">
						<button class="c-button c-button--full c-button--primary" id="btn_regSvc" onclick="btn_regSvc();">로밍 신청</button>
					</div>
					<hr class="c-hr c-hr--type2">
					<b class="c-text c-text--type3">
						<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
					</b>
					<ul class="c-text-list c-bullet c-bullet--dot">
						<li class="c-text-list__item u-co-gray">데이터/음성 로밍 차단 상품에 가입되어 있으면 해당 상품을 먼저 해지하셔야 합니다.</li>
						<li class="c-text-list__item u-co-gray">로밍 데이터 함께ON 서비스 신청 시 대표 번호 고객님이 서브회선 고객님을 사전 등록했더라도, 서브회선 고객님이 로밍 데이터 함께ON 서브 상품에 별도로 가입해야 무료 적용이 가능합니다.</li>
						<li class="c-text-list__item u-co-gray">부가서비스 신청과 변경은 한건씩 신청 가능합니다.</li>
					</ul>
				</div>
			</div>
		</div>
	</t:putAttribute>

	<t:putAttribute name="popLayerAttr">
		<!-- 로밍 해지 팝업 -->
		<div class="c-modal c-modal--lg850" id="divRegSvcCan" role="dialog" tabindex="-1" aria-labelledby="divRegSvcCanTitle">
			<div class="c-modal__dialog c-modal__dialog--full" role="document">
				<div class="c-modal__content">
					<div class="c-modal__header">
						<div class="c-modal__title" id="divRegSvcCanTitle">로밍 해지</div>
						<button class="c-button" data-dialog-close>
							<i class="c-icon c-icon--close" aria-hidden="true"></i>
							<span class="c-hidden">팝업닫기</span>
						</button>
					</div>
					<div class="c-modal__body">
					</div>
					<div class="c-modal__footer">
						<div class="c-button-wrap">
							<button class="c-button c-button--gray c-button--lg u-width--120" data-dialog-close>취소</button>
							<button id="btnPrdSvcCan" class="c-button c-button--full c-button--primary" data-dialog-close>해지</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</t:putAttribute>

</t:insertDefinition>