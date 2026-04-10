<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name  : msp_cmn_sms_1002.jsp
	 * @Description : SMS 발송조회
	 * @
	 * @  수정일	  	수정자			  수정내용
	 * @ ---------   ---------   -------------------------------
	 * @ 2017.01.17  	TREXSHIN		  최초생성
	 * @ 
	 * @author : TREXSHIN
	 * @Create Date : 2017.01.17
	 */
%>
	
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>

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
					{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "searchStartDate", value:strtDt, label: "전송일자", calendarPosition: "bottom", width:100,offsetLeft:10, readonly:true}
					, {type: "newcolumn"}
					, {type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "searchEndDate", value:endDt, label: "~", calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100, readonly:true}
					, {type: "newcolumn"}
					, {type: "select", name:"searchCode", width:100, offsetLeft:30, required:true , options:[{value:"rcptData",text:"수신자 번호"}]}
					, {type: "newcolumn"}
					, {type: "input", name:"searchValue", width:150}
				]}
				, {type: "button", value: "조회", name: "btnSearch" , className:"btn-search1"}
			]
//	 		,onXLE : function() {}
			,onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						if ('${orgnInfo.orgnId}' == 'A30040999') {
							if(mvno.cmn.isEmpty(form.getItemValue("searchValue"))){
								mvno.ui.alert("검색어를 입력하세요");
								return;
							}
						}
						
						PAGE.GRID1.list(ROOT + "/cmn/smsmgmt/getSmsSendList.json", form);
						PAGE.FORM2.clear();
						break;
				}
			}
			,onChange : function(name, value) {
				switch(name){
					case "searchGb":
						PAGE.FORM1.setItemValue("searchValue", "");
						break;
				}
			}
			,onValidateMore : function (data){
				const startDateStr = this.getItemValue("searchStartDate", true); // YYYYMMDD 형식 가정
				const endDateStr = this.getItemValue("searchEndDate", true);     // YYYYMMDD 형식 가정

				// 문자열을 YYYY-MM-DD 형식으로 변환 후 Date 객체로 변환
				const startDate = new Date(startDateStr.substring(0, 4), startDateStr.substring(4, 6) - 1, startDateStr.substring(6, 8));
				const endDate = new Date(endDateStr.substring(0, 4), endDateStr.substring(4, 6) - 1, endDateStr.substring(6, 8));

				// 현재 날짜 가져오기
				const today = new Date();

				// 6개월 전 날짜 계산
				const sixMonthsAgo = new Date(today);
				sixMonthsAgo.setMonth(today.getMonth() - 6);

				if (startDate > endDate) {
				    this.pushError("searchEndDate", "조회기간", "종료일자가 시작일자보다 클 수 없습니다.");
				    this.resetValidateCss();
				} else if (startDate < sixMonthsAgo) {
				    this.pushError("searchStartDate", "조회기간", "조회 기간은 최근 6개월 이내여야 합니다.");
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
			header : "접수일시,전송일시,제목,카카오전송결과,문자전송결과,수신자번호,발신자번호,내용,전송타입,reserved01,reserved02,reserved03,카카오메세지,카카오템플릿,카카오발신키,msgId",
			columnIds : "submitTime,scheduleTime,subject,kkoResultNm,resultNm,rcptData,callbackNum,message,msgType,reserved01,reserved02,reserved03,kMessage,kTmplcode,kSenderkey,msgId",
			colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center",
			colTypes : "roXdt,roXdt,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
			widths : "150,150,250,100,100,100,100,10,10,10,10,10,10,10,10,10,10,10,10,10",
			hiddenColumns: [7,8,9,10,11,12,13,14,15],
			paging: true,
			pageSize:20,
			showPagingAreaOnInit: true,
			buttons : {
			},
			onRowSelect : function(rowId, celInd) {
				var data = this.getRowData(rowId);
				PAGE.FORM2.setItemValue("msgId", data.msgId);
				PAGE.FORM2.setItemValue("message", data.message);
				PAGE.FORM2.setItemValue("msgType", data.msgType);
			}
		}
		//상세보기
		,FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth: "80"}
				,{type:"block", list:[
					{type:"input", name:"message", label:"문자내용", rows:10, width:700, offsetLeft:10, readonly:true}
					, {type:"hidden", name:"msgType"}
					, {type:"hidden", name:"msgId"}
				]}
			]
			,buttons : {
				right : { btnSend:{label: "재전송"}}
			}
			,onValidateMore : function (data) {
				var form = this;
				
			}
			,onButtonClick:function(btnId){
				var form = this;
				
				switch(btnId){
					case "btnSend":
						if(form.getItemValue("msgType") == '3'){
							mvno.ui.alert("MMS는 재전송이 불가능 합니다.");
							return;
						}
						
						mvno.cmn.ajax(ROOT + "/cmn/smsmgmt/setSmsRetry.json", form, function(result) {
							mvno.ui.notify("NCMN0001");
						});
						break;
				}
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
			if ('${orgnInfo.orgnId}' != 'A30040999') {
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2064", orderBy:"etc1"}, PAGE.FORM1, "searchCode");
			}
			
		}
	};

</script>