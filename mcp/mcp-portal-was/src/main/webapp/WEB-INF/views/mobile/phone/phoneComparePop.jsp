<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
    <div class="c-modal" id="phone-compare-dialog" role="dialog" tabindex="-1" aria-describedby="#phone-compare-title">
	    <form id="appForm" name="appForm" method="post" action="/m/appForm/appFormDesignView.do">
	    	<input type="hidden" name="hndsetModelId" />
	    	<input type="hidden" name="rateCd" /> 
	    	<input type="hidden" name="prodId" />    
	    	<input type="hidden" name="outUnitPric" value=""/>
	    	<input type="hidden" name="selPric" value=""/>
	    	<input type="hidden" name="prodCtgType" value=""/>   		
		</form>
	    <div class="c-modal__dialog c-modal__dialog--full" role="document">
	      <div class="c-modal__content">
	        <div class="c-modal__header">
	          <h1 class="c-modal__title" id="phone-compare-title">휴대폰 비교하기</h1>
	          <button class="c-button" data-dialog-close>
	            <i class="c-icon c-icon--close" aria-hidden="true"></i>
	            <span class="c-hidden">팝업닫기</span>
	          </button>
	        </div>
	        <div id="phoneCompareModalBody" class="c-modal__body">
	        	<div class="content-wrap">
		          <h3 class="c-heading c-heading--type5 u-mb--40 u-ta-center">
		            <b>요금부터 성능까지 한눈에</b>
		            <br>비교하세요!
		          </h3>
		          <ul class="phone-select">
		            <!-- case1 : 휴대폰 선택 후-->
		            <li class="phone-select__item">
		              <!-- 휴대폰, 용량/색상 선택 영역-->
		              <select id="phoneSelect_0" name="phoneSelect_0" class="c-select c-select--type2">
		                <option value="">휴대폰 선택</option>
		              </select>
		              <select id="colorSelect_0" name="colorSelect_0" class="c-select c-select--type2">
		                <option>용량/색상</option>
		              </select>
		              <span id="phone_img_div_0" >
		              </span><!-- 요금제 선택 영역-->
		              <select id="rateSelect_0" name="rateSelect_0" class="c-select c-select--type2 u-mt--40">
		                <option value="">요금제 선택</option>
		              </select>
		              <span id="phone_dl_0">
		              </span>
		              <button id="phone_button_0" type="button" class="c-button c-button--lg c-button--full c-button--red c-button--h48 u-mt--32">가입하기</button>
		            </li><!-- case2 : 휴대폰 선택 전-->
		            <li class="phone-select__item">
		              <!-- 휴대폰, 용량/색상 선택 영역-->
		              <select id="phoneSelect_1" name="phoneSelect_1" class="c-select c-select--type2">		              
		                <option value="">휴대폰 선택</option>
		              </select>
		              <select id="colorSelect_1" name="colorSelect_1" class="c-select c-select--type2">
		                <option value="">용량/색상</option>
		              </select>
		              <span id="phone_img_div_1" >
		              </span><!-- 요금제 선택 영역-->
		              <select id="rateSelect_1" name="rateSelect_1" class="c-select c-select--type2 u-mt--40">
		                 <option value="">요금제 선택</option>
		              </select>
		              <span id="phone_dl_1">
		              </span>
		              <button id="phone_button_1" type="button" class="c-button c-button--lg c-button--full c-button--red c-button--h48 u-mt--32 is-disabled">가입하기</button>
		            </li>
		          </ul>
		          <hr class="c-hr c-hr--type1 c-expand u-mb--0"><!-- 월 납부금액 리스트-->
		          <h3 class="c-heading c-heading--type2">월 납부금액</h3>
		          <ul class="phone-comparison">
		            <li id="phone_li_0" class="phone-comparison__item">
		            </li>
		            <li id="phone_li_1" class="phone-comparison__item">
		            </li>
		          </ul><!-- 기기사양 리스트-->
		          <h3 class="c-heading c-heading--type2">기기사양</h3>
		          <ul class="phone-comparison">
		            <li id="phone_comp_li_0" class="phone-comparison__item">
		            </li>
		            <li id="phone_comp_li_1" class="phone-comparison__item">
		            </li>
		          </ul>
		          <hr class="c-hr c-hr--type2">
		          <ul class="c-text-list c-bullet c-bullet--dot">
		            <li class="c-text-list__item u-co-gray">위 정보는 휴대폰과 요금제에 대한 가격만 비교하는 정보로 당해 프로모션에 따라 월 납부금액이 변경될 수 있습니다.</li>
		            <li class="c-text-list__item u-co-gray">각 휴대폰별로 적용된 약정기간이 다를 수 있으므로, 자세한 가격정보는 휴대폰 별 상세 페이지에서 확인하시기 바랍니다.</li>
		          </ul>
		        </div>  
	        </div>
	      </div>
	    </div>
	  </div>