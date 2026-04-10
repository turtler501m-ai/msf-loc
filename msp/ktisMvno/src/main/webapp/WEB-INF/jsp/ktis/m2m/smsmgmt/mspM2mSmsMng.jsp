<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name  : msp_cmn_sms_1002.jsp
	 * @Description : SMS 발송조회
	 * @
	 * @  수정일	  	수정자			  수정내용
	 * @ ---------   ---------   -------------------------------
	 * @ 2017.01.17  	TREXSHIN		  최초생성
	 * @ 
	 * @author : TREXSHIN
	 * @Create Date : 2017.01.17
	 */
%>
	
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<div id="GROUP1" style="display:none;">
	<div id="FORM2" class="section-box"></div>
	<div id="FORM3" class="section-pagescope"></div>
</div>

<script type="text/javascript">
	
var DHX = {
		FORM1 : {
			items : [
				 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				
				,{type:"block", list:[
					 {type:"calendar", name:"searchStartDate", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", validate: "NotEmpty", label:"발송일자", offsetLeft:10, width:100, labelWidth:70, required:true}
					,{type:"newcolumn"}
					,{type:"calendar", name:"searchEndDate", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", validate: "NotEmpty", label:"~", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100, required:true}
					/*,{type:"newcolumn"}
					,{type:"select", name:"searchWorkType", label:"업무구분", width:80, offsetLeft:10, labelWidth:50}
					,{type:"newcolumn"}
					,{type:"select", name:"searchCustType", label:"고객구분", width:100, offsetLeft:10, labelWidth:50}
					*/
					,{type:"newcolumn"}
					,{type:"select", name:"searchTemplateNm", label:"템플릿명", width:160, offsetLeft:10, labelWidth:50}
				]}
				,{type:"block", list:[
					 {type:"select", name:"searchCode", label:"검색구분", width:110, offsetLeft:10, labelWidth:70}
					,{type:"newcolumn"}
					,{type:"input", name:"searchValue", label:"", labelWidth:0, offsetLeft:0, width:105, maxLength:30}
					,{type:"newcolumn"}
					,{type:"select", name:"searchSendYn", label:"전송여부", width:80, offsetLeft:10, labelWidth:50}
					/* ,{type:"newcolumn"}
					,{type:"select", name:"searchResult", label:"발송결과", width:324, offsetLeft:10, labelWidth:50} */
				]}
				,{type:"newcolumn", offset:130}
				,{type:"button", name:"btnSearch", value:"조회", className:"btn-search2"}
			],
			
			onValidateMore : function(data) {
				if (data.searchStartDate > data.searchEndDate) {
					this.pushError("searchEndDt", "조회기간", "종료일자가 시작일자보다 클 수 없습니다.");
					//this.resetValidateCss();
				}
				
				if(data.searchCode != "" && data.searchValue == ""){
					this.pushError("searchValue", "검색어", "검색어를 입력하세요.");
				}
			},
			
			onChange : function(name, value) {
				
				var form = this;
				
				if(name == "searchCode") {
					form.setItemValue("searchValue","");
				}
			},
			
			onButtonClick : function(btnId) {
				
				var form = this;
				
				switch(btnId) {
					
					case "btnSearch" :
						PAGE.GRID1.list(ROOT + "/m2m/smsmgmt/getSmsSendList.json", form);
						break;
				}
			}
			
		},
		
		GRID1 : {
			 title      : "SMS발송목록"
			,css        : {height : "530px"}
			,header     : "발송요청일시,업무구분,수신자,수신자번호,고객구분,계약번호,홈페이지ID,예약번호,본사사용자ID,템플릿명,전송여부,발송결과,발송횟수,발송일시,처리자,업무구분코드,고객구분코드,발송결과코드,처리자ID,발송내용,SMS일련번호,발송일련번호,템플릿ID,전송여부" // 15 + 9
			,columnIds  : "sendReqDttm,workTypeNm,receiveNm,maskMobileNo,sendDivisionNm,contractNum,portalUserId,resNo,mspUserId,templateNm,sendYnNm,resultNm,sendCnt,sendTime,procNm,workType,sendDivision,result,procId,text,mseq,sendSeq,templateId,sendYn"
			,widths     : "130,80,80,100,70,90,90,90,100,150,70,130,70,130,80,0,0,0,0,0,0,0,0,0"
			,colAlign   : "center,center,left,center,center,center,left,center,left,left,center,left,right,center,left,left,left,left,left,left,left,left,left,left"
			,colTypes   : "roXdt,ro,ro,roXp,ro,ro,ro,ro,ro,ro,ro,ro,ron,roXdt,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			,hiddenColumns: [4,5,6,7,15,16,17,18,19,20,21,22,23]
			,paging     : true
			,showPagingAreaOnInit: true
			,pageSize: 20
			,multiCheckbox : true
			,buttons : {
				right : {
					 btnDelete : { label : "삭제" }
					,btnRetry : { label : "재발송" }
				}
			}
			, onRowDblClicked : function(rowId, celInd) {
				this.callEvent("onButtonClick", ["btnDetail"]); // ROW 더블 클릭시 상세보기
			}
			, onButtonClick : function(btnId, selectedData) {
				
				switch(btnId) {
					
					case "btnDelete":
						var rowIds = this.getCheckedRows(0);
						
						if(!rowIds) {
							mvno.ui.alert("ECMN0003");
							return;
						} else {
							var arrData = this.getCheckedRowData();
							for(var j=0; j<arrData.length; j++) {
								if(arrData[j].sendYn == 'Y') {
									mvno.ui.alert("전송여부가 대기,삭제가 아닌 건이 존재합니다.");
									return;
								}
							}
							
							var confirmMsg = "[ " + this.getCheckedRowData().length + " ] 건을 삭제 하시겠습니까?";
							var sdata = this.classifyRowsFromIds(rowIds);
							
							mvno.ui.confirm(confirmMsg, function() {
								
								mvno.cmn.ajax4json(ROOT + "/m2m/smsmgmt/setSmsDelete.json", sdata, function() {
									mvno.ui.notify("삭제 처리를 완료하였습니다.");
									PAGE.GRID1.refresh();
								});
							});
							
						}
						break;
						
					case "btnRetry":
						var rowIds = this.getCheckedRows(0);
						
						if(!rowIds) {
							mvno.ui.alert("ECMN0003");
							return;
						} else {
							var now = new Date();
							var hour = Number(now.getHours());
							var min = now.getMinutes();
							var sec = now.getSeconds();
							if(min < 10) {
								min = "0" + min;
							}
							if(sec < 10) {
								sec = "0" + sec;
							}
							var time = Number(now.getHours() + min + sec);
							if(hour < 8 || time > 210000) {
								mvno.ui.alert("문자발송은 오전 8시에서 오후 9시까지 가능합니다.");
								return;
							}
							
							var confirmMsg = "[ " + this.getCheckedRowData().length + " ] 건을 재발송 하시겠습니까?";
							var sdata = this.classifyRowsFromIds(rowIds);
							
							mvno.ui.confirm(confirmMsg, function() {
								
								mvno.cmn.ajax4json(ROOT + "/m2m/smsmgmt/setSmsRetry.json", sdata, function() {
									mvno.ui.notify("재발송 처리를 완료하였습니다.");
									PAGE.GRID1.refresh();
								});
							});
						}
						
						break;
						
					case "btnDetail":
						mvno.ui.createForm("FORM2");
						mvno.ui.createForm("FORM3");
						
						PAGE.FORM2.clear();
						
						var rowData = PAGE.GRID1.getRowData(PAGE.GRID1.getSelectedRowId());
						rowData.text = fn_replaceTextarea(rowData.text);
						PAGE.FORM2.setFormData(rowData);
						
						PAGE.FORM2.setItemValue("maskMobileNo", mvno.etc.setTelNumHyphen(rowData.maskMobileNo));
						
						PAGE.FORM2.setItemValue("sendTime", PAGE.GRID1.cells(PAGE.GRID1.getSelectedRowId(), PAGE.GRID1.getColIndexById("sendTime")).getTitle());
						
						mvno.ui.popupWindowById("GROUP1", "SMS발송상세", 460, 460, {
							onClose: function(win) {
								win.closeForce();
							}
						});
						
						break;
				}
			}
		},
		
		FORM2 : {
			title : "SMS발송상세",
			items : [
				 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, blockOffset:0}
				
				,{type:"hidden", name:"saveType"}
				
				,{type:"block", list:[
					 {type:"input" ,name:"receiveNm", label:"수신자", labelWidth:60, width:90, offsetLeft:10, readonly:true}
					,{type:"newcolumn"}
					,{type:"input", name:"maskMobileNo", label:"수신자번호", labelWidth:70, width:120, offsetLeft:20, readonly:true}
				]}
				
				,{type:"block", list:[
					 {type:"input", name:"sendYnNm", label:"전송여부", labelWidth:60, width:90, offsetLeft:10, readonly:true}
					,{type:"newcolumn"}
					,{type:"input", name:"sendTime", label:"발송일시", labelWidth:70, width:120, offsetLeft:20, readonly:true}
				]}
				
				,{type:"block", list:[
					 {type:"input", name:"resultNm", label:"발송결과", labelWidth:60, width:304, offsetLeft:10, readonly:true}
				]}
				
				,{type:"block", list:[
					 {type:"input", name:"templateNm", label:"발송항목", labelWidth:60, width:304, offsetLeft:10, readonly:true}
				]}
				
				,{type:"block", list:[
					 {type:"input", name:"text", label:"발송내용", rows:"12", labelWidth:60, width:304, offsetLeft:10, readonly:true}
				]}
				
			]
			
		},
		
		FORM3 : {
			buttons : {
				center : {
					 btnDelete : { label : "삭제" }
					,btnRetry : {label : "재발송"}
					,btnClose: {label : "닫기"}
				}
			},
			onButtonClick : function(btnId) {
				
				switch(btnId) {
					
					case "btnDelete":
						
						if(PAGE.GRID1.cells(PAGE.GRID1.getSelectedRowId(), PAGE.GRID1.getColIndexById("sendYn")).getValue() == "Y") {
							mvno.ui.alert("전송여부가 대기,삭제가 아닙니다.");
							return;
						}
						
						var sdata = PAGE.GRID1.classifyRowsFromIds(PAGE.GRID1.getSelectedRowId());
						
						mvno.ui.confirm("삭제 하시겠습니까?", function() {
							
							mvno.cmn.ajax4json(ROOT + "/m2m/smsmgmt/setSmsDelete.json", sdata, function() {
								mvno.ui.closeWindowById("GROUP1", true);
								mvno.ui.notify("삭제 처리를 완료하였습니다.");
								PAGE.GRID1.refresh();
							});
						});
						
						break;
						
					case "btnRetry" :
						
						var now = new Date();
						var hour = Number(now.getHours());
						var min = now.getMinutes();
						var sec = now.getSeconds();
						if(min < 10) {
							min = "0" + min;
						}
						if(sec < 10) {
							sec = "0" + sec;
						}
						var time = Number(now.getHours() + min + sec);
						if(hour < 8 || time > 210000) {
							mvno.ui.alert("문자발송은 오전 8시에서 오후 9시까지 가능합니다.");
							return;
						}
						
						var sdata = PAGE.GRID1.classifyRowsFromIds(PAGE.GRID1.getSelectedRowId());
						
						mvno.ui.confirm("재발송 하시겠습니까?", function() {
							
							mvno.cmn.ajax4json(ROOT + "/m2m/smsmgmt/setSmsRetry.json", sdata, function() {
								mvno.ui.closeWindowById("GROUP1", true);
								mvno.ui.notify("재발송 처리를 완료하였습니다.");
								PAGE.GRID1.refresh();
							});
						});
						
						break;
						
					case "btnClose" :
						
						mvno.ui.closeWindowById("GROUP1");
						break;
				}
			}
		}
		
};
	
var PAGE = {
     title : "${breadCrumb.title}",
     breadcrumb : "${breadCrumb.breadCrumb}",  
	 onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		
		// 조회조건 처리상태 combo 셋팅
		//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0068"}, PAGE.FORM1, "searchResult");
		//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0069",orderBy:"etc6"}, PAGE.FORM1, "searchWorkType"); // 업무구분
		//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0071",orderBy:"etc6"}, PAGE.FORM1, "searchCustType"); // 고객구분
		mvno.cmn.ajax4lov( ROOT + "/m2m/smsmgmt/getSearchCodeCombo.json", {grpId:"RCP2032",orderBy:"etc6"}, PAGE.FORM1, "searchCode"); // 검색구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2033",orderBy:"etc6"}, PAGE.FORM1, "searchSendYn"); // 전송여부
		mvno.cmn.ajax4lov( ROOT + "/m2m/smsmgmt/getTemplateCombo.json", {etc2 : "Y"}, PAGE.FORM1, "searchTemplateNm"); // 템플릿
		
		var endDate = new Date().format('yyyymmdd');
		var time = new Date().getTime();
		time = time - 1000 * 3600 * 24 * 6;
		var startDate = new Date(time);
		
		PAGE.FORM1.setItemValue("searchStartDate", startDate);
		PAGE.FORM1.setItemValue("searchEndDate", endDate);
		PAGE.FORM1.setItemValue("searchTemplateNm","206");
		
	}
	
};

function fn_replaceTextarea(s) { //textarea 에서 &가 &amp;로 자동 변경되어 수정함.
	var s = new String(s);
	s = s.replace(/&amp;/ig, "&");
	s = s.replace(/&lt;/ig, "<");
	s = s.replace(/&gt;/ig, ">");
	s = s.replace(/&quot;/ig, "\"");
	s = s.replace(/&#39;/ig, "'");
	s = s.replace(/<br>/ig, "\r\n");
	return s;
}

</script>