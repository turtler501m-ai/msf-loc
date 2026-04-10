<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_rcp_bs_0203.jsp
	 * @Description : 기기변경대상
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-full"></div>
<div id="FORM3" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

// 초기값 세팅
var sOrgnId = "";
var sOrgnNm = "";
var typeCd = "${loginInfo.userOrgnTypeCd}";
var stdrYm = new Date().format("yyyymm");
var DHX = {
	// 조회조건
	FORM1 : {
		items : [
			{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
			,{type:"block", list:[
				{type:"calendar", dateFormat:"%Y-%m", serverDateFormat:"%Y%m", name:"crtYm", label:"추출월", value:stdrYm, calendarPosition: "bottom", width:100, offsetLeft:10, required:true}
				,{type:"newcolumn"}
				,{type:"input", label:"개통대리점", name: "openAgntCd", value:"", width:100, offsetLeft:40}
				,{type:"newcolumn"}
				,{type:"button", value:"찾기", name:"btnOrgFind", disabled:false}
				,{type:"newcolumn"}
				,{type:"input", name: "openAgntNm", value:"", width:150}
// 				,{type:"newcolumn"}
// 				,{type:"select", width:100, label:"마케팅동의", name:"mktAgrmYn", userdata:{lov:"RCP0067"}, offsetLeft:30}
			]}
			,{type:"block", list:[
// 				{type:"select", label:"검색구분", name: "srchTp", width:100, userdata:{lov:"RCP0061"}, offsetLeft:10}
				{type:"select", label:"검색구분", name: "srchTp", width:100, offsetLeft:10}
				,{type:"newcolumn"}
				,{type:"input", name: "srchVal", width:150}
				,{type:"newcolumn"}
// 				,{type:"select", width:100, label:"TM상태",name:"tmStatCd", userdata:{lov:"RCP0066"}, offsetLeft:90}
				,{type:"select", width:100, label:"TM상태",name:"tmStatCd", offsetLeft:90}
				,{type:"newcolumn"}
// 				,{type:"select", width:100, label:"고객의사",name:"tmRsltCd", userdata:{lov:"RCP0069"}, offsetLeft:30}
				,{type:"select", width:100, label:"고객의사",name:"tmRsltCd", offsetLeft:30}
			]}
			, {type: "button", value: "조회", name: "btnSearch" , className:"btn-search2"}
		]
		, onValidateMore : function(data){
			if(data.srchTp == "" && data.srchVal != ""){
				this.pushError("srchTp", "검색구분", "검색구분을 선택하세요.");
			}
			if(data.srchTp != "" && data.srchVal == ""){
				this.pushError("srchVal", "검색어", "검색어를 입력하세요.");
			}
		}
		,onButtonClick : function(btnId) {
			var form = this;
			
			switch (btnId) {
				case "btnSearch":
					PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getDvcChgTrgtList.json", form, {
						callback:function(){
							PAGE.FORM3.clear();
						}
					});
					
					break;
				//대리점조회
				case "btnOrgFind":
					mvno.ui.codefinder("ORG", function(result) {
						form.setItemValue("openAgntCd", result.orgnId);
						form.setItemValue("openAgntNm", result.orgnNm);
					});
					break;
			}
		}
		, onChange : function(name, value) {//onChange START
			// 검색구분
			if(name == "srchTp") {
				PAGE.FORM1.setItemValue("srchVal", "");
			}
		}
	}
	//목록
	,GRID1 : {
		css : {
			height : "240px"
		}
		,title : "조회결과"
		,header : "추출월,계약번호,CTN,개통일자,계약상태,TM상태,상담원,고객명,개통대리점,단말모델,할부원금,할부개월수,잔여할부개월수,약정개월수,잔여약정개월수,보증보험관리번호,미납여부,마케팅동의여부" //16
		,columnIds : "crtYm,contractNum,subscriberNoMask,openDt,subStatusNm,tmStatNm,vocPrsnNm,custNmMask,openAgntNm,prdtCode,instAmnt,instMnthCnt,remainCnt,agrmMnthCnt,remainAgrmCnt,grntInsrMngmNo,unpdYn,mktAgrmYn"//
		,colAlign : "center,center,center,center,center,center,center,left,left,left,right,right,right,right,right,center,center,center"
		,colTypes : "roXdm,ro,roXp,roXd,ro,ro,ro,ro,ro,ro,ron,ron,ron,ron,ron,ro,ro,ro" //16
		,colSorting : "str,str,str,str,str,str,str,str,str,str,int,int,int,int,int,str,str,str" //16
		,widths : "80,100,100,80,80,80,80,120,150,100,80,80,100,80,100,120,80,100" //16
		,multiCheckbox : true
		,paging : true
		,showPagingAreaOnInit: true
		,pageSize : 100
		,buttons : {
// 			hright : {
// 				btnExcel:{ label:"엑셀다운로드"}
// 			}
// 			,right:{
// 				btnAsgn:{label:"할당"}
// 			}
		}
		,onRowSelect : function(rowId, celInd) {
			var rowData = this.getRowData(rowId);
			
			PAGE.FORM3.setFormData(rowData);
		}
		,onButtonClick : function(btnId, selectedData) {
			var form = this;
			switch (btnId) {
// 				case "btnExcel" : //엑셀다운로드 버튼 클릭시
// 					if(PAGE.GRID1.getRowsNum() == 0) {
// 						mvno.ui.alert("데이터가 존재하지 않습니다.");
// 						return;
// 					}
// 					var searchData =  PAGE.FORM1.getFormData(true);
// 					mvno.cmn.download(ROOT + "/rcp/rcpMgmt/getDvcChgTrgtListExcel.json?menuId=MSP1003010",true,{postData:searchData});
					
// 					break;
// 				case "btnAsgn" :
// 					if(PAGE.GRID1.getRowsNum() == 0) {
// 						mvno.ui.alert("데이터가 존재하지 않습니다.");
// 						return;
// 					}
					
// 					var url = "/rcp/rcpMgmt/setDvcChgTrgtAsgnList.json";
					
// 					var searchData =  PAGE.FORM1.getFormData(true);
					
// 					if(mvno.ui.confirm("상담원에게 할당하시겠습니까?", function(){
// 						mvno.cmn.ajax(ROOT + url, searchData, function(result) {
// 							mvno.ui.alert("할당을 진행합니다.<br/>대상건수에 따라서 시간이 소요됩니다.");
// 						});
// 					}));
// 					break;
			}
		}
	}
	// 상담원할당
	,FORM2: {
		items :[
			{type:"select", label:"상담원", name:"vocPrsnId", width:100, labelWidth:50, offsetLeft:730}
			,{type:"newcolumn"}
			,{type:"button", value:"할당", name:"btnAsgn"}
		]
		,onButtonClick : function(btnId) {
			var form = this;
			switch (btnId) {
				case "btnAsgn" :
					var rowIds = PAGE.GRID1.getCheckedRows(0); // 선택된 checkbox 의 rowId 를 가져온다.
					
					if (! rowIds) {
						mvno.ui.alert("ECMN0003");
						return;
					}
					
					if(form.getItemValue("vocPrsnId") == ""){
						mvno.ui.alert("상담원을 선택하세요");
						return;
					}
					
					// TM상태 확인
					var arrData = PAGE.GRID1.getCheckedRowData();
					for(var i=0; i<arrData.length; i++){
						if(Number(arrData[i].tmStatCd) > 30){
							mvno.ui.alert("할당이 불가능한 대상이 존재합니다");
							return;
						}
					}
					
					var checkedData = PAGE.GRID1.classifyRowsFromIds(rowIds, "crtYm,contractNum");
					var sData = {"vocPrsnId" : form.getItemValue("vocPrsnId")}
					sData.items = checkedData.ALL;
					
					mvno.ui.confirm("할당을 진행하시겠습니까?", function(){
						mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/setDvcChgTrgtAsgnList2.json", sData, function() {
							mvno.ui.notify("NCMN0006");
							// 그리드 및 하단 폼 초기화
							PAGE.GRID1.refresh();
							
							// 헤더의 체크박스 초기화
							$('#GRID1 input:checkbox').prop('checked',false);
						});
					});
					
					break;
			}
		}
	}
	//상세보기
	,FORM3 : {
		items : [
			{type: "settings", position: "label-left", labelAlign:"left", labelWidth: "120"}
			,{type: "block", list: [
				{type:"input", name:"contractNum", label:"가입계약번호", width:120, offsetLeft:40, readonly:true}
				,{type:"newcolumn"}
				,{type:"input", name:"svcCntrNo", label:"서비스계약번호", width:120, offsetLeft:60, readonly:true}
				,{type:"newcolumn"}
				,{type:"hidden", name:"subscriberNo"}
				,{type:"input", name:"subscriberNoMask", label:"휴대폰번호", width:120, offsetLeft:60, readonly:true}
			]}
			,{type: "block", list: [
				{type:"input", name:"subStatusNm", label:"계약상태", width:120, offsetLeft:40, readonly:true}
				,{type:"newcolumn"}
				,{type:"hidden", name:"custNm"}
				,{type:"input", name:"custNmMask", label:"고객명", width:120, offsetLeft:60, readonly:true}
				,{type:"newcolumn"}
				,{type:"input", name:"custTpNm", label:"고객유형", width:120, offsetLeft:60, readonly:true}
			]}
			,{type: "block", list: [
				{type:"input", name:"openAgntNm", label:"개통대리점", width:120, offsetLeft:40, readonly:true}
				,{type:"newcolumn"}
				,{type:"input", name:"prdtCode", label:"단말모델", width:120, offsetLeft:60, readonly:true}
				,{type:"newcolumn"}
				,{type:"input", name:"instAmt", label:"할부원금", width:120, offsetLeft:60, readonly:true, userdata:{align:"right"}, numberFormat:"000,000,000"}
			]}
			,{type: "block", list: [
// 				{type:"input", name:"remainAmt", label:"할부잔액", width:120, offsetLeft:40, readonly:true, userdata:{align:"right"}, numberFormat:"000,000,000"}
// 				,{type:"newcolumn"}
				{type:"input", name:"remainCnt", label:"잔여할부개월수", width:120, offsetLeft:40, readonly:true}
				,{type:"newcolumn"}
				,{type:"input", name:"remainAgrmCnt", label:"잔여약정개월수", width:120, offsetLeft:60, readonly:true}
				,{type:"newcolumn"}
				,{type:"input", name:"mktAgrmYn", label:"마케팅동의", width:120, offsetLeft:60, readonly:true}
			]}
			,{type: "block", list: [
				{type:"input", name:"grntInsrMngmNo", label:"보증보험번호", width:120, offsetLeft:40, readonly:true}
				,{type:"newcolumn"}
				,{type:"input", name:"unpdYn", label:"미납여부", width:120, offsetLeft:60, readonly:true}
			]}
			,{type: "block", list: [
				{type:"input", name:"tmStatNm", label:"TM상태", width:120, offsetLeft:40, readonly:true}
				,{type:"newcolumn"}
				,{type:"input", name:"tmRsltNm", label:"고객의사", width:120, offsetLeft:60, readonly:true}
				,{type:"newcolumn"}
				,{type:"input", name:"vocRcpId", label:"상담ID", width:120, offsetLeft:60, readonly:true}
			]}
// 			,{type: "block", list: [
// 				{type:"input", name:"cnslMstNm", label:"대분류", width:120, offsetLeft:40, readonly:true}
// 				,{type:"newcolumn"}
// 				,{type:"input", name:"cnslMidNm", label:"중분류", width:120, offsetLeft:60, readonly:true}
// 				,{type:"newcolumn"}
// 				,{type:"input", name:"cnslDtlNm", label:"소분류", width:120, offsetLeft:60, readonly:true}
// 			]}
			,{type: "block", list: [
				{type:"input", name:"tmCntt", label:"TM결과", rows:3, width:728, offsetLeft:40, readonly:true}
			]}
		]
	}
}; // end dhx

var PAGE = {
	title: "${breadCrumb.title}",
	breadcrumb: "${breadCrumb.breadCrumb}",
	buttonAuth: ${buttonAuth.jsonString},
	onOpen:function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		mvno.ui.createForm("FORM2");
		mvno.ui.createForm("FORM3");
		
		//대리점찾기 버튼
		if(${loginInfo.userOrgnTypeCd} != 10) {
			PAGE.FORM1.disableItem("btnOrgFind");
			PAGE.FORM1.setReadonly("agntCd",true);
			PAGE.FORM1.setReadonly("agntNm",true);
		}
		// 상담원
		mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0018"}, PAGE.FORM2, "vocPrsnId");
		PAGE.FORM1.setItemValue("mktAgrmYn", "Y");
		
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9013"}, PAGE.FORM1, "srchTp"); // 검색구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0066"}, PAGE.FORM1, "tmStatCd"); // TM상태
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0069"}, PAGE.FORM1, "tmRsltCd"); // 고객의사
	}
};

</script>