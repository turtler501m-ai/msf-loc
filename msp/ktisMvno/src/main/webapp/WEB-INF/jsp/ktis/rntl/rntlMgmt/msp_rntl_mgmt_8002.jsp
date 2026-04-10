<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name  : msp_rntl_mgmt_8002.jsp
 * @Description : 매각개통관리
 * @
 * @  수정일	  	수정자			  수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2017.04.17  	강무성		  최초생성
 * @ 
 * @author : 강무성
 * @Create Date : 2017.04.17
 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>

<script type="text/javascript">
var searchEndDt = new Date().format('yyyymmdd');
var searchStartDt = '20160601';

var orgId = '${orgnInfo.orgnId}';
var typeCd = '${orgnInfo.typeCd}';

var DHX = {
	FORM1 : {
		items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					, {type:"block", list:[
						{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchStartDt', label: '개통일자', calendarPosition: 'bottom', readonly:true ,width:100,offsetLeft:10}
						, {type: 'newcolumn'}
						, {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
						, {type: 'newcolumn'}
// 						, {type:"select", width:100, offsetLeft:15, label:"계약상태",name:"subStatus",userdata:{lov:"RCP0020"}}
						, {type:"select", width:100, offsetLeft:15, label:"계약상태",name:"subStatus"}
						, {type: 'newcolumn'}
						, {type:"select", width:150, offsetLeft:15, labelWidth:50, label:"대리점",name:"orgnId"}
					]}
					, {type:"block", list:[
// 						{type:"select", width:100, label:"검색구분",name:"searchGbn", userdata:{lov:"RNTL001"}, offsetLeft:10}
						{type:"select", width:100, label:"검색구분",name:"searchGbn", offsetLeft:10}
						, {type:"newcolumn"}
						, {type:"input", width:115, name:"searchName"}
						, {type:"newcolumn"}
// 						, {type:"select", width:100, offsetLeft:15, label:"렌탈상태",name:"rntlStatus", userdata:{lov:"RNTL002"}}
						, {type:"select", width:100, offsetLeft:15, label:"렌탈상태",name:"rntlStatus"}
					]}
					//버튼 여러번 클릭 막기 변수
					, {type : 'hidden', name: "btnCount1", value:"0"} 
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}
					// 조회버튼
					, {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"}
				],
				onXLE : function() {},
				onButtonClick : function(btnId) {
					var form = this;
					
					switch (btnId) {
						case "btnSearch":
							
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") {
								return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기
							}
							
							PAGE.GRID1.list(ROOT + "/rntl/rntlMgmt/getSaleOpenList.json", form, {
								callback : function () {
									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								}
							});
							
							break;
					}
				},
				onChange : function(name, value) {//onChange START
					// 검색구분
					if(name == "searchGbn") {
						PAGE.FORM1.setItemValue("searchName", "");

						if(value == null || value == "") {
							
							// 신청일자 Enable
							PAGE.FORM1.enableItem("searchStartDt");
							PAGE.FORM1.enableItem("searchEndDt");
							
							PAGE.FORM1.setItemValue("searchName", "");
							
							PAGE.FORM1.setItemValue("searchStartDt", searchStartDt);
							PAGE.FORM1.setItemValue("searchEndDt", searchEndDt);
							
							PAGE.FORM1.disableItem("searchName");
						}
						else {
							PAGE.FORM1.setItemValue("searchStartDt", "");
							PAGE.FORM1.setItemValue("searchEndDt", "");
							
							PAGE.FORM1.disableItem("searchStartDt");
							PAGE.FORM1.disableItem("searchEndDt");
							
							PAGE.FORM1.enableItem("searchName");
						}
					}
				},
				onValidateMore : function (data){
					if (this.getItemValue("searchStartDt", true) > this.getItemValue("searchEndDt", true)) {
						this.pushError("searchEndDt", "조회기간", "종료일자가 시작일자보다 클 수 없습니다.");
						this.resetValidateCss();
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
					
					if( data.searchGbn != "" && data.searchName.trim() == ""){
						this.pushError("searchName", "검색어", "검색어를 입력하세요");
						PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
				}
	},
	//개통 내역
	GRID1 : {
		css : {
				height : "505px"
		},
		title : "조회결과",
		header : "대리점,예약번호,계약번호,고객명,단말모델ID,단말모델코드,단말모델명,단말모델일련번호,개통일자,반납일자,계약상태,렌탈상태,매입단가,렌탈비용,정산일수(누적),정산금액(누적),정산일수(당월),정산금액(당월),잔존가액,대리점ID,반납상태ID",
		columnIds : "agncyNm,resNo,contractNum,custNm,prdtId,intmMdlCd,intmMdlNm,intmMdlSrl,openDt,rtrnDt,subStatus,rntlStat,buyAmnt,rentalCost,prvdTotDay,prvdTotAmnt,prvdMonDay,prvdMonAmnt,remainAmnt,agncyId,rntlRtrnStat",
		colAlign : "center,center,center,center,center,left,left,left,center,center,center,center,right,right,right,right,right,right,right,center,center",
		colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,roXd,roXd,ro,ro,roXns,roXns,ro,roXns,ro,roXns,roXns,ro,ro",
		colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
		widths : "120,80,80,100,100,120,150,120,100,100,100,100,100,100,100,100,100,100,100,80,80",
		hiddenColumns: [4,19,20],
		paging: true,
		pageSize:20,
		buttons : {
			hright : {
				btnDnExcel : { label : "엑셀다운로드" }
			},
			right : {
				btnReturn: { label : "반납" },
				btnCncl: { label : "반납취소" }
			}
		},
		checkSelectedButtons : ["btnDtl"],
		onRowDblClicked : function(rowId, celInd) {
			this.callEvent('onButtonClick', ['btnDtl']);
		},
		onButtonClick : function(btnId, selectedData) {
			var form = this;
			switch (btnId) {
				case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
					
					var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
					PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
					if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") {
						return; //버튼 최초 클릭일때만 조회가능하도록
					}
					
					if(PAGE.GRID1.getRowsNum() == 0) {
						mvno.ui.alert("데이터가 존재하지 않습니다.");
						return;
					}
					
					var searchData =  PAGE.FORM1.getFormData(true);
					mvno.cmn.download(ROOT + "/rntl/rntlMgmt/getSaleOpenListExcel.json?menuId=MSP8000002",true,{postData:searchData});
					
					break;
					
				case 'btnReturn' :
					if(PAGE.GRID1.getSelectedRowData() == null) {
						mvno.ui.alert("반납 대상을 선택해 주세요.");
						return;
					}
					
					var rowData = PAGE.GRID1.getSelectedRowData();
					
					if(rowData.rntlRtrnStat == "Y" && !mvno.cmn.isEmpty(rowData.rtrnDt)) {
						mvno.ui.alert("정산 처리 되어 반납 일자 변경이 불가능 합니다.");
						return;
					}
					
					mvno.ui.createForm("FORM2");
					
					PAGE.FORM2.clear();
					
					var rowData = PAGE.GRID1.getSelectedRowData();
					
					var toDate = new Date().format('yyyymmdd');
					
					PAGE.FORM2.setItemValue("custNm", rowData.custNm);
					PAGE.FORM2.setItemValue("resNo", rowData.resNo);
					PAGE.FORM2.setItemValue("contractNum", rowData.contractNum);
					PAGE.FORM2.setItemValue("intmMdlCd", rowData.intmMdlCd);
					PAGE.FORM2.setItemValue("intmMdlNm", rowData.intmMdlNm);
					PAGE.FORM2.setItemValue("openDt", rowData.openDt);
					PAGE.FORM2.setItemValue("rtrnDt", toDate);
					PAGE.FORM2.setItemValue("orgnId", rowData.agncyId);
					PAGE.FORM2.setItemValue("prdtId", rowData.prdtId);
					
					PAGE.FORM2.clearChanged();
					
					mvno.ui.popupWindowById("FORM2", "반납등록", 530, 270, {
						onClose: function(win) {
							if (! PAGE.FORM2.isChanged()) return true;
							mvno.ui.confirm("CCMN0005", function(){
								win.closeForce();
							});
						}
					});
					
					break;
					
				case 'btnDtl' :		//Row 더블클릭시 Start
					
					this.callEvent('onButtonClick', ['btnReturn']);
					
					break;	//Row 더블클릭시 End
					
				case 'btnCncl' :
					
					if(PAGE.GRID1.getSelectedRowData() == null) {
						mvno.ui.alert("반납 취소 대상을 선택해 주세요.");
						return;
					}
					
					var rowData = PAGE.GRID1.getSelectedRowData();
					
					if(mvno.cmn.isEmpty(rowData.rtrnDt)){
						mvno.ui.alert("반납 일자가 등록 되지 않았습니다.");
						return;
					}
					
					if(rowData.rntlRtrnStat == "Y" && !mvno.cmn.isEmpty(rowData.rtrnDt)) {
						mvno.ui.alert("정산 처리 되어 반납 취소가 불가능 합니다.");
						return;
					}
					
					var data = {
							orgnId : rowData.agncyId,
							prdtId : rowData.prdtId,
							contractNum : rowData.contractNum,
							rtrnDt : rowData.rtrnDt,
							rtrnTrtmTypeCd : "DEL"
					}
					
					mvno.cmn.ajax4confirm("취소하시겠습니까?", ROOT + "/rntl/rntlMgmt/saveReturnMgmtByInfo.json", data, function(){
						mvno.ui.notify("NCMN0004");
						
						PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
					});
					
					break;
			}//switch End
		}// onButtonClick
	} //End Grid1
	
	, FORM2 : {
		items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, blockOffset:0}
					, {type:"block", list:[
						{type:"input", width:120, offsetLeft:10, label:"고객명",name:"custNm", disabled:true}
					]}
					, {type:"block", list:[
						{type:"input", width:120, offsetLeft:10, label:"예약번호",name:"resNo", disabled:true}
						, {type: 'newcolumn'}
						, {type:"input", width:120, offsetLeft:30, label:"계약번호",name:"contractNum", disabled:true}
					]}
					, {type:"block", list:[
						{type:"input", width:120, offsetLeft:10, label:"단말모델코드",name:"intmMdlCd", disabled:true}
						, {type: 'newcolumn'}
						, {type:"input", width:120, offsetLeft:30, label:"단말모델명",name:"intmMdlNm", disabled:true}
					]}
					, {type:"block", list:[
						{type:"calendar", label:"개통일자", name:"openDt", width:120, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", offsetLeft:10, disabled:true}
						, {type: 'newcolumn'}
						, {type:"calendar", label:"반납일자", name:"rtrnDt", width:120, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", offsetLeft:30, required:true}
					]}
					, {type : 'hidden', name: "orgnId"} 
					, {type : 'hidden', name: "prdtId"} 
				]
				, buttons : {
					right : {
						btnReg : { label : "저장" }
						, btnClose : { label : "닫기" }
					}
				}
				, onButtonClick : function(btnId) {
					var form = this;
					
					switch (btnId) {
						case "btnReg":
							
							var openDtFM = new Date(form.getItemValue("openDt")).format("yyyymmdd");
							var rtrnDtFM = new Date(form.getItemValue("rtrnDt")).format("yyyymmdd");
							
							if(openDtFM > rtrnDtFM){
								mvno.ui.alert("반납일자가 개통일자보다 작을 수 없습니다.");
								return;
							}
							
							mvno.cmn.ajax(ROOT + "/rntl/rntlMgmt/saveReturnMgmtByInfo.json", form, function(){
								mvno.ui.notify("NCMN0004");
								
								mvno.ui.closeWindowById("FORM2");
								
								PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
							});
							
							break;
							
						case "btnClose":
							mvno.ui.closeWindowById("FORM2");
							
							break;
					}
				}
	} //FORM2 End
}; // end dhx

var PAGE = {
		 title : "${breadCrumb.title}",
		 breadcrumb : "${breadCrumb.breadCrumb}",  
		 buttonAuth: ${buttonAuth.jsonString},
		 onOpen:function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			PAGE.FORM1.setItemValue("searchStartDt", searchStartDt);
			PAGE.FORM1.setItemValue("searchEndDt", searchEndDt);
			
			PAGE.FORM1.disableItem("searchName");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0228"}, PAGE.FORM1, "orgnId");

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0037"}, PAGE.FORM1, "subStatus"); // 계약상태
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2029"}, PAGE.FORM1, "searchGbn"); // 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2030",orderBy:"etc6"}, PAGE.FORM1, "rntlStatus"); // 렌탈상태
			
			if(typeCd != 10){
				PAGE.FORM1.setItemValue("orgnId", orgId);
				PAGE.FORM1.disableItem("orgnId");
			}
		}
	};

</script>