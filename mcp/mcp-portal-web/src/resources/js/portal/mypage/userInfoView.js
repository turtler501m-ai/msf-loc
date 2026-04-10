/**
 *
 */

var regex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{10,15}$/;  //영문 숫자 특수기호 조합 10자리 이상 (정규식)



var regex1 = /^.*(?=^.{10,15}$)(?=.*\d)(?=.*[a-zA-Z\s]).*$/;

var regexN = /^.*(?=.*\d)(?=.*[a-zA-Z\s])(?=.*[!@#$%^&+=]).*$/;
var regexN1 = /^.*(?=.*\d)(?=.*[a-zA-Z\s]).*$/;
var regexN2 = /^.*(?=.*\d)(?=.*[!@#$%^&+=]).*$/;
var regexN3 = /^.*(?=.*[a-zA-Z\s])(?=.*[!@#$%^&+=]).*$/;
var pwVal = /^.*(?=^.{9,15}$)(?=.*[a-z\s]).*$/;
var phoneCh = /^[0-9]{8,13}$/;
var check = /^(\d+)$/;
var numCh = /[^0-9]/g;
var parentCheckId = [];

var pageObj = {
    niceType:""
    , authObj:{}
    , niceHistSeq:0
    , startTime:0
}
$(document).ready(function(){

    initChecks(); // 초기 동의 체크

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
    });

    $(document).on("keyup", "#newPw", function(){

        //패스워드 패턴 체크 추가  -S-
        var pwstr = $(this).val();
        var obj  = $(this);


        if(!regex.test(pwstr)){
            $("#inpMsgB").hide();
            $(this).parent().removeClass("has-safe");
           //$("#pwdOneChk").text("영문, 숫자, 특수문자 중 3종류를 조합하여 10~15자리 조합하여 비밀번호 입력해 주세요.");
           return false;
        }


        //동일 문자 체크
        if (/(\w)\1\1/.test(pwstr) || /([!@#$%^&+=])\1\1/.test(pwstr) || /(\s)\1\1/.test(pwstr)) {
            //$("#pwdOneChk").text("동일한 문자를 3자리 이상 반복해서 사용할 수 없습니다(예. 111, aaa)");
             $("#inpMsgB").hide();
             $(this).parent().removeClass("has-safe");
            return false;
        }


        $("#inpMsgB").show();
        $(this).parent().addClass("has-safe");

        /*if(regex1.test($(this).val())){
            $("#inpMsgB").show();
            $(this).parent().addClass("has-safe");
        }else{
            $("#inpMsgB").hide();
            $(this).parent().removeClass("has-safe");

        }*/
    });

    $(document).on("keyup", "#checkPw", function(){
        if($("#newPw").val() != $(this).val()){
            $("#inpMsgC").show();
            $(this).parent().addClass("has-error");
        }else{
            $("#inpMsgC").hide();
            $(this).parent().removeClass("has-error");
        }
    });

    $("#mobileNo1, #mobileNo2, #mobileNo3").on("click", function()  {

        if ($("#isAuth").val() == "1") {
            MCP.alert("인증를 완료 하였습니다.");
            return false;
        }

        pageObj.niceType = NICE_TYPE.CUST_AUTH ;
        var data = {width:'500', height:'700'};
        openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M', data)
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
        return;
    });


});

function jusoModal(){
    openPage('pullPopup', '/addPopup.do','','1');
    $(".is-active").removeClass("c-modal--xlg");
    $(".is-active").addClass("c-modal--md");
}
$("#mobileNo1, #mobileNo2, #mobileNo3").on("keyup", function(){

    var val = $(this).val();
    if(!check.test(val)){
        $(this).val(val.replace(numCh, ''));
    }
});

$("#chkG1").on("click", function(){
    if($("#chkG1").is(':checked')){
        $("input[name='emailRcvYn']").val('Y');
    }else{
        $("input[name='emailRcvYn']").val('N');
    }
});

$("#chkG2").on("click", function(){
    if($("#chkG2").is(':checked')){
        $("input[name='smsRcvYn']").val('Y');
    }else{
        $("input[name='smsRcvYn']").val('N');
    }
});

$("#newPw").on("keyup", function(){
    var check = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{9,15}$/;
    var val = $("#newPw").val();

    if(!regex.test(val) && (!regexN1.test(val)||!regexN2.test(val)|| !regexN3.test(val))){
        if(!regex1.test(val)){
            if(!regex2.test(val)){
                if(!regex3.test(val)){
                    $("#error2").prop("hidden", false);
                    $("#npw").addClass("has-error");
                }else{
                    $("#error2").prop("hidden", true);
                    $("#npw").removeClass("has-error");
                }
            }else{
                $("#error2").prop("hidden", true);
                $("#npw").removeClass("has-error");
            }
        }else{
            $("#error2").prop("hidden", true);
            $("#npw").removeClass("has-error");
        }
    }else{
        $("#error2").prop("hidden", true);
        $("#npw").removeClass("has-error");
    }
});

$("#checkPw").on("keyup", function(){
    var oriVal = $("#newPw").val();
    var val = $("#checkPw").val();
    if(oriVal !== val){
        $("#error3").prop("hidden", false);
        $("#cpw").addClass("has-error");
    }else{
        $("#error3").prop("hidden", true);
        $("#cpw").removeClass("has-error");
    }
});


function modifySubmit(){
    var email = $('#email').val();
    var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    var mobileNo = $("#mobileNo1").val() + $("#mobileNo2").val() + $("#mobileNo3").val();
    if(!$("#email").val()) {
        MCP.alert('이메일 주소를 입력해 주세요.',function(){
            $("#email").focus();
        });
        return;
    }

    if(!regExp.test(email)){
          MCP.alert("이메일 형식이 잘못되었습니다.", function(){
            $("#email").focus();
        });
         return;
    }

    if(!mobileNo ||!phoneCh.test(mobileNo)){
        MCP.alert('휴대폰번호를 확인해 주세요', function(){
            $('#mobileNo1').focus();
        });

        return;
    }




    /* var result = confirm('수정하시겠습니까?');

     if(result) {
         userUpdateAjax();
     } else {
         return;
     }*/
    new KTM.Confirm("변경하시겠습니까?", function(){
        userUpdateAjax();
        this.close();
    });
}

function userUpdateAjax(){
    if($("#pw").val() == 'password'){
        var pw = '';
    }else{
        var pw = $("#pw").val();
    }
     var varData = ajaxCommon.getSerializedData({
        name:$("#name").val()
        ,userid:$("#userId").val()
        ,password:pw
        ,mobileNo:$("#mobileNo1").val() + $("#mobileNo2").val() + $("#mobileNo3").val()
        ,email:$("#email").val()
        ,post:''
        ,address1:''
        ,address2:''
        ,personalInfoCollectAgree:$("#personalInfoCollectAgree").is(':checked') ? 'Y' : 'N'
        ,othersTrnsAgree:$("#othersTrnsAgree").is(':checked') ? 'Y' : 'N'
        ,clausePriAdFlag:$("#clausePriAdFlag").is(':checked') ? 'Y' : 'N'
        ,emailRcvYn:$("#emailRcvYn").val()
        ,smsRcvYn:$("#smsRcvYn").val()
        ,minorAgentName:$("#minorAgentName").val()
    });


    ajaxCommon.getItem({
        id:'userUpdateAjax'
        ,cache:false
        ,url:'/mypage/userUpdateAjax.do'
        ,data: varData
        ,dataType:"json"
    }
    ,function(data){

        if(data.RESULT_CODE == "00000"){
            if(data.RESULT_REGULAR_YN){
                MCP.alert("고객님은 정회원으로 확인되었습니다.", function (){
                    MCP.alert(data.RESULT_MSG, function(){
                        location.href="/mypage/updateForm.do";
                    });
                });
            }
            else{
                MCP.alert(data.RESULT_MSG, function(){
                    location.href="/mypage/updateForm.do";
                });
            }
        }else{
            MCP.alert(data.RESULT_MSG);
        }

     });
}


function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){
    $("#postNo").val(zipNo);//도로명주소
    $("#address1").val(roadAddrPart1);//상세주소
    $("#address2").val(addrDetail);//우편번호
    $(".c-modal").find(".c-icon--close").trigger("click");
}




function pwChange(){
    if($("#nowPw").val()==""||$("#nowPw").val()==null){
        MCP.alert("현재 비밀번호를 입력해 주세요.",function(){
            $("#nowPw").focus();
        });

        return;
    }

    if($("#newPw").val()=="" || $("#newPw").val()==null){
        MCP.alert("새 비밀번호를 입력해 주세요.",function(){
            $("#newPw").focus();
        });

        return;
    }

    if($("#checkPw").val()==""||$("#checkPw").val()==null){
        MCP.alert("새 비밀번호 확인을 입력해 주세요.",function(){
            $("#checkPw").focus();
        });

        return;
    }

    if($("#checkPw").val() != ""&& $("#newPw").val() !="" && $("#newPw").val() != $("#checkPw").val() ){
        MCP.alert("비밀번호가 일치하지 않습니다.", function(){
            $("#checkPw").focus();
        });

        return;
    }


  //패스워드 패턴 체크 추가  -S-
    var pwstr = $("#newPw").val();
    var obj  = $("#newPw");




    if(!regex.test(pwstr)){
        MCP.alert("영문,숫자,특수문자를 모두 조합하여 10~15자리 비밀번호를 입력해주세요", function(){
            $("#newPw").focus();
        });
        return false;
    }


    //동일 문자 체크
    if (/(\w)\1\1/.test(pwstr) || /([!@#$%^&+=])\1\1/.test(pwstr) || /(\s)\1\1/.test(pwstr)) {
        MCP.alert("동일한 문자를 3자리 이상 반복해서 사용할 수 없습니다(예. 111, aaa)", function(){
            $("#newPw").focus();
        });
        return false;
    }






    var varData = ajaxCommon.getSerializedData({
        id:$("#userId").val()
        ,pw:$("#nowPw").val()
        ,status:"update"
        ,pwChange:$("#newPw").val()

    });
    var changedPw = $("#newPw").val();

    ajaxCommon.getItem({
        id:'userPwCheckAjax'
        ,cache:false
        ,url:'/mypage/userPwCheckAjax.do'
        ,data: varData
        ,dataType:"json"
    }
    ,function(data){

            if(data.returnCode=="00"){

                $("#pw").val(changedPw);
                MCP.alert(data.message,function(){
                    $("#pwChgBut").trigger("click");
                });

            }else if(data.RESULT_CODE == "xxxx"){
                MCP.alert(data.message, function(){
                    location.href="/m/main.do";
                });

            } else if(data.returnCode == "98") {
                //location.href="/m/errorPage.do";
                MCP.alert(data.message);
                $("#nowPw").focus();
            }else if(data.returCode == '01'){
                MCP.alert(data.message);
                $("#nowPw").focus();
            }else if(data.returnCOde == '02'){
                MCP.alert(data.message);
                $("#newPw").focus();
            } else{
                MCP.alert(data.message);
                $("#nowPw").focus();
            }

    });

    $("#nowPw").val("");
    $("#newPw").val("");
    $("#checkPw").val("");

}


function initPwChg(){
    $("#nowPw").val('');
    $("#newPw").val('');
    $("#checkPw").val('');
}

function viewTerms(target, param) {
    $('#targetTerms').val(target);
    openPage('termsPop','/termsPop.do',param, 0);
}



function setChkbox(data) {
    var cnt = 0;
    if (data != undefined ) {
        var checkPossible = document.querySelector('#' + data.id).checked;
        if (parentCheckId.indexOf($(data).attr('data-dtl-cd')) != -1 && !checkPossible) {
            document.getElementsByName('terms').forEach(function (params) {
                if (parentCheckId.indexOf($(params).attr('inheritance')) != -1) {
                    params.checked = checkPossible;
                }
            });
            data.checked = checkPossible;

        } else if (parentCheckId.indexOf($(data).attr('inheritance')) != -1 && checkPossible) {
            document.getElementsByName('terms').forEach(function(params) {
                if ($(data).attr('inheritance') == $(params).attr('data-dtl-cd')) {
                    if (!$(params).is(':checked')) {
                        MCP.alert("고객 혜택 제공을 위한 개인정보 수집 및 이동 동의가 필요합니다.");
                        data.checked = false;
                    }
                }
            });
        }
        if (checkPossible == false) {
            $('#checkAll').prop('checked', false);
        }
    }

    // $('input[name=terms]').each(function (){
    //     if($(this).prop('checked')){
    //         cnt++;
    //     }
    // });
    //
    // if(cnt == $('input[name=terms]').length){
    //     $('#checkAll').prop('checked', 'checked');
    // }else{
    //     $('#checkAll').removeProp('checked');
    // }
}

var initChecks = function () {

    document.getElementsByName('agree').forEach(function (agreeData) {
        document.getElementsByName('terms').forEach(function(params) {
            if (agreeData.id == $(params).attr('data-dtl-cd')) {
                params.checked = 'Y' == agreeData.value ? true : false;
                $('#'+params.id+'label').attr('for', $(agreeData).attr('value2'));
                $(params).attr('id', $(agreeData).attr('value2'));

                if ( $(params).attr('inheritance') != '') {
                    if (parentCheckId.indexOf($(params).attr('inheritance')) == -1) {
                        parentCheckId.push($(params).attr('inheritance'));
                    }
                }
            }
        })
    });

    var terms = document.getElementsByName('terms');
    if (terms.length == $('input[name=terms]:checked').length) {
        $('#checkAll').prop('checked', true)
    }
}

var targetTermsAgree = function () {
    var id = '';
    var terms = document.getElementsByName('terms');
    terms.forEach(function (param) {
        if ($(param).attr('data-dtl-cd') == $('#targetTerms').val()) {
            id = param.id;
        }
    })
    if ($('#'+id).attr('inheritance') != '') {
        terms.forEach(function (param) {
            if ($(param).attr('data-dtl-cd') == $('#'+id).attr('inheritance')) {
                if ( !$('#'+param.id).is(':checked')) {
                    MCP.alert("고객 혜택 제공을 위한 개인정보 수집 및 이동 동의가 필요합니다.");
                    param.checked = false;
                } else {
                    $('#'+id).prop('checked', true);
                }
            }
        });
    } else {
        $('#'+id).prop('checked', true);
    }
}


function fnNiceCert(prarObj) {
    var strMsg = "고객 정보와 본인인증한 정보가 틀립니다.";
    pageObj.niceResLogObj = prarObj;


    // 본인 인증
    if(pageObj.niceType == "custAuth"){
        var authInfoJson ={ ncType: "0"};
        var isAuthDone = diAuthCallback(authInfoJson);
        if(isAuthDone){
            pageObj.niceHistSeq = diAuthObj.resAuthOjb.NICE_HIST_SEQ ;
            $("#isAuth").val("1");
            var sMobileNo = diAuthObj.resAuthOjb.NICE_LOG_RTN.sMobileNo;
            $("#mobileNo1").val(sMobileNo.substring(0, 3));
            $("#mobileNo2").val(sMobileNo.substring(3, 7));
            $("#mobileNo3").val(sMobileNo.substring(7, 11));
            $("#mobileNo").addClass("is-readonly");
            $("#mobileNo1, #mobileNo2, #mobileNo3").prop('readonly', 'readonly');
            $("#isAuthComplete").show();
        }else{
            strMsg= (diAuthObj.resAuthOjb.RESULT_MSG == undefined) ? strMsg : diAuthObj.resAuthOjb.RESULT_MSG;
            MCP.alert(strMsg);
            return null;
        }
    }
    // 법정대리인 인증
    else{
        var authInfoJson ={cstmrName: $.trim($("#minorAgentName").val()), ncType: "1"};
        var isAuthDone = diAuthCallback(authInfoJson);
        if(isAuthDone){
            pageObj.niceHistSeq = diAuthObj.resAuthOjb.NICE_HIST_SEQ ;
            $("#isAuthAgent").val("1");
            $("#minorAgentName").attr('readonly', 'true');

        }else{
            strMsg= (diAuthObj.resAuthOjb.RESULT_MSG == undefined) ? strMsg : diAuthObj.resAuthOjb.RESULT_MSG;
            MCP.alert(strMsg);
            return null;
        }
    }
}