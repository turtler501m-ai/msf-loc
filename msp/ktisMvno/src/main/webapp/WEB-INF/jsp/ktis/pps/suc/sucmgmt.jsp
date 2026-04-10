<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="FORM1" class="section-search"></div>
<div id="FORM99" class="section-application"></div><!-- 신청서정보 -->

<!-- 조직 리스트 그리드 -->
<div id="GRID1"></div>

<!-- 탭바 선언 -->
<div id="TABBAR1"></div>

<!-- 가입자 정보 -->
<div id="TABBAR1_a1" style="display:none">
	<div id="FORM2" class="section-box"></div>
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
<script type = "text/javascript">
	<%@ include file = "suc.formitems" %>
	var isCntpShop = false;
	var cntpntShopId="";
	var cntpntShopNm="";
	var typeCd = '${orgnInfo.typeCd}';
	if (typeCd == '20' || typeCd == '30' ) {
		isCntpShop = true;
		if(typeCd == '20'){
			cntpntShopId = '${orgnInfo.orgnId}';
			cntpntShopNm = '${orgnInfo.orgnNm}';
		}else{
			isAgent = true;
			cntpntShopId = '${orgnInfo.hghrOrgnCd}';
			cntpntShopNm = '${orgnInfo.hghrOrgnNm}';
			pAgentCode = '${orgnInfo.orgnId}';
			pAgentName = '${orgnInfo.orgnNm}';
		}
	}

	var userId = '${loginInfo.userId}';
	var userName = '${loginInfo.userName}';
	var orgId = '${orgnInfo.orgnId}';
	var orgNm = '${orgnInfo.orgnNm}';

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
	
	var DHX = {
	// 검색 조건
		FORM1 : {
			items : [
				{type : "settings", position : "label-left", labelAlign : "left", labelWidth : 55, blockOffset : 0},
				{type : "block",
					list : [
							{type : "calendar",width : 100,label : "등록일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d", name : "sysRdateS", offsetLeft:10},
							{type : "newcolumn"},
							{type : "calendar",label : "~",name : "sysRdateE",labelAlign : "center",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100}
					]
				},
				{type : "block",
					list : [
						{type : "select",width : 120,offsetLeft : 20,label : "검색구분",name : "searchGbn",userdata : {lov : "PPS0057"}, offsetLeft:10},
						{type : "newcolumn"},
						{type : "input",width : 100,name : "searchName"},
						{type : "newcolumn"},
						{type : "select",width : 120,offsetLeft : 20,label : "진행상태",name : "sstate",userdata : {lov : "PPS0058"}},
						{type : "newcolumn"},
						{type : "input", label:"대리점", name: "cntpntShopId", width:100, readonly: isCntpShop ,value:cntpntShopId, offsetLeft:10},
						{type : "newcolumn"},
						{type : "button", value:"찾기", name:"btnOrgFind2", disabled: isCntpShop },
						{type : "newcolumn"},
						{type : "input", name:"cntpntShopNm", width:100, readonly: isCntpShop, value:cntpntShopNm}
					]
				},
				{type : "button",name : "btnSearch",value : "조회",className : "btn-search2"}
			],
			onButtonClick : function (btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch":
						if (!this.validate()) return;
						var url = ROOT + "/pps/sucMgmt/getSucListAjax.json";

						PAGE.GRID1.list(url, form, {
							callback : function () {
								PAGE.FORM2.clear();
							}
						});
						PAGE.TABBAR1.tabs("a1").setActive();
					break;
					case "btnOrgFind2":
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("cntpntShopId", result.orgnId);
							form.setItemValue("cntpntShopNm", result.orgnNm);
						});
					break;	
				}
			}
		},
		//리스트 - GRID1
		GRID1 : {
			css : {height : "275px"},
			title : "등록리스트",
			header : "대리점(ID),유심번호,고객명,휴대폰번호,등록일자,진행상태,여권이미지유무,맥이미지유무,자동충전금액,처리완료일,처리자,진행상태히든,암호화히든,contractNum,rcgSeq,remark",
			columnIds : "agentId,usimNo,frgnrNameMsk,mobileNoMsk,rdate,sstateName,fileOx,etcFileOx,rcgAutoAmt,endDt,endUsrNm,sstate,fileEncFlag,contractNum,rcgSeq,remark",
			widths : "156,80,100,100,156,156,95,95,100,80,60,0,0,0,0,0",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
			colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center",
			paging : true,
			multiCheckbox: true,
			checkSelectedButtons : ["btnDtl"],
			onRowDblClicked : function (rowId, celInd) {
			var grid = this;
			},
			
			onRowSelect : function (rowId, celInd) {
				var rowData = "";
				var Tid = PAGE.TABBAR1.getActiveTab()
					rowData = this.getRowData(rowId);
				if (PAGE.FORM2){
					
					PAGE.FORM2.setFormData(rowData);
					
					mvno.cmn.ajax(
							ROOT + "/pps/sucMgmt/getSucPathCheckAjax.json",
							{
								sellUsimKey : PAGE.FORM2.getItemValue("sellUsimKey"),
								fileMask : PAGE.FORM2.getItemValue("fileMask"),
								etcFileMask : PAGE.FORM2.getItemValue("etcFileMask"),
								pageSize : 10,
								pageIndex : 1
							},
							function(results) {
								var result = results.result;
						        var data = result.data;
						        PAGE.FORM2.setItemValue("pageIndex",data.pageIndex);
								PAGE.FORM2.setItemValue("pageSize",data.pageSize);
								PAGE.FORM2.setItemValue("pathStr",data.checkImgPath);
								PAGE.FORM2.setItemValue("etcPathStr",data.checkEtcImgPath);
								
							},
							null
							);	
				}
					
				PAGE.TABBAR1.callEvent('onSelect', [Tid, null, true]);
				// todo
			},
			buttons : {
				/* hright : {
					btnDnExcel : {label : "엑셀다운로드",auth:"A-SUC-DOWN"}
				}, */
				right : {
                	btnDel : { label : "삭제" }
                }
           },
           onButtonClick : function (btnId, selectedData) {
				var form = this;
	            if(btnId == "btnDel"){
	            	var ckData = form.getCheckedRowData();
					if(ckData == null || ckData ==''){
						mvno.ui.alert("ECMN0003");//선택한(체크된) 데이터가 없습니다.
						return;
					}else{
						var sData = {
								 'sellUsimKey'  : ''
						};
						sData.items = ckData;
						mvno.ui.confirm("CCMN0015", function(){
							 mvno.cmn.ajax4json(ROOT + "/pps/sucMgmt/deleteSellUsim.json", sData, function(result) {
								mvno.ui.notify("NCMN0001");
								PAGE.GRID1.refresh();
							});
						});
				
					}
	            }else if(btnId == "btnDnExcel"){
	            	if (PAGE.GRID1.getRowsNum() == 0) {
						mvno.ui.alert("데이터가 존재하지 않습니다.");
						return;
					}else {
						/* mvno.ui.alert("다운 스타트!!!");
						return; */
						var searchData =  PAGE.FORM1.getFormData(true);
					  	mvno.cmn.download(ROOT + "/pps/sucMgmt/getExcelList.json?menuId=PPS1100008", {postData:searchData});
						return;
					}
				}
           }
           
		}, // 리스트 - GRID1
		//탭 구성
		TABBAR1 : {
			title : "상세내역",
			css : {	height : "260px" },
			tabs : [
			    {
					id : "a1",
					text : "상세내역",
					active : true
				}
			],
			sideArrow : false,
			onSelect : function (id, lastId, isFirst) {

				var rowData = PAGE.GRID1.getSelectedRowData();
				switch (id) {
					//상세내역
					case "a1":
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();
						if (rowData){
							PAGE.FORM2.setFormData(rowData);
							
							
							PAGE.FORM2.setItemValue("rcgAutoAmt", rowData.rcgAutoAmt);
							PAGE.FORM2.setItemValue("rcgAutoAmt2", rowData.rcgAutoAmt);
							PAGE.FORM2.disableItem("rcgAutoAmt2");
							
							if(rowData.rcgAutoAmt == "" 
									|| rowData.rcgAutoAmt == null 
									|| rowData.rcgAutoAmt == "0"){
								PAGE.FORM2.setItemValue("rcgAutoAmt", 20000);
								PAGE.FORM2.setItemValue("rcgAutoAmt2", 20000);
								PAGE.FORM2.disableItem("rcgAutoAmt2");
							}
							else{
								var amt1 = PAGE.FORM2.getItemValue("rcgAutoAmt");
								var amt2 = PAGE.FORM2.getItemValue("rcgAutoAmt2");
								if(amt1 != amt2){
									PAGE.FORM2.setItemValue("rcgAutoAmt", "ETC");
									PAGE.FORM2.enableItem("rcgAutoAmt2");
								}
							}
							
							if(rowData.sstate == "20"){
								PAGE.FORM2.disableItem("sstate");
							}
							else{
								PAGE.FORM2.enableItem("sstate");
							}
						}
							
						break;
				}
			}
		},
		
		FORM2 : {
			items : _FORMITEMS_['form2'],
			buttons : {
				right : {
					btnSave : {
						label : "저장" , auth:"A-SUC"
					},
					btnImg : {
						label : "여권이미지" , auth:"A-SUC"
					}, 
					btnEtcImg : {
						label : "맥이미지" , auth:"A-SUC"
					}
				}
			},
			onChange : function(name, value) {
				switch(name){
					case "rcgAutoAmt":
						
						var rcgVal = PAGE.FORM2.getItemValue("rcgAutoAmt"); 
						
						PAGE.FORM2.disableItem("rcgAutoAmt2");
						PAGE.FORM2.setItemValue("rcgAutoAmt2", rcgVal);
						
						if("ETC" == rcgVal){
							PAGE.FORM2.enableItem("rcgAutoAmt2");
							PAGE.FORM2.setItemValue("rcgAutoAmt2", 0);
						}
						else if("" == rcgVal){
							PAGE.FORM2.disableItem("rcgAutoAmt2");
							PAGE.FORM2.setItemValue("rcgAutoAmt2", 0);
						}
						else{
							
						}
						
					break;
				}
			},
			onButtonClick : function (btnId, selectedData) {
				var form = this;
				
				if (PAGE.GRID1.getSelectedRowData() == null) {
					mvno.ui.alert("ECMN0002");
				} else {

					if(btnId == "btnSave"){
						
						var confirmTxt = "";
						var amt = 0;
						
						if(form.getItemValue("rcgAutoAmt2") != null ){
							amt = form.getItemValue("rcgAutoAmt2");
						}
						
						
						if( form.getItemValue("sstate") == '20' )
						{
							confirmTxt = "최초 '처리완료' 변경시<br/>"+amt+"원이 자동 충전 됩니다.<br/><br/>저장하시겠습니까?"
						}
						else{
							confirmTxt = "수정 내용을 저장하시겠습니까?"	
						}
						
						mvno.ui.confirm(confirmTxt, function(){
							 
							mvno.cmn.ajax(ROOT + "/pps/sucMgmt/ssateUpdateAjax.json", form, function(resultData) {
								
								var results = resultData.result;
								var retData = results.retData;
								var oResCodeNm = retData.oResCodeNm; 
								
								mvno.ui.alert(oResCodeNm);
								PAGE.FORM2.clear();
								PAGE.GRID1.refresh();	
							});
							
						});
						
						
						
						
						
					}else if(btnId == "btnImg"){
						if(form.getItemValue("fileMask")!="null" && form.getItemValue("fileMask")!=""){	
							userPaperImageView2(form.getItemValue("pathStr"),form.getItemValue("fileMask"),form.getItemValue("fileEncFlag"),form.getItemValue("pageSize"),form.getItemValue("pageIndex"));
						}else{
							alert("이미지가 존재하지 않습니다.");
						}	
							
					}else if(btnId == "btnOrgFind2"){
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("agentId", result.orgnNm);
							form.setItemValue("usrId", result.orgnId);
						});
					}else if(btnId == "btnEtcImg"){
						if(form.getItemValue("etcFileMask")!="null" && form.getItemValue("etcFileMask")!=""){
							userPaperImageView2(form.getItemValue("etcPathStr"),form.getItemValue("etcFileMask"),form.getItemValue("fileEncFlag"),form.getItemValue("pageSize"),form.getItemValue("pageIndex"));
							/*
							var width = 900;
			               	var height = 700;
			               	var top  = 10;
			               	var left = 10;
							var url =  "/pps/sucMgmt/getSucImagePage.do?fileMask="+form.getItemValue("etcFileMask");
							window.open(url, "_popup1", "width="+width+",height="+height+",resizable=yes,scrollbars=yes,top="+top+",left="+left);
							*/
						}else{
							alert("이미지가 존재하지 않습니다.");
						}
					} 
				}
			}
		}
	};

var PAGE = {
	title : "${breadCrumb.title}",
	breadcrumb : "${breadCrumb.breadCrumb}", 
	buttonAuth: ${buttonAuth.jsonString},
	onOpen : function () {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		mvno.ui.createTabbar("TABBAR1");
		
		var sysRdateE = new Date().format('yyyymmdd');
		var time = new Date().getTime();
		time = time - 1000 * 3600 * 24 * 7;
		var sysRdateS = new Date(time);

		PAGE.FORM1.setItemValue("sysRdateS", sysRdateS);
		PAGE.FORM1.setItemValue("sysRdateE", sysRdateE);
		
		mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0089"}, PAGE.FORM2, "rcgAutoAmt");
		PAGE.FORM2.setItemValue("rcgAutoAmt2", "0");
		PAGE.FORM2.disableItem("rcgAutoAmt2");
		
		mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0090"}, PAGE.FORM1, "sstate");
		mvno.cmn.ajax4lov(ROOT + "/pps/hdofc/commonmgmt/PpsCodeComboList.json",{searchLovCd :"PPS_0090"}, PAGE.FORM2, "sstate");
		
		PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("sstate" ) ,true);
		PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("fileEncFlag" ) ,true);
		PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("contractNum" ) ,true);
		PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("rcgSeq" ) ,true);
		PAGE.GRID1.setColumnHidden(PAGE.GRID1.getColIndexById("remark" ) ,true);
		
		
	}
};
	
	
	function alignCenterStatus()
	{
		var w = screen.width / 2;
		var h = screen.height / 2;
	
		var LeftPosition = (screen.width) ? (screen.width-w)/2 : 0;
		var TopPosition = (screen.height) ? (screen.height-h)/2 : 0;
	
		var status = "width=" + w + ",height=" + h + ",top=" + TopPosition + ",left=" + LeftPosition;
		
		return status;
	}

	function userPaperImageView2(filePath,file,encodeYn,pSize,pIdx){
		//console.log(filePath+" , "+file+" , "+encodeYn+" , "+pSize+" , "+pIdx);
		
		filePath = filePath + file;
		
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
					
					//var popupUrl = "https://scn.ktmmobile.com/webscan/ui/callImageViewer.jsp"; // 실서버 
					//var popupUrl = "http://scndev.ktmmobile.com/webscan/ui/callImageViewer.jsp"; // 개발서버
					var popupUrl = result.scanSearchUrl;
					console.log(popupUrl);
				
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


</script>
