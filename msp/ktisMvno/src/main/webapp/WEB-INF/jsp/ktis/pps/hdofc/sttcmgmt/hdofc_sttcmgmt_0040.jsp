<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_sttcmgmt_0040.jsp
   * @Description : 본사  통계관리 예치금출금현황
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
	<div id="GROUP1">
		<div id="FORM2" class="section-box" ></div>
		<div id="TABBAR1"></div>  
		
			<div id="TABBAR1_a1" style="display:none;">
				<div class="row"><!-- 2단 이상 구성시 class="row" div 추가 -->
					<div id="FORM4" class="c5" ></div><!-- c3=30%, c5=50% -->
					<div id="FORM14" class="c5"></div>
				</div>  
			</div>  
			
			<div id="TABBAR1_a2" style="display:none; padding-top:10px;">
				<div id="FORM5"></div> 
			</div>  
			
			<div id="TABBAR1_a3" style="display:none">
			<div id="FORM7"></div> 
				<div id="GRID4"></div>  
			</div> 
			<div id="TABBAR1_a4" style="display:none; padding-top:10px;">
				<div id="GRID5"></div>   
			</div> 
			<div id="TABBAR1_a5" style="display:none; padding-top:10px;">
				<div id="FORM8" class="section-search"></div> 
				<div id="GRID6"></div>   
			</div>
			<div id="TABBAR1_a6" style="display:none; padding-top:10px;">
				<div id="FORM9" class="section-search"></div> 
				<div id="GRID7"></div>   
			</div> 
			<div id="TABBAR1_a7" style="display:none; padding-top:10px;">
				<div id="GRID8"></div>   
			</div>  	  	 	 	  	 	
			<div id="TABBAR1_a8" style="display:none; padding-top:10px;">
				<div id="GRID9"></div>
			</div>   	
	</div>
	
		<!-- 상담등록 -->
		<div id="FORM10"></div>
		<!-- sms보내기 --> 
		<div id="FORM11"></div>
		<!-- 엑셀출력 --> 
		<div id="FORM12"></div>
		<!-- 대리점변경  -->
		<div id="FORM13"></div>
		<!-- 이미지등록/변경  -->
		<div id="FORM15"></div>
		<!-- 국적/SMS 언어  -->
		<div id="FORM16"></div>      
	
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


		var DHX = {

			// ----------------------------------------
			FORM1 : {

				title : "",
				items : [ 	
							{type:"block", list:[
							 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
							 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "출금일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
							 {type : "newcolumn", offset:3},
							 {type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
								,{type: "label", label: "~"},
		                   	 ]},	 
		                     {type : "newcolumn", offset:3}, 
							 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
							 {type : "newcolumn"},
							 {type : "checkbox", label : "월별", width : 50, labelWidth:40,name:"searchReqSrc"},
							 {type : "newcolumn"},
							 {type : "checkbox", label : "대리점별", width : 50, labelWidth:50,name:"searchAgentId"},
							 {type : "newcolumn"},
							 {type : "checkbox", label : "출금유형", width : 50, labelWidth:50,name:"searchDepositType"},
							 {type : "newcolumn"},
							 
						 ]},// 1row
						 {type : "newcolumn", offset:10},
						 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
						 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
						 {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"}
						],

						onValidateMore : function(data){
							if(data.startDt==null || data.startDt==""){
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								this.pushError("startDt", "시작일시", "는 핊수입력입니다.");
							 }

							if(data.endDt==null || data.endDt==""){
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								this.pushError("endDt", "종료일시", "는 핊수입력입니다.");
							 }

							 if(data.startDt > data.endDt){
								 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
							}
								

							},

				onButtonClick : function(btnId) {

					var form = this;

					switch (btnId) {

						case "btnSearch":
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							
							PAGE.GRID1.list(ROOT + "/pps/hdofc/sttcmgmt/SttcDepositOutMgmtList.json", form, {
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
				header : "기간,대리점별,출금유형,출금액",
				columnIds : "depositDate,agentNm,depositTypeNm,recharge",
				//widths : "50,100,50,100,100,100,100,100,100,50,100,100,100,50,100,*", //총 1500
				colAlign : "center,center,center,right",
				colTypes : "ro,ro,ro,ron",
				colSorting : "str,str,str,int",
				paging: true,
				pageSize :15,
				pagingStyle :2,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" } 
						
					}
				},
				
		        			
				onButtonClick : function(btnId, selectedData) {

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
							
							var url = "/pps/hdofc/sttcmgmt/SttcDepositOutMgmtListExcel.json";
							
							  var searchData =  PAGE.FORM1.getFormData(true);
							  console.log("searchData",searchData);
								
							  mvno.cmn.download(url+"?menuId=PPS1500004",true,{postData:searchData});
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
				//setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
				setCurrentDate(PAGE.FORM1, "startDt");
				setCurrentDate(PAGE.FORM1, "endDt");

			}

		};
		
		
	</script>
