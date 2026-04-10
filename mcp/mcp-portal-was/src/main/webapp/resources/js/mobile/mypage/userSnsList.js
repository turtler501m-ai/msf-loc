/**
 * 
 */

$(document).ready(function(){


		ajaxCommon.getItem({
	        id:'userSnsListAjax'
	        ,cache:false
	        ,url:'/m/mypage/userSnsListAjax.do'
	        ,data: ''
	        ,dataType:"json"
	    }
	    ,function(data){
			console.log(data);
			for(var i = 0; i < data.length; i ++){
				if(data[i] == "NAVER"){
					$("#swtA1").prop("checked", true);
				}else if(data[i] == "FACEBOOK"){
					$("#swtA2").prop("checked", true);
				}else if(data[i] == "KAKAO"){
					$("#swtA3").prop("checked", true);
				}else if(data[i] == "APPLE"){
					$("#swtA4").prop("checked", true);
				}
			}
		});
});

window.fbAsyncInit = function() {
    FB.init({
        //appId   : '292838682458105',
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
		}else if(data.rsltCd == '1' || data.rsltCd == '2'){
			MCP.alert('애플 로그인이 취소되었습니다.', function (){
				location.href="/m/loginForm.do";
			});
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


$(".c-checkbox").on("change", function(){
	
	var key = $(this).attr("id");
	if(key == 'swtA1'){ // 네이버		
		if($(this).is(":checked")){

			
			new KTM.Confirm("소셜 계정을 연결하시겠습니까?", function(){
				this.close();
				$.ajax({
			        url: '/m/login/getNaverAuthUrl.do',
			        type: 'get',
			    }).done(function (res) {
					$("#naver_id_login_anchor").trigger("click");
					/*
			        var data = {width:'600', height:'700'};
					openPage('outLink2', res, data)
					*/
			    });
			});
			$(this).prop("checked", false);
		}else{
			new KTM.Confirm("소셜 계정 연결을 해제하시겠습니까?", function(){
				this.close();
				var varData = ajaxCommon.getSerializedData({
					snsCd:"NAVER"
			    });
		
				ajaxCommon.getItem({
			        id:'deleteUserSnsAjax'
			        ,cache:false
			        ,url:'/m/mypage/deleteUserSnsAjax.do'
			        ,data: varData
			        ,dataType:"json"
			    }
			    ,function(data){
					if(data.msg == "000"){
						MCP.alert("연동이 정상적으로 해지되었습니다.",function(){
							location.reload();
						});
					}else{
						MCP.alert("처리중 에러가 발생했습니다. 잠시후 다시 시도해 주세요.");
					}
				});
			});
			$(this).prop("checked", true);
		}
	}else if(key == 'swtA2'){ // 페이스북
		if($(this).is(":checked")){
			new KTM.Confirm("소셜 계정을 연결하시겠습니까?", function(){
				this.close();
				FB.login(function(response){
					
					fbLogin();
				});	
			});
			$(this).prop("checked", false);
		}else{
			new KTM.Confirm("소셜 계정 연결을 해제하시겠습니까?", function(){
				this.close();
				var varData = ajaxCommon.getSerializedData({
					snsCd:"FACEBOOK"
			    });
		
				ajaxCommon.getItem({
			        id:'deleteUserSnsAjax'
			        ,cache:false
			        ,url:'/m/mypage/deleteUserSnsAjax.do'
			        ,data: varData
			        ,dataType:"json"
			    }
			    ,function(data){
					if(data.msg == "000"){
						MCP.alert("연동이 정상적으로 해지되었습니다.",function(){
							location.reload();
						});
					}else{
						MCP.alert("처리중 에러가 발생했습니다. 잠시후 다시 시도해 주세요.");
					}
				});
			});
			$(this).prop("checked", true);
		}
	}else if(key == 'swtA4'){ // APPLE
		if($(this).is(":checked")){
			new KTM.Confirm("소셜 계정을 연결하시겠습니까?", function(){
				this.close();
				if($('#phoneOs').val() == 'I'){
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
			});
			$(this).prop("checked", false);
		}else{
			new KTM.Confirm("소셜 계정 연결을 해제하시겠습니까?", function(){
				this.close();
				var varData = ajaxCommon.getSerializedData({
					snsCd:"APPLE"
			    });
		
				ajaxCommon.getItem({
			        id:'deleteUserSnsAjax'
			        ,cache:false
			        ,url:'/m/mypage/deleteUserSnsAjax.do'
			        ,data: varData
			        ,dataType:"json"
			    }
			    ,function(data){
					if(data.msg == "000"){
						MCP.alert("연동이 정상적으로 해지되었습니다.",function(){
							location.reload();
						});
						
					}else{
						MCP.alert("처리중 에러가 발생했습니다. 잠시후 다시 시도해 주세요.");
					}
				});
			});
			$(this).prop("checked", true);
		}
	}else{ // 카카오
		if($(this).is(":checked")){
			new KTM.Confirm("소셜 계정을 연결하시겠습니까?", function(){
				this.close();
				$.ajax({
			        url: '/m/login/getKakaoAuthUrl.do',
			        type: 'get',
			    }).done(function (res) {
					
			        var data = {width:'600', height:'700'};
					openPage('outLink2', res, data)
			    });
			});
		$(this).prop("checked", false);
		}else{
			new KTM.Confirm("소셜 계정 연결을 해제하시겠습니까?", function(){
				this.close();
				var varData = ajaxCommon.getSerializedData({
					snsCd:"KAKAO"
			    });
		
				ajaxCommon.getItem({
			        id:'deleteUserSnsAjax'
			        ,cache:false
			        ,url:'/m/mypage/deleteUserSnsAjax.do'
			        ,data: varData
			        ,dataType:"json"
			    }
			    ,function(data){
					if(data.msg == "000"){
						MCP.alert("연동이 정상적으로 해지되었습니다.",function(){
							location.reload();
						});
						
					}else{
						MCP.alert("처리중 에러가 발생했습니다. 잠시후 다시 시도해 주세요.");
					}
				});
			});
			$(this).prop("checked", true);
		}
	}
});


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
