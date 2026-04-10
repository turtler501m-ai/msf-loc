<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">
	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blackOffset:0}
					, {type:"block", list:[
										{type:"select", label:"제휴사", name:"ptnrId", width:100, offsetLeft:10},
										{type:"newcolumn"},
										{type:"input", label:"제휴요금", name:"rateNm", width:100, offsetLeft:20},
										{type:"newcolumn"},
										{type:"select", label:"지급유형", name:"pointType", width:100, offsetLeft:20},
										{type:"newcolumn"},
										{type:"select", label:"사용여부", name:"useYn", width:100, offsetLeft:20}
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
							
							PAGE.GRID1.list(ROOT + "/ptnr/ptnrMgmt/ptnrRateList.json", form);
							
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
					height : "300px"
				},
				title : "제휴사요금관리",
				header : "제휴사,제휴요금제,요금제유형,데이터유형,기본요금,지급유형,지급포인트,사용여부,적용시작일시,적용종료일시,등록자,,",	//11
				columnIds : "ptnrNm,rateNm,rateTypeNm,dataType,baseAmt,pointTypeNm,pointVal,useYn,strtDate,endDate,usrNm,pointType,rateCd",
				widths : "100,190,80,80,80,80,80,80,150,150,80,0,0",
				colAlign : "center,left,center,center,right,center,right,center,center,center,center,center,center",
				colTypes : "ro,ro,ro,ro,ron,ro,ron,ro,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str",
				hiddenColumns:[11,12],
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
					
					PAGE.GRID2.list(ROOT + "/ptnr/ptnrMgmt/ptnrRateHist.json", this.getRowData(rowId));
					
				},
				onButtonClick : function(btnId, selectedData) {
					switch (btnId) {
						case "btnReg":
							mvno.ui.createForm("FORM2");
							
							PAGE.FORM2.clear();

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0243"}, PAGE.FORM2, "ptnrId"); // 제휴사
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0060"}, PAGE.FORM2, "pointType"); // 지급유형
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0242", orderBy:"etc1"}, PAGE.FORM2, "useYn"); // 사용여부
							mvno.cmn.ajax4lov( ROOT + "/sale/rateMgmt/getRateComboList.json", "", PAGE.FORM2, "rateCd"); //요금제

							PAGE.FORM2.setItemValue("pointVal",0);
							
							mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrRateInfo.json", PAGE.FORM2, function(result) {
								PAGE.FORM2.setItemValue("rateTypeNm",result.result.data.rows[0].rateTypeNm);
								PAGE.FORM2.setItemValue("dataType",result.result.data.rows[0].dataType);
								PAGE.FORM2.setItemValue("baseAmt",result.result.data.rows[0].baseAmt);
							})					
							mvno.ui.popupWindowById("FORM2", "등록화면", 350, 330, {
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
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0060"}, PAGE.FORM3, "pointType"); // 지급유형
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0242", orderBy:"etc1"}, PAGE.FORM3, "useYn"); // 사용여부
							mvno.cmn.ajax4lov( ROOT + "/sale/rateMgmt/getRateComboList.json", "", PAGE.FORM3, "rateCd"); //요금제
							
							var data = selectedData;
							PAGE.FORM3.setFormData(data); 

							PAGE.FORM3.clearChanged();
	
							mvno.ui.popupWindowById("FORM3", "수정화면", 350, 330, {
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
							mvno.cmn.download(ROOT + "/ptnr/ptnrMgmt/ptnrRateListEx.json?menuId=MSP6000022", true, {postData:searchData});
							break;							
	
					}
				
				}
			},
			
			// --------------------------------------------------------------------------
			// 등록화면
			FORM2 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', labelWidth:'80', inputWidth:'auto'},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						{type:'select', name:'ptnrId', label:'제휴사', width:150, required: true},
						{type:'select', name:'rateCd', label:'제휴요금제', width:150, required: true},
						{type:'input', name:'rateTypeNm', label:'요금제유형', width:150, disabled:true},
						{type:'hidden', name:'rateType'},
						{type:'input', name:'dataType', label:'데이터유형', width:150, disabled:true},
						{type:'input', name:'baseAmt', label:'기본요금', width:150, disabled:true, numberFormat:"0,000,000,000", validate:"ValidInteger"},
					 ]},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						{type:'select', name:'pointType', label:'지급유형', width:150, required: true},
						{type:'input', name:'pointVal', label:'지급포인트', width:150, required: true, maxLength:10, numberFormat:"0,000,000,000", validate:"ValidInteger"},
						{type:'select', name:'useYn', label:'사용여부', width:150, required: true}
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
						case "rateCd" :
							if(value != "" && value.trim() != ""){
								mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrRateInfo.json", form, function(result) {
									PAGE.FORM2.setItemValue("rateTypeNm",result.result.data.rows[0].rateTypeNm);
									PAGE.FORM2.setItemValue("dataType",result.result.data.rows[0].dataType);
									PAGE.FORM2.setItemValue("baseAmt",result.result.data.rows[0].baseAmt);
								})		
							} else {
								PAGE.FORM2.setItemValue("rateTypeNm","");
								PAGE.FORM2.setItemValue("dataType","");
								PAGE.FORM2.setItemValue("baseAmt","");								
							}
							
							break;
					}
				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnSave":
							mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrRateInsert.json", form, function(result) {
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
					}
				},
				onValidateMore : function (data){

				}
			},	

			FORM3 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', labelWidth:'80', inputWidth:'auto'},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						{type:'select', name:'ptnrId', label:'제휴사', width:150, disabled:true, required: true},
						{type:'select', name:'rateCd', label:'제휴요금제', width:150, disabled:true, required: true},
						{type:'input', name:'rateTypeNm', label:'요금제유형', width:150, disabled:true},
						{type:'hidden', name:'rateType'},
						{type:'input', name:'dataType', label:'데이터유형', width:150, disabled:true},
						{type:'input', name:'baseAmt', label:'기본요금', width:150, disabled:true, numberFormat:"0,000,000,000", validate:"ValidInteger"},
					 ]},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						{type:'select', name:'pointType', label:'지급유형', width:150, disabled:true, required: true},
						{type:'input', name:'pointVal', label:'지급포인트', width:150, required: true, maxLength:10, numberFormat:"0,000,000,000", validate:"ValidInteger"},
						{type:'select', name:'useYn', label:'사용여부', width:150, required: true}
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
							if(value != "" && value.trim() != ""){
								mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrRateInfo.json", form, function(result) {
									PAGE.FORM3.setItemValue("rateType",result.result.data.rows[0].rateType);
									PAGE.FORM3.setItemValue("dataType",result.result.data.rows[0].dataType);
									PAGE.FORM3.setItemValue("baseAmt",result.result.data.rows[0].baseAmt);
								})		
							} else {
								PAGE.FORM3.setItemValue("rateType","");
								PAGE.FORM3.setItemValue("dataType","");
								PAGE.FORM3.setItemValue("baseAmt","");								
							}
							
							break;
					}
				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnSave":
							mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrRateUpdate.json", form, function(result) {
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
					}
				},
				onValidateMore : function (data){

				}
			},	
			
			GRID2 : {
				css : {
					height : "180px"
				},
				title : "제휴요금이력",
				header : "제휴사,제휴요금제,요금제유형,데이터유형,기본요금,지급유형,지급포인트,사용여부,적용시작일시,적용종료일시,등록자",	//11
				columnIds : "ptnrNm,rateNm,rateTypeNm,dataType,baseAmt,pointTypeNm,pointVal,useYn,strtDate,endDate,usrNm",
				widths : "100,190,80,80,80,80,80,80,150,150,80",
				colAlign : "center,left,center,center,right,center,right,center,center,center,center",
				colTypes : "ro,ro,ro,ro,ron,ro,ron,ro,roXd,roXd,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str",
				paging : true,
				pageSize:20,
				buttons : {

				},
				onButtonClick : function(btnId, selectedData) {

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
				mvno.ui.createGrid("GRID2");

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0243"}, PAGE.FORM1, "ptnrId"); // 제휴사
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0060"}, PAGE.FORM1, "pointType"); // 지급유형
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0242", orderBy:"etc1"}, PAGE.FORM1, "useYn"); // 사용여부
			}
			
	};
</script>
