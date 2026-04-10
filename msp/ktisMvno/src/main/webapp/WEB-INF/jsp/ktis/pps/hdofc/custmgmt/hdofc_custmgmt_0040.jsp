<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_custmgmt_0040.jsp
   * @Description : 선불 본사   해지자관리
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

	var DHX = {

		FORM1 : {

			items : [ 
						 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0}
						 ,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "해지일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""} 
						 ,{type : "newcolumn", offset:3}
							,{type:"block", list:[
													{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
													,{type: "label", label: "~"},
							                   	 ]}
							,{type : "newcolumn", offset:3}
						 ,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""} 
						 ,{type : "newcolumn", offset:30}
						 ,{type:"block", list:[
											{type : "input",label : "대리점 ",name : "searchAgentNm", width:100, labelWidth:40,maxlength:20}
											,{type : "newcolumn"}, 
											,{type : "select",name : "searchAgentId", width:100} 
						   					]}
	   					,{type : "newcolumn", offset:30}
						 ,{type:"block", list:[
											{type : "select",label : "검색 ",name : "searchType",width:100, labelWidth:30}
											,{type : "newcolumn", offset:3}
											,{type: "input", label: ":", labelWidth: 5, labelAlign: "left", name: "searchNm", width:100,maxlength:20}
												 ]}
						 ,{type:"newcolumn", offset:30}
						 ,{type : 'hidden', name: "btnCount1", value:"0"} //버튼 여러번 클릭 막기 변수 by han
						 ,{type : 'hidden', name: "btnExcelCount1", value:"0"} //버튼 여러번 클릭 막기 변수 by han
						,{type : "button",name : "btnSearchPps",value : "조회",className:"btn-search1"} 
			]
			,onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnSearchPps":
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/custmgmt/TmntCustMgmt.json", form, {
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
					 this.pushError("startDt", "해지일자", "해지일자을 입력해주세요.");
				}
				
				if(data.endDt == '')
				{
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("startDt", "해지일자", "해지일자을 입력해주세요.");
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

				 if(data.searchType=="contract_num" )
				 {
					  if(data.searchNm.match(/^\d+$/ig) == null){
						  PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						  PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						  this.pushError("searchNm","계약번호","숫자만 입력하세요");
					   }
							
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
			}
		}
		,GRID1 : {

			css : {
				height : "550px"
			},
			title : "조회결과"
			,header : "계약번호,CTN,요금제,사용자명,유심일련번호,상태,해지사유,국적,잔액,개통대리점,개통일자,정지일자,만료일자,해지일자,서식지,음성(분),문자(건),데이터(M),총충전금액"
			,columnIds : "contractNum,subscriberNo,serviceNm,subLinkName,iccId,subStatusNm,cancelRsnCodeNm,adNationNm,basicRemains,agentNm,lstComActvDate,statusStopDt,basicExpire,statusCancelDt,paperImage,usLcVoice,usLcSms,usLcData,totalBasicChg"
			,widths :  "80,120,120,150,140,80,120,120,80,150,100,100,100,120,80,80,80,80,80"
			,colAlign : "center,center,center,left,center,center,left,left,right,left,center,center,center,center,center,right,right,right,right"
			,colTypes : "link,roXp,ro,ro,ro,ro,ro,ro,ron,ro,roXd,roXd,roXd,roXd,ron,ron,ron,ro,ron"
			,colSorting : "str,str,str,str,str,str,str,str,int,str,str,str,str,str,int,int,int,str,int"
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
						
						var url = "/pps/hdofc/custmgmt/TmntCustMgmtExcel.json";
						var searchData =  PAGE.FORM1.getFormData(true);

						console.log("searchData",searchData);
						
					  	mvno.cmn.download(url+"?menuId=PPS1100004",true,{postData:searchData});
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
			setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "endDt");

			var selType ="popPin";
			mvno.cmn.ajax4lov(
					ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
					, {
						selectType : selType
						}
					 , PAGE.FORM1, "searchAgentId");

			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0114"}, PAGE.FORM1, "searchType");

		}

	};
</script>