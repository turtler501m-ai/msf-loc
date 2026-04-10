;

var wireProdAllList = null; //유선 전체 상품 목록

VALIDATE_NOT_EMPTY_MSG = {};
VALIDATE_NOT_EMPTY_MSG.counselNm = "성명을 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrTelFn = "연락처를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrTelMn = "연락처를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.cstmrTelRn = "연락처를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.joinCorp = "관심상품을 선택해 주세요.";
VALIDATE_NOT_EMPTY_MSG.joinProdCd = "관심상품을 선택해 주세요.";
VALIDATE_NOT_EMPTY_MSG.joinProdDtlSeq = "관심상품을 선택해 주세요.";
VALIDATE_NOT_EMPTY_MSG.secureChr = "보안문자를 입력해 주세요.";
VALIDATE_NOT_EMPTY_MSG.acceptTxt = "개인정보 수집/이용에 동의하여 주시기 바랍니다.";

$(document).ready(function() {
	
	//전체 상품 목록 조회
	getAllProdList();
	
	var joinCorpText = $("#joinCorp option:eq(0)").text();
	var joinProdCdText = $("#joinProdCd option:eq(0)").text();
	var joinProdDtlSeqText = $("#joinProdDtlSeq option:eq(0)").text();
	
	//신청하기
	$(document).on("click", ".joinBtn", function() {
		changeCaptcha();
		$(".dim-layer").removeClass('redPopup').addClass('blkPopup');
		fn_layerPop('joinLayer', 700, 400);		
	
	});
	
   	//신청하기 > 사업자 선택
 	$(document).on("change","#joinCorp", function() {
 		var joinCorp = $(this).val();
 		$("#joinProdCd").html("");
 		$("#joinProdCd").append("<option value=''>"+ joinProdCdText +"</option>");
 		$("#joinProdDtlSeq").html("");
 		$("#joinProdDtlSeq").append("<option value=''>"+ joinProdDtlSeqText +"</option>");      
 		
 		var wireProdCdTmp = [];
 		$.each(wireProdAllList, function(i, item) {
			if (joinCorp == item.wireProdCorp) {
				if (wireProdCdTmp.indexOf(item.wireProdCd) == -1) {
					wireProdCdTmp.push(item.wireProdCd);
					$("#joinProdCd").append("<option value='"+ item.wireProdCd +"'>"+item.wireProdCdName+"</option>");
				}
			}	            			
 		});
 		
 		$("#joinProdDtlSeq").trigger("change");
 	});
 	
 	//신청하기 > 사업자 선택 > 관심상품
 	$(document).on("change","#joinProdCd", function() {
 		var joinProdCd = $(this).val();
 		$("#joinProdDtlSeq").html("");
 		$("#joinProdDtlSeq").append("<option value=''>"+ joinProdDtlSeqText +"</option>");           
 		$.each(wireProdAllList, function(i, item) {
 			if (item.wireProdCorp ==  $("#joinCorp").val() && item.wireProdCd ==  $("#joinProdCd").val()) {
 				$("#joinProdDtlSeq").append("<option value='"+ item.wireProdDtlSeq +"'>"+item.wireProdName+"</option>");
 			}
 		});
 		
 		$("#joinProdDtlSeq").trigger("change");
 	});
 	
 	//신청하기 > 사업자 선택 > 관심상품 > 상품명
 	$(document).on("change","#joinProdDtlSeq", function() {
 		var joinProdDtlSeq = $(this).val();
 	    if (joinProdDtlSeq != "") {
     		$.each(wireProdAllList, function(i, item) {
     			if (item.wireProdDtlSeq == joinProdDtlSeq) {
     				joinProdInfoView(item);
     				return false;
     			}
     		}); 
 	    } else {
 	    	$("#joinAmtDesc").text("");
 	    	$(".wishPdtBox > .pdtInfo").hide();
 	    }
 	});
 	
 	//'새로고침'버튼의 Click 이벤트 발생시 'changeCaptcha()'호출
	$('#reLoad').click(function() {
		changeCaptcha(); 
	}); 
	
	//한글음성듣기 버튼에 클릭이벤트 등록
	$('#soundOnKor').click(function() {
		audioCaptcha();
	}); 
	
	//신청
	$("#layerJoinBtn").click(function() {
		
		validator.config={};
        validator.config['joinCorp'] = 'isNonEmpty';
        validator.config['joinProdCd'] = 'isNonEmpty';
        validator.config['joinProdDtlSeq'] = 'isNonEmpty';     
        validator.config['counselNm'] = 'isNonEmpty';     
        validator.config['cstmrTelFn'] = 'isNonEmpty';     
        validator.config['cstmrTelMn'] = 'isNonEmpty';     
        validator.config['cstmrTelRn'] = 'isNonEmpty';     
        validator.config['secureChr'] = 'isNonEmpty';     
        validator.config['acceptTxt'] = 'isNonEmpty';     
        
        if (!validator.validate()) { 
        	 alert(validator.getErrorMsg());
        	 return;
        }
        
        
        var varData = ajaxCommon.getSerializedData({
        	wireProdDtlSeq:$("#joinProdDtlSeq").val()
            ,counselNm:$("#counselNm").val()
            ,counselCtn:$("#cstmrTelFn").val() + $("#cstmrTelMn").val() + $("#cstmrTelRn").val()
            ,counselMemo:$("#counselMemo").val()
            ,secureChr:$("#secureChr").val()
        });
        
		gtag('event', 'wireProdJoinComplete', {
			  'event_label': 'wireProdJoinComplete',
			  'event_category': 'wireProdJoinComplete'
			});
        
     	ajaxCommon.getItem({
            id:'wireProdJoinAjax'
            ,cache:false
            ,url:"/wire/wireProdJoinAjax.do"	      
           	,data: varData
            ,dataType:"json"
            ,loading:false
        }
    	,function(jsonObj) {
    		if (jsonObj.RESULT_CODE == "00000") {
    			alert("간편가입상담 신청 완료 되었습니다.");   
    			$("#joinFrm")[0].reset();
    			$("#joinCorp").trigger("change");   
    			$("#joinLayer > .popup_cancel").trigger("click");
    		} else {
    			alert(jsonObj.RESULT_MSG);
    		}    	
        });
        
	});
	
});


var getAllProdList = function() {     	 
   	
   	ajaxCommon.getItem({
        id:'wireProdListAjax'
        ,cache:false
        ,url:"/wire/wireProdListAjax.do"	           	
        ,dataType:"json"
        ,loading:false
    }
	,function(jsonObj){
		wireProdAllList = jsonObj.nmcpWireProdDtlDtoList;	
		$("#joinCorp").trigger("change");   
    });
   	
}
	
//신청 상품 정보 
var joinProdInfoView = function(obj) {    
	
	$(".wishPdtBox > .pdtInfo").hide();
	if (obj.wireProdAmtType == "M") {
		$("#joinAmt > .addTxt").text($.number(obj.wireProdAmt) + "원");            		
	} else {
		$("#joinAmt > .addTxt").text(obj.wireProdAmtText);            
	}
	$("#joinAmt").show(); //요금
	
	if (obj.wireProdFreebies != "") {
		$("#joinFreebies").show(); //사은품
		$("#joinFreebies > .addTxt").text(obj.wireProdFreebies);
	}
	
	if (obj.wireProdCd == "IT01" || obj.wireProdCd == "CB01") {
		$("#joinSpeed").show(); //인터넷 속도
		if (obj.wireProdSpeed < 1000) {
			$("#joinSpeed > .addTxt").text(obj.wireProdSpeed + "MBbps");
		} else {
			$("#joinSpeed > .addTxt").text(obj.wireProdSpeed/1000 + "GBbps");
		}
	} 
	
	if (obj.wireProdCd == "TV01" || obj.wireProdCd == "CB01") {
		$("#joinChn").show(); //채널 갯수
		$("#joinChn > .addTxt").text("총"+ obj.wireProdChannel+"개");
	}
	
	if (obj.wireProdAmtDesc != "") {
		$("#joinAmtDesc").text("("+obj.wireProdAmtDesc+")");
	} else {
		$("#joinAmtDesc").text("");
	}
		
	$("#joinProdInfo").show();
}

 var audioCaptcha = function() {
 	var uAgent = navigator.userAgent;
 	var soundUrl = "/callCaptChaAudioByKor.do?fake="+new Date();
 	if (uAgent.indexOf('Trident') > -1 || uAgent.indexOf('MSIE') > -1) {
 		winPlayer(soundUrl);
 	} else if (!!document.createElement('audio').canPlayType) {
 		try {
 			new Audio(soundUrl).play();
 		} catch(e) {
 			winPlayer(soundUrl);
 		}
 	}
 }
	
 function winPlayer(objUrl) {
 	var uAgent = navigator.userAgent;
 	if (uAgent.indexOf('Trident') > -1 || uAgent.indexOf('MSIE') > -1) {
 		$('#audiocatpch').html('<embed src="'+ objUrl +'" type="audio/wav">');
 	} else {
 		$('#audiocatpch').html(' <bgsound src="' + objUrl + '">');
 	}
 }
 
 function changeCaptcha() {
	  //IE에서 '새로고침'버튼을 클릭시 CaptChaImg.jsp가 호출되지 않는 문제를 해결하기 위해 "?rand='+ Math.random()" 추가
		rand = Math.random();
		$('#catpcha').html('<img src="/CaptChaImg.do?rand='+ rand + '" alt="보안인증확인문자"/>');
	 }
 