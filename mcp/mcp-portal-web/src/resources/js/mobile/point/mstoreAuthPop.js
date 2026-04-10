var pageObj = {
      niceType:""
    , niceHistSeq:0
    , authObj:{}
    , niceResLogObj:{}
    , startTime:0
}

//본인인증 콜백
var fnNiceCert = function(prarObj) {

    var strMsg = "고객 정보와 본인인증한 정보가 틀립니다.";
    pageObj.niceResLogObj = prarObj;

    if (pageObj.niceType == NICE_TYPE.CUST_AUTH) {

        var authInfoJson = {authType: prarObj.authType};
        var isAuthDone = mypageAuthCallback(authInfoJson);

        // 본인인증 후처리
        if(isAuthDone){
            $(".c-modal,.is-active").find(".c-icon--close").trigger("click");
            MCP.alert("본인인증에 성공 하였습니다.", function (){
                checkMstoreTermsAgree();
            });
        }else {

            var resultCd= niceAuthObj.resAuthOjb.RESULT_CODE;
            var errorMsg= niceAuthObj.resAuthOjb.RESULT_MSG;

            if (resultCd == "LOGIN") {
                MCP.alert(errorMsg, function () {
                    location.href = "/m/loginForm.do";
                });
                return null;
            }

            strMsg= (errorMsg == undefined) ? strMsg : errorMsg;
            MCP.alert(strMsg);
        }

        return null;
    } // end of if------------------------------
}

function checkMstoreTermsAgree() {
    // Mstore 동의 여부 확인
    ajaxCommon.getItem({
        id: 'mstoreTermsAgreeChk'
        ,cache: false
        ,url: '/point/mstoreTermsAgreeChk.do'
        ,dataType: "json"
        ,async: false
    }, function (jsonObj) {
        if (jsonObj.RESULT_CODE=='0001') { //로그인 세션 빠짐 > 로그인 페이지 이동
            MCP.alert(jsonObj.RESULT_MSG, function (){
                goToLogin();
            });
        } else if (jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS) { // 동의내역 없음
            openMstoreTermsAgreePop();
        } else { // 기존 동의 완료
            openExternalMarket();
        }
    });
}