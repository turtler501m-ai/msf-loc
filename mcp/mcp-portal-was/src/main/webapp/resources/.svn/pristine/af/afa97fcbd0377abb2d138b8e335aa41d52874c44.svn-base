$(document).ready(function() {
    // 배너
    getBannerList();

    // M쇼핑할인 사용후기 조회
    displayMcashReview();

    // 슬라이드 배너
    document.addEventListener('UILoaded', function() {
        const container = document.querySelector('#mcashTopBanner .swiper-container');
        if (!container) {
            return false;
        }
        const slides = container.querySelectorAll('.swiper-slide');
        const isSingleSlide = slides.length <= 1;

        KTM.swiperA11y('#mcashTopBanner .swiper-container', {
            direction: "vertical",
            loop: !isSingleSlide,
            autoplay: isSingleSlide ? false : {
                delay: 5000,
                disableOnInteraction: false,
            },
            pagination: {
                el: '#mcashTopBanner .swiper-pagination',
                type: 'fraction',
            }
        });
    });
});

function fn_go_banner(linkUrl,bannSeq, bannCtg ,tgt){
    if(!linkUrl) return;
    insertBannAccess(bannSeq,bannCtg);
    if(tgt.trim() == '_blank'){
        window.open(linkUrl);
    }else{
        window.location.href = linkUrl;
    }
}

// M쇼핑할인 사용후기 목록 조회
function displayMcashReview() {
    var varData = ajaxCommon.getSerializedData({
        reviewType : 'MCASH'
    });

    ajaxCommon.getItem({
        id: 'mcashReviewListAjax',
        url: '/review/bestReviewListAjax.do',
        data: varData,
        dataType: "json",
        cache: false,
        async: true
    }, function(jsonObj) {
        var appendHtml = "";
        var reviewList = jsonObj.bestReviewDtoList;

        if (jsonObj.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS) {
            if (reviewList.length < 3) {
                return false;
            }

            reviewList = reviewList.slice(0, 3);

            $.each(reviewList, function(index, value) {
                var mcashReviewImgList = value.reviewImgList;
                var regstNm = ajaxCommon.isNullNvl(value.mkRegstNm, "");
                var regstDt = ajaxCommon.isNullNvl(value.regstDt, "");
                var reviewContent = ajaxCommon.isNullNvl(value.reviewContent, "");
                var prizeRnk = ajaxCommon.isNullNvl(value.prizeRnk, "");

                if (prizeRnk < 4 && prizeRnk != null && prizeRnk != "") {
                    appendHtml += "<li class='review-item' onclick='mcashReviewMove("+index+")'>";
                } else {
                    appendHtml += "<li class='review-item' onclick='mcashReviewMove()'>";
                }
                appendHtml += " <span class='review-rank__label' style='background-color: #002060;'>★"+prizeRnk+"등</span>";
                appendHtml += " <p class='review-item__content ellipsis u-co-gray9' style='-webkit-line-clamp: 4; height: 4.375rem; max-height: 4.5rem;'>"+reviewContent+"</p>";
                appendHtml += " <br/>";

                if (mcashReviewImgList.length > 0) {
                    var firstImage = mcashReviewImgList[0];
                    var filePathNm = firstImage.filePathNm;
                    var fileAlt = ajaxCommon.isNullNvl(firstImage.fileAlt, "");

                    appendHtml += " <div class='review-img'>";
                    appendHtml += "     <img class='reivew-item__img' src='"+filePathNm+"' alt='"+fileAlt+"' onerror='noImage(this);'>";
                    if (mcashReviewImgList.length > 1) {
                        appendHtml += "     <span class='review__image__label'>+"+(mcashReviewImgList.length-1)+"</span>";
                    }
                    appendHtml += " </div>";

                } else {
                    appendHtml += " <div class='review-img'>";
                    appendHtml += "     <img class='reivew-item__img' src='/resources/images/portal/content/img_review_noimage.png' alt='no-image'>";
                    appendHtml += " </div>";
                }

                appendHtml += " <div class='review-item__info'>";
                appendHtml += "     <span class='name'>"+regstNm+"</span>";
                appendHtml += "     <span class='date'>"+regstDt+"</span>";
                appendHtml += " </div>";
                appendHtml += "</li>";

            });
            $("#mcashReview").show();
            $("#mcashReviewList").html(appendHtml);
        } else {
            MCP.alert(jsonObj.RESULT_MSG);
        }
    });
}

// M쇼핑할인 사용후기 이미지가 없을 경우
function noImage(img) {
    img.onerror = "";
    img.src = "/resources/images/portal/content/img_review_noimage.png";

    return true;
}

// M쇼핑할인 사용후기 이동
function mcashReviewMove(val) {
    if (val == undefined) {
        location.href = "/mcash/review/mcashReview.do";
    } else {
        location.href = "/mcash/review/mcashReview.do?choice="+val;
    }
}

// 배너 호출 ajax
function getBannerList() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'C003'
    });

    ajaxCommon.getItemNoLoading({
            id : 'bannerListAjax'
            , cache : false
            , async : false
            , url : '/bannerListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            if(result.result.length > 0){
                for(var i = 0; i < result.result.length; i ++){
                    var html = "";
                    html += "<div class='swiper-slide' addr='" + result.result[i].linkUrlAdr + "' key='" + result.result[i].bannSeq + "' ctg='" + result.result[i].bannCtg + "'>";
                    html += '<a class="swiper-banner__anchor" href='+result.result[i].linkUrlAdr+'>';
                    html += ' <img src="' + result.result[i].bannImg + '" alt="' + result.result[i].imgDesc + '">';
                    html += '</a>';
                    html += '</div>';
                    html += "<input type='hidden' value='" + result.result[i].bannSeq + "'>";
                    $("#swiperArea").append(html);
                }

                if(result.result.length == 1){
                    $("#swiperMshoppingBanner .swiper-button-next, #swiperMshoppingBanner .swiper-button-prev").hide();
                }

                KTM.swiperA11y("#swiperMshoppingBanner .swiper-container", {
                    navigation: {
                        nextEl: "#swiperMshoppingBanner .swiper-button-next",
                        prevEl: "#swiperMshoppingBanner .swiper-button-prev",
                    },
                    pagination: {
                        el: '#swiperMshoppingBanner .swiper-pagination',
                        type: 'fraction',
                    }
                });

                $("#swiperMshoppingBanner").show();
            } else {
                $("#swiperMshoppingBanner").hide();
            }
        });
}