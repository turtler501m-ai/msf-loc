<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_rcgmgmt_0050.jsp
   * @Description : 본사  충전관리 POS충전내역
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
							 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "부여일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : "", readonly:true},
							 {type : "newcolumn", offset:10},
							 {type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:13, labelHeight:15, blockOffset:0}	
								,{type: "label", label: "~"},
		                   	 ]},	 
		                     {type : "newcolumn"}, 
							 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : "", readonly:true},
		                     {type : "newcolumn", offset:10},
							 {type : "select",label:"요청구분",name : "searchReqType",width:100},
							 {type : "newcolumn", offset:10},
							 {type : "select",label:"취소여부",name : "searchCancelFlag",width:100}
						 ]},// 1row
						 {type:"block", list:[
							 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
							 {type:"block", list:[
							     					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, labelHeight:15, blockOffset:0},
							 					    {type : "select",label:"검색",name : "searchType",width:100}
							 					   ]},	
		                   	 {type : "newcolumn", offset:3},
		                     {type:"block", list:[
							     					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:0, labelHeight:15, blockOffset:0},
		                     						{type : "input",name : "searchNm", width:120, maxLength:50, validate:"ValidInteger", label:"검색"}
		                     						]}
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
							
							PAGE.GRID1.list(ROOT + "/pps/hdofc/rcgmgmt/RcgPosRechargeMgmtList.json", form, {
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
				header : "계약번호,CTN,요청구분,판매점명,승인번호,응답코드 ,충전금액,등록일자,취소여부,취소일자,일련번호",
				columnIds : "contractNum,subscriberNo,reqTypeNm,storeCodeNm,authCode,retCodeNm,amount,recordDate,cancelFlag,cancelDate,linenum",
				widths : "100,120,100,150,180,100,120,120,100,80,0", //총 1500
				colAlign : "center,center,center,left,left,center,right,center,center,center,center",
				colTypes : "link,roXp,ro,ro,ro,ro,ron,roXd,ro,roXd,ro",
				colSorting : "str,str,str,str,str,str,int,str,str,str,str",
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
							var url = ROOT +"/pps/hdofc/rcgmgmt/RcgPosRechargeMgmtListExcel.json";
							  var searchData =  PAGE.FORM1.getFormData(true);
							  console.log("searchData",searchData);
								
							  mvno.cmn.download(url+"?menuId=PPS1200005",true,{postData:searchData});
							break;
					}
				}
			}
			//---------------------------------------
			
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

				var lovCode="PPS_0142";
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode  } , PAGE.FORM1, "searchType");

					var lovCode2="PPS0061";
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode2  } , PAGE.FORM1, "searchReqType");
					
					mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0017"  } , PAGE.FORM1, "searchCancelFlag");

				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("linenum" ) ,true);

			}

		};
		
		
	</script>

	