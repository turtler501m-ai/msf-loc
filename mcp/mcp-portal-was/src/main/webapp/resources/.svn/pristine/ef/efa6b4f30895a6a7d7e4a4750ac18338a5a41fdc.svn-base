
var numCount = 1;
var maxCount = 50;

$(document).ready(function(){

  // 숫자만 입력
  $(".numOnly").keyup(function(){
    $(this).val($(this).val().replace(/[^0-9]/gi, ""));
  });

  // 숫자만 입력
  $(".numOnly").blur(function(){
    $(this).val($(this).val().replace(/[^0-9]/gi, ""));
  });

}); // end of document.ready --------------------------

/** 차단 번호 추가 */
var btn_numAdd = function(){

  var count = $(".addNumber > .numberAddView").length;

  if(maxCount <= count){
    MCP.alert("최대 " + maxCount + "개까지 차단 설정이 가능합니다.");
    return false;
  }

  var num = ++numCount;
  var html = '';

  html += '<li class="c-list__item numberAddView" id="addNum_' + num + '">';
  html += '  <div class="c-input-wrap c-input-wrap--flex u-mt--0">';
  html += '    <input class="c-input c-input--h34 u-ta-center numOnly" type="tel" id="phone1_' + num + '"  maxlength="3" placeholder="앞자리" title="앞자리 입력">';
  html += '    <input class="c-input c-input--h34 u-ta-center numOnly" type="tel" id="phone2_' + num + '"  maxlength="4" placeholder="중간자리" title="중간자리 입력">';
  html += '    <input class="c-input c-input--h34 u-ta-center numOnly" type="tel" id="phone3_' + num + '"  maxlength="4" placeholder="뒷자리" title="뒷자리 입력">';
  html += '  </div>';
  html += '  <button class="c-button" href="javascript:void(0);" onclick="btn_del(' + num + ')">';
  html += '    <i class="c-icon c-icon--delete2" aria-hidden="true"></i>';
  html += '  </button>';
  html += '</li>';

  $("#numCount").html(count+1);
  $(".addNumber").append(html);
}

/** 차단 번호 삭제 */
var btn_del = function(idx){
  $("#addNum_" + idx).remove();
  var remainCnt = $(".addNumber > .numberAddView").length;
  $("#numCount").html(remainCnt);
}

/** 불법수신차단 서비스 신청 유효성 검사 */
var btn_tmReg = function(){

  var isValid = true;
  var ftrNewParam = "";

  // 전화번호 검사
  $(".numberAddView").each(function(){

    var phone1 = $(this).find("input").eq(0).val();
    var phone2 = $(this).find("input").eq(1).val();
    var phone3 = $(this).find("input").eq(2).val();
    var phone = phone1 + phone2 + phone3;

    if(phone == ""){
      MCP.alert('정확한 정보를 입력해 주세요.', function(){
        $(this).find("input").eq(0).focus();
      });

      isValid = false;
      return false;
    }

    if(phone1 != ""){
      ftrNewParam += phone + ":";
    }else if(phone2 != ""){
      ftrNewParam += "*" + phone2 + "*:";
    }else if(phone3 != ""){
      ftrNewParam += "*" + phone3 + ":";
    }
  });

  if(isValid){
    $("#ftrNewParam").val(ftrNewParam);
    btn_reg();
  }
}

/** 번호도용 신청 유효성 검사 */
var btn_numberReg = function(){

  var chk = $("input[name=chkAgree]:checked").val();

  if(!chk){
    MCP.alert('정보 제공에 동의해 주세요.', function(){
      $("#chkAgree").focus();
    });
    return false;
  }

  $("#ftrNewParam").val('Y');
  btn_reg();
}

/** 요금할인 신청 유효성 검사 */
var btn_pointReg = function(){

  var amt = $("#pointAmt").val();

  if(amt == ""){
    MCP.alert('할인금액을 입력해 주세요.', function(){
      $("#pointAmt").focus();
    });
    return false;
  }

  if(amt < 1000){
    MCP.alert('최소 1,000원부터 신청 가능합니다.', function(){
      $("#pointAmt").focus();
    });
    return false;
  }

  if(20000 < amt){
    MCP.alert('최대 20,000원까지 신청가능합니다.', function(){
      $("#pointAmt").focus();
    });
    return false;
  }

  var totalPoint = ajaxCommon.isNullNvl($("#totalPoint").val(), "0");
  if(parseInt(totalPoint) < parseInt(amt)){
    MCP.alert('할인 금액은 현재 보유중인 포인트보다 높을 수 없습니다.', function(){
      $("#pointAmt").focus();
    });
    return false;
  }

  if(amt % 500 != 0){
    MCP.alert('할인 금액은 500 포인트 단위로 사용가능합니다.', function(){
      $("#pointAmt").focus();
    });
    return false;
  }

  $("#ftrNewParam").val(amt);
  $("#couponPrice").val(amt);
  btn_reg();
}

/** 로밍 하루종일 ON 신청 유효성 검사 */
var btn_roamReg = function(){

  var startDt = $("#statDt").val().trim().replace(/\-/gi, "");
  var endDt = $("#endDt").val().replace(/\-/gi, "");
  var timeSel = $("#timeSel").val();

  if(startDt == ""){
    MCP.alert('로밍 시작일자를 입력해 주세요.', function (){
      $("#statDt").focus();
    });
    return false;
  }

  if(endDt == ""){
    MCP.alert('로밍 종료일자를 입력해 주세요.', function (){
      $("#endDt").focus();
    });
    return false;
  }

  if(startDt < $("#now").val()){
    MCP.alert('시작일자는 오늘날짜보다 이전일수가 없습니다.', function(){
      $("#statDt").focus();
    });
    return false;
  }

  if($("#stDt").val() < startDt){
    MCP.alert('시작일자는 최대 2개월까지만 선택 가능합니다.', function(){
      $("#statDt").focus();
    });
    return false;
  }

  if($("#etDt").val() < endDt){
    MCP.alert('종료일자는 최대 6개월까지만 선택 가능합니다.', function (){
      $("#endDt").focus();
    });
    return false;
  }

  if(endDt <= startDt){
    MCP.alert('종료일자가 시작일자와 같거나 보다 이전일수가 없습니다.', function(){
      $("#endDt").focus();
    });
    return false;
  }

  if(timeSel == null || timeSel == ""){
    MCP.alert('시작 시각을 선택해 주세요.', function(){
      $("#timeSel").focus();
    });
    return false;
  }

  if($("#now").val() == startDt && timeSel <= $("#todayTime").val()){
    MCP.alert('시작일자가 당일이면 현재시간 이후로 선택해주세요.', function(){
      $("#timeSel").focus();
    });
    return false;
  }

  var selParam = startDt + timeSel + ":" + endDt;
  $("#ftrNewParam").val(selParam);
  btn_reg();
}

/** 부가서비스 신청 */
var btn_reg = function() {

  KTM.Confirm("부가서비스 신청하시겠습니까?", function () {

    this.close();
    var couponPrice = ajaxCommon.isNullNvl($("#couponPrice").val(), "0");

    var varData = ajaxCommon.getSerializedData({
       ncn: $("#ncn").val()
      ,soc: $("#rateAdsvcCd").val()
      ,ftrNewParam: $("#ftrNewParam").val()
      ,couponPrice: couponPrice
    });

    ajaxCommon.getItem({
       id: 'regSvcChgAjax'
      ,cache: false
      ,url: '/m/personal/regSvcChgAjax.do'
      ,data: varData
      ,dataType: "json"
    },function (data) {

      // 개인화 URL 정보 미존재
      if (data.RESULT_CODE == "0001") {
        MCP.alert(data.RESULT_MSG, function () {
          location.href = "/m/personal/auth.do";
        });
        return false;
      }

      // 신청 실패
      if (data.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS) {
        var defaultMsg = "부가서비스 신청에 실패하였습니다. 잠시후 다시 이용바랍니다.";
        var errMsg = ajaxCommon.isNullNvl(data.RESULT_MSG, defaultMsg);
        MCP.alert(errMsg);
        return false;
      }

      // 신청 성공
      MCP.alert("부가서비스 신청이 완료되었습니다.", function(){
        $('.c-icon--close').trigger('click');
        regSvcSearch();
      });
    });
  });
}