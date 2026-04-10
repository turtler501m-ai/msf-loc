<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
       	<form name="orderUsmForm" id="orderUsmForm">
       		<input type="hidden" name="orderUsimPageNo" id="orderUsimPageNo" />
       		<input type="hidden" name="orderUsimLastPage" id="orderUsimLastPage" />
       	</form>
       	<form name="orderSelfForm" id="orderSelfForm">
       		<input type="hidden" name="orderSelfPageNo" id="orderSelfPageNo" />
       		<input type="hidden" name="orderSelfLastPage" id="orderSelfLastPage" />
       	</form>
       	<input type="hidden" id="hdCurrentViewCount" />
       	<input type="hidden" id="hdTotalCount" />
       	<input type="hidden" id="hdCurrentViewCount_2" />
       	<input type="hidden" id="hdTotalCount_2" />
    
        	<div class="c-tabs c-tabs--type3" data-tab-active="0">
            <div class="c-tabs__list c-expand" data-tab-list="" role="tablist">
              <button type="button" class="c-tabs__button is-active" data-tab-header="#tabB1-panel" id="id_5z4u0tm55" role="tab" aria-controls="#tabB1-panel" aria-selected="true" tabindex="0">가입신청 배송조회</button>
              <button type="button" class="c-tabs__button" data-tab-header="#tabB2-panel" id="id_sgltr8y42" role="tab" aria-controls="#tabB2-panel" aria-selected="false" tabindex="-1" >유심구매 배송조회</button>
            </div>
            <div class="c-tabs__panel" id="tabB1-panel" role="tabpanel" aria-labelledby="id_5z4u0tm55" aria-hidden="true" tabindex="0" style="display: block;">
              <p class="c-bullet c-bullet--dot u-co-gray">최근 1개월 까지 주문한 내역을 보실 수 있습니다. 이전 내역은 PC에서 확인하세요.</p><!-- case1 : 데이터 없을 경우-->
              
              <div id="noDataDivArea" class="c-nodata" >
                <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                <p class="c-noadat__text">조회 결과가 없습니다.</p>
              </div>
              
              
              <ol class="deli-step c-expand u-mt--24" id="orderOlArea">
                <li class="is-active">
                  <b>3</b>
                  <span>접수대기</span>
                </li>
                <li>
                  <b>0</b>
                  <span>배송대기</span>
                </li>
                <li>
                  <b>0</b>
                  <span>배송중</span>
                </li>
                <li>
                  <b>0</b>
                  <span>개통대기</span>
                </li>
                <li class="is-active">
                  <b>3</b>
                  <span>개통완료</span>
                </li>
              </ol>
              <span id="dataLoopArea">
              
            </span>   
             <div id="moreDivArea" class="c-button-wrap u-mt--8">
            <button type="button" class="c-button c-button--full" id="searchNext" onclick="viewMore();">더보기<span class="c-button__sub" id="pageNav"><span id="currentViewCount">1</span>/<span id="totalCount"></span></span>
              <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
            </button>
            <span id="maxPage" style="display: none;"></span>
         	 </div>
            </div>
            <div class="c-tabs__panel" id="tabB2-panel" role="tabpanel" aria-labelledby="id_sgltr8y42" aria-hidden="false" tabindex="0" style="display: none;">
            
            
            
            <p class="c-bullet c-bullet--dot u-co-gray">최근 1개월 까지 주문한 내역을 보실 수 있습니다. 이전 내역은 PC에서 확인하세요.</p><!-- case1 : 데이터 없을 경우-->
              
              <div id="noDataDivArea_2" class="c-nodata" >
                <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                <p class="c-noadat__text">조회 결과가 없습니다.</p>
              </div>
              
              
              <ol class="deli-step c-expand u-mt--24" id="orderOlArea_2">
                <li class="is-active">
                  <b>3</b>
                  <span>접수대기</span>
                </li>
                <li>
                  <b>0</b>
                  <span>배송대기</span>
                </li>
                <li>
                  <b>0</b>
                  <span>배송중</span>
                </li>
                <li>
                  <b>0</b>
                  <span>개통대기</span>
                </li>
                <li class="is-active">
                  <b>3</b>
                  <span>개통완료</span>
                </li>
              </ol>
              <span id="dataLoopArea_2">
              
            </span>   
             <div id="moreDivArea_2" class="c-button-wrap u-mt--8">
            <button type="button" class="c-button c-button--full" id="searchNext" onclick="viewMore_2();">더보기<span class="c-button__sub" id="pageNav_2"><span id="currentViewCount_2">1</span>/<span id="totalCount_2"></span></span>
              <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
            </button>
            <span id="maxPage_2" style="display: none;"></span>
         	 </div>
            
            
            
            </div>
          </div>
     
          