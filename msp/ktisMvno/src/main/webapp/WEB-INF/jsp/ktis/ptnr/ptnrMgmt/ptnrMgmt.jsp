<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">
	var curMnth = new Date().format("yyyymm");
	var maskingYn = "${maskingYn}";				// 마스킹권한

	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blackOffset:0}
					, {type:"block", list:[
										{type:"select", label:"검색구분", name:"searchGbn", width:80, offsetLeft:10}
										, {type:"newcolumn"}
										, {type:"input", name:"searchVal", width:100}
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
							
							PAGE.GRID1.list(ROOT + "/ptnr/ptnrMgmt/ptnrMgmtList.json", form);
							
							break;
					}
				},
				
				onChange : function(name, value) {//onChange START

				},
				onValidateMore : function (data){
					if( data.searchGbn != "" && data.searchVal.trim() == ""){
						this.pushError("searchVal", "검색어", "검색어를 입력하세요");
					}
				}
			},
			
			GRID1 : {
				css : {
					height : "250px"
				},
				title : "제휴사목록",
				header : "제휴사ID,제휴사명,담당자명,연락처,연동ID,제휴여부,등록일자",	//7
				columnIds : "partnerId,partnerNm,managerNm,contactNum,partnerLinkId,useYn,regstDttm",
				widths : "100,190,100,150,150,100,160",
				colAlign : "center,left,center,center,center,center,center",
				colTypes : "ro,ro,ro,ro,ro,ro,roXd",
				colSorting : "str,str,str,str,str,str,str",
				paging : true,
				pageSize:20,
				buttons : {
					right : {
						btnReg : { label : "등록" },
						btnMod : { label : "수정" }
					}
				},
				checkSelectedButtons : ["btnMod"],
				onRowDblClicked : function(rowId, celInd) {
					// 수정버튼 누른것과 같이 처리?
					if (maskingYn == "Y") {
						this.callEvent('onButtonClick', ['btnMod']);
					}
				},
				onRowSelect : function (rowId, celInd) {
					
					PAGE.GRID2.list(ROOT + "/ptnr/ptnrMgmt/ptnrMgmtLinkList.json", this.getRowData(rowId));
					
				},
				onButtonClick : function(btnId, selectedData) {
					switch (btnId) {
						case "btnReg":
							mvno.ui.createForm("FORM2");
							
							PAGE.FORM2.clear();
							
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM2, "useYn"); // 제휴여부
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM2, "contactNum1"); // 연락처
													
							mvno.ui.popupWindowById("FORM2", "등록화면", 370, 300, {
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
							
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM3, "useYn"); // 제휴여부
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM3, "contactNum1"); // 연락처
							
							var data = selectedData;
							
							PAGE.FORM3.setFormData(data); 
							setInputDataAddHyphen(PAGE.FORM3, data, "contactNum", "tel");
	
							PAGE.FORM3.clearChanged();
	
							mvno.ui.popupWindowById("FORM3", "수정화면", 370, 300, {
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
			},
			
			// --------------------------------------------------------------------------
			// 등록화면
			FORM2 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', labelWidth:'80', inputWidth:'auto'},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						{type:'input', name:'partnerId', label:'제휴사ID', width:160, validate:'ValidAplhaNumeric', maxLength:15, required: true},
						{type:"newcolumn"},
						{type:'input', name:'partnerNm', label:'제휴사명', width:160, maxLength:15, required: true}
					 ]},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						{type:'input', name:'managerNm', label:'담당자명', width:160, maxLength:10},
						{type:"newcolumn"},
						{type:"select", width:72, label:"연락처",name:"contactNum1"},
						{type:"newcolumn"},
						{type:"input", width:40, label:"",name:"contactNum2", maxLength:4, validate:'ValidInteger'},
						{type:"newcolumn"},
						{type:"input", width:40, label:"",name:"contactNum3", maxLength:4, validate:'ValidInteger'},
						{type:"newcolumn"},
						{type:'input', name:'partnerLinkId', label:'연동ID', width:160, maxLength:15},
						{type:"newcolumn"},
						{type:'select', name:'useYn', label:'제휴여부', width:160, required: true}
					 ]},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
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
							mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrMgmtInsert.json", form, function(result) {
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
						{type:'input', name:'partnerId', label:'제휴사ID', width:160, validate:'ValidAplhaNumeric', maxLength:15, required: true, disabled:true},
						{type:"newcolumn"},
						{type:'input', name:'partnerNm', label:'제휴사명', width:160, maxLength:15, required: true}
					 ]},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						{type:'input', name:'managerNm', label:'담당자명', width:160, maxLength:10},
						{type:"newcolumn"},
						{type:"select", width:72, label:"연락처",name:"contactNum1"},
						{type:"newcolumn"},
						{type:"input", width:40, label:"",name:"contactNum2", maxLength:4, validate:'ValidInteger'},
						{type:"newcolumn"},
						{type:"input", width:40, label:"",name:"contactNum3", maxLength:4, validate:'ValidInteger'},
						{type:"newcolumn"},
						{type:'input', name:'partnerLinkId', label:'연동ID', width:160, maxLength:15},
						{type:"newcolumn"},
						{type:'select', name:'useYn', label:'제휴여부', width:160, required: true}
					 ]},
				],
				buttons : {
					center : {
						btnSave : { label : "수정" },
						btnClose : { label: "닫기" }
					}
				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnSave":
							mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrMgmtUpdate.json", form, function(result) {
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
				title : "제휴사연동정의",
				header : "제휴사ID,연동,#cspan,#cspan,FTP경로,#cspan",	//6
				header2 : "#rspan,IF_ID,주기,내용,업로드,다운로드",
				columnIds : "partnerId,ifNo,ifRule,memo,upDir,downDir",
				widths : "120,120,100,210,200,200",
				colAlign : "center,center,center,left,left,left",
				colTypes : "ro,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,str",
				paging : true,
				pageSize:20,
				buttons : {
					right : {
						btnReg : { label : "등록" }
					}
				},
				onButtonClick : function(btnId, selectedData) {
					switch (btnId) {
						case "btnReg":
							mvno.ui.createForm("FORM4");
							
							PAGE.FORM4.clear();
							
							var selectedData = PAGE.GRID1.getSelectedRowData();							
							PAGE.FORM4.setItemValue("partnerId",selectedData.partnerId);
							
							mvno.ui.popupWindowById("FORM4", "등록화면", 420, 300, {
								onClose: function(win) {
									if (! PAGE.FORM4.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
											win.closeForce();
									});
								}
							});
							
							break;
					}
					
				}	
			},

			FORM4 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', labelWidth:'80', inputWidth:'auto'},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						{type:'input', name:'partnerId', label:'제휴사ID', width:200, validate:'ValidAplhaNumeric', maxLength:15, required:true},
						{type:'input', name:'ifNo', label:'IF_ID', width:200, maxLength:15, required:true},
						{type:'input', name:'ifRule', label:'주기', width:200, maxLength:20},
						{type:'input', name:'memo', label:'내용', width:200, maxLength:300},
					 ]},
					 {type:"block", blockOffset:0, offsetLeft:30, list: [
						 {type:'input', name:'upDir', label:'업로드', width:200, maxLength:100},
						 {type:'input', name:'downDir', label:'다운로드', width:200, maxLength:100}
					 ]},
				],
				buttons : {
					center : {
						btnSave : { label : "등록" },
						btnClose : { label: "닫기" }
					}
				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnSave":
							mvno.cmn.ajax(ROOT + "/ptnr/ptnrMgmt/ptnrMgmtLinkInsert.json", form, function(result) {
								if (result.result.code == "OK") {
									mvno.ui.closeWindowById(form, true);
										mvno.ui.notify("NCMN0001");
										PAGE.GRID2.refresh();
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
	};

	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
		    buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.ui.createGrid("GRID2");

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0241"}, PAGE.FORM1, "searchGbn"); // 검색구분
		        if (maskingYn != "Y") {
		            mvno.ui.disableButton("GRID1", "btnMod");
		        }
			}
			
	};

	function setInputDataAddHyphen(form, data, itemname, type){
		 if(!form) return null;
		 if(data == null) return null;
		 if(!itemname) return data;

		var retValue  = "";
		 if(type == "rrnum"){
			 retValue = mvno.etc.setRrnumHyphen(data[itemname]);
		 } else if(type == "bizRegNum"){
			 retValue = mvno.etc.setBizRegNumHyphen(data[itemname]);
		 } else if(type == "tel"){
			 retValue = mvno.etc.setTelNumHyphen(data[itemname]);
		 }
		 
		var arrStr = retValue.split("-");
		var cnt = 1;
		for(i = 0; i < arrStr.length; i++){
			form.setItemValue(itemname+cnt,arrStr[i]);
			cnt++;
		}
	}
</script>
