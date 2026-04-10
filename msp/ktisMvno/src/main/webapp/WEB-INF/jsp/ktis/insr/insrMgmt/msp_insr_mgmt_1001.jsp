<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>
<div id="FORM2" class="section-box"></div>

<div id="GRID2" class="section"></div>
<div id="GROUP1" style="display:none;">
	<div id="FORM3" class="section-box"></div>
	<div id="GRID3" class="section"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	
	var trtmDt = new Date().format("yyyymmdd");
	
	var insrProdRowData = "";
	var intmInsrProdURL = "";
	
	var modRowIDs = new Object();
	
	var headerChckYN = true;
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"input", label:"대표모델ID", name:"rprsPrdtId", width:100, value:'', offsetLeft:10, labelWidth:60, maxLength:10}
					, {type:"newcolumn"}
					, {type: "button", name: 'btnRprsPrdtFind', value:"검색"}
					, {type:"newcolumn"}
					, {type:"input", name:"rprsPrdtIdResult", value:"", width:160, readonly: true}
					, {type:"newcolumn"}
					, {type:"select", label:"보험사용여부", name:"usgYn", width:80, offsetLeft:20, labelWidth:80}
					, {type:"newcolumn"}
					, {type:"select", label:"단말적용여부", name:"applYn", width:80, offsetLeft:20, labelWidth:80}
				]}
				, {type: "hidden", name: "trtmDt", value:trtmDt}
				, {type: "hidden", name: "pagingYn", value:'Y'}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"} 
			]
			, onChange: function (name, value) {
				switch(name) {
					case "rprsPrdtId":
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
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnRprsPrdtFind": //대표모델 찾기 팝업
						mvno.ui.codefinder("RPRSMLDSET", function(result) {
							form.setItemValue("rprsPrdtId", result.rprsPrdtId);
							form.setItemValue("rprsPrdtIdResult", result.prdtNm);
						});
						
						break;
					
					case "btnSearch":
						PAGE.GRID2.clearAll();
						
						PAGE.GRID1.list(ROOT + "/insr/insrMgmt/getIntmInsrMstList.json", form);
						
						if(!mvno.cmn.isEmpty(form.getItemValue("rprsPrdtId"))){
							PAGE.GRID2.list(ROOT + "/insr/insrMgmt/getIntmInsrRelList.json", form);
						}
						
						break;
				}
			}
		}
	
		, GRID1 : {
			css : {
				height : "250px"
			}
			, title : "보험목록"
			, header : "보험상품명,보험상품코드,보상한도,월정액,보험기간,보험유형,가입기간제한,사용여부,등록자,등록일시,수정자,수정일시"
			, columnIds : "insrProdNm,insrProdCd,cmpnLmtAmt,baseAmt,insrEnggCnt,insrTypeCd,subLmtYn,usgYn,regstId,regstDttm,rvisnId,rvisnDttm"
			, widths : "180,100,90,80,70,100,80,60,80,130,80,130"
			, colAlign : "Left,center,Right,Right,center,center,center,center,center,center,center,center"
			, colTypes : "ro,ro,ron,ron,ro,ro,ro,ro,ro,roXdt,ro,roXdt"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str"
			, hiddenColumns : [5]
			, buttons : {
				right : {
					btnMod : { label : "수정" }
					, btnReg : { label : "등록" }
				}
			}
			, onRowDblClicked : function(rowId, celInd) {
				PAGE.GRID1.callEvent("onButtonClick", ["btnMod"]);
			}
			, onRowSelect:function(rId, cId){
				
				var sData = {
					trtmDt : trtmDt
					, insrProdCd : this.getSelectedRowData().insrProdCd
					, applYn : PAGE.FORM1.getItemValue("applYn")
					, pagingYn : 'Y'
				};
				
				PAGE.GRID2.list(ROOT + "/insr/insrMgmt/getIntmInsrRelList.json", sData);
			}
			, onButtonClick : function(btnId) {
				var grid = this;
				switch (btnId) {
					case "btnMod":
						
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
						mvno.ui.createForm("FORM2");
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM2, "usgYn");		// 보험사용여부
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0248",orderBy:"etc1"}, PAGE.FORM2, "insrEnggCnt");	// 보험기간
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM2, "subLmtYn");		// 보험가입기간제한
						
						PAGE.FORM2.clear();
						PAGE.FORM2.setFormData(grid.getSelectedRowData());
						PAGE.FORM2.setItemLabel("pointLabel", "<span style='color:red;'> ** 가입기간제한이 Y-개통 후 한달 이내에 가입가능 / N-가입제한없음  </span>");
						
						var insrProdData = [{text:grid.getSelectedRowData().insrProdNm, value:grid.getSelectedRowData().insrProdCd}];
						PAGE.FORM2.reloadOptions("insrProdNm", insrProdData);
						PAGE.FORM2.disableItem("insrProdNm");
						PAGE.FORM2.clearChanged();
						
						intmInsrProdURL = "/insr/insrMgmt/updIntmInsrMst.json";
						
						mvno.ui.popupWindowById("FORM2", "단말보험수정", 570, 260, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						
						break;
					
					case "btnReg":
						
						mvno.ui.createForm("FORM2");
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM2, "usgYn");		// 보험사용여부
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0248",orderBy:"etc1"}, PAGE.FORM2, "insrEnggCnt");	// 보험기간
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM2, "subLmtYn");		// 보험가입기간제한
						
						getComboInfo("/insr/insrMgmt/getIntmInsrProdList.json", {trtmDt:trtmDt}, PAGE.FORM2, "insrProdNm", "01");
						
						PAGE.FORM2.clear();
						PAGE.FORM2.enableItem("insrProdNm");
						PAGE.FORM2.clearChanged();
						PAGE.FORM2.setItemLabel("pointLabel", "<span style='color:red;'> ** 가입기간제한이 Y-개통 후 한달 이내에 가입가능 / N-가입제한없음  </span>");
						
						intmInsrProdURL = "/insr/insrMgmt/regIntmInsrMst.json";
						
						mvno.ui.popupWindowById("FORM2", "단말보험등록", 570, 260, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						
						break;
				}
			}
		}
		
		, FORM2 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"select", label:"보험부가서비스", name:"insrProdNm", width:190, offsetLeft:10, labelWidth:110, required:true}
				]}
				, {type:"block", list:[
					{type:"select", label:"보험기간", name:"insrEnggCnt", width:100, offsetLeft:10, labelWidth:110, required:true}
					, {type:"newcolumn"}
					, {type:"select", label:"보험사용여부", name:"usgYn", width:100, offsetLeft:40, labelWidth:110, required:true}
				]}
				, {type:"block", list:[
					{type:"input", label:"보상한도", name:"cmpnLmtAmt", width:100, offsetLeft:10, labelWidth:110, validate: "ValidInteger", required:true, maxLength:10, numberFormat:"0,000,000,000", userdata:{align:"right"}}
					, {type:"newcolumn"}
					, {type:"input", label:"월정액", name:"baseAmt", width:100, offsetLeft:40, labelWidth:110, required:true, disabled:true, validate: "ValidInteger", maxLength:10, numberFormat:"0,000,000,000", userdata:{align:"right"}}
				]}
				, {type:"block", list:[
					{type:"select", label:"가입기간제한", name:"subLmtYn", width:100, offsetLeft:10, labelWidth:110, required:true}
				]}
				, {type:"block", list:[
					{type:"label", labelWidth:500, offsetLeft:10, name:"pointLabel"}
				]}
				, {type: "hidden", name: "insrProdCd"}
				, {type: "hidden", name: "insrTypeCd"}
			]
			, buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			}
			, onChange: function (name, value) {
				var form = this;
				
				switch(name) {
					case "insrProdNm":
						
						if(mvno.cmn.isEmpty(value)){
							form.setItemValue("insrProdCd", "");
							form.setItemValue("baseAmt", "");
							form.setItemValue("insrTypeCd", "");
						} else {
							var rData = insrProdRowData.rows[value];
							
							form.setItemValue("insrProdCd", rData.insrProdCd);
							form.setItemValue("baseAmt", rData.baseAmt);
							form.setItemValue("insrTypeCd", rData.insrTypeCd);
						}
						
						break;
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSave":
						
						mvno.ui.confirm("저장하시겠습니까?", function() {
							mvno.cmn.ajax(ROOT + intmInsrProdURL, form, function(){
								mvno.ui.notify("NCMN0004");
								PAGE.GRID1.refresh();
								PAGE.GRID2.clearAll();
								mvno.ui.closeWindowById("FORM2");
							});
						});
						
						break;
						
					case "btnClose":
						mvno.ui.closeWindowById("FORM2");
						
						break;
				}
			}
		}
		
		, GRID2 : {
			css : {
				height : "230px"
			}
			, title : "단말목록"
			, header : "보험상품명,보험상품코드,제조사,모델ID,모델코드,모델명,적용여부,등록자,등록일시,수정자,수정일시"
			, columnIds : "insrProdNm,insrProdCd,mnfctNm,rprsPrdtId,prdtCode,prdtNm,applYnNm,regstId,regstDttm,rvisnId,rvisnDttm"
			, widths : "180,100,100,90,120,150,70,80,130,80,130"
			, colAlign : "Left,Center,Center,Center,Left,Left,Center,Center,Center,Center,Center"
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,roXdt,ro,roXdt"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str"
			, paging : true
			, pageSize:20
			, showPagingAreaOnInit: true
			, buttons : {
				right : {
					btnMod : { label : "수정" }
					, btnReg : { label : "등록" }
				}
			}
			, onRowDblClicked : function(rowId, celInd) {
				PAGE.GRID2.callEvent("onButtonClick", ["btnMod"]);
				
				PAGE.FORM3.setItemValue("prdtNmByMod", PAGE.GRID2.getSelectedRowData().prdtNm);
				PAGE.GRID3.filterBy(6, PAGE.GRID2.getSelectedRowData().prdtNm);
			}
			, onButtonClick : function(btnId) {
				var grid = this;
				switch (btnId) {
					case "btnMod":
						
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
						mvno.ui.createForm("FORM3");
						mvno.ui.createGrid("GRID3");
						
						PAGE.FORM3.clear();
						
						var insrProdData = [{text:grid.getSelectedRowData().insrProdNm, value:grid.getSelectedRowData().insrProdCd}];
						PAGE.FORM3.reloadOptions("insrProdCd", insrProdData);
						PAGE.FORM3.disableItem("insrProdCd");
						PAGE.FORM3.clearChanged();
						PAGE.FORM3.showItem("MOD");
						PAGE.FORM3.hideItem("REG");
						
						var sData = {
							trtmDt : trtmDt
							, insrProdCd : this.getSelectedRowData().insrProdCd
						};
						
						PAGE.GRID3.list(ROOT + "/insr/insrMgmt/getIntmInsrRelList.json", sData, {async: false});
						
						mvno.ui.hideButton("GRID3", "btnInit");
						mvno.ui.hideButton("GRID3", "btnDel");
						mvno.ui.showButton("GRID3", "btnMod");
						mvno.ui.hideButton("GRID3", "btnSave");
						
						modRowIDs = new Object();
						
						mvno.ui.popupWindowById("GROUP1", "단말수정", 750, 540, {
							onClose: function(win) {
								PAGE.GRID3.filterBy(6, "");
								PAGE.GRID3._f_rowsBuffer = null;
								PAGE.GRID3.clearAll();
								
								if (! PAGE.GRID3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
								
							}
						});
						
						break;
					
					case "btnReg":
						
						mvno.ui.createForm("FORM3");
						mvno.ui.createGrid("GRID3");
						
						getComboInfo("/insr/insrMgmt/getIntmInsrMstList.json", {trtmDt:trtmDt}, PAGE.FORM3, "insrProdCd", "02");
						
						PAGE.FORM3.clear();
						PAGE.FORM3.enableItem("insrProdCd");
						PAGE.FORM3.clearChanged();
						PAGE.FORM3.showItem("REG");
						PAGE.FORM3.hideItem("MOD");
						
						PAGE.GRID3.clearAll();
						
						mvno.ui.showButton("GRID3", "btnInit");
						mvno.ui.showButton("GRID3", "btnDel");
						mvno.ui.showButton("GRID3", "btnSave");
						mvno.ui.hideButton("GRID3", "btnMod");
						
						mvno.ui.popupWindowById("GROUP1", "단말등록", 750, 540, {
							onClose: function(win) {
								PAGE.GRID3.clearAll();
								
								if (! PAGE.GRID3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
								
							}
						});
						
						break;
				}
			}
		}
		
		, FORM3 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"select", label:"단말보험", name:"insrProdCd", width:190, offsetLeft:10, labelWidth:60, required:true}
					, {type:"newcolumn"}
					, {type:"block", name : "REG", list:[
						, {type:"input", label:"단말모델명", name:"prdtNmByReg", width:200, value:'', offsetLeft:20, labelWidth:80, required:true}
						, {type:"newcolumn"}
						, {type: "button", name: 'btnPrdtSrchByReg', value:"검색"}
					]}
					, {type:"block", name : "MOD", list:[
						, {type:"input", label:"단말모델명", name:"prdtNmByMod", width:200, value:'', offsetLeft:20, labelWidth:80}
					]}
				]}
				, {type: "hidden", name: "trtmDt", value:trtmDt}
			]
			, onKeydown : function(inp, ev, name, value){
				
				var form = this;
				
				switch (name) {
					
					case "prdtNmByReg":
						
						if( 53 == ev.keyCode && ev.shiftKey){		//%
							ev.returnValue = false;
						}
						
						if(ev.keyCode == 13) {		//Enter
							form.callEvent("onButtonClick", ["btnPrdtSrchByReg"]);
						}
						
						break;
				}
			}
			, onKeyUp : function(inp, ev, name, value){
				
				var form = this;
				
				switch (name) {
					
					case "prdtNmByMod":
						
						PAGE.GRID3.filterBy(6, form.getItemValue("prdtNmByMod"));
						
						break;
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					
					case "btnPrdtSrchByReg":
						
						var regExp = /\%/gi;
						
						if(regExp.test(form.getItemValue("prdtNmByReg"))){
							mvno.ui.alert("사용할수 없는 문자를 포함 하고 있습니다.");
							return;
						}
						
						var fData = {
							trtmDt : trtmDt
							, insrProdCd : form.getItemValue("insrProdCd")
							, prdtNm : form.getItemValue("prdtNmByReg")
						};
						
						mvno.cmn.ajax(ROOT + "/insr/insrMgmt/getPrdtListByPrdtNm.json", fData, function(resultData) {
							
							var rData = resultData.result.data.rows;
							var totalCount = Number(rData.length);
							
							if(totalCount > 0){
								
								PRDT_INFO :
								for(var idx1 = 0; idx1 < totalCount; idx1++){
									
									for(var idx2 = 0; idx2 < PAGE.GRID3.getRowsNum(); idx2++){
										
										var cData = PAGE.GRID3.getRowData(PAGE.GRID3.getRowId(idx2));
										
										if(cData.rprsPrdtId == rData[idx1].rprsPrdtId && cData.insrProdCd == rData[idx1].insrProdCd){
											continue PRDT_INFO;
										}
									}
									
									var gData = {
										insrProdCd : rData[idx1].insrProdCd
										, insrProdNm : rData[idx1].insrProdNm
										, mnfctNm : rData[idx1].mnfctNm
										, rprsPrdtId : rData[idx1].rprsPrdtId
										, prdtCode : rData[idx1].prdtCode
										, prdtNm : rData[idx1].prdtNm
										, applYn : '1'
									};
									
									PAGE.GRID3.appendRow(gData);
								};
								
							}else{
								mvno.ui.alert("조회 결과가 없습니다.");
							}
							
						});
						
						break;
				}
			}
		}
		
		, GRID3 : {
			css : {
				height : "300px"
			}
			, title : "단말목록"
			, header : "선택,보험상품명,보험상품코드,제조자,모델ID,모델코드,모델명"
			, columnIds : "applYn,insrProdNm,insrProdCd,mnfctNm,rprsPrdtId,prdtCode,prdtNm"
			, widths : "50,90,100,80,80,120,150"
			, colAlign : "Center,Center,Center,Center,Center,Left,Left"
			, colTypes : "ch,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str"
			, buttons : {
				left : {
					btnInit : { label : "초기화" }
				}
				, right : {
					btnDel : { label : "삭제" }
					, btnSave : { label : "저장" }		//단말등록
					, btnMod : { label : "저장" }		//단말수정
					, btnClose : { label : "닫기" }
				}
			}
			, onHeaderClick : function(ind,obj){
				
				if(ind != 0){
					return;
				}
				
				PAGE.GRID3.forEachRow(function(id){
					var cell = PAGE.GRID3.cells(id,ind);
					
					if (cell.isCheckbox()){
						if(headerChckYN){
							if(!cell.isChecked()){
								cell.setValue(1);
								PAGE.GRID3.callEvent("onCheckBox", [id,ind,1]);
							}
						}else{
							if(cell.isChecked()){
								cell.setValue(0);
								PAGE.GRID3.callEvent("onCheckBox", [id,ind,0]);
							}
						}
					}
				});
				
				headerChckYN = !headerChckYN;
			}
			, onCheckBox : function(rId, cInd, state){
				
				if(rId in modRowIDs){
					delete modRowIDs[rId];
				} else {
					modRowIDs[rId] = rId;
				}
			}
			, onButtonClick : function(btnId) {
				var grid = this;
				switch (btnId) {
					case "btnInit":
						
						PAGE.FORM3.clear();
						PAGE.FORM3.setItemValue("trtmDt", trtmDt);
						PAGE.FORM3.clearChanged();
						
						PAGE.GRID3.clearAll();
						
						break;
						
					case "btnDel":
						
						var rowId = this.getCheckedRows(0);
						
						if(!rowId){
							mvno.ui.alert("ECMN0003");
							return;
						}else{
							this.deleteRowByIds(rowId);
						}
						
						this.clearSelection();
						
						break;
						
					case "btnSave":
						
						var rowIds = this.getCheckedRows(0);
						
						if(!rowIds){
							mvno.ui.alert("ECMN0003");
							return;
						}else{
							
							var sData = this.classifyRowsFromIds(rowIds);
							
							mvno.ui.confirm("[ " + this.getCheckedRowData().length + " ]건 단말 정보를 저장하시겠습니까?", function() {
								mvno.cmn.ajax4json(ROOT + "/insr/insrMgmt/regIntmInsrRel.json", sData, function(){
									mvno.ui.notify("NCMN0006");
									PAGE.GRID1.refresh();
									PAGE.GRID2.refresh();
									
									mvno.ui.closeWindowById("GROUP1");
									
								});
							});
						}
						
						break;
						
					case "btnMod":
						
						var aryRowIDs = new Array(); 
						
						for(var prop in modRowIDs){
							aryRowIDs.push(modRowIDs[prop]);
						}
						
						if(aryRowIDs.length < 1){
							mvno.ui.alert("변경 된 내용이 없습니다.");
							return;
						}
						
						var sData = this.classifyRowsFromIds(aryRowIDs);
						
						mvno.ui.confirm("[ " + aryRowIDs.length + " ]건 단말 정보를 변경하시겠습니까?", function() {
							mvno.cmn.ajax4json(ROOT + "/insr/insrMgmt/updIntmInsrRel.json", sData, function(){
								mvno.ui.notify("NCMN0006");
								PAGE.GRID1.refresh();
								PAGE.GRID2.refresh();
								
								mvno.ui.closeWindowById("GROUP1");
								
							});
						});
						
						break;
						
					case "btnClose":
						
						mvno.ui.closeWindowById("GROUP1");
						
						break;
						
				}
			}
		}
		
	};
	
	function getComboInfo(url, sData, dhxForm, itemName, typeCd){
		
		mvno.cmn.ajax(ROOT + url, sData, function(resultData) {
			var totalCount = Number(resultData.result.data.rows.length);
			var insrProdData = [{text:"- 선택 -", value:""}];
			
			var itemPulls = dhxForm.getItemPulls();
			
			if(totalCount > 0){
				
				insrProdRowData = resultData.result.data;
				
				for(var idx = 0; idx < totalCount; idx++){
					var insrProdNm = resultData.result.data.rows[idx].insrProdNm;
					var insrProdCd;
					
					if("01" == typeCd){
						insrProdCd = idx;
					}else{
						insrProdCd = resultData.result.data.rows[idx].insrProdCd;
					}
					
					var data = {text:insrProdNm, value:insrProdCd};
					
					insrProdData.push(data);
				};
				
			}else{
				insrProdData = [{text:"- 없음 -", value:""}];
			}
			
			dhxForm.reloadOptions(itemName, insrProdData);
		}
		, {async: false} );
	}
	
	var PAGE = {
	      title : "${breadCrumb.title}",
	      breadcrumb : "${breadCrumb.breadCrumb}",
		  buttonAuth: ${buttonAuth.jsonString} ,
		  onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			mvno.ui.createGrid("GRID2");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM1, "usgYn");	// 보험사용여부
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM1, "applYn");	// 단말적용여부
			
		}
	};
</script>

