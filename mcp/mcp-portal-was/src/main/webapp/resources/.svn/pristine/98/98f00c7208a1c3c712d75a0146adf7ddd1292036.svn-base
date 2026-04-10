/**
 *
 */
var prePreDateMonth = $("#prePreDateMonth").val().substr(5,2);
var preDateMonth = $("#preDateMonth").val().substr(5,2);
var nowDateMonth =$("#nowDateMonth").val().substr(5,2);
var prePreDate = $("#prePreDateMonth").val().substr(0,4) + "." + $("#prePreDateMonth").val().substr(5,2);
var preDate = $("#preDateMonth").val().substr(0,4) + "." + $("#preDateMonth").val().substr(5,2);
var nowDate = $("#nowDateMonth").val().substr(0,4) + "." + $("#nowDateMonth").val().substr(5,2);
var prePreDateSumCount = 0 ;
var prePreDateMMSumCount  = 0;
var prePreDateInSumCount = 0;
var preDateSumCount = 0;
var preDateMMSumCount  = 0;
var preDateInSumCount = 0;
var nowDateSumCount = 0;
var nowDateMMSumCount = 0;
var nowDateInSumCount = 0;

$(document).ready(function() {
    $("#prePreMon").html(prePreDate);
    $("#preMon").html(preDate);
    $("#nowMon").html(nowDate);

    getCommendState($("#ctn").val());


    $("#btnReCommendState").click(function(){
        location.href='/mypage/reCommendState.do';
    });


    $("#btnReCommendMng").click(function(){
        location.href='/mypage/reCommendMng.do';
    });



});


function select() {
    getCommendState($("#ctn").val());
}


function getCommendState(ctn) {
    if (ctn) {
        var varData = ajaxCommon.getSerializedData({
            ncn:ctn,
        });
    }else{
        var varData = ajaxCommon.getSerializedData({});
    }

    ajaxCommon.getItem({
        id:'getCommendStateAjax'
        ,cache:false
        ,url:'/mypage/getCommendStateAjax.do'
        ,data: varData
        ,dataType:"json"
    }
    ,function(jsonData){
        prePreDateSumCount = 0 ;
        preDateSumCount = 0;
        nowDateSumCount = 0;
        nowDateMMSumCount = 0;
         nowDateInSumCount = 0;

        if ('00000' == jsonData.RESULT_CODE  && jsonData.RESULT_SIZE > 0 ) {
            var jsonList = jsonData.RESULT_DATE ;


            var REQ_BUY_TYPE ={
                MOBILE:"ALL"
                ,INTER:"INTER"
            } ;

            for (var i=0 ; i < jsonList.length ; i++) {
                var lstComActvDate =jsonList[i].lstComActvDate.substr(4,2);

                if (prePreDateMonth ==  lstComActvDate) {
                    prePreDateSumCount += jsonList[i].sumCount;
                    if (jsonList[i].reqBuyType == REQ_BUY_TYPE.MOBILE) {
                        prePreDateMMSumCount += jsonList[i].sumCount;
                    } else if(jsonList[i].reqBuyType == REQ_BUY_TYPE.INTER){
                        prePreDateInSumCount += jsonList[i].sumCount;
                    }
                }

                if (preDateMonth ==  lstComActvDate) {
                    preDateSumCount += jsonList[i].sumCount;
                    if (jsonList[i].reqBuyType == REQ_BUY_TYPE.MOBILE) {
                        preDateMMSumCount += jsonList[i].sumCount;
                    } else if(jsonList[i].reqBuyType == REQ_BUY_TYPE.INTER){
                        preDateInSumCount += jsonList[i].sumCount;
                    }
                }

                if (nowDateMonth ==  lstComActvDate) {
                    nowDateSumCount += jsonList[i].sumCount;

                    if (jsonList[i].reqBuyType == REQ_BUY_TYPE.MOBILE) {
                        nowDateMMSumCount += jsonList[i].sumCount;
                    } else if(jsonList[i].reqBuyType == REQ_BUY_TYPE.INTER){
                        nowDateInSumCount += jsonList[i].sumCount;
                    }
                }
            }

            $("#thisMonPhoneCnt, #thisMonInterCnt, #thisMonCnt, #prePreMon, #prePreMonAllCnt, #preMon, #preMonAllCnt, #nowMon, #nowMonAllCnt ,#nowMonPhoCnt,#nowMonIntCnt, #preMonPhoCnt , #preMonIntCnt, #prePreMonPhoCnt , #prePreMonIntCnt").empty();
            $("#thisMonPhoneCnt").html(nowDateMMSumCount + "명");
            $("#thisMonInterCnt").html(nowDateInSumCount + "명");
            $("#thisMonCnt").html(nowDateSumCount + "명");
            $("#prePreMon").html(prePreDate);
            $("#prePreMonAllCnt").html(prePreDateSumCount + "명");
            $("#prePreMonPhoCnt").html(prePreDateMMSumCount + "명");
            $("#prePreMonIntCnt").html(prePreDateInSumCount + "명");

            $("#preMon").html(preDate);
            $("#preMonPhoCnt").html(preDateMMSumCount + "명");
            $("#preMonIntCnt").html(preDateInSumCount + "명");
            $("#preMonAllCnt").html(preDateSumCount + "명");

            $("#nowMon").html(nowDate);
            $("#nowMonAllCnt").html(nowDateSumCount + "명");
            $("#nowMonPhoCnt").html(nowDateMMSumCount + "명");
            $("#nowMonIntCnt").html(nowDateInSumCount + "명");




        } else if(jsonData.RESULT_CODE == '0002'){
            MCP.alert("등록된 회선이 없습니다.");
           initData();
        }else if(jsonData.RESULT_CODE == '0004'){
            MCP.alert("초대한 친구가 없습니다.");
           initData();
        }else{
            initData();
        }

    });
}

function initData(){
    $("#thisMonPhoneCnt, #thisMonInterCnt, #thisMonCnt, #prePreMon, #prePreMonAllCnt, #preMon, #preMonAllCnt, #nowMon, #nowMonAllCnt ,#nowMonPhoCnt,#nowMonIntCnt, #preMonPhoCnt , #preMonIntCnt, #prePreMonPhoCnt , #prePreMonIntCnt").empty();
    $("#thisMonPhoneCnt").html("0명");
    $("#thisMonInterCnt").html("0명");
    $("#thisMonCnt").html("0명");
    $("#prePreMon").html(prePreDate);
    $("#prePreMonAllCnt").html("0명");
    $("#prePreMonPhoCnt").html("0명");
    $("#prePreMonIntCnt").html("0명");
    $("#preMon").html(preDate);
    $("#preMonPhoCnt").html("0명");
    $("#preMonIntCnt").html("0명");
    $("#preMonAllCnt").html("0명");
    $("#nowMon").html(nowDate);
    $("#nowMonAllCnt").html("0명");
    $("#nowMonPhoCnt").html("0명");
    $("#nowMonIntCnt").html("0명");
}