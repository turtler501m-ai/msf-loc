/*

    ※※ 공통 JSP/JS 사용법: 간편본인인증을 사용하고자 하는 html 위치에 아래 내용 작성

    ▶ 한 페이지에 간편본인인증 1개만 존재하는 경우(인증수단 공통제어)
    <jsp:include page="/WEB-INF/views/portal/popup/simpleAuthForm.jsp">
      <jsp:param name="controlYn" value="Y"/>
    </jsp:include>

    ▶ 한 페이지에 간편본인인증 1개만 존재하는 경우(인증수단 개별제어: reqAuth는 사용하고자 하는 인증수단)
    <jsp:include page="/WEB-INF/views/portal/popup/simpleAuthForm.jsp">
      <jsp:param name="controlYn" value="N"/>
      <jsp:param name="reqAuth" value="CNATX"/>
    </jsp:include>

    ▶ 한 페이지에 간편본인인증 2개가 존재하는 경우
    첫번째 본인인증 위치: <jsp:include page="/WEB-INF/views/portal/popup/simpleAuthForm.jsp">
                         <jsp:param name="controlYn" value="Y"/>
                       </jsp:include>
    두번째 본인인증 위치: <jsp:include page="/WEB-INF/views/portal/popup/simpleAuthForm.jsp">
                         <jsp:param name="controlYn" value="Y"/>
                            <jsp:param name="spIndex" value="2"/>
                       </jsp:include>

    ▶ 상담원 전화도움 문구(해피콜)를 표출할 경우: 파라미터로 helpDesc값을 Y로 전달
    <jsp:include page="/WEB-INF/views/portal/popup/simpleAuthForm.jsp">
      <jsp:param name="controlYn" value="Y"/>
      <jsp:param name="helpDesc" value="Y"/>
    </jsp:include>

    ● 상담원 전화도움 문구(해피콜) 관련 이벤트 필요 시 각 js에서 $("input:checkbox[name=authCheckNone]") 사용
    ● 두번째 본인인증 태그인 경우 $("input:checkbox[name=authCheckNone2]") 사용


    ※※ 개별 JSP/JS 처리

    ▶ 필수 태그: <input type="hidden" id="isAuth" name="isAuth">
                간편본인인증 두개 사용시 <input type="hidden" id="isAuth2" name="isAuth"> 추가 존재

    ▶ 간편본인인증 DEFAULT 선택값 설정(옵션): <input type="hidden" id="defaultAuth" name="defaultAuth" value= "C">
                                         두번째 본인인증 태그의 DEFAULT 선택값 설정 시 <input type="hidden" id="defaultAuth2" name="defaultAuth" value= "C">

     ▶ 본인인증 전 유효성 검사 함수: simpleAuthvalidation (true/false 리턴)
                                두번째 본인인증 태그의 유효성 검사는 simpleAuthvalidation2 함수로 진행

      simpleAuthvalidation= function(){
         return (유효성검사 통과) ? true : false;
      }

     ▶ 본인인증 완료 콜백 함수: fnNiceCert(인증결과)
*/

var tossAjaxLastNum = 0;          // NOTE: ajax 중복요청 시 마지막 요청만 처리하기 위함
var simpleAuthvalidation = null;  // 본인인증전 유효성 검사 function
var simpleAuthvalidation2 = null; // 본인인증2 전 유효성 검사 function

var niceAuthObj = {  //niceAuth.js 전역변수
     resAuthOjb:{}
    ,nicePinObj:{}
}

$(document).ready(function() {

    if($("#isAuth").length > 0){

        // 간편본인인증 라디오버튼 클릭이벤트
        $("input:radio[name=onlineAuthType]").click(function(){

            // 에러 문구 제거
            $(".c-form__text").remove();
            $(".has-error").removeClass("has-error");

            if($("#isAuth").val() == "1"){ // 본인인증을 완료한 경우
                MCP.alert("본인 인증을 완료 하였습니다.");
                return false;
            }

            // 인증 전 유효성 검사
            if(checkAuthValidation()){
                var selAuthType = $(this).val();

                // 본인인증방법 설명문구 세팅
                var description= makeOnlineAuthDesc(selAuthType);
                $("#ulOnlineAuthDesc").html(description).parent().show();

                // 버튼 문구 세팅
                $("#btnNiceAuth").text($(this).data("btext"));
                $("#btnNiceAuth").parent().show();

                if(selAuthType == "X"){
                    $("#issuedBank").show();
                    $("#ulOnlineAuthDesc").parent().hide();
                }else{
                    $("#issuedBank").hide();
                }

            }else{
                $(this).prop("checked", "");
                $("#ulOnlineAuthDesc").parent().hide();
                $("#btnNiceAuth").parent().hide();
                $("#issuedBank").hide();
            }
        });

        // 간편본인인증 진행버튼 클릭 이벤트
        $("#btnNiceAuth").click(function(){

            // 인증 전 유효성 검사
            if(!checkAuthValidation()) return false;

            // 선택한 본인인증 확인
            var selAuthType = $("input:radio[name=onlineAuthType]:checked").val();
            if(typeof selAuthType == "undefined") return false;

            // nicepin 연동
            if(!getNicePinCi()){
                return false;
            }

            if(selAuthType == "C" || selAuthType == "X") creditCardAuth(selAuthType);
            else if(selAuthType == "N") naverAuth();
            else if(selAuthType == "A") passAuth();
            else if(selAuthType == "T") tossAuth();
            else if(selAuthType == "K") kakaoAuth();
        });


        // 간편본인인증 진행버튼 button 클릭 이벤트
        $("._btnNiceAuthBut").click(function(){

            // 인증 전 유효성 검사
            if(!checkAuthValidation()) return false;

            // 선택한 본인인증 확인
            var selAuthType = $(this).data("onlineAuthType");
            if(typeof selAuthType == "undefined") return false;


            creditCardAuth(selAuthType);
        });

        // 초기 default값 설정
        if($("#defaultAuth").length > 0){
            $("input:radio[name=onlineAuthType][value="+$("#defaultAuth").val()+"]").trigger("click");
        }
    }


    // 두번째 본인인증 태그를 사용할 경우 이벤트 추가 등록
    if($("#isAuth2").length > 0){

        // 간편본인인증 라디오버튼 클릭이벤트
        $("input:radio[name=onlineAuthType2]").click(function(){

            // 에러 문구 제거
            $(".c-form__text").remove();
            $(".has-error").removeClass("has-error");

            if($("#isAuth2").val() == "1"){ // 본인인증을 완료한 경우
                MCP.alert("본인 인증을 완료 하였습니다.");
                return false;
            }

            // 인증 전 유효성 검사
            if(checkAuthValidation("Y")){
                var selAuthType = $(this).val();

                // 본인인증방법 설명문구 세팅
                var description= makeOnlineAuthDesc(selAuthType);
                $("#ulOnlineAuthDesc2").html(description).parent().show();

                // 버튼 문구 세팅
                $("#btnNiceAuth2").text($(this).data("btext"));
                $("#btnNiceAuth2").parent().show();

                if(selAuthType == "X"){
                    $("#issuedBank2").show();
                    $("#ulOnlineAuthDesc2").parent().hide();
                }else{
                    $("#issuedBank2").hide();
                }

            }else{
                $(this).prop("checked", "");
                $("#ulOnlineAuthDesc2").parent().hide();
                $("#btnNiceAuth2").parent().hide();
                $("#issuedBank2").hide();
            }
        });

        // 간편본인인증 진행버튼 클릭 이벤트
        $("#btnNiceAuth2").click(function(){

            // 인증 전 유효성 검사
            if(!checkAuthValidation("Y")) return false;

            // 선택한 본인인증 확인
            var selAuthType = $("input:radio[name=onlineAuthType2]:checked").val();
            if(typeof selAuthType == "undefined") return false;

            if(selAuthType == "C" || selAuthType == "X") creditCardAuth(selAuthType, "Y");
            else if(selAuthType == "N") naverAuth("Y");
            else if(selAuthType == "A") passAuth("Y");
            else if(selAuthType == "T") tossAuth("Y");
            else if(selAuthType == "K") kakaoAuth("Y");
        });


        // 초기 default값 설정
        if($("#defaultAuth2").length > 0){
            $("input:radio[name=onlineAuthType2][value="+$("#defaultAuth2").val()+"]").trigger("click");
        }
    }

}); // end of $(document).ready(function() {---------------------------

function getStartTime(){
    ajaxCommon.getItem({
         id:'getTimeInMillisAjax'
        ,cache:false
        ,url:'/nice/getTimeInMillisAjax.do'
        ,data:""
        ,dataType:"json"
    }
    ,function(startTime){
        pageObj.startTime = startTime;
    });
}

function creditCardAuth(authType, isSecond){

    getStartTime();

    if(isSecond == undefined) isSecond= "";
    if(checkAuthValidation(isSecond)){

        var authReqObj= preProcSimpleAuth(authType, $("#onOffType").val());
        if(authReqObj.RESULT_CODE != "00000") return false;

        var data = {width:'500', height:'700'};
        var prcType= $("#onOffTypeInit").val() == "5" ? "COMMON" : "PARTIAL";
        openPage('outLink2', authReqObj.pollingPageUrl+'&prcType='+prcType, data);
    }
}

function naverAuth(isSecond){

    if(isSecond == undefined) isSecond= "";
    if(checkAuthValidation(isSecond)){

        var authReqObj= preProcSimpleAuth('N', $("#onOffType").val());
        if(authReqObj.RESULT_CODE != "00000") return false;

        $("#naver_id_login_anchor").trigger("click");
    }
}

function tossAuth(isSecond){

    getStartTime();

    if(isSecond == undefined) isSecond= "";
    if(checkAuthValidation(isSecond)){
        openTossAuthPopup(++tossAjaxLastNum);
    }
}

function passAuth(isSecond){

    getStartTime();

    if(isSecond == undefined) isSecond= "";
    if(checkAuthValidation(isSecond)){

        var authReqObj= preProcSimpleAuth('A', $("#onOffType").val());
        if(authReqObj.RESULT_CODE != "00000") return false;

        var data = {width:'500', height:'700'};
        var prcType= $("#onOffTypeInit").val() == "5" ? "COMMON" : "PARTIAL";
        openPage('outLink2', authReqObj.pollingPageUrl+'?prcType='+prcType, data);
    }
}

function kakaoAuth(isSecond){

    if(isSecond == undefined) isSecond= "";
    if(checkAuthValidation(isSecond)){

        var authReqObj= preProcSimpleAuth('K', $("#onOffType").val());
        if(authReqObj.RESULT_CODE != "00000") return false;

        var data = {width:'510', height:'772'};
        var prcType= $("#onOffTypeInit").val() == "5" ? "COMMON" : "PARTIAL";
        openPage('outLink2', authReqObj.pollingPageUrl+'?prcType='+prcType, data);
    }
}

// 본인인증 수단 설명 문구
function makeOnlineAuthDesc(authType){
    var arr =[];
    switch (authType) {
        case 'C':
            arr.push("<li class='c-text-list__item'>고객님의 본인명의(미성년자의 경우 법정 대리인)의 국내발행 신용카드 정보를 입력해주세요.(체크카드 인증불가)</li>\n");
            arr.push("<li class='c-text-list__item'>신용카드 비밀번호 3회 이상 오류 시 해당카드로 인증을 받으실 수 없으니 유의하시기 바랍니다.</li>\n");
            break;
        case 'N':
            arr.push("<li class='c-text-list__item'>네이버 앱에 인증을 진행하는 사용자의 ID가 로그인되어 있어야 인증 진행이 가능합니다.</li>\n");
            arr.push("<li class='c-text-list__item'>네이버인증서는 발급 과정에서 본인명의의 휴대폰인증이 필요하며, 아이폰5S/갤럭시S5, Note4 이상에서 지원됩니다.(iOS10, 안드로이드6 이상)</li>\n");
            arr.push("<li class='c-text-list__item'>네이버 인증이 정상적으로 진행되지 않으실 경우 네이버인증서를 재발급 받으신 후 이용이 가능합니다.</li>\n");
            break;
        case 'A':
            arr.push("<li class='c-text-list__item'>PASS 인증서 발급 과정에서 본인명의의 휴대폰인증이 필요합니다.</li>\n");
            arr.push("<li class='c-text-list__item'>PASS 인증서는 PASS 앱 설치 후 이용 가능합니다.</li>\n");
            break;
        case 'X':
            arr.push("<li class='c-text-list__item'>은행 및 한국전자인증, 한국정보인증에서 발급된 범용 공인증서(유료)만 사용 가능합니다.</li>\n");
            arr.push("<li class='c-text-list__item'>체크카드는 인증이 불가합니다.</li>\n");
            break;
        case 'T':
            arr.push("<li class='c-text-list__item'>toss 인증서 발급 과정에서 본인명의의 휴대폰인증이 필요합니다.</li>\n");
            arr.push("<li class='c-text-list__item'>toss 인증서는 toss 앱 설치 후 이용이 가능합니다.</li>\n");
            break;
        case 'K':
            arr.push("<li class='c-text-list__item'>카카오 본인인증 발급 과정에서 본인명의의 휴대폰인증이 필요합니다.</li>\n");
            arr.push("<li class='c-text-list__item'>카카오 본인인증 발급 인증서는 카카오톡 앱 설치 후 이용이 가능합니다.</li>\n");
            break;
    }

    return arr.join('');
}

// ======================= S: TOSS 간편인증 =======================
// TOSS 표준창 호출
var TossAuth = {
    _popup: null,
    _TOSS_POPUP_BASE_URL: 'https://auth.cert.toss.im',

    /**팝업 차단 방지를 위해 비동기 호출 전에 빈 팝업을 엽니다.*/
    preparePopup: function () {
        var popupOption = 'top=10, left=10, width=540, height=680, status=no, menubar=no, toolbar=no, resizable=no';
        var isIE = /*@cc_on!@*/ false || !!document.documentMode;
        var popupInitialEmptyUrl = isIE ? this._TOSS_POPUP_BASE_URL + '/loading' : undefined; // NOTE: IE는 undefined 팝업을 열지 못합니다.

        if (this._popup !== null) {
            this._popup.close(); // NOTE: 이미 팝업이 열려있다면 닫고 다시 띄웁니다 (모바일에선 팝업을 n번 띄울 때 포커싱이 안되는 현상 때문)
        }
        this._popup = window.open(popupInitialEmptyUrl, '토스로 인증하기', popupOption);
    },

    /**미리 열어두었던 팝업을 인증페이지로 전환하고, 이벤트 핸들러를 등록합니다.*/
    start: function (params) {
        const popup = this._popup;
        function postTxIdToPopup(originalUrl, name, txId) {
            var url = originalUrl;
            var form = document.createElement('form');
            form.setAttribute('method', 'post');
            form.setAttribute('action', url);
            form.setAttribute('target', name);
            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'txId';
            input.value = txId;
            form.appendChild(input);

            document.body.appendChild(form);
            popup.location.href = url;
            form.submit();
            document.body.removeChild(form);
        }

        function addPopupEvent(param) {
            var handleMessage = function (event) {
                var resetHandlers = function () {
                    window.removeEventListener('message', handleMessage);
                };
                var eventType = event.data.type;
                switch (eventType) {
                    case 'TOSS_AUTH_SUCCESS':
                        if (param.onSuccess !== undefined) {
                            param.onSuccess();
                        } else {
                            console.error('TossAuth.start에 onSuccess를 정의해주세요');
                        }
                        resetHandlers();
                        break;
                    case 'TOSS_AUTH_FAIL':
                        if (param.onFail !== undefined) {
                            param.onFail(event.data.error);
                        }
                        resetHandlers();
                        break;
                    default:
                        break;
                }
            };
            window.addEventListener('message', handleMessage, false);
        }

        var authUrlWithDomain = params.authUrl + '&domain=' + location.origin;
        postTxIdToPopup(authUrlWithDomain, '토스로 인증하기', params.txId);
        addPopupEvent(params);
    },
};

// NOTE: 서버 투 서버의 인증요청 API를 호출합니다. 원하는 방식으로 적절히 수정해주세요.
function getTxId(successCallback, failCallback) {

    var authReqObj= preProcSimpleAuth('T', $("#onOffType").val());
    if(authReqObj.RESULT_CODE != "00000"){
        authReqObj.alertYn= 'N';
        return failCallback(authReqObj);
    }

    var varData = ajaxCommon.getSerializedData({
        requestType:'USER_NONE'
       ,prcType: $("#onOffTypeInit").val() == "5" ? "COMMON" : "PARTIAL"
    });

    ajaxCommon.getItem({
         id: 'getToss'
        ,cache: false
        ,url: authReqObj.pollingPageUrl
        ,data: varData
        ,dataType: "json"
    }
    ,function(jsonObj){
        if (jsonObj.resultType == "SUCCESS") successCallback(jsonObj);
        else failCallback(jsonObj);
    });
}

// TOSS 인증 팝업 호출
function openTossAuthPopup(currentAjaxNum) {

    TossAuth.preparePopup(); // NOTE: 팝업차단 방지를 위해 빈 팝업을 먼저 엽니다

    getTxId(function(response) {

        TossAuth.start({
            authUrl: response.authUrl,
            txId: response.txId,
            onSuccess: function () {
                if(currentAjaxNum == tossAjaxLastNum){
                    tossAjaxLastNum= 0;
                    getTossCertifyInfo(response.txId, response.strAccToken);
                }
            },
            onFail: function (error) {

                if(currentAjaxNum == tossAjaxLastNum){

                    tossAjaxLastNum= 0;

                    // 본인인증 요청 알람(push) 인증완료 응답 건 로그 기록용 객체 생성
                    var tryLogObj= {
                         startTime: pageObj.startTime
                        ,reqSeq: (response.strAccToken == undefined || response.strAccToken == null) ? "" : response.strAccToken
                        ,authType: "T"
                        ,succYn: "N"
                    }

                    insertNiceTryLog(tryLogObj, function(){
                        MCP.alert("toss 본인인증에 실패했습니다. 다시 시도 바랍니다.");
                    });
                }
            }
        });
    }, function(response){

        if(TossAuth._popup != null && typeof TossAuth._popup != "undefined") TossAuth._popup.close();

        var reason= "처리중 오류가 발생했습니다. 잠시후 다시 시도해 주세요";
        if(response.reason != undefined) reason= response.reason;

        if(response.alertYn != "N") MCP.alert(reason);
    });
}

// NOTE: TOSS 본인인증 결과를 조회한다.
function getTossCertifyInfo(txId, strAccToken){

    var varData = ajaxCommon.getSerializedData({
         txId: txId
        ,strAccToken: strAccToken
    });

    ajaxCommon.getItem({
         id: 'getTossCertifyInfo'
        ,cache: false
        ,url: '/nice/getTossCertifyInfo.do'
        ,data: varData
        ,dataType: "json"
    }
    ,function(jsonObj){

        if(jsonObj.resultType != "SUCCESS"){
            var reason= "처리 중 오류가 발생했습니다.";
            if(jsonObj.reason != undefined) reason= jsonObj.reason;
            MCP.alert(reason);

        }else{
            fnNiceCert(jsonObj);
        }
    });
}
// ======================= E: TOSS 간편인증 =======================

// 본인인증 진행 전 유효성 검사: isSecond 값이 Y인 경우 두번째 본인인증 폼 유효성검사 진행
function checkAuthValidation(isSecond){

    // 에러 문구 제거
    $(".c-form__text").remove();
    $(".has-error").removeClass("has-error");

    var authStatus = (isSecond == "Y") ? $("#isAuth2").val() : $("#isAuth").val();

    if(authStatus == "1"){
        MCP.alert("본인 인증을 완료 하였습니다.");
        return false;
    }

    // 유효성검사 함수 호출 (simpleAuthvalidation 또는 simpleAuthvalidation2)
    pageObj.niceType = NICE_TYPE.CUST_AUTH;
    if(isSecond == "Y"){
        if(typeof simpleAuthvalidation2 == "function") return simpleAuthvalidation2();
        else return true;
    }

    // 고객선택유형 존재 시 필수값 검사 진행
    if(vdlCheckByCstmrType()){
        if(typeof simpleAuthvalidation == "function") return simpleAuthvalidation();
        else return true;
    }else{
        return false;
    }
}

// 고객유형에 따른 유효성 검사
function vdlCheckByCstmrType(){
    var operType = $("#operType").val();
    if(operType === "MCN") return true;

    // 고객선택유형 존재 시 필수값 검사 진행
    var cstmrDivCount = $("input:radio[name=cstmrType]").length;
    if(cstmrDivCount == 0) return true;  // 고객선택유형 미존재

    var cstmrType= $("input:radio[name=cstmrType]:checked").val();

    validator.config={};

    validator.config['cstmrType1'] = 'isNonEmpty';
    validator.config['cstmrName'] = 'isNonEmpty';
    if(cstmrType){ // 고객유형을 선택한 경우
        if(cstmrType == CSTMR_TYPE.NA){ //내국인

            if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE){
                validator.config['cstmrNativeRrn01'] = 'isNonEmpty';
                validator.config['cstmrNativeRrn02'] = 'isNonEmpty';
            }

            // eSIM wacth 검증하지 않음 + 2025-08-05 기기변경 검증하지않음
            if($("#cstmrNativeRrn02").length > 0 && !(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE)){
                validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
            }

        }else if(cstmrType == CSTMR_TYPE.NM){  //청소년
            if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE){
                validator.config['cstmrNativeRrn01'] = 'isNonEmpty';
                validator.config['cstmrNativeRrn02'] = 'isNonEmpty';
            }
            validator.config['minorAgentName'] = 'isNonEmpty';
            validator.config['minorAgentRrn01&minorAgentRrn02'] = 'isJimin';

            // eSIM wacth 검증하지 않음 + 2025-08-05 기기변경 검증하지않음
            if($("#cstmrNativeRrn02").length > 0 && !(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE)){
                validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isTeen';
            }

            // 법정대리인 안내 동의 여부 확인
            if($("#minorAgentAgrmYn").length > 0){
                validator.config['minorAgentAgrmYn'] = 'isNonEmpty';
            }

        }else if(cstmrType == CSTMR_TYPE.FN){  //외국인
            if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE){
                validator.config['cstmrForeignerRrn01'] = 'isNonEmpty';
                validator.config['cstmrForeignerRrn02'] = 'isNonEmpty';
            }
            // eSIM wacth 검증하지 않음  + 2025-08-05 기기변경 검증하지않음
            if($("#cstmrForeignerRrn02").length > 0  && !(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE)){
                validator.config['cstmrForeignerRrn01&cstmrForeignerRrn02'] = 'isFngno';
            }

        } else {
            MCP.alert("지원하지 않는 고객유형 입니다.");
            return false;
        }
    }
    if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE) {  // 기변인 경우
        validator.config['changeCstmrMobileFn'] = 'isNumFix3';
        validator.config['changeCstmrMobileMn'] = 'isNumBetterFixN3';
        validator.config['changeCstmrMobileRn'] = 'isNumFix4';
        validator.config['changeCstmrMobileFn&changeCstmrMobileMn&changeCstmrMobileRn'] = 'isPhoneNumberCheck';
    }


    if(validator.validate()){
        return true;
    }else{
        MCP.alert(validator.getErrorMsg(),function (){
            $selectObj = $("#"+validator.getErrorId());
            viewAuthErrorMgs($selectObj, validator.getErrorMsg());
            $('#' + validator.getErrorId()).focus();
        });
        return false;
    }
}

/** 본인인증 데이터 검증 */
/*
    [필수값]
    ● authType

    [페이지 별 선택값]
    ● cstmrName (default "")
    ● cstmrNativeRrn (default "")
    ● isCommonPrd (default true) : DOM 비활성화 처리 여부
    ● contractNum
    ● operType (default "")
    ● onOffType (default "")
    ● ncType (default "") : 대리인 구분값(대리인1, 청소년0) 또는 양수인 구분값 (양수인 1, 양도인0)
    ● isSecond (default "") : 2번째 본인인증 폼인 경우 Y
    ● cstmrType (default "")
*/
function authCallback(infoObj){

    var isCheck = false;

    // 0. 데이터세팅
    var niceType = NICE_TYPE.CUST_AUTH;
    if (pageObj.niceType != undefined && "" != pageObj.niceType) niceType = pageObj.niceType ;

    var startTime = 0 ;
    if (pageObj.startTime != undefined) startTime = pageObj.startTime;
    if (infoObj.isCommonPrd == undefined) infoObj.isCommonPrd = true;

    if (infoObj.contractNum == undefined || infoObj.contractNum == null || infoObj.contractNum == ""){

        // 1. option 데이터 default 세팅
        if(infoObj.cstmrName == undefined) infoObj.cstmrName= "";
        if(infoObj.cstmrNativeRrn == undefined) infoObj.cstmrNativeRrn= "";
        if(infoObj.operType == undefined) infoObj.operType= "";
        if(infoObj.onOffType == undefined) infoObj.onOffType= "";
        if(infoObj.ncType == undefined) infoObj.ncType= "";

        var varData = ajaxCommon.getSerializedData({
             cstmrName: infoObj.cstmrName
            ,cstmrNativeRrn: infoObj.cstmrNativeRrn
            ,reqSeq: pageObj.niceResLogObj.reqSeq
            ,resSeq: pageObj.niceResLogObj.resSeq
            ,paramR3: niceType
            ,startTime: startTime
            ,operType: infoObj.operType
            ,onOffType: infoObj.onOffType
            ,ncType: infoObj.ncType
        });

        ajaxCommon.getItemNoLoading({
             id: 'getReqAuthAjax'
            ,cache: false
            ,async: false
            ,url: '/auth/getReqAuthAjax.do'
            ,data: varData
            ,dataType: "json"
        }
        ,function(jsonObj){

            // 2. 본인인증 데이터 검증
            niceAuthObj.resAuthOjb=jsonObj;

            if (jsonObj.RESULT_CODE == "00000") {
                isCheck = true;

                // 필요에따라 시퀀스 보관
                if(infoObj.isSecond == "Y" && pageObj.niceHistSeq2 != undefined) pageObj.niceHistSeq2 = jsonObj.niceHistSeq;
                if(niceType == NICE_TYPE.RWD_PROD && pageObj.niceHistRwdProdSeq != undefined) pageObj.niceHistRwdProdSeq = jsonObj.niceHistSeq;
            }
        });

    } else { // ESIM_WATCH, 데이터쉐어링
        // 1. option 데이터 default 세팅
        if(infoObj.cstmrType == undefined) infoObj.cstmrType= "";
        if(infoObj.cstmrName == undefined) infoObj.cstmrName= "";
        if(infoObj.cstmrNativeRrn == undefined) infoObj.cstmrNativeRrn= "";
        if(infoObj.orgName == undefined) infoObj.orgName= "";
        if(infoObj.orgRrn == undefined) infoObj.orgRrn= "";

        var varData = ajaxCommon.getSerializedData({
             contractNum: infoObj.contractNum
            ,reqSeq: pageObj.niceResLogObj.reqSeq
            ,resSeq: pageObj.niceResLogObj.resSeq
            ,cstmrName: infoObj.cstmrName
            ,cstmrNativeRrn: infoObj.cstmrNativeRrn
            ,orgName: infoObj.orgName
            ,orgRrn: infoObj.orgRrn
            ,startTime: startTime
            ,cstmrType: infoObj.cstmrType
        });


        ajaxCommon.getItemNoLoading({
             id: 'getContractAuth'
            ,cache: false
            ,async: false
            ,url: '/appForm/getContractAuthAjax.do'
            ,data: varData
            ,dataType: "json"
        }
        ,function(jsonObj){
            // 2. 본인인증 데이터 검증
            niceAuthObj.resAuthOjb=jsonObj;
            if(jsonObj.RESULT_CODE == "00000") isCheck = true;
        });
    }

    // 3. 본인인증 검증결과 후처리
    if(isCheck && infoObj.isCommonPrd) commonPrdProcess(infoObj.authType, infoObj.isSecond);
    return isCheck;
}

/** 마이페이지 본인인증 데이터 검증 */
/*
    [필수값]
    ● authType

    [페이지 별 선택값]
    ● cstmrName (default "")
    ● cstmrNativeRrn (default "")
    ● contractNum (default "")
    ● ncType (default "") : 대리인 구분값(대리인1, 청소년0) 또는 양수인 구분값 (양수인 1, 양도인0)
    ● isSecond (default "") : 2번째 본인인증 폼인 경우 Y
*/
function mypageAuthCallback(infoObj){

    var isCheck = false;

    // 0. 데이터세팅
    var niceType = NICE_TYPE.CUST_AUTH ;
    if (pageObj.niceType != undefined && "" != pageObj.niceType) niceType = pageObj.niceType;

    // 1. option 데이터 default 세팅
    if(infoObj.cstmrName == undefined) infoObj.cstmrName= "";
    if(infoObj.cstmrNativeRrn == undefined) infoObj.cstmrNativeRrn= "";
    if(infoObj.ncType == undefined) infoObj.ncType= "";
    if(infoObj.contractNum == undefined) infoObj.contractNum= "";
    if(infoObj.cstmrType == undefined) infoObj.cstmrType= "";
    if(infoObj.cstmrMinorName == undefined) infoObj.cstmrMinorName= "";
    if(infoObj.cstmrMinorRrn == undefined) infoObj.cstmrMinorRrn= "";

    var varData = ajaxCommon.getSerializedData({
         cstmrName: infoObj.cstmrName
        ,cstmrNativeRrn: infoObj.cstmrNativeRrn
        ,contractNum: infoObj.contractNum
        ,reqSeq: pageObj.niceResLogObj.reqSeq
        ,resSeq: pageObj.niceResLogObj.resSeq
        ,paramR3: niceType
        ,ncType: infoObj.ncType
        ,cstmrType: infoObj.cstmrType
        ,cstmrMinorName: infoObj.cstmrMinorName
        ,cstmrMinorRrn: infoObj.cstmrMinorRrn
    });

    ajaxCommon.getItemNoLoading({
         id: 'getCertAuthAjax'
        ,cache: false
        ,async: false
        ,url: '/auth/getCertAuthAjax.do'
        ,data: varData
        ,dataType: "json"
    }
    ,function(jsonObj){

        // 2. 본인인증 데이터 검증
        niceAuthObj.resAuthOjb=jsonObj;

        if(jsonObj.RESULT_CODE == "00000"){
            isCheck = true;

            // 필요에따라 시퀀스 보관
            if(infoObj.isSecond == "Y" && pageObj.niceHistSeq2 != undefined) pageObj.niceHistSeq2 = jsonObj.niceHistSeq;
            if(niceType == NICE_TYPE.RWD_PROD && pageObj.niceHistRwdProdSeq != undefined) pageObj.niceHistRwdProdSeq = jsonObj.niceHistSeq;
        }
    });

    // 3. 본인인증 검증결과 후처리
    if(isCheck && niceType == NICE_TYPE.CUST_AUTH) commonPrdProcess(infoObj.authType, infoObj.isSecond);
    return isCheck;
}

// 본인인증 완료 후 DOM 처리
function commonPrdProcess(authType, isSecond){

    var authDesTag= "ulOnlineAuthDesc";
    var authButtonTag= "onlineAuthType";
    var bankTag= "issuedBank";
    var $authBtn= $('#btnNiceAuth');

    if(isSecond == "Y"){ // 두번째 본인인증 태그
        $("#isAuth2").val("1");
        authDesTag= "ulOnlineAuthDesc2";
        authButtonTag= "onlineAuthType2";
        bankTag= "issuedBank2";
        $authBtn= $('#btnNiceAuth2');
    }else{
        $("#isAuth").val("1");
    }

    var description= makeOnlineAuthDesc(authType);
    $("#"+authDesTag).html(description).parent().show();

    if(authType == "X"){
        $("#"+bankTag).show();
        $("#"+authDesTag).parent().hide();
    }else{
        $("#"+bankTag).hide();
    }

    $("input:radio[name="+authButtonTag+"]").each(function(){
        if(authType == $(this).val()){
            $(this).prop("checked", true);
            $authBtn.addClass('is-complete').html($(this).data("btext") +" 완료");
        }
        else $(this).prop("checked", false);
    });
}

// SMS 본인인증 정보 확인
function checkSmsAuthInfo(){

    var result = false;

    var varData = ajaxCommon.getSerializedData({
         reqSeq: pageObj.niceResLogObj.reqSeq
        ,resSeq: pageObj.niceResLogObj.resSeq
    });

    ajaxCommon.getItemNoLoading({
         id: 'checkSmsAuthInfo'
        ,cache: false
        ,url: '/nice/checkSmsAuthInfoAjax.do'
        ,data: varData
        ,async: false
        ,dataType: "json"
    }
    ,function(obj){
        if(obj.RESULT_CODE == AJAX_RESULT_CODE.SUCCESS) result= true;
    });

    return result;
}

/*
    SMS인증 정보 CERT 검사
    [필수값] menuType
    [페이지 별 선택값] name, ncType
*/
function checkSmsAuthInfoWithCert(infoObj){

    var result = {};

    // 선택값 빈문자열 처리
    if(infoObj.name == undefined) infoObj.name= "";
    if(infoObj.ncType == undefined) infoObj.ncType= "";

    var varData = ajaxCommon.getSerializedData({
         reqSeq: pageObj.niceResLogObj.reqSeq
        ,resSeq: pageObj.niceResLogObj.resSeq
        ,menuType: infoObj.menuType
        ,name: infoObj.name
        ,ncType: infoObj.ncType
    });

    ajaxCommon.getItemNoLoading({
             id: 'checkSmsAuthInfoWithCert'
            ,cache: false
            ,url: '/nice/checkSmsAuthInfoWithCert.do'
            ,data: varData
            ,async: false
            ,dataType: "json"
        }
        ,function(obj){
            result = obj;
        });

    return result;
}

// NICEPIN 연동
function getNicePinCi(){
    // 2024-12-03 셀프개통신규는 다른 로직에서 NICEPIN 연동을 하고 있음 / 셀프개통 신규 외에 전부 NICEPIN 연동
    var operType = ajaxCommon.isNullNvl($("#operType").val(),"");
    var onOffType = ajaxCommon.isNullNvl($("#onOffType").val(),"");
    var usimKindsCd = ajaxCommon.isNullNvl($("#usimKindsCd").val(),"");
    var prntsContractNo = ajaxCommon.isNullNvl($("#prntsContractNo").val(),"");

    var name= "";
    var paramR1= "";
    var paramR2= "";
    var contractNum= "";

    var cstmrType= $("input:radio[name=cstmrType]:checked").val();
    if(usimKindsCd == "09" && prntsContractNo != ""){ // esimWatch
        // if(CSTMR_TYPE.NA != cstmrType) return true;
        contractNum= prntsContractNo;
        if (cstmrType == CSTMR_TYPE.NM) { // 청소년
            name= $("#minorAgentName").val();
            paramR1= $("#minorAgentRrn01").val();
            paramR2= $("#minorAgentRrn02").val();
        }
    } else {
        // 모집경로 온라인(0), 온라인셀프개통(5), 온라인해피콜(6)
        if (onOffType == "0" || onOffType == "5" || onOffType == "6") {
            // 셀프개통 신규는 pass
            if (onOffType == "5" && OPER_TYPE.NEW == operType) return true;
            if (pageObj.telAdvice) return true;
            if (cstmrType == CSTMR_TYPE.NA) { // 내국인
                name= $("#cstmrName").val();
                paramR1= $("#cstmrNativeRrn01").val();
                paramR2= $("#cstmrNativeRrn02").val();
            } else if (cstmrType == CSTMR_TYPE.NM) { // 청소년
                name= $("#minorAgentName").val();
                paramR1= $("#minorAgentRrn01").val();
                paramR2= $("#minorAgentRrn02").val();
            } else if (cstmrType == CSTMR_TYPE.FN) { // 외국인
                name= $("#cstmrName").val();
                paramR1= $("#cstmrForeignerRrn01").val();
                paramR2= $("#cstmrForeignerRrn02").val();
            }
        } else {
            return true;
        }
    }

    var preCheckResult= false;

    var varData = ajaxCommon.getSerializedData({
         name: name
        ,paramR1: paramR1
        ,paramR2: paramR2
        ,contractNum: contractNum
        ,ncType: cstmrType == CSTMR_TYPE.NM ? '1' : ''
        ,nName: cstmrType == CSTMR_TYPE.NM ? $("#cstmrName").val() : '' // 미성년자이름
        ,nBirthDate: cstmrType == CSTMR_TYPE.NM ? $.trim($("#cstmrNativeRrn01").val())+ $.trim($("#cstmrNativeRrn02").val()) : '' // 미성년자주민번호
        ,operType : operType
    });

    ajaxCommon.getItemNoLoading({
         id: 'getNicePinCiAjax'
        ,cache: false
        ,async: false
        ,url: '/auth/getNicePinCiAjax.do'
        ,data: varData
        ,dataType: "json"
    }
    ,function(jsonObj){
        niceAuthObj.nicePinObj= jsonObj;

        if (AJAX_RESULT_CODE.SUCCESS == jsonObj.returnCode) {
            preCheckResult= true;
        } else if ("0010" == jsonObj.returnCode) {
            preCheckResult= false;
            MCP.alert('<p class="u-mt--12"><strong class="u-fw--bold">회원정보와 본인인증 정보가<br>일치하지 않습니다.</strong></p><p class="u-mt--12">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</p>');
        } else if ("9999" == jsonObj.returnCode || "9998"== jsonObj.returnCode){
            preCheckResult= false;
            MCP.alert(jsonObj.returnMsg);
        } else {
            preCheckResult= false;
            var pinStepErr= "본인 인증 요청 중 오류가 발생했습니다.<br>다시 시도 바랍니다.";
            if(niceAuthObj.nicePinObj.returnCode == "STEP01"){ // STEP 오류
                pinStepErr= niceAuthObj.nicePinObj.returnMsg;
            }
            MCP.alert(pinStepErr);
        }
    });

    return preCheckResult;
}

// 에러문구 표출
function viewAuthErrorMgs($thatObj, msg){
    if($thatObj.hasClass("mskDvcChg")){
        $thatObj.parent().parent().parent().addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
        return;
    }
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3") ) {
        $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
}

// 본인인증 요청 알람(push) 인증완료 응답 건 로그 기록
function insertNiceTryLog(tryLogObj, cbFn){

    var varData = ajaxCommon.getSerializedData(tryLogObj);

    ajaxCommon.getItem({
         id: 'insertNiceTryLog'
        ,cache: false
        ,url: '/nice/insertNiceTryLogAjax.do'
        ,data: varData
        ,dataType: "json"
    }
    ,function(obj){
        if(typeof cbFn == "function") return cbFn();
    });
};

// 간편 본인인증 요청 전처리
function preProcSimpleAuth(authType, onOffType){

    var result= {};

    var varData = ajaxCommon.getSerializedData({
        authType: authType
       ,onOffType: ajaxCommon.isNullNvl(onOffType, "")
    });

    ajaxCommon.getItemNoLoading({
         id: 'preProcSimpleAuth'
        ,cache: false
        ,async: false
        ,url: '/auth/preProcSimpleAuth.do'
        ,data: varData
        ,dataType: "json"
    }
    ,function(jsonObj){

        if(jsonObj.RESULT_CODE != "00000"){
            MCP.alert("인증 요청에 실패 하였습니다.<br>휴대폰 인증 후 이용 바랍니다.", function(){
                location.reload();
            });
        }

        result= jsonObj;
    });

    return result;
}
