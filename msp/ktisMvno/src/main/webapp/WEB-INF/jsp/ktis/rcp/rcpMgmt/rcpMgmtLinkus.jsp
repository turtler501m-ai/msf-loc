<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
	/**
	 * @Class Name : rcpMgmtLinkus.jsp
	 * @Description : 물류배송을 위한 KT링커스용 신청관리 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2015.01.28 장익준 최초생성
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>

   
<div id="POPUP1">
	<div id="POPUP1TOP"  class="section-full"></div>
	<div id="POPUP1MID" class="section-box"></div>
</div>

<!-- Script -->
<script type="text/javascript">

var maskingYn = "${maskingYn}";				// 마스킹권한
var DHX = {
	FORM1 : {
		items : [
			{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
			{type:"block", list:[
				{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"신청일자", value:"${strtDt}", calendarPosition:"bottom", readonly:true, width:100, offsetLeft:10, required:true},
				{type:"newcolumn"},
				{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~", value:"${endDt}", calendarPosition:"bottom", readonly:true, labelAlign:"center", labelWidth:10, width:100, offsetLeft:5},
				{type:'newcolumn'},
				{type:"select", label:"대리점", name:"agntCd", width:170, offsetLeft:40, required:true},
				{type:"newcolumn"},
				{type:"select", label:"서비스구분", name:"serviceType", width:120, offsetLeft:40}
			]},
			{type:"block", list:[
				{type:"block", list:[
  					{type:"select", label:"검색구분", name:"searchCd", width:100, offsetLeft:10},
  					{type:"newcolumn"},
  					{type:"input", name:"searchVal", width:115}
  				]},
				{type:"newcolumn"},
				{type:"select", label:"업무구분",name:"pOperType", width:100, offsetLeft:40},
				{type:"newcolumn"},
				{type:"select", label:"진행상태",name:"requestStateCode", width:120, offsetLeft:110}
			]},
			{type:"block", list:[
				{type:"select", label:"구매유형",name:"reqBuyType", width:100, offsetLeft:10},
				{type:"newcolumn"},
				{type:"select", label:"유심종류",name:"usimKindsCd", width:100, offsetLeft:159}
			]},			
			{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search3"},
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
					PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpMgmtListLinkus.json", form, {
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
		header : "구분,예약번호,확정시간,고객명,생년월일,휴대폰번호,대리점,요금제,업무구분,신청일자,진행상태,모집경로,유입경로,sysRdate,requestKey",
		columnIds : "serviceName,resNo,setDttm,cstmrName2,birthDt,cstmrMobile,agentName,socName,operName,reqInDt,requestStateName,onOffNm,openMarketReferer,sysRdate,requestKey",
		colAlign : "center,center,center,left,center,center,left,left,center,center,center,center,left,center,center",
		colTypes : "ro,ro,ro,ro,roXd,roXp,ro,ro,ro,roXd,ro,ro,ro,ro,ro",
		colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
		widths : "50,70,70,100,80,120,200,200,80,80,80,80,120,80,80",
		hiddenColumns: [13,14],
		paging: true,
		pageSize:20,
		buttons : {
			hright : {
				btnDnExcel : { label : "자료생성" }
			},
			
			left : {
				btnDnExcelUp : { label : "유심일련번호등록양식"}
			},
			right : {
				btnUsimSn : { label : "유심일련번호등록"}
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
					} else {
						//배치서버 엑셀다운로드로 변경 
						var searchData =  PAGE.FORM1.getFormData(true);
						var strtDt = searchData.strtDt;
						var endDt = searchData.endDt;
						var agntCd = searchData.agntCd;
						if((strtDt != null && strtDt != '' && endDt != null && endDt != '') && (agntCd != null && agntCd != '')){
							mvno.cmn.downloadCallback(function(result) {
								PAGE.FORM1.setItemValue("DWNLD_RSN",result);
								mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/rcpMgmtListLinkusExcel.json?menuId=MSPLNKS010", PAGE.FORM1.getFormData(true), function(result){
									mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
								});
							});
						}else{
							mvno.ui.alert("검색조건이 맞지 않습니다.");
							return;
						}
					}
					break;
					
				case 'btnDtl' : //Row 더블클릭시 Start
					mvno.ui.createForm("FORM2");
				
					PAGE.FORM2.clear();
					
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0049",orderBy:"etc1"}, PAGE.FORM2, "operType"); // 업무구분
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9004"}, PAGE.FORM2, "reqBuyType"); // 구매유형
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0032",etc1:"1"}, PAGE.FORM2, "serviceType"); // 서비스구분
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9007",orderBy:"etc6"}, PAGE.FORM2, "cstmrType"); // 고객구분
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM2, "onOffType"); // 모집경로
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM2, "requestStateCode"); // 진행상태
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM2, "dlvryTelFn"); // 전화번호
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM2, "dlvryMobileFn"); // 휴대폰
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0025"}, PAGE.FORM2, "tbCd"); // 택배사
					
					//PAGE.FORM2.setFormData(selectedData, true);
					
					if (selectedData == null) {
						break;
					} else {
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRcpMgmtListLinkusDtl.json", {"requestKey":selectedData.requestKey}, function(resultData) {
							var data = resultData.result.data.rows[0];
							PAGE.FORM2.setFormData(data);
						});
					}	
					
					mvno.ui.popupWindowById("FORM2", "상세화면", 850, 720, {
						onClose: function(win) {
							if (! PAGE.FORM2.isChanged()) return true;
							mvno.ui.confirm("CCMN0005", function(){
								win.closeForce();
							});
						}
					});
					
					if (maskingYn != "Y") {
						PAGE.FORM2.disableItem("cstmrName");
						PAGE.FORM2.disableItem("cstmrPost");
						PAGE.FORM2.disableItem("cstmrAddr");
						PAGE.FORM2.disableItem("cstmrAddrDtl");
						PAGE.FORM2.disableItem("cstmrJuridicalCname");
						PAGE.FORM2.disableItem("cstmrJuridicalNumber1");
						PAGE.FORM2.disableItem("cstmrJuridicalNumber2");
						PAGE.FORM2.disableItem("cstmrJuridicalNumber3");
						PAGE.FORM2.disableItem("cstmrPrivateCname");
						PAGE.FORM2.disableItem("cstmrPrivateNumber1");
						PAGE.FORM2.disableItem("cstmrPrivateNumber2");
						PAGE.FORM2.disableItem("cstmrPrivateNumber3");
						PAGE.FORM2.disableItem("cstmrMail");
						PAGE.FORM2.disableItem("cstmrTelFn");
						PAGE.FORM2.disableItem("cstmrTelMn");
						PAGE.FORM2.disableItem("cstmrTelRn");
						PAGE.FORM2.disableItem("cstmrMobileFn");
						PAGE.FORM2.disableItem("cstmrMobileMn");
						PAGE.FORM2.disableItem("cstmrMobileRn");
						PAGE.FORM2.disableItem("resNo");
						PAGE.FORM2.disableItem("socName");
						PAGE.FORM2.disableItem("mnfctNm");
						PAGE.FORM2.disableItem("prdtCode");
						PAGE.FORM2.disableItem("modelName");
						PAGE.FORM2.disableItem("reqModelColor");
						PAGE.FORM2.disableItem("usimMnfctNm");
						PAGE.FORM2.disableItem("usimId");
						PAGE.FORM2.disableItem("usimKindsNm");
						PAGE.FORM2.disableItem("reqUsimSn");
						PAGE.FORM2.disableItem("reqPhoneSn");						
						PAGE.FORM2.disableItem("dlvryName");
						PAGE.FORM2.disableItem("cloneBase");
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
					}

					break;
				
				case "btnDnExcelUp":
					
					mvno.cmn.download("/rcp/rcpMgmt/getUsimSnTempExcelDownLinkus.json");
					
				break;						
					
				case "btnUsimSn":
					
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
						
						var url = ROOT + "/rcp/rcpMgmt/regUsimSnUpListLinkus.json";
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
					
					mvno.ui.popupWindowById("POPUP1", "유심일련번호등록", 405, 490, {
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
				{type:"settings", position:"label-left", labelAlign:"right", labelWidth:65, blockOffset:0},
				// 고객정보
				{type: "fieldset", label: "고객정보", list:[
					{type:"settings"},
					{type:"block", list:[
						{type:"select", label:"업무구분", name:"operType", width:120, disabled:true},
						{type:"newcolumn"},
						{type:"select", label:"구매유형", name:"reqBuyType", width:120, offsetLeft:20, disabled:true},
						{type:"newcolumn"},
						{type:"select", label:"서비스구분",name:"serviceType", width:120, offsetLeft:20, disabled:true},
					]},
					{type:"block", list:[
						{type:"select", label:"고객구분", name:"cstmrType", width:120, disabled:true},
						{type:"newcolumn"},
						{type:"input",  label:"고객명", name:"cstmrName", width:250, offsetLeft:20, readonly:true}
	      	   		]},
					{type:"block", list:[
						{type:"input",  label:"고객주소", name:"cstmrPost", width:70, readonly:true},
						{type:"newcolumn"},
						{type:"input",  label:"", name:"cstmrAddr", width:300, readonly:true},
						{type:"newcolumn"},
						{type:"input",  label:"", name:"cstmrAddrDtl", width:200, readonly:true}
	      	   		]},
					{type:"block", list:[
						{type:"input", label:"법인명", name:"cstmrJuridicalCname", width:150, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"사업자등록번호", name:"cstmrJuridicalNumber1", labelWidth:90, width:40, offsetLeft:20, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"cstmrJuridicalNumber2", width:30, labelWidth:10, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"cstmrJuridicalNumber3", width:40, labelWidth:10, readonly:true}
		   			]},
					{type:"block", list:[
						{type:"input", label:"상호명", name:"cstmrPrivateCname", width:150, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"사업자등록번호", name:"cstmrPrivateNumber1", width:40, labelWidth:90, offsetLeft:20, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"cstmrPrivateNumber2", width:30, labelWidth:10, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"cstmrPrivateNumber3", width:40, labelWidth:10, readonly:true}
					]},
					{type:"block", list:[
						{type:"input", label:"메일", name:"cstmrMail", width:120, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"전화번호", name:"cstmrTelFn", width:40, offsetLeft:20, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"cstmrTelMn", width:40, labelWidth:10, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"cstmrTelRn", width:40, labelWidth:10, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"휴대폰", name:"cstmrMobileFn", width:40, offsetLeft:20, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"cstmrMobileMn", width:40, labelWidth:10, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"-", name:"cstmrMobileRn", width:40, labelWidth:10, readonly:true}
		   			]}
					,{type:"block", list:[
						{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"reqInDt", label:"신청일자", calendarPosition:"bottom", width:120, disabled:true},
						{type:"newcolumn"},
						{type:"select", label:"모집경로",name:"onOffType", width:120, offsetLeft:20, disabled:true},
						{type:"newcolumn"},
						{type:"input", label:"예약번호",name:"resNo", width:120, offsetLeft:50, readonly:true}
					]}
				]},
				// 개통정보
				{type:"fieldset", label:"개통정보", list:[
					{type:"settings"},
					{type:"block", list:[
						{type:"select", label:"진행상태", name:"requestStateCode", width:150, labelWidth:80},
						{type:"newcolumn"},
						{type:"input", label:"요금제", name:"socName", width:255, offsetLeft:35, readonly:true}
					]},
					{type:"block", list:[
						{type:"input", label:"단말제조사", name:"mnfctNm", width:150, labelWidth:80, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"단말제품명", name:"prdtCode", width:100, offsetLeft:35, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"", name:"modelName", width:150, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"", name:"reqModelColor", width:80, readonly:true}
					]},
					{type:"block", list:[
						{type:"input", label:"유심제조사", name:"usimMnfctNm", width:150, labelWidth:80, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"유심제품명", name:"usimId", width:100, offsetLeft:35, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"", name:"usimName", width:150, readonly:true},
						{type:"newcolumn"},
						{type:"input", label:"", name:"usimKindsNm", width:80, readonly:true}
					]},
					{type:"block", list:[
						{type:"input", label:"유심번호", name:"reqUsimSn", width:150, maxLength:30, labelWidth:80},
						{type:"hidden", name:"oldUsimSn"},
						{type:"newcolumn"},
						{type:"input", label:"단말일련번호", name:"reqPhoneSn", width:150, maxLength:20, labelWidth:80, offsetLeft:20},
						/* {type:"newcolumn"},
						{type:"button", value: "재고확인", name:"btnCheckSn"} */
						{type:"newcolumn"},
						{type:"input", label:"", name:"drctMngmPrdcNm", labelWidth:60, width:185, readonly:true, disabled:true}	//v2019.01 직영상품명 추가
					]},
					{type:"block", list:[
						{type:"input", label:"특별판매코드", name:"spcCode", width:150, maxLength:20, labelWidth:80},
						{type:"newcolumn"},
						{type:"input", label:"메모", name:"requestMemo", width:255, maxLength:200, offsetLeft:35, readonly:true}
					]}
				]},
				// 배송정보
				{type: "fieldset", label: "배송정보", list:[
					{type:"settings"},
					{type:"block", list:[
						{type:"input", label:"받으시는 분", name:"dlvryName", width:100, maxLength:20},
						{type:"newcolumn"},
						{type:"checkbox", label:"고객상동", name:"cloneBase", width:40, position:"label-right", labelAlign:"left", labelWidth:60, checked:false},
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
						{type:"input", label:"상세주소", name:"dlvryAddrDtl", width:500, maxLength:80},
					]},
					{type:"block", list:[
						{type:"input", label:"요청사항",name:"dlvryMemo", width:500, maxLength:200}
					]},
					{type:"block", list:[
						{type:"select", label:"택배사", name:"tbCd", width:100},
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
				btnClose : { label: "닫기" }
			}
		},
		onValidateMore : function (data){
			// 진행상태 변경
// 			if(!(data.requestStateCode == "09" || data.requestStateCode == "10")){
// 				this.pushError("requestStateCode","진행상태","배송대기 또는 배송중만 선택 가능합니다");
// 			}
// 			switch(data.reqBuyType){
// 				case "MM" :
// 					if(data.reqPhoneSn == ""){
// 						this.pushError("reqPhoneSn", "단말일련번호", "단말일련번호를 입력하세요");
// 					}
// 					if(data.checkSn == "N" || data.checkSn == ""){
// 						this.pushError("reqPhoneSn", "단말일련번호", "단말일련번호 재고확인을 하세요");
// 					}
// 					break;
// 				case "UU" :
// 					if(data.reqUsimSn == ""){
// 						this.pushError("reqUsimSn", "유심일련번호", "유심일련번호를 입력하세요");
// 					}
// 					break;
// 			}
			
			// 2016.12.08 재고확인- 신청정보와 같이 체크
			switch(data.reqBuyType) {
				case "MM" :
					
					/* if(data.checkSn == "N" || data.checkSn == ""){
						//진행상태가 접수대기, 접수 상태일때는 체크 제외(단말기일련번호 입력값이 있으면 체크)
						// 2015-11-25, 해피콜(02) 상태 추가
						if(data.requestStateCode != "00" && data.requestStateCode != "01" && data.requestStateCode != "02" && data.requestStateCode != "03" && data.requestStateCode != "04" && data.requestStateCode != "09"  && data.requestStateCode != "08" ) {
							this.pushError("reqPhoneSn","단말기일련번호", "단말기일련번호 재고확인을 해주세요.");
						} else {
							if(data.reqPhoneSn){
								this.pushError("reqPhoneSn","단말기일련번호", "단말기일련번호 재고확인을 해주세요.");
							}
						}
					} */
					
					break;
			}
			
			if (maskingYn == "Y") {
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
			}
		/* 2017-02-20 박준성 - 배송중으로 상태 변경시 택배사/송장번호 필수값 체크 하지 않도록 수정
			if(data.requestStateCode == "10" && (data.tbCd == "" || data.dlvryNo == "")){
				this.pushError("tbCd", "택배사/송장번호", "진행상태[배송중]으로 변경시 택배사/송장번호 정보를 입력하세요");
			} 
		*/
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
				case "btnCheckSn":
					var reqPhoneSn = form.getItemValue('reqPhoneSn');
					var prdtId = form.getItemValue('prdtId');
					var reqModelColor = form.getItemValue('reqModelColor');    // 단말색상코드
					var cntpntShopId = form.getItemValue('cntpntShopId');
					if(reqPhoneSn == ''){
						mvno.ui.alert('단말기일련번호를 입력하세요.');
						return;
					}
					
					var params = {
						'reqPhoneSn':reqPhoneSn,
						'prdtId':prdtId,
						'reqModelColor':reqModelColor,    // 단말기 색상
						'cntpntShopId':cntpntShopId
					};
					
					mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/checkPhoneSN.json", params, function(resultData) {
						var result = resultData.result.result;
						if(result == null || result.cnt == '' || result.cnt == null || result.cnt == undefined){
							mvno.ui.alert('해당 조직에 등록된 단말기일련번호가 아니거나 현재 재고가 없습니다.');
							form.setItemValue('checkSn', 'N');
							return;
						}
						var cnt = Number(result.cnt);
						var svcCtNum = Number(result.svcCtNum);
						if(cnt == 0){
							mvno.ui.alert('해당 조직에 등록된 단말기일련번호가 아니거나 현재 재고가 없습니다.');
							form.setItemValue('checkSn', 'N');
							return;
						}

						if(svcCtNum > 0
								&& !mvno.ui.confirm('기개통 이력이 있는 단말기일련번호입니다. 계속 진행 하시겠습니까?')){
							form.setItemValue('checkSn', 'N');
							return;
						}
						mvno.ui.alert('사용 가능한 단말기일련번호 입니다.');
						form.setItemValue('checkSn', 'Y');
					});
					break;
					
				case "btnDlvryPostFind":
					mvno.ui.codefinder4ZipCd("FORM2", "dlvryPost", "dlvryAddr", "dlvryAddrDtl");
					PAGE.FORM2.enableItem("dlvryAddrDtl");
					break;
					
				case "btnSave":
					var url = "/rcp/rcpMgmt/updateRcpDetailLinkus.json";
					if (maskingYn != "Y") {
						form.setItemValue('reqUsimSn', '');
						form.setItemValue('reqPhoneSn', '');
					}
					mvno.cmn.ajax(ROOT + url, form, function(result) {
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
	},
	
	 POPUP1TOP : {
			css : { height : "250px" },
			title : "유심일련번호등록",
			header : "예약번호,유심번호,진행상태", //2021.01.07 진행상태 추가
			columnIds : "resNo,reqUsimSn,requestStateCode",  
			widths : "100,165,80",
			colAlign : "center,center,center",
			colTypes : "ro,ro,ro"
	},	//POPUP1TOP End

	POPUP1MID : {
			title : "엑셀업로드",
			items : [
				{ type : "block",
				  list : [
					{ type : "newcolumn", offset : 10 },
					{ type : "upload", 
					  label : "유심일련번호등록 엑셀파일",
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
						mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/regUsimSnListLinkus.json", sData, function(result) {
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
		}	//POPUP1MID End
		
};

var PAGE = {
	 title: "${breadCrumb.title}",
	 breadcrumb: "${breadCrumb.breadCrumb}",
	 buttonAuth: ${buttonAuth.jsonString},
	 onOpen:function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		
		/* 대리점 Combo */
		mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0043"}, PAGE.FORM1, "agntCd");

		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0032",etc1:"1"}, PAGE.FORM1, "serviceType"); // 서비스구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2018"}, PAGE.FORM1, "searchCd"); // 검색구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0049",orderBy:"etc1"}, PAGE.FORM1, "pOperType"); // 업무구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM1, "requestStateCode"); // 진행상태
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9004"}, PAGE.FORM1, "reqBuyType"); // 구매유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2035" , etc1:"1"}, PAGE.FORM1, "usimKindsCd"); // 유심종류
	}
};

/* 주소 setting */
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
	PAGE.FORM2.setItemValue("dlvryPost",zipNo);
	PAGE.FORM2.setItemValue("dlvryAddr",roadAddrPart1);
	PAGE.FORM2.setItemValue("dlvryAddrDtl",addrDetail);
}
</script>