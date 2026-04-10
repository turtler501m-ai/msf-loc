<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : excelDown.jsp
	 * @Description : 저장된 엑셀다운로드 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2015.08.20 김연우 최초생성
	 * @
	 * @author : 김연우
	 * @Create Date : 2015. 10. 13.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>


<!-- Script -->
<script type="text/javascript">

	var DHX = {
			//------------------------------------------------------------
			FORM1 : {
				items : [
					{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},
						{type:"block", labelWidth:30 , list:[ 
							{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'startDt', label: '생성일자', value: "${startDate}", calendarPosition: 'bottom', readonly:true ,width:100,offsetLeft:10},
							/* {type:"newcolumn"},
							{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'endDt', label: '~', value: "${endDate}", calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100} */
						]},
					]},
					{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},
						/* {type:"label", width:500, label:"** 당일 생성된 엑셀 파일만 저장됩니다. 저장되는 데이터의 양에 따라 시간이 지체될 수 있으니 파일이 없을 경우 잠시후에 다시 조회하시기 바랍니다.", offsetLeft:10, name:pointLabel} */
						{type:"label", width:500, offsetLeft:10, name:"pointLabel"}
					]},
					{type:"newcolumn"},
					{type : "button",name : "btnSearch",value : "조회", className:"btn-search2"} 
				]
				, onValidateMore : function (data) {
					/*
					if(data.searchStartDt == '' || data.searchStartDt == null)
					{
						 this.pushError("searchStartDt", "생성일자", "생성일자는 필수 입니다");
					}
					if(data.searchEndDt == '' || data.searchEndDt == null)
					{
						 this.pushError("searchStartDt", "생성일자", "생성일자는 필수 입니다");
					}
					*/
				}
	 			, onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							PAGE.GRID1.clearAll();
							PAGE.GRID1.list(ROOT + "/cmn/exceldown/getExcelList.json", form);
						break;
					}
				}
			},
				
			//-------------------------------------------------------------
			GRID1 : {
				css : {
					height : "560px"
				},
				title : "조회결과",
				header : "등록일자,요청메뉴,상태명,파일명,등록자,등록사유",
				columnIds : "regstDttm,menuNm,statNm,fileNm,usrNm,dwnldRsn", //regstId
				widths : "140,100,80,330,100,200",
				colAlign : "center,center,center,left,center,left",
				colTypes : "roXd,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,str",
				//hiddenColumns:[1],
				paging : true,
				pageSize:20,
				buttons : {
					hright : {
						 btnDnExcel : { label : "파일저장" }
					}
				},
				onButtonClick : function(btnId, selectedData) {
					switch (btnId) {
						case "btnDnExcel":
							
							if(PAGE.GRID1.getSelectedRowData() == null || PAGE.GRID1.getSelectedRowData() == '') {
								mvno.ui.alert("저장 할 데이터를 선택해주세요.");
								return;
							}else if(PAGE.GRID1.getSelectedRowData().fileNm == null || PAGE.GRID1.getSelectedRowData().fileNm == ''){
								mvno.ui.alert("저장할 파일이 없습니다. 상태를 확인하세요.");
								return;
							} else {
								var url = "/cmn/exceldown/excelDownProcByExclDnldId.json";
								var sData = {
										'exclDnldId'  : PAGE.GRID1.getSelectedRowData().exclDnldId
									};
								mvno.cmn.submitDynamicForm(url, sData, "");
							}
							break;
					}
				}
			}
		};
			
	var PAGE = {
	    title : "${breadCrumb.title}",
	    breadcrumb : "${breadCrumb.breadCrumb}",
	
		buttonAuth: ${buttonAuth.jsonString},
		
		onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");

			PAGE.FORM1.disableItem("startDt");
			PAGE.FORM1.disableItem("endDt");
			PAGE.FORM1.setItemLabel("pointLabel", "<span style='color:red;'> ** 당일 생성된 엑셀 파일만 저장됩니다. 저장되는 데이터의 양에 따라 시간이 지체될 수 있으니 파일이 없을 경우 잠시후에 다시 조회하시기 바랍니다. </span>");
		}
	};

</script>