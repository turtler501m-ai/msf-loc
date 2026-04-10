<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : msp_rsk_unpBond_1002.jsp
 * @Description : 청구수미납상세
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.05.25 이춘수 최초 생성
 * @
 * @author : 이춘수
 * @Create Date : 2016.05.25
 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search" />
<!--  <div id="FORM2" class="section"></div> -->
<!-- 그리드영역 -->
<div id="GRID1"></div>
	
<!-- Script -->
<script type="text/javascript">

	<%@ include file="unpayBond.formitems" %>
	
	var dt = new Date();
	var today = new Date().format("yyyymm");
	var vWeek = new Date().getDay();
	var vTime = new Date().format("hh");
	
	var DHX = {
			// ----------------------------------------
			// 조회조건
			// ----------------------------------------
			FORM1 : {
					items : [
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						, {type:"block", list:[
							{type:"calendar", width:80, name:"stdrYm", label:"기준월", labelWidth:60, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", offsetLeft:10, required:true}
							, {type:"newcolumn"}
							, {type:"calendar", width:80, name:"strtYm", label:"청구월", labelWidth:60, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", offsetLeft:30, required:true}
							, {type:"newcolumn"}
							, {type:"calendar", width:80, name:"endYm", label:"~", labelWidth:10, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", offsetLeft:5}
							, {type:"newcolumn"}
// 							, {type:"select", name:"searchType", label:"검색구분", userdata:{lov:"RSK0004"}, offsetLeft:30, width:80}
							, {type:"select", name:"searchType", label:"검색구분", offsetLeft:30, width:80}
							, {type:"newcolumn"}
							, {type:"input", name:"searchVal", width:120, maxLength:50}
							, {type:"newcolumn"}
							, {type:"checkbox", label:"미납여부", name:"unpdYn", labelWidth:60, offsetLeft:20}
						]}
						//조회 버튼
						, {type : 'hidden', name: "DWNLD_RSN", value:""} //엑셀다운로드 사유
						, {type:"newcolumn"}
						, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}/* btn-search(열갯수1~4) */
					]
					, onChange:function(name, value){
						var form = this;
						switch(name){
							case "searchType" :
								form.setItemValue("searchVal", "");
								break;
						}
					}
					,onValidateMore : function(data) {
						if(data.endYm == "") {
							this.pushError("data.strtYm", "청구월", "ICMN0001");
							return;
						}
						if(mvno.validator.dateCompare(data.strtYm, data.endYm) < 0) {
							this.pushError("data.strtDt", "청구월", "ICMN0004");
							return;
						}
						if((!mvno.cmn.isEmpty(data.searchType) && mvno.cmn.isEmpty(data.searchVal)) || (mvno.cmn.isEmpty(data.searchType) && !mvno.cmn.isEmpty(data.searchVal))) {
							this.pushError(["searchVal"],"검색구분","검색어를 입력하십시오.");
						}
					}
					, onButtonClick : function(btnId) {
						var form = this;

						switch (btnId) {
							case "btnSearch":
								PAGE.GRID1.list(ROOT + "/rsk/unpayBondMgmt/getUnpayBondDtlList.json", form);
							break;
							
						}
					}
			}
		, GRID1 : {
			css : {
				height : "540px"
			}
			, title : "조회결과 <font size='2'>( * 생성된 파일은 엑셀다운로드 메뉴에서 다운받으실 수 있습니다 )</font>"
			, header : "기준월,사용월,청구월,서비스계약번호,계약번호,계약상태,고객명,개통대리점,청구항목,청구금액,#cspan,할인,#cspan,클레임,#cspan,위약금(공시지원금1),#cspan,위약금(공시지원금2),#cspan,위약금(추가지원금1),#cspan,위약금(추가지원금2),#cspan,절사,#cspan,기타,#cspan,조정합계,#cspan,#cspan,총청구금액,수납금액,#cspan,#cspan,미납금액,#cspan,#cspan,납기일자,수납일자,미납개월수,납부방법"
			, header2 : "#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,금액,부가세,금액,부가세,금액,부가세,금액,부가세,금액,부가세,금액,부가세,금액,부가세,금액,부가세,금액,부가세,금액,부가세,합계,#rspan,금액,부가세,합계,금액,부가세,합계,#rspan,#rspan,#rspan,#rspan"
			, columnIds : "stdrYm,usgYm,billYm,svcCntrNo,contractNum,statusNm,subLinkName,openAgntNm,saleItmNm,invAmnt,invVat,adjAAmnt,adjAVat,adjBAmnt,adjBVat,adjCAmnt,adjCVat,adjDAmnt,adjDVat,adjEAmnt,adjEVat,adjFAmnt,adjFVat,adjGAmnt,adjGVat,adjHAmnt,adjHVat,adjAmnt,adjVat,totAdjAmnt,totInvAmnt,pymAmnt,pymVat,totPymAmnt,unpdAmnt,unpdVat,totUnpdAmnt,duedatDate,blpymDate,unpdMnthCnt,pymnMthdNm"
			, colAlign : "center,center,center,center,center,center,left,left,left,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,center,center,right,left"
			, colTypes : "roXdm,roXdm,roXdm,ro,ro,ro,ro,ro,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,roXd,roXd,ron,ro"
			, widths : "80,80,80,100,100,80,100,120,250,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, paging : true
			, pageSize : 20
			, showPagingAreaOnInit: true
			, buttons : {
				hright : {
					btnDnExcel : {label : "자료생성"}
				}
				, right : {
					btnDtl : {label : "조정금액상세"}
				}
			}
			, onButtonClick : function(btnId, selectedData) {
				var form = this;
				switch (btnId) {
					case "btnDtl" :
						if (PAGE.GRID1.getSelectedRowData() == null) {
							mvno.ui.alert("ECMN0002");
							
							break;
						}
						
						var data = PAGE.GRID1.getSelectedRowData();
						
						if (data.svcCntrNo == "" || data.svcCntrNo == null) {
							mvno.ui.alert("검색어가 있는 경우 조회가능합니다.");
							return;
						}
						
						if (data.totAdjAmnt == 0) {
							mvno.ui.alert("조정금액 상세내역이 없습니다.");
							return;
						}
						
						mvno.ui.createGrid("GRID2");
						//mvno.ui.createForm("FORM2");
						
						var rowData = this.getSelectedRowData();
						PAGE.GRID2.list(ROOT + "/rsk/unpayBondMgmt/getAdjRsnList.json", rowData);
/* 						mvno.cmn.ajax(ROOT + "/rsk/unpayBondMgmt/getAdjAmntDtl.json", rowData, function(resultData) {
							PAGE.FORM2.setFormData(resultData.result.data.rows[0]);
						}); */
						
						mvno.ui.popupWindowById("GRID2", "조정금액상세", 750, 350, {
						onClose: function(win) {
								win.closeForce();
							}
						});
						break;
						
					case "btnDnExcel" :
						if(PAGE.FORM1.getItemValue("stdrYm") == null || PAGE.FORM1.getItemValue("stdrYm") == ""){
							mvno.ui.alert("기준월을 선택하세요");
							return;
						}
						
						if(PAGE.FORM1.getItemValue("strtYm") == null || PAGE.FORM1.getItemValue("strtYm") == ""){
							mvno.ui.alert("시작월을 선택하세요");
							return;
						}
						
						if(PAGE.FORM1.getItemValue("endYm") == null || PAGE.FORM1.getItemValue("endYm") == ""){
							mvno.ui.alert("종료월을 선택하세요");
							return;
						}
						
/* 						var searchData =  {
								stdrYm : new Date(PAGE.FORM1.getItemValue("stdrYm")).format("yyyymm"),
								strtYm : new Date(PAGE.FORM1.getItemValue("strtYm")).format("yyyymm"),
								endYm : new Date(PAGE.FORM1.getItemValue("endYm")).format("yyyymm"),
								userId: "${loginInfo.userId}"
						}
						
						var url = ROOT + "/cmn/btchmgmt/saveBatchRequest.json";
						
						var params = {
								 "batchJobId" : "BATCH00036"
								,"execParam" : JSON.stringify(searchData)
						}; */

                        mvno.cmn.downloadCallback(function(result) {
							PAGE.FORM1.setItemValue("DWNLD_RSN",result);
							mvno.cmn.ajax(ROOT + "/rsk/unpayBondMgmt/getUnpayBondDtlListExcelDownload.json?menuId=MSP1030110", PAGE.FORM1.getFormData(true), function(result){
								mvno.ui.alert("기준월 및 청구월을 이용하여<br />자료를 생성합니다.");
							});
						});
						
/* 						mvno.cmn.ajax(url, params, function(result) {
							mvno.ui.alert("기준월 및 청구월을 이용하여<br />자료를 생성합니다.");
						}); */
						
						break; 
  						
				}
			}
		}
 		, GRID2 : {
			css : {
				height : "200px"
				, width : "700px"
			}
			, title : "조정금액상세"
			, header : "청구월,서비스계약번호,청구항목,조정사유,조정금액,부가세,합계" //7
			, columnIds : "billYm,svcCntrNo,billItemNm,adjsRsnNm,adjAmnt,adjVat,totAdjAmnt"
			, colAlign : "center,center,left,left,right,right,right"
			, colTypes : "roXdm,ro,ro,ro,ron,ron,ron"
			, widths : "80,90,140,120,90,90,90"
			, colSorting : "str,str,str,str,str,str,str"
			, buttons : {
				center : {
					btnClose : {label : "닫기"}
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId) {
					case "btnClose" :
						mvno.ui.closeWindowById("GRID2", true);
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
			//mvno.ui.createForm("FORM2");
			mvno.ui.createGrid("GRID1");

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchType"); // 검색구분
			
			PAGE.FORM1.setItemValue("stdrYm", today);
			PAGE.FORM1.setItemValue("strtYm", today);
			PAGE.FORM1.setItemValue("endYm", today);
			
			PAGE.FORM1.setItemValue("unpdYn", 1);
			
			// IE 인 경우만 SPLIT 실행
			var fixGrid = PAGE.GRID1.getColIndexById("svcCntrNo");
			if(mvno.cmn.isIE()) PAGE.GRID1.splitAt(fixGrid+1);
		}
	};
</script>