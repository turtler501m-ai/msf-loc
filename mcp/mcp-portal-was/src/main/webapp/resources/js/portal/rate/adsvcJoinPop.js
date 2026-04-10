VALIDATE_NOT_EMPTY_MSG.txtPhoneF = "휴대폰번호 앞자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.txtPhoneM = "휴대폰번호 중간자리를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.txtPhoneL = "휴대폰번호 뒷자리를 입력해 주세요.";

$(document).ready(function (){

	// 숫자만 입력가능1
	$(".onlyNum").keyup(function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, ""));
		checkCtn();
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
			MCP.alert('정확한 정보를 입력해 주세요.', function (){
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
		
	if(20000 < amt ){
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
	
	var selParam = amt; 
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
		html +='<input class="c-input onlyNum" type="text"  id="phone1_'+num+'"  maxlength="3" placeholder="앞자리" title="앞자리 입력">';
		html +='<input class="c-input onlyNum" type="text"  id="phone2_'+num+'"  maxlength="4" placeholder="중간자리" title="중간자리 입력">';
		html +='<input class="c-input onlyNum" type="text"  id="phone3_'+num+'"  maxlength="4" placeholder="뒷자리" title="뒷자리 입력">';
		html +='</div>';
		html +='<button class="c-button" type="button"  onclick="btn_del('+num+')">';
		html +='<span class="c-hidden">삭제</span>';
		html +='<i class="c-icon c-icon--delete-ty2" aria-hidden="true"></i>';
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
	
	 var roamDay = $("#range").val();	 
	 var timeSel = $("#timeSel").val();
	 
	 if(roamDay  == ""){
		MCP.alert('로밍 시작일자와 종료일자를 입력해 주세요.', function (){
			$("#range").focus();
		}); 
		
		return false;
	}
	
	if(roamDay  != ""){
		var totalDt = $("#range").val().replaceAll("~","");
		var statDt = totalDt.replaceAll("-","").substring(0,8).trim();
		var endDt = totalDt.replaceAll("-","").substring(10,18).trim();

		if( $("#now").val() > statDt ){
			MCP.alert('시작일자는 오늘날짜보다 이전일수가 없습니다.', function (){
				$("#statDt").focus();
			}); 
			return false;	
		}
		
		if( statDt > $("#stDt").val()){
			MCP.alert('시작일자는 최대 2개월까지만 선택 가능합니다. ', function (){
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
	}

	 if(endDt  == ""){
		MCP.alert('로밍 종료일자를 입력해 주세요.', function (){
			$("#endDt").focus();
		}); 
		return false;
	}
	
	if(timeSel == "" || timeSel == null){
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
	var confirm = "부가서비스 신청하시겠습니까?"
	
	var couoponPrice = $("#couoponPrice").val();
	
	if(couoponPrice  == null && couoponPrice  == undefined){
		couoponPrice = "0";
	} else {
		couoponPrice =  $("#couoponPrice").val();
	}
	
	// 에러 문구 제거 
	$(".c-form__text").remove();
	$(".has-error").removeClass("has-error");
	
	validator.config={};
    
    validator.config['txtPhoneF'] = 'isNonEmpty';
    validator.config['txtPhoneM'] = 'isNonEmpty';
    validator.config['txtPhoneL'] = 'isNonEmpty';
               
    if(validator.validate()){
		
		//휴대폰번호 체크
		var strPhoneChk = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
		
		//입력한 휴대폰번호
		var phoneNum = $.trim($("#txtPhoneF").val() + "" + $("#txtPhoneM").val() + "" + $("#txtPhoneL").val());
		if(!strPhoneChk.test(phoneNum) || !(phoneNum.length == 11)){
			MCP.alert("연락가능 연락처를 올바르게 입력해 주세요.",function(){
	            viewErrorMgs($("#txtPhoneF"), "연락가능 연락처를 올바르게 입력해 주세요.");
	            $("#txtPhoneF").focus(); // 포커스
	        });
	    	return false;
    	}
	
		KTM.Confirm(confirm, function (){
			var varData1 = ajaxCommon.getSerializedData({
				ctn : phoneNum
		    });
		    
		    this.close();
		    KTM.LoadingSpinner.show();
	    	$.ajax({
			    id:'getNcnbyCtnAjax'
			    ,cache:false
			    ,url:'/rate/getNcnbyCtnAjax.do'
			    ,data: varData1
			    ,async : true
			    ,dataType:"json"
			    , success : function(rtnObj) {
					if(rtnObj.rtnCd == "0000") {
						var varData2 = ajaxCommon.getSerializedData({
							ncn : rtnObj.contractNum
					        ,soc : $("#rateAdsvcCd").val()
					        ,ftrNewParam : $("#ftrNewParam").val()
					        ,couoponPrice : couoponPrice
					    });
						KTM.LoadingSpinner.hide();
						KTM.LoadingSpinner.show();
						$.ajax({
						    id:'adsvcJoinAjax'
						    ,cache:false
						    ,url:'/rate/adsvcJoinAjax.do'
						    ,data: varData2
						    ,async : true
						    ,dataType:"json"
						    , success : function(jsonObj) {
								if (jsonObj.returnCode == "00") {
									MCP.alert("부가서비스 신청이 완료되었습니다.", function (){
										$('.c-icon--close').trigger('click');
										location.href = '/rate/adsvcGdncList.do';
									});
									//
								}else {
									if(jsonObj.RESULT_MSG) {
							         	MCP.alert(jsonObj.RESULT_MSG);
									}else if(jsonObj.message) {
										MCP.alert(jsonObj.message);
									}
						        }
								
								KTM.LoadingSpinner.hide();		       
							},error:function(){
								alert("오류가 발생했습니다.다시 시도해주세요.");
								KTM.LoadingSpinner.hide();
						
							}
							
						});
					
					} else {
						KTM.LoadingSpinner.hide();
						MCP.alert(rtnObj.rtnMsg, function(){
			            viewErrorMgs($("#txtPhoneF"), rtnObj.rtnMsg);
			            $("#txtPhoneF").focus(); // 포커스
				        });
					}
			 	}
		 	});
	 	});
 	} else {
    	var errId = validator.getErrorId();
    	MCP.alert(validator.getErrorMsg(),function (){
        	$selectObj = $("#"+errId);
        	viewErrorMgs($selectObj, validator.getErrorMsg());
        	$('#' + validator.id).focus(); // 포커스
    	});
    	$(this).prop("checked", "");
    }
	
}

//에러메시지 보여주기
var viewErrorMgs = function($thatObj, msg ) {
    if ($thatObj.hasClass("c-input") || $thatObj.hasClass("c-select")) {
        $thatObj.parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    } else if ($thatObj.hasClass("c-input--div2") || $thatObj.hasClass("c-input--div3") ) {
        $thatObj.parent().parent().removeClass('has-safe').addClass('has-error').after("<p class='c-form__text'>"+msg+"</p>");
    }
}

//포커스 자동옮기기
function nextFocus(obj, len, id){
    if($(obj).val().length >= len){
        $('#' + id).focus();
    }
}

function checkCtn() {
	var strPhoneChk = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
	
	var phoneF = $("#txtPhoneF").val();
	var phoneM = $("#txtPhoneM").val();
	var phoneL = $("#txtPhoneL").val();
	
	if(phoneF.length == 3
		&& phoneM.length == 4
		&& phoneL.length == 4) {
		var phoneNum = $.trim(phoneF + "" + phoneM + "" + phoneL);
		if(strPhoneChk.test(phoneNum)){
			$(".btnConfirm").attr('disabled', false);
	    	return;
    	}
	}
	
	$(".btnConfirm").attr('disabled', true);
}