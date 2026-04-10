<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : msp_rcp_cdat_1001.jsp
 * @Description : 통신자료제공내역신청
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.03.31 이춘수 최초 생성
 * @
 * @author : 이춘수
 * @Create Date : 2016.03.31
 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search" />
<!-- 그리드영역 -->
<div id="GRID1"></div>
	
<!-- Script -->
<script type="text/javascript">

	var dt = new Date();
	var today = new Date().format("yyyymmdd");
	
	var DHX = {
			// ----------------------------------------
			// 조회조건
			// ----------------------------------------
			FORM1 : {
					items : [
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						, {type:"block", list:[
// 							{type :"select", width:80, name:"searchCd", userdata:{lov:'RSK0006'}, required:true}
							{type :"select", width:80, name:"searchCd", required:true}
							, {type:"newcolumn"}
							, {type:"calendar", width:100, name:"strtDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", offsetLeft:5}
							, {type:"newcolumn"}
							, {type:"calendar", label:"~", name:"endDt", labelAlign:"center", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:10, width : 100}
							, {type:"newcolumn"}
// 							, {type:"select", name:"searchType", label:"검색구분", userdata:{lov:"RCP0062"}, offsetLeft:40, width:80}
							, {type:"select", name:"searchType", label:"검색구분", offsetLeft:40, width:80}
							, {type:"newcolumn"}
							, {type:"input", name:"searchVal", width:120, maxLength:50}
						]}
						, {type:"block", list:[
// 							, {type:"select", label:"처리여부", width:80, labelWidth:80, name:"resultYn", userdata:{lov:'ORG0048'}, offsetLeft:10}
							, {type:"select", label:"처리여부", width:80, labelWidth:80, name:"resultYn", offsetLeft:10}
							, {type:"newcolumn"}
							, {type : "select", label:"제공여부", width:80, name:"isInvstProc", offsetLeft:173, options:[
								 {value: "", text: "- 전체 -"}
								,{value: "Y", text: "제공"}
								,{value: "N", text: "미제공"}
							]}
						]}
						
						
						//조회 버튼
						, {type:"newcolumn"}
						, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"}/* btn-search(열갯수1~4) */
					]
					, onChange:function(name, value){
						var form = this;
						switch(name){
							case "searchType" :
								form.setItemValue("searchVal", "");
								break;
						}
					}
					, onValidateMore : function(data){
						if(data.stdtDt == "" || data.endDt == "") {
							this.pushError("data.strtDt", "신청기간", "신청기간을 입력하세요.");
							return;
						}
						if(mvno.validator.dateCompare(data.strtDt, data.endDt) < 0) {
							this.pushError("data.strtDt", "신청기간", "신청기간을 확인하세요.");
							return;
						}
						if(data.searchType == "" && data.searchVal != "") {
							this.pushError("data.searchType", "검색구분", "검색구분을 선택하세요.");
							return;
						}
						if(data.searchType != "" && data.searchVal == "") {
							this.pushError("data.searchVal", "검색어", "검색어를 입력하세요.");
						}
					}
					, onButtonClick : function(btnId) {
						var form = this;
						switch (btnId) {
							case "btnSearch":
								PAGE.GRID1.list(ROOT + "/rcp/commdatmgmt/getCommDatePrvList.json", form);
							break;
							
						}
					}
			}
		, GRID1 : {
			css : {
				height : "530px"
			}
			, title : "조회결과"
			, header : "처리여부,이름,생년월일,가입상품,연락처,대상번호,제공여부,수령이메일,요청기간,제공일자,요청사유,제공내역,신청일자,처리자,처리일자" //15
			, columnIds : "resultYn,apyNm,bthday,sbscPrdtNm,cntcTelNo,tgtSvcNo,isInvstProcNm,recpEmail,confSbst01Yn,confSbst02Yn,confSbst03Yn,confSbst04Yn,apyDt,procrNm,procDt"
			, colAlign : "center,center,center,center,center,center,center,left,center,center,center,center,center,center,center"
			, colTypes : "ro,ro,roXd,ro,roXp,roXp,ro,ro,ro,ro,ro,ro,roXd,ro,roXd"
			, widths : "70,80,100,80,100,100,80,180,70,70,70,70,100,80,100"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, paging : true
			, multiCheckbox: true
			, pageSize : 20
			, buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				},
				right : {
					 btnReg : { label : "처리완료" }
// 					,btnDel : { label : "메일미처리실행" }
				},
				left : {
					printData : {label : "확인서출력"}
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
  							mvno.cmn.download('/rcp/commdatmgmt/getCommDatePrvListEx.json'+"?menuId=MSP1030004",true,{postData:searchData});
  							break; 
  						}
  					case "btnReg":
  						var arrData = form.getCheckedRowData();
  						
  						if (arrData == null || arrData == '') {
							mvno.ui.alert("ECMN0003"); //선택한(체크된) 데이터가 없습니다.
							return;
  						} else {
							var sData = {
							};
							
							sData.items = arrData;
							
							for (var i=0; i<arrData.length; i++) {
								if (arrData[i].resultYn == '처리') {
									mvno.ui.alert("미처리건만 처리 가능합니다."); //선택한(체크된) 데이터가 없습니다.
									return;
								}
							}
							
							mvno.ui.confirm("CCMN0015", function(){
								 mvno.cmn.ajax4json(ROOT + "/rcp/commdatmgmt/updateResultY.json", sData, function(result) {
									mvno.ui.notify("NCMN0006");
									PAGE.GRID1.refresh();
								});
							});
							break;
						}
					case "btnDel":
						var arrData = form.getCheckedRowData();
						
						if (arrData == null || arrData == '') {
							mvno.ui.alert("ECMN0003"); //선택한(체크된) 데이터가 없습니다.
							return;
						} else {
							var sData = {
							};
							
							sData.items = arrData;
							
							mvno.ui.confirm("CCMN0015", function(){
								 mvno.cmn.ajax4json(ROOT + "/rcp/commdatmgmt/updateResultN.json", sData, function(result) {
									mvno.ui.notify("NCMN0006");
									PAGE.GRID1.refresh();
								});
							});
							break;
						}
					case "printData":
						
						var data = PAGE.GRID1.getSelectedRowData();
						
						if(data == null) {
							mvno.ui.alert("ECMN0002");
							return;
						}
						var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/rcp/commDatForm.jrf" + "&arg=";
						param = encodeURI(param);
						
						var arg = "apySeq#" + data.apySeq + "#isInvstProc#" + data.isInvstProc + "#strtDt#" + new Date(PAGE.FORM1.getItemValue("strtDt")).format("yyyymmdd") + "#endDt#" + new Date(PAGE.FORM1.getItemValue("endDt")).format("yyyymmdd") + "#";
						arg = encodeURIComponent(arg);
						
						param = param + arg;
						
						var msg = "확인서를 출력하시겠습니까?";
						mvno.ui.confirm(msg, function() {
							mvno.ui.popupWindow("/cmn/report/report.do"+param, "통신자료 제공내역 결과 통지서", 900, 700, {
								onClose: function(win) {
									win.closeForce();
								}
							});
						});
						
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
			
			PAGE.FORM1.setItemValue("strtDt", mvno.cmn.getAddDay(today, -7));
			PAGE.FORM1.setItemValue("endDt", today);

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2022",orderBy:"etc6"}, PAGE.FORM1, "searchCd"); // 기간구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2019"}, PAGE.FORM1, "searchType"); // 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1006",orderBy:"etc6"}, PAGE.FORM1, "resultYn"); // 처리여부
		}
	};
</script>