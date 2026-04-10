
var pageObj = {
  soc : ""
}

$(document).ready(function(){

  $(".footer-info").siblings("button").hide();

  // 탭 클릭 시 화면세팅
  $('button[id^=tab]').click(function(){
    $('button[id^=tab]').removeClass('is-active');
    $('button[id^=tab]').attr('aria-selected',"false");
    $(this).addClass('is-active');
    $(this).attr('aria-selected',"true");
  });

  // 진입 시 탭 선택
  var $initTab = $("#tab" + $("#initTab").val());
  if($initTab.length > 0) $initTab.trigger("click");
  else $('button[id^=tab]').first().trigger("click");

  // 부가서비스 해지
  $("#btnPrdSvcCan").click(function(){

    if(pageObj.soc == ""){
      MCP.alert("부가서비스 선택 하시기 바랍니다.");
      return;
    }

    var varData = ajaxCommon.getSerializedData({
       ncn: $("#ctn option:selected").val()
      ,rateAdsvcCd: pageObj.soc
    });

    ajaxCommon.getItem({
       id: 'moscRegSvcCanChgAjax'
      ,cache: false
      ,url: '/m/personal/moscRegSvcCanChgAjax.do'
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

      // 해지실패
      if(data.RESULT_CODE != "S"){
        var defaultMsg = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.";
        var errMsg = ajaxCommon.isNullNvl(data.RESULT_MSG, defaultMsg);
        MCP.alert(errMsg);
        return false;
      }

      // 해지성공 (이용중인 부가서비스 재조회)
      MCP.alert("부가서비스 해지가 완료되었습니다.", function(){
        pageObj.soc = "";
        addRegSvcSearch();
      });
    });
  });

}); // end of document.ready --------------------

/** 탭 클릭 이벤트 */
var btn_tab = function(type){
  if(type == "1"){
    addRegSvcSearch(); // 조회 및 변경 탭 (이용중인 부가서비스 조회)
  }else if(type == "2"){
    regSvcSearch(); // 신청 탭 (신청가능한 부가서비스 조회)
  }
}

/** 이용중인 부가서비스 조회 */
var addRegSvcSearch = function(){

  var varData = ajaxCommon.getSerializedData({
     ncn : $("#ctn option:selected").val()
  });

  ajaxCommon.getItem({
     id:'myAddSvcListAjax'
    ,cache:false
    ,url:'/m/personal/myAddSvcListAjax.do'
    ,data: varData
    ,dataType:"json"
  },function(data){

    var html = '';
    var svcList = data.outList;

    // 개인화 URL 정보 미존재
    if(data.RESULT_CODE == "0001"){
      MCP.alert(data.RESULT_MSG, function(){
        location.href = "/m/personal/auth.do";
      });
      return false;
    }

    if(svcList != null && svcList != ""){
      svcList = svcList.filter(item => (0 <= item.socRateVat));
    }

    // 가입된 부가서비스 미존재 또는 조회오류
    if(svcList == null || svcList.length <= 0){
      $(".regSvcYn").show();
      $(".regDataYn").hide();
      return false;
    }

    // 가입된 부가서비스 화면 생성
    for(var i=0; i < svcList.length; i++){

      html += '<tr>';
      html += '<td class="u-ta-center">'+svcList[i].socDescription+'</td>';

      if(svcList[i].socRateValue == 'Free'){
        html += '<td class="u-ta-center">무료</td>';
      }else if(svcList[i].soc == 'PL2078760'){
        html += '<td class="u-ta-center">' + numberWithCommas(svcList[i].socRateVatValue) + ' 원 / 1일</td>';
      } else {
        html += '<td class="u-ta-center">월 ' + numberWithCommas(svcList[i].socRateVatValue) +' 원</td>';
      }

      html += '<td class="u-ta-center">';

      if(svcList[i].onlineCanYn == 'Y'){
        html += '<div>';
        html += '<a class="c-button c-button--underline c-button--sm u-co-mint" href="javascript:void(0);" onclick="btn_regCancel(\'' + svcList[i].socDescription + '\',\'' + svcList[i].soc + '\');">해지</a>';
        html += '<input type="hidden" value=' + svcList[i].canCmnt + '/>';
        html += '</div>';
      }

      html += '</td>';
      html += '</tr>';
    }

    $("#addSvcList").html(html);
    $(".regSvcYn").hide();
    $(".regDataYn").show();
  });
}

/** 부가서비스 해지 팝업 */
var btn_regCancel = function(socName, socCode){

  if($("#underAge").val() == "true"){
    MCP.alert("미성년자의 경우 고객센터를 통해서만<br>해지가 가능합니다.<br>고객센터(1899-5000)로 연락 부탁 드립니다.");
    return false;
  }

  var innerHtml = socName + " 부가서비스를 해지하시겠습니까?";

  // 부가서비스 상세정보 조회
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
  $("#divRegSvcCan .c-modal__body").html(innerHtml);

  setTimeout(function(){
    var el = document.querySelector('#divRegSvcCan');
    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
    modal.open();
  }, 500);
}

/** 신청가능한 부가서비스 목록 조회 */
var regSvcSearch = function(){

  var varData = ajaxCommon.getSerializedData({
     ncn : $("#ctn option:selected").val()
  });

  ajaxCommon.getItem({
     id:'addSvcListAjax'
    ,cache:false
    ,url:'/m/personal/addSvcListAjax.do'
    ,data: varData
    ,dataType:"json"
  },function(data){

    var freeData = data.listC;    // 무료 부가서비스
    var noFreeData = data.listA;  // 유료 부가서비스
    var freeHtml = "";
    var noFreeHtml = "";

    // 개인화 URL 정보 미존재
    if(data.RESULT_CODE == "0001"){
      MCP.alert(data.RESULT_MSG, function(){
        location.href = "/m/personal/auth.do";
      });
      return false;
    }

    // 무료부가서비스 화면 생성
    if(freeData != null && 0 < freeData.length){
      for(var i=0; i<freeData.length; i++){
        if(freeData[i].useYn != 'Y'){ // 가입중인 부가서비는 제외
          freeHtml += '<li class="c-accordion__item freeAccordion" id="freeAccordion' + i + '" freeIdx=' + i + '>';
          freeHtml += '	 <div class="c-accordion__head">';
          freeHtml += '	   <div class="c-accordion__check">';
          freeHtml += '			 <input id="rateCdFr' + i + '" type="hidden" name="rateCd" value=' + freeData[i].rateCd + '>';
          freeHtml += '			 <input class="c-radio" id="nm' + i + '" type="radio" name="chkAS">';
          freeHtml += '			 <label class="c-label" for="nm' + i + '">' + freeData[i].rateNm + '</label>';
          freeHtml += '		 </div>';
          freeHtml += '		 <button class="c-accordion__button u-ta-right" type="button" href="javascript:void(0);" onclick="rateCdDtl(1,\'' + i + '\');" aria-expanded="false" data-acc-header="#acc_contentA' + i + '" >';
          freeHtml += '			 <div class="c-accordion__trigger"></div>';
          freeHtml += '		 </button>';
          freeHtml += '	 </div>';
          freeHtml += '	 <div class="c-accordion__panel expand c-expand" id="acc_contentA' + i + '">';
          freeHtml += '		 <div class="c-accordion__inside">';
          freeHtml += '		   <div class="c-text c-text--type3" id="freeRateContents' + i + '"></div>';
          freeHtml += '	   </div>';
          freeHtml += '  </div>';
          freeHtml += '</li>';
        }
      }

      $(".freeDataList").html(freeHtml);
      $(".freeDataYn").show();
      $(".noDataFree").hide();
    }

    if(freeHtml == ""){
      $(".freeDataList").html('');
      $(".freeDataYn").hide();
      $(".noDataFree").show();
    }

    // 유료부가서비스
    if(noFreeData != null && 0 < noFreeData.length){
      for(var idx=0; idx<noFreeData.length; idx++){
        if(noFreeData[idx].useYn != 'Y'){ // 가입중인 부가서비는 제외
          noFreeHtml += '<li class="c-accordion__item noFreeAccordion" id="noFreeAaccordion' + idx + '" nofreeIdx=' + idx + '>';
          noFreeHtml += '	 <div class="c-accordion__head">';
          noFreeHtml += '		 <div class="c-accordion__check">';
          noFreeHtml += '			 <input id="rateCdNo' + idx + '" type="hidden" name="rateCd" value=' + noFreeData[idx].rateCd + '>';
          noFreeHtml += '		 	 <input class="c-radio" id="noFree' + idx + '" type="radio" name="chkAS">';
          noFreeHtml += '			 <label class="c-label" for="noFree' + idx + '">' + noFreeData[idx].rateNm + '</label>';
          noFreeHtml += '		 </div>';
          noFreeHtml += '		 <button class="c-accordion__button u-ta-right" type="button" href="javascript:void(0);" onclick="rateCdDtl(2,\'' + idx + '\');" aria-expanded="false" data-acc-header="#acc_contentB' + idx + '" >';
          noFreeHtml += '			 <div class="c-accordion__trigger">';
          noFreeHtml += '				 <span class="c-text c-text--type4" id="baseVatAmt_' + idx + '">' + numberWithCommas(noFreeData[idx].baseVatAmt) + '원</span>';
          noFreeHtml += '			 </div>';
          noFreeHtml += '		 </button>';
          noFreeHtml += '	 </div>';
          noFreeHtml += '	 <div class="c-accordion__panel expand c-expand" id="acc_contentB' + idx + '">';
          noFreeHtml += '		 <div class="c-accordion__inside">';
          noFreeHtml += '			 <div class="c-text c-text--type3" id="nofreeRateContents' + idx + '"></div>';
          noFreeHtml += '	 	 </div>';
          noFreeHtml += '	 </div>';
          noFreeHtml += '</li>';
        }
      }

      $(".noFreeDataList").html(noFreeHtml);
      $(".noFreeDataYn").show();
      $(".noDataFreeView").hide();
    }

    if(noFreeHtml == ""){
      $(".noFreeDataList").html('');
      $(".noFreeDataYn").hide();
      $(".noDataFreeView").show();
    }

    // 무료 및 유료 가입가능한 부가서비스가 없는 경우
    if(freeHtml == "" && noFreeHtml == ""){
      $("#btn_regSvc").parent().hide();
    }else{
      $("#btn_regSvc").parent().show();
    }
  });

  //통화중대기 설정/해제(무료) 아코디언 내용
  var rateAdData = ajaxCommon.getSerializedData({
      rateAdsvcGdncSeq : '179',
      btnYn : 'Y'
  });

  ajaxCommon.getItem({
          id:'adsvcContentAjax'
          ,cache:false
          ,url:'/m/rate/adsvcContent.do'
          ,data: rateAdData
          ,dataType:"html"
      }
      ,function(jsonObj){
          $('#callWaiting').html(jsonObj)
      });

}

/** 부가서비스 상세설명 조회 */
var rateCdDtl = function(type, idx){

  var rateAdsvcCd = (type == 1) ? $("#rateCdFr" + idx).val() : $("#rateCdNo" + idx).val();

  var varData = ajaxCommon.getSerializedData({
    rateAdsvcCd : rateAdsvcCd
  });

  ajaxCommon.getItem({
     id: 'getContRateAdditionAjax'
    ,cache: false
    ,url: '/m/personal/getContRateAdditionAjax.do'
    ,data: varData
    ,dataType: "json"
  },function(data){

    var rateDtl = data.rateDtl;
    var rateDesc = (rateDtl == null) ? "-" : rateDtl.rateAdsvcBasDesc;
    rateDesc = ajaxCommon.isNullNvl(rateDesc, "-");

    if(type == 1){
      $("#freeRateContents" + idx).html(rateDesc);
    }else{
      $("#nofreeRateContents" + idx).html(rateDesc);
    }
  });
};

/** 부가서비스 신청 팝업 */
var btn_regSvc = function(){

  var rateAdsvcFree = "";
  var rateAdsvcNoFree = "";
  var rateAdsvcCd = "";
  var rateAdsvcNm = "";
  var baseVatAmt = "";
  var freeChk = "";
  var nofreeChk = "";

  // 선택한 부가서비스 조회
  $(".freeAccordion").each(function(){
    var fIdx = $(this).attr("freeIdx");
    freeChk = $("#nm" + fIdx).is(':checked');

    if(freeChk){
      rateAdsvcFree = $("#rateCdFr" + fIdx).val();
      rateAdsvcNm =  $("label[for='nm" + fIdx + "']").text();
    }
  });

  $(".noFreeAccordion").each(function(){
    var nIdx = $(this).attr("nofreeIdx");
    nofreeChk =$("#noFree" + nIdx).is(':checked');

    if(nofreeChk){
      rateAdsvcNoFree = $("#rateCdNo" + nIdx).val();
      baseVatAmt = $("#baseVatAmt_" + nIdx).text();
      rateAdsvcNm = $("label[for='noFree" + nIdx + "']").text();
    }
  });

  if(rateAdsvcFree == "" && rateAdsvcNoFree == ""){
    MCP.alert("가입하실 부가서비스를 선택해 주세요.");
    return false;
  }

  if($("#underAge").val() == "true"){
    MCP.alert("미성년자의 경우 고객센터를 통해서만<br>가입이 가능합니다.<br>고객센터(1899-5000)로 연락 부탁 드립니다.");
    return false;
  }

  if(rateAdsvcFree != ""){
    rateAdsvcCd = rateAdsvcFree;
    rateAdsvcNm = rateAdsvcNm;
  }

  if(rateAdsvcNoFree != ""){
    rateAdsvcCd = rateAdsvcNoFree;
    baseVatAmt = baseVatAmt;
    rateAdsvcNm = rateAdsvcNm;
  }

  var varData = ajaxCommon.getSerializedData({
     ncn: $("#ctn option:selected").val()
    ,rateAdsvcCd: rateAdsvcCd
    ,rateAdsvcNm: rateAdsvcNm
    ,baseVatAmt: baseVatAmt
  });

  openPage('pullPopupByPost','/m/personal/addSvcViewPop.do', varData);
}

/** 회선 조회 */
var select = function(){
  var tabId = $(".c-tabs__button.is-active").attr("id");
  if(tabId == "tab1") addRegSvcSearch();
  else regSvcSearch();
}

/** 로밍 페이지로 이동 */
var goRoamingView = function(tabType){

  ajaxCommon.createForm({
     method: "post"
    ,action: "/m/personal/roamingView.do"
  });

  ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
  ajaxCommon.attachHiddenElement("tab", tabType);
  ajaxCommon.formSubmit();
}

//통화중대기 설정/해제(무료)
const $checkbox = $("#callWaiting0");
const $modal = $("#regServiceModal");

$checkbox.on("click", function() {
      if ($(this).is(":checked")) {
          var el = document.querySelector('#regServiceModal');
          var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
          modal.open();
      }
    });