<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_rcgmgmt_0010.jsp
   * @Description : 본사  충전관리  충전내역
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
	<!-- 화면 배치 -->
	
	
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

	function goRcgCancel(seq){

		
		var url = ROOT + "/pps/hdofc/rcgmgmt/PpsRcgCancel.json";
		var opCode = "CANCEL";
		var reqType = "BS_CAN_VOICE";
		mvno.cmn.ajax4confirm("취소후에는 복구가 불가능합니다. <br/>충전취소 하시겠습니까?", url, {
			seq: seq,
			opCode: opCode,
			reqType: reqType
		}, function(results) {
			mvno.ui.alert(results.result.oResCodeNm);
			if(results.result.oResCode == "0000"){
				PAGE.GRID1.reflesh();
			}
		});
	}

	
	
		var DHX = {

			// ----------------------------------------
			FORM1 : {

				title : "",
				items : [ 	
							 {type:"block", list:[
								 {type:"settings", position:"label-left", labelAlign:"lfet", labelWidth:60, blockOffset:0},
								 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "충전일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
								 {type : "newcolumn", offset:3},
								 {type:"block", list:[
									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
									,{type: "label", label: "~"},
			                   	 ]},	 
			                     {type : "newcolumn", offset:3}, 
								 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
								 {type : "newcolumn", offset:10}, 
								 {type : "select",label : "충전요청",name : "searchReqSrc", width:100},
								 {type : "newcolumn", offset:10}, 
								 {type : "select",label :"충전구분",name : "searchChgType",width:100}, 
								 {type : "newcolumn", offset:10}, 
								 {type : "select",label : "결제방식",name : "searchRechargeMethod",width:100}, 
							 ]},// 1row
							 {type:"block", list:[
								 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
								 {type : "input",label:"충전대리점",name : "searchAgentStr",width:100, maxLength:10},	// KimWoong
				                 {type : "newcolumn", offset:3},
				                 {type:"block", list:[
										{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
										,{type: "label", label: ":"}
				                 ]},
				                 {type : "newcolumn", offset:3},
				                 {type : "select",name : "searchAgentId", width:100},
								 {type : "newcolumn", offset:10}, 
	   			             	 {type : "select",label : "충전결과",name : "searchKtResCode",width:100},
	   			             	 {type : "newcolumn", offset:10}, 
	   			             	 {type : "select",label : "요금제",name : "searchFeatures",width:100}
							 ]},// 2row
							 {type:"block", list:[
								{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
								{type : "select",label:"검색",name : "searchSItem",width:100}, 	
				                {type : "newcolumn", offset:3},
								{type:"block", list:[
  									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
  									,{type: "label", label: ":"}
  			                   	 ]},	 
  			                    {type : "newcolumn", offset:3},
  			                   	{type : "input",name : "searchSValue", width:100, maxLength:20},
  			                    {type : "newcolumn", offset:10},
								{type : "select",label : "취소여부",name : "searchCancelFlag",width:100}
								,{type : "newcolumn", offset:10}
					            ,{type : "select", label :"카드구분", name : "searchFreeFlag", width:100}
							 ]},// 3row
							 {type : "newcolumn", offset:10},
							 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : "button",name : "btnSearchPps",value : "조회", className:"btn-search3"} 
						],

				onButtonClick : function(btnId) {

					var form = this;

					switch (btnId) {

						case "btnSearchPps":
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							
							PAGE.GRID1.list(ROOT + " /pps/hdofc/rcgmgmt/RcgInfoMgmtList.json", form, {
								callback : function () {
									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								}
							});
							
							break;

					}

				},
				 onValidateMore : function(data){
					 if(data.startDt > data.endDt){
						 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
					}
				 },	
				onKeyUp : function(inp, ev, name, value)
				{
					if(ev.keyCode == 13)
					{
						switch(name)
						{
							case "searchAgentStr":
								var agentStr  = this.getItemValue("searchAgentStr");
								var selType ="popPin";
								//alert(agentStr);
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
										{ 
										  selectType : selType , 
									      searchAgentStr : agentStr
										} 
							      , PAGE.FORM1, "searchAgentId");
								var agentOption =  this.getOptions("searchAgentId");
						        
						           if(agentStr != ""){
						        	   if(agentOption.length==1)
								       {
							        	   //alert("ㄷ대리점이 존재하지 않습니다.");
							           }
							           else if(agentOption.length>=2)
								        {

									        var selValue = agentOption[1].value;

							        	   this.setItemValue("searchAgentId",selValue);
							           }      
											
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
			},

			// ----------------------------------------
			GRID1 : {

				css : {
					height : "540px"
				},
				title : "조회결과",
				header : "계약번호,충전요청,충전구분,충전정보,결제방식,충전금액,실입금액,충전결과,결과메세지,개통일자,충전일자,충전후잔액,충전후만료일,개통대리점,판매점,충전대리점,충전관리자,요금제,취소여부,취소일자,취소,카드구분,충전번호,일련번호",
				columnIds : "contractNum,reqSrc,chgTypeName,rechargeInfo,rechargeMethod,amount,inAmount,ktResCodeNm,remark,lstComActvDate,rechargeDate,basicRemains,basicExpire,openAgentNm,openAgentSaleNm,rechargeAgentName,adminNm,socNm,cancelFlag,cancelDt,cancelBtn,freeFlagNm,seq,linenum",
				widths : "70,60,60,90,60,60,60,60,120,80,130,70,80,80,80,70,70,120,60,80,60,60,0,0", //총 1500
				colAlign : "center,center,center,center,center,right,right,center,left,center,center,right,center,center,center,left,left,center,center,center,center,center,center,center",
				colTypes : "link,ro,ro,ro,ro,ron,ron,ro,ro,roXd,ro,ron,ro,ro,ro,ro,ro,ro,ro,ro,link,ro,ro,ro",
				colSorting : "str,str,str,str,str,int,int,str,str,str,str,int,str,str,str,str,str,str,str,str,str,str,str,str",
				paging : true,
				pageSize :15,
				pagingStyle :2,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
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
							var url = "/pps/hdofc/rcgmgmt/RcgInfoMgmtListExcel.json";

							 var searchData =  PAGE.FORM1.getFormData(true);

								 console.log("searchData",searchData);
								
							  mvno.cmn.download(url+"?menuId=PPS1200001",true,{postData:searchData});
							  
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
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("seq" ) ,true);
				PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("linenum" ) ,true);

				var selType ="popPin";
				mvno.cmn.ajax4lov(
						ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
						, {
							selectType : selType
							}
						 , PAGE.FORM1, "searchAgentId");
				//mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsFeatureCodeList.json", {selectType : selType}, PAGE.FORM1, "searchFeatures");
				var lovCode="PPS0012";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode  } , PAGE.FORM1, "searchReqSrc");
				var lovCode2="PPS0013";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode2  } , PAGE.FORM1, "searchChgType");
				var lovCode3="PPS0014";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode3  } , PAGE.FORM1, "searchRechargeMethod");
				var lovCode4="PPS0015";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode4  } , PAGE.FORM1, "searchSItem");
				var lovCode5="PPS0016";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode5  } , PAGE.FORM1, "searchKtResCode");
				var lovCode6="PPS0017";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode6  } , PAGE.FORM1, "searchCancelFlag");
				var lovCode7="PPS0062";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd : lovCode7 } , PAGE.FORM1, "searchFreeFlag");
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/custmgmt/PpsFeatureCodeList.json", null, PAGE.FORM1, "searchFeatures");
				

			}

		};
		
		
	</script>
