<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/phone/exhibitList.js?version=23-06-13"></script>
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
    </t:putAttribute>

  <t:putAttribute name="contentAttr">
    <form name="phoneViewfrm" id="phoneViewfrm" action="/product/phone/phoneView.do" method="post">
        <input type="hidden" name="ctgText" value="기획전"/>
        <input type="hidden" name="rateCd" value='${rateCd}'/>
        <input type="hidden" id="prodId" name="prodId" value=""/>
    </form>
       <form name="phonelistfrm" id="phonelistfrm" method="post">
           <input type="hidden" id="listOrderEnum" name="listOrderEnum" value="RECOMMEND"/>
            <input type="hidden" id="selProdCtgId" name="selProdCtgId" />
            <input type="hidden" id="shandYn" name="shandYn" value="N"/>
            <input type="hidden" id="ctgCd" name="ctgCd" value="0001" />
            <input type="hidden" id="sesplsYn" name="sesplsYn" value="N" />
            <input type="hidden" id="prodCtgId" name="prodCtgId" value="" />
            <input type="hidden" id="makrCd" name="makrCd" value="" />
            <input type="hidden" name="prodId" value="${param.prodId}"/>
       </form>
    <div class="ly-content" id="main-content">
        <div class="ly-page--title">
            <h2 class="c-page--title">기획전</h2>
        </div>
        <div class="ly-content--wrap">
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
                                            <img src="${bannerInfo.bannImg}" alt="${bannerInfo.imgDesc}"  onerror="this.src='/resources/images/mobile/content/img_240_no_phone.png'"  >
                                        </div>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="swiper-slide" tabindex="0" aria-label="" <c:if test="${!empty bannerInfo.bgColor}">style="background:<c:out value="${bannerInfo.bgColor}"/>"</c:if>>
                                        <div class="u-box--w1100 u-margin-auto">
                                            <a <c:if test="${bannerInfo.linkTarget eq 'Y' }"> title ='새창열림' </c:if> href="javascript:void(0);"
                                                onclick="fn_go_banner('${bannerInfo.linkUrlAdr}','${bannerInfo.bannSeq}','${bannerInfo.bannCtg}','${target}')">
                                                <img src="${bannerInfo.bannImg}" alt="${bannerInfo.imgDesc}" onerror="this.src='/resources/images/mobile/content/img_240_no_phone.png'"  >
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
        <div class="c-row c-row--lg">

            <div id="phoneSortDivArea" class="phone-sort-wrap u-mt--44">
                <div class="phone-sort-wrap__inner">
                    <button type="button" class="phone-sort-wrap__button is-active sortBtn" data-value="RECOMMEND">추천 순<span class="c-hidden">선택됨</span></button>
                    <button type="button" class="phone-sort-wrap__button sortBtn" data-value="NEW_ITEM">신상품 순</button>
                    <button type="button" class="phone-sort-wrap__button sortBtn" data-value="CHARGE_HIGH">할부금 높은 순</button>
                    <button type="button" class="phone-sort-wrap__button sortBtn" data-value="CHARGE_ROW">할부금 낮은 순</button>
                </div>
            </div>
            <!--데이터 없는 경우-->
            <div class="c-nodata" id="phoneListNoDataArea"
                style="display: none;">
                <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
                <p class="c-nodata__text">검색 결과가 없습니다.</p>
            </div>
            <ul id="phoneListUlArea" class="phone-list--type3">
            </ul>
        </div>

    </div>


  </t:putAttribute>
</t:insertDefinition>
