<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>
<div id="GRID2" class="section"></div>

<div id="GROUP1"  style="display:none;">
	<div id="FORM2" class="section-search"></div>
	<div id="GRID3" class="section"></div>
</div>

<div id="FORM3" class="section-box"></div>

<div id="POPUP1">
	<div id="POPUP1TOP"  class="section-full"></div>
	<div id="POPUP1MID" class="section-box"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:35, blockOffset:0}
				, {type:"block", list:[
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', label: '등록일자', name: 'searchFromDt', calendarPosition: 'bottom', readonly:true ,width:95,offsetLeft:10, labelWidth:55}
					, {type: 'newcolumn'}
					, {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchToDt', label: '~', calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:95}
					, {type:"newcolumn"}
					, {type: "input", name:"orgnId", label:"조직", value: "", width:100, offsetLeft:10}
					, {type: "newcolumn", offsetLeft:3}
					, {type: "button", value:"검색", name:"btnOrgFind"}
					, {type: "newcolumn"}
					, {type: "input", name: "usimNo", label: "유심일련번호", labelWidth: 80, value: "",maxLength:20, width:150, offsetLeft:30}
				]}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"} 
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnOrgFind":
						mvno.ui.codefinder("ORGSALESNEW", function(result) {
							form.setItemValue("orgnId", result.orgnId);
							form.setItemValue("orgnNm", result.orgnNm);
						});
						break;
						
					case "btnSearch":
						PAGE.GRID2.clearAll();
						PAGE.GRID1.list(ROOT + "/org/usimMgmt/getUsimMgmtList.json", form);
						
						break;
				}
			},
			onValidateMore : function (data){
				
				if (this.getItemValue("searchFromDt", true) > this.getItemValue("searchToDt", true)) {
					this.pushError("searchFromDt", "등록일자", "종료일자가 시작일보다 클수 없습니다.");
					this.resetValidateCss();
				}
			}
		}
	
		, GRID1 : {
			css : {
				//height : "530px"
				height : "380px"
			}
			, title : "조회결과"
			, header : "조직ID,조직명,유심일련번호,모델ID,모델코드,주문번호,배송일자,등록자,등록일자,수정자,수정일자,usimNo"
			, columnIds : "orgnId,orgnNm,usimNoMsk,usimPrdtId,usimPrdtCode,orderNum,deliveryDttm,regstId,regstDttm,rvisnId,rvisnDttm,usimNo"
			, widths : "90,140,155,80,150,120,90,90,140,90,140,0"
			, colAlign : "center,Left,center,center,left,center,center,center,center,center,center,center"
			, colTypes : "ro,ro,ro,ro,ro,ro,roXd,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str"
			, hiddenColumns:[11]
			//, multiCheckbox : true
			, paging : true
			, showPagingAreaOnInit : true
			, pageSize : 20
			, buttons : {
				hright : {
					btnExcel : { label : "개통현황"}
				},
				left : {
					btnUpExcel : { label : "엑셀업로드"},
					btnDnExcel : { label : "엑셀업로드양식"}
				},
				right : {
					btnReg : { label : "등록"},
					btnMod : { label : "수정"},
					//btnConfirm : { label : "확정"}
					btnDel : { label : "삭제"}
				}
			}
			, onRowSelect:function(rId, cId){
				
				PAGE.GRID2.clearAll();
								
				PAGE.GRID2.list(ROOT + "/org/usimMgmt/getUsimHistList.json", this.getSelectedRowData());
			}
			/* , onRowDblClicked : function(rowId, celInd) {
				PAGE.GRID1.callEvent("onButtonClick", ["btnMod"]);
			} */
			, onButtonClick : function(btnId) {
				var grid = this;
				
				switch (btnId) {
					case "btnExcel":
						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID3");
						PAGE.FORM2.clear();
						PAGE.GRID3.clearAll();
						
						var searchToDt = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var searchFromDt = new Date(time);

						PAGE.FORM2.setItemValue("searchFromDt", searchFromDt);
						PAGE.FORM2.setItemValue("searchToDt", searchToDt);
						
						mvno.ui.popupWindowById("GROUP1", "개통현황다운로드", 900, 542, {
							onClose: function(win) {
								win.closeForce();
							}
						}); 
						
						break;
						
					case "btnDnExcel":
						
						mvno.cmn.download("/org/usimMgmt/getUsimMgmtTempExcelDown.json");
						
						break;
						
					case "btnUpExcel":
						
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
							
							var url = ROOT + "/org/usimMgmt/regUsimUpList.json";
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
						
						mvno.ui.popupWindowById("POPUP1", "유심정보등록", 650, 500, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
						
						
						break;
					
					case "btnReg":
						mvno.ui.createForm("FORM3");
						
						PAGE.FORM3.clear();
						PAGE.FORM3.enableItem("usimNoMsk");
						mvno.ui.popupWindowById("FORM3", "유심정보등록", 460, 250, {
							onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						}); 
						
						break;
						
					case "btnMod":
						
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}

						mvno.ui.createForm("FORM3");
						
						PAGE.FORM3.clear();
						PAGE.FORM3.setItemValue("orgnId", grid.getSelectedRowData().orgnId);
						PAGE.FORM3.setItemValue("orgnNm", grid.getSelectedRowData().orgnNm);
						PAGE.FORM3.setItemValue("usimNo", grid.getSelectedRowData().usimNo);
						PAGE.FORM3.setItemValue("usimNoMsk", grid.getSelectedRowData().usimNoMsk);
						PAGE.FORM3.setItemValue("seq", grid.getSelectedRowData().usimNo);
						PAGE.FORM3.disableItem("usimNoMsk");
						
						PAGE.FORM3.clearChanged();
						
						mvno.ui.popupWindowById("FORM3", "유심정보등록", 460, 250, {
							onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						}); 
						
						break;
					
					case "btnDel":
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
						mvno.cmn.ajax4confirm("LGS00041", ROOT + "/org/usimMgmt/delUsimMgmt.json", grid.getSelectedRowData(), function(result) {
							mvno.ui.notify("LGS00042");
							PAGE.GRID1.refresh();
							/* 제휴유심관리에서 데이터 삭제를 했을때 변경이력이 남아있는 부분을 수정 */
							PAGE.GRID2.clearAll();
						});
						
						
						break;

				}
			}
		}
		, GRID2 : {
			css : {
				height : "130px"
			}
			, title : "변경이력"
			, header : "순번,조직ID,조직명,유심일련번호,변경유형,변경일시,등록자"
			, columnIds : "evntSeq,orgnId,orgnNm,usimNo,evntNm,evntDttm,evntRegstId"
			, widths : "50,140,180,185,90,180,110"
			, colAlign : "center,center,Left,center,center,center,center"
			, colTypes : "ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str"
			
		}
		
		, FORM2 : {
			title : "개통현황 엑셀다운로드"
			, items : [
				{type : "settings", position : "label-left", labelAlign : "left", labelWidth : 55, blockOffset : 0},
				{type : "block",
					list : [
						{type: "input", name:"orgnId", label:"조직", labelWidth : 35, value: "", width:120, offsetLeft:5},
						{type: "newcolumn", offsetLeft:3},
						{type: "button", value:"검색", name:"btnOrgFind"},
						{type: "newcolumn"},
						{type : "calendar",width : 100,label : "개통일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d", name : "searchFromDt", offsetLeft:50},
						{type : "newcolumn"},
						{type : "calendar",label : "~",name : "searchToDt",labelAlign : "center",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100}
					]
				}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}
				
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnOrgFind":
						mvno.ui.codefinder("ORGSALESNEW", function(result) {
							form.setItemValue("orgnId", result.orgnId);
							form.setItemValue("orgnNm", result.orgnNm);
						});
						break;
					case "btnSearch":
						PAGE.GRID3.list(ROOT + "/org/usimMgmt/getActiveUsimList.json", form);
							
						break;			
				}
			}
		}
		, GRID3 : {
			css : {
				height : "300px"
			}
			, title : "조회결과"
			, header : "개통일자,계약번호,최초요금제,상태,조직ID,조직명,대리점,신청일자,해지일자,해지사유,유심일련번호"
			, columnIds : "lstComActvDate,contractNum,fstRateNm,status,orgnId,orgnNm,agentNm,reqInDay,tmntDt,canRsn,usimNo"
			, widths : "80,90,180,60,90,120,120,120,120,120,142"
			, colAlign : "center,center,Left,center,center,center,center,center,center,Left,center"
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str"
			, paging: true
			, showPagingAreaOnInit: true
			, pageSize:20
			, buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드"}
				}
			}
			
			, onButtonClick : function(btnId) {
				var grid = this;
				
				switch (btnId) {
					case "btnExcel":
						var searchData =  PAGE.FORM2.getFormData(true);
						
						mvno.cmn.download(ROOT + "/org/usimMgmt/getActiveUsimListByExcel.json?menuId=MSP2003001", true, {postData:searchData});
												
						break;
					
				}
			}
		}
		
		, FORM3 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:100, blockOffset:0},
				{type:"block", offsetLeft:30, list:[
					{type:"input", name:"orgnId", label:"조직ID", width:165,  value:"", disabled:true, validate:"NotEmpty"},
					{type:"newcolumn"},
					{type:"button", name:"btnOrgFind", value:"찾기"}
				]},
				{type:"block", offsetLeft:30, list:[
					{type:"input", name:"orgnNm", label:"조직명", width:220, value:"", disabled:true, validate:"NotEmpty"}                                
				]},                                    
				{type:"block", offsetLeft:30, list:[
					{type:"input", name:"usimNoMsk", label:"유심일련번호", value: "", maxLength:20, width:220, validate:"NotEmpty"},
					{type:"hidden", name:"usimNo"}
   				]},
   				{type:"hidden", name:"seq", value:""}
			]
			, buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnOrgFind":
						//상위조직검색
						mvno.ui.codefinder("ORGSALESNEW", function(result) {
							form.setItemValue("orgnId", result.orgnId);
							form.setItemValue("orgnNm", result.orgnNm);
						});
						
						break;
						
					case "btnSave":
						mvno.ui.confirm("저장하시겠습니까?", function() {
							if (form.getItemValue("seq") == "") {
								form.setItemValue("usimNo", form.getItemValue("usimNoMsk"));
	                        	mvno.cmn.ajax(ROOT + "/org/usimMgmt/regUsimMgmt.json", form, function(){
									mvno.ui.notify("NCMN0004");
									PAGE.GRID1.refresh();
									PAGE.GRID2.clearAll();
									mvno.ui.closeWindowById("FORM3");
								});
							} else {
								if (! PAGE.FORM3.isChanged()) {
									mvno.ui.alert("CCMN0017");	
									return;
								}
								mvno.cmn.ajax(ROOT + "/org/usimMgmt/setUsimMgmt.json", form, function(){
									mvno.ui.notify("NCMN0004");
									PAGE.GRID1.refresh();
									PAGE.GRID2.clearAll();
									mvno.ui.closeWindowById("FORM3");
								});
							};
						});
						
						break;
						
					case "btnClose":
						mvno.ui.closeWindowById("FORM3");
						
						break;
				}
			},
			onValidateMore : function (data){
        // 등록인 경우 유심접점 필수값 제외
				if(data.seq && !data.orgnId){
					this.pushError("data.orgnId","조직ID",["ORGN0006"]);
				}
				else if(data.usimNoSt > data.usimNoEd){
					this.pushError("data.usimNoSt","유심일련번호","유심일련번호 시작값은 종료값보다 작거나 같아야 합니다.");
				}
			}
		}
				
		, POPUP1TOP : {
			css : { height : "250px" },
			title : "유심등록상세",
			header : "조직ID,유심일련번호,배송일자,주문번호,모델코드",
			columnIds : "orgnId,usimNo,deliveryDttm,orderNum,usimPrdtCode",
			widths : "90,170,90,120,120",
			colAlign : "center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro"
		}	//POPUP1TOP End
		
		, POPUP1MID : {
			title : "엑셀업로드",
			items : [
				{ type : "block",
				  list : [
					{ type : "newcolumn", offset : 10 },
					{ type : "upload", 
					  label : "유심등록요청 엑셀파일",
					  name : "file_upload1",
					  inputWidth : 420,
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
						
						mvno.cmn.ajax4json(ROOT + "/org/usimMgmt/regUsimMgmtList.json", sData, function(result) {
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
		
		
	}
			
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		 onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			mvno.ui.createGrid("GRID2");
			
			var searchToDt = new Date().format('yyyymmdd');
			var time = new Date().getTime();
			time = time - 1000 * 3600 * 24 * 30;
			var searchFromDt = new Date(time);
			
			PAGE.FORM1.setItemValue("searchFromDt", searchFromDt);
			PAGE.FORM1.setItemValue("searchToDt", searchToDt);
			
		}
	};
</script>
