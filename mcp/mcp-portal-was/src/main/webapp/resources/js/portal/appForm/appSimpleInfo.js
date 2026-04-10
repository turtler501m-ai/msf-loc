/**
 *
 */

var pageInfo = {
    simpleApplyObj :{}
}

$(document).ready(function(){
            getBannerList();

});

    var selfTimeChk = function(){


         //셀프개통 가능 여부 확인
        ajaxCommon.getItemNoLoading({
            id:'isSimpleApply'
            ,cache:false
            ,url:'/appForm/isSimpleApplyAjax.do'
            ,data: ""
            ,dataType:"json"
            ,async:false
        },function(jsonObj){
            pageInfo.simpleApplyObj = jsonObj ;
        });

        if (!pageInfo.simpleApplyObj.IsMacTime && !pageInfo.simpleApplyObj.IsMnpTime) {
            $('#movBtn').hide();
            $('#closeBtn').show();
            $('.self-opening__text').hide();
            $('.self-opening__title').text('셀프개통이 불가능한 시간입니다.');
            $('#selfBtn').trigger('click');
        } else if (pageInfo.simpleApplyObj.IsMacTime && !pageInfo.simpleApplyObj.IsMnpTime) {
            $('#movBtn').show();
            $('#closeBtn').hide();
            $('.self-opening__text').show();
            $('.self-opening__title').text('신규 가입만 가능한 시간입니다.');
            $('#selfBtn').trigger('click');
        } else {
            location.href='/appForm/appFormDesignView.do';
        }


    }

    //배너 호출 ajax
    function getBannerList() {
        var varData = ajaxCommon.getSerializedData({
             bannCtg : 'U201'
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
                    html += '<a class="swiper-banner__anchor" href="javascript:void(0);">';
                    html += ' <img src="' + result.result[i].bannImg + '" alt="' + result.result[i].imgDesc + '">';
                    html += '</a>';
                    html += '</div>';
                    html += "<input type='hidden' value='" + result.result[i].bannSeq + "'>";
                    $("#swiperArea").append(html);
                }

                if(result.result.length == 1){
                    $("#swiperFriendInvite .swiper-button-next, #swiperFriendInvite .swiper-button-prev").hide();
                }

                KTM.swiperA11y("#swiperFriendInvite .swiper-container", {
                    navigation: {
                      nextEl: "#swiperFriendInvite .swiper-button-next",
                      prevEl: "#swiperFriendInvite .swiper-button-prev",
                    },
               });

               $(".swiper-slide").on('click', function(){
                   var key = $(this).attr("key");
                   var ctg = $(this).attr("ctg");
                   var parameterData = ajaxCommon.getSerializedData({
                       eventPopTitle : '셀프개통 이벤트'
                   });

                   insertBannAccess(key,ctg);

                   $("#eventPop").remove();
                   openPage('eventPop', $(this).attr("addr"), parameterData);





              });

                $("#swiperFriendInvite").show();
            } else {
                $("#swiperFriendInvite").hide();
            }
        });
    }

