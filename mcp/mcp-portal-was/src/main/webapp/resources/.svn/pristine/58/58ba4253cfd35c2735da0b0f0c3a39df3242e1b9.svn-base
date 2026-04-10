;

var pageObj = {
    niceType:""
    , authObj:{}
    , niceHistSeq:0
    , startTime:0
    , modelIdOther:""
    , modelNmOther:""
    ,chk:"N"
    ,authChk:"N"
    ,intmSrlNoOther:""
}

let params = new URLSearchParams(window.location.search);
let rateCd = params.get("rateCd");


$(document).ready(function(){
    // 숫자만 입력가능1
    $(".numOnly").keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/gi, ""));
    });

    $(".ocrImg").change(function(){
        $("#nextStep").prop("disabled",false);
        pageObj.chk="N";
        pageObj.authChk = "N";
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
            clear();
            var retCode = result.retCode;
            var retMsg = result.retMsg;
            var uploadPhoneSrlNo = result.uploadPhoneSrlNo;
            var eid = "";
            var imei1 = "";
            var imei2 = "";
            if(retCode != '0000'){
                $("#errTxt").html(retMsg).show();
//                $("#setBtn").prop("disabled",true);
            } else {
                $("#errTxt").html("").hide();
//            	$("#setBtn").prop("disabled",false);
                eid = result.eid;
                imei1 = result.imei1;
                imei2 = result.imei2;
                $("#eidTxt").val(eid);
                $("#imei1Txt").val(imei1);
                $("#imei2Txt").val(imei2);

            }

            var el = document.querySelector('#esim-check-modal');
            var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
            modal.open();
        });

    });

    // 다시등록하기
    $("#reSetBtn").click(function(){
        clear();
    });

    // 다음
    $("#nextStep").click(function(e){

        var resultCode = ajaxCommon.isNullNvl($("#resultCode").val(),"");
        if(resultCode==""){
            MCP.alert("이미지를 등록한 후 다음버튼을 눌러주세요");
            return false;
        }
        if(resultCode=="6000"){
            var modelId = ajaxCommon.isNullNvl($("#phoneModel option:selected").val(),"");
            var modelNm = ajaxCommon.isNullNvl($("#phoneModel option:selected").attr("modelNm"),"");
            if(modelId==""){
                MCP.alert("모델명을 선택해 주세요.");
                return false;
            }
            $("#modelId").val(modelId);
            $("#modelNm").val(modelNm);
        }
        if(resultCode=="4000"){
            if(pageObj.authChk == "N"){
                MCP.alert("본인인증이 필요합니다.");
                return false;
            }
        }

        var errMsg = "";
        if(resultCode=="1000"||resultCode=="2000"||resultCode=="3000"||resultCode=="4000"||resultCode=="5000"||resultCode=="6000"){
            fncOmdReg(resultCode);
            return false;
        } else if(resultCode==""){
            errMsg = "이미지등록하여 eid/imei/imei2 값을 확인해 주세요.";
            MCP.alert(errMsg);
            return false;
        } else if(resultCode=="1002"){
            errMsg = "등록된 EID값이 기기정보와 불일치합니다. <br/> 고객센터(1899-5000)를 통해 EID 변경 후 시도 바랍니다.";
            MCP.alert(errMsg);
            return false;
        } else if(resultCode=="1010"){
            errMsg = "현재 IMEI2가 사용중입니다. <br/> IMEI1 eSIM 지원 기기일 경우 고객센터를 통하여 개통가능여부 확인 후 개통 진행이 가능합니다.";
            MCP.alert(errMsg);
            return false;
        } else if(resultCode=="1040"){
            errMsg = "사용이 불가한 기기입니다.";
            MCP.alert(errMsg);
            return false;
        } else if(resultCode=="9901") {
            errMsg = "입력하신 단말기는 셀프개통 이용이 불가합니다.<br/>자세한 내용은 고객센터(1899-5000)로 문의 부탁 드립니다.";
            MCP.alert(errMsg);
        } else {
            var uploadPhoneSrlNo = Number($("#uploadPhoneSrlNo").val());
            var eid = ajaxCommon.isNullNvl($("#eid").val(),"");
            var imei1 = ajaxCommon.isNullNvl($("#imei1").val(),"");
            var imei2 = ajaxCommon.isNullNvl($("#imei2").val(),"");
            if(eid.length==32 && imei1.length==15 && imei2.length==15){
                errMsg = "휴대폰 등록이 실패하였습니다.<br/> 상담사 개통 신청으로만 진행이 가능합니다. <br/><br/> 상담사 신청으로 이동하시겠습니까?";
                KTM.Confirm(errMsg
                    , function () {
                        location.href="/appForm/appFormDesignView.do?prdtIndCd=10&uploadPhoneSrlNo="+uploadPhoneSrlNo+"&rateCd="+rateCd;
                        return ;
                    },function() {
                        return ;
                    });
            } else {
                errMsg = "이미지등록하여 eid/imei/imei2 값을 확인해 주세요.";
                MCP.alert(errMsg);
            }
            return false;
        }


    });

    $("#setBtn").click(function(){

        if(pageObj.chk=="Y"){
            KTM.Dialog.closeAll();
            return false;
        }

        var imei1 = ajaxCommon.isNullNvl($("#imei1Txt").val(),"");
        var imei2 = ajaxCommon.isNullNvl($("#imei2Txt").val(),"");
        var eid = ajaxCommon.isNullNvl($("#eidTxt").val(),"");
        if(imei1 =="" || imei1.length != 15 ){
            MCP.alert("imei1 자릿수가 일치하는지 확인해 주세요.",function(){
                $("#imei1Txt").focus();
            });
            return false;
        }
        if(imei2 =="" || imei2.length != 15 ){
            MCP.alert("imei2 자릿수가 일치하는지 확인해 주세요.",function(){
                $("#imei2Txt").focus();
            });
            return false;
        }
        if(eid =="" || eid.length != 32 ){
            MCP.alert("eid 자릿수가 일치하는지 확인해 주세요.",function(){
                $("#eidTxt").focus();
            });
            return false;
        }

        var varData = ajaxCommon.getSerializedData({
            imei1 : imei1
            ,imei2 : imei2
            ,eid : eid
        });

        ajaxCommon.getItem({
            id:'eSimChkAjax'
            ,cache:false
            ,url:'/appForm/eSimChkAjax.do'
            ,data: varData
            ,dataType:"json"
            ,async:false
        }

        ,function(jsonObj){
            pageObj.chk = "Y";
            $("#eidTxt, #imei1Txt, #imei2Txt").attr("readonly",true);
            var resDto = jsonObj.resDto;
            $("#eid").val(eid).parent().addClass("has-value");
            $("#imei1").val(imei1).parent().addClass("has-value");
            $("#imei2").val(imei2).parent().addClass("has-value");
            var uploadPhoneSrlNo = resDto.uploadPhoneSrlNo;
            $("#uploadPhoneSrlNo").val(uploadPhoneSrlNo);

            var resultCode = resDto.resultCode;
            var moveTlcmIndCd = resDto.moveTlcmIndCd;
            var moveCmncGnrtIndCd = resDto.moveCmncGnrtIndCd;
            var modelId = resDto.modelId;
            var modelNm = resDto.modelNm;
            var intmSrlNo = resDto.intmSrlNo;
            var moveCd = resDto.moveCd;

            $("#resultCode").val(resultCode);
            $(".ocrImg").val("");
            if(resultCode=="6000"){
                $("#phoneArea").show();
            } else {
                $("#phoneArea").hide();
            }
            if(resultCode=="4000") {
                $("#authArea").show();
            } else {
                $("#authArea").hide();
            }
            if(resultCode=="1000"||resultCode=="2000"||resultCode=="3000"||resultCode=="4000"||resultCode=="5000"||resultCode=="6000"){
                KTM.Dialog.closeAll();
                $("#errTxt").html("").hide();
            } else {

                var errMsg = "";
                if(resultCode=="1002"){
                    errMsg = "등록된 EID값이 기기정보와 불일치합니다.<br/>고객센터(1899-5000)를 통해 EID 변경 후 시도 바랍니다.";
                } else if(resultCode=="1010"){
                    errMsg = "현재 IMEI2가 사용중입니다.<br/>IMEI1 eSIM 지원 기기일 경우 고객센터를 통하여 개통가능여부 확인 후 개통 진행이 가능합니다.";
                } else if(resultCode=="1040"){
                    errMsg = "사용이 불가한 기기입니다.";
                } else if(resultCode=="9901") {
                    errMsg = "입력하신 단말기는 셀프개통 이용이 불가합니다.<br/>자세한 내용은 고객센터(1899-5000)로 문의 부탁 드립니다.";
                } else {
                    errMsg = "휴대폰 등록이 실패하였습니다.<br/>상담사 개통 신청으로만 진행이 가능합니다.";
                }
                $("#errTxt").html(errMsg).show();
            }
            $("#moveTlcmIndCd").val(moveTlcmIndCd);
            $("#moveCmncGnrtIndCd").val(moveCmncGnrtIndCd);
            $("#moveCd").val(moveCd);
            $("#modelId").val(modelId);
            $("#modelNm").val(modelNm).parent().addClass("has-value");
            $("#intmSrlNo").val(intmSrlNo);
            pageObj.modelIdOther = resDto.modelIdOther;
            pageObj.modelNmOther = resDto.modelNmOther;
            pageObj.intmSrlNoOther = resDto.intmSrlNoOther;

        });
    });


    $("#btnSmsAuth").click(function(){
        var cstmrName = ajaxCommon.isNullNvl($("#cstmrName").val(),"");
        var cstmrNativeRrn01 = ajaxCommon.isNullNvl($("#cstmrNativeRrn01").val(),"");
        var cstmrNativeRrn02 = ajaxCommon.isNullNvl($("#cstmrNativeRrn02").val(),"");
        if(cstmrName==""){
            MCP.alert("이름을 작성해 주세요",function(){
                $("#cstmrName").focus();
            });

            return;
        }
        if(cstmrNativeRrn01=="" || cstmrNativeRrn01.length!=6){
            MCP.alert("주민등록 번호를 확인해 주세요.",function(){
                $("#cstmrNativeRrn01").focus();
            });

            return;
        }
        if(cstmrNativeRrn02=="" || cstmrNativeRrn02.length!=7){
            MCP.alert("주민등록 번호를 확인해 주세요.",function(){
                $("#cstmrNativeRrn02").focus();
            });

            return;
        }
        pop_nice();
    });

    // 휴대폰 모델명 공통코드조회
    $("#phoneNm").change(function(){

        var dtlCd = $("#phoneNm option:selected").val();
        if(dtlCd==""){
            $("#phoneModel").html("<option value=''>모델명 선택</option>");
        } else {
            getCdTable(dtlCd);
        }
    });

});

function clear(){
    $("#phoneArea").hide();
    $("#authArea").hide();
    $(".ocrImg").val("");
    $("#errTxt").html("").hide();
    $("#eidTxt, #imei1Txt, #imei2Txt").val("").removeAttr("readonly");
    $("#uploadPhoneSrlNo").val("");
    $("#eid,#imei1,#imei2,#modelNm").val("").parent().removeClass("has-value");
    $("#modelId,#modelNm,#intmSrlNo,#resultCode,#moveTlcmIndCd,#moveCmncGnrtIndCd,#moveCd").val("");
    $("#phoneNm option").eq(0).prop("selected",true);
    $("#phoneModel").html("").html("<option value=''>모델명 선택</option>");
    $("#cstmrName ,#cstmrNativeRrn01 ,#cstmrNativeRrn02").val("").parent().removeClass("has-value");
}

function getCdTable(varData){

    var varData = ajaxCommon.getSerializedData({
        grpCd:varData
    });

    ajaxCommon.getItem({
        id:'getCodeListAjax'
        ,cache:false
        ,url:'/getCodeListAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    }
    ,function(jsonObj){

        var rtnList = jsonObj;
        var ctgHtml = "<option value=''>모델명 선택</option>";
        if(rtnList !=null){
            for(var i=0; i<rtnList.length; i++){
                ctgHtml +="<option value='"+rtnList[i].dtlCd+"' modelNm='"+rtnList[i].expnsnStrVal1+"'>"+rtnList[i].dtlCdNm+"</option>";
            }
        }
        $("#phoneModel").html(ctgHtml);
    });

}

function fncOmdReg(resultCode){

    $("#nextStep").prop("disabled",true);
    var varData = ajaxCommon.getSerializedData({
        imei1 : $("#imei1").val()
        ,imei2 : $("#imei2").val()
        ,eid : $("#eid").val()
        ,uploadPhoneSrlNo : Number($("#uploadPhoneSrlNo").val())
        ,resultCode: resultCode
        ,modelId : $("#modelId").val()
        ,intmSrlNo : $("#intmSrlNo").val()
        ,modelNm : $("#modelNm").val()
        ,moveTlcmIndCd : $("#moveTlcmIndCd").val()
        ,moveCmncGnrtIndCd : $("#moveCmncGnrtIndCd").val()
        ,moveCd : $("#moveCd").val()
        ,modelIdOther : pageObj.modelIdOther
        ,modelNmOther : pageObj.modelNmOther
        ,intmSrlNoOther : pageObj.intmSrlNoOther

    });

    ajaxCommon.getItem({
        id:'omdRegAjax'
        ,cache:false
        ,url:'/appForm/omdRegAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    }

    ,function(jsonObj){

        var resDto = jsonObj.resDto;
        var resultCode = resDto.resultCode;
        var resultMsg = resDto.resultMsg;
        var uploadPhoneSrlNo = Number(ajaxCommon.isNullNvl($("#uploadPhoneSrlNo").val(),0));
        if(resultCode=="0000"){
            if( uploadPhoneSrlNo > 0 ){
                location.href="/appForm/appFormDesignView.do?prdtIndCd=10&uploadPhoneSrlNo="+uploadPhoneSrlNo+"&rateCd="+rateCd;
                return false;
            } else {
                MCP.alert("이미지등록하여 eid/imei/imei2 값을 확인해 주세요.");
                return false;
            }
        } else if (resultCode=="9901") {
            MCP.alert("입력하신 단말기는 셀프개통 이용이 불가합니다.<br/>자세한 내용은 고객센터(1899-5000)로 문의 부탁 드립니다.");
            return false;
        } else {
            var alertMsg = "휴대폰 등록이 실패하였습니다.<br/> 상담사 개통 신청으로만 진행이 가능합니다. <br/><br/> 상담사 신청으로 이동하시겠습니까?";
            KTM.Confirm(alertMsg
                , function () {
                    location.href="/appForm/appFormDesignView.do?prdtIndCd=10&uploadPhoneSrlNo="+uploadPhoneSrlNo+"&rateCd="+rateCd;
                    return ;
                },function() {
                    return ;
                });
            return false;

        }
        $("#nextStep").prop("disabled",false);
    });

}

function isSmsChk(){


    var varData = ajaxCommon.getSerializedData({
        cstmrName : $("#cstmrName").val()
        ,cstmrNativeRrn01 : $("#cstmrNativeRrn01").val()
    });

    ajaxCommon.getItem({
        id:'niceAuthChkAjax'
        ,cache:false
        ,url:'/appForm/niceAuthChkAjax.do'
        ,data: varData
        ,dataType:"json"
        ,async:false
    }

    ,function(jsonObj){
        var resDto = jsonObj.resDto;
        var resultCode = resDto.resultCode;
        if(resultCode!="0000"){
            MCP.alert("동일명이 아님");
        } else {
            pageObj.authChk = "Y";
        }

    });

}


function pop_nice() {
    var data = {width:'500', height:'700'};
    openPage('outLink2', '/nice/popNiceAuth.do?sAuthType=M', data)
}

function fnNiceCert(obj) {
    niceAuthResult(obj);
}

function fnNiceCertErr() {
    return false;
}

function niceAuthResult(prarObj){
    var cstmrName = ajaxCommon.isNullNvl($("#cstmrName").val(),"");
    var cstmrNativeRrn01 = ajaxCommon.isNullNvl($("#cstmrNativeRrn01").val(),"");

    var varData = ajaxCommon.getSerializedData({
        cstmrName:cstmrName
        , cstmrNativeRrn:cstmrNativeRrn01
        , reqSeq:prarObj.reqSeq
        , resSeq:prarObj.resSeq
        , paramR3:'custAuth'
        , startTime:pageObj.startTime
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
            isSmsChk();
        } else{
            MCP.alert(jsonObj.RESULT_DESC);
            return null;
        }
    });


}




