<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<div id="FORM2" class="section-box"></div>

<style>
fieldset input:read-only {
	background-color:#fae0d4;
}
</style>

<!-- Script -->
<script type="text/javascript">
	
	var orgId = "${orgnInfo.orgnId}";
	var orgNm = "${orgnInfo.orgnNm}";
	var userId = "${loginInfo.userId}";
	var userName = "${loginInfo.userName}";
	var typeCd = "${orgnInfo.typeCd}";
	var orgType = "";
	if(typeCd == "10") {
		orgType = "K";
	}else if(typeCd == "20") {
		orgType = "D";
	}else if(typeCd == "30") {
		orgType = "S";
	}
	var blackMakingYn = "Y";					// 블랙마킹사용권한
	var blackMakingFlag = "N";					// 블랙마킹해제권한 (마스킹권한여부에 따라 변화)
	var maskingYn = "${maskingYn}";				// 마스킹권한
	if(maskingYn == "Y") {
		blackMakingFlag = "Y";
	}
	var agentVersion = "${agentVersion}";		// 스캔버전
	var serverUrl = "${serverUrl}";				// 서버환경 (로컬 : L, 개발 : D, 운영 : R)
	var scanSearchUrl = "${scanSearchUrl}";		// 스캔호출 url
	var scanDownloadUrl = "${scanDownloadUrl}";	// 스캔파일 download url
	
	var fromDt = new Date(new Date().setDate(new Date().getDate() - 6)).format("yyyymmdd");
	var toDt = new Date().format("yyyymmdd");
	
	//권한 정의
	var modifyFlag = "N";
	var userOrgnTypeCd = '${loginInfo.userOrgnTypeCd}';
	if(userOrgnTypeCd == "10") {
		modifyFlag = "Y";
	}
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"lstComActvDateF", label:"개통일자", value:fromDt, calendarPosition: "bottom", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"lstComActvDateT", label:"~", value:toDt, calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type:"newcolumn"}
					, {type:"input", label:"단말모델명", name:"fstModelNm", width:120, offsetLeft:95}
					, {type:"newcolumn"}
					, {type:"select", label:"검증결과", name: "vrfyStatCd", width:120, offsetLeft:50}
				]}
				, {type:"block", list:[
					{type:"select", label:"검색구분", name: "searchCd", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchVal", width:165, offsetLeft:0, maxLength:60}
					, {type:"newcolumn"}
					, {type:"select", label:"할부개월", name:"instMnthCnt", width:120, offsetLeft:45}
					, {type:"newcolumn"}
					, {type:"select", label:"할인유형", name:"sprtTp", width:120, offsetLeft:50}
				]}
				, {type:"block", list:[
					{type:"input", label:"대리점", name: "agntId", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"button", value:"검색", name:"btnOrgFind"}
					, {type:"newcolumn"}
					, {type:"input", name: "agntNm", width:115, readonly: true}
					, {type:"newcolumn"}
					, {type:"select", label : "모집경로", width : 120, offsetLeft : 45, name : "onOffType"}
					, {type:"newcolumn"}
					, {type:"select", label : "개통유형", width : 120, offsetLeft : 50, name : "operType", options:[{value:"", text:"- 전체 -"}, {value:"NAC", text:"신규"}, {value:"C07", text:"기기변경"}]}
				]}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search3"} 
			]
			, onChange: function (name, value) {
				var form = this;
				
				switch(name) {
					case "searchCd":
						if(mvno.cmn.isEmpty(value)) {
							form.setItemValue("lstComActvDateF", fromDt);
							form.setItemValue("lstComActvDateT", toDt);
							form.setItemValue("searchVal", "");
							
							form.enableItem("lstComActvDateF");
							form.enableItem("lstComActvDateT");
							form.disableItem("searchVal");
						} else {
							form.setItemValue("lstComActvDateF", "");
							form.setItemValue("lstComActvDateT", "");
							
							form.disableItem("lstComActvDateF");
							form.disableItem("lstComActvDateT");
							
							form.enableItem("searchVal");
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
						
						PAGE.GRID1.list(ROOT + "/rcp/formVrfyMgmt/getFormVrfyList.json", form);
						
						break;
					case "btnOrgFind":
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("agntId", result.orgnId);
							form.setItemValue("agntNm", result.orgnNm);
						});
						break;
				}
			}
		}
		
		, GRID1 : {
			css : {
				height : "500px"
			}
			, title : "서식지검증목록"
			, header : "번호,상품구분,계약번호,고객명,생년월일,나이(만),휴대폰번호,요금제,할인유형,판매정책명,약정개월수,할부개월수,단말모델,단말일련번호,상태,해지사유,모집경로,유입경로,추천인구분,추천인정보,녹취여부,대리점,판매점명,판매자명,판매자ID,기변유형,기변일자,기변모델명,기변대리점,프로모션명,개통유형,할부원금,1차검증결과,1차검증자,1차검증일자,2차검증결과,2차검증자,2차검증일자,수정자,수정일자"
			, columnIds : "ROW_NUM,prodTypeNm,contractNum,cstmrName,birth,age,subscriberNo,fstRateNm,sprtTpNm,salePlcyNm,enggMnthCnt,instMnthCnt,fstModelNm,fstModelSrlNum,subStatusNm,canRsn,onOffTypeNm,openMarketReferer,recommendFlagNm,recommendInfo,recYn,agentNm,shopNm,shopUsrNm,shopUsrId,dvcOperTypeNm,dvcChgDt,dvcModelNm,dvcAgntNm,promotionNm,operTypeNm,modelInstallment,fstRsltNm,fstUsrNm,fstVrfyDttm,sndRsltNm,sndUsrNm,sndVrfyDttm,rvisnNm,rvisnDttm" //39
			, widths : "40,80,80,100,60,60,100,200,80,300,80,80,120,120,60,120,60,60,100,70,60,200,200,80,100,80,80,80,100,200,100,100,100,100,120,100,100,120,100,120"
			, colAlign : "center,center,center,center,center,right,center,left,center,left,center,center,left,center,center,left,center,center,center,center,center,left,left,left,left,center,center,left,left,left,center,right,center,center,center,center,center,center,center,center"
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ron,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,int,int,str,str,sr,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,int,str,str,str,str,str,str,str,str,str"
			, paging: true
			, showPagingAreaOnInit: true
			, pageSize:20
			, buttons : {
				hright : {
					btnExcel:{ label:"엑셀다운로드"}
				}
				, right : {
					btnVrfy : { label : "검증결과등록" }
				}
			}
			, onRowDblClicked : function(rowId, celInd) {
				PAGE.GRID1.callEvent("onButtonClick", ["btnVrfy"]);
			}
			, onButtonClick : function(btnId) {
				
				var grid = this;
				
				switch (btnId) {
					case "btnExcel":
						
						if(PAGE.buttonAuth.crud.exelAbableYn == "N"){
							mvno.ui.alert("권한이 없습니다.");
							break;
						}
						
						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}
						
						var searchData =  PAGE.FORM1.getFormData(true);
						
						mvno.cmn.download(ROOT + "/rcp/formVrfyMgmt/getFormVrfyListByExcel.json?menuId=MSP1007001", true, {postData:searchData}); 
						
						break;
					
					case "btnVrfy":
						
						var data = grid.getSelectedRowData();
						
						if(data == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.clear();
						
						PAGE.FORM2.enableItem("FSTVF");
						PAGE.FORM2.enableItem("SNDVF");
						$('#FORM2_btnSave').show();
						
						
						var param = {contractNum : data.contractNum};
						mvno.cmn.ajax2(ROOT + "/rcp/formVrfyMgmt/getFormVrfyInfo.json", param, function(resultData) {
							var rDate = resultData.result.data;
							
							PAGE.FORM2.setFormData(rDate, true);
							
							if(rDate.fstModifyYn == "N") {
								PAGE.FORM2.disableItem("FSTVF");
							}
							
							if(rDate.sndModifyYn == "N") {
								PAGE.FORM2.disableItem("SNDVF");
							}
							
							if(rDate.fstModifyYn == "N" && rDate.sndModifyYn == "N") {
								$('#FORM2_btnSave').hide();
							}
							
							mvno.ui.popupWindowById("FORM2", "검증결과등록", 900, 760, {
								onClose: function(win) {
									if (! PAGE.FORM2.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
									});
								}
							});
						});
						
						break;
						
				}
			}
		}
		
		, FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:100, blockOffset:0}
				,{type: "fieldset", label: "개통정보", name:"OPENINFO", inputWidth:810, list:[
					{type:"block", list:[
						{type:"input", label:"계약번호", name:"contractNum", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"대리점", name:"agentNm", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"고객명", name:"cstmrName", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
					]}
					, {type:"block", list:[
						, {type:"input", label:"나이", name:"age", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"요금제", name:"fstRateNm", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"할부기간", name:"instMnthCnt", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
					]}
					, {type:"block", list:[
						, {type:"input", label:"할인유형", name:"sprtTpNm", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"개통유형", name:"operTypeNm", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"단말모델명", name:"fstModelNm", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
					]}
					, {type:"block", list:[
						, {type:"input", label:"할부원금", name:"modelInstallment", width:140, userdata:{align:"right"}, offsetLeft:10, numberFormat:"0,000,000,000", userdata:{align:"right"}, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"월할부금", name:"monthlyInstallment", width:140, userdata:{align:"right"}, offsetLeft:10, numberFormat:"0,000,000,000", userdata:{align:"right"}, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"월평균할부수수료", name:"monthlyFee", width:140, userdata:{align:"right"}, offsetLeft:10, numberFormat:"0,000,000,000", userdata:{align:"right"}, readonly:true}
					]}
					, {type:"block", list:[
						, {type:"input", label:"월납부액", name:"monthylTotal", width:140, offsetLeft:10, numberFormat:"0,000,000,000", userdata:{align:"right"}, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"총할부수수료", name:"totalFee", width:140, offsetLeft:10, numberFormat:"0,000,000,000", userdata:{align:"right"}, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"판매점", name:"shopNm", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
					]}
					, {type:"block", list:[
						, {type:"input", label:"상태", name:"subStatusNm", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"해지사유", name:"canRsn", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"약정개월수", name:"enggMnthCnt", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
					]}
					, {type:"block", list:[
						, {type:"input", label:"심플약정기간", name:"simEnggMnthCnt", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"생년월일", name:"birth", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"휴대폰번호", name:"subscriberNo", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
					]}
					, {type:"block", list:[
						, {type:"input", label:"정보/광고 수신 위탁 동의", name:"reqClausePriAdFlagNm", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"선택형 단체보험 가입여부", name:"insrYn2", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"선택형 단체보험명", name:"insrNm2", width:140, userdata:{align:"right"}, offsetLeft:10, readonly:true}
					]}
				]}
				,{type: "fieldset", label: "기본정보", name:"BASEINFO", inputWidth:810, list:[
					{type:"block", list:[
						{type:"select", label:"광고수신동의여부", name:"clausePriAdFlag", width:140, offsetLeft:10, options:[{text: "- 선택 -", value: ""}, {text: "동의", value: "Y"}, {text: "미동의", value: "N"}, {text: "확인불가", value: "X"}] }
						, {type:"newcolumn"}
						, {type:"select", label:"선택형상해보험서식지등록여부", name:"insrFormFlag", width:140, offsetLeft:10, options:[{text: "- 선택 -", value: ""}, {text: "등록", value: "Y"}, {text: "미등록", value: "N"}] }
						, {type:"newcolumn"}
						, {type:"select", label:"선택형상해보험적격여부", name:"insrCfmtFlag", width:140, offsetLeft:10, options:[{text: "- 선택 -", value: ""}, {text: "적격", value: "Y"}, {text: "부적격", value: "N"}, {text: "무관", value: "X"}] }
					]}
				]}
				,{type:"newcolumn"}
				,{type: "fieldset", label: "1차 검증", name:"FSTVF", inputWidth:400, list:[
					{type:"block", list:[
						{type:"select", label:"검증결과", name:"fstRsltCd", width:140, offsetLeft:10, options:[{text: "- 선택 -", value: ""}, {text: "적격", value: "FOK"}, {text: "부적격", value: "FNOK"}, {text: "비대상", value: "EXP"}] }
						, {type:"newcolumn"}
						, {type:"input", label:"비고", name:"fstRemark", width:240, offsetLeft:10, rows:7, maxLength:1000}
					]}
				]}
				,{type:"newcolumn"}
				,{type: "fieldset", label: "2차 검증", name:"SNDVF", inputWidth:400, offsetLeft:8, list:[
					{type:"block", list:[
						{type:"select", label:"검증결과", name:"sndRsltCd", width:140, offsetLeft:10, options:[{text: "- 선택 -", value: ""}, {text: "적격", value: "SOK"}, {text: "부적격", value: "SNOK"}] }
						, {type:"newcolumn"}
						, {type:"input", label:"비고", name:"sndRemark", width:240, offsetLeft:10, rows:7, maxLength:1000}
					]}
				]}
				,{type:"newcolumn"}
				,{type: "fieldset", label: "1차 조치결과", name:"FSTACTRSLT", inputWidth:400, disabled:true, list:[
					{type:"block", list:[
						{type:"select", label:"조치결과", name:"fstActCd", width:140, offsetLeft:10, options:[{text: "- 선택 -", value: ""}, {text: "조치완료", value: "SOK"}, {text: "미조치", value: "SNOK"}] }
						, {type:"newcolumn"}
						, {type:"input", label:"비고", name:"fstActRemark", width:240, offsetLeft:10, rows:3, maxLength:1000}
					]}
				]}
				,{type:"newcolumn"}
				,{type: "fieldset", label: "2차 조치결과", name:"SNDACTRSLT", inputWidth:400, offsetLeft:8, disabled:true, list:[
					{type:"block", list:[
						{type:"select", label:"조치결과", name:"sndActCd", width:140, offsetLeft:10, options:[{text: "- 선택 -", value: ""}, {text: "조치완료", value: "SOK"}, {text: "미조치", value: "SNOK"}] }
						, {type:"newcolumn"}
						, {type:"input", label:"비고", name:"sndActRemark", width:240, offsetLeft:10, rows:3, maxLength:1000}
					]}
				]}
			]
			, buttons : {
				center : {
					btnPaper : { label : "서식지보기" },
					btnScnSarch : { label : "스캔검색" },
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					
					case "btnPaper":
						
						if(mvno.cmn.isEmpty(PAGE.GRID1.getSelectedRowData().scanId)) {
							mvno.ui.alert("등록된 서식지가 없습니다.");
							return;
						}
												
						appFormView(PAGE.GRID1.getSelectedRowData().scanId, PAGE.GRID1.getSelectedRowData().resNo);
						
						break;
					
					case "btnScnSarch":
						
						// MSP_CAN으로 이관된 고객은 사용x
						var cstmrName = PAGE.GRID1.getSelectedRowData().cstmrName;	//고객명
						var resNo = PAGE.GRID1.getSelectedRowData().resNo;
						var scanId = PAGE.GRID1.getSelectedRowData().scanId;
						
						if(cstmrName == "이관고객"){
							mvno.ui.alert("해지이관 고객은 스캔 검색을 할 수 없습니다.");
							return;
						}
						
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
						
					case "btnSave":
						
						if(PAGE.buttonAuth.crud.creatAbableYn == "N"){
							mvno.ui.alert("권한이 없습니다.");
							break;
						}
						
						mvno.ui.confirm("저장하시겠습니까?", function() {
							mvno.cmn.ajax(ROOT + "/rcp/formVrfyMgmt/regFormVrfyRslt.json", form, function(){
								mvno.ui.notify("NCMN0004");
								PAGE.GRID1.refresh();
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
	}
	
	function appFormView(scanId, resNo){
		
		var data = null;
		
		data = "AGENT_VERSION="+agentVersion+
			"&SERVER_URL="+serverUrl+
			"&VIEW_TYPE=MSPVIEW"+
			"&USE_DEL=N"+
			"&USE_STAT=N"+
			"&USE_BM="+blackMakingYn+
			"&USE_DEL_BM="+blackMakingFlag+
			"&RGST_PRSN_ID="+userId+
			"&RGST_PRSN_NM="+userName+
			"&ORG_TYPE="+orgType+
			"&ORG_ID="+orgId+
			"&ORG_NM="+orgNm+
			"&RES_NO="+resNo+
			"&PARENT_SCAN_ID="+scanId+
			"&DBMSType=S"
			;
		
		callViewer(data);
		
	}
	
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
			dataType : "jsonp",
			success : function(args){
				window.clearTimeout(timeOutTimer);
				if(args.RESULT == "SUCCESS") {
					console.log("SUCCESS");
				} else if(args.RESULT == "ERROR_TYPE2") {
					mvno.ui.alert("버전이 다릅니다. 설치페이지로 이동해주세요.");
					window.open(scanDownloadUrl, "", "width=750,height=650,resizable=no,scrollbars=yes,top=10,left=10");		// 설치페이지로 이동
				} else {
					mvno.ui.alert(args.RESULT + " : " + args.RESULT_MSG);
				}
			}
		});
	}
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		buttonAuth: ${buttonAuth.jsonString}
		, onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			PAGE.FORM1.disableItem("searchVal");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchCd");					// 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0079", orderBy:"etc1"}, PAGE.FORM1, "vrfyStatCd");	// 검증결과
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0058"}, PAGE.FORM1, "sprtTp"); // 할인유형
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2005"}, PAGE.FORM1, "instMnthCnt"); // 할부개월
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM1, "onOffType"); // 모집경로
			//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0049",orderBy:"etc1"}, PAGE.FORM1, "operType"); // 개통유형
			//mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpCommon.json", {grpId:"RENTAL"}, PAGE.FORM1, "prdtId");
		}
	};
</script>
