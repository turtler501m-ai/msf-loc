;

var naver_id_login;

$(document).ready(function(){

	var clientId = $("#clientId").val();
	var nearoCallBackUrl = $("#nearoCallBackUrl").val();
	naver_id_login = new naver_id_login(clientId, nearoCallBackUrl);
	naver_id_login.get_naver_userprofile("naverSignInCallback()");
});


function naverSignInCallback() {

	var access_token = naver_id_login.oauthParams.access_token;
	var expires_in = naver_id_login.oauthParams.expires_in;
	var name = naver_id_login.getProfileData('name');
	naverSignInJson(access_token,expires_in,name);
}

function naverSignInJson(access_token,expires_in,name){

	var varData = ajaxCommon.getSerializedData({
		accessToken:$.trim(access_token),
		platform : "MOBILE",
		name : name
	});

	ajaxCommon.getItem({
		id:'nearoCallBackJson'
		,cache:false
		,url:'/cert/nearoCallBackJson.do'
		,data: varData
		,dataType:"json"
		,timeout : expires_in*1000
	}
	,function(data){
		var rtnMsg = data.rtnMsg;
		var pollingPageUrl = data.pollingPageUrl;
		var errMsg = data.errMsg;
		if(rtnMsg!="success"){
			alert(errMsg);
			return false;
		} else {
			location.href = pollingPageUrl;
		}
	});
}




