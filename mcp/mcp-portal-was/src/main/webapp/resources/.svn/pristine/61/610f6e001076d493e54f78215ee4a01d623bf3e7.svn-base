

    if ( typeof VALIDATE_NOT_EMPTY_MSG == "undefined" )  {
        VALIDATE_NOT_EMPTY_MSG = {};
    }

    if ( typeof VALIDATE_COMPARE_MSG == "undefined" )  {
        VALIDATE_COMPARE_MSG = {};
    }


    if ( typeof VALIDATE_FIX_MSG == "undefined" )  {
        VALIDATE_FIX_MSG = {};
    }

    if ( typeof VALIDATE_NUMBER_MSG == "undefined" )  {
        VALIDATE_NUMBER_MSG = {};
    }

    VALIDATE_COMPARE_MSG.mobileNo = "휴대폰 형식이 일치 하지 않습니다. ";
    VALIDATE_COMPARE_MSG.birthDate = "생년월일  형식이 일치 하지 않습니다. ";

    VALIDATE_FIX_MSG.birthDate= "8자리 숫자만 입력 (19701225) ";
    VALIDATE_NUMBER_MSG.birthDate= "8자리 숫자만 입력 (19701225) ";

    var openPageObj = {
       niceResLogObj:{}   //로그을 저장 하기 위한 인증
       ,startTime:0
       ,niceHistSeq:0
    }





$(document).ready(function(){
    // 인풋 포커스 활성화 시 레이블 애니메이션 처리


    $("#customerNm,#mobileNo,#birthDate,#mobileModelNm").on("keyup", function(){
        isValidateNonEmpty();
    });

    $("#mobileCarrierCd ,#mobileCompanyCd ,#chkDS").on("change", function(){
        isValidateNonEmpty();
    });


    $("#retentionConfirm").on("click", function(){
        validator.config={};
        validator.config['mobileNo'] = 'isPhone';
        validator.config['birthDate'] = 'isDate';
        if (validator.validate()) {

            var inpDate = new Date($("#expiryDate").val().replace(/-/g, "/"));
            var minDate = new Date(nextDay.replace(/\./g, "/"));
            var maxDate = new Date(threeMonthLaterDate.replace(/\./g, "/"));

            if(inpDate < minDate){
                MCP.alert("오늘 이후 날짜로 입력해 주십시오.", function(){
                        $("#expiryDate").focus();
                });
                return;
            }

            if(inpDate > maxDate){
                MCP.alert("3개월 이내 날짜로 입력해 주십시오.", function(){
                        $("#expiryDate").focus();
                });
                return;
            }

            ajaxCommon.getItem({
                id:'getTimeInMillisAjax'
                ,cache:false
                ,url:'/nice/getTimeInMillisAjax.do'
                ,data: ""
                ,dataType:"json"
            }
            ,function(startTime){
                openPageObj.startTime = startTime ;
            });

            openPage('outLink','/nice/popNiceAuth.do?sAuthType=M&mType=Mobile&returnUrl=/nice/popNiceSuccOpenLayer.do','')
            return ;
        } else {
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg() ,function (){
                //console.log("dddd=>" + errId);
                $selectObj = $("#"+errId);
                viewErrorMgs($selectObj, validator.getErrorMsg());
            });
        }
    });



    var isValidateNonEmpty = function() {
        validator.config={};
        validator.config['customerNm'] = 'isNonEmpty';
        validator.config['mobileNo'] = 'isNonEmpty';
        validator.config['birthDate'] = 'isNonEmpty';
        validator.config['mobileCarrierCd'] = 'isNonEmpty';
        validator.config['mobileCompanyCd'] = 'isNonEmpty';
        validator.config['mobileModelNm'] = 'isNonEmpty';
        validator.config['chkDS'] = 'isNonEmpty';

        if (validator.validate(true)) {
            $('#retentionConfirm').removeClass('is-disabled');
            $('#retentionConfirm').prop("disabled",false);
        } else {
            $('#retentionConfirm').addClass('is-disabled');
            $('#retentionConfirm').prop("disabled",true);
        }
    } ;


});

    var fnNiceCertOpenLayer = function(prarObj) {

        var customerNm = ajaxCommon.isNullNvl($.trim($("#customerNm").val()),"");
        var birthDate = ajaxCommon.isNullNvl($.trim($("#birthDate").val()),"");

        if(birthDate.length > 6) birthDate= birthDate.substring(2, 8);

        openPageObj.niceResLogObj = prarObj;
        var varData = ajaxCommon.getSerializedData({
            cstmrName:customerNm
            , cstmrNativeRrn:birthDate
            , reqSeq:openPageObj.niceResLogObj.reqSeq
            , resSeq:openPageObj.niceResLogObj.resSeq
            , paramR3:"openAuth"
            , startTime:openPageObj.startTime
        });

        ajaxCommon.getItemNoLoading({
            id:'getContractAuth'
            ,cache:false
            ,async:false
            ,url:'/auth/getReqAuthAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            if (jsonObj.RESULT_CODE == "00000") {
                openPageObj.niceHistSeq = jsonObj.niceHistSeq ;
                setTimeout(function(){
                    var mobileNo = $.trim($("#mobileNo").val());
                    var expiryDate = $("#expiryDate").val().trim().replace(/-/gi, "");
                    var birthDate = $.trim($("#birthDate").val());
                    var mobileCarrierCd = $("#mobileCarrierCd").val() ;
                    var mobileCompanyCd = $("#mobileCompanyCd").val();
                    var mobileModelNm = $.trim($("#mobileModelNm").val());


                    var varData = ajaxCommon.getSerializedData({
                          customerNm : $.trim($("#customerNm").val())
                        , birthDate : birthDate
                        , mobileNo : mobileNo
                        , expiryDate : expiryDate
                        , mobileCarrierCd : mobileCarrierCd
                        , mobileCompanyCd : mobileCompanyCd
                        , mobileModelNm :  mobileModelNm
                    });
                    ajaxCommon.getItemNoLoading({
                            id:'retentionNoticeAjax'
                            ,cache:false
                            ,url:'/retention/retentionNoticeAjax.do'
                            ,data: varData
                            ,async:false
                            ,dataType:"json"
                        }
                        ,function(data){
                            if(data.resultCd == '00000'){
                                MCP.alert('약정만료 알림받기 신청이 완료되었습니다.');
                                $("#retentionCloseBtn").trigger('click');
                            }else{
                                MCP.alert(data.msg);
                            }
                        });
                }, 500);
            } else {
                MCP.alert("고객 정보와 본인인증한 정보가 틀립니다.");
                return null;
            }
        });

    };





