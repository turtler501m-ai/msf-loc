<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : agency_custmgmt_0010.jsp
   * @Description : 대리점 개통관리
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
	<div id="FORM18" class="section-search"></div>  
	<div id="FORM19" class="section-search"></div>
	<div id="FORM20" class="section-search"></div>
	<div id="FORM23"></div>
	   
	
	<!-- Script -->
	<script type="text/javascript">

	var old_url = "";
	var payinfo_old_url = "";
	var payinfo_path = "";
	var payinfo_path_tmp = "";
	var file_idx = 1;

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

	function goAgentCustInfoData(contractNum,pSize,pIdx){

		mvno.ui.createForm("FORM18");
		var grid1 = PAGE.GRID1;

		var startSize = ((pIdx-1)*pSize);
		var endSize = pIdx*pSize;
		var rowId = "";
		
		for (var i=startSize; i<endSize; i++) {
			var contractNumStr = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("contractNum")).getValue();
			
			if(contractNumStr == contractNum){
				rowId = i;
				break;
			}
		 }
		 		
		var contractNum = 
			  grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("contractNum" ) ).getValue();
		
		var selType2 = "agentSale";
		mvno.cmn.ajax4lov(
				ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
				, {
					selectType : selType2
					}
				 , PAGE.FORM18, "searchAgentSaleId"); 
		
		mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsVacCodeList.json", null, PAGE.FORM18, "vacBankCd");
		
		mvno.cmn.ajax4lov(ROOT + "/pps/agency/custmgmt/PpsCustomerNationInfo.json",{} , PAGE.FORM18, "adNation");	

		mvno.cmn.ajax4lov(ROOT + "/pps/agency/custmgmt/PpsCustomerLanguageInfo.json",{} , PAGE.FORM18, "langCode");
		
		var url = ROOT +"/pps/agency/custmgmt/OpenInfoMgmtDtlList.json";
		mvno.cmn.ajax(
		        url,
		        {
		            contractNum : contractNum
		        }, 
		        function(result) {
		             var result = result.result;
		              var rCode = result.code;
		              var msg = result.msg;
		              if(rCode == "OK") {
		                  PAGE.FORM18.setFormData(result.data.rows[0]);
		                  PAGE.FORM18.setItemValue("searchAgentSaleId",result.data.rows[0].agentSaleId);
		                  
		                  var virAccount = "";
			          	  if(result.data.rows[0].virAccountId == ''){
			          			virAccount = "미등록";
			          	  }else{
			          			virAccount = "("+ result.data.rows[0].vacBankNm +") "+result.data.rows[0].virAccountId;
			          	  }
			          	  
			          	  PAGE.FORM18.setItemValue("virAccount",virAccount);
		              }
		              else {
		                  mvno.ui.notify(msg);
		                 
		              }
		        });	
	
		
		PAGE.FORM18.setItemValue("contractNum",contractNum);
		
		PAGE.FORM18.clearChanged();
		mvno.ui.popupWindowById("FORM18", "고객상세정보", 600, 400, {
            onClose: function(win) {
            	if (! PAGE.FORM18.isChanged()) return true;
            	mvno.ui.confirm("CCMN0005", function(){
            		win.closeForce();
            	});
            }
        });
    
	}
	
	function cmsFormRefresh(contractNumStr)
	{
		mvno.cmn.ajax(
				ROOT + "/pps/agency/custmgmt/PpsUserCmsInfo.json",
				{
					contractNum : contractNumStr
				}
				, function(result) {
					var result = result.result;
					var data = result.data.rows[0];
					
					PAGE.FORM20.setItemValue("cmsBankCode",data.cmsBankCode);
					
					PAGE.FORM20.setItemValue("cmsAccount",data.cmsAccount);
					
					if(data.cmsChargeType == "1" || data.cmsChargeType == "2"){
						PAGE.FORM20.setItemValue("cmsChargeType",data.cmsChargeType);
					}else{
						PAGE.FORM20.setItemValue("cmsChargeType","1");
					}
					
					PAGE.FORM20.setItemValue("cmsTryRemains",data.cmsTryRemains);
					PAGE.FORM20.setItemValue("cmsChargeSelect",data.cmsCharge);
					//PAGE.FORM20.setItemValue("cmsCharge",data.cmsCharge);
					PAGE.FORM20.setItemValue("cmsChargeDate",data.cmsChargeDate);
					//PAGE.FORM20.setItemValue("cmsChargeMonth",data.cmsChargeMonth);
					PAGE.FORM20.setItemValue("cmsChargeMonthSelect",data.cmsChargeMonth);

					//PAGE.FORM20.hideItem("blockCmsType0");
					PAGE.FORM20.hideItem("blockCmsType1");
					PAGE.FORM20.hideItem("blockCmsType2");
					
					switch (data.cmsChargeType) {

					case "1":
						//PAGE.FORM20.hideItem("blockCmsType0");
						PAGE.FORM20.showItem("blockCmsType1");
						PAGE.FORM20.hideItem("blockCmsType2");
						break;
					case "2":
						//PAGE.FORM20.hideItem("blockCmsType0");
						PAGE.FORM20.hideItem("blockCmsType1");
						PAGE.FORM20.showItem("blockCmsType2");
						break;
					default:
						//PAGE.FORM20.showItem("blockCmsType0");
						PAGE.FORM20.showItem("blockCmsType1");
						PAGE.FORM20.hideItem("blockCmsType2");
						break;
					}
				});
	}
	
	function setImgGubunHide(){
		PAGE.FORM19.hideItem("imgGubun1");
        PAGE.FORM19.hideItem("imgGubun2");
        PAGE.FORM19.hideItem("imgGubun3");
        PAGE.FORM19.hideItem("imgGubun4");
        PAGE.FORM19.hideItem("imgGubun5");
        PAGE.FORM19.hideItem("imgGubun6");
        PAGE.FORM19.hideItem("imgGubun7");
        PAGE.FORM19.hideItem("imgGubun8");
        PAGE.FORM19.hideItem("imgGubun9");
        PAGE.FORM19.hideItem("imgGubun10");
	}
	
	function setForm19Uploader(){
        var uploaders = PAGE.FORM19.getUploader("file_upload1");
		//서버에서 한글깨짐으로 encode 2번처리
		var urls = "/pps/filemgmt/procPpsAgecyCustomerImgeInsertUpdate.json?opCode=REG&contractNum=" + PAGE.FORM19.getItemValue("contractNum") + "&imgIdx=" + file_idx + "&imgGubun=" + PAGE.FORM19.getItemValue("imgGubun" + file_idx) + "&memo=" + encodeURIComponent(encodeURIComponent(PAGE.FORM19.getItemValue("memo")));
		
		uploaders.setURL(urls);
		uploaders.setSLURL(urls);
		uploaders.setSWFURL(urls);
	}


		var DHX = {

			// ----------------------------------------
			FORM1 : {

				title : "",
				items : [ 	
							 {type:"block", list:[
								 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
								 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "개통일자",value : "",enableTime : false,calendarPosition:"bottom",inputWidth : 100,value : ""},
								 {type : "newcolumn", offset:3},
								 {type:"block", list:[
									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
									,{type: "label", label: "~"},
			                   	 ]},	 
			                     {type : "newcolumn", offset:3}, 
								 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition:"right" ,inputWidth : 100,value : ""},
								 {type : "newcolumn", offset:10}
								
								 
							 ]},// 1row
							 {type:"block", list:[
								 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
								 {type : "input",label : "잔액",name : "searchStartRemains", width:100, validate: 'ValidInteger',maxLength:9},
								 {type : "newcolumn", offset:3}, 
								 {type:"block", list:[
									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
									,{type: "label", label: "~"}
			                   	  ]},	 
			                     {type : "newcolumn", offset:3},
			                   	 {type : "input",name : "searchEndRemains", width:100, validate: 'ValidInteger',maxLength:9},
			                   	{type : "newcolumn", offset:10},
			                   	//{type : "select",label : "선후불",name : "searchServiceType", width:100},
			                   	{type : "newcolumn", offset:10},
			                   	{type : "select",label:"검색",name : "searchType",width:100, width:100},	
			                   	{type : "newcolumn", offset:3},
			                   	{type:"block", list:[
   									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
   									,{type: "label", label: ":"}
   			                   	 ]},
   			                  {type : "newcolumn", offset:3},
 			                   	{type : "input",name : "searchNm", width:100}	 
   			                  	
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
								
								 if(( data.searchType == null || data.searchType == '' ) && (data.searchNm != null && data.searchNm != '' ))
								{
									 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
									 this.pushError("searchType", "검색조건", "을 선택해주세요");							
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

								if(data.searchType=="customerId")
								{
									if(data.searchNm.match(/^\d+$/ig) == null)
									{
										PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
										PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										this.pushError("searchNm","고객번호","숫자만 입력하세요");
									}
								}

								if(data.searchType=="contractNum")
								{
									if(data.searchNm.match(/^\d+$/ig) == null)
									{
										PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
										PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										this.pushError("searchNm","계약번호","숫자만 입력하세요");
									}
								}

								if(data.searchType=="userSsn")
								{
									if(data.searchNm.match(/^\d+$/ig) == null)
									{
										PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
										PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										this.pushError("searchNm","가입자SSN","숫자만 입력하세요");
									}
								}

								
								if(data.searchType=="customerSsn")
								{
									if(data.searchNm.match(/^\d+$/ig) == null)
									{
										PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
										PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										this.pushError("searchNm","고객SSN","숫자만 입력하세요");
									}
								}

								if(data.searchType=="customerBan")
								{
									if(data.searchNm.match(/^\d+$/ig) == null)
									{
										PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
										PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										this.pushError("searchNm","청구번호","숫자만 입력하세요");
									}
								}

								if(data.searchType=="vacId")
								{
									if(data.searchNm.match(/^\d+$/ig) == null)
									{
										PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
										PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										this.pushError("searchNm","가상계좌번호","숫자만 입력하세요");
									}
								}
								
								
								
					
							}


							,onButtonClick : function(btnId) {
		
							var form = this;
		
							switch (btnId) {
		
								case "btnSearch":
									var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
									PAGE.FORM1.setItemValue("btnCount1", btnCount2)
									if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
									
									PAGE.GRID1.list(ROOT + "/pps/agency/custmgmt/OpenInfoMgmtList.json", form, {
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
				header : "계약번호,이름,CTN,모델명,개통일자,상태,잔액,판매점코드,판매점명,국적,요금제,식별구분,서류,자동이체,계약번호2,선불정액금액",
				columnIds : "contractNumLink,subLinkName,subscriberNo,modelName,enterDate,subStatusNm,basicRemains,agentSaleId,agentSaleNm,adNationNm,serviceNm,custIdntNoIndCdNm,paperImage,cmsChargeTypeNm,contractNum,topupAmt",
				widths : "120,100,100,100,130,100,100,100,100,100,100,100,80,80,0,0", //총 1500
				colAlign : "center,left,center,center,center,center,right,left,left,center,center,left,right,center,center,center",
				colTypes : "link,ro,ro,ro,ro,ro,ron,ro,ro,ro,ro,ro,ron,ro,ro,ron",
				colSorting : "str,str,str,str,str,str,int,str,str,str,str,str,int,str,str,int",
				paging : true,
				pageSize :15,
				pagingStyle :2,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					},
					right :{
						btnRegImage :{label :"서류등록"},
						btnRegCms :{label :"자동이체등록"}
						
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
							var url = "/pps/agency/custmgmt/OpenInfoMgmtListExcel.json";
							 var searchData =  PAGE.FORM1.getFormData(true);
							
						  	  mvno.cmn.download(url+"?menuId=PPS2100001",true,{postData:searchData});
							break;
						
						case "btnRegImage":
						   var fileLimitCnt = 1;
						   
						   var rowData = PAGE.GRID1.getSelectedRowData();
							
						   if(rowData == null){
								mvno.ui.notify("선택된 Data가 없습니다.");
								return;
						   }
		  			       mvno.ui.createForm("FORM19");
		                   PAGE.FORM19.clear();
		                   
		                   mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0063"}, PAGE.FORM19, "imgGubun1");
		                   mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0063"}, PAGE.FORM19, "imgGubun2");
		                   mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0063"}, PAGE.FORM19, "imgGubun3");
		                   mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0063"}, PAGE.FORM19, "imgGubun4");
		                   mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0063"}, PAGE.FORM19, "imgGubun5");
		                   mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0063"}, PAGE.FORM19, "imgGubun6");
		                   mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0063"}, PAGE.FORM19, "imgGubun7");
		                   mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0063"}, PAGE.FORM19, "imgGubun8");
		                   mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0063"}, PAGE.FORM19, "imgGubun9");
		                   mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0063"}, PAGE.FORM19, "imgGubun10");
		                   
		                   setImgGubunHide();
		                   
		                   var contractNum = rowData.contractNum;
		                   var uploader = PAGE.FORM19.getUploader("file_upload1");
		                   
		                   file_idx = 1;
		                   
		                   setForm19Uploader();
			               
			               PAGE.FORM19.setItemValue("contractNum", contractNum);
		                   
		                   uploader.revive();
							
							PAGE.FORM19.attachEvent("onUploadFail",function(realName){
								return true;
							});

		                   mvno.ui.popupWindowById("FORM19", "서류등록", 600, 500, {
		                	    onClose2: function(win) {
		                	        uploader.reset();
		                	      
		                	    }
		                   });	
		                  
			                break;
			                
						case "btnRegCms":
						   var fileLimitCnt = 1;
						   
						   var rowData = PAGE.GRID1.getSelectedRowData();
							
						   if(rowData == null){
								mvno.ui.notify("선택된 Data가 없습니다.");
								return;
						   }
						   
						   if(rowData.cmsChargeTypeNm != ""){
							   mvno.ui.notify("잔액/정기출금 설정된 고객은 등록불가능합니다.");
								return;
						   }
						   
						   var param = {
								   		"opCode" : "CHK",
								   		"contractNum" : rowData.contractNum
								   		};
						   
						   var url = ROOT + "/pps/agency/custmgmt/agencyPpsAgentCmsReqProc.json";
		                   mvno.cmn.ajax(url, param, function(results) {
	                		  	var result = results.result;
								var retCode = result.oRetCd;
								var msg = result.oRetMsg;
	
								if(retCode != "0000") {
				                     mvno.ui.notify(msg);
				                     return;
								}
								
							   	mvno.ui.createForm("FORM20");
			                   	PAGE.FORM20.clear();
			                   	PAGE.FORM20.getInput("cmsAccount").setAttribute("autocomplete","off");
			                   	
			                   	PAGE.FORM20.setItemValue("filePath", "");
			                   	payinfo_path_tmp = "";
			                   	
			                   	var contractNum = rowData.contractNum;
			                   	PAGE.FORM20.setItemValue("contractNum", contractNum);
			                   
			                   	//PAGE.FORM20.hideItem("blockCmsType0");
							   	PAGE.FORM20.hideItem("blockCmsType1");
							   	PAGE.FORM20.hideItem("blockCmsType2");
								
								var topupAmt = rowData.topupAmt;
								
								var selectChargeDtArr = [];
								
								if(topupAmt == ""){
									mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0059"}, PAGE.FORM20, "cmsChargeSelect");
									mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0059"}, PAGE.FORM20, "cmsChargeMonthSelect");
									PAGE.FORM20.enableItem("cmsCharge");
									PAGE.FORM20.enableItem("cmsChargeMonth");  
								}else{
									var rcgAmtData = [{text:topupAmt, value:topupAmt}];
									PAGE.FORM20.reloadOptions("cmsChargeSelect", rcgAmtData);
									PAGE.FORM20.reloadOptions("cmsChargeMonthSelect", rcgAmtData);
									PAGE.FORM20.setItemValue("cmsCharge", topupAmt);
									PAGE.FORM20.setItemValue("cmsChargeMonth", topupAmt);
									PAGE.FORM20.disableItem("cmsCharge");
									PAGE.FORM20.disableItem("cmsChargeMonth");  
									
									selectChargeDtArr.push({text:"만료일 3일전", value:-3});
								}
								
								for(var i=1; i<29; i++){
									selectChargeDtArr.push({text:i, value:(i < 10)?"0"+i:i});
								}
								
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0058"}, PAGE.FORM20, "cmsTryRemains");
								PAGE.FORM20.reloadOptions("cmsChargeDate", selectChargeDtArr);

							   	mvno.cmn.ajax4lov( ROOT + "/pps/hdofc/custmgmt/PpsRcgCodeList.json", null, PAGE.FORM20, "cmsBankCode");
			                   
			                   	cmsFormRefresh(contractNum);
			                   
							   	mvno.ui.popupWindowById("FORM20", "자동이체등록", 900, 500, {
							   		onClose: function(win) {
						            	if (! PAGE.FORM20.isChanged()) return true;
						            	mvno.ui.confirm("CCMN0005", function(){
						            		win.closeForce();
						            	});
						            }
			                   });
	                         
	                       });
			  			   
			                break;
							
					
					}
				}
			},

			//---------------------------------------
			
			

			FORM18: {
				title : "고객상세정보",
			    items:[	
						{type: 'hidden', name: 'contractNum', value: ''}
						,{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, labelHeight:15, blockOffset:0}
						,{type : "input",label : "CTN",name : "subscriberNo",width:150, readonly:true}
						,{type : "input",label : "실사용자명",name : "subLinkName",width:150, readonly:true}
						,{type:"block", list:[
										 {type : "input",label : "판매점 ",name : "searchAgentSaleNm", width:150}
										,{type : "newcolumn", offset:22} 
										, {type : "select",name : "searchAgentSaleId",width:150}
										,{type : "newcolumn", offset:3}
										, {type : "button", value : "변경", name : "btnReg",width:50}
									 	]
						}
						,{type:"block", list:[
										 {type : "select",label : "국적",name : "adNation", width:150}
										,{type : "newcolumn", offset:3}
										,{type:"block", list:[
											{type:"settings", position:"label-left", labelAlign:"left", labelWidth:15, labelHeight:15, blockOffset:0}					
											, {type : "label", label : ""}
										]}
										,{type : "newcolumn", offset:3} 
										, {type : "button", value : "변경", name : "btnNationMod",width:50}
									 	]
						}
						,{type:"block", list:[
										 {type : "select",label : "SMS문자언어",name : "langCode", width:150}
										,{type : "newcolumn", offset:3}
										,{type:"block", list:[
											{type:"settings", position:"label-left", labelAlign:"left", labelWidth:15, labelHeight:15, blockOffset:0}					
											, {type : "label", label : ""}
										]}
										,{type : "newcolumn", offset:3} 
										, {type : "button", value : "변경", name : "btnLangMod",width:50}
									 	]
						}
						,{type : "input",label : "가상계좌",name : "virAccount",width:150, readonly:true}
						,{type:"block", list:[
										 {type : "select",label : "가상계좌변경",name : "vacBankCd", width:150}
										,{type : "newcolumn", offset:3}
										,{type:"block", list:[
											{type:"settings", position:"label-left", labelAlign:"left", labelWidth:15, labelHeight:15, blockOffset:0}					
											, {type : "label", label : ""}
										]}
										,{type : "newcolumn", offset:3} 
										, {type : "button", value : "변경", name : "btnMod",width:50}
										,{type : "newcolumn", offset:3}
										, {type : "button", value : "문자발송", name : "btnDel",width:50}
									 	]
						}
						,{type:"block", list:[
										 {type : "input",label : "잔액",name : "basicRemains", width:150,validate:"ValidNumeric", numberFormat:"0,000,000,000", readonly:true}
											,{type : "newcolumn", offset:3} 
											,{type:"block", list:[
												{type:"settings", position:"label-left", labelAlign:"left", labelWidth:15, labelHeight:15, blockOffset:0}					
												, {type : "label", label : "원"}
											]}	
											,{type : "newcolumn", offset:3}	
											, {type : "button", value : "갱신", name : "remainsUpBtn",width:50}
											,{type : "newcolumn", offset:3}
											, {type : "button", value : "문자발송", name : "btnPrint",width:50}
										 	]
									}
					    ],
					    buttons: {
					    	center : { 
					    		btnSmsNumChgClose : { label : "닫기" }
							}
					    },
					    onKeyUp : function(inp, ev, name, value)
						{
							if(ev.keyCode == 13)
							{
								switch(name)
								{
									case "searchAgentSaleNm":
										var agentSaleStr  = this.getItemValue("searchAgentSaleNm");
										var selType ="agentSale";
										
										mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
												{ 
												  selectType : selType , 
											      searchAgentSaleStr : agentSaleStr
												} 
									      , PAGE.FORM18, "searchAgentSaleId");
										var agentSaleOption =  this.getOptions("searchAgentSaleId");
								        
								           if(agentSaleStr != ""){
								        	   if(agentSaleOption.length==1)
										       {
									        	   //alert("ㄷ대리점이 존재하지 않습니다.");
									           }
									           else if(agentSaleOption.length>=2)
										        {
				
											        var selValue = agentSaleOption[1].value;
				
									        	   this.setItemValue("searchAgentSaleId",selValue);
									           }
									       }

										break;
									
								
								}	
							}
						},
					    onButtonClick : function(btnId, selectedData) {

							switch (btnId) {
								case "btnMod": 
									var opCode = "CHANGE";
									var opMode = "A";
									var userType = "U";
									var contractNum = PAGE.FORM18.getItemValue("contractNum");
									var vacBankCd = PAGE.FORM18.getItemValue("vacBankCd");
									var comment = '선택한 은행으로 가상계좌를 변경 하시겠습니까?';

									if(vacBankCd=="")
									{
											mvno.ui.alert("[가상계좌변경] 은행을 선택하세요");
											return;
									}
									if(confirm(comment))
									{
										  var url = ROOT + "/pps/hdofc/custmgmt/PpsVacProcs.json";
											mvno.cmn.ajax(
													url, 
													{
														opCode : opCode,
														opMode : opMode,
														userType : userType,
														reqBankCode : vacBankCd,
														userNumber : contractNum
														
													}
													, function(results) {
														 var result = results.result;
						                                  var retCode = result.retCode;
						                                  var msg = result.retMsg;
						                                  mvno.ui.notify(msg);

						                                  
						                                  if(retCode == "0000") {
						                                	  //mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsVacCodeList.json", null, PAGE.FORM1, "virBankNm");
						                                	  var url = ROOT + "/pps/agency/custmgmt/OpenInfoMgmtList.json";
						                                	  
						                      				mvno.cmn.ajax(
						                      						url, 
						                      						{
																		contractNum : contractNum 
							                      					}
						                      						, function(results) {
						                      							var result = results.result;
						                                                var retCode = result.code;                       
						                                                var msg = result.retMsg;
						                                                var data = result.data.rows[0];
						                  								
						                                                if(retCode == "OK") {

							            									var virAccountId = data.virAccountId;  
							            									var vacBankCd = data.vacBankCd;
							            									var vacBankNm = data.vacBankNm;                            		
							                                          		
							                                              	var virAccount = "";
							                                        		if(virAccountId == ''){
							                                        			virAccount = "미등록";
							                                        		}else{
							                                        			virAccount = "("+ vacBankNm +") "+virAccountId;
							                                        		}  		        
	
							                                        		PAGE.FORM18.setItemValue("virAccount",virAccount);
							                                        		PAGE.FORM18.setItemValue("vacBankCd",vacBankCd);
						                                                   
						                                                }
						                                                else {
						                                              	  mvno.ui.notify(msg);
						                                                }

						                      						});
				                      						

						                      				
						                                  }
													});

									}		                
									break;
								case "btnDel":	
									var contractNum = PAGE.FORM18.getItemValue("contractNum");
									var comment = '가상계좌 정보를 문자로 발송하시겠습니까?';
									var event    = 'VAC_INFORM';

									if(confirm(comment))
									{
										var url = ROOT + "/pps/hdofc/custmgmt/PpsSmsProcs.json";
										mvno.cmn.ajax(
												url, 
												{
													contractNum : contractNum,
													event : event
													
												}
												, function(results) {
													 var result = results.result;
					                                  var retCode = result.oRetCode;
					                                  var msg = result.oRetMsg;
					                                  mvno.ui.notify(msg);
					                              });	
									}
					                break;
								case "remainsUpBtn":
									var contractNum = PAGE.FORM18.getItemValue("contractNum");
									var url = ROOT + "/pps/agency/rcgmgmt/agencySearchCtn.json";
									mvno.cmn.ajax(
											url, 
											{
												contractNum : contractNum
											}
											, function(results) {
												 var result = results.result;
				                                  var retCode = result.oResCode;
				                                  var msg = result.oResCodeNm;
				                                  mvno.ui.notify(msg);
				                                  if(retCode == "0000") {
				                                	  PAGE.FORM2.setItemValue("basicRemains",result.oRemains);
				                                  }
											});
									break;
								case "btnPrint":	
									var contractNum = PAGE.FORM18.getItemValue("contractNum");
									var comment = '잔액문자전송 하시겠습니까??\n(최신잔액은 잔액갱신후 가능합니다.)';
									var event    = 'REMAINS';

									if(confirm(comment))
									{
										var url = ROOT + "/pps/hdofc/custmgmt/PpsSmsProcs.json";
										mvno.cmn.ajax(
												url, 
												{
													contractNum : contractNum,
													event : event
													
												}
												, function(results) {
													 var result = results.result;
					                                  var retCode = result.oRetCode;
					                                  var msg = result.oRetMsg;
					                                  mvno.ui.notify(msg);
					                              });	
									}
					                break;
								case "btnReg":
									var agentSaleId = PAGE.FORM18.getItemValue("searchAgentSaleId");
									var contractNum = PAGE.FORM18.getItemValue("contractNum");
									var msg = "선택사항을 변경하시겠습니까?";
									var opCode = "AGENTSALECHG";

									mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/custmgmt/PpsAgentInfoChg.json", {
										opCode: opCode,
										agentSaleId: agentSaleId,
										contractNum: contractNum
									}, function(results) {
										var result = results.result;
										var retCode = result.oRetCd;
										var retMsg = result.oRetMsg;
										
										mvno.ui.notify(retMsg);
										if(retCode == "0000"){
											PAGE.GRID1.refresh();
											PAGE.FORM18.setItemValue("searchAgentSaleNm", "");
										}
										
									});
									break; 
									
								case "btnNationMod":
									var adNation = PAGE.FORM18.getItemValue("adNation");
									var contractNum = PAGE.FORM18.getItemValue("contractNum");
									var msg = "선택사항을 변경하시겠습니까?";
									var opCode = "AGENTNATIONCHG";

									mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/custmgmt/PpsAgentInfoChg.json", {
										opCode: opCode,
										adNation: adNation,
										contractNum: contractNum
									}, function(results) {
										var result = results.result;
										var retCode = result.oRetCd;
										var retMsg = result.oRetMsg;
										
										mvno.ui.notify(retMsg);
										if(retCode == "0000"){
											PAGE.GRID1.refresh();
										}
										
									});
									break; 
									
								case "btnLangMod":
									var langCode = PAGE.FORM18.getItemValue("langCode");
									var contractNum = PAGE.FORM18.getItemValue("contractNum");
									var msg = "선택사항을 변경하시겠습니까?";
									var opCode = "AGENTLANGCHG";

									mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/custmgmt/PpsAgentInfoChg.json", {
										opCode: opCode,
										langCode: langCode,
										contractNum: contractNum
									}, function(results) {
										var result = results.result;
										var retCode = result.oRetCd;
										var retMsg = result.oRetMsg;
										
										mvno.ui.notify(retMsg);
										if(retCode == "0000"){
											PAGE.GRID1.refresh();
											
										}
										
									});
									break; 
									
								case "btnSmsNumChgClose":
									mvno.ui.closeWindowById(this);
									break;
					                	
							}
						}
			}
			,FORM19: {
				title : "서류등록",
			    items:[	
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80},
							{type: 'fieldset', label: '고객 이미지 등록',  inputWidth:500, offsetLeft:10, list:[
							{type: 'hidden', name: 'contractNum', value: ''},
							{type: "block", list: [
								{type : "input",label : "메모",name : "memo",width:370 },
								{type : "select",label : "이미지구분1",name : "imgGubun1",width:167 },
								{type : "select",label : "이미지구분2",name : "imgGubun2",width:167 },
								{type : "select",label : "이미지구분3",name : "imgGubun3",width:167 },
								{type : "select",label : "이미지구분4",name : "imgGubun4",width:167 },
								{type : "select",label : "이미지구분5",name : "imgGubun5",width:167 },
								{type : "select",label : "이미지구분6",name : "imgGubun6",width:167 },
								{type : "select",label : "이미지구분7",name : "imgGubun7",width:167 },
								{type : "select",label : "이미지구분8",name : "imgGubun8",width:167 },
								{type : "select",label : "이미지구분9",name : "imgGubun9",width:167 },
								{type : "select",label : "이미지구분10",name : "imgGubun10",width:167 },
								{type : "newcolumn", offset:3},
								{type:"block", blockOffset: 0, list: [	
										   	{type: "upload", label:"첨부파일" 
										   	,name: "file_upload1"
										   	,inputWidth: 330 
											,url: "/pps/filemgmt/procPpsAgecyCustomerImgeInsertUpdate.json" 
										    ,userdata:{limitSize:10000, limitCount:10} 
										   //	,swfPath: "uploader.swf"
										   	,autoStart: false 
										   // ,swfUrl: "/lgs/prdtsrlmgmt/fileUpUsingExcel.do"
											}
										]}
							]}]}
					    ],
					    buttons: {
				            center: {
				            	 btnSave: { label: "확인" },
				                btnClose: { label: "닫기" }
				            }
				        },
				        onChange : function (name, value){
				            var form = this;
				        },
				        onUploadFile : function(realName, serverName) {
				        	$(".button_upload").hide();
		                	   
							if(realName == null || realName == '')
	                    	{
								alert('필수입력 입니다.');
	                        	return false;
	                        }
							
							file_idx = file_idx + 1;
		                    
		                    setForm19Uploader();
		                    
							return true; 
						},
						//@@@
						onBeforeFileRemove : function(realName, serverName){
						   setImgGubunHide();
		           			
	                       for(var i=1; i<$(".dhx_file_name").length; i++){
	                    	   PAGE.FORM19.showItem("imgGubun" + i);
	                       }
	                      
	                	   return true; 
						},
						onBeforeFileAdd : function(realName, size, file){
							$(".button_upload").hide();
		                      
		                      var pathpoint = realName.lastIndexOf('.'); 
		                      var filepoint = realName.substring(pathpoint+1,realName.length); 
		                      var filetype = filepoint.toLowerCase(); 
		                      
		                      if (filetype == 'gif'|| filetype == 'jpg' || filetype == 'jpeg'
		                      	|| filetype == 'tiff' || filetype == 'tif'){

		                      }
		                      else
		                      {
									alert('지원하지 않는 파일 형식 입니다.');
		                          return false;
		                      }
		                      
		                      if(parseInt(size) > 209715200){
		                    	  mvno.ui.alert("이미지는 최대 200M까지 업로드가능합니다.");
		                    	  return false;
		                      }
		                      
		                      setImgGubunHide();
		                      
		                      for(var i=1; i<$(".dhx_file_name").length+2; i++){
		                    	   PAGE.FORM19.showItem("imgGubun" + i);
		                       }
		                      
		                      return true; 
						},
						onUploadComplete : function(count, data){
							$(".button_upload").hide();
							mvno.ui.closeWindowById(PAGE.FORM19, true);  
							mvno.ui.alert("NCMN0015");
							PAGE.FORM19.clear();
		                    PAGE.GRID1.refresh();
							return true;
							
					   },
				        onButtonClick : function(btnId) {
				
				            var form = this; // 혼란방지용으로 미리 설정 (권고)
				            var fileLimitCnt = 1;
				            
				            switch (btnId) {
				               
				                case "btnSave":
				                	
				                	mvno.ui.confirm("서류를 등록하시겠습니까?", function(){
				                		file_idx = 1;
				                		setForm19Uploader();
				                		$(".button_upload").trigger("click");
				                	});
				                	
				                	/*
				                	return;
				                	var data19 = PAGE.FORM19.getFormData(true);
				                	
				                	var url = ROOT + "/pps/filemgmt/procPpsCustomerImgeInsertUpdate.json";
				                     mvno.ui.confirm("서류를 등록하시겠습니까?", function(){
					                	  mvno.cmn.ajax(url, form, function(results) {
					                		  var result = results.result;
												var retCode = result.oRetCode;
												var msg = result.oRetMsg;
												mvno.ui.alert(msg);
					
												if(retCode == "0000") {
					
													 mvno.ui.closeWindowById(form, true);  
								                     mvno.ui.notify("NCMN0015");
								                     PAGE.FORM19.clear();
								                     PAGE.GRID1.refresh();
												}
					                		 
					                         
					                      });
				                     });
				                     */
				
				                break;	
				               
				                case "btnClose" :  
				                    mvno.ui.closeWindowById(form, true);
				                            
				                    
				                    
				                    break;
				              
				               
				            }
				        },
				        onValidateMore : function (data){
				        	//
				        	
				        	var uploaders = this.getUploader("file_upload1");
				        	if(!data.imgGubun){
				        		this.pushError("data.imgGubun","이미지 구분",["ICMN0001"]);
				
				        	}
				        	
				        	else if(!data.file_upload1_count&&data.file_upload1_count==0){
				        		
				        		this.pushError("data.upload1","파일",["ICMN0001"]);
				        	}
				        	
				        }
			}
			,FORM20: {
				title : "자동이체등록",
			    items:[
							{type: 'hidden', name: 'opCode', value: ''}
							,{type: 'hidden', name: 'regTypePop', value: ''}
							,{type: 'hidden', name: 'contractNum', value: ''}
							,{type: 'hidden', name: 'filePath', value: ''}
							,{type: "block", list:[
								,{type: 'settings', position: 'label-left', labelWidth: 110, labelAlign:"right"}
								,{type:"block", list:[
													{type:"label", label:"회원증빙"}
													,{type:"newcolumn"}
													,{type:"block", list:[
														{type:"button", name:"btnImg", label:"",value:"증빙하기", width:50}
														,{type:"newcolumn",offset:10}
														,{type:"label", labelWidth:200, label:"* 증빙서류를 등록해주세요"}
													]		
													}
								]
							 }
							 ,{type:"block", list:[
													{type:"label", label:"계좌정보"}
													,{type:"newcolumn"}
													,{type:"block", list:[
																{type: 'settings', position: 'label-left', labelWidth: 60, inputWidth: 'left'}
														,{type :"select", label:"출금은행", name :"cmsBankCode", width:120 },
														,{type:"newcolumn", offset:10}
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
							 /*
							 ,{type:"block", name:"blockCmsType0", list:[
												{type:"label", label:"미출금설정"}
												,{type:"newcolumn", offset:10}
												,{type:"block", list:[
															{type:"settings", position:"label-right", labelWidth:"auto"}
													,{type:"label", label:"CMS 미출금으로 설정합니다."}
												]}
												,{type:"newcolumn", offset:10}
												,{type:"button", value:"적용하기", name:"btnCmsDisableApply"}
													]
							 }
							 */
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
							 ,{type:"block", name:"", list:[
 											{type:"label", label:""}
 											,{type:"newcolumn", offset:10}
 											,{type:"block", list:[
 												,{type:"settings", position:"label-left", labelWidth:"auto"}
 												,{type:"newcolumn"}
 												,{type:"label", label:"* 계약자와 계좌소유자가 일치한 경우만 자동이체 승인이 가능합니다."}
 											]}
 													]
 						 	}
								
							]}
						] ,
					    buttons: {
				            center: {
				            	btnSave: { label: "저장" }
				            	,btnClose: { label: "닫기" }
				            }
				        },
				        onChange : function (name, value){
				            var form = this;
				            
				            switch(name){
								case "cmsChargeType":
									//PAGE.FORM20.hideItem("blockCmsType0");
									PAGE.FORM20.hideItem("blockCmsType1");
									PAGE.FORM20.hideItem("blockCmsType2");
	
									switch (value) {
									
									case "0":
										//PAGE.FORM20.showItem("blockCmsType0");
										PAGE.FORM20.hideItem("blockCmsType1");
										PAGE.FORM20.hideItem("blockCmsType2");
										break;
									case "1":
										//PAGE.FORM20.hideItem("blockCmsType0");
										PAGE.FORM20.showItem("blockCmsType1");
										PAGE.FORM20.hideItem("blockCmsType2");
										break;
									case "2":
										//PAGE.FORM20.hideItem("blockCmsType0");
										PAGE.FORM20.hideItem("blockCmsType1");
										PAGE.FORM20.showItem("blockCmsType2");
										break;
									}
									break;
								case "cmsChargeMonthSelect":
									PAGE.FORM20.setItemValue("cmsChargeMonth", value);
									break;
								case "cmsChargeSelect":
									PAGE.FORM20.setItemValue("cmsCharge", value);
									break;
							}
				        },
				        onButtonClick : function(btnId) {
				            var form = this; // 혼란방지용으로 미리 설정 (권고)
				            PAGE.FORM20.setItemValue("opCode","REG");
				            
				            switch (btnId) {
				               	
				                case "btnSave":
						            var url = ROOT + "/pps/filemgmt/procAgencyPpsAgentCmsReq.json";
				                	var msg = "등록하시겠습니까?";
				                	
				                	if(PAGE.FORM20.getItemValue("filePath") == ""){
				                		mvno.ui.alert("증빙파일을 등록해주세요");
				                		return;
				                	}
				                	
				                	if(PAGE.FORM20.getItemValue("cmsBankCode") == "" || PAGE.FORM20.getItemValue("cmsAccount") == ""){
				                		mvno.ui.alert("계좌정보를 입력하세요");
				                		return;
				                	}
				                	
				                	if(PAGE.FORM20.getItemValue("cmsChargeType") == "1"){
				                		if(PAGE.FORM20.getItemValue("cmsTryRemains") == "" || PAGE.FORM20.getItemValue("cmsCharge") == ""){
					                		mvno.ui.alert("출금 금액정보를 입력하세요");
					                		return;
					                	}
				                	}else{
				                		if(PAGE.FORM20.getItemValue("cmsChargeDate") == "" || PAGE.FORM20.getItemValue("cmsChargeMonth") == ""){
					                		mvno.ui.alert("출금 금액정보를 입력하세요");
					                		return;
					                	}
				                	}
				                	
				                	mvno.cmn.ajax4confirm(msg, url, form, function(results) {
				                		  var result = results.result;
											var retCode = result.oRetCd;
											var msg = result.oRetMsg;
		
											if(retCode == "0000") {
												 mvno.ui.closeWindowById(form, true);  
							                     mvno.ui.alert("등록되었습니다.");
							                     PAGE.FORM20.clear(); 
											}else{
												mvno.ui.alert(msg);
											}
				                      });
				                	
				                	break;
				                
								case "btnImg":
				                	
								   var fileLimitCnt = 1;
					  			       
				  			       mvno.ui.createForm("FORM23");
				                   PAGE.FORM23.clear();
				                   
				                   mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0071"}, PAGE.FORM23, "regTypePop");
				                   var contractNum = PAGE.FORM20.getItemValue("contractNum");
				                   PAGE.FORM23.setItemValue("contractNum", contractNum);
				                 
				                   var uploader = PAGE.FORM23.getUploader("file_upload1");
				                   
				                   if(payinfo_old_url == ""){
				                	   payinfo_old_url = uploader._url;
				                   }
				                   
								   var url = payinfo_old_url + "?contractNum=" + contractNum;
					               uploader._url = url;   
					               uploader._swf_upolad_url = url;
				  
				                   uploader.revive();
				                   
				                   PAGE.FORM23.attachEvent("onBeforeFileAdd",function(realName, size, file){
				                	      var regTypePop = PAGE.FORM23.getItemValue("regTypePop");

				                	   	  var pathpoint = realName.lastIndexOf('.'); 
					                      var filepoint = realName.substring(pathpoint+1,realName.length); 
					                      var extention = filepoint.toLowerCase(); 
					                      
					                      uploader._url = payinfo_old_url; // url 초기화 contract_num이 계속붙음.
					                      
					                      if(regTypePop == '01'){
					                    	  if (extention == 'gif'|| extention == 'jpg'){

					  	                      }
					  	                      else
					  	                      {
					  								mvno.ui.alert('서면은 gif, jpg만 가능합니다.');
					  	                          return false;
					  	                      }
					                    	  
					                    	  if(parseInt(size) > 307200){
						                    	  mvno.ui.alert("서면은 최대 300KB까지 업로드가능합니다.");
						                    	  return false;
						                      }
					                      }else if(regTypePop == '02'){
					                    	  if (extention == 'mp3'|| extention == 'wmv'){

					  	                      }
					  	                      else
					  	                      {
					  								mvno.ui.alert('녹취는 mp3, wmv만 가능합니다.');
					  	                          return false;
					  	                      }
					                    	  
					                    	  if(parseInt(size) > 204800){
						                    	  mvno.ui.alert("녹취는 최대 200KB까지 업로드가능합니다.");
						                    	  return false;
						                      }
					                      }else{
					                    	  mvno.ui.alert('증빙서류를 선택하세요.');
					                    	  return false;
					                      }
					                      
					                      return true; 
				                   });
				                   
				                   PAGE.FORM23.attachEvent("onUploadFile",function(realName, serverName){
										if(realName == null || realName == '')
				                    	{
											alert('필수입력 입니다.');
				                        	return false;
				                        }
										
										payinfo_path_tmp = serverName;
										
										return true; 
					                      
									});
									PAGE.FORM23.attachEvent("onUploadComplete",function(realName, serverName,result){
									    var form23 = PAGE.FORM23;
										var data3 = form23.getFormData(true);
								   });

				                   mvno.ui.popupWindowById("FORM23", "회원증빙", 600, 300, {
				                	    onClose2: function(win) {
				                	        uploader.reset();
				                	      
				                	    }
				                   });		
									
				
				                   break;
				               
				                case "btnClose" :  
				                    mvno.ui.closeWindowById(form, true);
				                            
				                    
				                    
				                    break;
				              
				               
				            }
				        },
				        onValidateMore : function (data){
				        }
			}
			,FORM23:	{
				items: [
				       	
			   		{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80},
			   			{type: 'fieldset', label: '회원증빙',  inputWidth:500, offsetLeft:10, list:[
			   			{type: 'hidden', name: 'opCode', value: ''},		
			   			{type: "block", list: [
			   			{type : "select",label : "증빙서류",name : "regTypePop",width:167 }, 
			   			{type : "newcolumn", offset:3},
			   			{type:"block", blockOffset: 0, list: [	
			   					   	{type: "upload", label:"첨부파일" 
			   					   	,name: "file_upload1"
			   					   	,inputWidth: 330 
			   						,url: "/pps/filemgmt/ppsFileUpLoad.do"  // 제사용 안하는 파일이므로...
			   					    ,userdata:{limitSize:10000} 
			   					   //	,swfPath: "uploader.swf"
			   					   	,autoStart: true 
			   					   // ,swfUrl: "/lgs/prdtsrlmgmt/fileUpUsingExcel.do"
			   						}
			   					]}
			   			]}]}
				],
		        buttons: {
		            center: {
		            	 btnSave: { label: "저장" },
		                btnClose: { label: "닫기" }
		            }
		        },
		        onChange : function (name, value){
		            var form = this;
		        },
		        onButtonClick : function(btnId) {
		
		            var form = this; // 혼란방지용으로 미리 설정 (권고)
		            var fileLimitCnt = 1;
		            PAGE.FORM23.setItemValue("opCode","REG");
		
		            var contractNum = PAGE.FORM20.getItemValue("contractNum");
		            
		            switch (btnId) {
		               
		                case "btnSave":
		                	if(PAGE.FORM23.getItemValue("regTypePop") == ""){
		                		mvno.ui.notify("증빙서류를 선택하세요");
		                		return;
		                	}
		                	
		                	var uploaders = this.getUploader("file_upload1");
		                	
		                	if(PAGE.FORM23.getItemValue("file_upload1").file_upload1_count == 0){
		                		mvno.ui.notify("파일을 등록하세요");
		                		return;
				        	}
		                	
		                	PAGE.FORM20.setItemValue("filePath", payinfo_path_tmp);
	                		PAGE.FORM20.setItemValue("regTypePop", PAGE.FORM23.getItemValue("regTypePop"));
	                		
		                	payinfo_path_tmp = "";
		                	mvno.ui.alert("저장되었습니다");
		                	mvno.ui.closeWindowById(form, true);
		                	break;	
		               
		                case "btnClose" :  
		                    mvno.ui.closeWindowById(form, true);
		                    break;
		              
		               
		            }
		        },
		        onValidateMore : function (data){
		        	//
		        	
		        	var uploaders = this.getUploader("file_upload1");
		        	if(!data.regTypePop){
		        		this.pushError("data.regTypePop","증빙서류",["ICMN0001"]);
		
		        	}
		        	
		        	else if(!data.file_upload1_count&&data.file_upload1_count==0){
		        		
		        		this.pushError("data.upload1","파일",["ICMN0001"]);
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
				
				if("${agentDocFlag.AGENTDOCFLAG}" == "N" || "${agentDocFlag.AGENTDOCFLAG}" == ""){
					$(".right").hide();
					PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("cmsChargeTypeNm" ) ,true);  
				}
				
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("contractNum" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("topupAmt" ) ,true);

				//setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
				setCurrentDate(PAGE.FORM1, "startDt");
				setCurrentDate(PAGE.FORM1, "endDt");

				//var lovCode="PPS0035";
				//mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode  } , PAGE.FORM1, "searchServiceType");
				
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0037"} , PAGE.FORM1, "searchType");
				
			}

		};
		
		
	</script>
