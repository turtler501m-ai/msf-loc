;

$(document).ready(function () {
	
	$("#counselBtn").click(function(){
    	
    	var acceptTxt = $("#acceptTxt").is(":checked");
    	if(!acceptTxt){
    		alert("개인정보 수집/이용에 동의하여 주시기 바랍니다.");
    		$("#acceptTxt").focus();
    		return false;
    	}    	    	        
    	
    	var counselNm = ajaxCommon.isNullNvl($("#counselNm").val(),"");
    	var counselCtn = ajaxCommon.isNullNvl($("#counselCtn").val(),"");
    	var counselMemo = ajaxCommon.isNullNvl($("#counselMemo").val(),"");
    	if(counselNm==""){    		
    		alert("성명을 입력해 주세요.");
    		$("#counselNm").focus();
    		return false;
    	}
    	if(counselCtn==""){    		
    		alert("연락처를 입력해 주세요.");
    		$("#counselCtn").focus();
    		return false;
    	}    	      
    	
    	 var varData = ajaxCommon.getSerializedData({
         	wireProdDtlSeq:""
             ,counselNm:counselNm
             ,counselCtn:counselCtn
             ,counselMemo:counselMemo	             
         });
         
      	ajaxCommon.getItem({
             id:'wireProdJoinAjax'
             ,cache:false
             ,url:"/wire/wireProdSimpleJoinAjax.do"	      
            ,data: varData
             ,dataType:"json"
             ,loading:false
         }
     	,function(jsonObj) {
     		if (jsonObj.RESULT_CODE == "00000") {    
     			layerPopOpen();
     			fn_layerPop('conselApplyPop',650,850);
     			$("#counselNm").val("");
     			$("#counselCtn").val("");
     			$("#counselMemo").val("");
     			$("#acceptTxt").prop("checked",false);
     		} else {
     			alert(jsonObj.RESULT_MSG);
     		}    	
         });            	
    });
	
	
});

function layerPopOpen(){
	
	var dimHtml = "";
	dimHtml +="<div class='dim-layer'>";
	dimHtml +="<div class='dimBg'></div>";
	var layerHtml = "";
	layerHtml += "<div id='conselApplyPop' class='pop-layer'>";
	layerHtml += "<div class='pop-container'>";
	layerHtml += "<div class='pop-conts'>";
	layerHtml += "<div class='popup_top'>";
	layerHtml += "<h4 class='popup_title'>간편가입상담</h4>";
	layerHtml += "</div>";
	layerHtml += "<div class='popup_content'>";
	layerHtml += "<p>간편가입 상담신청에 감사 드립니다.</p>";
	layerHtml += "<p>고객센터(1899-0851/1899-4059)에서 연락드릴 예정입니다.</p>";
	layerHtml += "</br>";
	layerHtml += "<p>고객님의 관심에 다시 한 번 감사 드립니다.</p>";
	layerHtml += "<div class='popBtnCt popup_cancel'>";
	layerHtml += "<a href='javascript:;' class='btnRed'>확인</a>";
	layerHtml += "</div>";
	layerHtml += "</div>";
	layerHtml += "</div>";
	layerHtml += "</div>";
	layerHtml += "<a href='javascript:;' class='popup_cancel'>"; 
	layerHtml += "<img src='/resources/images/front/layer_cancel.png' alt='close' />";
	layerHtml += "</a>";
	layerHtml += "</div>";
	/* </div> */				
	var isDim = $('.dim-layer').children().hasClass('dimBg');
	if(isDim){ // 기존 화면에 레이어 영역이 있을때			
		var isLayer = $("#conselApplyPop").length;
		if(isLayer >= 1){			
			return;
		} else {			
			$(".dim-layer").append(layerHtml);	
		}																					
	} else {// 기존 화면에 레이어 영역이 없을때			
		var viewHtml = dimHtml+layerHtml+"</div>";
		$("#wireContainer").append(viewHtml);			
	}				
} 

