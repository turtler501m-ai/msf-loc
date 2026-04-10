<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : agency_sttcmgmt_0020.jsp
   * @Description : 대리점 가입자이용통계  현황
   * @Modification Information
   *
   *   수정일         수정자                   수정내용
   *  -------    --------    ---------------------------
   *  2009.02.01            최초 생성
   *
   * author 실행환경 개발팀
   * since 2009.02.01
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
	
	function setCurrentAgoMonth(form, name)
	{
		var today = new Date();

		var mm = today.getMonth()-1; //January is 0!
		var yyyy = today.getFullYear();

		form.setItemValue(name, new Date(yyyy,mm));
	}

	function setCurrentDate(form, name)
	{
		//var today = new Date().format("yyyy-MM-dd");

		form.setItemValue(name, new Date());
		
	}
	
	function fnMonth(mon) {
		var month;
		mon = Number(mon);
		mon--;
		
		if(Number(mon) < 10) {
			month = "0"+mon;
		} else {
			month = mon;
		}
		
		return month;
	}
	
	
		var DHX = {

			// ----------------------------------------
			FORM1 : {

				title : "",
				items : [ 	
							 {type:"block", list:[
								 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
								 {type: "calendar", dateFormat: "%Y-%m", serverDateFormat:"%Y%m", name: "searchDate", label: "개통년월", value: "", calendarPosition: "bottom", inputWidth: 100, labelWidth:50},
								 {type : "newcolumn", offset:3},								
								 {type : "newcolumn"}								
								
							 ]},// 1row
							 {type : "newcolumn", offset:10},
							 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
						],
						onValidateMore : function(data){
							if(data.searchDate==null || data.searchDate==""){
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								this.pushError("searchDate", "개통년월", "는 핊수입력입니다.");
							 }
							
							  var today = new Date().format("yyyymm");
							  var searchYear = data.searchDate.substring(0,4);
							  var searchMonth = fnMonth(data.searchDate.substring(4));
							  var searchDt = new Date(searchYear,searchMonth).format("yyyymm");
							
							 if(today==searchDt){
								 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								 this.pushError("searchDate", "개통년월", "은 현재월 이전으로 검색가능합니다.");
							 }

							},

							onButtonClick : function(btnId) {
			
								var form = this;
								var searchData =  PAGE.FORM1.getFormData(true);
								 console.log("searchData",searchData);
								
								switch (btnId) {
			
									case "btnSearch":
										var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
										PAGE.FORM1.setItemValue("btnCount1", btnCount2)
										if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
										
										PAGE.GRID1.list(ROOT + "/pps/agency/sttcmgmt/SttcSubscribersMgmtList.json", form, {
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
				header : "계약번호,이름, 요금제명, 상태, 개통일,정지일, 해지일, 사용기간, 총충전금액, 최근충전일, 음성사용액, 데이터사용액, 총사용액, 판매점",
				columnIds : "contractNum,subLinkName,serviceNm,subStatusNm,enterDate,statusStopDt,statusCancelDt,useTerm,totRcgCharge,lastBasicChgDt,strTotVocCharge,strTotPktCharge,strUseCharge,agentSaleNm",
				widths : "100,150,120,100,100,100,100,100,100,100,100,100,100,130", //총 1500
				colAlign : "center,left,left,left,center,center,center,right,right,left,right,right,right,left",
				colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,str,str,int,str,str,str,str,str,str",
				paging :true,
				pagingStyle :2,
				pageSize :15,
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
							
							 var searchData =  PAGE.FORM1.getFormData(true);
							
							 mvno.cmn.download('/pps/agency/sttcmgmt/SttcSubscribersMgmtListExcel.json'+"?menuId=PPS2500002",true,{postData:searchData});

							
							/* var url = ROOT + "/pps/hdofc/sttcmgmt/SttcOpenMgmtListExcel.json";
							mvno.cmn.ajax(url, PAGE.FORM1 , 
									function(result) {
										 var result = result.result;
		                                  var retCode = result.retCode;
		                                  var rCode = result.code;
	                         
		                                  var msg = result.msg;
		                                  if(rCode == "OK") {
												alert(rCode);
		                            		  mvno.ui.notify(msg);
		                            		 
		                                  }
		                                  else {
		                                	  mvno.ui.notify(msg);
		                                     
		                                  }
									}); */
							break;
							
						
							
						

					}
				}
			}

	
		

			
			
			
			//-------------------------------------------------------------------------------------
									

		};

		var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
			buttonAuth: ${buttonAuth.jsonString},
				
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				setCurrentAgoMonth(PAGE.FORM1,"searchDate");
				
				
			}

		};
		
		
	</script>
