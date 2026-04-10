
var interval ;
var clauseScroll;
$(document).ready(function (){

    // 숫자만 입력가능1.
    $(".numOnly").keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });

    // 숫자만 입력가능2
    $(".numOnly").blur(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });

    $("#custNm").keyup(function() {

        if($("#custNm").val() == ""){
            $("#custNmChk").show();
            $("#inputCustNm").addClass("has-error");
            $("#custNm").focus();
        } else{
            $("#custNmChk").hide();
            $("#inputCustNm").removeClass("has-error");
        }

    });

    $("#phoneNum").keyup(function(e) {

        if($("#phoneNum").val().length != 11){
            $("#phoneChk").show();
            $("#inputPhoneNum").addClass("has-error");
            $("#phoneNum").focus();
        } else{
            $("#phoneChk").hide();
            $("#inputPhoneNum").removeClass("has-error");
        }
    });

     $("#regularCertTimeBtn").click(function(){
        $("#timeYn").val('Y');
        $("#timeOverYn").val('');
        certifyCallBtn();
        clearInterval(interval);
        dailyMissionTimer(3);
    });


    onLoadSmsEvent();
});

function onLoadSmsEvent(){

    var custNm= $("#custNm").val();
    if(custNm != null && custNm != ""){
        $("#custNm").prop("disabled",true);
        $("#maskedcustNm").prop("disabled",true);
    }
}
function regularCertBtn(){
    var certify = $("#certify").val();
    var menuType = $("#menuType").val();
    if(certify!="Y"){
        MCP.alert('인증번호를 받으세요.', function (){
            $("#phoneNum" ).focus();
        });


        return false;
    }

    var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
    var certifySms = ajaxCommon.isNullNvl($("#certifySms").val(),"");
    if(certifySms==""){
        MCP.alert('인증번호를 입력해 주세요.', function (){
            $("#phoneNum" ).focus();
        });

        return false;
    }

    if($("#timeOverYn").val() == "Y"){
        MCP.alert("유효시간이 지났습니다. \n 인증번호를 재전송 후 다시 인증해주세요.",function(){
            $("#phoneNum").focus();
        });

        return false;
    }

    var ncType= $("#ncType").val();
    if(ncType == undefined) ncType= "";

    var varData = ajaxCommon.getSerializedData({
        certifySms:$.trim(certifySms)
        ,phone:$.trim(phoneNum)
        ,menuType :menuType
        ,svcCntrNo : $("#svcCntrNo").val()
        ,name:$("#custNm").val()
        ,birthday :ajaxCommon.isNullNvl($("#birthday").val(),"")
        ,ncType : ncType
    });


    ajaxCommon.getItem({
        id:'checkCertSmsAjax'
        ,cache:false
        ,url:'/m/sms/checkCertSmsAjax.do'
        ,data: varData
        ,dataType:"json"
    },function(data){

        if(data.returnCode == "00"){
            MCP.alert("인증이 완료되었습니다.");
            $("#certifyYn").val("Y");
            $("#combiChkYn").val("N");
            $("#timeover").hide();
            $("#countdown").hide();
            $("#countdownTime").hide();
            $("#countdownTime").hide();
            $("#userDivisionYn").val("02");
            $("#btnOk").prop("disabled", false);
            $("#btnOk").removeClass("is-disabled");
            $("#phoneNum").prop("disabled", true);
            $("#certifySms").prop("disabled", true);
            $("#regularCertBtn").prop("disabled", true);
            $("#certifyCallBtn").prop("disabled", true);
            $("#custNm").prop("disabled", true);
            $("#maskedcustNm").prop("disabled", true);
            $("#cstmrNativeRrn01").prop("disabled", true);
            $("#cstmrRun").addClass("is-disabled");
            clearInterval(interval);

            if ("frndReg" == menuType) {
                friendInvitation(); // 인증완료 후 나의 친구초대 추천 URL
            }

            try{
                btn_reg();
            }catch(e){
            }
        } else{
            MCP.alert(data.message);
        }
    }
  );

}
//인증번호 받기
function certifyCallBtn(){

    clearInterval(interval);

    var custNm = ajaxCommon.isNullNvl($("#custNm").val(),"");
    var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
    var cstmrNativeRrn01 = ajaxCommon.isNullNvl($("#cstmrNativeRrn01").val(),"");
    var cstmrNativeRrn02 = ajaxCommon.isNullNvl($("#cstmrNativeRrn02").val(),"");

    if(custNm == "" ){
        MCP.alert('고객명을 입력해 주세요.', function (){
            $("#custNmChk").show();
            $("#inputCustNm").addClass("has-error");
            $("#custNm").focus();
        });

        return true;
    } else {
        $("#custNmChk").hide();
        $("#inputCustNm").removeClass("has-error");

        const encoder = new TextEncoder();
        let custNmByte = encoder.encode(custNm).length;

        if(custNmByte > 90){
            MCP.alert('이름의 길이는 한글은 30자<br> 영어는 60자까지 가능합니다.');
            return false;
        }
    }

    if($('#menuType').val() == 'consentGift'){

        if(cstmrNativeRrn01 == ""){
            MCP.alert('주민등록번호 앞자리를 입력해 주세요.', function (){
                $("#cstmrRun").addClass("has-error");
                $("#cstmrNativeRrn01").focus();
            });

            return true;
        } else {
            $("#cstmrRun").removeClass("has-error");
        }

        if(cstmrNativeRrn02 == ""){
            MCP.alert('주민등록번호 뒷자리를 입력해 주세요.', function (){
                $("#cstmrRun").addClass("has-error");
                $("#cstmrNativeRrn02").focus();
            });

            return true;
        } else {
            $("#cstmrRun").removeClass("has-error");
        }

        if(cstmrNativeRrn01.length < 6){
            MCP.alert('주민등록번호 앞자리를 정확하게 입력해 주세요.', function (){
                $("#cstmrRun").addClass("has-error");
                $("#cstmrNativeRrn01").focus();
            });

            return true;
        } else {
            $("#cstmrRun").removeClass("has-error");
        }

        if(cstmrNativeRrn02.length < 7){
            MCP.alert('주민등록번호 뒷자리를 정확하게 입력해 주세요.', function (){
                $("#cstmrRun").addClass("has-error");
                $("#cstmrNativeRrn02").focus();
            });

            return true;
        } else {
            $("#cstmrRun").removeClass("has-error");
        }
    }

    if(phoneNum == ""){
        MCP.alert('핸드폰 번호를 입력해 주세요.', function (num){
            $("#phoneChk").show();
            $("#inputPhoneNum").addClass("has-error");
            $("#phoneNum").focus();
        });

        return true;
    } else {
        $("#phoneChk").hide();
        $("#inputPhoneNum").removeClass("has-error");
    }

    if(phoneNum.length < 11){
        MCP.alert('핸드폰 번호를 정확하게 입력해 주세요.', function (num){
            $("#phoneChk").show();
            $("#inputPhoneNum").addClass("has-error");
            $("#phoneNum").focus();
        });

        return true;
    }



    var ncType= $("#ncType").val();
    if(ncType == undefined) ncType= "";

    var varData = ajaxCommon.getSerializedData({
        phone:$.trim(phoneNum)
        ,name:$("#custNm").val()
        ,menuType:$("#menuType").val()
        ,timeYn : $("#timeYn").val()
        ,userSsn : $.trim($("#cstmrNativeRrn01").val() + $("#cstmrNativeRrn02").val())
        ,ncType : ncType
    });

    ajaxCommon.getItem({
        id:'sendCertSmsAjax'
        ,cache:false
        ,url:'/m/sms/sendCertSmsAjax.do'
        ,data: varData
        ,dataType:"json"
    },function(data){

        if (AJAX_RESULT_CODE.SUCCESS) {
            if( data.RESULT_CODE == "00000" ){

                MCP.alert("해당 번호로 인증번호를 발송하였습니다.", function(){
                    $("#certifySms").focus();
                });
                $("#countdown").show();
                $("#countdownTime").show();
                $("#certifyCallBtn").text("인증번호 재전송");
                dailyMissionTimer(3);
                $("#certify").val("Y");
                $("#timeOverYn").val('');
                $("#svcCntrNo").val(data.svcCntrNo);

                if (data.AUTH_NUM != null) {
                   $("#certifySms").val(data.AUTH_NUM );
                }
            } else if( data.RESULT_CODE == "00001" ){
                $("#countdown").hide();
                $("#countdownTime").hide();
                MCP.alert(data.message);
                return false;
            } else if(data.RESULT_CODE == "00003" ){
                MCP.alert(data.message, function(){
                    $("#custNm").val("");
                    $("#phoneNum").val("");
                    $("#cstmrNativeRrn01").val("");
                    $("#cstmrNativeRrn02").val("");
                    $("#custNm").prop("disabled",false);
                    $("#custNm").focus();

                });

                return false;

            }else if( data.RESULT_CODE == "00002" ){
                MCP.alert("kt M모바일 고객인 경우에만 이용가능합니다.");
                return false;
            }else if( data.RESULT_CODE =="00010"){
                $("#timeYn").val('N');
            }else{
                var message = data.message;

                if (message != undefined && message != null && message != "") {
                    MCP.alert(message);
                } else {
                    MCP.alert("인증 번호 발송에 실패했습니다.");
                }

                return false;
            }
        } else {
            $("#countdown").hide();
            $("#countdownTime").hide();
            MCP.alert("인증 번호 발송에 실패했습니다.");
            return false;
        }
    }
  );
}

function dailyMissionTimer(duration) {
    $("#timeover").hide();
    $("#countdown").show();
    $("#countdownTime").show();

    var timer = duration  * 60;//받아온 파라미터를 분단위로 계산한다
    var minutes =0;
    var seconds = 0;

    interval = setInterval(function(){
        minutes = parseInt(timer / 60 % 60, 10);
        seconds = parseInt(timer % 60, 10);
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;
        $("#timer").text(minutes+"분 "+seconds+"초");
        if (--timer < 0) {
            timer = 0;
            $("#timer").text("");
            $("#countdown").hide();
            $("#countdownTime").hide();
            $("#timeover").show();
            $("#timeOverYn").val("Y");

            return;
        }
    }, 1000);
}

function friendInvitation() {

    var varData = ajaxCommon.getSerializedData({
        name:$("#custNm").val()
        ,phone:$("#phoneNum").val()
        ,send:"confirm"
        ,mstoreTermAgree:"Y"
    });

    ajaxCommon.getItem({
        id:'frndSendAjax'
        ,cache:false
        ,url:'/m/event/frndSendAjax.do'
        ,data: varData
        ,dataType:"json"
    }
    ,function(data){

        if(data.RESULT_CODE =="STEP01" || data.RESULT_CODE =="STEP02"){  // STEP 오류
            $("input[name='recommend']").val(data.RESULT_MSG);
            return false;
        }

        $("input[name='recommend']").val(data.recommend);
    });

    return false;
}