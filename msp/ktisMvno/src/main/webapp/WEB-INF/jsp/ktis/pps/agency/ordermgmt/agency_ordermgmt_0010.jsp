<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : agency_ordermgmt_0010.jsp
 * @Description : 주문관리
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
	<div id="FORM12" class="section-search"></div>  
	<div id="FORM13" class="section-search"></div>      
			
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
	
	function setAgentDlv(){

		var url = ROOT + "/pps/agency/ordermgmt/orderAgentInfo.json";
		mvno.cmn.ajax(url, null, function(results) {
			if(results.result.data.rows.length == 0){
				PAGE.FORM11.setItemValue("dlvAddr", "");
				PAGE.FORM11.setItemValue("dlvName", "");
				PAGE.FORM11.setItemValue("dlvPhone", "");
			}else{
				if(results.result.data.rows[0].agentAddr == "()"){
					PAGE.FORM11.setItemValue("dlvAddr", "");	
				}else{
					PAGE.FORM11.setItemValue("dlvAddr", results.result.data.rows[0].agentAddr);	
				}
				
				PAGE.FORM11.setItemValue("dlvName", results.result.data.rows[0].rprsenNm);
				PAGE.FORM11.setItemValue("dlvPhone", results.result.data.rows[0].telnum);
			}
		});
	}
	
	function form11SetChangeValue(name, type, showFlag){
		if(name == "TYPE"){
			mvno.cmn.ajax4lov(ROOT + "/pps/agency/ordermgmt/orderGoodsCd.json",{searchType : type, searchShowFlag : showFlag, cdFlag : "Y"} , PAGE.FORM11, "cd");
		}
		
		var url = ROOT + "/pps/agency/ordermgmt/orderGoodsList.json";
		var cd = PAGE.FORM11.getItemValue("cd");
		mvno.cmn.ajax(url, {searchType : type, searchPopCd : cd}, function(results) {
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP1"} , PAGE.FORM11, "op1");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP2"} , PAGE.FORM11, "op2");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP3"} , PAGE.FORM11, "op3");
			
			if(results.result.data.rows.length == 0){
				PAGE.FORM11.setItemValue("cdNm", "");
				PAGE.FORM11.setItemValue("op1", "");
				PAGE.FORM11.setItemValue("op2", "");
				PAGE.FORM11.setItemValue("op3", "");
				PAGE.FORM11.setItemValue("saleAmt", "");
				
			}else{
				PAGE.FORM11.setItemValue("cdNm", results.result.data.rows[0].cdNm);
				PAGE.FORM11.setItemValue("op1", results.result.data.rows[0].op1);
				PAGE.FORM11.setItemValue("op2", results.result.data.rows[0].op2);
				PAGE.FORM11.setItemValue("op3", results.result.data.rows[0].op3);
				PAGE.FORM11.setItemValue("saleAmt", results.result.data.rows[0].saleAmt+"");
			}
			
		});
		
	}
	
	function form12SetChangeValue(name, type, showFlag, firstFlag, op1, op2, op3){
		if(name == "TYPE"){
			mvno.cmn.ajax4lov(ROOT + "/pps/agency/ordermgmt/orderGoodsCd.json",{searchType : type, searchShowFlag : showFlag, cdFlag : "Y"} , PAGE.FORM12, "cd");
		}
		
		var url = ROOT + "/pps/agency/ordermgmt/orderGoodsList.json";
		var cd = PAGE.FORM12.getItemValue("cd");
		mvno.cmn.ajax(url, {searchType : type, searchPopCd : cd}, function(results) {
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP1"} , PAGE.FORM12, "op1");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP2"} , PAGE.FORM12, "op2");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP3"} , PAGE.FORM12, "op3");
			
			if(firstFlag == "Y"){
				PAGE.FORM12.setItemValue("op1", op1);
				PAGE.FORM12.setItemValue("op2", op2);
				PAGE.FORM12.setItemValue("op3", op3);	
			}else{	
				if(results.result.data.rows.length == 0){
					PAGE.FORM12.setItemValue("cdNm", "");
					PAGE.FORM12.setItemValue("op1", "");
					PAGE.FORM12.setItemValue("op2", "");
					PAGE.FORM12.setItemValue("op3", "");
					PAGE.FORM12.setItemValue("saleAmt", "");
					
				}else{
					PAGE.FORM12.setItemValue("cdNm", results.result.data.rows[0].cdNm);
					PAGE.FORM12.setItemValue("op1", results.result.data.rows[0].op1);
					PAGE.FORM12.setItemValue("op2", results.result.data.rows[0].op2);
					PAGE.FORM12.setItemValue("op3", results.result.data.rows[0].op3);
					PAGE.FORM12.setItemValue("saleAmt", results.result.data.rows[0].saleAmt+"");
				}
			}
			
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
			paging : true,
			pageSize :15,
			pagingStyle :2,
			buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				},
				right :{
					btnReg :{label :"주문등록"},
					btnMod :{label :"주문수정"},
					btnDel :{label :"주문보기"}
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
							
						  mvno.cmn.download(url+"?menuId=PPS2300001",true,{postData:searchData});
						  
						break;
					case "btnReg":
						
						mvno.ui.createForm("FORM11");
						PAGE.FORM11.clear();
						
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0120" } , PAGE.FORM11, "dlvMethod");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM11, "type");
						
						var type = PAGE.FORM11.getItemValue("type");
						
						setAgentDlv();
						form11SetChangeValue("TYPE", type, "Y");
						
						PAGE.FORM11.disableItem("op1");
						PAGE.FORM11.disableItem("op2");
						PAGE.FORM11.disableItem("op3");
						PAGE.FORM11.disableItem("saleAmt");
						PAGE.FORM11.disableItem("totSaleAmt");
						
						PAGE.FORM11.clearChanged();
			            mvno.ui.popupWindowById("FORM11", "주문등록", 700, 500, {
			                onClose: function(win) {
			                	if (! PAGE.FORM11.isChanged()) return true;
			                	mvno.ui.confirm("CCMN0005", function(){
			                		win.closeForce();
			                	});
			                }
		                });
					
						break;

					case "btnMod":
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						if(rowData.status != "01"){
							mvno.ui.alert("주문등록 상태가 아닙니다");
							return;
						}
						
						mvno.ui.createForm("FORM12");
						PAGE.FORM12.clear();
						
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0120" } , PAGE.FORM12, "dlvMethod");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM12, "type");
						
						var type = PAGE.FORM12.getItemValue("type");
						
						form12SetChangeValue("TYPE", type, "Y", "Y", rowData.op1, rowData.op2, rowData.op3);
						
						PAGE.FORM12.setItemValue("type", rowData.type);
						PAGE.FORM12.setItemValue("cd", rowData.cd);
						PAGE.FORM12.setItemValue("cdNm", rowData.cdNm);
						PAGE.FORM12.setItemValue("reqOrderCnt", rowData.reqOrderCnt);
						PAGE.FORM12.setItemValue("saleAmt", rowData.reqSaleAmt);
						PAGE.FORM12.setItemValue("totSaleAmt", rowData.reqSaleAmt * rowData.reqOrderCnt);
						PAGE.FORM12.setItemValue("dlvAddr", rowData.dlvAddr);
						PAGE.FORM12.setItemValue("dlvMethod", rowData.dlvMethod);
						if(rowData.dlvReqDt == null){
							PAGE.FORM12.setItemValue("dlvReqDt", "");
						}else{
							PAGE.FORM12.setItemValue("dlvReqDt", new Date(rowData.dlvReqDt));
						}
						PAGE.FORM12.setItemValue("dlvAddr", rowData.dlvAddr);
						PAGE.FORM12.setItemValue("dlvName", rowData.dlvName);
						PAGE.FORM12.setItemValue("dlvPhone", rowData.dlvPhone);
						PAGE.FORM12.setItemValue("reqRemarkOld", rowData.reqRemark);
						PAGE.FORM12.setItemValue("orderNo", rowData.orderNo);
						
						PAGE.FORM12.disableItem("orderNo");
						PAGE.FORM12.disableItem("saleAmt");
						PAGE.FORM12.disableItem("totSaleAmt");
						PAGE.FORM12.disableItem("op1");
						PAGE.FORM12.disableItem("op2");
						PAGE.FORM12.disableItem("op3");
						
						PAGE.FORM12.clearChanged();
			            mvno.ui.popupWindowById("FORM12", "주문수정", 700, 600, {
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
						
						mvno.ui.createForm("FORM13");
						PAGE.FORM13.clear();
						
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0120" } , PAGE.FORM13, "dlvMethod");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM13, "type");
						
						var type = PAGE.FORM13.getItemValue("type");
						
						PAGE.FORM13.setItemValue("type", rowData.type);
						PAGE.FORM13.setItemValue("cd", rowData.cd);
						PAGE.FORM13.setItemValue("cdNm", rowData.cdNm);
						PAGE.FORM13.setItemValue("op1", rowData.op1Nm);
						PAGE.FORM13.setItemValue("op2", rowData.op2Nm);
						PAGE.FORM13.setItemValue("op3", rowData.op3Nm);
						PAGE.FORM13.setItemValue("reqOrderCnt", rowData.reqOrderCnt);
						PAGE.FORM13.setItemValue("saleAmt", rowData.reqSaleAmt);
						PAGE.FORM13.setItemValue("totSaleAmt", rowData.reqSaleAmt * rowData.reqOrderCnt);
						PAGE.FORM13.setItemValue("dlvMethod", rowData.dlvMethod);
						if(rowData.dlvReqDt == null){
							PAGE.FORM13.setItemValue("dlvReqDt", "");
						}else{
							PAGE.FORM13.setItemValue("dlvReqDt", new Date(rowData.dlvReqDt));
						}
						PAGE.FORM13.setItemValue("dlvAddr", rowData.dlvAddr);
						PAGE.FORM13.setItemValue("dlvName", rowData.dlvName);
						PAGE.FORM13.setItemValue("dlvPhone", rowData.dlvPhone);
						PAGE.FORM13.setItemValue("reqRemarkOld", rowData.reqRemark);
						PAGE.FORM13.setItemValue("orderNo", rowData.orderNo);
						
						PAGE.FORM13.disableItem("orderNo");
						PAGE.FORM13.disableItem("reqOrderCnt");
						PAGE.FORM13.disableItem("saleAmt");
						PAGE.FORM13.disableItem("totSaleAmt");
						PAGE.FORM13.disableItem("type");
						PAGE.FORM13.disableItem("cd");
						PAGE.FORM13.disableItem("cdNm");
						PAGE.FORM13.disableItem("op1");
						PAGE.FORM13.disableItem("op2");
						PAGE.FORM13.disableItem("op3");
						PAGE.FORM13.disableItem("saleAmt");
						PAGE.FORM13.disableItem("totSaleAmt");
						PAGE.FORM13.disableItem("dlvAddr");
						PAGE.FORM13.disableItem("dlvMethod");
						PAGE.FORM13.disableItem("dlvReqDt");
						PAGE.FORM13.disableItem("dlvName");
						PAGE.FORM13.disableItem("dlvPhone");
						
						PAGE.FORM13.clearChanged();
			            mvno.ui.popupWindowById("FORM13", "주문보기", 700, 600, {
			                onClose: function(win) {
			                	if (! PAGE.FORM13.isChanged()) return true;
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
			title : "주문등록"
			,items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "품목", name:"type", width:140, labelWidth:100, required:true}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "상품코드", name:"cd", width:140, labelWidth:100, required:true}
						 ,{type:"newcolumn", offset:12}
						 ,{type:"input", label:"", name: "cdNm", width:200, readonly:true}
					 ]},// 2row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택1", name:"op1", width:140, labelWidth:100, required:false}
					 ]},// 4row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택2", name:"op2", width:140, labelWidth:100, required:false}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택3", name:"op3", width:140, labelWidth:100, required:false}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "판매단가",name : "saleAmt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", required:true, labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "수량",name : "reqOrderCnt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", maxLength : 10, required:true, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "금액(VAT미포함)",name : "totSaleAmt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", labelWidth:109}
					 ]},
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
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "주문요청사항",name : "reqRemark",width:455 ,labelWidth:100}
					 ]}
				]
			,buttons: {
			   	center : {
		    		btnInoutReg : { label : "등록" },
		    		btnPopClose : { label : "닫기" }
				}
			}
			,onKeyUp : function(inp, ev, name, value)
			{
				
			}
			,onInputChange : function(name,val,inp) {
				
				switch(name)
				{
					case "reqOrderCnt":
						var saleAmt  = this.getItemValue("saleAmt");
						
						this.setItemValue("totSaleAmt", val * saleAmt);
						break;

				}
			}
			,onChange : function (name, value)
			{
				switch (name) {
					case "type":	
						form11SetChangeValue("TYPE", value, "Y");
						break;
					case "cd":	
						var type = PAGE.FORM11.getItemValue("type");
						form11SetChangeValue("CD", type, "Y");
						break;
				}
				
            }
			,onButtonClick : function(btnId) {
				    	
				var form = this;
					switch (btnId) {
						case "btnInoutReg":
							var data = PAGE.FORM11.getFormData(true);
							
							data.opCode = "ORDER_ADD";
							
							if(data.cd == "" || data.cd == "-1"){
								mvno.ui.alert("주문 가능한 상품이 없습니다");
								return;
							}
							
							if(data.reqOrderCnt == ""){
								mvno.ui.alert("수량은 필수입니다.");
								return;
							}
							
							var url = ROOT + "/pps/agency/ordermgmt/ppsOrderInfoProc.json";
							mvno.cmn.ajax(url, data, function(results) {
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
							
						case "btnPopClose":	
							mvno.ui.closeWindowById("FORM11", true);
			                break;
						}
			}
			
		},
		FORM12: {
			title : "주문수정"
			,items : [ 	
					 {type:"block", list:[
					 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"주문번호", name: "orderNo", width:200, labelWidth:100}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "품목", name:"type", width:140, labelWidth:100, required:true}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "상품코드", name:"cd", width:140, labelWidth:100, required:true}
						 ,{type:"newcolumn", offset:12}
						 ,{type:"input", label:"", name: "cdNm", width:200, readonly:true}
					 ]},// 2row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택1", name:"op1", width:140, labelWidth:100, required:false}
					 ]},// 4row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택2", name:"op2", width:140, labelWidth:100, required:false}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택3", name:"op3", width:140, labelWidth:100, required:false}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "판매단가",name : "saleAmt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", required:true, labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "수량",name : "reqOrderCnt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", maxLength : 10, required:true, labelWidth:100}
						 ,{type:"newcolumn", offset:60}
						 ,{type : "input",label : "금액(VAT미포함)",name : "totSaleAmt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", labelWidth:109}
					 ]},
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
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "주문요청사항",name : "reqRemarkOld",width:455, rows:5 ,labelWidth:100, readonly:true}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "추가",name : "reqRemark",width:455 ,labelWidth:100}
					 ]}
				]
			,buttons: {
			   	center : {
			   		btnInfoMod : { label : "수정" },
		    		btnPopClose : { label : "닫기" }
				}
			}
			,onChange : function (name, value)
			{
				switch (name) {
					case "type":	
						form12SetChangeValue("TYPE", value, "Y", "", "", "", "");
						break;
					case "cd":	
						var type = PAGE.FORM12.getItemValue("type");
						form12SetChangeValue("CD", type, "Y", "", "", "", "");
						break;
				}
	        }
			,onInputChange : function(name,val,inp) {
				
				switch(name)
				{
					case "reqOrderCnt":
						var saleAmt  = this.getItemValue("saleAmt");
						
						this.setItemValue("totSaleAmt", val * saleAmt);
						break;

				}
			}
			,onButtonClick : function(btnId) {
				    	
				var form = this;
					switch (btnId) {
					case "btnInfoMod":
						var data = PAGE.FORM12.getFormData(true);
						
						data.opCode = "ORDER_MOD";
						
						if(data.cd == "" || data.cd == "-1"){
							mvno.ui.alert("주문 가능한 상품이 없습니다");
							return;
						}
						
						if(data.reqOrderCnt == ""){
							mvno.ui.alert("수량은 필수입니다.");
							return;
						}
						
						var url = ROOT + "/pps/agency/ordermgmt/ppsOrderInfoProc.json";
						mvno.cmn.ajax(url, data, function(results) {
							var result = results.result;
							var retCode = result.retCode;
							var msg = result.retMsg;
							mvno.ui.alert(msg);
							//console.log(results);
							if(retCode == "0000") {
								mvno.ui.closeWindowById(form, true);  
								//mvno.ui.notify("NCMN0015");
								PAGE.FORM12.clear();
								PAGE.GRID1.refresh();
							}
							
						});
						
						break;
							
						case "btnPopClose":	
							mvno.ui.closeWindowById("FORM12", true);
			                break;
						}
			}
			
		},
		FORM13: {
			title : "주문보기"
				,items : [ 	
						 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
							 ,{type:"input", label:"주문번호", name: "orderNo", width:200, labelWidth:100}
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
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type : "input",label : "판매단가",name : "saleAmt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", required:true, labelWidth:100}
						 ]},
						 {type:"block", list:[
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type : "input",label : "수량",name : "reqOrderCnt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", maxLength : 10, required:true, labelWidth:100}
							 ,{type:"newcolumn", offset:60}
							 ,{type : "input",label : "금액(VAT미포함)",name : "totSaleAmt", numberFormat: "0,000,000,000", width:140, validate:"ValidInteger", labelWidth:109}
						 ]},
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
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type : "input",label : "주문요청사항",name : "reqRemarkOld",width:455, rows:5 ,labelWidth:100, readonly:true}
						 ]},
						 {type:"block", list:[
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type : "input",label : "추가",name : "reqRemark",width:455 ,labelWidth:100}
						 ]}
					]
			,buttons: {
			   	center : {
			   		btnInfoRpy : { label : "요청추가" },
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
						case "btnInfoRpy":
							var data = PAGE.FORM13.getFormData(true);
							
							data.opCode = "ORDER_RPY";
							
							var url = ROOT + "/pps/agency/ordermgmt/ppsOrderInfoProc.json";
							mvno.cmn.ajax(url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.alert(msg);
								//console.log(results);
								if(retCode == "0000") {
									mvno.ui.closeWindowById(form, true);  
									//mvno.ui.notify("NCMN0015");
									PAGE.FORM13.clear();
									PAGE.GRID1.refresh();
								}
								
							});
							
							break;
							
						
						case "btnPopClose":	
							mvno.ui.closeWindowById("FORM13", true);
			                break;			                
						}
			}
			
		},

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
				
				PAGE.FORM1.setItemValue("searchStatus", "ORDER");
				
			}

		};
		
		
	</script>