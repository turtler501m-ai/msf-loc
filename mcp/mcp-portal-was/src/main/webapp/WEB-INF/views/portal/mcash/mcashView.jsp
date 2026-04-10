<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<t:insertDefinition name="layoutGnbDefault">

  <t:putAttribute name="titleAttr">M쇼핑할인</t:putAttribute>

  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
    <script type="text/javascript" src="/resources/js/common/validator.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
    <script type="text/javascript" src="/resources/js/portal/mcash/mcashView.js"></script>
    <script type="text/javascript" src="/resources/js/portal/vendor/swiper.min.js"></script>
  </t:putAttribute>

  <t:putAttribute name="contentAttr">

    <div class="ly-content" id="main-content">
      <div class="ly-page--title">
        <h2 class="c-page--title">M쇼핑할인</h2>
      </div>
      <div class="mshopping-banner">
        <div class="mshopping-banner__wrap">
          <h3>
               M모바일로 쇼핑하고<br/>
            <span>통신비 할인받자!</span>
          </h3>
          <img src="/resources/images/portal/product/mshopping_banner.png" alt="">
        </div>
      </div>

      <!-- M쇼핑할인 컨텐츠 -->
      <div class="mshopping-content">
        <div class="mshopping-head">
          <p class="mshopping__title">M모바일로 쇼핑하면 통신비 할인</p>
          <p class="mshopping__text">원하는 곳에서 쇼핑할 때마다 캐시 받고 적립 된 캐시로<br/>매월 통신비 할인 받으세요. M쇼핑할인으로 보다 합리적인 통신생활이 가능해집니다.</p>
        </div>
        <img src="/resources/images/portal/product/mshopping_service.png?v=20250819" alt="1. M모바일 앱에 접속하고 복잡한 절차 없이 가입 후 경유만 하세요! 2. 원하는 곳에서 쇼핑하면 다양한 제휴사에서 원하는 상품 쇼핑하세요! 3. 통신비 최대 2만원 할인! 적립된 캐시로 익월 통신비 할인받으세요.">
      </div>
      <div class="ly-content--wrap self-open-bk--light-purple">
          <div class="c-row c-row--lg">
              <img src="/resources/images/portal/product/mshopping_tip.png" alt="할인 꿀팁! 누구나 받을 수 있는 할인 혜택 알아보기, M모바일만의 다양한 할인 혜택받고 통신비 0원으로 이용하세요! 최대 44,000원 요금할인 월 통신비 0원 ※제휴사 사정에 따라 할인 금액은 변동될 수 있습니다.">
          </div>
      </div>
        <div class="mshopping-content">
        <ul class="mshopping-logos">
          <li>
            <div class="slide-right">
              <img src="/resources/images/portal/product/mshopping_logo_trip.png" alt="트립 로고">
              <img src="/resources/images/portal/product/mshopping_logo_emart.png" alt="이마트 로고">
              <img src="/resources/images/portal/product/mshopping_logo_coupang.png" alt="쿠팡 로고">
              <img src="/resources/images/portal/product/mshopping_logo_gmarket.png" alt="지마켓 로고">
              <img src="/resources/images/portal/product/mshopping_logo_nol.png" alt="놀 로고">
              <img src="/resources/images/portal/product/mshopping_logo_temu.png" alt="테무 로고">
              <img src="/resources/images/portal/product/mshopping_logo_trip.png" alt="트립 로고">
              <img src="/resources/images/portal/product/mshopping_logo_emart.png" alt="이마트 로고">
              <img src="/resources/images/portal/product/mshopping_logo_coupang.png" alt="쿠팡 로고">
              <img src="/resources/images/portal/product/mshopping_logo_gmarket.png" alt="지마켓 로고">
              <img src="/resources/images/portal/product/mshopping_logo_nol.png" alt="놀 로고">
              <img src="/resources/images/portal/product/mshopping_logo_temu.png" alt="테무 로고">
            </div>
          </li>
          <li>
            <div class="slide-left">
              <img src="/resources/images/portal/product/mshopping_logo_aliexpress.png" alt="알리익스프레스 로고">
              <img src="/resources/images/portal/product/mshopping_logo_agoda.png" alt="아고다 로고">
              <img src="/resources/images/portal/product/mshopping_logo_auction.png" alt="옥션 로고">
              <img src="/resources/images/portal/product/mshopping_logo_lottehomeshopping.png" alt="롯데홈쇼핑 로고">
              <img src="/resources/images/portal/product/mshopping_logo_yes24.png" alt="예스24 로고">
              <img src="/resources/images/portal/product/mshopping_logo_cjthemarket.png" alt="cj더마켓 로고">
              <img src="/resources/images/portal/product/mshopping_logo_zigzag.png" alt="지그재그 로고">
              <img src="/resources/images/portal/product/mshopping_logo_aliexpress.png" alt="알리익스프레스 로고">
              <img src="/resources/images/portal/product/mshopping_logo_agoda.png" alt="아고다 로고">
              <img src="/resources/images/portal/product/mshopping_logo_auction.png" alt="옥션 로고">
              <img src="/resources/images/portal/product/mshopping_logo_lottehomeshopping.png" alt="롯데홈쇼핑 로고">
              <img src="/resources/images/portal/product/mshopping_logo_yes24.png" alt="예스24 로고">
              <img src="/resources/images/portal/product/mshopping_logo_cjthemarket.png" alt="cj더마켓 로고">
              <img src="/resources/images/portal/product/mshopping_logo_zigzag.png" alt="지그재그 로고">
            </div>
          </li>
        </ul>
        <!--  배너시작 -->
        <div class="swiper-banner u-mt--98" id="swiperMshoppingBanner">
          <div class="swiper-container">
            <div class="swiper-wrapper" id="swiperArea">

            </div>
          </div>
          <button class="swiper-banner-button--next swiper-button-next" type="button"></button>
          <button class="swiper-banner-button--prev swiper-button-prev" type="button"></button>
        </div>
        <!--  배너끝 -->
      </div>
      <div class="ly-content--wrap self-open-bk--light-purple" style="position: relative; margin-top: -1.35rem;">
        <div class="swiper-banner u-mt--120" id="mshoppingGuide">
          <div style="display: flex; justify-content: flex-end;">
              <span class="u-co-gray" style="position: absolute; z-index: 2; margin: 1.3rem 0.5rem 0;">조회 | ${mcashAccessCnt}</span>
          </div>
          <div class="swiper-container">
            <div class="swiper-wrapper">
              <div class="swiper-slide">
                <img src="/resources/images/portal/product/mshopping_guide_01.png" alt="쉽고 빠른 M쇼핑할인 이용방법, 해당 서비스는 이용을 위해 로그인이 필요하며 모바일에서만 이용 가능합니다.">
              </div>
              <div class="swiper-slide">
                <img src="/resources/images/portal/product/mshopping_guide_02.png" alt="M쇼핑할인 가입하기, 해당 서비스 이용을 위해 로그인 후 가입 필수, 모바일에서만 이용 가능">
              </div>
              <div class="swiper-slide">
                <img src="/resources/images/portal/product/mshopping_guide_03.png?v=20250819" alt="M쇼핑할인 서비스 경유하여 제휴사 쇼핑, 원하는 제휴사 선택 후 해당 링크로 접속 → 쇼핑/결제 후 캐시 적립 가능">
              </div>
              <div class="swiper-slide">
                <img src="/resources/images/portal/product/mshopping_guide_04.png" alt="제휴사별 적립률 만큼 M쇼핑할인 적립">
              </div>
              <div class="swiper-slide">
                <img src="/resources/images/portal/product/mshopping_guide_05.png" alt="보유 중인 캐시에서 익월 통신비 할인, 매월 말일 기준, 3천원/5천원/1만원/2만원 단위로 자동 차감 할인">
              </div>
            </div>
          </div>
          <button class="swiper-banner-button--next swiper-button-next" type="button"></button>
          <button class="swiper-banner-button--prev swiper-button-prev" type="button"></button>
          <div class="swiper-controls-wrap u-box--w1100">
            <div class="swiper-controls">
              <div class="swiper-pagination" aria-hidden="true"></div>
            </div>
          </div>
        </div>
      </div>
      <div class="ly-content--wrap mshopping-app">
        <div class="mshopping-content">
          <div class="mshopping-app__info-wrap">
            <div class="mshopping-app__info">
              <strong>M모바일 앱 다운로드</strong>
              <p>QR코드를 스캔하여 앱을 다운받으세요.</p>
            </div>
            <div class="mshopping-app__img">
              <img src="/resources/images/portal/product/mshopping_app_01.png" alt="M쇼핑할인 이용을 위한 애플 앱스토어 다운받기 QR코드(https://apps.apple.com/us/app/kt-m%EB%AA%A8%EB%B0%94%EC%9D%BC/id1094611503)">
              <img src="/resources/images/portal/product/mshopping_app_02.png" alt="M쇼핑할인 이용을 위한 구글플레이 다운받기 QR코드(https://play.google.com/store/apps/details?id=kt.co.ktmmobile)">
            </div>
          </div>
        </div>
      </div>
      <!-- M쇼핑할인 소개 영상 영역 start -->
      <div class="ly-content--wrap self-open-bk--gray">
        <div class="c-row c-row--lg">
          <div class="opening-guide__image u-mt--24">
            <iframe src="https://www.youtube.com/embed/DWGyC7I5Uhs" width="876" height="492" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen=""> </iframe>
          </div>
        </div>
      </div>
      <!-- M쇼핑할인 소개 영상 영역 end -->
      <c:if test="${!empty bannerTextInfo}">
        <div class="swiper-banner" id="mcashTopBanner" <c:if test="${!empty bannerTextInfo[0].bgColor}">style="background-color: ${bannerTextInfo[0].bgColor}"</c:if>>
          <div class="swiper-container" style="width: 68.7rem; margin: 0 auto">
            <div class="swiper-wrapper">
              <c:forEach var="bannerText" items="${bannerTextInfo}">
                <div class="swiper-slide" <c:if test="${!empty bannerText.bgColor}">style="background-color: ${bannerText.bgColor}"</c:if>>
                  <div class="swiper-slide__text">
                    ${bannerText.txtContent}
                  </div>
                </div>
              </c:forEach>
            </div>
          </div>
        </div>
      </c:if>
        <%--[START] 사용후기 --%>
      <section id="mcashReview" class="main-customer-review" style="display: none; background-color: #ebeff8;">
        <div class="c-main-row">
          <div class="section-title u-plr--80" style="align-items: center;">
            <div style="min-width: 30%;"></div>
            <img src="/resources/images/portal/product/mcash_review_rank_title.png" alt="M쇼핑할인 사용후기" style="height: min-content;">
            <div style="min-width: 30%;">
              <a class="link-all" href="/mcash/review/mcashReview.do" title="사용후기" style="float: right;">전체보기
                <i class="c-icon c-icon--arrow-link" aria-hidden="true"></i>
              </a>
            </div>
          </div>
          <ul id="mcashReviewList" class="review-list">
          </ul>
        </div>
      </section>
        <%--[END] 사용후기 --%>
      <div class="mshopping-content">
        <div class="c-notice-wrap u-mt--48">
          <h5 class="c-notice__title">
            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
            <span>유의사항</span>
          </h5>
          <ul class="c-notice__list">
            <li>M쇼핑할인 서비스는 PC에서 이용 불가합니다. kt M모바일 어플리케이션을 통해 이용해주세요.</li>
            <li>캐시는 통신요금 할인에 이용 가능한 포인트로 1캐시는 1원과 동일한 가치로 사용됩니다.</li>
            <li>통신요금 할인은 3천 원/5천 원/1만 원/2만 원 중 매월 말일 기준으로 보유 중인 캐시에서 통신비가 자동으로 차감되어 할인됩니다.</li>
            <li>캐시는 현금으로 교환 및 타 서비스에서 활용이 불가합니다.</li>
            <li>쇼핑 결제를 통해 적립된 캐시의 유효기간은 적립 일로부터 12개월이며, 이벤트로 지급된 캐시의 유효기간은 적립 일로부터 50일입니다.</li>
            <li>기간 내 미사용 캐시는 소멸되어 복구 불가능합니다.</li>
            <li>캐시적립 유형은 즉시적립/예정적립 두 가지로, 예정적립의 경우 구매 확정 후 캐시가 지급되기까지 일정 기간이 소요될 수 있습니다.</li>
            <li>제휴사 브랜드별 캐시적립 유형은 각 브랜드 캐시적립 페이지 하단의 유의사항을 참고 부탁드립니다.</li>
            <li>정당하지 못한 방법을 통해 적립한 캐시는 모두 회수 처리되며, 향후 캐시 지급 거절 또는 서비스 제공 불가 사유가 될 수 있습니다.</li>
            <li>캐시 적립과 이용에 대한 문의 사항은 (주)비티소프트 고객센터 02-3415-0865로 연락 부탁드립니다.</li>
          </ul>
        </div>
      </div>
    </div>

    <script>
      document.addEventListener('UILoaded', function() {
        KTM.swiperA11y('#mshoppingGuide .swiper-container', {
          navigation : {
            nextEl : '#mshoppingGuide .swiper-button-next',
            prevEl : '#mshoppingGuide .swiper-button-prev',
          },
          pagination: {
            el: '#mshoppingGuide .swiper-pagination',
            type: 'fraction',
          }
        });
      });
    </script>
  </t:putAttribute>
</t:insertDefinition>