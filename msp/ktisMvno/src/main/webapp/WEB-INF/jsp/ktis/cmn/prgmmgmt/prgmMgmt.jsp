<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name  : msp_cmn_batch_1002.jsp
	 * @Description : 배치실행이력
	 * @
	 * @  수정일	  	수정자			  수정내용
	 * @ ---------   ---------   -------------------------------
	 * @ 2016.06.27  	TREXSHIN		  최초생성
	 * @ 
	 * @author : TREXSHIN
	 * @Create Date : 2016.06.27
	 */
%>
	
	<div id="FORM1" class="section-search"></div>
	<div id="GRID1"></div>
	
	<script type="text/javascript">
	var DHX = {
			FORM1 : {
				items : [
					 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					
					,{type:"block", list:[
						 {type:"calendar", name:"searchStartDate", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", validate: "NotEmpty", label:"조회기간", offsetLeft:10, width:100, readonly: true}
						,{type:"newcolumn"}
						,{type:"calendar", name:"searchEndDate", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", validate: "NotEmpty", label:"~", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100, readonly: true}
						,{type:"newcolumn"}
						,{type:"input", name:"searchPrgmId", label:"프로그램ID", labelWidth:70, width:100, offsetLeft:30}
						,{type:"newcolumn"}
						,{type:"input", name:"searchPrgmNm", label:"프로그램명", labelWidth:70, width:180, offsetLeft:30}
					]}
					,{type:"block", list:[
						 {type:"select", name:"searchSysSctn", label:"시스템구분", width:80, offsetLeft:10}
						,{type:"newcolumn"}
						,{type:"select", name:"searchStatCd", label:"상태", labelWidth:35, width:80, offsetLeft:20}
						,{type:"newcolumn"}
						,{type:"input", name:"searchExecService", label:"실행서비스", labelWidth:70, width:180, offsetLeft:30}
					]}
					
					,{type:"newcolumn", offset:130}
					,{type:"button", name:"btnSearch", value:"조회", className:"btn-search2"}
				],
				
				onValidateMore : function(data) {
					
				},
				
				onButtonClick : function(btnId) {
					
					var form = this;
					
					switch(btnId) {
						
						case "btnSearch" :
							
							PAGE.GRID1.list(ROOT + "/cmn/prgmmgmt/getPrgmMgmtHst.json", form);
							break;
					}
				}
				
			},
			
			GRID1 : {
				 title      : "프로그램이력"
				,css        : {height : "530px"}
				,header     : "시스템구분,프로그램ID,프로그램명,상태,실행유형,시작시간,종료시간,요청시간,실행변수,오류코드,오류메시지,비고,실행서비스"
				,columnIds  : "sysSctn,prgmId,prgmNm,statCd,statNm,startDttm,endDttm,reqDttm,execParam,errCd,errMsg,remrk,prgmUrl"
				,widths     : "90,90,180,60,70,130,130,100,150,120,150,150,150"
				,colAlign   : "center,center,left,center,center,center,center,center,left,center,left,left,left"
				,colTypes   : "ro,ro,ro,ro,ro,roXdt,roXdt,roXdt,ro,ro,ro,ro,ro"
				,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str"
				//,hiddenColumns: [16,17,18,19]
				,paging     : true
				,showPagingAreaOnInit: true
				,pageSize: 20
				,buttons : {
					right : {
						btnRetry : { label : "배치재실행" }
					}
				}
				, checkSelectedButtons : ["btnRetry"]
				, onRowDblClicked : function(rowId, celInd) {
					this.callEvent('onButtonClick', ['btnRetry']); // ROW 더블 클릭시 수정버튼과 같은 기능 처리함.
				}
				, onButtonClick : function(btnId, selectedData) {
					
					switch(btnId) {
						
						case "btnRetry":
							if(selectedData.sysSctn == "BTCH") {
								var confirmMsg = selectedData.prgmNm + "<br>["+selectedData.prgmId+"]를 재실행하시겠습니까?<br>동일한 실행변수로 재실행됩니다.";
								
								mvno.ui.confirm(confirmMsg, function() {
									mvno.cmn.ajax(ROOT + "/cmn/btchmgmt/retryBatchRequest.json", selectedData, function(result) {
										mvno.ui.notify("NCMN0004");
										PAGE.GRID1.refresh();
									});
								});
							} else {
								mvno.ui.alert("배치 데이터만 재실행 가능합니다.");
							}
							
							break;
					}
				}
			}
			
		};
		
		var PAGE = {
				
			title : "${breadCrumb.title}",
			breadcrumb : " ${breadCrumb.breadCrumb}",
			
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				
				var endDate = new Date().format('yyyymmdd');
				var time = new Date().getTime();
				time = time - 1000 * 3600 * 24 * 3;
				var startDate = new Date(time);
				
				PAGE.FORM1.setItemValue("searchStartDate", startDate);
				PAGE.FORM1.setItemValue("searchEndDate", endDate);
				
				var fixGrid = PAGE.GRID1.getColIndexById("prgmNm");
				PAGE.GRID1.splitAt(fixGrid+1);

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0233"}, PAGE.FORM1, "searchSysSctn"); // 업무구분
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0027"}, PAGE.FORM1, "searchStatCd"); // 상태
			}
			
		};
		
		
	</script>