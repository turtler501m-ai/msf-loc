
$(document).ready(function(){

	$("#billTypeCd").change(function(){
		var billTypeCd = $(this).val();
		if(billTypeCd=="1"){
			$(".eArea").show();
			$(".moArea").hide();
		} else {
			$(".eArea").hide();
			$(".moArea").show();
		}
	});

	$("#applyBtn").click(function(){
		var billTypeCd = $("#billTypeCd option:selected").val();
		var email = ajaxCommon.isNullNvl($("#email").val(),"");
		if(billTypeCd=="1"){ // 이메일
			if(email==""){
				MCP.alert("발송받으실 이메일을 넣어주세요.");
				return false;
			}

			// 정규식 - 이메일 유효성 검사
	        var regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
	        if(!regEmail.test(email)){
				MCP.alert("이메일주소를 이메일형식에 맞게 입력해 주세요.");
				return false;
			}
		}

		var ncn = $("#selNcn").val();
		var thisMonth = $("#thisMonth").val();
		var varData = ajaxCommon.getSerializedData({
			ncn : ncn,
			thisMonth : thisMonth,
			billTypeCd : billTypeCd,
			email : email
	    });

		ajaxCommon.getItem({
	        id:'billWayReSendAjax'
	        ,cache:false
	        ,url:'/mypage/billWayReSendAjax.do'
	        ,data: varData
	        ,dataType:"json"
	     }
	     ,function(jsonObj){
	    	 var returnCode =  jsonObj.returnCode;
	    	 if(returnCode=="00"){
	    		 MCP.alert("명세서 재발송 신청이 완료되었습니다.",function(){
	    			 KTM.Dialog.closeAll();
	    		 });
	    	 } else {
	    		 MCP.alert("명세서 재발송 신청이 실패하였습니다.<br/>잠시 후 다시 시도하시거나 <br/>고객센터(1899-5000)으로 문의 부탁드립니다.",function(){
	    			 KTM.Dialog.closeAll();
	    		 });
	    	 }

    		 return false;
	     });
	});

});
