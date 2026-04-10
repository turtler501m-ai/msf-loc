<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : sms_smsmgmt_0020.jsp
   * @Description : 문자전송내역
   * @Modification Information
   *
   *   수정일         수정자                   수정내용
   *  -------    --------    ---------------------------
   *  2025.02.12		이승국  		최초생성
   *
   */
   %>

	
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>  
     

<!-- Script -->
<script type="text/javascript">
var endDt = new Date().format("yyyymmdd");
var time = new Date().getTime();
time = time - 1000 * 3600 * 24 * 6;
var strtDt = new Date(time).format("yyyymmdd");
	
	
var DHX = {
	FORM1 : {
		title : "",
		items : [ 	
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					 ,{type:"block", list:[
						 {type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "searchStartDate", value:strtDt, label: "접수일자", calendarPosition: "bottom", width:100,offsetLeft:10, readonly:true}
						, {type: "newcolumn"}
						, {type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "searchEndDate", value:endDt, label: "~", calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100, readonly:true}
						, {type: "newcolumn"}
					]},
					{type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					, {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				]
				,onValidateMore : function(data){
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
					    PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					} else if (startDate < sixMonthsAgo) {
					    this.pushError("searchStartDate", "조회기간", "조회 기간은 최근 6개월 이내여야 합니다.");
					    this.resetValidateCss();
					    PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
				},
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch":
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							PAGE.GRID1.list(ROOT + " /pps/sms/smsmgmt/SmsDumpMgmtList.json", form, {
								callback : function () {
									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								}
							});
							break;
					}
				}
	}
	,GRID1 : {
		css : {
			height : "590px"
		},
		title : "조회결과",
		header : "메세지유형, 전송일자, 제목, 관리부서, 건수, 처리시스템",
		columnIds : "msgType,scheduleTime,subject,orgnNm,cnt,systemNm",
		widths : "150,160,230,160,80,160", //총 1500
		colAlign : "center,center,left,left,center,left",
		colTypes : "ro,ro,ro,ro,ro,ro",
		colSorting : "str,str,str,str,str,str",
		paging :true,
		pageSize :20,
		buttons : {
			hright : {
				btnDnExcel : { label : "엑셀다운로드" } 
			}
		},				
       	onButtonClick : function(btnId) {

			switch (btnId) {
				case "btnDnExcel":
					var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
					PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
					if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
					
					var totCnt = PAGE.GRID1.getRowsNum();
					if(totCnt <= 0){
						mvno.ui.notify("출력건수가 없습니다.");
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					   return;
					}
					 var searchData =  PAGE.FORM1.getFormData(true);
					 mvno.cmn.download('/pps/sms/smsmgmt/SmsDumpMgmtListExcel.json'+"?menuId=PPS3000001",true,{postData:searchData});
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
	}

};
		
		
</script>
