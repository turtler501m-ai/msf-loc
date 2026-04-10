
var v_ctgList; // xml 카테고리 목록
var v_proList; // xml 상품 목록

var pageObj = {
  rateGdncSeq:""
}


$(document).ready(function(){

  // 모달창 스타일 적용
  $("#modalArs").addClass("rate-detail-view");

  // 현재 요금제 정보 조회
  rateResSearch();

  // 메인 카테고리 탭 클릭 이벤트
  $(document).on("click", "button[id^=mainTab]", function(){
    $('button[id^=mainTab]').removeClass('is-active');
    $('button[id^=mainTab]').removeAttr("aria-hidden");
    $('button[id^=mainTab]').attr('aria-selected', "false");
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

  // 제품 리스트 아코디언 클릭 이벤트
  $(document).on("click", ".runtime-data-insert", function(){
    var hasClass = $(this).hasClass("is-active");
    var title = $(this).siblings().find(".product__title").text();

    if(hasClass){
      $(this).children().remove();
      $(this).append("<span class='c-hidden'>" + title + " 정보 펼쳐보기</span>");
    } else {
      $(this).children().remove();
      $(this).append("<span class='c-hidden'>" + title + " 정보 접기</span>");
    }
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
    ,url: '/personal/myRateResvViewAjax.do'
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

    if(data.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
      MCP.alert(data.RESULT_MSG);
      return false;
    }

    $("#prvRateGrpNm").html("이용중인 요금제는<br><b>" + data.prvRateGrpNm + "</b>입니다.");
    $("#rateAdsvcLteDesc").text(data.rateAdsvcLteDesc);
    $("#rateAdsvcCallDesc").text(data.rateAdsvcCallDesc);
    $("#rateAdsvcSmsDesc").text(data.rateAdsvcSmsDesc);
    $("#prvRateGdncSeq").val(data.prvRateGdncSeq);
    $("#prvRateNm").val(data.prvRateGrpNm);

    var myPriceInfo = data.myPriceInfo;
    $("#prvRateCd").val(myPriceInfo.prvRateCd);

    var appStartDd = myPriceInfo.appStartDd;
    if(appStartDd.length >= 8){
      $("#appStartDdDate").html("신청일 : "+ appStartDd.substring(0,4) + "." + appStartDd.substring(4,6) + "."+appStartDd.substring(6,8));
    }

    // 요금제 변경 예약 존재
    if(myPriceInfo.rsrvPrdcCd != ""){
      var outHtml = "";
      outHtml += '<tr>';
      outHtml += '  <td>' + myPriceInfo.rsrvPrdcNm + '</td>';
      outHtml += '  <td> 월' + numberWithCommas(parseInt(myPriceInfo.rsrvBasicAmt)) + '원 </td>';
      outHtml += '  <td>';
      outHtml += '    <button class="c-button c-button--underline c-button--sm u-co-sub-4" onclick="btn_canCel();">취소</button>';
      outHtml += '  </td>'
      outHtml += '</tr>';

      $("#rsrvPrdcView").html(outHtml);
      $("#rsrvPrdcChk").val("Y");
      $(".resYn").show();
    } else {
      $(".resYn").hide();
    }

    // 변경가능한 요금제 존재여부
    var html = '';
    $(".tab_view").html('');

    if(data.total == 0){
      html += '<div class="c-nodata">';
      html += '  <i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
      html += '  <p class="c-nodata__text">변경 가능한 요금제가 존재하지 않습니다.</p>';
      html += '</div>';
      $(".tab_view").html(html);
    }else {
      html += '<div class="c-tabs c-tabs--type1">';
      html += '  <div class="c-tabs__list u-width--100p" role="tablist" id="1depthList">';
      html += '  </div>';
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
      ,url: '/personal/deleteMyRateResvAjax.do'
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

        html = '<button class="c-tabs__button" id="mainTab' + idx + '" role="tab" onclick="mainTabClick(\'' + item.rateAdsvcCtgCd + '\',\'' + idx + '\');">' + item.rateAdsvcCtgNm + '</button>';

        outHtml +='<div class="c-tabs__panel" id="tabD' + idx + 'panel" tabindex="0" style="display: none;">';
        outHtml +='  <div class="c-tabs c-tabs--type2">';
        outHtml +='    <div class="c-tabs__list" role="tablist" id="tabDtl' + idx + '">';
        outHtml += '   </div>';
        outHtml +='    <div class="c-tabs__panel u-block" id="rateDtl' + item.rateAdsvcDivCd + '" >';
        outHtml +='      <div class="c-row c-row--lg">';
        outHtml +='        <div class="c-accordion c-accordion--type3" data-ui-accordion="">';
        outHtml +='          <ul class="c-accordion__box" id="btn_item' + idx + '">';
        outHtml +='          </ul>';
        outHtml +='        </div>';
        outHtml +='      </div>';
        outHtml +='    </div>';
        outHtml += '  </div>';
        outHtml += '</div>';

        $(".tab_view").append(outHtml);
        MCP.addSingleTab('#1depthList', '.c-tabs__button', html, null);
      }
    }
  });

  $('button[id^=mainTab]').removeClass('is-active');
  $('button[id^=mainTab]').removeAttr("aria-hidden");
  $('button[id^=mainTab]').attr('aria-selected',"false");

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

  if(buttonXmlList != null && buttonXmlList != ""){
    for(var i=0; i< buttonXmlList.length; i++){
      outHtml += '<button class="c-tabs__button" id="subTab_' + buttonXmlList[i].rateAdsvcCtgCd + '" role="tab" aria-selected="false" tabindex="0" onclick="subTabClick(\'' + buttonXmlList[i].rateAdsvcCtgCd + '\',\'' + idx + '\')">' + buttonXmlList[i].rateAdsvcCtgNm + '</button>';
    }
  }

  // 보고있던 기존탭 숨기기
  $("#tabD" + $("#tabChk").val() + "panel").hide();

  // 메인탭에 서브 카테고리 화면 생성
  $("#tabDtl"+idx).html(outHtml);
  $("#tabD" + idx + "panel").show();
  $("#tabChk").val(idx);
  $("#upRateAdsvcCtgCd").val(resp.upRateAdsvcCtgCd);

  // 서브 카테고리가 없는 경우 메인카테고리의 제품리스트 조회
  // 서브 카테고리가 있는 경우 첫번째 서브카테고리의 제품리스트 조회
  if(buttonXmlList == null || buttonXmlList == ""){
    subTabClick($("#upRateAdsvcCtgCd").val(), idx);
  }else{
    $("#tabD" + idx + "panel").find("button[id^=subTab_]")[0].click();
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
    html += '  <i class="c-icon c-icon--nodata" aria-hidden="true"></i>';
    html += '  <p class="c-nodata__text">변경 가능한 요금제가 존재하지 않습니다.</p>';
    html += '</div>';
    $("#btn_item" + idx).html(html);
    return false;
  }

  // 리스트 중복 제거
  const arrDup = data;
  const arrUniqueList = arrDup.filter((character, idx, arr) => {
      return arr.findIndex((item) => item.rateAdsvcCtgCd === character.rateAdsvcCtgCd) === idx
  });

  for(var i=0; i < arrUniqueList.length; i++){

    $("#strRateAdsvcCtgCd").val(arrUniqueList[i].rateAdsvcCtgCd);

    html += '<li class="c-accordion__item" id= "forId' + arrUniqueList[i].rateAdsvcCtgCd + '">';
    html += '  <div class="c-accordion__head">';
    html += '    <div class="product__title-wrap">';
    html += '      <strong class="product__title">' + arrUniqueList[i].rateAdsvcCtgNm + '</strong>';
    html += '      <span class="product__sub">' + arrUniqueList[i].rateAdsvcCtgBasDesc + '</span>';
    html += '    </div>';
    html += '    <button class="runtime-data-insert c-accordion__button" id="acc_header_a' + arrUniqueList[i].rateAdsvcCtgCd + '" type="button" aria-expanded="false" aria-controls="acc_content_a' + arrUniqueList[i].rateAdsvcCtgCd + '" onclick="showUsimAccordion(' + arrUniqueList[i].rateAdsvcCtgCd + ', ' + idx + ', this)">';
    html += '      <span class="c-hidden">' + arrUniqueList[i].rateAdsvcCtgNm + ' 정보 펼쳐보기</span>';
    html += '    </button>';
    html += '  </div>';
    html += '  <div class="c-accordion__panel expand" id="acc_content_a' + arrUniqueList[i].rateAdsvcCtgCd + '" aria-labelledby="acc_header_a' + arrUniqueList[i].rateAdsvcCtgCd + '">';
    html += '    <div class="c-accordion__inside">';
    html += '      <div class="c-table c-table--product u-mt--16" id="content_view' + arrUniqueList[i].rateAdsvcCtgCd + '">';
    html += '      </div>';
    html += '    </div>';
    html += '  </div>';
    html += '</li>';
  }

  $("#btn_item"+idx).html(html);
  KTM.initialize();
}

/** 요금제 아코디언 컨텐츠 조회 -> 상세 제품리스트 조회 */
var showUsimAccordion = function(rateAdsvcCtgCd, id){

  // 아코디언 닫히는 경우 ajax 호출하지 않음
  var isOpen = $("#content_view" + rateAdsvcCtgCd).parent().parent().hasClass("expanded");
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
    $("#content_view" + rateAdsvcCtgCd).html('');
    return false;
  }

  html += '<table>'
  html += '  <caption>요금제 선택, 요금제, 기본료, 상세보기로 구성된 변경 가능한 요금제 정보</caption>'
  html += '  <colgroup>';
  html += '    <col style="width: 365px">';
  html += '    <col style="width: 330px">';
  html += '    <col style="width: 90px">';
  html += '    <col>';
  html += '  </colgroup>';
  html += '  <thead>';
  html += '    <th scope="col">요금제</th>';
  html += '    <th scope="col">기본료</th>';
  html += '    <th scope="col">상세보기</th>';
  html += '    <th scope="col">변경하기</th>';
  html += '  </thead>';
  html += '  <tbody class="paymentView">';

  for(var idx=0; idx<data.length; idx++){

      html += '<tr>';

      // 요금제 (column 1)
      html += '<td id="rateAdsvcNm' + data[idx].rateAdsvcGdncSeq + '">' + data[idx].rateAdsvcNm + '</td>';

      // 기본료 (column 2)
      if(data[idx].promotionAmtVatDesc != null && data[idx].promotionAmtVatDesc != "" && data[idx].promotionAmtVatDesc != "0"){
        html += '<td>';
        html += '  <span class="u-td-line-through c-text--fs15">월' + data[idx].mmBasAmtVatDesc + ' 원</span>';
        html += '  <span class="u-co-mint c-text--fs18 u-ml--12 u-fw--medium">';
        html += '    <span class="c-hidden">프로모션 요금</span>월' + data[idx].promotionAmtVatDesc + ' 원';
        html += '  </span>';
        html += '</td>';
      } else {
        html += '<td> 월 ' + data[idx].mmBasAmtVatDesc + '원 </td>';
      }

      // 상세보기 (column 3)
      html+= '<td>';
      html+= '  <button class="c-button c-button--underline c-button--sm u-co-sub-4" onclick="btn_ratePop(\'' + data[idx].rateAdsvcGdncSeq + '\',\'' + data[idx].rateAdsvcCd + '\');"> 보기<span class="c-hidden">요금제 상세보기 팝업</span></button>';
      html+= '</td>';

      // 변경하기 (column 4)
      html+= '<td>';
      html+= '  <button class="c-button c-button--underline c-button--sm u-co-sub-4" onclick="openFarChgModal(\'' + data[idx].rateAdsvcGdncSeq + '\')">변경<span class="c-hidden">요금제 변경하기 팝업</span></button>';
      html+= '  <input id="rateAdsvcCd' + data[idx].rateAdsvcGdncSeq + '" type="hidden"  value=' + data[idx].rateAdsvcCd + '>';
      html+= '  <input id="mmBasAmtVatDesc' + data[idx].rateAdsvcGdncSeq + '" type="hidden"  value=' + data[idx].mmBasAmtVatDesc + '>';
      html+= '  <input id="promotionAmtVatDesc' + data[idx].rateAdsvcGdncSeq + '" type="hidden"  value=' + data[idx].promotionAmtVatDesc + '>';
      html+= '</td>';

      html+= '</tr>';
  }

  html += '  </tbody>';
  html += '</table>';
  $("#content_view" + rateAdsvcCtgCd).html(html);
}

/** 요금제 상세보기 팝업 */
var btn_ratePop = function(rateAdsvcGdncSeq, rateAdsvcCd){
  openPage('pullPopup', '/rate/rateLayer.do?rateAdsvcGdncSeq=' + rateAdsvcGdncSeq + '&rateAdsvcCd=' + rateAdsvcCd,'');
}

/** 요금제 변경 팝업 */
var btn_farChg = function(rateAdsvcGdncSeq, chkRadion){

  if($("#rsrvPrdcChk").val() == "Y" ){
    MCP.alert("등록된 요금제 예약변경 취소 후 요금제 변경이 가능합니다.");
    return;
  }

  if($(".paymentView").length == 0 || !rateAdsvcGdncSeq){
    MCP.alert("가입하실 요금제를 선택해 주세요.");
    return;
  }

  var varData = ajaxCommon.getSerializedData({
    ncn: $("#ctn option:selected").val()
  });

  ajaxCommon.getItem({
     id: 'possibleStateCheckAjax'
    ,cache: false
    ,url: '/personal/possibleStateCheckAjax.do'
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
      baseAmt = $("#mmBasAmtVatDesc" + rateAdsvcGdncSeq).val();
    }

    var parameterData = ajaxCommon.getSerializedData({
       ncn: $("#ctn option:selected").val()
      ,instAmt: $("#instAmt").text()
      ,prvRateGrpNm: $("#prvRateNm").val()
      ,rateNm: $("#rateAdsvcNm" + rateAdsvcGdncSeq).text()
      ,baseAmt: baseAmt
      ,rateAdsvcCd: $("#rateAdsvcCd" + rateAdsvcGdncSeq).val()
      ,nxtRateGdncSeq: rateAdsvcGdncSeq
      ,prvRateGdncSeq: $("#prvRateGdncSeq").val()
      ,isActivatedThisMonth: data.isActivatedThisMonth // 이번달 개통여부
      ,isFarPriceThisMonth: data.isFarPriceThisMonth   // 이번달 요금제 변경여부
      ,chkRadion : chkRadion
    });

    openPage('pullPopupByPost','/personal/regServicePop.do', parameterData);
  });
}

/** 회선 조회 */
var select = function(){

  ajaxCommon.createForm({
     method: "post"
    ,action: "/personal/farPricePlanView.do"
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
function openFarChgModal(seq) {
    pageObj.rateGdncSeq = seq ;
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
          openModal('#farChgOvertModal');
          //openModal('#farChgStartModal');
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
  btn_farChg(pageObj.rateGdncSeq,'S');
});

//두 번째 모달 버튼 이벤트
$('#btnFarChgReserve').off('click').on('click', function() {
  closeModal('#farChgWarningModal');
  btn_farChg(pageObj.rateGdncSeq,'P');
});

$('#btnFarChgImmediate').off('click').on('click', function() {
  closeModal('#farChgWarningModal');
  btn_farChg(pageObj.rateGdncSeq,'S');
});


$('#btnFarOverChgN').on('click', function() {
  closeModal('#farChgOvertModal');
  openModal('#farChgOvertModal2');
});


$('#btnFarOverChgY').on('click', function() {
  closeModal('#farChgOvertModal');
  btn_farChg(pageObj.rateGdncSeq,'P');
});


$('#btnAgree').on('click', function() {
  closeModal('#farChgOvertModal2');
  btn_farChg(pageObj.rateGdncSeq,'S');
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