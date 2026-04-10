<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  /**
   * @Class Name : msp_rsk_bs_1001.jsp
   * @Description : 부실가입자관리
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

var DHX = {
	
	// ----------------------------------------
	FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", name:"strtDt", label:"조회기간", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", required:true, width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"calendar", name:"endDt", label:"~", labelWidth:20, labelAlign:"center", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", width:100}
//					, {type:"newcolumn"}
// 					, {type:"select", name:"serviceType", label:"선후불", userdata:{lov:"RCP0002"}, width:80, offsetLeft:20}
//					, {type:"select", name:"serviceType", label:"선후불", width:80, offsetLeft:20}
					, {type:"newcolumn"}
// 					, {type:"select", name:"searchType", label:"조회조건", userdata:{lov:"RSK0001"}, offsetLeft:20}
					, {type:"select", name:"searchType", label:"조회조건", offsetLeft:20}
				]}
				, {type:"block", list:[
					{type:"input", name:"orgnId", label:"조직ID", width:100, offsetLeft:10, maxLength:10}
					, {type:"newcolumn"}
					, {type:"button", value:"검색", name:"btnOrgFind"}
					, {type:"newcolumn"}
					, {type:"input", name:"orgnNm", width:150}
					, {type:"newcolumn"}
// 					, {type:"select", name:"searchCd", label:"검색구분", userdata:{lov:"RSK0004"}, offsetLeft:20}
					, {type:"select", name:"searchCd", label:"검색구분", offsetLeft:20}
					, {type:"newcolumn"}
					, {type:"input", name:"searchVal", width:140}
				]}
				//조회 버튼
				, {type:"newcolumn"}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"}/* btn-search(열갯수1~4) */
				, {type : 'hidden', name: "DWNLD_RSN", value:""} //엑셀다운로드 사유
			]
		 	// 조직 정보 초기화.
			, onChange : function(name, value) {
				var form = this;
				switch(name){
					case "orgnId" :
						form.setItemValue("orgnId", "");
						form.setItemValue("orgnNm", "");
						break;
					case "orgnNm" :
						form.setItemValue("orgnId", "");
						form.setItemValue("orgnNm", "");
						break;
// 					case "strtDt" :
// 						var dt = form.getItemValue("strtDt");
// 						var edt = new Date(dt.setDate(dt.getDate() + 15)).format("yyyymmdd");
// 						form.setItemValue("endDt", edt);
// 						break;
					/* case "endDt" :
						var dt = form.getItemValue("endDt");
						var sdt = new Date(dt.setDate(dt.getDate() - 15)).format("yyyymmdd");
						form.setItemValue("strtDt", sdt);
						break; */
					case "searchCd" :
						form.setItemValue("searchVal", "");
						break;
				}
			}
			, onValidateMore:function(data){
				if(data.endDt == null || data.endDt == "") this.pushError("data.endDt", "조회기간", "종료일자를 입력하세요");
				else{
					if(data.strtDt > data.endDt) this.pushError("data.endDt", "조회기간", "종료일자가 시작일자 이전입니다.");
					
					if(data.searchType == "70" || data.searchType == "80") {
						if(mvno.validator.dateCompare(data.strtDt, data.endDt) > 31) this.pushError("data.strtDt", "조회기간", "다회선, 다중납부 조회기간은 31일 이내만 가능합니다.");
					} else {
						if(mvno.validator.dateCompare(data.strtDt, data.endDt) > 15) this.pushError("data.strtDt", "조회기간", "조회기간은 15일 이내만 가능합니다.");	
					}
				}
				
				if(data.searchCd != "" && data.searchVal == "") this.pushError("data.searchVal", "검색어", "검색어를 입력하세요");
				if(data.searchCd == "" && data.searchVal != "") this.pushError("data.searchVal", "검색구분", "검색구분을 선택하세요");
			}
			, onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rsk/rskMgmt/getNgCustMgmtList.json", form);
						break;
					case "btnOrgFind":
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("orgnId", result.orgnId);
							form.setItemValue("orgnNm", result.orgnNm);
						});
						break;
				}
			}
	}
	, GRID1 : {
			css : {
				height : "570px"
			}
			, title : "조회결과"
			, header : "계약번호,서비스구분,고객명,생년월일,성별,CTN,납부계정번호,계약상태,가입경로,가입유형,신청일자,개통일자,해지일자,해지사유,개통대리점,판매정책,할부기간,약정기간,최초요금제,최종요금제,최초요금제변경일,최초단말,최초단말일련번호,현재단말,현재단말일련번호,최초기변일,최종기변일,최초정지일,최초명변일,가입기간,정지일수"
			, columnIds : "contractNum,serviceTypeNm,custNm,birthDt,gender,subscriberNo,ban,subStatusNm,onOffTypeNm,reqBuyTypeNm,reqDt,openDt,tmntDt,tmntRsnNm,agncyNm,plcySaleNm,instMnthCnt,agrmTrm,fstRateNm,lstRateNm,chgRateDt,fstPrdtCode,fstIntmSrlNo,lstPrdtCode,lstIntmSrlNo,fstDvcChgDt,lstDvcChgDt,fstSusdt,fstMcnDt,usgDtCnt,stopDtCnt"
			, colAlign : "center,center,left,center,center,center,center,center,center,center,center,center,center,left,left,left,right,right,left,left,center,left,center,left,center,center,center,center,center,right,right"
			, colTypes : "ro,ro,ro,roXd,ro,roXp,ro,ro,ro,ro,roXd,roXd,roXd,ro,ro,ro,ron,ron,ro,ro,roXd,ro,ro,ro,ro,roXd,roXd,roXd,roXd,ron,ron"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,int,int,str,str,str,str,str,str,str,str,str,str,str,int,int"
			, widths : "100,80,150,80,80,120,100,80,80,80,100,100,100,150,150,200,80,80,150,150,100,120,100,120,100,100,100,100,100,80,80"
			, paging: true
			, pageSize:50
			, buttons : {
				hright : {
					btnExcel : { label : "자료생성" }
				}
			}
			,onButtonClick:function(btnId, selectedData){
				var grid = this;
				
				switch(btnId){
					case "btnExcel" :
						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}else{
							var searchData =  PAGE.FORM1.getFormData(true);
							var strtDt = searchData.strtDt;
							var endDt = searchData.endDt;
							var searchType = searchData.searchType;
							
							if(strtDt != null && strtDt != '' && endDt != null && endDt != '' 
									&& (((searchType == "70" || searchType == "80") && mvno.validator.dateCompare(strtDt, endDt) <= 31)
										|| mvno.validator.dateCompare(strtDt, endDt) <= 15)){
                                mvno.cmn.downloadCallback(function(result) {
                                    PAGE.FORM1.setItemValue("DWNLD_RSN",result);
                                    mvno.cmn.ajax(ROOT + "/rsk/rskMgmt/getNgCustMgmtListExcel.json?menuId=MSP1030101", PAGE.FORM1.getFormData(true), function(result){
                                        mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
                                    });
                                });
								/* mvno.cmn.download(ROOT + "/rsk/rskMgmt/getNgCustMgmtListExcel.json?menuId=MSP1030101", true, {postData:searchData}); */
							}else{
								mvno.ui.alert("검색조건이 맞지 않습니다.");
								return;
							}
						}
						break;
				}
			}
	}
};
	
var PAGE = {
	title: "${breadCrumb.title}",
	breadcrumb: "${breadCrumb.breadCrumb}",
	buttonAuth: ${buttonAuth.jsonString},
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");

		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2023"}, PAGE.FORM1, "searchType"); // 조회조건
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchCd"); // 검색구분
		
		var dt = new Date();
		PAGE.FORM1.setItemValue("endDt", dt.format("yyyymmdd"));
		PAGE.FORM1.setItemValue("strtDt", new Date(dt.setDate(dt.getDate() - 15)).format("yyyymmdd"));

	}
};
</script>