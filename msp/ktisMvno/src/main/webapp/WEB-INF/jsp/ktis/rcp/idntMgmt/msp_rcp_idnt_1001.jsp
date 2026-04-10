<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<div id="GROUP1">
	<div id="FORM2" class="section-search"></div><!-- 가입자검색조건 -->
	<div id="GRID2" class="section-full"></div><!-- 가입자검색목록 -->
	<div id="FORM3" class="section-box"></div><!-- 요청기관 -->
	<div id="UPLOAD" class="section-box"></div><!-- 엑셀업로드 -->
</div>

<div id="GROUP2">
	<div id="GRID3" class="section-full"></div><!-- 가입자검색목록 -->
</div>
<!-- Script -->
<script type="text/javascript">
	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blackOffset:0}
					,{type:"block", list:[
						{type : "calendar",width : 100,label : "처리일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d", name : "procDateS", offsetLeft:10},
						{type : "newcolumn"},
						{type : "calendar",label : "~",name : "procDateE",labelAlign : "center",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 10,width : 100},
						{type : "newcolumn"},						
						{type:"select", label:"검색구분", name:"searchGb", width:120, offsetLeft:10}
						,{type:"newcolumn"}
						,{type:"input", name:"searchVal", width:100}
					]},
				{type : "block",
					list : [
						,{type:"select", label:"접수처", name:"invstType", width:100, offsetLeft:10}
						,{type:"newcolumn"}
						,{type:"input", name:"invstVal", width:120}
						,{type:"newcolumn"}
						,{type:"input", label:"문서번호", name:"reqDoc", width:120, offsetLeft:10}
						,{type:"newcolumn"}
						,{type:"select", name:"reqType", label:"요청종류", width:170, offsetLeft:10}
					]
				},					
					, {type : "button",name : "btnSearch",value : "조회", className:"btn-search2"} 
				],
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							PAGE.GRID1.list(ROOT + "/rcp/idntmgmt/getInvstReqList.json", form);
							break;
					}
				},
				onChange : function(name, value) {//onChange START
					if(name == "searchGb") {
						PAGE.FORM1.setItemValue("searchVal", "");
					}
					if(name == "invstType") {
						PAGE.FORM1.setItemValue("invstVal", "");
					}
				},
				onValidateMore : function (data){
					if( data.searchGb != "" && data.searchVal.trim() == ""){
						this.pushError("searchGb", "검색어", "검색어를 입력하세요");
						PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
					}
					
					if( data.invstType != "" && data.invstVal.trim() == ""){
						this.pushError("invstType", "접수처", "검색어를 입력하세요");
						PAGE.FORM1.setItemValue("invstVal", ""); // 검색어 초기화
					}
					
					if (this.getItemValue("procDateS", true) > this.getItemValue("procDateE", true)) {
						this.pushError("procDateS", "처리일자", "처리종료일자가 시작일보다 클수 없습니다.");
					}
				
				}
			}
			,GRID1 : {
				css : {
					height : "535px"
				}
				,title : "조회결과"
				,header : "처리일자,처리번호,식별유형,식별번호,전화번호,문서번호,접수처,이름,계약번호,상태,선후불,가입일,해지일,주소,접수처구분,지역,요청종류,이메일주소,요청자직책,요청자이름,결재권자직책,결재권자이름,사후요청,요청일자,처리자"	//17
				,columnIds : "procDt,seqNum,customerTypeNm,userSsn,subscriberNo,invstVal,invstNm,cstmrNm,contractNum,subStatusNm,pppo,openDt,tmntDt,cstmrAddr,invstTypeNm,invstLoc,reqTypeNm,email,reqOdty,reqUser,appOdtyNm,appUser,reqRsn,reqDttm,procrNm"
				,widths : "100,100,100,100,120,100,100,100,100,80,80,100,100,200,100,50,100,120,100,100,100,100,100,120,100"
				,colAlign : "center,center,left,center,center,center,left,left,center,center,center,center,center,left,center,center,center,left,center,left,center,left,center,center,center"
				,colTypes : "roXd,ro,ro,ro,roXp,ro,ro,ro,ro,ro,ro,roXd,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXd,ro"
				,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
				,multiCheckbox : true
				,paging : true
				,pageSize:20
				,showPagingAreaOnInit: true
				,buttons : {
					hright : {
						btnExcel : { label : "엑셀다운로드" }
					}
					,left : {
						btnPrint : { label : "출력" }
						, btnDnExcel : { label : "가입자확인등록양식" }
					}
					,right : {
						btnDel : { label : "삭제" }
					    , btnReg : { label : "가입자확인" }
					}
				}
				,onButtonClick : function(btnId, selectedData) {
					var grid = this;
					switch(btnId){
						case "btnPrint":
							var data = this.getSelectedRowData();
							
							if(data == null) {
								mvno.ui.alert("ECMN0002");
								return;
							}
							
							var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/rcp/invstReqReport.jrf" + "&arg=";
							param = encodeURI(param);
							
							var arg = "seqNum#" + data.seqNum + "#";
							arg = encodeURIComponent(arg);
							
							param = param + arg;
							
							console.log("paran=" + param);
							
							var msg = "확인서를 출력하시겠습니까?";
							mvno.ui.confirm(msg, function() {
								mvno.ui.popupWindow("/cmn/report/report.do"+param, "가입자확인 통지서", 900, 700, {
									onClose: function(win) {
										win.closeForce();
									}
								});
							});
							
							break;
						case "btnExcel":
							var arrData = grid.getCheckedRowData();						
							var rowIds = PAGE.GRID1.getCheckedRows(0); // 선택된 checkbox 의 rowId 를 가져온다.
							var form = PAGE.FORM1;
							
							if(rowIds == null || rowIds == ''){//선택된 것이 없으면 전체 다운로드
								if(this.getRowsNum() == 0) {
									mvno.ui.alert("데이터가 존재하지 않습니다.");
									return;
								} else {
									var searchData =  PAGE.FORM1.getFormData(true);
									mvno.cmn.download('/rcp/idntmgmt/getInvstReqListEx.json'+"?menuId=MSP1030007", true, {postData:searchData});
									break; 
								}
								
							}else{//선택된 것이 있으면, 수사기관, 문서 번호로 다운로드
								if(this.getRowsNum() == 0) {
									mvno.ui.alert("데이터가 존재하지 않습니다.");
									return;
								} else {
									
									var searchData =  PAGE.FORM1.getFormData(true);
									var checkedData = PAGE.GRID1.classifyRowsFromIds(rowIds);

									searchData["seqNumStr"] = JSON.stringify(checkedData);

									mvno.cmn.download('/rcp/idntmgmt/getInvstReqListMultiEx.json'+"?menuId=MSP1030007", true, {postData:searchData});
									break; 

								}
								
							}

						case "btnReg":
							mvno.ui.createForm("FORM2");//조회조건
							mvno.ui.createForm("UPLOAD");//엑셀업로드
							mvno.ui.createGrid("GRID2");//검색결과
							mvno.ui.createForm("FORM3");//요청기관
							PAGE.FORM3.disableItem("reqOdty");
							PAGE.FORM3.disableItem("appOdtyNm");
							PAGE.GRID2.clearAll();
							PAGE.FORM2.clear();
							PAGE.FORM3.clear();
							
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0022",orderBy:"etc2"}, PAGE.FORM2, "searchGb"); //검색구분
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0108"}, PAGE.FORM3, "reqType"); //요청종류 
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0107"}, PAGE.FORM3, "invstType"); //접수처구분 
				            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9020"}, PAGE.FORM3, "invstLoc"); // 지역
				            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0112", orderBy:"etc1"}, PAGE.FORM3, "appOdty"); // 결재권자직책
				            PAGE.FORM3.setItemValue("reqOdty", "수사관");
				            
							PAGE.FORM2.clearChanged();
							PAGE.FORM3.clearChanged();
													
							
							
							PAGE.UPLOAD.clear();
							PAGE.UPLOAD.setFormData({});
							
							var fileName;
							
							PAGE.UPLOAD.attachEvent("onUploadFile", function(realName, serverName) {
								fileName = serverName;
							});
							
							PAGE.UPLOAD.attachEvent("onUploadComplete", function(count){
								
								var url = ROOT + "/rcp/idntmgmt/regInvstUpList.json";
								var userOptions = {timeout:180000};
								
								mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
									var rData = resultData.result;
									
									PAGE.GRID2.clearAll();

									PAGE.FORM2.clear();
									PAGE.FORM3.clear();
									
									PAGE.GRID2.parse(rData.data.rows, "js");
									
									PAGE.FORM2.disableItem("searchGb");
									PAGE.FORM2.disableItem("searchVal");
									PAGE.FORM2.disableItem("btnSearch");
									PAGE.FORM3.disableItem("reqType");
									PAGE.FORM3.disableItem("invstType");
									PAGE.FORM3.disableItem("invstNm");
									PAGE.FORM3.disableItem("invstLoc");
									PAGE.FORM3.disableItem("invstVal");
									PAGE.FORM3.disableItem("reqOdty");
									PAGE.FORM3.disableItem("reqUser");
									PAGE.FORM3.disableItem("reqRsn");
									PAGE.FORM3.disableItem("appUser");
									PAGE.FORM3.disableItem("appOdty");
									PAGE.FORM3.disableItem("appOdtyNm");
									PAGE.FORM3.disableItem("reqDttm");

									PAGE.FORM2.setItemValue("btnChk", "1");
									
									mvno.ui.alert(rData.data.total + "건의 데이터가 조회되었습니다.");
								}, userOptions);
							});
							
							PAGE.UPLOAD.attachEvent("onFileRemove",function(realName, serverName){
								PAGE.GRID2.clearAll();
								
								console.log("onFileRemove", realName, serverName);

								PAGE.FORM2.enableItem("searchGb");
								PAGE.FORM2.enableItem("searchVal");
								PAGE.FORM2.enableItem("btnSearch");
								PAGE.FORM3.enableItem("reqType");
								PAGE.FORM3.enableItem("invstType");
								PAGE.FORM3.enableItem("invstNm");
								PAGE.FORM3.enableItem("invstLoc");
								PAGE.FORM3.enableItem("invstVal");
								PAGE.FORM3.disableItem("reqOdty");
								PAGE.FORM3.enableItem("reqUser");
								PAGE.FORM3.enableItem("reqRsn");
								PAGE.FORM3.enableItem("appUser");
								PAGE.FORM3.enableItem("appOdty");
								//PAGE.FORM3.enableItem("appOdtyNm");
								PAGE.FORM3.disableItem("appOdtyNm");
								PAGE.FORM3.enableItem("reqDttm");
								PAGE.FORM3.setItemValue("reqOdty", "수사관");
								

								PAGE.FORM2.setItemValue("btnChk", "0");
								
								return true;
							});
							
							var uploader = PAGE.UPLOAD.getUploader("file_upload1");
							uploader.revive();

							mvno.ui.popupWindowById("GROUP1", "가입자확인", 950, 775, {//750
								onClose: function(win) {
									mvno.ui.confirm("CCMN0005", function() {
										uploader.reset();
										win.closeForce();
									});
								}//, maximized:truez
							});
							
							break;
							
						case "btnDnExcel":

							mvno.cmn.download("/rcp/idntmgmt/regInvstTempExcelDown.json");
							
							break;

						case "btnDel":
							var rowIds = PAGE.GRID1.getCheckedRows(0); // 선택된 checkbox 의 rowId 를 가져온다.
							if (! rowIds) {
								mvno.ui.alert("ECMN0002");
								return;
							}			
							var checkedData = PAGE.GRID1.classifyRowsFromIds(rowIds);
							
							checkedData = JSON.stringify(checkedData);


							var url = ROOT + "/rcp/idntmgmt/savInvstReq.json";
							console.info("-----------------------------------------------");
							console.info(checkedData);
							console.info("-----------------------------------------------");
							console.info(JSON.stringify(checkedData));
							
							
							mvno.cmn.ajax4confirm("LGS00041", ROOT + "/rcp/idntmgmt/delinvstReq.json", {seqNumStr:checkedData}, function(result) {
								mvno.ui.notify("LGS00042");
								PAGE.GRID1.refresh();
							});

							break;
					}
				}
			}
			,FORM2 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blackOffset:0},
					{type:"block", list:[
						{type:"select", label:"검색구분", name:"searchGb", width:120, offsetLeft:10, required:true},
						{type:"newcolumn"},
						{type:"input", name:"searchVal", width:100}
					]}
					, {type : "button", name : "btnSearch", value : "조회", className:"btn-search1"} 
					, {type : 'hidden', name : "btnChk", value : "0"}, //엑셀업로드체크
				],
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							PAGE.GRID2.list(ROOT + "/rcp/idntmgmt/getInvstCusInfo.json", form);
							break;
					}
				},
				onChange : function(name, value) {//onChange START
					if(name == "searchGb") {
						PAGE.FORM2.setItemValue("searchVal", "");
					}
				},
				onValidateMore : function (data){
					if( data.searchGb != "" && data.searchVal.trim() == ""){
						this.pushError("searchGb", "검색어", "검색어를 입력하세요");
						PAGE.FORM2.setItemValue("searchVal", ""); // 검색어 초기화
					} 
				}
			}
			,GRID2 : {
				css : {
					height : "300px"
				}
				,title : "조회결과"
				,header : "이관여부,고객명,계약번호,고객유형,전화번호,가입일,해지일,계약상태,선후불,주소,요청종류,접수처,접수처명,지역,문서번호,요청자직책,요청자이름,결재권자직책,결재권자이름,사후요청,요청일자,,,,,,,,,,,,,,"
				,columnIds : "trnsYn,cstmrNmMsk,contractNum,customerTypeNm,subscriberNoMsk,openDt,tmntDt,subStatusNm,pppoNm,cstmrAddrMsk,reqTypeNm,invstTypeNm,invstNm,invstLocNm,invstVal,reqOdty,reqUser,appOdtyNm,appUser,reqRsn,reqDttm,searchGb,customerId,cstmrNm,subscriberNo,customerType,userSsn,subStatus,pppo,openDt,tmntDt,cstmrAddr,reqType,invstType,invstLoc"
				,widths : "80,100,100,80,100,100,100,80,80,200,100,100,100,100,100,100,100,100,100,120,100,0,0,0,0,0,0,0,0,0,0,0,0,0,0"
				,colAlign : "center,left,center,center,center,center,center,center,center,left,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center"
				,colTypes : "ro,ro,ro,ro,roXp,roXd,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
				,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" //35
				,hiddenColumns : [21,22,23,24,25,26,27,28,29,30,31,32,33,34] 
				,multiCheckbox : true
// 				,paging : true
// 				,pageSize : 20
// 				,showPagingAreaOnInit: true
				,onRowSelect:function(){
				}
				,onRowDblClicked : function(rowId) {
					mvno.ui.createGrid("GRID3");//검색결과
					PAGE.GRID3.clearAll();
					mvno.ui.popupWindowById("GROUP2", "번호이동 상세 이력", 700, 630, {
						onClose: function(win) {
							win.closeForce();
						}//, maximized:truez
					})
					var rowData = "";
					rowData = this.getRowData(rowId);
					/* selectContractNum = this.getSelectedRowData().contractNum */
					PAGE.GRID3.list(ROOT + "/rcp/idntmgmt/getInvstCusInfoDtl.json", rowData);
				}
			}
			,GRID3 : {
				css : {
					height : "500px"
				}
				,title : "조회결과"
				,header : "계약번호,전화번호,이벤트구분,이벤트발생일자"
				,columnIds : "contractNum,subscriberNoMsk,evntNm,evntChangeDate"
				,widths : "160,160,120,200"
				,colAlign : "center,center,center,center"
				,colTypes : "ro,roXp,ro,roXdt"
				,colSorting : "str,str,str,str" //35
// 				,paging : true
// 				,pageSize : 20
// 				,showPagingAreaOnInit: true
				,buttons : {
					hright : {
						btnExcelDown : { label : "엑셀다운로드" }
					}
				}
				,onButtonClick : function(btnId) {
					var grid = this;
					switch(btnId){
						case "btnExcelDown":
							debugger;
							var contractNum = grid.getRowData(1).contractNum

							// searchData["contractNum"] = JSON.stringify(contractNum);
	
							if(contractNum == null || contractNum == ''){//이력이 없는 경우
								
							} else {
								mvno.cmn.download('/rcp/idntmgmt/getInvstCusInfoDtlListEx.json'+"?menuId=MSP1030007&contractNum="+contractNum);
								break; 
							}
					}
				}
				
			}
			,UPLOAD : {
				title : "엑셀업로드",
				items : [
					{ type : "block",
					  list : [
						{ type : "newcolumn", offset : 10 },
						{ type : "upload", 
						  label : "가입자확인 등록 엑셀파일",
						  name : "file_upload1",
						  inputWidth : 260,
						  url : "/rcp/dlvyMgmt/regParExcelUp.do",
						  userdata : { limitSize:10000 },
						  autoStart : true } 
					]},
					{type:"input", width:100, name:"rowData", hidden:true}
				]
			}	//UPLOAD End
			
			,FORM3 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blackOffset:0},
					{type:"block",  list:[
						{type:"select", name:"reqType", label:"요청종류", width:150, offsetLeft:10, required:true},
						{type:"newcolumn"},
						{type:"select", name:"invstType", label:"접수처", width:80, offsetLeft:20, required:true},
						{type:"newcolumn"},
						{type:"input", name:"invstNm", width:100, maxLength:30},
						{type:"newcolumn"},
                        {type:"select", name:"invstLoc", label:"지역", width:80, offsetLeft:20, required:true},
						{type:"newcolumn"},
						{type:"input", name:"invstVal", label:"문서번호", width:100, offsetLeft:10, maxLength:30, required:true}
				]},{type:"block", list:[
					,{type: "fieldset", label: "사후등록정보", inputWidth:857, list:[
						   {type:"block", list:[
							{type:"input", name:"reqOdty", label:"요청자 직책", width:194, offsetLeft:10, required:true , value:"수사관" , labelWidth:100 , maxLength:100},
							{type:"newcolumn"},
							{type:"input", name:"reqUser", label:"요청자 이름", width:90, offsetLeft:20, required:true , labelWidth:100 , maxLength:60},
							{type:"newcolumn"},
	                        {type:"input", name:"reqRsn", label:"사후요청", width:200, offsetLeft:20 , labelWidth:68 ,  maxLength:100}
						]}
						,{type:"block", list:[
							{type:"select", name:"appOdty", label:"결재권자 직책", width:90, offsetLeft:10, required:true , labelWidth:100},
							{type:"newcolumn"},
							{type:"input", name:"appOdtyNm", width:100, maxLength:100},
							{type:"newcolumn"},
							{type:"input", name:"appUser", label:"결재권자 이름", width:90, offsetLeft:20, required:true , labelWidth:100 , maxLength:60},
							{type:"newcolumn"},
							{type : "calendar",width : 100,label : "요청일자",dateFormat : "%Y-%m-%d", serverDateFormat : "%Y%m%d", name : "reqDttm", offsetLeft:20 , labelWidth:69}
						]}
					]}
				]},{type:"block",  list:[
					{type:"input", name:"email", label:"이메일주소", width:150, offsetLeft:10, maxLength:50,  labelWidth:70}
			   ]}
					, {type:"hidden", name:"searchGb"}
					, {type:"hidden", name:"contractNum"}
					, {type:"hidden", name:"customerId"}
					, {type:"hidden", name:"cstmrNm"}
					, {type:"hidden", name:"subscriberNo"}
					, {type:"hidden", name:"customerType"}
					, {type:"hidden", name:"userSsn"}
					, {type:"hidden", name:"subStatus"}
					, {type:"hidden", name:"pppo"}
					, {type:"hidden", name:"openDt"}
					, {type:"hidden", name:"tmntDt"}
					, {type:"hidden", name:"cstmrAddr"}
				]
			
				,buttons : {
					center : {
						btnSave  : {label:"저장"}
						, btnClose  : {label:"닫기"}
						, btnClear  : {label:"초기화"}
					}
				}
				,onValidateMore : function (data){
					var form = this;
				}
				,onChange : function(name, value) {//onChange START
					if(name == "invstType") {
						if(value == "10"){
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0112" , orderBy:"etc1"}, PAGE.FORM3, "appOdty"); // 결재권자직책
							PAGE.FORM3.enableItem("appOdty");
							PAGE.FORM3.disableItem("appOdtyNm");
						}else if (value == "20"){
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0111" , orderBy:"etc1"}, PAGE.FORM3, "appOdty"); // 결재권자직책
							PAGE.FORM3.enableItem("appOdty");
							PAGE.FORM3.disableItem("appOdtyNm");
						}else{
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0112" , orderBy:"etc1"}, PAGE.FORM3, "appOdty"); // 결재권자직책
							PAGE.FORM3.setItemValue("appOdty" , "00");
							PAGE.FORM3.disableItem("appOdty");
							PAGE.FORM3.enableItem("appOdtyNm");
						}
					}
					if(name == "appOdty"){
						if(value == "00"){
							PAGE.FORM3.enableItem("appOdtyNm");
						}else{
							PAGE.FORM3.disableItem("appOdtyNm");
							PAGE.FORM3.setItemValue("appOdtyNm" , "");
						}
					}				
				
				}

				,onButtonClick : function(btnId) {
					var form = this;
					switch(btnId){
						case "btnSave":
							var rowIds = PAGE.GRID2.getCheckedRows(0); // 선택된 checkbox 의 rowId 를 가져온다.
							if (! rowIds) {
								mvno.ui.alert("ECMN0002");
								return;
							}			
							
							var checkedData = PAGE.GRID2.classifyRowsFromIds(rowIds);
							
							if(PAGE.FORM2.getItemValue("btnChk") != "1"){ // 엑셀업로드가 아니면 FORM3 데이터 사용
								if(form.getItemValue("invstNm").trim() == "") {
									form.pushError("invstNm", "접수처", "접수처를 입력하세요");
									mvno.ui.alert("접수처를 입력하세요");
									return;
								}
								if(form.getItemValue("invstVal").trim() == "") {
									form.pushError("invstVal", "문서번호", "문서번호를 입력하세요");
									mvno.ui.alert("문서번호를 입력하세요");
									return;
								}
								if(form.getItemValue("reqOdty").trim() == "") {
									form.pushError("reqOdty", "요청자 직책", "요청자 직책을 입력하세요");
									mvno.ui.alert("요청자 직책을 입력하세요");
									return;
								}
								if(form.getItemValue("reqUser").trim() == "") {
									form.pushError("reqUser", "요청자 이름", "요청자 이름을 입력하세요");
									mvno.ui.alert("요청자 이름을 입력하세요");
									return;
								}
								
								if(form.getItemValue("appUser").trim() == "") {
									form.pushError("appUser", "결재권자 이름", "결재권자 이름을 입력하세요");
									mvno.ui.alert("결재권자 이름을 입력하세요");
									return;
								}
								if(form.getItemValue("appOdty").trim() == "") {
									form.pushError("appOdty", "결재권자 직책", "결재권자 직책을 입력하세요");
									mvno.ui.alert("결재권자 직책을 입력하세요");
									return;
								}
								if(form.getItemValue("appOdty").trim() == "00" && form.getItemValue("appOdtyNm").trim() == "") {
									form.pushError("appOdty", "결재권자 직책", "결재권자 직책을 입력하세요");
									mvno.ui.alert("결재권자 직책을 입력하세요");
									return;
								}
								
								for(var i=0; i<checkedData.ALL.length; i++){
									checkedData.ALL[i]["reqType"] = form.getItemValue("reqType");
									checkedData.ALL[i]["invstType"] = form.getItemValue("invstType");
									checkedData.ALL[i]["invstNm"] = form.getItemValue("invstNm");
									checkedData.ALL[i]["invstLoc"] = form.getItemValue("invstLoc");
									checkedData.ALL[i]["invstVal"] = form.getItemValue("invstVal");
									checkedData.ALL[i]["reqOdty"] = form.getItemValue("reqOdty");
									checkedData.ALL[i]["reqUser"] = form.getItemValue("reqUser");
									checkedData.ALL[i]["reqRsn"] = form.getItemValue("reqRsn");
									checkedData.ALL[i]["appUser"] = form.getItemValue("appUser");
									checkedData.ALL[i]["appOdty"] = form.getItemValue("appOdty");
									checkedData.ALL[i]["appOdtyNm"] = form.getItemValue("appOdtyNm");
									checkedData.ALL[i]["reqDttm"] = new Date(form.getItemValue("reqDttm")).format("yyyymmdd");
									checkedData.ALL[i]["email"] = form.getItemValue("email");
									
								}
							}
							
							var urlCheck = ROOT + "/rcp/idntmgmt/getBeforeSaveCheck.json";

							var url = ROOT + "/rcp/idntmgmt/savInvstReq.json";
// 							mvno.ui.confirm("CCMN0001", function() {
							mvno.cmn.ajax4json(urlCheck, checkedData, function(result) {
								
								
								if(result.result.count > 0 ){

									var msg = "동일한 문서번호와 수사기관 정보가 있습니다. 그래도 등록하시겠습니까?";
									mvno.ui.confirm(msg, function() {
										mvno.cmn.ajax4json(url, checkedData, function(result) {
											mvno.ui.notify("NCMN0001");
											PAGE.FORM2.clear();
											//초기화버튼으로 이동 이미경 대리님 요청사항 2020-09-24 
											//PAGE.FORM3.clear();
											//PAGE.UPLOAD.clear();
											PAGE.GRID2.clearAll();
											if(PAGE.FORM2.getItemValue("btnChk") != "1"){ 
												PAGE.FORM3.setItemValue("reqOdty", "수사관");
											}else{
												PAGE.FORM3.setItemValue("reqOdty", "");
											}
										});
									});
																		
								}else{
									mvno.cmn.ajax4json(url, checkedData, function(result) {
										mvno.ui.notify("NCMN0001");
										PAGE.FORM2.clear();
										//초기화버튼으로 이동 이미경 대리님 요청사항 2020-09-24 
										//PAGE.FORM3.clear();
										//PAGE.UPLOAD.clear();
										PAGE.GRID2.clearAll();
										if(PAGE.FORM2.getItemValue("btnChk") != "1"){ 
											PAGE.FORM3.setItemValue("reqOdty", "수사관");
										}else{
											PAGE.FORM3.setItemValue("reqOdty", "");
										}
									});
									
								}
							});
// 							});
							break;
						case "btnClose":
							mvno.ui.closeWindowById("GROUP1");
							break;
						case "btnClear":
							PAGE.FORM3.clear();
							PAGE.UPLOAD.clear();
							if(PAGE.FORM2.getItemValue("btnChk") != "1"){ 
								PAGE.FORM3.setItemValue("reqOdty", "수사관");
							}else{
								PAGE.FORM3.setItemValue("reqOdty", "");
							}
							break;
							
					}
				}
				
			}
			
	};

	var PAGE = {
			title: "${breadCrumb.title}",
			breadcrumb: "${breadCrumb.breadCrumb}",
		    buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				
				var procDateE = new Date().format('yyyymmdd');
				var time = new Date().getTime();
				time = time - 1000 * 3600 * 24 * 7;
				var procDateS = new Date(time);
		
				PAGE.FORM1.setItemValue("procDateS", procDateS);
				PAGE.FORM1.setItemValue("procDateE", procDateE);
				
 				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0022",orderBy:"etc2"}, PAGE.FORM1, "searchGb"); // 검색구분
 				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0107"}, PAGE.FORM1, "invstType"); // 요청기관
 				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0108"}, PAGE.FORM1, "reqType"); // 요청종류
// 				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9020"}, PAGE.FORM1, "invstLoc"); // 지역
			}
			
	};
    
</script>
