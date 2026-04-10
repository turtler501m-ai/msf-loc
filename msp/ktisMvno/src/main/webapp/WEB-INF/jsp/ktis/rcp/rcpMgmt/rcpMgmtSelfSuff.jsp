<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
	/**
	 * @Class Name : rcpMgmtSelfSuff.jsp
	 * @Description : 자급제폰 신청 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2022.03.07 박준성 최초생성
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>
<div id="FORM3" class="section-box"></div>

   
<div id="POPUP1">
	<div id="POPUP1TOP"  class="section-full"></div>
	<div id="POPUP1MID" class="section-box"></div>
</div>
<div id="POPUP2">
	<div id="POPUP2TOP"  class="section-full"></div>
	<div id="POPUP2MID" class="section-box"></div>
</div>

<!-- Script -->
<script type="text/javascript">
var maskingYn = "${maskingYn}";

var DHX = {
	FORM1 : {
		items : [
			{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
			{type:"block", list:[
				{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"신청일자", value:"${strtDt}", calendarPosition:"bottom", readonly:true, width:100, offsetLeft:10, required:true},
				{type:"newcolumn"},
				{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~", value:"${endDt}", calendarPosition:"bottom", readonly:true, labelAlign:"center", labelWidth:10, width:100, offsetLeft:5},
				{type:'newcolumn'},
				{type:"select", label:"단말진행상태",name:"paramPhoneStateCode", width:120, offsetLeft:40, labelWidth:100},
				{type:"newcolumn"},
				{type:"select", label:"유심진행상태",name:"paramUsimStateCode", width:120, offsetLeft:40, labelWidth:100}
			]},
			{type:"block", list:[
				{type:"block", list:[
  					{type:"select", label:"검색구분", name:"searchCd", width:100, offsetLeft:10},
  					{type:"newcolumn"},
  					{type:"input", name:"searchVal", width:115}
  				]},
				{type:"newcolumn"},
				{type:"select", label:"신청서상태",name:"paramPstate", width:120, offsetLeft:40, labelWidth:100}
			]},
			{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"},
			//버튼 여러번 클릭 막기 변수
			{type : 'hidden', name: "btnCount1", value:"0"},
			{type : 'hidden', name: "btnExcelCount1", value:"0"},
			{type : 'hidden', name: "DWNLD_RSN", value:""}, //엑셀다운로드 사유  추가
		],
		onButtonClick : function(btnId) {
			var form = this;
			
			switch (btnId) {
				case "btnSearch":
					
					var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
					PAGE.FORM1.setItemValue("btnCount1", btnCount2)
					if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") {
						return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기
					}
					PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpMgmtListSelfSuff.json", form, {
						callback : function () {
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						}
					});
					
					break;
			}
		},
		onChange : function() {
			if(this.getItemValue("searchCd") == "" || this.getItemValue("searchCd") == null) {
				this.setItemValue("searchVal", "");
			}
		},
		onValidateMore : function (data){
			if((data.strtDt && !data.endDt) || (!data.strtDt&& data.endDt)){
				this.pushError("data.searchStartDt","신청일자","일자를 선택하세요");
				PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
			}
			
			if(data.strtDt > data.endDt){
				this.pushError("data.searchStartDt","신청일자",["ICMN0004"]);
				PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
			}
			
			if( data.searchCd != "" && data.searchVal.trim() == ""){
				this.pushError("searchVal", "검색어", "검색어를 입력하세요");
				PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
				PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
			}
		}
	},
	//신청관리 리스트
	GRID1 : {
		css : {
				height : "500px"
		},
		title : "조회결과",
		header : "단말진행상태,유심진행상태,신청서상태,예약번호,고객명,생년월일,휴대폰번호,대리점,요금제,업무구분,신청일자,모집경로,유입경로,sysRdate,requestKey",
		columnIds : "phoneStateName,usimStateName,pstateName,resNo,cstmrName2,birthDt,cstmrMobile,agentName,socName,operName,reqInDt,onOffNm,openMarketReferer,sysRdate,requestKey",
		colAlign : "center,center,center,center,left,center,center,left,left,center,center,center,center,left,center",
		colTypes : "ro,ro,ro,ro,ro,roXd,roXp,ro,ro,ro,roXd,ro,ro,ro,ro",
		colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
		widths : "100,100,100,70,100,80,120,200,200,80,80,80,80,120,80",
		hiddenColumns: [13,14],
		paging: true,
		pageSize:20,
		buttons : {
			hright : {
				btnDnExcel : { label : "엑셀다운로드" }
			},
			
			left : {
				btnDnExcelUp : { label : "엑셀업로드양식"},
			},
			
			right : {
				btnDlvryNo : { label : "송장번호등록"},
				btnDlvryOk : { label : "배송완료등록"},
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
					if(PAGE.GRID1.getRowsNum() == 0) {
						mvno.ui.alert("데이터가 존재하지 않습니다.");
					} else {
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download("/rcp/rcpMgmt/getSelfSuffListByExcel.json?menuId=MSP1010033", true, {postData:searchData});
					}
					break;
					
				case 'btnDtl' : //Row 더블클릭시 Start
					mvno.ui.createForm("FORM2");
				
					PAGE.FORM2.clear();
					
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM2, "phoneStateCode"); // 단말진행상태
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM2, "usimStateCode"); // 유심진행상태
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0005"}, PAGE.FORM2, "pstate"); // 유심진행상태
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM2, "dlvryTelFn"); // 전화번호
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM2, "dlvryMobileFn"); // 휴대폰
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0025"}, PAGE.FORM2, "phoneTbCd"); // 단말택배사
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0025"}, PAGE.FORM2, "tbCd"); // 유심택배사
					
					//PAGE.FORM2.setFormData(selectedData, true);
					
					if (selectedData == null) {
						break;
					} else {
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRcpMgmtListSelfSuffDtl.json", {"requestKey":selectedData.requestKey}, function(resultData) {
							var data = resultData.result.data.rows[0];
							PAGE.FORM2.setFormData(data);
						});
					}	

					if (maskingYn == "Y") {
						mvno.ui.showButton("FORM2" , "btnSave");
						mvno.ui.hideButton("FORM2" , "btnSaveMask");
					} else {
						PAGE.FORM2.disableItem("phoneStateCode");
						PAGE.FORM2.disableItem("reqPhoneSn");
						PAGE.FORM2.disableItem("usimStateCode");
						PAGE.FORM2.disableItem("reqUsimSn");
						PAGE.FORM2.disableItem("requestMemo");
						PAGE.FORM2.disableItem("dlvryName");
						PAGE.FORM2.disableItem("dlvryTelFn");
						PAGE.FORM2.disableItem("dlvryTelMn");
						PAGE.FORM2.disableItem("dlvryTelRn");
						PAGE.FORM2.disableItem("dlvryMobileFn");
						PAGE.FORM2.disableItem("dlvryMobileMn");
						PAGE.FORM2.disableItem("dlvryMobileRn");
						PAGE.FORM2.disableItem("dlvryPost");
						PAGE.FORM2.disableItem("btnDlvryPostFind");
						PAGE.FORM2.disableItem("dlvryAddr");
						PAGE.FORM2.disableItem("dlvryAddrDtl");
						PAGE.FORM2.disableItem("dlvryMemo");
						PAGE.FORM2.disableItem("phoneTbCd");
						PAGE.FORM2.disableItem("phoneDlvryNo");
						PAGE.FORM2.disableItem("tbCd");
						PAGE.FORM2.disableItem("dlvryNo");
						
						mvno.ui.hideButton("FORM2" , "btnSave");
						mvno.ui.showButton("FORM2" , "btnSaveMask");
					}

					mvno.ui.popupWindowById("FORM2", "상세화면", 850, 650, {
						onClose: function(win) {
							if (! PAGE.FORM2.isChanged()) return true;
							mvno.ui.confirm("CCMN0005", function(){
								win.closeForce();
							});
						}
					});

					break;

				case "btnDnExcelUp":
					
					mvno.ui.createForm("FORM3");
					PAGE.FORM3.clear();
					
					mvno.ui.popupWindowById("FORM3", "엑셀업로드양식", 400, 240, {
						onClose: function(win) {
							if (! PAGE.FORM3.isChanged()) return true;
							mvno.ui.confirm("CCMN0005", function(){
								win.closeForce();
							});
						}
					});
					
					break;
					
				case "btnDlvryNo":
					
					mvno.ui.createGrid("POPUP1TOP");
					mvno.ui.createForm("POPUP1MID");
					
					PAGE.POPUP1TOP.clearAll();
					PAGE.POPUP1MID.clear();
					
					PAGE.POPUP1MID.setFormData({});
					
					var fileName;
					
					PAGE.POPUP1MID.attachEvent("onUploadFile", function(realName, serverName) {
						fileName = serverName;
					});
					
					PAGE.POPUP1MID.attachEvent("onUploadComplete", function(count){
						
						var url = ROOT + "/rcp/rcpMgmt/getSelfSuffDlvryNoList.json";
						var userOptions = {timeout:180000};
						
						mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
							var rData = resultData.result;
							
							PAGE.POPUP1TOP.clearAll();
							
							PAGE.POPUP1TOP.parse(rData.data.rows, "js");
							
						}, userOptions);
					});
					
					PAGE.POPUP1MID.attachEvent("onFileRemove",function(realName, serverName){
						PAGE.POPUP1TOP.clearAll();
						
						return true;
					});
					
					var uploader = PAGE.POPUP1MID.getUploader("file_upload1");
					uploader.revive();
					
					mvno.ui.popupWindowById("POPUP1", "송장번호등록", 405, 490, {
						onClose2: function(win) {
							uploader.reset();
						}
					});
										
					break;
					
				case "btnDlvryOk":
					
					mvno.ui.createGrid("POPUP2TOP");
					mvno.ui.createForm("POPUP2MID");
					
					PAGE.POPUP2TOP.clearAll();
					PAGE.POPUP2MID.clear();
					
					PAGE.POPUP2MID.setFormData({});
					
					var fileName;
					
					PAGE.POPUP2MID.attachEvent("onUploadFile", function(realName, serverName) {
						fileName = serverName;
					});
					
					PAGE.POPUP2MID.attachEvent("onUploadComplete", function(count){
						
						var url = ROOT + "/rcp/rcpMgmt/getSelfSuffDlvryOkList.json";
						var userOptions = {timeout:180000};
						
						mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
							var rData = resultData.result;
							
							PAGE.POPUP2TOP.clearAll();
							
							PAGE.POPUP2TOP.parse(rData.data.rows, "js");
							
						}, userOptions);
					});
					
					PAGE.POPUP2MID.attachEvent("onFileRemove",function(realName, serverName){
						PAGE.POPUP2TOP.clearAll();
						
						return true;
					});
					
					var uploader = PAGE.POPUP2MID.getUploader("file_upload1");
					uploader.revive();
					
					mvno.ui.popupWindowById("POPUP2", "배송완료등록", 405, 490, {
						onClose2: function(win) {
							uploader.reset();
						}
					});
					
					break;
				
			}
		}
	},
	// 신청서조회
	FORM2 : {
		items : [
			{type:"block", list:[
				{type:"settings", position:"label-left", labelAlign:"right", labelWidth:70, blockOffset:0},
				// 고객정보
				{type: "fieldset", label: "고객정보", list:[
// 					{type:"settings"},
					{type:"block", list:[
						{type:"input",  label:"고객명", name:"cstmrName", width:100, readonly:true, disabled:true},
						{type:"newcolumn"},
						{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"reqInDt", label:"신청일자", calendarPosition:"bottom", width:100, offsetLeft:5, disabled:true},
						{type:"newcolumn"},
						{type:"input", label:"예약번호",name:"resNo", width:100, offsetLeft:5, readonly:true, disabled:true},
						{type:"newcolumn"},
						{type:"select", label:"신청서상태", name:"pstate", width:100, offsetLeft:5}
					]}
				]},
				// 신청정보(단말)
				{type:"fieldset", label:"신청정보(단말)", list:[
// 					{type:"settings"},
					{type:"block", list:[
						{type:"select", label:"진행상태", name:"phoneStateCode", width:100}
					]},
					{type:"block", list:[
						{type:"input", label:"단말모델", name:"prdtCode", width:100, readonly:true, disabled:true},
						{type:"newcolumn"},
						{type:"input", label:"", name:"modelName", width:100, readonly:true, disabled:true},
						{type:"newcolumn"},
						{type:"input", label:"", name:"reqModelColor", width:55, readonly:true, disabled:true},
						{type:"newcolumn"},
						{type:"input", label:"단말일련번호", name:"reqPhoneSn", width:80, offsetLeft:20, labelWidth:80, maxLength:7}						
					]},
					{type:"block", list:[
						{type:"input", label:"출고가", name:"hndsetSalePrice", width:80, numberFormat:"0,000,000,000", readonly:true, disabled:true},
						{type:"newcolumn"},
						{type:"input", label:"즉시할인", name:"cardDcAmt", width:80, offsetLeft:30, numberFormat:"0,000,000,000", readonly:true, disabled:true},
						{type:"newcolumn"},
						{type:"input", label:"포인트", name:"usePoint", width:80, offsetLeft:30, numberFormat:"0,000,000,000", readonly:true, disabled:true},
						{type:"newcolumn"},
						{type:"input", label:"결제금액", name:"totAmt", width:80, offsetLeft:30, numberFormat:"0,000,000,000", readonly:true, disabled:true}
					]},
				]},
				// 신청정보(유심)
				{type:"fieldset", label:"신청정보(유심)", list:[
					{type:"settings"},
					{type:"block", list:[
						{type:"select", label:"진행상태", name:"usimStateCode", width:100}
					]},
					{type:"block", list:[
						{type:"input", label:"유심모델", name:"usimName", width:100, readonly:true, disabled:true},
						{type:"newcolumn"},
						{type:"input", label:"재품명", name:"usimId", width:150, readonly:true, disabled:true},
						{type:"newcolumn"},
						{type:"input", label:"유심번호", name:"reqUsimSn", width:150, maxLength:20},
						{type:"hidden", name:"oldUsimSn"}
					]},
					{type:"block", list:[
						{type:"input", label:"메모", name:"requestMemo", width:550, maxLength:200}
					]}
				]},
				// 배송정보
				{type: "fieldset", label: "배송정보", list:[
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:85, blockOffset:0},
					{type:"block", list:[
						{type:"input", label:"받으시는 분", name:"dlvryName", width:100, maxLength:20},
						{type:"newcolumn"},
						{type:"select", label:"전화번호", name:"dlvryTelFn", width:60, offsetLeft:10},
						{type:"newcolumn"},
						{type:"input", label:"", name:"dlvryTelMn", width:40, maxLength:4},
						{type:"newcolumn"},
						{type:"input", label:"", name:"dlvryTelRn", width:40, maxLength:4},
						{type:"newcolumn"},
						{type:"select", label:"휴대폰", name:"dlvryMobileFn", width:60, offsetLeft:10},
						{type:"newcolumn"},
						{type:"input", label:"", name:"dlvryMobileMn", width:40, maxLength:4},
						{type:"newcolumn"},
						{type:"input", label:"", name:"dlvryMobileRn", width:40, maxLength:4}
					]},
					{type:"block", list:[
						{type:"input", label:"우편번호", name:"dlvryPost", width:75, readonly:true, maxLength:6},
						{type:"newcolumn"},
						{type:"button", value:"주소찾기", name :"btnDlvryPostFind"},
						{type:"newcolumn"},
						{type:"input", label:"", name:"dlvryAddr", width:400, readonly:true},
					]},
					{type:"block", list:[
						{type:"input", label:"상세주소", name:"dlvryAddrDtl", width:555, maxLength:80},
					]},
					{type:"block", list:[
						{type:"input", label:"요청사항",name:"dlvryMemo", width:555, maxLength:200}
					]},
					{type:"block", list:[
						{type:"select", label:"단말택배사", name:"phoneTbCd", width:100},
						{type:"newcolumn"},
						{type:"input", label:"송장번호", name:"phoneDlvryNo", width:100, maxLength:20, validate:'ValidAplhaNumeric'}
					]},
					{type:"block", list:[
						{type:"select", label:"유심택배사", name:"tbCd", width:100},
						{type:"newcolumn"},
						{type:"input", label:"송장번호", name:"dlvryNo", width:100, maxLength:20, validate:'ValidAplhaNumeric'}
					]}
				]},
				{type:"hidden", name:"prdtId"},
				{type:"hidden", name:"cntpntShopId"},
				{type:"hidden", name:"requestKey"},
				//{type:"hidden", name:"checkSn"},
				{type:"hidden", name:"sysRdate"}
			]}
		],
		buttons : {
			center : {
				btnSave : {label : "저장" },
				btnSaveMask : {label : "저장" },
				btnClose : { label: "닫기" }
			}
		},
		onValidateMore : function (data){
			if(data.dlvryName == ""){
				this.pushError("dlvryName", "받으시는분", "받으시는 분 정보를 입력하세요");
			}
			if(data.dlvryPost == ""){
				this.pushError("dlvryPost", "배송지 우편번호", "배송지 우편번호를 입력하세요");
			}
			if(data.dlvryAddr == ""){
				this.pushError("dlvryAddr", "배송지 주소", "배송지 주소를 입력하세요");
			}
			if(data.dlvryAddrDtl == ""){
				this.pushError("dlvryAddrDtl", "배송지 상세주소", "배송지 상세주소를 입력하세요");
			}
			var dlvryTel = data.dlvryTelFn + data.dlvryTelMn + data.dlvryTelRn;
			var dlvryMobile = data.dlvryMobileFn + data.dlvryMobileMn + data.dlvryMobileRn;
			if(dlvryTel == "" && dlvryMobile == ""){
				this.pushError("", "연락처", "받으시는 분 전화번호 또는 휴대폰 번호를 입력하세요");
			}
			if(dlvryTel != ""){
				if(data.dlvryTelFn == "" || data.dlvryTelMn == "" || data.dlvryTelRn == ""){
					this.pushError("dlvryTelFn", "전화번호", "받으시는 분 전화번호를 정확히 입력하세요");
				}
			}
			if(dlvryMobile != ""){
				if(data.dlvryMobileFn == "" || data.dlvryMobileMn == "" || data.dlvryMobileRn == ""){
					this.pushError("dlvryMobileFn", "휴대폰", "받으시는 분 휴대폰 번호를 정확히 입력하세요");
				}
			}
		},
		onChange : function(name) {
			var form=this;
			
			switch(name){
				case "cloneBase":
					var value = form.getItemValue(name);
					console.log("aa=" + value);
		    		if (value == "1") {
		    			form.setItemValue("dlvryName",form.getItemValue("cstmrName"));
		    			form.setItemValue("dlvryTelFn",form.getItemValue("cstmrTelFn"));
		    			form.setItemValue("dlvryTelMn",form.getItemValue("cstmrTelMn"));
		    			form.setItemValue("dlvryTelRn",form.getItemValue("cstmrTelRn"));
		    			form.setItemValue("dlvryMobileFn",form.getItemValue("cstmrMobileFn"));
		    			form.setItemValue("dlvryMobileMn",form.getItemValue("cstmrMobileMn"));
		    			form.setItemValue("dlvryMobileRn",form.getItemValue("cstmrMobileRn"));
		    			form.setItemValue("dlvryPost",form.getItemValue("cstmrPost"));
		    			form.setItemValue("dlvryAddr",form.getItemValue("cstmrAddr"));
		    			form.setItemValue("dlvryAddrDtl",form.getItemValue("cstmrAddrDtl"));
		    		}
	
					break;
			}
		},
		onButtonClick : function(btnId) {
			var form = this;

			switch (btnId) {
				case "btnDlvryPostFind":
					mvno.ui.codefinder4ZipCd("FORM2", "dlvryPost", "dlvryAddr", "dlvryAddrDtl");
					PAGE.FORM2.enableItem("dlvryAddrDtl");
					break;
					
				case "btnSave":
					var url = "/rcp/rcpMgmt/updateSelfSuffDetail.json";
					mvno.cmn.ajax(ROOT + url, form, function(result) {
						mvno.ui.closeWindowById(PAGE.FORM2, true);
						mvno.ui.notify("NCMN0001");
						PAGE.GRID1.refresh();
					});
					break;
					
				case "btnSaveMask":
					mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/updateSelfSuffDetailMask.json", form, function(result) {
						mvno.ui.closeWindowById(PAGE.FORM2, true);
						mvno.ui.notify("NCMN0001");
						PAGE.GRID1.refresh();
					});
					break;
					
				case "btnClose" :
					mvno.ui.closeWindowById(PAGE.FORM2, true);   // id 대신 form 도 가능
					break;
			}
		}
	}
	
	,FORM3 : {
		items : [
			{type:"block", list:[
				{type:"settings", position:"label-left", labelAlign:"right", labelWidth:180, labelHeight:30, blockOffset:10},
				{type:"block", list:[
					{type:"label", label:"송장번호등록양식", offsetLeft:30},
					{type:"newcolumn"},
					{type:"button", value:"다운로드", name :"btnDlvryNoEx"}
				]}
			]},
			{type:"block", list:[
			 	{type:"settings", position:"label-left", labelAlign:"right", labelWidth:180, labelHeight:30, blockOffset:0},
				{type:"block", list:[
					{type:"label", label:"배송완료등록양식", offsetLeft:30},
					{type:"newcolumn"},
					{type:"button", value:"다운로드", name :"btnDlvryOkEx"}
				]}
			]},			
		],
		buttons : {
			center : {
				btnClose : { label: "닫기" }
			}
		},
		onButtonClick : function(btnId) {
			var form = this;

			switch (btnId) {
				case "btnDlvryNoEx":
					
					mvno.cmn.download("/rcp/rcpMgmt/getSelfSuffDlvryNoExcelDown.json");
					
					break;
					
				case "btnDlvryOkEx":
					
					mvno.cmn.download("/rcp/rcpMgmt/getSelfSuffDlvryOkExcelDown.json");
					
					break;	
					
				case "btnClose" :
					mvno.ui.closeWindowById(PAGE.FORM3, true);
					break;
			}
		}
	},
	
	POPUP1TOP : {
		css : { height : "250px" },
		title : "송장번호등록",
		header : "일련번호,예약번호,택배사,송장번호",
		columnIds : "reqSrNo,resNo,tbNm,dlvryNo",  
		widths : "80,80,80,80",
		colAlign : "center,center,center,center",
		colTypes : "ro,ro,ro,ro"
	},	//POPUP1TOP End

	POPUP1MID : {
		title : "엑셀업로드",
		items : [
			{ type : "block",
			  list : [
				{ type : "newcolumn", offset : 10 },
				{ type : "upload", 
				  label : "송장번호등록 엑셀파일",
				  name : "file_upload1",
				  inputWidth : 320,
				  url : "/rcp/dlvyMgmt/regParExcelUp.do",
				  userdata : { limitSize:10000 },
				  autoStart : true } 
			]},
			{type:"input", width:100, name:"rowData", hidden:true}
		],
		
		buttons : {
			center : {
				btnSave : { label : "저장" },
				btnClose : { label : "닫기" }
			}
		},
		
		onButtonClick : function(btnId) {
			
			var form = this; // 혼란방지용으로 미리 설정 (권고)
			
			switch (btnId) {
				
				case "btnSave" :
					
					if(PAGE.POPUP1TOP.getRowsNum() == 0) {
						mvno.ui.alert("파일을 첨부해 주세요.");
						break;
					}
					
					var arrObj = [];
					
					PAGE.POPUP1TOP.forEachRow(function(id){
						var arrData = PAGE.POPUP1TOP.getRowData(id);
						
						arrObj.push(arrData);
					});
					
					var sData = {};
					
					sData.items = arrObj;
					
					var userOptions = {timeout:180000};
					mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/regSelfSuffDlvryNoList.json", sData, function(result) {
						mvno.ui.closeWindowById("POPUP1", true);
						mvno.ui.notify("NCMN0004");
						PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
					}, userOptions);
					
				break;
					
				case "btnClose":
					mvno.ui.closeWindowById("POPUP1");
				break;
			}
		}
	},	//POPUP1MID End
	
	POPUP2TOP : {
		css : { height : "250px" },
		title : "배송완료등록",
		header : "송장번호",
		columnIds : "dlvryNo",
		widths : "330",
		colAlign : "center",
		colTypes : "ro"
	},	//POPUP2TOP End
	
	POPUP2MID : {
		title : "엑셀업로드",
		items : [
			{ type : "block",
			  list : [
				{ type : "newcolumn", offset : 10 },
				{ type : "upload", 
				  label : "송장번호등록 엑셀파일",
				  name : "file_upload1",
				  inputWidth : 320,
				  url : "/rcp/dlvyMgmt/regParExcelUp.do",
				  userdata : { limitSize:10000 },
				  autoStart : true } 
			]},
			{type:"input", width:100, name:"rowData", hidden:true}
		],
		
		buttons : {
			center : {
				btnSave : { label : "저장" },
				btnClose : { label : "닫기" }
			}
		},
		
		onButtonClick : function(btnId) {
			
			var form = this; // 혼란방지용으로 미리 설정 (권고)
			
			switch (btnId) {
				
				case "btnSave" :
					
					if(PAGE.POPUP2TOP.getRowsNum() == 0) {
						mvno.ui.alert("파일을 첨부해 주세요.");
						break;
					}
					
					var arrObj = [];
					
					PAGE.POPUP2TOP.forEachRow(function(id){
						var arrData = PAGE.POPUP2TOP.getRowData(id);
						
						arrObj.push(arrData);
					});
					
					var sData = {};
					
					sData.items = arrObj;
					
					var userOptions = {timeout:180000};
					
					mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/regSelfSuffDlvryOkList.json", sData, function(result) {
						mvno.ui.closeWindowById("POPUP2", true);
						mvno.ui.notify("NCMN0004");
						PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
					}, userOptions);
					
				break;
					
				case "btnClose":
					mvno.ui.closeWindowById("POPUP2");
				break;
			}
		}
	}	//POPUP2MID End
	
};

var PAGE = {
	 title: "${breadCrumb.title}",
	 breadcrumb: "${breadCrumb.breadCrumb}",
// 	 buttonAuth: ${buttonAuth.jsonString},
	 buttonAuth: "",
	 onOpen:function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		
		/* Combo */
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2018"}, PAGE.FORM1, "searchCd"); // 검색구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM1, "paramPhoneStateCode"); // 진행상태
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM1, "paramUsimStateCode"); // 진행상태
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0005"}, PAGE.FORM1, "paramPstate"); // 신청서상태
	}
};

/* 주소 setting */
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
	PAGE.FORM2.setItemValue("dlvryPost",zipNo);
	PAGE.FORM2.setItemValue("dlvryAddr",roadAddrPart1);
	PAGE.FORM2.setItemValue("dlvryAddrDtl",addrDetail);
}
</script>