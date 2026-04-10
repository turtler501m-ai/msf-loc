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
<form id="storeForm" name="storeForm">
      <input type="hidden" id="storePageNo" name="storePageNo" value="1"/>
      <input type="hidden" id="maxPageNo" name="maxPageNo" value="1"/>
      <input type="hidden" id="totalCount" name="totalCount" value="1"/>
      <input type="hidden" id="storDiv" name="storDiv" value=""/>
      <input type="hidden" id="orderType" name="orderType" value="NORMAL"/>
      <input type="hidden" id="lotateVal" name="lotateVal" value=""/>
      <input type="hidden" id="searchStr" name="searchStr" value=""/>
      <input type="hidden" id="schDivStr" name="schDivStr" value=""/>
      <input type="hidden" id="subAddr" name="subAddr" value=""/>   
</form>    
	<script type="text/javascript">
  	$(document).ready(function (){
  		searchStore(1);
  	});
  	</script>
      <div class="c-modal__content">
        <div class="c-modal__header">
          <h2 class="c-modal__title" id="buy-store-modal__title">구매가능 매장</h2>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body u-pb--0">
          <div class="c-form c-form--type2 u-block">
            <div class="c-form__group" role="group" aria-labelledby="inpAddress">
              <div class="c-select-wrap u-mt--0">
                <select id="selSchDivStr" name="selSchDivStr" title="광역시/도 선택" class="c-select" onchange="selectSubAddrAjax();">
                     <option value="전국">전국</option>
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
                <select class="c-select" title="시/구/군 선택" id="selSubAddr" name="selSubAddr" disabled="">
                  <option value='0'>전체</option>
                </select>
              </div>	
              <div class="c-input-wrap u-mt--16">
                <input class="c-input" type="text" id="selSearchStr" name="selSearchStr" maxlength="20" style="ime-mode:active;" placeholder="주소 또는 매장명" title="주소 또는 매장명 입력">
                <button class="c-button c-button-round--md c-button--primary" type="button" onclick="searchStore(1);">검색</button>
              </div>
              
              <p class="c-text c-text--fs15 u-mt--16">
                <b>광역시/도 &gt; 시/구/군 순</b>으로 주소를 선택해주세요.
              </p>
            </div>
            
            <!-- 구매가능 매장 없는 경우-->
            <div id="store_result_empty_div" class="c-box c-box--type1 c-expand--pop u-plr--48 u-mt--48">
              <div class="store-result">
                <h3 class="c-heading c-heading--fs17 c-heading--regular u-mt--20">구매가능 매장</h3>
                <hr class="c-hr c-hr--title">
                <div class="c-nodata">
                  <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                  <p class="c-nodata__text">검색 결과가 없습니다.</p>
                </div>
              </div>
            </div>
            <!-- 구매가능 매장 있는 경우-->
            
            <div id="store_result_div" class="c-box c-box--type1 c-expand--pop u-plr--48 u-mt--48 u-pb--48" style="display: none;">
              <div class="store-result">
                <h3 class="c-heading c-heading--fs17 c-heading--regular u-mt--20">구매가능 매장</h3>
                <p class="store-result__text">
                  <b id="store_result_b_cnt" class="u-co-point-4"></b>
                  검색되었습니다.
                </p>
                <hr class="c-hr c-hr--title">
                <ul id="store-list" class="store-list">
                  <li class="store-list__item">
                    <dl class="store-list__info">
                      <dt>대치삼성점</dt>
                      <dd>
                        <p>서울특별시 강남구 테헤란로 98길 8, 1층</p>
                        <p>02-6916-1500</p>
                      </dd>
                    </dl>
                    <dl class="store-list__sub">
                      <dt>갤럭시 M12</dt>
                      <dd>
                        <span>재고 보유</span>
                      </dd>
                    </dl>
                    <button class="c-button c-button--xsm c-button--white c-button-round--xsm">
                      <i class="c-icon c-icon--map" aria-hidden="true"></i>지도보기
                    </button>
                  </li>
                  <li class="store-list__item">
                    <dl class="store-list__info">
                      <dt>대치역점</dt>
                      <dd>
                        <p>서울특별시 강남구 테헤란로 98길 8, 1층</p>
                        <p>02-6916-1500</p>
                      </dd>
                    </dl>
                    <dl class="store-list__sub">
                      <dt>갤럭시 M12</dt>
                      <dd>
                        <span>재고 보유</span>
                      </dd>
                    </dl>
                    <button class="c-button c-button--xsm c-button--white c-button-round--xsm">
                      <i class="c-icon c-icon--map" aria-hidden="true"></i>지도보기
                    </button>
                  </li>
                </ul><!-- 페이징 영역-->
                <nav id="phoneIventoryPaging" class="c-paging u-mt--40" aria-label="검색결과">
                  <!-- 이동할 페이지 없는 경우 .is-disabled 클래스 추가-->
                  <!-- a.c-button.is-disabled(href='#')-->
                  <a class="c-button" href="javascript:void(0);">
                    <i class="c-icon c-icon--triangle-start" aria-hidden="true"></i>
                    <span class="c-hidden">처음</span>
                  </a>
                  <a class="c-button" href="javascript:void(0);">
                    <span>이전</span>
                  </a>
                  <a class="c-paging__anchor c-paging__number" href="javascript:void(0);">1</a>
                  <a class="c-paging__anchor c-paging__number" href="javascript:void(0);">2</a>
                  <a class="c-paging__anchor c-paging__anchor--current c-paging__number" href="javascript:void(0);">
                    <span class="c-hidden">현재 페이지</span>3
                  </a>
                  <a class="c-paging__anchor c-paging__number" href="javascript:void(0);">4</a>
                  <a class="c-button" href="javascript:void(0);">
                    <span>다음</span>
                  </a>
                  <a class="c-button" href="javascript:void(0);">
                    <i class="c-icon c-icon--triangle-end" aria-hidden="true"></i>
                    <span class="c-hidden">마지막</span>
                  </a>
                </nav>
              </div>
            </div>
          </div>
        </div>
      </div>