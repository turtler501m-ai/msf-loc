<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_prmt_1005.jsp
	 * @Description : 프로모션 관리
	 * @author : 김동혁
	 * @Create Date : 2023.10.05 
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search" ></div>
<!-- 그리드영역 -->
<div id="GRID1"></div>

<div id="POPUP1">
	<div id="POPUP1TOP"  class="section-full"></div>
	<div id="POPUP1MID" class="section-box"></div>
</div>

<div id="GROUP1" style="display: none;">
	<div id="FORM10" class="section-search"></div>
	<div class="row">
		<div id="GRID11"></div>
	</div>
</div>

<div id="FORM20" class="section-search"></div>

<!-- Script -->
<script type="text/javascript">
	var today = new Date().format("yyyymmdd");
	var allChkYN = true;
	var endDt = mvno.cmn.getAddDay(today, 30);
	var fileName;
	
	var DHX = {
		// 검색
		FORM1 : { 
			items : [ 
				{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 70, blockOffset : 0 }
				,{ type : "block", list : [
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchBaseDate', label: '기준일자', calendarPosition: 'bottom' ,width:100, offsetLeft:10 }
					, {type: "newcolumn", offset:30}
					, {type:"input", width:345, label:"프로모션명", name: "searchPrmtNm", offsetLeft:20}
				]}
				,{ type: "block", list : [
					{type:"select", width:100, label:"채널유형", name:"searchChnlTp", offsetLeft:10}
					, {type: "newcolumn", offset:30}
					, {type:"select", width:100, label:"업무구분", name:"searchEvntCd", offsetLeft:20}
					, {type: "newcolumn", offset:30}
					, {type:"select", width:120, label:"가입유형", name:"searchSlsTp", offsetLeft:20}
				]}
				,{ type: "block", list : [
					{type:"select", width:70, name:"orgnData", offsetLeft:0 ,options:[{value:"orgnNm",text:"조직명"},{value:"orgnId",text:"조직ID"}]},
					{type:"newcolumn"},
					{type:"input", width:100, name:"orgnVal" },
					{type:"newcolumn"},
					{type:"select", width:80, name:"pRateData", offsetLeft:45 ,options:[{value:"pRateNm",text:"요금제명"},{value:"pRateCd",text:"요금제ID"}]},
					{type:"newcolumn"},
					{type:"input", width:100, name:"pRateVal"},
					{type:"newcolumn"},
					{type:"select", width:100, name:"rRateData", offsetLeft:35 ,options:[{value:"rRateNm",text:"부가서비스명"},{value:"rRateCd",text:"부가상품ID"}]},
					{type:"newcolumn"},
					{type:"input", width:100, name:"rRateVal"},
					{type:"newcolumn"}
				]}
				,{ type : 'hidden', name: "DWNLD_RSN", value:""}
				,{ type : 'hidden', name: "btnCount1", value:"0"}
				,{ type : "button",name : "btnSearch",value : "조회", className:"btn-search2" } 
			]
			, onValidateMore : function(data){
				if(data.searchBaseDate == null || data.searchBaseDate == "") {
					this.pushError("searchBaseDate","기준일자","기준일자를 입력해주세요.");
					return;
				}
			}
			, onButtonClick : function(btnId) {
				
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
												
						var url1 = ROOT + "/org/prmtMgmt/getDisPrmtList.json";
						
						PAGE.GRID1.list(url1, form);
						
					break;
				}
			}
		}//FORM1 End
		, GRID1 : {
			css : { 
				height : "520px"  
			}
			,title : "프로모션목록"
			,header : "선택,프로모션ID,프로모션명,시작일시,종료일시,채널유형,업무구분,가입유형,단말모델ID,단말모델명,약정기간_무약정,약정기간_12개월,약정기간_18개월,약정기간_24개월,약정기간_30개월,약정기간_36개월,등록자,등록일시,수정자,수정일시" //14
			,columnIds :"rowCheck,prmtId,prmtNm,strtDttm,endDttm,chnlNm,evntNm,slsNm,modelId,modelNm,enggCnt0,enggCnt12,enggCnt18,enggCnt24,enggCnt30,enggCnt36,regstNm,regstDttm,rvisnNm,rvisnDttm"//14
			,widths :"50,130,250,140,140,80,80,120,120,110,110,110,110,110,110,110,80,140,80,140"
			,colAlign :"Center,Center,Left,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center,Center"
			,colTypes :"ch,ro,ro,roXdt,roXdt,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXdt,ro,roXdt"
			,colSorting :"str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,strstr,str,str,str"
			//,hiddenColumns : "5"
			,paging : true
			,pageSize : 20
// 			,multiCheckbox : true
			//,showPagingAreaOnInit : true
			,buttons : {
				hright : {
					btnUpExcel: { label : "엑셀업로드"}
					,btnDownExcel : { label : "자료생성"}
					,btnPrmtExcel : { label : "프로모션엑셀다운"}
				},
				left : {
					btnAllChk : {label : "전체선택" }
					,btnAddAgncy : { label : "대리점추가" }
					,btnEndDt : { label : "종료일시변경" }
					,btnTempPrmt : { label : "프로모션일괄등록양식" }
				},
				right : {
					btnReg : { label : "등록" }
					,btnChrgPrmtCopy : { label : "프로모션복사" }
					,btnMod : { label : "수정" }
				}
			}
			// 그리드 행 선택시 checkbox도 선택되도록
			,onRowSelect : function(rowId){
				var cell = this.cells(rowId,0);
				if(!cell.isChecked()) cell.setValue(1);
				else cell.setValue(0);
			}
			,onRowDblClicked : function(rowId, celInd) {
				// 수정버튼 누른것과 같이 처리
				this.callEvent("onButtonClick", ["btnMod2"]);
			}
			,onButtonClick : function(btnId, selectedData) {
				
			 	var grid = this;         
			 	var chkData = grid.getCheckedRowData();
			 	
				 switch (btnId) {
					case "btnDownExcel":
						
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}

						mvno.cmn.downloadCallback(function(result) {
							PAGE.FORM1.setItemValue("DWNLD_RSN",result);
							mvno.cmn.ajax(ROOT + "/org/prmtmgmt/getDisPrmtListExcelDown.json?menuId=MSP2002510", PAGE.FORM1.getFormData(true), function(result){
								mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
							});
						});
						
					break;
														
					case "btnUpExcel":
						
						mvno.ui.createGrid("POPUP1TOP");
						mvno.ui.createForm("POPUP1MID");
						
						PAGE.POPUP1TOP.clearAll();
						PAGE.POPUP1MID.clear();
						PAGE.POPUP1MID.setFormData({});
												
						PAGE.POPUP1MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP1TOP.clearAll();
							
							return true;
						});
						
						var uploader = PAGE.POPUP1MID.getUploader("file_upload1");
						uploader.revive();
						
						mvno.ui.popupWindowById("POPUP1", "프로모션 일괄등록", 950, 700, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
						
					break;

					case "btnPrmtExcel" :

						if(chkData.length < 1 ){
							mvno.ui.alert("ECMN0003");
							return;
						}

						var prmtIdList = [];
						var searchData =  PAGE.FORM1.getFormData(true);

						for(var i = 0; i < chkData.length; i++){
							var chkDataPrmtId = chkData[i].prmtId;
							prmtIdList.push(chkDataPrmtId);
						};

						var sData = {
								prmtIdList : prmtIdList
						};

						mvno.cmn.download('/org/prmtmgmt/getDisChoChrgPrmtListExcelDown.json', false, {postData:sData});
						
						break;
					
					case "btnAllChk" :
						
						this.forEachRow(function(id){
							
							var cell = this.cells(id,0);
							
							if (cell.isCheckbox()){
								if(allChkYN){
									if(!cell.isChecked()) cell.setValue(1);
										
								}else{
									if(cell.isChecked()) cell.setValue(0);
								}
							}
						});
						
						allChkYN = !allChkYN;
						
						break;
					
					case "btnAddAgncy" :
						if(chkData.length < 1 ){
							mvno.ui.alert("ECMN0003");
							return;
						}
						
						mvno.ui.createForm("FORM10");
						mvno.ui.createGrid("GRID11");
						
						PAGE.GRID11.list(ROOT + "/org/prmtmgmt/getDisPrmtOrgnList.json");
						
						mvno.ui.popupWindowById("GROUP1", "대리점추가", 578, 500, {
								onClose: function(win) {
									if (!PAGE.FORM10.isChanged() || !PAGE.GRID11.isChanged()){
										return true;
									}
									mvno.ui.confirm("CCMN0005", function() {
										win.closeForce();
									});
								}
						});

						PAGE.FORM10.clear();
						PAGE.GRID11.clearAll();
						
					break;
					
					
					case "btnEndDt" :
						
						if(chkData.length < 1 ){
							mvno.ui.alert("ECMN0003");
							return;
						}

						mvno.ui.createForm("FORM20");
						
						PAGE.FORM20.setItemValue("endDtMod", today);
						PAGE.FORM20.setItemValue("endTmMod", "235959");
						
						mvno.ui.popupWindowById("FORM20", "종료일시변경", 450, 150, {
							onClose: function(win) {
								if (!PAGE.FORM20.isChanged()) {
									return true;
								}
								mvno.ui.confirm("CCMN0005", function() {
									win.closeForce();
								});
							}
						});
			
					break;
						
					case "btnTempPrmt":
						mvno.cmn.download("/org/prmtmgmt/getDisTempExcelDown.json");
						
					break;
				
					case "btnReg" :
						
						var url = "/org/prmtMgmt/getDisPrmtNew.do";
												
						var myTabbar = window.parent.mainTabbar;
						
						if (myTabbar.tabs(url)) {
							myTabbar.tabs(url).setActive();
						}
						else{
							myTabbar.addTab(url, "프로모션관리등록", null, null, true);
						}
						
						myTabbar.tabs(url).attachURL(url+"?menuId=MSP2002610");
						
					break; 	
						
					case "btnChrgPrmtCopy" :
						
						if(chkData.length < 1 ){
							mvno.ui.alert("ECMN0003");
							return;
						}
						if(chkData.length > 1 ){
							mvno.ui.alert("프로모션을 1개만 선택(체크)해주세요");
							return;
						}
						mvno.ui.confirm("선택(체크)한 프로모션을 복사 하시겠습니까?",function() {
							var url = "/org/prmtMgmt/getDisPrmtNew.do";
						
							var cate="COPY";
							
							var param = "?menuId=MSP2002610&prmtId=" + chkData[0].prmtId + "&cate=" +cate;
							
							var myTabbar = window.parent.mainTabbar;
							
							if (myTabbar.tabs(url)) {
								myTabbar.tabs(url).setActive();
							}else{
								myTabbar.addTab(url, "프로모션관리등록", null, null, true);
							}
							
							myTabbar.tabs(url).attachURL(url + param);
						});
						
					break; 	
						
					case "btnMod" :
						
						if(chkData.length < 1 ){
							mvno.ui.alert("ECMN0003");
							return;
						}
						if(chkData.length > 1 ){
							mvno.ui.alert("프로모션을 1개만 선택(체크)해주세요");
							return;
						}

						mvno.ui.confirm("선택(체크)한 프로모션을 수정 하시겠습니까?",function() {	
							var url = "/org/prmtMgmt/getDisPrmtNew.do";
						
							var cate="MOD";
							
							var param = "?menuId=MSP2002610&prmtId=" + chkData[0].prmtId + "&cate=" +cate;
							
							var myTabbar = window.parent.mainTabbar;
							
							if (myTabbar.tabs(url)) {
								myTabbar.tabs(url).setActive();
							}else{
								myTabbar.addTab(url, "프로모션관리등록", null, null, true);
							}
							
							myTabbar.tabs(url).attachURL(url + param);
						});
						
					break;	
						
					case "btnMod2" :
						if(mvno.cmn.isEmpty(grid.getSelectedRowData())){
							mvno.ui.alert("선택된 데이터가 없습니다.");
							return;
						}
						
						var url = "/org/prmtMgmt/getDisPrmtNew.do";
						var sData = grid.getSelectedRowData();
						var cate="MOD";
						
						var param = "?menuId=MSP2002610&prmtId=" + sData.prmtId + "&cate=" +cate;
						
						var myTabbar = window.parent.mainTabbar;
						
						if (myTabbar.tabs(url)) {
							myTabbar.tabs(url).setActive();
						}else{
							myTabbar.addTab(url, "프로모션관리등록", null, null, true);
						}
						myTabbar.tabs(url).attachURL(url + param);		
								
					break; 	
				}
			}
		}//GRID1 End	
		, POPUP1TOP : {
			 css : { height : "465px" }
			,title : "프로모션등록상세"
			,header : "번호,프로모션명,시작일자,시작시간,종료일자,종료시간,채널유형,업무구분,가입유형,개통단말기,대표모델ID,약정기간_무약정,약정기간_12개월,약정기간_18개월,약정기간_24개월,약정기간_30개월,약정기간_36개월,대상조직,대상요금제,대상부가서비스" // 18
			,columnIds : "regNum,prmtNm,strtDt,strtTm,endDt,endTm,chnlTp,evntCd,slsTp,intmYn,modelId,enggCnt0,enggCnt12,enggCnt18,enggCnt24,enggCnt30,enggCnt36,orgnId,rateCd,soc" // 18
			,widths : "50,150,100,100,100,100,80,110,70,70,100,120,120,120,120,120,120,200,200,200"
			,colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center"
			,colTypes : "ron,ro,roXd,roXt,roXd,roXt,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
		}	//POPUP1TOP End
		
		, POPUP1MID : {
			 title : "엑셀업로드"
			,items : [
				 { type : "block"
				  ,list : [
					 { type : "newcolumn", offset : 20 }
					,{ type : "upload" 
					  ,label : "프로모션 일괄등록 엑셀파일"
					  ,name : "file_upload1"
					  ,inputWidth : 830
					  ,url : "/rcp/dlvyMgmt/regParExcelUp.do"
					  ,userdata : { limitSize:10000 } 
					  ,autoStart : true } 
				]},
				{type:"input", width:100, name : "rowData", hidden:true}
			]  
			
			,buttons : {
				center : {
					btnSave : {label : "저장" },	
					btnClose : { label : "닫기" }
				}
			}
			,onUploadFile : function(realName, serverName) {
				fileName = serverName;
			}
			,onUploadComplete : function(count){
				
				var url = ROOT + "/org/prmtmgmt/readDisExcelUpList.json";
				var userOptions = {timeout:180000};
				
				mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
					var rData = resultData.result;
					
					PAGE.POPUP1TOP.clearAll();
					
					PAGE.POPUP1TOP.parse(rData.data.rows, "js");

					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				}, userOptions);
			}
			,onButtonClick : function(btnId) {
				
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				
				switch (btnId) {					
					
					case "btnSave" :
						
						if(PAGE.POPUP1TOP.getRowsNum() == 0) {
							mvno.ui.alert("파일을 첨부해 주세요.");
							break;
						}

						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)

						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") {
							return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기
						}

						var arrObj = [];
						
						PAGE.POPUP1TOP.forEachRow(function(id){
							var arrData = PAGE.POPUP1TOP.getRowData(id);
							arrObj.push(arrData);
						});
								
						var sData = { items : arrObj };

						var userOptions = {timeout:180000
											, errorCallback : function(result){
												PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
											}
										};

						mvno.cmn.ajax4json(ROOT + "/org/prmtmgmt/regDisPrmtInfoExcel.json", sData, function(result) {
							mvno.ui.alert(result.result.msg);
							PAGE.GRID1.refresh();
							mvno.ui.closeWindowById("POPUP1");
						}, userOptions);
						
					break;
						
					case "btnClose":
						mvno.ui.closeWindowById("POPUP1");
						
					break;
				}
			}
		}
		,FORM10 : { 
			items : [
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},	
						{type:"block", list:[
							{type : "input",label : "조직명",name : "orgnNm", width:100, offsetLeft:10}, 
						]},
						{type:"newcolumn", offset:0},
						{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],
			onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
				
				case "btnSearch":
						  						
					  PAGE.GRID11.list(ROOT + "/org/prmtmgmt/getDisPrmtOrgnList.json", form);
						
				break;
				}
			}
		},
		GRID11 : {
			css : {
				height : "300px",
				width : "520px"
			},
			title : "조회결과",
			header : "선택,조직ID,조직명,조직유형,조직상태",
			columnIds : "rowCheck,orgnId,orgnNm,typeNm,statNm",
			colAlign : "center,left,left,left,center",
			colTypes : "ch,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str",
			widths : "50,100,170,100,80",
              buttons : {
				  center : {
				   btnApply : { label : "확인" }
				   ,btnClose : { label: "닫기" }
				  }
              }
			  ,onRowSelect : function(rowId){
				var cell = this.cells(rowId,0);
				if(!cell.isChecked()) cell.setValue(1);
				else cell.setValue(0);
			  }
              ,onButtonClick : function(btnId) {
                  
            	  var grid = this;    
            	  var chkData=grid.getCheckedRowData();
           
                  switch (btnId) {
                
                  case "btnApply":  	 
                	
                 	    if(chkData.length == 0){
  	      					mvno.ui.alert("ECMN0003");
  	      					return;
  	      			    }
                 	    
                 	    var prmtIdList =[];
					    var now = new Date().format("yyyymmddhhmiss");

                 	  	for(var i = 0; i < PAGE.GRID1.getCheckedRowData().length; i++){
                 	  		var endDttm =  PAGE.GRID1.getCheckedRowData()[i].endDttm;
                 	  		if(endDttm < now){
    							mvno.ui.alert( "프로모션 ID ["+  PAGE.GRID1.getCheckedRowData()[i].prmtId +"] 은 이미 종료된 프로모션으로 대리점을 추가 할 수 없습니다.");
    							return;
    						}
                 	  		prmtIdList.push(PAGE.GRID1.getCheckedRowData()[i].prmtId);
                 	  	}

                 	    var orgnList = [];
                 	    
                 	  	for(var j = 0; j < chkData.length; j++) {
                 	  		orgnList.push(chkData[j].orgnId);
						}
                	  	var sData = {
                	  			prmtIdList : prmtIdList
                	  			,orgnList : orgnList
						};	
						
						mvno.ui.confirm("선택한 프로모션의 대리점을 추가 하시겠습니까?",function() {
	                		mvno.cmn.ajax4json(ROOT + "/org/prmtMgmt/setDisPrmtOrgAdd.json", sData, function(result) {
								if(result.result.code == "OK"){
									mvno.ui.notify("NCMN0006");
									mvno.ui.alert("NCMN0006");
									mvno.ui.closeWindowById("GROUP1");
								};
							});
						});
	                	
            	  break; 
                  
                  case "btnClose" :
                         
                   	    mvno.ui.closeWindowById("GROUP1");
                   	  
                  break;  
            	}
      		}
		}
		,FORM20 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},	
				{type:"block", list:[
				,{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'endDtMod', label: '종료일자', calendarPosition: 'bottom', readonly:true ,width:100} 
				,{type:"newcolumn", offset:10}
				,{type:"input", name: "endTmMod", width: 50, validate:"ValidInteger", maxLength:6}
				]},
				, {type:"newcolumn", offset:20}
				, {type :"button",name : "btnEndDtMod",value : "종료일시변경" } 
			]
			,onButtonClick : function(btnId) {
				var chkData = PAGE.GRID1.getCheckedRowData();
				
				switch (btnId) {
					case "btnEndDtMod":

						// 입력 날짜 유효성 검사
						var endDtMod = PAGE.FORM20.getItemValue("endDtMod");
						var endTmMod = PAGE.FORM20.getItemValue("endTmMod");

						if(endTmMod.length < 6){
							mvno.ui.alert("변경종료 시간은 6자리로 입력해주세요.");
							return;
						}

						if(endDtMod == null || endDtMod== ''){
							mvno.ui.alert("종료일자를 입력해주세요");
							return;
						}
										
						if(endTmMod == null || endTmMod== '' || endTmMod.length < 1){
							mvno.ui.alert("종료시간(시분초)을 입력해주세요");
							return;
						}
						
						var endDttmMod = endDtMod.format("yyyymmdd")+endTmMod;
						if(Number(endTmMod) < 0 || Number(endTmMod) > 235959) {
							mvno.ui.alert("정확한 종료시간(시분초)을 입력해주세요");
							return;
						}

						var prmtIdList = [];
						var now = new Date().format("yyyymmddhhmiss");

						for(var i = 0; i < chkData.length; i++){
							var strtDttm = chkData[i].strtDttm;
							var endDttm = chkData[i].endDttm;
							var chkDataPrmtId = chkData[i].prmtId;

							if(endDttm < now){
								mvno.ui.alert( "프로모션 ID ["+ chkDataPrmtId +"] 은 이미 종료된 프로모션으로 종료일을 변경할수 없습니다.");
								return;
							}
																						
							if(strtDttm > endDttmMod) {
								mvno.ui.alert( "프로모션 ID ["+ chkDataPrmtId +"] 의 종료일시 [" + endDttmMod + "] 가 시작일시["  + strtDttm + "] 전입니다. 종료일시을 변경하세요.");
								return;
							}
							if(endDttmMod < now) {
								mvno.ui.alert( "프로모션 ID ["+ chkDataPrmtId +"] 의 종료일시 [" + endDttmMod + "] 를 현재 ["  + now + "] 이전으로 변경 할 수 없습니다.");
								return;
							}
							
							prmtIdList.push(chkDataPrmtId);
						};

						var sData = {
								endDttmMod : endDttmMod
								,prmtIdList : prmtIdList
						};

						mvno.ui.confirm("선택한 프로모션의 종료일시를 변경 하시겠습니까?",function() {
							mvno.cmn.ajax4json(ROOT + "/org/prmtmgmt/updDisPrmtEndDttm.json", sData, function(result) {
								if(result.result.code == "OK"){
									PAGE.GRID1.refresh();
									mvno.ui.notify("NCMN0002");
									mvno.ui.alert("NCMN0002");
									mvno.ui.closeWindowById("FORM20");
								}
							});
						})
				   break;
				}
			}
		}
	}
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		buttonAuth: ${buttonAuth.jsonString},
		onOpen:function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			PAGE.FORM1.setItemValue("searchBaseDate", today);
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0090", etc3:"Y"}, PAGE.FORM1, "searchChnlTp");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0091", orderBy:"etc3"}, PAGE.FORM1, "searchEvntCd");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0093"}, PAGE.FORM1, "searchSlsTp");
		}
	};
</script>