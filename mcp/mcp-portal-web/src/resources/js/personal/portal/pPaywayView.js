
VALIDATE_NOT_EMPTY_MSG = {};
VALIDATE_NOT_EMPTY_MSG.blBankCode = "은행을 선택 하시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.bankAcctNo = "계좌번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cycleDueDay = "납부 기준일을 선택 하시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.creditCardNo = "신용카드 번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cardMM = "신용카드 유효기간 월을 확인해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cardYY = "신용카드 유효기간 년을 확인해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cardCycleDueDay = "납부 기준일을 선택 하시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.cardCode = "카드사를 선택 하시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.adrZip = "주소 정보를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.adrPrimaryLn = "주소 정보를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.adrSecondaryLn = "주소 정보를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.giroCycleDueDay = "납부 기준일을 선택 하시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.chkAgree = "본인인증 절차에 대한 동의 하여 주시기 바랍니다.";

var pageObj = {
   validReqAccount : false
  ,validReqCard : false
  ,niceType : ""
  ,niceResLogObj : {}
}

$(document).ready(function(){

  initData();  // 화면 초기화

  // 숫자만 입력
  $(".numOnly").keyup(function(){
    $(this).val($(this).val().replace(/[^0-9]/gi, ""));
  });

  // 납부방법변경
  $("input[name=reqPayType]").change(function(){

    var val = $(this).val();

    if(val == "D"){
      // 계좌이체
      $("#bank, #auth").show();
      $("#card, #paper, #kakao, #payCo").hide();
    }else if(val == "C"){
      // 신용카드
      $("#card, #auth").show();
      $("#bank, #paper, #kakao, #payCo").hide();
    }else if(val == "R"){
      // 지로
      $("#paper, #auth").show();
      $("#bank, #card, #kakao, #payCo").hide();
    }else if(val == "K"){
      // 카카오
      $("#kakao").show();
      $("#bank, #card, #paper, #payCo, #auth").hide();
    }else if(val == "P"){
      // 페이코
      $("#payCo").show();
      $("#bank, #card, #paper, #kakao, #auth").hide();
    }

    initData();
  });

  // 계좌번호
  $("#bankAcctNo").keyup(function(){
    isValidateNonEmptyBank();
  });

  // 은행명
  $("#blBankCode").change(function(){
    isValidateNonEmptyBank();
  });

  // 계좌납부기준일
  $("#cycleDueDay").change(function(){
    isValidateNonEmptyBank();
    isValidateBank();
  });

  // 카드번호
  $("#creditCardNo").keyup(function(){
    isValidateNonEmptyCard();
  });

  // 카드정보
  $("#cardCode, #cardMM, #cardYY").change(function(){
    isValidateNonEmptyCard();
  });

  // 카드납부기준일
  $("#cardCycleDueDay").change(function(){
    isValidateNonEmptyCard();
    isValidateCard();
  });

  // 지로납부기준일
  $("#giroCycleDueDay").change(function(){
    isValidateGiro();
  });

  // 본인인증 약관
  $("#chkAgree").change(function(){

    var payMethod = $("input[name=reqPayType]:checked").val();

    if(payMethod == "D") isValidateBank();
    else if(payMethod == "C") isValidateCard();
    else if(payMethod == "R") isValidateGiro();
  });

  // 계좌번호 유효성 체크
  $("#bankCheckBtn").click(function(){

    var blBankCode = $("#blBankCode option:selected").val();
    var bankAcctNo = $("#bankAcctNo").val();

    if(blBankCode == ""){
      MCP.alert("은행을 선택해 주세요.", function(){
        $("#blBankCode").focus();
      });
      return false;
    }

    if(bankAcctNo.length < 10){
      MCP.alert("계좌번호를 확인해 주세요(숫자만 입력)", function(){
        $("#bankAcctNo").focus();
      });
      return false;
    }

    var varData = ajaxCommon.getSerializedData({
       service: "3"
      ,svcGbn: "4"
      ,bankCode: blBankCode
      ,accountNo: bankAcctNo
    });

    ajaxCommon.getItem({
       id: 'accountCheckAjax'
      ,cache: false
      ,url: '/nice/accountCheckAjax.do'
      ,data: varData
      ,dataType: "json"
      ,async: false
    },function(data){

      if(data.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS){
        pageObj.validReqAccount = true;
        MCP.alert("계좌 번호 유효성 검증에 성공하였습니다.");
        $("#bankCheckBtn").prop("disabled", true).text("계좌번호 체크 완료");
        $("#blBankCode").prop("disabled", true);
        $("#bankAcctNo").prop("readOnly", true);
      }else{
        pageObj.validReqAccount = false;
        MCP.alert("계좌 번호 유효성 검증에 실패하였습니다.");
      }

      isValidateBank();
    });
  });

  // 카드번호 유효성 체크
  $("#cardCheckBtn").click(function(){

    var cardCode = $("#cardCode option:selected").val();
    var creditCardNo = $("#creditCardNo").val();
    var cardMM = $("#cardMM option:selected").val();
    var cardYY = $("#cardYY option:selected").val();

    if(cardCode == ""){
      MCP.alert("카드사를 선택해 주세요.", function(){
        $("#cardCode").focus();
      });
      return false;
    }

    if(creditCardNo == ""){
      MCP.alert("카드번호를 입력해 주세요.", function(){
        $("#creditCardNo").focus();
      });
      return false;
    }

    if(creditCardNo.length < 15){
      MCP.alert("신용카드 번호를 확인해 주세요.", function(){
        $("#creditCardNo").focus();
      });
      return false;
    }

    if(cardMM == ""){
      MCP.alert("신용카드 유효기간을 선택해 주세요",function(){
        $("#cardMM").focus();
      });
      return false;
    }

    if(cardYY == ""){
      MCP.alert("신용카드 유효기간을 선택해 주세요",function(){
        $("#cardYY").focus();
      });
      return false;
    }

    var today = Number(new Date().format("yyyyMM"));
    var yyyymm = Number(cardYY + cardMM);
    if(today > yyyymm){
      MCP.alert("신용카드 유효기간을 확인해 주세요", function(){
        $("#cardMM").focus();
      });
      return false;
    }

    if(checkCardNumber(creditCardNo)){
      pageObj.validReqCard = true;
      $("#cardCheckBtn").text("신용카드 체크완료").prop("disabled", true);
      $("#cardCode").prop("disabled", true);
      $("#creditCardNo").prop("readOnly", true);
      $("#cardMM").prop("disabled", true);
      $("#cardYY").prop("disabled", true);
    }else{
      MCP.alert("유용한 신용카드가 아닙니다.");
      pageObj.validReqCard = false;
    }

    isValidateCard();
  });

  // 본인인증 버튼
  $("._btnNiceAuthBut").click(function(){

    if($("#isAuth").val() == "1"){
      MCP.alert("본인 인증을 완료 하였습니다.");
      return false;
    }

    var authType = $(this).data("onlineAuthType");
    if(authType == "M") pop_nice();
    else pop_credit();
  });

  // 취소 버튼
  $(".cancelBtn").click(function(){
    ajaxCommon.createForm({
       method: "post"
      ,action: "/personal/chargeView05.do"
    });

    ajaxCommon.attachHiddenElement("ncn", $("#ncn").val());
    ajaxCommon.formSubmit();
  });

  // 납부방법 변경
  $(".changeBtn").click(function() {

    var reqPayType = $("input[name=reqPayType]:checked").val();
    var isAuth = $("#isAuth").val();
    var varData = null;

    if (isAuth != "1") {
      MCP.alert("본인인증을 받아주세요.");
      return false;
    }

    if (reqPayType == "D") { // 계좌이체

      var cycleDueDay = $("#cycleDueDay").val();

      if (!pageObj.validReqAccount) {
        MCP.alert("계좌번호 유효성 체크를 진행해주세요.");
        return false;
      }

      if (cycleDueDay == "") {
        MCP.alert("납부 기준일을 선택해 주세요.");
        return false;
      }

      varData = ajaxCommon.getSerializedData({
         ncn: $("#ncn").val()
        ,reqPayType: reqPayType
        ,cycleDueDay: cycleDueDay
        ,blBankCode: $("#blBankCode option:selected").val()
        ,bankAcctNo: $("#bankAcctNo").val()
        ,authType: "X"
      });

    } else if (reqPayType == "C") { // 신용카드

      var cardCycleDueDay = $("#cardCycleDueDay").val();
      var cardMM = $("#cardMM option:selected").val();
      var cardYY = $("#cardYY option:selected").val();

      if (!pageObj.validReqCard) {
        MCP.alert("신용카드 유효성 체크를 진행해주세요.");
        return false;
      }

      if (cardCycleDueDay == "") {
        MCP.alert("납부 기준일을 선택해 주세요.");
        return false;
      }

      varData = ajaxCommon.getSerializedData({
         ncn: $("#ncn").val()
        ,reqPayType: reqPayType
        ,cycleDueDay: '99'
        ,cardExpirDate: cardYY + "" + cardMM
        ,creditCardNo: $("#creditCardNo").val()
        ,cardCode: $("#cardCode option:selected").val()
        ,blpymTmsIndCd: cardCycleDueDay
      });

    } else if (reqPayType == "R") { // 지로납부

      var adrZip = $("#adrZip").val();
      var adrPrimaryLn = $("#adrPrimaryLn").val();
      var adrSecondaryLn = $("#adrSecondaryLn").val();
      var giroCycleDueDay = $("#giroCycleDueDay").val();

      if (adrZip == "" || adrPrimaryLn == "" || adrSecondaryLn == "") {
        MCP.alert("청구지 주소를 확인해 주세요.");
        return false;
      }

      if (giroCycleDueDay == "") {
        MCP.alert("납부 기준일을 선택해 주세요.");
        return false;
      }

      varData = ajaxCommon.getSerializedData({
         ncn: $("#ncn").val()
        ,reqPayType: reqPayType
        ,cycleDueDay: giroCycleDueDay
        ,adrZip: adrZip
        ,adrPrimaryLn: adrPrimaryLn
        ,adrSecondaryLn: adrSecondaryLn
      });
    }

    if (varData == null) return false;

    KTM.Confirm("납부방법을 변경하시겠습니까?", function (){

      this.close();

      ajaxCommon.getItem({
         id: 'farChgWayChgAjax'
        ,cache: false
        ,url: '/personal/farChgWayChgAjax.do'
        ,data: varData
        ,dataType: "json"
        ,async: false
      }, function (data) {

        // 개인화 URL 정보 미존재
        if (data.RESULT_CODE == "0001") {
          MCP.alert(data.RESULT_MSG, function () {
            location.href = "/personal/auth.do";
          });
          return false;
        }

        if (data.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS) {
          MCP.alert(data.RESULT_MSG);
          return false;
        }

        MCP.alert("납부방법 변경 요청을 완료 하였습니다.", function () {
          ajaxCommon.createForm({
             method: "post"
            ,action: "/personal/chargeView05.do"
          });

          ajaxCommon.attachHiddenElement("ncn", $("#ncn").val());
          ajaxCommon.formSubmit();
        });
      });
    });
  });

  // 카카오 및 간편결제 SMS 전송
  $(".smsPayBtn").click(function(){

    KTM.Confirm("납부방법을 변경하시겠습니까?", function (){

      this.close();

      var reqPayType = $("input[name=reqPayType]:checked").val();
      var payMethod = $("#payMethod").val();
      var payBizrCd = $("#payBizrCd").val();

      if(reqPayType == "K" && payMethod == "간편결제" && payBizrCd == "KKO"){
        MCP.alert("고객님은 이미 카카오페이로 자동납부중 입니다.");
        return false;
      }

      if(reqPayType == "P" && payMethod == "간편결제" && payBizrCd == "PYC"){
        MCP.alert("고객님은 이미 페이코 자동납부중 입니다.");
        return false;
      }

      var servicePayBizrCd = (reqPayType == "K") ? "KKO" : "PYC";

      var varData = ajaxCommon.getSerializedData({
         ncn: $("#ncn").val()
        ,reqPayType: reqPayType
        ,payBizCd: servicePayBizrCd
      });

      setTimeout(function(){

        ajaxCommon.getItem({
           id: 'sendKakaSmsAjax'
          ,cache: false
          ,async: false
          ,url: '/personal/sendKakaSmsAjax.do'
          ,data: varData
          ,dataType: "json"
        },function(data){

          // 개인화 URL 정보 미존재
          if (data.RESULT_CODE == "0001") {
            MCP.alert(data.RESULT_MSG, function () {
              location.href = "/personal/auth.do";
            });
            return false;
          }

          if (data.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS) {
            var defaultMsg = "자동납부를 위한 SMS 전송이 불가합니다.<br>하단의 ‘②카카오톡 or 카카오페이로 자동납부 신청하기’를 참고하여 신청 부탁드립니다.";
            var errMsg = ajaxCommon.isNullNvl(data.RESULT_MSG, defaultMsg);
            MCP.alert(errMsg);
            return false;
          }

          MCP.alert("SMS가 전송되었습니다.", function(){
            ajaxCommon.createForm({
               method: "post"
              ,action: "/personal/chargeView05.do"
            });

            ajaxCommon.attachHiddenElement("ncn", $("#ncn").val());
            ajaxCommon.formSubmit();
          });
        })
      }, 500);
    });
  });

}); // end of document.ready -------------------------

/** 화면 초기화 */
var initData = function(){

  pageObj.validReqAccount = false;
  pageObj.validReqCard = false;
  $(".changeBtn").prop("disabled", true);

  // 계좌이체 영역
  $("#bankAcctNo").val("").prop("readOnly", false);
  $("#blBankCode option").eq(0).prop("selected", true);
  $("#blBankCode").prop("disabled", false);
  $("#cycleDueDay option").eq(0).prop("selected", true);
  $("#bankCheckBtn").text("계좌번호 유효성 체크").prop("disabled", true);

  // 카드영역
  $("#creditCardNo").val("").prop("readOnly", false);
  $("#cardCode option").eq(0).prop("selected", true);
  $("#cardCode").prop("disabled", false);
  $("#cardMM option").eq(0).prop("selected", true);
  $("#cardMM").prop("disabled", false);
  $("#cardYY option").eq(0).prop("selected", true);
  $("#cardYY").prop("disabled", false);
  $("#cardCycleDueDay option").eq(0).prop("selected", true);
  $("#cardCheckBtn").text("신용카드 유효성 체크").prop("disabled", true);

  // 지로영역
  $("#giroCycleDueDay option").eq(0).prop("selected", true);
}

/** 계좌번호 유효성 체크 버튼 활성화 조건 */
var isValidateNonEmptyBank = function(){

  // 필수값 검사
  validator.config = {};
  validator.config['bankAcctNo'] = 'isNonEmpty';
  validator.config['blBankCode'] = 'isNonEmpty';
  validator.config['cycleDueDay'] = 'isNonEmpty';

  if(!validator.validate(true)){
    $("#bankCheckBtn").prop("disabled", true);
    return false;
  }

  // 추가제약
  var bankAcctNo = $("#bankAcctNo").val();
  if(bankAcctNo.length < 10 || pageObj.validReqAccount){
    $("#bankCheckBtn").prop("disabled", true);
    return false;
  }

  $("#bankCheckBtn").prop("disabled", false);
  return true;
}

/** 계좌이체 필수값 최종 검사 */
var isValidateBank = function(){

  // 필수값 검사
  validator.config = {};
  validator.config['bankAcctNo'] = 'isNonEmpty';
  validator.config['blBankCode'] = 'isNonEmpty';
  validator.config['cycleDueDay'] = 'isNonEmpty';
  validator.config['chkAgree'] = 'isNonEmpty';

  if(!validator.validate(true)){
    $(".changeBtn").prop("disabled", true);
    return false;
  }

  var bankAcctNo = $("#bankAcctNo").val();
  if(bankAcctNo.length < 10){
    $(".changeBtn").prop("disabled", true);
    return false;
  }

  var isAuth = $("#isAuth").val();
  if(!pageObj.validReqAccount || isAuth != "1"){
    $(".changeBtn").prop("disabled", true);
    return false;
  }

  $(".changeBtn").prop("disabled", false);
  return true;
}

/** 신용카드 유효성 체크 버튼 활성화 조건 */
var isValidateNonEmptyCard = function(){

  // 필수값 검사
  validator.config = {};
  validator.config['cardCode'] = 'isNonEmpty';
  validator.config['creditCardNo'] = 'isNonEmpty';
  validator.config['cardCycleDueDay'] = 'isNonEmpty';
  validator.config['cardMM'] = 'isNonEmpty';
  validator.config['cardYY'] = 'isNonEmpty';

  if(!validator.validate(true)){
    $("#cardCheckBtn").prop("disabled", true);
    return false;
  }

  // 추가제약
  var creditCardNo = $("#creditCardNo").val();
  if(creditCardNo.length < 15 || pageObj.validReqCard){
    $("#cardCheckBtn").prop("disabled", true);
    return false;
  }

  $("#cardCheckBtn").prop("disabled", false);
  return true;
}

/** 신용카드 필수값 최종 검사 */
var isValidateCard = function(){

  // 필수값 검사
  validator.config = {};
  validator.config['cardCode'] = 'isNonEmpty';
  validator.config['creditCardNo'] = 'isNonEmpty';
  validator.config['cardCycleDueDay'] = 'isNonEmpty';
  validator.config['cardMM'] = 'isNonEmpty';
  validator.config['cardYY'] = 'isNonEmpty';
  validator.config['chkAgree'] = 'isNonEmpty';

  if(!validator.validate(true)){
    $(".changeBtn").prop("disabled", true);
    return false;
  }

  var creditCardNo = $("#creditCardNo").val();
  if(creditCardNo.length < 15){
    $(".changeBtn").prop("disabled", true);
    return false;
  }

  var isAuth = $("#isAuth").val();
  if(!pageObj.validReqCard || isAuth != "1"){
    $(".changeBtn").prop("disabled", true);
    return false;
  }

  $(".changeBtn").prop("disabled", false);
  return true;
}

/** 지로 필수값 최종 검사 */
var isValidateGiro = function(){

  // 필수값 검사
  validator.config = {};
  validator.config['adrZip'] = 'isNonEmpty';
  validator.config['adrPrimaryLn'] = 'isNonEmpty';
  validator.config['adrSecondaryLn'] = 'isNonEmpty';
  validator.config['giroCycleDueDay'] = 'isNonEmpty';
  validator.config['chkAgree'] = 'isNonEmpty';

  if(!validator.validate(true)){
    $(".changeBtn").prop("disabled", true);
    return false;
  }

  var isAuth = $("#isAuth").val();
  if(isAuth != "1"){
    $(".changeBtn").prop("disabled", true);
    return false;
  }

  $(".changeBtn").prop("disabled", false);
  return true;
}

/** 주소 콜백 */
var jusoCallBack = function(roadFullAddr, roadAddrPart1, addrDetail, roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){
  $("#adrZip").val(zipNo);                //우편번호
  $("#adrPrimaryLn").val(roadAddrPart1);  //메인주소
  $("#adrSecondaryLn").val(addrDetail);   //상세주소
  isValidateGiro();
}

/** 카드번호 유효성 체크 */
var checkCardNumber = function(cardNumber){

  var digits = cardNumber.split('');
  for (var i = 0; i < digits.length; i++) {
    digits[i] = parseInt(digits[i], 10);
  }

  // 룬 알고리즘 실행
  var sum = 0;
  var alt = false;
  for (var i = digits.length - 1; i >= 0 ; i-- ) {
    if (alt) {
      digits[i] *= 2;
      if(digits[i] > 9) {
        digits[i] -= 9;
      }
    }
    sum += digits[i];
    alt = !alt;
  }

  return (sum % 10 == 0);
}

/** 휴대폰 인증창 요청 */
var pop_nice = function(){
  pageObj.niceType = NICE_TYPE.CUST_AUTH;
  var data = {width:'500', height:'700'};
  openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M', data);
}

/** 신용카드 인증창 요청 */
var pop_credit = function(){
  pageObj.niceType = NICE_TYPE.CUST_AUTH;
  var data = {width:'500', height:'700'};
  openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=C', data);
}

/** 본인인증 콜백 (휴대폰 및 신용카드) */
var fnNiceCert = function(prarObj){

  pageObj.niceResLogObj = prarObj;

  var pageType = $("#pageType").val();
  var ncn = $("#ncn").val();

  var authResult = personalAuthCallback(pageType, ncn);

  if(authResult.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS){

    $("#isAuth").val("1");

    if(prarObj.authType == "C"){
      $("._btnNiceAuthBut").eq(1).addClass("is-active");
      $("._btnNiceAuthBut").eq(1).html('<i class="c-icon c-icon--card" aria-hidden="true"></i>신용카드 인증완료');
      $("._btnNiceAuthBut").eq(0).prop('disabled', 'true');
    }else{
      $("._btnNiceAuthBut").eq(0).addClass("is-active");
      $("._btnNiceAuthBut").eq(0).html('<i class="c-icon  c-icon--phone" aria-hidden="true"></i>휴대폰 인증완료');
      $("._btnNiceAuthBut").eq(1).prop('disabled', 'true');
    }

    var payMethod = $("input[name=reqPayType]:checked").val();

    if(payMethod == "D") isValidateBank();
    else if(payMethod == "C") isValidateCard();
    else if(payMethod == "R") isValidateGiro();

  }else if(authResult.RESULT_CODE == "0001"){
    // 개인화 URL 정보 미존재
    MCP.alert(authResult.RESULT_MSG, function(){
      location.href = "/personal/auth.do";
    });
  }else{
    // 기타 오류
    MCP.alert(authResult.RESULT_MSG);
  }
}