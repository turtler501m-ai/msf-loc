


var pageObj = {
    rateNm:""          //요금제명
    ,prdtNm:""
}

$(document).ready(function(){
    $('#btnNext2').click(function(){
        location.href='/';
    });

    if ($("#btnNewForm").length) {
        ajaxCommon.getItem({
            id: 'getAgentFormLink',
            url: '/appForm/getAgentFormLinkAjax.do',
            data: "",
            dataType: "json",
            cache: false
        }, function(jsonObj) {
            if (jsonObj.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS) {
                $("#btnNewForm").removeClass('c-hidden');
                $("#btnNewForm").click(function() {
                    location.href = jsonObj.agentFormLink;
                });
            }
        });
    }

    var sendScanImage = function () {



        var varData = ajaxCommon.getSerializedData({
            requestKey : $("#requestKey").val()
            , cstmrName : $("#cstmrName").val()
        });
        //비동기
        ajaxCommon.getItemNoLoading({
            id:'sendScan'
            ,cache:false
            ,url:'/appform/sendScanAjax.do'
            ,data: varData
            ,dataType:"json"
            ,errorCall : function () {
                //nothing
            }
        }
        ,function(jsonObj){
            //nothing
        });
    }();

 });

// 진행중 이벤트 스와이퍼
var swiperCardBanner = new Swiper("#swiperCardBanner", {
    spaceBetween : 10,
    observer : true,
    observeParents : true,
});

