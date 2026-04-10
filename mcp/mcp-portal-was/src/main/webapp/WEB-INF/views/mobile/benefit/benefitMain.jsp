<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="nmcp" uri="nmcp.tag" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="un" uri="http://jakarta.apache.org/taglibs/unstandard-1.0" %>
<un:useConstants var="Constants" className="com.ktmmobile.mcp.common.constants.Constants" />

<t:insertDefinition name="mlayoutDefault">

    <t:putAttribute name="titleAttr">고객 혜택</t:putAttribute>

    <t:putAttribute name="scriptHeaderAttr">
        <script type="text/javascript" src="/resources/js/appForm/dataFormConfig.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/vendor/swiper.min.js"></script>
        <script type="text/javascript" src="/resources/js/mobile/benefit/benefitMain.js?version=25.12.24"></script>
    </t:putAttribute>

    <t:putAttribute name="contentAttr">
        <input type="hidden" id="tab" value="${tab}">
        <input type="hidden" id="seq" value="${seq}">
        <input type="hidden" id="landingUrl" name="landingUrl">
        <input type="hidden" id="loginSoc" value="${loginSoc}">

        <div class="ly-content" id="main-content">
            <div class="ly-page-sticky">
                <h2 class="ly-page__head">고객 혜택<button class="header-clone__gnb"></button></h2>
            </div>

            <!-- 슬라이드 배너 시작 -->
            <div class="cust-bene-banner__swiper upper-banner__box u-mb--0" id="custBeneBanner">
                <div class="c-swiper swiper-container">
                    <ul class="swiper-wrapper">
                        <c:if test="${bannerList ne null and !empty bannerList}">
                            <c:forEach items="${bannerList}" var="banner">
                                <li class="swiper-slide" aria-controls="${banner.mobileLinkUrl}">
                                    <img src="${banner.mobileBannImgNm}" alt="${banner.imgDesc}">
                                </li>
                            </c:forEach>
                        </c:if>
                    </ul>
                    <button class="swiper-play-button stop" type="button"></button>
                    <div class="cust-bene-pager swiper-pagination"></div>
                </div>
            </div>
            <!-- 슬라이드 배너 끝 -->

            <div class="cust-bene-head--wrap">
                <div class="cust-bene-head">
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
                        <div class="cust-bene-calc__result" id="calcResult" style="display:none;">
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
                        <button class="c-button c-button--h48 c-button--full u-round--30 c-button--primary u-mt--16" id="priceCalculateBtn" onclick="calculatePrice()">계산하기</button>
                    </div>
                </div>
            </div>

            <div class="cust-bene-content">
                <div class="c-tabs c-tabs--type2 cust-bene" id="custBene-tab">
                    <div class="c-tabs__list" data-tab-list>
                        <button class="c-tabs__button" data-tab-header="#tabpanel1" aria-controls="tabpanel1" data-name="shopping"><p>M쇼핑할인</p><span>통신비 할인</span></button>
                        <button class="c-tabs__button" data-tab-header="#tabpanel2" aria-controls="tabpanel2" data-name="card"><p>제휴카드 할인</p><span>통신비 할인</span></button>
                        <button class="c-tabs__button" data-tab-header="#tabpanel3" aria-controls="tabpanel3" data-name="coupon"><p>M쿠폰</p><span>무료 쿠폰</span></button>
                        <button class="c-tabs__button" data-tab-header="#tabpanel4" aria-controls="tabpanel4" data-name="market"><p>M마켓</p><span>멤버십 쇼핑몰</span></button>
                    </div>
                    <div class="c-tabs__panel" id="tabpanel1">
                        ${mcashContent}
                        <span class="u-co-gray u-fs-15" style="position: absolute; z-index: 2; margin: 0.65rem 1rem 0 0;right: 0;bottom: -2.5rem;">조회 | ${viewCount.shopping}</span>
                    </div>
                    <div class="c-tabs__panel" id="tabpanel2">
                        <div class="cust-bene-card-content">
                            <div class="cust-bene-card-wrap-title-banner">
                                <img src="/resources/images/mobile/benefit/custbene_card_title_banner.png?v=20251125" alt="통신비 절약 제휴카드 자동이체, 최대 2만 5천원 청구할인! 카드만 잘 골라도 통신비 반값">
                            </div>
                            <div class="cust-bene-card-wrap u-mt--10">
                                <div class="swiper cust-bene-card__main-swiper" id="swiperCustBeneCardMain">
                                    <p class="cust-bene-card__title"><b>제휴카드로 알뜰하게 통신비 할인</b></p>
									<p class="cust-bene-card__title-desc">제휴카드 자동이체 신청으로 통신비 할인 받으세요! 전월 실적에 따른 통신비 할인 혜택이 제공됩니다.</p>
									<p class="cust-bene-card__subtitle">※ 카드 별 자세한 사용 금액 구간은 카드 이미지 선택 후 확인 부탁드립니다.</p>
			    					<p class="cust-bene-card__subtitle u-mt--6 u-mb--32">※ 카드 별 할인 금액의 경우, 프로모션 금액이 합쳐진 최대 금액이며 해당 금액의 경우, 카드사의 상황에 따라 변동될 수 있습니다.</p>
                                    <ul class="swiper-wrapper">
                                    </ul>
                                </div>
                                <div class="cust-bene-card__thumb-swiper-wrap">
                                    <div class="swiper cust-bene-card__thumb-swiper" id="swiperCustBeneCardThumb">
                                        <ul class="swiper-wrapper">
                                        </ul>
                                    </div>
                                </div>
                                <div class="cust-bene-card-detail" id="custBeneCardDetail" style="display: none;">
                                    <div class="c-button-wrap u-mt--0 u-pt--20">
                                        <a class="c-button c-button--full c-button--primary" href="" title="카드 혜택 자세히 보기 페이지 이동" id="cardApplyBtn">카드 혜택 자세히 보기</a>
                                        <button class="c-button c-button--lg c-button--gray" id="custBeneCardDetailClose" onclick="closeDetail()"><i class="c-icon c-icon--close" aria-hidden="true"></i></button>
                                    </div>
                                    <p class="cust-bene-card-detail__title"></p>
                                    <div class="card-benefit--box one-source cardDetailContent">
                                    </div>
                                </div>
                            </div>
                            <div class="cust-bene-card-wrap" id="custBeneCardNotice">
                            	<div class="cust-bene-card-wrap-guide">
									<p class="cust-bene-card__title">제휴카드, <span>자동납부</span>를 등록해야<br />청구할인 받을 수 있어요!</p>
									<ul class="cust-bene-card-guide">
										<li class="cust-bene-card-guide__item">
											<p class="cust-bene-card-guide__desc">케이티엠모바일 공식<br />홈페이지 로그인 후,<br />마이페이지 > 납부방법 변경 클릭</p>
											<div class="cust-bene-card-guide__img">
												<img src="/resources/images/mobile/benefit/custbene_card_guide1.png" alt="">
											</div>
										</li>
										<li class="cust-bene-card-guide__item">
											<p class="cust-bene-card-guide__desc">신용카드 자동납부 선택 후,<br />발급받은 제휴카드 번호<br />입력하면 완료!</p>
											<div class="cust-bene-card-guide__img">
												<img src="/resources/images/mobile/benefit/custbene_card_guide2.png" alt="">
											</div>
										</li>
									</ul>
								</div>
                                ${cardNotice}
                            </div>
                        </div>
                        <span class="u-co-gray u-fs-15" style="position: absolute; z-index: 2; margin: 0.65rem 1rem 0 0;right: 0;bottom: -2.5rem;">조회 | ${viewCount.card}</span>
                    </div>
                    <div class="c-tabs__panel" id="tabpanel3">
                        <div class="cust-bene-coupon-content">
                            <div class="cust-bene-coupon-wrap-title-banner">
                                <img src="/resources/images/mobile/benefit/custbene_coupon_title_banner.png" alt="브랜드 무료 쿠폰 M쿠폰, M모바일 고객님을 위한 무료 쿠폰 서비스입니다. 매월 다양한 브랜드의 무료할인 쿠폰을 받아 알뜰하게 사용해 보세요">
                            </div>
                            <div class="cust-bene-coupon-wrap u-mt--10">
                                <div class="cust-bene-coupon__title-wrap">
				            		<span class="cust-bene-coupon__total">총 <span></span> 개의 쿠폰</span>
				            		<p class="cust-bene-coupon__title"><span class="cust-bene-coupon__label cust-bene-coupon__label--new"><em></em>new</span><span class="cust-bene-coupon__label cust-bene-coupon__label--event"><em></em>event</span><span class="cust-bene-coupon__label cust-bene-coupon__label--hot"><em></em>hot</span></p>
				            	</div>
                                <div class="c-accordion c-accordion--type1">
                                    <ul class="c-accordion__box" id="couponListDiv">
                                    </ul>
                                </div>
                            </div>
                            <div class="c-notice-wrap">
                                <hr>
                                <h5 class="c-notice__title">
                                    <i class="c-icon c-icon--bang--black" aria-hidden="true"></i>
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
                        <span class="u-co-gray u-fs-15" style="position: absolute; z-index: 2; margin: 0.65rem 1rem 0 0;right: 0;bottom: -2.5rem;">조회 | ${viewCount.coupon}</span>
                    </div>
                    <div class="c-tabs__panel" id="tabpanel4">
                        <div class="cust-bene-mmarket-content">
                            <div class="cust-bene-mmarket-wrap u-pt--48">
                                <img src="/resources/images/mobile/benefit/custbene_mmarket_title_banner.png" alt="멤버십 쇼핑몰 M마켓, M모바일 고객님을 위한 멤버십 쇼핑몰입니다. 자급제는 기본 다양한 할인 쿠폰과 반값 핫딜에서 최저가 상품들을 만나보세요.">
                            </div>
                            <div class="cust-bene-mmarket-wrap u-pt--0">
                                <div class="cust-bene-mmarket-special">
                                    <div class="cust-bene-mmarket-title">
                                        <p>인터넷 최저가 보장! 매일 바뀌는 특가상품 </p>
                                    </div>
                                    <ul class="cust-bene-mmarket-list" id="marketItemList">
                                    </ul>
                                </div>
                            </div>
                            <div class="cust-bene-mmarket-wrap u-pr--0 u-pl--0">
                                <img src="/resources/images/mobile/benefit/custbene_mmarket_guide.png" alt="M마켓 가입방법 M모바일 가입고객이라면 약관 동의 후 쉽고 간편하게 가입완료 1.M마켓 가입 안내 페이지 이동 2.M마켓 약관 동의(최초 1회) 3. M마켓 회원가입 후 이용">
                            </div>
                            <div class="cust-bene-mmarket-wrap">
                                <div class="cust-bene-mmarket-benefit">
                                    <div class="cust-bene-mmarket-benefit-wrap">
                                        <div class="cust-bene-mmarket-benefit__info">
                                            <div class="cust-bene-mmarket-benefit__info-desc">
                                                <h4><span>M마켓</span> 쇼핑하고 혜택받기</h4>
                                                <div class="c-button-wrap">
                                                    <button type="button" class="c-button" onclick="goToMarket()">바로가기</button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="cust-bene-mmarket-benefit__img">
                                            <div class="cust-bene-mmarket-benefit__store">
                                                <div class="cust-bene-mmarket-benefit__store-item">
                                                    <div class="cust-bene-mmarket-benefit__store-img">
                                                        <img src="/resources/images/mobile/benefit/custbene_mmarket_qr1.png" alt="M마켓 이용을 위한 애플 앱스토어 다운받기 QR코드(http://etbs.me/QAY-I)">
                                                    </div>
                                                    <a class="cust-bene-mmarket-benefit__store-anchor	" href="http://etbs.me/QAY-I" target="_blank" title="M마켓 이용을 위한 애플 앱스토어 페이지 새창 열림">
                                                        <img src="/resources/images/mobile/benefit/custbene_mmarket_store1.png" alt="M마켓 이용을 위한 스토어 이미지">
                                                    </a>
                                                </div>
                                                <div class="cust-bene-mmarket-benefit__store-item">
                                                    <div class="cust-bene-mmarket-benefit__store-img">
                                                        <img src="/resources/images/mobile/benefit/custbene_mmarket_qr2.png" alt="M마켓 이용을 위한 구글플레이 다운받기 QR코드(http://etbs.me/QAY-A)">
                                                    </div>
                                                    <a class="cust-bene-mmarket-benefit__store-anchor	" href="http://etbs.me/QAY-A" target="_blank" title="M마켓 이용을 위한 구글플레이 페이지 새창 열림">
                                                        <img src="/resources/images/mobile/benefit/custbene_mmarket_store2.png" alt="M마켓 이용을 위한 스토어 이미지">
                                                    </a>
                                                </div>
                                                <div class="cust-bene-mmarket-benefit__store-item">
                                                    <div class="cust-bene-mmarket-benefit__store-img">
                                                        <img src="/resources/images/mobile/benefit/custbene_mmarket_qr3.png" alt="M마켓 이용을 위한 원스토어 다운받기 QR코드(http://etbs.me/QAY-O)">
                                                    </div>
                                                    <a class="cust-bene-mmarket-benefit__store-anchor	" href="http://etbs.me/QAY-O" target="_blank" title="M마켓 이용을 위한 원스토어 페이지 새창 열림">
                                                        <img src="/resources/images/mobile/benefit/custbene_mmarket_store3.png" alt="M마켓 이용을 위한 스토어 이미지">
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="c-notice-wrap">
                                <hr>
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
                        <span class="u-co-gray u-fs-15" style="position: absolute; z-index: 2; margin: 0.65rem 1rem 0 0;right: 0;bottom: -2.5rem;">조회 | ${viewCount.market}</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- M쇼핑할인 이용약관 팝업 -->
        <div id="McashTermsOfServiceAgree" class="mcashAgreePop"></div>

        <!-- 개인정보 수집 · 이용동의 팝업 -->
        <div id="McashPersonalInfoCollectAgree" class="mcashAgreePop"></div>

        <!-- 개인정보 제3자 제공동의 팝업 -->
        <div id="McashOthersTrnsAgree" class="mcashAgreePop"></div>

        <div id="MstoreTermsAgree"></div>
    </t:putAttribute>

</t:insertDefinition>