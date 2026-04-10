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
		FORM1: {
			items: [
				{type: "settings", position: "label-left", labelAlign: "left", labelWidth: 60, blockOffset: 0},
				{type: "block", list: [
						{type: "calendar", name: "searchStartDate", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", calendarPosition: "bottom", validate: "NotEmpty", label: "조회기간", offsetLeft: 10, width: 100, readonly: true},
						{type: "newcolumn"},
						{type: "calendar", name: "searchEndDate", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", calendarPosition: "bottom", validate: "NotEmpty", label: "~", labelAlign: "center", labelWidth: 10, offsetLeft: 5, width: 100, readonly: true},
						{type: "newcolumn"},
						{type: "input", name: "searchBatchJobId", label: "배치작업ID", labelWidth: 70, width: 100, offsetLeft: 30},
						{type: "newcolumn"},
						{type: "input", name: "searchBatchJobNm", label: "배치작업명", labelWidth: 70, width: 180, offsetLeft: 30}
					]
				},
				{type: "block", list: [
						{type: "select", name: "searchDutySctn", label: "업무구분", width: 80, offsetLeft: 10},
						{type: "newcolumn"},
						{type: "select", name: "searchStatCd", label: "상태", labelWidth: 35, width: 80, offsetLeft: 20},
						{type: "newcolumn"},
						{type: "input", name: "searchExecService", label: "실행서비스", labelWidth: 70, width: 180, offsetLeft: 30},
						{type: "newcolumn"},
						{type:"select", name:"serverType", label:"배치서버", width:80 ,offsetLeft:10}
					]
				},
				{type: "newcolumn", offset: 130},
				{type: "button", name: "btnSearch", value: "조회", className: "btn-search2"}
			],
			onButtonClick: function (btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch" :
						PAGE.GRID1.list(ROOT + "/cmn/batch/getBatchExecHst.json", form);
						break;
				}
			}
		},
		GRID1: {
			title: "배치실행이력",
			css: {height: "530px"},
			header: "배치서버,배치작업ID,배치작업명,상태,실행유형,시작시간,종료시간,요청시간,실행변수,실행건수,에러코드,에러메시지,비고,업무구분,실행서비스,배치유형,실행Cron표현식,상태코드,실행유형코드,업무구분코드,배치유형코드",
			columnIds: "serverTypeNm,batchJobId,batchJobNm,statNm,execTypeNm,strtDttm,endDttm,reqDttm,execParam,execCount,errCd,errMsg,remrk,dutySctnNm,execService,batchTypeNm,execCron,statCd,execTypeCd,dutySctn,batchTypeCd",
			widths: "60,90,180,60,60,130,130,130,200,60,100,200,160,70,180,70,150,0,0,0,0", //20
			colAlign: "center,center,left,center,center,center,center,center,left,right,left,left,left,center,left,center,left,left,left,left,left",
			colTypes: "ro,ro,ro,ro,ro,roXdt,roXdt,roXdt,ro,ron,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting: "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
			hiddenColumns: [17, 18, 19, 20],
			paging: true,
			showPagingAreaOnInit: true,
			pageSize: 20,
			buttons: {
				right: {
					btnRetry: {label: "재실행"}
				}
			},
			checkSelectedButtons: ["btnRetry"],

			onRowDblClicked: function (rowId, celInd) {
				this.callEvent('onButtonClick', ['btnRetry']); // ROW 더블 클릭시 수정버튼과 같은 기능 처리함.
			},
			onButtonClick: function (btnId, selectedData) {
				switch (btnId) {
					case "btnRetry":
						var confirmMsg = selectedData.batchJobNm + "<br>[" + selectedData.batchJobId + "]를 재실행하시겠습니까?<br>동일한 실행변수로 재실행됩니다.";
						mvno.ui.confirm(confirmMsg, function () {
							mvno.cmn.ajax(ROOT + "/cmn/btchmgmt/retryBatchRequest.json", selectedData, function (result) {
								mvno.ui.notify("NCMN0004");
								PAGE.GRID1.refresh();
							});
						});
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

			var fixGrid = PAGE.GRID1.getColIndexById("batchJobNm");
			PAGE.GRID1.splitAt(fixGrid+1);

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0023",etc2:"2"}, PAGE.FORM1, "searchDutySctn"); // 업무구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0027"}, PAGE.FORM1, "searchStatCd"); // 상태
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0029"}, PAGE.FORM1, "serverType"); // 배치서버
		}
	};
		
	</script>