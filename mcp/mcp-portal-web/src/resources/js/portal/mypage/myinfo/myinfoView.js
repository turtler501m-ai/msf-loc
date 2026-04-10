var pageObj = {
    niceType:""
    , applDataForm:{}
    , niceHistSeq:0
    , authObj:{}
    , authPassObj:{}
    , insrNiceHistSeq:0
    , niceHistInsrProdSeq:0
    , niceResLogObj:{}      //로그을 저장 하기 위한 인증
    , onlineAuthType : ""   //인증타입
    , onlineAuthInfo : ""   //인증값
    , cstmrType : ""        //고객타입
    , initLoadingStatus: {
        farPriceInfo : false
        , realTimePrice : false
        , addServiceCnt : false
        , farChgWay : false
        , remainMcash : false
        , remindSmsAsk : false
    }
}

$(document).ready(function (){

    // 페이지 init
    initMyInfoData();

    $(".link").click(function(){
        KTM.LoadingSpinner.show();
        var url = $(this).attr("url");
        ajaxCommon.createForm({
            method : "post"
            ,action : url
        });
        var ncn = $("#ctn option:selected").val();
        ajaxCommon.attachHiddenElement("ncn",ncn);
        ajaxCommon.formSubmit();
    });

    // 정보/광고 수신동의
    $("#mktArgBtn").click(function(){
        var parameterData = ajaxCommon.getSerializedData({
            ncn : $("#ctn option:selected").val()
        });

        openPage('pullPopupByPost','/mypage/mktAgrPop.do',parameterData, '0');
    });

    // 가입증명원 인쇄
    $("#joinCert").click(function(){
        var parameterData = ajaxCommon.getSerializedData({
            menuType : 'joinCert'
            , phoneNum : $("#ctn option:selected").text()
            , contractNum :$("#ctn option:selected").val()
        });
//		openPage('pullPopupByPost','/sms/smsAuthInfoPop.do',parameterData);
        openPage('pullPopup','/sms/smsAuthInfoPop.do',parameterData, 3);
    });

    // 가입정보 인쇄
    $("#myInfoPrint").click(function(){
        var parameterData = ajaxCommon.getSerializedData({
            ncn : $("#ctn option:selected").val()
        });

        openPage('pullPopupByPost','/mypage/myInfoPrintPop.do',parameterData);
    });

    // M쇼핑할인 서비스 해지
    $("#mcashSvcCan").click(function() {
        if (!isServiceAvailable("MCASH")) {
            return;
        }

        openPage('pullPopup', '/mcash/mcashSvcCanPop.do');
    });

    $(".loding").click(function(){
        KTM.LoadingSpinner.show();
    });


    // 리마인드 문자 수신
    var remidBtn = $("#remindBlckYn").val();
    if(remidBtn != null){
        remindSmsAsk();
    }

});

// 페이지 init
var initMyInfoData = () => {

    var varData = ajaxCommon.getSerializedData({
        ncn: $("#ctn option:selected").val()
    });

    const initPromises = [getFarPriceInfo(varData)   // 당월청구요금 조회
        , getRealTimePrice(varData)  // 실시간 요금 조회
        , getAddServiceCnt(varData)  // 부가서비스 카운트 조회
        , getFarChgWay(varData)];    // 납부방법 조회

    // M쇼핑 사용자인 경우 M쇼핑 잔액조회
    if ($("#isMcashUser").val() === "Y") {
        initPromises.push(getRemainMcash(varData));
    }

    Promise.all(initPromises);
}

// 당월 청구요금 조회
var getFarPriceInfo = (reqParam) => {
    return ajaxCommon.getItemNoLoading({
            id: 'getFarPriceInfoAjax'
            , cache: false
            , url: '/mypage/getFarPriceInfoAjax.do'
            , data: reqParam
            , dataType: "json"
        }
        , function (jsonObj) {

            pageObj.initLoadingStatus.farPriceInfo = true; // ajax  완료 처리

            if (jsonObj.RESULT_CODE === AJAX_RESULT_CODE.SUCCESS) {
                var monthPaymentInfo = jsonObj.monthPaymentDto;
                $("#billMonth").html(monthPaymentInfo.billMonth + '월 청구요금');
                $("#billPeriod").html(monthPaymentInfo.billStartDate + "~" + monthPaymentInfo.billEndDate);

                var totlaDueAmt = monthPaymentInfo.totalDueAmt || "-";
                $("#totalDueAmt").html(totlaDueAmt);

            } else {
                var billMonth = new Date().getMonth() + 1;
                $("#billMonth").html(('0' + billMonth).slice(-2) + '월 청구요금');
                $("#totalDueAmt").html("-");
                $("#billPeriod").html("");
            }

            combinePriceInfoArea();
        });
}

// 실시간 요금 조회
var getRealTimePrice = (reqParam) => {
    return ajaxCommon.getItemNoLoading({
            id: 'getRealTimePriceAjax'
            , cache: false
            , url: '/mypage/getRealTimePriceAjax.do'
            , data: reqParam
            , dataType: "json"
        }
        , function (jsonObj) {

            pageObj.initLoadingStatus.realTimePrice = true; // ajax  완료 처리

            if (jsonObj.RESULT_CODE === AJAX_RESULT_CODE.SUCCESS) {
                $("#sumAmt").html(jsonObj.sumAmt);
            } else {
                $("#sumAmt").html("-");
            }

            combinePriceInfoArea();
        });
}

var combinePriceInfoArea= () => {

    // 당월청구요금 표출 영역의 ajax(2개)가 모두 완료된 경우, 해당 영역 표출
    if(pageObj.initLoadingStatus.farPriceInfo && pageObj.initLoadingStatus.realTimePrice){
        $("#loadingMonthBillArea").hide();
        $("#monthBillArea").show();
    }else{
        $("#loadingMonthBillArea").show();
        $("#monthBillArea").hide();
    }
}

// mCash 잔액조회
var getRemainMcash = (reqParam) => {
    return ajaxCommon.getItemNoLoading({
            id: 'getRemainMcashAjax'
            , cache: false
            , url: '/mypage/getRemainMcashAjax.do'
            , data: reqParam
            , dataType: "json"
        }
        , function (jsonObj) {

            pageObj.initLoadingStatus.remainMcash= true; // ajax  완료 처리

            if (jsonObj.RESULT_CODE === AJAX_RESULT_CODE.SUCCESS) {
                $("#remainMcash").html(jsonObj.remainMcash);
            } else {
                $("#remainMcash").html("-");
            }
        });
}

// 부가서비스 카운트 조회
var getAddServiceCnt = (reqParam) => {
    return ajaxCommon.getItemNoLoading({
            id: 'getAddServiceCntAjax'
            , cache: false
            , url: '/mypage/getAddServiceCntAjax.do'
            , data: reqParam
            , dataType: "json"
        }
        , function (jsonObj) {

            pageObj.initLoadingStatus.addServiceCnt= true; // ajax  완료 처리

            if (jsonObj.RESULT_CODE === AJAX_RESULT_CODE.SUCCESS) {
                $("#payCtn").html(jsonObj.payCtn);
                $("#freeCtn").html(jsonObj.freeCtn);
            } else {
                $("#payCtn").html("-");
                $("#freeCtn").html("-");
            }
        });
}


// 납부 방법 조회
var getFarChgWay = (reqParam) => {
    return ajaxCommon.getItemNoLoading({
            id: 'getFarChgWayAjax'
            , cache: false
            , url: '/mypage/getFarChgWayAjax.do'
            , data: reqParam
            , dataType: "json"
        }
        , function (jsonObj) {

            pageObj.initLoadingStatus.farChgWay= true; // ajax  완료 처리

            $("#payArea").html("");
            var addHtml = "";
            if (jsonObj.RESULT_CODE === AJAX_RESULT_CODE.SUCCESS && isSupportedPayMethod(jsonObj.payData.payMethod)) {
                addHtml = getPayWayHtml(jsonObj.payData, jsonObj.billData);
            } else {
                addHtml += "<tr>";
                addHtml += "	<td colspan='2' class='u-ta-center u-height--80'>조회결과가 없습니다.</td>";
                addHtml += "</tr>";
            }
            $("#payArea").html(addHtml);
            $("#loadingPayArea").hide();
            $("#payArea").show();
        });
}

var isSupportedPayMethod = (payMethod) => {
    var supportedPayMethods = ["자동이체", "지로", "신용카드", "간편결제"];
    return supportedPayMethods.includes(payMethod);
}

var getPayWayHtml = (payData, billData) => {

    var payMethod = payData.payMethod;
    var blBankAcctNo = payData.blBankAcctNo;
    var prevCardNo = payData.prevCardNo;
    var prevExpirDt = payData.prevExpirDt;
    var billCycleDueDay = payData.billCycleDueDay;

    var billTypeCd = billData ? billData.billTypeCd : "-";
    var reqType = billData ? billData.reqType : "-";
    var reqTypeNm = billData ? billData.reqTypeNm : "";
    var blaAddr = billData ? billData.blaAddr : "-";

    var payTmsCd = payData.payTmsCd;

    var addHtml = "";

    if(payMethod=="자동이체"){
        addHtml += getPayMethodHtml(payMethod);
        addHtml += "<tr>";
        addHtml += "	<th>납부계정정보</th>";
        addHtml += "	<td>"+blBankAcctNo+"</td>";
        addHtml += "</tr>";
        addHtml += getReqTypeHtml(reqType);

        if(reqTypeNm != "" && billTypeCd != "MB"){
            addHtml += "<tr>";
            addHtml += "	<th>"+reqTypeNm+"</th>";
            if (billTypeCd == "LX") addHtml += "<td>" + $("#addr").val() + "</td>";
            else addHtml += "<td>"+blaAddr+"</td>";
            addHtml += "</tr>";
        }

        addHtml += "<tr>";
        addHtml += "	<th>납부기준일</th>";
        addHtml += "	<td>"+billCycleDueDay+"</td>";
        addHtml += "</tr>";

    } else if(payMethod=="지로"){
        addHtml += getPayMethodHtml(payMethod);
        addHtml += "<tr>";
        addHtml += "	<th>납부일자</th>";
        addHtml += "	<td>"+billCycleDueDay+"</td>";
        addHtml += "</tr>";
        addHtml += getReqTypeHtml(reqType);
        if(reqTypeNm != ""){
            addHtml += "<tr>";
            addHtml += "	<th>"+reqTypeNm+"</th>";
            addHtml += "	<td>"+blaAddr+"</td>";
            addHtml += "</tr>";
        }

    } else if(payMethod=="신용카드"){
        addHtml += getPayMethodHtml(payMethod);
        addHtml += "<tr>";
        addHtml += "	<th>카드번호</th>";
        addHtml += "	<td>"+prevCardNo+"</td>";
        addHtml += "</tr>";
        addHtml += "<tr>";
        addHtml += "	<th>카드만료기간</th>";
        addHtml += "	<td>"+prevExpirDt+"</td>";
        addHtml += "</tr>";
        addHtml += getReqTypeHtml(reqType);
        if(reqTypeNm != "" && billTypeCd != "MB"){
            addHtml += "<tr>";
            addHtml += "	<th>"+reqTypeNm+"</th>";
            if (billTypeCd == "LX") addHtml += "<td>" + $("#addr").val() + "</td>";
            else addHtml += "	<td>"+blaAddr+"</td>";
            addHtml += "</tr>";
        }
        addHtml += "<tr>";
        addHtml += "	<th>납부기준일</th>";
        addHtml += "	<td>"+payTmsCd+"</td>";
        addHtml += "</tr>";

    } else if(payMethod=="간편결제"){
        addHtml += getPayMethodHtml(payMethod);
        addHtml += "<tr>";
        addHtml += "	<th>납부계정정보</th>";
        addHtml += "	<td>"+billCycleDueDay+"</td>";
        addHtml += "</tr>";
        addHtml += getReqTypeHtml(reqType);

        if(reqTypeNm != ""){  // 신용카드, 자동이체와 다르게 MB(모바일)조건이 없음.. 기존로직 유지
            addHtml += "<tr>";
            addHtml += "	<th>"+reqTypeNm+"</th>";
            if (billTypeCd == "LX") addHtml += "<td>" + $("#addr").val() + "</td>";
            else addHtml += "	<td>"+blaAddr+"</td>";
            addHtml += "</tr>";
        }
    }

    return addHtml;
}

var getPayMethodHtml = (payMethod) =>
    `<tr>
        <th>납부방법</th>
        <td>
            <div class="c-button-wrap--underline c-flex--between">
                <span>${payMethod}</span>
                <button type='button' class='c-button--underline u-co-sub-4' style='line-height: 1;' onclick='javascript:location.href="/mypage/chargeView05.do"'>납부방법 변경</button>
            </div>
        </td>
    </tr>`;

var getReqTypeHtml = (reqType) =>
    `<tr>
        <th>명세서유형</th>
        <td>
            <div class="c-button-wrap--underline c-flex--between">
                <span>${reqType}</span>
                <button type='button' class='c-button--underline u-co-sub-4' style='line-height: 1;' onclick='javascript:location.href="/mypage/billWayChgView.do"'>명세서 변경</button>
            </div>
        </td>
    </tr>`;

function select(){

    ajaxCommon.createForm({
        method:"post"
        ,action:"/mypage/myinfoView.do"
    });
    var ncn = $("#ctn option:selected").val();
    ajaxCommon.attachHiddenElement("ncn",ncn);
    ajaxCommon.formSubmit();
}

function btn_reg(){
    var certifyYn = $("#certifyYn").val();
    var ncn = $("#ctn option:selected").val();
    if(certifyYn=="Y"){
//		$("#smsClosePopBtn").trigger("click");

        var ncn = $("#ctn option:selected").val();
        var parameterData = ajaxCommon.getSerializedData({
            ncn : ncn
        });
        openPage('pullPopupByPost','/mypage/joinCert.do',parameterData);

    } else {
        return false;
    }
}

// 그래프
//function arcProgress() {
//    var progressBars = document.querySelectorAll(".c-indicator-arc");
//    if (!!progressBars) {
//      progressBars.forEach(function(arcProgress) {
//        var bar = arcProgress.querySelector(".arc");
//        var val = bar.dataset.percent;
//        var perc = parseInt(val, 10);
//        var bar_text = arcProgress.querySelector(".indicator-text");
//        //ie 11 경우 체크
//        var agent = navigator.userAgent.toLowerCase();
//        if ((navigator.appName == "Netscape" && agent.indexOf("trident") != -1) || agent.indexOf("msie") != -1) {
//          // ie일 경우
//          bar.style["msTransform"] = "rotate(" + (45 + perc * 1.8) + "deg)";
//        } else {
//          // ie가 아닐 경우
//          bar.style["WebkitTransform"] = "rotate(" + (45 + perc * 1.8) + "deg)";
//        }
//
//        function showData() {
//          //알맞은 percent 값을 계산하여 해당 정보에 뿌려주세요.
//        }
//      });
//    }
//  }
//
//  document.addEventListener("DOMContentLoaded", function() {
//    arcProgress();
//  });

//가려진 정보 보기
function obsInfoView() {
    openPage('pullPopup', '/maskingPop.do','')
}

//본인인증 콜백
var fnNiceCert = function(prarObj) {

    var strMsg= "고객 정보와 본인인증한 정보가 틀립니다.";
    pageObj.niceResLogObj = prarObj;

    //본인인증
    if (pageObj.niceType == NICE_TYPE.CUST_AUTH) {

        var authInfoJson= {authType: prarObj.authType};
        var isAuthDone = mypageAuthCallback(authInfoJson);

        // 성공
        if(isAuthDone){
            MCP.alert("본인인증에 성공 하였습니다.", function(){
                unMasking(prarObj.reqSeq, prarObj.resSeq);
            });
            //실패
        }else{

            var resultCd= niceAuthObj.resAuthOjb.RESULT_CODE;
            var errorMsg= niceAuthObj.resAuthOjb.RESULT_MSG;

            if (resultCd == "LOGIN") {
                MCP.alert(errorMsg, function () {
                    location.href = '/loginForm.do';
                });
                return null;
            }

            strMsg= (errorMsg == undefined) ? strMsg : errorMsg;
            MCP.alert(strMsg);
            return null;
        }
    }
}

// 리마인드 SMS 수신  팝업
$('#remindSwitch').on('click', function(){

    var chkBtn = $(this).is(':checked');

    //차단
    if(chkBtn == false){
        var el = document.querySelector('#remindOffModal');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();
    }else{
        var el = document.querySelector('#remindOnModal');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();
    }
});


//리마인드 수신 조회
var remindSmsAsk = () => {
    return ajaxCommon.getItemNoLoading({
            id: 'remindSmsAskAjax'
            , cache: false
            , url: '/remindSmsAskAjax.do'
            , data: ''
            , dataType: "json"
        }
        , function (jsonObj) {

            pageObj.initLoadingStatus.remindSmsAsk= true; // ajax  완료 처리

            var resultCode = jsonObj.resultCode;
            var message = jsonObj.message;
            var prcsMdlInd = jsonObj.prcsMdlInd;
            if(resultCode=="1000"){
                $('#remindSwitch').prop("checked", true);
                $('#prcsMdlInd').val(prcsMdlInd);
            }else if(resultCode=="1001") {
                $('#remindSwitch').prop("checked", false);
                $('#prcsMdlInd').val(prcsMdlInd);
            }
        });
}

//리마인드 수신 on/off 동의 체크
function remindSucBtn(data) {

    var prcsMdlInd = $('#prcsMdlInd').val();

    var varData = ajaxCommon.getSerializedData({
        remindBtn : data
       ,prcsMdlInd : prcsMdlInd
    });

    ajaxCommon.getItem({
        id:'remindSmsStatAjax'
        ,cache:false
        ,url:'/remindSmsStatAjax.do'
        ,data: varData
        ,dataType:"json"
     }
     ,function(jsonObj){
         var resultCode = jsonObj.resultCode;
         var message = jsonObj.message;
         if(resultCode=="1000"){
             MCP.alert(message);
         }else if(resultCode=="1001") {
             MCP.alert(message);
         }else if(resultCode=="1002"){
             MCP.alert(message);
         }else if(resultCode=="1003"){
             MCP.alert(message);
         }else if(resultCode=="E"){
             MCP.alert(message);
         }
         return false;
     });

}

//리마인드 수신 on/off 취소 버튼
function remindCancelBtn() {

    var remindChk = $('#remindSwitch').is(':checked');

    if(remindChk == false){
        $('#remindSwitch').prop("checked", true);
    }else{
        $('#remindSwitch').prop("checked", false);
    }
}

// 회선 변경 팝업 open
function openMcashCntrChgPop() {
    if (!isServiceAvailable("MCASH")) {
        return;
    }

    ajaxCommon.getItem({
        id: 'checkMcashUserAjax',
        url: '/mcash/checkMcashUserAjax.do',
        dataType: 'json',
        cache: false
    }, function(jsonObj) {
        if (jsonObj.mcashUserYn != "Y") {
            MCP.alert("M쇼핑할인 서비스 가입 고객만 가능합니다.");
        } else {
            ajaxCommon.getItem({
                id: 'getMcashAvailableCntrList',
                url: '/mcash/getMcashAvailableCntrListAjax.do',
                dataType: 'json',
                cache: false
            }, function (jsonObj) {
                if (jsonObj.availableCntrList.length > 0) {
                    openPage('pullPopup', '/mcash/mcashCntrChgPop.do');
                } else {
                    MCP.alert("변경 가능한 회선이 없습니다.");
                }
            });
        }
    });
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

var openVirtualAccountListPop = () => {
    var parameterData = ajaxCommon.getSerializedData({
        ncn : $("#ctn option:selected").val()
    });

    openPage('pullPopupByPost', '/mypage/virtualAccountListPop.do', parameterData);
}
