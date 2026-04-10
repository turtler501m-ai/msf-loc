$(document).ready(function() {
    // 고객 안면인증 URL 요청 (FS8)
    $("#btnFathUrlRqt").click(function(){
        if ($("#isFathTxnRetv").val() == "1") {
            MCP.alert("안면인증을 완료 하였습니다.");
            return false;
        }

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};
        if (validator.validate()) {

            var varData = ajaxCommon.getSerializedData({
                onlineOfflnDivCd : "ONLINE",
                resNo : $("#resNo").val(),
                operType : $("#operType").val(),
                gubun : "P"
            });

            ajaxCommon.getItem({
                    id:'reqFathUrlRqtAjax'
                    ,cache:false
                    ,url:'/appform/reqFathUrlRqtAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,errorCall : function () { //연동 실패시 상담사개통으로 전환
                        KTM.LoadingSpinner.hide(true);
                        MCP.alert("시스템에 문제가 발생하였습니다.<br>다음에 다시 진행 부탁드립니다.");
                    }
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        MCP.alert(jsonObj.RESULT_MSG);
                        if(jsonObj.URL_ADR){
                            MCP.alert(jsonObj.URL_ADR);
                        }
                        $('#btnFathTxnRetv').prop('disabled', false);
                        $('#btnFathSkip').prop('disabled', false);
                    } else if("00002" == jsonObj.RESULT_CODE) {
                        MCP.alert("현재 안면인증을 진행할 수 없습니다.<br> 상담원이 순차적으로 확인 후 안내 연락 드리도록 하겠습니다.");
                    } else {
                        KTM.LoadingSpinner.hide(true);
                        MCP.alert(jsonObj.RESULT_MSG);
                    }
                });
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg() ,function (){
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
        }
    });

    // 고객 안면인증 결과확인 (PUSH / FS9)
    $("#btnFathTxnRetv").click(function(){
        if ($("#isFathTxnRetv").val() == "1") {
            MCP.alert("안면인증을 완료 하였습니다.");
            return false;
        }

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};
        if (validator.validate()) {

            var varData = ajaxCommon.getSerializedData({
                resNo : $("#resNo").val(),
                operType : $("#operType").val(),
                gubun : "P"
            });

            ajaxCommon.getItem({
                    id:'reqFathTxnRetvAjax'
                    ,cache:false
                    ,url:'/appform/reqFathTxnRetvAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,errorCall : function () { //연동 실패시 에러메시지 표출
                        KTM.LoadingSpinner.hide(true);
                        MCP.alert("시스템에 문제가 발생하였습니다.<br>다음에 다시 진행 부탁드립니다.");
                    }
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        MCP.alert("안면인증을 완료하였습니다. <br> 상담원이 순차적으로 확인 후 안내 연락 드리도록 하겠습니다.");
                        $("#isFathTxnRetv").val("1");
                    } else if("00002" ==  jsonObj.RESULT_CODE) { //안면인증 실패
                        KTM.LoadingSpinner.hide(true);
                        MCP.alert("안면인증 실패 하였습니다. <br> 향후 추가 인증이 필요할 수 있습니다. <br>(개통은 계속 진행 됩니다.) <br> 개통을 원하시는 경우 고객센터로 연락부탁드립니다.");
                    } else {
                        KTM.LoadingSpinner.hide(true);
                        MCP.alert(jsonObj.RESULT_MSG);
                    }
                });
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg() ,function (){
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
        }
    });

    var viewErrorMgs = function($thatObj, msg ) {
        if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
            $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
        } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3") ) {
            $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
        }
    };

    updateFathSkipBtnVisibility();
});

function requestFathSkip() {
    var varData = ajaxCommon.getSerializedData({
        resNo : $("#resNo").val(),
        operType : $("#operType").val(),
        gubun : "P"
    });

    KTM.LoadingSpinner.show();
    ajaxCommon.getItem({
        id:'requestCustFathTxnSkipAjax'
        ,cache:false
        ,url:'/appform/requestCustFathTxnSkipAjax.do'
        ,data: varData
        ,dataType:"json"
        ,errorCall : function () {
            KTM.LoadingSpinner.hide(true);
            MCP.alert("시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
        }
    }, function(jsonObj) {
        KTM.LoadingSpinner.hide(true);
        if (jsonObj.RESULT_CODE === AJAX_RESULT_CODE.SUCCESS) {
            MCP.alert(jsonObj.RESULT_MSG);
            $("#isFathTxnRetv").val("1");
            $("._isFathCert").hide();
        } else if (jsonObj.RESULT_CODE === "0002") {
            MCP.alert("이미 안면인증 SKIP 완료되었습니다.");
            $("#isFathTxnRetv").val("1");
            $("._isFathCert").hide();
        } else if (jsonObj.RESULT_CODE === "0001"
            || jsonObj.RESULT_CODE === "0003"
            || jsonObj.RESULT_CODE === "0005") {
            MCP.alert("안면인증 SKIP에 실패하였습니다.<br>안면인증 URL 받기를 다시 진행 부탁드립니다.");
        } else if (jsonObj.RESULT_CODE === "0004") {
            MCP.alert("안면인증 SKIP이 불가능합니다.");
        }
    });
}

function updateFathSkipBtnVisibility() {
    ajaxCommon.getItem({
        id:'isEnabledFT1'
        ,cache:false
        ,url:'/appform/isEnabledFT1.do'
        ,dataType:"json"
    }, function(jsonObj) {
        if (jsonObj.isEnabledFT1) {
            $("#btnFathSkip").show();
        }
    });
}