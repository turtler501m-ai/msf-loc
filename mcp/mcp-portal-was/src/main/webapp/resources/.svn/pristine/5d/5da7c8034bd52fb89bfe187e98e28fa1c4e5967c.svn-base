


VALIDATE_NOT_EMPTY_MSG = {};
// 공통
VALIDATE_NOT_EMPTY_MSG.chkPreAgree = "신청 전 필수 확인 사항을 <br/>확인해 주세요 ";
VALIDATE_NOT_EMPTY_MSG.sameCustYn01 = "결합 명의를 선택해 주세요.";
VALIDATE_NOT_EMPTY_MSG.svcNoTypeCd01 = "무선 결합 , 유선 결합 중에 선택 하세요 ";

// 공통 약관
VALIDATE_NOT_EMPTY_MSG.chkAgree = "결합을 위한 필수사항에 동의하시기 바랍니다. ";
VALIDATE_NOT_EMPTY_MSG.chkAgree2 = "개인정보 수집 및 이용에 동의하시기 바랍니다. ";
VALIDATE_NOT_EMPTY_MSG.chkAgree3 = "개인정보 제 3자 정보제공에 동의하시기 바랍니다. ";
VALIDATE_NOT_EMPTY_MSG.chkAgree4 = "KT이용약관에 동의하시기 바랍니다. ";

// 1.가족명의 공통
VALIDATE_NOT_EMPTY_MSG.isVerify = "결합 대상 회선 정보에 대한 인증을 <br/>확인해 주세요 ";

// 1.가족명의, 2.모바일결합
VALIDATE_NOT_EMPTY_MSG.combUserNm = "이름을 입력해 주세요";
VALIDATE_NOT_EMPTY_MSG.svcIdfyNo = "생년월일 입력해 주세요 ";
VALIDATE_NOT_EMPTY_MSG.sexCd = "성별을 선택 하세요 ";
VALIDATE_NOT_EMPTY_MSG.ctn = "휴대폰 번호를 입력 하세요 ";
VALIDATE_NOT_EMPTY_MSG.timeOverYn2 = "유효시간이 지났습니다. <br/>인증번호를 재전송 후 다시 인증해주세요.";

// 1.가족명의, 2.인터넷결합
VALIDATE_NOT_EMPTY_MSG.combUserNm2 = "이름을 입력해 주세요";
VALIDATE_NOT_EMPTY_MSG.svcIdfyNo2 = "생년월일 입력해 주세요 ";
VALIDATE_NOT_EMPTY_MSG.sexCd2 = "성별을 선택 하세요 ";
VALIDATE_NOT_EMPTY_MSG.ctn2 = "서비스 ID를 입력 하세요 ";

// 1.본인명의 공통
VALIDATE_NOT_EMPTY_MSG.seq01 = "결합 회선을 선택해 주세요.";

// 1.본인명의, 2.인터넷결합
VALIDATE_NOT_EMPTY_MSG.homeCombTerm201 = "약정기간을 선택해 주세요.";

VALIDATE_NUMBER_MSG ={};

VALIDATE_FIX_MSG ={};
// 1.가족명의, 2.모바일결합
VALIDATE_FIX_MSG.svcIdfyNo = "생년월일이 올바르지 않습니다.<br/>ex) 19741219 ";
VALIDATE_FIX_MSG.ctn = "휴대폰 번호가 올바르지 않습니다 ";

// 1.가족명의, 2.인터넷결합
VALIDATE_FIX_MSG.svcIdfyNo2 = "생년월일이 올바르지 않습니다.<br/>ex) 19741219 ";

VALIDATE_COMPARE_MSG ={};



var interval2 ;

var pageObj = {
    eventId:""
}

$(document).ready(function (){

    // 유의사항 가져오기
    ajaxCommon.getItemNoLoading({
        id:'getNotice'
        ,cache:false
        ,url:'/termsPop.do'
        ,data: "cdGroupId1=TERMSCOM&cdGroupId2=combinePlusNoticeMO"
        ,dataType:"json"
    } ,function(jsonObj){
        var inHtml = unescapeHtml(jsonObj.docContent);
        $('.combine-notice').html(inHtml);
        KTM.initialize();
    });

    $("._btnVideo").click(function(){
        let iframeSrc = $(this).data("iframeSrc");
        let title = $(this).data("title");

        $("#youTubeIframe").attr('src', iframeSrc);
        // $("#modalContentTitle").html(title);

        var el = document.querySelector('#modalContent');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();
    });


    $("#btnLogin").click(function(){
        ajaxCommon.createForm({
            method:"post"
            ,action:"/loginForm.do"
        });

        ajaxCommon.attachHiddenElement("uri","/content/combineKt.do");
        ajaxCommon.formSubmit();
    });



    $("#btnOk").click(function(){
        if($("#certifyYn").val() != 'Y'){
            MCP.alert("인증을 받아주세요.");
            return;
        } else {
            var varData = ajaxCommon.getSerializedData({
                menu:$("#menuType").val()
            });

            ajaxCommon.getItemNoLoading({
                    id:'accountCheck'
                    ,cache:false
                    ,url:'/content/getNoLoginRateInfoAjax.do'
                    ,data: varData
                    ,dataType:"json"
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        var dataObj = jsonObj.DATA_OBJ
                        $("#ncn").empty();
                        $("#ncn").append("<option  value='"+dataObj.svcCntrNo+"'>"+dataObj.ctn+"</option>");
                        $("#ncn").parent().addClass("has-value");

                        $("#rateNm").val(dataObj.rateNm);
                        $("#divStep2").show();
                        $("#divStep1").hide();

                        //결과 페이지도 우선 설정
                        $("._ctn").html(dataObj.ctn);
                        $("._rate").html(dataObj.rateNm);
                        $("._userNm").html(dataObj.subLinkName);

                    } else {
                        var resultMsg = jsonObj.RESULT_MSG;
                        if (resultMsg!= null) {
                            MCP.alert(resultMsg);
                        } else {
                            MCP.alert("가입정보 확인 실패 하였습니다.");
                        }
                    }

                    var el = document.querySelector('#divCertifySms');
                    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                    if( modal._isOpen ){
                        modal.close();
                    }
                });
        }
    });

    $("#btnCombiSvcList").click(function(){
        KTM.LoadingSpinner.show();
        var varData = ajaxCommon.getSerializedData({
            ncn:$("#ncn").val()
        });

        ajaxCommon.getItemNoLoading({
                id:'combiSvcList'
                ,cache:false
                ,url:'/content/getCombiSvcListAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {

                    var $objList = $("#divCombinHistory tbody");
                    $objList.empty();

                    let combineKtList = "";
                    if(jsonObj.RESULT_LIST.length > 0){
                        combineKtList = jsonObj.RESULT_LIST.filter(item => item.svcContDivNm !== "Mobile(KIS)");
                    }

                    if ( combineKtList.length > 0) {
                        var objList = combineKtList;
                        var arr =[];
                        var arr2 =[];
                        for(i = 0 ; i < objList.length ; i++) {
                            arr.push("<tr>\n");
                            arr.push("    <th scope='row'>"+objList[i].combTypeNm+"</th>\n");
                            arr.push("    <td>\n");
                            arr.push("        <p>"+objList[i].svcNo+"</p>\n");
                            arr.push("        <p class='rate-info'>"+objList[i].prodNm+"</p>\n");
                            if (objList[i].combEngtExpirDtFormat !=null && objList[i].combEngtExpirDtFormat != "") {
                                arr.push("        <p class='date-info'>(만료예정일 : "+objList[i].combEngtExpirDtFormat+")</p>\n");
                            }
                            arr.push("    </td>\n");
                            arr.push("</tr>\n");
                        }
                        $objList.append(arr.join(''));

                        $("#divCombinHistory").show();
                    } else {

                        $("#divCombinHistory").hide();
                    }

                    var $objList2 = $("#divCombinHistory2 tbody");
                    $objList2.empty();

                    if (jsonObj.RESULT_LIST2.length > 0) {
                        var objList = jsonObj.RESULT_LIST2;
                        var arr =[];
                        for(i = 0 ; i < objList.length ; i++) {
                            arr.push("<tr>\n");
                            arr.push("    <th scope='row'>"+objList[i].combTypeNm+"</th>\n");
                            arr.push("    <td>\n");
                            arr.push("        <p>"+objList[i].combSvcNo+"</p>\n");
                            arr.push("        <span class='progress-info'>"+objList[i].rsltNm+"</span>\n");
                            arr.push("    </td>\n");
                            arr.push("</tr>\n");

                        }
                        $objList2.append(arr.join(''));
                        $("#divCombinHistory2").show();
                    } else {
                        $("#divCombinHistory2").hide();
                    }

                    if ( combineKtList.length < 1 && jsonObj.RESULT_LIST2.length < 1 ) {
                        $("#divCombinHistory").hide();
                        $("#divCombinHistory2").hide();
                        $("#divCombinHistoryNodata").show();
                        $("#pServiceNm").html("-");
                    } else {
                        $("#pServiceNm").html(jsonObj.AdsvcNm);
                        $("#divCombinHistoryNodata").hide();
                    }
                    KTM.LoadingSpinner.hide(true);
                    var el = document.querySelector('#combinHistory');
                    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                    modal.open();

                } else {
                    KTM.LoadingSpinner.hide(true);
                    var resultMsg = jsonObj.RESULT_MSG;
                    if (resultMsg!= null) {
                        MCP.alert(resultMsg);
                    } else {
                        MCP.alert("결합내역 조회 실패 하였습니다.");
                    }
                }
            });
    });

    $("#btnPrint").click(function(){
        KTM.LoadingSpinner.hide(true);
        var el = document.querySelector('#combinePrint');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();
    });

    // $("#btnBanner").click(function(){
    //     var parameterData = ajaxCommon.getSerializedData({
    //         eventPopTitle : '아무나 가족 결합+'
    //     });
    //     //1047  1005
    //     openPage('eventPop', "/m/event/eventDetailViewAjax.do?ntcartSeq=1005", parameterData);
    // });

    var getRateInfo = function() {
        var varData = ajaxCommon.getSerializedData({
            ncn:$("#ncn").val()
        });

        ajaxCommon.getItemNoLoading({
                id:'getRateInfo'
                ,cache:false
                ,url:'/content/getRateInfoAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    $("#rateNm").val(jsonObj.RESULT_RATE_NM);
                    $("._rate").html(jsonObj.RESULT_RATE_NM);
                    $("._ctn").html(jsonObj.RESULT_PHONE_NUM);
                    $("._userNm").html(jsonObj.RESULT_NM);
                } else {
                    MCP.alert("요금제 정보 조회 실패 하였습니다.");
                }
            });
    }

    $("#btnCheckCombine").click(function(){
        validator.config={};
        validator.config['chkPreAgree'] = 'isNonEmpty';

        if (validator.validate()) {
            var varData = ajaxCommon.getSerializedData({
                menu:$("#menuType").val()
                ,ncn:$("#ncn").val()
            });

            ajaxCommon.getItemNoLoading({
                    id:'CheckCombine'
                    ,cache:false
                    ,url:'/content/checkCombineAjax.do'
                    ,data: varData
                    ,dataType:"json"
                }
                ,function(jsonObj){

                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {

                        var serviceCd = jsonObj.RESULT_SERVICE_CD ;
                        var serviceNm = jsonObj.RESULT_SERVICE_NM ;

                        //결함 가능 여부
                        var arr =[];
                        arr.push("<div class='combine-result-possible'>\n");
                        arr.push("    <b>결합이 가능합니다.</b>\n");
                        if (serviceCd == "EMPTY") {
                            arr.push("    <p class='possible__info'>해당 요금제는 <span>결합 시 추가 데이터가 제공되지 않습니다.</span></p>\n");
                            arr.push("    <p class='possible__subtxt'> ※상대 회선의 결합 데이터 제공 여부는 자세히보기(아무나 가족 결합+ 혜택 대상 요금제)를 통해 확인해주세요</p>\n");
                            $("#divResult_01 .combine-complete__txt").html("<span class=\"u-co-mint\">결합이 완료 되었습니다.</span><br/>결합 신청요금제가 무료결합데이터 제공 요금제에 해당하지 않아, 데이터가 제공되지 않습니다.");

                        } else {
                            arr.push("    <p class='possible__info-provid'>해당 요금제는 결합 시 \""+serviceNm+"\"이 제공됩니다.</p>\n");
                            arr.push("    <p class='possible__subtxt'> ※상대 회선의 결합 데이터 제공 여부는 자세히보기(아무나 가족 결합+ 혜택 대상 요금제)를 통해 확인해주세요</p>\n");
                            $("#divResult_01 .combine-complete__txt").html("<span class=\"u-co-mint\">결합이 완료 되었습니다.</span><br/>kt M모바일 회선에 무료결합데이터(<span class='_tdServiceNm'>"+serviceNm+"</span>)이 제공되었습니다.");

                        }
                        arr.push("    <img src='/resources/images/mobile/product/combine_possible_mo.png' >\n");
                        arr.push("</div>\n");


                        $("#divStep2 > .combine-result").html(arr.join(''));
                        $("#divStep2 > .combine-result").show();
                        $("#divChild").show();
                        $("#divStep2 > .combine-sign").show();

                        //결과 페이지도 우선 설정
                        $("._serviceNm").html(serviceNm);

                    } else if ("0003" ==  jsonObj.RESULT_CODE) {
                        var arr =[];
                        arr.push("<div class='combine-result-impossible'>\n");
                        arr.push("    <b>해당 상품은 결합이 불가합니다.</b>\n");
                        arr.push("    <img src='/resources/images/mobile/product/combine_impossible_mo.png' >\n");
                        arr.push("</div>\n");

                        $("#divStep2 > .combine-result").html(arr.join(''));
                        $("#divStep2 > .combine-result").show();
                    } else {
                        var msg = jsonObj.RESULT_MSG;
                        if (msg != null && msg != "") {
                            MCP.alert(msg);
                        } else {
                            MCP.alert("결합 대상 여부 확인 실패 하였습니다.");
                        }
                    }
                });
        } else {
            MCP.alert(validator.getErrorMsg() );
        }
    });


    // 가족명의 - 모바일결합 회선조회 + 사전체크
    $("#btnChildCertify").click(function(){
        childCertifyMb();
    });

    // 가족명의 - 모바일결합 회선조회 + 사전체크
    var childCertifyMb = function () {

        validator.config={};
        validator.config['sameCustYn01'] = 'isNonEmpty';
        validator.config['svcNoTypeCd01'] = 'isNonEmpty';
        validator.config['combUserNm'] = 'isNonEmpty';
        validator.config['svcIdfyNo'] = 'isDate';
        validator.config['sexCd'] = 'isNonEmpty';
        validator.config['ctn'] = 'isPhone';

        clearInterval(interval2);
        if (validator.validate()) {
            KTM.LoadingSpinner.show();
            var varData = ajaxCommon.getSerializedData({
                menu:$("#menuType").val()
                ,svcNoTypeCd:$("input:radio[name=svcNoTypeCd]:checked").val()
                ,sexCd:$("#sexCd").val()
                ,birthday:$("#svcIdfyNo").val()
                ,subLinkName:$("#combUserNm").val()
                ,ctn:$("#ctn").val()
                ,prcsMdlInd:$("#prcsMdlInd").val()
            });

            ajaxCommon.getItemNoLoading({
                    id:'CheckCombine'
                    ,cache:false
                    ,url:'/content/childCertifyKtAjax.do'
                    ,data: varData
                    ,dataType:"json"
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        dailyMissionTimer2(5);
                        $("#isSendSms").val("Y");
                        $("#timeOverYn2").val("N");
                        MCP.alert("해당 번호로 인증번호를 발송하였습니다.");

                        $("._authNum").show();
                        $("#btnChildCertify").parent().hide();


                        var msg = jsonObj.RESULT_MSG ;
                        if (msg!= null && msg != "") {
                            $("#authNum").val(msg);
                            $("#authNum").parent().addClass("has-value");
                        }

                        $("#divCheck").show();
                        //본인이 아닌며
                        // if (jsonObj.RESULT_IS_OWN != "01") {
                        //     $("#divCheck").show();
                        // } else {
                        //     $("#divCheck").hide();
                        // }

                        KTM.LoadingSpinner.hide(true);

                    } else {
                        var resultMsg = jsonObj.RESULT_MSG;
                        if (resultMsg != null && resultMsg != undefined) {
                            MCP.alert(resultMsg);
                        } else {
                            MCP.alert("결합 대상 여부 확인 실패 하였습니다.");
                        }
                        KTM.LoadingSpinner.hide(true);
                    }
                });
        } else {
            KTM.LoadingSpinner.hide(true);
            MCP.alert(validator.getErrorMsg() );
        }
    }

    // 가족명의 - 인터넷결합 회선조회 + 사전체크
    $("#btnChildCertify2").click(function(){
        childCertifyMoIt();
    });

    // 가족명의 - 인터넷결합 회선조회 + 사전체크
    var childCertifyMoIt = function () {

        validator.config={};
        validator.config['sameCustYn01'] = 'isNonEmpty';
        validator.config['svcNoTypeCd01'] = 'isNonEmpty';
        validator.config['combUserNm2'] = 'isNonEmpty';
        validator.config['svcIdfyNo2'] = 'isDate';
        validator.config['sexCd2'] = 'isNonEmpty';
        validator.config['ctn2'] = 'isNonEmpty';

        if (validator.validate()) {
            KTM.LoadingSpinner.show();
            var varData = ajaxCommon.getSerializedData({
                menu:$("#menuType").val()
                ,svcNoTypeCd:$("input:radio[name=svcNoTypeCd]:checked").val()
                ,sexCd:$("#sexCd2").val()
                ,birthday:$("#svcIdfyNo2").val()
                ,subLinkName:$("#combUserNm2").val()
                ,ctn:$("#ctn2").val()
                ,homeCombTerm:$("#homeCombTerm").val()
                ,prcsMdlInd:$("#prcsMdlInd").val()
            });

            ajaxCommon.getItemNoLoading({
                    id:'CheckCombine'
                    ,cache:false
                    ,url:'/content/childCertifyKtAjax.do'
                    ,data: varData
                    ,dataType:"json"
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {

                        //결과 페이지도 우선 설정
                        $("#tdKtInfo").html(jsonObj.RESULT_KT_INFO);
                        $("#isVerify").val("Y");

                        $("#sameCustYn01").prop("disabled", true);
                        $("#sameCustYn02").prop("disabled", true);
                        $("#svcNoTypeCd01").prop("disabled", true);
                        $("#svcNoTypeCd02").prop("disabled", true);
                        $("#combUserNm2").prop("disabled", true);
                        $("#svcIdfyNo2").prop("disabled", true);
                        $("#sexCd2").prop("disabled", true);
                        $("#ctn2").prop("disabled", true);
                        $("#homeCombTerm").prop("disabled", true);

                        $("#divCheck").show();
                        // if (jsonObj.RESULT_IS_OWN != "01") {
                        //     $("#divCheck").show();
                        // } else {
                        //     $("#divCheck").hide();
                        // }

                        KTM.LoadingSpinner.hide(true);

                        MCP.alert("해당 회선은 결합이 가능합니다.", function (){
                            isValidateNonEmptyStep1();
                        });


                    } else {
                        var resultMsg = jsonObj.RESULT_MSG;
                        if (resultMsg != null && resultMsg != undefined) {
                            MCP.alert(resultMsg);
                        } else {
                            MCP.alert("결합 대상 여부 확인 실패 하였습니다.");
                        }
                        KTM.LoadingSpinner.hide(true);
                    }
                });
        } else {
            MCP.alert(validator.getErrorMsg() );
        }
    }

    $("#btnMoreTime").click(function(){

        ajaxCommon.getItemNoLoading({
                id:'CheckCombine'
                ,cache:false
                ,url:'/content/childCertifyMoreAjax.do'
                ,data: ""
                ,dataType:"json"
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    clearInterval(interval2);
                    dailyMissionTimer2(5);
                    $("#timeOverYn2").val("N");
                } else {
                    MCP.alert("시간 연장을 실패 하였습니다.");
                }
            });
    });



    $("#bthAuthSms").click(function(){
        validator.config={};
        validator.config['isSendSms'] = 'isNonEmpty';
        validator.config['authNum'] = 'isNumFix6';
        validator.config['timeOverYn2'] = 'isNonEmpty';

        if (validator.validate()) {

            var varData = ajaxCommon.getSerializedData({
                phoneNum:$("#ctn").val()
                ,authNum:$("#authNum").val()
            });

            ajaxCommon.getItemNoLoading({
                    id:'childVerify'
                    ,cache:false
                    ,url:'/content/childVerifyAjax.do'
                    ,data: varData
                    ,dataType:"json"
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        MCP.alert("인증이 완료되었습니다.<br/>해당 회선은 결합이 가능합니다.");
                        isValidateNonEmptyStep1();
                        $("#isVerify").val("Y");
                        $("#timeover2").hide();
                        $("#countdown2").hide();
                        $("#countdownTime2").hide();
                        $("#bthAuthSms").parent().hide();



                        $("#sameCustYn01").prop("disabled", true);
                        $("#sameCustYn02").prop("disabled", true);
                        $("#svcNoTypeCd01").prop("disabled", true);
                        $("#svcNoTypeCd02").prop("disabled", true);
                        $("#combUserNm").prop("disabled", true);
                        $("#svcIdfyNo").prop("disabled", true);
                        $("#sexCd").prop("disabled", true);
                        $("#ctn").prop("disabled", true);
                        $("#authNum").prop("disabled", true);
                        $("#bthAuthSms").prop("disabled", true);

                        //결과 페이지도 우선 설정
                        $("#tdKtInfo").html(jsonObj.RESULT_KT_INFO);

                        isValidateNonEmptyStep1();

                    } else {
                        var resultMsg = jsonObj.RESULT_MSG;
                        if (resultMsg != null && resultMsg != undefined) {
                            MCP.alert(resultMsg);
                        } else {
                            MCP.alert("인증번호가 틀렸습니다. 다시 확인해 주세요.");
                        }
                    }
                });
        } else {
            MCP.alert(validator.getErrorMsg() );
        }
    });



    $("#ctn").keyup(function(){
        validator.config={};
        validator.config['ctn'] = 'isPhone';
        if (!validator.validate()) {
            $(this).parent().addClass("has-error");
            $(this).parent().parent().find('.c-form__text').show();
            return true;
        } else {
            $(this).parent().removeClass("has-error");
            $(this).parent().parent().find('.c-form__text').hide();
        }
    });



    $("#svcIdfyNo").keyup(function(){
        validator.config={};
        validator.config['svcIdfyNo'] = 'isDate';
        if (!validator.validate()) {
            $(this).parent().addClass("has-error");
            $(this).parent().parent().find('.c-form__text').show();
            return true;
        } else {
            $(this).parent().removeClass("has-error");
            $(this).parent().parent().find('.c-form__text').hide();
        }
    });


    $("#svcIdfyNo2").keyup(function(){
        validator.config={};
        validator.config['svcIdfyNo2'] = 'isDate';
        if (!validator.validate()) {
            $(this).parent().addClass("has-error");
            $(this).parent().parent().find('.c-form__text').show();
            return true;
        } else {
            $(this).parent().removeClass("has-error");
            $(this).parent().parent().find('.c-form__text').hide();
        }
    });


    // 결합 명의 선택
    $("input:radio[name=sameCustYn]").click(function() {
        if ($("input:radio[name=svcNoTypeCd]:checked").val()) {
            clickSvcType();
        }
    });

    // 결합 상품 선택
    $("input:radio[name=svcNoTypeCd]").click(function() {
        clickSvcType();
    });

    var clickSvcType = function() {
        validator.config={};
        validator.config['sameCustYn01'] = 'isNonEmpty';
        validator.config['svcNoTypeCd01'] = 'isNonEmpty';
        if (validator.validate()) {
            $("._svcNoTypeCd").hide();
            $("#ktList").hide();
            var sameCustYnVal = $("input:radio[name=sameCustYn]:checked").val();
            var svcNoTypeCdVal = $("input:radio[name=svcNoTypeCd]:checked").val();

            // 결합명의가 본인명의 일때
            if (sameCustYnVal == 'Y') {
                KTM.LoadingSpinner.show();
                var varData = ajaxCommon.getSerializedData({
                    menu:$("#menuType").val()
                    ,svcNoTypeCd:svcNoTypeCdVal
                    ,sameCustYn:sameCustYnVal
                    ,prcsMdlInd:$("#prcsMdlInd").val()
                });

                ajaxCommon.getItemNoLoading({
                        id:'combineList'
                        ,cache:false
                        ,url:'/content/childCertifyKtAjax.do'
                        ,data: varData
                        ,dataType:"json"
                    }
                    ,function(jsonObj){
                        var arr =[];
                        if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                            if (jsonObj.combList != null && jsonObj.combList.length > 0) {
                                isValidateNonEmptyStep1();
                                // arr.push('<div class="c-row c-row--lg">\n');
                                arr.push('<div class="c-table u-mt--20">\n');
                                arr.push('  <table>\n');
                                arr.push('  <colgroup>\n');
                                arr.push('      <col style="width: 20%">\n');
                                arr.push('      <col>\n');
                                arr.push('      <col style="width: 30%">\n');
                                arr.push('  </colgroup>\n');
                                arr.push('  <thead>\n');
                                arr.push('      <tr>\n');
                                arr.push('          <th scope="col">선택</th>\n');
                                arr.push('          <th scope="col">상품명</th>\n');
                                arr.push('          <th scope="col">가입일</th>\n');
                                arr.push('      </tr>\n');
                                arr.push('  </thead>\n');
                                arr.push('  <tbody>\n');
                                for (var i=1; i<jsonObj.combList.length+1; i++) {
                                    arr.push('      <tr>\n');
                                    arr.push('          <td>\n');
                                    arr.push('              <div class="c-chk-wrap">\n');
                                    arr.push('                  <input class="c-radio c-radio--button--card" id="seq0' + i + '" name="seq" value="' + jsonObj.combList[i-1].seq + '" type="radio">\n');
                                    arr.push('                  <label class="c-label" for="seq0' + i + '"></label>\n');
                                    arr.push('              </div>\n');
                                    arr.push('          </td>\n');
                                    arr.push('          <td>\n');
                                    arr.push('                <span class="c-accordion__text">\n');
                                    arr.push('                    <span class="c-hidden">상품명</span>' + jsonObj.combList[i-1].svcDivCd + '(' + jsonObj.combList[i-1].mskSvcNo + ')</span>\n');
                                    arr.push('                </span>\n');
                                    arr.push('          </td>\n');
                                    arr.push('          <td>\n');
                                    arr.push('                <span class="c-accordion__text">\n');
                                    arr.push('                    <span class="c-hidden">가입일</span>' + jsonObj.combList[i-1].svcContOpnDt + '</span>\n');
                                    arr.push('                </span>\n');
                                    arr.push('          </td>\n');
                                    arr.push('      </tr>\n');
                                }
                                arr.push('      </tbody>\n');
                                arr.push('  </table>\n');
                                arr.push('</div>\n');
                                arr.push('<p class="u-co-gray c-text--type3 u-mt--8">');
                                arr.push('    <span>※현재 사용중인 KT상품이 조회되지 않을 경우 KT고객센터를 통해 MVNO 정보제공동의 후 다시 조회 해 주세요.</span>');
                                arr.push('</p>');
                                arr.push('<p class="u-co-gray c-text--type3 u-mt--8">');
                                arr.push('    <span>※인터넷 상품의 경우 조회일 기준 전월 개통한 상품만 보여집니다.</span>');
                                arr.push('</p>');

                                // 결합상품 인터넷일때 약정기간 노출
                                if (svcNoTypeCdVal == 'IT') {
                                    arr.push('<div class="c-form">\n');
                                    arr.push('  <span class="c-form__title--type2">약정 기간을 선택해 주세요</span>\n\n');
                                    arr.push('  <div class="c-check-wrap c-check-wrap--type3" role="group" aria-labelledby="sameCustInfo">\n\n');
                                    arr.push('  <div class="c-chk-wrap">\n');
                                    arr.push('      <input type="radio" class="c-radio c-radio--button" id="homeCombTerm201" name="homeCombTerm2" value="1" >\n\n');
                                    arr.push('      <label class="c-label u-ta-center" for="homeCombTerm201">1년</label>\n');
                                    arr.push('   </div>\n');
                                    arr.push('  <div class="c-chk-wrap">\n');
                                    arr.push('      <input type="radio" class="c-radio c-radio--button" id="homeCombTerm202" name="homeCombTerm2" value="2">\n\n');
                                    arr.push('      <label class="c-label u-ta-center" for="homeCombTerm202">2년</label>\n');
                                    arr.push('  </div>\n');
                                    arr.push('  <div class="c-chk-wrap">\n');
                                    arr.push('      <input type="radio" class="c-radio c-radio--button" id="homeCombTerm203" name="homeCombTerm2" value="3">\n\n');
                                    arr.push('      <label class="c-label u-ta-center" for="homeCombTerm203">3년</label>\n');
                                    arr.push('  </div>\n');
                                    arr.push('</div>\n');
                                    arr.push('</div>\n');
                                }
                                // arr.push('</div>\n');

                            } else {
                                // arr.push('<p class="nodata--type2">조회 결과가 없습니다.</p>');
                                arr.push('<div class="c-text-box c-text-box--red deco-combine u-mt--32">\n');
                                arr.push('    <b class="c-text c-text--type2">결합 가능한 상품이 없습니다.</b>\n');
                                arr.push('    <p class="c-text c-text--type6 u-co-black">만약 조회되지 않을 경우<br>KT 고객센터로 MVNO제공동의 여부를 확인하시기 바랍니다.</p>\n');
                                arr.push('    <img src="/resources/images/mobile/content/img_combine.svg" alt="">\n');
                                arr.push('</div>\n');
                                arr.push('<p class="u-co-gray c-text--type3 u-mt--8">');
                                arr.push('    <span>※현재 사용중인 KT상품이 조회되지 않을 경우 KT고객센터를 통해 MVNO 정보제공동의 후 다시 조회 해 주세요.</span>');
                                arr.push('</p>');
                                arr.push('<p class="u-co-gray c-text--type3 u-mt--8">');
                                arr.push('    <span>※인터넷 상품의 경우 조회일 기준 전월 개통한 상품만 보여집니다.</span>');
                                arr.push('</p>');
                            }

                            $("#ktList").show();
                            $("#ktList").html(arr.join(''));

                            KTM.LoadingSpinner.hide(true);
                        } else if ('1001' ==  jsonObj.RESULT_CODE) {
                            arr.push('<div class="c-text-box c-text-box--red deco-combine u-mt--32">\n');
                            arr.push('    <b class="c-text c-text--type2">결합 가능한 상품이 없습니다.</b>\n');
                            arr.push('    <p class="c-text c-text--type6 u-co-black">만약 조회되지 않을 경우<br>KT 고객센터로 MVNO제공동의 여부를 확인하시기 바랍니다.</p>\n');
                            arr.push('    <img src="/resources/images/mobile/content/img_combine.svg" alt="">\n');
                            arr.push('</div>\n');
                            arr.push('<p class="u-co-gray c-text--type3 u-mt--8">');
                            arr.push('    <span>※현재 사용중인 KT상품이 조회되지 않을 경우 KT고객센터를 통해 MVNO 정보제공동의 후 다시 조회 해 주세요.</span>');
                            arr.push('</p>');
                            arr.push('<p class="u-co-gray c-text--type3 u-mt--8">');
                            arr.push('    <span>※인터넷 상품의 경우 조회일 기준 전월 개통한 상품만 보여집니다.</span>');
                            arr.push('</p>');

                            $("#ktList").show();
                            $("#ktList").html(arr.join(''));

                            KTM.LoadingSpinner.hide(true);
                        } else {
                            var resultMsg = jsonObj.RESULT_MSG;
                            if (resultMsg != null && resultMsg != undefined) {
                                MCP.alert(resultMsg);
                            } else {
                                MCP.alert("결합 대상 여부 확인 실패 하였습니다.");
                            }
                            KTM.LoadingSpinner.hide(true);
                        }
                    });
            } else {
                // 결합명의가 가족명의 일때
                $("#svcNoTypeCd_"+svcNoTypeCdVal).show();
            }
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg());
        }
    }

    $("#ncn").change(function(){
        getRateInfo();
        $("#divStep2 > .combine-result").hide();
        $("#divChild").hide();
        $("#divStep2 > .combine-sign").hide();
    });

    //신청 전 필수 확인 사항 체크
    $("#chkPreAgree").click(function(){
        var $thisObj = $(this);
        if ($thisObj.is (':checked')) {
            $("#btnCheckCombine").removeClass('is-disabled');
        } else {
            $("#btnCheckCombine").addClass('is-disabled');
        }
    });

    //일괄 동의
    $("#btnStplAllCheck").click(function(){
        if ($(this).is(':checked')) {
            $("._agree").prop("checked", "checked");
        } else {
            $("._agree").prop("checked", false);
        }
        isValidateNonEmptyStep1();
    });

    //약관 동의 클릭
    $("._agree").click(function(){
        agreeAllChk();
    });

    // 본인명의 - 결합회선
    $("input:radio[name=seq]").click(function(){
        isValidateNonEmptyStep1();
    });

    // 본인명의 - 인터넷결합 약정 개월수
    $("input:radio[name=homeCombTerm2]").click(function(){
        isValidateNonEmptyStep1();
    });

    $("#btnRegCombin").click(function(){
        var sameCustYnVal = $("input:radio[name=sameCustYn]:checked").val();
        var svcNoTypeCdVal = $("input:radio[name=svcNoTypeCd]:checked").val();
        validator.config={};
        validator.config['sameCustYn01'] = 'isNonEmpty';
        validator.config['svcNoTypeCd01'] = 'isNonEmpty';
        if (sameCustYnVal == 'Y') {
            validator.config['seq01'] = 'isNonEmpty';
            if (svcNoTypeCdVal == 'IT') {
                validator.config['homeCombTerm201'] = 'isNonEmpty';
            }
        } else {
            validator.config['isVerify'] = 'isNonEmpty';
        }
        validator.config['chkAgree'] = 'isNonEmpty';
        validator.config['chkAgree2'] = 'isNonEmpty';
        validator.config['chkAgree3'] = 'isNonEmpty';
        validator.config['chkAgree4'] = 'isNonEmpty';

        if (validator.validate()) {
            KTM.Confirm('해당 회선과 결합하시겠습니까?', function () {
                this.close();
                var varData;
                var varUrl;
                // 본인명의
                if (sameCustYnVal == 'Y') {
                    var homeCombTermVal = svcNoTypeCdVal == 'IT' ? $("input:radio[name=homeCombTerm2]:checked").val() : '';
                    varData = ajaxCommon.getSerializedData({
                        menu:$("#menuType").val(),
                        combSeq:$("input:radio[name=seq]:checked").val(),
                        sameCustYn:sameCustYnVal,
                        svcNoTypeCd:svcNoTypeCdVal,
                        homeCombTerm:homeCombTermVal,
                        prcsMdlInd:$("#prcsMdlInd").val()
                    });
                    varUrl = '/content/mineRegCombineKtAjax.do';
                } else { // 가족명의
                    varData = ajaxCommon.getSerializedData({
                        prcsMdlInd:$("#prcsMdlInd").val()
                    });
                    varUrl = '/content/regCombineKtAjax.do';
                }
                ajaxCommon.getItem({
                        id:'regCombine'
                        ,cache:false
                        ,url:varUrl
                        ,data: varData
                        ,dataType:"json"
                    }
                    ,function(jsonObj){
                        if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                            var reqSeq = jsonObj.REQ_SEQ ;
                            ajaxCommon.createForm({
                                method:"post"
                                ,action:"/m/content/combineKtComplete.do"
                            });

                            ajaxCommon.attachHiddenElement("reqSeq",reqSeq);
                            ajaxCommon.formSubmit();

                            /*
                            $("._regForm").hide();
                            $("._completeForm").show();

                            if (jsonObj.APLY_RELATN_CD ==  "01") {
                                $("#divResult_01").show();
                                $("#divResult_02").hide();
                            } else {
                                $("#divResult_01").hide();
                                $("#divResult_02").show();
                            }

                            KTM.LoadingSpinner.hide(true);

                            setTimeout(function(){
                                window.scrollTo(0,0);
                            }, 500);
                            */
                        } else {
                            var alertMessage = jsonObj.RESULT_MSG ? jsonObj.RESULT_MSG : "결합 신청에 실패하였습니다.";
                            if ("1999" == jsonObj.RESULT_CODE) {
                                alertMessage = "결합이 지연되고 있습니다.";
                            }
                            MCP.alert(alertMessage + "<br/>다시 시도해 주시기 바랍니다.", function() {
                                location.reload();
                            });
                        }
                    });
                return ;
            });
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg());
        }
    });

    if ($("#ncn").val() !=null) {
        getRateInfo();
    }

    function dailyMissionTimer2(duration) {
        $("#timeover2").hide();
        $("#countdown2").show();
        $("#countdownTime2").show();

        var timer = duration  * 60;//받아온 파라미터를 분단위로 계산한다
        var minutes =0;
        var seconds = 0;

        interval2 = setInterval(function(){
            minutes = parseInt(timer / 60 % 60, 10);
            seconds = parseInt(timer % 60, 10);
            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;
            $("#timer2").text(minutes+"분 "+seconds+"초");
            if (--timer < 0) {
                timer = 0;
                $("#timer2").text("");
                $("#countdown2").hide();
                $("#countdownTime2").hide();
                $("#timeover2").show();
                $("#timeOverYn2").val("");

                $("._authNum").hide();
                $("#btnChildCertify").parent().show();
                return;
            }
        }, 1000);
    }


    //dailyMissionTimer2(3);
    //$("._completeForm").show();

});

var isValidateNonEmptyStep1 = function() {
    var sameCustYnVal = $("input:radio[name=sameCustYn]:checked").val();
    var svcNoTypeCdVal = $("input:radio[name=svcNoTypeCd]:checked").val();
    validator.config={};
    validator.config['sameCustYn01'] = 'isNonEmpty';
    validator.config['svcNoTypeCd01'] = 'isNonEmpty';
    if (sameCustYnVal == 'Y') {
        validator.config['seq01'] = 'isNonEmpty';
        if (svcNoTypeCdVal == 'IT') {
            validator.config['homeCombTerm201'] = 'isNonEmpty';
        }
    } else {
        validator.config['isVerify'] = 'isNonEmpty';
    }
    validator.config['chkAgree'] = 'isNonEmpty';
    validator.config['chkAgree2'] = 'isNonEmpty';
    validator.config['chkAgree3'] = 'isNonEmpty';
    validator.config['chkAgree4'] = 'isNonEmpty';

    if (validator.validate(true)) {
        $('#btnRegCombin').removeClass('is-disabled');
    } else {
        $('#btnRegCombin').addClass('is-disabled');
    }
} ;


var fnSetEventId = function(eventId){
    pageObj.eventId = eventId;
};

function targetTermsAgree() {
    $('#' + pageObj.eventId).prop('checked', 'checked');
    agreeAllChk();
};


var btnRegDtl = function (param) {
    openPage('termsInfoPop','/termsPop.do',param);
}

//전체 약관 동의 체크
var agreeAllChk = function () {
    var isAllCheck= true;
    $("._agree").each(function(){
        if (!$(this).is(':checked')) {
            isAllCheck= false;
        }
    });

    if (isAllCheck) {
        $("#btnStplAllCheck").prop("checked", "checked");
    } else {
        $("#btnStplAllCheck").prop("checked", "");
    }
    isValidateNonEmptyStep1();
}
