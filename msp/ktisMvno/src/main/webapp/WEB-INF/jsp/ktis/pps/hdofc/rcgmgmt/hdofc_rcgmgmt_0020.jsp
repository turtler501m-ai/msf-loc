<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_rcgmgmt_0020.jsp
   * @Description : 본사  충전관리  실시간자동출금내역
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
		//var today = new Date().format("yyyy-MM-dd");

		form.setItemValue(name, new Date());
		
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

	

		var DHX = {

			// ----------------------------------------
			FORM1 : {

				title : "",
				items : [ 	
						 {type:"block", list:[
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "출금일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
							 {type : "newcolumn", offset:3},
							 {type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
								,{type: "label", label: "~"},
		                   	 ]},	 
		                     {type : "newcolumn", offset:3}, 
							 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
							 {type : "newcolumn", offset:10},
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type : "select",label : "은행명",name : "searchRcgCmsBankNm",width:100},// KimWoong
							 {type : "newcolumn", offset:10}, 
							 {type : "select",label : "출금여부",name : "searchRcgCmsSuccessFlag",width:100}
						 ]},// 1row
						 {type:"block", list:[
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type : "select",label:"검색",name : "searchType",width:100},	
	 						 {type : "newcolumn", offset:3},
							 {type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
								,{type: "label", label: ":"}
                	 		 ]},	 
                  			 {type : "newcolumn", offset:3},
                  			 {type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0},
                				{type : "input",name : "searchNm", width:100, validate:"ValidInteger", maxLength:20, label:"검색"}
                	 		 ]},
                	 		{type : "newcolumn", offset:10}, 
							{type : "select",label : "충전결과",name : "searchRcgFlag",width:100}
                	 		 
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
								
								PAGE.GRID1.list(ROOT + "/pps/hdofc/rcgmgmt/RcgRealCmsMgmtList.json", form, {
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
				header : "계약번호,요청일자,구분 ,출금여부,은행코드,예금주명,출금요청금액,실제출금액,출금결과코드,출금수수료,충전결과,일련번호",
				columnIds : "contractNum,reqDate,cmsType,succFlagNm,bankCodeName,bankUserName,reqAmount,resAmount,resCodeName,chargeFee,rechargeResult,linenum",
				widths : "70,130,60,60,90,150,90,80,80,80,60,0", //총 1500
				colAlign : "center,center,center,center,left,left,right,right,center,right,center,center",
				colTypes : "link,ro,ro,ro,ro,ro,ron,ron,ro,ron,ro,ro",
				colSorting : "str,str,str,str,str,str,int,int,str,int,str,int",
				paging : true,
				pageSize :15,
				pagingStyle :2,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }, 
					
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
							var url = "/pps/hdofc/rcgmgmt/RcgRealCmsMgmtListExcel.json";
							  var searchData =  PAGE.FORM1.getFormData(true);
							  console.log("searchData",searchData);
								
							  mvno.cmn.download(url+"?menuId=PPS1200002",true,{postData:searchData});
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
				
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsRcgCodeList.json", null, PAGE.FORM1, "searchRcgCmsBankNm");

				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("linenum" ) ,true);

				var lovCode="PPS0018";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode  } , PAGE.FORM1, "searchType");
				var lovCode2="PPS0019";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode2  } , PAGE.FORM1, "searchRcgCmsSuccessFlag");
				var lovCode2="PPS0019";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode2  } , PAGE.FORM1, "searchRcgFlag");
			}

		};
		
		
	</script>
