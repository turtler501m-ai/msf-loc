<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>
<div id="GRID2" class="section"></div>

<div id="FORM2" class="section-box"></div>

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
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"select", width:80, label:"", name:"searchGb", options:[{value:"reqinday", text:"신청일자", selected:true},{value:"strtDttm", text:"가입일자"}]}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchFromDt", label:"", value:fromDt, calendarPosition: "bottom", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchToDt", label:"~", value:toDt, calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type:"newcolumn"}
					, {type:"select", label:"보험상태", name:"insrStatCd", width:110, offsetLeft:20}
					, {type:"newcolumn"}
					, {type:"select", label:"보험상품", name: "insrProdCd", width:190, offsetLeft:20}
				]}
				, {type:"block", list:[
					{type:"select", label:"검색구분", name: "searchCd", width:100, offsetLeft:10, labelWidth:86}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchVal", width:115, offsetLeft:0, maxLength:60}
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
						
						PAGE.GRID2.clearAll();
						
						PAGE.GRID1.list(ROOT + "/insr/insrMgmt/getInsrMemberList.json", form);
						
						break;
				}
			}
		}
		
		, GRID1 : {
			css : {
				height : "300px"
			}
			, title : "보험가입자목록"
			, header : "전화번호,고객명,계약번호,개통대리점ID,개통대리점,보험상품명,신청일자,가입일자,종료일자,보험상태,가입경로,구매유형,단말모델ID,단말모델명,단말일련번호,보험일련번호,보상한도금액,잔여보상금액,보험약정기간,해지사유,reqBuyType,scanId,resNo"
			, columnIds : "subscriberNo,subLinkName,contractNum,openAgntCd,openAgntNm,insrProdNm,reqinday,strtDttm,endDttm,insrStatNm,chnNm,reqBuyTypeNm,modelId,modelNm,intmSrlNo,insrMngmNo,cmpnLmtAmt,rmnCmpnAmt,insrEnggCnt,canRsltNm,reqBuyType,scanId,resNo"
			, widths : "120,100,100,100,130,180,130,130,130,100,100,100,100,100,100,100,100,100,100,150,100,100,100"
			, colAlign : "center,center,center,center,center,Left,Left,center,center,center,center,center,center,center,center,center,Right,Right,center,Left,center,center,center"
			, colTypes : "roXp,ro,ro,ro,ro,ro,roXdt,roXdt,roXdt,ro,ro,ro,ro,ro,ro,ro,roXns,roXns,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, hiddenColumns : [20,21,22]
			, paging: true
			, showPagingAreaOnInit: true
			, pageSize:20
			, buttons : {
				hright : {
					btnExcel:{ label:"엑셀다운로드"}
				}
				, left : {
					btnPaper : { label : "서식지보기" }
				}
				, right : {
					btnReg : { label : "직접등록" }
				}
			}
			, onRowDblClicked : function(rowId, celInd) {
				PAGE.GRID1.callEvent("onButtonClick", ["btnPaper"]);
			}
			, onRowSelect:function(rId, cId){
				
				PAGE.GRID2.clearAll();
				
				if(mvno.cmn.isEmpty(this.getSelectedRowData().insrMngmNo)) return; 
				
				var sData = {
					insrMngmNo : this.getSelectedRowData().insrMngmNo
				};
				
				PAGE.GRID2.list(ROOT + "/insr/insrMgmt/getInsrCmpnListByInsrMngmNo.json", sData);
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
						
						mvno.cmn.download(ROOT + "/insr/insrMgmt/getInsrMemberListByExcel.json?menuId=MSP9002001", true, {postData:searchData}); 
						
						break;
					
					case "btnPaper":
						
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
						if(mvno.cmn.isEmpty(grid.getSelectedRowData().scanId)) {
							mvno.ui.alert("등록된 서식지가 없습니다.");
							return;
						}
						
						appFormView(grid.getSelectedRowData().scanId, grid.getSelectedRowData().resNo);
						
						break;
						
					case "btnReg":
						
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.clear();
						
						PAGE.FORM2.disableItem("btnPrdtFind");
						PAGE.FORM2.disableItem("trgtIntmSrlNo");
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("FORM2", "직접등록", 650, 440, {
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
		
		, GRID2 : {
			css : {
				height : "190px"
			}
			, title : "보상내역"
			, header : "보상유형,보험관리번호,사고번호,사고등록일자,사고일자,보상승인일자,잔여보상금액,보상누적금액,잔여보상한도금액,고객부담금,실보상금액,초과금,유심비용"
			, columnIds : "cmpnTypeNm,insrMngmNo,acdntNo,acdntRegDt,acdntDt,cmpnCnfmDt,rmnCmpnAmt,cmpnAcmlAmt,rmnCmpnLmtAmt,custBrdnAmt,realCmpnAmt,overAmt,usimAmt"
			, widths : "100,120,120,100,100,100,100,100,120,100,100,100,100,100"
			, colAlign : "center,center,center,center,center,center,Right,Right,Right,Right,Right,Right,Right,Right"
			, colTypes : "ro,ro,ro,roXd,roXd,roXd,roXns,roXns,roXns,roXns,roXns,roXns,roXns,roXns"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			
		}
		
		, FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"input", label:"계약번호", name:"searchCntrNo", width:110, offsetLeft:10}
					, {type:"newcolumn"}
					, {type: "button", name: 'btnCustInfo', value:"조회"}
				]}
				, {type:"block", list:[
					{type:"input", label:"고객명", name:"custNm", width:110, offsetLeft:10, disabled:true}
					, {type:"newcolumn"}
					, {type:"input", label:"전화번호", name:"subscriberNo", width:110, offsetLeft:10, disabled:true}
					, {type:"newcolumn"}
					, {type:"input", label:"계약상태", name:"subStatusNm", width:110, offsetLeft:10, disabled:true}
				]}
				, {type:"block", list:[
					{type:"calendar", label:"개통일", name:"openDt", width:110, offsetLeft:10, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", disabled:true}
					, {type:"newcolumn"}
					, {type:"calendar", label:"기변일", name:"dvcChgDt", width:110, offsetLeft:10, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", disabled:true}
					, {type:"newcolumn"}
					, {type:"input", label:"구매유형", name:"reqBuyTypeNm", width:110, offsetLeft:10, disabled:true}
				]}
				, {type:"block", list:[
					{type:"input", label:"보험상품", name:"insrProdNm", width:180, offsetLeft:10, disabled:true}
					, {type:"newcolumn"}
					, {type:"input", label:"개통대리점", name:"openAgntCd", width:110, offsetLeft:10, disabled:true}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"openAgntNm", width:110, disabled:true}
				]}
				, {type:"block", list:[
					{type:"calendar", label:"시작일", name:"strtDt", width:180, offsetLeft:10, dateFormat:"%Y-%m-%d %H:%i:%s", serverDateFormat:"%Y%m%d%H%i%s", disabled:true}
					, {type:"newcolumn"}
					, {type:"calendar", label:"종료일", name:"endDt", width:180, offsetLeft:10, dateFormat:"%Y-%m-%d %H:%i:%s", serverDateFormat:"%Y%m%d%H%i%s", disabled:true}
				]}
				, {type:"block", list:[
					{type:"calendar", label:"신청일", name:"reqinday", width:180, offsetLeft:10, dateFormat:"%Y-%m-%d %H:%i:%s", serverDateFormat:"%Y%m%d%H%i%s", disabled:true}
					
				]}
				, {type:"block", list:[
					{type:"input", label:"모델ID", name:"trgtModelId", width:110, value:'', offsetLeft:10, labelWidth:60, maxLength:10, disabled:true}
					, {type:"newcolumn"}
					, {type: "button", name: 'btnPrdtFind', value:"검색", disabled:true}
					, {type:"newcolumn"}
					, {type:"input", name:"trgtModelNm", width:130, readonly: true, disabled:true}
					, {type:"newcolumn"}
					, {type:"input", label:"일련번호", name:"trgtIntmSrlNo", width:110, offsetLeft:10, disabled:true}
				]}
				, {type:"block", list:[]}
				, {type: "fieldset", label: "최종보험가입정보", name:"INSR", inputWidth:580, list:[
					{type:"block", list:[
						{type:"input", label:"보험상품", name:"sbscInsrProdNm", width:180, labelWidth:50, offsetLeft:10, disabled:true}
						, {type:"newcolumn"}
						, {type:"input", label:"보험상태", name:"intmInsrStatNm", width:110, labelWidth:50, offsetLeft:10, disabled:true}
					]}
					, {type:"block", list:[
						{type:"calendar", label:"시작일", name:"sbscInsrStrtDt", width:180, labelWidth:50, offsetLeft:10, dateFormat:"%Y-%m-%d %H:%i:%s", serverDateFormat:"%Y%m%d%H%i%s", disabled:true}
						, {type:"newcolumn"}
						, {type:"calendar", label:"종료일", name:"sbscInsrEndDt", width:180, labelWidth:50, offsetLeft:10, dateFormat:"%Y-%m-%d %H:%i:%s", serverDateFormat:"%Y%m%d%H%i%s", disabled:true}
					]}
					, {type:"block", list:[
						{type:"input", label:"모델ID", name:"insrModelId", width:110, labelWidth:50, offsetLeft:10, disabled:true}
						, {type:"newcolumn"}
						, {type:"input", label:"모델명", name:"insrModelNm", width:110, labelWidth:50, offsetLeft:10, disabled:true}
						, {type:"newcolumn"}
						, {type:"input", label:"일련번호", name:"insrIntmSrlNo", width:110, labelWidth:50, offsetLeft:10, disabled:true}
					]}
				]}
				, {type: "hidden", name: "reqBuyType"}
				, {type: "hidden", name: "insrProdCd"}
				, {type: "hidden", name: "contractNum"}
			]
			, buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					
					case "btnCustInfo":
						
						if(mvno.cmn.isEmpty(form.getItemValue("searchCntrNo"))) {
							mvno.ui.alert("계약번호를 입력하세요.");
							return;
						}
						
						mvno.cmn.ajax(ROOT + "/insr/insrMgmt/getInsrCntrInfo.json", {searchCntrNo:form.getItemValue("searchCntrNo")}, function(resultData) {
							
							if(mvno.cmn.isEmpty(resultData.result.data)) {
								mvno.ui.alert("보험 가입 정보가 존재 하지 않습니다.");
								form.clear();
								return;
							}
							
							form.setFormData(resultData.result.data);
							
							form.enableItem("btnPrdtFind");
							form.enableItem("trgtIntmSrlNo");
							
						});
						
						break;
						
					case "btnPrdtFind":
						mvno.ui.codefinder("RPRSMLDSET", function(result) {
							form.setItemValue("trgtModelId", result.rprsPrdtId);
							form.setItemValue("trgtModelNm", result.prdtCode);
						});
						
						break;
					
					case "btnSave":
						
						if(mvno.cmn.isEmpty(form.getItemValue("trgtModelId"))) {
							mvno.ui.alert("모델정보가 없습니다.");
							return;
						}
						
						if(mvno.cmn.isEmpty(form.getItemValue("trgtIntmSrlNo"))) {
							mvno.ui.alert("일련번호가 없습니다.");
							return;
						}
						
						mvno.ui.confirm("저장하시겠습니까?", function() {
							mvno.cmn.ajax(ROOT + "/insr/insrMgmt/regMspIntmInsrOrder.json", form, function(){
								mvno.ui.notify("NCMN0004");
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
	      breadcrumb : "${breadCrumb.breadCrumb}",
		  buttonAuth: ${buttonAuth.jsonString} ,
		  onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			mvno.ui.createGrid("GRID2");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchCd");					// 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0251", orderBy:"etc6"}, PAGE.FORM1, "insrStatCd");	// 보험상태
			mvno.cmn.ajax4lov( ROOT + "/insr/insrMgmt/getInsrCombo.json", "", PAGE.FORM1, "insrProdCd"); // 단말보험상품
		}
	};
</script>
