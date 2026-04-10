<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : usim_mgmt_0020.jsp
   * @Description : 본사  유심입고관리
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
		
	<div id="FORM101" class="section-search"></div> 
	
	<div id="FORM28" class="section-search" ></div>		
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
		
		mvno.ui.createForm("FORM101");
		
		PAGE.FORM101.setItemValue("excelCnt", "");
		PAGE.FORM101.setItemValue("file_upload1", "");
		PAGE.FORM101.setItemValue("memo", "");
		PAGE.FORM101.setItemValue("iccId", "");

   		var uploader = PAGE.FORM101.getUploader("file_upload1");

	 	uploader.revive();
	 	
	 	PAGE.FORM101.attachEvent("onBeforeFileAdd",function(realName, size, file){
	 		if(realName.substr(realName.length-3, realName.length) != 'xls' && realName.substr(realName.length-3, realName.length) != 'XLS'){
	 			mvno.ui.alert("유심입고는 xls파일만 가능합니다.");
	 			return false;
	 		}
             	    return true;
       });
	 	
	 	PAGE.FORM101.attachEvent("onFileAdd",function(file){
	 		PAGE.FORM101.setItemValue("fileName", file);
             	   
       });
   		
		PAGE.FORM101.attachEvent("onUploadComplete",function(count){
			var fileName = PAGE.FORM101.getItemValue("fileName");
			var data = {"fileName":fileName};
			
			mvno.cmn.ajax(
								ROOT + "/pps/usim/mgmt/readUsimExcelFile.do",
								data,
								function(result) {
	
									var result = result.result;
									var retCode = result.code;
									var resultIccIds = result.resultIccIds;
									
									PAGE.FORM101.setItemValue("iccId", resultIccIds);
									
									if(resultIccIds != null)
									{
										PAGE.FORM101.setItemValue("iccId", resultIccIds);
										
									}else{
										PAGE.FORM101.setItemValue("iccId", "");
									}
								},
								null
							);
	   });
		
		PAGE.FORM101.attachEvent("onBeforeFileRemove",function(realName, serverName){
			PAGE.FORM101.setItemValue("excelCnt", "");
			PAGE.FORM101.setItemValue("memo", "");
             	    return true;
       });
		var selType ="popPin";
		//mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",{selectType : selType, searchAgentStr : null  } , PAGE.FORM101, "searchAgentNm");
	
		mvno.cmn.ajax4lov(ROOT + "/pps/usim/mgmt/ppsUsimModelDataList.json",{searchLovCd :""  } , PAGE.FORM101, "searchUsimSnHeader");
	}
	
	<%@ include file="../../hdofcCustDetail.formitems" %>
	
	//검색 영역 
	var DHX = {
	
		FORM1 : {
			items : [ 	
					 {type:"block", list:[
					                      
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "입고일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                   	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						
						 {type : "newcolumn", offset:139},
						 {type : "select", label :"유심번호", name : "searchUsimSnHeader", width:100}, // KimWoong
						 {type : "newcolumn",offset:10}, 
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
						 
						 
						 {type : "select",label:"검색",name : "searchType",width:100, userdata: { lov: "PPS_0079"}},	
		                 {type : "newcolumn", offset:3},
		                 {type:"block", list:[
	 									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
	 									,{type: "label", label: ":"}
	 			                   	 ]},	 
	 			         {type : "newcolumn", offset:2},
	 			         {type : "input",name : "searchNm", width:200}
						  
					 ]},// 2row
					 
					 
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search2"} 
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
					

				}
			,onButtonClick : function(btnId) {
	
				var form = this;
				switch (btnId) {
	
					case "btnSearch":
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/usim/mgmt/UsimCreateMgmtList.json", form, {
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
				,header : "입고번호, 유심모델, 유심S/N(S), 유심SN(E), 입고수량, 입고일자, 입고자, 메모, 취소일자, 취소자,취소여부"
				,columnIds : "crSeq,usimModel,startSn,endSn,usimCnt,crDate,crAdminNm,remark,cancelDate,cancelAdminNm,cancelFlag"
				,widths   : "100,90,80,70,70,130,120,200,90,120,70" 
				,colAlign : "center,center,center,center,center,center,center,left,center,center,center"
				,colTypes : "ro,ro,ro,ro,ron,ro,ro,ro,ro,ro,ro"
				,colSorting : "str,str,str,str,str,str,str,str,str,str,str"
				,paging : true
				,pageSize :15
				,pagingStyle :2
				,buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" } 
						
					}
					,right : {
					btnReg : { label : "유심입고" },
					btnMod : { label : "입고취소" }
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
												
							var url = "/pps/usim/mgmt/UsimCreateMgmtListExcel.json";
							var searchData =  PAGE.FORM1.getFormData(true);
	
							  console.log("searchData",searchData);
							
						  	  mvno.cmn.download(url+"?menuId=PPS1700002",true,{postData:searchData});
						
							break;
							
							//유심입고
						   case "btnReg":
								openUsimManageTabbar(); // 유심 입고 탭바 open
                           break;
                           
                           
                           //유심취소
						   case "btnMod":
							   
							   
							var selectedData = PAGE.GRID1.getSelectedRowData();
							if(!selectedData){
								mvno.ui.alert("취소할  유심입고를 선택하세요.");
								return;
							}   
                    	   
							mvno.ui.createForm("FORM2");
							PAGE.FORM2.setFormData();
							PAGE.FORM2.clearChanged();
 							PAGE.FORM2.setItemValue("crSeq",selectedData.crSeq);
							
							
							
							mvno.ui.popupWindowById("FORM2", "유심입고취소", 750, 200, {
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
		,FORM28 : {
			title : "유심입고상세보기 - 유심입고번호:"
			,items: _FORMITEMS_['FORM28']
			,buttons : {
				center : {
					btnSmsWrite : { label : "닫기" },
					btnCancel : { label : "닫기" }
					
				}
			}
			,onButtonClick : function(btnId) {
	
				var form = this;
				switch (btnId) {
	
					case "btnSmsWrite":
	
					mvno.ui.closeWindowById(this);
					break;
					
					case "btnCancel":
	
					mvno.ui.closeWindowById(this);
					break;
					
					
					
				}
			}
		} // Form End..
		,FORM101 : {
			css : {
					height : "100px"
			}
			,title : "유심 입고"
			,items: _FORMITEMS_['FORM101']
			,onButtonClick : function(btnId) {
	
				var form = this;
				switch (btnId) {
						
					case "excelSample":
						var url = "/pps/usim/mgmt/usimCreateExcelSample.json";
				 		mvno.cmn.download(url, false, null);
						break;
					case "excelCreate":
						
						var memoStr = PAGE.FORM101.getItemValue("memo");
						var iccIdStr = PAGE.FORM101.getItemValue("iccId");
						//var agentIdStr = PAGE.FORM101.getItemValue("searchAgentNm");
						var cntStr = PAGE.FORM101.getItemValue("searchUsimCnt");
						if(iccIdStr == null || iccIdStr == '')
						{
							mvno.ui.alert('엑셀파일 오류');
							return;
						}
						if(cntStr == null || cntStr == '')
						{
							mvno.ui.alert('입고 수량을 입력하싮시오.');
							return;
						}
						
						mvno.ui.confirm("엑셀 유심 입고( "+cntStr+" 개) 처리 하시겠습니까?", function(){
							
								mvno.cmn.ajax(
									ROOT + "/pps/usim/mgmt/usimExcelCreateProc.json",
									{
										opCode:"CR",
										iccId:iccIdStr,
										usimCnt:cntStr,
										remark:memoStr
										//agentId : agentIdStr
									},
									function(result) {
		
										var result = result.result;
										var retCode = result.oRetCd;
										var resultIccIds = result.resultIccIds;
										PAGE.FORM101.setItemValue("memo","");
										PAGE.FORM101.setItemValue("searchUsimCnt","");
										PAGE.FORM101.setItemValue("iccId","");
										PAGE.FORM101.setItemValue("file_upload1","");
										
										if(retCode != null && retCode =="0000")
										{
											mvno.ui.alert("유심 excel 등록 성공");
											PAGE.GRID1.refresh();
											
										}else{
											mvno.ui.alert(retCode +" : "+result.oRetMsg);
										}
									},
									null
								);// ajax 끝
								
							});
						
						
						break;
					case "checkAllCRBtn":
						var memoStr = PAGE.FORM101.getFormData(true).memo;
						var startSnStr = PAGE.FORM101.getFormData(true).searchUsimSnStart;
						var endSnStr = PAGE.FORM101.getFormData(true).searchUsimSnEnd;
						var usimModelStr = PAGE.FORM101.getFormData(true).searchUsimSnHeader;
						//var agentIdStr = PAGE.FORM101.getItemValue("searchAgentNm");
						var cntStr = PAGE.FORM101.getItemValue("searchUsimCnt");
						
						if(usimModelStr == null || usimModelStr == '')
						{
							mvno.ui.alert('유심 모델을 선택하십시오.');
							return;
						}
						if(cntStr == null || cntStr == '')
						{
							mvno.ui.alert('입고 수량을 입력하싮시오.');
							return;
						}
						if(startSnStr == null || startSnStr == '')
						{
							mvno.ui.alert('유심 SN 번호를 입력하십시오.');
							return;
						}
						if(endSnStr == null || endSnStr == '')
						{
							endSnStr = startSnStr;
						}
						
						mvno.ui.confirm("유심 입고( "+cntStr+" 개) 처리 하시겠습니까?", function(){
							
								mvno.cmn.ajax(
									ROOT + "/pps/usim/mgmt/ppsUsimCreateProc.json",
									{
										opCode : "CR",
										telcoCd:"898230",
										usimModel : usimModelStr,
										startSn : startSnStr,
										endSn : endSnStr,
										usimCnt:cntStr,
										remark:memoStr
										//agentId : agentIdStr
										
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
												mvno.ui.alert('유심 입고 처리되엇습니다.');
												
												PAGE.FORM101.setItemValue("memo","");
												PAGE.FORM101.setItemValue("searchUsimSnStart","");
												PAGE.FORM101.setItemValue("searchUsimSnEnd","");
												PAGE.FORM101.setItemValue("searchUsimSnHeader","");
												PAGE.FORM101.setItemValue("searchAgentNm","");
												PAGE.FORM101.setItemValue("searchUsimCnt","");
												PAGE.FORM101.setItemValue("searchAgentStr","");
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
								      ,PAGE.FORM101, "searchAgentNm");
	
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
			,FORM2 : {

			title : "유심 입고취소",
			buttons : {
				center : {
					btnUsimModelReg : { label : "입고취소" }, 
					btnUsimModelCancel : { label : "닫기" }
				}
			},
			items : [
				{type: 'hidden', name: 'crSeq', value: ''},
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
							var crSeq = PAGE.FORM2.getItemValue("crSeq", "");
							var remark = PAGE.FORM2.getItemValue("remark", "");
							var opCode = "CR_CAN";
							
							mvno.ui.confirm("입고를 취소하시겠습니까?", function(){
								mvno.cmn.ajax(
									ROOT + "/pps/usim/mgmt/ppsUsimCreateProc.json",
									{
										opCode : opCode,
										seq : crSeq,
										remark:remark
										
									},
									function(result) {
		
										var data1 = result.result;
										console.log("D",data1);	
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

 			mvno.cmn.ajax4lov(ROOT + "/pps/usim/mgmt/ppsUsimModelDataList.json",{searchLovCd :""  } , PAGE.FORM1, "searchUsimSnHeader");
 			
 			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0079"  } , PAGE.FORM1, "searchType");

			setCurrentDate(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "endDt");
			

		}

	};
		
		
</script>