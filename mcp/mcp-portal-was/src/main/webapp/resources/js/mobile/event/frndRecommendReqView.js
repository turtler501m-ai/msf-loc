/**
 * 
 */

var cnt = 180;
var timer;

var pageObj = {
	eventId: ""
}

$(document).ready(function(){
	
	
	
	var termDat = ajaxCommon.getSerializedData({
			cdGroupId1:'INFO'
			,cdGroupId2: 'FRNDINVTAT'
	    });
	
	ajaxCommon.getItem({
	        id:'frndTerms'
	        ,cache:false
	        ,url:'/termsPop.do'
	        ,data: termDat
	        ,dataType:"json"
	    }
	    ,function(data){
			
			$("#docCont").after(data.docContent);
			
		});
		
		// 공통 onclick 이벤트 로드 전 유효성체크를 위한 변경
		$("#certifyCallBtn").attr("onclick", "sendCertSms()");

	
	// 카카오톡 공유, 나에게 문자발송 버튼 클릭 이벤트
	$("#frndSmsSend , #frndKakaoSend").click(function(){

		var certCheck = $("#chkA1").prop("checked");
		var mstoreTermAgree = $("#chkMstoreAgree").prop("checked"); // 개인정보 제 3자(M스토어) 제공 약관 (2023.08.28 추가)

		if(!certCheck || !mstoreTermAgree){
			MCP.alert("SMS인증 후 가능한 서비스입니다.");
			return false;
		}

		var certifyYn = $("#certifyYn").val();
		if( certifyYn!="Y" ){
			MCP.alert("SMS인증 후 가능한 서비스입니다.");
			return false;
		}

		var userName = ajaxCommon.isNullNvl($("#custNm").val(),"");
		var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
		
		var send = "";
		var id = $(this).attr("id");

		if(id=="frndSmsSend"){
			send = "sms";
		} else if(id=="frndKakaoSend"){
			send = "kakao";
		} else {
			return false;
		}

		var varData = ajaxCommon.getSerializedData({
			name:$.trim(userName)
			,phone:$.trim(phoneNum)
			,send:send
			,mstoreTermAgree: mstoreTermAgree ? "Y" : "N"
	    });

		ajaxCommon.getItem({
	        id:'frndSendAjax'
	        ,cache:false
	        ,url:'/m/event/frndSendAjax.do'
	        ,data: varData
	        ,dataType:"json"
	    }
	    ,function(data){
	    	if (AJAX_RESULT_CODE.SUCCESS){
	    		if( data.RESULT_CODE =="00000" ){
	    			if(id=="frndSmsSend"){
	    				alert("요청하신 번호로 문자가 발송 되었습니다.");
	    				location.reload();
	    			} else if(id="frndKakaoSend"){
	    				sendLink(data.kakaoMsg);
	    				/*fn_hideLoading();*/
	    			} else {
	    				return false;
	    			}
	    		} else if(data.RESULT_CODE =="00001"){
	    			MCP.alert("휴대폰 번호가 다릅니다. 다시 인증 해 주세요.");
	    			return false;
	    		} else if(data.RESULT_CODE =="00002"){
	    			MCP.alert("다시 인증 해 주세요.");
	    			return false;
	    		} else if(data.RESULT_CODE =="00003"){
	    			MCP.alert("친구추천 이벤트는 kt M모바일 고객인 경우에만 참여가 가능합니다.");
	    			return false;
	    		} else if(data.RESULT_CODE =="00004"){
	    			MCP.alert("요청하신 번호는 이벤트 참여가 불가합니다.");
	    			return false;
	    		} else if(data.RESULT_CODE =="00005"){
	    			MCP.alert("추천 아이디 생성에 실패 했습니다.");
	    			return false;
	    		} else if(data.RESULT_CODE =="00009"){
					MCP.alert("개인정보 제 3자 제공에 동의해주세요.");
					return false;
	    		} else if(data.RESULT_CODE =="STEP01" || data.RESULT_CODE =="STEP02"){ // STEP 오류
					MCP.alert(data.RESULT_MSG);
					return false;
	    		} else {
	    			if(id=="frndSmsSend"){
	    				MCP.alert("문자 발송에 실패하였습니다.\n휴대폰 번호를 다시 확인하시거나 잠시 후 다시 시도 해 주시기 바랍니다.");
	    			} else if(id=="frndKakaoSend"){
	    				MCP.alert("카카오톡 링크에 실패하였습니다.\n휴대폰 번호를 다시 확인하시거나 잠시 후 다시 시도 해 주시기 바랍니다.");
	    			}

	    			return false;
	    		}
	    	}else{

	    		if(id=="frndSmsSend"){
    				MCP.alert("문자 발송에 실패하였습니다.\n휴대폰 번호를 다시 확인하시거나 잠시 후 다시 시도 해 주시기 바랍니다.");
    			} else if(id=="frndKakaoSend"){
    				MCP.alert("카카오톡 링크에 실패하였습니다.\n휴대폰 번호를 다시 확인하시거나 잠시 후 다시 시도 해 주시기 바랍니다.");
    			}
	    		return false;
	    	}
	    });

	});

	// kakao
	/*Kakao.init("de6f55cc92785c6c337ebfcf3aba1470");*/
	/*Kakao.init("e302056d1b213cb21f683504be594f25");*/
		
});

window.onload = function(){
	if($("#userDivision").val() == '00'){
		MCP.alert("준회원의 경우 사용불가한 서비스입니다.", function(){
			//location.href = document.referrer;
			history.go(-1);
		});
	}
};
/*
// 인증번호 받기 이벤트
$("#sendSms").on("click", function(){
	
	
	
	if(!$("#chkA1").prop("checked")){
		MCP.alert("개인정보 수집 및 이용에 대한 동의를 확인해 주세요.");
		return false;
	}
	
	if(!$(".c-input[type='text']").val()){
		MCP.alert("고객명을 입력확인해 주세요.");
		return false;
	}
	
	if(!$(".c-input[type='tel']").val()){
		MCP.alert("휴대폰 번호를 입력확인해 주세요.");
		return false;
	}
	
	var param = ajaxCommon.getSerializedData({
		name : $(".c-input[type='text']").val(),
		phone : $(".c-input[type='tel']").val()
	});
	
	ajaxCommon.getItem({
	        id:'userSmsAjax'
	        ,cache:false
	        ,url:'/event/userCertSmsAjax.do'
	        ,data: param
	        ,dataType:"json"
	    }
	    ,function(data){
			if(data.RESULT_CODE == "00000"){
				MCP.alert("해당 번호로 인증번호를 발송하였습니다.");
				$("#sendSms").text("인증번호 재전송");
				$("#smsInfo, #smsTime").prop("hidden", false);
				$("#smsOver").prop("hidden", true);
				clearInterval(timer);
				timer = null;
				cnt = 180;	
				timer = setInterval(function(){
					var min = Math.floor(cnt / 60);
					var sec = cnt % 60;
					if(sec < 10){
						sec = '0' + sec;
					}
					$(".u-co-red").text(min + ":" + sec);
					//$(".c-input[type='text']").val(min + ":" + sec);
					if(cnt == 0){
						$("#smsOver").prop("hidden", false);
						clearInterval(timer);
							
					}
					cnt --;
				}, 1000);
			}else if(data.RESULT_CODE == "00001"){
				MCP.alert("인자값 오류가 발생 하였습니다.");
				return false;
			}else if(data.RESULT_CODE == "00002"){
				MCP.alert("인증 번호 발송에 실패했습니다.");
				return false;
			}else if(data.RESULT_CODE == "00003"){
				MCP.alert("친구추천 이벤트는 kt M모바일 고객인 경우에만<br/> 참여가 가능합니다.");
				return false;
			}else if( data.RESULT_CODE == "00004" ){
    			alert("요청하신 번호는 이벤트 참여가 불가합니다.");
    			return false;
    		}
		});
	
})

// 인즌번호 확인 이벤트
$("#checkSms").on("click", function(){
	
	var param = ajaxCommon.getSerializedData({
		certifySms : $(".c-input[type='number']").val(),
		phone : $(".c-input[type='tel']").val()
	});
	
	ajaxCommon.getItem({
		id:'userSmsCheckAjax'
		,cache:false
		,url:'/event/checkCertSmsAjax.do'
		,data:param
		,dataType:"json"
	}
	,function(data){

		if( data.RESULT_CODE =="00000" ){
			MCP.alert("인증이 완료되었습니다.\n고객님의 ID를 SMS 또는 카카오톡으로 공유하시고 친구 추천 혜택을 받으세요.");
			$("#certifyYn").val("Y");
			//$("#countdown , #timeover").hide();
			$("#smsInfo, #smsTime, #smsOver").prop("hidden", true);
			clearInterval(timer);
		} else if( data.RESULT_CODE =="00001" ){
			MCP.alert("인자값 오류가 발생 하였습니다.");
			return false;
		} else if(data.RESULT_CODE =="00002"){
			MCP.alert(data.MSG);
			return false;
		}
	})
	
})

// 휴대폰 번호 유효성체크
$(".c-input[type='tel']").on("propertychange change keyup paste input", function(){

	var chg = $(".c-input[type='tel']").val().replace(/[^0-9]/g, '');
	$(".c-input[type='tel']").val(chg);
	if($(".c-input[type='tel']").val().length == 10 || $(".c-input[type='tel']").val().length == 11){
		$("#phone-input").removeClass("has-error");
		$("#phone-check-txt").text("");
		$("#sendSms").prop("disabled", false);
	}else{
		$("#phone-input").addClass("has-error");
		$("#phone-check-txt").text("휴대폰 번호가 올바르지 않습니다.");
		$("#sendSms").prop("disabled", true);
	}
	
});
*/

//카카오톡 공유
function sendLink(kakaoMsg) {
	if(!Kakao.isInitialized()){
    	Kakao.init('e302056d1b213cb21f683504be594f25');
    	//Kakao.init("de6f55cc92785c6c337ebfcf3aba1470");
	}


    Kakao.Link.sendCustom({
      templateId: 74824
      ,templateArgs: {
//        title: '친구추가 카카오톡 공유하기',
        description: kakaoMsg
      },
    });
  }

$("#phoneNum").on("change", function(){
	if($("#phoneNum").val().length == 11 ){
		$("#phoneChk").hide();
		$("#inputPhoneNum").removeClass("has-error");
	}
});

// 인증번호 받기 이벤트
function sendCertSms(){


	if($("#chkA1").is(":checked") && $("#chkMstoreAgree").is(":checked")){
		certifyCallBtn();
	}else{

		if(!$("#chkA1").is(":checked")){
			MCP.alert("개인정보 수집 및 이용에 대한 동의를 확인해 주세요.", function(){
				$("#chkA1").focus();
			});
		}else{
			MCP.alert("개인정보 제 3자 제공에 동의해주세요.", function(){
				$("#chkMstoreAgree").focus();
			});
		}

		return false;
	}
}

/**
 * 약관 팝업 클릭 시 eventId 세팅
 */
var fnSetEventId = function(eventId){
	pageObj.eventId = eventId;
};

/**
 * 약관 동의 후 닫기
 */
var targetTermsAgree= function() {
	$('#' + pageObj.eventId).prop('checked', 'checked');
};


//인증번호 확인 이벤트
/*function checkCertSms(){
	var certify = $("#certify").val();
	if(certify!="Y"){
		MCP.alert("인증번호를 받아 주세요.");
		$("#certifyCallBtn").focus();
		return false;
	}

	var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
	var certifySms = ajaxCommon.isNullNvl($("#certifySms").val(),"");
	if(certifySms==""){
		MCP.alert("인증번호를 입력해 주세요");
		return false;
	}

	var varData = ajaxCommon.getSerializedData({
		certifySms:$.trim(certifySms)
		,phone:$.trim(phoneNum)
		,menuType :$("#menuType").val()
		,svcCntrNo : $("#svcCntrNo").val()
		,name:$("#custNm").val()
    });


	ajaxCommon.getItem({
	    id:'checkCertSmsAjax'
	    ,cache:false
	    ,url:'/m/sms/checkCertSmsAjax.do'
	    ,data: varData
	    ,dataType:"json"
	},function(data){

		if(data.returnCode == "00"){
				MCP.alert("인증이 완료되었습니다.\n고객님의 ID를 SMS 또는 카카오톡으로 공유하시고 친구 추천 혜택을 받으세요.");
    			$("#certifyYn").val("Y");
				$("#combiChkYn").val("N");
    			$("#timeover").hide();
				$("#countdown").hide();
				$("#countdownTime").hide();
				$("#countdown1").hide();
    			clearInterval(interval);
			} else{
				alert(data.message);
			}
	}
  );
}*/

$(".c-tabs__button").on("click", function(){
	if($(this).attr("data-tab-header") == '#tabA1-panel'){
		location.href = '/m/event/frndRecommend.do';
	}else{
		location.href = '/m/event/frndRecommendReqView.do';
	}
});
