<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name  : msp_cmn_sms_1003.jsp
 * @Description : 수신자선택발송
 */
%>
	
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>
<div id="FORM3" class="section-full"></div>
<!-- 팝업영역 -->
<div id="POPUP1">
	<div id="POPUP1TOP"  class="section-full"></div>
	<div id="POPUP1MID" class="section-box"></div>
</div>
<script type="text/javascript">
	
var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				,{type:"block", list:[
					{type:"select", name:"searchCustDiv", label:"고객유형", width:80, offsetLeft:10, required:true}
					,{type:"newcolumn"}
					,{type:"calendar", name:"searchFromDt", label:"개통일자", width:100, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", validate: "NotEmpty", offsetLeft:20, required:true}
					,{type:"newcolumn"}
					,{type:"calendar", name:"searchToDt", width:100, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", validate: "NotEmpty", label:"~", labelAlign:"center", labelWidth:10, offsetLeft:5, required:true}
					,{type:"newcolumn"}
					,{type:"select", name:"searchBuyType", label:"구매유형", width:100, offsetLeft:20}
					,{type:"newcolumn"}
					,{type:"select", name:"searchMarketing", label:"마케팅동의", width:100, options:[{text:"- 전체 -", value:""}, {text:"동의", value:"Y"}, {text:"미동의", value:"N"}], offsetLeft:20}
				]}
				,{type:"block", list:[
					{type:"select", name:"searchCode", label:"검색구분", width:80, offsetLeft:10, options:[{text:"- 전체 -", value:""}, {text:"고객명", value:"custNm"}, {text:"휴대폰번호", value:"mobileNo"}, {text:"부가서비스", value:"addService"}]}
					,{type:"newcolumn"}
					,{type:"input", name:"searchValue", width:100, maxLength:50}
					,{type:"newcolumn"}
					,{type:"hidden", name:"searchOrgnId"}
					,{type:"newcolumn"}
					,{type:"input", name:"searchOrgnNm", label:"대리점", width:120, offsetLeft:20}
					,{type:"newcolumn"}
					,{type:"button", value:"찾기", name:"btnOrgFind"}
					,{type:"newcolumn"}
					,{type:"select", name:"searchRateCd", label:"요금제", width:120, offsetLeft:20}
				]}
				,{type:"button", name:"btnSearch", value:"조회", className:"btn-search2"}
			],
			onValidateMore : function(data) {
				if (data.searchStartDate > data.searchEndDate) {
					this.pushError("searchEndDt", "조회기간", "종료일자가 시작일자보다 클 수 없습니다.");
					//this.resetValidateCss();
				}
				
				if(data.searchCode == "" && data.searchValue != ""){
					this.pushError("searchCode", "검색구분", "검색구분을 선택하세요.");
				}
				
				if(data.searchCode != "" && data.searchValue == ""){
					this.pushError("searchValue", "검색어", "검색어를 입력하세요.");
				}
				
			},
			onChange : function(name, value) {
				var form = this;
				
				switch(name){
					case "searchOrgnNm":
						form.setItemValue("searchOrgnId","");
						break;
					case "searchCustDiv":
						if(value == "MSP"){
							form.setItemLabel("searchFromDt", "개통일자");
							form.enableItem("searchBuyType");
							form.enableItem("searchRateCd");
							form.enableItem("searchOrgnNm");
							form.enableItem("btnOrgFind");
							form.enableItem("searchCode");
							form.enableItem("searchValue");
							form.enableItem("searchMarketing");
							form.setItemValue("searchOrgnId", "");
							form.setItemValue("searchCode", "");
							form.setItemValue("searchValue", "");
						}else if(value == "MCP"){
							form.setItemLabel("searchFromDt", "가입일자");
							form.enableItem("searchMarketing");
							form.disableItem("searchBuyType");
							form.disableItem("searchRateCd");
							form.disableItem("searchOrgnNm");
							form.disableItem("btnOrgFind");
							form.disableItem("searchCode");
							form.disableItem("searchValue");
							form.setItemValue("searchMarketing", "");
							form.setItemValue("searchBuyType", "");
							form.setItemValue("searchRateCd", "");
							form.setItemValue("searchOrgnId", "");
							form.setItemValue("searchOrgnNm", "");
							form.setItemValue("searchCode", "");
							form.setItemValue("searchValue", "");
						}else{
							form.setItemLabel("searchFromDt", "신청일자");
							form.enableItem("searchBuyType");
							form.enableItem("searchRateCd");
							form.enableItem("searchCode");
							form.enableItem("searchValue");
							form.enableItem("searchOrgnNm");
							form.enableItem("btnOrgFind");
							form.disableItem("searchMarketing");
							form.setItemValue("searchBuyType", "");
							form.setItemValue("searchRateCd", "");
							form.setItemValue("searchCode", "");
							form.setItemValue("searchValue", "");
							form.setItemValue("searchOrgnId", "");
							form.setItemValue("searchOrgnNm", "");
							form.setItemValue("searchMarketing", "");
						}
						break;
					case "searchOrgnNm":
						form.setItemValue("searchOrgnId", "");
						break;
					case "searchCode":
						form.setItemValue("searchValue", "");
						break;
				}
			},
			onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId) {
					case "btnSearch" :
						PAGE.GRID1.list(ROOT + "/cmn/smsmgmt/getSmsSendReceiveList.json", form);
						break;
					case "btnOrgFind":
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("searchOrgnId", result.orgnId);
							form.setItemValue("searchOrgnNm", result.orgnNm);
						});
						break;
				}
			}
		}
		// GRID1
		,GRID1 : {
			 title      : "대상목록"
			,css        : {height : "420px"}
			,header     : "고객명,휴대폰번호,개통일,가입일,신청일,요금제,구매유형,마케팅동의,이메일수신동의,SMS수신동의,부가서비스,계약번호,홈페이지ID,진행상태"
			,columnIds  : "custNmMsk,mobileNoMsk,openDt,sysRdate,reqInDay,rateNm,buyTypeNm,marketingCd,emailAgr,smsAgr,addService,contractNum,portalId,reqStateNm"
			,widths     : "150,120,80,80,80,120,80,80,100,80,200,80,100,80"
			,colAlign   : "left,center,center,center,center,left,center,center,center,center,left,center,left,center"
			,colTypes   : "ro,roXp,roXd,roXd,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			,paging     : true
			,pageSize   : 100
			,showPagingAreaOnInit: true
			,multiCheckbox : true
			,buttons : {
				hright : {
					 btnSave : { label : "SMS발송" }
				}
			},
			onButtonClick : function (btnId, selectedData) {
				switch (btnId) {
				    case "btnSave":
					// SMS발송
					var rowIds = PAGE.GRID1.getCheckedRows(0);
					
					if(!rowIds) {
						mvno.ui.alert("ECMN0003");
						return;
					}
					
					var reqYmd = "";
					// 즉시발송 vs. 예약발송
					if(PAGE.FORM2.getItemValue("sendType") == "R"){
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("reqYmd"))){
							mvno.ui.alert("발송일자를 선택하세요.");
							return;
						}
						
						reqYmd = new Date(PAGE.FORM2.getItemValue("reqYmd")).format("yyyymmdd");
						
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("reqHour"))){
							mvno.ui.alert("발송시간을 입력하세요.");
							return;
						}
						
						if(PAGE.FORM2.getItemValue("reqHour").length != 2 ){
							mvno.ui.alert("발송시간은 24시 형식으로 입력하세요.");
							return;
						}
						
						if(Number(PAGE.FORM2.getItemValue("reqHour")) < 8 ||  Number(PAGE.FORM2.getItemValue("reqHour")) > 21){
							mvno.ui.alert("문자발송은 오전 8시에서 오후 9시까지 가능합니다.");
							return;
						}
					}else{
						// 즉시발송
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
					}
					
					if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("templateId"))){
						mvno.ui.alert("템플릿을 선택하세요.");
						return;
					}
					
					var confirmMsg = "[ " + PAGE.GRID1.getCheckedRowData().length + " ] 건을 발송 하시겠습니까?";
					var sdata = PAGE.GRID1.classifyRowsFromIds(rowIds, "custDiv,mobileNo,contractNum,portalId,sendType,requestTime,templateId");
					for(var i=0; i<sdata.ALL.length; i++){
						sdata.ALL[i]["sendType"] = PAGE.FORM2.getItemValue("sendType");
						sdata.ALL[i]["requestTime"] = reqYmd + PAGE.FORM2.getItemValue("reqHour") + "0000";
						sdata.ALL[i]["templateId"] = PAGE.FORM2.getItemValue("templateId");
					}
					
					mvno.ui.confirm(confirmMsg, function() {
						mvno.cmn.ajax4json(ROOT + "/cmn/smsmgmt/saveSmsSendReceiveList.json", sdata, function() {
							mvno.ui.notify("SMS를 발송하였습니다.");
							PAGE.GRID1.clearAll();
							PAGE.FORM2.clear();
							PAGE.FORM2.disableItem("reqYmd");
							PAGE.FORM2.disableItem("reqHour");
						});
					});
					
					break;
				}
		   }
		}
		// FORM2
		,FORM2 : {
			title : "SMS발송상세",
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				,{type:"block", list:[
					{type:"select", name:"sendType", label:"발송구분", width:80, offsetLeft:10, options:[{text:"즉시발송", value:"I"}, {text:"예약발송", value:"R"}]}
					,{type:"newcolumn"}
					,{type:"calendar", name:"reqYmd", label:"발송일자", width:100, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", offsetLeft:20}
					,{type:"newcolumn"}
					,{type:"input", name:"reqHour", label:"발송시간", width:40, offsetLeft:20, maxLength:2, validate:"ValidInteger"}
					,{type:"newcolumn"}
					,{type:"select", name:"templateId", label:"템플릿", width:200, offsetLeft:20}
				]}
			]
			,onChange : function(name, value){
				var form = this;
				switch(name){
					case "sendType" :
						if(value == "R"){
							var ymd = new Date().format("yyyymmdd");
							form.enableItem("reqYmd");
							form.enableItem("reqHour");
							form.setItemValue("reqYmd", ymd);
						}else{
							form.disableItem("reqYmd");
							form.disableItem("reqHour");
							form.setItemValue("reqYmd", "");
							form.setItemValue("reqHour", "");
						}
						break;
					case "templateId" :
						if(value != ""){
							mvno.ui.createForm("FORM3");
							PAGE.FORM3.clear();
							
							var sdata = {"templateId" : value};
							
							mvno.cmn.ajax(ROOT + "/cmn/smsmgmt/getSendTemplate.json", sdata, function(result) {
								var data = result.result.text;
								PAGE.FORM3.setItemValue("text", data);
								
								mvno.ui.popupWindowById("FORM3", "템플릿", 400, 300, {
									onClose: function(win) {
										win.closeForce();
									}
								});
								
							});
						}
						
						break;
				}
			}
			,buttons : {
				left : {
					btnTemplate : { label : "업로드양식" }
					, btnUpload : { label : "엑셀업로드" }
				}
			}
			, onButtonClick : function(btnId, selectedData) {
				var form = this;
				switch(btnId) {
					case "btnTemplate" :
						mvno.cmn.download("/cmn/smsmgmt/getSmsSendXlsTemplate.json");
						break;
					case "btnUpload" :
						// 템플릿 선택 후 엑셀업로드 처리
						if(mvno.cmn.isEmpty(form.getItemValue("templateId"))){
							mvno.ui.alert("템플릿 선택 후 엑셀업로드 가능합니다.");
							return;
						}
						
						var requestTime = "";
						
						if(form.getItemValue("sendType") == "R"){
							if(mvno.cmn.isEmpty(form.getItemValue("reqYmd"))){
								mvno.ui.alert("발송일자를 선택하세요.");
								return;
							}
							
							if(mvno.cmn.isEmpty(form.getItemValue("reqHour"))){
								mvno.ui.alert("발송시간을 선택하세요.");
								return;
							}

							if(form.getItemValue("reqHour").length != 2 ){
								mvno.ui.alert("발송시간은 24시 형식으로 입력하세요.");
								return;
							}
							
							if(Number(form.getItemValue("reqHour")) < 8 ||  Number(form.getItemValue("reqHour")) > 21){
								mvno.ui.alert("문자발송은 오전 8시에서 오후 9시까지 가능합니다.");
								return;
							}
							
							var reqYmd = new Date(form.getItemValue("reqYmd")).format("yyyymmdd");
							requestTime = reqYmd + form.getItemValue("reqHour") + "0000";
						}
						
						mvno.ui.createGrid("POPUP1TOP");
						mvno.ui.createForm("POPUP1MID");
						
						PAGE.POPUP1TOP.clearAll();
						PAGE.POPUP1MID.clear();
						
						PAGE.POPUP1MID.setFormData({});
						
						PAGE.POPUP1MID.setItemValue("sendType", form.getItemValue("sendType"));
						PAGE.POPUP1MID.setItemValue("requestTime", requestTime);
						PAGE.POPUP1MID.setItemValue("templateId", form.getItemValue("templateId"));
						
						PAGE.POPUP1MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP1TOP.clearAll();
							
							return true;
						});
						
						var uploader = PAGE.POPUP1MID.getUploader("file_upload1");
						uploader.revive();
						
						mvno.ui.popupWindowById("POPUP1", "엑셀업로드", 450, 500, {
							onClose1: function(win) {
								uploader.reset();
							}
						});
						
						break;
				}
			}
		}
		// FORM3
		,FORM3 : {
			title : "",
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				,{type:"block", list:[
					{type:"input", name:"text", width:320, rows:12, readonly:true}
				]}
			]
			,buttons : {
				center : {
					btnClose : { label : "닫기" }
				}
			}
			,onButtonClick : function(btnId) {
				var form = this;
				switch(btnId){
					case "btnClose" :
						mvno.ui.closeWindowById("FORM3");
						break;
				}
			}
		}
		// POPUP1 GROUP
		, POPUP1TOP : {
			 css : { height : "270px" }
			,header : "휴대폰번호"
			,columnIds : "mobileNo" 
			,widths : "150"
			,colAlign : "center"
			,colTypes : "roXp"
		}
		, POPUP1MID : {
			title : "엑셀업로드"
			,items : [
				{type : "block", list : [
					{type:"upload", label:"엑셀파일", name:"file_upload1", inputWidth:330, url:"/cmn/smsmgmt/setSmsSendXlsUpload.do", userdata:{limitSize:10000}, autoStart:true, offsetLeft:20} 
				]}
				,{type:"hidden", name : "rowData"}
				,{type:"hidden", name : "sendType"}
				,{type:"hidden", name : "requestTime"}
				,{type:"hidden", name : "templateId"}
			]
			,buttons : {
				center : {
					btnSave : { label : "SMS발송" },
					btnClose1 : { label : "닫기" }
				}
			}
			,onUploadFile : function(realName, serverName) {
				fileName = serverName;
			}
			,onUploadComplete : function(count){
				
				var userOptions = {timeout:180000};
				
				mvno.cmn.ajax(ROOT + "/cmn/smsmgmt/setSmsSendXlsDataList.json", {fileName : fileName}, function(resultData) {
					var rData = resultData.result;
					
					PAGE.POPUP1TOP.clearAll();
					
					PAGE.POPUP1TOP.parse(rData.data.rows, "js");
					
				}, userOptions);
			}
			,onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				var uploader = form.getUploader("file_upload1");
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
						
						if(arrObj.length > 1000){
							mvno.ui.alert("엑셀업로드는  1000건 이하로 등록하세요.");
							return;
						}
						
						var sData = {
							items : arrObj
							, sendType : form.getItemValue("sendType")
							, requestTime : form.getItemValue("requestTime")
							, templateId : form.getItemValue("templateId")
						};
						
						sData.items = arrObj;
						
						var userOptions = {timeout:180000};
						
						mvno.cmn.ajax4json(ROOT + "setSmsSendXlsDataComplete.json", sData, function(result) {
							uploader.reset();
							mvno.ui.closeWindowById("POPUP1", true);
							mvno.ui.notify("NCMN0004");
						}, userOptions);
						
						break;
						
					case "btnClose1":
						uploader.reset();
						mvno.ui.closeWindowById("POPUP1");
						break;
				}
			}
		}
};
	
var PAGE = {
    title : "${breadCrumb.title}",
    breadcrumb : "${breadCrumb.breadCrumb}",  
    buttonAuth: ${buttonAuth.jsonString} ,
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		mvno.ui.createForm("FORM2");
		
		PAGE.FORM2.disableItem("reqYmd");
		PAGE.FORM2.disableItem("reqHour");
		
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0071", etc2:"Y", orderBy:"etc6"}, PAGE.FORM1, "searchCustDiv"); // 고객구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9004"}, PAGE.FORM1, "searchBuyType"); // 구매유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/smsmgmt/getRateCombo.json", "", PAGE.FORM1, "searchRateCd");	// 요금제
		mvno.cmn.ajax4lov( ROOT + "/cmn/smsmgmt/getTemplateCombo.json", {etc1:"Y"}, PAGE.FORM2, "templateId");	// 템플릿
		
		var endDate = new Date().format('yyyymmdd');
		var time = new Date().getTime();
		time = time - 1000 * 3600 * 24 * 6;
		var startDate = new Date(time);
		
		PAGE.FORM1.setItemValue("searchFromDt", startDate);
		PAGE.FORM1.setItemValue("searchToDt", endDate);
		
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