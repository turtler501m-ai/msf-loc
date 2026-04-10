$(document).ready(function (){

	$("#btnReg").addClass("is-disabled");
	$("#ncn").on("change",function(){

		ajaxCommon.createForm({
			    method:"post"
			    ,action:"/m/content/myCombinationView.do"
		});

		ajaxCommon.attachHiddenElement("ncn",$("#ncn option:selected").val());
		ajaxCommon.formSubmit();

	});


});


//ceheckbox 이벤트
function chkEvent(){
	let chk = $("input[name=chkAgree]:checked").val();
	 
	 if(!chk){
		$('#targetTerms').val('');
		$("#btnReg").addClass("is-disabled");
	}else if(chk){
		$("#btnReg").removeClass("is-disabled");

	}

}
//약관동의
function btnRegDtl(param){
	$('#targetTerms').val('Y');
	openPage('termsPop','/termsPop.do',param);
}

function btnConRegDtl(param){
	openPage('termsInfoPop','/termsPop.do',param);
}

function targetTermsAgree() {
	if($('#targetTerms').val() == 'Y'){
		$('#chkAgree').prop('checked', 'checked');
	}

	chkEvent();

 }

//결합내역 조회
function btn_combiPop(){



	var parameterData = ajaxCommon.getSerializedData({
		contractNum : $("#ncn option:selected").val()
	});

	openPage('pullPopupByPost','/m/content/myCombinationList.do',parameterData);

}

//결합신청
function btn_reg(){
	
		
	combiReg();
	
}

function btn_Combi(){
	var chk = $("input[name=chkAgree]:checked").val();
	var confirm = "해당 회선과 결합하시겠습니까?"
	var radionYn = $("input[name=radUsed]:checked").val();

	if(radionYn == null){
		MCP.alert("결합가능한 회선을 선택해 주세요.");
		return;
	}

	if(chk == null){
		MCP.alert("필수 이용약관을 동의해 주세요.");
		return;
	}

	if($('#targetTerms').val() != 'Y'){
		MCP.alert("필수 이용약관을 상세보기를 확인해 주세요.");
		return;
	}
	
	KTM.Confirm(confirm, function (){
		 if($("#certifyYn").val()!="Y"){
			var parameterData = ajaxCommon.getSerializedData({
				 menuType	 : 'combi'
				 ,phoneNum 	 : $("#ncn option:selected").text()
				 ,contractNum :$("#ncn option:selected").val()
			});
			openPage('pullPopupByPost','/m/sms/smsAuthInfoPop.do',parameterData);
		
		} else{
			 combiReg();
		}
		
		this.close();	
	});
	

}

function combiReg(){
	var radionYn = $("input[name=radUsed]:checked").val();
	
	if(radionYn != ""){
		var thisId=$("input[name=radUsed]:checked").prop('id');
		var combiType = $("input[name=radUsed]:checked").val();
		var thisNo = $("#"+thisId).attr("no");
		
		if(combiType == "M"){
			var reqSvc = $("#reqSvc"+thisNo).val();
			var svcNoTypeCdMb =$("#svcNoTypeCdMb"+thisNo).val();
		} else if(combiType == "K"){
			var cmbStndSvcNoEv =$("#cmbStndSvcNoEv"+thisNo).val();
			var svcNoTypeCdKt = $("#svcNoTypeCdKt"+thisNo).val();
			var reqSvcKt = $("#reqSvcKt"+thisNo).val();
			var combYnKt = $("#combYnKt"+thisNo).val();
		}
		
		
	
		var varData = ajaxCommon.getSerializedData({
				  ncn : $("#ncn option:selected").val()
				 , combiChkYn : $("#combiChkYn").val()
				 , reqSvc: reqSvc
				 , svcNoTypeCdMb : svcNoTypeCdMb
				 , cmbStndSvcNoEv : cmbStndSvcNoEv
				 , svcNoTypeCdKt : svcNoTypeCdKt
				 , reqSvcKt : reqSvcKt
				 , combYnKt : combYnKt
				 , combYnMb :  $("input[name=combYnMb]").val()
	    });
	}
	
		ajaxCommon.getItem({
		        id:'insertCombinationAjax'
		        ,cache:false
		        ,url:'/m/content/insertCombinationAjax.do'
		        ,data: varData
		        ,dataType:"json"
		     }
		     ,function(jsonObj){

				var returnCode = jsonObj.returnCode;
				var message = jsonObj.message;
				
				if(returnCode == "S"){
					ajaxCommon.createForm({
						    method:"post"
						    ,action:"/m/content/myCombinationCompletView.do"
					});
		
			
			
					ajaxCommon.attachHiddenElement("combiChkYn",'Y');
					ajaxCommon.attachHiddenElement("ncn", $("#ncn option:selected").val());
					ajaxCommon.attachHiddenElement("reqSvc",reqSvc);
					ajaxCommon.attachHiddenElement("svcNoTypeCdMb",svcNoTypeCdMb);
					ajaxCommon.attachHiddenElement("cmbStndSvcNoEv",cmbStndSvcNoEv);
					ajaxCommon.attachHiddenElement("svcNoTypeCdKt",svcNoTypeCdKt);
					ajaxCommon.attachHiddenElement("reqSvcKt",reqSvcKt);
					ajaxCommon.attachHiddenElement("combYnKt",combYnKt);

					ajaxCommon.formSubmit();
				}else{
					MCP.alert(message);
				}
  
			 }
		);		
}
