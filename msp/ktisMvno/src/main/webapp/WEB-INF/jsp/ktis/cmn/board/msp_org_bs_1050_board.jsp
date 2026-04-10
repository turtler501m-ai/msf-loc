<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : msp_org_bs_1050_board.jsp
 * @Description : 게시판
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015.10.05 이상직 최초 생성
 * @
 * @author : 이상직
 * @Create Date : 2015.10.05
 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search" />
<!-- 그리드영역 -->
<div id="GRID1"></div>
<div id="GRID2"></div>
<!-- 버튼영역 -->
<div id="FORM2"></div>
<!-- 글조회 -->
<div id="FORM3"></div>
<!-- 등록/수정 -->
<div id="FORM4"></div>

	
<!-- Script -->
<script type="text/javascript">
	// 파일갯수
	var maxFileCnt = 3;
	var maxFileSize = 1000000;
	var dt = new Date();
	var today = new Date().format("yyyymmdd");
	var boardCd = "${boardCd}";
	var userAuth = "${userAuth}";
	var usrOrg ='${orgnInfo.orgnId}';
	
	var DHX = {
			// ----------------------------------------
			// 조회조건
			// ----------------------------------------
			FORM1 : {
					items : [
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						, {type:"block", list:[
							{type:"hidden", name:"stdrDt"}
// 							, {type:"select", name:"searchType", label:"검색구분", userdata:{lov:"CMN0056"}, offsetLeft:20}
							, {type:"select", name:"searchType", label:"검색구분", offsetLeft:20}
							, {type:"newcolumn"}
							, {type:"input", name:"searchVal", width:180, maxLength:50}
							, {type:"hidden", name:"boardCd", value:boardCd}
							, {type:"hidden", name:"userAuth", value:"${userAuth}"}
						]}
						//조회 버튼
						, {type:"newcolumn"}
						, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}/* btn-search(열갯수1~4) */
					]
					, onChange:function(name, value){
						var form = this;
						switch(name){
							case "searchType" :
								if(value == "") form.setItemValue("searchVal", "");
								break;
						}
					}
					, onValidateMore : function(data){
						if(data.searchType == "" && data.searchVal != "") this.pushError("data.searchType", "검색구분", "검색구분을 선택하세요");
					}
					, onButtonClick : function(btnId) {
						var form = this;
						switch (btnId) {
							case "btnSearch":
								if(userAuth == "Y") PAGE.GRID2.list(ROOT + "/cmn/board/getBoardList.json", form, {callback : gridOnLoad});
								else PAGE.GRID1.list(ROOT + "/cmn/board/getBoardList.json", form, {callback : gridOnLoad});
								break;
						}
					}
			}
			// ----------------------------------------
			// 목록조회
			// ----------------------------------------
			, GRID1 : {
					css : {
						height : "575px"
					}
					, header : "게시판코드,일련번호,내용,번호,제목,첨부,게시일자,작성자,수정일시,작성자아이디"
					, columnIds : "boardCd,srlNum,cntt,linenum,title,fileYn,postStrtDt,regstNm,rvisnDttm,regId"
					, colAlign : "center,center,center,center,left,center,center,center,center,center"
					, colTypes : "ro,ro,ro,ro,ro,ch,roXd,ro,roXdt,ro"
					, widths : "80,80,80,100,*,50,100,100,120,120"
					, colSorting : "str,str,str,int,str,str,str,str,str,str"
					, hiddenColumns : [0, 1, 2, 9]
					, paging : true
					, pageSize : 20
					, buttons : {
						right : {
							btnRead : { label : "조회" }
						}
					}
					, onRowDblClicked : function(rowId, celInd) {
						this.callEvent('onButtonClick', ['btnRead']); // 읽기 폼 호출
					}
					, onButtonClick : function(btnId, selectedData) {
						switch (btnId) {
							case "btnRead":
								if(PAGE.GRID1.getSelectedRowData() == null){
									mvno.ui.alert("조회대상을 선택하세요");
									return;
								}
								
								mvno.ui.createForm("FORM3");
								
								var fileLimitCnt = maxFileCnt;
								
								var data = PAGE.GRID1.getSelectedRowData();
								
								PAGE.FORM3.setFormData(data);
								
								// 첨부파일조회
								var fileLimitCnt = maxFileCnt;
								mvno.cmn.ajax(ROOT + "/cmn/board/getBoardFile.json", {NOTICE_ID:data.srlNum}, function(resultData) {
									/*var totCnt = resultData.result.fileTotalCnt;
									PAGE.FORM3.setUserData("file_upload1", "limitCount", maxFileCnt-parseInt(totCnt));
									PAGE.FORM3.setUserData("file_upload1", "limitsize", maxFileSize);
									PAGE.FORM3.setUserData("file_upload1", "delUrl", "/org/common/deleteFile2.json");
									
									if(parseInt(totCnt) == fileLimitCnt){
										PAGE.FORM3.hideItem("file_upload1");
									}else{
										PAGE.FORM3.showItem("file_upload1");
									}*/
									
									// 첫번째파일
									if(!mvno.cmn.isEmpty(resultData.result.fileNm)){
										PAGE.FORM3.setItemValue("fileName1", resultData.result.fileNm);
										PAGE.FORM3.setItemValue("fileId1", resultData.result.fileId);
										PAGE.FORM3.enableItem("fileDown1");
										PAGE.FORM3.enableItem("fileDel1");
									}else{
										PAGE.FORM3.disableItem("fileDown1");
										PAGE.FORM3.disableItem("fileDel1");
									}
									
									// 두번째파일
									if(!mvno.cmn.isEmpty(resultData.result.fileNm1)){
										PAGE.FORM3.setItemValue("fileName2", resultData.result.fileNm1);
										PAGE.FORM3.setItemValue("fileId2", resultData.result.fileId1);
										PAGE.FORM3.enableItem("fileDown2");
										PAGE.FORM3.enableItem("fileDel2");
									}else{
										PAGE.FORM3.disableItem("fileDown2");
										PAGE.FORM3.disableItem("fileDel2");
									}
									
									// 세번째파일
									if(!mvno.cmn.isEmpty(resultData.result.fileNm2)){
										PAGE.FORM3.setItemValue("fileName3", resultData.result.fileNm2);
										PAGE.FORM3.setItemValue("fileId3", resultData.result.fileId2);
										PAGE.FORM3.enableItem("fileDown3");
										PAGE.FORM3.enableItem("fileDel3");
									}else{
										PAGE.FORM3.disableItem("fileDown3");
										PAGE.FORM3.disableItem("fileDel3");
									}
								});
								
								PAGE.FORM3.clearChanged();
								
								mvno.ui.popupWindowById("FORM3", "조회화면", 730, 500, {
									onClose: function(win) {
										if (! PAGE.FORM3.isChanged()) return true;
										//mvno.ui.confirm("CCMN0005", function(){
											win.closeForce();
										//});
									}
								});
								
								break;
						}
					}
			}
			// ----------------------------------------
			// 글조회
			// ----------------------------------------
			, FORM3 : {
					items : [
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
						, {type:"input", label:"제목", width:500, name:"title", readonly:true}
						, {type:"block", list:[
							{type:"calendar" , name:"postStrtDt", label:"게시일자", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", width:100, calendarPosition: 'bottom', disabled:true}
							, {type: "newcolumn" }
							, {type: "calendar" , name:"postEndDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", label:"~", labelWidth:30, labelAlign:"center", width:100, calendarPosition: 'bottom', disabled:true}
						]}
						, {type:"input",name:"cntt",label:"내용",width:500, rows:15, maxLength:1300, readonly:true}
						, {type:"block", list:[
							{type:"hidden",name:"fileId1"}
							, {type : "input" , name:"fileName1", label:"파일명", width:420, readonly:true}
							, {type : "newcolumn" }
							, {type : "button" , name:"fileDown1", value: "다운로드"}
						]}
						,{type:"block", list:[
							{type:"hidden",name:"fileId2"}
							, {type : "input" , name:"fileName2", label:"파일명", width:420, readonly:true}
							, {type : "newcolumn" }
							, {type : "button" , name:"fileDown2", value: "다운로드"}
						]}
						,{type:"block", list:[
							{type:"hidden",name:"fileId3"}
							, {type : "input" , name:"fileName3", label:"파일명", width:420, readonly:true}
							, {type : "newcolumn" }
							, {type : "button" , name:"fileDown3", value: "다운로드"}
						]}
					]
					, buttons : {
						center : {
							btnClose : { label: "닫기" }
						}
					}
					, onButtonClick : function(btnId) {
						var form = this; // 혼란방지용으로 미리 설정 (권고)
						var downUrl = ROOT + "/cmn/board/downFile.json?fileId=";
						
						switch (btnId) {
							
							case "fileDown1" :
								mvno.cmn.download(downUrl + form.getItemValue("fileId1"));
								break;
							case "fileDown2" :
								mvno.cmn.download(downUrl + form.getItemValue("fileId2"));
								break;
							case "fileDown3" :
								mvno.cmn.download(downUrl + PAGE.FORM2.getItemValue("fileId3"));
								break;
							case "btnClose":
								mvno.ui.closeWindowById(form);
								break;
						}
					}
			}
			// ----------------------------------------
			// 목록조회(관리자)
			// ----------------------------------------
			, GRID2 : {
					css : {
						height : "575px"
					}
					, header : "게시판코드,일련번호,내용,전체,번호,제목,첨부,게시일자,본사,고객센터,후불,선불,작성자아이디,작성자조직"
					, columnIds : "boardCd,srlNum,cntt,orgnTpCd1,linenum,title,fileYn,postStrtDt,orgnTpCd2,orgnTpCd3,orgnTpCd4,orgnTpCd5,regId,regOrgnId"
					, colAlign : "center,center,left,center,center,left,center,center,center,center,center,center,center,center"
					, colTypes : "ro,ro,ro,ch,ro,ro,ch,roXd,ch,ch,ch,ch,ro,ro"
					, widths : "80,80,100,100,100,*,50,100,50,50,50,50,50,50"
					, colSorting : "str,str,str,str,int,str,str,str,str,str,str,str,str,str"
					, hiddenColumns : [0, 1, 2, 3, 12,13]
					, paging : true
					, pageSize : 20
					, buttons : {
						right : {
							btnReg : { label : "등록" }
							, btnMod : { label : "수정" }
							, btnDel : { label : "삭제" }
							, btnRead : { label : "조회" }
						}
					}
					, onRowDblClicked : function(rowId, celInd) {
						this.callEvent('onButtonClick', ['btnRead']); // 읽기 폼 호출
					}
					, onButtonClick : function(btnId, selectedData) {
						var form = this;
						
						switch (btnId) {
							case "btnReg":
								mvno.ui.createForm("FORM4");
								
								PAGE.FORM4.clear();
								
								
								PAGE.FORM4.setItemValue("inputMod", "C" );
								PAGE.FORM4.setItemValue("boardCd", boardCd );
								PAGE.FORM4.setItemValue("postStrtDt", today);
								PAGE.FORM4.setItemValue("postEndDt", new Date(dt.setDate(dt.getDate() + 30)).format("yyyymmdd"));
								
								PAGE.FORM4.disableItem("fileDown1");
								PAGE.FORM4.disableItem("fileDel1");
								PAGE.FORM4.disableItem("fileDown2");
								PAGE.FORM4.disableItem("fileDel2");
								PAGE.FORM4.disableItem("fileDown3");
								PAGE.FORM4.disableItem("fileDel3");
								
								PAGE.FORM4.enableItem("orgnTpCd1");
								PAGE.FORM4.enableItem("orgnTpCd2");
								PAGE.FORM4.enableItem("orgnTpCd3");
								PAGE.FORM4.enableItem("orgnTpCd4");
								PAGE.FORM4.enableItem("orgnTpCd5");
								if(usrOrg == "A30040999"){
									PAGE.FORM4.disableItem("orgnTpCd1");
									PAGE.FORM4.disableItem("orgnTpCd2");
									PAGE.FORM4.disableItem("orgnTpCd3");
									PAGE.FORM4.disableItem("orgnTpCd4");
									PAGE.FORM4.disableItem("orgnTpCd5");
									PAGE.FORM4.setItemValue("orgnTpCd2" , '1');
									PAGE.FORM4.setItemValue("orgnTpCd3" , '1');
								}
								
								PAGE.FORM4.clearChanged();
								
								var uploader = PAGE.FORM4.getUploader("file_upload1");
								uploader.revive();
								
								PAGE.FORM4.showItem("file_upload1");
								PAGE.FORM4.setUserData("file_upload1","limitCount", maxFileCnt);
								PAGE.FORM4.setUserData("file_upload1","limitsize", maxFileSize);
								PAGE.FORM4.setUserData("file_upload1","delUrl", "/org/common/deleteFile2.json");
								
								mvno.ui.popupWindowById("FORM4", "등록화면", 700, 550, {
									onClose2: function(win) {
										uploader.reset();
									}
								});
								
								break;
							case "btnMod":
								fileLimitCnt = maxFileCnt;
								
								if(PAGE.GRID2.getSelectedRowData() == null){
									mvno.ui.alert("ECMN0002");
									return;
								}
								
								if(usrOrg == "A30040999" && PAGE.GRID2.getSelectedRowData().regOrgnId   != usrOrg){
									mvno.ui.alert("권한이없습니다.");
									return;
								}
								mvno.ui.createForm("FORM4");
								PAGE.FORM4.clear();
								
								
								
								var fileLimitCnt = maxFileCnt;
								var data = PAGE.GRID2.getSelectedRowData();
								PAGE.FORM4.setFormData(data);
								
								PAGE.FORM4.setItemValue("inputMod", "U");
								if(PAGE.GRID2.getSelectedRowData().orgnTpCd1 == "1"){
									PAGE.FORM4.disableItem("orgnTpCd2");
									PAGE.FORM4.disableItem("orgnTpCd3");
									PAGE.FORM4.disableItem("orgnTpCd4");
									PAGE.FORM4.disableItem("orgnTpCd5");
								}else{
									if(usrOrg == "A30040999"){
										PAGE.FORM4.disableItem("orgnTpCd1");
										PAGE.FORM4.disableItem("orgnTpCd2");
										PAGE.FORM4.disableItem("orgnTpCd3");
										PAGE.FORM4.disableItem("orgnTpCd4");
										PAGE.FORM4.disableItem("orgnTpCd5");
									}else{
										PAGE.FORM4.enableItem("orgnTpCd2");
										PAGE.FORM4.enableItem("orgnTpCd3");
										PAGE.FORM4.enableItem("orgnTpCd4");
										PAGE.FORM4.enableItem("orgnTpCd5");
									}
								}
								
								
								mvno.cmn.ajax(ROOT + "/cmn/board/getBoardFile.json", {NOTICE_ID : data.srlNum}, function(resultData) {
									var totCnt = resultData.result.fileTotalCnt;
									
									PAGE.FORM4.setUserData("file_upload1", "limitCount", fileLimitCnt-parseInt(totCnt));
									PAGE.FORM4.setUserData("file_upload1", "limitsize", maxFileSize);
									PAGE.FORM4.setUserData("file_upload1", "delUrl", "/org/common/deleteFile2.json");
									
									if(parseInt(totCnt) == maxFileCnt) PAGE.FORM4.hideItem("file_upload1");
									else PAGE.FORM4.showItem("file_upload1");
									
									if(!mvno.cmn.isEmpty(resultData.result.fileNm)){
										PAGE.FORM4.setItemValue("fileName1", resultData.result.fileNm);
										PAGE.FORM4.setItemValue("fileId1", resultData.result.fileId);
										PAGE.FORM4.enableItem("fileDown1");
										PAGE.FORM4.enableItem("fileDel1");
									}else{
										PAGE.FORM4.disableItem("fileDown1");
										PAGE.FORM4.disableItem("fileDel1");
									}
									
									if(!mvno.cmn.isEmpty(resultData.result.fileNm1)){
										PAGE.FORM4.setItemValue("fileName2", resultData.result.fileNm1);
										PAGE.FORM4.setItemValue("fileId2", resultData.result.fileId1);
										PAGE.FORM4.enableItem("fileDown2");
										PAGE.FORM4.enableItem("fileDel2");
									}else{
										PAGE.FORM4.disableItem("fileDown2");
										PAGE.FORM4.disableItem("fileDel2");
									}
									
									if(!mvno.cmn.isEmpty(resultData.result.fileNm2)){
										PAGE.FORM4.setItemValue("fileName3", resultData.result.fileNm2);
										PAGE.FORM4.setItemValue("fileId3", resultData.result.fileId2);
										PAGE.FORM4.enableItem("fileDown3");
										PAGE.FORM4.enableItem("fileDel3");
									}else{
										PAGE.FORM4.disableItem("fileDown3");
										PAGE.FORM4.disableItem("fileDel3");
									}
								});
								
								var uploader = PAGE.FORM4.getUploader("file_upload1");
								uploader.revive();
								PAGE.FORM4.clearChanged();
								
								mvno.ui.popupWindowById("FORM4", "수정화면", 700, 550, {
									onClose2: function(win) {
										uploader.reset();
									}
								});
								
								break;
							case "btnDel":
								if(PAGE.GRID2.getSelectedRowData() == null){
									mvno.ui.alert("ECMN0002");
									return;
								}
								if(usrOrg == "A30040999" && PAGE.GRID2.getSelectedRowData().regOrgnId   != usrOrg){
									mvno.ui.alert("권한이없습니다.");
									return;
								}
								mvno.cmn.ajax4confirm("CCMN0003", ROOT + "/cmn/board/deleteBoardMgmt.json", PAGE.GRID2.getSelectedRowData(), function(result){
									mvno.ui.notify("NCMN0003");
									PAGE.GRID2.refresh();
								});
								break;
							case "btnRead":
								if(PAGE.GRID2.getSelectedRowData() == null){
									mvno.ui.alert("조회대상을 선택하세요");
									return;
								}
								
								mvno.ui.createForm("FORM3");
								
								var data = PAGE.GRID2.getSelectedRowData();
								
								PAGE.FORM3.setFormData(data);
								
								// 첨부파일조회
								var fileLimitCnt = maxFileCnt;
								mvno.cmn.ajax(ROOT + "/cmn/board/getBoardFile.json", {NOTICE_ID:data.srlNum}, function(resultData) {
									var totCnt = resultData.result.fileTotalCnt;
									PAGE.FORM3.setUserData("file_upload1", "limitCount", fileLimitCnt-parseInt(totCnt));
									PAGE.FORM3.setUserData("file_upload1", "limitsize", maxFileSize);
									PAGE.FORM3.setUserData("file_upload1", "delUrl", "/org/common/deleteFile2.json");
									
									if(parseInt(totCnt) == fileLimitCnt){
										PAGE.FORM3.hideItem("file_upload1");
									}else{
										PAGE.FORM3.showItem("file_upload1");
									}
									
									// 첫번째파일
									if(!mvno.cmn.isEmpty(resultData.result.fileNm)){
										PAGE.FORM3.setItemValue("fileName1", resultData.result.fileNm);
										PAGE.FORM3.setItemValue("fileId1", resultData.result.fileId);
										PAGE.FORM3.enableItem("fileDown1");
										PAGE.FORM3.enableItem("fileDel1");
									}else{
										PAGE.FORM3.disableItem("fileDown1");
										PAGE.FORM3.disableItem("fileDel1");
									}
									
									// 두번째파일
									if(!mvno.cmn.isEmpty(resultData.result.fileNm1)){
										PAGE.FORM3.setItemValue("fileName2", resultData.result.fileNm1);
										PAGE.FORM3.setItemValue("fileId2", resultData.result.fileId1);
										PAGE.FORM3.enableItem("fileDown2");
										PAGE.FORM3.enableItem("fileDel2");
									}else{
										PAGE.FORM3.disableItem("fileDown2");
										PAGE.FORM3.disableItem("fileDel2");
									}
									
									// 세번째파일
									if(!mvno.cmn.isEmpty(resultData.result.fileNm2)){
										PAGE.FORM3.setItemValue("fileName3", resultData.result.fileNm2);
										PAGE.FORM3.setItemValue("fileId3", resultData.result.fileId2);
										PAGE.FORM3.enableItem("fileDown3");
										PAGE.FORM3.enableItem("fileDel3");
									}else{
										PAGE.FORM3.disableItem("fileDown3");
										PAGE.FORM3.disableItem("fileDel3");
									}
								});
								
								PAGE.FORM3.clearChanged();
								
								mvno.ui.popupWindowById("FORM3", "조회화면", 680, 500, {
									onClose: function(win) {
										if (! PAGE.FORM3.isChanged()) return true;
										//mvno.ui.confirm("CCMN0005", function(){
											win.closeForce();
										//});
									}
								});
								
								break;
						}
					}
			}
			// ----------------------------------------
			// 등록/수정
			// ----------------------------------------
			, FORM4 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
					,{type:"input", label:"제목", width:500, name:"title", maxLength:60, required:true}
					,{type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
					,{type:"block", list:[
						{type:"calendar", name:"postStrtDt", label:"게시일자", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", width:100, calendarPosition: "bottom", required:true}
						,{type:"newcolumn"}
						,{type:"calendar", name:"postEndDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", label:"~", labelWidth:30, labelAlign:"center", width:100, calendarPosition: "bottom"}
						,{type:"newcolumn" }
					]}
					,{type:"block", list:[
						{type:"label",label:"게시대상", width:50,offsetLeft:0 }
						,{type:"newcolumn"}
						,{type:"checkbox",width:5, label:"전체",labelWidth:30, name:"orgnTpCd1", value:"1", offsetLeft:0, labelAlign:"center"}
						,{type:"newcolumn"}
						,{type:"checkbox",width:5, label:"본사",name:"orgnTpCd2", value:"1", position:"label-left", labelAlign:"right"}
						,{type:"newcolumn"}
						,{type:"checkbox",width:5, label:"고객센터",name:"orgnTpCd3", value:"1", position:"label-left", labelAlign:"right"}
						,{type:"newcolumn"}
						,{type:"checkbox",width:5, label:"후불",name:"orgnTpCd4", value:"1", position:"label-left", labelAlign:"right"}
						,{type:"newcolumn"}
						,{type:"checkbox",width:5, label:"선불",name:"orgnTpCd5", value:"1", position:"label-left", labelAlign:"right"}
					]}
					,{type:"input", name:"cntt", label:"내용", width:500, maxLength:1300, rows:15, required:true}
					,{type:"hidden",name:"inputMod"}
					,{type:"hidden",name:"srlNum"}
					,{type:"hidden",name:"boardCd"}
					,{type:"hidden",name:"regId"}
					,{type:"block", list:[
						{type:"hidden",name:"fileId1"}
						,{type : "input" , name:"fileName1", label:"파일명", width:370, disabled:true}
						,{type : "newcolumn" }
						,{type : "button" , name:"fileDown1", value: "다운로드"}
						,{type : "newcolumn" }
						,{type : "button" , name:"fileDel1", value: "삭제"}
					]}
					,{type:"block", list:[
						{type:"hidden",name:"fileId2"}
						,{type : "input" , name:"fileName2", label:"파일명", width:370, disabled:true}
						,{type : "newcolumn" }
						,{type : "button" , name:"fileDown2", value: "다운로드"}
						,{type : "newcolumn" }
						,{type : "button" , name:"fileDel2", value: "삭제"}
					]}
					,{type:"block", list:[
					    {type:"hidden",name:"fileId3"}
						,{type : "input" , name:"fileName3", label:"파일명", width:370, disabled:true}
						,{type : "newcolumn" }
						,{type : "button" , name:"fileDown3", value: "다운로드"}
						,{type : "newcolumn" }
						,{type : "button" , name:"fileDel3", value: "삭제"}
					]}
					,{type: "block", list: [
						{type: "upload", label:"파일업로드" ,name: "file_upload1", width:535, url:"/cmn/board/fileUpLoad.do", autoStart: false, userdata: { limitCount : maxFileCnt, limitsize: maxFileSize, delUrl:"/org/common/deleteFile2.json"} }
					]}
				]
				, buttons : {
					center : {
						btnSave : { label : "저장" },
						btnClose : { label: "닫기" }
					}
				}
				, onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)
					var fileLimitCnt = maxFileCnt;
					var downUrl = ROOT + "/cmn/board/downFile.json?fileId=";
					
					switch (btnId) {
						case "btnSave":
							if(form.getItemValue('title').indexOf("<") < 0 && form.getItemValue('title').indexOf(">") < 0 && form.getItemValue('cntt').indexOf("<") < 0 && form.getItemValue('cntt').indexOf(">") < 0) { // 재목, 본문 특수문자(<, >) 체크
								var url = "";
								if(form.getItemValue("inputMod") == "C"){
									url = "/cmn/board/insertBoardMgmt.json";
								}else if(form.getItemValue("inputMod") == "U"){
									url = "/cmn/board/updateBoardMgmt.json";
								}
								
								mvno.cmn.ajax(ROOT + url, form, function(result) {
									mvno.ui.closeWindowById(form, true);
									mvno.ui.notify("NCMN0001");
									PAGE.GRID2.refresh();
								});
							} else {
								mvno.ui.alert("< 또는 > 특수문자는 사용이 불가능 합니다.");
							}
							break;
						case "fileDown1" :
							mvno.cmn.download(downUrl + form.getItemValue("fileId1"));
							break;
						case "fileDel1" :
							mvno.cmn.ajax(ROOT + "/cmn/board/deleteFile.json", {fileId:form.getItemValue("fileId1"), noticeId:form.getItemValue("srlNum")}, function(resultData) {
								mvno.ui.notify("NCMN0014");
								form.setItemValue("fileId1", "");
								form.setItemValue("fileName1", "");
								form.disableItem("fileDown1");
								form.disableItem("fileDel1");
								form.showItem("file_upload1");
								
								form.setUserData("file_upload1", "limitCount", fileLimitCnt-(resultData.result.fileTotCnt));
								form.setUserData("file_upload1", "limitsize", maxFileSize);
								form.setUserData("file_upload1", "delUrl", "/org/common/deleteFile2.json");
							});
							break;
						case "fileDown2" :
							mvno.cmn.download(downUrl + form.getItemValue("fileId2"));
							break;
						case "fileDel2" :
							mvno.cmn.ajax(ROOT + "/cmn/board/deleteFile.json", {fileId:PAGE.FORM4.getItemValue("fileId2"), noticeId:form.getItemValue("srlNum")} , function(resultData) {
								mvno.ui.notify("NCMN0014");
								form.setItemValue("fileId2", "");
								form.setItemValue("fileName2", "");
								form.disableItem("fileDown2");
								form.disableItem("fileDel2");
								form.showItem("file_upload1");
								
								form.setUserData("file_upload1", "limitCount", fileLimitCnt-(resultData.result.fileTotCnt));
								form.setUserData("file_upload1", "limitsize", maxFileSize);
								form.setUserData("file_upload1", "delUrl", "/org/common/deleteFile2.json");
							});
							break;
						case "fileDown3" :
							mvno.cmn.download(downUrl + form.getItemValue("fileId3"));
							break;
						case "fileDel3" :
							mvno.cmn.ajax(ROOT + "/cmn/board/deleteFile.json",  {fileId:PAGE.FORM4.getItemValue("fileId3"), noticeId:form.getItemValue("srlNum")} , function(resultData) {
								mvno.ui.notify("NCMN0014");
								form.setItemValue("fileId3", "");
								form.setItemValue("fileName3", "");
								form.disableItem("fileDown3");
								form.disableItem("fileDel3");
								form.showItem("file_upload1");
								
								form.setUserData("file_upload1", "limitCount", fileLimitCnt-(resultData.result.fileTotCnt));
								form.setUserData("file_upload1", "limitsize", maxFileSize);
								form.setUserData("file_upload1", "delUrl", "/org/common/deleteFile2.json");
							});
							break;
						case "btnClose":
							mvno.ui.closeWindowById(form);
							break;
					}
				}
				,onChange : function(name, value){
					var form = this;
					
					switch(name){
						case "orgnTpCd1" :
							if(form.isItemChecked(name) == true){
								form.setItemValue("orgnTpCd2", "1");
								form.setItemValue("orgnTpCd3", "1");
								form.setItemValue("orgnTpCd4", "1");
								form.setItemValue("orgnTpCd5", "1");
								
								form.disableItem("orgnTpCd2");
								form.disableItem("orgnTpCd3");
								form.disableItem("orgnTpCd4");
								form.disableItem("orgnTpCd5");
							}else{
								form.setItemValue("orgnTpCd2", "0");
								form.setItemValue("orgnTpCd3", "0");
								form.setItemValue("orgnTpCd4", "0");
								form.setItemValue("orgnTpCd5", "0");
								
								form.enableItem("orgnTpCd2");
								form.enableItem("orgnTpCd3");
								form.enableItem("orgnTpCd4");
								form.enableItem("orgnTpCd5");
							}
						case "orgnTpCd2" :
							if(form.isItemChecked(name) == false){
								if(form.getItemValue("orgnTpCd1") == "1") form.setItemValue("orgnTpCd1", "0");
							}
							break;
						case "orgnTpCd3" :
							if(form.isItemChecked(name) == false){
								if(form.getItemValue("orgnTpCd1") == "1") form.setItemValue("orgnTpCd1", "0");
							}
							break;
						case "orgnTpCd4" :
							if(form.isItemChecked(name) == false){
								if(form.getItemValue("orgnTpCd1") == "1") form.setItemValue("orgnTpCd1", "0");
							}
							break;
						case "orgnTpCd5" :
							if(form.isItemChecked(name) == false){
								if(form.getItemValue("orgnTpCd1") == "1") form.setItemValue("orgnTpCd1", "0");
							}
							break;
					}
				}
				,onValidateMore : function (data){
					if(data.postEndDt == "") this.pushError("data.postEndDt", "게시일자", "종료일자를 입력하세요");
					else {
						if(data.postStrtDt > data.postEndDt) this.pushError("data.postStrtDt","게시일자",["ICMN0004"]);
					}
					
					// 체크박스 하나도 선택 안된 경우 
					if(data.orgnTpCd1 == "0" && data.orgnTpCd2 == "0" && data.orgnTpCd3 == "0" && data.orgnTpCd4 == "0" && data.orgnTpCd5 == "0"){
						this.pushError("data.orgnTpCd1","게시대상",["ECMN0003"]);
					} 
				}
			}
			
	};
	
	var PAGE = {
		title: "${breadCrumb.title}",
		breadcrumb: "${breadCrumb.breadCrumb}",
		buttonAuth: ${buttonAuth.jsonString},
		onOpen : function() {
			mvno.ui.createForm("FORM1");

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0231"}, PAGE.FORM1, "searchType"); // 검색구분
			
			PAGE.FORM1.setItemValue("stdrDt", today);
			
			console.log("userAuth=" + '${userAuth}');
			
			if(userAuth == 'Y') {
				mvno.ui.createGrid("GRID2");
				PAGE.GRID2.list(ROOT + "/cmn/board/getBoardList.json", {boardCd:boardCd, stdrDt:today, userAuth:userAuth}, {callback : gridOnLoad});
			}else{
				mvno.ui.createGrid("GRID1");
				PAGE.GRID1.list(ROOT + "/cmn/board/getBoardList.json", {boardCd:boardCd, stdrDt:today}, {callback : gridOnLoad});
			}
			
		}
	};
	
	function gridOnLoad  ()
	{
		if('${userAuth}' == 'Y')
		{
			PAGE.GRID2.forEachRow(function(id){
				PAGE.GRID2.cells(id,6).setDisabled(true);
				PAGE.GRID2.cells(id,8).setDisabled(true);
				PAGE.GRID2.cells(id,9).setDisabled(true);
				PAGE.GRID2.cells(id,10).setDisabled(true);
				PAGE.GRID2.cells(id,11).setDisabled(true);
				if (PAGE.GRID2.cellById(id,7).getValue() == today)
				{
					PAGE.GRID2.setRowTextStyle(id,"color: blue");
					PAGE.GRID2.setRowTextBold(id);
				}
			});
		}else{
			PAGE.GRID1.forEachRow(function(id){
				PAGE.GRID1.cells(id,5).setDisabled(true);
				if (PAGE.GRID1.cellById(id,6).getValue() == today)
				{
					PAGE.GRID1.setRowTextStyle(id,"color: blue");
					PAGE.GRID1.setRowTextBold(id);
				}
			});
		}
	};
	
</script>