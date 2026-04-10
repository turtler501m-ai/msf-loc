
var eventId = ""; // 팝업 구분

$(document).ready(function (){

  // 제휴 약관
  $("#chkAgree1").click(function(){
    eventId = "chkAgree1";
    var isChecked = $('#targetTerms1').val();

    if(isChecked != "Y"){

      var data = {cdGroupId1:'FORMREQUIRED'
        ,cdGroupId2:'clauseJehuFlag'
        ,docType:'02'
        ,expnsnStrVal:$("#jehuProdType").val()
        ,name:$("#jehuProdName").val()};

      openPage('termsPop','/termsPop.do', data,0);

      $('#chkAgree1').prop("checked", false);
      var el = document.querySelector('#modalTerms');
      var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
      modal.open();
    }else{
      $('#chkAgree1').prop("checked", false);
      $('#targetTerms1').val("");
    }
  });

  // 요금제 변경 약관
  $('#chkAgreeA2').click(function(){
    eventId = "chkAgreeA2";
    var isChecked = $('#targetTerms2').val();

    if(isChecked != "Y"){
      $('#targetTerms2').val("Y");
    }else{
      $('#targetTerms2').val("");
    }
  });

  // 내 사용량 펼쳐보기
  $("#realTimeDataHeader").click(function(){
    if($(this).hasClass("is-active")) return true;
    searchUseageData(); // 내 사용량 조회
  });

}); // end of document.ready ---------------------------------------

/** 약관 동의 후 닫기 */
var targetTermsAgree = function(){

  $('#' + eventId).prop('checked', 'checked');

  if(eventId == "chkAgree1") $('#targetTerms1').val("Y");
  else if(eventId == "chkAgreeA2") $('#targetTerms2').val("Y");
}

/** 당월 실시간 이용량 조회 */
var searchUseageData = function(){

  var varData = ajaxCommon.getSerializedData({
    ncn : $("#ncn").val()
  });

  ajaxCommon.getItem({
     id: 'selectRealTimeUseageAjax'
    ,cache: false
    ,url: '/m/personal/selectRealTimeUseageAjax.do'
    ,data : varData
    ,dataType: "json"
  },function(data){

    // 개인화 URL 정보 미존재
    if(data.RESULT_CODE == "0001"){
      MCP.alert(data.RESULT_MSG, function(){
        location.href = "/m/personal/auth.do";
      });
      return false;
    }

    // 중복요청 > 기존데이터 그대로 표출
    if(data.RESULT_CODE == "0002"){
      return false;
    }

    // 서버오류
    if(data.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
      $("._realTimeDataView").hide();
      $("#realTimeDataView03").show();
      return false;
    }

    // 조회 횟수 초과
    if(data.useTimeSvcLimit){
      $("._realTimeDataView").hide();
      $("#realTimeDataView01").show();
      return false;
    }

    var useageData = data.useageData || "";
    var useageVoice = data.useageVoice || "";
    var useageSms = data.useageSms || "";
    drawUseageData(useageData, useageVoice, useageSms);
  });
}

/** 실시간 이용량 화면 생성 */
var drawUseageData= function(useageData, useageVoice, useageSms){

  // 이용량 조회결과 없음
  if((useageData == "" || useageData.length == 0) && (useageVoice == "" || useageVoice.length == 0) && (useageSms == "" || useageSms.length == 0)){
    $("._realTimeDataView").hide();
    $("#realTimeDataView02").show();
    return false;
  }

  // 이용량 조회결과 있음
  var drawHtml = "";
  var drawDataList = [useageData, useageVoice, useageSms];

  for(var i=0; i<drawDataList.length; i++){

    if(drawDataList[i] == "") continue;

    drawDataList[i].forEach(function(item){
      var rtnHtml = drawUseageDataOne(item);
      drawHtml += rtnHtml;
    });
  }

  $("#realTimeDataViewContent").html(drawHtml);
  $("._realTimeDataView").hide();
  $("#realTimeDataView04").show();
  $("#realTimeDataViewAddDesc").show();
}

/** 실시간 이용량 행 생성 */
var drawUseageDataOne = function(dataObj){

  var rtnHtml = '';
  if(!dataObj) return rtnHtml; // null체크

  rtnHtml += '<tr>';
  rtnHtml += '  <td class="u-pt--12 u-pb--12">' + dataObj.strSvcName + '</td>';
  rtnHtml += '  <td class="u-pt--12 u-pb--12">' + dataObj.strFreeMinUse + '</td>';
  rtnHtml += '  <td class="u-pt--12 u-pb--12">' + dataObj.strFreeMinRemain + '</td>';
  rtnHtml += '</tr>';

  return rtnHtml;
}

/** 요금제 변경 */
var btn_farReg = function(){

  var check1 = $('input:checkbox[id="chkAgree1"]').is(":checked");
  var check2 = $('input:checkbox[id="chkAgreeA2"]').is(":checked");
  var chkRadion =  $("input[name=chkRadion]:checked").val();

  if($("#chkAgree1").length != 0 && !check1){
    MCP.alert("제휴요금제 변경에 따른 제휴사 정보 제공에 동의해야합니다.", function(){
      $("#chkAgree1").focus();
    });
    return false;
  }

  if(!check2){
    MCP.alert("요금제 변경을 위한 동의를 하셔야 요금제변경이 됩니다.", function(){
      $("#chkAgreeA2").focus();
    });
    return false;
  }

  if(chkRadion == ""){
    MCP.alert("요금제 변경 방법을 선택해주세요");
    return false;
  }

  if(chkRadion == 'S') goFarReg();	        // 즉시변경
  else if(chkRadion == 'P') goResFarReg();  // 예약변경
}

/** 요금제 즉시변경 */
var goFarReg = function(){

  // 당월 개통
  if($("#isActivatedThisMonth").val() == "Y"){
    MCP.alert("개통 당월에는 요금제 즉시 변경이 불가합니다.<br>다음달에 요금제 변경을 진행해 주세요.");
    return false;
  }

  // 당월 요금제 변경
  if($("#isFarPriceThisMonth").val() == "Y"){
    MCP.alert("요금제는 월 1회만 변경 가능합니다.<br>다음달에 요금제 변경을 진행해 주세요.");
    return false;
  }

  KTM.Confirm("요금제 즉시변경 하시겠습니까?", function (){

    this.close();

    var aSocAmnt = ajaxCommon.isNullNvl($("#aSocAmnt").val(), '');
    aSocAmnt = aSocAmnt.replaceAll("원","");

    var tSocAmnt = ajaxCommon.isNullNvl($("#tSocAmnt").val(), '');
    tSocAmnt = tSocAmnt.replaceAll("원","");

    var varData = ajaxCommon.getSerializedData({
       ncn: $("#ncn").val()
      ,toSocCode: $("#rateAdsvcCd").val()
      ,aSocAmnt: aSocAmnt
      ,tSocAmnt: tSocAmnt
    });

    ajaxCommon.getItem({
       id: 'farPricePlanChgAjax'
      ,cache: false
      ,url: "/m/personal/farPricePlanChgAjax.do"
      ,data: varData
      ,dataType: "json"
    },function(data){

      // 개인화 URL 정보 미존재
      if(data.RESULT_CODE == "0001"){
        MCP.alert(data.RESULT_MSG, function(){
          location.href = "/m/personal/auth.do";
        });
        return false;
      }

      if(data.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
        var errMsg = ajaxCommon.isNullNvl(data.RESULT_MSG, "요금제 변경에 실패하였습니다.");
        MCP.alert(errMsg);
        return false;
      }

      MCP.alert("요금제 변경이 완료되었습니다.",function(){

        $('.c-icon--close').trigger('click');

        ajaxCommon.createForm({
           method:"post"
          ,action:"/m/personal/farPricePlanView.do"
        });

        ajaxCommon.attachHiddenElement("ncn", $("#ncn").val());
        ajaxCommon.formSubmit();
      });
    });
  });
}

/** 요금제 예약변경 */
var goResFarReg = function(){

  KTM.Confirm("요금제 예약변경 하시겠습니까?", function(){

    this.close();

    var varData = ajaxCommon.getSerializedData({
       ncn: $("#ncn").val()
      ,soc: $("#rateAdsvcCd").val()
      ,befChgRateCd: $("#prvRateCd").val()
      ,befChgRateAmnt: $("#instMnthAmt").val()
    });

    ajaxCommon.getItem({
      id: 'farPricePlanChgAjax'
      ,cache: false
      ,url: "/m/personal/farPricePlanResChgAjax.do"
      ,data: varData
      ,dataType: "json"
    },function(data){

      // 개인화 URL 정보 미존재
      if(data.RESULT_CODE == "0001"){
        MCP.alert(data.RESULT_MSG, function(){
          location.href = "/m/personal/auth.do";
        });
        return false;
      }

      if(data.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
        var errMsg = ajaxCommon.isNullNvl(data.RESULT_MSG, "요금제 예약에 실패했습니다.");
        MCP.alert(errMsg);
        return false;
      }

      MCP.alert("요금제 예약 변경이 완료되었습니다.<br><br>익월 1일부터 변경된 요금제로 반영됩니다.", function(){

        $('.c-icon--close').trigger('click');

        ajaxCommon.createForm({
           method: "post"
          ,action: "/m/personal/farPricePlanView.do"
        });

        ajaxCommon.attachHiddenElement("ncn", $("#ncn").val());
        ajaxCommon.formSubmit();
      });
    });
  });
}