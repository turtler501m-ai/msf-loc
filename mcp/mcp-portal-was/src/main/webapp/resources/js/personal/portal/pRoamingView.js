
var pageObj = {
  soc : ""
 ,prodHstSeq : ""
}

$(document).ready(function(){

  // 탭 클릭 시 화면세팅
  $('button[id^=tab]').click(function(){
    $('button[id^=tab]').removeClass('is-active');
    $('button[id^=tab]').attr('aria-selected',"false");
    $(this).addClass('is-active');
    $(this).attr('aria-selected',"true");
    var tabId = $(this).attr('id');
    $('.c-tabs__panel').removeClass('u-block');
    $('.c-tabs__panel[aria-labelledby =' + tabId + ']').addClass('u-block');
  });

  // 조회 및 변경 탭 (이용중인 로밍 조회)
  $("#tab1").click(function(){
    addRegSvcSearch();
  });

  // 신청 탭 (신청가능한 로밍 조회)
  $("#tab2").click(function(){
    regSvcSearch();
  });

  // 조회 및 변경 탭 (이용중인 부가서비스 조회)
  $("#generalTab1").click(function(){
    goRegServiceView("1");
  });

  // 신청 탭 (신청가능한 부가서비스 조회)
  $("#generalTab2").click(function(){
    goRegServiceView("2");
  });

  // 진입 시 탭 선택
  $('button[id^=tab]').removeAttr("tabindex");
  $('button[id^=tab]').removeAttr("aria-controls");

  var $initTab = $("#tab" + $("#initTab").val());
  if($initTab.length > 0) $initTab.trigger("click");
  else $('button[id^=tab]').first().trigger("click");

  // 로밍 해지
  $("#btnPrdSvcCan").click(function(){

    if(pageObj.soc == ""){
      MCP.alert("해외 로밍 선택 하시기 바랍니다.");
      return;
    }

    var varData = ajaxCommon.getSerializedData({
       ncn: $("#ctn option:selected").val()
      ,rateAdsvcCd: pageObj.soc
      ,prodHstSeq: pageObj.prodHstSeq
    });

    ajaxCommon.getItem({
       id: 'moscRegSvcCanChgAjax'
      ,cache: false
      ,url: '/personal/moscRegSvcCanChgAjax.do'
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

      // 해지실패
      if(data.RESULT_CODE != "S"){
        var defaultMsg = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.";
        var errMsg = ajaxCommon.isNullNvl(data.RESULT_MSG, defaultMsg);
        MCP.alert(errMsg);
        return false;
      }

      // 해지성공 (이용중인 로밍 재조회)
      MCP.alert("해외 로밍 해지가 완료되었습니다.", function(){
        pageObj.soc = "";
        pageObj.prodHstSeq = "";
        addRegSvcSearch();
      });
    });
  });

  // 부가서비스 상세정보 조회
  $(document).on("click", ".runtime-data-insert", function(){

    var hasClass = $(this).hasClass("is-active");
    var title = $(this).siblings().find(".c-label").text();

    if(hasClass){

      $(this).children().remove();
      $(this).append("<span class='c-hidden'>" + title + " 상세정보 펼쳐보기</span>");

      // 상세정보 조회
      var idx = $(this).closest("li").attr("idx");
      rateCdDtl(idx);

    } else {
      $(this).children().remove();
      $(this).append("<span class='c-hidden'>" + title + " 상세정보 접기</span>");
    }
  });

}); // end of document.ready --------------------

/** 이용중인 로밍 조회 */
var addRegSvcSearch = function(){

  var varData = ajaxCommon.getSerializedData({
    ncn : $("#ctn option:selected").val()
  });

  ajaxCommon.getItem({
     id: 'myRoamJoinListAjax'
    ,cache: false
    ,url: '/personal/myRoamJoinListAjax.do'
    ,data: varData
    ,dataType: "json"
  },function(data){

    var html = '';
    var svcList = data.outList;
    var infoList = data.prodInfoList;

    // 개인화 URL 정보 미존재
    if(data.RESULT_CODE == "0001"){
      MCP.alert(data.RESULT_MSG, function(){
        location.href = "/personal/auth.do";
      });
      return false;
    }

    // 가입된 로밍 미존재 또는 조회오류
    if(svcList == null || svcList == "" || svcList.length <= 0){
      $(".noAddSvcList").show();
      $("#addSvcData").hide();
      return false;
    }

    // 가입된 로밍 화면 생성
    for(var i = 0; i < svcList.length; i++){

      var strtDt = svcList[i].strtDt;
      var endDt = svcList[i].endDttm;
      var dateType = infoList[i].dateType;
      var lineType = infoList[i].lineType;
      var svcName = ajaxCommon.isNullNvl(infoList[i].rateAdsvcNm, svcList[i].socDescription);
      var svcPrice = ajaxCommon.isNullNvl(infoList[i].mmBasAmtVatDesc, svcList[i].socRateVatValue);

      html += '<tr>';
      html += '<td>';
      html += svcName;

      // 가입기간
      if(dateType == "1"){
        html += '<br/>';
        html += '<span class="product__sub u-mt--8 u-mb--0">(' + getRoamingDateFormat(strtDt, "yyyy.mm.dd") + ' ~</span>';
        html += '<span class="product__sub u-mb--0">' + getRoamingDateFormat(endDt, "yyyy.mm.dd") + ')</span>';
      }else if(dateType == "2"){
        html += '<br/>';
        html += '<span class="product__sub u-mt--8 u-mb--0">(' + getRoamingDateFormat(strtDt, "yyyy.mm.dd hh:mi") + ' ~)</span>';
      }else if(dateType == "3" || dateType == "4"){
        html += '<br/>';
        html += '<span class="product__sub u-mt--8 u-mb--0">(' + getRoamingDateFormat(strtDt, "yyyy.mm.dd hh:mi") + ' ~</span>';
        html += '<span class="product__sub u-mb--0">' + getRoamingDateFormat(endDt, "yyyy.mm.dd hh:mi") + ')</span>';
      }

      // 대표 및 서브 회선
      if(lineType == "M"){
        var subCtnList = svcList[i].shareSubCtnList;
        if(subCtnList != null &&  0 < subCtnList.length){
          for(var j = 0; j < subCtnList.length; j++) {
            html += '<span class="product__sub u-mb--0">서브회선' + (j + 1) + ' : ';
            html += subCtnList[j];
            html += '</span>';
          }
        }
      }else if(lineType == "S"){
        html += '<span class="product__sub u-mb--0">대표회선 : ';
        html +=  svcList[i].shareMainCtn
        html += '</span>';
      }

      html += '</td>';

      // 가격
      html += '<td>' + getRoamingPriceFormat(infoList[i].freeYn, svcPrice, dateType, infoList[i].usePrd, svcList[i].socRateValue) +'</td>';

      html += '<td>';
      html += '<div class="c-button-wrap c-button-wrap--underline">';

      if(svcList[i].updateFlag == 'Y' && 0 < infoList[i].rateAdsvcGdncSeq){
        html += '<button class="c-button c-button--underline c-button--sm u-co-sub-4"  type="button" onclick="btn_regChg(\'' + infoList[i].rateAdsvcGdncSeq + '\',\'Y\',\'' + svcList[i].prodHstSeq + '\');">변경<span class="c-hidden">로밍 변경 팝업 열기</span></button>';
      }

      if(svcList[i].onlineCanYn == 'Y'){
        html += '<button class="c-button c-button--underline c-button--sm u-co-sub-4" type="button" onclick="btn_regCancel(\'' + svcList[i].socDescription + '\',\'' + svcList[i].soc + '\',\'' + svcList[i].prodHstSeq + '\');">해지</button>';
        html += '<input type="hidden" value=' + svcList[i].canCmnt + '/>';
      }

      html += '</div>';
      html += '</td>';
      html += '</tr>';
    }

    $("#addSvcList").html(html);
    $(".noAddSvcList").hide();
    $("#addSvcData").show();
  });
}

/** 부가서비스 해지 팝업 */
var btn_regCancel = function(socName, socCode, prodHstSeq){

  if($("#underAge").val() == "true"){
    MCP.alert("미성년자의 경우 고객센터를 통해서만<br>해지가 가능합니다.<br>고객센터(1899-5000)로 연락 부탁 드립니다.");
    return false;
  }

  var innerHtml = socName + " 로밍 서비스를 해지하시겠습니까?";

  // 로밍 상세정보 조회
  ajaxCommon.getItemNoLoading({
     id: 'getRateAdsvcDtlAjax'
    ,cache: false
    ,async: false
    ,url: '/xmlInfo/getRateAdsvcDtlAjax.do'
    ,data: ajaxCommon.getSerializedData({rateAdsvcCd : socCode})
    ,dataType: "json"
  },function(data){

    var gdncDtlList = data.DATA_OBJ;

    // 해지안내문구가 등록되어 있는 경우 해당문구 표출
    if(gdncDtlList != null && 0 < gdncDtlList.length){
      var gdncDtlObj = gdncDtlList.filter(item => item.rateAdsvcItemCd === "ADDSV109");

      if(gdncDtlObj != null && 0 < gdncDtlObj.length){
        innerHtml = ajaxCommon.isNullNvl(gdncDtlObj[0].rateAdsvcItemSbst, innerHtml);
      }
    }
  });

  pageObj.soc = socCode;
  pageObj.prodHstSeq = ajaxCommon.isNullNvl(prodHstSeq, "");

  $("#divRegSvcCan .c-modal__body").html(innerHtml);

  setTimeout(function(){
    var el = document.querySelector('#divRegSvcCan');
    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
    modal.open();
  }, 500);
}

/** 부가서비스 변경 팝업 */
var btn_regChg = function(rateAdsvcGdncSeq, flag, prodHstSeq){

  var varData = ajaxCommon.getSerializedData({
     ncn: $("#ctn option:selected").val()
    ,rateAdsvcGdncSeq: rateAdsvcGdncSeq
    ,flag: flag
    ,prodHstSeq: prodHstSeq
  });

  openPage('pullPopup','/personal/roamingViewPop.do', varData, 1);
}

/** 신청가능한 로밍 리스트 조회 */
var regSvcSearch = function(){

  var varData = ajaxCommon.getSerializedData({
    ncn : $("#ctn option:selected").val()
  });

  ajaxCommon.getItem({
     id:'regRoamListAjax'
    ,cache:false
    ,url:'/personal/regRoamListAjax.do'
    ,data: varData
    ,dataType:"json"
  },function(data){

    var html = "";
    var list = data.list;

    // 개인화 URL 정보 미존재
    if(data.RESULT_CODE == "0001"){
      MCP.alert(data.RESULT_MSG, function(){
        location.href = "/personal/auth.do";
      });
      return false;
    }

    // 로밍 목록 화면 생성
    if(list != null && 0 < list.length){
      for(var i = 0; i < list.length; i++){
        if(list[i].selfYn == 'Y'){

          html += '<li class="c-accordion__item listAccordion" id="listAccordion' + i + '" idx=' + i + '>';
          html += '  <div class="c-accordion__head">';
          html += '    <div class="c-accordion__check">';
          html += '      <input id="rateCd' + i + '" type="hidden" name="rateCd" value=' + list[i].rateAdsvcCd + '>';
          html += '      <input id="rateSeq' + i + '" type="hidden" name="rateSeq" value=' + list[i].rateAdsvcGdncSeq + '>';
          html += '      <input class="c-radio" id="nm' + i + '"  type="radio" name="radServ">';
          html += '      <label class="c-label" for="nm' + i + '">' + list[i].rateAdsvcNm + '</label>';
          html += '    </div>';
          html += '    <button class="runtime-data-insert c-accordion__button" id="acc_headerA' + i + '" type="button" aria-expanded="false" aria-controls="acc_contentA' + i + '">';
          html += '      <span class="c-hidden">' + list[i].rateAdsvcNm + ' 상세정보 펼쳐보기</span>';
          html += '    </button>';

          // 로밍 유형에 따라 가격 조회
          html += '    <span class="c-accordion__amount" id="baseVatAmt_' + i + '">'
          html +=        getRoamingPriceFormat(list[i].freeYn, list[i].mmBasAmtVatDesc, list[i].dateType, list[i].usePrd, null);
          html += '    </span>';

          html += '  </div>';
          html += '  <div class="c-accordion__panel expand" id="acc_contentA' + i + '" aria-labelledby="acc_headerA' + i + '">';
          html += '    <div class="c-accordion__inside" id="rateContents' + i + '"></div>';
          html += '  </div>';
          html += '</li>';
        }
      }

      $(".roamingList").html(html);
      $(".roamingDataYn").show();
      $(".noDataFree").hide();
      $("#btn_regSvc").parent().show();
    }

    if(html == ""){
      $(".roamingList").html('');
      $(".roamingDataYn").hide();
      $(".noDataFree").show();
      $("#btn_regSvc").parent().hide();
    }
  });
}

/** 부가서비스 상세설명 조회 */
var rateCdDtl = function(idx){

  var varData = ajaxCommon.getSerializedData({
    rateAdsvcCd : $("#rateCd" + idx).val()
  });

  ajaxCommon.getItem({
     id: 'getContRateAdditionAjax'
    ,cache: false
    ,url: '/personal/getContRateAdditionAjax.do'
    ,data: varData
    ,dataType: "json"
  },function(data){

    var rateDtl = data.rateDtl;
    var rateDesc = (rateDtl == null) ? "-" : rateDtl.rateAdsvcBasDesc;
    rateDesc = ajaxCommon.isNullNvl(rateDesc, "-");
    $("#rateContents" + idx).html(rateDesc);
  });
};

/** 로밍 가격 포매팅 */
var getRoamingPriceFormat = function(freeYn, price, dateType, usePrd, socRateValue){

  // freeYn: 무료여부
  // price: 가격
  // dateType: 기간입력 유형
  // usePrd: 이용기간

  var priceView = "";
  var usePrdNumber = parseInt(usePrd);
  var priceNumber = parseInt(ajaxCommon.isNullNvl(price, "").replace(/[^0-9]/gi, ""));

  if(freeYn == "Y"){
    priceView = "무료제공";
  }else if(priceNumber != priceNumber){ // 가격이 숫자가 아닌경우 입력값 그대로 표출

    // xml 데이터 미존재 또는 xml 데이터가 숫자가 아닌 경우 > mp데이터 따라감
    // mp 데이터의 가격이 Free인 경우 priceNumber는 NaN > 무료제공으로 대체
    if(socRateValue == "Free") priceView = "무료제공";
    else priceView = ajaxCommon.isNullNvl(price, "-");

  }else if ((dateType == "1" || dateType == "3") && 0 < usePrdNumber){
    priceView = numberWithCommas(priceNumber) + '원 / ' + usePrd + '일';
  }else{
    priceView = numberWithCommas(priceNumber) + '원';
  }

  return priceView;
}

/** 로밍 날짜 포매팅 */
var getRoamingDateFormat = function(dateStr, format){

  if(ajaxCommon.isNullNvl(dateStr, "") == ""){
    return "";
  }

  var year = dateStr.substring(0, 4);
  var month = dateStr.substring(4, 6);
  var day = dateStr.substring(6, 8);
  var hour = dateStr.substring(8, 10);
  var min = dateStr.substring(10, 12);

  format = format.replace("yyyy", year);
  format = format.replace("mm", month);
  format = format.replace("dd", day);
  format = format.replace("hh", hour);
  format = format.replace("mi", min);

  return format;
}

/** 로밍 신청 팝업 */
var btn_regSvc = function(){

  var idx = "";
  var rateAdsvc = "";
  var roamingChk = false;

  // 선택한 로밍 조회
  $(".listAccordion").each(function(){
    idx = $(this).attr("idx");
    roamingChk = $("#nm" + idx).is(':checked');

    if(roamingChk){
      rateAdsvc = $("#rateSeq" + idx).val();
    }
  });

  if(rateAdsvc == ""){
    MCP.alert("가입하실 로밍 서비스를 선택해 주세요.");
    return false;
  }

  if($("#underAge").val() == "true"){
    MCP.alert("미성년자의 경우 고객센터를 통해서만<br>가입이 가능합니다.<br>고객센터(1899-5000)로 연락 부탁 드립니다.");
    return false;
  }

  var varData = ajaxCommon.getSerializedData({
     ncn: $("#ctn option:selected").val()
    ,rateAdsvcGdncSeq: rateAdsvc
  });

  openPage('pullPopup','/personal/roamingViewPop.do', varData, 1);
}

/** 회선 조회 */
var select = function(){
  var tabId = $(".c-tabs__button.is-active").attr("id");
  if(tabId == "tab1") addRegSvcSearch();
  else regSvcSearch();
}

/** 부가서비스 페이지 이동 */
var goRegServiceView = function(tabType){

  ajaxCommon.createForm({
     method: "post"
    ,action: "/personal/regServiceView.do"
  });

  ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
  ajaxCommon.attachHiddenElement("tab", tabType);
  ajaxCommon.formSubmit();
}