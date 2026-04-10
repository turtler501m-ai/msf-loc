<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />

<script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/resources/js/portal/search/searchView.js?ver=${jsver}"></script>

    <!--통합검색 영역-->
    <div class="ly-search">
      <div class="search">
        <div class="c-form c-form--search c-form--search--type2">
          <div class="c-form__input">
            <input class="c-input" id="searchInp" type="text" placeholder="검색어를 입력해주세요" role="combobox" aria-autocomplete="list" aria-expanded="false" aria-owns="autoList" aria-activedescentant="selectedAuto">
            <label class="c-form__label c-hidden" for="searchInp">검색어 입력</label>
            <button class="c-button c-button--reset" id="resetBtn">
              <span class="c-hidden">초기화</span>
              <i class="c-icon c-icon--reset" aria-hidden="true"></i>
            </button>
            <button class="c-button c-button-round--sm c-button--red" id="searchInputBtn" href="javascript:void(0);" onclick="return false;">검색</button>
          </div>
        </div>
        <!--자동완성( 인풋 데이터 입력 시 자동완성 활성화 - 활성화 처리: is-active 추가)-->
        <ul class="search-auto" id="autoList" role="listbox" style="display:is-active">
          
        </ul>
        <h3 class="search__title u-co-red">추천 검색어</h3>
        <div class="c-button-wrap c-button-wrap--left" id="recommendList">
        </div>
        <h3 class="search__title">인기 검색어</h3>
        <ol class="search-list" id="popularList">
          
        </ol>
        <div class="search__bottom">
          <button class="c-button">
            <i class="c-icon c-icon--close-red" id="closePopup" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
      </div>
    </div>
    