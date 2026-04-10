<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>
<div id="GRID11" class="section"></div>


<!-- 등록화면 배치-사용안함 -->
<div id="POPUP1">
	<div id="FORM2"  class="section-box"></div>
	<div id="GRID2" class="section-full"></div>
</div>

<!-- 수정화면 배치 -->
<div id="POPUP2">
	<div id="FORM3"  class="section-box"></div>
	<div id="GRID3" class="section-full"></div>
	<div id="FORM32" class="section-full"></div>
</div>

<!-- 제품등록화면 배치 -->
<div id="POPUP_PRDT" >
	<div id="FORM4"  class="section-box"></div>
 	<div id="IMAGE4" ></div> 
	<div id="FORM4btn"  class="section-full"></div>
</div>

<!-- 제품이미지 -->
<div id="POPUP_IMAGE" >
 	<div id="IMAGE5" style="text-align:center;"></div> 
	<div id="FORM5btn"  class="section-full"></div>
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
							
							PAGE.GRID1.list(ROOT + "/m2m/usimOrdMng/usimOrdMngList.json", form, {callback : function() {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								PAGE.FORM1.setItemValue("btnPrintCount1", 0); //로딩 끝나면 초기화 - Print버튼
							}});

							PAGE.GRID11.clearAll();
							
							
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
					height : "300px"
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
						btnMod : { label : "주문확인" }
					},
					left : {
						btnPrint3 : { label : "견적서출력" },
						btnPrint : { label : "발주서출력" },
						btnPrint2 : { label : "인수확인서출력" },
						btnPrint4 : { label : "거래명세서출력" }
					},
					hright : {
						btnDnExcel : { label : "엑셀다운로드" },
						btnDnExcel2 : { label : "제품엑셀다운로드" }
					}
				},
				checkSelectedButtons : ["btnMod","btnPrint","btnPrint2","btnPrint3"],
				onRowDblClicked : function(rowId, celInd) {
					// 수정버튼 누른것과 같이 처리?
					this.callEvent('onButtonClick', ['btnMod']);
				},
				onRowSelect : function (rowId, celInd) {

					PAGE.GRID11.list(ROOT + "/m2m/usimOrdMng/usimOrdMdlList.json", this.getRowData(rowId));
					
					
				},
				onButtonClick : function(btnId, selectedData) {
					
					switch (btnId) {
					/*
						case "btnReg":

							mvno.ui.createForm("FORM2");
							mvno.ui.createGrid("GRID2");
							
							PAGE.FORM2.clear();
							PAGE.GRID2.clearAll();

							mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdMng/getOrgIdComboList.json", "", PAGE.FORM2, "orgnId"); // 발주사

							PAGE.FORM2.setItemValue("ordDate",today);
							
							var orgnId = PAGE.FORM2.getItemValue("orgnId");
							
							
							mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdMng/getAreaNmComboList.json", {orgnId:orgnId}, PAGE.FORM2, "areaNm"); // 배송지명

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"M2M0001"}, PAGE.FORM2, "ordStatus");// 배송상태

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM2, "telFn1");// 전화번호 지역번호
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM2, "telFn2");// 전화번호 지역번호
							
							var seq = PAGE.FORM2.getItemValue("areaNm");
							if(seq != null && seq != "" && seq.trim() != ""){

								mvno.cmn.ajax(ROOT + "/m2m/usimOrdMng/getM2mAddr.json", {seq:seq}, function(result) {
									
									PAGE.FORM2.setItemValue("mngNm",result.result.data.rows[0].mngNm);
									PAGE.FORM2.setItemValue("telFn1",result.result.data.rows[0].telFn1);
									PAGE.FORM2.setItemValue("telMn1",result.result.data.rows[0].telMn1);
									PAGE.FORM2.setItemValue("telRn1",result.result.data.rows[0].telRn1);
									
									PAGE.FORM2.setItemValue("telFn2",result.result.data.rows[0].telFn2);
									PAGE.FORM2.setItemValue("telMn2",result.result.data.rows[0].telMn2);
									PAGE.FORM2.setItemValue("telRn2",result.result.data.rows[0].telRn2);
									
									PAGE.FORM2.setItemValue("post",result.result.data.rows[0].post);
									PAGE.FORM2.setItemValue("addr",result.result.data.rows[0].addr);
									PAGE.FORM2.setItemValue("addrDtl",result.result.data.rows[0].addrDtl);
									
								});
								
								
							} else {
								
							}


							grid2Chk = 0;
							mvno.ui.popupWindowById("POPUP1", "등록화면", 470, 600, {  
								onClose: function(win) {
									if (! (PAGE.GRID2.isChanged() || grid2Chk == 1)) return true;
									mvno.ui.confirm("CCMN0005", function(){
											win.closeForce();
									});
								}
							});
							
							break;
							*/
						case "btnMod":
							mvno.ui.createForm("FORM3");
							mvno.ui.createGrid("GRID3");
							mvno.ui.createForm("FORM32");
							
							PAGE.FORM3.clear();
							PAGE.GRID3.clearAll();
							PAGE.FORM32.clear();

							mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdMng/getOrgIdComboList.json", "", PAGE.FORM3, "orgnId"); // 발주사
							
							//mvno.cmn.ajax4lov( ROOT + "/sale/rateMgmt/getRateComboList.json", "", PAGE.FORM3, "rateCd"); //요금제

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"M2M0001"}, PAGE.FORM3, "ordStatus");// 배송상태

							var data = selectedData;
							
							var orgnId = selectedData.orgnId;
							
							mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdMng/getAreaNmComboList.json", {orgnId:orgnId}, PAGE.FORM3, "areaNm"); // 배송지명
							
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM3, "telFn1");// 전화번호 지역번호
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM3, "telFn2");// 전화번호 지역번호
							
							mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdMng/getDlvMethodCombo.json", "", PAGE.FORM3, "dlvMethod");  // 배송방식
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

							PAGE.GRID3.list(ROOT + "/m2m/usimOrdMng/usimOrdMdlList.json", data, {callback : function() {

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
	
							mvno.ui.popupWindowById("POPUP2", "주문확인", 690, 750, {
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
							mvno.cmn.download(ROOT + "/m2m/usimOrdMng/usimOrdMngListEx.json?menuId=M2M1000031", true, {postData:searchData});
							break;	

						case "btnDnExcel2":
							
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
							mvno.cmn.download(ROOT + "/m2m/usimOrdMng/usimOrdMngListEx2.json?menuId=M2M1000031", true, {postData:searchData});
							break;	
							
						case "btnPrint"://발주서
							/*
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnPrintCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnPrintCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnPrintCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							*/
							
							var data = this.getSelectedRowData();
							/*
							if(data == null) {
								mvno.ui.alert("ECMN0002");
								return;
							}
							*/
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
							
						case "btnPrint2"://인수확인서
								/*
								var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnPrintCount1", "")) + 1;
								PAGE.FORM1.setItemValue("btnPrintCount1", btnCount2)
								if (PAGE.FORM1.getItemValue("btnPrintCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
								*/
								
								var data = this.getSelectedRowData();
								/*
								if(data == null) {
									mvno.ui.alert("ECMN0002");
									return;
								}
								*/

								if(data.ordStatus != 5){
									
									var msg1 = "인수확인 되지 않았습니다.";
									mvno.ui.alert(msg1);
									return;
								}
								
								var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/m2m/m2mReportHnd_V2.0.jrf" + "&arg=";
								param = encodeURI(param);

								var arg = "ordId#" + data.ordId + "#";
								arg += "usrId#" + "${SESSION_USER_ID}" + "#";
								arg += "orgnId#" + "${SESSION_USER_ORGN_ID}" + "#";
								
								arg = encodeURIComponent(arg);
								
								param = param + arg;
																
								var msg = "인수확인서를 출력하시겠습니까?";
								mvno.ui.confirm(msg, function() {
									mvno.cmn.ajax(ROOT + "/m2m/usimRepHist/usimRepHistInsert.json", {ordId:data.ordId,reportCd:3}, function(result) {
										mvno.ui.popupWindow("/cmn/report/report.do"+param, "인수확인서", 900, 700, {
											onClose: function(win) {
	
												PAGE.FORM1.setItemValue("btnPrintCount1", 0); //로딩 끝나면 초기화 - Print버튼
												win.closeForce();
											}
										});
									});
								});
								
								break;

						case "btnPrint4"://거래명세서
								/*
								var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnPrintCount1", "")) + 1;
								PAGE.FORM1.setItemValue("btnPrintCount1", btnCount2)
								if (PAGE.FORM1.getItemValue("btnPrintCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
								*/
								
								var data = this.getSelectedRowData();
								/*
								if(data == null) {
									mvno.ui.alert("ECMN0002");
									return;
								}
								*/

								if(data.ordStatus != 5){
									
									var msg1 = "인수확인 되지 않았습니다.";
									mvno.ui.alert(msg1);
									return;
								}
								
								var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/m2m/m2mReportSpec_V1.0.jrf" + "&arg=";
								param = encodeURI(param);

								var arg = "ordId#" + data.ordId + "#";
								arg += "usrId#" + "${SESSION_USER_ID}" + "#";
								arg += "orgnId#" + "${SESSION_USER_ORGN_ID}" + "#";
								
								arg = encodeURIComponent(arg);
								
								param = param + arg;
																
								var msg = "거래명세서를 출력하시겠습니까?";
								mvno.ui.confirm(msg, function() {
									mvno.cmn.ajax(ROOT + "/m2m/usimRepHist/usimRepHistInsert.json", {ordId:data.ordId,reportCd:3}, function(result) {
										mvno.ui.popupWindow("/cmn/report/report.do"+param, "거래명세서", 900, 700, {
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
								/*
								if(data == null) {
									mvno.ui.alert("ECMN0002");
									return;
								}
								*/
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
			
			GRID11 : {
				css : {
					height : "180px"
				},
				title : "제품",
				header : "대표제품ID,제품명,수량,단가 (VAT별도),총금액 (VAT별도),총금액 (VAT포함),삭제여부", 
				columnIds : "rprsPrdtId,prdtNm,ordCnt,outUnitPric,amt,amtVat,delYn",  
				widths : "150,200,150,150,150,150,150",
				colAlign : "center,center,right,right,right,right,center",
				colTypes : "ro,ro,ron,ron,ron,ron,ro",
				hiddenColumns:[6],
				buttons : {

				},
				onButtonClick : function(btnId, selectedData) {

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
						{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'willTakeDate', label: '인수희망일자', calendarPosition: 'bottom', width:100, readonly: true, disabled: true, offsetLeft:100, labelWidth:100 }
					 ]},
							
					 {type:"block", blockOffset:0,  list: [
						{type:'input', name:'ordId', label:'주문번호', width:150, required: true, readonly: true, disabled: true},
						{type:'select', name:'orgnId', label:'발주사', width:150, required: true},
						{type:'select', name:'areaNm', label:'배송지명', width:150, required: true},
						{type:'input', name:'mngNm', label:'배송지 담당명', width:150, required: true}
					 ]},

					 {type:"block", list:[
						{type:"select", label:"유선전화번호", name:"telFn1", width:75},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telMn1", labelWidth:10, width:40, labelAlign:"center", maxLength:4},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telRn1", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
					 ]},

					 {type:"block", list:[
						{type:"select", label:"핸드폰번호", name:"telFn2", width:75},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telMn2", labelWidth:10, width:40, labelAlign:"center", maxLength:4},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"telRn2", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
						
					 ]},
					 {type:"block", list:[
						{type:"input", label:"주소", name:"post", width:75, readonly:true, maxLength:6},
						{type:"newcolumn"},
						{type:"button", value:"우편번호찾기", name:"btnPostFind"},
						{type:"newcolumn"},
						{type:"input", label:" ", name:"addr", labelWidth:100, width:270, readonly:true, maxLength:83},
						{type:"newcolumn"}
					]},
					{type:"block", list:[
						 {type:"input", label:"상세주소", name:"addrDtl", width:270, maxLength:83},
						 {type:'select', label:'배송상태', name:'ordStatus', width:75, required: true, style:"background-color:rgb(253,211,198)"},
						 {type:"input", label:"요청사항", name:"ordInfo", width:500, maxLength:100,style:"background-color:rgb(253,211,198)"},
						 {type : 'hidden', name: "itemData", value:""} 
					]},
					{type:"block", list:[
						{type:'select', name:'dlvMethod', label:'배송방식', width:100}
					]},
					{type:"block", list:[

						 {type:'select', name:'tbCd', label:'택배회사', width:100},
						 {type:"newcolumn"},
						 {type:"input", label:"송장번호", name:"invNo", width:130, maxLength:20, offsetLeft:10, labelWidth:50 },
						 {type:"input", label:"배송연락처", name:"dlvTelNo", width:130, maxLength:20, offsetLeft:10, labelWidth:60 , hidden:true, validate:"ValidAplhaNumeric"},
						 {type:"newcolumn"},
						 {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'sendDate', label: '출고일자', calendarPosition: 'bottom', width:80, readonly: true, offsetLeft:10, labelWidth:50 }
						 
					]},
					{type:"block", list:[

						 {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'takeDate', label: '인수일자', calendarPosition: 'bottom', width:100, readonly: true }
						 
					]}

				],
				onChange : function(name, value) {//onChange START
					var form = this;

					switch(name){
					case "orgnId" :
						if(value != null && value != "" && value.trim() != ""){
							mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdMng/getAreaNmComboList.json", form, PAGE.FORM3, "areaNm"); // 배송지명
							
							
						} else {
							
						}

						break;
						
					case "areaNm" :
						if(value != null && value != "" && value.trim() != ""){

							mvno.cmn.ajax(ROOT + "/m2m/usimOrdMng/getM2mAddr.json", {seq:value}, function(result) {
								
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
					
					if(data.telFn1.length < 2){

						this.pushError("telFn1", "유선지역번호", "유선지역번호를 입력하세요");
					}
					
					if(data.telMn1.length < 3){

						this.pushError("telMn1", "유선국번호", "유선국번호를 입력하세요");
					}
					
					if(data.telRn1.length < 4){

						this.pushError("telRn1", "유선번호", "유선번호를 입력하세요");
					}
					

					if(data.telFn2.length < 3){

						this.pushError("telFn2", "핸드폰통신사번호", "핸드통신사번호를 입력하세요");
					}
					
					if(data.telMn2.length < 3){

						this.pushError("telMn2", "핸드폰국번호", "핸드폰국번호를 입력하세요");
					}
					
					if(data.telRn2.length < 4){

						this.pushError("telRn2", "핸드폰번호", "핸드번호를 입력하세요");
					}
					
					
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
						/*
						center : {
							btnSave : { label : "수정" },
							btnClose : { label: "닫기" }
						},*/
						hright : {
							btnAdd   : { label : "제품추가" },
							btnDelete : { label : "삭제" }
						}
					},
					onButtonClick : function(btnId, selectedData) {
						var form = PAGE.FORM3; // 혼란방지용으로 미리 설정 (권고)
						
						switch (btnId) {
							case "btnAdd":

								mvno.ui.createForm("FORM4");
								mvno.ui.createForm("FORM4btn");

								PAGE.FORM4.clear();

								mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdMng/getMdlIdComboList.json", "", PAGE.FORM4, "rprsPrdtId"); // 제품정보
								
								var rprsPrdtId = PAGE.FORM4.getItemValue("rprsPrdtId");

								mvno.cmn.ajax(ROOT + "/m2m/usimOrdMng/getM2mMdl.json", {rprsPrdtId:rprsPrdtId}, function(result) {
									
									//PAGE.FORM5.setItemValue("prdtCode",result.result.data.rows[0].prdtCode);
									PAGE.FORM4.setItemValue("prdtNm",result.result.data.rows[0].prdtNm);
									PAGE.FORM4.setItemValue("outUnitPric",result.result.data.rows[0].outUnitPric);
									PAGE.FORM4.setItemValue("prdtDesc",result.result.data.rows[0].prdtDesc);
									
									var amt = PAGE.FORM4.getItemValue("ordCnt") * PAGE.FORM4.getItemValue("outUnitPric") ;
									var amtVat = amt * 1.1;

									PAGE.FORM4.setItemValue("amt",amt);
									PAGE.FORM4.setItemValue("amtVat",amtVat);
									

									var fileName = "";
									fileName = result.result.data.rows[0].fileNm;

									// 첫번째파일
									if(!mvno.cmn.isEmpty(result.result.data.rows[0].fileNm)){
										fileName = result.result.data.rows[0].fileNm;

										$("#IMAGE4").css("width","220px"); 
										$("#IMAGE4").css("height","190px"); 
										$("#IMAGE4").css("overflow","hidden"); 

										//var img = '<img id="img_usim" src="/images/m2mtmp/' + fileName + '" style="width:auto; height:90px: margin-left:-30px">';
										var img = '<img id="img_usim" src="/m2m/usimPrdtMng/getImageFile.do?rprsPrdtId=' + rprsPrdtId + '" style="width:auto; height:auto: margin-left:-30px">';//width:auto; height:90px: margin-left:-30px
										
										$("#IMAGE4").html(img);
										var myImg = document.getElementById("img_usim");
										myImg.onload = function () // 이미지 로딩 완료 시 실행되는 함수
										{
											
											var divObject = $("#IMAGE4");
											var imgObject = $("#img_usim");
											var divAspect = 190 / 220; // div의가로세로비는알고있는값이다
											var imgAspect = imgObject.height() / imgObject.width();
											
											if (imgAspect <= divAspect) {
											    // 이미지가 div보다 납작한 경우 세로를 div에 맞추고 가로는 잘라낸다
											    var imgWidthActual = divObject.offsetHeight / imgAspect;
											    
											    var imgWidthToBe = divObject.offsetHeight / divAspect;
											    var marginLeft = -Math.round((imgWidthActual - imgWidthToBe) / 2);

											    $("#img_usim").css("width","96%");
											    $("#img_usim").css("height","auto");
											    $("#img_usim").css("margin-left",marginLeft + 'px;');
											} else {
											    // 이미지가 div보다 길쭉한 경우 가로를 div에 맞추고 세로를 잘라낸다
											    $("#img_usim").css("width","auto");
											    $("#img_usim").css("height","96%");
											    $("#img_usim").css("margin-left","0");
											    
											}
										    $("#img_usim").css("border","2px solid lightgray");
										    
											$("#IMAGE4").css("height",imgObject.height() + 12); 

										}

										$("#FORM4btn_btnImage").show();
										
									}else{
										$("#IMAGE4").css("width","0px"); 
										$("#IMAGE4").css("height","0px"); 

										$("#IMAGE4").html("");

										$("#FORM4btn_btnImage").hide();
										
									}

								});
						
								
								PAGE.FORM4.clearChanged();
		
								mvno.ui.popupWindowById("POPUP_PRDT", "제품추가", 470, 500, {//450, 350
									onClose: function(win) {
										
										//주문 합계 (VAT 별도 포함) 계산
										var amt = 0;
										var amtVat = 0;

										PAGE.GRID3.forEachRow(function(id){
											var arrData = PAGE.GRID3.getRowData(id);

											if(arrData.delYn == 'N'){
												amt += parseInt(arrData.amt);
												amtVat += parseInt(arrData.amtVat);
												
											}
											
										});
										PAGE.FORM32.setItemValue("amt",amt);
										PAGE.FORM32.setItemValue("amtVat",amtVat);
										
										
										win.closeForce();
									}
								});
								
								break;
							case "btnDelete":
								
								var rowid = PAGE.GRID3.getSelectedRowId();
								
								if(rowid != null && rowid != "" && rowid.trim() != ""){
									PAGE.GRID3.cells(rowid,6).setValue("Y");  
									PAGE.GRID3.setRowHidden(rowid,true);
									
									grid3Chk = 1;
									

									//주문 합계 (VAT 별도 포함) 계산
									var amt = 0;
									var amtVat = 0;

									PAGE.GRID3.forEachRow(function(id){
										var arrData = PAGE.GRID3.getRowData(id);
										if(arrData.delYn == 'N'){
											amt += parseInt(arrData.amt);
											amtVat += parseInt(arrData.amtVat);
											
										}
										
									});
									
									PAGE.FORM32.setItemValue("amt",amt);
									PAGE.FORM32.setItemValue("amtVat",amtVat);
									
									
								}
								
								break;	
							
						}
					
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
						btnSave : { label : "저장" },
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
							
							/* 항상 수정할 수 있도록 처리함
							//그리드에 선택된 주문 정보가 주문, 주문확인, 배송중 일때문 수정할 수 있도록 한다.
							if(selected_Data_Org != null && 
									(selected_Data_Org.ordStatus < 2 || selected_Data_Org.ordStatus > 4)){

								mvno.ui.alert("주문 정보를 수정할 수 없습니다.");

								return;	
							}
							var ordStatus = PAGE.FORM3.getItemValue("ordStatus");
							
							if(ordStatus < 2 || ordStatus > 4){

								mvno.ui.alert("주문 정보를 수정할 수 없습니다.");

								return;	
							}
							*/


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
							
							
							mvno.cmn.ajax(ROOT + "/m2m/usimOrdMng/usimOrdMngUpdate.json", form, function(result) {
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
						
			// --------------------------------------------------------------------------
			//주문 수정 - 제품추가화면
			FORM4 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', labelWidth:'100', inputWidth:'auto'},
					 {type:"block", blockOffset:0,  list: [
							{type:'select', name:'rprsPrdtId', label:'제품', width:100, required: true, labelWidth:110},
							//{type:'input', name:'prdtCode', label:'제품', width:150, required: true},
							{type:"newcolumn"},
							{type:'hidden', name:'prdtNm', width:200 }
					 ]},

					 {type:"block", list:[
							{type:'input', name:'ordCnt', label:'수량', width:100, required: true, numberFormat:"0,000,000,000", validate:"ValidInteger", labelWidth:110 },
							{type:"newcolumn"},
							{type:'input', name:'outUnitPric', label:'단가(VAT별도)', width:100, required: true, numberFormat:"0,000,000,000", validate:"ValidInteger" , readonly:true , labelWidth:110, disabled: true},
							{type:"newcolumn"},
							{type:'input', name:'amt', label:'총금액(VAT별도)', width:100, numberFormat:"0,000,000,000", validate:"ValidInteger" , required: true, readonly:true, labelWidth:110, disabled: true},
							{type:"newcolumn"},
							{type:'input', name:'amtVat', label:'총금액(VAT포함)', width:100, numberFormat:"0,000,000,000", validate:"ValidInteger" , required: true, readonly:true, labelWidth:110, disabled: true},
							{type:"newcolumn"},
							{type:'input', name:'prdtDesc', label:'제품상세', width:270, readonly:true, labelWidth:110}
					 ]}

				],
				buttons : {
					/*
					center : {
						btnSave : { label : "저장" },
						btnClose : { label: "닫기" }
					}*/
				},
				onChange : function(name, value) {//onChange START
					var form = this;

					switch(name){
					case "rprsPrdtId" :
						if(value != null && value != "" && value.trim() != ""){

							mvno.cmn.ajax(ROOT + "/m2m/usimOrdMng/getM2mMdl.json", {rprsPrdtId:value}, function(result) {
								
								//PAGE.FORM4.setItemValue("prdtCode",result.result.data.rows[0].prdtCode);
								PAGE.FORM4.setItemValue("prdtNm",result.result.data.rows[0].prdtNm);
								PAGE.FORM4.setItemValue("outUnitPric",result.result.data.rows[0].outUnitPric);
								PAGE.FORM4.setItemValue("prdtDesc",result.result.data.rows[0].prdtDesc);
								
								var amt = PAGE.FORM4.getItemValue("ordCnt") * PAGE.FORM4.getItemValue("outUnitPric") ;
								var amtVat = amt * 1.1;

								PAGE.FORM4.setItemValue("amt",amt);
								PAGE.FORM4.setItemValue("amtVat",amtVat);

								var fileName = "";
								fileName = result.result.data.rows[0].fileNm;
								

								// 첫번째파일
								if(!mvno.cmn.isEmpty(result.result.data.rows[0].fileNm)){
									fileName = result.result.data.rows[0].fileNm;

									$("#IMAGE4").css("width","220px"); 
									$("#IMAGE4").css("height","190px"); 
									$("#IMAGE4").css("overflow","hidden"); 

									//var img = '<img id="img_usim" src="/images/m2mtmp/' + fileName + '" style="width:auto; height:90px: margin-left:-30px">';
									var img = '<img id="img_usim" src="/m2m/usimPrdtMng/getImageFile.do?rprsPrdtId=' + value + '" style="width:auto; height:auto: margin-left:-30px">';//width:auto; height:90px: margin-left:-30px
									
									$("#IMAGE4").html(img);
									
									
									var myImg = document.getElementById("img_usim");
									myImg.onload = function () // 이미지 로딩 완료 시 실행되는 함수
									{
									
										var divObject = $("#IMAGE4");
										var imgObject = $("#img_usim");
										var divAspect = 190 / 220; // div의가로세로비는알고있는값이다
										var imgAspect = imgObject.height() / imgObject.width();
										
										if (imgAspect <= divAspect) {
										    // 이미지가 div보다 납작한 경우 세로를 div에 맞추고 가로는 잘라낸다
										    var imgWidthActual = divObject.offsetHeight / imgAspect;
										    
										    var imgWidthToBe = divObject.offsetHeight / divAspect;
										    var marginLeft = -Math.round((imgWidthActual - imgWidthToBe) / 2);

										    $("#img_usim").css("width","96%");
										    $("#img_usim").css("height","auto");
										    $("#img_usim").css("margin-left",marginLeft + 'px;');
										} else {
										    // 이미지가 div보다 길쭉한 경우 가로를 div에 맞추고 세로를 잘라낸다
										    $("#img_usim").css("width","auto");
										    $("#img_usim").css("height","96%");
										    $("#img_usim").css("margin-left","0");
										    
										}
									    $("#img_usim").css("border","2px solid lightgray");
									    
										$("#IMAGE4").css("height",imgObject.height() + 12); 

									}

									$("#FORM4btn_btnImage").show();
									
								}else{
									$("#IMAGE4").css("width","0px"); 
									$("#IMAGE4").css("height","0px"); 

									$("#IMAGE4").html("");

									$("#FORM4btn_btnImage").hide();
									
								}
							});
							
							
						} else {
							
						}

						break;
					case "ordCnt":

						var ordCnt = String(PAGE.FORM4.getItemValue("ordCnt"));

						var indexP = ordCnt.indexOf('.');
						var indexS = ordCnt.indexOf('-');

						if(indexP != -1 || indexS != -1){

							ordCnt = ordCnt.replaceAll("-","");
							ordCnt = ordCnt.replaceAll(".","");
							
							PAGE.FORM4.setItemValue("ordCnt",ordCnt);
						}
						
						var amt = parseInt(ordCnt) * PAGE.FORM4.getItemValue("outUnitPric") ;
						var amtVat = amt * 1.1;

						PAGE.FORM4.setItemValue("amt",amt);
						PAGE.FORM4.setItemValue("amtVat",amtVat);
						
						break;
					
					}

				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnSave":
							
							grid3Chk = 1; //변경여부
							
							var rowId = PAGE.GRID3.uid();       //generates an unique id
							var pos = PAGE.GRID3.getRowsNum();  //gets the number of rows in grid
							
							var rprsPrdtId = PAGE.FORM5.getItemValue("rprsPrdtId");
							//var prdtCode = PAGE.FORM5.getItemValue("prdtCode");
							var prdtNm = PAGE.FORM5.getItemValue("prdtNm");
							var ordCnt = PAGE.FORM5.getItemValue("ordCnt");
							var outUnitPric = PAGE.FORM5.getItemValue("outUnitPric");
							var amt = PAGE.FORM5.getItemValue("amt");
							var amtVat = PAGE.FORM5.getItemValue("amtVat");
							var delYn = "N";

							if(ordCnt == null || parseInt(ordCnt) == 0 || ordCnt == ""){

								mvno.ui.alert("수량이 입력되지 않았습니다.");
	
								return;	
								
							}

							PAGE.GRID3.addRow(rowId,[rprsPrdtId,prdtNm,ordCnt,outUnitPric,amt,amtVat,delYn],pos);

							
							
							
							mvno.ui.closeWindowById(form);
							break;

						case "btnClose" :
							mvno.ui.closeWindowById(form);
							break;

					}
				},
				onValidateMore : function (data){

					
				}
			},		

			FORM4btn : {
				title : "",
				items : [
					/*
					 {type:'settings', position:'label-left', labelWidth:'100', inputWidth:'auto'},
					 {type:"block", blockOffset:0,  list: [
							{type:'select', name:'rprsPrdtId', label:'대표제품ID', width:70, required: true, labelWidth:110},
							//{type:'input', name:'prdtCode', label:'제품', width:150, required: true},
							{type:"newcolumn"},
							{type:'input', name:'prdtNm', width:200 }
					 ]},

					 {type:"block", list:[
							{type:'input', name:'ordCnt', label:'수량', width:100, required: true, numberFormat:"0,000,000,000", validate:"ValidInteger", labelWidth:110 },
							{type:"newcolumn"},
							{type:'input', name:'outUnitPric', label:'단가(VAT별도)', width:100, required: true, numberFormat:"0,000,000,000", validate:"ValidInteger" , readonly:true, labelWidth:110 },
							{type:"newcolumn"},
							{type:'input', name:'amt', label:'총금액(VAT별도)', width:100, numberFormat:"0,000,000,000", validate:"ValidInteger" , required: true, readonly:true, labelWidth:110},
							{type:"newcolumn"},
							{type:'input', name:'amtVat', label:'총금액(VAT포함)', width:100, numberFormat:"0,000,000,000", validate:"ValidInteger" , required: true, readonly:true, labelWidth:110},
							{type:"newcolumn"},
							{type:'input', name:'prdtDesc', label:'제품상세', width:270, readonly:true, labelWidth:110}
					 ]}
					*/
				],
				buttons : {
					center : {
						btnImage : {label : "이미지확대" },
						btnSave : { label : "저장" },
						btnClose : { label: "닫기" }
					}
				},
				onChange : function(name, value) {//onChange START
					/*
					var form = PAGE.FORM4;

					switch(name){
					case "rprsPrdtId" :
						if(value != null && value != "" ){

							mvno.cmn.ajax(ROOT + "/m2m/usimOrdMng/getM2mMdl.json", {rprsPrdtId:value}, function(result) {
								
								//PAGE.FORM4.setItemValue("prdtCode",result.result.data.rows[0].prdtCode);
								PAGE.FORM4.setItemValue("prdtNm",result.result.data.rows[0].prdtNm);
								PAGE.FORM4.setItemValue("outUnitPric",result.result.data.rows[0].outUnitPric);
								PAGE.FORM4.setItemValue("prdtDesc",result.result.data.rows[0].prdtDesc);
								
								var amt = PAGE.FORM4.getItemValue("ordCnt") * PAGE.FORM4.getItemValue("outUnitPric") ;
								var amtVat = amt * 1.1;

								PAGE.FORM4.setItemValue("amt",amt);
								PAGE.FORM4.setItemValue("amtVat",amtVat);

							});
							
							
						} else {
							
						}

						break;
					case "ordCnt":
						var amt = PAGE.FORM4.getItemValue("ordCnt") * PAGE.FORM4.getItemValue("outUnitPric") ;
						var amtVat = amt * 1.1;

						PAGE.FORM4.setItemValue("amt",amt);
						PAGE.FORM4.setItemValue("amtVat",amtVat);
						
						break;

					}*/

				},
				onButtonClick : function(btnId) {
					var form = PAGE.FORM4; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnSave":
							grid2Chk = 1;//변경여부

							var rowId ;       //generates an unique id
							var pos ;  //gets the number of rows in grid
							
// 							if(prdtPopUpGubun == "2"){

// 								rowId = PAGE.GRID2.uid();       //generates an unique id
// 								pos = PAGE.GRID2.getRowsNum();  //gets the number of rows in grid
								
// 							}else if(prdtPopUpGubun == "3"){
								
								rowId = PAGE.GRID3.uid();       //generates an unique id
								pos = PAGE.GRID3.getRowsNum();  //gets the number of rows in grid
// 							}
							
							var rprsPrdtId = PAGE.FORM4.getItemValue("rprsPrdtId");
							//var prdtCode = PAGE.FORM4.getItemValue("prdtCode");
							var prdtNm = PAGE.FORM4.getItemValue("prdtNm");
							var ordCnt = PAGE.FORM4.getItemValue("ordCnt");
							var outUnitPric = PAGE.FORM4.getItemValue("outUnitPric");
							var amt = PAGE.FORM4.getItemValue("amt");
							var amtVat = PAGE.FORM4.getItemValue("amtVat");
							var delYn = "N";
							
							if(ordCnt == null || parseInt(ordCnt) == 0 || ordCnt == ""){

								mvno.ui.alert("수량이 입력되지 않았습니다.");
	
								return;	
								
							}

							if(ordCnt != null && parseInt(ordCnt) < 0 && ordCnt != ""){

								mvno.ui.alert("수량은 음수를 입력할 수 없습니다.");
	
								return;	
								
							}

							if(rprsPrdtId == null || rprsPrdtId == ""){

								mvno.ui.alert("제품이 선택되지 않았습니다.");
	
								return;	
								
							}
							
// 							if(prdtPopUpGubun == "2"){
// 								PAGE.GRID2.addRow(rowId,[rprsPrdtId,prdtNm,ordCnt,outUnitPric,amt,amtVat,delYn],pos);
								
// 							}else if(prdtPopUpGubun == "3"){
								PAGE.GRID3.addRow(rowId,[rprsPrdtId,prdtNm,ordCnt,outUnitPric,amt,amtVat,delYn],pos);
								
// 							}

							
							mvno.ui.closeWindowById(form);
							break;

						case "btnClose" :
							mvno.ui.closeWindowById(form);
							break;

						case "btnImage" :
							

							mvno.ui.createForm("FORM5btn");
							
							
							var rprsPrdtId = PAGE.FORM4.getItemValue("rprsPrdtId");


							$("#IMAGE5").css("width","700px"); 
							$("#IMAGE5").css("height","640px"); 
							$("#IMAGE5").css("overflow","hidden"); 

							//var img = '<img id="img_usim" src="/images/m2mtmp/' + fileName + '" style="width:auto; height:90px: margin-left:-30px">';
							var img = '<img id="img_usim_big" src="/m2m/usimPrdtMng/getImageFile.do?rprsPrdtId=' + rprsPrdtId + '" style="width:auto; height:auto: margin-left:-30px">';//width:auto; height:90px: margin-left:-30px
							
							$("#IMAGE5").html(img);

							var myImg = document.getElementById("img_usim_big");
							myImg.onload = function () // 이미지 로딩 완료 시 실행되는 함수
							{
								
								var divObject = $("#IMAGE5");
								var imgObject = $("#img_usim_big");
								var divAspect = 640 / 700; // height/width   div의가로세로비는알고있는값이다
								var imgAspect = imgObject.height() / imgObject.width();
								
								if (imgAspect <= divAspect) {
								    // 이미지가 div보다 납작한 경우 세로를 div에 맞추고 가로는 잘라낸다
								    $("#img_usim_big").css("width","96%");
								    $("#img_uimg_usim_bigsim").css("height","auto");
								    $("#img_usim_big").css("margin-left", '0px;');
								} else {
								    // 이미지가 div보다 길쭉한 경우 가로를 div에 맞추고 세로를 잘라낸다
								    $("#img_usim_big").css("width","auto");
								    $("#img_usim_big").css("height","96%");
								    $("#img_usim_big").css("margin-left","0");
								    
								}
							    $("#img_usim_big").css("border","2px solid lightgray");
							    
								$("#IMAGE5").css("height",imgObject.height() + 12); 

							}
							
							
							mvno.ui.popupWindowById("POPUP_IMAGE", "이미지확대", 750, 750, {
								onClose: function(win) {

									win.closeForce();
									
								}
							});
							
							break;

					}
				},
				onValidateMore : function (data){

				}
			},	

			//-이미지확대 화면 버튼-----------------------------------------
			FORM5btn : {
				title : "",
				items : [
				],
				buttons : {
					center : {
						btnClose : { label: "닫기" }
					}
				},
				onChange : function(name, value) {//onChange START

				},
				onButtonClick : function(btnId) {
					var form = PAGE.FORM5btn; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
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
				mvno.ui.createGrid("GRID11");

				PAGE.FORM1.setItemValue("ordDate",today);

				//mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdMng/getOrgIdComboList.json", "", PAGE.FORM1, "orgnId"); // 발주사
				mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdMng/getAreaNmComboList.json", "", PAGE.FORM1, "areaNm"); // 배송지명
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"M2M0001"}, PAGE.FORM1, "ordStatus");// 배송상태
				
				mvno.cmn.ajax4lov( ROOT + "/m2m/usimOrdMng/getMdlIdComboList.json", "", PAGE.FORM1, "rprsPrdtId"); // 대표제품ID
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
