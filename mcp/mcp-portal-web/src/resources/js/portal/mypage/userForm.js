var dimHtml = '<div style="position: fixed; width: 100%; height: 100%; left: 0px; top: 0px; background-color: rgba(0, 0, 0, 0.6); z-index: 101;" id="dim"></div>';

$(document).on("keyup", "input:text[numberOnly]", function() {$(this).val( $(this).val().replace(/[^0-9]/gi,"") );});//숫자만입력

var myScroll;
window.onload = function(){
	if($("#status").val() == 'F'){

		/*$("#btnCheck").val('modify');
		$("#pw_confirm-dialog").addClass("is-active");
		$("body").append(dimHtml);*/
	}else if($("#status").val() == 'S'){
		$("#snsBut").get(0).click();
/*		
		$("#btnCheck").val('sns');
		$("#pw_confirm-dialog").addClass("is-active");
		$("body").append(dimHtml);*/
	}
}
$(document).ready(function() {
	$(document).on("click", "#pw_modal-cancelBut", function(){
		$("#dim").remove();
	});
	

	$(document).on("click","#pw_modal-cancelBut, .c-icon--close",function(){


		var el = document.querySelector("#pw_confirm-dialog");
		var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
		modal.close();
	});
	
	 //검색 엔터
    $("#inpB").keydown(function (key) {
        if (key.keyCode == 13) {
            $("#pw_modal-okBut").trigger("click");
        }
    });
    
 /*   $("#popup").on("click", function(){
	openPage('pullPopup', '/retention/retentionInfoView.do', '');
});*/
});

$("#certifyUser").on("click", function(){
	location.href = "/mypage/multiPhoneLine.do";
});

function btnCheck(txt){
	$("#btnCheck").val(txt);
}

function pwCheck(){


	if($("#inpB").val()==""||$("#inpB").val()==null){
		MCP.alert("비밀번호를 입력해 주세요.");
		$("#inpB").focus();
		return;
	}

	 var varData = ajaxCommon.getSerializedData({
   		 "id": $("#userId").val()
		,"pw":$("#inpB").val()
		,"status":"check"
	 });


	ajaxCommon.getItem({
	    id:'getCommendState'
	    ,cache:false
	    ,async:false
	    ,url:'/mypage/userPwCheckAjax.do'
	    ,data: varData
	    ,dataType:"json"
	}
	,function(data){

        	if (data.returnCode=="00"){
				if($("#btnCheck").val() == 'sns'){
					location.href = '/mypage/userSnsList.do';
				}else{
					location.href = '/mypage/userInfoView.do';
				}


        	}else if(data.returnCode == "98") {
                location.href="/errorPage.do";
            } else{
        		MCP.alert(data.message);
        	}
    });


}



