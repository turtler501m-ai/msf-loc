$(document).ready(function (){

	combiYnChkYn();
	
	$("#ncn").on("change",function(){

		var varData = ajaxCommon.getSerializedData({
    				  contractNum : $("#ncn option:selected").val()
    	});

        ajaxCommon.getItem({
            id:'myRateViewAjax'
            ,cache:false
            ,url:'/m/mypage/socDescAjax.do'
            ,data: varData
            ,dataType:"json"
         }
         ,function(jsonObj){

			var data = jsonObj.mcpUserCntrMngDto;
			if(jsonObj.RESULT_CODE == 'S'){
				$("#inpHP").val(data.rateNm);
				combiYnChkYn();
			}
    	 });

	});


});

//결합 가능 상품 조회
function combiYnChkYn(){
	var varData = ajaxCommon.getSerializedData({
    				  contractNum : $("#ncn option:selected").val()
    });


    ajaxCommon.getItem({
        id:'combinationCheckAjax'
        ,cache:false
        ,url:'/m/content/combinationCheckAjax.do'
        ,data: varData
        ,dataType:"json"
     }
     ,function(jsonObj){
		
		var data = jsonObj.myCombinationResDto;
		
		if(jsonObj.RESULT_CODE == 'S'){
			if(data.statusYn == 'Y'){
				$(".combichkYn1").show();
				$(".combichkYn2").hide();
				$(".combichkYn3").hide();
				$(".combichkReg").hide();
			} else if(data.custTypeYn == 'Y' || jsonObj.userType == 'Y'){
				$(".combichkYn2").show();
				$(".combichkYn1").hide();
				$(".combichkYn3").hide();
				$(".combichkReg").hide();
			} else if(data.socYn == 'Y'){
				$(".combichkYn1").hide();
				$(".combichkYn2").hide();
				$(".combichkYn3").show();
				$(".combichkReg").hide();
			} else {
				$(".combichkYn1").hide();
				$(".combichkYn2").hide();
				$(".combichkYn3").hide();
				$("#combiYn").val("N");
				$(".combichkReg").show();
			}
		} else {
			MCP.alert(data.message);
		}
	 });
}

//결합내역 조회
function btn_combiPop(){


	var parameterData = ajaxCommon.getSerializedData({
		contractNum : $("#ncn option:selected").val()
	});

	openPage('pullPopupByPost','/m/content/myCombinationList.do',parameterData);

}

//결합신청페이지 이동
function btn_combiYn(){
	
	var url = '/m/content/myCombinationReq.do';
	ajaxCommon.createForm({
	    method:"post"
	    ,action:url
	});

	ajaxCommon.attachHiddenElement("combiYn",$("#combiYn").val());
	ajaxCommon.attachHiddenElement("ncn",$("#ncn option:selected").val());
	ajaxCommon.formSubmit();

	
}

//컨텐츠 소개
function btnRegDtl(param){
	openPage('termsInfoPop','/termsPop.do',param);
}

