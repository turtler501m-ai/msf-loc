﻿history.pushState(null, null,location.href);
    window.onpopstate = function (event){
        var newForm = $('<form></form>');
        newForm.attr('name','newForm');
        newForm.attr('method','post');
        newForm.attr('action','/m/appForm/reqSelfDlvry.do');
        newForm.appendTo('body');
        newForm.submit();
}

var pageObj = {
    niceType:""
    , authObj:{}
    , niceHistSeq:0
    , startTime:0
    , onlineAuthType: ''
    , niceResLogObj:{}   //로그을 저장 하기 위한 인증
}

$(document).ready(function (){

    $('#checkAll').on('click', function (){
        if ($(this).is(':checked')) {
            $("input:checkbox[name=terms]").prop("checked", "checked");
        }else{
            $("input:checkbox[name=terms]").prop("checked", "");
        }
        setChkbox();
    });


    // 본인인증 전 추가 유효성 검사 > niceAuth.js 참고
    simpleAuthvalidation= function(){
         validator.config={};
         validator.config['cstmrName'] = 'isNonEmpty';
         validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
         if (validator.validate()) { // 유효성검사 통과 시
            return true;
        }else{
            // 유효성 검사 불충족시 팝업 표출
             var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg(),function (){
               $selectObj = $("#"+errId);
               viewAuthErrorMgs($selectObj, validator.getErrorMsg());
               $('#' + validator.id).focus(); // 포커스
            });
            return false;
        }
    };

    $("#btnSmsAuth").click(function(){
        if ($("#isSmsAuth").val() == "1") {
            MCP.alert("휴대폰 인증을 완료 하였습니다. ");
            return false;
        }
        ajaxCommon.getItem({
            id:'getTimeInMillisAjax'
            ,cache:false
            ,url:'/nice/getTimeInMillisAjax.do'
            ,data: ""
            ,dataType:"json"
            ,async:false
        }
        ,function(startTime){
            pageObj.startTime = startTime ;
        });

        //sAuthType M: 핸드폰, C: 신용카드, X: 공인인증서  isTeen isJimin
        openPage('outLink','/nice/popNiceAuth.do?sAuthType=M&mType=Mobile','')
        pageObj.niceType = NICE_TYPE.SMS_AUTH ;
        return ;
    });




    $('#btnAutAccount').click(function(){
        validator.config={};
        validator.config['isPassAuth'] = 'isNonEmpty';
        validator.config['reqBankAut'] = 'isNonEmpty';
        validator.config['reqAccountAut'] = 'isNonEmpty';

        cstmrName = $.trim($("#cstmrName").val());

       if (validator.validate()) {
           var varData = ajaxCommon.getSerializedData({
               service:"2"
               ,svcGbn:"2"
               ,bankCode:$("#reqBankAut").val()
               ,accountNo:$.trim($("#reqAccountAut").val())
               ,name:cstmrName
           });

          ajaxCommon.getItem({
               id:'niceAccountOtpNameAjax'
               ,cache:false
               ,url:'/nice/niceAccountOtpNameAjax.do'
               ,data: varData
               ,dataType:"json"
           }


           ,function(jsonObj){

               if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                   /* 이부분이 HIDDEN으로 존재해야 합니다.  */
                   $("#requestNo").val(jsonObj.request_no);
                   $("#resUniqId").val(jsonObj.res_uniq_id);
                   $("#btnAutAccount").hide();
                   $("#divBankAut1").hide();
                   $("#divBankAut2").show();

              } else {
                  MCP.alert("입력하신 계좌 정보로 인증이 불가합니다. 입력 정보를 확인 후 다시 시도해 주시기 바랍니다.");
              }
           }
        );

       } else {
           MCP.alert(validator.getErrorMsg());
       }

    });


    $('#btnCheckAccountOtpConfirm').click(function(){
        validator.config={};
        validator.config['isPassAuth'] = 'isNonEmpty';
        validator.config['reqBankAut'] = 'isNonEmpty';
        validator.config['reqAccountAut'] = 'isNonEmpty';
        validator.config['requestNo'] = 'isNonEmpty';
        validator.config['resUniqId'] = 'isNonEmpty';
        validator.config['textOpt'] = 'isNumFix6';

        cstmrName = $.trim($("#cstmrName").val());

       if (validator.validate()) {
           var varData = ajaxCommon.getSerializedData({
               bankCode:$("#reqBankAut").val()
               ,accountNo:$.trim($("#reqAccountAut").val())
               ,name:cstmrName
               ,requestNo:$.trim($("#requestNo").val())
               ,resUniqId:$.trim($("#resUniqId").val())
               ,otp:$.trim($("#textOpt").val())
           });

          ajaxCommon.getItem({
               id:'niceAccountOtpNameAjax'
               ,cache:false
               ,url:'/nice/niceAccountOtpConfirmAjax.do'
               ,data: varData
               ,dataType:"json"
           }
           ,function(jsonObj){
               if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    pageObj.niceResLogObj.authType = "A"; //PASS인증으로 저장
                    $("#isAuth").val("1");
                    $('#cstmrName').prop('readonly', 'readonly');
                    $('#rrnDiv').addClass('is-readonly');
                    $('#cstmrNativeRrn01').prop('readonly', 'readonly');
                    $('#cstmrNativeRrn02').prop('readonly', 'readonly');
                    $("#divBankAut2").hide();
                    $("#divBankAut3").show();
                    $('#btnNiceAuth4').text('2단계: 계좌인증 완료').addClass('is-complete');

                    var agreeCnt = 0;
                    $('input[name=terms]').each(function (){
                        if($(this).attr('data-mand-yn') == 'Y' && $(this).is(':checked')){
                            agreeCnt++;
                        }
                    });

                    if($('input[name=terms][data-mand-yn=Y]').length == agreeCnt){
                        $('#saveBtn').removeClass('is-disabled');
                    }

              } else {
                  MCP.alert("인증번호가 일치하지 않습니다.");
               }
           });

       } else {
           MCP.alert(validator.getErrorMsg());
       }
    });

    $('input[name=onlineAuthType]').on('change', function (){
        $('#isAuth').val('');
        $("#isPassAuth").val('');
        $("#isSmsAuth").val('');
        // $('#btnNiceAuth2').text('1단계: PASS인증');
        // $('#btnNiceAuth,#btnNiceAuth2,#btnNiceAuth4,#btnNiceAuth5, #btnNiceAuth6').removeClass('is-complete');
        $('._btnNiceAuth').removeClass('is-complete');
    });
});



function fnNiceCert(prarObj) {
    var cstmrNativeRrn01;
    var cstmrName ;
    var strMsg ;

    pageObj.niceResLogObj = prarObj;

    //본인인증
    if (pageObj.niceType == NICE_TYPE.CUST_AUTH) {
        cstmrNativeRrn01 = $("#cstmrNativeRrn01").val();
        cstmrName = $.trim($("#cstmrName").val());
        strMsg = "고객 정보와 본인인증한 정보가 틀립니다." ;

        var authInfoJson= {cstmrName: cstmrName , cstmrNativeRrn: cstmrNativeRrn01, authType:prarObj.authType};
        var isAuthDone= authCallback(authInfoJson);

        if(isAuthDone){ // 성공

            reqDataForm.onlineAuthType = prarObj.authType;
            pageObj.onlineAuthType = prarObj.authType;
            pageObj.authObj= prarObj;

            $('#cstmrName').prop('readonly', 'readonly');
            $('#rrnDiv').addClass('is-readonly');
            $('#cstmrNativeRrn01').prop('readonly', 'readonly');
            $('#cstmrNativeRrn02').prop('readonly', 'readonly');
            $('#cstmrNativeRrn01').prop('disabled', 'disabled');
            $('#cstmrNativeRrn02').prop('disabled', 'disabled');

            var agreeCnt = 0;
            $('input[name=terms]').each(function (){
                if($(this).attr('data-mand-yn') == 'Y' && $(this).is(':checked')){
                    agreeCnt++;
                }
            });

            if($('input[name=terms][data-mand-yn=Y]').length == agreeCnt){
                $('#saveBtn').removeClass('is-disabled');
            }

            MCP.alert("본인인증에 성공 하였습니다.");
            return null;

        }else{
            MCP.alert(strMsg);
            return null;
        }
    }
}

function setChkbox() {
    var cnt = 0;
    $('input[name=terms]').each(function (){
        if($(this).prop('checked')){
            cnt++;
        }
    });

    if(cnt == $('input[name=terms]').length){
        $('#checkAll').prop('checked', 'checked');
    }else{
        $('#checkAll').removeProp('checked');
    }

    var agreeCnt = 0;
    $('input[name=terms]').each(function (){
        if($(this).attr('data-mand-yn') == 'Y' && $(this).is(':checked')){
            agreeCnt++;
        }
    });

    if($('input[name=terms][data-mand-yn=Y]').length == agreeCnt && $("#isAuth").val() == "1"){
        $('#saveBtn').removeClass('is-disabled');
    }else{
        if(!$('#saveBtn').hasClass('is-disabled')){
            $('#saveBtn').addClass('is-disabled');
        }
    }

}

function viewTerms(targetId, param) {
    $('#targetTerms').val(targetId);
    openPage('termsPop','/termsPop.do',param);
}

function targetTermsAgree() {
    $('#' + $('#targetTerms').val()).prop('checked', 'checked');
    setChkbox();
}


var reqDataForm = {
    selfDlvryIdx:0
    , usimProdId:""
    , cstmrName:""
    , cstmrNativeRrn:""
    , dlvryName:""
    , dlvryTelFn:""
    , dlvryTelMn:""
    , dlvryTelRn:""
    , dlvryPost:""
    , dlvryAddr:""
    , dlvryAddrDtl:""
    , dlvryMemo:""
    , bizOrgCd:""
    , dlvryKind:""
    , usimAmt:""
    , acceptTime:""
    , entY : ""
    , entX : ""
    , dlvryJibunAddr : ""
    , dlvryJibunAddrDtl : ""
    , homePw : ""
    , exitTitle : ""
    , onlineAuthType : ""
    , reqBuyQnty: 0
    , usimUcost : ""
    , usimPrice : ""

}

var btnSave = function (){
    validator.config['cstmrName'] = 'isNonEmpty';
    validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
    validator.config['isAuth'] = 'isNonEmpty';

    if(validator.validate()){
        var agreeCnt = 0;
        $('input[name=terms]').each(function (){
            if(agreeCnt == 0 && $(this).attr('data-mand-yn') == 'Y' && !$(this).is(':checked')){
                agreeCnt++;
                MCP.alert('필수 이용약관을 모두 동의하셔야 합니다.', function (){
                    $(this).focus();
                });
                return false;
            }
        });

        if(agreeCnt > 0){
            return false;
        }

        var completeUrl = $('#completeUrl').val();

/*        if($.trim($('#dlvryKind').val()) =="01" && $.trim($('#usimProdId').val()) !="01"){
            MCP.alert("바로배송(당일 퀵)은 일반 유심만 제공됩니다.");
            return false;
        }*/

        reqDataForm.usimProdId = $.trim($('#usimProdId').val());
        reqDataForm.cstmrName = $.trim($("#cstmrName").val());
        reqDataForm.cstmrNativeRrn = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;
        reqDataForm.dlvryName = $.trim($("#dlvryName").val());
        reqDataForm.dlvryTelFn = $.trim($("#dlvryTelFn").val());
        reqDataForm.dlvryTelMn = $.trim($("#dlvryTelMn").val());
        reqDataForm.dlvryTelRn = $.trim($("#dlvryTelRn").val());
        reqDataForm.dlvryPost = $.trim($("#dlvryPost").val());
        reqDataForm.dlvryAddr = $.trim($("#dlvryAddr").val());
        reqDataForm.dlvryAddrDtl = $.trim($("#dlvryAddrDtl").val());
        reqDataForm.dlvryMemo = $.trim($('#dlvryMemo').val()).replace(/[?&=]/gi,' ');
        reqDataForm.selfDlvryIdx  = ajaxCommon.isNullNvl($.trim($("#selfDlvryIdx").val()),"0");
        reqDataForm.bizOrgCd = $.trim($('#bizOrgCd').val());
        reqDataForm.dlvryKind = $.trim($('#dlvryKind').val());
        reqDataForm.usimAmt = $.trim($('#usimAmt').val());
        reqDataForm.acceptTime = $("#acceptTime").val();
        // 지번주소 변경에 따른 필드 추가건
        reqDataForm.entY = $("#entY").val();
        reqDataForm.entX = $("#entX").val();
        reqDataForm.dlvryJibunAddr = $("#dlvryJibunAddr").val();
        reqDataForm.dlvryJibunAddrDtl = $("#dlvryJibunAddrDtl").val();
        reqDataForm.homePw = $.trim($('#homePw').val()).replace(/[?&=]/gi,' ');
        reqDataForm.exitTitle = $.trim($('#exitTitle').val());
        reqDataForm.reqBuyQnty = $.trim($('#reqBuyQnty').val());
        reqDataForm.usimUcost = $.trim($('#usimUcost').val());
        reqDataForm.usimPrice = $.trim($('#usimAmt').val());

        var varData = ajaxCommon.getSerializedData(reqDataForm);
        ajaxCommon.getItem({
            id:'saveSelfDlvryAjax'
            ,cache:false
            ,url:"/m/appForm/saveSelfDlvryAjax.do"
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){

            var mallReserved = "";
            var dlvryKindVal = jsonObj.dlvryKind;
            var selfDlvryIdx = jsonObj.selfDlvryIdx;
            var buyerTel = $.trim($("#dlvryTelFn").val()) + $.trim($("#dlvryTelMn").val()) + $.trim($("#dlvryTelRn").val());
            completeUrl = completeUrl+"?selfDlvryIdx="+selfDlvryIdx;

            if( jsonObj.RESULT_CODE=="DONE" ){
                location.href= completeUrl;
                return false;

            } else if(jsonObj.RESULT_CODE=="TIMECHECK"){
                MCP.alert("추석 연휴 기간동안(18일~22일) 바로배송(당일 퀵)의 신청이 일시 중단됩니다.\n 가까운 편의점에서 유심을 구매하시면 혜택이 더 좋습니다.");
                return false;

            } else if(jsonObj.RESULT_CODE=="TIME"){
                new KTM.Dialog(document.querySelector("#modalDirect")).open();
                $('#dlvryKind2').trigger('click');
                return false;

            }  else if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                $("#selfDlvryIdx").val(selfDlvryIdx);

                //SMARTRO 결제 시작
                var params={
                    buyType		: 'DIRECT_MALL_USIM'
                    , prodCd: $.trim($('#usimProdId').val())
                    , reqBuyQnty	: $.trim($('#reqBuyQnty').val())
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


                            //$('#TaxAmt').val(data.TaxAmt);
                            //$('#TaxFreeAmt').val('0');
                            //$('#VatAmt').val(data.VatAmt);
                            $('#TaxAmt').val("");
                            $('#TaxFreeAmt').val("");
                            $('#VatAmt').val("");

                            $('#Moid').val(selfDlvryIdx);
                            $('#Mid').val(data.Mid);
                            $('#ReturnUrl').val(data.ReturnUrl);
                            $('#StopUrl').val(data.StopUrl);
                            $('#BuyerName').val($.trim($("#cstmrName").val()));
                            $('#BuyerTel').val(buyerTel);
                            $('#BuyerEmail').val($('#userEmail').val());
                            $('#MallIP').val(data.MallIP);
                            $('#EncryptData').val(data.EncryptData);
                            $('#EdiDate').val(data.ediDate);
                            $('#mode').val(data.mode);

                            var menu = 'DIRECTMALL';
                            var dlvryKind = 'SelfDlvry';
                            if(dlvryKindVal == "01"){
                                dlvryKind = "NowDlvry";
                            }

                            $('#GoodsName').val(data.GoodsName);
                            if(dlvryKind == 'SelfDlvry'){
                                $('#GoodsName').val(data.GoodsName + '[택배]');
                            }else if(dlvryKind == 'NowDlvry'){
                                $('#GoodsName').val(data.GoodsName + '[바로배송]');
                            }


                            var usimProdId = $.trim($('#usimProdId').val());

                            //상점 예비 필드 - 메뉴,구매타입,상품코드
                            mallReserved = menu;
                            mallReserved += ',' + dlvryKind;
                            mallReserved += ',' + usimProdId;

                            $('#MallReserved').val(mallReserved);

                            goPay();
                        }else{
                            MCP.alert(data.msg);
                        }
                    }
                    ,error: function (jqXHR, textStatus, errorThrown)
                    {
                        MCP.alert("처리중 오류가 발생했습니다. 다시 시도해 주세요." + " : " + jqXHR + " : " + textStatus + " : " + errorThrown);
                    }
                });

            } else if (jsonObj.RESULT_CODE == "0003") {
                $("#btnSave1").show();
                MCP.alert("본인인증 한 정보가 다릅니다. 다시 본인인증을 하여 주시기 바랍니다.");
            } else if(jsonObj.RESULT_CODE == "STEP01" || jsonObj.RESULT_CODE == "STEP02"){ // STEP 오류
                $("#btnSave1").show();
                MCP.alert(jsonObj.RESULT_MSG);
            } else {
                $("#btnSave1").show();
                MCP.alert("등록에 실패 하였습니다.");
            }
        });


    }else{
        MCP.alert(validator.getErrorMsg(), function (){
            $('#' + validator.id).focus();
        });
        return false;
    }
}

function goPay() {
    // 스마트로페이 초기화
    smartropay.init({
        mode: $('#mode').val()		// STG: 테스트, REAL: 운영
    });

    // 스마트로페이 결제요청
    // 모바일용
    smartropay.payment({
        FormId : 'tranMgr'				// 폼ID
    });
};

function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

function accountChk (){
    if($.trim($('#reqAccountAut').val()) != '' && $('#reqBankAut').val() != ''){
        $('#btnAutAccount').removeClass('is-disabled');
    }else{
        $('#btnAutAccount').addClass('is-disabled');
    }
}

function certInit (){
    $('#isAuth').val('');
    $("#isPassAuth").val('');
    $("#isSmsAuth").val('');
    $('._onlineAuthType').hide();
    $('input[name=onlineAuthType]').removeProp('checked');
}

/*
window.addEventListener('message', function(e) {
    if (e.origin === document.location.origin) {
    // e.data를 사용한 동작 수행
      if(e.data.resultCode == '3001' || e.data.resultCode == 'KP00' || e.data.resultCode == 'NP00'){
          document.forms.targetForm.submit();
      }
  }
});
*/