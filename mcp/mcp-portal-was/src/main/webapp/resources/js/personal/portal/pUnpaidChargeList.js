
var pageObj = {
   confirmMsg : "자동납부를 이용하시는 경우,<br>당월 청구금액 납부 시 이중 납부가 될 수 있으니"+
                "<br>납부일 및 계좌/카드 거래내역 확인 후 이용하시기 바랍니다. 요금을 납부하시겠습니까?"
  ,niceType : ""
  ,niceResLogObj : {}
}

$(document).ready(function (){

  // 납부가능 금액 조회
  if($("#customerType").val() != "Y"){
    searchChg();
  }

  $("#totalCnt").html("<b>0</b> 원");

  // 숫자만 입력
  $(".numOnly").keyup(function(){
    $(this).val($(this).val().replace(/[^0-9]/gi, ""));
  });

  // 납부금액 입력 시 콤마처리
  $("#pay").keyup(function(){
    $(this).val(numberWithCommas($(this).val()));
  });

  // 전액납부 버튼
  $("#chkAll").change(function(){

    var isChecked = $("input[name=chkAll]:checked").val();
    var totalCntReal = $("#totalCntReal").val();

    if(totalCntReal == "0"){
      MCP.alert('납부하실 금액이 없습니다.', function(){
        $("#chkAll").prop("checked", false);
        $("#pay").focus();
      });
      return false;
    }

    if(isChecked){
      $("#pay").prop("disabled", true);
      $("#pay").val(numberWithCommas(totalCntReal));
    }else{
      $("#pay").prop("disabled", false);
      $("#pay").val("");
    }
  });

}); // end of document.ready -------------------------

/** 납부가능 금액 조회 */
var searchChg = function(){

  var varData = ajaxCommon.getSerializedData({
    ncn : $("#ctn option:selected").val()
  });

  ajaxCommon.getItem({
     id: 'unpaidChargeListAjax'
    ,cache: false
    ,url: '/personal/unpaidChargeListAjax.do'
    ,data: varData
    ,dataType: "json"
  },function(data){

    $("#pay").val("");
    $("#chkAll").prop("checked", false);

    // 개인화 URL 정보 미존재
    if(data.RESULT_CODE == "0001"){
      MCP.alert(data.RESULT_MSG, function(){
        location.href = "/personal/auth.do";
      });
      return false;
    }

    // 납부가능 금액 조회 오류
    if(data.RESULT_CODE != "S"){
      $("#customerTypeNoData").hide();
      $(".unpaidNoDataView").hide();
      $(".chargeView1").hide();
      $("#totalCntView1").hide();
      $("#totalCntView2").hide();

      if(data.RESULT_CODE == "0002"){
        $("#customerTypeNoData").show();
      }else{
        MCP.alert(data.RESULT_MSG);
      }
      return false;
    }

    var totNpayAmt = data.totNpayAmt;         // 총미납요금
    var currMthNpayAmt = data.currMthNpayAmt; // 당월 미납요금
    var payTgtAmt = data.payTgtAmt;           // 납입대상요금

    // 납부가능 요금 미존재
    if(payTgtAmt == "" || payTgtAmt == "0"){
      $(".unpaidNoDataView").show();
      $(".chargeView1").hide();
      $("#totalCnt").html("<b>0</b> 원");
      $("#totalCntView1").show();
      $("#totalCntView2").show();
      return false;
    }

    // 화면 생성
    var html= '';

    if(currMthNpayAmt != "0"){
      html += `<tr>`;
      html += `  <td>당월금액</td>`;
      html += `  <td class="u-ta-right">${numberWithCommas(currMthNpayAmt)}원</td>`;
      html += `</tr>`;
    }

    if(totNpayAmt != "0"){
      html += `<tr>`;
      html += `  <td>미납금액</td>`;
      html += `  <td class="u-ta-right">${numberWithCommas(totNpayAmt)}원</td>`;
      html += `</tr>`;
    }

    $("#realtimePayView").html(html);
    $(".unpaidNoDataView").hide();
    $(".chargeView1").show();
    $("#totalCnt").html(`<b>${numberWithCommas(payTgtAmt)}</b> 원`);
    $("#totalCntReal").val(payTgtAmt);
    $("#totalCntView1").show();
    $("#totalCntView2").show();
  });
}

/** 입력데이터 유효성검사 */
var payCertify = function(){

  var totalCntReal = $("#totalCntReal").val();
  var pay = $("#pay").val().replaceAll(",","");

  if(pay == "" || pay == "0"){
    MCP.alert('납부하실 금액을 입력해 주세요.', function(){
      $("#pay").focus();
    });
    return false;
  }

  if(parseFloat(pay) > parseFloat(totalCntReal)){
    MCP.alert('입력한 금액이 납부 가능 금액보다 큽니다.', function(){
      $("#pay").focus();
    });
    return false;
  }

  return true;
}

/** 휴대폰 인증 */
var auth_phone = function(){

  if(!payCertify()) return;

  KTM.Confirm(pageObj.confirmMsg, function(){
    pop_nice();
    this.close();
  });
}

/** 신용카드 인증 */
var auth_credit = function(){

  if(!payCertify()) return;

  KTM.Confirm(pageObj.confirmMsg, function(){
    pop_credit();
    this.close();
  });
}

/** 아이핀 인증 */
var auth_ipin = function(){

  if(!payCertify()) return;

  KTM.Confirm(pageObj.confirmMsg, function(){
    pop_ipin();
    this.close();
  });
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

/** 아이핀 인증창 요청 */
var pop_ipin = function(){
  pageObj.niceType = NICE_TYPE.CUST_AUTH;
  var data = {width:'500', height:'700'};
  openPage('outLink2', '/nice/popNiceIpinAuth.do', data);
}

/** 본인인증 콜백 (휴대폰 및 신용카드) */
var fnNiceCert = function(prarObj){
  personalAuthResult(prarObj);
}

var fnNiceCertErr = function(){return false;}

/** 본인인증 콜백 (아이핀) */
var fnNiceIpinCert = function(prarObj){
  personalAuthResult(prarObj);
}

var fnNiceIpinCertErr = function(){return false;}

/** 본인인증 확인 */
var personalAuthResult = function(prarObj){

  pageObj.niceResLogObj = prarObj;

  var pageType = $("#pageType").val();
  var ncn = $("#ctn option:selected").val();

  var authResult = personalAuthCallback(pageType, ncn);

  if(authResult.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS){

    // 즉시납부 페이지로 이동
    ajaxCommon.createForm({
       method: "post"
      ,action: "/personal/realTimePaymentView.do"
    });

    ajaxCommon.attachHiddenElement("payMentMoney", $("#pay").val());
    ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
    ajaxCommon.formSubmit();

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

/** 회선 조회 */
var select = function(){

  ajaxCommon.createForm({
     method: "post"
    ,action: "/personal/unpaidChargeList.do"
  });

  ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
  ajaxCommon.formSubmit();
}