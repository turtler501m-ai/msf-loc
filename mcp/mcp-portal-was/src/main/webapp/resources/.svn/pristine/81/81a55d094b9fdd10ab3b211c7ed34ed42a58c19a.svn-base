
function bioLoginResult(data) {

	var data = JSON.parse(data);

	if(data != null && data != undefined) {

		if( data.rsltCd == '00000' && data.token != undefined && data.token != ''){

			var token = data.token;

			var varData = ajaxCommon.getSerializedData({
				token : token,
				loginDivCd : 'BIO'
			});

			ajaxCommon.getItem({
					id:'getAppUuidInfo'
					,cache:false
					,url:'/app/loginProcJson.do'
					,data: varData
					,dataType:"json"
					,async:false
				}
				,function(data){

					if(data.CODE == '0000'){
						goAppLoginProcess(data.newToken,'BIO');
					} else {
						if(data.CODE == '0002'){
							MCP.alert(data.ERRORDESC, function (){
								location.href='/m/login/dormantUserView.do';
							});
						}else{
							MCP.alert(data.ERRORDESC, function (){
								location.href="/m/set/appSettingView.do";
							});
						}
					}
				});
		} else if( data.rsltCd == '2' || data.rsltCd == '5'){
			var serverMsg = "생체인증에 실패하였습니다. 로그인 후 다시 설정해 주세요.";

			if( data.rsltCd == '5'){
				serverMsg = data.rsltMsg;
			}

			var varData = ajaxCommon.getSerializedData({
				bioLoginYn : 'N'
			});

			ajaxCommon.getItem({
					id:'setBioSet'
					,cache:false
					,url:'/m/set/updateAppSetAjax.do'
					,data: varData
					,dataType:"json"
					,async:false
				}
				,function(data){
					if(data.CODE == '0000'){
						//MCP.alert(serverMsg);
					} else {
						//MCP.alert(data.ERRORDESC);
						//serverMsg = data.ERRORDESC;
					}
					MCP.alert(serverMsg, function (){
						location.href="/m/loginForm.do";
					});
				});
		}
	}
}

function simpleLoginResult(data) {

	var data = JSON.parse(data);

	if(data != null && data != undefined) {

		if( data.rsltCd == '00000' && data.token != undefined && data.token != ''){

			var token = data.token;

			var varData = ajaxCommon.getSerializedData({
				token : token,
				loginDivCd : 'SIMP'
			});

			ajaxCommon.getItem({
					id:'getAppUuidInfo'
					,cache:false
					,url:'/app/loginProcJson.do'
					,data: varData
					,dataType:"json"
					,async:false
				}
				,function(data){

					if(data.CODE == '0000'){
						goAppLoginProcess(data.newToken,'SIMP');
					} else {
						if(data.CODE == '0002'){
							MCP.alert(data.ERRORDESC, function (){
								location.href='/m/login/dormantUserView.do';
							});
						}else{
							MCP.alert(data.ERRORDESC, function (){
								location.href="/m/set/appSettingView.do";
							});
						}
					}
				});
		} else if( data.rsltCd == '2'){
			var serverMsg = "간편비밀번호에 실패하였습니다. 로그인 후 다시 설정해 주세요.";
			var varData = ajaxCommon.getSerializedData({
				simpleLoginYn : 'N'
			});

			ajaxCommon.getItem({
					id:'setSimpleSet'
					,cache:false
					,url:'/m/set/updateAppSetAjax.do'
					,data: varData
					,dataType:"json"
					,async:false
				}
				,function(data){
					if(data.CODE == '0000'){
						//MCP.alert(serverMsg);
					} else {
						//MCP.alert(data.ERRORDESC);
						serverMsg = data.ERRORDESC;
					}
					MCP.alert(serverMsg, function (){
						location.href="/m/set/appSettingView.do";
					});
				});
		}
	}
}

function goAppLoginProcess(token, loginDivCd) {

	var varData = ajaxCommon.getSerializedData({
		token : token,
		loginDivCd : loginDivCd
	});

	ajaxCommon.getItem({
			id:'getAppUuidInfo'
			,cache:false
			,url:'/m/set/appNewAjax.do'
			,data: varData
			,dataType:"json"
			,async:false
		}
		,function(data){

			if(data.CODE == '0000'){

				if($('#platFormCd').val() == 'A' && $('#uuid').val() != undefined && $('#uuid').val() != ''){

					var varData = ajaxCommon.getSerializedData({
						uuid : $('#uuid').val(),
						token : '',
						loginDivCd : loginDivCd
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

							if(loginDivCd == 'AUTO'){
								if($('#phoneOs').val() == 'I'){
									window.webkit.messageHandlers.onAutoLoginResult.postMessage(JSON.stringify(json));
								}else if($('#phoneOs').val() == 'A'){
									window.ktmmobile.onAutoLoginResult(JSON.stringify(json));
								}
							} else {
								if($('#phoneOs').val() == 'I'){
									window.webkit.messageHandlers.onLoginResult.postMessage(JSON.stringify(json));
								}else if($('#phoneOs').val() == 'A'){
									window.ktmmobile.onLoginResult(JSON.stringify(json));
								}
							}
						});
				}

				if(loginDivCd != 'AUTO'){
					$('#token').val(token);
					$('#loginDivCd').val(loginDivCd);
					$('form[name=mainForm]').attr('action', '/m/loginProcess.do');
					$('form[name=mainForm]').submit();
				}

			} else {
				MCP.alert("로그인에 실패하였습니다. 다시 등록 필요합니다.");
			}
		});
}


// 설정용
function reSetBioLogin() {
	if($('#phoneOs').val() == 'I'){			//아이폰 접속
		window.webkit.messageHandlers.bioSetting.postMessage('{"returnFunc" : ""}');
	}else{
		window.ktmmobile.bioSetting();
	}
}

function resetSimpleLogin() {
	if($('#phoneOs').val() == 'I'){			//아이폰 접속
		window.webkit.messageHandlers.simpleSetting.postMessage('{"returnFunc" : ""}');
	}else{
		window.ktmmobile.simpleSetting();
	}
}

function sendYnSettingResult(data) {

	if(data != null && data != undefined) {

		var varData = ajaxCommon.getSerializedData({
			sendYn : data
			,sendYnReferer: 'appSettingView'
		});

		ajaxCommon.getItem({
				id:'setSendSet'
				,cache:false
				,url:'/m/set/updateAppSetAjax.do'
				,data: varData
				,dataType:"json"
				,async:false
			}
			,function(data){
				if(data.CODE == '0000'){
					MCP.alert("변경 되었습니다.");
					$("#sendYn").val(sendYn);
					var checkerInputEl = document.querySelector('#swtC1');
					if(sendYn == 'Y'){
						checkerInputEl.checked = true;
					} else {
						checkerInputEl.checked = false;
					}
				} else {
					MCP.alert(data.ERRORDESC);
				}
			});
	}
}

function bioSettingResult(data) {

	var data = JSON.parse(data);

	if(data.rsltCd != null && data.rsltCd != undefined) {

		var resetData = data.rsltCd;

		if(resetData == '00000'){
			resetData = 'Y';
		}

		if(resetData == 'Y' || resetData == 'N'){
			var varData = ajaxCommon.getSerializedData({
				bioLoginYn : resetData
			});

			ajaxCommon.getItem({
					id:'setBioSet'
					,cache:false
					,url:'/m/set/updateAppSetAjax.do'
					,data: varData
					,dataType:"json"
					,async:false
				}
				,function(data){
					if(data.CODE == '0000'){
						MCP.alert("변경 되었습니다.", function (){
							location.href="/m/set/appSettingView.do";
						});
					} else {
						MCP.alert(data.ERRORDESC);
					}
				});
		} else if(resetData == '2') {
			var erMsg = "변경에 실패했습니다.";
			MCP.alert(erMsg, function (){
				location.href="/m/set/appSettingView.do";
			});
		} else if(resetData == '3'){
			var erMsg = "생체 정보가 등록되지 않았습니다.";
			MCP.alert(erMsg, function (){
				location.href="/m/set/appSettingView.do";
			});
		} else if(resetData == '4'){
			var erMsg = "생체 인증 미지원 단말기 입니다.";
			MCP.alert(erMsg, function (){
				location.href="/m/set/appSettingView.do";
			});
		} else if(resetData == '5'){
			var erMsg = data.rsltMsg;
			MCP.alert(erMsg, function (){
				location.href="/m/set/appSettingView.do";
			});
		}
	}
}

function simpleSettingResult(data) {

	var data = JSON.parse(data);

	if(data.rsltCd != null && data.rsltCd != undefined) {

		var resetData = data.rsltCd;
		if(resetData == '00000'){
			resetData = 'Y';
		}

		if(resetData == 'Y' || resetData == 'N'){
			var varData = ajaxCommon.getSerializedData({
				simpleLoginYn : resetData
			});

			ajaxCommon.getItem({
					id:'setSimpleSet'
					,cache:false
					,url:'/m/set/updateAppSetAjax.do'
					,data: varData
					,dataType:"json"
					,async:false
				}
				,function(data){
					if(data.CODE == '0000'){
						MCP.alert("변경 되었습니다.", function (){
							location.href="/m/set/appSettingView.do";
						});
					} else {
						MCP.alert(data.ERRORDESC);
					}
				});
		} else if(resetData == '2') {
			var erMsg = "변경에 실패했습니다.";
			MCP.alert(erMsg, function (){
				location.href="/m/set/appSettingView.do";
			});
		}
	}
}

function setAppSetCheck(type){

	ajaxCommon.createForm({
		method:"post"
		,action:"/m/set/appSimpleCheckView.do"
	});
	ajaxCommon.attachHiddenElement("loginType",type);
	ajaxCommon.formSubmit();
}

function setAppUuid(data) {

	var data = JSON.parse(data);

	if(data != undefined && data.uuid != undefined && data.uuid != ''){
		appUuid = data.uuid;
	}

	ajaxCommon.createForm({
		method:"post"
		,action:"/m/set/appSettingView.do"
	});
	ajaxCommon.attachHiddenElement("appUuid",appUuid);
	ajaxCommon.formSubmit();
}

function appSettingView() {
	//디바이스 아이디 요청
	if($('#phoneOs').val() == 'I'){			//아이폰 접속
		window.webkit.messageHandlers.getDevideId.postMessage('{"returnFunc" : "setAppUuid"}');
	}else if($('#phoneOs').val() == 'A'){	//안드로이드 접속
		var data = window.ktmmobile.getDevideId();
		setAppUuid(data);
	}
}

function appOutSideOpen(openUrl) {
	var senddata = '{"url":"'+openUrl+'"}';
	if($('#phoneOs').val() == 'I'){
		window.webkit.messageHandlers.appOutSideOpen.postMessage(senddata);
	}else if($('#phoneOs').val() == 'A'){
		window.ktmmobile.appOutSideOpen(senddata);
	}
}

function requestPermission(whichPerm, reqCode) {

	if(whichPerm == null || whichPerm == undefined || whichPerm == '') {
		MCP.alert("입력 값을 확인하세요.");
		return false;
	}
	if(reqCode == null || reqCode == undefined || reqCode == '') {
		MCP.alert("입력 값을 확인하세요.");
		return false;
	}

	var senddata = '{"type":"'+whichPerm+'","requestCode":"'+reqCode+'"}';
	if($('#phoneOs').val() == 'I'){
		window.webkit.messageHandlers.requestPermission.postMessage(senddata);
	}else if($('#phoneOs').val() == 'A'){
		window.ktmmobile.requestPermission(senddata);
	}
}

function onPermissionResult(data) {

	var data = JSON.parse(data);

	if(data.type != null && data.type != undefined) {
		if(data.requestCode != null && data.requestCode != undefined) {
			if(data.status != null && data.status != undefined) {
				if(data.status == 'Y') {
					if(data.type == 'PIC'){
						if(data.requestCode == 'review') {
							key = true;
							review();
						} else if(data.requestCode == 'XX2') {
							xx2();

						}
					}
					if(data.type == 'LOC'){
						if(data.requestCode == 'store') {
							key = true;
							store();
						} else if(data.requestCode == 'XX2') {
							xx2();

						}
					}
				} else {
					MCP.alert("권한이 설정되어있지 않습니다.");
				}
			}
		}
	}
}


function setLoginToken(data) {

	var data = JSON.parse(data);

	if(data != undefined && data.token != undefined && data.token != ''){
		var token = data.token;

		$('#uuid').val(data.uuid);

		var varData = ajaxCommon.getSerializedData({
			token : token,
			loginDivCd : 'AUTO'
		});

		ajaxCommon.getItem({
				id:'getAppUuidInfo'
				,cache:false
				,url:'/app/loginProcJson.do'
				,data: varData
				,dataType:"json"
				,async:false
			}
			,function(data){
				if(data.CODE == '0000'){
					goAppLoginProcess(data.newToken,'AUTO');
				} else {
					if($('#phoneOs').val() == 'I'){
						window.webkit.messageHandlers.onAutoLoginResult.postMessage(JSON.stringify(data));
					}else if($('#phoneOs').val() == 'A'){
						window.ktmmobile.onAutoLoginResult(JSON.stringify(data));
					}
				}
			});
	} else {
		var senddata = '{"CODE": "1001"}';
		if($('#phoneOs').val() == 'I'){
			window.webkit.messageHandlers.onAutoLoginResult.postMessage(senddata);
		}else if($('#phoneOs').val() == 'A'){
			window.ktmmobile.onAutoLoginResult(senddata);
		}
	}
}

function getLoginToken() {
	//디바이스 아이디 요청
	if($('#phoneOs').val() == 'I'){
		window.webkit.messageHandlers.getLoginToken.postMessage('{"returnFunc" : "setLoginToken"}');
	}else if($('#phoneOs').val() == 'A'){	//안드로이드 접속
		var data = window.ktmmobile.getLoginToken();
		setLoginToken(data);
	}
}

window.addEventListener("load", function(e) {
	//console.log("load appCommonNew");
});
