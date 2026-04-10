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



    // 숫자만 입력가능1
    $(".numOnly").keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });


    $("#custName").keyup(function(){
        var custSvcNo  = $("#custSvcNo1").val() + $("#custSvcNo2").val()+$("#custSvcNo3").val();
        var custName = $("#custName").val();

        if(custSvcNo.length  == 11  && custName != ""){
            $("#btnOk").removeClass("is-disabled");
        }else{
            $("#btnOk").addClass("is-disabled");
        }
    });



    $(".custSvcNo").keyup(function(){
        var custSvcNo = $("#custSvcNo1").val() + $("#custSvcNo2").val()+$("#custSvcNo3").val();

        var custName = $("#custName").val();
        var custSvcNo1 = $("#custSvcNo1").val().length;
        var custSvcNo2 = $("#custSvcNo2").val().length;

        if(custSvcNo1 == 3){
             $("#custSvcNo2").focus();
        }

        if(custSvcNo2 == 4){
             $("#custSvcNo3").focus();
        }

        if(custSvcNo.length  == 11  && custName != ""){
            $("#btnOk").removeClass("is-disabled");
        }else{
            $("#btnOk").addClass("is-disabled");
        }
    });
});


function btn_search(){

     ajaxCommon.createForm({
        method:"post"
        ,action:"/content/myShareDataView.do"
     });

    ajaxCommon.attachHiddenElement("ncn",$("#ncn option:selected").val());
    ajaxCommon.attachHiddenElement("menuType",$("#menuType").val());
    ajaxCommon.attachHiddenElement("search","Y");
    ajaxCommon.formSubmit();
}


//결합가능여부조회
function btn_ok(){

    var subLinkName = $.trim($("#custName").val());
    var cntrMobileNo = $("#custSvcNo1").val()+ $("#custSvcNo2").val()+$("#custSvcNo3").val();
    if(subLinkName == ""){
        MCP.alert('함께쓰기 회선 고객명을 입력해 주세요.', function (){
            $("#custName" ).focus();
        });

        return;
    }

    if(subLinkName == ""){
        MCP.alert('함께쓰기 회선 고객명을 입력해 주세요.', function (){
            $("#custName" ).focus();
        });

        return false;
    }

    if(cntrMobileNo == ""){
        MCP.alert('함께쓰기 회선 휴대전화을 입력해 주세요.', function (){
            $("#custSvcNo1" ).focus();
        });

        return false;
    }

    if(cntrMobileNo.length < 11){
        MCP.alert('함께쓰기 회선 휴대전화을 확인해 주세요.', function (){
            $("#custSvcNo1" ).focus();
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
        ,url:'/content/preShareDataCheckAjax.do'
        ,data: varData
        ,dataType:"json"
     }
     ,function(jsonObj){
            var resultCode = jsonObj.resultCode;


             if(resultCode == "00"){


                ajaxCommon.createForm({
                        method:"post"
                        ,action:"/content/reqShareDataView.do"
                });

                ajaxCommon.attachHiddenElement("cntrMobileNo",	 cntrMobileNo);
                ajaxCommon.attachHiddenElement("subLinkName",	subLinkName);
                ajaxCommon.attachHiddenElement("retvGubunCd",	$("#retvGubunCd").val());
                ajaxCommon.attachHiddenElement("ncn",	$("#ncn option:selected").val());

                ajaxCommon.formSubmit();

            } else if (resultCode != undefined) {
                MCP.alert(arrMsgObj[resultCode], function(){
                    $("#custName").val("");
                    $(".custSvcNo").val("");
                    $("#btnOk").addClass("is-disabled");

                });
                return false;
            } else {
                MCP.alert("시스템 오류 입니다. 다시 시도하기기 바랍니다. ");
            }
     });
}
//결합내역조회 팝업
function btn_combiPop(){

    var parameterData = ajaxCommon.getSerializedData({
            ncn : $("#ncn option:selected").val()
        ,	retvGubunCd :$("#retvGubunCd").val()
    });

    openPage('pullPopup','/content/myShareDataList.do',parameterData, 1);

}

function btnRegDtl(param){
    openPage('termsInfoPop','/termsPop.do',param);
}

