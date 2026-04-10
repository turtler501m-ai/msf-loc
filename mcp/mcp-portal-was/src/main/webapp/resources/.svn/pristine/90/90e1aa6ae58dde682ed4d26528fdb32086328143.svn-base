$(document).ready(function(){


	$("#tab1").click(function(){
		var certifyYn = ajaxCommon.isNullNvl($("#certifyYn").val(),"");
		if(certifyYn=="Y"){
			if(!$("#chkAgree").is(":checked")){
				MCP.alert("정보수집 동의에 체크해 주세요.");
				return false;
			}
		}
		location.href="/cs/csInquiryInt.do";
	});

	$("#applyBtn").click(function(){

		if(!$("#chkAgree").is(":checked")){
			MCP.alert("정보수집 동의에 체크해 주세요.");
			return false;
		}

		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/cs/csInquiryIntHist.do"
		});
		ajaxCommon.formSubmit();
	});

	// 체크박스
	$("#chkAgree").change(function(){
		var certifyYn = $("#certifyYn").val();
		if($(this).is(":checked") && certifyYn=="Y"){
			$("#applyBtn").prop("disabled",false);
		} else {
			$("#applyBtn").prop("disabled",true);
		}
	});

});

function btn_reg(){
	if($("#chkAgree").is(":checked")){
		$("#applyBtn").prop("disabled",false);
	}
}

//약관동의
function btnRegDtl(param){
	openPage('termsPop','/termsPop.do',param);
}

function targetTermsAgree() {
	$("#chkAgree").prop("checked",true);
	var certifyYn = $("#certifyYn").val();
	if(certifyYn=="Y"){
		$("#applyBtn").prop("disabled",false);
	} else {
		$("#applyBtn").prop("disabled",true);
	}
}

function goPaging(pageNo){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/cs/csInquiryIntHist.do"
	});
	ajaxCommon.attachHiddenElement("pageNo",pageNo);
	ajaxCommon.formSubmit();
}
