<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<div id="POPUP1">
	<div id="FORM2"  class="section-box"></div>
	<div id="GRID2" class="section-full"></div>
</div>

<div id="POPUP2">
	<div id="FORM3"  class="section-box"></div>
	<div id="GRID3" class="section-full"></div>
	<div id="FORM32" class="section-full"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	var today = new Date().format("yyyymmdd");
	// 주소 고객정보, 배송정보 구분
	var jusoGubun = "";
	
	var grid2Chk = 0;
	var grid3Chk = 0;
	
	var selected_Data_Org;
	
	
	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:70, blackOffset:0}
					, {type:"block", list:[
						{type:"input", label:"주문번호", name:"ordId", width:120, labelWidth:50, offsetLeft:0},
						{type:"newcolumn"},
						{type:"calendar", label:"주문월", name:"ordDate", width:100, dateFormat: '%Y-%m', serverDateFormat: '%Y%m', calendarPosition:"bottom",readonly: true, labelWidth:70, offsetLeft:20 },
						{type:"newcolumn"},
						{type:"select", label:"USIM제품", name:"rprsPrdtId", width:120, labelWidth:60, offsetLeft:20},
						{type:"newcolumn"},
						{type:"select", label:"배송상태", name:"ordStatus", width:100, labelWidth:60, offsetLeft:20}
					  ]}
					, {type:"block", list:[
						{type:"calendar", label:"인수월", name:"takeDate", width:100, dateFormat: '%Y-%m', serverDateFormat: '%Y%m', calendarPosition:"bottom",readonly: true, labelWidth:50 },
						{type:"newcolumn"},
						{type:"calendar", label:"인수희망월", name:"willTakeDate", width:100, dateFormat: '%Y-%m', serverDateFormat: '%Y%m', calendarPosition:"bottom",readonly: true, labelWidth:70, offsetLeft:40  },
						{type:"newcolumn"},
						{type:"calendar", label:"발송월", name:"sendDate", width:100, dateFormat: '%Y-%m', serverDateFormat: '%Y%m', calendarPosition:"bottom",readonly: true, labelWidth:60, offsetLeft:20  },
						{type:"newcolumn"},
						{type:"select", label:"배송지명", name:"areaNm", width:120, labelWidth:60, offsetLeft:40}
					  ]}

					//버튼 여러번 클릭 막기 변수
					, {type : 'hidden', name: "btnCount1", value:"0"} 
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}
					, {type : 'hidden', name: "btnPrintCount1", value:"0"}
					, {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"}
					
				],
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							
							PAGE.GRID1.list(ROOT + "/m2m/usimDlvMng/usimDlvMngList.json", form, {callback : function() {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								PAGE.FORM1.setItemValue("btnPrintCount1", 0); //로딩 끝나면 초기화 - Print버튼
							}});
							
							
							break;
					}
				},
				
				onChange : function(name, value) {//onChange START
					
				},
				onValidateMore : function (data){

				}
			},
			
			GRID1 : {
				css : {
					height : "500px"
				},
				title : "USIM 주문 관리",
				header : "주문번호,주문일자,출고일자,인수일자,인수희망일자,배송상태,발주사,대표제품코드,제품명,단가(VAT별도),수량,총 금액(VAT별도),총 금액(VAT포함),배송지담당자,유선전화번호,핸드폰전화번호,배송지명,등록자",	//17
				columnIds : "ordId,ordDate,sendDate,takeDate,willTakeDate,ordStatusNm,orgnNm,rprsPrdtId,prdtNm,outUnitPric,ordCnt,pric,pricVat,maskMngNm,mngPhn,mngMblphn,areaNmText,usrNm",
				widths : "100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100",
				colAlign : "center,center,center,center,center,center,center,center,center,right,right,right,right,center,center,center,center,center",
				colTypes : "ro,roXd,roXd,roXd,roXd,ro,ro,ro,ro,ro,ron,ron,ron,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
				hiddenColumns:[],
				paging : true,
				pageSize:20,
				buttons : {
					right : {
						//btnReg : { label : "등록" },
						btnMod : { label : "배송확인" }
					},
					left : {
						btnPrint3 : { label : "견적서출력" },
						btnPrint : { label : "발주서출력" },
					},
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					}
				},
				checkSelectedButtons : ["btnMod","btnPrint","btnPrint3"],
				onRowDblClicked : function(rowId, celInd) {
					// 수정버튼 누른것과 같이 처리?
					this.callEvent('onButtonClick', ['btnMod']);
				},
				onRowSelect : function (rowId, celInd) {
					
					
				},
				onButtonClick : function(btnId, selectedData) {
					
					switch (btnId) {
					
						case "btnMod":
							mvno.ui.createForm("FORM3");
							mvno.ui.createGrid("GRID3");
							mvno.ui.createForm("FORM32");
							
							PAGE.FORM3.clear();
							PAGE.GRID3.clearAll();
							PAGE.FORM32.clear();

							mvno.cmn.ajax4lov( ROOT + "/m2m/usimDlvMng/getOrgIdComboList.json", "", PAGE.FORM3, "orgnId"); // 발주사
							
							//mvno.cmn.ajax4lov( ROOT + "/sale/rateMgmt/getRateComboList.json", "", PAGE.FORM3, "rateCd"); //요금제

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"M2M0001"}, PAGE.FORM3, "ordStatus");// 배송상태

							var data = selectedData;

							var orgnId = selectedData.orgnId;
							
							
							mvno.cmn.ajax4lov( ROOT + "/m2m/usimDlvMng/getAreaNmComboList.json", {orgnId:orgnId}, PAGE.FORM3, "areaNm"); // 배송지명
							
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM3, "telFn1");// 전화번호 지역번호
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM3, "telFn2");// 전화번호 지역번호
							
							
							mvno.cmn.ajax4lov( ROOT + "/m2m/usimDlvMng/getDlvMethodCombo.json", "", PAGE.FORM3, "dlvMethod");  // 배송방식
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0025"}, PAGE.FORM3, "tbCd");  // 택배회사
							

							PAGE.FORM3.setFormData(data); 
							
							selected_Data_Org = selectedData;
							

							var value = PAGE.FORM3.getItemValue("dlvMethod");

							switch(value){
							case "DLV01" : //택배

								PAGE.FORM3.enableItem("tbCd");
								PAGE.FORM3.showItem("invNo");
								PAGE.FORM3.hideItem("dlvTelNo");
								
								break;
							
							case "DLV02": //퀵
								
								//form.setItemValue("tbCd","");

								PAGE.FORM3.disableItem("tbCd");

								PAGE.FORM3.hideItem("invNo");
								PAGE.FORM3.showItem("dlvTelNo");
								
								break;
							
							}
							
							
							
							
							
							//PAGE.GRID3.list(ROOT + "/m2m/usimOrdMng/usimOrdMdlList.json", data);

							PAGE.GRID3.list(ROOT + "/m2m/usimDlvMng/usimOrdMdlList.json", data, {callback : function() {

								//주문 합계 (VAT 별도 포함) 계산
								var amt = 0;
								var amtVat = 0;

								PAGE.GRID3.forEachRow(function(id){
									var arrData = PAGE.GRID3.getRowData(id);
									amt += parseInt(arrData.amt);
									amtVat += parseInt(arrData.amtVat);
									
								});
								PAGE.FORM32.setItemValue("amt",amt);
								PAGE.FORM32.setItemValue("amtVat",amtVat);
							
							}});
														
							
							
							
							grid3Chk = 0;
	
							mvno.ui.popupWindowById("POPUP2", "배송확인", 690, 750, {
								onClose: function(win) {
									if (! (PAGE.FORM3.isChanged() || grid3Chk == 1)) return true;
									mvno.ui.confirm("CCMN0005", function(){
											win.closeForce();
									});
								}
							});
							
							break;
							
						case "btnDnExcel":
							
							var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
							if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") {
								return; //버튼 최초 클릭일때만 조회가능하도록
							}
							
							if(PAGE.GRID1.getRowsNum() == 0) {
								mvno.ui.alert("데이터가 존재하지 않습니다.");
								return;
							}
							
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/m2m/usimDlvMng/usimDlvMngListEx.json?menuId=M2M1000031", true, {postData:searchData});
							break;	
							
						case "btnPrint"://발주서
							/*
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnPrintCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnPrintCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnPrintCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							*/
							
							var data = this.getSelectedRowData();
							
							var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/m2m/m2mReportShpNo_V2.0.jrf" + "&arg=";
							param = encodeURI(param);

							var arg = "ordId#" + data.ordId + "#";
							arg += "usrId#" + "${SESSION_USER_ID}" + "#";
							arg += "orgnId#" + "${SESSION_USER_ORGN_ID}" + "#";
							
							arg = encodeURIComponent(arg);
							
							param = param + arg;
							
							
							var msg = "발주서를 출력하시겠습니까?";
							mvno.ui.confirm(msg, function() {
								mvno.cmn.ajax(ROOT + "/m2m/usimRepHist/usimRepHistInsert.json", {ordId:data.ordId,reportCd:2}, function(result) {
									mvno.ui.popupWindow("/cmn/report/report.do"+param, "발주서", 900, 700, {
										onClose: function(win) {
	
											PAGE.FORM1.setItemValue("btnPrintCount1", 0); //로딩 끝나면 초기화 - Print버튼
											win.closeForce();
										}
									});
								});
							});
							
							break;						
							
						case "btnPrint3"://견적서
								/*
								var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnPrintCount1", "")) + 1;
								PAGE.FORM1.setItemValue("btnPrintCount1", btnCount2)
								if (PAGE.FORM1.getItemValue("btnPrintCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
								*/
								
								
								var data = this.getSelectedRowData();
								
								var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/m2m/m2mReportEye.jrf" + "&arg=";
								param = encodeURI(param);

								var arg = "ordId#" + data.ordId + "#";
								arg += "usrId#" + "${SESSION_USER_ID}" + "#";
								arg += "orgnId#" + "${SESSION_USER_ORGN_ID}" + "#";
								
								arg = encodeURIComponent(arg);
								
								param = param + arg;
								
								
								var msg = "견적서를 출력하시겠습니까?";
								mvno.ui.confirm(msg, function() {
									mvno.cmn.ajax(ROOT + "/m2m/usimRepHist/usimRepHistInsert.json", {ordId:data.ordId,reportCd:1}, function(result) {
										mvno.ui.popupWindow("/cmn/report/report.do"+param, "견적서", 900, 700, {
											onClose: function(win) {
	
												PAGE.FORM1.setItemValue("btnPrintCount1", 0); //로딩 끝나면 초기화 - Print버튼
												win.closeForce();
											}
										});
									});
								});
								
								break;
								
								
					}
				
				}
			},
			
			// --------------------------------------------------------------------------
			//주문 수정화면
			FORM3 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', labelWidth:'100', inputWidth:'auto'},
					 {type:"block", blockOffset:0,  list: [
						{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'ordDate', label: '주문일자', calendarPosition: 'bottom', width:100, readonly: true, disabled: true },
						{type:"newcolumn"},
						{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'willTakeDate', label: '인수희망일자', calendarPosition: 'bottom', width:100, readonly: true, disabled: true, offsetLeft:100 }
					 ]},
							
					 {type:"block", blockOffset:0,  list: [
						{type:'input', name:'ordId', label:'주문번호', width:150, required: true, readonly: true, disabled: true},
						{type:'select', name:'orgnId', label:'발주사', width:150, required: true, disabled: true},
						{type:'select', name:'areaNm', label:'배송지명', width:150, required: true, disabled: true},
						{type:'input', name:'mngNm', label:'배송지 담당명', width:150, required: true, readonly: true}
					 ]},

					 {type:"block", list:[
						{type:"select", label:"유선전화번호", name:"telFn1", width:75, disabled: true},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telMn1", labelWidth:10, width:40, labelAlign:"center", maxLength:4, readonly: true},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telRn1", labelWidth:10, width:40, labelAlign:"center", maxLength:4, readonly: true}
					 ]},

					 {type:"block", list:[
						{type:"select", label:"핸드폰번호", name:"telFn2", width:75, disabled: true},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telMn2", labelWidth:10, width:40, labelAlign:"center", maxLength:4, readonly: true},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telRn2", labelWidth:10, width:40, labelAlign:"center", maxLength:4, readonly: true}
							
					 ]},
					 {type:"block", list:[
						{type:"input", label:"주소", name:"post", width:75, readonly:true, maxLength:6},
						{type:"newcolumn"},
						{type:"button", value:"우편번호찾기", name:"btnPostFind", disabled: true}
					]},
					{type:"block", list:[
						{type:"input", label:" ", name:"addr", labelWidth:100, width:270, readonly:true, maxLength:83, readonly: true},
						{type:"input", label:"상세주소", name:"addrDtl", width:270, maxLength:83, readonly: true},
						{type:'select', label:'배송상태', name:'ordStatus', width:75, required: true, style:"background-color:rgb(253,211,198)"},
						{type:"input", label:"요청사항", name:"ordInfo", width:500, maxLength:100, readonly: true},
						{type : 'hidden', name: "itemData", value:""} 
					]},
					{type:"block", list:[
						{type:'select', name:'dlvMethod', label:'배송방식', width:100,style:"background-color:rgb(253,211,198)"}
					]},
					{type:"block", list:[
						{type:'select', name:'tbCd', label:'택배회사', width:100,style:"background-color:rgb(253,211,198)"},
						{type:"newcolumn"},
						{type:"input", label:"송장번호", name:"invNo", width:130, maxLength:20, offsetLeft:10, labelWidth:60 , style:"background-color:rgb(253,211,198)"},
						{type:"input", label:"배송연락처", name:"dlvTelNo", width:130, maxLength:13, offsetLeft:10, labelWidth:60 , style:"background-color:rgb(253,211,198)", hidden:true, validate:"ValidAplhaNumeric"},
						{type:"newcolumn"},
						{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'sendDate', label: '출고일자', calendarPosition: 'bottom', width:80, readonly: true, offsetLeft:10, labelWidth:60, style:"background-color:rgb(253,211,198)" }
							 
					]},
					{type:"block", list:[

						{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'takeDate', label: '인수일자', calendarPosition: 'bottom', width:100, readonly: true, disabled: true }
						 
					]}

				],
				onChange : function(name, value) {//onChange START
					var form = this;

					switch(name){
					case "orgnId" :
						if(value != null && value != "" && value.trim() != ""){
							mvno.cmn.ajax4lov( ROOT + "/m2m/usimDlvMng/getAreaNmComboList.json", form, PAGE.FORM3, "areaNm"); // 배송지명
							
							
						} else {
							
						}

						break;
						
					case "areaNm" :
						if(value != null && value != "" && value.trim() != ""){

							mvno.cmn.ajax(ROOT + "/m2m/usimDlvMng/getM2mAddr.json", {seq:value}, function(result) {
								
								PAGE.FORM3.setItemValue("mngNm",result.result.data.rows[0].mngNm);
								PAGE.FORM3.setItemValue("telFn1",result.result.data.rows[0].telFn1);
								PAGE.FORM3.setItemValue("telMn1",result.result.data.rows[0].telMn1);
								PAGE.FORM3.setItemValue("telRn1",result.result.data.rows[0].telRn1);
								
								PAGE.FORM3.setItemValue("telFn2",result.result.data.rows[0].telFn2);
								PAGE.FORM3.setItemValue("telMn2",result.result.data.rows[0].telMn2);
								PAGE.FORM3.setItemValue("telRn2",result.result.data.rows[0].telRn2);
								
								PAGE.FORM3.setItemValue("post",result.result.data.rows[0].post);
								PAGE.FORM3.setItemValue("addr",result.result.data.rows[0].addr);
								PAGE.FORM3.setItemValue("addrDtl",result.result.data.rows[0].addrDtl);
								
							});
							
							
						} else {
							
						}

						break;
						
					case "dlvMethod":
						
						switch(value){
						case "DLV01" : //택배

							form.enableItem("tbCd");
							form.showItem("invNo");
							form.hideItem("dlvTelNo");
							
							break;
						
						case "DLV02": //퀵
							
							//form.setItemValue("tbCd","");

							form.disableItem("tbCd");

							form.hideItem("invNo");
							form.showItem("dlvTelNo");
							
							break;
						
						}
						
						
						
						break;
					}

				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnPostFind":
							jusoGubun = "Mod";
							mvno.ui.codefinder4ZipCd("FORM3", "post", "addr", "addrDtl");
							PAGE.FORM3.enableItem("addrDtl");
							
							break;
							
					}
				},
				onValidateMore : function (data){

				}
			},
			
			//주문수정 - 제품
			GRID3 : {
					css : { height : "100px" },
					title : "제품",
					header : "대표제품ID,제품명,수량,단가 (VAT별도),총금액 (VAT별도),총금액 (VAT포함),삭제여부", 
					columnIds : "rprsPrdtId,prdtNm,ordCnt,outUnitPric,amt,amtVat,delYn",  
					widths : "100,100,100,100,100,100,100",
					colAlign : "center,center,right,right,right,right,center",
					colTypes : "ro,ro,ron,ron,ron,ron,ro",
					hiddenColumns:[6],
					buttons : {
						
					},
					onButtonClick : function(btnId, selectedData) {
						var form = PAGE.FORM3; // 혼란방지용으로 미리 설정 (권고)
						
					}
					
			},	//GRID3 End
			//주문 수정화면 : 주문합계
			FORM32 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', inputWidth:'auto'},
					 {type:"block", blockOffset:0,  list: [
							{type:'input', name:'amt', label:'주문합계(VAT별도)', width:100, numberFormat:"0,000,000,000", validate:"ValidInteger", readonly: true, labelWidth:110, offsetLeft:410 },
							{type:'input', name:'amtVat', label:'(VAT포함)', width:100, numberFormat:"0,000,000,000", validate:"ValidInteger", readonly: true, labelWidth:60, offsetLeft:460  }
					 ]}

				],
				buttons : {
					center : {
						btnSave : { label : "수정" },
						btnClose : { label: "닫기" }
					}
				},
				onChange : function(name, value) {//onChange START
					var form = this;


				},
				onButtonClick : function(btnId) {
					var form = PAGE.FORM3; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
							
						case "btnSave":
							var getRowsNum = 0;
							
							
							//그리드에 선택된 주문 정보가 주문, 주문확인, 배송중 일때문 수정할 수 있도록 한다.
							if(selected_Data_Org != null && 
									(selected_Data_Org.ordStatus < 2 || selected_Data_Org.ordStatus > 4)){

								mvno.ui.alert("주문 정보를 수정할 수 없습니다.");

								return;	
							}
							var ordStatus = PAGE.FORM3.getItemValue("ordStatus");
							
							if(ordStatus != 3 && ordStatus != 4){

								mvno.ui.alert("주문 정보를 수정할 수 없습니다.");

								return;	
							}

							PAGE.GRID3.forEachRow(function(id){
								var arrData = PAGE.GRID3.getRowData(id);
								
								
								if(arrData.delYn == "N"){
									getRowsNum = getRowsNum + 1;
								}
							});
							
							if(getRowsNum == 0){
								
								mvno.ui.alert("제품이 선택되지 않았습니다.");

								break;
							}

							var arrObj = [];
							
							PAGE.GRID3.forEachRow(function(id){
								var arrData = PAGE.GRID3.getRowData(id);
								arrObj.push(arrData);
							});
							var itemData = JSON.stringify({'ALL':arrObj});
							form.setItemValue("itemData",itemData);
							
							
							mvno.cmn.ajax(ROOT + "/m2m/usimDlvMng/usimDlvMngUpdate.json", form, function(result) {
								if (result.result.code == "OK") {
									mvno.ui.closeWindowById(form, true);
										mvno.ui.notify("NCMN0001");
										PAGE.GRID1.refresh();
								} else {
									mvno.ui.alert(result.result.msg);
								}
							});
							break;

						case "btnClose" :
							mvno.ui.closeWindowById(form);
							break;
							
					}
					
				},
				onValidateMore : function (data){

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

				PAGE.FORM1.setItemValue("ordDate",today);

				//mvno.cmn.ajax4lov( ROOT + "/m2m/usimDlvMng/getOrgIdComboList.json", "", PAGE.FORM1, "orgnId"); // 발주사
				mvno.cmn.ajax4lov( ROOT + "/m2m/usimDlvMng/getAreaNmComboList.json", "", PAGE.FORM1, "areaNm"); // 배송지명
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"M2M0001"}, PAGE.FORM1, "ordStatus");// 배송상태

				mvno.cmn.ajax4lov( ROOT + "/m2m/usimDlvMng/getMdlIdComboList.json", "", PAGE.FORM1, "rprsPrdtId"); // 대표제품ID
			}
			
	};
	

	/* 주소 setting */
	function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
		
		if(jusoGubun == "Reg"){
			PAGE.FORM2.setItemValue("post",zipNo);
			PAGE.FORM2.setItemValue("addr",roadAddrPart1);
			PAGE.FORM2.setItemValue("addrDtl",addrDetail);
		}else if(jusoGubun == "Mod"){
			PAGE.FORM3.setItemValue("post",zipNo);
			PAGE.FORM3.setItemValue("addr",roadAddrPart1);
			PAGE.FORM3.setItemValue("addrDtl",addrDetail);
		}
		
		jusoGubun = "";
		
		
		
	}
			
	
	
	
</script>
