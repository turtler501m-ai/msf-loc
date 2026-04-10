

$(document).ready(function (){

	window.onpopstate = function (event){
		var ncn = $("#ncn").val();
		var state = { 'ncn': ncn};
		history.pushState(state, null,location.href);
		history.go("/m/mypage/suspendView01.do");
	}

	$("#applyBtn").click(function(){

		if(confirm("일시정지를 해제 하시겠습니까?")) {

			var ncn = $("#ncn").val();
			var varData = ajaxCommon.getSerializedData({
				strNcn : ncn,
				cpPwdInsert : $("#cpPwdInsert").val()
		    });

		    ajaxCommon.getItem({
		        id:'suspenCnlChgInAjax'
		        ,cache:false
		        ,url:'/mypage/suspenCnlChgInAjax.do'
		        ,data: varData
		        ,dataType:"json"
		        ,async:false
		    }
		    ,function(jsonObj){


		    	if(jsonObj.returnCode == "00000") {

		    		var gubun = "A";
		    		var reasonCode = $("#sndarvStatCd").val();
					var subStatusDate = $("#subStatusDate").val().replace(/[^0-9]/gi, "");

		    		ajaxCommon.createForm({
		    		    method:"post"
		    		    ,action:"/m/mypage/suspendView03.do"
		    		});

		    		ajaxCommon.attachHiddenElement("gubun",gubun);
		    		ajaxCommon.attachHiddenElement("ncn",ncn);
		    		ajaxCommon.attachHiddenElement("reasonCode",reasonCode);
		    		ajaxCommon.attachHiddenElement("subStatusDate",subStatusDate);
		    		ajaxCommon.formSubmit();
               	} else {
               		MCP.alert(jsonObj.message);
               		return false;
               	}
		    });
		}
	});

	$("#cnlBtn").click(function(){
		ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/mypage/suspendView01.do"
		});
		var ncn = $("#ncn").val();
		ajaxCommon.attachHiddenElement("ncn",ncn);
		ajaxCommon.formSubmit();

	});

});
