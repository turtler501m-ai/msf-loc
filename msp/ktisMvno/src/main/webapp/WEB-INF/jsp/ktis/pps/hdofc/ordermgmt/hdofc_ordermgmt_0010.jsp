<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : hdofc_ordermgmt_0010.jsp
 * @Description : 재고관리
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
			
	<!-- Script -->
	<script type="text/javascript">
	
	function setCurrentDate(form, name)
	{
		form.setItemValue(name, new Date());
	}
	
	function form11SetChangeValue(name, type, showFlag){
		if(name == "TYPE"){
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "CD", cdFlag : "Y", showFlag : showFlag} , PAGE.FORM11, "cd");
		}
		
		mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP1", showFlag : showFlag} , PAGE.FORM11, "op1");
		mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP2", showFlag : showFlag} , PAGE.FORM11, "op2");
		mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP3", showFlag : showFlag} , PAGE.FORM11, "op3");
		
	}
	
	function form12SetChangeValue(name, type){
		if(name == "TYPE"){
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "CD", cdFlag : "Y"} , PAGE.FORM12, "cd");
		}
		
		mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP1"} , PAGE.FORM12, "op1");
		mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP2"} , PAGE.FORM12, "op2");
		mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/PpsOrderCodeList.json",{type : type, codeGubun : "OP3"} , PAGE.FORM12, "op3");
		
	}
	
	
	var DHX = {
		FORM1 : {
			title : "",
			items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
							,{type : "select",label : "품명",name : "searchType",width:100}
							,{type : "newcolumn", offset:20}
							,{type : "select",label : "입고여부",name : "searchTotCntFlag",width:100}
							,{type : "newcolumn", offset:20}
							,{type : "select",label:"검색",name : "searchItem",width:100} 	
		                   	,{type : "newcolumn", offset:3}
		                   	,{type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
								,{type: "label", label: ":"}
		                 	 ]}	 
		                   	,{type : "newcolumn"}
	 			            ,{type : "input",name : "searchSValue", width:112, maxLength:20}
					 ]},// 1row
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],
	
			onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnSearch":
						
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/ordermgmt/orderGoodsList.json", form, {
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
					null;	
				}
			}
		}, // FORM1 End
		GRID1 : {

			css : {
				height : "510px"
			},
			title : "조회결과",
			header : "품명,상품코드,상품명,선택1,선택2,전체입고,출고,폐기,재고수량,판매단가",
			columnIds : "typeNm,cd,cdNm,op1Nm,op2Nm,totCnt,outCnt,closeCnt,inCnt,saleAmt",
			widths : "100,110,170,80,80,90,80,80,80,80", //총 1500
			colAlign : "center,center,center,center,center,right,right,right,right,right",
			colTypes : "ro,ro,ro,ro,ro,ron,ron,ron,ron,ron",
			colSorting : "str,str,str,str,str,int,int,int,int,int",
			paging : true,
			pageSize :15,
			pagingStyle :2,
			buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				},
				right :{
					btnReg :{label :"재고정보등록"},
					btnMod :{label :"재고정보수정"},
					btnDel :{label :"재고정보삭제"},
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
						var url = "/pps/hdofc/ordermgmt/orderGoodsListExcel.json";

						 var searchData =  PAGE.FORM1.getFormData(true);

							 console.log("searchData",searchData);
							
						  mvno.cmn.download(url+"?menuId=PPS1920001",true,{postData:searchData});
						  
						break;
					case "btnReg":
						
						mvno.ui.createForm("FORM11");
						PAGE.FORM11.clear();
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM11, "type");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0110" } , PAGE.FORM11, "showFlag");
						
						var type = PAGE.FORM11.getItemValue("type");
						
						form11SetChangeValue("TYPE", type, "Y");
						
						PAGE.FORM11.clearChanged();
			            mvno.ui.popupWindowById("FORM11", "재고등록", 700, 450, {
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
						
						mvno.ui.createForm("FORM12");
						PAGE.FORM12.clear();
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM12, "type");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0110" } , PAGE.FORM12, "showFlag");
						
						PAGE.FORM12.setItemValue("type", rowData.type);
						PAGE.FORM12.setItemValue("showFlag", rowData.showFlag);
						
						var type = PAGE.FORM12.getItemValue("type");
						
						form12SetChangeValue("TYPE", type);
						
						PAGE.FORM12.setItemValue("cd", rowData.cd);
						PAGE.FORM12.setItemValue("cdNm", rowData.cdNm);
						PAGE.FORM12.setItemValue("op1", rowData.op1);
						PAGE.FORM12.setItemValue("op2", rowData.op2);
						PAGE.FORM12.setItemValue("op3", rowData.op3);
						PAGE.FORM12.setItemValue("saleAmt", rowData.saleAmt);
						PAGE.FORM12.setItemValue("remarkOld", rowData.remark);
						
						PAGE.FORM12.disableItem("type");
						PAGE.FORM12.disableItem("cd");
						PAGE.FORM12.disableItem("op1");
						PAGE.FORM12.disableItem("op2");
						PAGE.FORM12.disableItem("op3");
						
						PAGE.FORM12.clearChanged();
			            mvno.ui.popupWindowById("FORM12", "재고수정", 700, 500, {
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
						
						if(rowData.totCnt > 0){
							mvno.ui.alert("입고한 상품은 삭제불가능합니다");
							return;
						} 
						
						rowData.opCode = "GOODS_DEL";
						
						var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderGoodsProc.json";
						var msg = "선택한 상품을 삭제하시겠습니까?";
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
				}
			}
		}, // GRID1 End
		FORM11: {
			title : "재고등록"
			,items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "품목", name:"type", width:150, labelWidth:100, required:true}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"상품코드", name: "cd", width:150, labelWidth:100, required:true}
					 ]},// 2row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"상품명", name: "cdNm", width:150, labelWidth:100, required:true}
					 ]},// 2row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택1", name:"op1", width:150, labelWidth:100, required:true}
					 ]},// 4row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택2", name:"op2", width:150, labelWidth:100, required:true}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택3", name:"op3", width:150, labelWidth:100, required:false}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "판매단가",name : "saleAmt", numberFormat: "0,000,000,000", width:150, validate:"ValidInteger", required:true, labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "노출여부", name:"showFlag", width:150, labelWidth:100, required:true}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "관리자메모",name : "remark",width:500 ,labelWidth:100}
					 ]}
				]
			,buttons: {
			   	center : {
		    		btnGoodsReg : { label : "등록" },
		    		btnPopClose : { label : "닫기" }
				}
			}
			,onKeyUp : function(inp, ev, name, value)
			{
			}
			,onChange : function (name, value)
			{
				switch (name) {
					case "type":	
						form11SetChangeValue("TYPE", value, "Y");
						break;
				}
				
            }
			,onButtonClick : function(btnId) {
				    	
				var form = this;
					switch (btnId) {
						case "btnGoodsReg":
							var data = PAGE.FORM11.getFormData(true);
							
							data.opCode = "GOODS_ADD";
							
							if(data.cd == ""){
								mvno.ui.alert("상품코드는 필수입니다");
								return;
							}
							
							if(data.cdNm == ""){
								mvno.ui.alert("상품명은 필수입니다");
								return;
							}
							
							if(data.op1 == "" || data.op1 == "-1"){
								mvno.ui.alert("선택1은 필수입니다");
								return;
							}
							
							if(data.op2 == "" || data.op2 == "-1"){
								mvno.ui.alert("선택2는 필수입니다");
								return;
							}
						
							if(data.saleAmt == ""){
								mvno.ui.alert("판매단가는 필수입니다");
								return;
							}
							
							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderGoodsProc.json";
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
			
		}, // FORM11 End
		FORM12: {
			title : "재고수정"
			,items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "품목", name:"type", width:150, labelWidth:100, required:true}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"상품코드", name: "cd", width:150, labelWidth:100, readonly:true}
					 ]},// 2row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type:"input", label:"상품명", name: "cdNm", width:150, labelWidth:100}
					 ]},// 2row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"200", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택1", name:"op1", width:150, labelWidth:100, required:false}
					 ]},// 4row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택2", name:"op2", width:150, labelWidth:100, required:false}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택3", name:"op3", width:150, labelWidth:100, required:false}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "판매단가",name : "saleAmt", numberFormat: "0,000,000,000", width:150, validate:"ValidInteger", required:true, labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "노출여부", name:"showFlag", width:150, labelWidth:100, required:true}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "관리자메모",name : "remarkOld",width:450, rows:5 ,labelWidth:100, readonly:true}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "추가",name : "remark",width:450 ,labelWidth:100}
					 ]}
				]
			,buttons: {
			   	center : {
		    		btnGoodsMod : { label : "수정" },
		    		btnPopClose : { label : "닫기" }
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
						case "btnGoodsMod":
							var data = PAGE.FORM12.getFormData(true);
							
							data.opCode = "GOODS_MOD";
							
							if(data.cd == ""){
								mvno.ui.alert("상품코드는 필수입니다");
								return;
							}
							
							if(data.cdNm == ""){
								mvno.ui.alert("상품명은 필수입니다");
								return;
							}
						
							if(data.saleAmt == ""){
								mvno.ui.alert("판매단가는 필수입니다");
								return;
							}
							
							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderGoodsProc.json";
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
			
		}

	};
	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
			buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {

				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM1, "searchType");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0116" } , PAGE.FORM1, "searchItem");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0110" } , PAGE.FORM1, "searchTotCntFlag");
				
			}

		};
		
		
	</script>