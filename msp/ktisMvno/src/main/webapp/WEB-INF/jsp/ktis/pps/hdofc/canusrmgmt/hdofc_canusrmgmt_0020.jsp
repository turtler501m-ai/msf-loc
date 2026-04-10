<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_canusrmgmt_0020.jsp
   * @Description : 본사 직권해지 직권해지처리
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
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "요청일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 {type : "select",label : "처리상태",name : "searchStatus",width:100},
						 {type : "newcolumn"},
						 {type : "select",label : "요청구분",name : "searchReqGubun",width:100}
					 ]},
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "newcolumn"},
						 {type : "select",label:"검색",name : "searchType",width:100, width:100},	
	                   	 {type : "newcolumn", offset:3},
	                   	 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: ":"}
	                   	 ]},	 
	                     {type : "newcolumn", offset:3},
	                   	 {type : "input",name : "searchNm", width:100,maxlength:20}
					 ]},
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search2"} 
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
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/canusrmgmt/CanUsrQueueMgmtList.json", form, {
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
					height : "540px"
				}
				,title : "조회결과"
				,header : "계약번호,CTN,고객명,요금제,요청구분,처리상태,최근충전일,잔액소진일,상태,개통일,정지일,해지일,잔액,만료일,엠만료일,대리점,판매점,결과메세지,요청일자,요청자,처리일자,처리자,사유,seq"
				,columnIds : "contractNumStr,subscriberNo,subLinkName,serviceNm,reqGubunNm,statusNm,lastBasicChgDt,basicEmptDt,subStatusNm,openDate,statusStopDt,statusCancelDt,basicRemains,basicExpire,mvnoBasicExpire,agentNm,agentSaleNm,retMsg,reqDt,reqNm,confirmDt,confirmNm,remark,seq"
				,widths   : "120,90,120,90,80,80,80,70,70,70,70,70,90,70,70,120,100,300,80,100,80,100,300,0" 
				,colAlign : "center,center,left,center,center,center,center,center,center,center,center,center,right,center,center,center,center,left,center,center,center,center,left,center"
				,colTypes : "link,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
				,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,int,str,str,str,str,str,str,str,str,str,str,str"
				,paging : true
				,pageSize :500
				,pagingStyle :2
				,buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }, 
					},
					right :{
						btnReg :{label :"해지처리"}
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
												
							var url = "/pps/hdofc/canusrmgmt/PpsCanUsrQueueExcel.json";
							 var searchData =  PAGE.FORM1.getFormData(true);
	
						  	  mvno.cmn.download(url+"?menuId=PPS1800002",true,{postData:searchData});
						
							break;
							
						case "btnReg":
							
							if(PAGE.GRID1.getRowsNum() == 0){
								mvno.ui.notify("내역이 존재하지 않습니다.");
								return;
							}
							
							mvno.ui.createGrid("GRID2");
							
							PAGE.GRID2.clearAll();
							var rowIds = PAGE.GRID1.getAllRowIds();
							rowIds = rowIds.split(",");
							
							var row_cnt = 0;
							
							for(var i=0; i<rowIds.length; i++){
								var sdata = PAGE.GRID1.classifyRowsFromIds(rowIds[i]);
								if(sdata.ALL[0].status == 'N'){
									PAGE.GRID2.appendRow(sdata.ALL[0]);
									row_cnt = row_cnt + 1;
								}
							}
							
							mvno.ui.popupWindowById("GRID2", "해지처리", 950, 750, {
								onClose: function(win) {
									win.closeForce();
								}
							});
							
							break;
		
					}
				}
		},
		GRID2 : {
		   	   css : {
					height : "540px"
				},
				title : "해지처리",
				header : "계약번호,CTN,고객명,요금제,최근충전일,잔액소진일,상태,개통일,정지일,해지일,잔액,만료일,대리점,판매점,요청구분,처리결과,결과메세지,요청일자,요청자,처리일자,처리자,사유,seq", 
				columnIds : "contractNumStr,subscriberNo,subLinkName,serviceNm,lastBasicChgDt,basicEmptDt,subStatusNm,openDate,statusStopDt,statusCancelDt,basicRemains,basicExpire,agentNm,agentSaleNm,reqGubunNm,statusNm,retMsg,reqDt,reqNm,confirmDt,confirmNm,remark,seq", 
				widths   : "120,90,120,90,80,70,70,70,70,70,90,70,120,100,80,80,300,80,100,80,100,300,0" , 
				colAlign : "center,center,left,center,center,center,center,center,center,center,right,center,center,center,center,center,left,center,center,center,center,left,center", 
				colTypes : "link,ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro", 
				colSorting : "str,str,str,str,str,str,str,str,str,str,int,str,str,str,str,str,str,str,str,str,str,str,str", 
				paging : false,
				pageSize:500,
				multiCheckbox : true,
				buttons : {
		               right : {
		                    btnDel : { label : "삭제" },
		                    btnMod : { label : "해지처리" },
		                    btnClose : { label : "닫기" }
		               }
		          },
				
		  			onButtonClick : function(btnId, selectedData) {
		 				var form = this;
		 				switch (btnId) {
							case "btnClose":			// 닫기
								mvno.ui.closeWindowById("GRID2", true);
								break;	
								
							case "btnDel":			// 삭제
								var msg = "체크된 내역을 삭제 하시겠습니까?";
								var rowIds = this.getCheckedRows(0);
								
								if (! rowIds) {
								    mvno.ui.alert("ECMN0003");
								    return;
								}
							
								var checkedData = this.classifyRowsFromIds(rowIds, "seq");
								mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/canusrmgmt/PpsCanUsrQueue.json", {
									data: checkedData.ALL,
									opCode : "DEL",
									del_cnt : checkedData.ALL.length
								}, function(results) {
									if(results.result.oRetCd == '0000'){
										mvno.ui.alert(results.result.oRetMsg);
										PAGE.GRID1.refresh();
										PAGE.GRID2.deleteRowByIds(rowIds);
									}else{
										mvno.ui.alert(results.result.oRetMsg);
									}
								},{timeout:60000});
								break;
								
							case "btnMod":			// 삭제
								var msg = "현재 내역에 있는 고객을 직권해지 처리 하시겠습니까?";
							
								var rowId_arr = "";
								
								if(this.getRowsNum() == 0){
									mvno.ui.alert("직권해지할 내역이 없습니다.");
								    return;
								}
								
								for(var i=0; i<this.getRowsNum(); i++){
									rowId_arr = rowId_arr + PAGE.GRID2.getRowId(i);
									if(i+1 != this.getRowsNum()){
										rowId_arr = rowId_arr + ",";
									}
								}
								
								var checkedData = this.classifyRowsFromIds(rowId_arr, "seq");
								var confirm_cnt = this.getRowsNum();
								
								mvno.ui.prompt("직권해지사유", function(result){
									var msg = "현재 내역에 있는 고객을 직권해지 처리 하시겠습니까?";
									
									
									mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/canusrmgmt/PpsCanUsrQueue.json", {
										data: checkedData.ALL,
										opCode : "CONFIRM",
										remark : result,
										confirm_cnt : confirm_cnt
									}, function(results) {
										if(results.result.oRetCd == '0000'){
											mvno.ui.alert(results.result.oRetMsg);
											PAGE.GRID1.refresh();
											mvno.ui.closeWindowById("GRID2", true);
										}else{
											mvno.ui.alert(results.result.oRetMsg);
										}
									},{timeout:60000});
									
								} , { required:true,lines:1,maxLength : 80 });
							
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
			
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0087"}, PAGE.FORM1, "searchStatus");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0086"  } , PAGE.FORM1, "searchType");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0088"  } , PAGE.FORM1, "searchReqGubun");
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("seq" ) ,true);
		}

	};
		
		
</script>