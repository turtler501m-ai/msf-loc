<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_sttcmgmt_0080.jsp
   * @Description : 본사 ARPU분석통계 현황
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

	function setCurrentDate(form, name)
	{
		//var today = new Date().format("yyyy-MM-dd");

		form.setItemValue(name, new Date());
		
	}
	
	function setCurrentAgoMonth(form, name)
	{
		var today = new Date();

		var mm = today.getMonth()-1; //January is 0!
		var yyyy = today.getFullYear();

		form.setItemValue(name, new Date(yyyy,mm));
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
								 {type:"block", list:[
									 {type: "radio", name: "searchMonthType", value: "useMonth", label: "사용월", checked: true},
									 {type : "newcolumn"},
									 {type: "radio", name: "searchMonthType", value: "openMonth", label: "개통월"}
								 ]},
								 {type : "newcolumn", offset:3},								 
								 {type: "calendar", dateFormat: "%Y-%m", serverDateFormat:"%Y%m", name: "searchDate", value: "", calendarPosition: "bottom", inputWidth: 100},								 
								 {type : "newcolumn"},
								 {type : "checkbox", label : "대리점", width : 50, labelWidth:60,name:"searchAgentId"},
								 {type : "newcolumn"},								
								 {type : "checkbox", label : "국가별", width : 50, labelWidth:60,name:"searchNation"},
								 {type:"newcolumn"},
								 {type:"select", width:100, label:"사용일수",name:"searchType", userdata:{lov: 'PPS0060'}, offsetLeft:10},
								 {type:"newcolumn"},
								 {type : "input",name : "searchName", width:100,maxlength:20,numberFormat:"0,000,000,000", offsetLeft:10, validate:'ValidInteger'}, 
								 {type : "newcolumn"}
								
							 ]},// 1row
							 {type : "newcolumn", offset:10},
							 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
						],
						onValidateMore : function(data){
							
								if(data.searchName!=null && data.searchName!=""){
									if(data.searchType==null ||data.searchType==""){
										PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
										PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										this.pushError("searchType", "선택사항", "을 선택해주세요.");
									}
								}
								
								if(data.searchDate==null || data.searchDate==""){
									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
									this.pushError("searchDate", "검색월", "을 선택해주세요.");
								}

						},
						onButtonClick : function(btnId) {
			
								var form = this;
			
								switch (btnId) {
			
									case "btnSearch":	
										var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
										PAGE.FORM1.setItemValue("btnCount1", btnCount2)
										if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
										
										PAGE.GRID1.list(ROOT + " /pps/hdofc/sttcmgmt/SttcArpuMgmtList.json", form, {
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
				header : "요금제,가입자수,ARPU,성별,#cspan,#cspan,성별ARPU,#cspan,#cspan,사용일수(평균),무료충전액(평균),유료충전액(평균),사용금액(평균),#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,대리점,국적",
				header2 :"#rspan,#rspan,#rspan,남성,여성,기타,남성,여성,기타,#rspan,#rspan,#rspan,기본료(평균),국내음성(평균),국제음성(평균),국내문자(평균),국제문자(평균),국내데이터(평균),국제데이터(평균),기타(평균),#rspan,#rspan",
				columnIds :"socNm,totCnt,apruCharge,totMCnt,totFCnt,totEtcCnt,avgMUseCharge,avgFUseCharge,avgEtcUseCharge,avgUseTerm,avgRcgFreeCharge,avgRcgPayCharge,avgDayCharge,avgVocNCharge,avgVocICharge,avgSmsNCharge,avgSmsICharge,avgPktNCharge,avgPktICharge,avgEtcCharge,agentNm,adNationNm",
				widths : "160,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100", //총 1500
				colAlign : "center,center,center,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,left,left",
				colTypes : "ro,ro,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ro,ro",
				colSorting : "str,str,str,int,int,int,int,int,int,int,int,int,int,int,intint,int,int,int,int,str,str",
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
							
							 mvno.cmn.download('/pps/hdofc/sttcmgmt/SttcArpuMgmtListExcel.json'+"?menuId=PPS1500008",true,{postData:searchData});

							
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
				//setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
				setCurrentAgoMonth(PAGE.FORM1,"searchDate");
			}

		};
		
		
	</script>
