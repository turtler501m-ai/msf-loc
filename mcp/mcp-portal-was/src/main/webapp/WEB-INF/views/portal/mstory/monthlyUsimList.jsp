<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="layoutMainDefault">

    <t:putAttribute name="metaTagAttr">
        <meta property="og:title" content="M Story I kt M모바일 공식 다이렉트몰"/>
        <meta property="og:image" content="https://${header['host']}/ktMmobile_og.png" />
    </t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">

         <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/portal/mstory/monthlyUsimList.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">
          <div class="ly-page--title">
            <h2 class="c-page--title">M Story</h2>
          </div>
          <div class="m-storage">
            <div class="c-tabs c-tabs--type1" data-ui-tab>
              <div class="c-tabs__list" role="tablist">
                <button class="c-tabs__button" id="monthlyM" role="tab" aria-controls="tab1_panel" aria-selected="false" tabindex="-1">월간 M</button>
                <button class="c-tabs__button" id="snsList" role="tab" aria-controls="tab2_panel" aria-selected="false" tabindex="-1">고객소통</button>
              </div>
            </div>
            <input type="text" id="param" value="" hidden/>
            <input type="text" id="year" value="${year}" hidden/>
            <div class="c-tabs__panel no-bottom-spacing" id="tab1_panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
              <div class="m-select-wrap">
                  <div class="m-storage__select">
                    <label class="c-label c-hidden" for="yearSelect">월간유심 년도 선택</label>
                    <select id="yearSelect">
                      <option>2021</option>
                      <option>2020</option>
                      <option>2019</option>
                      <option>2018</option>
                      <option>2017</option>
                      <option>2016</option>
                    </select>
                  </div>
                  <button class="c-button c-button-round--xsm c-button--primary u-ml--10" id="searchMonthlyUsim">조회</button>
              </div>
              <div class="m-storage__content">
                <h3 class="c-heading c-heading--fs24 u-ta-center u-pt--32">매월 새롭고 다양한 이야기로 찾아 뵙는
                  <br>
                  <span class="u-fw--bold u-mt--8">[월간 You 心]</span>
                </h3>
              </div>
              <div class="m-storage-cover">
                <div class="c-row c-row--xlg u-ta-center">
                  <div class="swiper-container">
                    <div class="swiper-wrapper" id="mainList" style="height:auto;">

                    </div>
                  </div>
                  <button class="swiper-banner-button--next swiper-button-next" type="button" tabindex="-1"></button>
                  <button class="swiper-banner-button--prev swiper-button-prev" type="button" tabindex="-1"></button>
                  <button class="c-button c-button-round--sm c-button--white u-mt--48" id="snsShareBut" data-dialog-trigger="#modalShareAlert">공유하기</button>
                </div>
              </div>
            </div>
            <div class="c-tabs__panel" id="tab2_panel" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
              <!-- <div>고객소통(ET1003020000)</div> -->
            </div>
          </div>
        </div>

    </t:putAttribute>

</t:insertDefinition>
