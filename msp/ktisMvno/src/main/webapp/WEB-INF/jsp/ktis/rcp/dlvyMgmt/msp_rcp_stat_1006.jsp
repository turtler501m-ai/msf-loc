<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_rcp_stat_1006.jsp
	 * @Description : 택배발송현황
	 * @
	 * @ 수정일		수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2018.06.01 강무성 최초작성
	 * @author : 
	 * @Create Date : 
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search" ></div>
<!-- 그리드영역 -->
<div id="GRID1"></div>
<!-- 팝업영역 -->
<div id="POPUP1">
	<div id="POPUP1TOP"  class="section-full"></div>
	<div id="POPUP1MID" class="section-box"></div>
</div>
<div id="POPUP2">
	<div id="POPUP2TOP"  class="section-full"></div>
	<div id="POPUP2MID" class="section-box"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	var fileName;
	
	var DHX = {
		// 검색
		FORM1 : { 
			items : [ 
				{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 60, blockOffset : 0 }
				,{ type : "block", labelWidth : 30 , list : [
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'frmStartDate', label: '신청일자', value: "${startDate}", calendarPosition: 'bottom', readonly:true ,width:100 }
					, {type: 'newcolumn'}
					, {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'frmEndDate', label: '~', value: "${endDate}", calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type: 'newcolumn'}
					, {type:"select", width:100, label:"택배사", name:"frmTbCd", offsetLeft:146}
				]}
				,{ type : "block", labelWidth : 30 , list : [
					{type:"input", width:120, label:"송장번호", name: "frmDlvrNo"}
					, {type: 'newcolumn'}
					, {type:"input", width:120, label:"예약번호", name: "frmResNo", offsetLeft:30}
					, {type: 'newcolumn'}
					, {type:"input", width:120, label:"전화번호", name: "frmMobileNo", offsetLeft:30}
					, {type: 'newcolumn'}
					, {type:"input", width:120, label:"수취인", name: "frmDlvryName", offsetLeft:30}
				]}
				,{ type : "button",name : "btnSearch",value : "조회", className:"btn-search2" } 
			]
			,onButtonClick : function(btnId) {
				
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						
						var frmStartDateFM = new Date(form.getItemValue("frmStartDate")).format("yyyymmdd");
						
						if(frmStartDateFM > "${endDate}"){
							mvno.ui.alert("당일 이후 일자 조회 불가");
							return;
						}
						
						var frmEndDateFM = new Date(form.getItemValue("frmEndDate")).format("yyyymmdd");
						
						if(frmEndDateFM > "${endDate}"){
							mvno.ui.alert("당일 이후 일자 조회 불가");
							return;
						}
						
						var url1 = ROOT + "/rcp/dlvyMgmt/getParcelServiceLstInfo.json";
						
						PAGE.GRID1.list(url1, form);
						
					break;
				}
			}
		}	//FORM1 End
		
		, GRID1 : {
			css : { 
				height : "520px"  
			}
			,title : "택배발송현황"
			,header : "신청일자,택배사,송장번호,예약번호,수취인,전화번호,주소,상세주소,우편번호,메모,배송진행상태"
			,columnIds : "sysRdate,tbCd,dlvrNo,resNo,dlvryName,mobileNo,dlvryAddr,dlvryAddrDtl,dlvryPost,dlvryMemo,requestStateCode"
			,widths : "100,100,120,120,120,120,250,200,100,150,100"
			,colAlign : "center,center,center,center,center,center,left,left,center,left,center"
			,colTypes : "roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			,colSorting : "str,str,str,str,str,str,str,str,str,str,str"
			,hiddenColumns : "10"
			,paging : true
			,pageSize : 20
			,buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				}
				,left : {
					btnTempParNum : { label : "송장번호업로드양식" }
					,btnTempParCom : { label : "배송완료업로드양식" }
				}
				,right : {
					btnRegParNum : { label : "배송완료등록" }
					,btnRegParCom : { label : "송장번호등록" }
				}
			}
			,onButtonClick : function(btnId, selectedData) {
				switch (btnId) {
					case "btnDnExcel" :
						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}else{
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/rcp/dlvyMgmt/getParcelServiceLstInfoByExcel.json?menuId=LGS4002007", true, {postData:searchData}); 
						}
						
						break;
						
					case "btnTempParNum":
						mvno.cmn.download("/rcp/dlvyMgmt/getParNumTempExcelDown.json");
						break;
						
					case "btnTempParCom":
						mvno.cmn.download("/rcp/dlvyMgmt/getParComTempExcelDown.json");
						break;
						
					case "btnRegParNum":
						
						mvno.ui.createGrid("POPUP1TOP");
						mvno.ui.createForm("POPUP1MID");
						
						PAGE.POPUP1TOP.clearAll();
						PAGE.POPUP1MID.clear();
						
						PAGE.POPUP1MID.setFormData({});
						
						PAGE.POPUP1MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP1TOP.clearAll();
							
							return true;
						});
						
						var uploader = PAGE.POPUP1MID.getUploader("file_upload1");
						uploader.revive();
						
						mvno.ui.popupWindowById("POPUP1", "배송완료등록", 450, 490, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
						
						break;
						
					case "btnRegParCom":
						
						mvno.ui.createGrid("POPUP2TOP");
						mvno.ui.createForm("POPUP2MID");
						
						PAGE.POPUP2TOP.clearAll();
						PAGE.POPUP2MID.clear();
						
						PAGE.POPUP2MID.setFormData({});
						
						PAGE.POPUP2MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP2TOP.clearAll();
							
							return true;
						});
						
						var uploader = PAGE.POPUP2MID.getUploader("file_upload2");
						uploader.revive();
						
						mvno.ui.popupWindowById("POPUP2", "송장번호등록", 450, 490, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
						
						break;
					
				}
			}
		}	//GRID1 End
		
		, POPUP1TOP : {
			 css : { height : "250px" }
			,title : "배송완료등록상세"
			,header : "택배사,송장번호,배송진행상태"
			,columnIds : "tbCd,dlvrNo,requestStateCode" 
			,widths : "120,150,100"
			,colAlign : "center,center,center"
			,colTypes : "coro,ro,coro"
		}	//POPUP1TOP End
		
		, POPUP1MID : {
			 title : "엑셀업로드"
			,items : [
				 { type : "block"
				  ,list : [
					 { type : "newcolumn", offset : 20 }
					,{ type : "upload" 
					  ,label : "배송완료 등록요청 엑셀파일"
					  ,name : "file_upload1"
					  ,inputWidth : 330
					  ,url : "/rcp/dlvyMgmt/regParExcelUp.do"
					  ,userdata : { limitSize:10000 } 
					  ,autoStart : true } 
				]},
				{type:"input", width:100, name : "rowData", hidden:true}
			]
			
			,buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			}
			,onUploadFile : function(realName, serverName) {
				fileName = serverName;
			}
			,onUploadComplete : function(count){
				
				var url = ROOT + "/rcp/dlvyMgmt/regParComExcelUpList.json";
				var userOptions = {timeout:180000};
				
				mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
					var rData = resultData.result;
					
					PAGE.POPUP1TOP.clearAll();
					
					PAGE.POPUP1TOP.parse(rData.data.rows, "js");
					
				}, userOptions);
			}
			,onButtonClick : function(btnId) {
				
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
						
						mvno.cmn.ajax4json(ROOT + "/rcp/dlvyMgmt/regParcelCompleServiceInfo.json", sData, function(result) {
							mvno.ui.closeWindowById("POPUP1", true);
							mvno.ui.notify("NCMN0004");
						}, userOptions);
						
					break;
						
					case "btnClose":
						mvno.ui.closeWindowById("POPUP1");
					break;
				}
			}
		}	//POPUP1MID End
		
		, POPUP2TOP : {
			 css : { height : "250px" }
			,title : "송장번호등록상세"
			,header : "택배사,송장번호,예약번호"
			,columnIds : "tbCd,dlvrNo,resNo" 
			,widths : "120,150,100"
			,colAlign : "center,center,center"
			,colTypes : "ro,ro,ro"
		}	//POPUP2TOP End
		
		, POPUP2MID : {
			 title : "엑셀업로드"
			,items : [
				 { type : "block"
				  ,list : [
					 { type : "newcolumn", offset : 20 }
					,{ type : "upload" 
					  ,label : "송장번호 등록요청 엑셀파일"
					  ,name : "file_upload2"
					  ,inputWidth : 330
					  ,url : "/rcp/dlvyMgmt/regParExcelUp.do"
					  ,userdata : { limitSize:10000 } 
					  ,autoStart : true } 
				]},
				{type:"input", width:100, name : "rowData", hidden:true}
			]
			
			,buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			}
			,onUploadFile : function(realName, serverName) {
				fileName = serverName;
			}
			,onUploadComplete : function(count){
				
				var url = ROOT + "/rcp/dlvyMgmt/regParNumExcelUpList.json";
				var userOptions = {timeout:180000};
				
				mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
					var rData = resultData.result;
					
					PAGE.POPUP2TOP.clearAll();
					
					PAGE.POPUP2TOP.parse(rData.data.rows, "js");
					
				}, userOptions);
			}
			,onButtonClick : function(btnId) {
				
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				
				switch (btnId) {
					
					case "btnSave" :
						
						if(PAGE.POPUP2TOP.getRowsNum() == 0) {
							mvno.ui.alert("파일을 첨부해 주세요.");
							break;
						}
						
						var arrObj = [];
						
						PAGE.POPUP2TOP.forEachRow(function(id){
							var arrData = PAGE.POPUP2TOP.getRowData(id);
							
							arrObj.push(arrData);
						});
						
						var sData = {};
						
						sData.items = arrObj;
						
						var userOptions = {timeout:180000};
						
						mvno.cmn.ajax4json(ROOT + "/rcp/dlvyMgmt/regParcelServiceInfo.json", sData, function(result) {
							mvno.ui.closeWindowById("POPUP2", true);
							mvno.ui.notify("NCMN0004");
						}, userOptions);
						
					break;
						
					case "btnClose":
						mvno.ui.closeWindowById("POPUP2");
					break;
				}
			}
		}	//POPUP2MID End
	}
	
	var PAGE = {
		title: "${breadCrumb.title}",
		breadcrumb: "${breadCrumb.breadCrumb}",
		buttonAuth: ${buttonAuth.jsonString},
		onOpen:function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0025"}, PAGE.FORM1, "frmTbCd");
		}
	};
</script>