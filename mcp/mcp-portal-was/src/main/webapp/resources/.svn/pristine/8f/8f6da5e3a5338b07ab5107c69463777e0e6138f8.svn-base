//##############################################################################################
// APPLE 로그인 버튼 클릭
function loginWithApple() {
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
}

// 네이버 로그인 버튼 클릭
function loginWithNaver() {
    $("#naver_id_login_anchor").trigger("click");
    /*
    $.ajax({
        url: '/login/getNaverAuthUrl.do',
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
        url: '/login/getKakaoAuthUrl.do',
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
                    newForm.attr('action','/login/facebookCalbkUrl.do');
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
                goLoginProcess();	// 통과하고 로그인시도함
            } else if(data.returnCode == '40') {
                //생일,성별 입력 레이어
                $('#userId').val($('#userId').val().trim());
                $('form[name=mainForm]').attr('action', '/addBirthGenderView.do');
                $('form[name=mainForm]').submit();
            } else if(data.returnCode == '42') {
                MCP.alert('아이디나 비밀번호가 정확하지 않습니다.</br>비밀번호 5회 이상 오류 시 로그인이 제한되며,</br>비밀번호 찾기 후 로그인이 가능합니다.');
                $('#passWord').val('');
                pageObj.isSend = false ;
            } else if(data.returnCode == '43') {
                pageObj.isSend = false ;
                MCP.alert('로그인에 5회 이상 실패하였습니다.</br>비밀번호 찾기 후 다시 로그인해 주세요.', function (){
                    location.href ='/findPassword.do?tabId=p';
                });
            }  else if(data.returnCode == '98') {
                pageObj.isSend = false ;
                MCP.alert('단시간 반복된 서비스 호출로</br>로그인이 제한됩니다.</br>잠시 후 이용 바랍니다.', function (){
                    location.href ='/main.do';
                });
            }  else if(data.returnCode == '99') {
                pageObj.isSend = false ;
                MCP.alert('비정상적인 로그인 시도가 탐지되어</br> 비밀번호 변경 후 서비스 이용이 가능합니다.', function (){
                    location.href ='/findPassword.do?tabId=p';
                });
            } else if(data.returnCode == "50") {
                pageObj.isSend = false ;
                MCP.alert('해당 계정은 비밀번호 유출의심 신고(DarkWeb)되어, 본인여부 확인 및 비밀번호 변경 후</br> 로그인 및 서비스 이용이 가능합니다.</br></br>고객센터: 1899-5000', function (){
                    location.href ="/findPassword.do?tabId=p";
                });
            } else if(data.returnCode == "20"){		//휴면 계정 관련 추가
                pageObj.isSend = false ;
                MCP.alert('휴면 계정으로 확인 되어 </br>휴면 해제 진행페이지로 이동 합니다. ', function (){
                    location.href ="/login/dormantUserView.do";
                });
            } else if(data.returnCode == "96" || data.returnCode == "80"){  // 리캡챠 관련 추가, 본인인증 추가
                pageObj.isSend = false;
                MCP.alert(data.message, function(){
                    //var parameterData = ajaxCommon.getSerializedData({menuType : "login"});
                    //openPage('pullPopup','/sms/smsAuthInfoPop.do', parameterData, '1');
                    pageObj.niceType = NICE_TYPE.CUST_AUTH ;
                    var data = {width:'500', height:'700'};
                    openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M', data);
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
    }); // end of ajax-----------------------------
}

function goLoginProcess() {
    KTM.LoadingSpinner.show();
    $('#userId').val($('#userId').val().trim());
    $('form[name=mainForm]').attr('action', '/loginProcess.do');
    $('form[name=mainForm]').submit();
}

window.addEventListener("load", function(e) {
    $(document).on("keydown", "#userId,#passWord", function(e) {
        if ($.trim($('#userId').val()).length > 0 && $.trim($('#passWord').val()).length > 0 && e.keyCode == 13) {
            goLogin();
        }
    });
});

$(document).ready(function(){

    // 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
    var userInputId = getCookie('userInputId');
    $('input[name=userId]').val(userInputId);

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

    }

});

var nextCheck = function (){
    if($.trim($('#userId').val()) != '' && $.trim($('#passWord').val()) != ''){
        $('#loginBtn').removeAttr('disabled');
    }else{
        $('#loginBtn').attr('disabled', 'disabled');
    }
}


var aleadySns = function (msg, rtnUrl){
    MCP.alert(msg, function (){
        location.href = rtnUrl;
    });
}

//nice 인증 완료 후 return
function fnNiceCert(obj) {
    niceAuthResult(obj)
}

function niceAuthResult(prarObj){

    var strMsg = "고객 정보와 본인인증한 정보가 틀립니다.";
    pageObj.niceResLogObj = prarObj;

    var reqSeq = prarObj.reqSeq;
    var resSeq = prarObj.resSeq;
    var menuType = "login";
    var userId = $('#userId').val();

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