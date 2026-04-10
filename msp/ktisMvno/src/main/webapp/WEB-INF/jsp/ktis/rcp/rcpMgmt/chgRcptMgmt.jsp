<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	/**
	 * @Class Name : chgRcptMgmt.jsp
	 * @Description : 변경신청관리
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2016.04.18
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
	var endDt = new Date().format("yyyymmdd");
	var time = new Date().getTime();
	time = time - 1000 * 3600 * 24 * 6;
	var strDt = new Date(time).format("yyyymmdd");
	
	// 변경신청관리에서는 삭제, 검증상태변경 하지 않음.
	var modifyFlag = "N";
	var userOrgnTypeCd = "${info.sessionUserOrgnTypeCd}";
	/* if(userOrgnTypeCd == "10") {
		modifyFlag = "Y";
	} */
	
	var userId = "${info.sessionUserId}";
	var userName = "${info.sessionUserName}";
	var orgId = "${info.sessionUserOrgnId}";
	var orgNm = "${info.sessionUserOrgnNm}";
	var orgType = "";

	//조직 유형 - S:판매점, D:대리점, K:개통센터, "10:본사조직, 20:대리점, 30:판매점"
	if(userOrgnTypeCd == "10") {
		orgType = "K";
	} else if(userOrgnTypeCd == "20") {
		orgType = "D";
	} else if(userOrgnTypeCd == "30") {
		orgType = "S";
	}
	
	// 스캔관련
	var blackMakingYn = "Y";					// 블랙마킹사용권한
	var blackMakingFlag = "N";					// 블랙마킹해제권한 (마스킹권한여부에 따라 변화)
	var maskingYn = '${maskingYn}';				// 마스킹권한
	if(maskingYn == "Y") {
		blackMakingFlag = "Y";
	}	
	var agentVersion = '${agentVersion}';		// 스캔버전
	var serverUrl = '${serverUrl}';				// 서버환경 (로컬 : L, 개발 : D, 운영 : R)
	var scanSearchUrl = '${scanSearchUrl}';		// 스캔호출 url
	var scanDownloadUrl = '${scanDownloadUrl}';	// 스캔파일 download url
	
	// SRM18072675707, 단체보험
	var grpInsrYn = "${grpInsrYn}";
	
	var DHX = {
		FORM1 : {
			items : [
				  {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					  {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"처리일자", value: endDt, calendarPosition:"bottom", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~", value: endDt, calendarPosition:"bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type:"newcolumn"}
					, {type:"select", width:100, offsetLeft:20, label:"업무유형", name:"tskTp"}
					, {type:"newcolumn"}
					, {type:"select", width:100, offsetLeft:20, label:"검색구분", name:"searchClCd"}
					, {type:"newcolumn"}
					, {type:"input", width:100, name:"searchVal"}
				]}
				, {type:"newcolumn"}
				, {type:"button", value:"조회", name:"btnSearch", className:"btn-search1"}
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getChgRcptList.json", form);
						break;
						
					case "btnOrgFind":
						
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("procAgntCd", result.orgnId);
							form.setItemValue("procAgntNm", result.orgnNm);
							
						});
						break;
				}
			}
			, onChange : function(name, value) {
				if(name == "searchClCd") {
					PAGE.FORM1.setItemValue("searchVal", "");
					
					if(mvno.cmn.isEmpty(value)) {
						PAGE.FORM1.disableItem("searchVal");
					} else {
						PAGE.FORM1.enableItem("searchVal");
					}
				}
			}
			, onValidateMore : function (data){
				if (this.getItemValue("strtDt", true) > this.getItemValue("endDt", true)) {
					this.pushError("endDt", "처리일자", "종료일자가 시작일자보다 클 수 없습니다.");
					this.resetValidateCss();
				}
				
				if( data.searchClCd != "" && data.searchVal.trim() == ""){
					this.pushError("searchVal", "검색어", "검색어를 입력하세요");
				}
				
				if(mvno.cmn.isEmpty(this.getItemValue("strtDt"))) {
					this.pushError("strtDt", "처리일자", "시작일자를 선택하세요.");
				}
				
				if(mvno.cmn.isEmpty(this.getItemValue("endDt"))) {
					this.pushError("endDt", "처리일자", "종료일자를 선택하세요.");
				}
			}
		}
		// 목록
		, GRID1 : {
			css : {
					height : "560px"
			}
			, title : "조회결과"
			, header : "requestKey,resNo,업무유형,계약번호,개통대리점,고객명,전화번호,계약상태,선후불,개통일자,해지일자,처리대리점,처리자,처리일자"
			, columnIds : "requestKey,resNo,tskNm,contractNum,openAgntNm,custNm,subscriberNo,subStatusNm,payClNm,openDt,tmntDt,procAgntNm,procrNm,procDt"
			, colAlign : "left,left,left,center,left,left,center,center,center,center,center,left,left,center"
			, colTypes : "ro,ro,ro,ro,ro,ro,roXp,ro,ro,roXd,roXd,ro,ro,roXd"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, widths : "0,0,100,90,160,90,100,80,60,80,80,160,100,80"
			, paging: true
			, showPagingAreaOnInit: true
			, pageSize:20
			, buttons : {
				hright : {
// 					btnExcel : { label : "다운로드" }
				}
				, left : {
					  btnScanSearch : { label : "스캔검색" }
					, btnPaper : {label : "서식지보기" }
				}
				, right : {
					  btnReg: { label : "등록" }
				}
			}
			, checkSelectedButtons : ["btnDtl", "btnScanSearch", "btnPaper"]
			, onRowDblClicked : function(rowId, celInd) {
				this.callEvent('onButtonClick', ['btnDtl']);
			}
			, onButtonClick : function(btnId, selectedData) {
				var form = this;
				switch (btnId) {
				
					// 상세
					case "btnDtl" :
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();
						
						mvno.ui.hideButton("FORM2" , 'btnSave');
						mvno.ui.hideButton("FORM2" , 'btnScan');
						
						PAGE.FORM2.disableItem("btnFind");
						//PAGE.FORM2.disableItem("searchSubscriberNo");
						PAGE.FORM2.disableItem("searchGb");
						PAGE.FORM2.disableItem("searchGbVal");
						PAGE.FORM2.disableItem("tskTp");
						PAGE.FORM2.disableItem("tskNm");
						PAGE.FORM2.disableItem("rmk");
						PAGE.FORM2.disableItem("clauseInsuranceFlag");
						PAGE.FORM2.disableItem("insrCd");
						PAGE.FORM2.disableItem("insrProdCd");
						PAGE.FORM2.disableItem("clauseInsrProdFlag");

						// 콤보세팅
						mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0070", orderBy:"etc6"}, PAGE.FORM2, "tskTp", {topOption:"- 직접입력 -"}); // 업무유형
						mvno.cmn.ajax4lov(ROOT + "/ptnr/grpinsrmgmt/getGrpInsrCombo.json", "", PAGE.FORM2, "insrCd");
						mvno.cmn.ajax4lov(ROOT + "/insr/insrMgmt/getInsrCombo.json", "", PAGE.FORM2, "insrProdCd"); // 단말보험상품
						
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						PAGE.FORM2.setFormData(rowData);
						//PAGE.FORM2.setItemValue("searchSubscriberNo", rowData.subscriberNo);
						PAGE.FORM2.setItemValue("searchGb", "01");
						PAGE.FORM2.setItemValue("searchGbVal", rowData.subscriberNo);
						
						var nheight;
						
						if("CARE" == rowData.tskTp){
							PAGE.FORM2.showItem("IINSR");
							PAGE.FORM2.hideItem("BASE");
							PAGE.FORM2.hideItem("INTM");
							PAGE.FORM2.hideItem("TINTM");
							
							nheight = 470;
						} else {
							PAGE.FORM2.hideItem("IINSR");
							
							nheight = 400;
						}
						
						mvno.ui.popupWindowById("FORM2", "변경신청내역 상세", 580, nheight, {
							onClose: function(win) {
								win.closeForce();
							}
						});
						
						break;
						
					// 등록
					case "btnReg" :
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();
						
						mvno.ui.showButton("FORM2" , 'btnSave');
						mvno.ui.showButton("FORM2" , 'btnScan');
						
						//PAGE.FORM2.enableItem("searchSubscriberNo");
						PAGE.FORM2.enableItem("searchGb");
						PAGE.FORM2.enableItem("searchGbVal");
						PAGE.FORM2.enableItem("btnFind");
						PAGE.FORM2.enableItem("tskTp");
						PAGE.FORM2.enableItem("tskNm");
						PAGE.FORM2.enableItem("rmk");
						PAGE.FORM2.enableItem("clauseInsuranceFlag");
						PAGE.FORM2.enableItem("insrCd");
						PAGE.FORM2.enableItem("insrProdCd");
						PAGE.FORM2.enableItem("clauseInsrProdFlag");
						
						PAGE.FORM2.setItemValue("procAgntNm", orgNm);
						PAGE.FORM2.setItemValue("procrNm", userName);
						
						// 단체보험
						PAGE.FORM2.disableItem("insrCd");
						PAGE.FORM2.disableItem("clauseInsuranceFlag");
						
						// 콤보세팅
						mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0070", orderBy:"etc6"}, PAGE.FORM2, "tskTp", {topOption:"- 직접입력 -"}); // 업무유형
						mvno.cmn.ajax4lov(ROOT + "/ptnr/grpinsrmgmt/getGrpInsrCombo.json", "", PAGE.FORM2, "insrCd");
						mvno.cmn.ajax4lov(ROOT + "/insr/insrMgmt/getInsrCombo.json", {usgYn:"Y"}, PAGE.FORM2, "insrProdCd"); // 단말보험상품
						
						// 단말보험
						PAGE.FORM2.showItem("BASE");
						PAGE.FORM2.showItem("INTM");
						PAGE.FORM2.showItem("TINTM");
						PAGE.FORM2.hideItem("IINSR");
						
						//서식지 등록 스캔 타입 변경
						PAGE.FORM2.setItemValue("scanType","new");
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("FORM2", "변경신청내역 등록", 580, 400, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						
						break;
					
					case "btnScanSearch" :
						
						var requestKey = PAGE.GRID1.getSelectedRowData().requestKey;
						var resNo = PAGE.GRID1.getSelectedRowData().resNo;
						var scanId = PAGE.GRID1.getSelectedRowData().scanId;
						
						// 스캔솔루션 변경
						var form = this;
						
						var DBMSType = "S";
						var data = null;
						
						data = "AGENT_VERSION="+agentVersion+
								"&SERVER_URL="+serverUrl+
								"&VIEW_TYPE=SCANVIEW"+
								"&USE_DEL="+modifyFlag+
								"&USE_STAT="+modifyFlag+
								"&USE_BM="+blackMakingYn+
								"&USE_DEL_BM="+blackMakingFlag+
								"&RGST_PRSN_ID="+userId+
								"&RGST_PRSN_NM="+userName+
								"&ORG_TYPE="+orgType+
								"&ORG_ID="+orgId+
								"&ORG_NM="+orgNm+
								"&RES_NO="+resNo+
								"&PARENT_SCAN_ID="+scanId+
								"&DBMSType="+DBMSType
								;
	
						callViewer(data);
					
						break;
					
					case "btnPaper" :
						
						var requestKey = PAGE.GRID1.getSelectedRowData().requestKey;
						var resNo = PAGE.GRID1.getSelectedRowData().resNo;
						var scanId = PAGE.GRID1.getSelectedRowData().scanId;
						var scanFileCnt = PAGE.GRID1.getSelectedRowData().scanFileCnt;
						
						if(scanId == null || scanFileCnt == 0){
							mvno.ui.alert("서식지가 존재하지 않습니다.");
							return;
						}
						
						// 스캔솔루션 변경
						var form = this;
						
						var DBMSType = "S";
						
						var data = null;
						
						data = "AGENT_VERSION="+agentVersion+
								"&SERVER_URL="+serverUrl+
								"&VIEW_TYPE=MSPVIEW"+
								"&USE_DEL="+modifyFlag+
								"&USE_STAT="+modifyFlag+
								"&USE_BM="+blackMakingYn+
								"&USE_DEL_BM="+blackMakingFlag+
								"&RGST_PRSN_ID="+userId+
								"&RGST_PRSN_NM="+userName+
								"&ORG_TYPE="+orgType+
								"&ORG_ID="+orgId+
								"&ORG_NM="+orgNm+
								"&RES_NO="+PAGE.GRID1.getSelectedRowData().resNo+
								"&PARENT_SCAN_ID="+PAGE.GRID1.getSelectedRowData().scanId+
								"&DBMSType="+DBMSType
								;
						callViewer(data);
						
						break;
				}
			}
		}
		, FORM2 : {
				title : "변경신청내역 등록",
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					, {type:"block", list:[
						//{type:"input", name:"searchSubscriberNo", label:"전화번호", width:140, offsetLeft:20, required:true, maxLength:11}
						{type:"select", name:"searchGb", width:80, options:[{value:"01", text:"전화번호", selected:true}, {value:"02", text:"계약번호"}]}
						, {type:"newcolumn"}
						, {type:"input", name:"searchGbVal", width:134, required:true, maxLength:11}
						, {type:"newcolumn"}
						, {type:"button", name:"btnFind", value:"검색"}
						, {type:"newcolumn"}
						, {type:"input", name:"custNm", label:"고객명", width:120, offsetLeft:20, readonly:true, disabled:true}
					]}
					, {type:"block", list:[
						{type:"input", name:"subStatusNm", label:"계약상태", width:190, offsetLeft:20, readonly:true, disabled:true}
						, {type:"newcolumn"}
						, {type:"calendar", name:"openDt", label:"개통일자", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", width:120, offsetLeft:20, readonly:true, disabled:true}
					]}
					, {type:"block", list:[
						{type:"input", name:"insrNm", label:"가입보험", width:190, offsetLeft:20, readonly:true, disabled:true}
						, {type:"newcolumn"}
						, {type:"input", name:"statNm", label:"보험상태", width:120, offsetLeft:20, readonly:true, disabled:true}
					]}
					, {type:"block", list:[
						{type:"input", name:"procAgntNm", label:"처리점", width:190, offsetLeft:20, readonly:true, disabled:true}
						, {type:"newcolumn"}
						, {type:"input", name:"procrNm", label:"처리자", width:120, offsetLeft:20, readonly:true, disabled:true}
					]}
					, {type:"block", list:[
						{type:"input", name:"usgDt", label:"사용일수", width:190, offsetLeft:20, readonly:true, disabled:true, userdata:{align:"right"}}
						, {type:"newcolumn"}
						, {type:"input", name:"atmOpenYn", label:"ATM개통", width:120, offsetLeft:20, readonly:true, disabled:true}
					]}
					, {type:"block", list:[
						{type:"select", name:"tskTp", label:"업무유형", width:120, offsetLeft:20}
						, {type:"newcolumn"}
						, {type:"input", name:"tskNm", label:"", width:270, disabled:true}
					]}
					, {type:"block", list:[
						{type:"select", name:"insrCd", label:"단체보험", width:120, offsetLeft:20}
						, {type:"newcolumn"}
						, {type:"checkbox", name:"clauseInsuranceFlag", label:"단체상해보험가입동의", labelWidth:120, position:"label-right", value:"Y", offsetLeft:20}
					]}
					, {type: "fieldset", label: "단말보험", name:"IINSR", inputWidth:480, list:[
						{type:"block", list:[
							{type:"select", name:"insrProdCd", label:"보험상품", width:190, offsetLeft:10}
							, {type:"newcolumn"}
							, {type:"checkbox", name:"clauseInsrProdFlag", label:"단말보험가입동의", labelWidth:120, position:"label-right", value:"Y", offsetLeft:20}
						]}
						, {type: "fieldset", label: "보험가입기본정보", name:"BASE", inputWidth:430, list:[
							, {type:"block", list:[
								{type:"calendar", name:"intmInsrPsblDay", label:"가입가능일자", width:100, labelWidth:80, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", readonly:true, disabled:true}
								, {type:"newcolumn"}
								, {type:"input", name:"intmInsrPsblYn", label:"가입가능여부", width:100, labelWidth:80, offsetLeft:10, readonly:true, disabled:true}
							]}
							, {type:"block", list:[
								{type:"input", name:"insrProdNm", label:"보험상품명", width:100, labelWidth:80, readonly:true, disabled:true}
								, {type:"newcolumn"}
								, {type:"input", name:"intmInsrStatNm", label:"보험가입상태", width:100, labelWidth:80, offsetLeft:10, readonly:true, disabled:true}
							]}
							, {type:"block", list:[
								{type:"input", name:"reqBuyNm", label:"구매유형", width:100, labelWidth:80, readonly:true, disabled:true}
								, {type:"newcolumn"}
								, {type:"calendar", name:"dvcChgDt", label:"기변일자", width:100, labelWidth:80, offsetLeft:10, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", readonly:true, disabled:true}
							]}
						]}
						, {type: "fieldset", label: "보험가입단말정보", name:"INTM", inputWidth:430, list:[
							{type:"block", list:[
								{type:"input", name:"insrModelNm", label:"단말모델명", width:100, labelWidth:80, readonly:true, disabled:true}
								, {type:"newcolumn"}
								, {type:"input", name:"insrModelId", label:"단말모델ID ", width:100, labelWidth:80, offsetLeft:10, readonly:true, disabled:true}
							]}
							, {type:"block", list:[
								{type:"input", name:"insrIntmSrlNo", label:"단말일련번호", width:100, labelWidth:80, readonly:true, disabled:true}
							]}
						]}
						, {type: "fieldset", label: "가입대상단말정보", name:"TINTM", inputWidth:430, list:[
							{type:"block", list:[
								{type:"input", name:"trgtModelNm", label:"단말모델명", width:100, labelWidth:80, readonly:true, disabled:true}
								, {type:"newcolumn"}
								, {type:"input", name:"trgtModelId", label:"단말모델ID ", width:100, labelWidth:80, offsetLeft:10, readonly:true, disabled:true}
							]}
							, {type:"block", list:[
								{type:"input", name:"trgtIntmSrlNo", label:"단말일련번호", width:100, labelWidth:80, readonly:true, disabled:true}
							]}
						]}
					]}
					, {type:"block", list:[
						{type:"input", name:"rmk", label:"처리내용", width:396, rows:3, offsetLeft:20, required:true, maxLength:66}
					]}
					, {type:"hidden", name:"contractNum"}
					, {type:"hidden", name:"pppo"}
					, {type:"hidden", name:"subStatus"}
					, {type:"hidden", name:"openAgntCd"}
					, {type:"hidden", name:"scanId"}
					, {type:"hidden", name:"subscriberNo"}
					, {type:"hidden", name:"newScanId"}
					, {type:"hidden", name:"scanType"}
					, {type:"hidden", name:"age"}
					, {type:"hidden", name:"oldInsrCd"}
					, {type:"hidden", name:"customerType"}
					, {type:"hidden", name:"statCd"}
					, {type:"hidden", name:"reqBuyType"}
				]
				, buttons : {
					center : {
						  btnScan : { label : "스캔하기" }
						, btnSave : { label: " 저장" }
						, btnClose : { label : "닫기" }
					}
				}
				, onValidateMore : function(data) {
					var form = this;
					
					// 계약번호 유무 확인
					//if(mvno.cmn.isEmpty(form.getItemValue("contractNum"))) {
					if(data.contractNum == "") {
						//this.pushError("searchSubscriberNo", "전화번호", "전화번호 검색을 먼저 해주십시오.");
						this.pushError("searchGbVal", "검색어", "검색어를 입력하세요.");
					}
					
					// 2018-09-13 해지상태인 경우 업무유형 체크
					if(data.subStatus == "C" && data.tskTp != "CAN"){
						this.pushError("tskTp", "업무유형", "해지 상태인 경우 해지업무만 등록가능합니다.");
					}
					
					// 업무유형이 직접입력시 입력값 확인
					//if(mvno.cmn.isEmpty(form.getItemValue("tskTp")) && mvno.cmn.isEmpty(form.getItemValue("tskNm"))) {
					if(data.tskTp == "" && data.tskNm == "") {
						this.pushError("tskNm", "업무유형", "업무유형을 입력해 주십시오.");
					}
					
					// textarea maxlength 값이 IE9에서 동작하지 않아 validation 추가.
					var inputSize = ($('textarea[name="rmk"]').val()).length;
					var maxLength = $('textarea[name="rmk"]').attr('maxLength');
					if (inputSize > 0 && inputSize > maxLength) {
						this.pushError("rmk", "처리내용", "처리내용 항목은 "+maxLength+"자까지 입력가능합니다.");
					}
					
				}
				, onChange : function(name, value) {
					var form = this;
					//if(name == "searchSubscriberNo") {
					if(name == "searchGb" || name == "searchGbVal") {
						form.setItemValue("contractNum", "");
						form.setItemValue("custNm", "");
						form.setItemValue("pppo", "");
						form.setItemValue("subStatus", "");
						form.setItemValue("openDt", "");
						form.setItemValue("openAgntCd", "");
						form.setItemValue("scanId", "");
						form.setItemValue("payClNm", "");
						form.setItemValue("subStatusNm", "");
						form.setItemValue("subscriberNo", "");
						form.setItemValue("atmOpenYn", "");
						form.setItemValue("intmInsrPsblDay",			"");
						form.setItemValue("intmInsrPsblYn",	"");
						form.setItemValue("insrProdNm",		"");
						form.setItemValue("intmInsrStatNm",	"");
						form.setItemValue("reqBuyNm",		"");
						form.setItemValue("reqBuyType",		"");
						form.setItemValue("dvcChgDt",		"");
						form.setItemValue("insrModelNm",	"");
						form.setItemValue("insrModelId",	"");
						form.setItemValue("insrIntmSrlNo",	"");
						form.setItemValue("trgtModelNm",	"");
						form.setItemValue("trgtModelId",	"");
						form.setItemValue("trgtIntmSrlNo",	"");
					}
					
					if(name == "tskTp") {
						
						//윈도우 창 사이즈 변경
						if(value == "CARE"){
							setWinHeight(form, 750);
						} else {
							setWinHeight(form, 400);
						}
						
						form.setItemValue("insrCd", "");
						form.setItemValue("clauseInsuranceFlag", "");
						form.setItemValue("insrProdCd", "");
						form.setItemValue("clauseInsrProdFlag", "");
						
						if(mvno.cmn.isEmpty(form.getItemValue("tskTp"))) {
							form.enableItem("tskNm");
							form.disableItem("insrCd");
							form.disableItem("clauseInsuranceFlag");
							form.hideItem("IINSR");
						} else {
							form.setItemValue("tskNm", "");
							form.disableItem("tskNm");
							
							if(value == "INS"){
								if(grpInsrYn != "Y") {
									mvno.ui.alert("단체상해보험 가입 이벤트가 없습니다.");
									return;
								}
								
								form.enableItem("insrCd");
								form.enableItem("clauseInsuranceFlag");
								form.hideItem("IINSR");
							} else if(value == "CARE") {
								form.disableItem("insrCd");
								form.disableItem("clauseInsuranceFlag");
								form.showItem("IINSR");
							} else {
								form.disableItem("insrCd");
								form.disableItem("clauseInsuranceFlag");
								form.hideItem("IINSR");
							}
						}
					}
					
					if(name == "insrProdCd"){
						
						var sdata = {searchGb : form.getItemValue("searchGb")
								, searchGbVal : form.getItemValue("searchGbVal")
								, insrProdCd  : value
							};
						
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getInsrPsblYn.json", sdata, function(resultData) {
							
							form.setItemValue("intmInsrPsblDay", resultData.result.data.rows[0].intmInsrPsblDay);
							form.setItemValue("intmInsrPsblYn", resultData.result.data.rows[0].intmInsrPsblYn);
						});
					}
					
				}
				, onButtonClick : function(btnId) {
					var form = this;
					
					switch(btnId){
						case "btnFind" :
							/* if(mvno.cmn.isEmpty(form.getItemValue("searchSubscriberNo"))) {
								mvno.ui.alert("검색할 전화번호를 입력해 주십시오.");
								return;
							} */
							if(mvno.cmn.isEmpty(form.getItemValue("searchGbVal"))) {
								mvno.ui.alert("검색어를 입력하세요.");
								return;
							}
							
							var sdata = {searchGb : form.getItemValue("searchGb")
										, searchGbVal : form.getItemValue("searchGbVal")
									};
							
							//mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getContractInfo.json", {searchSubscriberNo:form.getItemValue("searchSubscriberNo")}, function(resultData) {
							mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getContractInfo.json", sdata, function(resultData) {
								var data = resultData.result.data.rows[0];
								if(data != null){
									form.setItemValue("contractNum",	data.contractNum);
									form.setItemValue("custNm",			data.custNm);
									form.setItemValue("pppo",			data.pppo);
									//form.setItemValue("payClNm",		data.payClNm);
									form.setItemValue("subStatus",		data.subStatus);
									form.setItemValue("openDt",			data.openDt);
									form.setItemValue("openAgntCd",		data.openAgntCd);
									form.setItemValue("scanId",			data.scanId);
									form.setItemValue("subStatusNm",	data.subStatusNm);
									form.setItemValue("subscriberNo",	data.subscriberNo);
									form.setItemValue("age",			data.age);
									form.setItemValue("oldInsrCd",		data.insrCd);
									form.setItemValue("customerType",	data.customerType);
									form.setItemValue("insrNm",			data.insrNm);
									form.setItemValue("statCd",			data.statCd);
									form.setItemValue("statNm",			data.statNm);
									form.setItemValue("atmOpenYn",		data.atmOpenYn);	/* ATM개통여부 */
									form.setItemValue("usgDt",			data.usgDt);		/* 개통일 제외 사용일수 */
									form.setItemValue("intmInsrPsblDay",			data.intmInsrPsblDay);
									form.setItemValue("intmInsrPsblYn",	data.intmInsrPsblYn);
									form.setItemValue("insrProdNm",		data.insrProdNm);
									form.setItemValue("intmInsrStatNm",	data.intmInsrStatNm);
									form.setItemValue("reqBuyNm",		data.reqBuyNm);
									form.setItemValue("reqBuyType",		data.reqBuyType);
									form.setItemValue("dvcChgDt",		data.dvcChgDt);
									form.setItemValue("insrModelNm",	data.insrModelNm);
									form.setItemValue("insrModelId",	data.insrModelId);
									form.setItemValue("insrIntmSrlNo",	data.insrIntmSrlNo);
									form.setItemValue("trgtModelNm",	data.trgtModelNm);
									form.setItemValue("trgtModelId",	data.trgtModelId);
									form.setItemValue("trgtIntmSrlNo",	data.trgtIntmSrlNo);
									
									var sData = {usgYn:"Y", rprsPrdtId:data.trgtModelId, reqBuyType:data.reqBuyType};
									mvno.cmn.ajax4lov(ROOT + "/insr/insrMgmt/getChgInsrCombo.json", sData, form, "insrProdCd"); // 단말보험상품
									
								} else {
									mvno.ui.alert("계약정보가 존재하지 않습니다.");
									return;
								}
							});
							
							break;
						case "btnScan" :
							// 스캔하기
							var form = this;
							var scanId = null;
							var DBMSType = "C";
							var scanType = "new";
							//scanId 받아오기
							mvno.cmn.ajax(ROOT + "/appForm/getScanId.json", "", function(resultData) {
								scanId = resultData.result.scanId;
								form.setItemValue("newScanId", scanId);
								
								var data = null;
								
								data = "AGENT_VERSION="+agentVersion+
										"&SERVER_URL="+serverUrl+
										"&VIEW_TYPE=SCAN"+
										"&USE_DEL="+modifyFlag+
										"&USE_STAT="+modifyFlag+
										"&USE_BM="+blackMakingYn+
										"&USE_DEL_BM="+blackMakingFlag+
										"&RGST_PRSN_ID="+userId+
										"&RGST_PRSN_NM="+userName+
										"&ORG_TYPE="+orgType+
										"&ORG_ID="+orgId+
										"&ORG_NM="+orgNm+
										"&RES_NO="+
										"&PARENT_SCAN_ID="+scanId+
										"&DBMSType="+DBMSType+
										"&CUST_NM="
										;
									
								callViewer(data);
								
							}); 
							
							break;
							
						case "btnSave" :
							// validation 추가
							if(form.getItemValue("tskTp") == "INS"){
								if(form.getItemValue("customerType") != "I"){
									mvno.ui.alert("개인고객인 경우 단체상해보험 가입이 가능합니다.");
									return;
								}
								
								if(mvno.cmn.isEmpty(form.getItemValue("insrCd"))){
									mvno.ui.alert("단체상해보험을 선택하세요.");
									return;
								}
								
								if(form.isItemChecked("clauseInsuranceFlag") == false){
									mvno.ui.alert("단체상해보험가입동의를 체크하세요.");
									return;
								}
								
								if(form.getItemValue("insrCd") == form.getItemValue("oldInsrCd")){
									mvno.ui.alert("기존 보험과 동일한 보험입니다.");
									return;
								}
								
								// 국민키즈안심보험을 제외한 단체보험은 만 15세 이상 가입가능
								if(form.getItemValue("insrCd") != "C0" && Number(form.getItemValue("age")) < 15){
									mvno.ui.alert("만 15세 이상 가입 가능합니다.");
									return;
								}
								
								if(form.getItemValue("newScanId") == ""){
									mvno.ui.alert("단체상해보험가입 등록시 서식지가 없는 경우<br />보험가입 처리가 되지않습니다.<br />보험가입동의서를 등록하세요.");
									return;
								}
							}
							
							if(form.getItemValue("tskTp") == "INC" && form.getItemValue("statCd") != "A"){
								mvno.ui.alert("해지할 단체상해보험이 없습니다.");
								return;
							}
							
							// 2018-12-19
							// 판매점에서 등록한 서식지의 경우 변경신청정보 등록시 서식지를 등록할 수 없어 서식지체크 삭제
							// 단체보험 가입에 대한 안내메세지로 대체
// 							if(form.getItemValue("newScanId") == "" && form.getItemValue("tskTp") != "INC"){
// 								mvno.ui.alert("서식지정보(SCAN_ID)가 존재하지 않습니다.<br />스캔하기 진행 후 저장하세요.");
// 								return;
// 							}
							
							// 2019-02-11, 단말보험 validation
							if(form.getItemValue("tskTp") == "CARE") {
								if(mvno.cmn.isEmpty(form.getItemValue("insrProdCd"))) {
									mvno.ui.alert("단말보험을 선택하세요.");
									return;
								}
								
								if(form.isItemChecked("clauseInsrProdFlag") == false) {
									mvno.ui.alert("단말보험가입동의를 체크하세요.");
									return;
								}
								
								if(form.getItemValue("intmInsrPsblYn") != 'Y' ){
									mvno.ui.alert("가입가능일자를 확인하세요.");
									return;
								}
								
								if(form.getItemValue("newScanId") == ""){
									mvno.ui.alert("단말보험 가입시<br>보험가입동의서를 등록하세요.");
									return;
								}
							}
							
							var strMsg = "저장하시겠습니까?";
							if(!mvno.cmn.isEmpty(form.getItemValue("newScanId"))) {
								strMsg = "서식지가 있는 경우<br>개통서식지에 합본처리합니다.<br>" + strMsg;
							}
							
							mvno.ui.confirm(strMsg, function() {
								mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/insertChgRcptMst.json", form, function(result) {
									mvno.ui.closeWindowById("FORM2", true);
									mvno.ui.notify("NCMN0004");
									PAGE.GRID1.refresh();
								});
							});
							
							break;
							
						case "btnClose" :
							mvno.ui.closeWindowById(form, true);
							break;
					}
				}
		}
	}; // end dhx

	//스캔 호출
	function callViewer(data){
		var url = scanSearchUrl + "?" + encodeURIComponent(data);
		
		// 타임아웃 설정 - 10초이내 응답없으면 서비스 실행상태가 아님.
		var timeOutTimer = window.setTimeout(function (){
			mvno.ui.confirm("이미지 프로그램이 설치되지 않았거나 실행중이 아닙니다. 설치페이지로 이동 하시겠습니까?",function(){
				window.open(scanDownloadUrl, "", "width=750,height=650,resizable=no,scrollbars=yes,top=10,left=10");		// 설치페이지로 이동
			});
		}, 10000);
		
		$.ajax({
			type : "GET", 
			url : url,
			crossDomain : true,
			dataType : 'jsonp',
			success : function(args){
				window.clearTimeout(timeOutTimer);
				if(args.RESULT == "SUCCESS") {
					console.log("SUCCESS");
					PAGE.GRID1.refresh();
				} else if(args.RESULT == "ERROR_TYPE2") {
					mvno.ui.alert("버전이 다릅니다. 설치페이지로 이동해주세요.");
					window.open(scanDownloadUrl, "", "width=750,height=650,resizable=no,scrollbars=yes,top=10,left=10");		// 설치페이지로 이동
				} else {
					mvno.ui.alert(args.RESULT + " : " + args.RESULT_MSG);
				}
			}
		});
	}	
	
	function setWinHeight(form, height) {
		var $el = $(form.cont);
		
		var dcw = $el.closest(".dhx_cell_wins");
		
		if(! dcw[0])  return;
		
		var winId = dcw[0]._winId;
		
		var win = _WINDOWS_.window(winId);
		
		win.setDimension(null, height);
		win.centerOnScreen();
	}
	
	var PAGE = {
		 title: "${breadCrumb.title}",
		 breadcrumb: "${breadCrumb.breadCrumb}",
		 buttonAuth: ${buttonAuth.jsonString},
		 onOpen:function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0070", orderBy:"etc6"}, PAGE.FORM1, "tskTp"); // 업무유형
			mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2019"}, PAGE.FORM1, "searchClCd"); // 검색구분
		}
	};

</script>