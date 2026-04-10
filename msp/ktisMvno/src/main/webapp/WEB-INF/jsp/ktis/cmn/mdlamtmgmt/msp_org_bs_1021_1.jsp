<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1021_1.jsp
	 * @Description : 모델 단가 관리 화면
	 * @
	 * @ 수정일		수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2015.08.04 심정보 최초생성
	 * @ 
	 * @author : 심정보
	 * @Create Date : 2015. 8. 4.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>
<div class="row"><!-- 2단 이상 구성시 class="row" div 추가 -->
	<div id="GRID2" class="c5"></div><!-- c3=30%, c5=50% -->
	<div id="GRID3" class="c5"></div>
</div>
<!-- <div id="GRID2" class="section-full"></div> -->
<div id="FORM2" class="section-box"></div>
<div id="FORM3" class="section-box"></div>
<!-- Script -->
<script type="text/javascript">

var gToday = "${gSesSysDt}";
var inUnitPricNew = false;
var outUnitPricNew = false;
	var DHX = {
			/* 
			-----------------------------------
			----------- 검색조건 --------------
			-----------------------------------
			*/
			FORM1 : {
				items : [
					{type: "settings", position:"label-left", labelAlign:"left", blockOffset:0},			  
					{type: "input",label : "대표모델ID",name : "rprsPrdtId" ,width:80, value:'', offsetLeft:10, labelWidth:60 },
					{type: 'newcolumn'},
					{type: "button", name: 'btnRprsPrdtFind', value:"검색"},
					{type: 'newcolumn'},
					{type: "input", name:"rprsPrdtIdResult", value:"", width:160, readonly: true},
					{type: 'newcolumn'},
					{type: 'select', name: 'mnfctId', label: '제조사명', width:150, offsetLeft:30, value:'', labelWidth:50}, 
					{type: 'newcolumn'},
					{type: 'select', label: '모델유형', name: 'prdtTypeCd', width:100, offsetLeft:30, value:'', labelWidth:60},
					{type: 'hidden', name: 'maxAmt', value:''},
					{type: 'hidden', name: 'today', value:gToday}, 
					{type: 'newcolumn', offset:240},
					{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"},
				],
				onChange: function (name, value) {
					switch(name) {
						case 'rprsPrdtId':
							if(value.length >= 4){
								mvno.cmn.ajax(ROOT + "/cmn/intmmdl/selectRprsPrdtNm.json", {rprsPrdtId:value, rprsYn:"Y"}, function(resultData) {
									if(resultData.result.resultCnt > 0){
										//대표모델 존재
										PAGE.FORM1.setItemValue("rprsPrdtIdResult", "[" + resultData.result.resultList[0].prdtNm + "/" + resultData.result.resultList[0].prdtCode + "]"); 
									}else{
										//대표모델 미존재
										PAGE.FORM1.setItemValue("rprsPrdtIdResult", "");
									} 
								});
							} else {
								
								PAGE.FORM1.setItemValue("rprsPrdtIdResult", "");
							}
							break;
					}
				},
				onButtonClick : function(btnId) {

					var form = this;

					switch (btnId) {
						case "btnRprsPrdtFind": //대표모델 찾기 팝업
							mvno.ui.codefinder("RPRSMLDSET", function(result) {
								form.setItemValue("rprsPrdtId", result.rprsPrdtId);
								form.setItemValue("rprsPrdtIdResult", result.prdtNm);
							});
							break;
						case "btnSearch":
							PAGE.GRID1.list(ROOT + "/cmn/hndsetamtmgmt/selectIntmMdlAmtList.json", form, {callback : function() {
								if(PAGE.GRID1.getSelectedRowData() == null) PAGE.GRID2.clearAll();
								if(PAGE.GRID1.getSelectedRowData() == null) PAGE.GRID3.clearAll();
							}});
							
// 							PAGE.GRID1.list(ROOT + "/org/hndsetamtmgmt/hndstAmtModelList.json", form);
// 							PAGE.GRID1.list(ROOT + "/cmn/hndsetamtmgmt/selectIntmMdlAmtList.json", form);
							
							break;
					}
				}
			},
			
		/* 
		-----------------------------------
		-------- 대표모델 GRID ------------
		-----------------------------------
		*/
		GRID1 : {
			css : {
				height : "320px"
			},
			title : "대표모델",
			header : "대표모델ID,대표모델코드,모델명,제조사,모델유형,모델구분,출시일자,단종일자", //8
			columnIds : "rprsPrdtId,prdtCode,prdtNm,mnfctNm,prdtTypeNm,prdtIndNm,prdtLnchDt,prdtDt",
			widths : "80,150,150,150,100,100,100,100",
			colAlign : "center,left,left,left,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,roXd,roXd,roXd",
			colSorting : "str,str,str,str,str,str,str,str",
			paging : true,
			showPagingAreaOnInit: true,
			pageSize:20,
			onRowSelect : function(rowId) {
					
				var rowData = this.getSelectedRowData();
					
				PAGE.GRID2.clearAll();
				PAGE.GRID2.list(ROOT + "/cmn/hndsetamtmgmt/selectMnfctAmtHisList.json", rowData);
				PAGE.GRID3.clearAll();
				PAGE.GRID3.list(ROOT + "/cmn/hndsetamtmgmt/selectHndstAmtHisList.json", rowData);
			}
		},
		/* 
		-----------------------------------
		-------- 입고단가 GRID ------------
		-----------------------------------
		*/
		GRID2 : {
			 css		: {height : "200px"},
			 title	  : "입고단가",
			 header : "대표모델ID,단가만료일시,공급사,중고여부,입고단가,단가적용일시,비고", //7
			 columnIds : "rprsPrdtId,unitPricExprDttm,mnfctNm,oldYnNm,inUnitPric,unitPricApplDttm,remrk",
			 widths : "80,120,80,80,100,120,150",
			 colAlign : "center,center,left,center,right,center,left",
			 colTypes : "ro,roXdt,ro,ro,ron,roXdt,ro",
			 colSorting : "str,str,str,str,str,str,str",
			 buttons : {
				right : {
					btnReg : { label : "등록" },
					btnMod : { label : "수정" }
				}
			},
			checkSelectedButtons : ["btnMod"],
			onRowDblClicked : function(rowId, celInd) {
				// 수정버튼 누른것과 같이 처리?
				this.callEvent('onButtonClick', ['btnMod']);
			},
			onButtonClick : function(btnId, selectedData) {
				
				switch (btnId) {
					case "btnReg":
							if(PAGE.GRID1.getSelectedRowData() == null){
								mvno.ui.alert("ECMN0002");
								break;
							}
						mvno.ui.createForm("FORM2");
						
						var data = {
								"maxAmt" :  PAGE.FORM1.getItemValue("maxAmt"),
								"today" :  PAGE.FORM1.getItemValue("today"),
								"unitPricExprDttm" : "99991231"
						};
						
						PAGE.FORM2.setFormData(data, true);
						
						PAGE.FORM2.showItem("btnRprsPrdtFind");
						PAGE.FORM2.enableItem("unitPricApplDttm");
						PAGE.FORM2.enableItem("mnfctId"); //공급사
						PAGE.FORM2.enableItem("oldYn"); //중고여부
						
						//제조사 셀렉트박스 셋팅
						mvno.cmn.ajax4lov( ROOT + "/org/mnfctmgmt/selectMnfctList.json", "", PAGE.FORM2, "mnfctId");
						
						//중고여부 셀렉트박스 셋팅
						mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0011"}, PAGE.FORM2, "oldYn");
						
						
						PAGE.FORM2.setItemValue("rprsPrdtId", PAGE.GRID1.getSelectedRowData().rprsPrdtId);
						PAGE.FORM2.setItemValue("prdtNm", PAGE.GRID1.getSelectedRowData().prdtNm);
						PAGE.FORM2.setItemValue("rprsPrdtCode", PAGE.GRID1.getSelectedRowData().prdtCode);
						PAGE.FORM2.setItemValue("mnfctId", PAGE.GRID1.getSelectedRowData().mnfctId);
						PAGE.FORM2.clearChanged();
						inUnitPricNew = true;
						mvno.ui.popupWindowById("FORM2", "입고단가 등록", 730, 290, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});	
						break;
						
						
					case "btnMod":
						

						if(PAGE.GRID2.getSelectedRowData() == null)
						{
							mvno.ui.alert("입고단가리스트에 선택된 데이터가 없습니다.");
							break;
						}
						else
						{

							var appDate = PAGE.GRID2.getSelectedRowData().unitPricExprDttm;

							if(appDate == "99991231235959")
							{
								mvno.ui.createForm("FORM2");
								//제조사 셀렉트박스 셋팅
								mvno.cmn.ajax4lov( ROOT + "/org/mnfctmgmt/selectMnfctList.json", "", PAGE.FORM2, "mnfctId");
								
								//중고여부 셀렉트박스 셋팅
								mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0011"}, PAGE.FORM2, "oldYn");
								
								
								var data = PAGE.GRID2.getSelectedRowData();
								data["maxAmt"] = PAGE.FORM1.getItemValue("maxAmt");
								data["today"] = PAGE.FORM1.getItemValue("today");
								data["unitPricExprDttm"] = "99991231";
								
								PAGE.FORM2.setFormData(data); 

								PAGE.FORM2.hideItem("btnRprsPrdtFind");
								PAGE.FORM2.enableItem("unitPricApplDttm");
								PAGE.FORM2.disableItem("mnfctId"); //공급사
								PAGE.FORM2.disableItem("oldYn"); //중고여부
					
								
								PAGE.FORM2.clearChanged();
								inUnitPricNew = false;
								mvno.ui.popupWindowById("FORM2", "입고단가 수정", 730, 290, {
									onClose: function(win) {
										if (! PAGE.FORM2.isChanged()) return true;
										mvno.ui.confirm("CCMN0005", function(){
											win.closeForce();
										});
									}
								});	
								break;
							}
							else
							{
								mvno.ui.alert("ORGN0005");
								break;
							}
						}
					}
				}
		},
			
		/* 
		-----------------------------------
		----- 입고단가 등록/수정 FORM -----
		-----------------------------------
		*/
		FORM2 : {
			title : "입고단가",
			items : [
				{type: 'settings', position: 'label-left', labelWidth: '120', inputWidth: 'auto'},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'rprsPrdtId', label: '대표모델ID',  width: 120,  value: '' , disabled:true, validate: 'NotEmpty'},
					{type: "newcolumn"},
					{type: 'input', name: 'prdtNm', label: '모델명',  width: 140, offsetLeft: 60, value: '', validate: 'NotEmpty', disabled:true},
// 					{type: "newcolumn"},
// 					{type: "button", name: 'btnRprsPrdtFind', value:"찾기"},
				]},
				
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'rprsPrdtCode', label: '대표모델코드',  width: 120,  value: '' , disabled:true, validate: 'NotEmpty'},
					{type: "newcolumn"},
					{type: 'select', name: 'mnfctId', label: '공급사',  width: 140, offsetLeft: 60, required: true},
				]},
				
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'calendar', name: 'unitPricApplDttm' , label: '단가적용일시', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', enableTime:true,	value:'',  calendarPosition: 'bottom' ,width:120, readonly:true,required: true, validate: 'NotEmpty'},
					{type: "newcolumn"},
					{type: 'calendar', name: 'unitPricExprDttm', label: '단가만료일시', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', enableTime:true, offsetLeft: 60,	value:'',  calendarPosition: 'bottom' ,width:140, readonly:true, required: true, disabled:true}, 
				]} ,
				
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'inUnitPric' , label: '입고단가', value:'', width:120,required: true, numberFormat:"00,000,000", validate: 'ValidInteger', maxLength:9},
					{type: "newcolumn"},
					{type: 'select', name: 'oldYn', label: '중고여부', value:'', offsetLeft: 60 ,width:140, validate: 'NotEmpty',required: true}, 
				]} ,
				
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'remrk' , label: '비고', value:'' ,width:444, maxLength:70},
					{type: 'hidden', name: 'maxAmt', value:''},
					{type: 'hidden', name: 'today', value:''},
				]}		
				
			],

			buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label: "닫기" }
				}
			},
			onValidateMore : function (data){
				
				if(!data.rprsPrdtId){
					this.pushError("data.rprsPrdtId","모델명","은 필수입니다.");
				}
				if(!inUnitPricNew){
					if(data.unitPricApplDttm != "" && (data.unitPricApplDttm <= PAGE.FORM1.getItemValue("today"))){
						this.pushError("data.unitPricApplDttm","단가적용일시",["ICMN0013"]);
					}
				}
			},
			onChange : function (name, value){
				var form = this;
			},
			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)
			
				switch (btnId) {
// 					case "btnRprsPrdtFind": //대표모델 찾기 팝업
// 							mvno.ui.codefinder("RPRSMLDSET", function(result) {
// 								form.setItemValue("rprsPrdtId", result.rprsPrdtId);
// 								form.setItemValue("rprsPrdtIdResult", result.prdtNm);
// 								form.setItemValue("rprsPrdtId", result.rprsPrdtId); //대표모델ID
// 								form.setItemValue("prdtNm", result.prdtNm);			//모델명
// 								form.setItemValue("rprsPrdtCode", result.prdtCode);	//모델코드
// 								form.setItemValue("mnfctId", result.mnfctId);		//제조사ID
// 							});
// 							break;
					case "btnSave":
						//저장버튼 클릭
						if (form.isNew()) { // 등록 처리
							mvno.cmn.ajax(ROOT + "/cmn/hndsetamtmgmt/insertMnfctAmtHisList.json", form, function(result) {
								mvno.ui.closeWindowById(form, true);  
								mvno.ui.notify("NCMN0001");
								PAGE.GRID2.refresh();
							});
						}
						else { // 수정 처리
							mvno.cmn.ajax(ROOT + "/cmn/hndsetamtmgmt/updateMnfctAmtHisList.json", form, function(result) {
								mvno.ui.closeWindowById(form, true);  
								mvno.ui.notify("NCMN0002");
								PAGE.GRID2.refresh();
							});
						}
						break;
					case "btnClose" :
						mvno.ui.closeWindowById(form);	
						break;						  
				}
			}
		},
		/* 
		-----------------------------------
		-------- 출고단가 GRID ------------
		-----------------------------------
		*/
		GRID3 : {
			 css		: {height : "200px"},
			 title	  : "출고단가",
			 header : "대표모델ID,단가만료일시,중고여부,출고단가,제조사장려금,단가적용일시,비고", //10
			 columnIds : "rprsPrdtId,unitPricExprDttm,oldYnNm,outUnitPric,mnfctGrant,unitPricApplDttm,remrk",
			 widths : "80,120,80,100,80,120,100",
			 colAlign : "center,center,center,right,right,center,left",
			 colTypes : "ro,roXdt,ro,ron,ron,roXdt,ro",
			 colSorting : "str,str,str,str,str,str",
			 buttons : {
				right : {
					btnReg : { label : "등록" },
					btnMod : { label : "수정" }
				}
			},
			checkSelectedButtons : ["btnMod"],
			onRowDblClicked : function(rowId, celInd) {
				// 수정버튼 누른것과 같이 처리?
				this.callEvent('onButtonClick', ['btnMod']);
			},
			onButtonClick : function(btnId, selectedData) {
				
				switch (btnId) {
					case "btnReg":
						if(PAGE.GRID1.getSelectedRowData() == null){
								mvno.ui.alert("ECMN0002");
								break;
						}
						
						mvno.ui.createForm("FORM3");
						
						var data = {
								"maxAmt" :  PAGE.FORM1.getItemValue("maxAmt"),
								"today" :  PAGE.FORM1.getItemValue("today"),
								"unitPricExprDttm" : "99991231"
						};
						
						PAGE.FORM3.setFormData(data, true);
						
						PAGE.FORM3.enableItem("unitPricApplDttm");
						PAGE.FORM3.showItem("btnRprsPrdtFind");
						PAGE.FORM3.enableItem("oldYn"); //중고여부
						
						//중고여부 셀렉트박스 셋팅
						mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0011"}, PAGE.FORM3, "oldYn");
						
						PAGE.FORM3.setItemValue("rprsPrdtId", PAGE.GRID1.getSelectedRowData().rprsPrdtId);
						PAGE.FORM3.setItemValue("prdtNm", PAGE.GRID1.getSelectedRowData().prdtNm);
						PAGE.FORM3.setItemValue("rprsPrdtCode", PAGE.GRID1.getSelectedRowData().prdtCode);
						
						PAGE.FORM3.clearChanged();
						outUnitPricNew = true;
						mvno.ui.popupWindowById("FORM3", "출고단가 등록", 730, 290, {
							onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});	
						break;
						
						
					case "btnMod":
						

						if(PAGE.GRID3.getSelectedRowData() == null)
						{
							mvno.ui.alert("출고단가리스트에 선택된 데이터가 없습니다.");
							break;
						}
						else
						{

							var appDate = PAGE.GRID3.getSelectedRowData().unitPricExprDttm;

							if(appDate == "99991231235959")
							{
								mvno.ui.createForm("FORM3");
								var data = PAGE.GRID3.getSelectedRowData();
								data["maxAmt"] = PAGE.FORM1.getItemValue("maxAmt");
								data["today"] = PAGE.FORM1.getItemValue("today");
								data["unitPricExprDttm"] = "99991231";

								//중고여부 셀렉트박스 셋팅
								mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0011"}, PAGE.FORM3, "oldYn");
								
								PAGE.FORM3.setFormData(data); 

								PAGE.FORM3.enableItem("unitPricApplDttm");
								PAGE.FORM3.hideItem("btnRprsPrdtFind");
								PAGE.FORM3.disableItem("oldYn"); //중고여부
								
								PAGE.FORM3.clearChanged();
								outUnitPricNew = false;
								mvno.ui.popupWindowById("FORM3", "출고단가 수정", 730, 290, {
									onClose: function(win) {
										if (! PAGE.FORM3.isChanged()) return true;
										mvno.ui.confirm("CCMN0005", function(){
											win.closeForce();
										});
									}
								});	
								break;
							}
							else
							{
								mvno.ui.alert("ORGN0005");
								break;
							}
						}
					}
				}
		},
			
		/* 
		-----------------------------------
		----- 출고단가 등록/수정 FORM -----
		-----------------------------------
		*/
		FORM3 : {
			title : "출고단가",
			items : [
				{type: 'settings', position: 'label-left', labelWidth: '120', inputWidth: 'auto'},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'rprsPrdtId', label: '대표모델ID',  width: 120,  value: '' , disabled:true, validate: 'NotEmpty'},
					{type: "newcolumn"},
					{type: 'input', name: 'prdtNm', label: '모델명',  width: 140, offsetLeft: 60, value: '', validate: 'NotEmpty', disabled:true},
// 					{type: "newcolumn"},
// 					{type: "button", name: 'btnRprsPrdtFind', value:"찾기"},
				]},
				
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'rprsPrdtCode', label: '대표모델코드',  width: 120,  value: '' , disabled:true, validate: 'NotEmpty'},
					{type: "newcolumn"},
					{type: 'select', name: 'oldYn', label: '중고여부', value:'', offsetLeft: 60 ,width:140, disabled:true, validate: 'NotEmpty',required: true}, 
				]},
				
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'calendar', name: 'unitPricApplDttm' , label: '단가적용일시', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', enableTime:true,	value:'',  calendarPosition: 'bottom' ,width:120, readonly:true,required: true, validate: 'NotEmpty'},
					{type: "newcolumn"},
					{type: 'calendar', name: 'unitPricExprDttm', label: '단가만료일시', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', enableTime:true, offsetLeft: 60,	value:'',  calendarPosition: 'bottom' ,width:140, readonly:true, required: true, disabled:true},
				]} ,
				
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'outUnitPric' , label: '출고단가', value:'' ,width:120,required: true, numberFormat:"00,000,000", validate: 'ValidInteger', maxLength:9},
					{type: "newcolumn"},
					{type: 'input', name: 'mnfctGrant' , label: '제조사장려금', value:'', offsetLeft: 60 ,width:140,required: true, numberFormat:"00,000,000", validate: 'ValidInteger', maxLength:9},
				]} ,
				
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'remrk' , label: '비고', value:'' ,width:444, maxLength:70},
					{type: 'hidden', name: 'maxAmt', value:''}, 
					{type: 'hidden', name: 'today', value:''},
				]}
			],

			buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label: "닫기" }
				}
			},
			onValidateMore : function (data){
				
				if(!data.rprsPrdtId){
					this.pushError("data.rprsPrdtId","모델명","은 필수입니다.");
				}
				if(!outUnitPricNew){
					
					if(data.unitPricApplDttm != "" && (data.unitPricApplDttm <= PAGE.FORM1.getItemValue("today"))){
						this.pushError("data.unitPricApplDttm","단가적용일시",["ICMN0013"]);
					}
				} 
			},
			onChange : function (name, value){
				var form = this;
			},
			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)
			
				switch (btnId) {
// 					case "btnRprsPrdtFind": //대표모델 찾기 팝업
// 						mvno.ui.codefinder("RPRSMLDSET", function(result) {
// 							form.setItemValue("rprsPrdtId", result.rprsPrdtId);
// 							form.setItemValue("rprsPrdtIdResult", result.prdtNm);
// 							form.setItemValue("rprsPrdtId", result.rprsPrdtId); //대표모델ID
// 							form.setItemValue("prdtNm", result.prdtNm);			//모델명
// 							form.setItemValue("rprsPrdtCode", result.prdtCode);	//모델코드
// 							form.setItemValue("mnfctId", result.mnfctId);		//제조사ID
// 						});
// 						break;
						
					case "btnSave":
						
// 						var agncyAmt = null;
// 						var ktAmt = null;
// 						var maxAmtInt = null;
// 						var resultAmt = null;
// 						if(PAGE.FORM3.getItemValue("subsidyMaxYn") == 'Y')
// 						{
							
// 							//제조사지원금 정책용
// 							agncyAmt = parseInt(PAGE.FORM3.getItemValue("mnfctGrant"));
// 							//통신사보조금
// 							ktAmt = parseInt(PAGE.FORM3.getItemValue("newsAgaencySubsidy"));
// 							//단통법 MAX 보조금
// 							maxAmtInt = parseInt(PAGE.FORM3.getItemValue("maxAmt"));
// 							//제조사지원금 정책용 + 통신사보조금
// 							resultAmt = parseInt(agncyAmt) + parseInt(ktAmt);
// 							//단통법MAX보조금 - (제조사지원금 정책용 + 통신사보조금)
// 							resultAmt = parseInt(maxAmtInt) - parseInt(resultAmt);
// 							if(resultAmt < 0 ){
// 								mvno.ui.alert("ORGN0017");
// 								return;
// 							}
// 						}
						if (form.isNew()) { // 등록 처리
							mvno.cmn.ajax(ROOT + "/cmn/hndsetamtmgmt/insertHndstAmtHisList.json", form, function(result) {
								mvno.ui.closeWindowById(form, true);  
								mvno.ui.notify("NCMN0001");
								PAGE.GRID3.refresh();
							});
						}
						else { // 수정 처리
							
							mvno.cmn.ajax(ROOT + "/cmn/hndsetamtmgmt/updateHndstAmtHisList.json", form, function(result) {
								mvno.ui.closeWindowById(form, true);  
								mvno.ui.notify("NCMN0002");
								PAGE.GRID3.refresh();
							});
						}
						break;
					case "btnClose" :
						mvno.ui.closeWindowById(form);	
						break;						  
				}
			}
		}
	};
	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : " ${breadCrumb.breadCrumb}",

			buttonAuth: ${buttonAuth.jsonString},
			
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.ui.createGrid("GRID2");
				mvno.ui.createGrid("GRID3");
				
				//조회조건 제조사 셀렉트박스 셋팅
				mvno.cmn.ajax4lov( ROOT + "/org/mnfctmgmt/selectMnfctList.json", {mnfctYn:"Y"}, PAGE.FORM1, "mnfctId");
				
				//조회조건 모델유형 셀렉트박스 셋팅
				mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0007"}, PAGE.FORM1, "prdtTypeCd");
				
				//AJAX로 테이블조회해서 값셋팅
				mvno.cmn.ajax(ROOT + "/org/hndsetamtmgmt/selectMaxAmt.json", PAGE.FORM1, function(resultData) {
					PAGE.FORM1.setItemValue("maxAmt", resultData.result.maxAmt);
				});
			}
			
			
	};
</script>