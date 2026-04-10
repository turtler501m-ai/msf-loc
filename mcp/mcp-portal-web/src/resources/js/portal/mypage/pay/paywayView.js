

var pageObj = {
    niceType:""
    , authObj:{}
    , niceHistSeq:0
    , startTime:0
}
// 납부정보(신용카드/자동이체) 카드번호/계좌번호 유효성 검증
var validReqAccount = false;
var validReqCard = false;


$(document).ready(function (){

    window.onpopstate = function (event){
        var ncn = $("#ncn").val();
        var state = { 'ncn': ncn};
        history.pushState(state, null,location.href);
        history.go("/mypage/chargeView05.do");
    }

    initData();

    $(".numOnly").keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });

    $("input[name=reqPayType]").change(function(){
        var val = $(this).val();
        if(val=="D"){
            $("#bank,#auth").show();
            $("#card , #paper , #kakao , #payCo").hide();
        } else if(val=="C"){
            $("#card,#auth").show();
            $("#bank , #paper , #kakao , #payCo").hide();
        } else if(val=="R"){
            $("#paper,#auth").show();
            $("#bank , #card , #kakao , #payCo").hide();
        } else if(val=="K"){
            $("#kakao").show();
            $("#bank , #card , #paper , #payCo ,#auth").hide();
        } else if(val=="P"){
            $("#payCo").show();
            $("#bank , #card , #paper , #kakao ,#auth").hide();
        }
        initData();
        isBankCheckBtnDisable();
    });

    // 은행계좌 자동이체 S
    // 신용카드 인증
    $("#auth_credit").click(function(){
        var cstmrName = $("#cstmrName").val();
        var cstmrNativeRrn01 = $("#cstmrNativeRrn01").val();
        var cstmrNativeRrn02 = $("#cstmrNativeRrn02").val();
        if(cstmrName==""){
            MCP.alert("성명 입력해 주세요.",function(){
                $("#cstmrName").focus();
            });
            return false;
        }
        if(cstmrNativeRrn01=="" || cstmrNativeRrn01.length < 6){
            MCP.alert("주민등록번호 앞자리를 입력해 주세요.",function(){
                $("#cstmrNativeRrn01").focus();
            });
            return false;
        }
        if(cstmrNativeRrn02=="" || cstmrNativeRrn02.length < 7){
            MCP.alert("주민등록번호 뒷자리를 입력해 주세요.",function(){
                $("#cstmrNativeRrn02").focus();
            });
            return false;
        }

        var varData = ajaxCommon.getSerializedData({
            cstmrName : cstmrName,
            cstmrNativeRrn01 : cstmrNativeRrn01
        });

        ajaxCommon.getItem({
            id:'certChkAjax'
            ,cache:false
            ,url:'/mypage/certChkAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(jsonObj){

            if(jsonObj.RESULT_CODE == '00000') {
                pop_credit();
            } else {
                   MCP.alert("로그인한 고객의 정보와 일치하지 않습니다.");
                   return false;
               }
        });

    });

    // 계좌번호 유효성 체크
    $("#bankCheckBtn").click(function(){
        var isAuth = $("#isAuth").val();
        var blBankCode = $("#blBankCode option:selected").val();
        var bankAcctNo = $("#bankAcctNo").val();

        /*if(isAuth!="1"){
            MCP.alert("본인인증 하여 주시기 바랍니다.",function(){
                $("#cstmrName").focus();
            });
            return false;
        }*/
        if(blBankCode==""){
            MCP.alert("은행을 선택해 주세요.",function(){
                $("#blBankCode").focus();
            });
            return false;
        }
        if(bankAcctNo=="" || bankAcctNo.length < 10){
            MCP.alert("계좌번호를 확인해 주세요(숫자만 입력)",function(){
                $("#bankAcctNo").focus();
            });
            return false;
        }

        var varData = ajaxCommon.getSerializedData({
            service : "3",
            svcGbn : "4",
            bankCode : blBankCode,
            accountNo : bankAcctNo
        });

        ajaxCommon.getItem({
            id:'accountCheckAjax'
            ,cache:false
            ,url:'/nice/accountCheckAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(jsonObj){

            if(jsonObj.RESULT_CODE == '00000') {
                validReqAccount = true;
                MCP.alert("계좌 번호 유효성 검증에 성공하였습니다.");
                $("#bankCheckBtn").prop("disabled",true).text("계좌번호 체크 완료");
                $("#blBankCode").prop("disabled",true);
                $("#bankAcctNo").prop("readOnly",true);

                isChangeBtnDisable();
            } else {
                validReqAccount = false;
                   MCP.alert("계좌 번호 유효성 검증에 실패하였습니다.");
                   return false;
               }
        });
    });
    // 은행계좌 자동이체 E

    // 신용카드
    $("#cardCheckBtn").click(function(){
        var cardCode = $("#cardCode option:selected").val();
        var creditCardNo = $("#creditCardNo").val();
        var cardMM = $("#cardMM option:selected").val();
        var cardYY = $("#cardYY option:selected").val();
        if(cardCode==""){
            MCP.alert("카드사를 선택해 주세요.",function(){
                $("#cardCode").focus();
            });
            return false;
        }
        if(creditCardNo==""){
            MCP.alert("카드번호를 입력해 주세요.",function(){
                $("#creditCardNo").focus();
            });
            return false;
        }
        if(creditCardNo.length < 14){
            MCP.alert("신용카드 번호를 확인해 주세요.",function(){
                $("#creditCardNo").focus();
            });
            return false;
        }
        if(cardMM==""){
            MCP.alert("신용카드 유효기간을 선택해 주세요",function(){
                $("#cardMM").focus();
            });
            return false;
        }
        if(cardYY==""){
            MCP.alert("신용카드 유효기간을 선택해 주세요",function(){
                $("#cardYY").focus();
            });
            return false;
        }

        var today = Number(new Date().format("yyyyMM"));
        var yyyymm = Number(cardYY+cardMM);
        if(today > yyyymm){
            MCP.alert("신용카드 유효기간을 확인해 주세요",function(){
                $("#cardMM").focus();
            });
            return false;
        }

        if(checkCardNumber(creditCardNo)){
            validReqCard = true;
            $("#cardCode").prop("disabled",true);
            $("#creditCardNo").prop("readOnly",true);
            $("#cardMM").prop("disabled",true);
            $("#cardYY").prop("disabled",true);
            $("#cardCheckBtn").text("신용카드 체크완료").prop("disabled",true);
            isChangeBtnDisable()
        }else{
            MCP.alert("유용한 신용카드가 아닙니다.");
            $(".changeBtn").prop("disabled",true);
            validReqCard = false;
            isChangeBtnDisable()
            return false;
        }
    });

    // 납부 방법 변경
    $(".changeBtn").click(function(){
        var ncn = $("#ncn").val();
        var reqPayType = $("input[name=reqPayType]:checked").val();
        if(reqPayType=="D"){ // 은행계좌 납부
            var isAuth = $("#isAuth").val();
            var cycleDueDay = $("#cycleDueDay").val();
            var blBankCode = $("#blBankCode option:selected").val();
            var bankAcctNo = $("#bankAcctNo").val();
            var cstmrName = $("#cstmrName").val();
            var cstmrNativeRrn01 = $("#cstmrNativeRrn01").val();

            if(isAuth !="1" ){
                MCP.alert("본인인증을 받아주세요.");
                return false;
            }
            if(!validReqAccount){
                MCP.alert("계좌번호 유효성 체크를 해 주세요.");
                return false;
            }
            if(cycleDueDay==""){
                MCP.alert("납부 기준일을 선택해 주세요.");
                return false;
            }

            var varData = ajaxCommon.getSerializedData({
                reqPayType : reqPayType,
                ncn : ncn,
                cycleDueDay : cycleDueDay,
                blBankCode : blBankCode,
                bankAcctNo : bankAcctNo,
                cstmrName : cstmrName,
                cstmrNativeRrn01 : cstmrNativeRrn01,
                authType : "X"
            });

        } else if(reqPayType=="C"){ // 카드인증

            if(!validReqCard){
                MCP.alert("신용카드 유효성 체크를 해 주세요.");
                return false;
            }
            var cardCode = $("#cardCode option:selected").val();
            var creditCardNo = $("#creditCardNo").val();
            var cardMM = $("#cardMM option:selected").val();
            var cardYY = $("#cardYY option:selected").val();
            var cardCycleDueDay =  $("#cardCycleDueDay option:selected").val();

            if(cardCycleDueDay==""){
                MCP.alert("납부 기준일을 선택해 주세요.");
                return false;
            }

            var varData = ajaxCommon.getSerializedData({
                reqPayType : reqPayType,
                ncn : ncn,
                cycleDueDay : '99',
                cardExpirDate : cardYY+""+cardMM,
                creditCardNo : creditCardNo,
                cardCode : cardCode,
                blpymTmsIndCd : cardCycleDueDay
            });
        } else if(reqPayType=="R"){ // 지로납부
            var adrZip = $("#adrZip").val();
            var adrPrimaryLn = $("#adrPrimaryLn").val();
            var adrSecondaryLn = $("#adrSecondaryLn").val();
            var giroCycleDueDay = $("#giroCycleDueDay option:selected").val();

            if( adrZip=="" || adrPrimaryLn=="" || adrSecondaryLn=="" ){
                MCP.alert("청구지 주소를 확인해 주세요.");
                return false;
            }
            if(giroCycleDueDay==""){
                MCP.alert("납부 기준일을 선택해 주세요.");
                return false;
            }

            var varData = ajaxCommon.getSerializedData({
                reqPayType : reqPayType,
                ncn : ncn,
                cycleDueDay : giroCycleDueDay,
                adrZip : adrZip,
                adrPrimaryLn : adrPrimaryLn,
                adrSecondaryLn : adrSecondaryLn
            });
        }

        if(!confirm("납부방법을 변경하시겠습니까?")){
            return false;
        }

        ajaxCommon.getItem({
            id:'farChgWayChgAjax'
            ,cache:false
            ,url:'/mypage/farChgWayChgAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(jsonObj){

            if(jsonObj.returnCode == "00") {
                MCP.alert("납부방법변경 요청을 완료 하였습니다.",function(){
                    ajaxCommon.createForm({
                        method:"post"
                        ,action:"/mypage/chargeView05.do"
                    });
                    ajaxCommon.attachHiddenElement("ncn",ncn);
                    ajaxCommon.formSubmit();
                });
            } else {
                   MCP.alert(jsonObj.message);
                   return false;
               }
        });

    });

    // 카카오/간편결제
    $(".smsPayBtn").click(function(){
        var ncn = $("#ncn").val();
        if(!confirm("납부방법을 변경하시겠습니까?")){
            return false;
        }

        var reqPayType = $("#reqPayType option:selected").val();
        var payMethod = $("#payMethod").val();
        var payBizrCd = $("#payBizrCd").val();
        if(reqPayType=="K"){
            if (payMethod == "간편결제" && payBizrCd == "KKO") {
                MCP.alert("고객님은 이미 카카오페이로 자동납부중 입니다.");
                return false;
            }
        } else {
            if (payMethod == "간편결제" && payBizrCd == "PYC") {
                MCP.alert("고객님은 이미 페이코 자동납부중 입니다.");
                return false;
            }
        }

        var servicePayBizrCd = "";
        if(reqPayType=="K"){
            servicePayBizrCd = "KKO";
        } else {
            servicePayBizrCd = "PYC";
        }
        var varData = ajaxCommon.getSerializedData({
            ncn : ncn,
            reqPayType : reqPayType,
            payBizCd : servicePayBizrCd
        });

        setTimeout(function(){

            ajaxCommon.getItem({
                id:'sendKakaSmsAjax'
                ,cache:false
                ,async:false
                ,url:'/mypage/sendKakaSmsAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){

                if ("00000" ==  jsonObj.RESULT_CODE) {
                    MCP.alert("전송 하였습니다.",function(){
                        ajaxCommon.createForm({
                            method:"post"
                            ,action:"/mypage/chargeView05.do"
                        });
                        ajaxCommon.attachHiddenElement("ncn",ncn);
                        ajaxCommon.formSubmit();
                    });
                } else {
                    var strMsg = jsonObj.RESULT_MSG;
                    if (strMsg != undefined  ) {
                        MCP.alert(strMsg);
                    } else {
                        MCP.alert("자동납부를 위한 SMS 전송이 불가합니다.\n하단의 ‘②카카오톡 or 카카오페이로 자동납부 신청하기’를 참고하여 신청 부탁드립니다.");
                    }
                }
            })

        }, 500);
    });

    $(".cancelBtn").click(function(){

        var ncn = $("#ncn").val();
        ajaxCommon.createForm({
            method:"post"
            ,action:"/mypage/chargeView05.do"
        });
        ajaxCommon.attachHiddenElement("ncn",ncn);
        ajaxCommon.formSubmit();
    });

    $("#srhBtn").click(function(){

        ajaxCommon.createForm({
            method:"post"
            ,action:"/mypage/chargeView05.do"
        });
        var ncn = $("#ctn option:selected").val();
        ajaxCommon.attachHiddenElement("ncn",ncn);
        ajaxCommon.formSubmit();
    });


    /////////////////////////////////버튼 활성화////////////////////////////////////
    // 1. 은행계좌
    $("#cstmrName,#cstmrNativeRrn01,#cstmrNativeRrn02,#bankAcctNo").keyup(function(){
        isBankCheckBtnDisable();

    });

    $("#chkAgree,#cycleDueDay").change(function(){
        isBankCheckBtnDisable();
        isChangeBtnDisable();
    });

    $("#blBankCode").change(function(){

        var isAuth = $("#isAuth").val();
        var check1 = true;
        var check2 = false;

        /*var cstmrName = $("#cstmrName").val();
        var cstmrNativeRrn01 = $("#cstmrNativeRrn01").val();
        var cstmrNativeRrn02 = $("#cstmrNativeRrn02").val();
        if(cstmrName !="" && cstmrNativeRrn01 !="" && cstmrNativeRrn01.length==6 && cstmrNativeRrn02 !="" && cstmrNativeRrn02.length==7){
            if(isAuth !="1"){
                $("#auth_credit").prop("disabled",false);
            }
            check1 = true;
        } else {
            $("#auth_credit").prop("disabled",true);
            check1 = false;
        }*/

        var blBankCode = $("#blBankCode option:selected").val();
        var bankAcctNo = $("#bankAcctNo").val();
        if(blBankCode !="" && bankAcctNo !="" && bankAcctNo.length >9 ){
            if(validReqAccount==false){
                $("#bankCheckBtn").prop("disabled",false);
            }
            check2 = true;
        } else {
            $("#bankCheckBtn").prop("disabled",true);
            check2 = false;
        }
        var cycleDueDay = $("#cycleDueDay option:selected").val();

        if(check1==true && check2==true && cycleDueDay !="" && isAuth !="" && validReqAccount==true){
            $(".changeBtn").prop("disabled",false);
        } else {
            $(".changeBtn").prop("disabled",true);
        }
    });

    // 2. 신용카드
    $("#cardCode,#cardMM,#cardYY").change(function(){

        var cardCode = $("#cardCode option:selected").val();
        var creditCardNo = $("#creditCardNo").val(); // 15
        var cardMM = $("#cardMM option:selected").val();
        var cardYY = $("#cardYY option:selected").val();
        if(cardCode !="" && creditCardNo !="" && creditCardNo.length > 14 && cardMM !="" && cardYY !="" ){
            $("#cardCheckBtn").prop("disabled",false);
        } else {
            $("#cardCheckBtn").prop("disabled",true);
        }
    });


    $("#creditCardNo").keyup(function(){
        var cardCode = $("#cardCode option:selected").val();
        var creditCardNo = $("#creditCardNo").val(); // 15
        var cardMM = $("#cardMM option:selected").val();
        var cardYY = $("#cardYY option:selected").val();
        if(cardCode !="" && creditCardNo !="" && creditCardNo.length > 14 && cardMM !="" && cardYY !="" ){
            $("#cardCheckBtn").prop("disabled",false);
        } else {
            $("#cardCheckBtn").prop("disabled",true);
        }
    });

    // 3. 지로
    $("#giroCycleDueDay").change(function(){
        isChangeBtnDisable();
    });

    /////////////////////////////////버튼 활성화////////////////////////////////////


});

    var isChangeBtnDisable = function() {
        var reqPayType = $("input[name=reqPayType]:checked").val();
        var isAuth = $("#isAuth").val();
        if(reqPayType=="D") { // 은행계좌 납부
            if(isAuth !="1" ){
                $(".changeBtn").prop("disabled",true);
                return false;
            }
            if(!validReqAccount){
                $(".changeBtn").prop("disabled",true);
                return false;
            }
        } else if ("C" == reqPayType) {
            if(isAuth !="1" ){
                $(".changeBtn").prop("disabled",true);
                return false;
            }
            if(!validReqCard){
                $(".changeBtn").prop("disabled",true);
                return false;
            }
        } else if ("R" == reqPayType) {
            var adrZip = $("#adrZip").val();
            var adrPrimaryLn = $("#adrPrimaryLn").val();
            var adrSecondaryLn = $("#adrSecondaryLn").val();
            var giroCycleDueDay = $("#giroCycleDueDay option:selected").val();

            if( adrZip=="" || adrPrimaryLn=="" || adrSecondaryLn=="" ){
                $(".changeBtn").prop("disabled",true);
                return false;
            }
            if(giroCycleDueDay==""){
                $(".changeBtn").prop("disabled",true);
                return false;
            }
        }
        if ($("#chkAgree:checked").length < 1) {
            $(".changeBtn").prop("disabled",true);
            return false;
        }
        $(".changeBtn").prop("disabled",false);
    }


    var isBankCheckBtnDisable = function() {
        var isAuth = $("#isAuth").val();
        var check1 = true;
        var check2 = false;

        var reqPayType = $("input[name=reqPayType]:checked").val();
        if(reqPayType!="D") { // 은행계좌 납부
            return;
        }
        var blBankCode = $("#blBankCode option:selected").val();
        var bankAcctNo = $("#bankAcctNo").val();
        if(blBankCode !="" && bankAcctNo !="" && bankAcctNo.length >9 ){
            var cycleDueDay = $("#cycleDueDay option:selected").val();
            if(validReqAccount==false && cycleDueDay !=""){
                $("#bankCheckBtn").prop("disabled",false);
            }
            check2 = true;
        } else {
            $("#bankCheckBtn").prop("disabled",true);
            check2 = false;
        }
        var cycleDueDay = $("#cycleDueDay option:selected").val();

        if(check1==true && check2==true && cycleDueDay !="" && isAuth !="" && validReqAccount==true){
            $(".changeBtn").prop("disabled",false);
        } else {
            $(".changeBtn").prop("disabled",true);
        }
    }

function initData(){
    $("#bankAcctNo").val("").prop("readOnly",false);
    $("#creditCardNo").val("").prop("readOnly",false);
    validReqAccount = false;
    validReqCard = false;
    $("#blBankCode option").eq(0).prop("selected",true);
    $("#blBankCode").prop("disabled",false);
    $("#cycleDueDay option").eq(0).prop("selected",true);
    $("#cardCode option").eq(0).prop("selected",true);
    $("#cardCode").prop("disabled",false);
    $("#cardMM option").eq(0).prop("selected",true);
    $("#cardMM").prop("disabled",false);
    $("#cardYY option").eq(0).prop("selected",true);
    $("#cardYY").prop("disabled",false);
    $("#giroCycleDueDay option").eq(0).prop("selected",true);
    $("#bankCheckBtn").text("계좌번호 유효성 체크").prop("disabled",true);
    $("#cardCheckBtn").text("신용카드 유효성 체크").prop("disabled",true);
    $("#isAuth").val("");
    $(".changeBtn").prop("disabled",true);

}


/*신용카드번호 유효성체크*/
function checkCardNumber(cardNumber){
    /*var a, b, c, d = 0;
    if(cardNumber.length == 16){
        for (var i = 0; i < 16; i++){
            a = i % 2 == 0 ? 2 : 1;
            b = a * cardNumber.charAt(i);
            c = b > 9 ? b % 10 + 1 : b;
            d += c;
        }
        return d % 10 == 0 ? true : false;
    } else{
        return false;
    }*/

    var digits = cardNumber.split('');
    for (var i = 0; i < digits.length; i++) {
        digits[i] = parseInt(digits[i], 10);
    }

    //그 배열을 대상으로 룬 알고리즘 실행
    var sum = 0;
    var alt = false;
    for (var i = digits.length - 1; i >= 0 ; i-- ) {
        if (alt) {
            digits[i] *= 2;
            if(digits[i] > 9) {
                digits[i] -= 9;
            }
        }
        sum += digits[i];
        alt = !alt;
    }
    //결국 카드 번호는 잘못 되었음이 밝혀짐
    if(sum % 10 == 0) {
        return true;
    } else {
        return false;
    }
}

function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){

    $("#adrZip").val(zipNo);//도로명주소
    $("#adrPrimaryLn").val(roadAddrPart1);//도로명주소
    $("#adrSecondaryLn").val(addrDetail);//도로명주소
    isChangeBtnDisable();

}

function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

function pop_credit() {
    ajaxCommon.getItemNoLoading({
            id:'getTimeInMillisAjax'
            ,cache:false
            ,async:false
            ,url:'/nice/getTimeInMillisAjax.do'
            ,data: ""
            ,dataType:"json"
        }
        ,function(startTime){
            pageObj.startTime = startTime ;
        });
    pageObj.niceType = NICE_TYPE.CUST_AUTH ;
    var data = {width:'500', height:'700'};
    openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=C', data);
}

function fnNiceCert(obj) {
    niceAuthResult(obj);
    isChangeBtnDisable();
}

function fnNiceCertErr() {
    return false;
}

function niceAuthResult(prarObj){

    var strMsg= "본인인증 한 정보가 다릅니다. 다시 본인인증을 하여 주시기 바랍니다."
    pageObj.niceResLogObj = prarObj;

    var contractNum = $("#contractNum").val();

    var authInfoJson= {contractNum : contractNum};
    var isAuthDone = diAuthCallback(authInfoJson);

    if(isAuthDone){
            if ("C" == prarObj.authType) {
                $("._btnNiceAuthBut").eq(1).addClass("is-active ");
                $("._btnNiceAuthBut").eq(1).html('<i class="c-icon c-icon--card" aria-hidden="true"></i>신용카드 인증완료');
                $("._btnNiceAuth").eq(0).prop('disabled', 'true');
            } else {
                $("._btnNiceAuthBut").eq(0).addClass("is-active ");
                $("._btnNiceAuthBut").eq(0).html('<i class="c-icon  c-icon--phone" aria-hidden="true"></i>휴대폰 인증완료');
                $("._btnNiceAuth").eq(1).prop('disabled', 'true');
            }
            //$("#auth_credit").text("신용카드 인증 완료").prop("disabled",true);
            $("#isAuth").val("1");

            // 신용카드 인증 완료 시 다른 납부방법 선택 불가 처리
            /*$("#reqPayType2").prop('disabled', 'true');
            $("#reqPayType3").prop('disabled', 'true');
            $("#reqPayType4").prop('disabled', 'true');
            $("#reqPayType5").prop('disabled', 'true');*/
            return null;
    }else{

        strMsg= (diAuthObj.resAuthOjb.RESULT_MSG == undefined) ? strMsg : diAuthObj.resAuthOjb.RESULT_MSG;
        MCP.alert(strMsg);

        $("#isAuth").val("");
        return null;
    }
}

