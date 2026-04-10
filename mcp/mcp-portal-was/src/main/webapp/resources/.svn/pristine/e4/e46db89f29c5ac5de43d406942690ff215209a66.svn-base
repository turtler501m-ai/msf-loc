
$(document).ready(function(){

	changeCaptcha(); //Captcha Image 요청

	$('#reLoad').click(function(){ changeCaptcha(); }); //'새로고침'버튼의 Click 이벤트 발생시 'changeCaptcha()'호출
	$('#soundOn').click(function(){ audioCaptcha(); }); //'음성듣기'버튼의 Click 이벤트 발생시 'audioCaptcha()'호출
	$('#soundOnKor').click(function(){ audioCaptcha(); }); //한글음성듣기 버튼에 클릭이벤트 등록

	setInputFilter(document.getElementById("juridicalNumber"), function(value) {
    	return value.replace(/[^0-9]/g,"");
	});

	setInputFilter(document.getElementById("mobileNo"), function(value) {
	  return value.replace(/[^0-9]/g,"");
	});

	setInputFilter(document.getElementById("answer"), function(value) {
	  return value.replace(/[^0-9]/g,"");
	});

	window.KTM.initialize();
});


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

var telCounselAjax = function() {


	if (chkRequire(["counselCtg","counselNm","juridicalNumber","agentNm","mobileNo","operType","answer"])) return;

	if ($("[name='chkDS']:checked").length == 0) {
		MCP.alert("개인정보 수집 및 이용을 동의해 주세요.");
		$("[name='chkDS']").focus();
		return;
	}

	if($("#juridicalNumber").val() !='' && $("#juridicalNumber").val().length < 10){
			MCP.alert("사업자 등록번호가 올바르지 않습니다.");
		$("#juridicalNumber").focus();
		return;
	}

	if($("#mobileNo").val() !='' && $("#mobileNo").val().length < 10){
			MCP.alert("휴대폰 번호가 올바르지 않습니다.");
		$("#mobileNo").focus();
		return;
	}

	var varData = ajaxCommon.getSerializedData(
		{
			counselCtg: $("#counselCtg").val(),
			counselNm: $("#counselNm").val(),
			juridicalNumber: $("#juridicalNumber").val(),
			agentNm: $("#agentNm").val(),
			mobileNo: $("#mobileNo").val(),
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
					     	this.close();
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

/**
 입력받은 input name 들의 입력값을 확인한다.
 @param 유효성 검사를 할 input의 name값을 가지고 있는 array
 @return 유효성 실패:false ,유효성 성공:true
*/
var chkRequire = function(objNames) {
var rtnValue = false;
$.each(objNames,function(index,val) {
	var $currentItem = $("input[name='"+val+"'],select[name='"+val+"']");
	if ($.trim($currentItem.val()) == '') {
		MCP.alert($currentItem.attr("title")+' 입력해 주세요.', function(){
				window.close();
		});
		$currentItem.focus();
		rtnValue = true;
		return false;
	}
});
return rtnValue;

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


var nextChk = function (){
	if($('#counselNm').val() != '' && $('#juridicalNumber').val() != '' && $('#agentNm').val() != '' &&
	$('#mobileNo').val() != '' && $('#answer').val() != '' && $('input[name=chkDS]:checked').val() != undefined){
		$('#telCounselConfirm').removeClass('is-disabled');
	}else{
		$('#telCounselConfirm').addClass('is-disabled');
	}
}