<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">
	var today = new Date().format("yyyymmdd");
	
	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:70, blackOffset:0}
					, {type:"block", list:[
										{type:"calendar", label:"기준일자", name:"stdrDt", width:100, dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', calendarPosition:"bottom", readonly:true, required:true},
										{type:"newcolumn"},
										{type:"select", label:"제휴사", name:"ptnrId", width:100, labelWidth:40, offsetLeft:20},
										{type:"newcolumn"},
										{type:"input", label:"프로모션", name:"prmtNm", width:120, labelWidth:50, offsetLeft:20},
										{type:"newcolumn"},
										{type:"input", label:"제휴요금제", name:"rateNm", width:120, offsetLeft:20}
					                	]}

					//버튼 여러번 클릭 막기 변수
					, {type : 'hidden', name: "btnCount1", value:"0"} 
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}
					                				
					, {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							
							PAGE.GRID1.list(ROOT + "/ptnr/ptnrPrmt/ptnrPrmtList.json", form);
							
							break;
					}
				},
				
				onChange : function(name, value) {//onChange START
					
				},
				onValidateMore : function (data){

				}
			},
			
			GRID1 : {
				css : {
					height : "550px"
				},
				title : "제휴프로모션관리",
				header : "제휴사,프로모션ID,프로모션명,제휴요금제,지급포인트,지급주기(개월),모집경로,대리점ID,대리점명,USIM접점조직ID,USIM접점조직명,적용시작일,적용종료일,등록자",	//11
				columnIds : "ptnrNm,prmtId,prmtNm,rateNm,pointVal,prvdCycl,onOffTypeNm,orgnId,orgnNm,usimOrgnId,usimOrgnNm,strtDate,endDate,usrNm",
				widths : "100,80,190,190,80,100,100,100,100,100,100,100,100,100",
				colAlign : "left,center,left,left,right,right,left,left,left,left,left,center,center,center",
				colTypes : "ro,ro,ro,ro,ron,ron,ro,ro,ro,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str",
				hiddenColumns:[],
				paging : true,
				pageSize:20,
				buttons : {
					right : {
						btnReg : { label : "등록" },
						btnMod : { label : "수정" }
					},
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					}
				},
				checkSelectedButtons : ["btnMod"],
				onRowDblClicked : function(rowId, celInd) {
					// 수정버튼 누른것과 같이 처리?
					this.callEvent('onButtonClick', ['btnMod']);
				},
				onRowSelect : function (rowId, celInd) {
					
					
				},
				onButtonClick : function(btnId, selectedData) {
					switch (btnId) {
						case "btnReg":
							mvno.ui.createForm("FORM2");
							
							PAGE.FORM2.clear();

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0243"}, PAGE.FORM2, "ptnrId"); // 제휴사

							PAGE.FORM2.setItemValue("pointVal",0);
							PAGE.FORM2.setItemValue("strtDate",today);
							PAGE.FORM2.setItemValue("endDate",today);
							PAGE.FORM2.setItemValue("prvdCycl",2);
							PAGE.FORM2.setItemValue("prmtNm","프로모션1");
							

							mvno.cmn.ajax4lov( ROOT + "/ptnr/ptnrAddSvc/getRateComboList.json", PAGE.FORM2, PAGE.FORM2, "rateCd"); //요금제
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM2, "onOffType");// 모집경로
							
							
							

							mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrRateInfo.json", PAGE.FORM2, function(result) {
								if(result.result.data.total !=0){
									PAGE.FORM2.setItemValue("baseAmt",result.result.data.rows[0].baseAmt);
								}
							});
							
							mvno.ui.popupWindowById("FORM2", "등록화면", 370, 390, {  //350, 330
								onClose: function(win) {
									if (! PAGE.FORM2.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
											win.closeForce();
									});
								}
							});
							
							break;
							
						case "btnMod":
							mvno.ui.createForm("FORM3");

							PAGE.FORM3.clear();

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0243"}, PAGE.FORM3, "ptnrId"); // 제휴사
							mvno.cmn.ajax4lov( ROOT + "/sale/rateMgmt/getRateComboList.json", "", PAGE.FORM3, "rateCd"); //요금제

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM3, "onOffType");// 모집경로

							var data = selectedData;
							
							data.strtDate = data.strtDate.replace(/-/gi,"");
							data.endDate = data.endDate.replace(/-/gi,"");
							
							
							PAGE.FORM3.setFormData(data); 

							
							mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrRateInfo.json", PAGE.FORM3, function(result) {
								if(result.result.data.total !=0){
									PAGE.FORM3.setItemValue("baseAmt",result.result.data.rows[0].baseAmt);
								}
							});
							
							
							PAGE.FORM3.clearChanged();
	
							mvno.ui.popupWindowById("FORM3", "수정화면", 370, 390, {
								onClose: function(win) {
									if (! PAGE.FORM3.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
											win.closeForce();
									});
								}
							});
							
							break;
							
						case "btnDnExcel":
							
							var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
							if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") {
								return; //버튼 최초 클릭일때만 조회가능하도록
							}
							
							if(PAGE.GRID1.getRowsNum() == 0) {
								mvno.ui.alert("데이터가 존재하지 않습니다.");
								return;
							}
							
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/ptnr/ptnrPrmt/ptnrPrmtListEx.json?menuId=MSP6000033", true, {postData:searchData});
							break;							
	
					}
				
				}
			},
			
			// --------------------------------------------------------------------------
			// 등록화면
			FORM2 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', labelWidth:'100', inputWidth:'auto'},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						{type:'select', name:'ptnrId', label:'제휴사', width:150, required: true},
						{type:'input', name:'prmtNm', label:'프로모션명', width:150, required: true},
						{type:'select', name:'rateCd', label:'제휴요금제', width:150, required: true},
						
						{type:'input', name:'baseAmt', label:'기본요금', width:150, disabled:true, numberFormat:"0,000,000,000", validate:"ValidInteger"},
					 ]},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						{type:'input', name:'pointVal', label:'지급포인트', width:150, required: true, maxLength:10, numberFormat:"0,000,000,000", validate:"ValidInteger"},
						{type:'input', name:'prvdCycl', label:'지급주기(개월)', width:150, required: true, maxLength:10, numberFormat:"0,000,000,000", validate:"ValidInteger"}
					 ]},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						
						{type:"select",name:"onOffType", label:"모집경로",  width:150},
						{type: "newcolumn", offsetLeft:3},
						
						{type: "input", name:"orgnId", label:"대리점", value: "", width:100},
						{type: "newcolumn", offsetLeft:3},
						{type: "button", value:"검색", name:"btnOrgFind"},
						{type: "newcolumn", offsetLeft:3},

						{type: "input", name:"usimOrgnId", label:"유심접점", value: "", width:100},
						{type: "newcolumn", offsetLeft:3},
						{type: "button", value:"검색", name:"btnUsimOrgFind"}
					 ]},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						
						{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'strtDate', label: '적용시작일', calendarPosition: 'bottom', width:100 },
						{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'endDate', label: '적용종료일', calendarPosition: 'bottom', width:100 }
					 ]}
				],
				buttons : {
					center : {
						btnSave : { label : "저장" },
						btnClose : { label: "닫기" }
					}
				},
				onChange : function(name, value) {//onChange START
					var form = this;
					switch(name){
						case "ptnrId" :
							mvno.cmn.ajax4lov( ROOT + "/ptnr/ptnrAddSvc/getRateComboList.json", {ptnrId:value}, form, "rateCd"); //요금제

							mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrRateInfo.json", form, function(result) {
								console.log(result);
								if(result.result.data.total !=0){
									PAGE.FORM2.setItemValue("baseAmt",result.result.data.rows[0].baseAmt);
								}
							});

							break;
							
						case "rateCd" :

							mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrRateInfo.json", form, function(result) {
								console.log(result);
								if(result.result.data.total !=0){
									PAGE.FORM2.setItemValue("baseAmt",result.result.data.rows[0].baseAmt);
								}
							});

							break;
							
					}
				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnSave":
							
							
							var strtDate = PAGE.FORM2.getItemValue("strtDate","");
							var endDate = PAGE.FORM2.getItemValue("endDate","");
							
							if(strtDate > endDate){
								mvno.ui.alert("적용종료일자가 더 커야 합니다.");
			                     return;
							}
							
							mvno.cmn.ajax(ROOT + "/ptnr/ptnrPrmt/ptnrPrmtInsert.json", form, function(result) {
								if (result.result.code == "OK") {
									mvno.ui.closeWindowById(form, true);
										mvno.ui.notify("NCMN0001");
										PAGE.GRID1.refresh();
								} else {
									mvno.ui.alert(result.result.msg);
								}
							});
							break;

						case "btnClose" :
							mvno.ui.closeWindowById(form);
							break;
						
						case "btnOrgFind" :
							mvno.ui.codefinder("APPRORGAGENCY", function(result) {
								form.setItemValue("orgnId", result.orgnId);
//								form.setItemValue("orgnNm", result.orgnNm);
							});
							
							break;
							
							
						case "btnUsimOrgFind" :
							mvno.ui.codefinder("APPRORGAGENCY", function(result) {
								form.setItemValue("usimOrgnId", result.orgnId);
//								form.setItemValue("orgnNm", result.orgnNm);
							});
							
							break;
							
					}
				},
				onValidateMore : function (data){

				}
			},	
			
			
			// --------------------------------------------------------------------------
			//수정화면
			FORM3 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', labelWidth:'100', inputWidth:'auto'},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						{type:'select', name:'ptnrId', label:'제휴사', width:150, disabled:true, required: true},
						{type:'input', name:'prmtNm', label:'프로모션명', width:150, required: true},
						{type:'select', name:'rateCd', label:'제휴요금제', width:150, disabled:true, required: true},

						{type:'input', name:'baseAmt', label:'기본요금', width:150, disabled:true, numberFormat:"0,000,000,000", validate:"ValidInteger"},
					 ]},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						 
						{type:'input', name:'pointVal', label:'지급포인트', width:150, required: true, maxLength:10, numberFormat:"0,000,000,000", validate:"ValidInteger"},
						{type:'input', name:'prvdCycl', label:'지급주기(개월)', width:150, required: true, maxLength:10, numberFormat:"0,000,000,000", validate:"ValidInteger"}
					 ]},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
							
						{type:"select",name:"onOffType", label:"모집경로",  width:150},
						{type: "newcolumn", offsetLeft:3},
						
						{type: "input", name:"orgnId", label:"대리점", value: "", width:100},
						{type: "newcolumn", offsetLeft:3},
						{type: "button", value:"검색", name:"btnOrgFind"},
						{type: "newcolumn", offsetLeft:3},

						{type: "input", name:"usimOrgnId", label:"유심접점", value: "", width:100},
						{type: "newcolumn", offsetLeft:3},
						{type: "button", value:"검색", name:"btnUsimOrgFind"}
					 ]},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'strtDate', label: '적용시작일', calendarPosition: 'bottom', width:100 },
						{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'endDate', label: '적용종료일', calendarPosition: 'bottom', width:100 }
					 ]}
				],
				buttons : {
					center : {
						btnSave : { label : "수정" },
						btnClose : { label: "닫기" }
					}
				},
				onChange : function(name, value) {//onChange START
					var form = this;
					switch(name){
						case "rateCd" :
							if(value != null && value != "" && value.trim() != ""){
								mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrRateInfo.json", form, function(result) {
									
									PAGE.FORM3.setItemValue("baseAmt",result.result.data.rows[0].baseAmt);
								});
								
								
							} else {
								
							}

							break;
					}
				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnSave":
							
							
							var strtDate = PAGE.FORM3.getItemValue("strtDate","");
							var endDate = PAGE.FORM3.getItemValue("endDate","");
							
							if(strtDate > endDate){
								mvno.ui.alert("적용종료일자가 더 커야 합니다.");
			                     return;
							}
							
							
							
							mvno.cmn.ajax(ROOT + "/ptnr/ptnrPrmt/ptnrPrmtUpdate.json", form, function(result) {
								if (result.result.code == "OK") {
									mvno.ui.closeWindowById(form, true);
										mvno.ui.notify("NCMN0002");
										PAGE.GRID1.refresh();
								} else {
									mvno.ui.alert(result.result.msg);
								}
							});
							break;

						case "btnClose" :
							mvno.ui.closeWindowById(form);
							break;

						case "btnOrgFind" :
							mvno.ui.codefinder("APPRORGAGENCY", function(result) {
								form.setItemValue("orgnId", result.orgnId);
//								form.setItemValue("orgnNm", result.orgnNm);
							});
							
							break;
							
							
						case "btnUsimOrgFind" :
							mvno.ui.codefinder("APPRORGAGENCY", function(result) {
								form.setItemValue("usimOrgnId", result.orgnId);
//								form.setItemValue("orgnNm", result.orgnNm);
							});
							
							break;
							
					}
				},
				onValidateMore : function (data){

				}
			},	
			
	};

	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
		    buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");

				PAGE.FORM1.setItemValue("stdrDt", today);
				
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0243"}, PAGE.FORM1, "ptnrId"); // 제휴사
			}
			
	};
</script>
