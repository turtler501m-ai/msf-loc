
$(document).ready(function(){

  $(".footer-info").siblings("button").hide();

  // 명세서 정보변경 페이지 이동
  $("#Fbill02").click(function(){

    ajaxCommon.createForm({
       method: "post"
      ,action: "/m/personal/billWayChgView.do"
    });

    ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
    ajaxCommon.formSubmit();
  });

  // 명세서 재발송 신청 팝업
  $(".pop").click(function(){

    var thisMonth = $(this).attr("thisMonth");
    var varData = ajaxCommon.getSerializedData({
       ncn : $("#ctn option:selected").val()
      ,thisMonth : thisMonth
    });

    openPage('pullPopupByPost','/m/personal/billWayReSendPop.do', varData);
  });

}); // end of document.ready -------------------------

/** 회선 조회 */
var select = function(){

  ajaxCommon.createForm({
     method: "post"
    ,action: "/m/personal/billWayReSend.do"
  });

  ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
  ajaxCommon.formSubmit();
}

