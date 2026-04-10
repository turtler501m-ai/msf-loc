<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_canusrmgmt_0010.jsp
   * @Description : 본사 직권해지 해지대상자조회
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

<div id="FORM20" class="section-search" ></div>	
	
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
	
	function setYesterDate(form, name)
	{
		var date = new Date();
		date.setDate(date.getDate()-1);
		form.setItemValue(name, date);
	}
	
	<%@ include file="../../hdofcCustDetail.formitems" %>

	var DHX = {
	
		FORM1 : {
			items : [ 
				 {type:"block", list:[
					 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
					 {type : "newcolumn"}, 
					 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "개통일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
					 {type : "newcolumn", offset:3},
					 {type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
						,{type: "label", label: "~"},
                   	 ]},	 
                     {type : "newcolumn", offset:3}, 
					 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
					 {type : "newcolumn", offset:3}, 
					 {type : "input",label:"만료일",name : "searchExpireCnt", validate:"ValidInteger", maxLength:3,width:100},
					 {type : "newcolumn", offset:3}, 
					 {type : "input",label:"엠만료일",name : "searchMvnoExpireCnt", validate:"ValidInteger", maxLength:3,width:100},
					 {type : "newcolumn"},
					 {type : "select",label : "요금제",name : "searchSoc",width:100}
				 ]},
				 {type:"block", list:[
					 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
					 {type : "newcolumn"},
					 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "basicExpireStartDt",label : "만료일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
					 {type : "newcolumn", offset:3},
					 {type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
						,{type: "label", label: "~"},
                   	 ]},	 
                     {type : "newcolumn", offset:3}, 
					 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "basicExpireEndDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
                   	 {type : "newcolumn", offset:3},
					 {type : "input",label:"잔액소진일",name : "searchEmptCnt", validate:"ValidInteger", maxLength:3,width:100},
					 {type : "newcolumn"},
					 {type : "select",label : "상태",name : "searchSubStatus",width:100},
					 {type : "newcolumn"},
					 {type : "select",label : "가입서류",name : "searchPaperImg",width:100}
				 ]},
				 {type:"block", list:[
					 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
					 {type : "newcolumn"},
					 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "mvnoBasicExpireStartDt",label : "엠만료일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
					 {type : "newcolumn", offset:3},
					 {type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
						,{type: "label", label: "~"},
                   	 ]},	 
                     {type : "newcolumn", offset:3}, 
					 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "mvnoBasicExpireEndDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
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
				 {type : "newcolumn", offset:10},
				 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
				 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
				 {type : "button",name : "btnSearch",value : "조회", className:"btn-search3"} 
			]
			,onValidateMore : function(data){

				if(data.startDt > data.endDt){
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
				}
				
				 if(data.searchNm!=""){
					 if(data.searchType==""){
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 	this.pushError("searchType", "검색조건", "검색조건을 선택하세요");
					 }
				}
				
				if(data.searchExpireCnt!=""){
					if(parseInt(data.searchExpireCnt) > 365){
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						this.pushError("searchExpireCnt", "만료일", "1 ~ 365사이의 값을 입력해 주세요.");
					}
				}
				
				if(data.searchMvnoExpireCnt!=""){
					if(parseInt(data.searchMvnoExpireCnt) > 365){
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						this.pushError("searchMvnoExpireCnt", "엠만료일", "1 ~ 365사이의 값을 입력해 주세요.");
					}
				}
				
				if(data.searchEmptCnt!=""){
					if(parseInt(data.searchEmptCnt) > 365){
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						this.pushError("searchEmptCnt", "잔액소진일", "1 ~ 365사이의 값을 입력해 주세요.");
					}
				}
				
				if(data.searchExpireCnt=="" && data.searchMvnoExpireCnt=="" && data.searchEmptCnt=="" && data.searchNm==""){
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					this.pushError("searchExpireCnt", "만료일,엠만료일,잔액소진일,검색", "한개 이상의 검색조건이 필요합니다.");
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
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/canusrmgmt/CanUsrMgmtList.json", form, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						
						break;
						
				}
	
			}
		}
		,GRID1 : {
				css : {
					height : "500px"
				}
				,title : "조회결과"
				,header : "계약번호,CTN,고객명,요금제,최근충전일,잔액소진일,상태,개통일,정지일,해지일,잔액,만료일,엠만료일,충전가능여부,대리점,판매점,서류"
				,columnIds : "contractNumStr,subscriberNo,subLinkName,serviceNm,lastBasicChgDt,basicEmptDtStr,subStatusNm,lstComActvDate,statusStopDt,statusCancelDt,basicRemains,basicExpire,mvnoBasicExpire,rechargeFlag,agentNm,agentSaleNm,paperImage"
				,widths   : "120,90,120,90,80,70,70,70,70,70,90,70,80,80,120,100,80" 
				,colAlign : "center,center,left,center,center,center,center,center,center,center,right,center,center,center,center,center,center"
				,colTypes : "link,ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ro,ro,ro,ro,ro,ron"
				,colSorting : "str,str,str,str,str,str,str,str,str,str,int,str,str,str,str,str,int"
				,paging : true
				,pageSize :500
				,pagingStyle :2
				,buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					},
					right :{
						btnReg :{label :"대상자등록"}
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
												
							var url = "/pps/hdofc/canusrmgmt/CanUsrMgmtExcel.json";
							 var searchData =  PAGE.FORM1.getFormData(true);
	
							  console.log("searchData",searchData);
							
						  	  mvno.cmn.download(url+"?menuId=PPS1800001",true,{postData:searchData});
						
							break;
							
						case "btnReg":
							var self = PAGE.GRID1;
							
							var rowIds = self.getCheckedRows(0);
	
							if (! rowIds) {
							    mvno.ui.alert("내역이 없습니다.");
							    return;
							}
	
							mvno.ui.prompt("요청사유", function(result){
								var msg = "현재 선택된 검색조건으로 요청등록됩니다.<br/>한 번 요청시 5000개씩 요청등록됩니다.<br/><br/>해지요청 등록을 하시겠습니까?";
								
								var searchData =  PAGE.FORM1.getFormData(true);
								searchData.remark = result;
								searchData.opCode = "REQ";
								
								mvno.cmn.ajax4confirm(msg, ROOT + "/pps/hdofc/canusrmgmt/PpsCanUsrQueue.json", searchData, function(results) {
									if(results.result.oRetCd == '0000'){
										mvno.ui.alert(results.result.oRetMsg);
										PAGE.GRID1.refresh();
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
			
			setYesterDate(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "endDt");
			
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsFeatureCodeList.json", null, PAGE.FORM1, "searchSoc");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0055"}, PAGE.FORM1, "searchSubStatus");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0084"  } , PAGE.FORM1, "searchType");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0001"}, PAGE.FORM1, "searchPaperImg");
			
			PAGE.FORM1.setItemValue("searchSubStatus", "S");
			
			//queue테이블 7일지난 내역 삭제처리
			mvno.cmn.ajax(ROOT + "/pps/hdofc/canusrmgmt/PpsCanUsrQueue.json",{opCode:"EXPIRE_DEL"}, function(result) {
				
			}, null);
			

		}

	};
		
		
</script>