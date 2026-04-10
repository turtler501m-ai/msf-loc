<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_prmt_1007.jsp
	 * @Description : 트리플혜액관리
	 * @
	 * @ 수정일		수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2023.10.30 권오승 최초작성
	 * @author : 
	 * @Create Date : 
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search" ></div>
<!-- 그리드영역 -->
<div id="GRID1"></div>

<div id="GROUP1" style="display:none;">
	<div id="FORM2" class="section-box"></div>
	<div class="row">
		<div id="GRID2" class="c5"></div>
		<div id="GRID3" class="c5"></div>
	</div>
	<div id="FORM3"></div>
</div>
<div id="POPUP1">
	<div id="POPUP1MID" class="section-box"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	var today = new Date().format("yyyymmdd");
	var tomorday = mvno.cmn.getAddDay(today, 1);
	var endDt = mvno.cmn.getAddDay(today, 30);
	
	var vasRowsData
	
	var DHX = {
		// 검색
		FORM1 : { 
			items : [ 
				{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 70, blockOffset : 0 }
				,{ type : "block", list : [
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchBaseDate', label: '기준일자', calendarPosition: 'bottom', readonly:true ,width:100, offsetLeft:10 }
					, {type: "newcolumn", offset:30}
					, {type:"input", width:345, label:"프로모션명", name: "prmtNm", offsetLeft:20}
				]}
				,{ type : "button",name : "btnSearch",value : "조회", className:"btn-search1" } 
			]
			,onButtonClick : function(btnId) {
				
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						
						var url1 = ROOT + "/org/prmtmgmt/getTriplePrmtList.json";
						
						PAGE.GRID1.list(url1, form);
						
					break;
				}
			}
		}	//FORM1 End
		
		, GRID1 : {
			css : { 
				height : "520px"  
			}
			,title : "프로모션목록"
			,header : "프로모션ID,프로모션명,시작일자,종료일자,등록자,등록일시,수정자,수정일시"
			,columnIds : "prmtId,prmtNm,strtDt,endDt,regstNm,regstDttm,rvisnNm,rvisnDttm"
			,widths : "120,265,110,110,100,145,100,145"
			,colAlign : "Center,Left,Center,Center,Center,Center,Center,Center"
			,colTypes : "ro,ro,roXd,roXd,ro,roXdt,ro,roXdt"
			,colSorting : "str,str,str,str,str,str,str,str"
			,paging : true
			,pageSize : 20
			,buttons : {
				left : {
					btnChrgPrmtCopy : { label : "프로모션복사" }
				},
				right : {
					btnReg : { label : "등록" }
					,btnMod : { label : "수정" }
				}
			}
			,onRowDblClicked : function(rowId, celInd) {
				// 수정버튼 누른것과 같이 처리
				this.callEvent("onButtonClick", ["btnMod"]);
			}
			,onButtonClick : function(btnId, selectedData) {
				switch (btnId) {

					case "btnChrgPrmtCopy" :
						
						var data = PAGE.GRID1.getSelectedRowData();
						
						if(data == null) {
							mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
							return;
						}
						
						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID2");	// 요금제
						mvno.ui.createGrid("GRID3");	// 부가서비스
						mvno.ui.createForm("FORM3");	// BUTTONS
												
						PAGE.GRID2.clearAll();
						PAGE.GRID3.clearAll();
						
						
						PAGE.GRID2.list(ROOT + "/org/prmtmgmt/getTriplePrmtSocList.json", {prmtId:data.prmtId});
						PAGE.GRID3.list(ROOT + "/org/prmtmgmt/getTriplePrmtAddList.json", {prmtId:data.prmtId});
						
						var fData = {
							prmtNm : data.prmtNm + " 복사본"
						};
						
						
						PAGE.FORM2.setFormData(fData, true);
						
						PAGE.FORM2.disableItem("prmtId");
						PAGE.FORM2.enableItem("prmtNm");
						PAGE.FORM2.enableItem("strtDt");
						PAGE.FORM2.setItemValue("strtDt", tomorday);
						PAGE.FORM2.setItemValue("endDt", endDt);
						
						PAGE.GRID2.setEditable(true);
						PAGE.GRID3.setEditable(true);
						
						mvno.ui.hideButton("GRID3" , 'btnDel');
						mvno.ui.showButton("GRID3" , 'btnReg');
						mvno.ui.hideButton("GRID3" , 'btnEndDt');
						mvno.ui.showButton("GRID2" , 'btnUpExcel');
						mvno.ui.showButton("GRID2" , 'btnDnSocExcel');
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "트리플할인 프로모션 복사", 750, 600, {
							onClose: function(win) {
								if (!PAGE.FORM2.isChanged()) {
									return true;
								}
								mvno.ui.confirm("CCMN0005", function() {
									win.closeForce();
								});
							}
						});
						
						break;
					
					case "btnReg":
						
						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID2");	// 요금제
						mvno.ui.createGrid("GRID3");	// 부가서비스
						mvno.ui.createForm("FORM3");	// BUTTONS
						
						PAGE.GRID2.clearAll();
						PAGE.GRID3.clearAll();
						
						PAGE.FORM2.setFormData(true);
						
						PAGE.GRID2.list(ROOT + "/org/prmtmgmt/getTriplePrmtSocList.json", "");
						PAGE.GRID3.list(ROOT + "/org/prmtmgmt/getTriplePrmtAddList.json", "");
						
						
						PAGE.FORM2.disableItem("prmtId");
						PAGE.FORM2.enableItem("prmtNm");
						PAGE.FORM2.setItemValue("strtDt", today);
						PAGE.FORM2.setItemValue("endDt", endDt);
						
						PAGE.GRID2.setEditable(true);
						PAGE.GRID3.setEditable(true);
						
						mvno.ui.showButton("GRID3" , 'btnReg');
						mvno.ui.hideButton("GRID3" , 'btnDel');
						mvno.ui.hideButton("GRID3" , 'btnEndDt');
						mvno.ui.showButton("GRID2" , 'btnUpExcel');
						mvno.ui.showButton("GRID2" , 'btnDnSocExcel');
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "트리플할인 프로모션 등록", 750, 600, {
							onClose: function(win) {
								if (!PAGE.FORM2.isChanged()) {
									return true;
								}
								mvno.ui.confirm("CCMN0005", function() {
									win.closeForce();
								});
							}
						});
						
						break;
						
					case "btnMod":
						
						var data = PAGE.GRID1.getSelectedRowData();
						
						if(data == null) {
							mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
							return;
						}
						
						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID2");
						mvno.ui.createGrid("GRID3");
						mvno.ui.createForm("FORM3");	// BUTTONS
												
						PAGE.GRID2.clearAll();
						PAGE.GRID3.clearAll();
												
						PAGE.GRID2.list(ROOT + "/org/prmtmgmt/getTriplePrmtSocList.json", {prmtId:data.prmtId});
						PAGE.GRID3.list(ROOT + "/org/prmtmgmt/getTriplePrmtAddList.json", {prmtId:data.prmtId});
						
						
						var fData = {
							prmtId : data.prmtId
							,prmtNm : data.prmtNm
							,strtDt : data.strtDt
							,endDt : data.endDt
						};
						
						
						PAGE.FORM2.setFormData(fData, true);
						
						PAGE.FORM2.disableItem("strtDt");
						PAGE.FORM2.disableItem("prmtId");
						PAGE.FORM2.disableItem("prmtNm");
						PAGE.GRID2.setEditable(false);
						PAGE.GRID3.setEditable(false);
						
						mvno.ui.showButton("GRID3" , 'btnDel');
						mvno.ui.showButton("GRID3" , 'btnEndDt');
						mvno.ui.hideButton("GRID3" , 'btnReg');
						mvno.ui.hideButton("GRID2" , 'btnUpExcel');
						mvno.ui.hideButton("GRID2" , 'btnDnSocExcel');
						
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("GROUP1", "트리플할인 프로모션 수정", 750, 600, {
							onClose: function(win) {
								if (!PAGE.FORM2.isChanged()) {
									return true;
								}
								mvno.ui.confirm("CCMN0005", function() {
									win.closeForce();
								});
							}
						//,maximized:true
						});
						
						break;
					
				}
			}
		}	//GRID1 End
		
		,FORM2 : {
			items : [
				{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 80, blockOffset : 0 }
				,{ type : "block", name: "INFO", labelWidth : 30 , list : [
					{type:"input", width:100, label:"프로모션명", name:"prmtId", width:110}
					, {type: 'newcolumn'}
					, {type:"input", width:200, label:"", name: "prmtNm", width:400}
				]}
				,{ type : "block", labelWidth : 30 , list : [
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'strtDt', label: '프로모션기간', calendarPosition: 'bottom', readonly:true, width:100 }
					, {type: 'newcolumn'}
					, {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'endDt', label: '~', calendarPosition: 'bottom', readonly:true , labelWidth:10, width:100, offsetLeft:5 }
				]}
			]
			, onChange : function(name, value) {
				var form = this;
				
				switch(name){
				
				}
			}
		}	//FORM2 End
		
		, GRID2 : {
			css : {
				height : "300px"
				//,width : "290px"
			}
			,title : "대상요금제"
			,header : "선택,요금제코드,요금제명,기본료"
			,columnIds : "rowCheck,rateCd,rateNm,baseAmt"
			,colAlign : "center,center,left,right"
			,colTypes : "ch,ro,ro,ron"
			,colSorting : "str,str,str,int"
			,widths : "50,120,190,68"
			,paging: false
			//,multiCheckbox : true
			,hiddenColumns : [1]
			,buttons : {
				hright : {
					btnUpExcel : {label : "엑셀업로드"}				
				},	
				left : {
					btnDnSocExcel : { label : "요금제엑셀업로드양식"}
				}
			}, onButtonClick : function(btnId, selectedData) {
				var form = this;
				switch (btnId) {
					
					case "btnUpExcel":
												
						mvno.ui.createForm("POPUP1MID");
						
						PAGE.POPUP1MID.clear();
						PAGE.POPUP1MID.setFormData({});
						
						var uploader = PAGE.POPUP1MID.getUploader("file_upload1");
						uploader.revive();
												
						mvno.ui.popupWindowById("POPUP1", "트리플할인 요금제 선택", 405, 200, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
											
						break;
						
					case "btnDnSocExcel":
						mvno.cmn.download("/gift/prmtmgmt/getSocTempExcelDown.json");
					break;			
				}
			}
		}	//GRID2 End
		
		, GRID3 : {
			css : {
				height : "300px"
				//,width : "290px"
			}
			,title : "대상부가서비스"
			,header : "선택,부가서비스명,기본료,부가서비스,중복여부"
			,columnIds : "rowCheck,vasNm,baseAmt,vasCd,dupYn"
			,colAlign : "center,left,right,center,center"
			,colTypes : "ch,coro,ron,ro,ro"
			,colSorting : "str,str,str,str,str"
			,widths : "50,190,68,10,10"
			,paging: false
			,hiddenColumns : [3,4]
			,buttons : {
				right : {
					btnEndDt : { label : "종료일변경" }
					,btnDel : { label : "삭제" }
					,btnReg : { label : "저장" }
					
				}
			}
			,onButtonClick : function(btnId) {
				
				var grid = this;
				
				switch(btnId){
				
				
					case "btnEndDt" :
						
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("prmtId"))){
							mvno.ui.alert("대상 프로모션이 존재하지 않습니다.");
							return;
						}
						
						var strtDtFM = new Date(PAGE.FORM2.getItemValue("strtDt")).format("yyyymmdd");
						var endDtFM = new Date(PAGE.FORM2.getItemValue("endDt")).format("yyyymmdd");
						
						if(strtDtFM > endDtFM) {
							mvno.ui.alert("종료일이 시작일 전입니다. 종료일을 변경하세요.");
							return;
						}
						
						if(endDtFM < today) {
							mvno.ui.alert("종료일을 오늘 이전 날짜로 변경 할 수 없습니다.");
							return;
						}
						
						var sData = {
								prmtId : PAGE.FORM2.getItemValue("prmtId")
								,endDt : endDtFM
								,chngTypeCd : 'U'
						};
						
						mvno.cmn.ajax4confirm("해당 프로모션의 종료일을 변경 하시겠습니까?", ROOT + "/org/prmtmgmt/updTriplePrmtByInfo.json", sData, function(result) {
							PAGE.FORM2.clearChanged();
							mvno.ui.closeWindowById("GROUP1");
							mvno.ui.notify("NCMN0002");
							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
						});
						
						break;
						
						case "btnDel" :
							
							if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("prmtId"))){
								mvno.ui.alert("대상 프로모션이 존재하지 않습니다.");
								return;
							}
							var strtDtFM = new Date(PAGE.FORM2.getItemValue("strtDt")).format("yyyymmdd");
							var endDtFM = new Date(PAGE.FORM2.getItemValue("endDt")).format("yyyymmdd");
							
							if(strtDtFM < today && endDtFM > today ) {
								mvno.ui.alert("이미 진행중인 할인 프로모션 입니다.");
								return;
							}
							
							var sData = {
									prmtId : PAGE.FORM2.getItemValue("prmtId")
									,usgYn : 'N'
									,chngTypeCd : 'D'
							};
							
							mvno.cmn.ajax4confirm("해당 프로모션을 삭제 하시겠습니까?", ROOT + "/org/prmtmgmt/updTriplePrmtByInfo.json", sData, function(result) {
								PAGE.FORM2.clearChanged();
								mvno.ui.closeWindowById("GROUP1");
								mvno.ui.notify("NCMN0003");
								PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
							});
							
							break;
										
					case "btnReg" :
						
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("prmtNm"))){
							mvno.ui.alert("프로모션명을 입력하세요.");
							return;
						}
						
						var strtDtFM = new Date(PAGE.FORM2.getItemValue("strtDt")).format("yyyymmdd");
						var endDtFM = new Date(PAGE.FORM2.getItemValue("endDt")).format("yyyymmdd");
						
						if(strtDtFM > endDtFM) {
							mvno.ui.alert("종료일이 시작일 전입니다.\n종료일을 변경하세요.");
							return;
						}
						
						if(strtDtFM < today) {
							mvno.ui.alert("시작일은 오늘 이전 날짜를 선택할 수 없습니다.");
							return;
						}
											
						if(PAGE.GRID2.getCheckedRowData().length < 1){
							mvno.ui.alert("대상요금제를 선택하세요.");
							return;
						}
						
						if(PAGE.GRID3.getCheckedRowData().length < 1){
							mvno.ui.alert("대상부가서비스를 선택하세요.");
							return;
						}
						
						mvno.ui.confirm("프로모션을 저장하시겠습니까?", function() {
							
							// 요금제
							var arrRate = [];
							
							var arrRateData = PAGE.GRID2.getCheckedRowData();
							
							for(var idx=0; idx<arrRateData.length; idx++) {
								arrRate.push(arrRateData[idx]);
							}
							
							// 부가서비스
							var arrVas = [];
							
							var arrVasData = PAGE.GRID3.getCheckedRowData();
							
							for(var idx=0; idx<arrVasData.length; idx++) {
								arrVas.push(arrVasData[idx]);
							}
							
													
							var sData = {
									prmtNm : PAGE.FORM2.getItemValue("prmtNm")
									, strtDt : PAGE.FORM2.getItemValue("strtDt").format("yyyymmdd")
									, endDt : PAGE.FORM2.getItemValue("endDt").format("yyyymmdd")
							};			
							
							// 요금제
							sData.rateList = arrRate;
							// 부가서비스
							sData.vasList = arrVas;
							
							mvno.cmn.ajax4json(ROOT + "/org/prmtmgmt/regTriplePrmtInfo.json", sData, function(result) {
								PAGE.FORM2.clearChanged();
								mvno.ui.closeWindowById("GROUP1");
								mvno.ui.notify("NCMN0004");
								PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
							});
						});
						
						break;
				}
			}
		}	//GRID3 End
		,FORM3  : {
			 title : ""
			,buttons : {
				center : {
					btnClose : { label : "닫기" }
				}
			}
			,onButtonClick : function(btnId) {
				
				switch(btnId){
					
					case "btnClose" :
						mvno.ui.closeWindowById("GROUP1");
						break;
				}
			}
		},	//FORM3 End
		POPUP1MID : {
			title : "엑셀업로드",
			items : [
				{ type : "block",
				  list : [
					{ type : "newcolumn", offset : 10 },
					{ type : "upload", 
					  label : "대상요금제등록 엑셀파일",
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
					btnClose : { label : "닫기" }
				}
			},
			
			onButtonClick : function(btnId) {
				
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				
				switch (btnId) {
						
					case "btnClose":
						mvno.ui.closeWindowById("POPUP1");
					break;
				}
			},
			
			onUploadFile : function(realName, serverName) {
				fileName = serverName;
			},
			
			onUploadComplete : function(count) {
				var url = ROOT + "/org/prmtmgmt/getExcelUpSocList.json";
				var userOptions = {timeout:180000};
				
				mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
					var rData = resultData.result;
					mvno.ui.closeWindowById("POPUP1");
					PAGE.GRID2.clearAll();
					PAGE.GRID2.parse(rData.data.rows, "js");
					mvno.ui.alert(rData.msg);						
				}, userOptions);
			}
			
		}
	
	}
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		buttonAuth: ${buttonAuth.jsonString},
		onOpen:function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			PAGE.FORM1.setItemValue("searchBaseDate", today);

		}
	};
</script>