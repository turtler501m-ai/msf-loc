<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">
	var maskingYn = "${maskingYn}";				// 마스킹권한
	
	var dt = new Date();
	var fstDt = new Date(dt.getFullYear(), dt.getMonth(), 1);
	var prvMnth = new Date(fstDt.setDate(fstDt.getDate())).format("yyyymm");
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m", serverDateFormat:"%Y%m", name:"searchBillYm", label:"청구월", value:prvMnth, calendarPosition: "bottom", width:130, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"select", label:"보상서비스명", name: "searchRwdProdCd", labelWidth:80, width:200, offsetLeft:30}
				]}
				, {type:"block", list:[
					{type:"select", label:"가입경로", name:"searchChnCd", width:130, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"select", label:"검색구분", name: "searchCd", labelWidth:80, width:80, offsetLeft:30}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchVal", width:114, offsetLeft:0, maxLength:60, disabled:true}
				]}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"} 
			]
			, onChange: function (name, value) {
				var form = this;
				
				switch(name) {
					case "searchCd":
						form.setItemValue("searchVal", "");
						
						if(mvno.cmn.isEmpty(value)) {
							form.setItemValue("searchBillYm", prvMnth);
							
							form.enableItem("searchBillYm");
							form.disableItem("searchVal");
						} else {
							form.setItemValue("searchBillYm", "");
							
							form.disableItem("searchBillYm");
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
						
						PAGE.GRID1.list(ROOT + "/rwd/rwdMgmt/getRwdBillPayList.json", form);
						
						break;
				}
			}
		}
		
		, GRID1 : {
			css : {
				height : "540px"
			}
			, title : "보상서비스 청구수납 목록"
			, header : "청구월,전화번호,고객명,계약번호,가입경로,보상서비스명,구분,청구/수납일자,금액,납부방법" //10
			, columnIds : "billYm,subscriberNo,custNm,contractNum,chnNm,rwdProdNm,pymnCd,blpymDate,pymnAmnt,pymnMthdNm" //10
			, widths : "80,120,80,80,130,200,80,120,90,120"
			, colAlign : "center,center,center,center,center,left,center,center,right,center"
			, colTypes : "ro,roXp,ro,ro,ro,ro,ro,ro,ron,ro"
			, colSorting : "str,str,str,str,str,str,str,str,int,str"
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
						 
						mvno.cmn.download(ROOT + "/rwd/rwdMgmt/getRwdBillPayListByExcel.json?menuId=MSP9100107", true, {postData:searchData}); 
						
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
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0249"}, PAGE.FORM1, "searchChnCd");					    // 가입경로구분
		}
	};
</script>
