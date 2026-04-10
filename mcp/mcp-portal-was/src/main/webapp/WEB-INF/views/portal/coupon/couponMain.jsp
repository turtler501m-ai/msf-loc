<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">M쿠폰</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/coupon/couponMain.js"></script>
        <script type="text/javascript" src="/resources/js/portal/vendor/swiper.min.js"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">

            <form name="frm" id="frm" method="post" action="/loginForm.do">
                <input type="hidden" name="uri" id="uri" value="/coupon/couponMain.do"/>
                <input type="hidden" name="userDivisionYn" id="userDivisionYn" value="02"/>
            </form>

            <div class="ly-page--title">
                <h2 class="c-page--title">M쿠폰</h2>
            </div>

            <!--  배너시작 -->
            <div class="mbership-banner">
                <c:if test="${!empty bannerInfo}">
                    <div class="swiper-container" id="swiperOpenMarket">
                        <ul class="swiper-wrapper">
                            <c:forEach var="bannerInfo" items="${bannerInfo}">
                                <c:set var="target" value="_self " />
                                <c:if test="${bannerInfo.linkTarget eq 'Y' }">
                                    <c:set var="target" value="_blank" />
                                    <c:set var="title" value="새창열림" />
                                </c:if>
                                <c:choose>
                                    <c:when test="${bannerInfo.linkTarget eq null || bannerInfo.linkTarget eq ''}">
                                        <li class="swiper-slide" tabindex="0" aria-label="" <c:if test="${!empty bannerInfo.bgColor}">style="background:<c:out value="${bannerInfo.bgColor}"/>"</c:if>>
                                            <div class="u-box--w1100 u-margin-auto">
                                                <img src="<c:out value="${bannerInfo.bannImg}"/>" alt="<c:out value="${bannerInfo.imgDesc}"/>">
                                            </div>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="swiper-slide" tabindex="0" aria-label="" <c:if test="${!empty bannerInfo.bgColor}">style="background:<c:out value="${bannerInfo.bgColor}"/>"</c:if>>
                                            <div class="u-box--w1100 u-margin-auto">
                                                <a <c:if test="${bannerInfo.linkTarget eq 'Y' }"> title ='새창열림' </c:if> href="javascript:void(0);"
                                                                                                                        onclick="fn_go_banner('<c:out value="${bannerInfo.linkUrlAdr}"/>','<c:out value="${bannerInfo.bannSeq}"/>','<c:out value="${bannerInfo.bannCtg}"/>','<c:out value="${target}"/>')">
                                                    <img src="${bannerInfo.bannImg}" alt="<c:out value="${bannerInfo.imgDesc}"/>">
                                                </a>
                                            </div>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>
                        <div class="swiper-controls-wrap u-box--w1100">
                            <c:if test="${!empty bannerInfo && fn:length(bannerInfo) > 1}">
                                <div class="swiper-controls">
                                    <button class="coupon-pager swiper-button-prev" type="button" role="button" aria-label="이전 슬라이드"></button>
                                    <button class="coupon-pager swiper-button-next" type="button" role="button" aria-label="다음 슬라이드"></button>
                                    <button class="swiper-button-pause" type="button">
                                        <span class="c-hidden">자동재생 정지</span>
                                    </button>
                                    <div class="swiper-pagination" aria-hidden="true"></div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:if>
            </div>
            <!--  배너끝 -->

            <div class="mbership-head">
                <p class="mbership-head__title">
                    <b>M쿠폰</b>은 kt M모바일을 이용하시는 고객님께 제공되는 무료 쿠폰 서비스입니다.
                </p>
                <c:if test="${isLogin eq false}">
                    <p class="mbership-head__text">
                             쿠폰을 받기 위해서는 로그인이 필요합니다.
                        <a class="c-text-link--bluegreen" href="javascript:$('#frm').submit();" title="로그인 페이지 바로가기">로그인하기</a>
                    </p>
                </c:if>
            </div>

            <div class="c-tabs c-tabs--type1" style="display:none;">
                <div class="c-tabs__list">
                    <a class="c-tabs__button is-active" id="tabF" aria-current="page">무료 쿠폰</a>
                    <a class="c-tabs__button" id="tabC">유료 쿠폰</a>
                </div>
            </div>

            <div class="c-tabs__panel u-block" style="margin-top:2.5rem;">
                <!-- M쿠폰 쿠폰 영역 시작 -->
                <div class="mbership-wrap">
                    <ul class="mbership-category">
                        <li class="mbership-category__item">
                            <button class="mbership-category__button is-active" id="categoryAll" onclick="categoryAll();">
                                <i class="c-icon--mbership-all" aria-hidden="true"></i>
                                <span>ALL</span>
                            </button>
                        </li>
                        <c:forEach items="${couponCategoryList}" var="item">
                            <li class="mbership-category__item">
                                <button class="mbership-category__button" onclick="categoryOther('${item.dtlCd}');">
                                    <i class="${item.expnsnStrVal1}" aria-hidden="true"></i>
                                    <span>${item.dtlCdNm}</span>
                                </button>
                            </li>
                        </c:forEach>
                    </ul>

                    <div class="mbership-coupon-button-wrap">
                                        <span id=categoryCnt></span>
                        <div class="mbership-coupon-button">
                            <button onclick="allCheck();" id="allCheck">
                                <span>전체선택</span>
                            </button>
                            <button onclick="checkDown();" id="checkDownAll">
                                <span>쿠폰 일괄 다운받기</span>
                                <i class="c-icon c-icon--download-ty2-red" aria-hidden="true"></i>
                            </button>
                        </div>
                    </div>

                    <ul class="mbership-list">
                        <c:if test="${mbList ne null and !empty mbList}">
                            <c:forEach var="mbList" items="${mbList}">
                                <li class="mbership-item" couponCategory="${mbList.couponCategory}">
                                    <div class="mbership-info">
                                      <c:if test="${mbList.leftQnty ne '0' and mbList.downPossibleYn ne 'N'}">
                                          <input class="c-checkbox c-checkbox--type3" id="${mbList.coupnCtgCd}" type="checkbox" name="couponCheckbox">
                                          <label class="c-label " for="${mbList.coupnCtgCd}"><span class="sr-only">${mbList.imgDesc}</span></label>
                                      </c:if>
                                      <button type="button" onclick="showDtl('${mbList.coupnCtgCd}');">
                                        <img src="${mbList.thumbImgPc}" alt="${mbList.imgDesc}">
                                      </button>
                                    </div>
                                    <div class="mbership-button">
                                      <button ${mbList.leftQnty eq '0' or mbList.downPossibleYn eq 'N' ? 'disabled':''} onclick="checkDown('${mbList.coupnCtgCd}');">쿠폰받기<span class="sr-only">${mbList.coupnCtgNm}</span><i class="c-icon c-icon--download-ty2-red" aria-hidden="true"></i></button>
                                    </div>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                </div>
                <div class="c-nodata" style="display:none;">
                    <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                    <p class="c-nodata__text">조회된 쿠폰이 없습니다.</p>
                </div>
                <div class="mbership-wrap">
                    <div class="mbership-head__total">
                        <span>조회</span>
                        <em>${couponAccessCnt}</em>
                    </div>
                </div>
                <!-- M쿠폰 쿠폰 영역 끝 -->
            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>
