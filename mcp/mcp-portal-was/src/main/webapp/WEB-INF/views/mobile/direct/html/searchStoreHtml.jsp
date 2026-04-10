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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<c:set var="agent" value="${header['User-Agent']}"/>
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="find-cvs-title">편의점 찾기</h1>
          <button class="c-button" id="closeBtn" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body">
          <div class="c-tabs c-tabs--type2" data-tab-active="0">
          <input type="hidden" id="pageNo" name="pageNo" value="1"/>
          <input type="hidden" id="storDiv" name="storDiv" value=""/>
          <input type="hidden" id="orderType" name="orderType" value="NORMAL"/>
          <input type="hidden" id="lotateVal" name="lotateVal" value=""/>
          <input type="hidden" id="latitVal" name="latitVal" value=""/>
          <input type="hidden" id="lngitVal" name="lngitVal" value=""/>
          <input type="hidden" id="isCheckTerms" name="isCheckTerms" value="${isCheckTerms}"/>


            <!-- [2021-11-19] 탭활성화 파라미터 업데이트-->

            <div class="c-tabs__panel" id="tabCVS1-panel">
              <div class="c-filter c-filter--accordion c-expand">
                <button class="c-filter--accordion__button">
                  <div class="c-hidden">필터 펼치기</div>
                </button>
                <div class="c-filter__inner">
                  <button class="c-button c-button--sm c-button--white is-active" onclick="selectStor(this);" storId="" id="allArea">전체</button>
                  <button class="c-button c-button--sm c-button--white" onclick="selectStor(this);" storId="5">GS25</button>
                  <button class="c-button c-button--sm c-button--white" onclick="selectStor(this);" storId="9">이마트24</button>
                  <button class="c-button c-button--sm c-button--white" onclick="selectStor(this);" storId="8">CU</button>
                  <button class="c-button c-button--sm c-button--white" onclick="selectStor(this);" storId="3">씨스페이스</button>
                  <button class="c-button c-button--sm c-button--white" onclick="selectStor(this);" storId="4">미니스톱</button>
                  <button class="c-button c-button--sm c-button--white" onclick="selectStor(this);" storId="6">세븐일레븐</button>
                  <button class="c-button c-button--sm c-button--white" onclick="selectStor(this);" storId="7">스토리웨이</button>
                </div>
              </div>
              <div class="c-form" id="searchDiv">
                <div class="c-form__group" role="group" aria-labelledby="inpAddress">
                  <div class="c-select-wrap">
                    <select id="schDivStr" name="schDivStr" title=" 지역을 선택해 주세요" class="c-select" onchange="selectSubAddrAjax();">
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
                  <div class="c-input-wrap">
                    <input class="c-input" type="text" id="searchStr" name="searchStr" placeholder="주소 또는 매장명" title="주소 또는 매장명" onkeypress="if(event.keyCode==13) {$(this).next().trigger('click'); return false;}" maxlength="20" style="ime-mode:active;">
                    <button class="c-button c-button--sm c-button--underline" type="button" onclick="initSearchStore();">검색</button>
                  </div>
                  <p class="c-text c-text--type3 u-mt--8">
                    <b>광역시/도 &gt; 시/구/군 순</b>으로 주소를 선택해주세요.
                  </p>
                </div>
              </div><!-- case1 - 검색 전-->
              <div class="store-result c-expand" id="searchArea">
                <strong class="store-result__heading">검색결과</strong>

                <hr class="c-hr c-hr--type2">
                <div class="store-result__cont">매장 또는 지역을 선택해주세요.</div>
              </div><!-- case2 - 검색 후-->

			  <input type="hidden" name="mobileAppChk" id="mobileAppChk" value="${nmcp:getPlatFormCd()}"/>

              <div class="c-button-wrap" id="searchNext" onclick="goNextPage();" style="display:none;">
					<button class="c-button c-button--full">
						더보기 <span class="c-button__sub" id="pageNav"><fmt:formatNumber value="${listCount}" pattern="#,###"/>/<fmt:formatNumber value="${pageInfoBean.totalCount }" type="number"/></span>
						<i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
					</button>
					<span id="maxPage" style="display: none;"></span>
				</div>
              <div class="c-button-wrap">
                <button class="c-button c-button--full c-button--primary" type="button" onclick="$('#closeBtn').click();">확인</button>
              </div>
            </div>
            <div class="c-tabs__panel" id="tabCVS2-panel">

            </div>
          <div class="c-tabs__list c-expand sticky-header--modal c-hidden" data-tab-list="">
              <button class="c-tabs__button is-active"  id="mainTab1" onclick="setModalScroll(1);">매장별/지역별</button>
          </div>




          </div>
        </div>
      </div>
