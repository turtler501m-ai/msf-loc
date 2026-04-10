var isLoad = false;
var mcashMainUrl = new URL($("#mcashMainUrl").val());

$(document).ready(function() {
    getBannerList();

    window.addEventListener('message', receiveMessage, false);
    var iframe = $("#mcashMain");
    iframe.attr('src', $("#mcashMainUrl").val());
    sleep(15000).then(() => checkLoad());
})

function receiveMessage(event) {
    if( event.origin !== mcashMainUrl.origin ) {
        return;
    }

    isLoad = true;

    var mcashMainHeight = event.data.mcashMainHeight;
    var mcashMoveUrl = event.data.mcashMoveUrl;

    if ( mcashMainHeight && mcashMainHeight > 0 ) {
        $("#mcashMain").height(mcashMainHeight);
    }

    if ( mcashMoveUrl ) {
        if ( $("#mobileAppChk").val() == 'A' ) {
            appOutSideOpen(mcashMoveUrl);
        } else {
            window.open(mcashMoveUrl, "_blank");
        }
    }
}

async function sleep(time) {
    await new Promise(resolve => setTimeout(resolve, time));
}

function checkLoad() {
    if(!isLoad) {
        MCP.alert("일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.", function() {
                location.href="/m/main.do";
            }
        );
    }
}

function fn_go_banner(linkUrl, bannSeq, bannCtg, tgt){
    if(!linkUrl) return;
    insertBannAccess(bannSeq, bannCtg);

    if(tgt.trim() == 'Y'){
        window.open(linkUrl);
    }else{
        window.location.href = linkUrl;
    }
}


// 배너 호출 ajax
var getBannerList = function() {
    var varData = ajaxCommon.getSerializedData({
        bannCtg : 'C004'
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

            $(".swiper-wrapper").append(html);

            var swiperCardBanner = new Swiper("#swiperCardBanner", {
                spaceBetween: 10,
            });
            $("#swiperCardBanner").show();
        }else{
            $("#swiperCardBanner").hide();
        }
    });
}
