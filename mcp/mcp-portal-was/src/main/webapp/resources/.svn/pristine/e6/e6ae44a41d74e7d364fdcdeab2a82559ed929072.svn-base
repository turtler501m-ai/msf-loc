
$(document).ready(function (){

	window.onpopstate = function (event){
		var ncn = $("#ncn").val();
		var state = { 'ncn': ncn};
		history.pushState(state, null,location.href);
		history.go("/m/mypage/suspendView01.do");
	}


	$("#apply").click(function(){
		if(validatorCheck()){
			if(confirm("일시정지 신청을 하시겠습니까?")){

				var ncn = $("#ncn").val();
				var reasonCode = $("#reasonCode option:selected").val();
				var cpStartDt = $("#cpStartDt").val().replace(/[^0-9]/gi, "");
				var cpEndDt = $("#cpEndDt").val().replace(/[^0-9]/gi, "");
				var userMemo = $("#userMemo").val();

				var varData = ajaxCommon.getSerializedData({
					ncn : ncn,
					reasonCode : reasonCode,
					cpStartDt : cpStartDt,
					cpEndDt : cpEndDt,
					cpPwdInsert : $("#cpPwdInsert").val(),
					userMemo : userMemo
			    });

				ajaxCommon.getItem({
			        id:'suspenChgAjax'
			        ,cache:false
			        ,url:'/mypage/suspenChgAjax.do'
			        ,data: varData
			        ,dataType:"json"
			        ,async:false
			    }
			    ,function(jsonObj){

			    	if(jsonObj.returnCode == "00") {
			    		var gubun = "S";
			    		ajaxCommon.createForm({
			    		    method:"post"
			    		    ,action:"/m/mypage/suspendView03.do"
			    		});

			    		ajaxCommon.attachHiddenElement("gubun",gubun);
			    		ajaxCommon.attachHiddenElement("ncn",ncn);
			    		ajaxCommon.attachHiddenElement("reasonCode",reasonCode);
			    		ajaxCommon.attachHiddenElement("cpStartDt",cpStartDt);
			    		ajaxCommon.attachHiddenElement("cpEndDt",cpEndDt);
			    		ajaxCommon.attachHiddenElement("userMemo",userMemo);
			    		ajaxCommon.formSubmit();
	               	} else {
	               		MCP.alert(jsonObj.message);
	               		return false;
	               	}

			    });
			}
		}
	});

	$("#canBtn").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/mypage/suspendView01.do"
		});
		var ncn = $("#ncn").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();
	});


});

function validatorCheck(){

	var cpStartDt = $("#cpStartDt").val();
	var cpEndDt = $("#cpEndDt").val();
	if(cpStartDt==""){
		MCP.alert("시작일을 입력해 주세요.",function(){
			$("#cpStartDt").focus();
		});
		return false;
	}
	if(cpEndDt==""){
		MCP.alert("종료일을 입력해 주세요.",function(){
			$("#cpEndDt").focus();
		});
		return false;
	}

	var secToday = 1000*60*60*24;
	var today = new Date().getTime(); // 25
	var cpStart = new Date($("#cpStartDt").val()).getTime();
	var cpEnd = new Date($("#cpEndDt").val()).getTime();
	var strBefore = (cpStart-today)/secToday;
	var endBefore = (cpEnd-today)/secToday;
	var between = (cpEnd-cpStart)/secToday;

	if(strBefore < -1){
		MCP.alert("시작일은 현재보다 이전일 수 없습니다.");
		var day = new Date().format("yyyy-MM-dd");
		$("#cpStartDt").val(day);
		return false;
	}
	if(endBefore < -1){
		MCP.alert("종료일은 현재보다 이전일 수 없습니다.");
		var day = new Date();
		day.setDate(day.getDate()+90);
		var setDay = day.format("yyyy-MM-dd");
		$("#cpEndDt").val(setDay);
		return false;
	}
	if(between > 90){
		MCP.alert("설정기간은 90일을 넘길 수 없습니다. 확인해 주세요.");
		var day = new Date();
		$("#cpStartDt").val(day.format("yyyy-MM-dd"));
		day.setDate(day.getDate()+90);
		$("#cpEndDt").val(day.format("yyyy-MM-dd"));
		return false;
	}

	var cpPwdInsert = $("#cpPwdInsert").val();
	if( cpPwdInsert !="" ){
		if(cpPwdInsert.length < 4) {
			MCP.alert("비밀번호는 4~8자리 숫자입니다.",function(){
				$("#cpPwdInsert").focus();
			});
			return false;
		}
	}

	return true;

}