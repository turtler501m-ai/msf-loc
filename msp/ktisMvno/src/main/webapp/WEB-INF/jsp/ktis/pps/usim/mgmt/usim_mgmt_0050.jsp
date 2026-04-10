<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : usim_mgmt_0050.jsp
   * @Description : 본사  유심모델관리
   * @Modification Information
   *
   *   수정일         수정자                   수정내용
   *  -------    --------    ---------------------------
   *  2016.03.25            최초 생성
   *
   * author 실행환경 개발팀
   * since  2016.03.25 
   *
   * Copyright (C) 2016 by MOPAS  All right reserved.
   */
%>


<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>  
<div id="FORM2" class="section-search" ></div>	

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


	<%@ include file="../../hdofcCustDetail.formitems" %>

	var DHX = {
	
		FORM1 : {
			items : [ 	
					 {type:"block", list:[
					                      
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "등록일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                   	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						
						 {type : "newcolumn", offset:40},
						 {type : "select",label:"검색",name : "searchType",width:100, userdata: { lov: "PPS_0082"}},	
		                 {type : "newcolumn", offset:3},
		                 {type:"block", list:[
	 									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
	 									,{type: "label", label: ":"}
	 			                   	 ]},	 
	 			         {type : "newcolumn", offset:2},
	 			         {type : "input",name : "searchNm", width:200}
						  
					 ]},// 1row
					 
				
					 
					 {type : "newcolumn", offset:40},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				]
				,onValidateMore : function(data){

					 if(data.startDt > data.endDt){
						 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 this.pushError("startDtCreate", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
					}
					 
		 			if(data.searchType=="USIM_MODEL")
					{
						if(data.searchNm.match(/^\d+$/ig) == null)
						{
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							this.pushError("searchNm","검색조건","숫자만 입력하세요");
						}
					}
					 
					 if(data.searchNm!=""){
						 if(data.searchType==""){
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						 	this.pushError("searchNumStatus", "검색조건", "검색조건을 선택하세요");
						 }
					}
	
				}
				,onKeyUp : function(inp, ev, name, value)
				{
					if(ev.keyCode == 13)
					{
						null;
					}
			}
			,onButtonClick : function(btnId) {
	
				var form = this;
				switch (btnId) {
	
					case "btnSearch":
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/usim/mgmt/UsimModelInfoMgmtList.json", form, {
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
					height : "550px"
				}
				,title : "조회결과"
				,header : "유심번호, 유심명, 등록자, 등록일자, 메모"
				,columnIds : "usimModel,usimModelName,recordAdminNm,recordDate,remark"
				,widths   : "80,150,150,150,420" 
				,colAlign : "center,center,center,center,left"
				,colTypes : "ro,ro,ro,ro,ro"
				,colSorting : "str,str,str,str,str"
				,paging : true
				,pageSize :15
				,pagingStyle :2
				,buttons : {
					right : {
						btnReg : { label : "등록" },
						btnDel : { label : "삭제" }
					}
				}
				
				,onButtonClick : function(btnId) {
	
					switch (btnId) {
						case "btnReg":
							mvno.ui.createForm("FORM2");
							PAGE.FORM2.setFormData();
							PAGE.FORM2.clearChanged();
							mvno.ui.popupWindowById("FORM2", "유심모델등록", 750, 300, {
				                onClose: function(win) {
				                	if (! PAGE.FORM2.isChanged()) return true;
				                	mvno.ui.confirm("CCMN0005", function(){
				                		win.closeForce();
				                	});
				                }
			                });	
						
							break;
						case "btnDel":
							var selectedData = PAGE.GRID1.getSelectedRowData();
							var opCode = "DEL";
							
							if(!selectedData){
								mvno.ui.alert("삭제할 유심모델을 선택하세요.");
								return;
							}
							
							mvno.cmn.ajax(
								ROOT + "/pps/usim/mgmt/PpsUsimModelInput.json",
								{
									opCode : opCode,
									usimModel : selectedData.usimModel
								},
								function(result) {
	
									var data1 = result.result;
									console.log("D",data1);	
									var retCode = data1.oRetCd;
									var retMsg = data1.oRetMsg;
									if(retCode=="0000")
									{
										mvno.ui.notify(data1.oRetMsg);
										PAGE.GRID1.refresh();
									}
								},
								null
							);
							break;
		
					}
				}
		}
		,

		//---------------------------------------

		FORM2 : {

			title : "유심모델등록",
			buttons : {
				center : {
					btnUsimModelReg : { label : "등록" }, 
					btnUsimModelCancel : { label : "취소" }
				}
			},
			items : [
				{type: 'hidden', name: 'opCode', value: ''},
				{type:"settings", position:"label-left", labelWidth:"120"},
				{type:"input", label:"유심모델", name: "usimModel", width:140 ,validate: "NotEmpty,ValidInteger", maxLength:5},
				{type:"input", label:"유심명", name: "usimModelName", width:140 ,validate: "NotEmpty"},
				{type:"input", label:"메모", name: "remark", width:500}
			],
			 onValidateMore : function (data){
				 
				/* if(data.crCount==null || data.crCount=="")
				{
					this.pushError("crCount","생성갯수","생성갯수는 필수입력입니다.");	
					 
				} */

			 }
			,onButtonClick : function(btnId, selectedData) {
				
				switch (btnId) {
					case "btnUsimModelReg":
						PAGE.FORM2.setItemValue("opCode", "REG");
						var form2 = this;
						mvno.cmn.ajax(
								ROOT + "/pps/usim/mgmt/PpsUsimModelInput.json",form2,
								function(result) {
									mvno.ui.notify(result.result.oRetMsg);
									if(result.result.oRetCd == "0000"){
										mvno.ui.closeWindowById(PAGE.FORM2);
										PAGE.GRID1.refresh();	
									}
								},
								null
								);
						break;

					case "btnUsimModelCancel":							
						mvno.ui.closeWindowById(this);
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
			
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0082"  } , PAGE.FORM1, "searchType");

		}

	};
		
		
</script>