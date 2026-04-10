<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	* @Class Name : plcyMgmt.jsp
	* @Description : 판매정책관리
	* @
	* @ 수정일	    수정자 수정내용
	* @ ---------- ------ -----------------------------
	* @ 2015.08.06
	* @Create Date : 2015. 8. 6.
	*/
%>

<!-- 화면 배치 -->
<!-- 조회조건 -->
<div id="FORM1" class="section-search"></div>

<!-- 판매정책목록 -->
<div id="GRID1"></div>

<!-- 정책등록 -->
<div id="GROUP1" style="display:none;">
	<!-- 정책기본정보 -->
	<div id="FORM2" class="section-search"></div>
	<div class="row">
		<!-- 대상대리점 -->
		<div id="GRID2" class="c2-5" style="width: 290px; z-index: 90;"></div>
		<!-- 대상요금제 -->
		<div id="GRID3" class="c3" style="width: 295px; z-index: 80;"></div>
		<!-- 개통유형 -->
		<div id="GRID4" class="c2" style="width: 100px; z-index: 70;"></div>
		<!-- 약정기간 -->
		<div id="GRID5" class="c2" style="width: 100px; z-index: 60;"></div>
		<!-- 할부개월수 -->
		<div id="GRID13" class="c2" style="width: 115px; z-index: 50;"></div>
	</div>
	<div class="row">
		<!-- 대상제품 -->
		<div id="GRID6" class="c5"></div>
		<!-- 제품별 arpu -->
		<div id="GRID7" class="c5"></div>
	</div>
	<!-- 정책내역 -->
	<div id="GRID8"></div>
</div>
<!-- 제품별 수수료 -->
<div id="GROUP2" style="display:none;">
	<!-- 제품목록 -->
	<div id="GRID9"></div>
	<!-- 수수료금액 -->
	<div id="FORM3" class="section-box"></div>
</div>
<!-- 제품별arpu -->
<div id="GROUP3" style="display:none;">
	<!-- 요금제조회조건 -->
	<div id="FORM4" class="section-search"></div>
	<!-- 요금제목록 -->
	<div id="GRID10"></div>
	<!-- arpu수수료 -->
	<div id="FORM5" class="section-box"></div>
</div>
<!-- 대리점추가 -->
<div id="GRID11" class="section-full"></div>
<!-- 요금제추가 -->
<div id="GRID12" class="section-full"></div>
<!-- 정책복사 -->
<div id="FORM6" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

var today = new Date().format("yyyymmdd");

var agencyType = "";

var allChkYN = true;

var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
				{type: "block", list: [
					{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "stdrDt", label: "기준일자", calendarPosition: "bottom", width:100, labelWidth: 70, required:true, offsetLeft:10},
					{type: "newcolumn", offset:30},
					{type: "input", name: "searchVal", label: "판매정책", width:324, offsetLeft: 20}
				]},
				{type: "block", list: [
					{type: "select", name: "searchPlcyTypeCd", label: "정책유형", width:100, labelWidth: 70, offsetLeft: 10},
					{type: "newcolumn", offset:30},
					{type: "select", name: "searchPlcySctnCd", label: "정책구분", width: 100, offsetLeft: 20},
					{type: "newcolumn", offset:30},
					{type: "select", name: "searchSupportTypeCd", label: "할인유형", width:100, labelWidth: 70, offsetLeft: 20}
				]},
				{type: "newcolumn"},
				{type: "button", value: "조회", name: "btnSearch" , className:"btn-search2"}
			],
			onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/sale/plcyMgmt/getPlcyMgmtList.json", form);
						break;
				}
	
			}
		},
		//-------------------------------------------------------------------------------------
		//판매정책목록
		GRID1 : {
			css : {
				height : "530px"
			},
			title : "판매정책목록",
			header : "판매정책코드,판매정책명,시작일시,종료일시,정책유형,정책구분,제품구분,할인유형,적용일구분,할부수수료율,신규여부,번호이동여부,일반기변여부,우수기변여부,시행확정자,시행확정일시,정책유형,할인유형,셀프개통여부", //19
			columnIds : "salePlcyCd,salePlcyNm,saleStrtDttm,saleEndDttm,plcyType,plcySctn,prdtSctnCd,sprtTpNm,applSctn,instRate,newYn,mnpYn,hcnYn,hdnYn,cnfmNm,cnfmDttm,plcyTypeCd,sprtTp,selfOpenYn", //19
			colAlign : "center,left,center,center,center,center,center,center,center,right,center,center,center,center,center,center,center,center,center",//19
			colTypes : "ro,ro,roXdt,roXdt,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXdt,ro,ro,ro", //19
			colSorting : "str,str,str,str,str,str,str,str,str,int,str,str,str,str,str,str,str,str,str", //19
			widths : "120,250,130,130,100,80,80,80,80,100,80,80,80,80,80,130,50,50,50", //19
			paging: true,
			pageSize:20,
			hiddenColumns:[16,17,18],
			buttons : {
				/* hright : {
					btnExcel : { label : "엑셀다운로드" }
				}, */
				left : {
					btnAddAgncy : { label : "대리점추가" },
					btnPlcyCopy01 : { label : "정책복사" },
					btnPayPlan : { label : "할부기간" },
					btnAddAgncy2 : { label : "선불대리점추가" },
					btnAddRate : { label : "요금제추가" }
				},
				right : {
					btnReg : { label : "등록" },
					btnMod : { label : "수정" }
				}
			},
			checkSelectedButtons : ["btnMod"],
			onRowDblClicked : function(rowId, celInd) {
				// 수정버튼 누른것과 같이 처리
				this.callEvent("onButtonClick", ["btnMod"]);
			},
			onButtonClick : function(btnId, selectedData) {
				var form = this;
	
				switch (btnId) {
					case "btnPlcyCopy01" :
						
						var data = PAGE.GRID1.getSelectedRowData();
						
						//console.log("data=" + data);
						if(data == null) {
							mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
							return;
						}
						
						mvno.ui.createForm("FORM6");
						
						var edate = new Date();
						var endDt = new Date(edate.setDate(edate.getDate() + 7)).format("yyyymmdd");
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0050"}, PAGE.FORM6, "newPlcyTypeCd");
						
						PAGE.FORM6.setFormData(data);
						
						PAGE.FORM6.setItemValue("newPlcyTypeCd", data.plcyTypeCd);
						PAGE.FORM6.setItemValue("newSaleStrtDt", today);
						PAGE.FORM6.setItemValue("newSaleStrtTm", "000000");
						PAGE.FORM6.setItemValue("newSaleEndDt", endDt);
						PAGE.FORM6.setItemValue("newSaleEndTm", "235959");
						
						PAGE.FORM6.clearChanged();
						
						mvno.ui.popupWindowById("FORM6", "정책복사", 720, 350, {
							onClose: function(win) {
								if (! PAGE.FORM6.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						
						break;
						
					case "btnAddAgncy" :
						
						var data = PAGE.GRID1.getSelectedRowData();
						
						//console.log("data=" + data);
						if(data == null) {
							mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
							return;
						}
						
						mvno.ui.createGrid("GRID11");
						
						agencyType = "addAgency";
						PAGE.GRID11.list(ROOT + "/sale/plcyMgmt/getPlcyAgncyAddList.json", data);
						
						mvno.ui.popupWindowById("GRID11", "판매정책대리점추가", 500, 500, {
							onClose: function(win) {
								/* if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								}); */
								$('#GRID11 input:checkbox').prop('checked',false);
								win.closeForce();
							}
						});
						
						break;

					case "btnAddAgncy2" :
						
						var data = PAGE.GRID1.getSelectedRowData();
						
						//console.log("data=" + data);
						if(data == null) {
							mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
							return;
						}
						
						mvno.ui.createGrid("GRID11");
						
						agencyType = "addPreAgency";
						PAGE.GRID11.list(ROOT + "/sale/plcyMgmt/getPlcyAgncyAddList2.json", data);
						
						mvno.ui.popupWindowById("GRID11", "판매정책대리점추가", 500, 500, {
							onClose: function(win) {
								/* if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								}); */
								$('#GRID11 input:checkbox').prop('checked',false);
								win.closeForce();
							}
						});
						
						break;						
						
					case "btnAddRate" :
						
						var data = PAGE.GRID1.getSelectedRowData();
						
						//console.log("data=" + data);
						if(data == null) {
							mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
							return;
						}
						
						mvno.ui.createGrid("GRID12");
						
						PAGE.GRID12.list(ROOT + "/sale/plcyMgmt/getPlcyRateAddList.json", data);
						
						mvno.ui.popupWindowById("GRID12", "판매정책요금제추가", 550, 500, {
							onClose: function(win) {
								/* if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								}); */
								$('#GRID12 input:checkbox').prop('checked',false);
								win.closeForce();
							}
						});
						
						break;
						
					case "btnExcel" :
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						} else {
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/sale/plcyMgmt/getPlcyMgmtListExcel.json?menuId=MSP2002012", true, {postData:searchData});
						}
						break;
						
					case "btnReg":
						
						mvno.ui.createForm("FORM2");	// 판매정책정보
						mvno.ui.createGrid("GRID2");	// 대리점
						mvno.ui.createGrid("GRID3");	// 요금제
						mvno.ui.createGrid("GRID4");	// 가입유형
						mvno.ui.createGrid("GRID5");	// 약정기간
						mvno.ui.createGrid("GRID13");	// 할부개월수
						mvno.ui.createGrid("GRID6");	// 대상제품
						mvno.ui.createGrid("GRID7");	// arpu수수료
						mvno.ui.createGrid("GRID8");	// 정책상세내역
						
						PAGE.GRID2.clearAll();
						PAGE.GRID3.clearAll();
						PAGE.GRID6.clearAll();
						PAGE.GRID7.clearAll();
						PAGE.GRID8.clearAll();
						
						PAGE.FORM2.setFormData(true);
						
						PAGE.FORM2.setItemValue("flag", "I");
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0055"}, PAGE.FORM2, "orgnType");		// 채널유형
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0058"}, PAGE.FORM2, "sprtTp", {topOption: "- 선택 -"});	// 할인유형
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0050"}, PAGE.FORM2, "plcyTypeCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0052"}, PAGE.FORM2, "applSctnCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0007"}, PAGE.FORM2, "plcySctnCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0048"}, PAGE.FORM2, "prdtSctnCd");


						
						var rateData = {
								payClCd : "PO",
								rateType : PAGE.FORM2.getItemValue("plcySctnCd"),
								dataType : PAGE.FORM2.getItemValue("prdtSctnCd")
						};
						
						PAGE.GRID2.list(ROOT + "/sale/plcyMgmt/getAgncyTrgtList.json", "");
						//PAGE.GRID3.list(ROOT + "/sale/plcyMgmt/getRateTrgtList.json", {payClCd:"PO", orgnType:PAGE.FORM2.getItemValue("orgnType")});
						PAGE.GRID3.list(ROOT + "/sale/plcyMgmt/getRateTrgtList.json", rateData);
						PAGE.GRID4.list(ROOT + "/sale/subsdMgmt/getCommonGridList.json", {grpId: "CMN0049"});	// 개통유형
						PAGE.GRID5.list(ROOT + "/sale/subsdMgmt/getCommonGridList.json", {grpId: "CMN0053"});	// 약정기간
						PAGE.GRID13.list(ROOT + "/sale/subsdMgmt/getCommonGridList.json", {grpId: "ORG0017"});	// 할부개월수
						
// 						PAGE.FORM2.enableItem("salePlcyCd");
						PAGE.FORM2.enableItem("salePlcyNm");
						PAGE.FORM2.enableItem("plcyTypeCd");
						PAGE.FORM2.enableItem("saleStrtDt");
						PAGE.FORM2.enableItem("saleStrtTm");
						PAGE.FORM2.enableItem("saleEndDt");
						PAGE.FORM2.enableItem("saleEndTm");
						PAGE.FORM2.enableItem("applSctnCd");
						PAGE.FORM2.enableItem("orgnType");
						PAGE.FORM2.enableItem("plcySctnCd");
						PAGE.FORM2.enableItem("prdtSctnCd");
						PAGE.FORM2.enableItem("instRate");
						PAGE.FORM2.enableItem("cnfmNm");
						PAGE.FORM2.enableItem("sprtTp");
						if(PAGE.FORM2.getItemValue("plcySctnCd") == "01") {
							PAGE.FORM2.disableItem("selfOpenYn");
						} else {
							PAGE.FORM2.enableItem("selfOpenYn");
						}

						PAGE.FORM2.setItemValue("sprtTp", "KD");
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "판매정책등록", 950, 1000, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) {
									$('#GRID6 input:checkbox').prop('checked',false);
									$('#GRID7 input:checkbox').prop('checked',false);
									
									return true;
								}
								mvno.ui.confirm("CCMN0005", function() {
									$('#GRID6 input:checkbox').prop('checked',false);
									$('#GRID7 input:checkbox').prop('checked',false);
									
									win.closeForce();
								});
							}
						,maximized:true
						});
						
						break;
						
					case "btnMod":
						var data = PAGE.GRID1.getSelectedRowData();
						
						if(data == null) {
							mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
							return;
						}
						
						mvno.ui.createForm("FORM2");	// 판매정책정보
						mvno.ui.createGrid("GRID2");	// 대리점
						mvno.ui.createGrid("GRID3");	// 요금제
						mvno.ui.createGrid("GRID4");	// 가입유형
						mvno.ui.createGrid("GRID5");	// 약정기간
						mvno.ui.createGrid("GRID13");	// 할부개월수
						mvno.ui.createGrid("GRID6");	// 대상제품
						mvno.ui.createGrid("GRID7");	// arpu수수료
						mvno.ui.createGrid("GRID8");	// 정책상세내역
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0055"}, PAGE.FORM2, "orgnType");		// 채널유형
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0058"}, PAGE.FORM2, "sprtTp", {topOption: "- 선택 -"});	// 할인유형
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0050"}, PAGE.FORM2, "plcyTypeCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0052"}, PAGE.FORM2, "applSctnCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0007"}, PAGE.FORM2, "plcySctnCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0048"}, PAGE.FORM2, "prdtSctnCd");
						
						PAGE.GRID2.list(ROOT + "/sale/plcyMgmt/getSaleOrgnList.json", selectedData);
						PAGE.GRID3.list(ROOT + "/sale/plcyMgmt/getSaleRateList.json", selectedData);
						PAGE.GRID4.list(ROOT + "/sale/plcyMgmt/getSaleOperList.json", selectedData);
						PAGE.GRID5.list(ROOT + "/sale/plcyMgmt/getSaleAgrmList.json", selectedData);
						PAGE.GRID13.list(ROOT + "/sale/plcyMgmt/getInstList.json", selectedData);
						PAGE.GRID6.list(ROOT + "/sale/plcyMgmt/getSalePrdtList.json", selectedData);
						PAGE.GRID7.list(ROOT + "/sale/plcyMgmt/getSaleArpuList.json", selectedData);
						PAGE.GRID8.list(ROOT + "/sale/plcyMgmt/getSaleSubsdList.json", selectedData);
						
						PAGE.FORM2.setFormData(selectedData, true);
						
						//PAGE.FORM2.disableItem("salePlcyCd");
						
						if(selectedData.cnfmYn == "Y") {
// 							PAGE.FORM2.disableItem("salePlcyCd");
							PAGE.FORM2.disableItem("salePlcyNm");
							PAGE.FORM2.disableItem("plcyTypeCd");
							PAGE.FORM2.disableItem("saleStrtDt");
							PAGE.FORM2.disableItem("saleStrtTm");
							//PAGE.FORM2.disableItem("saleEndDt");
							//PAGE.FORM2.disableItem("saleEndTm");
							PAGE.FORM2.disableItem("applSctnCd");
							PAGE.FORM2.disableItem("orgnType");
							PAGE.FORM2.disableItem("plcySctnCd");
							PAGE.FORM2.disableItem("prdtSctnCd");
							PAGE.FORM2.disableItem("instRate");
							PAGE.FORM2.disableItem("cnfmNm");
							PAGE.FORM2.disableItem("sprtTp");
							PAGE.FORM2.disableItem("selfOpenYn");
						} else {
// 							PAGE.FORM2.enableItem("salePlcyCd");
							PAGE.FORM2.enableItem("salePlcyNm");
							PAGE.FORM2.enableItem("plcyTypeCd");
							PAGE.FORM2.enableItem("saleStrtDt");
							PAGE.FORM2.enableItem("saleStrtTm");
							//PAGE.FORM2.enableItem("saleEndDt");
							//PAGE.FORM2.enableItem("saleEndTm");
							PAGE.FORM2.enableItem("applSctnCd");
							PAGE.FORM2.enableItem("orgnType");
							PAGE.FORM2.enableItem("plcySctnCd");
							PAGE.FORM2.enableItem("prdtSctnCd");
							PAGE.FORM2.enableItem("instRate");
							PAGE.FORM2.enableItem("cnfmNm");
							PAGE.FORM2.enableItem("selfOpenYn");
							if(selectedData.plcySctnCd== "01") { // 단말기
								PAGE.FORM2.enableItem("sprtTp");
							} else {
								PAGE.FORM2.disableItem("sprtTp");
							}
						}
						
						PAGE.FORM2.setItemValue("flag", "U");
						PAGE.FORM2.setItemValue("saleStrtDt", selectedData.saleStrtDttm.substring(0, 8));
						PAGE.FORM2.setItemValue("saleStrtTm", selectedData.saleStrtDttm.substring(8));
						PAGE.FORM2.setItemValue("saleEndDt", selectedData.saleEndDttm.substring(0, 8));
						PAGE.FORM2.setItemValue("saleEndTm", selectedData.saleEndDttm.substring(8));
						if ( selectedData.selfOpenYn == "Y" ) {
							PAGE.FORM2.setItemValue("selfOpenYn", '1');
						} else {
							PAGE.FORM2.setItemValue("selfOpenYn", '');
						}
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "판매정책수정", 950, 1000, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) {
									$('#GRID6 input:checkbox').prop('checked',false);
									$('#GRID7 input:checkbox').prop('checked',false);
									
									return true;
								}
								
								mvno.ui.confirm("CCMN0005", function() {
									$('#GRID6 input:checkbox').prop('checked',false);
									$('#GRID7 input:checkbox').prop('checked',false);
									
									win.closeForce();
								});
							}
							,maximized:true
						});
						
						break;
						
					case "btnPayPlan":
						var sdata = PAGE.GRID1.getSelectedRowData();
						if(sdata == null) {
							mvno.ui.alert("대상정책을 선택하세요");
							return;
						}
						
						mvno.ui.popupWindow(ROOT + "/sale/plcyMgmt/sertchInst.do?salePlcyCd=" + sdata.salePlcyCd, "개월수 선택", 400, 300, {});
						
						break;
				}
			}
		},
		
		// --------------------------------------------------------------------------
		//판매정책등록
		FORM2 : {
			title : "판매정책정보",
			items : [
				{type: "settings", position: "label-left", labelWidth: "90"},

				{type: "block", blockOffset: 0, list: [
					{type: "select", name: "plcyTypeCd", label: "정책유형", width:100, labelWidth: 83, required: true, offsetLeft: 5},
					{type: "newcolumn"},
					{type: "input", name: "salePlcyCd", label: "판매정책", width:100, offsetLeft: 70, disabled:true, required: true},
					{type: "newcolumn"},
					{type: "input", name: "salePlcyNm", label: "", width:252, validate: "NotEmpty", maxLength: 60}
				]},
				{type: "block", blockOffset: 0, list: [
					{type: "calendar", name: "saleStrtDt", label: "판매시작일시", width: 100, labelWidth: 83, dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", validate: "NotEmpty", required: true, offsetLeft: 5},
					{type: "newcolumn"},
					{type: "input", name: "saleStrtTm", width: 50, validate:"ValidInteger", maxLength:6},
					{type: "newcolumn"},
					{type: "calendar", name: "saleEndDt", label: "판매종료일시", width: 100, dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", validate: "NotEmpty", required: true, offsetLeft: 16},
					{type: "newcolumn"},
					{type: "input", name: "saleEndTm", width: 50, validate:"ValidInteger", maxLength:6},
					{type: "newcolumn"},
					{type: "select", name: "applSctnCd", label: "적용일기준", width:102, labelWidth: 80, offsetLeft: 16, required: true},
					{type: "newcolumn"},
					{type: "checkbox", name: "selfOpenYn", label: "셀프개통", width:20, labelWidth: 80, offsetLeft: 16, position: "label-right"}
				]},
				{type: "block", blockOffset: 0, list: [
					{type: "select", name: "orgnType", label: "채널유형", width: 100, labelWidth: 83, offsetLeft: 5, required:true},
					{type: "newcolumn", offsetLeft:10},
					{type: "select", name: "plcySctnCd", label: "정책구분", width: 75, labelWidth: 65, offsetLeft: 10, required: true},
					{type: "newcolumn"},
					{type: "select", name: "prdtSctnCd", label: "제품구분", width: 75, labelWidth: 65, offsetLeft: 20, required: true},
					{type: "newcolumn"},
					{type: "select", name: "sprtTp", label: "할인유형", width:102, labelWidth: 80, offsetLeft: 16, required: true},
					{type: "newcolumn"},
					{type: "input", name: "instRate", label: "할부수수료율", width: 50, labelWidth:85, offsetLeft: 15, maxLength:4, validate:"ValidNumeric", required: true},
					{type: "newcolumn"},
					{type: "hidden", name: "cnfmNm", label: "시행확정자", width:80, labelWidth: 60, offsetLeft: 15, readonly: true}
				]}

				, {type: "hidden", name: "flag"}
				, {type: "hidden", name: "cnfmYn"}

			],
			onChange : function(name, value) {
				var form = this;

				switch(name){
					case "orgnType" :
						var data = {
							orgnType : value
						};
						PAGE.GRID2.list(ROOT + "/sale/plcyMgmt/getAgncyTrgtList.json", data);
						
						// 직영 + 단말 정책인 경우 요금제 재조회
						if(value == "CS7" || value == "CS4") {
							data = {
								payClCd : form.getItemValue("plcySctnCd") == "02" ? "" : "PO",
								rateType : form.getItemValue("plcySctnCd") == "01" ? "" : form.getItemValue("plcySctnCd"),
								dataType : form.getItemValue("prdtSctnCd")
							};
						} else {
							data = {
								payClCd : "PO",
								rateType : form.getItemValue("plcySctnCd"),
								dataType : form.getItemValue("prdtSctnCd")
							};
						}
						PAGE.GRID3.list(ROOT + "/sale/plcyMgmt/getRateTrgtList.json", data);
						break;
						
					case "plcySctnCd" : //정책구분
						
						// USIM 일때 지원금 유형 초기화 & 비활성화
						// 2016-20-28 심플할인 추가로 주석처리
// 						if(value == "01") { // 단말기
// 							PAGE.FORM2.enableItem("sprtTp");
// 						} else if(value == "02") { // USIM
// 							PAGE.FORM2.disableItem("sprtTp");
// 							PAGE.FORM2.setItemValue("sprtTp", "");
// 						}
						
						if(value == "01") { // 단말기
							PAGE.FORM2.setItemValue("sprtTp", "KD");
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0048"}, PAGE.FORM2, "prdtSctnCd"); //202401 wooki

							// [SR-2024-055] 제휴사(위탁온라인, 제휴대리점) 셀프개통URL 제공 및 개발
							PAGE.FORM2.setItemValue("selfOpenYn", '');
							PAGE.FORM2.disableItem("selfOpenYn");
						} else if(value == "02") { // USIM
							PAGE.FORM2.setItemValue("sprtTp", "");
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0048",etc1:"7200"}, PAGE.FORM2, "prdtSctnCd"); //202401 wooki - USIM일땐 제품구분 LTE+5G빼고조회

							// [SR-2024-055] 제휴사(위탁온라인, 제휴대리점) 셀프개통URL 제공 및 개발
							PAGE.FORM2.enableItem("selfOpenYn");
						}
						
						var data = {
							payClCd : (form.getItemValue("orgnType") == "CS7" || form.getItemValue("orgnType") == "CS4")  && value == "02" ? "" : "PO",
							rateType : (form.getItemValue("orgnType") == "CS7"|| form.getItemValue("orgnType") == "CS4")  && value == "01" ? "" : value,
							dataType : form.getItemValue("prdtSctnCd")
						};
						
						PAGE.GRID3.list(ROOT + "/sale/plcyMgmt/getRateTrgtList.json", data);
						
						PAGE.GRID6.clearAll();
						PAGE.GRID7.clearAll();
						
						break;
						
					case "prdtSctnCd" : //제품구분
						var data = {
							payClCd : (form.getItemValue("orgnType") == "CS7"|| form.getItemValue("orgnType") == "CS4" ) && form.getItemValue("plcySctnCd") == "02" ? "" : "PO",
							rateType : (form.getItemValue("orgnType") == "CS7" || form.getItemValue("orgnType") == "CS4") && form.getItemValue("plcySctnCd") == "01" ? "" : form.getItemValue("plcySctnCd"),
							dataType : value 
						};
						
						PAGE.GRID3.list(ROOT + "/sale/plcyMgmt/getRateTrgtList.json", data);
						
						PAGE.GRID6.clearAll();
						PAGE.GRID7.clearAll();
						
						break;
					
					case "saleStrtDt":
						PAGE.FORM2.setItemValue("saleStrtTm", "000000");
						break;
						
					case "saleEndDt":
						PAGE.FORM2.setItemValue("saleEndTm", "235959");
						break;
					
				}
			},
		},
		//-------------------------------------------------------------------------------------
		//대상조직
		GRID2 : {
			css : {
				height : "200px",
				width : "300px"
			},
			title : "대상조직",
			header : "선택,조직ID,조직명,조직유형",
			columnIds : "chk,orgnId,orgnNm,typeNm",
			colAlign : "center,center,left,left",
			colTypes : "ch,ro,ro,ro",
			colSorting : "str,str,str,str",
			widths : "30,120,160,110",
			paging: false,
			//multiCheckbox : true,
			hiddenColumns : [1],
			buttons : {
				hright : {
					btnAllChk : { label : "전체선택" }
				}
			},
			onButtonClick : function(btnId) {

				var grid = this;
				
				switch(btnId){
					case "btnAllChk" :
						
						this.forEachRow(function(id){
							
							var cell = this.cells(id,0);
							
							if (cell.isCheckbox()){
								if(allChkYN){
									if(!cell.isChecked()){
										cell.setValue(1);
										this.callEvent("onCheckBox", [id,0,1]);
									}
								}else{
									if(cell.isChecked()){
										cell.setValue(0);
										this.callEvent("onCheckBox", [id,0,0]);
									}
								}
							}
						});
						
						allChkYN = !allChkYN;
						
						break;
				}
			}
		},
		//-------------------------------------------------------------------------------------
		//요금제
		GRID3 : {
			css : {
				height : "200px",
				width : "285px"
			},
			title : "대상요금제",
			header : "선택,요금제코드,요금제명,기본료",
			columnIds : "chk,rateCd,rateNm,baseAmt",
			colAlign : "center,center,left,right",
			colTypes : "ch,ro,ro,ron",
			colSorting : "str,str,str,int",
			widths : "30,120,160,70",
			paging: false,
			//multiCheckbox : true,
			hiddenColumns : [1],
			buttons : {
				hright : {
					btnAllChk : { label : "전체선택" }
				}
			},
			onButtonClick : function(btnId) {

				var grid = this;
				
				switch(btnId){
					case "btnAllChk" :
						
						this.forEachRow(function(id){
							
							var cell = this.cells(id,0);
							
							if (cell.isCheckbox()){
								if(allChkYN){
									if(!cell.isChecked()){
										cell.setValue(1);
										this.callEvent("onCheckBox", [id,0,1]);
									}
								}else{
									if(cell.isChecked()){
										cell.setValue(0);
										this.callEvent("onCheckBox", [id,0,0]);
									}
								}
							}
						});
						
						allChkYN = !allChkYN;
						
						break;
				}
			}
		},
		//-------------------------------------------------------------------------------------
		//개통유형
		GRID4 : {
			css : {
				height : "200px",
				width : "90px"
			},
			title : "개통유형",
			header : "선택,개통유형",
			columnIds : "chk,cdNm",
			colAlign : "center,center",
			colTypes : "ch,ro",
			colSorting : "str,str",
			widths : "30,*",
			paging: false
		},
		//-------------------------------------------------------------------------------------
		//약정기간
		GRID5 : {
			css : {
				height : "200px",
				width : "90px"
			},
			title : "약정기간",
			header : "선택,약정기간",
			columnIds : "chk,cdNm",
			colAlign : "center,center",
			colTypes : "ch,ro",
			colSorting : "str,str",
			widths : "30,*",
			paging: false
		},
		//할부개월수
		GRID13 : {
			css : {
				height : "200px",
				width : "90px"
			},
			title : "할부개월수",
			header : "선택,개월수",
			columnIds : "chk,cdNm",
			colAlign : "center,center",
			colTypes : "ch,ro",
			colSorting : "str,str",
			widths : "30,*",
			paging: false
		},
		//-------------------------------------------------------------------------------------
		//대상제품
		GRID6 : {
			css : {
				height : "200px"
			},
			title : "대상제품",
			header : "제품ID,제품코드,제품명,중고여부,신규수수료,MNP수수료,일반기변수수료,우수기변수수료,제품구분코드",
			columnIds : "prdtId,prdtCode,prdtNm,oldYn,newCmsnAmt,mnpCmsnAmt,hcnCmsnAmt,hdnCmsnAmt,prdtIndCd",
			colAlign : "center,left,left,center,right,right,right,right,center",
			colTypes : "ro,ro,ro,ro,ron,ron,ron,ron,ro",
			colSorting : "str,str,str,str,int,int,int,int,str",
			widths : "80,80,120,60,80,80,90,90,90",
			hiddenColumns : [0,8],
			paging: false,
			multiCheckbox : true,
			buttons : {
				hright : {
					btnPrdtExcel : { label : "엑셀다운로드" }
				},
				right : {
					btnAddMdl : { label : "추가" },
					btnDelMdl : { label : "삭제" }
				}
			},
			onButtonClick : function(btnId) {

				var grid = this;
				
				switch(btnId){
					case "btnPrdtExcel" :
						
						if(PAGE.GRID6.getRowsNum() == 0 && PAGE.FORM2.getItemValue("salePlcyCd") == ""){
							mvno.ui.alert("판매정책코드 또는 데이터가 존재하지 않습니다");
							return;
						}else{
							var searchData =  PAGE.FORM2.getFormData(true);
							mvno.cmn.download(ROOT + "/sale/plcyMgmt/getPlcyPrdtListExcel.json?menuId=MSP2002012", true, {postData:searchData});
						}
						break;
						
					// 모델찾기팝업
					case "btnAddMdl" :
						
						if(PAGE.FORM2.getItemValue("cnfmYn") == "Y"){
							mvno.ui.alert("확정취소후 등록가능합니다");
							return;
						}
						
						if(PAGE.FORM2.getItemValue("saleStrtDt") == null || PAGE.FORM2.getItemValue("saleStrtDt") == "") {
							mvno.ui.alert("판매시작일을 입력하세요");
							return;
						}
						var sStrtDt = new Date(PAGE.FORM2.getItemValue("saleStrtDt")).format("yyyymmdd");
						if(sStrtDt < today) {
							mvno.ui.alert("판매시작일은 오늘 이전 날짜를 선택할 수 없습니다");
							return;
						}
						if(Number(PAGE.FORM2.getItemValue("saleStrtTm")) < 0 || Number(PAGE.FORM2.getItemValue("saleStrtTm")) > 235959) {
							mvno.ui.alert("정확한 시작시간(시분초) 을 입력하세요");
							return;
						}
						
						mvno.ui.createForm("FORM3");
						mvno.ui.createGrid("GRID9");
	
						PAGE.FORM3.setFormData(true);
						
						PAGE.FORM3.disableItem("newCmsnAmt");
						PAGE.FORM3.disableItem("mnpCmsnAmt");
						PAGE.FORM3.disableItem("hcnCmsnAmt");
						PAGE.FORM3.disableItem("hdnCmsnAmt");
						
						var operData = PAGE.GRID4.getCheckedRowData();
						if(operData.length < 1){
							mvno.ui.alert("개통유형을 선택하세요");
							return;
						}else{
							for(var i=0; i<operData.length; i++){
								if(operData[i].cdVal == "NAC3"){
									PAGE.FORM3.enableItem("newCmsnAmt");
									PAGE.FORM3.setItemValue("newYn", "Y");
								}else if(operData[i].cdVal == "MNP3"){
									PAGE.FORM3.enableItem("mnpCmsnAmt");
									PAGE.FORM3.setItemValue("mnpYn", "Y");
								}else if(operData[i].cdVal == "HCN3"){
									PAGE.FORM3.enableItem("hcnCmsnAmt");
									PAGE.FORM3.setItemValue("hcnYn", "Y");
								}else if(operData[i].cdVal == "HDN3"){
									PAGE.FORM3.enableItem("hdnCmsnAmt");
									PAGE.FORM3.setItemValue("hdnYn", "Y");
								}
							}
						}
	
						// 대상제품조회
						var data = {
							prdtTypeCd : PAGE.FORM2.getItemValue("plcySctnCd"),
							prdtIndCd : PAGE.FORM2.getItemValue("prdtSctnCd"),
							saleStrtDt : PAGE.FORM2.getItemValue("saleStrtDt", true) + PAGE.FORM2.getItemValue("saleStrtTm"),
							selfOpenYn : PAGE.FORM2.getItemValue("selfOpenYn") == '1' ? 'Y' : 'N'
						}
						
						PAGE.GRID9.list(ROOT + "/sale/plcyMgmt/getPrdtTrgtList.json", data);
						
						PAGE.FORM3.clearChanged();
						
						mvno.ui.popupWindowById("GROUP2", "대상제품등록", 800, 600, {
							onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) {
                           $('#GRID9 input:checkbox').prop('checked',false);
                           
                           return true;
                        }
								$('#GRID9 input:checkbox').prop('checked',false);
								win.closeForce();
							}
						});
						
						break;
						
					case "btnDelMdl" :
						if(PAGE.FORM2.getItemValue("cnfmYn") == "Y"){
							mvno.ui.alert("확정취소후 삭제가능합니다");
							return;
						}
						
						var rowIds = this.getCheckedRows(0);
						
						if(!rowIds){
							mvno.ui.alert("ECMN0003");
						    return;
						}else{
							this.deleteRowByIds(rowIds);

							// [SR-2024-055] 제휴사(위탁온라인, 제휴대리점) 셀프개통URL 제공 및 개발
							if(PAGE.FORM2.getItemValue("plcySctnCd") == "02") {
								var isPossibleSelfOpen = true;
								var rowDataArr = this.getRowData(this.getAllRowIds(), true);
								for (var i = 0; i < rowDataArr.length; i++) {
									if (rowDataArr[i].prdtIndCd == '10' || rowDataArr[i].prdtIndCd == '11') {
										isPossibleSelfOpen = false;
									}
								}
								if (isPossibleSelfOpen) {
									PAGE.FORM2.enableItem("selfOpenYn");
								} else {
									PAGE.FORM2.disableItem("selfOpenYn");
								}
							}

						}
						
						break;
				}
			}
		},
		//-------------------------------------------------------------------------------------
		//ARPU수수료
		GRID7 : {
			css : {
				height : "200px"
			},
			title : "ARPU수수료",
			header : "제품ID,제품코드,요금제코드,요금제그룹,ARPU수수료,중고여부",
			columnIds : "prdtId,prdtCode,rateCd,rateGrpCd,arpuCmsnAmt,oldYn",
			colAlign : "center,left,center,center,right,center",
			colTypes : "ro,ro,ro,ro,ron,ro",
			colSorting : "str,str,str,str,int,str",
			widths : "100,80,80,80,80,60",
			hiddenColumns : [0],
			paging: false,
			multiCheckbox : true,
			buttons : {
				hright : {
					btnArpuExcel : { label : "엑셀다운로드" }
				},
				right : {
					btnAddArpu : { label : "추가" },
					btnDelArpu : { label : "삭제" }
				}
			}
			,onButtonClick: function(btnId){
				var grid = this;
				
				switch(btnId){
					case "btnArpuExcel":
						if(PAGE.GRID7.getRowsNum() == 0 || PAGE.FORM2.getItemValue("salePlcyCd") == ""){
							mvno.ui.alert("판매정책코드 또는 데이터가 존재하지 않습니다");
							return;
						}else{
							var searchData =  PAGE.FORM2.getFormData(true);
							mvno.cmn.download(ROOT + "/sale/plcyMgmt/getPlcyArpuCmsnListExcel.json?menuId=MSP2002012", true, {postData:searchData});
						}
						break;
					
					case "btnAddArpu" :
						if(PAGE.FORM2.getItemValue("cnfmYn") == "Y"){
							mvno.ui.alert("확정취소후 등록가능합니다");
							return;
						}
						
						mvno.ui.createForm("FORM4");
						mvno.ui.createForm("FORM5");
						mvno.ui.createGrid("GRID10");
						
						PAGE.FORM4.clear();
						PAGE.FORM5.clear();
						PAGE.GRID10.clearAll();
						
						//모델선택확인
						PAGE.FORM5.setFormData(true);
	
						var sdata = PAGE.GRID6.getSelectedRowData();
						if(sdata == null){
							mvno.ui.alert("대상제품을 선택하세요");
							return;
						}
						
						PAGE.FORM5.setItemValue("prdtId", sdata.prdtId);
						PAGE.FORM5.setItemValue("prdtCode", sdata.prdtCode);
						PAGE.FORM5.setItemValue("prdtNm", sdata.prdtNm);
						PAGE.FORM5.setItemValue("oldYn", sdata.oldYn);
						//PAGE.FORM5.setItemValue("oldNm", sdata.oldYn == "Y" ? "중고" : "신품");
						
						mvno.ui.popupWindowById("GROUP3", "ARPU수수료등록", 800, 600, {
							onClose: function(win) {
								if (! PAGE.FORM5.isChanged()) {
                           $('#GRID10 input:checkbox').prop('checked',false);
                           
                           return true;
                        }
                        $('#GRID10 input:checkbox').prop('checked',false);
                        win.closeForce();
							}
						});
						
						break;
					
					case "btnDelArpu" :
						if(PAGE.FORM2.getItemValue("cnfmYn") == "Y"){
							mvno.ui.alert("확정취소후 삭제가능합니다");
							return;
						}
						
						var rowIds = this.getCheckedRows(0);
						
						if(!rowIds){
							mvno.ui.alert("ECMN0003");
						    return;
						}else{
							this.deleteRowByIds(rowIds);
						}
						
						break;
				
				}
			}
		},
		//-------------------------------------------------------------------------------------
		//판매정책상세
		GRID8 : {
			css : {
				height : "330px"
			},
			title : "판매정책상세내역 <font size='2' color='red'>( TIME OUT 방지를 위하여 상세내역 일부만 조회합니다 )</font>",
			header : "판매정책코드,조직명,제품명,중고여부,요금제,약정기간,개통유형,출고가,공시지원금,대리점보조금MAX,할부원금,할부수수료,할인유형,기본료,기본할인금액,추가할인금액",
			columnIds : "salePlcyCd,orgnNm,prdtNm,oldYn,rateNm,agrmTrm,operTypeNm,hndstAmt,subsdAmt,agncySubsdMax,instAmt,instCmsn,sprtTpNm,baseAmt,dcAmt,addDcAmt",
			colAlign : "center,left,left,center,left,center,center,right,right,right,right,right,center,right,right,right",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ron,ron,ron,ron,ron,ro,ron,ron,ron",
			colSorting : "str,str,str,str,str,str,str,int,int,int,int,int,str,int,int,int",
			widths : "100,120,100,60,180,60,70,70,70,110,70,70,80,70,80,80",
			//paging : true,
			pageSize : 50,
			//multiCheckbox : true,
			hiddenColumns : [0],
			buttons: {
				hright : {
					btnPlcy: {label: "정책생성"}
				},
				left:{
					btnInit: {label: "초기화"}
				},
				center: {
					btnClose : { label : "닫기" }
				},
				right:{
					btnConfirm : { label : "확정" }
					, btnCancel : { label : "확정취소" }
					, btnDelete : { label : "정책삭제"}
					, btnPlcyClose : { label : "정책종료"}
					//, btnPlcyCopy : { label : "정책복사"}
				}
			},
			onButtonClick: function(btnId){
				var form = PAGE.FORM2;
				
				switch(btnId){
// 					case "btnPlcyCopy" :
// 						//mvno.ui.alert("정책코드" + PAGE.FORM2.getItemValue("salePlcyCd"));
// 						mvno.ui.createForm("FORM6");
						
// 						var edate = new Date();
// 						var endDt = new Date(edate.setDate(edate.getDate() + 7)).format("yyyymmdd");
						
// 						PAGE.FORM6.setFormData(true);
// 						PAGE.FORM6.setItemValue("salePlcyCd", form.getItemValue("salePlcyCd"));
// 						PAGE.FORM6.setItemValue("salePlcyNm", form.getItemValue("salePlcyNm"));
// 						PAGE.FORM6.setItemValue("saleStrtDt", form.getItemValue("saleStrtDt"));
// 						PAGE.FORM6.setItemValue("saleStrtTm", form.getItemValue("saleStrtTm"));
// 						PAGE.FORM6.setItemValue("saleEndDt", form.getItemValue("saleEndDt"));
// 						PAGE.FORM6.setItemValue("saleEndTm", form.getItemValue("saleEndTm"));
// 						PAGE.FORM6.setItemValue("newPlcyTypeCd", form.getItemValue("plcyTypeCd"));
// 						PAGE.FORM6.setItemValue("newSaleStrtDt", today);
// 						PAGE.FORM6.setItemValue("newSaleStrtTm", "000000");
// 						PAGE.FORM6.setItemValue("newSaleEndDt", endDt);
// 						PAGE.FORM6.setItemValue("newSaleEndTm", "235959");
						
// 						PAGE.FORM6.clearChanged();
						
// 						mvno.ui.popupWindowById("FORM6", "정책복사", 720, 350, {
// 							onClose: function(win) {
// 								if (! PAGE.FORM6.isChanged()) return true;
// 								mvno.ui.confirm("CCMN0005", function(){
// 									win.closeForce();
// 								});
// 							}
// 						});
						
// 						break;
						
					case "btnInit" :
						if(PAGE.GRID8.getRowsNum() == 0) {
							return;
						}
						
						if(PAGE.FORM2.getItemValue("cnfmYn") == "Y") {
							mvno.ui.alert("확정취소후 초기화가 가능합니다");
							return;
						}
						
						mvno.ui.confirm("판매정책 상세내역을 초기화하시겠습니까?", function() {
							
							//if(form.getItemValue("flag") != "U"){
								var data = PAGE.GRID8.getRowData(PAGE.GRID8.getRowId(0));
								mvno.cmn.ajax(ROOT + "/sale/plcyMgmt/initSalePlcyMst.json", data, function(){
									PAGE.GRID8.clearAll();
									form.clearChanged();
									mvno.ui.closeWindowById("GROUP1");
									mvno.ui.notify("판매정책 상세내역 초기화가 완료되면 SMS가 발송됩니다.");
									PAGE.GRID1.refresh();
									
								});
								
							//}
							
						});
						break;
					
					case "btnClose" :
						mvno.ui.closeWindowById("GROUP1");
						break;
						
					case "btnPlcyClose" :
						if(PAGE.FORM2.getItemValue("salePlcyCd") == ""){
							mvno.ui.alert("종료할 대상 정책이 존재하지 않습니다");
							return;
						}
						
						var endDt = PAGE.FORM2.getItemValue("saleEndDt", true); //날짜값 가져올때  true 넣어줘야함.
						if(endDt == ""){
							mvno.ui.alert("정책종료일을 입력하세요");
							return;
						}
						
						if(endDt < today){
							mvno.ui.alert("정책종료일은 오늘 이후 날짜를 선택하세요");
							return;
						}
						
						mvno.cmn.ajax4confirm("정책을 종료하시겠습니까?", ROOT + "/sale/plcyMgmt/updatePlcyMgmtClose.json", PAGE.FORM2, function(result) {
							//mvno.ui.closeWindowById(form, true);
							form.clearChanged();
							mvno.ui.closeWindowById("GROUP1");
							mvno.ui.notify("NCMN0004");
							PAGE.GRID1.refresh();
						});
						
						break;
					
					case "btnConfirm" :
						if(PAGE.FORM2.getItemValue("cnfmYn") == "Y"){
							mvno.ui.alert("이미 확정상태입니다");
							return;
						}
						
						var data = PAGE.GRID8.getRowData(PAGE.GRID8.getRowId(0));
						//console.log("data=" + data);
						
						var salePlcyCd = "";
						//if(data == null && PAGE.FORM2.getItemValue("salePlcyCd") == ""){
						if(data == null) {
							mvno.ui.alert("확정할 대상 정책이 존재하지 않습니다");
							return;
						}else{
							salePlcyCd = PAGE.FORM2.getItemValue("salePlcyCd") == "" ? data.salePlcyCd : PAGE.FORM2.getItemValue("salePlcyCd");
						}
						var sdata = {
							salePlcyCd: salePlcyCd 
						}
						
						mvno.cmn.ajax4confirm("정책을 확정하시겠습니까?", ROOT + "/sale/plcyMgmt/setPlcyConfirm.json", sdata, function(result) {
// 							mvno.ui.closeWindowById(form, true);
							form.clearChanged();
							mvno.ui.closeWindowById("GROUP1");
							mvno.ui.notify("NCMN0004");
							PAGE.GRID1.refresh();
						});
						break;
						
					case "btnCancel" :
						if(PAGE.FORM2.getItemValue("salePlcyCd") == ""){
							mvno.ui.alert("취소할 대상 정책이 존재하지 않습니다");
							return;
						}
						
						mvno.cmn.ajax4confirm("확정을 취소하시겠습니까?", ROOT + "/sale/plcyMgmt/setPlcyCancel.json", PAGE.FORM2, function(result) {
// 							mvno.ui.closeWindowById(form, true);
							form.clearChanged();
							mvno.ui.closeWindowById("GROUP1");
							mvno.ui.notify("NCMN0004");
							PAGE.GRID1.refresh();
						});
						break;
					
					case "btnDelete" :
						if(PAGE.FORM2.getItemValue("cnfmYn") == "Y"){
							mvno.ui.alert("확정취소후 삭제 가능합니다");
							return;
						}
						
						mvno.cmn.ajax4confirm("정책을 삭제하시겠습니까?", ROOT + "/sale/plcyMgmt/deleteSalePlcyMst.json", PAGE.FORM2, function(result){
							//mvno.ui.closeWindowById(form, true);
							form.clearChanged();
							mvno.ui.closeWindowById("GROUP1");
							mvno.ui.notify("정책삭제가 완료되면 SMS가 발송됩니다.");
							PAGE.GRID1.refresh();
							
						});
						break;
						
					case "btnPlcy" :
						if(PAGE.FORM2.getItemValue("cnfmYn") == "Y") {
							mvno.ui.alert("확정취소후 정책을 생성할 수 있습니다");
							return;
						}
						
						if(PAGE.GRID8.getRowsNum() > 0) {
							mvno.ui.alert("상세내역 초기화 후 정책생성 가능합니다");
							return;
						}
						
						if(form.getItemValue("plcyTypeCd") == null || form.getItemValue("plcyTypeCd") == "") {
							mvno.ui.alert("정책유형을 선택하세요");
							return;
						}
						
						if(form.getItemValue("salePlcyNm") == null || form.getItemValue("salePlcyNm") == "") {
							mvno.ui.alert("판매정책명을 입력하세요");
							return;
						}
						
						if(form.getItemValue("saleStrtDt") == null || form.getItemValue("saleStrtDt") == "") {
							mvno.ui.alert("판매시작일을 입력하세요");
							return;
						}
						
						if(form.getItemValue("saleEndDt") == null || form.getItemValue("saleEndDt") == "") {
							mvno.ui.alert("판매종료일을 입력하세요");
							return;
						}
						
						var sStrtDt = new Date(form.getItemValue("saleStrtDt")).format("yyyymmdd");
						var sEndDt = new Date(form.getItemValue("saleEndDt")).format("yyyymmdd");
						
						if(sStrtDt > sEndDt) {
							mvno.ui.alert("판매종료일이 판매시작일 전입니다. 판매종료일을 변경하세요");
							return;
						}
						
						if(sStrtDt < today) {
							mvno.ui.alert("판매시작일은 오늘 이전 날짜를 선택할 수 없습니다");
							return;
						}
						
						if(Number(form.getItemValue("newSaleStrtDtm")) < 0 || Number(form.getItemValue("newSaleStrtTm")) > 235959) {
							mvno.ui.alert("정확한 시작시간(시분초) 을 입력하세요");
							return;
						}
						
						if(Number(form.getItemValue("newSaleEndDtm")) < 0 || Number(form.getItemValue("newSaleEndTm")) > 235959) {
							mvno.ui.alert("정확한 종료시간(시분초) 을 입력하세요");
							return;
						}
						
						if(form.getItemValue("plcySctnCd") == "01" && (form.getItemValue("instRate") == null || form.getItemValue("instRate") == "")) {
							mvno.ui.alert("할부수수료율을 입력하세요");
							return;
						}
						
// 						if(form.getItemValue("plcySctnCd") == "01" && (form.getItemValue("sprtTp") == null || form.getItemValue("sprtTp") == "")) {
// 							mvno.ui.alert("할인유형을 선택하세요");
// 							return;
// 						}
						
						// 2016-02-28, 심플할인 추가로 할인유형 선택 validation 추가
						// 정책구분 : 단말
						if(form.getItemValue("plcySctnCd") == "01"){
							if(form.getItemValue("sprtTp") == null || form.getItemValue("sprtTp") == "SM") {
								mvno.ui.alert("단말정책은 단말할인/요금할인 선택가능합니다.");
								return;
							}
						}else{
							if(form.getItemValue("sprtTp") == "KD" || form.getItemValue("sprtTp") == "PM"){
								mvno.ui.alert("유심정책은 심플할인 선택가능합니다.");
								return;
							}
						}

						// [SR-2024-055] 제휴사(위탁온라인, 제휴대리점) 셀프개통URL 제공 및 개발
						if ( form.getItemValue("selfOpenYn") ) {
							if ( form.getItemValue("plcySctnCd") == "01" ) {
								mvno.ui.alert("셀프개통 정책은 단말 정책으로 생성할 수 없습니다.");
								return;
							}

							var containsEsim = false;
							var rowDataArr = PAGE.GRID6.getRowData(PAGE.GRID6.getAllRowIds(), true);
							for (var i = 0; i < rowDataArr.length; i++) {
								if (rowDataArr[i].prdtIndCd == '10' || rowDataArr[i].prdtIndCd == '11') {
									containsEsim = true;
									break;
								}
							}

							if ( containsEsim ) {
								mvno.ui.alert("셀프개통 정책은 eSIM 제품을 선택할 수 없습니다.");
								return;
							}
						}
						
						var orgnIds = "";
						var data = PAGE.GRID2.getCheckedRowData();
						if(data.length < 1) {
							mvno.ui.alert("대상조직을 선택하세요");
							return;
						} else {
							for(var i=0; i<data.length; i++) {
								orgnIds += data[i].orgnId + "&";
							}
						}
						
						var rateCds = "";
						var data = PAGE.GRID3.getCheckedRowData();
						if(data.length < 1) {
							mvno.ui.alert("대상요금제를 선택하세요");
							return;
						} else {
							for(var i=0; i<data.length; i++) {
								rateCds += data[i].rateCd + "&";
							}
						}
						
						var strNewYn = "N";
						var strMnpYn = "N";
						var strHcnYn = "N";
						var strHdnYn = "N";
						
						var operIds = "";
						var data = PAGE.GRID4.getCheckedRowData();
						if(data.length < 1) {
							mvno.ui.alert("개통유형을 선택하세요");
							return;
						} else {
							for(var i=0; i<data.length; i++) {
								if(data[i].cdVal == "NAC3") strNewYn = "Y";
								if(data[i].cdVal == "MNP3") strMnpYn = "Y";
								if(data[i].cdVal == "HCN3") strHcnYn = "Y";
								if(data[i].cdVal == "HDN3") strHdnYn = "Y";
								
								operIds += data[i].cdVal + "&";
							}
						}
						
						var agrmIds = "";
						var data = PAGE.GRID5.getCheckedRowData();
						if(data.length < 1 && PAGE.FORM2.getItemValue("plcySctnCd") == "01") {
							mvno.ui.alert("약정기간을 선택하세요");
							return;
						} else {
							for(var i=0; i<data.length; i++) {
								agrmIds += data[i].cdVal + "&";
							}
						}
						
						var instIds = "";
						var data = PAGE.GRID13.getCheckedRowData();
						if(data.length < 1 && PAGE.FORM2.getItemValue("plcySctnCd") == "01") {
							mvno.ui.alert("할부개월수를 선택하세요");
							return;
						} else {
							for(var i=0; i<data.length; i++) {
								instIds += data[i].cdVal + "&";
							}
						}
						
						var prdtIds = "";
						var data = PAGE.GRID6.getCheckedRowData();
						if(data.length < 1) {
							mvno.ui.alert("대상제품을 선택하세요");
							return;
						} else {
							for(var i=0; i<data.length; i++) {
								var newCmsnAmt = data[i].newCmsnAmt == "" ? "0" : data[i].newCmsnAmt; 
								var mnpCmsnAmt = data[i].mnpCmsnAmt == "" ? "0" : data[i].mnpCmsnAmt; 
								var hcnCmsnAmt = data[i].hcnCmsnAmt == "" ? "0" : data[i].hcnCmsnAmt;
								var hdnCmsnAmt = data[i].hdnCmsnAmt == "" ? "0" : data[i].hdnCmsnAmt;
								
								prdtIds += data[i].prdtId 
										+ ":" + data[i].oldYn 
										+ ":" + newCmsnAmt
										+ ":" + mnpCmsnAmt
										+ ":" + hcnCmsnAmt 
										+ ":" + hdnCmsnAmt 
										+ "&";
							}
						}
						
						var arpuIds = "";
						var data = PAGE.GRID7.getCheckedRowData();
						if(PAGE.GRID7.getRowsNum() > 0 && data.length < 1) {
							mvno.ui.confirm("ARPU 수수료 목록을 선택하세요.");
							return;
						} else {
							for(var i=0; i<data.length; i++) {
								if(data[i].prdtId == "" || data[i].oldYn == "") {
									mvno.ui.alert("대상모델을 선택후 ARPU수수료를 입력하세요");
									return;
								}
								
								var arpuCmsnAmt = data[i].arpuCmsnAmt == "" ? "0" : data[i].arpuCmsnAmt; 
									
								arpuIds += data[i].rateGrpCd
										+ ":" + data[i].rateCd
										+ ":" + arpuCmsnAmt
										+ ":" + data[i].prdtId
										+ ":" + data[i].oldYn
										+ ":" + PAGE.FORM2.getItemValue("plcySctnCd")
										+ ":" + PAGE.FORM2.getItemValue("prdtSctnCd")
										+ "&";
							}
							
						}
						
						PAGE.GRID8.clearAll();
						
						var sdata = {
								flag : form.getItemValue("flag"),
								salePlcyNm : form.getItemValue("salePlcyNm"),
								saleStrtDt : sStrtDt,
								saleStrtTm : form.getItemValue("saleStrtTm"),
								saleEndDt : sEndDt,
								saleEndTm : form.getItemValue("saleEndTm"),
								plcyTypeCd : form.getItemValue("plcyTypeCd"),
								salePlcyCd : form.getItemValue("salePlcyCd"),
								orgnType : form.getItemValue("orgnType"),
								applSctnCd : form.getItemValue("applSctnCd"),
								plcySctnCd : form.getItemValue("plcySctnCd"),
								prdtSctnCd : form.getItemValue("prdtSctnCd"),
								sprtTp : form.getItemValue("sprtTp"),
								newYn : strNewYn,
								mnpYn : strMnpYn,
								hcnYn : strHcnYn,
								hdnYn : strHdnYn,
								instRate : form.getItemValue("instRate"),
								orgnId : orgnIds.substring(0, orgnIds.length - 1),
								rateCd : rateCds.substring(0, rateCds.length - 1),
								operType : operIds.substring(0, operIds.length - 1),
								agrmTrm : agrmIds.substring(0, agrmIds.length - 1),
								instTrm : instIds.substring(0, instIds.length - 1),
								prdtId : prdtIds.substring(0, prdtIds.length - 1),
								arpuId : arpuIds.substring(0, arpuIds.length - 1),
								selfOpenYn : form.getItemValue("selfOpenYn") ? 'Y' : 'N'
						}
						
						mvno.cmn.ajax4confirm("정책생성은 시간이 다소 걸릴 수 있습니다. 정책을 생성하시겠습니까?", ROOT + "/sale/plcyMgmt/getSalePlcyTrgtList.json", sdata, function(result) {
							form.clearChanged();
							mvno.ui.closeWindowById("GROUP1");
							mvno.ui.notify("정책생성이 완료되면 SMS가 발송됩니다.");
							PAGE.GRID1.refresh();
						});
						
						/* PAGE.GRID8.list(ROOT + "/sale/plcyMgmt/getSalePlcyTrgtList.json", sdata, {callback:function() {
							PAGE.FORM2.setItemValue("flag", "U");
							PAGE.FORM2.setItemValue("salePlcyCd", PAGE.GRID8.getRowData(PAGE.GRID8.getRowId(0)).salePlcyCd);
						}}); */
						
						break;
				}
			}
		},
		//-------------------------------------------------------------------------------------
		//대상모델
		GRID9 : {
			css : {
				height : "300px"
			},
			title : "판매정책대상제품",
			header : "제품ID,제품코드,제품명,중고여부,출고가,신규수수료,MNP수수료,일반기변수수료,우수기변수수료,제품구분코드",
			columnIds : "prdtId,prdtCode,prdtNm,oldYn,outUnitPric,newCmsnAmt,mnpCmsnAmt,hcnCmsnAmt,hdnCmsnAmt,prdtIndCd",
			colAlign : "center,left,left,center,right,right,right,right,right,center",
			colTypes : "ro,ro,ro,ro,ron,ron,ron,ron,ron,ro",
			colSorting : "str,str,str,str,int,int,int,int,int,str",
			widths : "70,120,160,60,70,80,80,90,90,90",
			hiddenColumns : [9],
			paging: false,
			multiCheckbox : true
		},
		FORM3 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0}
				,{type: "block", list: [
					 {type: "input", name: "newCmsnAmt", label: "신규수수료", labelWidth:100, width: 120, offsetLeft: 20, validate:"ValidInteger", numberFormat:"0,000,000,000", maxLength:10}
					,{type: "newcolumn"}
					,{type: "input", name: "mnpCmsnAmt", label: "MNP수수료", labelWidth:100, width: 120, offsetLeft:30, validate:"ValidInteger", numberFormat:"0,000,000,000", maxLength:10}
				]}
				,{type: "block", list: [
   					{type: "input", name: "hcnCmsnAmt", label: "일반기변수수료", labelWidth:100, width: 120, offsetLeft:20, validate:"ValidInteger", numberFormat:"0,000,000,000", maxLength:10}
   					,{type: "newcolumn"}
   					,{type: "input", name: "hdnCmsnAmt", label: "우수기변수수료", labelWidth:100, width: 120, offsetLeft:30, validate:"ValidInteger", numberFormat:"0,000,000,000", maxLength:10}
   					,{type: "newcolumn"}
   					,{type: "button", name: "btnAddVal02", value: "적용", offsetLeft: 150}
   				]}
				,{type: "hidden", name: "newYn"}
				,{type: "hidden", name: "mnpYn"}
				,{type: "hidden", name: "hcnYn"}
				,{type: "hidden", name: "hdnYn"}
			],
			buttons :  {
				center : {
					btnAddPrdt : {label : "저장"},
					btnClose02 : {label : "닫기"} 
				}
			},
			onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnAddVal02":
						
						if(form.getItemValue("newYn") == "Y" && ( form.getItemValue("newCmsnAmt") == "" || Number(form.getItemValue("newCmsnAmt")) < 0 )){
							mvno.ui.alert("신규수수료를 입력하세요");
							return;
						}
						
						if(form.getItemValue("mnpYn") == "Y" && ( form.getItemValue("mnpCmsnAmt") == "" || Number(form.getItemValue("mnpCmsnAmt")) < 0 )){
							mvno.ui.alert("번호이동수수료를 입력하세요");
							return;
						}
						
						if(form.getItemValue("hcnYn") == "Y" && ( form.getItemValue("hcnCmsnAmt") == "" || Number(form.getItemValue("hcnCmsnAmt")) < 0 )){
							mvno.ui.alert("일반기변수수료를 입력하세요");
							return;
						}
						
						if(form.getItemValue("hdnYn") == "Y" && ( form.getItemValue("hdnCmsnAmt") == "" || Number(form.getItemValue("hdnCmsnAmt")) < 0 )){
							mvno.ui.alert("우수기변수수료를 입력하세요");
							return;
						}
						
						var sdata = PAGE.GRID9.getCheckedRowData();
						var rowIds = PAGE.GRID9.getCheckedRows(0).split(",");
						//console.log("rowIds=" + rowIds);
						if(sdata.length < 1){
							mvno.ui.alert("수수료 대상 제품을 선택하세요");
							return;
						}else{
							for(var i=0; i<sdata.length; i++){
								//console.log("rowId=" + rowIds[i]);
								var data = {
									prdtId : sdata[i].prdtId,
									prdtCode : sdata[i].prdtCode,
									prdtNm : sdata[i].prdtNm,
									oldYn : sdata[i].oldYn,
									outUnitPric : sdata[i].outUnitPric,
									newCmsnAmt : this.getItemValue("newCmsnAmt"),
									mnpCmsnAmt : this.getItemValue("mnpCmsnAmt"),
									hcnCmsnAmt : this.getItemValue("hcnCmsnAmt"),
									hdnCmsnAmt : this.getItemValue("hdnCmsnAmt")
								}
								
								PAGE.GRID9.setRowData(rowIds[i], data);
								PAGE.GRID9.cells(rowIds[i],0).setChecked(true);
							}
						}
						
						break;
					
					case "btnAddPrdt" :
						var sdata = PAGE.GRID9.getCheckedRowData();
						
						if(sdata.length < 1){
							mvno.ui.alert("대상제품을 선택하세요");
							return;
						}else{
							for(var i=0; i<sdata.length; i++){
								PAGE.GRID6.appendRow(sdata[i]);
							}

							// [SR-2024-055] 제휴사(위탁온라인, 제휴대리점) 셀프개통URL 제공 및 개발
							if(PAGE.FORM2.getItemValue("plcySctnCd") == "02") {
								var isPossibleSelfOpen = true;
								var rowDataArr = PAGE.GRID6.getRowData(PAGE.GRID6.getAllRowIds(), true);
								for (var i = 0; i < rowDataArr.length; i++) {
									if (rowDataArr[i].prdtIndCd == '10' || rowDataArr[i].prdtIndCd == '11') {
										isPossibleSelfOpen = false;
										break;
									}
								}
								if (isPossibleSelfOpen) {
									PAGE.FORM2.enableItem("selfOpenYn");
								} else {
									PAGE.FORM2.disableItem("selfOpenYn");
								}
							}

							mvno.ui.closeWindowById("GROUP2");
						}
						
						PAGE.GRID9.clearAll();
						
						break;
						
					case "btnClose02" :
						//console.log("aaa-");
						mvno.ui.closeWindowById("GROUP2");
						PAGE.GRID9.clearAll();
						break;
				}
	
			}
		},
		//-------------------------------------------------------------------------------------
		//요금제조회
		FORM4 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0}
				,{type: "block", list: [
					{type: "select", name: "searchCd", label: "조회구분", offsetLeft: 20, options:[{text:"요금제명", value:"20"}, {text:"요금제그룹", value:"10"}]}
					,{type: "newcolumn"}
					,{type: "input", name: "searchVal", label: "", width:200}
					,{type: "hidden", name: "payClCd", value:"PO"}
					,{type: "hidden", name: "searchDataType"}
					,{type: "hidden", name: "searchPlcySctnCd"}
				]},
				{type: "newcolumn"},
				{type: "button", value: "조회", name: "btnSearch" , className:"btn-search1"}
			],
			onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnSearch":
						//요금제 구분하여 해당요금제만 조회
						PAGE.FORM4.setItemValue("searchDataType", PAGE.FORM2.getItemValue("prdtSctnCd"));
						PAGE.FORM4.setItemValue("searchPlcySctnCd", PAGE.FORM2.getItemValue("plcySctnCd"));
						
						PAGE.GRID10.list(ROOT + "/sale/plcyMgmt/getRateArpuTrgtList.json", form);
						break;
				}
	
			}
		},
		//-------------------------------------------------------------------------------------
		//요금제조회
		GRID10 : {
			css : {
				height : "300px"
			},
			//title : "ARPU등록",
			header : "요금제그룹,요금제코드,요금제/그룹명,ARPU수수료,제품ID,제품코드,중고여부",
			columnIds : "rateGrpCd,rateCd,rateNm,arpuCmsnAmt,prdtId,prdtCode,oldYn",
			colAlign : "center,center,left,right,center,left,center",
			colTypes : "ro,ro,ro,ron,ro,ro,ro",
			colSorting : "str,str,str,int,str,str,str",
			widths : "80,80,200,80,80,90,80",
			//hiddenColumns : [4,5,6],
			paging: false,
			multiCheckbox : true
		},
		//-------------------------------------------------------------------------------------
		//APRU입력
		FORM5 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}              
				,{type: "block", list: [
					{type: "input", name: "prdtId", label: "제품정보", width: 50, offsetLeft: 20, readonly: true}
					,{type: "newcolumn"},
					,{type: "input", name: "prdtCode", label: "", width: 80, readonly: true}
					,{type: "newcolumn"},
					,{type: "input", name: "prdtNm", label: "", width: 150, readonly: true}
					,{type: "newcolumn"},
					,{type: "input", name: "oldYn", label: "", width: 20, readonly: true}
					,{type: "newcolumn"},
					,{type: "input", name: "cmsnAmt", label: "ARPU수수료", width: 50, labelWidth: 80, offsetLeft: 40, validate:"ValidInteger", numberFormat:"0,000,000,000", maxLength:10}
				]},
				{type: "newcolumn"},
				{type: "button", value: "적용", name: "btnAddVal03", offsetLeft: 30}
			]
			,buttons: {
				center:{
					btnAddCmsn: { label: "저장" }
					,btnClose03: { label: "닫기" }
				}
			}
			,onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnAddVal03" :
						if(form.getItemValue("cmsnAmt") == "" || Number(form.getItemValue("cmsnAmt")) < 0){
							mvno.ui.alert("ARPU수수료를 입력하세요");
							return;
						}
						
						var sdata = PAGE.GRID10.getCheckedRowData();
						var rowIds = PAGE.GRID10.getCheckedRows(0).split(",");
						//console.log("rowIds=" + rowIds);
						if(sdata.length < 1){
							mvno.ui.alert("ARPU 수수료 적용대상을 선택하세요");
							return;
						}else{
							for(var i=0; i<sdata.length; i++){
								//console.log("rowId=" + rowIds[i]);
								var data = {
									rateGrpCd : sdata[i].rateGrpCd,
									rateCd : sdata[i].rateCd,
									rateNm : sdata[i].rateNm,
									arpuCmsnAmt : this.getItemValue("cmsnAmt"),
									prdtId : this.getItemValue("prdtId"),
									prdtCode : this.getItemValue("prdtCode"),
									oldYn : this.getItemValue("oldYn")
								}
								
								PAGE.GRID10.setRowData(rowIds[i], data);
								PAGE.GRID10.cells(rowIds[i],0).setChecked(true);
							}
						}
						
						break;
					
					case "btnAddCmsn" :
						var sdata = PAGE.GRID10.getCheckedRowData();
						
						if(sdata.length < 1){
							mvno.ui.alert("적용대상을 선택하세요");
							return;
						}else{
							var chkARPU = true;
							for(var i=0; i<sdata.length; i++){
								var data = {
										prdtId : sdata[i].prdtId,
										prdtCode : sdata[i].prdtCode,
										oldYn : sdata[i].oldYn,
										rateGrpCd : sdata[i].rateGrpCd,
										rateCd : sdata[i].rateCd,
										arpuCmsnAmt : sdata[i].arpuCmsnAmt
								}
								
								if(data.prdtId != "") {
									PAGE.GRID7.appendRow(data);
									if (chkARPU) chkARPU = false;
								}
								//PAGE.GRID7.appendRow(sdata[i]);
							}
							if (chkARPU) {
								mvno.ui.alert("ARPU수수료가 적용된 항목이 없습니다");
								return;
							}
							PAGE.GRID7.checkAll(true);
							mvno.ui.closeWindowById("GROUP3");
						}
						
						break;
					case "btnClose03" :
						mvno.ui.closeWindowById("GROUP3");
						break;
				}
			}
		},
		// --------------------------------------------------------------------------
		//대리점추가
		GRID11 : {
			css : {
				height : "350px"
			},
			title:"대리점추가",
			header : "정책코드,조직ID,대리점명,유형,할인유형",
			columnIds : "salePlcyCd,orgnId,orgnNm,typeNm,sprtTp",
			colAlign : "center,center,left,center,center",
			colTypes : "ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str",
			widths : "100,100,180,94,50",
			paging: false,
			hiddenColumns : [0,4],
			multiCheckbox : true,
			buttons : {
				center : {
					btnSaveAgncy : { label : "추가" },
					btnCloseAgncy : { label : "닫기" }
				}
			},
			onButtonClick:function(btnId) {
				var form = this;
				
				switch(btnId) {
				
					case "btnSaveAgncy" :
						var rowIds = this.getCheckedRows(0);
						
						if(!rowIds) {
							mvno.ui.alert("ECMN0003");
							return;
						} else {
							
							var sdata = this.classifyRowsFromIds(rowIds);
							mvno.ui.confirm("[ " + this.getCheckedRowData().length + " ] 대리점을 추가하시겠습니까?", function() {
								var agencyUrl = ROOT + "/sale/plcyMgmt/setPlcyAgncyAdd.json";
								if(agencyType == "addPreAgency") {
									agencyUrl = ROOT + "/sale/plcyMgmt/setPlcyPreAgncyAdd.json";
								}
								mvno.cmn.ajax4json(agencyUrl, sdata, function() {
									mvno.ui.notify("NCMN0006");
									PAGE.GRID11.refresh();
								});
							});
						}
						break;
						
					case "btnCloseAgncy" :
						mvno.ui.closeWindowById("GRID11");
						break;
				
				}
				
			}
		},
		//-------------------------------------------------------------------------------------
		//정책복사
		FORM6 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:100, blockOffset:0}
				,{type: 'fieldset', label: '대상 정책',  offsetLeft:20, list:[
					{type: "block", list: [
						{type: "input", name: "salePlcyCd", label: "정책코드", width: 150, offsetLeft: 10, disabled:true}
						,{type: "newcolumn"},
						,{type: "input", name: "salePlcyNm", width: 230, disabled:true}
					]}
					,{type: "block", list: [
						{type: "calendar", name: "saleStrtDt", label: "판매시작일시", width: 100, dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", validate: "NotEmpty", required: true, offsetLeft: 10, disabled:true},
						{type: "newcolumn"},
						{type: "input", name: "saleStrtTm", width: 50, validate:"ValidInteger", maxLength:6, disabled:true},
						{type: "newcolumn"},
						{type: "calendar", name: "saleEndDt", label: "판매종료일시", width: 100, dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", validate: "NotEmpty", required: true, offsetLeft: 26, disabled:true},
						{type: "newcolumn"},
						{type: "input", name: "saleEndTm", width: 50, validate:"ValidInteger", maxLength:6, disabled:true}
					]}
				]}
				,{type: 'fieldset', label: '신규 정책', offsetLeft:20, list:[
					{type: "block", list: [
						{type: "select", name: "newPlcyTypeCd", label: "정책유형", width:100, offsetLeft:10, required:true},
						,{type: "newcolumn"},
						,{type: "input", name: "newSalePlcyNm", label: "정책명", width: 154, offsetLeft:80, validate: "NotEmpty", maxLength: 30, required:true}
					]}
					,{type: "block", list: [
						{type: "calendar", name: "newSaleStrtDt", label: "판매시작일시", width: 100, dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", validate: "NotEmpty", required: true, offsetLeft: 10},
						{type: "newcolumn"},
						{type: "input", name: "newSaleStrtTm", width: 50, validate:"ValidInteger", maxLength:6, required:true},
						{type: "newcolumn"},
						{type: "calendar", name: "newSaleEndDt", label: "판매종료일시", width: 100, dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", validate: "NotEmpty", required: true, offsetLeft: 26},
						{type: "newcolumn"},
						{type: "input", name: "newSaleEndTm", width: 50, validate:"ValidInteger", maxLength:6, required:true}
					]}
				]}
				//,{input:"hidden", name:"plcyTypeCd"}
			]
			,buttons: {
				center: {
					btnCopy: { label: "저장" }
					,btnClose06: { label: "닫기" }
				}
			}
			,onChange : function(name, value) {
				var form = this;
				
				switch(name) {
					case "newSaleStrtDt":
						PAGE.FORM6.setItemValue("newSaleStrtTm", "000000");
						break;
					case "newSaleEndDt":
						PAGE.FORM6.setItemValue("newSaleEndTm", "235959");
						break;
				}
			}
			,onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnCopy" :
						//console.log(form.getItemValue("plcyTypeCd") + "-" + form.getItemValue("newPlcyTypeCd"));
						
						var strtDt = new Date(form.getItemValue("newSaleStrtDt")).format("yyyymmdd");
						if(strtDt < today) {
							mvno.ui.alert("정책시작일자는 현재일 이후로 가능합니다");
							return;
						}
						
						if(Number(form.getItemValue("newSaleStrtTm")) < 0 || Number(form.getItemValue("newSaleStrtTm")) > 235959) {
							mvno.ui.alert("정확한 시작시간(시분초) 을 입력하세요");
							return;
						}
						
						if(Number(form.getItemValue("newSaleEndTm")) < 0 || Number(form.getItemValue("newSaleEndTm")) > 235959) {
							mvno.ui.alert("정확한 종료시간(시분초) 을 입력하세요");
							return;
						}
						
						if(form.getItemValue("newSaleStrtDt", true) + "" + form.getItemValue("newSaleStrtTm")
								> form.getItemValue("newSaleEndDt", true) + "" + form.getItemValue("newSaleEndTm")) {
							mvno.ui.alert("종료일자가 시작일자 이전입니다");
							return;
						}
						
						mvno.cmn.ajax4confirm("정책을 복사하시겠습니까?", ROOT + "/sale/plcyMgmt/setPlcyCopy.json", form, function(result) {
							form.clearChanged();
							mvno.ui.closeWindowById("FORM6");
							mvno.ui.notify("정책복사가 완료되면 SMS가 발송됩니다.");
							PAGE.GRID1.refresh();
							/* 
							mvno.ui.closeWindowById(form, true);
							mvno.ui.notify("NCMN0001");
							PAGE.GRID1.refresh(); */
						});
						
						break;
					
					case "btnClose06" :
						mvno.ui.closeWindowById("FORM6");
						break;
				}
			}
		},
		// --------------------------------------------------------------------------
		//요금제추가
		GRID12 : {
			css : {
				height : "350px"
			},
			title:"요금제추가",
			header : "정책코드,요금제그룹,요금제코드,요금제명,기본료,할인유형",
			columnIds : "salePlcyCd,rateGrpCd,rateCd,rateNm,baseAmt,sprtTp",
			colAlign : "center,center,center,left,right,center",
			colTypes : "ro,ro,ro,ro,ron,ro",
			colSorting : "str,str,str,str,str,str",
			widths : "100,80,90,170,80,80",
			paging: false,
			hiddenColumns : [0,5],
			multiCheckbox : true,
			buttons : {
				center : {
					btnSaveRate : { label : "추가" },
					btnCloseRate : { label : "닫기" }
				}
			},
			onButtonClick:function(btnId) {
				var form = this;
				
				switch(btnId) {
				
					case "btnSaveRate" :
						var rowIds = this.getCheckedRows(0);
						
						if(!rowIds) {
							mvno.ui.alert("ECMN0003");
							return;
						} else {
							
							var sdata = this.classifyRowsFromIds(rowIds);
							mvno.ui.confirm("[ " + this.getCheckedRowData().length + " ] 요금제를 추가하시겠습니까?", function() {
								mvno.cmn.ajax4json(ROOT + "/sale/plcyMgmt/setPlcyRateAdd.json", sdata, function() {
									mvno.ui.notify("NCMN0006");
									PAGE.GRID12.refresh();
								});
							});
						}
						break;
						
					case "btnCloseRate" :
						mvno.ui.closeWindowById("GRID12");
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
		
		PAGE.FORM1.setItemValue("stdrDt", today);
		
		// 조회조건 처리상태 combo 셋팅
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0058"}, PAGE.FORM1, "searchSupportTypeCd");
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0050"}, PAGE.FORM1, "searchPlcyTypeCd");
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0007"}, PAGE.FORM1, "searchPlcySctnCd");
		
	}

};

</script>