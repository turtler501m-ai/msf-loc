history.pushState(null, null,location.href);
    window.onpopstate = function (event){
        history.go("/m/loginForm.do");
}

function getToday(){
    var date = new Date();
    var year = date.getFullYear();
    var month = ("0" + (1 + date.getMonth())).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);

    return year + month + day;
}

function finalSubmit() {

    var param = $("#birthdayTxt").val().replace(/-/gi, '');

    // 자리수가 맞지않을때
    if( isNaN(param) || param.length!=8 ) {
        MCP.alert("생년월일을 '19990101'형식으로 입력해 주세요.", function (){
            $("#birthdayTxt").focus();
        });
        return false;
    }

    var year = Number(param.substring(0, 4));
    var month = Number(param.substring(4, 6));
    var day = Number(param.substring(6, 8));

    var dd = day / 0;
    var d = new Date();
    var y = d.getFullYear();
    if( year < 1900 || year > y) {
        MCP.alert("생년월일의 '년'부분이 부정확합니다.", function (){
            $("#birthdayTxt").val('');
            $("#birthdayTxt").focus();
        });
        return false;
    }

    if( month<1 || month>12 ) {
        MCP.alert("생년월일의 '월'부분이 부정확합니다.", function (){
            $("#birthdayTxt").val('');
            $("#birthdayTxt").focus();
        });
        return false;
    }

    var maxDaysInMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    var maxDay = maxDaysInMonth[month-1];

    // 윤년 체크
    if( month==2 && ( year%4==0 && year%100!=0 || year%400==0 ) ) {
        maxDay = 29;
    }

    if( day<=0 || day>maxDay ) {
        MCP.alert("생년월일의 '일'부분이 부정확합니다.", function (){
            $("#birthdayTxt").val('');
            $("#birthdayTxt").focus();
        });
        return false;
    }
    //       return true;
    if(Number(param) >= getToday()){
        MCP.alert('생년월일은 현재 날짜보다 크거나 같을 수 없습니다.', function (){
            $("#birthdayTxt").val('');
            $("#birthdayTxt").focus();
        });
        return false;
    }

    $('#birthday').val(param);
    $.ajax({
        type : "POST",
        url : "/m/addBirthGenderAjax.do",
        data : $('#frm').serialize(),
        dataType : "json",
        contentType : "application/x-www-form-urlencoded; charset=UTF-8",
        async : true,
        beforeSend : function(){
        },
        success : function(data) {
            if(data.returnCode == "00") {
                MCP.alert('추가정보 입력이 완료되었습니다.', function (){
                    goLoginProcess();
                });
            } else if(data.returnCode == "01") {
                MCP.alert('추가정보 입력이 완료되었습니다.', function (){
                    location.href='/m/main.do';
                });
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

function goLoginProcess() {
    $("#userId").val($("#userId").val().trim());
    $("form[name='frm']").attr('action', '/m/loginProcess.do');
    $("form[name='frm']").submit();
}

$(document).ready(function (){
    if($('#userId').val() == ''){
        location.href='/m/main.do';
    }
});