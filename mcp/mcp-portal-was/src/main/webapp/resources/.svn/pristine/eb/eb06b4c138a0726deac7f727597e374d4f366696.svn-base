var coupangDiscountRate = 0;
var couponPageInfo = {
    pageNo: 1,
    ITEM_PER_ROW: 3,
    RECORD_COUNT: 6,
    totalPageCount: null
}

var rateList = [];

var cardDiscountObj = {}; // 카드별 할인금액 목록을 저장한다.(PCT 제거, WON만 사용)
var cardAmountTiers = [];

$(document).ready(function () {
    initBenefitBannerList();
    setTimeout(function() {
        initMshoppingGuide();
    }, 0);

    initRateList();
    initDiscountRateAndPaymentAmount();

    initCprtCardList();
    initCouponList();
    initMarketItemList();

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
            renderCoupangDiscountTable(item);
        });
    });
}

function renderCoupangDiscountTable(item) {
    if (item.expnsnStrVal1 <= 0) {
        return;
    }

    $("#coupangDiscountTable colgroup").append('<col>');
    $("#coupangDiscountTable tbody > tr:eq(0)").append(`<td>${Number(item.dtlCd).toLocaleString() + '원'}</td>`);
    $("#coupangDiscountTable tbody > tr:eq(1)").append(`<td>${Number(item.expnsnStrVal1).toLocaleString() + '원'}</td>`);
}

function initCprtCardList() {
    bindCardToggleEvent();

    ajaxCommon.getItemNoLoading({
        id:'getCprtCardList'
        ,cache:false
        ,url:'/cprt/cprtCardXmlViewAjax.do'
        ,dataType:"json"
    }, function(cprtCardList) {
        var wonCprtCardList = filterWonCardList(cprtCardList);
        initCardDiscountList(wonCprtCardList);
        initCardAmountTiers(wonCprtCardList);
        renderCalculatorCardList(wonCprtCardList);
        $("#swiperCustBeneCard .swiper-wrapper").append(wonCprtCardList.map(buildCprtCardSlide).join(''));
        $("#cardTierList").append(cardAmountTiers.map(buildCardTier).join('') + `<p class="cust-bene-card__fee-title">연회비</p>`);
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

function initCardAmountTiers(cprtCardList) {
    var cardDiscountSet = new Set();
    cprtCardList.map(cprtCard =>
        cprtCard.wonDiscountList.map(wonDc =>
            cardDiscountSet.add(Number(wonDc.dcSectionStAmt))));
    cardAmountTiers = [...cardDiscountSet].sort((a, b) => a - b);
}

function renderCalculatorCardList(cprtCardList) {
    $("#cprtCardList").empty()
        .append(cprtCardList.map(cprtCard => `<option value="${cprtCard.cprtCardGdncSeq}" label="${cprtCard.cprtCardBasDesc}">${cprtCard.cprtCardBasDesc}</option>`).join(''))
        .trigger('change');
}

function buildCprtCardSlide(cprtCard) {
    return `
        <li class="swiper-slide" role="button" tabindex="0" aria-expanded="false" data-seq="${cprtCard.cprtCardGdncSeq}">
            <div class="cust-bene-card__img">
                <img src="${cprtCard.cprtCardLargeImgNm}" alt="${cprtCard.cprtCardGdncNm}">
            </div>
            <div class="cust-bene-card__benefit">
                <p class="cust-bene-card__name cprtCardGdncNm">${cprtCard.cprtCardGdncNm}</p>
                ${cardAmountTiers.map(cardAmountTier => buildCardDiscountPrice(cardDiscountObj[cprtCard.cprtCardGdncSeq], cardAmountTier)).join('')}
                <p class="cust-bene-card__fee-desc">${cprtCard.annualFeeItemDesc}</p>
            </div>
        </li>
    `;
}

function buildCardDiscountPrice(cardDiscountList, cardAmountTier) {
    var matchedDiscount = findDiscountAmountByTier(cardDiscountList, cardAmountTier);
    if (matchedDiscount) {
        return `<p class="cust-bene-card__price">${Number(matchedDiscount).toLocaleString() + '원 할인'}<span>(최대)</span></p>`;
    } else {
        return `<p class="cust-bene-card__price">-</p>`;
    }
}

function findDiscountAmountByTier(cardDiscountList, cardAmountTier) {
    var matchedDiscount;
    $.each(cardDiscountList, function (index, item) {
        if (item.dcSectionStAmt <= cardAmountTier && cardAmountTier <= item.dcSectionEndAmt) {
            matchedDiscount = item.dcAmt;
            return false;
        }
    });
    return matchedDiscount;
}

function buildCardTier(tier) {
    return `<p class="cust-bene-card-tier__item">${convertPriceToWon(tier) + ' 이상'}</p>`;
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
        $("#cardApplyBtn").attr('href', cardLink.linkUrl);
        if (cardLink.linkUrlTarget === "Y") {
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
                ${detail.cprtCardItemSbst}
            `;
        }).join('');
}

function initCouponList() {
    bindCouponToggleEvent();
    getCouponList();
    getMyCouponList();
}

function bindCouponToggleEvent() {
    // M쿠폰 리스트 상세 보기
    let $openDetail = null;
    $('.cust-bene-coupon-list').on('click keydown', '.cust-bene-coupon-item', function(e) {
        if (e.type === 'click' || e.key === 'Enter' || e.key === ' ') {
            e.preventDefault();

            const isKeyboard = e.type !== 'click';
            const $thisContainer = $(this).closest('.cust-bene-coupon-list-group');
            const $thisDetail = $thisContainer.find('.cust-bene-coupon-detail');
            const index = $(this).closest('.cust-bene-coupon-item').index();
            const isSame = $openDetail && $openDetail.is($thisDetail) && $openDetail.data('activeIndex') === index;

            if ($openDetail) {
                $openDetail.stop(true, true).slideUp(300).attr('aria-hidden', 'true');
                $openDetail.closest('.cust-bene-coupon-list-group').find('.cust-bene-coupon-item').removeClass('is-active')	.attr('aria-expanded', 'false');
                $openDetail = null;
            }

            if (isSame) return;

            $thisDetail.stop(true, true).slideDown(300).data('activeIndex', index).attr('aria-hidden', 'false');
            $openDetail = $thisDetail;

            loadCouponDetail($thisDetail, $(this).data('seq'));

            $thisContainer.find('.cust-bene-coupon-item').removeClass('is-active').attr('aria-expanded', 'false');
            $(this).closest('.cust-bene-coupon-item').addClass('is-active').attr('aria-expanded', 'true');

            if (isKeyboard) {
                $thisDetail.find('.cust-bene-coupon-detail__close').focus();
            }
        }
    });
    // M쿠폰 리스트 상세 닫기
    $('.cust-bene-coupon-list').on('click keydown', '.cust-bene-coupon-detail__close', function(e){
        if (e.type === 'click' || e.key === 'Enter' || e.key === ' ') {
            e.preventDefault();

            const $thisDetail = $(this).closest('.cust-bene-coupon-detail');
            const $thisGroup = $(this).closest('.cust-bene-coupon-list-group');
            const $parentItems = $thisGroup.find('.cust-bene-coupon-item');

            $thisDetail.stop(true, true).slideUp(300).attr('aria-hidden', 'true');
            $openDetail = null;
            $parentItems.removeClass('is-active').attr('aria-expanded', 'false');

            if (e.type !== 'click') {
                $parentItems.eq($thisDetail.data('activeIndex')).focus();
            }
        }
    });
}

function loadCouponDetail(couponDetail, seq) {
    var varData = ajaxCommon.getSerializedData({
        coupnCtgCd : seq
    });

    ajaxCommon.getItemNoLoading({
        id:'getBenefitCouponDetail'
        ,cache:false
        ,url:'/coupon/getBenefitCouponDetailAjax.do'
        ,data:varData
        ,dataType:"json"
    }, function(data) {
        couponDetail.data('seq', seq);
        couponDetail.find('.benefitDescDtl').html(data.couponDetail.benefitDescDtl);
        couponDetail.find('.usageWay').html(data.couponDetail.usageWay);
        couponDetail.find('.usageDesc').html(data.couponDetail.usageDesc);
        couponDetail.find('.contact').html(data.couponDetail.contact);
    });
}


function getCouponList() {
    if (couponPageInfo.totalPageCount && couponPageInfo.pageNo > couponPageInfo.totalPageCount) {
        return;
    }

    var varData = ajaxCommon.getSerializedData({
        pageNo: couponPageInfo.pageNo,
        recordCount: couponPageInfo.RECORD_COUNT
    });

    ajaxCommon.getItemNoLoading({
        id: 'getBenefitCouponList'
        , cache: false
        , url: '/coupon/getBenefitCouponListPagedAjax.do'
        , data: varData
        , dataType: "json"
    }, function (result) {
        couponPageInfo.totalPageCount = result.totalPageCount;
        if (!result.couponList || result.couponList.length === 0) {
            $("#couponMoreBtn").parent().remove();
            couponPageInfo.pageNo++;
            return;
        }

        $(".cust-bene-coupon__total span").text(result.couponTotalCount);

        var groupList = groupBySize(result.couponList, couponPageInfo.ITEM_PER_ROW);
        $("#couponListDiv").append(groupList.map(buildCouponGroup).join(''));

        if (couponPageInfo.pageNo === couponPageInfo.totalPageCount) {
            $("#couponMoreBtn").parent().remove();
        }
        couponPageInfo.pageNo++;
    });
}

function getMyCouponList() {
    ajaxCommon.getItemNoLoading({
        id: 'getMyCouponListAjax'
        , cache: false
        , url: '/coupon/getMyCouponListAjax.do'
        , dataType: "json"
    }, function (result) {
        if (result.resultCd === "00001") {
            // 비로그인 시
            $("#myCouponLogin").show();
        } else if (result.resultCd === "00002") {
            // 준회원
            $("#myCouponRegular").show();
        } else if (result.resultCd === AJAX_RESULT_CODE.SUCCESS) {
            if (result.myCouponList && result.myCouponList.length > 0) {
                $("#myCouponListDiv").append(result.myCouponList.map(buildMyCouponItem).join(''));
                $("#myCouponListDiv").show();
            } else {
                $("#myCouponEmpty").show();
            }
        }
    });
}

function buildMyCouponItem(myCoupon) {
    return `
        <div class="c-card c-card--coupon mcoupon">
            <span class="c-card__title">${myCoupon.coupnCtgNm}</span>
            <div class="c-card__sub">
                <p class="c-text">
                    ${myCoupon.smsSndPosblYn === 'N'
                        ? `<span class="sub-title">쿠폰번호 </span>
                           <span class="sub-code">${myCoupon.coupnNo}</span>`
                        : `<span class="sub-title"> </span>`
                    }
                </p>
                <button class="c-button c-button--white c-button-coupon--copy" onclick="copyCouponNum('${myCoupon.coupnNo}')">번호복사</button>
            </div>
            <div class="c-card__bottom">
                <div class="c-card__date">
                    <span class="sub-code">${myCoupon.pstngStartDate} ~ ${myCoupon.pstngEndDate}</span>
                </div>
                <button class="c-button c-button--primary c-button-coupon--detail" onclick="showDtl('${myCoupon.coupnNo}', '${myCoupon.coupnCtgCd}');">상세보기</button>
            </div>
        </div>
    `;
}

function groupBySize(list, size) {
    var result = [];
    for (var i = 0; i < list.length; i += size) {
        result.push(list.slice(i, i + size));
    }
    return result;
}

function buildCouponGroup(coupons) {
    return `
        <div class="cust-bene-coupon-list-group">
            <ul class="cust-bene-coupon-item-wrap">
                ${coupons.map(buildCouponItem).join('')}
            </ul>
            ${buildCouponDetail()}
        </div>
    `;
}

function buildCouponItem(coupon) {
    //쿠폰 라벨
    const labelList = coupon.couponLabel ? coupon.couponLabel.split(',') : [];

    const labelHtml = labelList
        .map(label => {
            const clsMap = { N: '--new', H: '--hot', E: '--event' };
            return `<span class="cust-bene-coupon__label cust-bene-coupon__label${clsMap[label] || ''}"><em></em></span>`;
        })
        .join('');

    return `
        <li class="cust-bene-coupon-item" type="button" aria-expanded="false" tabindex="0" data-seq="${coupon.coupnCtgCd}">
            <div class="cust-bene-coupon-item__img">
                <img src="${coupon.thumbImgPc}" alt="${coupon.imgDesc}">
            </div>
            <div class="cust-bene-coupon-item__desc">
                <div class="cust-bene-coupon__label-wrap">
                    ${labelHtml}
                </div>
                <strong>${coupon.coupnOutsideVendrNm}</strong>
                <p>${coupon.benefitDesc}</p>
            </div>
        </li>
    `;
}

function buildCouponDetail(seq) {
    return `
        <div class="cust-bene-coupon-detail" aria-hidden="true">
            <button class="cust-bene-coupon-detail__close"><i class="c-icon c-icon--arrow-gray-5" aria-hidden="true"></i><span class="sr-only">쿠폰 상세정보 열기</span></button>
            <div class="cust-bene-coupon-detail__content">
                <ul class="cust-bene-coupon-detail-list">
                    <li class="cust-bene-coupon-detail-item">
                        <div class="cust-bene-coupon-detail__title">혜택안내</div>
                        <div class="cust-bene-coupon-detail__desc benefitDescDtl"></div>
                    </li>
                    <li class="cust-bene-coupon-detail-item">
                        <div class="cust-bene-coupon-detail__title">이용방법</div>
                        <div class="cust-bene-coupon-detail__desc usageWay"></div>
                    </li>
                    <li class="cust-bene-coupon-detail-item">
                        <div class="cust-bene-coupon-detail__title">이용안내</div>
                        <div class="cust-bene-coupon-detail__desc usageDesc"></div>
                    </li>
                    <li class="cust-bene-coupon-detail-item">
                        <div class="cust-bene-coupon-detail__title">문의</div>
                        <div class="cust-bene-coupon-detail__desc contact"></div>
                    </li>
                </ul>
                <div class="c-button-wrap u-mt--24">
                    <button  type="button" class="c-button c-button-round--md c-button--green u-width--240 btnDownloadCoupon" onclick="downloadCoupon(this)"> 쿠폰받기</button>
                </div>
            </div>
        </div>
    `;
}

$(document).on("click", ".cust-bene-coupon-item", function () {
    const seq = $(this).data("seq");

    // 상세 영역 보여주기
    const $detail = $(this).closest(".cust-bene-coupon-list-group").find(".cust-bene-coupon-detail");
    $detail.attr("aria-hidden", false);

    const varData = ajaxCommon.getSerializedData({ coupnCtgCd: seq });

    ajaxCommon.getItemNoLoading({
        id: 'benefitCouponCheckAjax',
        cache: false,
        url: '/coupon/benefitCouponCheckAjax.do',
        data: varData,
        dataType: "json"
    }, function(data) {
        // 현재 클릭된 아이템의 버튼 선택
        const $btn = $detail.find(".btnDownloadCoupon");

        if(data.useYn === 'Y'){
            $btn.prop('disabled', true).addClass('is-disabled');
        } else {
            $btn.prop('disabled', false).removeClass('is-disabled');
        }
    });
});



function downloadCoupon(el){
    var coupnCtgCdList = [];
    coupnCtgCdList.push($(el).closest('.cust-bene-coupon-detail').data('seq'));

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
            openPage('pullPopup', '/coupon/couponDownPop.do', parameterData, '1');
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
        $("#swiperCustBeneMmarket .swiper-wrapper").append(productContents.map(buildTodaySalesProduct).join(''));
        initMarketSwiper();
    });
}

function buildTodaySalesProduct(product) {
    return `
        <li class="swiper-slide" role="button" tabindex="0" data-url="${product.url}">
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

function showDtl(coupnNo, coupnCtgCd){

    var parameterData = ajaxCommon.getSerializedData({
        coupnNo : coupnNo,
        coupnCtgCd : coupnCtgCd
    });
    openPage('pullPopup', '/mypage/couponDtlPop.do', parameterData, '1');
}

function copyCouponNum(cuponNum){

    var textarea = document.createElement("textarea");
    document.body.appendChild(textarea);
    textarea.value = cuponNum;
    textarea.select();
    document.execCommand("copy");
    document.body.removeChild(textarea);
    MCP.alert("쿠폰번호가 복사되었습니다.");
}

function initBenefitBannerList() {
    if ($("#custBeneBanner .swiper-slide")) {
        // 상단 배너 슬라이드
        KTM.swiperA11y('#custBeneBanner .swiper-container', {
            loop: true,
            navigation : {
                nextEl : '.cust-bene-pager.swiper-button-next',
                prevEl : '.cust-bene-pager.swiper-button-prev',
            },
            pagination: {
                el: '#custBeneBanner .swiper-pagination',
                clickable: true,
                type: 'fraction',
            },
            autoplay: {
                delay: 3000,
                disableOnInteraction: false,
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
                        top: offsetTop - 100,
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
    }
}

function initCardSwiper() {
    const slide = $("#swiperCustBeneCard .swiper-slide");
    const btn = $("#swiperCustBeneCard .swiper-button-next, #swiperCustBeneCard .swiper-button-prev");

    const slideCount = slide.length;
    const isSwipable = slideCount > 5;

    KTM.swiperA11y("#swiperCustBeneCard .swiper-container", {
        width: 828,
        spaceBetween: 40,
        slidesPerView: 5,
        allowTouchMove: isSwipable,
        navigation: {
            nextEl: "#swiperCustBeneCard .swiper-button-next",
            prevEl: "#swiperCustBeneCard .swiper-button-prev",
        },
        pagination: {
            el: '#swiperCustBeneCard .swiper-pagination',
            clickable: true,
        },
    });

    if (isSwipable) {
        btn.show();
    } else {
        btn.hide();
    }
}

function bindCardToggleEvent() {
    $("#swiperCustBeneCard").on("click keydown", ".swiper-slide", function(e) {
        if (e.type === "click" || e.key === "Enter" || e.key === " ") {
            e.preventDefault();

            const isKeyboard = e.type !== "click"; // 🔹 키보드 접근 여부
            const slides = $("#swiperCustBeneCard .swiper-slide");
            const currentSlide = $(this).closest(".swiper-slide");

            const $detail = $("#custBeneCardDetail");
            const $btnWrap = $detail.find(".c-button-wrap");
            const $closeBtn = $("#custBeneCardDetailClose");
            $closeBtn.data("lastSlide", currentSlide);

            const isOpen = $detail.is(":visible");
            const isSameSlide = currentSlide.hasClass("is-active");

            slides.removeClass("is-active is-deactive");
            currentSlide.addClass("is-active");
            slides.not(currentSlide).addClass("is-deactive");
            slides.attr("aria-expanded", "false");
            currentSlide.attr("aria-expanded", "true");

            $("#custBeneCardNotice").hide();

            if (!isSameSlide) {
                $("#custBeneCardDetail .cprtCardGdncNm").text($(this).find('.cprtCardGdncNm').text());
                loadCprtCardDetail($(this).data('seq'));
            }

            if (isOpen && !isSameSlide) {
                $btnWrap.stop(true, true).fadeOut(50);
                $detail.stop(true, true).slideUp(10, function() {
                    $(this).attr("aria-hidden", "true");

                    $(this).slideDown(200, function() {
                        $(this).attr("aria-hidden", "false");
                        if (isKeyboard) setTimeout(() => $closeBtn.focus(), 50);
                    });
                    $btnWrap.hide().fadeIn(200);
                });
            } else if (!isOpen) {
                $detail.stop(true, true).slideDown(200, function() {
                    $(this).attr("aria-hidden", "false");
                    if (isKeyboard) setTimeout(() => $closeBtn.focus(), 50);
                });
                $btnWrap.hide().fadeIn(200);
            }
        }
    });

    // 제휴카드 슬라이드 닫기 버튼
    $("#custBeneCardDetailClose").on("click keydown", function(e) {
        if (e.type === "click" || e.key === "Enter" || e.key === " ") {
            e.preventDefault();

            const isKeyboard = e.type !== "click"; // 키보드 접근 여부
            const $detail = $("#custBeneCardDetail");
            const $btnWrap = $detail.find(".c-button-wrap");
            const slides = $("#swiperCustBeneCard .swiper-slide");
            const lastSlide = $(this).data("lastSlide");

            $detail.stop(true, true).slideUp(600, function() {
                $(this).attr("aria-hidden", "true");
            });

            setTimeout(function() {
                $("#custBeneCardNotice").fadeIn(500);
            }, 650);

            slides.removeClass("is-active is-deactive").attr("aria-expanded", "false");
            if (isKeyboard && lastSlide && lastSlide.length) {
                setTimeout(() => lastSlide.focus(), 400);
            }
        }
    });
}

function initMarketSwiper() {
    // M마켓 슬라이드
    const slideMarket = $("#swiperCustBeneMmarket .swiper-slide");
    const btnMarket = $("#swiperCustBeneMmarket .swiper-button-next, #swiperCustBeneMmarket .swiper-button-prev");

    const slideCountMarket = slideMarket.length;
    const isSwipableMarket = slideCountMarket > 4;

    KTM.swiperA11y("#swiperCustBeneMmarket .swiper-container", {
        width: 900,
        spaceBetween: 20,
        slidesPerView: 4,
        allowTouchMove: isSwipableMarket,
        navigation: {
            nextEl: "#swiperCustBeneMmarket .swiper-button-next",
            prevEl: "#swiperCustBeneMmarket .swiper-button-prev",
        },
        pagination: {
            el: '#swiperCustBeneMmarket .swiper-pagination',
            clickable: true,
        },
    });

    if (isSwipableMarket) {
        btnMarket.show();
    } else {
        btnMarket.hide();
    }
}

function bindMarketItemClickEvent() {
    $("#swiperCustBeneMmarket").on("click", ".swiper-slide", function(e) {
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
            top: offsetTop - 100,
            behavior: 'smooth'
        });
    }
}

function selectCardBySeq(cprtCardGdncSeq) {
    $("#swiperCustBeneCard .swiper-slide").each(function (index, slide) {
        if ($(slide).data('seq') == cprtCardGdncSeq) {
            $(slide).trigger('click');
            $("#swiperCustBeneCard .swiper-container")[0].swiper.slideTo(index, 0);
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
                location.href = "/mypage/multiPhoneLine.do"
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
    location.href = "/loginForm.do";
}

function goToMarketIntro() {
    var landingUrl = $("#landingUrl").val();
    location.href = "/point/mstoreView.do" + (landingUrl ? `?landingUrl= + ${encodeURIComponent(landingUrl)}` : "");
}

function openMarketAuthPop() {
    openPage('pullPopup', '/point/mstoreAuthPop.do');
}

function openMstoreTermsAgreePop() {
    var el = document.querySelector('#mstoreTermsAgreePop');
    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
    modal.open();
}

function openExternalMarket() {
    var landingUrl = $("#landingUrl").val();
    window.open("/point/mstoreAuth.do" + (landingUrl ? `?landingUrl= + ${encodeURIComponent(landingUrl)}` : ""), "_blank");
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

function initMstoreTermsAgree() {
    ajaxCommon.getItem({
            id: 'mstoreTermsPop',
            url: "/termsPop.do",
            type: "GET",
            dataType: "json",
            data: "cdGroupId1=FormEtcCla&cdGroupId2=MstoreTermsAgree1"
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
        }
        , function (jsonObj) {

            if(jsonObj.RESULT_CODE=='0001'){ //로그인 세션 빠짐 > 로그인 페이지 이동
                MCP.alert(jsonObj.RESULT_MSG, function (){
                    location.href= "/loginForm.do";
                });
            }else if (jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
                MCP.alert(jsonObj.RESULT_MSG);
            }else {
                // 팝업 닫고 Mstore 이동
                KTM.Dialog.closeAll();
                openExternalMarket();
            }
        });
}
