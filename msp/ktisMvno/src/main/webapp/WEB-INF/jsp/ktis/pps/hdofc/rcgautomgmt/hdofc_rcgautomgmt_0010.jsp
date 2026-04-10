<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_rcgautomgmt_0010.jsp
   * @Description : 선불 본사   자동충전관리
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
<div id="FORM11" class="section-search"></div> 	
<!-- Script -->
<script type="text/javascript">

	function setCurrentMonthFirstDay(form, name)
	{
		var today = new Date();

		var mm = today.getMonth(); //January is 0!
		var yyyy = today.getFullYear();

		form.setItemValue(name, new Date(yyyy,mm,01));
	}

	function setCurrentDate(form, name)
	{
		form.setItemValue(name, new Date());
	}

<%@ include file="../../hdofcCustDetail.formitems" %>

var DHX = {

			// ----------------------------------------
	FORM1 : {
		items : [ 	
		          	 
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "newcolumn", offset:3},
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "등록일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:10}, 
						 {type : "select",label : "요금제",name : "searchSoc",width:100},
						 {type : "newcolumn", offset:10}, 
						 {type : "select",label : "충전구분",name : "searchReqType",width:100}
						 ,{type : "newcolumn", offset:10} 
				         ,{type : "select",label : "개통신분증",name : "searchCustIdntNoIndCd",width:100}
					 ]},// 1row
					 {type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						{type : "newcolumn", offset:3},
						{type : "select",label:"검색",name : "searchType",width:100, width:100},	
	                   	{type : "newcolumn", offset:3},
	                   	{type:"block", list:[
 									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
 									,{type: "label", label: ":"}
 			                   	 ]},	 
 			            {type : "newcolumn", offset:3}
 			            ,{type : "input",name : "searchNm", width:100,maxlength:20}
  			            ,{type : "newcolumn", offset:10} 
  			          	,{type : "input",label : "대리점 ",name : "searchAgentNm", width:100,maxlength:20}
						,{type : "newcolumn", offset:3}
						,{type : "select",name : "searchAgentId",width:167}
					 ]},// 2row
					 {type : "newcolumn", offset:10},
					 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search2"},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"} //버튼 여러번 클릭 막기 변수 by han
					 
				],

		onButtonClick : function(btnId) {

			var form = this;

			switch (btnId) {
			
				case "btnSearchPps":
					var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
					PAGE.FORM1.setItemValue("btnCount1", btnCount2)
					if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
					
					PAGE.GRID1.list(ROOT + "/pps/hdofc/rcgautomgmt/rcgAutoInfoMgmtList.json", form, {
						callback : function () {
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						}
					});
					
					break;

			}

		}
		,onKeyUp : function(inp, ev, name, value)
		{
			if(ev.keyCode == 13)
			{
				switch(name)
				{
					case "searchAgentNm":
						var agentStr  = this.getItemValue("searchAgentNm");
						//alert(agentStr);
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/rcgautomgmt/ppsRcgAutoAgentList.json",
								{ 
									searchAgentNm : agentStr
								} 
					      , PAGE.FORM1, "searchAgentId");
						var agentOption =  this.getOptions("searchAgentId");
				        
				        
				           if(agentOption.length==1)
					       {
				        	   //alert("ㄷ대리점이 존재하지 않습니다.");
				           }else if(agentOption.length==2)
					        {

						        var selValue = agentOption[1].value;

				        	   this.setItemValue("searchAgentId",selValue);
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
		},onValidateMore : function(data){

			if(data.startDt > data.endDt){
				 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
			}

			if(( data.searchType == null || data.searchType == '' ) && (data.searchNm != null && data.searchNm != '' ))
			{
				 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				 this.pushError("searchType", "검색조건", "을 선택해주세요");							
			}

		}

		
	}
	,FORM11: {
		title : "정책등록"
		,items : [
					{type: 'hidden', name: 'opCode', value: ''},
					{type:"block", width:700,height:15, list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
						,{type : "input",label : "대리점 ",name : "searchAgentNm", width:140,maxlength:20}
						,{type : "newcolumn", offset:3}
						,{type : "select",name : "agentId",width:167, validate: "NotEmpty"}
			        ]},
			        {type:"block", width:700,height:15, list:[
   						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
   						,{type:"label", label:"요금제"}
   						,{type:"newcolumn", offset:0}
   						,{type:"block", list:[
 							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:30, labelHeight:15}
 							,{type:"block", list:[
 								,{type:"block", list:[
   									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0}					
 									,{type : "select", name : "soc", width:140, label:"요금제", validate: "NotEmpty"}
 								]}						
 							]}												
 						]}
   			        ]},
					{type:"block", width:700,height:15, list:[
  						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
  						,{type:"label", label:"개통신분증"}
  						,{type:"newcolumn", offset:0}
  						,{type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:30, labelHeight:15}
							,{type:"block", list:[
								,{type:"block", list:[
  									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0}					
									,{type : "select", name : "custIdntNoIndCd", width:140, label:"개통신분증", validate: "NotEmpty"}
								]}						
							]}												
						]}
  			      ]},
  			      {type:"block", width:700,height:15, list:[
   						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
   						,{type:"label", label:"충전구분"}
    						,{type:"newcolumn", offset:0}
    						,{type:"block", list:[
  							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:30, labelHeight:15}
  							,{type:"block", list:[
  								,{type:"block", list:[
    									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0}					
  									,{type : "select", name : "reqType", width:140, label:"충전구분", validate: "NotEmpty"}
  								]}						
  							]}												
  						]}
   						
   			        ]},
  			      {type:"block", width:700,height:15, list:[
   						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
   						,{type:"label", label:"충전금액"}
   						,{type:"newcolumn", offset:0}
   						,{type:"input", label:"", name: "amount", width:140, validate: "ValidInteger"}
   						,{type:"newcolumn", offset:0}
   						,{type : "select",name : "amountSelect",width:167, validate: "NotEmpty"}
   			        ]},
   			     	{type:"block", width:700,height:15, list:[
   			             {type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
   			           	,{type:"label", label:"메모"}
   			            ,{type:"newcolumn", offset:0}
   			           	,{type:"input", label:"", name: "remark", width:500}	// 메모
   			          ]}		
				]
		,buttons: {
		   	center : {
	    		btnRcgAutoReg : { label : "등록" }, 
	    		btnRcgAutoMod : { label : "수정" }, 
	    		btnWarClose : { label : "닫기" }
			}
		}
		,onButtonClick : function(btnId) {
			    	
					//var form = this;
			
					switch (btnId) {
						case "btnRcgAutoReg": //등록
							
							PAGE.FORM11.setItemValue("opCode", "REG");
						
							var form = PAGE.FORM11.getFormData(true);
							
							if(form.soc == ""){
								mvno.ui.alert("요금제를 선택해주세요");
								return;
							}
							
							if(form.agentId == ""){
								mvno.ui.alert("대리점을 선택해주세요");
								return;
							}
							
							if(form.custIdntNoIndCd == ""){
								mvno.ui.alert("개통신분증을 선택해주세요");
								return;
							}
							
							mvno.ui.confirm("정책을 등록하시겠습니까?", function(){
								mvno.cmn.ajax(ROOT + "/pps/hdofc/rcgautomgmt/PpsRcgAutoReg.json", 
										form, function(results) {
											var data = results.result;
											var retCode = data.oRetCd;
											var retMsg = data.oRetMsg;
											mvno.ui.alert(retMsg);
											if(retCode=="0000")
											{
												mvno.ui.closeWindowById(PAGE.FORM11,false);											
												PAGE.GRID1.refresh();
												
											}
								})
							});
	        
							break;
							
						case "btnRcgAutoMod": //수정
							
							PAGE.FORM11.setItemValue("opCode", "MOD");
							
							mvno.ui.confirm("정책을 수정하시겠습니까?", function(){
								mvno.cmn.ajax(ROOT + "/pps/hdofc/rcgautomgmt/PpsRcgAutoReg.json", 
										PAGE.FORM11.getFormData(true), function(results) {
											var data = results.result;
											var retCode = data.oRetCd;
											var retMsg = data.oRetMsg;
											mvno.ui.alert(retMsg);
											if(retCode=="0000")
											{
												mvno.ui.closeWindowById(PAGE.FORM11,false);											
												PAGE.GRID1.refresh();
												
											}
								})
							});
	        
							break;
							
						case "btnWarClose":	
							mvno.ui.closeWindowById("FORM11", true);
			                break;
					}
		}
		,onKeyUp : function(inp, ev, name, value){
	
			if(ev.keyCode == 13) {
				switch(name)
				{
					case "searchAgentNm":
						var agentStr  = this.getItemValue("searchAgentNm");
						//alert(agentStr);
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/rcgautomgmt/ppsRcgAutoAgentList.json",
								{ 
									searchAgentNm : agentStr
								} 
					      , PAGE.FORM11, "agentId");
						var agentOption =  this.getOptions("agentId");
				        
				        
				           if(agentOption.length==1)
					       {
				        	   //alert("ㄷ대리점이 존재하지 않습니다.");
				           }else if(agentOption.length==2)
					        {

						        var selValue = agentOption[1].value;

				        	   this.setItemValue("agentId",selValue);
				           }
													      
					      
						break;
				}	
			}
		},
		onChange : function (name, value){
			switch(name)
			{
				case "soc":
					mvno.cmn.ajax(ROOT + "/pps/hdofc/rcgautomgmt/rcgAutoPps35Chk.json", 
							{soc:value}, function(results) {
								if(results.result.data.rows[0].pps35Flag == "Y"){
									mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0133"}, PAGE.FORM11, "amountSelect");
									PAGE.FORM11.setItemValue("amount", "");
									PAGE.FORM11.disableItem("amount");
								}else{
									mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0132"}, PAGE.FORM11, "amountSelect");
									PAGE.FORM11.disableItem("amount");
								}
					});
				
					break;
					
				case "amountSelect":
					if(value == "-1"){
						PAGE.FORM11.setItemValue("amount", "");
						PAGE.FORM11.disableItem("amount");
					}else{
						if(value == "ETC"){
							PAGE.FORM11.enableItem("amount");
							PAGE.FORM11.setItemValue("amount", "");
						}else{
							PAGE.FORM11.disableItem("amount");
							PAGE.FORM11.setItemValue("amount", value);
						}
					}
				
					break;
			
			}
        }
	}	

	,GRID1 : {

		css : {
			height : "530px"
		},
		title : "조회결과",
		header : "대리점,요금제명,개통신분증,충전구분,충전금액,등록일,변경일,처리자,설명",
		columnIds : "agentNm,socNm,custIdntNoIndCdNm,reqTypeNm,amountNm,regDt,modDt,adminNm,remark",
		widths : "150,120,120,100,100,120,120,80,200", //총 1500
		colAlign : "center,center,center,center,right,center,center,center,left",
		colTypes : "ro,ro,ro,ro,ro,roXd,roXd,ro,ro", //32
		colSorting : "str,str,str,str,str,str,str,str,str", //32
		paging : true,
		pageSize :15,
		pagingStyle :2,
		buttons : {
			hright : {
				btnDnExcel : { label : "엑셀다운로드" }
			},
			right :{
				btnReg :{label :"등록"},
				btnMod :{label :"수정"},
				btnDel :{label :"삭제"}
			}
		}
		,onButtonClick : function(btnId) {

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
					
					var url = "/pps/hdofc/rcgautomgmt/rcgAutoInfoMgmtListExcel.json";
					var searchData =  PAGE.FORM1.getFormData(true);
					 //console.log("searchData",searchData);
										
					 mvno.cmn.download(url+"?menuId=PPS1300005",true,{postData:searchData});
					break;

				case "btnReg":
					
					mvno.ui.createForm("FORM11");
					PAGE.FORM11.setFormData();
					PAGE.FORM11.clearChanged();
					
					mvno.cmn.ajax4lov(
							ROOT + "/pps/hdofc/rcgautomgmt/ppsRcgAutoAgentList.json"
							, null
							 , PAGE.FORM11, "agentId");
					
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/rcgautomgmt/ppsRcgAutoSocList.json", null, PAGE.FORM11, "soc");
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0129"}, PAGE.FORM11, "reqType");
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0130"}, PAGE.FORM11, "custIdntNoIndCd");
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0132"}, PAGE.FORM11, "amountSelect");
					
					PAGE.FORM11.enableItem("searchAgentNm");
					PAGE.FORM11.enableItem("agentId");
					PAGE.FORM11.enableItem("soc");
					PAGE.FORM11.enableItem("custIdntNoIndCd");
					PAGE.FORM11.disableItem("amount");
					
					mvno.ui.showButton("FORM11", "btnRcgAutoReg");
					mvno.ui.hideButton("FORM11", "btnRcgAutoMod");
					
					mvno.ui.updateSection("FORM11", "title", "정책등록");
					
					mvno.ui.popupWindowById("FORM11", "정책등록", 800, 350, {
		                onClose: function(win) {
		                	if (! PAGE.FORM11.isChanged()) return true;
		                	win.closeForce();
		                }
		            });	
	
					break;
					
				case "btnMod":
					var rowData = PAGE.GRID1.getSelectedRowData();
					
					if(rowData == null){
						mvno.ui.notify("선택된 Data가 없습니다.");
						return;
					}
					
					mvno.ui.createForm("FORM11");
					PAGE.FORM11.setFormData();
					PAGE.FORM11.clearChanged();
					
					mvno.cmn.ajax4lov(
							ROOT + "/pps/hdofc/rcgautomgmt/ppsRcgAutoAgentList.json"
							, null
							 , PAGE.FORM11, "agentId");
					
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/rcgautomgmt/ppsRcgAutoSocList.json", null, PAGE.FORM11, "soc");
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0129"}, PAGE.FORM11, "reqType");
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0130"}, PAGE.FORM11, "custIdntNoIndCd");
					
					mvno.cmn.ajax(ROOT + "/pps/hdofc/rcgautomgmt/rcgAutoPps35Chk.json", 
							{soc:rowData.soc}, function(results) {
								if(results.result.data.rows[0].pps35Flag == "Y"){
									mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0133"}, PAGE.FORM11, "amountSelect");
									PAGE.FORM11.setItemValue("amountSelect", rowData.amount);
									PAGE.FORM11.setItemValue("amount", "");
									PAGE.FORM11.disableItem("amount");
								}else{
									mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0132"}, PAGE.FORM11, "amountSelect");
									PAGE.FORM11.setItemValue("amountSelect", rowData.amount);
									PAGE.FORM11.setItemValue("amount", rowData.amount);
									
									if(PAGE.FORM11.getItemValue("amountSelect") == ""){
										PAGE.FORM11.setItemValue("amountSelect", "ETC");
										PAGE.FORM11.enableItem("amount");
									}else{
										PAGE.FORM11.disableItem("amount");
									}
								}
					});
					
					PAGE.FORM11.setItemValue("agentId", rowData.agentId);
					PAGE.FORM11.setItemValue("soc", rowData.soc);	
					PAGE.FORM11.setItemValue("reqType", rowData.reqType);	
					PAGE.FORM11.setItemValue("custIdntNoIndCd", rowData.custIdntNoIndCd);	
					PAGE.FORM11.setItemValue("remark", rowData.remark);	
					
					PAGE.FORM11.disableItem("searchAgentNm");
					PAGE.FORM11.disableItem("agentId");
					PAGE.FORM11.disableItem("soc");
					PAGE.FORM11.disableItem("custIdntNoIndCd");
					
					mvno.ui.hideButton("FORM11", "btnRcgAutoReg");
					mvno.ui.showButton("FORM11", "btnRcgAutoMod");

					mvno.ui.updateSection("FORM11", "title", "정책수정");
					
					mvno.ui.popupWindowById("FORM11", "정책수정", 800, 350, {
		                onClose: function(win) {
		                	if (! PAGE.FORM11.isChanged()) return true;
		                	win.closeForce();
		                }
		            });	
	
					break;
					
				case "btnDel":
					
					var rowData = PAGE.GRID1.getSelectedRowData();
					
					if(rowData == null){
						mvno.ui.notify("선택된 Data가 없습니다.");
						return;
					}
					
					rowData.opCode = "DEL";
					
					mvno.ui.confirm("정책을 삭제하시겠습니까?", function(){
						mvno.cmn.ajax(ROOT + "/pps/hdofc/rcgautomgmt/PpsRcgAutoReg.json", 
								rowData, function(results) {
									var data = results.result;
									var retCode = data.oRetCd;
									var retMsg = data.oRetMsg;
									mvno.ui.alert(retMsg);
									if(retCode=="0000")
									{										
										PAGE.GRID1.refresh();
										
									}
						})
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
			mvno.ui.createGrid("GRID1");

			//setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "endDt");
			
			mvno.cmn.ajax4lov(
					ROOT + "/pps/hdofc/rcgautomgmt/ppsRcgAutoAgentList.json"
					, null
					 , PAGE.FORM1, "searchAgentId");
			
			
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/rcgautomgmt/ppsRcgAutoSocList.json", null, PAGE.FORM1, "searchSoc");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0129"}, PAGE.FORM1, "searchReqType");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0130"}, PAGE.FORM1, "searchCustIdntNoIndCd");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0131"}, PAGE.FORM1, "searchType");
			
		}

	};
		
</script>