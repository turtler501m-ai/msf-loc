<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  /**
  * @Class Name : msp_stat_bs_3001.jsp
  * @Description : 청구수납자료조회
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
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
// 	var dt = new Date();
// 	var fstDt = new Date(dt.getFullYear(), dt.getMonth(), 1);
// 	var prvMnth = new Date(fstDt.setDate(fstDt.getDate() - 1)).format("yyyymm");
	var thsMnth = new Date().format("yyyymm");
	var vWeek = new Date().getDay();
	var vTime = new Date().format("hh");
	
	var DHX = {
			//------------------------------------------------------------
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					, {type:"block", list:[
						{type:"calendar", label:"기준월", name:"trgtYm", value:thsMnth, width:80, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:10, required:true}
						, {type:"newcolumn"}
						, {type:"calendar", label:"조회기간", name:"strtYm", width:80, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:60}
						, {type:"newcolumn"}
						, {type:"calendar", label:"~", name:"endYm", width:80, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:5, labelWidth:10}
						, {type:"newcolumn"}
// 						, {type:"select", label:"선후불", name:"pppo", userdata:{lov:"RCP0002"}, offsetLeft:30}
						, {type:"select", label:"선후불", name:"pppo", offsetLeft:30}
					]},
					, {type:"block", list:[
						{type:"input", label:"계약번호", name:"contractNum", width:100, offsetLeft:10}
						, {type:"newcolumn"}
						, {type:"checkbox", label:"미납여부", name:"unpdYn", offsetLeft:40}
					]}
					, {type : 'hidden', name: "DWNLD_RSN", value:""} //엑셀다운로드 사유
					//조회 버튼
					, {type:"newcolumn"}
					, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"}/* btn-search(열갯수1~4) */
				]
// 				,onChange : function(name, value) {
// 					var form = this;
					
// 					switch(name){
// 						case "orgnId" :
// 							if(value == null || value == "") {
// 								form.setItemValue("orgnId", "");
// 								form.setItemValue("orgnNm", "");
// 							}
// 							break;
// 						case "searchType" :
// 							form.setItemValue("searchVal", "");
// 							break;
// 					}
// 				}
				, onValidateMore : function(data){
					if(data.strtYm == "") this.pushError("data.strtYm", "조회기간", "시작월을 선택하세요");
					if(data.endYm == "") this.pushError("data.endYm", "조회기간", "종료월을 선택하세요");
					if(data.strtYm > data.endYm) this.pushError("data.strtYm", "조회기간", "시작월이 종료월보다 클 수 없습니다");
				}
				, onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							if (! this.validate())  return;
							var url = ROOT + "/rsk/statMgmt/getInvPymDataList.json";
							PAGE.GRID1.list(url, form);
							break;
// 						case "btnOrgFind":
// 							mvno.ui.codefinder("ORG", function(result) {
// 								form.setItemValue("orgnId", result.orgnId);
// 								form.setItemValue("orgnNm", result.orgnNm);
// 							});
// 							break;
					}
				}
			},
			//-------------------------------------------------------------
			GRID1 : {
				css : {
					height : "560px"
				}
				, title : "조회결과"
				, header : "계약번호,개통일자,계약상태,선후불,약정개월,요금제,청구월,청구금액,수납금액,미납금액,미납율,미납여부"
				, columnIds : "contractNum,openDt,subStatusNm,pppo,enggMnthCnt,rateNm,billYm,invAmnt,pymAmnt,unpdAmnt,unpdRatio,unpdYn"
				, widths : "100,100,80,80,80,150,150,100,100,100,80,80"
				, colAlign : "center,center,center,center,right,left,center,right,right,right,right,center"
				, colTypes : "ro,roXd,ro,ro,ro,ro,ro,ron,ron,ron,ron,ro"
				, colSorting : "str,str,str,str,str,str,str,int,int,int,str,str"
				, paging : true
				, pageSize : 50
				, onRowDblClicked : function(rowId, celInd) {
					//this.callEvent('onButtonClick', ['btnDtl']);
				}
				, buttons : {
					hright : {
						btnExcel : { label : "자료생성" }
					}
				}
				, onButtonClick : function(btnId){
					var form = this;
					switch(btnId){
						case "btnExcel" :
// 							if(PAGE.GRID1.getRowsNum() == 0){
// 								mvno.ui.alert("데이터가 존재하지 않습니다");
// 								return;
// 							}
							
// 							mvno.ui.alert("기준월 및 조회기간을 이용하여 자료를<br>생성합니다.<br>자료생성후 SMS 가 발송됩니다.")
							
							// 현재 업무시간중인지 확인
// 							if((vTime >= 8 && vTime <= 21) && (vWeek > 0 && vWeek < 6)){
//								mvno.ui.alert("주말 외 업무시간(08~21시) 중에는 자료생성이 제한됩니다.");
//								return;
//							} 
							
							var vTrgtYm = new Date(PAGE.FORM1.getItemValue("trgtYm")).format("yyyymm");
							var vStrtYm = new Date(PAGE.FORM1.getItemValue("strtYm")).format("yyyymm");
							var vEndYm = new Date(PAGE.FORM1.getItemValue("endYm")).format("yyyymm");
							
							if(vTrgtYm == null || vTrgtYm == "" || vStrtYm == null || vStrtYm == " " || vEndYm == null || vEndYm == " "){
								mvno.ui.alert("기준월/조회기간을 입력하세요");
								return;
							}
							
//							var searchData =  PAGE.FORM1.getFormData(true);
// 							var searchData =  { trgtYm : vTrgtYm, strtYm : vStrtYm, endYm : vEndYm }
							
/* 							searchData.userId = "${info.sessionUserId}";
							var url = ROOT + "/cmn/btchmgmt/saveBatchRequest.json";
							
							var params = {
									 "batchJobId" : "BATCH00035"
									,"execParam" : JSON.stringify(searchData)
							};
							
							mvno.cmn.ajax(url, params, function(result) {
								console.log("result=" + result);
								mvno.ui.alert("기준월 및 조회기간을 이용하여 자료를<br>생성합니다.<br>자료생성후 SMS 가 발송됩니다.");
							}); */

                            mvno.cmn.downloadCallback(function(result) {
                                PAGE.FORM1.setItemValue("DWNLD_RSN",result);
                                mvno.cmn.ajax(ROOT + "/rsk/statMgmt/setInvPymDataListDownload.json?menuId=MSP5004001", PAGE.FORM1.getFormData(true), function(result){
                                    console.log("result=" + result);
                                    mvno.ui.alert("기준월 및 조회기간을 이용하여 자료를<br>생성합니다.<br>자료생성후 SMS 가 발송됩니다.");
                                });
                            });
							
							/* mvno.cmn.ajax(ROOT + "/rsk/statMgmt/setInvPymDataList.json", searchData, function(result){
								console.log("result=" + result);
								mvno.ui.alert("기준월 및 조회기간을 이용하여 자료를<br>생성합니다.<br>자료생성후 SMS 가 발송됩니다.");
							}); */
							
							break;
					}
				}
			}
	};
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		buttonAuth:${buttonAuth.jsonString},
		onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0032",etc1:"1"}, PAGE.FORM1, "pppo"); // 선후불
			
// 			mvno.cmn.ajax4lov(ROOT+"/sale/rateMgmt/getRateComboList.json", "", PAGE.FORM1, "rateCd");
		}
	};
	
</script>