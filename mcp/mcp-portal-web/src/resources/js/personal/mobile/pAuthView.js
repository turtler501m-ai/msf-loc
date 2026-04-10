
var pageObj = {
  interval : null
}

$(document).ready(function() {

  $(".footer-info").siblings("button").hide();

  $("#maskedPhoneNum").prop("disabled",true);
  $("#countdown").hide();
  $("#countdownTime").hide();
  $("#timeover").hide();

  // 숫자만 입력
  $(".numOnly").keyup(function(){
    $(this).val($(this).val().replace(/[^0-9]/gi, ""));
  });

  // 숫자만 입력
  $(".numOnly").blur(function(){
    $(this).val($(this).val().replace(/[^0-9]/gi, ""));
  });

  // 시간연장
  $("#regularCertTimeBtn").click(function(){
    $("#timeYn").val('Y');
    $("#timeOverYn").val('');
    certifyCallBtn();
    clearInterval(pageObj.interval);
    dailyMissionTimer(3);  // 타이머 초기화
  });

  // 인증번호 확인
  $("#regularCertBtn").click(function(){

    var certify = $("#certify").val();
    var timeOverYn = $("#timeOverYn").val();
    var certifySms = ajaxCommon.isNullNvl($("#certifySms").val(),"");

    if(certify != "Y"){
      MCP.alert("인증번호를 받으세요.",function(){
        $("#maskedPhoneNum").focus();
      });
      return false;
    }
    
    if(timeOverYn == "Y"){
      MCP.alert("유효시간이 지났습니다.<br>인증번호를 재전송 후 다시 인증해주세요.",function(){
        $("#maskedPhoneNum").focus();
      });
      return false;
    }
    
    if(certifySms == ""){
      MCP.alert("인증번호를 입력해 주세요.",function(){
        $("#certifySms").focus()
      });
      return false;
    }

    smsAuthCheck();  // 인증번호 확인
  });

}); // end of document.ready -----------------------

/** 인증번호 받기 */
var certifyCallBtn = function(){

  var maskedPhoneNum = ajaxCommon.isNullNvl($("#maskedPhoneNum").val(),"");

  if(maskedPhoneNum == ""){
    MCP.alert('핸드폰 번호를 입력해 주세요.', function (){$("#phoneChk").show();});
    return false;
  }

  $("#phoneChk").hide();

  var varData = ajaxCommon.getSerializedData({
      maskedPhoneNum : $.trim(maskedPhoneNum)
     ,pageType : $("#pageType").val()
     ,landing : $("#landing").val()
     ,seq : $("#seq").val()
     ,timeYn : $("#timeYn").val()
  });

  ajaxCommon.getItem({
     id: 'sendSmsAjax'
    ,cache: false
    ,url: '/m/personal/sendSmsAjax.do'
    ,data: varData
    ,dataType: "json"
  },function(data){

    if(data.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS){
      MCP.alert("해당 번호로 인증번호를 발송하였습니다.");
      $("#countdown").show();
      $("#countdownTime").show();
      $("#certifyCallBtn").text("인증번호 재전송");
      clearInterval(pageObj.interval);
      dailyMissionTimer(3);
      $("#certify").val("Y");
      $("#certifySms").focus();
      $("#timeOverYn").val('');
      if(data.AUTH_NUM != null){
        $("#certifySms").val(data.AUTH_NUM);
      }
    }else if(data.RESULT_CODE == "00005"){  // 시간연장
      $("#timeYn").val('N');
    }else{ // 인증번호 발송 실패
      var errMsg =  ajaxCommon.isNullNvl(data.message, "인증 번호 발송에 실패했습니다.");
      MCP.alert(errMsg);
    }
  });
}

/** 인증 타이머 */
var dailyMissionTimer = function(duration){

  $("#timeover").hide();
  $("#countdown").show();

  var timer = duration * 60; // 분단위 계산
  var minutes = 0;
  var seconds = 0;

  pageObj.interval = setInterval(function(){
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
      $("#btn_reg").prop("disabled", true);
      return;
    }
  }, 1000);
}

/** 인증번호 확인 */
var smsAuthCheck = function(){

  var varData = ajaxCommon.getSerializedData({
     certifySms : ajaxCommon.isNullNvl($("#certifySms").val(),"")
    ,pageType : $("#pageType").val()
  });

  ajaxCommon.getItem({
     id: 'checkSmsAuthAjax'
    ,cache: false
    ,url: '/m/personal/checkSmsAuthAjax.do'
    ,data: varData
    ,dataType: "json"
  }
  ,function(data){

    if(data.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS){
        $("#certifyYn").val("Y");
        $("#timeover").hide();
        $("#countdown").hide();
        $("#countdownTime").hide();
        $("#maskedPhoneNum").prop("disabled", true);
        $("#certifySms").prop("disabled", true);
        $("#regularCertBtn").prop("disabled", true);
        $("#certifyCallBtn").prop("disabled", true);
        $("#btn_reg").prop("disabled",false);
        clearInterval(pageObj.interval);
    }else{
      var errMsg =  ajaxCommon.isNullNvl(data.message, "인증번호 확인에 실패했습니다.");
      MCP.alert(errMsg);
      return false;
    }
  });
}

/** 인증 후 확인버튼 */
var btn_reg = function(){

  var certifyYn = $("#certifyYn").val();
  if(certifyYn != "Y"){
    MCP.alert("인증을 진행해주세요.");
  }

  ajaxCommon.createForm({
     method:"post"
    ,action:"/m/personal/completeSmsAuth.do"
  });

  ajaxCommon.attachHiddenElement("seq", $("#seq").val());
  ajaxCommon.attachHiddenElement("pageType", $("#pageType").val());
  ajaxCommon.attachHiddenElement("landing", $("#landing").val());
  ajaxCommon.formSubmit();
}
