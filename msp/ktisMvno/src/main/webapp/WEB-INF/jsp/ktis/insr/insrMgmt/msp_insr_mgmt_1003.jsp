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
					, {type:"select", label:"검증상태", name: "vrfyRsltCd", width:110, offsetLeft:15}
					, {type:"newcolumn"}
					, {type:"select", label:"보험상품", name: "insrProdCd", width:250, offsetLeft:15}
				]}
				, {type:"block", list:[
					{type:"select", label:"검색구분", name: "searchCd", width:100, offsetLeft:10, labelWidth:86}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchVal", width:115, offsetLeft:0, maxLength:60}
					, {type:"newcolumn"}
					, {type:"select", label:"이미지등록여부", name:"imgChkYn", width:70, offsetLeft:15, labelWidth:100}
					, {type:"newcolumn"}
					, {type:"select", label:"처리상태", name:"ifTrgtCd", width:70, offsetLeft:15}
					, {type:"newcolumn"}
					, {type:"select", label:"가입경로", name:"chnCd", width:100, offsetLeft:15}
				]}
				, {type:"block", list:[
					{type:"select", label:"구매유형", name: "reqBuyType", width:100, offsetLeft:10, labelWidth:86}
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
				height : "510px"
			}
			, title : "보험신청목록"
			, header : "전화번호,고객명,계약번호,개통대리점,보험상품명,신청일자,가입일자,종료일자,처리상태,가입경로,구매유형,업무구분,상품구분,단말모델명,단말일련번호,이미지등록여부,서식지변경여부,검증결과,1차검증처리자,1차검증일자,2차검증처리자,2차검증일자,메모,insrProdCd,insrStatCd,modelId,reqBuyType,operType,prodType,vrfyRsltCd,scanId,resNo,onOffType,ifTrgtCd,insrTypeCd"
			, columnIds : "subscriberNo,subLinkName,contractNum,openAgntNm,insrProdNm,reqinday,strtDttm,endDttm,ifTrgtNm,chnNm,reqBuyTypeNm,operTypeNm,prodTypeNm,modelNm,intmSrlNo,imgChkYn,scanMdyYn,vrfyRsltNm,fstVrfyId,fstVrfyDttm,sndVrfyId,sndVrfyDttm,rmk,insrProdCd,insrStatCd,modelId,reqBuyType,operType,prodType,vrfyRsltCd,scanId,resNo,chnCd,ifTrgtCd,insrTypeCd"
			, widths : "120,100,100,130,180,130,130,130,80,120,100,100,100,100,100,100,100,100,100,130,100,130,500,100,100,100,100,100,100,100,100,100,100,100,100"
			, colAlign : "center,center,center,Left,Left,center,center,center,center,center,center,center,center,Left,center,center,center,center,center,center,center,center,center,center,Left,center,center,center,center,center,center,center,center,center,center"
			, colTypes : "roXp,ro,ro,ro,ro,roXdt,roXdt,roXdt,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXdt,ro,roXdt,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, hiddenColumns : [23,24,25,26,27,28,29,30,31,32,33,34]
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
					btnUsim : { label : "단말정보등록" }
					, btnVrfy : { label : "검증결과등록" }
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
						
						mvno.cmn.download(ROOT + "/insr/insrMgmt/getInsrOrderListByExcel.json?menuId=MSP9003001", true, {postData:searchData}); 
						
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
						
					case "btnUsim":
						
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
						if(grid.getSelectedRowData().insrTypeCd != "02"){
							mvno.ui.alert("해당 상품 고객은 단말등록이 불가능합니다.");
							return;
						}
						
						if(grid.getSelectedRowData().ifTrgtCd != "N" && grid.getSelectedRowData().ifTrgtCd != "Y"){
							mvno.ui.alert("처리상태가 대기 또는 대상인 고객만 가능합니다.");
							return;
						}
						
						var chkDt =  mvno.cmn.getAddDay(grid.getSelectedRowData().strtDttm.substring(0,8), 6);
						
						if(chkDt < toDt){
							mvno.ui.alert("가입일로 부터 7일 이내만 단말등록이 가능 합니다.");
							return;
						}
						
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.clear();
						
						PAGE.FORM2.setItemValue("chnCd", grid.getSelectedRowData().chnCd);
						PAGE.FORM2.setItemValue("contractNum", grid.getSelectedRowData().contractNum);
						PAGE.FORM2.setItemValue("insrProdCd", grid.getSelectedRowData().insrProdCd);
						PAGE.FORM2.setItemValue("strtDttm", grid.getSelectedRowData().strtDttm);
						
						if(grid.getSelectedRowData().modelId == "" && grid.getSelectedRowData().modelNm != "") {
							PAGE.FORM2.disableItem("modelId");
							PAGE.FORM2.disableItem("prdtNm");
							PAGE.FORM2.disableItem("btnPrdtFind");
							PAGE.FORM2.setReadonly("modelNm", false);
							PAGE.FORM2.checkItem("direct");
						} else {
							PAGE.FORM2.enableItem("modelId");
                        	PAGE.FORM2.enableItem("prdtNm");
                        	PAGE.FORM2.enableItem("btnPrdtFind");
                        	PAGE.FORM2.setReadonly("modelNm", true);
                        	PAGE.FORM2.uncheckItem("direct");
						}
						
						PAGE.FORM2.setItemValue("modelId", grid.getSelectedRowData().modelId);
						PAGE.FORM2.setItemValue("modelNm", grid.getSelectedRowData().modelNm);
						PAGE.FORM2.setItemValue("prdtNm", grid.getSelectedRowData().prdtNm);
						PAGE.FORM2.setItemValue("intmSrlNo", grid.getSelectedRowData().intmSrlNo);
						PAGE.FORM2.setItemValue("vrfyRsltCd", grid.getSelectedRowData().vrfyRsltCd);
						PAGE.FORM2.setItemValue("imgChkYn", grid.getSelectedRowData().imgChkYn);
						PAGE.FORM2.setItemValue("rmk", grid.getSelectedRowData().rmk);
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("FORM2", "단말정보등록", 550, 330, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						
						break;
						
					case "btnVrfy":
						
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
						if(grid.getSelectedRowData().ifTrgtCd != "N" && grid.getSelectedRowData().ifTrgtCd != "Y"){
							mvno.ui.alert("처리상태가 대기 또는 대상인 고객만 가능합니다.");
							return;
						}
						
						if(grid.getSelectedRowData().vrfyRsltCd == "SNOK" || grid.getSelectedRowData().vrfyRsltCd == "SOK" || grid.getSelectedRowData().vrfyRsltCd == "FOK"){
							mvno.ui.alert("검증이 완료된 고객 입니다.<br>검증 처리를 할 수 없습니다.");
							return;
						}
						
						var chkDt =  mvno.cmn.getAddDay(grid.getSelectedRowData().strtDttm.substring(0,8), 6);
						
						if(chkDt < toDt){
							mvno.ui.alert("가입일로 부터 7일 이내만 검증등록이 가능 합니다.");
							return;
						}
						
						mvno.ui.createForm("FORM3");
						
						PAGE.FORM3.clear();
						
						PAGE.FORM3.setItemValue("contractNum", grid.getSelectedRowData().contractNum);
						PAGE.FORM3.setItemValue("insrProdCd", grid.getSelectedRowData().insrProdCd);
						PAGE.FORM3.setItemValue("strtDttm", grid.getSelectedRowData().strtDttm);
						PAGE.FORM3.setItemValue("vrfyRsltCd", grid.getSelectedRowData().vrfyRsltCd);
						PAGE.FORM3.setItemValue("fstVrfyCd", grid.getSelectedRowData().vrfyRsltCd);
						PAGE.FORM3.setItemValue("fstVrfyId", grid.getSelectedRowData().fstVrfyId);
						PAGE.FORM3.setItemValue("fstVrfyDttm", grid.getSelectedRowData().fstVrfyDttm);
						PAGE.FORM3.setItemValue("sndVrfyCd", grid.getSelectedRowData().vrfyRsltCd);
						PAGE.FORM3.setItemValue("sndVrfyId", grid.getSelectedRowData().sndVrfyId);
						PAGE.FORM3.setItemValue("sndVrfyDttm", grid.getSelectedRowData().sndVrfyDttm);
						PAGE.FORM3.setItemValue("rmk", grid.getSelectedRowData().rmk);
						
						PAGE.FORM3.setItemValue("insrTypeCd", grid.getSelectedRowData().insrTypeCd);
						PAGE.FORM3.setItemValue("imgChkYn", grid.getSelectedRowData().imgChkYn);
						
						if(grid.getSelectedRowData().vrfyRsltCd == "REG"){
							PAGE.FORM3.enableItem("FSTVF");
							PAGE.FORM3.disableItem("SNDVF");
						} else if(grid.getSelectedRowData().vrfyRsltCd == "FNOK" || grid.getSelectedRowData().vrfyRsltCd == "FOK"){
							PAGE.FORM3.disableItem("FSTVF");
							PAGE.FORM3.enableItem("SNDVF");
						} else {
							PAGE.FORM3.disableItem("FSTVF");
							PAGE.FORM3.disableItem("SNDVF");
						}
						
						PAGE.FORM3.clearChanged();
						
						mvno.ui.popupWindowById("FORM3", "검증결과등록", 700, 400, {
							onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						
						break;
						
				}
			}
		}
		
		, FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"input", label:"모델ID", name:"modelId", width:80, value:'', offsetLeft:10, labelWidth:40, maxLength:10}
					, {type:"newcolumn"}
					, {type: "button", name:"btnPrdtFind", value:"검색"}
					, {type:"newcolumn"}
					, {type:"input", name:"prdtNm", value:"", width:90, readonly: true}
					, {type:"newcolumn"}
					, {type: 'checkbox', name:"direct", label: "직접입력", checked: false, offsetLeft:10, labelWidth:50}
					, {type:"newcolumn"}
					, {type:"input", name:"modelNm", value:"", width:100, readonly: true}
				]}
				, {type:"block", list:[
					{type:"input", label:"단말일련번호(IMEI)", name:"intmSrlNo", width:130, offsetLeft:10, labelWidth:120, maxLength:30}
					, {type:"newcolumn"}
					, {type:"select", label:"이미지등록여부", name:"imgChkYn", width:80, offsetLeft:15, labelWidth:90, options:[{text: "Y", value: "Y"},{text: "N", value: "N"}]}
				]}
				,{type: "fieldset", label: "메모", name:"MEMO", inputWidth:450, list:[
                  {type:"block", list:[
                  	{type:"input", label:"", name:"rmk", width:435, rows:5, maxLength:2000}
                  ]}
                ]}
				, {type: "hidden", name: "chnCd"}
				, {type: "hidden", name: "contractNum"}
				, {type: "hidden", name: "insrProdCd"}
				, {type: "hidden", name: "strtDttm"}
				, {type: "hidden", name: "vrfyRsltCd"}
			]
			, buttons : {
				left : {
					btnCancel : { label : "등록취소" }
				}
				, center : {
					btnSave : { label : "저장" }
					, btnClose : { label : "닫기" }
				}
			}
			, onChange: function (name, value) {
				switch(name) {
					case "modelId":
						if(value.length >= 4){
							mvno.cmn.ajax(ROOT + "/cmn/intmmdl/selectRprsPrdtNm.json", {rprsPrdtId:value, rprsYn:"Y"}, function(resultData) {
								if(resultData.result.resultCnt > 0){
									PAGE.FORM2.setItemValue("prdtNm", resultData.result.resultList[0].prdtNm);
									PAGE.FORM2.setItemValue("modelNm", resultData.result.resultList[0].prdtCode);
								}else{
									PAGE.FORM2.setItemValue("prdtNm", "");
									PAGE.FORM2.setItemValue("modelNm", "");
								}
							});
						} else {
							PAGE.FORM2.setItemValue("prdtNm", "");
							PAGE.FORM2.setItemValue("modelNm", "");
						}
						
						break;
					case "direct":
						PAGE.FORM2.setItemValue("modelId", "");
						PAGE.FORM2.setItemValue("prdtNm", "");
						PAGE.FORM2.setItemValue("modelNm", "");
						
						if(this.isItemChecked(name)) {
							PAGE.FORM2.disableItem("modelId");
							PAGE.FORM2.disableItem("prdtNm");
							PAGE.FORM2.disableItem("btnPrdtFind");
							PAGE.FORM2.setReadonly("modelNm", false);
                        }else{
                        	PAGE.FORM2.enableItem("modelId");
                        	PAGE.FORM2.enableItem("prdtNm");
                        	PAGE.FORM2.enableItem("btnPrdtFind");
                        	PAGE.FORM2.setReadonly("modelNm", true);
                        }
						
						break;
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					
					case "btnCancel":
						
						/* 2019.05.30, 정현주
						기존에는 imgChkYn으로 단말 등록 여부를 체크했으나 imgChkYn의 값에 N을 넣을수 있도록 개선됨에 따라
						단말등록여부를 modelId로 체크하도록 개선 적용 
						2020.10.30 직접등록 기능 추가로 modelNm으로 체크 */
						if(mvno.cmn.isEmpty(form.getItemValue("modelNm"))){
							mvno.ui.alert("등록된 단말정보가 없습니다.");
							return;
						}
						
						var sData = {
								modelId : ""
								, modelNm : ""
								, intmSrlNo : ""
								, imgChkYn : "N"
								, chnCd : form.getItemValue("chnCd")
								, contractNum : form.getItemValue("contractNum")
								, insrProdCd : form.getItemValue("insrProdCd")
								, strtDttm : form.getItemValue("strtDttm")
								, vrfyRsltCd : form.getItemValue("vrfyRsltCd")
						};
						
						mvno.ui.confirm("취소하시겠습니까?", function() {
							mvno.cmn.ajax(ROOT + "/insr/insrMgmt/regIntmInfoByUsimCust.json", sData, function(){
								mvno.ui.notify("NCMN0004");
								PAGE.GRID1.refresh();
								mvno.ui.closeWindowById("FORM2");
							});
						});
						
					break;
					
					case "btnPrdtFind":
						mvno.ui.codefinder("RPRSMLDSET", function(result) {
							form.setItemValue("modelId", result.rprsPrdtId);
							form.setItemValue("prdtNm", result.prdtNm);
							form.setItemValue("modelNm", result.prdtCode);
						});
						
						break;
					
					case "btnSave":
						
						/* if(mvno.cmn.isEmpty(form.getItemValue("modelId"))) {
							mvno.ui.alert("모델ID가 없습니다.");
							return;
						} */
						
						if(mvno.cmn.isEmpty(form.getItemValue("modelNm"))) {
							mvno.ui.alert("모델명이 없습니다.");
							return;
						}
						
						if(mvno.cmn.isEmpty(form.getItemValue("intmSrlNo"))) {
							mvno.ui.alert("모델일련번호가 없습니다.");
							return;
						}
						
						mvno.ui.confirm("저장하시겠습니까?", function() {
							mvno.cmn.ajax(ROOT + "/insr/insrMgmt/regIntmInfoByUsimCust.json", form, function(){
								mvno.ui.notify("NCMN0004");
								PAGE.GRID1.refresh();
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
		
		, FORM3 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				,{type: "fieldset", label: "1차검증", name:"FSTVF", inputWidth:630, list:[
					{type:"block", list:[
						{type:"select", label:"검증결과", name:"fstVrfyCd", width:70, offsetLeft:10, options:[{text: "- 선택 -", value: ""}, {text: "적격", value: "FOK"}, {text: "부적격", value: "FNOK"}] }
						, {type:"newcolumn"}
						, {type:"input", label:"검증처리자", name:"fstVrfyId", width:90, offsetLeft:20, labelWidth:70, disabled:true}
						, {type:"newcolumn"}
						, {type:"calendar", label:"검증일자", name:"fstVrfyDttm", width:150, offsetLeft:20, dateFormat:"%Y-%m-%d %H:%i:%s", serverDateFormat:"%Y%m%d%H%i%s", disabled:true}
					]}
				]}
				,{type: "fieldset", label: "2차검증", name:"SNDVF", inputWidth:630, list:[
					{type:"block", list:[
						{type:"select", label:"검증결과", name:"sndVrfyCd", width:70, offsetLeft:10, options:[{text: "- 선택 -", value: ""}, {text: "적격", value: "SOK"}, {text: "부적격", value: "SNOK"}] }
						, {type:"newcolumn"}
						, {type:"input", label:"검증처리자", name:"sndVrfyId", width:90, offsetLeft:20, labelWidth:70, disabled:true}
						, {type:"newcolumn"}
						, {type:"calendar", label:"검증일자", name:"sndVrfyDttm", width:150, offsetLeft:20, dateFormat:"%Y-%m-%d %H:%i:%s", serverDateFormat:"%Y%m%d%H%i%s", disabled:true}
					]}
				]}
				,{type: "fieldset", label: "메모", name:"MEMO", inputWidth:630, list:[
					{type:"block", list:[
						{type:"input", label:"", name:"rmk", width:570, rows:5, maxLength:2000}
					]}
				]}
				, {type: "hidden", name: "vrfyRsltCd"}
				, {type: "hidden", name: "contractNum"}
				, {type: "hidden", name: "insrProdCd"}
				, {type: "hidden", name: "strtDttm"}
				, {type: "hidden", name: "insrTypeCd"}
				, {type: "hidden", name: "imgChkYn"}
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
					
					case "btnSave":
						
						if( mvno.cmn.isEmpty(form.getItemValue("fstVrfyCd")) || 
							(form.getItemValue("vrfyRsltCd") != "REG" && mvno.cmn.isEmpty(form.getItemValue("sndVrfyCd"))) ) {
							mvno.ui.alert("검증결과를 선택 하세요.");
							return;
						}
						
						mvno.ui.confirm("저장하시겠습니까?", function() {
							mvno.cmn.ajax(ROOT + "/insr/insrMgmt/regVrfyRslt.json", form, function(){
								mvno.ui.notify("NCMN0004");
								PAGE.GRID1.refresh();
								mvno.ui.closeWindowById("FORM3");
							});
						});
						
						break;
						
					case "btnClose":
						mvno.ui.closeWindowById("FORM3");
						
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
			mvno.cmn.ajax4lov( ROOT + "/insr/insrMgmt/getInsrCombo.json", "", PAGE.FORM1, "insrProdCd");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0249"}, PAGE.FORM1, "chnCd");					// 가입경로구분
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9004"}, PAGE.FORM1, "reqBuyType");					// 구매유형
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9001"}, PAGE.FORM1, "operType");					// 업무구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0063"}, PAGE.FORM1, "prodType");					// 상품구분
          if (maskingYn != "Y") {
              mvno.ui.disableButton("GRID1", "btnUsim");
              mvno.ui.disableButton("GRID1", "btnVrfy");
          }
		}
	};
</script>
