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
						,{type:"select", width:120, label:"검색구분",name:"searchGb", offsetLeft:40, options:[{value:'', text:'- 전체 -', selected:true}, {value:'pRateCd', text:'요금제코드'}, {value:'pRateNm', text:'요금제명'}, {value:'rRateCd', text:'부가서비스코드'}, {value:'rRateNm', text:'부가서비스명'}]}
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
					
				},
				
				onChange : function(name, value) {
					if(name == "searchGb") {
						PAGE.FORM1.setItemValue("searchVal", "");

						if(value == null || value == "") {
							PAGE.FORM1.setItemValue("searchVal", "");
							PAGE.FORM1.disableItem("searchVal");
							PAGE.FORM1.enableItem("stdrDt");
							PAGE.FORM1.setItemValue("stdrDt", today);
						}
						else {
							PAGE.FORM1.enableItem("searchVal");
							//기준일자 disable
							PAGE.FORM1.setItemValue("stdrDt", "");
							PAGE.FORM1.disableItem("stdrDt");
						}
					}
				},
				
				onButtonClick : function(btnId) {
					
					var form = this;
					
					switch(btnId) {
						
						case "btnSearch" :
														
							form.setItemValue("searchVal", form.getItemValue("searchVal").trim());
							PAGE.GRID1.list(ROOT + "/sale/rateMgmt/getRateCombMappList.json", form);
							
							break;
					}
				}
			},
			
			GRID1 : {
				 title      : "결합요금제 목록"
				,css        : {height : "530px"}
				,header     : "요금제코드,요금제명,부가서비스코드,부가서비스명,적용시작,적용종료,등록자,등록일시,수정자,수정일자"
				,columnIds  : "pRateCd,pRateNm,rRateCd,rRateNm,appStartDt,appEndDt,regNm,regstDttm,rvisnNm,rvisnDttm"
				,widths     : "120,246,120,246,110,110,100,100,100,100"
				,colAlign   : "left,left,left,left,center,center,left,center,left,center"
				,colTypes   : "ro,ro,ro,ro,roXd,roXd,ro,roXd,ro,roXd"
				,colSorting : "str,str,str,str,str,str,str,str,str,str"
				,paging     : true
				,showPagingAreaOnInit: true
				,pageSize: 20
				//,multiCheckbox : true
				,buttons : {
					right : {
						btnReg : { label : "등록" },
						btnMod : { label : "수정" }
					},
					hright : {
						btnExcel : { label : "엑셀다운로드" }
					}
				}
				,onRowSelect : function(rowId, celInd) {
					var rowData = this.getRowData(rowId);
				}
				, onRowDblClicked : function(rowId, celInd) {
					
					mvno.ui.createForm("FORM2");
					mvno.ui.createGrid("GRID3");
					mvno.ui.createGrid("GRID4");
												
					var rowData = PAGE.GRID1.getSelectedRowData();
					
					PAGE.FORM2.setItemValue("appStrDt", rowData.appStartDt);
					PAGE.FORM2.setItemValue("appEndDt", rowData.appEndDt);
					PAGE.FORM2.setItemValue("rateCd", rowData.pRateCd);
					PAGE.FORM2.setItemValue("flag", 'U');
					PAGE.FORM2.disableItem("appStrDt");
					
					PAGE.GRID3.list(ROOT + "/sale/rateMgmt/getRatePListAll.json", {rateCd:rowData.pRateCd});
					PAGE.GRID4.list(ROOT + "/sale/rateMgmt/getRateRListAll.json", {rateCd:rowData.rRateCd});
					
					mvno.ui.popupWindowById("GROUP1", "결합요금제등록", 940, 670, {

						onClose: function(win) {
							PAGE.GRID3.clearAll();
							PAGE.GRID4.clearAll();
							win.closeForce();
							PAGE.FORM2.enableItem("appStrDt");
							PAGE.GRID1.refresh();
						}
					});
				}
				, onButtonClick : function(btnId, selectedData) {
					
					switch(btnId) {
						case "btnExcel":
						
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}
						
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download(ROOT + "/sale/rateMgmt/getRateCombMappListExcel.json?menuId=MSP2001014",true,{postData:searchData});
						
						break;
						
						case "btnReg" :
														
							
							mvno.ui.createForm("FORM2");
							mvno.ui.createGrid("GRID3");
							mvno.ui.createGrid("GRID4");
							PAGE.GRID3.attachHeader("#text_filter,#text_filter,#select_filter,#select_filter");
							PAGE.GRID4.attachHeader("#text_filter,#text_filter,#select_filter,#select_filter");
							
							var time = new Date().getTime();
							time = time + 1000 * 3600 * 24 * 30;
							var appEndDt = new Date(time);
							
							PAGE.FORM2.setItemValue("appStrDt", today);
							PAGE.FORM2.setItemValue("appEndDt", appEndDt);
							PAGE.FORM2.setItemValue("flag", 'I');
							
							PAGE.GRID3.list(ROOT + "/sale/rateMgmt/getRatePListAll.json");
							PAGE.GRID4.list(ROOT + "/sale/rateMgmt/getRateRListAll.json");
							
							mvno.ui.popupWindowById("GROUP1", "결합요금제등록", 940, 670, {

								onClose: function(win) {
									$("tr td div input").val("");
									$("tr td div select").val("");
									
									PAGE.GRID3.clearAll();
									PAGE.GRID4.clearAll();
									PAGE.GRID3.detachHeader(1);
									PAGE.GRID4.detachHeader(1);
									win.closeForce();
									
									PAGE.GRID1.refresh();
								}
							});

							break;
						case "btnMod" :
							
							if (!PAGE.GRID1.getSelectedRowData()) {
								mvno.ui.alert("수정 할 요금제를 먼저 선택하세요.");
								break;
							}
							
							mvno.ui.createForm("FORM2");
							mvno.ui.createGrid("GRID3");
							mvno.ui.createGrid("GRID4");
														
							var rowData = PAGE.GRID1.getSelectedRowData();
							
							PAGE.FORM2.setItemValue("appStrDt", rowData.appStartDt);
							PAGE.FORM2.setItemValue("appEndDt", rowData.appEndDt);
							PAGE.FORM2.setItemValue("rateCd", rowData.pRateCd);
							PAGE.FORM2.setItemValue("flag", 'U');
							PAGE.FORM2.disableItem("appStrDt");
							
							PAGE.GRID3.list(ROOT + "/sale/rateMgmt/getRatePListAll.json", {rateCd:rowData.pRateCd});
							PAGE.GRID4.list(ROOT + "/sale/rateMgmt/getRateRListAll.json", {rateCd:rowData.rRateCd});
							
							mvno.ui.popupWindowById("GROUP1", "결합요금제등록", 940, 670, {

								onClose: function(win) {
									$("tr td div input").val("");
									$("tr td div select").val("");
									
									PAGE.GRID3.clearAll();
									PAGE.GRID4.clearAll();
									win.closeForce();
									PAGE.FORM2.enableItem("appStrDt");
									PAGE.GRID1.refresh();
								}
							});

							break;
					}
				}
			},
			
					
			FORM2 : {
				title : "결합요금제정보",
				items : [
							{type:"settings", position:"label-left", labelAlign:"left",  blockOffset:0},
								{type:"block", blockOffset:0, list: [
									{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"appStrDt", label:"적용시작일자", calendarPosition: 'bottom', width:100},
									{type:"newcolumn"},
									{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"appEndDt", label:"적용종료일자", calendarPosition: 'bottom', width:100 , offsetLeft:40},
									 {type:"hidden", name:"flag"},
									 {type:"hidden", name:"rateCd"}
							  ]}
						]
				
			},
			
			GRID3 : {
				css : {
					height : "430px"
				},
				title : "결합 대상요금제(택1)",
				header : "요금제코드,요금제명,출시일자,종료일자",
				columnIds : "rateCd,rateNm,applStrtDt,applEndDt",
				colAlign : "left,left,center,center",
				colTypes   : "ro,ro,roXd,roXd",
				colSorting : "str,str,str,str",
				widths : "85,130,90,90",
				paging: false,
				onRowSelect: function(rowId){
					
				},
				buttons : {
					right : {
						btnClose: { label : "닫기" }
					}
				},
				onRowDblClicked : function(rowId, selectedData) {
					
				},
				onButtonClick : function(btnId) {
					var form = this;

					switch(btnId){

						case "btnClose" :
							mvno.ui.closeWindowById("GROUP1");
							
							break;
					}
				}
			},
			
			GRID4 : {
				css : {
					height : "430px"
				},
				title : "대상 데이터 부가서비스(택1)",
				header : "부가서비스코드,부가서비스명,출시일자,종료일자",
				columnIds : "rateCd,rateNm,applStrtDt,applEndDt",
				colAlign : "left,left,center,center",
				colTypes   : "ro,ro,roXd,roXd",
				colSorting : "str,str,str,str",
				widths : "105,130,80,80",
				paging: false,
				onRowSelect: function(rowId){

				},
				onRowDblClicked : function(rowId, selectedData) {
					
				},
				buttons : {
					right : {
						btnSave: { label : "확정" }
					}
				},
				onButtonClick : function(btnId) {
					
					var form = this;
					
					switch(btnId){
						case "btnSave" :
							
							if(PAGE.FORM2.getItemValue("flag") == "I"){
								if (!PAGE.GRID3.getSelectedRowData()) {
									mvno.ui.alert("요금제를 선택하세요.");
									return;
								}
								
								if (!PAGE.GRID4.getSelectedRowData()) {
									mvno.ui.alert("부가서비스를 선택하세요.");
									return;
								}
								
								if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("appStrDt"))){
									mvno.ui.alert("적용시작일자를 입력해주세요.");
									return;
								}
							}
							
							if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("appEndDt"))){
								mvno.ui.alert("적용종료일자를 입력해주세요.");
								return;
							}
							
							if (PAGE.FORM2.getItemValue("appStrDt") > PAGE.FORM2.getItemValue("appEndDt")) {
								mvno.ui.alert("적용시작일자가 적용종료일자보다 클 수 없습니다.");
								return;
							}
							
							
							var rowData1 = PAGE.GRID3.getSelectedRowData();
							var rowData2 = PAGE.GRID4.getSelectedRowData();
							var url; 
							var msg;
							var params;
							
							if(PAGE.FORM2.getItemValue("flag") == "I"){
								url = "/sale/rateMgmt/insertRateCombMapp.json";
								msg = "NCMN0001";	
								params = {
									"pRateCd": rowData1.rateCd
									,"rRateCd": rowData2.rateCd
									,"appStrDt":  new Date(PAGE.FORM2.getItemValue("appStrDt")).format("yyyymmdd")
									,"appEndDt":  new Date(PAGE.FORM2.getItemValue("appEndDt")).format("yyyymmdd")
								};
							}else if (PAGE.FORM2.getItemValue("flag") == "U"){
								url = "/sale/rateMgmt/updateRateCombMapp.json";
								msg = "NCMN0002";	
								params = {
										"pRateCd": PAGE.FORM2.getItemValue("rateCd")
										,"appStrDt":  new Date(PAGE.FORM2.getItemValue("appStrDt")).format("yyyymmdd")
										,"appEndDt":  new Date(PAGE.FORM2.getItemValue("appEndDt")).format("yyyymmdd")
									};
							} 
							if(PAGE.FORM2.getItemValue("flag") == "I"){
								mvno.ui.confirm("★요금제 "+"[" + rowData1.rateNm + "]"  + '<br/>' + "★부가서비스 " +  "[" + rowData2.rateNm + "]" +  '<br/>' + "을 저장하시겠습니까?" , function(){
		                      		mvno.cmn.ajax(ROOT + url, params, function(result){
		                      			mvno.ui.notify(msg);
										
										PAGE.GRID3.clearAll();
										PAGE.GRID4.clearAll();
										
										PAGE.GRID3.detachHeader(1);
										PAGE.GRID4.detachHeader(1);
										
										mvno.ui.closeWindowById("GROUP1");
										
										PAGE.GRID1.refresh();
		                      		}, {aysnc:false});
		                    	});
							}else if  (PAGE.FORM2.getItemValue("flag") == "U"){
								mvno.cmn.ajax(ROOT + url, params, function(result) {
									
									mvno.ui.notify(msg);
									
									PAGE.GRID3.clearAll();
									PAGE.GRID4.clearAll();
									
									PAGE.GRID3.detachHeader(1);
									PAGE.GRID4.detachHeader(1);
									
									mvno.ui.closeWindowById("GROUP1");
									
									PAGE.GRID1.refresh();
									
								}, {aysnc:false});
							}
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
				
				
				PAGE.FORM1.disableItem("searchVal");
				
				PAGE.FORM1.setItemValue("stdrDt", today);
			}
			
		};
		
	</script>