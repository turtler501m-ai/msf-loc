<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>  
<div id="FORM10" class="section-search"></div>
<FORM name="viewForm" id="viewForm">
	<input type="hidden" name="AGENT_VERSION" value="">
	<input type="hidden" name="SERVER_URL" value="">
	<input type="hidden" name="VIEW_TYPE" value="TVIEW">
	<input type="hidden" name="FILE_PATH" value="">
	<input type="hidden" name="ENCODE_YN" value="">
	<input type="hidden" name="USE_BM" value="">
	<input type="hidden" name="USE_DEL_BM" value="">
</FORM>
	
<!-- Script -->
<script type="text/javascript">

	//스캔관련
	var blackMakingYn = "Y";					// 블랙마킹사용권한
	var blackMakingFlag = "N";					// 블랙마킹해제권한 (마스킹권한여부에 따라 변화)
	var maskingYn = '${maskingYn}';				// 마스킹권한
	if(maskingYn == "Y") {
		blackMakingFlag = "Y";
	}	
	var agentVersion = '${agentVersion}';		// 스캔버전
	var serverUrl = '${serverUrl}';				// 서버환경 (로컬 : L, 개발 : D, 운영 : R)
	var scanSearchUrl = '${scanSearchUrl}';		// 스캔호출 url
	var scanDownloadUrl = '${scanDownloadUrl}';	// 스캔파일 download url
	var fileExt = "";
	var filePath = "";

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
	
	var DHX = {
	
		FORM1 : {
			items : [ 
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "등록일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : "",labelWidth : 80, offsetLeft:0},
						 {type : "newcolumn", offset:3},
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 {type : "select",label : "상태",name : "status",width:100},
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "input",label : "대리점 ",name : "searchAgentNm", width:100,maxlength:20,labelWidth : 80},
		                 {type : "newcolumn", offset:11},
		                 {type : "select",name : "searchAgentId",width:100},
		                 {type : "newcolumn", offset:3},
		                 {type : "select",label:"검색",name : "searchType",width:100, width:100},	
		                 {type : "newcolumn", offset:3},
	                   	 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: ":"}
	                   	 ]},	 
	                    {type : "newcolumn", offset:3},
	                   	{type : "input",name : "searchNm", width:100,maxlength:20}
					 ]},
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search2"},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"} //버튼 여러번 클릭 막기 변수 by han
			]
			,onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
				
					case "btnSearch":
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/imgchkmgmt/PpsAgentCmsReqMgmtList.json", form, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						
						break;
				}
	
			},
			onChange : function(name, value) {
				switch(name){
					case "searchRcgAmt":
						
						var rcgVal = PAGE.FORM1.getItemValue("searchRcgAmt"); 
						
						PAGE.FORM1.disableItem("searchRcgAmt2");
						PAGE.FORM1.setItemValue("searchRcgAmt2", rcgVal);
						
						if("ETC" == rcgVal){
							PAGE.FORM1.enableItem("searchRcgAmt2");
							PAGE.FORM1.setItemValue("searchRcgAmt2", 0);
						}
						else if("" == rcgVal){
							PAGE.FORM1.disableItem("searchRcgAmt2");
							PAGE.FORM1.setItemValue("searchRcgAmt2", 0);
						}
						
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
			}
			,onValidateMore : function(data){
				if(data.startDt == '')
				{
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("startDt", "개통일자", "사용일자을 입력해주세요.");
				}
				
				if(data.endDt == '')
				{
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("endDt", "개통일자", "사용일자을 입력해주세요.");
				}
				
				if(data.startDt > data.endDt)
				{
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

				 if(data.searchType=="contractNum" )
				 {
					
					  if(data.searchNm.match(/^\d+$/ig) == null){
						  PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						  PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						  this.pushError("searchNm","계약번호","숫자만 입력하세요");
					   }
							
				  }
				
				if(data.searchType=="subscriberNo")
				{
					if(data.searchNm.match(/^\d+$/ig) == null)
					{
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						this.pushError("searchNm","CTN","숫자만 입력하세요");
					}
				}
					
			}
		}
		,GRID1 : {
			css : {
				height : "550px"
			}
			,title : "조회결과"
			,header : "계약번호,대리점,상태,요청구분,잔액부족기준액,잔액부족출금액,정기출금일자,정기출금액,은행,등록일,수정일,수정관리자,seq,contractNum"
			,columnIds : "contractNumStr,agentNm,statusNm,cmsChargeTypeNm,cmsTryRemains,cmsCharge,cmsChargeDate,cmsChargeMonth,cmsBankCodeNm,regDt,modDt,modAdminNm,seq,contractNum"
			,widths : "80,150,60,80,100,100,80,70,100,120,120,120,0,0"
			,colAlign : "center,center,center,center,right,right,center,right,center,center,center,center,center,center"
			,colTypes : "link,ro,ro,ro,ron,ron,ro,ron,ro,roXp,roXp,ro,ro,ro"
			,colSorting : "str,str,str,str,int,int,str,int,str,str,str,str,str,str"
			,paging : true
			,pageSize :15
			,pagingStyle :2
			,buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }, 
				
				},
				right : {
					btnMod : { label : "수정" }
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
						var url = "/pps/hdofc/imgchkmgmt/PpsAgentCmsReqMgmtListExcel.json";
						var searchData =  PAGE.FORM1.getFormData(true);
	
					  	mvno.cmn.download(url+"?menuId=PPS1940004",true,{postData:searchData});
						break;
						
					case "btnMod":
						var form = PAGE.GRID1.getSelectedRowData();
						
						if(form == null) {
	                     	mvno.ui.notify("선택된 데이터가 존재하지 않습니다");
	                     	return;
	                  	} 
						 
						mvno.cmn.ajax(ROOT + "/pps/hdofc/imgchkmgmt/PpsAgentCmsReqRow.json", form, function(dataResult) {
							
							if(dataResult.result.data.rows.length > 0){
								var ret = dataResult.result.data.rows[0];
								
								mvno.ui.createForm("FORM10");
								PAGE.FORM10.clearChanged();
								PAGE.FORM10.getInput("cmsAccount").setAttribute("autocomplete","off");
								
								var selectChargeDtArr = [];
								
								if(ret.topupAmt == "" || ret.topupAmt == null){
									mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0059"}, PAGE.FORM10, "cmsChargeSelect");
									mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0059"}, PAGE.FORM10, "cmsChargeMonthSelect");
									PAGE.FORM10.enableItem("cmsCharge");
									PAGE.FORM10.enableItem("cmsChargeMonth");  
								}else{
									var rcgAmtData = [{text:ret.topupAmt, value:ret.topupAmt}];
									PAGE.FORM10.reloadOptions("cmsChargeSelect", rcgAmtData);
									PAGE.FORM10.reloadOptions("cmsChargeMonthSelect", rcgAmtData);
									PAGE.FORM10.disableItem("cmsCharge");
									PAGE.FORM10.disableItem("cmsChargeMonth"); 
									
									selectChargeDtArr.push({text:"만료일 3일전", value:-3});
								}
								
								for(var i=1; i<29; i++){
									selectChargeDtArr.push({text:i, value:(i < 10)?"0"+i:i});
								}
								
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0058"}, PAGE.FORM10, "cmsTryRemains");
								PAGE.FORM10.reloadOptions("cmsChargeDate", selectChargeDtArr);

							   	mvno.cmn.ajax4lov( ROOT + "/pps/hdofc/custmgmt/PpsRcgCodeList.json", null, PAGE.FORM10, "cmsBankCode");
							   	mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0141"}, PAGE.FORM10, "status");
								
							   	/****/
							   	PAGE.FORM10.setItemValue("contractNum",form.contractNum);
							   	PAGE.FORM10.setItemValue("seq",form.seq);
							   	PAGE.FORM10.setItemValue("cmsBankCode",ret.cmsBankCode);
								PAGE.FORM10.setItemValue("cmsAccount",ret.cmsAccount);
								PAGE.FORM10.setItemValue("cmsChargeType",ret.cmsChargeType);
								PAGE.FORM10.setItemValue("cmsTryRemains",ret.cmsTryRemains);
								PAGE.FORM10.setItemValue("cmsChargeSelect",ret.cmsCharge);
								PAGE.FORM10.setItemValue("cmsCharge",ret.cmsCharge);
								PAGE.FORM10.setItemValue("cmsChargeDate",ret.cmsChargeDate);
								PAGE.FORM10.setItemValue("cmsChargeMonth",ret.cmsChargeMonth);
								PAGE.FORM10.setItemValue("cmsChargeMonthSelect",ret.cmsChargeMonth);
								PAGE.FORM10.setItemValue("status",ret.status);
								PAGE.FORM10.setItemValue("adminMemo",ret.adminMemo);
								fileExt = ret.fileType;
								filePath = ret.filePath;

								//PAGE.FORM10.hideItem("blockCmsType0");
								PAGE.FORM10.hideItem("blockCmsType1");
								PAGE.FORM10.hideItem("blockCmsType2");
								
								switch (ret.cmsChargeType) {

								case "1":
									//PAGE.FORM10.hideItem("blockCmsType0");
									PAGE.FORM10.showItem("blockCmsType1");
									PAGE.FORM10.hideItem("blockCmsType2");
									break;
								case "2":
									//PAGE.FORM10.hideItem("blockCmsType0");
									PAGE.FORM10.hideItem("blockCmsType1");
									PAGE.FORM10.showItem("blockCmsType2");
									break;
								default:
									//PAGE.FORM10.showItem("blockCmsType0");
									PAGE.FORM10.showItem("blockCmsType1");
									PAGE.FORM10.hideItem("blockCmsType2");
									break;
								}
							   	/****/
							   	
							   	mvno.cmn.ajax(ROOT + "/pps/hdofc/imgchkmgmt/PpsAgentCmsReqCustInfo.json", form, function(dataResult) {
									console.log(dataResult);
									if(dataResult.result.data.rows.length > 0){
										var ret = dataResult.result.data.rows[0];
										PAGE.FORM10.setItemValue("subLinkName",ret.subLinkName);
										PAGE.FORM10.setItemValue("userSsn",ret.userSsn);
									}
								});
								
								mvno.ui.popupWindowById("FORM10", "자동이체수정", 950, 350, {
					                onClose: function(win) {
					                	if (! PAGE.FORM10.isChanged()) return true;
					                	mvno.ui.confirm("CCMN0005", function(){
					                		win.closeForce();
					                	});
					                }
				                });	
							}else{
								mvno.ui.notify("내역을 찾을 수 없습니다.");
								return;
							}
								
						});
						
						break;
				}
			}
		},
		FORM10 : {
			title : "자동이체수정",
			items : [
						{type: 'hidden', name: 'opCode', value: ''}
						,{type: 'hidden', name: 'contractNum', value: ''}
						,{type: 'hidden', name: 'seq', value: ''}
				 		,{type:"block", list:[
							{type:"settings", position:"label-left", labelWidth:110}
   							,{type:"block", list:[
								{type:"label", label:"상태"}
   								,{type:"newcolumn"}
   								,{type :"select", label:"", name :"status", width:120 }
   							]}
   							]
   					 	}
				 		,{type:"block", list:[
							{type:"settings", position:"label-left", labelWidth:110}
							,{type:"block", list:[
								{type:"label", label:"메모"}
								,{type:"newcolumn"}
								,{type :"input", label:"", name :"adminMemo", width:376}
							]}
							]
  					 	}
						,{type: "block", list:[
							,{type: 'settings', position: 'label-left', labelWidth: 110, labelAlign:"right"}
							,{type:"block", list:[
												{type:"label", label:"회원증빙"}
												,{type:"newcolumn"}
												,{type:"block", list:[
													{type:"button", name:"btnImg", label:"",value:"서류보기", width:50}
												]		
												}
												,{type:"newcolumn", offset:10}
												,{type:"block", list:[
													{type:"settings", position:"label-right", labelWidth:400}
													,{type:"label", label:"* 계약자와 자동이체 신청자가 동일한 경우 승인하여 주세요"}
													]
												}
							]
						 }
						 ,{type:"block", list:[
												{type:"label", label:"계좌정보"}
												,{type:"newcolumn"}
												,{type:"block", list:[
													{type: 'settings', position: 'label-left', labelWidth: 60, inputWidth: 'left'}
													,{type:"input", label:"예금주명", name : "subLinkName", width:120, readonly:true, validate:"", maxLength:20}
													,{type:"newcolumn", offset:5}
													,{type:"input", label:"주민번호", name : "userSsn", width:120, readonly:true, validate:""}
													,{type:"newcolumn", offset:5}
													,{type :"select", label:"출금은행", name :"cmsBankCode", width:80 },
													,{type:"newcolumn", offset:5}
													,{type:"input", label:"계좌번호", name : "cmsAccount", width:120, validate:"ValidNumeric", maxLength:20}
												]		
												}
							]
						 }
						 ,{type:"block", list:[
									{type:"label", label:"출금구분"}
									,{type:"newcolumn",offset:10}
									,{type:"block", list:[
										{type:"settings", position:"label-right", labelWidth:"auto"}
										/*,{type: "radio", name: "cmsChargeType", value: "0", label: "미출금설정", checked: true}
										,{type:"newcolumn", offset:20}*/
										,{type: "radio", name: "cmsChargeType", value: "1", label: "잔액부족 출금설정"}
										,{type:"newcolumn", offset:20}
										,{type: "radio", name: "cmsChargeType", value: "2", label: "정기일자 출금설정"}
										,{type:"newcolumn", offset:20}
										,{type:"block", list:[
												{type:"settings", position:"label-right", labelWidth:180}
												,{type:"label", label:"* 설정할 목록을 선택하세요"}
												]
											}
										]
									}
								]
						 }
						 ,{type:"block", name:"blockCmsType1", list:[
										{type:"label", label:"잔액부족시출금"}
										,{type:"newcolumn", offset:10}
										,{type:"block", list:[
											,{type:"settings", position:"label-left", labelWidth:"auto"}
											,{type:"newcolumn"}
											,{type :"select", label:"남은잔액이 : ", name :"cmsTryRemains", width:103 },
											,{type:"newcolumn", offset:10}
											,{type :"select", label:" 원 이하가 되면", name :"cmsChargeSelect", width:100	},
											,{type:"newcolumn", offset:10}
											,{type :"input", label:"", name :"cmsCharge", width:100, validate:"ValidNumeric", maxLength:5}
											,{type:"newcolumn", offset:10}
											,{type:"label", label:"원을 출금합니다."}
										]}
												]
						 }
						 ,{type:"block", name:"blockCmsType2", list:[
										{type:"label", label:"정기일자출금"}
										,{type:"newcolumn", offset:10}
										,{type:"block", list:[
											,{type:"settings", position:"label-left", labelWidth:"auto"}
											,{type:"newcolumn"}
											,{type :"select", label:"매월 : ", name :"cmsChargeDate" },
											,{type:"newcolumn", offset:10}
											,{type :"select", label:" 일 마다", name :"cmsChargeMonthSelect", width:100},
											,{type:"newcolumn", offset:10}
											,{type :"input", label:"", name :"cmsChargeMonth", width:100, validate:"ValidNumeric", maxLength:5},
											,{type:"newcolumn", offset:10}
											,{type:"label", label:"원을 출금합니다."}
										]}
												]
					 	}
						]}
			]
			,buttons : {
				center: {
	            	btnSave: { label: "저장" }
	            	,btnClose: { label: "닫기" }
	            }
			}
			,onKeyDown : function (inp, ev, name, value) {
				
			}
			,onValidateMore : function (data) {
				var form=this;
				
			}
			,onChange : function(name, value) {
				var form = this;
	            
	            switch(name){
					case "cmsChargeType":
						//PAGE.FORM10.hideItem("blockCmsType0");
						PAGE.FORM10.hideItem("blockCmsType1");
						PAGE.FORM10.hideItem("blockCmsType2");

						switch (value) {
						
						case "0":
							//PAGE.FORM10.showItem("blockCmsType0");
							PAGE.FORM10.hideItem("blockCmsType1");
							PAGE.FORM10.hideItem("blockCmsType2");
							break;
						case "1":
							//PAGE.FORM10.hideItem("blockCmsType0");
							PAGE.FORM10.showItem("blockCmsType1");
							PAGE.FORM10.hideItem("blockCmsType2");
							break;
						case "2":
							//PAGE.FORM10.hideItem("blockCmsType0");
							PAGE.FORM10.hideItem("blockCmsType1");
							PAGE.FORM10.showItem("blockCmsType2");
							break;
						}
						break;
					case "cmsChargeMonthSelect":
						PAGE.FORM10.setItemValue("cmsChargeMonth", value);
						break;
					case "cmsChargeSelect":
						PAGE.FORM10.setItemValue("cmsCharge", value);
						break;
				}
				
			}
			//,checkSelectedButtons : ["btnSave","btnMod"]
			,onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
					//대리점 검색
					case "btnSave":
			            var url = ROOT + "/pps/hdofc/imgchkmgmt/PpsAgentCmsReqProc.json";
	                	var msg = "수정하시겠습니까?";
	                	
	                	PAGE.FORM10.setItemValue("opCode", "MOD");
	                	
	                	if(PAGE.FORM10.getItemValue("cmsBankCode") == "" || PAGE.FORM10.getItemValue("cmsAccount") == ""){
	                		mvno.ui.alert("계좌정보를 입력하세요");
	                		return;
	                	}
	                	
	                	if(PAGE.FORM10.getItemValue("cmsChargeType") == "1"){
	                		if(PAGE.FORM10.getItemValue("cmsTryRemains") == "" || PAGE.FORM10.getItemValue("cmsCharge") == ""){
		                		mvno.ui.alert("출금 금액정보를 입력하세요");
		                		return;
		                	}
	                	}else{
	                		if(PAGE.FORM10.getItemValue("cmsChargeDate") == "" || PAGE.FORM10.getItemValue("cmsChargeMonth") == ""){
		                		mvno.ui.alert("출금 금액정보를 입력하세요");
		                		return;
		                	}
	                	}
	                	
	                	mvno.cmn.ajax4confirm(msg, url, form, function(results) {
	                		  var result = results.result;
								var retCode = result.oRetCd;
								var msg = result.oRetMsg;
	
								if(retCode == "0000") {
									 PAGE.GRID1.refresh();
									 mvno.ui.closeWindowById(form, true);  
				                     mvno.ui.alert("수정되었습니다.");
				                     PAGE.FORM10.clear(); 
								}else{
									mvno.ui.alert(msg);
								}
	                      });
	                	
	                	break;
					
					case "btnImg":
						if(fileExt == "jpg" || fileExt == "jpeg" ||  fileExt == "gif" ||  fileExt == "tif" ||  fileExt == "tiff"){ //확장자를 확인합니다.

							var width = 750;
							var height = 650;
							var top  = 10;
							var left = 10;

							document.viewForm.FILE_PATH.value = filePath;
							document.viewForm.ENCODE_YN.value = "Y";
							document.viewForm.AGENT_VERSION.value = agentVersion;
							document.viewForm.SERVER_URL.value = serverUrl;
							document.viewForm.USE_BM.value = blackMakingYn;	
							document.viewForm.USE_DEL_BM.value = blackMakingFlag;					
	
		 					var data = $("#viewForm").serialize();
							var url = scanSearchUrl + "?" + encodeURIComponent(data);
										
							// 타임아웃 설정 - 10초이내 응답없으면 서비스 실행상태가 아님.
							var timeOutTimer = window.setTimeout(function (){
								mvno.ui.confirm("이미지 프로그램이 설치되지 않았거나 실행중이 아닙니다. 설치페이지로 이동 하시겠습니까?",function(){
									window.open(scanDownloadUrl, "", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);         // 설치페이지로 이동
								});
							}, 10000);
							 
							$.ajax({     
								type : "GET", 
								url : url,     
								crossDomain : true,
								dataType : 'jsonp',
								success : function(args){
									window.clearTimeout(timeOutTimer);
									if(args.RESULT == "SUCCESS") {
										console.log("SUCCESS");
									} else if(args.RESULT == "ERROR_TYPE2") {
										mvno.ui.alert("버전이 다릅니다. 설치페이지로 이동해주세요.");
										window.open(scanDownloadUrl, "", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);         // 설치페이지로 이동
									} else {
										mvno.ui.alert(args.RESULT + " : " + args.RESULT_MSG);
									}
								}
							});
						}else{
							
							mvno.cmn.download('/pps/filemgmt/downFile.json?path=' + filePath);
							
						}
						
						break;
					case "btnClose":
						mvno.ui.closeWindowById("FORM10", true);
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
			setCurrentDate(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "endDt");
			
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("seq" ) ,true);
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("contractNum" ) ,true);
			
			var selType ="popPin";
			mvno.cmn.ajax4lov(
					ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
					, {
						selectType : selType
						}
					 , PAGE.FORM1, "searchAgentId");
			
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0141"}, PAGE.FORM1, "status");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0054"}, PAGE.FORM1, "searchType");
		}
	};
		
</script>