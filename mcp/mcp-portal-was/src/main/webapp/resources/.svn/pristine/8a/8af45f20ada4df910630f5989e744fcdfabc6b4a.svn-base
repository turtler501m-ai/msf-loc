;
VALIDATE_COMPARE_MSG.selfIssuExprDt = "발급일자의 날짜 형식이 맞지 않습니다. 발급일자를 확인해 주세요.";




var pageObj = {
    niceType:""
    , initAddition:0
    , applDataForm:{}
    , niceHistSeq:0
    , authObj:{}
    , authPassObj:{}
    , insrNiceHistSeq:0
    , niceHistInsrProdSeq:0
    , startTime:0
    , eventId:""
    , step:1
    , clauseJehuRateCount:0
    , clauseFinanceRateCount:0
    , additionBetaList:[]
    , additionKeyList:[]
    , additionTempKeyList:[]
    , reqAddition:[]
    , reqAdditionPrice:0
    , insrProdList:[]
    , insrProdCd:""      //안심보험 선택 코드값
    , insrProdObj:null   //{}
    , addrFlag:""
    , requestKey:0       //서식지 등록후 key값
    , resNo:0            //서식지 등록후 예약번호
    , niceResSmsObj:{}   //셀프개통 신규 인증한 SMS인증 정보
    , niceResLogObj:{}   //로그을 저장 하기 위한 인증
    , prdtNm:""          //상품명
    , rateNm:""          //요금제명
    , modelSalePolicyCode : "" //init 정책정보
    , modelId:""
    , prmtIdList:[]     // 사은품 프로모션 코드
    , prdtIdList:[]    // 사은품 제품ID
    , giftList:[]
    , operType:""
    , priceLimitObj:null
    , usimOrgnId:""
    // 자급제 보상 서비스 정보
    ,niceHistRwdProdSeq: 0
    ,rwdProdList: []
    ,rwdProdCd: ""
    ,rwdProdObj: null
    ,moveCompanyList: []
    , additionList:[]
    , additionCatchCallKey:49  //캐치콜 플러스 키값
    , additionCatchCall:null
    , nationList: []
    , checkCaution:"N"
}

var stepText = ["본인확인·약관동의","유심정보·신분증 확인","가입신청 정보","부가서비스 정보","납부정보·가입정보 확인"]

var fs9Succ = false;

$(document).ready(function(){

    pageObj.modelSalePolicyCode = $("modelSalePolicyCode").val();
    pageObj.modelId = $("modelId").val();


    KTM.datePicker('.flatpickr');

    $("#dlvryMemo ,#dlvryMemoSub").keyup(function(){
        $(this).val($(this).val().replace(/[?&=]/gi,''));
        dlvryMemoChk($(this));
    });

    var dlvryMemoChk = function ($thatObj){
        var content = $thatObj.val();
        if (content == null) {
            return;
        } else {
            var $targetObj = $thatObj.parent().find("span").eq(1);
            $targetObj.text(content.length + "/50");

            if (content.length > 50) {
                $thatObj.val(content.substring(0, 50));
                $targetObj.text("50/50");
            }
        }
    } ;

    dlvryMemoChk($("#dlvryMemo"));


    // 본인인증 전 추가 유효성 검사 > niceAuth.js 참고
    simpleAuthvalidation= function(){

        var operType = $("#operType").val();
        var cstmrType = $("input:radio[name=cstmrType]:checked").val();

        var rtn = true;
        if($("#isCheckmember").val() != "1" && (operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE) ) {  // 기변인 경우

            var customerSsn = ""
            if (cstmrType == CSTMR_TYPE.NA) { // 내국인
                customerSsn = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val());
            } else if (cstmrType == CSTMR_TYPE.NM) { // 청소년
                customerSsn = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val());
            } else if (cstmrType == CSTMR_TYPE.FN) { // 외국인
                customerSsn = $.trim($("#cstmrForeignerRrn01").val()) + $.trim($("#cstmrForeignerRrn02").val());
            }

            var varData = ajaxCommon.getSerializedData({
                customerLinkName:$.trim($("#cstmrName").val())
                ,subscriberNo:$.trim($("#changeCstmrMobileFn").val())+$.trim($("#changeCstmrMobileMn").val())+$.trim($("#changeCstmrMobileRn").val())
                ,customerSsn : customerSsn
            });
            ajaxCommon.getItemNoLoading({
                    id:'getAuth'
                    ,cache:false
                    ,url:'/appform/selRMemberAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,async:false
                }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    var cstmrType = jsonObj.CSTMR_TYPE;
                    // 입력값 백업
                    var currentType = $("input:radio[name=cstmrType]:checked").val();
                    var foreignerVal1 = $('#cstmrForeignerRrn01').val();
                    var foreignerVal2 = $('#cstmrForeignerRrn02').val();
                    var nativeVal1 = $('#cstmrNativeRrn01').val();
                    var nativeVal2 = $('#cstmrNativeRrn02').val();

                    $("input:radio[name=cstmrType]").each(function() {
                        if ($(this).val() !== cstmrType) {
                            $(this).prop("disabled", true);
                        }
                    });
                    $("input:radio[name=cstmrType][value="+cstmrType+"]").trigger("click");

                    // 값 복원
                    if (cstmrType == CSTMR_TYPE.FN) { // 외국인으로 바뀌었을 때
                        $('#cstmrForeignerRrn01').val(currentType == CSTMR_TYPE.FN ? foreignerVal1 : nativeVal1);
                        $('#cstmrForeignerRrn02').val(currentType == CSTMR_TYPE.FN ? foreignerVal2 : nativeVal2);
                        $("#cstmrNativeRrn01, #cstmrNativeRrn02").val('');
                    } else { // 내국인으로 바뀌었을 때
                        $('#cstmrNativeRrn01').val(currentType == CSTMR_TYPE.FN ? foreignerVal1 : nativeVal1);
                        $('#cstmrNativeRrn02').val(currentType == CSTMR_TYPE.FN ? foreignerVal2 : nativeVal2);
                        $("#cstmrForeignerRrn01, #cstmrForeignerRrn02").val('');
                    }

                    $('#cstmrForeignerRrn01').prop('readonly', true);
                    $('#cstmrForeignerRrn02').prop('readonly', true);
                    $('#cstmrNativeRrn01').prop('readonly', true);
                    $('#cstmrNativeRrn02').prop('readonly', true);

                    $("#isCheckmember").val("1");
                    $('#cstmrName').val().toUpperCase();

                    $('#cstmrMobileFn').val($.trim($("#changeCstmrMobileFn").val()));
                    $('#cstmrMobileMn').val($.trim($("#changeCstmrMobileMn").val()));
                    $('#cstmrMobileRn').val($.trim($("#changeCstmrMobileRn").val()));
                    $('#cstmrName').prop('readonly', true);


                    $("#changeCstmrMobile").addClass("is-readonly");
                    $('#changeCstmrMobileFn').prop('readonly', true);
                    $('#changeCstmrMobileMn').prop('readonly', true);
                    $('#changeCstmrMobileRn').prop('readonly', true);

                    $('#cstmrMobileFn').prop('readonly', true);
                    $('#cstmrMobileMn').prop('readonly', true);
                    $('#cstmrMobileRn').prop('readonly', true);
                    $('#inputPhoneNum').val($.trim($("#changeCstmrMobileFn").val()) + "-" + $.trim($("#changeCstmrMobileMn").val()) + "-" + $.trim($("#changeCstmrMobileRn").val()));

                    $("#cstmrName").parent().addClass('has-value');
                    $("#cstmrNativeRrn01").parent().addClass('has-value');
                    $("#cstmrForeignerRrn01").parent().addClass('has-value');
                } else if ("0010" == jsonObj.RESULT_CODE) { // 인가된 사용자 검증
                    rtn = false;
                    MCP.alert('<p class="u-mt--12"><strong class="u-fw--bold">회원정보와 본인인증 정보가<br>일치하지 않습니다.</strong></p><p class="u-mt--12">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</p>');
                } else if ("0004" == jsonObj.RESULT_CODE) {
                    rtn = false;
                    MCP.alert("약정기간이 6개월 이상 남아있어 온라인으로 기기변경 신청이 불가합니다.<br>기기변경을 원하실 경우 고객센터(1899-5000)로 신청을 부탁드립니다.");
                } else {
                    rtn = false;
                    MCP.alert(jsonObj.RESULT_DESC);
                }
            });
        }
        if(!rtn) {
            return false;
        }

        if (!checkPriceLimitForAge()) { // 유효성검사 최종 실패
            return false;
        }
        return true;
    }

    $("#btnAuthInsr").click(function(){
        if ($("#isAuthInsr").val() == "1") {
            MCP.alert("본인 인증을 완료 하였습니다. ");
            return false;
        }

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};
        validator.config['isAuth'] = 'isNonEmpty';
        validator.config['clauseInsrProdFlag'] = 'isNonEmpty';
        validator.config['clauseInsrProdFlag02'] = 'isNonEmpty';
        validator.config['clauseInsrProdFlag03'] = 'isNonEmpty';


        if (validator.validate()) {
            ajaxCommon.getItemNoLoading({
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

            openPage('outLink','/nice/popNiceAuth.do?sAuthType=M','')
            pageObj.niceType = NICE_TYPE.INSR_PROD ;
            return ;

        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg() ,function (){
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
        }
    });

    $("#btnSmsAuth").click(function(){
        if ($("#isSmsAuth").val() == "1") {
            MCP.alert("휴대폰 인증을 완료 하였습니다. ");
            return false;
        }
        ajaxCommon.getItemNoLoading({
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

        //sAuthType M: 핸드폰, C: 신용카드, X: 공인인증서  isTeen isJimin
        openPage('outLink','/nice/popNiceAuth.do?sAuthType=M','')
        pageObj.niceType = NICE_TYPE.SMS_AUTH ;
        return ;
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
            $(".c-form__text").remove();
            $(".has-error").removeClass("has-error");
            $("._isDefaultText").hide();
            $("._isDefault").show();
            $("._isDefaultMt").addClass("u-mt--0");
            $("#cstmrBillSendCode03").prop("disabled", true);

            $("#inpNameCertiTitle").html("가입자 정보(미성년자)");
            $("#radCertiTitle").html("본인인증방법 선택(법정대리인)");
            $("#radIdCardHead").html("신분증 확인(법정대리인)");

        } else if (thisVal == CSTMR_TYPE.FN) {
            $("._isTeen").hide();
            $("._isForeigner").show();
            $(".c-form__text").remove();
            $(".has-error").removeClass("has-error");
            $("._isCommon").show();
            $("._isDefaultText").hide();
            $("._isDefault").hide();
            $("._isDefaultMt").removeClass("u-mt--0");
            $("#cstmrBillSendCode03").prop("disabled", false);

            $("#inpNameCertiTitle").html("가입자 정보");
            $("#radCertiTitle").html("본인인증 방법 선택");
            $("#radIdCardHead").html("신분증 확인");

            $('input[name="selfCertType"]').prop('disabled', true);
            $('#selfCertType6').prop('disabled', false);
            $('#selfCertType6').prop('checked', true);

        } else {
            $("._isTeen").hide();
            $("._isCommon").show();
            $("._isForeigner").hide();
            $(".c-form__text").remove();
            $(".has-error").removeClass("has-error");
            $("._isDefaultText").show();
            $("._isDefault").show();
            $("._isDefaultMt").addClass("u-mt--0");
            $("#cstmrBillSendCode03").prop("disabled", false);

            $("#inpNameCertiTitle").html("가입자 정보");
            $("#radCertiTitle").html("본인인증 방법 선택");
            $("#radIdCardHead").html("신분증 확인");

        }
    });

    $("input:radio[name=operTypeRadio]").click(function(){
        var thisVal = $(this).val();
        pageObj.operType = $("#operType").val(); //이전 정보 저장

        $("#operType").val(thisVal);
        var operType = thisVal;

        if (operType == OPER_TYPE.MOVE_NUM) {
            $("#pOperTypeDesc").html("타 통신사에서 사용하던 번호 그대로 kt M모바일로 가입하시는 경우 선택해 주세요.");
        } else {
            $("#pOperTypeDesc").html("새로운 번호로 개통합니다.");
        }


        getListSale();
    });

    //약정기간
    $("#enggMnthCnt").change(function(){
        getListSale();
    });

    //할부기간
    $("#modelMonthly").change(function(){
        getListSale();
    });

    //단말
    $("#modelId").change(function(){
        chgBottomTitle();
    });

    //데이터타입
    $("input[name='dataTypeRadio']").change(function(){
        chgBottomTitle();
        getListSale();
    });

    var chgBottomTitle = function() {
        var prdtNmTemp = "";
        var prdtNm = $('#prdtNm').val();
        if (prdtNm  != "") {
            prdtNmTemp = " / " +prdtNm;
        }
        if ($("#modelId option:selected").length > 0) {
            prdtNmTemp += " / " +$("#modelId option:selected").attr("atribValNm1");
        }
        var operTypeNm = $("input:radio[name=operTypeRadio]:checked").attr("txtName");
        var dataType = $("input[name='dataTypeRadio']:checked").val() ? $("input[name='dataTypeRadio']:checked").val() : $('#prdtSctnCd').val();
        $('#bottomTitle').text(dataType + prdtNmTemp);
        $('#bottomTitle2').text(pageObj.rateNm);
        $('#pRateNm').text(pageObj.rateNm);
        $('#ddOperTypeNm').text(operTypeNm);
    }

    //0, 100, N --> 0세~100세 신청불가
    //0, 100, Y --> 0세~100세 신청가능
    var checkPriceLimitForAge = function(agePara) {
        var cstmrType = $("input:radio[name=cstmrType]:checked").val();
        var ageInt = 0;

        if (agePara == undefined ) {
            validator.config={};
            if (cstmrType == CSTMR_TYPE.FN) {
                validator.config['cstmrForeignerRrn01&cstmrForeignerRrn02'] = 'isFngno';
            } else if (cstmrType == CSTMR_TYPE.NM) {
                validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isTeen';
            } else {
                validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
            }

            if (validator.validate(true)) {
                if (cstmrType == CSTMR_TYPE.FN) {
                    ageInt = getAge($("#cstmrForeignerRrn01").val()+$("#cstmrForeignerRrn02").val());
                } else {
                    ageInt = getAge($("#cstmrNativeRrn01").val()+$("#cstmrNativeRrn02").val());
                }
            } else {
                if (cstmrType == CSTMR_TYPE.NM) {
                    ageInt = 12;
                } else {
                    return true;  //체크 필요 없음
                }

            }
        } else {
            ageInt = agePara;
        }

        console.log("price"+pageObj.priceLimitObj);
        if (pageObj.priceLimitObj != null && pageObj.priceLimitObj.dtlCd != null && pageObj.priceLimitObj.dtlCd != "") {
            var priceLimitObj = pageObj.priceLimitObj ;
            var startAge= parseInt(priceLimitObj.expnsnStrVal1,10) ;
            var endAge= parseInt(priceLimitObj.expnsnStrVal2,10) ;
            var division =priceLimitObj.expnsnStrVal3;

            if ("N" == division ) {
                if (startAge <= ageInt && endAge  >=  ageInt) {
                    MCP.alert(pageObj.priceLimitObj.dtlCdDesc);
                    return false;  //불가능
                } else {
                    return true;  //가능
                }
            } else {
                if (startAge <= ageInt && endAge  >=  ageInt) {
                    return true;  //가능
                } else {
                    MCP.alert(pageObj.priceLimitObj.dtlCdDesc);
                    return false;  //불가능
                }
            }
        } else {
            //체크 필요 없음
            return true;  //가능
        }
    }


    $("#selSocCode").change(function(){
        var thisVal = $(this).val();

        var varData = ajaxCommon.getSerializedData({
            dtlCd:thisVal
            ,cdGroupId:CD_GROUP_ID_OBJ.RateLimit
        });

        ajaxCommon.getItemNoLoading({
                id:'getPriceLimitInfo'
                ,cache:false
                ,url:'/getCodeNmAjax.do'
                ,data: varData
                ,dataType:"json"
                ,async:false
                ,errorCall : function () {
                    //NOTHING
                }
            }
            ,function(jsonObj){
                pageObj.priceLimitObj = jsonObj ;
            });

        if(!checkPriceLimitForAge()) {

            //요금제  신청 불가
            //이전 요금제 이동
            if (thisVal != $("#socCode").val()) {
                $("#selSocCode").val($("#socCode").val());
                $("#selSocCode").trigger("change") ;

                // error 표출되어있는 것 모두 제거
                $(".c-form__text").remove();
                $(".has-error").removeClass("has-error");
            } else {
                $("#selSocCode option").eq(0).prop("selected",true);
            }

            return false;
        }

        var agrmTrm = $("#enggMnthCnt").val();
        var modelMonthlyVal = $("#modelMonthly").val();
        if (agrmTrm == "") {
            agrmTrm = "0";
        }

        if (modelMonthlyVal == "") {
            modelMonthlyVal ="0" ;
        }

        $("#socCode").val(thisVal);
        getJoinUsimPriceInfo();


        var itemObj = $(this).find("option:selected").data();

        $("#payMnthChargeAmtTxt").html(numberWithCommas(itemObj.payMnthChargeAmt));
        $("#baseAmt").html(numberWithCommas(itemObj.baseVatAmt)+" 원");
        $("#dcAmt").html(numberMinusWithCommas(itemObj.dcVatAmt)+" 원");
        $("#addDcAmt").html(numberMinusWithCommas(itemObj.addDcVatAmt)+" 원");
        $("#promotionDcAmtTxt").html(numberMinusWithCommas(itemObj.promotionDcAmt)+" 원");
        $("#hndstAmtTxt").html(numberWithCommas(itemObj.hndstAmt)+" 원");
        $("#subsdAmtTxt").html(numberMinusWithCommas(itemObj.subsdAmt)+" 원");
        $("#agncySubsdAmtTxt").html(numberMinusWithCommas(itemObj.agncySubsdAmt)+" 원");
        $("#instAmtTxt").html(numberWithCommas(itemObj.instAmt)+" 원");
        $("#addDcAmtTxt").html(numberMinusWithCommas(itemObj.addDcAmt)+" 원");
        $("#payMnthAmtTxt").html(numberWithCommas(itemObj.payMnthAmt));
        $("#totalInstCmsnTxt").html(numberMinusWithCommas(itemObj.totalInstCmsnTxt)+" 원");

        if ("0" == modelMonthlyVal) {
            $("#paySumMnthTxt").html(numberWithCommas(itemObj.payMnthChargeAmt));
            $("#paySumMnthTxt2").html(numberWithCommas(itemObj.payMnthChargeAmt));
        } else {
            $("#paySumMnthTxt").html(numberWithCommas(itemObj.payMnthAmt+itemObj.payMnthChargeAmt));
            $("#paySumMnthTxt2").html(numberWithCommas(itemObj.payMnthAmt+itemObj.payMnthChargeAmt));
        }


        pageObj.rateNm = itemObj.rateNm;

        chgBottomTitle();
        // 제휴처 요금제 여부 확인
        ajaxCommon.getItemNoLoading({
                id:'getJehuInfoAjax'
                ,cache:false
                ,url:'/getJehuInfoAjax.do'
                ,data: ajaxCommon.getSerializedData({rateCd:thisVal, pCntpntShopId:$("#cntpntShopId").val()})
                ,dataType:"json"
                ,async:false
                ,errorCall : function () {
                    //NOTHING
                }
        },function(jsonObj){
                if(jsonObj.jehuProdType != null){
                    $("#jehuProdType").val(jsonObj.jehuProdType);
                    $("#jehuProdName").val(jsonObj.jehuProdName);
                    $("#jehuPartnerType").val(jsonObj.jehuPartnerType);
                    $("#jehuPartnerName").val(jsonObj.jehuPartnerName);
                    getJehuInfo();
                }
        });
        // 약관 동의 항목및 전체선택 관련..
        if($("#jehuProdType").val() != "" ) {
            $("#btnStplAllCheck").prop("checked", false)
        } else {
            // 제휴처 요금제가 아닌 일반 요금제 선택 시, 제휴처 동의약관을 제외한 나머지 동의 약관을 확인하여 전체체크 확인을 위해
            // 제휴처 동의약관을 임시로 체크 처리 (아래 주석에서 다시 해제)
            $("#clauseJehuFlag").prop("checked", "checked");
            var isAllCheck= true;
            $("._agree").each(function(){
                if (!$(this).is(':checked')) {
                    isAllCheck= false;
                }
            });
            if (isAllCheck) {
                $("#btnStplAllCheck").prop("checked", "checked");
            } else {
                $("#btnStplAllCheck").prop("checked", "");
            }
        }
        $("#clauseJehuFlag").prop("checked", false); // 요금제와 상관없이 제휴처 요금제 약관이 변경되므로 초기화
        isValidateNonEmptyStep1();
    });

    $("#btnStplAllCheck").click(function(){
        $("._agree").prop("checked", $(this).is(':checked'));
        isValidateNonEmptyStep1();
        applyCheckedWrapList();
    });


    $("#clauseJehuButton").click(function(){
        fnSetEventId('clauseJehuFlag');

        var data = {cdGroupId1:'FORMREQUIRED',
                    cdGroupId2:'clauseJehuFlag',
                    docType:'02',
                    expnsnStrVal:$("#jehuProdType").val(),
                    name:$("#jehuProdName").val()
                    };
        openPage('termsPop','/termsPop.do',data,0);
    });
    $("#clausePartnerOfferButton").click(function(){
        fnSetEventId('clausePartnerOfferFlag');

        var data = {cdGroupId1:'FORMREQUIRED',
                    cdGroupId2:'clausePartnerOfferFlag',
                    docType:'03',
                    expnsnStrVal:$("#jehuPartnerType").val(),
                    name:$("#jehuPartnerName").val()
                    };
        openPage('termsPop','/termsPop.do',data,0);
    });

    $("#btnInsrAllCheck").click(function(){
        $("._agreeInsr").prop("checked", $(this).is(':checked'));
        isValidateNonEmptyStep4();
    });

    $("._agreeInsr").click(function(){
        checkAllTermsStep4();  // 개별 약관 동의 시 전체약관 선택 버튼 처리
    });

    $("#minorAgentRelation").change(function(){
        isValidateNonEmptyStep1();
    });

    $("input[name='onOffLineUsim']").change(function(event, paramObj) {
        if ($("#isUsimNumberCheck").val() == "1") {
            MCP.alert("유심번호 유효성 체크 하였습니다. ");
            $(this).prop('checked', false);
            $("#onOffLineUsim01").prop('checked', true);
            return false ;
        }
        var isInit = false;
        if (paramObj != undefined ) {
            isInit = paramObj.isInit;
        }
        var reqBuyType = $("#reqBuyType").val() ;
        var arr =[];

        if ($(this).val() == "offLine") {
            //유심 보유
            $("#reqUsimSn").prop("disabled", false);
            $("._offLineUsim").show();
            $("._onLineUsim").hide();

            if (REQ_BUY_TYPE.USIM == reqBuyType ) {
                $("#divDlvry").hide();
            } else {
                $("#divDlvry").show();
            }
            $("#usimPriceTxt").html("0 원");
            arr.push("<li class='c-text-list__item u-co-gray'>유심 카드를 미리 구매하신 고객님께서는 유심번호 19자리를 입력해 주세요</li>\n");
            arr.push("<li class='c-text-list__item u-co-gray'>유심 보유를 선택하실 경우 유심비용이 미청구 됩니다.</li>\n");
        } else{
            //유심미보유
            $("#reqUsimSn").prop("disabled", true).val("");
            $("._offLineUsim").hide();
            $("._onLineUsim").show();
            $("#divDlvry").show();
            arr.push("<li class='c-text-list__item u-co-gray'>유심비용("+numberWithCommas($("#usimPrice").val())+"원)은 첫 달에만 부과됩니다.</li>\n");
            arr.push("<li class='c-text-list__item u-co-gray'>유심 보유를 선택하실 경우 유심비용이 미 청구 됩니다.</li>\n");
            showJoinUsimPrice();
        }


        $("#ulOnOffLineUsimDesc").empty();
        $("#ulOnOffLineUsimDesc").append(arr.join(''));
        isValidateNonEmptyStep2();
    });

    $("input:radio[name=moveCompanyGroup1]").click(function(){
        var thisVal = $(this).val();
        $("#moveCompany > option").remove();
        $("#moveCompanyGroup2 option").eq(0).prop("selected",true);

        if ("-1" == thisVal) {
            $('#moveCompany').append('<option value="">알뜰폰 사업자</option>');
            $('#divMoveCompanyGroup2').show();
        } else {
            var mpCode = $(this).attr("mpCode");
            var companyNm = $(this).attr("companyNm");
            var companyTel = $(this).attr("companyTel");

            $('#moveCompany').append('<option value="'+thisVal+'" mpCode="'+mpCode+'" companyNm="'+companyNm+'" companyTel="'+companyTel+'" >'+companyNm+'</option>').addClass("has-value");
            $('#divMoveCompanyGroup2').hide();
        }
        isValidateNonEmptyStep3();
    });

    $("#moveCompanyGroup2").change(function(){
        var thisVal = $(this).val();
        //console.log("moveCompanyGroup2===>" +thisVal );
        $("#moveCompany > option").remove();
        $('#moveCompany').append('<option value="">알뜰폰 사업자</option>');

        if (pageObj.moveCompanyList.length < 1) {
            getmoveCompanyList();
        }

        pageObj.moveCompanyList.forEach(function(moveCompanyObj) {
            if (thisVal == moveCompanyObj.dtlCdDesc) {
                $('#moveCompany').append('<option value="'+moveCompanyObj.dtlCd+'" mpCode="'+moveCompanyObj.expnsnStrVal1+'" companyNm="'+moveCompanyObj.dtlCdNm+'" companyTel="'+moveCompanyObj.expnsnStrVal2+'" >'+moveCompanyObj.dtlCdNm+'</option>');
            }
        });


        isValidateNonEmptyStep3();
    });


    var getmoveCompanyList = function() {
        var varData = ajaxCommon.getSerializedData({
            grpCd:"NSC"
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
                if (objList!=null) {
                    pageObj.moveCompanyList= objList ;
                }
            });
    };






    $('#btnAutAccount').click(function(){
        validator.config={};
        validator.config['isPassAuth'] = 'isNonEmpty';
        validator.config['reqBankAut'] = 'isNonEmpty';
        validator.config['reqAccountAut'] = 'isNonEmpty';

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        var cstmrType = $("input:radio[name=cstmrType]:checked").val();
        if (cstmrType == CSTMR_TYPE.NM) { //청소년
            cstmrName = $.trim($("#minorAgentName").val());
        } else {
            cstmrName = $.trim($("#cstmrName").val());
        }

        if (validator.validate()) {
            var varData = ajaxCommon.getSerializedData({
                service:"2"
                ,svcGbn:"2"
                ,bankCode:$("#reqBankAut").val()
                ,accountNo:$.trim($("#reqAccountAut").val())
                ,name:cstmrName
            });

            ajaxCommon.getItemNoLoading({
                    id:'niceAccountOtpNameAjax'
                    ,cache:false
                    ,url:'/nice/niceAccountOtpNameAjax.do'
                    ,data: varData
                    ,dataType:"json"
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        /* 이부분이 HIDDEN으로 존재해야 합니다.  */
                        $("#requestNo").val(jsonObj.request_no);
                        $("#resUniqId").val(jsonObj.res_uniq_id);

                        KTM.Dialog.closeAll();

                        setTimeout(function(){
                            var el = document.querySelector('#bankAutDialog2');
                            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                            modal.open();
                        }, 500);


                    } else {
                        MCP.alert("입력하신 계좌 정보로 인증이 불가합니다. 입력 정보를 확인 후 다시 시도해 주시기 바랍니다.");
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

    $('#btnUsimNumberCheck').click(function(){
        if ($("#isUsimNumberCheck").val() == "1") {
            MCP.alert("유심번호 유효성 체크 하였습니다. ");
            return ;
        }

        validator.config={};
        validator.config['reqUsimSn'] = 'isNumFix19';

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        if (validator.validate()) {

            var ncType= "";
            var cstmrType = $("input:radio[name=cstmrType]:checked").val();
            if(cstmrType == CSTMR_TYPE.NM) ncType= "0";

            var varData = ajaxCommon.getSerializedData({
                iccId:$.trim($("#reqUsimSn").val()),
                ncType: ncType
            });

            ajaxCommon.getItemNoLoading({
                    id:'moscIntmMgmtAjax'
                    ,cache:false
                    ,url:'/msp/moscIntmMgmtAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,errorCall : function () {
                        MCP.alert("유심번호 유효성 체크가 불가능합니다. <br/> 잠시 후 다시 시도하시기 바랍니다. ")
                    }
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        var usimOrgnId = jsonObj.USIM_ORGN_ID ;

                        if (usimOrgnId == undefined || usimOrgnId == "" ) {
                            usimOrgnId = $("#cntpntShopId").val();
                        }
                        pageObj.usimOrgnId = usimOrgnId ;
                        $("#isUsimNumberCheck").val("1");

                        MCP.alert("입력하신 유심번호 사용 가능합니다. ",function (){
                            $("#reqUsimSn").prop("disabled", true);
                            $('#btnUsimNumberCheck').addClass('is-complete').html("유심번호 유효성 체크 완료");
                            $("#reqUsimSn").parent().addClass('has-safe').after("<p class='c-form__text'>유심번호 유효성 검증에 성공하였습니다.</p>");
                            isValidateNonEmptyStep2();
                        });


                    } else {
                        var strMsg = "입력하신 유심번호 사용이 불가능 합니다. <br>확인 후 다시 시도해 주시기 바랍니다.";
                        if (jsonObj.RESULT_MSG != null) {
                            strMsg = jsonObj.RESULT_MSG;
                        }
                        MCP.alert(strMsg,function (){
                            $selectObj = $("#reqUsimSn");
                            viewErrorMgs($selectObj, strMsg);
                        });
                    }
                });

        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg() ,function (){
                //console.log("errId==>" + errId);
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
        }

    });


    $('#btnCheckAccountOtpConfirm').click(function(){
        validator.config={};
        validator.config['isPassAuth'] = 'isNonEmpty';
        validator.config['reqBankAut'] = 'isNonEmpty';
        validator.config['reqAccountAut'] = 'isNonEmpty';
        validator.config['requestNo'] = 'isNonEmpty';
        validator.config['resUniqId'] = 'isNonEmpty';
        validator.config['textOpt'] = 'isNumFix6';

        var cstmrType = $("input:radio[name=cstmrType]:checked").val();
        if (cstmrType == CSTMR_TYPE.NM) { //청소년
            cstmrName = $.trim($("#minorAgentName").val());
        } else {
            cstmrName = $.trim($("#cstmrName").val());
        }

        if (validator.validate()) {
            var varData = ajaxCommon.getSerializedData({
                bankCode:$("#reqBankAut").val()
                ,accountNo:$.trim($("#reqAccountAut").val())
                ,name:cstmrName
                ,requestNo:$.trim($("#requestNo").val())
                ,resUniqId:$.trim($("#resUniqId").val())
                ,otp:$.trim($("#textOpt").val())
            });

            ajaxCommon.getItemNoLoading({
                    id:'niceAccountOtpNameAjax'
                    ,cache:false
                    ,url:'/nice/niceAccountOtpConfirmAjax.do'
                    ,data: varData
                    ,dataType:"json"
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        pageObj.niceResLogObj.authType = "A"; //PASS인증으로 저장
                        insertNiceLog();
                        $("#isAuth").val("1");
                        $("#reqBank").val($("#reqBankAut").val());
                        $("#reqAccountNumber").val($("#reqAccountAut").val());
                        $('#reqBank').prop('disabled', 'true');
                        $('#reqAccountNumber').prop('readonly', 'readonly');
                        $('#isCheckAccount').val("1");
                        $("#btnCheckAccount").addClass('is-complete').html("계좌번호 체크 완료");
                        $('#cstmrName').prop('readonly', 'readonly');
                        $('#cstmrNativeRrn01').prop('readonly', 'readonly');
                        $('#cstmrNativeRrn02').prop('readonly', 'readonly');
                        $('#minorAgentRrn01').prop('readonly', 'readonly');
                        $('#minorAgentRrn02').prop('readonly', 'readonly');
                        $('#cstmrForeignerRrn01').prop('readonly', 'readonly');
                        $('#cstmrForeignerRrn02').prop('readonly', 'readonly');

                        KTM.Dialog.closeAll();

                        setTimeout(function(){
                            var el = document.querySelector('#bankAutDialog3');
                            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                            modal.open();
                        }, 500);

                        $('#btnNiceAuth4').addClass('is-complete').html("2단계: 계좌 인증 완료");
                        isValidateNonEmptyStep1();

                    } else {
                        MCP.alert("인증번호가 일치하지 않습니다.");
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

    $("input:radio[name=selfCertType]").click(function(){
        var thisVal= $(this).val();
        $("._selfIssuNumF").hide();
        $("._driverSelfIssuNumF").hide();
        $("._cstmrForeignerNation").hide();

        switch (thisVal) {
            case '01':
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("#divRadIdCardDesc").html("<p class='c-bullet c-bullet--dot u-co-gray'>주민등록증의 발급일자를 정확히 입력해주세요.</p>");
                $("#imgRadIdCard").attr("src","/resources/images/portal/content/img_jumin_card.png");
                break;
            case '03':
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("#divRadIdCardDesc").html("<p class='c-bullet c-bullet--dot u-co-gray'>장애인등록증의 발급일자를 정확히 입력해주세요.</p>");
                $("#imgRadIdCard").attr("src","/resources/images/portal/content/img_welfare_card.png");
                break;
            case '06':
                getNationList();
                setNationSelect();
                $("._cstmrForeignerNation").show();
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("#divRadIdCardDesc").html("<p class='c-bullet c-bullet--dot u-co-gray'>외국인등록증의 발급일자를 정확히 입력해주세요.</p>");
                $("#imgRadIdCard").attr("src","/resources/images/portal/content/img_foreigner_card.png");
                break;
            case '04':
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("._selfIssuNumF").show();
                $("#divRadIdCardDesc").html("<p class='c-bullet c-bullet--dot u-co-gray'>국가유공자증의 발급일자를 정확히 입력해주세요.</p>");
                $("#imgRadIdCard").attr("src","/resources/images/portal/content/img_merit_card.png");
                break;
            case '02':
                $("#selfIssuExprDt").attr("title", "운전면허증의 발급 일자");
                $("label[for=selfIssuExprDt]").html("운전면허증의 발급 일자");
                $("._driverSelfIssuNumF").show();

                var arr =[];
                arr.push("<ul class='c-text-list c-bullet c-bullet--dot'>\n");
                arr.push("    <li class='c-text-list__item u-co-gray'>운전면허증의 발급일자를 정확히 입력해 주세요.</li>\n");
                arr.push("    <li class='c-text-list__item u-co-gray'>지역포함 2자리(선택)-2자리-6자리-2자리 입력해주세요.</li>\n");
                arr.push("</ul>\n");
                $("#divRadIdCardDesc").html(arr.join(''));
                $("#imgRadIdCard").attr("src","/resources/images/portal/content/img_driving_license.png");
                break;
            case '05':
                $("#selfIssuExprDt").attr("title", "만료 일자");
                $("label[for=selfIssuExprDt]").html("만료 일자");
                $("#divRadIdCardDesc").html("<p class='c-bullet c-bullet--dot u-co-gray'>여권 만료 일자를 정확히 입력해주세요.</p>");
                $("#imgRadIdCard").attr("src","/resources/images/portal/content/img_passport_card.png");
                break;
        }

        //신규가입, 번호이동만 대상 (기기변경은 M전산에서 안면인증 수행 - 접수시 안면인증 받지않음 )
        if($("#operType").val() == OPER_TYPE.MOVE_NUM || $("#operType").val() == OPER_TYPE.NEW) {
            if(!fs9Succ) { //페이지 진입 or 고객이 직접 선택하는 경우 , (FS9는 성공 시 신분증타입 선택되는 경우는 제외)
                fnReqFathTgtYn();
            }
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


    $('#btnSetAddition').click(function(){
        pageObj.additionKeyList = [];
        pageObj.reqAddition = [];
        $("#divAdditionListFree").empty();
        $("#divAdditionListPrice").empty();

        var totalRantal = 0;
        var AdditionListPriceCnt = 0;
        var AdditionListAllCnt = 0;


        $("input:checkbox[name=additionName]:checked").each(function(index){
            var rantal = $(this).attr("rantal");
            var txtName = $(this).attr("txtName");
            var chargeFlag = $(this).attr("chargeFlag");
            var additionkey  = $(this).val();
            var arr =[];
            pageObj.additionKeyList.push($(this).val());
            pageObj.reqAddition.push(txtName);
            totalRantal += parseInt(rantal,10) ;

            if ("N" == chargeFlag ) {
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
        // $("#AdditionListPriceCnt").html("<b>"+AdditionListPriceCnt+"</b>건");

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
    }) ;


    $('._btnClose').click(function(){
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


    $("input:radio[name=reqPayType]").click(function(){
        var thisval = $(this).val();
        var modelMonthlyVal = $("#modelMonthly").val();
        var reqBuyType = $("#reqBuyType").val() ;
        if (modelMonthlyVal == "") {
            modelMonthlyVal ="0" ;
        }

        if (thisval == "D" ) {
            if (REQ_BUY_TYPE.PHONE == reqBuyType && "0" ==modelMonthlyVal ) {
                MCP.alert("일시 납부 선택 시 신용카드로만 요금 납부가 가능합니다.",function (){
                    $("input:radio[name=reqPayType][value=C]").trigger("click") ;
                });
            } else {
                $("._card").hide();
                $("._bank").show();
            }
        } else if (thisval == "C") {
            $("._card").show();
            $("._bank").hide()
        }
    });

    //공통에서 해야 하는것 아닌가???
    /*$("select").change(function(){
        var thisText = $(this).find("option:selected").text();
        $(this).parent().find("label").html(thisText);
    });*/

    $("#btnCheckAccount").click(function(){
        if ($('#isCheckAccount').val() == "1") {
            MCP.alert("계좌 번호 유효성 검증을 완료하였습니다.");
            return null;
        }

        validator.config={};
        validator.config['isAuth'] = 'isNonEmpty';

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

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

            ajaxCommon.getItemNoLoading({
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
                        // $("#btnCheckAccount").prop('disabled', true);
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

    $("#btnCheckCardNo").click(function(){
        if ($('#isCheckCard').val() == "1") {
            MCP.alert("신용카드번호 유효성 검증에 성공하였습니다.");
            return null;
        }
        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};
        validator.config['reqCardNo'] = 'isNumBetterFixN14';
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

    $("._btnAddr").click(function() {
        var addrFlag = $(this).attr("addrFlag");
        pageObj.addrFlag = addrFlag;
        if (addrFlag != "3") {
            openPage('pullPopup', '/addPopup.do','','1');
        }  else {
            openPage('pullPopup', '/dlvryInfo.do','','1');
        }
    });

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

        var hideIndex = setStep-1;
        $("#step"+setStep).show();

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

        if (setStep == 2) {
            
            if($("#cstmrAddr").length > 0 && "" != $("#cstmrAddr").val()) {
                $("#cstmrAddr").parent().addClass('has-value');
            }

            if($("#dlvryAddr").length > 0 && "" != $("#dlvryAddr").val()) {
                $("#dlvryAddr").parent().addClass('has-value');
            }
        }
    };


    $('#btnBefore').click(function(){
        var onOffType = $("#onOffType").val() ;
        var operType = $("#operType").val() ;

        $('._btnNext').removeClass('is-disabled');

        if (pageObj.step == 2 ) {
            $("._btnNext").html("다음");
            setStep(1);
            return;
        } else if (pageObj.step == 3) {
            setStep(2);
            return;
        } else if (pageObj.step == 4) {
            setStep(3);
            $("._btnNext").html("다음");
            return;
        } else if (pageObj.step == 5 ) {
            step4Show();
            $("._btnNext").html("다음");

            return;
        }
    }) ;

    $('._btnNext').click(function(){

        if ($("#bs_acc_btn").hasClass("is-active") ) {
            $("#bs_acc_btn").trigger("click");
        }

        var onOffType = $("#onOffType").val() ;
        var operType = $("#operType").val() ;

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        if (pageObj.step == 1 && isValidateStep1() ) {
            $('._btnNext').addClass('is-disabled');
            setStep(2);
            isValidateNonEmptyStep2();
            return;
        } else if (pageObj.step == 2 && isValidateStep2() ) {
            $('._btnNext').addClass('is-disabled');
            setStep(3);
            isValidateNonEmptyStep3();
            return;
        } else if (pageObj.step == 3 && isValidateStep3() ) {
            if (operType == OPER_TYPE.NEW && containsGoldNumber()) {
                return false;
            }

            $('._btnNext').addClass('is-disabled');
            step4Show();
            if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE ) {
                $("._btnNext").html("주문완료");
            }
            return;
        } else if (pageObj.step == 4 && isValidateStep4() ) {
            $('._btnNext').addClass('is-disabled');

            nextStepFive();

            return;
        } else if (pageObj.step == 5 && isValidateStep5() ) {

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
        }
    }) ;

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
        fnSaveForm();
        return;
    }


    var nextStepFive = function() {
        var operType = $("#operType").val() ;

        if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE ) {
            fnSaveForm();
        } else {
            setStep(5);
            $("._btnNext").html("주문완료");
            isValidateNonEmptyStep5();
        }
        return;

    }


    $('#btnClauseSimple').click(function(){
        fnSetEventId('clauseSimpleOpen');
        var arr =[];
        arr.push("최근 대출업자, SNS 등을 통해 이동전화 개통 시 자금을 제공해 주겠다고 권유한 후, 개통된 휴대폰/유심을 대출 사기\n");
        arr.push(", 보이스 피싱 조직에 유통하는 등의 악용하는 사례가 다수 적발되고 있습니다.<br/> \n");
        arr.push("이러한 제 3자에게 본인명의의 휴대폰/유심을 개통해주거나, 개통에 필요한 신청서류를 제공하는 행위는 \n");
        arr.push("<span class='text_color_red'>전기통신사업법 제30조(타인사용의 제한) 및 97조(벌칙)의 규정에 따라 1년 이하의 징역 및 5천만원 이하의 형사처벌</span>\n");
        arr.push("을 받을 수 있으니 각별히 주의하시기 바랍니다. \n");
        $('#terms-title').html("셀프개통 안내사항");
        $('#termsModalContents').html(arr.join(''));
        var el = document.querySelector('#modalTerms');
        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
        modal.open();
    }) ;





    $("#inEqualCheck").click(function() {
        var dlvryType = "01";

        if ($("input:radio[name=dlvryType]:checked").val() != undefined) {
            dlvryType = $("input:radio[name=dlvryType]:checked").val();
        }

        if($(this).is(':checked')) {
            $("#dlvryName").val($("#cstmrName").val());
            $("#dlvryMobileFn").val($("#cstmrMobileFn").val());
            $("#dlvryMobileMn").val($("#cstmrMobileMn").val());
            $("#dlvryMobileRn").val($("#cstmrMobileRn").val());
            $("#dlvryTelFn").val($("#cstmrTelFn").val());
            $("#dlvryTelMn").val($("#cstmrTelMn").val());
            $("#dlvryTelRn").val($("#cstmrTelRn").val());
            $("#dlvryPost").val($("#cstmrPost").val());
            $("#dlvryAddr").val($("#cstmrAddr").val());
            $("#dlvryAddrDtl").val($("#cstmrAddrDtl").val());

            $("#dlvryName").parent().addClass('has-value');
            $("#dlvryMobileFn").parent().addClass('has-value');
            $("#dlvryTelFn").parent().addClass('has-value');


            isValidateNonEmptyStep2();
        }
    });






    $("._agree").click(function(){
        handleCommonAgreeClick();
    });

    $("#minorAgentTelFn,#minorAgentTelMn,#minorAgentTelRn").keyup(function(){
        isValidateNonEmptyStep1();
    });

    $("#minorAgentAgrmYn").click(function(){
        isValidateNonEmptyStep1();
    });

    $("#cstmrNativeRrn02").blur(function(){
        if(!checkPriceLimitForAge()) {
            // error 표출되어있는 것 모두 제거
            $(".c-form__text").remove();
            $(".has-error").removeClass("has-error");
            return false;
        }
    });


    $("#cstmrForeignerRrn02").blur(function(){
        //기기변경 주민번호 수집삭제
        var operType = $("#operType").val() ;
        if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE ) {
            return true;
        }

        if(!checkPriceLimitForAge()) {
            return false;
        }
    });


    
    $("#selfInqryAgrmYnFlag").click(function(){
        isValidateNonEmptyStep2();
    });

    $("#reqUsimSn ,#cstmrMobileFn,#cstmrMobileMn,#cstmrMobileRn,#cstmrMail,#selfIssuExprDt,#driverSelfIssuNum1,#driverSelfIssuNum2,#selfIssuNum,#dlvryName,#dlvryMobileFn,#dlvryMobileMn,#dlvryMobileRn,#homePw").keyup(function(){
        isValidateNonEmptyStep2();
    });


    $("#selfIssuExprDt,#cstmrForeignerNation").change(function(){
        isValidateNonEmptyStep2();
    });

    $("#moveCompany").change(function(){
        isValidateNonEmptyStep3();
    });

    $("#moveMobileFn,#moveMobileMn,#moveMobileRn,#reqWantNumber,#reqWantNumber2,#reqWantNumber3").keyup(function(){
        isValidateNonEmptyStep3();
    });

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

    $("#recommendInfo").keyup(function(){
        isValidateNonEmptyStep4();
    });

    $("#clauseInsrProdFlag").click(function(){
        isValidateNonEmptyStep4();
    });



    var isValidateStep1 = function() {
        var operType = $("#operType").val() ;
        var cstmrType = $("input:radio[name=cstmrType]:checked").val();
        var reqBuyType = $("#reqBuyType").val() ;
        var onOffType = $("#onOffType").val() ;

        pageObj.applDataForm = APPL_DATA_FORM();
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
        pageObj.applDataForm.sntyColorCd= "";//$("#modelId option:selected").attr("atribValCd1") ; //색상코드
        pageObj.applDataForm.sntyCapacCd="";// $("#modelId option:selected").attr("atribValCd2") ; //용량코드
        pageObj.applDataForm.enggMnthCnt = $('#enggMnthCnt').val();
        //pageObj.applDataForm.modelId = $("#prdtId").val();<=== 유심 아이디 설정
        //pageObj.applDataForm.reqModelName  ="";

        pageObj.applDataForm.openMarketReferer = $("#openMarketReferer").val();
        validator.config={};
        validator.config['selSocCode'] = 'isNonEmpty';
        validator.config['cstmrType1'] = 'isNonEmpty';

        if(operType == OPER_TYPE.NEW && onOffType == "5"){
            //셀프개통 신규가입 신청서 내 ‘휴대폰 인증‘ 추가
            validator.config['isSmsAuth'] = 'isNonEmpty';
        }
        if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE ) {
            validator.config['changeCstmrMobileFn'] = 'isNumFix3';
            validator.config['changeCstmrMobileMn'] = 'isNumBetterFixN3';
            validator.config['changeCstmrMobileRn'] = 'isNumFix4';
            validator.config['changeCstmrMobileFn&changeCstmrMobileMn&changeCstmrMobileRn'] = 'isPhoneNumberCheck';
        }

        validator.config['cstmrName'] = 'isNonEmpty';
        if (cstmrType == CSTMR_TYPE.NA) {
            //내국인
            if(!(operType === OPER_TYPE.CHANGE || operType === OPER_TYPE.EXCHANGE)){ //기변은 체크하지않음
                validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
            }
            var rrnFront = $.trim($("#cstmrNativeRrn01").val());
            var rrnBack = $.trim($("#cstmrNativeRrn02").val());

            pageObj.applDataForm.cstmrNativeRrn = getMaskedRrn(rrnFront, rrnBack);
            pageObj.applDataForm.desCstmrNativeRrn =  getMaskedRrn(rrnFront, rrnBack);
            pageObj.applDataForm.birthDate = $.trim($("#cstmrNativeRrn01").val());
            pageObj.applDataForm.cstmrForeignerRrn = "";

        } else if (cstmrType == CSTMR_TYPE.NM) {
            //청소년
            if(!(operType === OPER_TYPE.CHANGE || operType === OPER_TYPE.EXCHANGE)){ //기변은 체크하지않음
                validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isTeen';
                validator.config['minorAgentRrn01&minorAgentRrn02'] = 'isJimin';
            }
            var rrnFront = $.trim($("#cstmrNativeRrn01").val());
            var rrnBack = $.trim($("#cstmrNativeRrn02").val());
            var agentRrnFront = $.trim($("#minorAgentRrn01").val());
            var agentRrnBack = $.trim($("#minorAgentRrn02").val());

            validator.config['minorAgentName'] = 'isNonEmpty';
            validator.config['minorAgentRelation'] = 'isNonEmpty';
            validator.config['minorAgentAgrmYn'] = 'isNonEmpty';
            validator.config['minorAgentTelFn'] = 'isNumFix3';
            validator.config['minorAgentTelMn'] = 'isNumBetterFixN3';
            validator.config['minorAgentTelRn'] = 'isNumFix4';
            validator.config['minorAgentTelFn&minorAgentTelMn&minorAgentTelRn'] = 'isPhoneNumberCheck';

            pageObj.applDataForm.cstmrNativeRrn = getMaskedRrn(rrnFront, rrnBack);
            pageObj.applDataForm.desCstmrNativeRrn = getMaskedRrn(rrnFront, rrnBack);
            pageObj.applDataForm.birthDate = $.trim($("#minorAgentRrn01").val());
            pageObj.applDataForm.cstmrForeignerRrn = "";
            pageObj.applDataForm.minorAgentName = $.trim($("#minorAgentName").val());
            pageObj.applDataForm.minorAgentRrn = agentRrnFront + agentRrnBack;
            pageObj.applDataForm.minorAgentTelFn = $.trim($("#minorAgentTelFn").val());
            pageObj.applDataForm.minorAgentTelMn = $.trim($("#minorAgentTelMn").val());
            pageObj.applDataForm.minorAgentTelRn = $.trim($("#minorAgentTelRn").val());
            pageObj.applDataForm.minorAgentRelation = $("#minorAgentRelation").val();

            pageObj.applDataForm.minorAgentAgrmYn = $("#minorAgentAgrmYn").is(":checked") ? "Y":"N";;


        } else if (cstmrType == CSTMR_TYPE.FN) {
            //외국인
            if(!(operType === OPER_TYPE.CHANGE || operType === OPER_TYPE.EXCHANGE)) { //기변은 체크하지않음
                validator.config['cstmrForeignerRrn01&cstmrForeignerRrn02'] = 'isFngno';
            }
            var frnRrnFront = $.trim($("#cstmrForeignerRrn01").val());
            var frnRrnBack = $.trim($("#cstmrForeignerRrn02").val());

            pageObj.applDataForm.cstmrNativeRrn = ""  ;
            pageObj.applDataForm.desCstmrNativeRrn =  ""  ;
            pageObj.applDataForm.birthDate = $.trim($("#cstmrForeignerRrn01").val());
            pageObj.applDataForm.cstmrForeignerRrn = getMaskedRrn(frnRrnFront, frnRrnBack);
        }

        validator.config['isAuth'] = 'isNonEmpty';
        pageObj.applDataForm.cstmrName = $.trim($("#cstmrName").val());
        pageObj.applDataForm.telAdvice = "N";

        if(operType == OPER_TYPE.MOVE_NUM ){
            validator.config['clauseMoveCode'] = 'isNonEmpty';
        }
        validator.config['clausePriCollectFlag'] = 'isNonEmpty';
        validator.config['clausePriOfferFlag'] = 'isNonEmpty';
        validator.config['clauseEssCollectFlag'] = 'isNonEmpty';
        validator.config['clauseFathFlag01'] = 'isNonEmpty';
        validator.config['clauseFathFlag02'] = 'isNonEmpty';
        // validator.config['clausePriTrustFlag'] = 'isNonEmpty';
        // validator.config['clauseConfidenceFlag'] = 'isNonEmpty';

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

        // pageObj.applDataForm.clauseFinanceFlag = $("#clauseFinanceFlag").is(":checked") ? "Y":"N";
        pageObj.applDataForm.clausePriCollectFlag = $("#clausePriCollectFlag").is(":checked") ? "Y":"N";
        pageObj.applDataForm.clausePriOfferFlag = $("#clausePriOfferFlag").is(":checked") ? "Y":"N";
        pageObj.applDataForm.clauseEssCollectFlag = $("#clauseEssCollectFlag").is(":checked") ? "Y":"N";
        pageObj.applDataForm.clauseFathFlag = $("#clauseFathFlag01").is(":checked") && $("#clauseFathFlag02").is(":checked") ? "Y":"N";
        // pageObj.applDataForm.clausePriTrustFlag = $("#clausePriTrustFlag").is(":checked") ? "Y":"N";
        // pageObj.applDataForm.clausePriAdFlag = $("#clausePriAdFlag").is(":checked") ? "Y":"N";
        // pageObj.applDataForm.clauseConfidenceFlag = $("#clauseConfidenceFlag").is(":checked") ? "Y":"N";
        //동의 추가
        pageObj.applDataForm.personalInfoCollectAgree = $("#personalInfoCollectAgree").is(":checked") ? "Y":"N"; // 고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의
        pageObj.applDataForm.clausePriAdFlag = $("#clausePriAdFlag").is(":checked") ? "Y":"N"; //개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의
        pageObj.applDataForm.othersTrnsAgree = $("#formOthersTrnsAgree").is(":checked") ? "Y":"N"; // 혜택 제공을 위한 제3자 제공 동의 : M모바일
        pageObj.applDataForm.othersTrnsKtAgree = $("#formOthersTrnsKtAgree").is(":checked") ? "Y":"N"; // 혜택 제공을 위한 제3자 제공 동의 : KT
        pageObj.applDataForm.othersAdReceiveAgree = $("#othersAdReceiveAgree").is(":checked") ? "Y":"N"; // 제3자 제공관련 광고 수신 동의

        if (validator.validate()) {
            return true;
        } else {
            var errId = validator.getErrorId();
            console.log("errId==>" + errId);
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

    var isValidateStep2 = function() {
        var cstmrType = $("input:radio[name=cstmrType]:checked").val();
        var operType = $("#operType").val() ;

        validator.config={};
        var onOffLineUsim = $("input:radio[name='onOffLineUsim']:checked").val();
        if (onOffLineUsim == "offLine") {
            validator.config['reqUsimSn'] = 'isNumFix19';
            validator.config['isUsimNumberCheck'] = 'isNonEmpty';
            pageObj.applDataForm.reqUsimSn = $.trim($("#reqUsimSn").val());
        }


        if(operType != OPER_TYPE.CHANGE && operType != OPER_TYPE.EXCHANGE ) {
            validator.config['cstmrMobileFn'] = 'isNumFix3';
            validator.config['cstmrMobileMn'] = 'isNumBetterFixN3';
            validator.config['cstmrMobileRn'] = 'isNumFix4';
            validator.config['cstmrMobileFn&cstmrMobileMn&cstmrMobileRn'] = 'isPhoneNumberCheck';


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


            pageObj.applDataForm.cstmrMobileFn = $.trim($("#cstmrMobileFn").val());
            pageObj.applDataForm.cstmrMobileMn = $.trim($("#cstmrMobileMn").val());
            pageObj.applDataForm.cstmrMobileRn = $.trim($("#cstmrMobileRn").val());

            validator.config['cstmrPost'] = 'isNonEmpty';
            validator.config['cstmrMail'] = 'isNonMail';

            pageObj.applDataForm.cstmrPost = $.trim($("#cstmrPost").val());
            pageObj.applDataForm.cstmrAddr = $.trim($("#cstmrAddr").val());
            pageObj.applDataForm.cstmrAddrDtl = $.trim($("#cstmrAddrDtl").val());
            pageObj.applDataForm.cstmrMail = $.trim($("#cstmrMail").val())  ;

            //신분증 타입
            validator.config['selfCertType1'] = 'isNonEmpty';
            
            //안면인증 
            if($("#fathTrgYn").val() == "Y") {
                validator.config['isFathTxnRetv'] = 'isNonEmpty';
            }
            pageObj.applDataForm.fathTrgYn = $("#fathTrgYn").val();

            //부정가입방지
            validator.config['selfInqryAgrmYnFlag'] = 'isNonEmpty';//본인조회동의
            validator.config['selfIssuExprDt'] = 'isIssuDate';//발급일자

            var selfCertType = $("input:radio[name=selfCertType]:checked").val();
            var cstmrType = $("input:radio[name=cstmrType]:checked").val();
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
                } else if (selfCertType=="06") {
                    validator.config['cstmrForeignerNation'] = 'isNonEmpty';
                    pageObj.applDataForm.cstmrForeignerNation = $.trim($("#cstmrForeignerNation").val());
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
                } else if (selfCertType=="06") {
                    validator.config['cstmrForeignerNation'] = 'isNonEmpty';
                    pageObj.applDataForm.cstmrForeignerNation = $.trim($("#cstmrForeignerNation").val());
                }
            }
        } else {
            //기변
            pageObj.applDataForm.cstmrMobileFn = $.trim($("#changeCstmrMobileFn").val());
            pageObj.applDataForm.cstmrMobileMn = $.trim($("#changeCstmrMobileMn").val());
            pageObj.applDataForm.cstmrMobileRn = $.trim($("#changeCstmrMobileRn").val());
        }

        //배송정보 확인
        var onOffType = $("#onOffType").val() ;
        if(onOffType != "5"){
            if ($('#divDlvry').is(':visible') ) {

                validator.config['dlvryName'] = 'isNonEmpty';
                if ($("#dlvryTelFn").val() != "") {
                    validator.config['dlvryTelFn'] = 'isNumBetterFixN2';
                    validator.config['dlvryTelMn'] = 'isNumBetterFixN3';
                    validator.config['dlvryTelRn'] = 'isNumFix4';
                    validator.config['dlvryTelFn&dlvryTelMn&dlvryTelRn'] = 'isTelNumberCheck';

                }
                validator.config['dlvryMobileFn'] = 'isNumFix3';
                validator.config['dlvryMobileMn'] = 'isNumBetterFixN3';
                validator.config['dlvryMobileRn'] = 'isNumFix4';
                validator.config['dlvryMobileFn&dlvryMobileMn&dlvryMobileRn'] = 'isPhoneNumberCheck';
                pageObj.applDataForm.dlvryName = $.trim($("#dlvryName").val());
                if ($("#dlvryTelFn").val() != "") {
                    pageObj.applDataForm.dlvryTelFn = $.trim($("#dlvryTelFn").val());
                    pageObj.applDataForm.dlvryTelMn = $.trim($("#dlvryTelMn").val());
                    pageObj.applDataForm.dlvryTelRn = $.trim($("#dlvryTelRn").val());
                }
                var dlvryMobile = $.trim($("#dlvryMobile").val());
                pageObj.applDataForm.dlvryMobileFn = $.trim($("#dlvryMobileFn").val());
                pageObj.applDataForm.dlvryMobileMn = $.trim($("#dlvryMobileMn").val());
                pageObj.applDataForm.dlvryMobileRn = $.trim($("#dlvryMobileRn").val());

                //바로배송
                var dlvryType = "01";
                if ($("input:radio[name=dlvryType]:checked").val() != undefined) {
                    dlvryType = $("input:radio[name=dlvryType]:checked").val();
                }

                pageObj.applDataForm.dlvryPost = $.trim($("#dlvryPost").val());
                pageObj.applDataForm.dlvryAddr = $.trim($("#dlvryAddr").val());
                pageObj.applDataForm.dlvryAddrDtl = $.trim($("#dlvryAddrDtl").val());
                pageObj.applDataForm.dlvryMemo = $.trim($("#dlvryMemo").val());

                if ("02" == dlvryType) {
                    validator.config['bizOrgCd'] = 'isNonEmpty';
                    //바로배송 등록
                    //reqDlvryForm.onlineAuthDi = pageObj.authObj.cid;
                    reqDlvryForm.cstmrName = $("#cstmrName").val();
                    reqDlvryForm.cstmrNativeRrn = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;
                    reqDlvryForm.dlvryName = $("#dlvryName").val();
                    reqDlvryForm.dlvryTelFn = pageObj.applDataForm.dlvryMobileFn;
                    reqDlvryForm.dlvryTelMn = pageObj.applDataForm.dlvryMobileMn;
                    reqDlvryForm.dlvryTelRn = pageObj.applDataForm.dlvryMobileRn;

                    reqDlvryForm.dlvryPost = $.trim($("#dlvryPost").val());
                    reqDlvryForm.dlvryAddr = $.trim($("#dlvryAddr").val());
                    reqDlvryForm.dlvryAddrDtl = $.trim($("#dlvryAddrDtl").val());

                    // 배송 메제시 추가작업
                    var orderReqMsg = $("#orderReqMsg").val();
                    var dlvryMemo = "";
                    if(orderReqMsg=="0"){
                        dlvryMemo = "";
                    } else if(orderReqMsg=="4"){
                        dlvryMemo = $("#dlvryMemo").val();
                    } else {
                        dlvryMemo = $("#orderReqMsg option:selected").text();
                    }
                    reqDlvryForm.dlvryMemo = $.trim(dlvryMemo).replace(/[?&=]/gi,' ');
                    reqDlvryForm.usimAmt = $("#usimPrice").val();
                    reqDlvryForm.homePw = $.trim($("#homePw").val()).replace(/[?&=]/gi,' ');
                    reqDlvryForm.exitTitle = $("input:radio[name=exitPw]:checked").attr("textval");

                    //신청서 메모 처리
                    if (reqDlvryForm.exitTitle != null && reqDlvryForm.exitTitle != "") {
                        pageObj.applDataForm.dlvryMemo = reqDlvryForm.dlvryMemo + " / " + reqDlvryForm.exitTitle;
                    } else {
                        pageObj.applDataForm.dlvryMemo = reqDlvryForm.dlvryMemo;
                    }

                    if("1" == $("input:radio[name=exitPw]:checked").val()) {
                        validator.config['homePw'] = 'isNonEmpty';
                    }
                } else if ("03" == dlvryType) {
                    validator.config['dlvryPost'] = 'isNonEmpty';
                }
            }
        }

        if (validator.validate()) {
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
                }
            });
            return false;
        }

    }


    var isValidateStep3 = function() {
        var operType = $("#operType").val() ;
        var onOffType = $("#onOffType").val() ;

        validator.config={};
        if (operType==OPER_TYPE.MOVE_NUM ) {
            //번호이동
            validator.config['moveCompany'] = 'isNonEmpty';
            validator.config['moveMobileFn&moveMobileMn&moveMobileRn'] = 'isNumsMNP';

            pageObj.applDataForm.moveCompany = $("#moveCompany").val();
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
                } else {
                    errorMsg = "사용중인 통신사을 선택해 주세요.";
                }
            }

            MCP.alert(errorMsg ,function (){
                if ($("#moveCompanyGroup2").val() == "") {
                    $("#moveCompanyGroup2").focus();
                }else {
                    $selectObj = $("#"+errId);
                    viewErrorMgs($selectObj, validator.getErrorMsg());
                }
            });
            return false;
        }

    } ;

    var isValidateStep4 = function() {
        validator.config={};

        if (undefined != $("#recommendInfo").val() && "" != $("#recommendInfo").val()  ) {
            pageObj.applDataForm.recommendInfo = $.trim($("#recommendInfo").val());
            pageObj.applDataForm.recommendFlagCd = $("#recommendFlagCd").val() ;
            if ("01" == $("#recommendFlagCd").val()) {
                validator.config['commendId'] = 'isCommendId';
                var commendId = $.trim($("#recommendInfo").val());
                $("#commendId").val(commendId.toUpperCase());
                pageObj.applDataForm.commendId = commendId.toUpperCase();
            } else {
                pageObj.applDataForm.commendId = "";
            }
        } else {
            pageObj.applDataForm.recommendFlagCd = "" ;
            pageObj.applDataForm.recommendInfo = "";
            pageObj.applDataForm.commendId = "";
        }
        pageObj.applDataForm.reqAddition = pageObj.reqAddition.join(',') ;
        //부가서비스 리스트
        pageObj.applDataForm.additionKeyList = pageObj.additionKeyList;

        //휴대폰 안심 서비스 처리
        if (pageObj.insrProdCd != ""){
            validator.config['clauseInsrProdFlag'] = 'isNonEmpty';
            validator.config['clauseInsrProdFlag02'] = 'isNonEmpty';
            validator.config['clauseInsrProdFlag03'] = 'isNonEmpty';
            validator.config['isAuthInsr'] = 'isNonEmpty';
            pageObj.applDataForm.insrProdCd = pageObj.insrProdCd;
        } else {
            pageObj. applDataForm.insrProdCd = "";
        }

        // 자급제 보상 서비스 처리
        if (pageObj.rwdProdCd != ""){

            // 1. 구입가
            validator.config['rwdBuyPric']='isNonEmpty';

            // 2. IMEI 입력여부
            var imei1Len= $("#rwdImei1").val().length;
            var imei2Len= $("#rwdImei2").val().length;
            if(imei1Len > 0 && imei2Len > 0){ // imei1과 imei2 모두 입력한 경우_두개 다 유효성검사 대상
                validator.config['rwdImei1']='isNumFix15';
                validator.config['rwdImei2']='isNumFix15';
            }
            else if(imei1Len > 0) validator.config['rwdImei1']='isNumFix15';
            else if(imei2Len > 0) validator.config['rwdImei2']='isNumFix15';
            else validator.config['rwdImei1']='isNumFix15'; // 아무것도 입력하지 않은 경우_입력 필요

            // 3. 구매영수증 첨부 여부
            validator.config['rwdFile1']='isImg';

            // 4. 약관동의 여부
            validator.config['clauseRwdRegFlag'] = 'isNonEmpty';            // 서비스 가입 동의
            validator.config['clauseRwdPaymentFlag'] = 'isNonEmpty';        // 지급기준 이행 동의
            validator.config['clauseRwdRatingFlag'] = 'isNonEmpty';         // 평가 기준 동의
            validator.config['clauseRwdPrivacyInfoFlag'] = 'isNonEmpty';    // 개인정보 수집ㆍ이용에 대한 동의
            validator.config['clauseRwdTrustFlag'] = 'isNonEmpty';          // 개인정보 수집ㆍ이용ㆍ제공ㆍ위탁 동의서

            // 5. 휴대폰 인증 여부
            validator.config['isAuthRwd'] = 'isNonEmpty';

            // 전송 데이터 세팅
            pageObj.applDataForm.rwdProdCd = pageObj.rwdProdCd;   // 선택한 자급제 보상 서비스 코드

        } else {
            // 자급제 보상 서비스 미선택
            pageObj.applDataForm.rwdProdCd = "";
        }

        pageObj.applDataForm.prmtIdList = pageObj.prmtIdList;
        pageObj.applDataForm.prdtIdList = pageObj.prdtIdList;

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
    } ;

    var isValidateStep5 = function() {
        var cstmrType = $("input:radio[name=cstmrType]:checked").val();
        var reqPayType = $('input:radio[name=reqPayType]:checked').val();

        validator.config={};
        pageObj.applDataForm.cstmrBillSendCode = $('input:radio[name=cstmrBillSendCode]:checked').val();
        pageObj.applDataForm.cstmrMailReceiveFlag = "";

        //pageObj.applDataForm.reqWireType = "";    ???
        //pageObj.applDataForm.reqModelName  = "";  ???

        pageObj.applDataForm.reqPayType = reqPayType;


        if ( reqPayType== "D") {
            //자동이체
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

        } else if ( reqPayType== "C"  ) {
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

    } ;

    var viewErrorMgs = function($thatObj, msg ) {
        if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
            $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
        } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3") ) {
            $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
        }
    };



    var fnSaveForm = function() {
        $('._btnNext').hide();
        var onOffType = $("#onOffType").val() ;
        pageObj.applDataForm.onOffType = onOffType ;
        pageObj.applDataForm.cstmrPost = $.trim($("#cstmrPost").val());
        pageObj.applDataForm.cstmrAddr = $.trim($("#cstmrAddr").val());
        pageObj.applDataForm.cstmrAddrDtl = $.trim($("#cstmrAddrDtl").val());

        //바로배송
        var dlvryType = "01";
        if ($("input:radio[name=dlvryType]:checked").val() != undefined) {
            dlvryType = $("input:radio[name=dlvryType]:checked").val();
        } else {
            dlvryType = "";
        }

        pageObj.applDataForm.dlvryType = dlvryType ;

        fnSaveForm2();

    };


    var fnSaveForm2 = function() {
        var varData = ajaxCommon.getSerializedData(pageObj.applDataForm);


        ajaxCommon.getItemNoLoading({
                id:'saveAppform'
                ,cache:false
                ,url:'/appForm/saveAppformAjax.do'
                ,data: varData
                ,dataType:"json"
                ,errorCall : function () {
                    $('._btnNext').show();
                }
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    pageObj.requestKey = jsonObj.REQUEST_KET ;

                    // 자급제 보상 서비스 신청 정보 insert
                    if (pageObj.applDataForm.rwdProdCd != ""){
                        applyRwd();
                    }

                    //sendScanImage();
                    ajaxCommon.createForm({
                        method:"post"
                        ,action:"/appForm/appOutFormComplete.do"
                    });

                    ajaxCommon.attachHiddenElement("requestKey",pageObj.requestKey);
                    ajaxCommon.formSubmit();

                } else {
                    $('._btnNext').show();
                    var strHtml = "시스템에 문제가 발생하였습니다.";
                    if (jsonObj.RESULT_MSG != undefined) {
                        strHtml = jsonObj.RESULT_MSG;
                    }
                    MCP.alert(strHtml);
                }
            })
    };



    //부가 서비스 배타 관계 체크...
    $(document).on("click","input:checkbox[name=additionName]",function() {
        var $thisObj = $(this);
        var isCheck = false ;

        if ($thisObj.is (':checked')) {
            var thisVal = $thisObj.val() ;
            for(var i = 0 ; i < pageObj.additionBetaList.length ; i++) {
                if (thisVal == pageObj.additionBetaList[i].expnsnStrVal1) {
                    for(var j = 0 ; j < $("input:checkbox[name=additionName]:checked").length ; j++) {
                        if (pageObj.additionBetaList[i].expnsnStrVal2 == $("input:checkbox[name=additionName]:checked").eq(j).val() ) {
                            MCP.alert(pageObj.additionBetaList[i].expnsnStrVal3 );
                            isCheck = true ;
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
                            MCP.alert(pageObj.additionBetaList[i].expnsnStrVal3 );
                            isCheck = true ;
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


    var getAdditionList = function (chargeFlag) {
        var varData = ajaxCommon.getSerializedData({
            chargeFlag:chargeFlag
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
                setAdditionList(jsonObj,chargeFlag);
            });
    }

    var setAdditionList = function (objList,chargeFlag) {
        if (chargeFlag == "F") {
            $("#ulAdditionListBase").html('');
            $("#ulAdditionListBase").append(getRowTemplate(objList ,chargeFlag));
        } else if(chargeFlag == "N") {
            $("#ulAdditionListFree").html('');
            $("#ulAdditionListFree").append(getRowTemplate(objList ,chargeFlag));
            pageObj.initAddition =1;
        } else if(chargeFlag == "Y") {
            $("#ulAdditionListPrice").html('');
            $("#ulAdditionListPrice").append(getRowTemplate(objList ,chargeFlag ));
            pageObj.additionList = objList;
            pageObj.initAddition =2;
        }
    }

    var getRowTemplate = function(objList ,chargeFlag ){
        var arr =[];
        for(i = 0 ; i < objList.length ; i++) {
            if (pageObj.additionCatchCallKey == objList[i].additionKey ) {
                //캐치콜 플러스 제외
                continue;
            }
            arr.push("<li class='c-accordion__item'>\n");
            arr.push("    <div class='c-accordion__head'>\n");
            arr.push("        <button class='runtime-data-insert c-accordion__button _btnAccordion' id='acc_header"+ chargeFlag+i +"' type='button' aria-expanded='false' aria-controls='acc_content"+ chargeFlag+i +"'  href='javascript:void(0);' rateAdsvcProdRelSeq='"+objList[i].rateAdsvcProdRelSeq+"' >\n");
            arr.push("            <span class='c-hidden'>"+objList[i].additionName+" 상세열기</span>\n");
            arr.push("        </button>\n");
            arr.push("        <div class='c-accordion__check'>\n");
            arr.push("        <input class='c-checkbox c-checkbox--type3' type='checkbox' name='additionName' id='additionName"+objList[i].additionKey+"' value='"+objList[i].additionKey+"' rantal="+objList[i].vatRantal+" txtName='"+objList[i].additionName+"' chargeFlag='"+objList[i].chargeFlag+"'/>\n");
            arr.push("        <label class='c-label' for='additionName"+objList[i].additionKey+"'>"+objList[i].additionName+"</label>\n");
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






    $(document).on("click","._btnAccordion",function() {
        var rateAdsvcProdRelSeq = $(this).attr("rateAdsvcProdRelSeq");
        var $thatObj = $(this);
        if (rateAdsvcProdRelSeq == null || rateAdsvcProdRelSeq == "" || rateAdsvcProdRelSeq == "null") {
            $thatObj.parent().parent().find('.c-accordion__inside').html("상세정보가 없습니다.");
            return;
        }


        if ($thatObj.parent().parent().find('.c-accordion__inside').html() != "" ) {
            return; //두번 호출 방지
        }
        /*
        RATE_ADSVC_PROD_REL_SEQ <> RATE_ADSVC_GDNC_SEQ 차이가 뭘까?
        */
        var varData = ajaxCommon.getSerializedData({
            subTabId : 'sub1'
            , rateAdsvcGdncSeq : rateAdsvcProdRelSeq
        });

        ajaxCommon.getItemNoLoading({
                id:'adsvcContent'
                ,cache:false
                ,url:"/rate/adsvcContent.do"
                ,data: varData
                ,dataType:"html"
                ,async:false
                , errorCall : function () {
                    $thatObj.parent().parent().find('.c-accordion__inside').html("상세정보가 없습니다.");
                }
            }
            ,function(strHtml){
                $thatObj.parent().parent().find('.c-accordion__inside').html(strHtml);
            });

    });

    //부가 서비스 추가 삭제 버튼 클릭
    $("#btnAddition").click(function(){
        if (pageObj.initAddition < 1) {
            getAdditionList("N");
            getAdditionList("Y");
        }
    });

    var getAdditionBetaList = function() {
        var varData = ajaxCommon.getSerializedData({
            grpCd:"additionbeta"
        });

        ajaxCommon.getItemNoLoading({
                id:'getCodeList'
                ,cache:false
                ,url:'/getCodeListAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(objList){
                if (objList!=null) {
                    pageObj.additionBetaList= objList ;
                }
            });
    };

    //휴대폰 안심 서비스 정보 조회
    var getInsrProdInfo = function() {
        var varData = ajaxCommon.getSerializedData({
            rprsPrdtId:$("#rprsPrdtId").val()
            ,reqBuyType:$("#reqBuyType").val()
        });
        ajaxCommon.getItemNoLoading({
                id:'selectInsrProdList'
                ,cache:false
                ,url:'/appform/selectInsrProdListAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonList){
                pageObj.insrProdList = jsonList;
                var insrProdCdTemp = $("#insrProdCdTemp").val() ;
                $("#divInsrProdList").empty();
                for(i = 0 ; i < jsonList.length ; i++) {
                    $("#divInsrProdList").append(getRowTemplateInsrProd(jsonList[i], insrProdCdTemp ));
                    $("#insrProdCD_"+jsonList[i].insrProdCd).last().data(jsonList[i]);
                }
                if (insrProdCdTemp != "") {
                    $("input:radio[name=insrProdCD][value="+insrProdCdTemp+"]").trigger("click") ;
                    setTimeout(function(){
                        $("#btnInsrProdConfirm").trigger("click") ;
                    }, 500);

                    //초기화
                    $("#insrProdCdTemp").val("") ;
                }

            });
    };

    $('#btnInsrProd').click(function(){
        validator.config={};
        validator.config['isAuth'] = 'isNonEmpty'; //TEST 위한

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        if (validator.validate()) {
            if (pageObj.insrProdList.length < 1) {
                getInsrProdInfo();
            }
            var el = document.querySelector('#insrProdDialog');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg() ,function (){
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
        }

    });

    var getRowTemplateInsrProd = function(obj , insrProdCdParam){
        var arr =[];

        arr.push("<div class='c-card c-card--type2'>\n");
        if (obj.insrProdCd == insrProdCdParam) {
            arr.push("            <input class='c-checkbox c-checkbox--type4' id='insrProdCD_"+obj.insrProdCd+"' value='"+obj.insrProdCd+"' type='radio' name='insrProdCD' checked >\n");
        } else {
            arr.push("            <input class='c-checkbox c-checkbox--type4' id='insrProdCD_"+obj.insrProdCd+"' value='"+obj.insrProdCd+"' type='radio' name='insrProdCD'>\n");
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

    $('#btnInsrProd2').click(function(){
        $("#btnInsrProd").trigger("click") ;
    });

    $(document).on("click","input:radio[name=insrProdCD]",function() {
        var itemObj = $(this).data();
        var insrProdCd = itemObj.insrProdCd;

        if (itemObj== null || insrProdCd==null) {
            pageObj.insrProdObj = null;
        } else {
            pageObj.insrProdObj = itemObj;
        }
    });

    $('#btnInsrProdConfirm').click(function(){

        if (pageObj.insrProdObj != null) {
            $("#divInsrProdInfo").hide() ;
            $("._insrProdList").show() ;
            $("#insrProdView").html(getRowTemplateInsrProdView(pageObj.insrProdObj)) ;
            //console.log("pageObj.insrProdCd==>" + pageObj.insrProdCd);

            //초기화
            if (pageObj.insrProdCd != pageObj.insrProdObj.insrProdCd) {
                $("#btnInsrAllCheck").prop("checked",false);
                $("#clauseInsrProdFlag").prop("checked",false);
                $("#clauseInsrProdFlag02").prop("checked",false);
                $("#clauseInsrProdFlag03").prop("checked",false);
            }
            pageObj.insrProdCd = pageObj.insrProdObj.insrProdCd ;

        } else {
            $("#divInsrProdInfo").show() ;
            $("._insrProdList").hide() ;
            // 약관 해제처리
            $("#btnInsrAllCheck").prop("checked", false);
            $("#clauseInsrProdFlag").prop("checked", false);
            $("#clauseInsrProdFlag02").prop("checked", false);
            $("#clauseInsrProdFlag03").prop("checked", false);
            pageObj.insrProdCd =  "";
        }

        isValidateNonEmptyStep4();
    });




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
            for (i=0; i < insrProdDescList.length; i++) {
                arr.push("        <span>"+insrProdDescList[i]+"</span>\n");
            }
            arr.push("    </p>\n");
            arr.push("</div>\n");
            arr.push("<div class='c-card__price-box'>\n");
            arr.push("    <span class='ico-type'>\n");
            for (i=0; i < insrProdIcoList.length; i++) {
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

    //약관정보 패치
    var getFormDesc = function(cdGroupId1,cdGroupId2) {
        var varData = ajaxCommon.getSerializedData({
            cdGroupId1:cdGroupId1
            ,cdGroupId2:cdGroupId2
        });
        var rtnObj;
        ajaxCommon.getItemNoLoading({
                id:'getFormDesc'
                ,cache:false
                ,async:false
                ,url:'/termsPop.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                rtnObj = jsonObj ;
            });
        return rtnObj ;
    };

    var step4Show = function() {
        setStep(4);

        //임시 저장 안심보험
        var insrProdCdTemp = $("#insrProdCdTemp").val() ;

        getInsrProdInfo();

        if($("._rwdProdForm").length > 0){
            getRwdProdInfo();
        }

        isValidateNonEmptyStep4();
    };


    //할부 금액 정보 조회 및 요금제
    var getListSale = function() {
        var prodId = $("#prodId").val();
        var modelSalePolicyCode = $("#modelSalePolicyCode").val();
        var agrmTrm = $("#enggMnthCnt").val();
        var modelMonthlyVal = $("#modelMonthly").val();
        var reqBuyType = $("#reqBuyType").val() ;
        var plcySctnCd = "02";  //--정책구분코드(01:단말,02:유심)
        if (REQ_BUY_TYPE.PHONE == reqBuyType ) {
            plcySctnCd = "01";
        }

        if (agrmTrm == "") {
            agrmTrm = "0";
        }

        if (modelMonthlyVal == "") {
            modelMonthlyVal ="0" ;
        }

        var varData = ajaxCommon.getSerializedData({
            salePlcyCd:modelSalePolicyCode
            ,prdtId:$("#rprsPrdtId").val()
            ,orgnId:$("#cntpntShopId").val()
            ,operType:$("#operType").val()
            ,rateCd:""
            ,onOffType:$("#onOffType").val()
            ,noArgmYn:"Y"
            ,agrmTrm:agrmTrm
            ,plcySctnCd:plcySctnCd
            ,modelMonthly:modelMonthlyVal
        });

        pageObj.preSocCode = $("#socCode").val();
        ajaxCommon.getItemNoLoading({
                id:'listChargeInfoAjax'
                ,cache:false
                ,url:'/msp/listChargeInfoAjax.do'
                ,data: varData
                ,dataType:"json"
                ,async:false
            }
            ,function(objList){
                if (objList.length > 0) {
                    $("#selSocCode option").remove();
                    for (var i=0 ; i < objList.length ; i++) {
                        //단말이면서 정책이 LTE5G(45)일때 dataType에 따른 요금제 노출
                        if ($("#reqBuyType").val() == "MM" && $("#prdtSctnCd").val() == "LTE5G") {
                            if ($("input[name='dataTypeRadio']:checked").val() != objList[i].mspRateMstDto.dataType) {
                                continue;
                            }
                        }
                        var arr =[];
                        arr.push("<option value="+objList[i].rateCd+" agrmTrm="+objList[i].agrmTrm+" rateNm='"+objList[i].rateNm+"'");
                        if (pageObj.preSocCode == objList[i].rateCd ) {
                            arr.push(" selected='selected' ");
                        }
                        arr.push(" >" +objList[i].rateNm+"</option>");
                        $("#selSocCode").append(arr.join(''));
                        $("#selSocCode option").last().data(objList[i]);

                        $("#selSocCode").parent().addClass('has-value');
                    }
                    $("#selSocCode").trigger("change") ; //change(function() ;

                    var operType = $("#operType").val();
                    if(operType == OPER_TYPE.NEW  ) {
                        $("._fromMove").hide();
                        $("._fromChange").hide();
                        $("._fromNew").show();
                    } else if (operType==OPER_TYPE.MOVE_NUM ) {
                        $("._fromNew").hide();
                        $("._fromChange").hide();
                        $("._fromMove").show();
                    } else if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE ) {
                        $("._fromNew").hide();
                        $("._fromMove").hide();
                        $("._fromChange").show();
                    }
                } else {
                    MCP.alert("요금제 정보가 없습니다. ",function (){
                        $("input:radio[name=operTypeRadio][value='"+pageObj.operType+"']").prop("checked",true); ;
                        $("#operType").val(pageObj.operType);
                    });
                }
            });

        if (modelSalePolicyCode != "") {
            ajaxCommon.getItemNoLoading({
                    id:'getSalePlcyAjax'
                    ,cache:false
                    ,url:'/msp/getSalePlcyAjax.do'
                    ,data: ajaxCommon.getSerializedData({salePlcyCd:modelSalePolicyCode})
                    ,dataType:"json"
                }
                ,function(obj){
                    //정책에서 정보 조회처리
                    $("#prdtSctnCd").val(obj.prdtSctnCd);
                    $("#sprtTp").val(obj.sprtTp);
                });
        }



    } ;



    var showJoinUsimPrice = function() {
        var joinPriceIsPay = $("#joinPriceIsPay").val();
        var usimPriceIsPay = $("#usimPriceIsPay").val();
        var usimPrice = $("#usimPrice").val();
        var joinPrice = $('#joinPrice').val();

        if (joinPriceIsPay == "Y") {
            $("#joinPriceTxt").html(numberWithCommas(joinPrice)+" 원");
        } else {
            $("#joinPriceTxt").html("<span class='c-text u-td-line-through'>"+numberWithCommas(joinPrice)+" 원</span>(무료)");
        }

        if (usimPriceIsPay == "Y") {
            $("#usimPriceTxt").html(numberWithCommas(usimPrice)+" 원");
        } else {
            $("#usimPriceTxt").html("<span class='c-text u-td-line-through'>"+numberWithCommas(usimPrice)+" 원</span>(무료)");
        }
    }

    //판매 요금제 별 가입비/유심비 면제 여부 조회
    var getJoinUsimPriceInfo = function() {
        var cntpntShopId = $("#cntpntShopId").val();
        var varData = "";

        if(CONTPNT_SHOP_ID.BASE == cntpntShopId) {
            varData = ajaxCommon.getSerializedData({
                dtlCd:$("#socCode").val()
                ,cdGroupId:CD_GROUP_ID_OBJ.JoinUsimPriceInfo
            });

            //기본값 설정 다른게
            //기본값 변경 했습니다. 값이 없으며..  무료 처리
            ajaxCommon.getItemNoLoading({
                    id:'getJoinUsimPriceInfo'
                    ,cache:false
                    ,url:'/m/getCodeNmAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,async:false
                }
                ,function(jsonObj){
                    if (jsonObj != null && jsonObj.expnsnStrVal1 == "Y") {
                        $("#joinPriceIsPay").val("Y");
                    } else {
                        $("#joinPriceIsPay").val("N");
                    }

                    if (jsonObj != null && jsonObj.expnsnStrVal2 == "Y") {
                        $("#usimPriceIsPay").val("Y");
                    } else {
                        $("#usimPriceIsPay").val("N");
                    }

                    if (jsonObj != null && jsonObj.expnsnStrVal3 == "Y") {
                        $("#usimPriceNfcIsPay").val("Y");
                    } else {
                        $("#usimPriceNfcIsPay").val("N");
                    }
                });

        } else {
            varData = ajaxCommon.getSerializedData({
                dtlCd:cntpntShopId
                ,cdGroupId:CD_GROUP_ID_OBJ.JoinUsimPriceInfoOther
            });

            ajaxCommon.getItemNoLoading({
                    id:'getJoinUsimPriceInfo'
                    ,cache:false
                    ,url:'/m/getCodeNmAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,async:false
                }
                ,function(jsonObj){
                    if (jsonObj != null && jsonObj.expnsnStrVal1 == "N") {
                        $("#joinPriceIsPay").val("N");
                    } else {
                        $("#joinPriceIsPay").val("Y");
                    }

                    if (jsonObj != null && jsonObj.expnsnStrVal2 == "N") {
                        $("#usimPriceIsPay").val("N");
                    } else {
                        $("#usimPriceIsPay").val("Y");
                    }

                    if (jsonObj != null && jsonObj.expnsnStrVal3 == "N") {
                        $("#usimPriceNfcIsPay").val("N");
                    } else {
                        $("#usimPriceNfcIsPay").val("Y");
                    }
                });
        }
        getJoinUsimPrice();
    };

    var getJoinUsimPrice = function() {
        var operType = $("#operType").val() ;
        var prdtSctnCd = $("#prdtSctnCd").val();
        //가입비조회
        var varData = ajaxCommon.getSerializedData({
            dataType: prdtSctnCd
            , operType : operType
            ,rateCd : $("#socCode").val()
        });

        ajaxCommon.getItemNoLoading({
            id:'getUsimInfo'
            ,cache:false
            ,url:'/m/usim/selectUsimBasJoinPriceAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        },function(jsonObj){
            if ('0001' ==  jsonObj.RESULT_CODE) {
                var joinUsimPrice = jsonObj.selectJoinUsimPrice;
                if (joinUsimPrice) {
                    $("#joinPrice").val(joinUsimPrice[0].joinPrice);
                }
                if (prdtSctnCd == PRDT_SCTN_CD.FIVE_G_FOR_MSP) {
                    $("#usimPrice").val($("#usimPrice5G").val());
                } else if(prdtSctnCd == PRDT_SCTN_CD.THREE_G_FOR_MSP) {
                    $("#usimPrice").val($("#usimPrice3G").val());
                } else {
                    $("#usimPrice").val($("#usimPriceBase").val());
                }
                showJoinUsimPrice();
            }
        });
    };




    //getListSale();
    getAdditionBetaList();
    //기본제공 부가 서비스 조회
    getAdditionList("F");
    //기본 부가서비스 설정

    $("input:checkbox[name=additionName]").each(function(index){
        $(this).prop("checked", "checked");
    });




    $("input:radio[name=operTypeRadio]:checked").trigger("click");
    // 부가 서비스 확인  클릭 (기본제공 부가서비스 설정 처리)
    $("#btnSetAddition").trigger("click") ;
    $("input:radio[name=cstmrType]:checked").trigger("click");
    var operType = $("#operType").val() ;

    $("input[name='onOffLineUsim']:checked").trigger("change",{'isInit':true});



    //TEST 위한
    //step4Show();

    // ================= START: 자급제 보상 서비스 관련 로직 =================

    // 자급제 보상 서비스 휴대폰 인증 클릭 이벤트
    $("#btnAuthRwd").click(function(){

        if ($("#isAuthRwd").val() == "1") {
            MCP.alert("본인 인증을 완료 하였습니다.");
            return false;
        }

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        // 자급제 보상 서비스 휴대폰 인증 전 유효성 검사
        validator.config={};
        validator.config['isAuth'] = 'isNonEmpty';                      // 1.본인인증
        validator.config['rwdFile1']='isImg';                           // 2.구매영수증
        validator.config['clauseRwdRegFlag'] = 'isNonEmpty';            // 3.rwd 필수약관_서비스 가입 동의
        validator.config['clauseRwdPaymentFlag'] = 'isNonEmpty';        // 3.rwd 필수약관_지급기준 이행 동의
        validator.config['clauseRwdRatingFlag'] = 'isNonEmpty';         // 3.rwd 필수약관_평가 기준 동의
        validator.config['clauseRwdPrivacyInfoFlag'] = 'isNonEmpty';    // 3.rwd 필수약관_개인정보 수집ㆍ이용에 대한 동의
        validator.config['clauseRwdTrustFlag'] = 'isNonEmpty';          // 3.rwd 필수약관_개인정보 수집ㆍ이용ㆍ제공ㆍ위탁 동의서

        if (validator.validate()) {
            // 휴대폰 인증요청 시간 세팅
            ajaxCommon.getItemNoLoading({
                    id:'getTimeInMillisAjax'
                    ,cache:false
                    ,url:'/nice/getTimeInMillisAjax.do'
                    ,data: ""
                    ,dataType:"json"
                }
                ,function(startTime){
                    pageObj.startTime = startTime ;
                });

            // 휴대폰 인증 창 open
            var data = {width:'500', height:'700'};
            openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M', data);
            pageObj.niceType = NICE_TYPE.RWD_PROD ;  // 본인인증 유형 세팅
            return ;
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg() ,function (){
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
        }
    });

    // 자급제 보상 서비스 초기값 세팅 (+리스트 조회)
    var getRwdProdInfo = function() {

        // 자급제 보상 서비스 리스트 조회
        ajaxCommon.getItemNoLoading({
                id:'selectRwdProdListAjax'
                ,cache:false
                ,url:'/mypage/selectRwdProdListAjax.do'
                ,dataType:"json"
            }
            ,function(jsonList){

                pageObj.rwdProdList = jsonList;

                var rwdProdCdTemp = $("#rwdProdCdTemp").val() ;

                // 자급제 보상 서비스 리스트 세팅
                $("#divRwdProdList").empty();
                for(var i = 0 ; i < jsonList.length ; i++) {
                    var eachRwdHtml= getRowTemplateRwdProd(jsonList[i], rwdProdCdTemp);

                    if(eachRwdHtml != ""){
                        $("#divRwdProdList").append(eachRwdHtml);
                        $("#rwdProdCD_"+jsonList[i].rwdProdCd).last().data(jsonList[i]);
                    }
                }

                // 자급제 보상 서비스 상품 클릭 이벤트 추가
                $("input:radio[name=rwdProdCD]").change(function(){
                    var itemObj = $(this).data();
                    var rwdProdCd = itemObj.rwdProdCd;

                    if (itemObj== null || rwdProdCd==null || itemObj== "" || rwdProdCd=="") {
                        pageObj.rwdProdObj = null;
                    } else {
                        pageObj.rwdProdObj = itemObj; // 선택한 자급제 보상서비스 OBJ
                    }
                });

                // 자급제 보상 서비스 초기값 화면 세팅
                if (rwdProdCdTemp != "") {
                    $("input:radio[name=rwdProdCD][value="+rwdProdCdTemp+"]").trigger("click") ;

                    setTimeout(function(){
                        $("#btnRwdProdConfirm").trigger("click");
                    }, 500);

                    $("#rwdProdCdTemp").val(""); //초기화 (STEP4 넘어간 후 다시 STEP4로 온 경우를 위해 값 비워주기)
                }
            });
    };

    // 자급제 보상 서비스 리스트 화면 그리기 (each)
    var getRowTemplateRwdProd = function(rwdObj, rwdProdCdTemp){
        var arr =[];

        arr.push("<div class='c-card c-card--type2'>\n");
        if (rwdObj.rwdProdCd == rwdProdCdTemp) {
            arr.push("<input class='c-checkbox c-checkbox--type4' id='rwdProdCD_"+rwdObj.rwdProdCd+"' value='"+rwdObj.rwdProdCd+"' type='radio' name='rwdProdCD' checked >\n");
        } else {
            arr.push("<input class='c-checkbox c-checkbox--type4' id='rwdProdCD_"+rwdObj.rwdProdCd+"' value='"+rwdObj.rwdProdCd+"' type='radio' name='rwdProdCD'>\n");
        }
        arr.push("<label class='c-card__label' for='rwdProdCD_"+rwdObj.rwdProdCd+"'>\n");
        arr.push("  <span class='c-hidden'>선택</span>\n");
        arr.push("</label>\n");

        var rwdContentHtml= RWD_PROD_CONTENT[rwdObj.rwdProdCd];

        if(!!rwdContentHtml) {
            arr.push(rwdContentHtml);
        }else if(rwdObj.rwdProdNm != null && rwdObj.rwdProdNm != "") {
            arr.push("<div class='c-card__title'>\n");
            arr.push("  <b>"+rwdObj.rwdProdNm+"</b>\n");
            arr.push("</div>\n");
        }else{ // 내용물 또는 서비스명이 존재하지 않는 경우 화면에 표출하지 않음
            return "";
        }

        arr.push("</div>\n");
        return arr.join('');
    }

    // 자급제 보상 서비스 전체 약관 동의 버튼 클릭 이벤트
    $("#clauseRwdFlag").click(function(){
        if ($(this).is(':checked')) {
            KTM.Confirm('모든 약관/동의에<br/>동의 하시겠습니까?'
                ,function () {
                    // 자급제 전체약관 동의
                    $("._rwdAgree").prop("checked", "checked");
                    this.close();
                    isValidateNonEmptyStep4();
                }
                ,function(){
                    // 선택 약관은 없으므로 취소 클릭 시 개별 약관 기존 체크 상태 유지
                    $("#clauseRwdFlag").prop("checked", false);
                    isValidateNonEmptyStep4();
                });
        } else {
            // 전체 약관 동의 체크 해제
            $("._rwdAgree").prop("checked", false);
            isValidateNonEmptyStep4();
        }
    });

    // 자급제 보상 서비스 개별 약관 동의 클릭 이벤트
    $("._rwdAgree").click(function(){
        rwdTermsCheckCbFn();
    });

    // 자급제 보상 서비스 신청/변경 버튼 클릭 이벤트
    $('#btnRwdProd').click(function(){

        // 유효성 검사 (본인인증)
        validator.config={};
        validator.config['isAuth'] = 'isNonEmpty';

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        if (validator.validate()) {
            // 자급제 보상 서비스 리스트 가져오기
            if (pageObj.rwdProdList.length < 1) {
                getRwdProdInfo();
            }

            var el = document.querySelector('#rwdProdDialog');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg() ,function (){
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
        }
    });

    // 자급제 보상 서비스 상품 레이어팝업 내 확인 버튼 클릭 이벤트
    $('#btnRwdProdConfirm').click(function(){

        var resetYn = false; // 자급제 보상서비스 STEP 초기화 여부

        if (pageObj.rwdProdObj != null) {
            $("#divRwdProdInfo").hide(); // 미선택 화면 숨김처리
            $("._rwdProdList").show();   // 선택 화면 표출처리

            $("#rwdProdView").html(getRowTemplateRwdProdView(pageObj.rwdProdObj)); // 선택화면 세팅
            pageObj.rwdProdCd = pageObj.rwdProdObj.rwdProdCd;

        } else {
            $("#divRwdProdInfo").show();  // 미선택 화면 표출처리
            $("._rwdProdList").hide();    // 선택 화면 숨김처리
            pageObj.rwdProdCd =  "";
            resetYn = true;
        }
        if(resetYn && pageObj.niceHistRwdProdSeq > 0){
            resetProdAuth(NICE_TYPE.RWD_PROD);
        }
        isValidateNonEmptyStep4();
    });

    // 자급제 보상 서비스 선택 화면 세팅
    var getRowTemplateRwdProdView = function(rwdObj){

        if(rwdObj == null || rwdObj == "") return "";

        var arr =[];
        arr.push("<div class='c-card__box box-gray'>\n");
        var rwdProdObj= RWD_PROD_OBJ[rwdObj.rwdProdCd];

        if (rwdProdObj != null) {
            var rwdProdDescList= RWD_PROD_DESC_LIST[rwdObj.rwdProdCd];
            var rwdProdIcoList= RWD_PROD_ICO_LIST[rwdObj.rwdProdCd];

            arr.push("<div class='c-card__title u-mt--0'>\n");
            arr.push(  "<b>"+rwdProdObj.name+"</b>\n");
            arr.push(  "<p class='c-card__sub u-pb--24'>\n");
            for (var i=0; i < rwdProdDescList.length; i++) {
                arr.push("<span>"+rwdProdDescList[i]+"</span>\n");
            }
            arr.push(  "</p>\n");
            arr.push("</div>\n");
            arr.push("<div class='c-card__price-box'>\n");
            arr.push(  "<span class='ico-type'>\n");
            for (i=0; i < rwdProdIcoList.length; i++) {
                arr.push(rwdProdIcoList[i]+"\n");
            }
            arr.push(  "</span>\n");
            arr.push(  "<span class='c-text c-text--type3 u-ml--auto'>월 이용료</span>\n");
            arr.push(  "<span class='u-ml--4'>\n");
            arr.push(    "<b>"+rwdProdObj.price+" 원</b>\n");
            arr.push(  "</span>\n");
            arr.push("</div>\n");
        }else if(rwdObj.rwdProdNm != null && rwdObj.rwdProdNm != ""){
            arr.push("<div class='c-card__title u-mt--0'>\n");
            arr.push(  "<b>"+rwdObj.rwdProdNm+"</b>\n");
            arr.push("</div>\n");
        }else{
            return "";
        }

        arr.push("</div>\n");
        return arr.join('');
    }

    // 자급제 보상 서비스 구입가 입력 이벤트
    $("input[name=rwdBuyPric]").on("keyup change", function(){

        // 에러 문구 제거
        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        // 입력값 확인 (숫자만 허용)
        var inputPrice= $(this).val().replace(/[^0-9]/gi,"");

        if(inputPrice.length > 0){ // 입력값이 존재하는 경우
            var commaPrice= numberWithCommas(inputPrice);
            $(this).val(commaPrice);
        }else{ // 입력값이 존재하지 않은 경우
            $(this).val("");
        }

        isValidateNonEmptyStep4();
    });

    // 자급제 보상 서비스 imei 등록 초기화
    var imeiClear= function(){
        $("#rwdImeiImage").val("");
        $("#imeiErrTxt").html("").hide();
        $("#imei1Txt, #imei2Txt").val("").removeAttr("readonly");
        $("#rwdImei1, #rwdImei2").val("").parent().removeClass("has-value");

        isValidateNonEmptyStep4();
    }

    // 자급제 보상 서비스 단말기 정보값 (IMEI) 이미지 등록 이벤트
    $("#rwdImeiImage").change(function(){
        var imeiImage = $(this).val();

        if(imeiImage ==""){
            MCP.alert("이미지 파일을 선택해 주세요.", function(){isValidateNonEmptyStep4();});
            return false;
        }

        // 확장자 체크
        var ext = imeiImage.split(".").pop().toLowerCase();
        if($.inArray(ext, ["jpg", "jpeg", "png", "tif","tiff", "bmp"]) == -1) {
            $("#rwdImeiImage").val("");
            MCP.alert("첨부파일은 이미지 파일만 등록 가능합니다.", function(){isValidateNonEmptyStep4();});
            return false;
        }

        // 이미지 파일명 체크
        var pattern =  /[\{\}\/?,;:|*~`!^\+<>@\#$%&\\\=\'\"]/gi;
        var fileName = imeiImage.split('\\').pop().toLowerCase();

        if(pattern.test(fileName) ){
            $("#rwdImeiImage").val("");
            MCP.alert('파일명에 특수문자가 포함되어 있습니다.', function(){isValidateNonEmptyStep4();});
            return false;
        }

        // 이미지 내의 imei1 , imei2 값 추출
        var formData = new FormData();
        formData.append("image",  $("#rwdImeiImage")[0].files[0]);

        KTM.LoadingSpinner.show();
        ajaxCommon.getItemFileUp({
                id : 'getOcrImgReadyAjax'
                ,cache : false
                ,async : false
                ,url : '/getOcrImgReadyAjax.do'
                ,data : formData
                ,dataType : "json"
            }
            ,function(result){
                KTM.LoadingSpinner.hide();
                imeiClear();  // imei관련 내용 초기화

                var retCode= (!result || !result.retCode) ? "99" :result.retCode;
                var retMsg= (!result || !result.retMsg) ? "이미지 파일을 확인해주세요." : result.retMsg;

                var imei1 = "";
                var imei2 = "";

                if(retCode != '0000'){
                    if(retCode == "0002"){ // 문구 바꿔치기
                        retMsg= "자급제 보상서비스 가입을 위해서는 IMEI1값 또는 IMEI2값이 필요합니다.<br/>자급제 보상서비스 가입가능한 기기 여부를 확인 바랍니다.";
                    }
                    $("#imeiErrTxt").html(retMsg).show();
                } else {
                    $("#imeiErrTxt").html("").hide();
                    imei1 = result.imei1;
                    imei2 = result.imei2;

                    $("#imei1Txt").val(imei1);
                    $("#imei2Txt").val(imei2);
                }

                // imei image register popup open
                var el = document.querySelector('#imei-check-modal');
                var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                modal.open();
            });
    });

    // 단말기 정보값 (IMEI) 이미지 다시 등록하기 버튼 클릭 이벤트
    $("#imeiReSetBtn").click(function(){
        imeiClear();
        $("#rwdImeiImage").trigger("click");
    });

    // 단말기 정보값 (IMEI) 이미지 확인 버튼 클릭 이벤트
    $("#imeiSetBtn").click(function() {

        var imei1 = ajaxCommon.isNullNvl($.trim($("#imei1Txt").val()), "");
        var imei2 = ajaxCommon.isNullNvl($.trim($("#imei2Txt").val()), "");

        // IMEI 자릿수 확인_IMEI 한개는 필수
        if(imei1 == "" && imei2 == ""){ // imei를 한개도 입력하지 않은 경우
            MCP.alert("IMEI 정보값을 입력해 주세요.", function () {
                $("#imei1Txt").val("");
                $("#imei2Txt").val("");
                $("#imei1Txt").focus();
            });
            return false;
        }

        if (imei1 != "" && imei1.length != 15) { // imei1을 입력한 경우 자릿수 체크
            MCP.alert("imei1 자릿수가 일치하는지 확인해 주세요.", function () {
                $("#imei1Txt").focus();
            });
            return false;
        }

        if (imei2 != "" && imei2.length != 15) { // imei2를 입력한 경우 자릿수 체크
            MCP.alert("imei2 자릿수가 일치하는지 확인해 주세요.", function () {
                $("#imei2Txt").focus();
            });
            return false;
        }

        // imei 사용가능 여부 체크
        var varData = ajaxCommon.getSerializedData({
            imei: imei1, imeiTwo: imei2
        });

        ajaxCommon.getItem({
                id:'checkImeiForRwd'
                ,cache:false
                ,url:'/mypage/checkImeiForRwd.do'
                ,data:varData
                ,dataType:"json"
            }
            ,function(jsonObj){

                var resultCd= (!jsonObj || !jsonObj.RESULT_CODE) ? "FAIL" : jsonObj.RESULT_CODE;
                var resultMsg= (!jsonObj || !jsonObj.RESULT_MSG) ? "사용 불가능한 단말기 정보값입니다." : jsonObj.RESULT_MSG;

                if(resultCd == "FAIL"){
                    MCP.alert(resultMsg);
                    return false;
                }

                var possibleYn= (!jsonObj || !jsonObj.possibleYn) ? "N" : jsonObj.possibleYn;
                if(possibleYn == "Y"){

                    // 에러 문구 제거
                    $(".c-form__text").remove();
                    $(".has-error").removeClass("has-error");

                    // imei image register popup close
                    var el = document.querySelector('#imei-check-modal');
                    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                    modal.close();

                    // 입력한 IMEI값 화면에 뿌리기
                    $("#imei1Txt, #imei2Txt").attr("readonly", true);

                    if(imei1 != "") $("#rwdImei1").val(imei1).parent().addClass("has-value");
                    if(imei2 != "") $("#rwdImei2").val(imei2).parent().addClass("has-value");

                    isValidateNonEmptyStep4();
                }else{
                    MCP.alert("사용불가한 단말기 정보값입니다.<br/>다른 단말기 정보값을 입력 바랍니다.");
                    return false;
                }
            });
    });

    // 구매영수증 등록 클릭 이벤트
    $(".file-label").click(function(){

        // 파일등록 창 open
        if($(".staged_img-list .staged-image").length < 1){
            $("#rwdFile1").trigger("click");
            return true;
        }

        // 증빙서류가 등록되어 있는 경우 alert
        MCP.alert("등록된 첨부파일이 존재합니다.<br>등록된 항목 삭제 후 재 등록 바랍니다.");
    });

    // 자급제 보상서비스 증빙서류 등록 클릭 이벤트_사용자가 강제로 파일 등록 호출하는 경우
    $("#rwdFile1").click(function(){
        // 증빙서류가 이미 등록되어 있는 경우
        if($(".staged_img-list .staged-image").length >= 1){
            MCP.alert("등록된 첨부파일이 존재합니다.<br>등록된 항목 삭제 후 재 등록 바랍니다.");
            return false;
        }
        return true;
    });
    // ================= END: 자급제 보상 서비스 관련 로직 ===================


    //TEST 위해
    //setStep(4);

    getAdditionList("N");
    getAdditionList("Y");


    let additionCatchCallList =  pageObj.additionList.filter(additionTemp => additionTemp.additionKey === pageObj.additionCatchCallKey);
    if (additionCatchCallList != null && additionCatchCallList.length > 0) {
        pageObj.additionCatchCall = additionCatchCallList[0];
    }

    $('.c-accordion.c-accordion--nested > .c-accordion__box > .c-accordion__item > .c-accordion__head .c-accordion__button, .c-accordion.c-accordion--nested__sub > .c-accordion__box > .c-accordion__item > .c-accordion__head .c-accordion__button').click(function() {
        handleAccordionClick(this);
    });

    updateFathSkipBtnVisibility();
});  // end of document.ready ---------------------------------

var insertNiceLog = function() {
    var rtnObj = {};

    var ncType= "";
    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    if(cstmrType == CSTMR_TYPE.NM) ncType= "1";

    var varData = ajaxCommon.getSerializedData({
        cstmrName:"11"
        , cstmrNativeRrn:"11"
        , reqSeq:pageObj.niceResLogObj.reqSeq
        , resSeq:pageObj.niceResLogObj.resSeq
        , paramR3:pageObj.niceType
        , startTime:pageObj.startTime
        , ncType: ncType
    });

    ajaxCommon.getItemNoLoading({
            id:'getReqAuthAjax'
            ,cache:false
            ,url:'/auth/getReqAuthAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(jsonObj){
            rtnObj =  jsonObj ;
        });

    return rtnObj;
} ;


var fnNiceCert = function(prarObj) {
    var cstmrNativeRrn ;
    var cstmrName ;
    var strMsg ;
    var operType = $("#operType").val();
    var onOffType = $("#onOffType").val();

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

        var authInfoJson= {cstmrName: cstmrName , cstmrNativeRrn: cstmrNativeRrn
                         , authType:prarObj.authType, operType : operType , onOffType : onOffType, ncType: ncType};

        var isAuthDone= authCallback(authInfoJson);

        if(isAuthDone){
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
            MCP.alert("본인인증에 성공 하였습니다.");
            return null;
        }else{

            if(niceAuthObj.resAuthOjb.RESULT_CODE == "STEP01"){
                strMsg= niceAuthObj.resAuthOjb.RESULT_MSG;
            }

            MCP.alert(strMsg);
            return null;
        }

    } else if (pageObj.niceType == NICE_TYPE.INSR_PROD) {

        var obj = insertNiceLog();

        if (obj.RESULT_CODE == "00000") {
            $("#isAuthInsr").val("1");
            pageObj.niceHistInsrProdSeq = obj.niceHistSeq ;
            $('#btnAuthInsr').addClass('is-complete').html("휴대폰 인증 완료");
            isValidateNonEmptyStep4();
            return null;

        } else {

            var insrErrMsg= "본인인증 시 고객정보와 휴대폰 인증 고객정보가 일치하지 않습니다.";
            if(obj.RESULT_CODE == "STEP01"){ // STEP 오류
                insrErrMsg= obj.RESULT_MSG;
            }

            MCP.alert(insrErrMsg);
            return null;
        }

    }else if(pageObj.niceType == NICE_TYPE.RWD_PROD){ // 자급제 보상 서비스 휴대폰 인증

        var obj = insertNiceLog();

        if (obj.RESULT_CODE == "00000") {
            $("#isAuthRwd").val("1");
            $('#btnAuthRwd').addClass('is-complete').html("휴대폰 인증 완료");
            pageObj.niceHistRwdProdSeq = obj.niceHistSeq ;
            isValidateNonEmptyStep4();
            return null;
        } else {

            var rwdErrMsg= "본인인증 시 고객정보와 휴대폰 인증 고객정보가 일치하지 않습니다.";
            if(obj.RESULT_CODE == "STEP01"){ // STEP 오류
                rwdErrMsg= obj.RESULT_MSG;
            }

            MCP.alert(rwdErrMsg);
            return null;
        }
    }
}

var fnSetEventId = function(eventId){
    pageObj.eventId = eventId;
};

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

    agree.prop('checked', 'checked');
    // 자급제 보상 서비스 약관 동의 시 전체 약관 동의 체크를 위해 이벤트 호출
    if(agree.hasClass("_rwdAgree")){
        rwdTermsCheckCbFn();
    }

    // 안심보험 약관인 경우 STEP4의 전체동의 버튼 처리
    var insrCheckText = ["clauseInsrProdFlag", "clauseInsrProdFlag02", "clauseInsrProdFlag03"];
    if(insrCheckText.indexOf(pageObj.eventId) != -1) checkAllTermsStep4();
};

// ================================ START 자급제 보상 서비스 관련 함수 ================================
// 자급제 보상 서비스 개별 약관 동의 클릭 이벤트
function rwdTermsCheckCbFn(){
    var isAllCheck= true;
    $("._rwdAgree").each(function(){
        if (!$(this).is(':checked')) isAllCheck= false;
    });

    if (isAllCheck) $("#clauseRwdFlag").prop("checked", "checked");
    else $("#clauseRwdFlag").prop("checked", false);
    isValidateNonEmptyStep4();
}

// 자급제 보상서비스 증빙서류 변경 이벤트
var setThumb= function(){

    var target= $("#rwdFile1")[0];
    var fileImage= $("#rwdFile1").val();
    var fileBox = '';  // 등록 파일 html

    if(fileImage ==""){
        MCP.alert("첨부파일을 선택해 주세요.", function(){isValidateNonEmptyStep4();});
        return false;
    }

    // 파일 확장자 체크
    var ext = fileImage.split(".").pop().toLowerCase();
    if($.inArray(ext, ["jpg", "jpeg", "png", ,"gif"]) == -1) {
        $("#rwdFile1").val("");
        MCP.alert("첨부파일 확장자를 확인해주세요.<br/>가능한 파일 확장자는 jpg, gif, png입니다.",function(){
            isValidateNonEmptyStep4();
        });
        return false;
    }

    // 파일 크기 체크
    if(target.files[0].size/1024/1024 > 2){
        $("#rwdFile1").val("");
        MCP.alert("업로드 가능한 파일용량은 2MB 이내입니다.", function(){isValidateNonEmptyStep4();});
        return false;
    }

    // 이미지 파일명 체크
    var pattern =  /[\{\}\/?,;:|*~`!^\+<>@\#$%&\\\=\'\"]/gi;
    var fileName = fileImage.split('\\').pop().toLowerCase();
    if(pattern.test(fileName) ){
        $("#rwdFile1").val("");
        MCP.alert('파일명에 특수문자가 포함되어 있습니다.', function(){isValidateNonEmptyStep4();});
        return false;
    }

    // 신규 등록 파일 html 그리기
    fileBox += '<div class="c-form staged-image">';
    fileBox += '    <div class="c-file-image__item" style="width:220px;height:167px;border:1px solid #ddd;border-radius:0.5rem;background:rgba(0,0,0,0);left:16rem;">';
    fileBox += '        <div>등록완료</div>';
    fileBox += '        <button class="c-button">';
    fileBox += '            <span class="sr-only">삭제</span>';
    fileBox += '            <i class="c-icon c-icon--delete" aria-hidden="true"></i>';
    fileBox += '        </button>';
    fileBox += '    </div>';
    fileBox += '</div>';

    $(".staged_img-list").html(fileBox);

    // 자급제 보상서비스 증빙서류 삭제 클릭 이벤트
    $(".c-icon--delete").click(function(){
        $(this).parent().parent().parent().remove();
        $(".c-label__sub").text("첨부파일 0/1");
        $("#rwdFile1").val("");
        isValidateNonEmptyStep4();
    });

    if($(".staged_img-list .staged-image").length == 1){
        $(".c-label__sub").text("첨부파일 1/1");
    }

    isValidateNonEmptyStep4();
}

// 자급제 보상서비스 신청
var applyRwd= function(){

    // 1.자급제 보상서비스 선택 여부 확인
    if(pageObj.applDataForm.rwdProdCd == "") return false;

    // 2. 자급제 보상서비스 데이터 세팅
    var priceVal= ($("input[name=rwdBuyPric]").val() == "") ? "0" : $("input[name=rwdBuyPric]").val();

    var formData = new FormData();
    formData.append("imei", $("#rwdImei1").val());
    formData.append("imeiTwo", $("#rwdImei2").val());
    formData.append("buyPric", priceVal.replace(/[^0-9]/gi,""));
    formData.append("requestKey", pageObj.requestKey);
    formData.append("file", $("input[name='rwdFile1']")[0].files[0]);

    KTM.LoadingSpinner.show();
    ajaxCommon.getItemFileWithAsync({
            id : 'rwdRegNonMemberAjax'
            , cache : false
            , async : false
            , url : '/mypage/rwdRegNonMemberAjax.do'
            , data : formData
            , dataType : "json"
            ,errorCall : function (e) {
                KTM.LoadingSpinner.hide();
            }
        }
        ,function(jsonObj){
            KTM.LoadingSpinner.hide();
        });
}

// ================================ END 자급제 보상 서비스 관련 함수 ================================


/* 주소 setting */
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
    if (pageObj.addrFlag == "1") {
        $("#cstmrPost").val(zipNo);
        $("#cstmrAddr").val(roadAddrPart1);
        $("#cstmrAddrDtl").val(roadAddrPart2 + " " + addrDetail);
    } else {
        $("#dlvryPost").val(zipNo);
        $("#dlvryAddr").val(roadAddrPart1);
        $("#dlvryAddrDtl").val(roadAddrPart2 + " " +addrDetail);
    }
    isValidateNonEmptyStep2();
}


function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}


var isValidateNonEmptyStep1 = function() {
    var operType = $("#operType").val() ;
    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    var reqBuyType = $("#reqBuyType").val() ;
    var onOffType = $("#onOffType").val() ;

    validator.config={};
    validator.config['selSocCode'] = 'isNonEmpty';
    validator.config['cstmrType1'] = 'isNonEmpty';

    if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE){
        validator.config['changeCstmrMobileFn'] = 'isNonEmpty';
        validator.config['changeCstmrMobileMn'] = 'isNonEmpty';
        validator.config['changeCstmrMobileRn'] = 'isNonEmpty';
    }

    validator.config['cstmrName'] = 'isNonEmpty';
    if (cstmrType == CSTMR_TYPE.NA) {
        //내국인
        validator.config['cstmrNativeRrn01'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn02'] = 'isNonEmpty';

    } else if (cstmrType == CSTMR_TYPE.NM) {
        //청소년
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
        validator.config['cstmrForeignerRrn01'] = 'isNonEmpty';
        validator.config['cstmrForeignerRrn02'] = 'isNonEmpty';
    }

    validator.config['isAuth'] = 'isNonEmpty';

    if(operType == OPER_TYPE.MOVE_NUM ){
        validator.config['clauseMoveCode'] = 'isNonEmpty';
    }
    validator.config['clausePriCollectFlag'] = 'isNonEmpty';
    validator.config['clausePriOfferFlag'] = 'isNonEmpty';
    validator.config['clauseEssCollectFlag'] = 'isNonEmpty';
    validator.config['clauseFathFlag01'] = 'isNonEmpty';
    validator.config['clauseFathFlag02'] = 'isNonEmpty';
    // validator.config['clausePriTrustFlag'] = 'isNonEmpty';
    // validator.config['clauseConfidenceFlag'] = 'isNonEmpty';

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

    /*if (pageObj.clauseJehuRateCount > 0) {
        validator.config['clauseJehuFlag'] = 'isNonEmpty';
    }

    if (pageObj.clauseFinanceRateCount > 0) {
        validator.config['clauseFinanceFlag'] = 'isNonEmpty';
    }*/

    if (validator.validate(true)) {
        $('._btnNext').removeClass('is-disabled');
    } else {
        $('._btnNext').addClass('is-disabled');
    }
} ;

var isValidateNonEmptyStep2 = function() {
    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    var operType = $("#operType").val() ;

    validator.config={};
    var onOffLineUsim = $("input:radio[name='onOffLineUsim']:checked").val();
    if (onOffLineUsim == "offLine") {
        validator.config['reqUsimSn'] = 'isNonEmpty';
        validator.config['isUsimNumberCheck'] = 'isNonEmpty';
    }

    if(operType != OPER_TYPE.CHANGE && operType != OPER_TYPE.EXCHANGE ) {
        validator.config['cstmrMobileFn'] = 'isNonEmpty';
        validator.config['cstmrMobileMn'] = 'isNonEmpty';
        validator.config['cstmrMobileRn'] = 'isNonEmpty';

        validator.config['cstmrPost'] = 'isNonEmpty';
        validator.config['cstmrMail'] = 'isNonEmpty';

        //부정가입방지
        validator.config['selfInqryAgrmYnFlag'] = 'isNonEmpty';//본인조회동의
        validator.config['selfIssuExprDt'] = 'isIssuDate';//발급일자
        
        //안면인증 
        if($("#fathTrgYn").val() == "Y") {
            validator.config['isFathTxnRetv'] = 'isNonEmpty';
        }
        

        var selfCertType = $("input:radio[name=selfCertType]:checked").val();
        if (selfCertType=="02") {
            validator.config['driverSelfIssuNum1'] = 'isNonEmpty';
            validator.config['driverSelfIssuNum2'] = 'isNonEmpty';
        } else if (selfCertType=="04") {
            validator.config['selfIssuNum'] = 'isNonEmpty';//발급 만료일자
        } else if (selfCertType=="06") {
            validator.config['cstmrForeignerNation'] = 'isNonEmpty';
        }
    }

    //배송정보 확인
    if ($('#divDlvry').is(':visible') ) {
        validator.config['dlvryName'] = 'isNonEmpty';
        if ($("#dlvryTelFn").val() != "") {
            validator.config['dlvryTelFn'] = 'isNonEmpty';
            validator.config['dlvryTelMn'] = 'isNonEmpty';
            validator.config['dlvryTelRn'] = 'isNonEmpty';
        }
        validator.config['dlvryMobileFn'] = 'isNonEmpty';
        validator.config['dlvryMobileMn'] = 'isNonEmpty';
        validator.config['dlvryMobileRn'] = 'isNonEmpty';
    }

    if (validator.validate(true)) {
        //console.log("isValidateNonEmptyStep2 Call==>" );
        $('._btnNext').removeClass('is-disabled');
    } else {
        var errId = validator.getErrorId();
        // console.log("errId==>" + errId);
        $('._btnNext').addClass('is-disabled');
    }
} ;

var isValidateNonEmptyStep3 = function() {
    var operType = $("#operType").val() ;
    var onOffType = $("#onOffType").val() ;

    validator.config={};
    if (operType==OPER_TYPE.MOVE_NUM ) {
        //번호이동
        validator.config['moveCompany'] = 'isNonEmpty';
        validator.config['moveMobileFn'] = 'isNonEmpty';
        validator.config['moveMobileMn'] = 'isNonEmpty';
        validator.config['moveMobileRn'] = 'isNonEmpty';

    } else if(operType==OPER_TYPE.NEW){
        validator.config['reqWantNumber'] = 'isNonEmpty';
        validator.config['reqWantNumber2'] = 'isNonEmpty';
        validator.config['reqWantNumber3'] = 'isNonEmpty';
    }

    if (validator.validate(true)) {
        $('._btnNext').removeClass('is-disabled');
    } else {
        $('._btnNext').addClass('is-disabled');
    }
} ;

var isValidateNonEmptyStep4 = function() {
    validator.config={};
    //휴대폰 안심 서비스 처리
    if (pageObj.insrProdCd != ""){
        validator.config['clauseInsrProdFlag'] = 'isNonEmpty';
        validator.config['clauseInsrProdFlag02'] = 'isNonEmpty';
        validator.config['clauseInsrProdFlag03'] = 'isNonEmpty';
        validator.config['isAuthInsr'] = 'isNonEmpty';
    }

    if (validator.validate(true)) {
        $('._btnNext').removeClass('is-disabled');
    } else {
        $('._btnNext').addClass('is-disabled');
    }
} ;

var isValidateNonEmptyStep5 = function() {
    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    var reqPayType = $('input:radio[name=reqPayType]:checked').val();
    validator.config={};

    if ( reqPayType== "D") {
        //자동이체
        validator.config['reqBank'] = 'isNonEmpty';
        validator.config['reqAccountNumber'] = 'isNonEmpty';
        validator.config['isCheckAccount'] = 'isNonEmpty';
    } else if ( reqPayType== "C"  ) {
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
} ;

// ============ STEP START ============
function resetProdAuth(moduleType){
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
        })
}
// ============ STEP END ============


function getJehuInfo() {
    if ($("#jehuProdType").val() != null && $("#jehuProdType").val() != "" ){
        $("._clauseJehuFlag").show();
        if($("#clauseJehuFlagLabel").length > 0){
            $("#clauseJehuFlagLabel").remove();
        }

        var arr =[];
        arr.push("<label class='c-label' for='clauseJehuFlag' id='clauseJehuFlagLabel'>개인정보 제3자 제공 동의[" +$('#jehuProdName').val() + " 가입고객 필수 동의]\n");
        arr.push("<span class='u-co-gray'>(필수)</span>\n");
        arr.push("</label>\n");
        $("#clauseJehuFlag").after(arr.join(""));

    } else {
        $("._clauseJehuFlag").hide();
    }
    if ($("#jehuPartnerType").val() != null && $("#jehuPartnerType").val() != "" ){
        $("._clausePartnerOfferFlag").show();
        if($("#clausePartnerOfferFlagLabel").length > 0){
            $("#clausePartnerOfferFlagLabel").remove();
        }
        var arr =[];
        arr.push("<label class='c-label' for='clausePartnerOfferFlag' id='clausePartnerOfferFlagLabel'>개인정보 제3자 제공 동의[" +$('#jehuPartnerName').val() + " 가입고객 필수 동의]\n");
        arr.push("<span class='u-co-gray'>(필수)</span>\n");
        arr.push("</label>\n");
        $("#clausePartnerOfferFlag").after(arr.join(""));

    } else {
        $("._clausePartnerOfferFlag").hide();
    }
}


/** 안심보험 개별 약관 동의 시 전체약관 선택 버튼 처리 */
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

    $("#cstmrForeignerNation").val('');
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

var getMaskedRrn = function(front, back) {
    back = $.trim(back);
    return back.length === 1 ? front + back + "000000" : front + back;
};

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