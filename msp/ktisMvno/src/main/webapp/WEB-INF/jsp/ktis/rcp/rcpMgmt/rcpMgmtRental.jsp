<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
	/**
	 * @Class Name : rcpMgmtRental.jsp
	 * @Description : 렌탈상품배송을 위한 신청조회화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-application"></div>

<!-- Script -->
<script type="text/javascript">

var dt = new Date();
var endDt = new Date().format("yyyymmdd");
var strtDt = new Date(dt.setDate(dt.getDate() - 7)).format("yyyymmdd");

var DHX = {
	FORM1 : {
		items : [
			{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0}
			, {type:"block", list:[
				{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"신청일자", value:strtDt, calendarPosition:"bottom", width:100, offsetLeft:10, required:true}
				, {type:"newcolumn"}
				, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~", value:endDt, calendarPosition:"bottom", labelAlign:"center", labelWidth:10, width:100, offsetLeft:5}
				, {type:"newcolumn"}
				, {type:"input", label:"대리점", width:100, name:"orgnId", offsetLeft:40}
				, {type:"newcolumn"}
				, {type:"button", value:"검색", name:"btnOrgFind"}
				, {type:"newcolumn"}
				, {type:"input", name:"orgnNm", width:150}
			]}
			, {type:"block", list:[
// 				, {type:"select", label:"검색구분", name:"searchCd", userdata:{lov:"RCP0046"}, width:100, offsetLeft:10}
				, {type:"select", label:"검색구분", name:"searchCd", width:100, offsetLeft:10}
				, {type:"newcolumn"}
				, {type:"input", name:"searchVal", width:120}
				, {type:"newcolumn"}
// 				, {type:"select", label:"진행상태",name:"requestStateCode", width:150, userdata:{lov:"RCP0006"}, offsetLeft:35}
				, {type:"select", label:"진행상태",name:"requestStateCode", width:150, offsetLeft:35}
			]}
			, {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"}
		],
		onButtonClick : function(btnId) {
			var form = this;
			
			switch (btnId) {
				case "btnSearch":
					PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpMgmtRentalList.json", form);
					break;
				case "btnOrgFind":
					mvno.ui.codefinder("ORG", function(result) {
						form.setItemValue("orgnId", result.orgnId);
						form.setItemValue("orgnNm", result.orgnNm);
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
			}
			
			if(data.strtDt > data.endDt){
				this.pushError("data.searchStartDt","신청일자",["ICMN0004"]);
			}
			
			if( data.searchCd != "" && data.searchVal.trim() == ""){
				this.pushError("searchVal", "검색어", "검색어를 입력하세요");
				PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
			}
		}
	},
	//신청관리 리스트
	GRID1 : {
		css : {
			height : "560px"
		},
		title : "조회결과",
		header : "대리점,예약번호,고객명,생년월일,휴대폰번호,요금제,업무구분,신청일자,진행상태,신청단말,단말일련번호",
		columnIds : "agentName,resNo,cstmrNameMask,birthDt,cstmrMobile,socName,operName,reqInDt,requestStateName,modelName,reqPhoneSnMask",
		colAlign : "left,center,left,center,center,left,center,center,center,left,left",
		colTypes : "ro,ro,ro,roXd,roXp,ro,ro,roXd,ro,ro,ro",
		colSorting : "str,str,str,str,str,str,str,str,str,str,str",
		widths : "120,100,100,80,100,150,80,80,80,120,80",
		paging: true,
		pageSize:20,
		buttons : {
			hright : {
				btnDnExcel : { label : "엑셀다운로드" }
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
						mvno.cmn.download("/rcp/rcpMgmt/getRcpMgmtRentalListExcel.json?menuId=MSP1010020", true, {postData:searchData});
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
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2003"}, PAGE.FORM2, "dlvryTelFn"); // 전화번호
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM2, "dlvryMobileFn"); // 휴대폰
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0025"}, PAGE.FORM2, "tbCd"); // 택배사
					
					
					PAGE.FORM2.setFormData(selectedData, true);
					
					console.log("selectedData=" + selectedData);

					mvno.ui.popupWindowById("FORM2", "상세화면", 800, 600, {
						onClose: function(win) {
							if (! PAGE.FORM2.isChanged()) return true;
							mvno.ui.confirm("CCMN0005", function(){
								win.closeForce();
							});
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
				{type:"settings", position:"label-left", labelAlign:"right", labelWidth:65, blockOffset:0}
				// 고객정보
				, {type: "fieldset", label: "고객정보", list:[
					{type:"settings"}
					, {type:"block", list:[
// 						{type:"select", label:"업무구분", name:"operType", userdata:{lov:"RCP0003"}, width:120, disabled:true}
						{type:"select", label:"업무구분", name:"operType", width:120, disabled:true}
						, {type:"newcolumn"}
// 						, {type:"select", label:"구매유형", name:"reqBuyType", userdata:{lov:"RCP0009"}, width:120, offsetLeft:20, disabled:true}
						, {type:"select", label:"구매유형", name:"reqBuyType", width:120, offsetLeft:20, disabled:true}
						, {type:"newcolumn"}
// 						, {type:"select", label:"서비스구분",name:"serviceType", userdata:{lov:"RCP0002"}, width:120, offsetLeft:20, disabled:true},
						, {type:"select", label:"서비스구분",name:"serviceType", width:120, offsetLeft:20, disabled:true},
					]}
					, {type:"block", list:[
// 						{type:"select", label:"고객구분", name:"cstmrType", userdata:{lov:"RCP0001"}, width:120, disabled:true}
						{type:"select", label:"고객구분", name:"cstmrType", width:120, disabled:true}
						, {type:"newcolumn"}
						, {type:"input",  label:"고객명", name:"cstmrName", width:250, offsetLeft:20, readonly:true}
					]}
					, {type:"block", list:[
						{type:"input",  label:"고객주소", name:"cstmrPost", width:70, readonly:true}
						, {type:"newcolumn"}
						, {type:"input",  label:"", name:"cstmrAddr", width:300, readonly:true}
						, {type:"newcolumn"}
						, {type:"input",  label:"", name:"cstmrAddrDtl", width:200, readonly:true}
					]}
					, {type:"block", list:[
						{type:"input", label:"법인명", name:"cstmrJuridicalCname", width:150, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"사업자등록번호", name:"cstmrJuridicalNumber1", labelWidth:80, width:40, offsetLeft:20, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"-", name:"cstmrJuridicalNumber2", width:30, labelWidth:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"-", name:"cstmrJuridicalNumber3", width:40, labelWidth:10, readonly:true}
					]}
					, {type:"block", list:[
						{type:"input", label:"상호명", name:"cstmrPrivateCname", width:150, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"사업자등록번호", name:"cstmrPrivateNumber1", width:40, labelWidth:80, offsetLeft:20, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"-", name:"cstmrPrivateNumber2", width:30, labelWidth:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"-", name:"cstmrPrivateNumber3", width:40, labelWidth:10, readonly:true}
					]}
					, {type:"block", list:[
						{type:"input", label:"메일", name:"cstmrMail", width:120, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"전화번호", name:"cstmrTelFn", width:40, offsetLeft:20, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"-", name:"cstmrTelMn", width:40, labelWidth:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"-", name:"cstmrTelRn", width:40, labelWidth:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"휴대폰", name:"cstmrMobileFn", width:40, offsetLeft:20, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"-", name:"cstmrMobileMn", width:40, labelWidth:10, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"-", name:"cstmrMobileRn", width:40, labelWidth:10, readonly:true}
					]}
					,{type:"block", list:[
						{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"reqInDt", label:"신청일자", calendarPosition:"bottom", width:120, disabled:true}
						, {type:"newcolumn"}
// 						, {type:"select", label:"모집경로",name:"onOffType", userdata:{lov:"RCP0007"}, width:120, offsetLeft:20, disabled:true}
						, {type:"select", label:"모집경로",name:"onOffType", width:120, offsetLeft:20, disabled:true}
						, {type:"newcolumn"}
						, {type:"input", label:"예약번호",name:"resNo", width:120, offsetLeft:48, readonly:true}
					]}
				]}
				// 렌탈
				,{type: "fieldset", label: "렌탈서비스", list:[
					{type:"settings"}
					,{type:"block", list:[
							{type:"checkbox", labelWidth:150, label:"렌탈서비스 이용 동의", labelAlign:"left", position:"label-right", name:"clauseRentalService", disabled:true, offsetLeft:10}
							, {type:"newcolumn"}
							, {type:"checkbox", labelWidth:150, label:"단말배상금 안내사항 동의", labelAlign:"left", position:"label-right", name:"clauseRentalModelCp", disabled:true}
							, {type:"newcolumn"}
							, {type:"checkbox", labelWidth:200, label:"단말배상금(부분파손) 안내사항 동의", labelAlign:"left", position:"label-right", name:"clauseRentalModelCpPr", disabled:true}
					]}
					,{type:"block", list:[
							{type:"input", width:100, label:"렌탈기본료", name:"rentalBaseAmt", numberFormat:"0,000,000,000", validate:"ValidInteger", readonly:true}
							, {type:"newcolumn"}
							, {type:"input", width:100, label:"렌탈료할인", name:"rentalBaseDcAmt", numberFormat:"0,000,000,000", validate:"ValidInteger", readonly:true, offsetLeft:50}
							, {type:"newcolumn"}
							, {type:"input", width:100, label:"렌탈단말금액", labelWidth:80, name:"rentalModelCpAmt", numberFormat:"0,000,000,000", validate:"ValidInteger", readonly:true, offsetLeft:50}
					]}
				]}
				// 신청정보
				, {type:"fieldset", label:"신청정보", list:[
					{type:"settings", labelWidth:80}
					, {type:"block", list:[
// 						{type:"select", label:"진행상태", name:"requestStateCode", userdata:{lov:"RCP0006"}, width:150}
						{type:"select", label:"진행상태", name:"requestStateCode", width:150}
						, {type:"newcolumn"}
						, {type:"input", label:"요금제", name:"socName", width:255, offsetLeft:35, readonly:true}
					]}
					, {type:"block", list:[
						{type:"input", label:"단말정보", name:"mnfctNm", width:100, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"", name:"prdtCode", width:100, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"", name:"modelName", width:150, readonly:true}
// 						, {type:"newcolumn"}
// 						, {type:"input", label:"", name:"reqModelColor", width:80, readonly:true}
						, {type:"newcolumn"},
						, {type:"input", label:"단말일련번호", name:"reqPhoneSn", width:150, maxLength:20, offsetLeft:20}
					]}
					, {type:"block", list:[
						{type:"input", label:"유심정보", name:"usimMnfctNm", width:100, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"", name:"usimId", width:100, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"", name:"usimName", width:150, readonly:true}
						, {type:"newcolumn"}
						, {type:"input", label:"유심일련번호", name:"reqUsimSn", width:150, maxLength:30, offsetLeft:20}
					]}
					, {type:"block", list:[
						{type:"input", label:"특별판매코드", name:"spcCode", width:150, maxLength:20}
						, {type:"newcolumn"}
						, {type:"input", label:"메모", name:"requestMemo", width:255, maxLength:200, offsetLeft:35, readonly:true}
					]}
				]}
				// 배송정보
				, {type: "fieldset", label: "배송정보", list:[
					{type:"settings"}
					, {type:"block", list:[
						{type:"input", label:"받으시는 분", name:"dlvryName", width:100, maxLength:20}
						, {type:"newcolumn"}
						, {type:"checkbox", label:"고객상동", name:"cloneBase", width:40, position:"label-right", labelAlign:"left", labelWidth:60, checked:false}
					]}
					, {type:"block", list:[
// 						{type:"select", label:"전화번호", name:"dlvryTelFn", userdata:{lov:"RCP0034"}, width:60}
						{type:"select", label:"전화번호", name:"dlvryTelFn", width:60}
						, {type:"newcolumn"}
						, {type:"input", label:"", name:"dlvryTelMn", width:40, maxLength:4}
						, {type:"newcolumn"}
						, {type:"input", label:"", name:"dlvryTelRn", width:40, maxLength:4}
						, {type:"newcolumn"}
// 						, {type:"select", label:"휴대폰", name:"dlvryMobileFn", userdata:{lov:"RCP0035"}, width:60, offsetLeft:10}
						, {type:"select", label:"휴대폰", name:"dlvryMobileFn", width:60, offsetLeft:10}
						, {type:"newcolumn"}
						, {type:"input", label:"", name:"dlvryMobileMn", width:40, maxLength:4}
						, {type:"newcolumn"}
						, {type:"input", label:"", name:"dlvryMobileRn", width:40, maxLength:4}
					]}
					, {type:"block", list:[
						{type:"input", label:"우편번호", name:"dlvryPost", width:75, readonly:true, maxLength:6}
						, {type:"newcolumn"}
						, {type:"button", value:"주소찾기", name :"btnDlvryPostFind"}
						, {type:"newcolumn"}
						, {type:"input", label:"", name:"dlvryAddr", width:400, readonly:true},
					]}
					, {type:"block", list:[
						{type:"input", label:"상세주소", name:"dlvryAddrDtl", width:500, maxLength:80},
					]}
					, {type:"block", list:[
						{type:"input", label:"요청사항",name:"dlvryMemo", width:500, maxLength:200}
					]}
					, {type:"block", list:[
// 						{type:"select", label:"택배사", name:"tbCd", userdata:{lov:"RCP0025"}, width:100}
						{type:"select", label:"택배사", name:"tbCd", width:100}
						, {type:"newcolumn"}
						, {type:"input", label:"송장번호", name:"dlvryNo", width:120, maxLength:20, validate:'ValidAplhaNumeric'}
					]}
				]}
				, {type:"hidden", name:"prdtId"}
				, {type:"hidden", name:"cntpntShopId"}
				, {type:"hidden", name:"requestKey"}
// 				, {type:"hidden", name:"checkSn"}
			]}
		]
		, buttons : {
			center : {
				btnSave : {label : "저장" }
				, btnClose : { label: "닫기" }
			}
		}
		, onValidateMore : function (data){
			// 진행상태 변경
			if(!(data.requestStateCode == "09" || data.requestStateCode == "10")){
				this.pushError("requestStateCode","진행상태","배송대기 또는 배송중만 선택 가능합니다");
			}
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
			if(data.reqPhoneSn == ""){
				this.pushError("reqPhoneSn", "단말일련번호", "단말일련번호를 입력하세요");
			}
			if(data.reqUsimSn == ""){
				this.pushError("reqUsimSn", "유심일련번호", "유심일련번호를 입력하세요");
			}
			if(data.onOffType != "1"){
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
			
			if(data.requestStateCode == "10" && (data.tbCd == "" || data.dlvryNo == "")){
				this.pushError("tbCd", "택배사/송장번호", "진행상태[배송중]으로 변경시 택배사/송장번호 정보를 입력하세요");
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
						//form.setItemValue('reqModelColor', result.prdtColrCd);
					});
					break;
					
				case "btnDlvryPostFind":
					mvno.ui.codefinder4ZipCd("FORM2", "dlvryPost", "dlvryAddr", "dlvryAddrDtl");
					PAGE.FORM2.enableItem("dlvryAddrDtl");
					break;
					
				case "btnSave":
					var url = "/rcp/rcpMgmt/updateRcpDetailRental.json";
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
	}
};

var PAGE = {
	 title: "${breadCrumb.title}",
	 breadcrumb: "${breadCrumb.breadCrumb}",
	 buttonAuth: ${buttonAuth.jsonString},
	 onOpen:function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		
		// 본사조직이 아닌 경우 대리점선택 비활성화
		if(${orgnInfo.typeCd} != "10"){
			PAGE.FORM1.setItemValue("orgnId", "${orgnInfo.orgnId}");
			PAGE.FORM1.setItemValue("orgnNm", "${orgnInfo.orgnNm}");
			
			PAGE.FORM1.disableItem("orgnId");
			PAGE.FORM1.disableItem("orgnNm");
			PAGE.FORM1.disableItem("btnOrgFind");
		}
		
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2018"}, PAGE.FORM1, "searchCd"); // 검색구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM1, "requestStateCode"); // 진행상태
		
// 		mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM1, "requestStateCode");
	}
};

/* 주소 setting */
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
	PAGE.FORM2.setItemValue("dlvryPost",zipNo);
	PAGE.FORM2.setItemValue("dlvryAddr",roadAddrPart1);
	PAGE.FORM2.setItemValue("dlvryAddrDtl",addrDetail);
}
</script>