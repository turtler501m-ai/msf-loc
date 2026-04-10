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
        <script type="text/javascript" src="/resources/js/mobile/mypage/coupon/couponListOut.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">My 쿠폰<button class="header-clone__gnb"></button></h2>
            </div>

            <div class="c-tabs c-tabs--type2" data-tab-active="0">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button" id="tabM">M쿠폰</button>
                    <button class="c-tabs__button is-active" id="tabE">데이터 쿠폰</button>
                </div>
                <hr class="c-hr c-hr--type3 c-expand">

                <h3 class="c-heading c-heading--type7 u-mt--40">조회회선</h3>
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

                <div class="c-tabs__panel" id="tabB1-panel">
                    <div class="c-tabs c-tabs--type3" data-tab-active="0">
                        <div class="c-tabs__list c-expand" data-tab-list="">
                              <button class="c-tabs__button is-active" id="tab1" data-tab-header="#tabB1-panel">사용 가능</button>
                              <button class="c-tabs__button" id="tab2" data-tab-header="#tabB2-panel">사용 완료</button>
                        </div>
                       </div>
                    <div class="c-item u-mt--16 u-mb--m20">
                        <div class="c-item__title">
                            <span class="c-text c-text--type4 u-co-gray-7 u-ml--auto">${toDay} 기준</span>
                        </div>
                    </div>

                    <div class="c-button-wrap c-button-wrap--column">
                        <button class="c-button c-button--md c-button--white" id="ctnRegBtn">
                            <i class="c-icon c-icon--coupon-ticket" aria-hidden="true"></i>
                            <span>쿠폰등록</span>
                            <span class="c-button__sub">
                                <i class="c-icon c-icon--arrow-right" aria-hidden="true"></i>
                            </span>
                        </button>
                    </div>
                    <c:choose>
                        <c:when test="${isData eq true}">
                            <ul class="c-list">
                                <c:if test="${coupInfoList ne null and !empty coupInfoList}">
                                    <c:forEach var="coupInfo" items="${coupInfoList}" varStatus="status">
                                        <li class="c-list__item">
                                            <div class="c-card c-card--type2 c-card--coupon">
                                                <div class="c-card__box">
                                                    <div class="c-card__title">
                                                        <b>${coupInfo.coupNm}</b>
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
                                                                <span class="sub-title">사용기간</span>
                                                                <fmt:parseDate var="useStrtDt" value="${coupInfo.useStrtDt}" pattern="yyyyMMddHHmmss" />
                                                                <fmt:parseDate var="useEndDt" value="${coupInfo.useEndDt}" pattern="yyyyMMddHHmmss" />
                                                                <span class="sub-code"><fmt:formatDate value="${useStrtDt}" pattern="yyyy.MM.dd" />~<fmt:formatDate value="${useEndDt}" pattern="yyyy.MM.dd" /></span>
                                                            </p>
                                                        </div>
                                                    </div>
                                                    <div class="c-accordion c-accordion--type4">
                                                        <ul class="c-accordion__box" id="accordion1">
                                                            <li class="c-accordion__item">
                                                                <div class="c-accordion__panel expand" id="chkCSP${status.count}">
                                                                    <div class="c-accordion__inside u-pt--24 u-pb--30">
                                                                        <div class="c-form">
                                                                            <input class="c-radio" id="coupSerialNo${status.count}" type="radio" name="coupSerialNo" value="${coupInfo.coupSerialNo}">
                                                                            <label class="c-label u-co-sub-1" for="coupSerialNo${status.count}">${coupInfo.coupNm}</label>
                                                                        </div>
                                                                        <!-- <div class="c-form u-mt--16">
                                                                            <input class="c-radio" id="radA2" type="radio" name="radA">
                                                                            <label class="c-label u-co-sub-1" for="radA2">무료통화 100분</label>
                                                                        </div> -->
                                                                    </div>
                                                                </div>
                                                                <div class="c-accordion__head" data-acc-header="#chkCSP${status.count}">
                                                                    <button class="c-accordion__button c-accordion__button_type2" type="button" aria-expanded="false">
                                                                        <i class="c-icon c-icon--arrow-down-bold" aria-hidden="true"></i>
                                                                    </button>
                                                                </div>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </c:if>

                                <c:if test="${useCoupInfoList ne null and !empty useCoupInfoList}">
                                    <c:forEach var="useCoupInfo" items="${useCoupInfoList}">
                                        <li class="c-list__item">
                                            <div class="c-card c-card--type2 c-card--coupon">
                                                <div class="c-card__box">
                                                    <div class="c-card__title">
                                                        <b>${useCoupInfo.coupnCtgNm}</b>
                                                        <div class="c-card__sub">
                                                            <p class="c-text c-text--type4 u-co-gray-7">
                                                                <span class="sub-title">쿠폰번호</span>
                                                                <span class="sub-code">${useCoupInfo.coupnNo}</span>
                                                            </p>
                                                            <p class="c-text c-text--type4 u-co-gray-7">
                                                                <span class="sub-title">사용기간</span>
                                                                <span class="sub-code">${useCoupInfo.pstngStartDate} ~ ${useCoupInfo.pstngEndDate}</span>
                                                            </p>
                                                        </div>
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
                    </ul>
                    <c:if test="${coupInfoList ne null and !empty coupInfoList}">
                        <div class="c-button-wrap">
                            <button class="c-button c-button--full c-button--primary" id="regPop">사용</button>
                        </div>
                    </c:if>

                </div>
                <div class="c-tabs__panel" id="tabB2-panel"></div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>