
var interval ;
var clauseScroll;
$(document).ready(function (){

    // 숫자만 입력가능1
    $(".numOnly").keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });

    // 숫자만 입력가능2
    $(".numOnly").blur(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });

    // 핸드폰번호 유효성 검사
    $("#phoneNum").keyup(function(e) {

        if($("#phoneNum").val().length != 11){
            $("#phoneChk").show();
            $(".inputPhone").addClass("has-error");
            $("#phoneNum").focus();
        } else{
            $(".inputPhone").removeClass("has-error");
            $("#phoneChk").hide();
        }
    });

    // 이름 라벨 처리
    $(document).on("change", "#custNm", function(){
        if($(this).val()){
            $(this).parent().addClass("has-value");
        }else{
            $(this).parent().removeClass("has-value");
        }
    });

    onLoadSmsEvent();

});

function timeReload(){
    $("#timeYn").val('Y');
    $("#timeOverYn").val('');
    certifyCallBtn();
    clearInterval(interval);
    dailyMissionTimer(3);
}

// 로그인 여부에 따른 이름박스 disabled 처리
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
        MCP.alert("인증번호를 받으세요.",function(){
            $("#certifyCallBtn").focus();
        });
        return false;
    }

    var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
    var certifySms = ajaxCommon.isNullNvl($("#certifySms").val(),"");
    if(certifySms==""){
        MCP.alert("인증번호를 입력해 주세요.",function(){
            $("#certifySms").focus()
        });
        return false;
    }else if(certifySms.length != 6){
        MCP.alert("인증번호를 확인해 주세요.",function(){
            $("#certifySms").focus()
        });
        return false;
    }


    if($("#timer").text() == '' || $("#timeover").is(":visible")){
        MCP.alert("유효시간이 지났습니다. \n 인증번호를 재전송 후 다시 인증해주세요.");
        return false;
    }
    var nm = '';
    if($("#custNm").val().indexOf('*') == -1){
        nm = $("#custNm").val();
    }else{
        nm = $("#unMkNm").val();
    }

    if($("#timer").text() == '' || $("#timeover").is(":visible")){
        MCP.alert("유효시간이 지났습니다. \n 인증번호를 재전송 후 다시 인증해주세요.");
        return false;
    }

    var varData = ajaxCommon.getSerializedData({
        certifySms:$.trim(certifySms)
        ,phone:$.trim(phoneNum)
        ,menuType :menuType
        ,svcCntrNo : $("#svcCntrNo").val()
        ,name:nm
        ,contractNum : $("#contractNum").val()
    });


    ajaxCommon.getItem({
        id:'checkCertSmsAjax'
        ,cache:false
        ,url:'/sms/smsAuthCheckAjax.do'
        ,data: varData
        ,dataType:"json"
    },function(data){

        if(data.RESULT_CODE == "00000"){
                MCP.alert("인증이 완료되었습니다.");
                $("#certifyYn").val("Y");
                $("#combiChkYn").val("N");
                $("#timeover").hide();
                $("#countdown").hide();
                $("#countdownTime").hide();
                $("#countdown1").hide();
                $("#custNm").prop("disabled", true);
                $("#maskedcustNm").prop("disabled", true);
                $("#phoneNum").prop("disabled", true);
                $("#maskedPhoneNum").prop("disabled", true);
                $("#certifySms").prop("disabled", true);
                $("#regularCertBtn").prop("disabled", true);
                $("#certifyCallBtn").prop("disabled", true);
                clearInterval(interval);

                if ("frndReg" == menuType) {
                    friendInvitation(); // 인증완료 후 나의 친구초대 추천 URL
                }

        } else{
            MCP.alert(data.MSG);
        }
    }
  );

}
//인증번호 받기
function certifyCallBtn(){
    if(!$("#chkAgreeA").is(":checked")){
        MCP.alert("개인정보 수집 및 이용을 동의해 주세요.",function(){
            $("#chkAgreeA").focus();
        });
        return false;
    }
    clearInterval(interval);

    var custNm = ajaxCommon.isNullNvl($("#custNm").val(),"");
    var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
    $("#certifySms").val('');

    if(custNm == "" ){
        MCP.alert('고객명을 입력해 주세요.', function (){
            $("#custNmChk").show();
            $(".custNmVal").addClass("has-error");
            $("#unMkNm").focus();
        });

        return false;
    } else {
        $("#custNmChk").hide();
        $(".custNmVal").removeClass("has-error");

        const encoder = new TextEncoder();
        let custNmByte = encoder.encode(custNm).length;

        if(custNmByte > 90){
            MCP.alert('이름의 길이는 한글은 30자<br> 영어는 60자까지 가능합니다.');
            return false;
        }
    }


    if(phoneNum == ""){
        MCP.alert('핸드폰 번호를 입력해 주세요.', function (num){
            $("#phoneChk").show();
            $(".inputPhone").addClass("has-error");
            $("#phoneNum").focus();
        });

        return false;
    } else {
        $("#phoneChk").hide();
        $(".inputPhone").removeClass("has-error");

    }

    if(phoneNum.length < 11){
        MCP.alert('핸드폰 번호를 정확하게 입력해 주세요.', function (num){
            $("#phoneChk").show();
            $(".inputPhone").addClass("has-error");
            $("#phoneNum").focus();
        });

        return false;
    }


    var varData = ajaxCommon.getSerializedData({
        phone:$.trim(phoneNum)
        ,name:$("#custNm").val()
        ,menuType:$("#menuType").val()
        ,timeYn : $("#timeYn").val()
        ,contractNum : $("#contractNum").val() ? $("#contractNum").val() : ""
    });

    ajaxCommon.getItem({
        id:'sendCertSmsAjax'
        ,cache:false
        ,url:'/sms/smsAuthAjax.do'
        ,data: varData
        ,dataType:"json"
    },function(data){


        if( data.RESULT_CODE == "00000" ){

            MCP.alert("해당 번호로 인증번호를 발송하였습니다.");
            $("#countdown").show();
            $("#countdownTime").show();
            $("#certifyCallBtn").text("인증번호 재전송");
            dailyMissionTimer(3);
            $("#certify").val("Y");
            $("#svcCntrNo").val(data.svcCntrNo);

            if (data.AUTH_NUM != null) {
                $("#certifySms").val(data.AUTH_NUM );
            }
        } else if( data.RESULT_CODE == "00001" || data.RESULT_CODE == "00003" ){
            $("#countdown").hide();
            $("#countdown1").hide();
            MCP.alert(data.message);
            return false;
        } else if( data.RESULT_CODE == "00002" ){
            MCP.alert("kt M모바일 고객인 경우에만 이용 가능합니다.");
            return false;
        }else if( data.RESULT_CODE =="00010" ){
            $("#timeYn").val('N');
        }else if( data.RESULT_CODE =="AUTH01" || data.RESULT_CODE =="STEP01"){ // AUTH 오류, STEP 오류
            $("#countdown").hide();
            $("#countdown1").hide();
            MCP.alert(data.message);
            return false;
        }else{
            $("#countdown").hide();
            $("#countdown1").hide();
            MCP.alert("인증 번호 발송에 실패했습니다.");
            return false;
        }
    }
  );

}

function dailyMissionTimer(duration) {
    $("#timeover").hide();
    $("#countdown").show();
    $("#countdown1").show();

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
            $("#countdown1").hide();
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
