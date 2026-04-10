;

var interval ;
var clauseScroll;

var msg = {


         "BLANK" : "인자값 오류가 발생 하였습니다."
        , "NUSE" : "현재 사용중인 고객만 신청이 가능합니다."
        , "OVER" : "신청 가능 기간을 초과하였습니다.<br>확인 버튼을 누르실 경우 다이렉트몰 메인으로 이동합니다."
        , "DOUBL" : "이미 신청하신 이력이 있습니다."
        , "ERROR" : "고객조회에 실패했습니다."
        , "NPROM" : "사은품 수령 대상자가 아닙니다."
        , "APPSUCC" : "저장이 완료되었습니다."
        , "APPFAIL" : "저장에 실패하였습니다. 다시 시도 바랍니다."
        , "CERT" : "휴대폰 인증 정보가 없습니다."
        , "END" : "사은품 수령 대상자가 아닙니다."
        , "FAIL" : "고객조회에 실패했습니다."
        , "STEP01" : "인증 정보가 일치하지 않습니다. 이 메시지가 계속되면 처음부터 다시 시도해 주세요."
        , "STEP02" : "필수 단계가 누락됐습니다. 처음부터 다시 시도해 주세요."
}

$(document).ready(function(){

    $("#custNm,#phoneNum,#certifySms").val("");

    // 숫자만 입력가능1
    $(".numOnly").keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });

    // 숫자만 입력가능2
    $(".numOnly").blur(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });


    $("#certBtn").click(function(){

        var chkA1 = $("#chkA1").is(":checked");
        if(!chkA1){
            MCP.alert("개인정보 수집/이용에 동의하여 주시기 바랍니다.",function(){
                $("#chkA1").focus();
            });
            return false;
        }

        var custNm = $("#custNm").val();
        if(!custNm){
            MCP.alert("고객명을 입력해 주세요.",function(){
                $("#custNm").focus();
            });
            return false;
        }

        var cstmrNativeRrn01 = $("#cstmrNativeRrn01").val();
        var cstmrNativeRrn02 = $("#cstmrNativeRrn02").val();
        if(cstmrNativeRrn01=="" || cstmrNativeRrn01.length < 6){
            MCP.alert("주민등록번호 앞자리를 입력해 주세요.",function(){
                $("#cstmrNativeRrn01").focus();
            });
            return false;
        }
        if(cstmrNativeRrn02=="" || cstmrNativeRrn02.length < 7){
            MCP.alert("주민등록번호 뒷자리를 입력해 주세요.",function(){
                $("#cstmrNativeRrn02").focus();
            });
            return false;
        }

        var certifyYn = $("#certify").val();
        if(certifyYn!="Y"){
            MCP.alert("휴대폰인증을 진행해 주세요.",function(){
                $("#custNm").focus();
            });
            return false;
        }

        var addressYn = $("#addressYn").val();
        if(addressYn == 'Y'){
            var cstmrPost = ajaxCommon.isNullNvl($("#cstmrPost").val(),"");
            var cstmrAddr = ajaxCommon.isNullNvl($("#cstmrAddr").val(),"");
            var cstmrAddrDtl = ajaxCommon.isNullNvl($("#cstmrAddrDtl").val(),"");
            if(cstmrPost=="" || cstmrAddr==""){
                MCP.alert("주소를 입력해주세요.");
                return false;
            }

            if(cstmrAddrDtl==""){
                MCP.alert("상세주소를 입력해주세요.");
                return false;
            }
        }

        var custNm = ajaxCommon.isNullNvl($("#custNm").val(),"");
        var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
        var taxNo = $("#taxNo").val();

        var varData = ajaxCommon.getSerializedData({
             userNm:$.trim(custNm)
            ,telNO:$.trim(phoneNum)
            ,taxNo : Number(taxNo)
        });

        ajaxCommon.getItem({
            id:'consentSmsAjax'
            ,cache:false
            ,url:'/m/gift/consentSmsAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(data){

            if( data.resCode == "SUCCESS" ){
                saveReplyDate();

            } else{
                MCP.alert(msg[data.resCode]);
                return false;
            }

        });
    });

    if($("#diffInDays").val() == 'Y'){
       setTimeout(function() {
           MCP.alert("신청 가능 기간을 초과하였습니다.<br>확인 버튼을 누르실 경우 다이렉트몰 메인으로 이동합니다.",function(){
           location.href = "/m/main.do";
        });
        return false;
        }, 1000);
    }

});

function agreeClose(){
   $("#chkA1").prop("checked", true);
}

function saveReplyDate() {

    var userName = $("#custNm").val();
    var phoneNum = $("#phoneNum").val();
    var taxNo = $("#taxNo").val();
    var cstmrPost = ajaxCommon.isNullNvl($("#cstmrPost").val(),"");
    var cstmrAddr = ajaxCommon.isNullNvl($("#cstmrAddr").val(),"");
    var cstmrAddrDtl = ajaxCommon.isNullNvl($("#cstmrAddrDtl").val(),"");

    var varData = ajaxCommon.getSerializedData({
          userNm : $.trim(userName)
        , telNO : $.trim(phoneNum)
        , taxAdrZip : cstmrPost
        , taxAdrPrimaryLn : cstmrAddr
        , taxAdrSecondaryLn : cstmrAddrDtl
        , taxNo : Number(taxNo)
    });

    ajaxCommon.getItem({
        id:'consentSaveAjax'
        ,cache:false
        ,url:'/m/gift/consentSaveAjax.do'
        ,data: varData
        ,dataType:"json"
    }
    ,function(data){
        if( data.resCode =="APPSUCC" ){
            MCP.alert("저장이 완료되었습니다.",function(){
            location.reload();
            return false;
             }, 1000);
        } else if(data.resCode == "STEP01" || data.resCode == "STEP02"){ // STEP 오류
            MCP.alert(msg[data.resCode]);
            return false;
        } else{
            MCP.alert("저장에 실패하였습니다. 다시 시도 바랍니다.");
            return false;
        }
    });
}

function btnRegDtl(param){
    openPage('termsPop','/termsPop.do',param);
}

function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

/* 주소 setting */
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
    $("#cstmrPost").val(zipNo);
    $("#cstmrAddr").val(roadAddrPart1);
    $("#cstmrAddrDtl").val(roadAddrPart2 + " " +addrDetail);
}
