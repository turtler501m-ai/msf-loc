var rtnObj = {
      reqSeq:""
    , resSeq:""
    , authType:""
    , hasSuccess:""
    , niceType:""
}

$(document).ready(function () {

    $("#kakaoCancle").on("click", function () {
        pop_kakaoCancle();
    })

    $("#kakaoClose").on("click", function () {
        pop_kakaoCancle();
    })

    $("#kakaoAuthAllCheck").on("click", function () {
        allAgree();
    })

});

var kakaoValidation = function () {

    if ($('#custNm').val() == '' || $('#custNm').val() == undefined) {
        MCP.alert("고객명을 확인해 주세요.");
        return true;
    }

    var cstmrType = $("input:radio[name=cstmrType]:checked",opener.document).val();
    //eSIM wacth 검증하지 않음
    if($("#cstmrNativeRrn02",opener.document).length > 0){
        if ('NM' == cstmrType) {
            var parentCstmNm = opener.document.getElementById('minorAgentName').value;
            if ($('#custNm').val() != parentCstmNm) {
                MCP.alert("고객명을 확인해 주세요.");
                return true;
            }
            var parentCstm = opener.document.getElementById('minorAgentRrn01').value;
            if ($('#birthday').val().substr(2) != parentCstm) {
                MCP.alert("생년월일을 확인해 주세요.");
                return true;
            }
        } else {
            var parentCstmNm = opener.document.getElementById('cstmrName').value;
            if ($('#custNm').val() != parentCstmNm) {
                MCP.alert("고객명을 확인해 주세요.");
                return true;
            }
            var parentCstm = opener.document.getElementById('cstmrNativeRrn01').value;
            if ($('#birthday').val().substr(2) != parentCstm) {
                MCP.alert("생년월일을 확인해 주세요.");
                return true;
            }
        }
    }

    if ($('#birthday').val().length < 8) {
        MCP.alert("생년월일을 확인해 주세요.");
        return true;
    }

    if ($('#inpPhone').val().length < 11) {
        MCP.alert("휴대폰 번호를 확인해 주세요.");
        return true;
    }

    if ( !$('#clausePriCollectFlag').is(':checked')) {
        MCP.alert("이용약관에 모두 동의하셔야 진행이 가능합니다.")
        return true;
    }

    if ( !$('#clauseEssCollectFlag').is(':checked')) {
        MCP.alert("이용약관에 모두 동의하셔야 진행이 가능합니다.")
        return true;
    }

    return false;
}

$(document).on('click', '#kakaoAuth', function() {

    if (kakaoValidation()) return;

    var varData = ajaxCommon.getSerializedData({
        phone_No: $('#inpPhone').val(),
        name : $('#custNm').val(),
        birthday : $('#birthday').val()
    });
    ajaxCommon.getItemNoLoading({
        id:'kakaoCert'
        ,cache:false
        ,url:'/kakaoCert.do'
        ,data: varData
        ,dataType: 'json'
        ,async:true
    },function(dataObejct){
        if (dataObejct != undefined) {
            if ("Y" == dataObejct.kakaoAuth.result) {
                $('#txId').val(dataObejct.kakaoAuth.txId);
                $('#kakaoInfoDev').css("display", "none");
                $('#KaAuthBtn').css("display", "none");
                $('#comfirmDiv').css("display", "");

            } else if ("9999" == dataObejct.RESULT_CODE) {
                MCP.alert("본인 확인에 실패하였습니다.<br/>입력 정보를 다시 확인하시기 바랍니다.");

            } else {
                MCP.alert("본인 확인에 실패하였습니다.<br/>다시 확인하시기 바랍니다.");
            }
        }
    });
});

$(document).on('click', '#kakaoComfirm', function() {
    var varData = ajaxCommon.getSerializedData({
        txId: $('#txId').val(),
        phone_No: $('#inpPhone').val(),
        name : $('#custNm').val(),
        birthday : $('#birthday').val()
    });
    ajaxCommon.getItemNoLoading({
        id:'kakaoVerify'
        ,cache:false
        ,url:'/kakaoCertVerify.do'
        ,data: varData
        ,dataType: 'json'
        ,async:true
    },function(dataObejct){
        if (dataObejct != undefined) {
            if ("COMPLETED" == dataObejct.RESULT_STATUS) {
                // MCP.alert("인증이 성공적으로 완료 되었습니다.");
                parentComfirm(dataObejct.niceLog);

            } else if ("EXPIRED" == dataObejct.RESULT_STATUS) {
                MCP.alert(dataObejct.RESULT_MSG, function (){
                    $("#kakaoCancle").trigger("click");
                });

            } else {
                MCP.alert(dataObejct.RESULT_MSG);
            }
        }
    });
});

var pop_kakaoCancle = function () {
    $('#custNm').val('');
    $('#inpPhone').val('');
    $('#birthday').val('');
    $('#kakaoInfoDev').css("display", "");
    $('#KaAuthBtn').css("display", "");
    $('#comfirmDiv').css("display", "none");
    window.close();
}

var parentComfirm = function (niceLog) {

    rtnObj.authType = niceLog.authType;
    rtnObj.niceType = niceLog.paramR3;
    rtnObj.reqSeq = niceLog.reqSeq;
    rtnObj.resSeq = niceLog.resSeq;

    if(opener != null) {
        var resultMsg = opener.fnNiceCert(rtnObj);
        if (resultMsg == "1") {
            alert("인증이 성공적으로 완료 되었습니다.");
            self.close();
        } else {
            if (resultMsg != null) {
                alert(resultMsg);
            }
            self.close();
        }
    } else {
        parent.fnNiceCert(rtnObj);
    }
}

var allAgree = function () {
    $('#clausePriCollectFlag').prop('checked', $('#kakaoAuthAllCheck').is(':checked'));
    $('#clauseEssCollectFlag').prop('checked', $('#kakaoAuthAllCheck').is(':checked'));
}

var kakaoAgreeCheck = function (agreeId) {
    $('#'+agreeId).prop('checked',true);
}
