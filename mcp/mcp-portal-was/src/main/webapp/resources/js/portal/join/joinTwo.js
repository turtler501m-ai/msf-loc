/*history.pushState(null, null,location.href);
    window.onpopstate = function (event){
        history.go("/loginForm.do");
 }*/

var pageObj = {
     authBirthDate:""
    , niceAgentHistSeq:0
};

$(window).load(function (){


    if($('#snsMobileNo').val() != undefined && $('#snsMobileNo').val() != ''){
        $('#mobileNo').val($('#snsMobileNo').val());
        $('#mobileNo').parent().addClass('has-value');
    }

    if($('#snsEmail').val() != undefined && $('#snsEmail').val() != ''){
        $('#email').val($('#snsEmail').val());
        $('#email').parent().addClass('has-value');
    }
});
$(document).ready(function() {

    if ($('#snsAddYn').val() == 'Y') {
        $('#inputForm').attr('action', '/join/thirdPage.do');
        $('#inputForm').submit();
    } else {
        $('#main-content').show();
    }

    /* 입력값 초기화 */
    $("#userId ").val('');
    $("#isPushBtn").val("N");
    //$("#mobileNo").val(''); 2025-01-21 인증한 휴대폰 번호로 입력
    $("#email").val('');



    /* 아이디 중복확인 클릭 */
    $("#dupleCheck").on("click", function() {
        /* 아이디 입력 여부 검사 */
        if (!inputIdChk()) return;
        /* 한글 및 특문, 영문대문자 체크 */
        if (!valicheckEngNum()) return;
        /* 아이디 길이 검사 ( 4 ~ 12 ) */
        if (!idLengthChk()) return;

        //$("#use_ok").text('사용 가능한 아이디 입니다.');
        $("#isPushBtn").val("Y");

        /* 아이디 중복 체크 */
        var url = "/join/idDuplicatedCheckAjax.do";
        var inputId = $("#userId").val();
        $.ajax({
            type: "POST",
            url: url,
            dataType: "text",
            data: {
                checkId: inputId
            },
            success: function(data) {
                if (data == '0') {
                    $("#use_ok").text('사용 가능한 아이디 입니다.');
                    $('#idCheckDiv').removeClass('has-error');
                    $('#idCheckDiv').addClass('has-safe');
                    MCP.alert('사용 가능한 아이디 입니다.');
                } else {
                    $("#use_ok").text('사용 불가능한 아이디 입니다.');
                    $('#idCheckDiv').addClass('has-error');
                    $('#idCheckDiv').removeClass('has-safe');
                    $("#userId").focus();
                    $("#isPushBtn").val("N");
                    MCP.alert('사용 불가능한 아이디 입니다.');
                }
                nextStepChk();
                return;
            }
        });

    });

    $('#userId').on('keydown', function(e) {
        if(e.keyCode != 9){
            $("#isPushBtn").val('N');
            $("#use_ok").text('');
            $('#idCheckDiv').removeClass('has-error');
            $('#idCheckDiv').removeClass('has-safe');
        }
        $('#userId').val($('#userId').val().replace(/[^a-z0-9]/gi,""));
        nextStepChk();

        return;
    });

    /* 입력 완료 버튼 클릭 */
    $("#btn_submit").on("click", function() {

        /* 중복 확인 버튼 */
        if ($("#isPushBtn").val() == 'N') {
            MCP.alert("아이디 중복확인을 확인해 주세요.", function (){
                $('#userId').focus();
            });
            return false;
        }



        if ($.trim($('#password').val()) == "") {
            MCP.alert("비밀번호를 입력해 주세요.", function (){
                $('#password').focus();
            });
            return false;
        }

        var pwdOneChkText = $("#pwdOneChk").text();
        if (pwdOneChkText != '사용 가능한 비밀번호입니다.') {
           MCP.alert(pwdOneChkText, function (){
               $('#password').focus();
           });
            return false;
        }


        if ($.trim($('#passwordChk').val()) == "") {
            MCP.alert("비밀번호 확인을 입력해 주세요.", function (){
                $('#passwordChk').focus();
            });
            return false;
        }

        var pwdSndChkText = $("#pwdSndChk").text();
        if (pwdSndChkText != '비밀번호가 일치 합니다.') {
            MCP.alert(pwdSndChkText, function (){
                $('#passwordChk').focus();
            });
            return false;
        }


        if ($.trim($('#mobileNo').val()) == '') {
            MCP.alert('휴대폰은 11자리로 입력해 주세요.', function (){
                $('#mobileNo').focus();
            });
            return false;
        }

        if ($.trim($('#mobileNo').val()) == '' || $.trim($('#mobileNo').val()).length != 11) {
            MCP.alert('휴대폰은 11자리로 입력해 주세요.', function (){
                $('#mobileNo').focus();
            });
            return false;
        }

        if ($.trim($("#email").val()) == '') {
            MCP.alert("이메일을 입력해 주세요.", function (){
                $("#email").focus();
            });
            return false;
        }

        if (!$("#email").val().match(/[0-9a-zA-Z][_0-9a-zA-Z-]*@[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+){1,2}$/)) {
            $('#error4').text('형식에 맞지 않는 이메일입니다.');
            $('#email').focus();
            MCP.alert('형식에 맞지 않는 이메일입니다.');
            return false;
        }



        //14세 미만 미성년자 법정대리인 동의
        if ($.trim($('#checkKid').val()) == "Y") {
            if ($.trim($('#isAuthAgent').val()) != "1") {
                MCP.alert("법정대리인 SMS인증 하시기 바랍니다.", function (){
                    $('#btnAgentAut').focus();
                });
                return false;
            }
            if ($.trim($('#minorAgentAgree').val()) != "Y") {
                MCP.alert("법정대리인 미성년자 개인정보 수집이용동의 하시기 바랍니다.", function (){
                    $('#minorAgentAgreeCheck').focus();
                });
                return false;
            }
        }

        KTM.Confirm('회원가입하시겠습니까?', function (){
            $("#minorAgentName").val($("#inputMinorAgentName").val());
            $('#inputForm').attr('action', "/join/joinAction.do");
            $('#inputForm').submit();
            this.close();
        });

    });

    $("#btnAgentAut").click(function() {

        if ($("#isAuthAgent").val() == "1") {
            MCP.alert("인증를 완료 하였습니다.");
            return false;
        }

        if ($.trim($('#inputMinorAgentName').val()) == "") {
            MCP.alert("법정대리인 성명을 입력 하시기 바랍니다. ", function (){
                $('#inputMinorAgentName').focus();
            });
            return false;
        }

        pageObj.authBirthDate = Date.now();

        ajaxCommon.getItem({
            id:'getTimeInMillisAjax'
            ,cache:false
            ,url:'/nice/getTimeInMillisAjax.do'
            ,data: ""
            ,dataType:"json"
            ,async:false
        }
        ,function(startTime){
            pageObj.startTime = startTime ;
        });

        pageObj.niceType = 'agentAuth';
        var data = {width:'500', height:'700'};
        openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M&sCheck=agentAuth&name='+$.trim($("#inputMinorAgentName").val())+'&birthDate='+pageObj.authBirthDate,data);
        return;
    });

    $('#minorAgentAgreeCheck').on('click', function (){
        if (!$("#minorAgentAgreeCheck").is(":checked")) {
            $('#minorAgentAgree').val("N")
        }
        else{
            $('#minorAgentAgree').val("Y")
        }
    });

});

function fnNiceCert(obj) {

    var strMsg = "고객 정보와 본인인증한 정보가 틀립니다.";
    pageObj.niceResLogObj = obj;

    var authInfoJson ={cstmrName: $.trim($("#inputMinorAgentName").val()), contractNum: "", ncType: "1"};
    var isAuthDone = diAuthCallback(authInfoJson);

    if(isAuthDone){
        pageObj.niceAgentHistSeq = diAuthObj.resAuthOjb.NICE_HIST_SEQ;
        $("#niceAgentHistSeq").val(pageObj.niceAgentHistSeq);
        $("#isAuthAgent").val("1");
        $("#minorAgentName").val($("#inputMinorAgentName").val());
        $("#inputMinorAgentName").attr('readonly', 'true');
        nextStepChk();
    }else{

        strMsg= (diAuthObj.resAuthOjb.RESULT_MSG == undefined) ? strMsg : diAuthObj.resAuthOjb.RESULT_MSG;
        MCP.alert(strMsg);
        return null;
    }

}

var mobileNoChk = function (){
    if($.trim($('#mobileNo').val()).length == 0){
        $('#mobileNoCheckDiv').removeClass('has-safe');
        $('#mobileNoCheckDiv').removeClass('has-error');
        $('#error3').text('');
    }else if($.trim($('#mobileNo').val()).length != 11){
        $('#mobileNoCheckDiv').removeClass('has-safe');
        $('#mobileNoCheckDiv').addClass('has-error');
        $('#error3').text('휴대폰번호를 정확히 입력해 주세요.');
    }else{
        $('#mobileNoCheckDiv').removeClass('has-safe');
        $('#mobileNoCheckDiv').removeClass('has-error');
        $('#error3').text('');

    }
}
var nextStepChk = function (){
    if( $("#isPushBtn").val() == 'Y' && $('#isChkPw').val() == 'Y' && $.trim($('#mobileNo').val()).length == 11  && $('#error4').text() == '형식이 일치합니다.' ){
        if ($.trim($('#checkKid').val()) == "Y") {
            if ($.trim($('#isAuthAgent').val()) != "1") {
                $('#btn_submit').addClass('is-disabled');
            }else{
                $('#btn_submit').removeClass('is-disabled');
            }
        }else{
            $('#btn_submit').removeClass('is-disabled');
        }
    }else{
        $('#btn_submit').addClass('is-disabled');
    }
}

