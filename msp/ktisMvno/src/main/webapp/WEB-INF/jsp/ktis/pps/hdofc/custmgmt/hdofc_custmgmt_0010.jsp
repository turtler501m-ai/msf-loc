<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_custmgmt_0010.jsp
   * @Description : 선불 본사   개통내역
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
<div id="FORM21" class="section-search"></div> 	
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

	function goCustInfoChange(contractNum,pSize,pIdx){

		mvno.ui.createForm("FORM19");

		PAGE.FORM19.setItemValue("searchAgentNm","");
		PAGE.FORM19.setItemValue("searchAgentSaleNm","");
		PAGE.FORM19.setItemValue("searchAdNationNm","");
		
		mvno.ui.popupWindowById("FORM19", "대리점수정", 450, 270, {
            onClose: function(win) {
            	if (! PAGE.FORM19.isChanged()) return true;
            	mvno.ui.confirm("CCMN0005", function(){
            		win.closeForce();
            	});
            }
        });

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
		//alert(rowId);
		//var rowId = linenum.substring(0,linenum.length-1);
		var grid1 = PAGE.GRID1;
        
		var agentId = 
			  grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("agentId" ) ).getValue();
		var agentSaleId = 
			  grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("agentSaleId" ) ).getValue();
		var adNation = 
			  grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("adNation" ) ).getValue();
		var langCode = 
			  grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("langCode" ) ).getValue();
		  		  
		var selType1 ="popPin";
		mvno.cmn.ajax4lov(
				ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
				, {selectType : selType1} , PAGE.FORM19, "searchAgentId");

		if(agentId == ''){
			PAGE.FORM19.reloadOptions("searchAgentSaleId",[{label: "- 전체 - ", value:""}]);
		}else{
			var selType2 = "agentSale";

			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
					, {
						selectType : selType2,
						agentId : agentId
						}
					 , PAGE.FORM19, "searchAgentSaleId");
		}

		mvno.cmn.ajax4lov(ROOT + "/pps/agency/custmgmt/PpsCustomerNationInfo.json",{} , PAGE.FORM19, "searchAdNation");	

		mvno.cmn.ajax4lov(ROOT + "/pps/agency/custmgmt/PpsCustomerLanguageInfo.json",{} , PAGE.FORM19, "searchSmsLang");

		//var delaerOption =  PAGE.FORM19.getOptions("searchAgentId");
		PAGE.FORM19.setItemValue("contractNum",contractNum);
		PAGE.FORM19.setItemValue("searchAgentId",agentId);
		PAGE.FORM19.setItemValue("searchAgentSaleId",agentSaleId);
		PAGE.FORM19.setItemValue("searchAdNation",adNation);
		PAGE.FORM19.setItemValue("searchSmsLang",langCode); 	
		PAGE.FORM19.clearChanged(); 				 
		
	}

	function userPaperImageView(contractNum,pNum,pSize,pIdx){
		
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
		//alert(rowId);
		//var rowId = linenum.substring(0,linenum.length-1);
		var pImageStr = "";
		var grid1 = PAGE.GRID1;
		var pNumber = pNum+"";
		switch(pNumber)
		{
			case "1" :
				pImageStr = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("paperImage1" ) ).getValue();
				break;
			case "2" :
				pImageStr = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("paperImage2" ) ).getValue();
				break;
			case "3" :
				pImageStr = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("paperImage3" ) ).getValue();
				break;
			case "4" :
				pImageStr = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("paperImage4" ) ).getValue();
				break;
			case "5" :
				pImageStr = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("paperImage5" ) ).getValue();
				break;
		}

		mvno.cmn.download('/pps/filemgmt/downFile.json?path=' + pImageStr);
	}

<%@ include file="../../hdofcCustDetail.formitems" %>

var DHX = {

			// ----------------------------------------
	FORM1 : {
		items : [ 	
		          	 
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "newcolumn", offset:3},
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "개통일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						 {type : "newcolumn"}, 
						 {type : "input",label : "대리점 ",name : "searchAgentNm", width:100,maxlength:20},
						 {type : "newcolumn", offset:3}, 
						 {type : "select",name : "searchAgentId",width:167}, // Kim Woong 주석지우지마세요
						 {type : "newcolumn", offset:5}, 
						 {type : "select",label : "요금제",name : "searchFeatures",width:100}, // Kim Woong
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "newcolumn", offset:3},
						 {type : "input",label : "잔액",name : "searchStartRemains", width:100,maxlength:9, validate: 'ValidNumeric'},
						 {type : "newcolumn", offset:3}, 
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"}
	                   	  ]},	 
	                     {type : "newcolumn", offset:3}
	                   	,{type : "input",name : "searchEndRemains", width:100 ,maxlength:9, validate: 'ValidNumeric'}
	                   	,{type : "newcolumn"}
	                   	,{type : "input",label : "판매점",name : "searchAgentSale", width:100,maxlength:20}
	                   	,{type : "newcolumn", offset:10}
 			                	,{type : "select",label : "실시간출금",name : "searchRealCms", width:100}
	                   	,{type : "newcolumn", offset:5}
 			                  	,{type : "select",label : "가입서류",name : "searchPaperImg", width:100}
					 ]},// 2row
					 {type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						{type : "newcolumn", offset:3},
						{type : "select",label:"검색",name : "searchType",width:100, width:100},	
	                   	{type : "newcolumn", offset:3},
	                   	{type:"block", list:[
 									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
 									,{type: "label", label: ":"}
 			                   	 ]},	 
 			                    {type : "newcolumn", offset:3},
 			                   	{type : "input",name : "searchNm", width:100,maxlength:20}
  			                ,{type : "newcolumn"} 
						,{type : "hidden",label : "선후불",name : "searchServiceType",width:100, value:'PP'}
  			              	,{type : "newcolumn"}
						,{type : "select",label : "상태",name : "searchSubStatus",width:100}
  			            ,{type : "newcolumn",offset:9}
	                 	,{type : "select",label : "가상계좌", name : "searchVac", width:100}
	                 	,{type : "newcolumn", offset:5}
	                 	,{type : "select",label : "예치금충전",name : "searchDpRcgType",width:100}
					 ]},// 3row
					 {type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"right", labelWidth:80, blockOffset:0}
						,{type : "newcolumn",offset:-17}
						,{type : "select",label : "신규/이동", name : "searchMnpIndCd", width:100}
						,{type : "newcolumn",offset:97}
						,{type : "select",label : "비자접수", name : "searchVizaFlag", width:100}
						,{type : "newcolumn",offset:12}
						,{type : "checkbox", label : "미성년자여부", name : "minorYn", value : "Y"}
  			            ,{type : "newcolumn",offset:9}
	                 	,{type : "checkbox", label:"APP설치동의", name : "appBlckAgrmYn", value : "Y"}
	                 	,{type : "newcolumn", offset:5}		
	                 	,{type : "checkbox", label:"APP설치확인", name : "appInstYn", value : "Y"}
					 ]},// 4row
					 {type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"right", labelWidth:80, blockOffset:0}
						,{type : "newcolumn",offset:-17}
						,{type : "select",label : "충전가능여부", name : "searchRechargeFlag", width:100}
					 ]},// 5row
					 {type : "newcolumn", offset:10},
					 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search4"},
					 {type:"hidden", name:"agentId", value:""}, 
					 {type:"hidden", name:"agentSaleId", value:""},
					 {type:"hidden", name:"changeReason", value:""},
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
					
					PAGE.GRID1.list(ROOT + "/pps/hdofc/custmgmt/OpenInfoMgmtList.json", form, {
						callback : function () {
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						}
					});
					
					var gridData = PAGE.GRID1;
					gridData["whereQuery"] = form;
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
			if(( data.searchType == null || data.searchType == '' ) && (data.searchNm != null && data.searchNm != '' ))
			{
				 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				 this.pushError("searchType", "검색조건", "을 선택해주세요");							
			}
			if(data.searchType=="subscriber_no")
			{
				if(data.searchNm.match(/^\d+$/ig) == null)
				{
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					this.pushError("searchNm","CTN","숫자만 입력하세요");
				}
			}
			else if(data.searchType=="contractNum")
			{
				if(data.searchNm.match(/^\d+$/ig) == null)
				{
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					this.pushError("searchNm","계약번호","숫자만 입력하세요");
				}
			}
			else if(data.searchType=="customerId")
			{
				if(data.searchNm.match(/^\d+$/ig) == null)
				{
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					this.pushError("searchNm","고객번호","숫자만 입력하세요");
				}
			}
			else if(data.searchType=="customerBan")
			{
				if(data.searchNm.match(/^\d+$/ig) == null)
				{
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					this.pushError("searchNm","청구번호","숫자만 입력하세요");
				}
			}
			else if(data.searchType=="customerSsn")
			{
				if(data.searchNm.match(/^\d+$/ig) == null)
				{
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					this.pushError("searchNm","고객SSN","숫자만 입력하세요");
				}
			}
			else if(data.searchType=="userSsn")
			{
				if(data.searchNm.match(/^\d+$/ig) == null)
				{
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					this.pushError("searchNm","가입자SSN","숫자만 입력하세요");
				}
			}
			else if(data.searchType=="vacId")
			{
				if(data.searchNm.match(/^\d+$/ig) == null)
				{
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					this.pushError("searchNm","가상계좌번호","숫자만 입력하세요");
				}
			}
			

		}

		
	}
	,FORM18: {
		title : "대리점일괄변경"
	    ,items: _FORMITEMS_['FORM18']
		,buttons: {
		   	center : {
	    		btnAllAgentChg : { label : "수정" }, 
	    		btnAllAgentClose : { label : "닫기" }
			}
		}
		,onButtonClick : function(btnId) {
			    	
					switch (btnId) {
						case "btnAllAgentChg": //대리점일괄변경
							var whereQuery = this.whereQuery;
							var agentId = PAGE.FORM18.getItemValue("searchAgentId");
							var agentSaleId = PAGE.FORM18.getItemValue("searchAgentSaleId");
							var changeReason = PAGE.FORM18.getItemValue("changeReason");

							whereQuery.setItemValue("agentId",agentId);
							whereQuery.setItemValue("agentSaleId",agentSaleId);
							whereQuery.setItemValue("changeReason",changeReason);
							
							mvno.ui.confirm("해당고객의 대리점 및 판매점을 변경하시겠습니까?", function(){
								mvno.cmn.ajax(ROOT + "/pps/hdofc/custmgmt/PpsAllAgentChg.json", whereQuery, function(dataResult) {
									mvno.ui.notify("변경되었습니다.");
									PAGE.GRID1.refresh();
	                                mvno.ui.closeWindowById("FORM18", true);
								})
							});
	        
							break;
						case "btnAllAgentClose":	
							mvno.ui.closeWindowById("FORM18", true);
			                break;
					}
		}
		,onKeyUp : function(inp, ev, name, value){
	
			if(ev.keyCode == 13) {
				switch(name)
				{
				case "searchAgentSaleNm":
					var agentSaleStr  = this.getItemValue("searchAgentSaleNm");
					var agentId  = this.getItemValue("searchAgentId");
					var selType ="agentSale";
					if(agentId == ''){
						PAGE.FORM18.reloadOptions("searchAgentSaleId",[{label: "- 전체 - ", value:""}]);
						return;
					}
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
							{ 
							  selectType : selType , 
						      searchAgentSaleStr : agentSaleStr,
						      agentId : agentId
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
					case "searchAgentNm":
						var agentStr  = this.getItemValue("searchAgentNm");
						var selType1 ="popPin";
						mvno.cmn.ajax4lov(
								ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
								, {
									selectType : selType1,
									searchAgentStr : agentStr
									}
								 , PAGE.FORM18, "searchAgentId");
						
						
						var agentOption =  this.getOptions("searchAgentId");
				        
				           if(agentStr != ""){
				        	   if(agentOption.length==1)
						       {
					        	   //alert("ㄷ대리점이 존재하지 않습니다.");
					           }
					           else if(agentOption.length>=2)
						        {

							        var selValue = agentOption[1].value;

					        	   this.setItemValue("searchAgentId",selValue);

					        	   var selType ="agentSale";
									
									mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
											{ 
											  selectType : selType , 
										      agentId : selValue
											} 
								      , PAGE.FORM18, "searchAgentSaleId");
									var agentSaleOption =  this.getOptions("searchAgentSaleId");
							        
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
					       }else{
					    	   PAGE.FORM18.reloadOptions("searchAgentSaleId",[{label: "- 전체 - ", value:""}]);
						   }

						break;
						
				}	
			}
		},
		onChange : function (name, value){
			
			if(name == "searchAgentId"){
				if(value == ''){
					PAGE.FORM18.reloadOptions("searchAgentSaleId",[{label: "- 전체 - ", value:""}]);
					return;
				}
				var selType2 = "agentSale";
        		mvno.cmn.ajax4lov(
        				ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
        				, {
        					selectType : selType2,
        					agentId : value
        					}
        				 , PAGE.FORM18, "searchAgentSaleId");
				 }
        }
	}	

		,FORM19: {
			title : "대리점수정"
		    ,items: _FORMITEMS_['FORM19']
			,buttons: {
			   	center : {
		    		btnMod : { label : "수정" }, 
		    		btnAllAgentClose : { label : "닫기" }
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
							var agentId  = this.getItemValue("searchAgentId");
							var selType ="agentSale";
							if(agentId == ''){
								PAGE.FORM19.reloadOptions("searchAgentSaleId",[{label: "- 전체 - ", value:""}]);
								return;
							}
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
									{ 
									  selectType : selType , 
								      searchAgentSaleStr : agentSaleStr,
								      agentId : agentId
									} 
						      , PAGE.FORM19, "searchAgentSaleId");
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
						case "searchAgentNm":
							var agentStr  = this.getItemValue("searchAgentNm");
							var selType1 ="popPin";
							mvno.cmn.ajax4lov(
									ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
									, {
										selectType : selType1,
										searchAgentStr : agentStr
										}
									 , PAGE.FORM19, "searchAgentId");
							
							
							var agentOption =  this.getOptions("searchAgentId");
					        
					           if(agentStr != ""){
					        	   if(agentOption.length==1)
							       {
						        	   //alert("ㄷ대리점이 존재하지 않습니다.");
						           }
						           else if(agentOption.length>=2)
							        {
	
								        var selValue = agentOption[1].value;
	
						        	   this.setItemValue("searchAgentId",selValue);

						        	   var selType ="agentSale";
										
										mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
												{ 
												  selectType : selType , 
											      agentId : selValue
												} 
									      , PAGE.FORM19, "searchAgentSaleId");
										var agentSaleOption =  this.getOptions("searchAgentSaleId");
								        
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
						       }else{
						    	   PAGE.FORM19.reloadOptions("searchAgentSaleId",[{label: "- 전체 - ", value:""}]);
							   }

							break;
						case "searchAdNationNm":
							var adNationStr  = this.getItemValue("searchAdNationNm");

							mvno.cmn.ajax4lov(
									ROOT + "/pps/agency/custmgmt/PpsCustomerNationInfo.json"
									,{
										adNationStr : adNationStr
									 }
									 , PAGE.FORM19, "searchAdNation");
							
							
							var adNationOption =  this.getOptions("searchAdNation");
					        
					           if(adNationStr != ""){
					        	   if(adNationOption.length==1)
							       {
						        	   //alert("ㄷ대리점이 존재하지 않습니다.");
						           }
						           else if(adNationOption.length>=2)
							        {
	
								        var selValue = adNationOption[1].value;
	
						        	   this.setItemValue("searchAdNation",selValue);
						           }
						       }

							break;
					
					}	
				}
			},
			onChange : function (name, value){
				
				if(name == "searchAgentId"){
					if(value == ''){
						PAGE.FORM19.reloadOptions("searchAgentSaleId",[{label: "- 전체 - ", value:""}]);
						return;
					}
					var selType2 = "agentSale";
	        		mvno.cmn.ajax4lov(
	        				ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
	        				, {
	        					selectType : selType2,
	        					agentId : value
	        					}
	        				 , PAGE.FORM19, "searchAgentSaleId");
   				 }
            }
			,onButtonClick : function(btnId) {
				    	var form = this;
						switch (btnId) {
							case "btnMod": 
								
								var contractNum = PAGE.FORM19.getItemValue("contractNum");
								var agentId = PAGE.FORM19.getItemValue("searchAgentId");
								var agentSaleId = PAGE.FORM19.getItemValue("searchAgentSaleId");
								var adNation = PAGE.FORM19.getItemValue("searchAdNation");
								var langCode = PAGE.FORM19.getItemValue("searchSmsLang");
								var msg = "선택사항을 변경하시겠습니까?";
								var opCode = "ALLCHG";

								mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/custmgmt/PpsAgentInfoChg.json", {
									opCode: opCode,
									agentId: agentId,
									agentSaleId: agentSaleId,
									adNation: adNation,
									langCode: langCode,
									contractNum: contractNum
								}, function(results) {
									var result = results.result;
									var retCode = result.oRetCd;
									var retMsg = result.oRetMsg;
									
									mvno.ui.notify(retMsg);
									if(retCode == "0000"){
										mvno.ui.closeWindowById(form, true);
										PAGE.GRID1.refresh();
									}
									
								});
										        
								break;
							case "btnAllAgentClose":	
								mvno.ui.closeWindowById("FORM19", true);
				                break;
						}
			}
				
		
	}
	,FORM21: {
		title : "선불고객수동등록"
	    ,items: _FORMITEMS_['FORM21']
		,buttons: {
		   	center : {
	    		btnCustReg : { label : "등록" }, 
	    		btnAllAgentClose : { label : "닫기" }
			}
		},
		onChange : function (name, value){
			
			if(name == "searchAgentId"){
				if(value == ''){
					PAGE.FORM21.reloadOptions("searchAgentSaleId",[{label: "- 전체 - ", value:""}]);
					return;
				}
				var selType2 = "agentSale";
        		mvno.cmn.ajax4lov(
        				ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
        				, {
        					selectType : selType2,
        					agentId : value
        					}
        				 , PAGE.FORM21, "searchAgentSaleId");
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
						var agentId  = this.getItemValue("searchAgentId");
						var selType ="agentSale";

						if(agentId == ''){
							PAGE.FORM21.reloadOptions("searchAgentSaleId",[{label: "- 전체 - ", value:""}]);
							return;
						}
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
								{ 
								  selectType : selType , 
							      searchAgentSaleStr : agentSaleStr,
							      agentId : agentId
								} 
					      , PAGE.FORM21, "searchAgentSaleId");
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
					case "searchAgentNm":
						
						var agentStr  = this.getItemValue("searchAgentNm");
						var selType1 ="popPin";
						mvno.cmn.ajax4lov(
								ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
								, {
									selectType : selType1,
									searchAgentStr : agentStr
									}
								 , PAGE.FORM21, "searchAgentId");
						
						
						var agentOption =  this.getOptions("searchAgentId");
				        
				           if(agentStr != ""){
				        	   if(agentOption.length==1)
						       {
					        	   //alert("ㄷ대리점이 존재하지 않습니다.");
					           }
					           else if(agentOption.length>=2)
						        {
									
							        var selValue = agentOption[1].value;

					        	   this.setItemValue("searchAgentId",selValue);

					        	   var selType ="agentSale";
									
									mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
											{ 
											  selectType : selType , 
										      agentId : selValue
											} 
								      , PAGE.FORM21, "searchAgentSaleId");
									var agentSaleOption =  this.getOptions("searchAgentSaleId");
							        
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
					       }else{
					    	   PAGE.FORM21.reloadOptions("searchAgentSaleId",[{label: "- 전체 - ", value:""}]);
							}

						break;
					case "searchAdNationNm":
						var adNationStr  = this.getItemValue("searchAdNationNm");

						mvno.cmn.ajax4lov(
								ROOT + "/pps/agency/custmgmt/PpsCustomerNationInfo.json"
								,{
									adNationStr : adNationStr
								 }
								 , PAGE.FORM21, "searchAdNation");
						
						
						var adNationOption =  this.getOptions("searchAdNation");
				        
				           if(adNationStr != ""){
				        	   if(adNationOption.length==1)
						       {
					        	   //alert("ㄷ대리점이 존재하지 않습니다.");
					           }
					           else if(adNationOption.length>=2)
						        {

							        var selValue = adNationOption[1].value;

					        	   this.setItemValue("searchAdNation",selValue);
					           }
					       }

						break;
				
				}	
			}
		}
		,onButtonClick : function(btnId) {
			    	var form = this;
					switch (btnId) {
						case "btnCustReg": 
							var opCode = PAGE.FORM21.getItemValue("opCode");
							var contractNum = PAGE.FORM21.getItemValue("contractNum");
							var subScriberNo = PAGE.FORM21.getItemValue("subscriberNo");
							var userName = PAGE.FORM21.getItemValue("userName");
							var soc = PAGE.FORM21.getItemValue("searchSoc");
							var adNation = PAGE.FORM21.getItemValue("searchAdNation");
							var langCode = PAGE.FORM21.getItemValue("searchSmsLang");							
							var agentId = PAGE.FORM21.getItemValue("searchAgentId");
							var agentSaleId = PAGE.FORM21.getItemValue("searchAgentSaleId");
							var remark = PAGE.FORM21.getItemValue("remark");

							if (! PAGE.FORM21.validate())  return;
							var msg = "선택사항을 변경하시겠습니까?";

							mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/custmgmt/PpsCustomerProc.json", {
								opCode : opCode,
								contractNum: contractNum,
								subscriberNo : subScriberNo,
								userName : userName,
								soc :soc,
								nation: adNation,
								langCode: langCode,
								agentId: agentId,
								agentSaleId: agentSaleId,
								remark : remark
								
							}, function(result) {
								var result = result.result;
								var retCode = result.oRetCd;
								var retMsg = result.oRetMsg;
								
								mvno.ui.notify(retMsg);
								if(retCode == "0000"){
									mvno.ui.closeWindowById(form, true);
									PAGE.GRID1.refresh();
								}
								
							});
									        
							break;
						case "btnAllAgentClose":	
							mvno.ui.closeWindowById("FORM21", true);
			                break;
					}
		}
			
	
}	// FORM end..

	,GRID1 : {

		css : {
			height : "550px"
		},
		title : "조회결과",
		header : "계약번호,상태,모델명,고객명,식별구분,생년월일,개통일자,개통대리점,판매점,요금제,선후불,신규/이동,잔액,만료일,소진일,국적,충전가능여부,비자접수,통신사,서류,대리점아이디,판매점아이디,국적코드,SMS언어코드,서류1,서류2,서류3,서류4,서류5,계약번호히든,모집경로",
		columnIds : "contractNumStr,subStatusNm,modelName,subLinkName,custIdntNoIndCdNm,birthDay,enterDate,agentNm,agentSaleNm,serviceNm,serviceTypeNm,mnpIndCdNm,basicRemains,basicExpire,basicEmptDt,adNationNm,rechargeFlag,vizaFlag,soCdNm,paperImage,agentId,agentSaleId,adNation,langCode,paperImage1,paperImage2,paperImage3,paperImage4,paperImage5,contractNum,srlIfIdNm",
		widths : "100,80,100,150,100,70,120,150,150,100,70,100,80,70,70,70,85,70,70,70,0,0,0,0,0,0,0,0,0,0,70", //총 1500
		colAlign : "center,center,center,left,center,center,center,left,left,center,center,center,right,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center",
		colTypes : "link,ro,ro,ro,ro,ro,roXd,link,ro,ro,ro,ro,ron,roXd,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro", //32
		colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,int,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str", //32
		paging : true,
		pageSize :15,
		pagingStyle :2,
		buttons : {
			hright : {
				btnDnExcel : { label : "엑셀다운로드" }
			},
			right :{
				btnReg :{label :"선불고객수동등록"}, 
				btnDel : { label : "대리점일괄변경" }
				
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
					
					var url = "/pps/hdofc/custmgmt/OpenInfoMgmtListExcel.json";
					var searchData =  PAGE.FORM1.getFormData(true);
					 //console.log("searchData",searchData);
										
					 mvno.cmn.download(url+"?menuId=PPS1100001",true,{postData:searchData});
					break;

				case "btnDel":
					var whereQuery = this.whereQuery;
					var totalCnt = PAGE.GRID1.getRowsNum();
					
					if(parseInt(totalCnt) == 0){
						mvno.ui.notify("검색된 고객이 없습니다.");
						return;
					}
					
					
					mvno.ui.createForm("FORM18");
					  
					  
					var gridData = PAGE.FORM18;
					gridData["whereQuery"] = whereQuery;  
					
					PAGE.FORM18.setItemLabel("changeCustAgent",totalCnt+" 명");
					PAGE.FORM18.setItemValue("searchAgentSaleNm","");
					PAGE.FORM18.setItemValue("searchAgentNm", "");
					PAGE.FORM18.setItemValue("changeReason","");
	 				var selType ="popPin";

					mvno.cmn.ajax4lov(
							ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
							, { selectType : selType } , PAGE.FORM18, "searchAgentId");
					
					 PAGE.FORM18.reloadOptions("searchAgentSaleId",[{label: "- 전체 - ", value:""}]);					  
					 PAGE.FORM18.clearChanged();
		             mvno.ui.popupWindowById("FORM18", "대리점일괄변경", 500, 300, {
		                onClose: function(win) {
		                	if (! PAGE.FORM18.isChanged()) return true;
		                	mvno.ui.confirm("CCMN0005", function(){
		                		win.closeForce();
		                	});
		                }
	                }); 	
					break;

					case "btnReg":
						mvno.ui.createForm("FORM21");
						
						PAGE.FORM21.setItemValue("opCode","REG");
						PAGE.FORM21.setItemValue("contractNum","");
						PAGE.FORM21.setItemValue("subscriberNo","");
						PAGE.FORM21.setItemValue("userName","");
						PAGE.FORM21.setItemValue("remark","");	
						PAGE.FORM21.setItemValue("searchAgentNm","");
						PAGE.FORM21.setItemValue("searchAgentSaleNm","");
						PAGE.FORM21.setItemValue("searchAdNationNm","");
						
						var selType ="popPin";
						mvno.cmn.ajax4lov(
								ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
								, {selectType : selType } , PAGE.FORM21, "searchAgentId");

						PAGE.FORM21.reloadOptions("searchAgentSaleId",[{label: "- 전체 - ", value:""}]); 
						
						mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsFeatureCodeList.json", null, PAGE.FORM21, "searchSoc");
						mvno.cmn.ajax4lov(
								ROOT + "/pps/agency/custmgmt/PpsCustomerNationInfo.json"
								,{}, PAGE.FORM21, "searchAdNation");	

						mvno.cmn.ajax4lov(
								ROOT + "/pps/agency/custmgmt/PpsCustomerLanguageInfo.json"
								,{}, PAGE.FORM21, "searchSmsLang");

						 PAGE.FORM21.clearChanged();
			             mvno.ui.popupWindowById("FORM21", "선불고객수동등록", 500, 430, {
			                onClose: function(win) {
			                	if (! PAGE.FORM21.isChanged()) return true;
			                	mvno.ui.confirm("CCMN0005", function(){
			                		win.closeForce();
			                	});
			                }
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

			//setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "endDt");

			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("agentId" ) ,true);
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("agentSaleId" ) ,true);
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("adNation" ) ,true);
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("langCode" ) ,true);
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("paperImage1" ) ,true);
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("paperImage2" ) ,true);
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("paperImage3" ) ,true);
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("paperImage4" ) ,true);
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("paperImage5" ) ,true);
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("contractNum" ) ,true);
			
			var selType ="popPin";
			mvno.cmn.ajax4lov(
					ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
					, {
						selectType : selType
						}
					 , PAGE.FORM1, "searchAgentId");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsFeatureCodeList.json", null, PAGE.FORM1, "searchFeatures");

			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0006"}, PAGE.FORM1, "searchRealCms");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0001"}, PAGE.FORM1, "searchPaperImg");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0002"}, PAGE.FORM1, "searchType");
			//mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0035"}, PAGE.FORM1, "searchServiceType");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0055"}, PAGE.FORM1, "searchSubStatus");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0036"}, PAGE.FORM1, "searchVac");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0006"}, PAGE.FORM1, "searchDpRcgType");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0069"}, PAGE.FORM1, "searchMnpIndCd");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0078"}, PAGE.FORM1, "searchVizaFlag");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0085"}, PAGE.FORM1, "searchRechargeFlag");

			

		}

	};
		
</script>