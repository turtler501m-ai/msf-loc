
var v_ctgList; // xml 카테고리 목록
var v_proList; // xml 상품 목록


var pageObj = {
  rateGdncSeq:""
  ,rateAdsvcCtgCd:""
}


$(document).ready(function(){

  $(".footer-info").siblings("button").hide();

  // 모달창 스타일 적용
  $("#modalArs").addClass("rate-detail-view");

  // 현재 요금제 정보 조회
  rateResSearch();

  // 메인 카테고리 탭 클릭 이벤트
  $(document).on("click", "button.main_tab", function(){
    $('button.main_tab').removeClass('is-active');
    $('button.main_tab').removeAttr("aria-hidden");
    $('button.main_tab').attr('aria-selected', "false");
    $(this).addClass('is-active');
    $(this).attr('aria-selected', "true");
  });

  // 서브 카테고리 탭 클릭 이벤트
  $(document).on("click", "button[id^=subTab_]", function(){
    $('button[id^=subTab_]').removeClass('is-active');
    $('button[id^=subTab_]').attr('aria-selected', "false");
    $(this).addClass('is-active');
    $(this).attr('aria-selected', "true");
  });

}); // end of document.ready -----------------------

/** 현재 요금제 정보 및 예약정보 조회 */
var rateResSearch = function(){

  var varData = ajaxCommon.getSerializedData({
    ncn : $("#ctn option:selected").val()
  });

  ajaxCommon.getItem({
     id: 'myRateResvViewAjax'
    ,cache: false
    ,url: '/m/personal/myRateResvViewAjax.do'
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
      MCP.alert(data.RESULT_MSG);
      return false;
    }

    $("#prvRateGrpNm").text(data.prvRateGrpNm);
    $("#rateAdsvcLteDesc").text(data.rateAdsvcLteDesc);
    $("#rateAdsvcCallDesc").text(data.rateAdsvcCallDesc);
    $("#rateAdsvcSmsDesc").text(data.rateAdsvcSmsDesc);
    $("#prvRateGdncSeq").val(data.prvRateGdncSeq);

    var myPriceInfo = data.myPriceInfo;
    $("#prvRateCd").val(myPriceInfo.prvRateCd);

    var appStartDd = myPriceInfo.appStartDd;
    if(appStartDd.length >= 8){
      $("#appStartDdDate").html("신청일 : "+ appStartDd.substring(0,4) + "." + appStartDd.substring(4,6) + "."+appStartDd.substring(6,8));
    }

    // 요금제 변경 예약 존재
    if(myPriceInfo.rsrvPrdcCd != ""){
      $("#rsrvPrdcNm").html(myPriceInfo.rsrvPrdcNm);
      $("#totalMony").html(numberWithCommas(parseInt(myPriceInfo.rsrvBasicAmt)));
      $("#rsrvPrdcChk").val("Y");
      $(".rsrvPrdcSelData").show();
    } else {
      $(".rsrvPrdcSelData").hide();
    }

    // 변경가능한 요금제 존재여부
    var html = '';
    $(".tab_view").html('');

    if(data.total == 0){
      html += '<div class="c-nodata">';
      html += '  <i class="c-icon c-icon--nodata" aria-hidden="true" ></i>';
      html += '  <p class="c-noadat__text">변경 가능한 요금제가 없습니다.</p>';
      html += '</div>';
      $(".tab_view").html(html);
    }else {
      html += '<div class="c-tabs__list c-expand" id="1depthList" data-ignore="true">';
      html += '</div>';
      $(".tab_view").html(html);
      loadXmlData(); // 메인 카테고리 조회
    }
  });
}

/** 요금제 변경 예약 취소 */
var btn_canCel = function(){

  var varData = ajaxCommon.getSerializedData({
    ncn : $("#ctn option:selected").val()
  });

  KTM.Confirm("변경예약을 취소하시겠습니까?", function (){

    this.close();

    ajaxCommon.getItem({
       id: 'deleteMyRateResvAjax'
      ,cache: false
      ,url: '/m/personal/deleteMyRateResvAjax.do'
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

      // 예약취소 실패
      if(data.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
        MCP.alert(data.RESULT_MSG);
        return false;
      }

      // 예약취소 성공
      MCP.alert("변경 예약이 취소되었습니다.", function(){
        $("#rsrvPrdcChk").val('');
        rateResSearch();
      });
    });
  });
}

/** 메인 카테고리 조회 */
var initXmlData = function() {

  KTM.LoadingSpinner.show();

  ajaxCommon.getItemNoLoading({
     id: 'farPriceCtgTab1Ajax'
    ,cache: false
    ,async: false
    ,url: '/mypage/farPriceCtgTab1Ajax.do'
    ,data: ''
    ,dataType: "json"
    ,errorCall: function () {
      KTM.LoadingSpinner.hide();
    }
  },function(data){
    v_ctgList = data.item.ctgList.item;
    v_proList = data.item.proList.item;
    KTM.LoadingSpinner.hide();
  });
}

/** 메인 카테고리 화면 생성 */
var loadXmlData = function(){

  var html = '';
  $("#1depthList").html('');
  $("#tabChk").val('');

  // 카테고리 전체 데이터 조회
  initXmlData();

  //로딩시 카테고리 조회
  $.each(v_ctgList, function(idx, item) {
    var outHtml = '';

    if(item.ctgOutputCd == $("#ctgOutputCd").val()){
      if(item.depthKey == 1 && item.rateAdsvcDivCd == $("#rateAdsvcDivCd").val()){

        html = '<button class="c-tabs__button main_tab" data-tab-header="#tabA1-panel_' + idx + '" href="javascript:void(0);" onclick="mainTabClick(\'' + item.rateAdsvcCtgCd+'\',\'' + idx + '\');">' + item.rateAdsvcCtgNm + '</button>';

        outHtml += '<div class="c-tabs__panel" id="tabA1-panel_' + idx + '" style="display: none;">';
        outHtml += '</div>';

        $(".tab_view").append(outHtml);
        MCP.addSingleTab('#1depthList', '.c-tabs__button', html, null);
      }
    }
  });

  // 첫번째 탭 클릭하여 서브 카테고리 생성
  $('.c-tabs__button')[0].click();
}

/** 메인 카테고리 클릭 이벤트 -> 서브 카테고리 조회 */
var mainTabClick = function(rateAdsvcCtgCd, idx){

  var varData = ajaxCommon.getSerializedData({
     rateAdsvcCtgCd: rateAdsvcCtgCd
    ,rateCd: $("#prvRateCd").val()
  });

  // 서브 카테고리 조회
  ajaxCommon.getItem({
    id: 'farPriceCtgTab2Ajax'
    ,cache: false
    ,url: "/mypage/farPriceCtgTab2Ajax.do"
    ,data: varData
    ,dataType: "json"
  },function(data){
    mainTabClickCallback(data, idx);
  });
}

/** 서브 카테고리 화면 생성 */
var mainTabClickCallback = function(resp, idx){

  var buttonXmlList = resp.buttonXmlList;

  var outHtml = '';
  outHtml += '<div class="c-tabs c-tabs--type3" data-ignore="true">';
  outHtml += '  <div class="c-tabs__list c-expand">';

  if(buttonXmlList != null && buttonXmlList != ""){
    for(var i=0; i< buttonXmlList.length; i++){
      outHtml += "<button id='subTab_" + buttonXmlList[i].rateAdsvcCtgCd + "' data-tab-header='#tabB1-panel_" + idx + "' class='c-tabs__button' href='javascript:void(0);' onclick='subTabClick(\"" + buttonXmlList[i].rateAdsvcCtgCd + "\",\"" + idx + "\")'>" + buttonXmlList[i].rateAdsvcCtgNm + "</button>";
    }
  }

  outHtml += '  </div>';
  outHtml += '  <div class="c-tabs__panel" id="tabB1-panel_' + idx + '">';
  outHtml += '  </div>';
  outHtml += '</div>';

  // 보고있던 기존탭 숨기기
  $("#tabA1-panel_" + $("#tabChk").val()).hide();

  // 메인탭에 서브 카테고리 화면 생성
  $("#tabA1-panel_" + idx).html(outHtml);
  $("#tabA1-panel_" + idx).show();
  $("#tabChk").val(idx);
  $("#upRateAdsvcCtgCd").val(resp.upRateAdsvcCtgCd);

  // 서브 카테고리가 없는 경우 메인카테고리의 제품리스트 조회
  // 서브 카테고리가 있는 경우 첫번째 서브카테고리의 제품리스트 조회
  if(buttonXmlList == null || buttonXmlList == ""){
    subTabClick($("#upRateAdsvcCtgCd").val(), idx);
  }else{
    $("#tabA1-panel_" + idx + " " + "button[id^=subTab_]")[0].click();
  }
}

/** 서브 카테고리 클릭 이벤트 -> 제품 리스트 조회 */
var subTabClick = function(rateAdsvcCtgCd, idx){

  var varData = ajaxCommon.getSerializedData({
     rateAdsvcCtgCd : rateAdsvcCtgCd
    ,rateCd : $("#prvRateCd").val()
  });

  // 카테고리에 해당하는 제품 조회
  ajaxCommon.getItem({
     id: 'farPriceCtgTab3Ajax'
    ,cache: false
    ,url: "/mypage/farPriceCtgTab3Ajax.do"
    ,data: varData
    ,dataType: "json"
  },function(data){
    subTabClickCallback(data, rateAdsvcCtgCd, idx);
  });
}

/** 제품 리스트 화면 생성 */
var subTabClickCallback = function(resp, rateAdsvcCtgCd, idx){

  var data = resp.ctgXmlList;
  var html = "";

  $("#strRateAdsvcCtgCd").val('');

  // 변경 가능한 요금제 없음
  if(data == null || data.length <= 0){
    html += '<div class="c-nodata">';
    html += '  <i class="c-icon c-icon--nodata" aria-hidden="true" ></i>';
    html += '  <p class="c-noadat__text">변경 가능한 요금제가 없습니다.</p>';
    html += '</div>';
    $("#tabB1-panel_" + idx).html(html);
    return false;
  }

  // 리스트 중복 제거
  const arrDup = data;
  const arrUniqueList = arrDup.filter((character, idx, arr) => {
    return arr.findIndex((item) => item.rateAdsvcCtgCd === character.rateAdsvcCtgCd) === idx
  });

  for(var i=0; i < arrUniqueList.length; i++){

    $("#strRateAdsvcCtgCd").val(arrUniqueList[i].rateAdsvcCtgCd);

    html += '<div class="c-accordion c-accordion--type1">';
    html += '  <ul class="c-accordion__box" id="accordionB">';
    html += '    <li class="c-accordion__item forIdView' + arrUniqueList[i].rateAdsvcCtgCd + '"   id="forId' + arrUniqueList[i].rateAdsvcCtgCd + '" >';
    html += '      <div class="c-accordion__head" data-acc-header="#compareCSP' + arrUniqueList[i].rateAdsvcCtgCd + '">';
    html += '        <button class="c-accordion__button" type="button" aria-expanded="false" href="javascript:void(0);" onclick="showUsimAccordion(' + arrUniqueList[i].rateAdsvcCtgCd + ', ' + idx + ', this)">' + arrUniqueList[i].rateAdsvcCtgNm + '<p class="c-text c-text--type4 u-co-gray-7">' + arrUniqueList[i].rateAdsvcCtgBasDesc + '</p>';
    html += '        </button>';
    html += '      </div>';
    html += '      <div class="c-accordion__panel expand c-expand" id="compareCSP' + arrUniqueList[i].rateAdsvcCtgCd+ '">';
    html += '        <div class="c-accordion__inside" id="compareView' + arrUniqueList[i].rateAdsvcCtgCd + '">';
    html += '        </div>';
    html += '      </div>';
    html += '    </li>';
    html += '  </ul>';
    html += '</div>';
  }

  $("#tabB1-panel_" + idx).html(html);
  KTM.initialize();
}

/** 요금제 아코디언 컨텐츠 조회 -> 상세 제품리스트 조회 */
var showUsimAccordion = function(rateAdsvcCtgCd, id){

  // 아코디언 닫히는 경우 ajax 호출하지 않음
  var isOpen = $("#forId" + rateAdsvcCtgCd + " div.c-accordion__head").hasClass("is-active");
  if(isOpen) return;

  var varData = ajaxCommon.getSerializedData({
     rateAdsvcCtgCd : rateAdsvcCtgCd
    ,rateCd : $("#prvRateCd").val()
  });

  //페이지 호출
  ajaxCommon.getItem({
     id: 'farPriceContent'
    ,cache: false
    ,url: "/mypage/farPriceContent.do"
    ,data: varData
    ,dataType: "json"
  },function(data){
    AccordionClickCallback(data, id, rateAdsvcCtgCd);
  });
}

/** 상세 제품리스트 화면 생성 */
var AccordionClickCallback = function(data, id, rateAdsvcCtgCd){

  var html = "";
  var data = data.prodXmlList;

  if(data == null || data.length <= 0){
    $("#compareView" + rateAdsvcCtgCd).html('-');
    return false;
  }

  for(var idx=0; idx<data.length; idx++){
    html += '<div class="c-card c-card--type2">';
    html += '  <div class="c-card__box">';
    html += '    <div class="c-card__title">';
    html += '      <div class="c-radio__box u-pt--20 c-flex">';
    html += '        <div class="u-width--60p">';
    html += '          <span class="c-card__subtitle" id="radA' + data[idx].rateAdsvcGdncSeq + '">' + data[idx].rateAdsvcNm + '</span>';
    html += '          <input id="rateAdsvcCd' + data[idx].rateAdsvcGdncSeq + '" type="hidden" value=' + data[idx].rateAdsvcCd + '>';
    html += '        </div>';
    html += '        <div>';
    html+= '           <button class="c-button c-button--xsm c-button--primary" onclick="btn_ratePop(\'' + data[idx].rateAdsvcGdncSeq + '\',\'' + data[idx].rateAdsvcCd + '\');">상세</button>';
    html+= '           <button class="c-button c-button--xsm c-button--primary u-ml--4" onclick="openFarChgModal(\'' + data[idx].rateAdsvcGdncSeq + '\',\'' + rateAdsvcCtgCd + '\');">변경</button>';
    html += '        </div>';
    html += '      </div>';
    html += '      <hr class="c-hr c-hr--type3 u-mt--20">';
    html += '    </div>';
    html += '    <div class="c-card__price-box u-co-mint">';
    html += '      <input id="promotionAmtVatDesc' + data[idx].rateAdsvcGdncSeq + '" type="hidden"  value=' + data[idx].promotionAmtVatDesc + '>';

    if(data[idx].promotionAmtVatDesc != null && data[idx].promotionAmtVatDesc != "" && data[idx].promotionAmtVatDesc != "0"){
      html += '    <span class="c-text c-text--type4">월 기본료(VAT 포함)</span>';
      html += '    <span class="c-text u-td-line-through u-ml--auto" id="mmBasAmtVat' + data[idx].rateAdsvcGdncSeq + '">' + data[idx].mmBasAmtVatDesc + '</span>';
      html += '    <span>';
      html += '      <b>' + data[idx].promotionAmtVatDesc + '</b>원';
      html += '    </span>';
    }else{
      html += '    <span class="c-text c-text--type3">월 기본료(VAT 포함)</span>';
      html += '    <span id="mmBasAmtVat' + data[idx].rateAdsvcGdncSeq + '">';
      html += '      <b>' + data[idx].mmBasAmtVatDesc + '</b>원';
      html += '    </span>';
    }

    html += '    </div>';
    html += '  </div>';
    html += '</div>';
  }

  $("#compareView" + rateAdsvcCtgCd).html(html);
  KTM.initialize();
}

/** 요금제 상세보기 팝업 */
var btn_ratePop = function(rateAdsvcGdncSeq, rateAdsvcCd){
  openPage('pullPopup', '/m/rate/rateLayer.do?rateAdsvcGdncSeq=' + rateAdsvcGdncSeq + '&rateAdsvcCd=' + rateAdsvcCd,'');
}

/** 요금제 변경 팝업 */
var btn_farChg = function(rateAdsvcGdncSeq, rateAdsvcCtgCd, chkRadion){

  if($("#rsrvPrdcChk").val() == "Y" ){
    MCP.alert("등록된 요금제 예약변경 취소 후 요금제 변경이 가능합니다.");
    return;
  }

  if($("#compareView" + rateAdsvcCtgCd + " div.c-card").length <= 0 || !rateAdsvcGdncSeq){
    MCP.alert("가입하실 요금제를 선택해 주세요.");
    return;
  }

  var varData = ajaxCommon.getSerializedData({
    ncn: $("#ctn option:selected").val()
  });

  ajaxCommon.getItem({
     id: 'possibleStateCheckAjax'
    ,cache: false
    ,url: '/m/personal/possibleStateCheckAjax.do'
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

    // 요금제 변경 불가
    var errMsg = "";

    if(data.RESULT_CODE == "001" || data.RESULT_CODE == "004" || data.RESULT_CODE == "005"){
      errMsg += "고객님께서는 고객센터(고객센터 114/무료) 를 통해 요금제 변경신청 가능하십니다.<br>";
      errMsg += "감사합니다.<br><br>";
      errMsg += "[고객센터를 통해서만 변경이 가능한 고객님]<br>";
      errMsg += "단말/유심상품으로 (재)약정 가입고객, <br>";
      errMsg += "미성년자, 법인, 개통 후 실사용일 90일이하, 일부 대리점을 통해 유심상품 가입고객";
    }else if(data.RESULT_CODE == "006"){
      errMsg += "해당 시간은 요금제 변경이 불가 합니다. <br>(23:30분 ~ 익일 00:30분, 1시간)<br>";
      errMsg += "위 시간 이후 요금제 변경 진행 바랍니다.<br>";
      errMsg += "감사합니다.<br>";
    }else if(data.RESULT_CODE == "008"){
      errMsg += "고객님께서는 고객센터(고객센터 114/무료) 를 통해 요금제 변경신청 가능하십니다.<br>";
      errMsg += "감사합니다."
    }else if(data.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
      errMsg += "시스템에 문제가 발생 하였습니다.<br>";
      errMsg += "잠시후에 다시 시도 하시기 바랍니다.<br>";
      errMsg += "도움드리지 못해 죄송합니다.<br>";
    }

    if(errMsg != ""){
      MCP.alert(errMsg);
      return;
    }

    // 요금제 변경 팝업 호출
    var baseAmt = $("#promotionAmtVatDesc" + rateAdsvcGdncSeq).val();
    if(baseAmt == null || baseAmt == ""){
      baseAmt = $("#mmBasAmtVat" + rateAdsvcGdncSeq).text();
    }

    if(baseAmt.indexOf("원") == -1){
      baseAmt = baseAmt + "원";
    }

    var parameterData = ajaxCommon.getSerializedData({
       ncn: $("#ctn option:selected").val()
      ,instAmt: $("#instAmt").text()
      ,prvRateGrpNm: $("#prvRateGrpNm").text()
      ,rateNm: $("#radA" + rateAdsvcGdncSeq).text()
      ,baseAmt: $.trim(baseAmt)
      ,rateAdsvcCd: $("#rateAdsvcCd" + rateAdsvcGdncSeq).val()
      ,nxtRateGdncSeq: rateAdsvcGdncSeq
      ,prvRateGdncSeq: $("#prvRateGdncSeq").val()
      ,isActivatedThisMonth: data.isActivatedThisMonth // 이번달 개통여부
      ,isFarPriceThisMonth: data.isFarPriceThisMonth   // 이번달 요금제 변경여부
      ,chkRadion : chkRadion
    });

    openPage('pullPopupByPost','/m/personal/regServicePop.do',parameterData);
  });
}

/** 회선 조회 */
var select = function(){

  ajaxCommon.createForm({
     method: "post"
    ,action: "/m/personal/farPricePlanView.do"
  });

  ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
  ajaxCommon.formSubmit();
}


function buildOverUsageText(result) {
  const map = {
    isOverUsageDatCharge: "데이터",
    isOverUsageVoiCharge: "통화",
    isOverUsageSmsCharge: "문자"
  };

  // true 값만 필터링 → 한글명 배열 생성
  const list = Object.keys(map)
      .filter(key => result[key] === true)
      .map(key => map[key]);

  return list.length > 0 ? `[${list.join(" / ")}]` : "";
}

//요금제 변경 시작,안내 팝업
function openFarChgModal(seq,rateAdsvcCtgCd) {
  pageObj.rateGdncSeq = seq ;
  pageObj.rateAdsvcCtgCd = rateAdsvcCtgCd ;

  var varData = ajaxCommon.getSerializedData({
    ncn : $("#ctn option:selected").val()
  });
  ajaxCommon.getItem({
    id : 'isOverUsageCharge'
    , cache : false
    , async : false
    , url : '/personal/isOverUsageChargeAjax.do'
    , data : varData
    , dataType : "json"
  },function(jsonObj){
    var resultCode = jsonObj.RESULT_CODE;
    if(resultCode=="00000"){
      if (jsonObj.isOverUsageCharge) {
        const overText = buildOverUsageText(jsonObj);
        $("#chgInfoTxt").text(overText);
        //openModal('#farChgOvertModal');
        openModal('#farChgStartModal');
      } else {
        //첫 번째 모달 오픈
        openModal('#farChgStartModal');
      }
    } else if (resultCode== "0001") {
      MCP.alert("개인화 URL 재발급/재인증 부탁드립니다.");
    } else {
      MCP.alert("잠시후 다시 이용해주세요.");
      return false;
    }
  });


}


//첫 번째 모달 버튼 이벤트
$('#btnFarChgY').off('click').on('click', function() {
  closeModal('#farChgStartModal');
  openModal('#farChgWarningModal');
});

$('#btnFarChgN').off('click').on('click', function() {
  closeModal('#farChgStartModal');
  btn_farChg(pageObj.rateGdncSeq,pageObj.rateAdsvcCtgCd,'S');
});

//두 번째 모달 버튼 이벤트
$('#btnFarChgReserve').off('click').on('click', function() {
  closeModal('#farChgWarningModal');
  btn_farChg(pageObj.rateGdncSeq,pageObj.rateAdsvcCtgCd,'P');
});

$('#btnFarChgImmediate').off('click').on('click', function() {
  closeModal('#farChgWarningModal');
  btn_farChg(pageObj.rateGdncSeq,pageObj.rateAdsvcCtgCd,'S');
});

$('#btnFarOverChgN').on('click', function() {
  closeModal('#farChgOvertModal');
  openModal('#farChgOvertModal2');
});


$('#btnFarOverChgY').on('click', function() {
  closeModal('#farChgOvertModal');
  btn_farChg(pageObj.rateGdncSeq,pageObj.rateAdsvcCtgCd,'P');
});


$('#btnAgree').on('click', function() {
  closeModal('#farChgOvertModal2');
  btn_farChg(pageObj.rateGdncSeq,pageObj.rateAdsvcCtgCd,'S');
});



function openModal(selector) {
    const el = $(selector)[0];
    const modal = KTM.Dialog.getInstance(el) || new KTM.Dialog(el);
    modal.open();
}

function closeModal(selector) {
    const el = $(selector)[0];
    const modal = KTM.Dialog.getInstance(el);
    if (modal) modal.close();
}