<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_datamgmt_0030.jsp
   * @Description : CMS충전대상
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
								 {type: "calendar", dateFormat: "%Y-%m", serverDateFormat:"%Y%m", name: "searchDate", label: "정산년월", value: "", calendarPosition: "bottom", inputWidth: 100, labelWidth:50},
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
								this.pushError("searchDate", "정산년월", "는 핊수입력입니다.");
								

							 }
							
							  var today = new Date().format("yyyymm");
							  var searchYear = data.searchDate.substring(0,4);
							  var searchMonth = fnMonth(data.searchDate.substring(4));
							  var searchDt = new Date(searchYear,searchMonth).format("yyyymm");
							
							 if(today==searchDt){
								 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								 this.pushError("searchDate", "정산년월", "은 현재월 이전으로 검색가능합니다.");
								
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
										
										PAGE.GRID1.list(ROOT + "/pps/hdofc/datamgmt/CmsDataInfoMgmtList.json", form, {
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
				header : "계약번호,충전요청,결제방식, 충전금액,실입금액,충전일자,충전후잔액,개통대리점,판매점",
				columnIds : "contractNum,reqSrc,rechargeMethod,amount,inAmount,rechargeDate,basicRemains,openAgentNm,rechargeAgentName",
				widths : "100,120,120,100,100,100,100,150,150", //총 1500
				colAlign : "center,left,left,right,right,center,right,left,left",
				colTypes : "ro,ro,ro,ro,ro,roXd,ro,ro,ro",
				colSorting : "str,str,str,int,int,str,int,str,str",
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
							
							 mvno.cmn.download('/pps/hdofc/datamgmt/CmsDataInfoMgmtListExcel.json'+"?menuId=PPS1600003",true,{postData:searchData});

							
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
