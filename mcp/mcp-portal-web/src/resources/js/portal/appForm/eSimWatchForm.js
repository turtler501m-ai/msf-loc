;


VALIDATE_NOT_EMPTY_MSG.reqWantNumberN = "가입희망번호 번호를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.reqCnt = "가입희망번호 번호를 설정해 주세요.";
VALIDATE_NUMBER_MSG.reqWantNumberN = "가입희망번호 번호를 입력해 주세요.";
VALIDATE_COMPARE_MSG.selfIssuExprDt = "발급일자의 날짜 형식이 맞지 않습니다. 발급일자를 확인해 주세요.";

var stepInfo = {
    isInitStep2: true
}
var pageObj = {
    step: 1
    , applDataForm: {}
    , rateNm: ""          //요금제명
    , rateObj: null
    , prdtNm: ""          //상품명
    , priceLimitObj: null
    , name: ""
    , eventId: ""
    , clauseJehuRateCount: 0
    , clauseFinanceRateCount: 0
    , giftList: []
    , prmtIdList: []     // 사은품 프로모션 코드
    , prdtIdList: []    // 사은품 제품ID
    , requestKey: 0
    , cid: ""
    , niceResLogObj:{}   //로그을 저장 하기 위한 인증
    , startTime:0
}

var fs9Succ = false;

var stepText = ["본인확인·약관동의", "가입신청 정보 확인"]

$(document).ready(function () {

    //simpleErrMsg.js 재정의
    SIMPLE_PC2_MSG.RESULT_2202 = "운전면허 재발급 등으로 실명인증정보가 변경되어 셀프개통이 불가합니다.<br/>상담사 개통 신청으로 진행을 부탁드리며, 해피콜 시 문의 바랍니다. " ;
    SIMPLE_PC2_MSG.RESULT_2202_01 = "실명인증 행정기관 시스템 연동 점검으로 인해 입력하신 신분증으로 개통이 불가합니다.<br/>익일 재 시도 부탁드립니다." ;
    SIMPLE_PC2_TYPE.RESULT_2202 = "divChangeButton" ;
    SIMPLE_PC2_TYPE.RESULT_2202_01 = "divDefaultButton" ;
    KTM.datePicker('.flatpickr');

    $("#reqWantNumber,#reqWantNumber2,#reqWantNumber3").keyup(function () {
        isValidateNonEmptyStep2();
    });
    var isValidateStep1 = function () {
        var operType = $("#operType").val();
        var cstmrType = $("input:radio[name=cstmrType]:checked").val();
        var reqBuyType = $("#reqBuyType").val();
        var onOffType = $("#onOffType").val();

        pageObj.applDataForm.onOffType = onOffType;
        pageObj.applDataForm.dlvryType = "";  //택배 의미 없음
        pageObj.applDataForm.reqBuyType = reqBuyType;
        pageObj.applDataForm.operType = $("#operType").val();
        pageObj.applDataForm.cstmrType = $("input:radio[name=cstmrType]:checked").val();
        pageObj.applDataForm.serviceType = $("#serviceType").val();
        pageObj.applDataForm.cntpntShopId = $("#cntpntShopId").val();
        pageObj.applDataForm.socCode = $("#socCode").val();
        pageObj.applDataForm.onlineAuthType = $('input:radio[name=onlineAuthType]:checked').val();
        pageObj.applDataForm.reqWireType = "";
        pageObj.applDataForm.prodId = $("#prodId").val();
        pageObj.applDataForm.bannerCd = $("#bannerCd").val();
        pageObj.applDataForm.prdtSctnCd = $("#prdtSctnCd").val();
        pageObj.applDataForm.rprsPrdtId = $("#rprsPrdtId").val();
        pageObj.applDataForm.modelSalePolicyCode = $("#modelSalePolicyCode").val();
        pageObj.applDataForm.sprtTp = $("#sprtTp").val();
        pageObj.applDataForm.resUniqId = $("#resUniqId").val();

        //단말기 , 유심 통일성
        pageObj.applDataForm.modelMonthly = $('#modelMonthly').val();
        pageObj.applDataForm.modelId = $("#modelId").val();
        pageObj.applDataForm.reqModelName = "";//$("#modelId option:selected").attr("hndsetModelNm");
        pageObj.applDataForm.enggMnthCnt = $('#enggMnthCnt').val();
        pageObj.applDataForm.usimKindsCd = $('#usimKindsCd').val();
        pageObj.applDataForm.uploadPhoneSrlNo = $('#uploadPhoneSrlNo').val();

        validator.config = {};
        validator.config['cstmrType1'] = 'isNonEmpty';


        if (cstmrType == CSTMR_TYPE.NM) {
            //청소년
            validator.config['minorAgentName'] = 'isNonEmpty';
            validator.config['minorAgentRelation'] = 'isNonEmpty';
            validator.config['minorAgentRrn01&minorAgentRrn02'] = 'isJimin';
            validator.config['minorAgentTelFn'] = 'isNumBetterFixN2';
            validator.config['minorAgentTelMn'] = 'isNumBetterFixN3';
            validator.config['minorAgentTelRn'] = 'isNumFix4';
            validator.config['minorAgentTelFn&minorAgentTelMn&minorAgentTelRn'] = 'isTelNumberCheck';
            validator.config['minorAgentAgrmYn'] = 'isNonEmpty';

            pageObj.applDataForm.birthDate = $.trim($("#minorAgentRrn01").val());
            pageObj.applDataForm.minorAgentName = $.trim($("#minorAgentName").val());
            pageObj.applDataForm.minorAgentRrn = $.trim($("#minorAgentRrn01").val()) + $.trim($("#minorAgentRrn02").val());
            pageObj.applDataForm.minorAgentTelFn = $.trim($("#minorAgentTelFn").val());
            pageObj.applDataForm.minorAgentTelMn = $.trim($("#minorAgentTelMn").val());
            pageObj.applDataForm.minorAgentTelRn = $.trim($("#minorAgentTelRn").val());
            pageObj.applDataForm.minorAgentRelation = $("#minorAgentRelation").val();
            pageObj.applDataForm.minorAgentAgrmYn = $("#minorAgentAgrmYn").is(":checked") ? "Y":"N";


        } else if (cstmrType == CSTMR_TYPE.FN) {
            //외국인
            pageObj.applDataForm.cstmrNativeRrn = "";
            pageObj.applDataForm.desCstmrNativeRrn = "";
            pageObj.applDataForm.birthDate = $.trim($("#cstmrForeignerRrn01").val());
            pageObj.applDataForm.cstmrForeignerRrn = $.trim($("#cstmrForeignerRrn01").val()) + $.trim($("#cstmrForeignerRrn02").val());
        }


        pageObj.applDataForm.cstmrName = $.trim($("#cstmrName").val());
        pageObj.applDataForm.telAdvice = "N";

        validator.config['isAuth'] = 'isNonEmpty';
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
            pageObj.applDataForm.nwBlckAgrmYn = $("#nwBlckAgrmYn").is(":checked") ? "Y" : "N";
            pageObj.applDataForm.appBlckAgrmYn = $("#appBlckAgrmYn").is(":checked") ? "Y" : "N";
        } else {
            pageObj.applDataForm.nwBlckAgrmYn = "";
            pageObj.applDataForm.appBlckAgrmYn = "";
        }

        if ($("#prdtSctnCd").val() == PRDT_SCTN_CD.FIVE_G_FOR_MSP) {
            validator.config['clause5gCoverage'] = 'isNonEmpty';
            pageObj.applDataForm.clause5gCoverageFlag = $("#clause5gCoverage").is(":checked") ? "Y" : "N";
        }

        if (pageObj.clauseJehuRateCount > 0) {
            validator.config['clauseJehuFlag'] = 'isNonEmpty';
        }

        // if (pageObj.clauseFinanceRateCount > 0) {
        //     validator.config['clauseFinanceFlag'] = 'isNonEmpty';
        //     pageObj.applDataForm.clauseFinanceFlag = $("#clauseFinanceFlag").is(":checked") ? "Y" : "N";
        // }

        if (isSimpleForm()) {
            validator.config['clauseSimpleOpen'] = 'isNonEmpty';
        }
        //신분증 타입
        validator.config['selfCertType1'] = 'isNonEmpty';
        
        //안면인증 
        if($("#fathTrgYn").val() == "Y") {
            validator.config['isFathTxnRetv'] = 'isNonEmpty';
        }
        
        pageObj.applDataForm.fathTrgYn = $("#fathTrgYn").val();

        validator.config['selfIssuExprDt'] = 'isIssuDate';
        validator.config['selfInqryAgrmYnFlag'] = 'isNonEmpty';

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

        // pageObj.applDataForm.clauseFinanceFlag = $("#clauseFinanceFlag").is(":checked") ? "Y" : "N";
        pageObj.applDataForm.clausePriCollectFlag = $("#clausePriCollectFlag").is(":checked") ? "Y" : "N";
        pageObj.applDataForm.clausePriOfferFlag = $("#clausePriOfferFlag").is(":checked") ? "Y" : "N";
        pageObj.applDataForm.clauseEssCollectFlag = $("#clauseEssCollectFlag").is(":checked") ? "Y" : "N";
        pageObj.applDataForm.clauseFathFlag = $("#clauseFathFlag01").is(":checked") && $("#clauseFathFlag02").is(":checked") ? "Y":"N";
        // pageObj.applDataForm.clausePriTrustFlag = $("#clausePriTrustFlag").is(":checked") ? "Y" : "N";
        // pageObj.applDataForm.clausePriAdFlag = $("#clausePriAdFlag").is(":checked") ? "Y" : "N";
        // pageObj.applDataForm.clauseConfidenceFlag = $("#clauseConfidenceFlag").is(":checked") ? "Y" : "N";
        pageObj.applDataForm.clauseJehuFlag = $("#clauseJehuFlag").is(":checked") ? "Y" : "N";
        //동의 추가
        pageObj.applDataForm.personalInfoCollectAgree = $("#personalInfoCollectAgree").is(":checked") ? "Y":"N"; // 고객 혜택 제공을 위한 개인정보 수집 및 이용 관련 동의
        pageObj.applDataForm.clausePriAdFlag = $("#clausePriAdFlag").is(":checked") ? "Y":"N"; // 개인정보 처리 위탁 및 고객 혜택 제공을 위한 광고 수신 동의
        pageObj.applDataForm.othersTrnsAgree = $("#formOthersTrnsAgree").is(":checked") ? "Y":"N"; // 혜택 제공을 위한 제3자 제공 동의 : M모바일
        pageObj.applDataForm.othersTrnsKtAgree = $("#formOthersTrnsKtAgree").is(":checked") ? "Y":"N"; // 혜택 제공을 위한 제3자 제공 동의 : KT
        pageObj.applDataForm.othersAdReceiveAgree = $("#othersAdReceiveAgree").is(":checked") ? "Y":"N"; // 제3자 제공관련 광고 수신 동의

        if (validator.validate()) {
            return true;
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg(), function () {
                if (errId == "isAuth") {
                    setTimeout(function () {
                        $("#onlineAuthType1").focus();
                    }, 500);
                } else if (errId == "isSmsAuth") {
                    setTimeout(function () {
                        $("#btnSmsAuth").focus();
                    }, 500);
                } else {
                    $selectObj = $("#" + errId);
                    viewErrorMgs($selectObj, validator.getErrorMsg());
                }
            });
            return false;
        }
    }

    var isValidateStep2 = function () {
        validator.config = {};

        if (isSimpleForm()) {
            validator.config['reqCnt'] = 'isNonEmpty';
            var reqCnt = $.trim($("#reqCnt").val());
            pageObj.applDataForm.reqWantNumber = $.trim($("#reqWantNumberN").val());
            pageObj.applDataForm.reqWantNumber2 = reqCnt.slice(-8, reqCnt.length - 4);
            pageObj.applDataForm.reqWantNumber3 = reqCnt.slice(-4, reqCnt.length);
        } else {
            validator.config['reqWantNumber&reqWantNumber2&reqWantNumber3'] = 'isNumFix4IsNotEqualsTriple';
            pageObj.applDataForm.reqWantNumber = $.trim($("#reqWantNumber").val());
            pageObj.applDataForm.reqWantNumber2 = $.trim($("#reqWantNumber2").val());
            pageObj.applDataForm.reqWantNumber3 = $.trim($("#reqWantNumber3").val());
        }

        //사은품 정보 설정
        pageObj.applDataForm.evntCdPrmt = $("#evntCdPrmt").val();
        pageObj.prmtIdList = [];
        pageObj.prdtIdList = [];
        $("input:checkbox[name=checkboxGift]:checked").each(function () {
            var prmtId = $(this).attr("prmtId");
            var prdtId = $(this).val();

            pageObj.prmtIdList.push(prmtId);
            pageObj.prdtIdList.push(prdtId);
        });

        pageObj.applDataForm.prmtIdList = pageObj.prmtIdList;
        pageObj.applDataForm.prdtIdList = pageObj.prdtIdList;

        if (undefined != $("#recommendInfo").val() && "" != $("#recommendInfo").val()) {
            pageObj.applDataForm.recommendInfo = $.trim($("#recommendInfo").val());
            pageObj.applDataForm.recommendFlagCd = $("#recommendFlagCd").val();
            if ("01" == $("#recommendFlagCd").val()) {
                validator.config['commendId'] = 'isCommendId';
                var commendId = $.trim($("#recommendInfo").val());
                $("#commendId").val(commendId.toUpperCase());
                pageObj.applDataForm.commendId = commendId.toUpperCase();
            } else {
                pageObj.applDataForm.commendId = "";
            }
        } else {
            pageObj.applDataForm.recommendFlagCd = "";
            pageObj.applDataForm.recommendInfo = "";
            pageObj.applDataForm.commendId = "";
        }


        if (validator.validate()) {
            return true;
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg(), function () {
                if (errId == "reqCnt") {
                    setTimeout(function () {
                        $("#btnSearchNumber").focus();
                    }, 500);
                } else {
                    $selectObj = $("#" + errId);
                    viewErrorMgs($selectObj, validator.getErrorMsg());
                }
            });
            return false;
        }
    }

    var setStep = function (setStep) {
        window.scrollTo(0, 0);
        pageObj.step = setStep;
        var tIndex = setStep - 1;

        $("#ulStepper > li").each(function () {
            $(this).removeClass("c-stepper__item--active");
            $(this).find(".c-stepper__title").remove();
        });
        $("#ulStepper > li").eq(tIndex).addClass("c-stepper__item--active").append("<span class='c-stepper__title'>" + stepText[tIndex] + "</span>");

        $("._divStep").hide();

        var hideIndex = setStep - 1;
        $("#step" + setStep).show();

        if (setStep == 2) {
            if (stepInfo.isInitStep2) {

                var evntCdPrmt = ajaxCommon.isNullNvl($("#evntCdPrmt").val(), "");    // 이벤트 코드 입력
                var recoUseYn = $("#recoUseYn").val();        // 추쳔인id 입력 노출여부

                // 이벤트 코드 입력 && 추천인 아이디 비활성일 경우 숨김 처리
                if(evntCdPrmt != "" && recoUseYn == "N"){
                    $("#recoIdArea").hide();
                }

                //사은품 정보
                getGiftList();

                stepInfo.isInitStep2 = false;
            }

        }
    };


    $("input:radio[name=cstmrType]").click(function () {
        var thisVal = $(this).val();

        if ($("#isAuth").val() == "1") {
            MCP.alert("본인 인증을 완료 하였습니다. ");
            return false;
        }
        if (thisVal == CSTMR_TYPE.NM) {
            $("._isTeen").show();
            $("._isForeigner").hide();
            $("._isDefault").show();
            $("._isDefaultMt").addClass("u-mt--0");
            //청소년 요금제 가능 여부
            if (!checkPriceLimitForAge(12)) {
                MCP.alert(pageObj.priceLimitObj.dtlCdDesc);
                $(this).prop("checked", false);
                return false;
            }

        } else if (thisVal == CSTMR_TYPE.FN) {
            $("._isTeen").hide();
            $("._isForeigner").show();
            $("._isDefault").hide();
            $("._isDefaultMt").removeClass("u-mt--0");

        } else {
            $("._isTeen").hide();
            $("._isForeigner").hide();
            $("._isDefault").show();
            $("._isDefaultMt").addClass("u-mt--0");
        }

        isValidateNonEmptyStep1();
    });


    $("#btnStplAllCheck").click(function () {
        if ($(this).is(':checked')) {
            KTM.Confirm('선택사항 포함 모든 약관/동의에 <br/>동의 하시겠습니까?'
                , function () {
                    $("._agree").prop("checked", "checked");
                    isValidateNonEmptyStep1();
                    this.close();
                }
                , function () {
                    $("._agree[validityyn=Y]").prop("checked", "checked");
                    $("._agree[validityyn=N]").prop("checked", "");
                    $(this).prop("checked", "");
                    MCP.alert("필수 동의만 선택 되었습니다.");
                    isValidateNonEmptyStep1();
                });
        } else {
            $("._agree").prop("checked", false);
            isValidateNonEmptyStep1();
        }
        applyCheckedWrapList();
    });

    $("#clauseSimpleOpen").click(function () {
        isValidateNonEmptyStep1();
    });

    //신분증 종류 별
    $("input:radio[name=selfCertType]").click(function(){
        var thisVal= $(this).val();
        $("._selfIssuNumF").hide();
        $("._driverSelfIssuNumF").hide();

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

        if(!fs9Succ) { //페이지 진입 or 고객이 직접 선택하는 경우 , (FS9는 성공 시 신분증타입 선택되는 경우는 제외)
            fnReqFathTgtYn();
        }

        isValidateNonEmptyStep1();
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
                orgId : $("#cntpntShopId").val(),
                cpntId : $("#cntpntShopId").val(),
                operType : $("#operType").val(),
                contractNum : $("#prntsContractNo").val(),
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
    
    $("#selfInqryAgrmYnFlag").click(function(){
        isValidateNonEmptyStep1();
    });


    $('._btnNext').click(function () {

        if ($("#bs_acc_btn").hasClass("is-active")) {
            $("#bs_acc_btn").trigger("click");
        }

        var operType = $("#operType").val();

        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");
        $('._btnNext').addClass('is-disabled');
        if (pageObj.step == 1 && isValidateStep1()) {
            if (isSimpleForm()) {
                KTM.LoadingSpinner.show();
                setTimeout(function () {
                    fnReqPreCheck(false);
                }, 300);
            } else {
                setStep(2);
                isValidateNonEmptyStep2();
                return;
            }
            return;
        } else if (pageObj.step == 2 && isValidateStep2()) {
            //사은품 확인
            if (isValidateGift()) {
                nextSend();
            } else {
                KTM.Confirm('사은품을 미선택 하셨습니다. <br/>미선택 시 사은품이 지급되지 않으니<br/> 사은품을 선택해주세요.<br/><br/> 확인을 누르실 경우 이전화면으로 돌아갑니다.'
                        , function () {
                            this.close();
                            isValidateStep2();
                            return;
                        }, function () {
                            nextSend();
                            return;
                        }, {
                            confirmText: "\uD655\uC778",
                            cancelText: "\uB2E4\uC74C"
                       });
            }
            return;
        }
    });

    $("._agree").click(function(){
        handleCommonAgreeClick();
    });

    $("#clauseSimpleOpen").click(function(){
        isValidateNonEmptyStep1();
    });

    $("#cstmrName,#cstmrForeignerRrn01,#cstmrForeignerRrn02,#cstmrNativeRrn01,#cstmrNativeRrn02, #minorAgentTelFn,#minorAgentTelMn,#minorAgentTelRn").keyup(function(){
        isValidateNonEmptyStep1();
    });


    $("#minorAgentAgrmYn").click(function(){
        isValidateNonEmptyStep1();
    });

    $('#btnOnline').click(function(){
        
        $("#onOffType").val("0");
        pageObj.applDataForm.onOffType = "0";
        $(".c-page--title").html("유심 요금제 가입하기");

        $("._self").hide();
        $("._noSelf").show();

        $("._btnNext").html("주문완료");  /////온라인 신청

        KTM.Dialog.closeAll();
        
        
        setTimeout(function(){
            window.scrollTo(0, 0);
        }, 500);

        if (pageObj.step == 2) {
            isValidateNonEmptyStep2();
        } else {
            isValidateNonEmptyStep1();
        }
    });

    $('._btnCheck').click(function(){

        if (pageObj.step == 1) {
            isValidateNonEmptyStep1();
        } else {
            isValidateNonEmptyStep2();
        }
    });

    var nextSend = function () {
        KTM.LoadingSpinner.show();
        if (isSimpleForm()) {
            setTimeout(function () {
                fnReqSelf();
            }, 300);
        } else {
            setTimeout(function () {
                fnSaveForm();
            }, 300);
        }
    }

    $("#btnSearchNumber").click(function () {
        var action = $(this).attr("action");
        if (action == "RSV") {
            searchNumber();
        } else {
            cancelNumber();
        }
    });

    var searchNumber = function () {
        validator.config = {}
        validator.config['reqWantNumberN'] = 'isNumFix4';
        $(".c-form__text").remove();
        $(".has-error").removeClass("has-error");

        if (validator.validate()) {
            var varData = ajaxCommon.getSerializedData({
                reqWantNumber: $("#reqWantNumberN").val()
                , requestKey: pageObj.requestKey
            });

            ajaxCommon.getItem({
                    id: 'searchNumber'
                    , cache: false
                    , url: '/appform/searchNumberAjax.do'
                    , data: varData
                    , dataType: "json"
                }
                , function (jsonObj) {
                    if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
                        var phoneNoList = jsonObj.RESULT_OBJ_LIST;
                        var arr = [];
                        var i = 0;


                        $("#searchCnt").html("조회 가능 횟수<b>" + (20 - jsonObj.SEARCH_CNT) + "회</b>");
                        for (i = 0; i < phoneNoList.length; i++) {
                            arr.push("<input type='radio' class='c-radio' name='rdoCtn' id='" + phoneNoList[i].tlphNo + "' value='" + phoneNoList[i].tlphNo + "'  ");
                            arr.push("   tlphNoStatCd='" + phoneNoList[i].tlphNoStatCd + "' tlphNoOwnCmncCmpnCd='" + phoneNoList[i].tlphNoOwnCmncCmpnCd + "' ");
                            arr.push("   cntValue='" + phoneNoList[i].tlphNoView + "' encdTlphNo='" + phoneNoList[i].encdTlphNo + "'  />\n");
                            arr.push("<label class='c-label u-mb--20' for='" + phoneNoList[i].tlphNo + "'>" + phoneNoList[i].tlphNoView + "</label>\n");
                        }

                        $("#searchNumDialog ._list").empty();
                        $("#searchNumDialog ._list").append(arr.join(''));

                        var el = document.querySelector('#searchNumDialog');
                        var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                        modal.open();

                    } else if (jsonObj.RESULT_CODE == "0001") {
                        MCP.alert("선택 가능한 번호가 없습니다.\n다른 번호로 입력 하시기 바랍니다.");
                    } else if (jsonObj.RESULT_CODE == "0004") {
                        MCP.alert("조회가능 횟수를 초과하였습니다. \n추가 조회를 원하신다면 신청서를 다시 작성해주세요.", function () {
                            ajaxCommon.createForm({
                                method: "post"
                                , action: "/appForm/appForm.do"
                            });

                            ajaxCommon.attachHiddenElement("requestKey", $("#requestKeyTemp").val());
                            ajaxCommon.formSubmit();
                        });
                    } else {
                        MCP.alert("번호 조회에 실패 하였습니다.");
                    }
                });
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg(), function () {
                $selectObj = $("#" + errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
            return;
        }
    };

    var cancelNumber = function () {
        KTM.LoadingSpinner.show();
        var varData = ajaxCommon.getSerializedData({
            reqWantNumber: $("#reqWantNumberN").val()
        });

        ajaxCommon.getItemNoLoading({
                id: 'cancelNumberAjax'
                , cache: false
                , url: '/appform/cancelNumberAjax.do'
                , data: varData
                , dataType: "json"
                , errorCall: function () {
                    KTM.LoadingSpinner.hide(true);
                }
            }
            , function (jsonObj) {
                if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
                    $("#reqCnt").val("");
                    $("#reqWantNumberN").val("");
                    $('#reqWantNumberN').prop('readonly', false);
                    $("._searchBefor").show();
                    $("._searchAfter").hide();
                    $('#btnSearchNumber').html("번호조회");
                    $("#btnSearchNumber").attr("action", "RSV");
                    setTimeout(function () {
                        KTM.LoadingSpinner.hide(true);
                    }, 500);

                } else {
                    alert("전화번호 예약 취소가 실패 하였습니다.");
                    KTM.LoadingSpinner.hide(true);

                }
            });
    };


    $("#btnConfirmNum").click(function () {
        if ($("input:radio[name=rdoCtn]:checked").length > 0) {
            KTM.LoadingSpinner.show();
            var $chkRadioObj = $("input:radio[name=rdoCtn]:checked");
            var varData = ajaxCommon.getSerializedData({
                tlphNo: $chkRadioObj.val()
                , tlphNoStatCd: $chkRadioObj.attr("tlphNoStatCd")
                , tlphNoOwnCmpnCd: $chkRadioObj.attr("tlphNoOwnCmncCmpnCd")
                , encdTlphNo: $chkRadioObj.attr("encdTlphNo")
            });

            ajaxCommon.getItemNoLoading({
                    id: 'setNumberAjax'
                    , cache: false
                    , url: '/appform/setNumberAjax.do'
                    , data: varData
                    , dataType: "json"
                    , errorCall: function () {
                        KTM.LoadingSpinner.hide(true);
                    }
                }
                , function (jsonObj) {
                    if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
                        $('#reqWantNumberN').prop('readonly', true);

                        $("#reqCnt").val($("input:radio[name=rdoCtn]:checked").val());
                        $("#reqWantNumberText").html($("input:radio[name=rdoCtn]:checked").attr("cntValue"));
                        $('#btnSearchNumber').html("번호취소");
                        $("#btnSearchNumber").attr("action", "RRS");
                        $("._searchBefor").hide();
                        $("._searchAfter").show();
                        isValidateNonEmptyStep2();
                        KTM.Dialog.closeAll();

                        setTimeout(function () {
                            KTM.LoadingSpinner.hide(true);
                        }, 500);


                    } else {
                        MCP.alert("번호 예약이 실패 하였습니다.");
                        KTM.LoadingSpinner.hide(true);

                    }
                });
        } else {
            MCP.alert("전화번호를 선택 하여 주시기 바랍니다.");
        }
    });


    $("#recommendFlagCd").change(function () {
        var thisVal = $(this).val();

        if (thisVal == "") {
            $("#recommendInfo").prop("disabled", "disabled");
            $("#recommendInfo").val("");
        } else {
            $("#recommendInfo").prop("disabled", "");
        }
        isValidateNonEmptyStep2();
    });

    $("#recommendInfo").keyup(function () {
        isValidateNonEmptyStep2();
    });

    var getGiftRowTemplate = function (obj) {
        var arr = [];

        var giftPromotionDtlList = obj.giftPromotionDtlList;

        if("C" == obj.prmtType){
            arr.push("<div class='gift-pick-wrap u-mb--14'>\n");
            arr.push("    <h5 class='gift-pick-tit'>" + obj.prmtNm + "</span></h5>\n");
            arr.push("    <ul class='summary-badge-list'>\n");
            for (var i = 0; i < giftPromotionDtlList.length; i++) {
                arr.push("        <li class='summary-badge-list__item'>\n");
                arr.push("            <input class='c-checkbox c-checkbox--type3' id='" + obj.prmtId + giftPromotionDtlList[i].prdtId + "' value='" + giftPromotionDtlList[i].prdtId + "' type='checkbox' prmtId='" + obj.prmtId + "' limitCnt='" + obj.limitCnt + "' prmtType='" + obj.prmtType + "' name='checkboxGift'>\n");
                arr.push("            <label class='c-label' for='" + obj.prmtId + giftPromotionDtlList[i].prdtId + "' >\n");
                arr.push("                <div class='summary-badge-list__item-img'>\n");
                arr.push("                    <img src='" + giftPromotionDtlList[i].webUrl + "' alt='" + giftPromotionDtlList[i].prdtNm + "' onerror=\"this.src='/resources/images/portal/content/img_phone_noimage.png';\">\n");
                arr.push("                </div>\n");
                arr.push("                <p>" + giftPromotionDtlList[i].prdtNm + "</p>\n");
                arr.push("            </label>\n");
                arr.push("        </li>\n");
            }
            arr.push("    </ul>\n");
            arr.push("</div>\n");
        }

        return arr.join('');
    }

    var getGiftList = function () {
        if (pageObj.giftList.length > 0) {
            return null;
        }

        var usimOrgnId = pageObj.usimOrgnId;
        if (usimOrgnId == null || usimOrgnId == "") {
            usimOrgnId = $("#cntpntShopId").val();
        }

        var enggMnthCnt = $("#enggMnthCnt").val();
        if (enggMnthCnt == null || enggMnthCnt == "") {
            enggMnthCnt = "0";
        }

        //5,5
        var onOffTypeVal = $("#onOffType").val();
        //onOffTypeVal = 5;  //TEST 위한
        var varData = ajaxCommon.getSerializedData({
            onOffType: onOffTypeVal
            , operType: $("#operType").val()
            , reqBuyType: $("#reqBuyType").val()
            , agrmTrm: enggMnthCnt
            , rateCd: $("#socCode").val()
            , orgnId: usimOrgnId
            , defaultOrgnId: $("#cntpntShopId").val()
            , modelId : $("#modelId").val()
        });

        ajaxCommon.getItemNoLoading({
                id: 'getGiftList'
                , cache: false
                , async: false
                , url: '/appForm/giftBasListDefaultAjax.do'
                , data: varData
                , dataType: "json"
                , errorCall: function () {
                    //nothing
                }
            }
            , function (resultList) {
                var $divGift = $('#divGift');
                pageObj.giftList = resultList;
                $divGift.empty();

                if (resultList.length > 0) {

                    var chooseGiftPrmt = resultList.filter(item => item.prmtType == "C");
                    if(chooseGiftPrmt.length > 0){
                        $divGift.append("<h4 class='gift-tit--type2'>선택 사은품</h4>");
                        $divGift.append("    <hr class='gift-hr--type2'>");

                        for (var i = 0; i < resultList.length; i++) {
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

    var isValidateGift = function () {
        //사은품 없음.. 통과
        if (pageObj.giftList.length < 1) {
            return true;
        }

        for (var i = 0; i < pageObj.giftList.length; i++) {
            var limitCnt = pageObj.giftList[i].limitCnt;
            var prmtId = pageObj.giftList[i].prmtId;
            var prmtType = pageObj.giftList[i].prmtType;


            if (prmtType == "C") {
                var checkCnt = $("input:checkbox[name=checkboxGift][prmtid=" + prmtId + "]:checked").length;
                if (limitCnt > checkCnt) {
                    return false;
                }
            }

        }
        return true;
    };

    $(document).on("click", "input:checkbox[name=checkboxGift]", function () {
        var $thisObj = $(this);
        var prmtId = $thisObj.attr("prmtId");
        var prmtType = $thisObj.attr("prmtType");
        var prdtId = $(this).val();
        var limitCnt = parseInt($thisObj.attr("limitCnt"), 10);
        var selectCnt = $("input:checkbox[name=checkboxGift][prmtId=" + prmtId + "]:checked").length;

        if ("D" != prmtType) {
            if (limitCnt < selectCnt) {
                MCP.alert("사은품 선택 개수를 초과하였습니다.", function () {
                    $thisObj.prop("checked", false);
                });
            }
        } else {
            $thisObj.prop("checked", true);
        }
    });

    //0, 100, N --> 0세~100세 신청불가
    //0, 100, Y --> 0세~100세 신청가능
    var checkPriceLimitForAge = function (ageInt) {
        if (pageObj.priceLimitObj != null && pageObj.priceLimitObj.dtlCd != null && pageObj.priceLimitObj.dtlCd != "") {
            var priceLimitObj = pageObj.priceLimitObj;
            var startAge = parseInt(priceLimitObj.expnsnStrVal1, 10);
            var endAge = parseInt(priceLimitObj.expnsnStrVal2, 10);
            var division = priceLimitObj.expnsnStrVal3;

            if ("N" == division) {
                if (startAge <= ageInt && endAge >= ageInt) {
                    return false;  //불가능
                } else {
                    return true;  //가능
                }
            } else {
                if (startAge <= ageInt && endAge >= ageInt) {
                    return true;  //가능
                } else {
                    return false;  //불가능
                }
            }
        } else {
            return true;  //가능
        }
    }

    //할부 금액 정보 조회 및 요금제
    var getListSale = function () {

        var prodId = $("#prodId").val();
        var modelSalePolicyCode = $("#modelSalePolicyCode").val();
        var agrmTrm = $("#enggMnthCnt").val();
        var modelMonthlyVal = $("#modelMonthly").val();
        var reqBuyType = $("#reqBuyType").val();
        var plcySctnCd = "02";  //--정책구분코드(01:단말,02:유심)

        if (agrmTrm == "") {
            agrmTrm = "0";
        }

        if (modelMonthlyVal == "") {
            modelMonthlyVal = "0";
        }

        var varData = ajaxCommon.getSerializedData({
            salePlcyCd: modelSalePolicyCode
            , prdtId: $("#rprsPrdtId").val()
            , orgnId: $("#cntpntShopId").val()
            , operType: $("#operType").val()
            , rateCd: $("#socCode").val()
            , onOffType: $("#onOffType").val()
            , noArgmYn: "Y"
            , agrmTrm: agrmTrm
            , plcySctnCd: plcySctnCd
            , modelMonthly: modelMonthlyVal
        });

        ajaxCommon.getItemNoLoading({
                id: 'listChargeInfoAjax'
                , cache: false
                , url: '/msp/listChargeInfoAjax.do'
                , data: varData
                , dataType: "json"
                , async: false
            }
            , function (objList) {
                if (objList.length > 0) {
                    pageObj.rateNm = objList[0].rateNm;
                    pageObj.rateObj = objList[0];
                    $("#payMnthChargeAmtTxt").html(numberWithCommas(objList[0].payMnthChargeAmt));
                    $("#baseAmt").html(numberWithCommas(objList[0].baseVatAmt) + " 원");
                    $("#dcAmtTxt").html(numberMinusWithCommas(objList[0].dcVatAmt) + " 원");
                    $("#addDcAmt").html(numberMinusWithCommas(objList[0].addDcVatAmt) + " 원");
                    $("#promotionDcAmtTxt").html(numberMinusWithCommas(objList[0].promotionDcAmt) + " 원");
                    $("#hndstAmtTxt").html(numberWithCommas(objList[0].hndstAmt) + " 원");
                    $("#subsdAmtTxt").html(numberMinusWithCommas(objList[0].subsdAmt) + " 원");
                    $("#agncySubsdAmtTxt").html(numberMinusWithCommas(objList[0].agncySubsdAmt) + " 원");
                    $("#instAmtTxt").html(numberWithCommas(objList[0].instAmt) + " 원");
                    $("#addDcAmtTxt").html(numberMinusWithCommas(objList[0].addDcAmt) + " 원");
                    $("#payMnthAmtTxt").html(numberWithCommas(objList[0].payMnthAmt));
                    $("#totalInstCmsnTxt").html(numberWithCommas(objList[0].totalInstCmsn) + " 원");
                    $("#paySumMnthTxt").html(numberWithCommas(getMnthChargeAmt()));
                    $("#paySumMnthTxt2").html(numberWithCommas(getMnthChargeAmt()));
                }
            });


        if (modelSalePolicyCode != "") {
            ajaxCommon.getItem({
                    id: 'getSalePlcyAjax'
                    , cache: false
                    , url: '/msp/getSalePlcyAjax.do'
                    , data: ajaxCommon.getSerializedData({salePlcyCd: modelSalePolicyCode})
                    , dataType: "json"
                }
                , function (obj) {
                    //정책에서 정보 조회처리
                    $("#prdtSctnCd").val(obj.prdtSctnCd);
                    $("#sprtTp").val(obj.sprtTp);
                });


            //USIM 상품정보 조회
            var varDataUsim = ajaxCommon.getSerializedData({
                prdtId: $("#modelId").val()
                //, salePlcyCd:modelSalePolicyCode
            });

            ajaxCommon.getItemNoLoading({
                    id: 'getUsimBasInfoAjax'
                    , cache: false
                    , url: '/appForm/getUsimBasInfoAjax.do'
                    , data: varDataUsim
                    , dataType: "json"
                    , async: false
                }
                , function (obj) {
                    if (obj.prdtNm != null) {
                        pageObj.prdtNm = obj.prdtNm;
                    }
                });
        }

        var prdtNmTemp = "";
        if (pageObj.prdtNm != "") {
            prdtNmTemp = " / " + pageObj.prdtNm;
        }
        var operTypeNm = " / " + $('#operTypeNm').val();


        $('#bottomTitle2').text(pageObj.rateNm);
        $('#pRateNm').text(pageObj.rateNm);
        $('#ddOperTypeNm').text($('#operTypeNm').val());

    };

    //청소년 요금제 확인
    var getPriceLimitInfo = function () {
        var varData = ajaxCommon.getSerializedData({
            dtlCd: $("#socCode").val()
            , cdGroupId: CD_GROUP_ID_OBJ.RateLimit
        });

        ajaxCommon.getItemNoLoading({
                id: 'getPriceLimitInfo'
                , cache: false
                , url: '/getCodeNmAjax.do'
                , data: varData
                , dataType: "json"
                , async: false
                , errorCall: function () {
                    //NOTHING
                }
            }
            , function (jsonObj) {
                pageObj.priceLimitObj = jsonObj;
            });
    }();

    var showJoinUsimPrice = function () {
        var prdtSctnCd = $("#prdtSctnCd").val();
        var joinPriceIsPay = $("#joinPriceIsPay").val();
        var usimPriceIsPay = $("#usimPriceIsPay").val();
        var usimPriceNfcIsPay = $("#usimPriceNfcIsPay").val();
        var usimPrice = $("#usimPrice").val();
        var joinPrice = $('#joinPrice').val();

        var radUsimProd = $("input[name='radUsimProd']:checked").val();
        if (radUsimProd == undefined) {
            radUsimProd = "05";
        }

        if (prdtSctnCd == PRDT_SCTN_CD.LTE_FOR_MSP) {
            if ("06" == radUsimProd) {
                usimPrice = $("#usimPriceNfc").val();
            }
        }

        if (joinPriceIsPay == "Y") {
            $("#joinPriceTxt").html(numberWithCommas(joinPrice) + " 원");
        } else {
            $("#joinPriceTxt").html("<span class='c-text u-td-line-through'>" + numberWithCommas(joinPrice) + " 원</span>(무료)");
        }

        if ("06" == radUsimProd) {
            if (usimPriceNfcIsPay == "Y") {
                $("#usimPriceTxt").html(numberWithCommas(usimPrice) + " 원");
            } else {
                $("#usimPriceTxt").html("<span class='c-text u-td-line-through'>" + numberWithCommas(usimPrice) + " 원</span>(무료)");
            }
        } else {
            if (usimPriceIsPay == "Y") {
                $("#usimPriceTxt").html(numberWithCommas(usimPrice) + " 원");
            } else {
                $("#usimPriceTxt").html("<span class='c-text u-td-line-through'>" + numberWithCommas(usimPrice) + " 원</span>(무료)");
            }
        }

    }

    var getInstMnthAmt = function () {
        var usePoint = 0;
        var payMnthAmt = pageObj.rateObj.payMnthAmt; //월납부 단말요금
        if (usePoint == 0) {
            return payMnthAmt;
        } else {
            return payMnthAmt - Math.floor(usePoint / pageObj.rateObj.modelMonthly);
        }
    }

    ///** 월납부 통신요금+월단말요금 */
    var getMnthChargeAmt = function () {
        var modelMonthlyVal = $("#modelMonthly").val();
        if (modelMonthlyVal == "") {
            modelMonthlyVal = "0";
        }

        if ("0" == modelMonthlyVal) {
            return pageObj.rateObj.payMnthChargeAmt;
        } else {
            return pageObj.rateObj.payMnthChargeAmt + getInstMnthAmt();
        }

    }

    //가입비, 유심비 , 무료 , 유료
    var getJoinUsimPrice = function () {
        var varData = ajaxCommon.getSerializedData({
            orgnId: $("#cntpntShopId").val()
            , operType: $("#operType").val()
            , dataType: $("#prdtSctnCd").val()
            , prdtIndCd: "11"
            , rateCd: $("#socCode").val()
        });

        ajaxCommon.getItemNoLoading({
            id: 'getSimInfo'
            , cache: false
            , url: '/usim/getSimInfoAjax.do'
            , data: varData
            , dataType: "json"
            , async: false
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
    }();

    var getContractInfoByNum = function () {
        var varData = ajaxCommon.getSerializedData({
            prntsContractNo: $("#prntsContractNo").val()
        });

        ajaxCommon.getItemNoLoading({
            id: 'getContractInfoByNum'
            , cache: false
            , url: '/appForm/getContractInfoByNumAjax.do'
            , data: varData
            , dataType: "json"
            , async: false
        }, function (jsonObj) {

            if (AJAX_RESULT_CODE.SUCCESS  == jsonObj.RESULT_CODE) {
                var cstmrType = jsonObj.CSTMR_TYPE;
                $("input:radio[name=cstmrType][value=" + cstmrType + "]").attr("disabled", false); //설정
                $("input:radio[name=cstmrType][value=" + cstmrType + "]").trigger("click");

                $("#cstmrName").val(jsonObj.CSTMR_NM);

                if (cstmrType == CSTMR_TYPE.FN) {
                    $("#cstmrForeignerRrn01").val(jsonObj.CSTMR_SSN);
                } else {
                    $("#cstmrNativeRrn01").val(jsonObj.CSTMR_SSN);
                }
            } else {
                MCP.alert("M모바일 정보를 확인할 수 없습니다. ");
            }



        });
    }();


    var fnSaveForm = function () {
        pageObj.applDataForm.requestKey = 0;
        pageObj.applDataForm.evntCdPrmt = $("#evntCdPrmt").val();
        var varData = ajaxCommon.getSerializedData(pageObj.applDataForm);

        ajaxCommon.getItemNoLoading({
                id: 'saveAppform'
                , cache: false
                , url: '/appForm/saveAppformAjax.do'
                , data: varData
                , dataType: "json"
                , errorCall: function () {
                    KTM.LoadingSpinner.hide(true);
                    $('._btnNext').show();
                }
            }
            , function (jsonObj) {
                if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
                    pageObj.requestKey = jsonObj.REQUEST_KET;

                    ajaxCommon.createForm({
                        method: "post"
                        , action: "/eSimForm/appFormComplete.do"
                    });

                    ajaxCommon.attachHiddenElement("requestKey", pageObj.requestKey);
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
    };

    //개통사전체크요청 PC0
    var fnReqPreCheck = function () {
        var onOffType = $("#onOffType").val();
        var errMsg = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.";
        var operType = $("#operType").val();

        KTM.LoadingSpinner.show();

        //pageObj.applDataForm.onlineAuthInfo = pageObj.cid;
        pageObj.applDataForm.onOffType = onOffType;
        pageObj.applDataForm.evntCdPrmt = $("#evntCdPrmt").val();

        var varData = ajaxCommon.getSerializedData(pageObj.applDataForm);
        ajaxCommon.getItemNoLoading({
            id: 'reqPreCheckAjax'
            , cache: false
            , url: '/appform/reqPreOpenCheckAjax.do'
            , data: varData
            , dataType: "json"
            , errorCall: function () {
                KTM.LoadingSpinner.hide(true);
                MCP.alert(errMsg);
            }
        }, function (jsonObj) {
            if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
                pageObj.requestKey = jsonObj.REQUEST_KET;
                pageObj.resNo = jsonObj.RES_NO;
                pageObj.checkCnt = 0;
                fnSelfPreOpenCallBack();

            } else {

                if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_PC0_RE_TRY[jsonObj.OSST_RESULT_CODE] != undefined && pageObj.fnReqPreCheckCount < 30) {
                    setTimeout(function () {
                        pageObj.fnReqPreCheckCount++;
                        fnReqPreCheck();
                    }, 20000);
                    return;
                }

                var strTitle = "번호이동 진행 실패";
                strTitle = "신규가입 진행 실패";

                if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_PC0_MSG[jsonObj.OSST_RESULT_CODE] != undefined) {
                    errMsg = SIMPLE_PC0_MSG[jsonObj.OSST_RESULT_CODE];
                } else if (jsonObj.ERROR_NE_MSG != undefined) {
                    errMsg = jsonObj.ERROR_NE_MSG;
                }
                KTM.LoadingSpinner.hide(true);
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


    var fnSelfPreOpenCallBack = function () {
        var varData = ajaxCommon.getSerializedData({
            resNo: pageObj.resNo
            , prgrStatCd: "PC2"
        });
        KTM.LoadingSpinner.show();
        ajaxCommon.getItemNoLoading({
                id: 'conPreCheckAjax'
                , cache: false
                , url: '/appform/conPreCheckAjax.do'
                , data: varData
                , dataType: "json"
            }
            , function (jsonObj) {
                if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
                    setStep(2);
                    isValidateNonEmptyStep2();
                    $('._btnNext').html("개통요청").show();
                    KTM.LoadingSpinner.hide(true);

                } else if ("9999" == jsonObj.RESULT_CODE) {
                    if (pageObj.checkCnt < 21) {

                        ++pageObj.checkCnt
                        setTimeout(function () {
                            fnSelfPreOpenCallBack();
                        }, 5000);
                    } else {
                        KTM.LoadingSpinner.hide(true);
                        MCP.alert("아직 고객 사전체크 결과가 확인되지 않습니다.<br>확인까지 최대 2분이 소요 될 수 있습니다.", function () {
                            pageObj.checkCnt = 0;
                            fnSelfPreOpenCallBack();
                        });
                    }
                } else {
                    KTM.LoadingSpinner.hide(true);
                    var operType = $("#operType").val();
                    var strHtml = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.";
                    if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_PC2_MSG[jsonObj.OSST_RESULT_CODE] != undefined) {
                        strHtml = SIMPLE_PC2_MSG[jsonObj.OSST_RESULT_CODE];
                    } else if (jsonObj.RESULT_MSG != undefined) {
                        strHtml = jsonObj.RESULT_MSG;
                    }

                    var layerType = "divDefaultButton";
                    var strTitle = "";
                    if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_PC2_TYPE[jsonObj.OSST_RESULT_CODE] != undefined) {
                        layerType = SIMPLE_PC2_TYPE[jsonObj.OSST_RESULT_CODE];
                    }

                    if (operType == OPER_TYPE.NEW) {
                        strTitle = "신규가입 진행 실패";
                    } else {
                        strTitle = "번호이동 진행 실패";
                    }

                    $("#simpleDialog ._title").html(strTitle);
                    $("#simpleDialog ._detail").html(strHtml);
                    $("._simpleDialogButton").hide();
                    $("#" + layerType).show();

                    var el = document.querySelector('#simpleDialog');
                    var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                    modal.open();
                }
            });

    };


    var fnReqSelf = function () {
        $('._btnNext').hide();
        var onOffType = $("#onOffType").val();
        pageObj.applDataForm.onOffType = onOffType;
        pageObj.applDataForm.requestKey = pageObj.requestKey;

        //UPDATE 처리 하지 않도록 처리
        pageObj.applDataForm.cstmrPost = "";
        pageObj.applDataForm.cstmrAddr = "";
        pageObj.applDataForm.cstmrAddrDtl = "";

        var varData = ajaxCommon.getSerializedData(pageObj.applDataForm);

        ajaxCommon.getItemNoLoading({
            id: 'reqSimpleOpenAjax'
            , cache: false
            , url: '/appform/reqSimpleOpenAjax.do'
            , data: varData
            , dataType: "json"
        }, function (jsonObj) {
            $('._btnNext').show();
            if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
                //sendScanImage();
                pageObj.checkCnt = 0;
                fnSelfOpenCallback();
            } else {
                KTM.LoadingSpinner.hide(true);
                var errMsg = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.";
                if (jsonObj.ERROR_NE_MSG != undefined) {
                    errMsg = jsonObj.ERROR_NE_MSG;
                }
                var operType = $("#operType").val();
                var strTitle = "";
                strTitle = "신규가입 진행 실패";

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

    var fnSelfOpenCallback = function () {
        $("#divBtnGroup1").show();
        $("#divBtnGroup2").hide();
        $("._btnNext").html("개통확인");
        $('._btnNext').removeClass('is-disabled');

        var varData = ajaxCommon.getSerializedData({
            resNo: pageObj.resNo
            , prgrStatCd: "OP2"
        });

        ajaxCommon.getItemNoLoading({
            id: 'conPreCheckAjax'
            , cache: false
            , url: '/appform/conPreCheckAjax.do'
            , data: varData
            , dataType: "json"
        }, function (jsonObj) {
            //KTM.LoadingSpinner.hide(true);
            if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
                var actionURL = "/eSimForm/appFormComplete.do";

                ajaxCommon.createForm({
                    method: "post"
                    , action: actionURL
                });
                ajaxCommon.attachHiddenElement("requestKey", pageObj.requestKey);
                ajaxCommon.formSubmit();
            } else if ("9999" == jsonObj.RESULT_CODE) {
                if (pageObj.checkCnt < 21) {
                    ++pageObj.checkCnt
                    setTimeout(function () {
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
                    $('#reqCardCompany').prop('disabled', false);
                    $('#reqBank').prop('disabled', false);
                    $('#reqAccountNumber').prop('readonly', false);
                    $("#btnCheckAccount").removeClass('is-complete').html("계좌번호 체크");
                    $("#btnCheckCardNo").removeClass('is-complete').html("신용카드 체크 ");

                }

                var strHtml = "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.";
                if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_OP2_MSG[jsonObj.OSST_RESULT_CODE] != undefined) {
                    strHtml = SIMPLE_OP2_MSG[jsonObj.OSST_RESULT_CODE];
                } else if (jsonObj.RESULT_MSG != undefined) {
                    strHtml = jsonObj.RESULT_MSG;
                }

                var layerType = "divDefaultButton";
                var strTitle = "";
                if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_OP2_TYPE[jsonObj.OSST_RESULT_CODE] != undefined) {
                    layerType = SIMPLE_OP2_TYPE[jsonObj.OSST_RESULT_CODE];
                }

                var operType = $("#operType").val();
                if (operType == OPER_TYPE.NEW) {
                    strTitle = "신규가입 진행 실패";
                } else {
                    strTitle = "번호이동 진행 실패";
                }

                $("#simpleDialog ._title").html(strTitle);
                $("#simpleDialog ._detail").html(strHtml);
                $("._simpleDialogButton").hide();
                $("#" + layerType).show();

                var el = document.querySelector('#simpleDialog');
                var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
                modal.open();

            }
        });
    };


    // 서식지 설계 배너 조회 (O001)
    var displayO001BannerList = function () {
        var varData = ajaxCommon.getSerializedData({
            bannCtg: 'O001'
        });

        ajaxCommon.getItem({
                id: 'getO001BannerListAjax'
                , cache: false
                , async: false
                , url: '/bannerListAjax.do'
                , data: varData
                , dataType: "json"
            }
            , function (result) {
                let bannerHtml = "";
                let returnCode = result.returnCode;
                let message = result.returnCode;
                let bannerList = result.result;

                if (returnCode == "00") {
                    if (bannerList.length > 0) {
                        $.each(bannerList, function (index, value) {
                            let v_bannSeq = ajaxCommon.isNullNvl(value.bannSeq, "");
                            let v_bannCtg = ajaxCommon.isNullNvl(value.bannCtg, "");
                            let v_bannNm = ajaxCommon.isNullNvl(value.bannNm, "");
                            let v_bannDesc = ajaxCommon.isNullNvl(value.bannDesc, "");
                            let v_bannApdDesc = ajaxCommon.isNullNvl(value.bannApdDesc, "");
                            let v_bannBgColor = ajaxCommon.isNullNvl(value.bannBgColor, "");
                            let v_bannImg = ajaxCommon.isNullNvl(value.bannImg, "");
                            let v_imgDesc = ajaxCommon.isNullNvl(value.imgDesc, "");
                            let v_mobileLinkUrl = ajaxCommon.isNullNvl(value.mobileLinkUrl, "");

                            let v_linkTarget = ajaxCommon.isNullNvl(value.linkTarget, "");
                            let v_linkUrlAdr = ajaxCommon.isNullNvl(value.linkUrlAdr, "");

                            if (v_linkTarget == 'Y' && v_linkUrlAdr != '') {
                                bannerHtml += '<div class="swiper-slide" href="javascript:void(0);" onclick="javascript:insertBannAccess(\'' + v_bannSeq + '\',\'' + v_bannCtg + '\');location.href=\'' + v_linkUrlAdr + '\'";>';
                            } else {
                                bannerHtml += '<div class="swiper-slide">';
                            }

                            bannerHtml += '     <img src="' + v_bannImg + '" alt="' + v_bannNm + '">';
                            bannerHtml += ' </div>';
                            if (index == 19) {
                                return false;
                            }
                        });

                        $("#O001").html(bannerHtml);
                    } else {
                        $('.c-bs-compare').addClass('no-banner');
                        $('.top-sticky-banner').hide();
                    }
                } else {
                    MCP.alert(message);
                }
            });
    }();

    getListSale();

    if (isSimpleForm()) {
        $("._self").show();
        $("._noSelf").hide();
        $("._btnNext").html("개통사전체크확인");
    } else {
        $("._self").hide();
        $("._noSelf").show();
    }


    //setStep(2)

    $('.c-accordion.c-accordion--nested > .c-accordion__box > .c-accordion__item > .c-accordion__head .c-accordion__button, .c-accordion.c-accordion--nested__sub > .c-accordion__box > .c-accordion__item > .c-accordion__head .c-accordion__button').click(function() {
        handleAccordionClick(this);
    });

    updateFathSkipBtnVisibility();
});  // end of document.ready ---------------------------------

    //셀프개통신규 제한 기능
    var checkLimit = function (reqSeq, resSeq) {

        var rtnObj = false;
        //셀프개통신규 다회선 제한 기능
        var varData = ajaxCommon.getSerializedData({
             socCode: $("#socCode").val()
            ,usimKindsCd: $('#usimKindsCd').val()
            ,reqSeq: reqSeq
            ,resSeq: resSeq
            ,requestKeyTemp:$('#requestKeyTemp').val()
        });

        ajaxCommon.getItemNoLoading({
                id: 'checkLimitFormAjax'
                , cache: false
                , async: false
                , url: '/appform/checkLimitFormAjax.do'
                , data: varData
                , dataType: "json"
            }
            , function (jsonObj) {
                if (AJAX_RESULT_CODE.SUCCESS == jsonObj.RESULT_CODE) {
                    rtnObj = true; //통과
                } else if ("-1" == jsonObj.RESULT_CODE) {
                    $("#simpleDialog ._title").html("개통 진행 실패");
                    $("#simpleDialog ._detail").html("※ 신규가입은 명의당 30일이내 1회선만 가입 가능합니다.<br>추가 가입은 최근 가입하신 KT M모바일 회선 가입일을 기준으로 30일 경과된 시점에 신청 부탁드립니다.");
                    $("._simpleDialogButton").hide();
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

    var fnNiceCert = function (prarObj) {

        var strMsg;
        pageObj.niceResLogObj = prarObj;

        var authInfoJson = {};

        //본인인증
        if (pageObj.niceType == NICE_TYPE.CUST_AUTH) {
            var cstmrType = $("input:radio[name=cstmrType]:checked").val();
            if (cstmrType == CSTMR_TYPE.NA) {
                //내국인
                authInfoJson = {
                    authType: prarObj.authType,
                    contractNum: $("#prntsContractNo").val(),
                    cstmrType: cstmrType
                };
                strMsg = '<p class="u-mt--12"><strong class="u-fw--bold">본인인증 정보가 일치하지 않습니다.</strong></p><p class="u-mt--12">입력하신 정보를 확인 후 다시 시도해 주시기 바랍니다.</p>';
            } else if (cstmrType == CSTMR_TYPE.NM) {
                var cstmrNativeRrn = $.trim($("#minorAgentRrn01").val()) + $.trim($("#minorAgentRrn02").val());
                cstmrNativeRrn = cstmrNativeRrn.substring(0,6);
                //청소년
                authInfoJson = {
                    cstmrName: $.trim($("#minorAgentName").val()),
                    cstmrNativeRrn: cstmrNativeRrn,
                    authType: prarObj.authType,
                    ncType: "1",
                    onOffType: $("#onOffType").val()
                };
                strMsg = '<p class="u-mt--12"><strong class="u-fw--bold">법정대리인 본인인증 정보가<br/>일치하지 않습니다.</strong></p><p class="u-mt--12">입력하신 정보를 확인 후 다시 시도해 주시기 바랍니다.</p>';
            } else if (cstmrType == CSTMR_TYPE.FN) {
                //외국인
                authInfoJson = {
                    authType: prarObj.authType,
                    contractNum: $("#prntsContractNo").val(),
                    cstmrType: cstmrType
                };
                strMsg = '<p class="u-mt--12"><strong class="u-fw--bold">본인인증 정보가 일치하지 않습니다.</strong></p><p class="u-mt--12">입력하신 정보를 확인 후 다시 시도해 주시기 바랍니다.</p>';
            } else {
                MCP.alert("지원하지 않는 고객유형 입니다.");
                return;
            }

            //기존 : 신규/번호이동 구분없이 eSIM/USIM 각 90일 이내 1회선으로 개통 제한
            //변경 : 2026.03 - 신규(셀프/상담사)만 30일 이내 1회선으로 개통 제한 - 워치 제외
            if (isSimpleForm()) {
                if (!checkLimit(prarObj.reqSeq, prarObj.resSeq)) return null;
            }

            var isAuthDone = authCallback(authInfoJson);
            if (isAuthDone) { // 본인인증 최종 성공
                pageObj.authObj = prarObj;
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
                $("input:checkbox[name=authCheckNone]").prop("checked", false);
                return null;

            } else { // 본인인증 최종 실패

                if(niceAuthObj.resAuthOjb.RESULT_CODE == "STEP01" || niceAuthObj.resAuthOjb.RESULT_CODE == "STEP02"){
                    strMsg= niceAuthObj.resAuthOjb.RESULT_MSG;
                }

                MCP.alert(strMsg);
                return null;
            }
        }
    }

    var isValidateNonEmptyStep1 = function () {
        var operType = $("#operType").val();
        var cstmrType = $("input:radio[name=cstmrType]:checked").val();
        var reqBuyType = $("#reqBuyType").val();

        validator.config = {};
        validator.config['cstmrType1'] = 'isNonEmpty';


        if (cstmrType == CSTMR_TYPE.NA) {
            //내국인
            validator.config['cstmrName'] = 'isNonEmpty';
            validator.config['cstmrNativeRrn01'] = 'isNonEmpty';

        } else if (cstmrType == CSTMR_TYPE.NM) {
            //청소년e
            validator.config['cstmrName'] = 'isNonEmpty';
            validator.config['minorAgentName'] = 'isNonEmpty';
            validator.config['cstmrNativeRrn01'] = 'isNonEmpty';
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
        }

        validator.config['isAuth'] = 'isNonEmpty';
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

        if (pageObj.clauseJehuRateCount > 0) {
            validator.config['clauseJehuFlag'] = 'isNonEmpty';
        }

        //안면인증 
        if($("#fathTrgYn").val() == "Y") {
            validator.config['isFathTxnRetv'] = 'isNonEmpty';
        }

        validator.config['selfIssuExprDt'] = 'isIssuDate';
        validator.config['selfInqryAgrmYnFlag'] = 'isNonEmpty';
        
        // if (pageObj.clauseFinanceRateCount > 0) {
        //     validator.config['clauseFinanceFlag'] = 'isNonEmpty';
        // }

        if (isSimpleForm()) {
            validator.config['clauseSimpleOpen'] = 'isNonEmpty';
        }

        if (validator.validate(true)) {
            $('._btnNext').removeClass('is-disabled');
        } else {
            var errId = validator.getErrorId();
            $('._btnNext').addClass('is-disabled');
        }
    };

    var isValidateNonEmptyStep2 = function () {
        validator.config = {};

        if (isSimpleForm()) {
            validator.config['reqCnt'] = 'isNonEmpty';
        } else {
            validator.config['reqWantNumber'] = 'isNonEmpty';
            validator.config['reqWantNumber2'] = 'isNonEmpty';
            validator.config['reqWantNumber3'] = 'isNonEmpty';
        }

        if (validator.validate(true)) {
            $('._btnNext').removeClass('is-disabled');
        } else {
            $('._btnNext').addClass('is-disabled');
        }
    };


    var fnSetEventId = function (eventId) {
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
    };

    var viewErrorMgs = function ($thatObj, msg) {
        if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
            $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>" + msg + "</p>");
        } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3")) {
            $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>" + msg + "</p>");
        }
    };

    function nextFocus(obj, len, id){
        if($(obj).val().length >= len){
            $('#' + id).focus();
        }
    }

    var isSimpleForm = function () {
        var onOffType = $("#onOffType").val();
        if ("7" == onOffType || "5" == onOffType) {
            return true;
        } else {
            return false;
        }
    }

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