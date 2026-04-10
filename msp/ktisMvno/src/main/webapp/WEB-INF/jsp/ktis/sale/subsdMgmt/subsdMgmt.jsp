<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	* @Class Name : subsdMgmt.jsp
	* @Description : 공시지원금관리
	* @
	* @ 수정일	    수정자 수정내용
	* @ ---------- ------ -----------------------------
	* @ 2015.08.06
	* @Create Date : 2015. 8. 6.
	*/
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID5" class="section-full"></div><!-- 메인 -->
<div id="GRID1" class="section-full"></div><!-- 상세 -->
<!-- 공시지원금 상세 등록 -->
<div id="GROUP1" style="display:none;">
	<div class="row">
		<div id="GRID2" class="c5"></div>
		<div id="GRID3" class="c5"></div>
	</div>
	<div id="FORM2" class="section-box"></div>
	<div id="GRID4"></div>
</div>
<!-- 공시지원금(상세) 단건 수정 -->
<div id="FORM3" class="section-box"></div>
<!-- 공시지원금 메인 등록 -->
<div id="GROUP2" style="display:none;">
	<div id="FORM4" class="section-box"></div>
	<div id="GRID6"></div>
</div>
<!-- 공시지원금(메인) 단건 수정 -->
<div id="FORM5" class="section-box"></div>

<!-- 공시지원금 일괄 등록(엑셀) -->
<div id="FORM6" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

var today = new Date().format("yyyymmdd");
var operTypeItmList = ["operNac", "operMnp", "operHcn"];
var agrmTrmItmList = ["agrm12", "agrm18", "agrm24", "agrm30", "agrm36"];

var operTypeItmList2 = ["nacYn", "mnpYn", "hcnYn"];
var agrmTrmItmList2 = ["agrm12Yn", "agrm18Yn", "agrm24Yn", "agrm30Yn", "agrm36Yn"];

var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
				{type: "block", list: [
					{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "stdrDt", label: "기준일자", calendarPosition: "bottom", width:120, required:true},
					{type: "newcolumn"},
					{type: "select", name: "rateCd", label: "요금제", width:150, offsetLeft: 30},
					{type: "newcolumn"},
					{type: "select", name: "prdtId", label: "단말모델", width:150, offsetLeft: 30}
				]},
				/* {type: "block", list: [
					{type: "select", name: "operType", label: "개통유형", width: 120, userdata: {lov:"CMN0049"}},
					{type: "newcolumn"},
					{type: "select", name: "agrmTrm", label: "약정기간", width:150, offsetLeft: 30},
					{type: "newcolumn"},
					{type: "select", name: "oldYn", label: "중고여부", width:150, offsetLeft: 30, options:[{value:"", text:"- 전체 -"}, {value:"N", text:"신품"}, {value:"Y", text:"중고"}]}
				]}, */
				{type: "newcolumn"},
				{type: "button", value: "조회", name: "btnSearch" , className:"btn-search1"}
			],
			onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
	
					case "btnSearch":
						PAGE.GRID1.clearAll();
						PAGE.GRID5.list(ROOT + "/sale/subsdMgmt/getSubsdMgmtMainList.json", form);
						break;
				}
	
			}
		},
		//-------------------------------------------------------------------------------------
		//공시지원금리스트(main)
		GRID5 : {
			css : {
				height : "200px"
			},
			title : "공시지원금목록",
			header : "시작일자,종료일자,요금제,제품명,공시지원금",
			columnIds : "applStrtDt,applEndDt,rateNm,prdtNm,subsdAmt",
			colAlign : "center,center,left,left,right",
			colTypes : "roXd,roXd,ro,ro,ron",
			colSorting : "str,str,str,str,int",
			widths : "120,120,300,240,150",
			paging: true,
			pageSize:20,
			buttons : {
				hright : {
					btnExcel05 : { label : "엑셀다운로드" }
				},
				right : {
					btnAllReg05 : { label : "일괄등록" },
					btnReg05 : { label : "단건등록" },
					btnMod05 : { label : "수정" }
				}
			}
			,checkSelectedButtons : ["btnMod05"]
			// 원클릭 이벤트와 중첩 이벤트가 발생하여 주석처리
// 			,onRowDblClicked : function(rId, cId) {
// 				// 수정버튼 누른것과 같이 처리
// 				this.callEvent("onButtonClick", ["btnMod05"]);
// 			}
			,onRowSelect:function(rId, cId){
				var grid = this;
				
				var sdata = grid.getSelectedRowData();
				var data = {
						stdrDt : new Date(PAGE.FORM1.getItemValue("stdrDt")).format("yyyymmdd"),
						rateCd : sdata.rateCd,
						prdtId : sdata.prdtId
				}
				
				PAGE.GRID1.list(ROOT + "/sale/subsdMgmt/getSubsdMgmtDtlList.json", data);
			}
			,onButtonClick:function(btnId, selectedData){
				var grid = this;
				switch(btnId){
					case "btnExcel05" :
						if(PAGE.GRID5.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}else{
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/sale/subsdMgmt/getSubsdMgmtMainListExcel.json?menuId=MSP2002011", true, {postData:searchData}); 
						}
						break;
					case "btnReg05":
						mvno.ui.createForm("FORM4");
						mvno.ui.createGrid("GRID6");
						
						PAGE.GRID6.clearAll();
	
						PAGE.FORM4.setFormData(selectedData, true);
	
						PAGE.FORM4.setItemValue("flag", "I");
						PAGE.FORM4.setItemValue("applStrtDt", today);
						PAGE.FORM4.disableItem("rateGrpCd");
						//PAGE.FORM4.disableItem("rateCd");
						
						initSubsdAmtReg();
						
						mvno.ui.enableButton("FORM4" , "btnAppendRow");
						mvno.ui.enableButton("FORM4" , "btnSubsdCopy");	
						
						mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0046"}, PAGE.FORM4, "rateGrpCd");
						mvno.cmn.ajax4lov(ROOT+"/sale/rateMgmt/getRateComboList.json", "", PAGE.FORM4, "rateCd");
						mvno.cmn.ajax4lov(ROOT+"/sale/subsdMgmt/getPrdtComboList.json", "", PAGE.FORM4, "prdtId");
						
						PAGE.FORM4.clearChanged();
						
						mvno.ui.popupWindowById("GROUP2", "공시지원금등록", 800, 600, {
							onClose: function(win) {
								$('#GRID6 input:checkbox').prop('checked',false);
// 								mvno.ui.confirm("CCMN0005", function(){
// 									$('#GRID6 input:checkbox').prop('checked',false);
									
// 								});
								PAGE.GRID5.refresh();
								win.closeForce();
							}
						});
						
						break;
					case "btnMod05":
						if(selectedData == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
						mvno.ui.createForm("FORM5");
						
						PAGE.FORM5.setFormData(selectedData);
						
						PAGE.FORM5.disableItem("prdtNm");
						PAGE.FORM5.disableItem("oldNm");
						PAGE.FORM5.disableItem("rateNm");
						
						PAGE.FORM5.clearChanged();
						
						mvno.ui.popupWindowById("FORM5", "공시지원금수정", 550, 230, {
							onClose: function(win) {
								if (! PAGE.FORM5.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						
						break;
						
					case "btnAllReg05":
						
						mvno.ui.createForm("FORM6");
						
						PAGE.FORM6.clear();
						
						PAGE.FORM6.setItemValue("applStrtDt", today);
						PAGE.FORM6.setItemValue("fileName", "");
						
						PAGE.FORM6.setItemValue("operAll", 1);
						PAGE.FORM6.setItemValue("agrmAll", 1);
						
						enbFrmItm(PAGE.FORM6, "operAll", operTypeItmList2);
						enbFrmItm(PAGE.FORM6, "agrmAll", agrmTrmItmList2);
						
						PAGE.FORM6.clearChanged();
						
						var uploader = PAGE.FORM6.getUploader("file_upload1");
						uploader.revive();
						
						mvno.ui.popupWindowById("FORM6", "공시지원금일괄등록", 450, 260, {
							onClose: function(win) {
								if (! PAGE.FORM6.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									uploader.reset();
									win.closeForce();
								});
							}
							, onClose2: function(win) {
								uploader.reset();
							}
						});
						
						break;
				}
			}
		},
		//-------------------------------------------------------------------------------------
		//공시지원금리스트(sub)
		GRID1 : {
			css : {
				height : "250px"
			},
			title : "공시지원금상세목록",
			header : "시작일자,종료일자,요금제그룹,요금제,제품명,중고여부,개통유형,약정기간,공시지원금,등록자,등록일시,수정자,수정일시",
			columnIds : "applStrtDt,applEndDt,rateGrpNm,rateNm,prdtNm,oldNm,operTypeNm,agrmTrm,subsdAmt,regstNm,regstDttm,rvisnNm,rvisnDttm",
			colAlign : "center,center,center,left,left,center,center,center,right,center,center,center,center",
			colTypes : "roXd,roXd,ro,ro,ro,ro,ro,ro,ron,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,int,str,str,str,str",
			widths : "120,120,100,200,120,120,80,80,100,100,120,100,120",
			paging: true,
			pageSize:20,
			buttons : {
// 				hright : {
// 					btnExcel : { label : "엑셀다운로드" }
// 				},
// 				right : {
// 					btnReg : { label : "등록" },
// 					btnMod : { label : "수정" }
// 				}
			},
			checkSelectedButtons : ["btnMod"],
// 			onRowDblClicked : function(rowId, celInd) {
// 				// 수정버튼 누른것과 같이 처리
// 				this.callEvent("onButtonClick", ["btnMod"]);
// 			},
			onButtonClick : function(btnId, selectedData) {
				var form = this;
	
				switch (btnId) {
	
					case "btnExcel" :
						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}else{
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/sale/subsdMgmt/getSubsdMgmtDtlListExcel.json?menuId=MSP2002011", true, {postData:searchData}); 
						}
						break;
	
					case "btnReg":
	
						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID2");
						mvno.ui.createGrid("GRID3");
						mvno.ui.createGrid("GRID4");
						
						PAGE.GRID4.clearAll();
	
						PAGE.FORM2.setFormData(selectedData, true);
	
						PAGE.FORM2.setItemValue("flag", "I");
						PAGE.FORM2.setItemValue("applStrtDt", today);
						//PAGE.FORM2.setItemValue("rateGrpYn", "Y");
						PAGE.FORM2.disableItem("rateGrpCd");
						//PAGE.FORM2.disableItem("rateCd");
						
						mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0046"}, PAGE.FORM2, "rateGrpCd");
						mvno.cmn.ajax4lov(ROOT+"/sale/rateMgmt/getRateComboList.json", "", PAGE.FORM2, "rateCd");
						mvno.cmn.ajax4lov(ROOT+"/sale/subsdMgmt/getPrdtComboList.json", "", PAGE.FORM2, "prdtId");
	
						PAGE.GRID2.list(ROOT + "/sale/subsdMgmt/getCommonGridList.json", {grpId: "CMN0049"});	// 개통유형
						PAGE.GRID3.list(ROOT + "/sale/subsdMgmt/getCommonGridList.json", {grpId: "CMN0053", optVal:"0"});	// 약정기간
	
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "공시지원금등록", 800, 770, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) {
									$('#GRID2 input:checkbox').prop('checked',false);
									$('#GRID3 input:checkbox').prop('checked',false);
									$('#GRID4 input:checkbox').prop('checked',false);
									
									return true;
								}
								mvno.ui.confirm("CCMN0005", function(){
									$('#GRID2 input:checkbox').prop('checked',false);
									$('#GRID3 input:checkbox').prop('checked',false);
									$('#GRID4 input:checkbox').prop('checked',false);
									
									win.closeForce();
								});
								
								
							}
						});
	
						break;
						
					case "btnMod":
						
						console.log("data=" + selectedData);
						if(selectedData == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
						mvno.ui.createForm("FORM3");
						
						PAGE.FORM3.setFormData(selectedData);
						
						PAGE.FORM3.disableItem("prdtNm");
						PAGE.FORM3.disableItem("oldNm");
						PAGE.FORM3.disableItem("rateNm");
						PAGE.FORM3.disableItem("operTypeNm");
						PAGE.FORM3.disableItem("agrmTrm");
						
						PAGE.FORM3.clearChanged();
						
						mvno.ui.popupWindowById("FORM3", "공시지원금수정", 500, 320, {
							onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						
						break;
				}
			}
		},
	
		// --------------------------------------------------------------------------
		//공시지원금등록
		FORM2 : {
			title : "",
			items : [
				{type: "settings", position: "label-left", labelWidth: "70"},

				{type: "block", blockOffset: 0, list: [
					{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "applStrtDt", label: "시작일자", calendarPosition: "bottom", width:100, offsetLeft: 20, required:true},
					{type: "newcolumn"},
					{type: "checkbox", name: "rateGrpYn", label: "그룹여부", value:"Y", offsetLeft: 20},
					{type: "newcolumn"},
					{type: "select", name: "rateGrpCd", label: "요금제그룹", width: 100, offsetLeft: 20},
					{type: "newcolumn"},
					{type: "select", name: "rateCd", label: "요금제", width: 120, offsetLeft: 20}
				]},
				{type: "block", blockOffset: 0, list: [
					{type: "select", name: "prdtId", label: "단말모델", width: 210, required: true, offsetLeft: 20},
					{type: "newcolumn"},
					{type: "checkbox", name: "oldYn", label: "중고여부", offsetLeft:26},
					{type: "newcolumn"},
					{type: "input", name: "subsdAmt", label: "공시지원금", width:50, labelWidth: 80, offsetLeft: 89, validate: "ValidInteger", required:true, maxLength:10, numberFormat:"0,000,000,000", userdata:{align:"right"}}
				]},

				{type: "hidden", name: "flag"}

			],
			buttons : {
				right : {
					btnInit : { label : "초기화" },
					btnAppendRow : { label : "추가" }
				}
			},
			onChange : function(name, value) {
				var form = this;

				switch(name){
					case "applStrtDt" :
						if(PAGE.GRID6.getRowsNum() > 0){
							/* mvno.ui.confirm("기준일자 변경시 데이터 초기화됩니다", function(result){
								console.log("okCallback=" + okCallback + result);
								PAGE.GRID6.clearAll();
							}); */
							mvno.ui.alert("시작일자가 변경되어 데이터 초기화됩니다");
							PAGE.GRID6.clearAll();
						}
						break;
					
					case "rateGrpYn" :
						if(form.isItemChecked("rateGrpYn") == true){
							form.setItemValue("rateCd", "");
							form.disableItem("rateCd");
							form.enableItem("rateGrpCd");
							mvno.ui.alert("요금제그룹에 등록된 요금제가 없는 경우 지원금 생성이 되지 않습니다.");
						}else{
							form.setItemValue("rateGrpCd", "");
							form.enableItem("rateCd");
							form.disableItem("rateGrpCd");
						}

					break;
				}
			},
			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {
					case "btnInit":
						PAGE.FORM2.clear();
						break;

					case "btnAppendRow":
						if(form.getItemValue("applStrtDt") == null || form.getItemValue("applStrtDt") == ""){
							mvno.ui.alert("시작일자를 선택하세요");
							return;
						}
						
						var strtDt = new Date(form.getItemValue("applStrtDt")).format("yyyymmdd");
						if(strtDt < today){
							mvno.ui.alert("시작일자는 오늘 이후 날짜로 입력하세요");
							return;
						}
						
						if(form.isItemChecked("rateGrpYn") == true){
							if(form.getItemValue("rateGrpCd") == ""){
								mvno.ui.alert("요금제그룹을 선택하세요");
								return;
							}
						}else{
							if(form.getItemValue("rateCd") == ""){
								mvno.ui.alert("요금제를 선택하세요");
								return;
							}
						}
						
						if(form.getItemValue("prdtId") == ""){
							mvno.ui.alert("단말모델을 선택하세요");
							return;
						}
						
						if(form.getItemValue("subsdAmt") == null || form.getItemValue("subsdAmt") == ""){
							mvno.ui.alert("공시지원금을 입력하세요");
							return;
						}
						
						if(Number(form.getItemValue("subsdAmt")) < 0){
							mvno.ui.alert("공시지원금은 0보다 작을 수 없습니다.");
							return;
						}
						
						var operData = PAGE.GRID2.getCheckedRowData();
						
						if(operData.length < 1){
							mvno.ui.alert("개통유형을 선택하세요");
							return;
						}
						
						var agrmData = PAGE.GRID3.getCheckedRowData();
						if(agrmData.length < 1){
							mvno.ui.alert("약정기간을 선택하세요");
							return;
						}
						
						// 중복데이터 확인
						for(var i=0; i<PAGE.GRID4.getRowsNum(); i++){
							var data = PAGE.GRID4.getSelectedRowData(i);
							
							if(form.isItemChecked("rateGrpYn") == true){
								if(data.prdtId == form.getItemValue("prdtId") && data.rateGrpCd == form.getItemValue("rateGrpCd")){
									mvno.ui.alert("중복된 요금제그룹이 존재합니다");
									return;
								}
							}else{
								if(data.prdtId == form.getItemValue("prdtId") && data.rateCd == form.getItemValue("rateCd")){
									mvno.ui.alert("중복된 요금제코드가 존재합니다");
									return;
								}
							}
						}
						
						for(var i=0; i<operData.length; i++){
							
							for(var j=0; j<agrmData.length; j++){
								var gridData = {
										applStrtDt : form.getItemValue("applStrtDt").format("yyyymmdd"),
										operType : operData[i].cdVal,
										operTypeNm : operData[i].cdNm,
										agrmTrm : agrmData[j].cdVal,
										rateGrpYn : form.isItemChecked("rateGrpYn") == true ? "Y" : "N",
										rateGrpCd : form.getItemValue("rateGrpCd"),
										rateCd : form.getItemValue("rateCd"),
										prdtId : form.getItemValue("prdtId"),
										oldYn : form.isItemChecked("oldYn") == true ? "Y" : "N",
										subsdAmt : form.getItemValue("subsdAmt")
								}
								
								PAGE.GRID4.appendRow(gridData);
								
							}
						}
						
						break;

					case "btnClose" :
						mvno.ui.closeWindowById(form);   
						break;
				}
			}
		},
		//-------------------------------------------------------------------------------------
		//개통유형
		GRID2 : {
			css : {
				height : "120px"
			},
			header : "개통유형",
			columnIds : "cdNm",
			colAlign : "center",
			colTypes : "ro",
			colSorting : "str",
			widths : "*",
			paging: false,
			multiCheckbox : true
		},
		//-------------------------------------------------------------------------------------
		//약정기간
		GRID3 : {
			css : {
				height : "120px"
			},
			header : "약정기간",
			columnIds : "cdNm",
			colAlign : "center",
			colTypes : "ro",
			colSorting : "str",
			widths : "*",
			paging: false,
			multiCheckbox : true
		},
		//-------------------------------------------------------------------------------------
		//공시지원금목록
		GRID4 : {
			css : {
				height : "300px"
			},
			title : "공시지원금목록",
			header : "시작일자,그룹코드,요금제코드,제품ID,중고여부,공시지원금,개통유형,약정기간,그룹적용여부,개통유형",
			columnIds : "applStrtDt,rateGrpCd,rateCd,prdtId,oldYn,subsdAmt,operTypeNm,agrmTrm,rateGrpYn,operType",
			colAlign : "center,center,center,center,center,right,center,center,center,center",
			colTypes : "roXd,ro,ro,ro,ro,ron,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,int,str,str,str,str",
			widths : "100,100,100,100,100,100,100,100,100,100",
			paging: false,
			multiCheckbox : true,
			hiddenColumns : [8,9],
			buttons : {
				left : {
					btnInit : { label : "초기화" }
				},
				right : {
					btnMDelete : { label : "삭제" },
					btnMSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			},
			onButtonClick : function(btnId) {
				var form = this;

				switch(btnId){
				
					case "btnInit" :
						PAGE.GRID4.clearAll();
						
						break;
						
					case "btnMDelete" :
						var rowIds = this.getCheckedRows(0);
						
						if(!rowIds){
							mvno.ui.alert("ECMN0003");
						    return;
						}else{
							this.deleteRowByIds(rowIds);
						}
						
						break;
						
					case "btnMDelete" :
						var rowIds = this.getCheckedRows(0);
						
						if(!rowIds){
							mvno.ui.alert("ECMN0003");
						    return;
						}else{
							this.deleteRowByIds(rowIds);
						}
						
						break;
						
					case "btnMSave" :
						
						var rowIds = this.getCheckedRows(0);
						
						if(!rowIds){
							mvno.ui.alert("ECMN0003");
						    return;
						}else{
							
							var sdata = this.classifyRowsFromIds(rowIds);
							mvno.ui.confirm("[ " + this.getCheckedRowData().length + " ]건 공시지원금 정보를 저장하시겠습니까?", function() {
								mvno.cmn.ajax4json(ROOT + "/sale/subsdMgmt/insertSubsdAmtList.json", sdata, function(){
									mvno.ui.notify("NCMN0006");
									PAGE.GRID2.refresh();
									PAGE.GRID3.refresh();
									PAGE.GRID4.clearAll();
								});
							});
						}
						
						break;
						
					case "btnClose" :
						mvno.ui.closeWindowById("GROUP1");
						break;
				}
			}
		},
		// --------------------------------------------------------------------------
		//공시지원금수정
		FORM3 : {
			title : "",
			items : [
				{type: "settings", position: "label-left", labelWidth: "80"},
				{type: "block", blockOffset: 0, list: [
					{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "applStrtDt", label: "시작일자", calendarPosition: "bottom", width:100, offsetLeft: 20, required:true, disabled:true},
					{type: "newcolumn"},
					{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "applEndDt", label: "종료일자", calendarPosition: "bottom", width:100, offsetLeft: 20, required:true, disabled:true},
				]},
				{type: "block", blockOffset: 0, list: [
					{type: "input", name: "prdtNm", label: "단말모델", width: 100, required: true, offsetLeft: 20, disabled:true},
					{type: "newcolumn"},
					{type: "input", name: "oldNm", label: "중고여부", width:100, offsetLeft:20, disabled:true}
				]},
				{type: "block", blockOffset: 0, list: [
					{type: "input", name: "rateNm", label: "요금제", width: 100, offsetLeft:20, disabled:true},
					{type: "newcolumn"},
					{type: "input", name: "operTypeNm", label: "개통유형", width: 100, offsetLeft: 20, disabled:true}
				]},
				{type: "block", blockOffset: 0, list: [
					{type: "input", name: "agrmTrm", label: "약정기간", width: 100, offsetLeft: 20, disabled:true},
					{type: "newcolumn"},
					{type: "input", name: "subsdAmt", label: "공시지원금", width:100, offsetLeft: 20, validate: "ValidInteger", required:true, maxLength:10, numberFormat:"0,000,000,000", userdata:{align:"right"}}
				]},
				{type: "hidden", name: "rateCd"},
				{type: "hidden", name: "prdtId"},
				{type: "hidden", name: "prdtCode"},
				{type: "hidden", name: "oldYn"}
			],
			buttons : {
				center : {
					btnSave01 : { label : "저장" },
					btnClose01 : { label : "닫기" }
				}
			}
			,onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId){
					case "btnSave01":
						if(new Date(form.getItemValue("applEndDt")).format("yyyymmdd") <= today){
							mvno.ui.alert("완료일이 현재일 이전인 경우 공시지원금 정보를 수정할 수 없습니다");
							return;
						}
						
						mvno.cmn.ajax(ROOT + "/sale/subsdMgmt/updateSubsdAmtInfo.json", form, function(){
							mvno.ui.notify("NCMN0004");
							PAGE.GRID1.refresh();
							mvno.ui.closeWindowById("FORM3");
						});
						break;
						
					case "btnClose01" :
						mvno.ui.closeWindowById("FORM3");
						break;
				}
			}
		},
		// --------------------------------------------------------------------------
		//공시지원금등록
		FORM4 : {
			title : "",
			items : [
				{type: "settings", position: "label-left", labelWidth: "70"},

				{type: "block", blockOffset: 0, list: [
					{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "applStrtDt", label: "시작일자", calendarPosition: "bottom", width:100, offsetLeft: 20, required:true},
					{type: "newcolumn"},
					{type: "checkbox", name: "rateGrpYn", label: "그룹여부", value:"Y", offsetLeft: 20},
					{type: "newcolumn"},
					{type: "select", name: "rateGrpCd", label: "요금제그룹", width: 100, offsetLeft: 20},
					{type: "newcolumn"},
					{type: "select", name: "rateCd", label: "요금제", width: 100, offsetLeft: 20}
				]},
				{type: "block", blockOffset: 0, list: [
					{type: "select", name: "prdtId", label: "단말모델", width: 210, required: true, offsetLeft: 20},
					{type: "newcolumn"},
					{type: "checkbox", name: "oldYn", label: "중고여부", offsetLeft:26},
					{type: "newcolumn"},
					{type: "input", name: "subsdAmt", label: "공시지원금", width:46, labelWidth: 80, offsetLeft: 88, validate: "ValidInteger", maxLength:10, numberFormat:"0,000,000,000", userdata:{align:"right"}, required:true}
				]},
				{type: "block", blockOffset: 0, list: [
					{type: "label", label: "업무구분", labelWidth : 50, offsetLeft:20},
					{type: "newcolumn"},
					{type: "checkbox", name: "operAll", label: "전체", width:20, labelWidth : 30, offsetLeft:10, value : "NAC3|MNP3|HCN3|HDN3" },
					{type: "newcolumn"},
					{type: "checkbox", name: "operNac", label: "신규", width:20, labelWidth:30, offsetLeft:3, value : "NAC3", disabled:true},
					{type: "newcolumn"},
					{type: "checkbox", name: "operMnp", label: "번호이동", width:40, labelWidth:50, offsetLeft:3, value : "MNP3", disabled:true},
					{type: "newcolumn"},
					{type: "checkbox", name: "operHcn", label: "기기변경", width:40, labelWidth:50, offsetLeft:3, value : "HCN3|HDN3", disabled:true},
					{type: "newcolumn"},
					{type: "label", label: "약정기간", labelWidth : 50, offsetLeft:20},
					{type: "newcolumn"},
					{type: "checkbox", name: "agrmAll", label: "전체", width:20, labelWidth : 30, offsetLeft:10 , value : "All"},
					{type: "newcolumn"},
					{type: "checkbox", name: "agrm12", label: "12", width:20, labelWidth:15, offsetLeft:3, value : "12", disabled:true},
					{type: "newcolumn"},
					{type: "checkbox", name: "agrm18", label: "18", width:20, labelWidth:15, offsetLeft:3, value : "18", disabled:true},
					{type: "newcolumn"},
					{type: "checkbox", name: "agrm24", label: "24", width:20, labelWidth:15, offsetLeft:3, value : "24", disabled:true},
					{type: "newcolumn"},
					{type: "checkbox", name: "agrm30", label: "30", width:20, labelWidth:15, offsetLeft:3, value : "30", disabled:true},
					{type: "newcolumn"},
					{type: "checkbox", name: "agrm36", label: "36", width:20, labelWidth:15, offsetLeft:3, value : "36", disabled:true},
				]},
				{type: "hidden", name: "flag"}
			],
			buttons : {
				right : {
					btnSubsdCopy : {label : "공시지원금 복사"},
					btnInit : { label : "초기화" },
					btnAppendRow : { label : "추가" }
				}
			},
			onChange : function(name, value) {
				var form = this;

				switch(name){
					case "applStrtDt" :
						if(PAGE.GRID6.getRowsNum() > 0){
							/* mvno.ui.confirm("기준일자 변경시 데이터 초기화됩니다", function(result){
								console.log("okCallback=" + okCallback + result);
								PAGE.GRID6.clearAll();
							}); */
							mvno.ui.alert("시작일자가 변경되어 데이터 초기화됩니다");
							PAGE.GRID6.clearAll();
						}
						break;
						
					case "rateGrpYn" :
						if(form.isItemChecked("rateGrpYn") == true){
							form.setItemValue("rateCd", "");
							form.disableItem("rateCd");
							form.enableItem("rateGrpCd");
							mvno.ui.alert("요금제그룹에 등록된 요금제가 없는 경우<br /> 공시지원금 생성이 되지 않습니다.");
						}else{
							form.setItemValue("rateGrpCd", "");
							form.enableItem("rateCd");
							form.disableItem("rateGrpCd");
						}
					break;
					
					case "operAll" :
						enbFrmItm(form, name, operTypeItmList);
					break;
					
					case "agrmAll" :
						enbFrmItm(form, name, agrmTrmItmList);
					break;
				}
			},
			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {
					case "btnSubsdCopy":
						if(form.getItemValue("applStrtDt") == null || form.getItemValue("applStrtDt") == ""){
							mvno.ui.alert("시작일자를 선택하세요");
							return;
						}
						
						var strtDt = new Date(form.getItemValue("applStrtDt")).format("yyyymmdd");
						
						if(strtDt < today){
							mvno.ui.alert("시작일자는 오늘 이후 날짜로 입력하세요");
							return;
						}
						
						if(form.getItemValue("rateCd") == ""){
							mvno.ui.alert("요금제를 선택하세요");
							return;
						}
						
						var aryOperType = [];
						var aryAgrmTrm  = [];
						
						aryOperType = chkValidate(form, "operAll", operTypeItmList);
						
						if( aryOperType.length < 1) {
							mvno.ui.alert("업무구분을 선택하세요");
							return;
						}
						
						aryAgrmTrm = chkValidate(form, "agrmAll" ,agrmTrmItmList);
						
						if( aryAgrmTrm.length < 1) {
							mvno.ui.alert("약정기간을 선택하세요");
							return;
						}
						
						mvno.ui.disableButton("FORM4" , "btnAppendRow");
						
						var sdata = {
							applStrtDt : form.getItemValue("applStrtDt").format("yyyymmdd"),
							rateCd : form.getItemValue("rateCd"),
							operTypeNm : aryOperType[0],
							agrmTrmNm : aryAgrmTrm[0],
							operType : aryOperType[1], 
							agrmTrm : aryAgrmTrm[1]
						}
						
						PAGE.GRID6.clearAll();
						PAGE.GRID6.list(ROOT + "/sale/subsdMgmt/getCopySubsdAmtInfo.json", sdata);
						
						break;
				
					case "btnInit":
						PAGE.FORM4.clear();
						
						initSubsdAmtReg();
						
						break;

					case "btnAppendRow":
						if(form.getItemValue("applStrtDt") == null || form.getItemValue("applStrtDt") == ""){
							mvno.ui.alert("시작일자를 선택하세요");
							return;
						}
						
						var strtDt = new Date(form.getItemValue("applStrtDt")).format("yyyymmdd");
						if(strtDt < today){
							mvno.ui.alert("시작일자는 오늘 이후 날짜로 입력하세요");
							return;
						}
						
						if(form.isItemChecked("rateGrpYn") == true){
							if(form.getItemValue("rateGrpCd") == ""){
								mvno.ui.alert("요금제그룹을 선택하세요");
								return;
							}
						}else{
							if(form.getItemValue("rateCd") == ""){
								mvno.ui.alert("요금제를 선택하세요");
								return;
							}
						}
						
						if(form.getItemValue("prdtId") == ""){
							mvno.ui.alert("단말모델을 선택하세요");
							return;
						}
						
						if(form.getItemValue("subsdAmt") == null || form.getItemValue("subsdAmt") == ""){
							mvno.ui.alert("공시지원금을 입력하세요");
							return;
						}
						
						if(Number(form.getItemValue("subsdAmt")) < 0){
							mvno.ui.alert("공시지원금은 0 보다 작을 수 없습니다.");
							return;
						}
						
						var aryOperType = [];
						var aryAgrmTrm  = [];
						
						aryOperType = chkValidate(form, "operAll", operTypeItmList);
						
						if( aryOperType.length < 1) {
							mvno.ui.alert("업무구분을 선택하세요");
							return;
						}
						
						aryAgrmTrm = chkValidate(form, "agrmAll" ,agrmTrmItmList);
						
						if( aryAgrmTrm.length < 1) {
							mvno.ui.alert("약정기간을 선택하세요");
							return;
						}
						
						// 단말/요금제 중복여부 체크
						for(var i=0; i<PAGE.GRID6.getRowsNum(); i++){
							var data = PAGE.GRID6.getSelectedRowData(i);
							
							if(form.isItemChecked("rateGrpYn") == true){
								if(data.prdtId == form.getItemValue("prdtId") && data.rateGrpCd == form.getItemValue("rateGrpCd")){
									mvno.ui.alert("중복된 요금제그룹이 존재합니다");
									return;
								}
							}else{
								if(data.prdtId == form.getItemValue("prdtId") && data.rateCd == form.getItemValue("rateCd")){
									mvno.ui.alert("중복된 요금제코드가 존재합니다");
									return;
								}
							}
						}
						
						mvno.ui.disableButton("FORM4" , "btnSubsdCopy");
						
						var prdtNmList = form.getSelect("prdtId")
						var prdtFullNm = prdtNmList.options[prdtNmList.selectedIndex].text
						var prdtNm     = prdtFullNm.substr(0,prdtFullNm.indexOf("["));
						
						var gdata = {
								applStrtDt : form.getItemValue("applStrtDt").format("yyyymmdd"),
								rateGrpYn : form.isItemChecked("rateGrpYn") == true ? "Y" : "N",
								rateGrpCd : form.getItemValue("rateGrpCd"),
								rateCd : form.getItemValue("rateCd"),
								prdtId : form.getItemValue("prdtId"),
								prdtNm : prdtNm,
								oldYn : form.isItemChecked("oldYn") == true ? "Y" : "N",
								subsdAmt : form.getItemValue("subsdAmt"),
								operTypeNm : aryOperType[0],
								agrmTrmNm : aryAgrmTrm[0],
								operType : aryOperType[1], 
								agrmTrm : aryAgrmTrm[1]
						}
						
						PAGE.GRID6.appendRow(gdata);
						
						
						break;

					case "btnClose" :
						mvno.ui.closeWindowById(form);   
						break;

				}

			},
			onValidateMore : function (data){

			}
		},
		//-------------------------------------------------------------------------------------
		//공시지원금목록
		GRID6 : {
			css : {
				height : "280px"
			},
			title : "공시지원금목록",
			header : "시작일자,그룹코드,요금제코드,제품ID,제품명,중고여부,공시지원금,그룹적용여부,약정기간,업무구분,업무구분코드,약정기간코드",
			columnIds : "applStrtDt,rateGrpCd,rateCd,prdtId,prdtNm,oldYn,subsdAmt,rateGrpYn,agrmTrmNm,operTypeNm,operType,agrmTrm",
			colAlign : "center,center,center,center,left,center,right,center,center,center,center,center",
			colTypes : "roXd,ro,ro,ro,ro,ro,ron,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,int,str,str,str,str,str",
			widths : "100,100,100,100,150,100,100,80,90,200,10,10",
			paging: false,
			multiCheckbox : true,
			hiddenColumns : [10,11],
			buttons : {
				left : {
					btnInit06 : { label : "초기화" }
				},
				right : {
					btnMDelete06 : { label : "삭제" },
					btnMSave06 : { label : "저장" },
					btnClose06 : { label : "닫기" }
				}
			},
			onButtonClick : function(btnId) {
				var grid = this;
				
				switch(btnId){
				
					case "btnInit06" :
						
						PAGE.GRID6.clearAll();
						
						mvno.ui.enableButton("FORM4" , "btnAppendRow");
						mvno.ui.enableButton("FORM4" , "btnSubsdCopy");
						
						$('#GRID6 input:checkbox').prop('checked',false);
						
						break;
						
					case "btnMDelete06" :
						var rowIds = this.getCheckedRows(0);
						
						if(!rowIds){
							mvno.ui.alert("ECMN0003");
							return;
						}else{
							this.deleteRowByIds(rowIds);
						}
						
						$('#GRID6 input:checkbox').prop('checked',false);
						
						break;
						
					case "btnMDelete06" :
						var rowIds = this.getCheckedRows(0);
						
						if(!rowIds){
							mvno.ui.alert("ECMN0003");
							return;
						}else{
							this.deleteRowByIds(rowIds);
						}
						
						break;
						
					case "btnMSave06" :
						
						var rowIds = this.getCheckedRows(0);
						
						if(!rowIds){
							mvno.ui.alert("ECMN0003");
							return;
						}else{
							
							var sdata = this.classifyRowsFromIds(rowIds);
							//mvno.ui.confirm("개통유형(신규/번호이동),<br />약정기간(24,30,36) 에 대하여<br />공시지원금 정보를 일괄생성하시겠습니까?", function() {
							mvno.ui.confirm("공시지원금 정보를 생성하시겠습니까?", function() {
								mvno.cmn.ajax4json(ROOT + "/sale/subsdMgmt/insertSubsdAmtGrpList.json", sdata, function(){
									mvno.ui.notify("NCMN0006");
									PAGE.GRID6.clearAll();
									
									mvno.ui.enableButton("FORM4" , "btnAppendRow");
									mvno.ui.enableButton("FORM4" , "btnSubsdCopy");
									
									$('#GRID6 input:checkbox').prop('checked',false);
								}, {timeout:180000});
							});
						}
						
						break;
						
					case "btnClose06" :
						mvno.ui.closeWindowById("GROUP2");
						break;
				}
			}
		},
		// --------------------------------------------------------------------------
		//공시지원금수정
		FORM5 : {
			title : "",
			items : [
				{type: "settings", position: "label-left", labelWidth: "80"},
				{type: "block", blockOffset: 0, list: [
					{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "applStrtDt", label: "시작일자", calendarPosition: "bottom", offsetLeft: 10, required:true, disabled:true},
					{type: "newcolumn"},
					{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "applEndDt", label: "종료일자", calendarPosition: "bottom", offsetLeft: 20, required:true, disabled:true},
				]},
				{type: "block", blockOffset: 0, list: [
					{type: "input", name: "prdtNm", label: "단말모델", required: true, offsetLeft: 10, disabled:true},
					{type: "newcolumn"},
					{type: "input", name: "oldNm", label: "중고여부",  offsetLeft:20, disabled:true}
				]},
				{type: "block", blockOffset: 0, list: [
					{type: "input", name: "rateNm", label: "요금제",  offsetLeft:10, disabled:true},
					{type: "newcolumn"},
					{type: "input", name: "subsdAmt", label: "공시지원금",  offsetLeft: 20, validate: "ValidInteger", required:true, maxLength:10, numberFormat:"0,000,000,000", userdata:{align:"right"}}
				]},
				{type: "hidden", name: "rateCd"},
				{type: "hidden", name: "prdtId"},
				{type: "hidden", name: "prdtCode"},
				{type: "hidden", name: "oldYn"}
			],
			buttons : {
				center : {
					btnSave05 : { label : "저장" },
					btnClose05 : { label : "닫기" }
				}
			}
			,onButtonClick : function(btnId) {
				var form = this;
	
				switch(btnId){
					case "btnSave05":
						
						// 완료된 지원금정보는 수정하면 안됨
						if(new Date(form.getItemValue("applEndDt")).format("yyyymmdd") <= today){
							mvno.ui.alert("완료일이 현재일 이전인 경우 공시지원금 정보를 수정할 수 없습니다");
							return;
						}
						
						mvno.cmn.ajax(ROOT + "/sale/subsdMgmt/updateSubsdAmtInfo.json", form, function(){
							mvno.ui.notify("NCMN0004");
							PAGE.GRID5.refresh();
							PAGE.GRID1.clearAll();
							mvno.ui.closeWindowById("FORM5");
						});
						break;
	
					case "btnClose05" :
						mvno.ui.closeWindowById("FORM5");
						break;
				}
			}
		}
		
		, FORM6 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type: "block", blockOffset: 0, list: [
					{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "applStrtDt", label: "시작일자", calendarPosition: "bottom", width:100, offsetLeft: 20, required:true}
				]}
				, {type: "block", blockOffset: 0, list: [
					{type: "label", label: "업무구분", labelWidth : 50, offsetLeft:20}
					, {type: "newcolumn"}
					, {type: "checkbox", name: "operAll", label: "전체", width:20, labelWidth : 30, offsetLeft:10}
					, {type: "newcolumn"}
					, {type: "checkbox", name: "nacYn", label: "신규", width:20, labelWidth:30, offsetLeft:3, disabled:true}
					, {type: "newcolumn"},
					, {type: "checkbox", name: "mnpYn", label: "번호이동", width:40, labelWidth:50, offsetLeft:3, disabled:true}
					, {type: "newcolumn"}
					, {type: "checkbox", name: "hcnYn", label: "기기변경", width:40, labelWidth:50, offsetLeft:3, disabled:true}
				]}
				, {type: "block", blockOffset: 0, list: [
					, {type: "label", label: "약정기간", labelWidth : 50, offsetLeft:20}
					, {type: "newcolumn"}
					, {type: "checkbox", name: "agrmAll", label: "전체", width:20, labelWidth : 30, offsetLeft:10}
					, {type: "newcolumn"}
					, {type: "checkbox", name: "agrm12Yn", label: "12", width:20, labelWidth:15, offsetLeft:3, disabled:true}
					, {type: "newcolumn"}
					, {type: "checkbox", name: "agrm18Yn", label: "18", width:20, labelWidth:15, offsetLeft:3, disabled:true}
					, {type: "newcolumn"}
					, {type: "checkbox", name: "agrm24Yn", label: "24", width:20, labelWidth:15, offsetLeft:3, disabled:true}
					, {type: "newcolumn"},
					, {type: "checkbox", name: "agrm30Yn", label: "30", width:20, labelWidth:15, offsetLeft:3, disabled:true}
					, {type: "newcolumn"}
					, {type: "checkbox", name: "agrm36Yn", label: "36", width:20, labelWidth:15, offsetLeft:3, disabled:true}
				]}
				, { type: "block", blockOffset: 0, list: [
					{ type : "newcolumn", offset : 20 }
					, { type: "upload" , label: "" , name: "file_upload1" , inputWidth: 350, url: "/sale/subsdMgmt/regSubsdExcelUp.do" , userdata: { limitSize:10000 } , autoStart: true } 
				]}
				, {type: "hidden", name: "fileName"}
			]
			, buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			}
			, onChange : function(name, value) {
				var form = this;
				
				switch(name){
					case "operAll" :
						enbFrmItm(form, name, operTypeItmList2);
						break;
						
					case "agrmAll" :
						enbFrmItm(form, name, agrmTrmItmList2);
						break;
				}
			}
			, onUploadFile : function(realName, serverName) {
				var form = this;
				
				form.setItemValue("fileName", serverName);
			}
			, onFileRemove : function(realName, serverName) {
				var form = this;
				
				form.setItemValue("fileName", "");
			}
			, onButtonClick : function(btnId) {
				
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				
				switch (btnId) {
					
					case "btnSave" :
						
						if(form.getItemValue("applStrtDt") == null || form.getItemValue("applStrtDt") == ""){
							mvno.ui.alert("시작일자를 선택하세요");
							return;
						}
						
						var strtDt = new Date(form.getItemValue("applStrtDt")).format("yyyymmdd");
						
						if(strtDt < today){
							mvno.ui.alert("시작일자는 오늘 이후 날짜로 입력하세요");
							return;
						}
						
						if(!form.isItemChecked("operAll") && !form.isItemChecked("nacYn") && !form.isItemChecked("mnpYn") && !form.isItemChecked("hcnYn")) {
							mvno.ui.alert("업무구분을 선택하세요");
							return;
						}
						
						if(!form.isItemChecked("agrmAll") && !form.isItemChecked("agrm12Yn") && !form.isItemChecked("agrm18Yn") 
							&& !form.isItemChecked("agrm24Yn") && !form.isItemChecked("agrm30Yn") && !form.isItemChecked("agrm36Yn")) {
							mvno.ui.alert("약정기간을 선택하세요");
							return;
						}
						
						if(mvno.cmn.isEmpty(form.getItemValue("fileName"))){
							mvno.ui.alert("첨부된 파일이 없습니다");
							return;
						}
						
						var url = ROOT + "/rcp/dlvyMgmt/regTmpOfclSubsdData.json";
						var userOptions = {timeout:300000};
						
						mvno.cmn.ajax(url, form, function(resultData) {
							mvno.ui.notify("NCMN0006");
							PAGE.GRID5.refresh();
							PAGE.GRID1.clearAll();
							mvno.ui.closeWindowById("FORM6");
						}, userOptions);
						
						break;
						
					case "btnClose":
						
						mvno.ui.closeWindowById("FORM6");
						
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
		mvno.ui.createGrid("GRID5");

		//mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0046"}, PAGE.FORM1, "rateGrpCd");
		//mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0053"}, PAGE.FORM1, "agrmTrm");
		mvno.cmn.ajax4lov(ROOT+"/sale/rateMgmt/getRateComboList.json", "", PAGE.FORM1, "rateCd");
		mvno.cmn.ajax4lov(ROOT+"/sale/subsdMgmt/getPrdtComboList.json", "", PAGE.FORM1, "prdtId");

		PAGE.FORM1.setItemValue("stdrDt", today);

	}

};

function initSubsdAmtReg(){
	PAGE.FORM4.setItemValue("operAll", 1);
	PAGE.FORM4.setItemValue("agrmAll", 1);
	
	for(var idx in operTypeItmList){
		PAGE.FORM4.disableItem(operTypeItmList[idx]);	
	}
	
	for(var idx in agrmTrmItmList){
		PAGE.FORM4.disableItem(agrmTrmItmList[idx]);	
	}
}

function enbFrmItm(form, data, aryData){
	if(form.isItemChecked(data)){
		for(var idx in aryData) {
			form.setItemValue(aryData[idx], 0);
			form.disableItem(aryData[idx]);
		}
	}else{
		for(var idx in aryData) {
			form.enableItem(aryData[idx]);
		}
	}	
}

function chkValidate(form, data, aryData){
	var rsltAryData = [];
	var dataNm = "";
	var dataCd = "";
	
	if(form.isItemChecked(data)) {
		dataNm = form.getItemLabel(data);
		dataCd = form.getItemValue(data);
	}else{
		for(var idx in aryData) {
			if(form.isItemChecked(aryData[idx])) {
				if(dataNm != "") {
					dataNm = dataNm + "/";
				}
				
				dataNm = dataNm + form.getItemLabel(aryData[idx]);
				
				dataCd = dataCd + form.getItemValue(aryData[idx]) + "|";
			}
		}
	}
	
	if(dataNm != "" && dataCd != ""){
		rsltAryData.push(dataNm);
		rsltAryData.push(dataCd);
	}
	
	return rsltAryData;
}

</script>