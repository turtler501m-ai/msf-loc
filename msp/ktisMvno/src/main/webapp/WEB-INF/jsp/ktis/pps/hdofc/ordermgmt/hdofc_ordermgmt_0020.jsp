<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : hdofc_ordermgmt_0020.jsp
 * @Description : 입출고관리
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
	
	function form11SetChangeValue(name, type, showFlag){
		if(name == "TYPE"){
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/ordermgmt/orderGoodsCd.json",{searchType : type, searchShowFlag : showFlag, cdFlag : "Y"} , PAGE.FORM11, "cd");
		}
		
		var url = ROOT + "/pps/hdofc/ordermgmt/orderGoodsList.json";
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
	
	var DHX = {
		FORM1 : {
			title : "",
			items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
							,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "등록일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""}
							,{type : "newcolumn", offset:3}
							,{type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
								,{type: "label", label: "~"}
		                   	]}	 
		                    ,{type : "newcolumn", offset:3} 
							,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""}
							,{type : "newcolumn", offset:30} 
							,{type : "select",label : "입출고",name : "searchInoutGubun",width:100, labelWidth:60}
							,{type : "newcolumn", offset:20}
							,{type : "select",label : "품명",name : "searchType",width:100, labelWidth:60}
							,{type : "newcolumn", offset:20}
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
	 			            ,{type : "newcolumn", offset:30} 
							,{type : "select",label : "취소여부",name : "searchCancelFlag",width:100, labelWidth:60}
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
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/ordermgmt/orderInoutList.json", form, {
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
			header : "입출고,품목,상품코드,상품명,선택1,선택2,판매단가,수량,재고수량,등록자,등록일,주문번호,인수구분,취소여부,취소일,취소자,관리자메모,취소사유",
			columnIds : "inoutGubunNm,typeNm,cd,cdNm,op1Nm,op2Nm,saleAmt,inoutCnt,remainsCnt,regAdminNm,regDt,orderNo,statusNm,cancelFlag,cancelDt,cancelAdminNm,remark,cancelRsn",
			widths : "70,90,110,120,80,80,80,80,80,110,130,160,90,70,110,110,400,400", //총 1500
			colAlign : "center,center,center,center,center,center,right,right,right,center,center,center,center,center,center,center,left,left",
			colTypes : "ro,ro,ro,ro,ro,ro,ron,ron,ron,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,int,int,int,str,str,str,str,str,str,str,str,str",
			paging : true,
			pageSize :15,
			pagingStyle :2,
			buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				},
				right :{
					btnReg :{label :"입출고등록"},
					btnDel :{label :"취소"},
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
						var url = "/pps/hdofc/ordermgmt/orderInoutListExcel.json";

						 var searchData =  PAGE.FORM1.getFormData(true);

							 console.log("searchData",searchData);
							
						  mvno.cmn.download(url+"?menuId=PPS1920002",true,{postData:searchData});
						  
						break;
					case "btnReg":
						
						mvno.ui.createForm("FORM11");
						PAGE.FORM11.clear();
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM11, "type");
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0117" } , PAGE.FORM11, "inoutGubun");
						
						var type = PAGE.FORM11.getItemValue("type");
						
						form11SetChangeValue("TYPE", type, "Y");
						
						PAGE.FORM11.disableItem("op1");
						PAGE.FORM11.disableItem("op2");
						PAGE.FORM11.disableItem("op3");
						
						PAGE.FORM11.clearChanged();
			            mvno.ui.popupWindowById("FORM11", "입출고등록", 700, 450, {
			                onClose: function(win) {
			                	if (! PAGE.FORM11.isChanged()) return true;
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
						
						if(rowData.cancelFlag == "Y"){
							mvno.ui.alert("이미 취소되었습니다");
							return;
						}
						
						mvno.ui.prompt("취소사유", function(result){
							var msg = "선택한 입출고를 취소하시겠습니까?";
							var url = ROOT + "/pps/hdofc/ordermgmt/ppsOrderGoodsProc.json";
							
							rowData.opCode = "INOUT_CAN";
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
			title : "입출고등록"
			,items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "작업구분", name:"inoutGubun", width:100, labelWidth:100, required:true}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "품목", name:"type", width:100, labelWidth:100, required:true}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "상품코드", name:"cd", width:100, labelWidth:100, required:true}
						 ,{type:"newcolumn", offset:0}
						 ,{type:"input", label:"", name: "cdNm", width:100, readonly:true}
					 ]},// 2row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택1", name:"op1", width:100, labelWidth:100, required:false}
					 ]},// 4row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택2", name:"op2", width:100, labelWidth:100, required:false}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						 ,{type : "select", label : "선택3", name:"op3", width:100, labelWidth:100, required:false}
					 ]},// 5row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "판매단가",name : "saleAmt", numberFormat: "0,000,000,000", width:100, validate:"ValidInteger", required:true, labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "수량",name : "cnt", numberFormat: "0,000,000,000", width:100, validate:"ValidInteger", required:true, labelWidth:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						 {type : "input",label : "관리자메모",name : "remark",width:450 ,labelWidth:100}
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
							
							data.opCode = "INOUT";
							
							if(data.cd == "" || data.cd == "-1"){
								mvno.ui.alert("입출고 가능한 상품이 없습니다");
								return;
							}
							
							if(data.cnt == ""){
								mvno.ui.alert("수량은 필수입니다");
								return;
							}
							
							if(parseInt(data.cnt) == 0){
								mvno.ui.alert("수량은 0이상 가능합니다");
								return;
							}
						
							if(data.saleAmt == ""){
								mvno.ui.alert("판매단가는 필수입니다.");
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
				
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0115" } , PAGE.FORM1, "searchType");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0117" } , PAGE.FORM1, "searchInoutGubun");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0118" } , PAGE.FORM1, "searchItem");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0110" } , PAGE.FORM1, "searchCancelFlag");
				
			}

		};
		
		
	</script>