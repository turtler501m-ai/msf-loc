;

VALIDATE_NOT_EMPTY_MSG = {}
VALIDATE_NOT_EMPTY_MSG.imei1 = "IMEI 번호를 입력해 주세요.<br> 또는 관련 이미지 업로드 해 주세요.";
VALIDATE_NOT_EMPTY_MSG.eid = "EID 번호를 입력해 주세요.<br> 또는 관련 이미지 업로드 해 주세요.";
VALIDATE_NOT_EMPTY_MSG.imeiTxt = "IMEI 번호를 입력해 주세요.<br> 또는 관련 이미지 업로드 해 주세요.";
VALIDATE_NOT_EMPTY_MSG.eidTxt = "EID 번호를 입력해 주세요.<br> 또는 관련 이미지 업로드 해 주세요.";

VALIDATE_NOT_EMPTY_MSG.cstmrName = "성명 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrNativeRrn01 = "주민등록번호 앞자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrNativeRrn02 = "주민등록번호 뒷자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrMobile = "고객 휴대전화 입력 하여 주시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.cstmrMobileFn = "고객 휴대전화 앞자리를 입력해 주세요. ";
VALIDATE_NOT_EMPTY_MSG.cstmrMobileMn = "고객 휴대전화 중간자리를 입력해 주세요. ";
VALIDATE_NOT_EMPTY_MSG.cstmrMobileRn = "고객 휴대전화 뒤자리를 입력해 주세요. ";

VALIDATE_NUMBER_MSG ={};
VALIDATE_NUMBER_MSG.imei1="IMEI  숫자 15자리 입력해 주세요.";
VALIDATE_NUMBER_MSG.eid = "EID 숫자 32자리 입력해 주세요.  "
VALIDATE_NUMBER_MSG.imeiTxt="IMEI  숫자 15자리 입력해 주세요.";
VALIDATE_NUMBER_MSG.eidTxt = "EID 숫자 32자리 입력해 주세요.  "
VALIDATE_NUMBER_MSG.cstmrMobile="고객 휴대전화 번호 숫자로 입력해 주세요.";
VALIDATE_NUMBER_MSG.cstmrMobileFn="고객 휴대전화 번호 앞자리를 숫자로 입력해 주세요.";
VALIDATE_NUMBER_MSG.cstmrMobileMn="고객 휴대전화 번호 중간자리를 숫자로 입력해 주세요.";
VALIDATE_NUMBER_MSG.cstmrMobileRn = "고객 휴대전화 뒤자리를 숫자로 입력해 주세요. ";


VALIDATE_FIX_MSG={};
VALIDATE_FIX_MSG.imei1 = "IMEI 숫자 15자리 입력해 주세요.";
VALIDATE_FIX_MSG.eid = "EID 숫자 32자리 입력해 주세요. ";
VALIDATE_FIX_MSG.imeiTxt = "IMEI 숫자 15자리 입력해 주세요.";
VALIDATE_FIX_MSG.eidTxt = "EID 숫자 32자리 입력해 주세요. ";
VALIDATE_FIX_MSG.cstmrNativeRrn = "주민등록번호 숫자 13자리  입력 하여 주시기 바랍니다.";
VALIDATE_FIX_MSG.cstmrMobileFn = "고객 휴대전화 앞자리를 숫자로 입력해 주세요. ";
VALIDATE_FIX_MSG.cstmrMobileMn="고객 휴대전화 번호 중간자리를  숫자로  입력해 주세요.";
VALIDATE_FIX_MSG.cstmrMobileRn="고객 휴대전화 번호 뒷자리를 숫자로 입력해 주세요.";
VALIDATE_FIX_MSG.cstmrMobileRn = "고객 휴대전화 뒤자리를 숫자로 입력해 주세요. ";


VALIDATE_COMPARE_MSG = {};
VALIDATE_COMPARE_MSG.cstmrNativeRrn01 = "주민등록번호 형식이 일치 하지 않습니다.";
VALIDATE_COMPARE_MSG.cstmrMobileFn = "고객 휴대전화 형식이 일치 하지 않습니다. ";

//type:1 alert
//type:2 상담사 전환
ESIM_WATCH_RESULT_OBJ={};
ESIM_WATCH_RESULT_OBJ.CO_1000E1 = {type:1,msg:"사용이 불가한 기기입니다."};
ESIM_WATCH_RESULT_OBJ.CO_1000E11 = {type:1,msg:"현재 워치가 사용중 상태로 개통이 불가합니다."};
ESIM_WATCH_RESULT_OBJ.CO_1000E12 = {type:1,msg:"사용이 불가한 기기입니다."};
ESIM_WATCH_RESULT_OBJ.CO_1000E2 = {type:1,msg:"등록된 EID값이 기기정보와 불일치합니다.<br/>고객센터(1899-5000)를 통해 EID 변경 후 시도 바랍니다."};
ESIM_WATCH_RESULT_OBJ.CO_1000E3 = {type:1,msg:"등록된 EID값이 기기정보와 불일치합니다.<br/>고객센터(1899-5000)를 통해 EID 변경 후 시도 바랍니다."};
ESIM_WATCH_RESULT_OBJ.CO_1000E4 = {type:1,msg:"서비스 오류 발생 하였습니다. 잠시이후 시도 하시기 바랍니다."};

ESIM_WATCH_RESULT_OBJ.CO_2000E2 = {type:2,msg:"기기 등록이 실패하였습니다. <br/> 상담사 개통 신청으로만 진행이 가능합니다. <br/><br/>상담사 신청으로 이동하시겠습니까?"};
ESIM_WATCH_RESULT_OBJ.CO_2000E3 = {type:2,msg:"기기 등록이 실패하였습니다. <br/> 상담사 개통 신청으로만 진행이 가능합니다. <br/><br/>상담사 신청으로 이동하시겠습니까?"};
ESIM_WATCH_RESULT_OBJ.CO_2000E1 = {type:2,msg:"기기 등록이 실패하였습니다. <br/> 상담사 개통 신청으로만 진행이 가능합니다. <br/><br/>상담사 신청으로 이동하시겠습니까?"};
ESIM_WATCH_RESULT_OBJ.CO_2000E4 = {type:2,msg:"기기 등록이 실패하였습니다. <br/> 상담사 개통 신청으로만 진행이 가능합니다. <br/><br/>상담사 신청으로 이동하시겠습니까?"};


ESIM_WATCH_RESULT_OBJ.CO_5000E1 = {type:2,msg:"기기 등록이 실패하였습니다. <br/> 상담사 개통 신청으로만 진행이 가능합니다. <br/><br/>상담사 신청으로 이동하시겠습니까?"};
ESIM_WATCH_RESULT_OBJ.CO_5000E2 = {type:2,msg:"기기 등록이 실패하였습니다. <br/> 상담사 개통 신청으로만 진행이 가능합니다. <br/><br/>상담사 신청으로 이동하시겠습니까?"};
ESIM_WATCH_RESULT_OBJ.CO_5000E3 = {type:2,msg:"기기 등록이 실패하였습니다. <br/> 상담사 개통 신청으로만 진행이 가능합니다. <br/><br/>상담사 신청으로 이동하시겠습니까?"};
ESIM_WATCH_RESULT_OBJ.CO_5000E4 = {type:2,msg:"기기 등록이 실패하였습니다. <br/> 상담사 개통 신청으로만 진행이 가능합니다. <br/><br/>상담사 신청으로 이동하시겠습니까?"};
ESIM_WATCH_RESULT_OBJ.CO_5000E5 = {type:2,msg:"기기 등록이 실패하였습니다. <br/> 상담사 개통 신청으로만 진행이 가능합니다. <br/><br/>상담사 신청으로 이동하시겠습니까?"};
ESIM_WATCH_RESULT_OBJ.CO_9999E1 = {type:2,msg:"미성년자/외국인은 셀프개통이 불가합니다. <br/><br/>상담사 신청으로 이동하시겠습니까?"};
ESIM_WATCH_RESULT_OBJ.CO_9999E2 = {type:2,msg:"납부방법이 신용카드/은행계좌 자동이체인 경우에만 셀프개통이 가능합니다. <br/><br/>상담사 신청으로 이동하시겠습니까?"};

ESIM_WATCH_RESULT_OBJ.CO_1000 = {type:3,msg:"성공"};
ESIM_WATCH_RESULT_OBJ.CO_2000 = {type:3,msg:"성공"};
ESIM_WATCH_RESULT_OBJ.CO_5000 = {type:3,msg:"성공"};


var pageObj = {
    contractNum:""
}

$(document).ready(function(){

    $("#btnAuthCstmr").click(function(){
        validator.config={};
        validator.config['cstmrName'] = 'isNonEmpty';
        validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJiminAndFngNo';
        validator.config['cstmrMobileFn'] = 'isNumFix3';
        validator.config['cstmrMobileMn'] = 'isNumBetterFixN3';
        validator.config['cstmrMobileRn'] = 'isNumFix4';
        validator.config['cstmrMobileFn&cstmrMobileMn&cstmrMobileRn'] = 'isPhoneNumberCheck';

        if (validator.validate()) {
            var varData = ajaxCommon.getSerializedData({
                cstmrName : $.trim($("#cstmrName").val())
                ,cstmrMobile : $.trim($("#cstmrMobileFn").val())+ $.trim($("#cstmrMobileMn").val())+ $.trim($("#cstmrMobileRn").val())
                ,cstmrNativeRrn01 : $.trim($("#cstmrNativeRrn01").val())+ $.trim($("#cstmrNativeRrn02").val())
            });

            KTM.LoadingSpinner.show();

            ajaxCommon.getItemNoLoading({
                id:'getContractInfoAjax'
                ,cache:false
                ,url:'/appForm/getContractInfoAjax.do'
                ,data: varData
                ,dataType:"json"
                ,async:false
                ,errorCall : function () {
                    KTM.LoadingSpinner.hide(true);
                }
            }
            ,function(jsonObj){
                KTM.LoadingSpinner.hide(true);
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE ) {
                    MCP.alert("고객 인증이 완료되었습니다.");
                    pageObj.contractNum = jsonObj.contractNum;

                    $("#divStep1").hide();
                    $("#divStep2").show();
                } else {
                    if ("0001" == jsonObj.RESULT_CODE) {
                        MCP.alert("입력하신 정보는 kt M모바일에 가입된 정보가 아니거나 사용중인 상태가 아닙니다.<br/><br/>확인 후 다시 입력 해 주세요.");
                    } else if ("0002" == jsonObj.RESULT_CODE) {
                        MCP.alert("입력하신 정보는 kt M모바일에 가입된 정보가 입니다. <br/>주민번호가 상이 합니다.<br/><br/>확인 후 다시 입력 해 주세요.");
                    } else if ("0010" == jsonObj.RESULT_CODE) { // 인가된 사용자 검증
                        MCP.alert('<p class="u-mt--12"><strong class="u-fw--bold">회원정보와 본인인증 정보가<br>일치하지 않습니다.</strong></p><p class="u-mt--12">다른 명의로 신청을 원하실 경우 로그아웃 후 이용 해 주시기 바랍니다.</p>');
                    } else {
                        MCP.alert("처리중 오류가 발생했습니다.<br/><br/>잠시후 다시 시도해 주세요.");
                    }
                }
            });
        } else {
            var errId = validator.getErrorId();
            //console.log("isValidateStep3 errId==>" + errId);
            MCP.alert(validator.getErrorMsg());
            return false;
        }
    });

    // 다음
    $("#nextStep").click(function(){
        validator.config={};
        validator.config['imei1'] = 'isNumFix15';
        validator.config['eid'] = 'isNumFix32';

        if (validator.validate()) {

            var varData = ajaxCommon.getSerializedData({
                imei1 : $.trim($("#imei1").val())
                ,eid : $.trim($("#eid").val())
                ,contractNum : pageObj.contractNum
            });

            KTM.LoadingSpinner.show();
            ajaxCommon.getItemNoLoading({
                id:'eSimChkAjax'
                ,cache:false
                ,url:'/appForm/eSimWatchAjax.do'
                ,data: varData
                ,dataType:"json"
                ,async:true
                ,errorCall : function () {
                    KTM.LoadingSpinner.hide(true);
                }
            }
            ,function(jsonObj){
                KTM.LoadingSpinner.hide(true);
                if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE ) {
                    var resDto = jsonObj.resDto;
                    var uploadPhoneSrlNo = resDto.uploadPhoneSrlNo;

                    var rtnwWatchObj = ESIM_WATCH_RESULT_OBJ["CO_"+resDto.resultCode];
                    if ( 1== rtnwWatchObj.type ) {
                        MCP.alert(rtnwWatchObj.msg);
                    } else if ( 2==rtnwWatchObj.type ) {
                        KTM.Confirm(rtnwWatchObj.msg
                            , function () {
                                location.href="/appForm/appFormDesignView.do?prdtIndCd=11&uploadPhoneSrlNo="+uploadPhoneSrlNo;
                                return ;
                            },function() {
                                return ;
                            });
                    } else if ( 3==rtnwWatchObj.type ) {
                        location.href="/appForm/appFormDesignView.do?prdtIndCd=11&uploadPhoneSrlNo="+uploadPhoneSrlNo;
                        return ;
                    }
                } else {
                    if (jsonObj.RESULT_MSG == null) {
                        MCP.alert("시스템에 문제가 발생하였습니다.<br/> 잠시후에  다시 진행 부탁드립니다.");
                    }  else {
                        MCP.alert(jsonObj.RESULT_MSG);
                    }
                }

            });
        } else {
            var errId = validator.getErrorId();
            //console.log("isValidateStep3 errId==>" + errId);
            MCP.alert(validator.getErrorMsg());
            return false;
        }
    });


    $("#btnConfirm").click(function(){
        validator.config={};
        validator.config['imeiTxt'] = 'isNumFix15';
        validator.config['eidTxt'] = 'isNumFix32';

        if (validator.validate()) {
            var eid =$("#eidTxt").val();
            var imei1 =$("#imeiTxt").val();

            $("#eid").val(eid).parent().addClass("has-value");
            $("#imei1").val(imei1).parent().addClass("has-value");
            KTM.Dialog.closeAll();
        } else {
            var errId = validator.getErrorId();
            //console.log("isValidateStep3 errId==>" + errId);
            MCP.alert(validator.getErrorMsg());
            return false;
        }
    });


    $("#btnRegEsim").click(function(){
        $("#reSetBtn").trigger("click");
    });




    $(".ocrImg").change(function(){
        //$("#nextStep").prop("disabled",false);
        var image = $(this).val();
        if(image ==""){
            alert("이미지 파일을 선택해 주세요.");
            return false;
        }

        var ext = image.split(".").pop().toLowerCase();
        if($.inArray(ext, ["jpg", "jpeg", "png", "tif","tiff", "bmp"]) == -1) {
            MCP.alert("첨부파일은 이미지 파일만 등록 가능합니다.");
            $(".ocrImg").val("");
            return false;
        }

        var pattern =  /[\{\}\/?,;:|*~`!^\+<>@\#$%&\\\=\'\"]/gi;
        var fileName = image.split('\\').pop().toLowerCase();
        if(pattern.test(fileName) ){
            MCP.alert('파일명에 특수문자가 포함되어 있습니다.');
            $(".ocrImg").val("");
            return false;
        }

        var formData = new FormData();
        formData.append("image", $(".ocrImg")[0].files[0]);

        KTM.LoadingSpinner.show();
        ajaxCommon.getItemFileUp({
            id : 'getOcrImgReadyAjax'
            , cache : false
            , async : false
            , url : '/getOcrImgReadyAjax.do'
            , data : formData
            , dataType : "json"
        }
        ,function(result){
            KTM.LoadingSpinner.hide();
            var retCode = result.retCode;
            var retMsg = result.retMsg;
            var uploadPhoneSrlNo = result.uploadPhoneSrlNo;
            var eid = "";
            var imei1 = "";
            if(retCode != '0000'){
                $("#errTxt").html(retMsg).show();
            } else {
                $("#errTxt").html("").hide();
                eid = result.eid;
                imei1 = result.imei1;

                if (eid != null && eid != "") {
                    $("#eid").val(eid).parent().addClass("has-value");
                }

                if (imei1 != null && imei1 != "") {
                    $("#imei1").val(imei1).parent().addClass("has-value");
                }
            }
        });
    });



    /*$("#divStep1").hide();
    $("#divStep2").show();*/

});


function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}





