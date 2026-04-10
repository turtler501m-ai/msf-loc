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
        <script type="text/javascript" src="/resources/js/mobile/mypage/coupon/couponList.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">My 쿠폰<button class="header-clone__gnb"></button></h2>
            </div>

            <div class="c-tabs c-tabs--type2" data-tab-active="0">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="">
                    <button class="c-tabs__button is-active" id="tabM" aria-current="page">M쿠폰</button>
                    <button class="c-tabs__button" id="tabE">데이터 쿠폰</button>
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

                <div class="c-tabs__panel border-none">
                    <div>
                        <span class="c-text c-text--type4 u-co-gray-7 u-ml--auto">※ 사용 기간이 만료된 쿠폰은 쿠폰 목록에서 미노출됩니다.</span>
                    </div>
                    <c:choose>
                        <c:when test="${isData eq true}">
                            <ul class="c-list">
                                <c:if test="${useCoupInfoList ne null and !empty useCoupInfoList}">
                                    <c:forEach var="useCoupInfo" items="${useCoupInfoList}">
                                        <li class="c-list__item">
                                            <div class="c-card c-card--type2 c-card--coupon mcoupon">
                                                <div class="c-card__box">
                                                    <div class="c-card__title" style="word-wrap:break-word;">
                                                        <b>${useCoupInfo.coupnCtgNm}</b>
                                                    </div>
                                                    <div class="c-card__sub">
                                                        <p class="c-text c-text--type4 u-co-gray-7">
                                                            <c:choose>
                                                                <c:when test="${useCoupInfo.smsSndPosblYn eq 'N'}">
                                                                    <span class="sub-title">쿠폰번호</span>
                                                                    <span class="sub-code">${useCoupInfo.coupnNo}</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="sub-title">&nbsp;</span>
                                                                    <span class="sub-code">&nbsp;</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </p>
                                                        <p class="c-text c-text--type4 u-co-gray-7">
                                                            <span class="sub-title">사용기간</span>
                                                            <span class="sub-code">${useCoupInfo.pstngStartDate} ~ ${useCoupInfo.pstngEndDate}</span>
                                                        </p>
                                                    </div>
                                                    <div class="c-card__bottom">
                                                          <div class="c-button-wrap u-mt--0">
                                                            <button class="c-button c-button--full c-button--white c-button-coupon--copy" onclick="copyCouponNum('${useCoupInfo.coupnNo}')">번호복사</button>
                                                            <button class="c-button c-button--full c-button--primary c-button-coupon--detail" onclick="showDtl('${useCoupInfo.coupnNo}', '${useCoupInfo.coupnCtgCd}');">상세보기</button>
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
                                <p class="c-noadat__text">조회된 쿠폰이 없습니다.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>