



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
                        ,action:"/m/mypage/reSpnsrPlcyDcComplete.do"
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
        $("#srchRstHArea").show();
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
            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
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
                            $("#divResult dd").eq(0).html(numberWithCommas(jsonObj.mspRateMst.baseVatAmt) +"원");
                        } else {
                            $("#divResult dd").eq(0).html("9,900원");
                        }


                        if (jsonObj.saleSubsdMst != null && jsonObj.saleSubsdMst.totalVatPriceDC != null && jsonObj.saleSubsdMst.totalVatPriceDC > 0) {
                            var totalVatPrice = jsonObj.saleSubsdMst.totalVatPriceDC ;
                            $("#divResult dd").eq(1).html(zeroRemoveMinu(jsonObj.saleSubsdMst.totalVatPriceDC) +"원");
                            $("#divResult dd").eq(2).html(numberWithCommas(jsonObj.saleSubsdMst.totalVatPrice) +"원");
                            $("#divResult dd").eq(3).html(numberWithCommas(jsonObj.saleSubsdMst.totalVatPriceDC*pageObj.engtPerd) +"원");
                            //재약정 가능
                            $("#divResult").show();
                            $("#divIntro").hide();
                             $('#svcBtn').trigger('click');


                            /*$('#divPrize').css('height', $("#imgPrizeType01").height() * ulSize + 50);*/

                        } else {
                            /*
                            var tempHtml = "<tr><td scope='col' class='text_align_center padding_top_20' >요금할인 재약정이 불가능합니다.<br/><br/>재약정 대상 요금제가 아닙니다.<br/> 고객센터(가입휴대폰에서 114) 통하여 재약정 문의가 가능합니다. </td></tr>"
                            $("#tbodyId").html(tempHtml);
                            $("#divPlcyDcInfo").show();
                            $("#srchRstHArea").hide();
                            */
                            var resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상 요금제가 아닙니다. <br/>고객센터(가입휴대폰에서 114)를 통하여 재약정 문의가 가능합니다.";
                            MCP.alert(resultMsg);
                        }
                    });

                } else {

                    var resultMsg = jsonObj.RESULT_MSG;
                    if (resultMsg ==null || resultMsg =="" ) {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상 요금제가 아닙니다.<br/> 고객센터(가입휴대폰에서 114)를 통하여 재약정 문의가 가능합니다.";
                    } else if (resultMsg == "스폰서 고객이므로 심플할인(알뜰폰)에 가입할 수 없습니다." ) {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상자가 아닙니다.";
                    } else if (resultMsg == "심플할인(알뜰폰) 가입 고객입니다. 수동해지 가능합니다." ) {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정에 이미 가입되어 있습니다.";
                    } else if (resultMsg == "정지 중 고객이므로 심플할인(알뜰폰)에 가입할 수 없습니다." ) {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>정지 상태에서는 가입이 불가합니다.";
                    } else {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상 요금제가 아닙니다. <br/>고객센터(가입휴대폰에서 114)를 통하여 재약정 문의가 가능합니다.";
                    }
                    MCP.alert(resultMsg);

                }
                //$("#btnSvcPreChk").hide();
                //$("#btnReSvcPreChk").show();
                $("#trInstnom2 td").eq(0).html(pageObj.engtPerd + " 개월");
                $("#trInstnom1").hide();
                $("#trInstnom2").show();
            }  else if ("00002" ==  jsonObj.RESULT_CODE) {

                var resultMsg = jsonObj.RESULT_MSG;
                if (resultMsg ==null || resultMsg =="" ) {
                    resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상 요금제가 아닙니다.<br/> 고객센터(가입휴대폰에서 114)를 통하여 재약정 문의가 가능합니다.";
                } else {
                    //resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>" + resultMsg ;
                    resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>";
                }
                MCP.alert(resultMsg);
                /*
                var tempHtml = "<tr><td scope='col' class='text_align_center padding_top_20' >"+resultMsg+"</td></tr>"
                $("#tbodyId").html(tempHtml);
                $("#divPlcyDcInfo").show();
                $("#srchRstHArea").hide();
                */
            } else if ("00001" ==  jsonObj.RESULT_CODE) {
                MCP.alert("동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다. ");
            } else {
                MCP.alert("요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상 요금제가 아닙니다.<br/> 고객센터(가입휴대폰에서 114)를 통하여 재약정 문의가 가능합니다. ");
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






})


/* 주소 setting */
    function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
        $("#dlvryPost").val(zipNo);
        $("#dlvryAddr").val(roadAddrPart1);
        $("#dlvryAddrDtl").val(roadAddrPart2 + " " +addrDetail);
        fn_mobile_layerClose("layerAddr");
        //fn_mobile_layerOpen('layerDeliveryAddrForm');

    }

function select() {
    ajaxCommon.createForm({
        method: "post"
       ,action: "/m/mypage/reSpnsrPlcyDc.do"
    });

    ajaxCommon.attachHiddenElement("ncn", $("#ctn").val());
    ajaxCommon.formSubmit();
}


function goMosc(){
    document.frm.svcCntrNo.value = $("#ctn").val();
    document.frm.engtPerd.value = $("input:radio[name=instNom]:checked").val();
    document.frm.submit();
}


