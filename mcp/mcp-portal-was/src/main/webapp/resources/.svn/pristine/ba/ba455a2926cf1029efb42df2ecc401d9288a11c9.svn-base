// ############ recaptcha 적용 ############

function recaptchalogin(cbFn){
    grecaptcha.ready(function() {
        grecaptcha.execute("6LctwxopAAAAAMtpxTvHOWe5EKPy9YgAep_DgRns", {action: 'login'}).then(function(token) {
            $("#recaptchaToken").val(token);

            if(typeof cbFn == "function") cbFn();
            else{
                MCP.alert("recaptchalogin cbFn 값을 확인해주세요.");
            }
        });
    }); // end of grecaptcha.ready---------
}

// sms 본인인증 callback
function btn_reg(){

    if($("#certifyYn").val() != "Y"){
        MCP.alert("인증을 받아주세요.");
        return;
    }

    var varData = ajaxCommon.getSerializedData({
         phone: $.trim($("#phoneNum").val())
        ,menuType : $("#menuType").val()
    });

    ajaxCommon.getItem({
            id:'authSmsCheckAjax'
            ,cache:false
            ,url:'/sms/loginSmsCheckAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(data){

            if($("#platFormCd").val() != "A" && $("#platFormCd").val() != "M"){
                KTM.Dialog.closeAll(); //모달닫기(포탈)
            }

            if(data.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
                MCP.alert(data.RESULT_MSG);
            }
        }); // end of ajax-------
}