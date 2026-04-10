var coupangDiscountRate = 0;

var rateList = [];

var cardDiscountObj = {}; // 카드별 할인금액 목록을 저장한다.(PCT 제거, WON만 사용)

var pageObj = {
    eventId: ""
}

$(document).ready(function() {
    initBenefitBannerList();
    initMshoppingGuide();

    initRateList();
    initDiscountRateAndPaymentAmount();

    initCprtCardList();
    getCouponList();
    initMarketItemList();

    $(".c-tabs__button").on('click', function() {
        setTimeout(() => {
        	initCardSwiper();
		}, 100);
    })

    var tabName = $("#tab").val();
    var seq = $("#seq").val();
    setTimeout(function() {
        moveTabBy(tabName, seq);
        increaseTabViewCount($("#custBene-tab .c-tabs__button.is-active").data('name'));
        bindTabClickEvent();
    }, 200);

    initMstoreTermsAgree();

    var loginSoc = $("#loginSoc").val();
    if(loginSoc != ''){
        setTimeout(function() {
            $("#rateList").val(loginSoc);
        }, 200);
    }

});

function initRateList() {
    ajaxCommon.getItemNoLoading({
        id:'getCtgRateAllListAjax'
        ,cache:false
        ,url:'/rate/getRateAmountListAjax.do'
        ,dataType:"json"
    }, function (rateList) {
        $("#rateList").empty();
        $.each(rateList, function (index, rate) {
            var promotionAmtVatDesc = rate.promotionAmtVatDesc ? rate.promotionAmtVatDesc.replaceAll(",", "") : "";
            $("#rateList").append(`<option label="${rate.rateAdsvcNm}" value="${rate.rateAdsvcCd}" vat-amt="${promotionAmtVatDesc}">${rate.rateAdsvcNm}</option>`);
        });
    });
}

function initDiscountRateAndPaymentAmount() {
    ajaxCommon.getItemNoLoading({
        id:'getShopDiscountRateList'
        ,cache:false
        ,url:'/mcash/getShopDiscountRateListAjax.do'
        ,dataType:"json"
    }, function(shopDiscountRateList) {
        coupangDiscountRate = shopDiscountRateList.find(shop => shop.shopId === 'coupang').discountRate;
        $(".coupangDiscountRate").text(coupangDiscountRate);
        initMonthlyPaymentAmountList();
    });
}

function initMonthlyPaymentAmountList() {
    ajaxCommon.getItemNoLoading({
        id:'getMonthlyPaymentAmountList'
        ,cache:false
        ,url:'/benefit/getMonthlyPaymentAmountListAjax.do'
        ,dataType:"json"
    }, function(monthlyPaymentAmountList) {
        $("#monthlyPaymentAmount").empty();
        $.each(monthlyPaymentAmountList, function (index, item) {
            var discountAmount = (item.expnsnStrVal1 > 0) ? item.expnsnStrVal1.replaceAll(",", "") : Math.round(item.dtlCd * coupangDiscountRate / 100);
            $("#monthlyPaymentAmount").append(`<option value="${item.dtlCd}" label="${item.dtlCdNm}" dc-amt="${discountAmount}">${item.dtlCdNm}</option>`);
            if (item.expnsnStrVal1) {
                renderCoupangDiscountTable(item);
            }
        });
    });
}

function renderCoupangDiscountTable(item) {
    if (item.expnsnStrVal1 <= 0) {
        return;
    }

    $("#coupangDiscountTable tbody").append(`
        <tr>
            <td>${Number(item.dtlCd).toLocaleString() + '원'}</td>
            <td class="dc-price">${Number(item.expnsnStrVal1).toLocaleString() + '원'}</td>
        </tr>
    `);
}

function initCprtCardList() {
    bindCardDetailClickEvent();

    ajaxCommon.getItemNoLoading({
        id:'getCprtCardList'
        ,cache:false
        ,url:'/cprt/cprtCardXmlViewAjax.do'
        ,dataType:"json"
    }, function(cprtCardList) {
        var wonCprtCardList = filterWonCardList(cprtCardList);
        initCardDiscountList(wonCprtCardList);
        renderCalculatorCardList(wonCprtCardList);
        $("#swiperCustBeneCardMain .swiper-wrapper").append(wonCprtCardList.map(buildCprtCardMainSlide).join(''));
        $("#swiperCustBeneCardThumb .swiper-wrapper").append(wonCprtCardList.map(buildCprtCardThumbSlide).join(''));
        initCardSwiper();
    });
}

function filterWonCardList(cprtCardList) {
    $.each(cprtCardList, function (index, cprtCard) {
        var cardDiscountList = cprtCard.alliCardDcAmtDtoList;
        cprtCard.wonDiscountList = cardDiscountList.filter(discount => discount.dcFormlCd === "WON");
    });
    return cprtCardList.filter(cprtCard => cprtCard.wonDiscountList && cprtCard.wonDiscountList.length > 0);
}

function initCardDiscountList(cprtCardList) {
    cprtCardList.map(cprtCard => {
        cprtCard.wonDiscountList.map(wonDc => { wonDc.dcSectionStAmtWon = convertPriceToWon(wonDc.dcSectionStAmt)})
            .sort(function (a, b) {
                return a.dcSectionStAmt - b.dcSectionStAmt;
            });
        cardDiscountObj[cprtCard.cprtCardGdncSeq] = cprtCard.wonDiscountList;
    });
}

function renderCalculatorCardList(cprtCardList) {
    $("#cprtCardList").empty()
        .append(cprtCardList.map(cprtCard => `<option value="${cprtCard.cprtCardGdncSeq}" label="${cprtCard.cprtCardBasDesc}">${cprtCard.cprtCardBasDesc}</option>`).join(''))
        .trigger('change');
}

function buildCprtCardMainSlide(cprtCard) {
    return `
        <li class="swiper-slide" data-seq="${cprtCard.cprtCardGdncSeq}" data-ctgcd="${cprtCard.cprtCardCtgCd}">
            <p class="cust-bene-card__name cprtCardGdncNm">${cprtCard.cprtCardGdncNm}</p>
            <div class="cust-bene-card__img">
                <img src="${cprtCard.cprtCardLargeImgNm}" alt="${cprtCard.cprtCardGdncNm}">
            </div>
            <div class="cust-bene-card__fee">
                <p class="cust-bene-card__fee-title">연회비</p>
                <p class="cust-bene-card__fee-desc">${cprtCard.annualFeeItemDesc}</p>
            </div>
            <div class="cust-bene-card__benefit">
                ${cardDiscountObj[cprtCard.cprtCardGdncSeq].map(buildCardDiscountPrice).join('')}
            </div>
            <div class="c-button-wrap u-mt--15">
                <button class="c-button c-button--xsm card-detail-btn"><i class="c-icon c-icon--search-gray2" aria-hidden="true"></i>자세히 보기</button>
                <button class="c-button c-button--xsm" onclick="javascript:openPage('pullPopup', '/m/event/cprtCardBnfitLayer.do', '');">혜택비교</button>
                <button class="c-button c-button--xsm" onclick="javascript:openPage('pullPopup', '/m/event/cprtCardRegLayer.do?cprtCardGdncSeq=${cprtCard.cprtCardGdncSeq}', '');">이용가이드</button>
            </div>
        </li>
    `;
}

function buildCardDiscountPrice(cardDiscount) {
    return `
        <dl class="cust-bene-card__benefit-list">
            <dt class="cust-bene-card-tier__item">${cardDiscount.dcSectionStAmtWon + ' 이상'}</dt>
            <dd class="cust-bene-card__price">${Number(cardDiscount.dcAmt).toLocaleString() + '원 할인'} <span>(최대)</span></dd>
        </dl>
    `;
}

function buildCprtCardThumbSlide(cprtCard) {
    return `
        <li class="swiper-slide">
            <div class="cust-bene-card__img">
                <img src="${cprtCard.cprtCardLargeImgNm}" alt="${cprtCard.cprtCardGdncNm}">
            </div>
            <p class="cust-bene-card__name">${cprtCard.cprtCardGdncNm}</p>
        </li>
    `;
}

function loadCprtCardDetail(seq) {
    $("#custBeneCardDetail .cardDetailContent").empty();

    var varData = ajaxCommon.getSerializedData({
        cprtCardGdncSeq: seq
    });

    ajaxCommon.getItemNoLoading({
        id: 'getCprtCardGdncDtlXmlList'
        , cache: false
        , url: '/cprt/getCprtCardGdncDtlXmlListAjax.do'
        , data: varData
        , dataType: "json"
    }, function (cardDetails) {
        $("#custBeneCardDetail .cardDetailContent").append(buildCprtCardDetail(cardDetails));
    });

    ajaxCommon.getItemNoLoading({
        id: 'getCprtCardLinkXml'
        , cache: false
        , url: '/cprt/getCprtCardLinkXmlAjax.do'
        , data: varData
        , dataType: "json"
    }, function (cardLink) {
        $("#cardApplyBtn").attr('href', cardLink.linkUrlMo);
        if (cardLink.linkUrlTargetMo === "Y") {
            $("#cardApplyBtn").attr('target', '_blank');
        } else {
            $("#cardApplyBtn").removeAttr('target');
        }
    });
}

function buildCprtCardDetail(cardDetails) {
    return (cardDetails || [])
        .filter(detail => detail.cprtCardItemCd !== "PCARD205" && detail.cprtCardItemCd !== "PCARD206" && detail.cprtCardItemCd !== "PCARD207")
        .map(detail => {
            return `
                <h4 class="c-heading c-title--type3">${detail.cprtCardItemCd === 'PCARD299' ? detail.cprtCardItemNm : detail.cprtCardItemCdNm}</h4>
                <ul class="c-text-list c-bullet c-bullet--dot">
                    ${detail.cprtCardItemSbst}
                </ul>
            `;
        }).join('');
}

function getCouponList() {
    var varData = ajaxCommon.getSerializedData({
        pageNo: 1,
        recordCount: 999
    });

    ajaxCommon.getItemNoLoading({
        id: 'getBenefitCouponList'
        , cache: false
        , url: '/coupon/getBenefitCouponListPagedAjax.do'
        , async: false
        , data: varData
        , dataType: "json"
    }, function (result) {
        if (!result.couponList || result.couponList.length === 0) {
            return;
        }

        $(".cust-bene-coupon__total span").text(result.couponTotalCount);

        $("#couponListDiv").append(
            result.couponList.map((coupon, index) => {
                return buildCouponItem(coupon, index);
            }).join('')
        );
    });
}

function buildCouponItem(coupon, index) {
    //쿠폰 라벨
    const labelList = coupon.couponLabel ? coupon.couponLabel.split(',') : [];

    const labelHtml = labelList
        .map(label => {
            const clsMap = { N: '--new', H: '--hot', E: '--event' };
            return `<span class="cust-bene-coupon__label cust-bene-coupon__label${clsMap[label] || ''}"><em></em></span>`;
        })
        .join('');

    return `
        <li class="c-accordion__item" data-seq="${coupon.coupnCtgCd}">
            <div class="c-accordion__head" data-acc-header="#custBeneCouponAcc${index + 1}">
                <div class="c-accordion__image">
                    <img src="${coupon.thumbImgMo}" alt="${coupon.imgDesc}">
                </div>
                <button class="c-accordion__button" type="button">
                    <div class="cust-bene-coupon__label-wrap">
                        ${labelHtml}
                    </div>
                    ${coupon.coupnOutsideVendrNm}
                    <p class="c-accordion__text">${coupon.benefitDesc}</p>
                </button>
            </div>
            <!--  custBeneCouponAcc1 넘버링 -->
            <div class="c-accordion__panel expand" id="custBeneCouponAcc${index + 1}">
                <div class="c-accordion__inside">
                    <ul class="cust-bene-coupon-detail-list">
                        <li class="cust-bene-coupon-detail-item">
                            <div class="cust-bene-coupon-detail__title">혜택안내</div>
                            <div class="cust-bene-coupon-detail__desc">${coupon.benefitDescDtl}</div>
                        </li>
                        <li class="cust-bene-coupon-detail-item">
                            <div class="cust-bene-coupon-detail__title">이용방법</div>
                            <div class="cust-bene-coupon-detail__desc">${coupon.usageWay}</div>
                        </li>
                        <li class="cust-bene-coupon-detail-item">
                            <div class="cust-bene-coupon-detail__title">이용안내</div>
                            <div class="cust-bene-coupon-detail__desc">${coupon.usageDesc}</div>
                        </li>
                        <li class="cust-bene-coupon-detail-item">
                            <div class="cust-bene-coupon-detail__title">문의</div>
                            <div class="cust-bene-coupon-detail__desc">${coupon.contact}</div>
                        </li>
                    </ul>
                    <div class="c-button-wrap u-mt--16">
                        <button type="button" class="c-button c-button--green" id="btnDownloadCoupon${coupon.coupnCtgCd}" onclick="downloadCoupon('${coupon.coupnCtgCd}')">쿠폰받기</button>
                    </div>
                </div>
            </div>
        </li>
    `;
}

function downloadCoupon(seq){
    var coupnCtgCdList = [];
    coupnCtgCdList.push(seq);

    //세션에 저장되어 있는 로그인 정보(이름, 생년월일) 가져오기
    ajaxCommon.getItem({
        id: "checkLogin"
        , cache: false
        , url: "/coupon/checkLogin.do"
        , data: ""
        , dataType: "json"
        , async: false
    }, function (data) {
        if (data.isLogin === false) {
            KTM.Confirm('M모바일을 사용하시는<br>회원 전용 서비스 입니다.<br><br>로그인 후 이용 하시겠습니까?', function () {
                goToLogin();
            });
        } else {
            var parameterData = ajaxCommon.getSerializedData({
                coupnCtgCdList: coupnCtgCdList
            });
            openPage('pullPopup', '/m/coupon/couponDownPop.do', parameterData, '1');
        }
    });
}

function initMarketItemList() {
    bindMarketItemClickEvent();
    getMarketContentItemList();
}

function getMarketContentItemList() {
    ajaxCommon.getItemNoLoading({
        id: "getMstoreContents"
        , cache: false
        , url: "/point/getMstoreContentsAjax.do"
        , dataType: "json"
    }, function (contents) {
        var productContents = contents.filter(content => content.itemType === "PRODUCT");
        $("#marketItemList").append(productContents.map(buildTodaySalesProduct).join(''));
    });
}

function buildTodaySalesProduct(product) {
    return `
        <li class="cust-bene-mmarket-item" data-url="${product.url}">
            <div class="cust-bene-mmarket__img">
                <img src="${product.imgUrl}" alt="${product.name}">
            </div>
            <p class="cust-bene-mmarket__name"><span>${product.name}</span></p>
            <p class="cust-bene-mmarket__price">${Number(product.originalPrice).toLocaleString() + '원'}</p>
            <p class="cust-bene-mmarket__disc-price"><span>${Number(product.discountPrice).toLocaleString()}</span><em>원</em></p>
        </li>
    `;
}

function convertPriceToWon(price) {
    if (price < 10000) {
        return price.toLocaleString() + '원';
    }

    if (price % 10000 === 0) {
        return (price / 10000) + '만원';
    } else if (price % 1000 === 0) {
        return (price / 10000).toFixed(1) + '만원';
    } else {
        return price.toLocaleString() + '원';
    }
}

function handleCardChange(card) {
    var cprtCardGdncSeq = $(card).val();
    var cardUsageAmountList = cardDiscountObj[cprtCardGdncSeq];

    $("#cardUsageAmount").empty();
    $.each(cardUsageAmountList, function (index, item) {
        $("#cardUsageAmount").append(`<option value="${item.dcSectionStAmt}" label="${item.dcSectionStAmtWon}" dc-amt="${item.dcAmt}">${item.dcSectionStAmtWon}</option>`);
    });
}

function calculatePrice() {
    $("#priceCalculateBtn").text("다시 계산하기");
    $('#calcResult').stop().slideDown(200);

    var rateVatAmt = Number($("#rateList option:selected").attr('vat-amt'));
    $("#ratePrice").text(rateVatAmt.toLocaleString());

    var mcashDiscountAmount = Number($("#monthlyPaymentAmount option:selected").attr('dc-amt'));
    $("#mcashDiscountAmount").text((-mcashDiscountAmount).toLocaleString());

    var cardDiscountAmount = Number($("#cardUsageAmount option:selected").attr('dc-amt'));
    $("#cardDiscountAmount").text((-cardDiscountAmount).toLocaleString());

    var totalPrice = rateVatAmt - mcashDiscountAmount - cardDiscountAmount;
    totalPrice = totalPrice < 0 ? 0 : totalPrice;
    $("#totalPrice").text(totalPrice.toLocaleString());

    var totalDiscountRate = Math.round((rateVatAmt - totalPrice) / rateVatAmt * 100);
    $("#totalDiscountRate").text(totalDiscountRate ? totalDiscountRate : 100);
}

function checkMcashUse() {
    ajaxCommon.getItem({
        id:'checkMcashUse'
        ,cache:false
        ,url:'/mcash/checkMcashUseAjax.do'
        ,dataType:"json"
    }, function(result) {
        if (result.resultMsg === "LOGIN") {
            MCP.alert("로그인 후 이용 가능합니다.", function() {
                goToLogin();
            });
        } else if (result.resultMsg === "GRADE") {
            MCP.alert("정회원 인증 후 이용 가능합니다.", function() {
                location.href = "/m/mypage/multiPhoneLine.do";
            });
        } else if (result.resultMsg === "JOIN") {
            if (result.resultCd === "0000") {
                location.href = "/m/mcash/mcashMain.do";
            } else if (result.resultCd === "0001" || result.resultCd === "0002") {
                openMcashJoinPop();
            } else if (result.resultCd === "0003") {
                MCP.alert("서비스 해지 당일에는<br>재가입하실 수 없습니다.");
            } else if (result.resultCd === "0004") {
                MCP.alert("비정상적인 회원 정보입니다.<br>고객센터에 문의바랍니다.");
            }
        }
    });
}

// 약관 팝업 클릭 시 eventId 세팅
function mcashSetEventId(eventId) {
    pageObj.eventId = eventId;
}

// 동의 후 닫기
function targetTermsAgree() {
    $('#' + pageObj.eventId).prop('checked', true);

    chkAgree();
}

function openMcashJoinPop() {
    if (!isServiceAvailable("MCASH")) {
        return;
    }
    openPage('pullPopup', '/m/mcash/mcashJoinPop.do');
}

function isServiceAvailable(serviceName) {
    var isAvailable = false;
    var varData = ajaxCommon.getSerializedData({
        serviceName : serviceName
    });
    ajaxCommon.getItemNoLoading({
            id:'checkServiceAvailable'
            ,cache:false
            ,async:false
            ,url:'/checkServiceAvailable.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
                isAvailable = true;
            } else {
                MCP.alert(jsonObj.RESULT_MSG);
            }
        });

    return isAvailable;
}

function initBenefitBannerList() {
    if ($("#custBeneBanner .swiper-wrapper > li").length) {
        // 상단 배너 슬라이드
        var swiper = new Swiper('#custBeneBanner .swiper-container', {
            loop: true,
            pagination: {
                el: '.swiper-pagination',
                type: 'fraction',
            },
            autoplay: {
                delay: 3000,
                disableOnInteraction: false,
            },
            on: {
                init: function() {
                    var swiper = this;
                    var autoPlayButton = this.el.querySelector('.swiper-play-button');
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
                },
            },
        });

        // 상단 배너 슬라이드 클릭 시 탭 선택
        const slides = document.querySelectorAll('#custBeneBanner .swiper-slide');
        const tabButtons = document.querySelectorAll('.c-tabs__button');
        const tabSection = document.querySelector('#custBene-tab');

        if (!slides.length || !tabButtons.length) return;

        slides.forEach(slide => {
            slide.addEventListener('click', () => {
                let targetPanel = slide.getAttribute('aria-controls');
                if (!targetPanel) return;

                const targetButton = Array.from(tabButtons).find(btn => {
                    const btnPanel = btn.getAttribute('aria-controls');
                    return btnPanel && btnPanel.replace('#', '') === targetPanel;
                });

                targetButton.click();
                targetButton.focus();

                tabButtons.forEach(btn => btn.classList.remove('is-active'));
                targetButton.classList.add('is-active');

                if (tabSection) {
                    const offsetTop = tabSection.getBoundingClientRect().top + window.scrollY;
                    window.scrollTo({
                        top: offsetTop - 86,
                        behavior: 'smooth'
                    });
                }
            });
        });
        $("#custBeneBanner").show();
    } else {
        $("#custBeneBanner").hide();
    }
}

function initMshoppingGuide() {
    if ($("#mshoppingGuide .swiper-slide")) {
        var mshoppingGuideSwiper = new Swiper('#mshoppingGuide .swiper-container', {
            observer: true,
            observerParents: true,
            pagination : {
                el : '#mshoppingGuide .swiper-pagination',
                type: 'fraction',
            },
        });
    }
}

function initCardSwiper() {
    // 제휴카드 썸네일 슬라이드
    const thumbSlides = Array.from(document.querySelectorAll('#swiperCustBeneCardThumb .swiper-slide'));
    const slidesPerView = 4;
    const thumbCount = thumbSlides.length;
    const isThumbSwipable = thumbCount > slidesPerView;
    const thumbBtns = document.querySelectorAll('.cust-bene-card__thumb-swiper-wrap .swiper-button-next, .cust-bene-card__thumb-swiper-wrap .swiper-button-prev');

    const thumbSwiper = new Swiper('#swiperCustBeneCardThumb', {
        spaceBetween: 8,
        slidesPerView: 'auto',
        freeMode: {
            enabled: true,
            momentum: false
        },
        watchSlidesProgress: true,
        allowTouchMove: isThumbSwipable,
        slideToClickedSlide: false,
        observer: true,
        observerParents: true,
        navigation: {
            nextEl: '.cust-bene-card__thumb-swiper-wrap .swiper-button-next',
            prevEl: '.cust-bene-card__thumb-swiper-wrap .swiper-button-prev',
        },
    });

    // 슬라이드 개수에 따라 버튼 표시/숨김
    if (isThumbSwipable) {
        thumbBtns.forEach(btn => btn.style.display = 'block');
    } else {
        thumbBtns.forEach(btn => btn.style.display = 'none');
    }

    // 제휴카드 메인 슬라이드
    const mainSwiper = new Swiper('#swiperCustBeneCardMain', {
        spaceBetween: 20,
        thumbs: {
            swiper: thumbSwiper,
        },
        observer: true,
        observerParents: true
    });

    mainSwiper.on('touchEnd', () => {
        const last = mainSwiper.slides.length - 1;

        // 마지막인데 index가 튀었으면 보정
        if (mainSwiper.activeIndex > last) {
            mainSwiper.slideTo(last, 0);
        }
    });

    setTimeout(() => syncSelectClass(mainSwiper.activeIndex || 0), 0);

    mainSwiper.on('slideChange', () => {
        const index = mainSwiper.activeIndex;

        syncSelectClass(index);

        // 이미 자세히 보기 상태라면 닫기 버튼처럼 처리
        if ($("#custBeneCardDetail").is(":visible")) {
            closeDetail();
        }

        // 썸네일 클릭
        const currentThumb = document.querySelectorAll('#swiperCustBeneCardThumb .swiper-slide')[index];
        if (currentThumb) {
            currentThumb.click();
        }

    });

    // 썸네일 클릭 시 슬라이드 위치 이동
    thumbSlides.forEach((slide, index) => {
        slide.addEventListener('click', () => {

            // 이미 자세히 보기 상태라면 닫기 버튼처럼 처리
            if ($("#custBeneCardDetail").is(":visible")) {
                closeDetail();
            }

            mainSwiper.slideTo(index);
            syncSelectClass(index);

            if (!isThumbSwipable) return;

            const maxIndex = thumbSlides.length - slidesPerView;
            let targetIndex = index - Math.floor(slidesPerView / 2);
            if (targetIndex < 0) targetIndex = 0;
            if (targetIndex > maxIndex) targetIndex = maxIndex;
            thumbSwiper.slideTo(targetIndex);
        });
    });
}

function syncSelectClass(index) {
    const thumbSlides = Array.from(document.querySelectorAll('#swiperCustBeneCardThumb .swiper-slide'));
    const mainSlides = Array.from(document.querySelectorAll('#swiperCustBeneCardMain .swiper-slide'));

    // 썸네일, 메인에 is-select
    thumbSlides.forEach((s, i) => s.classList.toggle('is-select', i === index));
    mainSlides.forEach((s, i) => s.classList.toggle('is-select', i === index));
}

function closeDetail() {
    const $detail = $("#custBeneCardDetail");
    const $btnWrap = $detail.find(".c-button-wrap");

    $("#custBeneCardNotice").show();
    $detail.stop(true, true).slideUp(500);

    $("#swiperCustBeneCardThumb .swiper-slide").removeClass("is-active is-deactive");
}

function bindCardDetailClickEvent() {
    $("#swiperCustBeneCardMain").on("click", ".card-detail-btn", function(e) {
        e.preventDefault();
        const $slide = $(this).closest(".swiper-slide");
        const index = $slide.index();
        const $detail = $("#custBeneCardDetail");
        const $btnWrap = $detail.find(".c-button-wrap");

        $("#custBeneCardNotice").hide();

        $("#custBeneCardDetail .cust-bene-card-detail__title").text($slide.find('.cprtCardGdncNm').text());
        loadCprtCardDetail($slide.data('seq'));

        const thumbs = $("#swiperCustBeneCardThumb .swiper-slide");
        thumbs.removeClass("is-active is-deactive");
        thumbs.eq(index).addClass("is-active");
        thumbs.not(thumbs.eq(index)).addClass("is-deactive");

        $detail.stop(true, true).slideDown(200);
    });
}

function bindMarketItemClickEvent() {
    $("#marketItemList").on('click', '.cust-bene-mmarket-item', function (e) {
        goToMarket($(this).data('url'));
    });
}

function moveTabBy(name, seq) {
    if (name === "shopping") {
        moveTab(1);
    } else if (name === "card") {
        moveTab(2);
        if (seq) {
            selectCardBySeq(seq);
        }
    } else if (name === "coupon") {
        moveTab(3);
    } else if (name === "market") {
        moveTab(4);
    }
}

function moveTab(num) {
    const tabButtons = document.querySelectorAll('.c-tabs__button');
    const tabSection = document.querySelector('#custBene-tab');

    const targetButton = Array.from(tabButtons).find(btn => {
        const btnPanel = btn.getAttribute('aria-controls');
        return btnPanel && btnPanel.replace('#', '') === `tabpanel${num}`;
    });

    targetButton.click();
    targetButton.focus();

    tabButtons.forEach(btn => btn.classList.remove('is-active'));
    targetButton.classList.add('is-active');

    if (tabSection) {
        const offsetTop = tabSection.getBoundingClientRect().top + window.scrollY;
        window.scrollTo({
            top: offsetTop - 86,
            behavior: 'smooth'
        });
    }
}

function selectCardBySeq(cprtCardGdncSeq) {
    $("#swiperCustBeneCardMain .swiper-slide").each(function (index, slide) {
        if ($(slide).data('seq') == cprtCardGdncSeq) {
            $("#swiperCustBeneCardThumb .swiper-slide")[index].click();
            return false;
        }
    });
}

function selectCardByCtgCd(cprtCardCtgCd) {
    $("#swiperCustBeneCardMain .swiper-slide").each(function (index, slide) {
        if ($(slide).data('ctgcd') == cprtCardCtgCd) {
            $("#swiperCustBeneCardThumb .swiper-slide")[index].click();
            return false;
        }
    });
}

function goToMarket(landingUrl) {
    $("#landingUrl").val(landingUrl);

    // Mstore 동의 여부 확인
    ajaxCommon.getItem({
            id: 'checkMstoreUse'
            ,cache: false
            ,url: '/point/checkMstoreUseAjax.do'
            ,dataType: "json"
            ,async: false
        }
        , function (jsonObj) {
            if (jsonObj.resultCd === '0001' && jsonObj.errorCause === 'LOGIN') { //로그인 세션 빠짐 > 로그인 페이지 이동
                MCP.alert(jsonObj.resultMessage, function (){
                    goToLogin();
                });
            } else if (jsonObj.resultCd === '0001' && jsonObj.errorCause === 'GRADE') { // 정회원 인증 필요
                MCP.alert(jsonObj.resultMessage, function (){
                    location.href = "/m/mypage/multiPhoneLine.do"
                });
            } else if (jsonObj.resultCd === '0003' && jsonObj.errorCause === 'PARAM') { // 본인인증 필업
                MCP.alert(jsonObj.resultMessage, function () {
                    if (jsonObj.isRedirect === "Y") {
                        openMarketAuthPop();
                    } else {
                        goToMarketIntro();
                    }
                });
            } else if (jsonObj.resultCd === '0001' && jsonObj.errorCause === 'TERMS') { // 약관 동의 필요
                MCP.alert(jsonObj.resultMessage, function () {
                    if (jsonObj.isRedirect === "Y") {
                        openMstoreTermsAgreePop();
                    } else {
                        goToMarketIntro();
                    }
                });
            } else if (jsonObj.resultCd === '0000') {
                openExternalMarket();
            } else {
                MCP.alert(jsonObj.resultMessage || '서비스 처리 중 오류가 발생하였습니다.<br/>고객센터(114/무료)로 문의 부탁드립니다.');
            }
        });
}

function goToLogin() {
    location.href = "/m/loginForm.do";
}

function goToMarketIntro() {
    var landingUrl = $("#landingUrl").val();
    location.href = "/m/point/mstoreView.do" + (landingUrl ? `?landingUrl= + ${encodeURIComponent(landingUrl)}` : "");
}

function openMarketAuthPop() {
    openPage('pullPopup', '/m/point/mstoreAuthPop.do');
}

function openMstoreTermsAgreePop() {
    var el = document.querySelector('#mstoreTermsAgreePop');
    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
    modal.open();
}

function openExternalMarket() {
    var landingUrl = $("#landingUrl").val();
    window.open("/m/point/mstoreAuth.do" + (landingUrl ? `?landingUrl= + ${encodeURIComponent(landingUrl)}` : ""), "_blank");
}

function increaseTabViewCount(tabName) {
    var varData = ajaxCommon.getSerializedData({
        tabName: tabName
    });

    ajaxCommon.getItemNoLoading({
        id: 'increaseTabViewCount'
        ,cache: false
        ,url: '/benefit/increaseTabViewCountAjax.do'
        ,data: varData
        ,dataType: "json"
    }, function (jsonObj) {});
}

function bindTabClickEvent() {
    $("#custBene-tab").on('click', '.c-tabs__button', function (e) {
        var tabName = $(this).data('name');
        increaseTabViewCount(tabName);

        var params = new URLSearchParams(window.location.search);
        params.set('tab', tabName);
        params.delete('seq');
        history.replaceState({}, null, '?' + params.toString());
    });
}

function initCprtCard(cprtCardCtgCd) {
    var selectedCard = null;

    // 선택된 카드 메뉴 클릭
    selectCardByCtgCd(cprtCardCtgCd);

    // Layer 닫기
    var modalEl = $("#modalArs")[0];
    modalEl.addEventListener(KTM.Dialog.EVENT.CLOSED, function closed() {
        modalEl.removeEventListener(KTM.Dialog.EVENT.CLOSED, closed);
        setTimeout(function() {
            if(selectedCard) {
                selectedCard.focus();
            }
        }, 400);
    });
    $("#modalArs button.c-button").click();
}

// 새창 링크
function pageLink(page){
    var win = window.open(page);
    win.onbeforeunload = function(){
    }
}

function initMstoreTermsAgree() {
    ajaxCommon.getItem({
            id: 'mstoreTermsPop',
            url: "/termsPop.do",
            type: "GET",
            dataType: "json",
            data: "cdGroupId1=FormEtcCla&cdGroupId2=MstoreTermsAgree2"
        }
        ,function(data){
            var inHtml = unescapeHtml(data.docContent);
            $('#MstoreTermsAgree').html(inHtml);
        });
}

function mstoreAgreeChk($trg) {
    if($("#"+$trg.id).is(":checked")) $("#mstoreAgreeSend").prop("disabled", false);
    else $("#mstoreAgreeSend").prop("disabled", true);
}

// 동의하고 계속하기 버튼 클릭 이벤트 부여
function mstoreAgreeSend(){
    var agreeYn= $("#mstoreAgreeChk").is(':checked') ? "Y" : "N";
    if(agreeYn != "Y"){
        MCP.alert("M마켓 이용 안내에 동의하셔야 합니다.");
        return;
    }

    var varData = ajaxCommon.getSerializedData({agreeYn: agreeYn});

    // 약관 동의 처리
    ajaxCommon.getItem({
        id: 'mstoreTermsAgreeAjax'
        ,cache: false
        ,url: '/point/mstoreTermsAgreeAjax.do'
        ,data : varData
        ,dataType: "json"
        ,async: false
    }, function (jsonObj) {

        if (jsonObj.RESULT_CODE=='0001') { //로그인 세션 빠짐 > 로그인 페이지 이동
            MCP.alert(jsonObj.RESULT_MSG, function (){
                location.href= "/m/loginForm.do";
            });
        } else if (jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS) {
            MCP.alert(jsonObj.RESULT_MSG);
        } else {
            // 팝업 닫고 Mstore 이동
            KTM.Dialog.closeAll();
            openExternalMarket();
        }
    });
}


//이번달 쿠폰을 받은 경우 M쿠폰 받기 비활성화
$("#couponListDiv").on("click", ".c-accordion__button", function () {
    let seq = $(this).closest(".c-accordion__item").data("seq");
    var varData = ajaxCommon.getSerializedData({
        coupnCtgCd : seq
    });

    ajaxCommon.getItemNoLoading({
        id:'benefitCouponCheckAjax'
        ,cache:false
        ,url:'/coupon/benefitCouponCheckAjax.do'
        ,data:varData
        ,dataType:"json"
    }, function(data) {
        if(data.useYn == 'Y'){
            $('#btnDownloadCoupon'+seq).prop('disabled', true);
            $('#btnDownloadCoupon'+seq).addClass('is-disabled');
        }else{
            $('#btnDownloadCoupon'+seq).prop('disabled', false);
            $('#btnDownloadCoupon'+seq).removeClass('is-disabled');
        }
    });
});