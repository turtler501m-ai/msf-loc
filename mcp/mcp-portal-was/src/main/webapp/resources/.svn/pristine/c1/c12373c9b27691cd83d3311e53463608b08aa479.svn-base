;

var naver_id_login;

$(document).ready(function(){

    var clientId = $("#clientId").val();
    var nearoCallBackUrl = $("#nearoCallBackUrl").val();
    naver_id_login = new naver_id_login(clientId, nearoCallBackUrl);
    //naver_id_login.get_naver_userprofile("naverSignInCallback()");

    var access_token = naver_id_login.oauthParams.access_token;
    naverSignInJson(access_token);
});


function naverSignInCallback() {

    var access_token = naver_id_login.oauthParams.access_token;
    naverSignInJson(access_token);
}

function naverSignInJson(access_token){


    ajaxCommon.createForm({
        method:"post"
        ,action:"/cert/naverLoginCallBackJson.do"
    });

    ajaxCommon.attachHiddenElement("accessToken",$.trim(access_token));
    ajaxCommon.attachHiddenElement("platform", "PC");
    ajaxCommon.formSubmit();

    
}




