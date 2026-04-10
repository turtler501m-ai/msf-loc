<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>  
<div id="FORM2" class="section-box"></div>
<div id="GROUP1">
	<div id="FORM3" class="section-box"></div>
	<div id="GRID2" class="section-full"></div>
</div>
<FORM name="viewForm" id="viewForm">
	<input type="hidden" name="AGENT_VERSION" value="">
	<input type="hidden" name="SERVER_URL" value="">
	<input type="hidden" name="VIEW_TYPE" value="TVIEW">
	<input type="hidden" name="FILE_PATH" value="">
	<input type="hidden" name="ENCODE_YN" value="">
	<input type="hidden" name="USE_BM" value="">
	<input type="hidden" name="USE_DEL_BM" value="">
</FORM>

	
<!-- Script -->
<script type="text/javascript">

	//스캔관련
	var blackMakingYn = "Y";					// 블랙마킹사용권한
	var blackMakingFlag = "N";					// 블랙마킹해제권한 (마스킹권한여부에 따라 변화)
	var maskingYn = '${maskingYn}';				// 마스킹권한
	if(maskingYn == "Y") {
		blackMakingFlag = "Y";
	}	
	var agentVersion = '${agentVersion}';		// 스캔버전
	var serverUrl = '${serverUrl}';				// 서버환경 (로컬 : L, 개발 : D, 운영 : R)
	var scanSearchUrl = '${scanSearchUrl}';		// 스캔호출 url
	var scanDownloadUrl = '${scanDownloadUrl}';	// 스캔파일 download url

	function setCurrentMonthFirstDay(form, name)
	{
		var today = new Date();

		var mm = today.getMonth(); //January is 0!
		var yyyy = today.getFullYear();

		form.setItemValue(name, new Date(yyyy,mm,01));
	}

	function setCurrentDate(form, name)
	{
		//var today = new Date().format("yyyy-MM-dd");

		form.setItemValue(name, new Date());
		
	}

	function goCustInfoData(contractNum)
	{
		var url = "/pps/hdofc/custmgmt/HdofcCustMgmtInfoDetailForm.do";
		var title = "고객정보상세"
		var menuId = "PPS1100007";

		var myTabbar = window.parent.mainTabbar;

		if (myTabbar.tabs(url)) {
        	myTabbar.tabs(url).close(); // 기존에 있는 탭을 닫기
        }

        myTabbar.addTab(url, title, null, null, true);
        myTabbar.tabs(url).attachURL(url + "?menuId=" + menuId+"&contractNum="+contractNum);

        // 잘안보여서.. 일단 살짝 Delay 처리 
        setTimeout(function() {
            //mainLayout.cells("b").progressOff();
        }, 100);
	}
	
	function userPaperImageView(contractNum,pNum,pSize,pIdx){
		
		var startSize = ((pIdx-1)*pSize);
		var endSize = pIdx*pSize;
		var rowId = "";
		for (var i=startSize; i<endSize; i++) {
			var imgSeqStr = PAGE.GRID2.cells(PAGE.GRID2.getRowId(i), PAGE.GRID2.getColIndexById("imgSeq")).getValue();
			if(pNum == imgSeqStr){
				rowId = i;
				break;
			} 
		 }
		//var rowId = linenum.substring(0,linenum.length-1);
		var pImageStr = "";
		var grid2 = PAGE.GRID2;
		var fileName="";
		var filePath="";
		
		var filePath="";
		var encodeYn="";
		filePath = grid2.cells(grid2.getRowId(rowId),grid2.getColIndexById("imgPath" ) ).getValue();
		encodeYn = grid2.cells(grid2.getRowId(rowId),grid2.getColIndexById("encFlag" ) ).getValue();
		
		mvno.cmn.ajax(
		ROOT + "/pps/filemgmt/ppsCheckFile.json",
		{
			filePath : filePath
		},
		function(results) {

			var width = 750;
			var height = 650;
			var top  = 10;
			var left = 10;
			
			var result = results.result;
            var retCode = result.retCode;
            var msg = result.retMsg;

			if(retCode == "0000"){
			
				//확장자 체크	
				var thumbext = filePath.slice(filePath.indexOf(".") + 1).toLowerCase(); //파일 확장자를 잘라내고, 비교를 위해 소문자로 만듭니다.
				
				if(thumbext == "jpg" || thumbext == "jpeg" ||  thumbext == "gif" ||  thumbext == "tif" ||  thumbext == "tiff"){ //확장자를 확인합니다.

					document.viewForm.FILE_PATH.value = filePath;
					document.viewForm.ENCODE_YN.value = encodeYn;
					document.viewForm.AGENT_VERSION.value = agentVersion;
					document.viewForm.SERVER_URL.value = serverUrl;
					document.viewForm.USE_BM.value = blackMakingYn;	
					document.viewForm.USE_DEL_BM.value = blackMakingFlag;					

 					var data = $("#viewForm").serialize();
					var url = scanSearchUrl + "?" + encodeURIComponent(data);
								
					// 타임아웃 설정 - 10초이내 응답없으면 서비스 실행상태가 아님.
					var timeOutTimer = window.setTimeout(function (){
						mvno.ui.confirm("이미지 프로그램이 설치되지 않았거나 실행중이 아닙니다. 설치페이지로 이동 하시겠습니까?",function(){
							window.open(scanDownloadUrl, "", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);         // 설치페이지로 이동
						});
					}, 10000);
					 
					$.ajax({     
						type : "GET", 
						url : url,     
						crossDomain : true,
						dataType : 'jsonp',
						success : function(args){
							window.clearTimeout(timeOutTimer);
							if(args.RESULT == "SUCCESS") {
								console.log("SUCCESS");
							} else if(args.RESULT == "ERROR_TYPE2") {
								mvno.ui.alert("버전이 다릅니다. 설치페이지로 이동해주세요.");
								window.open(scanDownloadUrl, "", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);         // 설치페이지로 이동
							} else {
								mvno.ui.alert(args.RESULT + " : " + args.RESULT_MSG);
							}
						}
					});
					
					
				}else{
					
					mvno.cmn.download('/pps/filemgmt/downFile.json?path=' + filePath);
					
				}
			
			
			}else{
				
				alert(msg);
			}
			
		},
		null
		);	
		

	}
	
	var DHX = {
	
		FORM1 : {
			items : [ 
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "검수등록일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : "",labelWidth : 80, offsetLeft:0},
						 {type : "newcolumn", offset:3},
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 {type : "select",label : "상태",name : "searchChkStatus",width:100},
					 ]},// 1row
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "input",label : "대리점 ",name : "searchAgentNm", width:100,maxlength:20,labelWidth : 80},
		                 {type : "newcolumn", offset:11},
		                 {type : "select",name : "searchAgentId",width:100},
		                 {type : "newcolumn", offset:3},
		                 {type : "select",label:"검색",name : "searchType",width:100, width:100},	
		                 {type : "newcolumn", offset:3},
	                   	 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: ":"}
	                   	 ]},	 
	                    {type : "newcolumn", offset:3},
	                   	{type : "input",name : "searchNm", width:100,maxlength:20}
					 ]},
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search2"},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"} //버튼 여러번 클릭 막기 변수 by han
			]
			,onButtonClick : function(btnId) {
	
				var form = this;
	
				switch (btnId) {
				
					case "btnSearch":
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						PAGE.GRID1.list(ROOT + "/pps/hdofc/imgchkmgmt/CustImgChkMgmtList.json", form, {
							callback : function () {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}
						});
						
						break;
				}
	
			},
			onChange : function(name, value) {
				switch(name){
					case "searchRcgAmt":
						
						var rcgVal = PAGE.FORM1.getItemValue("searchRcgAmt"); 
						
						PAGE.FORM1.disableItem("searchRcgAmt2");
						PAGE.FORM1.setItemValue("searchRcgAmt2", rcgVal);
						
						if("ETC" == rcgVal){
							PAGE.FORM1.enableItem("searchRcgAmt2");
							PAGE.FORM1.setItemValue("searchRcgAmt2", 0);
						}
						else if("" == rcgVal){
							PAGE.FORM1.disableItem("searchRcgAmt2");
							PAGE.FORM1.setItemValue("searchRcgAmt2", 0);
						}
						
					break;
				}
			}
			,onKeyUp : function(inp, ev, name, value)
			{
				if(ev.keyCode == 13)
				{
					switch(name)
					{
						case "searchAgentNm":
							var agentStr  = this.getItemValue("searchAgentNm");
							var selType ="popPin";
							//alert(agentStr);
							mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json",
									{ 
									  selectType : selType , 
								      searchAgentStr : agentStr
									} 
						      , PAGE.FORM1, "searchAgentId");
							var agentOption =  this.getOptions("searchAgentId");
					        
					        
					           if(agentOption.length==1)
						       {
					        	   //alert("ㄷ대리점이 존재하지 않습니다.");
					           }else if(agentOption.length==2)
						        {

							        var selValue = agentOption[1].value;

					        	   this.setItemValue("searchAgentId",selValue);
					           }
														      
						      
							break;
							
						default :
							if (this.getItemType(name) == 'input'){
								//대리점검색시 엔터클릭시 검색안되도록
								this.setItemFocus("btnSearchPps");	// input focus 가 남아있으면.. Enter 키가 계속먹음.
								this.callEvent("onButtonClick", ["btnSearchPps"]);		// 조회버튼 이름은 'btnSearch' 로!
							}
						
							break;
					
					}	
				}
			}
			,onValidateMore : function(data){
				if(data.startDt == '')
				{
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("startDt", "개통일자", "사용일자을 입력해주세요.");
				}
				
				if(data.endDt == '')
				{
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("endDt", "개통일자", "사용일자을 입력해주세요.");
				}
				
				if(data.startDt > data.endDt)
				{
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("startDt", "조회기간", "종료일자보다 시작일자 값이 큽니다.");
				}
				
				 if(( data.searchType == null || data.searchType == '' ) && (data.searchNm != null && data.searchNm != '' ))
				{
					 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					 this.pushError("searchType", "검색조건", "을 선택해주세요");							
				}

				 if(data.searchType=="contractNum" )
				 {
					
					  if(data.searchNm.match(/^\d+$/ig) == null){
						  PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						  PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						  this.pushError("searchNm","계약번호","숫자만 입력하세요");
					   }
							
				  }
				
				if(data.searchType=="subscriberNo")
				{
					if(data.searchNm.match(/^\d+$/ig) == null)
					{
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						this.pushError("searchNm","CTN","숫자만 입력하세요");
					}
				}
					
			}
		}
		,GRID1 : {
	
			css : {
				height : "550px"
			}
			,title : "조회결과"
			,header : "검수등록번호,계약번호,개통번호,업무구분,개통일,개통대리점,검수등록일,검수현황,개통신청서,신분증,기타,개통서류,신분증,서명,기타,검수메모,등록자,처리자,처리일,seq"
			,columnIds : "eventSeq,contractNumStr,subscriberNo,mnpIndCdNm,lstComActvDate,agentNm,regDt,chkStatusNm,imgOpenCnt,imgIdCnt,imgEtcCnt,dStr,iStr,sStr,eStr,memo1Flag,regAdminNm,chkAdminNm,chkDate,seq"
			,widths : "100,100,100,80,80,120,80,80,70,70,70,80,80,80,80,70,120,120,80,0"
			,colAlign : "center,center,center,center,center,center,center,center,right,right,right,center,center,center,center,center,center,center,center,center"
			,colTypes : "ro,link,roXp,ro,ro,ro,ro,ro,ron,ron,ron,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			,colSorting : "str,str,str,str,str,str,str,str,int,int,int,str,str,str,str,str,str,str,str,str"
			,paging : true
			,pageSize :15
			,pagingStyle :2
			,buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }, 
				
				},
				right : {
					btnReg : { label : "등록" },
					btnMod : { label : "전체검수" },
					btnDel : { label : "개별검수" }
				}
			}
			,onButtonClick : function(btnId) {
	
				switch (btnId) {
					case "btnDnExcel":
						var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
						if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
						
						var totCnt = PAGE.GRID1.getRowsNum();
						if(totCnt <= 0){
							mvno.ui.notify("출력건수가 없습니다.");
						   return;
						}
						//alert(totCnt);
						if(totCnt>5000)
						{
							mvno.ui.notify("엑셀출력건수가 5,000건이상이면 시간(수분~수십분)이 걸릴수 있습니다.\n 잠시 기다려주시기 바랍니다.");
						   
						}
						var url = "/pps/hdofc/imgchkmgmt/CustImgChkMgmtListExcel.json";
						var searchData =  PAGE.FORM1.getFormData(true);
	
					  	mvno.cmn.download(url+"?menuId=PPS1940001",true,{postData:searchData});
						break;
						
					case "btnReg":
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.setFormData();
						PAGE.FORM2.clearChanged();
						
						setCurrentDate(PAGE.FORM2, "startDt");

						mvno.ui.popupWindowById("FORM2", "검수등록", 600, 200, {
			                onClose: function(win) {
			                	if (! PAGE.FORM2.isChanged()) return true;
			                	mvno.ui.confirm("CCMN0005", function(){
			                		win.closeForce();
			                	});
			                }
		                });	
						
						break;
						
					case "btnMod":
						var form = PAGE.FORM1.getFormData(true);
						
						form.pageCode = "ALL";
						
						mvno.cmn.ajax(ROOT + "/pps/hdofc/imgchkmgmt/CustImgChkMgmtList.json", form, function(dataResult) {
							
							if(dataResult.result.data.rows.length > 0){
								var ret = dataResult.result.data.rows[0];
								
								mvno.ui.createForm("FORM3");
								PAGE.FORM3.clearChanged();
								
								PAGE.FORM3.enableItem("btnImgBefore");
								PAGE.FORM3.enableItem("btnImgNext");
								
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0134"}, PAGE.FORM3, "chkStatus");
								
								PAGE.FORM3.setFormData(ret);
								
								PAGE.FORM3.setItemValue("chkStatus", ret.chkStatus);
								
								if(ret.chkManty != "" && ret.chkManty != null){
									var mantyArr = ret.chkManty.split("|");
									$.each(mantyArr, function(i, v){
										if(v != ""){
											PAGE.FORM3.setItemValue("chkManty" + v.substring(0, 1), v.substring(1, 2));	
										}
									});
								}else{
									var frmData = PAGE.FORM3.getFormData(true);
									$.each(frmData, function(k, v){
										if(k.indexOf("chkManty") != -1){
											PAGE.FORM3.setItemValue(k, "2");
										}
									});
								}
								
								mvno.ui.createGrid("GRID2");
								
								PAGE.GRID2.list(
										ROOT + "/pps/hdofc/custmgmt/imgFileInfoMgmtTabList.json",
										{
											contractNum : ret.contractNum
										}
								);
								
								PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById("contractNum" ) ,true);
								PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById("imgSeq" ) ,true);
								PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById("imgPath" ) ,true);
								PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById("encFlag" ) ,true);

								mvno.ui.popupWindowById("GROUP1", "고객검수정보", 900, 700, {
					                onClose: function(win) {
					                	if (! PAGE.FORM3.isChanged()) return true;
					                	mvno.ui.confirm("CCMN0005", function(){
					                		win.closeForce();
					                	});
					                }
				                });	
							}else{
								mvno.ui.notify("내역을 찾을 수 없습니다.");
								return;
							}
								
						});
						
						break;
						
					case "btnDel":
						var form = PAGE.GRID1.getSelectedRowData();
						
						if(form == null) {
	                     	mvno.ui.notify("선택된 데이터가 존재하지 않습니다");
	                     	return;
	                  	} 
						 
						form.pageCode = "ONE";
						
						mvno.cmn.ajax(ROOT + "/pps/hdofc/imgchkmgmt/CustImgChkMgmtList.json", form, function(dataResult) {
							
							if(dataResult.result.data.rows.length > 0){
								var ret = dataResult.result.data.rows[0];
								
								mvno.ui.createForm("FORM3");
								PAGE.FORM3.clearChanged();
								
								PAGE.FORM3.disableItem("btnImgBefore");
								PAGE.FORM3.disableItem("btnImgNext");
								
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0134"}, PAGE.FORM3, "chkStatus");
								
								PAGE.FORM3.setFormData(ret);
								
								PAGE.FORM3.setItemValue("chkStatus", ret.chkStatus);
								
								if(ret.chkManty != "" && ret.chkManty != null){
									var mantyArr = ret.chkManty.split("|");
									$.each(mantyArr, function(i, v){
										if(v != ""){
											PAGE.FORM3.setItemValue("chkManty" + v.substring(0, 1), v.substring(1, 2));	
										}
									});
								}else{
									var frmData = PAGE.FORM3.getFormData(true);
									$.each(frmData, function(k, v){
										if(k.indexOf("chkManty") != -1){
											PAGE.FORM3.setItemValue(k, "2");
										}
									});
								}
								
								mvno.ui.createGrid("GRID2");
								
								PAGE.GRID2.list(
										ROOT + "/pps/hdofc/custmgmt/imgFileInfoMgmtTabList.json",
										{
											contractNum : ret.contractNum
										}
								);
								
								PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById("contractNum" ) ,true);
								PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById("imgSeq" ) ,true);
								PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById("imgPath" ) ,true);
								PAGE.GRID2.setColumnHidden(PAGE.GRID2.getColIndexById("encFlag" ) ,true);

								mvno.ui.popupWindowById("GROUP1", "고객검수정보", 900, 700, {
					                onClose: function(win) {
					                	if (! PAGE.FORM3.isChanged()) return true;
					                	mvno.ui.confirm("CCMN0005", function(){
					                		win.closeForce();
					                	});
					                }
				                });	
							}else{
								mvno.ui.notify("내역을 찾을 수 없습니다.");
								return;
							}
								
						});
						
						break;
				}
			}
		},
		FORM2 : {
			title : "검수등록",
			items : [
				{type:"block",name:"BODY", list:[
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					,{type:"block", list:[
						{type:"hidden", name:"serviceType"}
						,{type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "개통일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : "",labelWidth : 80, offsetLeft:0}
					]}
				]}
			]
			,buttons : {
				center : {
		    		btnImgReg : { label : "등록" }, 
		    		btnImgClose : { label : "닫기" }
				}
			}
			,onKeyDown : function (inp, ev, name, value) {
				
			}
			,onValidateMore : function (data) {
				var form=this;
				
			}
			,onChange : function(name, value) {
				var form=this;
				
			}
			//,checkSelectedButtons : ["btnSave","btnMod"]
			,onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
					//대리점 검색
					case "btnImgReg":
						mvno.ui.confirm("등록하시겠습니까?", function(){
							
							var form = PAGE.FORM2.getFormData(true);
							
							var param = {
								eventCd : "NAC",
								startDt : form.startDt,
								endDt : form.startDt,
								option : ""
							};
							
							mvno.cmn.ajax(ROOT + "/pps/hdofc/imgchkmgmt/PpsCustImgChkRegProc.json", param, function(dataResult) {
								var ret = dataResult.result;
								
								alert(ret.oRetMsg);
								
								if(ret.oRetCd == "0000"){
									mvno.ui.closeWindowById("FORM2", true);
									PAGE.GRID1.refresh();
								}
									
							});
						});
						
						break;
						
					case "btnImgClose":
						mvno.ui.closeWindowById("FORM2", true);
		                break;
						
				}
			}
		},
		FORM3 : {
			title : "고객검수정보",
			items : [
				{type:"block",name:"BODY", list:[
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					,{type: "fieldset", label: "고객정보", name:"customerInfo", inputWidth:380, offsetLeft:8, list:[
   						,{type:"input", label:"고객번호", name:"customerId", labelWidth:120, width:200, readonly:true}
						,{type:"input", label:"계약번호", name:"contractNum", labelWidth:120, width:200, readonly:true}
						,{type:"input", label:"전화번호", name:"subscriberNo", labelWidth:120, width:200, readonly:true}
						,{type:"input", label:"고객유형", name:"customerTypeNm", labelWidth:120, width:200, readonly:true}
						,{type:"input", label:"고객명", name:"subLinkName", labelWidth:120, width:200, readonly:true}
						,{type:"input", label:"개통구분", name:"mnpIndCdNm", labelWidth:120, width:200, readonly:true}
						,{type:"input", label:"개통일", name:"lstComActvDate", labelWidth:120, width:200, readonly:true}
						,{type:"input", label:"신분증구분", name:"custIdntNoIndCdNm", labelWidth:120, width:200, readonly:true}
						,{type:"input", label:"신분증번호", name:"userSsn", labelWidth:120, width:200, readonly:true}
						,{type:"input", label:"개통대리점", name:"agentNm", labelWidth:120, width:200, readonly:true}
						,{type:"input", label:"판매점", name:"agentSaleNm", labelWidth:120, width:200, readonly:true}
						,{type:"input", label:"서류등록", name:"imgCnt", labelWidth:120, width:200, readonly:true}
						,{type:"input", label:"체류기간", name:"stayExpirDt", labelWidth:120, width:200, readonly:true}
   					]}
					,{type:"newcolumn", offset:10}
					,{type: "fieldset", label: "검수구분", name:"imgChkInfo", inputWidth:400, list:[
   						{type:"label", label:"개통서류", position:"label-right", list:[
							{type:"settings", labelWidth:60, offsetLeft:0, position:"label-right"}
							,{type:"radio", name:"chkMantyD", value:"0", label:"검수"}
							,{type:"newcolumn"}
							,{type:"radio", name:"chkMantyD", value:"1", label:"미비"}
							,{type:"newcolumn"}
							,{type:"radio", name:"chkMantyD", value:"2", label:"없음", checked:true}
						]}
   						,{type:"label", label:"신분증", position:"label-right", list:[
       						{type:"settings", labelWidth:60, offsetLeft:0, position:"label-right"}
   							,{type:"radio", name:"chkMantyI", value:"0", label:"검수"}
   							,{type:"newcolumn"}
   							,{type:"radio", name:"chkMantyI", value:"1", label:"미비"}
   							,{type:"newcolumn"}
   							,{type:"radio", name:"chkMantyI", value:"2", label:"없음", checked:true}
   						]}
   						,{type:"label", label:"서명", position:"label-right", list:[
      						{type:"settings", labelWidth:60, offsetLeft:0, position:"label-right"}
  							,{type:"radio", name:"chkMantyS", value:"0", label:"검수"}
  							,{type:"newcolumn"}
  							,{type:"radio", name:"chkMantyS", value:"1", label:"미비"}
  							,{type:"newcolumn"}
  							,{type:"radio", name:"chkMantyS", value:"2", label:"없음", checked:true}
  						]}
   						,{type:"label", label:"기타", position:"label-right", list:[
       						{type:"settings", labelWidth:60, offsetLeft:0, position:"label-right"}
   							,{type:"radio", name:"chkMantyE", value:"0", label:"검수"}
   							,{type:"newcolumn"}
   							,{type:"radio", name:"chkMantyE", value:"1", label:"미비"}
   							,{type:"newcolumn"}
   							,{type:"radio", name:"chkMantyE", value:"2", label:"없음", checked:true}
   						]}
   						,{type:"label", label:"검수상태", labelWidth:64, position:"label-right", list:[
       						{type:"settings", labelWidth:70, offsetLeft:0, position:"label-right"}
   							,{type : "select",name : "chkStatus",width:167, required: true}
   							,{type:"newcolumn"}
   							,{type:"button", value: "전체검수", name:"btnAllChk", width:60}
   						]}
   						,{type:"input", label:"대리점메모", name:"agentMemo", labelWidth:60, width:294, offsetLeft:0}
   					]}
					,{type: "fieldset", label: "업무메모", name:"imgChkInfo2", inputWidth:380, list:[
   						,{type:"input",  rows:7, width:355, name:"memo1Str", readonly:true }
						,{type:"input", label:"메모추가", name:"memo", labelWidth:50, width:301, offsetLeft:4}
   					]}
					,{type:"label", label:"", position:"label-right", list:[
   						{type:"settings", labelWidth:0, offsetLeft:0, position:"label-right"}
   						,{type:"button", value: "저장", name:"btnImgChg", width:260}
					]}
					,{type:"label", label:"", position:"label-right", labelWidth:0, list:[
   						,{type:"button", value: "&larr;이전", name:"btnImgBefore", width:117}
   						,{type:"newcolumn"}
   						,{type:"button", value: "다음&rarr;", name:"btnImgNext", width:117}
					]}
					,{type : "hidden",label : "seq",name : "seq",width:100, value:''}
				]}
			]
			,buttons : {
				
			}
			,onKeyDown : function (inp, ev, name, value) {
				
			}
			,onValidateMore : function (data) {
				var form=this;
				
			}
			,onChange : function(name, value) {
				var form=this;
				
			}
			//,checkSelectedButtons : ["btnSave","btnMod"]
			,onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
					//대리점 검색
					case "btnImgChg":
						
						mvno.ui.confirm("수정하시겠습니까?", function(){
							
							var frmData = PAGE.FORM3.getFormData(true);
							
							//frmData.chkManty = "";
							var chkManty = "";
							$.each(frmData, function(k, v){
								if(k.indexOf("chkManty") != -1){
									if(k != "chkManty"){
										chkManty = chkManty + k.replace("chkManty", "") + v;
									}
								}
							});
							
							frmData.chkManty = chkManty;
							
							mvno.cmn.ajax(ROOT + "/pps/hdofc/imgchkmgmt/PpsCustImgChkChgProc.json", frmData, function(dataResult) {
								var ret = dataResult.result;
								
								alert(ret.oRetMsg);
								
								if(ret.oRetCd == "0000"){
									//mvno.ui.closeWindowById("FORM3", true);
									PAGE.GRID1.refresh();
								}
									
							});
						});
						
						break;
						
					case "btnAllChk":
						var frmData = PAGE.FORM3.getFormData(true);
						$.each(frmData, function(k, v){
							if(k.indexOf("chkManty") != -1){
								PAGE.FORM3.setItemValue(k, "0");
							}
						});
						
						break;
						
					case "btnImgNext":
						var form = PAGE.FORM1.getFormData(true);
						
						form.pageCode = "NEXT";
						form.seq = PAGE.FORM3.getItemValue("seq");
						
						mvno.cmn.ajax(ROOT + "/pps/hdofc/imgchkmgmt/CustImgChkMgmtList.json", form, function(dataResult) {
							
							if(dataResult.result.data.rows.length > 0){
								var ret = dataResult.result.data.rows[0];
								
								//mvno.ui.createForm("FORM3");
								PAGE.FORM3.clearChanged();
								
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0134"}, PAGE.FORM3, "chkStatus");
								
								PAGE.FORM3.setFormData(ret);
								
								PAGE.FORM3.setItemValue("chkStatus", ret.chkStatus);
								
								if(ret.chkManty != "" && ret.chkManty != null){
									var mantyArr = ret.chkManty.split("|");
									$.each(mantyArr, function(i, v){
										if(v != ""){
											PAGE.FORM3.setItemValue("chkManty" + v.substring(0, 1), v.substring(1, 2));	
										}
									});
								}else{
									var frmData = PAGE.FORM3.getFormData(true);
									$.each(frmData, function(k, v){
										if(k.indexOf("chkManty") != -1){
											PAGE.FORM3.setItemValue(k, "2");
										}
									});
								}
								
								PAGE.GRID2.list(
										ROOT + "/pps/hdofc/custmgmt/imgFileInfoMgmtTabList.json",
										{
											contractNum : ret.contractNum
										}
								);
								
							}else{
								mvno.ui.notify("내역을 찾을 수 없습니다.");
								return;
							}
								
						});
						
						break;
						
					case "btnImgBefore":
						var form = PAGE.FORM1.getFormData(true);
						
						form.pageCode = "BEFORE";
						form.seq = PAGE.FORM3.getItemValue("seq");
						
						mvno.cmn.ajax(ROOT + "/pps/hdofc/imgchkmgmt/CustImgChkMgmtList.json", form, function(dataResult) {
							
							if(dataResult.result.data.rows.length > 0){
								var ret = dataResult.result.data.rows[0];
								
								//mvno.ui.createForm("FORM3");
								PAGE.FORM3.clearChanged();
								
								mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0134"}, PAGE.FORM3, "chkStatus");
								
								PAGE.FORM3.setFormData(ret);
								
								PAGE.FORM3.setItemValue("chkStatus", ret.chkStatus);
								
								if(ret.chkManty != "" && ret.chkManty != null){
									var mantyArr = ret.chkManty.split("|");
									$.each(mantyArr, function(i, v){
										if(v != ""){
											PAGE.FORM3.setItemValue("chkManty" + v.substring(0, 1), v.substring(1, 2));	
										}
									});
								}else{
									var frmData = PAGE.FORM3.getFormData(true);
									$.each(frmData, function(k, v){
										if(k.indexOf("chkManty") != -1){
											PAGE.FORM3.setItemValue(k, "2");
										}
									});
								}
								
								PAGE.GRID2.list(
										ROOT + "/pps/hdofc/custmgmt/imgFileInfoMgmtTabList.json",
										{
											contractNum : ret.contractNum
										}
								);
								
							}else{
								mvno.ui.notify("내역을 찾을 수 없습니다.");
								return;
							}
								
						});
						
						break;
						
					case "btnImgClose":
						mvno.ui.closeWindowById("FORM3", true);
		                break;
						
				}
			}
		}
		,GRID2 : {

			css : {
				height : "300px"
			},
			header: "서식지종류,파일명,등록일자,처리자,메모,계약번호히든,일련번호히든,경로히든,암호화여부히든",
		    columnIds: "imgGubunNm,imgFileStr,recDt,recIdNm,memo,contractNum,imgSeq,imgPath,encFlag",
		    widths : "120,150,120,100,*,0,0,0,0",
		    colAlign: "center,left,center,center,left,center,center,left,center",
		    colTypes: "ro,link,roXd,ro,ro,ro,ro,ro,ro",
		    colSorting: "str,str,str,str,str,str,str,str,str",
		    paging : true,
			onRowSelect : function(rowId, celInd) {
				
			},
	        onRowDblClicked : function(rowId, celInd) {
	            // 수정버튼 누른것과 같이 처리?
	            this.callEvent('onButtonClick', ['btnMod']);
	        },				
			onButtonClick : function(btnId, selectedData) {

				switch (btnId) {

					case "btn01":
						// this.refresh();
						break;
				}
			}
		}
					
	};

	var PAGE = {

		title : "${breadCrumb.title}",
		breadcrumb : "${breadCrumb.breadCrumb}", 
		buttonAuth: ${buttonAuth.jsonString},
		onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			setCurrentDate(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "endDt");
			
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("seq" ) ,true);
			
			var selType ="popPin";
			mvno.cmn.ajax4lov(
					ROOT + "/pps/hdofc/cardmgmt/ppsAgentInfoDataList.json"
					, {
						selectType : selType
						}
					 , PAGE.FORM1, "searchAgentId");
			
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0134"}, PAGE.FORM1, "searchChkStatus");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0136"}, PAGE.FORM1, "searchType");
		}
	};
		
</script>