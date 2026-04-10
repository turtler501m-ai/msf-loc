<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>

<t:insertDefinition name="mlayoutDefault">

  <t:putAttribute name="titleAttr">M쇼핑할인</t:putAttribute>

  <t:putAttribute name="scriptHeaderAttr">
    <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/common/dataFormConfig.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/validateMsg.js"></script>
    <script type="text/javascript" src="/resources/js/common/validator.js"></script>
    <script type="text/javascript" src="/resources/js/appForm/simpleErrMsg.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/mcash/mcashView.js"></script>
    <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
    <script>
      var swiper = new Swiper('#swiperMshoppingBanner .c-swiper', {
        loop: true,
        pagination: {
          el: '#swiperMshoppingBanner .swiper-pagination',
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
            var autoPlayButton = this.el.querySelector('#swiperMshoppingBanner .swiper-play-button');
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

      var selfOpeningSwiper = new Swiper('#mshoppingGuide .swiper-container', {
        pagination : {
          el : '#mshoppingGuide .swiper-pagination',
          type: 'fraction',
        },
      });
    </script>
  </t:putAttribute>

  <t:putAttribute name="contentAttr">

    <div class="ly-content" id="main-content">
      <div class="ly-page-sticky">
        <h2 class="ly-page__head">M쇼핑할인
          <button class="header-clone__gnb"></button>
        </h2>
      </div>

      <div class="mshopping-banner">
        <div class="mshopping-banner__wrap">
          <h3>
               M모바일로 쇼핑하고<br/>
            <span>통신비 할인받자!</span>
          </h3>
          <c:if test="${RESULT_MSG eq 'LOGIN'}">
            <a href="/m/loginForm.do" title="로그인 페이지 바로가기">
              로그인 하기
              <i class="c-icon c-icon--anchor-white" aria-hidden="true"></i>
            </a>
          </c:if>
        </div>
        <div class="mshopping-banner__image">
          <img src="/resources/images/mobile/product/mshopping_banner.png" alt="">
        </div>
      </div>

      <!-- M쇼핑할인 컨텐츠 -->
      <div class="mshopping-content">
        <div class="mshopping-head">
          <p class="mshopping__title">M모바일로<br/>쇼핑하면 통신비 할인!</p>
          <p class="mshopping__text">원하는 곳에서 쇼핑할 때마다 캐시 받고<br/>적립 된 캐시로 매월 통신비 할인 받으세요.</p>
        </div>
        <div class="c-expand">
          <img src="/resources/images/mobile/product/mshopping_service.png?v=20250819" alt="1. M모바일 앱에 접속하고 복잡한 절차 없이 가입 후 경유만 하세요! 2. 원하는 곳에서 쇼핑하면 다양한 제휴사에서 원하는 상품 쇼핑하세요! 3. 통신비 최대 2만원 할인! 적립된 캐시로 익월 통신비 할인받으세요.">
          <img src="/resources/images/mobile/product/mshopping_tip.png" alt="할인 꿀팁! 누구나 받을 수 있는 할인 혜택 알아보기, M모바일만의 다양한 할인 혜택받고 통신비 0원으로 이용하세요! 최대 44,000원 요금할인 월 통신비 0원 ※제휴사 사정에 따라 할인 금액은 변동될 수 있습니다.">
        </div>
        <ul class="mshopping-logos">
          <li>
            <div class="slide-right">
              <img src="/resources/images/mobile/product/mshopping_logo_trip.png" alt="트립 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_emart.png" alt="이마트 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_coupang.png" alt="쿠팡 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_gmarket.png" alt="지마켓 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_nol.png" alt="놀 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_temu.png" alt="테무 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_trip.png" alt="트립 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_emart.png" alt="이마트 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_coupang.png" alt="쿠팡 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_gmarket.png" alt="지마켓 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_nol.png" alt="놀 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_temu.png" alt="테무 로고">
            </div>
          </li>
          <li>
            <div class="slide-left">
              <img src="/resources/images/mobile/product/mshopping_logo_aliexpress.png" alt="알리익스프레스 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_agoda.png" alt="아고다 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_auction.png" alt="옥션 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_lottehomeshopping.png" alt="롯데홈쇼핑 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_yes24.png" alt="예스24 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_cjthemarket.png" alt="cj더마켓 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_zigzag.png" alt="지그재그 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_aliexpress.png" alt="알리익스프레스 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_agoda.png" alt="아고다 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_auction.png" alt="옥션 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_lottehomeshopping.png" alt="롯데홈쇼핑 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_yes24.png" alt="예스24 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_cjthemarket.png" alt="cj더마켓 로고">
              <img src="/resources/images/mobile/product/mshopping_logo_zigzag.png" alt="지그재그 로고">
            </div>
          </li>
        </ul>
        <c:choose>
          <c:when test="${RESULT_MSG eq 'LOGIN'}">
            <!-- 비로그인 -->
            <div class="mshopping-button-wrap">
              <!-- 로그인 시 나오는 버튼 -->
              <p>쇼핑몰 이용을 위해서는 로그인이 필요합니다.</p>
              <a class="mshopping-button" href="/m/loginForm.do" title="로그인 페이지 바로가기">로그인하기</a>
            </div>
          </c:when>
          <c:when test="${RESULT_MSG eq 'GRADE'}">
            <div class="mshopping-button-wrap">
              <a class="mshopping-button" href="/m/mypage/multiPhoneLine.do">정회원 인증 하러가기</a>
            </div>
          </c:when>
          <c:when test="${RESULT_MSG eq 'JOIN'}">
            <c:if test="${RESULT_CODE eq '0001' || RESULT_CODE eq '0002'}">
              <div class="mshopping-button-wrap">
                <button class="mshopping-button" title="M쇼핑할인 가입하기" onclick="openMcashJoinPop();">M쇼핑할인 가입하기</button>
              </div>
            </c:if>
            <c:if test="${RESULT_CODE eq '0003'}">
              <div class="c-text-box c-text-box--red deco-combine u-mt--32">
                <p><b class="c-text c-text--type2">서비스 해지 당일에는</b></p>
                <p><b class="c-text c-text--type2">재가입하실 수 없습니다.</b></p>
                <img src="/resources/images/mobile/content/img_combine.svg" alt="">
              </div>
            </c:if>
            <c:if test="${RESULT_CODE eq '0004'}">
              <div class="c-text-box c-text-box--red deco-combine u-mt--32">
                <p><b class="c-text c-text--type2">비정상적인 회원 정보입니다.</b></p>
                <p><b class="c-text c-text--type2">고객센터에 문의바랍니다.</b></p>
                <img src="/resources/images/mobile/content/img_combine.svg" alt="">
              </div>
            </c:if>
          </c:when>
        </c:choose>

        <!--  배너시작 -->
        <div class="swiper-container swiper-event-banner c-expand" id="swiperMshoppingBanner">
          <div class="swiper-wrapper">

          </div>
        </div>
        <!--  배너끝 -->

        <div class="self-guide-wrap--type2 c-expand" id="mshoppingGuide" style="position: relative; margin-bottom: 2rem;">
          <div class="swiper-container">
            <div style="display: flex; justify-content: flex-end;">
              <span class="u-co-gray u-fs-15" style="position: absolute; z-index: 2; margin: 0.65rem 1rem 0 0;">조회 | ${mcashAccessCnt}</span>
            </div>
            <div class="swiper-wrapper">
              <div class="swiper-slide">
                <img src="/resources/images/mobile/product/mshopping_guide_01.png" alt="쉽고 빠른 M쇼핑할인 이용방법, 해당 서비스는 이용을 위해 로그인이 필요하며 모바일에서만 이용 가능합니다.">
              </div>
              <div class="swiper-slide">
                <img src="/resources/images/mobile/product/mshopping_guide_02.png" alt="M쇼핑할인 가입하기, 해당 서비스 이용을 위해 로그인 후 가입 필수, 모바일에서만 이용 가능">
              </div>
              <div class="swiper-slide">
                <img src="/resources/images/mobile/product/mshopping_guide_03.png?v=20250819" alt="M쇼핑할인 서비스 경유하여 제휴사 쇼핑, 원하는 제휴사 선택 후 해당 링크로 접속 → 쇼핑/결제 후 캐시 적립 가능">
              </div>
              <div class="swiper-slide">
                <img src="/resources/images/mobile/product/mshopping_guide_04.png" alt="제휴사별 적립률 만큼 M쇼핑할인 적립">
              </div>
              <div class="swiper-slide">
                <img src="/resources/images/mobile/product/mshopping_guide_05.png" alt="보유 중인 캐시에서 익월 통신비 할인, 매월 말일 기준, 3천원/5천원/1만원/2만원 단위로 자동 차감 할인">
              </div>
            </div>
            <div class="swiper-pagination u-mb--4"></div>
          </div>
        </div>
        <div class="mshopping-app">
          <div class="mshopping-app__info">
            <strong>M모바일 앱 다운로드</strong>
            <p>하단의 버튼을 눌러 앱을 다운받으세요.</p>
          </div>
          <div class="mshopping-app__img u-mb--40">
            <a class="mcash-store__anchor" href="https://play.google.com/store/apps/details?id=kt.co.ktmmobile" target="_blank" title="M쇼핑할인 이용을 위한 구글플레이 페이지 새창 열림">
              <img src="/resources/images/mobile/product/mshopping_app_01.png" alt="M쇼핑할인 이용을 위한 스토어 이미지">
            </a>
            <a class="mcash-store__anchor" href="https://apps.apple.com/us/app/kt-m%EB%AA%A8%EB%B0%94%EC%9D%BC/id1094611503" target="_blank" title="M쇼핑할인 이용을 위한 애플 앱스토어 페이지 새창 열림">
              <img src="/resources/images/mobile/product/mshopping_app_02.png" alt="M쇼핑할인 이용을 위한 스토어 이미지">
            </a>
          </div>
        </div>
        <!-- M쇼핑할인 소개 영상 영역 start -->
        <div class="self-opening-box c-expand u-mt--0">
          <iframe src="https://www.youtube.com/embed/DWGyC7I5Uhs" frameborder="0" allowfullscreen="1" style="width: 100%; height: 250px;" allow="autoplay; encrypted-media"></iframe>
        </div>
        <!-- M쇼핑할인 소개 영상 영역 end -->
        <c:if test="${!empty bannerTextInfo}">
          <div class="swiper-banner" id="mcashTopBanner" <c:if test="${!empty bannerTextInfo[0].bgColor}">style="background-color: ${bannerTextInfo[0].bgColor}; margin-bottom: 0;"</c:if>>
            <div class="swiper-container">
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
        <section id="mcashReview" class="review-info-box review-section c-expand">
          <div class="c-flex--between u-plr--16">
            <h3 class="review-rank__title mcash">
              <img src="/resources/images/mobile/product/mcash_review_rank_title.png" alt="M쇼핑할인 사용후기">
            </h3>
            <div>
              <a class="mcash-btn u-pr--10" href="/m/mcash/review/mcashReview.do" title="M쇼핑할인 사용후기 페이지로 이동">전체보기
                <i class="c-icon c-icon--arrow-link" aria-hidden="true"></i>
              </a>
            </div>
          </div>
          <ul id="mcashReviewList" class="main-review-list u-mt--32">
          </ul>
        </section>
        <%--[END] 사용후기 --%>
        <div class="c-notice-wrap">
          <hr>
          <h5 class="c-notice__title">
            <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
            <span>유의사항</span>
          </h5>
          <ul class="c-notice__list">
            <li>캐시는 통신요금 할인에 이용 가능한 포인트로 1캐시는 1원과 동일한 가치로 사용됩니다.</li>
            <li>통신요금 할인은 3천 원/5천 원/1만 원/2만 원 중 매월 말일 기준으로 보유 중인 캐시에서 통신비가 자동으로 차감되어 할인됩니다.</li>
            <li>캐시는 현금으로 교환 및 타 서비스에서 활용이 불가합니다.</li>
            <li>쇼핑 결제를 통해 적립된 캐시의 유효기간은 적립 일로부터 12개월이며, 이벤트로 지급된 캐시의 유효기간은 적립 일로부터 50일입니다.</li>
            <li>기간 내 미사용 캐시는 소멸되어 복구 불가능합니다.</li>
            <li>캐시적립 유형은 즉시적립/예정적립 두 가지로, 예정적립의 경우 구매 확정 후 캐시가 지급되기까지 일정 기간이 소요될 수 있습니다. </li>
            <li>제휴사 브랜드별 캐시적립 유형은 각 브랜드 캐시적립 페이지 하단의 유의사항을 참고 부탁드립니다.</li>
            <li>정당하지 못한 방법을 통해 적립한 캐시는 모두 회수 처리되며, 향후 캐시 지급 거절 또는 서비스 제공 불가 사유가 될 수 있습니다.</li>
            <li>캐시 적립과 이용에 대한 문의 사항은 (주)비티소프트 고객센터 02-3415-0865로 연락 부탁드립니다.</li>
          </ul>
        </div>
      </div>
    </div>

    <!-- M쇼핑할인 이용약관 팝업 -->
    <div id="McashTermsOfServiceAgree" class="mcashAgreePop"></div>

    <!-- 개인정보 수집 · 이용동의 팝업 -->
    <div id="McashPersonalInfoCollectAgree" class="mcashAgreePop"></div>

    <!-- 개인정보 제3자 제공동의 팝업 -->
    <div id="McashOthersTrnsAgree" class="mcashAgreePop"></div>
  </t:putAttribute>
</t:insertDefinition>