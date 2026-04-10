var pageObj = {
    eventId: ""
}

$(document).ready(function() {
   getBannerList();

   try {
       // 사용후기 조회
       displayMcashReview();
   } catch (error) {

   }

    document.addEventListener('UILoaded', function() {
        initBannerText();
    });
});

// 약관 팝업 클릭 시 eventId 세팅
var mcashSetEventId = function(eventId) {
    pageObj.eventId = eventId;
};

// 동의 후 닫기
var targetTermsAgree = function() {
    $('#' + pageObj.eventId).prop('checked', true);

    chkAgree();
};

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

function fn_go_banner(linkUrl,bannSeq, bannCtg ,tgt){
    if(!linkUrl) return;
    insertBannAccess(bannSeq,bannCtg);
    if(tgt.trim() == '_blank'){
        window.open(linkUrl);
    }else{
        window.location.href = linkUrl;
    }
}

// 슬라이드 배너
function initBannerText() {
    const container = document.querySelector('#mcashTopBanner .swiper-container');
    if (!container) {
        return false;
    }
    const slides = container.querySelectorAll('.swiper-slide');
    const isSingleSlide = slides.length <= 1;

    var mcashTopBanner = new Swiper('#mcashTopBanner .swiper-container', {
        direction: "vertical",
        loop: !isSingleSlide,
        autoplay: isSingleSlide ? false : {
            delay: 5000,
            disableOnInteraction: false,
        },
    });
}

// 사용후기 목록 조회
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
                    appendHtml += "<li class='reivew-item' onclick='mcashReviewMove("+index+")' style='cursor: pointer'>";
                } else {
                    appendHtml += "<li class='reivew-item' onclick='mcashReviewMove()' style='cursor: pointer'>";
                }
                appendHtml += " <span class='review-rank__label' style='background-color: #002060;'>★"+prizeRnk+"등</span>";
                appendHtml += " <p class='review-item__content ellipsis u-co-gray9'>"+reviewContent+"</p>";
                appendHtml += " <br/>";

                if (mcashReviewImgList.length > 0) {
                    var firstImage = mcashReviewImgList[0];
                    var filePathNm = firstImage.filePathNm;
                    var fileAlt = ajaxCommon.isNullNvl(firstImage.fileAlt, "");

                    appendHtml += " <div class='reivew-item__img' alt=''>";
                    appendHtml += "     <img src='"+filePathNm+"' alt='"+fileAlt+"' onerror='noImage(this);'>";

                    if (mcashReviewImgList.length > 1) {
                        appendHtml += "     <span class='review__image__label'>+"+(mcashReviewImgList.length-1)+"</span>";
                    }
                    appendHtml += "</div>";
                } else {
                    appendHtml += " <div class='reivew-item__img' alt=''>";
                    appendHtml += "     <img src='/resources/images/mobile/content/img_review_noimage.png' alt='no-image'>";
                    appendHtml += " </div>";
                }

                appendHtml += " <div class='review-item__info'>";
                appendHtml += "     <p class='u-co-gray-8'>"+regstNm+"</p>";
                appendHtml += "     <p class='u-co-gray-7'>"+regstDt+"</p>";
                appendHtml += " </div>";
                appendHtml += "</li>";
            });
            $("#mcashReview").show();
            $("#mcashReviewList").append(appendHtml);
        } else {
            MCP.alert(jsonObj.RESULT_MSG);
        }
    });
}

// 이미지가 없을경우
function noImage(image) {
    image.onerror = "";
    image.src = "/resources/images/mobile/content/img_review_noimage.png";
    return true;
}

// M쇼핑할인 사용후기 이동
function mcashReviewMove(val) {
    if (val == undefined) {
        location.href = "/m/mcash/review/mcashReview.do";
    } else {
        location.href = "/m/mcash/review/mcashReview.do?choice="+val;
    }
}

// 배너 호출 ajax
function getBannerList() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'C003'
    });

    ajaxCommon.getItem({
            id : 'bannerListAjax'
            , cache : false
            , async : false
            , url : '/m/bannerListAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(result){
            var bannerList = result.result;

            if(bannerList && bannerList.length > 0){
                var html = "";
                $.each(bannerList, function(index, banner) {
                    html += `<div class='swiper-slide' href='javascript:void(0);' onclick='fn_go_banner("${banner.mobileLinkUrl}", "${banner.bannSeq}", "${banner.bannCtg}", "${banner.linkTarget}")' style='background-color: ${banner.bgColor}'>`;
                    html += `<button class="swiper-event-banner__button">`;
                    html += `<div class="swiper-event-banner__item">`;
                    html += `<img src='${banner.mobileBannImgNm}' alt='${banner.imgDesc}'>`;
                    html += `</div>`;
                    html += `</button>`;
                    html += `</div>`;
                    html += `<input type='hidden' value='${banner.bannSeq}'>`;
                });

                $("#swiperMshoppingBanner .swiper-wrapper").append(html);

                var swiperMshoppingBanner = new Swiper("#swiperMshoppingBanner", {
                    spaceBetween: 10,
                });

                $("#swiperMshoppingBanner .swiper-slide").on("click", function(){
                    var parameterData = ajaxCommon.getSerializedData({
                        eventPopTitle : 'M쇼핑할인 소개페이지'
                    });
                    $("#eventPop").remove();
                    openPage('eventPop', $(this).attr("addr"), parameterData)
                    $("#invite-event-title").text("M쇼핑할인 소개페이지");
                });
                $("#swiperMshoppingBanner").show();
            }else{
                $("#swiperMshoppingBanner").hide();
            }
        });
}