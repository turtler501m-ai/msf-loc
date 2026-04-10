let sessionExpiredAlertShown = false;  // 세션 만료 알림 표시 여부를 기록하는 변수
let sessionCheckInterval; // setInterval ID 저장
let isSessionCheckRunning = false; // ajax 요청 중복 실행 방지 변수
$(document).ready(function (){

    // 특정 URL이 있을 경우
    const lyTitle = $(".ly-page--title");
	const maskingBtn = 	$("<div class='c-row c-row--lg'><div class='masking-badge-wrap'><div class='masking-badge'><a class='masking-badge__button' href='/mypage/myinfoView.do' title='가려진(*) 정보보기 페이지 바로가기'><i class='masking-badge__icon' aria-hidden='true'></i><p>가려진(*)<br/>정보보기</p></a></div></div></div>");
	const maskingPopBtn = $("<div class='c-row c-row--lg'><div class='masking-badge-wrap'><div class='masking-badge'><button id='osbinfoBtn' class='masking-badge__button' type='button' onclick='obsInfoView();'><i class='masking-badge__icon' aria-hidden='true'></i><p>가려진(*)<br/>정보보기</p></button></div></div></div>");
	const myUrl = window.document.location.href;
	const myPage = myUrl.includes("/mypage");
	const myView = myUrl.includes("/myinfoView");
	const myReview = myUrl.includes("/reView");
	const myKtDcView = myUrl.includes("/ktDcView");
	const myMultiPhone = myUrl.includes("/multiPhoneLine");
	const myCsInquiry = myUrl.includes("/myCsInquiryList");
	const myPromotion = myUrl.includes("/myPromotionList");
	const userSns = myUrl.includes("/userSnsList");

	if(myPage || myKtDcView){
	   $(lyTitle).after(maskingBtn);
	   if(myView){
			$(maskingBtn).remove();
			$(lyTitle).after(maskingPopBtn);
			$(maskingPopBtn).click(function(){
	            $(this).remove();
	        });
		} else if(myReview || myPromotion || userSns){
			$(maskingBtn).remove();
		} else if(myMultiPhone){
			setTimeout(function(){
				const hasNodata = $(".ly-content").find(".c-nodata");
				if (hasNodata.css("display") !== "none") {
					$(maskingBtn).remove();
				}
			},500);
		} else if(myCsInquiry){
			const hasNodata = $(".ly-content").find(".nodata--type2").hasClass("nodata--type2");
			if(hasNodata){
				$(maskingBtn).remove();
			}
		}
	}

	// 마스킹 세션이 있을 경우
	if($("#maskingSession").val() == 'Y'){
		$(maskingBtn).remove();
		$(maskingPopBtn).remove();
	    checkSessionStatus();
	}

	$('.masking-badge').on("click", function(){
        $(this).css("pointer-events", "none");
    });

});


//마스킹해제 세션저장
function unMasking(reqSeq,resSeq) {

    var varData = ajaxCommon.getSerializedData({
        reqSeq:reqSeq
        ,resSeq:resSeq
    });

    ajaxCommon.getItem({
        id:'unMaskingAjax'
        ,cache:false
        ,url:'/unMaskingAjax.do'
        ,data: varData
        ,dataType:"json"
    }
    ,function(data){
        if(data.RESULT_CODE == "0001"){
            MCP.alert(data.RESULT_MSG);
        }else if(data.RESULT_CODE == "0002"){
            MCP.alert(data.RESULT_MSG);
        }else{
            location.reload();
        }
    });
}

//세션 체크 시작 함수
function startSessionCheck(interval) {
    if (sessionCheckInterval) {
        clearInterval(sessionCheckInterval); // 기존 interval 제거
    }
    sessionCheckInterval = setInterval(checkSessionStatus, interval);
}

// 세션 상태를 체크하는 함수
function checkSessionStatus() {
    if (isSessionCheckRunning) return; // 이미 요청 중이면 추가 실행하지 않음
    isSessionCheckRunning = true; // 요청 시작 표시

    $.ajax({
        url: '/mskingCountAjax.do',
        method: 'GET',
        success: function(data) {
            isSessionCheckRunning = false; // 요청 완료 후 표시 해제
            if (data.sessionExpired) {
                if (!sessionExpiredAlertShown) {
                    sessionExpiredAlertShown = true;
                    $('.maskingSessionPop').show();
                }
                clearInterval(sessionCheckInterval); // 세션 만료 시 interval 중단
            } else {
                const timeLeft = data.timeLeft;  // 남은 시간 (밀리초 단위)

                // 남은 시간이 1분 이하일 경우 경고 메시지를 띄움
                //if (timeLeft < 60 * 1000) {
                //   MCP.alert("1분 후 세션이 만료됩니다.");
                //}

                // 다음 주기를 남은 시간에 맞춰 설정 가능
                startSessionCheck(timeLeft - (1 * 1000)); // 남은 시간에 맞춰 주기 설정

            }
        },
        error: function() {
            isSessionCheckRunning = false; // 요청 실패 시 표시 해제
            console.error("세션 상태 확인 중 오류가 발생했습니다.");
            clearInterval(sessionCheckInterval); // 오류 발생 시 interval 중단
        }
    });
}


function maskingOkBtn() {
    location.reload();
}