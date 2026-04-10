$(document).ready(function (){

	$("#ncn").on("change",function(){
			ajaxCommon.createForm({
			    method:"post"
			    ,action:"/m/content/mySharingView.do"
			 });
			
			ajaxCommon.attachHiddenElement("menuType",$("#menuType").val());
			ajaxCommon.attachHiddenElement("ncn",$("#ncn option:selected").val());
			ajaxCommon.formSubmit();

	});


});



function btnRegDtl(param){
	openPage('termsInfoPop','/termsPop.do',param);
}

//데이터 쉐어링결합하기
function btn_sharingReg(){

	ajaxCommon.createForm({
		    method:"post"
		    ,action:"/m/content/reqSharingView.do"
	});

	ajaxCommon.attachHiddenElement("opmdSvcNo",	 $("#opmdSvcNo").val());
	ajaxCommon.attachHiddenElement("contractNum",$("#ncn option:selected").val());
	ajaxCommon.formSubmit();

}

