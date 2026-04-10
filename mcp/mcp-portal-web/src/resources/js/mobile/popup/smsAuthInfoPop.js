var interval ;
var clauseScroll;
$(document).ready(function (){


	onLoadSmsEvent();

	// 숫자만 입력가능1
	$(".numOnly").keyup(function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
	});

	// 숫자만 입력가능2
	$(".numOnly").blur(function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
	});

  	$("#regularCertTimeBtn").click(function(){
		$("#timeYn").val('Y');
		$("#timeOverYn").val(''); 
		certifyCallBtn();
		clearInterval(interval);
		dailyMissionTimer(3);
		
	});
	
	$("#phoneNum").keyup(function(e) {

		if($("#phoneNum").val().length != 11){
			$("#phoneChk").show();
			$("#inputPhoneNum").addClass("has-error");
			$("#phoneNum").focus();
		} else{
			$("#phoneChk").hide();
			$("#inputPhoneNum").removeClass("has-error");
		}
    });

	//인증번호 확인
	$("#regularCertBtn").click(function(){
		var certify = $("#certify").val();
		if(certify!="Y"){
			MCP.alert('인증번호를 받으세요.', function (){
				$("#phoneNum" ).focus();
			}); 

			return false;
		}

		if($("#timeOverYn").val() == "Y"){
			MCP.alert("인증번호를 다시 받으세요.",function(){
				$("#phoneNum").focus();
			});
			
			return false;
		}
		
		var certifySms = ajaxCommon.isNullNvl($("#certifySms").val(),"");

		if(certifySms==""){
			MCP.alert('인증번호를 입력해 주세요.', function (){
				$("#certifySms" ).focus();
			}); 
			return false;
		}

		smsAuthCheck();
	});
});

function smsAuthCheck(){
	var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
	var certifySms = ajaxCommon.isNullNvl($("#certifySms").val(),"");
	
	var varData = ajaxCommon.getSerializedData({
		certifySms:$.trim(certifySms)
		,phone:$.trim(phoneNum)
		,menuType :$("#menuType").val()
		,contractNum : $("#svcContractNum").val()
		,svcCntrNo : $("#svcCntrNo").val()
	});
	
	ajaxCommon.getItem({
	    id:'authSmsCheckAjax'
	    ,cache:false
	    ,url:'/m/sms/smsAuthCheckAjax.do'
	    ,data: varData
	    ,dataType:"json"
	}
	,function(data){
		if (AJAX_RESULT_CODE.SUCCESS){
			if( data.RESULT_CODE =="00000" ){
				MCP.alert("인증이 완료되었습니다.");
				$("#certifyYn").val("Y");
				$("#combiChkYn").val("N");
				$("#timeover").hide();
				$("#countdown").hide();
				$("#countdownTime").hide();
				$("#countdown1").hide();
				$("#phoneNum").prop("disabled", true);
				$("#maskedPhoneNum").prop("disabled", true);
				$("#certifySms").prop("disabled", true);
				$("#regularCertBtn").prop("disabled", true);
				$("#certifyCallBtn").prop("disabled", true);
				$("#btn_reg").prop("disabled",false);
				clearInterval(interval);
			} else if( data.RESULT_CODE =="00001" ){
				MCP.alert(data.MSG);
				return false;
			} else if(data.RESULT_CODE =="00002" || data.RESULT_CODE =="00003" || data.RESULT_CODE =="99999" ){
				MCP.alert(data.MSG);
				return false;
			} else if(data.RESULT_CODE =="STEP01"){ // STEP 오류
				MCP.alert(data.MSG);
				return false;
			} else{
				MCP.alert("인증번호 확인에 실패했습니다.");
				return false;
			}

		}else{
			$("#rateSoc").val("");
			$("#rateNm").text("-");
			MCP.alert("인증에 실패했습니다.");
			return false;
		}
	});
}
function onLoadSmsEvent(){

	var phoneNum= $("#phoneNum").val();
	if(phoneNum != null && phoneNum != ""){
		$("#phoneNum").prop("disabled",true);
		$("#maskedPhoneNum").prop("disabled",true);
	}
	
	$("#countdown").hide();
	$("#countdownTime").hide();
	$("#timeover").hide();
}

//인증번호 받기
function certifyCallBtn(){

	
	clearInterval(interval);
	var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");

	if(phoneNum == "" ){
		MCP.alert('핸드폰 번호를 입력해 주세요.', function (){
			$("#phoneChk").show();
			$("#inputPhoneNum").addClass("has-error");
			$("#phoneNum").focus();
		}); 
		
		return true;
	} else {
		$("#phoneChk").hide();
		$("#inputPhoneNum").removeClass("has-error");
	}
	
	
	
	if(phoneNum.length < 11){
		MCP.alert('핸드폰 번호를 정확하게 입력해 주세요.', function (num){
			$("#phoneNum").focus();
		}); 
		
		return true;
	}


	var varData = ajaxCommon.getSerializedData({
		phone:$.trim(phoneNum)
		,contractNum : $("#svcContractNum").val()
		,menuType:$("#menuType").val()
		,timeYn : $("#timeYn").val()

	});

	ajaxCommon.getItem({
	    id:'userSmsAjax'
	    ,cache:false
	    ,url:'/m/sms/smsAuthAjax.do'
	    ,data: varData
	    ,dataType:"json"
	},function(data){
		if (AJAX_RESULT_CODE.SUCCESS) {
			if( data.RESULT_CODE == "00000" ){

				MCP.alert("해당 번호로 인증번호를 발송하였습니다.");
				$("#countdown").show();
				$("#countdownTime").show();
				$("#certifyCallBtn").text("인증번호 재전송");
				dailyMissionTimer(3);
				$("#certify").val("Y");
				$("#svcCntrNo").val(data.svcCntrNo);
   				$("#certifySms").focus();
				$("#timeOverYn").val('');

				if (data.AUTH_NUM != null) {
	                $("#certifySms").val(data.AUTH_NUM );
	             
	            }
			} else if( data.RESULT_CODE == "00001" || data.RESULT_CODE == "00003" || data.RESULT_CODE == "0003" || data.RESULT_CODE == "99999"){
				$("#countdown").hide();
				$("#countdown1").hide();
				if(data.message != null){
					MCP.alert(data.message);
				}else{
					MCP.alert("인증 번호 발송에 실패했습니다.");
				}
				return false;
			} else if( data.RESULT_CODE == "00002" ){
				MCP.alert("kt M모바일 고객인 경우에만 가능합니다.");
				return false;
			}else if( data.RESULT_CODE =="00010" ){
				$("#timeYn").val('N');
			} else if (data.RESULT_CODE == "00004") { // 단시간 다량 메세지 발송시
				MCP.alert(data.message);
			} else if (data.RESULT_CODE == "AUTH01" || data.RESULT_CODE == "STEP01"){ // STEP 오류
				MCP.alert(data.message);
				return false;
			} else {
				MCP.alert("인증 번호 발송에 실패했습니다.");
				return false;
			}
	    } else {
	    	$("#countdown").hide();
			$("#countdown1").hide();
	    	MCP.alert("인증 번호 발송에 실패했습니다.");
			return false;
	    }
	}
  );
}

function dailyMissionTimer(duration) {
    $("#timeover").hide();
    $("#countdown").show();
    $("#countdown1").show();

    var timer = duration  * 60;//받아온 파라미터를 분단위로 계산한다
    var minutes =0;
    var seconds = 0;

    interval = setInterval(function(){
        minutes = parseInt(timer / 60 % 60, 10);
        seconds = parseInt(timer % 60, 10);
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;
        $("#timer").text(minutes+"분 "+seconds+"초");
        if (--timer < 0) {
            timer = 0;
            $("#timer").text("");
            $("#countdown").hide();
            $("#countdown1").hide();
            $("#timeover").show();
            $("#countdownTime").hide();
            $("#timeOverYn").val("Y");
            $("#btn_reg").prop("disabled",true);
            return;
        }
    }, 1000);
}

