<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1044.jsp
	 * @Description : 직권해지 조회 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.08.22 장익준 최초생성
	 * @
	 * @author : 장익준
	 * @Create Date : 2014. 8. 22.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

var curYm = new Date().format("yyyymm");

var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},

// 				{type:"block", labelWidth:10, list:[ 
//                 {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchStartDt', label: '검색일자', value: "${startDate}", calendarPosition: 'bottom', readonly:true ,width:100,offsetLeft:10},
//                 {type: 'newcolumn'},
//                 {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', value: "${endDate}", calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
// 				]},
// 				{type:"newcolumn", offset:50},
// 				{type:"block", labelWidth:75, list:[
// 				{type : "input",label : "판매회사코드", width:100,name : "slsCmpnCd"}, 
// 				{type:"newcolumn", offset:30},					
// 				]},
				
				{type:"block", labelWidth:10, list:[
					{type: 'calendar', dateFormat: '%Y-%m', serverDateFormat: '%Y%m', name: 'strtYm', value:curYm, label: '검색기간', calendarPosition: 'bottom', width:80, offsetLeft:10, required:true}
					, {type: 'newcolumn'}
					, {type: 'calendar', dateFormat: '%Y-%m', serverDateFormat: '%Y%m', name: 'endYm', value:curYm, label: '~', calendarPosition: 'bottom', labelAlign:"center", labelWidth:10, offsetLeft:5, width:80}
				]},
				{type:"newcolumn", offset:50},
				{type:"block", labelWidth:75, list:[
// 					{type:"select", name:"searchGb", width:100, label:"검색구분", userdata:{lov: "RSK0004"}}
					{type:"select", name:"searchGb", width:100, label:"검색구분"}
					, {type:"newcolumn"}
					, {type:"input", name:"searchVal", width:150}
				]},
				{type:"newcolumn"},
				{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"}
			],
	
			onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
					case "btnSearch" :
						PAGE.GRID1.list(ROOT + "/org/powertmntmgmt/powerTmntMgmtList.json", form);
					break;
				}
			},
			
			onChange : function(name, value){
				var form = this;
				
				switch(name){
					case "searchGb":
						form.setItemValue("searchVal", "");
						break;
				}
			},
			onValidateMore : function (data){
// 				if((data.searchStartDt && !data.searchEndDt) || (!data.searchStartDt && data.searchEndDt)){
// 					this.pushError("data.searchStartDt","검색일자","일자를 선택하세요");
// 				}
// 				else if(data.searchStartDt > data.searchEndDt){
// 					this.pushError("data.searchStartDt","검색일자",["ICMN0004"]);
// 				}
// 				else if(getDateDiff(data.searchStartDt, data.searchEndDt) > 30){
// 					this.pushError("data.searchStartDt","검색일자","한달이내로 선택하세요.");
// 				}

				if((data.strtYm && !data.endYm) || (!data.strtYm && data.endYm)){
					this.pushError("data.strtYm","검색기간","검색기간을 선택하세요");
				}
				else if(data.strtYm > data.endYm){
					this.pushError("data.strtYm","검색기간", "시작월이 종료월보다 큽니다");
				}
				
				if(data.searchGb == "" && data.searchVal != ""){
					this.pushError("data.searchGb","검색구분", "검색구분을 선택하세요");
				}
				else if(data.searchGb != "" && data.searchVal == ""){
					this.pushError("data.searchVal","검색어", "검색어를 입력하세요");
				}
			}
		},

		//-------------------------------------------------------------
		GRID1 : {
			css : {
				height : "600px"
			},
			title : "조회결과",
			header : "판매회사코드,서비스계약번호,청구계정번호,고객번호,전화번호,고객명,개통일자,이용정지일자,은행,계좌번호,상품명,주소,우편번호,담당자명,담당자연락처,데이터추출일자,미납금액,구매유형, 모집대리점, 유심접점",
			columnIds : "slsCmpnCd,svcCntrNo,billAcntNo,custNo,tlphNo,custNm,openDt,svcCntrStopDt,bankNm,bnkacnNo,prdcNm,address,zipNo,empNm,telNo1,inputDate,unpdAmnt,reqBuyTypeNm,orgNm,fstUsimOrgNm",
			widths : "100,100,100,90,90,60,120,120,80,120,120,200,60,80,100,100,100,100,100,100",
			colAlign : "center,center,center,center,center,center,center,center,center,left,left,left,center,center,center,center,right,center,center,center",
			colTypes : "ro,ro,ro,ro,roXp,ro,roXd,roXd,ro,ro,ro,ro,ro,ro,ro,roXd,ron,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
			hiddenColumns : [17, 18, 19],
			paging : true,
			pageSize : 20,
			buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				}
			},
			onButtonClick : function(btnId, selectedData) {
				var form = this;
				switch (btnId) {
					case "btnDnExcel":
						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}
						
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download("/org/powertmntmgmt/excelDownProc.json?menuId=MSP1000007", true, {postData:searchData});
						break; 
				}
			}
		}
};
			
var PAGE = {
		title: "${breadCrumb.title}",
		breadcrumb: "${breadCrumb.breadCrumb}",
		
		buttonAuth: ${buttonAuth.jsonString},
		
		onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchGb"); // 검색구분
		}

};

/**
 * 시작날짜, 끝날짜
 */
function getDateDiff(date1, date2){
	
	var getDate1 = new Date(date1.substr(0,4), date1.substr(4,2)-1, date1.substr(6,2));
	var getDate2 = new Date(date2.substr(0,4), date2.substr(4,2)-1, date2.substr(6,2));
	
	var getDiffTime = getDate2.getTime() - getDate1.getTime();
	
	return Math.floor(getDiffTime / (1000 * 60 * 60 * 24));
}
</script>