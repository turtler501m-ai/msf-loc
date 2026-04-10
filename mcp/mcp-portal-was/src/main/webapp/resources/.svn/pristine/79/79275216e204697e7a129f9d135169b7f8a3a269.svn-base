var arrMsgSmsObj = {
     "01":"서비스 계약조건 처리 불가"
    ,"02":"요금제조건 처리 불가"
    ,"03":"이미 결합 중이거나 결합 제약 기간 내 결합이력 존재하여 처리 불가"
    ,"99":"SMS인증 호출이 실패 하였습니다. \n잠시후 다시 실행 하시기 바랍니다."
    ,"STEP01":"인증 정보가 일치하지 않습니다. 이 메시지가 계속되면 처음부터 다시 시도해 주세요."
 }

$(document).ready(function (){

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
		clearInterval(interval);
		dailyMissionTimer(10);	
	});

});

//otp 인증번호받기
function fn_btnReqAutNo(){
	
	var varData = ajaxCommon.getSerializedData({
              ncn: $("#ncn").val()
	        , svcNo: $("#reqSvcNo").val()
	        , retvGubunCd : $("#retvGubunCd").val()
	    });

        ajaxCommon.getItem({
            id:'btnReqAutNo'
            ,cache:false
            ,url:'/m/content/callOtpSvcAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
				$("#countdown").show();
				$("#countdownTime").show();
				$("#timeOverYn").val('');
				$("#certify").val("Y");

                dailyMissionTimer(10);
            } else if (jsonObj.RESULT_CODE != undefined) {
                MCP.alert(arrMsgSmsObj[jsonObj.RESULT_CODE]);
                $("#btnReqAutNo").show();
            } else {
                MCP.alert("시스템 오류 입니다. 다시 시도하기기 바랍니다. ");
                $("#btnReqAutNo").show();
            }
        });
}


//확인 
function fn_btnAutNoCheck(){
	
	if($("#certify").val() != 'Y'){
		MCP.alert('인증번호를  요청하세요.', function (){
			$("#authValue" ).focus();
		}); 

		return false;
	}
	
	if($("#authValue").val() == ""){
		MCP.alert('인증번호를 입력 하시기 바랍니다.', function (){
			$("#authValue" ).focus();
		}); 

		return false;
	}
	
	if($("#timeOverYn").val() == "Y"){
		MCP.alert("유효시간이 지났습니다. \n 인증번호를 재전송 후 다시 인증해주세요.",function(){
			$("#phoneNum").focus();
		});
		
		return false;
	}


    var varData = ajaxCommon.getSerializedData({
         ncn: $("#ncn").val()
        ,otpNo:$("#authValue").val()
        ,cntrMobileNo:$("#reqSvcNo").val()
        ,retvGubunCd : $("#retvGubunCd").val()
    });

    ajaxCommon.getItem({
        id:'btnReqAutNo'
        ,cache:false
        ,url:'/m/content/insertShareDataRequestAjax.do'
        ,data: varData
        ,dataType:"json"
    }
    ,function(jsonObj){
		
	
    	var resultCode = jsonObj.RESULT_CODE;
        if (resultCode== "00000") {
		
			
            ajaxCommon.createForm({
                method:"post"
                ,action:"/m/content/reShareDataComplete.do"
             });
            ajaxCommon.attachHiddenElement("ncn",$("#ncn").val());
            ajaxCommon.attachHiddenElement("reqCustName",$.trim($("#reqCustName").val()));
            ajaxCommon.attachHiddenElement("reqSvcNo",$("#reqSvcNo").val());
            ajaxCommon.formSubmit();
        } else if ("01" == resultCode) {

            $('#btnAutNoCheck').show();
            MCP.alert("인증번호를 요청 정보가 없습니다. \n다시 요청 하시기 바랍니다.");
        } else if ("02" == resultCode) {

            $('#btnAutNoCheck').show();
            MCP.alert("인증번호가 상이 합니다.\n인증번호 다시 입력 하시기 바랍니다.");
        } else if ("03" == resultCode) {

            $('#btnAutNoCheck').show();
            MCP.alert("결합 대상 회선이 kt M모바일 회선이 아닙니다.");
        } else if ("STEP01" == resultCode || "STEP02" == resultCode){

            $('#btnAutNoCheck').show();
            MCP.alert(jsonObj.RESULT_MSG);
        } else {

            $('#btnAutNoCheck').show();
            MCP.alert("시스템 오류 입니다. 다시 시도하기기 바랍니다. ");
        }
    });
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
            $("#timeOverYn").val('Y');
			 $("#countdownTime").hide();
            return;
        }
    }, 1000);
}