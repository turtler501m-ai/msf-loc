<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1020.jsp
	 * @Description : 모델 관리 화면
	 * @
	 * @ 수정일		수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2015.07.28 심정보 최초생성
	 * @
	 * @author : 심정보
	 * @Create Date : 2014. 07. 28.
	 */
%>
<!-- 

FORM1 : 검색조건
GRID1 : 대표모델 GRID
FORM2 : 대표모델 등록/수정 폼
GRID2 : 색상모델 GRID
FORM3 : 색상모델 등록/수정 폼
 -->
<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>
<div id="GRID2"></div>
<div id="FORM3" class="section-box"></div>
<!-- Script -->
<script type="text/javascript">
	var detailNewCheck = true;
	var DHX = {
			/* 
			-----------------------------------
			----------- 검색조건 --------------
			-----------------------------------
			*/
			FORM1 : {
				items : [
					{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},			  
						{type : "input",label : "대표모델ID",name : "rprsPrdtId" ,width:100, value:'' , offsetLeft:10, labelWidth:60, maxLength:10},
						{type: 'newcolumn'},
						{type: "button", name: 'btnRprsPrdtFind', value:"검색"},
						{type: 'newcolumn'},
						{type:"input", name:"rprsPrdtIdResult", value:"", width:160, readonly: true},
						{type: 'newcolumn'},
						{type : "input",label : "색상모델ID",name : "prdtId", width:100 ,offsetLeft:20, value:'', labelWidth:60, maxLength:10},
						{type: 'newcolumn'},
						{type: "button", name: 'btnPrdtFind', value:"검색"},
						{type: 'newcolumn'},
						{type:"input", name:"prdtIdResult", value:"", width:160, readonly: true},
					]},
					{type:"block", list:[
						{type: 'select', label: '제조사명', name: 'mnfctId', width:100, offsetLeft:10, value:'', labelWidth:60}, 
						{type: 'newcolumn'},
						{type: 'select', label: '모델유형', name: 'prdtTypeCd', width:100, offsetLeft:234, value:'', labelWidth:60}
					]},
					{type: 'newcolumn', offset:240},
					{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"}
				],
				onChange: function (name, value) {
					switch(name) {
						case 'rprsPrdtId':
							if(value.length >= 4){
								mvno.cmn.ajax(ROOT + "/cmn/intmmdl/selectRprsPrdtNm.json", {rprsPrdtId:value, rprsYn:"Y"}, function(resultData) {
									if(resultData.result.resultCnt > 0){
										//대표모델 존재
										PAGE.FORM1.setItemValue("rprsPrdtIdResult", "[" + resultData.result.resultList[0].prdtNm + "/" + resultData.result.resultList[0].prdtCode + "]"); 
									}else{
										//대표모델 미존재
										PAGE.FORM1.setItemValue("rprsPrdtIdResult", "");
									} 
								});
							} else {
								
								PAGE.FORM1.setItemValue("rprsPrdtIdResult", "");
							}
							break;
						
						case 'prdtId':
							if(value.length >= 4){
								mvno.cmn.ajax(ROOT + "/cmn/intmmdl/selectRprsPrdtNm.json", {prdtId:value, rprsYn:"N"}, function(resultData) {
									if(resultData.result.resultCnt > 0){
										//색상모델 존재
										PAGE.FORM1.setItemValue("prdtIdResult", "[" + resultData.result.resultList[0].prdtNm + "/" + resultData.result.resultList[0].prdtCode + "]"); 
									}else{
										//색상모델 미존재
										PAGE.FORM1.setItemValue("prdtIdResult", "");
									} 
								});
							} else {
								PAGE.FORM1.setItemValue("prdtIdResult", "");	
							}
							break;
					}
				},
				onButtonClick : function(btnId) {

					var form = this;

					switch (btnId) {
						case "btnRprsPrdtFind": //대표모델 찾기 팝업
							mvno.ui.codefinder("RPRSMLDSET", function(result) {
								form.setItemValue("rprsPrdtId", result.rprsPrdtId);
								form.setItemValue("rprsPrdtIdResult", result.prdtNm);
							});
							break;
						case "btnPrdtFind": //색상모델 찾기 팝업
							mvno.ui.codefinder("HNDSET", function(result) {
								form.setItemValue("prdtId", result.prdtId);
								form.setItemValue("prdtIdResult", result.prdtNm);
							});
							break;
							
						case "btnSearch": //조회버튼 클릭
							PAGE.GRID1.list(ROOT + "/cmn/intmmdl/intmMdlList.json", form, {callback : function() {
								if(PAGE.GRID1.getSelectedRowData() == null) PAGE.GRID2.clearAll();
							}});
							
// 							PAGE.GRID1.list(ROOT + "/cmn/intmmdl/intmMdlList.json", form);
// 							PAGE.GRID2.clearAll();
							break;
					}

				}
			},
			
		/* 
		-----------------------------------
		-------- 대표모델 GRID ------------
		-----------------------------------
		*/
		GRID1 : {
			css : {
				height : "320px"
			},
			title : "대표모델",
			header : "대표모델ID,대표모델코드,모델명,제조사,모델유형,모델구분,NFC지원여부,출시일자,단종일자",//8
			columnIds : "rprsPrdtId,prdtCode,prdtNm,mnfctNm,prdtTypeNm,prdtIndNm,nfcUsimYn,prdtLnchDt,prdtDt",
			widths : "80,150,150,150,100,100,100,100,100",
			colAlign : "center,left,left,left,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,roXd,ro,roXd,roXd",
			colSorting : "str,str,str,str,str,str,str,str,str",
			paging:true,
			showPagingAreaOnInit: true,
			pageSize:20,
			buttons : {
				hright : {
					btnDnExcel : {
						label : "엑셀다운로드"
					}
				},
				right : {
					btnReg : { label : "등록" },
					btnMod : { label : "수정" }
				}
			},
			onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {

					case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
						} else {
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download("/cmn/intmmdl/selectIntmMdlListExcel.json?menuId=MSP2000030", true, {postData:searchData});
						}
						break;
					
					case "btnReg": //대표모델 등록 팝업 호출
						
							mvno.ui.createForm("FORM2");
							var data = {
									"prdtTypeCd" :  "01",
									"prdtDt" : "99991231"
							};
							
						
							PAGE.FORM2.setFormData(data, true);

							PAGE.FORM2.setItemValue("btnExistFlag", "0" );
							PAGE.FORM2.setItemValue("btnExistFlagRprsPrdtId", "0" );
							PAGE.FORM2.enableItem("prdtTypeCd");
							PAGE.FORM2.enableItem("rprsPrdtId");
							PAGE.FORM2.enableItem("prdtCode");
							PAGE.FORM2.enableItem("btnExist");
							PAGE.FORM2.enableItem("btnExistRprsPrdtId");
							PAGE.FORM2.hideItem("nfcUsimYn");
							
						

							//제조사 셀렉트박스 셋팅
							mvno.cmn.ajax4lov( ROOT + "/org/mnfctmgmt/selectMnfctList.json", {mnfctYn:"Y"}, PAGE.FORM2, "mnfctId");
							//모델유형 셀렉트박스 셋팅
							mvno.cmn.ajax4lov( ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0007"}, PAGE.FORM2, "prdtTypeCd");
							//모델구분 셋렉트 박스 셋팅
							mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0045", etc1:PAGE.FORM2.getItemValue("prdtTypeCd")}, PAGE.FORM2, "prdtIndCd");
							
							PAGE.FORM2.clearChanged();
							
							mvno.ui.popupWindowById("FORM2", "대표모델 등록", 670, 260, {
								onClose: function(win) {
									if (! PAGE.FORM2.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
									});
								}
							});
						
						break;
						
					case "btnMod": //대표모델 수정 팝업 호출
						if(PAGE.GRID1.getSelectedRowData() == null){
							mvno.ui.alert("ECMN0002");
							break;
						}
						
						var prdtTypeCd = PAGE.GRID1.getSelectedRowData().prdtTypeCd;
						
						
						
							mvno.ui.createForm("FORM2");
							
							PAGE.FORM2.setItemValue("btnExistFlag", "1" );
							PAGE.FORM2.setItemValue("btnExistFlagRprsPrdtId", "1" );
							PAGE.FORM2.disableItem("prdtTypeCd");
							PAGE.FORM2.disableItem("rprsPrdtId");
							PAGE.FORM2.disableItem("prdtCode");
							PAGE.FORM2.disableItem("btnExist");
							PAGE.FORM2.disableItem("btnExistRprsPrdtId");
							PAGE.FORM2.hideItem("nfcUsimYn");
							
														
							if(prdtTypeCd == "02") {
								PAGE.FORM2.showItem("nfcUsimYn");
							}
							
							
							
							//제조사 셀렉트박스 셋팅
							mvno.cmn.ajax4lov( ROOT + "/org/mnfctmgmt/selectMnfctList.json", {mnfctYn:"Y"}, PAGE.FORM2, "mnfctId");
							//모델유형 셀렉트박스 셋팅
							mvno.cmn.ajax4lov( ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0007"}, PAGE.FORM2, "prdtTypeCd");
							
							
							//모델구분 셋렉트 박스 셋팅
							mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0045", etc1:prdtTypeCd}, PAGE.FORM2, "prdtIndCd");
							
							PAGE.FORM2.setFormData(PAGE.GRID1.getSelectedRowData());
							
							PAGE.FORM2.clearChanged();
							
							mvno.ui.popupWindowById("FORM2", "단말기 모델 수정", 670, 260, {
								onClose: function(win) {
									if (! PAGE.FORM2.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
									});
								}
							});
							
						break; 
				}
			},
			onRowSelect : function(rowId) {
					
					var rowData = this.getSelectedRowData();
					
					PAGE.GRID2.clearAll();
					PAGE.GRID2.list(ROOT + "/cmn/intmmdl/intmColrMdlList.json", rowData);
				}
		
		},

		/* 
		-----------------------------------
		----- 대표모델 등록/수정 폼 -------
		-----------------------------------
		*/
		FORM2 : {
			title : "", //대표모델ID, 대표모델코드, 모델명, 제조사, 모델유형, 모델구분, NFC지원여부,  출시일자, 단종일자
			items : [
					{type: "block", blockOffset: 0,offsetLeft: 30, list: [
						{type: 'select', name: 'prdtTypeCd', label: '모델유형', width:120, offsetLeft:0, value:'', required:true},
						
					]},
					{type: 'settings', position: 'label-left', labelWidth: '90', inputWidth: 'auto'},
					{type: "block", blockOffset: 0,offsetLeft: 30, list: [
						{type: 'input', name: 'rprsPrdtId', label: '대표모델ID',  width: 120, offsetLeft: 0,required: true, maxLength:10, validate: 'ValidAplhaNumeric'},
						{type: "newcolumn"},
						{type: 'button', value: '중복', name: 'btnExistRprsPrdtId'} ,  
						{type: 'hidden', value: '0', name: 'btnExistFlagRprsPrdtId'},
						{type: "newcolumn"},
						{type: 'input', name: 'prdtCode', label: '대표모델코드',  width: 120, offsetLeft: 10, value: '' , required:true, maxLength:30, validate: 'ValidAplhaNumeric'},
						{type: "newcolumn"},
						{type: 'button', value: '중복', name: 'btnExist'} ,  
						{type: 'hidden', value: '0', name: 'btnExistFlag'},
					]},
					{type: "block", blockOffset: 0,offsetLeft: 30, list: [
						{type: 'input', name: 'prdtNm', label: '모델명',  width: 120, offsetLeft: 0, value: '' , maxLength:20, required: true},
						{type: "newcolumn"},
						{type: 'select', name: 'mnfctId', label: '제조사',  width: 120, offsetLeft: 60, required: true},
					]},
					{type: "block", blockOffset: 0, offsetLeft: 30, list: [
						{type: 'select', name: 'prdtIndCd', label: '모델구분',  width: 120, offsetLeft: 0, required: true},
						{type: "newcolumn"},
						{type: 'select', name: 'nfcUsimYn', label: 'NFC지원여부',  width: 120, offsetLeft: 60 , options:[{text: "- 선택 -", value: ""},{text: "Y", value: "Y"}, {text: "N", value: "N"}]},
					]},
					{type: "block", blockOffset: 0,offsetLeft: 30, list: [
						{type: 'calendar', name: 'prdtLnchDt' , label: '모델출시일자',  dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d',value:'',  calendarPosition: 'bottom' ,width:120, readonly:true, required:true},
						{type: "newcolumn"},
						{type: 'calendar', name: 'prdtDt', label: '모델단종일자',  dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', offsetLeft: 60,value:'',  calendarPosition: 'bottom' ,width:120 }, 
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
						
						
					case "btnExistRprsPrdtId":
						//대표모델ID 중복 체크
						if(PAGE.FORM2.getItemValue("rprsPrdtId") != ""){
							
							if(PAGE.FORM2.getItemValue("rprsPrdtId").length != 8){
								mvno.ui.alert("대표모델ID는 8자리입니다.");
								return;
							}
							
							mvno.cmn.ajax(ROOT + "/cmn/intmmdl/isExistRprsPrdtId.json", {rprsPrdtId:PAGE.FORM2.getItemValue("rprsPrdtId")}, function(resultData) {
								if(resultData.result.resultCnt > 0){
										//중복
									mvno.ui.alert("ICMN0008");
									form.setItemValue("btnExistFlagRprsPrdtId","0"); 
								}else{
									//사용가능
									mvno.ui.alert("ICMN0007");
									form.setItemValue("btnExistFlagRprsPrdtId","1");
								} 
							});
						} else {
							mvno.ui.alert("대표모델ID를 입력하세요");
						}
						break;
						
					case "btnExist":
						//대표모델코드 중복 체크
						if(PAGE.FORM2.getItemValue("prdtCode") != ""){
							mvno.cmn.ajax(ROOT + "/cmn/intmmdl/isExistRprsPrdtCode.json", {prdtCode:PAGE.FORM2.getItemValue("prdtCode")}, function(resultData) {
								if(resultData.result.resultCnt > 0){
										//중복
									mvno.ui.alert("ICMN0008");
									form.setItemValue("btnExistFlag","0"); 
								}else{
									//사용가능
									mvno.ui.alert("ICMN0007");
									form.setItemValue("btnExistFlag","1");
								} 
							});
						} else {
							mvno.ui.alert("대표모델코드를 입력하세요");
						}
						break;
						
					case "btnSave":
						//저장버튼 클릭
						if (form.isNew()) { // 등록 처리
							
							if(PAGE.FORM2.getItemValue("rprsPrdtId").length != 8){
								mvno.ui.alert("대표모델ID는 8자리입니다.");
								return;
							}
							
							mvno.cmn.ajax(ROOT + "/cmn/intmmdl/insertIntmMdl.json", form, function(result) {
								mvno.ui.closeWindowById(form, true);  
								mvno.ui.notify("NCMN0001");
								PAGE.GRID1.refresh();
							});
						}
						else { // 수정 처리
							mvno.cmn.ajax(ROOT + "/cmn/intmmdl/updateIntmMdl.json", form, function(result) {
								mvno.ui.closeWindowById(form, true);  
								mvno.ui.notify("NCMN0002");
								PAGE.GRID1.refresh();
							});
						}
						break;

					case "btnClose" :
						//닫기버튼 클릭
						mvno.ui.closeWindowById(form);
						break;						  

				}

			},
			onChange : function(name, value){
				
				var prdtTypeCd = PAGE.FORM2.getItemValue("prdtTypeCd");
				
				if(prdtTypeCd == "02"){
					PAGE.FORM2.showItem("nfcUsimYn");
					PAGE.FORM2.setRequired('nfcUsimYn' , true);
				}else{
					PAGE.FORM2.hideItem("nfcUsimYn");
					PAGE.FORM2.setRequired('nfcUsimYn' , false);
				}
				
					
				if(name == "rprsPrdtId"){ //대표모델ID 변경
					PAGE.FORM2.setItemValue("btnExistFlagRprsPrdtId","0");
				}
				if(name == "prdtTypeCd"){ //모델유형 변경시 그에 따른 모델구분 설정 
					mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0045", etc1:PAGE.FORM2.getItemValue("prdtTypeCd")}, PAGE.FORM2, "prdtIndCd");
					
				}
						
						
			},
			onValidateMore : function (data){
				var form = this; 

				if(data.btnExistFlagRprsPrdtId == "0"){
					
					this.pushError("data.btnExistFlagRprsPrdtId","대표모델ID",["ICMN0010"]);
				} else if(data.btnExistFlag == "0"){
					
					this.pushError("data.btnExistFlag","대표모델코드",["ICMN0010"]);
				} else if(data.prdtLnchDt > data.prdtDt){
					
					this.pushError("data.prdtLnchDt","모델출시일자","모델단종일자보다 작아야합니다.");
				}
			}
		},
		/* 
		-----------------------------------
		-------- 색상모델 GRID ------------
		-----------------------------------
		*/
		GRID2 : {
			css : {
				height : "140px"
			},
			title : "색상모델",
			header : "대표모델ID,색상모델ID,색상모델코드,색상,단종일자",//4
			columnIds : "rprsPrdtId,prdtId,prdtCode,prdtColrNm,prdtDt",
			widths : "186,186,186,186,186",
			colAlign : "center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,roXd",
			colSorting : "str,str,str,str,str",
			/* paging:true,
			pageSize:20, */
			buttons : {
				hright : {
					btnDnExcel : {
						label : "엑셀다운로드"
					}
				},
				right : {
					btnReg : { label : "등록" },
					btnMod : { label : "수정" }
				}
			},
			onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {

					case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
						if(PAGE.GRID2.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
						} else {
							var searchData =  PAGE.GRID1.getSelectedRowData(true);
							mvno.cmn.download("/cmn/intmmdl/selectIntmColrMdlListExcel.json?menuId=MSP2000030", true, {postData:searchData});
						}
						break;
				
					case "btnReg":
							detailNewCheck = true;
							if(PAGE.GRID1.getSelectedRowData() == null){
								mvno.ui.alert("ECMN0002");
								break;
							}
							if(PAGE.GRID1.getSelectedRowData().prdtTypeCd != "01"){
								mvno.ui.alert("색상모델은 단말기만 등록 가능합니다.");
								break;
							}
								
							mvno.ui.createForm("FORM3");
							var data = {
									"prdtTypeCd" :  "01",
									"prdtDt" : "99991231"
							};
							
							PAGE.FORM3.setFormData(data, true);
							PAGE.FORM3.disableItem("prdtTypeCd");
							PAGE.FORM3.disableItem("rprsPrdtId");
							PAGE.FORM3.disableItem("rprsPrdtCode");
							PAGE.FORM3.disableItem("prdtNm");
							PAGE.FORM3.disableItem("mnfctId");
							PAGE.FORM3.disableItem("prdtIndCd");
							PAGE.FORM3.disableItem("prdtLnchDt");
							PAGE.FORM3.enableItem("prdtId");
							PAGE.FORM3.enableItem("btnExist");
							PAGE.FORM3.enableItem("prdtCode");
							PAGE.FORM3.enableItem("btnColorExist");
							PAGE.FORM3.enableItem("prdtColrCd");
							
// 							PAGE.FORM3.disableItem("prdtDt");
							//제조사 셀렉트박스 셋팅
							mvno.cmn.ajax4lov( ROOT + "/org/mnfctmgmt/selectMnfctList.json", {mnfctYn:"Y"}, PAGE.FORM3, "mnfctId");
							//모델유형 셀렉트박스 셋팅
							mvno.cmn.ajax4lov( ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0007"}, PAGE.FORM3, "prdtTypeCd");
							//모델구분 셋렉트 박스 셋팅
							mvno.cmn.ajax4lov( ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0045"}, PAGE.FORM3, "prdtIndCd");
							//색상 셀렉트박스 셋팅
							mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0044"}, PAGE.FORM3, "prdtColrCd");
							
							PAGE.FORM3.setFormData(PAGE.GRID1.getSelectedRowData());
							
							PAGE.FORM3.setItemValue("rprsPrdtCode", PAGE.GRID1.getSelectedRowData().prdtCode); 
							PAGE.FORM3.setItemValue("prdtId", "");
							PAGE.FORM3.setItemValue("prdtCode", "");
							PAGE.FORM3.setItemValue("prdtColrCd", "");
							
							PAGE.FORM3.clearChanged();
							
							mvno.ui.popupWindowById("FORM3", "색상모델 등록", 670, 320, {
								onClose: function(win) {
									if (! PAGE.FORM3.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
									});
								}
							});
						break;
						
					case "btnMod":
						detailNewCheck = false;
						if(PAGE.GRID2.getSelectedRowData() == null){
							mvno.ui.alert("ECMN0002");
							break;
						}
							
						mvno.ui.createForm("FORM3");

						
						PAGE.FORM3.setItemValue("btnExistFlag", "1" );
						PAGE.FORM3.setItemValue("btnExistColorFlag", "1" );
						PAGE.FORM3.disableItem("prdtTypeCd");
						PAGE.FORM3.disableItem("rprsPrdtId");
						PAGE.FORM3.disableItem("rprsPrdtCode");
						PAGE.FORM3.disableItem("prdtNm");
						PAGE.FORM3.disableItem("mnfctId");
						PAGE.FORM3.disableItem("prdtIndCd");
						PAGE.FORM3.disableItem("prdtLnchDt");
// 						PAGE.FORM3.disableItem("prdtDt");
						PAGE.FORM3.disableItem("prdtId");
						PAGE.FORM3.disableItem("btnExist");
						PAGE.FORM3.disableItem("prdtCode");
						PAGE.FORM3.disableItem("btnColorExist");
						PAGE.FORM3.disableItem("prdtColrCd");
						
							
						//모델유형 셀렉트박스 셋팅
						mvno.cmn.ajax4lov( ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0007"}, PAGE.FORM3, "prdtTypeCd");
						//색상 셀렉트박스 셋팅
						mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0044"}, PAGE.FORM3, "prdtColrCd");
						//제조사 셀렉트박스 셋팅
						mvno.cmn.ajax4lov( ROOT + "/org/mnfctmgmt/selectMnfctList.json", {mnfctYn:"Y"}, PAGE.FORM3, "mnfctId");
							
						PAGE.FORM3.setFormData(PAGE.GRID2.getSelectedRowData());
							
						//모델구분 셋렉트 박스 셋팅
						mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0045"}, PAGE.FORM3, "prdtIndCd");
						
						PAGE.FORM3.setItemValue("prdtIndCd", PAGE.GRID1.getSelectedRowData().prdtIndCd);
						
						PAGE.FORM3.clearChanged();
						mvno.ui.popupWindowById("FORM3", "색상모델 수정", 670, 320, {
							onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
							
						break; 

				}
			},
			onRowDblClicked : function(rowId, celInd) {
				PAGE.FORM4.callEvent('onButtonClick', ['btnMod']);
			}
		},
		
		/* 
		-----------------------------------
		----- 색상모델 등록/수정 폼 -------
		-----------------------------------
		*/
		FORM3 : {
			title : "",
			items : [
					{type: "block", blockOffset: 0,offsetLeft: 30, list: [
						{type: 'select', name: 'prdtTypeCd', label: '모델유형', width:120, offsetLeft:0, value:'', required: true },
						
					]},
					{type: 'settings', position: 'label-left', labelWidth: '90', inputWidth: 'auto'},
					{type: "block", blockOffset: 0,offsetLeft: 30, list: [
						{type: 'input', name: 'rprsPrdtId', label: '대표모델ID',  width: 120, offsetLeft: 0,required: true, maxLength:10, validate: 'ValidAplhaNumeric'},
// 						{type: "newcolumn"},
// 						{type: "button", name: 'btnRprsPrdtFind', value:"찾기"},
						{type: "newcolumn"},
						{type: 'input', name: 'rprsPrdtCode', label: '대표모델코드',  width: 120, offsetLeft: 60, value: '' , required:true, maxLength:30, validate: 'ValidAplhaNumeric'},
					]},
					{type: "block", blockOffset: 0,offsetLeft: 30, list: [
						{type: 'input', name: 'prdtNm', label: '모델명',  width: 120, offsetLeft: 0, value: '' , maxLength:20, required: true},
						{type: "newcolumn"},
						{type: 'select', name: 'mnfctId', label: '제조사',  width: 120, offsetLeft: 60, required: true},
					]},
					{type: "block", blockOffset: 0, offsetLeft: 30, list: [
						{type: 'select', name: 'prdtIndCd', label: '모델구분',  width: 120, offsetLeft: 0, required: true},
					]},
					{type: "block", blockOffset: 0,offsetLeft: 30, list: [
						{type: 'calendar', name: 'prdtLnchDt' , label: '모델출시일자',  dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d',value:'',  calendarPosition: 'bottom' ,width:120, readonly:true},
						{type: "newcolumn"},
						{type: 'calendar', name: 'prdtDt', label: '모델단종일자',  dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', offsetLeft: 60,value:'',  calendarPosition: 'bottom' ,width:120 }, 
					]}, 
					{type: "block", blockOffset: 0,offsetLeft: 30, list: [
						{type: 'input', name: 'prdtId', label: '색상모델ID',  width: 120, offsetLeft: 0, value: '' , maxLength:20, required: true, maxLength:10},
						{type: "newcolumn"},
						{type: 'button', value: '중복', name: 'btnExist'} ,  
						{type: 'hidden', value: '0', name: 'btnExistFlag'},
						{type: "newcolumn"},
						{type: 'input', name: 'prdtCode', label: '색상모델코드',  width: 120, offsetLeft: 10, value: '' , maxLength:20, required: true, validate: 'ValidAplhaNumeric'},
						{type: "newcolumn"},
						{type: 'button', value: '중복', name: 'btnColorExist'} ,  
						{type: 'hidden', value: '0', name: 'btnExistColorFlag'},
					]},
					{type: "block", blockOffset: 0,offsetLeft: 30, list: [
						{type: 'select', name: 'prdtColrCd', label: '색상',  width: 120, offsetLeft: 0, required: true},
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
// 					case "btnRprsPrdtFind":
// 							mvno.ui.codefinder("RPRSMLDSET", function(result) {
// 								if(result.prdtTypeCd == "01"){
// 									form.setItemValue("rprsPrdtId", result.rprsPrdtId); //대표모델ID
// 									form.setItemValue("prdtNm", result.prdtNm);			//모델명
// 									form.setItemValue("rprsPrdtCode", result.prdtCode);	//모델코드
// 									form.setItemValue("prdtTypeCd", result.prdtTypeCd);	//모델유형
// 									form.setItemValue("prdtIndCd", result.prdtIndCd);	//모델구분
// 									form.setItemValue("prdtColrCd", result.prdtColrCd);	//모델색상코드
// 									form.setItemValue("mnfctId", result.mnfctId);		//제조사ID
// 									form.setItemValue("prdtLnchDt", result.prdtLnchDt);	//출시일자
// 									form.setItemValue("prdtDt", result.prdtDt);			//단종일자
// 								} else {
// 									mvno.ui.alert("LGSO0033");
// 								}
// 							});
// 							break;
							
					case "btnExist":
						//색상모델ID 중복 체크
						if(PAGE.FORM3.getItemValue("prdtId") != ""){
							
							if(PAGE.FORM3.getItemValue("prdtId").length != 8){
								mvno.ui.alert("색상모델ID는 8자리입니다.");
								return;
							}
							
							mvno.cmn.ajax(ROOT + "/cmn/intmmdl/isExistPrdtId.json", {prdtId:PAGE.FORM3.getItemValue("prdtId")}, function(resultData) {
								if(resultData.result.resultCnt > 0){
									//중복
									mvno.ui.alert("ICMN0008");
									form.setItemValue("btnExistFlag","0"); 
								}else{
									//사용가능
									mvno.ui.alert("ICMN0007");
									form.setItemValue("btnExistFlag","1");
								} 
							});
						} else {
							mvno.ui.alert("색상모델ID를 입력하세요");
						}
						break;
					case "btnColorExist":
						//색상모델코드 중복 체크
						if(PAGE.FORM3.getItemValue("prdtCode") != ""){
							mvno.cmn.ajax(ROOT + "/cmn/intmmdl/isExistRprsPrdtCode.json", {prdtCode:PAGE.FORM3.getItemValue("prdtCode")}, function(resultData) {
								if(resultData.result.resultCnt > 0){
										//중복
									mvno.ui.alert("ICMN0008");
									form.setItemValue("btnExistColorFlag","0"); 
								}else{
									//사용가능
									mvno.ui.alert("ICMN0007");
									form.setItemValue("btnExistColorFlag","1");
								} 
							});
						} else {
							mvno.ui.alert("색상모델코드를 입력하세요");
						}
						break;
					case "btnSave":
						//저장버튼 클릭
						if(PAGE.FORM3.getItemValue("rprsPrdtId") == ""){
							mvno.ui.alert("LGSO0034");
							break;
						}
						
						if(PAGE.FORM3.getItemValue("prdtId").length != 8){
							mvno.ui.alert("색상모델ID는 8자리입니다.");
							return;
						}

						if (detailNewCheck) { // 등록 처리
							mvno.cmn.ajax(ROOT + "/cmn/intmmdl/insertIntmColrMdl.json", form, function(result) {
								mvno.ui.closeWindowById(form, true);  
								mvno.ui.notify("NCMN0001");
								PAGE.GRID2.refresh();
							});
						}
						else { // 수정 처리
							
							mvno.cmn.ajax(ROOT + "/cmn/intmmdl/updateIntmColrMdl.json", form, function(result) {
								mvno.ui.closeWindowById(form, true);  
								mvno.ui.notify("NCMN0002");
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
			onChange : function(name, value){
					
				if(name == "prdtId"){
					PAGE.FORM3.setItemValue("btnExistFlag","0");
				}
			},
			onValidateMore : function (data){
				var form = this; 
				if(data.btnExistFlag == "0"){
					
					this.pushError("data.btnExistFlag","색상모델ID",["ICMN0010"]);
				}
				if(data.prdtDt > PAGE.GRID1.getSelectedRowData().prdtDt){
					this.pushError("data.prdtDt","단종일자",["색상모델의 단종일자는 대표모델의 단종일자 보다 이후일 수 없습니다."]);
				}

			}
		}
	};
	
var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		
		buttonAuth: ${buttonAuth.jsonString},
		
	onOpen : function() {

		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		mvno.ui.createGrid("GRID2");
		

		//조회조건 제조사 셀렉트박스 셋팅
		mvno.cmn.ajax4lov( ROOT + "/org/mnfctmgmt/selectMnfctList.json", {mnfctYn:"Y"}, PAGE.FORM1, "mnfctId");
		
		//조회조건 모델유형 셀렉트박스 셋팅
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0007"}, PAGE.FORM1, "prdtTypeCd");
	}

};

</script>