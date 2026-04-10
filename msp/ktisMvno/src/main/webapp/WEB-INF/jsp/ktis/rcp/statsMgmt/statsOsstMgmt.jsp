<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div class="row">
	<div class="c3" >
		<div id="FORM1" class="section-search"></div>
		<div>		
			<div id="GRID1" class="section-half"></div>
		</div>
	</div>
	<div class="c7" >
		<div id="FORM2" class="section-search"></div>
		<div>
			<div id="GRID2" class="section-half"></div>
		</div>
	</div>
</div>
<div id="FORM3" style="display:none;"></div>
<div id="GRID3" class="section"></div>


   		
<!-- Script -->
<script type="text/javascript">
	
	var fromDt = new Date(new Date().setDate(new Date().getDate() - 6)).format("yyyymmdd");
	var toDt = new Date().format("yyyymmdd");
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0}
				, {type:"block", list:[
					{type:"select", label:"조회기간", width:100, name:"totalDay", options:[{value:"30", text:"30일"}, {value:"60", text:"60일"}, {value:"90", text:"90일"}, {value:"120", text:"120일"}]}
				]}
				, {type:"block", list:[
					{type:"input", label:"오류코드", name:"totalErrCd", width:100}
				]}	
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"} 
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getStatsOsstMgmtList.json", form);
						break;
				}
			}
		}
		, FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"stdrDate", label:"조회일자", calendarPosition: "bottom", width:100, offsetLeft : 20}
					, {type:"newcolumn"}
					, {type:"select", label : "서비스명", width : 150, offsetLeft : 80, name : "eventCd"}
				]}
				, {type:"block", list:[
					{type:"input", label:"오류코드", name: "errCd", width:160, offsetLeft : 20}
				]}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"} 
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":						
						PAGE.GRID2.list(ROOT + "/stats/statsMgmt/getStatsOsstMgmtDaily.json", form);
						
						break;
				}
			}
		}
		, FORM3 : {
			items : [
				{type:"hidden", name: "stdrDate"}
				, {type:"hidden", name: "eventCd"}
				, {type:"hidden", name: "errCd"}
			]
		}
		, GRID1 : {
			css : {
				height : "573px",
			}
			, title : "처리현황"
			, header : "일자,총건수,오류건수,비율"
			, columnIds : "regDt,tCnt,eCnt,ePer"
			, colAlign : "center,right,right,right"
			, widths : "76,60,60,52"
			, colTypes : "ro,ron,ron,ro"
			, colSorting : "str,int,int,str"
			, paging : true
			, pagingStyle : 1
			, pageSize : 120
			, showPagingAreaOnInit : true
			, onRowSelect : function(rowId, celInd) {
				var form = PAGE.FORM2;
				form.setItemValue("stdrDate", PAGE.GRID1.getSelectedRowData().regDt.replaceAll("-",""));
				form.setItemValue("eventCd", "");
				form.setItemValue("errCd", "");
				
				form.callEvent('onButtonClick', ['btnSearch']);
			}
		}
		, GRID2 : {
			css : {
				height : "574px"
			}
			, title : "일일현황요약"
			, header : "서비스명,오류코드,오류내용,건수"
			, columnIds : "prgrStatNm,rsltCd,rsltMsg,errCnt"
			, widths : "120,100,370,37"
			, colAlign : "left,center,left,right"
			, colTypes : "ro,ro,ro,ron"
			, colSorting : "str,str,str,int"
			, paging: true
			, showPagingAreaOnInit: true
			, pageSize:100
			, onRowDblClicked : function(rowId, celInd) {
				mvno.ui.createForm("FORM3");
				mvno.ui.createGrid("GRID3");
				PAGE.GRID3.clearAll();
				
				mvno.ui.popupWindowById("GRID3", "일일현황상세", 950, 740, {
					onClose: function(win) {
						win.closeForce();
					}
				});
				
				PAGE.FORM3.setItemValue("stdrDate",new Date(PAGE.FORM2.getItemValue("stdrDate")).format("yyyymmdd"));
				PAGE.FORM3.setItemValue("eventCd",PAGE.GRID2.getSelectedRowData().prgrStatCd);
				PAGE.FORM3.setItemValue("errCd",PAGE.GRID2.getSelectedRowData().rsltCd);
				
				PAGE.GRID3.list(ROOT + "/stats/statsMgmt/getStatsOsstMgmtDetail.json", PAGE.FORM3);
			}
		}
		, GRID3 : {
			css : {
				height : "573px"
				, width : "890px"
			}
			, title : "일일현황상세"
			, header : "MVNO오더번호,OSST오더번호,서비스코드,오류코드,오류내용,NSTEP GLOBAL ID,처리일시"
			, columnIds : "mvnoOrdNo,osstOrdNo,prgrStatCd,rsltCd,rsltMsg,nstepGlobalId,rsltDt"
			, widths : "110,110,80,120,900,180,120"
			, colAlign : "center,center,center,center,left,left,center"
			, colTypes : "ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str"
			, paging: true
			, showPagingAreaOnInit: true
			, pageSize:100
		
			, buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드"}
				}
			}
			, onButtonClick : function(btnId) {
				var grid = this;
				
				switch (btnId) {
					case "btnExcel":
						var searchData =  PAGE.FORM3.getFormData(true);
						
						mvno.cmn.download(ROOT + "/stats/statsMgmt/getStatsOsstMgmtDetailExcel.json?menuId=MSP1000111", true, {postData:searchData});
												
						break;
					
				}
			}
		}
		
	}
	
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		buttonAuth: ${buttonAuth.jsonString}
		, onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createForm("FORM2");
			mvno.ui.createGrid("GRID1");
			mvno.ui.createGrid("GRID2");
						
			var toDay = new Date().format('yyyymmdd');
			PAGE.FORM2.setItemValue("stdrDate", toDay);
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2040"}, PAGE.FORM2, "eventCd");
			
		}
	};
</script>
