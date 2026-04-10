<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<div id="FORM3" class="section-box"></div>
<div id="FORM4" class="section-box"></div> 

<!-- Script -->
<script type="text/javascript">
	var rwdSeq = "";

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
	
	var fromDt = new Date(new Date().setDate(new Date().getDate() + 520)).format("yyyymmdd");
	var toDt = new Date(new Date().setDate(new Date().getDate() + 550)).format("yyyymmdd");
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					  {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchFromDt", label:"도래일자", value:fromDt, calendarPosition: "bottom", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchToDt", label:"~", value:toDt, calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type:"newcolumn"}
					, {type:"select", label:"보상서비스명", name: "searchRwdProdCd", width:200, labelWidth:80, offsetLeft:30}
				]}
				, {type:"block", list:[
					  {type:"select", label:"검색구분", name: "searchCd", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchVal", width:115, offsetLeft:0, maxLength:60, disabled:true}
					, {type:"newcolumn"}
					, {type:"select", label:"도래기간", name: "searchTermCd", labelWidth:80, width:100, offsetLeft:30}
				]}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"} 
			]
	
			, onValidateMore : function(data) {
				if (data.searchFromDt > data.searchToDt) {
					this.pushError("searchFromDt", "도래일자", "시작일자가 종료일자보다 클 수 없습니다.");
				}
				if(data.searchFromDt != "" && data.searchToDt == ""){
					this.pushError("searchFromDt", "도래일자", "조회기간을 입력하세요.");
				}
				if(data.searchToDt != "" && data.searchFromDt == ""){
					this.pushError("searchToDt", "도래일자", "조회기간을 입력하세요.");
				}				
				if( data.searchCd != "" && data.searchCd.trim() == ""){
					this.pushError("searchCd", "검색어", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchCd", ""); // 검색어 초기화
				}
			}
			, onChange: function (name, value) {
				var form = this;
				
				switch(name) {
					case "searchCd":
						
						form.setItemValue("searchVal", "");
						
						if(mvno.cmn.isEmpty(value) && mvno.cmn.isEmpty(form.getItemValue("searchTermCd"))) {
							form.setItemValue("searchFromDt", fromDt);
							form.setItemValue("searchToDt", toDt);
							
							form.disableItem("searchVal");
							
							form.enableItem("searchFromDt");
							form.enableItem("searchToDt");
						} else if(mvno.cmn.isEmpty(value) && (form.getItemValue("searchTermCd") != null || form.getItemValue("searchTermCd") != "")) {
							form.disableItem("searchVal");
						} else {
							form.setItemValue("searchFromDt", "");
							form.setItemValue("searchToDt", "");
							
							form.enableItem("searchVal");
							
							form.disableItem("searchFromDt");
							form.disableItem("searchToDt");
						}
						
						break;
						
					case "searchTermCd":
						if(mvno.cmn.isEmpty(value) && mvno.cmn.isEmpty(form.getItemValue("searchCd"))) {
							form.setItemValue("searchFromDt", fromDt);
							form.setItemValue("searchToDt", toDt);		
							
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
						
						PAGE.GRID1.list(ROOT + "/rwd/rwdMgmt/getCmpnMemberList.json", form);
						
						break;
				}
			}
		}
		// 그리드 시작
		, GRID1 : {
			css : {
				height : "540px"
			}
			, title : "도래대상목록"
			, header : "전화번호,고객명,계약번호,개통대리점,보상서비스명,도래기간,가입일자,종료일자,가입경로,구매유형,구매가격,단말모델ID,단말모델명,IMEI,IMEI2,단말일련번호,약정기간" //17
			, columnIds : "subscriberNo,subLinkName,contractNum,openAgntNm,rwdProdNm,leftPrd,strtDttm,endDttm,chnNm,reqBuyTypeNm,buyPric,modelId,modelNm,imei,imeiTwo,intmSrlNo,rwdPrd" //17
			, widths : "120,100,100,130,200,100,130,130,120,100,100,100,100,120,120,120,100"
			, colAlign : "center,center,center,Left,Left,center,center,center,center,center,Right,center,center,center,center,center,center"
			, colTypes : "roXp,ro,ro,ro,ro,ro,roXdt,roXdt,ro,ro,ron,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,int,str,str,str,str,str,str"
			, paging: true
			, showPagingAreaOnInit: true
			, pageSize:20
			, buttons : {
				hright : {
					btnExcel:{ label:"엑셀다운로드"}
				}
			}
			, onButtonClick : function(btnId) {
				var grid = this;
				var form = this;
				switch (btnId) {
                    
					case "btnExcel":
						
						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}
						
						var searchData =  PAGE.FORM1.getFormData(true);
						 
						mvno.cmn.download(ROOT + "/rwd/rwdMgmt/getCmpnMemberListByExcel.json?menuId=MSP9100104", true, {postData:searchData}); 
						
						break;
				}
			}
		}
	}
	
	var PAGE = {
	      title : "${breadCrumb.title}",
	      breadcrumb : "${breadCrumb.breadCrumb}",
		  buttonAuth: ${buttonAuth.jsonString} ,
		  onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchCd");				    	// 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0258", orderBy:"etc5"}, PAGE.FORM1, "searchRwdProdCd");	// 보상서비스 상품
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0263"}, PAGE.FORM1, "searchTermCd");	// 보상서비스 도래기간
		}
	};
</script>