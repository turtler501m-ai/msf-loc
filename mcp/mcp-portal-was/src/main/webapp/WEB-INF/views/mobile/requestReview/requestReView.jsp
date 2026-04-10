
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="un"
    uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<un:useConstants var="Constants"
    className="com.ktmmobile.mcp.common.constants.Constants" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/review/requestReView.js"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">

    <div class="ly-content" id="main-content">
      <input type="hidden" id="certify" value="N"/>
      <input type="hidden" id="certifyYn" value="N" />
      <input type="hidden" id="platFormCd" value="${nmcp:getPlatFormCd()}" />
      <input type="hidden" id="appFormYn" value="${appFormYn}" />
       <input type="hidden" id="reviewCategory" value="review4" />
       <input type="hidden" id=questId value="${questId}"/>
       <input type="hidden" id=reviewVal value="${reviewVal}"/>
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">사용후기<button class="header-clone__gnb"></button>
        </h2>
      </div>
      <div class="c-tabs c-tabs--type2">
        <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
          <button type="button" class="c-tabs__button" onclick="javascript:location.href='/m/requestReView/requestReView.do';" data-tab-header="#tabA1-panel">사용후기 보기</button>
          <button type="button" class="c-tabs__button" onclick="javascript:location.href='/m/requestReView/requestReviewForm.do';" data-tab-header="#tabA2-panel">후기 작성하기</button>
        </div>
        <div class="c-tabs__panel" id="tabA1-panel">

          <!-- 이번달 사용후기 -->
          <c:if test="${reviewList eq 'reviewList'}">
              <div class="review-rank">
                  <div class="review-rank-wrap">
                      <h3 class="review-rank__title">
                          <img src="/resources/images/mobile/event/review_rank_title.png" alt="우수 사용후기">
                      </h3>
                      <ul class="review-rank__list">

                      </ul>
                  </div>
              </div>
         </c:if>

          <div class="swiper-container swiper-event-banner c-expand u-mt--32" id="swiperCardBanner" >
            <div class="swiper-wrapper">

            </div>
          </div>
        <c:if test="${!empty reviewCodeList}">
          <!-- 사용후기 차트 -->
          <div class="review-filter">
               <span class="c-form__title">사용후기 REVIEW</span>
              <div class="c-filter">
                  <div class="c-filter__inner" id="mainTab">
                      <c:forEach items="${reviewCodeList}" var="item">
                           <!-- 선택된 버튼  is-active 클래스 추가 -->
                            <button class="c-button" onclick="reviewTabClick(this);" name="reviewBtn" eventCategory="review${item.questionId}">${item.questionMm}</button>
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
          <div class="c-form">
            <div class="c-form__title flex-type">후기 골라보기</div>

    <%-- 			<h4 id="recommendInfo">
                    <span class="red">${resDto.percent == '100.0' ? '100' : resDto.percent }%</span>의 고객님이 <span class="red">추천</span>합니다!
                </h4> --%>

            <select class="c-select" title="이벤트 선택" id="selEventCd" name="selEventCd">
              <option value="">전체보기(${eventTotal})</option>
                <c:forEach var="nmcpMainCdDtlDtoList" items="${nmcpMainCdDtlDtoList}">
                    <option value="${nmcpMainCdDtlDtoList.dtlCd}">${nmcpMainCdDtlDtoList.dtlCdNm}(${nmcpMainCdDtlDtoList.eventCdCtn})</option>
                </c:forEach>
            </select>
            <div class="u-ta-right  u-mt--16">
                <c:if test="${nmcp:hasLoginUserSessionBean()}">
                    <input class="c-checkbox" id="chkMyReview" type="checkbox" name="chkMy">
                    <label class="c-label u-pl--28 u-mr--8" for="chkMyReview">내 후기</label>
                </c:if>
                <input class="c-checkbox" id="chkMyReviewBest" type="checkbox" name="reviewFilter">
                <label class="c-label u-pl--28 u-mr--8" for="chkMyReviewBest">우수 후기</label>
                <input class="c-checkbox" id="chkMyReviewSns" type="checkbox" name="reviewFilter">
                <label class="c-label u-pl--28 u-mr--8" for="chkMyReviewSns">SNS 공유 후기</label>
            </div>
          </div>
          <div class="u-mt--36" id="reviewBody">

            <ul class="review" id="reviewBoard">

            </ul>
          </div>
          <div class="c-button-wrap">
            <button class="c-button c-button--full" id="moreBtn">더보기<span class="c-button__sub"><span id="reViewCurrent"></span>/<span id="reViewTotal"></span></span>
              <i class="c-icon c-icon--arrow-bottom" aria-hidden="true"></i>
            </button>
          </div>
        </div>
        <div class="c-tabs__panel" id="tabA2-panel"> </div>
      </div>
    </div>
    <input type="hidden" id="pageNo" name="pageNo" value="1"/>
  </div><!-- [START]-->
  <script src="../../resources/js/mobile/vendor/swiper.min.js"></script>

    </t:putAttribute>
</t:insertDefinition>