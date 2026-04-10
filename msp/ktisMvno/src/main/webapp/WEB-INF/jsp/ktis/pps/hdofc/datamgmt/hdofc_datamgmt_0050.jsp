<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_datamgmt_0050.jsp
   * @Description : 본사  데아타관리  환수정산대상
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

	function getSelectBoxYearsDataFromCurrentYear(amount)
	{
		var reJsonArr = [
			    
			];
		var currentYear = new Date().getFullYear();

		for(amount ; 0 < amount ; amount--)
		{

			reJsonArr.push({ 
		        "label" : currentYear+'년',
		        "value"  : currentYear
		    });

			currentYear--;
		}

		return reJsonArr;
		
	}



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



	function goCustDetail(contractNum){

		var url = "/pps/hdofc/custmgmt/HdofcCustMgmtInfoDetailForm.do";
		var title = "고객정보상세"
		var menuId = "PPS1100007";

		var myTabbar = window.parent.mainTabbar;

        if (myTabbar.tabs(url)) {
        	myTabbar.tabs(url).close(); // 기존에 있는 탭을 닫기
        }

        myTabbar.addTab(url, title, null, null, true);
        myTabbar.tabs(url).attachURL(url + "?menuId=" + menuId+"&contractNum="+contractNum);

        // 잘안보여서.. 일단 살짝 Delay 처리 
        setTimeout(function() {
            //mainLayout.cells("b").progressOff();
        }, 100);
	}


		var DHX = {

			// ----------------------------------------
			FORM1 : {

				title : "",
				items : [ 	
						 {type:"hidden", name:"dataPageType", value:"basicPage"},	
						 {type:"block", list:[					 
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type: "calendar", dateFormat: "%Y-%m", serverDateFormat:"%Y%m", name: "searchDate", label: "개통년월", value: "", calendarPosition: "bottom", inputWidth: 100, labelWidth:50},
							 {type : "newcolumn", offset:10},	                     
							 {type:"block", list:[
													{type:"settings", position:"label-left", labelAlign:"left", labelWidth:30, labelHeight:15, blockOffset:0},
													{type : "input",label : "총충전액",name : "searchTotRcgCharge", width:100,maxlength:20,labelWidth:50, numberFormat:"0,000,000,000", offsetLeft:10, validate:'ValidInteger'} 
							 ]},
							 {type : "newcolumn", offset:10},
							 {type:"block", list:[
													{type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, labelHeight:15, blockOffset:0},
							 						{type : "input",label : "사용액",name : "searchRealCharge", width:100,maxlength:20,labelWidth:60, numberFormat:"0,000,000,000", offsetLeft:10, validate:'ValidInteger'} 
							 ]},
			                 {type : "newcolumn", offset:3}
			                 
			                 
						 ]},// 1row
						
						 {type : "newcolumn", offset:10},
						 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
						 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
						 {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
					],

				onButtonClick : function(btnId) {

					var form = this;

					switch (btnId) {

						case "btnSearch":
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							
							PAGE.GRID1.list(ROOT + "/pps/hdofc/datamgmt/DataInfoClawbackMgmtList.json", form, {
								callback : function () {
									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								}
							});
							
							break;

					}

				},
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
				}
			},

			// ----------------------------------------
			GRID1 : {

				css : {
					height : "550px"
				},
				title : "조회결과",
				header : "계약번호, 계약자, 국적, 요금제, 상태, 개통일, 정지일, 해지일,만료일, 모델명, 사용기간, 총시도수, 총시도액,조정금액,무료충전액,유료충전액,무료취소액,유료취소액,총충전액, 최근충전일,사용횟수,사용분,사용액,일차감,데이타량(M),데이타사용액,잔액,개통대리점,판매점,일차감건수,일차감액,국내음성(분),국내통화료,국제음성(분),국제통화료,국내데이터(M),국내데이타료,국내문자건수,국내문자료,국제문자건수,국제문자료,기타건수,기타금액",
				columnIds : "contractNum,subLinkName,adNationNm,serviceNm,subStatusNm,enterDate,statusStopDt,statusCancelDt,basicExpire,modelName,useTerm,tryRcgCnt,tryRcgCharge,rcgEditCharge,rcgFreeCharge,rcgPayCharge,rcgFreeCancelCharge,rcgPayCancelCharge,totRcgCharge,lastBasicChgDt,useCnt,strUseDur,strUseCharge,strDayCharge,strUsePkt,strTotPktCharge,strBasicRemains,agentNm,agentSaleNm,dayCnt,strDayCharge,strVocNDur,strVocNCharge,strVocIDur,strVocICharge,strPktNDur,strPktNCharge,smsNCnt,strSmsNCharge,smsICnt,strSmsICharge,etcCnt,strEtcCharge",
				widths : "70,120,80,100,60,80,80,80,80,120,60,70,60,70,70,70,70,70,80,80,60,60,60,60,80,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100", //총 1500
				colAlign : "center,center,center,center,center,center,center,center,center,left,right,right,right,right,right,right,right,right,right,center,right,right,right,right,right,right,right,right,right,right,right,right,center,right,right,right,right,right,right,right,right,right,right",
				colTypes : "ro,ro,ro,ro,ro,roXd,roXd,roXd,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,str,int,int,int,int,int,int,int,int,int,str,int,int,int,int,int,int,int,str,str,int,int,int,int,int,int,int,int,int,int,int,int,int",
				paging : true,
				pageSize :15,
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
							
							var url = "/pps/hdofc/datamgmt/DataInfoClawbackMgmtListExcel.json";
							  var searchData =  PAGE.FORM1.getFormData(true);
							  mvno.cmn.download(url+"?menuId=PPS1600005",true,{postData:searchData});
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

				
				setCurrentAgoMonth(PAGE.FORM1,"searchDate");
				
			}	

		};
		
		
	</script>
