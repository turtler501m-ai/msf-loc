<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_orgmgmt_0010.jsp
   * @Description : 본사  조직관리 대리점 예치금 입출금내역
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
	
	<div id="FORM1" class="section-search"></div>
	<div id="GRID1" class="section-full"></div> 

	
	<!-- Script -->
	<script type="text/javascript">

	function getParams(key){
	    var _parammap = {};
	    document.location.search.replace(/\??(?:([^=]+)=([^&]*)&?)/g, function () {
	        function decode(s) {
	            return decodeURIComponent(s.split("+").join(" "));
	        }
	
	        _parammap[decode(arguments[1])] = decode(arguments[2]);
	    });
	
	    return _parammap[key];
	};

		var DHX = {

			// ----------------------------------------
			//예치금수정
			FORM1: {
				title : "예치금 수정",
				items :[
					{type:"hidden", name:"searchAgentId", value:""}
					,{type: "block", blockOffset: 0, list: [
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, blockOffset:0}
					    ,{type: "label", label: "예치금정보"}
					    ,{type:"newcolumn"}					                   					
						,{type: "block", blockOffset: 0, list: [
					        {type: "input", label: "대리점명 : ", name:"agentNm", value:"", width:"150", readonly:true}
					        ,{type:"newcolumn", offset:"10"}
					        ,{type: "input", label: "예치금잔액 : ", name:"depositRemains",numberFormat:"0,000,000,000", value:"0", width:"100", readonly:true}
					        ,{type:"newcolumn", offset:"10"}
					        ,{type: "input", label: "가상계좌 : ", name:"vacInfo", value:"", width:"200", readonly:true}
					    ]}
						,{type: "block", blockOffset: 0, list: [
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, blockOffset:0}            						
   					     	,{type: "input", label: "조정금액 : ", name:"plusDeposit", value:"", width:150, validate:"NotEmpty,ValidNumeric", maxLength:10}
					        ,{type:"newcolumn", offset:"10"}
					        ,{type: "block", blockOffset: 0, list: [
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:100, blockOffset:0}
					        	,{type: "label", label: "(-)금액 입력가능"}
					        ]}
   					    ]}
						,{type: "block", blockOffset: 0, list: [
					        {type: "input", label: "조정사유 : ", name:"remark", value:"", width:"425"}
					        ,{type:"newcolumn", offset:"10"}
   					     	,{type:"button", value:"예치금조정", name:"btnReg"}
					    ]}
				    ]}
				],

				    onButtonClick : function(btnId, selectedData) {

						switch (btnId) {
							case "btnReg": //예치금조정버튼
								var plusDeposit = PAGE.FORM1.getItemValue("plusDeposit");
								var agentId = PAGE.FORM1.getItemValue("searchAgentId");
								var remark = PAGE.FORM1.getItemValue("remark"); 
								
								if(remark == ""){
									mvno.ui.alert("조정사유를 입력하세요");
									return;
								}
								
								mvno.cmn.ajax(ROOT + "/pps/hdofc/rcgmgmt/PpsDepositModify.json",{
									plusDeposit: plusDeposit,
									agentId: agentId,
									remark: remark
								},function(results) {
									mvno.ui.notify(results.result.oRetMsg);
									if(results.result.oRetCd == "0000"){
										PAGE.FORM1.refresh();
										PAGE.GRID1.refresh();
										PAGE.FORM1.setItemValue("plusDeposit","");
										PAGE.FORM1.setItemValue("remark","");
										var paramAgentId = PAGE.FORM1.getItemValue("searchAgentId");

										mvno.cmn.ajax(
												ROOT + "/pps/hdofc/rcgmgmt/PpsGetAgentInfo.json",
												{
													agentId : paramAgentId
												},
												function(results) {
													var virAccountId = "";
													var agentNm = results.result.data.rows[0].agentNm; 
													var virBankNm = results.result.data.rows[0].virBankNm;
													var depositRemains = results.result.data.rows[0].deposit;
													var vacInfo = "";
													if(virBankNm == "미등록"){
														vacInfo = "미등록";
													}else{
														virAccountId = results.result.data.rows[0].virAccountId;
														vacInfo = "("+virBankNm+") "+virAccountId;
													}
													PAGE.FORM1.setItemValue("vacInfo",vacInfo);
													PAGE.FORM1.setItemValue("depositRemains",depositRemains);
													PAGE.FORM1.setItemValue("agentNm",agentNm);
													
												},
												null
											);
									}
	                                //mvno.ui.closeWindowById("FORM17", true);
	                            });			                
								break;
						}
					}
			},
			GRID1 : {

				css : {
					height : "550px"
				},
				title : "예치금",
				  header : "입출금일자,예치금구분,조정사유,입금금액,출금액,잔액",
					columnIds : "depositDate,depositTypeName,remark,plusDeposit,minusDeposit,remains",
					widths : "200,150,181,140,140,140",
					colAlign : "center,left,left,right,right,right",
					colTypes : "ro,ro,ro,ron,ron,ron",
					colSorting : "str,str,str,int,int,int",
					paging : true,
				onRowSelect : function(rowId, celInd) {
					
				},
		        onRowDblClicked : function(rowId, celInd) {
		            // 수정버튼 누른것과 같이 처리?
		            this.callEvent('onButtonClick', ['btnMod']);
		        },				
				onButtonClick : function(btnId, selectedData) {

					switch (btnId) {

						case "btn01":
							// this.refresh();
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

				var paramAgentId = getParams("agentId");

				var paramCreateType = getParams("createType");

				if("BONSA" == paramCreateType)
				{
					mvno.ui.createForm("FORM1");	
					PAGE.FORM1.setItemValue("searchAgentId",paramAgentId);

					PAGE.FORM1.setItemValue("plusDeposit","");
					PAGE.FORM1.setItemValue("remark","");
					
					mvno.cmn.ajax(
						ROOT + "/pps/hdofc/rcgmgmt/PpsGetAgentInfo.json",
						{
							agentId : paramAgentId
						},
						function(results) {
							var virAccountId = "";
							var agentNm = results.result.data.rows[0].agentNm; 
							var virBankNm = results.result.data.rows[0].virBankNm;
							var depositRemains = results.result.data.rows[0].deposit;
							var vacInfo = "";
							if(virBankNm == "미등록"){
								vacInfo = "미등록";
							}else{
								virAccountId = results.result.data.rows[0].virAccountId;
								vacInfo = "("+virBankNm+") "+virAccountId;
							}
							PAGE.FORM1.setItemValue("vacInfo",vacInfo);
							PAGE.FORM1.setItemValue("depositRemains",depositRemains);
							PAGE.FORM1.setItemValue("agentNm",agentNm);
							
						},
						null
					);
				}
				mvno.ui.createGrid("GRID1");
				PAGE.GRID1.list(ROOT + " /pps/hdofc/orgmgmt/AgentDepositMgmtList.json", {searchAgentId : paramAgentId});

			}
		};
		
		
	</script>
