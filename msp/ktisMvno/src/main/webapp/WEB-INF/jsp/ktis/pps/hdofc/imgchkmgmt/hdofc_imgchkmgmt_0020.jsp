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
	                     {type : "checkbox", label : "대리점별", width : 50, labelWidth:60,name:"agentGrpChk"}
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "input",label : "대리점 ",name : "searchAgentNm", width:100,maxlength:20,labelWidth : 80},
		                 {type : "newcolumn", offset:11},
		                 {type : "select",name : "searchAgentId",width:100},
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
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search2"},
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
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/imgchkmgmt/CustImgChkGrpMgmtList.json", form, {
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
					
			}
		}
		,GRID1 : {
	
			css : {
				height : "550px"
			}
			,title : "조회결과"
			,header : "검수등록번호,등록건수,검수요청,보완요청,재검수요청,검수불가,검수불필요,검수완료,기타,개통일,대리점,등록일,등록관리자"
			,columnIds : "eventSeq,totCnt,status10,status20,status21,status90,status91,status00,status99,eventDt,agentNm,regDt,regAdminNm"
			,widths : "100,70,70,70,70,70,70,70,70,80,120,80,120"
			,colAlign : "center,right,right,right,right,right,right,right,right,center,center,center,center"
			,colTypes : "ro,ron,ron,ron,ron,ron,ron,ron,ron,roXp,ro,roXp,ro"
			,colSorting : "str,int,int,int,int,int,int,int,int,str,str,str,str"
			,paging : true
			,pageSize :15
			,pagingStyle :2
			,buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }, 
				
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
						var url = "/pps/hdofc/imgchkmgmt/CustImgChkGrpMgmtListExcel.json";
						var searchData =  PAGE.FORM1.getFormData(true);
	
					  	mvno.cmn.download(url+"?menuId=PPS1940002",true,{postData:searchData});
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
			
			var selType ="popPin";
			mvno.cmn.ajax4lov(
					ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
					, {
						selectType : selType
						}
					 , PAGE.FORM1, "searchAgentId");
			
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0135"}, PAGE.FORM1, "searchType");
		}
	};
		
</script>