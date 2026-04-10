<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name  : rateRelMgmt.jsp
	 * @Description : 서비스관계관리
	 * @
	 * @  수정일	  	수정자			  수정내용
	 * @ ---------   ---------   -------------------------------
	 * @ 2018.06.01  	JUNA		  최초생성
	 * @ 
	 * @author : JUNA
	 * @Create Date : 2018.06.01
	 */
%>
	
	<div id="FORM1" class="section-search"></div>
	<div id="GRID1"></div>
	<div id="GRID2"></div>
	
	<div id="GROUP1" style="display:none;">
		<div id="FORM2" class="section-box"></div>
		<div class="row">
			<div id="GRID3" class="c5"></div>
			<div id="GRID4" class="c5"></div>
		</div>
	</div>
	
	<div id="FORM3" class="section-box"></div>
	
	<script type="text/javascript">
	var today = new Date().format("yyyymmdd");
	
	var DHX = {
			FORM1 : {
				items : [
					 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
					 {type:"block", list:[
					 	 {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"stdrDt", label:"기준일자", calendarPosition: 'bottom', width:100, required:true},
						,{type:"newcolumn"}
						,{type:"select", name:"serviceType", label:"서비스유형", width:90, offsetLeft:40, labelWidth:65}
						,{type:"newcolumn"}
						,{type:"select", width:90, label:"검색구분",name:"searchGb", offsetLeft:40, options:[{value:'', text:'- 전체 -', selected:true}, {value:'rateCd', text:'요금제코드'}, {value:'rateNm', text:'요금제명'}]}
						,{type:"newcolumn"}
						,{type:"input", width:220, name:"searchVal", maxLength:40}
					 ]},
					 {type: "newcolumn"},
					 {type: "button", value: "조회", name: "btnSearch" , className:"btn-search1"}
				],
				
				onValidateMore : function(data) {
					if( data.searchGb != "" && data.searchVal.trim() == ""){
						this.pushError("searchVal", "검색구분", "검색어를 입력하세요");
						PAGE.FORM1.setItemValue("searchVal", "");
					}
					
					if(this.getItemValue("stdrDt", true) == null || this.getItemValue("stdrDt", true) == ""){
						this.pushError("stdrDt","기준일자","기준일자를 선택하세요");
					}
					
				},
				
				onChange : function(name, value) {
					if(name == "searchGb") {
						PAGE.FORM1.setItemValue("searchVal", "");

						if(value == null || value == "") {
							PAGE.FORM1.setItemValue("searchVal", "");
							PAGE.FORM1.disableItem("searchVal");
						}
						else {
							PAGE.FORM1.enableItem("searchVal");
						}
					}
				},
				
				onButtonClick : function(btnId) {
					
					var form = this;
					
					switch(btnId) {
						
						case "btnSearch" :
							PAGE.GRID2.clearAll();
							
							form.setItemValue("searchVal", form.getItemValue("searchVal").trim());
							PAGE.GRID1.list(ROOT + "/sale/rateMgmt/getRateMgmtList.json", form);
							
							break;
					}
				}
			},
			
			GRID1 : {
				 title      : "요금제 목록"
				,css        : {height : "300px"}
				,header     : "요금제코드,요금제명,서비스타입,서비스유형,출시일자,종료일자,선후불,요금제유형,데이터유형"
				,columnIds  : "rateCd,rateNm,serviceType,serviceTypeNm,applStrtDt,applEndDt,payClCdNm,rateTypeNm,dataType"
				,widths     : "120,246,100,100,100,100,80,100,90"
				,colAlign   : "left,left,center,center,center,center,center,center,center"
				,colTypes   : "ro,ro,ro,ro,roXd,roXd,ro,ro,ro"
				,colSorting : "str,str,str,str,str,str,str,str,str"
				,hiddenColumns: [2]
				,paging     : true
				,showPagingAreaOnInit: true
				,pageSize: 20
				//,multiCheckbox : true
				,buttons : {
					hright : {

					}
				}
				,onRowSelect : function(rowId, celInd) {
					var rowData = this.getRowData(rowId);
					PAGE.GRID2.list(ROOT + "/sale/rateMgmt/getRateMgmtRelList.json", {rateCd:rowData.rateCd});
				}
				, onRowDblClicked : function(rowId, celInd) {
				}
				, onButtonClick : function(btnId, selectedData) {
					
					switch(btnId) {
					
					}
				}
			},
						
			GRID2 : {
				 title      : "부가서비스 관계"
				,css        : {height : "190px"}
				,header     : "관계유형,요금제코드,요금제명,출시일자,종료일자,금액" // 8
				,columnIds  : "svcRelTpNm,addSvcCd,rateNm,applStrtDt,applEndDt,baseAmt"
				,widths     : "120,130,276,140,140,130"
				,colAlign   : "center,left,left,center,center,right"
				,colTypes   : "ro,ro,ro,roXd,roXd,ron"
				,colSorting : "str,str,str,str,str,int"
				//,hiddenColumns: [9,10]
				,paging     : true
				,showPagingAreaOnInit: true
				,pageSize: 20
				//,multiCheckbox : true
				,buttons : {
					right : {
						btnReg: { label : "등록" }
					}
				}
				, onRowDblClicked : function(rowId, celInd) {
				}
				, onButtonClick : function(btnId, selectedData) {
					
					switch (btnId) {

						case "btnReg" :
							
							if (!PAGE.GRID1.getSelectedRowData()) {
								mvno.ui.alert("요금제를 먼저 선택하세요.");
								break;
							}
							
							var rowData = PAGE.GRID1.getSelectedRowData();
							
							mvno.ui.createForm("FORM2");
							mvno.ui.createGrid("GRID3");
							mvno.ui.createGrid("GRID4");
							PAGE.GRID3.attachHeader("#text_filter,#text_filter,#select_filter,#select_filter,#numeric_filter");

							PAGE.FORM2.setFormData(rowData);
							PAGE.FORM2.disableItem("serviceTypeNm");
							PAGE.FORM2.disableItem("rateCd");
							PAGE.FORM2.disableItem("rateNm");
							
							PAGE.GRID3.list(ROOT + "/sale/rateMgmt/getRateRelListAll.json", {rateCd:rowData.rateCd});
							//PAGE.GRID4.list(ROOT + "/sale/rateMgmt/getRateMgmtRelList.json", {rateCd:rowData.rateCd});
							PAGE.GRID4.list(ROOT + "/sale/rateMgmt/getRateMgmtRelListPop.json", {rateCd:rowData.rateCd});
							
							mvno.ui.popupWindowById("GROUP1", "서비스관계등록", 940, 670, {

								onClose: function(win) {
									PAGE.GRID3.clearAll();
									PAGE.GRID4.clearAll();
									PAGE.GRID3.detachHeader(1);
									win.closeForce();
									
									PAGE.GRID1.refresh();
									PAGE.GRID2.refresh();
								}
							});

							break;
						
					}
				}
			},
			
			FORM2 : {
				title : "",
				items : [
							{type:"settings", position:"label-left", labelAlign:"left",  blockOffset:0},
								{type:"block", blockOffset:0, list: [
                                     {type:"input", label:"서비스정보", name:"serviceTypeNm", width:120, offsetLeft:5, labelWidth: 70, disable:true},
                                     {type:"hidden", name:"serviceType"},
                                     {type:"newcolumn"},
                                     {type:"input", name:"rateCd", width:120, offsetLeft:5, disable:true},
                                     {type:"newcolumn"},
                                     {type:"input", name:"rateNm", width:350, offsetLeft:5, disable:true}
							  ]}
						]
				
			},
			
			GRID3 : {
				css : {
					height : "430px"
				},
				title : "전체 부가서비스",
				header : "요금제코드,요금제명,출시일자,종료일자,금액",
				columnIds : "addSvcCd,rateNm,applStrtDt,applEndDt,baseAmt",
				colAlign : "left,left,center,center,right",
				colTypes   : "ro,ro,roXd,roXd,ron",
				colSorting : "str,str,str,str,int",
				widths : "80,125,75,75,47",
				paging: false,
				onRowSelect: function(rowId){
					
				},
				buttons : {

				},
				onRowDblClicked : function(rowId, selectedData) {
					var rowData = this.getSelectedRowData();
					
					mvno.ui.createForm("FORM3");
					
					PAGE.FORM3.setItemValue("rateNm", rowData.rateNm);
					PAGE.FORM3.setItemValue("addSvcCd", rowData.addSvcCd);
					PAGE.FORM3.setItemValue("serviceType", PAGE.FORM2.getItemValue("serviceType"));
					PAGE.FORM3.setItemValue("rateCd", PAGE.FORM2.getItemValue("rateCd"));
					PAGE.FORM3.disableItem("rateNm");
					
					mvno.ui.popupWindowById("FORM3", "관계유형선택", 400, 250, {
						onClose: function(win) {
							if (! PAGE.FORM3.isChanged()) return true;
							mvno.ui.confirm("CCMN0005", function(){
								win.closeForce();
							});
						}
					});
				},
				onButtonClick : function(btnId) {
					
				}
			},
			
			GRID4 : {
				css : {
					height : "430px"
				},
				title : "선택된 부가서비스",
				header : "관계유형,요금제코드,요금제명,출시일자,종료일자,금액",
				columnIds : "svcRelTpNm,addSvcCd,rateNm,applStrtDt,applEndDt,baseAmt",
				colAlign : "center,left,left,center,center,right",
				colTypes   : "ro,ro,ro,roXd,roXd,ron",
				colSorting : "str,str,str,str,str,int",
				widths : "60,80,130,80,80,50",
				paging: false,
				onRowSelect: function(rowId){

				},
				onRowDblClicked : function(rowId, selectedData) {
					var rowData = PAGE.GRID4.getSelectedRowData();
										
					mvno.cmn.ajax4confirm("삭제하시겠습니까?",ROOT + "/sale/rateMgmt/deleteRateRel.json", {rateCd:PAGE.FORM2.getItemValue("rateCd"), addSvcCd:rowData.addSvcCd}, function(result) {
						mvno.ui.notify("NCMN0003");
						PAGE.GRID3.refresh();
						PAGE.GRID4.refresh();
					});
				},
				buttons : {
					right : {
						btnClose: { label : "닫기" }
					}
				},
				onButtonClick : function(btnId) {
					var form = this;

					switch(btnId){

						case "btnClose" :
							PAGE.GRID3.clearAll();
							PAGE.GRID4.clearAll();
							
							PAGE.GRID3.detachHeader(1);
							mvno.ui.closeWindowById("GROUP1");

							PAGE.GRID1.refresh();
							PAGE.GRID2.refresh();
							
							break;
					}
				}
			},
			
			FORM3 : {
				title : "",
				items : [
							{type:"settings", position:"label-left", labelAlign:"center",  blockOffset:0},
							{type:"block", blockOffset:0, list: [
							     {type:"input", name:"rateNm", label:"요금제명", labelWidth:80, width:220, disable:true},
							     {type:"hidden", name:"addSvcCd"},
							     {type:"hidden", name:"rateCd"},
							     {type:"hidden", name:"serviceType"}
							]},
							{type:"block", blockOffset:0, list: [
							     {type:"label", label:"관계유형", labelWidth:80, offsetLeft:16},
							     {type:"newcolumn"},
								 {type:"radio", name:"svcRelTp", value:"B", label:"기본", checked:true},
								 {type:"newcolumn"},
								 {type:"radio", name:"svcRelTp", value:"C", label:"선택"}
   							]}
						],
				buttons : {
					 center : {
						btnSave: { label : "추가" },
						btnCancel: { label : "취소" }
					}
				},
				onButtonClick : function(btnId) {
					var form = this;

					switch(btnId){
						case "btnSave" :
							mvno.cmn.ajax(ROOT + "/sale/rateMgmt/insertRateRel.json", form, function(result) {
								mvno.ui.notify("NCMN0001");
								PAGE.GRID3.refresh();
								PAGE.GRID4.refresh();
								mvno.ui.closeWindowById(form, true);
							});

							break;

						case "btnCancel" :
							mvno.ui.closeWindowById(form, true);
							break;
					}
				}
			}
			
		};
		
		var PAGE = {
				
			title : "${breadCrumb.title}",
			breadcrumb : " ${breadCrumb.breadCrumb}",
			
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.ui.createGrid("GRID2");
				
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0018"}, PAGE.FORM1, "serviceType");
				
				PAGE.FORM1.disableItem("searchVal");
				
				PAGE.FORM1.setItemValue("stdrDt", today);
			}
			
		};
		
	</script>