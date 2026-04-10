
$(document).ready(function(){

  // 명세서 발송 유형 변경
  $("#billTypeCd").change(function(){

    var billTypeCd = $(this).val();

    if(billTypeCd == "1"){
      $(".eArea").show();
      $(".moArea").hide();
    }else{
      $(".eArea").hide();
      $(".moArea").show();
    }
  });

  // 명세서 발송 신청
  $("#applyBtn").click(function(){

    var billTypeCd = $("#billTypeCd option:selected").val();
    var email = ajaxCommon.isNullNvl($("#email").val(),"");

    // 이메일 값 검사
    if(billTypeCd == "1"){

      if(email == ""){
        MCP.alert("발송받으실 이메일을 넣어주세요.");
        return false;
      }

      var regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
      if(!regEmail.test(email)){
        MCP.alert("이메일주소를 이메일형식에 맞게 입력해 주세요.");
        return false;
      }
    }

    var varData = ajaxCommon.getSerializedData({
       ncn : $("#selNcn").val()
      ,thisMonth : $("#thisMonth").val()
      ,billTypeCd : billTypeCd
      ,email : email
    });

    ajaxCommon.getItem({
       id: 'billWayReSendAjax'
      ,cache: false
      ,url: '/personal/billWayReSendAjax.do'
      ,data: varData
      ,dataType: "json"
    },function(data){

      if(data.RESULT_CODE == "S"){
        MCP.alert("명세서 재발송 신청이 완료되었습니다.",function(){
          KTM.Dialog.closeAll();
        });
      }else if(data.RESULT_CODE == "0001"){
        // 개인화 URL 정보 미존재
        MCP.alert(data.RESULT_MSG, function(){
          location.href = "/personal/auth.do";
        });
      }else{
        MCP.alert("명세서 재발송 신청이 실패하였습니다.<br>잠시 후 다시 시도하시거나<br>고객센터(1899-5000)으로 문의 부탁드립니다.",function(){
          KTM.Dialog.closeAll();
        });
      }
    });
  });

}); // end of document.ready -------------------------