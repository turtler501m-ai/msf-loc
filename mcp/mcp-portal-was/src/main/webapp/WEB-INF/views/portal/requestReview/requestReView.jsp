<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>


<t:insertDefinition name="layoutGnbDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/review/requestReView.js?version=22-10-13"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    </t:putAttribute>



    <t:putAttribute name="contentAttr">
        <div class="ly-loading" id="subbody_loading">
            <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
        </div>
        <div class="ly-content" id="main-content">
          <input type="hidden" id="certify" value="N" />
          <input type="hidden" id="certifyYn" value="N" />
          <input type="hidden" id="reviewCategory" value="review4" />
          <input type="hidden" id=questId value="${questId}"/>
           <input type="hidden" id=reviewVal value="${reviewVal}"/>
          <div class="ly-page--title">
            <h2 class="c-page--title">사용후기</h2>
          </div>
          <div class="c-tabs c-tabs--type1">
            <div class="c-tabs__list">
              <a class="c-tabs__button is-active" id="tab1" role="tab" aria-selected="true" href="/requestReView/requestReView.do"><h3>사용후기 보기</h3></a>
              <a class="c-tabs__button" id="tab2" role="tab" aria-selected="false" href="/requestReView/requestReviewForm.do">후기 작성하기</a>
            </div>
          </div>

          <!-- 이번달 사용후기 -->
          <c:if test="${reviewList eq 'reviewList'}">
              <div class="review-rank">
                  <div class="review-rank-wrap">
                      <h3 class="review-rank__title">
                            <img src="/resources/images/portal/event/review_rank_title.png" alt="우수 사용후기">
                      </h3>
                      <ul class="review-rank__list">

                      </ul>
                  </div>
              </div>
        </c:if>

          <div class="c-tabs__panel u-block">
            <div>
              <div class="c-row c-row--lg">
                <div class="swiper-banner" id="swiperReviewBanner">
                  <div class="swiper-container">
                    <div class="swiper-wrapper" id="swiperArea">

                    </div>
                  </div>
                  <button class="swiper-banner-button--next swiper-button-next" type="button"></button>
                  <button class="swiper-banner-button--prev swiper-button-prev" type="button"></button>
                </div>

                <!-- 사용후기 차트 -->
               <c:if test="${!empty reviewCodeList}">
                    <div class="review-filter">
                          <h3 class="c-form__title--type2 u-mt--64">사용후기 REVIEW</h3>
                          <div class="c-filter">
                            <div class="c-filter__inner" id="mainTab">
                                <c:forEach items="${reviewCodeList}" var="item">
                                     <!-- 선택된 버튼  is-active 클래스 추가 -->
                                        <button class="c-button" name="reviewBtn" onclick="reviewTabClick(this);" eventCategory="review${item.questionId}">${item.questionMm}</button>
                                  </c:forEach>
                              </div>
                          </div>

                           <ul class="review-chart">

                          </ul>
                         <div class="c-nodata" id="noDataVal">
                          <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                          <p class="c-nodata__text">통계를 집계 중입니다.</p>
                         </div>
                    </div>
                </c:if>
                <div class="c-form-wrap u-mt--64">
                  <div class="c-form-row c-col2 u-al-center">
                    <div class="c-form c-form--type2 u-width--460">
                      <label class="c-label c-hidden" for="selEventCd">사용후기 검색구분</label>
                      <select class="c-select" id="selEventCd" name="selEventCd">
                        <option value="">전체보기 (${eventTotal})</option>
                        <c:forEach var="nmcpMainCdDtlDtoList" items="${nmcpMainCdDtlDtoList}">
                            <option value="${nmcpMainCdDtlDtoList.dtlCd}">${nmcpMainCdDtlDtoList.dtlCdNm}(${nmcpMainCdDtlDtoList.eventCdCtn})</option>
                        </c:forEach>
                      </select>
                      <button class="c-button c-button-round--md c-button--primary" id="selectEventReview">조회</button>
                    </div>
                    <div class="c-checktype-row">
                    <c:if test="${nmcp:hasLoginUserSessionBean()}">
                      <input class="c-checkbox" id="chkMyReview" type="checkbox" name="chkA">
                      <label class="c-label u-mb--0 u-mr--8" for="chkMyReview">내 후기 보기</label>
                    </c:if>
                      <input class="c-checkbox" id="chkMyReviewBest" type="checkbox" name="reviewFilter">
                      <label class="c-label u-mb--0 u-mr--8" for="chkMyReviewBest">우수 후기 보기</label>
                      <input class="c-checkbox" id="chkMyReviewSns" type="checkbox" name="reviewFilter">
                      <label class="c-label u-mb--0 u-mr--0" for="chkMyReviewSns">SNS 공유 후기 보기</label>
                    </div>
                  </div>
                </div><!-- 데이터 있는 경우-->
                <div class="c-accordion review" id="accordionReview" data-ui-accordion>
                  <ul class="c-accordion__box" id="reviewBoard">

                  </ul>
                </div>
                <div class="board_list_table_paging" id="paging">
                <nmcp:pageInfo pageInfoBean="${pageInfoBean}" function="requestReview"/>
                </div>

              </div>
            </div>
          </div>
          <div class="c-tabs__panel">
            <div></div>
          </div>
        </div>
    </t:putAttribute>


</t:insertDefinition>