<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : msp_ptnr_mgmt_2001.jsp
 * @Description : 단체보험관리
 * @
 * @ 수정일     수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2018.08.01 이상직 최초
 * @author : 
 * @Create Date : 
 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search" ></div>
<!-- 그리드영역 -->
<div id="GRID1"></div>
<!-- 상세화면 -->
<div id="GROUP1" style="display:none;">
	<div id="FORM2" class="section-box"></div>
	<div id="GRID2" class="section-full"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	
	var today = new Date().format("yyyymmdd");
	
	var DHX = {
		// 검색
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0 }
				,{type:"block", list:[
					{type:"calendar", name:"stdrDt", label:"기준일자", width:100, dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', calendarPosition:"bottom", readonly:true, required:true}
					,{type:"newcolumn"}
					,{type:"select", name:"grpInsrCd", label:"단체보험", width:100, offsetLeft:20}
					,{type:"newcolumn"}
					,{type:"checkbox", name:"usgYn", label:"사용여부", value:"Y", offsetLeft:20, checked:true}
				]}
				,{ type : "button",name : "btnSearch",value : "조회", className:"btn-search1" } 
			]
			, onButtonClick : function(btnId) {
				
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/ptnr/grpinsrmgmt/getGrpInsrMgmtList.json", form);
						break;
				}
			}
		}
		// 목록조회
		, GRID1 : {
			css : { 
				height : "560px"
			}
			,title : "단체보험관리"
			,header : "단체보험명,시작일자,종료일자,보험명,사용여부,등록자,등록일시,수정자,수정일시"
			,columnIds : "grpInsrNm,strtDt,endDt,insrNm,usgYn,regstNm,regstDt,rvisnNm,rvisnDt"
			,widths : "200,80,80,200,80,80,150,80,150"
			,colAlign : "left,center,center,left,center,center,center,center,center"
			,colTypes : "ro,roXd,roXd,ro,ro,ro,roXdt,ro,roXdt"
			,colSorting : "str,str,str,str,str,str,str,str,str"
			,paging : true
			,pageSize : 20
			,showPagingAreaOnInit: true
			,buttons : {
				right : {
					btnReg : { label : "등록" }
					, btnMod : { label : "수정" }
				}
			}
			,checkSelectedButtons : ["btnMod"]
			,onRowDblClicked : function(rowId, celInd) {
				this.callEvent("onButtonClick", ['btnMod']);
			}
			,onButtonClick : function(btnId) {
				var grid = this;
				switch (btnId) {
					case "btnReg" :
						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID2");

						PAGE.FORM2.clear();
						PAGE.GRID2.clearAll();
						
						PAGE.GRID2.list(ROOT + "/ptnr/grpinsrmgmt/getGrpInsrCdList.json", "");
						
						PAGE.FORM2.setFormData(true);
						PAGE.FORM2.enableItem("grpInsrNm");
						PAGE.FORM2.enableItem("strtDt");
						PAGE.FORM2.enableItem("endDt");
						PAGE.FORM2.setItemValue("strtDt", today);
						PAGE.GRID2.setEditable(true);
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "단체보험등록", 450, 450, {
							onClose: function(win) {
								if (!PAGE.FORM2.isChanged()) {
									return true;
								}
							}
						});
						
						break;
					case "btnMod" :
						var rowData = this.getSelectedRowData();
						
						if(rowData == null) {
							mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
							return;
						}
						
						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID2");
						
						PAGE.FORM2.clear();
						PAGE.GRID2.clearAll();
						
						PAGE.FORM2.setFormData(rowData);
						PAGE.GRID2.list(ROOT + "/ptnr/grpinsrmgmt/getGrpInsrCdList.json", rowData);
						
						PAGE.FORM2.disableItem("grpInsrNm");
						PAGE.FORM2.disableItem("strtDt");
						PAGE.FORM2.disableItem("endDt");
						PAGE.GRID2.setEditable(false);
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "단체보험수정", 450, 450, {
							onClose: function(win) {
								if (!PAGE.FORM2.isChanged()) {
									return true;
								}
							}
						});
						break;
				}
			}
		} //GRID1 End
		, FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				,{type:"block", list:[
					{type:"input", name:"grpInsrNm", label:"프로모션", width:120, offsetLeft:10, maxLength:30, required:true}
				]}
				,{type:"block", list:[
					{type:"calendar", name:"strtDt", label:"기간", width:100, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", readonly:true, required:true, offsetLeft:10}
					,{type:"newcolumn"}
					,{type:"calendar", name:"endDt", label:"~", labelWidth:10, width:100, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", readonly:true, required:true}
				]}
				,{type:"block", list:[
					{type:"checkbox", name:"usgYn", label:"사용여부", width:100, value:"Y", offsetLeft:10}
				]}
				/* ,{type:"block", list:[
					{type:"input", name:"exceptRate", label:"제외요금제", width:250}
				]} */
				,{type:"hidden", name:"seq"}
				,{type:"hidden", name:"insrCds"}
				,{type:"hidden", name:"insrNms"}
			]
		}
		, GRID2 : {
			css : {
				height : "180px"
			}
			,title : "대상보험"
			,header : "선택,보험코드,보험명"
			,columnIds : "chk,insrCd,insrNm"
			,colAlign : "center,center,left"
			,colTypes : "ch,ro,ro"
			,colSorting : "str,str,str"
			,widths : "50,80,200"
			,paging: false
			//,multiCheckbox : true
			,hiddenColumns : [1]
			,buttons : {
				center : {
					btnSave : { label : "저장" }
					, btnClose : { label : "닫기" }
				}
			}
			,onButtonClick : function(btnId) {
				var grid = this;
				switch(btnId) {
					case "btnSave":
						var form = PAGE.FORM2;
						
						if(mvno.cmn.isEmpty(form.getItemValue("grpInsrNm"))){
							mvno.ui.alert("단체보험명을 입력하세요.");
							return;
						}
						
						if(mvno.cmn.isEmpty(form.getItemValue("strtDt"))){
							mvno.ui.alert("시작일자를 선택하세요.");
							return;
						}
						
						if(mvno.cmn.isEmpty(form.getItemValue("endDt"))){
							mvno.ui.alert("종료일자를 선택하세요.");
							return;
						}
						
						var sStrtDt = new Date(form.getItemValue("strtDt")).format("yyyymmdd");
						var sEndDt = new Date(form.getItemValue("endDt")).format("yyyymmdd");
						
						if(sStrtDt > sEndDt){
							mvno.ui.alert("종료일자는 시작일자 이후로 선택하세요.");
							return;
						}
						
						var arrInsrData = [];
						var arrInsrData = PAGE.GRID2.getCheckedRowData();
						var arrInsrCds = "";
						var arrInsrNms = "";
						for(var i=0; i<arrInsrData.length; i++){
							if(i >= arrInsrData.length - 1){
								arrInsrCds += arrInsrData[i].insrCd;
								arrInsrNms += arrInsrData[i].insrNm;
							} else {
								arrInsrCds += arrInsrData[i].insrCd + ",";
								arrInsrNms += arrInsrData[i].insrNm + ",";
							}
						}
						
						form.setItemValue("insrCds", arrInsrCds);
						form.setItemValue("insrNms", arrInsrNms);
						
						if(mvno.cmn.isEmpty(form.getItemValue("insrCds"))){
							mvno.ui.alert("적용하실 단체보험을 선택하세요.");
							return;
						}
						
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("seq"))){
							url = "/ptnr/grpinsrmgmt/insertGrpInsrMst.json";
						} else {
							url = "/ptnr/grpinsrmgmt/updateGrpInsrMst.json";
						}
						
						mvno.cmn.ajax(ROOT + url, form, function(result) {
							PAGE.FORM2.clearChanged();
							mvno.ui.closeWindowById("GROUP1");
							mvno.ui.notify("NCMN0004");
							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
						});
						
					case "btnClose":
						mvno.ui.closeWindowById("GROUP1");
						break;
				}
			}
		}
	}
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : "${breadCrumb.breadCrumb}", 
		buttonAuth: ${buttonAuth.jsonString},
		
		onOpen:function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			PAGE.FORM1.setItemValue("stdrDt", today);
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0246", etc2:"Y"}, PAGE.FORM1, "grpInsrCd");
		}
	};
</script>