
$(document).ready(function (){

    $(".onlyKorEng").on("blur keyup", function() {
        $(this).val( $(this).val().replace( /[^(ㄱ-ㅎ가-힣a-zA-Z)]/, '' ) );
    });

    $(".notKor").on("blur keyup", function() {
        $(this).val( $(this).val().replace(/[\ㄱ-ㅎㅏ-ㅣ가-힣]/g, '' ) );
    });

    $(".numOnly").keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });

    // 문의 내용 글자막기
    $("#jehuContent").keyup(function(){
        var content = $(this).val();
        if(content.length > 800) {
            alert("글자수는 800자로 이내로 제한됩니다.");
            $(this).val($(this).val().substring(0, 800));
        }
        $("#textAreaSbstSize").text($(this).val().length + '/800자');
    });

    $("#jehuSuggBtn").click(function(){
        var obj = valueChk();
        var result = obj.result;
        var varData = obj.varData;
        if(result){

            ajaxCommon.getItem({
                id:'insertJehuSuggAjax'
                ,cache:false
                ,url:'/jehuSugg/insertJehuSuggAjax.do'
                ,data: varData
                ,dataType:"json"
                ,async:false
            }
            ,function(jsonObj){

                if(jsonObj.returnCode == "00") {
                       MCP.alert("제휴제안 내용 등록이 완료되었습니다.",function(){
                           $('#closePop').trigger('click');
                       });
                   } else {
                       MCP.alert("제휴제안 등록에 실패하였습니다. 다시 등록해주세요");
                       return false;
                   }
            });
        }
    });


    $("#jehuMobileNo").keyup(function(){
        var val = $(this).val();
        var len = val.length;
        if(len > 10){
            var regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
            if (regPhone.test(val) == true) {
                $("#msg1").prev().addClass("has-safe").removeClass("has-error");
                $(this).attr("aria-invalid","false");
                $("#msg1").text("등록가능한 번호 입니다.");
            } else {
                $("#msg1").prev().addClass("has-error").removeClass("has-safe");
                $(this).attr("aria-invalid","true");
                $("#msg1").text("휴대폰 번호가 올바르지 않습니다.");
            }
        }else {
            $("#msg1").prev().addClass("has-error").removeClass("has-safe");
            $(this).attr("aria-invalid","true");
            $("#msg1").text("휴대폰 번호가 올바르지 않습니다.");
        }
        if(val.length>0){
            $("#msg1").show();
        } else {
            $("#msg1").prev().addClass("has-safe").removeClass("has-error");
            $("#msg1").hide();
        }
    });

    $("#jehuEmail").keyup(function(){
        var val = $(this).val();
        var regExpEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
        if(val.length < 6 || !regExpEmail.test(val)){
            $("#msg2").prev().addClass("has-error").removeClass("has-safe");
            $(this).attr("aria-invalid","true");
            $("#msg2").text("형식에 맞지 않는 이메일입니다.");
        }else {
            $("#msg2").prev().addClass("has-safe").removeClass("has-error");
            $(this).attr("aria-invalid","false");
            $("#msg2").text("등록가능한 이메일입니다.");
        }
        if(val.length>0){
            $("#msg2").show();
        } else {
            $("#msg2").prev().addClass("has-safe").removeClass("has-error");
            $("#msg2").hide();
        }

    });

    //////////////////////////////////// 버튼 이벤트 ////////////////////////////////////
    $("#jehuTitle,#jehuContent,#jehuCompanyNm,#jehuRegNm,#jehuMobileNo,#jehuEmail").keyup(function(){
        btnChk();
    });

    $("#jehuPriAgreeYn").change(function(){
        btnChk();
    });

});

function btnChk(){
    var jehuTitle = ajaxCommon.isNullNvl($("#jehuTitle").val(),"");
    var jehuCategory = ajaxCommon.isNullNvl($("#jehuCategory").val(),"");
    var jehuContent = ajaxCommon.isNullNvl($("#jehuContent").val(),"");
    var jehuCompanyNm = ajaxCommon.isNullNvl($("#jehuCompanyNm").val(),"");
    var jehuRegNm = ajaxCommon.isNullNvl($("#jehuRegNm").val(),"");
    var jehuMobileNo = ajaxCommon.isNullNvl($("#jehuMobileNo").val(),"");
    var jehuEmail = ajaxCommon.isNullNvl($("#jehuEmail").val(),"");
    var jehuPriAgreeYn = $("input:checkbox[name=jehuPriAgreeYn]").is(":checked");
    // 휴대폰 유효성
    var isMo = false;
    var moLen = jehuMobileNo.length;
    if(moLen > 10){
        var regPhone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
        if (regPhone.test(jehuMobileNo) == true) {
            isMo = true;
        }
    }

    // 이메일 유효성
    var isEmail = false;
    var regExpEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    if(regExpEmail.test(jehuEmail) == true) {
        isEmail = true;
    }

    if(jehuTitle!="" && jehuCategory !="" && jehuContent !="" && jehuCompanyNm !="" && jehuRegNm !="" && jehuMobileNo !="" && jehuEmail !="" && jehuPriAgreeYn==true && isMo==true && isEmail==true){
        $("#jehuSuggBtn").prop("disabled",false);
    } else {
        $("#jehuSuggBtn").prop("disabled",true);
    }
}

function valueChk(){

    var obj ={};
    var result = true;
    var varData = null;
    var jehuTitle = ajaxCommon.isNullNvl($("#jehuTitle").val(),"");
    var jehuCategory = ajaxCommon.isNullNvl($("#jehuCategory").val(),"");
    var jehuContent = ajaxCommon.isNullNvl($("#jehuContent").val(),"");
    var jehuCompanyNm = ajaxCommon.isNullNvl($("#jehuCompanyNm").val(),"");
    var jehuRegNm = ajaxCommon.isNullNvl($("#jehuRegNm").val(),"");
    var jehuMobileNo = ajaxCommon.isNullNvl($("#jehuMobileNo").val(),"");
    var jehuEmail = ajaxCommon.isNullNvl($("#jehuEmail").val(),"");
    var jehuPriAgreeYn = $("input:checkbox[name=jehuPriAgreeYn]").is(":checked");
    if(jehuTitle==""){
        MCP.alert("제목을 입력해 주세요.",function(){
            $("#jehuTitle").focus();
        });
        return false;
    }
    if(jehuCategory==""){
        MCP.alert("카테고리를 선택해 주세요.",function(){
            $("#jehuCategory").focus();
        });
        return false;
    }
    if(jehuContent==""){
        MCP.alert("제휴제안 내용을 입력해 주세요.",function(){
            $("#jehuContent").focus();
        });
        return false;
    }
    if(jehuCompanyNm==""){
        MCP.alert("회사명을 입력해 주세요.",function(){
            $("#jehuCompanyNm").focus();
        });
        return false;
    }
    if(jehuRegNm==""){
        MCP.alert("제안자명을 입력해 주세요.",function(){
            $("#jehuRegNm").focus();
        });
        return false;
    }
    if(jehuMobileNo==""){
        MCP.alert("휴대폰번호를 입력해 주세요.",function(){
            $("#jehuMobileNo").focus();
        });
        return false;
    } else {
        var invalid = $("#jehuMobileNo").attr("aria-invalid");
        if(invalid=="true"){
            MCP.alert("휴대폰번호를 확인해 주세요.",function(){
                $("#jehuMobileNo").focus();
            });
            return false;
        }
    }
    if(jehuEmail==""){
        MCP.alert("이메일을 입력해 주세요.",function(){
            $("#jehuEmail").focus();
        });
        return false;
    } else {
        var invalid = $("#jehuEmail").attr("aria-invalid");
        if(invalid=="true"){
            MCP.alert("이메일 주소를 확인해 주세요.",function(){
                $("#jehuEmail").focus();
            });
            return false;
        }
    }
    if(!jehuPriAgreeYn){
        MCP.alert("개인정보 수집 및 이용에 대한 동의체크해 주세요.",function(){
            $("input:checkbox[name=jehuPriAgreeYn]").focus();
        });
        return false;
    }

    if(!confirm("등록하시겠습니까?")){
        return false;
    }

    var varData = ajaxCommon.getSerializedData({
        jehuTitle : jehuTitle,
        jehuCategory : jehuCategory,
        jehuContent : jehuContent,
        jehuCompanyNm : jehuCompanyNm,
        jehuRegNm : jehuRegNm,
        jehuMobileNo : jehuMobileNo,
        jehuEmail : jehuEmail,
        jehuPriAgreeYn : "Y"
    });

    obj = {"result":result ,"varData":varData};
    return obj;
}


