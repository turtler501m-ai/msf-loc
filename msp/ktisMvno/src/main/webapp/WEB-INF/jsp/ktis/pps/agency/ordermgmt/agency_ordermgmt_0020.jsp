<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : agency_ordermgmt_0010.jsp
 * @Description : 인수관리
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
	
	var DHX = {
		FORM1 : {
			title : "",
			items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
							,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "주문일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""}
							,{type : "newcolumn", offset:3}
							,{type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
								,{type: "label", label: "~"}
		                   	]}	 
		                    ,{type : "newcolumn", offset:3} 
							,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""}
							,{type : "newcolumn", offset:10}
							,{type : "select",label : "상태",name : "searchStatus",width:100, labelWidth:60}
							,{type : "newcolumn", offset:10}
							,{type : "select",label : "배송방법",name : "searchDlvMethod",width:101, labelWidth:60}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
							,{type : "select",label:"검색",name : "searchItem",width:100} 	
		                   	,{type : "newcolumn", offset:3}
		                   	,{type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
								,{type: "label", label: ":"}
		                 	 ]}	 
		                   	,{type : "newcolumn", offset:3}
	 			            ,{type : "input",name : "searchSValue", width:100, maxLength:20}
							,{type : "newcolumn", offset:10}
							,{type : "select",label : "품명",name : "searchType",width:101, labelWidth:60}
							,{type : "newcolumn", offset:10}
							,{type : "select",label : "결제방법",name : "searchPayMethod",width:100, labelWidth:60}
							,{type : "newcolumn", offset:10}
							,{type : "select",label : "결제여부",name : "searchPayFlag",width:100, labelWidth:60}
					 ]},// 1row
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search2"} 
				],
	
			onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnSearch":
						
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/agency/ordermgmt/orderInfoList.json", form, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						
						break;
	
				}
	
			},
			 onValidateMore : function(data){
				 null;
			 },	
			 onKeyUp : function(inp, ev, name, value)
			{
				 
			}
		}, // FORM1 End
		GRID1 : {

			css : {
				height : "510px"
			},
			title : "조회결과",
			header : "주문번호,주문일자,상태,대리점명,품명,상품코드,상품명,선택1,선택2,주문수량,주문단가,배정수량,배정금액,총청구액(VAT포함),배송방법,배정일자,파일등록여부,결제여부,결제방법,결제일자,인수확인일자",
			columnIds : "orderNo,orderDt,statusNm,agentNm,typeNm,cd,cdNm,op1Nm,op2Nm,reqOrderCnt,reqSaleAmt,sendCnt,sendAmt,sendTotAmt,dlvMethodNm,sendDt,sendFileFlag,payFlag,payMethodNm,payDt,endDt",
			widths : "160,130,80,120,80,80,110,80,80,80,80,80,80,120,90,100,90,90,90,100,110", //총 1500
			colAlign : "center,center,center,center,center,center,center,center,center,right,right,right,right,right,center,center,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ron,ron,ron,ron,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,int,int,int,int,int,str,str,str,str,str,str,str",
			multiCheckbox : true,
			pageSize :300,
			pagingStyle :2,
			buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				},
				right :{
					btnReg :{label :"인수확정"},
					btnMod :{label :"파일다운로드"},
					btnDel :{label :"거래명세서"}
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
						var url = "/pps/agency/ordermgmt/orderInfoListExcel.json";

						 var searchData =  PAGE.FORM1.getFormData(true);

							 console.log("searchData",searchData);
							
						  mvno.cmn.download(url+"?menuId=PPS2300002",true,{postData:searchData});
						  
						break;
					case "btnReg":
						
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						if(rowData.status != "20"){
							mvno.ui.alert("출고확정상태가 아닙니다");
							return;
						}
						
						rowData.opCode = "AGENT_CONFIRM";
						
						var url = ROOT + "/pps/agency/ordermgmt/ppsOrderInfoProc.json";
						var msg = "주문번호(" + rowData.orderNo + ")<br/>주문건 인수확정 하시겠습니까?<br/>(결제완료, 배송완료건만)";
						
						mvno.cmn.ajax4confirm(msg, url, rowData, function(results) {
							var result = results.result;
							var retCode = result.retCode;
							var msg = result.retMsg;
							mvno.ui.alert(msg);
							//console.log(results);
							if(retCode == "0000") {
								PAGE.GRID1.refresh();
							}
						});
					
						break;
						
					case "btnMod":	
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						if(rowData.status != "30"){
							mvno.ui.alert("인수확정 상태가 아닙니다.");
							return;
						}
						
						if(rowData.sendFileFlag == "Y") {
							var url = ROOT + "/pps/agency/ordermgmt/orderFileDown.json";
							mvno.cmn.download(url+"?orderNo="+rowData.orderNo);
						}else{
							mvno.ui.alert("등록된 파일이 없습니다");
						}
						
		                break;
		                
					case "btnDel":
						
						var self = PAGE.GRID1;
						
						var rowIds = self.getCheckedRows(0);

						if (! rowIds) {
						    mvno.ui.alert("ECMN0003");
						    return;
						}
						
						var checkedData = self.classifyRowsFromIds(rowIds, "orderNo,agentId,status,sendDt");
						
						var orderNoStr = "";
						var agentIdStr = "";
						var sendDtStr = "";
						for(var i=0; i<checkedData.OLD.length; i++){
							if(checkedData.OLD[i].status != "30"){
								mvno.ui.alert("인수확정 상태만 거래명세서 출력이 가능합니다");
							    return;
							}
							
							if(checkedData.OLD[i].orderNo != ""){
								if(orderNoStr == ""){
									orderNoStr = orderNoStr + checkedData.OLD[i].orderNo;	
								}else{
									orderNoStr = orderNoStr + "," + checkedData.OLD[i].orderNo;
								}
							}
							
							if(i == 0){
								agentIdStr = checkedData.OLD[i].agentId;
							}else{
								if(agentIdStr != checkedData.OLD[i].agentId){
									mvno.ui.alert("동일하지 않은 대리점이 있습니다");
								    return;
								}
							}
							
							if(i == 0){
								sendDtStr = checkedData.OLD[i].sendDt.substring(0,7);
							}else{
								if(sendDtStr != checkedData.OLD[i].sendDt.substring(0,7)){
									mvno.ui.alert("출고월이 같은 내역만 출력 가능합니다");
								    return;
								}
							}
							
						}
						
						if(orderNoStr == "" || agentIdStr == "" || sendDtStr == ""){
							mvno.ui.alert("필수항목 누락");
						    return;
						}
						
						mvno.cmn.ajax(ROOT + "/pps/hdofc/commonmgmt/PpsCodeDescInfo.json", {eventCd : "PPS_ORDER_INFO_REPORT_DESC"}, function(results) {
							var descInfo = results.result.data.rows[0].descInfo;
							
							var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/pps/ppsOrdAgntForm.jrf" + "&arg=";
							param = encodeURI(param);
							
							var arg = "agentId#" + agentIdStr + "#orderNo#" + orderNoStr + "#descInfo#" + descInfo + "#sendDt#" + sendDtStr + "#";
							arg = encodeURIComponent(arg);
							
							param = param + arg;
							
							var msg = "거래명세서를 출력하시겠습니까?";
							mvno.ui.confirm(msg, function() {
								mvno.ui.popupWindow("/cmn/report/report.do"+param, "대리점 인수확정 내역서 ", 900, 700, {
									onClose: function(win) {
										win.closeForce();
									}
								});
							});
						});
						
						break;

				}
			}
		} // GRID1 End

	};
	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
			buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {

				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				
				setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
				setCurrentDate(PAGE.FORM1, "endDt");
				
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM1, "searchType");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0119" } , PAGE.FORM1, "searchStatus");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0122" } , PAGE.FORM1, "searchItem");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0120" } , PAGE.FORM1, "searchDlvMethod");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0121" } , PAGE.FORM1, "searchPayMethod");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0110" } , PAGE.FORM1, "searchPayFlag");
				
				PAGE.FORM1.setItemValue("searchStatus", "END");
				
			}

		};
		
		
	</script>