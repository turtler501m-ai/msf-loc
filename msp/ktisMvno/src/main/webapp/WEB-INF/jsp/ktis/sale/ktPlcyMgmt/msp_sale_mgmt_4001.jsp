<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	* @Class Name : msp_sale_mgmt_4001.jsp
	* @Description : 판매번호관리
	* @
	* @ 수정일	    수정자 수정내용
	* @ ---------- ------ -----------------------------
	* @ 2018.01.03 강무성  최초작성
	* @ 
	* @Create Date : 2018. 01. 03
	*/
%>

<!-- 조회조건 -->
<div id="FORM1" class="section-search"></div>

<!-- 판매번호목록 -->
<div id="GRID1"></div>

<!-- 판매번호상세목록 -->
<div id="GRID2"></div>

<!-- 판매번호등록 및 수정 -->
<div id="GROUP1" style="display:none;">
	<div id="FORM2" class="section-box"></div>
	<div class="row">
		<!-- 할인유형 -->
		<div id="GRID3" class="c1" style="width: 110px; z-index: 80;"></div>
		<!-- 개통유형 -->
		<div id="GRID4" class="c1" style="width: 130px; z-index: 70;"></div>
		<!-- 약정기간 -->
		<div id="GRID5" class="c1" style="width: 130px; z-index: 60;"></div>
		<!-- 할부개월수 -->
		<div id="GRID6" class="c1" style="width: 130px; z-index: 50;"></div>
	</div>
</div>

<script type="text/javascript">
	
	var bInqrYN = false;
	
	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
					{type: "block", list: [
						{type: "select", name: "useYn", label: "사용여부", width: 80},
						{type: "newcolumn"},
						{type: "input", name: "slsNm", label: "판매정책명", width:150, offsetLeft: 20},
						{type: "newcolumn"},
						{type: "select", name: "plcyType", label: "정책구분", width: 80, offsetLeft: 20},
						{type: "newcolumn"},
						{type: "select", name: "pppo", label: "서비스구분", width:80, labelWidth: 70, offsetLeft: 20}
					]},
					{type: "button", value: "조회", name: "btnSearch" , className:"btn-search1"}
				],
				onButtonClick : function(btnId) {
					
					var form = this;
					
					switch (btnId) {
					
						case "btnSearch":
							
							PAGE.GRID2.clearAll();
							
							PAGE.GRID1.list(ROOT + "/sale/ktPlcyMgmt/getKtPlcyList.json", form);
							break;
					}
				}
			},
			
			GRID1 : {
				css : {
					height : "280px"
				},
				title : "판매번호목록",
				header : "사용여부,정책구분,판매번호,판매정책명,서비스구분,등록자,등록일시",
				columnIds : "useYnNm,plcyTypeNm,slsNo,slsNm,pppoNm,regstId,regstDttm",
				colAlign : "center,center,center,left,center,center,center",
				colTypes : "ro,ro,ro,ro,ro,ro,roXdt",
				colSorting : "str,str,str,str,str,str,str",
				widths : "80,100,100,300,100,100,150",
				paging: true,
				pageSize:20,
				onRowDblClicked : function(rowId, celInd) {
					PAGE.GRID2.callEvent("onButtonClick", ["btnMod"]);
				},
				onRowSelect:function(rId, cId){
					
					var grid = this;
					
					var sdata = grid.getSelectedRowData();
					var data = {
							slsNo : sdata.slsNo
					}
					
					PAGE.GRID2.list(ROOT + "/sale/ktPlcyMgmt/getKtPlcyDtlList.json", data);
				}
			},
			
			GRID2 : {
				css : {
					height : "210px"
				},
				title : "판매번호상세목록",
				header : "순번,사용여부,정책구분,판매번호,판매정책명,서비스구분,할인유형,개통유형,약정기간,할부개월수,등록자,등록일시",
				columnIds : "ktPlcyNum,useYnNm,plcyTypeNm,slsNo,slsNm,pppoNm,dcTypeNm,operTypeNm,enggCntNm,instCntNm,regstId,regstDttm",
				colAlign : "center,center,center,center,left,center,left,left,left,left,center,center",
				colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXdt",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str",
				widths : "50,80,80,80,200,80,200,200,200,100,100,150",
				paging: true,
				pageSize:20,
				buttons : {
					right : {
						btnReg : { label : "등록" },
						btnMod : { label : "수정" }
					}
				},
				onRowDblClicked : function(rowId, celInd) {
					
					bInqrYN = true;
					
					this.callEvent("onButtonClick", ["btnMod"]);
				},
				
				onButtonClick : function(btnId, selectedData) {
					var form = this;
					
					switch (btnId) {
						case "btnReg" :
							
							mvno.ui.createForm("FORM2");
							mvno.ui.createGrid("GRID3");	// 할인유형
							mvno.ui.createGrid("GRID4");	// 개통유형
							mvno.ui.createGrid("GRID5");	// 약정기간
							mvno.ui.createGrid("GRID6");	// 할부개월수
							
							PAGE.GRID3.clearAll();
							PAGE.GRID4.clearAll();
							PAGE.GRID5.clearAll();
							PAGE.GRID6.clearAll();
							
							PAGE.FORM2.setFormData(true);
							
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011"}, PAGE.FORM2, "useYn");		//사용여부
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0073"}, PAGE.FORM2, "plcyType");	//정책구분(단말/유심)
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0013"}, PAGE.FORM2, "pppo");		//서비스구분
							
							PAGE.FORM2.setItemValue("useYn", "Y");
							PAGE.FORM2.setItemValue("trtmTypeCd", "I");
							PAGE.FORM2.enableItem("slsNo");
							PAGE.FORM2.showItem("btnChk");
							PAGE.FORM2.hideItem("btnInit");
							
							PAGE.FORM2.enableItem("BODY");
							PAGE.GRID3.setEditable(true);
							PAGE.GRID4.setEditable(true);
							PAGE.GRID5.setEditable(true);
							PAGE.GRID6.setEditable(true);
							mvno.ui.enableButton("GRID4","btnSave");
							
							PAGE.GRID3.list(ROOT + "/sale/subsdMgmt/getCommonGridList.json", {grpId: "CMN0074"});	// 할인유형
							PAGE.GRID4.list(ROOT + "/sale/subsdMgmt/getCommonGridList.json", {grpId: "CMN0049"});	// 개통유형
							PAGE.GRID5.list(ROOT + "/sale/subsdMgmt/getCommonGridList.json", {grpId: "CMN0053"});	// 약정기간
							PAGE.GRID6.list(ROOT + "/sale/subsdMgmt/getCommonGridList.json", {grpId: "ORG0017"});	// 할부개월수
							
							PAGE.FORM2.clearChanged();
							
							mvno.ui.popupWindowById("GROUP1", "판매번호등록", 560, 480, {
								onClose: function(win) {
									if (! PAGE.FORM2.isChanged()) {
										return true;
									}
									
									mvno.ui.confirm("CCMN0005", function() {
										win.closeForce();
									});
								}
							});
							
							break;
							
						case "btnMod" :
							
							var data;
							
							if(bInqrYN){
								data = this.getSelectedRowData();
							}else{
								data = PAGE.GRID1.getSelectedRowData();
							}
							
							if(data == null) {
								mvno.ui.alert("선택된 데이터가 존재하지 않습니다.");
								return;
							}
							
							mvno.ui.createForm("FORM2");
							mvno.ui.createGrid("GRID3");	// 할인유형
							mvno.ui.createGrid("GRID4");	// 개통유형
							mvno.ui.createGrid("GRID5");	// 약정기간
							mvno.ui.createGrid("GRID6");	// 할부개월수
							
							PAGE.GRID3.clearAll();
							PAGE.GRID4.clearAll();
							PAGE.GRID5.clearAll();
							PAGE.GRID6.clearAll();
							
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011"}, PAGE.FORM2, "useYn");		//사용여부
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0073"}, PAGE.FORM2, "plcyType");	//정책구분(단말/유심)
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0013"}, PAGE.FORM2, "pppo");		//서비스구분
							
							PAGE.GRID3.list(ROOT + "/sale/ktPlcyMgmt/getMspKtPlcyDiscountByGrid.json", data);	// 할인유형
							PAGE.GRID4.list(ROOT + "/sale/ktPlcyMgmt/getMspKtPlcyOperByGrid.json", data);		// 개통유형
							PAGE.GRID5.list(ROOT + "/sale/ktPlcyMgmt/getMspKtPlcyEnggByGrid.json", data);		// 약정기간
							PAGE.GRID6.list(ROOT + "/sale/ktPlcyMgmt/getMspKtPlcyInstGrid.json", data);			// 할부개월수
							
							PAGE.FORM2.setFormData(data);
							
							PAGE.FORM2.setItemValue("trtmTypeCd", "U");
							PAGE.FORM2.disableItem("slsNo");
							PAGE.FORM2.hideItem("btnChk");
							PAGE.FORM2.hideItem("btnInit");
							
							PAGE.FORM2.clearChanged();
							
							var title = "";
							
							if(bInqrYN){
								title = "판매번호조회";
								PAGE.FORM2.disableItem("BODY");
								PAGE.GRID3.setEditable(false);
								PAGE.GRID4.setEditable(false);
								PAGE.GRID5.setEditable(false);
								PAGE.GRID6.setEditable(false);
								mvno.ui.disableButton("GRID4","btnSave");
							}else{
								title = "판매번호수정";
								PAGE.FORM2.enableItem("BODY");
								PAGE.GRID3.setEditable(true);
								PAGE.GRID4.setEditable(true);
								PAGE.GRID5.setEditable(true);
								PAGE.GRID6.setEditable(true);
								mvno.ui.enableButton("GRID4","btnSave");
							}
							
							mvno.ui.popupWindowById("GROUP1", title, 560, 480, {
								onClose: function(win) {
									bInqrYN = false;
									
									if (! PAGE.FORM2.isChanged()) {
										return true;
									}
									
									mvno.ui.confirm("CCMN0005", function() {
										win.closeForce();
									});
								}
							});
							
							break;
					}
				}
			},
			
			FORM2 : {
				title : "판매번호정보",
				items : [
					{type: "block", name: "BODY", list: [
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						{type: "block", list: [
							{type: "input", name: "slsNo", label: "판매번호", width:80, required: true},
							{type: "newcolumn"},
							{type: "input", name: "slsNm", label: "", width:200},
							{type: "newcolumn"},
							{type: "button", name: "btnChk", value: "중복체크"},
							{type: "button", name: "btnInit", value: "판매번호초기화"}
						]},
						{type: "block", list: [
							{type: "select", name: "useYn", label: "사용여부", width: 70, required: true},
							{type: "newcolumn"},
							{type: "select", name: "plcyType", label: "정책구분", width: 70, offsetLeft: 20, required: true},
							{type: "newcolumn"},
							{type: "select", name: "pppo", label: "서비스구분", width:70, labelWidth: 80, offsetLeft: 20, required: true}
						]},
						{type: "hidden", name: "chkSlsNo"},
						{type: "hidden", name: "trtmTypeCd"}
					]}
				],
				onButtonClick : function(btnId) {
					
					var form = this;
					
					switch (btnId) {
					
						case "btnChk":
							mvno.cmn.ajax(ROOT + "/sale/ktPlcyMgmt/getMspKtPlcyMstBySlsNo.json", form, function(result) {
								if(result.result.code == "OK"){
									mvno.ui.alert("사용가능한 판매번호 입니다.");
									form.setItemValue("chkSlsNo", "OK");
									form.disableItem("slsNo");
									
									form.showItem("btnInit");
									form.hideItem("btnChk");
								}
							});
							break;
							
						case "btnInit":
							form.showItem("btnChk");
							form.hideItem("btnInit");
							form.setItemValue("chkSlsNo", "");
							form.setItemValue("slsNo", "");
							form.enableItem("slsNo");
							break;
					}
				}
			},
			
			GRID3 : {
				css : {
					height : "200px",
					width : "108px"
				},
				title : "할인유형",
				header : "선택,할인유형",
				columnIds : "chk,cdNm",
				colAlign : "center,center",
				colTypes : "ch,ro",
				colSorting : "str,str",
				widths : "30,*",
				paging: false
			},
			
			GRID4 : {
				css : {
					height : "200px",
					//width : "200px"
				},
				title : "개통유형",
				header : "선택,개통유형",
				columnIds : "chk,cdNm",
				colAlign : "center,center",
				colTypes : "ch,ro",
				colSorting : "str,str",
				widths : "30,*",
				paging: false,
				buttons: {
					right:{
						btnSave : { label : "저장" }
					}
				},
				onButtonClick: function(btnId){
					
					switch(btnId){
						case "btnSave" :
							
							if(PAGE.FORM2.getItemValue("trtmTypeCd") == "I"){
								
								if(PAGE.FORM2.getItemValue("slsNo") == null || PAGE.FORM2.getItemValue("slsNo") == "") {
									mvno.ui.alert("판매번호를 입력하세요.");
									return;
								}
								
								if(PAGE.FORM2.getItemValue("chkSlsNo") != "OK"){
									mvno.ui.alert("판매번호 중복체크를 진행해 주세요.");
									return;
								}
								
							}
							
							if(PAGE.FORM2.getItemValue("slsNm") == null || PAGE.FORM2.getItemValue("slsNm") == "") {
								mvno.ui.alert("판매정책명을 입력하세요.");
								return;
							}
							
							var dcIds = "";
							var data = PAGE.GRID3.getCheckedRowData();
							if(data.length < 1) {
								mvno.ui.alert("할인유형을 선택하세요.");
								return;
							} else {
								for(var i=0; i<data.length; i++) {
									dcIds += data[i].cdVal + "&";
								}
							}
							
							var operIds = "";
							var data = PAGE.GRID4.getCheckedRowData();
							if(data.length < 1) {
								mvno.ui.alert("개통유형을 선택하세요.");
								return;
							} else {
								for(var i=0; i<data.length; i++) {
									operIds += data[i].cdVal + "&";
								}
							}
							
							var enggIds = "";
							var data = PAGE.GRID5.getCheckedRowData();
							if(data.length < 1) {
								mvno.ui.alert("약정기간을 선택하세요.");
								return;
							} else {
								for(var i=0; i<data.length; i++) {
									enggIds += data[i].cdVal + "&";
								}
							}
							
							var instIds = "";
							var data = PAGE.GRID6.getCheckedRowData();
							if(data.length < 1) {
								mvno.ui.alert("할부개월수를 선택하세요.");
								return;
							} else {
								for(var i=0; i<data.length; i++) {
									instIds += data[i].cdVal + "&";
								}
							}
							
							var sdata = {
									trtmTypeCd : PAGE.FORM2.getItemValue("trtmTypeCd"),
									slsNo : PAGE.FORM2.getItemValue("slsNo"),
									slsNm : PAGE.FORM2.getItemValue("slsNm"),
									plcyType : PAGE.FORM2.getItemValue("plcyType"),
									pppo : PAGE.FORM2.getItemValue("pppo"),
									useYn : PAGE.FORM2.getItemValue("useYn"),
									dcType : dcIds,
									operType : operIds,
									enggCnt : enggIds,
									instCnt : instIds
							}
							
							mvno.cmn.ajax4confirm("판매번호를 저장하시겠습니까?", ROOT + "/sale/ktPlcyMgmt/savMspKtPlcyMst.json", sdata, function(result) {
								PAGE.FORM2.clearChanged();
								mvno.ui.closeWindowById("GROUP1");
								PAGE.GRID2.clearAll();
								PAGE.GRID1.refresh();
							});
							
							break;
					}
				}
			},
			
			GRID5 : {
				css : {
					height : "200px",
					//width : "200px"
				},
				title : "약정기간",
				header : "선택,약정기간",
				columnIds : "chk,cdNm",
				colAlign : "center,center",
				colTypes : "ch,ro",
				colSorting : "str,str",
				widths : "30,*",
				paging: false,
				buttons: {
					left:{
						btnClose : { label : "닫기" }
					}
				},
				onButtonClick: function(btnId){
					
					switch(btnId){
						case "btnClose" :
							mvno.ui.closeWindowById("GROUP1");
							break;
					}
				}
			},
			
			GRID6 : {
				css : {
					height : "200px",
					//width : "200px"
				},
				title : "할부개월수",
				header : "선택,개월수",
				columnIds : "chk,cdNm",
				colAlign : "center,center",
				colTypes : "ch,ro",
				colSorting : "str,str",
				widths : "30,*",
				paging: false
			},
	};
	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : " ${breadCrumb.breadCrumb}",
			
			buttonAuth: ${buttonAuth.jsonString},
			
			onOpen : function() {
				
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.ui.createGrid("GRID2");
				
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011"}, PAGE.FORM1, "useYn");		//사용여부
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0073"}, PAGE.FORM1, "plcyType");	//정책유형
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0013"}, PAGE.FORM1, "pppo");		//서비스구분
				
			}
			
	};
	
</script>