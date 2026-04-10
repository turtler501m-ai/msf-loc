<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>
<div id="FORM4" class="section-box"></div> 

<!-- Script -->
<script type="text/javascript">
	var rwdSeq = "";

	var orgId = "${orgnInfo.orgnId}";
	var orgNm = "${orgnInfo.orgnNm}";
	var userId = "${loginInfo.userId}";
	var userName = "${loginInfo.userName}";
	var typeCd = "${orgnInfo.typeCd}";
	var orgType = "";
	if(typeCd == "10") {
		orgType = "K";
	}else if(typeCd == "20") {
		orgType = "D";
	}else if(typeCd == "30") {
		orgType = "S";
	}
	var blackMakingYn = "Y";					// 블랙마킹사용권한
	var blackMakingFlag = "N";					// 블랙마킹해제권한 (마스킹권한여부에 따라 변화)
	var maskingYn = "${maskingYn}";				// 마스킹권한
	if(maskingYn == "Y") {
		blackMakingFlag = "Y";
	}
	var agentVersion = "${agentVersion}";		// 스캔버전
	var serverUrl = "${serverUrl}";				// 서버환경 (로컬 : L, 개발 : D, 운영 : R)
	var scanSearchUrl = "${scanSearchUrl}";		// 스캔호출 url
	var scanDownloadUrl = "${scanDownloadUrl}";	// 스캔파일 download url
	
	// 변경신청관리에서는 삭제, 검증결과변경 하지 않음.
	var modifyFlag = "N";
	
	var fromDt = new Date(new Date().setDate(new Date().getDate() - 6)).format("yyyymmdd");
	var toDt = new Date().format("yyyymmdd");
	
	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					  {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchFromDt", label:"가입일자", value:fromDt, calendarPosition: "bottom", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchToDt", label:"~", value:toDt, calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type:"newcolumn"}
					, {type:"select", label:"가입상태", name:"searchRsltCd", width:130, offsetLeft:20}
					, {type:"newcolumn"}
					, {type:"select", label:"보상서비스명", name: "searchRwdProdCd", width:200, labelWidth:80, offsetLeft:20}
				]}
				, {type:"block", list:[
					  {type:"select", label:"검색구분", name: "searchCd", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchVal", width:115, offsetLeft:0, maxLength:60, disabled:true}
					, {type:"newcolumn"}
					, {type:"select", label:"가입경로", name:"searchChnCd", width:130, offsetLeft:20}
				]}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"} 
			]
	
			, onValidateMore : function(data) {
				if (data.searchFromDt > data.searchToDt) {
					this.pushError("searchFromDt", "조회기간", "시작일자가 종료일자보다 클 수 없습니다.");
				}
				if(data.searchFromDt != "" && data.searchToDt == ""){
					this.pushError("searchFromDt", "조회기간", "조회기간을 입력하세요.");
				}
				if(data.searchToDt != "" && data.searchFromDt == ""){
					this.pushError("searchToDt", "조회기간", "조회기간을 입력하세요.");
				}				
				if( data.searchCd != "" && data.searchCd.trim() == ""){
					this.pushError("searchCd", "검색어", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchCd", ""); // 검색어 초기화
				}
			}
			, onChange: function (name, value) {
				var form = this;
				
				switch(name) {
					case "searchCd":
						form.setItemValue("searchVal", "");
						
						if(mvno.cmn.isEmpty(value)) {
							form.setItemValue("searchFromDt", fromDt);
							form.setItemValue("searchToDt", toDt);
							
							form.enableItem("searchFromDt");
							form.enableItem("searchToDt");
							form.disableItem("searchVal");
						} else {
							form.setItemValue("searchFromDt", "");
							form.setItemValue("searchToDt", "");
							
							form.disableItem("searchFromDt");
							form.disableItem("searchToDt");
							form.enableItem("searchVal");
						}
						
						break;
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					
					case "btnSearch":
						
						if( !mvno.cmn.isEmpty(form.getItemValue("searchCd")) && mvno.cmn.isEmpty(form.getItemValue("searchVal")) ) {
							mvno.ui.alert("검색 할 내용이 없습니다.");
							return;
						}
						
						PAGE.GRID1.list(ROOT + "/rwd/rwdMgmt/getMemberList.json", form);
						
						break;
				}
			}
		}
		// 그리드 시작
		, GRID1 : {
			css : {
				height : "540px"
			}
			, title : "보상서비스 가입자목록"
			, header : "전화번호,고객명,계약번호,개통대리점ID,개통대리점,보상서비스명,가입일자,종료일자,가입상태,가입경로,구매유형,구매가격,단말모델ID,단말모델명,IMEI,IMEI2,단말일련번호,약정기간,해지사유,reqBuyType,scanId,resNo" //22
			, columnIds : "subscriberNo,subLinkName,contractNum,openAgntCd,openAgntNm,rwdProdNm,strtDttm,endDttm,rwdStatCd,chnNm,reqBuyTypeNm,buyPric,modelId,modelNm,imei,imeiTwo,intmSrlNo,rwdPrd,canRsltNm,reqBuyType,scanId,resNo" //22
			, widths : "120,100,100,100,130,200,130,130,80,120,100,100,100,100,120,120,120,100,100,150,100,100"
			, colAlign : "center,center,center,center,Left,Left,center,center,center,center,center,Right,center,center,center,center,center,center,center,Left,center,center"
			, colTypes : "roXp,ro,ro,ro,ro,ro,roXdt,roXdt,ro,ro,ro,ron,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,int,str,str,str,str,str,str,str,str,str,str"
			, hiddenColumns : [19,20,21]
			, paging: true
			, showPagingAreaOnInit: true
			, pageSize:20
			, buttons : {
				hright : {
					btnExcel:{ label:"엑셀다운로드"}
				}
				, left : {
					btnPaper : { label : "서식지보기" },
					btnFile  : { label : "구매영수증 파일관리" }
				}
			}
			, onButtonClick : function(btnId) {
				var grid = this;
				var form = this;
				switch (btnId) {
	
					case "btnFile" :
	                	fileLimitCnt = 1;
	
	                    if(PAGE.GRID1.getSelectedRowData() == null)
	                    {
	                        mvno.ui.alert("ECMN0002");
	                        break;
	                    }                        	
	                    mvno.ui.createForm("FORM4");
	                    PAGE.FORM4.clear();
	                    
	                    var data = PAGE.GRID1.getSelectedRowData();
	                    PAGE.FORM4.setFormData(data);
	                    
                        var uploader = PAGE.FORM4.getUploader("file_upload");
                        uploader.revive();
                        
	                    mvno.cmn.ajax(ROOT + "/rwd/rwdMgmt/rwdFile.json", data, function(resultData) {
	                    	var totCnt = resultData.result.fileTotalCnt;
	                    	if( parseInt(totCnt) > 0){
	                            PAGE.FORM4.setUserData("file_upload","limitCount",fileLimitCnt-parseInt(totCnt));
	                            PAGE.FORM4.setUserData("file_upload","limitsize",1000);
// 	                            PAGE.FORM4.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
	                        }
	                    	
	                    	if(parseInt(totCnt) == fileLimitCnt){
	                            PAGE.FORM4.hideItem("file_upload");
	                    	}
	                    	
	                        if(!mvno.cmn.isEmpty(resultData.result.fileId)){
	                        	PAGE.FORM4.setItemValue("fileName1", resultData.result.fileId + "." + resultData.result.ext);
	                        	PAGE.FORM4.setItemValue("rwdSeq", resultData.result.rwdSeq); 
	                        	PAGE.FORM4.enableItem("fileDown1");
	                            //PAGE.FORM4.enableItem("fileDel1");
	                        } else {
	                        	   PAGE.FORM4.setItemValue("fileId1", "");
	                        	   PAGE.FORM4.setItemValue("fileName1", "");
	                        	   PAGE.FORM4.disableItem("fileDown1");
	                        	   //PAGE.FORM4.disableItem("fileDel1");
	                        	   PAGE.FORM4.showItem("file_upload");
	                        }
	                    });
	                    
	                    mvno.ui.popupWindowById("FORM4", "파일관리", 600, 270, {
	                 	    onClose2: function(win) {
	                 	        uploader.reset();
	                 	    }
	                    });
	                    
	                    break;
                    
					case "btnExcel":
						
						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}
						
						var searchData =  PAGE.FORM1.getFormData(true);
						 
						mvno.cmn.download(ROOT + "/rwd/rwdMgmt/getRwdMemberListByExcel.json?menuId=MSP9100102", true, {postData:searchData}); 
						
						break;
					
					case "btnPaper":
						
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						
						if(mvno.cmn.isEmpty(grid.getSelectedRowData().scanId)) {
							mvno.ui.alert("등록된 서식지가 없습니다.");
							return;
						}
						
						appFormView(grid.getSelectedRowData().scanId, grid.getSelectedRowData().resNo);
						
						break;
				}
			}
		}
        //파일관리
        , FORM4: {
            items: [
     			{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80},
    			
	    			{type: "block", list: [
	    			{type: 'input', name: 'contractNum', label: '계약번호',  width: 100, labelWidth:80, value: '' ,readonly:true, offsetLeft:43, disabled:true},
	    			{type: "newcolumn"},
	    			{type: 'input', name: 'subscriberNo', label: '전화번호',  width: 120, labelWidth:80, value: '',readonly:true, offsetLeft:47, disabled:true}]},
	    			
	    			{type: 'fieldset', label: '구매영수증 파일 첨부',  inputWidth:500, offsetLeft:10, offsetTop:40, list:[
	    			{type: "block", list: [
	    			{type: 'input', name: 'fileName1', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
	    			{type: 'hidden', name: 'rwdSeq'},
	    			{type: 'hidden', name: 'fileId1'},
	    			{type: "newcolumn"},			
	    			{type: "button", name: 'fileDown1', value:"다운로드"},
	    			{type: "newcolumn"},			
	    			//{type: "button", name: 'fileDel1', value:"삭제"},
	    			]},
	    			{type: "block", list: [
	    				{type: "upload", label:"파일업로드" ,name: "file_upload",width: 384 ,inputWidth: 330 ,url: "/rwd/rwdMgmt/fileUpLoad.do" ,autoStart: true, offsetLeft:20, userdata: { limitCount : 1, limitsize: 1000, delUrl:"/rwd/rwdMgmt/deleteFile2.json"} }
    				]}
    			]}
    		],
             buttons: {
                 center: {
                     //btnSave: { label: "확인" },
                     btnClose: { label: "닫기" }
                 }
             },
             onChange : function (name, value){
                 var form = this;
             },
             onButtonClick : function(btnId) {

                 var form = this; // 혼란방지용으로 미리 설정 (권고)
                 var fileLimitCnt = 1;
                 
                 var strRwdSeq = PAGE.GRID1.getSelectedRowData().rwdSeq;
                 PAGE.FORM4.setItemValue("rwdSeq", strRwdSeq);
                 
                 switch (btnId) {
                 	/*
                    case "btnSave":
						mvno.cmn.ajax(ROOT + "/rwd/rwdMgmt/insertImageFile.json", form, function(result) {
                        	mvno.ui.closeWindowById(form, true);  
                            mvno.ui.notify("NCMN0015");
                            PAGE.GRID1.refresh();
                            PAGE.FORM4.clear();
                        });
                        break;
                    */
					case "btnClose" :
                         mvno.ui.closeWindowById(form);
                         break;
                    case "fileDown1" :
                     	 mvno.cmn.download('/rwd/rwdMgmt/downFile.json?rwdSeq=' + PAGE.FORM4.getItemValue("rwdSeq"));
                          break;
                    /*
                    case "fileDel1" :
                    	mvno.cmn.ajax(ROOT + "/rwd/rwdMgmt/deleteFile.json",  {rwdSeq:PAGE.FORM4.getItemValue("rwdSeq")} , function(resultData) {
                        	mvno.ui.notify("NCMN0014");
                            PAGE.FORM4.setItemValue("fileName1", "");
                            PAGE.FORM4.disableItem("fileDown1");
                            PAGE.FORM4.disableItem("fileDel1");
                             
                            PAGE.FORM4.setUserData("file_upload","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
                            PAGE.FORM4.setUserData("file_upload","limitsize",1000);
                            PAGE.FORM4.setUserData("file_upload","delUrl","/rwd/rwdMgmt/deleteFile.json");
                            PAGE.FORM4.showItem("file_upload");
                         });
                         break;
                     */
                 }
             }
         }
	}
	
	function appFormView(scanId, resNo){
		
		var data = null;
		
		data = "AGENT_VERSION="+agentVersion+
			"&SERVER_URL="+serverUrl+
			"&VIEW_TYPE=MSPVIEW"+
			"&USE_DEL=N"+
			"&USE_STAT=N"+
			"&USE_BM="+blackMakingYn+
			"&USE_DEL_BM="+blackMakingFlag+
			"&RGST_PRSN_ID="+userId+
			"&RGST_PRSN_NM="+userName+
			"&ORG_TYPE="+orgType+
			"&ORG_ID="+orgId+
			"&ORG_NM="+orgNm+
			"&RES_NO="+resNo+
			"&PARENT_SCAN_ID="+scanId+
			"&DBMSType=S"
			;
		
		callViewer(data);
		
	}
	
	function callViewer(data){
		var url = scanSearchUrl + "?" + encodeURIComponent(data);
		
		// 타임아웃 설정 - 10초이내 응답없으면 서비스 실행상태가 아님.
		var timeOutTimer = window.setTimeout(function (){
			mvno.ui.confirm("이미지 프로그램이 설치되지 않았거나 실행중이 아닙니다. 설치페이지로 이동 하시겠습니까?",function(){
				window.open(scanDownloadUrl, "", "width=750,height=650,resizable=no,scrollbars=yes,top=10,left=10");		// 설치페이지로 이동
			});
		}, 10000);
		
		$.ajax({
			type : "GET",
			url : url,
			crossDomain : true,
			dataType : "jsonp",
			success : function(args){
				window.clearTimeout(timeOutTimer);
				if(args.RESULT == "SUCCESS") {
					console.log("SUCCESS");
				} else if(args.RESULT == "ERROR_TYPE2") {
					mvno.ui.alert("버전이 다릅니다. 설치페이지로 이동해주세요.");
					window.open(scanDownloadUrl, "", "width=750,height=650,resizable=no,scrollbars=yes,top=10,left=10");		// 설치페이지로 이동
				} else {
					mvno.ui.alert(args.RESULT + " : " + args.RESULT_MSG);
				}
			}
		});
	}
	
	var PAGE = {
	      title : "${breadCrumb.title}",
	      breadcrumb : "${breadCrumb.breadCrumb}",
		  buttonAuth: ${buttonAuth.jsonString} ,
		  onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchCd");				    	// 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0265", etc1:"1"}, PAGE.FORM1, "searchRsltCd");	                // 보상서비스 가입상태
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0258", orderBy:"etc5"}, PAGE.FORM1, "searchRwdProdCd");	// 보상서비스 상품
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0249"}, PAGE.FORM1, "searchChnCd");					    // 가입경로구분
		}
	};
</script>