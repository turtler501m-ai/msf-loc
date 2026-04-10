<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>

<t:insertDefinition name="layoutGnbDefault">

    <c:choose>
        <c:when test="${prodType eq '06'}">
            <t:putAttribute name="titleAttr">리퍼폰 구매하기</t:putAttribute>
        </c:when>
        <c:otherwise>
            <t:putAttribute name="titleAttr">새 휴대폰 구매하기</t:putAttribute>
        </c:otherwise>
    </c:choose>


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
         loop: $("#swiperOpenMarket .swiper-wrapper .swiper-slide").length > 1,  // 상단 배너 루프 처리
       });
     });
    setTimeout(function(){
		$('#swiperOpenMarket .swiper-slide').removeAttr('tabindex');
	},100);
    </script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
    <form name="phoneViewfrm" id="phoneViewfrm" action="/product/phone/phoneView.do" method="post">
        <input type="hidden" name="ctgText" value="공통지원금 단말"/>
        <input type="hidden" name="rateCd" value='<c:out value="${rateCd}"/>'/>
        <input type="hidden" id="prodId" name="prodId" value=""/>
    </form>

        <form name="phonelistfrm" id="phonelistfrm">
           <input type="hidden" id="listOrderEnum" name="listOrderEnum" value="RECOMMEND"/>
           <input type="hidden" id="selProdCtgId" name="selProdCtgId" />
        <input type="hidden" id="shandYn" name="shandYn" value="N"/>
        <input type="hidden" id="prodType" name="prodType" value="${prodType}" />
        <input type="hidden" id="sesplsYn" name="sesplsYn" value="N" />
        <input type="hidden" id="prodCtgId" name="prodCtgId" value="" />
        <input type="hidden" id="makrCd" name="makrCd" value="" />
        <input type="hidden" name="prodId" value="${param.prodId}"/>
       </form>
    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
          <c:choose>
             <c:when test="${prodType eq '06'}">
                 <h2 class="c-page--title">리퍼폰</h2>
             </c:when>
             <c:otherwise>
                 <h2 class="c-page--title">새 휴대폰 구매하기</h2>
             </c:otherwise>
          </c:choose>
      </div>
      <div class="ly-content--wrap">
      <c:if test="${!empty bannerInfo}">
        <div class="swiper-container" id="swiperOpenMarket">
          <ul class="swiper-wrapper">
            <c:forEach var="bannerInfo" items="${bannerInfo}">
                <c:set var="target" value="_self "/>
                <c:if test="${bannerInfo.linkTarget eq 'Y' }"><c:set var="target" value="_blank"/><c:set var="title" value="새창열림"/></c:if>
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
                        <a <c:if test="${bannerInfo.linkTarget eq 'Y' }"> title ='새창열림' </c:if> href="javascript:void(0);" onclick="fn_go_banner('<c:out value="${bannerInfo.linkUrlAdr}"/>','<c:out value="${bannerInfo.bannSeq}"/>','<c:out value="${bannerInfo.bannCtg}"/>','<c:out value="${target}"/>')">
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
        </div>


          <c:choose>
              <c:when test="${prodType eq '06'}">
                  <!-- 리퍼폰(기존) -->
                  <div id="phoneSortDivArea" class="phone-sort-wrap">
                      <div class="c-checktype-row c-hidden">
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
              </c:when>
              <c:otherwise>
                  <!-- 새휴대폰(제조사 추가) -->
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
              </c:otherwise>
          </c:choose>










        <!--데이터 없는 경우-->
        <div class="c-nodata" id="phoneListNoDataArea" style="display: none;">
          <i class="c-icon c-icon--nodata" aria-hidden="true"></i>
          <p class="c-nodata__text">검색 결과가 없습니다.</p>
        </div>
        <ul id="phoneListUlArea" class="phone-list--type3">
        </ul>
      </div>
      <div class="c-bs-compare c-bs-compare--type1">
        <button class="c-bs-compare__openner" id="bs_acc_btn" type="button">휴대폰 비교함<span id="phoneInBoxArea" class="in-box">
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
                    <p class="c-text c-text--fs17 u-mt--12 u-co-gray"> 휴대폰 선택 고민되시나요?
                      <br>휴대폰과 월 납부금액을 한 눈에 비교해보세요.
                    </p>
                    <div class="c-button-wrap u-width--340 u-mt--24">
                      <button type="button" class="c-button c-button-round--md c-button--gray u-width--160" onclick="$('#bs_acc_btn').click();sessionProc('','','','','removeAll',0);">비교함 비우기</button>
                      <button id="compareBtn" type="button" class="c-button c-button-round--md c-button--primary u-width--160">비교하기</button>
                    </div>
                  </div>
                  <div class="phone-comparison-box" id="phoneComparisonBoxArea">
                    <div class="phone-comparison-box__item dummy">
                      <i class="c-icon c-icon--compare-phone" aria-hidden="true"></i>
                      <p class="c-text c-text--fs14 u-co-gray u-mt--12">휴대폰을
                        <br> 선택해 주세요
                      </p>
                    </div>
                    <div class="phone-comparison-box__item dummy">
                      <i class="c-icon c-icon--compare-phone" aria-hidden="true"></i>
                      <p class="c-text c-text--fs14 u-co-gray u-mt--12">휴대폰을
                        <br> 선택해 주세요
                      </p>
                    </div>
                    <div class="phone-comparison-box__item dummy">
                      <i class="c-icon c-icon--compare-phone" aria-hidden="true"></i>
                      <p class="c-text c-text--fs14 u-co-gray u-mt--12">휴대폰을
                        <br> 선택해 주세요
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
    <c:import url="/product/phone/phoneCompareList.do"></c:import>
    </t:putAttribute>
</t:insertDefinition>
