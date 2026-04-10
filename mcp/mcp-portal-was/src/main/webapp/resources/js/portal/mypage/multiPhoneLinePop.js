/**
 * 
 */

$(document).ready(function(){
	
	$("#certifyCallBtn").attr("onclick", "sendCertSms()");
	//$("#regularCertBtn").attr("onclick", "regularCertLogin()");
	$("#custNm").prop("disabled", true);
	$("#regularCertTimeBtn").attr("onclick", "multiPhoneTimeReload()");
	
	// 고객정보수집 동의 약관
	var termDat = ajaxCommon.getSerializedData({
			cdGroupId1:'TERMSINF'
			,cdGroupId2: 'TERMSINF01'
	    });
	
	ajaxCommon.getItem({
	        id:'smsTermsInf'
	        ,cache:false
	        ,url:'/termsPop.do'
	        ,data: termDat
	        ,dataType:"json"
	    }
	    ,function(data){

	});
});

function multiPhoneTimeReload(){

	$("#timeYn").val('Y');
	$("#timeOverYn").val('');
	sendCertSms(); 
	clearInterval(interval);
	dailyMissionTimer(3);	
}

// 취소버튼 
$("#modalCancel").on("click", function(){
	$(".c-icon--close").trigger("click");
});

// 회선 추가
$("#addMultiBut").on("click", function(){
	if($("#certifyYn").val() == 'Y'){
		var varData = ajaxCommon.getSerializedData({
				name:$("#unMkNm").val()
				,phone1:$("#phoneNum").val()
				,menuType:$("#menuType").val()
				,certifySms:$("#certifySms").val()
			});
			
			ajaxCommon.getItem({
			    id:'userMultiReg'
			    ,cache:false
			    ,url:'/mypage/userMultiReg.do'
			    ,data: varData
			    ,dataType:'json'
			},function(data){

					MCP.alert(data.successMsg, function(){
						location.href = data.redirectUrl;	
					});
			});
	}else{
		MCP.alert("SMS인증 후 다회선 추가가 가능합니다.", function(){
			$("#phoneNum").focus();
		});
	}
});

// 인증번호 발송 전 유효성체크
function sendCertSms(){
	
	if($("#chkAgreeA").is(":checked")){
		$("#custNm").attr("name", "name");
		if(!$("#phoneNum").val() || $("#phoneNum").val().length < 11){
			MCP.alert("유효한 휴대폰 번호를 입력해 주세요.", function(){
				$("#phoneNum").focus();
			});
			return false;
		}else{
			clearInterval(interval);


			var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
			$("#certifySms").val('');
		
			
			if(phoneNum == ""){
				MCP.alert('핸드폰 번호를 입력해 주세요.', function (num){
					$("#phoneChk").show();
					$(".inputPhone").addClass("has-error");
					$("#phoneNum").focus();
				}); 
				
				return flase;
			} else {
				$("#phoneChk").hide();
				$(".inputPhone").removeClass("has-error");
		
			}
			
			if(phoneNum.length < 11){
				MCP.alert('핸드폰 번호를 정확하게 입력해 주세요.', function (num){
					$("#phoneChk").show();
					$(".inputPhone").addClass("has-error");
					$("#phoneNum").focus();
				}); 
				
				return false;
			}
			
		
			var varData = ajaxCommon.getSerializedData({
				cntrMobileNo:$.trim(phoneNum)
				,subLinkName:$("#unMkNm").val()
				,menuType:$("#menuType").val()
				,timeYn : $("#timeYn").val()
			});
		
			ajaxCommon.getItem({
		    id:'userMultiSmsAjax'
		    ,cache:false
		    ,url:'/mypage/userMultiSmsAjax.do'
		    ,data: varData
		    ,dataType:"json"
		},function(data){

			if(data.RESULT_CODE == '00000'){
				MCP.alert("인증번호를 발송했습니다.");

				$("#countdown").show();
				$("#countdownTime").show();
				$("#certifyCallBtn").text("인증번호 재전송");
				dailyMissionTimer(3);
				$("#certify").val("Y");
				$("#svcCntrNo").val(data.svcCntrNo);
				$("#contNum").val(data.contractNum);
			}else if(data.RESULT_CODE == '0001'){
				MCP.alert("입력하신 정보에 오류가 있습니다. 다시 입력해 주세요.");
				$("#countdown").hide();
				$("#countdown1").hide();
			}else if(data.RESULT_CODE == '0002' || data.RESULT_CODE == '0003' || data.RESULT_CODE == '0004'){
				MCP.alert(data.msg);
			}else if( data.RESULT_CODE =="00010" ){
				$("#timeYn").val('N');
				clearInterval(interval);
				dailyMissionTimer(3);
			}else if( data.RESULT_CODE =="00011" ){
				MCP.alert(data.message);
			}else{
				MCP.alert("인증번호 발송에 실패하였습니다.");
				$("#countdown").hide();
				$("#countdown1").hide();
			}
		})

		}
		/*$("#phone1").val($("#phoneNum").val().substr(0,3));
		if($("#phoneNum").val().length > 10){
			var phone2 = $("#phoneNum").val().substr(3,4);
			var phone3 = $("#phoneNum").val().substr(7,4);
		}else{
			var phone2 = $("#phoneNum").val().substr(3,3);
			var phone3 = $("#phoneNum").val().substr(6,4);
		}
		var varData = ajaxCommon.getSerializedData({
			name:$("#custNm").val()
			,phone1:$("#phoneNum").val().substr(0,3)
			,phone2:phone2
			,phone3:phone3
	    });*/
		/*var varData = ajaxCommon.getSerializedData({
			cntrMobileNo:$("#phoneNum").val()
		});	
			
		ajaxCommon.getItem({
		    id:'userMultiSmsAjax'
		    ,cache:false
		    ,url:'/m/mypage/userMultiSmsAjax.do'
		    ,data: varData
		    ,dataType:"json"
		},function(data){

			if(data.RESULT_CODE == '0001'){
				MCP.alert("입력하신 정보에 오류가 있습니다. 다시 입력해 주세요.");
			}else if(data.RESULT_CODE == '0002' || data.RESULT_CODE == '0003' || data.RESULT_CODE == '0004'){
				MCP.alert(data.msg);
			}else{
				certifyCallLogin();
				MCP.alert("인증번호를 발송했습니다.");
			}
		});*/
	}else{
		MCP.alert("개인정보수집 동의 약관에 동의해 주세요.");
		return false;
	}
	
}

// 인증번호 발송
/*function certifyCallLogin(){

	clearInterval(interval);

	var custNm = ajaxCommon.isNullNvl($("#custNm").val(),"");
	var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");

	if(custNm == ""){
		$("#custNmChk").show();
		$("#inputCustNm").addClass("has-error");
		$("#custNm").focus();
		return;
	} else {
		$("#custNmChk").hide();
		$("#inputCustNm").removeClass("has-error");
	}


	if(phoneNum == "" && phoneNum.length <11){
		$("#phoneChk").show();
		$("#inputPhoneNum").addClass("has-error");
		$("#phoneNum").focus();
		return;
	}else{
		$("#phoneChk").hide();
		$("#inputPhoneNum").removeClass("has-error");
	}


	var varData = ajaxCommon.getSerializedData({
		phone:$.trim(phoneNum)
		,name:$("#custNm").val()
		,menuType:"multiPhone"
		,certifySms:$("#certifySms").val()
	});

	ajaxCommon.getItem({
	    id:'smsAuthAjax'
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


			} else if( data.RESULT_CODE == "00001" || data.RESULT_CODE == "00003" ){
				$("#countdown").hide();
				$("#countdown1").hide();
				MCP.alert("인증 번호 발송에 실패했습니다.");
				return false;
			} else if( data.RESULT_CODE == "00002" ){
				MCP.alert("kt M모바일 고객인 경우에만 가능합니다.");
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
}*/


// 인증번호 확인
function regularCertLogin(){
	var certify = $("#certify").val();
	if(certify!="Y"){
		MCP.alert("인증번호를 받으세요.");
		$("#certifyCallBtn").focus();
		return false;
	}

	var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
	var certifySms = ajaxCommon.isNullNvl($("#certifySms").val(),"");
	if(certifySms==""){
		MCP.alert("인증번호를 입력해 주세요.");
		return false;
	}

	var varData = ajaxCommon.getSerializedData({
		certifySms:$.trim(certifySms)
		,phone:$.trim(phoneNum)
		,menuType :$("#menuType").val()
		,svcCntrNo : $("#svcCntrNo").val()
		,contractNum : document.getElementById('contNum').value
    });


	ajaxCommon.getItem({
	    id:'smsAuthCheckAjax'
	    ,cache:false
	    ,url:'/sms/smsAuthCheckAjax.do'
	    ,data: varData
	    ,dataType:"json"
	},function(data){

		if (AJAX_RESULT_CODE.SUCCESS){
	    		if( data.RESULT_CODE =="00000" ){
	    			MCP.alert("인증이 완료되었습니다.");
	    			$("#certifyYn").val("Y");
					$("#combiChkYn").val("N");
	    			$("#timeover").hide();
					$("#countdown").hide();
					$("#countdownTime").hide();
    				$("#countdown1").hide();
	    			clearInterval(interval);
	    		} else if( data.RESULT_CODE =="0001" ){
	    			MCP.alert(data.MSG);
	    			return false;
				} else if (data.RESULT_CODE =="STEP01"){ // STEP 오류
					MCP.alert(data.MSG);
					return false;
				} else{
					MCP.alert("인증에 실패했습니다.");
					return false;
	    		}
	    	}else{
	    		$("#rateSoc").val("");
    			$("#rateNm").text("-");
	    		MCP.alert("인증에 실패했습니다.");
	    		return false;
	    	}
	}
  );

}