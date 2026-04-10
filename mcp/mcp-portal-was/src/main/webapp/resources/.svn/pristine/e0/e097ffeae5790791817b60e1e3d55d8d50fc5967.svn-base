$(document).ready(function(){

	$("#coupSerialNo").keyup(function(){

		var val = $(this).val();
		if(val !="" && val.length > 10){
			$("#copNumSrchBtn").prop("disabled",false);
		} else {
			$("#copNumSrchBtn").prop("disabled",true);
		}
	});


	$("#copNumSrchBtn").click(function(){

		var coupSerialNo = $("#coupSerialNo").val();
		if(coupSerialNo ==""){
			MCP.alert("발급된 쿠폰번호를 입력해 주세요.",function(){
				$("#coupSerialNo").focus();
			});
			return false;
		}
		coupSerialNo = coupSerialNo.replace(/\-/g,'').toUpperCase();
		var varData = ajaxCommon.getSerializedData({
			coupSerialNo:$.trim(coupSerialNo)
	    });

	    ajaxCommon.getItem({
	        id:'couponDataAjax'
	        ,cache:false
	        ,url:'/mypage/couponDataAjax.do'
	        ,data: varData
	        ,dataType:"json"
	    }
	    ,function(data){
	    	$("#divArea").html("");
			var rtnCode = data.rtnCode;
	    	var rtnMsg = data.rtnMsg;
	    	var coupInfoList = data.coupInfoList;
	    	var couponHtml = "";
	    	if(rtnCode=="999999"){
	    		location.href="/loginForm.do";
				return false;
	    	} else if(rtnCode=="0000"){

	    		if( coupInfoList !=null && coupInfoList.length > 0 ){

	    			var mobileNo = $("#mobileNo").val();
	    			var coupNm = coupInfoList[0].coupNm;
	        		var coupSerialNo = coupInfoList[0].coupSerialNo;
	        		if(coupSerialNo !="" && coupSerialNo.length > 14){
	        			coupSerialNo = coupSerialNo.substring(0,4)+"-"+coupSerialNo.substring(4,8)+"-"+coupSerialNo.substring(8,12)+"-"+coupSerialNo.substring(12,15);
	    			}
	        		var useStrtDt = coupInfoList[0].useStrtDt;
	        		if(useStrtDt !="" && useStrtDt.length >13 ){
	        			useStrtDt = useStrtDt.substring(0,4)+"."+useStrtDt.substring(4,6)+"."+useStrtDt.substring(6,8);
	        		}
	        		var useEndDt = coupInfoList[0].useEndDt;
	        		if(useEndDt !="" && useEndDt.length >13 ){
	        			useEndDt = useEndDt.substring(0,4)+"."+useEndDt.substring(4,6)+"."+useEndDt.substring(6,8);
	        		}
	        		couponHtml += "<div class='c-table c-table--row u-mt--40'>";
	        		couponHtml += "	<table>";
	        		couponHtml += "		<caption>쿠폰등록 열람</caption>";
	        		couponHtml += "		<colgroup>";
	        		couponHtml += "			<col style='width: 32%'>";
	        		couponHtml += "			<col style='width: 68%'>";
	        		couponHtml += "		</colgroup>";
	        		couponHtml += "		<tbody>";
	        		couponHtml += "			<tr>";
	        		couponHtml += "				<th class='u-ta-left' scope='row'>쿠폰명</th>";
	        		couponHtml += "				<td class='u-ta-left'>"+coupNm+"</td>";
	        		couponHtml += "			</tr>";
	        		couponHtml += "			<tr>";
	        		couponHtml += "				<th class='u-ta-left' scope='row'>회선 번호</th>";
	        		couponHtml += "				<td class='u-ta-left'>"+mobileNo+"</td>";
	        		couponHtml += "			</tr>";
	        		couponHtml += "			<tr>";
	        		couponHtml += "				<th class='u-ta-left' scope='row'>등록 유효기간</th>";
	        		couponHtml += "				<td class='u-ta-left'>"+useStrtDt+"~"+useEndDt+"</td>";
	        		couponHtml += "			</tr>";
	        		couponHtml += "			<tr>";
	        		couponHtml += "				<th class='u-ta-left' scope='row'>사용가능 기간</th>";
	        		couponHtml += "				<td class='u-ta-left'>신청 즉시</td>";
	        		couponHtml += "			</tr>";
	        		couponHtml += "		</tbody>";
	        		couponHtml += "	</table>";
	        		couponHtml += "</div>";
	        		$("#regBtn").prop("disabled",false);
	        		$("#regChk").val("Y");
	        	} else {
	        		couponHtml += "<div class='c-nodata'>";
	        		couponHtml += "	<i class='c-icon c-icon--nodata' aria-hidden='true'></i>";
	        		couponHtml += "	<p class='c-noadat__text'>유효하지 않은 쿠폰번호 입니다.</p>";
	        		couponHtml += "</div>";
	        		$("#regBtn").prop("disabled",true);
	        		$("#regChk").val("");
	        	}
	    	} else {
	    		couponHtml += "<div class='c-nodata'>";
        		couponHtml += "	<i class='c-icon c-icon--nodata' aria-hidden='true'></i>";
        		couponHtml += "	<p class='c-noadat__text'>유효하지 않은 쿠폰번호 입니다.</p>";
        		couponHtml += "</div>";
	    		$("#regBtn").prop("disabled",true);
	    		$("#regChk").val("");
	    	}
	    	$("#divArea").html(couponHtml);
	    });

	});

	// 쿠폰 등록
	$("#regBtn").click(function(){

		var coupSerialNo = ajaxCommon.isNullNvl($("#coupSerialNo").val(),"");
    	coupSerialNo = coupSerialNo.replace(/\-/g,'').toUpperCase();
    	if(coupSerialNo==""){
    		MCP.alert("쿠폰번호를 입력해 주세요.!",function(){
    			$("#coupSerialNo").focus();
    		});
    		return false;
    	}

		var regChk = $("#regChk").val();
    	if( regChk !="Y" ){
    		MCP.alert("쿠폰 번호를 조회해 주세요.",function(){
    			$("#copNumSrchBtn").focus();
    		});
    		return false;
    	}

    	var ncn = $("#ncn").val();
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
    				var ncn = $("#ncn").val();
    				ajaxCommon.attachHiddenElement("ncn",ncn);
    				ajaxCommon.formSubmit();
    			});
    			return false;
    		} else if( rtnCode == "999999" ){
    			location.href="/loginForm.do";
    			return false;
    		} else {
    			MCP.alert(rtnMsg,function(){
    				$("#popClose").trigger("click");
    			});
    			return false;
    		}
    	});
	});


});



