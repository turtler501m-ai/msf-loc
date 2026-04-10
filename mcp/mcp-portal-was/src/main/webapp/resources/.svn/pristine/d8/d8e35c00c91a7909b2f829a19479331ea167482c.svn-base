;

VALIDATE_NOT_EMPTY_MSG.cstmrName = "신청자 성명을 입력해 주세요.";
VALIDATE_NUMBER_MSG.cstmrTelFn = "신청자 휴대폰 번호가 형식에 맞지 않습니다.";
VALIDATE_NUMBER_MSG.cstmrTelMn = "신청자 휴대폰 번호가 형식에 맞지 않습니다.";
VALIDATE_NUMBER_MSG.cstmrTelRn = "신청자 휴대폰 번호가 형식에 맞지 않습니다.";
VALIDATE_FIX_MSG.cstmrTelFn = "신청자 휴대폰 번호가 형식에 맞지 않습니다.";
VALIDATE_FIX_MSG.cstmrTelMn = "신청자 휴대폰 번호가 형식에 맞지 않습니다.";
VALIDATE_FIX_MSG.cstmrTelRn = "신청자 휴대폰 번호가 형식에 맞지 않습니다.";

var pageObj = {
   niceType: ""
  ,startTime: 0
  ,niceResLogObj: {}
  ,checkCbCnt: 0
  ,isCallbackIng: false
}

$(document).ready(function(){

  $(".footer-info").siblings("button").hide();

  // 유심번호 유효성 체크
  $('#btnUsimNumberCheck').click(function(){

    if($("#isUsimNumberCheck").val() == "1") {
      MCP.alert("유심번호 유효성 체크 하였습니다.");
      return false;
    }

    validator.config={};
    validator.config["reqUsimSn"] = 'isNumFix19';
    if (!validator.validate(true)) {
      var errId = validator.getErrorId();
      MCP.alert(validator.getErrorMsg(),function (){
        $("#"+errId).focus();
      });
      return false;
    }

    var varData = ajaxCommon.getSerializedData({
      iccId: $.trim($("#reqUsimSn").val())
    });

    ajaxCommon.getItem({
       id:'moscIntmMgmtAjax'
      ,cache:false
      ,url:'/msp/moscIntmMgmtAjax.do'
      ,data: varData
      ,dataType:"json"
      ,errorCall : function () {
        MCP.alert("유심번호 유효성 체크가 불가능합니다.<br>잠시 후 다시 시도하시기 바랍니다.");
      }
    }
    ,function(jsonObj){
        if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
          $("#isUsimNumberCheck").val("1");
          MCP.alert("입력하신 유심번호 사용 가능합니다.", function(){
            $("#reqUsimSn").prop("disabled", true);
            $('#btnUsimNumberCheck').addClass('is-complete').html("유심번호 유효성 체크 완료");
            $("#divUsimScan").hide();
          });
        }else{
          var errMsg = jsonObj.RESULT_MSG || "사용이 불가한 유심입니다.<br>다른 유심으로 시도 바랍니다.";
          MCP.alert(errMsg);
        }
      });
  });

  // 본인인증 전 추가 유효성 검사
  simpleAuthvalidation = function(){

    validator.config = {};
    validator.config["cstmrName"] = 'isNonEmpty';
    validator.config["cstmrTelFn"] = 'isNonEmpty';
    validator.config["cstmrTelMn"] = 'isNonEmpty';
    validator.config["cstmrTelRn"] = 'isNonEmpty';
    validator.config['cstmrTelFn'] = 'isNumBetterFixN2';
    validator.config['cstmrTelMn'] = 'isNumBetterFixN3';
    validator.config['cstmrTelRn'] = 'isNumFix4';

    if (!validator.validate(true)) {
      var errId = validator.getErrorId();
      MCP.alert(validator.getErrorMsg(),function (){
        $("#"+errId).focus();
      });
      return false;
    }

    if($("#isUsimNumberCheck").val() != "1"){
      MCP.alert("유심번호 유효성 체크하여 주시기 바랍니다.");
      return false;
    }

    if(!$('#chkAgree').prop('checked')){
      MCP.alert("본인인증 절차 진행에 동의해야 합니다.");
      return false;
    }

    return true;
  }

  // 유심변경
  $('#btnUsimChg').click(function(){

    validator.config = {};
    validator.config["cstmrName"] = 'isNonEmpty';
    validator.config["cstmrTelFn"] = 'isNonEmpty';
    validator.config["cstmrTelMn"] = 'isNonEmpty';
    validator.config["cstmrTelRn"] = 'isNonEmpty';
    validator.config['cstmrTelFn'] = 'isNumBetterFixN2';
    validator.config['cstmrTelMn'] = 'isNumBetterFixN3';
    validator.config['cstmrTelRn'] = 'isNumFix4';

    if (!validator.validate(true)) {
      var errId = validator.getErrorId();
      MCP.alert(validator.getErrorMsg(),function(){
        $("#"+errId).focus();
      });
      return false;
    }

    if(!$('#chkAgree').prop('checked')){
      MCP.alert("본인인증 절차 진행에 동의해야 합니다.");
      return false;
    }

    if($("#isUsimNumberCheck").val() != "1"){
      MCP.alert("유심번호 유효성 체크하여 주시기 바랍니다.");
      return false;
    }

    if($("#isAuth").val() != "1"){
      MCP.alert("본인 인증이 완료되지 않았습니다.");
      return false;
    }

    var varData = ajaxCommon.getSerializedData({
       cstmrName: $.trim($("#cstmrName").val())
      ,cstmrTel: $.trim($('#cstmrTelFn').val()) + $.trim($('#cstmrTelMn').val()) + $.trim($('#cstmrTelRn').val())
      ,iccId: $.trim($("#reqUsimSn").val())
    });

    ajaxCommon.getItem({
       id: 'replaceUsimSelfAjax'
      ,cache: false
      ,url: '/apply/replaceUsimSelfAjax.do'
      ,data: varData
      ,dataType:"json"
      ,errorCall : function () {
        MCP.alert("시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
      }
    }, function(jsonObj){

      if(AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE){
        MCP.alert("유심 변경을 처리 중입니다.<br>전산처리에 1~2분 정도 걸릴 수 있으니, 화면을 닫지 말고 잠시만 기다려 주세요.", function(){
          if(pageObj.isCallbackIng) KTM.LoadingSpinner.show();
        });
        pageObj.checkCbCnt = 0;
        fnSelfUsimChgCallBack(jsonObj.MVNO_ORD_NO);
      }else{
        var errMsg = jsonObj.RESULT_MSG || "서비스 처리 중 오류가 발생했습니다.";
        MCP.alert(errMsg, function(){
          location.href = "/m/apply/replaceUsimSelf.do";
        });
      }
    })
  });

}); // end of document.ready -------------------

function nextFocus(obj, len, id) {
  if ($(obj).val().length >= len) {
    $('#' + id).focus();
  }
}

function fnNiceCert(prarObj) {

  pageObj.niceResLogObj = prarObj;

  var infoObj = {
     name: $.trim($("#cstmrName").val())
    ,sMobileNo: $.trim($('#cstmrTelFn').val()) + $.trim($('#cstmrTelMn').val()) + $.trim($('#cstmrTelRn').val())
    ,authType: prarObj.authType
    ,menuType: "replaceUsimSelf"
  };

  var certResult = authCallbackWithMenuType(infoObj);

  if(certResult.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS){
    $("#cstmrName").prop("disabled", true);
    $("#cstmrTelFn").prop("disabled", true);
    $("#cstmrTelMn").prop("disabled", true);
    $("#cstmrTelRn").prop("disabled", true);

    MCP.alert("본인인증에 성공 하였습니다.");
    $('#btnUsimChg').show();

  }else{
    var errMsg = certResult.RESULT_MSG || "서비스 처리 중 오류가 발생했습니다.<br>잠시 후 다시 시도 바랍니다.";
    MCP.alert(errMsg);
  }
}

function fnSelfUsimChgCallBack(mvnoOrdNo) {

  pageObj.isCallbackIng = true;

  var varData = ajaxCommon.getSerializedData({
    mvnoOrdNo: mvnoOrdNo
  });

  ajaxCommon.getItemNoLoading({
     id: 'usimChgChkAjax'
    ,cache: false
    ,url: '/mypage/usimChgChkAjax.do'
    ,data: varData
    ,dataType: "json"
    ,errorCall: function () {
      pageObj.isCallbackIng = false;
      KTM.LoadingSpinner.hide(true);
      KTM.Dialog.closeAll();
      MCP.alert("시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
    }
  }, function(jsonObj){

    if("0000" ==  jsonObj.RESULT_CODE) {
      pageObj.isCallbackIng = false;
      KTM.LoadingSpinner.hide(true);
      KTM.Dialog.closeAll();
      MCP.alert('<p class="u-mt--12"><strong class="u-fw--bold">유심변경이 완료되었습니다.</strong></p><p class="u-mt--12">고객님 유심변경이 완료되어 음성, 문자 등 휴대폰 사용이 중단됩니다.</p><p class="u-mt--12">신속히 새로운 유심을 단말기에 장착하여 1~3회 전원을 껐다 켜면 사용가능합니다.</p>', function(){
        location.href = "/m/apply/replaceUsimSelf.do";
      });
    }else if("9999" == jsonObj.RESULT_CODE){
      if(pageObj.checkCbCnt < 21){
        ++pageObj.checkCbCnt;
        setTimeout(function(){
          fnSelfUsimChgCallBack(mvnoOrdNo);
        }, 5000);
      }else{
        pageObj.isCallbackIng = false;
        KTM.LoadingSpinner.hide(true);
        KTM.Dialog.closeAll();

        MCP.alert("아직 유심 변경 결과가 확인되지 않습니다.<br>전산처리에 1~2분 정도 걸릴 수 있으니, 화면을 닫지 말고 잠시만 기다려 주세요.", function(){
          KTM.LoadingSpinner.show();
          pageObj.checkCbCnt = 0;
          fnSelfUsimChgCallBack(mvnoOrdNo);
        });
      }
    }else{
      pageObj.isCallbackIng = false;
      KTM.LoadingSpinner.hide(true);
      KTM.Dialog.closeAll();
      MCP.alert("유심 변경에 실패하였으며 자세한 사항은 고객센터(114/무료)로 문의 부탁드립니다.");
    }
  });
}