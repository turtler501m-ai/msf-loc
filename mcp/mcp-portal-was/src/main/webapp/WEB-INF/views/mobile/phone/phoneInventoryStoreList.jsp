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
  	<div class="c-modal__content">
  	<script type="text/javascript">
  	$(document).ready(function (){
  		searchStore(1);
  	});
  	</script>
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
        <div class="c-modal__header">
          <h1 class="c-modal__title" id="find-store-title">구매가능 매장</h1>
          <button class="c-button" data-dialog-close>
            <i class="c-icon c-icon--close" aria-hidden="true"></i>
            <span class="c-hidden">팝업닫기</span>
          </button>
        </div>
        <div class="c-modal__body u-pb--0">
          <div class="c-form">
            <div class="c-form__group" role="group" aria-labelledby="inpAddress">
              
              <div class="c-select-wrap">
                <select id="selSchDivStr" name="selSchDivStr" title=" 지역을 선택 하세요" class="c-select" onchange="selectSubAddrAjax();">
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
                <select class="c-select" id="selSubAddr" name="selSubAddr" disabled="">
                <option value='0'>전체</option>
                </select>
              </div>
              <div class="c-input-wrap">
                <input class="c-input" type="text" id="selSearchStr" name="selSearchStr" placeholder="주소 또는 매장명" maxlength="20" title="주소 또는 매장명">
                <button class="c-button c-button--sm c-button--underline" type="button" onclick="searchStore(1);">검색</button>
              </div>
              <p class="c-text c-text--type3 u-mt--8">
                <b>광역시/도 &gt; 시/구/군 순</b>으로 주소를 선택해주세요. 
              </p>
            </div>
          </div><!-- case1 - 검색 전-->
          
         <div class="store-result c-expand" id="searchArea">
           <strong class="store-result__heading">구매가능 매장</strong>
           <hr class="c-hr c-hr--type2">
           <div class="store-result__cont">매장 또는 지역을 선택해주세요.</div>
         </div><!-- case2 - 검색 후-->
         
       <div class="c-button-wrap" id="searchNext" onclick="goNextPage();" style="display:none;">
			<button class="c-button c-button--full">
				더보기 <span class="c-button__sub" id="pageNav">${listCount}/${pageInfoBean.totalCount }</span>
				<i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
			</button>
			<span id="maxPage" style="display: none;"></span>
	   </div>  
       </div>
     </div>