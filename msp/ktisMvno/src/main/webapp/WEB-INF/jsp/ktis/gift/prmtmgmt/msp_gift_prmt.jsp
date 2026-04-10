<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_gift_prmt.jsp
	 * @Description : 사은품프로모션관리
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

 <div id="GROUP1" >   <!--style="display:none;" -->
	<div id="FORM2" class="section-box"></div>
	<div class="row">
		<!--대상조직-->
		<div id="GRID2" class="c3"></div>
		<!--대상요금제-->
		<div id="GRID3" class="c4"></div>
		<!--모집경로-->
		<div id="GRID6" class="c3"></div>
	</div>
	<div class="row">
		<!--대상제품-->
		<div id="GRID7" class="c5"></div>
		<!--사은품-->
		<div id="GRID4" class="c5"></div>
	</div>
	<div id="FORM3"></div>
</div>
<div id="POPUP1">
	<div id="POPUP1MID" class="section-box"></div>
</div>

<div id="POPUP2">
	<div id="POPUP2MID" class="section-box"></div>
</div>


<!-- Script -->
<script type="text/javascript">
	var authYn = "${authYn}"; // 노출여부 권한 조회 N: 비노출, Y:노출
	var today = new Date().format("yyyymmdd");
	var startDt = mvno.cmn.getAddDay(today, 1);
	var endDt = mvno.cmn.getAddDay(today, 30);
	var fileName;
	
	var vasRowsData
	
	var DHX = {
		// 검색
		FORM1 : { 
			items : [ 
				{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 70, blockOffset : 0 }
				,{ type : "block", list : [
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchBaseDate', value: "", label: '시작기준일자', calendarPosition: 'bottom', readonly:true, width:95 , labelWidth : 75, offsetLeft:10 }
					, {type: 'newcolumn'}
					, {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchBaseEndDate', value: "",label: '~', calendarPosition: 'bottom', readonly:true , labelWidth:10, width:95, offsetLeft:5 }					
					, {type: "newcolumn"}
					, {type:"input", width:445, label:"프로모션명", name: "prmtNm", offsetLeft:14}
				]}
				,{ type: "block", list : [
					{type:"select", width:95, label:"채널유형", name:"orgnType", labelWidth : 75, offsetLeft:10}
					, {type: "newcolumn", offset:35}
					, {type:"select", width:90, label:"구매유형", name:"reqBuyType", offsetLeft:95}
					, {type: "newcolumn", offset:5}
					, {type:"select", width:120, label:"모집경로", name:"onOffType", labelWidth : 55, offsetLeft:10}
					, {type: "newcolumn", offset:5}
					, {type:"select", width:83, label:"노출여부", name:"showYn", options:[{value:"", text:"- 전체 -", selected:true},{value:"Y", text:"노출"},{value:"N", text:"비노출"}], labelWidth : 55, offsetLeft:10}
					, {type: "newcolumn", offset:5}
					//버튼 여러번 클릭 막기 변수
					, {type : 'hidden', name: "btnCount1", value:"0"} , {type: "newcolumn", offset:5}
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}
				]}
				,{ type : "button",name : "btnSearch",value : "조회", className:"btn-search2" } 
			]
			,onButtonClick : function(btnId) {
				
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						var url1 = ROOT + "/gift/prmtmgmt/getGiftPrmtList.json";

						PAGE.GRID1.list(url1, form, {callback : function() {
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						}});
						
					break;
				}
			}
		}	//FORM1 End
		
		, GRID1 : {
			css : { 
				height : "520px"  
			}
			,title : "프로모션목록"
			,header : "프로모션ID,프로모션명,노출여부,노출여부,시작일자,종료일자,프로모션 유형,채널유형,구매유형,총금액 제한,신규,번호이동,약정기간_무약정,약정기간_12개월,약정기간_18개월,약정기간_24개월,약정기간_30개월,약정기간_36개월,노출문구,등록자,등록일시,수정자,수정일시,wirelessYn"
			,columnIds : "prmtId,prmtNm,showYn,showYnNm,strtDt,endDt,prmtTypeNm,orgnTypeNm,reqBuyTypeNm,amountLimit,nacYn,mnpYn,enggCnt0,enggCnt12,enggCnt18,enggCnt24,enggCnt30,enggCnt36,prmtText,regstNm,regstDttm,rvisnNm,rvisnDttm,wirelessYn"
			,widths : "110,250,50,55,100,100,90,100,110,80,80,80,110,110,110,110,110,110,110,80,140,80,140,0"
			,colAlign : "Center,Left,Center,Center,Center,Center,Center,Center,Center,Right,Center,Center,Center,Center,Center,Center,Center,Center,Left,Center,Center,Center,Center,Center"
			,colTypes : "ro,ro,ro,ro,roXd,roXd,ro,ro,ro,ron,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXdt,ro,roXdt,ro"
			,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			,hiddenColumns : "2,23"
			,paging : true
			,pageSize : 20
			//,showPagingAreaOnInit : true
			,buttons : {
				left : {
					btnChrgPrmtCopy : { label : "프로모션복사" }
				},
				right : {
					btnReg : { label : "등록" }
					,btnMod : { label : "상세조회" }  
				},
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				}
			}
			,onRowDblClicked : function(rowId, celInd) {
				// 수정버튼 누른것과 같이 처리
				this.callEvent("onButtonClick", ["btnMod"]);
			}
			,onButtonClick : function(btnId, selectedData) {
				switch (btnId) {

					case "btnChrgPrmtCopy" :
						
						var data = PAGE.GRID1.getSelectedRowData();
						
						if(data == null) {
							mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
							return;
						}
						//본인 권한과 복사할 노출여부 내용이 상이함
						if(authYn != data.showYn){
							mvno.ui.alert("노출 여부 권한이 상이하여 복사 할수 없습니다.");
							return;
						}
						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID2");	// 조직
						mvno.ui.createGrid("GRID3");	// 요금제
						mvno.ui.createGrid("GRID6");	// 모집경로
						mvno.ui.createGrid("GRID7");	// 대상제품
						mvno.ui.createGrid("GRID4");	// 사은품제품
						mvno.ui.createForm("FORM3");	// BUTTONS
						
						PAGE.GRID2.clearAll();
						PAGE.GRID3.clearAll();
						PAGE.GRID6.clearAll();
						PAGE.GRID7.clearAll();
						PAGE.GRID4.clearAll();
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9022"}, PAGE.FORM2, "reqBuyType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0055"}, PAGE.FORM2, "orgnType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0268"}, PAGE.FORM2, "prmtOrgnId");
						
						PAGE.GRID2.list(ROOT + "/gift/prmtmgmt/getGiftPrmtOrgnList.json", {orgnType:data.orgnType, prmtId:data.prmtId});
						PAGE.GRID3.list(ROOT + "/gift/prmtmgmt/getGiftPrmtSocList.json", {prmtId:data.prmtId});
						PAGE.GRID6.list(ROOT + "/gift/prmtmgmt/getGiftPrmtOnoffList.json", {prmtId:data.prmtId});
						PAGE.GRID7.list(ROOT + "/gift/prmtmgmt/getGiftPrmtModelList.json", {reqBuyType:data.reqBuyType, prmtId:data.prmtId});
						PAGE.GRID4.list(ROOT + "/gift/prmtmgmt/getGiftPrmtPrdtList.json", {prmtId:data.prmtId});
				
						
						var fData = {
							prmtNm : data.prmtNm + " 복사본"
							,reqBuyType : data.reqBuyType
							,orgnType : data.orgnType
							,amountLimit : data.amountLimit
							,nacYn : (data.nacYn == 'Y') ? 1 : 0
							,mnpYn : (data.mnpYn == 'Y') ? 1 : 0
							,enggCnt0 : (data.enggCnt0 == 'Y') ? 1 : 0
							,enggCnt12 : (data.enggCnt12 == 'Y') ? 1 : 0
							,enggCnt18 : (data.enggCnt18 == 'Y') ? 1 : 0
							,enggCnt24 : (data.enggCnt24 == 'Y') ? 1 : 0
							,enggCnt30 : (data.enggCnt30 == 'Y') ? 1 : 0
							,enggCnt36 : (data.enggCnt36 == 'Y') ? 1 : 0
							,prmtText : data.prmtText
							,prmtType : data.prmtType
							,choiceLimit : data.choiceLimit
							,showYn : data.showYn // 권한 불일치 시  
							,billDt : data.billDt
							,prmtOrgnId : data.prmtOrgnId
							,wirelessYn : data.wirelessYn
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
										if(vasRowsData.rows[nValue].prdtId == rowData.prdtId){
											grid.cells(rId, 1).setValue("");
											grid.cells(rId, 2).setValue("");
											grid.cells(rId, 3).setValue("");
											grid.cells(rId, 4).setValue("");
											mvno.ui.alert("중복 선택이 불가능한 사은품입니다.");
											bDupChk = true;
										}
									}
								});
								
							}
							
							if(!bDupChk){
								grid.cells(rId, 2).setValue(vasRowsData.rows[nValue].outUnitPric);
								grid.cells(rId, 3).setValue(vasRowsData.rows[nValue].prdtId);
								grid.cells(rId, 4).setValue(vasRowsData.rows[nValue].dupYn);
								grid.cells(rId, 5).setValue(vasRowsData.rows[nValue].sort);
							}
						});
						
						PAGE.FORM2.disableItem("prmtId");
						
						PAGE.FORM2.setItemValue("strtDt", startDt);
						PAGE.FORM2.setItemValue("endDt", endDt);
						
						PAGE.FORM2.enableItem("INFO");
						PAGE.FORM2.enableItem("JOIN");
						PAGE.FORM2.enableItem("DISPLAY");
						PAGE.FORM2.enableItem("ENGG");
						PAGE.FORM2.enableItem("strtDt");
						PAGE.FORM2.enableItem("reqBuyType");
						PAGE.FORM2.enableItem("orgnType");
						PAGE.FORM2.enableItem("prmtTypeGroup");
						if (PAGE.FORM2.getItemValue("prmtType") == 'C') {
							PAGE.FORM2.enableItem("choiceLimit");	
						} else {
							PAGE.FORM2.disableItem("choiceLimit");
						}

						PAGE.FORM2.enableItem("amountLimit");
						
						PAGE.GRID2.setEditable(true);
						PAGE.GRID3.setEditable(true);
						PAGE.GRID6.setEditable(true);
						PAGE.GRID7.setEditable(true);
						PAGE.GRID4.setEditable(true);
						
						PAGE.FORM2.enableItem("nacYn");
						PAGE.FORM2.enableItem("mnpYn");
						PAGE.FORM2.enableItem("enggCnt0");
						PAGE.FORM2.enableItem("enggCnt12");
						PAGE.FORM2.enableItem("enggCnt18");
						PAGE.FORM2.enableItem("enggCnt24");
						PAGE.FORM2.enableItem("enggCnt30");
						PAGE.FORM2.enableItem("enggCnt36");
						PAGE.FORM2.enableItem("prmtText");
						
						mvno.ui.showButton("GRID4" , 'btnDel');
						mvno.ui.showButton("GRID4" , 'btnAdd');
						mvno.ui.showButton("GRID4" , 'btnReg');
						
						mvno.ui.hideButton("GRID4" , 'btnMod');
						
						mvno.ui.hideButton("FORM3" , 'btnEndDt');
						
						mvno.ui.hideButton("FORM3" , 'btnText');
						mvno.ui.hideButton("FORM3" , 'btnEnd');
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "사은품프로모션복사", 980, 750, {
							onClose: function(win) {
								if (!PAGE.FORM2.isChanged()) {
									return true;
								}
								mvno.ui.confirm("CCMN0005", function() {
									win.closeForce();
								});
							}
						});
						
						break;
					
					case "btnReg":
						
						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID2");	// 조직
						mvno.ui.createGrid("GRID3");	// 요금제
						mvno.ui.createGrid("GRID6");	// 모집경로
						mvno.ui.createGrid("GRID7");	// 대상제품
						mvno.ui.createGrid("GRID4");	// 사은품제품
						mvno.ui.createForm("FORM3");	// buttons
						
						PAGE.GRID2.clearAll();
						PAGE.GRID3.clearAll();
						PAGE.GRID6.clearAll();
						PAGE.GRID7.clearAll();
						PAGE.GRID4.clearAll();
						
						PAGE.FORM2.setFormData(true);
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9022"}, PAGE.FORM2, "reqBuyType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0055"}, PAGE.FORM2, "orgnType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0268"}, PAGE.FORM2, "prmtOrgnId");	
						//N : 비노출 권한 있음, Y : 노출
						if(authYn == "Y"){
							PAGE.FORM2.setItemValue("showYn", "Y");
						}else{
							PAGE.FORM2.setItemValue("showYn", "N");
						}
						
						PAGE.GRID2.list(ROOT + "/gift/prmtmgmt/getGiftPrmtOrgnList.json", "");
						PAGE.GRID3.list(ROOT + "/gift/prmtmgmt/getGiftPrmtSocList.json", "");
						PAGE.GRID6.list(ROOT + "/gift/prmtmgmt/getGiftPrmtOnoffList.json", "");
						PAGE.GRID7.list(ROOT + "/gift/prmtmgmt/getGiftPrmtModelList.json", {reqBuyType:"UU"});
						
						mvno.cmn.ajax(ROOT + "/gift/prmtmgmt/getGiftPrmtPrdtList.json", "", function(resultData) {
							vasRowsData = resultData.result.data;
							var totalCount = Number(resultData.result.data.total);
							
							var combobox = PAGE.GRID4.getCombo(1);
							
							if(totalCount > 0){
								for(var idx = 0; idx < totalCount; idx++){
									var prdtNm = vasRowsData.rows[idx].prdtNm;
									var prdtId = vasRowsData.rows[idx].prdtId;
									
									combobox.put(idx, prdtNm);
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
										if(vasRowsData.rows[nValue].prdtId == rowData.prdtId){
											grid.cells(rId, 1).setValue("");
											grid.cells(rId, 2).setValue("");
											grid.cells(rId, 3).setValue("");
											grid.cells(rId, 4).setValue("");
											grid.cells(rId, 5).setValue(0);
											mvno.ui.alert("중복 선택이 불가능한 사은품입니다.");
											bDupChk = true;
										}
									}
								});
								
							}
							
							if(!bDupChk){
								grid.cells(rId, 2).setValue(vasRowsData.rows[nValue].outUnitPric);
								grid.cells(rId, 3).setValue(vasRowsData.rows[nValue].prdtId);
								grid.cells(rId, 4).setValue(vasRowsData.rows[nValue].dupYn);
								grid.cells(rId, 5).setValue(vasRowsData.rows[nValue].sort);
							}
						});
						
						
						PAGE.FORM2.disableItem("prmtId");
						PAGE.FORM2.setItemValue("reqBuyType", "UU");
						PAGE.FORM2.setItemValue("strtDt", startDt);
						PAGE.FORM2.setItemValue("endDt", endDt);
						PAGE.FORM2.setItemValue("billDt", endDt); 
						
						PAGE.FORM2.enableItem("INFO");
						PAGE.FORM2.enableItem("JOIN");
						PAGE.FORM2.enableItem("DISPLAY");						
						PAGE.FORM2.enableItem("ENGG");
						PAGE.FORM2.enableItem("strtDt");
						PAGE.FORM2.enableItem("reqBuyType");
						PAGE.FORM2.enableItem("orgnType");
						PAGE.FORM2.enableItem("prmtTypeGroup");
						PAGE.FORM2.disableItem("choiceLimit");
						
						PAGE.FORM2.enableItem("amountLimit");
						
						PAGE.GRID2.setEditable(true);
						PAGE.GRID3.setEditable(true);
						PAGE.GRID6.setEditable(true);
						PAGE.GRID7.setEditable(true);
						PAGE.GRID4.setEditable(true);
						
						PAGE.FORM2.enableItem("nacYn");
						PAGE.FORM2.enableItem("mnpYn");
						PAGE.FORM2.enableItem("enggCnt0");
						PAGE.FORM2.enableItem("enggCnt12");
						PAGE.FORM2.enableItem("enggCnt18");
						PAGE.FORM2.enableItem("enggCnt24");
						PAGE.FORM2.enableItem("enggCnt30");
						PAGE.FORM2.enableItem("enggCnt36");
						PAGE.FORM2.enableItem("prmtText");
						
						mvno.ui.showButton("GRID4" , 'btnDel');
						mvno.ui.showButton("GRID4" , 'btnAdd');
						mvno.ui.showButton("GRID4" , 'btnReg');
						
						mvno.ui.showButton("GRID3" , 'btnUpExcel');
						mvno.ui.showButton("GRID7" , 'btnUpExcel');
						
						mvno.ui.hideButton("GRID4" , 'btnMod');
						
						mvno.ui.hideButton("FORM3" , 'btnEndDt');

						mvno.ui.hideButton("FORM3" , 'btnText');
						
						mvno.ui.hideButton("FORM3" , 'btnEnd');
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "사은품프로모션등록", 980, 750, { //750
							onClose: function(win) {
								if (!PAGE.FORM2.isChanged()) {
									return true;
								}
								mvno.ui.confirm("CCMN0005", function() {
									win.closeForce();
								});
							}
						});
						
						break;
						
					case "btnMod":
						
						var data = PAGE.GRID1.getSelectedRowData();
						
						if(data == null) {
							mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
							return;
						}
						
						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID2");
						mvno.ui.createGrid("GRID3");
						mvno.ui.createGrid("GRID6");
						mvno.ui.createGrid("GRID7");
						mvno.ui.createGrid("GRID4");
						mvno.ui.createForm("FORM3");
						
						PAGE.GRID2.clearAll();
						PAGE.GRID3.clearAll();
						PAGE.GRID6.clearAll();
						PAGE.GRID7.clearAll();
						PAGE.GRID4.clearAll();
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9022"}, PAGE.FORM2, "reqBuyType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0055"}, PAGE.FORM2, "orgnType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0268"}, PAGE.FORM2, "prmtOrgnId");	
						
						PAGE.GRID2.list(ROOT + "/gift/prmtmgmt/getGiftPrmtOrgnList.json", {orgnType:data.orgnType, prmtId:data.prmtId});
						PAGE.GRID3.list(ROOT + "/gift/prmtmgmt/getGiftPrmtSocList.json", {prmtId:data.prmtId});
						PAGE.GRID6.list(ROOT + "/gift/prmtmgmt/getGiftPrmtOnoffList.json", {prmtId:data.prmtId});
						PAGE.GRID7.list(ROOT + "/gift/prmtmgmt/getGiftPrmtModelList.json", {reqBuyType:data.reqBuyType, prmtId:data.prmtId});
						PAGE.GRID4.list(ROOT + "/gift/prmtmgmt/getGiftPrmtPrdtList.json", {prmtId:data.prmtId});
						
						var fData = {
							prmtId : data.prmtId
							,prmtNm : data.prmtNm
							,reqBuyType : data.reqBuyType
							,orgnType : data.orgnType
							,amountLimit : data.amountLimit
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
							,prmtText : data.prmtText
							,prmtType : data.prmtType
							,choiceLimit : data.choiceLimit
							,showYn : data.showYn
							,billDt : data.billDt
							,prmtOrgnId : data.prmtOrgnId
							,wirelessYn : data.wirelessYn
						};
						
						
						PAGE.FORM2.setFormData(fData, true);
						
						PAGE.FORM2.disableItem("INFO");
						PAGE.FORM2.disableItem("JOIN");
						PAGE.FORM2.disableItem("DISPLAY");
						PAGE.FORM2.disableItem("ENGG");
						PAGE.FORM2.disableItem("strtDt");
						PAGE.FORM2.disableItem("reqBuyType");
						PAGE.FORM2.disableItem("orgnType");
						PAGE.FORM2.disableItem("amountLimit");
						PAGE.FORM2.disableItem("prmtTypeGroup");

						// 기존 데이터의 경우 checkbox 선택되어있지 않도록 하기 위해서
						if (data.prmtType == null) {
							PAGE.FORM2.setItemValue("prmtType", 0);
						}
						
						PAGE.FORM2.enableItem("prmtText");
						
						PAGE.GRID2.setEditable(false);
						PAGE.GRID3.setEditable(false);
						PAGE.GRID6.setEditable(false);
						PAGE.GRID7.setEditable(false);
						PAGE.GRID4.setEditable(false);
						/*
						mvno.ui.hideButton("GRID4" , 'btnDel');
						mvno.ui.hideButton("GRID4" , 'btnAdd');
						mvno.ui.hideButton("GRID4" , 'btnReg');
						
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
						*/
						mvno.ui.hideButton("GRID4" , 'btnDel');
						mvno.ui.hideButton("GRID4" , 'btnAdd');
						mvno.ui.hideButton("GRID4" , 'btnReg');
						
						mvno.ui.hideButton("GRID4" , 'btnMod');
						
						mvno.ui.hideButton("GRID3" , 'btnUpExcel');
						mvno.ui.hideButton("GRID7" , 'btnUpExcel');
						
						mvno.ui.showButton("FORM3" , 'btnEndDt');

						mvno.ui.showButton("FORM3" , 'btnText');
						mvno.ui.showButton("FORM3" , 'btnEnd');
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "사은품프로모션 상세조회", 980, 750, {
							onClose: function(win) {
								if (!PAGE.FORM2.isChanged()) {
									return true;
								}
								mvno.ui.confirm("CCMN0005", function() {
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

						debugger;	
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download(ROOT + "/gift/prdtMng/getGiftPrmtListEx.json?menuId=GIFT100021", true, {postData:searchData});

						break;		
				}
			}
		}	//GRID1 End
		
		,FORM2 : {
			items : [
				{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 80, blockOffset : 0 }
				,{ type : "block", name: "INFO", labelWidth : 30 , list : [
					{type:"input", label:"프로모션명", name:"prmtId", width:110}
					, {type: 'newcolumn'}
					, {type:"input", label:"", name: "prmtNm", width:680}
				]}
				,{ type : "block", labelWidth : 30 , list : [
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'strtDt', label: '프로모션기간', calendarPosition: 'bottom', readonly:true, width:100 }
					, {type: 'newcolumn'}
					, {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'endDt', label: '~', calendarPosition: 'bottom', readonly:true , labelWidth:10, width:100, offsetLeft:5 }
					, {type: 'newcolumn'}
					, {type:"select", width:100, label:"구매유형", name:"reqBuyType", labelWidth:60, offsetLeft:25, required: true}
					, {type: 'newcolumn'}
					, {type:"select", width:100, label:"채널유형", name:"orgnType", labelWidth:60, offsetLeft:25, required: true}
					, {type: 'newcolumn'}
					, {type:"input", width:95, label:"총금액 제한", name:"amountLimit", labelWidth:80, numberFormat:"0,000,000,000", validate:"ValidInteger", maxLength:10, offsetLeft:12/* , required: true */}
				]}
				,{ type : "block", name: "DISPLAY", labelWidth : 30 , list : [

					{type:"select", width:100, label:"노출여부", name:"showYn", labelWidth:80, options:[{value:"Y", text:"노출", selected:true},{value:"N", text:"비노출"}], required: true, disabled:true}
					, {type: 'newcolumn'}
					, {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'billDt', label: '정산기준일', calendarPosition: 'bottom', readonly:true ,required: true,width:100,labelWidth:76, offsetLeft:130 }
					, {type: 'newcolumn'}
					, {type: "select",label: "주관부서",  name: "prmtOrgnId", width:100, labelWidth : 60, offsetLeft:25, required: true}
					, {type: 'newcolumn'}
					, {type: "select",label: "유무선정책",  name: "wirelessYn", width:95, labelWidth : 80, offsetLeft:12, required: true, options:[{value:"Y", text:"무선프로모션", selected:true},{value:"N", text:"유선프로모션"}]}
				]},
				{ type : "block", name: "JOIN", labelWidth : 30 , list : [
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
				,{ type : "block", labelWidth : 30 , list : [
					{type:"input", label:"노출문구", name: "prmtText", width:800, maxLength:250}
				]}
				,{ type : "block", name: "prmtTypeGroup", labelWidth : 30 , list : [
					{type: "label", label: "프로모션 유형"}
					, {type: "newcolumn"}
					, {type: "radio", label: "기본 사은품",  name: "prmtType", value: "D", width:20, labelWidth : 70, offsetLeft:10}
					, {type: "newcolumn"}
					, {type: "radio", label: "선택 사은품",  name: "prmtType", value: "C", width:20, labelWidth : 70, offsetLeft:10}
					, {type: "newcolumn"}
					, {type: "input", width:80, label:"총 선택 개수 제한", name:"choiceLimit", labelWidth:100, validate:"ValidInteger", maxLength:4, offsetLeft:30, disabled: true}
				]}
			]
			, onChange : function(name, value) {
				var form = this;
				
				switch(name){
					case "orgnType" :
						var data = {
							orgnType : value,
							prmtId : PAGE.FORM2.getItemValue("prmtId")
						};
						PAGE.GRID2.list(ROOT + "/gift/prmtmgmt/getGiftPrmtOrgnList.json", data);						
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
					case "prmtType":
						if ("C" == value) {
							form.enableItem("choiceLimit");
						} else if ("D" == value){
							form.setItemValue("choiceLimit", null);
							form.disableItem("choiceLimit");
						}
						break;
					case "reqBuyType" :
						var data = {
							reqBuyType : value,
							prmtId : PAGE.FORM2.getItemValue("prmtId")
						};
						PAGE.GRID7.list(ROOT + "/gift/prmtmgmt/getGiftPrmtModelList.json", data);
						break;
						
				}
			}

			,onValidateMore : function (data){
				
			},			
		
		}	//FORM2 End
		
		, GRID2 : {
			css : {
				height : "200px"
				,width : "270px" 
			}
			,title : "대상조직"
			,header : "선택,조직ID,조직명"
			,columnIds : "rowCheck,orgnId,orgnNm"
			,colAlign : "center,center,left"
			,colTypes : "ch,ro,ro"
			,colSorting : "str,str,str"
			,widths : "50,0,220"
			,paging: false
			//,multiCheckbox : true
			,hiddenColumns : [1]
		}	//GRID2 End
		
		, GRID3 : {
			css : {
				height : "200px"
				,width : "340px"
			}
			,title : "대상요금제"
			,header : "선택,요금제코드,요금제명,기본료"
			,columnIds : "rowCheck,rateCd,rateNm,baseAmt"
			,colAlign : "center,center,left,right"
			,colTypes : "ch,ro,ro,ron"
			,colSorting : "str,str,str,int"
			,widths : "50,0,205,70"
			,paging: false
			//,multiCheckbox : true
			,hiddenColumns : [1]
			,buttons : {
				hright : {
					btnUpExcel : {
						label : "엑셀업로드"
					}
				}
			},
			onButtonClick : function(btnId, selectedData) {
				var form = this;
				switch (btnId) {
					
					case "btnUpExcel":
						
						
						mvno.ui.createForm("POPUP1MID");
						
						PAGE.POPUP1MID.clear();
						PAGE.POPUP1MID.setFormData({});
						
						//var fileName;
						
						/*
						PAGE.POPUP1MID.attachEvent("onUploadFile", function(realName, serverName) {
							fileName = serverName;
						});
						
						PAGE.POPUP1MID.attachEvent("onUploadComplete", function(count){
							
							var url = ROOT + "/gift/prmtmgmt/getExcelUpSocList.json";
							var userOptions = {timeout:180000};
							
							mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
								alert(2222);
								var rData = resultData.result;
								mvno.ui.closeWindowById("POPUP1");
 								PAGE.GRID3.clearAll();
								PAGE.GRID3.parse(rData.data.rows, "js");
								mvno.ui.alert(rData.msg);
								
							}, userOptions);
								
						});
						*/
						
/* 						PAGE.POPUP1MID.attachEvent("onFileRemove",function(realName, serverName){
							//PAGE.POPUP1TOP.clearAll();
							
							return true;
						});
						 */
						var uploader = PAGE.POPUP1MID.getUploader("file_upload1");
						uploader.revive();
												
						mvno.ui.popupWindowById("POPUP1", "사은품프로모션 대상요금제 선택", 405, 200, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
											
						break;
				}
			}
		}	//GRID3 End

		, GRID6 : {
			css : {
				height : "200px"
				,width : "250px"
			}
			,title : "모집경로"
			,header : "선택,모집경로,모집경로"
			,columnIds : "rowCheck,onOffType,onOffTypeNm"
			,colAlign : "center,center,left"
			,colTypes : "ch,ro,ro"
			,colSorting : "str,str,str"
			,widths : "50,0,200"
			,paging: false
			//,multiCheckbox : true
			,hiddenColumns : [1]
		}	//GRID6 End

		, GRID7 : {
			css : {
				height : "200px"
				,width : "435px"
			}
			,title : "대상제품"
			,header : "선택,제품ID,제품코드,제품명"
			,columnIds : "rowCheck,modelId,modelCode,modelNm"
			,colAlign : "center,left,left,left"
			,colTypes : "ch,ro,ro,ro"
			,colSorting : "str,str,str,str"
			,widths : "50,0,150,235"
			,paging: false
			//,multiCheckbox : true
			,hiddenColumns : [1]
			,buttons : {
				hright : {
					btnUpExcel : {label : "엑셀업로드"}				
				},	
				left : {
					btnDnSocExcel : { label : "대상요금제엑셀업로드양식"}
			    	,btnDnModelExcel : { label : "대상제품엑셀업로드양식"}
				}
			}, onButtonClick : function(btnId, selectedData) {
				var form = this;
				switch (btnId) {
					
					case "btnUpExcel":
						
						
						mvno.ui.createForm("POPUP2MID");
						
						PAGE.POPUP2MID.clear();
						PAGE.POPUP2MID.setFormData({});
						
						//var fileName;
						
						/*
						PAGE.POPUP2MID.attachEvent("onUploadFile", function(realName, serverName) {
							fileName = serverName;
						});
						
						PAGE.POPUP2MID.attachEvent("onUploadComplete", function(count){
							
							var url = ROOT + "/gift/prmtmgmt/getExcelUpModelList.json";
							var userOptions = {timeout:180000};
							
							mvno.cmn.ajax(url, {fileName : fileName , reqBuyType : PAGE.FORM2.getItemValue("reqBuyType")}, function(resultData) {
								var rData = resultData.result;
								mvno.ui.closeWindowById("POPUP2");
 								PAGE.GRID7.clearAll();
								PAGE.GRID7.parse(rData.data.rows, "js");
								mvno.ui.alert(rData.msg);
							}, userOptions);
						});
						
						*/
						
						/*
						PAGE.POPUP2MID.attachEvent("onFileRemove",function(realName, serverName){
							//PAGE.POPUP1TOP.clearAll();
							
							return true;
						});
						*/
						
						var uploader = PAGE.POPUP2MID.getUploader("file_upload1");
						uploader.revive();
						
						mvno.ui.popupWindowById("POPUP2", "사은품프로모션 대상제품 선택", 405, 200, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
											
						break;
					case "btnDnSocExcel":
						
						mvno.cmn.download("/gift/prmtmgmt/getSocTempExcelDown.json");
						
					break;			
					case "btnDnModelExcel":
						
						mvno.cmn.download("/gift/prmtmgmt/getModelTempExcelDown.json");
						
					break;			
				}
			}
		
		}	//GRID7 End

		, GRID4 : {
			css : {
				height : "200px"
				,width : "435px"
			}
			,title : "사은품"
			,header : "선택,사은품명,가격,제품ID,중복여부,정렬"
			,columnIds : "rowCheck,prdtNm,outUnitPric,prdtId,dupYn,sort"
			,colAlign : "center,left,right,center,center,right"
			,colTypes : "ch,coro,ron,ro,ro,edn"
			,colSorting : "str,str,str,str,str,int"
			,widths : "50,280,55,0,0,50"
			,paging: false
			,hiddenColumns : [3,4]
			,buttons : {
				right : {
					btnDel : { label : "삭제" }
					,btnAdd : { label : "추가" }
					,btnReg : { label : "저장" }
					,btnMod : { label : "저장(수정)" }
				}
			}

			,onValidateMore : function (data){
				
			}
			,onButtonClick : function(btnId) {
				
				var grid = this;
				
				switch(btnId){
					
					case "btnDel" :
						
						if(grid.getSelectedRowId() == null){
							mvno.ui.alert("삭제 하려는 사은품을 선택하세요.");
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
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("billDt"))){
							mvno.ui.alert("정산기준일 을 입력하세요.");
							return;
						}
						var strtDtFM = new Date(PAGE.FORM2.getItemValue("strtDt")).format("yyyymmdd");
						var endDtFM = new Date(PAGE.FORM2.getItemValue("endDt")).format("yyyymmdd");
						
						if(strtDtFM > endDtFM) {
							mvno.ui.alert("종료일이 시작일 전입니다.\n종료일을 변경하세요.");
							return;
						}
						
						if(strtDtFM < startDt) {
							mvno.ui.alert("시작일은 내일 이후의 날짜를 선택해야 합니다.");
							return;
						}
						/* 
						var amountLimit = PAGE.FORM2.getItemValue("amountLimit");
						if(amountLimit == "" || amountLimit.valueOf()== 0){
							mvno.ui.alert("총금액 제한을 입력하세요.");
							return;
							
						}
						 */
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

						// 숫자 체크 정규식(ValidInteger 제대로 동작되지 않음, '2-13' 입력가능)
						var regex = /^[0-9]*$/;
						if (PAGE.FORM2.getItemValue("prmtType") == 'D') {
							// 기본 사은품은 총 선택 개수 입력을 받지 않는다.
							PAGE.FORM2.setItemValue("choiceLimit", null);
						} else if (PAGE.FORM2.getItemValue("prmtType") == 'C' && (!regex.test(PAGE.FORM2.getItemValue("choiceLimit")) || PAGE.FORM2.getItemValue("choiceLimit") < 1)) {
							mvno.ui.alert("총 선택 개수 제한을 1개 이상으로 입력하세요.");
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
						
						if(PAGE.GRID6.getCheckedRowData().length < 1){
							mvno.ui.alert("모집경로를 선택하세요.");
							return;
						}
						
						if(PAGE.GRID7.getCheckedRowData().length < 1){
							mvno.ui.alert("대상제품을 선택하세요.");
							return;
						}
						
						if(PAGE.GRID4.getCheckedRowData().length < 1){
							mvno.ui.alert("사은품을 선택하세요.");
							return;
						}
						
						if (PAGE.FORM2.getItemValue("choiceLimit") > PAGE.GRID4.getCheckedRowData().length) {
							mvno.ui.alert("총 선택 개수 제한이 선택한 사은품 개수 보다 큽니다. 사은품 혹은 총 선택 개수 제한을 수정하시기 바랍니다.");
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
							
							// 모집경로
							var arrOnOffType = [];
							
							var arrOnOffData = PAGE.GRID6.getCheckedRowData();
							
							for(var idx=0; idx<arrOnOffData.length; idx++) {
								arrOnOffType.push(arrOnOffData[idx]);
							}
							
							// 대상제품
							var arrModel = [];
							
							var arrModelData = PAGE.GRID7.getCheckedRowData();
							
							for(var idx=0; idx<arrModelData.length; idx++) {
								arrModel.push(arrModelData[idx]);
							}
							
							// 사은품제품
							var arrVas = [];
							
							var arrVasData = PAGE.GRID4.getCheckedRowData();
							
							for(var idx=0; idx<arrVasData.length; idx++) {
								var strSort = arrVasData[idx].sort + '';
								
								var intSort = strSort.replace(/[^0-9]/g,'');
								
								arrVasData[idx].sort = intSort;
								
								arrVas.push(arrVasData[idx]);
							}
							
							var prmtText = PAGE.FORM2.getItemValue("prmtText")+ "";
							prmtText = prmtText.substr(0,160);
							
							var sData = {
									prmtNm : PAGE.FORM2.getItemValue("prmtNm")
									, strtDt : PAGE.FORM2.getItemValue("strtDt").format("yyyymmdd")
									, endDt : PAGE.FORM2.getItemValue("endDt").format("yyyymmdd")
									, orgnType : PAGE.FORM2.getItemValue("orgnType")
									, reqBuyType : PAGE.FORM2.getItemValue("reqBuyType")
									, amountLimit : PAGE.FORM2.getItemValue("amountLimit")
									, nacYn : PAGE.FORM2.getItemValue("nacYn")
									, mnpYn : PAGE.FORM2.getItemValue("mnpYn")
									, enggCnt0 : PAGE.FORM2.getItemValue("enggCnt0")
									, enggCnt12 : PAGE.FORM2.getItemValue("enggCnt12")
									, enggCnt18 : PAGE.FORM2.getItemValue("enggCnt18")
									, enggCnt24 : PAGE.FORM2.getItemValue("enggCnt24")
									, enggCnt30 : PAGE.FORM2.getItemValue("enggCnt30")
									, enggCnt36 : PAGE.FORM2.getItemValue("enggCnt36")
									, prmtText : prmtText
									, prmtType : PAGE.FORM2.getItemValue("prmtType")
									, choiceLimit : PAGE.FORM2.getItemValue("prmtType") == 'C' ? PAGE.FORM2.getItemValue("choiceLimit") : null
									, showYn : PAGE.FORM2.getItemValue("showYn")
									, billDt : PAGE.FORM2.getItemValue("billDt").format("yyyymmdd")
									, prmtOrgnId : PAGE.FORM2.getItemValue("prmtOrgnId")
									, wirelessYn : PAGE.FORM2.getItemValue("wirelessYn")
							};
							
							// 조직
							sData.orgnList = arrOrgn;
							
							// 요금제
							sData.rateList = arrRate;
							
							// 모집경로
							sData.onOffTypeList = arrOnOffType;
							
							// 대상제품
							sData.modelList = arrModel;
							
							// 사은품제품
							sData.giftPrdtList = arrVas;
							
							mvno.cmn.ajax4json(ROOT + "/gift/prmtmgmt/regGiftPrmtInfo.json", sData, function(result) {
								PAGE.FORM2.clearChanged();
								mvno.ui.closeWindowById("GROUP1");
								mvno.ui.notify("NCMN0004");
								PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
							});
						});
						
						break;
						
					case "btnMod" :
						
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
						/* 
						var amountLimit = PAGE.FORM2.getItemValue("amountLimit");
						if(amountLimit == "" || amountLimit.valueOf()== 0){
							mvno.ui.alert("총금액 제한을 입력하세요.");
							return;
							
						}
						 */
						
						if(strtDtFM < startDt) {
							mvno.ui.alert("시작일은 내일 이전 날짜를 선택할 수 없습니다.");
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
						
						if(PAGE.GRID6.getCheckedRowData().length < 1){
							mvno.ui.alert("모집경로를 선택하세요.");
							return;
						}
						
						if(PAGE.GRID7.getCheckedRowData().length < 1){
							mvno.ui.alert("대상제품을 선택하세요.");
							return;
						}
						
						if(PAGE.GRID4.getCheckedRowData().length < 1){
							mvno.ui.alert("사은품을 선택하세요.");
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
							
							// 모집경로
							var arrOnOffType = [];
							
							var arrOnOffData = PAGE.GRID6.getCheckedRowData();
							
							for(var idx=0; idx<arrOnOffData.length; idx++) {
								arrOnOffType.push(arrOnOffData[idx]);
							}
							
							// 대상제품
							var arrModel = [];
							
							var arrModelData = PAGE.GRID6.getCheckedRowData();
							
							for(var idx=0; idx<arrModelData.length; idx++) {
								arrModelType.push(arrModelData[idx]);
							}
							
							// 사은품제품
							var arrVas = [];
							
							var arrVasData = PAGE.GRID4.getCheckedRowData();
							
							for(var idx=0; idx<arrVasData.length; idx++) {
								var strSort = arrVasData[idx].sort + '';
								console.info(arrVasData[idx]);
								
								var intSort = strSort.replace(/[^0-9]/g,'');
								
								arrVasData[idx].sort = intSort;
								
								arrVas.push(arrVasData[idx]);
							}
							
							var data = PAGE.GRID1.getSelectedRowData();

							var prmtText = PAGE.FORM2.getItemValue("prmtText") + "";
							prmtText = prmtText.substr(0,160);
							
							var sData = {
									prmtId:data.prmtId
									, prmtNm : PAGE.FORM2.getItemValue("prmtNm")
									, strtDt : PAGE.FORM2.getItemValue("strtDt").format("yyyymmdd")
									, endDt : PAGE.FORM2.getItemValue("endDt").format("yyyymmdd")
									, orgnType : PAGE.FORM2.getItemValue("orgnType")
									, reqBuyType : PAGE.FORM2.getItemValue("reqBuyType")
									, amountLimit : PAGE.FORM2.getItemValue("amountLimit")
									, nacYn : PAGE.FORM2.getItemValue("nacYn")
									, mnpYn : PAGE.FORM2.getItemValue("mnpYn")
									, enggCnt0 : PAGE.FORM2.getItemValue("enggCnt0")
									, enggCnt12 : PAGE.FORM2.getItemValue("enggCnt12")
									, enggCnt18 : PAGE.FORM2.getItemValue("enggCnt18")
									, enggCnt24 : PAGE.FORM2.getItemValue("enggCnt24")
									, enggCnt30 : PAGE.FORM2.getItemValue("enggCnt30")
									, enggCnt36 : PAGE.FORM2.getItemValue("enggCnt36")
									, prmtText : prmtText
							};
							
							// 조직
							sData.orgnList = arrOrgn;
							
							// 요금제
							sData.rateList = arrRate;
							
							// 모집경로
							sData.onOffTypeList = arrOnOffType;
							
							// 대상경로
							sData.modelList = arrModel;
							
							// 사은품제품
							sData.giftPrdtList = arrVas;
							
							mvno.cmn.ajax4json(ROOT + "/gift/prmtmgmt/modGiftPrmtInfo.json", sData, function(result) {
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
		
		,FORM3  : {
			 title : ""
			,buttons : {
				center : {
					btnEndDt : { label : "종료일변경" }
					,btnText : { label : "노출문구변경" }
					,btnEnd : { label : "삭제" }
					,btnClose : { label : "닫기" }
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
						
						mvno.cmn.ajax4confirm("해당 프로모션의 종료일을 변경 하시겠습니까?", ROOT + "/gift/prmtmgmt/updGiftPrmtByInfo.json", sData, function(result) {
							PAGE.FORM2.clearChanged();
							mvno.ui.closeWindowById("GROUP1");
							mvno.ui.notify("NCMN0002");
							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
						});
						
						break;

					case "btnText" :
						
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("prmtId"))){
							mvno.ui.alert("대상 프로모션이 존재하지 않습니다.");
							return;
						}
						
						
						var sData = {
								prmtId : PAGE.FORM2.getItemValue("prmtId")
								,chngTypeCd : 'T'
								,prmtText : PAGE.FORM2.getItemValue("prmtText")
						};
						
						mvno.cmn.ajax4confirm("해당 프로모션의 노출문구를 변경 하시겠습니까?", ROOT + "/gift/prmtmgmt/updGiftPrmtByInfo.json", sData, function(result) {
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
						
						mvno.cmn.ajax4confirm("해당 프로모션을 삭제 하시겠습니까?", ROOT + "/gift/prmtmgmt/updGiftPrmtByInfo.json", sData, function(result) {
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
			,onValidateMore : function (data){
				
			}

		},	//FORM3 End

		POPUP1MID : {
				title : "엑셀업로드",
				items : [
					{ type : "block",
					  list : [
						{ type : "newcolumn", offset : 10 },
						{ type : "upload", 
						  label : "대상요금제등록 엑셀파일",
						  name : "file_upload1",
						  inputWidth : 320,
						  url : "/rcp/dlvyMgmt/regParExcelUp.do",
						  userdata : { limitSize:10000 },
						  autoStart : true } 
					]},
					{type:"input", width:100, name:"rowData", hidden:true}
				],
				
				buttons : {
					center : {
						btnClose : { label : "닫기" }
					}
				},
				
				onButtonClick : function(btnId) {
					
					var form = this; // 혼란방지용으로 미리 설정 (권고)
					
					switch (btnId) {
							
						case "btnClose":
							mvno.ui.closeWindowById("POPUP1");
						break;
					}
				},
				
				onUploadFile : function(realName, serverName) {
					fileName = serverName;
				},
				
				onUploadComplete : function(count) {
					var url = ROOT + "/gift/prmtmgmt/getExcelUpSocList.json";
					var userOptions = {timeout:180000};
					
					mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
						var rData = resultData.result;
						mvno.ui.closeWindowById("POPUP1");
						PAGE.GRID3.clearAll();
						PAGE.GRID3.parse(rData.data.rows, "js");
						mvno.ui.alert(rData.msg);						
					}, userOptions);
				}
				
			},	//POPUP1MID End
			POPUP2MID : {
				title : "엑셀업로드",
				items : [
					{ type : "block",
					  list : [
						{ type : "newcolumn", offset : 10 },
						{ type : "upload", 
						  label : "대상제품등록 엑셀파일",
						  name : "file_upload1",
						  inputWidth : 320,
						  url : "/rcp/dlvyMgmt/regParExcelUp.do",
						  userdata : { limitSize:10000 },
						  autoStart : true } 
					]},
					{type:"input", width:100, name:"rowData", hidden:true}
				],
				
				buttons : {
					center : {
						btnClose : { label : "닫기" }
					}
				},
				
				onButtonClick : function(btnId) {
					
					var form = this; // 혼란방지용으로 미리 설정 (권고)
					
					switch (btnId) {
							
						case "btnClose":
							mvno.ui.closeWindowById("POPUP2");
						break;
					}
				},
				
				onUploadFile : function(realName, serverName) {
					fileName = serverName;
				},
				
				onUploadComplete : function(count) {
					var url = ROOT + "/gift/prmtmgmt/getExcelUpModelList.json";
					var userOptions = {timeout:180000};
					
					mvno.cmn.ajax(url, {fileName : fileName , reqBuyType : PAGE.FORM2.getItemValue("reqBuyType")}, function(resultData) {
						var rData = resultData.result;
						mvno.ui.closeWindowById("POPUP2");
						PAGE.GRID7.clearAll();
						PAGE.GRID7.parse(rData.data.rows, "js");
						mvno.ui.alert(rData.msg);
					}, userOptions);
				}
				
			}	//POPUP2MID End
		
	}
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		buttonAuth: ${buttonAuth.jsonString},
		onOpen:function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");

			var time = new Date().getTime();
			time = time - 1000 * 3600 * 24 * 7;
			var frDay = new Date(time).format("yyyymmdd");

			PAGE.FORM1.setItemValue("searchBaseDate",frDay);
			PAGE.FORM1.setItemValue("searchBaseEndDate",today);
			
			//PAGE.FORM1.setItemValue("searchBaseDate", today);
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0055", etc3:"Y"}, PAGE.FORM1, "orgnType");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9022"}, PAGE.FORM1, "reqBuyType");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM1, "onOffType");
		}
	};
</script>