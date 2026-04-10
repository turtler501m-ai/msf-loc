var pageObj = {
    sbscYnCode:"Y"
     ,engtPerd:"24"
}

$(document).ready(function(){

	$("#rateSoc").val("");
	$("#rateNm").text("-");
	$("#svcCntrNo").val("");

	$("#applyBtn").click(function(){
		var certifyYn = $("#certify").val();
		if(certifyYn!="Y"){
			MCP.alert("본인인증을 진행해 주세요.",function(){
				$("#custNm").focus();
			});
			return false;
		}

		if(!$("#chkA1").is(":checked")){
			MCP.alert("정보수집 동의에 체크해 주세요.");
			return false;
		}

		var custNm = ajaxCommon.isNullNvl($("#custNm").val(),"");
		var phoneNum = ajaxCommon.isNullNvl($("#phoneNum").val(),"");
		var certifySms = ajaxCommon.isNullNvl($("#certifySms").val(),"");
		var varData = ajaxCommon.getSerializedData({
			custNm:$.trim(custNm)
			,phoneNum:$.trim(phoneNum)
			,certifySms : certifySms
	    });

	    ajaxCommon.getItem({
	        id:'userSmsCheckAjax'
	        ,cache:false
	        ,url:'/rate/userSmsCheckAjax.do'
	        ,data: varData
	        ,dataType:"json"
	    }
	    ,function(data){

	    	if (data.RESULT_CODE == '00000'){
	    		$("#rateSoc").val(data.soc);
    			$("#rateNm").text(data.rateNm);
    			$("#btnSvcPreChk").prop("disabled",false);
    			$("#svcCntrNo").val(data.svcCntrNo);
    			$("#applyBtn").prop("disabled",true);
	    	}else{
	    		MCP.alert(data.MSG,function(){
	    			$("#rateSoc").val("");
	    			$("#rateNm").text("-");
	    			$("#btnSvcPreChk").prop("disabled",true);
	    			$("#svcCntrNo").val("");
	    			$("#applyBtn").prop("disabled",false);
	    		});
	    		return false;
	    	}
	    });
	});

	$('#btnSvcPreChk').click(function(){

		var certifyYn = $("#certify").val();
		if(certifyYn!="Y"){
			MCP.alert("본인인증을 진행해 주세요.",function(){
				$("#custNm").focus();
			});
			return false;
		}

        var varData = ajaxCommon.getSerializedData({
            ncn:$("#svcCntrNo").val()
        });

        pageObj.engtPerd = $('input:radio[name=instNom]:checked').val();
        ajaxCommon.getItem({
            id:'moscSdsSvcPreChkAjax'
            ,cache:false
            ,url:'/rate/moscSdsSvcPreChkAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {

                if ( jsonObj.SBSC_YN = "Y" ) {
                    //심플할인 금액 조회
                    var varData2 = ajaxCommon.getSerializedData({
                        ncn:$("#svcCntrNo").val()
                        , rateSoc:$("#rateSoc").val()
                        , engtPerd: pageObj.engtPerd
                    });

                    ajaxCommon.getItem({
                        id:'getReSpnsrPriceInfoAjax'
                        ,cache:false
                        ,url:'/rate/getReSpnsrPriceInfoAjax.do'
                        ,data: varData2
                        ,dataType:"json"
                        ,async:false
                    }
                    ,function(jsonObj){

                        if (jsonObj.saleSubsdMst != null && jsonObj.saleSubsdMst.totalVatPriceDC != null && jsonObj.saleSubsdMst.totalVatPriceDC > 0) {
                            var totalVatPrice = jsonObj.saleSubsdMst.totalVatPriceDC ;
                            $("#totalVatPriceDC").text(zeroRemoveMinu(jsonObj.saleSubsdMst.totalVatPriceDC) +"원");
                            //재약정 가능
                            $("#divResult").show();
                            $("#divIntro").hide();

                        } else {

                            var tempHtml = "<strong class='c-item__title'>요금할인 재약정이 불가능합니다.<br/><br/>재약정 대상 요금제가 아닙니다.<br/> 고객센터(1899-5000) 통하여 재약정 문의가 가능합니다.</strong>";

                            if(jsonObj.RESULT_CODE == "STEP01"){
                                tempHtml = "<strong class='c-item__title'>"+jsonObj.RESULT_MSG+"</strong>";
                            }

                            $("#tbodyId").html(tempHtml);
                            $("#divPlcyDcInfo").show();
                        }
                    });

                } else {
                    var resultMsg = jsonObj.RESULT_MSG;
                    if (resultMsg ==null || resultMsg =="" ) {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상 요금제가 아닙니다.<br/> 고객센터(1899-5000)를 통하여 재약정 문의가 가능합니다.";
                    } else if (resultMsg == "스폰서 고객이므로 심플할인(알뜰폰)에 가입할 수 없습니다." ) {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상자가 아닙니다.";
                    } else if (resultMsg == "심플할인(알뜰폰) 가입 고객입니다. 수동해지 가능합니다." ) {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정에 이미 가입되어 있습니다.";
                    } else if (resultMsg == "정지 중 고객이므로 심플할인(알뜰폰)에 가입할 수 없습니다." ) {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>정지 상태에서는 가입이 불가합니다.";
                    } else {
                        resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상 요금제가 아닙니다. <br/>고객센터(1899-5000)를 통하여 재약정 문의가 가능합니다.";
                    }

                    var tempHtml = "<strong class='c-item__title'>"+resultMsg+"</strong>"
                    $("#tbodyId").html(tempHtml);
                    $("#divPlcyDcInfo").show();
                }
                $("#btnSvcPreChkArea").hide();
                $("#btnReSvcPreChkArea").show();
                $("#trInstnomTxt").text(pageObj.engtPerd + " 개월");
                $("#trInstnom1").hide();
                $("#trInstnom2").show();


            } else if ("00002" ==  jsonObj.RESULT_CODE) {
                var resultMsg = jsonObj.RESULT_MSG;
                if (resultMsg ==null || resultMsg =="" ) {
                    resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>요금할인 재약정 대상 요금제가 아닙니다.<br/> 고객센터(1899-5000)를 통하여 재약정 문의가 가능합니다.";
                } else {
                    resultMsg = "요금할인 재약정이 불가능합니다.<br/><br/>" + resultMsg ;
                }

                var tempHtml = "<strong class='c-item__title'>"+resultMsg+"</strong>"
                $("#tbodyId").html(tempHtml);
                $("#divPlcyDcInfo").show();

            } else if ("00003" ==  jsonObj.RESULT_CODE) {
                MCP.alert("동일한 시간에 중복 요청 입니다. 잠시후 다시 시도 하시기 바랍니다.");
                return false;
            } else if("00005" ==  jsonObj.RESULT_CODE){
            	MCP.alert("본인인증이 필요합니다.");
            	return false;
            } else if("STEP01" == jsonObj.RESULT_CODE){ // STEP 오류
                MCP.alert(jsonObj.RESULT_MSG);
                return false;
            }else {
            	MCP.alert("약정 가능 여부 조회 실패 하였습니다. \n잠시후 다시 시도 하시기 바랍니다. ");
                return false;
            }
        });
    });

	// 재약정 가입 버튼
	$("#btnReqReg").click(function(){

		var certifyYn = $("#certify").val();
		if(certifyYn!="Y"){
			MCP.alert("본인인증을 진행해 주세요.",function(){
				$("#certifyCallBtn").focus();
			});
			return false;
		}

		var chkAgree = $("input:checkbox[name=chkAgree]").is(":checked");
		if(!chkAgree){
			MCP.alert("개인정보 수집이용 동의에 체크해주세요.");
			return false;
		}

		if(!confirm("재약정가입 하시겠습니까?")){
    		return false;
		}
		var presentCode = ajaxCommon.isNullNvl($('input:radio[name="prizeType"]:checked').val(),"");
        var varData = ajaxCommon.getSerializedData({
            mCode:"rate"
            , ncn:$("#svcCntrNo").val()
            , engtPerd: pageObj.engtPerd
            , presentCode:presentCode
            , ctn : $.trim($("#phoneNum").val())
        });

        ajaxCommon.getItem({
            id:'moscSdsSvcChgAjax'
            ,cache:false
            ,url:'/rate/moscSdsSvcChgAjax.do'
            ,data: varData
            ,dataType:"json"
        }
        ,function(jsonObj){
            if (AJAX_RESULT_CODE.SUCCESS ==  jsonObj.RESULT_CODE) {
                ajaxCommon.createForm({
                    method:"post"
                    ,action:"/m/rate/spnsrRecontractComplete.do"
                 });

                ajaxCommon.attachHiddenElement("svcCntrNo",$("#svcCntrNo").val());
                ajaxCommon.formSubmit();
            } else {
                alert(jsonObj.RESULT_MSG);
            }
        });

    });

	$("input:radio[name=instNom]").click(function(){
        var thisVal = $(this).val();
        if (pageObj.engtPerd != thisVal) {

            var tempHtml = "<strong class='c-item__title'>약정 기간을 선택하시고,<br/>약정 가능 여부 조회 버튼을 클릭하시면 자동 계산됩니다.</strong>"
            $("#tbodyId").html(tempHtml);
            $("#divResult").hide();
            $("#divIntro").show();
            $("#divPlcyDcInfo").hide();
            $("#btnSvcPreChkArea").show();
            $("#btnReSvcPreChkArea").hide();
            $("#trInstnom1").show();
            $("#trInstnom2").hide();
        }
    }) ;

    $("#btnReSvcPreChk").click(function(){
        var tempHtml = "<strong class='c-item__title'>약정 기간을 선택하시고,<br/>약정 가능 여부 조회 버튼을 클릭하시면 자동 계산됩니다.</strong>"
            $("#tbodyId").html(tempHtml);
            $("#divResult").hide();
            $("#divIntro").show();
            $("#divPlcyDcInfo").hide();
            $("#btnSvcPreChkArea").show();
            $("#btnReSvcPreChkArea").hide();
            $("#trInstnom1").show();
            $("#trInstnom2").hide();
        }) ;


    var zeroRemoveMinu = function(input) {
        if (input == 0) {
            return input;
        } else {
            return "-" + numberWithCommas(input);
        }
    };
});


function btnRegDtl(param){
	openPage('termsPop','/termsPop.do',param);
}



