var pageObj = {
      niceType:""
    , niceHistSeq:0
    , authObj:{}
    , niceResLogObj:{}
    , startTime:0
    , etcParam: {
         termsParam: "cdGroupId1=FormEtcCla&cdGroupId2=MstoreTermsAgree1"
        ,loginUrl: "/loginForm.do"
        ,mstoreAuthUrl: "/point/mstoreAuth.do"
      }
}

$(document).ready(function() {

    // 모바일, pc 구분값 설정
    if($("#isMobile").val() == 'Y') {
        pageObj.etcParam.termsParam = "cdGroupId1=FormEtcCla&cdGroupId2=MstoreTermsAgree2";
        pageObj.etcParam.loginUrl= "/m/loginForm.do";
        pageObj.etcParam.mstoreAuthUrl = "/m/point/mstoreAuth.do";
    }

    // Mstore 안내 동의 팝업 생성
    if($("#MstoreTermsAgree").length > 0){
        ajaxCommon.getItem({
                id: 'mstoreTermsPop',
                url: "/termsPop.do",
                type: "GET",
                dataType: "json",
                data: pageObj.etcParam.termsParam,
                async: false
            }
            ,function(data){
                var inHtml = unescapeHtml(data.docContent);
                $('#MstoreTermsAgree').html(inHtml);
            });
    }

    // Mstore 바로가기 버튼 클릭
    $(".goMstore").click(function() {
        var landingUrl = $("#landingUrl").val();

        // Mstore 동의 여부 확인
        ajaxCommon.getItem({
                id: 'mstoreTermsAgreeChk'
                ,cache: false
                ,url: '/point/mstoreTermsAgreeChk.do'
                ,dataType: "json"
                ,async: false
            }
            , function (jsonObj) {
                if(jsonObj.RESULT_CODE=='0001'){ //로그인 세션 빠짐 > 로그인 페이지 이동
                    MCP.alert(jsonObj.RESULT_MSG, function (){
                        location.href= pageObj.etcParam.loginUrl;
                    });
                }else if (jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){ // 동의내역 없음

                    // 약관 팝업을 불러온 경우만 약관 동의 팝업 표출
                    if($("#mstoreTermsAgreePop").length == 0) return;

                    var el = document.querySelector('#mstoreTermsAgreePop');
                    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                    modal.open();

                }else{ // 기존 동의 완료
                    window.open(pageObj.etcParam.mstoreAuthUrl + (landingUrl ? `?landingUrl= + ${encodeURIComponent(landingUrl)}` : ""), "_blank" );
                }
            });
    });

    //배너
    document.addEventListener('UILoaded', function() {
       KTM.swiperA11y('#swiperOpenMarket', {
         autoplay: {
           delay: 3000,
           disableOnInteraction: false,
         },
       });
     });


}); // end of $(document).ready(function() {

// 체크박스(개인정보의 제3자 제공 동의) 클릭 이벤트
function mstoreAgreeChk($trg) {
    if($("#"+$trg.id).is(":checked")) $("#mstoreAgreeSend").prop("disabled", false);
    else $("#mstoreAgreeSend").prop("disabled", true);
}

// 동의하고 계속하기 버튼 클릭 이벤트 부여
function mstoreAgreeSend(){
    var landingUrl = $("#landingUrl").val();

    var agreeYn= $("#mstoreAgreeChk").is(':checked') ? "Y" : "N";
    if(agreeYn != "Y"){
        MCP.alert("M마켓 이용 안내에 동의하셔야 합니다.");
        return;
    }

    var varData = ajaxCommon.getSerializedData({agreeYn: agreeYn});

    // 약관 동의 처리
    ajaxCommon.getItem({
            id: 'mstoreTermsAgreeAjax'
            ,cache: false
            ,url: '/point/mstoreTermsAgreeAjax.do'
            ,data : varData
            ,dataType: "json"
            ,async: false
        }
        , function (jsonObj) {

            if(jsonObj.RESULT_CODE=='0001'){ //로그인 세션 빠짐 > 로그인 페이지 이동
                MCP.alert(jsonObj.RESULT_MSG, function (){
                    location.href= pageObj.etcParam.loginUrl;
                });
            }else if (jsonObj.RESULT_CODE != AJAX_RESULT_CODE.SUCCESS){
                MCP.alert(jsonObj.RESULT_MSG);
            }else {
                // 팝업 닫고 Mstore 이동
                KTM.Dialog.closeAll();
                window.open(pageObj.etcParam.mstoreAuthUrl + (landingUrl ? `?landingUrl= + ${encodeURIComponent(landingUrl)}` : ""), "_blank");
            }
        });
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
            MCP.alert("본인인증에 성공 하였습니다.", function (){
                $("#authFrm").parent().hide();
                $(".mstore-button-wrap").removeClass('c-hidden');
                window.scrollTo(0, 0);
            });
        }else {

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

        return null;
    } // end of if------------------------------
}

function fn_go_banner(linkUrl,bannSeq, bannCtg ,tgt){
    insertBannAccess(bannSeq,bannCtg);
    if(tgt.trim() == '_blank'){
        window.open(linkUrl);
    }else{
        window.location.href = linkUrl;
    }
}

