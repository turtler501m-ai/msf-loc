<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<script type="text/javascript" src="/resources/js/personal/mobile/pRoamingViewPop.js?version=25.07.22"></script>

<div class="c-modal__content">

	<input type="hidden" id="dateType" value="${prodInfo.dateType}">
	<input type="hidden" id="lineType" value="${prodInfo.lineType}">
	<input type="hidden" id="usePrd" value="${prodInfo.usePrd}">
	<input type="hidden" id="freeYn" value="${prodInfo.freeYn}">
	<input type="hidden" id="mmBasAmtVatDesc" value="${prodInfo.mmBasAmtVatDesc}">
	<input type="hidden" id="lineCnt" value="${prodInfo.lineCnt}">
	<input type="hidden" id="rateAdsvcGdncSeq" value="${prodInfo.rateAdsvcGdncSeq}">
	<input type="hidden" id="rateAdsvcNm" value="${prodInfo.rateAdsvcNm}"/>
	<input type="hidden" id="rateAdsvcCd" value="${prodRel.rateAdsvcCd}"/>
	<input type="hidden" id="reqLineCnt" value="${reqLineCnt}"/>
	<input type="hidden" id="ncn" value="${ncn}"/>
	<input type="hidden" id="flag" value="${flag}"/>
	<input type="hidden" id="prodHstSeq" value="${prodHstSeq}"/>

	<div class="c-modal__header">
		<c:choose>
			<c:when test="${'Y' eq flag}">
				<h1 class="c-modal__title">로밍상품 변경</h1>
			</c:when>
			<c:otherwise>
				<h1 class="c-modal__title">로밍상품 신청</h1>
			</c:otherwise>
		</c:choose>
		<button class="c-button" data-dialog-close>
			<i class="c-icon c-icon--close" aria-hidden="true"></i>
			<span class="c-hidden">팝업닫기</span>
		</button>
	</div>

	<div class="c-modal__body">
		<p class="u-fw--medium u-fs-16">선택 로밍상품 내역</p>
		<div class="u-mt--16 c-table c-table--product">
			<table>
				<caption>로밍 상품명, 이용요금(VAT)포함으로 구성된 선택 로밍 정보</caption>
				<thead>
				<tr>
					<th>상품명</th>
					<th>이용요금(VAT)포함</th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td>${prodInfo.rateAdsvcNm}</td>
					<td id="roamingPrice"></td>
				</tr>
				</tbody>
			</table>

			<c:if test="${'S' eq prodInfo.lineType}">
				<p class="u-mt--24 u-fw--medium u-fs-16 addMainPhoneTitle">대표 번호</p>
				<div id="addMainPhone"></div>
			</c:if>

			<c:if test="${'1' eq prodInfo.dateType or '2' eq prodInfo.dateType or '3' eq prodInfo.dateType or '4' eq prodInfo.dateType}">
				<p class="u-mt--24 u-fw--medium u-fs-16 date">이용기간 설정(한국시간 기준)</p>
				<div class="c-form-row c-col2 date">
					<div class="c-form c-form--datepicker">
						<div class="c-form__input">
							<input class="c-input date-input" id="useStartDate" type="text" placeholder="YYYY-MM-DD" pattern="yyyy-MM-dd" title="날짜입력" maxlength="10">
							<label class="c-form__label" for="useStartDate">시작일</label>
							<button class="c-button c-button--calendar">
								<i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
							</button>
						</div>
					</div>
					<div class="c-form-field">
						<div class="c-form__select u-mt--8">
							<select class="c-select c-select--time" id="startTime" name="startTime">
								<option label="시작시간선택" value="" disabled selected>시작시간 선택</option>
								<c:forEach var="i" begin="0" end="23">
									<option value="${i>9?i:'0'}${i>9?'':i}">${i>9?i:'0'}${i>9?'':i}시</option>
								</c:forEach>
							</select>
							<label class="c-form__label" for="startTime">시작시간</label>
						</div>
					</div>
					<div class="c-form c-form--datepicker" id="endDateWrap">
						<div class="c-form__input useEndDate">
							<input class="c-input date-input" id="useEndDate" type="text" placeholder="YYYY-MM-DD" pattern="yyyy-MM-dd" title="날짜입력" maxlength="10">
							<label class="c-form__label" for="useEndDate">종료일</label>
							<button class="c-button c-button--calendar">
								<i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
							</button>
						</div>
					</div>
				</div>
			</c:if>

			<c:if test="${'M' eq prodInfo.lineType}">
				<p class="u-mt--30 u-fw--medium u-fs-16 addSubPhoneTitle">
					서브 번호<span class="u-mt--24 u-fw--medium u-fs-14 addPhoneDesc"> (데이터를 함께 이용하실 추가 고객 등록)</span>
				</p>
				<ul class="u-mt--0 u-mb--14 c-text-list c-bullet c-bullet--dot">
					<li class="u-mt--4 c-text-list__item">추가 고객은 kt M모바일 사용 휴대폰 번호에 한해 최대 ${prodInfo.lineCnt}개까지 등록 가능합니다.</li>
					<li class="u-mt--4 c-text-list__item" id="subRoamName">추가 등록된 고객님도 '로밍 데이터 함께ON(서브)'를 별도 가입하셔야합니다.</li>
				</ul>
				<div id="addSubPhone"></div>
			</c:if>
		</div>

		<b class="c-text c-text--type3 u-mt--30 u-mb--16">
			<i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
		</b>

		<c:choose>
			<c:when test="${'M' eq prodInfo.lineType}">
				<ul class="c-text-list c-bullet c-bullet--dot">
					<li class="c-text-list__item u-co-gray">데이터/음성 로밍 차단 상품에 가입되어 있으면 해당 상품을 먼저 해지하셔야 합니다.</li>
					<li class="c-text-list__item u-co-gray">신청 후 데이터 미 사용 시, 요금은 청구되지 않습니다.</li>
					<li class="c-text-list__item u-co-gray">서브회선의 신청/가입 여부는 대표회선에서 확인이 불가합니다. 서브회선 이용 고객에게 확인 바랍니다.</li>
					<li class="c-text-list__item u-co-gray">서브회선 고객님이 ‘데이터 함께ON’ 서브 상품에 별도로 가입해야 무료 적용이 가능합니다.</li>
					<li class="c-text-list__item u-co-gray">부가서비스 신청과 변경은 한건씩 신청 가능합니다.</li>
				</ul>
			</c:when>
			<c:when test="${'S' eq prodInfo.lineType}">
				<ul class="c-text-list c-bullet c-bullet--dot">
					<li class="c-text-list__item u-co-gray">데이터/음성 로밍 차단 상품에 가입되어 있으면 해당 상품을 먼저 해지하셔야 합니다.</li>
					<li class="c-text-list__item u-co-gray">데이터 함께ON 대표회선 고객님이 지정하신 고객님만 신청 가능합니다.</li>
					<li class="c-text-list__item u-co-gray">대표 번호 고객님이 서브 번호 고객님을 사전 등록했더라도 서브 회선 고객님이 ‘데이터 함께ON’ 서브 상품에 별도로 가입해야 무료 적용이 가능합니다.</li>
					<li class="c-text-list__item u-co-gray">신청 후 데이터 미 사용 시, 요금은 청구되지 않습니다.</li>
					<li class="c-text-list__item u-co-gray">대표회선 고객님께서 설정한 기간 내에서 이용이 가능합니다.</li>
					<li class="c-text-list__item u-co-gray">부가서비스 신청과 변경은 한건씩 신청 가능합니다.</li>
				</ul>
			</c:when>
			<c:otherwise>
				<ul class="c-text-list c-bullet c-bullet--dot">
					<li class="c-text-list__item u-co-gray">신청 후 데이터 또는 음성 이용 시 요금 전액이 청구 됩니다.</li>
					<li class="c-text-list__item u-co-gray">부가서비스 신청과 변경은 한건씩 신청 가능합니다.</li>
				</ul>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="c-modal__footer c-button-wrap u-mt--48">
		<button class="c-button c-button--full c-button--gray" data-dialog-close>취소</button>
		<button class="c-button c-button--primary c-button--full" id="applyRoaming" disabled>확인</button>
	</div>
</div>