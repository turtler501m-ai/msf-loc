
var diAuthObj = {
    resAuthOjb : {}
}

/** 본인인증 결과 확인 */
function diAuthCallback(infoObj){

    var isCheck = false;

    // 0. 데이터세팅
    var niceType = NICE_TYPE.CUST_AUTH ;
    if (pageObj.niceType != undefined && "" !=pageObj.niceType ) niceType = pageObj.niceType ;

    // 1. option 데이터 default 세팅
    if(infoObj.cstmrName == undefined) infoObj.cstmrName = "";
    if(infoObj.cstmrNativeRrn == undefined) infoObj.cstmrNativeRrn = "";
    if(infoObj.contractNum == undefined) infoObj.contractNum = "";
    if(infoObj.ncType == undefined) infoObj.ncType= "";
    if(infoObj.menuType == undefined) infoObj.menuType= "";
    if(infoObj.userId == undefined) infoObj.userId= "";

    var varData = ajaxCommon.getSerializedData({
         cstmrName:infoObj.cstmrName
        ,cstmrNativeRrn:infoObj.cstmrNativeRrn
        ,contractNum : infoObj.contractNum
        ,ncType: infoObj.ncType
        ,reqSeq: pageObj.niceResLogObj.reqSeq
        ,resSeq: pageObj.niceResLogObj.resSeq
        ,paramR3: niceType
        ,menuType: infoObj.menuType
        ,userId: infoObj.userId
    });

    ajaxCommon.getItemNoLoading({
         id: 'diCertAuthAjax'
        ,cache: false
        ,async: false
        ,url: '/auth/diCertAuthAjax.do'
        ,data: varData
        ,dataType: "json"
    }
    ,function(jsonObj){

        // 2. 본인인증 데이터 검증
        diAuthObj.resAuthOjb = jsonObj;

        if(jsonObj.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS) isCheck = true;
        else isCheck = false;
    });

    return isCheck;
}