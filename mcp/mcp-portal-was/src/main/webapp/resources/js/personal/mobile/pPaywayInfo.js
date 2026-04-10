
$(document).ready(function (){

  $(".footer-info").siblings("button").hide();

  // 제휴카드 혜택 팝업 내 자세히보기 버튼 숨김처리
  $("#modalArs").addClass("payway-info");

  // 납부방법 변경 페이지 이동
  $("#btnMovePage").click(function(){

    ajaxCommon.createForm({
       method: "post"
      ,action: "/m/personal/paywayView.do"
    });

    ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
    ajaxCommon.formSubmit();
  });

}); // end of document.ready --------------------------

window.addEventListener('pageshow', function(event) {
  KTM.LoadingSpinner.hide();
});

/** 회선 조회 */
var select = function(){

  ajaxCommon.createForm({
     method: "post"
    ,action: "/m/personal/chargeView05.do"
  });

  ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
  ajaxCommon.formSubmit();
}

var openVirtualAccountListPop = () => {
  var parameterData = ajaxCommon.getSerializedData({
    ncn : $("#ctn option:selected").val()
  });

  openPage('pullPopupByPost', '/m/personal/virtualAccountListPop.do', parameterData);
}