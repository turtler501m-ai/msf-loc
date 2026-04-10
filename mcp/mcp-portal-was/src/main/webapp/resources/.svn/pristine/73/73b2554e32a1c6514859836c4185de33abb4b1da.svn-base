VALIDATE_COMPARE_MSG.selfIssuExprDt = "발급일자의 날짜 형식이 맞지 않습니다. 발급일자를 확인해 주세요.";

var pageObj = {
    niceType:""
    , authObj:{}
    , niceHistSeq:0
    , startTime:0
    , niceResLogObj:{}   //로그을 저장 하기 위한 인증
    , requestKey:""
    , resNo:""
    , checkCnt:0         //callback 호출 건수
    , tlphNo:""
    , eventId:""
    , cstmrType:""
    , nationList: []
}
var fs9Succ = false;

$(document).ready(function (){

    pageObj.cstmrType = $("input:radio[name=cstmrType]:checked").val();

    if (CSTMR_TYPE.NM == pageObj.cstmrType) {
        $("#inpNameCertiTitle").html("가입자 정보(미성년자)");
        $("#radCertiTitle").html("본인인증방법 선택(법정대리인)");
        $("#radIdCardHead").html("신분증 확인(법정대리인)");
    } else if (CSTMR_TYPE.FN == pageObj.cstmrType){
        $('input[name="selfCertType"]').prop('disabled', true);
        $('#selfCertType6').prop('disabled', false);
        $('#selfCertType6').prop('checked', true);

        ajaxCommon.getItemNoLoading({
            id:'getNationList'
            ,cache:false
            ,url:'/getCodeListAjax.do'
            ,data: ajaxCommon.getSerializedData({grpCd:"NATIONLIST"})
            ,dataType:"json"
            ,async:false
        } ,function(objList){
            if (objList.length) {
                pageObj.nationList = objList;

                pageObj.nationList.forEach(function(nation) {
                    $('#cstmrForeignerNation').append('<option value="' + nation.dtlCd + '">' + nation.dtlCdNm + '</option>');
                });
            }
        });
        $("._cstmrForeignerNation").show();
    }

    // 숫자만 입력가능1
    $(".numOnly").keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });


    // 본인인증 전 추가 유효성 검사 > niceAuth.js 참고
    simpleAuthvalidation = function () {
        return true;
    }

    //일괄 동의
    $("#btnStplAllCheck").click(function(){
        $("._agree").prop("checked", $(this).is(':checked'));
        checkRequiredFields();
        applyCheckedWrapList();
    });

    $("._agree").click(function(){
        handleCommonAgreeClick();
    });

    //필수값 체크여부 바뀔때마다 체크
    $("input[type=checkbox][validityyn='Y']").on("change", checkRequiredFields);
    $("#selfInqryAgrmYnFlag").on("change", checkRequiredFields);

    //신분증 종류 별
    $("input:radio[name=selfCertType]").click(function(){
        var thisVal= $(this).val();
        $("._selfIssuNumF").hide();
        $("._driverSelfIssuNumF").hide();

        switch (thisVal) {
            case '01':
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("._divScan").show();
                $("._divScan button").data('type','social');
                $("#imgRadIdCard").attr("src","/resources/images/mobile/content/img_jumin_card.png");
                break;
            case '03':
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("._divScan").hide();
                $("#imgRadIdCard").attr("src","/resources/images/mobile/content/img_welfare_card.png");
                break;
            case '06':
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("._divScan").hide();
                $("#imgRadIdCard").attr("src","/resources/images/mobile/content/img_foreigner_card.png");
                break;
            case '04':
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("._selfIssuNumF").show();
                $("._divScan").hide();
                $("#imgRadIdCard").attr("src","/resources/images/mobile/content/img_merit_card.png");
                break;
            case '02':
                $("#selfIssuExprDt").attr("title", "운전면허증의 발급 일자");
                $("label[for=selfIssuExprDt]").html("운전면허증의 발급 일자");
                $("._driverSelfIssuNumF").show();
                $("._divScan").show();
                $("._divScan button").data('type','driver');

                $("#imgRadIdCard").attr("src","/resources/images/mobile/content/img_driving_license.png");
                break;
            case '05':
                $("#selfIssuExprDt").attr("title", "만료 일자");
                $("label[for=selfIssuExprDt]").html("만료 일자");
                $("._divScan").hide();
                $("#imgRadIdCard").attr("src","/resources/images/mobile/content/img_passport_card.png");
                break;
        }

        if(!fs9Succ) { //페이지 진입 or 고객이 직접 선택하는 경우 , (FS9는 성공 시 신분증타입 선택되는 경우는 제외)
            fnReqFathTgtYn();
        }

        checkRequiredFields();
    });

    
    // 고객 안면인증 URL 요청 (FS8)
    $("#btnFathUrlRqt").click(function(){
        if ($("#isFathTxnRetv").val() == "1") {
            MCP.alert("안면인증을 완료 하였습니다.");
            return false;
        }

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};
        if (validator.validate()) {

            var varData = ajaxCommon.getSerializedData({
                onlineOfflnDivCd : "ONLINE",
                orgId : "1100011741",
                cpntId : "1100011741",
                contractNum : $("#realContractNum").val(),
                operType : "NAC3",
                selfCertType : $("input:radio[name=selfCertType]:checked").val(),
                onOffType : $("#onOffType").val()
            });

            ajaxCommon.getItem({
                    id:'reqFathUrlRqtAjax'
                    ,cache:false
                    ,url:'/appform/reqFathUrlRqtAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,errorCall : function () { //연동 실패시 상담사개통으로 전환
                        KTM.LoadingSpinner.hide(true);
                        MCP.alert("시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
                    }
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        MCP.alert(jsonObj.RESULT_MSG);
                        if(jsonObj.URL_ADR){
                            MCP.alert(jsonObj.URL_ADR);
                        }
                        $('#btnFathTxnRetv').prop('disabled', false);
                        $('#btnFathSkip').prop('disabled', false);
                    } else if("00001" == jsonObj.RESULT_CODE) { //해피콜신청서로 전환
                        MCP.alert(jsonObj.RESULT_MSG, function (){
                            $("#btnOnline").trigger("click");
                        });
                        $("._isFathCert").hide();
                        $("._chkIdCardTitle").show();
                        $("#isFathTxnRetv").val("1");

                    } else if("00002" == jsonObj.RESULT_CODE) {
                        MCP.alert(jsonObj.RESULT_MSG);
                        $("._isFathCert").hide();
                        $("._chkIdCardTitle").show();
                        $("#isFathTxnRetv").val("1");

                    } else {
                        KTM.LoadingSpinner.hide(true);
                        MCP.alert(jsonObj.RESULT_MSG);
                    }
                });
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg() ,function (){
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
        }
    });

    // 고객 안면인증 결과확인 (PUSH / FS9)
    $("#btnFathTxnRetv").click(function(){
        if ($("#isFathTxnRetv").val() == "1") {
            MCP.alert("안면인증을 완료 하였습니다.");
            return false;
        }

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};
        if (validator.validate()) {

            var varData = ajaxCommon.getSerializedData({
                orgId : "1100011741"
                ,cstmrType : $("input:radio[name=cstmrType]:checked").val()
                ,operType : $("#operType").val()
                ,onOffType : $("#onOffType").val()
            });

            ajaxCommon.getItem({
                    id:'reqFathTxnRetvAjax'
                    ,cache:false
                    ,url:'/appform/reqFathTxnRetvAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,errorCall : function () { //연동 실패시 상담사개통으로 전환
                        KTM.LoadingSpinner.hide(true);
                        MCP.alert("시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
                    }
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        MCP.alert(jsonObj.RESULT_MSG);
                        fs9Succ = true; //신분증타입 트리거 안면인증 관련 로직 플래그
                        var selfCertType = jsonObj.selfCertType;        //신분증 타입                        
                        $("input:radio[name=selfCertType][value='" + selfCertType + "']").prop("checked", true).trigger("click");

                        var selfIssuExprDt = jsonObj.selfIssuExprDt;
                        if (selfIssuExprDt && selfIssuExprDt.length === 8) {
                            var formatted = selfIssuExprDt.substring(0, 4) + "-" + selfIssuExprDt.substring(4, 6) + "-" + selfIssuExprDt.substring(6, 8);
                            $("#selfIssuExprDt").val(formatted);       //발급일자
                        }
                        if (selfCertType === "02") { // 운전면허증
                            $("#driverSelfIssuNum").val(jsonObj.driverSelfIssuNum).trigger('input'); //운전면허 번호
                        } else {
                            $("#driverSelfIssuNum").val("");
                        }

                        $("#isFathTxnRetv").val("1");
                        $("._isFathCert").hide();
                        $("._chkIdCardTitle").show();                               //신분증 확인 show

                        $("input:radio[name=selfCertType]").each(function() {
                            if ($(this).val() === selfCertType) {
                                $(this).prop("checked", true);  // 체크
                                $(this).prop("disabled", false); // 활성화
                            } else {
                                $(this).prop("checked", false);
                                $(this).prop("disabled", true);  // 나머지는 비활성화
                            }
                        });

                        $('#selfIssuExprDt').prop('disabled', true);
                        $('#driverSelfIssuNum').prop('disabled', true);

                        $("input:radio[name=selfCertType]").off("click");

                    } else if("00001" == jsonObj.RESULT_CODE) {
                        //안면인증 안정화 기간이 아니고 셀프개통인 경우 
                        MCP.alert(jsonObj.RESULT_MSG, function (){
                            $("#btnOnline").trigger("click");
                        });
                        $("._isFathCert").hide();
                        $("._chkIdCardTitle").show();                               //신분증 확인 show
                        $("#isFathTxnRetv").val("1");

                    } else if("00002" == jsonObj.RESULT_CODE) {
                        MCP.alert(jsonObj.RESULT_MSG);
                        $("._isFathCert").hide();
                        $("._chkIdCardTitle").show();                               //신분증 확인 show
                        $("#isFathTxnRetv").val("1");
                    } else {
                        KTM.LoadingSpinner.hide(true);
                        MCP.alert(jsonObj.RESULT_MSG);
                    }
                });
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg() ,function (){
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
        }
    });


    
    $('#btnOnline').click(function(){
        
        $("._self").hide();
        $("#onOffType").val("0");
        KTM.Dialog.closeAll();
        $("#clauseSimpleOpen").removeAttr("validityyn");
       
        checkRequiredFields();
    });

    updateFathSkipBtnVisibility();
});



function btn_usimChk(){

    var iccId = $("#iccId").val();

    if(iccId == ""){
        MCP.alert("유심 번호를 입력하세요.",function (){
            $("#iccId").focus();
        });
        return false;
    }

    if(iccId.length != 19){
        MCP.alert("유심 번호 자릿수를 확인하세요.",function (){
            $("#iccId").focus();
        });
        return false;
    }
    var varData = ajaxCommon.getSerializedData({
        iccId : $("#iccId").val()
    });
    ajaxCommon.getItem({
        id:'moscIntmMgmtAjax'
        ,cache:false
        ,url:'/msp/moscIntmMgmtAjax.do'
        ,data: varData
        ,dataType:"json"
    }
    ,function(jsonObj){
        if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
            MCP.alert("입력하신 유심번호 사용 가능합니다.");
            $("#iccId").prop("disabled", true);
            $("#iccBtn").text("유심번호 유효성 체크 성공");
            $("#iccBtn").prop("disabled", true);
            $("#iccYn").val("1");
            $("._ocrRecord").prop("disabled", true);
            checkRequiredFields();
        } else{
            var strMsg = "입력하신 유심번호 사용이 불가능 합니다. 확인 후 다시 시도해 주시기 바랍니다.";
            if (jsonObj.RESULT_MSG != null) {
                strMsg = jsonObj.RESULT_MSG;
            }
            checkRequiredFields();
            MCP.alert(strMsg,function (){
                $("#iccId").val('');
                $("#iccYn").val("");
                $("#iccId").focus('');
            });
        }

    });

}

//약관동의
function viewTerms(targetId,param){
    $('#targetTerms').val(targetId);
    openPage('termsPop','/termsPop.do',param);
}

function pop_credit() {
    if ($("#isAuth").val() == "1") {
        MCP.alert("본인 인증를 완료 하였습니다. ");
        return false;
    }

    var thisVal = $("input:radio[name=onlineAuthType]:checked").val();
    // PASS 1단계 인증
    if ("A"==thisVal) {
        thisVal = "M";
    }

    pageObj.niceType = NICE_TYPE.CUST_AUTH ;
    openPage('outLink','/nice/popNiceAuth.do?sAuthType='+thisVal+'&mType=Mobile','');
    return;
}

//네이버
function fn_naverAuth(){
    if ($("#isAuth").val() == "1") {
        MCP.alert("본인 인증를 완료 하였습니다. ");
        return false;
    }

    validator.config={};
    validator.config['cstmrName'] = 'isNonEmpty';
    validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
    validator.config['onlineAuthType2'] = 'isNonEmpty';

    pageObj.niceType = NICE_TYPE.CUST_AUTH ; // niceType 세팅
    $("#naver_id_login_anchor").trigger("click");

}

// TOSS 간편인증 표준창 오픈
function pop_toss(){
    if ($("#isAuth").val() == "1") {
        MCP.alert("본인 인증를 완료 하였습니다. ");
        return false;
    }
    preOpenTossAuthPopup(); // niceAuth.js에서 처리
}

// PASS 2단계 인증
function pop_pass2(){
    if ($("#isAuth").val() == "1") {
        MCP.alert("본인 인증를 완료 하였습니다.");
        return false;
    }

    if ($("#isPassAuth").val() != "1") {
        MCP.alert("PASS 인증 하여 주시기 바랍니다.");
        return false;
    }

    // 계좌 인증
    var el = document.querySelector('#bankAutDialog');
    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
    modal.open();
}

function fnNiceCert(prarObj) {
    pageObj.niceResLogObj = prarObj;
    var strMsg = "고객 정보와 본인인증한 정보가 틀립니다.";

    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    var cstmrNativeRrn ;
    var cstmrName ;
    var orgName = "";
    var orgRrn = "";

    if (cstmrType == CSTMR_TYPE.NA) {
        //내국인
        cstmrNativeRrn = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;
        cstmrName = $.trim($("#cstmrName").val());
        strMsg = '<p class="u-mt--12"><strong class="u-fw--bold">본인인증 정보가 일치하지 않습니다.</strong></p><p class="u-mt--12">입력하신 정보를 확인 후 다시 시도해 주시기 바랍니다.</p>';
    } else if (cstmrType == CSTMR_TYPE.NM) {

        //청소년
        cstmrNativeRrn = $.trim($("#minorAgentRrn01").val()) + $.trim($("#minorAgentRrn02").val()) ;
        cstmrName = $.trim($("#minorAgentName").val());

        orgRrn = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;
        orgName = $.trim($("#cstmrName").val());

        orgRrn = orgRrn.substring(0,6);

        strMsg = '<p class="u-mt--12"><strong class="u-fw--bold">법정대리인 본인인증 정보 및 가입자 주민번호 정보가<br/>일치하지 않습니다.</strong></p><p class="u-mt--12">입력하신 정보를 확인 후 다시 시도해 주시기 바랍니다.</p>';
    } else if (cstmrType == CSTMR_TYPE.FN) {
        //외국인
        cstmrNativeRrn = $.trim($("#cstmrForeignerRrn01").val()) + $.trim($("#cstmrForeignerRrn02").val()) ;
        cstmrName = $.trim($("#cstmrName").val());
        strMsg = '<p class="u-mt--12"><strong class="u-fw--bold">본인인증 정보가 일치하지 않습니다.</strong></p><p class="u-mt--12">입력하신 정보를 확인 후 다시 시도해 주시기 바랍니다.</p>';
    } else {
        MCP.alert("지원하지 않는 고객유형 입니다.");
        return ;
    }
    cstmrNativeRrn = cstmrNativeRrn.substring(0,6);

    //본인인증 비교
    var authInfoJson = {  cstmrName: cstmrName , cstmrNativeRrn: cstmrNativeRrn , cstmrType : cstmrType
        , orgName: orgName , orgRrn: orgRrn
        , authType: prarObj.authType, contractNum : $("#realContractNum").val()};
    var isAuthDone = authCallback(authInfoJson);

    if (isAuthDone) { // 본인인증 최종 성공
        pageObj.authObj= prarObj ;
        MCP.alert("본인인증에 성공 하였습니다.");
        $("input[name=onlineAuthType]").prop("disabled",true); // 본인인증 버튼 disabled 처리
        checkRequiredFields();

        $('#cstmrNativeRrn01').prop('readonly', 'readonly');
        $('#cstmrNativeRrn02').prop('readonly', 'readonly');
        $('#cstmrForeignerRrn01').prop('readonly', 'readonly');
        $('#cstmrForeignerRrn02').prop('readonly', 'readonly');

        $('#minorAgentName').prop('readonly', 'readonly');
        $('#minorAgentRrn01').prop('readonly', 'readonly');
        $('#minorAgentRrn02').prop('readonly', 'readonly');

        return null;
    } else {

        if(niceAuthObj.resAuthOjb.RESULT_CODE == "STEP01" || niceAuthObj.resAuthOjb.RESULT_CODE == "STEP02"){
            strMsg= niceAuthObj.resAuthOjb.RESULT_MSG;
        }
        checkRequiredFields();
        MCP.alert(strMsg);
        return null;
    }
}

//쉐어링 가입
function sharing_reg(){
    VALIDATE_NOT_EMPTY_MSG.iccYn = "유심 유효성 인증을 해주세요.";
    let onOffType = $("#onOffType").val();
    var isValid = true;
    $(".c-form__text").remove();
    validator.config={};

    if (pageObj.cstmrType == CSTMR_TYPE.NM) {
        //청소년
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isTeen';
        validator.config['minorAgentName'] = 'isNonEmpty';
        validator.config['minorAgentRelation'] = 'isNonEmpty';
        validator.config['minorAgentRrn01&minorAgentRrn02'] = 'isJimin';
        validator.config['minorAgentTel'] = 'isPhone';
        validator.config['minorAgentAgrmYn'] = 'isNonEmpty'; //법정대리인 안내사황(필수)

    } else if (pageObj.cstmrType == CSTMR_TYPE.FN) {
        //외국인
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['cstmrForeignerRrn01'] = 'isNonEmpty';
        validator.config['cstmrForeignerRrn02'] = 'isNonEmpty';
    } else {
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
    }

    if ($("#cstmrMobile").length) {
        validator.config['cstmrMobile'] = 'isPhone';
    }
    validator.config['isAuth'] = 'isNonEmpty';
    validator.config['iccYn'] = 'isNonEmpty';

    //신분증 타입
    validator.config['selfCertType1'] = 'isNonEmpty';
    
    //안면인증 
    if($("#fathTrgYn").val() == "Y") {
        validator.config['isFathTxnRetv'] = 'isNonEmpty';
    }
  
    
    validator.config['selfIssuExprDt'] = 'isIssuDate';
    validator.config['selfInqryAgrmYnFlag'] = 'isNonEmpty';

    var selfCertType = $("input:radio[name=selfCertType]:checked").val();
    var cstmrForeignerNation = "";

    if (selfCertType=="02") {
        validator.config['driverSelfIssuNum1'] = 'isNonEmpty';
        validator.config['driverSelfIssuNum2'] = 'isNumFix10';
    } else if (selfCertType=="04") {
        validator.config['selfIssuNum'] = 'isNonEmpty';
    } else if (selfCertType=="06") {
        validator.config['cstmrForeignerNation'] = 'isNonEmpty';
        cstmrForeignerNation = $.trim($("#cstmrForeignerNation").val());
    }

    validator.config['clausePriCollectFlag'] = 'isNonEmpty';
    validator.config['clausePriOfferFlag'] = 'isNonEmpty';
    validator.config['clauseEssCollectFlag'] = 'isNonEmpty';
    validator.config['clauseFathFlag01'] = 'isNonEmpty';
    validator.config['clauseFathFlag02'] = 'isNonEmpty';
    validator.config['notice'] = 'isNonEmpty';

    if (pageObj.cstmrType == CSTMR_TYPE.NM) {
        validator.config['nwBlckAgrmYn'] = 'isNonEmpty';
        validator.config['appBlckAgrmYn'] = 'isNonEmpty';
    }

    if (onOffType == "5" || onOffType == "7" ) {
        validator.config['clauseSimpleOpen'] = 'isNonEmpty';
    }

    if(!validator.validate()){
        checkRequiredFields();
        MCP.alert(validator.getErrorMsg(),function (){
            let errorId = validator.getErrorId();
            $selectObj = $("#"+errorId);
            viewErrorMgs($selectObj, validator.getErrorMsg());
            if ("isAuth" == errorId) {
                $('#onlineAuthType1').focus();
            } else if ("iccYn" == errorId) {
                $('#iccId').focus();
            } else if ("clausePriCollectFlag" == errorId || "clausePriOfferFlag" == errorId || "clauseEssCollectFlag" == errorId ||"clauseFathFlag01" == errorId ||"clauseFathFlag02" == errorId ||  "notice" == errorId ) {
                $('#btnStplAllCheck').focus();
            } else {
                $('#' + validator.getErrorId()).focus();
            }
        });
        isValid = false;
        return false;
    }

    if (!isValid) return;

    // PC2 7003 에러 발생해서 신분증 재입력 할 경우
    var selfCertType = "";
    var selfIssuExprDt = "";
    var selfIssuNum = "";

    if ($("#selfCertSection1").is(":visible")) {
        selfCertType = $("input:radio[name=selfCertType]:checked").val();
        if (selfCertType) {
            selfIssuExprDt = $("#selfIssuExprDt").val();

            if (selfIssuExprDt != null) {
                selfIssuExprDt = selfIssuExprDt.replace(/-/gi,"");
            }

            if (selfCertType == "02") {
                selfIssuNum = $("#driverSelfIssuNum1").val() + $.trim($("#driverSelfIssuNum2").val());
            } else if (selfCertType == "04") {
                selfIssuNum = $("#selfIssuNum").val();
            }
        }
    }

    let minorAgentName = ($("#minorAgentName").val() == null) ? "" : $.trim($("#minorAgentName").val());
    let minorAgentRrn   = ($("#minorAgentRrn01").val() == null) ? "" : $.trim($("#minorAgentRrn01").val())  + $.trim($("#minorAgentRrn02").val());
    let minorAgentRelation   =  ($("#minorAgentRelation").val() == null) ? "" : $.trim($("#minorAgentRelation").val());
    let minorAgentTelFn = "";
    let minorAgentTelMn  = "";
    let minorAgentTelRn  = "";
    var cstmrMobileFn = "";
    var cstmrMobileMn= "";
    var cstmrMobileRn= "";

    if ($("#minorAgentTel").val() != null) {
        var minorAgentTel = $.trim($("#minorAgentTel").val());
        minorAgentTelFn = minorAgentTel.slice(0, minorAgentTel.length-8) ;
        minorAgentTelMn  = minorAgentTel.slice(-8, minorAgentTel.length-4);
        minorAgentTelRn  = minorAgentTel.slice(-4, minorAgentTel.length);
    }

    if ($("#cstmrMobile").length) {
        var cstmrMobile = $.trim($("#cstmrMobile").val());
        cstmrMobileFn = cstmrMobile.slice(0, cstmrMobile.length-8) ;
        cstmrMobileMn  = cstmrMobile.slice(-8, cstmrMobile.length-4);
        cstmrMobileRn  = cstmrMobile.slice(-4, cstmrMobile.length);
    }

    var clausePriOfferFlag = $("#clausePriOfferFlag").is(":checked") ? "Y":"N";
    var clausePriCollectFlag = $("#clausePriCollectFlag").is(":checked") ? "Y":"N";
    var clauseEssCollectFlag = $("#clauseEssCollectFlag").is(":checked") ? "Y":"N";
    var clauseFathFlag = $("#clauseFathFlag01").is(":checked") && $("#clauseFathFlag02").is(":checked") ? "Y":"N";
    var personalInfoCollectAgree = $("#personalInfoCollectAgree").is(":checked") ? "Y":"N"; // 고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의
    var clausePriAdFlag = $("#clausePriAdFlag").is(":checked") ? "Y":"N"; // 개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의
    var othersTrnsAgree = $("#formOthersTrnsAgree").is(":checked") ? "Y":"N"; // 혜택 제공을 위한 제3자 제공 동의 : M모바일
    var othersTrnsKtAgree = $("#formOthersTrnsKtAgree").is(":checked") ? "Y":"N"; // 혜택 제공을 위한 제3자 제공 동의 : KT
    var othersAdReceiveAgree = $("#othersAdReceiveAgree").is(":checked") ? "Y":"N"; // 제3자 제공관련 광고 수신 동의
    var nwBlckAgrmYn = "" ;
    var appBlckAgrmYn = "" ;
    
    var fathTrgYn = $("#fathTrgYn").val();
    
    if (pageObj.cstmrType == CSTMR_TYPE.NM) {
        nwBlckAgrmYn = $("#nwBlckAgrmYn").is(":checked") ? "Y":"N";
        appBlckAgrmYn= $("#appBlckAgrmYn").is(":checked") ? "Y":"N";
    }

    var varData = ajaxCommon.getSerializedData({
        contractNum : $("#contractNum").val()
        , reqUsimSn : $("#iccId").val()
        , cstmrType : pageObj.cstmrType
        , selfCertType : selfCertType
        , selfIssuExprDt : selfIssuExprDt
        , selfIssuNum : selfIssuNum
        , onOffType : onOffType
        , minorAgentName : minorAgentName
        , minorAgentRrn : minorAgentRrn
        , minorAgentTelFn : minorAgentTelFn
        , minorAgentTelMn : minorAgentTelMn
        , minorAgentTelRn : minorAgentTelRn
        , minorAgentRelation : minorAgentRelation
        , minorAgentAgrmYn : $("#minorAgentAgrmYn").is(":checked") ? "Y":"N"
        , cstmrForeignerNation : cstmrForeignerNation
        , cstmrMobileFn : cstmrMobileFn
        , cstmrMobileMn : cstmrMobileMn
        , cstmrMobileRn : cstmrMobileRn
        , clausePriOfferFlag : clausePriOfferFlag
        , clausePriCollectFlag : clausePriCollectFlag
        , clauseEssCollectFlag : clauseEssCollectFlag
        , personalInfoCollectAgree : personalInfoCollectAgree
        , othersTrnsKtAgree : othersTrnsKtAgree
        , othersAdReceiveAgree : othersAdReceiveAgree
        , clausePriAdFlag : clausePriAdFlag
        , othersTrnsAgree : othersTrnsAgree
        , nwBlckAgrmYn : nwBlckAgrmYn
        , appBlckAgrmYn : appBlckAgrmYn
        , clauseFathFlag : clauseFathFlag
        , fathTrgYn : fathTrgYn
    });

    if (onOffType == "5" || onOffType == "7" ) {
        fnSaveDataSharingSelf(varData);
    } else {
        fnSaveDataSharing(varData);
    }
}


    var fnSaveDataSharing = function(varData) {
        var confirm = "신청하시겠습니까?";

        KTM.Confirm(confirm, function (){
            $('#sharing_reg').addClass('is-disabled');
            KTM.LoadingSpinner.show();
            ajaxCommon.getItemNoLoading({
                id:'saveDataSharingStep1'
                , cache:false
                , url:'/appform/saveDataSharingAjax.do'
                , data: varData
                , dataType:"json"
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    pageObj.requestKey = jsonObj.REQUEST_KET ;
                    pageObj.resNo = jsonObj.RES_NO ;
                    pageObj.checkCnt = 0;

                    $("#selfShareSuccesYn").val(pageObj.requestKey);

                    sendScanImage();

                    ajaxCommon.createForm({
                        method:"post"
                        ,action:"/m/content/dataSharingStep4.do"
                    });
                    ajaxCommon.attachHiddenElement("opmdSvcNo","9999");
                    ajaxCommon.attachHiddenElement("ncn",$("#contractNum").val());
                    ajaxCommon.formSubmit();

                } else {
                    var strHtml = "쉐어링 가입 실패했습니다.";
                    KTM.LoadingSpinner2.hide();
                    if(jsonObj.ERROR_MSG != null) {
                        strHtml = jsonObj.ERROR_MSG;
                    }
                    MCP.alert(strHtml);
                }
            });
            this.close();
        });
    };



    /*    신청서 테이블에 저장_데이터 쉐어링 DATA 생성,사전체크 및 고객생성(PC0)   */
    var fnSaveDataSharingSelf = function(varData) {
        var confirm = "개통하시겠습니까?";


        KTM.Confirm(confirm, function (){
            $('#sharing_reg').addClass('is-disabled');
            KTM.LoadingSpinner2.show();

            ajaxCommon.getItemNoLoading({
                    id:'saveDataSharingStep1'
                    , cache:false
                    , url:'/appform/saveDataSharingStep1Ajax.do'
                    , data: varData
                    , dataType:"json"
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        pageObj.requestKey = jsonObj.REQUEST_KET ;
                        pageObj.resNo = jsonObj.RES_NO ;
                        pageObj.checkCnt = 0;
                        fnSelfPreOpenCallBack();
                    } else {
                        var strHtml = "쉐어링 가입 실패했습니다.";
                        KTM.LoadingSpinner2.hide();
                        if(jsonObj.ERROR_MSG != null) {
                            strHtml = jsonObj.ERROR_MSG;
                        }
                        MCP.alert(strHtml);
                    }
                });
            this.close();
        });
    };


    /*    PC2확인  */
    var fnSelfPreOpenCallBack = function() {
        var varData = ajaxCommon.getSerializedData({
            resNo:pageObj.resNo
            ,contractNum : $("#contractNum").val()   // ncn
            ,prgrStatCd:"PC2"
        });
        ajaxCommon.getItemNoLoading({
                id:'conPreCheckAjax'
                , cache:false
                , url:'/appform/conPreCheckAjax.do'
                , data: varData
                , dataType:"json"
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    fnSaveDataSharingStep2();
                } else if ("9999" ==  jsonObj.RESULT_CODE) {
                    if (pageObj.checkCnt < 21) {
                        ++pageObj.checkCnt
                        setTimeout(function(){
                            fnSelfPreOpenCallBack();
                        }, 5000);
                    } else {
                        KTM.LoadingSpinner2.hide(true);
                        MCP.alert("아직 고객 사전체크 결과가 확인되지 않습니다.<br>확인까지 최대 2분이 소요 될 수 있습니다.",function (){
                            pageObj.checkCnt = 0;
                            fnSelfPreOpenCallBack();
                        });
                    }
                } else if("0001" == jsonObj.RESULT_CODE && "RESULT_7003" == jsonObj.OSST_RESULT_CODE){
                    KTM.LoadingSpinner2.hide(true);
                    MCP.alert("고객정보 확인에 실패하였습니다.<br><br>확인버튼을 눌러 신분증 정보를 입력 후<br>다시 시도 해 주세요.");
                    $("#driverSelfIssuNum1, #driverSelfIssuNum2, #selfIssuNum, #selfIssuExprDt").val("");
                    $("#selfInqryAgrmYnFlag").prop("checked",false);
                    $("#selfCertSection1, #selfCertSection2, #selfCertSection3, #selfCertSection4").show();
                    $("input:radio[name=selfCertType][value='01']").prop("checked", true);
                    $("input:radio[name=selfCertType][value='01']").trigger("click");
                    checkRequiredFields();
                } else {
                    var strHtml = "쉐어링 가입 실패했습니다.(PC2)";
                    KTM.LoadingSpinner2.hide();
                    MCP.alert(strHtml);
                }
            });
    };

    /*    번호조회(NU1) , 번호예약/취소(NU2)     */
    var fnSaveDataSharingStep2 = function() {
        var varData = ajaxCommon.getSerializedData({
            contractNum : $("#contractNum").val()
            , reqUsimSn : $("#iccId").val()
        });

        ajaxCommon.getItemNoLoading({
                id:'saveDataSharingStep2'
                , cache:false
                , url:'/appform/saveDataSharingStep2Ajax.do'
                , data: varData
                , dataType:"json"
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    pageObj.tlphNo = jsonObj.TLPH_NO ;
                    fnSaveDataSharingStep3();
                } else {
                    var strHtml = "쉐어링 가입 실패했습니다.(step2)";
                    KTM.LoadingSpinner2.hide();
                    if(jsonObj.ERROR_MSG != null) {
                        strHtml = jsonObj.ERROR_MSG;
                    }
                    MCP.alert(strHtml);
                }
            });
    }

    /*    개통및수납(OP0)    */
    var fnSaveDataSharingStep3 = function() {
        var varData = ajaxCommon.getSerializedData({
            contractNum : $("#contractNum").val()
            , reqUsimSn : $("#iccId").val()
        });

        ajaxCommon.getItemNoLoading({
                id:'saveDataSharingStep3'
                , cache:false
                , url:'/appform/saveDataSharingStep3Ajax.do'
                , data: varData
                , dataType:"json"
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    pageObj.checkCnt = 0;
                    fnSelfOpenCallback();
                } else {
                    var strHtml = "쉐어링 가입 실패했습니다.(step2)";
                    KTM.LoadingSpinner2.hide();
                    if(jsonObj.ERROR_MSG != null) {
                        strHtml = jsonObj.ERROR_MSG;
                    }
                    MCP.alert(strHtml);
                }
            });
    }

    /*    개통및수납(OP2) 확인     */
    var fnSelfOpenCallback = function() {
        var varData = ajaxCommon.getSerializedData({
            resNo:pageObj.resNo
            ,prgrStatCd:"OP2"
        });

        ajaxCommon.getItemNoLoading({
            id:'conPreCheckAjax'
            ,cache:false
            ,url:'/appform/conPreCheckAjax.do'
            ,data: varData
            ,dataType:"json"
            , async:false
        },function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                $("#selfShareSuccesYn").val(pageObj.requestKey);
                $("#opmdSvcNo").val(pageObj.tlphNo.trim());

                sendScanImage();
                sharingYnChg();
                KTM.LoadingSpinner2.hide();
            } else if ("9999" ==  jsonObj.RESULT_CODE) {
                if (pageObj.checkCnt < 21) {
                    ++pageObj.checkCnt
                    setTimeout(function(){
                        fnSelfOpenCallback();
                    }, 5000);  //20000
                } else {
                    KTM.LoadingSpinner2.hide(true);
                    MCP.alert("아직 개통 상태 정보가 확인되지 않습니다.<br>확인까지 최대 2분이 소요 될 수 있습니다.");
                }
            } else {
                var strHtml = "쉐어링 가입 실패했습니다.(OP2)";
                KTM.LoadingSpinner2.hide();
                MCP.alert(strHtml);

            }
        });
    };


function sharingYnChg(){


    var varData = ajaxCommon.getSerializedData({
        ncn : $("#contractNum").val()
        , opmdSvcNo : $("#opmdSvcNo").val()
        , selfShareYn :$("#selfShareYn").val()
    });

    ajaxCommon.getItem({
            id:'insertOpenRequestAjax'
            ,cache:false
            ,url:'/m/content/insertOpenRequestAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){


            if(jsonObj.RESULT_CODE == 'S'){

                ajaxCommon.createForm({
                    method:"post"
                    ,action:"/m/content/dataSharingStep4.do"
                });

                ajaxCommon.attachHiddenElement("opmdSvcNo",$("#opmdSvcNo").val());
                ajaxCommon.attachHiddenElement("ncn",$("#contractNum").val());
                ajaxCommon.formSubmit();

            } else{
                MCP.alert("쉐어링 가입 실패했습니다.")
                $('#sharing_reg').removeClass('is-disabled');
            }
        }

    );
}

function sendScanImage(){
    var varData = ajaxCommon.getSerializedData({
        requestKey : $("#selfShareSuccesYn").val()
        // , cstmrName : $("#cstmrName").val()
    });
    //비동기
    ajaxCommon.getItemNoLoading({
            id:'sendScan'
            ,cache:false
            ,async:false
            ,url:'/appform/sendScanAjax.do'
            ,data: varData
            ,dataType:"json"
            ,errorCall : function () {
                //nothing
            }
        }
        ,function(jsonObj){
            //nothing
        });
}

//취소
function sharing_cancel(){
    ajaxCommon.createForm({
        method:"post"
        ,action:"/m/content/dataSharingStep2.do"
    });

    ajaxCommon.formSubmit();
}

function viewErrorMgs($thatObj, msg ) {
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") ) {
        $thatObj.parent().parent().addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
}

//개통 요청 버튼 활성화/비활성화
function checkRequiredFields() {
    var allChecked = true;

    $("input[type=checkbox][validityyn='Y']").each(function () {
        if (!$(this).is(":checked")) {
            allChecked = false;
        }
    });

    if ($("#selfCertSection1").is(":visible")) {
        var idSelected = $("input[name='selfCertType']:checked").val();
        var dateValue = $("#selfIssuExprDt").val().trim();
        var isChecked = $("#selfInqryAgrmYnFlag").is(":checked");

        if (!(idSelected && dateValue && isChecked)) {
            allChecked = false;
        }
    }

    if (allChecked) {
        $("#sharing_reg").removeClass("is-disabled");
    } else {
        $("#sharing_reg").addClass("is-disabled");
    }
}

function targetTermsAgree() {
    if (pageObj.eventId === "othersTrnsAllAgree") {
        if (!$("#othersTrnsAgree").is(':checked')) {
            $("#othersTrnsAgree").trigger('click');
        }

        if ($("#othersTrnsAgree").is(':checked') && !$("#othersTrnsKtAgree").is(':checked')) {
            $("#othersTrnsKtAgree").trigger('click');
        }

        return;
    }

    var agree = $('#' + pageObj.eventId);
    if (agree.is(':checked')) {
        return;
    }

    agree.trigger('click');
    checkRequiredFields();
};

function fnSetEventId(eventId){
    pageObj.eventId = eventId;
};

function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

$("#driverSelfIssuNum").on('input', function () {
    let raw = $(this).val().replace(/[^0-9]/g, ''); // 숫자만 추출
    let formatted = '';

    $("#driverSelfIssuNum1").val(raw.substring(0, 2));
    $("#driverSelfIssuNum2").val(raw.substring(2, 12));


    if (raw.length > 0) formatted += raw.substring(0, 2);
    if (raw.length > 2) formatted += '-' + raw.substring(2, 4);
    if (raw.length > 4) formatted += '-' + raw.substring(4, 10);
    if (raw.length > 10) formatted += '-' + raw.substring(10, 12);

    $(this).val(formatted);
});

var handleOptionalAgreeClick = function(agree) {
    if (agree.checked && !validateRequiredAgree(getRequiredAgreeChecks(agree), true)) {
        agree.checked = false;
        return false;
    }

    if (agree.id === "othersTrnsAgree") {
        $("#formOthersTrnsAgree").prop("checked", agree.checked);
    }
    if (agree.id === "othersTrnsKtAgree") {
        $("#formOthersTrnsKtAgree").prop("checked", agree.checked);
    }

    uncheckSecondaryIfRequiredUnchecked();
    applyCheckedWrapList();

}

var validateRequiredAgree = function(requiredAgreeChecks, isAlert) {
    var isCheckedRequiredAgrees = true;
    if (requiredAgreeChecks.length > 0) {
        requiredAgreeChecks.each(function(index, requiredAgree) {
            if (!requiredAgree.checked) {
                isAlert && MCP.alert(`${getAgreeName(requiredAgree.id)}가 필요합니다.`);

                isCheckedRequiredAgrees = false;
                return false;
            }
        });
    }

    return isCheckedRequiredAgrees;
}

var handleOptionalAgreeWrapClick = function(agreeWrap) {
    if (agreeWrap.checked && !validateRequiredAgreeInWrap(agreeWrap)) {
        agreeWrap.checked = false;
        return true;
    }

    checkChildAgrees(agreeWrap);
    uncheckSecondaryIfRequiredUnchecked();
    applyCheckedWrapList();
}

var validateRequiredAgreeInWrap = function(agreeWrap) {
    var agreeChecks = getAgreeChecks(agreeWrap);
    var secondaryAgreeChecks = agreeChecks.filter('[required-agree-id]');
    for (var i = 0; i < secondaryAgreeChecks.length; i++) {
        var requiredAgreeChecks = getRequiredAgreeChecks(secondaryAgreeChecks[i]);
        if (!validateRequiredAgree(requiredAgreeChecks.not(agreeChecks), false)) {
            MCP.alert(`${getAgreeName(secondaryAgreeChecks[i].getAttribute("required-agree-id"))}가 필요합니다.`);
            return false;
        }
    }
    return true;
}

var getRequiredAgreeChecks = function(agree) {
    var requiredAgreeId = agree.getAttribute('required-agree-id');
    if (requiredAgreeId) {
        return getAgreeChecks($("#" + requiredAgreeId)[0]);
    }
    return [];
}

var getAgreeName = function(agreeId) {
    return $("label[for='" + agreeId + "']")
        .contents()
        .filter(function() {
            return this.nodeType === Node.TEXT_NODE;
        })
        .text().trim();
}

var uncheckSecondaryIfRequiredUnchecked = function() {
    $("[required-agree-id]").each(function(index, agree) {
        if (!validateRequiredAgree(getRequiredAgreeChecks(agree), false)) {
            agree.checked = false;
        }
    });
    handleCommonAgreeClick();
}

var applyCheckedWrapList = function() {
    $(".agreeWrap").each(function(index, agreeWrap) {
        applyCheckedWrap(agreeWrap);
    });
}

var applyCheckedWrap = function (agreeWrap) {
    var isCheckedWrap = true;
    var agreeChecks = getAgreeChecks(agreeWrap);
    agreeChecks.each(function(index, agreeCheck) {
        if (!agreeCheck.checked) {
            isCheckedWrap = false;
            return false;
        }
    });

    agreeWrap.checked = isCheckedWrap;
}

var checkChildAgrees = function (agreeWrap) {
    var agreeChecks = getAgreeChecks(agreeWrap);
    agreeChecks.each(function(index, agreeCheck) {
        agreeCheck.checked = agreeWrap.checked;
    });
    handleCommonAgreeClick();
}

var getAgreeChecks = function (agreeWrap) {
    return $(agreeWrap).closest('.c-agree__item').find('.agreeCheck');
}

var initOthersTrnsAgrees = function() {
    $("#othersTrnsAgree").prop("checked", $("#formOthersTrnsAgree").is(":checked"));
    $("#othersTrnsKtAgree").prop("checked", $("#formOthersTrnsKtAgree").is(":checked"));
    $("#othersTrnsAgree, #othersTrnsKtAgree").click(function() {
        handleCommonAgreeClick();
    });
}

var handleCommonAgreeClick = function() {
    var isAllCheck= true;
    $("._agree").each(function(){
        if (!$(this).is(':checked')) {
            isAllCheck= false;
            return false;
        }
    });
    $("#btnStplAllCheck").prop("checked", isAllCheck);

    checkRequiredFields();
}

// 고객 안면인증 대상 여부 조회(FT0)
function fnReqFathTgtYn() {

    $("#isFathTxnRetv").val("");
    $('#btnFathTxnRetv').prop('disabled', true);
    $('#btnFathSkip').prop('disabled', true);

    var varData = ajaxCommon.getSerializedData({
        onlineOfflnDivCd : "ONLINE",
        orgId : "1100011741",
        selfCertType : $("input:radio[name=selfCertType]:checked").val(),
        onOffType : $("#onOffType").val()
    });

    ajaxCommon.getItem({
            id:'reqFathTgtYnAjax'
            ,cache:false
            ,url:'/appform/reqFathTgtYnAjax.do'
            ,data: varData
            ,dataType:"json"
            ,errorCall : function () {
                KTM.LoadingSpinner.hide(true);
                MCP.alert("시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
                $("input:radio[name=selfCertType]").prop("checked", false);
            }
        }
        ,function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                //안면인증 페이지 표출
                $("._isFathCert").show();
                $("._chkIdCardTitle").hide();
                $("#fathTrgYn").val("Y");

            } else if("00001" == jsonObj.RESULT_CODE) { //해피콜신청서로 전환
                MCP.alert(jsonObj.RESULT_MSG, function (){
                    $("#btnOnline").trigger("click");
                });

                $("._isFathCert").hide();
                $("._chkIdCardTitle").show();
                $("#fathTrgYn").val("N");
            } else if("00002" == jsonObj.RESULT_CODE) { //안면인증 대상 아님
                $("._isFathCert").hide();
                $("._chkIdCardTitle").show();
                $("#fathTrgYn").val("N");
            } else { //연동에러 및 실패 메세지
                KTM.LoadingSpinner.hide(true);
                MCP.alert(jsonObj.RESULT_MSG);
                $("input:radio[name=selfCertType]").prop("checked", false);
            }
        });
}

function requestFathSkip() {
    var varData = ajaxCommon.getSerializedData({
        orgId : "1100011741"
    });

    KTM.LoadingSpinner.show();
    ajaxCommon.getItem({
        id:'requestCustFathTxnSkipAjax'
        ,cache:false
        ,url:'/appform/requestCustFathTxnSkipAjax.do'
        ,data: varData
        ,dataType:"json"
        ,errorCall : function () {
            KTM.LoadingSpinner.hide(true);
            MCP.alert("시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
        }
    }, function(jsonObj) {
        KTM.LoadingSpinner.hide(true);
        if (jsonObj.RESULT_CODE === AJAX_RESULT_CODE.SUCCESS) {
            MCP.alert(jsonObj.RESULT_MSG);
            $("#isFathTxnRetv").val("1");
            $("._isFathCert").hide();
            $("._chkIdCardTitle").show();
        } else if (jsonObj.RESULT_CODE === "0002") {
            MCP.alert("이미 안면인증 SKIP 완료되었습니다.");
            $("#isFathTxnRetv").val("1");
            $("._isFathCert").hide();
            $("._chkIdCardTitle").show();
        } else if (jsonObj.RESULT_CODE === "0001"
            || jsonObj.RESULT_CODE === "0003"
            || jsonObj.RESULT_CODE === "0005") {
            MCP.alert("안면인증 SKIP에 실패하였습니다.<br>안면인증 URL 받기를 다시 진행 부탁드립니다.");
        } else if (jsonObj.RESULT_CODE === "0004") {
            MCP.alert("안면인증 SKIP이 불가능합니다.");
        }
    });
}

function updateFathSkipBtnVisibility() {
    ajaxCommon.getItem({
        id:'isEnabledFT1'
        ,cache:false
        ,url:'/appform/isEnabledFT1.do'
        ,dataType:"json"
    }, function(jsonObj) {
        if (jsonObj.isEnabledFT1) {
            $("#btnFathSkip").show();
        }
    });
}