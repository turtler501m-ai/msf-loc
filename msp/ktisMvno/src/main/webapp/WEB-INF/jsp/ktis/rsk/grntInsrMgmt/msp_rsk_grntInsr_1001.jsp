<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : msp_rsk_grntInsr_1001.jsp
 * @Description : 보증보험청구조회
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.05.10 이춘수 최초 생성
 * @
 * @author : 이춘수
 * @Create Date : 2016.05.10
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

	var DHX = {
			// ----------------------------------------
			// 조회조건
			// ----------------------------------------
			FORM1 : {
					items : [
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						, {type:"block", list:[
							  {type:"calendar", width:80, name:"strtYm", label:"연동월", labelWidth:60, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", offsetLeft:5, required:true}
							, {type:"newcolumn"}
							, {type:"calendar", width:80, name:"endYm", label:"~", labelWidth:10, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", offsetLeft:5}
							, {type:"newcolumn"}
// 							, {type:"select", name:"searchType", label:"검색구분", userdata:{lov:"RSK0007"}, offsetLeft:40, width:120}
							, {type:"select", name:"searchType", label:"검색구분", offsetLeft:40, width:120,
								options:[{value:"", text:"- 전체 -"}, {value:"10", text:"보증보험관리번호"}, {value:"20", text:"계약번호"}]}
							, {type:"newcolumn"}
							, {type:"input", name:"searchVal", width:120, maxLength:50}
						]}
						//조회 버튼
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
						
						if(!mvno.cmn.isEmpty(data.searchType) && mvno.cmn.isEmpty(data.searchVal)) {
							this.pushError(["searchVal"],"검색구분","검색어를 입력하십시오.");
						}
						
						if(data.endYm == "") {
							this.pushError("data.strtYm", "연동월", "ICMN0001");
							return;
						}
						if(mvno.validator.dateCompare(data.strtYm, data.endYm) < 0) {
							this.pushError("data.strtDt", "연동월", "ICMN0004");
							return;
						}
						
					}
					, onButtonClick : function(btnId) {
						var form = this;
						switch (btnId) {
							case "btnSearch":
								PAGE.GRID1.list(ROOT + "/rsk/grntInsrMgmt/getGrntInsrList.json", form);
							break;
							
						}
					}
			}
		, GRID1 : {
			css : {
				height : "600px"
			}
			, title : "조회결과"
			, header : "연동월,계약번호,계약상태,보증보험관리번호,청구일자,청구금액,지급일자,지급금액,오류사유" //9
			, columnIds : "workYm,contractNum,subStatusNm,grntInsrMngmNo,billDt,billAmnt,pymnDt,pymnAmnt,errDscr" //9
			, colAlign : "center,center,center,center,center,right,center,right,left" //9
			, colTypes : "roXdm,ro,ro,ro,roXd,ron,roXd,ron,ro" //9
			, widths : "80,100,70,120,100,100,100,100,185" //9
			, colSorting : "str,str,str,str,str,str,str,str,str" //9
			, paging : true
			, pageSize : 20
			, buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
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
  							mvno.cmn.download('/rsk/grntInsrMgmt/getGrntInsrListEx.json'+"?menuId=MSP1030108",true,{postData:searchData});
  							break; 
  						}
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

			PAGE.FORM1.setItemValue("strtYm", today);
			PAGE.FORM1.setItemValue("endYm", today);
			
		//	mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2024"}, PAGE.FORM1, "searchType"); // 검색구분
		}
	};
</script>