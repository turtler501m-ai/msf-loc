<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name  : msp_cmn_sms_1005.jsp
 * @Description : 대량문자발송
 */
%>
<!-- 메인화면 -->	
<div id="FORM1" class="section-search"></div>
<!-- 조회결과 -->
<div id="GRID1"></div>
<!-- 상세화면-->
<div id="GROUP1" >
	<div id="FORM2" class="section-box" ></div>
	<div id="GRID2"></div>
</div>
<!-- 엑셀업로드 양식 -->
<div id="FORM4" class="section-box"></div>
<!-- 템플릿 엑셀업로드 -->
<div id="FORM5" class="section-box"></div>	 
<!-- MMS 엑셀업로드 -->
<div id="FORM6" class="section-box"></div>

<script type="text/javascript">
var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				,{type:"block", list:[
					{type : "calendar",width : 100,label : "전송일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",  value:"${srchStrtDt}",name : "srchStrtDt", offsetLeft:10, readonly:true}
					, {type : "newcolumn"}
					, {type : "calendar",label : "~",name : "srchEndDt",labelAlign : "center",dateFormat : "%Y-%m-%d", value:"${srchEndDt}",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true}
					, {type : "newcolumn"}
					, {type : "select",width : 100 , label : "검색구분",name : "searchGbn" , 
						offsetLeft:10, options:[{value:"", text:"- 전체 -"}, {value:"1", text:"제목"}, {value:"2", text:"등록자"}]}
					, {type : "newcolumn"}
					, {type : "input",width : 100,name : "searchName", disabled:true}
					, {type : "newcolumn"}
					, {type : "select",width : 100, offsetLeft:10, label : "완료여부", name : "resultYn",
						offsetLeft:10, options:[{value:"", text:"- 전체 -"}, {value:"Y", text:"완료"}, {value:"N", text:"대기"}, {value:"E", text:"오류"}]}
				]}		
				,{type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}
			],
			onValidateMore : function(data) {

				if (data.srchStrtDt > data.srchEndDt) {
					this.pushError("srchStrtDt", "전송일자", "시작일자가 종료일자보다 클 수 없습니다.");
				}
				if(data.srchStrtDt != "" && data.srchEndDt == ""){
					this.pushError("srchStrtDt", "전송일자", "조회기간을 입력하세요.");
				}
				if(data.srchEndDt != "" && data.srchStrtDt == ""){
					this.pushError("srchEndDt", "전송일자", "조회기간을 입력하세요.");
				}
				if(!mvno.cmn.isEmpty(data.searchGbn) && mvno.cmn.isEmpty(data.searchName)) {
					this.pushError(["searchName"],"검색구분","검색어를 입력하십시오.");
				}	
			},
			onChange : function(name, value) {
				
				var form = this;
				
				// 검색구분 
				if(name == "searchGbn") {
					PAGE.FORM1.setItemValue("searchName", "");
					
					if(value == null || value == "") {

						var endDate = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var startDate = new Date(time);
						
						PAGE.FORM1.enableItem("srchStrtDt");
						PAGE.FORM1.enableItem("srchEndDt");
						
						PAGE.FORM1.setItemValue("srchStrtDt", startDate);
						PAGE.FORM1.setItemValue("srchEndDt", endDate);
						
						PAGE.FORM1.disableItem("searchName");
					}
					else {
						PAGE.FORM1.setItemValue("srchStrtDt", "");
						PAGE.FORM1.setItemValue("srchEndDt", "");
						
						// 개통일자 Disable
						PAGE.FORM1.disableItem("srchStrtDt");
						PAGE.FORM1.disableItem("srchEndDt");
						
						PAGE.FORM1.enableItem("searchName");
					}
				}			
			},
			onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId) {
					case "btnSearch" :
						PAGE.GRID1.list(ROOT + "/cmn/smsmgmt/getMsgSmsSendList.json", form);
						break;			
				}
			}
		}
		// GRID1
		,GRID1 : {
			css : {
				height : "560px"
			}
			, title : "조회결과"
			, header : "번호,전송일자,제목,건수,발송타입,완료여부,결과코드,카카오여부,등록자,등록일자" // 9 
			, columnIds : "msgNo,requestTime,subject,sendCnt,msgSmsTypeNm,resultYnNm,resultCode,kakaoYn,regstId,regstTime" // 9 
			, widths : "50,130,246,50,60,60,60,70,80,130" // 9
			, colAlign : "center,center,left,right,center,center,center,center,center,center" // 9
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" // 9
			, colSorting : "str,str,str,str,str,str,str,str,str,str" // 9
			//, hiddenColumns:[9]
			, paging : true
			, pageSize:20
			, showPagingAreaOnInit: true
			, buttons : {
				left : {
					btnDnExcelUp : { label : "엑셀업로드양식"}
				},
				right  : {
					btnTmpExcel : { label : "템플릿 엑셀업로드" },
					btnExcelUpload : { label : "MMS 엑셀업로드" },
					btnDel   : { label : "삭제" },	
				}	
			}
			,onRowDblClicked : function(rowId, celInd) {
				this.callEvent("onButtonClick", ["btnDtl"]);
			}	
			,onButtonClick : function (btnId) {
				var grid = this;
				
				switch (btnId) {
				
					//상세등록정보
					case "btnDtl":
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						 
						mvno.ui.createForm("FORM2");
						mvno.ui.createGrid("GRID2");
					
						PAGE.GRID2.clearAll();
						
						PAGE.FORM2.disableItem("msgNo");
						PAGE.FORM2.disableItem("subject");
						PAGE.FORM2.disableItem("sendCnt");
						PAGE.FORM2.disableItem("msgTmpId");
						var selectData = grid.getSelectedRowData();
						
						PAGE.FORM2.setFormData(grid.getSelectedRowData(), true);
				
						PAGE.GRID2.list(ROOT + "/cmn/smsmgmt/getMsgSmsSendListDt.json", selectData);	
						
						mvno.ui.popupWindowById("GROUP1", "상세화면", 770, 580, {
							onClose: function(win) {		
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});					
							}
						});
						break;
					  
					//엑셀업로드 양식
					case "btnDnExcelUp":
						
						mvno.ui.createForm("FORM4");
						PAGE.FORM4.clear();
						
						mvno.ui.popupWindowById("FORM4", "엑셀업로드양식", 400, 250, {
							onClose: function(win) {
								if (! PAGE.FORM4.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
						break;
						
					//템플릿 엑셀업로드
					case "btnTmpExcel":
						
						mvno.ui.createForm("FORM5");
						PAGE.FORM5.clear();
						
						var today = new Date().format("yyyymmdd");
 						var fileName;
 						
 						PAGE.FORM5.setItemValue("reqYmd", today);
						
 						PAGE.FORM5.attachEvent("onUploadFile", function(realName, serverName) {
 							PAGE.FORM5.setItemValue("fileName", serverName);
							
 						});
						
 						PAGE.FORM5.attachEvent("onFileRemove",function(realName, serverName) {		
 							PAGE.FORM5.setItemValue("fileName", "");
 							return true;
 						});
					
						var uploader = PAGE.FORM5.getUploader("file_upload1");
						uploader.revive();
						uploader.reset();
						
						mvno.ui.popupWindowById("FORM5", "템플릿 엑셀업로드", 540, 360, {
							onClose: function(win) {								
								if (! PAGE.FORM5.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){		
									win.closeForce();
								});
							}
						}); 
						
						break;
						
						//MMS 엑셀업로드
					case "btnExcelUpload":
						
						mvno.ui.createForm("FORM6");
						PAGE.FORM6.clear();
											
						var today = new Date().format("yyyymmdd");
						var fileName;
						
						PAGE.FORM6.setItemValue("reqYmd", today);
						  
						PAGE.FORM6.attachEvent("onUploadFile", function(realName, serverName) {
							PAGE.FORM6.setItemValue("fileName", serverName);
						});
						
						PAGE.FORM6.attachEvent("onFileRemove",function(realName, serverName) {
							PAGE.FORM6.setItemValue("fileName", "");
							return true;
						});
						
						var uploader = PAGE.FORM6.getUploader("file_upload1");
						uploader.revive();
						uploader.reset();
						
						mvno.ui.popupWindowById("FORM6", "MMS 엑셀업로드", 540, 340, {
							onClose: function(win) {
								if (! PAGE.FORM6.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
								
									win.closeForce();
								});
							}
						}); 
						
						break;
						
						//삭제
					case "btnDel":
						
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						} else if(grid.getSelectedRowData().resultYn != "N"){
							mvno.ui.alert("전송대기 상태인 경우에만 삭제 가능 합니다");
							return;
						}
						
						mvno.cmn.ajax4confirm("LGS00041", ROOT + "/cmn/smsmgmt/deleteMsgSmsList.json", grid.getSelectedRowData(), function(result) {
							mvno.ui.notify("LGS00042");
							PAGE.GRID1.refresh();
							PAGE.GRID2.clearAll();
						});
						
						break;
				}
		   }
		},
		// GROUP1 > FORM2 상세화면
		FORM2 : {		
			title : "등록정보",
			items : [
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:80, blockOffset:0},
					// 고객정보
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"seq", name:"msgNo", width:80,labelWidth : 30,  disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"제목", name:"subject", width:270, offsetLeft:10,labelWidth : 30,  disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"전송요청건수", name:"sendCnt", width:50, offsetLeft:10, readonly:true, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"템플릿ID", name:"msgTmpId", width:50, offsetLeft:10,labelWidth : 50, readonly:true, disabled:true}
						]}
			],
		},
		// GROUP1 > GRID2 상세 리스트
		GRID2 : {
			css : {
				height : "300px"
			    ,width  : "710px"
			},
			title : "상세 조회결과",
			header : "발신번호,수신번호,제목,전송내용,번호",
			columnIds : "msgSendCtn,msgRecvCtn,msgTitle,msgText,msgNo",
			widths : "70,90,211,322,0",
			colAlign : "center,center,left,left,center",
			colTypes : "ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str",
			hiddenColumns:[4] ,
			paging:true,
			buttons : {
				center : {		
					btnClose : { label: "닫기" }
				}
			},
			onButtonClick : function(btnId, selectedData) {
				switch (btnId) {

					case "btnClose" :
						mvno.ui.closeWindowById("GRID2");
						break;
				}
			}
		}
		//엑셀업로드 양식
		,FORM4 : {
			title : "<font size='2' color='red'>(시간당 최대 2만건 등록 가능합니다.)</font>",
			items : [
				{type:"block", list:[
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:180, labelHeight:30, blockOffset:10},
					{type:"block", list:[
						{type:"label", label:"템플릿 엑셀업로드 양식", offsetLeft:30},
						{type:"newcolumn"},
						{type:"button", value:"다운로드", name :"btnSmsTmpEx"}
					]},
					{type:"block", list:[
						{type:"label", label:"MMS 엑셀업로드 양식", offsetLeft:30},
						{type:"newcolumn"},
						{type:"button", value:"다운로드", name :"btnSmsSendEx"}
					]}
				]},
			],
			buttons : {
				center : {
					btnClose : { label: "닫기" }
				}
			},
			onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
					
					case "btnSmsTmpEx":						
						mvno.cmn.download("/cmn/smsmgmt/getSmsTmpExcelDown.json");					
						break;
					
					case "btnSmsSendEx":						
						mvno.cmn.download("/cmn/smsmgmt/getSmsSendExcelDown.json");				
						break;					
						
					case "btnClose" :
						mvno.ui.closeWindowById(PAGE.FORM4, true);
						break;
				}
			}
		}
		//템플릿 엑셀업로드 화면
		, FORM5 : {
			title : "<font size='2' color='red'>(엑셀 업로드시 시간이 다소 소요될 수 있습니다.)</font>",
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0,  inputWidth: 'auto'},
				{type: "fieldset", label: "등록정보", list:[
					{type:"settings"},
					{type:"block", offsetLeft:20, list:[
						  {type:"input", name:"subject", label:"제목", width:337, value:""}                                
					]},  
					{type: "block", blockOffset: 0,offsetLeft: 20, list: [
					  {type : "calendar",width : 170,name: "reqYmd",label : "발송일자", value:'',dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",calendarPosition:"bottom"}	
					, {type : "newcolumn"}
	   				, {type:"select", name:"reqHour", label:"발송시간", width:72, value:"", maxLength:2, offsetLeft: 40,validate:"ValidInteger",
	   					options:[{value:"09", text:"9시"}, {value:"10", text:"10시"}, {value:"11", text:"11시"}
	   						, {value:"12", text:"12시"}, {value:"13", text:"13시"},  {value:"14", text:"14시"}
	   						, {value:"15", text:"15시"}, {value:"16", text:"16시"}
	   						, {value:"17", text:"17시"}
	   						]}    
					]},
					{type:"block", offsetLeft:20, list:[
						  {type:"input", name:"msgTmpId", label:"템플릿ID", width:120, value:"", maxLength:10}    
						, {type : "newcolumn"}
						//, {type: 'checkbox', name: 'kakaoYn', label: '카카오발송여부',  width:92, offsetLeft: 110, labelWidth:90,value:'Y'},    
						, {type:"select", name:"kakaoYn", label:"카카오여부", width:72, maxLength:2, offsetLeft: 70,labelWidth:70,
	   					options:[{value:"N", text:"N"}, {value:"Y", text:"Y"}
	   					]}
					]},	
				]},
				{type: "fieldset", label: "엑셀업로드", list:[
					 {type: "block", offsetLeft: 20, list: [
						 { type: "upload" , label: "" , name: "file_upload1" ,width:390, inputWidth: 320, url: "/rcp/dlvyMgmt/regParExcelUp.do" , userdata: { limitSize:30000 } , autoStart: true } 
					]},
					 {type:"block", offsetLeft:20, list:[
						 {type:"hidden", name: "fileName"}
						,{type:"hidden",  label:"발송일자시간",name : "requestTime", value:""}	
					]},
				]},
			]	
			, buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			}
			, onButtonClick : function(btnId) {
				
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				var requestTime = "";
				
				/* 오늘 날짜 가져오기 */
				var date = new Date().format('yyyymmdd');
				var now = new Date();
				var hour = "0"+Number(now.getHours());
				var min = now.getMinutes();
				var sec = now.getSeconds();
					if(min < 10) {
						min = "0" + min;
					}
					if(sec < 10) {
						sec = "0" + sec;
					}
				var time = Number(hour + min + sec);
				
				
				switch (btnId) {
				
					case "btnSave" :
						
						if(mvno.cmn.isEmpty(PAGE.FORM5.getItemValue("subject"))){
							mvno.ui.alert("제목을 입력하세요.");
							return;
						}
					
						if(mvno.cmn.isEmpty(PAGE.FORM5.getItemValue("reqYmd"))){
							mvno.ui.alert("발송일자를 선택하세요.");
							return;
						}		
						
						if(mvno.cmn.isEmpty(PAGE.FORM5.getItemValue("reqHour"))){
							mvno.ui.alert("발송시간을 선택하세요.");
							return;
						}
						 
						if(mvno.cmn.isEmpty(PAGE.FORM5.getItemValue("msgTmpId"))){
							mvno.ui.alert("템플릿을 선택하세요.");
							return;
						}		
						
						var reqYmdd = new Date(PAGE.FORM5.getItemValue("reqYmd")).format("yyyymmdd");			
						var requestTime = reqYmdd + PAGE.FORM5.getItemValue("reqHour") + "0000";
						PAGE.FORM5.setItemValue("requestTime", requestTime);
						var nowDate = date +  time;
						
						// 현재시간 date형식으로 replace				
						var nowDt = new Date(nowDate.replace(/^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/, '$1-$2-$3 $4:$5:$6'));
						// 현재시간 date형식으로 replace
						var repDt =  new Date(requestTime.replace(/^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/, '$1-$2-$3 $4:$5:$6'));
						
						
						// 현재 날짜는 2022년07월22일 9시인데 등록하고싶은 날짜가 2022년07월21일 9시이면 등록이 안되게끔 함
					
						if(nowDt > repDt ) {
							mvno.ui.alert("이미 지난 발송일자입니다. 다른시간대를 선택해주세요");
							return;
						}
						
						// requestTime date형식으로 replace해서 만약 선택한시간이 9시면 8시30분이 나올수있게함			
						var dt = repDt;
						dt = dt - 1000 * 1800;
						var dddt = new Date(dt);
				
						// 예약전송시간 최소 30분전에 등록할수 있게함 선택한 시간대는 9시인데 현재시간이 8시31분이면 예약이 안되게끔 함
						if(nowDt > dddt) {
							mvno.ui.alert("전송시간 최소 30분전에 등록해주세요.");	
							return;
						}
						
						if(mvno.cmn.isEmpty(form.getItemValue("fileName"))){
 							mvno.ui.alert("첨부된 파일이 없습니다");
 							return;
 						}
						var userOptions = {timeout:180000};
					
	 					if(PAGE.FORM5.getItemValue("requestTime") != "") {				
							 	mvno.cmn.ajax(ROOT + "/cmn/smsmgmt/insertMsgSmsTmp.json", form, function(resultData) {					
									if(resultData.result.code == "NOK") {
										mvno.ui.alert(resultData.result.msg);
					                    return;
									}			
									mvno.ui.notify("NCMN0004"); 			
									PAGE.FORM5.clear();
	 	 							mvno.ui.closeWindowById("FORM5",true);
	 	 							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);	
	 	 							}, userOptions);
							 	
						}								
					break;
						
					case "btnClose":
						
						mvno.ui.confirm("나가시겠습니까?", function() {
						mvno.ui.closeWindowById("FORM5");
						PAGE.FORM5.clear();
						});
						
					break;
				}
			}
		
		}
		//MMS엑셀업로드 등록
		, FORM6 : {
			title : "<font size='2' color='red'>(엑셀 업로드시 시간이 다소 소요될 수 있습니다. )</font>",
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0,  inputWidth: 'auto'},
				{type: "fieldset", label: "등록정보", list:[
					{type:"settings"},
					{type:"block", offsetLeft:20, list:[
						  {type:"input", name:"subject", label:"제목", width:337, value:""}                                
					]},  
					{type: "block", blockOffset: 0,offsetLeft: 20, list: [
					  {type : "calendar",width : 170,name: "reqYmd",label : "발송일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d"}	
					, {type : "newcolumn"}
	   				, {type:"select", name:"reqHour", label:"발송시간", width:70, value:"", maxLength:2, offsetLeft: 40,validate:"ValidInteger",
	   					options:[{value:"09", text:"9시"}, {value:"10", text:"10시"}, {value:"11", text:"11시"}
	   						, {value:"12", text:"12시"}, {value:"13", text:"13시"},  {value:"14", text:"14시"}
	   						, {value:"15", text:"15시"}, {value:"16", text:"16시"}
	   						, {value:"17", text:"17시"}
	   						]}    
					]},			
				]},
				{type: "fieldset", label: "엑셀업로드", list:[
					 {type: "block", offsetLeft: 20, list: [
						 { type: "upload" , label: "" , name: "file_upload1" ,width:390, inputWidth: 320, url: "/rcp/dlvyMgmt/regParExcelUp.do" , userdata: { limitSize:30000 } , autoStart: true } 
					]},
					 {type:"block", offsetLeft:20, list:[
						 {type:"hidden", name: "fileName"}
						,{type:"hidden",  label:"발송일자시간",name : "requestTime", value:""}	
					]},
				]},
			]	
				
		, buttons : {
			center : {
				btnSave : { label : "저장" },
				btnClose : { label : "닫기" }
			}
		}
		, onButtonClick : function(btnId) {
			
			var form = this; // 혼란방지용으로 미리 설정 (권고)
			var requestTime = "";
			
			/* 오늘 날짜 가져오기 */
			var date = new Date().format('yyyymmdd');
			var now = new Date();
			var hour = "0"+Number(now.getHours());
			var min = now.getMinutes();
			var sec = now.getSeconds();
				if(min < 10) {
					min = "0" + min;
				}
				if(sec < 10) {
					sec = "0" + sec;
				}
			var time = Number(hour + min + sec);
			
			switch (btnId) {
			
				case "btnSave" :
					
					if(mvno.cmn.isEmpty(PAGE.FORM6.getItemValue("subject"))){
						mvno.ui.alert("제목을 입력하세요.");
						return;
					}
				
					if(mvno.cmn.isEmpty(PAGE.FORM6.getItemValue("reqYmd"))){
						mvno.ui.alert("발송일자를 선택하세요.");
						return;
					}		
					
					if(mvno.cmn.isEmpty(PAGE.FORM6.getItemValue("reqHour"))){
						mvno.ui.alert("발송시간을 선택하세요.");
						return;
					}
							
					var reqYmdd = new Date(PAGE.FORM6.getItemValue("reqYmd")).format("yyyymmdd");			
					requestTime = reqYmdd + PAGE.FORM6.getItemValue("reqHour") + "0000";
					PAGE.FORM6.setItemValue("requestTime", requestTime);
					var nowDate = date +  time;
					
					// 현재시간 date형식으로 replace				
					var nowDt = new Date(nowDate.replace(/^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/, '$1-$2-$3 $4:$5:$6'));
					// 현재시간 date형식으로 replace
					var repDt =  new Date(requestTime.replace(/^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/, '$1-$2-$3 $4:$5:$6'));
					
					// 현재 날짜는 2022년07월22일 9시인데 등록하고싶은 날짜가 2022년07월21일 9시이면 등록이 안되게끔 함
					if(nowDt > repDt ) {
						mvno.ui.alert("이미 지난 발송일자입니다. 다른시간대를 선택해주세요");
						return;
					}
					
					// requestTime date형식으로 replace해서 만약 선택한시간이 9시면 8시30분이 나올수있게함			
					var dt = repDt;
					dt = dt - 1000 * 1800;
					var dddt = new Date(dt);
			
					// 예약전송시간 최소 30분전에 등록할수 있게함 선택한 시간대는 9시인데 현재시간이 8시31분이면 예약이 안되게끔 함
					if(nowDt > dddt) {
						mvno.ui.alert("전송시간 최소 30분전에 등록해주세요.");	
						return;
					}
					
					if(mvno.cmn.isEmpty(form.getItemValue("fileName"))){
							mvno.ui.alert("첨부된 파일이 없습니다");
							return;
						}
					var userOptions = {timeout:180000};
						
 					if(PAGE.FORM6.getItemValue("requestTime") != "") {				
						 	mvno.cmn.ajax(ROOT + "/cmn/smsmgmt/insertMsgSmsSend.json", form, function(resultData) {					
								if(resultData.result.code == "NOK") {
									mvno.ui.alert(resultData.result.msg);
				                    return;
								}			
								mvno.ui.notify("NCMN0004"); 	
								PAGE.FORM6.clear();
 	 							mvno.ui.closeWindowById("FORM6",true);
 	 							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);	
 	 							}, userOptions);
					}								
					
					break;
					
				case "btnClose":
					
					mvno.ui.confirm("나가시겠습니까?", function() {
					mvno.ui.closeWindowById("FORM6");
					PAGE.FORM6.clear();
					});
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