<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>


<t:insertDefinition name="layoutGnbDefault">
  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/portal/review/mcashReview.js"></script>
  </t:putAttribute>

  <t:putAttribute name="contentAttr">
    <div class="ly-loading" id="subbody_loading">
      <img src="/resources/images/portal/common/loading_logo.gif" alt="kt M 로고이미지">
    </div>
    <div class="ly-content" id="main-content">
      <input type="hidden" id=reviewVal value="${reviewVal}"/>
      <div class="ly-page--title">
        <h2 class="c-page--title">M쇼핑할인 사용후기</h2>
      </div>
      <div class="c-tabs c-tabs--type1" id="tabs">
        <div class="c-tabs__list">
          <a class="c-tabs__button is-active" id="mcashReviewTab" role="tab" aria-selected="true" href="/mcash/review/mcashReview.do"><h3>사용후기 보기</h3></a>
          <a class="c-tabs__button" id="mcashReviewFormTab" role="tab" aria-selected="false" href="#">후기 작성하기</a>
        </div>
      </div>

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

            <div class="c-form-wrap u-mt--64">
              <div class="c-form-row c-col2 c-flex--item-center u-al-center">
                <div class="c-form c-form--type2 u-width--460">
                  <label class="c-label c-hidden" for="selReviewType">사용후기 검색구분</label>
                  <select class="c-select" id="selReviewType" name="selReviewType">
                  </select>
                  <button class="c-button c-button-round--md c-button--primary" id="selectEventReview">조회</button>
                </div>
                <div class="c-checktype-row">
                  <c:if test="${nmcp:hasLoginUserSessionBean() and checkUserType eq 'Y'}">
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
              <ul class="c-accordion__box" id="mcashReviewBoard">

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