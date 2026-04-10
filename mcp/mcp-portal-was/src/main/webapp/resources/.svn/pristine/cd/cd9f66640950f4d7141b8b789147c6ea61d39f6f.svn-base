var pageObj = {
    rateNm: "" ,    // 요금제명
    prdtNm: ""
}

$(document).ready(function() {

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

    // 대리점 신청서 URL 세션 조회
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

});