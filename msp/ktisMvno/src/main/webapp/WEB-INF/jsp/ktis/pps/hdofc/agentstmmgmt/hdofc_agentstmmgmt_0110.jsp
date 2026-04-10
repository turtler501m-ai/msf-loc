<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : hdofc_agentstmmgmt_0110.jsp
 * @Description : 대리점별 정산 내역
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
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/agentstmmgmt/AgentStmHistoryList.json", form, {
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
			header : "정산월,대리점명,기본,#cspan,유심,#cspan,GRADE,#cspan,우량,#cspan,명변,#cspan,재충전,#cspan,자동이체,#cspan,무료충전환수액,환수,#cspan,조정,#cspan,수수료조정,#cspan,합계,#cspan,등록관리자,등록일자,메모,agentId",
			header2 :"#rspan,#rspan,카드,현금,카드,현금,카드,현금,카드,현금,카드,현금,카드,현금,카드,현금,현금,카드,현금,카드,현금,카드,현금,카드,현금,#rspan,#rspan,#rspan,#rspan",
			columnIds : "billMonth,agentNm,basicCardSd,basicCashSd,usimCardSd,usimCashSd,gradeCardSd,gradeCashSd,ulCardSd,ulCashSd,mbCardSd,mbCashSd,rcgCardSd,rcgCashSd,cmsCardSd,cmsCashSd,refundFreeRcgAmt,refundCardSd,refundCashSd,modCardSd,modCashSd,modAgentCardSd,modAgentCashSd,cardSd,cashSd,regAdminNm,regDt,remark,agentId",
			widths : "90,100,100,100,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,300,0",
			colTypes : "ro,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ro,ro,ro,ro",
			colSorting : "str,str,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,str,str,str,str",
			colAlign : "center,center,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,center,center,left,center",
			paging : true,
			pageSize :15,
			pagingStyle :2,
			buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				},
				right :{
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
						var url = "/pps/hdofc/agentstmmgmt/AgentStmHistoryListExcel.json";

						var searchData =  PAGE.FORM1.getFormData(true);
						
						console.log("searchData",searchData);
							
						mvno.cmn.download(url+"?menuId=PPS1910013",true,{postData:searchData});
						break;
					case "btnReg":
						
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						mvno.ui.createForm("FORM11");
						
						//mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0099" } , PAGE.FORM11, "status1");
						
						PAGE.FORM11.setItemValue("cardSd",rowData.modAgentCardSd);
						PAGE.FORM11.setItemValue("cashSd",rowData.modAgentCashSd);
						//PAGE.FORM11.setItemValue("depSd",rowData.modAgentDepSd);
						PAGE.FORM11.setItemValue("remark",rowData.remark);
						
						PAGE.FORM11.setItemValue("billMonth",rowData.billMonth);
						PAGE.FORM11.setItemValue("agentId",rowData.agentId);
						
						PAGE.FORM11.clearChanged();
			            mvno.ui.popupWindowById("FORM11", "수수료  변경", 500, 350, {
			                onClose: function(win) {
			                	if (! PAGE.FORM11.isChanged()) return true;
			                	mvno.ui.confirm("CCMN0005", function(){
			                		win.closeForce();
			                	});
			                }
		                });
					
						break;
				}
			}
		}, // GRID1 End
		FORM11: {
			title : "수수료 상태 변경"
			,items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
							,{type : "input",label : "카드 수수료",name : "cardSd",width:200 ,labelWidth:100}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
							,{type : "input",label : "현금 수수료",name : "cashSd",width:200 ,labelWidth:100}
					 ]},// 2row
					 /*
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
							,{type : "input",label : "예치금 수수료",name : "depSd",width:200 ,labelWidth:100}
					 ]},// 3row
					 */
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "메모",name : "remark",width:300 ,labelWidth:100},
						 {type : 'hidden', name: "agentId", value:""},
						 {type : 'hidden', name: "billMonth", value:""}
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
							/*
							if(!data.status1){
				        		//this.pushError("data.refundGubun","환수  구분",["ICMN0001"]);
				        		mvno.ui.notify("수수료 상태는  필수 입력 값입니다.");
				        		return;
				
				        	}
							*/
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsAgentStmAgentModProc.json";
							mvno.cmn.ajax(url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.notify(msg);
								//console.log(results);
								if(retCode == "0000") {
									mvno.ui.closeWindowById(form, true);  
									PAGE.GRID1.refresh();
									PAGE.FORM11.clear();
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
			

			}

		};
		
		
	</script>