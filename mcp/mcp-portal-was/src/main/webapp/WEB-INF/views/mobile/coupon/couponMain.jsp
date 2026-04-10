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
        <script type="text/javascript" src="/resources/js/mobile/coupon/couponMain.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
        <script>
            var reqRateCd = '<c:out value="${rateCd}"/>';
            var swiper = new Swiper('.c-swiper', {
                loop: true,
                pagination: {
                    el: '.swiper-pagination',
                    type: 'fraction',
                }
                <c:if test="${!empty bannerInfo && fn:length(bannerInfo) > 1}">
                ,
                autoplay: {
                    delay: 3000,
                    disableOnInteraction: false,
                },
                on: {
                    init: function() {
                        var swiper = this;
                        var autoPlayButton = this.el.querySelector('.swiper-play-button');
                        if(typeof autoPlayButton != "undefined"){
                            autoPlayButton.addEventListener('click', function() {
                                if (autoPlayButton.classList.contains('play')) {
                                    autoPlayButton.classList.remove('play');
                                    autoPlayButton.classList.add('stop');
                                    swiper.autoplay.start();
                                } else {
                                    autoPlayButton.classList.remove('stop');
                                    autoPlayButton.classList.add('play');
                                    swiper.autoplay.stop();
                                }
                            });
                        }
                    },
                },
                </c:if>
            });
        </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">

        <div class="ly-content" id="main-content">

            <form name="frm" id="frm" method="post" action="/m/loginForm.do">
                <input type="hidden" name="uri" id="uri" value="/m/coupon/couponMain.do"/>
                <input type="hidden" name="userDivisionYn" id="userDivisionYn" value="02"/>
            </form>

            <div class="ly-page-sticky">
                <h2 class="ly-page__head">
                    M쿠폰
                    <button class="header-clone__gnb"></button>
                </h2>
            </div>

            <!--  배너시작 -->
            <c:if test="${!empty bannerInfo}">
                <div class="mbership-banner__swiper upper-banner__box">
                    <div class="c-swiper swiper-container">
                        <div class="swiper-wrapper">
                            <c:forEach var="bannerInfo" items="${bannerInfo}">
                                <c:set var="target" value="_self " />
                                <c:if test="${bannerInfo.linkTarget eq 'Y' }">
                                    <c:set var="target" value=""/>
                                </c:if>
                                <c:choose>
                                    <c:when test="${bannerInfo.mobileLinkUrl eq null || bannerInfo.mobileLinkUrl eq ''}">
                                        <div class="swiper-slide">
                                            <div class="c-hidden">
                                                <p>
                                                    <c:out value="${bannerInfo.bannDesc}" />
                                                </p>
                                            </div>
                                            <img src="<c:out value="${bannerInfo.mobileBannImgNm}"/>" onerror="this.src='/resources/images/mobile/content/img_240_no_phone.png'" alt="<c:out value="${bannerInfo.imgDesc}"/>">
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="swiper-slide">
                                            <div class="c-hidden">
                                                <p>
                                                    <c:out value="${bannerInfo.bannDesc}" />
                                                </p>
                                            </div>
                                            <a href="#" onclick="fn_go_banner('<c:out value="${bannerInfo.mobileLinkUrl}"/>','<c:out value="${bannerInfo.bannSeq}"/>','<c:out value="${bannerInfo.bannCtg}"/>','${target}')">
                                                <img src="${bannerInfo.mobileBannImgNm}" onerror="this.src='/resources/images/mobile/content/img_240_no_phone.png'" alt="<c:out value="${bannerInfo.imgDesc}"/>">
                                            </a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>

                        <c:if test="${!empty bannerInfo && fn:length(bannerInfo) > 1}">
                            <button class="swiper-play-button stop" type="button"></button>
                            <div class="swiper-pagination"></div>
                        </c:if>
                    </div>
                </div>
            </c:if>
            <!--  배너끝 -->
            <div class="mbership-head">
                <p class="mbership-head__title">
                    <b>M쿠폰</b>은 kt M모바일을 이용하시는 고객님께 제공 되는 무료 쿠폰 서비스입니다.
                </p>
                <c:if test="${isLogin eq false}">
                    <p class="mbership-head__text">
                             쿠폰을 받기 위해서는 로그인이 필요합니다.
                        <a class="c-text-link--bluegreen" href="javascript:$('#frm').submit();" title="로그인 페이지 바로가기">로그인하기</a>
                    </p>
                </c:if>
            </div>
            <div class="c-tabs c-tabs--type2" data-tab-active="0">
                <div class="c-tabs__list c-expand sticky-header" data-tab-list="" style="display:none;">
                    <button class="c-tabs__button is-active" id="tabF" aria-current="page">무료 쿠폰</button>
                    <button class="c-tabs__button" id="tabC">유료 쿠폰</button>
                </div>
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
                    <div class="mbership-head__total-wrap">
                        <span id=categoryCnt></span>
                    </div>
                    <div class="mbership-coupon-button">
                        <button onclick="allCheck();" id="allCheck">
                            <span>전체선택</span>
                        </button>
                        <button onclick="checkDown();" id="checkDownAll">
                            <span>쿠폰 일괄 다운받기</span>
                            <i class="c-icon c-icon--download-ty2-white" aria-hidden="true"></i>
                        </button>
                    </div>
                    <br>
                    <ul class="mbership-list">
                        <c:if test="${mbList ne null and !empty mbList}">
                            <c:forEach var="mbList" items="${mbList}">
                                <li class="mbership-item" couponCategory="${mbList.couponCategory}">
                                    <div class="mbership-info" data-dialog-trigger="#mbershipModal">
                                        <c:if test="${mbList.leftQnty ne '0' and mbList.downPossibleYn ne 'N'}">
                                            <input ${mbList.leftQnty eq '0' or mbList.downPossibleYn eq 'N' ? 'disabled':''} class="c-checkbox c-checkbox--type3" id="${mbList.coupnCtgCd}"  type="checkbox" name="couponCheckbox">
                                            <label class="c-label" for="${mbList.coupnCtgCd}"><span class="sr-only">${mbList.imgDesc}</span></label>
                                        </c:if>
                                        <img src="${mbList.thumbImgMo}" alt="${mbList.imgDesc}" onclick="showDtl('${mbList.coupnCtgCd}');" tabindex="0">
                                    </div>
                                    <div class="mbership-button">
                                        <button ${mbList.leftQnty eq '0' or mbList.downPossibleYn eq 'N' ? 'disabled':''} onclick="checkDown('${mbList.coupnCtgCd}');"><i class="c-icon c-icon--download-ty2-white" aria-hidden="true"></i>쿠폰받기</button>
                                    </div>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                    <div class="c-nodata" style="display:none;">
                        <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                        <p class="c-noadat__text">조회된 쿠폰이 없습니다.</p>
                    </div>
                    <div class="mbership-head__total-wrap u-mt--24 u-mb--0">
                        <span></span>
                        <div class="mbership-head__total">
                            <span>조회</span>
                            <em>${couponAccessCnt}</em>
                        </div>
                    </div>
                </div>

            </div>
        </div>

    </t:putAttribute>
</t:insertDefinition>