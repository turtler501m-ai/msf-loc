<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
	* @Class Name : rateMgmt.jsp
	* @Description : 요금제관리
	* @
	* @ 수정일     수정자 수정내용
	* @ ---------- ------ -----------------------------
	* @ 2015.08.06
	* @ 2017.03.17 이상직 통계대상 항목 추가(부가서비스 마감통계용)
	* @Create Date : 2015. 8. 6.
	*/
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div class="row"><!-- 2단 이상 구성시 class="row" div 추가 -->
	<div id="GRID4" class="c6"></div><!-- c3=30%, c5=50% -->
	<div id="GRID5" class="c4"></div>
</div>
<div class="row"><!-- 2단 이상 구성시 class="row" div 추가 -->
	<div id="GRID6" class="c6"></div><!-- c3=30%, c5=50% -->
	<div id="GRID7" class="c4"></div>
</div>

<div id="FORM2" class="section-box"></div>
<div id="GROUP1" style="display:none;">
	<div id="GRID2"></div>
	<div id="FORM3" class="section-box"></div>
</div>
<div id="GROUP2" style="display:none;">
	<div id="GRID3"></div>
	<div id="FORM4" class="section-box"></div>
</div>
<div id="GROUP3" style="display:none;">
	<div id="GRID8"></div>
	<div id="FORM5" class="section-box"></div>
</div>
<div id="GROUP4" style="display: none;">
	<div class="row">
			<div id="GRID9" class="c4-5"></div>
			<div id="GRID10" class="c5-5"></div>
	</div>
	<div id="FORM6"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	var today = new Date().format("yyyymmdd");
	var gridGroup4Chk = 0;

	var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
				{type: "block", list: [
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'stdrDt', label: '기준일자', calendarPosition: 'bottom', width:100, required:true},
					{type: 'newcolumn'},
					{type: "select", name: "rateGrpCd", label: "요금제그룹", width:80, offsetLeft: 10},
					{type: "newcolumn"},
					{type: "select", name: "serviceType", label: "서비스유형", width:80, offsetLeft: 10},
					{type: "newcolumn"},
					{type: "select", name: "searchGb", offsetLeft:10, width:80, options:[{value:"rateNm", text:"요금제명"}, {value:"rateCd", text:"요금제코드"}]},
					{type: "newcolumn"},
					{type: "input", name: "searchVal", width:80, maxLength:40},
					{type: "newcolumn"},
					{type: "select", name: "payClCd", label: "선후불구분", offsetLeft:10, width:80}
				]},
				{type: "newcolumn"},
				{type: "button", value: "조회", name: "btnSearch" , className:"btn-search1"}
			],

			onButtonClick : function(btnId) {

				var form = this;

				switch (btnId) {

					case "btnSearch":
						form.setItemValue("searchVal", form.getItemValue("searchVal").trim());
						PAGE.GRID1.list(ROOT + "/sale/rateMgmt/getRateMgmtList.json", form);
						PAGE.GRID4.clearAll();
						PAGE.GRID5.clearAll();
						break;
				}
			}
		},
		//-------------------------------------------------------------------------------------
		//요금제리스트
		GRID1 : {
			css : {
				height : "200px"
			},
			title : "요금제목록",
			header : "요금제그룹,요금제코드,요금제명,출시일자,종료일자,서비스유형,선후불구분,요금제유형,데이터유형,기본료,무료통화,망내(분),망외(분),문자(건),데이터(MB),온라인노출유형,등록자,등록일시,수정자,수정일시,무료통화코드,망내무료통화코드,무료문자코드,무료데이터코드,리마인드대상여부,제휴처,제휴동의대상,약관제휴처",
			columnIds : "rateGrpNm,rateCd,rateNm,applStrtDt,applEndDt,serviceTypeNm,payClCdNm,rateTypeNm,dataType,baseAmt,freeCallCnt,nwInCallCnt,nwOutCallCnt,freeSmsCnt,freeDataCnt,onlineTypeNm,regstNm,regstDttm,rvisnNm,rvisnDttm,freeCallCd,nwInCallCd,freeSmsCd,freeDataCd,remindYn,remindProdType,jehuYn,jehuProdType",
			colAlign : "center,left,left,center,center,center,center,center,center,right,right,right,right,right,right,center,center,center,center,center,center,center,center,center,center,center,center,center",
			colTypes : "ro,ro,ro,roXd,roXd,ro,ro,ro,ro,ron,ro,ro,ro,ro,ro,ro,ro,roXdt,ro,roXdt,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,int,int,int,int,int,int,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
			widths : "75,90,220,80,80,80,80,80,80,65,75,75,75,75,75,120,80,120,80,120,120,120,120,120,0,0,0,0",
			paging: true,
			pageSize:20,
			hiddenColumns : "20,21,22,23,24,25,26,27",
			buttons : {
				left : {
					btnGrpReg : { label : "그룹등록" },
					btnSpec : { label : "할인금액등록" },
					btnCancelSpec : { label : "위약금등록" },
					btnDisAddMng : { label : "평생할인 부가서비스 관리" }
				},
				right : {
					btnReg : { label : "등록" },
					btnMod : { label : "수정" }
				},
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				}
			},
			checkSelectedButtons : ["btnMod"],
			onRowDblClicked : function(rowId, celInd) {
				// 수정버튼 누른것과 같이 처리
				this.callEvent("onButtonClick", ['btnMod']);
			},
			onRowSelect:function(rId, cId){
				var grid = this;
				var data = grid.getSelectedRowData();
				PAGE.GRID5.clearAll();
				PAGE.GRID4.list(ROOT + "/sale/rateMgmt/getRateSpecList.json", data);
				PAGE.GRID7.clearAll();
				PAGE.GRID6.list(ROOT + "/sale/rateMgmt/getRateCancelSpecList.json", data);

			},
			onButtonClick : function(btnId, selectedData) {
				var form = this;
				
				switch (btnId) {
					case "btnReg":
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.setFormData(true);
						PAGE.FORM2.setItemValue("flag", "I");
						PAGE.FORM2.enableItem("serviceType");
						PAGE.FORM2.enableItem("rateCd");
						PAGE.FORM2.enableItem("rateNm");
						PAGE.FORM2.enableItem("payClCd");
						PAGE.FORM2.enableItem("rateType");
						PAGE.FORM2.enableItem("dataType");
						PAGE.FORM2.enableItem("baseAmt");
						PAGE.FORM2.enableItem("onlineTypeCd");
						PAGE.FORM2.enableItem("freeCallClCd");
						//PAGE.FORM2.enableItem("freeCallCnt");
						//PAGE.FORM2.enableItem("nwInCallCnt");
						PAGE.FORM2.enableItem("nwOutCallCnt");
						//PAGE.FORM2.enableItem("freeSmsCnt");
						//PAGE.FORM2.enableItem("freeDataCnt");
						PAGE.FORM2.enableItem("applStrtDt");
						PAGE.FORM2.disableItem("applEndDt");
						PAGE.FORM2.setItemValue("applStrtDt", today);
						PAGE.FORM2.setItemValue("applEndDt", "99991231");
						// 2017-03-20, 요금제 변경
						PAGE.FORM2.disableItem("sttcYn");
						PAGE.FORM2.disableItem("onlineCanYn");
						PAGE.FORM2.disableItem("canCmnt");
						PAGE.FORM2.disableItem("onlineCanDay");
						
						// 2020-03-02						
						PAGE.FORM2.disableItem("freeCallCnt");
						PAGE.FORM2.disableItem("nwInCallCnt");
						PAGE.FORM2.disableItem("freeSmsCnt");
						PAGE.FORM2.disableItem("freeDataCnt");
						PAGE.FORM2.enableItem("freeCallCd");
						PAGE.FORM2.enableItem("nwInCallCd");
						PAGE.FORM2.enableItem("freeSmsCd");
						PAGE.FORM2.enableItem("freeDataCd");
						// 2024-11-12
						PAGE.FORM2.enableItem("remindYn");
						PAGE.FORM2.disableItem("remindProdType");

						PAGE.FORM2.enableItem("jehuYn");
						PAGE.FORM2.disableItem("jehuProdType");

						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0046"}, PAGE.FORM2, "rateGrpCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0008"}, PAGE.FORM2, "serviceType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0050"}, PAGE.FORM2, "onlineTypeCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0013"}, PAGE.FORM2, "payClCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0047"}, PAGE.FORM2, "rateType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0048"}, PAGE.FORM2, "dataType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0054"}, PAGE.FORM2, "freeCallClCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0282"}, PAGE.FORM2, "remindProdType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"JehuProdType"}, PAGE.FORM2, "jehuProdType");

						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"SALE0001"}, PAGE.FORM2, "freeCallCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"SALE0002"}, PAGE.FORM2, "nwInCallCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"SALE0003"}, PAGE.FORM2, "freeSmsCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"SALE0004"}, PAGE.FORM2, "freeDataCd");

						PAGE.FORM2.clearChanged();

						mvno.ui.popupWindowById("FORM2", "요금제등록", 630, 750, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						break;

					case "btnMod":

						mvno.ui.createForm("FORM2");

						PAGE.FORM2.clear();

						mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0046"}, PAGE.FORM2, "rateGrpCd");
						mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0008"}, PAGE.FORM2, "serviceType");
											
						
						mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0050"}, PAGE.FORM2, "onlineTypeCd");
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"SALE0001"}, PAGE.FORM2, "freeCallCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"SALE0002"}, PAGE.FORM2, "nwInCallCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"SALE0003"}, PAGE.FORM2, "freeSmsCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"SALE0004"}, PAGE.FORM2, "freeDataCd");
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0013"}, PAGE.FORM2, "payClCd");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0047"}, PAGE.FORM2, "rateType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0048"}, PAGE.FORM2, "dataType");
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0282"}, PAGE.FORM2, "remindProdType");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"JehuProdType"}, PAGE.FORM2, "jehuProdType");

						PAGE.FORM2.setFormData(selectedData);

						PAGE.FORM2.setItemValue("flag", "U");
						PAGE.FORM2.disableItem("serviceType");
						PAGE.FORM2.disableItem("rateCd");
						PAGE.FORM2.disableItem("rateNm");
						PAGE.FORM2.disableItem("payClCd");
						PAGE.FORM2.disableItem("rateType");
						PAGE.FORM2.disableItem("dataType");
						PAGE.FORM2.disableItem("baseAmt");
						PAGE.FORM2.enableItem("onlineTypeCd");
						PAGE.FORM2.disableItem("freeCallClCd");
						PAGE.FORM2.disableItem("freeCallCnt");
						PAGE.FORM2.disableItem("nwInCallCnt");
						PAGE.FORM2.disableItem("nwOutCallCnt");
						PAGE.FORM2.disableItem("freeSmsCnt");
						PAGE.FORM2.disableItem("freeDataCnt");
						PAGE.FORM2.disableItem("freeCallCd");
						PAGE.FORM2.disableItem("nwInCallCd");
						PAGE.FORM2.disableItem("freeSmsCd");
						PAGE.FORM2.disableItem("freeDataCd");
						
						PAGE.FORM2.disableItem("applStrtDt");
						PAGE.FORM2.enableItem("applEndDt");
						// 2017-03-20, 요금제변경
						if(selectedData.serviceType == "R") {
							PAGE.FORM2.disableItem("onlineTypeCd");
							PAGE.FORM2.disableItem("rateGrpCd");
							PAGE.FORM2.disableItem("cmnt");
							PAGE.FORM2.disableItem("rmk");
							PAGE.FORM2.enableItem("sttcYn");
							PAGE.FORM2.enableItem("onlineCanYn");
							if(selectedData.onlineCanYn == "Y") {
								PAGE.FORM2.enableItem("canCmnt");
								PAGE.FORM2.enableItem("onlineCanDay");
							} else {
								PAGE.FORM2.disableItem("canCmnt");
								PAGE.FORM2.disableItem("onlineCanDay");
							}
							// 2024-11-12, 리마인드대상여부 개발
							PAGE.FORM2.disableItem("remindYn");
							PAGE.FORM2.disableItem("remindProdType");
							PAGE.FORM2.disableItem("jehuYn");
							PAGE.FORM2.disableItem("jehuProdType");
						}else{
							PAGE.FORM2.enableItem("cmnt");
							PAGE.FORM2.enableItem("rmk");
							PAGE.FORM2.disableItem("sttcYn");
							PAGE.FORM2.disableItem("canCmnt");
							PAGE.FORM2.disableItem("onlineCanYn");
							PAGE.FORM2.disableItem("onlineCanDay");
							// 2024-11-12, 리마인드대상여부 개발
							PAGE.FORM2.enableItem("remindYn");
							if(selectedData.remindYn == "Y") {
								PAGE.FORM2.enableItem("remindProdType");
							} else {
								PAGE.FORM2.disableItem("remindProdType");
							}

							PAGE.FORM2.enableItem("jehuYn");
							if(selectedData.jehuYn == "Y") {
								PAGE.FORM2.enableItem("jehuProdType");
							} else {
								PAGE.FORM2.disableItem("jehuProdType");
							}
						}
						
						if(selectedData.freeCallCnt == "" || selectedData.freeCallCnt == "0" || selectedData.freeCallClCd == "N"){
							PAGE.FORM2.setItemValue("freeCallClCd", "N");
						}else{
							PAGE.FORM2.setItemValue("freeCallClCd", "Y");
						}

						PAGE.FORM2.clearChanged();

						mvno.ui.popupWindowById("FORM2", "요금제수정", 630, 750, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});

						break;

					case "btnGrpReg":

						mvno.ui.createGrid("GRID2");
						mvno.ui.createForm("FORM3");

						PAGE.GRID2.list(ROOT + "/sale/rateMgmt/getRateGrpMgmtList.json", "");

						mvno.ui.popupWindowById("GROUP1", "요금제그룹등록", 400, 400, {

						onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});

						break;

					case "btnSpec":

						var data = PAGE.GRID1.getSelectedRowData();

						//console.log("selectedData=" + data);

						if(data == null){
							mvno.ui.alert("요금제 선택후 할인금액을 등록하세요");
							return;
						}

						if(data.serviceType == "R") {
							mvno.ui.alert("부가서비스는 할인금액을 등록할 수 없습니다");
							return;
						}

						mvno.ui.createGrid("GRID3");
						mvno.ui.createForm("FORM4");
	
						PAGE.FORM4.setFormData(data);
						mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0058"}, PAGE.FORM4, "sprtTp");
						mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0053"}, PAGE.FORM4, "agrmTrm");
	
						PAGE.GRID3.list(ROOT + "/sale/rateMgmt/getRateSpecList.json", data);
	
						mvno.ui.popupWindowById("GROUP2", "할인금액등록", 480, 500, {

						onClose: function(win) {
								if (! PAGE.FORM4.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						
						break;

					case "btnCancelSpec":

						var data = PAGE.GRID1.getSelectedRowData();

						if(data == null){
							mvno.ui.alert("요금제 선택후 위약금을 등록하세요");
							return;
						}

						if(data.serviceType == "R") {
							mvno.ui.alert("부가서비스는 위약금을 등록할 수 없습니다");
							return;
						}

						mvno.ui.createGrid("GRID8");
						mvno.ui.createForm("FORM5");

						PAGE.FORM5.setFormData(data);
						mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0058"}, PAGE.FORM5, "sprtTp");
						mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0053"}, PAGE.FORM5, "agrmTrm");

						PAGE.GRID8.list(ROOT + "/sale/rateMgmt/getRateCancelSpecList.json", data);

						mvno.ui.popupWindowById("GROUP3", "위약금등록", 555, 420, {
							onClose: function(win) {
								if (! PAGE.FORM5.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						break;
						
					case "btnDisAddMng":

						mvno.ui.createGrid("GRID9");
						mvno.ui.createGrid("GRID10");
						mvno.ui.createForm("FORM6");

						var combobox = PAGE.GRID10.getCombo(4);
						combobox.put('Y', 'Y');
						combobox.put('N', 'N');

						gridGroup4Chk = 0;

						mvno.ui.popupWindowById("GROUP4", "평생할인 부가서비스 관리", 990, 700, {
							onClose: function(win) {
								if (gridGroup4Chk == 0) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});

						PAGE.GRID9.clearAll();
						PAGE.GRID10.clearAll();

						PAGE.GRID9.list(ROOT + "/sale/rateMgmt/getAddSvcList.json", "");
						PAGE.GRID10.list(ROOT + "/sale/rateMgmt/getDisAddSvcList.json", "");

						break;

					case "btnDnExcel":
						
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}
						
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download(ROOT + "/sale/rateMgmt/getRateMgmtListExcel.json?menuId=MSP2001011",true,{postData:searchData});
						
						break;
				}
			}
		},

		GRID4 : {
			css : {
				height : "140px"
			},
			title : "할인금액목록",
			header : "요금제코드,요금제명,약정기간,기본할인,요금할인,심플할인",
			columnIds : "rateCd,rateNm,agrmTrm,tmAmt,rtAmt,spAmt",
			colAlign : "left,left,center,right,right,right",
			colTypes : "ro,ro,ro,ron,ron,ron",
			colSorting : "str,,str,str,int,int,int",
			widths : "90,160,70,70,70,70",
			onRowSelect: function(rowId){
				var rowData = this.getSelectedRowData();

				//PAGE.FORM3.setFormData(rowData);
				PAGE.GRID5.list(ROOT + "/sale/rateMgmt/getRateSpecHist.json", rowData);
			}
		},

		GRID5 : {
			css : {
				height : "140px"
			},
			title : "할인금액이력",
			header : "할인유형,약정기간,시작일자,종료일자,할인금액",
			columnIds : "sprtTpNm,agrmTrm,applStrtDttm,applEndDttm,dcAmt",
			colAlign : "center,center,center,center,right",
			colTypes : "ro,ro,roXd,roXd,ron",
			colSorting : "str,str,str,str,int",
			widths : "70,50,70,70,70,50"
		},

		GRID6 : {
			css : {
				height : "140px"
			},
			title : "위약금목록",
			header : "약정기간,단말할인,#cspan,요금할인,#cspan,심플할인,#cspan",
			header2 : "#rspan,12개월,18개월,12개월,18개월,12개월,18개월",
			columnIds : "agrmTrm,tmAmt12,tmAmt18,rtAmt12,rtAmt18,spAmt12,spAmt18",
			colAlign : "center,right,right,right,right,right,right",
			colTypes : "ro,ron,ron,ron,ron,ron,ron",
			colSorting : "str,int,int,int,int,int,int",
			widths : "80,75,75,75,75,75,75",
			onRowSelect: function(rowId){
				var rowData = this.getSelectedRowData();
				PAGE.GRID7.list(ROOT + "/sale/rateMgmt/getRateCancelSpecHist.json", rowData);
			}
		},

		GRID7 : {
			css : {
				height : "140px"
			},
			title : "위약금이력",
			header : "할인유형,약정기간,시작일자,종료일자,위약금,#cspan",
			header2 : "#rspan,#rspan,#rspan,#rspan,12개월,18개월",
			columnIds : "sprtTpNm,agrmTrm,applStrtDttm,applEndDttm,ccAmt12,ccAmt18",
			colAlign : "center,center,center,center,right,right",
			colTypes : "ro,ro,roXd,roXd,ron,ron",
			colSorting : "str,str,str,str,int,int",
			widths : "64,50,69,69,46,46"
		},

		// --------------------------------------------------------------------------
		//요금제 등록화면
		FORM2 : {
			title : "",
			items : [
				{type: "settings", position: "label-left", labelWidth: "100", inputWidth: "auto"},

				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: "select", name: "serviceType", label: "서비스유형", width: 120, required: true}
				]},

				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: "input", name: "rateCd", label: "요금제코드", width: 120, validate:"ValidAplhaNumeric,mvno.vxRule.excludeKorean", required: true, maxLength: 10},
					{type: "newcolumn"},
					{type: "input", name: "rateNm", label: "요금제명", width: 120, offsetLeft: 20, validate: "NotEmpty", required: true, maxLength: 30}
				]},

				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: "select", name: "rateGrpCd", label: "요금제그룹유형", width: 120},
					{type: "newcolumn"},
					{type: "select", name: "payClCd", label: "선후불구분", width: 120, offsetLeft: 20}
				]},

				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: "select", name: "rateType", label: "요금제유형", width: 120, required: true},
					{type: "newcolumn"},
					{type: "select", name: "dataType", label: "데이터유형", width: 120, offsetLeft: 20}
				]},

				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: "input", name: "baseAmt", label: "기본료", width: 120, validate:"ValidInteger", required: true, maxLength: 10},
					{type: "newcolumn"},
					{type: "select", name: "onlineTypeCd", label: "온라인노출유형", width: 120, offsetLeft: 20}
					//{type: "input", name: "dcAmt", label: "할인금액", width: 120, offsetLeft: 20, validate:"ValidInteger", maxLength: 10}
				]},

				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: "select", name: "freeCallClCd", label: "무료통화구분", width: 120},
					{type: "newcolumn"},
					{type: "input", name: "nwOutCallCnt", label: "(망외)무료통화", width: 120, offsetLeft: 20, maxLength: 10}
				]},

				{type: "block", blockOffset: 0, offsetLeft: 30,list: [
					{type: "select", name: "freeCallCd", label: "무료통화(분)", width: 140},
					{type:"newcolumn"},
					{type:"input",  name:"freeCallCnt", width:111, offsetLeft:0, maxLength:60}
					
				]},
				
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: "select", name: "nwInCallCd", label: "(망내)무료통화", width: 140},
					{type:"newcolumn"},
					{type:"input",  name:"nwInCallCnt", width:111, offsetLeft:0, maxLength:60}
				]},

				{type: "block", blockOffset: 0, offsetLeft: 30,list: [
					{type: "select", name: "freeSmsCd", label: "무료문자(건)", width: 140},
					{type:"newcolumn"},
					{type:"input",  name:"freeSmsCnt", width:111, offsetLeft:0, maxLength:60}					
				]},
				
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: "select", name: "freeDataCd", label: "무료데이터(MB)", width: 140},
					{type:"newcolumn"},
					{type: "input", name: "freeDataCnt", width: 111, offsetLeft: 0, maxLength: 30}
				]},

				{type: "block", blockOffset: 0, offsetLeft: 30,list: [
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'applStrtDt', label: '출시일자', calendarPosition: 'bottom', width:120, required:true},
					{type: "newcolumn"},
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'applEndDt', label: '종료일자', calendarPosition: 'bottom', width:120, offsetLeft: 20 }
				]},

				{type: "block", blockOffset: 0, offsetLeft: 30,list: [
					{type: "checkbox", name: "sttcYn", label: "통계대상", value:"Y"}
					,{type: "newcolumn"}
					,{type: "checkbox", name: "onlineCanYn", label: "온라인해지가능", offsetLeft: 118, value:"Y"}
					,{type:"newcolumn"}
					,{type: "input", name: "onlineCanDay", width: 95, offsetLeft: 0, maxLength: 4, validate:'ValidNumeric'}
				]},
				{type: "block", blockOffset: 0, offsetLeft: 30,list: [
					{type: "checkbox", name: "remindYn", label: "리마인드대상여부", value:"Y"},
					{type: "newcolumn"},
					{type: "select", name: "remindProdType", label: "제휴처", width: 120, offsetLeft: 118}
				]},

				{type: "block", blockOffset: 0, offsetLeft: 30,list: [
					{type: "checkbox", name: "jehuYn", label: "제휴 동의 대상", value:"Y"},
					{type: "newcolumn"},
					{type: "select", name: "jehuProdType", label: "제휴처", width: 170, labelWidth: 50, offsetLeft: 118}
				]},

				{type: "block", blockOffset: 0, offsetLeft: 30,list: [
					{type: "input", name: "cmnt", label: "요금제혜택", width: 364, maxLength: 30}
				]},

				{type: "block", blockOffset: 0, offsetLeft: 30,list: [
					{type: "input", name: "rmk", label: "요금제설명", width: 364, rows: 10, maxLength: 1300}
				]},

				{type: "block", blockOffset: 0, offsetLeft: 30,list: [
					{type: "input", name: "canCmnt", label: "해지안내", width: 364, rows: 3, maxLength: 300}
				]},

				{type: "hidden", name: "flag"}
			],
			buttons : {
				/* left : {
					btnInit : { label : "초기화" }
				}, */
				center : {
					btnSave : { label : "저장" },
					btnClose : { label: "닫기" }
				}
			},
			onChange : function(name, value, isChecked) {
				var form = this;

				switch(name){
					case "serviceType" :
						if(value == "R"){
							// 부가서비스
							form.disableItem("rateGrpCd");
							form.disableItem("payClCd");
							form.disableItem("rateType");
							form.disableItem("dataType");
							form.disableItem("onlineTypeCd");
							form.disableItem("freeCallClCd");
							form.disableItem("freeCallCnt");
							form.disableItem("nwInCallCnt");
							form.disableItem("nwOutCallCnt");
							form.disableItem("freeSmsCnt");
							form.disableItem("freeDataCnt");
							form.disableItem("cmnt");
							form.disableItem("rmk");
							form.disableItem("canCmnt");
							
							form.disableItem("freeCallCd");
							form.disableItem("nwInCallCd");
							form.disableItem("freeSmsCd");
							form.disableItem("freeDataCd");
							
							// 초기화
							form.setItemValue("rateGrpCd", "");
							form.setItemValue("payClCd", "");
							form.setItemValue("rateType", "");
							form.setItemValue("dataType", "");
							form.setItemValue("onlineTypeCd", "");
							form.setItemValue("freeCallClCd", "");
							form.setItemValue("freeCallCnt", "");
							form.setItemValue("nwInCallCnt", "");
							form.setItemValue("nwOutCallCnt", "");
							form.setItemValue("freeSmsCnt", "");
							form.setItemValue("freeDataCnt", "");
							form.setItemValue("cmnt", "");
							form.setItemValue("rmk", "");
							
							form.setItemValue("freeCallCd", "");
							form.setItemValue("nwInCallCd", "");
							form.setItemValue("freeSmsCd", "");
							form.setItemValue("freeDataCd", "");
							
							// 2017-03-20, 요금제변경
							form.enableItem("sttcYn");
							form.enableItem("onlineCanYn");
							form.setItemValue("sttcYn", "");
							form.setItemValue("onlineCanYn", "");
							form.setItemValue("onlineCanDay", "");
							form.setItemValue("canCmnt", "");
							// 2024-11-12, 리마인드대상여부 개발
							form.setItemValue("remindYn", "");
							form.setItemValue("remindProdType", "");
							form.disableItem("remindYn");
							form.disableItem("remindProdType");

							form.setItemValue("jehuYn", "");
							form.setItemValue("jehuProdType", "");
							form.disableItem("jehuYn");
							form.disableItem("jehuProdType");

						}else{
							// 상품
							form.enableItem("rateGrpCd");
							form.enableItem("payClCd");
							form.enableItem("rateType");
							form.enableItem("onlineTypeCd");
							form.enableItem("freeCallClCd");
							form.disableItem("freeCallCnt");
							
							form.setItemValue("freeCallCd", "");
							form.setItemValue("nwInCallCd", "");
							form.setItemValue("freeSmsCd", "");
							form.setItemValue("freeDataCd", "");
							
							form.disableItem("nwInCallCnt");
							form.enableItem("nwOutCallCnt");
							form.disableItem("freeSmsCnt");
							form.disableItem("freeDataCnt");
							form.enableItem("cmnt");
							form.enableItem("rmk");
							
							
							// 2017-03-20, 요금제변경
							form.disableItem("sttcYn");
							form.disableItem("onlineCanYn");
							form.disableItem("onlineCanDay");
							form.disableItem("canCmnt");
							form.setItemValue("sttcYn", "");
							form.setItemValue("onlineCanYn", "");
							form.setItemValue("canCmnt", "");
							form.setItemValue("onlineCanDay", "");
							
							// 2020-03-02 
							form.enableItem("freeCallCd");
							form.enableItem("nwInCallCd");
							form.enableItem("freeSmsCd");
							form.enableItem("freeDataCd");
							// 2024-11-12, 리마인드대상여부 개발
							form.setItemValue("remindYn", "");
							form.setItemValue("remindProdType", "");
							form.enableItem("remindYn");
							form.disableItem("remindProdType");

							form.setItemValue("jehuYn", "");
							form.setItemValue("jehuProdType", "");
							form.enableItem("jehuYn");
							form.disableItem("jehuProdType");
						}

						break;
					case "freeCallClCd" :
						if(value == "Y"){
							form.disableItem("freeCallCnt");
							form.disableItem("freeCallCd");
							PAGE.FORM2.setItemValue("freeCallCd", "");
							PAGE.FORM2.setItemValue("freeCallCnt", "");
							form.enableItem("nwInCallCd");
							form.enableItem("nwOutCallCnt");
						}else if(value == ""){
							form.enableItem("freeCallCd");
							form.disableItem("nwInCallCnt");
							form.enableItem("nwInCallCd");
							PAGE.FORM2.setItemValue("nwInCallCnt", "");
							PAGE.FORM2.setItemValue("nwInCallCd", "");
							form.enableItem("nwOutCallCnt");
						}else{
							form.enableItem("freeCallCd");
							form.disableItem("nwInCallCnt");
							form.disableItem("nwInCallCd");
							PAGE.FORM2.setItemValue("nwInCallCnt", "");
							PAGE.FORM2.setItemValue("nwInCallCd", "");
							form.disableItem("nwOutCallCnt");
						}
						break;
					
					// 온라인해지가능 체크시 해지안내 항목 활성화 및  Day TEXT 활성화
					case "onlineCanYn":
						if(isChecked == true) {
							form.enableItem("canCmnt");
							form.enableItem("onlineCanDay");
						}else{
							form.disableItem("canCmnt");
							form.setItemValue("canCmnt", "");
							form.disableItem("onlineCanDay");
							form.setItemValue("onlineCanDay", "");
						}
						break;
					//무료통화(분)
					case "freeCallCd" : 
						if(value == "A1"){
							form.enableItem("freeCallCnt");
						}else{
							form.disableItem("freeCallCnt");
						}
						break;
					case "nwInCallCd" :
						if(value == "A1"){
							form.enableItem("nwInCallCnt");
						}else{
							form.disableItem("nwInCallCnt");
						}
						break;
					case "freeSmsCd" :
						if(value == "A1"){
							form.enableItem("freeSmsCnt");
						}else{
							form.disableItem("freeSmsCnt");
						}
						break;
					case "freeDataCd" :
						if(value == "A1"){
							form.enableItem("freeDataCnt");
						}else{
							form.disableItem("freeDataCnt");
						}
						break;
					case "remindYn" :
						if(isChecked == true) form.enableItem("remindProdType");
						else{
							form.disableItem("remindProdType");
							form.setItemValue("remindProdType", "");
							form.setItemValue("remindYn", "");
						}
						break;
					case "jehuYn" :
						if(isChecked == true) form.enableItem("jehuProdType");
						else{
							form.disableItem("jehuProdType");
							form.setItemValue("jehuProdType", "");
							form.setItemValue("jehuYn", "");
						}
						break;
				}
				
			},
			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {
					case "btnInit":
						if(form.getItemValue("flag") == "U") return;

						PAGE.FORM2.clear();
						break;

					case "btnSave":

						// textarea maxlength 값이 IE9에서 동작하지 않아 validation 추가.
						var inputSize = ($('textarea[name="rmk"]').val()).length;
						var maxLength = $('textarea[name="rmk"]').attr('maxLength');
						if (inputSize > 0 && inputSize > maxLength) {
							mvno.ui.alert("요금제설명 항목은 "+maxLength+"자까지 입력가능합니다.");
							return;
						}

						// 2017-03-17, 통계대상여부 체크
						if(form.getItemValue("serivceType") == "R" && form.getItemValue("sttcYn") == "Y"){
							mvno.ui.alert("상품은 통계대상을 선택할 수 없습니다.");
							return;
						}

						// 2024-11-18, 제휴처 필수값 체크
						if(form.isItemChecked("remindYn") && form.getItemValue("remindProdType") == "") {
							mvno.ui.alert("제휴처를 선택해주세요.");
							return;
						}

						// 제휴 동의 대상이 Y인 경우 제휴처 필수
						if(form.isItemChecked("jehuYn") && form.getItemValue("jehuProdType") == "") {
							mvno.ui.alert("(약관) 제휴처를 선택해주세요.");
							return;
						}

						var url;
						var msg;
						if(form.getItemValue("flag") == "I"){
							url = "/sale/rateMgmt/insertRateCd.json";
							msg = "NCMN0001";
						}else{
							url = "/sale/rateMgmt/updateRateCd.json";
							msg = "NCMN0002";
						}

						mvno.cmn.ajax(ROOT + url, form, function(result) {
							mvno.ui.closeWindowById(form, true);
							mvno.ui.notify(msg);
							PAGE.GRID1.refresh();
							PAGE.GRID4.clearAll();
						}, {aysnc:false});

						break;

					case "btnClose" :
						mvno.ui.closeWindowById(form);
						break;

				}

			},
			onValidateMore : function (data){

				if(data.serviceType == "P" && data.payClCd == "") {
					this.pushError("data.serviceType", "선후불구분", "선후불구분을 선택해주세요.");
				}

				if(data.serviceType == "P" && data.dataType == "") {
					this.pushError("data.dataType", "데이터유형", "데이터유형을 선택해주세요.");
				}
				
				if(data.remindYn == "Y" && data.remindProdType == "") {
					this.pushError("data.remindProdType", "제휴처", "제휴처을 선택해주세요.");
				}

				if(data.jehuYn == "Y" && data.jehuProdType == "") {
					this.pushError("data.jehuProdType", "제휴처", "(약관) 제휴처를 선택해주세요.");
				}

			}
		},
		//-------------------------------------------------------------------------------------
		//요금제그룹리스트
		GRID2 : {
			css : {
				height : "150px"
			},
			title : "요금제그룹목록",
			header : "그룹코드,그룹명,정렬순서",
			columnIds : "rateGrpCd,rateGrpNm,sort",
			colAlign : "center,left,center",
			colTypes : "ro,ro,ro",
			colSorting : "str,str,int",
			widths : "80,185,60",//340
			paging: false,
			onRowSelect: function(rowId){
				var rowData = this.getSelectedRowData();

				PAGE.FORM3.setFormData(rowData);
			}
		},
		//-------------------------------------------------------------------------
		// 요금제그룹등록
		FORM3 : {
			title : "",
			items : [
				{type: "settings", position: "label-left", labelWidth: "60", inputWidth: "auto", offsetLeft: 10},
				{type: "block", blockOffset: 0, list: [
					{type: "input", name: "rateGrpCd", label: "그룹코드", width: 200, validate:"mvno.vxRule.AplhaRealNumber", required: true, maxLength: 10},
					//{type: "newcolumn"},
					{type: "input", name: "rateGrpNm", label: "그룹명", width: 200, required: true, maxLength: 10},
					//{type: "newcolumn"},
					{type: "input", name: "sort", label: "정렬순서", width: 200, required: true, maxLength: 10, validate:"ValidInteger"}
				]}
			],
			buttons : {
				center : {
					btnSave : { label : "저장" },
					btnDel : { label : "삭제" },
					btnClose : { label: "닫기" }
				}
			},
			onButtonClick : function(btnId) {
				var form = this;

				switch(btnId){

					case "btnSave" :
						mvno.cmn.ajax(ROOT + "/sale/rateMgmt/insertRateGrpCd.json", form, function(result) {
							//mvno.ui.closeWindowById(form, true);
							mvno.ui.notify("NCMN0001");
							PAGE.GRID2.refresh();
							PAGE.FORM3.clear();
						});

						break;

					case "btnDel" :
						if(form.getItemValue("rateGrpCd") == "") return;

						mvno.cmn.ajax4confirm("그룹삭제시 요금제에 세팅된 그룹정보도 사라집니다<br />삭제하시겠습니까?", ROOT + "/sale/rateMgmt/deleteRateGrpCd.json", form, function(result) {
							//mvno.ui.closeWindowById(form, true);
							mvno.ui.notify("NCMN0001");
							PAGE.GRID2.refresh();
							PAGE.FORM3.clear();
						});

						break;

					case "btnClose" :
						mvno.ui.closeWindowById(form, true);
						break;
				}
			}
		},
		//-------------------------------------------------------------------------------------
		//할인금액리스트
		GRID3 : {
			css : {
				height : "280px"
			},
			//title : "할인금액",
			header : "요금제명,약정기간,기본할인,요금할인,심플할인",
			columnIds : "rateNm,agrmTrm,tmAmt,rtAmt,spAmt",
			colAlign : "left,center,right,right,right",
			colTypes : "ro,ro,ron,ron,ron",
			colSorting : "str,str,int,int,int",
			widths : "130,70,70,70,70",//410
			paging: false,
			//hiddenColumns: [0],
			onRowSelect: function(rowId){
				/*
				var rowData = this.getSelectedRowData();

				PAGE.FORM4.setFormData(rowData);
				if(rowData.sprtTp == "KD") {
					PAGE.FORM4.setItemValue("dcAmt", rowData.tmAmt);
				} else if (rowData.sprtTp == "PM") {
					PAGE.FORM4.setItemValue("dcAmt", rowData.rtAmt);
				}
				*/
			}
		},
		//-------------------------------------------------------------------------
		// 스펙등록
		FORM4 : {
			title : "",
			items : [
				{type: "settings", position: "label-left", labelWidth: "80", inputWidth: "auto", offsetLeft: 10},
				{type: "block", blockOffset: 0, list: [
					{type: "select", name: "sprtTp", label: "할인유형", width: 100, required: true},
					{type: "newcolumn"},
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'applStrtDttm', label: '시작일자', calendarPosition: 'bottom', width:100, required:true, validate: 'NotEmpty'},
				]},
				{type: "block", blockOffset: 0, list: [
					{type: "select", name: "agrmTrm", label: "약정기간", width: 100, required: true},
					{type: "newcolumn"},
					{type: "input", name: "dcAmt", label: "할인금액", width: 100, required: true, maxLength: 6, validate:"ValidInteger"}
				]},
				{type: "hidden", name: "rateCd"}
			],
			buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label: "닫기" }
				}
			},
			onValidateMore : function (data){

				var today = new Date().format('yyyymmdd');
				if (Number(data.applStrtDttm) < Number(today)){
					this.pushError("data.unitPricApplDttm","시작일자",["ICMN0013"]);
				}
			},
			onButtonClick : function(btnId) {
				var form = this;

				switch(btnId){

				case "btnSave" :
					mvno.cmn.ajax(ROOT + "/sale/rateMgmt/insertRateSpec.json", form, function(result) {
						//mvno.ui.closeWindowById(form, true);
						mvno.ui.notify("NCMN0001");
						PAGE.GRID3.refresh();
						PAGE.GRID4.refresh();
						//PAGE.FORM4.clear(); clear 시 요금제 코드 값이 사라져서 주석처리
					});
					break;

				case "btnClose" :
					PAGE.GRID5.clearAll();
					mvno.ui.closeWindowById(form, true);
					break;
				}
			}
		},

		GRID8 : {
			css : {
				height : "180px"
			},
			header : "약정기간,단말할인,#cspan,요금할인,#cspan,심플할인,#cspan",
			header2 : "#rspan,12개월,18개월,12개월,18개월,12개월,18개월",
			columnIds : "agrmTrm,tmAmt12,tmAmt18,rtAmt12,rtAmt18,spAmt12,spAmt18",
			colAlign : "center,right,right,right,right,right,right",
			colTypes : "ro,ron,ron,ron,ron,ron,ron",
			colSorting : "str,int,int,int,int,int,int",
			widths : "60,70,70,70,70,70,70",//480
			paging: false
		},

		FORM5 : {
			title : "",
			items : [
				{type: "settings", position: "label-left", labelWidth: "80", inputWidth: "auto", offsetLeft: 10},
				{type: "block", blockOffset: 0, list: [
					{type: "select", name: "sprtTp", label: "할인유형", width: 100, required: true},
					{type: "newcolumn"},
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'applStrtDttm', label: '시작일자', calendarPosition: 'bottom', width:100, offsetLeft: 70, required:true, validate: 'NotEmpty'},
				]},
				{type: "block", blockOffset: 0, list: [
					{type: "select", name: "agrmTrm", label: "약정기간", width: 100, required: true}
				]},
				{type: "block", blockOffset: 0, list: [
					{type: "input", name: "ccAmt12", label: "위약금(12)", width: 100, required: true, maxLength: 6, validate:"ValidInteger"},
					{type: "newcolumn"},
					{type: "input", name: "ccAmt18", label: "위약금(18)", width: 100, offsetLeft: 70, required: true, maxLength: 6, validate:"ValidInteger"}
				]},
				{type: "hidden", name: "rateCd"}
			],
			buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label: "닫기" }
				}
			},
			onValidateMore : function (data){

				var today = new Date().format('yyyymmdd');
				if (Number(data.applStrtDttm) < Number(today)){
					this.pushError("data.unitPricApplDttm","시작일자",["ICMN0013"]);
				}
			},
			onButtonClick : function(btnId) {
				var form = this;

				switch(btnId){
					case "btnSave" :
						mvno.cmn.ajax(ROOT + "/sale/rateMgmt/insertRateCancelSpec.json", form, function(result) {
							mvno.ui.notify("NCMN0001");
							PAGE.GRID6.refresh();
							PAGE.GRID8.refresh();
						});
						break;

					case "btnClose" :
						PAGE.GRID8.clearAll();
						mvno.ui.closeWindowById(form, true);
						break;
				}
			}
		 },

		//평생할인 부가서비스- 부가서비스 전체 목록
		GRID9 : {
			css : {
				height : "500px"
			},
			title : "부가서비스 전체목록",
			header : "선택,부가서비스코드,부가서비스명,기본료",
			columnIds : "rowCheck,rateCd,rateNm,baseAmt",
			colAlign : "center,center,left,right,center",
			colTypes : "ch,ro,coro,ron,ro",
			colSorting : "str,str,str,str,str",
			widths : "50,90,180,60,10",
			buttons : {
				right : {
					btnMove : { label : "등록" }
				}
			}
			// 그리드 행 선택시 checkbox도 선택되도록
			,onRowSelect : function(rowId){
				var cell = this.cells(rowId,0);
				if(!cell.isChecked()) cell.setValue(1);
				else cell.setValue(0);
			}
			,onButtonClick : function(btnId) {
				var grid = this;

				switch (btnId) {
					case "btnMove":
						var chkData = grid.getCheckedRowData();
						var rowIds = grid.getCheckedRows(0);
						var combobox = PAGE.GRID10.getCombo(4);

						if(chkData.length == 0){
							mvno.ui.alert("ECMN0003");
							return;
						}

						gridGroup4Chk = 1;

						for(let i = 0; i < chkData.length; i++){
							PAGE.GRID10.appendRow({rowCheck: 0, rateCd : chkData[i].rateCd, rateNm : chkData[i].rateNm, baseAmt : chkData[i].baseAmt });
							this.deleteRowByIds(rowIds);
							combobox.put('Y', 'Y');
							combobox.put('N', 'N');
						}


						break;
				}
			}
		},

		//평생할인 부가서비스- RCP2042 전체 목록
		GRID10 : {
			css : {
				height : "500px"
			},
			title : "평생할인 부가서비스",
			header : "선택,부가서비스코드,부가서비스명,기본료, 중복할인방지",
			columnIds : "rowCheck,rateCd,rateNm,baseAmt,dupYn",
			colAlign : "center,center,left,right,center,center",
			colTypes : "ch,ro,ro,ron,coro",
			colSorting : "str,str,str,str,str",
			widths : "50,90,190,60,80",
			buttons : {
				right : {
					btnDel : { label : "삭제" }
				}
			}
			,onButtonClick : function(btnId, selectedData) {

				var grid = this;

				switch (btnId) {
					case "btnDel":
						var chkData = grid.getCheckedRowData();
						var rowIds = grid.getCheckedRows(0);

						if(chkData.length == 0){
							mvno.ui.alert("ECMN0003");
							return;
						}

						gridGroup4Chk = 1;

						for(let i = 0; i < chkData.length; i++){
							PAGE.GRID9.appendRow({rowCheck: 1, rateCd : chkData[i].rateCd, rateNm : chkData[i].rateNm, baseAmt : chkData[i].baseAmt });
							this.deleteRowByIds(rowIds);
						}
						break;
				};
			}
		},

		//평생할인 부가서비스 관리 저장 및 닫기 버튼
		FORM6  : {
			title : "",
			buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			},
			onButtonClick : function(btnId) {

				switch(btnId){

					case "btnSave" :

						var disAddServiceArr = [];

						if (!validateDupYn()) {
							return;
						}

						PAGE.GRID10.forEachRow(function(id){
							var arrData = PAGE.GRID10.getRowData(id);
							disAddServiceArr.push(arrData);
						});

						var sData = {};
						sData.items = disAddServiceArr;

						var confirmMsg = "평생할인 부가서비스를 수정하시겠습니까?";
						mvno.ui.confirm(confirmMsg, function() {
							mvno.cmn.ajax4json(ROOT + "/sale/rateMgmt/manageDisAddSvcList.json", sData, function(result) {
								if (result.result.code == "OK") {
									gridGroup4Chk = 0;
									mvno.ui.alert("수정이 완료되었습니다.",'', function() {
										mvno.ui.notify("NCMN0006");
										PAGE.GRID9.refresh();
										PAGE.GRID10.refresh();
										mvno.ui.closeWindowById("GROUP4");
									});
								}
							});
						});

						break;

					case "btnClose":
							mvno.ui.closeWindowById("GROUP4");
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
			mvno.ui.createGrid("GRID4");
			mvno.ui.createGrid("GRID5");
			mvno.ui.createGrid("GRID6");
			mvno.ui.createGrid("GRID7");

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0046"}, PAGE.FORM1, "rateGrpCd");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"MSP0008"}, PAGE.FORM1, "serviceType");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0013"}, PAGE.FORM1, "payClCd");


			PAGE.FORM1.setItemValue("stdrDt", today);
		}
	};

	function validateDupYn() {
		for (var idx = 0; idx < PAGE.GRID10.getRowsNum(); idx++) {
			var rowId = PAGE.GRID10.getRowId(idx);
			var arrData = PAGE.GRID10.cells(rowId, 4).getValue();

			if (arrData == "" || arrData == null) {
				mvno.ui.alert("중복할인방지를 선택해 주세요.");
				return false;
			}
		}
		return true;
	}

</script>
