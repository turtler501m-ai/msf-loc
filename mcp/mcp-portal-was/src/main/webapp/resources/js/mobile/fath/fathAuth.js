
var btn_reg = function(){
  
  $("#btn_regFath").prop("disabled",false);
}

/** 인증 후 확인버튼 */
var btn_regFath = function(){

  var certifyYn = $("#certifyYn").val();
  if(certifyYn != "Y"){
    MCP.alert("인증을 진행해주세요.");
  }

  ajaxCommon.createForm({
     method:"post"
    ,action:"/m/fath/fathSelf.do"
  });

  ajaxCommon.attachHiddenElement("resNo", $("#resNo").val());
  ajaxCommon.attachHiddenElement("operType", $("#operType").val());
  ajaxCommon.formSubmit();
}
