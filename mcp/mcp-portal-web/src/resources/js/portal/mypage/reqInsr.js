VALIDATE_NOT_EMPTY_MSG.rdNew1 = "보험 종류를 선택해 주세요.";
VALIDATE_NOT_EMPTY_MSG.rdInsrNew1 = "보험 종류를 선택해 주세요.";
VALIDATE_NOT_EMPTY_MSG.rdSelf1 = "보험 종류를 선택해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrReceiveTelFn = "사진전송을 위한 연락처의 앞자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrReceiveTelMn = "사진전송을 위한 연락처의 전화번호 중간자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrReceiveTelRn = "사진전송을 위한 연락처의 전화번호 뒷자리를 입력해 주세요.";
VALIDATE_FIX_MSG.cstmrReceiveTelFn = "사진전송을 위한 연락처의 앞자리는 세자리 이상입니다.";
VALIDATE_FIX_MSG.cstmrReceiveTelMn = "사진전송을 위한 연락처의 중간자리는 세자리 이상입니다.";
VALIDATE_FIX_MSG.cstmrReceiveTelRn = "사진전송을 위한 연락처의 뒷자리는 네자리입니다.";



VALIDATE_COMPARE_MSG.cstmrReceiveTelFn = "사진전송을 위한 연락처를 올바르게 입력해 주세요. ";

var pageObj = {
    niceType:""
    , applDataForm:{}
    , niceHistSeq:0
    , authObj:{}
    , authPassObj:{}
    , insrNiceHistSeq:0
    , niceHistInsrProdSeq:0
    , niceResLogObj:{}   //로그을 저장 하기 위한 인증

    , reqType : ""          //고객요청타입
    , onlineAuthType : ""   //인증타입
     , onlineAuthInfo : ""   //인증값
     , insrType : ""         //보험타입
     , insrCode : ""         //보험상품코드
     , smsPhoneNum   : ""	//사진전송위한연락처
     , alertMsg : ""         //신청후 alert 메시지
    , eventId:""
}

history.pushState(null, null, "/mypage/reqInsr.do");
window.onpopstate = function (event){
    history.go("/mypage/reqInsr.do");
}

$(document).ready(function (){

    pageObj.insrType = $("#insrType").val();

    //A:보험가입중/B:고객센터안내(미성년자,법인,신청상이 등)/C:신규단말45일이내/D:신규단말45일이후/E:유심45일이내/F:유심45일이후
    if(pageObj.insrType == "A"){
        $("#divNotA").show();
    }else if(pageObj.insrType == "ING"){
        $("#divNotIng").show();
    }else if(pageObj.insrType == "B"){
        $("#divNotB").show();
    }else if(pageObj.insrType == "C"){
        /*$("#divNew").show();
        $("#divTypeNew").show();*/
        $("#divNew2").show();
        $("#divTypeNew2").show();
        $("#divExplain").show();
        $("#divAuth").show();
        $("#divSubmit").show();
        /*
        $("#divSelNew").show();
        $("#btnRequest").prop("disabled", false);
        */
    }else if(pageObj.insrType == "D" || pageObj.insrType == "F"){
        $("#divSelf").show();
        $("#divTypeSelf").show();
        $("#divExplain").show();
        $("#divAuth").show();
        $("#divSubmit").show();
        /*
        $("#divSelSelf").show();
        $("#divPhone").show();
        $("#btnRequest").prop("disabled", false);
        */
    }else if(pageObj.insrType == "E"){
        $("#divUsim").show();
        $("#divTypeUsim").show();
        $("#divExplain").show();
        $("#divAuth").show();
        $("#divSubmit").show();
        /*
        $("#divSelUsim").show();
        $("#divSelSelf").show();
        $("#divPhone").show();
        $("#btnRequest").prop("disabled", false);
        */
    }

    //신청버튼 클릭
    $("#btnRequest").click(function(){

        if ($("#isAuth").val() != "1") {
            MCP.alert("본인 인증이 완료되지 않았습니다.");
            return false;
        }

        // 에러 문구 제거
        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config = {};

        //보험 종류 선택 여부, 연락처 입력 검사
        if(pageObj.insrType == "C"){
            //validator.config["rdNew1"] = "isNonEmpty";
            validator.config["rdInsrNew1"] = "isNonEmpty";
            validator.config['clauseInsrProdFlag'] = 'isNonEmpty';
            validator.config['clauseInsrProdFlag02'] = 'isNonEmpty';
            validator.config['clauseInsrProdFlag03'] = 'isNonEmpty';
        }else {
            validator.config["rdSelf1"] = "isNonEmpty";
            validator.config['clauseInsrProdFlag'] = 'isNonEmpty';
            validator.config['clauseInsrProdFlag02'] = 'isNonEmpty';
            validator.config['clauseInsrProdFlag03'] = 'isNonEmpty';
            validator.config['cstmrReceiveTelFn&cstmrReceiveTelMn&cstmrReceiveTelRn'] = 'isPhoneNumberCheck';
        }


        //유효성 검사 성공하면
        if(validator.validate()){

            if(pageObj.insrType == "C"){
                //pageObj.insrCode = $("input:radio[name=rdInsrNew]:checked").val();
                pageObj.insrCode = $("input:radio[name=rdInsrNew2]:checked").val();
                pageObj.alertMsg = "보험 신청이 완료되었습니다.<br><br>감사합니다.";
            }else{
                pageObj.insrCode = $("input:radio[name=rdInsrSelf]:checked").val();
                pageObj.smsPhoneNum = $.trim($("#cstmrReceiveTelFn").val() + "" + $("#cstmrReceiveTelMn").val() + "" + $("#cstmrReceiveTelRn").val());
                pageObj.alertMsg = "보험 신청이 완료되었습니다.<br><br>고객님이 남겨주신 별도연락처로<br>휴대폰 상태(사진)을 등록할 수 있는 URL이<br>전송됩니다.<br><br>7일이내 사진등록 시 보험사 심사결과를<br>안내드립니다.<br><br>※7일이내 사진 미등록 시 보험 자동해지<br><br>감사합니다.";
            }

            //pageObj.applDataForm["contractNum"] = $("#selSvcCntrNo option:selected").val();
            pageObj.applDataForm["contractNum"] = $("#contractNum").val();
            //pageObj.applDataForm["cstmrType"] = $("#selSvcCntrNo option:selected").attr("data-customerType");
            //pageObj.applDataForm["reqBuyType"] = $("#selSvcCntrNo option:selected").attr("data-buyType");
            pageObj.applDataForm["onlineAuthType"] = pageObj.onlineAuthType;
            //pageObj.applDataForm["onlineAuthInfo"] = pageObj.onlineAuthInfo;
            pageObj.applDataForm.reqSeq = pageObj.niceResLogObj.reqSeq;
            pageObj.applDataForm.resSeq = pageObj.niceResLogObj.resSeq;
            pageObj.applDataForm["insrProdCd"] = pageObj.insrCode;
            pageObj.applDataForm["etcMobile"] = pageObj.smsPhoneNum;
            pageObj.applDataForm["insrType"] = pageObj.insrType;
            pageObj.applDataForm["reqType"] = "IS";

            KTM.Confirm("휴대폰 안심보험 가입요청을 하시겠습니까?", function() {

                KTM.Dialog.closeAll(); //모달닫기

                var varData = ajaxCommon.getSerializedData(pageObj.applDataForm);

                KTM.LoadingSpinner.show();

                //CustRequestController 호출
                ajaxCommon.getItem({
                    id:'custRequesAjax'
                    ,cache:false
                    ,url:'/mypage/custRequestAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,async:false
                 }
                 ,function(data){
                    KTM.LoadingSpinner.hide();

                    //성공
                    if(data.RESULT_CODE == 'SUCCESS'){
                        MCP.alert(pageObj.alertMsg, function (){
                            location.href="/main.do";
                        });
                    //실패
                    }else if(data.RESULT_CODE == 'AUTH01'){ // AUTH 오류
                        MCP.alert(data.RESULT_MSG);
                    }else if(data.RESULT_CODE == 'STEP01' || data.RESULT_CODE == 'STEP02'){ // STEP 오류
                        MCP.alert(data.RESULT_MSG);
                    }else {
                        MCP.alert(data.RESULT_CODE + ' ' + data.RESULT_MSG);
                    }
                   });
            });
        }else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg(),function (){
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
                $('#' + validator.id).focus(); // 포커스
            });
            $(this).prop("checked", "");
        }
    });



    $("#btnInsrAllCheck").click(function(){
        if ($(this).is(':checked')) {
            $("._agreeInsr").prop("checked", "checked");
        } else {
            $("._agreeInsr").prop("checked", false);
        }
    });

    $("._agreeInsr").click(function(){
        var isAllCheck= true;
        $("._agreeInsr").each(function(){
            if (!$(this).is(':checked')) {
                isAllCheck= false;
            }
        });

        if (isAllCheck) {
            $("#btnInsrAllCheck").prop("checked", "checked");
        } else {
            $("#btnInsrAllCheck").prop("checked", "");
        }
    });



});


//본인인증 콜백
var fnNiceCert = function(prarObj) {

    var strMsg = "고객 정보와 본인인증한 정보가 틀립니다." ;
    pageObj.niceResLogObj = prarObj;

    //본인인증
    if (pageObj.niceType == NICE_TYPE.CUST_AUTH) {

        var authInfoJson= {contractNum: $("#contractNum").val(), authType: prarObj.authType};
        var isAuthDone = mypageAuthCallback(authInfoJson);

        // 성공
        if(isAuthDone){
            //현재 페이지의 authObj에 prarObj 넣기
            pageObj.authObj = prarObj;
            //인증타입
            pageObj.onlineAuthType = prarObj.authType;
            //메시지 띄우기
            MCP.alert("본인인증에 성공 하였습니다.");

            $("#divInsur").show();
            //보험선택폼 보이기
            if(pageObj.insrType == "C"){
                //$("#divSelNew").show();
                $("#divSelNew2").show();
            }else if(pageObj.insrType == "D" || pageObj.insrType == "F"){
                $("#divSelSelf").show();
                $("#divPhone").show();
            }else if(pageObj.insrType == "E"){
                $("#divSelUsim").show();
                $("#divSelSelf").show();
                $("#divPhone").show();
            }
            //신청버튼 활성화
            $("#btnRequest").prop("disabled", false);
            return null;
        //실패
        } else {
            var resultCd= niceAuthObj.resAuthOjb.RESULT_CODE;
            var errorMsg= niceAuthObj.resAuthOjb.RESULT_MSG;

            if (resultCd == "LOGIN") {
                MCP.alert(errorMsg, function () {
                    location.href = '/loginForm.do';
                });
                return null;
            }

            strMsg= (errorMsg == undefined) ? strMsg : errorMsg;
            MCP.alert(strMsg);
            return null;
        }
    }
}

//회선 조회
function select(){
    document.selfFrm.ncn.value = $("#ctn").val();
    document.selfFrm.submit();
}

//포커스 자동옮기기
function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

//에러메시지 보여주기
var viewErrorMgs = function($thatObj, msg) {
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3") ) {
        $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
};

var fnSetEventId = function(eventId){
    pageObj.eventId = eventId;
};

function targetTermsAgree() {
    $('#' + pageObj.eventId).prop('checked', 'checked');
}