var pageObj = {
     authBirthDate:""
};


$(document).ready(function () {

    $('input[type="checkbox"][name="chkInterest"]').click(function() {

        if($(this).prop('checked')){
            $('input[type="checkbox"][name="chkInterest"]').prop('checked',false);
            $(this).prop('checked',true);
        }
    });


     if($('#checkKid').val() == 'N'){
         $("#isAuthAgent").val(1);
     }

    setTimeout(function () {

        if($('#snsAddYn').val() == 'Y'){

            MCP.alert("본 이벤트는 신규 가입 회원만 참여 가능합니다.",function(){
                $('#promoClose').trigger('click');
            });
        }

          }, 300);

    $('#checkAll').on('click', function (){
        if($(this).prop('checked')){
            $('input[name=terms]').prop('checked', 'checked');
            $('input[name=terms]').addClass('btn_agree_active');
            $('input[name=terms]').removeClass('btn_agree');
        }else{
            $('input[name=terms]').removeProp('checked');
            $('input[name=terms]').addClass('btn_agree');
            $('input[name=terms]').removeClass('btn_agree_active');
        }
        setChkbox();
    });

    /* 입력값 초기화 */
    $("#userId ").val('');
    $("#isPushBtn").val("N");
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
        }


        if (!$("[name='chkInterest']").is(":checked")) {
            MCP.alert("관심사를 선택해 주시길 바랍니다.");
            $("[name='chkInterest']").focus();
            return false;
        }


        if(!validationChk()) return;

        KTM.Confirm('회원가입하시겠습니까?', function (){
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

        if ($.trim($('#minorAgentName').val()) == "") {
            MCP.alert("법정대리인 성명을 입력 하시기 바랍니다. ", function (){
                $('#minorAgentName').focus();
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
        openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M&sCheck=agentAuth&name='+$.trim($("#minorAgentName").val())+'&birthDate='+pageObj.authBirthDate,data);
        return false;
    });

});


function fnNiceCert(obj) {
    niceAuthResult(obj)
}


function niceAuthResult(prarObj){

   var strMsg = "고객 정보와 본인인증한 정보가 틀립니다.";
   pageObj.niceResLogObj = prarObj;

   var authInfoJson ={cstmrName: $.trim($("#minorAgentName").val()), contractNum: "", ncType: "1"};
   var isAuthDone = diAuthCallback(authInfoJson);

   if(isAuthDone){
       pageObj.niceAgentHistSeq = diAuthObj.resAuthOjb.NICE_HIST_SEQ;
       $("#niceAgentHistSeq").val(pageObj.niceAgentHistSeq);
       $("#isAuthAgent").val(1);
       $("#minorAgentName").attr('readonly', 'true');
       nextStepChk();
   }else{

       strMsg= (diAuthObj.resAuthOjb.RESULT_MSG == undefined) ? strMsg : diAuthObj.resAuthOjb.RESULT_MSG;
       MCP.alert(strMsg);
       return null;
   }
}


//약관동의
function viewTerms(targetId,param){
    $('#targetTerms').val(targetId);
    openPage('termsPop','/termsPop.do',param);
}


function setChkbox(data) {
    var cnt = 0;
    if (data != undefined ) {
        if ($(data).attr('inheritance') != '' && $(data).attr('inheritance') != undefined) {
            document.getElementsByName('terms').forEach((param) => {
                if ($(param).attr('data-dtl-cd') == $(data).attr('inheritance') && !$('#'+param.id).is(':checked')) {
                    MCP.alert("정보/광고 수신 동의를 위해서는 먼저 고객 혜택 제공을 위한 개인정보 수집 및 이용 동의에 동의하여야 합니다.\n");
                    data.checked = false;
                    return;
                }
            })
        }
        if ($(data).attr('inheritance') == '' || $(data).attr('inheritance') == undefined) {
            if (checkDtls.indexOf($(data).attr('data-dtl-cd')) != -1) {
                document.getElementsByName('terms').forEach((param) => {
                    if (checkDtls.indexOf($(param).attr('inheritance')) != -1) {
                        param.checked = false;
                    }
                });
            }
        }
    }

    $('input[name=terms]').each(function (){
        if($(this).prop('checked')){
            cnt++;
        }
    });

    if(cnt == $('input[name=terms]').length){
        $('#checkAll').prop('checked', 'checked');
    }else{
        $('#checkAll').removeProp('checked');
    }
}

/* validation check - 약관 동의 여부 확인 */
var validationChk = function (){
    var cnt = 0;
    $('input[name=terms]').each(function (){
        if(!$(this).is(':checked')){
            MCP.alert("이벤트 참여를 위해 미동의 항목에 <br/> 동의 해 주시기 바랍니다.", function (){
                $(this).focus();
                $('input[name=radCertification]').removeProp('checked');
                $('input[name=radCertification]').blur();
            });
            cnt++;
            return false;
        }
    });
    if(cnt == 0){
        var clauseYn = 'N';
        var clauseYn04 = 'N';
        var clauseYn05 = 'N';
        $('input[name=terms]').each(function (){
            if($(this).attr('data-dtl-cd') == 'TERMSMEM03' && $(this).is(':checked')){
                clauseYn = 'Y';
            }
            if($(this).attr('data-dtl-cd') == 'TERMSMEM04' && $(this).is(':checked')){
                clauseYn04 = 'Y';
            }
            if($(this).attr('data-dtl-cd') == 'TERMSMEM05' && $(this).is(':checked')){
                clauseYn05 = 'Y';
            }
        });
        $('#clausePriAdFlag').val(clauseYn);
        $('#personalInfoCollectAgree').val(clauseYn04);
        $('#othersTrnsAgree').val(clauseYn05);
        return true;

    }else{
        return false;

    }
};

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
