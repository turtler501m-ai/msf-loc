<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : agency_rcgmgmt_0010.jsp
   * @Description : 대리점  충전내역
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




		var DHX = {

			// ----------------------------------------
			FORM1 : {

				title : "",
				items : [ 	
							 {type:"block", list:[
								 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
								 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "개통일자",value : "",enableTime : false,calendarPosition:"bottom",inputWidth : 100,value : ""},
								 {type : "newcolumn", offset:3},
								 {type:"block", list:[
									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
									,{type: "label", label: "~"},
			                   	 ]},	 
			                     {type : "newcolumn", offset:3}, 
								 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition:"right",inputWidth : 100,value : ""},
								 {type : "newcolumn"}, 
								 {type : "select", label : "충전구분", name : "searchChgType",width:100}, 
								 {type : "newcolumn"} 
								 
							 ]},// 1row
							 {type:"block", list:[
								 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
								 {type : "select",label:"검색",name : "searchType",width:100, width:100},	
				                   	{type : "newcolumn", offset:3},
				                   	{type:"block", list:[
	   									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
	   									,{type: "label", label: ":"}
	   			                   	 ]},
	   			                  {type : "newcolumn", offset:3},
	 			                   	{type : "input",name : "searchNm", width:100,maxLenth:20},
	 			                   {type : "newcolumn"},
	 			                  {type : "select", label : "충전결과", name : "searchKtResCode",width:100},
	 			                 {type : "newcolumn"},
	 			                  {type : "select", label : "취소여부", name : "searchCancelFlag",width:100} 
								 
							 ]},// 2row
							 {type : "newcolumn", offset:10},
							 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
							 {type : "button",name : "btnSearch",value : "조회", className:"btn-search2"} 
						]
						,onValidateMore : function(data){
								 if(data.startDt > data.endDt){
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
					
								if(data.searchType=="subscriber_no")
								{
									if(data.searchNm.match(/^\d+$/ig) == null)
									{
										PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
										PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										this.pushError("searchNm","CTN","숫자만 입력하세요");
									}
								}

								if(data.searchType=="contract_num")
								{
									if(data.searchNm.match(/^\d+$/ig) == null)
									{
										PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
										PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										this.pushError("searchNm","계약번호","숫자만 입력하세요");
									}
								}

								if(data.searchType=="recharge_info")
								{
									if(data.searchNm.match(/^\d+$/ig) == null)
									{
										PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
										PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										this.pushError("searchNm","핀번호","숫자만 입력하세요");
									}
								}
								if(data.searchType=="vac_id")
								{
									if(data.searchNm.match(/^\d+$/ig) == null)
									{
										PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
										PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
										this.pushError("searchNm","가상계좌번호","숫자만 입력하세요");
									}
								}
							


						}
						,onButtonClick : function(btnId) {
		
							var form = this;
		
							switch (btnId) {
		
								case "btnSearch":
									var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
									PAGE.FORM1.setItemValue("btnCount1", btnCount2)
									if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
									
									PAGE.GRID1.list(ROOT + " /pps/agency/rcgmgmt/RcgInfoMgmtList.json", form, {
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
				header : "CTN, 충전요청, 충전구분, 충전정보, 결제방식, 충전금액, 실입금액, 충전결과, 결과메세지, 충전일자, 충전후잔액, 충전후만료일, 충전관리자, 취소여부, 취소일자",
				columnIds : "subscriberNo,reqSrc,chgTypeName,rechargeInfo,rechargeMethod,amount,inAmount,ktResCodeNm,remark,rechargeDate,basicRemains,basicExpire,adminId,cancelFlag,cancelDt",
				widths : "100,90,90,100,100,100,100,100,130,140,100,100,100,100,80", //총 1500
				colAlign : "center,center,center,center,center,right,right,center,left,center,right,center,left,center,center",
				colTypes : "ro,ro,ro,ro,ro,ron,ron,ro,ro,roXd,ron,roXd,ro,ro,roXd",
				colSorting : "str,str,str,str,str,int,int,str,str,str,int,str,str,str,str",
				paging :true,
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
							var url = "/pps/agency/rcgmgmt/RcgInfoMgmtListExcel.json";
							 var searchData =  PAGE.FORM1.getFormData(true);
	
							  console.log("searchData",searchData);
							
						  	  mvno.cmn.download(url+"?menuId=PPS2200001",true,{postData:searchData});
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

				var lovCode="PPS0013";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode  } , PAGE.FORM1, "searchChgType");
				var lovCode2="PPS0015";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode2  } , PAGE.FORM1, "searchType");
				var lovCode3="PPS0016";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode3  } , PAGE.FORM1, "searchKtResCode");
				var lovCode4="PPS0017";
				mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :lovCode4  } , PAGE.FORM1, "searchCancelFlag");

			}

		};
		
		
	</script>
