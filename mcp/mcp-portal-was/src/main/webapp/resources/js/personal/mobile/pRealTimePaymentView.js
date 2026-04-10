
VALIDATE_NOT_EMPTY_MSG = {};
VALIDATE_NOT_EMPTY_MSG.inpCardnum = "신용카드 번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.reqCardMmTmp = "신용카드 유효기간 월을 확인해 주세요.";
VALIDATE_NOT_EMPTY_MSG.reqCardYyTmp = "신용카드 유효기간 년을 확인해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cardPwd = "신용카드 비밀번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.chkAgree = "카드 인출에 대한 동의 하여 주시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.blBankCode = "은행을 선택 하시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.bankAcctNo = "계좌번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.chkAgree2 = "계좌 인출에 대한 동의 하여 주시기 바랍니다.";

$(document).ready(function(){

  $(".footer-info").siblings("button").hide();

  $(".payTransfer").hide();
  $(".payKakao").hide();
  $(".payPayco").hide();
  $(".payNaver").hide();

  // 숫자만 입력
  $(".numOnly").keyup(function(){
    $(this).val($(this).val().replace(/[^0-9]/gi, ""));
  });

  // 신용카드 유효기간, 동의
  $("#reqCardMmTmp, #reqCardYyTmp, #chkAgree").change(function(){
    isValidateNonEmptyCard();
  });

  // 카드번호, 카드비밀번호
  $("#inpCardnum, #cardPwd").keyup(function(){
    isValidateNonEmptyCard();
  });

  // 은행명, 동의
  $("#blBankCode, #chkAgree2").change(function(){
    isValidateNonEmptyTransfer();
  });

  // 계좌번호
  $("#bankAcctNo").keyup(function(){
    isValidateNonEmptyTransfer();
  });

  // 납부방법 변경
  $("#paySelect").change(function(){
    var paySel =$("#paySelect option:selected").val();

    if(paySel == 'C'){ // 신용카드
      $(".payCard").show();
      $(".payTransfer").hide();
      $(".payKakao").hide();
      $(".payPayco").hide();
      $(".payNaver").hide();
    } else if(paySel == 'K'){ // 카카오페이
      $(".payCard").hide();
      $(".payTransfer").hide();
      $(".payKakao").show();
      $(".payPayco").hide();
      $(".payNaver").hide();
    } else if(paySel == 'P'){ // 페이코
      $(".payCard").hide();
      $(".payTransfer").hide();
      $(".payKakao").hide();
      $(".payPayco").show();
      $(".payNaver").hide();
    } else if(paySel == 'N'){ // 네이버페이
      $(".payCard").hide();
      $(".payTransfer").hide();
      $(".payKakao").hide();
      $(".payPayco").hide();
      $(".payNaver").show();
    } else if(paySel=='D'){ // 계좌이체
      $(".payCard").hide();
      $(".payTransfer").show();
      $(".payKakao").hide();
      $(".payPayco").hide();
      $(".payNaver").hide();
    }
  });

}); // end of document.ready -----------------------

/** 카드납부 필수값 검사 */
var isValidateNonEmptyCard = function(){

  validator.config={};
  validator.config['inpCardnum'] = 'isNonEmpty';
  validator.config['reqCardMmTmp'] = 'isNonEmpty';
  validator.config['reqCardYyTmp'] = 'isNonEmpty';
  validator.config['cardPwd'] = 'isNonEmpty';
  validator.config['chkAgree'] = 'isNonEmpty';

  if (validator.validate(true)) {
    $('#btn_payReg').removeClass('is-disabled');
  } else {
    $('#btn_payReg').addClass('is-disabled');
  }
}

/** 카드납부 필수값 최종 검사 */
var isValidateCard = function(){

  // 필수값 확인
  validator.config={};
  validator.config['inpCardnum'] = 'isNonEmpty';
  validator.config['reqCardMmTmp'] = 'isNonEmpty';
  validator.config['reqCardYyTmp'] = 'isNonEmpty';
  validator.config['cardPwd'] = 'isNonEmpty';
  validator.config['chkAgree'] = 'isNonEmpty';

  if(!validator.validate(true)){
    MCP.alert(validator.getErrorMsg());
    return false;
  }

  // 추가제약 확인
  var inpCardnum = $("#inpCardnum").val();
  if(inpCardnum.length < 15){
    MCP.alert('신용카드 번호를 확인해 주세요.', function(){
      $("#inpCardnum").focus();
    });
    return false;
  }

  var cardPwd = $("#cardPwd").val();
  if(cardPwd.length != 2){
    MCP.alert('신용카드 비밀번호는 두자리까지 입력해 주세요.', function(){
      $('#cardPwd').focus();
    });
    return false;
  }

  var payments = $("#payments").val(); // 할부기간
  var payMentMoney = $("#payMentMoney").val().replaceAll(",","");

  if(payments != "0" && payMentMoney < 50000){
    MCP.alert('할부는 5만원 이상부터 가능합니다.', function(){
      $("#payments").focus();
    });
    return false;
  }

  var today = $("#sysYear").val();
  var cardTmp = $("#reqCardYyTmp").val() + $("#reqCardMmTmp").val();

  if(today > cardTmp){
    MCP.alert('신용카드 유효기간 년도를 확인해 주세요.', function(){
      $('#reqCardYyTmp').focus();
    });
    return false;
  }

  return true;
}

/** 계좌이체 필수값 검사 */
var isValidateNonEmptyTransfer = function(){

  validator.config={};
  validator.config['blBankCode'] = 'isNonEmpty';
  validator.config['bankAcctNo'] = 'isNonEmpty';
  validator.config['chkAgree2'] = 'isNonEmpty';

  if (validator.validate(true)) {
    $('#btnTransfer').removeClass('is-disabled');
  } else {
    $('#btnTransfer').addClass('is-disabled');
  }
}

/** 계좌이체 필수값 최종 검사 */
var isValidateTransfer = function(){

  // 필수값 확인
  validator.config={};
  validator.config['blBankCode'] = 'isNonEmpty';
  validator.config['bankAcctNo'] = 'isNonEmpty';
  validator.config['chkAgree2'] = 'isNonEmpty';

  if(!validator.validate(true)){
    MCP.alert(validator.getErrorMsg());
    return false;
  }

  return true;
}

/** 즉시납부 취소 */
var btn_cancel = function(){
  KTM.Confirm("작성중인 납부정보가 취소됩니다.<br>즉시납부를 취소하시겠습니까?", function(){
    ajaxCommon.createForm({
       method: "post"
      ,action: "/m/personal/unpaidChargeList.do"
    });

    ajaxCommon.attachHiddenElement("ncn", $("#ncn").val());
    ajaxCommon.formSubmit();
  });
}

/** 즉시납부 신청 */
var btn_payReg = function(payType){

  var varData = null;

  if(payType == "C"){ // 신용카드

    if(!isValidateCard()) return;

    var payments = $("#payments").val();
    var paymentsSel = (payments == "0") ? "일시불" : payments + "개월";

    var confirmMsg = "신용/체크카드 납부정보 입니다.<br>"
      + "● 납부금액 : " + $("#payMentMoney").val() + "<br>"
      + "● 카드번호 : " + $("#inpCardnum").val() + "<br>"
      + "● 카드 유효기간 : " + $("#reqCardMmTmp").val() + "/" + $("#reqCardYyTmp").val() + "<br>"
      + "● 할부기간 : " + paymentsSel + "<br>"
      + "수납하시겠습니까?";

    KTM.Confirm(confirmMsg, function(){

      var cardInstMnthCnt = (payments == "0") ? "" : payments;

      varData = ajaxCommon.getSerializedData({
         ncn: $("#ncn").val()
        ,payMentMoney: $("#payMentMoney").val()
        ,blMethod: payType
        ,cardNo: $("#inpCardnum").val().trim()
        ,cardExpirDate: $("#reqCardYyTmp").val() + $("#reqCardMmTmp").val()
        ,cardPwd: $("#cardPwd").val()
        ,cardInstMnthCnt: cardInstMnthCnt
        ,rmnyChId: $("#paySelect").val()
      });

      btn_payRegApply(payType, varData);
      this.close();
    });

  }else if(payType == "D"){ // 계좌이체

    if(!isValidateTransfer()) return;

    var bankAcctNo = $("#bankAcctNo").val();
    bankAcctNo = bankAcctNo.replace(/([0-9]{6})$/gi,"******");

    var confirmMsg = "계좌이체 납부 정보입니다.<br>"
      + "● 납부금액 : " + $("#payMentMoney").val() + "<br>"
      + "● 계좌번호 : " + bankAcctNo + "<br>"
      + "수납하시겠습니까?";

    KTM.Confirm(confirmMsg, function(){

      varData = ajaxCommon.getSerializedData({
        ncn: $("#ncn").val()
        ,payMentMoney: $("#payMentMoney").val()
        ,blMethod: payType
        ,blBankCode: $("#blBankCode").val()
        ,bankAcctNo: $("#bankAcctNo").val().trim()
        ,agrDivCd: "03"
        ,myslfAthnTypeItgCd: "01"
      });

      btn_payRegApply(payType, varData);
      this.close();
    });

  }else{ // 카카오페이, 페이코, 네이버페이
    varData = ajaxCommon.getSerializedData({
      ncn: $("#ncn").val()
      ,payMentMoney: $("#payMentMoney").val()
      ,blMethod: "P"
      ,rmnyChId: payType
    });

    btn_payRegApply(payType, varData);
  }
}

/** 즉시납부 신청 및 후처리 */
var btn_payRegApply = function(payType, varData){

  ajaxCommon.getItem({
    id: 'insertRealTimePaymentAjax'
    ,cache: false
    ,url: '/personal/insertRealTimePaymentAjax.do'
    ,data: varData
    ,dataType: "json"
  }, function(data){

    // 개인화 URL 정보 미존재
    if(data.RESULT_CODE == "0001"){
      MCP.alert(data.RESULT_MSG, function(){
        location.href = "/m/personal/auth.do";
      });
      return false;
    }

    if(data.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
      MCP.alert(data.RESULT_MSG);
      return false;
    }

    if(payType == "C" || payType == "D"){ // 신용카드 또는 계좌이체

      ajaxCommon.createForm({
        method: "post"
        ,action: "/personal/realTimePaymentCompleteView.do"
      });

      ajaxCommon.attachHiddenElement("rmnyChId", payType);
      ajaxCommon.attachHiddenElement("payMentMoney", $("#payMentMoney").val());
      ajaxCommon.attachHiddenElement("ncn", $("#ncn").val());
      ajaxCommon.formSubmit();

    }else{ // 카카오페이, 페이코, 네이버페이

      ajaxCommon.createForm({
        method: "post"
        ,action: "/personal/unpaidChargeList.do"
      });

      ajaxCommon.attachHiddenElement("ncn", $("#ncn").val());
      ajaxCommon.formSubmit();
    }
  });
}