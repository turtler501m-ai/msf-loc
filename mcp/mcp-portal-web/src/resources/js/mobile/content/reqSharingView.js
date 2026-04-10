var pageObj = {
    niceType:""
    , authObj:{}
    , niceHistSeq:0
    , startTime:0
    , niceResLogObj:{}   //로그을 저장 하기 위한 인증

}
var fs9Succ = false;
$(document).ready(function (){

    // 숫자만 입력가능1
    $(".numOnly").keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });

    $('#btnClauseSimple').click(function(){
       // fnSetEventId('clauseSimpleOpen');
        $("#targetTerms").val('clauseSimpleOpen');
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

    //전체동의
    $("#chkD1").on("click", function(){

        var chkList = $("input[name=terms]");

        if($(this).is(":checked")){
            chkList.prop("checked", true);
        }else{
            chkList.prop("checked", false);
        }

        if($(this).is(":checked")){
            $('#sharing_reg').removeClass('is-disabled');
        } else{
            if(!$('#sharing_reg').hasClass('is-disabled')){
                $('#sharing_reg').addClass('is-disabled');
            }
        }
    });

    $('input[name=terms]').on("click",function(){
        var cnt = 0;

        $('input[name=terms]').each(function (){
            if($(this).prop('checked')){
                cnt++;
            }
        });


        if(cnt == $('input[name=terms]').length){
            $('#chkD1').prop('checked', 'checked');
        }else{
            $('#chkD1').removeProp('checked');
        }

        var agreeCnt = 0;
        $('input[name=terms]').each(function (){
            if($(this).attr('data-mand-yn') == 'Y' && $(this).is(':checked')){
                agreeCnt++;
            }
        });

        if($('input[name=terms][data-mand-yn=Y]').length == agreeCnt){
            $('#sharing_reg').removeClass('is-disabled');
        } else{
            if(!$('#sharing_reg').hasClass('is-disabled')){
                $('#sharing_reg').addClass('is-disabled');
            }
        }
    });

    // 본인인증 전 추가 유효성 검사 > niceAuth.js 참고
    simpleAuthvalidation = function () {
        return true;
    }

    //신분증 종류 별
    $("input:radio[name=selfCertType]").click(function(){
        var thisVal= $(this).val();
        $("._selfIssuNumF").hide();
        $("._driverSelfIssuNumF").hide();

        switch (thisVal) {
            case '01':  // 주민등록증
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("._divScan").show();
                $("._divScan button").data('type','social');
                $("#imgRadIdCard").attr("src","/resources/images/mobile/content/img_jumin_card.png");
                break;
            case '03':  // 장애인등록증
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("._divScan").hide();
                $("#imgRadIdCard").attr("src","/resources/images/mobile/content/img_welfare_card.png");
                break;
            case '06':  // 외국인등록증
                getNationList();
                setNationSelect();
                $("._cstmrForeignerNation").show();
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("._divScan").hide();
                $("#imgRadIdCard").attr("src","/resources/images/mobile/content/img_foreigner_card.png");

                break;
            case '04':  // 국가유공자증
                $("#selfIssuExprDt").attr("title", "발급 일자");
                $("label[for=selfIssuExprDt]").html("발급 일자");
                $("._selfIssuNumF").show();
                $("._divScan").hide();
                $("#imgRadIdCard").attr("src","/resources/images/mobile/content/img_merit_card.png");
                break;
            case '02':  // 운전면허증
                $("#selfIssuExprDt").attr("title", "운전면허증의 발급 일자");
                $("label[for=selfIssuExprDt]").html("운전면허증의 발급 일자");
                $("._driverSelfIssuNumF").show();
                $("._divScan").show();
                $("._divScan button").data('type','driver');
                $("#imgRadIdCard").attr("src","/resources/images/mobile/content/img_driving_license.png");
                break;
            case '05':  // 여권
                $("#selfIssuExprDt").attr("title", "만료 일자");
                $("label[for=selfIssuExprDt]").html("만료 일자");
                $("._divScan").hide();
                $("#imgRadIdCard").attr("src","/resources/images/mobile/content/img_passport_card.png");
                break;
        }

        if(!fs9Succ) { //페이지 진입 or 고객이 직접 선택하는 경우 , (FS9는 성공 시 신분증타입 선택되는 경우는 제외)
            fnReqFathTgtYn()
        }
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
                contractNum : $("#contractNum").val(),
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

        } else{
            var strMsg = "입력하신 유심번호 사용이 불가능 합니다. 확인 후 다시 시도해 주시기 바랍니다.";
            if (jsonObj.RESULT_MSG != null) {
                strMsg = jsonObj.RESULT_MSG;
             }

             MCP.alert(strMsg,function (){
                 $("#iccId").val('');
                 $("#iccYn").val("");
                 $("#iccId").focus('');
             });
        }

    }

 );

}

//약관동의
function viewTerms(targetId,param){
    $('#targetTerms').val(targetId);
    openPage('termsPop','/termsPop.do',param);
}


function pop_credit() {
    if ($("#isAuth").val() == "1") {
        MCP.alert("본인 인증을 완료 하였습니다. ");
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
        MCP.alert("본인 인증을 완료 하였습니다. ");
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
        MCP.alert("본인 인증을 완료 하였습니다. ");
        return false;
    }

    preOpenTossAuthPopup(); // niceAuth.js에서 처리
}

// PASS 2단계 인증
function pop_pass2(){
    if ($("#isAuth").val() == "1") {
        MCP.alert("본인 인증을 완료 하였습니다.");
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

      //본인인증 비교
    var authInfoJson = {authType: prarObj.authType, contractNum : $("#realContractNum").val()};
    var isAuthDone = authCallback(authInfoJson);

    if (isAuthDone) { // 본인인증 최종 성공
      pageObj.authObj= prarObj ;
      MCP.alert("본인인증에 성공 하였습니다.");
      $("input[name=onlineAuthType]").prop("disabled",true); // 본인인증 버튼 disabled 처리
      return null;
    } else {

      if(niceAuthObj.resAuthOjb.RESULT_CODE == "STEP01" || niceAuthObj.resAuthOjb.RESULT_CODE == "STEP02"){
        strMsg= niceAuthObj.resAuthOjb.RESULT_MSG;
      }

      MCP.alert(strMsg);
      return null;
    }
}


function targetTermsAgree() {

    $('#' + $('#targetTerms').val()).prop('checked', 'checked');

 }



//쉐어링 가입
function sharing_reg(){
    var cnt = 0;

    if($("#isAuth").val() != "1"){
        MCP.alert("본인 인증을 해주세요.",function(){
            $("#onlineAuthType1").focus();
        });
        return false;
    }

    if($("#iccYn").val() != "1"){
        MCP.alert("유심 유효성 인증을 해주세요.",function(){
            $("#iccId").focus();
        });
        return false;
    }


    $('input[name=terms]').each(function (){
        if($(this).attr('data-mand-yn') == 'Y' && !$(this).is(':checked')){
            MCP.alert('필수 이용약관을 모두 동의하셔야 합니다.', function (num){
                $(this).focus();
                cnt++;
            });

            return false;

        }
    });
    validator.config={};
    //신분증 타입
    validator.config['selfCertType1'] = 'isNonEmpty';
    
    //안면인증 
    if($("#fathTrgYn").val() == "Y") {
        validator.config['isFathTxnRetv'] = 'isNonEmpty';
    }
    validator.config['selfIssuExprDt'] = 'isIssuDate';
    validator.config['selfInqryAgrmYnFlag'] = 'isNonEmpty';
    
    if (validator.validate()) {
        sharingRegSave();
    } else {
        var errId = validator.getErrorId();
        MCP.alert(validator.getErrorMsg() ,function (){
            $selectObj = $("#"+errId);
            viewErrorMgs($selectObj, validator.getErrorMsg());
        });
    }
}

function sharingRegSave(){
    fn_reqSelfChg();
}

function fn_reqSelfChg(){

    var selfCertType = "";
    var selfIssuExprDt = "";
    var selfIssuNum = "";

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

    var confirm = "개통하시겠습니까?";
    var varData = ajaxCommon.getSerializedData({
              contractNum : $("#contractNum").val()
            // , npTlphNo  : $("#opmdSvcNo").val().substring(4,8)
              , reqUsimSn : $("#iccId").val()
              , menuType : $("#menuType").val()
              , clauseFathFlag : $("#clauseFathFlag01").is(":checked") && $("#clauseFathFlag02").is(":checked") ? "Y":"N"
              , fathTrgYn :$("#fathTrgYn").val()
              , selfCertType : selfCertType
              , selfIssuExprDt : selfIssuExprDt
              , selfIssuNum : selfIssuNum
    });

    KTM.Confirm(confirm, function (){
    $('#sharing_reg').addClass('is-disabled');
    KTM.LoadingSpinner.show();

     $.ajax({
        id:'saveDataSharingSimpleAjax'
        ,cache:false
        ,url:'/m/appform/saveDataSharingSimpleAjax.do'
        ,data: varData
        ,dataType:"json"
        , success : function(jsonObj) {
            if(jsonObj.RESULT_CODE == "00000"){
                $("#selfShareSuccesYn").val(jsonObj.REQUEST_KET);
                $("#opmdSvcNo").val(jsonObj.tlphNo.trim());

                sendScanImage();
                sharingYnChg();
                KTM.LoadingSpinner.hide();

            }else{

                var strHtml = "쉐어링 가입 실패했습니다.";

                // STEP 관련 오류
                if(jsonObj.RESULT_CODE == "STEP01" || jsonObj.RESULT_CODE == "STEP02" || jsonObj.RESULT_CODE == "STEP03"){
                    strHtml = jsonObj.RESULT_MSG;
                    $('#sharing_reg').removeClass('is-disabled');
                }

               /* if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_NP1_MSG[jsonObj.OSST_RESULT_CODE] != undefined) {
                    strHtml = SIMPLE_NP1_MSG[jsonObj.OSST_RESULT_CODE];
                } else if (jsonObj.ERROR_NE_MSG != undefined) {
                    strHtml = jsonObj.ERROR_NE_MSG;
                }

                if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_NP1_TYPE[jsonObj.OSST_RESULT_CODE] != undefined) {
                    strHtml = SIMPLE_NP1_TYPE[jsonObj.OSST_RESULT_CODE];
                }

                if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_PC0_MSG[jsonObj.OSST_RESULT_CODE] != undefined) {
                   strHtml = SIMPLE_PC0_MSG[jsonObj.OSST_RESULT_CODE];
                } else if (jsonObj.ERROR_NE_MSG != undefined) {
                   strHtml = jsonObj.ERROR_NE_MSG ;
                }

                 if (jsonObj.OSST_RESULT_CODE != undefined && SIMPLE_OP2_MSG[jsonObj.OSST_RESULT_CODE] != undefined) {
                    strHtml = SIMPLE_OP2_MSG[jsonObj.OSST_RESULT_CODE];
                } else if (jsonObj.RESULT_MSG != undefined) {
                    strHtml = jsonObj.RESULT_MSG;
                }*/

                KTM.LoadingSpinner.hide();

                MCP.alert(strHtml, function(){
                        ajaxCommon.createForm({
                             method:"post"
                            ,action:"/m/content/mySharingView.do"
                        });

                        ajaxCommon.attachHiddenElement("ncn",$("#contractNum").val());
                        ajaxCommon.formSubmit();

                });
            }
        },error:function(){
            alert("쉐어링 가입 실패했습니다.");
            KTM.LoadingSpinner.hide();
            $('#sharing_reg').removeClass('is-disabled');
        }
   });
     this.close();
  });
}
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
                    ,action:"/m/content/reqSharingCompleteView.do"
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
            ,action:"/m/content/mySharingView.do"
    });

    ajaxCommon.formSubmit();
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

//안면인증 연동오류 및 FS9(결과확인) 실패 시
function simpleDialogModal(type) {
    MCP.alert("시스템에 문제가 발생하여 현재페이지에서 진행이 어렵습니다.<br>" +
        "확인을 누르시면 다른 페이지로 이동하여 계속 진행하실 수 있습니다.",function(){
        location.href = "/m/content/dataSharingStep1.do";
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