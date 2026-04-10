<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_rcgmgmt_0040.jsp
   * @Description : 본사  충전관리  가상계좌 관리
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
	<div id="GRID1"  class="section-full"></div>  
	    
	
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

	

	function cmsFormRefresh(contractNumStr)
	{
		mvno.cmn.ajax(
				ROOT + " /pps/hdofc/custmgmt/PpsUserCmsInfo.json",
				{
					contractNum : contractNumStr
				}
				, function(result) {

					var result = result.result;
					var data = result.data;
					PAGE.FORM7.setItemValue("bankCode","");
					PAGE.FORM7.setItemValue("bankCode",data.cmsBankCode);
					PAGE.FORM7.setItemValue("bankUserName",data.cmsUserName);
					PAGE.FORM7.setItemValue("bankPeopleId",data.cmsUserSsn);
					PAGE.FORM7.setItemValue("bankAccount",data.cmsAccount);
					PAGE.FORM7.setItemValue("cmsType",data.cmsChargeType);
					
					PAGE.FORM7.setItemValue("cmsLastRemains",data.cmsTryRemains);
					PAGE.FORM7.setItemValue("cmsRequestAmount",data.cmsCharge);
					PAGE.FORM7.setItemValue("cmsScheduleDate","");
					PAGE.FORM7.setItemValue("cmsScheduleDate",data.cmsChargeDate);
					PAGE.FORM7.setItemValue("cmsScheduleMonth",data.cmsChargeMonth);

					
					PAGE.FORM7.setItemValue("cmsNowChargeAmount","");
				});
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

	function goAgentDeposit(vacIdStr,pSize,pIdx){

		var startSize = ((pIdx-1)*pSize);
		var endSize = pIdx*pSize;
		var rowId = "";
		
		for (var i=startSize; i<endSize; i++) {
			var vacId = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("vacId")).getValue();
			
			if(vacIdStr == vacId){
				rowId = i;
				break;
			}
		 }
		var grid1 = PAGE.GRID1;
		var agentId = 
			  grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("agentId" ) ).getValue();

		var url = "/pps/hdofc/orgmgmt/AgentDepositMgmtDetailForm.do";
		var title = "예치금 상세";
		var menuId = "PPS1300004";
		var createType = "BONSA";

		var myTabbar = window.parent.mainTabbar;

        if (myTabbar.tabs(url)) {
        	myTabbar.tabs(url).close(); // 기존에 있는 탭을 닫기
        }

        myTabbar.addTab(url, title, null, null, true);
        myTabbar.tabs(url).attachURL(url + "?menuId=" + menuId+"&agentId="+agentId+"&createType="+createType);

        // 잘안보여서.. 일단 살짝 Delay 처리 
        setTimeout(function() {
            //mainLayout.cells("b").progressOff();
        }, 100);
		
		
	}

	function goVacCancel(vacIdStr,pSize,pIdx){

		var startSize = ((pIdx-1)*pSize);
		var endSize = pIdx*pSize;
		var rowId = "";
		
		for (var i=startSize; i<endSize; i++) {
			var vacId = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("vacId")).getValue();
			
			if(vacIdStr == vacId){
				rowId = i;
				break;
			}
		 }
		 
		var grid1 = PAGE.GRID1;
		var vacId = 
			  grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("vacId" ) ).getValue();
		var userType = 
			  grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("userType" ) ).getValue();
		var userNumber = "";
		if(userType == "A"){
			userNumber = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("agentId" ) ).getValue();
		}else if(userType == "U"){
			var contractNum = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("contractNum" ) ).getValue();
			userNumber = contractNum.substring(0,9);
		}
		var opCode = "BACK";
		var opMode = "B";

		mvno.cmn.ajax4confirm("현재선택한 가상계좌를 회수하시겠습니까?", ROOT + "/pps/hdofc/custmgmt/PpsVacProcs.json", {
			vacId: vacId,
			userType: userType,
			userNumber: userNumber,
			opCode: opCode,
			opMode: opMode
		}, function(results) {
			var result = results.result;
			mvno.ui.notify(result.retMsg);
			if(result.retCode == "0000"){
				PAGE.GRID1.refresh();
			}

		});

		
	}


		var DHX = {

			// ----------------------------------------
			FORM1 : {

				title : "",
				items : [ 	
						 {type:"block", list:[
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "부여일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : "", readonly:true},
							 {type : "newcolumn", offset:5},
							 {type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
								,{type: "label", label: "~"},
		                   	 ]},	 
		                     {type : "newcolumn", offset:5}, 
							 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : "", readonly:true},
							 {type : "newcolumn", offset:10},
							 {type : "select",label : "이용자구분",name : "searchRcgVacUserType",width:100},
			                 {type : "newcolumn", offset:10},
			                 {type : "select",label : "은행명",name : "virBankNm",width:100},// KimWoong
			                 {type : "newcolumn", offset:10},
			                 {type : "select",label : "부여상태",name : "searchStatus",width:100}
							 						 
						 ]},// 1row
						 {type:"block", list:[
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type : "input",label:"대리점",name : "searchAgentStr",width:100, maxLength:10},	// KimWoong
			                 {type : "newcolumn", offset:7},
			                 {type:"block", list:[
									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
									,{type: "label", label: ":"}
			                 ]},
			                 {type : "newcolumn", offset:3},
			                 {type : "select",name : "searchAgentId", width:100},
							 {type : "newcolumn", offset:10},
							 {type : "select",label:"검색",name : "searchType",width:100, maxLength:11},	
		                   	{type : "newcolumn", offset:7},
		                   	{type:"block", list:[
									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
									,{type: "label", label: ":"}
			                   	 ]},	 
			                    {type : "newcolumn", offset:5},
			                	{type:"block", list:[
			     					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0},
			                   		{type : "input",name : "searchNm", width:153, maxLength:30, validate:"ValidInteger", label:"검색"}
			                   	]}						 
						 ]},// 2row
						 {type : "newcolumn", offset:10},
						 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
						 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
						 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search2"} 
					],

				onButtonClick : function(btnId) {

					var form = this;

					switch (btnId) {

						case "btnSearchPps":
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							
							PAGE.GRID1.list(ROOT + "/pps/hdofc/rcgmgmt/RcgVacInfoMgmtList.json", form, {
								callback : function () {
									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								}
							});
							
							break;

					}

				},
				 onValidateMore : function(data){
					 if(data.startDt > data.endDt){
						 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
					}
				 },	
				onKeyUp : function(inp, ev, name, value)
				{
					if(ev.keyCode == 13)
					{
						switch(name)
						{
							case "searchAgentStr":
								var agentStr  = this.getItemValue("searchAgentStr");
								var selType ="popPin";
								//alert(agentStr);
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
										{ 
										  selectType : selType , 
									      searchAgentStr : agentStr
										} 
							      , PAGE.FORM1, "searchAgentId");
								var agentOption =  this.getOptions("searchAgentId");
						        
						        
						           if(agentOption.length==1)
							       {
						        	   //alert("ㄷ대리점이 존재하지 않습니다.");
						           }
						           else if(agentOption.length==2)
							        {

								        var selValue = agentOption[1].value;

						        	   this.setItemValue("searchAgentId",selValue);
						           }      
							      
															      
							      
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
				}
			},

			// ----------------------------------------
			GRID1 : {

				css : {
					height : "550px"
				},
				title : "조회결과",
				header : "은행명, 가상계좌, 이용자구분, 계약번호, 대리점명, 상태, 부여일자, 회수일자, 최종입금일자, 수납횟수, 회수,대리점아이디,이용자구분코드,일련번호",
				columnIds : "vacBankName,vacId,userTypeNm,contractNum,agentName,statusNm,openDate,closeDate,lastPaymentDate,payCount,resetBtn,agentId,userType,linenum",
				widths : "70,130,70,100,100,50,100,100,100,60,73,0,0,0", //총 1500
				colAlign : "left,left,center,center,left,center,center,center,center,right,center,center,center,left",
				colTypes : "ro,ro,ro,link,link,ro,ro,ro,ro,ron,link,ro,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,int,str,str,str,str",
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
							var url = "/pps/hdofc/rcgmgmt/RcgVacInfoMgmtListExcel.json";
							  var searchData =  PAGE.FORM1.getFormData(true);
							  mvno.cmn.download(url+"?menuId=PPS1200004",true,{postData:searchData});
							break;
					}
				}
			}


			//---------------------------------------
			

		};

		var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
			buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {

				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("agentId" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("userType" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("linenum" ) ,true);
				//setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
				setCurrentDate(PAGE.FORM1, "startDt");
				setCurrentDate(PAGE.FORM1, "endDt");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsVacCodeList.json", null, PAGE.FORM1, "virBankNm");
				var selType ="popPin";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType, searchAgentStr : null  } , PAGE.FORM1, "searchAgentId");

					var lovCode="PPS0023";
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode  } , PAGE.FORM1, "searchType");
					var lovCode2="PPS0020";
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode2  } , PAGE.FORM1, "searchRcgVacUserType");
					var lovCode3="PPS0005";
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode3  } , PAGE.FORM1, "searchStatus");
					
			}

		};
		
		
	</script>
