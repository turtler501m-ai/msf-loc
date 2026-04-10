
VALIDATE_NOT_EMPTY_MSG.name = "이름을 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.phone = "연락처를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.birthDate = "생년월일을 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.btnStplAllCheck = "이벤트 참여를 위해 미동의 항목에<br>동의 해 주시기 바랍니다.";

VALIDATE_FIX_MSG.birthDate = "생년월일을 8자리로 입력해 주세요.";
VALIDATE_COMPARE_MSG.phone = "휴대폰 형식이 일치 하지 않습니다.";

var objPopup;
const date = new Date();

$(document).ready(function(){

    $(".board_detail_table").css("margin-bottom","10px");

    // 숫자만 입력가능1
    $(".numOnly").keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });

    // 숫자만 입력가능2
    $(".numOnly").blur(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });

    var eventCode = ajaxCommon.isNullNvl($("#eventCode").val(),"");
    if(eventCode==""){
        KTM.Dialog.closeAll(); //모달닫기
        location.href="/main.do";
    }

    $("#prePromotionBtn").click(function(){

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        var name = $.trim(ajaxCommon.isNullNvl($("#name").val(),""));
        var birthDate = $.trim(ajaxCommon.isNullNvl($("#birthDate").val(),""));
        var phone = $.trim(ajaxCommon.isNullNvl($("#phone").val(),""));


        validator.config={};
        validator.config['name'] = 'isNonEmpty';
        validator.config['phone'] = 'isNonEmpty';
        validator.config['phone'] = 'isPhone';
        validator.config['birthDate'] = 'isNonEmpty';
        validator.config['birthDate'] = 'isNumFix8';
        validator.config['btnStplAllCheck'] = 'isNonEmpty';

        if (validator.validate()) {
            var varData = ajaxCommon.getSerializedData({
                   phone : phone,
                   code : $("#eventCode").val()
            });



            ajaxCommon.getItem({
                id:'promotionCheckAjax'
                ,cache:false
                ,url:'/event/promotionCheckAjax.do'
                ,data: varData
                ,dataType:"json"
                ,async:false
            }
            ,function(jsonObj){
                KTM.LoadingSpinner.hide();
                var resultCode = jsonObj.resultCode;
                if(resultCode=="S"){
                    /*objPopup = window.open("about:blank" ,"popupChk", "width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no");

                    ajaxCommon.createForm({
                        method:"post"
                        ,action:'/nice/popNiceAuth.do'
                        ,target:"popupChk"
                    });
                    ajaxCommon.attachHiddenElement("sAuthType","M");
                    ajaxCommon.attachHiddenElement("returnUrl","/nice/popNiceSuccM.do");
                    ajaxCommon.attachHiddenElement("sCheck","smsAuth");
                    ajaxCommon.attachHiddenElement("name",name);
                    ajaxCommon.attachHiddenElement("birthDate",birthDate);
                    ajaxCommon.formSubmit();
                    objPopup.focus();*/
                    $("#promotionPopup").hide();
                    $("#promotionModal").show();
                } else {
                    MCP.alert("이미 등록된 번호 입니다.");
                    return false;
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

    // 약관 동의 전체선택 or 전체해제
    $('#btnStplAllCheck').on('click', function (){

         if($(priAdAgree).prop('checked')){
             if($(this).prop('checked')){
                    $('input[name=terms]').prop('checked', 'checked');
                }else{
                    $('input[name=terms]').removeProp('checked');
                }
         }else{
             if($(this).prop('checked')){
                 $('input[name=terms]').prop('checked', 'checked');
                 MCP.alert("귀하가 " +date.format("yyyy-MM-dd")+ " 요청하신 수신동의가<br> 정상적으로 처리 되었습니다.<br>수신동의 철회(무료) : ARS 080-870-9812");
             }else{
                 $('input[name=terms]').removeProp('checked');
             }
         }

        setChkbox();
    });

    $("#promotionNiceAuthBtn").click(function(){

        var birthDate = $.trim(ajaxCommon.isNullNvl($("#birthDate").val(),""));
        var phone = $.trim(ajaxCommon.isNullNvl($("#phone").val(),""));

        objPopup = window.open("about:blank" ,"popupChk", "width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no");

        ajaxCommon.createForm({
            method:"post"
            ,action:'/nice/popNiceAuth.do'
            ,target:"popupChk"
        });
        ajaxCommon.attachHiddenElement("sAuthType","M");
        ajaxCommon.attachHiddenElement("returnUrl","/nice/popNiceSuccM.do");
        ajaxCommon.attachHiddenElement("sCheck","smsAuth");
        ajaxCommon.attachHiddenElement("name",name);
        ajaxCommon.attachHiddenElement("birthDate",birthDate);
        ajaxCommon.formSubmit();
        objPopup.focus();

    });

    $("#promotionCancelbtn, #promotionCancelPop").click(function(){
        $("#promotionPopup").show();
        $("#promotionModal").hide();
    });
});

// sms 인증 완료 후 수행
function fnNiceCert(rtnObj){
    KTM.LoadingSpinner.hide();
    $("#promotionModal").hide();

    var name = ajaxCommon.isNullNvl($.trim($("#name").val()),"");
    var birthDate = ajaxCommon.isNullNvl($.trim($("#birthDate").val()),"");
    if(birthDate.length > 6) birthDate= birthDate.substring(2, 8);

    var varData = ajaxCommon.getSerializedData({
          cstmrName: name
        , cstmrNativeRrn:birthDate
        , reqSeq: rtnObj.reqSeq
        , resSeq: rtnObj.resSeq
    });

    ajaxCommon.getItemNoLoading({
            id:'checkPromotionInfoAjax'
            ,cache:false
            ,async:false
            ,url:'/nice/checkPromotionAuth.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            if (jsonObj.RESULT_CODE == "00000") {

                // 본인인증 정보 disabled 처리
                $("#name").prop('readonly', true);
                $("#phone").prop('readonly', true);
                $("#birthDate").prop('readonly', true);

                prePromotionReg();
            } else {

                $("#name").prop('readonly', false);
                $("#phone").prop('readonly', false);
                $("#birthDate").prop('readonly', false);

                MCP.alert("고객 정보와 본인인증한 정보가 틀립니다.");
                $("#promotionPopup").show();
                return null;
            }
        });
}


function prePromotionReg(){

    var eventCode = $("#eventCode").val();
     var name = $("#name").val();
     var phone = $("#phone").val();
     var agree = "Y";
     var birthDate = $("#birthDate").val();
     // var ci = rtnObj.cid;
     // var di = rtnObj.pin;
     var useTelCode = $("#useTelCode").val();
     var usePayAmt = $("#usePayAmt").val();

       var varData = ajaxCommon.getSerializedData({
           code : eventCode
           , name : name
           , phone : phone
           , agree : agree
           , birthDate : birthDate
           //, ci : ci
           //, di : di
           , useTelCode : useTelCode
           , usePayAmt : usePayAmt
    });

    ajaxCommon.getItem({
        id:'promotionRegAjax'
        ,cache:false
        ,url:'/event/promotionRegAjax.do'
        ,data: varData
        ,dataType:"json"
    }
    ,function(jsonObj){
        var resultCode = jsonObj.resultCode;
        if(resultCode=="S"){
            /*KTM.Confirm('신청이 완료 되었습니다.<br>신청화면을 닫으시겠습니까?' ,function () {
                KTM.Dialog.closeAll(); //모달닫기
            });*/
            MCP.alert('신청이 완료 되었습니다.' ,function () {
                KTM.Dialog.closeAll();
            });
        } else {

            $("#name").prop('readonly', false);
            $("#phone").prop('readonly', false);
            $("#birthDate").prop('readonly', false);

            MCP.alert("신청이 실패 되었습니다.");
            $("#promotionPopup").show();
            return false;
        }
    });

}

var viewErrorMgs = function($thatObj, msg ) {
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") ) {
        $thatObj.parent().parent().addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
};

//약관동의 제어
function setChkbox(data) {
    var cnt = 0;

    var agreeId = $(data).attr("id");

    $('input[name=terms]').each(function (){
        if($(this).prop('checked')){
            cnt++;
        }
    });

    if(cnt == $('input[name=terms]').length){
        $('#btnStplAllCheck').prop('checked', 'checked');
    }else{
        $('#btnStplAllCheck').removeProp('checked');
    }

    if(agreeId=='priAdAgree'){
        if($(priAdAgree).prop('checked')){
            MCP.alert("귀하가 " +date.format("yyyy-MM-dd")+ " 요청하신 수신동의가<br> 정상적으로 처리 되었습니다.<br>수신동의 철회(무료) : ARS 080-870-9812");
        }
    }
}
