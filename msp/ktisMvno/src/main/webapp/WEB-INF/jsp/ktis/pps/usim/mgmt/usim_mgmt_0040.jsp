<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : usim_mgmt_0040.jsp
   * @Description : 본사  유심반품관리
   * @Modification Information
   *
   *   수정일         수정자                   수정내용
   *  -------    --------    ---------------------------
   *  2016.03.16            최초 생성
   *
   * author 
   * since  2016.03.16 
   *
   * Copyright (C) 2016 by MOPAS  All right reserved.
   */
%>

<div id="GROUP1">
	<div id="FORM1" class="section-search"></div>
	<div id="GRID1" class="section-full"></div>  
	
	<div id="FORM24" class="section-search" ></div>	
	<div id="TABBAR1"></div>  
	
		<div id="TABBAR1_a1" style="margin-top: 20px">
			<div id="FORM301" class="section-search"></div> 
			<div id="GRID301"></div>   
		</div>
		
		<div id="TABBAR1_a2" class="section-search" style="margin-top: 20px">
			<div id="FORM302" class="section-search"></div> 
			<div id="GRID302"></div>
		</div>
	 	<div id="FORM2" class="section-search" ></div>	
</div>

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
	
	function openUsimManageTabbar()
	{
		
		mvno.ui.createTabbar("TABBAR1");
	}
	

	<%@ include file="../../hdofcCustDetail.formitems" %>

	
	
	
	//검색 영역 
	var DHX = {
	
		FORM1 : {
			items : [ 	
					 {type:"block", list:[
					                      
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "반품일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                   	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						
						 {type : "newcolumn", offset:139},
						 {type : "select", label :"유심번호", name : "searchUsimSnHeader", width:100}, // KimWoong
						 {type : "newcolumn"}, 
						 {type:"block", list:[
 									{type:"settings", position:"label-left", labelAlign:"left",labelHeight:15, blockOffset:0},
 									{type : "input", label :"", name : "searchUsimSnStart", width:70, labelWidth:0,maxLength:7, validate: 'ValidNumeric'},
									{type : "newcolumn", offset:5},	
 									{type: "label", label: "부터",labelWidth:30},
 									{type : "newcolumn"},
 									{type : "input", label :"", name : "searchUsimSnEnd", width:70, labelWidth:00,maxLength:7, validate: 'ValidNumeric'},
						 {type : "newcolumn", offset:5},
						 {type: "label", label: "까지",labelWidth:30}
			   			]} 
						  
					 ]},// 1row
					 
					 
					 {type:"block", list:[
					                      
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 
						 
						 {type : "select",label:"검색",name : "searchType",width:100, userdata: { lov: "PPS_0081"}},	
		                 {type : "newcolumn", offset:3},
		                 {type:"block", list:[
	 									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
	 									,{type: "label", label: ":"}
	 			                   	 ]},	 
	 			         {type : "newcolumn", offset:2},
	 			         {type : "input",name : "searchNm", width:200},
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
					 
					 
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search2"} 
				]
				,onValidateMore : function(data){

					 if(data.startDt > data.endDt){
						 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
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
					
					if(data.searchType=="BACK_SEQ")
					{
						if(data.searchNm.match(/^\d+$/ig) == null)
						{
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							this.pushError("searchNm","검색조건","숫자만 입력하세요.");
						}
					}
					
					if(data.searchUsimSnEnd!="" || data.searchUsimSnStart!=""){
						 if(data.searchUsimSnHeader==""){
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 	this.pushError("searchUsimSnHeader", "검색조건", "유심번호를 선택하세요.");
						 }
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
						
						PAGE.GRID1.list(ROOT + "/pps/usim/mgmt/UsimReturnMgmtList.json", form, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						
						break;
				}
	
			}
		}
	//그리드 목록 부분
		,GRID1 : {
				css : {
					height : "200px"
				}
				,title : "조회결과"
				,header : " 반품번호, 유심모델, 유심S/N(S), 유심SN(E), 반품수량, 반품일자, 반품처리자, 반품대리점, 메모, 반품취소일자, 취소처리자, 취소여부"
				,columnIds : "backSeq,usimModel,startSn,endSn,usimCnt,backDate,backAdminNm,backAgentNm,remark,cancelDate,cancelAdminNm,cancelFlag"
				,widths   : "100,90,80,70,70,130,120,120,200,90,120,70" 
				,colAlign : "center,center,center,center,center,center,center,center,left,center,center,center"
				,colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
				,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str"
				,paging : true
				,pageSize :15
				,pagingStyle :2
				,buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" } 
						
					}
					,right : {
					btnReg : { label : "유심반품" },
					btnMod : { label : "반품취소" }
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
												
							var url = "/pps/usim/mgmt/UsimReturnMgmtListExcel.json";
							var searchData =  PAGE.FORM1.getFormData(true);
	
							  console.log("searchData",searchData);
							
						  	  mvno.cmn.download(url+"?menuId=PPS1700004",true,{postData:searchData});
						
							break;
							
							//유심반품
						   case "btnReg":
							openUsimManageTabbar();
                           break;
                           
                          //유심반품취소
						   case "btnMod":
							   
							var selectedData = PAGE.GRID1.getSelectedRowData();
							if(!selectedData){
								mvno.ui.alert("취소할 유심반품을 선택하세요.");
								return;
							}   
                    	   
							mvno.ui.createForm("FORM2");
							PAGE.FORM2.setFormData();
							PAGE.FORM2.clearChanged();
							PAGE.FORM2.setItemValue("backSeq",selectedData.backSeq);
 							PAGE.FORM2.setItemValue("agentId",selectedData.backAgent);
							
							
							
							mvno.ui.popupWindowById("FORM2", "유심반품취소", 750, 200, {
				                onClose: function(win) {
				                	if (! PAGE.FORM2.isChanged()) return true;
				                	mvno.ui.confirm("CCMN0005", function(){
				                		win.closeForce();
				                	});
				                }
			                });	

                           break;
		
					}
				}
		}
		,FORM24 : {
			title : "유심반품상세보기 - 유심반품번호:"
			,items: _FORMITEMS_['FORM24']
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
		,FORM301 : {
			css : {
					height : "30px"
			}
			,title : "검색조건 및 출고 정보"
			,items: _FORMITEMS_['FORM301']
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
								      ,PAGE.FORM301, "searchAgentNm");
	
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
						}	
					}
			}
		}
		
		,GRID301 : {
				css : {
					height : "150px"
				}
				,title : "출고내역"
				,header : " 출고번호, 유심모델, 유심S/N(S), 유심SN(E), 출고수량, 출고일자, 출고자, 출고대리점, 메모, 출고취소일자, 취소처리자, 취소여부"
				,columnIds : "outSeq,usimModel,startSn,endSn,usimCnt,outDate,outAdminNm,outAgentNm,remark,cancelDate,cancelAdminNm,cancelFlag"
				,widths   : "100,90,80,70,70,70,70,70,200,90,80,70" 
				,colAlign : "center,center,center,center,center,center,center,center,left,center,center,center"
				,colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
				,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str"
				,paging : true
				,pageSize : 500
				,pagingStyle :1
				,buttons : {
					hright : {
						btnReg : { label : "출고 내역 반품" }
						,btnSearch : { label : "출고 내역 조회" }
						
					}
				}
				,onButtonClick : function(btnId) {
	
					switch (btnId) {
					
						case "btnSearch":
						
							var backListFlag = "Y";
							var searchCancelFlag = "N";
							
							//var btnCount2 = parseInt(PAGE.FORM302.getItemValue("btnCount1", "")) + 1;
							//PAGE.FORM302.setItemValue("btnCount1", btnCount2)
							//if (PAGE.FORM302.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							
							//PAGE.GRID302.list(ROOT + "/pps/usim/mgmt/getPpsUsimInfoForProcData.json", data, {
							//	callback : function () {
									//PAGE.FORM302.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									//PAGE.FORM302.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							//	}
							//});
							
							PAGE.GRID301.list(ROOT + "/pps/usim/mgmt/UsimOutMgmtList.json", 
							{
								backListFlag : backListFlag,
								searchCancelFlag : searchCancelFlag
							}, {
							callback : function () {
							}
						});
						
						break;
	
						case "btnReg":
							var sdata = PAGE.GRID301.getSelectedRowData();
							
							if(sdata != null)
							{
								var memoStr = PAGE.FORM301.getFormData(true).memo;
								var agentIdStr = PAGE.FORM301.getItemValue("searchAgentNm");
								mvno.ui.confirm("선택 항목을 반품 처리 하시겠습니까?", function(){
									mvno.cmn.ajax(
										ROOT + "/pps/usim/mgmt/ppsUsimBackProc.json",
										{
											opCode : "BK_SN",
											usimModel : sdata.usimModel,
											remark : memoStr,
											startSn : sdata.startSn,
											endSn : sdata.endSn,
											usimCnt:sdata.usimCnt,
											agentId : agentIdStr
											
										},
										function(result) {
											var result = result.result;
											var data = result.data;
											var code = result.code;	
											
											if(code=="OK")
											{
												if(result != null && result.oRetCd == '0000')
												{
													mvno.ui.alert('유심 반품 처리되엇습니다.');
													PAGE.FORM301.setItemValue("memo","");
													PAGE.FORM301.setItemValue("searchAgentNm","");
													PAGE.FORM301.setItemValue("searchAgentStr","");
													PAGE.GRID1.refresh();
													PAGE.GRID301.refresh();
												}
												else
												{
													mvno.ui.alert(result.oRetCd + " : "+result.oRetMsg);
												}
												
											}else{
												mvno.ui.notify(retMsg);
											}
										},
										null
										);// ajax 끝..
								
									});
								
							}
							else
							{
								mvno.ui.alert("반품할 출고 내역을 선택 하십시오.");
							}
							
							
							
							break;
							
                           
					}
				}
		}
		,FORM302 : {
			css : {
					height : "55px"
			}
			,title : "유심 반품"
			,items: _FORMITEMS_['FORM302']
			,buttons : {
					hright : {
						//btnReg : { label : "체크 유심 반품" } 
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
								      ,PAGE.FORM302, "searchAgentNm");
	
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
						}	
					}
			}
			,onButtonClick : function(btnId) {
	
				var form = this;
				switch (btnId) {
	
					case "checkBKBtn":
						
						var usimModelStr = PAGE.FORM302.getFormData(true).searchUsimSnHeader;
						var startSnStr = PAGE.FORM302.getFormData(true).searchUsimSnStart;
						var endSnStr = PAGE.FORM302.getFormData(true).searchUsimSnEnd;
						
						PAGE.FORM302.setItemValue("usimCnt","");
						
						var memoStr = PAGE.FORM302.getFormData(true).memo;
						var agentIdStr = PAGE.FORM302.getItemValue("searchAgentNm");
						
						if(usimModelStr == null || usimModelStr == '')
						{
							mvno.ui.alert('유심 모델을 선택하싮시오.');
							return;
						}
						
						if((startSnStr == null || startSnStr == '') && (endSnStr == null || endSnStr == ''))
						{
							mvno.ui.alert('유심 SN을 입력 하세요.');
							return;
						}
						
						mvno.ui.confirm("반품 가능한 유심을 확인 하시겠습니까?", function(){
								
							mvno.cmn.ajax(
									ROOT + "/pps/usim/mgmt/ppsUsimBackProc.json",
									{
										opCode : "BK_CHK",
										usimModel : usimModelStr,
										startSn : startSnStr,
										endSn : endSnStr,
										agentId : agentIdStr
										
									},
									function(result) {
										var result = result.result;
										var data = result.data;
										var cntStr = result.oCnt;
										var code = result.code;	
										
										if(code=="OK")
										{
											if(result != null && result.oRetCd == '0000')
											{
												var data = PAGE.FORM302.getFormData(true);
							
												var btnCount2 = parseInt(PAGE.FORM302.getItemValue("btnCount1", "")) + 1;
												PAGE.FORM302.setItemValue("btnCount1", btnCount2)
												if (PAGE.FORM302.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
												
												PAGE.GRID302.list(ROOT + "/pps/usim/mgmt/getPpsUsimInfoForProcData.json", data, {
													callback : function () {
														PAGE.FORM302.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
														PAGE.FORM302.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
													}
												});
												
												mvno.ui.alert("반품가능개수 : "+cntStr+" 개 입니다.");
												PAGE.FORM302.setItemLabel("checkResultCnt", "반품가능개수 : "+cntStr+" 개");
												PAGE.FORM302.setItemValue("usimCnt",cntStr);
											}
											else
											{
												mvno.ui.alert(result.oRetCd + " : "+result.oRetMsg);
												PAGE.FORM302.setItemLabel("checkResultCnt", "반품가능개수 : 0 개");
											}
											
										}else{
											mvno.ui.notify(retMsg);
										}
									},
									null
									); // ajax 끝..
								});
						break;
					case "btnReg":
						
						var memoStr = PAGE.FORM302.getFormData(true).memo;
						var startSnStr = PAGE.FORM302.getFormData(true).searchUsimSnStart;
						var endSnStr = PAGE.FORM302.getFormData(true).searchUsimSnEnd;
						var usimModelStr = PAGE.FORM302.getFormData(true).searchUsimSnHeader;
						var cntStr = PAGE.FORM302.getFormData(true).usimCnt;
						var agentIdStr = PAGE.FORM302.getItemValue("searchAgentNm");
						
						
						mvno.cmn.ajax(
									ROOT + "/pps/usim/mgmt/ppsUsimBackProc.json",
									{
										opCode : "BK_SN",
										remark : memoStr,
										usimModel : usimModelStr,
										startSn : startSnStr,
										endSn : endSnStr,
										usimCnt:cntStr,
										agentId :agentIdStr
										
									},
									function(result) {
										var result = result.result;
										var data = result.data;
										var code = result.code;	
										
										if(code=="OK")
										{
											if(result != null && result.oRetCd == '0000')
											{
												mvno.ui.alert('유심 반품 처리되엇습니다.');	
												
												PAGE.FORM302.setItemValue("usimCnt","");
												PAGE.FORM302.setItemValue("memo","");
												PAGE.FORM302.setItemValue("searchAgentNm","");
												PAGE.FORM302.setItemValue("searchAgentStr","");
												PAGE.FORM302.setItemValue("searchUsimSnHeader","");
												PAGE.FORM302.setItemValue("searchUsimSnStart","");
												PAGE.FORM302.setItemValue("searchUsimSnEnd","");
											}
											else
											{
												mvno.ui.alert(result.oRetCd + " : "+result.oRetMsg);
											}
											
										}else{
											mvno.ui.notify(retMsg);
										}
									},
									null
									);
								
						break;
				}
	
			}
		}
		,GRID302 : {
				css : {
					height : "150px"
				}
				,title : "출고내역"
				,header : "일련번호, 유심모델, 유심S/N, 유심S/N(full),상태, 개통일자, 계약번호, 입고번호, 입고일자, 입고자, 출고번호, 출고일자, 출고자, 대리점, 반품일, 반품처리자"
				,columnIds : "sn,usimModel,usimSn,usimFullSn,statusNm,openDate,contractNum,crSeq,crDate,crAdminNm,outSeq,outDate,outAdminNm,outAgentNm,backDate,backAdminNm"
				,widths   : "120,90,80,100,70,70,70,70,70,90,90,70,120,70,70,70" 
				,colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center"
				,colTypes : "link,ro,ro,ro,ro,ro,link,ro,ro,ro,ro,ro,ro,ro,ro,ro"
				,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
				,paging : true
				,multiCheckbox : true
				,pageSize : 500
				,pagingStyle :1
				,buttons : {
					hright : {
						btnReg : { label : "선택 유심 반품" }//,
						//btnSearch : { label : "출고 유심 조회" }
						
					}
				}
				,onButtonClick : function(btnId) {
	
					switch (btnId) {
					
						case "btnSearch":
						
							var data = PAGE.FORM302.getFormData(true);
							
							var btnCount2 = parseInt(PAGE.FORM302.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM302.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM302.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							
							PAGE.GRID302.list(ROOT + "/pps/usim/mgmt/getPpsUsimInfoForProcData.json", data, {
								callback : function () {
									PAGE.FORM302.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM302.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								}
							});
						
						break;
	
						case "btnReg":
							
							var self = PAGE.GRID302;
							
							var rowIds = self.getCheckedRows(0);

							if (! rowIds) {
							    mvno.ui.alert("ECMN0003");
							    return;
							}
							var checkedData = self.classifyRowsFromIds(rowIds, "usimFullSn");
							//alert(checkedData.OLD.length);
							
							var fullSnStr = '';
							for(var i = 0 ;  i < checkedData.OLD.length ; i++){
								if(i > 0)
									fullSnStr += '|';
								
								fullSnStr += checkedData.OLD[i].usimFullSn;
							}
							
							
							var memoStr = PAGE.FORM302.getFormData(true).memo;
							var agentIdStr = PAGE.FORM302.getItemValue("searchAgentNm");
							
							if(agentIdStr == null || agentIdStr == '')
							{
								mvno.ui.alert('출고 대리점을 선택하십시오.');
								return;
							}
							mvno.ui.confirm("유심 반품 ( "+checkedData.OLD.length+" 개) 처리 하시겠습니까?", function(){
								
								mvno.cmn.ajax(
									ROOT + "/pps/usim/mgmt/ppsUsimBackProc.json",
									{
										opCode : "BK",
										remark : memoStr,
										usimCnt:checkedData.OLD.length,
										iccId : fullSnStr,
										agentId : agentIdStr
										
									},
									function(result) {
										var result = result.result;
										var data = result.data;
										//console.log("D",data);
										var code = result.code;	
										
										if(code=="OK")
										{
											if(result != null && result.oRetCd == '0000')
											{
												mvno.ui.alert('유심 반품 처리되엇습니다.');
												PAGE.FORM302.setItemValue("usimCnt","");
												PAGE.FORM302.setItemValue("memo","");
												PAGE.FORM302.setItemValue("searchAgentNm","");
												PAGE.FORM302.setItemValue("searchAgentStr","");
												PAGE.FORM302.setItemValue("searchUsimSnHeader","");
												PAGE.FORM302.setItemValue("searchUsimSnStart","");
												PAGE.FORM302.setItemValue("searchUsimSnEnd","");
												PAGE.GRID1.refresh();
											}
											else
											{
												mvno.ui.alert(result.oRetCd + " : "+result.oRetMsg);
											}
											
										}else{
											mvno.ui.notify(retMsg);
										}
									},
									null
									);// ajax 끝..
									
								});
							
						
							break;
							
                           
					}
				}
		}
		,TABBAR1: {

		css : { height : "360px" }
		,tabs: [
		       { id: "a1", text: "자동 반품" },
			    { id: "a2", text: "수동 반품" }
		]	
	    ,onSelect: function (id, lastId, isFirst) {

			switch (id) {

				case "a1":
					
					mvno.ui.createForm("FORM301");
					mvno.ui.createGrid("GRID301");
					
					var backListFlag = "Y";
					var searchCancelFlag = "N";
					
					PAGE.GRID301.list(ROOT + "/pps/usim/mgmt/UsimOutMgmtList.json", 
					{
						backListFlag : backListFlag,
						searchCancelFlag : searchCancelFlag
					}, {
						callback : function () {
						}
					});
					var selType ="popPin";
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType, searchAgentStr : null  } , PAGE.FORM301, "searchAgentNm");
		    		
					break;

				case "a2":
					
					mvno.ui.createForm("FORM302");
					mvno.ui.createGrid("GRID302");
					
					var selType ="popPin";
	 			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType, searchAgentStr : null  } , PAGE.FORM302, "searchAgentNm");
	
	 			mvno.cmn.ajax4lov(ROOT + "/pps/usim/mgmt/ppsUsimModelDataList.json",{searchLovCd :""  } , PAGE.FORM302, "searchUsimSnHeader");
	 			
		    		
		    		break;

			    
				
			}

	    }

	}// FORM end..
	,
	FORM2 : {

			title : "유심 반품취소",
			buttons : {
				center : {
					btnUsimModelReg : { label : "반품취소" }, 
					btnUsimModelCancel : { label : "닫기" }
				}
			},
			items : [
				{type: 'hidden', name: 'backSeq', value: ''},
				{type: 'hidden', name: 'agentId', value: ''},
				{type:"settings", position:"label-left", labelWidth:"120"},
				{type:"input", label:"메모", name: "remark", width:500}
			],
			 onValidateMore : function (data){
				 
				/* if(data.crCount==null || data.crCount=="")
				{
					this.pushError("crCount","생성갯수","생성갯수는 필수입력입니다.");	
					 
				} */

			 }
			,onButtonClick : function(btnId, selectedData) {
				
				switch (btnId) {
					case "btnUsimModelReg":
						
						//back:BK_CAN , out:OT_CAN , create:CR_CAN
							var searchData =  PAGE.FORM2.getFormData(true);
							var backSeq = PAGE.FORM2.getItemValue("backSeq", "");
							var remark = PAGE.FORM2.getItemValue("remark", "");
							var agentId = PAGE.FORM2.getItemValue("agentId", "");
							var opCode = "BK_CAN";
							
							mvno.ui.confirm("반품을 취소하시겠습니까?", function(){
								mvno.cmn.ajax(
									ROOT + "/pps/usim/mgmt/ppsUsimBackProc.json",
									{
										opCode : opCode,
										seq : backSeq,
										remark:remark,
										agentId : agentId
										
									},
									function(result) {
		
										var data1 = result.result;
										var retCode = data1.oRetCd;
										var retMsg = data1.oRetMsg;
										if(retCode=="0000")
										{
											mvno.ui.closeWindowById("FORM2", true);
											mvno.ui.notify(data1.oRetMsg);
											PAGE.GRID1.refresh();
										}else{
											mvno.ui.notify(data1.oRetMsg);
											mvno.ui.closeWindowById("FORM2", true);
										}
									},
									null
								);
							});
						break;

					case "btnUsimModelCancel":							
						mvno.ui.closeWindowById(this);
						break;
				}
			}
		}

};
	
//상단 네비게이션
	var PAGE = {

		title : "${breadCrumb.title}",
		breadcrumb : "${breadCrumb.breadCrumb}", 
		buttonAuth: ${buttonAuth.jsonString},
		onOpen : function() {

			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			var selType ="popPin";
 			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType, searchAgentStr : null  } , PAGE.FORM1, "searchAgentNm");

 			mvno.cmn.ajax4lov(ROOT + "/pps/usim/mgmt/ppsUsimModelDataList.json",{searchLovCd :""  } , PAGE.FORM1, "searchUsimSnHeader");
 			
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0081"  } , PAGE.FORM1, "searchType");

			setCurrentDate(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "endDt");
			

		}

	};
		
		
</script>