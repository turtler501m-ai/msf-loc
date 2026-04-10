//addrObj

//요금제
//pageObj.clauseJehuRateCount  "reqWantNumberN"
//pageObj.clauseFinanceRateCount

VALIDATE_NOT_EMPTY_MSG.reqWantNumberN = "가입희망번호 번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.reqCnt = "가입희망번호 번호를 설정해 주세요.";
VALIDATE_NUMBER_MSG.reqWantNumberN = "가입희망번호 번호를 입력해 주세요.";
VALIDATE_COMPARE_MSG.selfIssuExprDt = "발급일자의 날짜 형식이 맞지 않습니다. 발급일자를 확인해 주세요.";

var stepInfo = {
    isInitStep2:true
    ,isInitStep3:true
    ,isInitStep4:true
}

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
    , checkCnt:0         //callback 호출 건수
    , prdtNm:""          //상품명
    , rateNm:""          //요금제명
    , rateObj:{}
    , modelSalePolicyCode : "" //init 정책정보
    , modelId:""
    , telAdvice:false   //전화상담 여부
    , prmtIdList:[]     // 사은품 프로모션 코드
    , prdtIdList:[]    // 사은품 제품ID
    , phoneObj:null
    , giftList:[]
    , custPoint:null
    , cardDcCd:""
    , rateObj:null
    , fnReqPreCheckCount:0
    , priceLimitObj:null
    , usimOrgnId:""
    , maxDcAmtInt:0
    , companyTel:""
    , cid :""
    // 자급제 보상 서비스 정보
    , niceHistRwdProdSeq: 0
    , rwdProdList: []
    , rwdProdCd: ""
    , rwdProdObj: null
    , moveCompanyList: []
    , osstResultCode:"99999" //코드에 따라 재시도 처리
    , moveCompanyGroup2:""
    , npExceptionList : null
    , additionList:[]
    , additionCatchCallKey:49  //캐치콜 플러스 키값
    , additionCatchCall:null
    , nationList: []
    , checkCaution:"N"
    , requestKeyTemp:0
}


var reqDlvryForm = {
    selfDlvryIdx:0
    , usimProdId:"01"
    , cstmrName:""
    , cstmrNativeRrn:""
    , onlineAuthDi:""
    , dlvryName:""
    , dlvryTelFn:""
    , dlvryTelMn:""
    , dlvryTelRn:""
    , dlvryPost:""
    , dlvryAddr:""
    , dlvryAddrDtl:""
    , dlvryMemo:""
    , bizOrgCd:""
    , dlvryKind:"01"  //바로배송
    , usimAmt:""
    , acceptTime:""
    , entY : ""
    , entX : ""
    , dlvryJibunAddr : ""
    , dlvryJibunAddrDtl : ""
    , homePw : ""
    , exitTitle : ""
    , onlineAuthCi:""
    , reqType:"02"  //신청서요청
    , certId :""
}
var fs9Succ = false;

$(document).ready(function(){



    pageObj.modelSalePolicyCode = $("modelSalePolicyCode").val();
    pageObj.modelId = $("modelId").val();
    pageObj.applDataForm = APPL_DATA_FORM();

    var swiperOutsideBanner = new Swiper("#swiperOutsideBanner", {
        spaceBetween : 10,
        observer : true,
        observeParents : true,
        centeredSlides : true,
        pagination : {
            el : ".swiper-pagination",
            type : "fraction",
        },
    });

    // 진행중 이벤트 스와이퍼
    var swiperCardBanner = new Swiper("#swiperCardBanner", {
        spaceBetween : 10,
        observer : true,
        observeParents : true,
    });


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



    $("input:checkbox[name=authCheckNone]").click(function(){
        var $thisObj = $(this);
        var onOffTypeInit = $("#onOffTypeInit").val() ;

        if ($("#isAuth").val() == "1") {
            MCP.alert("본인 인증을 완료 하였습니다. ");
            pageObj.telAdvice = false;
            $thisObj.prop("checked",false);
            return;
        }
        if ($thisObj.is (':checked')) {
            validator.config={};
            validator.config['cstmrType1'] = 'isNonEmpty';
            //고객유형 선택 외국인 선택 불가
            if (validator.validate()) {
                KTM.Confirm('전화상담가입 시, 가입 신청서가 우체국 계약등기로 발송되며 배송시까지 D+2일~3일 시간이 소요됩니다. (대면 수령 시 가입자 본인만 수령 가능)<br/><br/>※ 본인인증을 하지 않으실 경우 상담사에게 신분증 사본 발송이 필요합니다.', function () {

                    $("._insrProdForm").hide();//안심보험 hide
                    if($("._rwdProdForm").length > 0) $("._rwdProdForm").hide();//자급제 보상 서비스 hide

                    pageObj.telAdvice = true;
                    if (onOffTypeInit == "7") {
                        MCP.alert("해피콜 신청서로 전환합니다.",function (){
                            $("#btnOnline").trigger("click");
                        });
                    }

                    $("input:radio[name=onlineAuthType]").prop("disabled", true).prop("checked", false);
                    $("._onlineAuthType").hide();
                    // $("#ulOnlineAuthDesc").parent().hide();
                    $("input:radio[name=dlvryType][value='01']").trigger("click",{'isInit':false});  //택배

                    $thisObj.prop("checked", "checked");
                    $(".dlvryType01").show();
                    isValidateNonEmptyStep1();
                    this.close();
                    return ;
                });

                $thisObj.prop("checked", "");

            } else {
                var errId = validator.getErrorId();
                MCP.alert(validator.getErrorMsg(),function (){
                    $selectObj = $("#"+errId);
                    viewErrorMgs($selectObj, validator.getErrorMsg());
                });
                $thisObj.prop("checked", "");
            }
        } else {
            $("._insrProdForm").show();
            if($("._rwdProdForm").length > 0) $("._rwdProdForm").show();//자급제 보상 서비스 show

            pageObj.telAdvice = false;
            if (onOffTypeInit == "7") {
                MCP.alert("셀프개통 신청서로 전환합니다.",function (){
                    changeSelf();
                });
            }

            $("input:radio[name=onlineAuthType]").prop("disabled", false);
        }
        isValidateNonEmptyStep1();
    });

    // 본인인증 전 추가 유효성 검사 > niceAuth.js 참고
    simpleAuthvalidation= function(){
        var operType = $("#operType").val() ;
        var onOffType = $("#onOffType").val() ;
        var cstmrType = $("input:radio[name=cstmrType]:checked").val();

        //셀프개통 신규가입 신청서 내 ‘휴대폰 인증‘ 추가
        if(operType == OPER_TYPE.NEW && onOffType == "7"){
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
                ,subscriberNo:$.trim($("#changeCstmrMobile").val())
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

                    $('#cstmrMobile').val($.trim($("#changeCstmrMobile").val()));
                    $('#cstmrName').prop('readonly', true);

                    $("#changeCstmrMobile").prop('readonly', true);
                    $('#cstmrMobile').prop('readonly', true);


                    $("#cstmrName").parent().addClass('has-value');
                    $("#cstmrNativeRrn01").parent().addClass('has-value');
                    $("#cstmrForeignerRrn01").parent().addClass('has-value');
                } else if ("0010" == jsonObj.RESULT_CODE) { // 인가된 사용자 검증
                    rtn = false;
                    MCP.alert('<p class="u-mt--12"><strong class="u-fw--bold">회원정보와 본인인증 정보가<br>일치하지 않습니다.</strong></p><p class="u-mt--12">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</p>');
                } else if ("0004" == jsonObj.RESULT_CODE) {
                    rtn = false;
                    MCP.alert("약정기간이 6개월 이상 남아있어 온라인으로 기기변경 신청이 불가합니다.<br>기기변경을 원하실 경우 고객센터(1899-5000)로 신청을 부탁드립니다. <br>확인버튼을 누르실 경우 휴대폰 화면으로 이동합니다.", function (){
                        location.href = "/m/product/phone/phoneList.do";
                    });
                } else {
                    rtn = false;
                    MCP.alert(jsonObj.RESULT_DESC);
                }
            });
        }
        if(!rtn) {
            return false;
        }

        // 나이 체크
        var age = 0;
        if (cstmrType == CSTMR_TYPE.FN) {
            age = getAge($("#cstmrForeignerRrn01").val()+$("#cstmrForeignerRrn02").val());
        } else {
            age = getAge($("#cstmrNativeRrn01").val()+$("#cstmrNativeRrn02").val());
        }

        if (!checkPriceLimitForAge(age)) { // 유효성검사 최종 실패
            MCP.alert(pageObj.priceLimitObj.dtlCdDesc);
            return false;
        }else{ // 유효성검사 최종 성공
            return true;
        }
    } // end of simpleAuthvalidation= function(){----------------------



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

            openPage('outLink','/nice/popNiceAuth.do?sAuthType=M&mType=Mobile','')
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

        // ======================= 휴대폰 인증 전 유효성 검사 START
        // 에러 문구 제거
        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        if ($("#isSmsAuth").val() == "1") {
            MCP.alert("휴대폰 인증을 완료 하였습니다. ");
            return false;
        }

        // 1. 고객유형, 이름, 주민번호(내국인) 필수
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
        if (!checkPriceLimitForAge(age)) { // 유효성검사 최종 실패
            MCP.alert(pageObj.priceLimitObj.dtlCdDesc);
            return false;
        }

        // 3. 정보 확인
        var nicePinChk= false;
        var varData = ajaxCommon.getSerializedData({
            name: $("#cstmrName").val()
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
                if (AJAX_RESULT_CODE.SUCCESS == jsonObj.returnCode) {
                    nicePinChk= true;
                } else if ('0010' == jsonObj.returnCode) { // 인가된 사용자 검증
                    MCP.alert('<p class="u-mt--12"><strong class="u-fw--bold">회원정보와 본인인증 정보가<br>일치하지 않습니다.</strong></p><p class="u-mt--12">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</p>');
                } else {
                    MCP.alert("본인 인증 요청 중 오류가 발생했습니다.<br>다시 시도 바랍니다.");
                }
            }); // end of ajax-----------

        if(!nicePinChk){
            return false;
        }

        // ======================= 휴대폰 인증 전 유효성 검사 END

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

        //sAuthType M: 핸드폰, C: 신용카드, X: 공인인증서
        openPage('outLink','/nice/popNiceAuth.do?sAuthType=M&mType=Mobile','');
        pageObj.niceType = NICE_TYPE.SMS_AUTH ;
        return ;
    });

    $("input:radio[name=cstmrType]").click(function(){
        var thisVal = $(this).val();
        var operType = $("#operType").val() ;

        var descVal = $(this).attr("desc");
        if ($("#isAuth").val() == "1") {
            MCP.alert("본인 인증을 완료 하였습니다. ");
            return false;
        }


        if (thisVal == CSTMR_TYPE.NM) {
            $("._isTeen").show();
            $("._isForeigner").hide();
            $(".c-form__text").remove();
            $(".has-error").removeClass("has-error");
            $("._isDefaultText").hide();
            $("._isDefault").show();

            $("#inpNameCertiTitle").html("가입자 정보(미성년자)");
            $("#spOnlineAuthType").html("본인인증방법 선택(법정대리인)");
            $("#radIdCardHead").html("신분증 확인(법정대리인)");
            if (operType != OPER_TYPE.CHANGE && operType != OPER_TYPE.EXCHANGE) {
                $('#cstmrType1').attr('disabled', 'disabled');
                $('#cstmrType3').attr('disabled', 'disabled');
            }

            //청소년 요금제 가능 여부
            if (!checkPriceLimitForAge(12)) {
                MCP.alert("청소년이 신청 불가능한 요금제입니다. ");
                $(this).prop("checked" , false);
                return false;
            }
        } else if (thisVal == CSTMR_TYPE.FN) {
            $("._isTeen").hide();
            $("._isForeigner").show();
            $(".c-form__text").remove();
            $(".has-error").removeClass("has-error");
            $("._isDefaultText").hide();
            $("._isDefault").hide();

            $("#inpNameCertiTitle").html("가입자 정보");
            $("#spOnlineAuthType").html("본인인증 방법 선택");
            $("#radIdCardHead").html("신분증 확인");
            if (operType != OPER_TYPE.CHANGE && operType != OPER_TYPE.EXCHANGE) {
                $('#cstmrType1').attr('disabled', 'disabled');
                $('#cstmrType2').attr('disabled', 'disabled');
            }

            $('input[name="selfCertType"]').prop('disabled', true);
            $('#selfCertType6').prop('disabled', false);
            $('#selfCertType6').prop('checked', true);
            $('#selfCertType6').click();

            //전화상담 초기화 처리
            if (pageObj.telAdvice ) {
                pageObj.telAdvice = false;
                $("input:checkbox[name=authCheckNone]").trigger("click");
            }
        } else {
            $("._isTeen").hide();
            $("._isForeigner").hide();
            $(".c-form__text").remove();
            $(".has-error").removeClass("has-error");
            $("._isDefaultText").show();
            $("._isDefault").show();

            $("#inpNameCertiTitle").html("가입자 정보");
            $("#spOnlineAuthType").html("본인인증 방법 선택");
            $("#radIdCardHead").html("신분증 확인");
            if (operType != OPER_TYPE.CHANGE && operType != OPER_TYPE.EXCHANGE) {
                $('#cstmrType2').attr('disabled', 'disabled');
                $('#cstmrType3').attr('disabled', 'disabled');
            }
            $('#selfCertType6').attr('disabled', 'disabled');
        }

        $("#ddCstmrType").html(descVal);
        isValidateNonEmptyStep1();
    });

    $("#btnStplAllCheck").click(function(){
        $("._agree").prop("checked", $(this).is(':checked'));
        isValidateNonEmptyStep1();
        applyCheckedWrapList();
    });

    getJehuInfo();
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
        if ($(this).is(':checked')) {
            $("._agreeInsr").prop("checked", "checked");
            isValidateNonEmptyStep4();
        } else {
            $("._agreeInsr").prop("checked", false);
            isValidateNonEmptyStep4();
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
        isValidateNonEmptyStep4();
    });

    $(document).on('click', '.c-label', function(event) {
        if ($(event.target).hasClass('_allcheck')) {
            $('#btnStplAllCheck').click();
        }
    });


    $("#minorAgentRelation").change(function(){
        isValidateNonEmptyStep1();
    });

    $("input[name='onOffLineUsim']").change(function(event, paramObj) {
        var thisVal = $(this).val();

        if (thisVal == "onLine" && $("#isUsimNumberCheck").val() == "1") {
            MCP.alert("유심번호 유효성 체크 하였습니다. ");
            $(this).prop('checked', false);
            $("#onOffLineUsim01").prop('checked', true);
            return false ;
        }

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");


        var isInit = false;
        var prdtSctnCd = $("#prdtSctnCd").val();
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

            //pageObj.applDataForm.sesplsYn 자급제
            if (REQ_BUY_TYPE.USIM == reqBuyType && pageObj.applDataForm.sesplsYn != "Y" ) {
                $("#divDlvry").hide();
            } else {
                $("#divDlvry").show();
            }

            $("input:radio[name=dlvryType][value='01']").trigger("click");

            $("#usimPriceTxt").html("0 원");
            arr.push("<li class='c-text-list__item'>유심 카드를 보유한 고객님께서는 유심번호 19자리를 입력해 주세요.</li>\n");
            arr.push("<li class='c-text-list__item'>유심 보유를 선택하실 경우 유심비용이 미청구 됩니다.</li>\n");
        } else{
            //유심미보유
            $("#reqUsimSn").prop("disabled", true).val("");
            $("._offLineUsim").hide();
            $("._onLineUsim").show();
            $("#divDlvry").show();

//            if ( REQ_BUY_TYPE.USIM == reqBuyType ) {
//                arr.push("<li class='c-text-list__item'>모바일 NFC 기능(교통카드 등)의 사용을 원하실 경우 NFC 유심을 선택해 주세요.</li>\n");
//            }
            arr.push("<li class='c-text-list__item'>해당 유심은 모바일 NFC 기능을 지원합니다.</li>\n");
            arr.push("<li class='c-text-list__item'>유심비용은 배송방식에 따라 선결제 또는 후청구됩니다.</li>\n");
            $("input:radio[name=radUsimProd]").eq(0).trigger("click");
        }

        $("#ulOnOffLineUsimDesc").empty();
        $("#ulOnOffLineUsimDesc").append(arr.join(''));
        isValidateNonEmptyStep2();
    });

    $("input[name='radUsimProd']").click(function() {
        var thisVal = $(this).val();
        var usimNm = $(this).attr("usimNm");
        var usimPrice = $(this).attr("usimPrice");
        showJoinUsimPrice();
        setDlvryType(true);

        var arr =[];

        if(thisVal == '06'){
            arr.push("<li class='c-text-list__item'>해당 유심은 모바일 NFC 기능을 지원합니다.</li>\n");
        }else{
            arr.push("<li class='c-text-list__item'>해당 유심은 모바일 NFC 기능을 지원하지 않습니다.</li>\n");
        }

        arr.push("<li class='c-text-list__item'>유심비용은 배송방식에 따라 선결제 또는 후청구됩니다.</li>\n");

        $("#ulOnOffLineUsimDesc").empty();
        $("#ulOnOffLineUsimDesc").append(arr.join(''));

    });

    $("input:radio[name=moveCompanyGroup1]").click(function(){
        if ($("#isNpPreCheck").val() == "1") {
            return false;
        }

        var thisVal = $(this).val();
        $("#moveCompany > option").remove();
        $("#moveCompanyGroup2 option").eq(0).prop("selected",true);

        if ("-1" == thisVal) {
            $('#moveCompany').append('<option value="">알뜰폰 사업자</option>');
            $('#divMoveCompanyGroup2').show();

            if($("#onOffType").val() == "7"){
                $("#btnNext").html("사전동의 요청");
                $("#btnNext2").html("사전동의 요청");
                $("#npDesc").show();
            }

        } else {
            var mpCode = $(this).attr("mpCode");
            var companyNm = $(this).attr("companyNm");
            var companyTel = $(this).attr("companyTel");

            $('#moveCompany').append('<option value="'+thisVal+'" mpCode="'+mpCode+'" companyNm="'+companyNm+'" companyTel="'+companyTel+'" >'+companyNm+'</option>').addClass("has-value");
            $('#divMoveCompanyGroup2').hide();

            // NP 예외 통신사 확인
            if($("#onOffType").val() == "7"){
                if(isNpExceptionCompany(mpCode)){
                    $("#btnNext").html("개통사전체크확인");
                    $("#btnNext2").html("개통사전체크확인");
                    $("#npDesc").hide();
                }else{
                    $("#btnNext").html("사전동의 요청");
                    $("#btnNext2").html("사전동의 요청");
                    $("#npDesc").show();
                }
            }
        }
        isValidateNonEmptyStep3();
    });

    $("#moveCompanyGroup2").change(function(){
        if ($("#isNpPreCheck").val() == "1") {
            $("#moveCompanyGroup2").val(pageObj.moveCompanyGroup2);
            return false;
        }
        var thisVal = $(this).val();
        pageObj.moveCompanyGroup2= thisVal;

        $("#moveCompany > option").remove();
        $('#moveCompany').append('<option value="">알뜰폰 사업자</option>');

        if($("#onOffType").val() == "7"){
            $("#btnNext").html("사전동의 요청");
            $("#btnNext2").html("사전동의 요청");
            $("#npDesc").show();
        }

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

    //배송유형 선택 표현
    var setDlvryType = function (isInit) {
        var cstmrType = $("input:radio[name=cstmrType]:checked").val();
        var radUsimProd = $("input[name='radUsimProd']:checked").val();
        var reqBuyType = $("#reqBuyType").val() ;
        //var radUsimProd = "05";

        $("#divDlvryType").empty();

        $("#divDlvryTop").show();
        //if (radUsimProd == "05") {
        pageObj.applDataForm.usimKindsCd = "";
        if(radUsimProd == "06"){
            pageObj.applDataForm.usimKindsCd = "08";
        }
        //일반 USIM
        var arr =[];
        arr.push("<input class='c-radio dlvryType01' id='dlvryType01' type='radio' name='dlvryType' value='01'>\n");
        arr.push("<label class='c-label dlvryType01' for='dlvryType01'>택배</label>\n");
        if ("Y" != pageObj.applDataForm.sesplsYn  && REQ_BUY_TYPE.USIM == reqBuyType &&  cstmrType == CSTMR_TYPE.NA) {
            arr.push("<input class='c-radio dlvryType02' id='dlvryType02' type='radio' name='dlvryType' value='02' >\n");
            arr.push("<label class='c-label dlvryType02' for='dlvryType02'>바로배송(당일 퀵)</label>\n");
        }

        $("#divDlvryType").html(arr.join(''));
        if ("Y" != pageObj.applDataForm.sesplsYn  && REQ_BUY_TYPE.USIM == reqBuyType &&  cstmrType == CSTMR_TYPE.NA) {

            var radUsimProd = $("input[name='radUsimProd']:checked").data("usimProdCode");// 유심 TYPE  $("input[name='radUsimProd']:checked").data(usimProdCode);/
            var varData = ajaxCommon.getSerializedData({
                usimProdType: radUsimProd
                , type: "01"
            });

            //바로배송 유무
            ajaxCommon.getItemNoLoading({
                id:'accountCheck'
                ,cache:false
                ,url:'/appform/isSelfDlvryAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                if (jsonObj && jsonObj.IS_NOW_DLVRY) {
                    $("input:radio[name=dlvryType][value='02']").trigger("click",{'isInit':isInit});  //바로배송 으로.. 기본값..
                } else {
                    $("input:radio[name=dlvryType][value='01']").trigger("click",{'isInit':isInit});  //택배
                }

                if (jsonObj.IS_DLVRY || pageObj.telAdvice) {
                    $(".dlvryType01").show();
                } else {
                    $(".dlvryType01").hide();
                }

                if (jsonObj.IS_NOW_DLVRY ) {
                    $(".dlvryType02").show();
                } else {
                    $(".dlvryType02").hide();
                    $(".dlvryType01").show();
                    var alertMsg = "";

                    if (jsonObj && jsonObj.isTimeMsg != "") {
                        alertMsg = jsonObj.isTimeMsg ;
                    }
                    if (alertMsg != "") {
                        MCP.alert(alertMsg);
                    }
                }

            });

        } else {
            $("input:radio[name=dlvryType][value='01']").trigger("click",{'isInit':isInit});  //택배
        }
        /*} else{
            pageObj.applDataForm.usimKindsCd = "08";
            //NFC USIM
            var arr =[];
            arr.push("<input class='c-radio' id='dlvryType01' type='radio' name='dlvryType' value='01' >\n");
            arr.push("<label class='c-label' for='dlvryType01'>택배</label>\n");
            if ("Y" != pageObj.applDataForm.sesplsYn  ) {
                arr.push("<input class='c-radio' id='dlvryType03' type='radio' name='dlvryType' value='03'>\n");
                arr.push("<label class='c-label' for='dlvryType03'>당일배송(서울지역 한정)</label>\n");
            }
            $("#divDlvryType").html(arr.join(''));
            $("input:radio[name=dlvryType][value='01']").trigger("click",{'isInit':isInit});  //택배
        }*/

    };

    $(document).on('click', 'input:radio[name=dlvryType]', function (event, paramObj) {

        var isInit = false;
        if (paramObj != undefined ) {
            isInit = paramObj.isInit;
        }

        var thisval = $(this).val();

        if (thisval == "02" ) {
            if (pageObj.telAdvice) {
                if (!isInit) {
                    MCP.alert("전화상담은 바로배송 신청할 수 없습니다. ");
                }
                $("input:radio[name=dlvryType][value='01']").trigger("click");
                return false;
            }

            var radUsimProd = $("input[name='radUsimProd']:checked").data("usimProdCode");// 유심 TYPE  $("input[name='radUsimProd']:checked").data(usimProdCode);/
            var varData = ajaxCommon.getSerializedData({
                usimProdType: radUsimProd
            });

            ajaxCommon.getItem({
                    id:'accountCheck'
                    ,cache:false
                    ,url:'/appform/isSelfDlvryAjax.do'
                    ,data: varData
                    ,dataType:"json"
                }
                ,function(jsonObj){
                    if (jsonObj && jsonObj.IS_NOW_DLVRY) {
                        var $btnAddrOjb = $("._btnAddr[addrFlag='2']");
                        $btnAddrOjb.attr("addrFlag","3");
                        $btnAddrOjb.text("바로배송 지역 조회");

                        $("#dlvryPost").val("");
                        $("#dlvryAddr").val("");
                        $("#dlvryAddrDtl").val("");
                        $("#bizOrgCd").val("");
                        $("#inEqualCheck").prop("checked", false);
                        $("._isNotNowDlvry").hide();
                        $("._isNowDlvry").show();
                        setTimeout(function(){
                            $("#exitPw").trigger("change");
                        }, 500);


                    } else {
                        if (!isInit) {
                            var isTimeMsg = "바로배송 운영 시간이 아닙니다.<br/>선택 할수 없습니다.";
                            if (jsonObj && jsonObj.isTimeMsg != "") {
                                isTimeMsg = jsonObj.isTimeMsg ;
                            }
                            MCP.alert(isTimeMsg);
                        }
                        $("input:radio[name=dlvryType][value='01']").trigger("click");
                    }
                });

        } else if (thisval == "03" ){
            var $btnAddrOjb = $("._btnAddr[addrFlag='2']");
            $btnAddrOjb.attr("addrFlag","4");
            $btnAddrOjb.text("당일배송 지역 조회");

            $("#dlvryPost").val("");
            $("#dlvryAddr").val("");
            $("#dlvryAddrDtl").val("");

            $("._isNotNowDlvry").show();
            $("._isNowDlvry").hide();

        } else {
            var $btnAddrOjb = $("._btnAddr[addrFlag='3']");
            if ($btnAddrOjb.length == 0) {
                $btnAddrOjb = $("._btnAddr[addrFlag='4']");
            }
            $btnAddrOjb.attr("addrFlag","2");
            $btnAddrOjb.text("우편번호 찾기");

            $("._isNotNowDlvry").show();
            $("._isNowDlvry").hide();
        }
        isValidateNonEmptyStep2();
    });

    // 바로배송 배송시 요구사항
    $("#orderReqMsg").change(function(){

        var val = $(this).val();
        if(val=="4"){
            $("._isNowDlvry2").show();
        } else {
            $("._isNowDlvry2").hide();
        }
    });

    // 공동현관 출입방법
    $("#exitPw").change(function(){
        $("#homePw").val("");
        var exitPw = $(this).val();
        if(exitPw=="1"){
            $("#homePw").show();
        } else {
            $("#homePw").hide();
        }
    });

    //월 단말 요금 포인트 처리
    //휴대폰..
    var getInstMnthAmt = function() {
        var usePoint = pageObj.applDataForm.usePoint;
        var payMnthAmt = pageObj.rateObj.payMnthAmt ; //월납부 단말요금
        if (usePoint == 0) {
            return payMnthAmt ;
        } else {
            return payMnthAmt - Math.floor(usePoint / pageObj.rateObj.modelMonthly);
        }
    }

    ///** 월납부 통신요금+월단말요금 */
    var getMnthChargeAmt = function() {
        var modelMonthlyVal = $("#modelMonthly").val();
        if (modelMonthlyVal == "") {
            modelMonthlyVal ="0" ;
        }

        if ("0" == modelMonthlyVal) {
            return pageObj.rateObj.payMnthChargeAmt ;
        } else {
            return pageObj.rateObj.payMnthChargeAmt +getInstMnthAmt()   ;
            //return pageObj.rateObj.payMnthChargeAmt +getInstMnthAmt()   ;
        }

    }


    var getPaymentHndsetPrice = function() {
        var paymentHndsetPrice =0;
        var hndsetSalePrice = pageObj.applDataForm.hndsetSalePrice ;
        var usePoint = pageObj.applDataForm.usePoint;
        var cardDcAmt = pageObj.applDataForm.cardDcAmt ;

        paymentHndsetPrice = hndsetSalePrice - usePoint - getCardDcAmt();

        if (paymentHndsetPrice > 0) {
            return paymentHndsetPrice;
        } else {
            return 0 ;
        }
    }

    var getCardDcAmt = function() {
        var hndsetSalePrice = pageObj.applDataForm.hndsetSalePrice ;
        var cardDcDivCd = pageObj.applDataForm.cardDcDivCd ;
        var cardDcAmt = pageObj.applDataForm.cardDcAmt ;


        if (cardDcDivCd == "WON") {
            return cardDcAmt ;
        } else {
            var rtnVal = hndsetSalePrice * cardDcAmt * 0.01 ;
            if(pageObj.maxDcAmtInt > 0 && pageObj.maxDcAmtInt < rtnVal) {
                return pageObj.maxDcAmtInt;
            } else {
                return Math.floor(rtnVal);
            }
        }
    }

    var viewPhoneChargeInfo = function() {
        var userDivision =  $("#userDivision").val();

        if (pageObj.rateObj== null) {
            return ;
        }

        var settlWayCd = $("#settlWayCd").val();
        var arr =[];

        if ("01" == settlWayCd) {
            arr.push("<dl class='c-addition c-addition--type2'>\n");
            arr.push("    <dt>단말기 실구매가</dt>\n");
            arr.push("    <dd class='u-ta-right'>"+numberWithCommas(pageObj.rateObj.instAmt)+" 원</dd>\n");
            arr.push("</dl>\n");
            arr.push("<dl class='c-addition c-addition--type2'>\n");
            arr.push("    <dt>단말기 잔여 금액</dt>\n");
            arr.push("    <dd class='u-ta-right'>"+numberWithCommas(pageObj.rateObj.instAmt)+" 원</dd>\n");
            arr.push("</dl>\n");
            arr.push("<dl class='c-addition c-addition--type1 u-mt--16'>\n");
            arr.push("    <dt>일시납부 금액</dt>\n");
            arr.push("    <dd class='u-ta-right'>\n");
            arr.push("    <b>"+numberWithCommas(pageObj.rateObj.instAmt)+"</b>원\n");
            arr.push("    </dd>\n");
            arr.push("</dl>\n");
        } else {
            arr.push("<dl class='c-addition c-addition--type2'>\n");
            arr.push("    <dt>단말기 실구매가</dt>\n");
            arr.push("    <dd class='u-ta-right'>"+numberWithCommas(pageObj.rateObj.instAmt)+" 원</dd>\n");
            arr.push("</dl>\n");
            /*if ("01" == userDivision) {
                arr.push("<dl class='c-addition c-addition--type2'>\n");
                arr.push("    <dt>사용포인트</dt>\n");
                arr.push("    <dd class='u-ta-right'>"+numberWithCommas(pageObj.applDataForm.usePoint)+ " P</dd>\n");
                arr.push("</dl>\n");
            }*/
            arr.push("<dl class='c-addition c-addition--type2'>\n");
            arr.push("    <dt>단말기 잔여 금액</dt>\n");
            arr.push("    <dd class='u-ta-right'>"+numberWithCommas(pageObj.rateObj.instAmt-pageObj.applDataForm.usePoint)+" 원</dd>\n");
            arr.push("</dl>\n");
            arr.push("<dl class='c-addition c-addition--type2'>\n");
            arr.push("    <dt>할부기간</dt>\n");
            arr.push("    <dd class='u-ta-right'>"+$("#modelMonthly").val()+" 개월</dd>\n");
            arr.push("</dl>\n");
            arr.push("<dl class='c-addition c-addition--type1 u-mt--16'>\n");
            arr.push("    <dt>단말기 할부 월 납부금액</dt>\n");
            arr.push("    <dd class='u-ta-right'>\n");
            arr.push("    <b>"+numberWithCommas(getInstMnthAmt())+"</b>원\n");
            arr.push("    </dd>\n");
            arr.push("</dl>\n");
        }


        $("#usePointTxt").html(numberMinusWithCommas(pageObj.applDataForm.usePoint));
        $("#payMnthAmtTxt").html(numberWithCommas(getInstMnthAmt()));
        $("#paySumMnthTxt").html(numberWithCommas(getMnthChargeAmt()));
        $("#paySumMnthTxt2").html(numberWithCommas(getMnthChargeAmt()));

        $("#divChargeInfo").html(arr.join(""));
    }

    var viewSelfPhoneChargeInfo = function() {
        var userDivision =  $("#userDivision").val();
        var reqBuyType = $("#reqBuyType").val() ;
        if(REQ_BUY_TYPE.PHONE == reqBuyType) {
            viewPhoneChargeInfo();
            return;
        }

        if (pageObj.phoneObj == null) {
            return ;
        }
        var arr =[];

        arr.push("<dl class='c-addition c-addition--type2'>\n");
        arr.push("    <dt>단말기 실구매가</dt>\n");
        arr.push("    <dd class='u-ta-right'>"+numberWithCommas(pageObj.applDataForm.hndsetSalePrice)+ " 원</dd>\n");
        arr.push("</dl>\n");
        /*if ("01" == userDivision) {
          arr.push("<dl class='c-addition c-addition--type2'>\n");
          arr.push("    <dt>사용 포인트</dt>\n");
          arr.push("    <dd class='u-ta-right'>"+numberWithCommas(pageObj.applDataForm.usePoint)+ " P</dd>\n");
          arr.push("</dl>\n");
        }*/
        arr.push("<dl class='c-addition c-addition--type2'>\n");
        arr.push("    <dt>카드 즉시할인</dt>\n");
        if (0 < pageObj.applDataForm.cardDcAmt) {
            arr.push("    <dd class='u-ta-right'>"+numberWithCommas(getCardDcAmt())+ " 원</dd>\n");
        } else {
            arr.push("    <dd class='u-ta-right'>0 원</dd>\n");
        }
        arr.push("</dl>\n");
        arr.push("<dl class='c-addition c-addition--type2'>\n");
        arr.push("    <dt>단말기 잔여 금액</dt>\n");
        arr.push("    <dd class='u-ta-right'>"+numberWithCommas(getPaymentHndsetPrice())+ " 원</dd>\n");
        arr.push("</dl>\n");


        $("#divChargeInfo").html(arr.join(""));

        $("._paymentHndsetPrice").html(numberWithCommas(getPaymentHndsetPrice()));
        $("#hndsetSalePriceTxt").html(numberWithCommas(pageObj.applDataForm.hndsetSalePrice) + " 원");
        $("#usePointTxt").html(numberMinusWithCommas(pageObj.applDataForm.usePoint) + " 원");

        var cardDcDivCd = pageObj.applDataForm.cardDcDivCd ;
        $("#cardDcAmtTxt").html(numberMinusWithCommas(getCardDcAmt()) + " 원");


    }

    $("#usePointSvcCntrNo").change(function(){
        var thisVal = $(this).val();

        if (thisVal == null || thisVal == "") {
            return false;
        }

        var varData = ajaxCommon.getSerializedData({
            cntrNo:thisVal
        });

        ajaxCommon.getItem({
                id:'myPointInfoAjax'
                ,cache:false
                ,url:'/mypage/myPointInfoAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                if (jsonObj.result == "00001") {
                    pageObj.custPoint =  jsonObj.custPoint ;
                    $("#totRemainPointTxt").html(numberWithCommas(pageObj.custPoint.totRemainPoint) +" P");
                    $("#usePoint").val("");
                } else {
                    pageObj.custPoint =  null ;
                    $("#totRemainPointTxt").html("0 P");
                    $("#usePoint").val("");
                }

                //초기화
                pageObj.applDataForm.usePoint = 0 ;
                pageObj.applDataForm.usePointSvcCntrNo = "";

                viewSelfPhoneChargeInfo();

            });
    });

    if ($("#usePointSvcCntrNo").length ) {
        $("#usePointSvcCntrNo").trigger("change") ;
    }

    $("input:radio[name=radPayType]").click(function(){
        var thisVal = $(this).val();
        if (thisVal == "1") {
            $("._cardDcDiv").hide();
        } else {
            $("._cardDcDiv").show();
        }
        $("#cardDcCd").val("").trigger("change");
    })


    $("#cardDcCd").change(function(){
        var thisVal = $(this).val();

        if (thisVal != "" && thisVal != "etc" ) {
            var cardNm =$("#cardDcCd option:selected").attr("cardNm");
            var pstngStartDate = $("#cardDcCd option:selected").attr("pstngStartDate");
            var pstngEndDate = $("#cardDcCd option:selected").attr("pstngEndDate");
            var cardDcAmt= $("#cardDcCd option:selected").attr("cardDcAmt");
            var cardDcDivCd=$("#cardDcCd option:selected").attr("cardDcDivCd");
            var maxDcAmt=$("#cardDcCd option:selected").attr("maxDcAmt");
            var descTxt = cardNm + "카드 즉시할인 프로모션 기간 "+ pstngStartDate +" ~ " + pstngEndDate;
            var cardDcAmtInt = parseInt(cardDcAmt,10) ;
            var maxDcAmtInt = parseInt(maxDcAmt,10) ;
            //var asIsCardDcAmt = pageObj.applDataForm.cardDcAmt;

            pageObj.applDataForm.cardDcAmt = 0 ;
            var paymentHndsetPrice = getPaymentHndsetPrice();

            if (paymentHndsetPrice < cardDcAmtInt) {
                MCP.alert("단말기 가격보단 할인 금액이 높습니다. ");
                $("#cardDcCd").val(pageObj.cardDcCd);
                $("#cardDcCd").trigger("change");
                return false ;
            }

            pageObj.applDataForm.cardDcCd = thisVal;
            pageObj.applDataForm.cardDcAmt = cardDcAmtInt ;
            pageObj.applDataForm.cardDcDivCd = cardDcDivCd;
            pageObj.maxDcAmtInt = 0;

            var realCardDcAmt = getCardDcAmt();
            if (realCardDcAmt > maxDcAmtInt ) {
                MCP.alert("최대 "+numberWithCommas(maxDcAmtInt)+"원 할인 적용 됩니다.");
                pageObj.maxDcAmtInt = maxDcAmtInt;
            }

            $("#cardDescLi").html(descTxt);

        } else  {
            $("#cardDescLi").html("즉시할인 카드 선택하세요");

            pageObj.applDataForm.cardDcCd = "";
            pageObj.applDataForm.cardDcAmt = 0 ;
            pageObj.applDataForm.cardDcDivCd = "";
        }
        pageObj.cardDcCd = thisVal ;
        viewSelfPhoneChargeInfo();

    });

    $("#usePoint").blur(function(){
        var thisVal = $(this).val() ;
        var thisValInt = parseInt(thisVal,10) ;
        if (isNaN(thisVal)) {
            return false ;
        }

        if ( pageObj.custPoint == null) {
            MCP.alert("보유한 포인트가 없습니다.");
            $("#usePoint").val("");
            return false;
        }

        if (thisVal == 0) {
            $("#usePoint").val("");
            return ;
        }

        if (thisVal < 1000) {
            MCP.alert("kt M모바일 포인트는 최소 1,000 포인트 이상 사용 가능합니다 ");
            $("#usePoint").val("");
            return false;
        }


        if (thisVal > getHndsetPrice()) {
            MCP.alert("단말기 실구매 가격보단 낮게 입력하세요");
            $("#usePoint").val("");
            return false;
        }

        if (thisVal > pageObj.custPoint.totRemainPoint) {
            MCP.alert("보유한 포인트만큼 입력 하시기 바랍니다.");
            $("#usePoint").val("");
            return false;
        }

        //kt M모바일 포인트는 최소 1,000 포인트 이상, 500포인트 단위로 기입하여 사용 가능합니다
        var subValInt = parseInt(thisVal.slice(-3,thisVal.length),10) ;

        //천이하 버림
        thisValInt = Math.floor(thisValInt / 1000) * 1000;
        if (subValInt >= 500) {
            thisValInt = thisValInt + 500;
        }

        $("#usePoint").val(thisValInt);
    });

    $('#btnPointApply').click(function(){
        var usePoint = $("#usePoint").val();

        if (usePoint == "") {
            pageObj.applDataForm.usePoint = 0 ;
            pageObj.applDataForm.usePointSvcCntrNo = "";
        } else {
            pageObj.applDataForm.usePoint = parseInt(usePoint,10) ;
            pageObj.applDataForm.usePointSvcCntrNo = $("#usePointSvcCntrNo").val();
        }

        viewSelfPhoneChargeInfo();
    });

    var getHndsetPrice = function() {
        var hndsetSalePrice = 0 ;

        if (pageObj.applDataForm.sesplsYn == "Y") {
            hndsetSalePrice = pageObj.applDataForm.hndsetSalePrice ;
        } else {
            hndsetSalePrice = pageObj.rateObj.instAmt ;
        }
        if (hndsetSalePrice > 0) {
            return hndsetSalePrice;
        } else {
            return 0 ;
        }
    }


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

            ajaxCommon.getItem({
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
                        $("#divBankAut1").hide();
                        $("#divBankAut2").show();

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
        // 신청서 중복 작성 체크
        if ($("#reqUsimSn").val()) {
            if (chkReqDup()) {
                return false;
            }
        }

        validator.config={};
        validator.config['reqUsimSn'] = 'isNumFix19';

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        if (validator.validate()) {

            //2026.03 신규(셀프/상담사)만 30일 이내 1회선으로 개통 제한
            //유심유효성 체크시에는 상담사개통일 때에만 제한
            if ($("#onOffType").val() == "0" || $("#onOffType").val() == "3") {
                if (!checkLimitCounsel()) return null;
            }

            var ncType= "";
            var cstmrType = $("input:radio[name=cstmrType]:checked").val();
            if(cstmrType == CSTMR_TYPE.NM) ncType= "0";

            var varData = ajaxCommon.getSerializedData({
                iccId:$.trim($("#reqUsimSn").val()),
                ncType: ncType
            });

            ajaxCommon.getItem({
                    id:'moscIntmMgmtAjax'
                    ,cache:false
                    ,url:'/msp/moscIntmMgmtAjax.do'
                    ,data: varData
                    ,dataType:"json"
                    ,errorCall : function () {
                        MCP.alert("유심번호 유효성 체크가 불가능합니다. <br> 잠시 후 다시 시도하시기 바랍니다. ")
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
                            $("#divUsimScan").hide();
                            isValidateNonEmptyStep2();
                        });

                    } else {
                        var strMsg = "입력하신 유심번호 사용이 불가능 합니다.<br>확인 후 다시 시도해 주시기 바랍니다.";
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

            ajaxCommon.getItem({
                    id:'niceAccountOtpNameAjax'
                    ,cache:false
                    ,url:'/nice/niceAccountOtpConfirmAjax.do'
                    ,data: varData
                    ,dataType:"json"
                }
                ,function(jsonObj){
                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                        pageObj.niceResLogObj.authType = "A"; //PASS인증으로 저장
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
                        $("#divBankAut2").hide();
                        $("#divBankAut3").show();
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
                getNationList();
                setNationSelect();
                $("._cstmrForeignerNation").show();
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
        validator.config['cstmrMobile'] = 'isPhone';
        if (validator.validate()) {

            var varData = ajaxCommon.getSerializedData({
                onlineOfflnDivCd : "ONLINE",
                orgId : $("#cntpntShopId").val(),
                cpntId : $("#cntpntShopId").val(),
                smsRcvTelNo : $("#cstmrMobile").val(),
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


        $("input:checkbox[name=additionName]").each(function(index){
            if ($(this).is(':checked')) {
                var rantal = $(this).attr("rantal");
                var txtName = $(this).attr("txtName");
                var chargeFlag = $(this).attr("chargeFlag");
                var additionkey  = $(this).val();
                var arr =[];
                pageObj.additionKeyList.push(additionkey);
                pageObj.reqAddition.push(txtName);
                totalRantal += parseInt(rantal,10) ;

                if ("N" == chargeFlag) {
                    arr.push(
                        `<dl class='c-addition c-addition--type2'>
                            <dt>${txtName}<em>(무료)</em></dt>
                            <dd class='u-ta-right'>
                                <button type='button' class='_btnDelAddition' data-addition-key='${additionkey}'>
                                    <span class='sr-only'>부가서비스 삭제</span><i class='c-icon c-icon--delete' aria-hidden='true'></i>
                                 </button>
                             </dd>
                        </dl>`
                    );
                    $("#divAdditionListFree").append(arr.join(''));
                    AdditionListAllCnt += 1;
                } else if ("Y" == chargeFlag){
                    var rantalTxt = numberWithCommas(rantal);
                    arr.push(
                        `<dl class='c-addition c-addition--type2'>
                            <dt>${txtName}<b>(<span>${rantalTxt}</span>원)</b></dt>
                            <dd class='u-ta-right'>
                                <button type='button' class='_btnDelAddition' data-addition-key='${additionkey}'>
                                    <span class='sr-only'>부가서비스 삭제</span><i class='c-icon c-icon--delete' aria-hidden='true'></i>
                                 </button>
                             </dd>
                        </dl>`
                    );
                    $("#divAdditionListPrice").append(arr.join(''));
                    AdditionListPriceCnt += 1;
                    AdditionListAllCnt += 1;
                }
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

        $("#totalRantalTxt").html("<b>"+numberWithCommas(totalRantal)+"</b>원");
        $("#AdditionListPriceCnt").html("<b>"+AdditionListPriceCnt+"</b>건");

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

    $("input:radio[name=reqPayType]").click(function(){
        var thisval = $(this).val();
        var modelMonthlyVal = $("#modelMonthly").val();
        var reqBuyType = $("#reqBuyType").val() ;
        if (modelMonthlyVal == "") {
            modelMonthlyVal ="0" ;
        }

        if (thisval == "D" ) {
            $("._card").hide();
            $("._bank").show();
        } else if (thisval == "C") {
            $("._card").show();
            $("._bank").hide()

            if ($('#isCheckCard').val() == "1") {
                $("#divScanCard").hide();
            }
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

        if (!pageObj.telAdvice) {
            validator.config['isAuth'] = 'isNonEmpty';
        }

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

    $("#btnCheckCardNo").click(function(){
        if ($('#isCheckCard').val() == "1") {
            MCP.alert("신용카드번호 유효성 검증에 성공하였습니다.");
            return null;
        }
        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        validator.config={};
        validator.config['reqCardNo'] = 'isNumBetterFixN14';  //  isCheckCardNumberAll
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
                        $("#divScanCard").hide();

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
        var radUsimProd = $("input[name='radUsimProd']:checked").val();
        var nfcYn = radUsimProd == '05' ? 'N' : 'Y';
        pageObj.addrFlag = addrFlag;
        if (addrFlag != "3") {
            openPage('pullPopup', '/m/addPopup.do');
        }  else {
            openPage('pullPopup', '/m/dlvryInfo.do','nfcYn='+nfcYn);
        }
    });



    var setStep = function(setStep) {
        window.scrollTo(0, 0);
        pageObj.step = setStep;

        $("._divStep").hide();
        $("#step"+setStep).show();
        window.scrollTo(0, 0);

        var widthPer = setStep * 20;
        $("#spIndicator").css("width", widthPer +"%");

        if (setStep == 2) {
            if (stepInfo.isInitStep2) {


                if ($("input[name='onOffLineUsim']:checked").length > 0) {
                    $("input[name='onOffLineUsim']:checked").trigger("change",{'isInit':true});  //2
                }

                if($("#cstmrAddr").length > 0 && "" != $("#cstmrAddr").val()) {
                    $("#cstmrAddr").parent().addClass('has-value');
                }

                if($("#dlvryAddr").length > 0 && "" != $("#dlvryAddr").val()) {
                    $("#dlvryAddr").parent().addClass('has-value');
                }

                var usimKindsCd = $('#usimKindsCd').val();
                if ("09" == usimKindsCd) {
                    $("#divDlvry").hide();
                }

                stepInfo.isInitStep2 = false;
            }

        } else if (setStep == 3) {
            if (stepInfo.isInitStep3) {
                $("input:radio[name=reqPayType]:checked").trigger("click");
                stepInfo.isInitStep3 = false;
            }

        } else if (setStep == 4) {
            if (stepInfo.isInitStep4) {

                var evntCdPrmt = ajaxCommon.isNullNvl($("#evntCdPrmt").val(), "");    // 이벤트 코드 입력
                var recoUseYn = $("#recoUseYn").val();        // 추쳔인id 입력 노출여부

                // 이벤트 코드 입력 && 추천인 아이디 비활성일 경우 숨김 처리
                if(evntCdPrmt != "" && recoUseYn == "N"){
                    $(".recoIdArea").hide();
                }

                $("#recommendFlagCd").trigger("change");

                //임시 저장 부가서비스
                getAdditionTempList();
                if (pageObj.additionTempKeyList.length > 0) {
                    getAdditionList("N");
                    getAdditionList("Y");
                    $("#btnSetAddition").trigger("click") ;
                }

                //임시 저장 안심보험
                var insrProdCdTemp = $("#insrProdCdTemp").val() ;
                //pageObj.telAdvice 전화상담이.. 아닌때...
                if (insrProdCdTemp !="" && pageObj.insrProdList.length < 1 && !pageObj.telAdvice) {
                    getInsrProdInfo();
                }

                // 자급제 보상 서비스 추가 (초기 선택값 세팅)
                var imei1 = ajaxCommon.isNullNvl($.trim($("#rwdImei1").val()), "");
                var imei2 = ajaxCommon.isNullNvl($.trim($("#rwdImei2").val()), "");
                if(imei1 != "" || imei2 != ""){
                    checkRwdImei();  // imei 초기값이 존재하는 경우 iemi 중복값 확인
                }
                var rwdProdCdTemp = $("#rwdProdCdTemp").val();
                if (rwdProdCdTemp !="" && pageObj.rwdProdList.length < 1 && !pageObj.telAdvice) {
                    getRwdProdInfo();  // 자급제 보상 서비스 초기값 세팅
                }

                //사은품 정보
                getGiftList();

                stepInfo.isInitStep4 = false;
            }

            isValidateNonEmptyStep4();
        }

    };

    $('#btnBefore').click(function(){
        var onOffType = $("#onOffType").val() ;
        var operType = $("#operType").val() ;

        $('#btnNext').removeClass('is-disabled');
        $("._divStep").hide();

        if (pageObj.step == 2 ) {
            setStep(1);
            $("#btnNext").html("다음");
            $("#btnNext2").html("다음");
            $("#btnBefore").hide();

            isValidateNonEmptyStep1();
            return;
        } else if (pageObj.step == 3) {
            setStep(2);
            $("#btnNext").html("다음");
            $("#btnNext2").html("다음");
            isValidateNonEmptyStep2();
            return;
        } else if (pageObj.step == 4) {
            if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE ) {
                setStep(2);
                isValidateNonEmptyStep2();
            }  else {
                setStep(3);
                if(operType == OPER_TYPE.NEW && onOffType == "7"){
                    $("#btnBefore").hide();
                }
                isValidateNonEmptyStep3();
            }
            $("#btnNext").html("다음");
            $("#btnNext2").html("다음");
            return;
        } else if (pageObj.step == 5 ) {
            setStep(4);
            $("#btnNext").html("다음");
            $("#btnNext2").html("다음");
            if(operType == OPER_TYPE.MOVE_NUM && onOffType == "7"){
                $("#btnBefore").hide();
            }
            return;
        }
    }) ;

    $('#btnNext2').click(function(){
        KTM.Dialog.closeAll();
        $("#btnNext").trigger("click");
    });

    $('#btnNext').click(function(){
        var onOffType = $("#onOffType").val() ;
        var operType = $("#operType").val() ;


        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");



        if (pageObj.step == 1 && isValidateStep1() ) {
            $('#btnNext').addClass('is-disabled');
            ajaxCommon.getItem(
                {
                    id:'selectIdentityIp'
                    ,cache:false
                    ,async:false
                    ,url:'/appForm/selectIdentityIp.do'
                    ,dataType:"json"
                },function(obj){
                    var isStolenIp = obj.RESULT_CODE
                    // if (isStolenIp && onOffType == "7" && operType == "NAC3") { //셀프개통 / 신규가입일 경우
                    if (isStolenIp && onOffType == "7") { //셀프개통 / 신규가입, 번호이동
                        MCP.alert("접속하신 IP는 부정사용 개통 신고되어 있는<br> " +
                            "IP로 셀프개통이 제한되어 있습니다.<br>" +
                            "가입을 원하시면 『상담사 개통 신청』 을 통해<br>" +
                            "가입신청 가능하며 IP 접속제한 해제를<br>" +
                            "희망하실 경우 고객센터(114/무료)로<br>" +
                            "연락주시면 본인인증 후 제한 해제 가능합니다.");

                    } else {
                        if(fnSaveTempStep1()){
                            setStep(2);
                            if(operType == OPER_TYPE.NEW && onOffType == "7"){
                                $("#btnNext").html("개통사전체크확인");
                                $("#btnNext2").html("개통사전체크확인");
                            }
                            $("#btnBefore").show();
                            isValidateNonEmptyStep2();
                        };
                    } // end of else---------------------
                }
            );
            // setStep(2);
            // fnSaveTempStep1();
            //
            // if(operType == OPER_TYPE.NEW && onOffType == "7"){
            //     $("#btnNext").html("개통사전체크확인");
            //     $("#btnNext2").html("개통사전체크확인");
            // }
            // $("#btnBefore").show();
            // isValidateNonEmptyStep2();
            return;
        } else if (pageObj.step == 2 && isValidateStep2() ) {
            $('#btnNext').addClass('is-disabled');
            fnSaveTempStep2();
            if(operType == OPER_TYPE.NEW && onOffType == "7"){
                KTM.LoadingSpinner.show();
                setTimeout(function () {
                    fnReqPreCheck(false);
                }, 300);
            } else {
                if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE ) {
                    setStep(4);
                    window.scrollTo(0, 0);
                    $("#btnNext").html("온라인 신청");
                    $("#btnNext2").html("온라인 신청");
                } else {
                    if( onOffType == "7"){

                        // 사전동의 제외 통신사리스트 조회
                        getNpExceptionList();

                        var mpCode= $("#moveCompany option:selected").attr("mpCode");

                        if ($("#isNpPreCheck").val() == "1") {
                            $("#btnNext").html("동의 완료");
                            $("#btnNext2").html("동의 완료");
                        } else if(isNpExceptionCompany(mpCode)){
                            $("#btnNext").html("개통사전체크확인");
                            $("#btnNext2").html("개통사전체크확인");
                        } else{
                            $("#btnNext").html("사전동의 요청");
                            $("#btnNext2").html("사전동의 요청");
                        }

                    }
                    setStep(3);
                    isValidateNonEmptyStep3();
                }
            }
            return;
        } else if (pageObj.step == 3 && isValidateStep3() ) {
            // 번호이동 신청서 중복 작성 체크
            if (operType == OPER_TYPE.MOVE_NUM) {
                if (chkReqDup()) {
                    return false;
                }
            }

            if (operType == OPER_TYPE.NEW && onOffType != "7") {
                if (containsGoldNumber()) {
                    return false;
                }
            }

            $('#btnNext').addClass('is-disabled');
            fnSaveTempStep3();
            if(operType != OPER_TYPE.NEW && onOffType == "7" ){
                var isNpPreCheck = $("#isNpPreCheck").val();
                var mpCode = $("#moveCompany option:selected").attr("mpCode");

                KTM.LoadingSpinner.show();

                if (isNpPreCheck == "1" || isNpExceptionCompany(mpCode)) {
                    setTimeout(function () {
                        fnReqPreCheck(false);
                    }, 300);
                } else {
                    setTimeout(function () {
                        fnReqPreMoveAut();
                    }, 300);
                }
            } else {
                setStep(4);
                $("#btnBefore").show();
                window.scrollTo(0, 0);
            }
            return;
        } else if (pageObj.step == 4 && isValidateStep4() ) {
            $('#btnNext').addClass('is-disabled');
            //사은품 확인
            if (isValidateGift()) {
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

        } else if (pageObj.step == 6 ) {
            $('#btnNext').addClass('is-disabled');
            fnSelfOpenCallback();
            return;
        }
    }) ;

    //2026.03 개통 후 주의사항 확인 후 개통진행(신청서작성 완료)
    $("#cautionCheck").click(function() {
        if(!$('#cautionFlag').is(':checked')){
            MCP.alert("유의사항 이해여부 확인 바랍니다.");
            return false;
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
        $('#btnNext').addClass('is-disabled');
        fnSaveTempStep5();
        KTM.LoadingSpinner.show();
        if($("#onOffType").val() != "7"){
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

    var nextStepFive = function() {
        var onOffType = $("#onOffType").val() ;
        var operType = $("#operType").val() ;

        fnSaveTempStep4();
        if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE ) {
            fnSaveForm();
        } else {
            setStep(5);
            $("#btnBefore").show();
            isValidateNonEmptyStep5();
            if(onOffType == "7"){
                $("#btnNext").html("개통요청");
                $("#btnNext2").html("개통요청");
            }  else {
                if ( 0 < getPaymentHndsetPrice()) {
                    $("#btnNext").html("결제 후 주문완료");
                    $("#btnNext2").html("결제 후 주문완료");
                } else {
                    $("#btnNext").html("주문완료");
                    $("#btnNext2").html("주문완료");
                }
            }
        }
    }

    //$("#btnOnline").trigger("click");
    $('#btnOnline').click(function(){

        var siteReferer = $("#siteReferer").val();
        if (siteReferer == "") {
            $("#onOffType").val("8");
        } else {
            $("#onOffType").val("3");
        }

        var usimKindsCd = $('#usimKindsCd').val();

        if ("09" != usimKindsCd) {
            $(".ly-page__head").html("유심 요금제 가입하기");
        }

        $("._self").hide();
        $("._noSelf").show();


        if (pageObj.step >= 5) {
            $("#btnNext").html("주문완료");  ///온라인 신청
            $("#btnNext2").html("주문완료");
            pageObj.step = 5;
        } else {
            $("#btnNext").html("다음");
            $("#btnNext2").html("다음");
        }

        KTM.Dialog.closeAll();


        setTimeout(function(){
            window.scrollTo(0, 0);
        }, 500);
        if (pageObj.step == 3) {
            isValidateNonEmptyStep3();
        }
        //$("input[name='onOffLineUsim']:checked").trigger("change",{'isInit':true});
        $("input[name='onOffLineUsim'][value='offLine']").trigger("change",{'isInit':true});
        procOnline('S');
    }) ;

    var changeSelf = function () {
        $("#onOffType").val($("#onOffTypeInit").val());
        $(".ly-page__head").html("셀프개통");
        $("._self").show();
        $("._noSelf").hide();
        $("input[name='onOffLineUsim'][value='offLine']").trigger("change",{'isInit':true});
        procOnline('R');
    }

    $('._btnCheck').click(function(){
        if (RETRY_RESULT_CODE_LIST.includes(pageObj.osstResultCode)) {
            //초기화 처리 다시 등록 할수 있도록..
            let init = false ;
            ajaxCommon.getItemNoLoading({
                id:'reqInit'
                ,cache:false
                ,url:'/appform/reqInitAjax.do'
                ,data: ''
                ,dataType:"json"
                ,async:false
            }, function(jsonObj) {
                init = true ;
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
                $("#btnNext").html("개통사전체크확인");
                $("#btnNext2").html("개통사전체크확인");
                $("#npDesc").hide();
            }else{
                $("#btnNext").html("사전동의 요청");
                $("#btnNext2").html("사전동의 요청");
                $("#npDesc").show();
            }

        }
        if (pageObj.step == 3) {
            isValidateNonEmptyStep3();
        }
    });

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

    $("#btnSearchNumber").click(function() {
        var action = $(this).attr("action");
        if (action == "RSV") {
            searchNumber();
        } else {
            cancelNumber();
        }
    })

    $("#btnConfirmNum").click(function() {
        if ($("input:radio[name=rdoCtn]:checked").length > 0) {
            KTM.LoadingSpinner.show();
            $(this).parent().hide();
            var $chkRadioObj = $("input:radio[name=rdoCtn]:checked");

            var varData = ajaxCommon.getSerializedData({
                tlphNo:$chkRadioObj.val()
                ,tlphNoStatCd:$chkRadioObj.attr("tlphNoStatCd")
                ,tlphNoOwnCmpnCd:$chkRadioObj.attr("tlphNoOwnCmncCmpnCd")
                ,encdTlphNo:$chkRadioObj.attr("encdTlphNo")
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
                        $("#reqWantNumberN").hide();
                        $("#reqCnt").val($("input:radio[name=rdoCtn]:checked").val());
                        $("#spReqWantNumber").html($("input:radio[name=rdoCtn]:checked").attr("cntValue") );
                        $('#btnSearchNumber').html("번호취소");
                        $("#btnSearchNumber").attr("action", "RRS");
                        isValidateNonEmptyStep3();
                        KTM.Dialog.closeAll();
                        setTimeout(function() {
                            KTM.LoadingSpinner.hide(true);
                        }, 500);
                        $("#btnConfirmNum").parent().show();
                    } else {
                        MCP.alert("번호 예약이 실패 하였습니다.");
                        KTM.LoadingSpinner.hide(true);
                        $("#btnConfirmNum").parent().show();
                    }
                });
        } else {
            MCP.alert("전화번호를 선택 하여 주시기 바랍니다.");
        }
    });

    $("#inEqualCheck").click(function() {
        var dlvryType = "01";

        if ($("input:radio[name=dlvryType]:checked").val() != undefined) {
            dlvryType = $("input:radio[name=dlvryType]:checked").val();
        }

        if($(this).is(':checked')) {
            $("#dlvryName").val($("#cstmrName").val());
            $("#dlvryMobile").val($("#cstmrMobile").val());
            $("#dlvryTel").val($("#cstmrTel").val());
            $("#dlvryName").parent().addClass('has-value');
            $("#dlvryMobile").parent().addClass('has-value');
            $("#dlvryTel").parent().addClass('has-value');

            if (dlvryType == "02" && $("#cstmrPost").val() != "") {
                //바로배송(당일 퀵) 이면
                //바로배송 여부 확인
                var isDlvrType = false;

                //좌표 조회을 위한 javascript 가지고 오기
                var radUsimProd = $("input[name='radUsimProd']:checked").val();
                var nfcYn = radUsimProd == '05' ? 'N' : 'Y';
                openPage('pullPopupNoOpen', '/m/dlvryInfo.do','nfcYn='+nfcYn,'1');

                var $cstmrPost = $("#cstmrPost");
                var admCd = $cstmrPost.data('admCd');
                var rnMgtSn = $cstmrPost.data('rnMgtSn');
                var udrtYn = $cstmrPost.data('udrtYn');
                var buldMnnm = $cstmrPost.data('buldMnnm');
                var buldSlno = $cstmrPost.data('buldSlno');
                var jibunAddr = $cstmrPost.data('jibunAddr');
                var addrDetail =   $cstmrPost.data('addrDetail');
                var roadAddrPart2 =   $cstmrPost.data('roadAddrPart2');


                var dataObj = getJusoAddrCoordApi(admCd,rnMgtSn,udrtYn,buldMnnm,buldSlno);
                if(dataObj !=null && dataObj.length > 1) {
                    var entY ="";
                    var entX ="";
                    var zipNo = $cstmrPost.val();
                    var roadAddr = $("#cstmrAddr").val();

                    entY = ajaxCommon.isNullNvl(dataObj[1],"")+"";
                    entX = ajaxCommon.isNullNvl(dataObj[0],"")+"";
                    if(entY !="" && (entY.indexOf(".") > -1) ){
                        entY = Number(entY).toFixed(4);
                    }
                    if(entX !="" && (entX.indexOf(".") > -1) ){
                        entX = Number(entX).toFixed(4);
                    }

                    roadAddr = roadAddr.replace(/[?&=]/gi,' ');
                    jibunAddr = jibunAddr.replace(/[?&=]/gi,' ');
                    if (typeof addrDetail === 'string') {
                        addrDetail = addrDetail.replace(/[?&=]/gi,' ');
                    }

                    var varData = ajaxCommon.getSerializedData({
                        zipNo : zipNo
                        ,targetAddr1 : roadAddr // 기본주소
                        ,targetAddr2 : "" // 상세주소
                        ,bizOrgCd : ""
                        ,entY : entY
                        ,entX : entX
                        ,jibunAddr : jibunAddr
                        ,targetAddr2 : addrDetail
                        ,nfcYn : nfcYn
                    });

                    ajaxCommon.getItem({
                            id:'getNicePinCiAjax'
                            ,cache:false
                            ,async:false
                            ,url:"/dlvryAddrChkAjax.do"
                            ,data: varData
                            ,dataType:"json"
                        }
                        ,function(jsonObj){
                            var psblYn = ajaxCommon.isNullNvl(jsonObj.psblYn,"");
                            var bizOrgCd = ajaxCommon.isNullNvl(jsonObj.bizOrgCd,"");
                            var acceptTime = ajaxCommon.isNullNvl(jsonObj.acceptTime,"");

                            var isable = false;
                            if(psblYn=="Y" && bizOrgCd !=""){ // 배달가능
                                isable = true;
                            }

                            if(!isable){
                                MCP.alert("해당 지역은 바로 배송(당일 퀵)이 불가합니다.<br/>일반 택배로 변경되어 배송됩니다.\n");
                            }

                            var roadAddrPart1 = $("#cstmrAddr").val();
                            var roadFullAddr = roadAddrPart1;

                            if(addrDetail != null  && addrDetail != ""){
                                roadFullAddr += ", " + addrDetail;
                            }
                            if(roadAddrPart2 != null  && roadAddrPart2 != "" ){
                                roadFullAddr += " " + roadAddrPart2;
                            }
                            dlvryJusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,jibunAddr,zipNo,psblYn,bizOrgCd,acceptTime,entY,entX);

                        }); // end of ajax-----------
                }
            } else if (dlvryType == "03" && $("#cstmrPost").val() != "") {
                var strReg = /01|02|03|04|05|06|07|08|09/;
                var zipNoCi = $("#cstmrPost").val().substring(0,2);
                if (strReg.test(zipNoCi)) {
                    $("#dlvryPost").val($("#cstmrPost").val());
                    $("#dlvryAddr").val($("#cstmrAddr").val());
                    $("#dlvryAddrDtl").val($("#cstmrAddrDtl").val());
                } else {
                    MCP.alert("서울 지역에 한하여 당일 배송이 가능합니다. ")
                }
            } else {
                $("#dlvryPost").val($("#cstmrPost").val());
                $("#dlvryAddr").val($("#cstmrAddr").val());
                $("#dlvryAddrDtl").val($("#cstmrAddrDtl").val());
            }

            isValidateNonEmptyStep2();
        }
    });

    //개통사전체크요청
    $("#reqPreButton").click(function(){
        KTM.LoadingSpinner.show();
        setTimeout(function () {
            fnReqPreCheck(false);
        }, 300);
        KTM.Dialog.closeAll();
    });


    //고객인증
    $("#btnChangeCancel").click(function(){
        ajaxCommon.createForm({
            method:"post"
            ,action:"/m/appForm/appFormDesignView.do"
        });

        ajaxCommon.attachHiddenElement("requestKey",$("#requestKeyTemp").val());
        ajaxCommon.formSubmit();
    }) ;



    $("._agree").click(function(){
        handleCommonAgreeClick();
    });

    $("#clauseSimpleOpen").click(function(){
        isValidateNonEmptyStep1();
    });

    $("#clauseSettlWayCd").click(function(){
        isValidateNonEmptyStep1();
    });

    $("#minorAgentTel,#cstmrName,#cstmrForeignerRrn01,#cstmrForeignerRrn02,#cstmrNativeRrn01,#cstmrNativeRrn02").keyup(function(){
        isValidateNonEmptyStep1();
    });

    $("#minorAgentAgrmYn").click(function(){
        isValidateNonEmptyStep1();
    });

    $("#cstmrNativeRrn02").blur(function(){
        if($(this).val() == '') return;

        validator.config={};
        validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';

        if (validator.validate()) {
            var age = 0;
            age = getAge($("#cstmrNativeRrn01").val()+$("#cstmrNativeRrn02").val());
            if (!checkPriceLimitForAge(age)) {
                MCP.alert(pageObj.priceLimitObj.dtlCdDesc);
                // error 표출되어있는 것 모두 제거
                $(".c-form__text").remove();
                $(".has-error").removeClass("has-error");
                return false;
            }
        }
    });


    $("#cstmrForeignerRrn02").blur(function(){
        //기기변경 주민번호 수집삭제
        var operType = $("#operType").val() ;
        if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE ) {
            return true;
        }

        if($(this).val() == '') return;

        validator.config={};
        //console.log("cstmrType===>" + cstmrType);

        validator.config['cstmrForeignerRrn01&cstmrForeignerRrn02'] = 'isFngno';

        if (validator.validate()) {
            age = getAge($("#cstmrForeignerRrn01").val()+$("#cstmrForeignerRrn02").val());
            if (!checkPriceLimitForAge(age)) {
                MCP.alert(pageObj.priceLimitObj.dtlCdDesc);
                return false;
            }
        }
    });



    $("#selfInqryAgrmYnFlag").click(function(){
        isValidateNonEmptyStep2();
    });

    $("#reqUsimSn  , #cstmrMobile,#cstmrMail,#selfIssuExprDt,#driverSelfIssuNum1,#driverSelfIssuNum2,#selfIssuNum,#dlvryName,#dlvryMobile,#homePw").keyup(function(){
        isValidateNonEmptyStep2();
    });

    $("#selfIssuExprDt,#cstmrForeignerNation").change(function(){
        isValidateNonEmptyStep2();
    });

    $("#driverSelfIssuNum1").change(function(){
        isValidateNonEmptyStep2();
    });

    $("#moveCompany").change(function(){
        if ($("#isNpPreCheck").val() == "1") {
            $("#moveCompany").val(pageObj.applDataForm.moveCompany);
            return false;
        }

        // NP 예외 통신사 확인
        if($("#onOffType").val() == "7"){
            var mpCode = $("#moveCompany option:selected").attr("mpCode");
            if(isNpExceptionCompany(mpCode)){
                $("#btnNext").html("개통사전체크확인");
                $("#btnNext2").html("개통사전체크확인");
                $("#npDesc").hide();
            }else{
                $("#btnNext").html("사전동의 요청");
                $("#btnNext2").html("사전동의 요청");
                $("#npDesc").show();
            }
        }

        isValidateNonEmptyStep3();
    });





    $("#moveMobile,#reqWantNumber,#reqWantNumber2,#reqWantNumber3").keyup(function(){
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

    $("#clauseInsrProdFlag,#clauseInsrProdFlag02,#clauseInsrProdFlag03").click(function(){
        isValidateNonEmptyStep4();
    });




    var searchNumber = function() {
        validator.config={}
        validator.config['reqWantNumberN'] = 'isNumFix4';
        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        if (validator.validate()) {
            var varData = ajaxCommon.getSerializedData({
                reqWantNumber:$("#reqWantNumberN").val()
                ,requestKey:pageObj.requestKey
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

                        $("#searchCnt").html("조회 가능 횟수<b>" + (20-jsonObj.SEARCH_CNT) +"회</b>");
                        for (i=0; i < phoneNoList.length; i++) {
                            arr.push("<div class='c-chk-wrap'>\n");
                            arr.push("    <input type='radio' class='c-radio' name='rdoCtn' id='"+phoneNoList[i].tlphNo+"' value='"+phoneNoList[i].tlphNo+"'  ");
                            arr.push("      tlphNoStatCd='"+phoneNoList[i].tlphNoStatCd+"' tlphNoOwnCmncCmpnCd='"+phoneNoList[i].tlphNoOwnCmncCmpnCd+"' ");
                            arr.push("      cntValue='"+phoneNoList[i].tlphNoView+"' encdTlphNo='"+phoneNoList[i].encdTlphNo+"'  />\n");
                            arr.push("    <label class='c-label' for='"+phoneNoList[i].tlphNo+"'>"+phoneNoList[i].tlphNoView+"</label>\n");
                            arr.push("</div>  \n");
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
                            ajaxCommon.createForm({
                                method:"post"
                                ,action:"/appForm/appForm.do"
                            });

                            ajaxCommon.attachHiddenElement("requestKey",$("#requestKeyTemp").val());
                            ajaxCommon.formSubmit();
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
            return ;
        }
    };

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
                    $("#reqWantNumberN").show();
                    $("#spReqWantNumber").html("010 - **** -" );
                    $('#btnSearchNumber').html("번호조회");
                    $("#btnSearchNumber").attr("action", "RSV");
                    setTimeout(function() {
                        KTM.LoadingSpinner.hide(true);
                    }, 500);
                } else {
                    alert("전화번호 예약 취소가 실패 하였습니다.");
                    KTM.LoadingSpinner.hide(true);
                }
            });
    };



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
        pageObj.applDataForm.usimKindsCd = $('#usimKindsCd').val();
        pageObj.applDataForm.uploadPhoneSrlNo = $('#uploadPhoneSrlNo').val();

        validator.config={};
        validator.config['cstmrType1'] = 'isNonEmpty';

        if(operType == OPER_TYPE.NEW && onOffType == "7"){
            //셀프개통 신규가입 신청서 내 ‘휴대폰 인증‘ 추가
            validator.config['isSmsAuth'] = 'isNonEmpty';
        }
        if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE ) {
            validator.config['changeCstmrMobile'] = 'isPhone';
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
            validator.config['minorAgentTel'] = 'isPhone';
            // validator.config['minorAgentTel'] = 'isTel';

            var minorAgentTel = $.trim($("#minorAgentTel").val());
            pageObj.applDataForm.cstmrNativeRrn = getMaskedRrn(rrnFront, rrnBack);
            pageObj.applDataForm.desCstmrNativeRrn = getMaskedRrn(rrnFront, rrnBack);
            pageObj.applDataForm.birthDate = $.trim($("#minorAgentRrn01").val());
            pageObj.applDataForm.cstmrForeignerRrn = "";
            pageObj.applDataForm.minorAgentName = $.trim($("#minorAgentName").val());
            pageObj.applDataForm.minorAgentRrn = agentRrnFront + agentRrnBack;
            pageObj.applDataForm.minorAgentTelFn = minorAgentTel.slice(0, minorAgentTel.length-8)
            pageObj.applDataForm.minorAgentTelMn = minorAgentTel.slice(-8, minorAgentTel.length-4);
            pageObj.applDataForm.minorAgentTelRn = minorAgentTel.slice(-4, minorAgentTel.length);
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
        // validator.config['clausePriTrustFlag'] = 'isNonEmpty';
        // validator.config['clauseConfidenceFlag'] = 'isNonEmpty';
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

        if (onOffType == "7") {
            validator.config['clauseSimpleOpen'] = 'isNonEmpty';
        }

        if ("01" == $("#settlWayCd").val() ) {
            validator.config['clauseSettlWayCd'] = 'isNonEmpty';
        }

        pageObj.applDataForm.clauseFinanceFlag = $("#clauseFinanceFlag").is(":checked") ? "Y":"N";
        pageObj.applDataForm.clausePriCollectFlag = $("#clausePriCollectFlag").is(":checked") ? "Y":"N";
        pageObj.applDataForm.clausePriOfferFlag = $("#clausePriOfferFlag").is(":checked") ? "Y":"N";
        pageObj.applDataForm.clauseEssCollectFlag = $("#clauseEssCollectFlag").is(":checked") ? "Y":"N";
        pageObj.applDataForm.clauseFathFlag = $("#clauseFathFlag01").is(":checked") && $("#clauseFathFlag02").is(":checked") ? "Y":"N";
        pageObj.applDataForm.clausePriTrustFlag = $("#clausePriTrustFlag").is(":checked") ? "Y":"N";
        pageObj.applDataForm.clauseConfidenceFlag = $("#clauseConfidenceFlag").is(":checked") ? "Y":"N";
        //동의 추가
        pageObj.applDataForm.personalInfoCollectAgree = $("#personalInfoCollectAgree").is(":checked") ? "Y":"N"; //고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의
        pageObj.applDataForm.clausePriAdFlag = $("#clausePriAdFlag").is(":checked") ? "Y":"N"; //개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의
        pageObj.applDataForm.othersTrnsAgree = $("#formOthersTrnsAgree").is(":checked") ? "Y":"N"; // 혜택 제공을 위한 제3자 제공 동의 : M모바일
        pageObj.applDataForm.othersTrnsKtAgree = $("#formOthersTrnsKtAgree").is(":checked") ? "Y":"N"; // 혜택 제공을 위한 제3자 제공 동의 : KT
        pageObj.applDataForm.othersAdReceiveAgree = $("#othersAdReceiveAgree").is(":checked") ? "Y":"N"; // 제3자 제공관련 광고 수신 동의

        if (validator.validate()) {
            // if (true) { //test
            return true;
        } else {
            var errId = validator.getErrorId();
            //console.log("errId==>" + errId);
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

        if(operType != OPER_TYPE.CHANGE && operType != OPER_TYPE.EXCHANGE ) {

            if (onOffLineUsim == "offLine") {
                validator.config['reqUsimSn'] = 'isNumFix19';
                validator.config['isUsimNumberCheck'] = 'isNonEmpty';
                pageObj.applDataForm.reqUsimSn = $.trim($("#reqUsimSn").val());
            }

            validator.config['cstmrMobile'] = 'isPhone';
            var cstmrMobile = $.trim($("#cstmrMobile").val());

            pageObj.applDataForm.cstmrMobileFn = cstmrMobile.slice(0, cstmrMobile.length-8);
            pageObj.applDataForm.cstmrMobileMn = cstmrMobile.slice(-8, cstmrMobile.length-4);
            pageObj.applDataForm.cstmrMobileRn = cstmrMobile.slice(-4, cstmrMobile.length);

            var cstmrTel = $.trim($("#cstmrTel").val());
            if (cstmrTel != "") {
                validator.config['cstmrTel'] = 'isTel';

                pageObj.applDataForm.cstmrTelFn = cstmrTel.slice(0, cstmrTel.length-8);
                pageObj.applDataForm.cstmrTelMn = cstmrTel.slice(-8, cstmrTel.length-4);
                pageObj.applDataForm.cstmrTelRn = cstmrTel.slice(-4, cstmrTel.length);
            } else {
                //휴대폰 번호로 설정
                pageObj.applDataForm.cstmrTelFn = pageObj.applDataForm.cstmrMobileFn ;
                pageObj.applDataForm.cstmrTelMn = pageObj.applDataForm.cstmrMobileMn ;
                pageObj.applDataForm.cstmrTelRn = pageObj.applDataForm.cstmrMobileRn ;
            }

            validator.config['cstmrMail'] = 'isNonMail';
            validator.config['cstmrPost'] = 'isNonEmpty';

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
            validator.config['selfIssuExprDt'] = 'isIssuDate';//발급일자
            validator.config['selfInqryAgrmYnFlag'] = 'isNonEmpty';//본인조회동의

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
                    validator.config['driverSelfIssuNum1'] = 'isNumFix2';
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
                    validator.config['driverSelfIssuNum1'] = 'isNumFix2';
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
            //기변 기존 유심 사용 ...
            if (onOffLineUsim == "offLine") {
                pageObj.applDataForm.usimKindsCd = "06";  // usimKind 06 미발송
            }

            //기변
            var cstmrMobile = $.trim($("#changeCstmrMobile").val());
            pageObj.applDataForm.cstmrMobileFn = cstmrMobile.slice(0, cstmrMobile.length-8);
            pageObj.applDataForm.cstmrMobileMn = cstmrMobile.slice(-8, cstmrMobile.length-4);
            pageObj.applDataForm.cstmrMobileRn = cstmrMobile.slice(-4, cstmrMobile.length);
        }

        //배송정보 확인
        var onOffType = $("#onOffType").val() ;
        if(onOffType != "7"){
            if ($('#divDlvry').is(':visible') ) {

                validator.config['dlvryName'] = 'isNonEmpty';
                if ($("#dlvryTel").val() != "") {
                    validator.config['dlvryTel'] = 'isTel';
                }
                validator.config['dlvryMobile'] = 'isPhone';

                pageObj.applDataForm.dlvryName = $.trim($("#dlvryName").val());
                if ($("#dlvryTel").val() != "") {
                    var dlvryTel = $.trim($("#dlvryTel").val());
                    pageObj.applDataForm.dlvryTelFn = dlvryTel.slice(0, dlvryTel.length-8);
                    pageObj.applDataForm.dlvryTelMn = dlvryTel.slice(-8, dlvryTel.length-4);
                    pageObj.applDataForm.dlvryTelRn = dlvryTel.slice(-4, dlvryTel.length);
                }
                var dlvryMobile = $.trim($("#dlvryMobile").val());
                pageObj.applDataForm.dlvryMobileFn = dlvryMobile.slice(0, dlvryMobile.length-8);
                pageObj.applDataForm.dlvryMobileMn = dlvryMobile.slice(-8, dlvryMobile.length-4);
                pageObj.applDataForm.dlvryMobileRn = dlvryMobile.slice(-4, dlvryMobile.length);

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
                    validator.config['dlvryPost'] = 'isNonEmpty';
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
                    var radUsimProd = $("input[name='radUsimProd']:checked").val();
                    if(radUsimProd == "06"){
                        reqDlvryForm.usimAmt = $("#usimPriceNfc").val();
                    }
                    reqDlvryForm.homePw = $.trim($("#homePw").val()).replace(/[?&=]/gi,' ');
                    reqDlvryForm.exitTitle = $("#exitPw option:selected").text();

                    //신청서 메모 처리
                    if (reqDlvryForm.exitTitle != null && reqDlvryForm.exitTitle != "") {
                        pageObj.applDataForm.dlvryMemo = reqDlvryForm.dlvryMemo + " / " + reqDlvryForm.exitTitle;
                    } else {
                        pageObj.applDataForm.dlvryMemo = reqDlvryForm.dlvryMemo;
                    }

                    if("1" == $("#exitPw option:selected").val()) {
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
            validator.config['moveMobile'] = 'isNumMNP';
            var moveMobile = $.trim($("#moveMobile").val());

            pageObj.applDataForm.moveCompany = $("#moveCompany").val();
            pageObj.applDataForm.mpCode = $("#moveCompany option:selected").attr("mpCode");
            pageObj.applDataForm.moveMobileFn = moveMobile.slice(0, moveMobile.length-8);
            pageObj.applDataForm.moveMobileMn = moveMobile.slice(-8, moveMobile.length-4);
            pageObj.applDataForm.moveMobileRn = moveMobile.slice(-4, moveMobile.length);
            pageObj.applDataForm.moveThismonthPayType = "NM";
            pageObj.applDataForm.moveAllotmentStat = "AD";
            pageObj.applDataForm.moveRefundAgreeFlag = "Y";

        } else if(operType==OPER_TYPE.NEW){
            if(onOffType != "7"){
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
        pageObj.applDataForm.ktInterSvcNo = $.trim($("#ktInterSvcNo").val());
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

        //사은품 정보 설정
        pageObj.applDataForm.evntCdPrmt = $("#evntCdPrmt").val();
        pageObj.prmtIdList = [];
        pageObj.prdtIdList = [];
        $("input:checkbox[name=checkboxGift]:checked").each(function() {
            var prmtId = $(this).attr("prmtId") ;
            var prdtId = $(this).val();

            pageObj.prmtIdList.push(prmtId);
            pageObj.prdtIdList.push(prdtId);
        }) ;

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
    } ;


    var isValidateGift = function() {
        //사은품 없음.. 통과
        if (pageObj.giftList.length < 1) {
            return true;
        }

        for(var i = 0 ; i < pageObj.giftList.length ; i++) {
            var limitCnt = pageObj.giftList[i].limitCnt;
            var prmtId = pageObj.giftList[i].prmtId;
            var prmtType =  pageObj.giftList[i].prmtType;


            if (prmtType == "C") {
                var checkCnt = $("input:checkbox[name=checkboxGift][prmtid="+prmtId+"]:checked").length;
                if (limitCnt > checkCnt) {
                    return false;
                }
            }

        }
        return true;
    };

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

    } ;

    var viewErrorMgs = function($thatObj, msg ) {
        if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
            $thatObj.parent().addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
        } else if ($thatObj.hasClass("c-input--div2") ) {
            $thatObj.parent().parent().addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
        }
    };

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
            var minorAgentTel = $.trim($("#minorAgentTel").val());
            paramObj.minorAgentTelFn = minorAgentTel.slice(0, minorAgentTel.length-8);
            paramObj.minorAgentTelMn = minorAgentTel.slice(-8, minorAgentTel.length-4);
            paramObj.minorAgentTelRn = minorAgentTel.slice(-4, minorAgentTel.length);
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
            }) // end of ajax-----
        return isStepSucc;
    };

    var fnSaveTempStep2 = function() {
        var paramObj = APPL_DATA_FORM();
        var cstmrType = $("input:radio[name=cstmrType]:checked").val();
        var operType = $("#operType").val() ;
        var onOffLineUsim = $("input:radio[name='onOffLineUsim']:checked").val();

        paramObj.requestKey = $("#requestKeyTemp").val() ;
        paramObj.telAdvice = pageObj.telAdvice ? "Y":"N";
        paramObj.tmpStep = "02";
        if (onOffLineUsim == "offLine") {
            paramObj.clauseMpps35Flag = "N";
            paramObj.reqUsimSn = $.trim($("#reqUsimSn").val());
        } else {
            paramObj.clauseMpps35Flag = "Y";
        }

        if(operType != OPER_TYPE.CHANGE && operType != OPER_TYPE.EXCHANGE ) {
            var cstmrTel = $.trim($("#cstmrTel").val());
            var cstmrMobile = $.trim($("#cstmrMobile").val());


            paramObj.cstmrTelFn = cstmrTel.slice(0, cstmrTel.length-8);
            paramObj.cstmrTelMn = cstmrTel.slice(-8, cstmrTel.length-4);
            paramObj.cstmrTelRn = cstmrTel.slice(-4, cstmrTel.length);
            paramObj.cstmrMobileFn = cstmrMobile.slice(0, cstmrMobile.length-8);
            paramObj.cstmrMobileMn = cstmrMobile.slice(-8, cstmrMobile.length-4);
            paramObj.cstmrMobileRn = cstmrMobile.slice(-4, cstmrMobile.length);

            paramObj.cstmrPost = $.trim($("#cstmrPost").val());
            paramObj.cstmrAddr = $.trim($("#cstmrAddr").val());
            paramObj.cstmrAddrDtl = $.trim($("#cstmrAddrDtl").val());
            paramObj.cstmrMail = $.trim($("#cstmrMail").val());

            var selfCertType = $("input:radio[name=selfCertType]:checked").val();
            var selfIssuExprDt = $("#selfIssuExprDt").val();
            if (selfIssuExprDt != null) {
                selfIssuExprDt = selfIssuExprDt.replace(/-/gi,"");
            }

            paramObj.selfCertType = $("input:radio[name=selfCertType]:checked").val();
            paramObj.selfIssuExprDt = selfIssuExprDt;
            if (selfCertType=="02") {
                paramObj.cstmrJejuId = $("#driverSelfIssuNum1").val()
                paramObj.selfIssuNum = $.trim($("#driverSelfIssuNum2").val());
            } else if (selfCertType=="04") {
                paramObj.selfIssuNum = $("#selfIssuNum").val();
            } else if (selfCertType=="06") {
                paramObj.cstmrForeignerNation = $.trim($("#cstmrForeignerNation").val());
            }
        } else {
            //기변
            var cstmrMobile = $.trim($("#changeCstmrMobile").val());
            paramObj.cstmrMobileFn = cstmrMobile.slice(0, cstmrMobile.length-8);
            paramObj.cstmrMobileMn = cstmrMobile.slice(-8, cstmrMobile.length-4);
            paramObj.cstmrMobileRn = cstmrMobile.slice(-4, cstmrMobile.length);
        }

        //배송정보 확인
        var onOffType = $("#onOffType").val() ;
        if(onOffType != "7"){
            paramObj.dlvryName = $.trim($("#dlvryName").val());
            if ($("#dlvryTel").val() != "") {
                var dlvryTel = $.trim($("#dlvryTel").val());
                paramObj.dlvryTelFn = dlvryTel.slice(0, dlvryTel.length-8);
                paramObj.dlvryTelMn = dlvryTel.slice(-8, dlvryTel.length-4);
                paramObj.dlvryTelRn = dlvryTel.slice(-4, dlvryTel.length);
            }
            var dlvryMobile = $.trim($("#dlvryMobile").val());
            paramObj.dlvryMobileFn = dlvryMobile.slice(0, dlvryMobile.length-8);
            paramObj.dlvryMobileMn = dlvryMobile.slice(-8, dlvryMobile.length-4);
            paramObj.dlvryMobileRn = dlvryMobile.slice(-4, dlvryMobile.length);
            paramObj.dlvryPost = $.trim($("#dlvryPost").val());
            paramObj.dlvryAddr = $.trim($("#dlvryAddr").val());
            paramObj.dlvryAddrDtl = $.trim($("#dlvryAddrDtl").val());
            paramObj.dlvryMemo = $.trim($("#dlvryMemo").val());;
        }

        var varData = ajaxCommon.getSerializedData(paramObj);

        ajaxCommon.getItemNoLoading({
                id:'updateRequestTemp'
                ,cache:false
                ,url:'/appForm/updateRequestTemp.do'
                ,data: varData
                ,dataType:"json"
                ,errorCall : function () {
                    //nothing
                }
            }
            ,function(jsonObj){
                //nothing
            })
    };

    var fnSaveTempStep3 = function() {
        var paramObj = APPL_DATA_FORM();
        paramObj.requestKey = $("#requestKeyTemp").val() ;
        paramObj.telAdvice = pageObj.telAdvice ? "Y":"N";
        paramObj.tmpStep = "03";

        var operType = $("#operType").val() ;
        var onOffType = $("#onOffType").val() ;

        if (operType==OPER_TYPE.MOVE_NUM ) {
            //번호이동
            var moveMobile = $.trim($("#moveMobile").val());
            paramObj.moveCompany = $("#moveCompany").val();
            paramObj.moveMobileFn = moveMobile.slice(0, moveMobile.length-8);
            paramObj.moveMobileMn = moveMobile.slice(-8, moveMobile.length-4);
            paramObj.moveMobileRn = moveMobile.slice(-4, moveMobile.length);

        } else if(operType==OPER_TYPE.NEW){
            if(onOffType != "7"){
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
                ,errorCall : function () {
                    //nothing
                }
            }
            ,function(jsonObj){
                //nothing
            })
    };

    var fnSaveTempStep4 = function() {
        var paramObj = APPL_DATA_FORM();
        paramObj.requestKey = $("#requestKeyTemp").val();
        paramObj.telAdvice = pageObj.telAdvice ? "Y":"N";
        paramObj.tmpStep = "04";
        paramObj.recommendInfo = $.trim($("#recommendInfo").val());
        paramObj.recommendFlagCd = $("#recommendFlagCd").val() ;
        //부가서비스 리스트
        paramObj.additionKeyList = pageObj.additionKeyList;

        //휴대폰 안심 서비스 처리
        if (pageObj.insrProdCd != ""){
            paramObj.insrProdCd = pageObj.insrProdCd;
        } else {
            paramObj.insrProdCd = "";
        }

        // 자급제 보상 서비스 처리 (안심 보험과 동일하게 선택 코드값만 임시 저장)
        if (pageObj.rwdProdCd != ""){
            paramObj.rwdProdCd= pageObj.rwdProdCd;
        } else {
            paramObj.rwdProdCd= "";
        }

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
                ,errorCall : function () {
                    //nothing
                }
            }
            ,function(jsonObj){
                //nothing
            })
    };

    var fnSaveTempStep5 = function() {
        var paramObj = APPL_DATA_FORM();
        var reqPayType = $('input:radio[name=reqPayType]:checked').val();

        paramObj.requestKey = $("#requestKeyTemp").val() ;
        paramObj.telAdvice = pageObj.telAdvice ? "Y":"N";
        paramObj.tmpStep = "05";
        paramObj.cstmrBillSendCode = $('input:radio[name=cstmrBillSendCode]:checked').val();
        paramObj.reqPayType = reqPayType;
        if ( reqPayType== "D") {
            //자동이체
            paramObj.reqBank = $("#reqBank").val();
            paramObj.reqAccountNumber = $.trim($("#reqAccountNumber").val());
        } else if ( reqPayType== "C"  ) {
            paramObj.reqCardNo = $.trim($("#reqCardNo").val()) ;
            paramObj.reqCardYy = $.trim($("#reqCardYy").val());
            paramObj.reqCardMm = $.trim($("#reqCardMm").val());
        }

        var varData = ajaxCommon.getSerializedData(paramObj);
        ajaxCommon.getItemNoLoading({
                id:'updateRequestTemp5'
                ,cache:false
                ,url:'/appForm/updateRequestTemp5.do'
                ,data: varData
                ,dataType:"json"
                ,errorCall : function () {
                    //nothing
                }
            }
            ,function(jsonObj){
                //nothing
            });
    };

    var fnSaveForm = function() {
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

        var reqBuyType = $("#reqBuyType").val() ;
        if (REQ_BUY_TYPE.USIM == reqBuyType ) {
            var onOffLineUsim = $("input:radio[name='onOffLineUsim']:checked").val();
            if (onOffLineUsim == "offLine") { //유심 보유
                dlvryType = "00";  // 00 소지유심
            }
        }

        pageObj.applDataForm.dlvryType = dlvryType ;

        if("02" == dlvryType ) {
            var radUsimProd = $("input[name='radUsimProd']:checked").val();
            var usimProdId = radUsimProd == "05" ? "01" : "02";
            reqDlvryForm.usimProdId = usimProdId;

            var varDataD = ajaxCommon.getSerializedData(reqDlvryForm);

            ajaxCommon.getItemNoLoading({
                    id:'saveSelfDlvryAjax'
                    ,cache:false
                    ,url:"/m/appForm/saveSelfDlvryAjax.do"
                    ,data: varDataD
                    ,dataType:"json"
                }
                ,function(jsonObj){
                    reqDlvryForm.selfDlvryIdx = jsonObj.selfDlvryIdx;
                    reqDlvryForm.certId = jsonObj.certId;

                    pageObj.applDataForm.selfDlvryIdx = jsonObj.selfDlvryIdx;
                    pageObj.applDataForm.dlvryType = "02" ;

                    if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE || jsonObj.RESULT_CODE=="DONE") {
                        //신청서 등록 처리
                        fnSaveForm2();
                    } else if(jsonObj.RESULT_CODE=="TIMECHECK"){
                        KTM.LoadingSpinner.hide(true);
                        var isTimeMsg = jsonObj.isTimeMsg;
                        MCP.alert(isTimeMsg);
                        return ;
                    } else if(jsonObj.RESULT_CODE=="TIME"){
                        KTM.LoadingSpinner.hide(true);
                        MCP.alert("바로배송 운영 시간이 아닙니다.<br>다른 배송수단을 선택해 주세요. <br>(바로배송 운영시간 : 10:00~22:00)");
                        return ;
                    } else if (jsonObj.RESULT_CODE == "0003") {
                        KTM.LoadingSpinner.hide(true);
                        $("#isAuth").val("");
                        $('#cstmrName').prop('readonly', false);
                        $('#cstmrNativeRrn01').prop('readonly', false);
                        $('#cstmrNativeRrn02').prop('readonly', false);
                        $('#cstmrForeignerRrn01').prop('readonly', false);
                        $('#cstmrForeignerRrn02').prop('readonly', false);
                        MCP.alert("본인인증 한 정보가 다릅니다. 다시 본인인증을 하여 주시기 바랍니다.");
                        return ;
                    } else {
                        KTM.LoadingSpinner.hide(true);
                        MCP.alert("등록에 실패 하였습니다.");
                        return ;
                    }
                })
        } else {
            fnSaveForm2();
        }

    };


    var fnSaveForm2 = function() {
        pageObj.applDataForm.requestKey=0;
        pageObj.applDataForm.evntCdPrmt = $("#evntCdPrmt").val();
        var varData = ajaxCommon.getSerializedData(pageObj.applDataForm);

        ajaxCommon.getItemNoLoading({
                id:'saveAppform'
                ,cache:false
                ,url:'/m/appForm/saveAppformAjax.do'
                ,data: varData
                ,dataType:"json"
                ,errorCall : function () {
                    KTM.LoadingSpinner.hide(true);
                }
            }
            ,function(jsonObj){
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    pageObj.requestKey = jsonObj.REQUEST_KET ;
                    //sendScanImage();

                    // 자급제 보상 서비스 신청 정보 insert
                    if (pageObj.applDataForm.rwdProdCd != ""){
                        applyRwd();
                    }

                    //바로배송
                    var dlvryType = "01";
                    if ($("input:radio[name=dlvryType]:checked").val() != undefined) {
                        dlvryType = $("input:radio[name=dlvryType]:checked").val();
                    }
                    //즉시 결제
                    var settlWayCd = $("#settlWayCd").val();

                    var paymentHndsetPrice = getPaymentHndsetPrice();

                    if("02" == dlvryType ) {
                        //바로배송 결제
                        smartroUsimPay();
                        //buyType		: 'APPFORM_SELF_SUF'
                    } else if ("01" == settlWayCd ) {
                        smartroPhoneSettlPay();
                    } else if (pageObj.applDataForm.sesplsYn == "Y" && paymentHndsetPrice > 0 ) {
                        //자급제 결제
                        smartroSelfSufPay();
                    } else {

                        var usimKindsCd = $('#usimKindsCd').val();
                        var actionURL = "/m/appForm/appFormComplete.do";

                        //eSIM
                        if ("09" == usimKindsCd) {
                            actionURL = "/m/eSimForm/appFormComplete.do";
                        }

                        ajaxCommon.createForm({
                            method:"post"
                            ,action:actionURL
                        });

                        ajaxCommon.attachHiddenElement("requestKey",pageObj.requestKey);
                        ajaxCommon.formSubmit();
                    }

                }else if("-1" == jsonObj.RESULT_CODE) {
                    KTM.LoadingSpinner.hide(true);
                    $("#simpleDialog ._title").html("개통 진행 실패");
                    $("#simpleDialog ._detail").html("※ 신규가입은 명의당 30일이내 1회선만 가입 가능합니다.<br>추가 가입은 최근 가입하신 KT M모바일 회선 가입일을 기준으로 30일 경과된 시점에 신청 부탁드립니다.");
                    $("._simpleDialogButton").hide();
                    $("#btnOnline").hide();
                    $("#divChangeButton").show();

                    var el = document.querySelector('#simpleDialog');
                    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                    modal.open();

                }else {
                    KTM.LoadingSpinner.hide(true);
                    var strHtml = "시스템에 문제가 발생하였습니다.";
                    if (jsonObj.RESULT_MSG != undefined) {
                        strHtml = jsonObj.RESULT_MSG;
                    }
                    MCP.alert(strHtml);
                }
            })
    };

    var smartroUsimPay = function() {
        //SMARTRO 결제 시작
        var radUsimProd = $("input[name='radUsimProd']:checked").val();
        var prodCd = radUsimProd == "05" ? "01" : "02";

        var params=ajaxCommon.getSerializedData({
            buyType		: 'DIRECT_MALL_USIM'
            , prodCd: prodCd
            , reqBuyQnty	: "1"
            , reqType : "02"
            , requestKeyTemp:$("#requestKeyTemp").val()
            , prdtSctnCd:$("#prdtSctnCd").val()
        });

        ajaxCommon.getItemNoLoading({
                id:'payInit'
                ,cache:false
                ,url:'/m/smartro/payInit.do'
                ,data: params
                ,dataType:"json"
                ,errorCall : function () {
                    KTM.LoadingSpinner.hide(true);
                    MCP.alert(errMsg);
                }
            }
            ,function(data){
                if(data.resltCd == '0000'){
                    var buyerTel = reqDlvryForm.dlvryTelFn + reqDlvryForm.dlvryTelMn + reqDlvryForm.dlvryTelRn;

                    if($('#PayMethod').val() == undefined || $('#PayMethod').val() == ''){
                        $('#PayMethod').val(data.PayMethod);
                    }
                    $('#GoodsCnt').val(data.reqBuyQnty);
                    $('#GoodsName').val(data.GoodsName);
                    $('#Amt').val(data.Amt);

                    //$('#TaxAmt').val(data.TaxAmt);
                    //$('#TaxFreeAmt').val('0');
                    //$('#VatAmt').val(data.VatAmt);
                    $('#TaxAmt').val("");
                    $('#TaxFreeAmt').val("");
                    $('#VatAmt').val("");


                    $('#Moid').val(pageObj.applDataForm.selfDlvryIdx);
                    $('#Mid').val(data.Mid);
                    $('#ReturnUrl').val(data.ReturnUrl);
                    $('#StopUrl').val(data.StopUrl);
                    $('#BuyerName').val(reqDlvryForm.cstmrName);
                    $('#BuyerTel').val(buyerTel);
                    $('#BuyerEmail').val(pageObj.applDataForm.cstmrMail);
                    $('#MallIP').val(data.MallIP);
                    $('#EncryptData').val(data.EncryptData);
                    $('#EdiDate').val(data.ediDate);
                    $('#mode').val(data.mode);

                    var menu = 'DIRECT_FORM';  //DIRECTMALL
                    var radUsimProd = $("input[name='radUsimProd']:checked").val();
                    var usimProdId = radUsimProd == "05" ? "01" : "02";

                    //상점 예비 필드 - 메뉴,구매타입,상품코드
                    mallReserved = menu;
                    mallReserved += ',NowDlvry_Form' ;
                    mallReserved += ',' + usimProdId;
                    mallReserved += ',' + pageObj.requestKey;
                    mallReserved += ',' + $("#requestKeyTemp").val();

                    $('#MallReserved').val(mallReserved);

                    goPay();
                } else {
                    KTM.LoadingSpinner.hide(true);
                    MCP.alert("처리중 오류가 발생했습니다. 다시 시도해 주세요." );
                }
            })
    }

    var smartroSelfSufPay = function() {
        //SMARTRO 결제 시작
        var params=ajaxCommon.getSerializedData({
            buyType		: 'APPFORM_SELF_SUF'
            , requestKeyTemp:$("#requestKeyTemp").val()
            , requestKey:pageObj.requestKey
        });

        ajaxCommon.getItemNoLoading({
                id:'payInit'
                ,cache:false
                ,url:'/m/smartro/payInit.do'
                ,data: params
                ,dataType:"json"
                ,errorCall : function () {
                    MCP.alert(errMsg);
                    $('#btnNext').show();
                    $('#btnNext2').show();
                }
            }
            ,function(data){
                if(data.resltCd == '0000'){
                    var buyerTel = pageObj.applDataForm.cstmrMobileFn + reqDlvryForm.cstmrMobileMn + reqDlvryForm.cstmrMobileRn;

                    var radPayType = $("input:radio[name=radPayType]:checked").val() ;
                    if (radPayType != undefined && radPayType == "2" && pageObj.applDataForm.cardDcCd != "" ) {
                        //일반결제
                        $('#PayType').val("CARD");
                        //$('#NonUi').val("Y");
                        //모바일은 N으로 두어야 하나?? 특수가맹점용?
                        $('#NonUi').val("N");
                        $('#FnCd').val($('#cardDcCd').val());
                    } else {
                        $('#PayType').val("");
                        $('#NonUi').val("N");
                        $('#FnCd').val("");
                    }

                    if($('#PayMethod').val() == undefined || $('#PayMethod').val() == ''){
                        $('#PayMethod').val(data.PayMethod);
                    }
                    $('#GoodsCnt').val(data.reqBuyQnty);
                    $('#GoodsName').val(data.GoodsName);
                    $('#Amt').val(data.Amt);
                    //$('#TaxAmt').val(data.TaxAmt);
                    //$('#TaxFreeAmt').val('0');
                    //$('#VatAmt').val(data.VatAmt);
                    $('#TaxAmt').val("");
                    $('#TaxFreeAmt').val("");
                    $('#VatAmt').val("");

                    $('#Moid').val("F"+pageObj.requestKey);
                    $('#Mid').val(data.Mid);
                    $('#ReturnUrl').val(data.ReturnUrl);
                    $('#StopUrl').val(data.StopUrl);
                    $('#BuyerName').val(pageObj.applDataForm.cstmrName);
                    $('#BuyerTel').val(buyerTel);
                    $('#BuyerEmail').val(pageObj.applDataForm.cstmrMail);
                    $('#MallIP').val(data.MallIP);
                    $('#EncryptData').val(data.EncryptData);
                    $('#EdiDate').val(data.ediDate);
                    $('#mode').val(data.mode);

                    var menu = 'DIRECT_FORM';  //DIRECTMALL
                    var usimProdId = "01";

                    //상점 예비 필드 - 메뉴,구매타입,상품코드
                    //smartroSelfSufPay SELF_SUF_PAY
                    mallReserved = menu;
                    mallReserved += ',SELF_SUF_PAY' ;
                    mallReserved += ',' + usimProdId;
                    mallReserved += ',' + pageObj.requestKey;
                    mallReserved += ',' + $("#requestKeyTemp").val();

                    $('#MallReserved').val(mallReserved);

                    goPay();
                } else {
                    KTM.LoadingSpinner.hide(true);
                    MCP.alert("처리중 오류가 발생했습니다. 다시 시도해 주세요." );
                }
            })
    }



    //APPFORM_SETTL
    var smartroPhoneSettlPay = function() {
        //SMARTRO 결제 시작
        var radUsimProd = $("input[name='radUsimProd']:checked").val();
        var params=ajaxCommon.getSerializedData({
            buyType		: 'APPFORM_DIRECT_PHONE'
            , requestKeyTemp:$("#requestKeyTemp").val()
            , requestKey:pageObj.requestKey
            , prodNm : pageObj.phoneObj.prodNm
        });

        ajaxCommon.getItem({
                id:'payInit'
                ,cache:false
                ,url:'/m/smartro/payInit.do'
                ,data: params
                ,dataType:"json"
                ,errorCall : function () {
                    MCP.alert(errMsg);
                    $('._btnNext').show();
                }
            }
            ,function(data){
                if(data.resltCd == '0000'){
                    var buyerTel = pageObj.applDataForm.cstmrMobileFn + pageObj.applDataForm.cstmrMobileMn  + pageObj.applDataForm.cstmrMobileRn;

                    if($('#PayMethod').val() == undefined || $('#PayMethod').val() == ''){
                        $('#PayMethod').val(data.PayMethod);
                    }
                    $('#GoodsCnt').val(data.reqBuyQnty);
                    $('#GoodsName').val(data.GoodsName);
                    $('#Amt').val(data.Amt);
                    //$('#TaxAmt').val(data.TaxAmt);
                    //$('#TaxFreeAmt').val('0');
                    //$('#VatAmt').val(data.VatAmt);
                    $('#TaxAmt').val("");
                    $('#TaxFreeAmt').val("");
                    $('#VatAmt').val("");
                    $('#Moid').val(pageObj.requestKey);
                    $('#Mid').val(data.Mid);
                    $('#ReturnUrl').val(data.ReturnUrl);
                    $('#StopUrl').val(data.StopUrl);
                    $('#BuyerName').val(pageObj.applDataForm.cstmrName);
                    $('#BuyerTel').val(buyerTel);
                    $('#BuyerEmail').val(pageObj.applDataForm.cstmrMail);
                    $('#MallIP').val(data.MallIP);
                    $('#EncryptData').val(data.EncryptData);
                    $('#EdiDate').val(data.ediDate);
                    $('#mode').val(data.mode);

                    var menu = 'DIRECT_FORM';  //DIRECTMALL
                    var prodId = $("#prodId").val();;
                    //var usimProdId = $.trim($('#usimProdId').val());  //<===여기 다시..

                    //상점 예비 필드 - 메뉴,구매타입,상품코드
                    mallReserved = menu;
                    mallReserved += ',DIRECT_PHONE' ;
                    mallReserved += ',' + prodId;
                    mallReserved += ',' + pageObj.requestKey;
                    mallReserved += ',' + $("#requestKeyTemp").val();

                    $('#MallReserved').val(mallReserved);

                    goPay();
                } else {
                    MCP.alert("처리중 오류가 발생했습니다. 다시 시도해 주세요." );
                }
            })
    }

    function goPay() {
        // 스마트로페이 초기화
        smartropay.init({
            mode: $('#mode').val()		// STG: 테스트, REAL: 운영
        });

        // 스마트로페이 결제요청
        // 모바일용
        smartropay.payment({
            FormId : 'tranMgr'				// 폼ID
        });
    };


    $("#btnArsConn").click(function(){
        //var companyTel=  $("#moveCompany option:selected").attr("companyTel");
        if (pageObj.companyTel != "") {
            document.location.href="tel:"+pageObj.companyTel;
        }
    });

    //사전동의 요청
    var fnReqPreMoveAut = function() {
        var cstmrType = $("input:radio[name=cstmrType]:checked").val();
        var custIdntNo = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;

        var mpCode= $("#moveCompany option:selected").attr("mpCode");
        var varData = ajaxCommon.getSerializedData({
            npTlphNo:$.trim($("#moveMobile").val())
            ,bchngNpCommCmpnCd:mpCode
            ,slsCmpnCd:$("#cntpntShopId").val()
            ,custIdntNoIndCd:"01"
            ,custIdntNo:custIdntNo
            ,custNm:$.trim($("#cstmrName").val())
            ,custTypeCd:cstmrType
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
                var companyNm=  $("#moveCompany option:selected").attr("companyNm");
                var companyTel=  $("#moveCompany option:selected").attr("companyTel");
                pageObj.companyTel = companyTel;
                pageObj.applDataForm.globalNoNp1 = jsonObj.GLOBAL_NO  ;

                $("#divArs .ars-company__name").html(companyNm);
                $("#divArs .ars-company__number").html(companyTel);
                $("#isNpPreCheck").val("1");
                $("#moveMobile").prop("readonly","readonly");
                $("#btnNext").html("개통사전체크확인");
                $("#btnNext2").html("개통사전체크확인");
                $("#btnNext").html("동의 완료");
                $("#btnNext2").html("동의 완료");

                MCP.alert("<i class='c-icon c-icon--notice' aria-hidden='true'></i><br><br>받으신 문자 내 URL 동의 또는<br>확인버튼 클릭 후 안내되는 번호로 전화를 걸어<br><br>번호이동 확인(동의)를 진행 해 주세요. ", function() {
                    $("#divArs").show();
                    isValidateNonEmptyStep3();
                });

            } else {
                var strTitle = "번호이동 진행 실패";
                var layerType= "divDefaultButton" ;
                var strHtml = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.";

                if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_NP1_MSG[jsonObj.OSST_RESULT_CODE] != undefined) {
                    strHtml = SIMPLE_NP1_MSG[jsonObj.OSST_RESULT_CODE];
                } else if (jsonObj.ERROR_NE_MSG != undefined) {
                    strHtml = jsonObj.ERROR_NE_MSG;
                }

                if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_NP1_TYPE[jsonObj.OSST_RESULT_CODE] != undefined) {
                    layerType = SIMPLE_NP1_TYPE[jsonObj.OSST_RESULT_CODE];
                }
                $("#simpleDialog ._title").html(strTitle);
                $("#simpleDialog ._detail").html(strHtml);
                $("._simpleDialogButton").hide();
                $("#"+layerType).show();

                var el = document.querySelector('#simpleDialog');
                var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                modal.open();
            }
        })
    } ;




    //개통사전체크요청
    var fnReqPreCheck = function(isArs) {
        var onOffType = $("#onOffType").val() ;
        var errMsg = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다." ;
        var operType = $("#operType").val() ;

        if (isArs == undefined || isArs == null) {
            isArs = false;
        }

        if (!isArs) {
            KTM.LoadingSpinner.show();
        }

        if (operType == OPER_TYPE.NEW) {
            pageObj.applDataForm.reqSeq= pageObj.niceResSmsObj.reqSeq;
            pageObj.applDataForm.resSeq= pageObj.niceResSmsObj.resSeq;
        } else {
            var mpCode = $("#moveCompany option:selected").attr("mpCode");
            if(!isNpExceptionCompany(mpCode)){
                fnReqPreMoveAgree(); //사전동의 결과조회(NP3)
                if (""== pageObj.applDataForm.globalNoNp3) {
                    return;
                }
            }
        }

        pageObj.applDataForm.onOffType = onOffType ;
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
                pageObj.requestKey = jsonObj.REQUEST_KET ;
                pageObj.resNo = jsonObj.RES_NO ;
                pageObj.checkCnt = 0;
                pageObj.osstResultCode = "99999" ;
                fnSelfPreOpenCallBack();

            } else if(jsonObj.RESULT_CODE == "IP_FAIL") {

                KTM.LoadingSpinner.hide(true);
                MCP.alert("상담사 개통으로만 신청 가능합니다.<br/>상담사 개통 신청으로 진행합니다.", function(){
                    location.href = "/m/appForm/appCounselorInfo.do";
                });

            } else {

                if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_PC0_RE_TRY[jsonObj.OSST_RESULT_CODE] != undefined && pageObj.fnReqPreCheckCount < 30) {
                    setTimeout(function(){
                        pageObj.fnReqPreCheckCount++;
                        pageObj.applDataForm.globalNoNp3 = ""; //
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
                    errMsg = jsonObj.ERROR_NE_MSG ;
                }
                pageObj.osstResultCode = jsonObj.OSST_RESULT_CODE ;


                $("#simpleDialog ._title").html(strTitle);
                $("#simpleDialog ._detail").html(errMsg);
                $("._simpleDialogButton").hide();
                $("#divDefaultButton").show();

                var el = document.querySelector('#simpleDialog');
                var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                modal.open();
            }
        });

    };


    var fnReqSelf = function() {
        var onOffType = $("#onOffType").val() ;
        pageObj.applDataForm.onOffType = onOffType ;
        pageObj.applDataForm.requestKey = pageObj.requestKey ;

        //UPDATE 처리 하지 않도록 처리
        pageObj.applDataForm.cstmrPost = "";
        pageObj.applDataForm.cstmrAddr = "";
        pageObj.applDataForm.cstmrAddrDtl = "";


        //ktm 모바일
        var userDivision =  $("#userDivision").val();
        if (userDivision =="99") {
            KTM.LoadingSpinner.hide(true);
            $("#simpleDialog ._title").html("고객포탈");
            $("#simpleDialog ._detail").html("고객포탈");
            $("._simpleDialogButton").hide();
            $("#divChangeButton").show();
            var el = document.querySelector('#simpleDialog');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
            return;
        }


        var varData = ajaxCommon.getSerializedData(pageObj.applDataForm);

        ajaxCommon.getItemNoLoading({
            id:'reqSimpleOpenAjax'
            ,cache:false
            ,url:'/appform/reqSimpleOpenAjax.do'
            ,data: varData
            ,dataType:"json"
        } ,function(jsonObj){

            if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {

                // 자급제 보상 서비스 신청 정보 insert
                if (pageObj.applDataForm.rwdProdCd != ""){
                    applyRwd();
                }

                //sendScanImage();
                pageObj.checkCnt = 0;
                fnSelfOpenCallback();

            } else if(jsonObj.RESULT_CODE == "IP_FAIL") {

                KTM.LoadingSpinner.hide(true);
                MCP.alert("상담사 개통으로만 신청 가능합니다.<br/>상담사 개통 신청으로 진행합니다.", function(){
                    location.href = "/m/appForm/appCounselorInfo.do";
                });

            } else {
                KTM.LoadingSpinner.hide(true);
                var errMsg = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.";
                if (jsonObj.ERROR_NE_MSG != undefined) {
                    errMsg = jsonObj.ERROR_NE_MSG;
                }
                var operType = $("#operType").val() ;
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

    };

    var fnSelfPreOpenCallBack = function() {
        var varData = ajaxCommon.getSerializedData({
            resNo:pageObj.resNo
            ,prgrStatCd:"PC2"
        });

        ajaxCommon.getItemNoLoading({
                id:'conPreCheckAjax'
                ,cache:false
                ,url:'/appform/conPreCheckAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){

                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                    KTM.LoadingSpinner.hide(true);
                    var operType = $("#operType").val() ;
                    var onOffType = $("#onOffType").val() ;
                    if(operType == OPER_TYPE.NEW && onOffType == "7"){
                        setStep(3);
                    } else {
                        setStep(4);
                    }
                    $("#btnNext").html("다음");
                    $('#btnNext').show();
                    $("#btnNext2").html("다음");
                    $('#btnNext2').show();
                    $("#btnBefore").hide();
                    pageObj.osstResultCode = "9999" ;

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

                    var layerType= "divDefaultButton" ;
                    var strTitle = "";
                    if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_PC2_TYPE[jsonObj.OSST_RESULT_CODE] != undefined) {
                        layerType = SIMPLE_PC2_TYPE[jsonObj.OSST_RESULT_CODE];
                    }

                    if(operType == OPER_TYPE.NEW){
                        strTitle ="신규가입 진행 실패";
                    } else {
                        strTitle ="번호이동 진행 실패";
                    }

                    pageObj.osstResultCode = jsonObj.OSST_RESULT_CODE ;

                    $("#simpleDialog ._title").html(strTitle);
                    $("#simpleDialog ._detail").html(strHtml);
                    $("._simpleDialogButton").hide();
                    $("#"+layerType).show();

                    var el = document.querySelector('#simpleDialog');
                    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                    modal.open();
                }
            });

    };

    var fnSelfOpenCallback = function() {

        $("#btnBefore").hide();
        pageObj.step = 6;
        $("#btnNext").html("개통확인");
        $("#btnNext2").html("개통확인");
        $('#btnNext').removeClass('is-disabled');


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
                //완료페이지
                //$("#step5").hide();

                var usimKindsCd = $('#usimKindsCd').val();
                var actionURL = "/m/appForm/appFormComplete.do";

                //eSIM
                if ("09" == usimKindsCd) {
                    actionURL = "/m/eSimForm/appFormComplete.do";
                }

                ajaxCommon.createForm({
                    method:"post"
                    ,action:actionURL
                });



                ajaxCommon.attachHiddenElement("requestKey",pageObj.requestKey);
                ajaxCommon.formSubmit();


            } else if ("9999" ==  jsonObj.RESULT_CODE) {
                if (pageObj.checkCnt < 21) {
                    ++pageObj.checkCnt
                    setTimeout(function(){
                        fnSelfOpenCallback();
                    }, 5000);  //20000
                } else {
                    KTM.LoadingSpinner.hide(true);
                    MCP.alert("아직 개통 상태 정보가 확인되지 않습니다.<br>확인까지 최대 2분이 소요 될 수 있습니다.");
                }

            } else {
                KTM.LoadingSpinner.hide(true);
                var rtnObj = jsonObj.RESULT_OBJ;
                if ("|RESULT_2028|RESULT_2016|RESULT_2028_01|RESULT_9000_01".indexOf(jsonObj.OSST_RESULT_CODE) > 0) {
                    //신용카드 유효성 초기화
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

                var layerType= "divDefaultButton" ;
                var strTitle = "";
                if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_OP2_TYPE[jsonObj.OSST_RESULT_CODE] != undefined) {
                    layerType = SIMPLE_OP2_TYPE[jsonObj.OSST_RESULT_CODE];
                }

                var operType = $("#operType").val() ;
                if(operType == OPER_TYPE.NEW){
                    strTitle ="신규가입 진행 실패";
                } else {
                    strTitle ="번호이동 진행 실패";
                }

                $("#simpleDialog ._title").html(strTitle);
                $("#simpleDialog ._detail").html(strHtml);
                $("._simpleDialogButton").hide();
                $("#"+layerType).show();

                var el = document.querySelector('#simpleDialog');
                var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                modal.open();

            }
        });
    };

    var sendScanImage = function () {
        var varData = ajaxCommon.getSerializedData({
            requestKey : pageObj.requestKey
            , cstmrName : $("#cstmrName").val()
        });
        //비동기
        ajaxCommon.getItem({
                id:'sendScan'
                ,cache:false
                ,url:'/appform/sendScanAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonObj){
                //nothing
            });
    }

    //아무나 SOLO 결합 가입 버튼
    $('input[name="combineSoloType"]').on('change', function() {
      if ($(this).val() === 'Y') {
        $('#divReg').show();
      } else {
        $('#divReg').hide();
      }
    });

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
            $("#ulAdditionListBase").append(getRowTemplate(objList));
        } else if(chargeFlag == "N") {
            $("#ulAdditionListFree").html('');
            $("#ulAdditionListFree").append(getRowTemplate(objList));
            pageObj.initAddition =1;
            $("#ulAdditionListFree ").addClass('c-accordion');
            KTM.initialize();

        } else if(chargeFlag == "Y") {
            $("#ulAdditionListPrice").html('');
            $("#ulAdditionListPrice").append(getRowTemplate(objList));
            pageObj.additionList = objList;
            pageObj.initAddition =2;
            $("#ulAdditionListPrice ").addClass('c-accordion');
            KTM.initialize();
        }
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

    var getRowTemplate = function(objList){
        var arr =[];
        for(i = 0 ; i < objList.length ; i++) {
            if (pageObj.additionCatchCallKey == objList[i].additionKey ) {
                //캐치콜 플러스 제외
                continue;
            }
            arr.push("<li class='c-accordion__item'>\n");
            arr.push("    <div class='c-accordion__head'>\n");
            arr.push("        <div class='c-accordion__check'>\n");
            if (pageObj.additionTempKeyList.includes(objList[i].additionKey+"")) {
                arr.push("        <input class='c-checkbox c-checkbox--type3' type='checkbox' name='additionName' id='additionName"+objList[i].additionKey+"' value='"+objList[i].additionKey+"' rantal="+objList[i].vatRantal+" txtName='"+objList[i].additionName+"' chargeFlag='"+objList[i].chargeFlag+"' checked />\n");
            } else {
                arr.push("        <input class='c-checkbox c-checkbox--type3' type='checkbox' name='additionName' id='additionName"+objList[i].additionKey+"' value='"+objList[i].additionKey+"' rantal="+objList[i].vatRantal+" txtName='"+objList[i].additionName+"' chargeFlag='"+objList[i].chargeFlag+"'/>\n");
            }
            arr.push("        <label class='c-label' for='additionName"+objList[i].additionKey+"'>"+objList[i].additionName+"</label>\n");
            arr.push("        </div>\n");
            arr.push("        <button class='c-accordion__button u-ta-right _btnAccordion' type='button' aria-expanded='false' data-acc-header='#chkASP"+objList[i].additionKey+"' id='btnChkAsp"+objList[i].additionKey+"' href='javascript:void(0);' rateAdsvcProdRelSeq='"+objList[i].rateAdsvcProdRelSeq+"' >\n");
            arr.push("        <div class='c-accordion__trigger'>\n");
            if (objList[i].rantal > 0) {
                arr.push("        <span class='c-text c-text--type4'>"+numberWithCommas(objList[i].vatRantal)+"원</span>\n");
            }
            arr.push("        </div>\n");
            arr.push("        </button>\n");
            arr.push("    </div>\n");
            arr.push("    <div class='c-accordion__panel expand c-expand' id='chkASP"+objList[i].additionKey+"'>\n");
            arr.push("        <div class='c-accordion__inside'>\n");
            arr.push("          <div class='c-text c-text--type3'></div>\n");
            arr.push("        </div>\n");
            arr.push("    </div>\n");
            arr.push("</li>\n");
        }

        return arr.join('');
    }

    $(document).on("click","._btnAccordion",function() {
        var rateAdsvcProdRelSeq = $(this).attr("rateAdsvcProdRelSeq");
        var $thatObj = $(this).parent().parent().find('.c-accordion__inside > div');
        if (rateAdsvcProdRelSeq == null || rateAdsvcProdRelSeq == "" || rateAdsvcProdRelSeq == "null") {
            $thatObj.html("상세정보가 없습니다.");
            return;
        }

        if ($thatObj.html() != "" ) {
            return; //두번 호출 방지
        }
        /*
        RATE_ADSVC_PROD_REL_SEQ <> RATE_ADSVC_GDNC_SEQ 차이가 뭘까?
        */
        var varData = ajaxCommon.getSerializedData({
            subTabId : 'sub1'
            , rateAdsvcGdncSeq : rateAdsvcProdRelSeq
        });

        ajaxCommon.getItem({
                id:'adsvcContent'
                ,cache:false
                ,url:"/m/rate/adsvcContent.do"
                ,data: varData
                ,dataType:"html"
                , errorCall : function () {
                    $thatObj.html("상세정보가 없습니다.");
                }
            }
            ,function(strHtml){
                $thatObj.html(strHtml);
            });

    });

    //부가 서비스 추가 삭제 버튼 클릭
    $("#btnAddition, #btnAddition2").click(function(){
        if (pageObj.initAddition < 1) {
            getAdditionList("N");
            getAdditionList("Y");
        }
    });

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
        ajaxCommon.getItem({
                id:'selectInsrProdList'
                ,cache:false
                ,url:'/appform/selectInsrProdListAjax.do'
                ,data: varData
                ,dataType:"json"
            }
            ,function(jsonList){
                pageObj.insrProdList = jsonList;
                var insrProdCdTemp = $("#insrProdCdTemp").val() ;
                $("#ulInsrProdList").empty();
                for(i = 0 ; i < jsonList.length ; i++) {
                    $("#ulInsrProdList").append(getRowTemplateInsrProd(jsonList[i], insrProdCdTemp ));
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
        validator.config['isAuth'] = 'isNonEmpty';  //TEST 위한

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

        arr.push("<li class='c-card c-card--type3'>\n");
        arr.push("    <div class='c-card__box'>\n");
        arr.push("        <div class='c-chk-wrap'>\n");
        if (obj.insrProdCd == insrProdCdParam) {
            arr.push("            <input class='c-checkbox c-checkbox--type4' id='insrProdCD_"+obj.insrProdCd+"' value='"+obj.insrProdCd+"' type='radio' name='insrProdCD' checked >\n");
        } else {
            arr.push("            <input class='c-checkbox c-checkbox--type4' id='insrProdCD_"+obj.insrProdCd+"' value='"+obj.insrProdCd+"' type='radio' name='insrProdCD'>\n");
        }
        arr.push("            <label class='c-card__label' for='insrProdCD_"+obj.insrProdCd+"'>\n");
        arr.push("            <span class='c-hidden'>선택</span>\n");
        arr.push("            </label>\n");
        arr.push("        </div>\n");

        var contentHtml= INSR_PROD_CONTENT[obj.insrProdCd];
        if (contentHtml != null) {
            arr.push(contentHtml);
        } else {
            arr.push("        <div class='c-card__title'>\n");
            arr.push("            <b>"+obj.insrProdNm+"</b>   \n");
            arr.push("        </div>\n");
        }

        arr.push("    </div>\n");
        arr.push("</li> \n");
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

            $("#btnInsrAllCheck").prop("checked",false);
            $("#clauseInsrProdFlag").prop("checked",false);
            $("#clauseInsrProdFlag02").prop("checked",false);
            $("#clauseInsrProdFlag03").prop("checked",false);
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

    var getGiftList = function (buyType){
        if (pageObj.giftList.length > 0) {
            return null;
        }

        var usimOrgnId = pageObj.usimOrgnId ;
        if (usimOrgnId == null || usimOrgnId == "") {
            usimOrgnId = $("#cntpntShopId").val();
        }

        var enggMnthCnt = $("#enggMnthCnt").val();
        if (enggMnthCnt == null || enggMnthCnt == "") {
            enggMnthCnt = "0";
        }

        //5,5
        var onOffTypeVal =  $("#onOffType").val() ;
        var varData = ajaxCommon.getSerializedData({
            onOffType : onOffTypeVal
            , operType : $("#operType").val()
            , reqBuyType : $("#reqBuyType").val()
            , agrmTrm : enggMnthCnt
            , rateCd : $("#socCode").val()
            , orgnId : usimOrgnId
            , defaultOrgnId : $("#cntpntShopId").val()
            , modelId : $("#modelId").val()
        });

        ajaxCommon.getItem({
                id : 'getGiftList'
                , cache : false
                , url : '/appForm/giftBasListDefaultAjax.do'
                , data : varData
                , dataType : "json"
                , errorCall : function () {
                    //nothing
                }
            }
            ,function(resultList){
                var $divGift = $('#divGift');
                pageObj.giftList = resultList;
                $divGift.empty();

                if(resultList.length > 0){

                    var chooseGiftPrmt = resultList.filter(item => item.prmtType == "C");
                    if(chooseGiftPrmt.length > 0){
                        $divGift.append("<hr class='c-hr c-hr--type1 c-expand'>");
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
    }

    var getGiftRowTemplate = function(obj) {
        var arr =[];

        var giftPromotionDtlList = obj.giftPromotionDtlList ;

        if("C" == obj.prmtType){ // 선택사은품
            arr.push("<div class='gift-free-wrap u-mb--14'>\n");
            arr.push("    <h5 class='gift-free-tit'>"+obj.prmtNm+"</h5>\n");
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

    $(document).on("click","input:checkbox[name=checkboxGift]",function() {
        var $thisObj = $(this);
        var prmtId = $thisObj.attr("prmtId") ;
        var prdtId = $(this).val();
        var prmtType = $thisObj.attr("prmtType") ;
        var limitCnt = parseInt($thisObj.attr("limitCnt"), 10);
        var selectCnt = $("input:checkbox[name=checkboxGift][prmtId="+prmtId+"]:checked").length ;

        if ("D" != prmtType) {
            if (limitCnt < selectCnt) {
                MCP.alert("사은품 선택 개수를 초과하였습니다.",function (){
                    $thisObj.prop("checked",false);
                });
            }
        } else {
            $thisObj.prop("checked",true);
        }
    });

    //할부 금액 정보 조회 및 요금제
    var getListSale = function() {

        var prodId = $("#prodId").val();
        var agrmTrm = $("#enggMnthCnt").val();
        var modelMonthlyVal = $("#modelMonthly").val();
        var modelSalePolicyCode = $("#modelSalePolicyCode").val();
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
            ,rateCd:$("#socCode").val()
            ,onOffType:$("#onOffType").val()
            ,noArgmYn:"Y"
            ,agrmTrm:agrmTrm
            ,plcySctnCd:plcySctnCd
            ,modelMonthly:modelMonthlyVal
        });

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
                    pageObj.rateNm = objList[0].rateNm;
                    pageObj.rateObj = objList[0]

                    $("#payMnthChargeAmtTxt").html(numberWithCommas(objList[0].payMnthChargeAmt));
                    $("#baseAmt").html(numberWithCommas(objList[0].baseVatAmt));
                    $("#dcAmtTxt").html(numberMinusWithCommas(objList[0].dcVatAmt));
                    $("#addDcAmt").html(numberWithCommas(objList[0].addDcVatAmt));
                    $("#promotionDcAmtTxt").html(numberWithCommas(objList[0].promotionDcAmt));

                    $("#hndstAmtTxt").html(numberWithCommas(objList[0].hndstAmt));
                    $("#subsdAmtTxt").html(numberMinusWithCommas(objList[0].subsdAmt));
                    $("#agncySubsdAmtTxt").html(numberMinusWithCommas(objList[0].agncySubsdAmt));
                    $("#instAmtTxt").html(numberWithCommas(objList[0].instAmt));
                    $("#payMnthAmtTxt").html(numberWithCommas(objList[0].payMnthAmt));
                    $("#totalInstCmsnTxt").html(numberWithCommas(objList[0].totalInstCmsn));
                    $("#paySumMnthTxt").html(numberWithCommas(getMnthChargeAmt()));
                    $("#paySumMnthTxt2").html(numberWithCommas(getMnthChargeAmt()));

                    var prdtNmTemp = "";
                    if (pageObj.prdtNm  != "") {
                        prdtNmTemp = " / " +pageObj.prdtNm ;
                    }
                    var operTypeNm = " / " + $('#operTypeNm').val() ;
                    var rateNm = " / " +  pageObj.rateNm
                    $('#bottomTitle').text($('#prdtSctnCd').val()+prdtNmTemp + operTypeNm + rateNm) ;
                    $('#ddOperTypeNm').text($('#operTypeNm').val()) ;

                    ;
                }
            });

        if (modelSalePolicyCode != "") {
            ajaxCommon.getItem({
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
                    //$("#prdtSctnCd").val("3G"); //TEST설정
                });


            if (REQ_BUY_TYPE.USIM == reqBuyType ) {
                //USIM 상품정보 조회
                var varDataUsim = ajaxCommon.getSerializedData({
                    prdtId:$("#modelId").val()
                    //, salePlcyCd:modelSalePolicyCode
                });

                ajaxCommon.getItem({
                        id:'getUsimBasInfoAjax'
                        , cache:false
                        , url:'/appForm/getUsimBasInfoAjax.do'
                        , data: varDataUsim
                        , dataType:"json"
                        , async : false
                    }
                    ,function(obj){
                        if (obj.prdtNm != null ) {
                            pageObj.prdtNm =obj.prdtNm ;

                            var prdtNmTemp = "";
                            if (pageObj.prdtNm  != "") {
                                prdtNmTemp = " / " +pageObj.prdtNm ;
                            }
                            var operTypeNm = " / " + $('#operTypeNm').val() ;
                            var rateNm = " / " +  pageObj.rateNm
                            $('#bottomTitle').text($('#prdtSctnCd').val()+prdtNmTemp + operTypeNm + rateNm) ;
                            $('#ddOperTypeNm').text($('#operTypeNm').val()) ;
                        }
                    });
            }
        }



        if (prodId != "" ) {
            var varData2 = ajaxCommon.getSerializedData({
                prodId:prodId
            });
            ajaxCommon.getItem({
                    id:'phoneProdInfoAjax'
                    ,cache:false
                    ,url:'/appForm/phoneProdInfoAjax.do'
                    ,data: varData2
                    ,dataType:"json"
                }
                ,function(phoneObj){
                    pageObj.phoneObj = phoneObj ;


                    if (pageObj.phoneObj.sesplsYn == "Y" && REQ_BUY_TYPE.USIM == reqBuyType) {
                        pageObj.applDataForm.sesplsYn = "Y";
                        pageObj.applDataForm.hndsetSalePrice = pageObj.phoneObj.outUnitPric;
                        pageObj.applDataForm.sesplsProdId = $('#sesplsProdId').val();

                        var phoneSntyList =  pageObj.phoneObj.phoneSntyBasDtosList ;
                        var sesplsProdId = $('#sesplsProdId').val() ;
                        var thisPhoneSnty = null;

                        for(var i = 0 ; i < phoneSntyList.length ; i++) {
                            if (sesplsProdId == phoneSntyList[i].hndsetModelId) {
                                thisPhoneSnty = phoneSntyList[i];
                                break;
                            }
                        }
                        if (thisPhoneSnty == null) {
                            thisPhoneSnty = phoneSntyList[0];
                        }

                        pageObj.applDataForm.sntyColorCd= thisPhoneSnty.atribValCd1;//$("#modelId option:selected").attr("atribValCd1") ; //색상코드
                        pageObj.applDataForm.sntyCapacCd = thisPhoneSnty.atribValCd2;// $("#modelId option:selected").attr("atribValCd2") ; //용량코드


                        $('._bottomTitle').text(pageObj.phoneObj.prodNm +" / " + thisPhoneSnty.atribValNm2+" / " + thisPhoneSnty.atribValNm1) ;
                        viewSelfPhoneChargeInfo();

                    } else if (REQ_BUY_TYPE.PHONE == reqBuyType) {
                        pageObj.applDataForm.sesplsYn = "N";
                        pageObj.applDataForm.hndsetSalePrice = 0;

                        var phoneSntyList =  pageObj.phoneObj.phoneSntyBasDtosList ;
                        var modelId = $('#modelId').val() ;
                        var thisPhoneSnty = null;

                        for(var i = 0 ; i < phoneSntyList.length ; i++) {
                            if (modelId == phoneSntyList[i].hndsetModelId) {
                                thisPhoneSnty = phoneSntyList[i];
                                break;
                            }
                        }
                        if (thisPhoneSnty == null) {
                            thisPhoneSnty = phoneSntyList[0];
                        }

                        pageObj.applDataForm.sntyColorCd= thisPhoneSnty.atribValCd1;//$("#modelId option:selected").attr("atribValCd1") ; //색상코드
                        pageObj.applDataForm.sntyCapacCd = thisPhoneSnty.atribValCd2;// $("#modelId option:selected").attr("atribValCd2") ; //용량코드

                        $('._bottomTitle').text(pageObj.phoneObj.prodNm +" / " + thisPhoneSnty.atribValNm2+" / " + thisPhoneSnty.atribValNm1) ;
                        viewSelfPhoneChargeInfo();
                    } else {
                        pageObj.applDataForm.sesplsYn = "N";
                        pageObj.applDataForm.hndsetSalePrice = 0;
                    }
                    $('#ddProdNm').text(pageObj.phoneObj.prodNm) ;
                });


        }
    } ;


    var getAdditionTempList = function() {
        var requestKey = $("#requestKeyTemp").val() ;
        var varData = ajaxCommon.getSerializedData({
            requestKey:requestKey
        });

        ajaxCommon.getItemNoLoading({
                id:'getAdditionTempListAjax'
                ,cache:false
                ,url:'/appForm/getAdditionTempListAjax.do'
                ,data: varData
                ,dataType:"json"
                ,async:false
                ,errorCall : function () {
                    //nothing
                }
            }
            ,function(objList){
                if (objList.length > 0) {
                    objList.forEach(function(item){
                        pageObj.additionTempKeyList.push(item);
                    });
                }
            });
    };




    // 서식지 설계 배너 조회 (O001)
    var displayO001BannerList = function() {
        var varData = ajaxCommon.getSerializedData({
            bannCtg : 'O001'
        });

        ajaxCommon.getItem({
                id : 'getO001BannerListAjax'
                , cache : false
                , async : false
                , url : '/m/bannerListAjax.do'
                , data : varData
                , dataType : "json"
            }
            ,function(result){
                let bannerHtml = "";
                let returnCode = result.returnCode;
                let message = result.returnCode;
                let bannerList = result.result;

                if(returnCode == "00") {
                    if(bannerList.length > 0) {
                        $.each(bannerList, function(index, value) {
                            let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
                            let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
                            let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
                            let v_bannDesc = ajaxCommon.isNullNvl(value.bannDesc, "");
                            let v_bannApdDesc = ajaxCommon.isNullNvl(value.bannApdDesc, "");
                            let v_bannBgColor = ajaxCommon.isNullNvl(value.bannBgColor, "");
                            let v_bannImg = ajaxCommon.isNullNvl(value.mobileBannImgNm, "");
                            let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                            let v_mobileLinkUrl = ajaxCommon.isNullNvl(value.mobileLinkUrl, "");

                            let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");
                            let v_linkUrlAdr = ajaxCommon.isNullNvl(value.linkUrlAdr, "");

                            if(v_linkTarget == 'Y' && v_linkUrlAdr != ''){
                                bannerHtml += '<div class="swiper-slide" onclick="javascript:insertBannAccess(\'' + v_bannSeq + '\',\'' + v_bannCtg + '\');location.href=\'' + v_linkUrlAdr + '\'";>';
                            }else{
                                bannerHtml += '<div class="swiper-slide">';
                            }

                            bannerHtml += '         <button class="swiper-event-banner__button">';
                            bannerHtml += '             <div class="swiper-event-banner__item">';
                            bannerHtml += '                 <img src="' + v_bannImg + '" alt="' + v_bannNm + '">';
                            bannerHtml += '             </div>';
                            bannerHtml += '         </button>';
                            bannerHtml += '     </div>';

                            if(index == 19) {
                                return false;
                            }
                        });

                        $("#O001").html(bannerHtml);
                    }else{
                        $('.top-sticky-banner').hide();
                    }
                } else {
                    MCP.alert(message);
                }
            });
    }();

    //0, 100, N --> 0세~100세 신청불가
    //0, 100, Y --> 0세~100세 신청가능
    var checkPriceLimitForAge = function(ageInt) {
        if (pageObj.priceLimitObj != null && pageObj.priceLimitObj.dtlCd != null && pageObj.priceLimitObj.dtlCd != "") {
            var priceLimitObj = pageObj.priceLimitObj ;
            var startAge= parseInt(priceLimitObj.expnsnStrVal1,10) ;
            var endAge= parseInt(priceLimitObj.expnsnStrVal2,10) ;
            var division =priceLimitObj.expnsnStrVal3;

            if ("N" == division ) {
                if (startAge <= ageInt && endAge  >=  ageInt) {
                    return false;  //불가능
                } else {
                    return true;  //가능
                }
            } else {
                if (startAge <= ageInt && endAge  >=  ageInt) {
                    return true;  //가능
                } else {
                    return false;  //불가능
                }
            }
        } else {
            return true;  //가능
        }
    }

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
                ,errorCall : function () {
                    //NOTHING
                }
            }
            ,function(jsonObj){
                pageObj.priceLimitObj = jsonObj ;
            });
    }();





    var showJoinUsimPrice = function() {
        var prdtSctnCd = $("#prdtSctnCd").val();
        var joinPriceIsPay = $("#joinPriceIsPay").val();
        var usimPriceIsPay = $("#usimPriceIsPay").val();
        var usimPriceNfcIsPay = $("#usimPriceNfcIsPay").val();
        var usimPrice = $("#usimPrice").val();
        var joinPrice = $('#joinPrice').val();

        var radUsimProd = $("input[name='radUsimProd']:checked").val();
        if (radUsimProd == undefined ) {
            radUsimProd = "05";
        }

        if ("06" == radUsimProd ) {
            usimPrice =  $("#usimPriceNfc").val();
        }


        if (joinPriceIsPay == "Y") {
            $("#joinPriceTxt").html(numberWithCommas(joinPrice)+" 원");
        } else {
            $("#joinPriceTxt").html("<span class='c-text u-td-line-through'>"+numberWithCommas(joinPrice)+" 원</span>(무료)");
        }

        /*if ("06" == radUsimProd ) {
            if (usimPriceNfcIsPay == "Y") {
                $("#usimPriceTxt").html(numberWithCommas(usimPrice)+" 원");
            } else {
                $("#usimPriceTxt").html("<span class='c-text u-td-line-through'>"+numberWithCommas(usimPrice)+" 원</span>(무료)");
            }
            $("._isNowDlvryTip").hide();
            $("._isQuickDlvryTip").show();

        } else {*/
        if (usimPriceIsPay == "Y") {
            $("#usimPriceTxt").html(numberWithCommas(usimPrice)+" 원");
        } else {
            $("#usimPriceTxt").html("<span class='c-text u-td-line-through'>"+numberWithCommas(usimPrice)+" 원</span>(무료)");
        }
        $("._isNowDlvryTip").show();
        $("._isQuickDlvryTip").hide();
        //}


    }




  //가입비, 유심비 , 무료 , 유료
    var getJoinUsimPrice = (function () {

        var fn = function () {
            var prdtIndCd = $('#usimKindsCd').val() == '09' ? '10' : '';
            var dataType;
            // 단말구매시 일때만
            // getListSale()에 의해 "LTE" or "5G" 였던 prdtSctnCd 값이 "LTE5G"로 바뀔수도있어 기존값으로 비교후 재새팅
            if ($('#reqBuyType').val() == REQ_BUY_TYPE.PHONE) {
                if ($("#prdtSctnCd").val() != $("#prdtSctnCdTemp").val()) {
                    dataType = $("#prdtSctnCdTemp").val();
                } else {
                    dataType = $("#prdtSctnCd").val();
                }
            } else {
                dataType = $("#prdtSctnCd").val();
            }

            var varData = ajaxCommon.getSerializedData({
                orgnId: $("#cntpntShopId").val(),
                operType: $("#operType").val(),
                dataType: dataType,
                prdtIndCd: prdtIndCd,
                rateCd: $("#socCode").val(),
                reqBuyType: $("#reqBuyType").val()
            });

            ajaxCommon.getItemNoLoading({
                id: 'getSimInfo',
                cache: false,
                url: '/usim/getSimInfoAjax.do',
                data: varData,
                dataType: "json",
                async: false
            }, function (jsonObj) {
                $("#joinPrice").val(jsonObj.JOIN_PRICE);
                $("#usimPrice").val(jsonObj.SIM_PRICE);
                $("#joinPriceIsPay").val(jsonObj.JOIN_IS_PAY);
                $("#usimPriceIsPay").val(jsonObj.SIM_IS_PAY);
                $("#usimPriceNfcIsPay").val(jsonObj.NFC_SIM_IS_PAY);

                $("#spCommUsimPrice").html(numberWithCommas(jsonObj.SIM_PRICE));
                $("#spCommUsimNfcPrice").html(numberWithCommas($("#usimPriceNfc").val()));
            });

            showJoinUsimPrice();
        };

        // 즉시 실행
        fn();

        // 이후 재호출 가능하도록 함수 반환
        return fn;

    })();

    $(document).on('change', 'input[name="onOffLineUsim"]', function () {

        // 단말구매(reqBuyType => MM)가 아니면 리턴
        if ($('#reqBuyType').val() !== REQ_BUY_TYPE.PHONE) {
            return;
        }

        var onOffVal = $(this).val(); // 보유 / 미보유 값

        // 유심 보유 선택시 0원처리 후 리턴
        if (onOffVal === 'offLine') {
            $("#usimPrice").val(0);

            showJoinUsimPrice();
            return;
        }
        // 유심 미보유 선택시 M전산 공통코드 8800원 세팅
        getJoinUsimPrice();
    });


    getListSale();
    getAdditionBetaList();
    //기본제공 부가 서비스 조회
    getAdditionList("F");
    //기본 부가서비스 설정

    $("input:checkbox[name=additionName]").each(function(index){
        $(this).prop("checked", "checked");
    });
    // 부가 서비스 확인  클릭 (기본제공 부가서비스 설정 처리)
    $("#btnSetAddition").trigger("click") ;





    var onOffType = $("#onOffType").val() ;
    if(onOffType == "7"){
        $("._self").show();
        $("._noSelf").hide();
    } else {
        $("._self").hide();
        $("._noSelf").show();
    }

    $("input:radio[name=cstmrType]:checked").trigger("click");



    //$("._divStep").hide();
    //$("#step5").show();



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
            openPage('outLink','/nice/popNiceAuth.do?sAuthType=M&mType=Mobile','');
            pageObj.niceType = NICE_TYPE.RWD_PROD; // 본인인증 유형 세팅
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
        ajaxCommon.getItem({
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

        arr.push("<div class='c-card c-card--type3'>\n");
        arr.push("  <div class='c-card__box'>\n");
        arr.push("      <div class='c-chk-wrap'>\n");
        if (rwdObj.rwdProdCd == rwdProdCdTemp) {
            arr.push("          <input class='c-checkbox c-checkbox--type4' id='rwdProdCD_"+rwdObj.rwdProdCd+"' value='"+rwdObj.rwdProdCd+"' type='radio' name='rwdProdCD' checked>\n");
        }else{
            arr.push("          <input class='c-checkbox c-checkbox--type4' id='rwdProdCD_"+rwdObj.rwdProdCd+"' value='"+rwdObj.rwdProdCd+"' type='radio' name='rwdProdCD'>\n");
        }
        arr.push("          <label class='c-card__label' for='rwdProdCD_"+rwdObj.rwdProdCd+"'>\n");
        arr.push("             <span class='c-hidden'>선택</span>\n");
        arr.push("          </label>\n");

        var rwdContentHtml= RWD_PROD_CONTENT[rwdObj.rwdProdCd];
        if (!!rwdContentHtml) {
            arr.push(rwdContentHtml);
        } else if(rwdObj.rwdProdNm != null && rwdObj.rwdProdNm != ""){
            arr.push("<div class='c-card__title'>\n");
            arr.push("  <strong>"+rwdObj.rwdProdNm+"</strong>\n");
            arr.push("</div>\n");
        }else{
            // 내용물 또는 서비스명이 존재하지 않는 경우 화면에 표출하지 않음
            return '';
        }

        arr.push("      </div>\n");
        arr.push("  </div>\n");
        arr.push("</div>\n");
        return arr.join('');
    }

    // 자급제 보상 서비스 선택 데이터 없음 영역 클릭 시 이벤트
    $('#btnRwdProd2').click(function(){
        $("#btnRwdProd").trigger("click") ;
    });

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

        if(inputPrice.length <= 0){// 입력값이 존재하지 않은 경우
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
                var resultMsg= (!jsonObj || !jsonObj.RESULT_MSG) ? "서비스 처리중 오류가 발생 하였습니다." : jsonObj.RESULT_MSG;

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

                    // 자급제 보상서비스 신청 버튼 활성화/비활성화 처리
                    isValidateNonEmptyStep4();
                }else{
                    MCP.alert("사용불가한 단말기 정보값입니다.<br/>다른 단말기 정보값을 입력 바랍니다.");
                    return false;
                }
            });
    });

    // 자급제 보상서비스 증빙서류 등록 클릭 이벤트
    $("#rwdFile1").click(function(){
        // 증빙서류가 이미 등록되어 있는 경우
        if($(".staged_img-list .staged-image").length >= 1){
            MCP.alert("등록된 첨부파일이 존재합니다.<br>등록된 항목 삭제 후 재 등록 바랍니다.");
            return false;
        }
        return true;
    });
    // ================= END: 자급제 보상 서비스 관련 로직 =================


    //TEST 위해
    //setStep(4);

    getAdditionList("N");
    getAdditionList("Y");

    //아무나 SOLO 결합 가능여부
    getCombineSoloType($("#socCode").val());

    let additionCatchCallList =  pageObj.additionList.filter(additionTemp => additionTemp.additionKey === pageObj.additionCatchCallKey);
    if (additionCatchCallList != null && additionCatchCallList.length > 0) {
        pageObj.additionCatchCall = additionCatchCallList[0];
    }

    updateFathSkipBtnVisibility();
}); // end of document.ready ---------------------------------

//셀프개통신규 제한 기능
//기존 - 신규/번호이동 구분없이 eSIM/USIM 각 90일 이내 1회선으로 개통 제한
//변경 - 2026.03 - 신규(셀프/상담사)만 30일 이내 1회선으로 개통 제한
var checkLimit = function(reqSeq, resSeq) {

    var rtnObj = false ;

    //셀프개통신규 다회선 제한 기능
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
            } else if ("-1" ==  jsonObj.RESULT_CODE ) {
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
        //변경 - 2026.03 - 신규(셀프/상담사)만 30일 이내 1회선으로 개통 제한
        var onOffType = $("#onOffType").val() ;
        if(onOffType == "5" || onOffType == "7") {
            if(!checkLimit(prarObj.reqSeq, prarObj.resSeq)) return null;
        }

        var authInfoJson= {cstmrName: cstmrName , cstmrNativeRrn: cstmrNativeRrn
            , authType:prarObj.authType, operType : operType , onOffType : onOffType, ncType: ncType};

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
            $('#btnAuthInsr').addClass('is-complete').html("휴대폰 인증 완료");
            pageObj.niceHistInsrProdSeq = niceAuthObj.resAuthOjb.niceHistSeq ;
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

    } else if(pageObj.niceType == NICE_TYPE.RWD_PROD){ // 자급제 보상 서비스 휴대폰 인증

        var authInfoJson= {cstmrName: "rwdProdNm" , cstmrNativeRrn: "rwdProdRrn", authType:prarObj.authType , isCommonPrd:false, ncType: ncType};

        var isAuthDone= authCallback(authInfoJson);
        if(isAuthDone){ // 본인인증 최종 성공
            $("#isAuthRwd").val("1");
            $('#btnAuthRwd').addClass('is-complete').html("휴대폰 인증 완료");
            pageObj.niceHistRwdProdSeq = niceAuthObj.resAuthOjb.niceHistSeq;
            isValidateNonEmptyStep4();
            return null;
        } else { // 본인인증 최종 실패

            var rwdErrMsg= "본인인증 시 고객정보와 휴대폰 인증 고객정보가 일치하지 않습니다.";
            if(niceAuthObj.resAuthOjb.RESULT_CODE == "STEP01"){ // STEP 오류
                rwdErrMsg= niceAuthObj.resAuthOjb.RESULT_MSG;
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

    // 자급제 보상 서비스 약관 동의 시 전체 약관 동의 체크를 위해 이벤트 호출
    if(agree.hasClass("_rwdAgree")){
        rwdTermsCheckCbFn();
    }
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
    fileBox += '    <div class="c-file-image__item u-ml--12" style="width:95px;height:72px;border:1px solid #ddd;border-radius:0.5rem;">';
    fileBox += '        <div class="c-item u-ml--2 fs-14 " style="top: 25px;">등록완료</div>';
    fileBox += '        <button class="upload-image__delete">';
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
            , url : '/m/mypage/rwdRegNonMemberAjax.do'
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

// imei 중복 체크 (esim개통이면서 imei값을 찍고 들어오는 경우)
function checkRwdImei(){

    // 해피콜인 경우 즉시 리턴
    if( $("input:checkbox[name=authCheckNone]:checked").length >= 1) return false;

    //imei값 중복인 경우 자급제 보상 서비스 선택 영역 hide처리
    var imei1 = ajaxCommon.isNullNvl($.trim($("#rwdImei1").val()), "");
    var imei2 = ajaxCommon.isNullNvl($.trim($("#rwdImei2").val()), "");

    if(imei1 == "" && imei2 == "") {
        $("._rwdProdForm").show();
        $("#rwdImeiForm").show();
        return true;
    }

    var varData = ajaxCommon.getSerializedData({
        imei: imei1, imeiTwo: imei2
    });

    ajaxCommon.getItemNoLoading({
            id:'checkImeiForRwd'
            ,cache:false
            ,url:'/mypage/checkImeiForRwd.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){

            var possibleYn= (!jsonObj || !jsonObj.possibleYn) ? "N" : jsonObj.possibleYn;
            if(possibleYn == "Y"){
                $("._rwdProdForm").show();
                $("#rwdImeiForm").hide();
                return true;
            }else{
                $("._rwdProdForm").hide();
                return false;
            }
        });
}

// ================================ END 자급제 보상 서비스 관련 함수 ================================


/* 주소 setting */
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn, dataOjb) {
    if (pageObj.addrFlag == "1") {
        $("#cstmrPost").val(zipNo);
        $("#cstmrAddr").val(roadAddrPart1);
        $("#cstmrAddrDtl").val(roadAddrPart2 + " " + addrDetail);

        $("#cstmrAddr").parent().addClass('has-value');

        var admCd = dataOjb.find('admCd').text();
        var rnMgtSn = ajaxCommon.isNullNvl(dataOjb.find('rnMgtSn').text(),dataOjb.find('rdMgtSn').text());
        var udrtYn = dataOjb.find('udrtYn').text();
        var buldMnnm = dataOjb.find('buldMnnm').text();
        var buldSlno = dataOjb.find('buldSlno').text();
        var jibunAddr = dataOjb.find('jibunAddr').text();
        var roadAddrPart2 = dataOjb.find('roadAddrPart2').text();

        var $cstmrPost = $("#cstmrPost");
        $cstmrPost.removeData();
        $cstmrPost.attr('data-adm-cd', admCd);
        $cstmrPost.attr('data-rn-mgt-sn', rnMgtSn);
        $cstmrPost.attr('data-udrt-yn', udrtYn);
        $cstmrPost.attr('data-buld-mnnm', buldMnnm);
        $cstmrPost.attr('data-buld-slno', buldSlno);
        $cstmrPost.attr('data-jibun-addr', jibunAddr);
        $cstmrPost.attr('data-addr-detail', addrDetail);
        $cstmrPost.attr('data-road-addr-part2', roadAddrPart2);

    } else if (pageObj.addrFlag == "4") {
        var zipNoCi = zipNo.substring(0,2);
        var strReg = /01|02|03|04|05|06|07|08|09/;
        if (strReg.test(zipNoCi)) {
            $("#dlvryPost").val(zipNo);
            $("#dlvryAddr").val(roadAddrPart1);
            $("#dlvryAddrDtl").val(roadAddrPart2 + " " +addrDetail);

            $("#dlvryAddr").parent().addClass('has-value');
        } else {
            MCP.alert("당일배송 가능지역이 아닙니다.<br/>(서울 전 지역에 한 해 가능)<br/>택배를 이용해주시기 바랍니다. ") ;
            $("#dlvryPost").val(zipNo);
            $("#dlvryAddr").val(roadAddrPart1);
            $("#dlvryAddrDtl").val(roadAddrPart2 + " " +addrDetail);
            $("input:radio[name=dlvryType][value='01']").trigger("click"); //택배
        }

    } else {
        $("#dlvryPost").val(zipNo);
        $("#dlvryAddr").val(roadAddrPart1);
        $("#dlvryAddrDtl").val(roadAddrPart2 + " " +addrDetail);

        $("#dlvryAddr").parent().addClass('has-value');
    }
    isValidateNonEmptyStep2();
}

//바로배송 추가
function dlvryJusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,jibunAddr, zipNo, psblYn,bizOrgCd,acceptTime,entY,entX) {
    if(psblYn=="Y"){
        $("#bizOrgCd").val(bizOrgCd);
        reqDlvryForm.bizOrgCd = bizOrgCd;
        reqDlvryForm.entY = ajaxCommon.isNullNvl(entY,"");
        reqDlvryForm.entX = ajaxCommon.isNullNvl(entX,"");
        reqDlvryForm.acceptTime = acceptTime;
        reqDlvryForm.dlvryJibunAddr = jibunAddr;
        reqDlvryForm.dlvryJibunAddrDtl = addrDetail;
    } else {
        $("#bizOrgCd").val("");
        $(".dlvryType01").show();
        $("input:radio[name=dlvryType][value='01']").trigger("click");
    }
    if (typeof addrDetail === 'string') {
        addrDetail = addrDetail.replace(/[?&=]/gi,' ');
    }
    $("#dlvryPost").val(zipNo);
    $("#dlvryAddr").val(roadAddrPart1);
    $("#dlvryAddrDtl").val(roadAddrPart2 + " " +addrDetail);
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

    if(onOffType != "7"){
        $('#btnNext').attr('data-step','31');
    }else{
        $('#btnNext').attr('data-step','11');
    }

    validator.config={};
    validator.config['cstmrType1'] = 'isNonEmpty';

    if(operType == OPER_TYPE.NEW && onOffType == "7"){
        //셀프개통 신규가입 신청서 내 ‘휴대폰 인증‘ 추가
        validator.config['isSmsAuth'] = 'isNonEmpty';
    }
    if(operType == OPER_TYPE.CHANGE || operType == OPER_TYPE.EXCHANGE){
        validator.config['changeCstmrMobile'] = 'isPhone';
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
        validator.config['minorAgentTel'] = 'isTel';
        validator.config['minorAgentAgrmYn'] = 'isNonEmpty';

    } else if (cstmrType == CSTMR_TYPE.FN) {
        //외국인
        validator.config['cstmrForeignerRrn01&cstmrForeignerRrn02'] = 'isFngno';
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
    // validator.config['clausePriTrustFlag'] = 'isNonEmpty';
    // validator.config['clauseConfidenceFlag'] = 'isNonEmpty';
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

    /*if (pageObj.clauseJehuRateCount > 0) {
        validator.config['clauseJehuFlag'] = 'isNonEmpty';
    }

    if (pageObj.clauseFinanceRateCount > 0) {
        validator.config['clauseFinanceFlag'] = 'isNonEmpty';
    }*/

    if (onOffType == "7") {
        validator.config['clauseSimpleOpen'] = 'isNonEmpty';
    }

    if ("01" == $("#settlWayCd").val() ) {
        validator.config['clauseSettlWayCd'] = 'isNonEmpty';
    }

    if (validator.validate(true)) {
        $('#btnNext').removeClass('is-disabled');
    } else {
        $('#btnNext').addClass('is-disabled');
    }
} ;

var isValidateNonEmptyStep2 = function() {
    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    var operType = $("#operType").val() ;

    validator.config={};

    if($("#onOffType").val()  != "7"){
        $('#btnNext').attr('data-step','32');
    }else{
        $('#btnNext').attr('data-step','12');
    }

    if(operType != OPER_TYPE.CHANGE && operType != OPER_TYPE.EXCHANGE ) {
        var onOffLineUsim = $("input:radio[name='onOffLineUsim']:checked").val();
        if (onOffLineUsim == "offLine") {
            validator.config['reqUsimSn'] = 'isNonEmpty';
            validator.config['isUsimNumberCheck'] = 'isNonEmpty';
        }
        validator.config['cstmrMobile'] = 'isNonEmpty';
        validator.config['cstmrMail'] = 'isNonEmpty';
        validator.config['cstmrPost'] = 'isNonEmpty';

        //안면인증
        if($("#fathTrgYn").val() == "Y") {
            validator.config['isFathTxnRetv'] = 'isNonEmpty';
        }

        //부정가입방지
        validator.config['selfInqryAgrmYnFlag'] = 'isNonEmpty';//본인조회동의
        validator.config['selfIssuExprDt'] = 'isIssuDate';//발급일자

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
    var onOffType = $("#onOffType").val() ;
    if(onOffType != "7"){
        if ($('#divDlvry').is(':visible') ) {
            validator.config['dlvryName'] = 'isNonEmpty';
            if ($("#dlvryTel").val() != "") {
                validator.config['dlvryTel'] = 'isNonEmpty';
            }
            validator.config['dlvryMobile'] = 'isNonEmpty';

            //바로배송
            var dlvryType = "01";
            if ($("input:radio[name=dlvryType]:checked").val() != undefined) {
                dlvryType = $("input:radio[name=dlvryType]:checked").val();
            }

            if ("02" == dlvryType) {
                validator.config['bizOrgCd'] = 'isNonEmpty';
                if("1" == $("#exitPw option:selected").val()) {
                    validator.config['homePw'] = 'isNonEmpty';
                }
            } else if ("03" == dlvryType) {
                validator.config['dlvryPost'] = 'isNonEmpty';
            }
        }
    }

    if (validator.validate(true)) {
        $('#btnNext').removeClass('is-disabled');
    } else {
        $('#btnNext').addClass('is-disabled');
    }
} ;

var isValidateNonEmptyStep3 = function() {
    var operType = $("#operType").val() ;
    var onOffType = $("#onOffType").val() ;

    if(onOffType  != "7"){
        $('#btnNext').attr('data-step','33');
    }else{
        $('#btnNext').attr('data-step','13');
    }

    validator.config={};
    if (operType==OPER_TYPE.MOVE_NUM ) {
        //번호이동
        validator.config['moveCompany'] = 'isNonEmpty';
        validator.config['moveMobile'] = 'isNonEmpty';

    } else if(operType==OPER_TYPE.NEW){
        if(onOffType != "7"){
            validator.config['reqWantNumber'] = 'isNonEmpty';
            validator.config['reqWantNumber2'] = 'isNonEmpty';
            validator.config['reqWantNumber3'] = 'isNonEmpty';
        } else {
            validator.config['reqCnt'] = 'isNonEmpty';
        }
    }

    if (validator.validate(true)) {
        //console.log(" isValidateNonEmptyStep3 =>call");
        $('#btnNext').removeClass('is-disabled');
    } else {
        //console.log("isValidateNonEmptyStep3 errId=>" + validator.getErrorId());
        $('#btnNext').addClass('is-disabled');

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


    var onOffType = $("#onOffType").val() ;

    if(onOffType  != "7"){
        $('#btnNext').attr('data-step','34');
    }else{
        $('#btnNext').attr('data-step','14');
    }

    // 자급제 보상 서비스 추가
    if (pageObj.rwdProdCd != ""){

        // 자급제 보상 서비스 유효성 검사
        // 1. 구입가 입력여부
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
    }

    //아무나 SOLO 결합 가입 동의
    var combineSoloType = $("input:radio[name=combineSoloType]:checked").val();
    if (combineSoloType == "Y"){
        validator.config['combineSoloFlag'] = 'isNonEmpty';
    }

    if (validator.validate(true)) {
        $('#btnNext').removeClass('is-disabled');
    } else {
        $('#btnNext').addClass('is-disabled');
    }
} ;

var isValidateNonEmptyStep5 = function() {
    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    var reqPayType = $('input:radio[name=reqPayType]:checked').val();


    var onOffType = $("#onOffType").val() ;

    if(onOffType  != "7"){
        $('#btnNext').attr('data-step','35');
    }else{
        $('#btnNext').attr('data-step','15');
    }

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
        $('#btnNext').removeClass('is-disabled');
    } else {
        $('#btnNext').addClass('is-disabled');
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

var procOnline= function(procType){

    var varData = ajaxCommon.getSerializedData({
        procType: ajaxCommon.isNullNvl(procType, 'R')
    });

    ajaxCommon.getItemNoLoading({
            id:'procOnline'
            ,cache:false
            ,async:false
            ,url:'/auth/procOnline.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            //nothing
        });
}
// ============ STEP END ============

/**
 * 사전동의 요청  reqNpAgreeAjax
 * 사전동의 결과조회(NP3)
 */
var fnReqPreMoveAgree = function() {
    var cstmrType = $("input:radio[name=cstmrType]:checked").val();
    var custIdntNo = $.trim($("#cstmrNativeRrn01").val()) + $.trim($("#cstmrNativeRrn02").val()) ;
    var mpCode= $("#moveCompany option:selected").attr("mpCode");

    var moveMobile = $.trim($("#moveMobile").val());

    var varData = ajaxCommon.getSerializedData({
        npTlphNo:moveMobile
        ,bchngNpCommCmpnCd:mpCode
        ,slsCmpnCd:$("#cntpntShopId").val()
        ,custIdntNoIndCd:"01"
        ,custIdntNo:custIdntNo
        ,custNm:$.trim($("#cstmrName").val())
        ,custTypeCd:cstmrType
    });

    ajaxCommon.getItemNoLoading({
        id:'conPreAgreeAjax'
        ,cache:false
        ,async:false
        ,url:'/appform/reqNpAgreeAjax.do'
        ,data: varData
        ,dataType:"json"
    }, function(jsonObj){
        //KTM.LoadingSpinner.hide(true);
        if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
            var rsltCd = jsonObj.RSLT_CD;
            if ("S" != rsltCd) {
                KTM.LoadingSpinner.hide(true);
                MCP.alert("현 통신사의 번호이동 사전동의 승인이<br/> 완료되지 않았습니다.<br/>문자 또는 ARS번호로 사전동의 절차<br/> 완료 후 재시도 바랍니다.<br/><br/>ARS 또는 문자로 이미 동의를 완료하셨을 경우 1~2분 후 다시 한번 동의 완료 버튼을 클릭 해 주시기 바랍니다. "
                    ,function (){
                        isValidateNonEmptyStep3();
                    }
                );
            } else {
                pageObj.applDataForm.globalNoNp3 = jsonObj.GLOBAL_NO;
            }
        } else {
            MCP.alert("처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요");
        }
    })
} ;

function getJehuInfo() {
    if ($("#jehuProdType").val() != null && $("#jehuProdType").val() != "" ){
        $("._clauseJehuFlag").show();
    } else {
        $("._clauseJehuFlag").hide();
    }
    if ($("#jehuPartnerType").val() != null && $("#jehuPartnerType").val() != "" ){
        $("._clausePartnerOfferFlag").show();
    } else {
        $("._clausePartnerOfferFlag").hide();
    }
}


//이탈고객 타겟 마케팅을 위한 스크립트 설치 (구글애즈)
function gtag_report_conversion(type,url) {
    var callback = function () {
        if (typeof(url) != 'undefined') {
            window.location = url;
        }
    };
    if(type == '11'){
        //Event snippet for 이탈모수_셀프개통_MO_1
        gtag('event', 'conversion', {
            'send_to': 'AW-862149999/Xa4GCPbe06YaEO-6jZsD',
            'value': 1.0,
            'currency': 'KRW',
            'event_callback': callback
        });
    }else  if(type == '12'){
        //Event snippet for 이탈모수_셀프개통_MO_2
        gtag('event', 'conversion', {
            'send_to': 'AW-862149999/YI4vCILf06YaEO-6jZsD',
            'value': 1.0,
            'currency': 'KRW',
            'event_callback': callback
        });
    }else  if(type == '13'){
        //Event snippet for 이탈모수_셀프개통_MO_3
        gtag('event', 'conversion', {
            'send_to': 'AW-862149999/CJHDCI7f06YaEO-6jZsD',
            'value': 1.0,
            'currency': 'KRW',
            'event_callback': callback
        });
    }else  if(type == '14'){
        //Event snippet for 이탈모수_셀프개통_MO_4
        gtag('event', 'conversion', {
            'send_to': 'AW-862149999/y2tSCJrf06YaEO-6jZsD',
            'value': 1.0,
            'currency': 'KRW',
            'event_callback': callback
        });
    }else  if(type == '31'){
        //Event snippet for 이탈모수_상담사개통_MO_1
        gtag('event', 'conversion', {
            'send_to': 'AW-862149999/CoZHCPze06YaEO-6jZsD',
            'value': 1.0,
            'currency': 'KRW',
            'event_callback': callback
        });
    }else  if(type == '32'){
        //Event snippet for 이탈모수_상담사개통_MO_2
        gtag('event', 'conversion', {
            'send_to': 'AW-862149999/GP-YCIjf06YaEO-6jZsD',
            'value': 1.0,
            'currency': 'KRW',
            'event_callback': callback
        });
    }else  if(type == '33'){
        //Event snippet for 이탈모수_상담사개통_MO_3
        gtag('event', 'conversion', {
            'send_to': 'AW-862149999/CRkWCJTf06YaEO-6jZsD',
            'value': 1.0,
            'currency': 'KRW',
            'event_callback': callback
        });
    }else  if(type == '34'){
        //Event snippet for 이탈모수_상담사개통_MO_4
        gtag('event', 'conversion', {
            'send_to': 'AW-862149999/8_YDCKDf06YaEO-6jZsD',
            'value': 1.0,
            'currency': 'KRW',
            'event_callback': callback
        });
    }
    return false;
}

// 중복 신청 체크
function chkReqDup() {
    // 온라인, 모바일만 중복 체크
    if ($("#onOffType").val() != '3' && $("#onOffType").val() != '0') {
        return false;
    }
    var isDup = false;
    var moveMobile = $("#moveMobile").val();
    var _moveMobileFn = moveMobile ? moveMobile.slice(0, moveMobile.length-8) : "";
    var _moveMobileMn = moveMobile ? moveMobile.slice(-8, moveMobile.length-4) : "";
    var _moveMobileRn = moveMobile ? moveMobile.slice(-4, moveMobile.length) : "";
    var varData = ajaxCommon.getSerializedData({
        operType: $("#operType").val()
        ,reqUsimSn: $("#reqUsimSn").val()
        ,moveMobileFn: _moveMobileFn
        ,moveMobileMn: _moveMobileMn
        ,moveMobileRn: _moveMobileRn
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
                        $('#moveMobile').focus();
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

    $("#cstmrForeignerNation").val($("#cstmrForeignerNationTmp").val());
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
//2026.03 신규(셀프/상담사)만 30일 이내 1회선으로 개통 제한
//유심유효성 체크시에는 상담사개통일 때에만 제한
var checkLimitCounsel = function() {

    var rtnRslt = false;

    var varData = ajaxCommon.getSerializedData({
        socCode: $("#socCode").val()
        , requestKeyTemp: $('#requestKeyTemp').val()
    });

    ajaxCommon.getItemNoLoading({
            id: 'checkLimitCounselAjax'
            , cache: false
            , async: false
            , url: '/appForm/checkLimitCounselAjax.do'
            , data: varData
            , dataType: "json"
        }
        , function (jsonObj) {
            if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
                rtnRslt = true; //통과
            }else if("-1" == jsonObj.RESULT_CODE) {
                $("#simpleDialog ._title").html("개통 진행 실패");
                $("#simpleDialog ._detail").html("※ 신규가입은 명의당 30일이내 1회선만 가입 가능합니다.<br>추가 가입은 최근 가입하신 KT M모바일 회선 가입일을 기준으로 30일 경과된 시점에 신청 부탁드립니다.");
                $("._simpleDialogButton").hide();
                $("#btnOnline").hide();
                $("#divChangeButton").show();

                var el = document.querySelector('#simpleDialog');
                var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                modal.open();
            }else {
                MCP.alert("문제가 발생 하였습니다.<br>잠시후 다시 시도 하시기 바랍니다.");
            }
        });

    return rtnRslt;
}