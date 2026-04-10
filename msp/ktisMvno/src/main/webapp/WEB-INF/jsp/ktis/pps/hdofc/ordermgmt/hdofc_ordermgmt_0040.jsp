<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : hdofc_ordermgmt_0040.jsp
 * @Description : 출고관리
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
	
	var old_url = "";
	
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
	
	function setForm11Data(orderNo){
		mvno.cmn.ajax(ROOT + "/pps/hdofc/ordermgmt/PpsOrderRefreshList.json", {orderNo : orderNo}, function(results) {
			if(results.result.data.rows[0].sendOrgFile == "" || results.result.data.rows[0].sendOrgFile == null){
				PAGE.FORM11.setItemLabel("sendOrgFileLabel", "");
			}else{
				PAGE.FORM11.setItemLabel("sendOrgFileLabel", results.result.data.rows[0].sendOrgFile);
			}
			PAGE.FORM11.showItem("btnFileDown");
			PAGE.FORM11.setItemValue("sendFileFlag", "Y");
			PAGE.FORM11.setItemValue("remarkOld", results.result.data.rows[0].remark);
			PAGE.FORM11.setItemValue("remark", "");
			PAGE.FORM11.setItemValue("reqRemarkOld", results.result.data.rows[0].reqRemark);
			PAGE.FORM11.setItemValue("reqRemark", "");
		});
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
							,{type : "input",label : "대리점 ",name : "searchAgentNm", width:100,maxlength:20}
							,{type : "newcolumn", offset:3}
							,{type : "select",name : "searchAgentId",width:167}
							,{type : "newcolumn", offset:10}
							,{type : "select",label : "상태",name : "searchStatus",width:100, labelWidth:60}
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
							,{type : "select",label : "배송방법",name : "searchDlvMethod",width:101, labelWidth:60}
							,{type : "newcolumn", offset:10}
							,{type : "select",label : "결제방법",name : "searchPayMethod",width:100, labelWidth:60}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select",label : "결제여부",name : "searchPayFlag",width:100, labelWidth:60}
						 
					 ]},// 1row
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search3"} 
				],
	
			onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnSearchPps":
						
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/ordermgmt/orderInfoList.json", form, {
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
				height : "510px"
			},
			title : "조회결과",
			header : "주문번호,주문일자,상태,대리점명,품명,상품코드,상품명,선택1,선택2,주문수량,주문단가,배정수량,배정금액,총청구액(VAT포함),배송방법,배정일자,파일등록여부,결제여부,결제방법,결제일자,결제관리자,인수확인일자",
			columnIds : "orderNo,orderDt,statusNm,agentNm,typeNm,cd,cdNm,op1Nm,op2Nm,reqOrderCnt,reqSaleAmt,sendCnt,sendAmt,sendTotAmt,dlvMethodNm,sendDt,sendFileFlag,payFlag,payMethodNm,payDt,payAdminNm,endDt",
			widths : "160,130,80,120,80,80,110,80,80,80,80,80,80,120,90,100,90,90,90,100,120,110", //총 1500
			colAlign : "center,center,center,center,center,center,center,center,center,right,right,right,right,right,center,center,center,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ron,ron,ron,ron,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,int,int,int,int,int,str,str,str,str,str,str,str,str",
			paging : true,
			multiCheckbox : true,
			pageSize :300,
			pagingStyle :2,
			buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				},
				right :{
					btnReg :{label :"출고지시"},
					btnMod :{label :"출고보류"},
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
						var url = "/pps/hdofc/ordermgmt/orderInfoListExcel.json";

						 var searchData =  PAGE.FORM1.getFormData(true);

							 console.log("searchData",searchData);
							
						  mvno.cmn.download(url+"?menuId=PPS1920004",true,{postData:searchData});
						  
						break;
					case "btnReg":
						
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						if(rowData.status != "10" && rowData.status != "15" && rowData.status != "19" && rowData.status != "20" ){
							mvno.ui.alert("주문확정/출고지시/출고보류/출고확정상태가 아닙니다");
							return;
						}
						
						mvno.ui.createForm("FORM11");
						PAGE.FORM11.clear();
						
						var selType ="popPin";
						mvno.cmn.ajax4lov(
								ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
								, {
									selectType : selType
									}
								 , PAGE.FORM11, "agentId");
						
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0120" } , PAGE.FORM11, "dlvMethod");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/orderGoodsType.json",{searchType : "", searchShowFlag : ""} , PAGE.FORM11, "type");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0121" } , PAGE.FORM11, "payMethod");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0110" } , PAGE.FORM11, "payFlag");
						
						var type = PAGE.FORM11.getItemValue("type");
						
						PAGE.FORM11.setItemValue("agentId", rowData.agentId);
						PAGE.FORM11.setItemValue("type", rowData.type);
						PAGE.FORM11.setItemValue("cd", rowData.cd);
						PAGE.FORM11.setItemValue("cdNm", rowData.cdNm);
						PAGE.FORM11.setItemValue("op1", rowData.op1Nm);
						PAGE.FORM11.setItemValue("op2", rowData.op2Nm);
						PAGE.FORM11.setItemValue("op3", rowData.op3Nm);
						PAGE.FORM11.setItemValue("dlvAddr", rowData.dlvAddr);
						PAGE.FORM11.setItemValue("dlvMethod", rowData.dlvMethod);
						if(rowData.dlvReqDt == null){
							PAGE.FORM11.setItemValue("dlvReqDt", "");
						}else{
							PAGE.FORM11.setItemValue("dlvReqDt", new Date(rowData.dlvReqDt));
						}
						PAGE.FORM11.setItemValue("dlvAddr", rowData.dlvAddr);
						PAGE.FORM11.setItemValue("dlvName", rowData.dlvName);
						PAGE.FORM11.setItemValue("dlvPhone", rowData.dlvPhone);
						PAGE.FORM11.setItemValue("dlvNo", rowData.dlvNo);
						PAGE.FORM11.setItemValue("reqRemarkOld", rowData.reqRemark);
						PAGE.FORM11.setItemValue("remarkOld", rowData.remark);
						PAGE.FORM11.setItemValue("payMethod", rowData.payMethod);
						PAGE.FORM11.setItemValue("payFlag", rowData.payFlag);
						PAGE.FORM11.setItemValue("agentId", rowData.agentId);
						PAGE.FORM11.setItemValue("orderNo", rowData.orderNo);
						PAGE.FORM11.setItemValue("sendFileFlag", rowData.sendFileFlag);
						
						if(rowData.sendOrgFile == "" || rowData.sendOrgFile == null){
							PAGE.FORM11.setItemLabel("sendOrgFileLabel", "");
						}else{
							PAGE.FORM11.setItemLabel("sendOrgFileLabel", rowData.sendOrgFile);
						}
						
						
						if(rowData.sendCnt == "" || rowData.sendCnt == "0"){
							PAGE.FORM11.setItemValue("sendAmt", rowData.reqSaleAmt);	
							PAGE.FORM11.setItemValue("sendCnt", rowData.reqOrderCnt);	
							var sendTotAmt = PAGE.FORM11.getItemValue("sendAmt") * PAGE.FORM11.getItemValue("sendCnt") * 1.1;
							sendTotAmt = Math.floor(sendTotAmt);
							PAGE.FORM11.setItemValue("sendTotAmt", sendTotAmt);	
						}else{
							PAGE.FORM11.setItemValue("sendAmt", rowData.sendAmt);
							PAGE.FORM11.setItemValue("sendCnt", rowData.sendCnt);
							PAGE.FORM11.setItemValue("sendTotAmt", rowData.sendTotAmt);
						}
						
						var sendTotAmtOld = PAGE.FORM11.getItemValue("sendAmt") * PAGE.FORM11.getItemValue("sendCnt");
						PAGE.FORM11.setItemValue("sendTotAmtOld", sendTotAmtOld);
						
						if(rowData.sendFileFlag == "Y"){
							PAGE.FORM11.showItem("btnFileDown");
						}else{
							PAGE.FORM11.hideItem("btnFileDown");
						}
						
						PAGE.FORM11.disableItem("payMethod");
						PAGE.FORM11.disableItem("payFlag");
						PAGE.FORM11.disableItem("sendAmt");
						PAGE.FORM11.disableItem("sendCnt");
						PAGE.FORM11.disableItem("agentId");
						PAGE.FORM11.disableItem("type");
						PAGE.FORM11.disableItem("cd");
						PAGE.FORM11.disableItem("cdNm");
						PAGE.FORM11.disableItem("op1");
						PAGE.FORM11.disableItem("op2");
						PAGE.FORM11.disableItem("op3");
						PAGE.FORM11.disableItem("reqOrderCnt");
						PAGE.FORM11.disableItem("saleAmt");
						PAGE.FORM11.disableItem("sendTotAmt");
						PAGE.FORM11.disableItem("sendTotAmtOld");
						PAGE.FORM11.disableItem("dlvReqDt");
						
						var uploader = PAGE.FORM11.getUploader("file_upload1");
		                
						if(old_url == ""){
							old_url = uploader._url;
						}
						
	                    var url = old_url + "?agentId=" + rowData.agentId;
		                uploader._url = url;   
		                uploader._swf_upolad_url = url;
		               
	                    uploader.revive();
	                    
	                    PAGE.FORM11.attachEvent("onBeforeFileAdd",function(realName, size, file){
		                      uploader._url = old_url; 
		                      return true; 
	                    });
						
						PAGE.FORM11.clearChanged();
			            mvno.ui.popupWindowById("FORM11", "출고지시", 700, 820, {
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
							if(checkedData.OLD[i].status != "20" && checkedData.OLD[i].status != "30"){
								mvno.ui.alert("출고완료, 인수확정 상태만 거래명세서 출력이 가능합니다");
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
						
					case "btnMod":
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						if(rowData.status != "15" && rowData.status != "20"){
							mvno.ui.alert("출고지시/출고완료상태가 아닙니다");
							return;
						}
						
						mvno.ui.prompt("출고보류사유", function(result){
							var msg = "출고보류로 변경하시겠습니까?";
							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							
							rowData.opCode = "OUT_HOLD";
							rowData.remark = result;
							
							mvno.cmn.ajax4confirm(msg, url, rowData, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									PAGE.GRID1.refresh();
								}
							},{timeout:60000});
							
						} , { required:true,lines:1,maxLength : 80 });
					
						break;
				}
			}
		}, // GRID1 End
		FORM11: {
			title : "출고지시"
			,items : [ 	
					 {type: 'hidden', name: 'orderNo', value: ''}
					 ,{type: 'hidden', name: 'sendFileFlag', value: ''}
					 ,{type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
	  	                 ,{type : "select",name : "agentId",label:"대리점", width:300, labelWidth:100}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "품목", name:"type", width:140, labelWidth:100, required:true}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"상품코드", name: "cd", width:140, labelWidth:100, readonly:true}
						 ,{type:"newcolumn", offset:12}
						 ,{type:"input", label:"", name: "cdNm", width:200, readonly:true}
					 ]},// 2row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"선택1", name: "op1", width:140, labelWidth:100, readonly:true}
					 ]},// 4row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"선택2", name: "op2", width:140, labelWidth:100, readonly:true}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"선택3", name: "op3", width:140, labelWidth:100, readonly:true}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "배송방법", name:"dlvMethod", width:140, labelWidth:100, required:true}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "dlvReqDt",label : "희망배송일자", value : "",enableTime : false, calendarPosition : "right",inputWidth : 140, labelWidth:110}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "배송주소",name : "dlvAddr",width:455 ,labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "수취인명",name : "dlvName", width:140, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "연락처",name : "dlvPhone", width:140, labelWidth:109}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "송장번호",name : "dlvNo", width:140, labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "주문요청사항",name : "reqRemarkOld",width:455, rows:5 ,labelWidth:100, readonly:true}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "추가",name : "reqRemark",width:400 ,labelWidth:100}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"button", value:"저장", name:"btnReqRemarkSave"}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "배정수량",name : "sendCnt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", maxLength : 10, required:true, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "배정단가",name : "sendAmt", width:140, labelWidth:109}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "배정금액",name : "sendTotAmtOld", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", maxLength : 10, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "총청구액(VAT포함)",name : "sendTotAmt", width:140, labelWidth:109}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "select", label : "결제방법", name:"payMethod", width:140, labelWidth:100, required:true}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "결제상태", name:"payFlag", width:140, labelWidth:100, required:true}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"block", blockOffset: 0, list: [	
         					   	{type: "upload", label:"첨부파일"
         					   	,name: "file_upload1"
         					   	,width:330
         					   	,inputWidth: 330 
         						,url: "/pps/ordermgmt/ppsFileEncUpLoad.do" 
         					    ,userdata:{limitSize:10000} 
         					   //	,swfPath: "uploader.swf"
         					   	,autoStart: true 
         					   // ,swfUrl: "/lgs/prdtsrlmgmt/fileUpUsingExcel.do"
         						}
         					]}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"button", value:"저장", name:"btnFileSave"}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"button", value:"다운", name:"btnFileDown"}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 ,{type:"label", label:"파일명", name:"", labelWidth:100}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"label", label:"", name:"sendOrgFileLabel", labelWidth:400}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "관리자메모",name : "remarkOld",width:455, rows:5 ,labelWidth:100, readonly:true}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "추가",name : "remark",width:400 ,labelWidth:100}
						 ,{type:"newcolumn", offset:5}
						 ,{type:"button", value:"저장", name:"btnRemarkSave"}
					 ]}
				]
			,buttons: {
			   	center : {
		    		btnInfoReg1 : { label : "출고지시" },
		    		btnInfoReg2 : { label : "출고완료" },
		    		btnPopClose : { label : "닫기" }
				}
			}
			,onKeyUp : function(inp, ev, name, value)
			{
				
			}
			,onInputChange : function(name,val,inp) {
				
			}
			,onChange : function (name, value)
			{
				
            }
			,onButtonClick : function(btnId) {
				    	
				var form = this;
					switch (btnId) {
						case "btnInfoReg1":
							var data = PAGE.FORM11.getFormData(true);
							
							data.opCode = "OUT_ADD";
							data.outAddFlag = "N";
							
							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							var msg = "출고지시 처리하시겠습니까?";
							
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									mvno.ui.closeWindowById(form, true);  
									//mvno.ui.notify("NCMN0015");
									PAGE.FORM11.clear();
									PAGE.GRID1.refresh();
								}
							});
							
							break;
							
						case "btnInfoReg2":
							var data = PAGE.FORM11.getFormData(true);
							
							data.opCode = "OUT_ADD";
							data.outAddFlag = "Y";	
							
							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							var msg = "출고완료 처리하시겠습니까?";
							
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									mvno.ui.closeWindowById(form, true);  
									//mvno.ui.notify("NCMN0015");
									PAGE.FORM11.clear();
									PAGE.GRID1.refresh();
								}
							});
							
							break;
							
						case "btnFileDown":	
							var data = PAGE.FORM11.getFormData(true);
							
							if(data.sendFileFlag == "Y") {
								var url = "/pps/hdofc/ordermgmt/orderFileDown.json";
								mvno.cmn.download(url+"?orderNo="+data.orderNo+"&agentId=BONSA");
							}else{
								mvno.ui.alert("등록된 파일이 없습니다");
							}
							
			                break;
			                
						case "btnFileSave":
							var data = PAGE.FORM11.getFormData(true);
							
							data.opCode = "FILE_SAVE";
							
							if(data.file_upload1_count == 0){
								mvno.ui.alert("파일을 등록해 주세요");
								return;
							}

							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							var msg = "첨부파일을 등록 하시겠습니까?";
							var orderNo = data.orderNo;
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									setForm11Data(orderNo);
									PAGE.GRID1.refresh();
								}
								
								PAGE.FORM11.setItemValue("file_upload1");
								
							});
							
							break;
							
						case "btnRemarkSave":
							
							var data = PAGE.FORM11.getFormData(true);
							
							data.opCode = "ORDER_RPY";
							
							if(data.remark == ""){
								mvno.ui.alert("관리자메모를 입력해주세요");
								return;
							}
							
							data.reqRemark = "";

							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							var msg = "관리자메모를 등록 하시겠습니까?";
							var orderNo = data.orderNo;
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									setForm11Data(orderNo);
									PAGE.GRID1.refresh();
								}
								
							});
							
							break;
							
						case "btnReqRemarkSave":
							var data = PAGE.FORM11.getFormData(true);
							
							data.opCode = "ORDER_RPY";
							
							if(data.reqRemark == ""){
								mvno.ui.alert("주문요청사항을 입력해주세요");
								return;
							}
							
							data.remark = "";

							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderInfoProc.json";
							var msg = "주문요청사항을 등록 하시겠습니까?";
							var orderNo = data.orderNo;
							mvno.cmn.ajax4confirm(msg, url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									setForm11Data(orderNo);
									PAGE.GRID1.refresh();
								}
								
							});
							
							break;
							
						case "btnPopClose":	
							mvno.ui.closeWindowById("FORM11", true);
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
				
				setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
				setCurrentDate(PAGE.FORM1, "endDt");
				
				var selType ="popPin";
				mvno.cmn.ajax4lov(
						ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
						, {
							selectType : selType
							}
						 , PAGE.FORM1, "searchAgentId");
				
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM1, "searchType");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0119" } , PAGE.FORM1, "searchStatus");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0122" } , PAGE.FORM1, "searchItem");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0120" } , PAGE.FORM1, "searchDlvMethod");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0121" } , PAGE.FORM1, "searchPayMethod");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0110" } , PAGE.FORM1, "searchPayFlag");
				
				PAGE.FORM1.setItemValue("searchStatus", "OUT");
				
			}

		};
		
		
	</script>