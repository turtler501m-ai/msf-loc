<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_rcgautomgmt_0010.jsp
   * @Description : 선불 본사   자동충전관리
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
		form.setItemValue(name, new Date());
	}

<%@ include file="../../hdofcCustDetail.formitems" %>

var DHX = {

			// ----------------------------------------
	FORM1 : {
		items : [ 	
		          	 
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "newcolumn", offset:3},
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "변경일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:10}, 
						 {type : "select",label : "요금제",name : "searchSoc",width:100},
						 {type : "newcolumn", offset:10}, 
						 {type : "select",label : "충전구분",name : "searchReqType",width:100}
						 ,{type : "newcolumn", offset:10} 
				         ,{type : "select",label : "개통신분증",name : "searchCustIdntNoIndCd",width:100}
					 ]},// 1row
					 {type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						{type : "newcolumn", offset:3},
						{type : "select",label:"검색",name : "searchType",width:100, width:100},	
	                   	{type : "newcolumn", offset:3},
	                   	{type:"block", list:[
 									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
 									,{type: "label", label: ":"}
 			                   	 ]},	 
 			            {type : "newcolumn", offset:3}
 			            ,{type : "input",name : "searchNm", width:100,maxlength:20}
  			            ,{type : "newcolumn", offset:10} 
  			          	,{type : "input",label : "대리점 ",name : "searchAgentNm", width:100,maxlength:20}
						,{type : "newcolumn", offset:3}
						,{type : "select",name : "searchAgentId",width:167}
					 ]},// 2row
					 {type : "newcolumn", offset:10},
					 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search2"},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"} //버튼 여러번 클릭 막기 변수 by han
					 
				],

		onButtonClick : function(btnId) {

			var form = this;

			switch (btnId) {
			
				case "btnSearchPps":
					var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
					PAGE.FORM1.setItemValue("btnCount1", btnCount2)
					if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
					
					PAGE.GRID1.list(ROOT + "/pps/hdofc/rcgautomgmt/rcgAutoHistInfoMgmtList.json", form, {
						callback : function () {
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						}
					});
					
					break;

			}

		}
		,onKeyUp : function(inp, ev, name, value)
		{
			if(ev.keyCode == 13)
			{
				switch(name)
				{
					case "searchAgentNm":
						var agentStr  = this.getItemValue("searchAgentNm");
						//alert(agentStr);
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/rcgautomgmt/ppsRcgAutoAgentList.json",
								{ 
									searchAgentNm : agentStr
								} 
					      , PAGE.FORM1, "searchAgentId");
						var agentOption =  this.getOptions("searchAgentId");
				        
				        
				           if(agentOption.length==1)
					       {
				        	   //alert("ㄷ대리점이 존재하지 않습니다.");
				           }else if(agentOption.length==2)
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
		},onValidateMore : function(data){

			if(data.startDt > data.endDt){
				 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
			}

			if(( data.searchType == null || data.searchType == '' ) && (data.searchNm != null && data.searchNm != '' ))
			{
				 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				 this.pushError("searchType", "검색조건", "을 선택해주세요");							
			}

		}

		
	}
	,GRID1 : {

		css : {
			height : "530px"
		},
		title : "조회결과",
		header : "대리점,요금제명,개통신분증,충전구분,충전금액,등록일,변경일,변경구분,처리자,설명",
		columnIds : "agentNm,socNm,custIdntNoIndCdNm,reqTypeNm,amountNm,regDt,hisDt,hisOpCodeNm,hisAdminNm,remark",
		widths : "150,120,120,100,100,120,120,80,80,200", //총 1500
		colAlign : "center,center,center,center,right,center,center,center,center,left",
		colTypes : "ro,ro,ro,ro,ro,roXd,roXd,ro,ro,ro", //32
		colSorting : "str,str,str,str,str,str,str,str,str,str", //32
		paging : true,
		pageSize :15,
		pagingStyle :2,
		buttons : {
			hright : {
				btnDnExcel : { label : "엑셀다운로드" }
			}
		}
		,onButtonClick : function(btnId) {

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
					
					var url = "/pps/hdofc/rcgautomgmt/rcgAutoHistInfoMgmtListExcel.json";
					var searchData =  PAGE.FORM1.getFormData(true);
					 //console.log("searchData",searchData);
										
					 mvno.cmn.download(url+"?menuId=PPS1300006",true,{postData:searchData});
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
			
			mvno.cmn.ajax4lov(
					ROOT + "/pps/hdofc/rcgautomgmt/ppsRcgAutoAgentList.json"
					, null
					 , PAGE.FORM1, "searchAgentId");
			
			
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/rcgautomgmt/ppsRcgAutoSocList.json", null, PAGE.FORM1, "searchSoc");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0129"}, PAGE.FORM1, "searchReqType");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0130"}, PAGE.FORM1, "searchCustIdntNoIndCd");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0131"}, PAGE.FORM1, "searchType");
			
		}

	};
		
</script>