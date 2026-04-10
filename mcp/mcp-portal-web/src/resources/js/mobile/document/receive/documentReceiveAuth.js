$(document).ready(function() {

  $("#maskedMobileNo").prop("disabled",true);

  // 숫자만 입력
  $(".numOnly").keyup(function(){
    $(this).val($(this).val().replace(/[^0-9]/gi, ""));
  });

  // 숫자만 입력
  $(".numOnly").blur(function(){
    $(this).val($(this).val().replace(/[^0-9]/gi, ""));
  });
}); // end of document.ready -----------------------

var authenticateOtp = function() {
    var otp = $("#otp").val();
    if (!otp || !otp.trim()) {
        MCP.alert("OTP 인증번호를 입력해주세요.");
        return;
    }

    if (otp.trim().length !== 6) {
        MCP.alert("OTP 인증번호는 6자리입니다.");
        return;
    }

    var varData = ajaxCommon.getSerializedData({
        otp : otp,
        docRcvId : $("#docRcvId").val(),
        rcvUrlId : $("#rcvUrlId").val()
    });

    ajaxCommon.getItem({
        id: 'authenticateOtp'
        ,cache: false
        ,url: '/document/receive/authenticateOtp.do'
        ,data: varData
        ,dataType: "json"
    },function(data){
        if (data.RESULT_CODE === AJAX_RESULT_CODE.SUCCESS) {
            MCP.alert("인증을 완료하였습니다.", function() {
                ajaxCommon.createForm({
                    method:"post"
                    ,action:"/m/document/receive/uploadView.do"
                });

                ajaxCommon.attachHiddenElement("docRcvId", $("#docRcvId").val());
                ajaxCommon.formSubmit();
            });
        } else {
            MCP.alert(data.RESULT_MSG);
        }
    });
}

var confirmToResendNewOtp = function() {
    KTM.Confirm("인증번호를 재전송하시겠습니까?", function() {
        resendNewOtp();
        this.close();
    });
}

var resendNewOtp = function() {
    var varData = ajaxCommon.getSerializedData({
        docRcvId : $("#docRcvId").val(),
        rcvUrlId : $("#rcvUrlId").val()
    });

    ajaxCommon.getItem({
        id: 'resendNewOtp'
        ,cache: false
        ,url: '/document/receive/resendNewOtp.do'
        ,data: varData
        ,dataType: "json"
    },function(data){
        if (data.RESULT_CODE === AJAX_RESULT_CODE.SUCCESS) {
            MCP.alert("인증번호가 재전송되었습니다.");
        } else {
            MCP.alert(data.RESULT_MSG);
        }
    });
}