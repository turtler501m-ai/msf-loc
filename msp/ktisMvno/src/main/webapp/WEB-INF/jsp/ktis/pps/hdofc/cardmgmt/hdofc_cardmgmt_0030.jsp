<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_cardmgmt_0030.jsp
   * @Description : 본사 선불카드관리 PIN출고관리
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
	<div id="GROUP1">
		<div id="FORM2" class="section-search" ></div>
	</div>
	<div id="FORM3" class="section-search" ></div>
	<div id="FORM4" class="section-search" ></div>
	<div id="FORM5" class="section-search" ></div>
	
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
		//var today = new Date().format("yyyy-MM-dd");

		form.setItemValue(name, new Date());
		
	}

	function goPinOutCancel(outSeq,pSize,pIdx){

// 		var startSize = ((pIdx-1)*pSize);
// 		var endSize = pIdx*pSize;
// 		var rowId = "";

// 		for (var i=startSize; i<endSize; i++) 
// 		{
// 			 var outSeqStr = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("outSeq")).getValue();
// 			 //alert(crSeqStr);
// 			if(outSeqStr == outSeq)
// 			{
// 				rowId = i;
// 				break;
// 			}

// 		}

// 		  var grid1 = PAGE.GRID1;
		  
// 		  var selectStartMngCode = 
// 			  grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("startMngCode" ) ).getValue();
// 		  //alert(selectStartMngCode);


// 			var selectEndMngCode = 
// 				grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("endMngCode" ) ).getValue();

			
					
// 			var otSeq = 
// 				grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("outSeq" ) ).getValue();


// 			var otAgentId = 
// 				grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("outAgentId" ) ).getValue();

// 			var otAgentType = 
// 				grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("outAgentType" ) ).getValue();

// 			var op_code_str ="CANCEL";
// 			var mngHeader = selectStartMngCode.substring(0,6);
// 			var startCode = selectStartMngCode.substring(6);
// 			var endCode =  selectEndMngCode.substring(6);
// 			var url= ROOT + "/pps/hdofc/cardmgmt/PpsPinOutProc.json";

// 			mvno.ui.confirm("CCMN0003", function() {

// 				mvno.cmn.ajax(url, 
// 						{
// 							startMngCode : startCode,
// 							endMngCode : endCode,
// 							opCodeStr : op_code_str,
// 							outSeq : otSeq,
// 							manageCodeHeader : mngHeader,
// 							outAgentType : otAgentType,
// 							agentId : otAgentId
					
// 				},
// 				 function(results) {
// 					var data1 = results.result;	
// 					console.log("D",data1);	
// 					var retCode = data1.oRetCode;
// 					var retMsg = data1.oRetMsg;
// 					mvno.ui.notify(retMsg);
// 					if(retCode=="0000")
// 					{
// 						PAGE.GRID1.refresh();											
// 					}
// 				});

// 			});

		var startSize = (pIdx-1)*pSize;
 		var endSize = pIdx*pSize;
 		var rowId = "";

 		for (var i=startSize; i<endSize; i++) 
 		{
 			 var outSeqStr = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("outSeq")).getValue();
 			 //alert(crSeqStr);
 			if(outSeqStr == outSeq)
			{
 				rowId = i;
 				break;
 			}

 		}

 		var grid1 = PAGE.GRID1;
		  
		var selectStartMngCode =
			grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("startMngCode" ) ).getValue();
		//alert(selectStartMngCode);
		console.log(selectStartMngCode);


		var selectEndMngCode =
			grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("endMngCode" ) ).getValue();

			
					
		var otSeq =
			grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("outSeq" ) ).getValue();


		var otAgentId =
			grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("outAgentId" ) ).getValue();

		var otAgentType =
			grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("outAgentType" ) ).getValue();
 			
 			
		mvno.ui.createForm("FORM5");
		PAGE.FORM5.setFormData();
		PAGE.FORM5.clearChanged();
		mvno.ui.popupWindowById("FORM5", "PIN출고취소", 400, 270, {
			onClose: function(win) {
				if (! PAGE.FORM5.isChanged()) return true;
				win.closeForce();
			}
		});

		PAGE.FORM5.setItemValue("startMngCode",selectStartMngCode);
		PAGE.FORM5.setItemValue("endMngCode",selectEndMngCode);
		PAGE.FORM5.setItemValue("outSeq",otSeq);
		PAGE.FORM5.setItemValue("outAgentId",otAgentId);
		PAGE.FORM5.setItemValue("outAgentType",otAgentType);
		//PAGE.FORM5.setItemValue("freeFlag",freeFlag);

		
	}

	function goPinInfoExcel(outSeq,pSize,pIdx){

		var startSize = (pIdx-1)*pSize;
		var endSize = pIdx*pSize;
		var rowId = "";

		for (var i=startSize; i<endSize; i++) 
		{
			 var outSeqStr = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("outSeq")).getValue();
			 //alert(crSeqStr);
			if(outSeqStr == outSeq)
			{
				rowId = i;
				break;
			}

		}
	

			  
	  var grid1 = PAGE.GRID1;
	  	 var searchData = grid1.getRowData(rowId+1);
	  	 //alert(searchData.startMngCode);
		

		 var url= "/pps/hdofc/cardmgmt/PinInfoListExcel.json";

		 mvno.cmn.download(url+"?menuId=PPS1400003",true,{postData:searchData});
	

	}

	

	

		var DHX = {

			// ----------------------------------------
			FORM1 : {

				title : "",
				items : [ 	
					{type:"block", list:[
					 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
					 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "출고일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
					 {type : "newcolumn", offset:3},
					 {type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
						,{type: "label", label: "~"},
                   	 ]},	 
                     {type : "newcolumn", offset:3}, 
					 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
					 {type : "newcolumn", offset:110}, 
					 {type : "select", label :"관리번호", name : "searchManageCodeHeader", width:100, labelWidth:70}, // KimWoong
					 {type : "newcolumn",offset:12}, 
					 {type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left",labelHeight:15, blockOffset:0},
						{type : "input", label :"", name : "searchManageCodeStart", width:80, labelWidth:0,maxlength:9, validate: 'ValidNumeric'},
						{type : "newcolumn", offset:5},	
						{type: "label", label: "부터",labelWidth:30},
						{type : "newcolumn"},
						{type : "input", label :"", name : "searchManageCodeEnd", width:80, labelWidth:0,maxlength:9, validate: 'ValidNumeric'},
						{type : "newcolumn", offset:5},
						{type: "label", label: "까지",labelWidth:30}
		             ]}, 
				 ]},// 1row
				 {type:"block", list:[
				  					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
				  					{type : "input",label:"대리점",name : "searchAgentStr",width:100},	// KimWoong
				  	                 {type : "newcolumn", offset:3},
				  	                 {type:"block", list:[
				  							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
				  							,{type: "label", label: ":"}
				  	                 ]},
				  	                 {type : "newcolumn", offset:3},
				  	                 {type : "select",name : "searchAgentNm", width:200}, 
				  	         		{type : "newcolumn", offset:10}, 
				  					{type : "input",label:"카드대리점",name : "searchDealerStr",width:100, labelWidth:70},	// KimWoong
				                     	{type : "newcolumn", offset:3},
				                     	{type:"block", list:[
				  							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
				  							,{type: "label", label: ":"}
				  	                   	 ]},	 
				  	                    {type : "newcolumn", offset:5},
				  	                   	{type : "select",name : "searchDealerNm", width:200} 	
				  				 ]},// 2row
				 {type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						{type : "select", label:"구분", name : "searchPinOpenAgentType", width:100, userdata: { lov: "PPS0029"}},
						{type : "newcolumn", offset:15},
						{type : "select", label :"카드구분", name : "searchFreeFlag", width:136}, // KimWoong
			            {type : "newcolumn", offset:10}, 
						{type : "select",label :"취소여부", name : "searchCancelFlag",width:100, labelWidth:70}
				 ]},// 3row 
				 {type : "newcolumn", offset:10},
				 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
				 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
				 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search3"}  
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

									case "searchDealerStr":
										var dealerStr  = this.getItemValue("searchDealerStr");
										var selType2 ="dealer";
										//alert(agentStr);
										mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
												{ 
												  selectType : selType2 , 
											      searchAgentStr : dealerStr
												} 
									      , PAGE.FORM1, "searchDealerNm");
										 var delaerOption =  this.getOptions("searchDealerNm");
									        
									        
								           if(delaerOption.length==1)
									       {
								        	   //alert("ㄷ대리점이 존재하지 않습니다.");
								           }
								           else if(delaerOption.length==2)
									        {

										        var selValue = delaerOption[1].value;

								        	   this.setItemValue("searchDealerNm",selValue);
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

						onValidateMore : function(data){

							 if(data.startDt > data.endDt){
								 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
							 }
						}
							,onButtonClick : function(btnId) 
							{
			
								var form = this;
			
								switch (btnId) {
			
									case "btnSearchPps":
										var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
										PAGE.FORM1.setItemValue("btnCount1", btnCount2)
										if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
										
										PAGE.GRID1.list(ROOT + " /pps/hdofc/cardmgmt/PinReleaseMgmtList.json", form, {
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
				header : "구분, 대리점/카드대리점, 대리점명/카드대리점명, 관리번호, 관리자, 출고수량, 개통수량, 카드구분, 취소, 출고일자, 비고, 출고번호,시작관리번호,종료관리번호,대리점구분코드,일련번호",
				columnIds : "outAgentTypeNm,outAgentId,outAgentNm,mngCodeStr,outAdminNm,outCount,openCount,freeFlagNm,cancelFlagNm,outDate,remark,outSeq,startMngCode,endMngCode,outAgentType,linenum",
				widths : "80,110,130,240,100,100,80,80,70,100,70,0,0,0,0,0", //총 1500
				colAlign : "center,center,left,center,left,right,right,center,center,center,center,center,center,center,center,left",
				colTypes : "ro,ro,ro,link,ro,ron,ron,ro,link,ro,ro,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,int,int,str,str,str,str,str,str,str,str,str",
				paging : true,
				pageSize :15,
				pagingStyle :2,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }, 

					},
					right : {
						btnReg : { label : "대리점 출고" },
						btnMod : { label : "카드 대리점 출고"}
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
							
							var url = "/pps/hdofc/cardmgmt/PinReleaseMgmtListExcel.json";
							 var searchData =  PAGE.FORM1.getFormData(true);

							 	console.log("searchData",searchData);
							
						  		mvno.cmn.download(url+"?menuId=PPS1400003",true,{postData:searchData});
							break;
							
						case "btnReg":
							mvno.ui.createForm("FORM3");
							PAGE.FORM3.setFormData();
							PAGE.FORM3.setItemLabel("lblOutCnt", "");
							PAGE.FORM3.setItemLabel("lblTotalCharge", "");
							setCurrentDate(PAGE.FORM3, "outDt");
							setCurrentDate(PAGE.FORM3, "payDt");
							PAGE.FORM3.clearChanged();
							mvno.ui.popupWindowById("FORM3", "PIN출고/개통(개통대리점)", 900, 350, {
				                onClose: function(win) {
				                	if (! PAGE.FORM3.isChanged()) return true;
				                	mvno.ui.confirm("CCMN0005", function(){
				                		win.closeForce();
				                	});
				                }
			                });	
							var selType ="popPin";
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType, searchAgentStr : null  } , PAGE.FORM3, "searchAgentId");
							var tblName ="CREATE";
							
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsMngCodeHeaderList.json",{tblNm :tblName }, PAGE.FORM3, "searchMngCodeHeader");
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0062"  } , PAGE.FORM3, "freeFlag");
							PAGE.FORM3.setItemValue("freeFlag","N");
							break;
						case "btnMod":
							mvno.ui.createForm("FORM4");
							PAGE.FORM4.setFormData();
							PAGE.FORM4.setItemLabel("lblOutCnt", "");
							PAGE.FORM4.setItemLabel("lblTotalCharge", "");
							setCurrentDate(PAGE.FORM4, "outDt");
							PAGE.FORM4.clearChanged();
							mvno.ui.popupWindowById("FORM4", "PIN출고(카드대리점)", 900, 350, {
				                onClose: function(win) {
				                	if (! PAGE.FORM4.isChanged()) return true;
				                	mvno.ui.confirm("CCMN0005", function(){
				                		win.closeForce();
				                	});
				                }
			                });	
							var selType ="dealer";
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType, searchAgentStr : null  } , PAGE.FORM4, "searchDealerId");
							var tblName ="CREATE";
							
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsMngCodeHeaderList.json",{tblNm :tblName }, PAGE.FORM4, "searchMngCodeHeader");
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0062"  } , PAGE.FORM4, "freeFlag");
							PAGE.FORM4.setItemValue("freeFlag","N");
					}
				}
			},


			FORM3 : {

				title : "PIN출고/개통(개통대리점)",
				buttons : {
					center : {
						btnPinOut : { label : "출고" }, 
						btnPinOutCancel : { label : "취소" }
					}
				},
				items : [
					{type: "hidden", name: "outCheckFlag", value: "0"},
					{type: "hidden", name: "totalCharge", value: "0"},
					{type: "hidden", name: "pinOutCnt", value: "0"},
					{type:"block", width:700,height:15, list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
						,{type:"label", label:"출고대리점"}
						,{type:"newcolumn", offset:0}
           				,{type : "input",label:"",name : "searchAgentStr",width:100, maxLength:10}
           				,{type : "newcolumn"}
           				,{type:"block", list:[
        					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0}
							,{type : "select",name : "searchAgentId", width:200, label:"출고대리점" ,validate: "NotEmpty"}
						]}
						,{type : "newcolumn", offset:10}
						,{type : "label", label:"", name : "searchDeposit" , labelWidth : 100}
			        ]},
					{type:"block", width:700,height:15, list:[
  						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
  						,{type:"label", label:"관리번호"}
  						,{type:"newcolumn", offset:0}
  						,{type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:30, labelHeight:15}
							,{type:"block", list:[
								,{type:"block", list:[
  									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0}					
									,{type : "select", name : "searchMngCodeHeader", width:100, label:"관리번호", validate: "NotEmpty", required: true}
								]}
								,{type : "newcolumn"}
								,{type:"block", list:[
							        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0}
									,{type : "input", label:"시작관리번호", name :"searchStartMngCode", width:77,maxLength:9, validate: 'NotEmpty,ValidNumeric'}
								]}
								,{type : "newcolumn", offset:15}
								,{type:"block", list:[
									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:16, labelHeight:15}
									,{type: "label", label: "~"}
									,{type : "newcolumn"}
									,{type:"block", list:[
								        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0}
										,{type : "input",label:"종료관리번호", name : "searchEndMngCode",width:77, offsetLeft:10,maxLength:9, validate: 'NotEmpty,ValidNumeric'}
									]}
								]}
								,{type : "newcolumn", offset:10}
								,{type:"button", value:"조회", name:"btnFind"}						
							]}												
						]}// 관리번호
  			        ]},
  			      {type:"block", width:700,height:15, list:[
   						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
   						,{type:"label", label:"출고가능갯수"}
   						,{type:"newcolumn", offset:5}
   						,{type:"label", label:"", name : "lblOutCnt", labelHeight:15} // 출고가능갯수
   						,{type:"newcolumn", offset:93}
   						,{type:"label", label:"출고합계금액"}
   						,{type:"newcolumn", offset:10}
   						,{type:"label", label:"0", name: "lblTotalCharge", labelHeight:15} // 출고합계금액
   			        ]},
   			     	{type:"block", width:800,height:15, list:[
   			            {type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
   			         	,{type:"label", label:"입금액"}
   			            ,{type:"newcolumn", offset:0}
   			         	,{type:"input", label:"", name: "payAmt", width:180 ,numberFormat:"0,000,000,000", validate: 'ValidInteger', maxLength:10}	// 입금액
   			            ,{type:"newcolumn", offset:48}
   			         	,{type:"label", label:"입금여부"}
   			            ,{type:"newcolumn", offset:0}
   			         	,{type:"block", list:[
							{type:"settings", position:"label-right", labelWidth:"auto"}
							,{type: "radio", name: "payFlag", value: "Y", label: "입금", checked: true}
							,{type:"newcolumn", offset:10}
							,{type: "radio", name: "payFlag", value: "N", label: "미입금"}
							,{type:"newcolumn", offset:10}
							,{type: "radio", name: "payFlag", value: "D", label: "예치금입금"}
						]} //입금여부
   			        ]},
   			     	{type:"block", width:800,height:15, list:[
   			            {type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
   			         	,{type:"label", label:"출고일자"}
   			            ,{type:"newcolumn", offset:0}
   			         	,{type:"calendar", label:"", name: "outDt", width:180, readonly:true}	// 출고일자
   			            ,{type:"newcolumn", offset:48}
   			         	,{type:"label", label:"입금일자"}
   			            ,{type:"newcolumn", offset:0}
   			         	,{type:"calendar", label:"", name: "payDt", width:180}	// 입금일자
   			        ]},
   			     	{type:"block", width:700,height:15, list:[
   			            {type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
   			             ,{type:"newcolumn", offset:3}
   			         	,{type : "select", label:"카드구분", name : "freeFlag", width:138, required: true} // KimWoong	// 메모
   			        ]},
   			     	{type:"block", width:700,height:15, list:[
   			             {type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
   			           	,{type:"label", label:"메모"}
   			            ,{type:"newcolumn", offset:0}
   			           	,{type:"input", label:"", name: "remark", width:552}	// 메모
   			          ]}		
				]
				,onKeyUp : function(inp, ev, name, value)
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
							      , PAGE.FORM3, "searchAgentId");
								  var agentOption =  this.getOptions("searchAgentId");
							        
							     if(agentStr != ''){
							    	 if(agentOption.length==1)
								       {
							        	   //alert("대리점이 존재하지 않습니다.");
							           }else if(agentOption.length>=2)
								        {
									        var selValue = agentOption[1].value;
							        	   this.setItemValue("searchAgentId",selValue);
							           }
								 }
						           
      
							      
								break;
						
						}	
					}
				}
				,onChange : function(name, value) {
					var form3 =this;
					switch(name){
						case "searchAgentId":
							if(form3.getItemValue("searchAgentId")!=null && form3.getItemValue("searchAgentId")!="" )
								{
									//alert("test");
									//TODO 대리점 예치금 가져오는 로직 추가 
									var agentId = form3.getItemValue("searchAgentId");
									
									mvno.cmn.ajax(
											ROOT + "/pps/hdofc/cardmgmt/PpsAgentInfo.json",
											{
												searchAgentId : agentId 
												
											},
											function(result) {
												var result = result.result;
												var data = result.data;
												 console.log("D",data);
												 var code = result.code;	
												var retCode = data.retCode;
												var retMsg = data.retMsg;
												if(code=="OK")
												{
													var title= "PIN출고/개통(개통대리점)  예치금할인율  "+data.pinBuyRate+"%";
													mvno.ui.updateSection("FORM3", "title", title);

													var outCnt = data.deposit.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
													
													var deposit = " 예치금 : "+outCnt+"원";
													form3.setItemLabel("searchDeposit",deposit);
													
												}else{
													mvno.ui.notify(retMsg);
													}
											},
											null
											)
								

								}
									
							
						break;


					
					}
					
				},
				onValidateMore : function(data){
					

					if(data.outCheckFlag!="1" )
					{
						this.pushError("btnFind","조회","관리번호 조회는 필수입니다.");
					}	
					
					if(data.pinOutCnt==null||data.pinOutCnt=="" || data.pinOutCnt=="0")
					{
						this.pushError("searchStartMngCode","출고가능갯수","출고가능한 핀이 없습니다.");
					}				
					
				},

				onButtonClick : function(btnId) {

					switch (btnId) {
						case "btnFind":
							
							var searchData = this.getFormData(true);
							   console.log("D",searchData);
							var op_code_str ="CHECK";     	
							var agentType ="A";
							var manageHeader = searchData.searchMngCodeHeader;
							var startCode = searchData.searchStartMngCode;
							var endCode = searchData.searchEndMngCode;
							var searchAgentId = searchData.searchAgentId;
							if(searchData.searchAgentId == ""){
								mvno.ui.alert("[출고대리점] 출고대리점은 선택은 필수입니다.");	
								return;	
							}

							if(searchData.searchStartMngCode==null||searchData.searchStartMngCode=="")
							{
								mvno.ui.alert("[시작관리번호] 시작관리번호는 필수입니다.");
								return;
							}
							if(searchData.searchEndMngCode==null||searchData.searchEndMngCode=="")
							{
								mvno.ui.alert("[종료관리번호] 종료관리번호는 필수입니다.");
								return;
							}
							
							

							mvno.cmn.ajax(
									ROOT + "/pps/hdofc/cardmgmt/PpsPinOutProc.json",
									{
										startMngCode : startCode,
										endMngCode : endCode,
										opCodeStr : op_code_str,
										manageCodeHeader : manageHeader,
										outAgentType : agentType,
										agentId : searchAgentId
										
										
										
									},
									function(result) {

										var data1 = result.result;	
										console.log("D",data1);	
										var retCode = data1.oRetCode;
										var retMsg = data1.oRetMsg;
										if(retCode=="0000")
										{
											//TODO 화면셋팅 
											
											//PAGE.FORM3.setFormData(data1);
											//alert(retMsg);
											var totCharge = data1.oRetTotalCharge.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
											var otCnt = data1.oRetOutCount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
											
											PAGE.FORM3.setItemLabel("lblOutCnt",retMsg); //출고가능갯수
											PAGE.FORM3.setItemLabel("lblTotalCharge",totCharge); // 출고합계금액
											PAGE.FORM3.setItemValue("outCheckFlag","1");
											PAGE.FORM3.setItemValue("totalCharge",data1.oRetAgentCharge);
											PAGE.FORM3.setItemValue("payAmt",data1.oRetAgentCharge);
											PAGE.FORM3.setItemValue("pinOutCnt",data1.oRetOutCount);
											
											
											
										}else{
												alert(retMsg);
											 	mvno.ui.notify(retMsg);


											}
									},
									null
									)
							break;   

						case "btnPinOut":
							if (! PAGE.FORM3.validate())  return;
							var searchData = this.getFormData(true);
							   //console.log("D",searchData);
							var op_code_str ="OUT";     	
							var agentType ="A";
							var manageHeader = searchData.searchMngCodeHeader;
							var startCode = searchData.searchStartMngCode;
							var endCode = searchData.searchEndMngCode;
							var searchAgentId = searchData.searchAgentId;
							var outDt = searchData.outDt;
							var payAt = searchData.payAmt;
							var remark = searchData.remark;
							var payFlag = searchData.payFlag;
							var payDt = searchData.payDt;
							var reqCnt = searchData.pinOutCnt;
							var freeFlag = searchData.freeFlag;

							if (! PAGE.FORM3.validate())  return;
							mvno.cmn.ajax(
									ROOT + "/pps/hdofc/cardmgmt/PpsPinOutProc.json",
									{
										startMngCode : startCode,
										endMngCode : endCode,
										opCodeStr : op_code_str,
										manageCodeHeader : manageHeader,
										outAgentType : agentType,
										agentId : searchAgentId,
										outDate : outDt,
										payAmt : payAt,
										remark : remark,
										payFlag : payFlag,
										payDate : payDt,
										freeFlag : freeFlag,
										reqOutCount : reqCnt  
										
									},
									function(results) {

										var data = results.result;
										 console.log("D",data);
										var retCode = data.oRetCode;
										var retMsg = data.oRetMsg;
										mvno.ui.notify(retMsg);
										if(retCode=="0000")
										{
											mvno.ui.closeWindowById(PAGE.FORM3,true);
											PAGE.GRID1.refresh();
										}
									},
									null
									)
							break;
						case "btnPinOutCancel":
							mvno.ui.closeWindowById(this);
							break;
						
					}
				}
			},

			FORM4 : {

				title : "PIN출고(카드대리점)",
				buttons : {
					center : {
						btnPinOut : { label : "출고" }, 
						btnPinOutCancel : { label : "취소" }
					}
				},
				items : [
					{type: "hidden", name: "outCheckFlag", value: "0"},
					{type: "hidden", name: "totalCharge", value: "0"},
					{type: "hidden", name: "pinOutCnt", value: "0"},
					{type:"block", width:700,height:15, list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
						,{type:"label", label:"출고카드대리점"}
						,{type:"newcolumn", offset:0}
           				,{type : "input",label:"",name : "searchDealerStr",width:100, maxLength:10}
           				,{type : "newcolumn"}
           				,{type:"block", list:[
        					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0}
							,{type : "select",name : "searchDealerId", width:200, label:"출고카드대리점" ,validate: "NotEmpty"}
						]}
						,{type : "newcolumn", offset:10}
						,{type : "label", label:"", name : "searchDeposit" , labelWidth : 100}
			        ]},
					{type:"block", width:700,height:15, list:[
  						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
  						,{type:"label", label:"관리번호"}
  						,{type:"newcolumn", offset:0}
  						,{type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:30, labelHeight:15}
							,{type:"block", list:[
								,{type:"block", list:[
  									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0}					
									,{type : "select", name : "searchMngCodeHeader", width:100, label:"관리번호", validate: "NotEmpty", required: true}
								]}
								,{type : "newcolumn"}
								,{type:"block", list:[
							        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0}
									,{type : "input", label:"시작관리번호", name :"searchStartMngCode", width:77,maxLength:9, validate: 'NotEmpty,ValidNumeric'}
								]}
								,{type : "newcolumn", offset:15}
								,{type:"block", list:[
									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:16, labelHeight:15}
									,{type: "label", label: "~"}
									,{type : "newcolumn"}
									,{type:"block", list:[
								        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0}
										,{type : "input",label:"종료관리번호", name : "searchEndMngCode",width:77, offsetLeft:10,maxLength:9, validate: 'NotEmpty,ValidNumeric'}
									]}
								]}
								,{type : "newcolumn", offset:10}
								,{type:"button", value:"조회", name:"btnFind"}						
							]}												
						]}// 관리번호
  			        ]},
  			      {type:"block", width:700,height:15, list:[
   						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
   						,{type:"label", label:"출고가능갯수"}
   						,{type:"newcolumn", offset:5}
   						,{type:"label", label:"", name : "lblOutCnt", labelHeight:15} // 출고가능갯수
   						,{type:"newcolumn", offset:93}
   						,{type:"label", label:"출고합계금액"}
   						,{type:"newcolumn"}
   						,{type:"label", label:"0", name: "lblTotalCharge", labelHeight:15} // 출고합계금액
   			        ]},
   			     	{type:"block", width:800,height:15, list:[
   			            {type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
   			         	,{type:"label", label:"출고일자"}
   			            ,{type:"newcolumn", offset:0}
   			         	,{type:"calendar", label:"", name: "outDt", width:180, readonly:true}	// 출고일자
   			         	,{type:"newcolumn", offset:60}
   			         	,{type : "select", label :"카드구분", name : "freeFlag",labelWidth:60, width:138, required: true} // KimWoong
						,{type : "newcolumn"} 
   			        ]},
   			     	{type:"block", width:700,height:15, list:[
   			            {type:"settings", position:"label-left", labelAlign:"left", labelWidth:140, labelHeight:15, blockOffset:0}
   			         	,{type:"label", label:"메모"}
   			            ,{type:"newcolumn", offset:0}
   			         	,{type:"input", label:"", name: "remark", width:552}	// 메모
   			        ]}							
				],
				onChange : function(name, value) {
					var form4 =this;
					switch(name){
						case "searchDealerId":
							if(form4.getItemValue("searchDealerId")!=null && form4.getItemValue("searchDealerId")!="" )
								{
									//TODO 대리점 예치금 가져오는 로직 추가 
									var agentId = form4.getItemValue("searchDealerId");
									
									
									mvno.cmn.ajax(
											ROOT + "/pps/hdofc/cardmgmt/PpsAgentInfo.json",
											{
												searchAgentId : agentId 
												
											},
											function(results) {
												var result = results.result;
												var data = result.data;
												 console.log("D",data);
												 var code = result.code;	
												var retCode = data.retCode;
												var retMsg = data.retMsg;
												if(code=="OK")
												{

													var title= "PIN출고(카드대리점)  예치금할인율  "+data.pinBuyRate+"%";
													mvno.ui.updateSection("FORM4", "title", title);

													var outCnt = data.deposit.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
													
													var deposit = " 예치금 : "+outCnt+"원";
													form4.setItemLabel("searchDeposit",deposit);
													
													
													
												}
											},
											null
											)

								}
									
							
						break;


					
					}
					
				},
				onValidateMore : function(data){
											
					if(data.pinOutCnt==null||data.pinOutCnt==""||data.pinOutCnt=="0" )
					{
						this.pushError("searchStartMngCode","출고가능갯수","출고가능한 핀이 없습니다.");
					}					
	
				},

			onKeyUp : function(inp, ev, name, value)
			{
				if(ev.keyCode == 13)
				{
					switch(name)
					{
						

						case "searchDealerStr":
							var dealerStr  = this.getItemValue("searchDealerStr");
							var selType2 ="dealer";
							//alert(agentStr);
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
									{ 
									  selectType : selType2 , 
								      searchAgentStr : dealerStr
									} 
						      , PAGE.FORM4, "searchDealerId");
							 var agentOption =  this.getOptions("searchDealerId");
						        
						       if(dealerStr != ''){
						    	   if(agentOption.length==1)
							       {
						        	   //alert("대리점이 존재하지 않습니다.");
						           }else if(agentOption.length>=2)
							        {

								        var selValue = agentOption[1].value;

						        	   this.setItemValue("searchDealerId",selValue);
						           }
							   } 
														      
							break; 
					
					}	
				}
			},
				onButtonClick : function(btnId) {

					switch (btnId) {
						case "btnFind":
							
							var searchData = this.getFormData(true);
							   console.log("D",searchData);
							var op_code_str ="CHECK";     	
							var agentType ="D";
							var manageHeader = searchData.searchMngCodeHeader;
							var startCode = searchData.searchStartMngCode;
							var endCode = searchData.searchEndMngCode;
							var searchAgentId = searchData.searchDealerId;
							if(searchData.searchDealerId == ""){
								mvno.ui.alert("[출고대리점] 출고대리점은 선택은 필수입니다.");	
								return;	
							}

							if(searchData.searchStartMngCode==null||searchData.searchStartMngCode=="")
							{
								mvno.ui.alert("[시작관리번호] 시작관리번호는 필수입니다.");
								return;
							}
							if(searchData.searchEndMngCode==null||searchData.searchEndMngCode=="")
							{
								mvno.ui.alert("[종료관리번호] 종료관리번호는 필수입니다.");
								return;
							}

							mvno.cmn.ajax(
									ROOT + "/pps/hdofc/cardmgmt/PpsPinOutProc.json",
									{
										startMngCode : startCode,
										endMngCode : endCode,
										opCodeStr : op_code_str,
										manageCodeHeader : manageHeader,
										outAgentType : agentType,
										agentId : searchAgentId
										
										
										
									},
									function(result) {

										var data1 = result.result;
										console.log("D",data1);	
										var retCode = data1.oRetCode;
										var retMsg = data1.oRetMsg;
										if(retCode=="0000")
										{
											
											var totCharge = data1.oRetTotalCharge.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
											var otCnt = data1.oRetOutCount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			
											
											PAGE.FORM4.setItemLabel("lblOutCnt", retMsg); //출고가능갯수
											PAGE.FORM4.setItemLabel("lblTotalCharge",totCharge); // 출고합계금액
											PAGE.FORM4.setItemValue("outCheckFlag","1");
											PAGE.FORM4.setItemValue("totalCharge",data1.oRetAgentCharge);
											PAGE.FORM4.setItemValue("payAmt",data1.oRetAgentCharge);
											PAGE.FORM4.setItemValue("pinOutCnt",data1.oRetOutCount);
											
											
											
										}
									},
									null
									)
							break;   

						case "btnPinOut":
							if (! PAGE.FORM4.validate())  return;
							var searchData = this.getFormData(true);
							   console.log("searchData",searchData);
							var op_code_str ="OUT";     	
							var agentType ="D";
							var manageHeader = searchData.searchMngCodeHeader;
							var startCode = searchData.searchStartMngCode;
							var endCode = searchData.searchEndMngCode;
							var searchAgentId = searchData.searchDealerId;
							var outDt = searchData.outDt;
							var payAt = searchData.payAmt;
							var remark = searchData.remark;
							var payFlag = searchData.payFlag;
							var reqCnt = searchData.pinOutCnt;
							var freeFlag = searchData.freeFlag

							if (! PAGE.FORM4.validate())  return;
							
							
							
							mvno.cmn.ajax(
									ROOT + "/pps/hdofc/cardmgmt/PpsPinOutProc.json",
									{
										startMngCode : startCode,
										endMngCode : endCode,
										opCodeStr : op_code_str,
										manageCodeHeader : manageHeader,
										outAgentType : agentType,
										agentId : searchAgentId,
										outDate : outDt,
										payAmt : payAt,
										remark : remark,
										payFlag : payFlag,
										freeFlag : freeFlag,
										reqOutCount : reqCnt  
										
									},
									function(results) {
										 
										 var data = results.result;
										var retCode = data.oRetCode;
										var retMsg = data.oRetMsg;
										mvno.ui.notify(retMsg);
										if(retCode=="0000")
										{
											mvno.ui.closeWindowById(PAGE.FORM4,false);											
											PAGE.GRID1.refresh();
											
										}									},
									null
									)	
							
							break;
						case "btnPinOutCancel":
							mvno.ui.closeWindowById(this);
							break;
						
					}
				}
			},
			FORM5 : {
				title : "PIN출고 취소사유",
				
				items : [
					 {type: "hidden", name: "startMngCode"}
					,{type: "hidden", name: "endMngCode"}					
					,{type: "hidden", name: "outSeq"}
					,{type: "hidden", name: "outAgentId"}
					,{type: "hidden", name: "outAgentType"}
					,{type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
					,{type:"block", list:[ 
						,{type:"input",  rows:5, width:300, name:"CANCEL_RSN",  maxLength:250 }
					]}
				],
				buttons : {
	                center : {
	                	btnApply : { label : "확인" },
	                    btnClose : { label : "닫기" }
	                }
	            },
	            
	            onButtonClick : function(btnId) {

					//var form = this; // 혼란방지용으로 미리 설정 (권고)
					
					switch (btnId) {
						case "btnApply":
							if(!PAGE.FORM5.getItemValue("CANCEL_RSN")){
			                      mvno.ui.alert("취소사유를 입력하세요.");
			                      break;
	                        }
							
							var searchData = this.getFormData(true);
							 console.log("searchData",searchData);
							
							var op_code_str ="CANCEL";
					 		var mngHeader = searchData.startMngCode.substring(0,6);
					 		var startCode = searchData.startMngCode.substring(6);
					 		var endCode =  searchData.endMngCode.substring(6);
					 		
					 		var url= ROOT + "/pps/hdofc/cardmgmt/PpsPinOutProc.json";
					 		
					 		mvno.ui.confirm("취소하시겠습니까", function() {

				 				mvno.cmn.ajax(url, 
				 						{
				 							startMngCode : startCode,
				 							endMngCode : endCode,
				 							opCodeStr : op_code_str,
				 							outSeq : searchData.outSeq,
				 							manageCodeHeader : mngHeader,
				 							outAgentType : searchData.outAgentType,
				 							agentId : searchData.outAgentId,
				 							remark : searchData.CANCEL_RSN
									
				 				},
				 				 function(results) {
				 					var data1 = results.result;	
				 					console.log("D",data1);	
				 					var retCode = data1.oRetCode;
				 					var retMsg = data1.oRetMsg;
				 					mvno.ui.notify(retMsg);
				 					if(retCode=="0000")
				 					{
										mvno.ui.closeWindowById(PAGE.FORM5,false);	
				 						PAGE.GRID1.refresh();											
				 					}
				 				},null); 
					 		},null);
					 		break;
				 				

							
					        
							
							
						case "btnClose":
							
							mvno.ui.closeWindowById(this);
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
				
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("outSeq" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("startMngCode" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("endMngCode" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("outAgentType" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("linenum" ) ,true);	

				//PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("cancelFlagNm" ) ,true);

				var selType ="popPin";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType, searchAgentStr : null  } , PAGE.FORM1, "searchAgentNm");
				
				var tblName ="CREATE";
				
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsMngCodeHeaderList.json",{tblNm :tblName }, PAGE.FORM1, "searchManageCodeHeader");

				var selType2 ="dealer";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType2, searchAgentStr : null  } , PAGE.FORM1, "searchDealerNm");
				
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0062"  } , PAGE.FORM1, "searchFreeFlag");
				
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0017"}, PAGE.FORM1, "searchCancelFlag");
			}

		};
		
		
	</script>
