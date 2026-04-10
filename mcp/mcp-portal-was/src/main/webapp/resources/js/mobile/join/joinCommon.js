﻿/* 아이디 입력 여부 검사 */
var inputIdChk = function() {
    var inputId = $("#userId").val();
    if($.trim(inputId) == "" || $.trim(inputId) == null) {
        $("#use_ok").text('아이디 미입력 되었습니다. 확인해 주세요.');
        $('#idCheckDiv').addClass('has-error');
        $('#idCheckDiv').removeClass('has-safe');
        //alert('아이디 미입력 되었습니다. 확인하세요.');
        $("#userId").focus();
        return false;
    }
    return true;
};

/* 아이디 유효성 검사 (영문소문자, 숫자만 허용) */
var valicheckEngNum = function() {
    var inputCdBox = $('input[name=userId]');
    var han = /[ㄱ-힣]/g;
    for (var i=0; i < inputCdBox.length; i++) {
        var carLength = inputCdBox[i].value.length;
        for(var j=0;j<carLength;j++){
            var ch = inputCdBox[i].value.charAt(j);
            var chk_han = inputCdBox[i].value.match(han);
               if (!(ch>='0' && ch<='9') && !(ch>='a' && ch<='z')) {
                   $("#use_ok").text('4~12자의 영문 소문자, 숫자만 사용 가능합니다.');
                    $('#idCheckDiv').addClass('has-error');
                    $('#idCheckDiv').removeClass('has-safe');
                   //alert('4~12자의 영문 소문자, 숫자만 사용 가능합니다.');
                   inputCdBox[i].focus();
                   return false;
               }
               if(chk_han) {
                   $("#use_ok").text('4~12자의 영문 소문자, 숫자만 사용 가능합니다.');
                    $('#idCheckDiv').addClass('has-error');
                    $('#idCheckDiv').removeClass('has-safe');
                   //alert('4~12자의 영문 소문자, 숫자만 사용 가능합니다.');
                   return false;
               }
        }
    }
    return true;
};

/* 아이디 길이 검사 ( 4 ~ 12 ) */
var idLengthChk = function() {
    var inputId = $("#userId").val();
    if (inputId.length<4 || inputId.length>12) {
        $("#use_ok").text('아이디를 4~12자까지 입력해 주세요.');
        $('#idCheckDiv').addClass('has-error');
        $('#idCheckDiv').removeClass('has-safe');
        //alert('아이디를 4~12자까지 입력해 주세요.');
        $("#userId").focus();
        $("#userId").select();
        return false;
    }
    return true;
};

/* 아이디 입력값 변경되는 경우 -> 중복체크 초기화 */
function idChange() {
    $("#isPushBtn").val('N');
}

/* 비밀번호 규칙 확인 */
var pwChange = function() {
    $("#isChkPw").val("N");
    $('#btn_submit').addClass('is-disabled');

    if($("#password").val() == ''){
        $("#pwdOneChk").text('');
        $("#pwCheckDiv").removeClass('has-safe');
        $("#pwCheckDiv").removeClass('has-error');
        return;
    }

    $("#pwdSndChk").text('');
    $("#pwCheckConfirmDiv").removeClass('has-safe');
    $("#pwCheckConfirmDiv").removeClass('has-error');

    var regex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{10,15}$/;  //영문 숫자 특수기호 조합 10자리 이상 (정규식)

    /* 패스워드 규칙이 잘 맞으면 */
    $("#pwCheckDiv").removeClass('has-safe');
    $("#pwCheckDiv").removeClass('has-error');
    $("#pwdOneChk").html("");

    //패스워드 패턴 체크 추가  -S-
    var pwstr = $("#password").val();
    var obj  = $("#password");

    /* 패스워드 규칙 체크 */

    if(!regex.test($("#password").val())){
        $("#pwdSndChk").text('');
        $("#pwdOneChk").text("영문,숫자,특수문자를 모두 조합하여 10~15자리 비밀번호를 입력해주세요.");
        $("#pwCheckDiv").removeClass('has-safe');
        $("#pwCheckDiv").addClass('has-error');
        return false;
    }

    //패스워드 패턴 체크 추가  -S-


    //동일 문자 체크
    if (/(\w)\1\1/.test(pwstr) || /([!@#$%^&+=])\1\1/.test(pwstr) || /(\s)\1\1/.test(pwstr)) {
        $("#pwdSndChk").text('');
        $("#pwdOneChk").text("동일한 문자를 3자리 이상 반복해서 사용할 수 없습니다(예. 111, aaa)");
        $("#pwCheckDiv").removeClass('has-safe');
        $("#pwCheckDiv").addClass('has-error');
        //alert('동일한 문자를 3자리 이상 반복해서 사용할 수 없습니다(예. 111, aaa)');
        obj.focus();
        return false;
    }

    //연속 문자 체크
    var SamePass_1 = 0; //연속성(+) 카운드
    var SamePass_2 = 0; //연속성(-) 카운드

    var chr_pass_0;
    var chr_pass_1;
    var chr_pass_2;

    for (var i=0; i < pwstr.length; i++) {
        chr_pass_0 = pwstr.charAt(i);
        chr_pass_1 = pwstr.charAt(i+1);
        chr_pass_2 = pwstr.charAt(i+2);

        //연속성(+) 카운드
        if (chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == 1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == 1) {
            SamePass_1 = SamePass_1 + 1
        }

        //연속성(-) 카운드
        if (chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == -1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == -1) {
            SamePass_2 = SamePass_2 + 1
        }
    }

    if (SamePass_1 > 0 || SamePass_2 > 0 ) {
        $("#pwdSndChk").text('');
        $("#pwdOneChk").text("연속된 문자열은 사용 할 수 없습니다(예. 123, 321, abc, cba)");
        $("#pwCheckDiv").removeClass('has-safe');
        $("#pwCheckDiv").addClass('has-error');
        //alert('연속된 문자열은 사용 할 수 없습니다(예. 123, 321, abc, cba)');
        obj.focus();
        return false;
    }

    //키보드 연속 입력 체크
    var listThreeChar = "qwe|wer|ert|rty|tyu|yui|uio|iop|asd|sdf|dfg|fgh|ghj|hjk|jkl|zxc|xcv|cvb|vbn|bnm";
    var listThreeChar_r = listThreeChar.split("").reverse().join("");
    var arrThreeChar = listThreeChar.split("|");
    for (var i=0; i<arrThreeChar.length; i++) {
        if (pwstr.toLowerCase().match(".*" + arrThreeChar[i] + ".*")) {
            $("#pwdSndChk").text('');
            $("#pwdOneChk").text("키보드 상의 3글자 이상 연속되는 문자열은 사용 할 수 없습니다( 예. 123, qwe, ewq)");
            $("#pwCheckDiv").removeClass('has-safe');
            $("#pwCheckDiv").addClass('has-error');
            //alert('키보드 상의 3글자 이상 연속되는 문자열은 사용 할 수 없습니다( 예. 123, qwe, ewq)');
            obj.focus();
            return false;
        }
    }

    var arrThreeChar_r = listThreeChar_r.split("|");
    for (var i=0; i<arrThreeChar_r.length; i++) {
        if (pwstr.toLowerCase().match(".*" + arrThreeChar_r[i] + ".*")) {
            $("#pwdSndChk").text('');
            $("#pwdOneChk").text("키보드 상의 3글자 이상 연속되는 문자열은 사용 할 수 없습니다( 예. 123, qwe, ewq)");
            $("#pwCheckDiv").removeClass('has-safe');
            $("#pwCheckDiv").addClass('has-error');
            //alert('키보드 상의 3글자 이상 연속되는 문자열은 사용 할 수 없습니다( 예. 123, qwe, ewq)');
            obj.focus();
            return false;
        }
    }
    //패스워드 패턴 체크 추가  -E-

    $("#pwdOneChk").text('사용 가능한 비밀번호입니다.');
    $("#pwCheckDiv").addClass('has-safe');
    $("#pwCheckDiv").removeClass('has-error');

    /* 두개의 패스워드 비교 */
    if($("#password").val() != "" && $("#passwordChk").val() != "") {
        if($("#password").val() != $("#passwordChk").val()) {
            $("#pwdSndChk").text("비밀번호가 일치 하지 않습니다. 동일하게 입력해 주세요.");
            $("#pwCheckConfirmDiv").removeClass('has-safe');
            $("#pwCheckConfirmDiv").addClass('has-error');
            //alert('비밀번호가 일치 하지 않습니다. 동일하게 입력해 주세요.');
            nextStepChk();
            return false;
        } else {
            $("#isChkPw").val("Y");
            //alert('비밀번호가 일치 합니다.');
            $("#pwdSndChk").text('비밀번호가 일치 합니다.');
            $("#pwCheckConfirmDiv").addClass('has-safe');
            $("#pwCheckConfirmDiv").removeClass('has-error');
            nextStepChk();
            return true;
        }
    }
    return true;
};

var pwConfirm = function (){

    if($('#passwordChk').val() == ''){
        $("#pwdSndChk").text('');
        $("#pwCheckConfirmDiv").removeClass('has-safe');
        $("#pwCheckConfirmDiv").removeClass('has-error');
        return;
    }

        /* 두개의 패스워드 비교 */
    if($("#password").val() != "" && $("#passwordChk").val() != "") {
        if($("#password").val() != $("#passwordChk").val()) {
            $("#pwdSndChk").text("비밀번호가 일치 하지 않습니다. 동일하게 입력해 주세요.");
            $("#pwCheckConfirmDiv").removeClass('has-safe');
            $("#pwCheckConfirmDiv").addClass('has-error');
            //alert('비밀번호가 일치 하지 않습니다. 동일하게 입력해 주세요.');
            $("#passwordChk").focus();
            nextStepChk();
            return false;
        } else {
            $("#isChkPw").val("Y");
            //alert('비밀번호가 일치 합니다.');
            $("#pwdSndChk").text('비밀번호가 일치 합니다.');
            $("#pwCheckConfirmDiv").addClass('has-safe');
            $("#pwCheckConfirmDiv").removeClass('has-error');
            nextStepChk();
            return true;
        }
    }
    nextStepChk();
}

/* 이메일 형식 확인 */
var emailValiChk = function() {
    var checkType = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    var emailChk = $("#email").val();
    if($("#email").val() != '' && emailChk.search(checkType) == -1) {
        $('#error4').text('형식에 맞지 않는 이메일입니다.');
        $("#emailCheckDiv").removeClass('has-safe');
        $("#emailCheckDiv").addClass('has-error');
        $('#email').focus();
        nextStepChk();
        return false;
    }else if($.trim($('#email').val()) == ''){
        $('#error4').text('형식에 맞지 않는 이메일입니다.');
        $("#emailCheckDiv").removeClass('has-safe');
        $("#emailCheckDiv").addClass('has-error');
    }else{
        $('#error4').text('형식이 일치합니다.');
        $("#emailCheckDiv").removeClass('has-error');
        $("#emailCheckDiv").addClass('has-safe');
        nextStepChk();
        return true;

    }
};

/* 휴대폰 숫자키 입력제한(한글제한 포함) */
function onlyNumber(event){
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 )
        return;
    else
        return false;
}
function removeChar(event) {
    event = event || window.event;
    var keyID = (event.which) ? event.which : event.keyCode;
    if ( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 )
        return;
    else
        event.target.value = event.target.value.replace(/[^0-9]/g, "");
}


/* 입력완료 버튼 클릭 시 모든 input값 validation check - 빈칸 여부 체크 */
var allValiChk = function() {
    var rtn = true;
    for (var i = 0; i < $('input').length; i++) {
        //input type text check
        if ($('input').eq(i).attr('textCheck') == 'check') {
            if (!$('input').eq(i).val()) {
                alert($('input').eq(i).attr('alt') + '를 입력해 주세요.');
                $('input').eq(i).focus();
                rtn = false;
                break;
            }

        }
        //input type text end
    }
    return rtn;
};

/* 주소 setting */
function jusoCallBack(roadFullAddr, roadAddrPart1, addrDetail,
        roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
    $("#zipNo").val(zipNo);
    $("#addr").val(roadAddrPart1);
    $("#addrDtl").val(addrDetail);
    nextStepChk();
};