<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



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

	var DHX = {
	
		FORM1 : {
			items : [ 
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "검수등록일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : "",labelWidth : 80, offsetLeft:0},
						 {type : "newcolumn", offset:3},
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
		                 {type : "select",label:"검색",name : "searchType",width:100, width:100},	
		                 {type : "newcolumn", offset:3},
	                   	 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: ":"}
	                   	 ]},	 
	                    {type : "newcolumn", offset:3},
	                   	{type : "input",name : "searchNm", width:100,maxlength:20}
					 ]},// 1row
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"},
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
						
						PAGE.GRID1.list(ROOT + "/pps/agency/imgchkmgmt/CustImgChkMgmtList.json", form, {
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
					 this.pushError("startDt", "등록일자", "등록일자을 입력해주세요.");
				}
				
				if(data.endDt == '')
				{
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("endDt", "등록일자", "등록일자을 입력해주세요.");
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
			,header : "검수등록번호,계약번호,개통번호,업무구분,개통일,검수등록일,검수현황,개통신청서,신분증,기타,개통서류,신분증,서명,기타,메모,seq"
			,columnIds : "eventSeq,contractNum,subscriberNo,mnpIndCdNm,lstComActvDate,regDt,chkStatusNm,imgOpenCnt,imgIdCnt,imgEtcCnt,dStr,iStr,sStr,eStr,agentMemo,seq"
			,widths : "100,100,100,80,80,80,80,70,70,70,80,80,80,80,300,0"
			,colAlign : "center,center,center,center,center,center,center,right,right,right,center,center,center,center,center,center"
			,colTypes : "ro,link,roXp,ro,ro,ro,ro,ron,ron,ron,ro,ro,ro,ro,ro,ro"
			,colSorting : "str,str,str,str,str,str,str,int,int,int,str,str,str,str,str,str"
			,paging : true
			,pageSize :15
			,pagingStyle :2
			,buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }, 
				
				},
				right : {
					btnReg : { label : "재검수요청" }
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
						var url = "/pps/agency/imgchkmgmt/CustImgChkMgmtListExcel.json";
						var searchData =  PAGE.FORM1.getFormData(true);
	
					  	mvno.cmn.download(url+"?menuId=PPS2100002",true,{postData:searchData});
						break;
						
					case "btnReg":
						
						var form = PAGE.GRID1.getSelectedRowData();
						
						if(form == null) {
	                     	mvno.ui.notify("선택된 데이터가 존재하지 않습니다");
	                     	return;
	                  	} 
						
						mvno.ui.confirm("재검수요청을 하시겠습니까?", function(){
							mvno.cmn.ajax(ROOT + "/pps/agency/imgchkmgmt/PpsCustImgChkRegProc.json", {seq : form.seq}, function(dataResult) {
								var ret = dataResult.result;
								
								alert(ret.oRetMsg);
								
								if(ret.oRetCd == "0000"){
									PAGE.GRID1.refresh();
								}
									
							});
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
			setCurrentDate(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "endDt");
			
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("seq" ) ,true);
			
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0136"}, PAGE.FORM1, "searchType");
		}
	};
		
</script>