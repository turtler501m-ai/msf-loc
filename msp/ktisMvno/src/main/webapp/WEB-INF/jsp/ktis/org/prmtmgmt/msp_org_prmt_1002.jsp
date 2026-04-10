<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_prmt_1002.jsp
	 * @Description : 채널별요금할인관리
	 * @
	 * @ 수정일		수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2018.06.07 강무성 최초작성
	 * @author : 
	 * @Create Date : 
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search" ></div>
<!-- 그리드영역 -->
<div id="GRID1"></div>

<div id="POPUP1">
	<div id="POPUP1TOP"  class="section-full"></div>
	<div id="POPUP1MID" class="section-box"></div>
</div>

<div id="GROUP1" style="display:none;">
	<div id="FORM2" class="section-box"></div>
	<div class="row">
		<div id="GRID2" class="c2-5"></div>
		<div id="GRID3" class="c2-5"></div>
		<div id="GRID6" class="c2-5"></div>
		<div id="GRID4" class="c2-5"></div>
	</div>
	<div id="GRID5"></div>
</div>

<div id="GROUP2" style="display: none;">
	<div id="FORM3" class="section-search"></div>
	<div class="row">
		<div id="GRID7"></div>
	</div>
</div>

<!-- Script -->
<script type="text/javascript">
	var today = new Date().format("yyyymmdd");
	var allChkYN = true;
	var endDt = mvno.cmn.getAddDay(today, 30);
	
	var vasRowsData
	
	var DHX = {
		// 검색
		FORM1 : { 
			items : [ 
				{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 70, blockOffset : 0 }
				,{ type : "block", list : [
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchBaseDate', label: '기준일자', calendarPosition: 'bottom', width:100, offsetLeft:10 }
					, {type: "newcolumn", offset:30}
					, {type:"input", width:345, label:"프로모션명", name: "prmtNm", offsetLeft:20}
				]}
				,{ type: "block", list : [
					{type:"select", width:100, label:"채널유형", name:"orgnType", offsetLeft:10}
					, {type: "newcolumn", offset:30}
					, {type:"select", width:100, label:"구매유형", name:"reqBuyType", offsetLeft:20}
					, {type: "newcolumn", offset:30}
					, {type:"select", width:120, label:"모집경로", name:"onOffType", offsetLeft:20}
				]}
				,{ type: "block", list : [
					{type:"select", width:70, name:"orgnData", offsetLeft:0 ,options:[{value:"orgnNm",text:"조직명"},{value:"orgnId",text:"조직ID"}]},
					{type:"newcolumn"},
					{type:"input", width:100, name:"orgnVal" },
					{type:"newcolumn"},
					{type:"select", width:80, name:"pRateData", offsetLeft:45 ,options:[{value:"pRateNm",text:"요금제명"},{value:"pRateCd",text:"요금제ID"}]},
					{type:"newcolumn"},
					{type:"input", width:100, name:"pRateVal"},
					{type:"newcolumn"},
					{type:"select", width:100, name:"rRateData", offsetLeft:35 ,options:[{value:"rRateNm",text:"부가서비스명"},{value:"rRateCd",text:"부가상품ID"}]},
					{type:"newcolumn"},
					{type:"input", width:100, name:"rRateVal"},
					{type:"newcolumn"}
				]}
				,{ type : 'hidden', name: "DWNLD_RSN", value:""}
				,{ type : "button",name : "btnSearch",value : "조회", className:"btn-search2" }
			]
			,onButtonClick : function(btnId) {
				
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						
						var url1 = ROOT + "/org/prmtmgmt/getChrgPrmtList.json";
						
						PAGE.GRID1.list(url1, form);
						
					break;
				}
			}
		}	//FORM1 End
		
		, GRID1 : {
			css : { 
				height : "520px"  
			}
			,title : "프로모션목록"
			,header : "선택,프로모션ID,프로모션명,시작일자,종료일자,채널유형,구매유형,신규,번호이동,약정기간_무약정,약정기간_12개월,약정기간_18개월,약정기간_24개월,약정기간_30개월,약정기간_36개월,등록자,등록일시,수정자,수정일시"
			,columnIds : "rowCheck,prmtId,prmtNm,strtDt,endDt,orgnTypeNm,reqBuyTypeNm,nacYn,mnpYn,enggCnt0,enggCnt12,enggCnt18,enggCnt24,enggCnt30,enggCnt36,regstNm,regstDttm,rvisnNm,rvisnDttm"
			,widths : "50,110,250,100,100,100,110,80,80,110,110,110,110,110,110,80,140,80,140"
			,colAlign : "Center,Center,Left,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center"
			,colTypes : "ch,ro,ro,roXd,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXdt,ro,roXdt"
			,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			//,hiddenColumns : "5"
			,paging : true
			,pageSize : 20
			//,showPagingAreaOnInit : true
			,buttons : {
				hright : {
					btnUpExcel: { label : "엑셀업로드"}
					,btnDownExcel : { label : "자료생성"}
					,btnPrmtExcel : { label : "프로모션엑셀다운"}
				},
				left : {
					btnAllChk : {label : "전체선택" }
					,btnChrgPrmtCopy : { label : "프로모션복사" }
					,btnAddAgncy : { label : "대리점추가" }
					,btnEndDt : { label : "종료일시변경" }
					,btnTempPrmt : { label : "프로모션일괄등록양식" }
				},
				right : {
					btnReg : { label : "등록" }
					,btnMod : { label : "수정" }
				}
			}
			/* ,onRowSelect : function(rowId){
				var cell = this.cells(rowId,0);
				if(!cell.isChecked()) cell.setValue(1);
				else cell.setValue(0);
			} */
			,onRowDblClicked : function(rowId, celInd) {
				// 수정버튼 누른것과 같이 처리
				this.callEvent("onButtonClick", ["btnMod"]);
			}
			,onButtonClick : function(btnId, selectedData) {

				var grid = this;         
			 	var chkData = grid.getCheckedRowData();
				
				switch (btnId) {
					case "btnDownExcel":

						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}

						mvno.cmn.downloadCallback(function(result) {
							PAGE.FORM1.setItemValue("DWNLD_RSN",result);
							mvno.cmn.ajax(ROOT + "/org/prmtmgmt/getChrgPrmtListExcelDown.json?menuId=MSP2002210", PAGE.FORM1.getFormData(true), function(result){
								mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
							});
						});

					break;

					case "btnUpExcel":

						mvno.ui.createGrid("POPUP1TOP");
						mvno.ui.createForm("POPUP1MID");

						PAGE.POPUP1TOP.clearAll();
						PAGE.POPUP1MID.clear();

						PAGE.POPUP1MID.setFormData({});
						PAGE.POPUP1MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP1TOP.clearAll();

							return true;
						});

						var uploader = PAGE.POPUP1MID.getUploader("file_upload1");
						uploader.revive();

						mvno.ui.popupWindowById("POPUP1", "프로모션 일괄등록", 950, 700, {
							onClose2: function(win) {
								uploader.reset();
							}
						});

						break;

					case "btnPrmtExcel" :

						if(chkData.length < 1 ){
							mvno.ui.alert("ECMN0003");
							return;
						}

						var prmtIdList = [];
						var searchData =  PAGE.FORM1.getFormData(true);

						for(var i = 0; i < chkData.length; i++){
							var chkDataPrmtId = chkData[i].prmtId;
							prmtIdList.push(chkDataPrmtId);
						};

						var sData = {
								prmtIdList : prmtIdList
						};

						mvno.cmn.download('/org/prmtmgmt/getChoChrgPrmtListExcelDown.json', false, {postData:sData});
						
						break;

					case "btnAllChk" :
						
						this.forEachRow(function(id){
							
							var cell = this.cells(id,0);
							
							if (cell.isCheckbox()){
								if(allChkYN){
									if(!cell.isChecked()) cell.setValue(1);
										
								}else{
									if(cell.isChecked()) cell.setValue(0);
								}
							}
						});
						
						allChkYN = !allChkYN;
						
						break;

					case "btnChrgPrmtCopy" :

						if(chkData.length < 1 ){
							mvno.ui.alert("ECMN0003");
							return;
						}

						if(chkData.length > 1 ){
							mvno.ui.alert("프로모션을 1개만 선택(체크)해주세요");
							return;
						}

						var chkRowData = chkData[0];

						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID2");	// 조직
						mvno.ui.createGrid("GRID3");	// 요금제
						mvno.ui.createGrid("GRID4");	// 부가서비스
						mvno.ui.createGrid("GRID5");	// 상세
						mvno.ui.createGrid("GRID6");	// 모집경로
						
						PAGE.GRID2.clearAll();
						PAGE.GRID3.clearAll();
						PAGE.GRID4.clearAll();
						PAGE.GRID5.clearAll();
						PAGE.GRID6.clearAll();
						
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9022"}, PAGE.FORM2, "reqBuyType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0055"}, PAGE.FORM2, "orgnType");
						
						PAGE.GRID2.list(ROOT + "/org/prmtmgmt/getChrgPrmtOrgnList.json", {orgnType:chkRowData.orgnType, prmtId:chkRowData.prmtId});
						PAGE.GRID3.list(ROOT + "/org/prmtmgmt/getChrgPrmtSocList.json", {prmtId:chkRowData.prmtId});
						PAGE.GRID4.list(ROOT + "/org/prmtmgmt/getChrgPrmtAddList.json", {prmtId:chkRowData.prmtId});
						//PAGE.GRID5.list(ROOT + "/org/prmtmgmt/getChrgPrmtDtlList.json", {prmtId:chkRowData.prmtId});
						PAGE.GRID6.list(ROOT + "/org/prmtmgmt/getChrgPrmtOnoffList.json", {prmtId:chkRowData.prmtId});
						
						var fData = {
							prmtNm : chkRowData.prmtNm + " 복사본"
							,reqBuyType : chkRowData.reqBuyType
							,orgnType : chkRowData.orgnType
							,nacYn : (chkRowData.nacYn == 'Y') ? 1 : 0
							,mnpYn : (chkRowData.mnpYn == 'Y') ? 1 : 0
							,enggCnt0 : (chkRowData.enggCnt0 == 'Y') ? 1 : 0
							,enggCnt12 : (chkRowData.enggCnt12 == 'Y') ? 1 : 0
							,enggCnt18 : (chkRowData.enggCnt18 == 'Y') ? 1 : 0
							,enggCnt24 : (chkRowData.enggCnt24 == 'Y') ? 1 : 0
							,enggCnt30 : (chkRowData.enggCnt30 == 'Y') ? 1 : 0
							,enggCnt36 : (chkRowData.enggCnt36 == 'Y') ? 1 : 0
						};
						
						
						PAGE.FORM2.setFormData(fData, true);
						
						PAGE.GRID4.attachEvent("onCellChanged", function(rId, cInd, nValue){
							var grid = this;
							
							if( cInd != 1 ) {
								return;
							}
							
							if(grid.getSelectedRowId() == null){
								return;
							}
							
							if(grid.cells(rId, 1).getValue() == ""){
								return;
							}
							
							var bDupChk = false;
							
							if("N" == vasRowsData.rows[nValue].dupYn){
								
								grid.forEachRow(function(id){
									var rowData = grid.getRowData(id);
									
									if(rId != id){
										if(vasRowsData.rows[nValue].vasCd == rowData.vasCd){
											grid.cells(rId, 1).setValue("");
											grid.cells(rId, 2).setValue("");
											grid.cells(rId, 3).setValue("");
											grid.cells(rId, 4).setValue("");
											mvno.ui.alert("중복 선택이 불가능한 부가서비스 입니다.");
											bDupChk = true;
										}
									}
								});
								
							}
							
							if(!bDupChk){
								grid.cells(rId, 2).setValue(vasRowsData.rows[nValue].baseAmt);
								grid.cells(rId, 3).setValue(vasRowsData.rows[nValue].vasCd);
								grid.cells(rId, 4).setValue(vasRowsData.rows[nValue].dupYn);
							}
						});
						
						PAGE.FORM2.disableItem("prmtId");
						
						PAGE.FORM2.setItemValue("strtDt", today);
						PAGE.FORM2.setItemValue("endDt", endDt);
						
						PAGE.FORM2.enableItem("INFO");
						PAGE.FORM2.enableItem("JOIN");
						PAGE.FORM2.enableItem("ENGG");
						PAGE.FORM2.enableItem("strtDt");
						PAGE.FORM2.enableItem("reqBuyType");
						PAGE.FORM2.enableItem("orgnType");
						PAGE.GRID2.setEditable(true);
						PAGE.GRID3.setEditable(true);
						PAGE.GRID4.setEditable(true);
						PAGE.GRID6.setEditable(true);
						
						PAGE.FORM2.enableItem("nacYn");
						PAGE.FORM2.enableItem("mnpYn");
						PAGE.FORM2.enableItem("enggCnt0");
						PAGE.FORM2.enableItem("enggCnt12");
						PAGE.FORM2.enableItem("enggCnt18");
						PAGE.FORM2.enableItem("enggCnt24");
						PAGE.FORM2.enableItem("enggCnt30");
						PAGE.FORM2.enableItem("enggCnt36");
						
						mvno.ui.showButton("GRID4" , 'btnDel');
						mvno.ui.showButton("GRID4" , 'btnAdd');
						mvno.ui.showButton("GRID4" , 'btnReg');
						
						mvno.ui.hideButton("GRID5" , 'btnEndDt');
						mvno.ui.hideButton("GRID5" , 'btnEnd');
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "채널별요금할인복사", 930, 750, {
							onClose: function(win) {
								if (!PAGE.FORM2.isChanged()) {
									return true;
								}
								mvno.ui.confirm("CCMN0005", function() {
									win.closeForce();
								});
							}
						,maximized:true
						});
						
						break;

					case "btnEndDt" :
						
						if(chkData.length < 1 ){
							mvno.ui.alert("ECMN0003");
							return;
						}

						mvno.ui.createForm("FORM20");
						
						PAGE.FORM20.setItemValue("endDtMod", today);
						
						mvno.ui.popupWindowById("FORM20", "종료일시변경", 350, 140, {
							onClose: function(win) {
								if (!PAGE.FORM20.isChanged()) {
									return true;
								}
								mvno.ui.confirm("CCMN0005", function() {
									win.closeForce();
								});
							}
						});
			
					break;
					
					case "btnTempPrmt":
						mvno.cmn.download("/org/prmtmgmt/getChrgTempExcelDown.json");
						break;

					case "btnReg":
						
						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID2");	// 조직
						mvno.ui.createGrid("GRID3");	// 요금제
						mvno.ui.createGrid("GRID4");	// 부가서비스
						mvno.ui.createGrid("GRID5");	// 상세
						mvno.ui.createGrid("GRID6");	// 모집경로
						
						PAGE.GRID2.clearAll();
						PAGE.GRID3.clearAll();
						PAGE.GRID4.clearAll();
						PAGE.GRID5.clearAll();
						PAGE.GRID6.clearAll();
						
						PAGE.FORM2.setFormData(true);
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9022"}, PAGE.FORM2, "reqBuyType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0055"}, PAGE.FORM2, "orgnType");
						
						PAGE.GRID2.list(ROOT + "/org/prmtmgmt/getChrgPrmtOrgnList.json", "");
						PAGE.GRID3.list(ROOT + "/org/prmtmgmt/getChrgPrmtSocList.json", "");
						PAGE.GRID6.list(ROOT + "/org/prmtmgmt/getChrgPrmtOnoffList.json", "");
						
						mvno.cmn.ajax(ROOT + "/org/prmtmgmt/getChrgPrmtAddList.json", "", function(resultData) {
							vasRowsData = resultData.result.data;
							var totalCount = Number(resultData.result.data.total);
							
							var combobox = PAGE.GRID4.getCombo(1);
							
							if(totalCount > 0){
								for(var idx = 0; idx < totalCount; idx++){
									var vasNm = vasRowsData.rows[idx].vasNm;
									var vasCd = vasRowsData.rows[idx].vasCd;
									
									combobox.put(idx, vasNm);
								};
							}
						});
						
						PAGE.GRID4.attachEvent("onCellChanged", function(rId, cInd, nValue){
							var grid = this;
							
							if( cInd != 1 ) {
								return;
							}
							
							if(grid.getSelectedRowId() == null){
								return;
							}
							
							if(grid.cells(rId, 1).getValue() == ""){
								return;
							}
							
							var bDupChk = false;
							
							if("N" == vasRowsData.rows[nValue].dupYn){
								
								grid.forEachRow(function(id){
									var rowData = grid.getRowData(id);
									
									if(rId != id){
										if(vasRowsData.rows[nValue].vasCd == rowData.vasCd){
											grid.cells(rId, 1).setValue("");
											grid.cells(rId, 2).setValue("");
											grid.cells(rId, 3).setValue("");
											grid.cells(rId, 4).setValue("");
											mvno.ui.alert("중복 선택이 불가능한 부가서비스 입니다.");
											bDupChk = true;
										}
									}
								});
								
							}
							
							if(!bDupChk){
								grid.cells(rId, 2).setValue(vasRowsData.rows[nValue].baseAmt);
								grid.cells(rId, 3).setValue(vasRowsData.rows[nValue].vasCd);
								grid.cells(rId, 4).setValue(vasRowsData.rows[nValue].dupYn);
							}
						});
						
						
						PAGE.FORM2.disableItem("prmtId");
						PAGE.FORM2.setItemValue("reqBuyType", "UU");
						PAGE.FORM2.setItemValue("strtDt", today);
						PAGE.FORM2.setItemValue("endDt", endDt);
						
						PAGE.FORM2.enableItem("INFO");
						PAGE.FORM2.enableItem("JOIN");
						PAGE.FORM2.enableItem("ENGG");
						PAGE.FORM2.enableItem("strtDt");
						PAGE.FORM2.enableItem("reqBuyType");
						PAGE.FORM2.enableItem("orgnType");
						PAGE.GRID2.setEditable(true);
						PAGE.GRID3.setEditable(true);
						PAGE.GRID4.setEditable(true);
						PAGE.GRID6.setEditable(true);
						
						PAGE.FORM2.enableItem("nacYn");
						PAGE.FORM2.enableItem("mnpYn");
						PAGE.FORM2.enableItem("enggCnt0");
						PAGE.FORM2.enableItem("enggCnt12");
						PAGE.FORM2.enableItem("enggCnt18");
						PAGE.FORM2.enableItem("enggCnt24");
						PAGE.FORM2.enableItem("enggCnt30");
						PAGE.FORM2.enableItem("enggCnt36");
						
						mvno.ui.showButton("GRID4" , 'btnDel');
						mvno.ui.showButton("GRID4" , 'btnAdd');
						mvno.ui.showButton("GRID4" , 'btnReg');
						
						mvno.ui.hideButton("GRID5" , 'btnEndDt');
						mvno.ui.hideButton("GRID5" , 'btnEnd');
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "채널별요금할인등록", 930, 750, {
							onClose: function(win) {
								if (!PAGE.FORM2.isChanged()) {
									return true;
								}
								mvno.ui.confirm("CCMN0005", function() {
									win.closeForce();
								});
							}
						,maximized:true
						});
						
						break;
						
					case "btnMod":

						if(chkData.length < 1 ){
							mvno.ui.alert("ECMN0003");
							return;
						}

						if(chkData.length > 1 ){
							mvno.ui.alert("프로모션을 1개만 선택(체크)해주세요");
							return;
						}
						var data = chkData[0];
						
						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID2");
						mvno.ui.createGrid("GRID3");
						mvno.ui.createGrid("GRID4");
						mvno.ui.createGrid("GRID5");
						mvno.ui.createGrid("GRID6");
						
						PAGE.GRID2.clearAll();
						PAGE.GRID3.clearAll();
						PAGE.GRID4.clearAll();
						PAGE.GRID5.clearAll();
						PAGE.GRID6.clearAll();
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9022"}, PAGE.FORM2, "reqBuyType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0055"}, PAGE.FORM2, "orgnType");
						
						PAGE.GRID2.list(ROOT + "/org/prmtmgmt/getChrgPrmtOrgnList.json", {orgnType:data.orgnType, prmtId:data.prmtId});
						PAGE.GRID3.list(ROOT + "/org/prmtmgmt/getChrgPrmtSocList.json", {prmtId:data.prmtId});
						PAGE.GRID4.list(ROOT + "/org/prmtmgmt/getChrgPrmtAddList.json", {prmtId:data.prmtId});
						PAGE.GRID5.list(ROOT + "/org/prmtmgmt/getChrgPrmtDtlList.json", {prmtId:data.prmtId});
						PAGE.GRID6.list(ROOT + "/org/prmtmgmt/getChrgPrmtOnoffList.json", {prmtId:data.prmtId});
						
						var fData = {
							prmtId : data.prmtId
							,prmtNm : data.prmtNm
							,reqBuyType : data.reqBuyType
							,orgnType : data.orgnType
							,strtDt : data.strtDt
							,endDt : data.endDt
							,nacYn : (data.nacYn == 'Y') ? 1 : 0
							,mnpYn : (data.mnpYn == 'Y') ? 1 : 0
							,enggCnt0 : (data.enggCnt0 == 'Y') ? 1 : 0
							,enggCnt12 : (data.enggCnt12 == 'Y') ? 1 : 0
							,enggCnt18 : (data.enggCnt18 == 'Y') ? 1 : 0
							,enggCnt24 : (data.enggCnt24 == 'Y') ? 1 : 0
							,enggCnt30 : (data.enggCnt30 == 'Y') ? 1 : 0
							,enggCnt36 : (data.enggCnt36 == 'Y') ? 1 : 0
						};
						
						
						PAGE.FORM2.setFormData(fData, true);
						
						PAGE.FORM2.disableItem("INFO");
						PAGE.FORM2.disableItem("JOIN");
						PAGE.FORM2.disableItem("ENGG");
						PAGE.FORM2.disableItem("strtDt");
						PAGE.FORM2.disableItem("reqBuyType");
						PAGE.FORM2.disableItem("orgnType");
						PAGE.GRID2.setEditable(false);
						PAGE.GRID3.setEditable(false);
						PAGE.GRID4.setEditable(false);
						PAGE.GRID6.setEditable(false);
						
						mvno.ui.hideButton("GRID4" , 'btnDel');
						mvno.ui.hideButton("GRID4" , 'btnAdd');
						mvno.ui.hideButton("GRID4" , 'btnReg');
						
						mvno.ui.showButton("GRID5" , 'btnEndDt');
						mvno.ui.showButton("GRID5" , 'btnEnd');
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "채널별요금할인수정", 930, 750, {
							onClose: function(win) {
								if (!PAGE.FORM2.isChanged()) {
									return true;
								}
								mvno.ui.confirm("CCMN0005", function() {
									win.closeForce();
								});
							}
						,maximized:true
						});
						
						break;
						
					case "btnAddAgncy" :
						if(chkData.length < 1 ){
							mvno.ui.alert("ECMN0003");
							return;
						}
						
						mvno.ui.createForm("FORM3");
						mvno.ui.createGrid("GRID7");
						
						PAGE.GRID7.list(ROOT + "/org/prmtmgmt/getChrgPrmtOrgnList.json");
						
						mvno.ui.popupWindowById("GROUP2", "대리점추가", 578, 500, {
								onClose: function(win) {
									if (!PAGE.FORM3.isChanged() || !PAGE.GRID7.isChanged()){
										return true;
									}
									mvno.ui.confirm("CCMN0005", function() {
										win.closeForce();
									});
								}
						});

						PAGE.FORM3.clear();
						PAGE.GRID7.clearAll();
						
					break;
				}
			}
		}	//GRID1 End
		, POPUP1TOP : {
			 css : { height : "465px" }
			,title : "프로모션등록상세"
			,header : "번호,프로모션명,시작일자,종료일자,채널유형,구매유형,신규,번호이동,약정기간_무약정,약정기간_12개월,약정기간_18개월,약정기간_24개월,약정기간_30개월,약정기간_36개월,대상조직,대상요금제,모집경로,대상부가서비스"
			,columnIds : "regNum,prmtNm,strtDt,endDt,orgnType,reqBuyType,nacYn,mnpYn,enggCnt0,enggCnt12,enggCnt18,enggCnt24,enggCnt30,enggCnt36,orgnId,rateCd,onOffType,soc"
			,widths : "50,200,100,100,100,100,80,80,120,120,120,120,120,120,120,120,60,120"
			,colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center"
			,colTypes : "ron,ro,roXd,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
		}	//POPUP1TOP End

		, POPUP1MID : {
			 title : "엑셀업로드"
			,items : [
				 { type : "block"
				  ,list : [
					 { type : "newcolumn", offset : 20 }
					,{ type : "upload"
					  ,label : "채널별요금할인 일괄등록 엑셀파일"
					  ,name : "file_upload1"
					  ,inputWidth : 830
					  ,url : "/rcp/dlvyMgmt/regParExcelUp.do"
					  ,userdata : { limitSize:10000 }
					  ,autoStart : true }
				]},
				{type:"input", width:100, name : "rowData", hidden:true}
			]

			,buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			}
			,onUploadFile : function(realName, serverName) {
				fileName = serverName;
			}
			,onUploadComplete : function(count){

				var url = ROOT + "/org/prmtmgmt/readChrgExcelUpList.json";
				var userOptions = {timeout:180000};

				mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
					var rData = resultData.result;

					PAGE.POPUP1TOP.clearAll();

					PAGE.POPUP1TOP.parse(rData.data.rows, "js");

					mvno.ui.enableButton("POPUP1MID", "btnChkExl");
				}, userOptions);
			}
			,onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {
					case "btnSave" :

						if(PAGE.POPUP1TOP.getRowsNum() == 0) {
							mvno.ui.alert("파일을 첨부해 주세요.");
							break;
						}
						var arrObj = [];

						PAGE.POPUP1TOP.forEachRow(function(id){
							var arrData = PAGE.POPUP1TOP.getRowData(id);

							arrObj.push(arrData);
						});
						var arrData = PAGE.POPUP1TOP.getRowData();

						var sData = {};

						sData.items = arrObj;

						var userOptions = {timeout:180000};

						mvno.cmn.ajax4json(ROOT + "/org/prmtmgmt/regChrgPrmtInfoExcel.json", sData, function(result) {
							mvno.ui.closeWindowById("POPUP1", true);
							mvno.ui.notify("NCMN0004");
							PAGE.GRID1.refresh();
						}, userOptions);

					break;

					case "btnClose":
						mvno.ui.closeWindowById("POPUP1");
					break;
				}
			}
		}



		,FORM2 : {
			items : [
				{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 80, blockOffset : 0 }
				,{ type : "block", name: "INFO", labelWidth : 30 , list : [
					{type:"input", width:100, label:"프로모션명", name:"prmtId", width:110}
					, {type: 'newcolumn'}
					, {type:"input", width:200, label:"", name: "prmtNm", width:600}
				]}
				,{ type : "block", labelWidth : 30 , list : [
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'strtDt', label: '프로모션기간', calendarPosition: 'bottom', width:100 }
					, {type: 'newcolumn'}
					, {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'endDt', label: '~', calendarPosition: 'bottom', labelWidth:10, width:100, offsetLeft:5 }
					, {type: 'newcolumn'}
					, {type:"select", width:100, label:"구매유형", name:"reqBuyType", labelWidth:60, offsetLeft:30, required: true}
					, {type: 'newcolumn'}
					, {type:"select", width:100, label:"채널유형", name:"orgnType", labelWidth:60, offsetLeft:30, required: true}
				]}
				,{ type : "block", name: "JOIN", labelWidth : 30 , list : [
					{type: "label", label: "가입유형"}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "전체",  name: "joinTypeAll", width:20, labelWidth : 40, offsetLeft:10}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "신규",  name: "nacYn", width:20, labelWidth : 50, offsetLeft:20}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "번호이동",  name: "mnpYn", width:20, labelWidth : 50, offsetLeft:20}
				]}
				,{ type : "block", name: "ENGG", labelWidth : 30 , list : [
					{type: "label", label: "약정기간"}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "전체",  name: "enggCntAll", width:20, labelWidth : 40, offsetLeft:10}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "무약정",  name: "enggCnt0", width:20, labelWidth : 50, offsetLeft:20}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "12개월",  name: "enggCnt12", width:20, labelWidth : 50, offsetLeft:20}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "18개월",  name: "enggCnt18", width:20, labelWidth : 50, offsetLeft:20}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "24개월",  name: "enggCnt24", width:20, labelWidth : 50, offsetLeft:20}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "30개월",  name: "enggCnt30", width:20, labelWidth : 50, offsetLeft:20}
					, {type: "newcolumn"}
					, {type: "checkbox",label: "36개월",  name: "enggCnt36", width:20, labelWidth : 50, offsetLeft:20}
				]}
			]
			, onChange : function(name, value) {
				var form = this;
				
				switch(name){
					case "orgnType" :
						var data = {
							orgnType : value
						};
						PAGE.GRID2.list(ROOT + "/org/prmtmgmt/getChrgPrmtOrgnList.json", {orgnType : data.orgnType});
						
						
						break;
						
					case "joinTypeAll" :
						if(form.isItemChecked(name)){
							form.setItemValue("nacYn", 1);
							form.setItemValue("mnpYn", 1);
							
							form.disableItem("nacYn");
							form.disableItem("mnpYn");
						}else{
							form.setItemValue("nacYn", 0);
							form.setItemValue("mnpYn", 0);
							
							form.enableItem("nacYn");
							form.enableItem("mnpYn");
						}
						break;
					
					case "enggCntAll" :
						if(form.isItemChecked(name)){
							form.setItemValue("enggCnt0", 1);
							form.setItemValue("enggCnt12", 1);
							form.setItemValue("enggCnt18", 1);
							form.setItemValue("enggCnt24", 1);
							form.setItemValue("enggCnt30", 1);
							form.setItemValue("enggCnt36", 1);
							
							form.disableItem("enggCnt0");
							form.disableItem("enggCnt12");
							form.disableItem("enggCnt18");
							form.disableItem("enggCnt24");
							form.disableItem("enggCnt30");
							form.disableItem("enggCnt36");
						}else{
							form.setItemValue("enggCnt0", 0);
							form.setItemValue("enggCnt12", 0);
							form.setItemValue("enggCnt18", 0);
							form.setItemValue("enggCnt24", 0);
							form.setItemValue("enggCnt30", 0);
							form.setItemValue("enggCnt36", 0);
							
							form.enableItem("enggCnt0");
							form.enableItem("enggCnt12");
							form.enableItem("enggCnt18");
							form.enableItem("enggCnt24");
							form.enableItem("enggCnt30");
							form.enableItem("enggCnt36");
						}
						break;
				}
			}
		}	//FORM2 End
		
		, GRID2 : {
			css : {
				height : "200px"
				/* ,width : "310px" */
			}
			,title : "대상조직"
			,header : "선택,조직ID,조직명"
			,columnIds : "rowCheck,orgnId,orgnNm"
			,colAlign : "center,center,left"
			,colTypes : "ch,ro,ro"
			,colSorting : "str,str,str"
			,widths : "50,120,140"
			,paging: false
			//,multiCheckbox : true
			,hiddenColumns : [1]
		}	//GRID2 End
		
		, GRID3 : {
			css : {
				height : "200px"
				/* ,width : "290px" */
			}
			,title : "대상요금제"
			,header : "선택,요금제코드,요금제명,기본료"
			,columnIds : "rowCheck,rateCd,rateNm,baseAmt"
			,colAlign : "center,center,left,right"
			,colTypes : "ch,ro,ro,ron"
			,colSorting : "str,str,str,int"
			,widths : "50,120,150,70"
			,paging: false
			//,multiCheckbox : true
			,hiddenColumns : [1]
		}	//GRID3 End
		
		, GRID4 : {
			css : {
				height : "200px"
				/* ,width : "275px" */
			}
			,title : "대상부가서비스"
			,header : "선택,부가서비스명,기본료,부가서비스,중복여부"
			,columnIds : "rowCheck,vasNm,baseAmt,vasCd,dupYn"
			,colAlign : "center,left,right,center,center"
			,colTypes : "ch,coro,ron,ro,ro"
			,colSorting : "str,str,str,str,str"
			,widths : "50,140,60,10,10"
			,paging: false
			,hiddenColumns : [3,4]
			,buttons : {
				right : {
					btnDel : { label : "삭제" }
					,btnAdd : { label : "추가" }
					,btnReg : { label : "저장" }
				}
			}
			,onButtonClick : function(btnId) {
				
				var grid = this;
				
				switch(btnId){
					
					case "btnDel" :
						
						if(grid.getSelectedRowId() == null){
							mvno.ui.alert("삭제 하려는 부가서비스를 선택하세요.");
							return
						}
						
						grid.deleteRow(grid.getSelectedRowId());
					
						break;
				
					case "btnAdd" :
						
						grid.appendRow();
						
						break;
						
					case "btnReg" :
						
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("prmtNm"))){
							mvno.ui.alert("프로모션명을 입력하세요.");
							return;
						}
						
						var strtDtFM = new Date(PAGE.FORM2.getItemValue("strtDt")).format("yyyymmdd");
						var endDtFM = new Date(PAGE.FORM2.getItemValue("endDt")).format("yyyymmdd");
						
						if(strtDtFM > endDtFM) {
							mvno.ui.alert("종료일이 시작일 전입니다.\n종료일을 변경하세요.");
							return;
						}
						
						if(strtDtFM < today) {
							mvno.ui.alert("시작일은 오늘 이전 날짜를 선택할 수 없습니다.");
							return;
						}
						
						if(!PAGE.FORM2.isItemChecked("nacYn") && !PAGE.FORM2.isItemChecked("mnpYn")){
							mvno.ui.alert("가입유형을 선택해 주십시오.");
							return;
						}
						
						if(!PAGE.FORM2.isItemChecked("enggCnt0") && !PAGE.FORM2.isItemChecked("enggCnt12")
							&& !PAGE.FORM2.isItemChecked("enggCnt18") && !PAGE.FORM2.isItemChecked("enggCnt24") 
							&& !PAGE.FORM2.isItemChecked("enggCnt30") && !PAGE.FORM2.isItemChecked("enggCnt36")){
							mvno.ui.alert("약정기간을 선택해 주십시오.");
							return;
						}
						
						if(PAGE.GRID2.getCheckedRowData().length < 1){
							mvno.ui.alert("대상조직을 선택하세요.");
							return;
						}
						
						if(PAGE.GRID3.getCheckedRowData().length < 1){
							mvno.ui.alert("대상요금제를 선택하세요.");
							return;
						}
						
						if(PAGE.GRID4.getCheckedRowData().length < 1){
							mvno.ui.alert("대상부가서비스를 선택하세요.");
							return;
						}
						
						mvno.ui.confirm("프로모션을 저장하시겠습니까?", function() {
							// 조직
							var arrOrgn = [];
							
							var arrOrgnData = PAGE.GRID2.getCheckedRowData();
							
							for(var idx=0; idx<arrOrgnData.length; idx++) {
								arrOrgn.push(arrOrgnData[idx]);
							}
							
							// 요금제
							var arrRate = [];
							
							var arrRateData = PAGE.GRID3.getCheckedRowData();
							
							for(var idx=0; idx<arrRateData.length; idx++) {
								arrRate.push(arrRateData[idx]);
							}
							
							// 부가서비스
							var arrVas = [];
							
							var arrVasData = PAGE.GRID4.getCheckedRowData();
							
							for(var idx=0; idx<arrVasData.length; idx++) {
								arrVas.push(arrVasData[idx]);
							}
							
							// 모집경로
							var arrOnOffType = [];
							
							var arrOnOffData = PAGE.GRID6.getCheckedRowData();
							
							for(var idx=0; idx<arrOnOffData.length; idx++) {
								arrOnOffType.push(arrOnOffData[idx]);
							}
							
							var sData = {
									prmtNm : PAGE.FORM2.getItemValue("prmtNm")
									, strtDt : PAGE.FORM2.getItemValue("strtDt").format("yyyymmdd")
									, endDt : PAGE.FORM2.getItemValue("endDt").format("yyyymmdd")
									, orgnType : PAGE.FORM2.getItemValue("orgnType")
									, reqBuyType : PAGE.FORM2.getItemValue("reqBuyType")
									, nacYn : PAGE.FORM2.getItemValue("nacYn")
									, mnpYn : PAGE.FORM2.getItemValue("mnpYn")
									, enggCnt0 : PAGE.FORM2.getItemValue("enggCnt0")
									, enggCnt12 : PAGE.FORM2.getItemValue("enggCnt12")
									, enggCnt18 : PAGE.FORM2.getItemValue("enggCnt18")
									, enggCnt24 : PAGE.FORM2.getItemValue("enggCnt24")
									, enggCnt30 : PAGE.FORM2.getItemValue("enggCnt30")
									, enggCnt36 : PAGE.FORM2.getItemValue("enggCnt36")
							};
							
							// 조직
							sData.orgnList = arrOrgn;
							
							// 요금제
							sData.rateList = arrRate;
							
							// 부가서비스
							sData.vasList = arrVas;
							
							// 모집경로
							sData.onOffTypeList = arrOnOffType;
							
							mvno.cmn.ajax4json(ROOT + "/org/prmtmgmt/regChrgPrmtInfo.json", sData, function(result) {
								PAGE.FORM2.clearChanged();
								mvno.ui.closeWindowById("GROUP1");
								mvno.ui.notify("NCMN0004");
								PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
							});
						});
						
						break;
				}
			}
		}	//GRID4 End
		
		, GRID5 : {
			css : {
				height : "180px"
			}
			,title : "상세정보"
			,header : "조직ID,조직명,요금제코드,요금제명,부가서비스코드,부가서비스명,기본료,부가서비스순번,모집경로"
			,columnIds : "orgnId,orgnNm,rateCd,rateNm,soc,socNm,socAmt,seq,onOffTypeNm"
			,colAlign : "center,left,center,left,center,left,right,center,left"
			,colTypes : "ro,ro,ro,ro,ro,ro,ron,ro,ro"
			,colSorting : "str,str,str,str,str,str,str,str,str"
			,widths : "90,180,100,180,100,150,80,100,120"
			,paging: false
			,hiddenColumns : [7]
			,buttons : {
				right : {
					btnEndDt : { label : "종료일변경" }
					,btnEnd : { label : "삭제" }
				}
				,center : {
					btnClose : { label : "닫기" }
				}
			}
			,onButtonClick : function(btnId) {
				
				var grid = this;
				
				switch(btnId){
					
					case "btnEndDt" :
						
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("prmtId"))){
							mvno.ui.alert("대상 프로모션이 존재하지 않습니다.");
							return;
						}
						
						var strtDtFM = new Date(PAGE.FORM2.getItemValue("strtDt")).format("yyyymmdd");
						var endDtFM = new Date(PAGE.FORM2.getItemValue("endDt")).format("yyyymmdd");
						
						if(strtDtFM > endDtFM) {
							mvno.ui.alert("종료일이 시작일 전입니다. 종료일을 변경하세요.");
							return;
						}
						
						if(endDtFM < today) {
							mvno.ui.alert("종료일을 오늘 이전 날짜로 변경 할 수 없습니다.");
							return;
						}
						
						var sData = {
								prmtId : PAGE.FORM2.getItemValue("prmtId")
								,endDt : endDtFM
								,chngTypeCd : 'U'
						};
						
						mvno.cmn.ajax4confirm("해당 프로모션의 종료일을 변경 하시겠습니까?", ROOT + "/org/prmtmgmt/updChrgPrmtByInfo.json", sData, function(result) {
							PAGE.FORM2.clearChanged();
							mvno.ui.closeWindowById("GROUP1");
							mvno.ui.notify("NCMN0002");
							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
						});
						
						break;
						
					case "btnEnd" :
						
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("prmtId"))){
							mvno.ui.alert("대상 프로모션이 존재하지 않습니다.");
							return;
						}
						
						var sData = {
								prmtId : PAGE.FORM2.getItemValue("prmtId")
								,usgYn : 'N'
								,chngTypeCd : 'D'
						};
						
						mvno.cmn.ajax4confirm("해당 프로모션을 삭제 하시겠습니까?", ROOT + "/org/prmtmgmt/updChrgPrmtByInfo.json", sData, function(result) {
							PAGE.FORM2.clearChanged();
							mvno.ui.closeWindowById("GROUP1");
							mvno.ui.notify("NCMN0003");
							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
						});
						
						break;
						
					case "btnClose" :
						mvno.ui.closeWindowById("GROUP1");
						break;
				}
			}
		}	//GRID5 End
		, GRID6 : {
			css : {
				height : "200px"
				/* ,width : "290px" */
			}
			,title : "모집경로"
			,header : "선택,모집경로,모집경로"
			,columnIds : "rowCheck,onOffType,onOffTypeNm"
			,colAlign : "center,center,left"
			,colTypes : "ch,ro,ro"
			,colSorting : "str,str,str"
			,widths : "50,50,150"
			,paging: false
			//,multiCheckbox : true
			,hiddenColumns : [1]
		}	//GRID6 End

		,FORM20 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},	
				{type:"block", list:[
				,{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'endDtMod', label: '종료일자', calendarPosition: 'bottom' ,width:100} 
				]},
				, {type:"newcolumn", offset:20}
				, {type :"button",name : "btnEndDtMod",value : "종료일시변경" } 
			]
			,onButtonClick : function(btnId) {
				var chkData = PAGE.GRID1.getCheckedRowData();
				
				switch (btnId) {
					case "btnEndDtMod":

						// 입력 날짜 유효성 검사
						var endDtMod = new Date(PAGE.FORM20.getItemValue("endDtMod")).format("yyyymmdd");

						if(endDtMod == null || endDtMod== ''){
							mvno.ui.alert("종료일자를 입력해주세요");
							return;
						}
										
						var prmtIdList = [];
						var now = new Date().format("yyyymmdd");

						for(var i = 0; i < chkData.length; i++){
							var strtDt = chkData[i].strtDt;
							var chkDataPrmtId = chkData[i].prmtId;

							if(endDtMod < strtDt){
								mvno.ui.alert( "프로모션 ID ["+ chkDataPrmtId +"] 은 종료일이 시작일 전입니다. 종료일을 변경하세요.");
								return;
							}
							
							if(endDtMod < now){
								mvno.ui.alert( "프로모션 ID ["+ chkDataPrmtId +"] 은 이미 종료된 프로모션으로 종료일을 변경할수 없습니다.");
								return;
							}
							
							prmtIdList.push(chkDataPrmtId);
						};

						var sData = {
								endDt : endDtMod
								,prmtIdList : prmtIdList
								,chngTypeCd : 'U'
						};

						mvno.ui.confirm("선택한 프로모션의 종료일시를 변경 하시겠습니까?",function() {
							mvno.cmn.ajax4json(ROOT + "/org/prmtmgmt/updPrmtEndDttm.json", sData, function(result) {
								if(result.result.code == "OK"){
									PAGE.GRID1.refresh();
									mvno.ui.notify("NCMN0002");
									mvno.ui.alert("NCMN0002");
									mvno.ui.closeWindowById("FORM20");
								}
							});
						})
				   break;
				}
			}
		}

		,FORM3 : { 
			items : [
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},	
						{type:"block", list:[
							{type : "input",label : "조직명",name : "orgnNm", width:100, offsetLeft:10}, 
						]},
						{type:"newcolumn", offset:0},
						{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],
			onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
				
				case "btnSearch":
						  						
					  PAGE.GRID7.list(ROOT + "/org/prmtmgmt/getChrgPrmtOrgnList.json", form);
						
				break;
				}
			}
		},
		GRID7 : {
			css : {
				height : "300px",
				width : "520px"
			},
			title : "조회결과",
			header : "선택,조직ID,조직명,조직유형,조직상태",
			columnIds : "rowCheck,orgnId,orgnNm,typeNm,statNm",
			colAlign : "center,left,left,left,center",
			colTypes : "ch,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str",
			widths : "50,100,170,100,80",
              buttons : {
				  center : {
				   btnApply : { label : "확인" }
				   ,btnClose : { label: "닫기" }
				  }
              }
			  ,onRowSelect : function(rowId){
				var cell = this.cells(rowId,0);
				if(!cell.isChecked()) cell.setValue(1);
				else cell.setValue(0);
			  }
              ,onButtonClick : function(btnId) {
                  
            	  var grid = this;    
            	  var chkData=grid.getCheckedRowData();
           
                  switch (btnId) {
                
                  case "btnApply":  	 
                	
                 	    if(chkData.length == 0){
  	      					mvno.ui.alert("ECMN0003");
  	      					return;
  	      			    }
                 	    
                 	    var prmtIdList =[];
					    var now = new Date().format("yyyymmddhhmiss");

                 	  	for(var i = 0; i < PAGE.GRID1.getCheckedRowData().length; i++){
                 	  		var endDttm =  PAGE.GRID1.getCheckedRowData()[i].endDt;
                 	  		if(endDttm < now){
    							mvno.ui.alert( "프로모션 ID ["+  PAGE.GRID1.getCheckedRowData()[i].prmtId +"] 은 이미 종료된 프로모션으로 대리점을 추가 할 수 없습니다.");
    							return;
    						}
                 	  		prmtIdList.push(PAGE.GRID1.getCheckedRowData()[i].prmtId);
                 	  	}

                 	    var orgnList = [];
                 	    
                 	  	for(var j = 0; j < chkData.length; j++) {
                 	  		orgnList.push(chkData[j].orgnId);
						}
                	  	var sData = {
                	  			prmtIdList : prmtIdList
                	  			,orgnList : orgnList
						};	
						
						mvno.ui.confirm("선택한 프로모션의 대리점을 추가 하시겠습니까?",function() {
	                		mvno.cmn.ajax4json(ROOT + "/org/prmtMgmt/setChrgPrmtOrgAdd.json", sData, function(result) {
								if(result.result.code == "OK"){
									mvno.ui.notify("NCMN0006");
									mvno.ui.alert("NCMN0006");
									mvno.ui.closeWindowById("GROUP2");
								};
							});
						});
	                	
            	  break; 
                  
                  case "btnClose" :
                         
                   	    mvno.ui.closeWindowById("GROUP2");
                   	  
                  break;  
            	}
      		}
		}
	}
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		buttonAuth: ${buttonAuth.jsonString},
		onOpen:function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			PAGE.FORM1.setItemValue("searchBaseDate", today);
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0055", etc3:"Y"}, PAGE.FORM1, "orgnType");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9022"}, PAGE.FORM1, "reqBuyType");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM1, "onOffType");
		}
	};
</script>