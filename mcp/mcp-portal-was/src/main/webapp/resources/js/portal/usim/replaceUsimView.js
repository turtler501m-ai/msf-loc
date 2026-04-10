;

VALIDATE_NOT_EMPTY_MSG.cstmrName = "가입자 성명을 입력해 주세요.";
VALIDATE_NUMBER_MSG.dlvryTelFn = "배송받을 연락처가 형식에 맞지 않습니다.";
VALIDATE_NUMBER_MSG.dlvryTelMn = "배송받을 연락처가 형식에 맞지 않습니다.";
VALIDATE_NUMBER_MSG.dlvryTelRn = "배송받을 연락처가 형식에 맞지 않습니다.";
VALIDATE_FIX_MSG.dlvryTelFn = "배송받을 연락처가 형식에 맞지 않습니다.";
VALIDATE_FIX_MSG.dlvryTelMn = "배송받을 연락처가 형식에 맞지 않습니다.";
VALIDATE_FIX_MSG.dlvryTelRn = "배송받을 연락처가 형식에 맞지 않습니다.";

var applyErrMessage = {
   PRE_APPLY_DONE : '유심 배송 신청 완료(재신청 제한)'
  ,PRE_APPLY_ING : '유심 배송 신청 완료(재신청 제한)'
  ,ESIM_APPLY_LIMIT : 'eSIM 사용 회선으로 유심 배송 대상에서 제외됩니다. eSIM 변경을 원하시면 고객센터로 문의 해 주세요.'
  ,PRE_CHECK_FAIL : '회선 정보 확인이 완료되지 않아 이번 신청에서 제외되었습니다. 다시 시도해 주세요.'
  ,CONTRACT_STOP : '정지 회선으로 유심 배송 대상에서 제외됩니다.'
}

var pageMessage = {
   F_LOGIN_REQ: '<p class="u-mt--12"><strong class="self-opening__highlight">회원정보와 가입정보가<br>일치하지 않습니다.</strong></p><p class="u-mt--12">다른 명의로 신청을 원하실 경우<br>로그아웃 후 이용 해 주시기 바랍니다.</p>'
  ,F_LOGIN: '<p class="u-mt--12"><strong class="self-opening__highlight">회원정보와 본인인증 정보가<br>일치하지 않습니다.</strong></p><p class="u-mt--12">다른 명의로 신청을 원하실 경우<br>로그아웃 후 이용 해 주시기 바랍니다.</p>'
  ,F_MEMBER: '<p class="u-mt--12"><strong class="self-opening__highlight">kt M모바일 가입 정보를<br>확인할 수 없습니다.</strong></p><p class="u-mt--8"><strong class="self-opening__highlight">입력 내용을 다시 확인 후 시도해 주세요.</strong></p><p class="u-mt--12">가입되어 있으나 인증이 실패한 경우 고객센터로 문의해 주세요.</p>'
  ,F_AUTH: '<p class="u-mt--12"><strong class="self-opening__highlight">본인인증 정보가 일치하지 않습니다.</strong></p><p class="u-mt--12">입력하신 정보를 확인 후 다시 시도해 주시기 바랍니다.</p>'
  ,F_AGE: '<p class="u-mt--12"><strong class="self-opening__highlight">만 19세 미만 사용자는 온라인 신청이 제한됩니다.</strong></p><p class="u-mt--12">신청을 원하실 경우 법정대리인께서 고객센터로 문의해 주세요.</p>'
}

var pageObj = {
   menuType: "replaceUsim"
  ,startTime: 0
  ,niceType: ""
  ,niceResLogObj: {}
};

var reqDataForm = {
   cstmrName: ""
  ,dlvryName: ""
  ,dlvryTelFn: ""
  ,dlvryTelMn: ""
  ,dlvryTelRn: ""
  ,dlvryPost: ""
  ,dlvryAddr: ""
  ,dlvryAddrDtl: ""
  ,dlvryMemo: ""
  ,agrmYn: ""
  ,osstNo: ""
};

$(document).ready(function(){

  $('#dlvryMemo').val("");

  // 유심 교체 가이드 영상
  $("#guideTag").click(function(){
    let iframeSrc = $(this).data("iframeSrc");
    $("#youTubeIframe").attr('src', iframeSrc);
    var el = document.querySelector('#modalContent');
    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
    modal.open();
  });

  // input 특수문자 제거
  $("#dlvryMemo").keyup(function(){
    $(this).val($(this).val().replace(/[?&=]/gi,''));
    dlvryMemoLenChk();
  });

  $("#dlvryMemo").blur(function(){
    $(this).val($(this).val().replace(/[?&=]/gi,''));
    dlvryMemoLenChk();
  });

  // 우편번호 찾기
  $("._btnAddr").click(function() {
    openPage('pullPopup', '/addPopup.do','','1');
  });

  // 필수약관 클릭 이벤트
  $("#chkAgree").click(function(){
    requiredFieldChk();
  });

  // 휴대폰 인증 요청
  $("#btnSmsAuth").click(function(){

    if($("#isSmsAuth").val() == "1") {
      MCP.alert("휴대폰 인증을 완료 하였습니다.");
      return false;
    }

    validator.config={};
    validator.config["cstmrName"] = 'isNonEmpty';

    if (!validator.validate(true)) {
      var errId = validator.getErrorId();
      MCP.alert(validator.getErrorMsg(),function (){
        $("#"+errId).focus();
      });
      return false;
    }

    var pollingPageUrl = "";

    var varData = ajaxCommon.getSerializedData({
      name: $.trim($("#cstmrName").val()),
      menuType: pageObj.menuType
    });

    ajaxCommon.getItemNoLoading({
         id: 'getSmsAuthPopWithCert'
        ,cache: false
        ,async: false
        ,url: '/auth/getSmsAuthPopWithCert.do'
        ,data: varData
        ,dataType: "json"
      }
      ,function(jsonObj){

        if(jsonObj.RESULT_CODE == "F_AUTH"){ // 해당 ajax에서 F_AUTH 떨어지는 경우 > 회원정보 불일치
          MCP.alert(pageMessage.F_LOGIN_REQ);
        }else if(jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
          MCP.alert("서비스 처리 중 오류가 발생했습니다.");
        }else{
          // 휴대폰 인증 팝업창 오픈
          pageObj.startTime = jsonObj.startTime;
          pageObj.niceType = NICE_TYPE.SMS_AUTH;
          pollingPageUrl = jsonObj.pollingPageUrl;
        }
      });

      if(pollingPageUrl != ""){
        openPage('outLink2', pollingPageUrl, {width:'500', height:'700'});
      }
  });

  // 신청 이벤트
  $("#saveBtn").click(function(){

    // 유효성 검사
    var chkResult = requiredFieldChk();
    if(chkResult.RESULT_CODE != "S"){
      MCP.alert(chkResult.RESULT_MSG);
      return false;
    }

    // 유효성 추가 검사
    validator.config={};
    validator.config['dlvryTelFn'] = 'isNumBetterFixN2';
    validator.config['dlvryTelMn'] = 'isNumBetterFixN3';
    validator.config['dlvryTelRn'] = 'isNumFix4';

    if (!validator.validate(true)) {
      var errId = validator.getErrorId();
      MCP.alert(validator.getErrorMsg(),function (){
        $("#"+errId).focus();
      });
      return false;
    }

    reqDataForm.cstmrName = $.trim($("#cstmrName").val());
    reqDataForm.dlvryName = $.trim($("#dlvryName").val());
    reqDataForm.dlvryTelFn = $.trim($('#dlvryTelFn').val());
    reqDataForm.dlvryTelMn = $.trim($('#dlvryTelMn').val());
    reqDataForm.dlvryTelRn = $.trim($('#dlvryTelRn').val());
    reqDataForm.dlvryPost = $.trim($("#dlvryPost").val());
    reqDataForm.dlvryAddr = $.trim($("#dlvryAddr").val());
    reqDataForm.dlvryAddrDtl = $.trim($("#dlvryAddrDtl").val());
    reqDataForm.dlvryMemo = $.trim($("#dlvryMemo").val()).replace(/[?&=]/gi,' ');
    reqDataForm.agrmYn = $("#chkAgree").is(':checked') ? "Y" : "N";
    reqDataForm.osstNo = $("#osstNo").val();

    var varData = ajaxCommon.getSerializedData(reqDataForm);

    ajaxCommon.getItem({
         id:'saveReplaceUsimRequest'
        ,cache:false
        ,url:"/apply/saveReplaceUsimRequest.do"
        ,data: varData
        ,dataType:"json"
      }
      ,function(jsonObj){

        if(jsonObj.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS){
          MCP.alert('<p class="u-mt--12"><strong class="self-opening__highlight">배송 신청이 접수되었습니다.</strong></p><p class="u-mt--12">유심을 수령하신 후<br>안내 문자에 포함된 URL 또는 마이페이지에서 유심변경을 진행해 주세요.</p>', function (){
            location.href = "/apply/replaceUsimView.do";
          });
        }else if(jsonObj.RESULT_CODE == "F_MEMBER"){
          MCP.alert(pageMessage.F_MEMBER);
        }else if(jsonObj.RESULT_CODE == "F_AGE"){
          MCP.alert(pageMessage.F_AGE);
        }else{
          var errMsg = jsonObj.RESULT_MSG || "서비스 처리 중 오류가 발생했습니다.";
          MCP.alert(errMsg);
        }
      });
  });

}); // end of document.ready -------------------

function dlvryMemoLenChk() {
  var content = $('#dlvryMemo').val();
  if(content.length > 50){
    content = content.substring(0, 50);
  }

  $('#dlvryMemo').val(content);
  $('#dlvryMemoChk').text(content.length +"/50");
  requiredFieldChk();
}

function jusoCallBack(roadFullAddr, roadAddrPart1, addrDetail, roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
  $("#dlvryPost").val(zipNo);
  $("#dlvryAddr").val(roadAddrPart1);
  $("#dlvryAddrDtl").val(roadAddrPart2 + " " +addrDetail);
  requiredFieldChk();
}

function targetTermsAgree() {
  $('#chkAgree').prop('checked', 'checked');
  requiredFieldChk();
}

function nextFocus(obj, len, id){
  if($(obj).val().length >= len){
    $('#' + id).focus();
  }
}

function fnNiceCert(prarObj) {

  pageObj.niceResLogObj = prarObj;

  if(prarObj.resSeq == undefined || prarObj.resSeq == "") {
    MCP.alert("휴대폰 인증에 실패 하였습니다.");
    return null;
  }

  var infoObj = {name: $.trim($("#cstmrName").val()), menuType: pageObj.menuType, ncType: "0"};
  var certResult = checkSmsAuthInfoWithCert(infoObj);

  if(certResult.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS){
    fnNiceCertMore(); // 추가 검증 진행
    return null;
  }else if(certResult.RESULT_CODE == "F_LOGIN"){
    MCP.alert(pageMessage.F_LOGIN);
  }else if(certResult.RESULT_CODE == "F_AGE"){
    MCP.alert(pageMessage.F_AGE);
  }else{
    MCP.alert(pageMessage.F_AUTH);
  }
}

function requiredFieldChk() {

  var chkResult = {RESULT_CODE: 'F' , RESULT_MSG : '필수값이 누락되었습니다.'};

  // 가입정보
  var cstmrName = $.trim($("#cstmrName").val());

  if(cstmrName == "" || $("#isSmsAuth").val() != "1"){
    $("#saveBtn").addClass('is-disabled');
    chkResult.RESULT_MSG = "가입정보 확인을 위해 M모바일을 이용중인 휴대폰으로 인증을 완료 해 주세요.";
    return chkResult;
  }

  // 신청 가능 회선 존재 여부
  if(Number($("#possibleCnt").val()) < 1){
    $("#saveBtn").addClass('is-disabled');
    chkResult.RESULT_MSG = "유심 신청 대상이 존재하지 않습니다.";
    return chkResult;
  }

  // 받는분
  var dlvryName = $.trim($("#dlvryName").val());
  var dlvryTelFn = $.trim($('#dlvryTelFn').val());
  var dlvryTelMn = $.trim($('#dlvryTelMn').val());
  var dlvryTelRn = $.trim($('#dlvryTelRn').val());

  if(dlvryName == "" || dlvryTelFn == "" ||  dlvryTelMn == "" || dlvryTelRn == ""){
    $("#saveBtn").addClass('is-disabled');
    chkResult.RESULT_MSG = "받는 분 정보(이름/연락처)를 입력해주세요.";
    return chkResult;
  }

  // 배송지
  var dlvryPost = $.trim($('#dlvryPost').val());
  var dlvryAddr = $.trim($('#dlvryAddr').val());
  var dlvryAddrDtl = $.trim($('#dlvryAddrDtl').val());

  if(dlvryPost == "" || dlvryAddr == "" || dlvryAddrDtl == ""){
    $("#saveBtn").addClass('is-disabled');
    chkResult.RESULT_MSG = "배송지 정보를 입력해주세요.";
    return chkResult;
  }

  // 약관
  if($('#chkAgree:checked').length <= 0){
    $("#saveBtn").addClass('is-disabled');
    chkResult.RESULT_MSG = "필수 약관에 동의해주세요.";
    return chkResult;
  }

  $("#saveBtn").removeClass('is-disabled');
  chkResult.RESULT_CODE = "S";
  chkResult.RESULT_MSG = "requiredFieldChk 성공";
  return chkResult;
}

function fnNiceCertMore(){

  ajaxCommon.getItem({
       id: 'checkSmsAuthInfoWithCert'
      ,cache: false
      ,url: '/apply/checkReplaceUsimRequestUser.do'
      ,dataType: "json"
    }
    ,function(jsonObj){

      if(jsonObj.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS){

        $("#isSmsAuth").val("1");
        $('#cstmrName').prop('readonly', 'readonly');
        $('#btnSmsAuth').addClass('is-complete').html("휴대폰 인증 완료");

        var possibleCnt = jsonObj.replaceUsimSubDtos.filter(item => item.resultCd == "00").length;
        $("#possibleCnt").val(possibleCnt);
        $("#osstNo").val(jsonObj.osstNo);

        drawCntrInfoTable(jsonObj.replaceUsimSubDtos);
        requiredFieldChk();

      }else if(jsonObj.RESULT_CODE == "F_MEMBER"){
        MCP.alert(pageMessage.F_MEMBER);
      }else if(jsonObj.RESULT_CODE == "F_AGE"){
        MCP.alert(pageMessage.F_AGE);
      }else{
        var errMsg = jsonObj.RESULT_MSG || "서비스 처리 중 오류가 발생했습니다.";
        MCP.alert(errMsg);
      }
    });
}

function drawCntrInfoTable(replaceUsimSubDtos){

  var totalCnt = replaceUsimSubDtos.length;
  var possibleCnt = 0;

  // 회선확인 테이블 html 생성
  var tableHtml = "";

  for(var i = 0; i < replaceUsimSubDtos.length; i++){

    var item = replaceUsimSubDtos[i];

    var resultMsg = item.resultMsg;
    var resultCd = item.resultCd;

    if(resultCd == "00"){
      resultMsg = "-";
      possibleCnt++;
    }

    tableHtml += "<tr>";
    tableHtml += "  <td>" + item.mskMobileNo + "</td>";
    tableHtml += "  <td>" + (resultCd == "00" ? "가능" : "불가능") + "</td>";
    tableHtml += "  <td class='u-ta-left'>" + (applyErrMessage[resultCd] || ajaxCommon.isNullNvl(resultMsg, applyErrMessage.PRE_CHECK_FAIL)) + "</td>";
    tableHtml += "</tr>";
  }

  $("#replaceUsimCntrInfo").html(tableHtml);

  // 회선확인 안내문구 html 생성
  var textHtml = '';
  if(0 < possibleCnt){
    textHtml = `총 <span class="u-fw--bold">${totalCnt}개 회선</span> 중 <span class="u-fw--bold">${possibleCnt}개 회선</span>이 신청 가능하며, <span class="u-fw--bold">배송 수량 ${possibleCnt}</span>개가 자동 적용됩니다.`;
  }else{
    textHtml = '신청가능 회선이 없습니다.';
  }

  $("#replaceUsimCntrText").html(textHtml);
  $("#replaceUsimCntrText").show();
}