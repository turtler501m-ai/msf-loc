<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : msp_rsk_unpBond_1001.jsp
 * @Description : 청구수미납현황
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
<!-- 그리드영역 -->
<div id="GRID1"></div>
	
<!-- Script -->
<script type="text/javascript">

	var dt = new Date();
	var today = new Date().format("yyyymm");
	<%@ include file="unpayBond.formitems" %>
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
							, {type:"calendar", width:80, name:"strtYm", label:"청구월", labelWidth:60, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", offsetLeft:50, required:true}
							, {type:"newcolumn"}
							, {type:"calendar", width:80, name:"endYm", label:"~", labelWidth:10, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", offsetLeft:5}
						]}
						//조회 버튼
						, {type:"newcolumn"}
						, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}/* btn-search(열갯수1~4) */
					]
					, onChange:function(name, value){
						var form = this;
						switch(name){
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
					}
					, onButtonClick : function(btnId) {
						var form = this;
						
						switch (btnId) {
							case "btnSearch":
								PAGE.GRID1.list(ROOT + "/rsk/unpayBondMgmt/getUnpayBondList.json", form);
							break;
							
						}
					}
			}
		, GRID1 : {
			css : {
				height : "540px"
			}
			, title : "조회결과"
			, header : "기준월,청구월,청구항목,청구금액,#cspan,#cspan,조정금액,#cspan,#cspan,실청구금액,수납금액,#cspan,#cspan,미납금액,청구건수,수납건수,미납건수"
			, header2 : "#rspan,#rspan,#rspan,금액,부가세,합계,금액,부가세,합계,#rspan,금액,부가세,합계,#rspan,#rspan,#rspan,#rspan" //16
			, columnIds : "stdrYm,billYm,saleItmNm,invAmnt,invVat,totInvAmnt,adjAmnt,adjVat,totAdjAmnt,realInvAmnt,pymAmnt,pymVat,totPymAmnt,unpdAmnt,invCnt,pymCnt,unpdCnt"
			, colAlign : "center,center,left,right,right,right,right,right,right,right,right,right,right,right,right,right,right"
			, colTypes : "roXdm,roXdm,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron"
			, widths : "80,80,250,100,100,100,100,100,100,100,100,100,100,100,100,100,100"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, paging : true
			, pageSize : 20
			, showPagingAreaOnInit: true
			, buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				}
				, right : {
					btnDtl : {label : "조정금액상세"}
				}
			}
			, onButtonClick : function(btnId, selectedData) {
				var form = this;
				switch (btnId) {
					case "btnDnExcel":
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						} else {
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download('/rsk/unpayBondMgmt/getUnpayBondListEx.json'+"?menuId=MSP1030109",true,{postData:searchData});
							break; 
						}
						
					case "btnDtl" :
						if (PAGE.GRID1.getSelectedRowData() == null) {
							mvno.ui.alert("ECMN0002");
							
							break;
						}
						
						var data = PAGE.GRID1.getSelectedRowData();
						
						if (data.totAdjAmnt == 0) {
							mvno.ui.alert("조정금액 상세내역이 없습니다.");
							break;
						}
						
						mvno.ui.createForm("FORM2");
						
						mvno.cmn.ajax(ROOT + "/rsk/unpayBondMgmt/getAdjAmntDtl.json", data, function(resultData) {
							PAGE.FORM2.setFormData(resultData.result.data.rows[0]);
						});
						
						mvno.ui.popupWindowById("FORM2", "조정금액상세", 550, 370, {
						onClose: function(win) {
								win.closeForce();
							}
						});
						break;
  						
				}
			}
		}
		, FORM2: {
			items: _FORMITEMS_['form2'],
			buttons: {
				center: {
					btnClose: {label: "닫기"}
				}
			}
			, onButtonClick : function(btnId) {
				var form = this
				switch(btnId) {
					case "btnClose" :
						mvno.ui.closeWindowById(form);
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
			
			PAGE.FORM1.setItemValue("stdrYm", today);
			PAGE.FORM1.setItemValue("strtYm", today);
			PAGE.FORM1.setItemValue("endYm", today);
			
			// IE 인 경우만 SPLIT 실행
			var fixGrid = PAGE.GRID1.getColIndexById("saleItmNm");
			if(mvno.cmn.isIE()) PAGE.GRID1.splitAt(fixGrid+1);
		}
	};
</script>