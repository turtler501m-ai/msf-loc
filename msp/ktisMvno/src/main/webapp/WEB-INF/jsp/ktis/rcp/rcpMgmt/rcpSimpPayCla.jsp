<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header -->
<div class="page-header">
	<h1>납부주장 팝업</h1>
</div>

<!-- 화면 배치 -->
<div id="FORM1" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

	var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type: 'fieldset', label: "미납정보", inputWidth:460, list:[
					{type: 'block', list: [
						{type:"input", label:"타사미청구금액", name:"npNchrgAmt", labelWidth:100, width:80, offsetLeft:0, validate:"ValidInteger", numberFormat:"0,000,000,000", value:"0", readonly: true}
						, {type:'newcolumn'}
						, {type:"input", label:"번호이동위약금", name:"npPnltAmt", labelWidth:120, width:80, offsetLeft:10, validate:"ValidInteger", numberFormat:"0,000,000,000", value:"0", readonly: true}
					]}
					, {type: 'block', list: [
						{type:"input", label:"번호이동미납금액", name:"npUnpayAmt", labelWidth:100, width:80, offsetLeft:0, validate:"ValidInteger", numberFormat:"0,000,000,000", value:"0", readonly: true}
						, {type:'newcolumn'}
						, {type:"input", label:"번호이동단말할부금", name:"npHndstInstAmt", labelWidth:120, width:80, offsetLeft:10, validate:"ValidInteger", numberFormat:"0,000,000,000", value:"0", readonly: true}
					]}
				]}
				, {type: 'fieldset', label: "납부방법", name: "PAYTYPE", inputWidth:460, list:[
						{type: 'block', list: [
							{type:"checkbox", label:"은행자동이체", name:"bankFlag", labelWidth:100, width:90, offsetLeft:0, checked:false}
							,{type:"newcolumn"}
							,{type:"checkbox", label:"카드자동이체", name:"cardFlag", labelWidth:100, width:90, offsetLeft:100, checked:false}
						]}
						, {type: 'block', list: [
							{type:"checkbox", label:"지로", name:"giroFlag", labelWidth:100, width:90, offsetLeft:0, checked:false}
							,{type:"newcolumn"}
							,{type:"checkbox", label:"무통장입금", name:"depositFlag", labelWidth:100, width:90, offsetLeft:100, checked:false}
						]}
				]}
				, {type: 'fieldset', label: "납부일자", name: "", inputWidth:460, list:[
					{type: 'block', list: [
						{type:"calendar", label:"", name:"payDay", labelWidth:0, width:100, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d"}
					]}
				]}
				, {type:"hidden", label:"예약번호", name:"resNo"}
				, {type:"hidden", label:"requestKey", name:"requestKey"}
				, {type:"hidden", label:"이벤트코드", name:"appEventCd"}
			]
			,buttons : {
				center : {
					btnNP2: {label:"납부주장" }
					,btnClose : {label : "닫기" }
				}
			}
			,onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					
					case "btnNP2":
						
						var osstPayType =  String(form.getItemValue("bankFlag")) 
										+ String(form.getItemValue("cardFlag"))
										+ String(form.getItemValue("giroFlag"))
										+ String(form.getItemValue("depositFlag"));
						
						if("0000" == osstPayType){
							mvno.ui.alert("납부방법을 선택 하세요.");
							
							return;
						}
						
						var osstPayDay = new Date(form.getItemValue("payDay")).format("yyyymmdd");
						
						var data = {
								osstPayDay : osstPayDay
								,osstPayType : osstPayType
								,requestKey : form.getItemValue("requestKey")
						};
						
						mvno.cmn.ajax4confirm("납부처리하시겠습니까?<br />완료시까지 잠시만 기다려주세요.", ROOT + "/rcp/rcpMgmt/updMcpMovePayClaByInfo.json", data, function() {
							form.setItemValue("appEventCd", "NP2");
							mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstServiceCall.json", form, function(resultData) {
								mvno.ui.alert(resultData.result.msg);
							});
						});
						
						break;
						
					case "btnClose" :
						
						mvno.ui.closeWindow();   
						
						break;
				}
			}
		}
	};
	
	var PAGE = {
		onOpen : function() {
			mvno.ui.createForm("FORM1");
			
			// 예약번호
			if("${param.resNo}" != ''){
				PAGE.FORM1.setItemValue("resNo","${param.resNo}");
			}
			
			// request key
			if("${param.requestKey}" != ''){
				PAGE.FORM1.setItemValue("requestKey","${param.requestKey}");
			}
			
			PAGE.FORM1.disableItem("payDay");
			PAGE.FORM1.disableItem("PAYTYPE");
			mvno.ui.disableButton("FORM1", "btnNP2");
			
			mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRcpSimpPayClaByInfo.json", PAGE.FORM1, function(resultData) {
				PAGE.FORM1.enableItem("payDay");
				PAGE.FORM1.enableItem("PAYTYPE");
				mvno.ui.enableButton("FORM1", "btnNP2");
				
				PAGE.FORM1.setItemValue("npNchrgAmt", resultData.result.data.npNchrgAmt);
				PAGE.FORM1.setItemValue("npPnltAmt", resultData.result.data.npPnltAmt);
				PAGE.FORM1.setItemValue("npUnpayAmt", resultData.result.data.npUnpayAmt);
				PAGE.FORM1.setItemValue("npHndstInstAmt", resultData.result.data.npHndstInstAmt);
				
				var toDay = new Date().format('yyyymmdd');
				PAGE.FORM1.setItemValue("payDay", toDay);
			});
		}
	};
	
</script>