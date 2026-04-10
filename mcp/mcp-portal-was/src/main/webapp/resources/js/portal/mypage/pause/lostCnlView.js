
$(document).ready(function (){

	window.onpopstate = function (event){
		var ncn = $("#ncn").val();
		var state = { 'ncn': ncn};
		history.pushState(state, null,location.href);
		history.go("/mypage/lostView.do");
	}

	// 분실신고 화면
	$("#tab1").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/mypage/lostView.do"
		});
		var ncn = $("#ctn option:selected").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	});

	// 분실신고 해제
	$("#cnlApplyBtn").click(function(){

		var ncn = $("#ctn option:selected").val();
		var strPwdNumInsert = ajaxCommon.isNullNvl($("#strPwdNumInsert").val(),"");
		if(strPwdNumInsert==""){
			MCP.alert("분실신고 해제 비밀번호를 입력해 주세요.",function(){
				$("#strPwdNumInsert").focus();
			});
			return false;
		} else {
			if(strPwdNumInsert.length < 4){
				MCP.alert("비밀번호는 4~8자리 숫자입니다.");
				return false;
			}
		}

		var custNm = $("#userName").val();
		var cntcTlphNo = $("#cntcTlphNo").text();
		var conTect = "확인 버튼을 선택하시면 분실신고가 해제됩니다." +
		"\n고객명 :"+custNm+"" +
		"\n연락 가능한 연락처 :"+cntcTlphNo;

		if(confirm(conTect)){

			var varData = ajaxCommon.getSerializedData({
				ncn : ncn,
				strPwdNumInsert : strPwdNumInsert
		    });
			ajaxCommon.getItem({
		        id:'pcsLostChgAjax'
		        ,cache:false
		        ,url:'/mypage/pcsLostCnlChgAjax.do'
		        ,data: varData
		        ,dataType:"json"
		        ,async:false
		    }
		    ,function(jsonObj){

		    	if(jsonObj.returnCode == "00") {
		       		MCP.alert("분실해제 신청이 정상적으로 접수 되었습니다.");
		       		setTimeout(function(){
		       			location.reload();
                    }, 500);
		       	} else {
		       		MCP.alert(jsonObj.message);

		       	}
		    });
		}

	});

	////////////////////////////// 버튼 비활성 ///////////////////////////////////
	$("#strPwdNumInsert").keyup(function(){
		var strPwdNumInsert = ajaxCommon.isNullNvl($("#strPwdNumInsert").val(),"");
		if(strPwdNumInsert !="" && strPwdNumInsert.length > 3){
			$("#cnlApplyBtn").prop("disabled",false);
		} else {
			$("#cnlApplyBtn").prop("disabled",true);
		}
	});



});


function select(){

	ajaxCommon.createForm({
	    method:"post"
	    ,action:"/mypage/lostCnlView.do"
	});
	var ncn = $("#ctn option:selected").val();
	ajaxCommon.attachHiddenElement("ncn",ncn);
	ajaxCommon.formSubmit();
}

