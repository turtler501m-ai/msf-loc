<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
	
	var fromDt = new Date(new Date().setDate(new Date().getDate() - 6)).format("yyyymmdd");
	var toDt = new Date().format("yyyymmdd");
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchFromDt", label:"보상승인일자", labelWidth:80, value:fromDt, calendarPosition: "bottom", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchToDt", label:"~", value:toDt, calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type:"newcolumn"}
					, {type:"select", label:"보상유형", name:"cmpnType", width:110, offsetLeft:20}
					, {type:"newcolumn"}
					, {type:"select", label:"보험상품", name: "insrProdCd", width:190, offsetLeft:20}
				]}
				, {type:"block", list:[
					{type:"select", label:"검색구분", name: "searchCd", labelWidth:80, width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchVal", width:115, offsetLeft:0, maxLength:60}
					, {type:"newcolumn"}
					, {type:"select", label:"결제방법", name: "payType", width:110, offsetLeft:20}
					, {type:"newcolumn"}
					, {type:"select", label:"결제여부", name: "payYn", width:100, offsetLeft:20}
				]}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"} 
			]
			, onChange: function (name, value) {
				var form = this;
				
				switch(name) {
					case "searchCd":
						if(mvno.cmn.isEmpty(value)) {
							form.setItemValue("searchFromDt", fromDt);
							form.setItemValue("searchToDt", toDt);
							
							form.setItemValue("searchVal", "");
							
							form.enableItem("searchFromDt");
							form.enableItem("searchToDt");
						} else {
							form.setItemValue("searchFromDt", "");
							form.setItemValue("searchToDt", "");
							
							form.disableItem("searchFromDt");
							form.disableItem("searchToDt");
						}
						
						break;
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					
					case "btnSearch":
						
						if( !mvno.cmn.isEmpty(form.getItemValue("searchCd")) && mvno.cmn.isEmpty(form.getItemValue("searchVal")) ) {
							mvno.ui.alert("검색 할 내용이 없습니다.");
							return;
						}
						
						PAGE.GRID1.list(ROOT + "/insr/insrMgmt/getInsrCmpnList.json", form);
						
						break;
				}
			}
		}
		
		, GRID1 : {
			css : {
				height : "530px"
			}
			, title : "보험보상목록"
			, header : "전화번호,고객명,계약번호,보험상품명,보상유형,단말모델ID,단말모델명,단말일련번호,보험관리번호,사고번호,사고등록일자,사고일자,사고유형,보상승인일자,보상한도금액,잔여보상금액,보상누적금액,잔여보상한도금액,영수증금액,실보상금액,은행명,계좌번호,예금주,보상시점출고가,고객부담금,초과금,유심비용,보상단말모델(대표),보상단말모델(색상),연락처,결제방법,결제여부,결제일시,검증결과,처리자,처리일시,tbNm,dlvryNo,reqPhoneSn,resNo,vacId,vacBankCd,payAmt,insrProdCd,cmpnType,payType,insrCmpnNum"
			, columnIds : "subscriberNo,subLinkName,contractNum,insrProdNm,cmpnTypeNm,modelId,modelNm,intmSrlNo,insrMngmNo,acdntNo,acdntRegDt,acdntDt,acdntTp,cmpnCnfmDt,cmpnLmtAmt,rmnCmpnAmt,cmpnAcmlAmt,rmnCmpnLmtAmt,rcptAmt,realCmpnAmt,bankNm,acntNo,dpstNm,outUnitPric,custBrdnAmt,overAmt,usimAmt,cmpnModelNm,cmpnModelColor,cmpnCtn,payTypeNm,payYn,payDttm,vrfyRsltNm,rvisnId,rvisnDttm,tbNm,dlvryNo,reqPhoneSn,resNo,vacId,vacBankCd,payAmt,insrProdCd,cmpnType,payType,insrCmpnNum"
			, widths : "120,100,100,180,80,100,100,100,120,120,100,100,100,100,100,100,100,120,100,100,100,100,100,100,100,100,100,130,130,100,80,80,130,80,100,130,50,50,50,50,50,50,50,50,50,50,50"
			, colAlign : "center,center,center,Left,center,center,Left,Left,center,center,center,center,center,center,Right,Right,Right,Right,Right,Right,Right,center,center,center,Right,Right,Right,Left,Left,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center"
			, colTypes : "roXp,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXd,roXd,ro,roXd,roXns,roXns,roXns,roXns,roXns,roXns,ro,ro,ro,roXns,roXns,roXns,roXns,ro,ro,roXp,ro,ro,roXdt,ro,ro,roXdt,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, hiddenColumns : [12,27,28,29,36,37,38,39,40,41,42,43,44,45,46]
			, paging: true
			, showPagingAreaOnInit: true
			, pageSize:20
			, buttons : {
				hright : {
					btnExcel:{ label:"엑셀다운로드"}
				}
				, right : {
					btnCmpn : { label : "전손관리" }
				}
			}
			, onRowDblClicked : function(rowId, celInd) {
				PAGE.GRID1.callEvent("onButtonClick", ["btnCmpn"]);
			}
			, onButtonClick : function(btnId) {
				var grid = this;
				
				switch (btnId) {
					case "btnExcel":
						
						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}
						
						var searchData =  PAGE.FORM1.getFormData(true);
						
						mvno.cmn.download(ROOT + "/insr/insrMgmt/getInsrCmpnListByExcel.json?menuId=MSP9005001", true, {postData:searchData}); 
						
						break;
						
					case "btnCmpn":
						
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
						if(grid.getSelectedRowData().cmpnType != "A"){
							mvno.ui.alert("보상유형이 전손인 경우만 가능합니다.");
							return;
						}
						
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.clear();
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0254", orderBy:"etc1"}, PAGE.FORM2, "payType");		// 결제방법
						mvno.cmn.ajax4lov( ROOT + "/pps/hdofc/custmgmt/PpsVacCodeList.json", null, PAGE.FORM2, "vacBankCd");
						
						var rowData = grid.getSelectedRowData();
						
						PAGE.FORM2.setFormData(rowData);
						
						if("A" == grid.getSelectedRowData().payType) {
							PAGE.FORM2.showItem("VAC");
							PAGE.FORM2.hideItem("CARD");
						} else if ("C" == grid.getSelectedRowData().payType) {
							PAGE.FORM2.hideItem("VAC");
							PAGE.FORM2.showItem("CARD");
						} else {
							PAGE.FORM2.hideItem("VAC");
							PAGE.FORM2.hideItem("CARD");
						}
						
						if("Y" == grid.getSelectedRowData().payYn){
							PAGE.FORM2.enableItem("resNo");
							PAGE.FORM2.enableItem("btnResNo");
							
							PAGE.FORM2.disableItem("cmpnCtn");
							PAGE.FORM2.disableItem("btnCtn");
							PAGE.FORM2.disableItem("payType");
							PAGE.FORM2.disableItem("btnPayType");
							
							PAGE.FORM2.disableItem("VAC");
							PAGE.FORM2.disableItem("CARD");
						} else {
							PAGE.FORM2.disableItem("resNo");
							PAGE.FORM2.disableItem("btnResNo");
							
							PAGE.FORM2.enableItem("cmpnCtn");
							PAGE.FORM2.enableItem("btnCtn");
							PAGE.FORM2.enableItem("payType");
							PAGE.FORM2.enableItem("btnPayType");
							
							PAGE.FORM2.enableItem("VAC");
							PAGE.FORM2.enableItem("CARD");
						}
						
						var selectedId = this.getSelectedRowId();
						
						PAGE.FORM2.setItemValue("selectedId", selectedId);
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("FORM2", "전손관리", 600, 380);
						
						break;
						
				}
			}
		}
		
		, FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:70, blockOffset:0}
				, {type:"block", list:[
					{type:"input", label:"고객명", name:"subLinkName", width:100, offsetLeft:10, readonly: true}
					, {type:"newcolumn"}
					, {type:"input", label:"연락처", name:"cmpnCtn", width:100, offsetLeft:70, validate:"ValidNumeric", maxLength:11, required:true}
					, {type:"newcolumn"}
					, {type: "button", name: 'btnCtn', value:"변경"}
				]}
				, {type:"block", list:[
					{type:"input", label:"대표모델", name:"cmpnModelNm", width:100, offsetLeft:10, readonly: true}
					, {type:"newcolumn"}
					, {type:"input", label:"색상모델", name:"cmpnModelColor", width:100, offsetLeft:70, readonly: true}
				]}
				, {type:"block", list:[
					{type:"input", label:"고객부담금", name:"custBrdnAmt", width:100, offsetLeft:10, readonly: true, numberFormat:"0,000,000,000", userdata:{align:"right"}}
					, {type:"newcolumn"}
					, {type:"input", label:"초과금", name:"overAmt", width:100, offsetLeft:70, readonly: true, numberFormat:"0,000,000,000", userdata:{align:"right"}}
				]}
				, {type:"block", list:[
					{type:"input", label:"유심비용", name:"usimAmt", width:100, offsetLeft:10, readonly: true, numberFormat:"0,000,000,000", userdata:{align:"right"}}
					, {type:"newcolumn"}
					, {type:"input", label:"결제금액", name:"payAmt", width:100, offsetLeft:70, readonly: true, numberFormat:"0,000,000,000", userdata:{align:"right"}}
				]}
				, {type:"block", list:[
					{type:"select", label:"결제유형", name:"payType", width:100, offsetLeft:10, required:true}
					, {type:"newcolumn"}
					, {type: "button", name: 'btnPayType', value:"변경"}
				]}
				, {type: "fieldset", label: "가상계좌", name:"VAC", inputWidth:530, list:[
					{type:"block", list:[
						{type:"select", label:"은행", name:"vacBankCd", width:100, offsetLeft:10, labelWidth:60, required:true}
						, {type:"newcolumn"}
						, {type: "button", name: 'btnBank', value:"변경"}
						, {type:"newcolumn"}
						, {type:"input", label:"가상계좌", name:"vacId", width:150, offsetLeft:20, labelWidth:70, readonly: true}
					]}
				]}
				, {type: "fieldset", label: "카드", name:"CARD", inputWidth:530, list:[
					{type:"block", list:[
						{type: "button", name: 'btnPay', value:"카드결제완료", offsetLeft:10}
					]}
				]}
				, {type:"block", list:[
					{type:"input", label:"결제여부", name:"payYn", width:100, offsetLeft:10, readonly: true}
					, {type:"newcolumn"}
					, {type:"calendar", label:"결제일시", name:"payDttm", width:150, offsetLeft:70, dateFormat:"%Y-%m-%d %H:%i:%s", serverDateFormat:"%Y%m%d%H%i%s", disabled: true}
				]}
				, {type:"block", list:[
					{type:"input", label:"예약번호", name:"resNo", width:100, offsetLeft:10, validate:"ValidNumeric"}
					, {type:"newcolumn"}
					, {type: "button", name: 'btnResNo', value:"등록"}
				]}
				, {type: "hidden", name: "contractNum"}
				, {type: "hidden", name: "insrCmpnNum"}
				, {type: "hidden", name: "selectedId"}
				, {type: "hidden", name: "insrProdNm"}
			]
			, buttons : {
				center : {
					btnClose : { label : "닫기" }
				}
			}
			, onChange: function (name, value) {
				var form = this;
				
				switch(name) {
					case "payType":
						
						if("A" == value) {
							form.showItem("VAC");
							form.hideItem("CARD");
						} else if ("C" == value) {
							form.hideItem("VAC");
							form.showItem("CARD");
						}
						
						break;
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					
					case "btnCtn":
						
						if(mvno.cmn.isEmpty(form.getItemValue("cmpnCtn"))) {
							mvno.ui.alert("연락처 정보가 없습니다.");
							return;
						}
						
						var regStr = /(01[0|1|6|7|8|9])(\d{3}|\d{4})(\d{4}$)/g;
						
						if(!regStr.test(form.getItemValue("cmpnCtn"))){
							mvno.ui.alert("유효하지 않은 연락처 입니다.");
							return;
						}
						
						fn_updIntmInsrCmpnDtl("01", "연락처를 변경 하시겠습니까?");
						
						break;
						
					case "btnPayType":
						
						if (PAGE.GRID1.getRowData(form.getItemValue("selectedId")).payType == form.getItemValue("payType")){
							mvno.ui.alert("동일한 결제유형 입니다. 변경 후 처리하세요.");
							return;
						}
						
						fn_updIntmInsrCmpnDtl("02", "결제유형을 변경 하시겠습니까?");
						
						break;
						
					case "btnBank":
						
						if ("C" == PAGE.GRID1.getRowData(form.getItemValue("selectedId")).payType){
							mvno.ui.alert("가상 계좌로 변경 후 처리하세요.");
							return;
						}
						
						if (PAGE.GRID1.getRowData(form.getItemValue("selectedId")).vacBankCd == form.getItemValue("vacBankCd")){
							mvno.ui.alert("동일한 은행 입니다. 변경 후 처리하세요.");
							return;
						}
						
						fn_updIntmInsrCmpnDtl("03", "은행을 변경 하시겠습니까?");
						
						break;
						
					case "btnPay":
						
						if ("A" == PAGE.GRID1.getRowData(form.getItemValue("selectedId")).payType){
							mvno.ui.alert("카드결제로 변경 후 처리하세요.");
							return;
						}
						
						fn_updIntmInsrCmpnDtl("04", "결제 완료 처리 하시겠습니까?");
						
						break;
						
					case "btnResNo":
						
						if(mvno.cmn.isEmpty(form.getItemValue("resNo"))) {
							mvno.ui.alert("예약번호를 입력 하세요.");
							return;
						}
						
						fn_updIntmInsrCmpnDtl("05", "예약번호를 등록 하시겠습니까?");
						
						break;
						
					case "btnClose":
						mvno.ui.closeWindowById("FORM2");
						
						break;
				}
			}
		}
		
	}
	
	function fn_updIntmInsrCmpnDtl(cmpnTrtmTypeCd, msg) {
		
		var sData = {
			cmpnTrtmTypeCd : cmpnTrtmTypeCd
			, insrCmpnNum : PAGE.FORM2.getItemValue("insrCmpnNum")
			, contractNum : PAGE.FORM2.getItemValue("contractNum")
		};
		
		switch (cmpnTrtmTypeCd) {
			
			case "01" :
				
				sData = $.extend({}, {cmpnCtn : PAGE.FORM2.getItemValue("cmpnCtn")}, sData);
				
				break;
				
			case "02" :
			case "03" :
				
				var vacBankNm = "";
				var vacBankCd = "";
				
				if(PAGE.FORM2.getItemValue("payType") == "A"){
					
					vacBankCd = PAGE.FORM2.getItemValue("vacBankCd");
					
					var opt = PAGE.FORM2.getOptions("vacBankCd");
					
					vacBankNm = opt[opt.selectedIndex].label;
				}
				
				sData = $.extend({}, {
					payType : PAGE.FORM2.getItemValue("payType")
					, cmpnCtn : PAGE.FORM2.getItemValue("cmpnCtn")
					, payAmt : PAGE.FORM2.getItemValue("payAmt")
					, vacBankCd : vacBankCd
					, vacBankNm : vacBankNm
					, insrProdNm : PAGE.FORM2.getItemValue("insrProdNm")
				}, sData);
				
				break;
				
			case "04" :
				
				sData = $.extend({}, {payYn : "Y"}, sData);
				
				break;
				
			case "05" :
				
				sData = $.extend({}, {resNo : PAGE.FORM2.getItemValue("resNo")}, sData);
				
				break;
		}
		
		mvno.ui.confirm(msg, function() {
			mvno.cmn.ajax(ROOT + "/insr/insrMgmt/updIntmInsrCmpnDtl.json", sData, function(rsltData){
				mvno.ui.notify("NCMN0004");
				PAGE.GRID1.refresh();
				
				if("02" == cmpnTrtmTypeCd || "03" == cmpnTrtmTypeCd){
					PAGE.FORM2.setItemValue("vacId", rsltData.result.vacId);
				} else if("04" == cmpnTrtmTypeCd){
					mvno.ui.closeWindowById("FORM2");
				}
				
			}, {async: false});
		});
	}
	
	var PAGE = {
	      title : "${breadCrumb.title}",
	      breadcrumb : "${breadCrumb.breadCrumb}",
		  buttonAuth: ${buttonAuth.jsonString} ,
		  onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchCd");					// 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0253", orderBy:"etc1"}, PAGE.FORM1, "cmpnType");	// 사고유형
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0254", orderBy:"etc1"}, PAGE.FORM1, "payType");		// 결제방법
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005", orderBy:"etc1"}, PAGE.FORM1, "payYn");		// 결제여부
			mvno.cmn.ajax4lov( ROOT + "/insr/insrMgmt/getInsrCombo.json", "", PAGE.FORM1, "insrProdCd");										// 단말보험상품
			
		}
	};
</script>
