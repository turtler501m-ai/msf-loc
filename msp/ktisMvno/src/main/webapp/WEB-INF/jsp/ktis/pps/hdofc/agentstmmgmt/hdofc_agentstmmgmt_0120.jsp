<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : hdofc_agentstmmgmt_0120.jsp
 * @Description : 통합수수료 정산
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
	
	function setPreYearDate(form, name)
	{
		var today = new Date();

		var mm = today.getMonth(); //January is 0!
		var yyyy = today.getFullYear();
		var dd = today.getDate();

		form.setItemValue(name, new Date(yyyy-1,mm,dd));
	}
	
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
						 ,{type : "calendar",dateFormat : "%Y-%m",serverDateFormat: "%Y%m",name : "startDt",label : "정산월", labelWidth:50, value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""} 
							,{type : "newcolumn", offset:3}
							,{type:"block", list:[
													{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
													,{type: "label", label: "~"},
							                   	 ]}
							,{type : "newcolumn", offset:3}
							,{type : "calendar",dateFormat : "%Y-%m",serverDateFormat: "%Y%m",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""}
					 ]},// 1row
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by hans
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],
	
			onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnSearch":
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/agentstmmgmt/AgentStmList.json", form, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						
						
						break;
	
				}
	
			},
			 onValidateMore : function(data){
			 },	
			onKeyUp : function(inp, ev, name, value)
			{
				
			}
		}, // FORM1 End
		GRID1 : {

			css : {
				height : "540px"
			},
			title : "조회결과",
			header : "정산월,기본,#cspan,유심,#cspan,GRADE,#cspan,우량,#cspan,명변,#cspan,재충전,#cspan,자동이체,#cspan,무료충전환수액,환수,#cspan,조정,#cspan,수수료조정,#cspan,합계,#cspan,마감여부,마감관리자,마감일자,등록관리자,등록일자,메모",
			header2 :"#rspan,카드,현금,카드,현금,카드,현금,카드,현금,카드,현금,카드,현금,카드,현금,현금,카드,현금,카드,현금,카드,현금,카드,현금,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan",
			columnIds : "billMonth,basicCardSd,basicCashSd,usimCardSd,usimCashSd,gradeCardSd,gradeCashSd,ulCardSd,ulCashSd,mbCardSd,mbCashSd,rcgCardSd,rcgCashSd,cmsCardSd,cmsCashSd,refundFreeRcgAmt,refundCardSd,refundCashSd,modCardSd,modCashSd,modAgentCardSd,modAgentCashSd,cardSd,cashSd,endFlag,endAdminNm,endDt,regAdminNm,regDt,remark",
			widths : "90,100,100,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,300",
			colTypes : "ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ro,ro,ro,ro,ro,ro",
			colSorting : "str,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,str,str,str,str,str,str",
			colAlign : "center,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,center,center,center,center,center,left",
			paging : true,
			pageSize :15,
			pagingStyle :2,
			buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				},
				right :{
					btnReg :{label :"정산"}
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
						var url = "/pps/hdofc/agentstmmgmt/AgentStmListExcel.json";

						var searchData =  PAGE.FORM1.getFormData(true);
						
						console.log("searchData",searchData);
							
						mvno.cmn.download(url+"?menuId=PPS1910014",true,{postData:searchData});
						break;
						
					case "btnReg":
						
						mvno.ui.createForm("FORM11");
						PAGE.FORM11.clearChanged();
						PAGE.FORM11.disableItem("btnBasic");
						PAGE.FORM11.disableItem("btnGrade1");
						PAGE.FORM11.disableItem("btnGrade1_2");
						PAGE.FORM11.disableItem("btnMb");
						PAGE.FORM11.disableItem("btnRcg");
						PAGE.FORM11.disableItem("btnRefund");
						PAGE.FORM11.disableItem("btnNouse");
						PAGE.FORM11.disableItem("btnReopen");
						PAGE.FORM11.disableItem("btnGrade15");
						PAGE.FORM11.disableItem("btnGrade15_2");
						PAGE.FORM11.disableItem("btnUl");
						PAGE.FORM11.disableItem("btnUl_2");
						PAGE.FORM11.disableItem("btnCms");
						PAGE.FORM11.disableItem("btnStmAgent");
						PAGE.FORM11.disableItem("btnStm");
						PAGE.FORM11.disableItem("btnStmEnd");
						PAGE.FORM11.setItemLabel("btnStmAgent", "대리점정산");
						PAGE.FORM11.setItemLabel("btnStm", "통합정산");
						PAGE.FORM11.setItemLabel("btnStmEnd", "확정");
						
						mvno.cmn.ajax(
							ROOT + "/pps/hdofc/agentstmmgmt/AgentStmCntList.json",
							null,
							function(result) {
								var result = result.result;
								var data = result.data;
								
								PAGE.FORM11.setItemValue("agentFlag", data.rows[0].agentFlag);
								PAGE.FORM11.setItemValue("stmFlag", data.rows[0].stmFlag);
								PAGE.FORM11.setItemValue("endFlag", data.rows[0].endFlag);
								
								if(data.rows[0].agentFlag == "Y"){
									PAGE.FORM11.enableItem("btnBasic");
									PAGE.FORM11.enableItem("btnGrade1");
									PAGE.FORM11.enableItem("btnGrade1_2");
									PAGE.FORM11.enableItem("btnMb");
									PAGE.FORM11.enableItem("btnRcg");
									PAGE.FORM11.enableItem("btnRefund");
									PAGE.FORM11.enableItem("btnNouse");
									PAGE.FORM11.enableItem("btnReopen");
									PAGE.FORM11.enableItem("btnGrade15");
									PAGE.FORM11.enableItem("btnGrade15_2");
									PAGE.FORM11.enableItem("btnUl");
									PAGE.FORM11.enableItem("btnUl_2");
									PAGE.FORM11.enableItem("btnCms");
									PAGE.FORM11.enableItem("btnStmAgent");
								}else{
									if(data.rows[0].stmFlag == "Y"){
										PAGE.FORM11.enableItem("btnStmAgent");
										PAGE.FORM11.enableItem("btnStm");
										PAGE.FORM11.setItemLabel("btnStmAgent", "대리점정산취소");
									}else{
										if(data.rows[0].endFlag == "Y"){
											PAGE.FORM11.enableItem("btnStm");
											PAGE.FORM11.enableItem("btnStmEnd");
											PAGE.FORM11.setItemLabel("btnStm", "통합정산취소");	
										}else{
											PAGE.FORM11.enableItem("btnStmEnd");
											PAGE.FORM11.setItemLabel("btnStmEnd", "확정취소");
										}
									}
								}
								
								var m = new Date();
								
								PAGE.FORM11.setItemLabel("billMonthNm", (m.getMonth()+1) + "월");
								
								mvno.ui.popupWindowById("FORM11", "수수료정산", 400, 500, {
					                onClose: function(win) {
					                	if (! PAGE.FORM11.isChanged()) return true;
					                	mvno.ui.confirm("CCMN0005", function(){
					                		win.closeForce();
					                	});
					                }
				                });
								
				        });	
						
						
						
						
						break;
				}
			}
		},  // GRID1 End
		FORM11: {
			title : " "
			,items : [ 	
					{type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"정산월",name : "",width:300 ,labelWidth:160}
						 ,{type:"newcolumn"}
						 ,{type : "label",label:"",name : "billMonthNm",width:300 ,labelWidth:100}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"기본/USIM수수료",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"초기화", name:"btnBasic"}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"Grade수수료(1일)",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"초기화", name:"btnGrade1"}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"재정산", name:"btnGrade1_2"}
					 ]}, // 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"명의변경수수료",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"초기화", name:"btnMb"}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"재충전수수료",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"초기화", name:"btnRcg"}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"비정상해지",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"초기화", name:"btnRefund"}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"사용기준미달",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"초기화", name:"btnNouse"}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"해지후재가입",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"초기화", name:"btnReopen"}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"Grade수수료(15일)",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"초기화", name:"btnGrade15"}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"재정산", name:"btnGrade15_2"}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"우량수수료",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"초기화", name:"btnUl"}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"재정산", name:"btnUl_2"}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"자동이체수수료",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"초기화", name:"btnCms"}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"대리점별정산",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"대리점정산", name:"btnStmAgent"}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"통합정산",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"통합정산", name:"btnStm"}
						 ,{type:"newcolumn"}
						 ,{type:"button", value:"확정", name:"btnStmEnd"}
					 ]}, // 1row
					 {type : 'hidden', name: "agentFlag", value:""},
					 {type : 'hidden', name: "stmFlag", value:""},
					 {type : 'hidden', name: "endFlag", value:""}
				]
			,buttons: {
			   	center : { 
		    		btnClose : { label : "닫기" }
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
						case "btnBasic":
							var data = PAGE.FORM11.getFormData(true);
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmBasicProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnGrade1":
							var data = PAGE.FORM11.getFormData(true);
							data.opCode = "REG";
							
							var today = new Date();

							var mm = today.getMonth()+1; //January is 0!
							if((""+mm).length == 1) mm = "0" + mm; 
							var yyyy = today.getFullYear();
							
							data.billMonth = yyyy + "-" + mm + "-01";
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmGradeProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnGrade1_2":
							var data = PAGE.FORM11.getFormData(true);
							data.opCode = "MOD";
							var today = new Date();

							var mm = today.getMonth()+1; //January is 0!
							if((""+mm).length == 1) mm = "0" + mm; 
							var yyyy = today.getFullYear();
							
							data.billMonth = yyyy + "-" + mm + "-01";
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmGradeProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
						
						case "btnMb":
							var data = PAGE.FORM11.getFormData(true);
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmMbProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnRcg":
							var data = PAGE.FORM11.getFormData(true);
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmRcgProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
						
						case "btnRefund":
							var data = PAGE.FORM11.getFormData(true);
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmRefundProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnNouse":
							var data = PAGE.FORM11.getFormData(true);
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmNouseProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnReopen":
							var data = PAGE.FORM11.getFormData(true);
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmReopenProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnGrade15":
							var data = PAGE.FORM11.getFormData(true);
							data.opCode = "REG";
							var today = new Date();

							var mm = today.getMonth()+1; //January is 0!
							if((""+mm).length == 1) mm = "0" + mm; 
							var yyyy = today.getFullYear();
							
							data.billMonth = yyyy + "-" + mm + "-15";
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmGradeProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnGrade15_2":
							var data = PAGE.FORM11.getFormData(true);
							data.opCode = "MOD";
							var today = new Date();

							var mm = today.getMonth()+1; //January is 0!
							if((""+mm).length == 1) mm = "0" + mm; 
							var yyyy = today.getFullYear();
							
							data.billMonth = yyyy + "-" + mm + "-15";
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmGradeProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnUl":
							var data = PAGE.FORM11.getFormData(true);
							data.opCode = "REG";
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmUlProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnUl_2":
							var data = PAGE.FORM11.getFormData(true);
							data.opCode = "MOD";
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmUlProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnCms":
							var data = PAGE.FORM11.getFormData(true);
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmCmsProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnStmAgent":
							var data = PAGE.FORM11.getFormData(true);
							
							if(data.agentFlag == "Y"){
								data.opCode = "REG";
							}else{
								data.opCode = "CAN";
							}
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmAgentProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnStm":
							var data = PAGE.FORM11.getFormData(true);
							
							if(data.stmFlag == "Y"){
								data.opCode = "STM";
							}else{
								data.opCode = "STM_CAN";
							}
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnStmEnd":
							var data = PAGE.FORM11.getFormData(true);
							
							if(data.endFlag == "Y"){
								data.opCode = "END";
							}else{
								data.opCode = "END_CAN";
							}
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmProc.json";
								mvno.ui.confirm("CCMN0005", function(){
								mvno.cmn.ajax(url, data, function(results) {
									var result = results.result;
									var retCode = result.retCode;
									var msg = result.retMsg;
									mvno.ui.notify(msg);
									if(retCode == "0000") {
										mvno.ui.closeWindowById(form, true);  
										PAGE.FORM11.clear();
										PAGE.GRID1.refresh();
									}
									
								});
							});
							
							break;
							
						case "btnClose":	
							mvno.ui.closeWindowById("FORM11", true);
			                break;
						}
			}
		}, // FORM11 End

	};
	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
			buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {

				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				
				setPreYearDate(PAGE.FORM1, "startDt");
				setCurrentDate(PAGE.FORM1, "endDt");
				
				var url = ROOT + "/pps/hdofc/agentstmmgmt/AgentStmList.json";
//	            PAGE.GRID1.list(url);
				PAGE.GRID1.list(url, null, { callback : gridOnLoad });

			}

		};
	
	function gridOnLoad  ()
	{
		
	};
		
		
	</script>