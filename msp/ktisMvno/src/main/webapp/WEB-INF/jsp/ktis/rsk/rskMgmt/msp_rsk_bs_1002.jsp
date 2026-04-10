<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  /**
   * @Class Name : msp_rsk_bs_1002.jsp
   * @Description : 다중계좌정보
   * @Modification Information
   *
   *   수정일         수정자                   수정내용
   *  -------    --------    ---------------------------
   *  2016.01.18            최초 생성
   *
   * author 한상욱
   * since 2016.01.18
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
					{type:"calendar", name:"strtDt", label:"개통일자", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", required:true, width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"calendar", name:"endDt", label:"~", labelWidth:20, labelAlign:"center", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", width:100}
					, {type:"newcolumn"}
// 					, {type:"select", name:"searchCd", label:"검색구분", userdata:{lov:"RSK0004"}, offsetLeft:20}
					, {type:"select", name:"searchCd", label:"검색구분", offsetLeft:20}
					, {type:"newcolumn"}
					, {type:"input", name:"searchVal", width:140}
				]}
				//조회 버튼
				, {type:"newcolumn"}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}/* btn-search(열갯수1~4) */
			]
			, onValidateMore:function(data){
				if(data.endDt == null || data.endDt == "") this.pushError("data.endDt", "개통일자", "종료일자를 입력하세요");
				else{
					if(data.strtDt > data.endDt) this.pushError("data.endDt", "개통일자", "종료일자가 시작일자 이전입니다.");
					
					if(mvno.validator.dateCompare(data.strtDt, data.endDt) > 31) this.pushError("data.strtDt", "개통일자", "조회기간은 31일 이내만 가능합니다.");	
				}
			}
			//초기화.
			, onChange : function(name, value) {
				var form = this;
				switch(name){
					case "searchCd" :
						form.setItemValue("searchVal", "");
						break;
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rsk/rskMgmt/getNgAccMgmtList.json", form);
						break;
				}
			}
	}
	, GRID1 : {
			css : {
				height : "570px"
			}
			, title : "조회결과"
			, header : "계약번호,고객명,CTN,대리점명,정책명,최초요금제,납부자명,납부자생년월일,최초단말기,사용자상태"
			, columnIds : "contractNum,subLinkName,ctn,agncyNm,plcySaleNm,fstRateNm,banLinkName,birthDt,fstModelNm,subStatusNm"
			, colAlign : "center,left,center,left,left,left,left,center,left,center"
			, colTypes : "ro,ro,roXp,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str"
			, widths : "100,100,100,150,250,250,100,100,100,80"
			, paging: true
			, pageSize:50
			, buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드" }
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
							mvno.cmn.download(ROOT + "/rsk/rskMgmt/getNgAccMgmtListExcel.json?menuId=MSP1030104", true, {postData:searchData}); 
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
		
		var dt = new Date();
		PAGE.FORM1.setItemValue("endDt", dt.format("yyyymmdd"));
		PAGE.FORM1.setItemValue("strtDt", new Date(dt.setDate(dt.getDate() - 15)).format("yyyymmdd"));
		PAGE.FORM1.setItemValue("serviceType", "PO");

		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchCd"); // 검색구분
	}
};
</script>