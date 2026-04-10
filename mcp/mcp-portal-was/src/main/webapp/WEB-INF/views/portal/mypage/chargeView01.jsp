<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<c:set var="jsver" value="${nmcp:getJsver()}" />
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">
    <t:putAttribute name="titleAttr">요금조회</t:putAttribute>
    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/mypage/chargeView01.js?version=23-04-27"></script>

    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">월별금액 조회</h2>
            </div>
            <div class="c-tabs c-tabs--type1">
                <div class="c-tabs__list">
                    <a class="c-tabs__button is-active" id="tab1" href="javascript:;" aria-current="page">월별금액 조회</a>
                    <a class="c-tabs__button realSrch" id="tab2" href="javascript:;">실시간요금 조회</a>
                </div>
            </div>

            <div class="c-tabs__panel u-block">
                <div class="c-row c-row--lg">
                    <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--48">조회할 회선을 선택해 주세요.</h3>
                    <%@ include file="/WEB-INF/views/portal/mypage/myCommCtn.jsp" %>
                    <form id="frm" name="frm" method="post">
                        <input type="hidden" name="ncn" id="ncn" value="" />
                        <iFrame title="빈프레임" id="tmpFrm" name="tmpFrm" style="display: none"></iFrame>
                    </form>

                    <hr class="c-hr c-hr--title">
                    <div class="u-flex-between u-mt--64">
                        <c:if test="${fn:length(detailInfo.itemList) ne 0}">
                            <div class="c-select-search">
                                <div class="c-form--line sel-month">
                                    <label class="c-label c-hidden" for="billMonth">조회 월 선택</label>
                                    <select class="c-select" name="billMonth" id="billMonth">
                                        <c:forEach items="${billInfo.monthList}" var="item">
                                            <c:set value="${item.billMonth}" var="billMonth" />
                                            <option value="${item.billMonth}" <c:if test="${searchVO.billMonth eq  item.billMonth }">selected</c:if>>${fn:substring(billMonth,4,6)}월 청구요금</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <button class="c-button c-button--search2 c-button--white" type="button" onclick="btn_search('month')">조회</button>
                            </div>
                            <fmt:parseDate value="${monthPay.billStartDate}" pattern="yyyyMMdd" var="parseDate1" />
                            <fmt:formatDate value="${parseDate1}" pattern="yyyy.MM.dd" var="resultDate" />
                            <fmt:parseDate value="${monthPay.billEndDate}" pattern="yyyyMMdd" var="parseDate2" />
                            <fmt:formatDate value="${parseDate2}" pattern="yyyy.MM.dd" var="resultDate2" />
                            <span class="u-co-gray">${resultDate} ~ ${resultDate2}</span>
                        </c:if>
                    </div>
                    <!-- [2022-02-03] nodata 케이스 추가-->
                    <c:choose>
                        <c:when test="${fn:length(detailInfo.itemList) eq 0}">
                            <div class="c-nodata">
                                <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                                <p class="c-nodata__text">조회된 청구(월별) 금액이 없습니다.</p>
                            </div>
                            <div class="c-button-wrap u-mt--40">
                                <a class="c-button c-button--lg c-button--primary c-button--w460 realSrch" href="javascript:;" >실시간 요금조회</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="total-charge u-mt--16">
                                <dl class="total-charge__price">
                                    <dt class="total-charge__title">이번 달에 납부하실 총 요금</dt>
                                    <dd class="total-charge__text">
                                        <b><fmt:formatNumber value="${monthPay.totalDueAmt}" type="number" /></b> 원
                                    </dd>
                                </dl>
                            </div>
                            <ul class="c-text-list c-bullet c-bullet--fyr u-mt--16">
                                <li class="c-text-list__item u-co-sub-4">요금조회는 최근 6개월 이내까지 가능합니다.</li>
                                <li class="c-text-list__item u-co-gray">최근 6개월 내 발행된 명세서는 요금명세서 메뉴에서 재발송이 가능합니다.
                                    <a class="c-text c-text--underline u-ml--6 u-co-sub-4" href="/mypage/billWayReSend.do">요금명세서 바로가기</a>
                                </li>
                            </ul>
                            <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--48">청구요금 상세</h3>
                            <div class="c-button-wrap c-button-wrap--right">
                                <button class="c-button c-button--sm c-button--white c-button-round--sm" type="button" onclick="printPopup();" data-dialog-trigger="#before-agree-modal">
                                    <i class="c-icon c-icon--print" aria-hidden="true"></i> <span>인쇄</span>
                                </button>
                                <button class="c-button c-button--sm c-button--white c-button-round--sm u-ml--8" data-dialog-trigger="#before-agree-modal" type="button" onclick="excelDownload();">
                                    <i class="c-icon c-icon--excel" aria-hidden="true"></i> <span>엑셀저장</span>
                                </button>
                            </div>
                            <!-- [2022-02-15] 간격조정 유틸클래스 추가-->
                            <hr class="c-hr c-hr--title u-mb--8">
                            <!-- [2022-02-15] 청구요금 상세 영역 아코디언으로 변경(마크업 수정) 시작-->
                            <div class="c-accordion c-accordion--type4 charge-detail" data-ui-accordion>
                                <ul class="c-accordion__box">
                                    <c:forEach items="${ detailInfo.itemList }" var="item" varStatus="status">
                                        <c:if test="${item.splitDescription eq '당월 요금' or item.splitDescription eq '미납요금'}">
                                            <li class="c-accordion__item">
                                                <div class="c-accordion__head">
                                                    <strong class="c-accordion__title">${item.splitDescription}</strong>
                                                    <span class="c-accordion__amount"><fmt:formatNumber value="${item.actvAmt}" type="number" />원</span>
                                                </div>
                                            </li>
                                        </c:if>
                                        <c:choose>
                                            <c:when test="${item.messageLine ne '' and 'DISCBYSVC' ne item.messageLine}">
                                                <li class="c-accordion__item" id="parent${status.count}" adDetail="F">
                                                    <div class="c-accordion__head">
                                                        <strong class="c-accordion__title">${item.splitDescription}
                                                            <input type="hidden" value="${item.messageLine}" id="mlId${status.count}" name="messageLineVal" />
                                                            <input type="hidden" value="${item.billSeqNo}" id="bs${status.count}" name="billSeqNoTmp" />
                                                        </strong>
                                                        <button class="runtime-data-insert c-accordion__button"
                                                            id="acc_header_c${status.count}" aria-expanded="false" aria-controls="acc_content_c${status.count}"
                                                            type="button" onclick="detailView('parentView${status.count}','${item.messageLine}');">
                                                            <span class="c-hidden">${item.splitDescription}상세열기</span>
                                                        </button>
                                                        <span class="c-accordion__amount"><fmt:formatNumber value="${item.actvAmt}" type="number" />원</span>
                                                    </div>
                                                    <div class="c-accordion__panel expand" id="acc_content_c${status.count}" aria-labelledby="acc_header_c${status.count}">
                                                        <div class="c-accordion__inside">
                                                            <div class="pay-detail">
                                                                <dl class="pay-detail__item" id="parentView${status.count}"></dl>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${'할인요금' eq item.splitDescription}">
                                                    <li class="c-accordion__item" id="parent${status.count}" adDetail="F">
                                                        <div class="c-accordion__head">
                                                            <strong class="c-accordion__title">${item.splitDescription}
                                                                <input type="hidden" value="${item.messageLine}" id="mlId${status.count}" name="messageLineVal" />
                                                                <input type="hidden" value="${item.billSeqNo}" id="bs${status.count}" name="billSeqNoTmp" />
                                                            </strong>
                                                            <button class="runtime-data-insert c-accordion__button" id="acc_header_c${status.count}"
                                                                type="button" aria-expanded="false" aria-controls="acc_content_c${status.count}"
                                                                onclick="detailView('parentView${status.count}','${item.messageLine}');">
                                                                <span class="c-hidden">${item.splitDescription} 상세열기</span>
                                                            </button>
                                                            <span class="c-accordion__amount"><fmt:formatNumber value="${item.actvAmt}" type="number" />원</span>
                                                        </div>
                                                        <div class="c-accordion__panel expand" id="acc_content_c${status.count}" aria-labelledby="acc_header_c${status.count}">
                                                            <div class="c-accordion__inside">
                                                                <div class="pay-detail">
                                                                    <dl class="pay-detail__item" id="parentView${status.count}"></dl>
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
                                                    <div class="c-accordion__head">
                                                        <strong class="c-accordion__title">${item2.splitDescription}</strong>
                                                        <span class="c-accordion__amount"><fmt:formatNumber value="${item2.actvAmt}" type="number" />원</span>
                                                    </div>
                                                </li>
                                            </c:when>
                                            <c:when test="${item2.messageLine eq '' and 'DISCBYSVC' ne item2.messageLine}">
                                                <li class="c-accordion__item">
                                                    <div class="c-accordion__head">
                                                        <strong class="c-accordion__title">${item2.splitDescription}</strong>
                                                        <span class="c-accordion__amount"><fmt:formatNumber value="${item2.actvAmt}" type="number" />원</span>
                                                    </div>
                                                </li>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                                </ul>
                            </div>
                            <dl class="charge-amount">
                                <dt>납부하실 총 요금</dt>
                                <dd><fmt:formatNumber value="${monthPay.totalDueAmt}" type="number" />원	</dd>
                            </dl>
                        </c:otherwise>
                    </c:choose>

                    <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--48">납부내역</h3>
                    <hr class="c-hr c-hr--title u-mb--0">
                    <div class="c-accordion c-accordion--type3" data-ui-accordion>
                        <ul class="c-accordion__box">
                            <li class="c-accordion__item">
                                <div class="c-button-wrap--right__acco">
                                    <button class="c-button c-button--sm c-button--white c-button-round--sm btnPrint" type="button" listCnt="0" >
                                        <i class="c-icon c-icon--print" aria-hidden="true"></i> <span>인쇄</span>
                                    </button>
                                    <button class="c-button c-button--sm c-button--white c-button-round--sm u-ml--8 btnExcel" data-dialog-trigger="#before-agree-modal" type="button" listCnt="0" >
                                        <i class="c-icon c-icon--excel" aria-hidden="true"></i> <span>엑셀저장</span>
                                    </button>
                                </div>
                                <div class="c-accordion__head">
                                    <strong class="c-accordion__title">최근 납부내역</strong>
                                    <button class="runtime-data-insert c-accordion__button" id="acc_header_a1" type="button" aria-expanded="false" aria-controls="acc_content_a1"></button>
                                </div>
                                <div class="c-accordion__panel expand" id="acc_content_a1" aria-labelledby="acc_header_a1">
                                    <div class="c-accordion__inside">
                                        <p class="c-text c-text--fs15"></p>
                                        <div class="c-table u-mt--16">
                                            <table>
                                                <caption>납부일자, 납부방법, 납구금액으로 구성된 납부내역 정보</caption>
                                                <colgroup>
                                                    <col style="width: 33.33%">
                                                    <col style="width: 33.33%">
                                                    <col>
                                                </colgroup>
                                                <thead>
                                                    <tr>
                                                        <th scope="col">납부일자</th>
                                                        <th scope="col">납부방법</th>
                                                        <th scope="col">납부금액</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="recentArea">

                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li class="c-accordion__item">
                                <div class="c-button-wrap--right__acco">
                                    <button class="c-button c-button--sm c-button--white c-button-round--sm btnPrint" type="button" listCnt="1" >
                                        <i class="c-icon c-icon--print" aria-hidden="true"></i> <span>인쇄</span>
                                    </button>
                                    <button class="c-button c-button--sm c-button--white c-button-round--sm u-ml--8 btnExcel" data-dialog-trigger="#before-agree-modal" type="button" listCnt="1" >
                                        <i class="c-icon c-icon--excel" aria-hidden="true"></i> <span>엑셀저장</span>
                                    </button>
                                </div>
                                <div class="c-accordion__head">
                                    <strong class="c-accordion__title">작년 납부내역</strong>
                                    <button class="runtime-data-insert c-accordion__button" id="acc_header_a2" type="button" aria-expanded="false" aria-controls="acc_content_a2"></button>
                                </div>
                                <div class="c-accordion__panel expand" id="acc_content_a2" aria-labelledby="acc_header_a2">
                                    <div class="c-accordion__inside">
                                        <p class="c-text c-text--fs15"></p>
                                        <div class="c-table u-mt--16">
                                            <table>
                                                <caption>납부일자, 납부방법, 납구금액으로 구성된 납부내역 정보</caption>
                                                <colgroup>
                                                    <col style="width: 33.33%">
                                                    <col style="width: 33.33%">
                                                    <col>
                                                </colgroup>
                                                <thead>
                                                    <tr>
                                                        <th scope="col">납부일자</th>
                                                        <th scope="col">납부방법</th>
                                                        <th scope="col">납부금액</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="lastArea">

                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>

                    <h3 class="c-heading c-heading--fs20 c-heading--regular u-mt--48">휴대폰 단말 대금</h3>
                    <div class="c-table u-mt--24">
                        <table>
                            <caption>잔여할부금액, 잔여개월, 만료예정일로 구성된 휴대폰 단말 대금 정보</caption>
                            <colgroup>
                                <col style="width: 33.33%">
                                <col style="width: 33.33%">
                                <col>
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
                                    <c:when test="${detailInfo.installmentAmt eq '0' or detailInfo.installmentYN ne 'Y'}">
                                        <tr>
                                            <td colspan="3">휴대폰 할부 정보가 없습니다.</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td scope="col">
                                                <fmt:formatNumber value="${detailInfo.installmentAmt}" type="number" />원
                                            </td>
                                            <td>${detailInfo.totalNoOfInstall}</td>
                                            <td>${detailInfo.lastInstallMonth}</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>

                    <b class="c-flex c-text c-text--fs15 u-mt--48">
                        <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                        <span class="u-ml--4">알려드립니다</span>
                    </b>
                    <ul class="c-text-list c-bullet c-bullet--dot u-mt--16">
                        <li class="c-text-list__item u-co-gray">해당 청구월의 상세보기 또는 항목별 상세보기를 클릭하시면 해당월의 상세요금을 보실 수 있습니다.</li>
                        <li class="c-text-list__item u-co-gray">매월 초에는 모바일 청구작업 기간 차이로 인해, 최근 청구월의 합산금액이 일시적으로 차이가 있을 수 있습니다.</li>
                        <li class="c-text-list__item u-co-gray">단말기 할부 구매 고객님은 할부원금의 연 5.9%의 할부수수료가 할부기간동안 청구됩니다. (부가세 별도) <br>할부수수료는 월 잔여할부금에 따라 차등 부과됩니다.</li>
                        <li class="c-text-list__item u-co-gray">월 중 가입하신 경우, 조회기간은 1일부터 말일까지 단순 표시됨을 양해해 주시기 바립니다.</li>
                    </ul>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>
