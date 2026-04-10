
$(document).ready(function(){

  initRoamingPriceFormat(); // 로밍 가격 세팅
  initDateTime();           // 캘린터 세팅
  initViewByLineType();     // 회선 유형에 따라 화면 세팅
  isEmptyValidationChk();   // 신청버튼 활성화 여부

  $(".onlyNum").on("blur keyup", function(){
    $(this).val($(this).val().replace(/[^0-9]/gi,""));
  });

  /** 시작시간 변경 이벤트 */
  $("#startTime").change(function (){
    isEmptyValidationChk();   // 신청버튼 활성화 여부
  });



  /** 시작일 변경 이벤트 */
  $("#useStartDate").change(function(){

    $("#applyRoaming").prop("disabled", true);

    // 시작일 유효성 검사
    var errMsg = validateStartDate();
    if(errMsg == ""){
      // 시작일자에 따른 종료일 설정
      var dateType = $("#dateType").val();
      var usePrd = parseInt($("#usePrd").val());

      if((dateType == '1' || dateType == '3')  && 0 < usePrd) {
        var newEndDate = getUseDate($(this).val(), usePrd - 1);
        $('#useEndDate').val(newEndDate);
      }
    }

    isEmptyValidationChk();   // 신청버튼 활성화 여부
  });

  /** 종료일 변경 이벤트 */
  $("#useEndDate").change(function(){
    isEmptyValidationChk();   // 신청버튼 활성화 여부
  });

  /** 휴대폰 번호 입력 이벤트 */
  $(".addPhone").on('input keyup',function(){

    $("#applyRoaming").prop("disabled", true);

    var $addPhoneGrp = $(this).closest('.addPhoneGrp');
    $addPhoneGrp.next("p.c-form__text").remove();
    $addPhoneGrp.removeClass("has-error");

    var regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
    var phoneFull = '';

    $addPhoneGrp.find('.addPhone').each(function () {
      phoneFull += (phoneFull == '') ? '' : '-';
      phoneFull += $(this).val();
    });

    if(12 <= phoneFull.length && !regPhone.test(phoneFull)){
      viewErrorMgs($(this), "유효한 번호가 아닙니다. 확인 후 다시 입력해주세요.");
      return;
    }

    isEmptyValidationChk();   // 신청버튼 활성화 여부
  });

  /** 로밍 신청하기 버튼 클릭 이벤트 */
  $("#applyRoaming").click(function () {

    $(".c-form__text").remove();
    $(".has-error").removeClass("has-error");

    // 유효성 검사
    var errId = isEmptyValidationChk();
    var errMsg = "";

    if(errId == "useStartDate"){
      errMsg = "시작일을 올바르게 선택해주세요.";
    }else if(errId == "startTime"){
      errMsg = "시작시간을 올바르게 선택해주세요.";
    }else if(errId == "useEndDate"){
      errMsg = "종료일을 올바르게 선택해주세요.";
    }else if(errId == "phone"){
      errMsg = "회선을 올바르게 입력해주세요.";
    }

    if(errMsg != ""){
      MCP.alert(errMsg);
      return false;
    }

    // 시작시간 유효성 검사
    var dateType = $("#dateType").val();
    if(dateType == '2' || dateType == '3'){

      var today = new Date();
      var useToday = getUseDate(today, 0).replace(/[^0-9]/g, '');
      var hours = today.getHours();

      var useStartDate = $('#useStartDate').val().replace(/[^0-9]/g, '');
      var startTime = $('#startTime').val();

      if(useToday == useStartDate && startTime < hours){
        MCP.alert("현재 시간보다 이전 시간은 선택할 수 없습니다.");
        return false;
      }
    }

    // 로밍 가입 신청
    var paramPhoneList = getPhoneList().map(item => item.replace(/[^0-9]/g, ''));
    applyRoamingAjax(paramPhoneList);
  });

}); // end of document.ready --------------------

/** 로밍 가격 세팅 */
function initRoamingPriceFormat(){

  var freeYn = $("#freeYn").val();          // 무료여부
  var price = $("#mmBasAmtVatDesc").val();  // 가격
  var dateType = $("#dateType").val();      // 기간입력 유형
  var usePrd = $("#usePrd").val();          // 이용기간

  var priceView = "";
  var usePrdNumber = parseInt(usePrd);
  var priceNumber = parseInt(ajaxCommon.isNullNvl(price, "").replace(/[^0-9]/gi, ""));

  if(freeYn == "Y"){
    priceView = "무료제공";
  }else if(priceNumber != priceNumber){ // 가격이 숫자가 아닌경우 입력값 그대로 표출
    priceView = ajaxCommon.isNullNvl(price, "-");
  }else if ((dateType == "1" || dateType == "3") && 0 < usePrdNumber){
    priceView = numberWithCommas(priceNumber) + '원 / ' + usePrd + '일';
  }else{
    priceView = numberWithCommas(priceNumber) + '원';
  }

  $("#roamingPrice").html(priceView);
}

/** 캘린더 초기값 세팅 */
function initDateTime(){

  // 날짜 미선택 부가서비스
  if($("#useStartDate").length == 0 || $("#useEndDate").length == 0){
    return;
  }


  // 선택가능 날짜 범위: 시작일(최대 2개월), 종료일(최대 6개월)
  var today = new Date();
  var strtMax = new Date(today.getFullYear(), today.getMonth() + 2, today.getDate());
  var endMax = new Date(today.getFullYear(), today.getMonth() + 6, today.getDate());

  KTM.datePicker('#useStartDate', {
    dateFormat: 'Y-m-d',
    minDate: today,
    maxDate: strtMax
  });

  KTM.datePicker('#useEndDate', {
    dateFormat: 'Y-m-d',
    minDate: today,
    maxDate: endMax
  });

  var dateType = $("#dateType").val();
  var usePrd = parseInt($("#usePrd").val());
  var  romingRateCd = $("#rateAdsvcCd").val();

  // 시작일 및 종료일 설정
  var useStartDate = getUseDate(today, 0);
  var useEndDate = '';
  if((dateType == '1' || dateType == '3') && 0 < usePrd) {
    useEndDate = getUseDate(today, usePrd - 1);
  }

  $("#useStartDate").val(useStartDate);
  $("#useEndDate").val(useEndDate);

  // 기간 선택 유형에 따라 시작시작, 종료일 disabled 처리
  if(dateType == '1') {
    $("#startTime").prop("disabled", true);
    $("#startTime").val("00");
    $("#useEndDate").prop("disabled", true);
  }else if(dateType == '2'){
    $("#endDateWrap").hide();
  } else if(dateType == '3' &&  romingRateCd == 'ITGSAFE3G'){
     $("#useEndDate").prop("disabled", true);
  }else if(dateType == '4'){
    $("#startTime").prop("disabled", true);
  }
}

/** 로밍 날짜 포매팅 */
function getUseDate(paramDate, period){

  var date = new Date(paramDate);
  var useDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() + parseInt(period));
  var useMonth = useDate.getMonth() + 1 + "";
  var useDay = useDate.getDate() + "";

  useMonth = useMonth.padStart(2, "0");
  useDay = useDay.padStart(2, "0");

  return useDate.getFullYear() + '-' + useMonth + '-' + useDay;
}

/** 회선 유형에 따라 화면 세팅 */
function initViewByLineType(){

  var lineType = $("#lineType").val();

  // 일반상품: 화면 세팅작업 없음
  if(lineType == 'G') return;

  // 대표 또는 서브 핸드폰번호 입력 폼 세팅
  var lineCnt =  (lineType == 'S') ? 1 : parseInt($("#lineCnt").val());
  var $htmlId = (lineType == 'S') ? $("#addMainPhone") : $("#addSubPhone");
  var reqLineCnt = parseInt($("#reqLineCnt").val());
  var html = "";

  for(var i = 1; i <= lineCnt; i++){
    html += '<div class="c-form__input-group addPhoneGrp">'
    html += '	 <div class="c-form__input c-form__input-division addPhoneGrp2" name="addPhone">'
    html += '		 <input class="c-input--div3 addPhone onlyNum" id="addPhone' + i + '_1" type="text" pattern="[0-9]" placeholder="숫자입력" title="연락처 앞자리 입력" maxLength="3" onKeyUp="nextFocus(this, 3, \'addPhone'+i+'_2\');">'
    html += '		 <span>-</span>'
    html += '		 <input class="c-input--div3 addPhone onlyNum" id="addPhone' + i + '_2" type="text" pattern="[0-9]" placeholder="숫자입력" title="연락처 중간자리 입력" maxLength="4" onKeyUp="nextFocus(this, 4, \'addPhone'+i+'_3\');">'
    html += '		 <span>-</span>'
    html += '		 <input class="c-input--div3 addPhone onlyNum" id="addPhone' + i + '_3" type="text" pattern="[0-9]" placeholder="숫자입력" title="연락처 뒷자리 입력" maxLength="4">'
    html += '		 <label class="c-form__label" for="addPhone' + i + '_1">휴대폰 번호'

    if(lineType == 'M'){
      if(reqLineCnt > 0){
        html += '(필수)';
        reqLineCnt--;
      } else {
        html += '(선택)';
      }
    }

    html += '    </label>'
    html += '	 </div>'
    html += '</div>'
  }

  $htmlId.html(html);

  // 대표상품: 안내문구 추가
  if(lineType == 'M'){

    var rateAdsvcNm = $("#rateAdsvcNm").val();
    rateAdsvcNm = rateAdsvcNm.substring(0, rateAdsvcNm.length - 4);

    var subRoamNameHtml = "추가 등록된 고객님도 '" + rateAdsvcNm + "(서브)'를 별도 가입하셔야합니다.";
    $("#subRoamName").html(subRoamNameHtml);
  }
}

/** 자동 포커싱 */
function nextFocus(obj, len, id){
  if($(obj).val().length >= len){
    $('#' + id).focus();
  }
}

/** 신청버튼 활성화 여부 - 오류 아이디 리턴 */
function isEmptyValidationChk(){

  // 날짜 에러문구 제거
  $("#useStartDate").parent().next("p.c-form__text").remove();
  $("#useStartDate").parent().removeClass("has-error");
  $("#useEndDate").parent().next("p.c-form__text").remove();
  $("#useEndDate").parent().removeClass("has-error");

  // 기간 입력 유형에 따른 필수값 체크
  var dateType = $("#dateType").val();
  var dateRequiredList = getRequiredArrayByDateType(dateType);
  var emptyId = dateRequiredList.find(item => !$("#"+item).val());

  if(emptyId != undefined){
    $("#applyRoaming").prop("disabled", true);
    return emptyId;
  }

  if(0 < dateRequiredList.length){
    // 시작일 체크
    var startChkMsg = validateStartDate();
    if(startChkMsg != ""){
      $("#applyRoaming").prop("disabled", true);
      viewErrorMgs($("#useStartDate"), startChkMsg);
      return "useStartDate";
    }

    // 종료일 체크
    var endChkMsg = validateEndDate();
    if(endChkMsg != ""){
      $("#applyRoaming").prop("disabled", true);
      viewErrorMgs($("#useEndDate"), endChkMsg);
      return "useEndDate";
    }
  }

  // 대표 및 서브 핸드폰 번호 유효성 검사
  var lineType = $("#lineType").val();
  var phoneFlag = validatePhoneByLineType(lineType);

  if(!phoneFlag){
    $("#applyRoaming").prop("disabled", true);
    return "phone"; // 아이디 대신 phone 문자열 리턴
  }

  $("#applyRoaming").prop("disabled", false);
  return ""; // 유효성 통과 시 빈문자열 리턴
}

/** 기간 입력 유형에 따른 필수값 */
function getRequiredArrayByDateType(dateType){

  var dateRequired = [];

  if(dateType == "1") {
    // 시작일
    dateRequired.push("useStartDate");
  }else if(dateType == "2"){
    // 시작일, 시간
    dateRequired.push("useStartDate");
    dateRequired.push("startTime");
  }else if(dateType == "3"){
    // 시작일, 시간, 종료일
    dateRequired.push("useStartDate");
    dateRequired.push("startTime");
    dateRequired.push("useEndDate");
  }else if(dateType == "4"){
    // 시작일, 종료일
    dateRequired.push("useStartDate");
    dateRequired.push("useEndDate");
  }

  return dateRequired;
}

/** 대표 및 서브 입력 핸드폰번호 배열 */
function getPhoneList(){

  var addPhoneList = [];

  $('.addPhoneGrp2').each(function () {
    var addPhone = '';

    $(this).children('input').each(function () {
      addPhone += (addPhone == '') ? '' : '-';
      addPhone += $(this).val();
    });

    if(0 < addPhone.length) addPhoneList.push(addPhone);
  });

  return addPhoneList;
}

/** 대표 및 서브 핸드폰 번호 유효성 검사 */
function validatePhoneByLineType(lineType){

  // 일반상품: 번호 유효성 검사 대상 아님
  if(lineType == 'G') return true;

  var regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
  var reqLineCnt = parseInt($("#reqLineCnt").val());

  var addPhoneList = getPhoneList();

  // 유효성 검증 통과 개수
  var passCnt = addPhoneList.filter(item => regPhone.test(item)).length;
  var failCnt = addPhoneList.length - passCnt;

  if(0 < failCnt) return false;
  if(passCnt < reqLineCnt) return false;

  return true;
}

/** 시작일자 유효성 검사: 오류메세지 리턴 */
function validateStartDate(){

  // 기간 설정 유형 (dateType)
  // 1: 시작일
  // 2: 시작일 + 시작시간
  // 3: 시작일 + 시작시간 + 종료일
  // 4: 시작일 + 종료일

  var errMsg = "";
  var lineType = $("#lineType").val();
  var dateType = $("#dateType").val();

  var today = new Date();
  var useToday = getUseDate(today, 0).replace(/[^0-9]/g, '');

  var strtMax = new Date(today.getFullYear(), today.getMonth() + 2, today.getDate());
  var useStrtMax = getUseDate(strtMax, 0).replace(/[^0-9]/g, '');

  var useStartDate = $('#useStartDate').val().replace(/[^0-9]/g, '');
  var useEndDate = ajaxCommon.isNullNvl($('#useEndDate').val(), '9999.12.31');

  if(dateType == '1' || dateType == '3') useEndDate = '9999.12.31';
  useEndDate = useEndDate.replace(/[^0-9]/g, '');

  if(useStartDate < useToday){
    errMsg = "시작일자는 오늘 날짜보다 이전일 수 없습니다.";
  }else if(useStartDate > useStrtMax){
    errMsg = "시작일자는 최대 2개월까지만 선택 가능합니다.";
  }else if(useStartDate > useEndDate){
    errMsg = "시작일자는 종료일자 이전이어야 합니다.";
  }else if((lineType == 'M' || lineType == 'G') && (useStartDate == useEndDate)){
    // 대표회선 또는 일반회선인 경우, 시작일자와 종료일자가 같으면 안됨
    errMsg = "시작일자는 종료일자 이전이어야 합니다.";
  }

  return errMsg;
}

/** 종료일자 유효성 검사: 오류메세지 리턴 */
function validateEndDate(){

  // 기간 설정 유형 (dateType)
  // 1: 시작일
  // 2: 시작일 + 시작시간
  // 3: 시작일 + 시작시간 + 종료일
  // 4: 시작일 + 종료일

  var errMsg = "";
  var lineType = $("#lineType").val();
  var dateType = $("#dateType").val();

  // 종료일 유효성 검사 대상 아님
  if(dateType == "2") return "";

  var today = new Date();
  var endMax = new Date(today.getFullYear(), today.getMonth() + 6, today.getDate());
  var useEndMax = getUseDate(endMax, 0).replace(/[^0-9]/g, '');

  var useStartDate = $('#useStartDate').val().replace(/[^0-9]/g, '');
  var useEndDate = ajaxCommon.isNullNvl($('#useEndDate').val(), useEndMax).replace(/[^0-9]/g, '');

  if(useStartDate > useEndDate){
    errMsg = "종료일자는 시작일자 이후여야 합니다.";
  }else if(useEndDate > useEndMax){
    errMsg = "종료일자는 최대 6개월까지만 선택 가능합니다.";
  }else if(dateType != '1'){
    // 대표회선 또는 일반회선인 경우, 시작일자와 종료일이 같으면 안됨
    // 시작일만 선택하는 상품(1) 예외
    if((lineType == 'M' || lineType == 'G') && (useStartDate == useEndDate)){
      errMsg = "종료일자는 시작일자 이후여야 합니다.";
    }
  }

  return errMsg;
}

/** 에러 문구 표출 */
function viewErrorMgs($thatObj, msg) {
  if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
    $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
  } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3") ) {
    $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
  }
}

/** 로밍 가입 신청 AJAX */
function applyRoamingAjax(paramPhoneList) {

  var confirmMsg = "해외 로밍 상품을 신청하시겠습니까?";
  var completeMsg = "해외로밍 신청이 완료되었습니다.";
  var completeErrMsg = "로밍 신청에 실패하였습니다. 잠시후 다시 이용바랍니다.";
  if($("#flag").val() == "Y"){
    confirmMsg = "해외 로밍 상품을 변경하시겠습니까?";
    completeMsg = "해외로밍 변경이 완료되었습니다.";
    completeErrMsg = "로밍 변경에 실패하였습니다. 잠시후 다시 이용바랍니다.";
  }

  KTM.Confirm(confirmMsg, function(){

    this.close();

    var varData = ajaxCommon.getSerializedData({
       ncn: $("#ncn").val()
      ,rateAdsvcGdncSeq: $("#rateAdsvcGdncSeq").val()
      ,soc: $("#rateAdsvcCd").val()
      ,strtDt: ajaxCommon.isNullNvl($("#useStartDate").val(), "").replace(/[^0-9]/g, '')
      ,strtTm: ajaxCommon.isNullNvl($("#startTime option:selected").val(),"")
      ,endDt: ajaxCommon.isNullNvl($("#useEndDate").val(), "").replace(/[^0-9]/g, '')
      ,addPhone: paramPhoneList
      ,flag: $("#flag").val()
      ,prodHstSeq: $("#prodHstSeq").val()
    });

    ajaxCommon.getItem({
       id:'roamingJoinAjax'
      ,cache:false
      ,url:'/personal/roamingJoinAjax.do'
      ,data: varData
      ,dataType:"json"
    },function(data){

      // 개인화 URL 정보 미존재
      if(data.RESULT_CODE == "0001"){
        MCP.alert(data.RESULT_MSG, function(){
          location.href = "/personal/auth.do";
        });
        return false;
      }

      // 신청 실패
      if(data.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
        var errMsg = ajaxCommon.isNullNvl(data.RESULT_MSG, completeErrMsg);
        MCP.alert(errMsg);
        return false;
      }

      // 신청 성공
      MCP.alert(completeMsg, function () {
        $('.c-icon--close').trigger('click');
        select();
      });
    });
  });
}