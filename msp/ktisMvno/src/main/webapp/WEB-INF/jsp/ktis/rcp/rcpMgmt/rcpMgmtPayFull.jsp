<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
/**
 * @Class Name : rcpMgmtPayFull.jsp
 * @Description : 신청 관리 화면(일시납)
 * @
 * @ ---------- ------ -----------------------------
 * @ 2024.07.23 이승국 최초작성
 */

%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>

<div id="POPUP1">
	<div id="GRID2"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
				{type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchStartDt", label:"신청일자", value:"${startDate}", calendarPosition:"bottom", readonly:true, width:100, offsetLeft:10, required:true},
					{type:"newcolumn"},
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchEndDt", label:"~", value:"${endDate}", calendarPosition:"bottom", readonly:true, labelAlign:"center", labelWidth:10, width:100, offsetLeft:5},
					{type:'newcolumn'},
					{type:"select", label:"진행상태", name:"pRequestStateCode", width:110, offsetLeft:40},
					{type:"newcolumn"},
					{type:"select", label:"결제상태",name:"payRstCd", width:110, offsetLeft:40, options:[{value:"", text:"- 전체 -", selected:true},{value:"01", text:"정상"},{value:"02", text:"결제취소"},{value:"03", text:"결제취소완료"}]}
				]},
				{type:"block", list:[
					{type:"block", list:[
							{type:"select", label:"검색구분", name:"pSearchGbn", width:90, offsetLeft:10},
							{type:"newcolumn"},
							{type:"input", name:"searchVal", width:125, maxlength:30}
						]},
					{type:"newcolumn"},
					{type:"select", label:"신청취소",name:"pPstate", width:110, offsetLeft:40},
					{type:"newcolumn"},
					{type:"select", label:"업무구분",name:"operType", width:110, offsetLeft:40},
					{type : 'hidden', name: "DWNLD_RSN", value:""}, //엑셀다운로드 사유
				]},
				{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"}
			],
			
			onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpMgmtPayFullList.json", form);
						
						break;
				}
			},
			onChange : function(name, value) {//onChange START
				// 검색구분
				if(name == "pSearchGbn") {
					PAGE.FORM1.setItemValue("searchVal", "");

					if(value == null || value == "" || value == "3") {
						var searchsearchEndDt = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var searchStartDt = new Date(time);

						// 신청일자 Enable
						PAGE.FORM1.enableItem("searchStartDt");
						PAGE.FORM1.enableItem("searchEndDt");

						PAGE.FORM1.setItemValue("searchVal", "");

						PAGE.FORM1.setItemValue("searchStartDt", searchStartDt);
						PAGE.FORM1.setItemValue("searchEndDt", searchsearchEndDt);
						
						if(value == "3"){
							PAGE.FORM1.enableItem("searchVal");	
						}
						else{
							PAGE.FORM1.disableItem("searchVal");
						}						
					}
					else {
						PAGE.FORM1.setItemValue("searchStartDt", "");
						PAGE.FORM1.setItemValue("searchEndDt", "");
						
						// 신청일자 Disable
						PAGE.FORM1.disableItem("searchStartDt");
						PAGE.FORM1.disableItem("searchEndDt");
						
						PAGE.FORM1.enableItem("searchVal");
					}	
				}			
			},	
			/*	2022-04-12 주문번호는 DB상으로 type이 NUMBER 값을 가지고 있어서 string을 넣고 조회 버튼을 누르면 "자료처리오류입니다"라는 메세지가 뜸 
				이를 방지하기 위해 아래 코드 추가 */
			onKeyUp : function (inp, ev, name, value){
			
				var pSearchGbn = PAGE.FORM1.getItemValue("pSearchGbn");
		
				if(name == "searchVal"){
					if(pSearchGbn == "8" ) {	
						var val = PAGE.FORM1.getItemValue("searchVal");
						PAGE.FORM1.setItemValue("searchVal",val.replace(/[^0-9]/g,""));
					}
				}
			},
			onValidateMore : function (data){
				
				if(data.searchStartDt > data.searchEndDt){
					this.pushError("data.searchStartDt","신청일자",["ICMN0004"]);
				}
				
				if( data.pSearchGbn != "" && data.searchVal.trim() == ""){
					this.pushError("searchVal", "검색어", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
				}
			}
		}
		
		, GRID1 : {
			css : {
				height : "560px"
			}
			, title : "조회결과"
			, header : "주문번호,예약번호,업무구분,고객명,연락처,단말모델명,단말일련번호,단말색상,신청일자,진행상태,신청취소,수정자,수정일자,결제상태,결제취소실패사유,결제취소자,결제취소일,modelName,hndstAmt,modelDiscount2,modelDiscount3,modelInstallment,agrmTrm,dlvryName,dlvryMobileFn,dlvryMobileMn,dlvryMobileRn,dlvryTelFn,dlvryTelMn,dlvryTelRn,tbCd,dlvryNo,dlvryPost,dlvryAddr,dlvryAddrDtl,dlvryMemo,payRstCd" //16
			, columnIds : "requestKey,resNo,operName,cstmrName,cstmrTel,prdtCode,reqPhoneSn,reqModelColor,reqInDt,requestStateName,pstate,amdNm,amdDt,payRstNm,cnclFailRsn,cnclNm,cnclDt,modelName,hndstAmt,modelDiscount2,modelDiscount3,modelInstallment,agrmTrm,dlvryName,dlvryMobileFn,dlvryMobileMn,dlvryMobileRn,dlvryTelFn,dlvryTelMn,dlvryTelRn,tbCd,dlvryNo,dlvryPost,dlvryAddr,dlvryAddrDtl,dlvryMemo,payRstCd" //16
			, widths : "80,80,80,100,100,100,100,100,130,100,100,100,130,100,250,100,100,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0" //16
			, colAlign : "center,center,center,left,center,center,center,center,left,center,center,center,left,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center" //16 
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" //16
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" //16
			, hiddenColumns:[17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35]
			//, multiCheckbox : true
			, paging : true
			, showPagingAreaOnInit : true
			, pageSize : 20
			, buttons : {
				hright : {
					btnDnExcel : { label : "자료생성"}
				},
			},
			//checkSelectedButtons : ["btnDtl"],
			onRowDblClicked : function(rowId, celInd) {
				this.callEvent('onButtonClick', ['btnDtl']);
			},
			
			onButtonClick : function(btnId) {
				var grid = this;
				
				switch (btnId) {
				
					case "btnDtl":
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
					
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.clear();		
						PAGE.FORM2.setFormData(grid.getSelectedRowData(), true);	

						PAGE.FORM2.disableItem("cstmrName");
						PAGE.FORM2.disableItem("reqInDt");
						PAGE.FORM2.disableItem("requestKey");
						PAGE.FORM2.disableItem("resNo");
						PAGE.FORM2.disableItem("requestStateName");
						PAGE.FORM2.disableItem("pstate");
						PAGE.FORM2.disableItem("modelName");
						PAGE.FORM2.disableItem("prdtCode");
						PAGE.FORM2.disableItem("reqPhoneSn");
						PAGE.FORM2.disableItem("hndstAmt");
						PAGE.FORM2.disableItem("modelDiscount2");
						PAGE.FORM2.disableItem("modelDiscount3");
						PAGE.FORM2.disableItem("modelInstallment");
						PAGE.FORM2.disableItem("agrmTrm");
						PAGE.FORM2.disableItem("dlvryName"); 
						PAGE.FORM2.disableItem("dlvryTelFn");
						PAGE.FORM2.disableItem("dlvryTelMn");
						PAGE.FORM2.disableItem("dlvryTelRn");
						PAGE.FORM2.disableItem("dlvryMobileFn");
						PAGE.FORM2.disableItem("dlvryMobileMn");
						PAGE.FORM2.disableItem("dlvryMobileRn");
						PAGE.FORM2.disableItem("tbCd");
						PAGE.FORM2.disableItem("dlvryNo");
						PAGE.FORM2.disableItem("dlvryPost");
						PAGE.FORM2.disableItem("dlvryAddr");
						PAGE.FORM2.disableItem("dlvryAddrDtl");
						PAGE.FORM2.disableItem("dlvryMemo");
					
						mvno.ui.popupWindowById("FORM2", "상세화면", 790, 530, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
									
						break;
						
					case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
						} else {
							mvno.ui.prompt("다운로드 사유",  function(result) {
								PAGE.FORM1.setItemValue("DWNLD_RSN",result);
								mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRcpMgmtPayFullListExcelDownload.json?menuId=MSP1010089", PAGE.FORM1.getFormData(true), function(result){
									mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
								});
							}, { lines: 5,  maxLength : 80, minLength : 10 } );
						}
						break;
				
				}
			}
		},
		
		FORM2 : {
			items : [
				{type:"block", list:[
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:80, blockOffset:0},
					// 고객정보
					{type: "fieldset", label: "고객정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"고객명", name:"cstmrName", width:120, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"신청일자", name:"reqInDt", width:130, offsetLeft:10, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"주문번호", name:"requestKey", width:120, offsetLeft:20, readonly:true}
						]}
					]},
					{type: "fieldset", label: "신청정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"예약번호", name:"resNo", width:120, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"진행상태", name:"requestStateName", width:130, offsetLeft:10, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"신청취소", name :"pstate", width:120, offsetLeft:20, readonly:true}
						]},
						{type:"block", list:[
							{type:"input", label:"단말명", name:"modelName", width:120, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"단말모델명", name:"prdtCode", width:130, offsetLeft:10, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"단말일련번호", name :"reqPhoneSn", width:120, offsetLeft:20, readonly:true}
						]},
						{type:"block", list:[
							{type:"input", label:"출고가", name:"hndstAmt", width:120, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"공시지원금", name:"modelDiscount2", width:130, offsetLeft:10, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"추가지원금", name :"modelDiscount3", width:120, offsetLeft:20, readonly:true}
						]},
						{type:"block", list:[
							{type:"input", label:"일시납금액", name:"modelInstallment", width:120, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"약정개월수", name:"agrmTrm", width:130, offsetLeft:10, readonly:true},
							{type:"newcolumn"},
							{type:"select", label:"결제상태", name:"payRstCd", width:120, offsetLeft:20, options:[{value:"01", text:"정상"},{value:"02", text:"결제취소"},{value:"03", text:"결제취소완료"}]},
						]},
					]},
					// 배송정보
					{type: "fieldset", label: "배송정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"받으시는분", name:"dlvryName", width:120, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"택배사", name:"tbCd", width:130, offsetLeft:10, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"송장번호", name:"dlvryNo", width:120, offsetLeft:20, readonly:true}
						]},
						{type:"block", list:[
							{type:"input", label:"전화번호", name:"dlvryTelFn", width:60, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryTelMn", width:60, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryTelRn", width:60, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"휴대폰", name:"dlvryMobileFn", width:60, offsetLeft:104,readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryMobileMn", width:60, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryMobileRn", width:60, readonly:true},
							{type:"newcolumn"},
						]},
						{type:"block", list:[ 
							{type:"input", label:"우편번호", name:"dlvryPost", width:75, readonly:true},
							{type:"newcolumn"},
							{type:"input", label:"", name:"dlvryAddr", width:490, readonly:true},
						]},
						{type:"block", list:[
							{type:"input", label:"상세주소", name:"dlvryAddrDtl", width:570, readonly:true}
						]},
						{type:"block", list:[ 
							{type:"input", label:"요청사항",name:"dlvryMemo", width:570, readonly:true}
						]}
					]}
				]}
			],
			buttons : {
				center : {
					btnSave : {label : "저장" },
					btnClose : { label: "닫기" }
				}
			},
			onValidateMore : function (data){

			},
			onChange : function(name, value) {//onChange START
				// 검색구분
			
			},
			onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
				    case "btnSave":
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/rcpMgmtPayFullUpdate.json", form, function(result) {
						mvno.ui.closeWindowById(PAGE.FORM2, true);
						mvno.ui.notify("NCMN0001");
						
						PAGE.GRID1.refresh();
						});
						break;
											
					case "btnClose" :
						mvno.ui.closeWindowById(PAGE.FORM2, true);
						break;
				}
			}
		}
	}
			
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		 onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2063"}, PAGE.FORM1, "pSearchGbn");	// 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0005"}, PAGE.FORM1, "pPstate"); // 신청취소
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0049",orderBy:"etc1"}, PAGE.FORM1, "operType"); // 업무구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM1, "pRequestStateCode"); // 진행상태
		}
	};
</script>
