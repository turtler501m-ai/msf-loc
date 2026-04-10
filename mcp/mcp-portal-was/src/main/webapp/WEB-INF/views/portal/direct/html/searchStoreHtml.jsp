<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un"
	uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="c-modal__content">
	<div class="c-modal__header">
		<h2 class="c-modal__title" id="search-store-modal__title">편의점 찾기</h2>
		<button class="c-button" data-dialog-close>
			<i class="c-icon c-icon--close" aria-hidden="true"></i> <span
				class="c-hidden">팝업닫기</span>
		</button>
	</div>
	<div class="c-modal__body u-pb--0 store-modal ">

		<div class="c-tabs__panel" id="tabC1panel" role="tabpanel" aria-labelledby="mainTab1" tabindex="-1" aria-hidden="false" style="display: block;">
			<div class="c-filter">
				<div class="c-filter__inner">
					<!-- [2022-01-20] .c-button--sm &gt; .c-button-round--sm 클래스수정-->
					<button
						class="c-button c-button-round--sm c-button--white is-active"
						href="javascript:void(0);" onclick="selectStor(this);" storId="" id="allArea">전체</button>
					<button class="c-button c-button-round--sm c-button--white"
						href="javascript:void(0);" onclick="selectStor(this);" storId="5">GS25</button>
					<button class="c-button c-button-round--sm c-button--white"
						href="javascript:void(0);" onclick="selectStor(this);" storId="9">이마트24</button>
					<button class="c-button c-button-round--sm c-button--white"
						href="javascript:void(0);" onclick="selectStor(this);" storId="8">CU</button>
					<button class="c-button c-button-round--sm c-button--white"
						href="javascript:void(0);" onclick="selectStor(this);" storId="3">씨스페이스</button>
					<button class="c-button c-button-round--sm c-button--white"
						href="javascript:void(0);" onclick="selectStor(this);" storId="4">미니스톱</button>
					<button class="c-button c-button-round--sm c-button--white"
						href="javascript:void(0);" onclick="selectStor(this);" storId="6">세븐일레븐</button>
					<button class="c-button c-button-round--sm c-button--white"
						href="javascript:void(0);" onclick="selectStor(this);" storId="7">스토리웨이</button>
					<!-- [$2022-01-20] .c-button--sm &gt; .c-button-round--sm 클래스수정-->
				</div>
			</div>
			<div class="c-form c-form--type2 u-block u-mt--48" id="searchDiv">
				<div class="c-form__group" role="group" aria-labelledby="inpAddress">
					<div class="c-select-wrap">
						<select class="c-select" id="schDivStr" name="schDivStr"
							title="지역을 선택 하세요" onchange="selectSubAddrAjax();">
							<option value="전국">광역시/도</option>
							<option value="강원">강원</option>
							<option value="경기">경기</option>
							<option value="경남">경남</option>
							<option value="경북">경북</option>
							<option value="광주">광주</option>
							<option value="대구">대구</option>
							<option value="대전">대전</option>
							<option value="부산">부산</option>
							<option value="서울">서울</option>
							<option value="세종">세종</option>
							<option value="울산">울산</option>
							<option value="인천">인천</option>
							<option value="전남">전남</option>
							<option value="전북">전북</option>
							<option value="제주">제주</option>
							<option value="충남">충남</option>
							<option value="충북">충북</option>
						</select>
						<select class="c-select" id="subAddr" name="subAddr" disabled="">
						</select>
					</div>
					<div class="c-input-wrap u-mt--16">
						<input class="c-input" type="text" id="searchStr" name="searchStr"
							placeholder="주소 또는 매장명" title="주소 또는 매장명" onkeypress="if(event.keyCode==13) {$(this).next().trigger('click'); return false;}" maxlength="20" style="ime-mode:active;">
						<button class="c-button c-button-round--md c-button--primary"
							href="javascript:void(0);" onclick="initSearchStore();">검색</button>
					</div>
					<p class="c-text c-text--fs15 u-mt--16">
						<b>광역시/도 &gt; 시/구/군 순</b>으로 주소를 선택해주세요.
					</p>
				</div>
			</div>
			<!-- 검색 전-->

			<!-- [2022-01-21] .u-pb--48 클래스추가-->
			<div class="c-box c-box--type1 c-expand--pop u-plr--48 u-mt--48">
				<div class="store-result" id="searchArea">
					<h3 class="c-heading c-heading--fs17 c-heading--regular u-mt--20">검색결과</h3>
					<hr class="c-hr c-hr--title">
					<div class="c-nodata">
						<i class="c-icon c-icon--nodata" aria-hidden="true"></i>
						<p class="c-nodata__text">검색 결과가 없습니다.</p>
					</div>
				</div>
				<!--[$2021-12-03] 편의점 아이콘 내용 보강-->
				<!-- [2022-01-21] 더보기 버튼 마크업 수정-->
				<div class="c-button-wrap" id="searchNext" href="javascript:void(0);" onclick="goNextPage();" style="display: none;">
					<button class="c-button c-button--more">
						<span>더보기</span>
						<span class="c-button__sub">
							<span class="c-hidden">현재 노출된 항목</span>
							<span id="list-count">
								<fmt:formatNumber value="${listCount}" pattern="#,###" />
							</span>
						</span>
						<span class="c-button__sub">
							<span class="c-hidden">전체 항목</span>
							<span id="total-count">
								<fmt:formatNumber value="${pageInfoBean.totalCount }" type="number" />
							</span>
						</span>
						<span id="maxPage" style="display: none;">
						</span>
						<i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
					</button>
				</div>
				<!-- [$2022-01-21] 더보기 버튼 마크업 수정-->
			</div>

		</div>
		<div class="c-tabs__panel" id="tabC2panel" role="tabpanel"
			aria-labelledby="mainTab2" tabindex="-1" aria-hidden="false"></div>

		<div class="c-tabs c-tabs--type2 c-hidden" data-ui-tab>
			<input type="hidden" id="pageNo" name="pageNo" value="1" />
			<input type="hidden" id="storDiv" name="storDiv" value="" />
			<input type="hidden" id="orderType" name="orderType" value="NORMAL" />
			<input type="hidden" id="lotateVal" name="lotateVal" value="" />
			<input type="hidden" id="latitVal" name="latitVal" value=""/>
			<input type="hidden" id="lngitVal" name="lngitVal" value=""/>
			<input type="hidden" id="isCheckTerms" name="isCheckTerms" value="${isCheckTerms}" />
			<div class="c-tabs__list u-pt--0" role="tablist">
				<button class="c-tabs__button is-active" id="mainTab1" role="tab"
						aria-controls="tabC1panel" aria-selected="false" tabindex="-1"
						href="javascript:void(0);" onclick="setModalScroll(1);">매장별/지역별</button>

			</div>
		</div>

	</div>
</div>
