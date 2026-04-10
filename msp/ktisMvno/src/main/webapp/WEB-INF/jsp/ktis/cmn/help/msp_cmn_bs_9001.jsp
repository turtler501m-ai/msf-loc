<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_cmn_bs_9001.jsp
	 * @Description : 도움말조회
	 * @
	 * @ 수정일		수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 
	 * @
	 * @author : 
	 * @Create Date : 
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search" ></div>
<!-- 그리드영역 -->
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
	var maxFileCnt = 10;
	var maxFileSize = 1000000;
	
	var DHX = {
		// 검색
		FORM1 : { 
			items : [ 
				{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 60, blockOffset : 0 }
				,{ type : "block", labelWidth : 30 , list : [
					{ type : "select", label : "메뉴", name : "menuId", id : "searchMenuList", width : 235 }
				]}
				,{ type : "button",name : "btnSearch",value : "조회", className:"btn-search1" } 
			]
			,onButtonClick : function(btnId) {
				
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						
					var url1 = ROOT + "/cmn/help/selectHelpMgmtList.json";
					
					PAGE.GRID1.list(url1, form);
					
					break;
				}
			}
		},
		
		// 도움말 목록
		GRID1 : {
			css : { 
				height : "540px"  
			}
			,title : "도움말목록"
			,header : "메뉴명,파일명,등록자,등록일시,수정자,수정일시,메뉴ID"
			,columnIds : "menuNm,fileNm,regstId,regDttm,rvisnId,rvisnDttm,menuId"
			,widths : "250,200,100,140,100,140,10"
			,colAlign : "left,left,center,center,center,center,center"
			,colTypes : "ro,ro,ro,roXdt,ro,roXdt,ro"
			,colSorting : "str,str,str,str,str,str,str"
			,hiddenColumns : "6"
			,paging : true
			,pageSize : 20
			,buttons : {
				right : {
					btnReg : { label : "등록" }
				}
			}
			,onRowDblClicked : function(rowId, celInd) {
				
				this.callEvent('onButtonClick', ['btnReg']);
				
				var data = PAGE.GRID1.getSelectedRowData();
				
				PAGE.FORM2.setItemValue("menuId", data.menuId);
				
				getFileList(data.menuId);
			}
			,onButtonClick : function(btnId, selectedData) {
				switch (btnId) {
					case "btnReg":
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.clear();
						
						PAGE.FORM2.disableItem("fileDel0");
						PAGE.FORM2.disableItem("fileDel1");
						PAGE.FORM2.disableItem("fileDel2");
						PAGE.FORM2.disableItem("fileDel3");
						PAGE.FORM2.disableItem("fileDel4");
						PAGE.FORM2.disableItem("fileDel5");
						PAGE.FORM2.disableItem("fileDel6");
						PAGE.FORM2.disableItem("fileDel7");
						PAGE.FORM2.disableItem("fileDel8");
						PAGE.FORM2.disableItem("fileDel9");
						
						PAGE.FORM2.clearChanged();
						
						var uploader = PAGE.FORM2.getUploader("file_upload1");
						uploader.revive();
						
						
						PAGE.FORM2.showItem("file_upload1");
						PAGE.FORM2.setUserData("file_upload1","limitCount", maxFileCnt);
						PAGE.FORM2.setUserData("file_upload1","limitsize", maxFileSize);
						PAGE.FORM2.setUserData("file_upload1","delUrl", "/org/common/deleteFile2.json");
						
						mvno.cmn.ajax4lov(ROOT + "/cmn/codefind/cmnMenuMst.json", {usgYn:"Y"}, PAGE.FORM2, "menuId");
						
						mvno.ui.popupWindowById("FORM2", "도움말등록", 580, 425, {
							onClose2: function(win) {
								uploader.reset();
								
								PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
							}
						});
						
						PAGE.FORM2.attachEvent("onBeforeFileAdd",function(realName, size){
							
							var menuId = PAGE.FORM2.getItemValue("menuId");
							
							if( mvno.cmn.isEmpty(menuId) ){
								mvno.ui.alert("메뉴를 선택 하세요.");
								return false;
							}
							
							var uploaders = this.getUploader("file_upload1");
							var urls = "/cmn/help/fileUpLoad.do?menuId=" + menuId;
							
							uploaders.setURL(urls);
							uploaders.setSLURL(urls);
							uploaders.setSWFURL(urls);
							
							if(realName == null || realName == '') return false;
							
							var pathpoint = realName.lastIndexOf('.');
							var filepoint = realName.substring(pathpoint+1,realName.length);
							var filetype = filepoint.toLowerCase();
							
							if (filetype == 'jpg' || filetype == 'jpeg' || filetype == 'png'){
								return true;
							}else{
								mvno.ui.alert('지원하지 않는 파일 형식 입니다.');
								return false;
							}
							
							return true;
						});
						
						PAGE.FORM2.attachEvent("onUploadComplete",function(count, data){
							
							uploader.reset();
							
							var menuId = PAGE.FORM2.getItemValue("menuId");
							
							getFileList(menuId);
							
							return true;
						});
						
						break;
					
				}
			}
		}
		
		// 등록/수정
		, FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, blockOffset:0}
				,{ type : "block", labelWidth : 30 , list : [
					{ type : "select", label : "메뉴", name : "menuId", id : "regMenuList", width : 235 }
				]}
				,{type:"block", list:[
					{type:"hidden",name:"fileId0"}
					,{type : "input" , name:"fileName0", label:"파일1", width:370, disabled:true}
					,{type : "newcolumn" }
					,{type : "button" , name:"fileDel0", value: "삭제"}
				]}
				,{type:"block", list:[
					{type:"hidden",name:"fileId1"}
					,{type : "input" , name:"fileName1", label:"파일2", width:370, disabled:true}
					,{type : "newcolumn" }
					,{type : "button" , name:"fileDel1", value: "삭제"}
				]}
				,{type:"block", list:[
					{type:"hidden",name:"fileId2"}
					,{type : "input" , name:"fileName2", label:"파일3", width:370, disabled:true}
					,{type : "newcolumn" }
					,{type : "button" , name:"fileDel2", value: "삭제"}
				]}
				,{type:"block", list:[
					{type:"hidden",name:"fileId3"}
					,{type : "input" , name:"fileName3", label:"파일4", width:370, disabled:true}
					,{type : "newcolumn" }
					,{type : "button" , name:"fileDel3", value: "삭제"}
				]}
				,{type:"block", list:[
					{type:"hidden",name:"fileId4"}
					,{type : "input" , name:"fileName4", label:"파일5", width:370, disabled:true}
					,{type : "newcolumn" }
					,{type : "button" , name:"fileDel4", value: "삭제"}
				]}
				,{type:"block", list:[
					{type:"hidden",name:"fileId5"}
					,{type : "input" , name:"fileName5", label:"파일6", width:370, disabled:true}
					,{type : "newcolumn" }
					,{type : "button" , name:"fileDel5", value: "삭제"}
				]}
				,{type:"block", list:[
					{type:"hidden",name:"fileId6"}
					,{type : "input" , name:"fileName6", label:"파일7", width:370, disabled:true}
					,{type : "newcolumn" }
					,{type : "button" , name:"fileDel6", value: "삭제"}
				]}
				,{type:"block", list:[
					{type:"hidden",name:"fileId7"}
					,{type : "input" , name:"fileName7", label:"파일8", width:370, disabled:true}
					,{type : "newcolumn" }
					,{type : "button" , name:"fileDel7", value: "삭제"}
				]}
				,{type:"block", list:[
					{type:"hidden",name:"fileId8"}
					,{type : "input" , name:"fileName8", label:"파일9", width:370, disabled:true}
					,{type : "newcolumn" }
					,{type : "button" , name:"fileDel8", value: "삭제"}
				]}
				,{type:"block", list:[
					{type:"hidden",name:"fileId9"}
					,{type : "input" , name:"fileName9", label:"파일10", width:370, disabled:true}
					,{type : "newcolumn" }
					,{type : "button" , name:"fileDel9", value: "삭제"}
				]}
				,{type: "block", list: [
					{type: "upload", label:"파일업로드" ,name: "file_upload1", width:523, url:"/cmn/help/fileUpLoad.do" , autoStart: false, userdata: { limitCount : maxFileCnt, limitsize: maxFileSize, delUrl:"/org/common/deleteFile2.json"} }
				]}
			]
			, buttons : {
				center : {
					btnClose : { label: "닫기" }
				}
			}
			, onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				
				if( btnId.indexOf("fileDel") > -1 ) {
					var delBtnSeq =  btnId.substr(-1);
					
					var fileId = "fileId" + delBtnSeq
					var fileName = "fileName" + delBtnSeq
					
					var limitCnt = PAGE.FORM2.getUserData("file_upload1", "limitCount");
					
					mvno.cmn.ajax(ROOT + "/org/common/deleteFile2.json", {fileKey:form.getItemValue(fileId)}, function(resultData) {
						mvno.ui.notify("NCMN0014");
						form.setItemValue(fileId, "");
						form.setItemValue(fileName, "");
						form.disableItem(btnId);
						
						form.showItem("file_upload1");
						
						form.setUserData("file_upload1", "limitCount", limitCnt + 1);
						form.setUserData("file_upload1", "limitsize", maxFileSize);
						form.setUserData("file_upload1", "delUrl", "/org/common/deleteFile2.json");
					});
				}
				
				switch (btnId) {
					case "btnClose":
						mvno.ui.closeWindowById(form);
						break;
				}
			}
			,onChange : function(name, value){
				var form = this;
				
				switch(name){
					case "menuId" :
						
						getFileList(value);
						
						break;
				}
			}
		}
	};
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		
		buttonAuth: ${buttonAuth.jsonString},
		
		onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			mvno.cmn.ajax4lov(ROOT + "/cmn/codefind/cmnMenuMst.json", {usgYn:"Y"}, PAGE.FORM1, "menuId");
		}
	};
	
	function getFileList(value){
		for(var idx = 0; idx < 10; idx++){
			PAGE.FORM2.setItemValue("fileName"+idx,"");
			PAGE.FORM2.disableItem("fileDel"+idx);
		}
		
		if( mvno.cmn.isEmpty(value) ){
			return;
		}
		
		var fileLimitCnt = maxFileCnt;
		
		mvno.cmn.ajax(ROOT + "/cmn/help/selectHelpMgmtList.json", {menuId:value}, function(resultData) {
			var totCnt = resultData.result.data.total;
			
			if(totCnt == fileLimitCnt){
				PAGE.FORM2.hideItem("file_upload1");
			}else{
				PAGE.FORM2.showItem("file_upload1");
			}
			
			PAGE.FORM2.setUserData("file_upload1", "limitCount", fileLimitCnt-parseInt(totCnt));
			PAGE.FORM2.setUserData("file_upload1", "limitsize", maxFileSize);
			PAGE.FORM2.setUserData("file_upload1", "delUrl", "/org/common/deleteFile2.json");
			
			for(var idx = 0; idx < totCnt; idx++){
				var fileIdx = resultData.result.data.rows[idx].fileSeq;
				
				PAGE.FORM2.setItemValue("fileName" + fileIdx, resultData.result.data.rows[idx].fileNm);
				PAGE.FORM2.setItemValue("fileId" + fileIdx, resultData.result.data.rows[idx].fileId);
				PAGE.FORM2.enableItem("fileDel" + fileIdx);
			}
			
		});
	}
</script>