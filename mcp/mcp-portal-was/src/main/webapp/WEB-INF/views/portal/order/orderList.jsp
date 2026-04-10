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
       		<input type="hidden" name="orderStdt" id="orderStdt" />
       		<input type="hidden" name="orderEddt" id="orderEddt" />
       	</form>
       	<form name="orderSelfForm" id="orderSelfForm">
       		<input type="hidden" name="orderSelfPageNo" id="orderSelfPageNo" />
       		<input type="hidden" name="orderSelfLastPage" id="orderSelfLastPage" />
       		<input type="hidden" name="selfStdt" id="selfStdt" />
       		<input type="hidden" name="selfEddt" id="selfEddt" />
       	</form>
        <div>
          <div class="c-row c-row--xlg">
            <!-- 백업용 다시 말바꾸질몰라서.. -->
            <!--
            <div class="c-tabs c-tabs--type2">
              <div class="c-tabs__list">
                <button class="c-tabs__button" data-value="taba11panel" id="tab11" >가입신청 배송조회</button>
                <button class="c-tabs__button" data-value="taba22panel" id="tab22" >유심구매 배송조회</button>
              </div>
            </div>
            -->

            <div class="c-tabs c-tabs--type2" data-ui-tab>
              <div class="c-tabs__list" role="tablist">
                <button class="c-tabs__button" id="tab11" role="tab" data-value="taba11panel" aria-controls="taba11panel" aria-selected="false" tabindex="-1">가입신청 배송조회</button>
                <button class="c-tabs__button" id="tab22" role="tab" data-value="taba22panel" aria-controls="taba22panel" aria-selected="false" tabindex="-1">유심구매 배송조회</button>
              </div>
            </div>
            <div class="c-tabs__panel" id="taba11panel" role="tabpanel" aria-labelledby="tab11" tabindex="-1">
            <!-- 백업용 다시 말바꾸질몰라서.. -->
            <!-- <div class="c-tabs__panel" id="taba11panel"> -->

              <div>
                <div class="c-row c-row--lg">
                  <div class="c-filter c-filter--period u-mt--64" role="group" aira-labelledby="radGroupA">
                    <h3 class="c-filter__title" id="radGroupA">조회기간</h3>
                    <div class="u-flex-between c-box c-box--type3">
                    <div class="c-filter__inner">
	                      <div class="c-checktype-row">
	                        <input class="c-radio c-radio--button c-radio--button--type3" id="radA1" type="radio" name="radAOne">
	                        <label class="c-label" for="radA1">1주</label>
	                        <input class="c-radio c-radio--button c-radio--button--type3" id="radA2" type="radio" name="radAOne" checked>
	                        <label class="c-label" for="radA2">1개월</label>
	                        <input class="c-radio c-radio--button c-radio--button--type3" id="radA3" type="radio" name="radAOne">
	                        <label class="c-label" for="radA3">2개월</label>
	                      </div>
                      </div>
                      <div class="c-form">
                        <div class="c-input-wrap u-width--300">
                          <input class="c-input flatpickr-input flatpickr" id="range" type="text" placeholder="시작일-종료일" title="날짜입력">
                          <span class="c-button c-button--icon c-button--calendar" type="button" title="날짜입력창 버튼" tabindex="-1">
                            <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                          </span>
                        </div>
                      </div>
                      <button type="button" class="c-button c-button-round--md c-button--primary" onclick="goOrderSearch(1)">조회</button>
                    </div>
                  </div>
                  <p class="c-bullet c-bullet--dot u-co-gray u-mt--16">현재일 기준 1년 이내까지만 조회가 가능합니다.</p>

                  <ol id="olOrderstepperArea" class="c-stepper c-stepper--type2 u-mt--56 u-mb--56">
                    <!-- 건수가 있는 경우 is-active 클래스추가-->

                    <li class="c-stepper__item">
                      <span class="c-stepper__title">접수대기</span>
                      <span class="c-stepper__num">0</span>
                    </li>
                    <li class="c-stepper__item">
                      <span class="c-stepper__title">배송대기</span>
                      <span class="c-stepper__num">0</span>
                    </li>
                    <li class="c-stepper__item">
                      <span class="c-stepper__title">배송중</span>
                      <span class="c-stepper__num">0</span>
                    </li>
                    <li class="c-stepper__item">
                      <span class="c-stepper__title">개통대기</span>
                      <span class="c-stepper__num">0</span>
                    </li>
                    <li class="c-stepper__item">
                      <span class="c-stepper__title">개통완료</span>
                      <span class="c-stepper__num">0</span>
                    </li>

                  </ol>

                  <!-- 데이터 있는 경우-->
                  <ul id="orderUlArea" class="c-list u-mt--8">

                  </ul>
                  <!-- 데이터 없는 경우-->
                  <div id="orderNoDataDiv" class="c-nodata u-bt-gray-3 u-bb-gray-3 u-mt--48" style="display: none;">
                    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                    <p class="c-nodata__text">조회 결과가 없습니다.</p>
                  </div>
                  <!-- //-->
                  <nav id="orderPaging" class="c-paging" aria-label="조회결과" style="display: none;">
                  </nav>
                </div>
              </div>

            </div>
            <div class="c-tabs__panel" id="taba22panel" role="tabpanel" aria-labelledby="tab22" tabindex="-1">
            <!-- 백업용 다시 말바꾸질몰라서.. -->
            <!-- <div class="c-tabs__panel" id="taba22panel"> -->

              <div>
                <div class="c-row c-row--lg">
                  <div class="c-filter c-filter--period u-mt--64" role="group" aira-labelledby="radGroupA2">
                    <h3 class="c-filter__title" id="radGroupA2">조회기간</h3>
                    <div class="u-flex-between c-box c-box--type3">
                    <div class="c-filter__inner">
                      <div class="c-checktype-row">
                        <input class="c-radio c-radio--button c-radio--button--type3" id="radB1" type="radio" name="radBTwo" >
                        <label class="c-label" for="radB1">1주</label>
                        <input class="c-radio c-radio--button c-radio--button--type3" id="radB2" type="radio" name="radBTwo" checked>
                        <label class="c-label" for="radB2">1개월</label>
                        <input class="c-radio c-radio--button c-radio--button--type3" id="radB3" type="radio" name="radBTwo">
                        <label class="c-label" for="radB3">2개월</label>
                      </div>
                      </div>
                      <div class="c-form">
                        <div class="c-input-wrap u-width--300">
                          <input class="c-input flatpickr-input flatpickr" id="range_2" type="text" placeholder="시작일-종료일" title="날짜입력">
                          <span class="c-button c-button--icon c-button--calendar" type="button" title="날짜입력창 버튼" tabindex="-1">
                            <i class="c-icon c-icon--calendar-black" aria-hidden="true"></i>
                          </span>
                        </div>
                      </div>
                      <button type="button" class="c-button c-button-round--md c-button--primary" onclick="goSelfSearch(1)">조회</button>
                    </div>
                  </div>
                  <p class="c-bullet c-bullet--dot u-co-gray u-mt--16">현재일 기준 1년 이내까지만 조회가 가능합니다.</p>

                  <ol id="olSelfStepperArea"  class="c-stepper c-stepper--type2 u-mt--56 u-mb--56">
                    <!-- 건수가 있는 경우 is-active 클래스추가-->
                    <li class="c-stepper__item">
                      <span class="c-stepper__title">접수대기</span>
                      <span class="c-stepper__num">0</span>
                    </li>
                    <li class="c-stepper__item">
                      <span class="c-stepper__title">배송대기</span>
                      <span class="c-stepper__num">0</span>
                    </li>
                    <li class="c-stepper__item">
                      <span class="c-stepper__title">배송중</span>
                      <span class="c-stepper__num">0</span>
                    </li>
                    <li class="c-stepper__item">
                      <span class="c-stepper__title">배송완료</span>
                      <span class="c-stepper__num">0</span>
                    </li>
                  </ol>

                  <!-- 데이터 있는 경우-->
                  <ul id="selfUlArea" class="c-list u-mt--8">
                    <!--
                    <li class="c-list__item">
                      <a class="c-list__head c-list__anchor" href="">
                        <div class="c-list__title">
                          <span class="c-hidden">주문번호</span>
                          <b>00000000</b>
                          <div class="c-list__sub">
                            <span class="c-hidden">주문일자</span>0000.00.00
                          </div>
                        </div>
                        <span class="c-hidden">상세보기</span>
                      </a>
                      <div class="c-list__body">
                        <div class="detail">
                          [2022-02-04] 이미지 마크업 수정
                          <div class="detail__image">
                            <img src="../../resources/images/portal/content/temp_usim.png" alt="" aria-hidden="true">
                          </div>[$2022-02-04] 이미지 마크업 수정
                          <div class="detail__title">
                            <span class="detail__sub-title c-hidden">상품명/금액</span>
                            <b>일반유심</b>
                            <b>000,000원</b>
                          </div>
                          <div class="detail__item">
                            <span class="detail__sub-title c-hidden">배송상태</span>
                            <b class="u-co-point-4">접수대기</b>
                          </div>
                          <div class="c-button-wrap">
                            <button class="c-button c-button-round--sm c-button--white" type="button">배송조회</button>
                          </div>
                        </div>
                      </div>
                    </li>
                    <li class="c-list__item">
                      <a class="c-list__head c-list__anchor" href="">
                        <div class="c-list__title">
                          <span class="c-hidden">주문번호</span>
                          <b>00000000</b>
                          <div class="c-list__sub">
                            <span class="c-hidden">주문일자</span>0000.00.00
                          </div>
                        </div>
                        <span class="c-hidden">상세보기</span>
                      </a>
                      <div class="c-list__body">
                        <div class="detail">
                          [$2022-02-04] 이미지 마크업 수정
                          <div class="detail__image">
                            <img src="../../resources/images/portal/content/temp_usim.png" alt="" aria-hidden="true">
                          </div>[$2022-02-04] 이미지 마크업 수정
                          <div class="detail__title">
                            <span class="detail__sub-title c-hidden">상품명/금액</span>
                            <b>일반유심</b>
                            <b>000,000원</b>
                          </div>
                          <div class="detail__item">
                            <span class="detail__sub-title c-hidden">배송상태</span>
                            <b class="u-co-point-4">배송대기</b>
                          </div>
                          <div class="c-button-wrap">
                            <button class="c-button c-button-round--sm c-button--white" type="button">배송조회</button>
                          </div>
                        </div>
                      </div>
                    </li>
                    -->
                  </ul>
                  <!-- 데이터 없는 경우-->
                  <div id="orderNoDataDiv_2" class="c-nodata u-bt-gray-3 u-bb-gray-3 u-mt--48" style="display: none;">
                    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                    <p class="c-nodata__text">조회 결과가 없습니다.</p>
                  </div>
                  <!-- //-->
                  <nav id="orderPaging_2" class="c-paging" aria-label="조회결과" style="display: none;">
                  </nav>
                </div>
              </div>


            </div>
          </div>
        </div>

