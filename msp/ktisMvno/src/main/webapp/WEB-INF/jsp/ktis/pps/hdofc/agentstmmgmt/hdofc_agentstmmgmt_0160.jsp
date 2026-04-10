<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : hdofc_agentstmmgmt_0160.jsp
 * @Description : 대리점별 정산 관리
 * @Modification Information
 *
 *   수정일 			수정자				 수정내용
 *  -------		--------		---------------------------
 *  2017.05.01	김 웅				최초 생성
 *
 * author 김 웅
 * since 2017.05.01
 */
 %>
	<!-- 화면 배치 -->
	
	<div id="FORM1" class="section-search"></div>
	<div id="GRID1" class="section-full"></div>
	<div id="FORM11" class="section-box"></div>  
	<div id="FORM12" class="section-application"></div>  
			
	<!-- Script -->
	<script type="text/javascript">
	
	function setCurrentDate(form, name)
	{
		form.setItemValue(name, new Date());
	}
	
	var DHX = {
		FORM1 : {
			title : "",
			items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
							,{type : "calendar",dateFormat : "%Y-%m",serverDateFormat: "%Y%m",name : "startDt",label : "정산월",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : "", labelWidth:100}
							,{type : "newcolumn", offset:10} 
							,{type : "input",label : "대리점 ",name : "searchAgentNm", width:100,maxlength:20, labelWidth:100}
							,{type : "newcolumn", offset:9} 
							,{type : "select",name : "searchAgentId",width:157}
					 ]},// 1row
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by hans
					 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search1"} 
				],
	
			onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnSearchPps":
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/agentstmmgmt/AgentStmSelfList.json", form, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						
						break;
	
				}
	
			},
			 onValidateMore : function(data){
				 /*
				 if(data.startDt > data.endDt){
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
				}
				 */
			 },	
			onKeyUp : function(inp, ev, name, value)
			{
				if(ev.keyCode == 13)
				{
					switch(name)
					{
						case "searchAgentNm":
							var agentStr  = this.getItemValue("searchAgentNm");
							var selType ="popPin";
							//alert(agentStr);
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
									{ 
									  selectType : selType , 
								      searchAgentStr : agentStr
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
			}
		}, // FORM1 End
		GRID1 : {
			css : {
				height : "540px"
			},
			title : "조회결과",
			header : "정산월,대리점명,정산액,#cspan,실적,#cspan,#cspan,위탁수수료,#cspan,#cspan,유지수수료,#cspan,#cspan,프로모션수수료,#cspan,#cspan,환수,#cspan,#cspan,전월미정산,#cspan,기타정산,#cspan,지급수수료,#cspan,등록일자,등록관리자,agentId",
			header2 :"#rspan,#rspan,카드,현금,개통,해지,실정산,건수,카드,현금,건수,카드,현금,건수,카드,현금,건수,카드,현금,카드,현금,카드,현금,카드,현금,#rspan,#rspan,#rspan",
			columnIds : "billMonth,agentNm,opPps,opOpenAmt,opOpenCnt,opCanCnt,opRealCnt,psdCnt,psdPps,psdAmt,mngCnt,mngPps,mngAmt,prmCnt,prmPps,prmAmt,rfCnt,rfPps,rfAmt,lastPps,lastAmt,etcPps,etcAmt,totPps,totAmt,regDt,regAdminNm,agentId",
			widths : "90,120,70,100,70,70,70,70,70,100,70,70,100,70,70,100,70,70,100,70,100,70,100,70,100,110,120,0",
			colTypes : "ro,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ro,ro,ro",
			colSorting : "str,str,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,str,str,str",
			colAlign : "center,center,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,center,center,center",
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
					btnDel :{label :"보기"}
				}
			},
			onButtonClick : function(btnId) {

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
						var url = "/pps/hdofc/agentstmmgmt/AgentStmSelfListExcel.json";

						var searchData =  PAGE.FORM1.getFormData(true);
						
						console.log("searchData",searchData);
							
						mvno.cmn.download(url+"?menuId=PPS1910016",true,{postData:searchData});
						break;
						
					case "btnReg":
						
						mvno.ui.createForm("FORM11");
						PAGE.FORM11.clear();
						
						var uploader = PAGE.FORM11.getUploader("file_upload1");
						
						uploader.revive();
						
						PAGE.FORM11.attachEvent("onBeforeFileAdd",function(realName, size, file){
					 		if(realName.substr(realName.length-3, realName.length) != 'xls' && realName.substr(realName.length-3, realName.length) != 'XLS'){
					 			mvno.ui.alert("정산등록은 xls파일만 가능합니다.");
					 			return false;
					 		}
	                	    return true;
				       });
						
						PAGE.FORM11.attachEvent("onFileAdd",function(file){
					 		PAGE.FORM11.setItemValue("fileName", file);
				       });
			    		
					   PAGE.FORM11.attachEvent("onUploadComplete",function(count){
							//var fileName = PAGE.FORM11.getItemValue("fileName");
							//var data = {"fileName":fileName};
					   });
						
						PAGE.FORM11.attachEvent("onBeforeFileRemove",function(realName, serverName){
							//PAGE.FORM10.setItemValue("excelCnt", "");
							
							//PAGE.FORM10.setItemValue("smsTitle", "");
	                	    return true;
				       });
						
						setCurrentDate(PAGE.FORM11, "billMonth");
						
						PAGE.FORM11.clearChanged();
			            mvno.ui.popupWindowById("FORM11", "정산등록", 500, 250, {
			                onClose: function(win) {
			                	if (! PAGE.FORM11.isChanged()) return true;
			                	mvno.ui.confirm("CCMN0005", function(){
			                		win.closeForce();
			                	});
			                },
			                onClose2: function(win) {
			                	uploader.reset();
			                }
		                });
					
						break;
						
					case "btnMod":
						
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						mvno.ui.createForm("FORM12");
						
						//mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0099" } , PAGE.FORM12, "status1");
						
						PAGE.FORM12.setItemLabel("billMonthStr",rowData.billMonth);
						PAGE.FORM12.setItemLabel("agentNmStr",rowData.agentNm);

						PAGE.FORM12.setItemValue("opPps",rowData.opPps);
						PAGE.FORM12.setItemValue("opOpenAmt",rowData.opOpenAmt);
						PAGE.FORM12.setItemValue("opOpenCnt",rowData.opOpenCnt);
						PAGE.FORM12.setItemValue("opCanCnt",rowData.opCanCnt);
						PAGE.FORM12.setItemValue("psdCnt",rowData.psdCnt);
						PAGE.FORM12.setItemValue("psdPps",rowData.psdPps);
						PAGE.FORM12.setItemValue("psdAmt",rowData.psdAmt);
						PAGE.FORM12.setItemValue("mngCnt",rowData.mngCnt);
						PAGE.FORM12.setItemValue("mngPps",rowData.mngPps);
						PAGE.FORM12.setItemValue("mngAmt",rowData.mngAmt);
						PAGE.FORM12.setItemValue("prmCnt",rowData.prmCnt);
						PAGE.FORM12.setItemValue("prmPps",rowData.prmPps);
						PAGE.FORM12.setItemValue("prmAmt",rowData.prmAmt);
						PAGE.FORM12.setItemValue("rfCnt",rowData.rfCnt);
						PAGE.FORM12.setItemValue("rfPps",rowData.rfPps);
						PAGE.FORM12.setItemValue("rfAmt",rowData.rfAmt);
						PAGE.FORM12.setItemValue("lastPps",rowData.lastPps);
						PAGE.FORM12.setItemValue("lastAmt",rowData.lastAmt);
						PAGE.FORM12.setItemValue("etcPps",rowData.etcPps);
						PAGE.FORM12.setItemValue("etcAmt",rowData.etcAmt);
						
						PAGE.FORM12.setItemValue("opCode","MOD");
						PAGE.FORM12.setItemValue("billMonth",rowData.billMonth);
						PAGE.FORM12.setItemValue("agentId",rowData.agentId);
						
						PAGE.FORM12.clearChanged();
			            mvno.ui.popupWindowById("FORM12", "정산수정", 800, 650, {
			                onClose: function(win) {
			                	if (! PAGE.FORM12.isChanged()) return true;
			                	mvno.ui.confirm("CCMN0005", function(){
			                		win.closeForce();
			                	});
			                }
		                });
					
						break;
						
					case "btnDel":
						
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/pps/ppsStmSelfForm.jrf" + "&arg=";
						param = encodeURI(param);
						
						var arg = "agentId#" + rowData.agentId + "#billMonth#" + rowData.billMonth + "#";
						arg = encodeURIComponent(arg);
						
						param = param + arg;
						
						var msg = "대리점수수료내역서를 출력하시겠습니까?";
						mvno.ui.confirm(msg, function() {
							mvno.ui.popupWindow("/cmn/report/report.do"+param, "대리점수수료내역서 ", 900, 700, {
								onClose: function(win) {
									win.closeForce();
								}
							});
						});
						
						break;
				}
			}
		}, // GRID1 End
		FORM11: {
			title : "정산등록"
			,items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "calendar",dateFormat : "%Y-%m",serverDateFormat: "%Y%m",name : "billMonth",label : "정산월",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : "", labelWidth:100}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type:"block", blockOffset: 0, list: [	
		 					   	{type: "upload", label:"첨부파일" 
		 					   	,name: "file_upload1"
		 					   	,inputWidth: 330 
		 						,url: "/pps/hdofc/agentstmmgmt/uploadAgentStmSelfExcelFile.do" 
		 					    ,userdata:{limitSize:10000} 
		 					   //	,swfPath: "uploader.swf"
		 					   	,autoStart: true 
		 					   // ,swfUrl: "/lgs/prdtsrlmgmt/fileUpUsingExcel.do"
		 						},
		 						{type:"newcolumn", offset:5},
					 			{type:"button", name: "excelSample", value:"샘플"}
		 					]}
					 ]}
				]
			,buttons: {
			   	center : {
		    		btnSave : { label : "등록" }, 
		    		btnAllAgentClose : { label : "닫기" }
				}
			}
			,onKeyUp : function(inp, ev, name, value)
			{
			}
			,onChange : function (name, value)
			{
				
            }
			,onButtonClick : function(btnId) {
				    	
				var form = this;
					switch (btnId) {
						case "btnSave": 
							var data = PAGE.FORM11.getFormData(true);
							
							mvno.ui.confirm("정산을 등록하시겠습니까?", function(){
								var url = ROOT + "/pps/hdofc/agentstmmgmt/uploadAgentStmSelfExcelProc.json";
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.alert(msg);
									//console.log(results);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.GRID1.refresh();
										PAGE.FORM11.clear();
									}
									
								});
							});
							break;
							
						case "excelSample" :  
		                	var url = "/pps/hdofc/agentstmmgmt/agentStmSelfExcelSample.json";
		   				 	mvno.cmn.download(url, false, null);
		                    break;
		                    
						case "btnAllAgentClose":	
							mvno.ui.closeWindowById("FORM11", true);
			                break;
						}
			}
		}, // FORM11 End
		FORM12: {
			items : [ 	
					 {type:"block", list:[
					 	{type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
					 	,{type : "label",label:"정산월",name : "",width:300 ,labelWidth:110, offsetLeft: 10}
					 	,{type:"newcolumn"}
					 	,{type : "label",label:"",name : "billMonthStr",width:300 ,labelWidth:150}
					 ]},// 1row
					 {type:"block", list:[
					 	{type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
					 	,{type : "label",label:"대리점명",name : "",width:300 ,labelWidth:110, offsetLeft: 10}
					 	,{type:"newcolumn"}
					 	,{type : "label",label:"",name : "agentNmStr",width:300 ,labelWidth:150}
					 ]},// 1row
					 {type:"block", list:[
					 	{type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
					 	,{type : "label",label:"",name : "",width:300 ,labelWidth:110}
					 ]},// 1row
					 {type: 'fieldset', label: '정산액', inputWidth:700, offsetLeft:0, list:[
						 {type : "input",label : "카드",name : "opPps", width:70, validate: 'ValidInteger' ,labelWidth:30}
						 ,{type:"newcolumn"}
						 ,{type : "input",label : "현금",name : "opOpenAmt", width:100, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
					 ]},// 1row
					 {type: 'fieldset', label: '실적', inputWidth:700, offsetLeft:0, list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:30, blockOffset:0}
						 ,{type : "input",label : "개통",name : "opOpenCnt", width:80, validate: 'ValidInteger' ,labelWidth:30}
						 ,{type:"newcolumn"}
						 ,{type : "input",label : "해지",name : "opCanCnt", width:100, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
					 ]},// 2row
					 {type: 'fieldset', label: '위탁수수료', inputWidth:700, offsetLeft:0, list:[
   						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:30, blockOffset:0}
   						 ,{type : "input",label : "건수",name : "psdCnt", width:80, validate: 'ValidInteger' ,labelWidth:30}
   						 ,{type:"newcolumn"}
   						 ,{type : "input",label : "카드",name : "psdPps", width:80, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
  						 ,{type:"newcolumn"}
   						 ,{type : "input",label : "현금",name : "psdAmt", width:100, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
   					 ]},// 2row
   					{type: 'fieldset', label: '유지수수료', inputWidth:700, offsetLeft:0, list:[
  						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:30, blockOffset:0}
  						 ,{type : "input",label : "건수",name : "mngCnt", width:80, validate: 'ValidInteger' ,labelWidth:30}
  						 ,{type:"newcolumn"}
  						 ,{type : "input",label : "카드",name : "mngPps", width:80, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
 						 ,{type:"newcolumn"}
  						 ,{type : "input",label : "현금",name : "mngAmt", width:100, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
  					 ]},// 2row
    				 {type: 'fieldset', label: '프로모션수수료', inputWidth:700, offsetLeft:0, list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:30, blockOffset:0}
						 ,{type : "input",label : "건수",name : "prmCnt", width:80, validate: 'ValidInteger' ,labelWidth:30}
						 ,{type:"newcolumn"}
						 ,{type : "input",label : "카드",name : "prmPps", width:80, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
					 ,{type:"newcolumn"}
						 ,{type : "input",label : "현금",name : "prmAmt", width:100, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
					 ]},// 2row
					 {type: 'fieldset', label: '환수', inputWidth:700, offsetLeft:0, list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:30, blockOffset:0}
						 ,{type : "input",label : "건수",name : "rfCnt", width:80, validate: 'ValidInteger' ,labelWidth:30}
						 ,{type:"newcolumn"}
						 ,{type : "input",label : "카드",name : "rfPps", width:80, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
					 ,{type:"newcolumn"}
						 ,{type : "input",label : "현금",name : "rfAmt", width:100, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
					 ]},// 2row
					 {type: 'fieldset', label: '전월미정산', inputWidth:700, offsetLeft:0, list:[
 						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:30, blockOffset:0}
 						 ,{type : "input",label : "카드",name : "lastPps", width:80, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
 					 	 ,{type:"newcolumn"}
 						 ,{type : "input",label : "현금",name : "lastAmt", width:100, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
 					 ]},// 2row
 					{type: 'fieldset', label: '기타', inputWidth:700, offsetLeft:0, list:[
   						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:30, blockOffset:0}
   						 ,{type : "input",label : "카드",name : "etcPps", width:80, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
   					 	 ,{type:"newcolumn"}
   						 ,{type : "input",label : "현금",name : "etcAmt", width:100, validate: 'ValidInteger' ,labelWidth:30, offsetLeft: 10}
   					 ]},// 2row
   					{type : 'hidden', name: "agentId", value:""},
					{type : 'hidden', name: "billMonth", value:""},
   					{type : 'hidden', name: "opCode", value:""}
				]
			,buttons: {
			   	center : {
		    		btnSave : { label : "수정" }, 
		    		btnAllAgentClose : { label : "닫기" }
				}
			}
			,onKeyUp : function(inp, ev, name, value)
			{
			}
			,onChange : function (name, value)
			{
				
            }
			,onButtonClick : function(btnId) {
				    	
				var form = this;
					switch (btnId) {
						case "btnSave": 
							var data = PAGE.FORM12.getFormData(true);
							console.log("data", data);
							/*
							if(!data.status1){
				        		//this.pushError("data.refundGubun","환수  구분",["ICMN0001"]);
				        		mvno.ui.notify("수수료 상태는  필수 입력 값입니다.");
				        		return;
				
				        	}
							*/
							mvno.ui.confirm("정산을 수정하시겠습니까?", function(){
								var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsAgentStmSelfProc.json";
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.alert(msg);
									//console.log(results);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.GRID1.refresh();
										PAGE.FORM12.clear();
									}
									
								});
							});
							
							break;
						case "btnAllAgentClose":	
							mvno.ui.closeWindowById("FORM12", true);
			                break;
						}
			}
		} // FORM12 End

	};
	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
			buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {

				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				setCurrentDate(PAGE.FORM1, "startDt");
				//setCurrentDate(PAGE.FORM1, "endDt");
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("agentId" ) ,true);
				//PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("linenum" ) ,true);

				var selType ="popPin";
				mvno.cmn.ajax4lov(
						ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
						, {
							selectType : selType
							}
						 , PAGE.FORM1, "searchAgentId");
			

			}

		};
		
		
	</script>