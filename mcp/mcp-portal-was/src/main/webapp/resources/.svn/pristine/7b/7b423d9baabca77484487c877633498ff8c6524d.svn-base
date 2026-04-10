var passObj = { // pass인증 관련 변수
     timeCnt: 0
    ,timeLeft: 600
    ,passTimerInterval:null
    ,passRequestObject:null
}

$(document).ready(function() {

    // ====================== S: PASS 인증서 관련이벤트 ======================
    // '인증서 사용에 어려움이 있으신가요' 버튼 클릭 이벤트
    $(".auth_faq div.q").on("click", function(){
        $(this).closest(".item").toggleClass("active");
    });

    // input 포커스 효과
    $(".input_row input[type='text'], .input_row input[type='tel'], .input_row select").on("focus", function(){
        $(this).parents(".input_row").addClass("focus");
    });
    $(".input_row input[type='text'], .input_row input[type='tel'], .input_row select").on("focusout", function(){
        $(this).parents(".input_row").removeClass("focus");
    });

    // input error 메세지 제거
    $(".input_row input[type='text'], .input_row input[type='tel'], .input_row select").on("keydown", function(){
        $(this).parents(".input_row").removeClass("error");
    });

    // 인증시간 초과 확인버튼 클릭 이벤트 (팝업닫기)
    $('.layer .layer_btnwrap a').on("click", function(){
        $(this).parents(".layer").hide();
        $("span.deem").hide();
        self.close();
    });

    // 내용보기 클릭 이벤트 (개인정보 제3자 제공동의)
    $("#showJoinAgree").click(function(){
        $("#passLoginWrap").hide();    // 로그인 화면
        $("#passAgreeWrap").show();    // 제공동의내용
        $("#passWaitingWrap").hide();  // 인증요청 후 화면
        $("#passAgree2Wrap").hide();   // 개인정보 수집 및 동의
        $("#passInstallWrap").hide();  // pass 설치 화면
    });

    // 내용보기 클릭 이벤트 (개인정보 수집 및 이용)
    $("#showUseAgree").click(function(){
        $("#passLoginWrap").hide();    // 로그인 화면
        $("#passAgreeWrap").hide();    // 제공동의내용
        $("#passWaitingWrap").hide();  // 인증요청 후 화면
        $("#passAgree2Wrap").show();   // 개인정보 수집 및 동의
        $("#passInstallWrap").hide();  // pass 설치 화면
    });

    // pass 설치하러 가기 클릭 이벤트
    $("#passInstallBtn").click(function(){
        $("#passLoginWrap").hide();    // 로그인 화면
        $("#passAgreeWrap").hide();    // 제공동의내용
        $("#passWaitingWrap").hide();  // 인증요청 후 화면
        $("#passAgree2Wrap").hide();   // 개인정보 수집 및 동의
        $("#passInstallWrap").show();  // pass 설치 화면
    });

    // 제공동의내용, 개인정보 수집 및 이용, pass 설치하러가기 닫기 이벤트
    $(".close_agree").click(function(){
        $("#passLoginWrap").show();    // 로그인 화면
        $("#passAgreeWrap").hide();    // 제공동의내용
        $("#passWaitingWrap").hide();  // 인증요청 후 화면
        $("#passAgree2Wrap").hide();   // 개인정보 수집 및 동의
        $("#passInstallWrap").hide();  // pass 설치 화면
    });

    // 자주묻는 질문 클릭 이벤트
    $("#faq").click(function(){
        window.open('https://www.passauth.co.kr/question', "_blank");
        return;
    });

    // PASS 간편인증 1단계(알림요청)
    $("#passNext").click(function(){

        // 유효성검사
        if(!checkPassValidation()) return;

        // 간편인증 알림 요청
        var varData = ajaxCommon.getSerializedData({
            name: $("#name").val()
            ,ctn: $("#phone_prefix").val()+$("#phone_num").val()
            ,agreeYn: ($("#chk1").is(':checked')) ? 'Y' : 'N'
            ,agreeYn2: ($("#chk2").is(':checked')) ? 'Y' : 'N'
            ,mType: "Mobile"
        });

        ajaxCommon.getItem({
                 id: 'pushPassAlram'
                ,cache: false
                ,url: '/nice/pushPassAlram.do'
                ,data: varData
                ,dataType: "json"
            }
            ,function(jsonObj){

                if(jsonObj.RESULT_CODE == "00000"){

                    passObj.passRequestObject= jsonObj;
                    $("input[name=passAlram]").val("1");

                    // 인증 요청 후 화면 표출
                    $("#passWaitingWrap").show();  // 인증요청 후 화면
                    $("#passLoginWrap").hide();    // 로그인 화면
                    $("#passAgreeWrap").hide();    // 제공동의내용
                    $("#passAgree2Wrap").hide();   // 개인정보 수집 및 동의
                    $("#passInstallWrap").hide();  // pass 설치 화면

                    // slick 중복호출 방지
                    var isSlickWorked= $("input[name=autoSlickAction]").val();
                    if(isSlickWorked != "1"){
                        $("input[name=autoSlickAction]").val("1");
                        $('.step_slide').slick({
                            autoplay: true,
                            autoplaySpeed:5000
                        });
                    }

                    // 타이머 실행
                    $("#remain_time").html(convertSeconds(passObj.timeLeft - passObj.timeCnt));
                    passObj.passTimerInterval = setInterval(passTimer, 1000);

                }else{
                    var alertMsg= "시스템 장애로 인하여 통신이 원활하지 않습니다.";
                    alertMsg= (jsonObj.RESULT_MSG == undefined) ? alertMsg : jsonObj.RESULT_MSG;
                    MCP.alert(alertMsg);
                }
            });
    });

    // PASS 간편인증 2단계(인증확인)
    $("#passConfirm").click(function(){

        // 알람 전송여부 확인
        if($("input[name=passAlram]").val() != "1"){
            MCP.alert("PASS 인증을 위한 요청이 진행중입니다.");
            return;
        }

        // 타이머 일시정지
        passTimerStop();

        // 인증결과 확인
        var varData = ajaxCommon.getSerializedData({
             requestNo: passObj.passRequestObject.requestNo
            ,resUniqId: passObj.passRequestObject.resUniqId
            ,name: $("#name").val()
            ,ctn: $("#phone_prefix").val()+$("#phone_num").val()
        });

        // PASS 간편인증 결과확인
        ajaxCommon.getItem({
                id: 'passCertifyInfo'
                ,cache: false
                ,url: '/nice/passCertifyInfo.do'
                ,data: varData
                ,dataType: "json"
            }
            ,function(jsonObj){

                var alertMsg= "시스템 장애로 인하여 통신이 원활하지 않습니다.";
                alertMsg= (jsonObj.RESULT_MSG == undefined) ? alertMsg : jsonObj.RESULT_MSG;

                if(jsonObj.RESULT_CODE == "00000"){
                    opener.fnNiceCert(jsonObj);
                    self.close();

                }else if(jsonObj.RESULT_CODE == "0006"){ // 인증 미완료
                    passObj.passTimerInterval = setInterval(passTimer, 1000);
                    MCP.alert(alertMsg);

                }else{
                    MCP.alert(alertMsg, function (){self.close();});
                }
            });
    });
    // ====================== E: PASS 인증서 관련이벤트 ======================
})

// ====================== S: PASS 인증서 ======================
// 팝업 닫히기 직전, 타이머 종료
$(window).bind("beforeunload", function (){
    if(passObj.passTimerInterval != null && typeof passObj.passTimerInterval != "undefined"){
        clearInterval(passObj.passTimerInterval);
    }
});

// PASS 인증서 유효성 검사
function checkPassValidation(){

    var name= $("#name").val();
    var phone= $("#phone_num").val();
    var passAgree= $("#chk1").is(':checked');
    var passAgree2= $("#chk2").is(':checked');

    if($.trim(name) == ""){
        $("#name").parents(".input_row").addClass("error");
        $("#name").focus();
        return false;
    }

    if($.trim(phone) == "" || $.trim(phone).length < 7){
        $("#phone_num").parents(".input_row").addClass("error");
        $("#phone_num").focus();
        return false;
    }

    if(!passAgree){
        MCP.alert("개인정보 제 3자 제공에 동의해야 합니다.");
        return false;
    }

    if(!passAgree2){
        MCP.alert("개인정보 수집 이용에 동의해야 합니다.");
        return false;
    }

    return true;
}

// 타이머 유효시간 포맷
function convertSeconds(s){
    var min= Math.floor(s/60);
    var sec= s%60;

    min= ("00" + min).slice(-2);
    sec= ("00" + sec).slice(-2);

    return min + ":" + sec;
}

// PASS 인증 타이머 함수
function passTimer(){

    // 타이머가 작동하지 않고 있는 경우
    if(passObj.passTimerInterval == null || typeof passObj.passTimerInterval == "undefined") return false;

    // 남은 시간 계산
    passObj.timeCnt++;
    if(passObj.timeCnt > passObj.timeLeft){

        clearInterval(passObj.passTimerInterval); // 타이머 종료

        // 기존재 얼럿 비우기
        $(".c-modal__body").remove();
        $(".c-modal").remove();
        $(".fadeIn").remove();

        // 타임아웃 팝업 표출
        $("#passTimeoutlayer").show();
        $(".deem").show();
    }else{
        $("#remain_time").html(convertSeconds(passObj.timeLeft - passObj.timeCnt));
    }
}

// pass 인증 타이머 일시정지
function passTimerStop(){
    if(passObj.timeCnt > passObj.timeLeft) return;
    else clearInterval(passObj.passTimerInterval);
}
// ====================== E: PASS 인증서 ======================