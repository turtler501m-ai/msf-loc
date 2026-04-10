<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name  : rateGroupRelMgmt.jsp
	 * @Description : 요금그룹관계관리
	 * @
	 * @  수정일	  	수정자			  수정내용
	 * @ ---------   ---------   -------------------------------
	 * @ 2018.06.01  	JUNA		  최초생성
	 * @ 
	 * @author : JUNA
	 * @Create Date : 2018.06.01
	 */
%>
<div class="row">
	<div id="GRID1" class="c5"></div>
	<div id="GRID2" class="c5"></div>
</div>
<div class="row">
	<div id="GRID3" class="c5" style="margin-top: -373px" ></div>
</div>
<div id="FORM1" class="section-box"></div>
<div id="FORM2" class="section-box"></div>
<div id="FORM3" class="section-box"></div>

<script type="text/javascript">
	var DHX = {
			GRID1 : {
				css : { height : "300px"},
				title : "요금그룹관리",
				header : "요금그룹코드,요금그룹,그룹유형,등록자,등록일자", 
				columnIds : "mstRateGrpCd,mstRateGrpNm,rateGrpTypeNm,usrNm,usrDttm",
				colAlign : "left,left,center,center,center",
				colTypes : "ro,ro,ro,ro,ro",
				widths : "80,250,60,55,75",
				colSorting : "str,str,str,str,str",
				hiddenColumns: [0],
				buttons : {
					right : {
						btnAdd: { label : "추가" },
						btnDel: { label : "삭제" }
					}
				},
				onRowSelect : function(rowId, celInd) {
					var rowData = this.getRowData(rowId);
					PAGE.GRID3.list(ROOT + "/sale/rateMgmt/getGroupMappingRateList.json", {mstRateGrpCd:rowData.mstRateGrpCd});
				},
				onButtonClick : function(btnId, selectedData) {
					switch (btnId) {
						case "btnAdd" :
							mvno.ui.createForm("FORM1");
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0063"}, PAGE.FORM1, "rateGrpTypeCd");
							
							mvno.ui.popupWindowById("FORM1", "요금그룹등록", 400, 250, {
								onClose: function(win) {
									if (! PAGE.FORM1.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
									});
								}
							});

							break;
						case "btnDel" :
							if (!PAGE.GRID1.getSelectedRowData()) {
								mvno.ui.alert("요금그룹을 먼저 선택하세요.");
								break;
							}
							
							var rowData = PAGE.GRID1.getSelectedRowData();
							
							mvno.cmn.ajax4confirm("삭제하시겠습니까?",ROOT + "/sale/rateMgmt/deleteRateGroupInfo.json", {mstRateGrpCd:rowData.mstRateGrpCd}, function(result) {
								mvno.ui.notify("NCMN0003");
								PAGE.GRID1.refresh();
								PAGE.GRID2.refresh();
								PAGE.GRID3.refresh();
							});
							
							break;
					}
				},
				onRowDblClicked : function(rowId, selectedData) {
					mvno.ui.createForm("FORM1");
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0063"}, PAGE.FORM1, "rateGrpTypeCd");
					
					var rowData = PAGE.GRID1.getSelectedRowData();
					
					PAGE.FORM1.setFormData(rowData);
					
					mvno.ui.popupWindowById("FORM1", "요금그룹수정", 400, 250, {
						onClose: function(win) {
							if (! PAGE.FORM1.isChanged()) return true;
							mvno.ui.confirm("CCMN0005", function(){
								win.closeForce();
							});
						}
					});
				}
			},
			
			GRID2 : {
				css : { height : "650px"},
				title : "그룹관계관리",
				header : "변경전코드,변경후코드,요금그룹,#cspan,등록자,등록일자",
				header2 : "#rspan,#rspan,변경전,변경후,#rspan,#rspan",
				columnIds : "mstRateGrpCd,subRateGrpCd,mstRateGrpNm,subRateGrpNm,usrNm,usrDttm",
				colAlign : "left,left,left,left,center,center",
				colTypes : "ro,ro,ro,ro,ro,roXd",
				widths : "150,150,155,155,55,75",
				colSorting : "str,str,str,str,str,str",
				hiddenColumns: [0,1],
				buttons : {
					hright : {
						btnRateRelView : { label : "요금변경관계VIEW" }
					},
					right : {
						btnAdd: { label : "추가" },
						btnDel: { label : "삭제" }
					}
				},
				onButtonClick : function(btnId, selectedData) {
					switch (btnId) {
						case "btnRateRelView" :
							mvno.ui.popupWindow(ROOT + "/sale/rateMgmt/groupByRateReList.do", "요금변경관계", '900', '700');
							
							break;
						case "btnAdd" :
							mvno.ui.createForm("FORM2");
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0062"}, PAGE.FORM2, "mstRateGrpCd");
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0062"}, PAGE.FORM2, "subRateGrpCd");
							
							mvno.ui.popupWindowById("FORM2", "그룹관계등록", 400, 250, {
								onClose: function(win) {
									if (! PAGE.FORM2.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
									});
								}
							});

							break;
						case "btnDel" :
							if (!PAGE.GRID2.getSelectedRowData()) {
								mvno.ui.alert("그룹관계를 먼저 선택하세요.");
								break;
							}
							
							var rowData = PAGE.GRID2.getSelectedRowData();
							
							mvno.cmn.ajax4confirm("삭제하시겠습니까?",ROOT + "/sale/rateMgmt/deleteGroupRelRateInfo.json", {mstRateGrpCd:rowData.mstRateGrpCd,subRateGrpCd:rowData.subRateGrpCd}, function(result) {
								mvno.ui.notify("NCMN0003");
								PAGE.GRID2.refresh();
							});
							
							break;
					}
				}
			},
			
			GRID3 : {
				css : { height : "300px"},
				title : "그룹별요금관리",
				header : "요금그룹코드,요금그룹,요금상품,등록자,등록일자", 
				columnIds : "mstRateGrpCd,mstRateGrpNm,rateNm,usrNm,usrDttm",
				colAlign : "left,left,left,center,center",
				colTypes : "ro,ro,ro,ro,roXd",
				widths : "80,145,160,55,80",
				colSorting : "str,str,str,str,str",
				hiddenColumns: [0],
				buttons : {
					right : {
						btnAdd: { label : "추가" },
						btnDel: { label : "삭제" }
					}
				},
				onButtonClick : function(btnId, selectedData) {
					switch (btnId) {
						case "btnAdd" :
							mvno.ui.createForm("FORM3");
							mvno.cmn.ajax4lov( ROOT + "/sale/rateMgmt/getAllRateList.json", "", PAGE.FORM3, "rateCd");
							
							var rowData = PAGE.GRID1.getSelectedRowData();
							PAGE.FORM3.setFormData(rowData);
							PAGE.FORM3.disableItem("mstRateGrpNm");
							
							mvno.ui.popupWindowById("FORM3", "그룹요금상품등록", 400, 250, {
								onClose: function(win) {
									if (! PAGE.FORM3.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
									});
								}
							});

							break;
						case "btnDel" :
							if (!PAGE.GRID3.getSelectedRowData()) {
								mvno.ui.alert("그룹요금상품를 먼저 선택하세요.");
								break;
							}
							
							var rowData = PAGE.GRID3.getSelectedRowData();
							
							mvno.cmn.ajax4confirm("삭제하시겠습니까?",ROOT + "/sale/rateMgmt/deleteGroupMappingRateInfo.json", {rateCd:rowData.rateCd}, function(result) {
								mvno.ui.notify("NCMN0003");
								PAGE.GRID3.refresh();
							});
							
							break;
					}
				}
			},
			
			FORM1 : {
				title : "",
				items : [
							{type:"settings", position:"label-left", labelAlign:"left",  blockOffset:0},
							{type:"block", blockOffset:0, list: [
								{type:"select", name:"rateGrpTypeCd", label:"그룹유형", width:120, labelWidth:80},
								{type:"newcolumn"},
							    {type:"input", name:"mstRateGrpNm", label:"요금그룹", width:220, labelWidth:80},
							    {type:"hidden", name:"mstRateGrpCd"}
							]}
						],
				buttons : {
					 center : {
						btnSave: { label : "저장" },
						btnCancel: { label : "취소" }
					}
				},
				onButtonClick : function(btnId) {
					var form = this;

					switch(btnId){
						case "btnSave" :
								mvno.cmn.ajax(ROOT + "/sale/rateMgmt/setRateGroupInfo.json", form, function(result) {
								mvno.ui.notify("NCMN0004");
								PAGE.GRID1.refresh();
								PAGE.GRID2.refresh();
								PAGE.GRID3.refresh();
								mvno.ui.closeWindowById(form, true);
							});

							break;

						case "btnCancel" :
							mvno.ui.closeWindowById(form, true);
							break;
					}
				},
				onValidateMore : function(data){

					if(data.rateGrpTypeCd == "") {
						this.pushError("data.rateGrpTypeCd", "그룹유형", "그룹유형을 선택해주세요.");
					}

					if(data.mstRateGrpNm == "") {
						this.pushError("data.mstRateGrpNm", "요금그룹", "요금그룹을 입력해주세요.");
					}

				}
			},
			
			FORM2 : {
				title : "",
				items : [
							{type:"settings", position:"label-left", labelAlign:"left",  blockOffset:0},
							{type:"block", blockOffset:0, list: [
								{type:"select", name:"mstRateGrpCd", label:"변경전", labelWidth:80, width:220},
								{type:"newcolumn"},
								{type:"select", name:"subRateGrpCd", label:"변경후", labelWidth:80, width:220}
							]}
						],
				buttons : {
					 center : {
						btnSave: { label : "저장" },
						btnCancel: { label : "취소" }
					}
				},
				onButtonClick : function(btnId) {
					var form = this;

					switch(btnId){
						case "btnSave" :
								mvno.cmn.ajax(ROOT + "/sale/rateMgmt/insertGroupRelRateInfo.json", form, function(result) {
								mvno.ui.notify("NCMN0001");
								PAGE.GRID2.refresh();
								mvno.ui.closeWindowById(form, true);
							});

							break;

						case "btnCancel" :
							mvno.ui.closeWindowById(form, true);
							break;
					}
				},
				onValidateMore : function(data){
					if(data.rateGrpTypeCd == "") {
						this.pushError("data.mstRateGrpCd", "요금그룹", "변경전 요금그룹을 선택해주세요.");
					}

					if(data.mstRateGrpNm == "") {
						this.pushError("data.subRateGrpCd", "요금그룹", "변경후 요금그룹을 선택해주세요.");
					}

				}
			},
			
			FORM3 : {
				title : "",
				items : [
							{type:"settings", position:"label-left", labelAlign:"left",  blockOffset:0},
							{type:"block", blockOffset:0, list: [
								{type:"input", name:"mstRateGrpNm", label:"요금그룹", labelWidth:80, width:220},
							    {type:"hidden", name:"mstRateGrpCd"},
								{type:"newcolumn"},
								{type:"select", name:"rateCd", label:"요금상품", labelWidth:80, width:220}
							]}
						],
				buttons : {
					 center : {
						btnSave: { label : "저장" },
						btnCancel: { label : "취소" }
					}
				},
				onButtonClick : function(btnId) {
					var form = this;

					switch(btnId){
						case "btnSave" :
								mvno.cmn.ajax(ROOT + "/sale/rateMgmt/insertGroupMappingRateInfo.json", form, function(result) {
								mvno.ui.notify("NCMN0001");
								PAGE.GRID3.refresh();
								mvno.ui.closeWindowById(form, true);
							});

							break;

						case "btnCancel" :
							mvno.ui.closeWindowById(form, true);
							break;
					}
				},
				onValidateMore : function(data){
					if(data.rateGrpTypeCd == "") {
						this.pushError("data.rateCd", "요금상품", "요금상품을 선택해주세요.");
					}

				}
			}
			
	};
		
		
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : " ${breadCrumb.breadCrumb}",
			onOpen : function() {
				mvno.ui.createGrid("GRID1");
				mvno.ui.createGrid("GRID2");
				mvno.ui.createGrid("GRID3");
				
				PAGE.GRID1.list(ROOT + "/sale/rateMgmt/getRateGroupList.json");
				PAGE.GRID2.list(ROOT + "/sale/rateMgmt/getGroupRelRateList.json");
				
			}
		
	};
		
</script>