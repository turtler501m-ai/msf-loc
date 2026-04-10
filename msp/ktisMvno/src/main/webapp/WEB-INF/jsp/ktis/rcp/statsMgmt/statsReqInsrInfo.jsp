<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<div id="FORM2" class="section-box"></div>
<div id="FORM3" class="section-box"></div>

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
					, {type:"select", label:"처리상태", name:"ifTrgtCd", width:100, offsetLeft:20}
					, {type:"newcolumn"}
					, {type:"select", label:"보험상품", name: "insrProdCd", width:220, offsetLeft:20}
					, {type:"newcolumn"}
				]}
				, {type:"block", list:[
					 {type:"select", label:"검색구분", name: "searchCd", width:100, offsetLeft:10, labelWidth:86}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchVal", width:115, offsetLeft:0, maxLength:60}
					, {type:"newcolumn"}
					, {type:"select", label:"가입경로", name:"chnCd", width:100, offsetLeft:20} 
					, {type:"newcolumn"}
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
						
						PAGE.GRID1.list(ROOT + "/insr/insrMgmt/getInsrOrderList.json", form);
						
						break;
				}
			}
		}
		
		, GRID1 : {
			css : {
				height : "530px"
			}
			, title : "보험신청목록"
			, header : "계약번호,고객명,전화번호,성별,나이,개통일,개통대리점,모집유형,유심접점,신청일시,가입일시,가입경로,보험상품,처리상태,별도연락처,insrProdCd,insrStatCd,modelId,reqBuyType,vrfyRsltCd,scanId,resNo,onOffType,ifTrgtCd,insrTypeCd"
			, columnIds : "contractNum,subLinkName,subscriberNo,genderType,custAge,lstComActvDate,openAgntNm,onOffTypeNm,usimOrgNm,reqinday,strtDttm,chnNm,insrProdNm,ifTrgtNm,smsPhone,insrProdCd,insrStatCd,modelId,reqBuyType,vrfyRsltCd,scanId,resNo,chnCd,ifTrgtCd,insrTypeCd"
			, widths : "100,80,100,60,60,100,130,100,130,140,140,120,220,100,100,100,100,100,100,100,100,100,100,100,100"
			, colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center"
			, colTypes : "ro,ro,roXp,ro,ro,ro,ro,ro,ro,roXdt,roXdt,ro,ro,ro,roXp,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, hiddenColumns : [15,16,17,18,19,20,21,22,23,24]
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
			}
			, onRowDblClicked : function(rowId, celInd) {
				PAGE.GRID1.callEvent("onButtonClick", ["btnPaper"]);
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
						
						mvno.cmn.download(ROOT + "/insr/insrMgmt/getInsrOrderListByExcel.json?menuId=MSP1010045", true, {postData:searchData}); 
						
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
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0250", orderBy:"etc6"}, PAGE.FORM1, "ifTrgtCd");	// 처리상태
			mvno.cmn.ajax4lov( ROOT + "/insr/insrMgmt/getInsrCombo.json", "", PAGE.FORM1, "insrProdCd");										// 단말보험상품
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0249"}, PAGE.FORM1, "chnCd");					// 가입경로구분
		}
	};
</script>
