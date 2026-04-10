$(document).ready(function (){

	// 숫자만 입력가능1
	$(".numOnly").keyup(function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
	}); 




});

//번호도용
function btn_numberReg(){
	
	var chk = $("input[name=chkAgree]:checked").val();
	if(!chk){
		MCP.alert('정보 제공에 동의해 주세요.', function (){
			$("#chkAgree").focus();
		}); 

		return false;
	} else{
		$("#ftrNewParam").val('Y');
		btn_reg();
	}
			
			
}


//불법수신차단 서비스 
function btn_tmReg(){
	
	var ftrNewParam="";
    var isValid = true
       
	$(".numberAddView").each(function(index){
		
		var num = index+1;

		var phone1 = $("#phone1_"+num).val();
		var phone2 = $("#phone2_"+num).val();
		var phone3 = $("#phone3_"+num).val();
		var phone = phone1+phone2+phone3;

		if(phone == ""){
			MCP.alert('정확한 정보를 입력해 주세요.', function (num){
				$("#phone1_"+num).focus();
			}); 
			
			isValid = false;
			return false;

		}
		
		if(isValid){
			if( phone1 !=""){
				ftrNewParam += phone+":";
			} else if(phone2 != ""){
				ftrNewParam += "*"+phone2+"*:";
			} else if(phone3 != ""){
				ftrNewParam += "*"+phone3+":";
			} else if(phone2 != "" && phone3 != ""){
				ftrNewParam += "*"+phone2+"*:"+"*"+phone3+":";
			}
		}
		
		return isValid;
					
	});
	
	
	$("#ftrNewParam").val(ftrNewParam);
	 
	if(isValid){
		btn_reg();
	}

	

}

//포인트관리
function btn_pointReg(){
	var amt = $("#pointAmt").val();
	
	if($("#pointAmt").val() == ""){
		MCP.alert('할인금액을 입력해 주세요.', function (){
			$("#pointAmt" ).focus();
		}); 
		
		return false;
	}	
	
	if(amt < 1000){
		MCP.alert('최소 1,000원부터 신청 가능합니다.', function (){
			$("#pointAmt" ).focus();
		}); 
		
		return false;
	}	
	
	if(20000  < amt){
		MCP.alert('최대 20,000원까지 신청가능합니다.', function (){
			$("#pointAmt" ).focus();
		}); 
		
		return false;
	}	
	
	if(amt > $("#totalPoint").val()){
		MCP.alert('할인 금액은 현재 보유중인 포인트보다 높을 수 없습니다.', function (){
			$("#pointAmt" ).focus();
		}); 
		
		return false;
	}
	
	if(amt  % 500 != 0){
		MCP.alert('할인 금액은 500 포인트 단위로 사용가능합니다.', function (){
			$("#pointAmt" ).focus();
		}); 
		
		return false;
	}
	
	var selParam = amt; //파라미터 확인 필요 예)1000:24:N
	$("#ftrNewParam").val(selParam);
	$("#couoponPrice").val(amt);
	
	if( $("#ftrNewParam").val() != ""){
		btn_reg();
	}		
}

 //번호추가
 function btn_numAdd(){
	
	var count = Number.parseInt($("#numCount").html());
	var html ='';
	
	
	if(50 > count){
		var num =Number.parseInt($("#numCount").html())+1;

		html +='<li class="c-list__item numberAddView" id="addNum_'+num+'">';
		html +='<div class="c-input-wrap c-input-wrap--flex u-mt--0">';
		html +='<input class="c-input c-input--h34 u-ta-center numOnly" type="tel"  id="phone1_'+num+'"  maxlength="3" placeholder="앞자리" title="앞자리 입력">';
		html +='<input class="c-input c-input--h34 u-ta-center numOnly" type="tel"  id="phone2_'+num+'"  maxlength="4" placeholder="중간자리" title="중간자리 입력">';
		html +='<input class="c-input c-input--h34 u-ta-center numOnly" type="tel"  id="phone3_'+num+'"  maxlength="4" placeholder="뒷자리" title="뒷자리 입력">';
		html +='</div>';
		html +='<button class="c-button" href="javascript:void(0);" onclick="btn_del('+num+')">';
		html +='<i class="c-icon c-icon--delete2" aria-hidden="true"></i>';
		html +='</button>';
		html +=' </li>';

		$("#numCount").html(num);
		$(".addNumber").append(html);
	} else {
		MCP.alert("최대 50개까지 차단 설정이 가능합니다.");
		return ;
	}
		
}

//x버튼 삭제  
function btn_del(index){
	$("#addNum_"+index).remove();
	var removeNum =Number.parseInt($("#numCount").html())-1;
	$("#numCount").html(removeNum);

}

//로밍신청
function btn_roamReg(){
	
	 var statDt = $("#statDt").val().replaceAll("-","");
	 var endDt = $("#endDt").val().replaceAll("-","");
	 var timeSel = $("#timeSel").val();
	 
	 if(statDt  == ""){
		MCP.alert('로밍 시작일자를 입력해 주세요.', function (){
			$("#statDt").focus();
		}); 
		
		return false;
	}
	
	 if(endDt  == ""){
		MCP.alert('로밍 종료일자를 입력해 주세요.', function (){
			$("#endDt").focus();
		}); 
		return false;
	}
	
	if( statDt > $("#stDt").val()){
		MCP.alert('시작일자는 최대 2개월까지만 선택 가능합니다. ', function (){
			$("#statDt").focus();
		}); 
		
		return false;
	}
	
	if(endDt > $("#etDt").val()){
		MCP.alert('종료일자는 최대 6개월까지만 선택 가능합니다. ', function (){
			$("#endDt").focus();
		}); 
		
		return false;
	}
		
	if(endDt <= statDt){
		MCP.alert('종료일자가 시작일자보다 이전일수가 없습니다.', function (){
			$("#endDt").focus();
		}); 
		
		return false;	
	}
	if(timeSel == "" ||  timeSel == null){
		MCP.alert('시작 시각을 선택해 주세요.', function (){
			$("#timeSel").focus();
		}); 
		
		return false;
	}
	
	if( $("#now").val() ==  statDt ){
		if($("#todayTime").val() >= timeSel){
			MCP.alert('시작일자가 당일이면 현재시간 이후로 선택해주세요.', function (){
				$("#timeSel").focus();
			}); 
			
			return false;
		}
	}
 
	if(statDt < $("#now").val()){
		MCP.alert('시작일자는 오늘날짜보다 이전일수가 없습니다.', function (){
			$("#statDt").focus();
		}); 
		
		return false;	
	}
	
	if(endDt <= statDt){
		MCP.alert('종료일자가 시작일자보다 이전일수가 없습니다.', function (){
			$("#endDt").focus();
		}); 
		
		return false;	
	}
	
	var selParam = statDt + timeSel +":" + endDt;
	 $("#ftrNewParam").val(selParam);

	
	if( $("#ftrNewParam").val() != ""){
		btn_reg();
	}		

}

//취소
function btn_cancel(){
	$('.c-icon--close').trigger('click');
}

//부가서비스 신청
function btn_reg(){
	var confirm = "";

	if($("#flag").val() == "Y"){
		confirm  = "부가서비스를 변경하시겠습니까?";
	}else{
		confirm= "부가서비스 신청하시겠습니까?";
	}

	var couoponPrice = $("#couoponPrice").val();
	
	if(couoponPrice  == null && couoponPrice  == undefined){
		couoponPrice = "0";
	} else {
		couoponPrice =  $("#couoponPrice").val();
	}
	
	KTM.Confirm(confirm, function (){

		var ncnVal = "";

		if ($("#ctn option:selected").length == 0) {
			ncnVal = $("#contractNum").val();
		} else {
			ncnVal = $("#ctn option:selected").val();
		}

		var varData = ajaxCommon.getSerializedData({
	        ncn:ncnVal
	        ,soc :$("#rateAdsvcCd").val()
	        ,ftrNewParam : $("#ftrNewParam").val()
	        ,couoponPrice : couoponPrice
 	    });
	
	   KTM.LoadingSpinner.show();
	   $.ajax({
	        id:'regSvcChgAjax'
	        ,cache:false
	        ,url:'/m/mypage/regSvcChgAjax.do'
	        ,data: varData
	        ,dataType:"json"
	        ,success : function(jsonObj) {
			
			if (jsonObj.returnCode == "00") {
					var msg = "";
					if($("#flag").val() == "Y"){
						msg = "부가서비스 변경이 완료되었습니다.";
					}else{
						msg = "부가서비스 신청이 완료되었습니다.";
					}
					MCP.alert(msg, function (){
						this.close();
						$('.c-icon--close').trigger('click');
						regSvcSearch();
					});
				}else {
		         	MCP.alert(jsonObj.message);
		        }
		   		KTM.LoadingSpinner.hide();
	   		},error:function(){
				alert("오류가 발생했습니다.다시 시도해주세요.");
				KTM.LoadingSpinner.hide();
				
			}
	    });
	    
	
		this.close();
	});
	
}