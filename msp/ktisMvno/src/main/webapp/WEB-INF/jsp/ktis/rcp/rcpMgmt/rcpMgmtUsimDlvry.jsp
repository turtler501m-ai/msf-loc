<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>
<div id="FORM3" class="section-box"></div>
<div id="GRID2"></div>

<div id="POPUP1">
	<div id="POPUP1TOP"  class="section-full"></div>
	<div id="POPUP1MID" class="section-box"></div>
</div>
<div id="POPUP2">
	<div id="POPUP2TOP"  class="section-full"></div>
	<div id="POPUP2MID" class="section-box"></div>
</div>
<div id="POPUP3">
	<div id="POPUP3TOP"  class="section-full"></div>
	<div id="POPUP3MID" class="section-box"></div>
</div>
<div id="POPUP4">
	<div id="POPUP4TOP"  class="section-full"></div>
	<div id="POPUP4MID" class="section-box"></div>
</div>
<!-- 2020.12.10 유심일련번호등록 팝업 추가 -->
<div id="POPUP5">
	<div id="POPUP5TOP"  class="section-full"></div>
	<div id="POPUP5MID" class="section-box"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	var maskingYn = "${maskingYn}";
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
				{type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"신청일자", value:"${strtDt}", calendarPosition:"bottom", readonly:true, width:100, offsetLeft:10, required:true},
					{type:"newcolumn"},
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~", value:"${endDt}", calendarPosition:"bottom", readonly:true, labelAlign:"center", labelWidth:10, width:100, offsetLeft:5},
					{type:'newcolumn'},
					{type:"select", label:"유심종류", name:"usimProdInfo", width:110, offsetLeft:40},
					{type:'newcolumn'},
					{type:"select", label:"배송유형", name:"dlvryType", width:110, offsetLeft:80}
				]},
				{type:"block", list:[
					{type:"block", list:[
							{type:"select", label:"검색구분", name:"searchCd", width:90, offsetLeft:10, options:[{value:"", text:"- 선택 -"}, {value:"1", text:"주문번호"}, {value:"2", text:"고객명"}, {value:"3", text:"연락처"}, {value:"4", text:"일련번호"}, {value:"5", text:"송장번호"}]},
							{type:"newcolumn"},
							{type:"input", name:"searchVal", width:125, maxlength:30}
						]},
					{type:"newcolumn"},
					{type:"select", label:"진행상태",name:"dStateCode", width:110, offsetLeft:40},
					{type:"newcolumn"},
					{type:"select", label:"신청상태",name:"sStateCode", width:110, offsetLeft:80}
				]},
				{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"}
			],
			
			onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getUsimDlvryList.json", form);
						
						/* 2022-04-22  일련번호상세에 데이터가 보이는 상황에서 다시 조회버튼을 누르면 일련번호 상세가 초기화 되지 않는 부분을 수정 */
						PAGE.GRID2.clearAll();
						
						break;
				}
			},
			onChange : function(name, value) {//onChange START
				// 검색구분
				if(name == "searchCd") {
					PAGE.FORM1.setItemValue("searchVal", "");
					
					if(value == null || value == "") {
						var searchEndDt = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var searchStartDt = new Date(time);

						// 신청일자 Enable
						PAGE.FORM1.enableItem("strtDt");
						PAGE.FORM1.enableItem("endDt");

						PAGE.FORM1.setItemValue("searchVal", "");

						PAGE.FORM1.setItemValue("strtDt", searchStartDt);
						PAGE.FORM1.setItemValue("endDt", searchEndDt);
						
						PAGE.FORM1.disableItem("searchVal");
					}
					else {
						PAGE.FORM1.setItemValue("strtDt", "");
						PAGE.FORM1.setItemValue("endDt", "");
						
						// 신청일자 Disable
						PAGE.FORM1.disableItem("strtDt");
						PAGE.FORM1.disableItem("endDt");
						
						PAGE.FORM1.enableItem("searchVal");
					}
				}
			},
			onKeyUp : function (inp, ev, name, value){
				
				var searchCd = PAGE.FORM1.getItemValue("searchCd");
				
				if(name == "searchVal"){
					var val = PAGE.FORM1.getItemValue("searchVal");
					
					if(searchCd == "1") {	
						PAGE.FORM1.setItemValue("searchVal",val.replace(/[^0-9]/g,""));
					}
				}
			},
			onValidateMore : function (data){
				
				if(data.strtDt > data.endDt){
					this.pushError("data.searchStartDt","신청일자",["ICMN0004"]);
				}
				
				if( data.searchCd != "" && data.searchVal.trim() == ""){
					this.pushError("searchVal", "검색어", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
				}
			}
		}
		
		, GRID1 : {
			css : {
				height : "320px"
			}
			, title : "조회결과"
			, header : "주문번호,배송유형,고객명,연락처,유심종류,수량,신청경로,예약번호,신청일자,진행상태,신청상태,법정대리인,수정자,수정일자,결제취소상태,결제취소실패사유,결제취소자,결제취소일" //19
			, columnIds : "selfDlvryIdx,dlvryTypeNm,cstmrName,dlvryTel,usimProdNm,reqBuyQnty,reqTypeNm,resNo,sysRdate,dlvryStateNm,selfStateNm,minorAgentName,rvisnId,rvisnDttm,cnclYnStat,cnclFailRsn,cnclNm,cnclDt" //19
			, widths : "80,100,120,130,110,50,90,80,100,100,100,100,100,150,100,200,100,170" //19
			, colAlign : "center,center,left,center,center,left,center,center,center,center,center,center,center,center,center,left,center,center" //19
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" //19
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" //19
			//, multiCheckbox : true
			, paging : true
			, showPagingAreaOnInit : true
			, pageSize : 20
			, buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드"},
					btnDnExcelDtl : { label : "엑셀상세다운로드"}
				},
				left : {
					btnDnExcelUp : { label : "엑셀업로드양식"}
				},
				//2020.12.10 유심일련번호 버튼 추가
				right : {
					btnDlvryNo : { label : "송장번호등록"},
					btnDlvryWait : { label : "배송대기등록"},
					btnDlvryOk : { label : "배송완료등록"},
					btnOpenOk : { label : "개통완료등록"},
					btnUsimSn : { label : "유심일련번호등록"}
				}
			},
			//checkSelectedButtons : ["btnDtl"],
			onRowDblClicked : function(rowId, celInd) {
				this.callEvent('onButtonClick', ['btnDtl']);
			},
			onRowSelect : function(rowId) {
				
				var rowData = this.getSelectedRowData();
				mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"selFDlvryIdx" : rowData.selfDlvryIdx, "resNo":rowData.resNo, "trgtMenuId":"MSP1010030"}, function(result){});
				
				PAGE.GRID2.clearAll();
				PAGE.GRID2.list(ROOT + "/rcp/rcpMgmt/getUsimDlvryDtlList.json", rowData);
				
					
			},
			onButtonClick : function(btnId) {
				var grid = this;
				
				switch (btnId) {
				
					case "btnDtl":
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.clear();
												
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"usimProdInfo"}, PAGE.FORM2, "usimProdId"); // 유심종류
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"usimProdDetailInfo", etc1:grid.getSelectedRowData().usimProdId}, PAGE.FORM2, "usimProdDtlId"); // 제품명
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0257"}, PAGE.FORM2, "dlvryType"); // 배송유형
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"dStateCode"}, PAGE.FORM2, "dlvryStateCode"); // 진행상태
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"sStateCode"}, PAGE.FORM2, "selfStateCode"); // 신청상태
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM2, "dlvryTelFn"); // 연락처
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"PERCEL"}, PAGE.FORM2, "tbCd"); // 택배사
						
						PAGE.FORM2.setFormData(grid.getSelectedRowData(), true);
						
						mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"selFDlvryIdx" : grid.getSelectedRowData().selfDlvryIdx, "resNo":grid.getSelectedRowData().resNo, "trgtMenuId":"MSP1010030"}, function(result){});
						
						if (maskingYn == "Y") {
							mvno.ui.showButton("FORM2" , "btnSave");
							mvno.ui.hideButton("FORM2" , "btnSaveMask");
						} else {
							PAGE.FORM2.disableItem("cstmrName");
							PAGE.FORM2.disableItem("minorAgentName");
							PAGE.FORM2.disableItem("sysRdate");
							//PAGE.FORM2.disableItem("selfDlvryIdx");
							PAGE.FORM2.disableItem("dlvryStateCode");
							PAGE.FORM2.disableItem("usimProdId");
							PAGE.FORM2.disableItem("usimProdDtlId");
							PAGE.FORM2.disableItem("reqUsimSn");
							PAGE.FORM2.disableItem("selfMemo");
							PAGE.FORM2.disableItem("dlvryName");
							PAGE.FORM2.disableItem("dlvryTelFn");
							PAGE.FORM2.disableItem("dlvryTelMn");
							PAGE.FORM2.disableItem("dlvryTelRn");
							PAGE.FORM2.disableItem("dlvryPost");
							PAGE.FORM2.disableItem("btnDlvryPostFind");
							PAGE.FORM2.disableItem("dlvryAddr");
							PAGE.FORM2.disableItem("dlvryAddrDtl");
							PAGE.FORM2.disableItem("dlvryMemo");
							PAGE.FORM2.disableItem("tbCd");
							PAGE.FORM2.disableItem("dlvryNo");
							PAGE.FORM2.disableItem("reqUsimSn1");
							PAGE.FORM2.disableItem("reqUsimSn2");
							PAGE.FORM2.disableItem("reqUsimSn3");
							PAGE.FORM2.disableItem("reqUsimSn4");
							PAGE.FORM2.disableItem("reqUsimSn5");
							PAGE.FORM2.disableItem("reqUsimSn6");
							PAGE.FORM2.disableItem("reqUsimSn7");
							PAGE.FORM2.disableItem("reqUsimSn8");
							PAGE.FORM2.disableItem("reqUsimSn9");
							PAGE.FORM2.disableItem("reqUsimSn10");
							
							mvno.ui.hideButton("FORM2" , "btnSave");
							mvno.ui.showButton("FORM2" , "btnSaveMask");
						}
												
						mvno.ui.popupWindowById("FORM2", "상세화면", 790, 620, {
							onClose: function(win) {
// 								if (! PAGE.FORM2.isChanged()) return true;
// 								mvno.ui.confirm("CCMN0005", function(){
// 									win.closeForce();
// 								});
								win.closeForce();
							}
						});
	
						break;
						
					case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
						} else {
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download("/rcp/rcpMgmt/getUsimDlvryListByExcel.json?menuId=MSP1010030", true, {postData:searchData});
						}
						break;
						
					case 'btnDnExcelDtl' : //엑셀상세다운로드 버튼 클릭시
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
						} else {
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download("/rcp/rcpMgmt/getUsimDlvryDtlListByExcel.json?menuId=MSP1010030", true, {postData:searchData});
						}
						break;
						
					case "btnDnExcelUp":
						
						mvno.ui.createForm("FORM3");
						PAGE.FORM3.clear();
						
						mvno.ui.popupWindowById("FORM3", "엑셀업로드양식", 400, 350, {
							onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						
						break;
						
					case "btnDlvryNo":
						
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
							
							var url = ROOT + "/rcp/rcpMgmt/regDlvryNoUpList.json";
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
						
						mvno.ui.popupWindowById("POPUP1", "송장번호등록", 405, 490, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
						
						
						break;
						
					case "btnDlvryWait":
						
						mvno.ui.createGrid("POPUP4TOP");
						mvno.ui.createForm("POPUP4MID");
						
						PAGE.POPUP4TOP.clearAll();
						PAGE.POPUP4MID.clear();
						
						PAGE.POPUP4MID.setFormData({});
						
						var fileName;
						
						PAGE.POPUP4MID.attachEvent("onUploadFile", function(realName, serverName) {
							fileName = serverName;
						});
						
						PAGE.POPUP4MID.attachEvent("onUploadComplete", function(count){
							
							var url = ROOT + "/rcp/rcpMgmt/regDlvryWaitUpList.json";
							var userOptions = {timeout:180000};
							
							mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
								var rData = resultData.result;
								
								PAGE.POPUP4TOP.clearAll();
								
								PAGE.POPUP4TOP.parse(rData.data.rows, "js");
								
							}, userOptions);
						});
						
						PAGE.POPUP4MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP4TOP.clearAll();
							
							return true;
						});
						
						var uploader = PAGE.POPUP4MID.getUploader("file_upload1");
						uploader.revive();
						
						mvno.ui.popupWindowById("POPUP4", "배송대기등록", 405, 490, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
						
						
						break;
					case "btnDlvryOk":
						
						mvno.ui.createGrid("POPUP2TOP");
						mvno.ui.createForm("POPUP2MID");
						
						PAGE.POPUP2TOP.clearAll();
						PAGE.POPUP2MID.clear();
						
						PAGE.POPUP2MID.setFormData({});
						
						var fileName;
						
						PAGE.POPUP2MID.attachEvent("onUploadFile", function(realName, serverName) {
							fileName = serverName;
						});
						
						PAGE.POPUP2MID.attachEvent("onUploadComplete", function(count){
							
							var url = ROOT + "/rcp/rcpMgmt/regDlvryOkUpList.json";
							var userOptions = {timeout:180000};
							
							mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
								var rData = resultData.result;
								
								PAGE.POPUP2TOP.clearAll();
								
								PAGE.POPUP2TOP.parse(rData.data.rows, "js");
								
							}, userOptions);
						});
						
						PAGE.POPUP2MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP2TOP.clearAll();
							
							return true;
						});
						
						var uploader = PAGE.POPUP2MID.getUploader("file_upload1");
						uploader.revive();
						
						mvno.ui.popupWindowById("POPUP2", "배송완료등록", 405, 490, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
						
						
						break;
						
					case "btnOpenOk":
						
						mvno.ui.createGrid("POPUP3TOP");
						mvno.ui.createForm("POPUP3MID");
						
						PAGE.POPUP3TOP.clearAll();
						PAGE.POPUP3MID.clear();
						
						PAGE.POPUP3MID.setFormData({});
						
						var fileName;
						
						PAGE.POPUP3MID.attachEvent("onUploadFile", function(realName, serverName) {
							fileName = serverName;
						});
						
						PAGE.POPUP3MID.attachEvent("onUploadComplete", function(count){
							
							var url = ROOT + "/rcp/rcpMgmt/regOpenOkUpList.json";
							var userOptions = {timeout:180000};
							
							mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
								var rData = resultData.result;
								
								PAGE.POPUP3TOP.clearAll();
								
								PAGE.POPUP3TOP.parse(rData.data.rows, "js");
								
							}, userOptions);
						});
						
						PAGE.POPUP3MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP3TOP.clearAll();
							
							return true;
						});
						
						var uploader = PAGE.POPUP3MID.getUploader("file_upload1");
						uploader.revive();
						
						mvno.ui.popupWindowById("POPUP3", "개통완료등록", 405, 490, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
											
						break;
					//2020.12.10 유심일련번호등록(right) 버튼 이벤트 추가 시작						
					case "btnUsimSn":
						
						mvno.ui.createGrid("POPUP5TOP");
						mvno.ui.createForm("POPUP5MID");
						
						PAGE.POPUP5TOP.clearAll();
						PAGE.POPUP5MID.clear();
						
						PAGE.POPUP5MID.setFormData({});
						
						var fileName;
						
						PAGE.POPUP5MID.attachEvent("onUploadFile", function(realName, serverName) {
							fileName = serverName;
						});
						
						PAGE.POPUP5MID.attachEvent("onUploadComplete", function(count){
							
							var url = ROOT + "/rcp/rcpMgmt/regUsimSnUpList.json";
							var userOptions = {timeout:180000};
							
							mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
								var rData = resultData.result;
								
								PAGE.POPUP5TOP.clearAll();
								
								PAGE.POPUP5TOP.parse(rData.data.rows, "js");
								
							}, userOptions);
						});
						
						PAGE.POPUP5MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP5TOP.clearAll();
							
							return true;
						});
						
						var uploader = PAGE.POPUP5MID.getUploader("file_upload1");
						uploader.revive();
						
						mvno.ui.popupWindowById("POPUP5", "유심일련번호등록", 405, 490, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
											
						break;						
						
				}
			}
		},

		GRID2 : {
			css : {
				height : "140px"
			},
			title : "일련번호 상세",
			header : "유심주문번호,주문번호,주문수량,제품명,일련번호,계약번호,등록일자,reqUsimSnOrg",//7
			columnIds : "usimBuySeq,selfDlvryIdx,qntySort,usimProdDtlNm,reqUsimSn,svcCntrNo,cretDt,reqUsimSnOrg",
			widths : "100,100,100,100,300,100,150,0",
			colAlign : "center,center,center,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str",
			hiddenColumns: [7],
			/* paging:true,
			pageSize:20, */
			buttons : {
				right : {
// 					btnReg : { label : "등록" },
					btnMod : { label : "수정" }
				}
			},
			onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
// 					case "btnReg":
// 						if(PAGE.GRID1.getSelectedRowData() == null){
// 							mvno.ui.alert("ECMN0002");
// 							break;
// 						}
						
// 						mvno.ui.createForm("FORM4");
// 						PAGE.FORM4.clear();
// 						PAGE.FORM4.setItemValue("saveType", "I");
// 						PAGE.FORM4.setItemValue("selfDlvryIdx", PAGE.GRID1.getSelectedRowData().selfDlvryIdx);
						
// 						mvno.ui.popupWindowById("FORM4", "유심일련번호 등록", 370, 220, {
// 							onClose: function(win) {
// 								if (! PAGE.FORM4.isChanged()) return true;
// 								mvno.ui.confirm("CCMN0005", function(){
// 									win.closeForce();
// 								});
// 							}
// 						});
						
// 						break;
						
					case "btnMod":
						if(PAGE.GRID2.getSelectedRowData() == null){
							mvno.ui.alert("ECMN0002");
							break;
						}
							
						mvno.ui.createForm("FORM4");
						PAGE.FORM4.clear();
						PAGE.FORM4.setFormData(PAGE.GRID2.getSelectedRowData());
						PAGE.FORM4.setItemValue("saveType", "U");
						PAGE.FORM4.setItemValue("contractNum", PAGE.GRID2.getSelectedRowData().svcCntrNo);
						
						mvno.ui.popupWindowById("FORM4", "유심일련번호 수정", 370, 220, {
							onClose: function(win) {
// 								if (! PAGE.FORM4.isChanged()) return true;
// 								mvno.ui.confirm("CCMN0005", function(){
// 									win.closeForce();
// 								});
								win.closeForce();
							}
						});

						if (maskingYn != "Y") {
							PAGE.FORM4.disableItem("reqUsimSn");
							PAGE.FORM4.disableItem("contractNum");
							mvno.ui.hideButton("FORM4" , "btnSave");
						}
							
						break; 

				}
			},
		},
		
		FORM2 : {
			items : [
				{type:"block", list:[
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:80, blockOffset:0},
					// 고객정보
					{type: "fieldset", label: "고객정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"신청일자", name:"sysRdate", width:120,readonly:true, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"주문번호", name:"selfDlvryIdx", width:120, offsetLeft:20, readonly:true, disabled:true}
						]},
						{type:"block", list:[
							{type:"input", label:"고객명", name:"cstmrName", width:120, readonly:true, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"법정대리인", name:"minorAgentName", width:120, offsetLeft:20, readonly:true, disabled:true}
						]}
					]},
					// 신청정보
					{type:"fieldset", label:"신청정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"select", label:"진행상태", name:"dlvryStateCode", width:120},
							{type:"newcolumn"},
							{type:"select", label:"신청서상태", name:"selfStateCode", width:120, offsetLeft:20},
							{type:"newcolumn"},
							{type:"select", label:"배송유형", name:"dlvryType", width:120, offsetLeft:20, disabled:true}
						]},
						{type:"block", list:[
							{type:"select", label:"유심종류", name:"usimProdId", width:120},
							{type:"newcolumn"},
							{type:"select", label:"제품명", name:"usimProdDtlId", width:120, offsetLeft:20},
							{type:"newcolumn"},
							{type:"input", label:"주문수량", name:"reqBuyQnty", width:120, offsetLeft:20, maxLength:2, validate:'ValidNumeric', readonly:true, disabled:true}
						]},
						{type:"block", list:[
							{type:"input", label:"유심번호1", name:"reqUsimSn1", width:120, maxLength:19, validate:'ValidNumeric'},
							{type:"newcolumn"},
							{type:"input", label:"유심번호2", name:"reqUsimSn2", width:120, offsetLeft:20, maxLength:19, validate:'ValidNumeric'},
							{type:"newcolumn"},
							{type:"input", label:"유심번호3", name:"reqUsimSn3", width:120, offsetLeft:20, maxLength:19, validate:'ValidNumeric'},
							{type:"hidden", name:"reqUsimSn1org"},
							{type:"hidden", name:"reqUsimSn2org"},
							{type:"hidden", name:"reqUsimSn3org"},
						]},
						{type:"block", list:[
							{type:"input", label:"유심번호4", name:"reqUsimSn4", width:120, maxLength:19, validate:'ValidNumeric'},
							{type:"newcolumn"},
							{type:"input", label:"유심번호5", name:"reqUsimSn5", width:120, offsetLeft:20, maxLength:19, validate:'ValidNumeric'},
							{type:"newcolumn"},
							{type:"input", label:"유심번호6", name:"reqUsimSn6", width:120, offsetLeft:20, maxLength:19, validate:'ValidNumeric'},
							{type:"hidden", name:"reqUsimSn4org"},
							{type:"hidden", name:"reqUsimSn5org"},
							{type:"hidden", name:"reqUsimSn6org"},
						]},
						{type:"block", list:[
							{type:"input", label:"유심번호7", name:"reqUsimSn7", width:120, maxLength:19, validate:'ValidNumeric'},
							{type:"newcolumn"},
							{type:"input", label:"유심번호8", name:"reqUsimSn8", width:120, offsetLeft:20, maxLength:19, validate:'ValidNumeric'},
							{type:"newcolumn"},
							{type:"input", label:"유심번호9", name:"reqUsimSn9", width:120, offsetLeft:20, maxLength:19, validate:'ValidNumeric'},
							{type:"hidden", name:"reqUsimSn7org"},
							{type:"hidden", name:"reqUsimSn8org"},
							{type:"hidden", name:"reqUsimSn9org"},
						]},
						{type:"block", list:[
							{type:"input", label:"유심번호10", name:"reqUsimSn10", width:120, maxLength:19, validate:'ValidNumeric'},
							{type:"hidden", name:"reqUsimSn10org"},
						]},
						{type:"block", list:[
							{type:"input", label:"메모", name:"selfMemo", width:572, maxLength:200}
						]}
					]},
					// 배송정보
					{type: "fieldset", label: "배송정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"받으시는분", name:"dlvryName", width:120, maxLength:20},
							{type:"newcolumn"},
							{type:"select", label:"연락처", name:"dlvryTelFn", width:80, offsetLeft:20},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryTelMn", width:60, maxLength:4, validate:'ValidNumeric'},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryTelRn", width:60, maxLength:4, validate:'ValidNumeric'}
						]},
						{type:"block", list:[
							{type:"input", label:"우편번호", name:"dlvryPost", width:75, readonly:true, maxLength:6},
							{type:"newcolumn"},
							{type:"button", value:"주소찾기", name :"btnDlvryPostFind"},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryAddr", width:417, readonly:true}
						]},
						{type:"block", list:[
							{type:"input", label:"상세주소", name:"dlvryAddrDtl", width:572, maxLength:80}
						]},
						{type:"block", list:[
							{type:"input", label:"요청사항",name:"dlvryMemo", width:572, maxLength:200}
						]},
						{type:"block", list:[
							{type:"select", label:"택배사", name:"tbCd", width:130},
							{type:"newcolumn"},
							{type:"input", label:"송장번호", name:"dlvryNo", width:130, offsetLeft:10, maxLength:20, validate:'ValidAplhaNumeric'}
						]}
					]}
				]}
			],
			buttons : {
				center : {
					btnSave : {label : "저장" },
					btnSaveMask : {label : "저장"},
					btnClose : { label: "닫기" }
				}
			},
			onValidateMore : function (data){
				if(data.usimProdId == ""){
					this.pushError("usimProdId", "유심종류", "유심종류 정보를 선택하세요");
				}
			},
			onChange : function(name, value) {//onChange START
				// 검색구분
				if(name == "usimProdId") {
					if (value == "") {
						PAGE.FORM2.setItemValue("usimProdDtlId", "");
						PAGE.FORM2.disableItem("usimProdDtlId");
					} else {
						PAGE.FORM2.enableItem("usimProdDtlId");
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"usimProdDetailInfo", etc1:PAGE.FORM2.getItemValue("usimProdId")}, PAGE.FORM2, "usimProdDtlId"); // 제품명
					}
				}
				
			},
			onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
					
					case "btnDlvryPostFind":
						mvno.ui.codefinder4ZipCd("FORM2", "dlvryPost", "dlvryAddr", "dlvryAddrDtl");
						PAGE.FORM2.enableItem("dlvryAddrDtl");
						break;
						
					case "btnSave":
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/setUsimDlvryInfo.json", form, function(result) {
							mvno.ui.closeWindowById(PAGE.FORM2, true);
							mvno.ui.notify("NCMN0001");
							PAGE.GRID1.refresh();
							PAGE.GRID2.refresh();
						});
						break;
					
					case "btnSaveMask":
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/setUsimDlvryInfoMask.json", form, function(result) {
							mvno.ui.closeWindowById(PAGE.FORM2, true);
							mvno.ui.notify("NCMN0001");
							PAGE.GRID1.refresh();
							PAGE.GRID2.refresh();
						});
						break;
						
					case "btnClose" :
						mvno.ui.closeWindowById(PAGE.FORM2, true);
						break;
				}
			}
		}
		
		,FORM3 : {
			title : "<font size='2' color='red'>택배 발송건만 업로드 가능 </font>",
			items : [
				{type:"block", list:[
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:180, labelHeight:30, blockOffset:10},
					{type:"block", list:[
						{type:"label", label:"송장번호등록양식", offsetLeft:30},
						{type:"newcolumn"},
						{type:"button", value:"다운로드", name :"btnDlvryNoEx"}
					]}
				]},
				{type:"block", list:[
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:180, labelHeight:30, blockOffset:10},
					{type:"block", list:[
						{type:"label", label:"배송대기등록양식", offsetLeft:30},
						{type:"newcolumn"},
						{type:"button", value:"다운로드", name :"btnDlvryWaitEx"}
					]}
				]},
				{type:"block", list:[
				 	{type:"settings", position:"label-left", labelAlign:"right", labelWidth:180, labelHeight:30, blockOffset:0},
					{type:"block", list:[
						{type:"label", label:"배송완료등록양식", offsetLeft:30},
						{type:"newcolumn"},
						{type:"button", value:"다운로드", name :"btnDlvryOkEx"}
					]}
				]},
				{type:"block", list:[
				 	{type:"settings", position:"label-left", labelAlign:"right", labelWidth:180, labelHeight:30, blockOffset:0},
					{type:"block", list:[
						{type:"label", label:"개통완료등록양식", offsetLeft:30},
						{type:"newcolumn"},
						{type:"button", value:"다운로드", name :"btnOpenOkEx"}
					]}
				]},
				//2020.12.10 엑셀업로드양식FORM에 유심일련번호등록양식 추가 
				{type:"block", list:[
				 	{type:"settings", position:"label-left", labelAlign:"right", labelWidth:180, blockOffset:0},
					{type:"block", list:[
						{type:"label", label:"유심일련번호등록양식", offsetLeft:30},
						{type:"newcolumn"},
						{type:"button", value:"다운로드", name :"btnUsimSnEx"}
					]}
				]}				
			],
			buttons : {
				center : {
					btnClose : { label: "닫기" }
				}
			},
			onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
					
					case "btnDlvryNoEx":
						
						mvno.cmn.download("/rcp/rcpMgmt/getDlvryNoTempExcelDown.json");
						
						break;
					
					case "btnDlvryWaitEx":
						
						mvno.cmn.download("/rcp/rcpMgmt/getDlvryWaitTempExcelDown.json");
						
						break;
						
					case "btnDlvryOkEx":
						
						mvno.cmn.download("/rcp/rcpMgmt/getDlvryOkTempExcelDown.json");
						
						break;
						
					case "btnOpenOkEx":
						
						mvno.cmn.download("/rcp/rcpMgmt/getOpenOkTempExcelDown.json");
						
						break;
					//2020.12.10 엑셀다운로드 양식(유심일련번호등록) 추가						
					case "btnUsimSnEx":
						
						mvno.cmn.download("/rcp/rcpMgmt/getUsimSnTempExcelDown.json");
						
						break;						
						
					case "btnClose" :
						mvno.ui.closeWindowById(PAGE.FORM3, true);
						break;
				}
			}
		}
		, FORM4 : {
			items : [
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'usimBuySeq', label: '유심주문번호', labelWidth:100 , width: 120, offsetLeft: 0, readonly:true, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'selfDlvryIdx', label: '주문번호', labelWidth:100 , width: 120, offsetLeft: 0, readonly:true, disabled:true},
				]},
				{type: "block", blockOffset: 0, offsetLeft: 30, list: [
					{type: 'input', name: 'reqUsimSn', label: '유심일련번호', labelWidth:100 , width: 120, offsetLeft: 0, maxLength:19, validate:'ValidNumeric', required: true},
					{type: "newcolumn"},
					{type: 'input', name: 'contractNum', label: '계약번호', labelWidth:100 , width: 120, offsetLeft: 0, maxLength:9, validate:'ValidNumeric'},
					{type: 'hidden', name: 'saveType'},
					{type: 'hidden', name: 'reqUsimSnOrg'}
				]},
			],

			buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label: "닫기" }
				}
			},

			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)
				
				switch (btnId) {
					case "btnSave":
						//저장버튼 클릭						
						if(PAGE.FORM4.getItemValue("reqUsimSn").length != 19){
							mvno.ui.alert("유심일련번호는 19자리를 입력해주세요.");
							return;
						} else {
							mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/saveUsimBuyTxnSn.json", form, function(result) {
								mvno.ui.closeWindowById(form, true);  
								mvno.ui.notify("NCMN0004");
								PAGE.GRID1.refresh();
								PAGE.GRID2.refresh();
							});
						}
						break;
	
					case "btnClose" :
						//닫기버튼 클릭
						mvno.ui.closeWindowById(form);
						break;
				}	
			},
			
			onValidateMore : function (data){
				var form = this; 

				if(data.reqUsimSn == null){
					this.pushError("data.reqUsimSn","유심일련번호","유심일련변호를 입력해주세요.");
				}
			}
		}
				
		, POPUP1TOP : {
			css : { height : "250px" },
			title : "송장번호등록",
			header : "주문번호,택배사,송장번호",
			columnIds : "selfDlvryIdx,tbNm,dlvryNo",
			widths : "110,110,110",
			colAlign : "center,center,center",
			colTypes : "ro,ro,ro"
		}	//POPUP1TOP End
		
		, POPUP1MID : {
			title : "엑셀업로드",
			items : [
				{ type : "block",
				  list : [
					{ type : "newcolumn", offset : 10 },
					{ type : "upload", 
					  label : "송장번호등록 엑셀파일",
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
						
						mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/regDlvryNoList.json", sData, function(result) {
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
		
		, POPUP2TOP : {
			css : { height : "250px" },
			title : "배송완료등록",
			header : "송장번호",
			columnIds : "dlvryNo",
			widths : "330",
			colAlign : "center",
			colTypes : "ro"
		}	//POPUP2TOP End
		
		, POPUP2MID : {
			title : "엑셀업로드",
			items : [
				{ type : "block",
				  list : [
					{ type : "newcolumn", offset : 10 },
					{ type : "upload", 
					  label : "송장번호등록 엑셀파일",
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
						
						if(PAGE.POPUP2TOP.getRowsNum() == 0) {
							mvno.ui.alert("파일을 첨부해 주세요.");
							break;
						}
						
						var arrObj = [];
						
						PAGE.POPUP2TOP.forEachRow(function(id){
							var arrData = PAGE.POPUP2TOP.getRowData(id);
							
							arrObj.push(arrData);
						});
						
						var sData = {};
						
						sData.items = arrObj;
						
						var userOptions = {timeout:180000};
						
						mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/regDlvryOkList.json", sData, function(result) {
							mvno.ui.closeWindowById("POPUP2", true);
							mvno.ui.notify("NCMN0004");
							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
						}, userOptions);
						
					break;
						
					case "btnClose":
						mvno.ui.closeWindowById("POPUP2");
					break;
				}
			}
		}	//POPUP2MID End
		
		, POPUP3TOP : {
			css : { height : "250px" },
			title : "개통완료등록",
			header : "주문번호,유심주문번호,계약번호",
			columnIds : "selfDlvryIdx,usimBuySeq,contractNum",
			widths : "110,110,110",
			colAlign : "center,center,center",
			colTypes : "ro,ro,ro"
		}	//POPUP3TOP End
		
		, POPUP3MID : {
			title : "엑셀업로드",
			items : [
				{ type : "block",
				  list : [
					{ type : "newcolumn", offset : 10 },
					{ type : "upload", 
					  label : "개통완료등록 엑셀파일",
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
						
						if(PAGE.POPUP3TOP.getRowsNum() == 0) {
							mvno.ui.alert("파일을 첨부해 주세요.");
							break;
						}
						
						var arrObj = [];
						
						PAGE.POPUP3TOP.forEachRow(function(id){
							var arrData = PAGE.POPUP3TOP.getRowData(id);
							
							arrObj.push(arrData);
						});
						
						var sData = {};
						
						sData.items = arrObj;
						
						var userOptions = {timeout:180000};
						
						mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/regOpenOkList.json", sData, function(result) {
							mvno.ui.closeWindowById("POPUP3", true);
							mvno.ui.notify("NCMN0004");
							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
						}, userOptions);
						
					break;
						
					case "btnClose":
						mvno.ui.closeWindowById("POPUP3");
					break;
				}
			}
		}	//POPUP3MID End
		
		, POPUP4TOP : {
			css : { height : "250px" },
			title : "배송대기등록",
			header : "주문번호",
			columnIds : "selfDlvryIdx",
			widths : "330",
			colAlign : "center",
			colTypes : "ro"
		}	//POPUP4TOP End
		
		, POPUP4MID : {
			title : "엑셀업로드",
			items : [
				{ type : "block",
				  list : [
					{ type : "newcolumn", offset : 10 },
					{ type : "upload", 
					  label : "배송대기등록 엑셀파일",
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
						
						if(PAGE.POPUP4TOP.getRowsNum() == 0) {
							mvno.ui.alert("파일을 첨부해 주세요.");
							break;
						}
						
						var arrObj = [];
						
						PAGE.POPUP4TOP.forEachRow(function(id){
							var arrData = PAGE.POPUP4TOP.getRowData(id);
							
							arrObj.push(arrData);
						});
						
						var sData = {};
						
						sData.items = arrObj;
						
						var userOptions = {timeout:180000};
						
						mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/regDlvryWaitList.json", sData, function(result) {
							mvno.ui.closeWindowById("POPUP4", true);
							mvno.ui.notify("NCMN0004");
							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
						}, userOptions);
						
					break;
						
					case "btnClose":
						mvno.ui.closeWindowById("POPUP4");
					break;
				}
			}
		}	//POPUP2MID End

		//2020.12.10 유심일련번호등록 팝업 추가 시작
		//POPUP5 TOP & MID START
		, POPUP5TOP : {
			css : { height : "250px" },
			title : "유심일련번호등록",
			header : "주문번호,유심번호",
			columnIds : "selfDlvryIdx,reqUsimSn",
			widths : "165,165",
			colAlign : "center,center",
			colTypes : "ro,ro"
		}	//POPUP4TOP End
		
		, POPUP5MID : {
			title : "엑셀업로드",
			items : [
				{ type : "block",
				  list : [
					{ type : "newcolumn", offset : 10 },
					{ type : "upload", 
					  label : "유심일련번호등록 엑셀파일",
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
						
						if(PAGE.POPUP5TOP.getRowsNum() == 0) {
							mvno.ui.alert("파일을 첨부해 주세요.");
							break;
						}
						
						var arrObj = [];
						
						PAGE.POPUP5TOP.forEachRow(function(id){
							var arrData = PAGE.POPUP5TOP.getRowData(id);
							
							arrObj.push(arrData);
						});
						
						var sData = {};
						
						sData.items = arrObj;
						
						var userOptions = {timeout:180000};
						
						mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/regUsimSnList.json", sData, function(result) {
							mvno.ui.closeWindowById("POPUP5", true);
							mvno.ui.notify("NCMN0004");
							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
						}, userOptions);
						
					break;
						
					case "btnClose":
						mvno.ui.closeWindowById("POPUP5");
					break;
				}
			}
		}	////POPUP5 TOP & MID END		
		//2020.12.10 유심일련번호등록 팝업 추가 종료
		
	}
			
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		 onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			mvno.ui.createGrid("GRID2");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"usimProdInfo"}, PAGE.FORM1, "usimProdInfo"); // 유심종류
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0257"}, PAGE.FORM1, "dlvryType"); // 배송유형
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"dStateCode"}, PAGE.FORM1, "dStateCode"); // 진행상태
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"sStateCode"}, PAGE.FORM1, "sStateCode"); // 신청상태
		}
	};

	/* 주소 setting */
	function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
		PAGE.FORM2.setItemValue("dlvryPost",zipNo);
		PAGE.FORM2.setItemValue("dlvryAddr",roadAddrPart1);
		PAGE.FORM2.setItemValue("dlvryAddrDtl",addrDetail);
	}
</script>
