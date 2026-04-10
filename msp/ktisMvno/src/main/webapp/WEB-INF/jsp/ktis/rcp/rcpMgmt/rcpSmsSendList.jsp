<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
/**
 * @Class Name : rcpSmsSendList.jsp
 * @Description : SMS발송이력
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2018.05.30 이상직
 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
var endDt = new Date().format("yyyymmdd");
var time = new Date().getTime();
time = time - 1000 * 3600 * 24 * 6;
var strtDt = new Date(time).format("yyyymmdd");

var DHX = {
	FORM1 : {
		items : [
			{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
			, {type:"block", list:[
				{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "searchStartDt", value:strtDt, label: "접수일자", calendarPosition: "bottom", width:100,offsetLeft:10}
				, {type: "newcolumn"}
				, {type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "searchEndDt", value:endDt, label: "~", calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
				, {type: "newcolumn"}
				, {type: "select", name:"searchGb", width:100, offsetLeft:30, required:true}
				, {type: "newcolumn"}
				, {type: "input", name:"searchVal", width:150}
			]}
			, {type: "button", value: "조회", name: "btnSearch" , className:"btn-search1"}
		]
// 		,onXLE : function() {}
		,onButtonClick : function(btnId) {
			var form = this;
			
			switch (btnId) {
				case "btnSearch":
					if(mvno.cmn.isEmpty(form.getItemValue("searchVal"))){
						mvno.ui.alert("검색어를 입력하세요");
						return;
					}
					
					PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpSmsSendList.json", form);
					PAGE.FORM2.clear();
					break;
			}
		}
		,onChange : function(name, value) {
			switch(name){
				case "searchGb":
					PAGE.FORM1.setItemValue("searchVal", "");
					break;
			}
		}
		,onValidateMore : function (data){
			if (this.getItemValue("searchStartDt", true) > this.getItemValue("searchEndDt", true)) {
				this.pushError("searchEndDt", "조회기간", "종료일자가 시작일자보다 클 수 없습니다.");
				this.resetValidateCss();
			}
		}
	}
	//신청관리 리스트
	,GRID1 : {
		css : {
			height : "365px"
		},
		title : "조회결과",
		header : "접수일시,템플릿,전송일시,전송결과,예약번호,CALLBACK,내용,제목,종료시간,유형,템플릿ID,수신자번호",
		columnIds : "requestTime,templateNm,sendTime,resultNm,resNo,callback,text,subject,expireTime,msgType,templateId,dstaddr",
		colAlign : "center,left,center,center,center,center,center,center,center,center,center",
		colTypes : "roXdt,ro,roXdt,ro,ro,ro,ro,ro,ro,ro,ro,ro",
		colSorting : "str,str,str,str,str,str,str,str,str,str,str,str",
		widths : "150,350,150,100,100,100,10,10,10,10,10,10",
		hiddenColumns: [6,7,8,9,10,11],
		paging: true,
		pageSize:20,
		showPagingAreaOnInit: true,
		buttons : {
		}
// 		,checkSelectedButtons : ["btnDtl"]
// 		,onRowDblClicked : function(rowId, celInd) {
// 			this.callEvent('onButtonClick', ['btnDtl']);
// 		}
// 		,onButtonClick : function(btnId, selectedData) {
// 			var form = this;
// 			switch (btnId) {
// 			}
// 		}
		, onRowSelect : function(rowId, celInd) {
			var data = this.getRowData(rowId);
			
			PAGE.FORM2.setItemValue("dstaddr", data.dstaddr);
			PAGE.FORM2.setItemValue("subject", data.subject);
			PAGE.FORM2.setItemValue("text", data.text);
			PAGE.FORM2.setItemValue("msgType", data.msgType);
			PAGE.FORM2.setItemValue("templateId", data.templateId);
			PAGE.FORM2.setItemValue("expireTime", data.expireTime);
			PAGE.FORM2.setItemValue("callback", data.callback);
			PAGE.FORM2.setItemValue("resNo", data.resNo);
			
		}
	}
	//상세보기
	,FORM2 : {
		items : [
			{type:"settings", position:"label-left", labelAlign:"left", labelWidth: "80"}
			,{type:"block", list:[
				{type:"input", name:"text", label:"문자내용", rows:10, width:700, offsetLeft:10, readonly:true}
				, {type:"hidden", name:"dstaddr"}
				, {type:"hidden", name:"subject"}
				, {type:"hidden", name:"msgType"}
				, {type:"hidden", name:"templateId"}
				, {type:"hidden", name:"expireTime"}
				, {type:"hidden", name:"callback"}
				, {type:"hidden", name:"resNo"}
			]}
		]
		,onValidateMore : function (data) {
			var form = this;
			
		}
		,onButtonClick:function(btnId){
			var form = this;
		}
	}
};

var PAGE = {
	 title: "${breadCrumb.title}",
	 breadcrumb: "${breadCrumb.breadCrumb}",
	 buttonAuth: ${buttonAuth.jsonString},
	 onOpen:function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		mvno.ui.createForm("FORM2");
		
		// 검색구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2045"}, PAGE.FORM1, "searchGb");
		
	}
};

</script>