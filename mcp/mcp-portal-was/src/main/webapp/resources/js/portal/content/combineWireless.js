


VALIDATE_NOT_EMPTY_MSG = {};
VALIDATE_NOT_EMPTY_MSG.childCtn = "결합 대상 회선 휴대폰 정보를  입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.isSendSms = "인증번호를 받으세요.";
VALIDATE_NOT_EMPTY_MSG.authNum = "인증번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.timeOverYn2 = "유효시간이 지났습니다. <br/>인증번호를 재전송 후 다시 인증해주세요.";
VALIDATE_NOT_EMPTY_MSG.isVerify = "결합 대상 회선 정보에 대한 인증을 <br/>확인해 주세요 ";
VALIDATE_NOT_EMPTY_MSG.chkAgree = "결합을 위한 필수사항에 동의하시기 바랍니다. ";
VALIDATE_NOT_EMPTY_MSG.chkAgree2 = "개인정보 수집 및 이용에 동의하시기 바랍니다. ";

VALIDATE_NUMBER_MSG ={};
VALIDATE_NUMBER_MSG.childCtn = "결합 대상 회선을  입력해 주세요.";
VALIDATE_NUMBER_MSG.authNum = "인증번호를 확인해 주세요.";

VALIDATE_FIX_MSG ={};
VALIDATE_FIX_MSG.authNum = "인증번호를 확인해 주세요.";

VALIDATE_COMPARE_MSG ={};
VALIDATE_COMPARE_MSG.childCtn = "결합 대상 회선  휴대폰 번호가 올바르지<br/> 않습니다";


var interval2 ;

var pageObj = {
    eventId:""
    ,adsvcNm:""
}

$(document).ready(function (){

    ajaxCommon.getItemNoLoading({
        id:'getNotice'
        ,cache:false
        ,url:'/termsPop.do'
        ,data: "cdGroupId1=TERMSCOM&cdGroupId2=combineNotice"
        ,dataType:"json"
    } ,function(jsonObj){
        var inHtml = unescapeHtml(jsonObj.docContent);
        $('.combine-notice').html(inHtml);
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

                        $("#btnCheckCombine").trigger("click");


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
                });
        }
    });


    $("#btnCheckCombine").click(function(){

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
                    arr.push("    <p>결합이 가능합니다.</p>\n");
                    if (serviceCd == "EMPTY") {
                        arr.push("    <p class='possible__info'>해당 요금제는 <span>결합 시 추가 데이터가 제공되지 않습니다.</span></p>\n");
                        arr.push("    <p class='possible__subtxt'>※상대 회선의 결합 데이터 제공 여부는 자세히보기(아무나 결합 혜택 대상 요금제)를 통해 확인해주세요</p>\n");

                        $("._completeForm .combine-complete__txt").html("<span class=\"u-co-mint\">결합이 완료 되었습니다.</span><br/>결합 신청요금제가 무료결합데이터 제공 요금제에 해당하지 않아, 데이터가 제공되지 않습니다.");

                    } else {
                        arr.push("    <p class='possible__info-provid'>해당 요금제는 결합 시 \""+serviceNm+"\"이 제공됩니다.</p>\n");
                        arr.push("    <p class='possible__subtxt'>※상대 회선의 결합 데이터 제공 여부는 자세히보기(아무나 결합 혜택 대상 요금제)를 통해 확인해주세요</p>\n");

                        $("._completeForm .combine-complete__txt").html("<span class=\"u-co-mint\">결합이 완료 되었습니다.</span><br/>kt M모바일 회선에 무료결합데이터(<span class='_tdServiceNm'>"+serviceNm+"</span>)이 제공되었습니다.");

                    }

                    arr.push("    <img src='/resources/images/portal/product/combine_possible.png' aria-hidden=true >\n");
                    arr.push("</div>\n");

                    $("#divStep2 > .combine-result").html(arr.join(''));
                    $("#divStep2 > .combine-result").show();
                    $("#divChild").show();
                    $("#divStep2 > .combine-sign").show();

                    //결과 페이지도 우선 설정
                    $("._tdServiceNm").html(serviceNm);


                } else if ("0003" ==  jsonObj.RESULT_CODE) {
                    var arr =[];
                    arr.push("<div class='combine-result-impossible'>\n");
                    arr.push("    <p><span>해당 상품은</span>결합이 불가합니다. </p>\n");
                    arr.push("    <img src='/resources/images/portal/product/combine_impossible.png' aria-hidden='true'>\n");
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
    });

    $(document).on("keyup", "#childCtn", function(){{}

        validator.config={};
        validator.config['childCtn'] = 'isPhone';

        if (!validator.validate()) {
            $("#childCtnChk").show();
            $("#childCtn").parent().addClass("has-error");
            $("#childCtnChk").removeAttr("style");
            return true;
        } else {
            $("#childCtn").parent().removeClass("has-error");
            $("#childCtnChk").hide();
        }
    })


    $("#btnChildCertify").click(function(){

        validator.config={};
        validator.config['childCtn'] = 'isPhone';

        if (validator.validate()) {
            KTM.LoadingSpinner.show();
            var varData = ajaxCommon.getSerializedData({
                menu:$("#menuType").val()
                ,cntrMobileNo:$("#childCtn").val()
                ,prcsMdlInd:$("#prcsMdlInd").val()
            });

            clearInterval(interval2);

            ajaxCommon.getItemNoLoading({
                    id:'CheckCombine'
                    ,cache:false
                    ,url:'/content/childCertifyAjax.do'
                    ,data: varData
                    ,dataType:"json"
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        dailyMissionTimer2(5);
                        $("#isSendSms").val("Y");
                        $("#timeOverYn2").val("N");
                        MCP.alert("해당 번호로 인증번호를 발송하였습니다.");

                        var msg = jsonObj.RESULT_MSG ;
                        if (msg!= null && msg != "") {
                            $("#authNum").val(msg);
                        }
                        //결과 페이지도 우선 설정
                        $("#tdChildServiceNm").html(jsonObj.RESULT_SERVICE_NM);
                        KTM.LoadingSpinner.hide(true);

                    } else {
                        var resultMsg = jsonObj.RESULT_MSG;
                        if (resultMsg!= null) {
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

    });


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
                phoneNum:$("#childCtn").val()
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
                        MCP.alert("인증이 완료되었습니다.");
                        $("#isVerify").val("Y");
                        $("#timeover2").hide();
                        $("#countdown2").hide();
                        $("#countdownTime2").hide();
                        $("#childCtn").prop("disabled", true);
                        $("#authNum").prop("disabled", true);
                        $("#btnChildCertify").prop("disabled", true);
                        $("#bthAuthSms").prop("disabled", true);

                        //결과 페이지도 우선 설정
                        $("#tdComChildCtn").html(jsonObj.RESULT_PHONE_NUM);
                        $("#tdCoChildmRate").html(jsonObj.RESULT_RATE_NM);
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


    $("#btnRegCombin").click(function(){
        validator.config={};
        validator.config['isVerify'] = 'isNonEmpty';
        validator.config['chkAgree'] = 'isNonEmpty';
        validator.config['chkAgree2'] = 'isNonEmpty';

        if (validator.validate()) {
            KTM.Confirm('해당 회선과 결합하시겠습니까?', function () {

                var varData = ajaxCommon.getSerializedData({
                    prcsMdlInd:$("#prcsMdlInd").val()
                });

                this.close();
                ajaxCommon.getItem({
                        id:'regCombine'
                        ,cache:false
                        ,url:'/content/regCombineAjax.do'
                        ,data: varData
                        ,dataType:"json"
                    }
                    ,function(jsonObj){

                        if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                            //$("._regForm").hide();
                            //$("._completeForm").show();
                            //window.scrollTo(0,0);
                            var reqSeq = jsonObj.REQ_SEQ ;
                            ajaxCommon.createForm({
                                method:"post"
                                ,action:"/content/combineWirelessComplete.do"
                            });

                            ajaxCommon.attachHiddenElement("reqSeq",reqSeq);
                            ajaxCommon.formSubmit();

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

    $("#bntLogin").click(function(){
        ajaxCommon.createForm({
            method:"post"
            ,action:"/loginForm.do"
        });

        ajaxCommon.attachHiddenElement("uri","/content/combineWireless.do");
        ajaxCommon.formSubmit();
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
                    /*var $objList = $("#divCombinHistory tbody");
                    var $objList2 = $("#combinePrint .combine-info-list tbody");

                    $objList.empty();
                    $objList2.empty();*/
                    let combineWirelessCnt = 0;
                    if(jsonObj.RESULT_LIST.length > 0){
                        combineWirelessCnt = jsonObj.RESULT_LIST.filter(item => item.svcContDivNm === "Mobile(KIS)").length;
                    }
                    if (combineWirelessCnt > 0) {
                        /*var objList = jsonObj.RESULT_LIST;
                        var arr =[];
                        var arr2 =[];
                        /*for(i = 0 ; i < objList.length ; i++) {
                            arr.push("<tr>\n");
                            arr.push("    <th scope='row'>"+objList[i].combTypeNm+"</th>\n");
                            arr.push("    <td>\n");
                            arr.push("        <p>"+objList[i].svcNo+"</p>\n");
                            arr.push("        <p class='rate-info'>"+objList[i].prodNm+"</p>\n");
                            arr.push("    </td>\n");
                            arr.push("    <th scope='row'>만료예정일</th>\n");
                            arr.push("    <td>"+objList[i].combEngtExpirDtFormat+"</td>\n");
                            arr.push("</tr>\n");

                            arr2.push("<tr>\n");
                            arr2.push("    <td>"+objList[i].combTypeSmNm+"</td>\n");
                            arr2.push("    <td>"+objList[i].combTypeSmNm2+"</td>\n");
                            arr2.push("    <td>"+objList[i].svcNo+"</td>\n");
                            arr2.push("    <td>"+objList[i].combEngtPerdMonsNum+"</td>\n");
                            arr2.push("    <td>"+objList[i].combEngtExpirDtFormat2+"</td>\n");
                            arr2.push("</tr>\n");

                        }
                        $objList.append(arr.join(''));
                        $objList2.append(arr2.join(''));

                        $("#divCombinHistory").show();*/
                        $("#divCombinHistoryData").show();
                        $("#divCombinHistoryData2").show();
                        $("#btnPrint").show();
                        $("._serviceNm").html(jsonObj.AdsvcNm);
                        $("#divCombinHistoryNodata").hide();
                    } else {
                        //$("#divCombinHistory").hide();
                        $("#divCombinHistoryData").hide();
                        $("#divCombinHistoryData2").hide();
                        $("#btnPrint").hide();
                        $("#divCombinHistoryNodata").show();
                        $("._serviceNm").html("-");
                    }

                    /*var $objList2 = $("#divCombinHistory2 tbody");
                    $objList2.empty();

                    if (jsonObj.RESULT_LIST2.length > 0) {
                        var objList = jsonObj.RESULT_LIST2;
                        var arr =[];
                        for(i = 0 ; i < objList.length ; i++) {
                            arr.push("<tr>\n");
                            arr.push("    <th scope='row'>" + objList[i].combTypeNm + "</th>\n");
                            arr.push("    <td>"+objList[i].combSvcNo+"</td>\n");
                            arr.push("    <th scope='row'>진행현황</th>\n");
                            arr.push("    <td><span class='progress-info'>"+objList[i].rsltNm+"</span></td>\n");
                            arr.push("</tr>\n");
                        }
                        $objList2.append(arr.join(''));

                        $("#divCombinHistory2").show();
                    } else {
                        $("#divCombinHistory2").hide();
                    }

                    if ( jsonObj.RESULT_LIST.length < 1) {
                        $("#divCombinHistory").hide();
                        $("#divCombinHistory2").hide();
                        $("#divCombinHistoryNodata").show();
                        $("._serviceNm").html("-");
                    } else {
                        $("._serviceNm").html(jsonObj.AdsvcNm);
                        $("#divCombinHistoryNodata").hide();
                    }*/
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
    //         eventPopTitle : '아무나 결합'
    //     });
    //     //1047  1005
    //     openPage('eventPop', "/event/eventDetailViewAjax.do?ntcartSeq=1379", parameterData);
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
                , async:false
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

    $("#ncn").change(function(){
        getRateInfo();
        $("#divStep2 > .combine-result").hide();
        $("#divChild").hide();
        $("#divStep2 > .combine-sign").hide();
    });

    $("#chkAgree").click(function(){
        isValidateNonEmptyStep1();
    });

    $("#chkAgree2").click(function(){
        isValidateNonEmptyStep1();
    });

    $("#btnAgreeClose").click(function(){
        $("#chkAgree").prop('checked', 'checked');
        isValidateNonEmptyStep1();
        KTM.Dialog.closeAll();
    });

    $("#btnAgreeClose2").click(function(){
        $("#chkAgree2").prop('checked', 'checked');
        isValidateNonEmptyStep1();
        KTM.Dialog.closeAll();
    });


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
                return;
            }
        }, 1000);
    }


    if ($("#ncn").val() !=null) {
        getRateInfo();
    }

    if ($("#divStep2").is(':visible')) {
        $("#btnCheckCombine").trigger("click");
    }

    //$("#divStep2").removeClass("c-hidden");
    //$("#divChild").removeClass("c-hidden");
    //dailyMissionTimer2(3);
    //$("._regForm").hide();
    //$("._completeForm").show();

});

var isValidateNonEmptyStep1 = function() {
    validator.config={};
    validator.config['isVerify'] = 'isNonEmpty';
    validator.config['chkAgree'] = 'isNonEmpty';
    validator.config['chkAgree2'] = 'isNonEmpty';


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
};

document.addEventListener('UILoaded', function() {
    var PrintButton = document.querySelector('.print-btn');
    PrintButton.addEventListener('click', function() {
        window.print();
    });
});

var btnRegDtl = function (param) {
    openPage('termsInfoPop','/termsPop.do',param);
}