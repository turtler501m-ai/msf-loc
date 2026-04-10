<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/portal/phone/phoneList.js?version=23-10-30"></script>
    <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
    <script type="text/javascript">
    var reqRateCd = '${param.rateCd}';
    document.addEventListener('UILoaded', function() {
       KTM.swiperA11y('#swiperOpenMarket', {
         autoplay: {
           delay: 3000,
           disableOnInteraction: false,
         },
         loop: $("#swiperOpenMarket .swiper-wrapper .swiper-slide").length > 1, // 상단 배너 루프 처리
       });
     });
    setTimeout(function(){
		$('#swiperOpenMarket .swiper-slide').removeAttr('tabindex');
	},100);
    </script>
    </t:putAttribute>

  <t:putAttribute name="contentAttr">
    <form name="phoneViewfrm" id="phoneViewfrm" action="/product/phone/phoneView.do" method="post">
        <input type="hidden" name="ctgText" value="중고폰"/>
        <input type="hidden" name="rateCd" value='<c:out value="${rateCd}"/>'/>
        <input type="hidden" id="prodId" name="prodId" value=""/>
    </form>
       <form name="phonelistfrm" id="phonelistfrm" method="post">
           <input type="hidden" id="listOrderEnum" name="listOrderEnum" value="RECOMMEND"/>
        <input type="hidden" id="selProdCtgId" name="selProdCtgId" />
        <input type="hidden" id="shandYn" name="shandYn" value="N"/>
        <input type="hidden" id="prodType" name="prodType" value="04" />
        <input type="hidden" id="sesplsYn" name="sesplsYn" value="N" />
        <input type="hidden" id="prodCtgId" name="prodCtgId" value="" />
        <input type="hidden" id="makrCd" name="makrCd" value="" />
        <input type="hidden" name="prodId" value="${param.prodId}"/>
       </form>
    <div class="ly-content" id="main-content">
        <div class="ly-page--title">
            <h2 class="c-page--title">중고폰</h2>
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
            <div class="u-mt--68">
                <a class="c-button c-button--sm c-button--white c-button-round--sm" href="/product/phone/officialNoticeSupportList.do">단말 공통지원금 안내</a>
                <button class="c-button c-button--sm c-button--white c-button-round--sm u-ml--8"	data-dialog-trigger="#phone_grade">등급안내</button>
            </div>

            <%-- <div class="c-accordion c-accordion--type5" data-ui-accordion>
                <ul class="c-accordion__box">
                    <li class="c-accordion__item">
                        <div class="c-accordion__head">
                            <strong class="c-accordion__title">어떤 중고폰을 원하세요?</strong>
                            <button class="runtime-data-insert c-accordion__button" id="acc_header_c1" type="button" aria-expanded="false" aria-controls="acc_content_c1">
                                <span class="c-hidden">어떤 휴대폰을 원하세요? 상세열기</span>
                                <span class="c-accordion__icon"> <i class="c-icon c-icon--arrow-round" aria-hidden="true"></i></span>
                            </button>
                        </div>
                        <div class="c-accordion__panel expand" id="acc_content_c1" aria-labelledby="acc_header_c1">
                            <div class="c-accordion__inside">
                                <div class="c-filter">
                                    <strong class="c-filter__title">네트워크</strong>
                                    <div class="c-filter__inner">
                                        <input value="" class="c-radio c-radio--button c-radio--button--type3"	id="radNetwork1" type="radio" name="radNetwork" checked>
                                        <label class="c-label" for="radNetwork1">전체</label>
                                        <input value="04" class="c-radio c-radio--button c-radio--button--type3" id="radNetwork2" type="radio" name="radNetwork">
                                        <label class="c-label" for="radNetwork2">LTE</label>
                                        <input value="08" class="c-radio c-radio--button c-radio--button--type3"	id="radNetwork3" type="radio" name="radNetwork">
                                        <label class="c-label" for="radNetwork3">5G</label>
                                    </div>
                                </div>
                                <div class="c-filter">
                                    <strong class="c-filter__title">제조사</strong>
                                    <div class="c-filter__inner no-scroll">
                                        <input value="" class="c-radio c-radio--button c-radio--button--type3"	id="radProduct0" type="radio" name="radProduct" checked>
                                        <label class="c-label" for="radProduct0">전체</label>
                                        <c:forEach items="${mspOrgList}" var="orgItem" varStatus="status">
                                            <input class="c-radio c-radio--button c-radio--button--type3"	name="radProduct"	id="radManufact<c:out value="${status.count}"/>" type="radio" value="${orgItem.mnfctId }" <c:if test ="${commonSearchDto.makrCd eq orgItem.mnfctId }">checked</c:if>>
                                            <label class="c-label" for="radManufact<c:out value="${status.count}"/>"><c:out	value="${orgItem.mnfctNm}" /></label>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="c-button-wrap u-mt--40">
                                    <button id="search_button" type="button" class="c-button c-button-round--md c-button--mint c-button--w460">검색</button>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div> --%>

            <div id="phoneSortDivArea" class="phone-sort-wrap u-mt--20 c-form-row u-al-center">
            	<div class="c-checktype-row">
                    <span class="u-fs-16 u-fw--medium">제조사</span>
                    <input type="checkbox" class="c-checkbox c-checkbox--type3" id="chkProduct_0" value="0000" name="chkProduct"  checked >
                    <label class="c-label u-mb--0 u-ml--8 u-mr--8" for="chkProduct_0">전체</label>
                    <input type="checkbox" class="c-checkbox c-checkbox--type3" id="chkProduct_1" value="0141" name="chkProduct" checked >
                    <label class="c-label u-mb--0 u-mr--8" for="chkProduct_1">삼성</label>
                    <input type="checkbox" class="c-checkbox c-checkbox--type3" id="chkProduct_2" value="0222" name="chkProduct" checked >
                    <label class="c-label u-mb--0 u-mr--8" for="chkProduct_2">애플</label>
                    <input type="checkbox" class="c-checkbox c-checkbox--type3" id="chkProduct_3" value="9999" name="chkProduct" checked >
                    <label class="c-label u-mb--0 u-mr--0" for="chkProduct_3">기타</label>
			    </div>
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
        <div class="c-bs-compare c-bs-compare--type1">
            <button class="c-bs-compare__openner" id="bs_acc_btn" type="button">
                휴대폰 비교함
                <span id="phoneInBoxArea" class="in-box">
                    <span class="c-hidden">0개</span>
                    <i class="c-icon c-icon--num0" aria-hidden="true"></i>
                </span>
                <span class="sr-only">휴대폰 비교함 상세 열기|휴대폰 비교함 상세 닫기</span>
            </button>
            <div class="c-bs-compare__container">
                <div class="c-bs-compare__inner">
                    <div class="c-bs-compare__detail" aria-hidden="true">
                        <div class="c-row c-row--lg u-pt--32 u-pb--32">
                            <div class="u-flex-between">
                                <div class="left-box">
                                    <h4 class="c-heading c-heading--fs24">
                                        <b>휴대폰 비교하기</b>
                                    </h4>
                                    <p class="c-text c-text--fs17 u-mt--12 u-co-gray">
                                        휴대폰 선택 고민되시나요? <br>휴대폰과 월 납부금액을 한 눈에 비교해보세요.
                                    </p>
                                    <div class="c-button-wrap u-width--340 u-mt--24">
                                        <button type="button" class="c-button c-button-round--md c-button--gray u-width--160" onclick="sessionProc('','','','','removeAll',0);">비교함 비우기</button>
                                        <button id="compareBtn" type="button" class="c-button c-button-round--md c-button--primary u-width--160">비교하기</button>
                                    </div>
                                </div>
                                <div class="phone-comparison-box" id="phoneComparisonBoxArea">
                                    <div class="phone-comparison-box__item dummy">
                                        <i class="c-icon c-icon--compare-phone" aria-hidden="true"></i>
                                        <p class="c-text c-text--fs14 u-co-gray u-mt--12">
                                            휴대폰을 <br> 선택해 주세요
                                        </p>
                                    </div>
                                    <div class="phone-comparison-box__item dummy">
                                        <i class="c-icon c-icon--compare-phone" aria-hidden="true"></i>
                                        <p class="c-text c-text--fs14 u-co-gray u-mt--12">
                                            휴대폰을 <br> 선택해 주세요
                                        </p>
                                    </div>
                                    <div class="phone-comparison-box__item dummy">
                                        <i class="c-icon c-icon--compare-phone" aria-hidden="true"></i>
                                        <p class="c-text c-text--fs14 u-co-gray u-mt--12">
                                            휴대폰을 <br> 선택해 주세요
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="c-modal c-modal--md" id="phone_grade" role="dialog" aria-labelledby="#phone_grade_title">
        <div class="c-modal__dialog" role="document">
            <div class="c-modal__content">
                <div class="c-modal__header">
                    <h2 class="c-modal__title" id="phone_grade_title">중고폰 등급안내</h2>
                    <button class="c-button" data-dialog-close>
                        <i class="c-icon c-icon--close" aria-hidden="true"></i>
                        <span class="c-hidden">팝업닫기</span>
                    </button>
                </div>
                <div class="c-modal__body">
                    <div class="c-table">
                        <table>
                            <caption>중고폰(S 급, A급, 리퍼폰) 등급에 대한 안내 표입니다.</caption>
                            <colgroup>
                                <col style="width: 24%">
                                <col>
                            </colgroup>
                            <tbody class="c-table__left">
                                <tr>
                                    <th scope="row">S+급</th>
                                    <td>단순 개봉이력으로 새폰과 다름없는 퀄리티</td>
                                </tr>
                                <tr>
                                    <th scope="row">S급</th>
                                    <td>미세한 생활 스크래치 수준의 새폰같은 깨끗함</td>
                                </tr>
                                <tr>
                                    <th scope="row">리퍼폰</th>
                                    <td>새로운 정품 부품으로 외관 교체가 완료된 폰</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:import url="/product/phone/phoneCompareList.do"></c:import>
  </t:putAttribute>
</t:insertDefinition>
