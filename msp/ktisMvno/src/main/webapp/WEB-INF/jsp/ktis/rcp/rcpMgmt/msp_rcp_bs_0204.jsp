<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_rcp_bs_0204.jsp
	 * @Description : 상담할당조회
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

// 초기값 세팅
var usrOrgnId = "${loginInfo.userOrgnId}";
var usrId = "${loginInfo.userId}";
var usrNm = "${loginInfo.userName}";
var stdrYm = new Date().format("yyyymm");

var authChk = "${authCheck}";
if(authChk == null || authChk == "") authChk = "N";

var DHX = {
	// 조회조건
	FORM1 : {
		items : [
			{type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0}
			,{type:"block", list:[
				{type:"calendar", dateFormat:"%Y-%m", serverDateFormat:"%Y%m", name:"crtYm", label:"추출월", value:stdrYm, calendarPosition: "bottom", width:80, offsetLeft:10, required:true}
				,{type:"newcolumn"}
				,{type:"input", label:"상담원", name: "vocPrsnId", value:usrId, width:80, offsetLeft:10}
				,{type:"newcolumn"}
				,{type:"button", value:"찾기", name:"btnUserFind", disabled:false}
				,{type:"newcolumn"}
				,{type:"input", name: "vocPrsnNm", value:usrNm, width:80}
				,{type:"newcolumn"}
// 				,{type:"select", width:90, label:"TM상태",name:"tmStatCd", userdata:{lov:"RCP0066"}, offsetLeft:20}
				,{type:"select", width:90, label:"TM상태",name:"tmStatCd", offsetLeft:20}
				,{type:"newcolumn"}
// 				,{type:"select", label:"검색구분", name: "srchTp", width:90, userdata:{lov:"RCP0061"}, offsetLeft:10}
				,{type:"select", label:"검색구분", name: "srchTp", width:90, offsetLeft:10}
				,{type:"newcolumn"}
				,{type:"input", name: "srchVal", width:90}
			]}
			, {type: "button", value: "조회", name: "btnSearch" , className:"btn-search1"}
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
					PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getDvcChgCnslList.json", form, {
						callback:function(){
							PAGE.FORM2.clear();
						}
					});
					
					break;
				//대리점조회
				case "btnUserFind":
					mvno.ui.codefinder("USER", function(result) {
						form.setItemValue("vocPrsnId", result.usrId);
						form.setItemValue("vocPrsnNm", result.usrNm);
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
			height : "270px"
		}
		,title : "조회결과"
		,header : "추출월,계약번호,CTN,개통일자,계약상태,TM상태,고객명,개통대리점,단말모델,할부원금,할부개월수,잔여할부개월수,약정개월수,잔여약정개월수,보증보험관리번호,미납여부,마케팅동의여부"
		,columnIds : "crtYm,contractNum,subscriberNoMask,openDt,subStatusNm,tmStatNm,custNmMask,openAgntNm,prdtCode,instAmnt,instMnthCnt,remainCnt,agrmMnthCnt,remainAgrmCnt,grntInsrMngmNo,unpdYn,mktAgrmYn"//
		,colAlign : "center,center,center,center,center,center,left,left,left,right,center,center,center,center,center,center,center"
		,colTypes : "roXdm,ro,roXp,roXd,ro,ro,ro,ro,ro,ron,ron,ron,ron,ron,ro,ro,ro"
		,colSorting : "str,str,str,str,str,str,str,str,str,int,int,int,int,int,str,str,str"
		,widths : "80,100,100,80,80,80,120,150,100,80,80,100,80,100,120,80,100"
		,paging: true
		,showPagingAreaOnInit: true
		,pageSize:20
		,buttons : {
			
		}
		,onRowSelect : function(rowId, celInd) {
			var rowData = this.getRowData(rowId);
			
			// 완료 이후 세팅
			if(Number(rowData.tmStatCd) >= 30){
				console.log(Number(rowData.tmStatCd));
				PAGE.FORM2.disableItem("btnRcp");
				PAGE.FORM2.disableItem("btnSave");
			}else{
				PAGE.FORM2.enableItem("btnRcp");
				PAGE.FORM2.enableItem("btnSave");
			}
			
			PAGE.FORM2.setFormData(rowData);
			PAGE.FORM2.setItemValue("orgTmStatCd", rowData.tmStatCd);
			
			// 권한자인 경우 비활성화 해제
		}
	}
	//상세보기
	,FORM2 : {
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
// 				{type:"select", name:"tmStatCd", label:"TM상태", width:120, offsetLeft:40, userdata:{lov:"RCP0066"}}
				{type:"select", name:"tmStatCd", label:"TM상태", width:120, offsetLeft:40}
				,{type:"newcolumn"}
// 				,{type:"select", name:"tmRsltCd", label:"고객의사", width:120, offsetLeft:60, userdata:{lov:"RCP0069"}}
				,{type:"select", name:"tmRsltCd", label:"고객의사", width:120, offsetLeft:60}
				,{type:"newcolumn"}
				,{type:"input", name:"vocRcpId", label:"상담ID", width:120, offsetLeft:60, readonly:true}
			]}
			,{type: "block", list: [
// 				{type:"select", name:"dvcChgRsnCd", label:"기변사유", width:120, offsetLeft:40, userdata:{lov:"VOC0002"}}
				{type:"select", name:"dvcChgRsnCd", label:"기변사유", width:120, offsetLeft:40}
				,{type:"newcolumn"}
// 				,{type:"select", name:"dvcChgRsnDtlCd", label:"상세사유", width:120, offsetLeft:60, userdata:{lov:"VOC0003"}}
				,{type:"select", name:"dvcChgRsnDtlCd", label:"상세사유", width:120, offsetLeft:60}
			]}
			,{type: "block", list: [
				{type:"input", name:"tmCntt", label:"TM결과", rows:3, width:728, offsetLeft:40, maxLength:1300}
			]}
			,{type:"hidden", name:"crtYm"}
			,{type:"hidden", name:"orgTmStatCd"}
		]
		,buttons : {
			left : {
				btnRcp:{label:"기변신청"}
			}
			, right : {
				btnSave:{label:"저장"}
			}
		}
		,onValidateMore : function (data) {
			var form = this;
			
		}
		,onChange:function(name, value){
			var form = this;
			
			switch(name){
// 				case "cnslMstCd" :
// 					// 중분류 조회
// 					if(value == ""){
// 						mvno.ui.reloadLOV(PAGE.FORM2, "cnslMidCd", [{text: "- 선택 -", value: "", selected: true}]);
// 						mvno.ui.reloadLOV(PAGE.FORM2, "cnslDtlCd", [{text: "- 선택 -", value: "", selected: true}]);
// 					}
// 					else {
// 						mvno.cmn.ajax4lov( ROOT + "/voc/vocMgmt/getVocCommon.json", {grpId:"VOC0002",pGrpId:value}, PAGE.FORM2, "cnslMidCd");
// 						mvno.ui.reloadLOV(PAGE.FORM2, "cnslDtlCd", [{text: "- 선택 -", value: "", selected: true}]);
// 					}
// 					break;
// 				case "cnslMidCd" :
// 					// 소분류 조회
// 					if(value == ""){
// 						mvno.ui.reloadLOV(PAGE.FORM2, "cnslDtlCd", [{text: "- 선택 -", value: "", selected: true}]);
// 					}
// 					else {
// 						mvno.cmn.ajax4lov( ROOT + "/voc/vocMgmt/getVocCommon.json", {grpId:"VOC0003",pGrpId:value}, PAGE.FORM2, "cnslDtlCd");
// 					}
// 					break;
// 				case "cnslMidCd" :
// 					// 소분류 조회
// 					if(value != ""){
// 						var dtlStr = PAGE.FORM2.getItemValue(name);
// 						// 중분류
// 						PAGE.FORM2.setItemValue("cnslMidCd", dtlStr.substring(0,3));
// 						// 대분류
// 						PAGE.FORM2.setItemValue("cnslMstCd", dtlStr.substring(0,2));
// 					}
// 					else{
						
// 					}
// 					break;
			}
			
		}
		,onButtonClick:function(btnId){
			var form = this;
			
			switch (btnId) {
				
				case "btnSave":
					// 완료상태에서는 권한 체크
					console.log("orgTmStatCd=" + form.getItemValue("orgTmStatCd"));
					
					if(Number(form.getItemValue("orgTmStatCd") >= 30) && authChk != "Y"){
						mvno.ui.alert("TM상태 변경후 저장가능합니다.(권한자)");
						return;
					}
					
					// 완료시 메세지
					if(Number(form.getItemValue("tmStatCd")) >= 30 && authChk != "Y"){
						mvno.ui.alert("기변성공/실패/통화실패/OB금지 변경 후 권한자 외 수정이 불가합니다.");
						return;
					}
					
					mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/updateDvcChgTmRslt.json", form, function(result) {
						mvno.ui.notify("NCMN0002");
						PAGE.GRID1.refresh();
						PAGE.FORM2.clear();
					});
					
					break;
				case "btnRcp":
// 					var contractNum = form.getItemValue("contractNum");
					var subscriberNo = form.getItemValue("subscriberNo");
					var custNm = form.getItemValue("custNm");
					
					if(subscriberNo == "" || custNm == ""){
						mvno.ui.alert("대상 고객을 선택하세요");
						return;
					}
					
					//기변신청
					if(Number(form.getItemValue("orgTmStatCd")) >= 30){
						mvno.ui.alert("TM상태 변경후 기변신청가능합니다.(권한자)");
						return;
					}
					
					if(!mvno.ui.confirm("기변신청을 진행하시겠습니까?", function(){
						// v2017.02 신청관리 간소화 신청등록(신규) 호출
						var url = "/rcp/rcpMgmt/getRcpNewMgmt.do";
						var menuId = "MSP1000014";
						
						var param = "?menuId=" + menuId + "&custNm=" + custNm + "&subscriberNo=" + subscriberNo + "&trgtMenuId=MSP1003020";
						
						var myTabbar = window.parent.mainTabbar;
						
						if (myTabbar.tabs(url)) {
							myTabbar.tabs(url).setActive();
						}else{
							myTabbar.addTab(url, "신청등록", null, null, true);
						}
						
						myTabbar.tabs(url).attachURL(url + param);
						
/* 						
						var url = "/rcp/rcpMgmt/dvcChgRcpForm.do";
						
						var title = "기변신청등록"
						var menuId = "MSP1010101";
						
						var param = "?menuId=" + menuId + "&custNm=" + custNm + "&subscriberNo=" + subscriberNo;
						
						var myTabbar = window.parent.mainTabbar;
						
						if (myTabbar.tabs(url)) {
							myTabbar.tabs(url).close(); // 기존에 있는 탭을 닫기
						}
						
						myTabbar.addTab(url, title, null, null, true);
						myTabbar.tabs(url).attachURL(url + param);
						 */
						// 잘안보여서.. 일단 살짝 Delay 처리 
						setTimeout(function() {
							//mainLayout.cells("b").progressOff();
						}, 100);
					}));
					break;
			}
		}
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
		
// 		mvno.ui.reloadLOV(PAGE.FORM2, "cnslMidCd", [{text: "- 선택 -", value: "", selected: true}]);
// 		mvno.ui.reloadLOV(PAGE.FORM2, "cnslDtlCd", [{text: "- 선택 -", value: "", selected: true}]);
		
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0066"}, PAGE.FORM1, "tmStatCd"); // TM상태
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9013"}, PAGE.FORM1, "srchTp"); // 검색구분

		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0066"}, PAGE.FORM2, "tmStatCd"); // TM상태
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0069"}, PAGE.FORM2, "tmRsltCd"); // 고객의사
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0002",etc1:"HA"}, PAGE.FORM2, "dvcChgRsnCd"); // 기변사유
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0051"}, PAGE.FORM2, "dvcChgRsnDtlCd"); // 상세사유
		
		if(usrOrgnId != "1"){
			PAGE.FORM1.disableItem("btnUserFind");
		}
	}
};

</script>