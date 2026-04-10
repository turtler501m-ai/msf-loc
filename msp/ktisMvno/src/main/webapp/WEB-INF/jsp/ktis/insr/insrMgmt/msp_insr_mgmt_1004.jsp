<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">
	
	var orgId = "${orgnInfo.orgnId}";
	var orgNm = "${orgnInfo.orgnNm}";
	var userId = "${loginInfo.userId}";
	var userName = "${loginInfo.userName}";
	var typeCd = "${orgnInfo.typeCd}";
	var orgType = "";
	var reqFlag = true;
	var sOrgnId="";
	var sOrgnNm="";
	var isCntpShop = true;
	
	if(typeCd == "10"){
		isCntpShop = false;
		reqFlag = false;
	}else{
		isCntpShop = true;
		reqFlag = true;
		sOrgnId = '${orgnInfo.orgnId}';
		sOrgnNm = '${orgnInfo.orgnNm}';
	}
	
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
					, {type:"select", label:"검증상태", name: "vrfyRsltCd", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"select", label:"보험상품", name: "insrProdCd", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"select", label:"처리상태", name:"ifTrgtCd", width:90, offsetLeft:10}
				]}
				, {type:"block", list:[
					{type:"select", label:"검색구분", name: "searchCd", width:100, offsetLeft:10 , labelWidth:86}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchVal", width:115, offsetLeft:0, maxLength:60}
					, {type:"newcolumn"}
					, {type:"select", label:"이미지등록여부", name:"imgChkYn", width:70, offsetLeft:10, labelWidth:90}
					, {type:"newcolumn"}
					, {type:"input", label:"대리점", name: "agntId", value:sOrgnId, width:100, readonly:reqFlag, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"button", value:"검색", name:"btnOrgFind", disabled:isCntpShop}
					, {type:"newcolumn"}
					, {type:"input", name: "agntNm",value:sOrgnNm, width:110, readonly: true}
				]}
				, {type:"block", list:[
					{type:"select", label:"구매유형", name: "reqBuyType", width:100, offsetLeft:10 , labelWidth:86}
					, {type:"newcolumn"}
					, {type:"select", label:"업무구분", name: "operType", width:100, offsetLeft:15}
					, {type:"newcolumn"}
					, {type:"select", label:"상품구분", name: "prodType", width:100, offsetLeft:15}
				]}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search3"} 
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
					case "btnOrgFind":
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("agntId", result.orgnId);
							form.setItemValue("agntNm", result.orgnNm);
						});
						
						break;
						
					case "btnSearch":
						if( !mvno.cmn.isEmpty(form.getItemValue("searchCd")) && mvno.cmn.isEmpty(form.getItemValue("searchVal")) ) {
							mvno.ui.alert("검색 할 내용이 없습니다.");
							return;
						}
						
						PAGE.GRID1.list(ROOT + "/insr/insrMgmt/getInsrOrderAgntList.json", form);
						
						break;
				}
			}
		}
		
		, GRID1 : {
			css : {
				height : "510px"
			}
			, title : "보험신청목록"
			, header : "전화번호,고객명,계약번호,개통대리점,보험상품명,가입일자,종료일자,처리상태,가입경로,구매유형,업무구분,상품구분,단말모델명,단말일련번호,이미지등록여부,서식지변경여부,검증결과,1차검증처리자,1차검증일자,2차검증처리자,2차검증일자,메모,insrProdCd,insrStatCd,modelId,reqBuyType,operType,prodType,vrfyRsltCd,scanId,resNo,onOffType,ifTrgtCd"
			, columnIds : "subscriberNo,subLinkName,contractNum,openAgntNm,insrProdNm,strtDttm,endDttm,ifTrgtNm,chnNm,reqBuyTypeNm,operTypeNm,prodTypeNm,modelNm,intmSrlNo,imgChkYn,scanMdyYn,vrfyRsltNm,fstVrfyId,fstVrfyDttm,sndVrfyId,sndVrfyDttm,rmk,insrProdCd,insrStatCd,modelId,reqBuyType,operType,prodType,vrfyRsltCd,scanId,resNo,chnCd,ifTrgtCd"
			, widths : "120,100,100,130,180,130,130,80,120,100,100,100,100,100,100,100,100,100,130,100,130,500,100,100,100,100,100,100,100,100,100"
			, colAlign : "center,center,center,Left,Left,center,center,center,center,center,center,center,Left,center,center,center,center,center,center,center,center,Left,center,center,center,center,center,center,center,center,center,center,center"
			, colTypes : "roXp,ro,ro,ro,ro,roXdt,roXdt,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXdt,ro,roXdt,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, hiddenColumns : [22,23,24,25,26,27,28,29,30,31,32]
			, paging: true
			, showPagingAreaOnInit: true
			, pageSize:20
			, buttons : {
				left : {
					btnPaper : { label : "서식지보기" }
				}
			}
			, onRowDblClicked : function(rowId, celInd) {
				PAGE.GRID1.callEvent("onButtonClick", ["btnPaper"]);
			}
			, onButtonClick : function(btnId) {
				var grid = this;
				
				switch (btnId) {
					
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
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchCd");					// 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0200", orderBy:"etc6"}, PAGE.FORM1, "vrfyRsltCd");	// 검증상태
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM1, "imgChkYn");		// 이미지등록여부(유심단독개통고객)
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0250", orderBy:"etc6"}, PAGE.FORM1, "ifTrgtCd");	// 처리상태
			mvno.cmn.ajax4lov( ROOT + "/insr/insrMgmt/getInsrCombo.json", "", PAGE.FORM1, "insrProdCd");										// 단말보험상품

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9004"}, PAGE.FORM1, "reqBuyType");					// 구매유형
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9001"}, PAGE.FORM1, "operType");					// 업무구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0063"}, PAGE.FORM1, "prodType");					// 상품구분
		}
	};
</script>
