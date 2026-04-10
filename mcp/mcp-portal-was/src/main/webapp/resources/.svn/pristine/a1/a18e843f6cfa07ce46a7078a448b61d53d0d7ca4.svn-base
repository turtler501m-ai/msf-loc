;

VALIDATE_NOT_EMPTY_MSG = {};
VALIDATE_NOT_EMPTY_MSG.orderName = "주문자 이름을 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.minorAgentName = "법정대리인 이름을 입력해 주세요.";

VALIDATE_NUMBER_MSG ={};
VALIDATE_NUMBER_MSG.dlvryTelFn = "배송받을 연락처가 형식에 맞지 않습니다.";
VALIDATE_NUMBER_MSG.dlvryTelMn = "배송받을 연락처가 형식에 맞지 않습니다.";
VALIDATE_NUMBER_MSG.dlvryTelRn = "배송받을 연락처가 형식에 맞지 않습니다.";

VALIDATE_FIX_MSG = {};
VALIDATE_FIX_MSG.dlvryTelFn = "배송받을 연락처가 형식에 맞지 않습니다.";
VALIDATE_FIX_MSG.dlvryTelMn = "배송받을 연락처가 형식에 맞지 않습니다.";
VALIDATE_FIX_MSG.dlvryTelRn = "배송받을 연락처가 형식에 맞지 않습니다.";


var usimBuyLimit = Number($('#usimBuyLimit1').val()); // 유심 구매 제한 수

var pageObj = {
    menuType: "usimBuy"
   ,startTime: 0
   ,niceType: ""
   ,niceTarget: "0" // 주문자 0, 대리인 1
   ,niceResLogObj: {}
   ,isNowDlvry: ""
   ,isDlvry: ""
   ,strMsg: ""
}

var reqDataForm = {
    selfDlvryIdx: 0
   ,usimProdId: ""
   ,usimUcost: ""
   ,usimPrice: ""
   ,usimAmt: ""
   ,reqBuyQnty: 0
   ,cstmrName: ""
   ,dlvryKind: ""
   ,dlvryName: ""
   ,dlvryTelFn: ""
   ,dlvryTelMn: ""
   ,dlvryTelRn: ""
   ,dlvryPost: ""
   ,dlvryAddr: ""
   ,dlvryAddrDtl: ""
   ,dlvryMemo: ""
   ,homePw: ""
   ,exitTitle: ""
   ,bizOrgCd: ""
   ,acceptTime: ""
   ,entY: ""
   ,entX: ""
   ,dlvryJibunAddr: ""
   ,dlvryJibunAddrDtl: ""
   ,minorAgentName: ""
   ,minorAgentAgrmYn: ""
}

$(window).load(function (){
    pageInit();
});

$(document).ready(function(){

    $('#dlvryMemo').val('');  // ASIS 유지

    // 고객 input 특수문자 제거
    $("#homePw, #dlvryMemo").keyup(function(){
        $(this).val($(this).val().replace(/[?&=]/gi,''));
        dlvryMemoLenChk(); // 배송 요청사항 길이 체크
    });

    // 고객 input 특수문자 제거
    $("#homePw, #dlvryMemo").blur(function(){
        $(this).val($(this).val().replace(/[?&=]/gi,''));
        dlvryMemoLenChk(); // 배송 요청사항 길이 체크
    });

    // 유심 변경 이벤트
    $("input:radio[name=usimProdId]").change(function(){

        var usimType = $(this).val();
        var dtlCdDesc = $(this).attr('data-dtlCdDesc');

        if(usimType == '01'){
            usimBuyLimit = Number($('#usimBuyLimit1').val());
            $('#reqBuyQnty').attr('max', $('#usimBuyLimit1').val());
            $('#moNfcPoss').text(dtlCdDesc);

        }else if(usimType == '02'){
            usimBuyLimit = Number($('#usimBuyLimit2').val());
            $('#reqBuyQnty').attr('max', $('#usimBuyLimit2').val());
            $('#moNfcPoss').text(dtlCdDesc);

        }else if(usimType == '03'){
            usimBuyLimit = Number($('#usimBuyLimit3').val());
            $('#reqBuyQnty').attr('max', $('#usimBuyLimit3').val());
            $('#moNfcPoss').text(dtlCdDesc);
        }

        // 수량을 1로 맞추기 위한 로직...? (ASIS 유지)
        KTM.initialize();
        for(var i=0; i<99; i++){
            if(Number($('#reqBuyQnty').val()) > 1){
                $('.minus').trigger('click');
            }
        }

        $('#usimPrice').text(numberWithCommas($(this).attr('amount')));
        $('#limitSpan').text(numberWithCommas(usimBuyLimit));


        //바로 배송 가능 여부
        var varData = ajaxCommon.getSerializedData({
            usimProdType: usimType
        });

        ajaxCommon.getItemNoLoading({
                id:'isSelfDlvry'
                ,cache:false
                ,url:'/appform/isSelfDlvryAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                if (jsonObj.IS_NOW_DLVRY) {
                    $(".dlvryKind01").show();
                    $(".dlvryKind02").hide();
                    $('#dlvryKind1').trigger('click');
                } else {
                    $(".dlvryKind01").hide();
                    $(".dlvryKind02").show();
                    $('#dlvryKind2').trigger('click');
                    var alertMsg = "";

                    if (jsonObj && jsonObj.isTimeMsg != "") {
                        alertMsg = jsonObj.isTimeMsg ;
                    }
                    if (alertMsg != "") {
                        MCP.alert(alertMsg);
                    }
                }
            });

    });

    // 유심 수량 감소 이벤트
    $('.minus').click(function(){

        var fromQnty = Number($('#reqBuyQnty').val());
        var price = Number($("input:radio[name=usimProdId]:checked").attr("amount")) || 0; // NaN 방지;
        var toQnty = (fromQnty > 1) ? fromQnty-1 : 1;

        $('#usimPrice').text(numberWithCommas(price * toQnty));
    });

    // 유심 수량 증가 이벤트
    $('.plus').click(function(){

        var fromQnty = Number($('#reqBuyQnty').val());
        var price = Number($("input:radio[name=usimProdId]:checked").attr("amount")) || 0; // NaN 방지;
        var toQnty = (fromQnty < usimBuyLimit) ? fromQnty+1 : usimBuyLimit;

        $('#usimPrice').text(numberWithCommas(price * toQnty));

        // 2개 이상의 유심 구매 시, 배송방법은 택배만 가능
        if(1 < toQnty){
            $('#dlvryKind2').trigger('click');
        }
    });

    // 휴대폰 인증 요청 이벤트
    $("#btnSmsAuth, #btnSmsMinorAgentAuth").click(function(){

        var id = $(this).attr("id");

        var nameId = "orderName";
        var ncType = "0";
        var smsAuthId = "isSmsAuth";

        if(id == "btnSmsMinorAgentAuth"){
            nameId = "minorAgentName";
            ncType = "1";
            smsAuthId = "isSmsMinorAgentAuth";
        }

        // 휴대폰 완료여부 확인
        if($("#" + smsAuthId).val() == "1") {
            MCP.alert("휴대폰 인증을 완료 하였습니다.");
            return false;
        }

        // 필수값 확인
        validator.config={};
        validator.config[nameId] = 'isNonEmpty';

        if (!validator.validate()) {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg(),function (){
                $("#"+errId).focus();
            });
            return false;
        }

        var varData = ajaxCommon.getSerializedData({
            name: $.trim($("#" + nameId).val()),
            menuType: pageObj.menuType,
            ncType: ncType
        });

        ajaxCommon.getItem({
             id:'getSmsAuthPopWithCert'
            ,cache:false
            ,url:'/auth/getSmsAuthPopWithCert.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){

            if(jsonObj.RESULT_CODE == "F_AUTH"){
              MCP.alert('<p class="u-mt--12"><strong class="u-fw--bold">회원정보와 본인인증 정보가<br>일치하지 않습니다.</strong></p><p class="u-mt--12">다른 명의로 신청을 원하실 경우<br>로그아웃 후 이용 해 주시기 바랍니다.</p>');
            }else if(jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
              MCP.alert("서비스 처리 중 오류가 발생했습니다.");
            }else{

              // 휴대폰 인증 팝업창 오픈
              pageObj.startTime = jsonObj.startTime;
              pageObj.niceType = NICE_TYPE.SMS_AUTH;
              pageObj.niceTarget = ncType;

              openPage('outLink',jsonObj.pollingPageUrl +'&mType=Mobile','');
            }
         });
    });

    // 배송유형 변경 이벤트
    $("input:radio[name=dlvryKind]").change(function(){

        var dlvryType = $(this).val();

        // 바로배송 가능 조건 확인
        if(dlvryType == '01'){
            if(!pageObj.isNowDlvry){
                if ("" == pageObj.strMsg) {
                    new KTM.Dialog(document.querySelector("#modalDirect")).open();
                } else {
                    MCP.alert(pageObj.strMsg);
                }

                $('#dlvryKind2').prop('checked', true);  // 입력한 주소는 초기화시키지 않기 위해 trigger 사용안함
                //$('#dlvryKind2').trigger('click');
                return false;
            }

            if(Number($('#reqBuyQnty').val()) > 1){
                MCP.alert('바로배송(당일 퀵)은 주문서 당<br/>한개의 유심만 구매하실 수 있습니다.', function (){
                    $('#dlvryKind2').prop('checked', true);  // 입력한 주소는 초기화시키지 않기 위해 trigger 사용안함
                    //$('#dlvryKind2').trigger('click');
                });
                return false;
            }
        }

        // 초기화
        $('#dlvryPost').val('');
        $('#dlvryAddr').val('');
        $('#dlvryAddrDtl').val('');
        $("#dlvryMemo").val('');
        $('#dlvryMemoChk').text('0/50');
        $('.dlvryType1,.dlvryType2').hide();

        if(dlvryType == '01'){ // 바로배송(당일 퀵)

            $(".dlvryKind01").show();
            $('.dlvryType2').show();

            $("._btnAddr").hide();
            $("#dlvryChk").text('바로배송(당일 퀵) 지역 조회').show();
            $(".nowArea1").show(); // 배송시 요구사항
            $(".nowArea2").show(); // 공동현관 출입방법

            // 배송시 요구사항 default 세팅
            $('#orderReqMsg').val('0');
            $('#dlvryMemoWrap').hide();
            $('#dlvryMemoWrap').find('textarea').attr('placeholder', '배송 요청사항');

            // 공동현관 출입방법 default 세팅
            $('#exitPw').val('0');
            $('#homePw').val('');
            $('.nowArea2Sub').hide();

        } else { // 택배

            $(".dlvryKind02").show();
            $('.dlvryType1').show();

            $("._btnAddr").show();
            $("#dlvryChk").hide();
            $("#bizOrgCd").val('');
            $("#psblYn").val('');

            $(".nowArea1").hide(); // 배송시 요구사항
            $(".nowArea2").hide(); // 공동현관 출입방법

            // 배송시 요구사항 default 세팅
            $('#dlvryMemoWrap').show();
            $('#dlvryMemoWrap').find('textarea').attr('placeholder', '배송 요청사항(선택)');
        }

        inputChk();
    });

    // 우편번호 찾기 클릭 이벤트
    $("._btnAddr").click(function() {
        openPage('pullPopup', '/m/addPopup.do');
    });

    // 바로배송(당일 퀵) 지역 조회 클릭 이벤트
    $("#dlvryChk").click(function() {

        var isTimeCheck = ajaxCommon.isNullNvl($("#isTimeCheck").val(),"");
        var isTimeMsg = $("#isTimeMsg").val();
        isTimeMsg = isTimeMsg.replace(/\\n/gi,'\n');

        if(isTimeCheck=="F"){
            MCP.alert(isTimeMsg, function (){
                $('#dlvryKind2').trigger('click');
            });
            return false;
        }

        var dlvryKind = $("input:radio[name=dlvryKind]:checked").val();
        var usimProdId = $("input:radio[name=usimProdId]:checked").val();
        var nfcYn = usimProdId == '01' ? 'N' : 'Y';

        if(dlvryKind != '01'){
            MCP.alert("바로(당일)배송인 경우만 필요한 서비스 입니다.");
            return false;
        }

        var now = new Date();
        var nowHH = now.getHours();

        if(nowHH < 9 || nowHH > 21){
            new KTM.Dialog(document.querySelector("#modalDirect")).open();
            $('#dlvryKind2').trigger('click');
            return false;
        }

        openPage('pullPopup', '/m/dlvryInfo.do','nfcYn='+nfcYn);
    });

    // 배송시 요구사항 변경 이벤트
    $("#orderReqMsg").change(function(){

        var orderReq = $(this).val();
        $('#dlvryMemo').val('');
        $('#dlvryMemoChk').text('0/50');
        $('#dlvryMemoWrap').find('textarea').attr('placeholder', '배송 요청사항');

        if(orderReq == "4"){
            $("#dlvryMemoWrap").show();
        } else {
            $("#dlvryMemoWrap").hide();

            var dlvryText= (orderReq == '0') ? '' : $("#orderReqMsg option:selected").text();
            $('#dlvryMemo').val(dlvryText);
            $('#dlvryMemoChk').text($('#dlvryMemo').val().length+'/50');
        }

        inputChk();
    });

    // 공동현관 출입방법
    $("#exitPw").change(function(){

        $("#homePw").val("");

        var exitPw = ajaxCommon.isNullNvl($(this).val(),"");
        if(exitPw == "1"){
            $(".nowArea2Sub").show();
        } else {
            $(".nowArea2Sub").hide();
        }

        inputChk();
    });

    // 필수약관 클릭 이벤트
    $("#chkAgree").click(function(){
        inputChk();
    });

    // 대리인 필수약관 클릭 이벤트
    $("#minorAgentAgrmYn").click(function(){
        inputChk();
    });

    // 결제 후 신청완료 버튼 클릭 이벤트
    $("#saveBtn").click(function(){

        // 1. 필수 입력값 검사
        var chkResult = inputChk();
        if(chkResult.RESULT_CODE != 'S'){
            MCP.alert(chkResult.RESULT_MSG);
            return false;
        }

        if(Number($('#reqBuyQnty').val()) > Number(usimBuyLimit)){
            MCP.alert('유심 최대 구매 수량을 초과하였습니다.');
            return false;
        }

        validator.config={};
        validator.config['dlvryTelFn'] = 'isNumBetterFixN2';
        validator.config['dlvryTelMn'] = 'isNumBetterFixN3';
        validator.config['dlvryTelRn'] = 'isNumFix4';

        if (!validator.validate()) {
            MCP.alert(validator.getErrorMsg());
            return false;
        }

        // 2. 추가 유효성 검사
        var dlvryKind = $("input:radio[name=dlvryKind]:checked").val();
        var usimAmt = $("input:radio[name=usimProdId]:checked").attr("amount");
        var usimProdId = $("input:radio[name=usimProdId]:checked").val();
        var bizOrgCd = ajaxCommon.isNullNvl($("#bizOrgCd").val(),"");
        var psblYn = $("#psblYn").val();

        var dlvryMemo = "";
        var exitTitle = "";
        var homePw = "";
        var exitPwVal = "";

        if(dlvryKind == "01"){

            if(psblYn != "Y"){
                MCP.alert("바로배송(당일 퀵) 가능 지역 여부를 먼저 확인해 주세요.", function (){
                    $("#dlvryChk").focus();
                });
                return false;
            }

            if(bizOrgCd == ""){
                MCP.alert("배달업체를 다시 조회해 주세요.", function (){
                    $("#dlvryChk").focus();
                });
                return false;
            }

            // 배송요청사항
            var dlvryMemoType = $("#orderReqMsg").val();
            if(dlvryMemoType == "0"){
                dlvryMemo = ''
            }else if(dlvryMemoType == "4"){
                dlvryMemo = $("#dlvryMemo").val();
            }else{
                dlvryMemo = $("#orderReqMsg option:selected").text();
            }

            // 공동현관 출입방법
            exitTitle = ajaxCommon.isNullNvl($("#exitPw option:selected").text(),"");
            exitPwVal = ajaxCommon.isNullNvl($("#exitPw option:selected").val(),"");

            if(exitPwVal == "1"){
                homePw = ajaxCommon.isNullNvl($("#homePw").val(),"");
            }

        } else {
            dlvryMemo = $("#dlvryMemo").val(); // 배송요청사항
        }

        // 3. 값 세팅
        reqDataForm.usimProdId = usimProdId;
        reqDataForm.usimUcost = usimAmt;
        reqDataForm.reqBuyQnty = $('#reqBuyQnty').val();
        reqDataForm.usimPrice = Number(usimAmt) * Number($('#reqBuyQnty').val());
        reqDataForm.usimAmt = Number(usimAmt) * Number($('#reqBuyQnty').val());
        reqDataForm.cstmrName = $.trim($("#orderName").val());
        reqDataForm.dlvryKind = dlvryKind;
        reqDataForm.dlvryName = $.trim($("#dlvryName").val());
        reqDataForm.dlvryTelFn = $.trim($('#dlvryTelFn').val());
        reqDataForm.dlvryTelMn = $.trim($('#dlvryTelMn').val());
        reqDataForm.dlvryTelRn = $.trim($('#dlvryTelRn').val());
        reqDataForm.dlvryPost = $.trim($("#dlvryPost").val());
        reqDataForm.dlvryAddr = $.trim($("#dlvryAddr").val());
        reqDataForm.dlvryAddrDtl = $.trim($("#dlvryAddrDtl").val());
        reqDataForm.dlvryMemo = $.trim(dlvryMemo).replace(/[?&=]/gi,' ');
        reqDataForm.homePw = $.trim(homePw).replace(/[?&=]/gi,' ');
        reqDataForm.exitTitle = exitTitle;
        reqDataForm.selfDlvryIdx  = ajaxCommon.isNullNvl($.trim($("#selfDlvryIdx").val()),"0");
        reqDataForm.bizOrgCd = bizOrgCd;
        reqDataForm.acceptTime = $("#acceptTime").val();
        reqDataForm.entY = $("#entY").val();
        reqDataForm.entX = $("#entX").val();
        reqDataForm.dlvryJibunAddr = $("#dlvryJibunAddr").val();
        reqDataForm.dlvryJibunAddrDtl = $("#dlvryJibunAddrDtl").val();
        reqDataForm.minorAgentName = $.trim($("#minorAgentName").val());
        reqDataForm.minorAgentAgrmYn = $("#minorAgentAgrmYn").is(':checked') ? "Y" : "N";

        var varData = ajaxCommon.getSerializedData(reqDataForm);

        ajaxCommon.getItem({
                id:'saveSelfDlvryAjax'
                ,cache:false
                ,url:"/m/appForm/saveSelfDlvryAjax.do"
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){

                var alertMsg = "";
                var selfDlvryIdx = ajaxCommon.isNullNvl($.trim($("#selfDlvryIdx").val()),"0");
                var buyerTel = $.trim($("#dlvryTelFn").val()) + $.trim($("#dlvryTelMn").val()) + $.trim($("#dlvryTelRn").val());
                var dlvryKindVal = jsonObj.dlvryKind;
                var mallReserved = "";

                if( jsonObj.RESULT_CODE == "DONE" ){
                    location.href= jsonObj.completeUrl + "?selfDlvryIdx=" + selfDlvryIdx;
                    return false;

                }else if(jsonObj.RESULT_CODE == "TIMECHECK"){

                    // 기존문구
                    alertMsg = "추석 연휴 기간동안(18일~22일) 바로배송(당일 퀵)의 신청이 일시 중단됩니다.<br>가까운 편의점에서 유심을 구매하시면 혜택이 더 좋습니다.";
                    // 공통코드 DESC가 있다면 해당 문구로 변경
                    alertMsg = jsonObj.isTimeMsg || alertMsg;

                    MCP.alert(alertMsg);
                    return false;

                }else if(jsonObj.RESULT_CODE == "TIME"){
                    new KTM.Dialog(document.querySelector("#modalDirect")).open();
                    $('#dlvryKind2').trigger('click');
                    return false;

                }else if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {

                    selfDlvryIdx= jsonObj.selfDlvryIdx;
                    $("#selfDlvryIdx").val(selfDlvryIdx);

                    //SMARTRO 결제 시작
                    var params={
                        buyType: 'DIRECT_MALL_USIM'
                       ,prodCd: usimProdId
                       ,reqBuyQnty	: $.trim($('#reqBuyQnty').val())
                    }

                    jQuery.ajaxSettings.traditional = true;

                    $.ajax({
                        url : '/m/smartro/payInit.do'
                        ,type : 'POST'
                        ,data : params
                        ,async : false
                        ,cache : false
                        ,success: function(data, textStatus, jqXHR)
                        {
                            if(data.resltCd == '0000'){
                                if($('#PayMethod').val() == undefined || $('#PayMethod').val() == ''){
                                    $('#PayMethod').val(data.PayMethod);
                                }

                                $('#GoodsCnt').val(data.reqBuyQnty);
                                $('#Amt').val(data.Amt);
                                $('#TaxAmt').val("");
                                $('#TaxFreeAmt').val("");
                                $('#VatAmt').val("");

                                $('#Moid').val(selfDlvryIdx);
                                $('#Mid').val(data.Mid);
                                $('#ReturnUrl').val(data.ReturnUrl);
                                $('#StopUrl').val(data.StopUrl);
                                $('#BuyerName').val($.trim($("#orderName").val()));
                                $('#BuyerTel').val(buyerTel);
                                $('#BuyerEmail').val($('#userEmail').val());
                                $('#MallIP').val(data.MallIP);
                                $('#EncryptData').val(data.EncryptData);
                                $('#EdiDate').val(data.ediDate);
                                $('#mode').val(data.mode);

                                var menu = 'DIRECTMALL';
                                var dlvryKindNm = 'SelfDlvry';
                                if(dlvryKindVal == "01"){
                                    dlvryKindNm = "NowDlvry";
                                }

                                $('#GoodsName').val(data.GoodsName);
                                if(dlvryKindNm == 'SelfDlvry'){
                                    $('#GoodsName').val(data.GoodsName + '[택배]');
                                }else if(dlvryKindNm == 'NowDlvry'){
                                    $('#GoodsName').val(data.GoodsName + '[바로배송]');
                                }

                                //상점 예비 필드 - 메뉴,구매타입,상품코드
                                mallReserved = menu;
                                mallReserved += ',' + dlvryKindNm;
                                mallReserved += ',' + usimProdId;

                                $('#MallReserved').val(mallReserved);
                                goPay();

                            }else{
                                alertMsg = data.msg || "처리중 오류가 발생했습니다. 잠시후 다시 시도해 주세요.";
                                MCP.alert(alertMsg);
                            }
                        }
                        ,error: function (jqXHR, textStatus, errorThrown)
                        {
                            MCP.alert("처리중 오류가 발생했습니다. 다시 시도해 주세요." + " : " + jqXHR + " : " + textStatus + " : " + errorThrown);
                        }
                    });

                }else {
                    alertMsg = "처리중 오류가 발생했습니다. 잠시후 다시 시도해 주세요.";
                    alertMsg = jsonObj.RESULT_MSG || alertMsg;

                    MCP.alert(alertMsg);
                    return false;
                }
            });
    });

}); // end of document.ready -------------------------


// 페이지 init
var pageInit = function(){

    // 유심유형 default 세팅
    $('input[name=usimProdId]').each(function (){
          var indcOdrg = $(this).attr('data-indcOdrg');
          var dtlCdDesc = $(this).attr('data-dtlCdDesc');
        if(indcOdrg == '1'){
            $(this).prop('checked', true);
            //$('#usimPrice').text(numberWithCommas($(this).attr('amount')));
            var amount = $(this).attr('amount');
            var parsedAmount = amount ? Number(amount) : 0; // NaN 방지 처리
            $('#moNfcPoss').text(dtlCdDesc);

            if (!isNaN(parsedAmount)) {
                $('#usimPrice').text(numberWithCommas(parsedAmount));
            } else {
                $('#usimPrice').text('0'); // NaN 방지
            }
        }
    });

    // 배송유형 초기세팅
    initDlvryType();
}

// 본인인증 결과 확인
var fnNiceCert = function(prarObj) {

    pageObj.niceResLogObj = prarObj;

    if(prarObj.resSeq == undefined || prarObj.resSeq == "") {
        MCP.alert("휴대폰 인증에 실패 하였습니다.");
        return null;
    }

    var infoObj = {name: $.trim($("#orderName").val())
                  ,menuType: pageObj.menuType
                  ,ncType: pageObj.niceTarget}

    if(pageObj.niceTarget == "1"){
        infoObj.name = $.trim($("#minorAgentName").val());
    }

    var certResult = checkSmsAuthInfoWithCert(infoObj);
    var underAgeYn = certResult.UNDER_AGE_YN;

    if(certResult.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS){

        if(pageObj.niceTarget == "0"){ // 주문자 핸드폰 인증
            $("#isSmsAuth").val("1");
            $('#orderName').prop('readonly', 'readonly');
            $('#btnSmsAuth').addClass('is-complete').html("휴대폰 인증 완료");

            if(underAgeYn == "Y") $("#minorAgentWrap").show();
            else $("#minorAgentWrap").hide();

        }else{ // 대리인 핸드폰 인증
            $("#isSmsMinorAgentAuth").val("1");
            $('#minorAgentName').prop('readonly', 'readonly');
            $('#btnSmsMinorAgentAuth').addClass('is-complete').html("휴대폰 인증 완료");
        }

        inputChk();

    }else if(certResult.RESULT_CODE == "F_LOGIN"){
        MCP.alert('<p class="u-mt--12"><strong class="u-fw--bold">회원정보와 본인인증 정보가<br>일치하지 않습니다.</strong></p><p class="u-mt--12">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</p>');
    }else if(certResult.RESULT_CODE == "F_AUTH_AGNT"){
        MCP.alert('<p class="u-mt--12"><strong class="u-fw--bold">본인인증 정보가 만 19세 미만입니다.</strong></p><p class="u-mt--12">법정대리인은 만 19세 이상 성인이어야 합니다.</p>');
    }else{
        MCP.alert('<p class="u-mt--12"><strong class="u-fw--bold">본인인증 정보가 일치하지 않습니다.</strong></p><p class="u-mt--12">입력하신 정보를 확인 후 다시 시도해 주시기 바랍니다.</p>');
    }

}

// 약관 상세보기
function btnRegDtl(param){
    openPage('termsPop','/termsPop.do', param);
}

// 약관 동의 후 닫기
function targetTermsAgree() {
    $('#chkAgree').prop('checked', 'checked');
    inputChk();
}

// 포커스 이동
function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

// 배송유형 초기세팅
function initDlvryType(){

    ajaxCommon.getItemNoLoading({
        id:'isSelfDlvryAjax'
        ,cache:false
        ,url:'/appform/isSelfDlvryAjax.do'
        ,data: ""
        ,dataType:"json"
        ,async:false
    },function(jsonObj){
        pageObj.isNowDlvry = jsonObj.IS_NOW_DLVRY;
        pageObj.isDlvry = jsonObj.IS_DLVRY;
        pageObj.strMsg = jsonObj.STR_MSG;
    });

    // 바로배송 무조건 표현
    $(".dlvryKind01").show();

    if (pageObj.isDlvry) {
        $(".dlvryKind02").show();
    } else {
        $(".dlvryKind02").hide();
    }

    if (pageObj.isNowDlvry) {
        $('#dlvryKind1').trigger('click');
    } else {
        $('#dlvryKind2').trigger('click');
    }

}

// 주소 setting
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
    $("#dlvryPost").val(zipNo);
    $("#dlvryAddr").val(roadAddrPart1);
    $("#dlvryAddrDtl").val(roadAddrPart2 + " " +addrDetail);
    inputChk();
}

// 바로배송(당일 퀵) 추가
function dlvryJusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,jibunAddr, zipNo, psblYn,bizOrgCd,acceptTime,entY,entX) {

    entY = ajaxCommon.isNullNvl(entY,""); // 위도
    entX = ajaxCommon.isNullNvl(entX,""); // 경도

    if(psblYn=="Y"){
        $("#dlvryKind1").prop("checked",true);
        $("#dlvryChk").show();
        $("._btnAddr").hide();
    } else {

        // 택배배송으로 초기화
        $("#dlvryChk").hide();
        $("._btnAddr").show();
        $("#entY").val("");
        $("#entX").val("");
        $("#dlvryMemo").val('');
        $('#dlvryMemoChk').text('0/50');
        $('.dlvryType1,.dlvryType2').hide();

        $(".dlvryKind02").show();
        $("#dlvryKind2").prop("checked",true);
        $('.dlvryType1').show();

        $(".nowArea1").hide(); // 배송시 요구사항
        $(".nowArea2").hide(); // 공동현관 출입방법
        $('#dlvryMemoWrap').show();
        $('#dlvryMemoWrap').find('textarea').attr('placeholder', '배송 요청사항(선택)');
    }

    addrDetail = addrDetail.replace(/[?&=]/gi,' ');

    $("#dlvryPost").val(zipNo);
    $("#dlvryAddr").val(roadAddrPart1);
    $("#dlvryAddrDtl").val(roadAddrPart2 + " " +addrDetail);
    $("#bizOrgCd").val(bizOrgCd);
    $("#psblYn").val(psblYn);
    $("#acceptTime").val(acceptTime);
    $("#dlvryJibunAddr").val(jibunAddr);
    $("#dlvryJibunAddrDtl").val(addrDetail);
    $("#entY").val(entY);
    $("#entX").val(entX);
    inputChk();
}

// 배송 요청사항 입력길이 제한
var dlvryMemoLenChk = function (){

    var content = $('#dlvryMemo').val();
    if(content.length > 50){
        content = content.substring(0, 50);
    }

    $('#dlvryMemo').val(content);
    $('#dlvryMemoChk').text(content.length +"/50");
}

// 필수 입력값 체크
function inputChk(){

    var chkResult = {RESULT_CODE: 'F' , RESULT_MSG : '필수값이 누락되었습니다.'}

    // 1) 유심 선택
    var usimType = $('input[name=usimProdId]:checked').val();
    if(usimType == undefined || usimType == ''){
        $("#saveBtn").addClass('is-disabled');
        chkResult.RESULT_MSG = "유심 종류를 선택해주세요.";
        return chkResult;
    }

    // 2) 구매 수량
    var buyCnt = Number($('#reqBuyQnty').val());
    if(buyCnt < 1){
        $("#saveBtn").addClass('is-disabled');
        chkResult.RESULT_MSG = "1개 이상의 구매수량을 입력해주세요.";
        return chkResult;
    }

    // 3) 주문자 정보
    var orderName = $.trim($("#orderName").val());
    if(orderName == '' || $("#isSmsAuth").val() != "1"){
        $("#saveBtn").addClass('is-disabled');
        chkResult.RESULT_MSG = "주문자 휴대폰 인증을 완료해주세요.";
        return chkResult;
    }

    // 법정대리인 정보
    if($("#minorAgentWrap").css("display") != "none"){

        if(!$("#minorAgentAgrmYn").is(':checked')){
            $("#saveBtn").addClass('is-disabled');
            chkResult.RESULT_MSG = "법정대리인 필수동의를 확인해주세요.";
            return chkResult;
        }

        var minorAgentName = $.trim($("#minorAgentName").val());
        if(minorAgentName == '' || $("#isSmsMinorAgentAuth").val() != "1"){
            $("#saveBtn").addClass('is-disabled');
            chkResult.RESULT_MSG = "법정대리인 휴대폰 인증을 완료해주세요.";
            return chkResult;
        }
    }

    // 4) 배송유형
    var dlvryKind = $('input[name=dlvryKind]:checked').val();
    if(dlvryKind == undefined || dlvryKind == ''){
        $("#saveBtn").addClass('is-disabled');
        chkResult.RESULT_MSG = "배송 유형을 선택해주세요.";
        return chkResult;
    }

    // 5) 받는분
    var dlvryName = $.trim($("#dlvryName").val());
    var dlvryTelFn = $.trim($('#dlvryTelFn').val());
    var dlvryTelMn = $.trim($('#dlvryTelMn').val());
    var dlvryTelRn = $.trim($('#dlvryTelRn').val());

    if(dlvryName == '' || dlvryTelFn == '' ||  dlvryTelMn == '' || dlvryTelRn == ''){
        $("#saveBtn").addClass('is-disabled');
        chkResult.RESULT_MSG = "받는 분 정보(이름/연락처)를 입력해주세요.";
        return chkResult;
    }

    // 6) 배송지
    var dlvryPost = $.trim($('#dlvryPost').val());
    var dlvryAddr = $.trim($('#dlvryAddr').val());
    var dlvryAddrDtl = $.trim($('#dlvryAddrDtl').val());

    if(dlvryPost == '' || dlvryAddr == '' || dlvryAddrDtl == ''){
        $("#saveBtn").addClass('is-disabled');
        chkResult.RESULT_MSG = "배송지 정보를 입력해주세요.";
        return chkResult;
    }

    // 7) 배송요청사항은 선택. 바로배송이면서 배송요청사항 option을 선택한 경우, 배송 메시지 필수
    var dlvryMemoType = $("#orderReqMsg option:selected").val();
    var dlvryMemo = $.trim($("#dlvryMemo").val());

    if(dlvryKind == '01' && dlvryMemoType != '0' && dlvryMemo == ''){
        $("#saveBtn").addClass('is-disabled');
        chkResult.RESULT_MSG = "배송 요청사항을 입력해주세요.";
        return chkResult;
    }

    // 8) 공동현관 출입방법은 선택. 바로배송인 경우 필수 (방법 중 비밀번호를 선택한 경우, 비밀번호 입력 필수)
    var exitType = $("#exitPw option:selected").val();
    var homePw = $.trim($('#homePw').val());

    if(dlvryKind == '01'){
        if(exitType == undefined || exitType == ''){
            $("#saveBtn").addClass('is-disabled');
            chkResult.RESULT_MSG = "공동현관 출입방법을 선택해주세요.";
            return chkResult;
        }

        if(exitType == '1' && homePw == ''){
            $("#saveBtn").addClass('is-disabled');
            chkResult.RESULT_MSG = "공동현관 출입방법 상세 내용을 입력해주세요.";
            return chkResult;
        }
    }

    // 9) 약관 동의
    if($('#chkAgree:checked').length <= 0){
        $("#saveBtn").addClass('is-disabled');
        chkResult.RESULT_MSG = "필수 약관에 동의해주세요.";
        return chkResult;
    }

    $("#saveBtn").removeClass('is-disabled');
    chkResult.RESULT_CODE = "S";
    chkResult.RESULT_MSG = "input chk 성공";
    return chkResult;

}

// 결제창 호출
function goPay() {

    // 스마트로페이 초기화
    smartropay.init({
        mode: $('#mode').val()
    });

    // 스마트로페이 결제요청 (모바일용)
    smartropay.payment({
        FormId : 'tranMgr'
    });
};
