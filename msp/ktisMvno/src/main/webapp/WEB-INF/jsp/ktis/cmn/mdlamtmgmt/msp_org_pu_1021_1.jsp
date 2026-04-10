<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_pu_1020_1.jsp
	 * @Description : 대표모델 출고단가 찾기 팝업 -- 단종모델 제외
	 * @
	 * @ 수정일		수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2015.07.31 심정보 최초생성
	 * @ 
	 * @author : 심정보
	 * @Create Date : 2015. 7. 31.
	 */
%>

<!-- header -->
<div class="page-header">
	<h1>모델 출고단가 찾기 팝업</h1>
</div>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"block", list:[
					{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},			  
					{type : "input",label : "대표모델ID",name : "rprsPrdtId" ,width:100, value:'' , offsetLeft:10, labelWidth:60, maxLength:10},
					{type: 'newcolumn'},
					{type:"input", name:"rprsPrdtIdResult", value:"", width:200, disabled: true},
					{type: 'newcolumn'},
					{type : "input",label : "색상모델ID",name : "prdtId", width:100 ,offsetLeft:10, value:'', labelWidth:60, maxLength:10},
					{type: 'newcolumn'},
					{type:"input", name:"prdtIdResult", value:"", width:200, disabled: true}
				]},
				{type:"block", list:[
					{type : "input",label : "모델명",name : "prdtNm" ,width:120, value:'' , offsetLeft:10, labelWidth:60, maxLength:10},
					{type: 'newcolumn'},
					{type: 'select', label: '모델유형', name: 'prdtTypeCd', width:100, offsetLeft:10, value:'', labelWidth:60},
					{type: 'newcolumn'},
					{type: 'select', label: '제조사명', name: 'mnfctId', width:100, offsetLeft:20, value:'', labelWidth:60},
					{type: 'newcolumn'},
					{type: 'select', label: '중고여부', name: 'oldYn', width:100, offsetLeft:20, value:'', labelWidth:60}
				]},
				{type: 'newcolumn', offset:100},
				{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"}
				],
				onChange: function (name, value) {
					switch(name) {
						case 'rprsPrdtId':
							if(value.length >= 4){
								mvno.cmn.ajax(ROOT + "/cmn/intmmdl/selectRprsPrdtNm.json", {rprsPrdtId:value, rprsYn:"Y"}, function(resultData) {
									if(resultData.result.resultCnt > 0){
										//대표모델 존재
										PAGE.FORM1.setItemValue("rprsPrdtIdResult", "[" + resultData.result.resultList[0].prdtNm + "/" + resultData.result.resultList[0].prdtCode + "]"); 
									}else{
										//대표모델 미존재
										PAGE.FORM1.setItemValue("rprsPrdtIdResult", "");
									} 
								});
							} else {
								
								PAGE.FORM1.setItemValue("rprsPrdtIdResult", "");
							}
							break;
							
						case 'prdtId':
						if(value.length >= 4){
							mvno.cmn.ajax(ROOT + "/cmn/intmmdl/selectRprsPrdtNm.json", {prdtId:value, rprsYn:"N"}, function(resultData) {
								if(resultData.result.resultCnt > 0){
									//색상모델 존재
									PAGE.FORM1.setItemValue("prdtIdResult", "[" + resultData.result.resultList[0].prdtNm + "/" + resultData.result.resultList[0].prdtCode + "]"); 
								}else{
									//색상모델 미존재
									PAGE.FORM1.setItemValue("prdtIdResult", "");
								} 
							});
						} else {
							PAGE.FORM1.setItemValue("prdtIdResult", "");	
						}
						break;
					}
					
				},
				onButtonClick : function(btnId) {

					var form = this;

					switch (btnId) {
						
						case "btnSearch":

							PAGE.GRID1.list(ROOT + "/cmn/hndsetamtmgmt/selectRprsHndstAmtHisList.json", form);
							break;
					}

				}
			},

	// ----------------------------------------
		GRID1 : {
			css : {
				height : "260px"
			},
			title : "모델리스트",
			header : "대표모델ID,대표모델코드,색상모델ID,색상모델코드,모델명,색상,중고여부,출고단가,모델유형,모델구분,제조사,출시일자,단종일자",//,통신사보조금,제조사장려금,정책보조금,보조금상한적용여부,단가적용일시,단가만료일시,비고",//17
			columnIds : "rprsPrdtId,rprsPrdtCode,prdtId,prdtCode,prdtNm,prdtColrNm,oldYnNm,outUnitPric,prdtTypeNm,prdtIndNm,mnfctNm,prdtLnchDt,prdtDt",//,newsAgaencySubsidy,mnfctGrant,maxAmt,subsidyMaxYnNm,unitPricApplDttm,unitPricExprDttm,remrk",
			widths : "80,100,80,120,150,80,80,100,100,80,120,80,80",//,80,80,80,120,120,120,100",
			colAlign : "center,left,center,left,left,center,center,right,center,center,left,center,center",//,right,right,right,center,center,center,left",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ron,ro,ro,ro,roXd,roXd",//,ron,ron,ron,ro,roXdt,roXdt,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str",//,str,str,str,str,str,str,str",
			paging:true,
			showPagingAreaOnInit: true,
			buttons : {
				center : {
					btnApply : { label : "확인" },
					btnClose : { label: "닫기" }
				}
			},
			checkSelectedButtons : ["btnApply"],
			onRowDblClicked : function(rowId, celInd) {
				// 선택버튼 누른것과 같이 처리
				this.callEvent('onButtonClick', ['btnApply']);
			},
			onButtonClick : function(btnId, selectedData) {
				
				switch (btnId) {

					case "btnApply":
						/*
						대표모델ID			: rprsPrdtId
						대표모델코드		: prdtCode
						모델명				: prdtNm
						제조사				: mnfctId
						모델유형			: prdtTypeCd
						모델구분			: prdtIndCd
						출시일자			: prdtLnchDt
						단종일자			: prdtDt
						출고단가			: outUnitPric
						중고여부			: oldYn
						중고여부명			: oldYnNm
						통신사보조금		: newsAgaencySubsidy
						제조사장려금		: mnfctGrant
						정책보조금			: maxAmt
						보조금상한적용여부	: subsidyMaxYn
						단가적용일시		: unitPricApplDttm
						단가만료일시		: unitPricExprDttm
						비고				: remrk
						*/
						
						if(selectedData.mnfctNm != null && selectedData.mnfctNm != ""){
							selectedData.mnfctNm = selectedData.mnfctNm.replace(/&amp;/g,"&");
						}
						mvno.ui.closeWindow(selectedData, true);
						break; 
					case "btnClose" :
						
						mvno.ui.closeWindow();	
						break

				}
			}
		}
};

var PAGE = {

	onOpen : function() {

		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		
		//제조사 셀렉트박스 셋팅
		mvno.cmn.ajax4lov( ROOT + "/org/mnfctmgmt/selectMnfctList.json", {mnfctYn:"Y"}, PAGE.FORM1, "mnfctId");
	
		//조회조건 모델유형 셀렉트박스 셋팅
		mvno.cmn.ajax4lov(ROOT+"/lgs/invtmgmt/getCommCombo.json", {grpId:"ORG0007"}, PAGE.FORM1, "prdtTypeCd");

		//조회조건 중고여부 셀렉트박스 셋팅
		mvno.cmn.ajax4lov(ROOT+"/lgs/invtmgmt/getCommCombo.json", {grpId:"ORG0011"}, PAGE.FORM1, "oldYn");
		
		//모델유형(prdtTypeCd) 파라메터가 넘어오면 모델유형 선택불가.
		if(PAGE.param.data != undefined) {
			if(PAGE.param.data.prdtTypeCd != "" && PAGE.param.data.prdtTypeCd != null){
				PAGE.FORM1.setItemValue("prdtTypeCd",PAGE.param.data.prdtTypeCd);
				PAGE.FORM1.disableItem("prdtTypeCd");
				
			}
		}
	}

};
</script>