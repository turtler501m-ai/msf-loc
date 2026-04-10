<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	/**
	 * @Class Name : reuseRipMgmt.jsp
	 * @Description : 신청IP재사용설정 메뉴
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2023.03.17
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
	var endDt = new Date().format("yyyymmdd");
	var time = new Date().getTime();
	time = time - 1000 * 3600 * 24 * 7;
	var strDt = new Date(time).format("yyyymmdd");
		
   var flug; //I: insert , U : update

	
		
	var DHX = {
		FORM1 : {
			items : [
				  {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					  {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"등록기간", value: strDt, calendarPosition:"bottom", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~", value: endDt, calendarPosition:"bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type:"newcolumn"}
					, {type:"select", width:100, offsetLeft:20, label:"검색구분", name:"searchGb", options:[{value:'', text:'- 전체 -', selected:true}, {value:'pContractNum', text:'계약번호'}, {value:'pSubLinkName', text:'고객명'}, {value:'pCtn', text:'핸드폰번호'}, {value:'pRip', text:'IP'}]}
					, {type:"newcolumn"}
					, {type:"input", width:160, name:"searchName"}
					, {type:"newcolumn"}
					, {type:"select", width:90, offsetLeft:20, label:"IP상태", name:"pRipStatus"}
					
				]}
				, {type:"newcolumn"}
				, {type:"button", value:"조회", name:"btnSearch", className:"btn-search1"}
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/voc/reuserip/selectReuseRipList.json", form);
						break;
				}
			}
			, onChange : function(name, value) {
				
			
				if(name == "searchGb") {
					PAGE.FORM1.setItemValue("searchName", "");
					
					if(value == null || value == "") {
						var endDt = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var strtDt = new Date(time);
						
						// 등록일자 Enable
						PAGE.FORM1.enableItem("strtDt");
						PAGE.FORM1.enableItem("endDt");

						PAGE.FORM1.setItemValue("strtDt", strtDt);
						PAGE.FORM1.setItemValue("endDt", endDt);
						
						PAGE.FORM1.disableItem("searchName");	
					}
					else {
						PAGE.FORM1.setItemValue("strtDt", "");
						PAGE.FORM1.setItemValue("endDt", "");
						
						// 등록일자 Disable
						PAGE.FORM1.disableItem("strtDt");
						PAGE.FORM1.disableItem("endDt");
						
						PAGE.FORM1.enableItem("searchName");
					}
				}
			}
			
			, onValidateMore : function (data){
				if (this.getItemValue("strtDt", true) > this.getItemValue("endDt", true)) {
					this.pushError("endDt", "등록일자", "종료일자가 시작일자보다 클 수 없습니다.");
					this.resetValidateCss();
				}
				
				if( data.searchGb != "" && data.searchName.trim() == ""){
					this.pushError("searchName", "검색어", "검색어를 입력하세요");
				}
				// 검색조건 없는 경우 등록일자 필수 체크
				if(data.searchGb == ""){
					if(this.getItemValue("strtDt") == null || this.getItemValue("strtDt") == ""){
						this.pushError("strtDt","등록일자","시작일자를 선택하세요");
					}
					
					if(this.getItemValue("endDt") == null || this.getItemValue("endDt") == ""){
						this.pushError("endDt","등록일자","종료일자를 선택하세요");
					}
				}
			}
		}
		, GRID1 : {
			css : {
					height : "560px"
			}
			, title : "조회결과"
			, header : "계약번호,고객명,휴대폰번호,구매유형,업무구분,최초요금제코드,최초요금제,현재요금제코드,현재요금제,상태,해지사유,모집경로,대리점,개통일자,해지일자,유심접점,최초유심접점,본인인증방식,IP상태,등록사유,등록일자,등록자,수정일자,수정자"
			, columnIds : "contractNum,subLinkName,subscriberNo,reqBuyTypeNm,operTypeNm,fstRateCd,fstRateNm,lstRateCd,lstRateNm,subStatusNm,canRsn,onOffTypeNm,openAgntNm,lstComActvDate,canDate,usimOrgNm,fstUsimOrgNm,authType,ripStatusNm,regRsnNm,regDttm,regstNm,rvisnDttm,rvisnNm"
			, colAlign : "center,left,center,center,center,center,left,center,left,center,left,center,left,center,center,left,left,center,center,left,center,left,center,left" 
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXd,roXd,ro,ro,ro,ro,roXd,ro,roXd,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, widths : "100,120,120,100,90,120,180,120,180,80,150,110,120,100,100,120,120,120,90,150,120,100,120,100"
			, paging: true
			, showPagingAreaOnInit: true
			, pageSize:20
			, buttons : {
				hright : {
 					btnExcel : { label : "엑셀다운로드" }
				}
				, right : {
					  btnReg: { label : "등록" }
					, btnMod: { label : "수정" }
				}
			}
			, checkSelectedButtons : ["btnMod"]
			, onRowDblClicked : function(rowId, celInd) {
				this.callEvent('onButtonClick', ['btnMod']);
			}
			, onButtonClick : function(btnId, selectedData) {
				var form = this;
				switch (btnId) {
					case "btnExcel":
						
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}
						
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download(ROOT + "/voc/reuserip/selectReuseRipListExcel.json?menuId=VOC1003020",true,{postData:searchData});
						
						break;
						
					// 수정
					case "btnMod" :
						flug = "U";
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();
						
						
						PAGE.FORM2.enableItem("ripStatus");			
						PAGE.FORM2.disableItem("btnFind");
						PAGE.FORM2.disableItem("searchVal");
	
						// 콤보세팅
						mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0022"}, PAGE.FORM2, "ripStatus"); // IP상태
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData.ripStatus == "L"){
							mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0023" , etc1:"L"}, PAGE.FORM2, "regRsn"); // 등록사유
						}else if(rowData.ripStatus == "U"){
							mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0023" , etc1:"U"}, PAGE.FORM2, "regRsn"); // 등록사유
						}
						PAGE.FORM2.setFormData(rowData);
						
						PAGE.FORM2.setItemValue("searchVal", rowData.contractNum);					
						mvno.ui.popupWindowById("FORM2", "명도주장발생제한", 700, 380, {
							onClose: function(win) {
								win.closeForce();
							}
						});
						
						break;
						
					// 등록
					case "btnReg" :
						flug = "I";
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();
												
						PAGE.FORM2.enableItem("btnFind");
						PAGE.FORM2.enableItem("searchVal");		
						
						PAGE.FORM2.clearChanged();
						// 콤보세팅
						mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0022"}, PAGE.FORM2, "ripStatus"); // IP상태
						mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0023" , etc1:"L"}, PAGE.FORM2, "regRsn"); // 등록사유
						
						PAGE.FORM2.setItemValue("ripStatus" , "L");
						PAGE.FORM2.setItemValue("regRsn" , "01");
						PAGE.FORM2.disableItem("ripStatus");
						
						
						mvno.ui.popupWindowById("FORM2", "명도주장발생제한", 700, 380, {
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
		}
		, FORM2 : {
				title : "명도주장발생제한"
				, items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					, {type: "fieldset", label: "계약정보", name:"subInfo", inputWidth:600, list:[
						{type:"block", list:[
							{type:"input", name:"searchVal", label:"계약번호", width:120, offsetLeft:20}
							, {type:"newcolumn"}
							, {type:"button", name:"btnFind", value:"검색"}
						]}
						, {type:"hidden", name:"contractNum"}
						, {type:"hidden", name:"subStatus"}
						, {type:"hidden", name:"lstRateCd"}
						, {type:"hidden", name:"reuseIpSeq"}
						, {type:"hidden", name:"requestKey"}
						, {type:"block", list:[
							{type:"input", name:"subLinkName", label:"고객명", width:100, offsetLeft:20, readonly:true, disabled:true}
							, {type:"newcolumn"}
							, {type:"input", name:"subStatusNm", label:"계약상태", width:100, offsetLeft:20, readonly:true, disabled:true}
							, {type:"newcolumn"}
							, {type:"calendar", name:"lstComActvDate", label:"개통일자", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y-%m-%d", width:100, offsetLeft:20, readonly:true, disabled:true}
						]}
						, {type:"block", list:[
							{type:"input", name:"reqBuyTypeNm", label:"구매유형", width:100, offsetLeft:20, readonly:true, disabled:true}
							, {type:"newcolumn"}
							, {type:"input", name:"operTypeNm", label:"업무구분", width:100, offsetLeft:20, readonly:true, disabled:true}
							, {type:"newcolumn"}
							, {type:"input", name:"onOffTypeNm", label:"모집경로", width:100, offsetLeft:20, readonly:true, disabled:true}
						]}
					]}
					, {type: "fieldset", label: "IP재설정", name:"reuseRip", inputWidth:600, list:[
						{type:"block", list:[
							{type:"select", name:"ripStatus", label:"IP상태",  width:120, offsetLeft:20}
						]}
						, {type:"block", list:[
							{type:"select", name:"regRsn", label:"등록사유",  width:200, offsetLeft:20}
						]}
					]}	
				]
				, buttons : {
					center : {
						btnSave : { label: " 저장" }
						, btnClose : { label : "닫기" }
					}
				}
				, onValidateMore : function(data) {
					var form = this;
					
				}
				, onChange : function(name, value) {
					var form = this;
					
					if(name == "ripStatus") {
						if(value == "L") {
							mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0023" , etc1:"L"}, PAGE.FORM2, "regRsn");
						} else if(value == "U") {
							mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0023" , etc1:"U"}, PAGE.FORM2, "regRsn");
						} else {
							mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0023"}, PAGE.FORM2, "regRsn");
						}
					}
					
					
				}
				, onButtonClick : function(btnId) {
					var form = this;
					
					switch(btnId){
					
					case "btnFind" :
					
						if(mvno.cmn.isEmpty(form.getItemValue("searchVal"))) {
							mvno.ui.alert("검색어를 입력하세요.");
							return;
						}
						
						var sdata = {searchVal : form.getItemValue("searchVal")};
						
						mvno.cmn.ajax(ROOT + "/voc/reuserip/getContractInfo.json", sdata, function(resultData) {
							var data = resultData.result.data.rows[0];
							if(data != null){
								form.setItemValue("requestKey",	data.requestKey);
								form.setItemValue("contractNum",	data.contractNum);
								form.setItemValue("subLinkName",	data.subLinkName);
								form.setItemValue("subStatusNm",			data.subStatusNm);
								form.setItemValue("lstComActvDate",			data.lstComActvDate);
								form.setItemValue("reqBuyTypeNm",		data.reqBuyTypeNm);
								form.setItemValue("operTypeNm",			data.operTypeNm);
								form.setItemValue("onOffTypeNm",			data.onOffTypeNm);
								form.setItemValue("lstRateCd",			data.lstRateCd);
								form.setItemValue("subStatus",			data.subStatus);
								
								if(data.requestKey == null){
									mvno.ui.alert("신청서정보가 존재하지 않아 IP정보를 가져올 수 없습니다.");
									return;
								}
								
							} else {
								mvno.ui.alert("계약정보가 존재하지 않습니다.");
								return;
							}
						});
						
						break;
							
						case "btnSave" :
							// validation 추가
							
						if(mvno.cmn.isEmpty(form.getItemValue("contractNum"))) {
							mvno.ui.alert("계약정보를 조회 후 저장하세요.");
							return;
						}
							
						if(mvno.cmn.isEmpty(form.getItemValue("ripStatus"))) {
							mvno.ui.alert("IP상태를 선택 후 저장하세요.");
							return;
						}	
							
						if(mvno.cmn.isEmpty(form.getItemValue("regRsn"))) {
							mvno.ui.alert("등록사유를 선택 후 저장하세요.");
							return;
						}	
						
						if(mvno.cmn.isEmpty(form.getItemValue("requestKey"))) {
							mvno.ui.alert("신청서가 존재하지 않는 계약정보입니다.");
							return;
						}	
							
							var strMsg = "저장하시겠습니까?";
							var url;
							
							if(flug == "I"){
								url = "/voc/reuserip/insertReuseRipMst.json"
							}else if (flug == "U"){
								url = "/voc/reuserip/updateReuseRipMst.json"
							}
							
							mvno.ui.confirm(strMsg, function() {
								mvno.cmn.ajax(ROOT + url , form, function(result) {
									mvno.ui.closeWindowById("FORM2", true);
									mvno.ui.notify("NCMN0004");
									PAGE.GRID1.refresh();
								});
							});
							
							break;
							
						case "btnClose" :
							mvno.ui.closeWindowById(form, true);
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
			
			PAGE.FORM1.disableItem("searchName");
			mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0022"}, PAGE.FORM1, "pRipStatus"); // IP상태
		}
	};

</script>