


let OCRtoken ;
var tmOcrint;
var OCRtype;

var pageOcrObj = {
    ocrCnt:0
    ,isStop:false
}

//usim
//social
//driver
//creditcard

var DRIVER_LOCAL_CODE ={
    "서울":"11"
    ,"부산":"12"
    ,"경기":"13"
    ,"강원":"14"
    ,"충북":"15"
    ,"충남":"16"
    ,"전북":"17"
    ,"전남":"18"
    ,"경북":"19"
    ,"경남":"20"
    ,"제주":"21"
    ,"대구":"22"
    ,"인천":"23"
    ,"광주":"24"
    ,"대전":"25"
    ,"울산":"26"
    ,"경기북부":"28"
}


/*
social ==> jumin(주민등록증),
driver  ==> driver(운전면허증),
creditcard= => creditcard(신용카드),
usim   ==> usim(유심)
*/

$(document).ready(function(){

    $('._ocrRecord').click(function(){
        var mobileAppChk = $("#mobileAppChk").val();
        OCRtype = $(this).data('type');
        if ("social" == OCRtype || "driver" == OCRtype) {
            OCRtype = "general"; //AS-IS 호환을 위해 변경 처리
        }
        pageOcrObj.ocrCnt =0 ;
        pageOcrObj.isStop = false;
        //앱/어플리케이션으로 실행시에만 실행
        if (mobileAppChk == "A") {
            requestPermission('PIC', 'review');
        } else {
            review() ;
        }
    });
});


function review() {
    KTM.LoadingSpinner.show();
    var varData = ajaxCommon.getSerializedData({
        type:OCRtype
    });

    ajaxCommon.getItemNoLoading({
            id:'getOcrReadyAjax'
            ,cache:false
            ,url:'/m/getOcrReadyAjax.do'
            ,data: varData
            ,dataType:"json"
            ,errorCall : function () {
                //NOTHING
            }
        }
        ,function(result){
            KTM.LoadingSpinner.hide(true);


            if(result.returnCode == '0000'){
                //alert(result.message);
                var data = JSON.parse(result.message);

                //console.dir(data);
                //alert(data.message);
                var openUrl = data._links._start_page.href ;

                if ( openUrl == undefined ){
                    MCP.alert('다시 시도해주세요.');
                } else {
                    var OcrPopupStatus = window.open(openUrl);
                    OCRtoken = data.token;

                    var tm = setTimeout(function(){}, 5000);
                    clearTimeout(tm);
                    tmOcrint = setInterval(function() {

                        if(!pageOcrObj.isStop){
                            if(OcrPopupStatus.closed){
                                fnResultOcr();
                            }
                        } else {
                            clearInterval(tmOcrint);
                        }


                    }, 2000);
                    //결과 자동호출
                }
            } else {
                MCP.alert(result.message);
            }
        });
}

var fnResultOcr = function() {
    KTM.LoadingSpinner.hide(true);

    var varData = ajaxCommon.getSerializedData({
        token:OCRtoken
    });

    ajaxCommon.getItemNoLoading({
            id:'getOcrResultAjax'
            ,cache:false
            ,url:'/m/getOcrResultAjax.do'
            ,data: varData
            ,dataType:"json"
            ,errorCall : function () {
                //NOTHING
            }
        }
        ,function(result){
            var jsonObj = JSON.parse(result.message);
            pageOcrObj.ocrCnt++;

            if(result.returnCode == '0000' && jsonObj.status == 0){

                // 뭐지 모르지만..?? 여기에 없으면  무한으로 interval 발생 .. 함..
                // 아래 까지 내려 가지 않는 듯...
                pageOcrObj.ocrCnt = 0;
                pageOcrObj.isStop = true;


                var idCardType = jsonObj.idCardType;

                if (idCardType == "usim") {
                    /*"original" : {
                        "cardType" : "usim",
                            "usim_code" : "8982300621003206977F"
                    },
                    "modified" : {
                        "cardType" : "usim",
                            "usim_code" : "8982300621003206977F"
                    },*/

                    var reqUsimSn = "";

                    if(jsonObj.modified.usim_code != undefined) {
                        reqUsimSn = jsonObj.modified.usim_code ;
                        if (reqUsimSn.length > 19) {
                            reqUsimSn = reqUsimSn.substring(0,19);
                        }
                    } else {
                        alert("jsonObj.modified.usim_code [undefined]");
                        return;
                    }

                    $("#reqUsimSn").val(reqUsimSn);
                    $("#reqUsimSn").parent().addClass('has-value');
                } else if (idCardType == "jumin") {
                    var pDate = jsonObj.modified.issue_date ;
                    if (pDate.length == 8) {
                        pDate= pDate.substring(0,4) +"-"+ pDate.substring(4,6) +"-"+ pDate.slice(-2);
                    }
                    $("#selfIssuExprDt").val(pDate);
                    $("#selfIssuExprDt").parent().addClass('has-value');
                    isValidateNonEmptyStep2();
                } else if (idCardType == "driver") {
                    var pDate = jsonObj.modified.issue_date ;
                    if (pDate.length == 8) {
                        pDate= pDate.substring(0,4) +"-"+ pDate.substring(4,6) +"-"+ pDate.slice(-2);
                    }
                    $("#selfIssuExprDt").val(pDate);
                    $("#selfIssuExprDt").parent().addClass('has-value');

                    var pNumber = jsonObj.modified.driver_number;
                    var jbSplit2 = pNumber.split('-');

                    if (jbSplit2.length == 4) {
                        //23-04-11111-80
                        $("#driverSelfIssuNum1").val(jbSplit2[0]);
                        $("#driverSelfIssuNum2").val(jbSplit2[1]+jbSplit2[2]+jbSplit2[3]);
                    } else if (jbSplit2.length == 3) {
                        // 서울99-043159-22
                        //숫자 제거
                        var tempStr = jbSplit2[0].replace(/[0-9]/g, "");

                        // 숫자만 var regex= /[^0-9]/g
                        var tempStr2 = jbSplit2[0].replace(/[^0-9]/g, "");

                        $("#driverSelfIssuNum1").val(DRIVER_LOCAL_CODE[tempStr]);
                        $("#driverSelfIssuNum2").val(tempStr2+jbSplit2[1]+jbSplit2[2]);
                    }
                    isValidateNonEmptyStep2();
                } else if (idCardType == "creditcard") {
                    var cardNumber = "";
                    var expiryDate = "";

                    if(jsonObj.modified.card_number != undefined) {
                        cardNumber = jsonObj.modified.card_number ;
                    } else {
                        alert("jsonObj.modified.card_number [undefined]");
                        return;
                    }

                    if(jsonObj.modified.expiry_date != undefined) {
                        expiryDate = jsonObj.modified.expiry_date ;
                    } else {
                        alert("jsonObj.modified.expiry_date [undefined]");
                        return;
                    }

                    $("#reqCardNo").val(cardNumber);
                    $("#reqCardNo").parent().addClass('has-value');

                    var jbSplit = expiryDate.split('/');

                    if (jbSplit.length == 2) {
                        var tempStr = "0"+jbSplit[0];
                        $("#reqCardMm").val(tempStr.slice(-2));
                        $("#reqCardYy").val("20"+jbSplit[1]);
                    }
                    isValidateNonEmptyStep5();
                }

            } else {
                if(pageOcrObj.ocrCnt > 60){
                    pageOcrObj.ocrCnt = 0;
                    pageOcrObj.isStop = true;
                }
                //MCP.alert(result.message);
            }

            //clearInterval(tmOcrint);
            //alert("end");
        });



}





