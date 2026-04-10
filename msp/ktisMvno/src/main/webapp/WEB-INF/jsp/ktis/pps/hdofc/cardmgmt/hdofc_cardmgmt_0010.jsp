<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_cardmgmt_0010.jsp
   * @Description : 본사 선불카드관리 PIN관리
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

<div id="FORM20" class="section-search" ></div>	
	
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

	function goPinInfoData(mngCode)
	{
		mvno.ui.createForm("FORM20");

		mvno.cmn.ajax(
				ROOT + "/pps/hdofc/cardmgmt/PpsPinInfo.json",
				{
					mngCode : mngCode
				},
				function(result) {

					console.log(result.result);
					
					var data1 = result.result.data;	
					var title= "PIN상세보기 - 핀번호:"+data1.pinNumber;
					mvno.ui.updateSection("FORM20", "title", title);
					console.log(data1);
					PAGE.FORM20.setItemValue("mngCode",data1.mngCode);

					PAGE.FORM20.setItemValue("onoffGubunNm",data1.onoffGubunNm);	
					PAGE.FORM20.setItemValue("nowPrice",data1.nowPrice);	
					PAGE.FORM20.setItemValue("outFlagNm",data1.outFlagNm);	
					PAGE.FORM20.setItemValue("outAgentTypeNm",data1.outAgentTypeNm);	
					PAGE.FORM20.setItemValue("outPrice",data1.outPrice);	
					PAGE.FORM20.setItemValue("openFlagNm",data1.openFlagNm);	
					PAGE.FORM20.setItemValue("saleFlagNm",data1.saleFlagNm);	
					PAGE.FORM20.setItemValue("rcgFlagNm",data1.rcgFlagNm);	
					PAGE.FORM20.setItemValue("remark",data1.remark);	
					PAGE.FORM20.setItemValue("expireDate",data1.expireDate);	
					PAGE.FORM20.setItemValue("statusNm",data1.statusNm);	
					PAGE.FORM20.setItemValue("crAdminNm",data1.crAdminNm);	
					PAGE.FORM20.setItemValue("outDate",data1.outDate);	
					PAGE.FORM20.setItemValue("outAgentNm",data1.outAgentNm);	
					PAGE.FORM20.setItemValue("openAdminNm",data1.openAdminNm);	
					PAGE.FORM20.setItemValue("saleAdminNm",data1.saleAdminNm);	
					PAGE.FORM20.setItemValue("rcgDate",data1.rcgDate);	
					PAGE.FORM20.setItemValue("pinGubunNm",data1.pinGubunNm);	
					PAGE.FORM20.setItemValue("startPrice",data1.startPrice);	
					PAGE.FORM20.setItemValue("crDate",data1.crDate);	
					PAGE.FORM20.setItemValue("outAdminNm",data1.outAdminNm);	
					PAGE.FORM20.setItemValue("outDealerNm",data1.outDealerNm);	
					PAGE.FORM20.setItemValue("openDate",data1.openDate);	
					PAGE.FORM20.setItemValue("userInfo",data1.contractNum);		
					PAGE.FORM20.setItemValue("freeFlagNm",data1.freeFlagNm);	
					
				},
				null
				);

				mvno.ui.popupWindowById("FORM20", "핀상세정보", 900, 500, {
		            onClose: function(win) {
		            	if (! PAGE.FORM20.isChanged()) return true;
		            	mvno.ui.confirm("CCMN0005", function(){
		            		win.closeForce();
		            	});
		            }
		        });




	}

	function goCustInfoData(contractNum)
	{
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

	<%@ include file="../../hdofcCustDetail.formitems" %>

	var DHX = {
	
		FORM1 : {
			items : [ 	
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDtCreate",label : "생성일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDtCreate", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						 {type : "newcolumn"},
						 {type : "select",label :"ON/OFF구분",labelWidth:80,name : "searchOnOffGubun",width:100,userdata: { lov: "PPS0025"}}, 
						 {type : "newcolumn"}, 
						 {type : "input",label:"대리점",name : "searchAgentStr",width:80, labelWidth:70},	// KimWoong
		                 {type : "newcolumn", offset:3},
		                 {type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
								,{type: "label", label: ":"}
		                 ]},	 
		                 {type : "newcolumn", offset:5},
		                 {type : "select",name : "searchAgentNm", width:170}  
						  
					 ]},// 1row
					 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
							{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDtOut",label : "출고일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
							{type : "newcolumn", offset:3},
							{type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
								,{type: "label", label: "~"},
								 ]},	 
							{type : "newcolumn", offset:3}, 
							{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDtOut", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
							{type : "newcolumn"},
							{type : "select",label :"구분",labelWidth:80,name : "searchPinGubun",width:100,userdata: { lov: "PPS0026"}}, 
							{type : "newcolumn"}, 
							{type : "input",label:"카드대리점",name : "searchDealerStr",width:80, labelWidth:70},	// KimWoong
		                   	{type : "newcolumn", offset:3},
		                   	{type:"block", list:[
	 									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
	 									,{type: "label", label: ":"}
	 			                   	 ]},	 
	 			                    {type : "newcolumn", offset:5},
	 			                   	{type : "select",name : "searchDealerNm", width:170} 				 
						  
					 ]},// 2row
					 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
							{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDtOpen",label : "개통일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
							{type : "newcolumn", offset:3},
							{type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
								,{type: "label", label: "~"},
								 ]},	 
							{type : "newcolumn", offset:3}, 
							{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDtOpen", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
							{type : "newcolumn"},
							{type : "select",label :"핀상태",labelWidth:80,name : "searchPinStatus",width:100,userdata: { lov: "PPS0027"}}, 
							{type : "newcolumn"}, 
							{type : "select",label:"검색",name : "searchType",width:80, labelWidth:70, userdata: { lov: "PPS0028"}},	
		                   	{type : "newcolumn", offset:3},
		                   	{type:"block", list:[
	 									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
	 									,{type: "label", label: ":"}
	 			                   	 ]},	 
	 			                    {type : "newcolumn", offset:5},
	 			                   	{type : "input",name : "searchNm", width:170}
					 ]},// 3row
					 {type:"block", list:[
											{type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
											{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDtExpire",label : "만료일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
											{type : "newcolumn", offset:3},
											{type:"block", list:[
												{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
												,{type: "label", label: "~"},
												 ]},	 
											{type : "newcolumn", offset:3}, 
											{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDtExpire", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
											{type : "newcolumn"}, 
											{type : "select",label :"충전여부",name : "searchPinRcgFlag",width:100, labelWidth:80,userdata: { lov: "PPS0024"}}, 
											{type : "newcolumn"},
											{type : "select", label :"카드구분", name : "searchFreeFlag", width:80, labelWidth:70}, // KimWoong
											{type : "newcolumn"}, 
											
											
					]},// 4row
					{type:"block", list:[

											{type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
											{type : "select", label :"관리번호", name : "searchManageCodeHeader", width:100}, // KimWoong
											{type : "newcolumn"}, 
											{type:"block", list:[
								   									{type:"settings", position:"label-left", labelAlign:"left",labelHeight:15, blockOffset:0},
								   									{type : "input", label :"", name : "searchManageCodeStart", width:70, labelWidth:0,maxlength:9, validate: 'ValidNumeric'},
																	{type : "newcolumn", offset:5},	
								   									{type: "label", label: "부터",labelWidth:30},
								   									{type : "newcolumn"},
								   									{type : "input", label :"", name : "searchManageCodeEnd", width:70, labelWidth:00,maxlength:9, validate: 'ValidNumeric'},
																	{type : "newcolumn", offset:5},
																	{type: "label", label: "까지",labelWidth:30}
								   			                   	 ]} 
											
					]},// 5row
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search4"} 
				]
				,onValidateMore : function(data){

					 if(data.startDtCreate > data.endDtCreate){
						 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 this.pushError("startDtCreate", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
					}

					 if(data.startDtOut > data.endDtOut){
						 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 this.pushError("startDtOut", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
					}

					 if(data.startDtOpen > data.endDtOpen){
						 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 this.pushError("startDtOpen", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
					}
					 if(data.searchNm!=""){
						 if(data.searchType==""){
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 	this.pushError("searchType", "검색조건", "검색조건을 선택하세요");
						 }
					}
	
				}
				,onKeyUp : function(inp, ev, name, value)
				{
					if(ev.keyCode == 13)
					{
						switch(name)
						{
							case "searchAgentStr":
								var agentStr  = this.getItemValue("searchAgentStr");
								var selType ="popPin";
								//alert(agentStr);
								mvno.cmn.ajax4lov(
										ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
										,{ 
										  selectType : selType , 
									      searchAgentStr : agentStr
										} 
								      ,PAGE.FORM1, "searchAgentNm");
	
								var agentOption =  this.getOptions("searchAgentNm");
							        
								if(agentOption.length==1)
								{
									   
								}
								else if(agentOption.length==2)
								{
									var selValue = agentOption[1].value;
									this.setItemValue("searchAgentNm",selValue);
								}
								break;
	
							case "searchDealerStr":
								var dealerStr  = this.getItemValue("searchDealerStr");
								var selType2 ="dealer";
								//alert(agentStr);
								mvno.cmn.ajax4lov(
										ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
										,{ 
										  selectType : selType2 , 
									      searchAgentStr : dealerStr
										} 
							      		,PAGE.FORM1
							      		,"searchDealerNm");
								var delaerOption =  this.getOptions("searchDealerNm");
								if(delaerOption.length==1)
								{
									  
								}
								else if(delaerOption.length==2)
								{
									var selValue = delaerOption[1].value;
									this.setItemValue("searchDealerNm",selValue);
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
			,onButtonClick : function(btnId) {
	
				var form = this;
				switch (btnId) {
	
					case "btnSearchPps":
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/cardmgmt/PinInfoMgmtList.json", form, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						
						break;
				}
	
			}
		}
		,GRID1 : {
				css : {
					height : "550px"
				}
				,title : "조회결과"
				,header : "관리번호, PIN구분, ON/OFF구분, 핀상태, 생성일자, 출고일자, 개통일자, 충전일자, 액면가격, 유통가격, 만료일자, 계약번호, 카드구분"
				,columnIds : "mngCode,pinGubunNm,onoffGubunNm,statusNm,crDate,outDate,openDate,rcgDate,startPrice,outPrice,expireDate,contractNum,freeFlagNm"
				,widths   : "120,90,80,60,70,70,70,70,90,90,70,120,100" 
				,colAlign : "center,center,center,center,center,center,center,right,right,right,center,center,center"
				,colTypes : "link,ro,ro,ro,ro,ro,ro,ro,ron,ron,ro,link,ro"
				,colSorting : "str,str,str,str,str,str,str,str,int,int,int,str,str,str"
				,paging : true
				,pageSize :15
				,pagingStyle :2
				,buttons : {
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
												
							var url = "/pps/hdofc/cardmgmt/PinInfoMgmtListExcel.json";
							 var searchData =  PAGE.FORM1.getFormData(true);
	
							  console.log("searchData",searchData);
							
						  	  mvno.cmn.download(url+"?menuId=PPS1400001",true,{postData:searchData});
						
							break;
		
					}
				}
		}
		,FORM20 : {
			title : "PIN상세보기 - 핀번호:"
			,items: _FORMITEMS_['FORM20']
			,buttons : {
				center : {
					btnSmsWrite : { label : "닫기" }
					
				}
			}
			,onButtonClick : function(btnId) {
	
				var form = this;
				switch (btnId) {
	
					case "btnSmsWrite":
	
						mvno.ui.closeWindowById(this);
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

			var selType ="popPin";
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType, searchAgentStr : null  } , PAGE.FORM1, "searchAgentNm");
			
			var tblName ="CREATE";
			
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsMngCodeHeaderList.json",{tblNm :tblName }, PAGE.FORM1, "searchManageCodeHeader");

			var selType2 ="dealer";
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType2, searchAgentStr : null  } , PAGE.FORM1, "searchDealerNm");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0062"  } , PAGE.FORM1, "searchFreeFlag");
			
			//var mngOption =  PAGE.FORM1.getOptions("searchManageCodeHeader");
			//var mngCdheader = mngOption[1].value;
			//PAGE.FORM1.setItemValue("searchManageCodeHeader",mngCdheader);

			setCurrentDate(PAGE.FORM1, "startDtCreate");
			setCurrentDate(PAGE.FORM1, "endDtCreate");
			

		}

	};
		
		
</script>