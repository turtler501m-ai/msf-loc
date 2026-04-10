<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_cardmgmt_0070.jsp
   * @Description : 본사 선불카드관리 PIN 현황통계
   * @Modification Information
   *
   *   수정일         수정자                   수정내용
   *  -------    --------    ---------------------------
   *  2009.02.01            최초 생성
   *
   * author 실행환경 개발팀
   * since 2020.12.15
   *
   * Copyright (C) 2009 by MOPAS  All right reserved.
   */
   %>
	
	
	<div id="FORM1" class="section-search"></div>
	<div id="GRID1" class="section-full"></div>  
	
	<!-- Script -->
	<script type="text/javascript">

	function setCurrentMonthFirstDay(form, name)
	{
		var today = new Date();

		var mm = today.getMonth(); //January is 0!
		var yyyy = today.getFullYear();

		form.setItemValue(name, new Date(yyyy,mm,01));
	}

	function setCurrentDate(form, name)
	{
		//var today = new Date().format("yyyy-MM-dd");

		form.setItemValue(name, new Date());
		
	}
	
	function setCurrentFirstMonth(form, name)
	{
		var today = new Date();

		var yyyy = today.getFullYear();

		form.setItemValue(name, new Date(yyyy,00,01));
		
	}

	
	
	
		var DHX = {

			// ----------------------------------------
			FORM1 : {

				title : "",
				items : [ 	
							 {type:"block", list:[
								 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
								 {type : "calendar",dateFormat : "%Y-%m",serverDateFormat: "%Y%m",name : "startDt",label : "생성월",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
								 {type : "newcolumn", offset:3},
								 {type:"block", list:[
									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
									,{type: "label", label: "~"},
			                   	 ]},	 
			                     {type : "newcolumn", offset:3}, 
								 {type : "calendar",dateFormat : "%Y-%m",serverDateFormat: "%Y%m",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
								 {type : "newcolumn"},
								 {type : "checkbox", label : "월별", width : 50, value:"Y", labelWidth:60,name:"searchMonth"},
								 {type : "newcolumn"},
								 {type : "checkbox", label : "권종별", width : 50, value:"Y", labelWidth:60,name:"searchStartPrice"},
								 {type : "newcolumn"},
								 {type:"label", name:"formLabel", labelWidth:200},
								 {type : "newcolumn"}
								
							 ]},// 1row
							 {type : "newcolumn", offset:10},
							 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
						],

						onKeyUp : function(inp, ev, name, value)
						{
							if(ev.keyCode == 13)
							{
								switch(name)
								{
									case "searchDealerStr":
										
										break; 
										
									default :
										if (this.getItemType(name) == 'input'){
											//대리점검색시 엔터클릭시 검색안되도록
											this.setItemFocus("btnSearchPps");	// input focus 가 남아있으면.. Enter 키가 계속먹음.
											this.callEvent("onButtonClick", ["btnSearchPps"]);		// 조회버튼 이름은 'btnSearch' 로!
										}
									
										break;
								
								}	
							}
						},
						onValidateMore : function(data){
							/*
							 if(data.startDt > data.endDt){
								 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
							 }
							*/
						}
						,onButtonClick : function(btnId) {

							var form = this;
		
							switch (btnId) {
		
								case "btnSearch":
									var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
									PAGE.FORM1.setItemValue("btnCount1", btnCount2)
									if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
									
									PAGE.GRID1.list(ROOT + " /pps/hdofc/cardmgmt/PinInfoStatsMgmtList.json", form, {
										callback : function () {
											PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
											PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										}
									});
									
									break;
		
							}

				}
			},

			// ----------------------------------------
			GRID1 : {

				css : {
					height : "550px"
				},
				title : "조회결과",
				header : "권종,생성월,수량,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,금액,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan",
				header2 :"#rspan,#rspan,생성개수,미출고,출고,개통,충전,정지,폐기,기타,유통수량,생성금액,미출고금액,출고금액,개통금액,충전금액,정지금액,폐기금액,기타,유통중인카드",
				columnIds : "startPrice,crDate,totCnt,crCnt,otCnt,opCnt,reCnt,stCnt,clCnt,etcCnt,slCnt,totAmt,crAmt,otAmt,opAmt,reAmt,stAmt,clAmt,etcAmt,slAmt",
				widths : "110,80,80,80,80,80,80,80,80,80,80,110,110,110,110,110,110,110,110,110", //총 1500
				colAlign : "center,center,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right",
				colTypes : "ro,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron",
				colSorting : "str,str,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int",
				paging : true,
				pageSize :500,
				pagingStyle :2,
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
							   return;
							}
							//alert(totCnt);
							if(totCnt>5000)
							{
								mvno.ui.notify("엑셀출력건수가 5,000건이상이면 시간(수분~수십분)이 걸릴수 있습니다.\n 잠시 기다려주시기 바랍니다.");
							   
							}
							
							var url = ROOT +"/pps/hdofc/cardmgmt/PinInfoStatsMgmtListExcel.json";
							 var searchData =  PAGE.FORM1.getFormData(true);

							 	console.log("searchData",searchData);
							
						  		mvno.cmn.download(url+"?menuId=PPS1400007",true,{postData:searchData});
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
				PAGE.FORM1.getInput("startDt").setAttribute("autocomplete","off");
				PAGE.FORM1.getInput("endDt").setAttribute("autocomplete","off");
				PAGE.FORM1.setItemLabel("formLabel", "<span style='color:red;'> * 금일 00시 업데이트</span>");
				//setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
				//setCurrentFirstMonth(PAGE.FORM1, "startDt");
				//setCurrentDate(PAGE.FORM1, "endDt");
				
				

			}

		};
		
		
	</script>
