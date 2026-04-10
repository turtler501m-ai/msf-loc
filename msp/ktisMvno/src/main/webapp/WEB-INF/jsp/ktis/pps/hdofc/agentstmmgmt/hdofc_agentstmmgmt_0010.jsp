<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : hdofc_agentstmmgmt_0010.jsp
 * @Description : 정책관리
 * @Modification Information
 *
 *   수정일 			수정자				 수정내용
 *  -------		--------		---------------------------
 *  2017.05.01	김 웅				최초 생성
 *
 * author 김 웅
 * since 2017.05.01
 */
 %>
	<!-- 화면 배치 -->
	
	<div id="FORM1" class="section-search"></div>
	<div id="GRID1"></div>
	<div id="GRID2"></div>
	<div id="FORM11a" class="section-search"></div>
	<div id="FORM11b" class="section-search"></div>
	<div id="FORM12" class="section-search"></div>
			
	<!-- Script -->
	<script type="text/javascript">
	
	function setCurrentDate(form, name)
	{
		form.setItemValue(name, new Date());
	}
	
	function grid2Clear(){
		PAGE.GRID2.clearAll();
	}
	
	function grid2Display(gridId){
		$("#GRID2").hide();
		
		$("#"+gridId).show();
	}
	
	function gridRefresh(){
		PAGE.GRID2.refresh();
	}
	
	function form12ItemControl(type){
		if(type == "HIDE"){
			PAGE.FORM12.hideItem("codeALabel");
			PAGE.FORM12.hideItem("codeAInput");
			PAGE.FORM12.hideItem("codeASelect");
			PAGE.FORM12.hideItem("codeBLabel");
			PAGE.FORM12.hideItem("codeBInput");
			PAGE.FORM12.hideItem("codeBSelect");
			PAGE.FORM12.hideItem("codeCLabel");
			PAGE.FORM12.hideItem("codeCInput");
			PAGE.FORM12.hideItem("codeCSelect");
			PAGE.FORM12.hideItem("codeDLabel");
			PAGE.FORM12.hideItem("codeDInput");
			PAGE.FORM12.hideItem("codeDSelect");
			PAGE.FORM12.hideItem("codeELabel");
			PAGE.FORM12.hideItem("codeEInput");
			PAGE.FORM12.hideItem("codeESelect");
			PAGE.FORM12.hideItem("stmGubun");
			PAGE.FORM12.hideItem("stmSd");
		}else{
			PAGE.FORM12.showItem("codeALabel");
			PAGE.FORM12.showItem("codeAInput");
			PAGE.FORM12.showItem("codeASelect");
			PAGE.FORM12.showItem("codeBLabel");
			PAGE.FORM12.showItem("codeBInput");
			PAGE.FORM12.showItem("codeBSelect");
			PAGE.FORM12.showItem("codeCLabel");
			PAGE.FORM12.showItem("codeCInput");
			PAGE.FORM12.showItem("codeCSelect");
			PAGE.FORM12.showItem("codeDLabel");
			PAGE.FORM12.showItem("codeDInput");
			PAGE.FORM12.showItem("codeDSelect");
			PAGE.FORM12.showItem("codeELabel");
			PAGE.FORM12.showItem("codeEInput");
			PAGE.FORM12.showItem("codeESelect");
			PAGE.FORM12.showItem("stmGubun");
			PAGE.FORM12.showItem("stmSd");
		}
	}
	
	function grid2Reset(bool){
		PAGE.GRID2.clearAll();

		var cIds = [];
		cIds.push(PAGE.GRID2.getColIndexById('codeA'));
		cIds.push(PAGE.GRID2.getColIndexById('codeANm'));
		cIds.push(PAGE.GRID2.getColIndexById('codeB'));
		cIds.push(PAGE.GRID2.getColIndexById('codeBNm'));
		cIds.push(PAGE.GRID2.getColIndexById('codeC'));
		cIds.push(PAGE.GRID2.getColIndexById('codeCNm'));
		cIds.push(PAGE.GRID2.getColIndexById('codeD'));
		cIds.push(PAGE.GRID2.getColIndexById('codeDNm'));
		cIds.push(PAGE.GRID2.getColIndexById('codeE'));
		cIds.push(PAGE.GRID2.getColIndexById('codeENm'));
		cIds.push(PAGE.GRID2.getColIndexById('stmGubunNm'));
		cIds.push(PAGE.GRID2.getColIndexById('stmSd'));

		cIds.forEach((id) => {
			PAGE.GRID2.setColumnHidden(id, bool);
		});
	}

	function grid2Build(){
		var rowDataDtl;

		var fullRowWidth = 1200;
		var rowWidth = 120;

		if(PAGE.GRID2.getRowsNum() == 0){
			mvno.cmn.ajax(ROOT + "/pps/hdofc/agentstmmgmt/AgentStmCodeList.json", {dtlCd : rowData.dtlCd}, function(result) {
				rowDataDtl = result.result.data.rows[0];
			});
		} else {
			rowDataDtl = PAGE.GRID2.getRowData(1);
		}

		if(rowDataDtl.codeAType == "SELECT"){
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeA'), true); //codeA
			PAGE.GRID2.setHeaderCol(PAGE.GRID2.getColIndexById('codeANm'), rowDataDtl.codeAHeadNm);

			rowWidth = rowWidth + 120;
		}else if(rowDataDtl.codeAType == "INPUT"){
			PAGE.GRID2.setHeaderCol(PAGE.GRID2.getColIndexById('codeA'), rowDataDtl.codeAHeadNm);
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeANm'), true); //codeA

			rowWidth = rowWidth + 120;
		}else{
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeA'), true); //codeA
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeANm'), true); //codeA
		}

		if(rowDataDtl.codeBType == "SELECT"){
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeB'), true); //codeB
			PAGE.GRID2.setHeaderCol(PAGE.GRID2.getColIndexById('codeBNm'), rowDataDtl.codeBHeadNm);

			rowWidth = rowWidth + 120;
		}else if(rowDataDtl.codeBType == "INPUT"){
			PAGE.GRID2.setHeaderCol(PAGE.GRID2.getColIndexById('codeB'), rowDataDtl.codeBHeadNm);
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeBNm'), true); //codeB

			rowWidth = rowWidth + 120;
		}else{
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeB'), true); //codeB
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeBNm'), true); //codeB
		}

		if(rowDataDtl.codeCType == "SELECT"){
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeC'), true); //codeC
			PAGE.GRID2.setHeaderCol(PAGE.GRID2.getColIndexById('codeCNm'), rowDataDtl.codeCHeadNm);

			rowWidth = rowWidth + 120;
		}else if(rowDataDtl.codeCType == "INPUT"){
			PAGE.GRID2.setHeaderCol(PAGE.GRID2.getColIndexById('codeC'), rowDataDtl.codeCHeadNm);
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeCNm'), true); //codeC

			rowWidth = rowWidth + 120;
		}else{
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeC'), true); //codeC
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeCNm'), true); //codeC
		}

		if(rowDataDtl.codeDType == "SELECT"){
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeD'), true); //codeD
			PAGE.GRID2.setHeaderCol(PAGE.GRID2.getColIndexById('codeDNm'), rowDataDtl.codeDHeadNm);

			rowWidth = rowWidth + 120;
		}else if(rowDataDtl.codeDType == "INPUT"){
			PAGE.GRID2.setHeaderCol(PAGE.GRID2.getColIndexById('codeD'), rowDataDtl.codeDHeadNm);
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeDNm'), true); //codeD

			rowWidth = rowWidth + 120;
		}else{
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeD'), true); //codeD
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeDNm'), true); //codeD
		}

		if(rowDataDtl.codeEType == "SELECT"){
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeE'), true); //codeE
			PAGE.GRID2.setHeaderCol(PAGE.GRID2.getColIndexById('codeENm'), rowDataDtl.codeEHeadNm);

			rowWidth = rowWidth + 120;
		}else if(rowDataDtl.codeEType == "INPUT"){
			PAGE.GRID2.setHeaderCol(PAGE.GRID2.getColIndexById('codeE'), rowDataDtl.codeEHeadNm);
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeENm'), true); //codeE

			rowWidth = rowWidth + 120;
		}else{
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeE'), true); //codeE
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('codeENm'), true); //codeE
		}

		if(rowDataDtl.stmGubunNm == ""){
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('stmGubunNm'), true); //stmGubunNm
			PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById('stmSd'), true); //stmSd
		}else{
			PAGE.GRID2.setHeaderCol(PAGE.GRID2.getColIndexById('stmGubunNm'), "수수료구분"); //stmGubunNm
			PAGE.GRID2.setHeaderCol(PAGE.GRID2.getColIndexById('stmSd'), "지급수수료"); //stmSd

			rowWidth = rowWidth + 240;
		}

		PAGE.GRID2.setColWidth(PAGE.GRID2.getColIndexById('remark'), fullRowWidth - rowWidth);
	}
	
	var DHX = {
		FORM1 : {
			title : "",
			items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
							,{type : "select",label : "정책그룹",name : "searchGroupId",width:150 ,labelWidth:100 ,required:true}
					 ]},// 1row
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],
	
			onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnSearch":
						
						grid2Reset(true);
						
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/agentstmmgmt/AgentStmSetupList.json", PAGE.FORM1, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						
						break;
	
				}
	
			},
			 onValidateMore : function(data){
				 /*
				 if(data.startDt > data.endDt){
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
				}
				 */
			 },	
			onKeyUp : function(inp, ev, name, value)
			{
				
			}
		}, // FORM1 End
		GRID1 : {

			css : {
				height : "250px"
			},
			title : "조회결과",
			header : "정책그룹명,정책명,정책상세명,상세값,등록가능여부,수정가능여부,상세여부,비고,setupSeq,dtlCd,setupView",
			columnIds : "groupName,setupName,setupDtlName,setupCd,regFlag,setupFlag,dtlFlag,remark,setupSeq,dtlCd,setupView",
			widths : "120,180,180,80,80,80,80,350,0,0,0", //총 1500
			colAlign : "center,center,center,center,center,center,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str",
			paging : true,
			pageSize :200,
			pagingStyle :2,
			buttons : {
				hright : {
					//btnDnExcel : { label : "엑셀다운로드" }
				},
				right :{
					btnMod :{label :"수정"}
				}
			},
			onButtonClick : function(btnId) {

				switch (btnId) {
					case "btnMod":

						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						if(rowData.setupView == "YN"){
							
							mvno.ui.createForm("FORM11a");
							PAGE.FORM11a.clearChanged();
							
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : "PPS_0110" } , PAGE.FORM11a, "codeA");

							PAGE.FORM11a.setItemValue("dtlSeq",rowData.setupSeq);
							PAGE.FORM11a.setItemValue("codeA",rowData.setupCd);
							PAGE.FORM11a.setItemValue("remark",rowData.remark);
							PAGE.FORM11a.setItemValue("opCode","SETUP_MOD");
							PAGE.FORM11a.setItemLabel("setupCdLabel",rowData.setupDtlName);
							
							mvno.ui.popupWindowById("FORM11a", "정책수정", 800, 220, {
				                onClose: function(win) {
				                	if (! PAGE.FORM11a.isChanged()) return true;
				                	mvno.ui.confirm("CCMN0005", function(){
				                		win.closeForce();
				                	});
				                }
			                });
							
						}else if(rowData.setupView == "NUM"){
							
							mvno.ui.createForm("FORM11b");
							PAGE.FORM11b.clearChanged();

							PAGE.FORM11b.setItemValue("dtlSeq",rowData.setupSeq);
							PAGE.FORM11b.setItemValue("codeA",rowData.setupCd);
							PAGE.FORM11b.setItemValue("remark",rowData.remark);
							PAGE.FORM11b.setItemValue("opCode","SETUP_MOD");
							PAGE.FORM11b.setItemLabel("setupCdLabel",rowData.setupDtlName);
							
							mvno.ui.popupWindowById("FORM11b", "정책수정", 800, 220, {
				                onClose: function(win) {
				                	if (! PAGE.FORM11b.isChanged()) return true;
				                	mvno.ui.confirm("CCMN0005", function(){
				                		win.closeForce();
				                	});
				                }
			                });

						}else{
							mvno.ui.notify("수정이 불가능합니다.");
							return;
						}

						break;
				}
			},
			onRowSelect : function(rowId) {
				
				var rowData = this.getSelectedRowData();
				
				if(rowData.dtlCd == ""){
					grid2Reset(true);
				}else{
					var tmpIndex;
					grid2Reset(false);
					PAGE.GRID2.list(ROOT + "/pps/hdofc/agentstmmgmt/AgentStmSetupDtlList.json",rowData,{callback:grid2Build});
				}
			}
		}, // GRID1 End
		GRID2 : {

			css : {
				height : "210px"
			},
			title : "조회결과",
			header : "label1,label1Val,label2,label2Val,label3,label3Val,label4,label4Val,label5,label5Val,label6,label6Val,삭제가능여부,비고,dtlSeq,dtlCd,codeAType,codeACombo,codeAHeadNm,codeAModFlag,codeBType,codeBCombo,codeBHeadNm,codeBModFlag,codeCType,codeCCombo,codeCHeadNm,codeCModFlag,codeDType,codeDCombo,codeDHeadNm,codeDModFlag,codeEType,codeECombo,codeEHeadNm,codeEModFlag,stmCombo,delFlag",
			columnIds : "codeA,codeANm,codeB,codeBNm,codeC,codeCNm,codeD,codeDNm,codeE,codeENm,stmGubunNm,stmSd,delFlag,remark,dtlSeq,dtlCd,codeAType,codeACombo,codeAHeadNm,codeAModFlag,codeBType,codeBCombo,codeBHeadNm,codeBModFlag,codeCType,codeCCombo,codeCHeadNm,codeCModFlag,codeDType,codeDCombo,codeDHeadNm,codeDModFlag,codeEType,codeECombo,codeEHeadNm,codeEModFlag,stmCombo,delFlag",
			widths : "120,120,120,120,120,120,120,120,120,120,100,100,100,345,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0", //총 1500
			colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
			paging : true,
			pageSize :200,
			pagingStyle :2,
			buttons : {
				hright : {
					//btnDnExcel : { label : "엑셀다운로드" }
				},
				right :{
					btnReg :{label :"등록"},
					btnMod :{label :"수정"},
					btnDel :{label :"삭제"}
				}
			},
			onButtonClick : function(btnId) {

				switch (btnId) {
					case "btnMod":

						var rowData = PAGE.GRID2.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						var tmpIndex;
						
						mvno.ui.createForm("FORM12");
						PAGE.FORM12.clearChanged();

						form12ItemControl("HIDE");
						PAGE.FORM12.setItemValue("opCode", "DTL_MOD");
						PAGE.FORM12.setItemValue("remark", rowData.remark);
						PAGE.FORM12.setItemValue("dtlCd", rowData.dtlCd);
						PAGE.FORM12.setItemValue("dtlSeq", rowData.dtlSeq);
						PAGE.FORM12.setItemValue("codeAType", rowData.codeAType);
						PAGE.FORM12.setItemValue("codeBType", rowData.codeBType);
						PAGE.FORM12.setItemValue("codeCType", rowData.codeCType);
						PAGE.FORM12.setItemValue("codeDType", rowData.codeDType);
						PAGE.FORM12.setItemValue("codeEType", rowData.codeEType);
						PAGE.FORM12.setItemValue("codeAFlag", rowData.codeAModFlag);
						PAGE.FORM12.setItemValue("codeBFlag", rowData.codeBModFlag);
						PAGE.FORM12.setItemValue("codeCFlag", rowData.codeCModFlag);
						PAGE.FORM12.setItemValue("codeDFlag", rowData.codeDModFlag);
						PAGE.FORM12.setItemValue("codeEFlag", rowData.codeEModFlag);
						
						if(rowData.stmCombo != ""){
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : rowData.stmCombo } , PAGE.FORM12, "stmGubun");
							PAGE.FORM12.showItem("stmGubun");
							PAGE.FORM12.setItemValue("stmGubun", rowData.stmGubun);
							PAGE.FORM12.showItem("stmSd");
							PAGE.FORM12.setItemValue("stmSd", rowData.stmSd);
							
							PAGE.FORM12.setItemValue("stmFlag", "Y");
						}else{
							PAGE.FORM12.setItemValue("stmFlag", "N");
						}
						
						if(rowData.codeAModFlag == "Y"){
							PAGE.FORM12.showItem("codeALabel");
							
							
							if(rowData.codeAType == "SELECT"){
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : rowData.codeACombo } , PAGE.FORM12, "codeASelect");
								PAGE.FORM12.setItemLabel("codeALabel", rowData.codeAHeadNm);
								PAGE.FORM12.showItem("codeASelect");
								PAGE.FORM12.setItemValue("codeASelect", rowData.codeA);
							}else if(rowData.codeAType == "INPUT"){
								PAGE.FORM12.setItemLabel("codeALabel", rowData.codeANm);
								PAGE.FORM12.showItem("codeAInput");
								PAGE.FORM12.setItemValue("codeAInput", rowData.codeA);
							}
						}
						
						if(rowData.codeBModFlag == "Y"){
							PAGE.FORM12.showItem("codeBLabel");
							
							if(rowData.codeBType == "SELECT"){
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : rowData.codeBCombo } , PAGE.FORM12, "codeBSelect");
								PAGE.FORM12.setItemLabel("codeBLabel", rowData.codeBHeadNm);
								PAGE.FORM12.showItem("codeBSelect");
								PAGE.FORM12.setItemValue("codeBSelect", rowData.codeB);
							}else if(rowData.codeBType == "INPUT"){
								PAGE.FORM12.setItemLabel("codeBLabel", rowData.codeBNm);
								PAGE.FORM12.showItem("codeBInput");
								PAGE.FORM12.setItemValue("codeBInput", rowData.codeB);
							}
						}
						
						if(rowData.codeCModFlag == "Y"){
							PAGE.FORM12.showItem("codeCLabel");
							
							if(rowData.codeCType == "SELECT"){
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : rowData.codeCCombo } , PAGE.FORM12, "codeCSelect");
								PAGE.FORM12.setItemLabel("codeCLabel", rowData.codeCHeadNm);
								PAGE.FORM12.showItem("codeCSelect");
								PAGE.FORM12.setItemValue("codeCSelect", rowData.codeC);
							}else if(rowData.codeCType == "INPUT"){
								PAGE.FORM12.setItemLabel("codeCLabel", rowData.codeCNm);
								PAGE.FORM12.showItem("codeCInput");
								PAGE.FORM12.setItemValue("codeCInput", rowData.codeC);
							}
						}
						
						if(rowData.codeDModFlag == "Y"){
							PAGE.FORM12.showItem("codeDLabel");
							
							if(rowData.codeDType == "SELECT"){
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : rowData.codeDCombo } , PAGE.FORM12, "codeDSelect");
								PAGE.FORM12.setItemLabel("codeDLabel", rowData.codeDHeadNm);
								PAGE.FORM12.showItem("codeDSelect");
								PAGE.FORM12.setItemValue("codeDSelect", rowData.codeD);
							}else if(rowData.codeDType == "INPUT"){
								PAGE.FORM12.setItemLabel("codeDLabel", rowData.codeDNm);
								PAGE.FORM12.showItem("codeDInput");
								PAGE.FORM12.setItemValue("codeDInput", rowData.codeD);
							}
						}
						
						if(rowData.codeEModFlag == "Y"){
							PAGE.FORM12.showItem("codeELabel");
							
							if(rowData.codeEType == "SELECT"){
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : rowData.codeECombo } , PAGE.FORM12, "codeESelect");
								PAGE.FORM12.setItemLabel("codeELabel", rowData.codeEHeadNm);
								PAGE.FORM12.showItem("codeESelect");
								PAGE.FORM12.setItemValue("codeESelect", rowData.codeE);
							}else if(rowData.codeEType == "INPUT"){
								PAGE.FORM12.setItemLabel("codeELabel", rowData.codeENm);
								PAGE.FORM12.showItem("codeEInput");
								PAGE.FORM12.setItemValue("codeEInput", rowData.codeE);
							}
						}
						
						mvno.ui.popupWindowById("FORM12", "정책등록", 800, 320, {
			                onClose: function(win) {
			                	if (! PAGE.FORM12.isChanged()) return true;
			                	mvno.ui.confirm("CCMN0005", function(){
			                		win.closeForce();
			                	});
			                }
		                });
						
						break;
						
					case "btnReg":

						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData.regFlag == ''){
							mvno.ui.notify("등록이 불가능합니다.");
							return;
						}
						
						var tmpIndex;
						
						mvno.ui.createForm("FORM12");
						PAGE.FORM12.clearChanged();

						form12ItemControl("HIDE");
						PAGE.FORM12.setItemValue("opCode", "DTL_REG");
						PAGE.FORM12.setItemValue("dtlCd", rowData.dtlCd);
						PAGE.FORM12.setItemValue("remark", "");
						PAGE.FORM12.setItemValue("dtlSeq", "");
						
						PAGE.FORM12.setItemValue("codeAInput", "");
						PAGE.FORM12.setItemValue("codeBInput", "");
						PAGE.FORM12.setItemValue("codeCInput", "");
						PAGE.FORM12.setItemValue("codeDInput", "");
						PAGE.FORM12.setItemValue("codeEInput", "");
						PAGE.FORM12.setItemValue("sdSd", "");

						PAGE.FORM12.setItemValue("dtlSeq", "");
						PAGE.FORM12.setItemValue("codeAType", "");
						PAGE.FORM12.setItemValue("codeBType", "");
						PAGE.FORM12.setItemValue("codeCType", "");
						PAGE.FORM12.setItemValue("codeDType", "");
						PAGE.FORM12.setItemValue("codeEType", "");
						PAGE.FORM12.setItemValue("codeAFlag", "N");
						PAGE.FORM12.setItemValue("codeBFlag", "N");
						PAGE.FORM12.setItemValue("codeCFlag", "N");
						PAGE.FORM12.setItemValue("codeDFlag", "N");
						PAGE.FORM12.setItemValue("codeEFlag", "N");
						
						mvno.cmn.ajax(
							ROOT + "/pps/hdofc/agentstmmgmt/AgentStmCodeList.json",
							{
								dtlCd : rowData.dtlCd 
								
							},
							function(result) {
								var result = result.result;
								var data = result.data;
								
								PAGE.FORM12.setItemValue("codeAType", data.rows[0].codeAType);
								PAGE.FORM12.setItemValue("codeBType", data.rows[0].codeBType);
								PAGE.FORM12.setItemValue("codeCType", data.rows[0].codeCType);
								PAGE.FORM12.setItemValue("codeDType", data.rows[0].codeDType);
								PAGE.FORM12.setItemValue("codeEType", data.rows[0].codeEType);
								
								if(data.rows[0].stmCombo != null && data.rows[0].stmCombo != ""){
									mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : data.rows[0].stmCombo } , PAGE.FORM12, "stmGubun");
									PAGE.FORM12.showItem("stmGubun");
									PAGE.FORM12.showItem("stmSd");
									PAGE.FORM12.setItemValue("stmFlag", "Y");
								}else{
									PAGE.FORM12.setItemValue("stmFlag", "N");
								}
								
								if(data.rows[0].codeAType != null && data.rows[0].codeAType != ""){
									PAGE.FORM12.showItem("codeALabel");
									PAGE.FORM12.setItemLabel("codeALabel", data.rows[0].codeAHeadNm);
									PAGE.FORM12.setItemValue("codeAFlag", "Y");
									
									if(data.rows[0].codeAType == "SELECT"){
										mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : data.rows[0].codeACombo } , PAGE.FORM12, "codeASelect");
										PAGE.FORM12.showItem("codeASelect");
									}else if(data.rows[0].codeAType == "INPUT"){
										PAGE.FORM12.showItem("codeAInput");
									}
								}
								
								if(data.rows[0].codeBType != null && data.rows[0].codeBType != ""){
									PAGE.FORM12.showItem("codeBLabel");
									PAGE.FORM12.setItemLabel("codeBLabel", data.rows[0].codeBHeadNm);
									PAGE.FORM12.setItemValue("codeBFlag", "Y");
									
									if(data.rows[0].codeBType == "SELECT"){
										mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : data.rows[0].codeBCombo } , PAGE.FORM12, "codeBSelect");
										PAGE.FORM12.showItem("codeBSelect");
									}else if(data.rows[0].codeBType == "INPUT"){
										PAGE.FORM12.showItem("codeBInput");
									}
								}

								if(data.rows[0].codeCType != null && data.rows[0].codeCType != ""){
									PAGE.FORM12.showItem("codeCLabel");
									PAGE.FORM12.setItemLabel("codeCLabel", data.rows[0].codeCHeadNm);
									PAGE.FORM12.setItemValue("codeCFlag", "Y");
									
									if(data.rows[0].codeCType == "SELECT"){
										mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : data.rows[0].codeCCombo } , PAGE.FORM12, "codeCSelect");
										PAGE.FORM12.showItem("codeCSelect");
									}else if(data.rows[0].codeCType == "INPUT"){
										PAGE.FORM12.showItem("codeCInput");
									}
								}
								
								if(data.rows[0].codeDType != null && data.rows[0].codeDType != ""){
									PAGE.FORM12.showItem("codeDLabel");
									PAGE.FORM12.setItemLabel("codeDLabel", data.rows[0].codeDHeadNm);
									PAGE.FORM12.setItemValue("codeDFlag", "Y");
									
									if(data.rows[0].codeDType == "SELECT"){
										mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpscodeComboList.json",{searchLovCd : data.rows[0].codeDCombo } , PAGE.FORM12, "codeDSelect");
										PAGE.FORM12.showItem("codeDSelect");
									}else if(data.rows[0].codeDType == "INPUT"){
										PAGE.FORM12.showItem("codeDInput");
									}
								}
								
								if(data.rows[0].codeEType != null && data.rows[0].codeEType != ""){
									PAGE.FORM12.showItem("codeELabel");
									PAGE.FORM12.setItemLabel("codeELabel", data.rows[0].codeEHeadNm);
									PAGE.FORM12.setItemValue("codeEFlag", "Y");
									
									if(data.rows[0].codeEType == "SELECT"){
										mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpscodeComboList.json",{searchLovCd : data.rows[0].codeECombo } , PAGE.FORM12, "codeESelect");
										PAGE.FORM12.showItem("codeESelect");
									}else if(data.rows[0].codeEType == "INPUT"){
										PAGE.FORM12.showItem("codeEInput");
									}
								}
								
								mvno.ui.popupWindowById("FORM12", "정책등록", 800, 320, {
					                onClose: function(win) {
					                	if (! PAGE.FORM12.isChanged()) return true;
					                	mvno.ui.confirm("CCMN0005", function(){
					                		win.closeForce();
					                	});
					                }
				                });
								
								
								
				        });	
						
						break;
						
					case "btnDel":

						var rowData = PAGE.GRID2.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						if(rowData.delFlag != "Y"){
							mvno.ui.notify("삭제가 불가능합니다.");
							return;
						}
						
						mvno.ui.confirm("CCMN0005", function(){
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmSetupChgProc.json";
							mvno.cmn.ajax(url, 
							{
								opCode : "DTL_DEL",
								dtlSeq : rowData.dtlSeq
											
							}, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.notify(msg);
								if(retCode == "0000") {
									gridRefresh();
								}
								
							});
	                	});

						break;
				}
			}
		}, // GRID2 End
		FORM11a: {
			title : "정책수정"
			,items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						,{type : "select",label : "",name : "codeA",width:100 ,labelWidth:100 ,required:true}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "비고",name : "remark",width:400 ,labelWidth:150}
					 ]},// 2row
					 {type : 'hidden', name: "dtlSeq", value:""},
					 {type : 'hidden', name: "opCode", value:""}
				]
			,buttons: {
			   	center : {
			   		btnSetupUpdate : { label : "수정" }, 
		    		btnSetupClose : { label : "닫기" }
				}
			}
			,onKeyUp : function(inp, ev, name, value)
			{
			}
			,onChange : function (name, value)
			{
				
            }
			,onButtonClick : function(btnId) {
				    	
				var form = this;
					switch (btnId) {
						case "btnSetupUpdate":
							var data = PAGE.FORM11a.getFormData(true);
							
							if(!data.codeA){
				        		mvno.ui.notify("변경할 값을 선택해 주세요.");
				        		return;
				
				        	}
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmSetupChgProc.json";
							mvno.cmn.ajax(url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.notify(msg);
								if(retCode == "0000") {
									mvno.ui.closeWindowById(form, true);  
									PAGE.FORM11a.clear();
									PAGE.GRID1.refresh();
								}
								
							});
							
							break;
						case "btnSetupClose":	
							mvno.ui.closeWindowById("FORM11a", true);
			                break;
						}
			}
		}, // FORM11a End
		FORM11b: {
			title : "정책수정"
			,items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"",name : "setupCdLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						,{type : "input",label : "",name : "codeA",width:100 ,labelWidth:100, validate:"ValidNumeric", numberFormat: "0,000,000,000"}
					 ]}, // 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "비고",name : "remark",width:400 ,labelWidth:150}
					 ]},// 2row
					 {type : 'hidden', name: "dtlSeq", value:""},
					 {type : 'hidden', name: "opCode", value:""}
				]
			,buttons: {
			   	center : {
			   		btnSetupUpdate : { label : "수정" }, 
			   		btnSetupClose : { label : "닫기" }
				}
			}
			,onKeyUp : function(inp, ev, name, value)
			{
			}
			,onChange : function (name, value)
			{
				
            }
			,onButtonClick : function(btnId) {
				    	
				var form = this;
					switch (btnId) {
						case "btnSetupUpdate":
							var data = PAGE.FORM11b.getFormData(true);
							
							if(!data.codeA){
				        		mvno.ui.notify("변경할 값을 입력해 주세요.");
				        		return;
				
				        	}
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmSetupChgProc.json";
							mvno.cmn.ajax(url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.notify(msg);
								if(retCode == "0000") {
									mvno.ui.closeWindowById(form, true);  
									PAGE.FORM11b.clear();
									PAGE.GRID1.refresh();
								}
								
							});

							break;
						case "btnSetupClose":	
							mvno.ui.closeWindowById("FORM11b", true);
			                break;
						}
			}
		}, // FORM11b End
		FORM12: {
			title : "정책설정"
			,items : [ 	
					{type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"",name : "codeALabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type : "input",label : "",name : "codeAInput",width:100 ,labelWidth:150, validate:"ValidNumeric", numberFormat: "0,000,000,000"}
						 ,{type:"newcolumn"}
						 ,{type : "select",label : "",name : "codeASelect",width:100 ,labelWidth:150 ,required:true}
					 ]}, // 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"",name : "codeBLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type : "input",label : "",name : "codeBInput",width:100 ,labelWidth:150, validate:"ValidNumeric", numberFormat: "0,000,000,000"}
						 ,{type:"newcolumn"}
						 ,{type : "select",label : "",name : "codeBSelect",width:100 ,labelWidth:150 ,required:true}
					 ]}, // 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"",name : "codeCLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type : "input",label : "",name : "codeCInput",width:100 ,labelWidth:150, validate:"ValidNumeric", numberFormat: "0,000,000,000"}
						 ,{type:"newcolumn"}
						 ,{type : "select",label : "",name : "codeCSelect",width:100 ,labelWidth:150 ,required:true}
					 ]}, // 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"",name : "codeDLabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type : "input",label : "",name : "codeDInput",width:100 ,labelWidth:150, validate:"ValidNumeric", numberFormat: "0,000,000,000"}
						 ,{type:"newcolumn"}
						 ,{type : "select",label : "",name : "codeDSelect",width:100 ,labelWidth:150 ,required:true}
					 ]}, // 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "label",label:"",name : "codeELabel",width:300 ,labelWidth:150}
						 ,{type:"newcolumn"}
						 ,{type : "input",label : "",name : "codeEInput",width:100 ,labelWidth:150, validate:"ValidNumeric", numberFormat: "0,000,000,000"}
						 ,{type:"newcolumn"}
						 ,{type : "select",label : "",name : "codeESelect",width:100 ,labelWidth:150 ,required:true}
					 ]}, // 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "select",label : "수수료구분",name : "stmGubun",width:100 ,labelWidth:150 ,required:true}
					 ]}, // 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "지급수수료",name : "stmSd",width:100 ,labelWidth:150, validate:"ValidNumeric", numberFormat: "0,000,000,000.0"}
					 ]}, // 2row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0}
						 ,{type : "input",label : "비고",name : "remark",width:400 ,labelWidth:150}
					 ]},// 3row
					 {type : 'hidden', name: "dtlCd", value:""},
					 {type : 'hidden', name: "dtlSeq", value:""},
					 {type : 'hidden', name: "codeAType", value:""},
					 {type : 'hidden', name: "codeBType", value:""},
					 {type : 'hidden', name: "codeCType", value:""},
					 {type : 'hidden', name: "codeDType", value:""},
					 {type : 'hidden', name: "codeEType", value:""},
					 {type : 'hidden', name: "codeAFlag", value:""},
					 {type : 'hidden', name: "codeBFlag", value:""},
					 {type : 'hidden', name: "codeCFlag", value:""},
					 {type : 'hidden', name: "codeDFlag", value:""},
					 {type : 'hidden', name: "codeEFlag", value:""},
					 {type : 'hidden', name: "stmFlag", value:""},
					 {type : 'hidden', name: "opCode", value:""}
				]
			,buttons: {
			   	center : {
			   		btnSetupUpdate : { label : "수정" }, 
			   		btnSetupClose : { label : "닫기" }
				}
			}
			,onKeyUp : function(inp, ev, name, value)
			{
			}
			,onChange : function (name, value)
			{
				
            }
			,onButtonClick : function(btnId) {
				    	
				var form = this;
					switch (btnId) {
						case "btnSetupUpdate":
							var data = PAGE.FORM12.getFormData(true);
							
							if(data.codeAFlag == "Y" && data.codeAType == "INPUT" && data.codeA == ""){
				        		mvno.ui.notify(PAGE.FORM12.getItemLabel("codeALabel") + "을/를 입력하세요.");
				        		return;
				
				        	}
							
							if(data.codeBFlag == "Y" && data.codeBType == "INPUT" && data.codeB == ""){
				        		mvno.ui.notify(PAGE.FORM12.getItemLabel("codeBLabel") + "을/를 입력하세요.");
				        		return;
				
				        	}
							
							if(data.codeCFlag == "Y" && data.codeCType == "INPUT" && data.codeC == ""){
				        		mvno.ui.notify(PAGE.FORM12.getItemLabel("codeCLabel") + "을/를 입력하세요.");
				        		return;
				
				        	}
							
							if(data.codeDFlag == "Y" && data.codeDType == "INPUT" && data.codeD == ""){
				        		mvno.ui.notify(PAGE.FORM12.getItemLabel("codeDLabel") + "을/를 입력하세요.");
				        		return;
				
				        	}
							
							if(data.codeEFlag == "Y" && data.codeEType == "INPUT" && data.codeE == ""){
				        		mvno.ui.notify(PAGE.FORM12.getItemLabel("codeELabel") + "을/를 입력하세요.");
				        		return;
				
				        	}
							
							if(data.stmFlag == "Y" && !data.stmSd){
				        		mvno.ui.notify("수수료를 입력하세요.");
				        		return;
				
				        	}
							
							var url = ROOT + "/pps/hdofc/agentstmmgmt/ppsStmSetupChgProc.json";
							mvno.cmn.ajax(url, data, function(results) {
								var result = results.result;
								var retCode = result.retCode;
								var msg = result.retMsg;
								mvno.ui.notify(msg);
								if(retCode == "0000") {
									mvno.ui.closeWindowById(form, true);  
									PAGE.FORM12.clear();
									gridRefresh();
								}
								
							});

							break;
						case "btnSetupClose":	
							mvno.ui.closeWindowById("FORM12", true);
			                break;
						}
			}
		}  // FORM12 End

	};
	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
			buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {

				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.ui.createGrid("GRID2");
				
				/*
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("setupSeq" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("dtlCd" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("dtlView" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("setupView" ) ,true);
				*/
				
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/agentstmmgmt/PpsAgentStmGroupList.json",null , PAGE.FORM1, "searchGroupId");
				
				PAGE.GRID1.list(ROOT + "/pps/hdofc/agentstmmgmt/AgentStmSetupList.json", PAGE.FORM1, {
					callback : function () {
						grid2Reset(true);
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
				});

				
			}

		};
		
		
	</script>