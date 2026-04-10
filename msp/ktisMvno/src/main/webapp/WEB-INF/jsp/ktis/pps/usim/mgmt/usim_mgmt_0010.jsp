<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : usim_mgmt_0010.jsp
   * @Description : 본사  유심관리
   * @Modification Information
   *
   *   수정일         수정자                   수정내용
   *  -------    --------    ---------------------------
   *  2016.03.16            최초 생성
   *
   * author 실행환경 개발팀
   * since  2016.03.16 
   *
   * Copyright (C) 2016 by MOPAS  All right reserved.
   */
%>


<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>  
<div id="FORM24" class="section-search" ></div>	

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

	function goUsimInfoData(sn,usimModel)
	{
		
		mvno.ui.createForm("FORM24");

		mvno.cmn.ajax(
				ROOT + "/pps/usim/mgmt/PpsUsimInfo.json",
				{
					sn : sn,
					usimModel : usimModel
				},
				function(result) {

					console.log(result.result);
					
					var data1 = result.result.data;	
					var title= "유심상세보기 - 유심번호:"+sn;
					mvno.ui.updateSection("FORM24", "title", title);
					console.log(data1);
					
					PAGE.FORM24.setItemValue("telcoCd",data1.telcoCd);
					PAGE.FORM24.setItemValue("sn",data1.sn);

					PAGE.FORM24.setItemValue("usimModel",data1.usimModel);	
					PAGE.FORM24.setItemValue("usimSn",data1.usimSn);	
					PAGE.FORM24.setItemValue("status",data1.status);	
					PAGE.FORM24.setItemValue("statusNm",data1.statusNm);
					PAGE.FORM24.setItemValue("openDate",data1.openDate);	
					PAGE.FORM24.setItemValue("contractNum",data1.contractNum);	
					PAGE.FORM24.setItemValue("crDate",data1.crDate);	
					
					PAGE.FORM24.setItemValue("crSeq",data1.crSeq);	
					PAGE.FORM24.setItemValue("crAdmin",data1.crAdmin);	
					PAGE.FORM24.setItemValue("remark",data1.remark);	
					PAGE.FORM24.setItemValue("outSeq",data1.outSeq);	
					PAGE.FORM24.setItemValue("outAdmin",data1.outAdmin);	
					PAGE.FORM24.setItemValue("outAgent",data1.outAgent);
					PAGE.FORM24.setItemValue("outDate",data1.outDate);	
					PAGE.FORM24.setItemValue("backDate",data1.backDate);	
					PAGE.FORM24.setItemValue("backSeq",data1.backSeq);	
					PAGE.FORM24.setItemValue("backAdmin",data1.backAdmin);	
					
					PAGE.FORM24.setItemValue("addremark","");	
				},
				null
				);

				mvno.ui.popupWindowById("FORM24", "유심상세정보", 630, 520, {
		            onClose: function(win) {
		            	if (! PAGE.FORM24.isChanged()) return true;
		            	mvno.ui.confirm("CCMN0005", function(){
		            		win.closeForce();
		            	});
		            }
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

	<%@ include file="../../hdofcCustDetail.formitems" %>

	var DHX = {
	
		FORM1 : {
			items : [ 	
					 {type:"block", list:[
					                      
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDtCreate",label : "입고일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                   	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDtCreate", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						
						 {type : "newcolumn", offset:40},
						 {type : "select", label :"유심번호", name : "searchUsimSnHeader", width:100, userdata: { lov: ""}}, // KimWoong
						 {type : "newcolumn", offset:10}, 
						 {type:"block", list:[
 									{type:"settings", position:"label-left", labelAlign:"left",labelHeight:15, blockOffset:0},
 									{type : "input", label :"", name : "searchUsimSnStart", width:70, labelWidth:0,maxLength:7, validate: 'ValidNumeric'},
									{type : "newcolumn", offset:5},	
 									{type: "label", label: "부터",labelWidth:30},
 									{type : "newcolumn"},
 									{type : "input", label :"", name : "searchUsimSnEnd", width:70, labelWidth:0,maxLength:7, validate: 'ValidNumeric'},
						 {type : "newcolumn", offset:5},
						 {type: "label", label: "까지",labelWidth:30}
			   			]} 
						  
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
						 
						 {type : "newcolumn", offset:40}, 
						 							{type : "input",label:"출고대리점",name : "searchAgentStr",width:100, labelWidth:60},	// KimWoong
		                   	{type : "newcolumn", offset:3},
		                   	{type:"block", list:[
	 									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
	 									,{type: "label", label: ":"}
	 			                   	 ]},	 
	 			                    {type : "newcolumn", offset:2},
	 			                   	{type : "select",name : "searchAgentNm", width:200} 		
						  
					 ]},// 2row
					 
					 {type:"block", list:[
					                      
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDtBack",label : "반품일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},
	                   	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDtBack", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						 {type : "newcolumn",offset:40}, 
						 {type : "select",label:"검색",name : "searchType",width:100, userdata: { lov: "PPS_0076"}},	
		                 {type : "newcolumn", offset:3},
		                 {type:"block", list:[
	 									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
	 									,{type: "label", label: ":"}
	 			                   	 ]},	 
	 			         {type : "newcolumn", offset:2},
	 			         {type : "input",name : "searchNm", width:200}
						 
						  
					 ]},// 3row
					 
					  {type:"block", list:[
					              
					                      
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 
						 {type : "select",label :"상태",name : "searchStatus",width:100,userdata: { lov: "PPS_0072"}}

						  
					 ]},// 4row 
					 
					 {type : "newcolumn", offset:40},
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

					 if(data.startDtBack > data.endDtBack){
						 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 this.pushError("startDtBack", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
					}
					 
					var startVal = parseInt(data.searchUsimSnStart);
					var endVal = parseInt(data.searchUsimSnEnd);
					  
					if(endVal != "" && endVal != null){
						if(startVal > endVal){
							 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							 this.pushError("searchUsimSnEnd", "검색조건", "앞번호가 뒷번호보다 큽니다.");
						} 
					}
					
					if(data.searchUsimSnEnd!="" || data.searchUsimSnStart!=""){
						 if(data.searchUsimSnHeader==""){
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 	this.pushError("searchUsimSnHeader", "검색조건", "유심번호를 선택하세요.");
						 }
					}
					 
		 			if(data.searchType=="CR_SEQ")
					{
						if(data.searchNm.match(/^\d+$/ig) == null)
						{
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							this.pushError("searchNm","검색조건","숫자만 입력하세요.");
						}
					}
		 			
		 			if(data.searchType=="OUT_SEQ")
					{
						if(data.searchNm.match(/^\d+$/ig) == null)
						{
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							this.pushError("searchNm","검색조건","숫자만 입력하세요.");
						}
					}
		 			if(data.searchType=="BACK_SEQ")
					{
						if(data.searchNm.match(/^\d+$/ig) == null)
						{
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							this.pushError("searchNm","검색조건","숫자만 입력하세요.");
						}
					}
		 			if(data.searchType=="CONTRACT_NUM")
					{
						if(data.searchNm.match(/^\d+$/ig) == null)
						{
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							this.pushError("searchNm","검색조건","숫자만 입력하세요.");
						}
					}
					 
					 if(data.searchNm!=""){
						 if(data.searchType==""){
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 	this.pushError("searchType", "검색조건", "검색조건을 선택하세요.");
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
						
						PAGE.GRID1.list(ROOT + "/pps/usim/mgmt/UsimInfoMgmtList.json", form, {
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
				,header : "유심번호, 유심모델, 유심S/N, 상태, 개통일자, 계약번호, 입고번호, 입고일자, 입고자, 출고번호, 출고일자, 출고자, 대리점, 반품일자, 반품처리자"
				,columnIds : "sn,usimModel,usimSn,statusNm,openDate,contractNum,crSeq,crDate,crAdminNm,outSeq,outDate,outAdminNm,outAgentNm,backDate,backAdminNm"
				,widths   : "120,90,80,70,80,70,70,80,120,90,80,120,120,80,120" 
				,colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center"
				,colTypes : "link,ro,ro,ro,ro,link,ro,ro,ro,ro,ro,ro,ro,ro,ro"
				,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
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
												
							var url = "/pps/usim/mgmt/UsimInfoMgmtListExcel.json";
							 var searchData =  PAGE.FORM1.getFormData(true);
	
							  console.log("searchData",searchData);
							
						  	  mvno.cmn.download(url+"?menuId=PPS1700001",true,{postData:searchData});
						
							break;
		
					}
				}
		}
		,FORM24 : {
			title : "유심상세보기 - 유심번호:"
			,items: _FORMITEMS_['FORM24']
			,buttons : {
				center : {
					btnUsimWrite : { label : "저장" },
					btnUsimClose : { label : "닫기" }
					
				}
			}
			,onButtonClick : function(btnId) {
	
				var form = this;
				switch (btnId) {
	
					case "btnUsimClose":
	
						mvno.ui.closeWindowById(this);
					break;
					
					case "btnUsimWrite":
							 var remark = PAGE.FORM24.getItemValue("addremark", "");
							 var telcoCd = PAGE.FORM24.getItemValue("telcoCd", "");
							 var usimModel = PAGE.FORM24.getItemValue("usimModel", "");
							 var sn = PAGE.FORM24.getItemValue("sn", "");
							 var iccId =    telcoCd+usimModel+sn;
						
							 var comment = '메모를 저장하시겠습니까?';
							 var opCode    = 'CR_REMARK';

								 
							 mvno.ui.confirm(comment, function(){
								var url = ROOT + "/pps/usim/mgmt/ppsUsimCreateProc.json";
									mvno.cmn.ajax(
											url, 
											{
												opCode : opCode,
												remark : remark,
												iccId : iccId
												
											}
											, function(results) {
												 var result = results.result;
				                                  var retCode = result.oRetCd;
				                                  var msg = result.oRetMsg;
				                                  mvno.ui.notify(msg);
				                                  if(retCode == "0000"){
				                                	     mvno.ui.closeWindowById("FORM24", true);
				                                  }
				                               
				                              });	
									});
						
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
 			
 			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0072"  } , PAGE.FORM1, "searchStatus");
 			
 			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0076"  } , PAGE.FORM1, "searchType");
 			
 			mvno.cmn.ajax4lov(ROOT + "/pps/usim/mgmt/ppsUsimModelDataList.json",{searchLovCd :""  } , PAGE.FORM1, "searchUsimSnHeader");
			
 			//달력에 오늘날짜Set
 			setCurrentDate(PAGE.FORM1, "startDtCreate");
			setCurrentDate(PAGE.FORM1, "endDtCreate");

		}

	};
		
		
</script>