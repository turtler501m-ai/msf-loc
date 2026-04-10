<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="/resources/js/mobile/mypage/childinfo/childChargeView.js"></script>

<div class="c-modal__content">
  <div class="c-modal__header">
    <h2 class="c-modal__title" id="ChangeProductTitle">요금조회</h2>
    <button class="c-button" data-dialog-close>
      <i class="c-icon c-icon--close" aria-hidden="true"></i>
      <span class="c-hidden">팝업닫기</span>
    </button>
  </div>
  <div class="c-modal__body chargeView">
    <div class="c-tabs c-tabs--type2" data-tab-active="0">
      <div class="c-tabs__list c-expand" data-tab-list="">
        <button class="c-tabs__button is-active" id="tab1" data-tab-header="#tabB1-panel" >월별금액조회</button>
        <button class="c-tabs__button realSrch" id="tab2" data-tab-header="#tabB2-panel" >실시간 요금조회</button>
        <input type="hidden" id="selectedMonth" value="${billMonth}">
      </div>
      <hr class="c-hr c-hr--type3 c-expand">
      <h3 class="c-heading c-heading--type7 u-mt--32">${childInfo.name}님</h3>
      <div class="phone-line-box">
        <%@ include file="/WEB-INF/views/mobile/mypage/childinfo/childCommCtn.jsp" %>
          <select class="c-select c-select--type3 billHeader" name="billMonth" id="billMonth">
          </select>
      </div>
      <div class="c-tabs__panel border-none" id="tabB1-panel">
        <div id="billNoData" style="display: none">
          <div class="c-nodata">
            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
            <p class="c-nodata__text">조회된 청구(월별) 금액이 없습니다.</p>
          </div>
          <div class="c-button-wrap u-mt--48 chekPUllNn">
            <a class="c-button c-button--full c-button--primary realSrch">실시간 요금조회</a>
          </div>
        </div>
        <div class="c-card c-card--type4 billHeader">
          <div class="c-card__box u-mt--32">
            <div class="c-card__title">
              <b id="monthInfo"></b>
            </div>

            <div class="c-card__price-box">
              <span class="c-text c-text--type4 u-co-gray" id="billMonthPeriod"></span>
              <span class="c-text c-text--type3">
                <b class="totalDueAmt"></b><span class="u-ml--4">원</span>
              </span>
            </div>
          </div>
        </div>
        <ul class="c-text-list c-bullet c-bullet--fyr u-mb--24">
          <li class="c-text-list__item u-co-sub-4">요금조회는 최근 6개월 이내까지 가능합니다.</li>
        </ul>
        <div class="c-accordion c-accordion--type1 u-mb--40">
          <ul class="c-accordion__box" id="accordionB">
            <li class="c-accordion__item" id="billBody">
              <div class="c-flex c-flex--jfy-end c-flex--jfy-between">
                <span>납부하실 총 요금</span>
                <span class="u-co-mint totalDueAmt">원</span>
              </div>
            </li>

            <li class="c-accordion__item">
              <div class="c-accordion__head" data-acc-header>
                <button class="c-accordion__button" id="acc_header_a1" type="button" aria-expanded="false">최근 납부내역</button>
              </div>
              <div class="c-accordion__panel expand c-expand">
                <div class="c-accordion__inside">
                  <div class="c-table c-table--plr-8">
                    <div class="c-nodata" id="nodata">
                      <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                      <p class="c-nodata__text">납부내역이 없습니다.</p>
                    </div>
                    <table class="payMent">
                      <caption>최근 1년 납부내역</caption>
                      <colgroup>
                        <col style="width: 29%">
                        <col style="width: 38%">
                        <col style="width: 33%">
                      </colgroup>
                      <thead>
                      <tr>
                        <th scope="col">납부일자</th>
                        <th scope="col">납부방법</th>
                        <th scope="col">납부금액</th>
                      </tr>
                      </thead>
                      <tbody id="payMentDetail">

                      </tbody>
                    </table>
<%--                    <ul class="c-text-list c-bullet c-bullet--fyr u-mt--8 payMent">--%>
<%--                      <li class="c-text-list__item u-co-sub-4">PC에서는 올해 납부내역과 작년 1년동안의 납부내역 조회가 가능하며, 납부내역의 인쇄와 엑셀저장 기능을 지원합니다.</li>--%>
<%--                    </ul>--%>
                  </div>
                </div>
              </div>
            </li>
            <li class="c-accordion__item">
              <div class="c-accordion__head" data-acc-header>
                <button class="c-accordion__button" type="button" aria-expanded="false">휴대폰 단말 대금</button>
              </div>
              <div class="c-accordion__panel expand c-expand">
                <div class="c-accordion__inside">
                  <c:choose>
                    <c:when test="${detailInfo.installmentYN eq 'Y'}">
                      <div class="c-table c-table--plr-8">
                        <table>
                          <caption>휴대폰 단말 대금 정보</caption>
                          <colgroup>
                            <col style="width: 29%">
                            <col style="width: 38%">
                            <col style="width: 33%">
                          </colgroup>
                          <thead>
                          <tr>
                            <th scope="col">잔여할부금액</th>
                            <th scope="col">잔여개월</th>
                            <th scope="col">만료예정일</th>
                          </tr>
                          </thead>
                          <tbody id="installmentInfo">
                          <c:choose>
                            <c:when test="${detailInfo.installmentAmt eq '0'}">
                              <tr>
                                <td scope="col" colspan="3">휴대폰 할부 정보가 없습니다.</td>
                              </tr>
                            </c:when>
                            <c:otherwise>
                              <tr>
                                <td><fmt:formatNumber value="${detailInfo.installmentAmt}" type="number" />원</td>
                                <td>${detailInfo.totalNoOfInstall}</td>
                                <td>${detailInfo.lastInstallMonth}</td>
                              </tr>
                            </c:otherwise>
                          </c:choose>
                          </tbody>
                        </table>
                      </div>
                    </c:when>
                    <c:otherwise>
                      <div class="c-nodata">
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-noadat__text">휴대폰 할부 정보가 없습니다.</p>
                      </div>
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>
            </li>
          </ul>
        </div>
        <h3 class="c-text c-text--type3 u-mt--24">
          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>알려드립니다.
        </h3>
        <ul class="c-text-list c-bullet c-bullet--dot">
          <li class="c-text-list__item u-co-gray">해당 청구월의 상세보기 또는 항목별 상세보기를 클릭하시면 해당월의 상세요금을 보실 수 있습니다.</li>
          <li class="c-text-list__item u-co-gray">매월 초에는 모바일 청구작업 기간 차이로 인해, 최근 청구월의 합산금액이 일시적으로 차이가 있을 수 있습니다.</li>
          <li class="c-text-list__item u-co-gray">단말기 할부 구매 고객님은 할부원금의 연 5.9%의 할부 수수료가 할부기간동안 청구됩니다. (부가세 별도)</li>
          <li class="c-text-list__item u-co-gray">할부 수수료는 월 잔여할부금에 따라 차등 부과됩니다.</li>
          <li class="c-text-list__item u-co-gray">월 중 가입하신 경우, 조회기간은 1일부터 말일까지 단순 표기됨을 양해해 주시기 바랍니다.</li>
        </ul>
      </div>

      <div class="c-tabs__panel" id="tabB2-panel">
      </div>
    </div>
  </div>
</div>