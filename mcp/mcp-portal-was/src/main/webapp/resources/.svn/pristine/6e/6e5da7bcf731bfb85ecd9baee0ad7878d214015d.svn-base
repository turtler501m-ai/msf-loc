
$(document).ready(function(){
	$('#modalArs').removeClass('c-modal--xlg');
	$('#modalArs').addClass('c-modal--md');
	changeCaptcha(); //Captcha Image 요청

	$('#reLoad').click(function(){ changeCaptcha(); }); //'새로고침'버튼의 Click 이벤트 발생시 'changeCaptcha()'호출
	$('#soundOn').click(function(){ audioCaptcha(); }); //'음성듣기'버튼의 Click 이벤트 발생시 'audioCaptcha()'호출
	$('#soundOnKor').click(function(){ audioCaptcha(); }); //한글음성듣기 버튼에 클릭이벤트 등록

	setInputFilter(document.getElementById("juridicalNumber"), function(value) {
	  return /^\d*\.?\d*$/.test(value);
	});

	setInputFilter(document.getElementById("inpTelNum1"), function(value) {
	  return /^\d*\.?\d*$/.test(value);
	});

	setInputFilter(document.getElementById("inpTelNum2"), function(value) {
	  return /^\d*\.?\d*$/.test(value);
	});

	setInputFilter(document.getElementById("inpTelNum3"), function(value) {
	  return /^\d*\.?\d*$/.test(value);
	});

	setInputFilter(document.getElementById("answer"), function(value) {
	  return /^\d*\.?\d*$/.test(value);
	});
	window.KTM.initialize();
});


var telCounselAjax = function() {
	 /*
	 - 법인명 입력 여부
	 - 사업자 등록번호 입력 여부
	 - 사업자 등록번호 10자리 입력 여부
	 - 대표자(대리인) 이름 입력 여부
	 - 연락처 입력 여부
	 - 연락처 최소 10자리 입력 여부
	 - 보안문자 입력 여부
	 - 약관 동의 여부
	 - 보안문자가 유효하지 않은 경우 안내 메시지 노출
	 */
	if (!$("[name='chkDS']").is(":checked")) {
		MCP.alert("개인정보 수집 및 이용을 동의해 주세요.");
		$("[name='chkDS']").focus();
		return;
	}

	if($("#counselNm").val().trim() ==''){
			MCP.alert("법인명을 입력해 주십시오.");
		$("#counselNm").focus();
		return;
	}

	if($("#juridicalNumber").val().trim() == '' || $("#juridicalNumber").val().length < 10){
			MCP.alert("사업자 등록번호가 올바르지 않습니다.");
		$("#juridicalNumber").focus();
		return;
	}

	if($("#agentNm").val().trim() == ''){
		MCP.alert("대표자(대리인) 이름을 입력해주십시오.");
		$("#juridicalNumber").focus();
		return;
	}

	if($("#inpTelNum1").val().trim()  == '' || $("#inpTelNum1").val().length < 3){
		MCP.alert("휴대폰 번호가 올바르지 않습니다.");
		$("#inpTelNum1").focus();
		return;
	}

	if($("#inpTelNum1").val().trim() =='' || $("#inpTelNum1").val().length < 3){
		MCP.alert("휴대폰 번호가 올바르지 않습니다.");
		$("#inpTelNum1").focus();
		return;
	}

	if($("#inpTelNum2").val().trim() == '' || $("#inpTelNum2").val().length < 4){
		MCP.alert("휴대폰 번호가 올바르지 않습니다.");
		$("#inpTelNum2").focus();
		return;
	}

	if($("#inpTelNum3").val().trim() !='' && $("#inpTelNum3").val().length < 4){
			MCP.alert("휴대폰 번호가 올바르지 않습니다.");
		$("#inpTelNum3").focus();
		return;
	}

	if($("#answer").val().trim() =='' || $("#answer").val().length < 5){
			MCP.alert("보안문자를 입력해주세요.");
		$("#answer").focus();
		return;
	}

	var mobilePhoneNo = $("#inpTelNum1").val() + $("#inpTelNum2").val() + $("#inpTelNum3").val();
	var varData = ajaxCommon.getSerializedData(
		{
			counselCtg: '02',
			counselNm: $("#counselNm").val(),
			juridicalNumber: $("#juridicalNumber").val(),
			agentNm: $("#agentNm").val(),
			mobileNo: mobilePhoneNo,
			operType: $("#operType").val(),
			answer: $("#answer").val()
		}
	);

	// 전화상담 중복체크
   	ajaxCommon.getItem(
   		{
			id:'telCounselChk'
			,cache:false
			,url:'/m/telCounsel/telCounselDupChk.do'
			,data: varData
			,dataType:"json"
		}
			,function(jsonObj) {
				if(typeof jsonObj.RESULT_MSG != "undefined") {
					MCP.alert(jsonObj.RESULT_MSG, function(){
						     						window.close();
				});
				} else {
					if(jsonObj.rs == 1){
						KTM.Confirm('1개월 내 동일 연락처로 신청 건이 존재합니다. 추가 신청하시겠습니까?', function (){
							this.close();
							ajaxCommon.getItem(
				     			{
				     	   			id:'telCounselAjax'
				     	  			,cache:false
				     	  			,url:'/m/telCounsel/telCounselAjax.do'
				     	  			,data: varData
				     	  			,dataType:"json"
				     			}
				     			,function(jsonObj2) {
				     				if(typeof jsonObj2.RESULT_MSG != "undefined") {
				     					MCP.alert(jsonObj2.RESULT_MSG, function(){
					     						window.close();
											});
				     				} else {
				     					if (jsonObj2.rs == "1") {
				     						MCP.alert("법인 가입 상담신청이 완료 되었습니다.", function(){
												$('#telCounselCloseBtn').trigger('click');
											});
				     					} else if(jsonObj2.rs == "2") {
				     						MCP.alert("보안문자가 일치하지 않습니다.", function(){
					                            $("#answer").focus();
											});
			     					    } else {
			     						    MCP.alert("법인 가입 상담신청 중 오류가 발생하였습니다. 다시 신청해 주세요.");
				     					}
				     				}
				     	   		}
					     	);
						});
					} else {
				     	ajaxCommon.getItem(
				     			{
				     	   			id:'telCounselAjax'
				     	  			,cache:false
				     	  			,url:'/m/telCounsel/telCounselAjax.do'
				     	  			,data: varData
				     	  			,dataType:"json"
				     			}
				     			,function(jsonObj2) {
				     				if(typeof jsonObj2.RESULT_MSG != "undefined") {
				     					  MCP.alert(jsonObj2.RESULT_MSG, function(){
						     						window.close();
											});
				     				} else {
				     					if (jsonObj2.rs == "1") {
				     						 MCP.alert("법인 가입 상담신청이 완료 되었습니다.", function(){
						    	              		$('#telCounselCloseBtn').trigger('click');
											});
				     					} else if(jsonObj2.rs == "2") {
				     						 MCP.alert("보안문자가 일치하지 않습니다.", function(){
													$("#answer").focus();
												});
				     					} else {
				     						 MCP.alert("법인 가입 상담신청 중 오류가 발생하였습니다. 다시 신청해 주세요.");				     					}
				     				}
				     	   		}
				     	);
					}
				}
	   		}
	);
}

var audioCaptcha = function() {
	var uAgent = navigator.userAgent;
	var soundUrl = "/callCaptChaAudioByKor.do?fake="+new Date();
	if (uAgent.indexOf('Trident') > -1 || uAgent.indexOf('MSIE') > -1) {
		winPlayer(soundUrl);
	} else if (!!document.createElement('audio').canPlayType) {
		try {
			new Audio(soundUrl).play();
		} catch(e) {
			winPlayer(soundUrl);
		}
	}
}


function winPlayer(objUrl) {
	var uAgent = navigator.userAgent;
	if (uAgent.indexOf('Trident') > -1 || uAgent.indexOf('MSIE') > -1) {
		$('#audiocatpch').html('<embed src="'+ objUrl +'" type="audio/wav">');
	} else {
		$('#audiocatpch').html(' <bgsound src="' + objUrl + '">');
	}
}


function changeCaptcha() {
	  //IE에서 '새로고침'버튼을 클릭시 CaptChaImg.jsp가 호출되지 않는 문제를 해결하기 위해 "?rand='+ Math.random()" 추가
		rand = Math.random();
		$('#catpcha').html('<img src="/CaptChaImg.do?rand='+ rand + '" alt="보안인증확인문자"/>');
}


var nextChk = function (obj){

	if($(obj).attr('id') == 'juridicalNumber' && $('#juridicalNumber').val().trim() != '' && $('#juridicalNumber').val().length >= 11){
		$("#agentNm").focus();
	}

	if($(obj).attr('id') == 'inpTelNum1' && $('#inpTelNum1').val().trim() != '' && $('#inpTelNum1').val().length >= 3){
		$("#inpTelNum2").focus();
	}

	if($(obj).attr('id') == 'inpTelNum2' && $('#inpTelNum2').val().trim() != '' && $('#inpTelNum2').val().length >= 4){
		$("#inpTelNum3").focus();
	}

	if($(obj).attr('id') == 'inpTelNum3' && $('#inpTelNum3').val().trim() != '' && $('#inpTelNum3').val().length >= 4){
		$('#answer').focus();
	}

	if($('#counselNm').val().trim() != '' && $('#juridicalNumber').val().trim() != '' && $('#agentNm').val().trim() != '' &&
	$("#inpTelNum1").val().trim() != '' && $("#inpTelNum2").val().trim() != '' && $("#inpTelNum3").val().trim() != '' && $('#answer').val().trim() != '' && $('input[name=chkDS]').is(":checked")){
		$('#telCounselConfirm').removeClass('is-disabled');
		$('#telCounselConfirm').prop("disabled",false);
	}else{
		$('#telCounselConfirm').addClass('is-disabled');
		$('#telCounselConfirm').removeClass('is-disabled');
		$('#telCounselConfirm').prop("disabled",true);
	}
}

function setInputFilter(textbox, inputFilter) {
  ["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop"].forEach(function(event) {
    textbox.addEventListener(event, function() {
      if (inputFilter(this.value)) {
        this.oldValue = this.value;
        this.oldSelectionStart = this.selectionStart;
        this.oldSelectionEnd = this.selectionEnd;
      } else if (this.hasOwnProperty("oldValue")) {
        this.value = this.oldValue;
        this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
      } else {
        this.value = "";
      }
    });
  });
}