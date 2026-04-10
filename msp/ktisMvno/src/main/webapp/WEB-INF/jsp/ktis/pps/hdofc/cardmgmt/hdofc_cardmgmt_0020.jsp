<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_cardmgmt_0020.jsp
   * @Description : 본사 선불카드관리 PIN생성관리
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
	<div id="GROUP1">
		<div id="FORM2" class="section-search" ></div>
	</div>
	<div id="FORM3" class="section-search" ></div>
	
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
	
	function goPinCreateCancel(crSeq,pSize,pIdx)
	{
		mvno.ui.prompt("핀생성취소사유", function(result){
			
			var msg = "해당내역의 핀생성을 취소하시겠습니까?";
			mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/cardmgmt/PpsPinInfoCreate.json", {
				opCode : "CANCEL",
			 	crSeq : crSeq,
			 	remark : result
			}, function(results) {
				mvno.ui.notify(results.result.retMsg);
				if(results.result.retCode == "0000"){
					PAGE.GRID1.refresh();	
				}
			},null);
			
		} , { required:true,lines:1,maxLength : 80 });

	}

	function goPinInfoExcel(crSeq,pSize,pIdx)
	{
		var startSize = (pIdx-1)*pSize;
		var endSize = pIdx*pSize;
		var rowId = "";
		for (var i=startSize; i<endSize; i++) 
		{
			 var crSeqStr = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("crSeq")).getValue();
			 //alert(crSeqStr);
			if(crSeqStr == crSeq)
			{
				rowId = i;
				break;
			}

		}


		
		//alert(rowId);
	  var grid1 = PAGE.GRID1;
	  	 var searchData = grid1.getRowData(rowId+1);
	  	 //console.log(searchData);
	  	 //alert(searchData.startMngCode);
		

		 var url= "/pps/hdofc/cardmgmt/PinInfoListExcel.json";

		 mvno.cmn.download(url+"?menuId=PPS1400002",true,{postData:searchData});

		 	
		
	

	}

	

		var DHX = {

			// ----------------------------------------
			FORM1 : {

				title : "",
				items : [ 	
							 {type:"block", list:[
								 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
								 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "생성일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 110,value : ""},
								 {type : "newcolumn", offset:3},
								 {type:"block", list:[
									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
									,{type: "label", label: "~"},
			                   	 ]},	 
			                     {type : "newcolumn", offset:3}, 
								 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 110,value : ""},
								 {type : "newcolumn",offset:40}, 
								 {type : "select",label :"ON/OFF구분",labelWidth:104, name : "searchOnOffGubun",width:110,userdata: { lov: "PPS0025"}}, 
								 {type : "newcolumn",offset:10}, 
								 {type : "select",label :"구분",labelWidth:60,name : "searchPinGubun",width:110,userdata: { lov: "PPS0026"}}
								 
							 ]},// 1row
							 {type:"block", list:[
								 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
								 	{type : "select", label :"관리번호", name : "searchManageCodeHeader", width:110}, // KimWoong
									{type : "newcolumn"}, 
									{type:"block", list:[
	   									{type:"settings", position:"label-left", labelAlign:"left",labelHeight:15, blockOffset:0},
	   									{type : "newcolumn", offset:11},	
	   									{type : "input", label :"", name : "searchStartMngCode", width:110,maxlength:9, validate: 'ValidNumeric'},
										{type : "newcolumn", offset:5},	
	   									{type: "label", label: "부터",labelWidth:30},
	   									{type : "newcolumn"},
	   									{type : "input", label :"", name : "searchEndMngCode", width:110, labelWidth:0,maxlength:9, validate: 'ValidNumeric'},
										{type : "newcolumn", offset:5},
										{type: "label", label: "까지",labelWidth:30}
	   			                   	 ]},
	   			                  {type : "newcolumn",offset:84}, 
								  {type : "select",label :"취소여부",labelWidth:60,name : "searchCancelFlag",width:110}
			                   
							 ]},// 2row
							 {type : "newcolumn", offset:10},
							 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : "button",name : "btnSearch",value : "조회", className:"btn-search2"} 
						],
						onValidateMore : function(data){

							 if(data.startDt > data.endDt){
								 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
							}

						},
						onButtonClick : function(btnId) {

							var form = this;
		
							switch (btnId) {
		
								case "btnSearch":
									var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
									PAGE.FORM1.setItemValue("btnCount1", btnCount2)
									if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
									
									PAGE.GRID1.list(ROOT + " /pps/hdofc/cardmgmt/PinCreateMgmtList.json", form, {
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
				header : " PIN관리번호,PIN구분, ON/OFF구분,PIN길이,PIN개수,등록관리자,등록일자,PIN금액,취소,비고,시작관리번호,종료관리번,일련번호,생성일련번호",
				columnIds : "mngCodeStr,pinGubunNm,onoffGubunNm,pinLength,crCount,crAdminNm,crDate,startPrice,cancelFlagNm,remark,startMngCode,endMngCode,linenum,crSeq",
				widths : "220,100,100,80,80,100,100,70,80,200,0,0,0,0", //총 1500
				colAlign : "center,center,center,right,right,left,center,right,center,left,center,center,left,left",
				colTypes : "link,ro,ro,ron,ron,ro,ro,ron,link,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,int,int,str,str,int,str,str,str,str,str,str",
				paging : true,
				pageSize :15,
				pagingStyle :2,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }, 
					},
					right : {
						btnReg : { label : "등록" }
						
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
							
							var url = "/pps/hdofc/cardmgmt/PinCreateMgmtListExcel.json";
							 var searchData =  PAGE.FORM1.getFormData(true);

							 	console.log("searchData",searchData);
							
						  		mvno.cmn.download(url+"?menuId=PPS1400002",true,{postData:searchData});
							break;
							
						case "btnReg":
								mvno.ui.createForm("FORM3");
								PAGE.FORM3.setFormData();
								PAGE.FORM3.clearChanged();
								PAGE.FORM3.setItemValue("opCode", "CREATE");
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0083"}, PAGE.FORM3, "startPrice");
								mvno.ui.popupWindowById("FORM3", "핀생성", 900, 300, {
					                onClose: function(win) {
					                	if (! PAGE.FORM3.isChanged()) return true;
					                	mvno.ui.confirm("CCMN0005", function(){
					                		win.closeForce();
					                	});
					                }
				                });	
							break;
							
						

					}
				}
			},

			//---------------------------------------

			FORM3 : {

				title : "PIN생성",
				buttons : {
					center : {
						btnPinCreateReg : { label : "등록" }, 
						btnPinCreateCancel : { label : "취소" }
					}
				},
				items : [
					{type: "hidden", name: "pinNum", value: ""},
					{type: "hidden", name: "opCode", value: ""},
					{type:"block", width:700,height:15, list:[
						{type:"settings", position:"label-right", labelWidth:"120"}                     					
							,{type:"label", label:"PIN번호길이"}
							,{type:"newcolumn", offset:30}
							,{type:"select", label:"", name: "pinLength", width:140, userdata: { lov: "PPS0040"}, validate: "NotEmpty", required: true} 	// Pin 번호 길이
							,{type:"newcolumn", offset:90}
							,{type:"label", label:"음성/데이타구분"}
							,{type:"newcolumn", offset:30}
							,{type:"select", label:"", name: "pinGubun", width:140, userdata: { lov: "PPS0041"}, validate: "NotEmpty", required: true} // 음성/데이타구분
					]},
					{type:"block", width:700,height:15, list:[
   						{type:"settings", position:"label-right", labelWidth:"120"}                     					
   							,{type:"label", label:"구분"}
   							,{type:"newcolumn", offset:30}
   							,{type:"block", list:[
								{type:"settings", position:"label-right", labelWidth:"auto"}
								,{type: "radio", name: "onoffGubun", value: "F", label: "오프라인", checked: true}
								,{type:"newcolumn", offset:7}
								,{type: "radio", name: "onoffGubun", value: "O", label: "온라인"}
							]} //구분
   							,{type:"newcolumn", offset:92}
   							,{type:"label", label:"발행금액"}
   							,{type:"newcolumn", offset:30}
   							,{type:"select", label:"",name:"startPrice", width:140 ,validate: "NotEmpty", required: true} // 발행금액
   					]},
   					{type:"block", width:700,height:15, list:[
   						{type:"settings", position:"label-right", labelWidth:"120"}                     					
   							,{type:"label", label:"생성갯수"}
   							,{type:"newcolumn", offset:30}
   							,{type:"block", list:[
								{type:"settings", position:"label-right", labelWidth:"0", labelHeight:"15"}					
								,{type:"input", label:"생성갯수", name: "crCount", width:140 ,validate: "NotEmpty,ValidInteger", maxLength:10}	// 생성갯수
							]}
   					]},
					{type:"block", width:800,height:15, list:[
   						{type:"settings", position:"label-right", labelWidth:"120"}                     					
   							,{type:"label", label:"메모"}
   							,{type:"newcolumn", offset:30}
   							,{type:"input", label:"", name: "remark", width:521,maxLength:100}	// 메모
   					]}
					
								
					],
				 onValidateMore : function (data){
					 
					/* if(data.crCount==null || data.crCount=="")
					{
						this.pushError("crCount","생성갯수","생성갯수는 필수입력입니다.");	
						 
					} */

				 }
				,onButtonClick : function(btnId, selectedData) {

					var form3 = this;
					switch (btnId) {
						case "btnPinCreateReg":
							if(this.getItemValue("crCount") > 30000){
								mvno.ui.notify("한번에 최대 30,000개까지 생성가능합니다.");
								return;
							}
							
							mvno.cmn.ajax(
									ROOT + "/pps/hdofc/cardmgmt/PpsPinInfoCreate.json",form3,
									function(results) {
										mvno.ui.notify(results.result.retMsg);
										if(results.result.retCode == "0000"){
											mvno.ui.closeWindowById(PAGE.FORM3);
											PAGE.GRID1.refresh();	
										}
									},
									null
									);

							
							break;

						case "btnPinCreateCancel":							
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
				//setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
				setCurrentDate(PAGE.FORM1, "startDt");
				setCurrentDate(PAGE.FORM1, "endDt");

				var tblName ="CREATE";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsMngCodeHeaderList.json",{tblNm :tblName }, PAGE.FORM1, "searchManageCodeHeader");
				
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0017"}, PAGE.FORM1, "searchCancelFlag");

				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("startMngCode" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("endMngCode" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("linenum" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("crSeq" ) ,true);					
				
			}

		};
		
		
	</script>
