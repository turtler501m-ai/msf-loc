<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/mobile/mypage/coupon/couponUsedDataList.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    My 쿠폰
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>
            <div class="c-tabs c-tabs--type2" data-tab-active="1">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button" id="tabM">M쿠폰</button>
                    <button class="c-tabs__button is-active" id="tabE">데이터 쿠폰</button>
                </div>

                <h3 class="c-heading c-heading--type7 u-mt--32">조회회선</h3>
                <div class="phone-line-wrap">
                    <div class="phone-line">
                          <span class="phone-line__cnt">${searchVO.moCtn}</span>
                      </div>
                    <select name="ctn" id="ctn" onchange="select();">
                        <c:forEach items="${cntrList}" var="item">
                            <c:choose>
                                <c:when test="${maskingSession eq 'Y'}">
                                    <option value="${item.svcCntrNo}" ${item.svcCntrNo eq searchVO.ncn?'selected':''} >${item.formatUnSvcNo}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${item.svcCntrNo}" ${item.svcCntrNo eq searchVO.ncn?'selected':''} >${item.cntrMobileNo}</option>
                                </c:otherwise>
                           </c:choose>
                        </c:forEach>
                    </select>
                </div>
                <hr class="c-hr c-hr--type3">

                <div class="c-tabs__panel" id="tabB2-panel">
                    <div class="c-tabs c-tabs--type3" data-tab-active="0">
                        <div class="c-tabs__list c-expand" data-tab-list="">
                              <button class="c-tabs__button" id="tab1">사용 가능</button>
                              <button class="c-tabs__button is-active" id="tab2">사용 완료</button>
                        </div>
                       </div>
                    <div class="c-item u-mt--16 u-mb--12">
                        <div class="c-item__title">
                            <span class="c-text c-text--type4 u-co-gray-7 u-ml--auto">${toDay} 기준</span>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${isData eq true}">
                            <ul class="c-list c-coupon-list">
                                <c:if test="${coupInfoList ne null and !empty coupInfoList}">
                                    <c:forEach var="coupInfo" items="${coupInfoList}">
                                        <li class="c-list__item">
                                            <div class="c-card c-coupon">
                                                <div class="c-card__box">
                                                    <div class="c-pad__box u-flex-between">
                                                        <div class="c-card__title">
                                                            <b class="fs-14 u-fw-normal">${coupInfo.coupNm}</b>
                                                            <div class="c-card__sub">
                                                                <p class="c-text c-text--type4 u-co-gray-7">
                                                                    <span class="sub-title">쿠폰번호</span>
                                                                    <span class="sub-code">
                                                                        <c:choose>
                                                                            <c:when test="${fn:length(coupInfo.coupSerialNo) eq 15}">
                                                                                ${fn:substring(coupInfo.coupSerialNo,0,4)}-${fn:substring(coupInfo.coupSerialNo,4,8)}-${fn:substring(coupInfo.coupSerialNo,8,12)}-${fn:substring(coupInfo.coupSerialNo,12,15)}
                                                                            </c:when>
                                                                            <c:otherwise>${coupInfo.coupSerialNo}</c:otherwise>
                                                                        </c:choose>
                                                                    </span>
                                                                </p>
                                                                <p class="c-text c-text--type4 u-co-gray-7">
                                                                    <span class="sub-title">등록일</span>
                                                                    <fmt:parseDate var="rgstStrtDt" value="${coupInfo.rgstStrtDt}" pattern="yyyyMMddHHmmss" />
                                                                    <fmt:parseDate var="useReqDt" value="${coupInfo.useReqDt}" pattern="yyyyMMddHHmmss" />
                                                                    <span><fmt:formatDate value="${rgstStrtDt}" pattern="yyyy.MM.dd" /></span>
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <span class="c-text-label c-text-label--gray-type1 u-mr--4 u-ml--4">${coupInfo.coupStatCdNm}</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </c:if>

                                <c:if test="${usedCoupInfoList ne null and !empty usedCoupInfoList}">
                                    <c:forEach var="usedCoupInfo" items="${usedCoupInfoList}">
                                        <li class="c-list__item">
                                            <div class="c-card c-coupon">
                                                <div class="c-card__box">
                                                    <div class="c-pad__box u-flex-between">
                                                        <div class="c-card__title">
                                                            <b class="fs-14 u-fw-normal">${usedCoupInfo.coupnCtgNm}</b>
                                                            <div class="c-card__sub">
                                                                <p class="c-text c-text--type4 u-co-gray-7">
                                                                    <span class="sub-title">쿠폰번호</span>
                                                                    <span class="sub-code">${usedCoupInfo.coupnNo}</span>
                                                                </p>
                                                                <p class="c-text c-text--type4 u-co-gray-7">
                                                                    <span class="sub-title">사용기간</span>
                                                                    <span>${usedCoupInfo.pstngStartDate} ~ ${usedCoupInfo.pstngEndDate}</span>
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <span class="c-text-label c-text-label--gray-type1 u-mr--4 u-ml--4">사용완료</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </c:if>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <div class="c-nodata">
                                <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                                <p class="c-noadat__text">조회 결과가 없습니다.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>