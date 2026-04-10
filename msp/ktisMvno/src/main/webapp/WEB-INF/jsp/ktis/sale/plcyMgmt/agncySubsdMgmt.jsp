<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1023.jsp
	 * @Description : 판매점 보조금 관리 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.09.20 장익준 최초생성
	 * @
	 * @author : 장익준
	 * @Create Date : 2014. 9. 20.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>
<div id="FORM3" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
	var DHX = {
			//------------------------------------------------------------
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:70, blockOffset:0},
		 			{type:"block", list:[
						{type:"input", name:"orgnId", label:"조직", value: "${info.orgnId}", width:100, offsetLeft:10, required:true},
						{type:"newcolumn"},
						{type:"button", value:"검색", name:"btnOrgFind", offsetLeft:3},
						{type:"newcolumn"},
						{type:"input", name:"orgnNm", value:"${info.orgnNm}", width:150, readonly: true, offsetLeft:3},
						{type:"newcolumn"},
// 						{type:"input", name:"searchVal", label:"정책코드/명", width:289, offsetLeft: 30}
						{type:"select", name:"searchVal", label:"정책명", width:289, offsetLeft: 30, required:true},
					]},
					{type:"block", list:[
						{type:"calendar", name:"stdrDt", dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', calendarPosition:"bottom", label:"기준일자", width:100, offsetLeft:10, required:true},
						{type:"newcolumn"},
						{type:"select", name:"prdtId", label: "단말모델", width:100, offsetLeft:20},
						{type:"newcolumn"},
						{type:"select", name:"operType", label: "개통유형", width:100, offsetLeft:46},
						{type:"newcolumn"},
						{type:"select", name:"agrmTrm", label: "약정기간", width:100, offsetLeft:20}
					]},
					{type:"block", list:[
						{type:"select", name:"sprtTp", label: "할인유형", width:100, offsetLeft:10},
						{type:"newcolumn"},
						{type:"select", name:"rateCd", label: "요금제", width:200, offsetLeft:20}
					]},
					{type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					{type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					{type:"newcolumn"},
					{type : "button",name : "btnSearch",value : "조회", className:"btn-search3"}
				],
				// 조직 정보 초기화.
				onChange : function(name, value) {
					var form = this;
					
					if(name == "orgnId") {
						form.reloadOptions("searchVal",[{label: "", value:""}]);
						mvno.cmn.ajax4lov(ROOT+"/sale/rateMgmt/getPlcyComboList.json",  {orgnId:PAGE.FORM1.getItemValue("orgnId")}, PAGE.FORM1, "searchVal");
					}
					
					if(name == "orgnId") {
						if(value == null || value == "") {
							form.setItemValue("orgnId", "");
							form.setItemValue("orgnNm", "");
						}
					}
					
					if(name == "stdrDt") {
						
						var sData = {
								'orgnId'  : PAGE.FORM1.getItemValue("orgnId")
								, 'stdrDt'  : PAGE.FORM1.getItemValue("stdrDt", true)
						};
							
						form.reloadOptions("searchVal",[{label: "", value:""}]);
						mvno.cmn.ajax4lov(ROOT+"/sale/rateMgmt/getPlcyComboList.json", sData, PAGE.FORM1, "searchVal");
					}
					
					if(name == "prdtId") {
						
						var sData = {
								'orgnId'  : PAGE.FORM1.getItemValue("orgnId")
								, 'stdrDt'  : PAGE.FORM1.getItemValue("stdrDt", true)
								, 'prdtId'  : PAGE.FORM1.getItemValue("prdtId")
						};
							
						form.reloadOptions("searchVal",[{label: "", value:""}]);
						mvno.cmn.ajax4lov(ROOT+"/sale/rateMgmt/getPlcyComboList.json", sData, PAGE.FORM1, "searchVal");
					}
					
					if(name == "rateCd") {
						
						var sData = {
								'orgnId'  : PAGE.FORM1.getItemValue("orgnId")
								, 'stdrDt'  : PAGE.FORM1.getItemValue("stdrDt", true)
								, 'prdtId'  : PAGE.FORM1.getItemValue("prdtId")
								, 'rateCd'  : PAGE.FORM1.getItemValue("rateCd")
						};
							
						form.reloadOptions("searchVal",[{label: "", value:""}]);
						mvno.cmn.ajax4lov(ROOT+"/sale/rateMgmt/getPlcyComboList.json", sData, PAGE.FORM1, "searchVal");
					}
					
				},
				onButtonClick : function(btnId) {
					
					var form = this;
					
					switch (btnId) {
						case "btnOrgFind":
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("orgnId", result.orgnId);
							form.setItemValue("orgnNm", result.orgnNm);
							
							mvno.cmn.ajax4lov(ROOT+"/sale/rateMgmt/getPlcyComboList.json", {orgnId:result.orgnId}, PAGE.FORM1, "searchVal");
						});
						break;
						
					case "btnHndstFind":
						mvno.ui.codefinder("HNDSET", function(result) {
							form.setItemValue("prdtId" , result.prdtId);
							form.setItemValue("prdtNm" , result.prdtNm);
						});
						break;
						
					case "btnSearch":
						
						if (!this.validate()) return;
						var url = ROOT + "/sale/plcyMgmt/getAgncySubsdList.json";
						
						
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2);
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(url, form, {
							callback : function () {
								PAGE.FORM2.clear();
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						break;
					}
				}
		},
		// ----------------------------------------
		GRID1 : {
			css : {
				height : "280px"
			},
			title : "조회결과",
			header : "조직ID,요금제코드,판매정책ID,판매정책명,조직명,판매시작일시,판매종료일시,요금제,제품명,중고여부,약정기간,개통유형,할인유형,기본료,할인금액,추가할인금액,출고단가,공시지원금,추가지원금MAX,추가지원금,할부원금,할부수수료,신규수수료,MNP수수료,일반기변수수료,우수기변수수료",
			columnIds : "orgnId,rateCd,salePlcyCd,salePlcyNm,orgnNm,saleStrtDttm,saleEndDttm,rateNm,prdtNm,oldNm,agrmTrm,operTypeNm,sprtTpNm,baseAmt,dcAmt,addDcAmt,hndstAmt,subsdAmt,agncySubsdMax,agncySubsdAmt,instAmt,instCmsn,newCmsnAmt,mnpCmsnAmt,hcnCmsnAmt,hdnCmsnAmt",
			widths : "100,100,120,150,150,150,150,150,150,60,60,60,100,100,100,100,100,100,100,100,100,100,100,100,100,100",
			colAlign : "center,center,center,left,left,center,center,left,left,center,center,center,center,right,right,right,right,right,right,right,right,right,right,right,right,right",
			colTypes : "ro,ro,ro,ro,ro,roXdt,roXdt,ro,ro,ro,ro,ro,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
			paging : true,
			pagingSize : 50,
			hiddenColumns:[0,1],
			buttons : {
				hright : {
					 btnDnExcel : { label : "엑셀다운로드" }
				}
			},
			onRowSelect : function(rowId, celInd) {
				var rowData = this.getRowData(rowId);
				
				PAGE.FORM2.setFormData(rowData);
			},
			onButtonClick : function(btnId, selectedData) {
				
				var form = this;
				
				switch (btnId) {
					case "btnDnExcel":
						var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
						if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
						
						if(PAGE.GRID1.getRowsNum()== 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}
						
						if(PAGE.GRID1.getRowsNum() > 10000){
							mvno.ui.alert("10000건 이하 데이터만 다운로드 가능합니다.");
							return;
						}
						
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download(ROOT + "/sale/plcyMgmt/getAgncySubsdListExcel.json?menuId=MSP2002013", true, {postData:searchData});
						break;
				}
			}
		},
		// --------------------------------------------------------------------------
		//상세폼
		FORM2 : {
			title : "",
				items : [
				{type: 'settings', position: 'label-left', labelAlign:"left", labelWidth: '80'},
				{type: "block", list: [
					{type: 'input', name: 'orgnId', label: '조직ID', labelWidth: 90, width: 120, offsetLeft: 10, readonly:true, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'orgnNm', label: '조직명', labelWidth: 90, width: 120, offsetLeft: 20, readonly:true, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'salePlcyCd', label: '판매정책ID',  width: 120, offsetLeft: 20, readonly:true, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'salePlcyNm', label: '판매정책명',  width: 120, offsetLeft: 20, readonly:true, disabled:true}
				]},
				{type: "block", list: [
					{type: 'input', name: 'prdtCode', label: '제품코드', labelWidth: 90, width: 120, offsetLeft: 10, readonly:true, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'prdtNm', label: '제품명', labelWidth: 90, width: 120, offsetLeft: 20, readonly:true, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'rateCd', label: '요금제ID',  width: 120, offsetLeft: 20, readonly:true, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'rateNm', label: '요금제명',  width: 120, offsetLeft: 20, readonly:true, disabled:true}
				]},
				{type: "block", list: [
					{type: 'input', name: 'agrmTrm', label: '약정기간', labelWidth: 90, width: 120, offsetLeft: 10, readonly:true, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'operTypeNm', label: '개통유형', labelWidth: 90, width: 120, offsetLeft: 20, readonly:true, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'hndstAmt', label: '단말금액',  width: 120, offsetLeft: 20, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'subsdAmt', label: '공시지원금',  width: 120, offsetLeft: 20, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
				]},
				{type: "block", list: [
					{type: 'input', name: 'instAmt', label: '할부원금', labelWidth: 90, width: 120, offsetLeft:10, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'instCmsn', label: '할부수수료', labelWidth: 90, width: 120, offsetLeft: 20, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'newCmsnAmt', label: '신규수수료',  width: 120, offsetLeft: 20, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'mnpCmsnAmt', label: 'MNP수수료',  width: 120, offsetLeft:20, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true},
					
				]},
				{type: "block", list: [
					{type: 'input', name: 'hcnCmsnAmt', label: '일반기변수수료', labelWidth: 90, width: 120, offsetLeft:10, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'hdnCmsnAmt', label: '우수기변수수료', labelWidth: 90, width: 120, offsetLeft:20, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'sprtTpNm', label: '할인유형', width: 120, offsetLeft: 20, readonly:true, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'baseAmt', label: '기본료',  width: 120, offsetLeft:20, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
					
				]},
				{type: "block", list: [
					{type: 'input', name: 'dcAmt', label: '할인금액', labelWidth: 90, width: 120, offsetLeft: 10, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'addDcAmt', label: '추가할인금액', labelWidth: 90, width: 120, offsetLeft: 20, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'agncySubsdMax', label: '추가지원금 MAX', width: 120, offsetLeft: 20, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'agncySubsdAmt', label: '추가지원금', width: 120, offsetLeft: 20, numberFormat:"0,000,000,000", userdata:{align:"right"}, required: true, validate:"ValidInteger", maxLength:"10"},
				]}
			],
			buttons : {
				left : {
					btnReg : { label : "추가 지원금 일괄 적용" }
				},
			 	right : {
					btnMod : { label : "추가 지원금 수정" }
				}
			},
			onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
				
					case "btnReg":
						var sdata = PAGE.GRID1.getSelectedRowData();
						
						if(sdata == null){
							mvno.ui.alert("선택된 정책이 없습니다");
							return;
						}
							
						mvno.ui.createForm("FORM3");
						
						PAGE.FORM3.setFormData(sdata);
						
						PAGE.FORM3.clearChanged();
						
						mvno.ui.popupWindowById("FORM3", "추가지원금일괄적용", 700, 300, {
							onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						
						break;
						
					case "btnMod" :
						var sdata = PAGE.GRID1.getSelectedRowData();
						
						if(sdata == null){
							mvno.ui.alert("선택된 정책이 없습니다");
							return;
						}
						
						if(Number(form.getItemValue("agncySubsdMax")) < Number(form.getItemValue("agncySubsdAmt"))){
							mvno.ui.alert("추가지원금은 MAX 금액보다 클 수 없습니다");
							return;
						}
						
						mvno.cmn.ajax(ROOT + "/sale/plcyMgmt/updateAgncySubsdAmt.json", form, function(result) {
							mvno.ui.notify("NCMN0002");
							PAGE.GRID1.refresh();
							PAGE.FORM2.clear();
						});
						
						break;
				}
			}
		},
		// --------------------------------------------------------------------------
		//보조금일괄적용
		FORM3 : {
			title : "일괄적용",
			items : [
				{type: 'settings', position: 'label-left', labelAlign:"left", labelWidth: '100'},
				{type: "block", list: [
					{type: 'input', name: 'salePlcyCd', label: '판매정책코드',  width: 120, offsetLeft: 30, readonly:true, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'salePlcyNm', label: '판매정책명',  width: 120, offsetLeft:30, readonly:true, disabled:true},
				]},
				{type: "block", list: [
					{type: 'input', name: 'prdtCode', label: '제품코드',  width: 120, offsetLeft: 30, readonly:true, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'prdtNm', label: '제품명',  width: 120, offsetLeft:30, readonly:true, disabled:true},
				]},
				/* {type: "block", list: [
					{type: 'input', name: 'agrmTrm', label: '약정기간',  width: 120, offsetLeft: 30, readonly:true},
					{type: "newcolumn"},
					{type: 'input', name: 'operTypeNm', label: '개통유형',  width: 120, offsetLeft:30, readonly:true},
				]}, */
				{type: "block", list: [
					{type: 'input', name: 'agncySubsdMax', label: '추가지원금MAX',  width: 120, offsetLeft: 30, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'agncySubsdAmt', label: '추가지원금',  width: 120, offsetLeft:30, validate:"ValidInteger", maxLength:"10", numberFormat:"0,000,000,000", userdata:{align:"right"}},
				]},
				{type: 'hidden', name: 'orgnId'},
				{type: 'hidden', name: 'prdtId'},
				{type: 'hidden', name: 'oldYn'}
			],
			buttons: {
				center: {
					btnSave: { label: "저장" },
					btnClose: { label: "닫기" }
				}
			},
			onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				
				switch (btnId) {
					case "btnSave":
						if(form.getItemValue("agncySubsdAmt") == "" || Number(form.getItemValue("agncySubsdAmt")) < 0){
							mvno.ui.alert("추가지원금은 0보다 작을 수 없습니다");
							return;
						}
						
						if(Number(form.getItemValue("agncySubsdMax")) < Number(form.getItemValue("agncySubsdAmt"))){
							mvno.ui.alert("추가지원금은 MAX 금액보다 클 수 없습니다");
							return;
						}
						
						var data = {
								salePlcyCd : form.getItemValue("salePlcyCd"),
								orgnId : form.getItemValue("orgnId"),
								prdtId : form.getItemValue("prdtId"),
								oldYn : form.getItemValue("oldYn"),
								agncySubsdAmt : form.getItemValue("agncySubsdAmt")
						}
						
						mvno.cmn.ajax(ROOT + "/sale/plcyMgmt/updateAgncySubsdAmt.json", data, function(result) {
							mvno.ui.closeWindowById(form, true); 
							mvno.ui.notify("NCMN0002");
							PAGE.GRID1.refresh();
							PAGE.FORM2.clear();
						});
						break;
						
					case "btnClose" :
						mvno.ui.closeWindowById(form);
						break;
				}
			}
		}
	};

	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : " ${breadCrumb.breadCrumb}",
			buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.ui.createForm("FORM2");
				
				mvno.cmn.ajax4lov(ROOT+"/sale/rateMgmt/getPlcyComboList.json",  {orgnId:PAGE.FORM1.getItemValue("orgnId")}, PAGE.FORM1, "searchVal");
				
				mvno.cmn.ajax4lov(ROOT+"/sale/rateMgmt/getRateComboList.json", "", PAGE.FORM1, "rateCd");
				mvno.cmn.ajax4lov(ROOT+"/sale/subsdMgmt/getPrdtComboList.json", {prdtTypeCd:"A"}, PAGE.FORM1, "prdtId");
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0053"}, PAGE.FORM1, "agrmTrm");
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0058"}, PAGE.FORM1, "sprtTp");
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0049"}, PAGE.FORM1, "operType");
				
				PAGE.FORM1.setItemValue("stdrDt", new Date().format("yyyymmdd"));
				
				if(${info.userOrgnTypeCd} != 10) {
					PAGE.FORM1.disableItem("btnOrgFind");
					PAGE.FORM1.setReadonly("orgnId",true);
				}
			}
	};
</script>