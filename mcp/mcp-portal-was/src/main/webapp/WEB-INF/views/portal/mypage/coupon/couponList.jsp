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
        <script type="text/javascript" src="/resources/js/portal/mypage/coupon/couponList.js"></script>
    </t:putAttribute>
<t:putAttribute name="contentAttr">
    <div class="ly-content" id="main-content">
        <div class="ly-page--title">
            <h2 class="c-page--title">My 쿠폰</h2>
        </div>

        <div class="c-tabs__panel u-block">
            <div class="c-tabs c-tabs--type1">
                <div class="c-tabs__list">
                    <a class="c-tabs__button is-active" id="tabM" aria-current="page">M쿠폰</a>
                    <a class="c-tabs__button" id="tabE" href="/mypage/couponListOut.do">데이터 쿠폰</a>
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
            </div>
        </div>

        <div class="c-tabs__panel u-block">
            <div class="c-row c-row--lg">
                <div style="text-align:right;">
                    <span class="c-text c-text--type4 u-co-gray-7 u-ml--auto">※ 사용 기간이 만료된 쿠폰은 쿠폰 목록에서 미노출됩니다.</span>
                </div>
                <c:choose>
                    <c:when test="${isData eq true}">
                        <div class="c-card-col c-card-col--3 u-mt--4">
                            <c:if test="${useCoupInfoList ne null and !empty useCoupInfoList}">
                                <c:forEach var="useCoupInfo" items="${useCoupInfoList}">
                                    <div class="c-card c-card--coupon mcoupon">
                                        <span class="c-card__title">${useCoupInfo.coupnCtgNm}</span>
                                        <div class="c-card__sub">
                                            <p class="c-text">
                                                <c:choose>
                                                    <c:when test="${useCoupInfo.smsSndPosblYn eq 'N'}">
                                                        <span class="sub-title">쿠폰번호</span>
                                                        <span class="sub-code">${useCoupInfo.coupnNo}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="sub-title"> </span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </p>
                                            <button class="c-button c-button--white c-button-coupon--copy" onclick="copyCouponNum('${useCoupInfo.coupnNo}')">번호복사</button>
                                        </div>
                                        <div class="c-card__bottom">
                                            <div class="c-card__date">
                                                <span class="sub-code">${useCoupInfo.pstngStartDate} ~ ${useCoupInfo.pstngEndDate}</span>
                                            </div>
                                            <button class="c-button  c-button--primary c-button-coupon--detail" onclick="showDtl('${useCoupInfo.coupnNo}', '${useCoupInfo.coupnCtgCd}');">상세보기</button>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="c-nodata">
                            <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                            <p class="c-nodata__text">조회된 쿠폰이 없습니다.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</t:putAttribute>
</t:insertDefinition>