<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">
	var maskingYn = "${maskingYn}";				// 마스킹권한
	
	var fromDt = new Date(new Date().setDate(new Date().getDate() - 6)).format("yyyymmdd");
	var toDt = new Date().format("yyyymmdd");
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchFromDt", label:"지급일자", value:fromDt, calendarPosition: "bottom", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchToDt", label:"~", value:toDt, calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type:"newcolumn"}
					, {type:"select", label:"보상서비스명", name: "searchRwdProdCd", labelWidth:80, width:220, offsetLeft:30}					
				]}
				, {type:"block", list:[
					{type:"select", label:"검색구분", name: "searchCd", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchVal", width:115, offsetLeft:0, maxLength:60, disabled:true}
				]}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"} 
			]
			, onValidateMore : function(data) {
				if (data.searchFromDt > data.searchToDt) {
					this.pushError("searchFromDt", "조회기간", "시작일자가 종료일자보다 클 수 없습니다.");
				}
				if(data.searchFromDt != "" && data.searchToDt == ""){
					this.pushError("searchFromDt", "조회기간", "조회기간을 입력하세요.");
				}
				if(data.searchToDt != "" && data.searchFromDt == ""){
					this.pushError("searchToDt", "조회기간", "조회기간을 입력하세요.");
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
					
					if(mvno.cmn.isEmpty(value)) {
						form.setItemValue("searchFromDt", fromDt);
						form.setItemValue("searchToDt", toDt);
						
						form.enableItem("searchFromDt");
						form.enableItem("searchToDt");
						form.disableItem("searchVal");
					} else {
						form.setItemValue("searchFromDt", "");
						form.setItemValue("searchToDt", "");
						
						form.disableItem("searchFromDt");
						form.disableItem("searchToDt");
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
						
						PAGE.GRID1.list(ROOT + "/rwd/rwdMgmt/getRwdMemPayList.json", form);
						
						break;
				}
			}
		}
		
		, GRID1 : {
			css : {
				height : "540px"
			}
			, title : "보상지급현황"
			, header : "지급일자,전화번호,고객명,계약번호,보상서비스명,보장금액,실보상금액" //7
			, columnIds : "payDttm,subscriberNo,custNm,contractNum,rwdProdNm,asrPric,realCmpnAmt" //7
			, widths : "100,100,80,100,200,100,100"
			, colAlign : "center,center,center,center,left,right,right"
			, colTypes : "ro,roXp,ro,ro,ro,ron,ron"
			, colSorting : "str,str,str,str,str,int,int"
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
				switch (btnId) {
				
					case "btnExcel":
						
						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}
						
						var searchData =  PAGE.FORM1.getFormData(true);
						 
						mvno.cmn.download(ROOT + "/rwd/rwdMgmt/getRwdMemPayListByExcel.json?menuId=MSP9100106", true, {postData:searchData}); 
						
						break;
				}
			}
		}
	};
	
	var PAGE = {
	      title : "${breadCrumb.title}",
	      breadcrumb : "${breadCrumb.breadCrumb}",
		  buttonAuth: ${buttonAuth.jsonString} ,
		  onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchCd");				    	// 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0258", orderBy:"etc5"}, PAGE.FORM1, "searchRwdProdCd");	// 보상서비스 상품
		}
	};
</script>
