
VALIDATE_NOT_EMPTY_MSG={};
VALIDATE_NOT_EMPTY_MSG.userId="아이디를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.userName="이름을 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.newPass="비밀번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.newPassChk="비밀번호를 입력해 주세요.";

VALIDATE_NOT_KOR_MSG={};
VALIDATE_NOT_KOR_MSG.userId="입력하신 아이디/이름을 찾을 수 없습니다.";
VALIDATE_NOT_KOR_MSG.userName="입력하신 아이디/이름을 찾을 수 없습니다.";


var pageObj = {
    niceType:""
    , authObj:{}
    , niceHistSeq:0
    , startTime:0
	, newStart: "N"
}

window.onload = function (){
	if($('#tabId').val() == 'p'){
		$("#idPwSel").val("P");
		$("#passTab").trigger("click");
		pageObj.newStart= "Y";
	}else{
		$("#idPwSel").val("I");
		$("#idTab").trigger("click");
		pageObj.newStart= "Y";
	}

	$('.c-tabs--type1').show();

	if($('#tabId').val() == 'p') $('.searchIdDiv').show();
	else $('.searchPwStep1').show();

	$('#main-content').show();
}

//byte 계산식 추가
function chkByte(str){
	var string = undefined;  // 대상 문자열
	string = str;
	var stringByteLength = 0;
	stringByteLength = string.replace(/[\0-\x7f]|([0-\u07ff]|(.))/g,"$&$1$2").length;
	return stringByteLength;
}

function charByteSize(ch) {
	if (ch == null || ch.length == 0) {
		return 0;
	}
	var charCode = ch.charCodeAt(0);
	if (charCode <= 0x00007F) {
		return 1;
	} else if (charCode <= 0x0007FF) {
		return 2;
	} else if (charCode <= 0x00FFFF) {
		return 3;
	} else {
		return 4;
	}
}

function cutByteLength(s, len) {
	if (s == null || s.length == 0) {
		return 0;
	}
	var size = 0;
	var rIndex = s.length;
	for ( var i = 0; i < s.length; i++) {
		size += this.charByteSize(s.charAt(i));
		if( size == len ) {
			rIndex = i + 1;
			break;
		} else if( size > len ) {
			rIndex = i;
			break;
		}
	}
	return s.substring(0, rIndex);
}

var nextChk = function (e){
	if($.trim($('#userName').val()).length > 20){
		if(chkByte($.trim($('#userName').val()))>60){
			var cstr = cutByteLength($.trim($('#userName').val()),60);
			$('#userName').val(cstr)
		}
	}

	if($.trim($('#userId').val()) != '' && $.trim($('#userName').val()) != ''){
		$('#schPwStep1Btn').removeClass('is-disabled');
	}else{
		if(!$('#schPwStep1Btn').hasClass('is-disabled')){
			$('#schPwStep1Btn').addClass('is-disabled');
		}
	}
}

$(document).on("keyup", "#userId,#userName", function(e) {
    if (e.keyCode == 13) {
        $('#schPwStep1Btn').trigger('click');
    }
});

$(document).ready(function () {
	$('#idTab').on('click', function (){

		if(pageObj.newStart == "Y"){
			location.href= "/findPassword.do";
			return false;
		}

		$("#idPwSel").val("I");
		$('.searchIdDiv').show();
	});

	$('#passTab').on('click', function (){

		if(pageObj.newStart == "Y"){
			location.href= "/findPassword.do?tabId=p";
			return false;
		}

		if(!$(this).hasClass('is-active')){
			$("#idPwSel").val("P");
			$('.searchPwDiv').show();
			$('.searchPwStep1').hide();
			$('.searchPwStep2').hide();
		}
	});

	$("#findIdBtn").click(function(){	//아이디찾기 휴대폰인증
		$("#idPwSel").val("I");
		pageObj.niceType = NICE_TYPE.CUST_AUTH ;
		var data = {width:'500', height:'700'};
    	openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M', data);
	});

	$("#findIdIpin").click(function(){	//아이디찾기 아이핀인증
		$("#idPwSel").val("I");
		pageObj.niceType = NICE_TYPE.CUST_AUTH ;
		var data = {width:'500', height:'700'};
    	openPage('outLink2', '/nice/popNiceIpinAuth.do', data);
	});

	$("#findIdCredit").click(function(){	//아이디찾기 카드인증
		$("#idPwSel").val("I");
		pageObj.niceType = NICE_TYPE.CUST_AUTH ;
		var data = {width:'500', height:'700'};
    	openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=C', data);
	});

	$("#findPwBtn").click(function(){	//비번찾기 휴대폰인증
		$("#idPwSel").val("P");
		pageObj.niceType = NICE_TYPE.CUST_AUTH ;
		var data = {width:'500', height:'700'};
    	openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M', data);
	});

	$("#findPwIPin").click(function(){	//비번찾기 아이핀인증
		$("#idPwSel").val("P");
		pageObj.niceType = NICE_TYPE.CUST_AUTH ;
		var data = {width:'500', height:'700'};
    	openPage('outLink2', '/nice/popNiceIpinAuth.do', data)
	});

	$("#findPwCredit").click(function(){	//비번찾기 휴대폰인증
		$("#idPwSel").val("P");
		pageObj.niceType = NICE_TYPE.CUST_AUTH ;
		var data = {width:'500', height:'700'};
    	openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=C', data);
	});

	//SMS발송
	$("#sendSmsBtn").click(function() {
		 var varData = ajaxCommon.getSerializedData({
			 sendSmsNum : $('#selMobileNo').val()
	     });
		 ajaxCommon.getItem({
	         id:'sendSms'
	         ,cache:false
	         ,url:'/sendIdSmsAjax.do'
	         ,data: varData
	         ,dataType:"json"
	      }
	      ,function(jsonObj) {
	    	  if (jsonObj.RESULT_CODE == "S") {
	    		  $(".searchIdDiv").hide();
	    		  $(".searchIdStep1").hide();
	    		  $("#searchIdStep3").show();
	    	  } else {
	    	  	MCP.alert(jsonObj.RESULT_MSG);
	    	  }
	      });
	});

	//비밀번호 찾기 step1
	$("#schPwStep1Btn").click(function() {

		if($('#userId').val() == ''){
			MCP.alert('아이디를 입력해주세요.', function (){
				$('#userId').focus();
			});
			return false;
		}
		if($('#userName').val() == ''){
			MCP.alert('이름을 입력해주세요.', function (){
				$('#userName').focus();
			});
			return false;
		}

    	var varData = ajaxCommon.getSerializedData({
    		userId : $.trim($("#userId").val()),
    		userName : $.trim($("#userName").val())
    	});

   		ajaxCommon.getItem({
   	    	id:'checkId'
   	        ,cache:false
   	        ,url:'/checkIdAjax.do'
   	        ,data: varData
   	        ,dataType:"json"
   	    }
       ,function(jsonObj) {
    	  if (jsonObj.RESULT_CODE == "S") {
    		  $(".searchPwDiv").hide();
    		  $("#dispalyId").text(jsonObj.userId);
			  $(".searchPwStep1").show();
    	  } else {
    	  	  MCP.alert(jsonObj.RESULT_MSG);
    	  }
       });
	});

	//새 비밀번호 변경
	$("#searchPwStep2Btn").click(function() {

        if (pwChange()) {
			KTM.Confirm("비밀번호를 변경하시겠습니까?", function(){
	        	var varData = ajaxCommon.getSerializedData({
	        		userId:$.trim($("#userId").val()),
	        		userName:$.trim($("#userName").val()),
	        		newPassword : $.trim($("#password").val())
	   	    	});
		   		ajaxCommon.getItem({
		   	    	id:'changePw'
		   	        ,cache:false
		   	        ,url:'/changePwAjax.do'
		   	        ,data: varData
		   	        ,dataType:"json"
		   	    }
	   	       ,function(jsonObj) {
	   	    	  if (jsonObj.RESULT_CODE == "S") {
	   	    		$(".searchPwDiv").hide();
					$(".searchPwStep2").hide();
	   	    		$("#searchPwStep3").show();
	   	    		MCP.alert2('비밀번호가 변경되었습니다.</br>변경한 비밀번호로 로그인해 주시기 바랍니다.', function (){
						location.href='/loginForm.do';
					}, '로그인으로 이동');
	   	    	  } else {
	   	    	  	  MCP.alert(jsonObj.RESULT_MSG, function (){
						location.href='/findPassword.do?tabId=p';
					  });

	   	    	  }
	   	       });
	   	       this.close();
			});
		}else{
			return false;
		}

	});

});

function niceAuthResult(prarObj){

	var strMsg = "고객 정보와 본인인증한 정보가 틀립니다.";
	pageObj.niceResLogObj = prarObj;

	var authInfoJson ={contractNum: ""};
	var isAuthDone = diAuthCallback(authInfoJson);

	if(isAuthDone){
		$('#isNice').val('Y');
		pageObj.niceHistSeq = diAuthObj.resAuthOjb.NICE_HIST_SEQ;
		certSuccessFn();
	}else{

		strMsg= (diAuthObj.resAuthOjb.RESULT_MSG == undefined) ? strMsg : diAuthObj.resAuthOjb.RESULT_MSG;
		MCP.alert(strMsg);
		return null;
	}

}

function fnNiceCert(prarObj) {
	niceAuthResult(prarObj);
}

function fnNiceIpinCert(prarObj) {
	niceAuthResult(prarObj);
}

//인증 완료
function certSuccessFn() {

	var selUrl = "/checkNicePwAjax.do";
	var name = $('#userName').val();

	if ($("#idPwSel").val() == 'I') {
		selUrl = "/checkNiceCertAjax.do";
		name = '';
	}

	var varData = ajaxCommon.getSerializedData({
		name : name
    });

	ajaxCommon.getItem({
	   	id:'certSuccessFn'
	    ,cache:false
	    ,url:selUrl
	    ,data: varData
	    ,dataType:"json"
	}
    ,function(jsonObj) {
    	if (jsonObj.RESULT_CODE == "S") {
    		if ($("#idPwSel").val()=="I") { //아이디 찾기
       			$("#maskId").text(jsonObj.userId);
				$("#selMobileNo option[value='USER']").text(jsonObj.userMobileNo);
				if (jsonObj.certMobileNoList != null && jsonObj.certMobileNoList.length > 0) {
					var html = "";
					for (var i=0;i<jsonObj.certMobileNoList.length;i++) {
						if(jsonObj.userMobileNo != jsonObj.certMobileNoList[i].certMobileNo){
							html += "<option value="+ jsonObj.certMobileNoList[i].contractNum +">";
							html += jsonObj.certMobileNoList[i].certMobileNo;
							html += "</option>";
						}
					}
					$("#selMobileNo").append(html);
				}
				$("#selMobileNo").val("USER");
				$(".searchIdDiv").hide();
				$(".searchIdStep1").show();

       		} else { //비밀번호 찾기
       			$(".searchPwDiv").hide();
       			$(".searchPwStep1").hide();
				$(".searchPwStep2").show();
       		}
    	} else {
			if(jsonObj.RESULT_CODE == 'E0001'){
				KTM.Confirm('장기 미사용자로 해당 아이디는 사용 불가능합니다.', function(){
					KTM.Confirm('기존 회원 정보 삭제 후 재가입하시겠습니까?', function (){
						ajaxCommon.getItem({
							id: 'resetDormancy'
							, cache: false
							, url: '/resetDormancy.do'
							, data: varData
							, dataType: "json"
						}
						, function(jsonObj) {
							if (jsonObj.RESULT_CODE == "S") {
								MCP.alert('정상적으로 처리되었습니다.', function (){
									location.href='/join/fstPage.do';
								});
							} else {
								// 페이지 새로고침
								MCP.alert(jsonObj.RESULT_MSG, function(){
									if ($("#idPwSel").val() == 'I') $("#idTab").trigger("click");
									else $("#passTab").trigger("click");
								});
							}
						});

						this.close();
					});
		   	       this.close();
				});
			}else{

				// 본인인증은 성공했으나, 아이디/비밀번호 찾기에 실패한 경우 > 새로고침
	    		MCP.alert(jsonObj.RESULT_MSG, function(){
					if ($("#idPwSel").val() == 'I') $("#idTab").trigger("click");
					else $("#passTab").trigger("click");
				});
			}
    	}
    });
}

var nextStepChk = function (){
	if($('#isChkPw').val() == 'Y'){
		$('#searchPwStep2Btn').removeClass('is-disabled');
	}else{
		$('#searchPwStep2Btn').addClass('is-disabled');

	}
}