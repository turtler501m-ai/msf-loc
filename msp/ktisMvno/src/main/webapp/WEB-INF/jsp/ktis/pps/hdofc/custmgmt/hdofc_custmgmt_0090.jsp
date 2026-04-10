<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : hdofc_custmgmt_0090.jsp
   * @Description : 선불 서식지관리
   * @Modification Information
   *
   *   수정일         수정자                   수정내용
   *  -------    --------    ---------------------------
   *  2009.02.01            최초 생성
   *
   * author 실행환경 개발팀
   * since 2009.02.01
   *
   * Copyright (C) 2009 by MOPAS  All right reserved.
   */
   %>

	
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>
<div id="FORM18" class="section-search"></div>
<div id="FORM19" class="section-search"></div>   
<div id="FORM21" ></div> 	
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

	// 스캔관련
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

	function alignCenterStatus()
	{
		var w = screen.width / 2;
		var h = screen.height / 2;
	
		var LeftPosition = (screen.width) ? (screen.width-w)/2 : 0;
		var TopPosition = (screen.height) ? (screen.height-h)/2 : 0;
	
		var status = "width=" + w + ",height=" + h + ",top=" + TopPosition + ",left=" + LeftPosition;
		
		return status;
	}

	function setCurrentMonthFirstDay(form, name)
	{
		var today = new Date();

		var mm = today.getMonth(); //January is 0!
		var yyyy = today.getFullYear();

		form.setItemValue(name, new Date(yyyy,mm,01));
	}

	function setCurrentDate(form, name)
	{
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
	
     function goDeleteFileData(seq,pSize,pIdx){
    	 
    	 var startSize = ((pIdx-1)*pSize);
 		var endSize = pIdx*pSize;
 		var rowId = "";
 		
 		for (var i=startSize; i<endSize; i++) {
 			var imgSeqStr = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("imgSeq")).getValue();
 			
 			if(imgSeqStr == seq){
 				rowId = i;
 				break;
 			}
 		 }
 		
 		var grid1 = PAGE.GRID1;
 			
        var filePath="";
		var contractNum="";
		var opCode ="DEL";
		var status="D";
		var msg = "파일을 삭제하시겠습니까?";
		
		filePath = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("imgPath" ) ).getValue();
 		contractNum = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("contractNum" ) ).getValue();
 		
 		 var searchData =  PAGE.FORM1.getFormData(true);
 		
 		mvno.cmn.ajax4confirm(msg, ROOT + "/pps/filemgmt/ppsDeleteFile.json", {
			opCode: opCode,
			imgSeq : seq,
			contractNum : contractNum,
			status: status,
			imgPath: filePath,
			contractNum: contractNum
		}, function(results) {
			var result = results.result;
			var retCode = result.oRetCode;
			var retMsg = result.oRetMsg;
			
			mvno.ui.notify(retMsg);
			if(retCode == "0000"){
				grid1.clearAll();
				PAGE.GRID1.list(ROOT + "/pps/hdofc/custmgmt/imgFileInfoMgmtList.json", searchData);
				
				
			}
			
		});
 		
 		
     }

	function userPaperImageView(contractNum,pNum,pSize,pIdx){
		
 		var startSize = ((pIdx-1)*pSize);
		var endSize = pIdx*pSize;
		var rowId = "";
		
		for (var i=startSize; i<endSize; i++) {
			var imgSeqStr = PAGE.GRID1.cells(PAGE.GRID1.getRowId(i), PAGE.GRID1.getColIndexById("imgSeq")).getValue();
			
			if(pNum == imgSeqStr){
				rowId = i;
				break;
			} 
		 }
		var pImageStr = "";
		var grid1 = PAGE.GRID1;
		
		var filePath="";
		var encodeYn="";
		filePath = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("imgPath" ) ).getValue();
		encodeYn = grid1.cells(grid1.getRowId(rowId),grid1.getColIndexById("encFlag" ) ).getValue();
		
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
		/* 			
					document.viewForm.FILE_PATH.value = filePath;
					document.viewForm.ENCODE_YN.value = encodeYn;
					
					var popupTitle = "이미지검색";
					
					var popupUrl = "https://scn.ktmmobile.com/webscan/ui/callImageViewer.jsp"; // 실서버 
					//var popupUrl = "http://scndev.ktmmobile.com/webscan/ui/callImageViewer.jsp"; // 개발서버  
				
					var status = alignCenterStatus();
					status += ", scrollbars=no";
					
					window.opener=null;
					
					var browserPopup = window.open("", popupTitle, status);
				
					document.viewForm.target = popupTitle;
					document.viewForm.action = popupUrl;
					document.viewForm.method = "post";
					document.viewForm.submit();
					 */
			
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

			// ----------------------------------------
	FORM1 : {
		items : [ 	
		          	 
					 {type:"block", list:[
						 {type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "startDt",label : "등록일자",value : "",enableTime : false,calendarPosition : "bottom",inputWidth : 100,value : ""},
						 {type : "newcolumn", offset:3},
						 {type:"block", list:[
							{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
							,{type: "label", label: "~"},
	                   	 ]},	 
	                     {type : "newcolumn", offset:3}, 
						 {type : "calendar",dateFormat : "%Y-%m-%d",serverDateFormat: "%Y%m%d",name : "endDt", value : "",enableTime : false,	calendarPosition : "right",inputWidth : 100,value : ""},
						 {type : "newcolumn",offset:5}, 
						 {type : "select",label : "이미지구분",name : "searchImgGubun",width:167}, // Kim Woong 주석지우지마세요
						 {type : "newcolumn", offset:5}, 
						 {type : "select",label : "가입자상태",name : "searchSubStatus",width:100}
					 ]},// 1row
					 {type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"right", labelWidth:60, blockOffset:0},
						{type : "select",label:"검색",name : "searchType",width:100,userdata: { lov: "PPS0059"} },	
	                   	{type : "newcolumn", offset:3},
	                   	{type:"block", list:[
 									{type:"settings", position:"label-left", labelAlign:"left", labelWidth:5, labelHeight:15, blockOffset:0}	
 									,{type: "label", label: ":"}
 			                   	 ]},	 
 			                    {type : "newcolumn", offset:3},
 			                   	{type : "input",name : "searchNm", width:100,maxlength:20}
  			                ,{type : "newcolumn"} 
						
					 ]},// 2row					
					 {type : "newcolumn", offset:10},
					 {type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
					 {type : "button",name : "btnSearch",value : "조회", className:"btn-search2"},
					 
					 
				],

		onButtonClick : function(btnId) {

			var form = this;

			switch (btnId) {
			
				case "btnSearch":
					var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
					PAGE.FORM1.setItemValue("btnCount1", btnCount2)
					if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
					
					PAGE.GRID1.list(ROOT + "/pps/hdofc/custmgmt/imgFileInfoMgmtList.json", form, {
						callback : function () {
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						}
					});
					
					var gridData = PAGE.GRID1;
					gridData["whereQuery"] = form;
					break;

			}

		}		
		,onValidateMore : function(data){

			if(data.startDt > data.endDt){
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
			if(( data.searchType == null || data.searchType == '' ) && (data.searchNm != null && data.searchNm != '' ))
			{
				 PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				 PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				 this.pushError("searchType", "검색조건", "을 선택해주세요");							
			}
			if(data.searchType=="subscriber_no")
			{
				if(data.searchNm.match(/^\d+$/ig) == null)
				{
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					this.pushError("searchNm","CTN","숫자만 입력하세요");
				}
			}
			else if(data.searchType=="contractNum")
			{
				if(data.searchNm.match(/^\d+$/ig) == null)
				{
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					this.pushError("searchNm","계약번호","숫자만 입력하세요");
				}
			}
			
		}

		
	}
	
	// FORM end..

	,GRID1 : {

		css : {
			height : "550px"
		},
		title : "조회결과",
		header : "계약번호,가입자상태,서식지종류,파일명,개통일자,등록일자,메모,처리자,삭제,계약번호히든,일련번호히든,경로히든,암호화여부히든",
		columnIds : "contractNumStr,subStatusNm,imgGubunNm,imgFileStr,enterDate,recDt,memo,recIdNm,delBtn,contractNum,imgSeq,imgPath,encFlag",
		widths : "100,100,150,150,120,120,150,100,100,0,0,0,0", //총 1500
		colAlign : "center,center,center,center,center,center,center,center,center,center,center,left,center",
		colTypes : "link,ro,ro,link,roXd,roXd,ro,ro,link,ro,ro,ro,ro",
		colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str",
		paging : true,
		pageSize :15,
		pagingStyle :2,
		buttons : {
			hright : {
				btnDnExcel : { label : "엑셀다운로드" }
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
					
					var url = "/pps/hdofc/custmgmt/imgFileInfoMgmtListExcel.json";
					var searchData =  PAGE.FORM1.getFormData(true);
					 //console.log("searchData",searchData);
										
					 mvno.cmn.download(url+"?menuId=PPS1100009",true,{postData:searchData});
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

			//setCurrentMonthFirstDay(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "startDt");
			setCurrentDate(PAGE.FORM1, "endDt");

			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("contractNum" ) ,true);
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("imgSeq" ) ,true);
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("imgPath" ) ,true);
			PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("encFlag" ) ,true);
			var selType ="popPin";			
			
			
			//mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0055"}, PAGE.FORM1, "searchSubStatus");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0070"}, PAGE.FORM1, "searchSubStatus");
			mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS0063"}, PAGE.FORM1, "searchImgGubun");
			

			

		}

	};
		
</script>