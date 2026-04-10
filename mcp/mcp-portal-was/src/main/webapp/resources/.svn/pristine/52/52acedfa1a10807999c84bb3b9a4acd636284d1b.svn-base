
$(document).on("keyup", "input:text[numberOnly]", function() {$(this).val( $(this).val().replace(/[^0-9]/gi,"") );});//숫자만입력

var myScroll;
$(document).ready(function() {
	if($("#status").val() == 'F'){
		/*$(".c-button--mint").trigger("click");*/
		$("#btnCheck").val('modify');
		$("#pw_confirm-dialog").addClass("is-active");
	}else if($("#status").val() == 'S'){
		/*$(".c-button--mint--type2").trigger("click");*/
		$("#btnCheck").val('sns');
		$("#pw_confirm-dialog").addClass("is-active");
	}

	$(document).on("click",".c-button--gray, .c-icon--close",function(){
		/*$(".c-modal").removeClass("is-active");*/

		var el = document.querySelector("#pw_confirm-dialog");
		var modal = KTM.Dialog.getInstance(el) ? KTM.Dialog.getInstance(el) : new KTM.Dialog(el);
		modal.close();
	});
});

$(".certifyUser").on("click", function(){
	location.href = "/m/mypage/multiPhoneLine.do";
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
	    ,url:'userPwCheckAjax.do'
	    ,data: varData
	    ,dataType:"json"
	}
	,function(data){

        	if (data.returnCode=="00"){
				if($("#btnCheck").val() == 'sns'){
					location.href = '/m/mypage/userSnsList.do';
				}else{
					location.href = '/m/mypage/userInfoView.do';
				}


        	}else if(data.returnCode == "98") {
                location.href="/m/errorPage.do";
            } else{
        		MCP.alert(data.message);
        	}
    });


}


$(".c-buttom--sm").on("click", function(){
	location.href="/m/mypage/mutiPhoneLine.do";
})


