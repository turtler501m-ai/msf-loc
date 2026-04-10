//##############################################################################################
// APPLE 로그인 버튼 클릭
function loginWithApple() {
	if($('#phoneOs').val() == 'I'){
		//아이폰 버전 체크
		window.webkit.messageHandlers.requestAppleLogin.postMessage('');
	}else{
		$.ajax({
			url: '/m/login/getAppleAuthUrl.do',
			type: 'get',
			async: false,
			dataType: 'text',
			success: function(res) {
				var data = {width:'600', height:'700'};
				openPage('outLink3', res, data)
			}
		});
	}
}

function responseAppleLogin(data){
	var data = JSON.parse(data);
	if(data != null && data != undefined){
		if(data.rsltCd == '00000' && data.token != undefined && data.token != ''){
			var newForm = $('<form></form>');
			newForm.attr('name','newForm');
			newForm.attr('method','post');
			newForm.attr('action','/m/login/appleCalbkUrl.do');
			newForm.append($('<input/>', {type: 'hidden', name: 'token', value:data.token}));
			newForm.appendTo('body');
			newForm.submit();
		}else if(data.rsltCd == '4'){
			$.ajax({
				url: '/m/login/getAppleAuthUrl.do',
				type: 'get',
				async: false,
				dataType: 'text',
				success: function(res) {
					var data = {width:'600', height:'700'};
					openPage('outLink3', res, data)
				}
			});
		}
	}
}


// 네이버 로그인 버튼 클릭
function loginWithNaver() {
	$("#naver_id_login_anchor").trigger("click");
	/*
    $.ajax({
        url: '/m/login/getNaverAuthUrl.do',
        type: 'get',
    }).done(function (res) {
        var data = {width:'600', height:'700'};
        openPage('outLink2', res, data)
    });
    */
}

// 카카오 로그인 버튼 클릭
function loginWithKakao() {
	$.ajax({
		url: '/m/login/getKakaoAuthUrl.do',
		type: 'get',
	}).done(function (res) {
		var data = {width:'600', height:'700'};
		openPage('outLink2', res, data)
	});

}

// 페이스북 로그인 버튼 클릭
function loginWithFacebook() {
	FB.login(function(response){
		fbLogin();
	});
}


function fbLogin() {
	FB.getLoginStatus(function(response) {

		if (response.status === 'connected') {
			FB.api('/me', function(res) {
				if(res.id != '' && res.id != undefined){
					var newForm = $('<form></form>');
					newForm.attr('name','newForm');
					newForm.attr('method','post');
					newForm.attr('action','/m/login/facebookCalbkUrl.do');
					newForm.append($('<input/>', {type: 'hidden', name: 'snsCd', value:'FACEBOOK' }));
					newForm.append($('<input/>', {type: 'hidden', name: 'snsId', value:res.id }));
					newForm.appendTo('body');
					newForm.submit();
				}
			});
		} else if (response.status === 'not_authorized') {
			MCP.alert('앱에 로그인해야 이용가능한 기능입니다.');
		} else {
			MCP.alert('페이스북에 로그인해야 이용가능한 기능입니다.');
		}
	}, false);
}

window.fbAsyncInit = function() {
	FB.init({
		//appId   : '292838682458105',
		//appId   : '525256709014287',
		appId   : '2070441719782678',
		cookie  : true,
		xfbml   : true,
		version : 'v12.0'
	});
};

(function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) return;
	js = d.createElement(s); js.id = id;
	js.src = "//connect.facebook.net/ko_KR/sdk.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));


var myScroll;

function setCookie(cookieName, value, exdays){
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
	document.cookie = cookieName + "=" + cookieValue;
}

function deleteCookie(cookieName){
	var expireDate = new Date();
	expireDate.setDate(expireDate.getDate() - 1);
	document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

function getCookie(cookieName) {
	cookieName = cookieName + '=';
	var cookieData = document.cookie;
	var start = cookieData.indexOf(cookieName);
	var cookieValue = '';
	if(start != -1){
		start += cookieName.length;
		var end = cookieData.indexOf(';', start);
		if(end == -1)end = cookieData.length;
		cookieValue = cookieData.substring(start, end);
	}
	return unescape(cookieValue);
}



var pageObj = {
	isSend:false
	,niceType:""
	,authObj:{}
	,niceHistSeq:0
	,startTime:0
}

function goLogin() {

	var f = document.mainForm;

	$('#name').val('');
	$('#mobileNo').val('');
	$('#authValue').val('');
	$('#certifyYn').val('N');

	if($('#userId').val().length == 0) {
		MCP.alert('아이디를 입력해 주세요.', function (){
			$('#userId').focus();
		});
		return;
	}

	if($('#passWord').val().length == 0) {
		MCP.alert('비밀번호를 입력해 주세요.', function (){
			$('#passWord').focus();
		});
		return;
	}

	if (pageObj.isSend) {
		return ;
	}

	pageObj.isSend = true;
	$('#userId').val($('#userId').val().trim());
	//alert($('#uuid').val());

	if(typeof recaptchalogin == "function"){
		recaptchalogin(loginPreCheckAjax);
	}else{
		loginPreCheckAjax();
	}
}

function loginPreCheckAjax(){
	// 기존 로직
	$.ajax({
		type : 'POST',
		url : '/isBirthGenderAjax.do',
		data : $('#mainForm').serialize(),
		dataType : 'json',
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		async : true,
		beforeSend : function(){
		},
		success : function(data) {
			if(data.returnCode == '00') {
				//alert(data.returnCode);
				//APP 로그인 && I || A
				if($('#platFormCd').val() == 'A' && $('#uuid').val() != ''){
					var varData = ajaxCommon.getSerializedData({
						uuid : $('#uuid').val(),
						token : '',
						loginAuto : $('#loginAuto').val()
					});

					ajaxCommon.getItem({
							id:'getAppUuidInfo'
							,cache:false
							,url:'/m/app/getUuidAppInfo.do'
							,data: varData
							,dataType:"json"
							,async:false
						}
						,function(json){
							if($('#phoneOs').val() == 'I'){
								window.webkit.messageHandlers.onLoginResult.postMessage(JSON.stringify(json));
							}else if($('#phoneOs').val() == 'A'){
								window.ktmmobile.onLoginResult(JSON.stringify(json));
							}
						});
				}
				goLoginProcess();	// 통과하고 로그인시도함
			} else if(data.returnCode == '40') {
				//생일,성별 입력 레이어
				$('#userId').val($('#userId').val().trim());
				$('form[name=mainForm]').attr('action', '/m/addBirthGenderView.do');
				$('form[name=mainForm]').submit();
			} else if(data.returnCode == '42') {
				MCP.alert('아이디나 비밀번호가 정확하지 않습니다.</br>비밀번호 5회 이상 오류 시 로그인이 제한되며,</br>비밀번호 찾기 후 로그인이 가능합니다.');
				$('#passWord').val('');
				pageObj.isSend = false ;
			} else if(data.returnCode == '43') {
				pageObj.isSend = false ;
				MCP.alert('로그인에 5회 이상 실패하였습니다.</br>비밀번호 찾기 후 다시 로그인해 주세요.', function (){
					location.href ='/m/findPassword.do?tabId=p';
				});
			}  else if(data.returnCode == '98') {
				pageObj.isSend = false ;
				MCP.alert('단시간 반복된 서비스 호출로</br>로그인이 제한됩니다.</br>잠시 후 이용 바랍니다.', function (){
					location.href ='/m/main.do';
				});
			}  else if(data.returnCode == '99') {
				pageObj.isSend = false ;
				MCP.alert('로그인에 5회 이상 실패하였습니다.</br>비밀번호 찾기 후 다시 로그인해 주세요.', function (){
					location.href ='/m/findPassword.do?tabId=p';
				});

			} else if(data.returnCode == "50") {
				pageObj.isSend = false ;
				MCP.alert('해당 계정은 비밀번호 유출의심 신고(DarkWeb)되어, 본인여부 확인 및 비밀번호 변경 후</br> 로그인 및 서비스 이용이 가능합니다.</br></br>고객센터: 1899-5000', function (){
					location.href ="/m/findPassword.do?tabId=p";
				});
			} else if(data.returnCode == "96" || data.returnCode == "80"){  // 리캡챠 관련 추가, 본인인증 추가
				pageObj.isSend = false;
				MCP.alert(data.message, function(){
					//var parameterData = ajaxCommon.getSerializedData({menuType : "login"});
					//openPage('pullPopup','/m/sms/smsAuthInfoPop.do', parameterData);
					pageObj.niceType = NICE_TYPE.CUST_AUTH ;
					openPage('outLink','/nice/popNiceAuth.do?sAuthType=M&mType=Mobile','');
				}, 'click');
			} else {
				MCP.alert(data.message);
				pageObj.isSend = false ;
			}
			return;
		},
		error : function() {
			MCP.alert('오류가 발생했습니다. 다시 시도해 주세요.');
			pageObj.isSend = false ;
			return;
		}

	}); // end of ajax ----------------------------
}

function goLoginProcess() {
	KTM.LoadingSpinner.show();
	$('#userId').val($('#userId').val().trim());
	$('form[name=mainForm]').attr('action', '/m/loginProcess.do');
	$('form[name=mainForm]').submit();
}

$(document).ready(function(){
	displayM008BannerList();
	$(document).on("keydown", "#userId,#passWord", function(e) {
		if ($.trim($('#userId').val()).length > 0 && $.trim($('#passWord').val()).length > 0 && e.keyCode == 13) {
			goLogin();
		}
	});

	// 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
	var userInputId = getCookie('userInputId');
	if(userInputId != undefined && userInputId != ''){
		$('input[name=userId]').val(userInputId);
	}

	if($('input[name=userId]').val() != ''){ // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
		$('#idSave').attr('checked', true); // ID 저장하기를 체크 상태로 두기.
	}

	$('#idSave').change(function(){ // 체크박스에 변화가 있다면,
		if($('#idSave').is(':checked')){ // ID 저장하기 체크했을 때,
			var userInputId = $('input[name=userId]').val().trim();
			setCookie('userInputId', userInputId, 7); // 7일 동안 쿠키 보관
		}else{ // ID 저장하기 체크 해제 시,
			deleteCookie('userInputId');
		}
	});

	// ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
	$('input[name=userId]').keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
		if($('#idSave').is(':checked')){ // ID 저장하기를 체크한 상태라면,
			var userInputId = $('input[name=userId]').val().trim();
			setCookie('userInputId', userInputId, 7); // 7일 동안 쿠키 보관
		}
	});


	var snsChk = $('#lastSnsLogin').val();
	if(snsChk == 'NAVER'){
		$('#naverToolTip').show();
	}else if(snsChk == 'KAKAO'){
		$('#kakaoToolTip').show();
	}else if(snsChk == 'FACEBOOK'){
		$('#facebookToolTip').show();
	}else if(snsChk == 'APPLE'){
		$('#appleToolTip').show();
	}

	$('#reserveLogin').change(function(){ // 체크박스에 변화가 있다면,
		if($('#reserveLogin').is(':checked')){
			MCP.alert("로그인 유지 이용시, 개인정보 유출<br/>위험이 있을 수 있습니다.");
			$('#loginAuto').val('Y');
		} else {
			$('#loginAuto').val('N');
		}
	});

});

//디바이스 ID 요청값 셋팅
var setUuid = function (data){
	//alert("setUuid");
	//alert(data);
	var data = JSON.parse(data);
	//alert(data.uuid);
	if(data != undefined && data.uuid != undefined && data.uuid != ''){
		$('#uuid').val(data.uuid);

		var varData = ajaxCommon.getSerializedData({
			uuid : data.uuid,
			token : ''
		});

		ajaxCommon.getItem({
				id:'getAppUuidInfo'
				,cache:false
				,url:'/m/app/getUuidAppInfo.do'
				,data: varData
				,dataType:"json"
				,async:false
			}
			,function(data){

				if(data.resltCd == '00000'){
					if(data.bioLoginYn == 'Y'){
						if($('#phoneOs').val() == 'I'){
							window.webkit.messageHandlers.bioLogin.postMessage('{"returnFunc" : ""}');
						}else if($('#phoneOs').val() == 'A'){
							window.ktmmobile.bioLogin();
						}
					}else if(data.simpleLoginYn == 'Y'){
						if($('#phoneOs').val() == 'I'){
							window.webkit.messageHandlers.simpleLogin.postMessage('{"returnFunc" : ""}');
						}else if($('#phoneOs').val() == 'A'){
							window.ktmmobile.simpleLogin();
						}
					}
				}

			});

	}
}

var nextCheck = function (){
	if($.trim($('#userId').val()) != '' && $.trim($('#passWord').val()) != ''){
		$('#loginBtn').removeClass('is-disabled');
	}else{
		$('#loginBtn').addClass('is-disabled');
	}
}

/*

$(document).ready(function(){
	//input에 numberOnly = true 이면 숫자만 입력한다.
	/*
	$(document).on("keyup", "input:text[numberOnly]", function() {$(this).val( $(this).val().replace(/[^0-9]/gi,"") );});
	$('input, text').placeholder();

	$("#smsRcv").click(function(){
		if($("#name").val()==""){
			$("#name").focus();
			MCP.alert("성명을 입력해 주세요.");
			return false;
		}


		if(!($("#mobileNo").val().length == 10 || $("#mobileNo").val().length == 11)){
			$("#mobileNo").focus();
			MCP.alert("유효한 휴대폰번호를 입력해 주세요.");
			return false;
		}

        $.ajax({
            type : "POST",
            url : "/sendCertSmsAjax.do",
            data : $('#mainForm').serialize(),
            dataType : "json",
            contentType : "application/x-www-form-urlencoded; charset=UTF-8",
            async : true,
            beforeSend : function(){
    		},
            success : function(data) {
               	if(data.returnCode == "00") {
			//		$(".message2").text("고객님의 휴대폰으로 인증번호를 전송 하였습니다.");
			    	$("#smsRcv").html("인증번호 재전송");
			    	$("#certifyYn").val("Y");
			    	clearInterval(interval);
			    	dailyMissionTimer(3);//타이머 스타트

               	} else {
               		MCP.alert(data.message);
               	}
				return;
            },
   			error : function() {
 				MCP.alert("오류가 발생했습니다. 다시 시도해 주세요.");
 				return;
 			}

        });
    	return false;
	});

	changeCaptcha(); //Captcha Image 요청

	$('#reLoad').click(function(){ changeCaptcha(); }); //'새로고침'버튼의 Click 이벤트 발생시 'changeCaptcha()'호출
	$('#soundOn').click(function(){ audioCaptcha(); }); //'음성듣기'버튼의 Click 이벤트 발생시 'audioCaptcha()'호출
	$('#soundOnKor').click(function(){ audioCaptcha(); }); //한글음성듣기 버튼에 클릭이벤트 등록

	//'확인' 버튼 클릭시
	$('#frmSubmit').click(function(){
		if ( !$('#answer').val() ) {
			MCP.alert('이미지에 보이는 숫자 또는 스피커를 통해 들리는 숫자를 입력해 주세요.');
		} else {
			$.ajax({
				url: '/CaptchaSubmit.do',
				type: 'POST',
				dataType: 'text',
				data: 'answer=' + $('#answer').val(),
				async: false,
				success: function(resp) {
					MCP.alert(resp);
					$('#reLoad').click();
					$('#answer').val('');
				}
			});
		}
	});
});

function bimemLogin() {

	$("#userId").val('');
	$("#passWord").val('');
	$("#answer").val('');

	if($("#name").val()==""){
		$("#name").focus();
		MCP.alert("성명을 입력해 주세요.");
		return false;
	}

	if($("#mobileNo").val()==""){
		$("#mobileNo").focus();
		MCP.alert("휴대폰번호를 입력해 주세요.");
		return false;
	}

//	if(!($("#mobileNo").val().length == 10 || $("#mobileNo").val().length == 11)){
//		$("#mobileNo").focus();
//		MCP.alert("유효한 휴대폰번호를 입력해 주세요.");
//		return false;
//	}

	if($("#certifyYn").val() == "N"){
		MCP.alert("인증번호를 전송해 주세요.");
		return false;
	}

	if($("#authValue").val()==""){
		$("#authValue").focus();
		MCP.alert("인증번호를 입력해 주세요.");
		return false;
	}

	if($("#timer0").val()=="0"){
		MCP.alert("인증번호 유효시간이 지났습니다.\n\n 인증번호를 다시 받아주세요.");
		return false;
	}

	if(!($("#terms").is(":checked"))){
		MCP.alert("개인정보 처리방침의 '확인 후 동의'를 체크해 주세요.");
		return false;
	}

	$("#checkValue").val($("#authValue").val());
    $.ajax({
        type : "POST",
        url : "/checkCertSmsAjax.do",
        data : $('#mainForm').serialize(),
        dataType : "json",
        contentType : "application/x-www-form-urlencoded; charset=UTF-8",
        async : true,
        beforeSend : function(){
		},
        success : function(data) {
           	if(data.returnCode == "00") {
           		var k = document.referrer;   var a = k.split('ktmmobile.com');
				var ref = a[1];
           		if(ref == ''){
           			window.document.mainForm.action = "/m/main.do";
           		}else{
           			window.document.mainForm.action = ref;
           		}
           		window.document.mainForm.submit();
           	} else {
           		MCP.alert(data.message);
           	}
			return;
        },
			error : function() {
				MCP.alert("오류가 발생했습니다. 다시 시도해 주세요.");
				return;
			}

    });
	return false;
}

var interval ;

function dailyMissionTimer(duration) {

	$("#timeover").css("display","none");
    $("#countdown").css("display","block");
    $("#timer0").val("1");

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
            $("#countdown").css("display","none");
            $("#timeover").css("display","block");
            $("#timer0").val("0");

			return;
        }
    }, 1000);
}

var rand;
 * Captcha Image 요청
 * [주의] IE의 경우 CaptChaImg.jsp 호출시 매번 변하는 임의의 값(의미없는 값)을 파라미터로 전달하지 않으면
 * '새로고침'버튼을 클릭해도 CaptChaImg.jsp가 호출되지 않는다. 즉, 이미지가 변경되지 않는 문제가 발생한다.
 *  그러나 크롭의 경우에는 파라미터 전달 없이도 정상 호출된다.
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
		} else {
			var userAgent = navigator.userAgent.toLowerCase();
			if ((userAgent.search("iphone") > -1) || (userAgent.search("ipod") > -1) || (userAgent.search("ipad") > -1)){
				MCP.alert("보안문자 음성듣기 기능은 아이폰에서 지원되지 않습니다.");
			}else{
				MCP.alert("보안문자 음성듣기 기능은 \n\nInternet Explorer 9 이상부터 지원 합니다.");
			}
		}
	}




function winPlayer(objUrl) {
	$('#audiocatpch').html(' <bgsound src="' + objUrl + '">');
}


function changeCaptcha() {
	  //IE에서 '새로고침'버튼을 클릭시 CaptChaImg.jsp가 호출되지 않는 문제를 해결하기 위해 "?rand='+ Math.random()" 추가
		rand = new Date().getTime();
		$('#catpcha').html('<img src="/CaptChaImg.do?rand='+ rand + '"/>');
}

/*
function finalSubmit() {

	if($("input:checkbox[id='agree']").is(":checked") == false) {
		MCP.alert("개인정보처리방침에 동의 해주세요");
		return;
	}

	if(!$(":input:radio[name='gender']:checked").val()) {
		MCP.alert("성별을 선택해 주세요.");
		$("#gender").focus();
		return;
	}

// 	if($("#birthday").val().length != 8) {
//		MCP.alert("생일을 '19990101'형식으로 입력해 주세요.");
//		$("#birthday").focus();
//		return;
//	}

		param = $("#birthday").val();

        // 자리수가 맞지않을때
        if( isNaN(param) || param.length!=8 ) {
        	MCP.alert("생년월일을 '19990101'형식으로 입력해 주세요.");
            return false;
        }

        var year = Number(param.substring(0, 4));
        var month = Number(param.substring(4, 6));
        var day = Number(param.substring(6, 8));

        var dd = day / 0;

        var d = new Date();
    	var y = d.getFullYear();
        if( year < 1900 || year > y) {
        	MCP.alert("생년월일의 '년'부분이 부정확합니다.");
        	return false;
        }

        if( month<1 || month>12 ) {
        	MCP.alert("생년월일의 '월'부분이 부정확합니다.");
            return false;
        }

        var maxDaysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        var maxDay = maxDaysInMonth[month-1];

        // 윤년 체크
        if( month==2 && ( year%4==0 && year%100!=0 || year%400==0 ) ) {
            maxDay = 29;
        }

        if( day<=0 || day>maxDay ) {
        	MCP.alert("생년월일의 '일'부분이 부정확합니다.");
            return false;
        }
 //       return true;

    $.ajax({
        type : "POST",
        url : "/addBirthGenderAjax.do",
        data : $('#mainForm').serialize(),
        dataType : "json",
        contentType : "application/x-www-form-urlencoded; charset=UTF-8",
        async : true,
        beforeSend : function(){
		},
        success : function(data) {
           	if(data.returnCode == "00") {
           		fn_layoutClose($('.dim-layer'));	//레이어 팝업 닫기
           		goLoginProcess();

           	} else {
           		MCP.alert(data.message);
           	}
			return;
        },
			error : function() {
				MCP.alert("오류가 발생했습니다. 다시 시도해 주세요.");
				return;
			}

    });
}

function fn_layoutClose(obj){
	obj.fadeOut();
	$(document).find('html, body').css({
    	'overflow': 'auto',
    	'height': 'auto'
    });
}
*/

var deviceTest = function (){
	//디바이스 아이디 요청
	if($('#phoneOs').val() == 'I'){			//아이폰 접속
		window.webkit.messageHandlers.getDevideId.postMessage('{"returnFunc" : "setUuid"}');

	}else if($('#phoneOs').val() == 'A'){	//안드로이드 접속
		alert('device 버튼 클릭 안드로이드');
		var data = window.ktmmobile.getDevideId();
		setUuid(data);
	}
}

var bioLoginTest = function (){
	alert('바이오 버튼 클릭 안드로이드');
	if($('#phoneOs').val() == 'I'){			//아이폰 접속
		window.webkit.messageHandlers.bioLogin.postMessage('{"returnFunc" : ""}');
	}else{
		window.ktmmobile.bioLogin();
	}
}

var simpleLoginTest = function (){
	alert('간편 버튼 클릭 안드로이드');
	if($('#phoneOs').val() == 'I'){			//아이폰 접속
		window.webkit.messageHandlers.simpleLogin.postMessage('{"returnFunc" : ""}');
	}else{
		window.ktmmobile.simpleLogin();

	}
}

window.addEventListener("load", function(e) {
	//APP 접속
	if($('#platFormCd').val() == 'A'){

		//디바이스 아이디 요청
		if($('#phoneOs').val() == 'I'){			//아이폰 접속
			window.webkit.messageHandlers.getDevideId.postMessage('{"returnFunc" : "setUuid"}');
		}else if($('#phoneOs').val() == 'A'){	//안드로이드 접속
			var data = window.ktmmobile.getDevideId();
			setUuid(data);
		}
	}
});


var aleadySns = function (msg, rtnUrl){
	MCP.alert(msg, function (){
		location.href = rtnUrl;
	});
}


var displayM008BannerList = function() {
	var varData = ajaxCommon.getSerializedData({
		bannCtg : 'M008'
	});

	ajaxCommon.getItem({
			id : 'getM008BannerListAjax'
			, cache : false
			, async : true
			, url : '/m/bannerListAjax.do'
			, data : varData
			, dataType : "json"
		}
		,function(result){
			let bannerHtml = "";
			let returnCode = result.returnCode;
			let message = result.message;
			let bannerList = result.result;

			if(returnCode == "00") {
				if(bannerList.length > 0) {
					$.each(bannerList, function(index, value) {
						let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
						let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
						let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
						let v_bannImg = ajaxCommon.isNullNvl(value.mobileBannImgNm, "");
						let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
						let v_bannBgColor = ajaxCommon.isNullNvl(value.bgColor, "");
						let v_linkUrl = ajaxCommon.isNullNvl(value.mobileLinkUrl, "");
						let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");

						var linkData = {
							bannSeq : v_bannSeq
							, bannCtg : v_bannCtg
							, linkUrl : v_linkUrl
							, linkTarget : v_linkTarget
						};

						bannerHtml += "<div class='swiper-slide'>";
						bannerHtml += "    <a class='swiper-top-banner__item' href='#' onclick='addBannAccess("+JSON.stringify(linkData)+"); bannerLink("+JSON.stringify(linkData)+");'>";
						bannerHtml += "        <img src='"+v_bannImg+"' alt='"+v_imgDesc+"'>";
						bannerHtml += "    </a>";
						bannerHtml += "</div>";

						if(index == 98) {
							return false;
						}
					});
					$("#sec_M008").show();
					$("#M008").html(bannerHtml);
				} else {
					$("#sec_M008").hide();
				}
			} else {
				MCP.alert(message);
			}
		});
}

function fnNiceCert(obj) {
	niceAuthResult(obj)
}

function niceAuthResult(prarObj){

	var strMsg = "고객 정보와 본인인증한 정보가 틀립니다.";
	pageObj.niceResLogObj = prarObj;

	var reqSeq = prarObj.reqSeq;
	var resSeq = prarObj.resSeq;
	var menuType = "login";
	var userId = $('#userId').val()

	var authInfoJson ={contractNum: "", ncType: "0", menuType : menuType, userId : userId};
	var isAuthDone = diAuthCallback(authInfoJson);

	if(isAuthDone){
		pageObj.niceHistSeq = diAuthObj.resAuthOjb.NICE_HIST_SEQ ;
		var data = {resSeq:resSeq, reqSeq:reqSeq};
		if(diAuthObj.resAuthOjb.regularYn) {
			MCP.alert("고객님은 정회원으로 확인되었습니다. 다시 로그인을 진행해주세요.");
		} else {
			MCP.alert("인증에 성공했습니다. 다시 로그인을 진행해주세요.");
		}
		return null;
	}else{
		strMsg= (diAuthObj.resAuthOjb.RESULT_MSG == undefined) ? strMsg : diAuthObj.resAuthOjb.RESULT_MSG;
		MCP.alert(strMsg);
		return null;
	}
}