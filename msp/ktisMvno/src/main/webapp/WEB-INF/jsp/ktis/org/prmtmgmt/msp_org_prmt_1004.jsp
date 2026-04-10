<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<div id="POPUP1">
	<div id="POPUP1TOP"  class="section-full"></div>
	<div id="POPUP1MID" class="section-box"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	var fromDt = new Date(new Date().setDate(new Date().getDate() - 6)).format("yyyymmdd");
	var toDt = new Date().format("yyyymmdd");
	var dateSercType1 = true; // 데이트 검색 타입 블록 처리
	var dateSercType2 = true; // 데이트 검색 타입 블록 처리
	var appYN = [{value:"", text:"- 전체 -", selected:true}, {value:"Y", text:"적용"}, {value:"N", text:"미적용"}];
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchFromDt", label:"피추천인 개통일자", labelWidth:105, value:fromDt, calendarPosition: "bottom", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchToDt", label:"~", value:toDt, calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type:"newcolumn"}
					, {type:"select", label:"링크유형구분", name:"linkTypeCd", labelWidth:105, width:100, offsetLeft:20}
					, {type:"newcolumn"}
					, {type:"select", label:"혜택적용여부", name:"appType", options:appYN, labelWidth:105, width:100, offsetLeft:20}
					
				]}
				, {type:"block", list:[
					{type:"select", label:"추천인 구분", name: "searchCd", labelWidth:105, width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchVal", width:115, offsetLeft:0, maxLength:60}
					, {type:"newcolumn"}
					, {type:"select", label:"피추천인 구분", name: "deSearchCd", labelWidth:105, width:100, offsetLeft:20}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"deSearchVal", width:115, offsetLeft:0, maxLength:60}
					
				]}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"}
			]
			, onChange: function (name, value) {
				var form = this;

				switch(name) {

					case "searchCd":
						if(mvno.cmn.isEmpty(value)) {
							form.setItemValue("searchVal", "");
							form.disableItem("searchVal");
							
							//
							dateSercType1 = true;
							if(dateSercType1 && dateSercType2){
								form.enableItem("searchFromDt");
								form.enableItem("searchToDt");
								form.setItemValue("searchFromDt", fromDt);
								form.setItemValue("searchToDt", toDt);
							}
						} else {
							form.enableItem("searchVal");
							form.setItemValue("searchVal", "");
							
							//
							dateSercType1 = false;
							form.setItemValue("searchFromDt", "");
							form.setItemValue("searchToDt", "");
							form.disableItem("searchFromDt");
							form.disableItem("searchToDt");
							
						}

						break;
					case "deSearchCd":
						if(mvno.cmn.isEmpty(value)) {
							form.setItemValue("deSearchVal", "");
							form.disableItem("deSearchVal");
							
							//
							dateSercType2 = true;
							if(dateSercType1 && dateSercType2){
								form.enableItem("searchFromDt");
								form.enableItem("searchToDt");
								form.setItemValue("searchFromDt", fromDt);
								form.setItemValue("searchToDt", toDt);
							}
							
						} else {
							form.enableItem("deSearchVal");
							form.setItemValue("deSearchVal", "");
							
							dateSercType2 = false;
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
						if( !mvno.cmn.isEmpty(form.getItemValue("deSearchCd")) && mvno.cmn.isEmpty(form.getItemValue("deSearchVal")) ) {
							mvno.ui.alert("검색 할 내용이 없습니다.");
							return;
						}
						PAGE.GRID1.list(ROOT + "/org/prmtmgmt/getRecommenHstList.json", form);

						break;
				}
			}
		}
		, GRID1 : {
			css : {
				height : "530px"
			}
			, title : "조회결과"
			, header : "추천인,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,피추천인,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan"
			, header2 : "추천인ID,링크유형,계약번호,추천인,휴대폰번호,추천인생년월일,요금제,계약상태,혜택,적용일자,계약번호,피추천인,업무구분,이동전통신사,휴대폰번호,피추천인생년월일,요금제,계약상태,개통일자"
			, columnIds : "commendId,linkTypeNm,contractNum,subLinkName,subscriberNo,birthDt,rateNm,subStatus,applyYn,applyDate,dContractNum,deSubLinkName,dOperTypeNm,dBfCommCmpnNm,deSubscriberNo,dBirthDt,dLstRateNm,dSubStatus,dLstComActvDate"
			, widths : "80,90,90,90,110,100,200,80,60,90,90,90,80,120,110,100,200,80,90"
			, colAlign : "left,center,center,left,center,center,left,center,center,center,center,left,center,left,center,center,left,center,center"
			, colTypes : "ro,ro,ro,ro,roXp,ro,ro,ro,ro,roXdt,ro,ro,ro,ro,roXp,ro,ro,ro,roXdt"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, paging : true
			, showPagingAreaOnInit : true
			, pageSize : 20
			, buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드"}
				},
				left : {
					btnDnExcel : { label : "엑셀업로드양식"}
				},
				right : {
					btnUpExcel : { label : "혜택적용"}
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
	
						mvno.cmn.download(ROOT + "/org/prmtmgmt/getRecommenHstListByExcel.json?menuId=MSP2002410", true, {postData:searchData});
	
						break;

				
					case "btnDnExcel":
						
						mvno.cmn.download("/org/prmtmgmt/getBenefitsTempExcelDown.json");
						
						break;
						
					case "btnUpExcel":
						
						mvno.ui.createGrid("POPUP1TOP");
						mvno.ui.createForm("POPUP1MID");
						
						PAGE.POPUP1TOP.clearAll();
						PAGE.POPUP1MID.clear();
						
						PAGE.POPUP1MID.setFormData({});
						
						var fileName;
						
						PAGE.POPUP1MID.attachEvent("onUploadFile", function(realName, serverName) {
							fileName = serverName;
						});
						
						PAGE.POPUP1MID.attachEvent("onUploadComplete", function(count){
							
							var url = ROOT + "/org/prmtmgmt/benefitsUpList.json";
							var userOptions = {timeout:180000};
							
							mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
								
								var rData = resultData.result;
								
								PAGE.POPUP1TOP.clearAll();
								
								PAGE.POPUP1TOP.parse(rData.data.rows, "js");
								
							}, userOptions);
						});
						
						PAGE.POPUP1MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP1TOP.clearAll();
							
							return true;
						});
						
						var uploader = PAGE.POPUP1MID.getUploader("file_upload1");
						uploader.revive();
						
						mvno.ui.popupWindowById("POPUP1", "혜택적용등록", 420, 490, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
						break;
				}
			}
		}
		, POPUP1TOP : {
			css : { height : "250px" },
			title : "혜택적용대상",
			header : "피추천인계약번호",
			columnIds : "uploadContractNum",
			widths : "360",
			colAlign : "center",
			colTypes : "ro"
		}	//POPUP1TOP End
		
		, POPUP1MID : {
			title : "엑셀업로드",
			items : [
				{ type : "block",
				  list : [
					{ type : "newcolumn", offset : 10 },
					{ type : "upload", 
					  label : "혜택적용등록 엑셀파일",
					  name : "file_upload1",
					  inputWidth : 320,
					  url : "/rcp/dlvyMgmt/regParExcelUp.do",
					  userdata : { limitSize:10000 },
					  autoStart : true } 
				]},
				{type:"input", width:100, name:"rowData", hidden:true}
			],
			
			buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			},
			
			onButtonClick : function(btnId) {
				
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				
				switch (btnId) {
					
					case "btnSave" :
						
						if(PAGE.POPUP1TOP.getRowsNum() == 0) {
							mvno.ui.alert("파일을 첨부해 주세요.");
							break;
						}
						
						var arrObj = [];
						
						PAGE.POPUP1TOP.forEachRow(function(id){
							var arrData = PAGE.POPUP1TOP.getRowData(id);
							
							arrObj.push(arrData);
						});
						
						var sData = {};
						
						sData.items = arrObj;
						
						var userOptions = {timeout:180000};
						
						mvno.cmn.ajax4json(ROOT + "/org/prmtmgmt/regBenefitsList.json", sData, function(result) {
							mvno.ui.closeWindowById("POPUP1", true);
							mvno.ui.notify("NCMN0004");
							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
						}, userOptions);
						
					break;
						
					case "btnClose":
						mvno.ui.closeWindowById("POPUP1");
					break;
				}
			}
		}	//POPUP1MID End
		
		
	}
			
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		 onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0070"}, PAGE.FORM1, "searchCd");		// 추천인 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0072"}, PAGE.FORM1, "deSearchCd");		// 피추천인 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0287"}, PAGE.FORM1, "linkTypeCd");		// 링크유형 검색구분
			
			PAGE.FORM1.disableItem("searchVal");
			PAGE.FORM1.disableItem("deSearchVal");
		}
	};
</script>
