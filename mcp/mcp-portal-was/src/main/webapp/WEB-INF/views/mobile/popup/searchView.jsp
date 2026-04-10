<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <script type="text/javascript" src="/resources/js/nmcpCommon.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/nmcpCommonComponent.js"></script>
	<script type="text/javascript" charset="UTF-8" src="/resources/js/mobile/popup/searchView.js"></script>
	
	<div class="c-modal__content">
	
	 <!-- 통합검색( 공통영역 )-->
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="total_search_title">통합검색</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" id="closePopup" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__search">
          <div class="c-form c-form--search">
            <div class="c-form__input">
              <input class="c-input" id="searchPopupText" type="text" placeholder="검색어를 입력해주세요" title="검색어 입력" autocomplete='off' maxlength="20">
              <!--[2022-03-07] 검색버튼 디자인 변경-->
              <button class="c-button c-button--search-bk" id="searchNewButton">
                <span class="c-hidden">검색어</span>
                <i class="c-icon c-icon--search-bk" aria-hidden="true"></i>
              </button>
              <button class="c-button c-button--reset" id="searchPopupTextReset">
                <span class="c-hidden">초기화</span>
                <i class="c-icon c-icon--reset" aria-hidden="true"></i>
              </button>
            </div>
          </div>
          <!--자동완성 영역('display:none' 삭제하고 사용하세요)-->
          <ul class="autocomplete" id="autocompleteResult" style="display:none ">
          </ul><!-- 자동완성 영역-->
        </div>
        <div class="c-modal__body overflow-x-hidden">
          <div class="c-filter c-filter--accordion c-expand u-mt--20">
            <button class="c-filter--accordion__button" id="btnRecommWord" aria-expanded="false">
              <div class="c-hidden">접기</div>
            </button>
            <div class="c-filter__inner" id="recommendList">
            <%-- 추천검색어 목록조회 10개 --%>
            </div>
          </div>
          <div class="popular-keyword c-expand u-mb--m32">
            <div class="popular-keyword__title">인기 검색어</div>
            <ul class="popular-keyword__list" id="popularList">
            </ul>
          </div>
        </div>
        
  <!-- //통합검색( 공통영역 )-->
	
	</div>
	