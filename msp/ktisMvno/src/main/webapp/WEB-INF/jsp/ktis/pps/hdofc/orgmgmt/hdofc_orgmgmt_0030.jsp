<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_orgmgmt_0030.jsp
   * @Description : 본사  조직관리  개통대리점관리
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
	
	
	<div id="FORM1" class="section-search"></div>
	<div id="GRID1" class="section-full"></div>  
	
	<!-- 가상계좌 수정 팝업 -->
	<div id="FORM2"></div>
	
	<div id="FORM3" class="section-search"></div>
	
	<!-- Script -->
	<script type="text/javascript">
	function goAgentInfoData(linenumStr,pSize,pIdx)
	{
		var startSize = ((pIdx-1)*pSize);
		var endSize = pIdx*pSize;
		var rowId = "";
		
		for (var i=startSize; i<endSize; i++) {
			var linenum = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("linenum")).getValue();
			
			if(linenumStr == linenum){
				
				rowId = i;
				break;
			}
		 }
		rowId = rowId - startSize;

		var rowData = PAGE.GRID1.getRowData(rowId+1);
		
		if(rowData)
		{
			mvno.ui.createForm("FORM3");

			PAGE.FORM3.setFormData({});
			var selectedOrgnId = rowData.agentId;
		
			PAGE.FORM3.disableItem("agentId");
			PAGE.FORM3.disableItem("agentNm");
			PAGE.FORM3.disableItem("adminId");
			
			PAGE.FORM3.disableItem("deposit");
			PAGE.FORM3.disableItem("virAccountId");
			PAGE.FORM3.disableItem("recordDate");
			
			var lovCode="PPS_0078";
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode  } , PAGE.FORM3, "agentDocFlag");

			var url = ROOT +"/org/orgmgmt/ppsOrgnInfo.json";
			mvno.cmn.ajax(
			        url,
			        {
			            orgnId : selectedOrgnId
			        }, 
			        function(result) {
			             var result = result.result;
			              var rCode = result.code;
			              var msg = result.msg;
			              if(rCode == "OK") {
			                  PAGE.FORM3.setFormData(result.data);
			              }
			              else {
			                  mvno.ui.notify(msg);
			                 
			              }
			        });	

			mvno.ui.popupWindowById("FORM3", "대리점 정보", 840, 370, {
	            onClose: function(win) {
	                if (! PAGE.FORM3.isChanged()) return true;
	                mvno.ui.confirm("CCMN0005", function(){
	                    win.closeForce();
	                });
	            }
	        });	
			
		}
	}
	function goVirAccountData(linenumStr,pSize,pIdx)
	{	
		
		var startSize = ((pIdx-1)*pSize);
		var endSize = pIdx*pSize;
		var rowId = "";
		
		for (var i=startSize; i<endSize; i++) {
			var linenum = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("linenum")).getValue();
			
			if(linenumStr == linenum){
				
				rowId = i;
				break;
			}
		 }
		rowId = rowId - startSize;
		var rowData = PAGE.GRID1.getRowData(rowId+1);
		if(rowData)
		{
			mvno.ui.createForm("FORM2");

			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsVacCodeList.json", null, PAGE.FORM2, "virBankNm");
	    	
	    	PAGE.FORM2.setItemValue("acntnum", rowData.virAccountId);
	    	PAGE.FORM2.setItemValue("bankcd", rowData.virBankNm);
	    	PAGE.FORM2.setItemValue("virBankNm", rowData.virBankCd);
	    	PAGE.FORM2.setItemValue("orgOrgnId", rowData.agentId);
	        
	    	mvno.ui.popupWindowById("FORM2", "가상계좌변경", 480, 320, {
	            onClose: function(win) {
	                if (! PAGE.FORM2.isChanged()) return true;
	                mvno.ui.confirm("CCMN0005", function(){
	                    win.closeForce();
	                });
	            }
	        });	
		}	

	}


	function goDepositInfoData(linenumStr,pSize,pIdx)
	{
		var startSize = ((pIdx-1)*pSize);
		var endSize = pIdx*pSize;
		var rowId = "";
		
		for (var i=startSize; i<endSize; i++) {
			var linenum = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("linenum")).getValue();
			
			if(linenumStr == linenum){
				
				rowId = i;
				break;
			}
		 }
		rowId = rowId - startSize;

		var rowData = PAGE.GRID1.getRowData(rowId+1);
		var url = "/pps/hdofc/orgmgmt/AgentDepositMgmtDetailForm.do";
		var title = "예치금 상세";
		var menuId = "PPS1300004";
		var createType ="BONSA";
		var agentId = rowData.agentId;
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

	

		var DHX = {

			// ----------------------------------------
			FORM1 : {

				title : "",
				items : [ 	
							 {type:"block", list:[
								 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
								 {type : "input",label:"대리점",name : "searchAgentStr",width:100,maxLength:20},	// KimWoong
				                 {type : "newcolumn", offset:3},
				                 {type:"block", list:[
										{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
										,{type: "label", label: ":"}
				                 ]},
				                 {type : "newcolumn", offset:3},
				                 {type : "select",name : "searchAgentNm", width:200}, // KimWoong
				                 {type : "newcolumn"},
				                 {type : "select",label : "가상계좌",name : "searchVacFlag",width:100},
				                 {type : "newcolumn", offset:3},
				                 {type : "input",label : "예치금",name : "searchStartDeposit", width:100,maxlength:9, validate: 'ValidNumeric'},
								 {type : "newcolumn", offset:3}, 
								 {type:"block", list:[
									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
									,{type: "label", label: "~"}
			                   	  ]},	 
			                     {type : "newcolumn", offset:3}
			                   	,{type : "input",name : "searchEndDeposit", width:100 ,maxlength:9, validate: 'ValidNumeric'}
							 ]},// 1row
							 {type : "newcolumn", offset:10},
							 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search1"} 
						],
						onKeyUp : function(inp, ev, name, value)
						{
							if(ev.keyCode == 13)
							{
								switch(name)
								{
									case "searchAgentStr":
										var agentStr  = this.getItemValue("searchAgentStr");
										var selType ="popPin";
										//alert(agentStr);
										mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
											{ 
											  selectType : selType , 
										      searchAgentStr : agentStr
											} 
										     , PAGE.FORM1, "searchAgentNm");

									        var agentOption =  this.getOptions("searchAgentNm");
							        
									           if(agentOption.length==1)
										       {
									        	   //alert("ㄷ대리점이 존재하지 않습니다.");
									           }else if(agentOption.length==2)
										        {

											        var selValue = agentOption[1].value;

									        	   this.setItemValue("searchAgentNm",selValue);
									           }

								      
										break;
										
									default :
										if (this.getItemType(name) == 'input'){
											//대리점검색시 엔터클릭시 검색안되도록
											this.setItemFocus("btnSearchPps");	// input focus 가 남아있으면.. Enter 키가 계속먹음.
											this.callEvent("onButtonClick", ["btnSearchPps"]);		// 조회버튼 이름은 'btnSearch' 로!
										}
									
										break;

										
								
								}	
							}
						},

						onButtonClick : function(btnId) {

							var form = this;
		
							switch (btnId) {
		
								case "btnSearchPps":
									var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
									PAGE.FORM1.setItemValue("btnCount1", btnCount2)
									if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
									
									PAGE.GRID1.list(ROOT + "/pps/hdofc/orgmgmt/OpenAgentMgmtList.json", form, {
										callback : function () {
											PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
											PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										}
									});
									
									break;
		
							}

						}
			},

			// ----------------------------------------
			GRID1 : {

				css : {
					height : "550px"
				},
				title : "조회결과",
				header : "대리점코드,대리점명,대표자명,담당자연락처,유선연락처,팩스,예치금,가상계좌,은행명,등록일자,대리점코드,일련번호,은행코드,계좌번호 ",
				columnIds : "agentIdStr,agentNm,rprsenNm,respnPrsnNum,telnum,fax,depositStr,virAccountIdStr,virBankNm,regDttm,agentId,linenum,virBankCd,virAccountId",
				widths : "100,200,120,100,100,100,150,150,100,90,0,0,0,0", //총 1500
				colAlign : "center,left,left,center,center,center,right,left,left,center,left,left,left,left",
				colTypes : "link,ro,ro,roXp,roXp,roXp,link,link,ro,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str",
				paging : true,
				pageSize :15,
				pagingStyle :2,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					}
				},
				 onButtonClick : function(btnId, selectedData) {

					switch (btnId) {

						case "btnDnExcel":
							var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
							if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
							
							var totCnt = PAGE.GRID1.getRowsNum();
							if(totCnt <= 0){
								mvno.ui.notify("출력건수가 없습니다.");
							   return;
							}
							//alert(totCnt);
							if(totCnt>5000)
							{
								mvno.ui.notify("엑셀출력건수가 5,000건이상이면 시간(수분~수십분)이 걸릴수 있습니다.\n 잠시 기다려주시기 바랍니다.");
							   
							}
							
							var url = "/pps/hdofc/orgmgmt/OpenAgentMgmtListExcel.json";
							 var searchData =  PAGE.FORM1.getFormData(true);

							 	console.log("searchData",searchData);
							
						  		mvno.cmn.download(url+"?menuId=PPS1300003",true,{postData:searchData});
							break;
							
						
						
					}
				}
			},
			FORM2 : {
				items : [	
							{type: 'hidden', name: 'contractNum', value: ''}
							,{type: 'hidden', name: 'bankName', value: ''}
							,{type: 'hidden', name: 'orgOrgnId', value: ''}
							,{type: "fieldset", label: "가상계좌정보", inputWidth:400, height : 50, offsetRight:5, list:[
	                            {type: 'settings', position: 'label-left', labelWidth: 160, inputWidth: 'left'},
	                            ,{type:"block", list:[
														{type: 'settings', position: 'label-left',inputWidth: 'left'}
									                   	,{type:"input", label:"은행명", name: "bankcd", labelWidth:100, width:150, labelAlign : "left"}
									                   	,{type:"input", label:"계좌번호", name: "acntnum", labelWidth:100, width:150, labelAlign : "left",validate:"ValidNumeric"}
			                 						]
				                 }
				             ]} 
	                         ,{type: "fieldset", label: "가상계좌변경", inputWidth:400, height : 50, offsetLeft:5, list:[
	                         {type: 'settings', position: 'label-left', inputWidth: 'left'},
				                 ,{type:"block", list:[
									                   	{type : "select", label:"은행선택", name : "virBankNm",  labelWidth: 100, width:150, labelAlign : "left"},
									                   	,{type:"block", list:[
																				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:12, blockOffset:0}
														                		,{type:"label", label:""}
														                	]}//공백처리
									                   	,{type:"newcolumn"}
									                   	,{type:"button", value:"변경", name:"btnMod"}
									                   	
			                 						]
				                 }
				             ]}
		                 	
				],
	            buttons: {
	                center: {
	                    btnClose: { label: "닫기"}
	                }
	            }
				,onButtonClick : function(btnId) {
					
					var form = this;
	
					switch (btnId) {
												
						case "btnMod":
							var data = this.getFormData(true);
							var op_code_str ="CHANGE"; 
							var op_mode_str = 'A';
							var user_type_str = 'A';
							
							var req_bank_name_str = data.virBankNm;
							
							var req_org_orgn_id = data.orgOrgnId;
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
												reqBankCode : req_bank_name_str,
												orgOrgnId : req_org_orgn_id
												
											}
											, function(results) {
												 var result = results.result;
				                                  var retCode = result.retCode;
				                                  var msg = result.retMsg;
				                                  mvno.ui.notify(msg);

				                                  
				                                  if(retCode == "0000") {
				                                	var url = ROOT +"/org/orgmgmt/ppsOrgnInfo.json";

				                                	var selectedOrgnId = PAGE.FORM2.getItemValue("orgOrgnId");
				                                	  
				                      				mvno.cmn.ajax(
				                      						url, 
				                      						{
				                                                orgnId : selectedOrgnId
				                                            }
				                      						, function(results) {
				                      							var result = results.result;
				                                                var retCode = result.code;                       
				                                                var msg = result.retMsg;
				                  								
				                                                if(retCode == "OK") {
					                                                
																	PAGE.GRID1.refresh();
																	mvno.ui.closeWindowById(form, true);
				                                                   
				                                                }
				                                                else {
				                                              	  mvno.ui.notify(msg);
				                                                }

				                      						});
		                      						

				                      				
				                                  }
											});

							}	
							
							break;
						case"btnClose":
							mvno.ui.closeWindowById(form, true);
							break;
	
					}
	
				}
				
		
			},
			FORM3: {
                items: [
				{type: 'hidden', name: 'opCode', value: ''},
				{type: 'hidden', name: 'rentalFee', value: '0'},
				{type: 'hidden', name: 'virBankNm', value: ''},
				{type: 'hidden', name: 'virBankCd', value: ''},
				{type:"settings", position:"label-left", labelAlign:"left"},	
				{type:"block", width:140,height:15, list:[
					{type:"label", label:"대리점아이디"},
					{type:"label", label:"예치금"},
					{type:"label", label:"예치금차감율(일반)"},
					{type:"label", label:"데이타요율"},
					{type:"label", label:"KOS 전산 아이디"},
					{type:"label", label:"가상계좌"}
					
				  	]
				},
				{type:"newcolumn"},
				{type:"block", width:200,height:15, list:[
					{type:"input", name: "agentId", value: '' ,readonly:true}, 	//대리점아이디
					{type:"block", list:[
						{type:"input", name:"deposit", value: '' , readonly:true, numberFormat:"0,000,000,000", validate:"ValidNumeric"} //예치금
						
					]},
					{type:"block", list:[
						{type:"input", name:"depositRate", value: '' , validate:"ValidNumeric"}, // 예치금차감율
						{type:"newcolumn", offset:3},
						{type:"label",label:"%", labelHeight:14, labelWidth:"5"}
					]},
					{type:"block", list:[
						{type:"input", name:"dataRate", value: '' , validate:"ValidNumeric"}, //데이타요율
						{type:"newcolumn", offset:3},
						{type:"label",label:"%", labelHeight:14, labelWidth:"5"}
					]},
					{type:"block", list:[
						{type:"input", name:"ktAgencyId", value: ''} // KOS 전산 아이디
					]},
					{type:"block", list:[
						{type:"input", name:"virAccountId", value: '' , readonly:true, validate:"ValidNumeric"} // 가상계좌
					]}
					
					]
				},
				{type:"newcolumn" , offset:30},
				{type:"block", width:140,height:15, list:[
					{type:"label", label:"대리점명"},
					{type:"label", label:"상위조직ID"},
					{type:"label", label:"예치금차감율(RS)"},
					{type:"label", label:"핀구매할인율"},
					{type:"label", label:"대리점서식지 노출여부"},
					{type:"label", label:"등록수정일자"}
					]
				},
				{type:"newcolumn"},
				{type:"block", width:200,height:15, list:[
					{type:"input", name:"agentNm", value: '' , readonly:true}, //대리점명
					{type:"input", name:"adminId", value: '' , readonly:true}, // 상위조직ID
					{type:"block", list:[
						{type:"input", name:"pps35DepositRate", value: '' , validate:"ValidNumeric"}, // 예치금차감율
						{type:"newcolumn", offset:3},
						{type:"label",label:"%", labelHeight:14, labelWidth:"5"}
					]},
					{type:"block", list:[
						{type:"input", name:"pinBuyRate", value: '' , validate:"ValidNumeric"}, // 핀구매할인율
						{type:"newcolumn", offset:3},
						{type:"label",label:"%", labelHeight:14, labelWidth:"5"}
					]},
					{type:"block", list:[
						{type : "select",name : "agentDocFlag", width:125 ,required:true, height : 14, labelHeight:14}
					]},
					{type:"input", name:"recordDate", value: '' , readonly:true} // 등록수정일
					
					]
				}
                ],
                buttons: {
                    center: {
                        btnReg: { label: "수정"},
                        btnClose: { label: "닫기"},
                    }
                },
                onButtonClick : function(btnId) {
                    var form = this; // 혼란방지용으로 미리 설정 (권고)

                    switch (btnId) 
                    {
	                    case "btnReg":

	                    	PAGE.FORM3.setItemValue("rentalFee","0");
	            			PAGE.FORM3.setItemValue("opCode","EDT");	
		                    
	                    	var url = ROOT +"/org/orgmgmt/ppsOrgnChgProc.json";
                            mvno.cmn.ajax(
                                    url,
                                    form, 
                                    function(results) {
                                         console.log(results);
                                         var result = results.result;
                                          var rCode = result.oRetCd;
                                          var msg = result.oRetMsg;
                                          if(rCode == "0000") {
                                             PAGE.GRID1.refresh();
                                             mvno.ui.notify(msg);
                                             mvno.ui.closeWindowById(form, true);
                                          }
                                          else {
                                              mvno.ui.notify(msg);
                                          }
                                    });
	
	                        break;
						case "btnClose":
							mvno.ui.closeWindowById(form, true);
	
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
				mvno.ui.createGrid("GRID1");
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("linenum" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("agentId" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("virBankCd" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("virAccountId" ) ,true);
				

				var selType ="popPin";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType, searchAgentStr : null  } , PAGE.FORM1, "searchAgentNm");
				var lovCode="PPS0005";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode  } , PAGE.FORM1, "searchVacFlag");
				

			}

		};
		
		
	</script>
