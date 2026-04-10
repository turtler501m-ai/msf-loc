<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : hdofc_agentstmmgmt_0140.jsp
 * @Description : 조정 수수료
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
	<!-- 조정 수수료 등록  -->
	<div id="FORM10"></div>
	<!-- 수수료상태 -->
	<div id="FORM11" class="section-search"></div>  
			
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
							,{type : "newcolumn", offset:9} 
							,{type : "input",label : "대리점 ",name : "searchAgentNm", width:100,maxlength:20, labelWidth:100}
							,{type : "newcolumn", offset:8} 
							,{type : "select",name : "searchAgentId",width:157}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
	  			          	{type : "select",label:"수수료상태",name : "searchStatus", width:100, labelWidth:100}
	  			            ,{type : "newcolumn",offset:9}
							,{type : "select",label:"검색",name : "searchSItem", width:100, labelWidth:100} 	
		                   	,{type : "newcolumn", offset:3}
		                   	,{type:"block", list:[
	 									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
	 									,{type: "label", label: ":"}
	 			                 	 ]}	 
							,{type : "newcolumn"}
	 			            ,{type : "input",name : "searchSValue", width:158, maxLength:20}
					 ]},
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by hans
					 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search2"} 
				],
	
			onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnSearchPps":
						
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/agentstmmgmt/AgentStmModList.json", form, {
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
			header : "정산월,계약번호,대리점명,판매점명,카드조정,현금조정,수수료상태,등록관리자,등록일자,메모",
			columnIds : "billMonth,contractNum,agentNm,agentSaleNm,cardSd,cashSd,statusNm,regAdminNm,regDt,remark",
			widths : "90,100,120,100,100,110,110,110,110,300",
			colTypes : "ro,ro,ro,ro,ro,ron,ron,ron,ron,ron",
			colSorting : "str,str,str,str,str,int,int,int,int,int",
			colAlign : "center,center,center,center,center,right,right,right,right,left",
			paging : true,
			pageSize :15,
			pagingStyle :2,
			buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				},
				right :{
					btnSave :{label :"조정수수료 등록"},
					btnReg :{label :"수정"}
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
						var url = "/pps/hdofc/agentstmmgmt/AgentStmModListExcel.json";

						var searchData =  PAGE.FORM1.getFormData(true);
						
						console.log("searchData",searchData);
							
						mvno.cmn.download(url+"?menuId=PPS1910012",true,{postData:searchData});
						break;
					case "btnReg":
						
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						mvno.ui.createForm("FORM11");
						
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0100" } , PAGE.FORM11, "status1");
						
						PAGE.FORM11.setItemValue("status1",rowData.status);
						PAGE.FORM11.setItemValue("remark",rowData.remark);
						
						PAGE.FORM11.setItemValue("billMonth",rowData.billMonth);
						PAGE.FORM11.setItemValue("contractNum",rowData.contractNum);
						
						PAGE.FORM11.clearChanged();
			            mvno.ui.popupWindowById("FORM11", "수수료 상태 변경", 500, 230, {
			                onClose: function(win) {
			                	if (! PAGE.FORM11.isChanged()) return true;
			                	mvno.ui.confirm("CCMN0005", function(){
			                		win.closeForce();
			                	});
			                }
		                });
					
						break;
					case "btnSave":
						
						var fileLimitCnt = 1;
		  			       
						mvno.ui.createForm("FORM10");
						PAGE.FORM10.clear();
						
						//mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0104" } , PAGE.FORM10, "refundGubun");
						
						var uploader = PAGE.FORM10.getUploader("file_upload1");
						
						uploader.revive();
					 	
					 	PAGE.FORM10.attachEvent("onBeforeFileAdd",function(realName, size, file){
					 		if(realName.substr(realName.length-3, realName.length) != 'xls' && realName.substr(realName.length-3, realName.length) != 'XLS'){
					 			mvno.ui.alert("환수 등록은 xls파일만 가능합니다.");
					 			return false;
					 		}
	                	    return true;
				       });
					 	
					 	PAGE.FORM10.attachEvent("onFileAdd",function(file){
					 		PAGE.FORM10.setItemValue("fileName", file);
					 		console.log();
	                	   
				       });
			    		
						PAGE.FORM10.attachEvent("onUploadComplete",function(count){
							var fileName = PAGE.FORM10.getItemValue("fileName");
							var data = {"fileName":fileName};
							
							console.log(data);
							
							//PAGE.GRID1.list(ROOT + "/pps/smsmgmt/readSmsExcelFile.do", data, {callback : function() {
							//	PAGE.FORM3.setItemValue("excelCnt", "총 전송가능수 : " + PAGE.GRID1.getRowsNum());
							//}});
					   });
						
						PAGE.FORM10.attachEvent("onBeforeFileRemove",function(realName, serverName){
							//PAGE.FORM10.setItemValue("excelCnt", "");
							
							//PAGE.FORM10.setItemValue("smsTitle", "");
	                	    return true;
				       });


	                   mvno.ui.popupWindowById("FORM10", "조정수수료 등록", 600, 200, {
	                	    onClose2: function(win) {
	                	        uploader.reset();
	                	      
	                	    }
	                   });	
		                   
					break;
				}
			}
		}, // GRID1 End
		FORM10 : {
			items : [
			 		{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80},
			 			{type: 'fieldset', label: '조정 수수료 엑셀등록',  inputWidth:500, offsetLeft:10, list:[
			 			{type: "block", list: [
			 			{type:"block", blockOffset: 0, list: [	
			 					   	{type: "upload", label:"첨부파일" 
			 					   	,name: "file_upload1"
			 					   	,inputWidth: 330 
			 						,url: "/pps/hdofc/agentstmmgmt/uploadAgentStmModExcelFile.do" 
			 					    ,userdata:{limitSize:10000} 
			 					   //	,swfPath: "uploader.swf"
			 					   	,autoStart: true 
			 					   // ,swfUrl: "/lgs/prdtsrlmgmt/fileUpUsingExcel.do"
			 						},
			 						{type:"newcolumn", offset:5},
						 			{type:"button", name: "excelSample", value:"샘플"}
			 					]}
			 			]}]},
			 			{type: 'hidden', name: 'fileName', value: ''}
		 	],
			buttons: {
	            center: {
	            	btnSave: { label: "등록" },
	                btnClose: { label: "닫기" }
	            }
	        },
	        onChange : function (name, value){
	            var form = this;
	        },
	        onButtonClick : function(btnId) {
	
	            var form = this; // 혼란방지용으로 미리 설정 (권고)
	            var fileLimitCnt = 1;
	            
	            switch (btnId) {
	               
					case "btnSave":
	                	
						var data = PAGE.FORM10.getFormData(true);
						console.log("data", data);
						
						/*
						if(!data.refundGubun){
			        		//this.pushError("data.refundGubun","환수  구분",["ICMN0001"]);
			        		mvno.ui.notify("환수  구분 필수 입력 값입니다.");
			        		return;
			
			        	}
						else if(!data.file_upload1_count&&data.file_upload1_count==0){
			        		
			        		//this.pushError("data.upload1","파일",["ICMN0001"]);
			        		mvno.ui.notify("파일 첨부 필수 입력 값입니다.");
			        		return;
			        	}
						*/
						
						var url = ROOT + "/pps/hdofc/agentstmmgmt/uploadAgentStmModExcelProc.json";
						mvno.cmn.ajax(url, data, function(results) {
							var result = results.result;
							var retCode = result.retCode;
							var msg = result.retMsg;
							mvno.ui.notify(msg);
							//console.log(results);
							if(retCode == "0000") {
								mvno.ui.closeWindowById(form, true);  
								//mvno.ui.notify("NCMN0015");
								PAGE.FORM10.clear();
							}
							
						});
	
	                	break;	
	                case "btnClose" :  
						mvno.ui.closeWindowById(form, true);
	                    break;
	                
	                case "excelSample" :  
	                	var url = "/pps/hdofc/agentstmmgmt/agentStmModExcelSample.json";
	   				 	mvno.cmn.download(url, false, null);
	                    break;
	            }
	        },
	        onValidateMore : function (data){
	        	
	        	
	        	var uploaders = this.getUploader("file_upload1");
	        	//console.log(uploaders);
	        	if(!data.refundGubun){
	        		this.pushError("data.refundGubun","환수 등록 구분",["ICMN0001"]);
	
	        	}
	        	
	        	else if(!data.file_upload1_count&&data.file_upload1_count==0){
	        		
	        		this.pushError("data.upload1","파일",["ICMN0001"]);
	        	}
	        	
	        	
	        }
		
		}, // FORM10 End
		FORM11: {
			title : "수수료 상태 변경"
			,items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
							,{type : "select",label : "수수료상태",name : "status1",width:200 ,labelWidth:100}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "메모",name : "remark",width:300 ,labelWidth:100},
						 {type : 'hidden', name: "billMonth", value:""},
						 {type : 'hidden', name: "contractNum", value:""}
					 ]}
				]
			,buttons: {
			   	center : {
		    		btnMod : { label : "수정" }, 
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
						case "btnMod": 
							var data = PAGE.FORM11.getFormData(true);
							console.log("data", data);
							
							if(!data.status1){
				        		//this.pushError("data.refundGubun","환수  구분",["ICMN0001"]);
				        		mvno.ui.notify("수수료 상태는  필수 입력 값입니다.");
				        		return;
				
				        	}
							
							data.opCode = "MOD";
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmStatusChgProc.json";
							mvno.cmn.ajax(url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.notify(msg);
								//console.log(results);
								if(retCode == "0000") {
									mvno.ui.closeWindowById(form, true);  
									//mvno.ui.notify("NCMN0015");
									PAGE.FORM11.clear();
									PAGE.GRID1.refresh();
								}
								
							});
							break;
						case "btnAllAgentClose":	
							mvno.ui.closeWindowById("FORM11", true);
			                break;
						}
			}
		} // FORM11 End

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
				//PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("seq" ) ,true);
				//PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("linenum" ) ,true);

				var selType ="popPin";
				mvno.cmn.ajax4lov(
						ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
						, {
							selectType : selType
							}
						 , PAGE.FORM1, "searchAgentId");
				
				//mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsFeatureCodeList.json", null, PAGE.FORM1, "searchFeatures");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0100" } , PAGE.FORM1, "searchStatus");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS0054" } , PAGE.FORM1, "searchSItem");			

			}

		};
		
		
	</script>