$(document).ready(function(){

	// 쿠폰 등록
	$("#dtlRegBtn").click(function(){

		var coupSerialNo = ajaxCommon.isNullNvl($("#dtlCoupSerialNo").val(),"");
    	coupSerialNo = coupSerialNo.replace(/\-/g,'').toUpperCase();
    	if(coupSerialNo==""){
    		MCP.alert("쿠폰번호가 없습니다.다시조회해 주세요.",function(){
    			$("#coupSerialNo").focus();
    		});
    		return false;
    	}

    	var ncn = $("#dtlNcn").val();
    	if(!confirm("쿠폰을 등록하시겠습니까?")){
    		return false;
    	}

    	var varData = ajaxCommon.getSerializedData({
    		coupSerialNo:$.trim(coupSerialNo)
    		,ncn : ncn
	    });

    	ajaxCommon.getItem({
	        id:'couponRegAjax'
	        ,cache:false
	        ,url:'/mypage/couponRegAjax.do'
	        ,data: varData
	        ,dataType:"json"
	    }
	    ,function(data){
	    	var rtnCode = data.rtnCode;
    		var rtnMsg = data.rtnMsg;
    		if( rtnCode == "0000" ){
    			MCP.alert("쿠폰 등록에 성공 했습니다.",function(){
    				ajaxCommon.createForm({
    				    method:"post"
    				    ,action:"/m/mypage/couponUsedDataList.do"
    				});
    				var ncn = $("#dtlNcn").val();
    				ajaxCommon.attachHiddenElement("ncn",ncn);
    				ajaxCommon.formSubmit();
    			});
    			return false;
    		} else if( rtnCode == "999999" ){
    			location.href="/loginForm.do";
    			return false;
    		} else {
    			MCP.alert(rtnMsg,function(){
    				$("#dtlPopClose").trigger("click");
    			});
    			return false;
    		}
    	});
	});


});



