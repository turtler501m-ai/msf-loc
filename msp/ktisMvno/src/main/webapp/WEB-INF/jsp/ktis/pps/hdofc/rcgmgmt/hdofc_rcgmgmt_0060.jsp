<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_rcgmgmt_0060.jsp
   * @Description : 본사  충전관리 일괄충전
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
						 {type:"block", list:[
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startStopDt",label : "정지일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100},
							 {type : "newcolumn"},
							 {type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"right", labelWidth:10, blockOffset:0},
								{type : "calendar",dateFormat : "%Y-%m-%d",label:"~", serverDateFormat: "%Y%m%d",name : "endStopDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100},
							 ]},
							 {type : "newcolumn", offset:10}, 
							 {type : "input",label : "잔액 ",name : "startRemains", width:100, maxLength:10, validate:"ValidInteger"},
							 {type : "newcolumn"},
							 {type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"right", labelWidth:11, blockOffset:0},
							 	{type : "input",label : "~",name : "endRemains", width:100, maxLength:10, validate:"ValidInteger"}
							 ]}
						 ]},// 1row
						 {type:"block", list:[
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startExpireDt",label : "만료일자",value : "",enableTime : false,calendarPosition : "center",inputWidth : 100},
							 {type : "newcolumn"},
							 {type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"right", labelWidth:10, blockOffset:0},
								{type : "calendar",dateFormat : "%Y-%m-%d",label:"~", serverDateFormat: "%Y%m%d",name : "endExpireDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100},
							 ]},
							 {type : "newcolumn", offset:10}, 
							 {type : "input",label:"대리점",name : "searchAgentStr",width:100, maxLength:10},	// KimWoong
			                 {type : "newcolumn", offset:3},
			                 {type:"block", list:[
									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
									,{type: "label", label: ":"}
			                 ]},
			                 {type : "newcolumn", offset:3},
			                 {type : "select",name : "searchAgentId", width:100},
							{type : "newcolumn", offset:10},
							{type : "select",label : "요금제",name : "searchFeatures",width:100}, // Kim Woong						 
						 ]},// 2row
						 {type:"block", list:[
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startOpenDt",label : "개통일자",value : "",enableTime : false,calendarPosition : "center",inputWidth : 100},
							 {type : "newcolumn"},
							 {type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"right", labelWidth:10, blockOffset:0},
								{type : "calendar",dateFormat : "%Y-%m-%d",label:"~", serverDateFormat: "%Y%m%d",name : "endOpenDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100},
							 ]},
							{type : "newcolumn", offset:10},
							{type : "select",label:"검색",name : "searchType",width:100},	
		                   	{type : "newcolumn", offset:3},
		                   	{type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:8, labelHeight:15, blockOffset:0},
								{type : "label", label:":"}
		                   	 ]},
		                   	{type : "newcolumn"},
		                   	{type:"block", list:[
		         				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0},
		                   		{type : "input", label:":",name : "searchNm", width:100, maxLength:11, validate:"ValidInteger", label:"검색"}
		                   	]},
							 {type : "newcolumn", offset:10},
							 {type : "select" ,label:"상태",name : "subStatus", width:100}				 
						 ]},// 3row
						 {type:"block", list:[
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type : "select" ,label:"소진여부",name : "emptyRemains", width:100},
							 {type : "newcolumn", offset:124},
							 {type : "input", label:"금액",name : "rechargeAmount", width:100, maxLength:10, validate:"ValidInteger"},	
							 {type : "newcolumn", offset:13},
							 {type : "button",name : "btnReg",value : "일괄충전"}
						 ]},// 4row
						 {type : "newcolumn", offset:10},	
						 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
						 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
						 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search4"} 
					],
				onButtonClick : function(btnId) {

					var form = this;

					switch (btnId) {

						case "btnSearchPps":
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							
							PAGE.GRID1.list(ROOT + "/pps/hdofc/rcgmgmt/RcgBatchSearchMgmtList.json", form, {
								callback : function () {
									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								}
							});
							
							break;
							
						case "btnReg":
							var self = PAGE.GRID1;
							
							var rowIds = self.getCheckedRows(0);

							if (! rowIds) {
							    mvno.ui.alert("ECMN0003");
							    return;
							}

							
							var recharge = PAGE.FORM1.getItemValue("rechargeAmount");
							if(recharge == '' || parseInt(recharge) <= 0){
								this.pushError(["rechargeAmount"],"금액","일괄충전 금액을 입력해주세요.");
								mvno.ui.alert("[금액] 일괄충전 금액을 입력해주세요.");
								return;
								
							}
							
						
							mvno.ui.prompt("충전사유", function(result){
								var checkedData = self.classifyRowsFromIds(rowIds, "contractNum");
								var msg = "선택된 데이타가 총" + checkedData.ALL.length + "건입니다." + recharge +"원으로 일괄 충전하시겠습니까?";
								var paramCnt = checkedData.ALL.length;
								var opCode ="ADMIN";
								mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/rcgmgmt/PpsRcgBatch.json", {
								data: checkedData.OLD,
								opCode : opCode,
								recharge : recharge,
								title : result,
								paramCnt : paramCnt
							}, function(results) {
								if(results.result.oRetCd == '0000'){
									mvno.ui.alert(results.result.oRetMsg+"<br> 충전은 사용자 수가 적은 시간에 <br>순차적으로 진행됩니다.");
									PAGE.FORM1.setItemValue("rechargeAmount", "");
									PAGE.GRID1.refresh();
								}else{
									mvno.ui.alert(results.result.oRetMsg);
								}
							},{timeout:60000});
						           
								
							} , { required:true,lines:5,maxLength : 80 });
							
							
							
							
							

							
							break;
					}

				},
				 onValidateMore : function(data){
					 if(data.startStopDt > data.endStopDt){
						 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 this.pushError("startStopDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
					}

					 if(data.startExpireDt > data.endExpireDt){
						 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 this.pushError("startExpireDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
					}

					 if(data.startOpenDt > data.endOpenDt){
						 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 this.pushError("startOpenDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
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
						        	   //alert("대리점이 존재하지 않습니다.");
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
				header : "계약번호, 휴대폰번호, 요금제, 최근충전일자, 잔액소진일자, 상태, 개통일자, 정지일자, 만료일자, 개통대리점, 잔액, 충전횟수, 총충전금,일련번호,실계약번호",
				columnIds : "contractNumLink,subscriberNo,serviceName,lastBasicChgDt,basicEmptDt,subStatusName,enterDate,statusStopDt,basicExpire,agentName,basicRemains,rechargeCnt,rechargeSum,linenum,contractNum",
				widths : "80,100,90,90,90,50,130,90,90,130,70,70,70,0,0", //총 1500
				colAlign : "center,center,center,center,center,center,center,center,center,left,right,right,right,left,center",
				colTypes : "link,roXp,ro,ro,ro,ro,ro,ro,ro,ro,ron,ron,ron,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,str,int,int,int,str,str",
				paging : true,
				multiCheckbox : true,
				pageSize : 500,
				pagingStyle :1,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }, 
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
							var url = "/pps/hdofc/rcgmgmt/RcgBatchSearchMgmtListExcel.json";
							  var searchData =  PAGE.FORM1.getFormData(true);
							  console.log("searchData",searchData);
								
							  mvno.cmn.download(url+"?menuId=PPS1200006",true,{postData:searchData});
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
				//setCurrentMonthFirstDay(PAGE.FORM1, "startOpenDt");
				setCurrentDate(PAGE.FORM1, "startStopDt");
				setCurrentDate(PAGE.FORM1, "endStopDt");
				var selType ="popPin";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType, searchAgentStr : null  } , PAGE.FORM1, "searchAgentId");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsFeatureCodeList.json", null, PAGE.FORM1, "searchFeatures");
				var lovCode="PPS0054";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode  } , PAGE.FORM1, "searchType");
				var lovCode2="PPS0055";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode2  } , PAGE.FORM1, "subStatus");
				var lovCode3="PPS0056";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode3  } , PAGE.FORM1, "emptyRemains");
				

				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("linenum" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("contractNum" ) ,true);

			}

		};
		
		
	</script>

