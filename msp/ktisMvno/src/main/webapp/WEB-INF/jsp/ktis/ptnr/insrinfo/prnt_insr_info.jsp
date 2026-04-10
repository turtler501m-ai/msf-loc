<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : prnt_insr_info.jsp
	 * @Description : 보험가입자정보
	 * @
	 * @ 수정일		수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2018.06.05 권오승 최초생성
	 * @
	 * @author : 권오승
	 * @Create Date : 2018.06.05
	 */
%>
<!-- 

FORM1 : 검색조건
GRID1 : 보험가입자목록 GRID
GRID2 : 가입이력 GRID
 -->
<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="GRID2"></div>
<!-- Script -->
<script type="text/javascript">
	var detailNewCheck = true;
	
	var DHX = {
			/* 
			-----------------------------------
			----------- 검색조건 --------------
			-----------------------------------
			*/
			FORM1 : {
				items : [
					{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 60, blockOffset : 0 }
					, {type:"block", list:[
						{type:"calendar", width:100, label:"기준일자", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchBaseDate", offsetLeft:10, required:true}
						, {type:"newcolumn"}
						, {type:"select", width:80, offsetLeft:10, label:"검색구분", name:"searchCd"}
						, {type:"newcolumn"}
						, {type:"input", name:"searchVal", width:100, maxLength:20}
						, {type:"newcolumn"}
						, {type:"select", label: "요금제", name:"searchRateCd", width:100, offsetLeft:10}
						, {type:"newcolumn"}
						, {type:"select", label: "단체보험", name:"searchInsrCd", width:100, offsetLeft:10}
					]}
					, {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"}
				],
				onChange: function (name, value) {
					var form = this;
					
					// 검색구분
					if(name == "searchCd") {
						PAGE.FORM1.setItemValue("searchVal", "");
						if(value == null || value == "" || (value != '1' && value != '2' && value != '3')) {
							var searchBaseDate = new Date().format('yyyymmdd');
							// 기준일자 Enable
							PAGE.FORM1.enableItem("searchBaseDate");
							PAGE.FORM1.setItemValue("searchBaseDate", searchBaseDate);
							PAGE.FORM1.disableItem("searchVal");
						}
						else {
							PAGE.FORM1.setItemValue("searchBaseDate", "");
							// 기준일자 Disable
							PAGE.FORM1.disableItem("searchBaseDate");
							PAGE.FORM1.enableItem("searchVal");
						}
					}
				},
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch": //조회버튼 클릭
							if (!this.validate()) return;
							PAGE.GRID1.list(ROOT + "/ptnr/ptnrMgmt/getInsrMemberList.json", form, {callback : function() {
								PAGE.GRID2.clearAll();
							}});
							break;
					}
				},
				onValidateMore : function (data) {
					//검색어 구분에 값이 있는데 검색어가 없을 경우 return
					if(data.searchCd != ""  && data.searchVal.trim() == ""){
							this.pushError("searchVal", "검색어", "검색어를 입력하세요");
							PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
			    } 
			}
		},
			
		/* 
		-----------------------------------
		-------- 보험가입자목록 GRID ------------
		-----------------------------------
		*/
		GRID1 : {
			css : {
				height : "350px"
			},
			title : "보험가입자목록",
			header : "계약번호,고객명,전화번호,요금제,가입보험,보험개시일,보험종료일,수납여부,생년월일,가입나이(만),법정대리인명,법정대리인전화번호,가입대리점",
			columnIds : "contractNum,custNm,ctn,rateNm,insrNm,efctStrtDt,efctEndDt,pymnYn,birthDt,age,minorAgentNm,minorAgentTel,openAgntNm",
			widths : "100,120,100,150,150,100,100,80,100,80,100,120,120",
			colAlign : "center,left,center,left,left,center,center,center,center,right,left,center,left",
			colTypes : "ro,ro,roXp,ro,ro,roXd,roXd,ro,roXd,ro,ro,roXp,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str",
			paging:true,
			showPagingAreaOnInit: true,
			pageSize:20,
			onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
				
				}
			},
			onRowSelect : function(rowId) {
				var rowData = this.getSelectedRowData();
				PAGE.GRID2.clearAll();
				PAGE.GRID2.list(ROOT + "/ptnr/ptnrMgmt/getInsrHistory.json", rowData);
			}
		},

		/* 
		-----------------------------------
		-------- 가입이력 GRID ------------
		-----------------------------------
		*/
		GRID2 : {
			css : {
				height : "150px"
			},
			title : "가입이력",
			header : "고객명,요금제,가입보험,보험효력개시일,보험효력종료일",
			columnIds : "custNm,rateNm,insrNm,efctStrtDt,efctEndDt",
			widths : "150,150,150,120,120",
			colAlign : "left,left,left,center,center",
			colTypes : "ro,ro,ro,roXd,roXd",
			colSorting : "str,str,str,str,str",
			paging:true,
			showPagingAreaOnInit: true,
			pageSize:20
		}
	};
	
var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : "${breadCrumb.breadCrumb}", 
		buttonAuth: ${buttonAuth.jsonString},
		
	onOpen : function() {

		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		mvno.ui.createGrid("GRID2");
		
		var searchBaseDate = new Date().format('yyyymmdd');

		PAGE.FORM1.setItemValue("searchBaseDate", searchBaseDate);
		
		//조회조건 검색조건 세팅
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9013"}, PAGE.FORM1, "searchCd");
		//조회조건 요금제 세팅
		mvno.cmn.ajax4lov( ROOT + "/ptnr/ptnrMgmt/selectDongbuRate.json", {parnterId:"dongbu"}, PAGE.FORM1, "searchRateCd");
		//조회조건 단체보험
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0246", etc2:"Y"}, PAGE.FORM1, "searchInsrCd");
		
		PAGE.FORM1.disableItem("searchVal");
	}

};

</script>