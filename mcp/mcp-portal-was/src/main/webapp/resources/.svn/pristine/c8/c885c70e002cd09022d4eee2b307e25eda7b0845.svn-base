
;

VALIDATE_NOT_EMPTY_MSG = {};
VALIDATE_NOT_EMPTY_MSG.cstmrName = "성명 입력하세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrNativeRrn01 = "주민등록번호 앞자리를 입력하세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrNativeRrn02 = "주민등록번호 뒷자리를 입력하세요.";
VALIDATE_NOT_EMPTY_MSG.isAuth = "본인인증 하여 주시기 바랍니다.";
VALIDATE_NOT_EMPTY_MSG.reqUsimSn01 = "유심번호 첫번째 4자리를 입력하세요.";
VALIDATE_NOT_EMPTY_MSG.reqUsimSn02 = "유심번호 두번째 4자리를 입력하세요.";
VALIDATE_NOT_EMPTY_MSG.reqUsimSn03 = "유심번호 세번째 4자리를 입력하세요.";
VALIDATE_NOT_EMPTY_MSG.reqUsimSn04 = "유심번호 네번째 4자리를 입력하세요.";
VALIDATE_NOT_EMPTY_MSG.reqUsimSn05 = "유심번호 다섯번째 3자리를 입력하세요.";

VALIDATE_NUMBER_MSG = {};
VALIDATE_NUMBER_MSG.reqUsimSn01 = "유심번호 첫번째 4자리를 입력하세요.";
VALIDATE_NUMBER_MSG.reqUsimSn02 = "유심번호 두번째 4자리를 입력하세요.";
VALIDATE_NUMBER_MSG.reqUsimSn03 = "유심번호 세번째 4자리를 입력하세요.";
VALIDATE_NUMBER_MSG.reqUsimSn04 = "유심번호 네번째 4자리를 입력하세요.";
VALIDATE_NUMBER_MSG.reqUsimSn05 = "유심번호 다섯번째 3자리를 입력하세요.";

VALIDATE_FIX_MSG = {};
VALIDATE_FIX_MSG.reqUsimSn01 = "유심번호 첫번째 4자리를 입력하세요.";
VALIDATE_FIX_MSG.reqUsimSn02 = "유심번호 두번째 4자리를 입력하세요.";
VALIDATE_FIX_MSG.reqUsimSn03 = "유심번호 세번째 4자리를 입력하세요.";
VALIDATE_FIX_MSG.reqUsimSn04 = "유심번호 네번째 4자리를 입력하세요.";
VALIDATE_FIX_MSG.reqUsimSn05 = "유심번호 다섯번째 3자리를 입력하세요.";
VALIDATE_FIX_MSG.reqUsimSn = "유심 일련번호 19자리 입력하세요.";




var pageObj = {
    niceType:""
    , nowStep:1
    , cid :""
    , authObj:{}
    , cstmrType: ""
    , niceResLogObj:{}
}



$(document).ready(function(){

    // 본인인증 전 유효성 검사
    simpleAuthvalidation = function(){

        validator.config = {};
        validator.config['cstmrName'] = 'isNonEmpty';

        var idNum = $.trim($("#cstmrNativeRrn02").val()).substring(0,1);
        if (idNum == '5' || idNum == '6' || idNum == '7' || idNum == '8') {  //외국인
            validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isFngno';
            pageObj.cstmrType = "FN";
        } else {
            var age = fn_getAge($.trim($("#cstmrNativeRrn01").val()),idNum);
            if (age < 19) { // 청소년
                validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isTeen';
                pageObj.cstmrType = "NM";
            } else { // 내국인
                validator.config['cstmrNativeRrn01&cstmrNativeRrn02'] = 'isJimin';
                pageObj.cstmrType = "NA";
            }
        }

        if (validator.validate()) { // 유효성검사 통과 시
            return true;
        }else{
            // 유효성 검사 불충족시 팝업 표출
            var errId = validator.getErrorId();
            MCP.alert(validator.getErrorMsg(),function (){
                $selectObj = $("#"+errId);
                viewAuthErrorMgs($selectObj, validator.getErrorMsg());
                $('#' + validator.id).focus(); // 포커스
            });
            return false;
        }
    }


    $("#btnNext").click(function(){
        if(1==pageObj.nowStep) {
            getFormDlveyList()
            //$(this).html("완료");
        } else if(2==pageObj.nowStep) {

            validator.config={};
            validator.config['reqUsimSn'] = 'isNumFix19';

            $(".c-form__text").remove();
            $(".has-error").removeClass("has-error");

            if (validator.validate()) {
               var varData = ajaxCommon.getSerializedData({
                   iccId:$.trim($("#reqUsimSn").val())
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
                      // 유심번호 유효성 검사 성공 시, disabled 처리
                      $('#reqUsimSn').prop('disabled', true);
                      updateFormDlveyUsim();
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
                    $selectObj = $("#"+errId);
                    viewErrorMgs($selectObj, validator.getErrorMsg());
                });
           }
        }
     });


    var updateFormDlveyUsim = function() {
        var cstmrName = $.trim($("#cstmrName").val());
        var cstmrBirthDate = $.trim($("#cstmrNativeRrn01").val());

        var varData = ajaxCommon.getSerializedData({
            cstmrName:cstmrName
            ,birthDate:cstmrBirthDate
            ,requestKey:$("#selResNo").val()
            ,reqUsimSn:$.trim($("#reqUsimSn").val())
            ,dlvryType :$("#selResNo option:selected").data("dlvry-type")
            ,cntpntShopId : $("#cntpntShopId").val()
        });

        ajaxCommon.getItem({
            id:'updateFormDlveyUsim'
            ,cache:false
            ,url:'/appform/updateFormDlveyUsimAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }
        ,function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                $("#step2").hide();
                $("#step3").show();
                pageObj.nowStep = 3;
                $("#divButtonStep").hide();
            }else if(jsonObj.RESULT_CODE == "AUTH01" || jsonObj.RESULT_CODE == "AUTH02"){ // AUTH 오류
                MCP.alert(jsonObj.RESULT_MSG);
            }else if(jsonObj.RESULT_CODE == "STEP01" || jsonObj.RESULT_CODE == "STEP02"){ // STEP 오류
                MCP.alert(jsonObj.RESULT_MSG);
            }else {
                MCP.alert("실패 하였습니다.");
            }
        });
     };

     var getFormDlveyList = function() {
         var cstmrName = $.trim($("#cstmrName").val());
         var cstmrBirthDate = $.trim($("#cstmrNativeRrn01").val());

         var varData = ajaxCommon.getSerializedData({
             cstmrName:cstmrName
             ,birthDate:cstmrBirthDate
             ,resNo : $("#resNo").val()
             ,cntpntShopId : $("#cntpntShopId").val()
         });

         ajaxCommon.getItem({
             id:'getUsimNoDlveyList'
             ,cache:false
             ,url:'/appform/getFormDlveyListAjax.do'
             ,data: varData
             ,dataType:"json"
             ,async:false
         }
         ,function(jsonObj){
             if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                 var $cList  = jsonObj.RESULT_OBJ ;
                 $("#selResNo option").remove();
                 $.each($cList,function(idx,data){
                     $("#selResNo").append("<option value="+data.requestKey+" data-dlvry-type="+data.dlvryType+">"+data.resNo+"</option>");
                 }) ;

                 pageObj.nowStep = 2;
                 $("#step1").hide();
                 $("#step2").show();
                 $("#btnNext").html("완료");
                 $("#spIndicator").css("width", "67%");
             } else {
                 $("#step1").hide();
                 $("#step4").show();
                 $("#divButtonStep").hide();
                 $("#spIndicator").css("width", "100%");

                 //제휴위탁온라인
                 if(jsonObj.RESULT_CODE == "002" || jsonObj.RESULT_CODE == "003"){
                     $("#endMsg").html(jsonObj.RESULT_MSG);
                 }
             }
         });
     }

    var viewErrorMgs = function($thatObj, msg ) {
        if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
            $thatObj.parent().addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
        } else if ($thatObj.hasClass("c-input--div2") ) {
            $thatObj.parent().parent().addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
        }
    };

   // $("#step1").hide();
    //$("#step4").show();


    //TEST 위한
    //step4Show();
 });

var fnNiceCert = function(prarObj) {
    var cstmrNativeRrn ;
    var cstmrName ;

    var strMsg= "고객 정보와 본인인증한 정보가 틀립니다." ;
    pageObj.niceResLogObj = prarObj;

    //본인인증 (해당 페이지에서 휴대폰인증도 CUST_AUTH로 분류되고 있음.. 그대로 유지)
    if (pageObj.niceType == NICE_TYPE.CUST_AUTH) {

        cstmrNativeRrn = $.trim($("#cstmrNativeRrn01").val());
        cstmrName = $.trim($("#cstmrName").val());

        var authInfoJson= {cstmrName: cstmrName , cstmrNativeRrn: cstmrNativeRrn, authType: prarObj.authType};
        var isAuthDone= authCallback(authInfoJson);

        if (isAuthDone) {
            pageObj.niceHistSeq = niceAuthObj.resAuthOjb.niceHistSeq;
            pageObj.authObj= prarObj;

            $('#cstmrName').prop('readonly', 'readonly');
            $('#cstmrNativeRrn01').prop('readonly', 'readonly');
            $('#cstmrNativeRrn02').prop('readonly', 'readonly');
            $("#isAuth").val("1");
            $("#divButtonStep").show();

            MCP.alert("본인인증에 성공 하였습니다.");
            return null;
        }else{
            MCP.alert(strMsg);
            return null;
        }
    }
}



function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}