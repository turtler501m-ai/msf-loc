<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">
	var curMnth = new Date().format("yyyymm");

	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blackOffset:0}
					, {type:"block", list:[
										{type:"calendar", label:"연동년월", name:"searchYm", width:80, value:curMnth, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:10}
										, {type:"newcolumn"}
										, {type:"input", label:"전화번호", name:"subscriberNo", width:100, offsetLeft:20}
										, {type:"newcolumn"}
										, {type:"select", label:"지급결과", name:"resultCd", width:100, offsetLeft:20}
					                	]}

					//버튼 여러번 클릭 막기 변수
					, {type : 'hidden', name: "btnCount1", value:"0"} 
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}
					                				
					, {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							PAGE.GRID1.list(ROOT + "/ptnr/ptnrCalc/getPtnrCalcTmoneyList.json", form, {callback : function() {
								mvno.cmn.ajax(ROOT + "/ptnr/ptnrCalc/getPtnrCalcTmoneyListSum.json", form, function(resultData) {
									PAGE.FORM2.setItemValue("reqPointSum",resultData.result.data.rows[0].reqPointSum);
									PAGE.FORM2.setItemValue("calPointSum",resultData.result.data.rows[0].calPointSum);
								});
							}});
							
							break;
					}
				},
				
				onChange : function(name, value) {//onChange START
					if(name == "searchYm") {
						PAGE.FORM1.setItemValue("searchYm", curMnth);
					}

				},
				onValidateMore : function (data){
					if( (data.searchYm == "" || data.searchYm.trim() == "") && (data.subscriberNo == "" || data.subscriberNo.trim() == "") ){
						this.pushError("searchYm", "연동년월, 전화번호", "검색할 값을 입력하세요");
						
					}
				}
			},
			
			GRID1 : {
				css : {
					height : "530px"
				},
				title : "조회결과",
				header : "연동년월,청구년월,고객명,지급포인트,정산금액,지급결과",	//6
				columnIds : "linkYm,billYm,cusNm,reqPoint,calPoint,resultCd",
				widths : "150,150,200,120,120,200",
				colAlign : "center,center,left,right,right,left",
				colTypes : "roXdm,roXdm,ro,ron,ron,ro",
				colSorting : "str,str,str,str,str,str",
				paging : true,
				pageSize:20,
				onButtonClick : function(btnId) {

				}
				
			},
			
			FORM2 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"center", labelWidth:150, blackOffset:0}
					, {type:"block", list:[
										{type:"input", label:"지급포인트 합계", name:"reqPointSum", width:200, offsetLeft:50, numberFormat:"0,000,000,000", validate:"ValidInteger"}
										, {type:"newcolumn"}
										, {type:"input", label:"정산금액 합계", name:"calPointSum", width:200, offsetLeft:30, numberFormat:"0,000,000,000", validate:"ValidInteger"}
					                	]}
				],
			},
						
			
	};

	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
		    buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.ui.createForm("FORM2");

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0200",etc1:"tmoney"}, PAGE.FORM1, "resultCd"); // 검색구분
			}
			
	};
    
</script>
