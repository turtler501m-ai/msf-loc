<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="layoutGnbDefault">

    <t:putAttribute name="titleAttr">고객 혜택</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/portal/vendor/swiper.min.js"></script>
        <script type="text/javascript" src="/resources/js/portal/benefit/benefitMain.js?version=25.12.24"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="tab" value="${tab}">
        <input type="hidden" id="seq" value="${seq}">
        <input type="hidden" id="landingUrl" name="landingUrl">
                <input type="hidden" id="loginSoc" value="${loginSoc}">

        <div class="ly-content" id="main-content">
            <div class="ly-page--title">
                <h2 class="c-page--title">고객 혜택</h2>
            </div>

            <!-- 슬라이드 배너 시작 -->
            <div class="cust-bene-banner u-mb--0" id="custBeneBanner">
                <div class="swiper-container">
                    <ul class="swiper-wrapper">
                        <c:if test="${bannerList ne null and !empty bannerList}">
                            <c:forEach items="${bannerList}" var="banner">
                                <li class="swiper-slide" tabindex="0" style="background-color: ${banner.bgColor}" aria-controls="${banner.linkUrlAdr}">
                                    <div class="u-box--w1100 u-margin-auto">
                                        <img src="${banner.bannImg}" alt="${banner.imgDesc}">
                                    </div>
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                    <div class="swiper-controls-wrap u-box--w1100">
                        <div class="swiper-controls">
                            <button class="cust-bene-pager swiper-button-prev" type="button" role="button" aria-label="이전 슬라이드"></button>
                            <button class="cust-bene-pager swiper-button-next" type="button" role="button" aria-label="다음 슬라이드"></button>
                            <button class="cust-bene-pager swiper-button-pause" type="button">
                                <span class="c-hidden">자동재생 정지</span>
                            </button>
                            <div class="cust-bene-pager swiper-pagination" aria-hidden="true"></div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 슬라이드 배너 끝 -->

            <div class="cust-bene-head--wrap">
                <div class="cust-bene-head">
                    <div class="cust-bene-head__desc-wrap">
                        <p class="cust-bene-head__title">놓치고 있는 혜택까지 모아보는 진짜 내 통신비!</p>
                        <p class="cust-bene-head__desc"><b>통신비 계산기</b></p>
                    </div>
                    <div class="cust-bene-calc">
                        <div class="cust-bene-calc__form">
                            <div class="cust-bene-calc__row">
                                <div class="c-form">
                                    <label class="c-label" for="rateList">요금제 선택</label>
                                    <select class="c-select" id="rateList">
                                    </select>
                                </div>
                            </div>
                            <div class="cust-bene-calc__col-wrap">
                                <div class="cust-bene-calc__col">
                                    <div class="c-form">
                                        <div class="c-input-wrap u-mt--0">
                                            <label class="c-label" for="mcashShop">M쇼핑할인</label>
                                            <input class="c-input" id="mcashShop" type="text" value="쿠팡" readonly>
                                        </div>
                                    </div>
                                    <div class="c-form">
                                        <label class="c-label" for="monthlyPaymentAmount">월 예상 결제 금액</label>
                                        <select class="c-select" id="monthlyPaymentAmount">
                                        </select>
                                    </div>
                                </div>
                                <div class="cust-bene-calc__col">
                                    <div class="c-form">
                                        <label class="c-label" for="cprtCardList">제휴카드 선택</label>
                                        <select class="c-select" id="cprtCardList" onchange="handleCardChange(this);">
                                        </select>
                                    </div>
                                    <div class="c-form">
                                        <label class="c-label" for="cardUsageAmount">월 예상 사용 금액</label>
                                        <select class="c-select" id="cardUsageAmount">
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cust-bene-calc__result">
                            <p class="cust-bene-calc__result-title">내가 받을 할인 혜택</p>
                            <dl class="cust-bene-calc__result-item">
                                <dt class="cust-bene-calc__result-item-desc">요금제 월 정액</dt>
                                <dd class="cust-bene-calc__result-item-price"><span id="ratePrice">0</span>원</dd>
                            </dl>
                            <dl class="cust-bene-calc__result-item">
                                <dt class="cust-bene-calc__result-item-desc">M쇼핑할인 금액</dt>
                                <dd class="cust-bene-calc__result-item-price"><b><span id="mcashDiscountAmount">0</span>원</b></dd>
                            </dl>
                            <dl class="cust-bene-calc__result-item">
                                <dt class="cust-bene-calc__result-item-desc">제휴카드 할인 금액</dt>
                                <dd class="cust-bene-calc__result-item-price"><b><span id="cardDiscountAmount">0</span>원</b></dd>
                            </dl>
                            <div class="cust-bene-calc__result-total">
                                <div class="cust-bene-calc__result-total-desc">월 청구 금액 <em>(<span id="totalDiscountRate">0</span>% 할인)</em></div>
                                <div class="cust-bene-calc__result-total-price"><span id="totalPrice">0</span>원</div>
                            </div>
                        </div>
                    </div>
                    <div class="c-button-wrap u-mt--28">
                        <button type="button" class="c-button c-button-round--md c-button--primary u-width--320" id="priceCalculateBtn" onclick="calculatePrice()">계산하기</button>
                    </div>
                </div>
            </div>

            <div class="cust-bene-content">
                <div class="c-tabs c-tabs--type1 cust-bene no-sticky" id="custBene-tab" data-ui-tab>
                    <div class="c-tabs__list" role="tablist">
                        <button class="c-tabs__button" id="tab1" role="tab" aria-controls="tabpanel1" aria-selected="false" tabindex="-1" data-name="shopping"><p>M쇼핑할인</p><span>통신비 할인</span></button>
                        <button class="c-tabs__button" id="tab2" role="tab" aria-controls="tabpanel2" aria-selected="false" tabindex="-1" data-name="card"><p>제휴카드 할인</p><span>통신비 할인</span></button>
                        <button class="c-tabs__button" id="tab3" role="tab" aria-controls="tabpanel3" aria-selected="false" tabindex="-1" data-name="coupon"><p>M쿠폰</p><span>무료 쿠폰</span></button>
                        <button class="c-tabs__button" id="tab4" role="tab" aria-controls="tabpanel4" aria-selected="false" tabindex="-1" data-name="market"><p>M마켓</p><span>멤버십 쇼핑몰</span></button>
                    </div>
                </div>
                <div class="c-tabs__panel" id="tabpanel1" role="tabpanel" aria-labelledby="tab1" tabindex="-1">
                    ${mcashContent}
                    <span class="u-co-gray" style="position: absolute;z-index: 2;margin: 1.3rem 0.5rem 0;right: calc(50% - 29rem);bottom: -2rem;">조회 | ${viewCount.shopping}</span>
                </div>
                <div class="c-tabs__panel" id="tabpanel2" role="tabpanel" aria-labelledby="tab2" tabindex="-1">
                    <div class="cust-bene-card-content">
                        <img src="/resources/images/portal/benefit/custbene_card_title_banner.png?v=20251125" alt="통신비 절약 제휴카드 자동이체, 최대 2만 5천원 청구할인! 카드만 잘 골라도 통신비 반값">
                        <div class="cust-bene-card-wrap u-mt--96">
                            <p class="cust-bene-card__title">제휴카드로 알뜰하게 통신비 할인</p>
			    			<p class="cust-bene-card__title-desc">제휴카드 자동이체 신청으로 통신비 할인 받으세요!<br />전월 실적에 따른 통신비 할인 혜택이 제공됩니다.</p>
                            <p class="cust-bene-card__subtitle">※ 카드 별 자세한 사용 금액 구간은 카드 이미지 선택 후 확인 부탁드립니다.</p>
                            <p class="cust-bene-card__subtitle u-mt--4">※ 카드 별 할인 금액의 경우, 프로모션 금액이 합쳐진 최대 금액이며 해당 금액의 경우, 카드사의 상황에 따라 변동될 수 있습니다.</p>
                            <div class="cust-bene-card-list">
                                <div class="cust-bene-card-tier" id="cardTierList">
                                    <p class="cust-bene-card-tier__title">제휴카드<br />월 사용 금액</p>
                                </div>
                                <div class="swiper-banner" id="swiperCustBeneCard">
                                    <div class="swiper-container">
                                        <ul class="swiper-wrapper">
                                        </ul>
                                    </div>
                                    <button class="swiper-button-next" type="button"></button>
                                    <button class="swiper-button-prev" type="button"></button>
                                </div>
                            </div>
                            <div class="cust-bene-card-detail" id="custBeneCardDetail" aria-hidden="true" style="display:none;">
                                <div class="c-button-wrap u-mt--48" style="display:none;">
                                    <button class="c-button c-button--lg c-button--gray--type3 c-button--w220 u-ml--20" id="custBeneCardDetailClose">자세히 보기 닫기</button>
                                    <a class="c-button c-button--lg c-button--primary c-button--w220 u-ml--0" href="" title="카드 혜택 자세히 보기 페이지 이동" id="cardApplyBtn">카드 혜택 자세히 보기</a>
                                </div>
                                <p class="cust-bene-card-detail__title cprtCardGdncNm"></p>
                                <div class="card-benefit--box one-source cardDetailContent">
                                </div>
                            </div>
                        </div>
                        <div class="cust-bene-card-wrap" id="custBeneCardNotice">
                        	<div class="cust-bene-card-wrap-guide u-mt--80">
								<p class="cust-bene-card__title">제휴카드, <span>자동납부</span>를 등록해야<br />청구할인 받을 수 있어요!</p>
								<ul class="cust-bene-card-guide">
									<li class="cust-bene-card-guide__item">
										<p class="cust-bene-card-guide__desc">케이티엠모바일 공식<br />홈페이지 로그인 후,<br />마이페이지 > 납부방법 변경 클릭</p>
										<div class="cust-bene-card-guide__img">
											<img src="/resources/images/portal/benefit/custbene_card_guide1.png" alt="">
										</div>
									</li>
									<li class="cust-bene-card-guide__item">
										<p class="cust-bene-card-guide__desc">신용카드 자동납부 선택 후,<br />발급받은 제휴카드 번호<br />입력하면 완료!</p>
										<div class="cust-bene-card-guide__img">
											<img src="/resources/images/portal/benefit/custbene_card_guide2.png" alt="">
										</div>
									</li>
								</ul>
							</div>
                            ${cardNotice}
                        </div>
                    </div>
                    <span class="u-co-gray" style="position: absolute;z-index: 2;margin: 1.3rem 0.5rem 0;right: calc(50% - 29rem);bottom: -2rem;">조회 | ${viewCount.card}</span>
                </div>
                <div class="c-tabs__panel" id="tabpanel3" role="tabpanel" aria-labelledby="tab3" tabindex="-1">
                    <div class="cust-bene-coupon-content">
                        <img src="/resources/images/portal/benefit/custbene_coupon_title_banner.png" alt="브랜드 무료 쿠폰 M쿠폰, M모바일 고객님을 위한 무료 쿠폰 서비스입니다. 매월 다양한 브랜드의 무료할인 쿠폰을 받아 알뜰하게 사용해 보세요">
                        <div class="cust-bene-coupon-wrap u-mt--48">
                            <div class="cust-bene-coupon__title-wrap">
		            			<p class="cust-bene-coupon__title">인기 쿠폰 <span class="cust-bene-coupon__total">총 <span></span> 개의 쿠폰</span></p>
								<div class="cust-bene-coupon__label-group">
									<span class="cust-bene-coupon__label cust-bene-coupon__label--new"><em></em>new</span>
									<span class="cust-bene-coupon__label cust-bene-coupon__label--event"><em></em>event</span>
									<span class="cust-bene-coupon__label cust-bene-coupon__label--hot"><em></em>hot</span>
								</div>
			            	</div>
                            <div class="cust-bene-coupon-list" id="couponListDiv">
                            </div>
                            <!-- 더보기 -->
                            <div class="c-button-wrap u-mt--20">
                                <button type="button" class="c-button btn-more" id="couponMoreBtn" onclick="getCouponList()">더보기 <i class="c-icon c-icon--arrow-red" aria-hidden="true"></i></button>
                            </div>
                        </div>
                        <div class="cust-bene-coupon-wrap">
                            <p class="cust-bene-coupon__title u-mt--60">My 쿠폰</p>
                            <div class="cust-bene-coupon-mylist">
                                <!-- 비 로그인 시 나오는 영역 -->
                                <div class="c-box c-box--type1 u-mr--20" id="myCouponLogin" style="display:none;">
                                    <p class="mbership-head__text"><a id="btnLogin" class="c-text-link--bluegreen" href="javascript:void(0);" title="로그인 페이지 바로가기" onclick="goToLogin()">로그인</a> 하고 다운로드 받은 쿠폰을 확인하세요.</p>
                                </div>
                                <div class="c-box c-box--type1 u-mr--20" id="myCouponRegular" style="display:none;">
                                    <p class="mbership-head__text"><a id="btnLogin" class="c-text-link--bluegreen" href="/mypage/multiPhoneLine.do" title="정회원 인증 페이지 바로가기">정회원 인증</a> 하고 다운로드 받은 쿠폰을 확인하세요.</p>
                                </div>
                                <div class="c-box c-box--type1 u-mr--20" id="myCouponEmpty" style="display:none;">
                                    <p class="mbership-head__text">다운로드 받은 쿠폰이 없습니다.</p>
                                </div>
                                <!-- 마이페이지 쿠폰 기존 소스 활용 -->
                                <div class="c-card-col c-card-col--3" id="myCouponListDiv" style="display:none;">
                                </div>
                            </div>
                        </div>
                        <div class="cust-bene-coupon-wrap">
                            <div class="c-notice-wrap u-mt--56">
                                <h5 class="c-notice__title">
                                    <span>유의사항</span>
                                </h5>
                                <ul class="c-notice__list">
                                    <li>쿠폰은 매월 다운로드 받을 수 있습니다.</li>
                                    <li>다운로드 받은 쿠폰은 중복으로 등록 및 사용이 불가합니다.</li>
                                    <li>쿠폰의 기간 연장은 불가하며, 유효기간 내 사용하지 않은 쿠폰은 만료되고 재발급되지 않습니다.</li>
                                    <li>가입 상품 및 이용 조건에 따라 쿠폰 다운로드는 불가능할 수도 있으며, 변경될 수 있습니다.</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <span class="u-co-gray" style="position: absolute;z-index: 2;margin: 1.3rem 0.5rem 0;right: calc(50% - 29rem);bottom: -2rem;">조회 | ${viewCount.coupon}</span>
                </div>
                <div class="c-tabs__panel" id="tabpanel4" role="tabpanel" aria-labelledby="tab4" tabindex="-1">
                    <div class="cust-bene-mmarket-content">
                        <img src="/resources/images/portal/benefit/custbene_mmarket_title_banner.png" alt="멤버십 쇼핑몰 M마켓, M모바일 고객님을 위한 멤버십 쇼핑몰입니다. 자급제는 기본 다양한 할인 쿠폰과 반값 핫딜에서 최저가 상품들을 만나보세요.">
                        <div class="cust-bene-mmarket-wrap">
                            <div class="cust-bene-mmarket-special">
                                <div class="cust-bene-mmarket-title">
                                    <div class="cust-bene-mmarket-title__img"></div>
                                    <p>인터넷 최저가보장! 매일 바뀌는 특가상품</p>
                                </div>
                                <div class="swiper-banner" id="swiperCustBeneMmarket">
                                    <div class="swiper-container">
                                        <ul class="swiper-wrapper">
                                        </ul>
                                    </div>
                                    <button class="swiper-button-next" type="button"></button>
                                    <button class="swiper-button-prev" type="button"></button>
                                </div>
                            </div>
                        </div>
                        <div class="cust-bene-mmarket-wrap">
                            <img class="u-mt--80" src="/resources/images/portal/benefit/custbene_mmarket_guide.png" alt="M마켓 가입방법 M모바일 가입고객이라면 약관 동의 후 쉽고 간편하게 가입완료 1.M마켓 가입 안내 페이지 이동 2.M마켓 약관 동의(최초 1회) 3. M마켓 회원가입 후 이용">
                        </div>
                        <div class="cust-bene-mmarket-wrap">
                            <div class="cust-bene-mmarket-benefit">
                                <div class="cust-bene-mmarket-benefit-wrap">
                                    <div class="cust-bene-mmarket-benefit__info">
                                        <div class="cust-bene-mmarket-benefit__info-desc">
                                            <h4>M마켓 쇼핑하고 혜택받기</h4>
                                            <div class="c-button-wrap">
                                                <button type="button" class="c-button" onclick="goToMarket()">바로가기</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="cust-bene-mmarket-benefit__img">
                                        <div class="cust-bene-mmarket-benefit__store">
                                            <div class="cust-bene-mmarket-benefit__store-item">
                                                <div class="cust-bene-mmarket-benefit__store-img">
                                                    <img src="/resources/images/portal/benefit/custbene_mmarket_qr1.png" alt="M마켓 이용을 위한 애플 앱스토어 다운받기 QR코드(http://etbs.me/QAY-I)">
                                                </div>
                                                <a class="cust-bene-mmarket-benefit__store-anchor	" href="http://etbs.me/QAY-I" target="_blank" title="M마켓 이용을 위한 애플 앱스토어 페이지 새창 열림">
                                                    <img src="/resources/images/portal/benefit/custbene_mmarket_store1.png" alt="M마켓 이용을 위한 스토어 이미지">
                                                </a>
                                            </div>
                                            <div class="cust-bene-mmarket-benefit__store-item">
                                                <div class="cust-bene-mmarket-benefit__store-img">
                                                    <img src="/resources/images/portal/benefit/custbene_mmarket_qr2.png" alt="M마켓 이용을 위한 구글플레이 다운받기 QR코드(http://etbs.me/QAY-A)">
                                                </div>
                                                <a class="cust-bene-mmarket-benefit__store-anchor	" href="http://etbs.me/QAY-A" target="_blank" title="M마켓 이용을 위한 구글플레이 페이지 새창 열림">
                                                    <img src="/resources/images/portal/benefit/custbene_mmarket_store2.png" alt="M마켓 이용을 위한 스토어 이미지">
                                                </a>
                                            </div>
                                            <div class="cust-bene-mmarket-benefit__store-item">
                                                <div class="cust-bene-mmarket-benefit__store-img">
                                                    <img src="/resources/images/portal/benefit/custbene_mmarket_qr3.png" alt="M마켓 이용을 위한 원스토어 다운받기 QR코드(http://etbs.me/QAY-O)">
                                                </div>
                                                <a class="cust-bene-mmarket-benefit__store-anchor	" href="http://etbs.me/QAY-O" target="_blank" title="M마켓 이용을 위한 원스토어 페이지 새창 열림">
                                                    <img src="/resources/images/portal/benefit/custbene_mmarket_store3.png" alt="M마켓 이용을 위한 스토어 이미지">
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="c-notice-wrap  u-mt--64">
                            <h5 class="c-notice__title">
                                <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
                                <span>유의사항</span>
                            </h5>
                            <ul class="c-notice__list">
                                <li>M마켓은 kt M모바일을 이용하시는 고객님만 서비스 이용이 가능합니다.</li>
                                <li>M마켓 가입/이용은 만 14세 이상 다이렉트몰 회원만 가입/이용 하실 수 있습니다.</li>
                                <li>최초 1회 약관동의 및 M마켓 회원가입 후에는 별도 접속 및 로그인을 통한 M마켓 이용이 가능합니다.</li>
                                <li>kt M모바일 회선을 해지하거나 다이렉트몰 회원을 탈퇴하실 경우 M마켓 포인트는 소멸되오니 유의하시기 바랍니다.</li>
                                <li>기타 M마켓 관련 문의는 운영대행사인 e-제너두(주) 고객센터를 통해 가능합니다.</li>
                                <li class="bullet--fyr">고객센터 1899-0522 (평일 09:00 ~ 18:00 / 점심시간 12:00 ~ 13:00, 주말, 공휴일 휴무)</li>
                            </ul>
                        </div>
                    </div>
                    <span class="u-co-gray" style="position: absolute;z-index: 2;margin: 1.3rem 0.5rem 0;right: calc(50% - 29rem);bottom: -2rem;">조회 | ${viewCount.market}</span>
                </div>
            </div>
        </div>

        <div id="MstoreTermsAgree"></div>
    </t:putAttribute>
</t:insertDefinition>
