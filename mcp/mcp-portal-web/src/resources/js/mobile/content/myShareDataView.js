var arrMsgObj = {
     "00" : "DATA 검증 완료"
    ,"01" : "함께쓰기 결합 대상 요금제가 아닙니다.신청 가능한 요금제는 ‘알려드립니다’를 참고 바랍니다."
    ,"02" : "결합 가능한 회선 수를 초과하였습니다."
    ,"03" : "결합 대상 회선에 3개월 이내 결합이력이 존재하여 결합이 불가합니다."
    ,"04" : "결합 대상 회선이 이미 결합 중 입니다."
    ,"05" : "결합 대상 회선이 kt M모바일 회선이 아닙니다."
    ,"06" : "현재 회선을 사용 중인 고객만 결합이 가능합니다."
    ,"07" : "필수 값이 누락되었습니다."
}
$(document).ready(function (){

    //$(".reg_combiYn").hide();
    //shareDataCheck($("#ncn option:selected").val());

    $("#ncn").on("change",function(){

         ajaxCommon.createForm({
            method:"post"
            ,action:"/m/content/myShareDataView.do"
         });


        ajaxCommon.attachHiddenElement("menuType",$("#menuType").val());
        ajaxCommon.attachHiddenElement("ncn",$("#ncn option:selected").val());
        ajaxCommon.attachHiddenElement("search","Y");
        ajaxCommon.formSubmit();

    });

    // 숫자만 입력가능1
    $(".numOnly").keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });


    $("#custName").keyup(function(){

        var custSvcNo =$("#custSvcNo").val();
        var custName = $("#custName").val();

        if(custSvcNo.length  == 13  && custName != ""){
            $("#btnOk").removeClass("is-disabled");
        }else{
            $("#btnOk").addClass("is-disabled");
        }
    });

    $("#custSvcNo").keyup(function(){
        var custSvcNo =$("#custSvcNo").val();
        var custName = $("#custName").val();

        this.value = autoHypenPhone( this.value ) ;

        if(this.value.length  == 13   && custName != ""){
            $("#btnOk").removeClass("is-disabled");
        }else{
            $("#btnOk").addClass("is-disabled");
        }
    });



    var autoHypenPhone = function(str){
      str = str.replace(/[^0-9]/g, '');
      var tmp = '';
      if( str.length < 4){
          return str;
      }else if(str.length < 7){
          tmp += str.substr(0, 3);
          tmp += '-';
          tmp += str.substr(3);
          return tmp;
      }else if(str.length < 11){
          tmp += str.substr(0, 3);
          tmp += '-';
          tmp += str.substr(3, 3);
          tmp += '-';
          tmp += str.substr(6);
          return tmp;
      }else{
          tmp += str.substr(0, 3);
          tmp += '-';
          tmp += str.substr(3, 4);
          tmp += '-';
          tmp += str.substr(7);
          return tmp;
      }

      return str;
    }
});




//결합가능여부조회
function btn_ok(){

    var subLinkName = $.trim($("#custName").val());
    var cntrMobileNo = $("#custSvcNo").val().replaceAll("-","");
    if(subLinkName == ""){
        MCP.alert('함께쓰기 회선 고객명을 입력해 주세요.', function (){
            $("#custName" ).focus();
        });

        return false;
    }

    if(subLinkName == ""){
        MCP.alert('함께쓰기 회선 고객명을 입력해 주세요.', function (){
            $("#custName" ).focus();
        });

        return false;
    }

    if(cntrMobileNo == ""){
        MCP.alert('함께쓰기 회선 휴대전화을 입력해 주세요.', function (){
            $("#custSvcNo" ).focus();
        });

        return false;
    }

    if(cntrMobileNo.length < 11){
        MCP.alert('함께쓰기 회선 휴대전화을 확인해 주세요.', function (){
            $("#custSvcNo" ).focus();
        });

        return false;
    }


    var varData = ajaxCommon.getSerializedData({
          cntrMobileNo :cntrMobileNo
        , subLinkName: subLinkName
        , retvGubunCd : $("#retvGubunCd").val()
    });



    ajaxCommon.getItem({
        id:'preShareDataCheckAjax'
        ,cache:false
        ,url:'/m/content/preShareDataCheckAjax.do'
        ,data: varData
        ,dataType:"json"
     }
     ,function(jsonObj){


            var resultCode = jsonObj.resultCode;

            if(resultCode == "00"){


                ajaxCommon.createForm({
                        method:"post"
                        ,action:"/m/content/reqShareDataView.do"
                });

                ajaxCommon.attachHiddenElement("cntrMobileNo",	 cntrMobileNo);
                ajaxCommon.attachHiddenElement("subLinkName",	subLinkName);
                ajaxCommon.attachHiddenElement("retvGubunCd",	$("#retvGubunCd").val());
                ajaxCommon.attachHiddenElement("ncn",	$("#ncn option:selected").val());

                ajaxCommon.formSubmit();

            } else if (resultCode != undefined) {
                MCP.alert(arrMsgObj[resultCode], function(){
                    $("#custName").val("");
                    $("#custSvcNo").val("");
                    $("#btnOk").addClass("is-disabled");
                });
            } else {
                MCP.alert("시스템 오류 입니다. 다시 시도하기기 바랍니다. ");
            }
     });
}
//결합내역조회 팝업
function btn_combiPop(){

    var parameterData = ajaxCommon.getSerializedData({
        ncn : $("#ncn option:selected").val()
        ,retvGubunCd : $("#retvGubunCd").val()

    });

    openPage('pullPopupByPost','/m/content/myShareDataList.do',parameterData);

}

function btnRegDtl(param){
    openPage('termsInfoPop','/termsPop.do',param);
}



