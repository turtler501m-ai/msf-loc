<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/jquery.number.min.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/mypage/callView01.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    이용량 조회
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
            <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>
            <div class="banner-balloon u-mt--40 u-mb--24">
                <p>
                    <span class="c-text c-text--type5">이용중인 요금제</span> <br>
                    <b class="c-text c-text--type7" id="strSvcNameSms">${vo.strSvcNameSms}</b>
                    <img class="deco-img" src="/resources/images/mobile/content/img_bolloon_banner_03.png" alt="">
                </p>
            </div>
            <ul class="payment-extra-info u-mt--40 u-mb--32 u-plr--20">
                <li>
                    <i class="c-icon c-icon--payment-data" aria-hidden="true"></i>
                    <span class="c-text c-text--type7" id="rateAdsvcLteDesc">${mcpFarPriceDto.rateAdsvcLteDesc}</span>
                </li>
                <li>
                    <i class="c-icon c-icon--payment-call" aria-hidden="true"></i>
                    <span class="c-text c-text--type7" id="rateAdsvcCallDesc">${mcpFarPriceDto.rateAdsvcCallDesc}</span>
                </li>
                <li>
                    <i class="c-icon c-icon--payment-sms" aria-hidden="true"></i>
                    <span class="c-text c-text--type7" id="rateAdsvcSmsDesc">${mcpFarPriceDto.rateAdsvcSmsDesc}</span>
                </li>
            </ul>
            <hr class="c-hr c-hr--type3 c-expand">
            <div class="c-box u-mt--40">
                <div class="c-box__title flex-type--between">실시간 이용량</div>
                <div class="c-form u-width--60p">
                    <fmt:formatDate value="${toMonth}" pattern="yyyyMM" var="startDate" />
                    <fmt:parseDate value="${startDate}" pattern="yyyyMMdd" var="parseDate1" />
                    <fmt:formatDate value="${parseDate1}" pattern="yyyy.MM.dd" var="resultDate" />
                    <fmt:formatDate value="${toMonth}" pattern="yyyy.MM.dd" var="resultDate2" />
                    <fmt:formatDate value="${toMonth}" pattern="dd" var="toDay" />
                    <input type="hidden" name="useMonth" value="${useMonth}" />
                    <select class="c-select c-select--type3" id="datalist">
                        <c:forEach items="${todayList}" var="datalist">
                            <c:set var="dataMonth" value="${fn:replace(fn:substring(datalist,0,7), '.','')}" />
                            <option value="${dataMonth}" <c:if test="${useMonth eq dataMonth}">selected</c:if>>${datalist}</option>
                        </c:forEach>
                    </select>
                </div>
                <c:choose>
                    <c:when test="${useTimeSvcLimit}">
                        <div class="u-ta-center u-mt--40">
                            <i class="c-icon c-icon--notice-sm" aria-hidden="true"></i>
                            <p class="c-text c-text--type3 u-mt--16">
                                안정적인 서비스를 위해 이용량 조회는 <br> <b>120분 내 20회</b>로 제한하고 있습니다. <br>잠시 후 이용해 주시기 바랍니다.
                            </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${(data eq null or empty data) and (voice eq null or empty voice) and (sms eq null or empty sms) and (roaming eq null or empty roaming)}">
                                <div class="c-nodata">
                                    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                                    <p class="c-noadat__text">이용량 조회 결과가 없습니다.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <!-- 사용량 -->
                                <c:if test="${data ne null and !empty data}">
                                    <c:forEach items="${data}" var="dataList" varStatus="vs">
                                        <div class="c-item c-item--type1 u-mt--24 u-mb--40">
                                            <div class="c-item__title flex-type--between">
                                                <span class="data-title--short">
                                                    <i class="c-icon c-icon--data" aria-hidden="true"></i>
                                                    <span class="u-ml--4">${dataList.strSvcName}</span>
                                                </span>
                                                <span class="u-ml--auto c-text c-text--type4">
                                                    <span class="u-co-gray-8 u-fw--regular">사용 ${dataList.strFreeMinUse}</span> / 잔여 ${dataList.strFreeMinRemain}
                                                </span>
                                            </div>
                                            <div class="c-indicator c-indicator--type1 u-mt--12">
                                                <c:set var="percent"><fmt:formatNumber value="${dataList.percent}" /></c:set>
                                                <span class="mark-mint" style="width:${percent}%"></span>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                                <!-- 사용량 -->

                                <!-- 음성/영상 -->
                                <c:if test="${voice ne null and !empty voice}">
                                    <c:forEach items="${voice}" var="voiceList" varStatus="vs">
                                        <div class="c-item c-item--type1 u-mb--40 u-mt--40">
                                            <div class="c-item__title flex-type--between">
                                                <span class="data-title--short">
                                                    <i class="c-icon c-icon--call-gray" aria-hidden="true"></i>
                                                    <sapn class="u-ml--4">${voiceList.strSvcName}</sapn>
                                                </span>
                                                <span class="right c-text c-text--type4">
                                                    <span class="u-co-gray-8 u-fw--regular">사용 ${voiceList.strFreeMinUse }${voiceList.bunGunNm}</span> / 잔여 ${voiceList.strFreeMinRemain}${voiceList.bunGunNm}
                                                </span>
                                            </div>
                                            <div class="c-indicator c-indicator--type1 u-mt--12">
                                                <c:set var="percent"><fmt:formatNumber value="${voiceList.percent}" /></c:set>
                                                <span class="mark-blue" style="width:${percent}%"></span>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                                <!-- 음성/영상 -->

                                <!-- 문자 -->
                                <c:if test="${sms ne null and !empty sms}">
                                    <c:forEach items="${sms}" var="smsList" varStatus="vs">
                                        <div class="c-item c-item--type1 u-mb--40 u-mt--40">
                                            <div class="c-item__title flex-type--between">
                                                <span class="data-title--short">
                                                    <i class="c-icon c-icon--msg" aria-hidden="true"></i>
                                                    <span class="u-ml--4">${smsList.strSvcName}</span>
                                                </span>
                                                <span class="right u-ml--auto c-text c-text--type4">
                                                    <span class="u-co-gray-8 u-fw--regular">사용 ${smsList.strFreeMinUse}${smsList.bunGunNm}</span> / 잔여 ${smsList.strFreeMinRemain}${smsList.bunGunNm}
                                                </span>
                                            </div>
                                            <div class="c-indicator c-indicator--type1 u-mt--12">
                                                <c:set var="percent"><fmt:formatNumber value="${smsList.percent}" /></c:set>
                                                <span class="mark-green" style="width:${percent}%"></span>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                                <!-- 문자 -->
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
                <button class="c-button c-button--full c-button--white u-mt--20 link" url="/m/mypage/chargeView03.do">실시간 요금조회</button>
                <c:if test="${!empty vo.itemTelList and  !useTimeSvcLimit}">
                    <button class="c-button c-button--full c-button--primary u-mt--12 link" url="/m/mypage/pullData01.do">데이터 당겨쓰기</button>
                </c:if>

                <c:if test="${roaming ne null and !empty roaming}">
                    <h3 class="c-heading c-heading--type7 u-mt--64">로밍 이용량</h3>
                    <hr class="c-hr c-hr--type2 u-mt--16">
                    <h3 class="c-form__title u-mt--0">총 이용량</h3>
                    <div class="c-table">
                        <table>
                            <caption>로밍 총 이용량</caption>
                            <colgroup>
                                <col>
                                <col>
                                <col style="width: 7.5rem">
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
                        <p class="c-text-list__item u-co-red u-fs-13 u-mt--10">※ 위 사용량과 금액은 조회 시점에 따라 상이할 수 있습니다.</p>
                    </div>
                </c:if>

                <hr class="c-hr c-hr--type3 u-mt--40">
                <h3 class="c-heading c-heading--type6">
                  <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                  <span class="u-ml--4">알려드립니다.</span>
                </h3>
                <ul class="c-text-list c-bullet c-bullet--dot u-mb--32 u-mt--16">
                    <li class="c-text-list__item u-co-red">기본제공량 모두 소진 후 초과 사용량은 조회가 불가하며 초과사용 금액은 실시간 요금조회에서 확인 부탁드립니다.</li>
                    <li class="c-text-list__item u-co-gray">요금제/부가서비스/데이터상품에 대한 요금제 및 제공 혜택은 신청월의 사용기간에 따라 일할 적용됩니다.</li>
                    <li class="c-text-list__item u-co-gray">용량집계에 소요되는 시간 때문에 실제
                        사용시점과 다소 차이가 있을 수 있으므로, 사용량은 참고용으로 이용해주시기 바랍니다. 기본 제공량 외 추가 사용요금,
                        국제전화 요금, 소액결제 금액 등을 포함한 최종금액은 요금 청구작업 후 확정되오니, 다음달 사용요금을 함께 확인하시기
                        바랍니다.
                    </li>
                </ul>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>