<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name  : taxMgmt.jsp
 * @Description : 제세공과금
 */
%>
<!-- 메인화면 -->	
<div id="FORM1" class="section-search"></div>
<!-- 조회결과 -->
<div id="GRID1" class="section"></div>
<!-- 상세화면-->
<div id="GRID2" class="section"></div>
<!-- 템플릿 엑셀업로드 -->
<div id="FORM2" class="section-box"></div>	 
<!-- 등록 상세화면 -->
<div id="FORM3" class="section-box"></div>	 

<script type="text/javascript">

function goTaxDetail(taxNo,pSize,pIdx){

	mvno.ui.createForm("FORM3");
	
	mvno.ui.popupWindowById("FORM3", "등록정보", 550, 380, {
        onClose: function(win) {
       		win.closeForce();
        }
    });

	var startSize = (pIdx-1)*pSize;
	var endSize = pIdx*pSize; 
	var rowId = "";
	
	for (var i=startSize; i<endSize; i++) {
		var taxNoStr = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("taxNo")).getValue();
		
		if(taxNoStr == taxNo){
			rowId = i;
			break;
		}
	 }
	var grid1 = PAGE.GRID1;
    
	var subject = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("subject" ) ).getValue();
	var kakaoYn = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("kakaoYn" ) ).getValue();
	var addressYn = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("addressYn" ) ).getValue();
	var taxTmpId = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("taxTmpId" ) ).getValue();
	var reqYmd = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("requestTime" ) ).getValue().split(' ')[0];
	var reqHour = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("requestHour" ) ).getValue();
	var agentYn = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("agentYn" ) ).getValue();
	var agentTmpId = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("agentTmpId" ) ).getValue();
	
	PAGE.FORM3.setItemValue("subject",subject);
	PAGE.FORM3.setItemValue("kakaoYn",kakaoYn);
	PAGE.FORM3.setItemValue("addressYn",addressYn);
	PAGE.FORM3.setItemValue("taxTmpId",taxTmpId); 	
	PAGE.FORM3.setItemValue("reqYmd",reqYmd);
	PAGE.FORM3.setItemValue("reqHour",reqHour);
	PAGE.FORM3.setItemValue("agentYn",agentYn);
	PAGE.FORM3.setItemValue("agentTmpId",agentTmpId);
	PAGE.FORM3.clearChanged(); 				 
}

var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				,{type:"block", list:[
					{type : "calendar",width : 100,label : "전송일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",  value:"${srchStrtDt}",name : "srchStrtDt", offsetLeft:10, readonly:true}
					, {type : "newcolumn"}
					, {type : "calendar",label : "~",name : "srchEndDt",labelAlign : "center",dateFormat : "%Y-%m-%d", value:"${srchEndDt}",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true}
					, {type : "newcolumn"}
					, {type : "input", label:"제목", width:445, name : "subject" ,offsetLeft : 30}
				]}		
				,{type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}
				,{type : 'hidden', name: "taxNo", value:"0"}
				//버튼 여러번 클릭 막기 변수
				, {type : 'hidden', name: "btnCount1", value:"0"} 
				, {type : 'hidden', name: "btnExcelCount1", value:"0"}
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
			},
			onButtonClick : function(btnId) {
				var form = this;
				switch(btnId) {
					case "btnSearch" :

						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						var url = ROOT + "/gift/taxMgmt/taxMgmtList.json";
						
						PAGE.GRID1.list(url, form, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						PAGE.GRID2.clearAll();
						break;			
				}
			}
		}
		// GRID1
		,GRID1 : {
			css : {
				height : "300px"
			}
			, title : "조회결과"
			, header : "번호,전송일자,제목,주소여부,등록건수,회신건수,등록자,등록일자,카카오여부,템플릿ID,제세공과금번호히든,발송시간,법정대리인문자발송,법정대리인템플릿ID" // 13
			, columnIds : "taxNoStr,requestTime,subject,addressYn,sendCnt,replyCnt,regstNm,regstTime,kakaoYn,taxTmpId,taxNo,requestHour,agentYn,agentTmpId" // 13
			, widths : "60,140,283,80,80,80,70,140,0,0,0,0,0,0" // 13
			, colAlign : "center,center,left,center,center,center,center,center,center,center,center,center,center,center" // 13
			, colTypes : "link,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" // 13
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str" // 13
			, paging : true
			, pageSize:20
			, hiddenColumns: [8,9,10,11,12,13]
			, showPagingAreaOnInit: true
			, buttons : {
				left : {
					btnDnExcelUp : { label : "엑셀업로드양식"}
				},
				right  : {
					btnTmpExcel : { label : "등록" },
					btnDel   : { label : "삭제" },	
				}	
			}
			,onRowSelect : function (rowId, celInd) {
				PAGE.GRID2.list(ROOT + "/gift/taxMgmt/taxMgmtHist.json", this.getRowData(rowId));
			}
			,onButtonClick : function (btnId) {
				var grid = this;
				
				switch (btnId) {
					//엑셀업로드 양식
					case "btnDnExcelUp":
						
						mvno.cmn.download("/gift/taxMgmt/getTaxTmpExcelDown.json");					
						break;
						
					//템플릿 엑셀업로드
					case "btnTmpExcel":
						
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();
						
 						var fileName;
 						
 						PAGE.FORM2.setItemValue("reqYmd", "${tomorrow}");
						
 						PAGE.FORM2.attachEvent("onUploadFile", function(realName, serverName) {
 							PAGE.FORM2.setItemValue("fileName", serverName);
							
 						});
						
 						PAGE.FORM2.attachEvent("onFileRemove",function(realName, serverName) {		
 							PAGE.FORM2.setItemValue("fileName", "");
 							return true;
 						});
						PAGE.FORM2.disableItem("agentTmpId");
					
						var uploader = PAGE.FORM2.getUploader("file_upload1");
						uploader.revive();
						uploader.reset();
						
						mvno.ui.popupWindowById("FORM2", "템플릿 엑셀업로드", 550, 410, {
							onClose: function(win) {								
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){		
									win.closeForce();
								});
							}
						}); 
						
						break;
						
						//삭제
					case "btnDel":
						/* 오늘 날짜 가져오기 */
						var nowDt = new Date().format('yyyymmdd');		
						var reqDt = new Date(grid.getSelectedRowData().requestTime).format("yyyymmdd");

						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						} else if(nowDt >= reqDt){
							mvno.ui.alert("전송일자가 당일이거나 지났을 경우 삭제가 불가능 합니다.");
							return;
						}
						
						mvno.cmn.ajax4confirm("LGS00041", ROOT + "/gift/taxMgmt/deleteTaxSmsList.json", grid.getSelectedRowData(), function(result) {
							mvno.ui.notify("LGS00042");
							PAGE.GRID1.refresh();
							PAGE.GRID2.clearAll();
						});
						
						break;
				}
		   }
		}
		//템플릿 엑셀업로드 화면
		, FORM2 : {
			title : "<font size='2' color='red'>(엑셀 업로드시 시간이 다소 소요될 수 있습니다.)</font>",
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0,  inputWidth: 'auto'},
				{type: "fieldset", label: "등록정보", list:[
					{type:"settings"},
					{type:"block", offsetLeft:15, list:[
						  {type:"input", name:"subject", label:"제목", width:330, value:"", labelWidth:70}                                
					]},  
					{type: "block", blockOffset: 0,offsetLeft: 15, list: [
					  {type : "calendar",width : 150,name: "reqYmd",label : "전송일자", value:"${srchStrtDt}",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",calendarPosition:"bottom", labelWidth:70}	
					, {type : "newcolumn"}
					, {type:"select", name:"reqHour", label:"발송시간", width:65, value:"", maxLength:2, offsetLeft: 40,validate:"ValidInteger",labelWidth:70,
	   					options:[{value:"09", text:"9시"}, {value:"10", text:"10시"}, {value:"11", text:"11시"}
	   						, {value:"12", text:"12시"}, {value:"13", text:"13시"},  {value:"14", text:"14시"}
	   						, {value:"15", text:"15시"}, {value:"16", text:"16시"}
	   						, {value:"17", text:"17시"}
	   						]}    
					]},
					{type:"block", offsetLeft:15, list:[
						  {type:"input", name:"taxTmpId", label:"템플릿ID", width:120, value:"", maxLength:10, labelWidth:70}    
						, {type : "newcolumn"}
						, {type:"select", name:"kakaoYn", label:"카카오여부", width:65, maxLength:2, offsetLeft: 70,labelWidth:70,
	   					options:[{value:"N", text:"N"}, {value:"Y", text:"Y"}
	   					]}
					]},	
					{type:"block", offsetLeft:15, list:[
						, {type:"select", name:"addressYn", label:"주소여부", width:65,  maxLength:2, labelWidth:70,
		   					options:[{value:"N", text:"N"}, {value:"Y", text:"Y"}
	   					]}
					]},
					{type:"block", offsetLeft:15, list:[
						{type:"select", name:"agentYn", label:"법정대리인 문자발송", width:65, maxLength:2, labelWidth:70
								, options:[{value:"N", text:"N"}, {value:"Y", text:"Y"}
						]}
						, {type : "newcolumn"}
						, {type:"input", name:"agentTmpId", label:"템플릿ID", width:120, value:"", maxLength:10, offsetLeft: 90, disabled:true}
					]},
				]},
				{type: "fieldset", label: "엑셀업로드", list:[
					 {type: "block", offsetLeft: 15, list: [
						 { type: "upload" , label: "" , name: "file_upload1" ,width:390, inputWidth: 320, url: "/rcp/dlvyMgmt/regParExcelUp.do" , userdata: { limitSize:1000 } , autoStart: true } 
					]},
					 {type:"block", offsetLeft:20, list:[
						 {type:"hidden", name: "fileName"}
						,{type:"hidden",  label:"전송일자",name : "requestTime", value:""}	
					]},
				]},
			]	
			, buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			}

			, onChange: function (name, value) {
				switch(name) {
					case "agentYn":
						if(value == "Y"){
							PAGE.FORM2.enableItem("agentTmpId");
						}else if(value == "N"){
							PAGE.FORM2.setItemValue("agentTmpId", "");
							PAGE.FORM2.disableItem("agentTmpId");
						}
						break;
				}
			}
			, onButtonClick : function(btnId) {
				
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				
				/* 오늘 날짜 가져오기 */
				var nowDt = new Date().format('yyyymmdd');
				var requestTime = "";
				
				switch (btnId) {
				
					case "btnSave" :
						
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("subject"))){
							mvno.ui.alert("제목을 입력하세요.");
							return;
						}
					
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("reqYmd"))){
							mvno.ui.alert("전송일자를 선택하세요.");
							return;
						}

						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("reqHour"))){
							mvno.ui.alert("발송시간을 선택하세요.");
							return;
						}
						 
						if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("taxTmpId"))){
							mvno.ui.alert("템플릿을 선택하세요.");
							return;
						}
						
						var reqDt = new Date(PAGE.FORM2.getItemValue("reqYmd")).format("yyyymmdd");
						requestTime = reqDt + PAGE.FORM2.getItemValue("reqHour") + "3000";
						PAGE.FORM2.setItemValue("requestTime", requestTime);
						
						if(nowDt >= reqDt) {
							mvno.ui.alert("전송일자는 오늘 이후의 일자를 선택해야 합니다.");
							return;
						}

						if(PAGE.FORM2.getItemValue("agentYn") == "Y"){
							if(mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("agentTmpId"))){
								mvno.ui.alert("법정대리인 템플릿을 선택하세요.");
								return;
							}
						}
						
						if(mvno.cmn.isEmpty(form.getItemValue("fileName"))){
 							mvno.ui.alert("첨부된 파일이 없습니다");
 							return;
 						}
						var userOptions = {timeout:180000};
					
	 					if(PAGE.FORM2.getItemValue("reqYmd") != "") {				
							 	mvno.cmn.ajax(ROOT + "/gift/taxMgmt/insertTaxSmsTmp.json", form, function(resultData) {					
									if(resultData.result.code == "NOK") {
										mvno.ui.alert(resultData.result.msg);
					                    return;
									}			
									mvno.ui.notify("NCMN0004"); 			
									PAGE.FORM2.clear();
	 	 							mvno.ui.closeWindowById("FORM2",true);
	 	 							PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);	
	 	 							}, userOptions);
							 	
						}								
					break;
						
					case "btnClose":
						
						mvno.ui.confirm("나가시겠습니까?", function() {
						mvno.ui.closeWindowById("FORM2");
						PAGE.FORM2.clear();
						});
						
					break;
				}
			}
		}

		//상세 화면
		, FORM3 : {
			title : "등록정보 상세보기",
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0,  inputWidth: 'auto'},
				{type: "fieldset", label: "등록정보", list:[
					{type:"settings"},
					{type:"block", offsetLeft:15, list:[
						  {type:"input", name:"subject", label:"제목", width:330, value:"", disabled:true, labelWidth:70}                                
					]},  
					{type: "block", blockOffset: 0,offsetLeft: 15, list: [
					  {type : "input",width : 170,name: "reqYmd",label : "전송일자", disabled:true, labelWidth:70}	
					, {type : "newcolumn"}
					, {type:"input", name:"reqHour", label:"발송시간", width:65, maxLength:2, offsetLeft: 20,labelWidth:70, disabled:true, labelWidth:70}
					]},
					{type:"block", offsetLeft:15, list:[
						  {type:"input", name:"taxTmpId", label:"템플릿ID", width:120, value:"", maxLength:10, disabled:true, labelWidth:70}    
						, {type : "newcolumn"}
						, {type:"input", name:"kakaoYn", label:"카카오여부", width:65, maxLength:2, offsetLeft: 70,labelWidth:70, disabled:true}
					]},
					{type:"block", offsetLeft:15, list:[
						{type:"input", name:"addressYn", label:"주소여부", width:65, maxLength:2, disabled:true, labelWidth:70}
					]},
					{type:"block", offsetLeft:15, list:[
						{type:"input", name:"agentYn", label:"법정대리인 문자발송", width:65, maxLength:2, disabled:true, labelWidth:70}
						, {type : "newcolumn"}
						, {type:"input", name:"agentTmpId", label:"템플릿ID", width:120, value:"", maxLength:10, disabled:true , offsetLeft: 90}
					]},
				]},
			]	
			, buttons : {
				center : {
					btnClose : { label : "닫기" }
				}
			}
			, onButtonClick : function(btnId) {
				switch (btnId) {
					case "btnClose":
					mvno.ui.closeWindowById("FORM3");
					PAGE.FORM3.clear();
					break;
				}
			}
		}

		// 상세 리스트
		, GRID2 : {
			css : {
				height : "180px"
			},
			title : "상세 조회결과",
			header : "계약번호,고객명,수신번호,주민번호,법정대리인 연락처,주소,회신일자", // 7
			columnIds : "contractNum,custName,telNo,ssn,agentTelNum,addr,recvTime", // 7
			widths : "120,100,130,170,170,253,160", // 7
			colAlign : "center,center,center,center,center,left,center", // 7
			colTypes : "ro,ro,ro,ro,ro,ro,ro", // 7
			colSorting : "str,str,str,str,str,str,str", // 7
			paging:true,
			buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				}
			},
			onButtonClick : function(btnId) {
				switch (btnId) {
					case "btnDnExcel":
						if(PAGE.GRID2.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;	
						}else{
							var selectedData = PAGE.GRID1.getSelectedRowData();
							PAGE.FORM1.setItemValue("taxNo", selectedData.taxNo);
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/gift/taxMgmt/taxMgmtSendListEx.json?menuId=GIFT100041", true, {postData:searchData});
							break;
						}
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
		mvno.ui.createGrid("GRID2");
	}	
};

</script>