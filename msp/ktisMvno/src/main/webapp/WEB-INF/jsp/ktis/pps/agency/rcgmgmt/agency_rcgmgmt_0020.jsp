<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : agency_rcgmgmt_0020.jsp
   * @Description : 대리점  충전처리
   * @Modification Information
   *
   *   수정일         수정자                   수정내용
   *  -------    --------    ---------------------------
   *  2009.02.01            최초 생성
   *
   * author 실행환경 개발팀
   * since 2009.02.01
   *
   * Copyright (C) 2009 by MOPAS  All right reserved.
   */
   %>
	<!-- 화면 배치 -->
	<div id="FORM1" class="section-charge"></div>
	<div id="FORM2" class="section-charge"></div>

	<!-- Script -->
	<script type="text/javascript">


	function goAgentDepositList(agentId, createType)
	{
		var url = "/pps/hdofc/orgmgmt/AgentDepositMgmtDetailForm.do";
		var title = "예치금 상세"
		var menuId = "PPS1300004";

		var myTabbar = window.parent.mainTabbar;

        if (myTabbar.tabs(url)) {
        	myTabbar.tabs(url).close(); // 기존에 있는 탭을 닫기
        }

        myTabbar.addTab(url, title, null, null, true);
        myTabbar.tabs(url).attachURL(url + "?menuId=" + menuId+"&agentId="+agentId+"&createType="+createType);

        // 잘안보여서.. 일단 살짝 Delay 처리 
        setTimeout(function() {
            //mainLayout.cells("b").progressOff();
        }, 100);
	}

	function comma(str) {
	    str = String(str);
	    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	}


		var DHX = {

			// ----------------------------------------
			
			FORM1 : {
				title:"가상계좌",
				items : [	
							{type: 'hidden', name: 'contractNum', value: ''}
							,{type: 'hidden', name: 'bankName', value: ''}
							,{type: 'hidden', name: 'agentId', value: ''}
							,{type: "fieldset", label: "가상계좌정보", inputWidth:460, height : 50, offsetRight:5, list:[
	                            {type: 'settings', position: 'label-left', labelWidth: 160, inputWidth: 'left'},
	                            ,{type:"block", list:[
														{type: 'settings', position: 'label-left',inputWidth: 'left'}
									                   	,{type:"input", label:"은행명", name: "bankcd", labelWidth:100, width:150, labelAlign : "left"}
									                   	,{type:"input", label:"계좌번호", name: "acntnum", labelWidth:100, width:150, labelAlign : "left",validate:"ValidNumeric"}
									                   	,{type:"newcolumn"}
									                   	,{type:"button", value:"문자발송", name:"btnPrint", width : 60}
			                 						]
				                 }
				             ]} 
							 ,{type:"newcolumn"}   
	                         ,{type: "fieldset", label: "가상계좌변경", inputWidth:460, height : 50, offsetLeft:5, list:[
	                         {type: 'settings', position: 'label-left', inputWidth: 'left'},
				                 ,{type:"block", list:[
									                   	{type : "select", label:"은행선택", name : "virBankNm",  labelWidth: 100, width:150, labelAlign : "left"},
									                   	,{type:"block", list:[
																				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:12, blockOffset:0}
														                		,{type:"label", label:""}
														                	]}//공백처리
									                   	,{type:"newcolumn"}
									                   	,{type:"button", value:"변경", name:"btnReg", width : 60}
									                   	
			                 						]
				                 }
				             ]}
		                 	
				]
				,onButtonClick : function(btnId) {
					
					var form = this;
	
					switch (btnId) {
						case "btnPrint":
							var contract_num_str = PAGE.FORM2.getItemValue("contractNum");
							var comment = '가상계좌 정보를 문자로 발송하시겠습니까?';
							var event_str    = 'AGENT_VAC_INFO';

							if(confirm(comment))
							{
								var url = ROOT + "/pps/hdofc/custmgmt/PpsSmsProcs.json";
								mvno.cmn.ajax(
										url, 
										{
											contractNum : contract_num_str,
											event : event_str
											
										}
										, function(results) {
											 var result = results.result;
			                                  var retCode = result.oRetCode;
			                                  var msg = result.oRetMsg;
			                                  mvno.ui.notify(msg);
			                              });	
							}
							break;
						case "btnVacRequest":

							var data = this.getFormData(true);
							var op_code_str ="GIVE"; 
							var op_mode_str = 'B';
							var user_type_str = 'U';
							var user_number_str=PAGE.FORM2.getItemValue("contractNum");
							var req_bank_name_str = data.virBankNm;
							var comment = '선택한 은행으로 가상계좌를 부여 하시겠습니까?';
							if(req_bank_name_str=="")
							{
									alert("은행을 선택하세요");
									return;
							}
							if(confirm(comment))
							{
								  var url = ROOT + "/pps/hdofc/custmgmt/PpsVacProcs.json";
									mvno.cmn.ajax(
											url, 
											{
												opCode : op_code_str,
												opMode : op_mode_str,
												userType : user_type_str,
												reqBankCode : req_bank_name_str,
												userNumber : user_number_str,
												
											}
											, function(results) {
												 console.log(results);
												 var result = results.result;
				                                  var retCode = result.retCode;
				                                  var msg = result.retMsg;
				                                  mvno.ui.notify(msg);
				                                  if(retCode == "0000") {
				                            		 //TODO 재설정 
				                                      Page.FORM1.refresh();
				                                  }
											});

							}	
							break;
						case "btnVacCollect":
							var op_code_str ="BACK"; 
							var data = this.getFormData(true);
							var op_mode_str = 'B';
							var user_type_str = 'U';
							var user_number_str=PAGE.FORM2.getItemValue("contractNum");
							var req_bank_name_str = data.virBankNm;
							//alert("user_number_str["+user_number_str+"]")
							//alert("UserBankName ["+req_bank_name_str+"]");
							var comment = '현재 사용중인 가상계좌를 회수 하시겠습니까?';
							
							if(confirm(comment))
							{
								  var url = ROOT + "/pps/hdofc/custmgmt/PpsVacProcs.json";
									mvno.cmn.ajax(
											url, 
											{
												opCode : op_code_str,
												opMode : op_mode_str,
												userType : user_type_str,
												reqBankName : req_bank_name_str,
												userNumber : user_number_str,
												adminId : data.adminId
												
											}
											, function(results) {
												 var result = results.result;
				                                  var retCode = result.retCode;
				                                  var msg = result.retMsg;
				                                  mvno.ui.notify(msg);
				                                  if(retCode == "0000") { 
				                                      Page.FORM1.refresh();
				                                  }
											});

							}	
							break;
						case "btnReg":
							var data = this.getFormData(true);
							var op_code_str ="CHANGE"; 
							var op_mode_str = 'A';
							var user_type_str = 'A';
							//var user_number_str=PAGE.FORM2.getItemValue("contractNum");
							var req_bank_name_str = data.virBankNm;
							//alert("user_number_str["+user_number_str+"]")
							//alert("UserBankName ["+req_bank_name_str+"]");
							var comment = '선택한 은행으로 가상계좌를 변경 하시겠습니까?';
							if(req_bank_name_str=="")
							{
									alert("은행을 선택하세요");
									return;
							}
							if(confirm(comment))
							{
								  var url = ROOT + "/pps/hdofc/custmgmt/PpsVacProcs.json";
									mvno.cmn.ajax(
											url, 
											{
												opCode : op_code_str,
												opMode : op_mode_str,
												userType : user_type_str,
												reqBankCode : req_bank_name_str
												
											}
											, function(results) {
												 var result = results.result;
				                                  var retCode = result.retCode;
				                                  var msg = result.retMsg;
				                                  mvno.ui.notify(msg);

				                                  
				                                  if(retCode == "0000") {
				                                	  //mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsVacCodeList.json", null, PAGE.FORM1, "virBankNm");
				                                	  var url = ROOT + "/pps/agency/rcgmgmt/PpsAgentInfo.json";
				                                	  
				                      				mvno.cmn.ajax(
				                      						url, 
				                      						PAGE.FORM1
				                      						, function(results) {
				                      							var result = results.result;
				                                                var retCode = result.code;                       
				                                                var msg = result.retMsg;
				                                                var data = result.data;
				                  								
				                                                if(retCode == "OK") {

				                                          		
				                                          		 if(data.virAccountId!=null&& data.virAccountId!="")
				                                              	 {  
				                                          		  	PAGE.FORM1.setItemValue("acntnum",data.virAccountId);
				                                              	 }
				                                              	 if(data.virBankNm!=null && data.virBankNm!="")
				                                                   {
				                                          		 	PAGE.FORM1.setItemValue("bankcd",data.virBankNm);
				                                              	 }
				                                              	 if(data.virBankCd!=null && data.virBankCd!="")
				                                                   {
				                                          		 	PAGE.FORM1.setItemValue("virBankNm",data.virBankCd);
				                                              	 }
				                                              	 

				                                          		 PAGE.FORM2.setFormData(data);
				                                          		 var depositComma =  comma(data.deposit);
				                                          		 PAGE.FORM2.setItemValue("lblDeposit", data.deposit);
				                                          		
				                                                   
				                                                }
				                                                else {
				                                              	  mvno.ui.notify(msg);
				                                                }

				                      						});
		                      						

				                      				
				                                  }
											});

							}	
							
							break;
	
					}
	
				}
				
		
			},

			FORM2 : {
				title:"음성충전",
				items : [	{type: 'hidden', name: 'phoneCheckFlag', value: 'N'},
				         	{type: 'hidden', name: 'pinCheckFlag', value: 'N'},
				         	{type: 'hidden', name: 'deposit', value: ''},
							{type: "fieldset", label: "예치금 현금 충전", inputWidth:460, offsetRight:5, list:[
								{type: 'settings', position: 'label-left', labelWidth: 90, inputWidth: 'left'},
								{type:"block", list:[
														{type:"input", label: "CTN/계약번호", name :"subscriberNo1", labelWidth:100, width:150, labelAlign : "left"}
														,{type:"input", label: "예치금잔액",name:"lblDeposit", labelWidth:100, width:150, labelAlign : "left", validate:"ValidNumeric", numberFormat:"0,000,000,000" ,readonly: true}
														,{type:"input", label: "충전금액", name : "depPrice", labelWidth:100, width:150, labelAlign : "left", validate:"ValidNumeric", numberFormat:"0,000,000,000"}
														,{type:"newcolumn"}
														,{type:"button", value:"검색", name:"btnCtnRemains1"}
														,{type:"button", value:"내역", name:"btnDepositHistory"}
														,{type:"button", value:"충전", name:"btnMod"}
														,{type:"newcolumn", offset:10}
														,{type:"label",label:"", name:"custSearchResult1", labelWidth : 400,labelHeight:13}
														,{type:"block", list:[
																				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:13, blockOffset:0}
														                		,{type:"label", label:""}
														                	]}//공백처리
														,{type:"label",label:"", name:"", labelWidth : 70, labelHeight:13}
													]
								}													
			                 ]}
							,{type:"newcolumn"}
			                 ,{type: "fieldset", label: "PIN 음성 충전",  inputWidth:460, offsetLeft:5, list:[
   								{type: 'settings', position: 'label-left', labelWidth: 90, inputWidth: 'left'},
   								{type:"block", list:[
														{type:"input", label: "CTN/계약번호", name : "subscriberNo2", labelWidth:100, width:150, labelAlign : "left"}
														,{type:"input", label: "PIN 번호", name: "pinNumber", labelWidth:100, width:150, labelAlign : "left"}
														,{type:"input", label: "충전금액", name : "pinPrice", labelWidth:100, width:150, labelAlign : "left", validate:"ValidNumeric", numberFormat:"0,000,000,000", readonly:true}
														,{type:"newcolumn"}
														,{type:"button", value:"검색", name:"btnCTNremains2"}
														,{type:"button", value:"검색", name:"btnSearch"}
														,{type:"button", value:"충전", name:"btnDel"}
														,{type:"newcolumn"}
														,{type:"label",label:"", name:"custSearchResult2", labelWidth : 400, labelHeight:13}
														,{type:"label",label:"", name:"", labelWidth : 70, labelHeight:13}
														,{type:"label",label:"", name:"", labelWidth : 70, labelHeight:13}
													]
								}
			               ]}
			                 
				]
				,onKeyUp : function(inp, ev, name, value)
				{
					if(ev.keyCode == 13)
					{
						
					}
				}
				,onChange : function(name, value) 
				{
					switch(name)
					{
						case "subscriberNo1":
							var subscriberNo1Str = PAGE.FORM2.getItemValue("subscriberNo1");
							PAGE.FORM2.setItemValue("subscriberNo2", subscriberNo1Str);
							break;
						case "subscriberNo2":
							var subscriberNo2Str = PAGE.FORM2.getItemValue("subscriberNo2");
							PAGE.FORM2.setItemValue("subscriberNo1", subscriberNo2Str);
							break;
					}
				}
				,onButtonClick : function(btnId) {
	
					var form = this;
	
					switch (btnId) {
	
						case "btnMod":
							//alert('예치금충전');
							var subscriberNo =PAGE.FORM2.getItemValue("subscriberNo1");
						    var reqType="AG_RCG_VOICE_DEP";
						    var opCode = "RCG";
						    var recharge = PAGE.FORM2.getItemValue("depPrice");
						    var deposit = PAGE.FORM2.getItemValue("deposit");
						    var phoneCheckFlag = PAGE.FORM2.getItemValue("phoneCheckFlag");
						    
						    if(recharge == '' || parseInt(recharge) < 10){
						    	mvno.ui.notify("충전은 10원 이상부터 가능합니다.");	
						    	return;
							}
							
						    if(parseInt(recharge) > parseInt(deposit)){
						    	mvno.ui.notify("예치금이 부족합니다.");
						    	return;	
							}
							
							 if(phoneCheckFlag == 'N'){
								mvno.ui.notify("휴대폰번호를 확인해 주세요.");
								return;
							}

 							if(subscriberNo.length != 11 && subscriberNo.length != 9){
 								mvno.ui.notify("휴대폰번호를 확인해 주세요.");
 								return;
							}	 

						    var url = ROOT + "/pps/agency/rcgmgmt/agencyRecharge.json";
							mvno.cmn.ajax(
									url, 
									{
										subscriberNo : subscriberNo
										, reqType : reqType
										, opCode : opCode
										, recharge : recharge
									}
									, function(results) {
										 var result = results.result;
		                                  var retCode = result.oResCode;
		                                  var msg = result.oResCodeNm;
		                                  if(retCode == "0000") {

		                            		  mvno.ui.notify(msg);
		                            		  PAGE.FORM2.setItemLabel("custSearchResult1","");
		                                	  PAGE.FORM2.setItemLabel("custSearchResult2","");
		                                	  PAGE.FORM2.setItemValue("subscriberNo1", "010");
		                                	  PAGE.FORM2.setItemValue("subscriberNo2", "");
		                                	  PAGE.FORM2.setItemValue("phoneCheckFlag", "N");
		                                	  PAGE.FORM2.setItemValue("pinCheckFlag", "N");
		                                	  PAGE.FORM2.setItemValue("pinNumber", "");
		                                	  PAGE.FORM2.setItemValue("depPrice", "");
		                                	  PAGE.FORM2.setItemValue("pinPrice", "");

		                                	  var url = ROOT + "/pps/agency/rcgmgmt/PpsAgentInfo.json";
		                      				mvno.cmn.ajax(
		                      						url, 
		                      						PAGE.FORM1
		                      						, function(results) {

		                      							
		                      							 var result = results.result;
		                                                    var retCode = result.code;                       
		                                                    var msg = result.retMsg;
		                                                    var data = result.data;
		                      								
		                                                    if(retCode == "OK") {

		                                              		 PAGE.FORM2.setFormData(data);
		                                              		 var depositComma =  comma(data.deposit);
		                                              		 PAGE.FORM2.setItemValue("lblDeposit", data.deposit);
		                                                       
		                                                    }
		                                                    else {
		                                                  	  mvno.ui.notify(msg);
		                                                    }
		                      						});
		                            		  
		                                  }
		                                  else {
		                                	  mvno.ui.notify(msg);
		                                  }
									});					
							break;
						
						case "btnSearch":
							//alert('PIN번호 조회');
							var data = this.getFormData(true);

							if(data.pinNumber == '')
							{
								alert("PIN번호를 입력해주세요");
								return;
							}
							PAGE.FORM2.setItemValue("pinPrice", "");
							 var url = ROOT + "/pps/hdofc/custmgmt/PpsPinInfo.json";
								mvno.cmn.ajax(
										url, 
										data
										, function(results) {
											 var result = results.result;
			                                  var retCode = result.retCode;
			                                  var msg = result.retMsg;
			                                  if(retCode == "0000") {

			                            		  mvno.ui.notify(msg);
			                            		  
			                            		  PAGE.FORM2.setItemValue("pinPrice", result.pinPrice);
			                            		  PAGE.FORM2.setItemValue("pinCheckFlag", "Y");

			                            		  var url = ROOT + "/pps/agency/rcgmgmt/PpsAgentInfo.json";
			                      				mvno.cmn.ajax(
			                      						url, 
			                      						{
			                      							orgnId : ""
			                      						}
			                      						, function(result) {
			                      							
			                      							 var result = result.result;
			                                                    var retCode = result.code;                       
			                                                    var msg = result.retMsg;
			                                                    var data = result.data;
			                      								
			                                                    if(retCode == "OK") {

		 

			                                              		 PAGE.FORM2.setItemValue("deposit", data.deposit);
			                                              		 PAGE.FORM2.setItemLabel("lblDeposit",data.deposit);
			                                              		
			                                                       
			                                                    }
			                                                    else {
			                                                  	  mvno.ui.notify(msg);
			                                                    }
			                      						});

			                            		  

			                            		  
			                            		  
			                                  }
			                                  else {
			                                	  mvno.ui.notify(msg);
			                                	  PAGE.FORM2.setItemValue("pinCheckFlag", "N");
			                                     // mvno.ui.notify("ECMN0001");
			                                  }
										});							
							break;
						case "btnDel":
	
							//alert('PIN번호 충전');
							 var subscriberNo =PAGE.FORM2.getItemValue("subscriberNo2");
						     var reqType = "AG_RCG_VOICE_PIN";
						     var opCode = "RCG";
						     var recharge = PAGE.FORM2.getItemValue("pinPrice");
						     var rechargeInfo = PAGE.FORM2.getItemValue("pinNumber");
						     var phoneCheckFlag = PAGE.FORM2.getItemValue("phoneCheckFlag");
						     var pinCheckFlag = PAGE.FORM2.getItemValue("pinCheckFlag");
							
						 	if(phoneCheckFlag == 'N'){
								mvno.ui.notify("휴대폰번호를 확인해 주세요.");
								return;
							}

							if(pinCheckFlag == 'N'){
								mvno.ui.notify("핀번호를 확인해 주세요.");
								return;
							}

 							if(rechargeInfo.length == 0){
 								mvno.ui.notify("핀번호를 확인해 주세요.");
 								return;
							}

							 var url = ROOT + "/pps/agency/rcgmgmt/agencyRecharge.json";
								mvno.cmn.ajax(
										url, 
										{
											subscriberNo : subscriberNo
											, reqType : reqType
											, opCode : opCode
											, recharge : recharge
											, rechargeInfo : rechargeInfo
										}
										, function(results) {
											var result = results.result;
			                                  var retCode = result.oResCode;
			                                  var msg = result.oResCodeNm;
			                                  if(retCode == "0000") {

			                            		  mvno.ui.notify(msg);
			                            		  PAGE.FORM2.setItemLabel("custSearchResult1",msg);
			                                	  PAGE.FORM2.setItemLabel("custSearchResult2",msg);
			                                	  
			                                	  PAGE.FORM2.setItemValue("phoneCheckFlag", "N");
			                                	  PAGE.FORM2.setItemValue("pinCheckFlag", "N");

			                                	  PAGE.FORM2.setItemValue("pinNumber", "");
			                                	  PAGE.FORM2.setItemValue("pinPrice", "");
			                                  }
			                                  else {
			                                	  mvno.ui.notify(retCode + ":"+msg);
			                                	  PAGE.FORM2.setItemLabel("custSearchResult1",msg);
			                                	  PAGE.FORM2.setItemLabel("custSearchResult2",msg);
			                                  }
										});
							break;

						case "btnDepositHistory":
							var agentIdStr = PAGE.FORM1.getItemValue("agentId");
							goAgentDepositList(agentIdStr, "AGENT");
							break;
						case "btnCtnRemains1" :
							var subscriberNo1 = PAGE.FORM2.getItemValue("subscriberNo1");
							var url = ROOT + "/pps/agency/rcgmgmt/agencySearchCtn.json";
							mvno.cmn.ajax(
									url, 
									{
										subscriberNo : subscriberNo1
									}
									, function(results) {
										 var result = results.result;
		                                  var retCode = result.oResCode;
		                                  var msg = result.oResCodeNm;
		                                  if(retCode == "0000") {
		                                	  PAGE.FORM2.setItemLabel("custSearchResult1",msg);
		                                	  PAGE.FORM2.setItemLabel("custSearchResult2",msg);
		                                	  PAGE.FORM2.setItemValue("subscriberNo1", subscriberNo1);
		                                	  PAGE.FORM2.setItemValue("subscriberNo2", subscriberNo1);
		                                	  PAGE.FORM2.setItemValue("phoneCheckFlag", "Y");
		                                  }
		                                  else {
		                                	  PAGE.FORM2.setItemLabel("custSearchResult1",msg);
		                                	  PAGE.FORM2.setItemLabel("custSearchResult2",msg);
		                                	  PAGE.FORM2.setItemValue("subscriberNo1", subscriberNo1);
		                                	  PAGE.FORM2.setItemValue("subscriberNo2", subscriberNo1);
		                                	  PAGE.FORM2.setItemValue("phoneCheckFlag", "N");  
		                                  }
									});
							break;
						case "btnCTNremains2" :
							var subscriberNo2 = PAGE.FORM2.getItemValue("subscriberNo2");
							var url = ROOT + "/pps/agency/rcgmgmt/agencySearchCtn.json";
							mvno.cmn.ajax(
									url, 
									{
										subscriberNo : subscriberNo2
									}
									, function(results) {
										 var result = results.result;
		                                  var retCode = result.oResCode;
		                                  var msg = result.oResCodeNm;
		                                  if(retCode == "0000") {
		                                	  PAGE.FORM2.setItemLabel("custSearchResult1",msg);
		                                	  PAGE.FORM2.setItemLabel("custSearchResult2",msg);
		                                	  PAGE.FORM2.setItemValue("subscriberNo1", subscriberNo2);
		                                	  PAGE.FORM2.setItemValue("subscriberNo2", subscriberNo2);
		                                	  PAGE.FORM2.setItemValue("phoneCheckFlag", "Y");
		                                  }
		                                  else {
		                                	  PAGE.FORM2.setItemLabel("custSearchResult1",msg);
		                                	  PAGE.FORM2.setItemLabel("custSearchResult2",msg);
		                                	  PAGE.FORM2.setItemValue("subscriberNo1", subscriberNo2);
		                                	  PAGE.FORM2.setItemValue("subscriberNo2", subscriberNo2);
		                                	  PAGE.FORM2.setItemValue("phoneCheckFlag", "N");  
		                                  }
									});
							break;
	
					}
	
				}
			}
			

		};

		var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
			buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {

				mvno.ui.createForm("FORM1");
				mvno.ui.createForm("FORM2");
				//mvno.ui.createGrid("GRID1");
				
				

				//Kim Woong
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsVacCodeList.json", null, PAGE.FORM1, "virBankNm");
				var url = ROOT + "/pps/agency/rcgmgmt/PpsAgentInfo.json";
				mvno.cmn.ajax(
						url, 
						PAGE.FORM1
						, function(result) {

							
							 var result = result.result;
                              var retCode = result.code;                       
                              var msg = result.retMsg;
                              var data = result.data;

                              var agentIdStr = data.agentId;
                              PAGE.FORM1.setItemValue("agentId", agentIdStr);
								
                              if(retCode == "OK") {

                        		  //mvno.ui.notify(msg);
                        		  console.log(data);
                        		// PAGE.FORM1.setFormData(data);
                        		 if(data.virAccountId!=null&& data.virAccountId!="")
                            	 {  
                        		  	PAGE.FORM1.setItemValue("acntnum",data.virAccountId);
                            	 }
                            	 if(data.virBankNm!=null && data.virBankNm!="")
                                 {
                        		 	PAGE.FORM1.setItemValue("bankcd",data.virBankNm);
                            	 }
                            	 if(data.virBankCd!=null && data.virBankCd!="")
                                 {
                        		 	PAGE.FORM1.setItemValue("virBankNm",data.virBankCd);
                            	 }
                            	 

                        		 PAGE.FORM2.setFormData(data);
                        		 var depositComma =  comma(data.deposit);
                        		 PAGE.FORM2.setItemValue("lblDeposit", data.deposit);

                        		 PAGE.FORM2.setItemValue("subscriberNo1","010");
                        		 PAGE.FORM2.setItemValue("phoneCheckFlag","N");
                        		 PAGE.FORM2.setItemValue("pinCheckFlag","N");
                        		
                                 
                              }
                              else {
                            	  mvno.ui.notify(msg);
                              }
						});


			}

		};
		
		
	</script>
