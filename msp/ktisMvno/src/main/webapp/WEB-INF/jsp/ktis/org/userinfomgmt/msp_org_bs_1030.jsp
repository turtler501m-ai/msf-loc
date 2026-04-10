<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1030.jsp
	 * @Description : 해지자관리 - ID 관리 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2017.06.07 TREXSHIN 최초생성
	 * @author : TREXSHIN
	 * @Create Date : 2017.06.07.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>


<!-- Script -->
<script type="text/javascript">
	
	var strtDt = new Date(new Date().setDate(new Date().getDate() - 10)).format("yyyymmdd");
	var endDt = new Date().format("yyyymmdd");
	var addDt = new Date(new Date().setDate(new Date().getDate() + 30)).format("yyyymmdd");
	
	var DHX = {
			//------------------------------------------------------------
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
					{type: "block", list: [
						 {type:"calendar", name:"searchStartDate", value:strtDt, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", validate: "NotEmpty", label:"조회기간", offsetLeft:10, width:100, readonly: true}
						,{type:"newcolumn"}
						,{type:"calendar", name:"searchEndDate", value:endDt, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", validate: "NotEmpty", label:"~", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100, readonly: true}
						,{type:"newcolumn"}
						,{type:"select", name:"searchCode", label:"검색구분", options:[{text:"- 전체 -", value:"", selected:true}, {text:"ID", value:"ID"}, {text:"이름", value:"NAME"}], width:100, offsetLeft:30}
						,{type:"newcolumn"}
						,{type:"input", width:200, name:"searchVal"}
					]},
					{type:"newcolumn", offset:130},
					{type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}
				],
				onButtonClick : function(btnId) {
					var form = this;
					
					switch (btnId) {
						case "btnSearch":
							
							if(form.getItemValue("searchCode") != "" && form.getItemValue("searchVal") == "") {
								mvno.ui.alert("검색어를 입력해 주십시오.");
								this.setItemFocus("searchVal");
								return;
							}
							
							PAGE.GRID1.clearSelection();
							PAGE.FORM2.clear();
							PAGE.FORM2.setItemValue("usgStrtDt", endDt);
							PAGE.FORM2.setItemValue("usgEndDt", addDt);
							
							PAGE.GRID1.list(ROOT + "/org/userinfomgmt/canUserMgmtList.json", form);
							break;
							
					}
				
				},
				onValidateMore : function (data) {
					if(data.searchStartDate > data.searchEndDate) {
						this.pushError("data.searchStartDate","등록일자",["ICMN0004"]);
					}
				},
				onChange : function(name, value) {
					
					var form = this;
					
					if(name == "searchCode") {
						form.setItemValue("searchVal","");
					}
				}
			},
			
			
			// ----------------------------------------
			//사용자리스트
			GRID1 : {
				css : {
					height : "460px"
				},
				title : "조회결과",
				header : "ID,이름,시작일,종료일,사유,등록일,usrId",
				columnIds : "usrIdMsk,usrNm,usgStrtDt,usgEndDt,reason,regDt,usrId",
 				colAlign : "left,left,center,center,left,center,center",
 				colTypes : "ro,ro,roXd,roXd,ro,roXd,ro",
 				colSorting : "str,str,str,str,str,str,str",
 				widths : "120,170,120,120,250,120,0",
 				hiddenColumns:[6],
 				paging: true,
 				showPagingAreaOnInit: true,
 				pageSize:20,
 				onRowSelect : function(rowId, celInd) {
 					var rowData = this.getRowData(rowId);
 					
 					PAGE.FORM2.setFormData(rowData);
 				}
			},
			
			// --------------------------------------------------------------------------
			//사용자 등록화면
			FORM2 : {
				title : "",
				items : [
					{type: "settings", position: "label-left", labelWidth: "80", inputWidth: "auto"},
					{type: "hidden", name:"saveType"},
					{type: "block", blockOffset: 0, offsetLeft: 30, list: [
						{type:"input", name:"usrIdMsk", label:"사용자ID", width:130, value:"", readonly:true, required:true, maxLength:10, validate: "ValidAplhaNumeric"},
						{type:"hidden", name:"usrId"},
						{type:"newcolumn"},
						{type:"button", value:"찾기", name:"btnFindUser"} ,
						{type:"newcolumn"},
						{type:"input", name:"usrNm", width:140, value:"", disabled:true},
						{type:"newcolumn"},
						{type:"calendar", name:"usgStrtDt", label:"사용기간", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", value:endDt, calendarPosition:"bottom", width:120, offsetLeft:40, required:true},
						{type:"newcolumn"},
						{type:"calendar", name:"usgEndDt", label:"~", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", value:addDt, calendarPosition:"bottom", labelWidth:10, offsetLeft:5, width:120, required:true}
					]},
					{type: "block", blockOffset: 0, offsetLeft: 30, list: [
						{type:"input", name:"reason", label:"사유", width:712, value:"", required:true, maxLength:100}
					]}
				],
				
				buttons : {
					left : {
						 btnInit : { label : "초기화" }
					},
					right : {
						 btnReg : { label : "등록" }
						,btnMod : { label : "수정" }
					}
				},
				
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)
					
					switch (btnId) {
						case "btnInit":
							PAGE.GRID1.clearSelection();
							PAGE.FORM2.clear();
							form.setItemValue("usgStrtDt", endDt);
							form.setItemValue("usgEndDt", addDt);
							break;
							
						case "btnFindUser":
							mvno.ui.popupWindow(ROOT+"/org/userinfomgmt/searchUserInfo.do?attcSctnCd=10", "사용자찾기", 860, 620, {
								param : {
									callback : function(result) {
										form.setItemValue("usrIdMsk", result.usrId);
										form.setItemValue("usrId", result.usrId);
										form.setItemValue("usrNm", result.usrNm);
									}
								}
							});
   							break;
							
						case "btnReg" :
							
							var flag = false;
							form.setItemValue("saveType", "I");
							
							mvno.cmn.ajax(ROOT + "/org/userinfomgmt/checkCanUser.json", form, function(result) {
								if(result.result.checkCnt > 0) {
									mvno.ui.confirm("이미 등록된 ID입니다.\n입력하신 내용으로 수정하시겠습니까?", function() {
										form.setItemValue("saveType", "U");
										mvno.cmn.ajax(ROOT + "/org/userinfomgmt/saveCanUser.json", form, function(result2) {
											mvno.ui.notify("NCMN0002");
											PAGE.GRID1.refresh();
											PAGE.FORM2.clear();
										});
									});
								} else {
									mvno.cmn.ajax(ROOT + "/org/userinfomgmt/saveCanUser.json", form, function(result2) {
										mvno.ui.notify("NCMN0001");
										PAGE.GRID1.refresh();
										PAGE.FORM2.clear();
									});
								}
								
							});
							
							break;
							
						case "btnMod" :
							if(PAGE.GRID1.getSelectedRowData() != null) {
								
								form.setItemValue("saveType", "U");
								
								mvno.cmn.ajax(ROOT + "/org/userinfomgmt/saveCanUser.json", form, function(result) {
									mvno.ui.notify("NCMN0002");
									PAGE.GRID1.refresh();
									PAGE.FORM2.clear();
								});
							} else {
								mvno.ui.notify("ECMN0002");
							}
							break;
					}
					
				},
				
				onValidateMore : function (data) {
					if(data.usgStrtDt > data.usgEndDt) {
						this.pushError("data.usgStrtDt","시작일자","종료일자보다 작아야합니다.");
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
				mvno.ui.createForm("FORM2");
				
			}
	};
	
	</script>