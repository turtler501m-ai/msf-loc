<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag"%>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/mypage/coupon/couponListOut.js"></script>
    </t:putAttribute>
    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">My 쿠폰</h2>
            </div>

            <div class="c-tabs__panel u-block">
                <div class="c-tabs c-tabs--type1">
                    <div class="c-tabs__list">
                        <a class="c-tabs__button" id="tabM" href="/mypage/couponList.do" aria-current="page">M쿠폰</a>
                        <a class="c-tabs__button is-active" id="tabE">데이터 쿠폰</a>
                    </div>
                </div>
                </br></br></br>

                <div class="c-row c-row--lg">
                    <h3 class="c-heading c-heading--fs20 c-heading--regular">조회할 회선을 선택해 주세요.</h3>
                    <div class="c-select-search">
                        <div class="phone-line">
                            <span class="phone-line__cnt">${searchVO.moCtn}</span>
                        </div>
                        <div class="c-form--line">
                            <label class="c-label c-hidden" for="ctn">회선 선택</label>
                            <select class="c-select" name="ctn" id="ctn">
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
                        <button class="c-button c-button-round--xsm c-button--primary u-ml--10" onclick="select();">조회</button>
                    </div>
                    <hr class="c-hr c-hr--title">

                    <div class="c-tabs c-tabs--type2">
                        <div class="c-tabs__list" role="tablist" style="padding-top:0;">
                            <button class="c-tabs__button is-active" id="tab1">사용 가능</button>
                            <button class="c-tabs__button" id="tab2">사용 완료</button>
                          </div>
                    </div>
                </div>
            </div>

            <div class="c-tabs__panel u-block">
                <div class="c-row c-row--lg"></br>
                    <div class="c-button-wrap c-button-wrap--right">
                        <button class="c-button c-button-round--sm c-button--white" type="button" id="ctnRegBtn">쿠폰등록</button>
                    </div>
                    <c:choose>
                        <c:when test="${isData eq true}">
                            <div class="c-card-col c-card-col--3 u-mt--4">
                                <c:if test="${coupInfoList ne null and !empty coupInfoList}">
                                    <c:forEach var="coupInfo" items="${coupInfoList}">
                                        <div class="c-card c-card--coupon">
                                            <span class="c-card__title">${coupInfo.coupNm}</span>
                                            <div class="c-form c-form--line">
                                                <label class="c-label c-hidden" for="coupSerialNo">쿠폰 종류 선택</label>
                                                <select class="c-select u-fs-13 coupSerialNo" name="coupSerialNo">
                                                    <option label="${coupInfo.coupNm}" value="${coupInfo.coupSerialNo}">${coupInfo.coupNm}</option>
                                                </select>
                                            </div>
                                            <div class="c-card__sub">
                                                <p class="c-text">
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
                                            </div>
                                            <div class="c-card__bottom">
                                                <div class="c-card__date">
                                                    <span class="c-hidden">사용기간</span>
                                                        <fmt:parseDate var="useStrtDt" value="${coupInfo.useStrtDt}" pattern="yyyyMMddHHmmss" />
                                                        <fmt:parseDate var="useEndDt" value="${coupInfo.useEndDt}" pattern="yyyyMMddHHmmss" />
                                                    <span class="sub-code"><fmt:formatDate value="${useStrtDt}" pattern="yyyy.MM.dd" />~<fmt:formatDate value="${useEndDt}" pattern="yyyy.MM.dd" /></span>
                                                </div>
                                                <button class="c-button c-button-round--xxsm c-button--primary regPop">사용</button>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${useCoupInfoList ne null and !empty useCoupInfoList}">
                                    <c:forEach var="useCoupInfo" items="${useCoupInfoList}">
                                        <div class="c-card c-card--coupon">
                                            <span class="c-card__title">${useCoupInfo.coupnCtgNm}</span>
                                            <div class="c-card__sub">
                                                <p class="c-text">
                                                    <span class="sub-title">쿠폰번호</span>
                                                    <span class="sub-code">${useCoupInfo.coupnNo}</span>
                                                </p>
                                            </div>
                                            <div class="c-card__bottom">
                                                <div class="c-card__date">
                                                    <span class="c-hidden">사용기간</span>
                                                    <span class="sub-code"><c:out value="${useCoupInfo.pstngStartDate}" />~<fmt:parseDate var="useEndDt2" value="${useCoupInfo.pstngEndDate}" pattern="yyyy.MM.dd" /><fmt:formatDate value="${useEndDt2}" pattern="yyyy.MM.dd"  /></span>
                                                </div>
                                                <button class="c-button c-button-round--xxsm c-button--primary regPop c-hidden">사용</button>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="c-nodata">
                                <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                                <p class="c-nodata__text">등록 가능한 쿠폰이 없습니다.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </t:putAttribute>
</t:insertDefinition>