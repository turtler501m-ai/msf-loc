<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_canusrmgmt_0030.jsp
   * @Description : 본사 직권해지 해지결과내역
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

<div id="GRID2" class="section-search" ></div>	
	
	<!-- Script -->
	<script type="text/javascript">

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
	
	function setCurrentDate(form, name)
	{
		form.setItemValue(name, new Date());
	}

	<%@ include file="../../hdofcCustDetail.formitems" %>

	var DHX = {
	
		FORM1 : {
			items : [ 
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "newcolumn"},
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "처리일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						 {type : "newcolumn"},
						 {type : "select",label : "요청구분",name : "searchReqGubun",width:100},
					 ]},
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				]
				,onValidateMore : function(data){

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
					
			}
			,onButtonClick : function(btnId) {
	
				var form = this;
				switch (btnId) {
	
					case "btnSearch":
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/canusrmgmt/CanUsrStatsMgmtList.json", form, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						/*
						PAGE.GRID2.list(ROOT + "/pps/hdofc/canusrmgmt/CanUsrQueueMgmtList.json", form, {
							callback : function () {
								
							}
						});
						*/
						
						break;
						
				}
	
			}
		}
		,GRID1 : {
				css : {
					height : "550px"
				}
				,title : "조회결과"
				,header : "그룹번호,요청건수,해지요청,예약취소,전송성공,전송실패,해지성공,해지실패,요청관리자,요청일자,메모"
				,columnIds : "groupSeq,confirmCnt,ingCnt,canCnt,s1Cnt,f1Cnt,s2Cnt,f2Cnt,confirmNm,confirmDt,remark"
				,widths   : "80,70,70,70,70,70,70,70,120,130,300" 
				,colAlign : "center,right,right,right,right,right,right,right,center,center,left"
				,colTypes : "ron,ron,ron,ron,ron,ron,ron,ron,ro,ro,ro"
				,colSorting : "int,int,int,int,int,int,int,int,str,str,str"
				,paging : true
				,pageSize :15
				,pagingStyle :2
				,buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }, 
					},
					right :{
						btnReg :{label :"해지예약취소"}
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
												
							var url = "/pps/hdofc/canusrmgmt/PpsCanUsrStatsExcel.json";
							 var searchData =  PAGE.FORM1.getFormData(true);
	
						  	  mvno.cmn.download(url+"?menuId=PPS1800003",true,{postData:searchData});
						
							break;
							
						case "btnReg":
							
							var msg = "선택된 내역을 해지예약취소 하시겠습니까?";
							
							rowData = PAGE.GRID1.getSelectedRowData();
							
							if(rowData == null){
								mvno.ui.notify("선택된 Data가 없습니다.");
								return;
						   }
							
							mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/canusrmgmt/PpsCanUsrQueue.json", {
								param: rowData.groupSeq,
								opCode : "CAN"
							}, function(results) {
								if(results.result.oRetCd == '0000'){
									mvno.ui.alert(results.result.oRetMsg);
									PAGE.GRID1.refresh();
								}else{
									mvno.ui.alert(results.result.oRetMsg);
								}
							},{timeout:60000});
							
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
			
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0088"  } , PAGE.FORM1, "searchReqGubun");
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("seq" ) ,true);
		}

	};
		
		
</script>