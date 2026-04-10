
$(document).ready(function (){

  $(".footer-info").siblings("button").hide();

  // 조회 날짜 변경
  $("#datalist").change(function(){

    ajaxCommon.createForm({
       method:"post"
      ,action:"/m/personal/callView01.do"
    });

    ajaxCommon.attachHiddenElement("useMonth", $("#datalist option:selected").val());
    ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
    ajaxCommon.formSubmit();
  });

}); // end of document.ready --------------------

/** 회선 조회 */
var select = function(){

  ajaxCommon.createForm({
     method: "post"
    ,action: "/m/personal/callView01.do"
  });

  ajaxCommon.attachHiddenElement("ncn", $("#ctn option:selected").val());
  ajaxCommon.formSubmit();
}
