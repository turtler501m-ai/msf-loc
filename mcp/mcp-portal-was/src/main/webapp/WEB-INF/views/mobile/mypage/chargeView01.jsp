<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="jsver" value="${nmcp:getJsver()}" />
<t:insertDefinition name="mlayoutDefault">
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/mypage/chargeView01.js?version=${jsver}"></script>
</t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content chargeView" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    요금조회
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="c-tabs c-tabs--type2" data-tab-active="0">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button is-active" id="tab1" data-tab-header="#tabB1-panel" >월별금액조회</button>
                    <button class="c-tabs__button realSrch" id="tab2" data-tab-header="#tabB2-panel" >실시간 요금조회</button>
                </div>
                <hr class="c-hr c-hr--type3 c-expand">
                <c:if test="${empty maskingSession}">
				  <div class="masking-badge-wrap chargeView">
			          <div class="masking-badge">
					   	  <a class="masking-badge__button" href="/m/mypage/myinfoView.do" title="가려진(*) 정보보기 페이지 바로가기">
					    	<i class="masking-badge__icon" aria-hidden="true"></i>
						   	<p>가려진(*) 정보보기</p>
						  </a>
				      </div>
			      </div>
			    </c:if>
                <h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
                <div class="phone-line-box">
                    <%@ include file="/WEB-INF/views/mobile/mypage/myCommCtn.jsp" %>
                    <c:if test="${fn:length(detailInfo.itemList) ne 0}">
                        <select class="c-select c-select--type3" name="billMonth" id="billMonth">
                            <c:forEach items="${billInfo.monthList}" var="item">
                                <c:set value="${item.billMonth}" var="billMonth" />
                                <option value="${item.billMonth}" <c:if test="${searchVO.billMonth eq  item.billMonth }">selected</c:if>>${fn:substring(billMonth,0,4)}년${fn:substring(billMonth,4,6)}월</option>
                            </c:forEach>
                        </select>
                    </c:if>
                </div>
                <div class="c-tabs__panel border-none" id="tabB1-panel">
                    <c:if test="${fn:length(detailInfo.itemList) eq 0}">
                        <div class="c-nodata">
                            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                            <p class="c-nodata__text">조회된 청구(월별) 금액이 없습니다.</p>
                        </div>
                        <div class="c-button-wrap u-mt--48 chekPUllNn">
                            <a class="c-button c-button--full c-button--primary realSrch">실시간 요금조회</a>
                        </div>
                    </c:if>
                    <c:if test="${fn:length(detailInfo.itemList) ne 0}">
                        <div class="c-card c-card--type4">
                            <div class="c-card__box u-mt--32">
                                <div class="c-card__title">
                                    <b>${monthPay.month}월 청구요금</b>
                                </div>

                                <div class="c-card__price-box">
                                    <fmt:parseDate value="${monthPay.billStartDate}" pattern="yyyyMMdd" var="parseDate1" />
                                    <fmt:formatDate value="${parseDate1}" pattern="yyyy.MM.dd" var="resultDate" />
                                    <fmt:parseDate value="${monthPay.billEndDate}" pattern="yyyyMMdd" var="parseDate2" />
                                    <fmt:formatDate value="${parseDate2}" pattern="yyyy.MM.dd" var="resultDate2" />
                                    <span class="c-text c-text--type4 u-co-gray">${resultDate} ~ ${resultDate2}</span>
                                    <span class="c-text c-text--type3">
                                        <b><fmt:formatNumber value="${monthPay.totalDueAmt}" type="number" /></b><span class="u-ml--4">원</span>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <ul class="c-text-list c-bullet c-bullet--fyr u-mb--24">
                        <li class="c-text-list__item u-co-sub-4">요금조회는 최근 6개월 이내까지 가능합니다.</li>
                        <li class="c-text-list__item u-co-gray">최근 6개월 내 발행된 명세서는 요금명세서 메뉴에서 재발송이 가능합니다.
                            <a class="c-button c-button--underline c-button--sm fs-13 u-co-sub-4" href="/m/mypage/billWayReSend.do">요금명세서 바로가기</a>
                        </li>
                    </ul>
                    <div class="c-accordion c-accordion--type1 u-mb--40">
                        <ul class="c-accordion__box" id="accordionB">
                            <c:if test="${fn:length(detailInfo.itemList) ne 0}">
                                <c:forEach items="${ detailInfo.itemList }" var="item" varStatus="status">
                                    <c:choose>
                                        <c:when test="${item.splitDescription eq '당월 요금' or item.splitDescription eq '미납요금'}">
                                            <li class="c-accordion__item">
                                                <div class="c-flex c-flex--jfy-end c-flex--jfy-between">
                                                    <span>${item.splitDescription}</span>
                                                    <span class="fs-13 u-co-gray-7"><fmt:formatNumber value="${item.actvAmt}" type="number" />원</span>
                                                </div>
                                            </li>
                                        </c:when>
                                        <c:when test="${item.messageLine ne '' and 'DISCBYSVC' ne item.messageLine}">
                                            <li class="c-accordion__item">
                                                <div class="c-accordion__head" data-acc-header="#parent${status.count}">
                                                    <button class="c-accordion__button inner--charge" type="button" aria-expanded="false" onclick="detailView('parentView${status.count}','${item.messageLine}');">
                                                        <span>${item.splitDescription}
                                                            <input type="hidden" value="${item.messageLine}" id="mlId${status.count}" name="messageLineVal" />
                                                            <input type="hidden" value="${item.billSeqNo}" id="bs${status.count}" name="billSeqNoTmp" />
                                                        </span>
                                                        <span class="fs-13 u-co-gray-7"><fmt:formatNumber value="${item.actvAmt}" type="number" />원</span>
                                                    </button>
                                                </div>
                                                <div class="c-accordion__panel expand c-expand" id="parent${status.count}">
                                                    <div class="c-accordion__inside">
                                                        <div class="fee-list-wrap">
                                                            <dl class="fee-item--type1">
                                                                <dd class="fee-subs">
                                                                    <ul id="parentView${status.count}"></ul>
                                                                </dd>
                                                            </dl>
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${'할인요금' eq item.splitDescription}">
                                                <li class="c-accordion__item">
                                                    <div class="c-accordion__head" data-acc-header="#parent${status.count}">
                                                        <button class="c-accordion__button inner--charge" type="button" aria-expanded="false" onclick="detailView('parentView${status.count}','${item.messageLine}');">
                                                            <span>${item.splitDescription}
                                                                <input type="hidden" value="${item.messageLine}" id="mlId${status.count}" name="messageLineVal" />
                                                                <input type="hidden" value="${item.billSeqNo}" id="bs${status.count}" name="billSeqNoTmp" />
                                                            </span>
                                                            <span class="fs-13 u-co-gray-7"><fmt:formatNumber value="${item.actvAmt}" type="number" />원</span>
                                                        </button>
                                                    </div>
                                                    <div class="c-accordion__panel expand c-expand" id="parent${status.count}">
                                                        <div class="c-accordion__inside">
                                                            <div class="fee-list-wrap">
                                                                <dl class="fee-item--type1">
                                                                    <dd class="fee-subs">
                                                                        <ul id="parentView${status.count}"></ul>
                                                                    </dd>
                                                                </dl>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </li>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:forEach items="${ detailInfo.itemList }" var="item2" varStatus="status">
                                    <c:choose>
                                        <c:when test="${'당월요금계' eq item2.splitDescription}">
                                            <li class="c-accordion__item">
                                                <div class="c-flex c-flex--jfy-end c-flex--jfy-between">
                                                    <span>${item2.splitDescription}</span>
                                                    <span class="fs-13 u-co-gray-7"><fmt:formatNumber value="${item2.actvAmt}" type="number" />원</span>
                                                </div>
                                            </li>
                                        </c:when>
                                        <c:when test="${item2.messageLine eq '' and 'DISCBYSVC' ne item2.messageLine}">
                                            <li class="c-accordion__item">
                                                <div class="c-flex c-flex--jfy-end c-flex--jfy-between">
                                                    <span>${item2.splitDescription}</span>
                                                    <span class="fs-13 u-co-gray-7"><fmt:formatNumber value="${item2.actvAmt}" type="number" />원</span>
                                                </div>
                                            </li>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                                <li class="c-accordion__item">
                                    <div class="c-flex c-flex--jfy-end c-flex--jfy-between">
                                        <span>납부하실 총 요금</span>
                                        <span class="u-co-mint"><fmt:formatNumber value="${monthPay.totalDueAmt}" type="number" />원</span>
                                    </div>
                                </li>
                            </c:if>

                            <li class="c-accordion__item">
                                <div class="c-accordion__head" data-acc-header>
                                    <button class="c-accordion__button" type="button" aria-expanded="false">최근 납부내역</button>
                                </div>
                                <div class="c-accordion__panel expand c-expand">
                                    <div class="c-accordion__inside">
                                        <div class="c-table c-table--plr-8">
                                            <c:choose>
                                                <c:when test="${fn:length(vo.itemPay) == 0 }">
                                                    <div class="c-nodata">
                                                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                                                        <p class="c-noadat__text">납부내역이 없습니다.</p>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <table>
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
                                                        <tbody>
                                                            <c:forEach items="${vo.itemPay }" var="item">
                                                                <tr>
                                                                    <td class="u-ta-center">
                                                                        <c:set value="${item.payMentDate }" var="oDate" />
                                                                        <c:set value="${fn:substring(oDate,0,8)}" var="parseDate" />
                                                                        <fmt:parseDate value="${parseDate}" pattern="yyyyMMdd" var="parseDate1" />
                                                                        <fmt:formatDate value="${parseDate1}" pattern="yyyy.MM.dd" var="resultDate" /> ${resultDate}
                                                                    </td>
                                                                    <td class="u-ta-center">
                                                                        <p>${item.payMentMethod}</p>
                                                                    </td>
                                                                    <td class="u-ta-right"><fmt:formatNumber value="${item.payMentMoney}" type="number" />원</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                    <ul class="c-text-list c-bullet c-bullet--fyr u-mt--8">
                                                        <li class="c-text-list__item u-co-sub-4">PC에서는 올해 납부내역과 작년 1년동안의 납부내역 조회가 가능하며, 납부내역의 인쇄와 엑셀저장 기능을 지원합니다.</li>
                                                    </ul>
                                                </c:otherwise>
                                            </c:choose>
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
                                                        <tbody>
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
        <!-- [START]-->

    </t:putAttribute>
</t:insertDefinition>