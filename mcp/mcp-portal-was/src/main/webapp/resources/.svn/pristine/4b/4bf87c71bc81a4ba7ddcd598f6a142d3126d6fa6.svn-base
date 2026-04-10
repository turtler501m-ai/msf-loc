
VALIDATE_NOT_EMPTY_MSG.reqWantNumberN = "가입희망번호 번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.reqCnt = "가입희망번호 번호를 설정해 주세요.";
VALIDATE_NUMBER_MSG.reqWantNumberN = "가입희망번호 번호를 입력해 주세요.";
VALIDATE_COMPARE_MSG.selfIssuExprDt = "발급일자의 날짜 형식이 맞지 않습니다. 발급일자를 확인해 주세요.";

var stepInfo = {
    isInitStep2: true,
    isInitStep3: true,
    isInitStep4: true
}

var pageObj = {
    niceType:""
    , initAddition:0
    , applDataForm:{}
    , authObj:{}
    , niceHistInsrProdSeq:0
    , startTime:0
    , eventId:""
    , step:1
    , additionBetaList:[]
    , additionKeyList:[]
    , additionTempKeyList:[]
    , reqAddition:[]
    , reqAdditionPrice:0
    , insrProdList:[]
    , insrProdCd:""
    , insrProdObj:null
    , addrFlag:""
    , requestKey:0
    , resNo:0
    , niceResSmsObj:{}
    , niceResLogObj:{}
    , checkCnt:0
    , prdtNm:""
    , modelSalePolicyCode : ""
    , modelId:""
    , telAdvice:false
    , prmtIdList:[]
    , prdtIdList:[]
    , giftList:[]
    , fnReqPreCheckCount:0
    , priceLimitObj:null
    , usimOrgnId:""
    , moveCompanyList: []
    , osstResultCode:"99999"
    , moveCompanyGroup2:""
    , openMarketId: ""
    , npExceptionList : null
    , additionList:[]
    , additionCatchCallKey:49  //캐치콜 플러스 키값
    , additionCatchCall:null
    , nationList: []
    , checkCaution:"N"
    , requestKeyTemp:0
}


var stepText = ["본인확인·약관동의","유심정보·신분증 확인","가입신청 정보","부가서비스 정보","납부정보·가입정보 확인"]

var fs9Succ = false;

$(document).ready(function(){

    pageObj.modelSalePolicyCode = $("#modelSalePolicyCode").val();
    pageObj.modelId = $("#modelId").val();
    pageObj.prdtNm = $("#prdtNm").val();
    pageObj.openMarketId = $("#openMarketId").val();

    KTM.datePicker('.flatpickr');
    pageObj.applDataForm = APPL_DATA_FORM();

    pageObj.applDataForm.openMarketReferer = $("#openMarketReferer").val();
    pageObj.applDataForm.jehuRefererYn = $("#jehuRefererYn").val();

    $('[data-tpbox^=#deli_tp]').focus(function(){
        $(this).next().css("visibility","visible");
    });
    $('[data-tpbox^=#deli_tp]').blur(function(){
        $(this).next().css("visibility","hidden");
    });

    var wrap = document.querySelector('.ly-wrap');
    wrap.classList.add('use-bs');

    getPriceLimitInfo();                   // 요금제 나이제한 정보 조회
    getJehuInfo();                         // 제휴처 및 제휴사 약관 세팅
    setBottomFloatingView();               // 하단 플로팅바 세팅

    getAdditionList("F");                  // 기본제공 부가서비스 조회

    // 기본제공 부가서비스 체크 처리
    $("input:checkbox[name=additionName]").each(function(index){
        $(this).prop("checked", "checked");
    });

    setAdditionListView();                 // 기본제공 부가서비스 화면세팅
    getAdditionBetaList();                 // 동시선택 불가 부가서비스 조회
    getJoinUsimPriceInfo();                // 가입비 세팅


    // --------------------- 이벤트 부여 시작 ----------------------------

    /** [공통] 다음버튼 클릭 이벤트 */
    $('._btnNext').click(function(){

        // 하단 플로팅바 close 처리
        if ($("#bs_acc_btn").hasClass("is-active") ) {
            $("#bs_acc_btn").trigger("click");
        }
        var onOffType = $("#onOffType").val() ;
        var operType = $("#operType").val();

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        if (pageObj.step == 1 && isValidateStep1() ) {

            $('._btnNext').addClass('is-disabled');

            // 명의도용 IP 조회
            ajaxCommon.getItem(
                {
                    id:'selectIdentityIp'
                    ,cache:false
                    ,async:false
                    ,url:'/appForm/selectIdentityIp.do'
                    ,dataType:"json"
                },function(obj){
                    var isStolenIp = obj.RESULT_CODE
                    if (isStolenIp && onOffType == "5") { //셀프개통 신규가입, 번호이동
                        MCP.alert("접속하신 IP는 부정사용 개통 신고되어 있는<br> " +
                            "IP로 셀프개통이 제한되어 있습니다.<br>" +
                            "가입을 원하시면 『상담사 개통 신청』 을 통해<br>" +
                            "가입신청 가능하며 IP 접속제한 해제를<br>" +
                            "희망하실 경우 고객센터(114/무료)로<br>" +
                            "연락주시면 본인인증 후 제한 해제 가능합니다.");

                    } else {
                        if(fnSaveTempStep1()){
                            setStep(2);
                            if(operType == OPER_TYPE.NEW && onOffType == "5"){
                                $("._btnNext").html("개통사전체크확인");
                            }
                            isValidateNonEmptyStep2();
                        }
                    }
                }
            );
            return;
        } else if (pageObj.step == 2 && isValidateStep2() ) {

            $('._btnNext').addClass('is-disabled');
            fnSaveTempStep2();

            if(operType == OPER_TYPE.NEW && onOffType == "5"){
                KTM.LoadingSpinner.show();
                setTimeout(function () {
                    fnReqPreCheck(false);
                }, 300);
            } else {
                if( onOffType == "5"){
                    // 사전동의 제외 통신사리스트 조회
                    getNpExceptionList();

                    var mpCode= $("#moveCompany option:selected").attr("mpCode");

                    if ($("#isNpPreCheck").val() == "1") {
                        $("._btnNext").html("동의 완료");    //개통사전체크확인
                    } else if(isNpExceptionCompany(mpCode)){
                        $("._btnNext").html("개통사전체크확인");
                    } else{
                        $("._btnNext").html("사전동의 요청");
                    }
                }
                setStep(3);
                isValidateNonEmptyStep3();
            }
            return;
        } else if (pageObj.step == 3 && isValidateStep3() ) {
            // 번호이동 신청서 중복 작성 체크
            if (operType == OPER_TYPE.MOVE_NUM) {
                if (chkReqDup()) {
                    return false;
                }
            }

            if (operType == OPER_TYPE.NEW && onOffType != "5") {
                if (containsGoldNumber()) {
                    return false;
                }
            }

            $('._btnNext').addClass('is-disabled');
            fnSaveTempStep3();
            if(operType != OPER_TYPE.NEW && onOffType == "5" ){
                var isNpPreCheck = $("#isNpPreCheck").val();
                var mpCode = $("#moveCompany option:selected").attr("mpCode");

                KTM.LoadingSpinner.show();

                if (isNpPreCheck == "1" || isNpExceptionCompany(mpCode)) {
                    setTimeout(function () {
                        fnReqPreCheck(false); //개통사전체크요청 PC0
                    }, 300);
                } else {
                    setTimeout(function () {
                        fnReqPreMoveAut(); ////사전동의 요청 NP1
                    }, 300);
                }
            } else {
                setStep(4);
            }
            return;
        } else if (pageObj.step == 4 && isValidateStep4()) {

            $('._btnNext').addClass('is-disabled');

            if (isValidateGift()) {   //사은품 확인
                nextStepFive();
            } else {
                KTM.Confirm('사은품을 미선택 하셨습니다. <br/>미선택 시 사은품이 지급되지 않으니<br/> 사은품을 선택해주세요.<br/><br/> 확인을 누르실 경우 이전화면으로 돌아갑니다.'
                        , function () {
                            this.close();
                            isValidateNonEmptyStep4();
                            return ;
                        },function() {
                            nextStepFive();
                            return ;
                        },   {
                            confirmText: "\uD655\uC778",
                            cancelText: "\uB2E4\uC74C"
                          });
            }

            return;
        } else if (pageObj.step == 5 && isValidateStep5()) {

            //2026.03 개통 후 주의사항 확인 후 개통진행(신청서작성 완료)
            //기존에 있존 로직을 fnSaveFinal로 이동
            if(pageObj.checkCaution == "N") { //pageObj.checkCaution이 N일때에만
                //2026.03 주문완료 or 개통요청시 주의사항안내
                $("#simpleDialog ._title").html("개통 후 주의사항 안내");
                $("#simpleDialog ._detail").html("KAIT(한국정보통신진흥협회) 명의도용 방지 정책에 따라 하나의 휴대폰으로는 여러명의의 휴대폰을 사용할 수 없습니다.</br></br>" +
                    "개통 후 사용하시려는 휴대폰에 USIM 또는 eSIM을 인식 하실 경우 해당 휴대폰 사용자 인증을 요청하는 안내문자가 발송될 수 있으며,</br>" +
                    "그러한 경우 해당 인증 URL을 통한 본인인증이 필요하오니 꼭 인증 부탁드립니다.</br></br>" +
                    "※ 정상적으로 인증되지 않을 경우 관련 약관에 의거하여 2일 이내 자동 이용정지 및 향후 직권 해지처리 될 수 있습니다.");
                $("._simpleDialogButton").hide();
                $("#cautionFlag").prop("checked", false);
                $("#divCheckCaution").show();

                var el = document.querySelector('#simpleDialog');
                var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                modal.open();
            }else {
                fnSaveFinal();
            }

        } else if (pageObj.step == 6 ) {
            $('._btnNext').addClass('is-disabled');
            fnSelfOpenCallback(); // 개통결과 확인 OP2
            return;
        }
    });

    //2026.03 개통 후 주의사항 확인 후 개통진행(신청서작성 완료)
    $("#cautionCheck").click(function() {
        if(!$('#cautionFlag').is(':checked')){
            MCP.alert("유의사항 이해여부 확인 바랍니다.");
        }else {
            pageObj.checkCaution = "Y";
            fnSaveFinal();
            //KTM.Dialog.closeAll();
            var el = document.querySelector('#simpleDialog');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.close();
        }
    });

    var fnSaveFinal = function() {
        $('._btnNext').addClass('is-disabled');
        fnSaveTempStep5();

        KTM.LoadingSpinner.show();
        if($("#onOffType").val() != "5"){
            setTimeout(function () {
                fnSaveForm();
            }, 300);
        } else {
            setTimeout(function () {
                fnReqSelf();
            }, 300);
        }
        return;
    }

    /** [공통] 이전버튼 클릭 이벤트 */
    $('#btnBefore').click(function(){
        $('._btnNext').removeClass('is-disabled');

        if (pageObj.step == 2 ) {
            $("._btnNext").html("다음");
            setStep(1);
            return;
        } else if (pageObj.step == 3) {
            $("._btnNext").html("다음");
            setStep(2);
            return;
        } else if (pageObj.step == 4) {
            setStep(3);
            $("._btnNext").html("다음");
            return;
        } else if (pageObj.step == 5 ) {
            setStep(4);
            $("._btnNext").html("다음");
            return;
        }
    });

    $("input:radio[name=cstmrType]").click(function(){
        var thisVal = $(this).val();

        if ($("#isAuth").val() == "1") {
            MCP.alert("본인 인증을 완료 하였습니다. ");
            return false;
        }

        if (thisVal == CSTMR_TYPE.NM) {
            //청소년 요금제 가능 여부
           if (!checkPriceLimitForAge(12)) {
                $(this).prop("checked" , false);
                return false;
            }


            $("._isTeen").show();
            $("._isCommon").hide();
            $("._isForeigner").hide();
            $("._isDefaultText").hide();
            $("._isDefault").show();
            $("._isDefaultMt").addClass("u-mt--0");
            $("#cstmrBillSendCode03").prop("disabled", true);

            $("#inpNameCertiTitle").html("가입자 정보(미성년자)");
            $("#radCertiTitle").html("본인인증방법 선택(법정대리인)");
            $("#radIdCardHead").html("신분증 확인(법정대리인)");
            $('#cstmrType1').attr('disabled', 'disabled');
            $('#cstmrType3').attr('disabled', 'disabled');

        } else if (thisVal == CSTMR_TYPE.FN) {
            $("._isTeen").hide();
            $("._isForeigner").show();
            $("._isCommon").show();
            $("._isDefaultText").hide();
            $("._isDefault").hide();
            $("._isDefaultMt").removeClass("u-mt--0");
            $("#cstmrBillSendCode03").prop("disabled", false);

            $("#inpNameCertiTitle").html("가입자 정보");
            $("#radCertiTitle").html("본인인증 방법 선택");
            $("#radIdCardHead").html("신분증 확인");
            $('#cstmrType1').attr('disabled', 'disabled');
            $('#cstmrType2').attr('disabled', 'disabled');

        } else {
            $("._isTeen").hide();
            $("._isCommon").show();
            $("._isForeigner").hide();
            $("._isDefaultText").show();
            $("._isDefault").show();
            $("._isDefaultMt").addClass("u-mt--0");
            $("#cstmrBillSendCode03").prop("disabled", false);

            $("#inpNameCertiTitle").html("가입자 정보");
            $("#radCertiTitle").html("본인인증 방법 선택");
            $("#radIdCardHead").html("신분증 확인");
            $('#cstmrType2').attr('disabled', 'disabled');
            $('#cstmrType3').attr('disabled', 'disabled');

        }
        isValidateNonEmptyStep1();
    });

    var onOffType = $("#onOffType").val() ;
    if(onOffType == "5"){
        $("._self").show();
        $("._noSelf").hide();
    } else {
        $("._self").hide();
        $("._noSelf").show();
    }


    $("input:radio[name=cstmrType]:checked").trigger("click");

    $("#minorAgentRelation").change(function(){
        isValidateNonEmptyStep1();
    });

    /** [step1] 휴대폰 인증 */
    $("#btnSmsAuth").click(function(){

        // 에러 문구 제거
        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        if ($("#isSmsAuth").val() == "1") {
            MCP.alert("휴대폰 인증을 완료 하였습니다. ");
            return false;
        }

        // 1. 필수값 검사
        validator.config= {};
        validator.config['cstmrType1'] = 'isNonEmpty';
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';

        if(!validator.validate()) {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg(), function () {
                $selectObj = $("#" + errId);
                viewAuthErrorMgs($selectObj, validator.getErrorMsg());
                $('#' + validator.id).focus(); // 포커스
            });
            return false;
        }

        // 2. 요금제 나이체크
        var age = getAge($("#cstmrNativeRrn01").val()+$("#cstmrNativeRrn02").val());
        if (!checkPriceLimitForAge(age)) {
            MCP.alert(pageObj.priceLimitObj.dtlCdDesc);
            return false;
        }

        // 3. nicepin 호출
        var nicePinChk= false;
        var varData = ajaxCommon.getSerializedData({
            name:    $("#cstmrName").val()
            ,paramR1: $("#cstmrNativeRrn01").val()
            ,paramR2: $("#cstmrNativeRrn02").val()
        });

        ajaxCommon.getItemNoLoading({
                id:'getNicePinCiAjax'
                ,cache:false
                ,async:false
                ,url:'/auth/getNicePinCiAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.returnCode) {
                    nicePinChk= true;
                } else if ('0010' == jsonObj.returnCode) { // 인가된 사용자 검증
                    MCP.alert('<p class="u-mt--12"><strong class="u-fw--bold">회원정보와 본인인증 정보가<br>일치하지 않습니다.</strong></p><p class="u-mt--12">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</p>');
                } else {
                    MCP.alert("본인 인증 요청 중 오류가 발생했습니다.<br>다시 시도 바랍니다.");
                }
            });

        if(!nicePinChk){
            return false;
        }

        // 4. 휴대폰 인증 요청시간 세팅
        ajaxCommon.getItem({
                id:'getTimeInMillisAjax'
                ,cache:false
                ,url:'/nice/getTimeInMillisAjax.do'
                ,data: ""
                ,dataType:"json"
            }
            ,function(startTime){
                pageObj.startTime = startTime ;
            });

        // 5. 휴대폰 인증창 호출 (M:핸드폰)
        var data = {width:'500', height:'700'};
        openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M', data);
        pageObj.niceType = NICE_TYPE.SMS_AUTH;
        return;
    });



    /** [step1] 간편본인인증 전 유효성 검사 */
    simpleAuthvalidation= function(){

        var operType = $("#operType").val();
        var onOffType = $("#onOffType").val();
        var cstmrType = $("input:radio[name=cstmrType]:checked").val();

        //셀프개통 신규가입 신청서 내 ‘휴대폰 인증‘ 추가
        if(operType == OPER_TYPE.NEW && onOffType == "5"){
            validator.config={};
            validator.config['isSmsAuth'] = 'isNonEmpty';
            if(!validator.validate()){ // 추가 유효성 검사 실패 시
                var errId = validator.getErrorId();
                MCP.alert(validator.getErrorMsg(),function (){
                    $selectObj = $("#"+errId);
                    viewErrorMgs($selectObj, validator.getErrorMsg());
                });
                return false;
            }
        }

        // 2. 요금제 나이체크
        var age = 0;
        if (cstmrType == CSTMR_TYPE.FN) {
            age = getAge($("#cstmrForeignerRrn01").val()+$("#cstmrForeignerRrn02").val());
        } else {
            age = getAge($("#cstmrNativeRrn01").val()+$("#cstmrNativeRrn02").val());
        }

        if (!checkPriceLimitForAge(age)) { // 유효성검사 최종 실패
            MCP.alert(pageObj.priceLimitObj.dtlCdDesc);
            return false;
        }

        return true;
    };

    /** [step1] 약관 전체동의 */
    $("#btnStplAllCheck").click(function(){
        $("._agree").prop("checked", $(this).is(':checked'));
        isValidateNonEmptyStep1();
        applyCheckedWrapList();
    });

    /** [step1] 약관 전체동의 */
    $(document).on('click', '.c-label', function(event) {
        if ($(event.target).hasClass('_allcheck')) {
            $('#btnStplAllCheck').click();
        }
    });

    /** [step1] 약관 개별동의 */
    $("._agree").click(function(){
        handleCommonAgreeClick();
    });

    /** [step1] 셀프개통 안내사항 동의 */
    $("#clauseSimpleOpen").click(function(){
        isValidateNonEmptyStep1();
    });

    /** [step1] 요금제 제휴처 약관 상세보기 */
    $("#clauseJehuButton").click(function(){
        fnSetEventId('clauseJehuFlag');

        var data = {cdGroupId1:'FORMREQUIRED',
            cdGroupId2:'clauseJehuFlag',
            docType:'02',
            expnsnStrVal:$("#jehuProdType").val(),
            name:$("#jehuProdName").val()};

        openPage('termsPop', '/termsPop.do', data, 0);
    });

    /** [step1] 제휴사 약관 상세보기 */
    $("#clausePartnerOfferButton").click(function(){
        fnSetEventId('clausePartnerOfferFlag');

        var data = {cdGroupId1:'FORMREQUIRED',
            cdGroupId2:'clausePartnerOfferFlag',
            docType:'03',
            expnsnStrVal:$("#jehuPartnerType").val(),
            name:$("#jehuPartnerName").val()};

        openPage('termsPop', '/termsPop.do', data, 0);
    });

    /** [step1] 고객입력정보 keyup 이벤트 */
    $("#cstmrName,#cstmrForeignerRrn01,#cstmrForeignerRrn02,#cstmrNativeRrn01,#cstmrNativeRrn02, #minorAgentTelFn,#minorAgentTelMn,#minorAgentTelRn").keyup(function(){
        isValidateNonEmptyStep1();
    });

    /** [step1] 고객정보(주민등록번호) 입력에 따른 요금제 나이제한 정보 체크 */
    $("#cstmrNativeRrn02").blur(function(){

        if($(this).val() == '') return;

        validator.config={};
        validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';

        if (validator.validate()) {
            var age = getAge($("#cstmrNativeRrn01").val()+$("#cstmrNativeRrn02").val());
            if (!checkPriceLimitForAge(age)) {
                MCP.alert(pageObj.priceLimitObj.dtlCdDesc);
                $(".c-form__text").remove();
                $(".has-error").removeClass("has-error");
            }
        }
    });

    $("input[name='onOffLineUsim']").change(function(event, paramObj) {

        var thisVal = $(this).val();
        if (thisVal == "onLine" && $("#isUsimNumberCheck").val() == "1") {
            MCP.alert("유심번호 유효성 체크 하였습니다. ");
            $(this).prop('checked', false);
            $("#onOffLineUsim01").prop('checked', true);
            return false ;
        }
        if ($(this).val() == "offLine") {
            //유심 보유
            $("#reqUsimSn").prop("disabled", false);
            $("._offLineUsim").show();
            $("._onLineUsim").hide();
        } else {
            //유심미보유
            $("#reqUsimSn").prop("disabled", true).val("");
            $("._offLineUsim").hide();
            $("._onLineUsim").show();
        }
        isValidateNonEmptyStep2();
    });


    /** [step2] 유심번호 유효성 체크 */
    $('#btnUsimNumberCheck').click(function(){

        if ($("#isUsimNumberCheck").val() == "1") {
            MCP.alert("유심번호 유효성 체크 하였습니다.");
            return ;
        }

        // 신청서 중복 작성 체크
        if ($("#reqUsimSn").val()) {
            if (chkReqDup()) {
                return false;
            }
        }

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};
        validator.config['reqUsimSn'] = 'isNumFix19';

        if (validator.validate()) {

            var ncType= "";
            var cstmrType = $("input:radio[name=cstmrType]:checked").val();
            if(cstmrType == CSTMR_TYPE.NM) ncType= "0";

            var varData = ajaxCommon.getSerializedData({
                iccId:$.trim($("#reqUsimSn").val()),
                reqSeq: ajaxCommon.isNullNvl(pageObj.niceResSmsObj.reqSeq, ""),
                resSeq: ajaxCommon.isNullNvl(pageObj.niceResSmsObj.resSeq, ""),
                ncType: ncType
            });

            ajaxCommon.getItem({
                    id:'moscIntmMgmtAjax'
                    ,cache:false
                    ,url:'/msp/moscIntmMgmtAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,errorCall : function () {
                        MCP.alert("유심번호 유효성 체크가 불가능합니다. <br/> 잠시 후 다시 시도하시기 바랍니다.");
                    }
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE ) {
                        var usimOrgnId = jsonObj.USIM_ORGN_ID ;

                        if (usimOrgnId == undefined || usimOrgnId == "" ) {
                            usimOrgnId = $("#cntpntShopId").val();
                        }

                        pageObj.usimOrgnId = usimOrgnId;
                        $("#isUsimNumberCheck").val("1");

                        MCP.alert("입력하신 유심번호 사용 가능합니다.", function (){
                            $("#reqUsimSn").prop("disabled", true);
                            $('#btnUsimNumberCheck').addClass('is-complete').html("유심번호 유효성 체크 완료");
                            $("#reqUsimSn").parent().addClass('has-safe').after("<p class='c-form__text'>유심번호 유효성 검증에 성공하였습니다.</p>");
                            isValidateNonEmptyStep2();
                        });

                    } else {
                        var strMsg = "입력하신 유심번호 사용이 불가능 합니다.<br>확인 후 다시 시도해 주시기 바랍니다.";
                        if (jsonObj.RESULT_MSG != null) {
                            strMsg = jsonObj.RESULT_MSG;
                        }
                        MCP.alert(strMsg, function (){
                            $selectObj = $("#reqUsimSn");
                            viewErrorMgs($selectObj, strMsg);
                        });
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

    /** [step2] 우편번호 찾기 */
    $("._btnAddr").click(function() {
        pageObj.addrFlag =  $(this).attr("addrFlag");
        openPage('pullPopup', '/addPopup.do','','1');
    });

    /** [step2] 신분증 확인수단 선택 */
    $("input:radio[name=selfCertType]").click(function(){

        var thisVal= $(this).val();
        $("._selfIssuNumF").hide();
        $("._driverSelfIssuNumF").hide();
        $("._cstmrForeignerNation").hide();

        switch (thisVal) {
            case '01':  // 주민등록증
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("#imgRadIdCard").attr("src","/resources/images/portal/content/img_jumin_card.png");
                break;
            case '03':  // 장애인등록증
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("#imgRadIdCard").attr("src","/resources/images/portal/content/img_welfare_card.png");
                break;
            case '06':  // 외국인등록증
                getNationList();
                setNationSelect();
                $("._cstmrForeignerNation").show();
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("#imgRadIdCard").attr("src","/resources/images/portal/content/img_foreigner_card.png");
                break;
            case '04':  // 국가유공자증
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("._selfIssuNumF").show();
                $("#imgRadIdCard").attr("src","/resources/images/portal/content/img_merit_card.png");
                break;
            case '02':  // 운전면허증
                $("#selfIssuExprDt").attr("title", "운전면허증의 발급 일자");
                $("label[for=selfIssuExprDt]").html("운전면허증의 발급 일자");
                $("._driverSelfIssuNumF").show();
                $("#imgRadIdCard").attr("src","/resources/images/portal/content/img_driving_license.png");
                break;
            case '05':  // 여권
                $("#selfIssuExprDt").attr("title", "만료 일자");
                $("label[for=selfIssuExprDt]").html("만료 일자");
                $("#imgRadIdCard").attr("src","/resources/images/portal/content/img_passport_card.png");
                break;
        }

        if(!fs9Succ) { //페이지 진입 or 고객이 직접 선택하는 경우 , (FS9는 성공 시 신분증타입 선택되는 경우는 제외)
            fnReqFathTgtYn();
        }

        isValidateNonEmptyStep2();
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
        validator.config['cstmrMobileFn'] = 'isNumFix3';
        validator.config['cstmrMobileMn'] = 'isNumBetterFixN3';
        validator.config['cstmrMobileRn'] = 'isNumFix4';
        if (validator.validate()) {
            
            var varData = ajaxCommon.getSerializedData({
                onlineOfflnDivCd : "ONLINE",
                orgId : $("#cntpntShopId").val(),
                cpntId : $("#cntpntShopId").val(),
                smsRcvTelNo : $("#cstmrMobileFn").val() + $("#cstmrMobileMn").val() + $("#cstmrMobileRn").val(),
                operType : $("#operType").val(),
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
                orgId : $("#cntpntShopId").val()
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


    
    /** [step2] 본인조회 동의 */
    $("#selfInqryAgrmYnFlag").click(function(){
        isValidateNonEmptyStep2();
    });

    /** [step2] 고객입력정보 keyup 이벤트 */
    $("#reqUsimSn,#cstmrMobileFn, #cstmrMobileMn, #cstmrMobileRn, #cstmrMail, #selfIssuExprDt, #driverSelfIssuNum2, #selfIssuNum").keyup(function(){
        isValidateNonEmptyStep2();
    });

    /** [step2] 면허지역 및 발급일자 변경 이벤트 */
    $("#driverSelfIssuNum1, #selfIssuExprDt").change(function(){
        isValidateNonEmptyStep2();
    });

    /** [step3] 사용중인 통신사 선택 */
    $("input:radio[name=moveCompanyGroup1]").click(function(){

        if ($("#isNpPreCheck").val() == "1") {
            return false;
        }

        var thisVal = $(this).val();
        $("#moveCompany > option").remove();
        $("#moveCompanyGroup2 option").eq(0).prop("selected",true);

        if ("-1" == thisVal) {  // 알뜰폰 선택
            $('#moveCompany').append('<option value="">알뜰폰 사업자</option>');
            $('#divMoveCompanyGroup2').show();

            if($("#onOffType").val() == "5"){
                $("._btnNext").html("사전동의 요청");
                $("#npDesc").show();
            }

        } else {
            var mpCode = $(this).attr("mpCode");
            var companyNm = $(this).attr("companyNm");
            var companyTel = $(this).attr("companyTel");

            $('#moveCompany').append('<option value="'+thisVal+'" mpCode="'+mpCode+'" companyNm="'+companyNm+'" companyTel="'+companyTel+'" >'+companyNm+'</option>').addClass("has-value");
            $('#divMoveCompanyGroup2').hide();


            // NP 예외 통신사 확인
            if($("#onOffType").val() == "5"){
                if(isNpExceptionCompany(mpCode)){
                    $("._btnNext").html("개통사전체크확인");
                    $("#npDesc").hide();
                }else{
                    $("._btnNext").html("사전동의 요청");
                    $("#npDesc").show();
                }
            }
        }

        isValidateNonEmptyStep3();
    });

    /** [step3] 알뜰폰 통신사 선택 */
    $("#moveCompanyGroup2").change(function(){

        if ($("#isNpPreCheck").val() == "1") {
            $("#moveCompanyGroup2").val(pageObj.moveCompanyGroup2);
            return false;
        }

        var thisVal = $(this).val();
        pageObj.moveCompanyGroup2 = thisVal;

        $("#moveCompany > option").remove();
        $('#moveCompany').append('<option value="">알뜰폰 사업자</option>');

        if($("#onOffType").val() == "5"){
            $("._btnNext").html("사전동의 요청");
            $("#npDesc").show();
        }

        if (pageObj.moveCompanyList.length < 1) {
            getmoveCompanyList(); // 알뜰폰 통신사 리스트 조회
        }

        if(thisVal != ""){
            pageObj.moveCompanyList.forEach(function(moveCompanyObj) {
                if (thisVal == moveCompanyObj.dtlCdDesc) {
                    $('#moveCompany').append('<option value="'+moveCompanyObj.dtlCd+'" mpCode="'+moveCompanyObj.expnsnStrVal1+'" companyNm="'+moveCompanyObj.dtlCdNm+'" companyTel="'+moveCompanyObj.expnsnStrVal2+'" >'+moveCompanyObj.dtlCdNm+'</option>');
                }
            });
        }

        isValidateNonEmptyStep3();
    });

    /** [step3] 알뜰폰 사업자 선택 */
    $("#moveCompany").change(function(){
        if ($("#isNpPreCheck").val() == "1") {
            $("#moveCompany").val(pageObj.applDataForm.moveCompany);
            return false;
        }

        // NP 예외 통신사 확인
        if($("#onOffType").val() == "5"){
            var mpCode = $("#moveCompany option:selected").attr("mpCode");
            if(isNpExceptionCompany(mpCode)){
                $("._btnNext").html("개통사전체크확인");
                $("#npDesc").hide();
            }else{
                $("._btnNext").html("사전동의 요청");
                $("#npDesc").show();
            }
        }

        isValidateNonEmptyStep3();
    });

    /** [step3] 고객입력정보 keyup 이벤트 */
    $("#moveMobileFn, #moveMobileMn, #moveMobileRn, #reqWantNumber, #reqWantNumber2, #reqWantNumber3").keyup(function(){
        isValidateNonEmptyStep3();
    });

    /** [step3] 번호조회 및 취소 */
    $("#btnSearchNumber").click(function() {
        var action = $(this).attr("action");
        if (action == "RSV") {
            searchNumber();  // 번호예약
        } else {
            cancelNumber();  // 번호예약취소
        }
    });

    /** [step3] 조회된 번호 중 선택 */
    $("#btnConfirmNum").click(function() {

        // 전화번호 선택 여부
        if ($("input:radio[name=rdoCtn]:checked").length == 0){
            MCP.alert("전화번호를 선택 하여 주시기 바랍니다.");
            return;
        }

        KTM.LoadingSpinner.show();
        var $chkRadioObj = $("input:radio[name=rdoCtn]:checked");

        // 번호예약 처리
        var varData = ajaxCommon.getSerializedData({
            tlphNo: $chkRadioObj.val()
            ,tlphNoStatCd: $chkRadioObj.attr("tlphNoStatCd")
            ,tlphNoOwnCmpnCd: $chkRadioObj.attr("tlphNoOwnCmncCmpnCd")
            ,encdTlphNo: $chkRadioObj.attr("encdTlphNo")
        });

        ajaxCommon.getItemNoLoading({
                id:'setNumberAjax'
                ,cache:false
                ,url:'/appform/setNumberAjax.do'
                ,data: varData
                ,dataType:"json"
                ,errorCall : function () {
                    KTM.LoadingSpinner.hide(true);
                }
            }
            ,function(jsonObj){

                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {

                    $('#reqWantNumberN').prop('readonly', true);
                    $("#reqCnt").val($("input:radio[name=rdoCtn]:checked").val());
                    $("#reqWantNumberText").html($("input:radio[name=rdoCtn]:checked").attr("cntValue"));

                    $('#btnSearchNumber').html("번호취소");
                    $("#btnSearchNumber").attr("action", "RRS");

                    $("._searchBefor").hide();
                    $("._searchAfter").show();

                    isValidateNonEmptyStep3();
                    KTM.Dialog.closeAll();

                    setTimeout(function() {
                        KTM.LoadingSpinner.hide(true);
                    }, 500);

                } else {
                    MCP.alert("번호 예약이 실패 하였습니다.");
                    KTM.LoadingSpinner.hide(true);
                }
            });
    });

    //아무나 SOLO 결합 가입 버튼
    $('input[name="combineSoloType"]').on('change', function() {
      if ($(this).val() === 'Y') {
        $('#divReg').show();
      } else {
        $('#divReg').hide();
      }
    });

    /** [STEP4] 부가서비스 정보 : 친구초대 추천인 ID */
    $("#recommendFlagCd").change(function(){
        var thisVal = $(this).val();

        if (thisVal == "") {
            $("#recommendInfo").prop("disabled","disabled");
            $("#recommendInfo").val("");
        } else {
            $("#recommendInfo").prop("disabled","");
        }

        isValidateNonEmptyStep4();
    });

    /** [STEP4] 부가서비스 정보 : 부가서비스 추가/삭제 버튼 */
    $("#btnAddition").click(function(){
        if (pageObj.initAddition < 1){
            getAdditionList("N"); // 무료부가서비스 조회
            getAdditionList("Y"); // 유료부가서비스 조회
        }
    });

    /** [STEP4] 부가서비스 정보 : 부가서비스 체크 이벤트 */
    $(document).on("click","input:checkbox[name=additionName]",function() {

        var $thisObj = $(this);
        if(!$thisObj.is(':checked')) return;

        var isCheck = false;
        var thisVal = $thisObj.val();

        // 부가서비스 배타 관계 체크
        for(var i = 0 ; i < pageObj.additionBetaList.length ; i++) {

            if (thisVal == pageObj.additionBetaList[i].expnsnStrVal1) {
                for(var j = 0 ; j < $("input:checkbox[name=additionName]:checked").length ; j++) {
                    if (pageObj.additionBetaList[i].expnsnStrVal2 == $("input:checkbox[name=additionName]:checked").eq(j).val()) {
                        MCP.alert(pageObj.additionBetaList[i].expnsnStrVal3 );
                        isCheck = true;
                        break;
                    }
                }

                //캐치콜 플러스 배타 체크
                if ($("#cbAddServiceCatchCall").is(":checked")) {
                    if (pageObj.additionBetaList[i].expnsnStrVal2 == pageObj.additionCatchCallKey ) {
                        MCP.alert(pageObj.additionBetaList[i].expnsnStrVal3 );
                        $thisObj.prop("checked", "");
                        isCheck = true ;
                        break;
                    }
                }

                if (isCheck) {
                    $thisObj.prop("checked", "");
                    break;
                }
            }

            if (thisVal == pageObj.additionBetaList[i].expnsnStrVal2) {
                for(var j = 0 ; j < $("input:checkbox[name=additionName]:checked").length ; j++) {
                    if (pageObj.additionBetaList[i].expnsnStrVal1 == $("input:checkbox[name=additionName]:checked").eq(j).val() ) {
                        MCP.alert(pageObj.additionBetaList[i].expnsnStrVal3);
                        isCheck = true;
                        break;
                    }
                }

                //캐치콜 플러스 배타 체크
                if ($("#cbAddServiceCatchCall").is(":checked")) {
                    if (pageObj.additionBetaList[i].expnsnStrVal1 == pageObj.additionCatchCallKey ) {
                        MCP.alert(pageObj.additionBetaList[i].expnsnStrVal3 );
                        $thisObj.prop("checked", "");
                        isCheck = true ;
                        break;
                    }
                }

                if (isCheck) {
                    $thisObj.prop("checked", "");
                    break;
                }
            }
        }
    });

    $("#cbAddServiceCatchCall").click(function(){
        var $thisObj = $(this);
        var isCheck = false ;

        if ($thisObj.is (':checked')) {
            var thisVal = pageObj.additionCatchCallKey ;
            for(var i = 0 ; i < pageObj.additionBetaList.length ; i++) {
                if (thisVal == pageObj.additionBetaList[i].expnsnStrVal1) {
                    for(var j = 0 ; j < $("input:checkbox[name=additionName]:checked").length ; j++) {
                        if (pageObj.additionBetaList[i].expnsnStrVal2 == $("input:checkbox[name=additionName]:checked").eq(j).val() ) {
                            MCP.alert(pageObj.additionBetaList[i].expnsnStrVal3 );
                            $thisObj.prop("checked", "");
                            isCheck = true ;
                            break;
                        }
                    }

                    if (isCheck) {
                        $thisObj.prop("checked", "");
                        break;
                    }
                }

                if (thisVal == pageObj.additionBetaList[i].expnsnStrVal2) {
                    for(var j = 0 ; j < $("input:checkbox[name=additionName]:checked").length ; j++) {
                        if (pageObj.additionBetaList[i].expnsnStrVal1 == $("input:checkbox[name=additionName]:checked").eq(j).val() ) {
                            MCP.alert(pageObj.additionBetaList[i].expnsnStrVal3 );
                            $thisObj.prop("checked", "");
                            isCheck = true ;
                            break;
                        }
                    }

                    if (isCheck) {
                        $thisObj.prop("checked", "");
                        break;
                    }
                }
            }
        }

        $('#btnSetAddition').trigger("click");
    });

    //부가 서비스 캐치콜 아코디언 버튼 클릭
    $('#accAddService').on('click', function () {
        if (pageObj.additionCatchCall != null ) {
            var varData = ajaxCommon.getSerializedData({
                rateAdsvcGdncSeq:pageObj.additionCatchCall.rateAdsvcProdRelSeq
                , btnYn : "N"
            });

            ajaxCommon.getItem({
                id:'adsvcContent'
                ,cache:false
                ,url:'/rate/adsvcContent.do'
                ,data: varData
                ,dataType:"html"
                , errorCall : function () {
                    MCP.alert("상세정보가 없습니다.");
                }
            },function(strHtml){
                $("#accAddServiceContent .c-accordion__inside").html(strHtml);
                $("#accAddService").parent().toggleClass('is-active');
            });
        } else {
            MCP.alert("상세정보가 없습니다.");
        }
    });

 // 통화중대기 설정/해제 아코디언 버튼 클릭
    $('#callWaitingService').on('click', function () {

            var varData = ajaxCommon.getSerializedData({
                rateAdsvcGdncSeq : '179'
               , btnYn : 'N'
            });

            ajaxCommon.getItem({
                id:'adsvcContent'
                ,cache:false
                ,url:'/rate/adsvcContent.do'
                ,data: varData
                ,dataType:"html"
                , errorCall : function () {
                    MCP.alert("상세정보가 없습니다.");
                }
            },function(strHtml){
                $("#callWaitingServiceContent .c-accordion__inside").html(strHtml);
                $("#callWaitingService").parent().toggleClass('is-active');
            });
    });

    /** [STEP4] 부가서비스 정보 : 부가서비스 선택 확인 버튼 */
    $('#btnSetAddition').click(function(){
        setAdditionListView(); // 선택한 부가서비스 화면 세팅
    });

    /** [STEP4] 부가서비스 정보 : 부가서비스 설명 아코디언 */
    $(document).on("click","._btnAccordion",function() {

        var rateAdsvcProdRelSeq = $(this).attr("rateAdsvcProdRelSeq");
        var $thatObj = $(this);

        if (rateAdsvcProdRelSeq == null || rateAdsvcProdRelSeq == "" || rateAdsvcProdRelSeq == "null") {
            $thatObj.parent().parent().find('.c-accordion__inside').html("상세정보가 없습니다.");
            return;
        }

        // 두번 호출 방지
        if ($thatObj.parent().parent().find('.c-accordion__inside').html() != "" ) {
            return;
        }

        var varData = ajaxCommon.getSerializedData({
            subTabId : 'sub1'
            ,rateAdsvcGdncSeq : rateAdsvcProdRelSeq
        });

        ajaxCommon.getItem({
                id:'adsvcContent'
                ,cache:false
                ,url:"/rate/adsvcContent.do"
                ,data: varData
                ,dataType:"html"
                , errorCall : function () {
                    $thatObj.parent().parent().find('.c-accordion__inside').html("상세정보가 없습니다.");
                }
            }
            ,function(strHtml){
                $thatObj.parent().parent().find('.c-accordion__inside').html(strHtml);
            });
    });

    /** [STEP4] 부가서비스 정보 : 안심보험 신청/변경 버튼 */
    $('#btnInsrProd').click(function(){

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};
        validator.config['isAuth'] = 'isNonEmpty';

        if (validator.validate()) {
            if (pageObj.insrProdList.length < 1) {
                getInsrProdInfo();  // 안심보험 리스트 조회
            }

            var el = document.querySelector('#insrProdDialog');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();

        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg(), function (){
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
        }
    });

    /** [STEP4] 부가서비스 정보 : 안심보험 라디오버튼 */
    $(document).on("click","input:radio[name=insrProdCD]",function() {
        var itemObj = $(this).data();
        var insrProdCd = itemObj.insrProdCd;

        if (itemObj == null || insrProdCd == null) {
            pageObj.insrProdObj = null;
        } else {
            pageObj.insrProdObj = itemObj;
        }
    });

    /** [STEP4] 부가서비스 정보 : 안심보험 선택 확인 버튼 */
    $('#btnInsrProdConfirm').click(function(){

        if (pageObj.insrProdObj != null) {

            $("#divInsrProdInfo").hide();
            $("._insrProdList").show();
            $("#insrProdView").html(getRowTemplateInsrProdView(pageObj.insrProdObj));

            // 약관 초기화
            if (pageObj.insrProdCd != pageObj.insrProdObj.insrProdCd) {
                // 약관 해제처리
                $("#btnInsrAllCheck").prop("checked",false);
                $("#clauseInsrProdFlag").prop("checked",false);
                $("#clauseInsrProdFlag02").prop("checked",false);
                $("#clauseInsrProdFlag03").prop("checked",false);
            }
            pageObj.insrProdCd = pageObj.insrProdObj.insrProdCd;

        } else {
            $("#divInsrProdInfo").show();
            $("._insrProdList").hide();
            // 약관 해제처리
            $("#btnInsrAllCheck").prop("checked", false);
            $("#clauseInsrProdFlag").prop("checked", false);
            $("#clauseInsrProdFlag02").prop("checked", false);
            $("#clauseInsrProdFlag03").prop("checked", false);
            pageObj.insrProdCd = "";
        }

        isValidateNonEmptyStep4();
    });

    /** [STEP4] 부가서비스 정보 : 안심보험 휴대폰 인증 */
    $("#btnAuthInsr").click(function(){

        if ($("#isAuthInsr").val() == "1") {
            MCP.alert("본인 인증을 완료 하였습니다.");
            return false;
        }

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};
        validator.config['isAuth'] = 'isNonEmpty';
        validator.config['clauseInsrProdFlag'] = 'isNonEmpty';
        validator.config['clauseInsrProdFlag02'] = 'isNonEmpty';
        validator.config['clauseInsrProdFlag03'] = 'isNonEmpty';

        if (validator.validate()){

            ajaxCommon.getItem({
                    id:'getTimeInMillisAjax'
                    ,cache:false
                    ,url:'/nice/getTimeInMillisAjax.do'
                    ,data: ""
                    ,dataType:"json"
                }
                ,function(startTime){
                    pageObj.startTime = startTime;
                });

            var data = {width:'500', height:'700'};
            openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M', data);
            pageObj.niceType = NICE_TYPE.INSR_PROD;

            return;

        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg() ,function (){
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
        }
    });

    /** [step4] 안심보험 약관 전체동의 */
    $("#btnInsrAllCheck").click(function(){
        $("._agreeInsr").prop("checked", $(this).is(':checked'));
        isValidateNonEmptyStep4();
    });

    /** [step4] 안심보험 약관 개별동의 */
    $("._agreeInsr").click(function(){
        checkAllTermsStep4();  // 개별 약관 동의 시 전체약관 선택 버튼 처리
    });

    /** [step4] 사은품 선택 */
    $(document).on("click","input:checkbox[name=checkboxGift]",function() {

        var $thisObj = $(this);
        var prmtId = $thisObj.attr("prmtId");
        var prmtType = $thisObj.attr("prmtType");
        var limitCnt = parseInt($thisObj.attr("limitCnt"), 10);
        var selectCnt = $("input:checkbox[name=checkboxGift][prmtId="+prmtId+"]:checked").length;

        if ("D" != prmtType){ // 선택사은품
            if (limitCnt < selectCnt) {
                MCP.alert("사은품 선택 개수를 초과하였습니다.",function (){
                    $thisObj.prop("checked",false);
                });
            }
        } else {
            $thisObj.prop("checked",true);
        }
    });

    /** [step4] 고객입력정보 keyup 이벤트 */
    $("#recommendInfo").keyup(function(){
        isValidateNonEmptyStep4();
    });

    /** [step5] 납부정보·가입정보 확인 : 요금 납부 방법 */
    $("input:radio[name=reqPayType]").click(function(){

        var thisval = $(this).val();

        if (thisval == "D") { // 자동이체
            $("._card").hide();
            $("._bank").show();
        } else if (thisval == "C") { // 신용카드
            $("._card").show();
            $("._bank").hide();
        }

        isValidateNonEmptyStep5();
    });

    /** [step5] 납부정보·가입정보 확인 : 계좌번호 유효성검사 */
    $("#btnCheckAccount").click(function(){

        if ($('#isCheckAccount').val() == "1") {
            MCP.alert("계좌 번호 유효성 검증을 완료하였습니다.");
            return null;
        }

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};
        validator.config['isAuth'] = 'isNonEmpty';
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['reqBank'] = 'isNonEmpty';
        validator.config['reqAccountNumber'] = 'accountChk';

        var ncType= "";
        var cstmrName ;
        var cstmrType = $("input:radio[name=cstmrType]:checked").val();
        if (cstmrType == CSTMR_TYPE.NM) { //청소년
            validator.config['minorAgentName'] = 'isNonEmpty';
            cstmrName = $.trim($("#minorAgentName").val());
            ncType= "1";
        } else {
            validator.config['cstmrName'] = 'isNonEmpty';
            cstmrName = $.trim($("#cstmrName").val());
        }

        validator.config['reqBank'] = 'isNonEmpty';
        validator.config['reqAccountNumber'] = 'accountChk';

        if (validator.validate()) {
            var varData = ajaxCommon.getSerializedData({
                service:"2"
                ,svcGbn:"2"
                ,bankCode:$("#reqBank").val()
                ,accountNo:$.trim($("#reqAccountNumber").val())
                ,name:$.trim(cstmrName)
                ,ncType: ncType
            });

            ajaxCommon.getItem({
                    id:'accountCheck'
                    ,cache:false
                    ,url:'/nice/accountCheckAjax.do'
                    ,data: varData
                    ,dataType:"json"
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {

                        MCP.alert("계좌 번호 유효성 검증에 성공하였습니다.");
                        $('#reqBank').prop('disabled', true);
                        $('#reqAccountNumber').prop('readonly', 'readonly');
                        $('#reqPayType02').prop('disabled', 'true');
                        $('#isCheckAccount').val("1");

                        $("#btnCheckAccount").addClass('is-complete').html("계좌번호 체크 완료");
                        isValidateNonEmptyStep5();

                    } else if(jsonObj.RESULT_CODE == "STEP01"){ // STEP 오류
                        MCP.alert(jsonObj.RESULT_MSG);
                    } else {
                        MCP.alert("계좌 번호 유효성 검증에 실패하였습니다.");
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

    /** [step5] 납부정보·가입정보 확인 : 신용카드 유효성검사 */
    $("#btnCheckCardNo").click(function(){
        if ($('#isCheckCard').val() == "1") {
            MCP.alert("신용카드번호 유효성 검증에 성공하였습니다.");
            return null;
        }
        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};
        validator.config['reqCardNo'] = 'isNumBetterFixN14'; //isCheckCardNumberAll
        validator.config['reqCardMm'] = 'isNonEmpty';
        validator.config['reqCardYy'] = 'isNonEmpty';

        var ncType= "";

        if (validator.validate()) {
            //신용카드 인증조회(X91)
            var cstmrType = $("input:radio[name=cstmrType]:checked").val();
            pageObj.applDataForm.reqCardNo = $.trim($("#reqCardNo").val()) ;
            pageObj.applDataForm.reqCardYy = $.trim($("#reqCardYy").val());
            pageObj.applDataForm.reqCardMm = $.trim($("#reqCardMm").val());

            if (cstmrType == CSTMR_TYPE.NA) {
                pageObj.applDataForm.reqCardName = $.trim($("#cstmrName").val());
                pageObj.applDataForm.reqCardRrn = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;
            } else if (cstmrType == CSTMR_TYPE.NM) {
                ncType= "1";
                pageObj.applDataForm.reqCardName = $.trim($("#minorAgentName").val());
                pageObj.applDataForm.reqCardRrn = $.trim($("#minorAgentRrn01").val()) + $.trim($("#minorAgentRrn02").val()) ;
            } else if (cstmrType == CSTMR_TYPE.FN) {
                pageObj.applDataForm.reqCardName = $.trim($("#cstmrName").val());
                pageObj.applDataForm.reqCardRrn = $.trim($("#cstmrForeignerRrn01").val()) + $.trim($("#cstmrForeignerRrn02").val()) ;
            }

            var varData = ajaxCommon.getSerializedData({
                reqCardNo: pageObj.applDataForm.reqCardNo
                , reqCardYy: pageObj.applDataForm.reqCardYy
                , reqCardMm: pageObj.applDataForm.reqCardMm
                , reqCardRrn: pageObj.applDataForm.reqCardRrn
                , reqCardName: pageObj.applDataForm.reqCardName
                , ncType: ncType
            });

            ajaxCommon.getItemNoLoading({
                    id:'crdtCardAthnInfo'
                    ,cache:false
                    ,async:false
                    ,url:'/crdtCardAthnInfoAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,errorCall : function () {
                        //nothing
                    }
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        pageObj.applDataForm.reqCardCompany = jsonObj.CRDT_CARD_CODE_NM;

                        MCP.alert("신용카드번호 유효성 검증에 성공하였습니다.");
                        $('#reqCardYy').prop('disabled', 'true');
                        $('#reqCardMm').prop('disabled', 'true');
                        $('#reqCardNo').prop('readonly', 'readonly');
                        $('#reqPayType01').prop('disabled', 'true');
                        $('#isCheckCard').val("1");
                        $("#btnCheckCardNo").addClass('is-complete').html("신용카드 체크 완료");

                        isValidateNonEmptyStep5();
                    } else {
                        var msg = "신용카드번호 유효성 검증에 실패 하였습니다.";
                        if (jsonObj.ALTER_MSG != null) {
                            msg = jsonObj.ALTER_MSG;
                        }
                        MCP.alert(msg);
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

    /** [step5] 납부정보·가입정보 확인 : 고객입력정보 keyup 이벤트 */
    $("#reqAccountNumber, #reqCardNo").keyup(function(){
        isValidateNonEmptyStep5();
    });

    /** [step5] 납부정보·가입정보 확인 : 은행명 및 카드 유효시간 변경 이벤트 */
    $("#reqBank, #reqCardMm, #reqCardYy").change(function(){
        isValidateNonEmptyStep5();
    });

    //해피콜
    $('#btnOnline').click(function(){
       
        var siteReferer = $("#siteReferer").val();
        if (siteReferer == "") {
            $("#onOffType").val("6");
        } else {
            $("#onOffType").val("0");
        }

        $("._self").hide();
        $("._noSelf").show();

        if (pageObj.step >= 5) {
            $("._btnNext").html("주문완료");  /////온라인 신청
            pageObj.step = 5;
        } else {
            $("._btnNext").html("다음");
        }

        KTM.Dialog.closeAll();
        
        
        setTimeout(function(){
            window.scrollTo(0, 0);
        }, 500);

        if (pageObj.step == 3) {
            isValidateNonEmptyStep3();
        } else if(pageObj.step == 2 ) {
            isValidateNonEmptyStep2();
        }
        $("input[name='onOffLineUsim'][value='offLine']").trigger("change",{'isInit':true});
        
    });

    /** [공통] OSST 오류 코드에 따라서 초기화 처리 */
    $('._btnCheck').click(function(){

        // 번호이동 관련 오류처리인듯함
        if (RETRY_RESULT_CODE_LIST.includes(pageObj.osstResultCode)) {

            let init = false;

            ajaxCommon.getItemNoLoading({
                id:'reqInit'
                ,cache:false
                ,url:'/appform/reqInitAjax.do'
                ,data: ''
                ,dataType:"json"
                ,async:false
            }, function(jsonObj) {
                init = true;
            });

            $("#divArs").hide();
            $("#divArs .ars-company__name").html("-");
            $("#divArs .ars-company__number").html("-");
            $("#isNpPreCheck").val("");
            $("#moveMobileFn").prop("readonly",false);
            $("#moveMobileMn").prop("readonly",false);
            $("#moveMobileRn").prop("readonly",false);

            var mpCode = $("#moveCompany option:selected").attr("mpCode");
            // NP 예외 통신사 확인
            if(isNpExceptionCompany(mpCode)){
                $("._btnNext").html("개통사전체크확인");
                $("#npDesc").hide();
            }else{
                $("._btnNext").html("사전동의 요청");
                $("#npDesc").show();
            }

            pageObj.applDataForm.globalNoNp1 = "";
            pageObj.applDataForm.globalNoNp3 = "";
        }

        if (pageObj.step == 3) {
            isValidateNonEmptyStep3();
        }
    });

    $('.c-accordion.c-accordion--nested > .c-accordion__box > .c-accordion__item > .c-accordion__head .c-accordion__button, .c-accordion.c-accordion--nested__sub > .c-accordion__box > .c-accordion__item > .c-accordion__head .c-accordion__button').click(function() {
        handleAccordionClick(this);
    });

    updateFathSkipBtnVisibility();
}); // end of document.ready -------------------------------------

/** 요금제 나이제한 정보 조회 */
var getPriceLimitInfo = function() {
    var varData = ajaxCommon.getSerializedData({
        dtlCd:$("#socCode").val()
        ,cdGroupId:CD_GROUP_ID_OBJ.RateLimit
    });

    ajaxCommon.getItemNoLoading({
            id:'getPriceLimitInfo'
            ,cache:false
            ,url:'/getCodeNmAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(jsonObj){
            pageObj.priceLimitObj = jsonObj;
        });
};

/** 요금제 나이제한 체크 */
var checkPriceLimitForAge = function(ageInt) {

    //0, 100, N --> 0세~100세 신청불가
    //0, 100, Y --> 0세~100세 신청가능

    if (pageObj.priceLimitObj != null && pageObj.priceLimitObj.dtlCd != null && pageObj.priceLimitObj.dtlCd != "") {
        var priceLimitObj = pageObj.priceLimitObj;
        var startAge= parseInt(priceLimitObj.expnsnStrVal1,10);
        var endAge= parseInt(priceLimitObj.expnsnStrVal2,10);
        var division =priceLimitObj.expnsnStrVal3;

        if ("N" == division ) {
            if (startAge <= ageInt && endAge  >=  ageInt) {
                return false;  //불가능
            } else {
                return true;   //가능
            }
        } else {
            if (startAge <= ageInt && endAge  >=  ageInt) {
                return true;   //가능
            } else {
                return false;  //불가능
            }
        }
    } else {
        return true;  // 가능
    }
}

/** 포커스 이동 처리 */
var nextFocus = function(obj, len, id) {
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

/** 본인인증 콜백 */
var fnNiceCert = function(prarObj) {
    var cstmrNativeRrn ;
    var cstmrName ;
    var strMsg ;
    var operType = $("#operType").val();

    pageObj.niceResLogObj = prarObj;

    var ncType= "";
    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    if(cstmrType == CSTMR_TYPE.NM) ncType= "1";

    //본인인증
    if (pageObj.niceType == NICE_TYPE.CUST_AUTH) {

        if (cstmrType == CSTMR_TYPE.NA) {
            //내국인
            cstmrNativeRrn = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;
            cstmrName = $.trim($("#cstmrName").val());
            strMsg = '<p class="u-mt--12"><strong class="u-fw--bold">본인인증 정보가 일치하지 않습니다.</strong></p><p class="u-mt--12">입력하신 정보를 확인 후 다시 시도해 주시기 바랍니다.</p>';
        } else if (cstmrType == CSTMR_TYPE.NM) {
            //청소년
            cstmrNativeRrn = $.trim($("#minorAgentRrn01").val()) + $.trim($("#minorAgentRrn02").val()) ;
            cstmrName = $.trim($("#minorAgentName").val());
            strMsg = '<p class="u-mt--12"><strong class="u-fw--bold">법정대리인 본인인증 정보가<br/>일치하지 않습니다.</strong></p><p class="u-mt--12">입력하신 정보를 확인 후 다시 시도해 주시기 바랍니다.</p>';
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

        //기존 - 신규/번호이동 구분없이 eSIM/USIM 각 90일 이내 1회선으로 개통 제한
        //변경 - 2026.03 - 제휴는 신규(셀프)만 30일 이내 1회선으로 개통 제한
        var onOffType = $("#onOffType").val() ;
        if(onOffType == "5" || onOffType == "7") {
            if (!checkLimit(prarObj.reqSeq, prarObj.resSeq)) return null;
        }

        var authInfoJson= {cstmrName: cstmrName , cstmrNativeRrn: cstmrNativeRrn, authType:prarObj.authType,
            operType: operType , onOffType: onOffType, ncType: ncType};

        var isAuthDone= authCallback(authInfoJson);
        if(isAuthDone){ // 본인인증 최종 성공
            pageObj.authObj= prarObj ;
            $('#cstmrName').prop('readonly', 'readonly');
            $('#cstmrNativeRrn01').prop('readonly', 'readonly');
            $('#cstmrNativeRrn02').prop('readonly', 'readonly');
            $('#cstmrForeignerRrn01').prop('readonly', 'readonly');
            $('#cstmrForeignerRrn02').prop('readonly', 'readonly');

            // 법정대리인 정보(이름, 생년월일) 변경 불가처리
            if(cstmrType == CSTMR_TYPE.NM){
                $('#minorAgentName').prop('readonly', 'readonly');
                $('#minorAgentRrn01').prop('readonly', 'readonly');
                $('#minorAgentRrn02').prop('readonly', 'readonly');
            }

            isValidateNonEmptyStep1();
            pageObj.telAdvice = false;
            MCP.alert("본인인증에 성공 하였습니다.");
            $("input:checkbox[name=authCheckNone]").prop("checked",false);
            return null;
        }else{ // 본인인증 최종 실패

            if(niceAuthObj.resAuthOjb.RESULT_CODE == "STEP01"){
                strMsg= niceAuthObj.resAuthOjb.RESULT_MSG;
            }

            MCP.alert(strMsg);
            return null;
        }
    } else if (pageObj.niceType == NICE_TYPE.SMS_AUTH) {

        if(prarObj.resSeq == undefined || prarObj.resSeq == "") {
            MCP.alert("휴대폰 인증에 실패 하였습니다.");
            return null;
        }

        if(!checkLimit(prarObj.reqSeq, prarObj.resSeq)) return null;

        if(!checkSmsAuthInfo()){
            MCP.alert("고객정보와 휴대폰 인증 정보가 일치하지 않습니다.");
            return null;
        }

        $('#cstmrName').prop('readonly', 'readonly');
        $('#cstmrNativeRrn01').prop('readonly', 'readonly');
        $('#cstmrNativeRrn02').prop('readonly', 'readonly');

        $("#isSmsAuth").val("1");
        $('#btnSmsAuth').addClass('is-complete').html("휴대폰 인증 완료");
        pageObj.niceResSmsObj =  prarObj;
        return null;

    } else if (pageObj.niceType == NICE_TYPE.INSR_PROD) {

        var authInfoJson= {cstmrName: "insrProdNm" , cstmrNativeRrn: "insrProdRrn", authType:prarObj.authType , isCommonPrd:false, ncType: ncType};

        var isAuthDone= authCallback(authInfoJson);
        if(isAuthDone){ // 본인인증 최종 성공
            $("#isAuthInsr").val("1");
            pageObj.niceHistInsrProdSeq = niceAuthObj.resAuthOjb.niceHistSeq ;
            $('#btnAuthInsr').addClass('is-complete').html("휴대폰 인증 완료");
            isValidateNonEmptyStep4();
            return null;
        } else { // 본인인증 최종 실패

            var insrErrMsg= "본인인증 시 고객정보와 휴대폰 인증 고객정보가 일치하지 않습니다.";
            if(niceAuthObj.resAuthOjb.RESULT_CODE == "STEP01"){ // STEP 오류
                insrErrMsg= niceAuthObj.resAuthOjb.RESULT_MSG;
            }

            MCP.alert(insrErrMsg);
            return null;
        }
    }
}

/** 셀프개통 다회선 제한 체크 */
//기존 - 신규/번호이동 구분없이 eSIM/USIM 각 90일 이내 1회선으로 개통 제한
//변경 - 2026.03 - 제휴는 신규(셀프)만 30일 이내 1회선으로 개통 제한
var checkLimit = function(reqSeq, resSeq) {

    var rtnObj = false;

    var varData = ajaxCommon.getSerializedData({
        socCode:$("#socCode").val()
        ,usimKindsCd:$('#usimKindsCd').val()
        ,reqSeq: reqSeq
        ,resSeq: resSeq
        ,requestKeyTemp:$('#requestKeyTemp').val()
    });

    ajaxCommon.getItemNoLoading({
            id:'checkLimitFormAjax'
            ,cache:false
            ,async:false
            ,url:'/appform/checkLimitFormAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                rtnObj = true ; //통과
            } else if ("-1" ==  jsonObj.RESULT_CODE) {
                $("#simpleDialog ._title").html("개통 진행 실패");
                $("#simpleDialog ._detail").html("※ 신규가입은 명의당 30일이내 1회선만 가입 가능합니다.<br>추가 가입은 최근 가입하신 KT M모바일 회선 가입일을 기준으로 30일 경과된 시점에 신청 부탁드립니다.");
                $("._simpleDialogButton").hide();
                $("#btnOnline").hide();
                $("#divChangeButton").show();

                var el = document.querySelector('#simpleDialog');
                var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                modal.open();

            } else if(jsonObj.RESULT_CODE == "AUTH01" || jsonObj.RESULT_CODE == "AUTH02"){ // AUTH 오류
                MCP.alert("본인 인증한 정보가 없습니다.<br>본인 인증 해주시기 바랍니다.");
            } else if(jsonObj.RESULT_CODE == "STEP01" || jsonObj.RESULT_CODE == "STEP02"){ // STEP 오류
                MCP.alert(jsonObj.RESULT_MSG);
            } else {
                MCP.alert("문제가 발생 하였습니다.<br>잠시후 다시 시도 하시기 바랍니다.");
            }
        });

    return rtnObj;
}

/** 선택한 약관ID 임시저장 */
var fnSetEventId = function(eventId){
    pageObj.eventId = eventId;
};

/** 약관 동의후닫기 */
var targetTermsAgree = function(){
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

    // 안심보험 약관인 경우 STEP4의 전체동의 버튼 처리
    // 이외의 경우 STEP1의 전체동의 버튼 처리
    var insrCheckText = ["clauseInsrProdFlag", "clauseInsrProdFlag02", "clauseInsrProdFlag03"];
    if(insrCheckText.indexOf(pageObj.eventId) != -1) checkAllTermsStep4();
    else checkAllTermsStep1();
}

/** [step1] 개별 약관 동의 시 전체약관 선택 버튼 처리 */
var checkAllTermsStep1 = function(){

    var isAllCheck= true;
    $("._agree").each(function(){
        if ( $(this).parent().css("display") != "none" && !$(this).is(':checked')){
            isAllCheck= false;
        }
    });

    $("#btnStplAllCheck").prop("checked", isAllCheck);
    isValidateNonEmptyStep1();
}

/** 제휴처 및 제휴사 약관 표출여부 */
var getJehuInfo = function(){

    $("._clauseJehuFlag").hide();
    $("._clausePartnerOfferFlag").hide();

    var jehuProdType = ajaxCommon.isNullNvl($("#jehuProdType").val(), "");
    var jehuPartnerType = ajaxCommon.isNullNvl($("#jehuPartnerType").val(), "");

    if(jehuProdType != "") $("._clauseJehuFlag").show();
    if(jehuPartnerType != "") $("._clausePartnerOfferFlag").show();
}

/** 주소 정보 화면 세팅 */
var jusoCallBack = function(roadFullAddr, roadAddrPart1, addrDetail, roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn, dataOjb){

    var $cstmrPost = $("#cstmrPost");
    $cstmrPost.val(zipNo);
    $("#cstmrAddr").val(roadAddrPart1);
    $("#cstmrAddrDtl").val(roadAddrPart2 + " " + addrDetail);

    var admCd = dataOjb.find('admCd').text();
    var rnMgtSn = ajaxCommon.isNullNvl(dataOjb.find('rnMgtSn').text(),dataOjb.find('rdMgtSn').text());
    var udrtYn = dataOjb.find('udrtYn').text();
    var buldMnnm = dataOjb.find('buldMnnm').text();
    var buldSlno = dataOjb.find('buldSlno').text();
    var jibunAddr = dataOjb.find('jibunAddr').text();
    var roadAddrPart2 = dataOjb.find('roadAddrPart2').text();

    $cstmrPost.removeData();
    $cstmrPost.attr('data-adm-cd', admCd);
    $cstmrPost.attr('data-rn-mgt-sn', rnMgtSn);
    $cstmrPost.attr('data-udrt-yn', udrtYn);
    $cstmrPost.attr('data-buld-mnnm', buldMnnm);
    $cstmrPost.attr('data-buld-slno', buldSlno);
    $cstmrPost.attr('data-jibun-addr', jibunAddr);
    $cstmrPost.attr('data-addr-detail', addrDetail);
    $cstmrPost.attr('data-road-addr-part2', roadAddrPart2);
    isValidateNonEmptyStep2();
}

/** 알뜰폰 통신사 조회 */
var getmoveCompanyList = function() {
    var varData = ajaxCommon.getSerializedData({
        grpCd : "NSC"
    });

    ajaxCommon.getItemNoLoading({
            id:'getmoveCompanyList'
            ,cache:false
            ,url:'/getCodeListAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(objList){
            if (objList != null) {
                pageObj.moveCompanyList = objList;
            }
        });
};

/** 사전동의 예외 통신사 조회 */
var getNpExceptionList = function() {

    if(pageObj.npExceptionList != null) return;

    ajaxCommon.getItemNoLoading({
            id:'getNpNscExceptionList'
            ,cache:false
            ,url:'/getCodeListAjax.do'
            ,data: ajaxCommon.getSerializedData({grpCd:"NpNscException"})
            ,dataType:"json"
            ,async:false
        }
        ,function(objList){
            pageObj.npExceptionList = (objList == null) ? [] : objList;
        });
};

/** 사전동의 예외 통신사 여부*/
var isNpExceptionCompany = function(mpCode){
    var mCompany = ajaxCommon.isNullNvl(mpCode, "");
    if(mCompany == "" || pageObj.npExceptionList == null){
        return false;
    }

    var fIdx = pageObj.npExceptionList.findIndex(item => item.dtlCd == mpCode);
    return (fIdx == -1) ? false : true;
}

var getCombineSoloType = function (rateCd) {
    var varData = ajaxCommon.getSerializedData({
        rateCd:rateCd
    });

    ajaxCommon.getItemNoLoading({
            id:'getAddition'
            ,cache:false
            ,url:'/content/getCombineSoloTypeAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(jsonObj){
            if(AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE){
                $("#combineSoloSection").show();
                $("#combineSoloDataText").text(jsonObj.R_RATE_NM);
            }
        });
}

/** 임시저장된 부가서비스 리스트 조회 */
var getAdditionTempList = function() {

    var varData = ajaxCommon.getSerializedData({
        requestKey: $("#requestKeyTemp").val()
    });

    ajaxCommon.getItemNoLoading({
            id:'getAdditionTempListAjax'
            ,cache:false
            ,url:'/appForm/getAdditionTempListAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(objList){

            if (objList != null && objList.length > 0) {
                objList.forEach(function(item){
                    pageObj.additionTempKeyList.push(item);
                });
            }
        });
};

/** 부가서비스 조회 */
var getAdditionList = function (chargeFlag) {

    var varData = ajaxCommon.getSerializedData({
        chargeFlag : chargeFlag
    });

    ajaxCommon.getItemNoLoading({
            id:'getAddition'
            ,cache:false
            ,url:'/appform/getMcpAdditionListAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(jsonObj){
            setAdditionList(jsonObj, chargeFlag);
        });
}

/** 부가서비스 리스트 팝업 세팅 */
var setAdditionList = function (objList, chargeFlag) {

    if (chargeFlag == "F") {       // 기본제공
        $("#ulAdditionListBase").html('');
        $("#ulAdditionListBase").append(getRowTemplate(objList ,chargeFlag));
    } else if(chargeFlag == "N") { // 무료
        $("#ulAdditionListFree").html('');
        $("#ulAdditionListFree").append(getRowTemplate(objList ,chargeFlag));
        pageObj.initAddition = 1;
    } else if(chargeFlag == "Y") { // 유료
        $("#ulAdditionListPrice").html('');
        $("#ulAdditionListPrice").append(getRowTemplate(objList ,chargeFlag));
        pageObj.additionList = objList;
        pageObj.initAddition = 2;
    }
}

/** 부가서비스 리스트 팝업 세팅 - 행 template */
var getRowTemplate = function(objList ,chargeFlag){

    var arr =[];
    for(var i = 0 ; i < objList.length ; i++) {
        if (pageObj.additionCatchCallKey == objList[i].additionKey ) {
            //캐치콜 플러스 제외
            continue;
        }

        arr.push("<li class='c-accordion__item'>\n");
        arr.push("    <div class='c-accordion__head'>\n");
        arr.push("        <button class='runtime-data-insert c-accordion__button _btnAccordion' id='acc_header"+ chargeFlag+i +"' type='button' aria-expanded='false' aria-controls='acc_content"+ chargeFlag+i +"'  href='javascript:void(0);' rateAdsvcProdRelSeq='"+objList[i].rateAdsvcProdRelSeq+"' >\n");
        arr.push("        </button>\n");
        arr.push("        <div class='c-accordion__check'>\n");
        if (pageObj.additionTempKeyList.includes(objList[i].additionKey+"")) {
            arr.push("        <input class='c-checkbox c-checkbox--type3' type='checkbox' name='additionName' id='additionName_"+objList[i].additionKey+"' value='"+objList[i].additionKey+"' rantal="+objList[i].vatRantal+" txtName='"+objList[i].additionName+"' chargeFlag='"+objList[i].chargeFlag+"' checked />\n");
        } else {
            arr.push("        <input class='c-checkbox c-checkbox--type3' type='checkbox' name='additionName' id='additionName_"+objList[i].additionKey+"' value='"+objList[i].additionKey+"' rantal="+objList[i].vatRantal+" txtName='"+objList[i].additionName+"' chargeFlag='"+objList[i].chargeFlag+"'/>\n");
        }
        arr.push("        <label class='c-label' for='additionName_"+objList[i].additionKey+"'>"+objList[i].additionName+"</label>\n");
        arr.push("        </div>\n");

        if (objList[i].rantal > 0) {
            arr.push("        <span class='c-accordion__amount'>"+numberWithCommas(objList[i].vatRantal)+"원</span>\n");
        }

        arr.push("    </div>\n");
        arr.push("    <div class='c-accordion__panel expand c-expand--pop' id='acc_content"+ chargeFlag+i +"' aria-labelledby='acc_header"+ chargeFlag+i +"'>\n");
        arr.push("        <div class='c-accordion__inside'></div>\n");
        arr.push("    </div>\n");
        arr.push("</li>\n");
    }

    return arr.join('');
}

/** 동시신청 불가 부가서비스 조회 */
var getAdditionBetaList = function() {

    var varData = ajaxCommon.getSerializedData({
        grpCd:"additionbeta"
    });

    ajaxCommon.getItem({
            id:'getCodeList'
            ,cache:false
            ,url:'/getCodeListAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(objList){
            if (objList!=null) {
                pageObj.additionBetaList= objList;
            }
        });
}

/** 부가서비스 화면 세팅 */
var setAdditionListView = function(){
    // 초기화
    pageObj.additionKeyList = [];
    pageObj.reqAddition = [];
    $("#divAdditionListFree").empty();
    $("#divAdditionListPrice").empty();

    var totalRantal = 0;            // 총가격
    var AdditionListPriceCnt = 0;   // 유료 부가서비스 수
    var AdditionListAllCnt = 0;     // 전체 선택된 부가서비스 수


    // 선택된 부가서비스
    $("input:checkbox[name=additionName]:checked").each(function(index){

        var rantal = $(this).attr("rantal");
        var txtName = $(this).attr("txtName");
        var chargeFlag = $(this).attr("chargeFlag");
        var additionkey  = $(this).val();
        var arr =[];
        pageObj.additionKeyList.push(additionkey);
        pageObj.reqAddition.push(txtName);
        totalRantal += parseInt(rantal,10);

        if ("N" == chargeFlag ) {  // 무료 또는 기본제공
            arr.push(
                `<dl class='c-charge__item'>
                             <dt class='u-co-gray-9'>${txtName}<span class='u-fw--medium u-co-sub-4'>(무료)</span></dt>
                            <dd>
                                <button type='button' class='_btnDelAddition' data-addition-key='${additionkey}'>
                                    <span class='sr-only'>부가서비스 삭제</span>
                                    <i class='c-icon c-icon--delete-ty2' aria-hidden='true'></i>
                                </button>
                            </dd>
                    </dl>`
            );
            $("#divAdditionListFree").append(arr.join(''));
            AdditionListAllCnt += 1;

        } else if ("Y" == chargeFlag){
            var rantalTxt = numberWithCommas(rantal);
            arr.push(
                `<dl class='c-charge__item'>
                             <dt class='u-co-gray-9'>${txtName}<span class='u-fw--medium u-co-black'>(<em>${rantalTxt}</em>원)</span></dt></dt>
                            <dd>
                                <button type='button' class='_btnDelAddition' data-addition-key='${additionkey}'>
                                    <span class='sr-only'>부가서비스 삭제</span>
                                    <i class='c-icon c-icon--delete-ty2' aria-hidden='true'></i>
                                </button>
                            </dd>
                    </dl>`
            );
            $("#divAdditionListPrice").append(arr.join(''));
            AdditionListPriceCnt += 1;
            AdditionListAllCnt += 1;
        }
    });

    //캐치콜 플러스
    if ($("#cbAddServiceCatchCall").is(":checked")) {
        var rantal = pageObj.additionCatchCall.vatRantal;
        var txtName = pageObj.additionCatchCall.additionName;
        var additionkey  = pageObj.additionCatchCall.additionKey;
        pageObj.additionKeyList.push(additionkey);
        pageObj.reqAddition.push(txtName);
        totalRantal += parseInt(rantal,10) ;
    }

    //통화중대기 설정/해제(무료)
    const $checkbox = $("#callWaiting");
    const $modal = $("#regServiceModal");

    $checkbox.on("click", function() {
          if ($(this).is(":checked")) {
              var el = document.querySelector('#regServiceModal');
              var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
              modal.open();
          }
        });

    $("#totalRantalTxt").html(numberWithCommas(totalRantal));

    if (AdditionListAllCnt > 0) {
        $("#divAdditionListEmpty").hide();
        $("#divAdditionListFree").show();
        $("#divAdditionListPrice").show();
    } else {
        $("#divAdditionListEmpty").show();
        $("#divAdditionListFree").hide();
        $("#divAdditionListPrice").hide();
    }

    $("#spAdditionCnt").html(AdditionListAllCnt);
    pageObj.reqAdditionPrice = totalRantal;
}


$(document).on("click","._btnClose",function() {
    //이전에 선택 만.. 선택 처리
    $("input:checkbox[name=additionName]").each(function(index){
        var additionkey  = $(this).val();
        if (pageObj.additionKeyList.includes(additionkey)) {
            $(this).prop("checked", true); // 체크 해제
        } else {
            $(this).prop("checked", false); // 체크 해제
        }
    });
}) ;

$(document).on("click","._btnDelAddition",function() {
    let additionKey = $(this).data("additionKey");
    let $checkbox = $("input:checkbox[name=additionName][value='" + additionKey + "']");
    $checkbox.prop("checked", false); // 체크 해제
    $('#btnSetAddition').trigger("click");
});

/** 휴대폰 안심보험 리스트 조회 */
var getInsrProdInfo = function() {

    var varData = ajaxCommon.getSerializedData({
        rprsPrdtId:$("#rprsPrdtId").val()
        ,reqBuyType:$("#reqBuyType").val()
    });

    ajaxCommon.getItem({
            id:'selectInsrProdList'
            ,cache:false
            ,url:'/appform/selectInsrProdListAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonList){

            pageObj.insrProdList = jsonList;

            var insrProdCdTemp = $("#insrProdCdTemp").val(); // 임시저장된 안심보험

            $("#divInsrProdList").empty();
            for(var i = 0 ; i < jsonList.length ; i++) {
                $("#divInsrProdList").append(getRowTemplateInsrProd(jsonList[i], insrProdCdTemp));
                $("#insrProdCD_"+jsonList[i].insrProdCd).last().data(jsonList[i]);
            }

            if (insrProdCdTemp != "") {
                // 임시저장된 안심보험 선택 처리
                $("input:radio[name=insrProdCD][value="+insrProdCdTemp+"]").trigger("click");

                setTimeout(function(){
                    $("#btnInsrProdConfirm").trigger("click");
                }, 500);

                $("#insrProdCdTemp").val("");  //초기화
            }
        });
};

/** 휴대폰 안심보험 화면 세팅 - 행 template */
var getRowTemplateInsrProd = function(obj, insrProdCdParam){

    var arr =[];

    arr.push("<div class='c-card c-card--type2'>\n");
    if (obj.insrProdCd == insrProdCdParam) {
        arr.push(" <input class='c-checkbox c-checkbox--type4' id='insrProdCD_"+obj.insrProdCd+"' value='"+obj.insrProdCd+"' type='radio' name='insrProdCD' checked >\n");
    } else {
        arr.push(" <input class='c-checkbox c-checkbox--type4' id='insrProdCD_"+obj.insrProdCd+"' value='"+obj.insrProdCd+"' type='radio' name='insrProdCD'>\n");
    }
    arr.push("    <label class='c-card__label' for='insrProdCD_"+obj.insrProdCd+"'>\n");
    arr.push("        <span class='c-hidden'>선택</span>\n");
    arr.push("    </label>\n");

    var contentHtml= INSR_PROD_CONTENT[obj.insrProdCd];
    if (contentHtml != null) {
        arr.push(contentHtml);
    } else {
        arr.push("        <div class='c-card__title'>\n");
        arr.push("            <b>"+obj.insrProdNm+"</b>   \n");
        arr.push("        </div>\n");
    }
    arr.push("</div>\n");

    return arr.join('');
}

/** 휴대폰 안심보험 화면 세팅 - 선택 VIEW */
var getRowTemplateInsrProdView = function(obj){

    var arr =[];
    arr.push("<div class='c-card__box box-gray'>\n");

    var insrProdObj= INSR_PROD_OBJ[obj.insrProdCd];

    if (insrProdObj != null) {
        var insrProdDescList= INSR_PROD_DESC_LIST[obj.insrProdCd];
        var insrProdIcoList= INSR_PROD_ICO_LIST[obj.insrProdCd];

        arr.push("<div class='c-card__title u-mt--0'>\n");
        arr.push("    <b>"+insrProdObj.name+"</b>\n");
        arr.push("    <p class='c-card__sub u-pb--24'>\n");
        for (var i=0; i < insrProdDescList.length; i++) {
            arr.push("        <span>"+insrProdDescList[i]+"</span>\n");
        }
        arr.push("    </p>\n");
        arr.push("</div>\n");
        arr.push("<div class='c-card__price-box'>\n");
        arr.push("    <span class='ico-type'>\n");
        for (var i=0; i < insrProdIcoList.length; i++) {
            arr.push(insrProdIcoList[i]+"\n");
        }
        arr.push("    </span>\n");
        arr.push("    <span class='c-text c-text--type3 u-ml--auto'>월 이용료</span>\n");
        arr.push("    <span class='u-ml--4'>\n");
        arr.push("        <b>"+insrProdObj.price+" 원</b>\n");
        arr.push("    </span>\n");
        arr.push("</div>\n");
    } else {
        arr.push("    <div class='c-card__title u-mt--0'>\n");
        arr.push("        <b>"+obj.insrProdNm+"</b>\n");
        arr.push("    </div>\n");
    }
    arr.push("</div>\n");
    return arr.join('');
}

/** 안심보험 인증 STEP 초기화 */
var resetProdAuth = function(moduleType){

    if(moduleType == undefined) return;

    var varData = ajaxCommon.getSerializedData({moduleType: moduleType});

    ajaxCommon.getItemNoLoading({
            id:'resetProdAuth'
            ,cache:false
            ,async:false
            ,url:'/auth/resetProdAuth.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            //nothing
        });
}

/** 사은품 리스트 조회 */
var getGiftList = function (){

    if (pageObj.giftList.length > 0) {
        return null;
    }

    //var usimOrgnId = ajaxCommon.isNullNvl(pageObj.usimOrgnId, $("#cntpntShopId").val());
    var enggMnthCnt = ajaxCommon.isNullNvl($("#enggMnthCnt").val(), "0");

    var varData = ajaxCommon.getSerializedData({
        onOffType : $("#onOffType").val()
        , operType : $("#operType").val()
        , reqBuyType : $("#reqBuyType").val()
        , agrmTrm : enggMnthCnt
        , rateCd : $("#socCode").val()
        //, orgnId : usimOrgnId
        , orgnId : $("#cntpntShopId").val()
        , defaultOrgnId : $("#cntpntShopId").val()
        , modelId : $("#modelId").val()
    });

    ajaxCommon.getItem({
            id : 'getGiftList'
            , cache : false
            , async : false
            , url : '/appForm/giftBasListDefaultAjax.do'
            , data : varData
            , dataType : "json"
        }
        ,function(resultList){

            pageObj.giftList = resultList;

            var $divGift = $('#divGift');
            $divGift.empty();

            if(resultList != null && resultList.length > 0){

                var chooseGiftPrmt = resultList.filter(item => item.prmtType == "C");
                if(chooseGiftPrmt.length > 0){
                    $divGift.append("<h4 class='c-form__title--type2'>선택 사은품</h4>");
                    for(var i = 0 ; i < resultList.length ; i++) {
                        $divGift.append(getGiftRowTemplate(resultList[i]));
                    }
                    $divGift.show();
                    MCP.init();
                }else{
                    $divGift.hide();
                }
            } else {
                $divGift.hide();
            }
        });
};

/** 사은품 리스트 화면 세팅 - 행 template */
var getGiftRowTemplate = function(obj) {

    var arr =[];

    var giftPromotionDtlList = obj.giftPromotionDtlList;

    if("C" == obj.prmtType){  // 선택사은품
        arr.push("<div class='gift-pick-wrap u-mb--14'>\n");
        arr.push("    <h5 class='gift-pick-tit'>"+obj.prmtNm+"</span></h5>\n");
        arr.push("    <ul class='summary-badge-list'>\n");
        for(var i = 0 ; i < giftPromotionDtlList.length ; i++) {
            arr.push("        <li class='summary-badge-list__item'>\n");
            arr.push("            <input class='c-checkbox c-checkbox--type3' id='"+obj.prmtId+giftPromotionDtlList[i].prdtId+"' value='"+giftPromotionDtlList[i].prdtId+"' type='checkbox' prmtId='"+obj.prmtId+"' limitCnt='"+obj.limitCnt+"' prmtType='"+obj.prmtType+"' name='checkboxGift'>\n");
            arr.push("            <label class='c-label' for='"+obj.prmtId+giftPromotionDtlList[i].prdtId+"' >\n");
            arr.push("                <div class='summary-badge-list__item-img'>\n");
            arr.push("                    <img src='" + giftPromotionDtlList[i].webUrl + "' alt='" + giftPromotionDtlList[i].prdtNm + "' onerror=\"this.src='/resources/images/portal/content/img_phone_noimage.png';\">\n");
            arr.push("                </div>\n");
            arr.push("                <p>"+giftPromotionDtlList[i].prdtNm+"</p>\n");
            arr.push("            </label>\n");
            arr.push("        </li>\n");
        }
        arr.push("    </ul>\n");
        arr.push("</div>\n");
    }

    return arr.join('');
}

/** [step4] 안심보험 개별 약관 동의 시 전체약관 선택 버튼 처리 */
var checkAllTermsStep4 = function(){

    var isAllCheck= true;
    $("._agreeInsr").each(function(){
        if(!$(this).is(':checked')){
            isAllCheck= false;
        }
    });

    $("#btnInsrAllCheck").prop("checked", isAllCheck);
    isValidateNonEmptyStep4();
}

/** 사은품 선택여부 확인 */
var isValidateGift = function() {

    if (pageObj.giftList.length < 1) {
        return true; //사은품 없음
    }

    for(var i = 0 ; i < pageObj.giftList.length ; i++) {

        var limitCnt = pageObj.giftList[i].limitCnt;
        var prmtId = pageObj.giftList[i].prmtId;
        var prmtType =  pageObj.giftList[i].prmtType;

        if (prmtType == "C") {  // 선택사은품
            var checkCnt = $("input:checkbox[name=checkboxGift][prmtid="+prmtId+"]:checked").length;
            if (limitCnt > checkCnt) {
                return false;
            }
        }
    }

    return true;
};


/** [STEP1] 본인확인·약관동의 : 필수값 검사 */
var isValidateNonEmptyStep1 = function(){
    var operType = $("#operType").val() ;
    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    var reqBuyType = $("#reqBuyType").val() ;
    var onOffType = $("#onOffType").val() ;

    validator.config={};
    validator.config['cstmrType1'] = 'isNonEmpty';

    if(operType == OPER_TYPE.NEW && onOffType == "5"){
        //셀프개통 신규가입 신청서 내 ‘휴대폰 인증‘ 추가
        validator.config['isSmsAuth'] = 'isNonEmpty';
    }

    if (cstmrType == CSTMR_TYPE.NA) {
        //내국인
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn01'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn02'] = 'isNonEmpty';

    } else if (cstmrType == CSTMR_TYPE.NM) {
        //청소년
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn01'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn02'] = 'isNonEmpty';
        validator.config['minorAgentName'] = 'isNonEmpty';
        validator.config['minorAgentRelation'] = 'isNonEmpty';
        validator.config['minorAgentRrn01'] = 'isNonEmpty';
        validator.config['minorAgentRrn02'] = 'isNonEmpty';
        validator.config['minorAgentTelFn'] = 'isNonEmpty';
        validator.config['minorAgentTelMn'] = 'isNonEmpty';
        validator.config['minorAgentTelRn'] = 'isNonEmpty';
        validator.config['minorAgentAgrmYn'] = 'isNonEmpty';

    } else if (cstmrType == CSTMR_TYPE.FN) {
        //외국인
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['cstmrForeignerRrn01'] = 'isNonEmpty';
        validator.config['cstmrForeignerRrn02'] = 'isNonEmpty';
    }

    if (!pageObj.telAdvice) {
        validator.config['isAuth'] = 'isNonEmpty';
    }

    if(operType == OPER_TYPE.MOVE_NUM ){
        validator.config['clauseMoveCode'] = 'isNonEmpty';
    }
    validator.config['clausePriCollectFlag'] = 'isNonEmpty';
    validator.config['clausePriOfferFlag'] = 'isNonEmpty';
    validator.config['clauseEssCollectFlag'] = 'isNonEmpty';
    validator.config['clauseFathFlag01'] = 'isNonEmpty';
    validator.config['clauseFathFlag02'] = 'isNonEmpty';
    validator.config['notice'] = 'isNonEmpty'; //서비스 이용약관

    if (cstmrType == CSTMR_TYPE.NM) {
        validator.config['nwBlckAgrmYn'] = 'isNonEmpty';
        validator.config['appBlckAgrmYn'] = 'isNonEmpty';
    }

    if ($("#prdtSctnCd").val() == PRDT_SCTN_CD.FIVE_G_FOR_MSP) {
        validator.config['clause5gCoverage'] = 'isNonEmpty';
    }
    if ($("#jehuProdType").val() != null && $("#jehuProdType").val() != "") {
        validator.config['clauseJehuFlag'] = 'isNonEmpty';
    }
    if ($("#jehuPartnerType").val() != null && $("#jehuPartnerType").val() != "") {
        validator.config['clausePartnerOfferFlag'] = 'isNonEmpty';
    }
    if (onOffType == "5") {
        validator.config['clauseSimpleOpen'] = 'isNonEmpty';
    }

    if ("01" == $("#settlWayCd").val() ) {
        validator.config['clauseSettlWayCd'] = 'isNonEmpty';
    }

    if (validator.validate(true)) {

        $('._btnNext').removeClass('is-disabled');
    } else {
        $('._btnNext').addClass('is-disabled');
    }
} ;


/** [STEP1] 본인확인·약관동의 : 필수값 검사 및 데이터 세팅 */
var isValidateStep1 = function() {
    var operType = $("#operType").val() ;
    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    var reqBuyType = $("#reqBuyType").val() ;
    var onOffType = $("#onOffType").val() ;

    pageObj.applDataForm.reqBuyType = reqBuyType;
    pageObj.applDataForm.operType = $("#operType").val();
    pageObj.applDataForm.cstmrType = $("input:radio[name=cstmrType]:checked").val();
    pageObj.applDataForm.serviceType  = $("#serviceType").val();
    pageObj.applDataForm.cntpntShopId = $("#cntpntShopId").val();
    pageObj.applDataForm.socCode= $("#socCode").val();
    pageObj.applDataForm.onlineAuthType = $('input:radio[name=onlineAuthType]:checked').val();
    pageObj.applDataForm.reqWireType = ""  ;
    pageObj.applDataForm.prodId = $("#prodId").val();
    pageObj.applDataForm.bannerCd = $("#bannerCd").val() ;
    pageObj.applDataForm.prdtSctnCd = $("#prdtSctnCd").val() ;
    pageObj.applDataForm.rprsPrdtId = $("#rprsPrdtId").val();
    pageObj.applDataForm.modelSalePolicyCode= $("#modelSalePolicyCode").val();
    pageObj.applDataForm.sprtTp = $("#sprtTp").val();
    pageObj.applDataForm.resUniqId =  $("#resUniqId").val();

    //단말기 , 유심 통일성
    pageObj.applDataForm.modelMonthly = $('#modelMonthly').val();
    pageObj.applDataForm.modelId = $("#modelId").val();
    pageObj.applDataForm.reqModelName  = "";//$("#modelId option:selected").attr("hndsetModelNm");
    //pageObj.applDataForm.sntyColorCd= "";//$("#modelId option:selected").attr("atribValCd1") ; //색상코드
    //pageObj.applDataForm.sntyCapacCd="";// $("#modelId option:selected").attr("atribValCd2") ; //용량코드
    pageObj.applDataForm.enggMnthCnt = $('#enggMnthCnt').val();
    //pageObj.applDataForm.modelId = $("#prdtId").val();<=== 유심 아이디 설정
    //pageObj.applDataForm.reqModelName  ="";
    pageObj.applDataForm.usimKindsCd = $('#usimKindsCd').val();
    pageObj.applDataForm.uploadPhoneSrlNo = $('#uploadPhoneSrlNo').val();

    validator.config={};
    validator.config['cstmrType1'] = 'isNonEmpty';

    if(operType == OPER_TYPE.NEW && onOffType == "5"){
        //셀프개통 신규가입 신청서 내 ‘휴대폰 인증‘ 추가
        validator.config['isSmsAuth'] = 'isNonEmpty';
    }

    if (cstmrType == CSTMR_TYPE.NA) {
        //내국인
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
        pageObj.applDataForm.cstmrNativeRrn = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;
        pageObj.applDataForm.desCstmrNativeRrn =  $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;
        pageObj.applDataForm.birthDate = $.trim($("#cstmrNativeRrn01").val());
        pageObj.applDataForm.cstmrForeignerRrn = "";

    } else if (cstmrType == CSTMR_TYPE.NM) {
        //청소년
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isTeen';
        validator.config['minorAgentName'] = 'isNonEmpty';
        validator.config['minorAgentRelation'] = 'isNonEmpty';
        validator.config['minorAgentRrn01&minorAgentRrn02'] = 'isJimin';
        validator.config['minorAgentTelFn'] = 'isNumFix3';
        validator.config['minorAgentTelMn'] = 'isNumBetterFixN3';
        validator.config['minorAgentTelRn'] = 'isNumFix4';
        validator.config['minorAgentTelFn&minorAgentTelMn&minorAgentTelRn'] = 'isPhoneNumberCheck';
        validator.config['minorAgentAgrmYn'] = 'isNonEmpty'; //법정대리인 안내사황(필수)



        pageObj.applDataForm.cstmrNativeRrn = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;
        pageObj.applDataForm.desCstmrNativeRrn =   $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;
        pageObj.applDataForm.birthDate = $.trim($("#minorAgentRrn01").val());
        pageObj.applDataForm.cstmrForeignerRrn = "";
        pageObj.applDataForm.minorAgentName = $.trim($("#minorAgentName").val());
        pageObj.applDataForm.minorAgentRrn = $.trim($("#minorAgentRrn01").val())  + $.trim($("#minorAgentRrn02").val()) ;
        pageObj.applDataForm.minorAgentTelFn = $.trim($("#minorAgentTelFn").val());
        pageObj.applDataForm.minorAgentTelMn = $.trim($("#minorAgentTelMn").val());
        pageObj.applDataForm.minorAgentTelRn = $.trim($("#minorAgentTelRn").val());
        pageObj.applDataForm.minorAgentRelation = $("#minorAgentRelation").val();
        pageObj.applDataForm.minorAgentAgrmYn = $("#minorAgentAgrmYn").is(":checked") ? "Y":"N";


    } else if (cstmrType == CSTMR_TYPE.FN) {
        //외국인
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['cstmrForeignerRrn01&cstmrForeignerRrn02'] = 'isFngno';

        pageObj.applDataForm.cstmrNativeRrn = ""  ;
        pageObj.applDataForm.desCstmrNativeRrn =  ""  ;
        pageObj.applDataForm.birthDate = $.trim($("#cstmrForeignerRrn01").val());
        pageObj.applDataForm.cstmrForeignerRrn = $.trim($("#cstmrForeignerRrn01").val()) + $.trim($("#cstmrForeignerRrn02").val()) ;
    }

    if (!pageObj.telAdvice) {
        validator.config['isAuth'] = 'isNonEmpty';
    }
    pageObj.applDataForm.cstmrName = $.trim($("#cstmrName").val());
    pageObj.applDataForm.telAdvice = pageObj.telAdvice ? "Y":"N";

    if(operType == OPER_TYPE.MOVE_NUM ){
        validator.config['clauseMoveCode'] = 'isNonEmpty';
    }
    validator.config['clausePriCollectFlag'] = 'isNonEmpty';
    validator.config['clausePriOfferFlag'] = 'isNonEmpty';
    validator.config['clauseEssCollectFlag'] = 'isNonEmpty';
    validator.config['clauseFathFlag01'] = 'isNonEmpty';
    validator.config['clauseFathFlag02'] = 'isNonEmpty';
    // validator.config['clausePriTrustFlag'] = 'isNonEmpty'; //개인정보위탁동의
    // validator.config['clauseConfidenceFlag'] = 'isNonEmpty'; //신용정보조회이용동의
    validator.config['notice'] = 'isNonEmpty'; //서비스 이용약관

    if (cstmrType == CSTMR_TYPE.NM) {
        validator.config['nwBlckAgrmYn'] = 'isNonEmpty';
        validator.config['appBlckAgrmYn'] = 'isNonEmpty';
        pageObj.applDataForm.nwBlckAgrmYn = $("#nwBlckAgrmYn").is(":checked") ? "Y":"N";
        pageObj.applDataForm.appBlckAgrmYn = $("#appBlckAgrmYn").is(":checked") ? "Y":"N";
    } else {
        pageObj.applDataForm.nwBlckAgrmYn = "";
        pageObj.applDataForm.appBlckAgrmYn = "";
    }

    if ($("#prdtSctnCd").val() == PRDT_SCTN_CD.FIVE_G_FOR_MSP) {
        validator.config['clause5gCoverage'] = 'isNonEmpty';
        pageObj.applDataForm.clause5gCoverageFlag = $("#clause5gCoverage").is(":checked") ? "Y":"N";
    }

    if ($("#jehuProdType").val() != null && $("#jehuProdType").val() != "") {
        validator.config['clauseJehuFlag'] = 'isNonEmpty';
        pageObj.applDataForm.clauseJehuFlag = $("#clauseJehuFlag").is(":checked") ? "Y":"N";
        pageObj.applDataForm.jehuProdType = $.trim($("#jehuProdType").val());
    }
    if ($("#jehuPartnerType").val() != null && $("#jehuPartnerType").val() != "") {
        validator.config['clausePartnerOfferFlag'] = 'isNonEmpty';
        pageObj.applDataForm.clausePartnerOfferFlag = $("#clausePartnerOfferFlag").is(":checked") ? "Y":"N";
    }


    if (onOffType == "5") {
        validator.config['clauseSimpleOpen'] = 'isNonEmpty';
    }

    if ("01" == $("#settlWayCd").val() ) {
        validator.config['clauseSettlWayCd'] = 'isNonEmpty';
    }

    pageObj.applDataForm.clauseFinanceFlag = $("#clauseFinanceFlag").is(":checked") ? "Y":"N";
    pageObj.applDataForm.clausePriCollectFlag = $("#clausePriCollectFlag").is(":checked") ? "Y":"N";
    pageObj.applDataForm.clausePriOfferFlag = $("#clausePriOfferFlag").is(":checked") ? "Y":"N";
    pageObj.applDataForm.clauseConfidenceFlag = $("#clauseConfidenceFlag").is(":checked") ? "Y":"N";

    pageObj.applDataForm.clauseEssCollectFlag = $("#clauseEssCollectFlag").is(":checked") ? "Y":"N";
    pageObj.applDataForm.clauseFathFlag = $("#clauseFathFlag01").is(":checked") && $("#clauseFathFlag02").is(":checked") ? "Y":"N";
    pageObj.applDataForm.clausePriTrustFlag = $("#clausePriTrustFlag").is(":checked") ? "Y":"N";
    //동의 추가
    pageObj.applDataForm.personalInfoCollectAgree = $("#personalInfoCollectAgree").is(":checked") ? "Y":"N"; //고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의
    pageObj.applDataForm.clausePriAdFlag = $("#clausePriAdFlag").is(":checked") ? "Y":"N"; // 개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의
    pageObj.applDataForm.othersTrnsAgree = $("#formOthersTrnsAgree").is(":checked") ? "Y":"N"; // 혜택 제공을 위한 제3자 제공 동의 : M모바일
    pageObj.applDataForm.othersTrnsKtAgree = $("#formOthersTrnsKtAgree").is(":checked") ? "Y":"N"; // 혜택 제공을 위한 제3자 제공 동의: KT
    pageObj.applDataForm.othersAdReceiveAgree = $("#othersAdReceiveAgree").is(":checked") ? "Y":"N"; // 제3자 제공관련 광고 수신 동의


    if (validator.validate()) {
        // if (true) { //test
        return true;
    } else {
        var errId = validator.getErrorId();
        MCP.alert(validator.getErrorMsg() ,function (){
            if (errId == "isAuth") {
                setTimeout(function(){
                    $("#onlineAuthType1").focus();
                }, 500);
            } else if (errId == "isSmsAuth" ) {
                setTimeout(function(){
                    $("#btnSmsAuth").focus();
                }, 500);
            } else {
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            }
        });
        return false;
    }
}


/** [STEP1] 본인확인·약관동의 : 값 저장 */
var fnSaveTempStep1 = function() {
    var paramObj = APPL_DATA_FORM();
    paramObj.telAdvice = pageObj.telAdvice ? "Y":"N";
    paramObj.tmpStep = "01";
    paramObj.operType = $("#operType").val();
    paramObj.requestKey = $("#requestKeyTemp").val() ;
    paramObj.cstmrType = $("input:radio[name=cstmrType]:checked").val();
    paramObj.cstmrName = $.trim($("#cstmrName").val());
    paramObj.cstmrNativeRrn = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;  ;

    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    if (cstmrType == CSTMR_TYPE.NM) {
        paramObj.minorAgentName = $.trim($("#minorAgentName").val());
        paramObj.minorAgentRrn = $.trim($("#minorAgentRrn01").val()) + $.trim($("#minorAgentRrn02").val()) ;
        paramObj.minorAgentTelFn = $.trim($("#minorAgentTelFn").val());
        paramObj.minorAgentTelMn = $.trim($("#minorAgentTelMn").val());
        paramObj.minorAgentTelRn = $.trim($("#minorAgentTelRn").val());
        paramObj.minorAgentRelation = $("#minorAgentRelation").val();
    } else if (cstmrType == CSTMR_TYPE.FN) {
        paramObj.cstmrForeignerRrn = $.trim($("#cstmrForeignerRrn01").val()) +$.trim($("#cstmrForeignerRrn02").val()) ;
        paramObj.cstmrNativeRrn = "";
    }

    var varData = ajaxCommon.getSerializedData(paramObj);
    var isStepSucc= false;
    ajaxCommon.getItemNoLoading({
            id:'updateRequestTemp'
            ,cache:false
            ,async:false
            ,url:'/appForm/updateRequestTemp.do'
            ,data: varData
            ,dataType:"json"
            ,errorCall : function () {
                //nothing
            }
        }
        ,function(jsonObj){
            if(jsonObj.RESULT_CODE == "STEP01" || jsonObj.RESULT_CODE == "STEP02"){ // STEP 오류
                isStepSucc= false;
                MCP.alert(jsonObj.RESULT_MSG);
            }else{
                isStepSucc= true;
            }
        }); // end of ajax----
    return isStepSucc;
};

/** [STEP2] 유심정보·신분증 확인 : 필수값 검사 */
var isValidateNonEmptyStep2 = function(){

    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    var operType = $("#operType").val() ;
    validator.config={};

    var onOffLineUsim = $("input:radio[name='onOffLineUsim']:checked").val();
    if (onOffLineUsim == "offLine") {
        // 유심정보
        validator.config['reqUsimSn'] = 'isNonEmpty';
        validator.config['isUsimNumberCheck'] = 'isNonEmpty';
    }

    // 연락처
    validator.config['cstmrMobileFn'] = 'isNonEmpty';
    validator.config['cstmrMobileMn'] = 'isNonEmpty';
    validator.config['cstmrMobileRn'] = 'isNonEmpty';

    if (cstmrType == CSTMR_TYPE.FN) {
        //외국인
        validator.config['cstmrForeignerNation'] = 'isNonEmpty';
    }
    // 메일
    validator.config['cstmrMail'] = 'isNonEmpty';

    // 주소
    validator.config['cstmrPost'] = 'isNonEmpty';

    //안면인증 
    if($("#fathTrgYn").val() == "Y") {
        validator.config['isFathTxnRetv'] = 'isNonEmpty';
    }
    pageObj.applDataForm.fathTrgYn = $("#fathTrgYn").val();
    
    
    // 본인조회동의 여부
    validator.config['selfInqryAgrmYnFlag'] = 'isNonEmpty';

    // 신분증정보
    validator.config['selfIssuExprDt'] = 'isIssuDate';

    var selfCertType = $("input:radio[name=selfCertType]:checked").val();
    if (selfCertType=="02") {
        validator.config['driverSelfIssuNum1'] = 'isNonEmpty';
        validator.config['driverSelfIssuNum2'] = 'isNonEmpty';
    } else if (selfCertType=="04") {
        validator.config['selfIssuNum'] = 'isNonEmpty';
    }

    if (validator.validate(true)) {
        $('._btnNext').removeClass('is-disabled');
    } else {
        $('._btnNext').addClass('is-disabled');
    }
}

/** [STEP2] 유심정보·신분증 확인 : 필수값 검사 및 데이터 세팅 */
var isValidateStep2 = function(){

    var cstmrType = $("input:radio[name=cstmrType]:checked").val();

    validator.config={};
    var onOffLineUsim = $("input:radio[name='onOffLineUsim']:checked").val();

    if (onOffLineUsim == "offLine") {
        // 유심정보
        validator.config['reqUsimSn'] = 'isNumFix19';
        validator.config['isUsimNumberCheck'] = 'isNonEmpty';
        pageObj.applDataForm.reqUsimSn = $.trim($("#reqUsimSn").val());
    }


    // 연락처
    validator.config['cstmrMobileFn'] = 'isNumFix3';
    validator.config['cstmrMobileMn'] = 'isNumBetterFixN3';
    validator.config['cstmrMobileRn'] = 'isNumFix4';
    validator.config['cstmrMobileFn&cstmrMobileMn&cstmrMobileRn'] = 'isPhoneNumberCheck';

    pageObj.applDataForm.cstmrMobileFn = $.trim($("#cstmrMobileFn").val());
    pageObj.applDataForm.cstmrMobileMn = $.trim($("#cstmrMobileMn").val());
    pageObj.applDataForm.cstmrMobileRn = $.trim($("#cstmrMobileRn").val());

    if ($("#cstmrTelFn").val() != "" && $("#cstmrTelMn").val() != "" && $("#cstmrTelRn").val() != "") {
        validator.config['cstmrTelFn'] = 'isNumBetterFixN2';
        validator.config['cstmrTelMn'] = 'isNumBetterFixN3';
        validator.config['cstmrTelRn'] = 'isNumFix4';
        validator.config['cstmrTelFn&cstmrTelMn&cstmrTelRn'] = 'isTelNumberCheck';
        pageObj.applDataForm.cstmrTelFn = $.trim($("#cstmrTelFn").val());
        pageObj.applDataForm.cstmrTelMn = $.trim($("#cstmrTelMn").val());
        pageObj.applDataForm.cstmrTelRn = $.trim($("#cstmrTelRn").val());
    } else {
        //휴대폰 번호로 설정
        pageObj.applDataForm.cstmrTelFn = $.trim($("#cstmrMobileFn").val());
        pageObj.applDataForm.cstmrTelMn = $.trim($("#cstmrMobileMn").val());
        pageObj.applDataForm.cstmrTelRn = $.trim($("#cstmrMobileRn").val());
    }

    if (cstmrType == CSTMR_TYPE.FN) {
        validator.config['cstmrForeignerNation'] = 'isNonEmpty';
        pageObj.applDataForm.cstmrForeignerNation = $.trim($("#cstmrForeignerNation").val());
    }

    // 메일
    validator.config['cstmrMail'] = 'isNonMail';
    pageObj.applDataForm.cstmrMail = $.trim($("#cstmrMail").val());

    // 주소
    validator.config['cstmrPost'] = 'isNonEmpty';
    pageObj.applDataForm.cstmrPost = $.trim($("#cstmrPost").val());
    pageObj.applDataForm.cstmrAddr = $.trim($("#cstmrAddr").val());
    pageObj.applDataForm.cstmrAddrDtl = $.trim($("#cstmrAddrDtl").val());

    //신분증 타입
    validator.config['selfCertType1'] = 'isNonEmpty';
    
    //안면인증 
    if($("#fathTrgYn").val() == "Y") {
        validator.config['isFathTxnRetv'] = 'isNonEmpty';
    }
    
    // 본인조회동의 여부
    validator.config['selfInqryAgrmYnFlag'] = 'isNonEmpty';
    pageObj.applDataForm.selfInqryAgrmYn = $("#selfInqryAgrmYnFlag").is(":checked") ? "Y" : "N";

    // 신분증정보
    validator.config['selfIssuExprDt'] = 'isIssuDate';

    var selfCertType = $("input:radio[name=selfCertType]:checked").val();
    var selfIssuExprDt = $("#selfIssuExprDt").val();
    if (selfIssuExprDt != null) {
        selfIssuExprDt = selfIssuExprDt.replace(/-/gi,"");
    }
    if (cstmrType == CSTMR_TYPE.NM) {
        //청소년
        pageObj.applDataForm.minorSelfCertType = $("input:radio[name=selfCertType]:checked").val();
        pageObj.applDataForm.minorSelfInqryAgrmYn = $("#selfInqryAgrmYnFlag").is(":checked") ? "Y":"N";
        pageObj.applDataForm.minorSelfIssuExprDt = selfIssuExprDt;
        if (selfCertType=="02") {
            validator.config['driverSelfIssuNum1'] = 'isNonEmpty';
            validator.config['driverSelfIssuNum2'] = 'isNumFix10';
            pageObj.applDataForm.minorSelfIssuNum = $("#driverSelfIssuNum1").val() + $.trim($("#driverSelfIssuNum2").val());
        } else if (selfCertType=="04") {
            validator.config['selfIssuNum'] = 'isNonEmpty';//발급 만료일자
            pageObj.applDataForm.minorSelfIssuNum = $("#selfIssuNum").val();
        }
    } else {
        pageObj.applDataForm.selfCertType = $("input:radio[name=selfCertType]:checked").val();
        pageObj.applDataForm.selfInqryAgrmYn = $("#selfInqryAgrmYnFlag").is(":checked") ? "Y":"N";
        pageObj.applDataForm.selfIssuExprDt = selfIssuExprDt;
        if (selfCertType=="02") {
            validator.config['driverSelfIssuNum1'] = 'isNonEmpty';
            validator.config['driverSelfIssuNum2'] = 'isNumFix10';
            pageObj.applDataForm.selfIssuNum = $("#driverSelfIssuNum1").val() + $.trim($("#driverSelfIssuNum2").val());
        } else if (selfCertType=="04") {
            validator.config['selfIssuNum'] = 'isNonEmpty';//발급 만료일자
            pageObj.applDataForm.selfIssuNum = $("#selfIssuNum").val();
        }
    }


    if (validator.validate(true)) {
        return true;
    } else {
        var errId = validator.getErrorId();
        MCP.alert(validator.getErrorMsg() ,function (){
            if (errId == "isUsimNumberCheck") {
                setTimeout(function(){
                    $("#btnUsimNumberCheck").focus();
                }, 500);
            }  else {
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
                $selectObj.focus();
            }
        });
        return false;
    }
}

/** [STEP2] 유심정보·신분증 확인 : 값 저장 */
var fnSaveTempStep2 = function(){

    var paramObj = APPL_DATA_FORM();
    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    var onOffLineUsim = $("input:radio[name='onOffLineUsim']:checked").val();

    paramObj.requestKey = $("#requestKeyTemp").val();
    paramObj.telAdvice = pageObj.telAdvice ? "Y" : "N";
    paramObj.tmpStep = "02";
    if (onOffLineUsim == "offLine") {
        paramObj.clauseMpps35Flag = "N";
        paramObj.reqUsimSn = $.trim($("#reqUsimSn").val());
    } else {
        paramObj.clauseMpps35Flag = "Y";
    }

    paramObj.clauseMpps35Flag = "N";
    paramObj.reqUsimSn = $.trim($("#reqUsimSn").val());

    paramObj.cstmrMobileFn = $.trim($("#cstmrMobileFn").val());
    paramObj.cstmrMobileMn = $.trim($("#cstmrMobileMn").val());
    paramObj.cstmrMobileRn = $.trim($("#cstmrMobileRn").val());
    paramObj.cstmrTelFn = $.trim($("#cstmrTelFn").val());
    paramObj.cstmrTelMn = $.trim($("#cstmrTelMn").val());
    paramObj.cstmrTelRn = $.trim($("#cstmrTelRn").val());
    if (cstmrType == CSTMR_TYPE.FN) {
        paramObj.cstmrForeignerNation = $.trim($("#cstmrForeignerNation").val());
    }


    paramObj.cstmrMail = $.trim($("#cstmrMail").val());
    paramObj.cstmrPost = $.trim($("#cstmrPost").val());
    paramObj.cstmrAddr = $.trim($("#cstmrAddr").val());
    paramObj.cstmrAddrDtl = $.trim($("#cstmrAddrDtl").val());

    var selfCertType = $("input:radio[name=selfCertType]:checked").val();
    var selfIssuExprDt = $("#selfIssuExprDt").val();
    if (selfIssuExprDt != null) {
        selfIssuExprDt = selfIssuExprDt.replace(/-/gi,"");
    }

    paramObj.selfCertType = selfCertType;
    paramObj.selfIssuExprDt = selfIssuExprDt;

    if (selfCertType=="02") {
        paramObj.cstmrJejuId = $("#driverSelfIssuNum1").val()
        paramObj.selfIssuNum = $.trim($("#driverSelfIssuNum2").val());
    } else if (selfCertType=="04") {
        paramObj.selfIssuNum = $("#selfIssuNum").val();
    }

    var varData = ajaxCommon.getSerializedData(paramObj);

    ajaxCommon.getItemNoLoading({
            id:'updateRequestTemp'
            ,cache:false
            ,url:'/appForm/updateRequestTemp.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            //nothing
        })
}

/** [STEP3] 가입신청 정보 : 필수값 검사 */
var isValidateNonEmptyStep3 = function(){

    var operType = $("#operType").val();
    var onOffType = $("#onOffType").val() ;

    validator.config={};
    if (operType==OPER_TYPE.MOVE_NUM ) {
        //번호이동
        validator.config['moveCompany'] = 'isNonEmpty';
        validator.config['moveMobileFn'] = 'isNonEmpty';
        validator.config['moveMobileMn'] = 'isNonEmpty';
        validator.config['moveMobileRn'] = 'isNonEmpty';

    } else if(operType==OPER_TYPE.NEW){
        if(onOffType != "5"){
            validator.config['reqWantNumber'] = 'isNonEmpty';
            validator.config['reqWantNumber2'] = 'isNonEmpty';
            validator.config['reqWantNumber3'] = 'isNonEmpty';
        } else {
            validator.config['reqCnt'] = 'isNonEmpty';
        }
    }

    if (validator.validate(true)) {
        $('._btnNext').removeClass('is-disabled');
    } else {
        $('._btnNext').addClass('is-disabled');
    }
}

/** [STEP3] 가입신청 정보 : 필수값 검사 및 데이터 세팅 */
var isValidateStep3 = function(){
    var operType = $("#operType").val() ;
    var onOffType = $("#onOffType").val() ;

    validator.config={};
    if (operType==OPER_TYPE.MOVE_NUM ) {
        //번호이동
        validator.config['moveCompany'] = 'isNonEmpty';
        validator.config['moveMobileFn&moveMobileMn&moveMobileRn'] = 'isNumsMNP';

        pageObj.applDataForm.moveCompany = $("#moveCompany").val();
        pageObj.applDataForm.mpCode = $("#moveCompany option:selected").attr("mpCode");
        pageObj.applDataForm.moveMobileFn = $.trim($("#moveMobileFn").val());
        pageObj.applDataForm.moveMobileMn = $.trim($("#moveMobileMn").val());
        pageObj.applDataForm.moveMobileRn = $.trim($("#moveMobileRn").val());
        pageObj.applDataForm.moveThismonthPayType = "NM";
        pageObj.applDataForm.moveAllotmentStat = "AD";
        pageObj.applDataForm.moveRefundAgreeFlag = "Y";

    } else if(operType==OPER_TYPE.NEW){
        if(onOffType != "5"){
            validator.config['reqWantNumber&reqWantNumber2&reqWantNumber3'] = 'isNumFix4IsNotEqualsTriple';
            pageObj.applDataForm.reqWantNumber = $.trim($("#reqWantNumber").val());
            pageObj.applDataForm.reqWantNumber2 = $.trim($("#reqWantNumber2").val());
            pageObj.applDataForm.reqWantNumber3 = $.trim($("#reqWantNumber3").val());
        } else {
            validator.config['reqCnt'] = 'isNonEmpty';
            var reqCnt = $.trim($("#reqCnt").val());
            pageObj.applDataForm.reqWantNumber = $.trim($("#reqWantNumberN").val());
            pageObj.applDataForm.reqWantNumber2 = reqCnt.slice(-8, reqCnt.length-4);
            pageObj.applDataForm.reqWantNumber3 = reqCnt.slice(-4, reqCnt.length);
        }
    }

    if (validator.validate()) {
        return true;
    } else {
        var errId = validator.getErrorId();
        var errorMsg = validator.getErrorMsg();
        //console.log("isValidateStep3 errId==>" + errId);
        if (errId == "moveCompany") {
            if ($("input:radio[name=moveCompanyGroup1]:checked").val() != undefined
                && $("#moveCompanyGroup2").val() == "") {
                errorMsg = "알뜰폰 통신사을 선택해 주세요.";
            } else if ($("input:radio[name=moveCompanyGroup1]:checked").val() == "-1") {
                errorMsg = "알뜰폰 사업자을 선택해 주세요.";
            }  else {
                errorMsg = "사용중인 통신사을 선택해 주세요.";
            }
        }

        MCP.alert(errorMsg ,function (){
            if (errId == "reqCnt") {
                setTimeout(function(){
                    $("#btnSearchNumber").focus();
                }, 500);
            } else if (errId == "moveCompany") {
                setTimeout(function(){
                    if ($("input:radio[name=moveCompanyGroup1]:checked").val() == undefined) {
                        $("input:radio[name=moveCompanyGroup1]").eq(0).focus();
                    } else if ($("#moveCompanyGroup2").val() == "") {
                        $("#moveCompanyGroup2").focus();
                    }
                }, 500);

            } else {
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            }
        });
        return false;
    }
}

/** [STEP3] 가입신청 정보 : 값 저장 */
var fnSaveTempStep3 = function(){

    var operType = $("#operType").val();
    var onOffType = $("#onOffType").val();

    var paramObj = APPL_DATA_FORM();
    paramObj.requestKey = $("#requestKeyTemp").val();
    paramObj.telAdvice = pageObj.telAdvice ? "Y" : "N";
    paramObj.tmpStep = "03";

    if (operType==OPER_TYPE.MOVE_NUM ) {
        paramObj.moveCompany = $("#moveCompany").val();
        paramObj.moveMobileFn = $.trim($("#moveMobileFn").val());
        paramObj.moveMobileMn = $.trim($("#moveMobileMn").val());
        paramObj.moveMobileRn = $.trim($("#moveMobileRn").val());
    }else if(operType==OPER_TYPE.NEW){
        if(onOffType != "5"){
            paramObj.reqWantNumber = $.trim($("#reqWantNumber").val());
            paramObj.reqWantNumber2 = $.trim($("#reqWantNumber2").val());
            paramObj.reqWantNumber3 = $.trim($("#reqWantNumber3").val());
        }
    }

    var varData = ajaxCommon.getSerializedData(paramObj);

    ajaxCommon.getItemNoLoading({
            id:'updateRequestTemp3'
            ,cache:false
            ,url:'/appForm/updateRequestTemp3.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            //nothing
        })
}

/** [STEP4] 부가서비스 정보 : 필수값 검사 */
var isValidateNonEmptyStep4 = function(){

    validator.config={};

    //휴대폰 안심보험
    if (pageObj.insrProdCd != ""){
        validator.config['clauseInsrProdFlag'] = 'isNonEmpty';
        validator.config['clauseInsrProdFlag02'] = 'isNonEmpty';
        validator.config['clauseInsrProdFlag03'] = 'isNonEmpty';
        validator.config['isAuthInsr'] = 'isNonEmpty';
    }

    //아무나 SOLO 결합 가입 동의
    var combineSoloType = $("input:radio[name=combineSoloType]:checked").val();
    if (combineSoloType == "Y"){
        validator.config['combineSoloFlag'] = 'isNonEmpty';
    }

    if (validator.validate(true)) {
        $('._btnNext').removeClass('is-disabled');
    } else {
        $('._btnNext').addClass('is-disabled');
    }
}

/** [STEP4] 부가서비스 정보 : 필수값 검사 및 데이터 세팅 */
var isValidateStep4 = function(){

    validator.config={};

    // 추천인 정보
    var recommendInfo = ajaxCommon.isNullNvl($("#recommendInfo").val(), "");

    if (recommendInfo != "") {

        pageObj.applDataForm.recommendFlagCd = $("#recommendFlagCd").val();
        pageObj.applDataForm.recommendInfo = $.trim($("#recommendInfo").val());

        if ("01" == $("#recommendFlagCd").val()) {

            var commendId = $.trim($("#recommendInfo").val());
            $("#commendId").val(commendId.toUpperCase());

            validator.config['commendId'] = 'isCommendId';
            pageObj.applDataForm.commendId = commendId.toUpperCase();

        } else {
            pageObj.applDataForm.commendId = "";
        }
    } else {
        pageObj.applDataForm.recommendFlagCd = "" ;
        pageObj.applDataForm.recommendInfo = "";
        pageObj.applDataForm.commendId = "";
    }

    // 부가서비스
    pageObj.applDataForm.reqAddition = pageObj.reqAddition.join(',');
    pageObj.applDataForm.additionKeyList = pageObj.additionKeyList;

    //휴대폰안심보험
    if (pageObj.insrProdCd != ""){
        validator.config['clauseInsrProdFlag'] = 'isNonEmpty';
        validator.config['clauseInsrProdFlag02'] = 'isNonEmpty';
        validator.config['clauseInsrProdFlag03'] = 'isNonEmpty';
        validator.config['isAuthInsr'] = 'isNonEmpty';

        pageObj.applDataForm.insrProdCd = pageObj.insrProdCd;
    } else {
        pageObj.applDataForm.insrProdCd = "";
    }

    // 자급제 보상 서비스
    pageObj.applDataForm.rwdProdCd = "";

    // 사은품
    pageObj.prmtIdList = [];
    pageObj.prdtIdList = [];

    $("input:checkbox[name=checkboxGift]:checked").each(function() {
        var prmtId = $(this).attr("prmtId");
        var prdtId = $(this).val();

        pageObj.prmtIdList.push(prmtId);
        pageObj.prdtIdList.push(prdtId);
    });

    pageObj.applDataForm.prmtIdList = pageObj.prmtIdList;
    pageObj.applDataForm.prdtIdList = pageObj.prdtIdList;

    //아무나 SOLO 결합 가입 동의
    var combineSoloType = $("input:radio[name=combineSoloType]:checked").val();

    if (combineSoloType == "Y"){
        validator.config['combineSoloFlag'] = 'isNonEmpty';
        pageObj.applDataForm.combineSoloFlag = "Y";
    } else {
        pageObj.applDataForm.combineSoloFlag = "";
    }
    pageObj.applDataForm.combineSoloType = combineSoloType;

    if (validator.validate()) {
        return true;
    } else {
        var errId = validator.getErrorId();
        MCP.alert(validator.getErrorMsg() ,function (){
            $selectObj = $("#"+errId);
            viewErrorMgs($selectObj, validator.getErrorMsg());
        });
        return false;
    }
}

/** [STEP4] 부가서비스 정보 : 값 저장 */
var fnSaveTempStep4 = function() {

    var paramObj = APPL_DATA_FORM();
    paramObj.requestKey = $("#requestKeyTemp").val();
    paramObj.telAdvice = pageObj.telAdvice ? "Y" : "N";
    paramObj.tmpStep = "04";

    paramObj.recommendInfo = $.trim($("#recommendInfo").val());
    paramObj.recommendFlagCd = $("#recommendFlagCd").val();

    paramObj.additionKeyList = pageObj.additionKeyList;

    if (pageObj.insrProdCd != ""){
        paramObj.insrProdCd = pageObj.insrProdCd;
    } else {
        paramObj.insrProdCd = "";
    }

    paramObj.rwdProdCd= "";

    //아무나 SOLO 결합 가입
    paramObj.combineSoloType = $("input:radio[name=combineSoloType]:checked").val();
    if(paramObj.combineSoloType == "Y"){
        paramObj.combineSoloFlag = "Y";
        paramObj.additionKeyList.push("138");
    } else {
        paramObj.combineSoloFlag = "";
        paramObj.additionKeyList = paramObj.additionKeyList.filter(item => item !== "138");
    }

    var varData = ajaxCommon.getSerializedData(paramObj);

    ajaxCommon.getItemNoLoading({
            id:'updateRequestTemp4'
            ,cache:false
            ,url:'/appForm/updateRequestTemp4.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            //nothing
        })
};

/** STEP5로 이동 */
var nextStepFive = function() {

    var onOffType = $("#onOffType").val() ;

    fnSaveTempStep4();  // 신청서 임시 저장
    setStep(5);
    isValidateNonEmptyStep5();
    if(onOffType == "5") {
        $("._btnNext").html("개통요청");
    }  else {
        $("._btnNext").html("주문완료");
    }
}

/** [step5] 납부정보·가입정보 확인 : 필수값 검사 */
var isValidateNonEmptyStep5 = function(){

    var reqPayType = $('input:radio[name=reqPayType]:checked').val();

    validator.config={};

    if (reqPayType== "D") { //자동이체
        validator.config['reqBank'] = 'isNonEmpty';
        validator.config['reqAccountNumber'] = 'isNonEmpty';
        validator.config['isCheckAccount'] = 'isNonEmpty';

    } else if (reqPayType== "C") { // 신용카드
        validator.config['reqCardNo'] = 'isNonEmpty';
        validator.config['reqCardMm'] = 'isNonEmpty';
        validator.config['reqCardYy'] = 'isNonEmpty';
        validator.config['isCheckCard'] = 'isNonEmpty';
    }

    if (validator.validate(true)) {
        $('._btnNext').removeClass('is-disabled');
    } else {
        $('._btnNext').addClass('is-disabled');
    }
}

/** [step5] 납부정보·가입정보 확인 : 필수값 검사 및 데이터 세팅 */
var isValidateStep5 = function(){

    var reqPayType = $('input:radio[name=reqPayType]:checked').val();
    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    validator.config={};

    // 명세서 수신 유형
    pageObj.applDataForm.cstmrBillSendCode = $('input:radio[name=cstmrBillSendCode]:checked').val();
    pageObj.applDataForm.cstmrMailReceiveFlag = "";

    // 요금 납부방법
    pageObj.applDataForm.reqPayType = reqPayType;

    if (reqPayType== "D") { // 자동이체

        validator.config['reqBank'] = 'isNonEmpty';
        validator.config['reqAccountNumber'] = 'accountChk';
        validator.config['isCheckAccount'] = 'isNonEmpty';

        pageObj.applDataForm.reqBank = $("#reqBank").val();
        pageObj.applDataForm.reqAccountNumber = $.trim($("#reqAccountNumber").val());
        if (cstmrType == CSTMR_TYPE.NA) {
            pageObj.applDataForm.reqAccountName = $.trim($("#cstmrName").val());
            pageObj.applDataForm.reqAccountRrn = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;
        } else if (cstmrType == CSTMR_TYPE.NM) {
            pageObj.applDataForm.reqAccountName = $.trim($("#minorAgentName").val());
            pageObj.applDataForm.reqAccountRrn = $.trim($("#minorAgentRrn01").val()) + $.trim($("#minorAgentRrn02").val()) ;
        } else if (cstmrType == CSTMR_TYPE.FN) {
            pageObj.applDataForm.reqAccountName = $.trim($("#cstmrName").val());
            pageObj.applDataForm.reqAccountRrn = $.trim($("#cstmrForeignerRrn01").val()) + $.trim($("#cstmrForeignerRrn02").val()) ;
        }

    } else if (reqPayType== "C") { // 신용카드

        validator.config['reqCardNo'] = 'isNumBetterFixN14';
        validator.config['reqCardMm'] = 'isNonEmpty';
        validator.config['reqCardYy'] = 'isNonEmpty';
        validator.config['isCheckCard'] = 'isNonEmpty';

        pageObj.applDataForm.reqCardNo = $.trim($("#reqCardNo").val()) ;
        pageObj.applDataForm.reqCardYy = $.trim($("#reqCardYy").val());
        pageObj.applDataForm.reqCardMm = $.trim($("#reqCardMm").val());

        if (cstmrType == CSTMR_TYPE.NA) {
            pageObj.applDataForm.reqCardName = $.trim($("#cstmrName").val());
            pageObj.applDataForm.reqCardRrn = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;
        } else if (cstmrType == CSTMR_TYPE.NM) {
            pageObj.applDataForm.reqCardName = $.trim($("#minorAgentName").val());
            pageObj.applDataForm.reqCardRrn = $.trim($("#minorAgentRrn01").val()) + $.trim($("#minorAgentRrn02").val()) ;
        } else if (cstmrType == CSTMR_TYPE.FN) {
            pageObj.applDataForm.reqCardName = $.trim($("#cstmrName").val());
            pageObj.applDataForm.reqCardRrn = $.trim($("#cstmrForeignerRrn01").val()) + $.trim($("#cstmrForeignerRrn02").val()) ;
        }
    }

    if (validator.validate()) {
        return true;
    } else {
        var errId = validator.getErrorId();
        MCP.alert(validator.getErrorMsg() ,function (){
            $selectObj = $("#"+errId);
            viewErrorMgs($selectObj, validator.getErrorMsg());
        });
        return false;
    }
}

/** [step5] 납부정보·가입정보 확인 : 값 저장 */
var fnSaveTempStep5 = function(){

    var paramObj = APPL_DATA_FORM();
    var reqPayType = $('input:radio[name=reqPayType]:checked').val();

    paramObj.requestKey = $("#requestKeyTemp").val();
    paramObj.telAdvice = pageObj.telAdvice ? "Y" : "N";
    paramObj.tmpStep = "05";

    paramObj.cstmrBillSendCode = $('input:radio[name=cstmrBillSendCode]:checked').val();
    paramObj.reqPayType = reqPayType;

    if (reqPayType== "D") { //자동이체
        paramObj.reqBank = $("#reqBank").val();
        paramObj.reqAccountNumber = $.trim($("#reqAccountNumber").val());
    } else if (reqPayType== "C") { // 신용카드
        paramObj.reqCardNo = $.trim($("#reqCardNo").val());
        paramObj.reqCardMm = $.trim($("#reqCardMm").val());
        paramObj.reqCardYy = $.trim($("#reqCardYy").val());
    }

    var varData = ajaxCommon.getSerializedData(paramObj);

    ajaxCommon.getItemNoLoading({
            id:'updateRequestTemp5'
            ,cache:false
            ,url:'/appForm/updateRequestTemp5.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            //nothing
        });
}

/** [공통] 신청서 작성 STEP 별 화면 세팅 */
var setStep = function(setStep) {

    window.scrollTo(0, 0);
    pageObj.step = setStep;

    var tIndex = setStep-1;
    var onOffType = $("#onOffType").val() ;

    $("#ulStepper > li").each (function(){
        $(this).removeClass("c-stepper__item--active");
        $(this).find(".c-stepper__title").remove();
    });
    $("#ulStepper > li").eq(tIndex).addClass("c-stepper__item--active").append("<span class='c-stepper__title'>"+stepText[tIndex]+"</span>");

    $("._divStep").hide();
    $("#step"+setStep).show();

    if (setStep == 2) {
        if (stepInfo.isInitStep2) {

            if ($("input[name='onOffLineUsim']:checked").length > 0) {
                $("input[name='onOffLineUsim']:checked").trigger("change",{'isInit':true});
            }

            stepInfo.isInitStep2 = false;
        }

    } else if (setStep == 3) {
        if (stepInfo.isInitStep3) {
            $("input:radio[name=reqPayType]:checked").trigger("click");    //3
            stepInfo.isInitStep3 = false;
        }

    } else if (setStep == 4) {

        if (stepInfo.isInitStep4) {

            // 친구초대 추천인ID
            $("#recommendFlagCd").trigger("change");

            // 임시 저장 부가서비스 조회
            getAdditionTempList();
            getAdditionList("N");
            getAdditionList("Y");

            //아무나 SOLO 결합 가능여부
            getCombineSoloType($("#socCode").val());

            let additionCatchCallList =  pageObj.additionList.filter(additionTemp => additionTemp.additionKey === pageObj.additionCatchCallKey);
            if (additionCatchCallList != null && additionCatchCallList.length > 0) {
                pageObj.additionCatchCall = additionCatchCallList[0];
            }

            if (pageObj.additionTempKeyList.length > 0) {
                $("#btnSetAddition").trigger("click");   // 부가서비스 화면 세팅
            }

            //임시 저장 안심보험
            var insrProdCdTemp = $("#insrProdCdTemp").val() ;
            if (insrProdCdTemp !="" && pageObj.insrProdList.length < 1 && !pageObj.telAdvice) {
                getInsrProdInfo();  // 안심보험 리스트 조회
            }

            //사은품 정보
            getGiftList();

            stepInfo.isInitStep4 = false;
        }

        isValidateNonEmptyStep4();
    }

    var operType = $("#operType").val();
    if (setStep == 1) {
        $("#divBtnGroup1").show();
        $("#divBtnGroup2").hide();
    } else {
        if( operType == OPER_TYPE.NEW && onOffType == "5" && setStep == 3){
            $("#divBtnGroup1").show();
            $("#divBtnGroup2").hide();
        } else if( operType == OPER_TYPE.MOVE_NUM && onOffType == "5" && setStep == 4 ){
            $("#divBtnGroup1").show();
            $("#divBtnGroup2").hide();
        }else  {
            $("#divBtnGroup1").hide();
            $("#divBtnGroup2").show();
        }
    }
};

/** 에러메시지 화면 표출 */
var viewErrorMgs = function($thatObj, msg ) {
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3") ) {
        $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
};

/** 번호이동 사전동의 요청 NP1 */
var fnReqPreMoveAut = function() {

    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    var custIdntNo = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val());
    var mpCode= $("#moveCompany option:selected").attr("mpCode");

    pageObj.applDataForm.moveMobileFn = $.trim($("#moveMobileFn").val());
    pageObj.applDataForm.moveMobileMn = $.trim($("#moveMobileMn").val());
    pageObj.applDataForm.moveMobileRn = $.trim($("#moveMobileRn").val());

    var varData = ajaxCommon.getSerializedData({
        npTlphNo : $.trim($("#moveMobileFn").val())+$.trim($("#moveMobileMn").val())+$.trim($("#moveMobileRn").val())
        ,bchngNpCommCmpnCd : mpCode
        ,slsCmpnCd : $("#cntpntShopId").val()
        ,custIdntNoIndCd : "01"
        ,custIdntNo : custIdntNo
        ,custNm : $.trim($("#cstmrName").val())
        ,custTypeCd : cstmrType
    });

    ajaxCommon.getItemNoLoading({
        id:'conPreCheckAjax'
        ,cache:false
        ,url:'/appform/reqNpPreCheckAjax.do'
        ,data: varData
        ,dataType:"json"
    }, function(jsonObj){

        KTM.LoadingSpinner.hide(true);

        if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {

            var companyNm =  $("#moveCompany option:selected").attr("companyNm");
            var companyTel = $("#moveCompany option:selected").attr("companyTel");
            pageObj.applDataForm.globalNoNp1 = jsonObj.GLOBAL_NO;

            $("#divArs .ars-company__name").html(companyNm);
            $("#divArs .ars-company__number").html(companyTel);

            $("#isNpPreCheck").val("1");

            $("#moveMobileFn").prop("readonly","readonly");
            $("#moveMobileMn").prop("readonly","readonly");
            $("#moveMobileRn").prop("readonly","readonly");
            $("._btnNext").html("동의 완료");

            MCP.alert("<i class='c-icon c-icon--notice' aria-hidden='true'></i><br><br>받으신 문자 내 URL 동의 또는<br>확인버튼 클릭 후 안내되는 번호로 전화를 걸어<br><br>번호이동 확인(동의)를 진행 해 주세요.", function() {
                $("#divArs").show();
                isValidateNonEmptyStep3();
            });

        } else {

            var strTitle = "번호이동 진행 실패";
            var strHtml = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.";

            if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_NP1_MSG[jsonObj.OSST_RESULT_CODE] != undefined) {
                strHtml = SIMPLE_NP1_MSG[jsonObj.OSST_RESULT_CODE];
            } else if (jsonObj.ERROR_NE_MSG != undefined) {
                strHtml = jsonObj.ERROR_NE_MSG;
            }

            $("#simpleDialog ._title").html(strTitle);
            $("#simpleDialog ._detail").html(strHtml);
            $("._simpleDialogButton").hide();
            $("#divDefaultButton").show();

            var el = document.querySelector('#simpleDialog');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
        }
    })
}

/** 번호이동 사전동의 결과 조회 NP3 */
var fnReqPreMoveAgree = function() {

    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    var custIdntNo = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val());
    var mpCode = $("#moveCompany option:selected").attr("mpCode");

    pageObj.applDataForm.moveMobileFn = $.trim($("#moveMobileFn").val());
    pageObj.applDataForm.moveMobileMn = $.trim($("#moveMobileMn").val());
    pageObj.applDataForm.moveMobileRn = $.trim($("#moveMobileRn").val());

    var varData = ajaxCommon.getSerializedData({
        npTlphNo : $.trim($("#moveMobileFn").val())+$.trim($("#moveMobileMn").val())+$.trim($("#moveMobileRn").val())
        ,bchngNpCommCmpnCd : mpCode
        ,slsCmpnCd : $("#cntpntShopId").val()
        ,custIdntNoIndCd : "01"
        ,custIdntNo : custIdntNo
        ,custNm : $.trim($("#cstmrName").val())
        ,custTypeCd : cstmrType
    });

    ajaxCommon.getItemNoLoading({
        id:'conPreAgreeAjax'
        ,cache:false
        ,async:false
        ,url:'/appform/reqNpAgreeAjax.do'
        ,data: varData
        ,dataType:"json"
    }, function(jsonObj){

        if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {

            var rsltCd = jsonObj.RSLT_CD;
            if ("S" != rsltCd) {
                KTM.LoadingSpinner.hide(true);
                MCP.alert("현 통신사의 번호이동 사전동의 승인이<br/> 완료되지 않았습니다.<br/>문자 또는 ARS번호로 사전동의 절차<br/> 완료 후 재시도 바랍니다.<br/><br/>ARS 또는 문자로 이미 동의를 완료하셨을 경우 1~2분 후 다시 한번 동의 완료 버튼을 클릭 해 주시기 바랍니다. "
                    ,function (){ isValidateNonEmptyStep3(); }
                );
            } else {
                pageObj.applDataForm.globalNoNp3 = jsonObj.GLOBAL_NO;
            }

        } else {
            KTM.LoadingSpinner.hide(true);
            MCP.alert("처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요");
        }
    })
}

/** 개통 사전체크 요청 PC0 */
var fnReqPreCheck = function(isArs) {

    var onOffType = $("#onOffType").val();
    var errMsg = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다." ;
    var operType = $("#operType").val();

    if (isArs == undefined || isArs == null) {
        isArs = false;
    }

    if (!isArs) {
        KTM.LoadingSpinner.show();
    }

    if (operType == OPER_TYPE.NEW) {
        pageObj.applDataForm.reqSeq = pageObj.niceResSmsObj.reqSeq;
        pageObj.applDataForm.resSeq = pageObj.niceResSmsObj.resSeq;
    } else {
        var mpCode = $("#moveCompany option:selected").attr("mpCode");
        if(!isNpExceptionCompany(mpCode)){
            fnReqPreMoveAgree(); //사전동의 결과조회 (NP3)
            if (""== pageObj.applDataForm.globalNoNp3) {
                return;
            }
        }
    }

    pageObj.applDataForm.onOffType = onOffType;
    pageObj.applDataForm.openMarketId = pageObj.openMarketId;
    pageObj.applDataForm.evntCdPrmt = $("#evntCdPrmt").val();

    var varData = ajaxCommon.getSerializedData(pageObj.applDataForm);

    ajaxCommon.getItemNoLoading({
        id:'reqPreCheckAjax'
        ,cache:false
        ,url:'/appform/reqPreOpenCheckAjax.do'
        ,data: varData
        ,dataType:"json"
        ,errorCall : function () {
            KTM.LoadingSpinner.hide(true);
            MCP.alert(errMsg);
        }
    } ,function(jsonObj){
        if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {

            pageObj.requestKey = jsonObj.REQUEST_KET;
            pageObj.resNo = jsonObj.RES_NO;
            pageObj.checkCnt = 0;
            pageObj.osstResultCode = "99999";
            fnSelfPreOpenCallBack(); // 개통 사전체크 결과 확인 (PC2)

        } else if(jsonObj.RESULT_CODE == "IP_FAIL") {

            KTM.LoadingSpinner.hide(true);
            MCP.alert("셀프개통 이용이 불가합니다.");
            $('._btnNext').addClass('is-disabled');
        } else {
            if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_PC0_RE_TRY[jsonObj.OSST_RESULT_CODE] != undefined && pageObj.fnReqPreCheckCount < 30) {
                setTimeout(function(){
                    pageObj.fnReqPreCheckCount++;
                    pageObj.applDataForm.globalNoNp3 = "";
                    fnReqPreCheck(isArs);
                }, 20000);
                return;
            }

            KTM.LoadingSpinner.hide(true);

            var strTitle = "번호이동 진행 실패";
            if(operType == OPER_TYPE.NEW){
                strTitle ="신규가입 진행 실패";
            } else {
                strTitle ="번호이동 진행 실패";
            }

            if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_PC0_MSG[jsonObj.OSST_RESULT_CODE] != undefined) {
                errMsg = SIMPLE_PC0_MSG[jsonObj.OSST_RESULT_CODE];
            } else if (jsonObj.ERROR_NE_MSG != undefined) {
                errMsg = jsonObj.ERROR_NE_MSG;
            }

            pageObj.osstResultCode = jsonObj.OSST_RESULT_CODE;

            $("#simpleDialog ._title").html(strTitle);
            $("#simpleDialog ._detail").html(errMsg);
            $("._simpleDialogButton").hide();
            $("#divDefaultButton").show();

            var el = document.querySelector('#simpleDialog');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
        }
    });
}

/** 개통 사전체크 결과 확인 PC2 */
var fnSelfPreOpenCallBack = function() {

    var varData = ajaxCommon.getSerializedData({
        resNo: pageObj.resNo
        ,prgrStatCd: "PC2"
    });

    KTM.LoadingSpinner.show();

    ajaxCommon.getItemNoLoading({
            id:'conPreCheckAjax'
            ,cache:false
            ,url:'/appform/conPreCheckAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){

            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                var operType = $("#operType").val() ;
                var onOffType = $("#onOffType").val() ;
                if(operType == OPER_TYPE.NEW && onOffType == "5"){
                    setStep(3);
                    isValidateNonEmptyStep3();
                } else {
                    setStep(4);
                }
                $('._btnNext').html("다음").show();
                $("#divBtnGroup1").show();
                $("#divBtnGroup2").hide();
                pageObj.osstResultCode = "9999";
                KTM.LoadingSpinner.hide(true);

            } else if ("9999" ==  jsonObj.RESULT_CODE) {
                if (pageObj.checkCnt < 21) {

                    ++pageObj.checkCnt
                    setTimeout(function(){
                        fnSelfPreOpenCallBack();
                    }, 5000);

                } else {
                    KTM.LoadingSpinner.hide(true);
                    MCP.alert("아직 고객 사전체크 결과가 확인되지 않습니다.<br>확인까지 최대 2분이 소요 될 수 있습니다.",function (){
                        pageObj.checkCnt = 0;
                        fnSelfPreOpenCallBack();
                    });
                }
            } else {

                KTM.LoadingSpinner.hide(true);

                var operType = $("#operType").val() ;
                var strHtml = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.";

                if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_PC2_MSG[jsonObj.OSST_RESULT_CODE] != undefined) {
                    strHtml = SIMPLE_PC2_MSG[jsonObj.OSST_RESULT_CODE];
                } else if (jsonObj.RESULT_MSG != undefined) {
                    strHtml = jsonObj.RESULT_MSG;
                }

                pageObj.osstResultCode = jsonObj.OSST_RESULT_CODE;

                var strTitle = "";
                if(operType == OPER_TYPE.NEW){
                    strTitle ="신규가입 진행 실패";
                } else {
                    strTitle ="번호이동 진행 실패";
                }

                $("#simpleDialog ._title").html(strTitle);
                $("#simpleDialog ._detail").html(strHtml);
                $("._simpleDialogButton").hide();
                $("#divDefaultButton").show();

                var el = document.querySelector('#simpleDialog');
                var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                modal.open();
            }
        });
}

/** 번호조회 NU1 */
var searchNumber = function() {

    $(".c-form__text").remove();
    $(".has-error").removeClass("has-error");

    validator.config = {}
    validator.config['reqWantNumberN'] = 'isNumFix4';

    if (validator.validate()) {

        var varData = ajaxCommon.getSerializedData({
            reqWantNumber : $("#reqWantNumberN").val()
            ,requestKey : pageObj.requestKey
        });

        ajaxCommon.getItem({
                id:'searchNumber'
                ,cache:false
                ,url:'/appform/searchNumberAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){

                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {

                    var phoneNoList = jsonObj.RESULT_OBJ_LIST;
                    var arr =[];
                    var i =0;

                    $("#searchCnt").html("조회 가능 횟수 <b>" + (20-jsonObj.SEARCH_CNT) +"회</b>");
                    for (i=0; i < phoneNoList.length; i++) {
                        arr.push("<input type='radio' class='c-radio' name='rdoCtn' id='"+phoneNoList[i].tlphNo+"' value='"+phoneNoList[i].tlphNo+"'  ");
                        arr.push("   tlphNoStatCd='"+phoneNoList[i].tlphNoStatCd+"' tlphNoOwnCmncCmpnCd='"+phoneNoList[i].tlphNoOwnCmncCmpnCd+"' ");
                        arr.push("   cntValue='"+phoneNoList[i].tlphNoView+"' encdTlphNo='"+phoneNoList[i].encdTlphNo+"'  />\n");
                        arr.push("<label class='c-label u-mb--20' for='"+phoneNoList[i].tlphNo+"'>"+phoneNoList[i].tlphNoView+"</label>\n");
                    }

                    $("#searchNumDialog ._list").empty();
                    $("#searchNumDialog ._list").append(arr.join(''));

                    var el = document.querySelector('#searchNumDialog');
                    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                    modal.open();

                } else if (jsonObj.RESULT_CODE ==  "0001") {
                    MCP.alert("선택 가능한 번호가 없습니다.\n다른 번호로 입력 하시기 바랍니다.");
                } else if (jsonObj.RESULT_CODE ==  "0004") {
                    MCP.alert("조회가능 횟수를 초과하였습니다. \n추가 조회를 원하신다면 신청서를 다시 작성해주세요." ,function (){
                        history.back();
                    });
                } else {
                    MCP.alert("번호 조회에 실패 하였습니다.");
                }
            });
    } else {
        var errId = validator.getErrorId();
        MCP.alert(validator.getErrorMsg() ,function (){
            $selectObj = $("#"+errId);
            viewErrorMgs($selectObj, validator.getErrorMsg());
        });
        return;
    }
}

/** 번호예약 취소 NU2 */
var cancelNumber = function() {

    KTM.LoadingSpinner.show();

    var varData = ajaxCommon.getSerializedData({
        reqWantNumber:$("#reqWantNumberN").val()
    });

    ajaxCommon.getItemNoLoading({
            id:'cancelNumberAjax'
            ,cache:false
            ,url:'/appform/cancelNumberAjax.do'
            ,data: varData
            ,dataType:"json"
            ,errorCall : function () {
                KTM.LoadingSpinner.hide(true);
            }
        }
        ,function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {

                $("#reqCnt").val("");
                $("#reqWantNumberN").val("");
                $('#reqWantNumberN').prop('readonly', false);

                $("._searchBefor").show();
                $("._searchAfter").hide();

                $('#btnSearchNumber').html("번호조회");
                $("#btnSearchNumber").attr("action", "RSV");

                isValidateNonEmptyStep3();

                setTimeout(function() {
                    KTM.LoadingSpinner.hide(true);
                }, 500);

            } else {
                MCP.alert("전화번호 예약 취소가 실패 하였습니다.");
                KTM.LoadingSpinner.hide(true);
            }
        });
};

/** 개통 요청 OP0 */
var fnReqSelf = function(){

    $('._btnNext').hide();

    var onOffType = $("#onOffType").val();
    pageObj.applDataForm.onOffType = onOffType;
    pageObj.applDataForm.requestKey = pageObj.requestKey;

    // UPDATE 되지 않도록 처리
    pageObj.applDataForm.cstmrPost = "";
    pageObj.applDataForm.cstmrAddr = "";
    pageObj.applDataForm.cstmrAddrDtl = "";

    var varData = ajaxCommon.getSerializedData(pageObj.applDataForm);

    ajaxCommon.getItemNoLoading({
        id:'reqSimpleOpenAjax'
        ,cache:false
        ,url:'/appform/reqSimpleOpenAjax.do'
        ,data: varData
        ,dataType:"json"
    } ,function(jsonObj){

        $('._btnNext').show();

        if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
            pageObj.checkCnt = 0;
            fnSelfOpenCallback();  // 개통 결과 확인

        } else if(jsonObj.RESULT_CODE == "IP_FAIL") {

            KTM.LoadingSpinner.hide(true);
            MCP.alert("셀프개통 이용이 불가합니다.");
            $('._btnNext').addClass('is-disabled');

        } else {

            KTM.LoadingSpinner.hide(true);

            var errMsg = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.";
            if (jsonObj.ERROR_NE_MSG != undefined) {
                errMsg = jsonObj.ERROR_NE_MSG;
            }

            var operType = $("#operType").val();
            var strTitle = "";
            if(operType == OPER_TYPE.NEW){
                strTitle ="신규가입 진행 실패";
            } else {
                strTitle ="번호이동 진행 실패";
            }

            $("#simpleDialog ._title").html(strTitle);
            $("#simpleDialog ._detail").html(errMsg);
            $("._simpleDialogButton").hide();
            $("#divDefaultButton").show();
            var el = document.querySelector('#simpleDialog');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
        }
    });
}

/** 개통 결과 확인 OP2 */
var fnSelfOpenCallback = function() {

    $("#divBtnGroup1").show();
    $("#divBtnGroup2").hide();

    pageObj.step = 6;

    $("._btnNext").html("개통확인");
    $('._btnNext').removeClass('is-disabled');

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
    },function(jsonObj){

        if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {

            ajaxCommon.createForm({
                method:"post"
                ,action:"/appForm/appJehuFormComplete.do"
            });

            ajaxCommon.attachHiddenElement("requestKey", pageObj.requestKey);
            ajaxCommon.formSubmit();

        } else if ("9999" ==  jsonObj.RESULT_CODE) {
            if (pageObj.checkCnt < 21) {
                ++pageObj.checkCnt
                setTimeout(function(){
                    fnSelfOpenCallback();
                }, 5000);
            } else {
                KTM.LoadingSpinner.hide(true);
                MCP.alert("아직 개통 상태 정보가 확인되지 않습니다.<br>확인까지 최대 2분이 소요 될 수 있습니다.");
            }

        } else {

            KTM.LoadingSpinner.hide(true);
            if ("|RESULT_2028|RESULT_2016|RESULT_2028_01|RESULT_9000_01".indexOf(jsonObj.OSST_RESULT_CODE) > 0) {

                // 요금납부 방법 초기화
                $('#isCheckAccount').val("");
                $('#isCheckCard').val("");

                $('#reqCardYy').prop('disabled', false);
                $('#reqCardMm').prop('disabled', false);
                $('#reqCardNo').prop('readonly', false);

                $('#reqBank').prop('disabled', false);
                $('#reqAccountNumber').prop('readonly', false);

                $("#btnCheckAccount").removeClass('is-complete').html("계좌번호 체크");
                $("#btnCheckCardNo").removeClass('is-complete').html("신용카드 체크");
            }

            var strHtml = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.";
            if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_OP2_MSG[jsonObj.OSST_RESULT_CODE] != undefined) {
                strHtml = SIMPLE_OP2_MSG[jsonObj.OSST_RESULT_CODE];
            } else if (jsonObj.RESULT_MSG != undefined) {
                strHtml = jsonObj.RESULT_MSG;
            }

            var strTitle = "";
            var operType = $("#operType").val();

            if(operType == OPER_TYPE.NEW){
                strTitle ="신규가입 진행 실패";
            } else {
                strTitle ="번호이동 진행 실패";
            }

            $("#simpleDialog ._title").html(strTitle);
            $("#simpleDialog ._detail").html(strHtml);
            $("._simpleDialogButton").hide();
            $("#divDefaultButton").show();

            var el = document.querySelector('#simpleDialog');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
        }
    });
};

/** 가입비 면제 여부 조회 */
var getJoinUsimPriceInfo = function() {

    var cntpntShopId = $("#cntpntShopId").val();
    var socCode = $("#socCode").val();  // 진입 요금제코드
    var varData = "";

    if(CONTPNT_SHOP_ID.BASE == cntpntShopId) {
        varData = ajaxCommon.getSerializedData({
            dtlCd:socCode
            ,cdGroupId:CD_GROUP_ID_OBJ.JoinUsimPriceInfo
        });
    }else{
        varData = ajaxCommon.getSerializedData({
            dtlCd:cntpntShopId
            ,cdGroupId:CD_GROUP_ID_OBJ.JoinUsimPriceInfoOther
        });
    }

    ajaxCommon.getItem({
            id:'getJoinUsimPriceInfo'
            ,url:'/getCodeNmAjax.do'
            ,data: varData
            ,dataType:"json"
            ,cache:false
            ,async:false
        }
        ,function(jsonObj){

            if(jsonObj != null && jsonObj.expnsnStrVal1 == "Y"){
                $("#joinPriceIsPay").val("Y");  // 가입비 필수
            }else{
                $("#joinPriceIsPay").val("N");
            }

            getJoinUsimPrice();  // 가입비 조회
        });
}

/** 가입비 조회 */
var getJoinUsimPrice = function() {

    var varData = ajaxCommon.getSerializedData({
        dataType: $("#prdtSctnCd").val()
        , operType: $("#operType").val()
        , rateCd: ''
    });

    ajaxCommon.getItem({
        id: 'getUsimInfo'
        , url: '/usim/selectUsimBasJoinPriceAjax.do'
        , data: varData
        , dataType: "json"
        , cache: false
        , async: false
    }, function (jsonObj) {
        if ('0001' == jsonObj.RESULT_CODE) {

            var joinUsimPrice = jsonObj.selectJoinUsimPrice;
            if (joinUsimPrice != undefined && joinUsimPrice.length > 0) {
                $("#joinPrice").val(numberWithCommas(joinUsimPrice[0].joinPrice));
            }

            showJoinUsimPrice();  // 가입비 화면 세팅
        }
    });
}

/** 가입비 화면 세팅 */
var showJoinUsimPrice = function() {

    var joinPriceIsPay = $("#joinPriceIsPay").val();
    var joinPrice = $('#joinPrice').val();

    if (joinPriceIsPay == "Y") {
        $("#joinPriceTxt").html(numberWithCommas(joinPrice)+" 원");
    } else {
        $("#joinPriceTxt").html("<span class='c-text u-td-line-through'>"+numberWithCommas(joinPrice)+" 원</span>(무료)");
    }
}

/** 하단 플로팅 정보 조회 및 세팅 */
var setBottomFloatingView = function(){

    var rateInfo = getRateInfo();      // 요금제 정보
    var plcyInfo = getSalePlcyInfo();  // 정책 정보

    $('#ddOperTypeNm').text($('#operTypeNm').val());

    if(rateInfo != null){
        $('#bottomTitle2').text(rateInfo.rateNm);
        $('#pRateNm').text(rateInfo.rateNm);
        $("#baseAmt").html(numberWithCommas(rateInfo.baseVatAmt)+" 원");                     // 기본요금
        $("#dcAmtTxt").html(numberMinusWithCommas(rateInfo.dcVatAmt)+" 원");                 // 기본할일
        $("#addDcAmt").html(numberMinusWithCommas(rateInfo.addDcVatAmt)+" 원");              // 요금할인
        $("#promotionDcAmtTxt").html(numberMinusWithCommas(rateInfo.promotionDcAmt)+" 원");  // 프로모션 할인
        $("#payMnthChargeAmtTxt").html(numberWithCommas(rateInfo.payMnthChargeAmt));         // 월 통신요금

        var modelMonthlyVal = ajaxCommon.isNullNvl($("#modelMonthly").val(), "0");
        if ("0" == modelMonthlyVal) {
            $("#paySumMnthTxt").html(numberWithCommas(rateInfo.payMnthChargeAmt));                        // 월 납부금액
            $("#paySumMnthTxt2").html(numberWithCommas(rateInfo.payMnthChargeAmt));                       // 월 납부금액
        } else {
            $("#paySumMnthTxt").html(numberWithCommas(rateInfo.payMnthAmt+rateInfo.payMnthChargeAmt));    // 월 납부금액
            $("#paySumMnthTxt2").html(numberWithCommas(rateInfo.payMnthAmt+rateInfo.payMnthChargeAmt));   // 월 납부금액
        }
    }
}

/** 요금제 정보 조회 */
var getRateInfo = function(){

    var rateInfo = null;

    var agrmTrm = ajaxCommon.isNullNvl($("#enggMnthCnt").val(), "0");
    var modelMonthlyVal = ajaxCommon.isNullNvl($("#modelMonthly").val(), "0");

    var varData = ajaxCommon.getSerializedData({
        salePlcyCd:   $("#modelSalePolicyCode").val()    // 정책
        ,prdtId:       $("#rprsPrdtId").val()            // 상품
        ,orgnId:       $("#cntpntShopId").val()          // 조직
        ,operType:     $("#operType").val()              // 가입유형
        ,rateCd:       $("#socCode").val()               // 요금제
        ,onOffType:    $("#onOffType").val()             // 개통유형
        ,noArgmYn:     "Y"
        ,agrmTrm:      agrmTrm                           // 약정
        ,plcySctnCd:   "02"                              // 정책구분 (02: 유심)
        ,modelMonthly: modelMonthlyVal                   // 할부
    });

    ajaxCommon.getItemNoLoading({
            id:'listChargeInfoAjax'
            ,url:'/msp/listChargeInfoAjax.do'
            ,data: varData
            ,dataType:"json"
            ,cache:false
            ,async:false
        }
        ,function(objList){

            if(objList != null && objList.length > 0){
                rateInfo= objList[0];
            }
        });

    return rateInfo;
}

/** 정책 정보 조회 */
var getSalePlcyInfo = function(){

    var plcyInfo = null;
    var modelSalePolicyCode = $("#modelSalePolicyCode").val();

    ajaxCommon.getItemNoLoading({
            id:'getSalePlcyAjax'
            ,cache:false
            ,url:'/msp/getSalePlcyAjax.do'
            ,data: ajaxCommon.getSerializedData({salePlcyCd:modelSalePolicyCode})
            ,dataType:"json"
            ,async:false
        }
        ,function(obj){

            if(obj != null){
                $("#sprtTp").val(obj.sprtTp);
                plcyInfo = obj;
            }
        });

    return plcyInfo;
}

var fnSaveForm = function() {
    var onOffType = $("#onOffType").val() ;
    var onOffLineUsim = $("input:radio[name='onOffLineUsim']:checked").val();

    pageObj.applDataForm.onOffType = onOffType ;
    pageObj.applDataForm.cstmrPost = $.trim($("#cstmrPost").val());
    pageObj.applDataForm.cstmrAddr = $.trim($("#cstmrAddr").val());
    pageObj.applDataForm.cstmrAddrDtl = $.trim($("#cstmrAddrDtl").val());

    pageObj.applDataForm.usimKindsCd = "06"; // 미발송으로 설정
    var dlvryType = "";
    if (onOffLineUsim == "offLine") { //유심 보유
        dlvryType = "00";  // 00 소지유심
    } else { //유심 미보유
        dlvryType = "04"; // 04 제휴유심미보유
    }

    pageObj.applDataForm.dlvryType = dlvryType ;
    pageObj.applDataForm.requestKey=0;
    pageObj.applDataForm.evntCdPrmt = $("#evntCdPrmt").val();

    var varData = ajaxCommon.getSerializedData(pageObj.applDataForm);
    ajaxCommon.getItemNoLoading({
            id:'saveAppform'
            ,cache:false
            ,url:'/appForm/saveAppformAjax.do'
            ,data: varData
            ,dataType:"json"
            ,errorCall : function () {
                KTM.LoadingSpinner.hide(true);
                $('._btnNext').show();
            }
        }
        ,function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                pageObj.requestKey = jsonObj.REQUEST_KET ;
                var varData = ajaxCommon.getSerializedData({
                    requestKey:pageObj.requestKey,
                    mobileNo : pageObj.applDataForm.cstmrMobileFn + pageObj.applDataForm.cstmrMobileMn + pageObj.applDataForm.cstmrMobileRn
                });
                if("04" == dlvryType ){ // 유심 미보유의 경우 유심등록 URL 전송
                    ajaxCommon.getItemNoLoading({
                            id:'sendUsimRegUrl'
                            ,cache:false
                            ,url:'/appform/sendUsimRegUrl.do'
                            ,data: varData
                            ,dataType:"json"
                            ,async: false
                    }
                    ,function(jsonObj){
                        if (AJAX_RESULT_CODE.SUCCESS !=  jsonObj.RESULT_CODE) {
                            var strHtml = "시스템에 문제가 발생하였습니다.";
                            if (jsonObj.RESULT_MSG != undefined) {
                                strHtml = jsonObj.RESULT_MSG;
                            }
                            MCP.alert(strHtml);
                            return false;
                        }
                    });
                }
                var actionURL = "/appForm/appJehuFormComplete.do";
                ajaxCommon.createForm({
                    method:"post"
                    ,action:actionURL
                });

                ajaxCommon.attachHiddenElement("requestKey",pageObj.requestKey);
                ajaxCommon.formSubmit();
            } else {
                KTM.LoadingSpinner.hide(true);
                $('._btnNext').show();
                var strHtml = "시스템에 문제가 발생하였습니다.";
                if (jsonObj.RESULT_MSG != undefined) {
                    strHtml = jsonObj.RESULT_MSG;
                }
                MCP.alert(strHtml);
            }
        })
}

// 중복 신청 체크
function chkReqDup() {
    // 온라인, 모바일만 중복 체크
    if ($("#onOffType").val() != '3' && $("#onOffType").val() != '0') {
        return false;
    }
    var isDup = false;
    var varData = ajaxCommon.getSerializedData({
        operType: $("#operType").val()
        ,reqUsimSn: $("#reqUsimSn").val()
        ,moveMobileFn: $("#moveMobileFn").val()
        ,moveMobileMn: $("#moveMobileMn").val()
        ,moveMobileRn: $("#moveMobileRn").val()
    });
    ajaxCommon.getItemNoLoading({
            id: 'dupReqAjax'
            ,cache: false
            ,async: false
            ,url: '/appform/checkDupReqAjax.do'
            ,data: varData
            ,dataType: "json"
        }
        ,function(jsonObj){
            if (!(AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE)) {
                isDup = true;
                MCP.alert(jsonObj.RESULT_MSG, function(){
                    if (jsonObj.RESULT_CODE == '9999') {
                        $('#reqUsimSn').focus();
                    } else if (jsonObj.RESULT_CODE == '9998') {
                        $('#moveMobileFn').focus();
                    }
                });
            }
        });
    return isDup;
}

var getNationList = function() {
    if (pageObj.nationList.length) {
        return;
    }

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
        }
    });
}

var setNationSelect = function() {
    if ($("#cstmrForeignerNation option").length) {
        return;
    }

    pageObj.nationList.forEach(function(nation) {
        $('#cstmrForeignerNation').append('<option value="' + nation.dtlCd + '">' + nation.dtlCdNm + '</option>');
    });
}

var containsGoldNumber = function() {
    var containsGoldNumber = false;

    var varData = ajaxCommon.getSerializedData({
        reqWantNumber: $("#reqWantNumber").val()
        , reqWantNumber2: $("#reqWantNumber2").val()
        , reqWantNumber3: $("#reqWantNumber3").val()
    });

    ajaxCommon.getItemNoLoading({
        id:'containsGoldNumbers'
        ,cache:false
        ,async:false
        ,url:'/appform/containsGoldNumbersAjax.do'
        ,data: varData
        ,dataType:"json"
    } ,function(jsonObj){
        if (jsonObj.RESULT_CODE !== AJAX_RESULT_CODE.SUCCESS) {
            MCP.alert(jsonObj.RESULT_MSG);
            containsGoldNumber = true;
        }
    });

    return containsGoldNumber;
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

var handleAccordionClick = function(el) {
    var $btn = $(el);
    var $panel = $btn.closest('.c-accordion__item').children('.c-accordion__panel');
    var isActive = $btn.hasClass('is-active');

    $btn.toggleClass('is-active', !isActive);
    $btn.attr('aria-expanded', !isActive);

    $panel.stop()[isActive ? 'slideUp' : 'slideDown']();
    $panel.attr('aria-hidden', isActive);
}

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
    return $(agreeWrap).closest('.c-accordion__sub-check').find('.agreeCheck');
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

    isValidateNonEmptyStep1();
}


// 고객 안면인증 대상 여부 조회(FT0)
function fnReqFathTgtYn() {

    $("#isFathTxnRetv").val("");
    $('#btnFathTxnRetv').prop('disabled', true);
    $('#btnFathSkip').prop('disabled', true);

    var varData = ajaxCommon.getSerializedData({
        onlineOfflnDivCd : "ONLINE",
        orgId : $("#cntpntShopId").val(),
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
        orgId : $("#cntpntShopId").val()
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