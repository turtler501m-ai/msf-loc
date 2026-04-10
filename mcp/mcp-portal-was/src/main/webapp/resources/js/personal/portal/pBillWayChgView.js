
$(document).ready(function () {

  // 명세서 정보 조회
  getBillInfo();

  // 이메일주소 입력
  $("#emailAdr").keyup(function(){
    if($(this).val().trim()){
      $("#change-submit").prop("disabled", false);
    }else{
      $("#change-submit").prop("disabled", true);
    }
  });

  // 명세서 변경 유형
  $("#selCategory").change(function(){
    var sBillType = $(this).val();

    if(sBillType == 'MB'){
      $("#cateMobile").removeClass("u-hide");
      $("#cateEmail").addClass("u-hide");
      $("#catePost").addClass("u-hide");
      $("#change-submit").prop("disabled", false);
    }else if(sBillType == 'CB'){
      $("#cateMobile").addClass("u-hide");
      $("#cateEmail").removeClass("u-hide");
      $("#catePost").addClass("u-hide");
      $("#emailAdr").trigger("keyup");
    }else if(sBillType == 'LX'){
      $("#cateMobile").addClass("u-hide");
      $("#cateEmail").addClass("u-hide");
      $("#catePost").removeClass("u-hide");

      var postNo = ajaxCommon.isNullNvl($("#postNo").val(), "");
      var address1 = ajaxCommon.isNullNvl($('#address1').val(), "");
      var address2 = ajaxCommon.isNullNvl($('#address2').val(), "");

      if(postNo.length < 5 || !$.isNumeric(postNo)){
        $("#change-submit").prop("disabled", true);
      }else if(address1.length < 10){
        $("#change-submit").prop("disabled", true);
      }else if(address2.length < 1){
        $("#change-submit").prop("disabled", true);
      }else{
        $("#change-submit").prop("disabled", false);
      }
    }
  });

  // 명세서 재발송 페이지 이동
  $("#Fbill01").click(function(){

    ajaxCommon.createForm({
       method: "post"
      ,action: "/personal/billWayReSend.do"
    });

    ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
    ajaxCommon.formSubmit();
  });

}); // end of document.ready -------------------------

/** 명세서 정보 조회 */
var getBillInfo = function(){

  var varData = ajaxCommon.getSerializedData({
    ncn : $("#ctn option:selected").val()
  });

  ajaxCommon.getItem({
     id: 'myBillInfoViewAjax'
    ,cache: false
    ,url: '/personal/myBillInfoViewAjax.do'
    ,data: varData
    ,dataType: "json"
  },function(data){

    // 개인화 URL 정보 미존재
    if(data.RESULT_CODE == "0001"){
      MCP.alert(data.RESULT_MSG, function(){
        location.href = "/personal/auth.do";
      });
      return false;
    }

    var zipCode = ajaxCommon.isNullNvl(data.zipCode,"");
    var pAddr = ajaxCommon.isNullNvl(data.pAddr,"");
    var sAddr = ajaxCommon.isNullNvl(data.sAddr,"");
    $("#postNo").val(zipCode);
    $("#address1").val(pAddr);
    $("#address2").val(sAddr);

    var payMethod = $.trim(data.payMethod);  // 납부방법
    var billTypeCd = data.billTypeCd;        // 명세서 발송유형
    var billTypeNm = "";                     // 명세서 발송유형명
    var addInfoTitle = "";                   // 명세서 유형 별 추가정보 (제목)
    var addInfo = "";                        // 명세서 유형 별 추가정보

    // 납부기준일
    var dueDay = ajaxCommon.isNullNvl(data.billCycleDueDay,"-");
    dueDay = ("99" == dueDay) ? "말일" : dueDay + "일";

    if(billTypeCd == "CB"){
      var email = ajaxCommon.isNullNvl(data.email,"");
      $("#selCategory option[value='CB']").prop("selected", true);
      $("#cateEmail").removeClass("u-hide");
      $("#cateMobile").addClass("u-hide");
      $("#catePost").addClass("u-hide");
      $("#emailAdr").val(email);
      billTypeNm = "이메일 명세서";
      addInfoTitle = "메일주소";
      addInfo = ajaxCommon.isNullNvl(data.maskedEmail,"");
    }else if(billTypeCd == "LX"){
      $("#selCategory option[value='LX']").prop("selected", true);
      $("#cateEmail").addClass("u-hide");
      $("#cateMobile").addClass("u-hide");
      $("#catePost").removeClass("u-hide");
      billTypeNm = "우편 명세서";
      addInfoTitle = "청구지";
      addInfo = ajaxCommon.isNullNvl(data.addr,"");
    }else if(billTypeCd == "MB"){
      $("#selCategory option[value='MB']").prop("selected", true);
      $("#cateEmail").addClass("u-hide");
      $("#cateMobile").removeClass("u-hide");
      $("#catePost").addClass("u-hide");
      billTypeNm = "모바일 명세서(MMS)";
      addInfoTitle = "휴대폰번호";
      addInfo = ajaxCommon.isNullNvl(data.maskedCtn,"");
    }else{
      $("#selCategory option[value='MB']").prop("selected", true);
      $("#cateEmail").addClass("u-hide");
      $("#cateMobile").removeClass("u-hide");
      $("#catePost").addClass("u-hide");
      // 기본값 세팅
      billTypeNm = "모바일 명세서(MMS)";
      addInfoTitle = "청구지";
      addInfo = ajaxCommon.isNullNvl(data.addr,"");
    }

    // 화면 생성
    $(".info-box").html("");

    var html = "";
    if(payMethod == "자동이체"){
      html += `<tr>`;
      html += `  <th scope="row">납부방법</th>`;
      html += `	 <td class="u-ta-left">${payMethod}</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">납부계정정보</th>`;
      html += `  <td class="u-ta-left">${data.blBankAcctNo}</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">납부기준일</th>`;
      html += `  <td class="u-ta-left">${dueDay}</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">명세서유형</th>`;
      html += `  <td class="u-ta-left">${billTypeNm}</td>`;
      html += `</tr>`;
      if(billTypeCd != 'MB'){
        html += `<tr>`;
        html += `  <th scope="row">${addInfoTitle}</th>`;
        html += `  <td class="u-ta-left">${addInfo}</td>`;
        html += `</tr>`;
      }
    }else if(payMethod == "지로"){
      // 납부방법이 지로인 경우 명세서유형 '청구지'로 바꿔치기
      addInfoTitle = "청구지";
      addInfo = ajaxCommon.isNullNvl(data.blAddr,"");

      html += `<tr>`;
      html += `  <th scope="row">납부방법</th>`;
      html += `	 <td class="u-ta-left">${payMethod}</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">납부기준일</th>`;
      html += `  <td class="u-ta-left">${dueDay}</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">명세서유형</th>`;
      html += `  <td class="u-ta-left">${billTypeNm}</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">${addInfoTitle}</th>`;
      html += `  <td class="u-ta-left">${addInfo}</td>`;
      html += `</tr>`;
    }else if(payMethod == "신용카드"){
      html += `<tr>`;
      html += `  <th scope="row">납부방법</th>`;
      html += `	 <td class="u-ta-left">${payMethod}</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">카드번호</th>`;
      html += `	 <td class="u-ta-left">${data.prevCardNo}</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">납부기준일</th>`;
      html += `  <td class="u-ta-left">${dueDay}</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">명세서유형</th>`;
      html += `  <td class="u-ta-left">${billTypeNm}</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">${addInfoTitle}</th>`;
      html += `  <td class="u-ta-left">${addInfo}</td>`;
      html += `</tr>`;
    }else if(payMethod == "간편결제"){
      html += `<tr>`;
      html += `  <th scope="row">납부방법</th>`;
      html += `	 <td class="u-ta-left">${payMethod}</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">납부기준일</th>`;
      html += `  <td class="u-ta-left">${dueDay}</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">명세서유형</th>`;
      html += `  <td class="u-ta-left">${billTypeNm}</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">${addInfoTitle}</th>`;
      html += `  <td class="u-ta-left">${addInfo}</td>`;
      html += `</tr>`;
    }else{
      html += `<tr>`;
      html += `  <th scope="row">납부방법</th>`;
      html += `	 <td class="u-ta-left">-</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">납부기준일</th>`;
      html += `  <td class="u-ta-left">-</td>`;
      html += `</tr>`;
      html += `<tr>`;
      html += `  <th scope="row">명세서유형</th>`;
      html += `  <td class="u-ta-left">-</td>`;
      html += `</tr>`;
    }

    $(".info-box").html(html);
    $("#selCategory").trigger("change");
  });
}

/** 주소 콜백 */
var jusoCallBack = function(roadFullAddr, roadAddrPart1, addrDetail, roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){
  $("#postNo").val(zipNo);            // 우편번호
  $("#address1").val(roadAddrPart1);  // 주소
  $("#address2").val(addrDetail);     // 상세주소
  $("#change-submit").prop("disabled", false);
}

/** 명세서 변경 */
var changeSubmit = function(){

  var sBillType = $("#selCategory").val();
  var ncn = $("#ctn option:selected").val();
  var confirmMsg = "";
  var completeMsg = "";
  var varData = null;

  if(sBillType == "CB"){

    var email = ajaxCommon.isNullNvl($("#emailAdr").val(), "");

    if(email == ""){
      MCP.alert('이메일주소를 입력해 주세요.');
      $("#emailAdr").focus();
      return false;
    }

    if(email.length < 8){
      MCP.alert("이메일주소를 올바르게 입력해 주세요.");
      $("#emailAdr").focus();
      return false;
    }

    var regEmail = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    if(!regEmail.test(email)){
      MCP.alert("이메일주소를 이메일형식에 맞게 입력해 주세요.");
      $("#emailAdr").focus();
      return false;
    }

    confirmMsg = "e-mail 청구서를 변경하시겠습니까?";
    completeMsg = "e-mail 청구서 변경 요청을 완료 하였습니다.";
    varData = ajaxCommon.getSerializedData({
       billTypeCd : sBillType
      ,ncn : ncn
      ,billAdInfo : email
    });

  }else if(sBillType == "LX"){

    var postNo = ajaxCommon.isNullNvl($("#postNo").val(), "");
    var address1 = ajaxCommon.isNullNvl($('#address1').val(), "");
    var address2 = ajaxCommon.isNullNvl($('#address2').val(), "");

    if(postNo.length < 5 || !$.isNumeric(postNo)){
      MCP.alert("우편번호를 확인해 주세요.");
      $('#postNo').focus();
      return false;
    }

    if(address1.length < 10){
      MCP.alert("주소를 확인해 주세요.");
      $('#address1').focus();
      return false;
    }

    if(address2.length < 1){
      MCP.alert("나머지 주소를 확인해 주세요.");
      $('#address2').focus();
      return false;
    }

    confirmMsg = "우편 청구서 주소를 변경하시겠습니까?";
    completeMsg = "주소변경 요청을 완료 하였습니다.";
    varData = ajaxCommon.getSerializedData({
       billTypeCd : sBillType
      ,ncn : ncn
      ,zip : postNo
      ,addr1 : address1
      ,addr2 : address2
    });

  }else if(sBillType == "MB"){
    confirmMsg = "MMS명세서를 변경하시겠습니까?";
    completeMsg = "MMS명세서 변경 요청을 완료 하였습니다.";
    varData = ajaxCommon.getSerializedData({
       billTypeCd : sBillType
      ,ncn : ncn
    });
  }

  if(varData == null) return false;

  // 명세서 변경 요청
  KTM.Confirm(confirmMsg, function(){
    changeBill(varData, completeMsg);
    this.close();
  });
}

/** 명세서 변경 요청 */
var changeBill = function(varData, completeMsg){

  ajaxCommon.getItem({
     id: 'billChgAjax'
    ,cache: false
    ,url: '/personal/billChgAjax.do'
    ,data: varData
    ,dataType: "json"
  },function(data){

    // 개인화 URL 정보 미존재
    if(data.RESULT_CODE == "0001"){
      MCP.alert(data.RESULT_MSG, function(){
        location.href = "/personal/auth.do";
      });
      return false;
    }

    if(data.RESULT_CODE != "S"){
      MCP.alert(data.RESULT_MSG);
      return false;
    }

    MCP.alert(completeMsg, function(){
      location.reload();
    });
  });
}

/** 회선 조회 */
var select = function(){
  getBillInfo(); // 명세서 정보 조회
}