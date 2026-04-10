<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutNoGnbDefault">

  <t:putAttribute name="titleAttr">이용량 조회</t:putAttribute>

  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/personal/portal/pCallView01.js?version=25.06.10"></script>
  </t:putAttribute>

  <t:putAttribute name="contentAttr">

    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">개인화 URL (이용량 조회)</h2>
      </div>

      <div class="c-row c-row--lg">
        <%@ include file="/WEB-INF/views/personal/portal/pCommCtn.jsp" %>
        <hr class="c-hr c-hr--title">
        <strong class="u-block c-heading c-heading--fs24 c-heading--regular u-mt--48 u-ta-center">이용중인 요금제는<br><b>${vo.strSvcNameSms}</b>입니다.</strong>
        <div class="total-charge u-mt--24 u-bd--0">
          <div class="total-charge__panel">
            <ul class="product-summary">
              <li class="product-summary__item">
                <i class="c-icon c-icon--payment-data" aria-hidden="true"></i>
                <span class="product-summary__text">${mcpFarPriceDto.rateAdsvcLteDesc}</span>
              </li>
              <li class="product-summary__item">
                <i class="c-icon c-icon--payment-call" aria-hidden="true"></i>
                <span class="product-summary__text">${mcpFarPriceDto.rateAdsvcCallDesc}</span>
              </li>
              <li class="product-summary__item">
                <i class="c-icon c-icon--payment-sms" aria-hidden="true"></i>
                <span class="product-summary__text">${mcpFarPriceDto.rateAdsvcSmsDesc}</span>
              </li>
            </ul>
          </div>
        </div>

        <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--64">실시간 이용량</h3>
        <div class="c-select-search">
          <div class="c-form--line">
            <label class="c-label c-hidden" for="datalist">이용량 월 선택</label>
            <input type="hidden" name="useMonth" value="${useMonth}" />
            <select class="c-select" id="datalist" name="datalist">
              <c:forEach items="${todayList}" var="datalist">
                <c:set var="dataMonth" value="${fn:replace(fn:substring(datalist,0,7), '.','')}" />
                <option value="${dataMonth}" <c:if test="${useMonth eq dataMonth}">selected</c:if>>${datalist}</option>
              </c:forEach>
            </select>
          </div>
          <button class="c-button c-button-round--xsm c-button--primary u-ml--10" type="button" onclick="btn_search();">조회</button>
        </div>

        <hr class="c-hr c-hr--title">
        <c:choose>
          <c:when test="${useTimeSvcLimit}">
            <div class="u-ta-center u-mt--48">
              <i class="c-icon c-icon--notice-sm" aria-hidden="true"></i>
              <p class="c-text c-text--fs16 u-mt--16">
                안정적인 서비스를 위해 이용량 조회는 <b>120분 내 20회</b>로 제한하고 있습니다.<br>잠시 후 이용해 주시기 바랍니다.
              </p>
            </div>
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${(data eq null or empty data) and (voice eq null or empty voice) and (sms eq null or empty sms)}">
                <div class="c-nodata">
                  <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                  <p class="c-nodata__text">이용량 조회 결과가 없습니다.</p>
                </div>
              </c:when>
              <c:otherwise>
                <c:set var="dataLen">0</c:set>
                <ul class="usage-chart u-mt--64">
                <c:if test="${data ne null and !empty data}">
                  <c:forEach items="${data}" var="dataList" varStatus="vs">
                    <c:set var="dataLen">${dataLen+1}</c:set>
                    <c:if test="${dataLen > 0 and dataLen mod 3 eq 1}"></ul><ul class="usage-chart u-mt--32"></c:if>
                    <li class="usage-chart__item">
                      <div class="c-indicator-arc">
                        <div class="arcOverflow">
                          <div class="arc arc-mint" data-percent="<fmt:formatNumber value="${dataList.percent}" />"></div>
                        </div>
                        <span class="indicator-state">
                          ${dataList.strSvcName}<br>
                          <i class="c-icon c-icon--data" aria-hidden="true"></i>
                        </span>
                        <div class="indicator-text">
                          <span>
                            <span class="c-hidden">${dataList.strFreeMinUse}</span>사용 ${dataList.strFreeMinUse}
                          </span>
                          <strong>
                            <span class="c-hidden">${dataList.strFreeMinRemain}</span> / 잔여 ${dataList.strFreeMinRemain}
                          </strong>
                        </div>
                      </div>
                    </li>
                  </c:forEach>
                </c:if>

                <c:if test="${voice ne null and !empty voice}">
                  <c:forEach items="${voice}" var="voiceList" varStatus="vs">
                    <c:set var="dataLen">${dataLen+1}</c:set>
                    <c:if test="${dataLen > 0 and dataLen mod 3 eq 1}"></ul><ul class="usage-chart u-mt--32"></c:if>
                    <li class="usage-chart__item">
                      <div class="c-indicator-arc">
                        <div class="arcOverflow">
                          <div class="arc arc-blue" data-percent="<fmt:formatNumber value="${voiceList.percent}" />"></div>
                        </div>
                        <span class="indicator-state">
                          ${voiceList.strSvcName}<br>
                          <i class="c-icon c-icon--call-gray" aria-hidden="true"></i>
                        </span>
                        <div class="indicator-text">
                          <span>
                            <span class="c-hidden">${voiceList.strFreeMinUse}${voiceList.bunGunNm}</span>사용 ${voiceList.strFreeMinUse}${voiceList.bunGunNm}
                          </span>
                          <strong>
                            <span class="c-hidden">${voiceList.strFreeMinRemain}${voiceList.bunGunNm}</span> / 잔여 ${voiceList.strFreeMinRemain}${voiceList.bunGunNm}
                          </strong>
                        </div>
                      </div>
                    </li>
                  </c:forEach>
                </c:if>

                <c:if test="${sms ne null and !empty sms}">
                  <c:forEach items="${sms}" var="smsList" varStatus="vs">
                    <c:set var="dataLen">${dataLen+1}</c:set>
                    <c:if test="${dataLen > 0 and dataLen mod 3 eq 1}"></ul><ul class="usage-chart u-mt--32"></c:if>
                    <li class="usage-chart__item">
                      <div class="c-indicator-arc">
                        <div class="arcOverflow">
                          <div class="arc arc-green" data-percent="<fmt:formatNumber value="${smsList.percent}" />"></div>
                        </div>
                        <span class="indicator-state">
                          ${smsList.strSvcName}<br>
                          <i class="c-icon c-icon--msg" aria-hidden="true"></i>
                        </span>
                        <div class="indicator-text">
                          <span>
                            <span class="c-hidden">${smsList.strFreeMinUse}${smsList.bunGunNm}</span>사용 ${smsList.strFreeMinUse}${smsList.bunGunNm}
                          </span>
                          <strong>
                            <span class="c-hidden">${smsList.strFreeMinRemain}${smsList.bunGunNm}</span> / 잔여 ${smsList.strFreeMinRemain}${smsList.bunGunNm}
                          </strong>
                        </div>
                      </div>
                    </li>
                  </c:forEach>
                </c:if>
                </ul>
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
        <c:if test="${roaming ne null and !empty roaming}">
          <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--96">로밍 이용량</h3>
          <hr class="c-hr c-hr--title">
          <h3 class="c-form__title">총 이용량</h3>
          <div class="c-table u-mt--16">
            <table>
              <caption>로밍 총 이용량</caption>
              <colgroup>
                <col>
                <col>
                <col>
              </colgroup>
              <thead>
              <tr>
                <th scope="col">구분</th>
                <th scope="col">전체 이용량</th>
                <th scope="col">요금부과 이용량</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach var="roamingList" items="${roaming}">
                <tr>
                  <td>${roamingList.strSvcName}</td>
                  <td>${roamingList.strCtnSecs}</td>
                  <td>${roamingList.strSecsToRate}</td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </div>
          <div>
            <p class="c-text-list__item u-co-red u-fs-14 u-mt--12">※ 위 사용량과 금액은 조회 시점에 따라 상이할 수 있습니다.</p>
          </div>
        </c:if>
        <b class="c-flex c-text c-text--fs15 u-mt--64">
          <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
          <span class="u-ml--4">알려드립니다</span>
        </b>
        <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
          <li class="c-text-list__item u-co-red">기본제공량 모두 소진 후 초과 사용량은 조회가 불가하며 초과사용 금액은 kt M모바일 홈페이지의 실시간 요금조회 메뉴에서 확인 부탁드립니다.</li>
          <li class="c-text-list__item u-co-gray">요금제/부가서비스/데이터상품에 대한 요금제 및 제공 혜택은 신청월의 사용기간에 따라 일할 적용됩니다.</li>
          <li class="c-text-list__item u-co-gray">
            용량집계에 소요되는 시간 때문에 실제 사용시점과 다소 차이가 있을 수 있으므로, 이용량은 참고용으로 이용해주시기 바랍니다. 기본 제공량 외 추가 사용요금,
            국제전화 요금, 소액결제 금액 등을 포함한 최종금액은 요금 청구작업 후 확정되오니, 다음달 사용요금을 함께 확인하시기 바랍니다.
          </li>
        </ul>
      </div>
    </div>
  </t:putAttribute>
</t:insertDefinition>