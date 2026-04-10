var pageObj = {
    niceType:""
    , niceHistSeq:0
    , authObj:{}
    , niceResLogObj:{}
    , startTime:0
    , etcParam: {
        McashTermsOfServiceAgree: "cdGroupId1=FormEtcCla&cdGroupId2=McashTermsOfServiceAgree",
        McashPersonalInfoCollectAgree: "cdGroupId1=FormEtcCla&cdGroupId2=McashPersonalInfoCollectAgree",
        McashOthersTrnsAgree: "cdGroupId1=FormEtcCla&cdGroupId2=McashOthersTrnsAgree",
    }
}

$(document).ready(function() {
    $(".mcashAgreePop").each(function(index, item) {
        var agreeDiv = $(item);
        var divId = agreeDiv.attr('id');

        ajaxCommon.getItem({
            id: divId + 'Pop',
            url: '/termsPop.do',
            type: 'GET',
            dataType: 'json',
            data: pageObj.etcParam[divId],
            async: false
        }, function(data) {
            var inHtml = unescapeHtml(data.docContent);
            agreeDiv.html(inHtml);
        });
    })

    // 약관 동의 팝업 체크
    $(".chkAgree").change(function() {
        chkAgree();
        changeSendButtonStatus();
    });

    // 휴대폰 인증 클릭 시 validation check
    simpleAuthvalidation = function() {
        return checkValidationAuth(true);
    }

    // 확인 버튼 클릭
    $("#mcashServiceJoinSend").click(function() {
        if(!checkValidationSend(true))
            return false;

        var varData = ajaxCommon.getSerializedData({
            svcCntrNo: $('input[name=serviceNum]:checked').val()
        });

        ajaxCommon.getItem({
            id: 'joinMcashUser',
            url: '/mcash/joinMcashUserAjax.do',
            dataType: 'json',
            data: varData,
            cache: false,
            async: false
        }, function(jsonObj) {
            if (jsonObj.rsltCd == '200001') {
                MCP.alert("M쇼핑할인 가입이 완료되었습니다.", function() {
                    location.reload();
                });
            } else {
                MCP.alert("M쇼핑할인 가입 중 오류가 발생했습니다.<br>다시 시도해 주세요.");
            }
        });
    });

}); // end of $(document).ready(function() {

// 체크박스(이용약관 및 개인정보 수집/이용에 모두 동의) 클릭 이벤트
function chkAgreeAll($trg) {
    var isCheck = $("#"+$trg.id).is(":checked");

    $(".chkAgree").prop("checked", isCheck);

    chkAgree();
    changeSendButtonStatus();
}

function chkAgree() {
    var checksTrue = $(".chkAgree:checked"); // 각각 체크 확인
    var checks = $(".chkAgree");   // 전체 체크 확인
    var isCheck = true;

    if(checks.length != checksTrue.length)
        isCheck = false;

    $("#chkAgreeAll").prop("checked", isCheck);
}

function changeSendButtonStatus(){
    $("#mcashServiceJoinSend").prop("disabled", !checkValidationSend(false));
}

function checkValidationSend(isAlert) {
    var checksTrue = $(".chkAgree:checked");                    // 이용약관 동의 각각 체크 확인
    var checks = $(".chkAgree");                                // 이용약관 동의 전체 체크 확인
    var cntrChk = $("input[name=serviceNum]").is(":checked");   // 회선 선택 확인
    var benefitChk = $("#benefitAgreeChk").is(":checked");      // 혜택 받기 동의 선택 확인
    var niceAuthChk = $("#isAuth").val() == "1";                // 휴대폰 인증 확인

    // 이용약관 동의 선택 확인
    if ( checks.length > checksTrue.length ) {
        if (isAlert) MCP.alert("이용약관에 모두 동의하셔야 진행이 가능합니다.");
        return false;
    }

    // 회선 선택 확인
    if (!cntrChk) {
        if (isAlert) MCP.alert("서비스 적용할 회선을 선택해 주세요.");
        return false;
    }

    // 혜택 받기 동의 선택 확인
    if (!benefitChk) {
        if (isAlert) MCP.alert("혜택 받기 동의를 선택해 주세요.");
        return false;
    }

    // 휴대폰 인증 확인
    if (!niceAuthChk) {
        if (isAlert) MCP.alert("휴대폰 인증을 완료해 주세요.");
        return false;
    }

    return true;
}

function checkValidationAuth(isAlert) {
    var checksTrue = $(".chkAgree:checked");                    // 이용약관 동의 각각 체크 확인
    var checks = $(".chkAgree");                                // 이용약관 동의 전체 체크 확인
    var cntrChk = $("input[name=serviceNum]").is(":checked");   // 회선 선택 확인
    var benefitChk = $("#benefitAgreeChk").is(":checked");      // 혜택 받기 동의 선택 확인

    // 이용약관 동의 선택 확인
    if ( checks.length > checksTrue.length ) {
        if (isAlert) MCP.alert("이용약관에 모두 동의하셔야 진행이 가능합니다.");
        return false;
    }

    // 회선 선택 확인
    if (!cntrChk) {
        if (isAlert) MCP.alert("서비스 적용할 회선을 선택해 주세요.");
        return false;
    }

    // 혜택 받기 동의 선택 확인
    if (!benefitChk) {
        if (isAlert) MCP.alert("혜택 받기 동의를 선택해 주세요.");
        return false;
    }

    return true;
}

var fnNiceCert = function(prarObj) {
    var strMsg= "고객 정보와 본인인증한 정보가 틀립니다." ;
    pageObj.niceResLogObj = prarObj;

    if (pageObj.niceType == NICE_TYPE.CUST_AUTH) {
        var authInfoJson = {authType: prarObj.authType};
        var isAuthDone = mypageAuthCallback(authInfoJson);

        if (isAuthDone) {
            $("#isAuth").val("1");
            changeSendButtonStatus();

            MCP.alert("본인인증에 성공 하였습니다.");
            return null;
        } else {
            var resultCd= niceAuthObj.resAuthOjb.RESULT_CODE;
            var errorMsg= niceAuthObj.resAuthOjb.RESULT_MSG;

            if (resultCd == "LOGIN") {
                MCP.alert(errorMsg, function () {
                    location.href = pageObj.etcParam.loginUrl;
                });
                return null;
            }

            strMsg= (errorMsg == undefined) ? strMsg : errorMsg;
            MCP.alert(strMsg);
        }
    }
}