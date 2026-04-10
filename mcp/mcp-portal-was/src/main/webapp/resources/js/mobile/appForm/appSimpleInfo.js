
var interval ;

var pageInfo = {
        simpleApplyObj :{}
    }

$(document).ready(function(){
   getBannerList();
});

    //배너 호출 ajax
    function getBannerList() {
        var varData = ajaxCommon.getSerializedData({
             bannCtg : 'U201'
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
            if(result.result.length > 0){
                for(var i = 0; i < result.result.length; i ++){
                    var html = "";
                    html += "<div class='swiper-slide' href='javascript:void(0);'  addr='" + result.result[i].mobileLinkUrl + "' key='" + result.result[i].bannSeq + "' ctg='" + result.result[i].bannCtg + "'  style='background-color:" + result.result[i].bgColor + "'>";
                    html+= '<button class="swiper-event-banner__image">';
                    html += "<img src='" + result.result[i].mobileBannImgNm + "' alt='" + result.result[i].imgDesc + "'>";
                    html += "</button>";
                    html += "</div>";
                    html += "<input type='hidden' value='" + result.result[i].bannSeq + "'>";
                    $("#swiperInvitBanner .swiper-wrapper").append(html);
                    $("#swiperInvitBanner").show();
                }
                var swiperInvitBanner = new Swiper("#swiperInvitBanner", {
                    spaceBetween : 10,
                    slidesPerView : 1,
                });
            }else{
                $("#swiperInvitBanner").hide();
            }

            $(".swiper-slide").on('click', function(){
                var key = $(this).attr("key");
                var ctg = $(this).attr("ctg");
                var parameterData = ajaxCommon.getSerializedData({
                    eventPopTitle : '셀프개통 이벤트'
                });

                insertBannAccess(key,ctg);
                $("#eventPop").remove();
                openPage('eventPop', $(this).attr("addr"), parameterData)
            });
        });
    }


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
            MCP.alert('셀프개통이 불가능한 시간입니다.<br/><br/>셀프개통 가능한 시간은<br/>신규(08:00~21:50)<br/>번호이동(10:00~19:50) 입니다.');
        } else if (pageInfo.simpleApplyObj.IsMacTime && !pageInfo.simpleApplyObj.IsMnpTime) {
            MCP.alert('신규 가입만 가능한 시간입니다.<br/><br/>셀프개통 가능한 시간은<br/>신규(08:00~21:50)<br/>번호이동(10:00~19:50) 입니다.', function (){
                location.href='/m/appForm/appFormDesignView.do';
            });
        } else {
            location.href='/m/appForm/appFormDesignView.do';
        }


    }




