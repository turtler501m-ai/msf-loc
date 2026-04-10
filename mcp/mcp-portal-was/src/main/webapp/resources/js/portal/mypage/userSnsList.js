/**
 * 
 */
var key;
var isChg = false;
$(document).ready(function(){


		ajaxCommon.getItem({
	        id:'userSnsListAjax'
	        ,cache:false
	        ,url:'/mypage/userSnsListAjax.do'
	        ,data: ''
	        ,dataType:"json"
	    }
	    ,function(data){

			for(var i = 0; i < data.length; i ++){
				if(data[i] == "NAVER"){
					$("#switch1").addClass("is-active");
				}else if(data[i] == "FACEBOOK"){
					$("#switch2").addClass("is-active");
				}else if(data[i] == "KAKAO"){
					$("#switch3").addClass("is-active");
				}else if(data[i] == "APPLE"){
					$("#switch4").addClass("is-active");
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




$(".c-button--switch").on("click", function(){
	
	key = $(this).attr("id");
	if(key == 'switch1'){ // 네이버		
		if(!$(this).hasClass("is-active")){

			
			KTM.Confirm("소셜 계정을 연결하시겠습니까?", function(){
				this.close();
				
				$.ajax({
			        url: '/login/getNaverAuthUrl.do',
			        type: 'get',
			    }).done(function (res) {
					$("#naver_id_login_anchor").trigger("click");
					/*
					MCP.alert("정상적으로 연결되었습니다.", function(){
						isChg = true;
						$(".is-active .c-button--gray").trigger("click");
					})
					var data = {width:'600', height:'700'};
					openPage('outLink2', res, data)
					*/
			    });
			    
			});
		}else{
			 KTM.Confirm("소셜 계정 연결을 해제하시겠습니까?", function(){
				this.close();
				var varData = ajaxCommon.getSerializedData({
					snsCd:"NAVER"
			    });
		
				ajaxCommon.getItem({
			        id:'deleteUserSnsAjax'
			        ,cache:false
			        ,url:'/mypage/deleteUserSnsAjax.do'
			        ,data: varData
			        ,dataType:"json"
			    }
			    ,function(data){

					if(data.msg == "000"){
						MCP.alert("연동이 정상적으로 해지되었습니다.",function(){
							isChg = true;
							$("#switch1").removeClass("is-active");
						});
						
					}else{
						MCP.alert("처리중 에러가 발생했습니다. 잠시후 다시 시도해 주세요.",function(){
							$("#switch1").removeClass("is-active");
						});
					}
				});
			});
		}
	}else if(key == 'switch2'){ // 페이스북
		if(!$(this).hasClass("is-active")){
			new KTM.Confirm("소셜 계정을 연결하시겠습니까?", function(){
				this.close();
				FB.login(function(response){
					fbLogin();
				});	
			});
		}else{
			new KTM.Confirm("소셜 계정 연결을 해제하시겠습니까?", function(){
				this.close();
				var varData = ajaxCommon.getSerializedData({
					snsCd:"FACEBOOK"
			    });
		
				ajaxCommon.getItem({
			        id:'deleteUserSnsAjax'
			        ,cache:false
			        ,url:'/mypage/deleteUserSnsAjax.do'
			        ,data: varData
			        ,dataType:"json"
			    }
			    ,function(data){
					if(data.msg == "000"){
						MCP.alert("연동이 정상적으로 해지되었습니다.",function(){
							isChg = true;
							$("#switch2").removeClass("is-active");
						});
						
					}else{
						MCP.alert("처리중 에러가 발생했습니다. 잠시후 다시 시도해 주세요.",function(){
							$("#switch2").removeClass("is-active");
						});
					}
				});
			});
		}
	}else if(key == 'switch4'){ // 애플
		if(!$(this).hasClass("is-active")){
			$.ajax({
				url: '/login/getAppleAuthUrl.do',
				type: 'get',
				async: false,
				dataType: 'text',
				success: function(res) {
					var data = {width:'600', height:'700'};
					openPage('outLink3', res, data)
				}
			});
		}else{
			new KTM.Confirm("소셜 계정 연결을 해제하시겠습니까?", function(){
				this.close();
				var varData = ajaxCommon.getSerializedData({
					snsCd:"APPLE"
			    });
		
				ajaxCommon.getItem({
			        id:'deleteUserSnsAjax'
			        ,cache:false
			        ,url:'/mypage/deleteUserSnsAjax.do'
			        ,data: varData
			        ,dataType:"json"
			    }
			    ,function(data){
					if(data.msg == "000"){
						MCP.alert("연동이 정상적으로 해지되었습니다.",function(){
							isChg = true;
							$("#switch4").removeClass("is-active");
						});
						
					}else{
						MCP.alert("처리중 에러가 발생했습니다. 잠시후 다시 시도해 주세요.",function(){
							$("#switch4").removeClass("is-active");
						});
					}
				});
			});
		}
	}else{ // 카카오
		if(!$(this).hasClass("is-active")){
			new KTM.Confirm("소셜 계정을 연결하시겠습니까?", function(){
				this.close();
				$.ajax({
			        url: '/login/getKakaoAuthUrl.do',
			        type: 'get',
			    }).done(function (res) {
	
					/*MCP.alert("정상적으로 연결되었습니다.", function(){
						isChg = true;
						$(".is-active .c-button--gray").trigger("click");
					})*/
					var data = {width:'600', height:'700'};
					openPage('outLink2', res, data)
			    });
			});
		}else{
			new KTM.Confirm("소셜 계정 연결을 해제하시겠습니까?", function(){
				this.close();
				var varData = ajaxCommon.getSerializedData({
					snsCd:"KAKAO"
			    });
		
				ajaxCommon.getItem({
			        id:'deleteUserSnsAjax'
			        ,cache:false
			        ,url:'/mypage/deleteUserSnsAjax.do'
			        ,data: varData
			        ,dataType:"json"
			    }
			    ,function(data){

					if(data.msg == "000"){
						MCP.alert("연동이 정상적으로 해지되었습니다.",function(){
							isChg = true;
							$("#switch3").removeClass("is-active");
						});
					}else{
						MCP.alert("처리중 에러가 발생했습니다. 잠시후 다시 시도해 주세요.",function(){
							$("#switch3").removeClass("is-active");
						});
					}
				});
			});
		}
	}
	
	$(".is-active .c-button--gray").on("click", function(){

		if(!isChg){
			if($("#" + key).is(":checked")){
				$("#" + key).prop("checked", false);
			}else{
				$("#" + key).prop("checked", true);
			}
		}
		isChg = false;
	})
});


function fbLogin() {
    FB.getLoginStatus(function(response) {

        if (response.status === 'connected') {
            FB.api('/me', function(res) {

				if(res.id != '' && res.id != undefined){
					var newForm = $('<form></form>');
					newForm.attr('name','newForm');
					newForm.attr('method','post');
					newForm.attr('action','/login/facebookCalbkUrl.do');
					newForm.append($('<input/>', {type: 'hidden', name: 'snsCd', value:'FACEBOOK' }));
					newForm.append($('<input/>', {type: 'hidden', name: 'snsId', value:res.id }));
					newForm.appendTo('body');
					newForm.submit();
				}
            });
            isChg = true;
            $(".is-active .c-button--gray").trigger("click");
        } else if (response.status === 'not_authorized') {
            MCP.alert('앱에 로그인해야 이용가능한 기능입니다.', function(){
				/*$(".is-active .c-button--gray").trigger("click");*/
				/*$("#switch2").prop("checked", false);*/
				$("#switch2").removeClass("is-active");
			});
        } else {
            MCP.alert('페이스북에 로그인해야 이용가능한 기능입니다.', function(){
				/*$(".is-active .c-button--gray").trigger("click");*/
				/*$("#switch2").prop("checked", false);*/
				$("#switch2").removeClass("is-active");
			});
        }
    }, false);
}
