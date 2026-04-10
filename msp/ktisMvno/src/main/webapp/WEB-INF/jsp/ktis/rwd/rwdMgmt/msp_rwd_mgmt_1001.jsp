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
	
	var rwdProdRowData = "";
	var rwdProdURL = "";
	
	var modRowIDs = new Object();
	
	var headerChckYN = true;
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"input", label:"대표모델ID", name:"rprsPrdtId", width:90, value:'', offsetLeft:10, labelWidth:60, maxLength:10}
					, {type:"newcolumn"}
					, {type: "button", name: 'btnRprsPrdtFind', value:"검색"}
					, {type:"newcolumn"}
					, {type:"input", name:"rprsPrdtIdResult", value:"", width:160, readonly: true}
					, {type:"newcolumn"}
					, {type:"select", label:"서비스사용여부", name:"useYn", width:100, offsetLeft:20, labelWidth:100}
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
						
						PAGE.GRID1.list(ROOT + "/rwd/rwdMgmt/getRwdMstList.json", form);
						
						if(!mvno.cmn.isEmpty(form.getItemValue("rprsPrdtId"))){
							PAGE.GRID2.list(ROOT + "/rwd/rwdMgmt/getRwdRelList.json", form);
						}
						
						break;
				}
			}
		}
	
		, GRID1 : {
			css : {
				height : "250px"
			}
			, title : "보상서비스목록"
			, header : "보상서비스명,서비스코드,월정액,약정기간,사용여부,등록자,등록일시,수정자,수정일시" //9
			, columnIds : "rwdProdNm,rwdProdCd,baseAmt,rwdPrd,useYn,regstId,regstDttm,rvisnId,rvisnDttm" //9
			, widths : "200,100,80,80,60,80,136,80,136" //9
			, colAlign : "Left,center,Right,center,center,center,center,center,center" //9
			, colTypes : "ro,ro,ron,ro,ro,ro,roXdt,ro,roXdt" //9
			, colSorting : "str,str,int,str,str,str,str,str,str" //9
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
					, rwdProdCd : this.getSelectedRowData().rwdProdCd
					, applYn : PAGE.FORM1.getItemValue("applYn")
					, pagingYn : 'Y'
				};
				
				PAGE.GRID2.list(ROOT + "/rwd/rwdMgmt/getRwdRelList.json", sData);
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
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM2, "useYn");		// 보상서비스 사용여부
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0259",orderBy:"etc1"}, PAGE.FORM2, "rwdPrd");	    // 보상서비스 기간
						
						PAGE.FORM2.clear();
						PAGE.FORM2.setFormData(grid.getSelectedRowData());
						
						var rwdProdData = [{text:grid.getSelectedRowData().rwdProdNm, value:grid.getSelectedRowData().rwdProdCd}];
						PAGE.FORM2.reloadOptions("rwdProdNm", rwdProdData);
						PAGE.FORM2.disableItem("rwdProdNm");
						PAGE.FORM2.clearChanged();
						
						rwdProdURL = "/rwd/rwdMgmt/updRwdMst.json";
						
						mvno.ui.popupWindowById("FORM2", "보상서비스수정", 400, 245, {
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
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM2, "useYn");		// 서비스사용여부
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0259",orderBy:"etc1"}, PAGE.FORM2, "rwdPrd");	    // 서비스가입기간
						
						getComboInfo("/rwd/rwdMgmt/getRwdProdList.json", {trtmDt:trtmDt}, PAGE.FORM2, "rwdProdNm", "01");
						
						PAGE.FORM2.clear();
						PAGE.FORM2.enableItem("rwdProdNm");
						PAGE.FORM2.clearChanged();
						
						rwdProdURL = "/rwd/rwdMgmt/regRwdMst.json";
						
						mvno.ui.popupWindowById("FORM2", "보상서비스등록", 400, 245, {
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
					{type:"select", label:"보상부가서비스", name:"rwdProdNm", width:200, offsetLeft:10, labelWidth:110, required:true}
				]}
				, {type:"block", list:[
					{type:"select", label:"서비스약정기간", name:"rwdPrd", width:100, offsetLeft:10, labelWidth:110, required:true}
				]}
				, {type:"block", list:[
					{type:"select", label:"사용여부", name:"useYn", width:100, offsetLeft:10, labelWidth:110, required:true}
				]}
				, {type:"block", list:[
					{type: "hidden", name: "rwdProdCd"}
					, {type:"newcolumn"}
					, {type:"input", label:"월정액", name:"baseAmt", width:100, offsetLeft:10, labelWidth:110, required:true, disabled:true, validate: "ValidInteger", maxLength:10, numberFormat:"0,000,000,000", userdata:{align:"right"}}
				]}
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
					case "rwdProdNm":
						
						if(mvno.cmn.isEmpty(value)){
							form.setItemValue("rwdProdCd", "");
							form.setItemValue("baseAmt", "");
						} else {
							var rData = rwdProdRowData.rows[value];
							
							form.setItemValue("rwdProdCd", rData.rwdProdCd);
							form.setItemValue("baseAmt", rData.baseAmt);
						}
						
						break;
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSave":
						
						mvno.ui.confirm("저장하시겠습니까?", function() {
							mvno.cmn.ajax(ROOT + rwdProdURL, form, function(){
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
			, header : "보상서비스명,서비스코드,제조사,모델ID,모델코드,모델명,적용여부,등록자,등록일시,수정자,수정일시"
			, columnIds : "rwdProdNm,rwdProdCd,mnfctNm,rprsPrdtId,prdtCode,prdtNm,applYnNm,regstId,regstDttm,rvisnId,rvisnDttm"
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
						
						var rwdProdData = [{text:grid.getSelectedRowData().rwdProdNm, value:grid.getSelectedRowData().rwdProdCd}];
						PAGE.FORM3.reloadOptions("rwdProdCd", rwdProdData);
						PAGE.FORM3.disableItem("rwdProdCd");
						PAGE.FORM3.clearChanged();
						PAGE.FORM3.showItem("MOD");
						PAGE.FORM3.hideItem("REG");
						
						var sData = {
							trtmDt : trtmDt
							, rwdProdCd : this.getSelectedRowData().rwdProdCd
						};
						
						PAGE.GRID3.list(ROOT + "/rwd/rwdMgmt/getRwdRelList.json", sData, {async: false});
						
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
						
						getComboInfo("/rwd/rwdMgmt/getRwdMstList.json", {trtmDt:trtmDt}, PAGE.FORM3, "rwdProdCd", "02");
						
						PAGE.FORM3.clear();
						PAGE.FORM3.enableItem("rwdProdCd");
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
					{type:"select", label:"보상서비스명", name:"rwdProdCd", width:200, offsetLeft:10, labelWidth:90, required:true}
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
							, rwdProdCd : form.getItemValue("rwdProdCd")
							, prdtNm : form.getItemValue("prdtNmByReg")
						};
						
						mvno.cmn.ajax(ROOT + "/rwd/rwdMgmt/getPrdtListByPrdtNm.json", fData, function(resultData) {
							
							var rData = resultData.result.data.rows;
							var totalCount = Number(rData.length);
							
							if(totalCount > 0){
								
								PRDT_INFO :
								for(var idx1 = 0; idx1 < totalCount; idx1++){
									
									for(var idx2 = 0; idx2 < PAGE.GRID3.getRowsNum(); idx2++){
										
										var cData = PAGE.GRID3.getRowData(PAGE.GRID3.getRowId(idx2));
										
										if(cData.rprsPrdtId == rData[idx1].rprsPrdtId && cData.rwdProdCd == rData[idx1].rwdProdCd){
											continue PRDT_INFO;
										}
									}
									
									var gData = {
										  rwdProdCd : rData[idx1].rwdProdCd
										, rwdProdNm : rData[idx1].rwdProdNm
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
			, header : "선택,보상서비스명,보상서비스코드,제조사,모델ID,모델코드,모델명"
			, columnIds : "applYn,rwdProdNm,rwdProdCd,mnfctNm,rprsPrdtId,prdtCode,prdtNm"
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
								mvno.cmn.ajax4json(ROOT + "/rwd/rwdMgmt/regRwdRel.json", sData, function(){
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
							mvno.cmn.ajax4json(ROOT + "/rwd/rwdMgmt/updRwdRel.json", sData, function(){
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
			var rwdProdData = [{text:"- 선택 -", value:""}];
			
			var itemPulls = dhxForm.getItemPulls();
			
			if(totalCount > 0){
				
				rwdProdRowData = resultData.result.data;
				
				for(var idx = 0; idx < totalCount; idx++){
					var rwdProdNm = resultData.result.data.rows[idx].rwdProdNm;
					var rwdProdCd;
					
					if("01" == typeCd){
						rwdProdCd = idx;
					}else{
						rwdProdCd = resultData.result.data.rows[idx].rwdProdCd;
					}
					
					var data = {text:rwdProdNm, value:rwdProdCd};
					
					rwdProdData.push(data);
				};
				
			}else{
				rwdProdData = [{text:"- 없음 -", value:""}];
			}
			
			dhxForm.reloadOptions(itemName, rwdProdData);
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
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM1, "useYn");	// 서비스사용여부
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM1, "applYn");	// 단말적용여부
			
		}
	};
</script>

