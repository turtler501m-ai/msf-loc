
;
VALIDATE_NOT_EMPTY_MSG = {};
VALIDATE_NOT_EMPTY_MSG.blBankCode = "은행을 선택 하시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.bankAcctNo = "계좌번호를  입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.chkAgree2 = "계좌 인출에 대한 동의 하여 주시기 바랍니다.";


history.pushState(null, null,location.href);
window.onpopstate = function (event){
    location.href = "/m/mypage/unpaidChargeList.do";
}


$(document).ready(function (){
    $(".payKakao").hide();
    $(".payPayco").hide();
    $(".payNaver").hide();

    // 숫자만 입력가능1
    $(".numOnly").keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });

$("#reqCardMmTmp").on("change", function(){
        var inpCardnum = $("#inpCardnum").val();
        var reqCardMmTmp = $("#reqCardMmTmp option:selected").val();
        var reqCardYyTmp = $("#reqCardYyTmp option:selected").val();
        var cardPwd = $("#cardPwd").val();

        if(inpCardnum  != "" && reqCardMmTmp != "" && reqCardYyTmp!="" &&cardPwd !=""){
            $("#btn_payReg").removeClass("is-disabled");
        }else{
            $("#btn_payReg").addClass("is-disabled");
        }
    });


    $("#reqCardYyTmp").on("change", function(){
        var inpCardnum = $("#inpCardnum").val();
        var reqCardMmTmp = $("#reqCardMmTmp option:selected").val();
        var reqCardYyTmp = $("#reqCardYyTmp option:selected").val();
        var cardPwd = $("#cardPwd").val();

        if(inpCardnum  != "" && reqCardMmTmp != "" && reqCardYyTmp!="" &&cardPwd !=""){
            $("#btn_payReg").removeClass("is-disabled");
        }else{
            $("#btn_payReg").addClass("is-disabled");
        }
    });
    $(".inputPay").keyup(function(){

        var inpCardnum = $("#inpCardnum").val();
        var reqCardMmTmp = $("#reqCardMmTmp option:selected").val();
        var reqCardYyTmp = $("#reqCardYyTmp option:selected").val();
        var cardPwd = $("#cardPwd").val();

        if(inpCardnum  != "" && reqCardMmTmp != "" && reqCardYyTmp!="" &&cardPwd !=""){
            $("#btn_payReg").removeClass("is-disabled");
        }else{
            $("#btn_payReg").addClass("is-disabled");
        }
    });
    $("#paySelect").on("change",function(){
        var paySel =$("#paySelect option:selected").val();

        if(paySel=='C'){
            $(".payCard").show();
            $(".payKakao").hide();
            $(".payPayco").hide();
            $(".payNaver").hide();
            $(".payTransfer").hide();
        } else if(paySel=='K'){
            $(".payCard").hide();
            $(".payKakao").show();
            $(".payPayco").hide();
            $(".payNaver").hide();
            $(".payTransfer").hide();
        } else if(paySel=='P'){
            $(".payCard").hide();
            $(".payKakao").hide();
            $(".payPayco").show();
            $(".payNaver").hide();
            $(".payTransfer").hide();
        } else if(paySel=='N'){
            $(".payCard").hide();
            $(".payKakao").hide();
            $(".payPayco").hide();
            $(".payNaver").show();
            $(".payTransfer").hide();
        } else if(paySel=='D'){
            $(".payCard").hide();
            $(".payKakao").hide();
            $(".payPayco").hide();
            $(".payNaver").hide();
            $(".payTransfer").show();
        }
    });

    $("#blBankCode").change(function(){
        isValidateNonEmptyTransfer();
    });

    $("#bankAcctNo").keyup(function(){
        isValidateNonEmptyTransfer();
    });

    $("#chkAgree2").click(function(){
        isValidateNonEmptyTransfer();
    });

});

    //신용카드 유효성체크
    function checkReqCard(){
        var paySel =$("#paySelect option:selected").val();
        if(paySel == 'C'){
            if($("#inpCardnum").val() == "" || $("#inpCardnum").val() == null){
                MCP.alert('신용카드 번호를 입력해 주세요.', function (){
                    $("#inpCardnum").focus();
                });
                return false;
            }

            if($("#inpCardnum").val().length < 16 || $("#inpCardnum").val().length < 15){
                MCP.alert('신용카드 번호를 확인해 주세요.', function (){
                    $("#inpCardnum").focus();
                });
                return false;
            }

            if($("#reqCardMmTmp").val() == ''){
                MCP.alert('신용카드 유효기간 월을 확인해 주세요.', function (){
                    $("#reqCardMmTmp").focus();
                });
                return false;
            }

            if($('#reqCardYyTmp').val() == ''){
                MCP.alert('신용카드 유효기간 년을 확인해 주세요.', function (){
                    $('#reqCardYyTmp').focus();
                });
                return false;
            }

            if($("#cardPwd").val() == ''){
                MCP.alert('신용카드 비밀번호를 입력해 주세요.', function (){
                    $('#cardPwd').focus();
                });
                return false;

            }

            var payments = $("#payments").val();
            var payMentMoney = $("#payMentMoney").val().replaceAll(",","");

            if(payments != "0"){
                if(50000 > payMentMoney ){
                    MCP.alert('할부는 5만원 이상부터 가능합니다.', function (){
                        $('#payMentMoney').focus();
                    });
                    return false;
                }
            }

            var chk = $("input[name=chkAgree]:checked").val();

            var today = $("#sysYear").val();
            var cardTmp = $('#reqCardYyTmp').val() +$('#reqCardMmTmp').val();

            if(today >= cardTmp){
                MCP.alert('신용카드 유효기간 년도를 입력해 주세요.', function (){
                     $('#reqCardYyTmp').focus();
                });

                return false;
            }

            if(!chk){
                MCP.alert("약관을 동의해 주세요.", function (){
                    $("#chkAgree").focus();
                });
                return false;
            }

            return true;


        }

    }


    //취소
    function btn_cancel(){
        var confirm = "작성중인 납부정보가 취소됩니다. 납부방법을 변경하시겠습니까?";
        KTM.Confirm(confirm, function (){
            ajaxCommon.createForm({
                        method:"post"
                        ,action:"/m/mypage/unpaidChargeList.do"
                });

            ajaxCommon.formSubmit();
        });
    }

    //납부
    function btn_payReg(payType){

        if(payType == "C"){

            if(!checkReqCard()){
                return;
            }

            var varData = '';
            var paymentsSel ='';
            var payments = $("#payments").val();
            if("0" == payments){
                paymentsSel = "일시불";
            } else{
                paymentsSel = payments + "개월";
            }
            var confirm = "신용/체크카드 납부정보 입니다.</br>● 납부금액 : " + $("#payMentMoney").val()+"</br>"
            +"● 카드번호 : " + $("#inpCardnum").val()+"</br>"
            +"● 카드 유효기간 : "+$("#reqCardMmTmp").val()+ "/"+$("#reqCardYyTmp").val()+"</br>"
            +"● 할부기간 : " +paymentsSel+"</br>"
            +"수납하시겠습니까?";
            var cardInstMnthCnt = "";
            if($("#payments").val() != "0"){
                cardInstMnthCnt  = $("#payments").val();
            }

            KTM.Confirm(confirm, function (){
                varData = ajaxCommon.getSerializedData({
                          contractNum : $("#contractNum").val()
                        , payMentMoney: $("#payMentMoney").val()
                        , blMethod : payType
                        , cardNo :$("#inpCardnum").val().trim()
                        , cardExpirDate : $("#reqCardYyTmp").val()+$("#reqCardMmTmp").val()
                        , cardPwd : $("#cardPwd").val()
                        , cardInstMnthCnt :cardInstMnthCnt
                        , rmnyChId : $("#paySelect").val()
                });

                ajaxCommon.getItem({
                    id:'myRateViewAjax'
                    ,cache:false
                    ,url:'/m/mypage/insertRealTimePaymentAjax.do'
                    ,data: varData
                    ,dataType:"json"
                },function(jsonObj){
                    if(jsonObj.RESULT_CODE == 'S'){
                        ajaxCommon.createForm({
                            method:"post"
                            ,action:"/m/mypage/realTimePaymentCompleteView.do"
                        });

                        ajaxCommon.attachHiddenElement("rmnyChId",$("#paySelect").val());
                        ajaxCommon.attachHiddenElement("payMentMoney",$("#payMentMoney").val());
                        ajaxCommon.attachHiddenElement("contractNum",$("#contractNum").val());
                        ajaxCommon.formSubmit();
                    } else {
                        var strMsg = jsonObj.RESULT_MSG ;
                        if (strMsg != undefined && strMsg != null) {
                            MCP.alert(strMsg);
                        } else {
                            MCP.alert("서비스 처리중 오류가 발생 하였습니다.");
                        }
                    }
                });

                this.close();
            });
        } else if(payType == "D"){
            validator.config={};
            validator.config['blBankCode'] = 'isNonEmpty';
            validator.config['bankAcctNo'] = 'isNonEmpty';
            validator.config['chkAgree2'] = 'isNonEmpty';

            if (validator.validate()) {

                var bankAcctNo = $("#bankAcctNo").val() ;
                bankAcctNo = bankAcctNo.replace(/([0-9]{6})$/gi,"******") ;

                var confirm = "계좌이체 납부 정보입니다.</br>● 납부금액 : " + $("#payMentMoney").val()+"</br>"
                    +"● 계좌번호  : " + bankAcctNo+"</br>"
                    +"수납하시겠습니까?";

                KTM.Confirm(confirm, function (){
                    varData = ajaxCommon.getSerializedData({
                        contractNum : $("#contractNum").val()
                        , payMentMoney: $("#payMentMoney").val()
                        , blMethod : payType
                        , blBankCode :$("#blBankCode").val()
                        , bankAcctNo : $("#bankAcctNo").val().trim()
                        , agrDivCd : "03"
                        , myslfAthnTypeItgCd : "01"
                    });

                    ajaxCommon.getItem({
                        id:'myRateViewAjax'
                        ,cache:false
                        ,url:'/mypage/insertRealTimePaymentAjax.do'
                        ,data: varData
                        ,dataType:"json"
                    }, function(jsonObj){
                        if(jsonObj.RESULT_CODE == 'S'){
                            ajaxCommon.createForm({
                                method:"post"
                                ,action:"/m/mypage/realTimePaymentCompleteView.do"
                            });
                            ajaxCommon.attachHiddenElement("rmnyChId",payType);
                            ajaxCommon.attachHiddenElement("payMentMoney",$("#payMentMoney").val());
                            ajaxCommon.attachHiddenElement("contractNum",$("#contractNum").val());
                            ajaxCommon.formSubmit();
                        } else {
                            var strMsg = jsonObj.RESULT_MSG ;
                            if (strMsg != undefined && strMsg != null) {
                                MCP.alert(strMsg);
                            } else {
                                MCP.alert("서비스 처리중 오류가 발생 하였습니다.");
                            }
                        }
                    });

                    this.close();
                });
            } else {
                MCP.alert(validator.getErrorMsg());
            }

        } else {
            varData = ajaxCommon.getSerializedData({
                contractNum : $("#contractNum").val()
                , payMentMoney: $("#payMentMoney").val()
                , blMethod : "P"
                , rmnyChId : payType
            });

            ajaxCommon.getItem({
                id:'myRateViewAjax'
                ,cache:false
                ,url:'/m/mypage/insertRealTimePaymentAjax.do'
                ,data: varData
                ,dataType:"json"
            },function(jsonObj){
                if(jsonObj.RESULT_CODE == 'S'){
                    ajaxCommon.createForm({
                        method:"post"
                        ,action:"/m/mypage/unpaidChargeList.do"
                    });

                    ajaxCommon.attachHiddenElement("contractNum",$("#contractNum").val());
                    ajaxCommon.formSubmit();
                } else {
                    var strMsg = jsonObj.RESULT_MSG ;
                    if (strMsg != undefined && strMsg != null) {
                        MCP.alert(strMsg);
                    } else {
                        MCP.alert("서비스 처리중 오류가 발생 하였습니다.");
                    }
                }

             });
        }
    }

    var isValidateNonEmptyTransfer =  function() {
        validator.config={};
        validator.config['blBankCode'] = 'isNonEmpty';
        validator.config['bankAcctNo'] = 'isNonEmpty';
        validator.config['chkAgree2'] = 'isNonEmpty';

        if (validator.validate(true)) {
            $('#btnTransfer').removeClass('is-disabled');
        } else {
            $('#btnTransfer').addClass('is-disabled');
        }
    } ;