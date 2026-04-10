<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_custmgmt_0020.jsp
   * @Description : 선불 본사   주의고객관리
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
<div id="FORM11" class="section-search"></div> 	
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

	function goCustDetail(contractNum)
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

			// ----------------------------------------
	FORM1 : {
		items : [ 	
		          	 
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "newcolumn", offset:3},
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "등록일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:10}, 
						 {type : "select",label : "처리근거",name : "searchWarCd1",width:100},
						 {type : "newcolumn", offset:10}, 
						 {type : "select",label : "등록사유",name : "searchWarCd2",width:100}
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
 			                    {type : "newcolumn", offset:3},
 			                   	{type : "input",name : "searchNm", width:100,maxlength:20}
  			            ,{type : "newcolumn", offset:10} 
  			            ,{type : "select",label : "상태",name : "searchSubStatus",width:100}
					 ]},// 2row
					 {type : "newcolumn", offset:10},
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search2"},
					 {type:"hidden", name:"agentId", value:""}, 
					 {type:"hidden", name:"agentSaleId", value:""},
					 {type:"hidden", name:"changeReason", value:""},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"} //버튼 여러번 클릭 막기 변수 by han
					 
				],

		onButtonClick : function(btnId) {

			var form = this;

			switch (btnId) {
			
				case "btnSearch":
					var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
					PAGE.FORM1.setItemValue("btnCount1", btnCount2)
					if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
					
					PAGE.GRID1.list(ROOT + "/pps/hdofc/warmgmt/WarRegInfoMgmtList.json", form, {
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
				null;
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
			
			if(data.searchType=="subscriberNo")
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

		}

		
	}
	,FORM11: {
		title : "주의고객등록"
		,items : [
					{type: 'hidden', name: 'fileName', value: ''},
					{type: 'hidden', name: 'contractNum', value: ''},
					{type:"block", width:700,height:15, list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, labelHeight:15, blockOffset:0}
						,{type:"label", label:"처리근거"}
						,{type:"newcolumn", offset:0}
           				,{type:"block", list:[
        					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0}
							,{type : "select",name : "warCd1", width:120, label:"처리근거", required: true}
						]}
						,{type : "newcolumn", offset:10}
						,{type : "label", label:"", name : "searchDeposit" , labelWidth : 100}
			        ]},
					{type:"block", width:700,height:15, list:[
  						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, labelHeight:15, blockOffset:0}
  						,{type:"label", label:"등록사유"}
  						,{type:"newcolumn", offset:0}
  						,{type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:30, labelHeight:15}
							,{type:"block", list:[
								,{type:"block", list:[
  									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0}					
									,{type : "select", name : "warCd2", width:120, label:"등록사유", validate: "NotEmpty", required: true}
								]}						
							]}												
						]}// 관리번호
  			        ]},
	  			    {type:"block", list:[
											{type:"newcolumn", offset:0}
											,{type: "upload", label:"문자전송엑셀파일" 
											   	,name: "file_upload1"
											   	,inputWidth: 400 
												,url: "/pps/warmgmt/uploadWarExcelFile.do" 
											    ,userdata:{limitSize:1000} 
											   //	,swfPath: "uploader.swf"
											   	,autoStart: true 
											   // ,swfUrl: "/lgs/prdtsrlmgmt/fileUpUsingExcel.do"
												}
											,{type:"newcolumn", offset:5}
											,{type:"button", name: "excelSample", value:"샘플"}
						                   	
	            						]
	                },
   			     	{type:"block", width:700,height:15, list:[
   			             {type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, labelHeight:15, blockOffset:0}
   			           	,{type:"label", label:"메모"}
   			            ,{type:"newcolumn", offset:0}
   			           	,{type:"input", label:"", name: "descInfo", width:500}	// 메모
   			          ]}		
				]
		,buttons: {
		   	center : {
	    		btnWarReg : { label : "등록" }, 
	    		btnWarClose : { label : "닫기" }
			}
		}
		,onButtonClick : function(btnId) {
			    	
					switch (btnId) {
						case "btnWarReg": //주의고객등록
							var contractNum = PAGE.FORM11.getItemValue("contractNum");
							var warCd1 = PAGE.FORM11.getItemValue("warCd1");
							var warCd2 = PAGE.FORM11.getItemValue("warCd2");
							var descInfo = PAGE.FORM11.getItemValue("descInfo");
							
							mvno.ui.confirm("주의고객 등록을 하시겠습니까?", function(){
								mvno.cmn.ajax(ROOT + "/pps/hdofc/warmgmt/PpsWarReg.json", 
										{
											opCode: "REG",
											contractNum: contractNum,
											warCd1: warCd1,
											warCd2: warCd2,
											descInfo: descInfo
										}, function(results) {
											var data = results.result;
											var retCode = data.oRetCd;
											var retMsg = data.oRetMsg;
											mvno.ui.alert(retMsg);
											if(retCode=="0000")
											{
												mvno.ui.closeWindowById(PAGE.FORM11,false);											
												PAGE.GRID1.refresh();
												
											}
								})
							});
	        
							break;
						case "btnWarClose":	
							mvno.ui.closeWindowById("FORM11", true);
			                break;
			                
						case "excelSample":	
							var url = "/pps/warmgmt/WarMgmtExcelSample.json";
		    				mvno.cmn.download(url, false, null);
			                break;
			               
					}
		}
		,onKeyUp : function(inp, ev, name, value){
	
			if(ev.keyCode == 13) {
				null;	
			}
		},
		onChange : function (name, value){
			
			null;
        }
	}	

	,GRID1 : {

		css : {
			height : "550px"
		},
		title : "조회결과",
		header : "계약번호,고객명,요금제,상태,개통일,상태변경일,이용일수,대리점명,판매점명,처리근거,등록사유,등록일자,등록관리자,메모,contractNum",
		columnIds : "contractNumStr,subLinkName,serviceNm,subStatusNm,lstComActvDate,subStatusDate,useTerm,agentNm,agentSaleNm,warCd1Nm,warCd2Nm,enterDate,adminNm,descInfo,contractNum",
		widths : "80,120,100,80,100,100,70,150,150,90,110,140,120,300,0", //총 1500
		colAlign : "center,center,center,center,center,center,right,center,center,center,center,center,center,left,center",
		colTypes : "link,ro,ro,ro,roXd,roXd,ron,ro,ro,ro,ro,roXd,ro,ro,ro", //32
		colSorting : "str,str,str,str,str,str,int,str,str,str,str,str,str,str,str", //32
		paging : true,
		pageSize :15,
		pagingStyle :2,
		buttons : {
			hright : {
				btnDnExcel : { label : "엑셀다운로드" }
			},
			right :{
				btnReg :{label :"등록"},
				btnDel :{label :"삭제"}
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
					
					var url = "/pps/hdofc/warmgmt/WarRegInfoMgmtListExcel.json";
					var searchData =  PAGE.FORM1.getFormData(true);
					 //console.log("searchData",searchData);
										
					 mvno.cmn.download(url+"?menuId=PPS1930001",true,{postData:searchData});
					break;

				case "btnReg":
					
					mvno.ui.createForm("FORM11");
					PAGE.FORM11.setFormData();
					PAGE.FORM11.clearChanged();
					
					PAGE.FORM11.setItemValue("file_upload1", "");

		    		var uploader = PAGE.FORM11.getUploader("file_upload1");

				 	uploader.revive();
				 	
				 	PAGE.FORM11.attachEvent("onBeforeFileAdd",function(realName, size, file){
				 		if(realName.substr(realName.length-3, realName.length) != 'xls' && realName.substr(realName.length-3, realName.length) != 'XLS'){
				 			mvno.ui.alert("주의고객등록은 xls파일만 가능합니다.");
				 			return false;
				 		}
                	    return true;
			       });
				 	
				   PAGE.FORM11.attachEvent("onFileAdd",function(file){
				 		PAGE.FORM11.setItemValue("fileName", file);
			       });
		    		
					PAGE.FORM11.attachEvent("onUploadComplete",function(count){
						var fileName = PAGE.FORM11.getItemValue("fileName");
						mvno.cmn.ajax(ROOT + "/pps/warmgmt/readWarExcelFile.json", 
								{
									fileName: fileName
								}, function(results) {
									var data = results.result;
									PAGE.FORM11.setItemValue("contractNum", data.contractNumArr);
						});
				   });
					
					PAGE.FORM11.attachEvent("onBeforeFileRemove",function(realName, serverName){
						return true;
			       });
					
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0127"  } , PAGE.FORM11, "warCd1");
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0128"  } , PAGE.FORM11, "warCd2");
					
					mvno.ui.popupWindowById("FORM11", "주의고객등록", 800, 350, {
		                onClose: function(win) {
		                	if (! PAGE.FORM11.isChanged()) return true;
		                	win.closeForce();
		                }
		            });	
	
					break;
					
				case "btnDel":
					var rowData = PAGE.GRID1.getSelectedRowData();
					
					if(rowData == null){
						mvno.ui.notify("선택된 Data가 없습니다.");
						return;
					}
					
					mvno.ui.confirm("해당고객을 삭제하시겠습니까?", function(){
						mvno.cmn.ajax(ROOT + "/pps/hdofc/warmgmt/PpsWarReg.json", 
								{
									opCode: "DEL",
									contractNum: rowData.contractNum
								}, function(results) {
									var data = results.result;
									var retCode = data.oRetCd;
									var retMsg = data.oRetMsg;
									mvno.ui.alert(retMsg);
									if(retCode=="0000")
									{									
										PAGE.GRID1.refresh();
										
									}
						})
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
			
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("contractNum" ) ,true);
			
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0092"  } , PAGE.FORM1, "searchSubStatus");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0124"  } , PAGE.FORM1, "searchType");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0127"  } , PAGE.FORM1, "searchWarCd1");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0128"  } , PAGE.FORM1, "searchWarCd2");
			
			var selType ="popPin";

		}

	};
		
</script>