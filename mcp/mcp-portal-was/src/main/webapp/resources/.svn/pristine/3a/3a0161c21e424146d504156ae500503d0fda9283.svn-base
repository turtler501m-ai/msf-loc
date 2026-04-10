var pageObj = {
    sbscYnCode:"Y"
    ,engtPerd:"24"
}


VALIDATE_NOT_EMPTY_MSG = {};
VALIDATE_NOT_EMPTY_MSG.rePlcyClauseFlag = "재약정에 내용을 확인하시고 동의하여 주시기 바랍니다. ";
VALIDATE_NOT_EMPTY_MSG.certifyYn = "인증번호 전송후 시도 하여 주시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.authValue = "인증번호를 입력하여 주시기 바랍니다. ";
VALIDATE_NOT_EMPTY_MSG.isAuth = "휴대폰인증 하여 주시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.prizeType01 = "사은품을 선택 하여 주시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.dlvryPost = "배송지 우편번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.dlvryName = "받으시는 분 이름을  입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.dlvryMobileMn = "배송 받을 휴대전화 중간자리번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.dlvryMobileRn = "배송 받을 휴대전화 뒷자리번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.isDlvrySele = "배송지 주소를 설정 하시기 바랍니다. ";


VALIDATE_NUMBER_MSG ={};
VALIDATE_NUMBER_MSG.dlvryMobileMn = "배송 받을 휴대전화 번호 중간자리를 입력해 주세요.";
VALIDATE_NUMBER_MSG.dlvryMobileRn= "배송 받을 휴대전화 번호 뒷자리를 입력해 주세요.";

VALIDATE_FIX_MSG={};
VALIDATE_FIX_MSG.dlvryMobileMn = "배송 받을 휴대전화 번호 중간자리를 입력해 주세요.";
VALIDATE_FIX_MSG.dlvryMobileRn= "배송 받을 휴대전화 번호 뒷자리를 입력해 주세요.";

var clauseScroll;

$(document).ready(function() {
    /*
    clauseScroll = new iScroll('clauseContents',{hideScrollbar:true,
        onBeforeScrollStart: function (e) {
            var target = e.target;
            while (target.nodeType != 1) target = target.parentNode;
            if (target.tagName != 'SELECT' && target.tagName != 'INPUT' && target.tagName != 'TEXTAREA' && target.tagName != 'BUTTON' && target.tagName != 'LABEL')
                e.preventDefault();
        }
    });
    */
    $('#jusoFrame').css('width', $(window).width() - 25);
    $('#jusoFrame').css('height', $(window).height() - 50);

    var zeroRemoveMinu = function(input) {
        if (input == 0) {
            return input;
        } else {
            return "-" + numberWithCommas(input);
        }
    };

    //약관정보 패치
    var getFormDesc = function(cdGroupId1,cdGroupId2) {
        var varData = ajaxCommon.getSerializedData({
            cdGroupId1:cdGroupId1
            ,cdGroupId2:cdGroupId2
        });
        var rtnObj;
        ajaxCommon.getItem({
            id:'getFormDesc'
            ,cache:false
            ,async:false
            ,url:'/getFormDescAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(jsonObj){
            rtnObj = jsonObj ;
        });

        return rtnObj ;
    }

    $('#ctn').change(function(){
        ajaxCommon.createForm({
            method:"post"
            ,action:"/m/mypage/reSpnsrPlcyDc.do"
         });

        ajaxCommon.attachHiddenElement("ncn",$(this).val());
        ajaxCommon.formSubmit();
    });

    $('#btnViewPlcyDcInfo').click(function(){
        var formDtlDTO = getFormDesc("FormEtcCla","spnsrPlcyDcInfo");
        if (formDtlDTO != null) {
            $("#layerDcInfo .popup_title").html(formDtlDTO.formNm);
            $("#layerDcInfo ._content").html(formDtlDTO.docContent);
            setTimeout(function(){
                fn_layerPop('layerDcInfo',750,850);
            }, 100);
        }
    });


    $('#btnSmsCert').click(function(){
        validator.config={};
        validator.config['rePlcyClauseFlag'] = 'isNonEmpty';

        if ("1"== $("#isAuth").val()) {
            alert("휴대폰 인증을 완료 하였습니다. ");
            return false;
        }

        if (validator.validate()) {
            fn_mobile_layerOpen('smslayer');
        } else {
            alert(validator.getErrorMsg());
        }

    });

    $("#btnReqAutNo").click(function(){
        var varData = ajaxCommon.getSerializedData({
            mCode:menuCode
            ,ncn:$("#ctn").val()
        });

        ajaxCommon.getItem({
            id:'btnReqAutNo'
            ,cache:false
            ,url:'/mypage/sendCertSmsAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            if(jsonObj.returnCode == "00") {
                alert("인증 SMS가 전송되었습니다.");
                $("#btnReqAutNo").html("인증번호 재전송");
                $("#certifyYn").val("Y");
                $("#authValue").val(jsonObj.message);
            } else {
                alert(jsonObj.message);
            }
        });
    });

    $("#btnAutNoCheck").click(function(){
        validator.config={};
        validator.config['certifyYn'] = 'isNonEmpty';
        validator.config['authValue'] = 'isNonEmpty';

        if (validator.validate()) {
            var varData = ajaxCommon.getSerializedData({
                mCode:menuCode
                ,ncn:$("#ctn").val()
                ,checkValue:$.trim($("#authValue").val())
            });

            ajaxCommon.getItem({
                id:'btnReqAutNo'
                ,cache:false
                ,url:"/mypage/checkCertSmsAjax.do"
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                if(jsonObj.returnCode == "00") {
                    $("#isAuth").val("1");
                    $(".popup_cancel").trigger("click");
                } else {
                    alert(jsonObj.message);
                }
            });
        } else {
            alert(validator.getErrorMsg());
        }
    });


    $("#btnReqReg").click(function(){
        validator.config={};
        validator.config['rePlcyClauseFlag'] = 'isNonEmpty';
        validator.config['isAuth'] = 'isNonEmpty';
        validator.config['prizeType01'] = 'isNonEmpty';


        if ( "N" !=  $(':radio[name="prizeType"]:checked').attr("isDlvry") ) {
            validator.config['isDlvrySele'] = 'isNonEmpty';
            validator.config['dlvryName'] = 'isNonEmpty';
            validator.config['dlvryMobileMn'] = 'isNumBetterFixN3';
            validator.config['dlvryMobileRn'] = 'isNumFix4';
            validator.config['dlvryPost'] = 'isNonEmpty';
        }


        if (validator.validate()) {
            var varData = ajaxCommon.getSerializedData({
                mCode:menuCode
                , ncn:$("#ctn").val()
                , engtPerd: pageObj.engtPerd
                , dlvryName:$.trim($("#dlvryName").val())
                , dlvryMobileFn:$("#dlvryMobileFn").val()
                , dlvryMobileMn:$.trim($("#dlvryMobileMn").val())
                , dlvryMobileRn:$.trim($("#dlvryMobileRn").val())
                , dlvryPost:$("#dlvryPost").val()
                , dlvryAddr:$.trim($("#dlvryAddr").val())
                , dlvryAddrDtl:$.trim($("#dlvryAddrDtl").val())
                , presentCode:$(':radio[name="prizeType"]:checked').val()
            });

            ajaxCommon.getItem({
                id:'btnReqAutNo'
                ,cache:false
                ,url:'/mypage/moscSdsSvcChgAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    ajaxCommon.createForm({
                        method:"post"
                        ,action:"/mypage/reSpnsrPlcyDcComplete.do"
                     });

                    ajaxCommon.attachHiddenElement("ncn",$("#ctn option:selected").val());
                    ajaxCommon.formSubmit();
                } else {
                    alert(jsonObj.RESULT_MSG);
                }
            });
        } else {
            alert(validator.getErrorMsg());
        }


    });


    $('#btnSvcPreChk').on('click',function(){

        var varData = ajaxCommon.getSerializedData({
            ncn:$("#ctn option:selected").val()
        });
        pageObj.engtPerd = $('input:radio[name=instNom]:checked').val();
        ajaxCommon.getItem({
            id:'formSvcPreChk'
            ,cache:false
            ,url:'/mypage/moscSdsSvcPreChkAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            if ("00000" ==  jsonObj.RESULT_CODE) {
                if (pageObj.sbscYnCode ==  jsonObj.SBSC_YN) {
                    //심플할인 금액 조회
                    var varData2 = ajaxCommon.getSerializedData({
                        ncn:$("#ctn").val()
                        , rateSoc:$("#rateSoc").val()
                        , engtPerd: pageObj.engtPerd
                    });

                    ajaxCommon.getItem({
                        id:'btnReqAutNo'
                        ,cache:false
                        ,url:'/mypage/getReSpnsrPriceInfoAjax.do'
                        ,data: varData2
                        ,dataType:"json"
                        ,async:false
                    }
                    ,function(jsonObj){
                        if (jsonObj.mspRateMst != null && jsonObj.mspRateMst.baseVatAmt) {
                            $("#divResult_td_one").html(numberWithCommas(jsonObj.mspRateMst.baseVatAmt) +"원");
                        } else {
                            $("#divResult_td_one").html("9,900원");
                        }

                        if (jsonObj.saleSubsdMst != null && jsonObj.saleSubsdMst.totalVatPriceDC != null && jsonObj.saleSubsdMst.totalVatPriceDC > 0) {
                            var totalVatPrice = jsonObj.saleSubsdMst.totalVatPriceDC ;
                            $("#divResult_td_two").html(zeroRemoveMinu(jsonObj.saleSubsdMst.totalVatPriceDC) +"원");
                            $("#divResult_td_three").html(numberWithCommas(jsonObj.saleSubsdMst.totalVatPrice) +"원");
                            $("#divResult_td_four").html(numberWithCommas(jsonObj.saleSubsdMst.totalVatPriceDC*pageObj.engtPerd) +"원");
                            //재약정 가능
                            $("#divResult").show();
                            $("#divResultBtnArea").show();
                            $("#divNoResult").hide();
                            $("#divNoResultBtnArea").hide();
                             $('#svcBtn').trigger('click');

                        } else {
                            var tempHtml = "<tr><td scope='col' class='text_align_center padding_top_20' >요금할인 재약정이 불가능합니다.<br/><br/>재약정 대상 요금제가 아닙니다.<br/> 고객센터(1899-5000) 통하여 재약정 문의가 가능합니다. </td></tr>"
                            $("#divNoResult").html(tempHtml);
                            $("#divResult").hide();
                            $("#divResultBtnArea").hide();
                            $("#divNoResult").show();
                            $("#divNoResultBtnArea").show();
                        }
                    });

                } else {

                    var resultMsg = jsonObj.RESULT_MSG;
                    if (resultMsg ==null || resultMsg =="" ) {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상 요금제가 아닙니다.<br/> 고객센터(1899-5000)를 통하여 재약정 문의가 가능합니다.";
                    } else if (resultMsg == "스폰서 고객이므로 심플할인(알뜰폰)에 가입할 수 없습니다." ) {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상자가 아닙니다.";
                    } else if (resultMsg == "심플할인(알뜰폰) 가입 고객입니다. 수동해지 가능합니다." ) {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정에 이미 가입되어 있습니다.";
                    } else if (resultMsg == "정지 중 고객이므로 심플할인(알뜰폰)에 가입할 수 없습니다." ) {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>정지 상태에서는 가입이 불가합니다.";
                    } else {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상 요금제가 아닙니다. <br/>고객센터(1899-5000)를 통하여 재약정 문의가 가능합니다.";
                    }
                    MCP.alert(resultMsg);

                    var tempHtml = "<tr><td scope='col' class='text_align_center padding_top_20' >"+resultMsg+"</td></tr>"
                    $("#divNoResult").html(tempHtml);
                    $("#divResult").hide();
                    $("#divResultBtnArea").hide();
                    $("#divNoResult").show();
                    $("#divNoResultBtnArea").show();
                }
                //$("#btnSvcPreChk").hide();
                //$("#btnReSvcPreChk").show();
                //$("#trInstnom2 td").eq(0).html(pageObj.engtPerd + " 개월");
                //$("#trInstnom1").hide();
                //$("#trInstnom2").show();
            }  else if ("00002" ==  jsonObj.RESULT_CODE) {

                var resultMsg = jsonObj.RESULT_MSG;
                if (resultMsg ==null || resultMsg =="" ) {
                    resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상 요금제가 아닙니다.<br/> 고객센터(1899-5000)를 통하여 재약정 문의가 가능합니다.";
                } else {
                    resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>" + resultMsg ;
                }

                var tempHtml = "<tr><td scope='col' class='text_align_center padding_top_20' >"+resultMsg+"</td></tr>"
                $("#divNoResult").html(tempHtml);
                $("#divResult").hide();
                $("#divResultBtnArea").hide();
                $("#divNoResult").show();
                $("#divNoResultBtnArea").show();

            } else if ("00001" ==  jsonObj.RESULT_CODE) {
                MCP.alert("동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다. ");
            } else {
                MCP.alert("약정 가능 여부 조회 실패 하였습니다. \n잠시후 다시 시도 하시기 바랍니다. ");
            }
        });
    });

    $('#btnDeliveryAdr').click(function(){
        fn_mobile_layerOpen('layerDeliveryAddrForm');
    });


    $('#btnDeliveryAddrChange').click(function(){
        fn_layerPop('layerDeliveryAddrForm',750,850);
    });

    $('#btnAddr').click(function(){
        $('#jusoFrame').attr('src', '/addrPop.do');
        fn_mobile_layerOpen('layerAddr');
    });


    $('#btnDeliveryAddrSave').click(function(){
        validator.config={};
        validator.config['dlvryName'] = 'isNonEmpty';
        validator.config['dlvryMobileMn'] = 'isNumBetterFixN3';
        validator.config['dlvryMobileRn'] = 'isNumFix4';
        validator.config['dlvryPost'] = 'isNonEmpty';

        if (validator.validate()) {
            $("#isDlvrySele").val("1");
            $(".popup_cancel").trigger("click");
        } else {
            alert(validator.getErrorMsg());
        }
    });


    $("input:radio[name=instNom]").click(function(){
        var thisVal = $(this).val();
        if (pageObj.engtPerd != thisVal) {

            var tempHtml = "<tr><td>약정 기간을 선택하시고,<br/>약정 가능 여부 조회 버튼을 클릭하시면 자동 계산됩니다. </td></tr>"
            $("#tbodyId").html(tempHtml);
            $("#divResult").hide();
            $("#divIntro").show();
            $("#divPlcyDcInfo").hide();
            $("#btnSvcPreChk").show();
            $("#btnReSvcPreChk").hide();
            $("#trInstnom1").show();
            $("#trInstnom2").hide();
        }
    }) ;


    $("#btnReSvcPreChk").click(function(){
        var tempHtml = "<tr><td>약정 기간을 선택하시고,<br/>약정 가능 여부 조회 버튼을 클릭하시면 자동 계산됩니다. </td></tr>"
        $("#tbodyId").html(tempHtml);
        $("#divResult").hide();
        $("#divIntro").show();
        $("#divPlcyDcInfo").hide();
        $("#btnSvcPreChk").show();
        $("#btnReSvcPreChk").hide();
        $("#trInstnom1").show();
        $("#trInstnom2").hide();
    }) ;


    $("#btnViewPlcyClause").click(function(){

        var formDtlDTO = getFormDesc("FormEtcCla","rePlcyClause");
        if (formDtlDTO != null) {
            $("#clauseDetail ._title").html(formDtlDTO.formNm);
            $("#clauseDetail ._detail").html(formDtlDTO.docContent);
            var table_width = $("#clauseDetail ._detail").find('table').css('width');
            $("#clauseDetail ._detail").css('width',table_width);
            $('#clauseContents').css('height', $(window).height()-100);

            fn_mobile_layerOpen('clauseDetail', clauseScroll);
            clauseScroll.refresh();
        }
    }) ;

});

/* 주소 setting */
    function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
        $("#inpAdressNum").val(zipNo);
        $("#inpAdress1").val(roadAddrPart1);
        $("#inpAddress2").val(roadAddrPart2 + " " +addrDetail);
    }


function chgChk(obj){
    if($(obj).is(":checked")){
        $("input[name='chkSelectBasicService']").prop("checked",false);
        $(obj).prop("checked",true);
        if($(obj).data("isdlvry") == 'Y'){
            $("#divInpCsArea").show();

            setInputFilter(document.getElementById("inpOrdererTel1"), function(value) {
              return /^\d*\.?\d*$/.test(value);
            });

            setInputFilter(document.getElementById("inpOrdererTel2"), function(value) {
              return /^\d*\.?\d*$/.test(value);
            });

            setInputFilter(document.getElementById("inpOrdererTel3"), function(value) {
              return /^\d*\.?\d*$/.test(value);
            });


        }else{

            $("#inpOrdererName").val('');
            $("#inpOrdererTel1").val('');
            $("#inpOrdererTel2").val('');
            $("#inpOrdererTel3").val('');
            $("#inpAdressNum").val('');
            $("#inpAdress1").val('');
            $("#inpAddress2").val('');
            $("#divInpCsArea").hide();
        }
    }else{
        $("input[name='chkSelectBasicService']").prop("checked",false);
        $("#inpOrdererName").val('');
        $("#inpOrdererTel1").val('');
        $("#inpOrdererTel2").val('');
        $("#inpOrdererTel3").val('');
        $("#inpAdressNum").val('');
        $("#inpAdress1").val('');
        $("#inpAddress2").val('');
        $("#divInpCsArea").hide();
    }

}

//약관동의
function btnRegDtl(param){
    $('#targetTerms').val('Y');
    openPage('termsPop','/termsPop.do',param);
}

function targetTermsAgree(){

    $("#chkStepAgreeAll").prop("checked",true);
    $("#chkStepAgree1").prop("checked",true);
    $("#main-content > div.c-row.c-row--lg > div.c-button-wrap.u-mt--56 > button").prop("disabled",false);

}

function phoneNumChk(){

    if($('#inpOrdererTel1').val().trim() != '' && $('#inpOrdererTel1').val().length >= 3){
        $("#inpOrdererTel2").focus();
    }

    if($('#inpOrdererTel2').val().trim() != '' && $('#inpOrdererTel2').val().length >= 4){
        $("#inpOrdererTel3").focus();
    }

}

function agreeChk(obj){

    if($(obj).is(":checked")){
        $("#chkStepAgreeAll").prop("checked",true);
        $("#chkStepAgree1").prop("checked",true);
    }else{
        $("#chkStepAgreeAll").prop("checked",false);
        $("#chkStepAgree1").prop("checked",false);
    }

    if(!$("#chkStepAgreeAll").prop("checked") || !$("#chkStepAgree1").prop("checked")){
        $("#main-content > div.c-row.c-row--lg > div.c-button-wrap.u-mt--56 > button").prop("disabled",true);
    }else{
        $("#main-content > div.c-row.c-row--lg > div.c-button-wrap.u-mt--56 > button").prop("disabled",false);
    }

}

function authSms(){

    if($("input[name='chkSelectBasicService']:checked").length <= 0){
        MCP.alert("사은품을 선택 하여 주시기 바랍니다.");
        $("input[name='chkSelectBasicService']").eq(0).focus();
        return;
    }

    if(!$("input[name='chkStepAgree']").is(":checked")){
        MCP.alert("재약정에 내용을 확인하시고 동의하여 주시기 바랍니다. ");
        return;
    }

    if(!$("#chkStepAgree1").is(":checked")){
        MCP.alert("요금할인 재약정을 위한 필수 확인사항에 동의하여 주시기 바랍니다.");
        return;
    }

    if ("Y" ==  $("input[name='chkSelectBasicService']:checked").data("isdlvry") ) {
        if($("#inpOrdererName").val() == ''){
            $("#inpOrdererName").focus();
            MCP.alert("받으시는 분 이름을  입력해 주세요.");
            return;
        }
        if($("#inpOrdererTel1").val() == '' || $("#inpOrdererTel1").val().length < 3){
            $("#inpOrdererTel1").focus();
            MCP.alert("배송 받을 휴대전화 번호 앞자리를 입력해 주세요.");
            return;
        }
        if($("#inpOrdererTel2").val() == '' || $("#inpOrdererTel2").val().length < 4){
            $("#inpOrdererTel2").focus();
            MCP.alert("배송 받을 휴대전화 번호 중간자리를 입력해 주세요.");
            return;
        }
        if($("#inpOrdererTel3").val() == ''  || $("#inpOrdererTel3").val().length < 4){
            $("#inpOrdererTel3").focus();
            MCP.alert("배송 받을 휴대전화 번호를 뒷자리를 입력해 주세요.");
            return;
        }
        if($("#inpAdressNum").val() == ''){
            $("#inpAdressNum").focus();
            MCP.alert("배송지 우편번호를 입력해 주세요.");
            return;
        }
        if($("#inpAdress1").val() == ''){
            $("#inpAdress1").focus();
            MCP.alert("배송지 주소를 설정 하시기 바랍니다. ");
            return;
        }
        if($("#inpAddress2").val() == ''){
            $("#inpAddress2").focus();
            MCP.alert("배송지 주소를 설정 하시기 바랍니다. ");
            return;
        }

    }

        var parameterData = ajaxCommon.getSerializedData({
             menuType	 : 'reRate'
            ,phoneNum 	 : $("#hPhoneNum").val()
        });
        openPage('pullPopupByPost','/sms/smsAuthInfoPop.do',parameterData);
    }

function btn_reg(){
        $("#pullModalContents > div > div > div.c-modal__header > button").click();
        goComplete();
    }

function goComplete(){

    if($("input[name='chkSelectBasicService']:checked").length <= 0){
        MCP.alert("사은품을 선택 하여 주시기 바랍니다.");
        $("input[name='chkSelectBasicService']").eq(0).focus();
        return;
    }

    if(!$("input[name='chkStepAgree']").is(":checked")){
        MCP.alert("재약정에 내용을 확인하시고 동의하여 주시기 바랍니다. ");
        return;
    }

    if(!$("#chkStepAgree1").is(":checked")){
        MCP.alert("요금할인 재약정을 위한 필수 확인사항에 동의하여 주시기 바랍니다.");
        return;
    }

    if ( "Y" ==  $("input[name='chkSelectBasicService']:checked").data("isdlvry") ) {
        if($("#inpOrdererName").val() == ''){
            $("#inpOrdererName").focus();
            MCP.alert("받으시는 분 이름을  입력해 주세요.");
            return;
        }
        if($("#inpOrdererTel1").val() == '' || $("#inpOrdererTel1").val().length < 3){
            $("#inpOrdererTel1").focus();
            MCP.alert("배송 받을 휴대전화 번호 앞자리를 입력해 주세요.");
            return;
        }
        if($("#inpOrdererTel2").val() == '' || $("#inpOrdererTel2").val().length < 4){
            $("#inpOrdererTel2").focus();
            MCP.alert("배송 받을 휴대전화 번호 중간자리를 입력해 주세요.");
            return;
        }
        if($("#inpOrdererTel3").val() == ''  || $("#inpOrdererTel3").val().length < 4){
            $("#inpOrdererTel3").focus();
            MCP.alert("배송 받을 휴대전화 번호를 뒷자리를 입력해 주세요.");
            return;
        }
        if($("#inpAdressNum").val() == ''){
            $("#inpAdressNum").focus();
            MCP.alert("배송지 우편번호를 입력해 주세요.");
            return;
        }
        if($("#inpAdress1").val() == ''){
            $("#inpAdress1").focus();
            MCP.alert("배송지 주소를 설정 하시기 바랍니다. ");
            return;
        }
        if($("#inpAddress2").val() == ''){
            $("#inpAddress2").focus();
            MCP.alert("배송지 주소를 설정 하시기 바랍니다. ");
            return;
        }

    }



        var varData = ajaxCommon.getSerializedData({
            mCode:'reRate'
            , ncn:$("#ncn").val()
            , ctn:$("#hPhoneNum").val()
            , engtPerd: $("#engtPerd").val()
            , dlvryName:$.trim($("#inpOrdererName").val())
            , dlvryMobileFn:$("#inpOrdererTel1").val()
            , dlvryMobileMn:$.trim($("#inpOrdererTel2").val())
            , dlvryMobileRn:$.trim($("#inpOrdererTel3").val())
            , dlvryPost:$("#inpAdressNum").val()
            , dlvryAddr:$.trim($("#inpAdress1").val())
            , dlvryAddrDtl:$.trim($("#inpAddress2").val())
            , presentCode:$("input[name='chkSelectBasicService']:checked").val()
        });

        ajaxCommon.getItem({
            id:'btnReqAutNo'
            ,cache:false
            ,url:'/mypage/moscSdsSvcChgAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            if ('00000' ==  jsonObj.RESULT_CODE) {
                ajaxCommon.createForm({
                    method:"post"
                    ,action:"/mypage/reSpnsrPlcyDcComplete.do"
                 });

                ajaxCommon.attachHiddenElement("ncn",$("#ncn").val());
                ajaxCommon.formSubmit();
            } else {
                alert(jsonObj.RESULT_MSG);
            }
        });
    }



function setInputFilter(textbox, inputFilter) {
  ["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop"].forEach(function(event) {
    textbox.addEventListener(event, function() {
      if (inputFilter(this.value)) {
        this.oldValue = this.value;
        this.oldSelectionStart = this.selectionStart;
        this.oldSelectionEnd = this.selectionEnd;
      } else if (this.hasOwnProperty("oldValue")) {
        this.value = this.oldValue;
        this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
      } else {
        this.value = "";
      }
    });
  });
}

