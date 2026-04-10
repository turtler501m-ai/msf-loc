<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name  : creditMgmt.jsp
	 * @Description : 신용정보 동의서 관리
	 * @
	 * @ 수정일		수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2016.04.06  최초생성
	 * @ 
	 * @author : 한상욱
	 * @Create Date : 2016.04.06
	 */
%>
	
	<div id="FORM1" class="section-search"></div>
	<div id="FORM2" class="section-box"></div>
	<div id="FORM3" class="section-box"></div>
	
	<div id="GRID1"></div>
	
	<script type="text/javascript">
	var DHX = {
			FORM1 : {
				items : [
					 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
					 {type:"block", list:[
						 {type:"calendar", name:"startDate", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", value:"${info.startDate}", validate: "NotEmpty, mvno.vxRule.beforeToday", label:"등록일자", offsetLeft:10, width:100, readonly: true},
						 {type:"newcolumn"},
						 {type:"calendar", name:"endDate", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", value:"${info.endDate}", validate: "NotEmpty", label:"~", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100, readonly: true},
						 {type:"newcolumn"},
						 {type:"input", name:"orgnId", position:"label-left", labelAlign:"center", label:"대리점", width:100, offsetLeft:10},
						 {type:"newcolumn"},
						 {type:"button", value:"검색", name:"btnOrgFind"},
						 {type:"newcolumn"},
						 {type:"input", name:"orgnNm", width:150, readonly: true}
					]},					
					{type:"newcolumn", offset:130},
					{type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}
				],
				onValidateMore : function(data) {
					if(data.startDate > data.endDate) {
						this.pushError(["startDate,endDate"],"조회기간","조회시작일자가 조회종료일자보다 이후의 일자입니다");
					}
				},
				onButtonClick : function(btnId) {
					
					var form = this;
					
					switch(btnId) {
						case "btnOrgFind":
							mvno.ui.codefinder("ORG", function(result) {
								form.setItemValue("orgnId", result.orgnId);
								form.setItemValue("orgnNm", result.orgnNm);
							});
							break;
						
						case "btnSearch" :
							PAGE.GRID1.list(ROOT + "/rcp/credit/list.json", form);
							break;
					}
				},
				onChange : function(name, value) {
					var form = this;
					if(name == "orgnId") {
						if(mvno.cmn.isEmpty(value)) {
							form.setItemValue("orgnNm","");
						}
					}
				}
			},
			
			GRID1 : {
				css : {
					height : "555px"
				},

				title : "조회결과",
				header : " 순번,등록일자,조직코드,조직명,고객명,연락처,등록자ID,등록자명", 
				columnIds : "creditId,regDate,orgCode,orgName,custName,telNo,regId,regName",
				colAlign : "center,center,center,center,center,center,center,center",
				colTypes : "ro,roXd,ro,ro,ro,roXp,ro,ro",
				widths : "50,120,120,180,120,150,120,120",
				colSorting : "str,str,str,str,str,str,str,str",
				hiddenColumns : "0",
				paging : true,
				pageSize : 20,
				
				buttons : {
					right : {
						btnReg : { label : "등록" },
						btnMod : { label : "수정" }
					}
				},
				
				onRowDblClicked : function(rowId, celInd) {
	 				this.callEvent('onButtonClick', ['btnRead']); // 읽기 폼 호출
					
	             },
	            
				onButtonClick : function(btnId) {
					var form = this;
					var fileLimitCnt = 5;
				
					switch (btnId) {
						case "btnRead":
							fileLimitCnt = 5;
		                   	   
							if (PAGE.GRID1.getSelectedRowId() == null)
	                   	   	{
								mvno.ui.alert("ECMN0002");
								return;
							}

	                   		mvno.ui.createForm("FORM3");
	                    	PAGE.FORM3.clear();
	                    	
							var data = PAGE.GRID1.getSelectedRowData();
							PAGE.FORM3.setFormData(data);
	                          
							mvno.cmn.ajax(ROOT + "/rcp/credit/getFile.json", PAGE.GRID1.getSelectedRowData(), function(resultData) {
								var totCnt = resultData.result.fileTotalCnt;
								
		                        PAGE.FORM3.setItemValue("orgnId", data.orgCode);
		                        PAGE.FORM3.setItemValue("orgnNm", data.orgName);
		                        
								if(!mvno.cmn.isEmpty(resultData.result.fileNm)){
		                        	   
									PAGE.FORM3.setItemValue("fileName1", resultData.result.fileNm);
									PAGE.FORM3.setItemValue("fileId1", resultData.result.fileId); 
									PAGE.FORM3.enableItem("fileDown1");
									PAGE.FORM3.enableItem("fileDel1");
								} else {
									PAGE.FORM3.disableItem("fileDown1");
									PAGE.FORM3.disableItem("fileDel1");
								}
		                    	   
		                        if(!mvno.cmn.isEmpty(resultData.result.fileNm1)){
		                            PAGE.FORM3.setItemValue("fileName2", resultData.result.fileNm1);
		                            PAGE.FORM3.setItemValue("fileId2", resultData.result.fileId1);
		                            PAGE.FORM3.enableItem("fileDown2");
		                            PAGE.FORM3.enableItem("fileDel2");
		                        } else {
		                            PAGE.FORM3.disableItem("fileDown2");
		                            PAGE.FORM3.disableItem("fileDel2");
		                        }
		                           
		                        if(!mvno.cmn.isEmpty(resultData.result.fileNm2)){
		                            PAGE.FORM3.setItemValue("fileName3", resultData.result.fileNm2);
		                            PAGE.FORM3.setItemValue("fileId3", resultData.result.fileId2);
		                            PAGE.FORM3.enableItem("fileDown3");
		                            PAGE.FORM3.enableItem("fileDel3");
		                        } else {
		                            PAGE.FORM3.disableItem("fileDown3");
		                            PAGE.FORM3.disableItem("fileDel3");
		                        }
		                           
		                        if(!mvno.cmn.isEmpty(resultData.result.fileNm3)){
		                            PAGE.FORM3.setItemValue("fileName4", resultData.result.fileNm3);
		                            PAGE.FORM3.setItemValue("fileId4", resultData.result.fileId3);
		                            PAGE.FORM3.enableItem("fileDown4");
		                            PAGE.FORM3.enableItem("fileDel4");
		                        } else {
		                            PAGE.FORM3.disableItem("fileDown4");
		                            PAGE.FORM3.disableItem("fileDel4");
		                        }
		                           
		                        if(!mvno.cmn.isEmpty(resultData.result.fileNm4)){
		                            PAGE.FORM3.setItemValue("fileName5", resultData.result.fileNm4);
		                            PAGE.FORM3.setItemValue("fileId5", resultData.result.fileId4);
		                            PAGE.FORM3.enableItem("fileDown5");
		                            PAGE.FORM3.enableItem("fileDel5");
		                        } else {
		                            PAGE.FORM3.disableItem("fileDown5");
		                            PAGE.FORM3.disableItem("fileDel5");
		                        }
							});
 
							mvno.ui.popupWindowById("FORM3", "신용정보 동의서", 730, 620);
							
							break;
						
						case "btnReg": 
				      	
							var data = PAGE.FORM1.getFormData(true);
	
							if (data.orgnId == '') {
								mvno.ui.alert("대리점을 선택하세요.");
								form.setItemFocus("orgnId");
								
								return;
							}
							
				      		mvno.ui.createForm("FORM2");
	                        PAGE.FORM2.clear();
	                        
	                        PAGE.FORM2.setItemValue('inputMod', 'R' );
	                        PAGE.FORM2.setItemValue("orgnId", data.orgnId);
	                        PAGE.FORM2.setItemValue("orgnNm", data.orgnNm);
	                        PAGE.FORM2.setItemValue("startDate", data.startDate);
	                        PAGE.FORM2.setItemValue("endDate", data.endDate);
	                        
	                        PAGE.FORM2.enableItem("custName");
	                        PAGE.FORM2.enableItem("telNo");
	                        
	                        PAGE.FORM2.disableItem("fileDown1");
	                        PAGE.FORM2.disableItem("fileDel1");
	                        PAGE.FORM2.disableItem("fileDown2");
	                        PAGE.FORM2.disableItem("fileDel2");
	                        PAGE.FORM2.disableItem("fileDown3");
	                        PAGE.FORM2.disableItem("fileDel3");
	                        PAGE.FORM2.disableItem("fileDown4");
	                        PAGE.FORM2.disableItem("fileDel4");
	                        PAGE.FORM2.disableItem("fileDown5");
	                        PAGE.FORM2.disableItem("fileDel5");
	                        
	                        PAGE.FORM2.clearChanged();
	                        
	                        var uploader = PAGE.FORM2.getUploader("file_upload1");
	                        uploader.revive();
	                        
	                        PAGE.FORM2.showItem("file_upload1");
	                        PAGE.FORM2.setUserData("file_upload1","limitCount",fileLimitCnt);
	                        PAGE.FORM2.setUserData("file_upload1","limitsize",1000);
	                        PAGE.FORM2.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
	                        	
	                        mvno.ui.popupWindowById("FORM2", "등록", 730, 620, {
		                           onClose2: function(win) {
		                        	   uploader.reset();
		                           }
	                        });   

                        	break;
	                        
						case "btnMod":
		                    
							fileLimitCnt = 5;
	                   	   
							if (PAGE.GRID1.getSelectedRowId() == null)
	                   	   	{
								mvno.ui.alert("ECMN0002");
								return;
							}
	
	                   		mvno.ui.createForm("FORM2");
	                    	PAGE.FORM2.clear();
	                    	
							var data = PAGE.GRID1.getSelectedRowData();
							PAGE.FORM2.setFormData(data);
							
							mvno.cmn.ajax(ROOT + "/rcp/credit/getFile.json", PAGE.GRID1.getSelectedRowData(), function(resultData) {
								var totCnt = resultData.result.fileTotalCnt;
								
								PAGE.FORM2.setItemValue('inputMod', 'M' );
		                        PAGE.FORM2.setItemValue("orgnId", data.orgCode);
		                        PAGE.FORM2.setItemValue("orgnNm", data.orgName);
		                        
		                        PAGE.FORM2.disableItem("custName");
		                        PAGE.FORM2.disableItem("telNo");
								
								PAGE.FORM2.setUserData("file_upload1","limitCount",fileLimitCnt-parseInt(totCnt));
								PAGE.FORM2.setUserData("file_upload1","limitsize",1000);
								PAGE.FORM2.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
		                           	
								if(parseInt(totCnt) == fileLimitCnt){
									PAGE.FORM2.hideItem("file_upload1");
								}
								else
								{
									PAGE.FORM2.showItem("file_upload1");
								}
		                           	
								if(!mvno.cmn.isEmpty(resultData.result.fileNm)){
		                        	   
									PAGE.FORM2.setItemValue("fileName1", resultData.result.fileNm);
									PAGE.FORM2.setItemValue("fileId1", resultData.result.fileId); 
									PAGE.FORM2.enableItem("fileDown1");
									PAGE.FORM2.enableItem("fileDel1");
								} else {
									PAGE.FORM2.disableItem("fileDown1");
									PAGE.FORM2.disableItem("fileDel1");
								}
		                    	   
		                        if(!mvno.cmn.isEmpty(resultData.result.fileNm1)){
		                            PAGE.FORM2.setItemValue("fileName2", resultData.result.fileNm1);
		                            PAGE.FORM2.setItemValue("fileId2", resultData.result.fileId1);
		                            PAGE.FORM2.enableItem("fileDown2");
		                            PAGE.FORM2.enableItem("fileDel2");
		                        } else {
		                            PAGE.FORM2.disableItem("fileDown2");
		                            PAGE.FORM2.disableItem("fileDel2");
		                        }
		                           
		                        if(!mvno.cmn.isEmpty(resultData.result.fileNm2)){
		                            PAGE.FORM2.setItemValue("fileName3", resultData.result.fileNm2);
		                            PAGE.FORM2.setItemValue("fileId3", resultData.result.fileId2);
		                            PAGE.FORM2.enableItem("fileDown3");
		                            PAGE.FORM2.enableItem("fileDel3");
		                        } else {
		                            PAGE.FORM2.disableItem("fileDown3");
		                            PAGE.FORM2.disableItem("fileDel3");
		                        }
		                           
		                        if(!mvno.cmn.isEmpty(resultData.result.fileNm3)){
		                            PAGE.FORM2.setItemValue("fileName4", resultData.result.fileNm3);
		                            PAGE.FORM2.setItemValue("fileId4", resultData.result.fileId3);
		                            PAGE.FORM2.enableItem("fileDown4");
		                            PAGE.FORM2.enableItem("fileDel4");
		                        } else {
		                            PAGE.FORM2.disableItem("fileDown4");
		                            PAGE.FORM2.disableItem("fileDel4");
		                        }
		                           
		                        if(!mvno.cmn.isEmpty(resultData.result.fileNm4)){
		                            PAGE.FORM2.setItemValue("fileName5", resultData.result.fileNm4);
		                            PAGE.FORM2.setItemValue("fileId5", resultData.result.fileId4);
		                            PAGE.FORM2.enableItem("fileDown5");
		                            PAGE.FORM2.enableItem("fileDel5");
		                        } else {
		                            PAGE.FORM2.disableItem("fileDown5");
		                            PAGE.FORM2.disableItem("fileDel5");
		                        }
							});
							
							var uploader = PAGE.FORM2.getUploader("file_upload1");
	                        uploader.revive();
	                        PAGE.FORM2.clearChanged();
	                          
							mvno.ui.popupWindowById("FORM2", "수정", 730, 620, {
		                           onClose2: function(win) {
		                        	   uploader.reset();
		                           }
	                     	});   
	                          
							break;
					}
				}
			},
			
			FORM2 : {
				items : [
					{type: "fieldset", label: "기본정보", inputWidth:650, list:[
						{type:"settings", position:"label-left", labelWidth:90},
						{type: "block", list: [
							{type: "input", name: "orgnId", label: "조직ID/명", width: 120, validate:"NotEmpty", maxLength: 10, disabled:true},
				            {type: "newcolumn"},
				            {type: "input", name: "orgnNm", width: 120, offsetLeft: 10, validate: "NotEmpty", maxLength: 30, disabled:true}
						]},
						{type: "block", list: [
							{type: "input", name: "custName", label: "고객명", width: 120, validate:"NotEmpty", maxLength: 10, required:true }
						]},
						{type: "block", list: [
							{type: "input", name: "telNo", label: "연락처", width: 120, validate: "NotEmpty", maxLength: 11, required:true}
						]}
					]},
					{type: "fieldset", label: "파일정보", inputWidth:650, list:[
						{type:"settings", position:"label-left", labelWidth:90},
						{type:"block", list:[
							{type:"hidden",name:"fileId1"},
							{type : "input" , name:"fileName1", label:"파일명", width:370, disabled:true},
							{type : "newcolumn" },
							{type : "button" , name:"fileDown1", value: "다운로드"},
							{type : "newcolumn" },
							{type : "button" , name:"fileDel1", value: "삭제"}
						]},
						{type:"block", list:[
							{type:"hidden",name:"fileId2"},
				  			{type : "input" , name:"fileName2", label:"파일명", width:370, disabled:true},
				  			{type : "newcolumn" },
				  			{type : "button" , name:"fileDown2", value: "다운로드"},
				  			{type : "newcolumn" },
				  			{type : "button" , name:"fileDel2", value: "삭제"}
						]},
						{type:"block", list:[
							{type:"hidden",name:"fileId3"},
				  			{type : "input" , name:"fileName3", label:"파일명", width:370, disabled:true},
				  			{type : "newcolumn" },
				  			{type : "button" , name:"fileDown3", value: "다운로드"},
				  			{type : "newcolumn" },
				  			{type : "button" , name:"fileDel3", value: "삭제"}
						]},
						{type:"block", list:[
							{type:"hidden",name:"fileId4"},
				  			{type : "input" , name:"fileName4", label:"파일명", width:370, disabled:true},
				  			{type : "newcolumn" },
				  			{type : "button" , name:"fileDown4", value: "다운로드"},
				  			{type : "newcolumn" },
				  			{type : "button" , name:"fileDel4", value: "삭제"}
						]},
						{type:"block", list:[
				  			{type:"hidden",name:"fileId5"},
				  			{type : "input" , name:"fileName5", label:"파일명", width:370, disabled:true},
				  			{type : "newcolumn" },
				  			{type : "button" , name:"fileDown5", value: "다운로드"},
				  			{type : "newcolumn" },
				  			{type : "button" , name:"fileDel5", value: "삭제"}
						]},
						{type: "block", list: [
							{type: "upload", label:"파일업로드" ,name: "file_upload1", width:535, url: "/rcp/credit/fileUpLoad.do" ,autoStart: false, userdata: { limitCount : 5, limitsize: 1000, delUrl:"/org/common/deleteFile2.json"} }
						]},
						{type:"hidden",name:"inputMod"},
						{type:"hidden",name:"startDate"},
						{type:"hidden",name:"endDate"}
					]}
				],
				buttons : {
			    	center : {
						btnSave : { label : "저장" },
			            btnClose : { label: "닫기" }
			        }
				},
				onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				var fileLimitCnt = 5;

					switch (btnId) {
						case "btnSave":
							
							if ( form.getItemValue('inputMod') == "R"){	// 등록 처리
	                            mvno.cmn.ajax(ROOT + "/rcp/credit/creditInsert.json", form, function(result) {
	                                mvno.ui.closeWindowById(form, true);  
	                                mvno.ui.notify("NCMN0001");
	                                
	                                PAGE.GRID1.refresh();
	                            });
	                        }
							else if ( form.getItemValue('inputMod') == "M") { // 수정 처리
	                            mvno.cmn.ajax(ROOT + "/rcp/credit/creditUpdate.json", form, function(result) {
	                                mvno.ui.closeWindowById(form, true);  
	                                mvno.ui.notify("NCMN0002");
									
	                                PAGE.GRID1.refresh();
	                            });
	                        }
							break;	
							
						case "fileDown1" :
							mvno.cmn.download('/cmn/board/downFile.json?fileId=' + PAGE.FORM2.getItemValue("fileId1"));
							break;
	                        
						case "fileDel1" :
	                        mvno.cmn.ajax(ROOT + "/rcp/credit/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId1"),creditId:PAGE.GRID1.getSelectedRowData().creditId} , function(resultData) {
	                        
	                           	mvno.ui.notify("NCMN0014");
	                          	PAGE.FORM2.setItemValue("fileId1", "");
	                           	PAGE.FORM2.setItemValue("fileName1", "");
	                        	PAGE.FORM2.disableItem("fileDown1");
	                        	PAGE.FORM2.disableItem("fileDel1");
	                        	PAGE.FORM2.showItem("file_upload1");
	                        	
	                        	PAGE.FORM2.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
	                            PAGE.FORM2.setUserData("file_upload1","limitsize",1000);
	                            PAGE.FORM2.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");

	                        });
	                        break;
	                           
						case "fileDown2" :
							mvno.cmn.download('/cmn/board/downFile.json?fileId=' + PAGE.FORM2.getItemValue("fileId2"));
							break;
	                        
						case "fileDel2" :
	                        mvno.cmn.ajax(ROOT + "/rcp/credit/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId2"),creditId:PAGE.GRID1.getSelectedRowData().creditId} , function(resultData) {
	                          	mvno.ui.notify("NCMN0014");
	                           	PAGE.FORM2.setItemValue("fileId2", "");
	                           	PAGE.FORM2.setItemValue("fileName2", "");
	                           	PAGE.FORM2.disableItem("fileDown2");
	                        	PAGE.FORM2.disableItem("fileDel2");
	                        	PAGE.FORM2.showItem("file_upload1");
	                        	
	                        	PAGE.FORM2.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
	                            PAGE.FORM2.setUserData("file_upload1","limitsize",1000);
	                            PAGE.FORM2.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
	                        });
	                        break;
	                           
						case "fileDown3" :
							mvno.cmn.download('/cmn/board/downFile.json?fileId=' + PAGE.FORM2.getItemValue("fileId3"));
							break;
	                        
						case "fileDel3" :
	                        mvno.cmn.ajax(ROOT + "/rcp/credit/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId3"),creditId:PAGE.GRID1.getSelectedRowData().creditId} , function(resultData) {
	                           	mvno.ui.notify("NCMN0014");
	                           	PAGE.FORM2.setItemValue("fileId3", "");
	                           	PAGE.FORM2.setItemValue("fileName3", "");
	                           	PAGE.FORM2.disableItem("fileDown3");
	                        	PAGE.FORM2.disableItem("fileDel3");
	                        	PAGE.FORM2.showItem("file_upload1");
	                        	
	                        	PAGE.FORM2.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
	                            PAGE.FORM2.setUserData("file_upload1","limitsize",1000);
	                            PAGE.FORM2.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
	                        });
	                        break;
						
						case "fileDown4" :
							mvno.cmn.download('/cmn/board/downFile.json?fileId=' + PAGE.FORM2.getItemValue("fileId4"));
							break;
	                        
						case "fileDel4" :
	                        mvno.cmn.ajax(ROOT + "/rcp/credit/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId4"),creditId:PAGE.GRID1.getSelectedRowData().creditId} , function(resultData) {
	                           	mvno.ui.notify("NCMN0014");
	                           	PAGE.FORM2.setItemValue("fileId4", "");
	                           	PAGE.FORM2.setItemValue("fileName4", "");
	                           	PAGE.FORM2.disableItem("fileDown4");
	                        	PAGE.FORM2.disableItem("fileDel4");
	                        	PAGE.FORM2.showItem("file_upload1");
	                        	
	                        	PAGE.FORM2.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
	                            PAGE.FORM2.setUserData("file_upload1","limitsize",1000);
	                            PAGE.FORM2.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
	                        });
	                        break;
	                        
						case "fileDown5" :
							mvno.cmn.download('/cmn/board/downFile.json?fileId=' + PAGE.FORM2.getItemValue("fileId5"));
							break;
	                        
						case "fileDel5" :
	                        mvno.cmn.ajax(ROOT + "/rcp/credit/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId5"),creditId:PAGE.GRID1.getSelectedRowData().creditId} , function(resultData) {
	                           	mvno.ui.notify("NCMN0014");
	                           	PAGE.FORM2.setItemValue("fileId5", "");
	                           	PAGE.FORM2.setItemValue("fileName5", "");
	                           	PAGE.FORM2.disableItem("fileDown5");
	                        	PAGE.FORM2.disableItem("fileDel5");
	                        	PAGE.FORM2.showItem("file_upload1");
	                        	
	                        	PAGE.FORM2.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
	                            PAGE.FORM2.setUserData("file_upload1","limitsize",1000);
	                            PAGE.FORM2.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
	                        });
	                        break;
	                        
						case "btnClose":
							mvno.ui.closeWindowById(form);
							break;
					}
	           }
			},
			
			FORM3 : {
				items : [
					{type: "fieldset", label: "기본정보", inputWidth:650, list:[
						{type:"settings", position:"label-left", labelWidth:90},
						{type: "block", list: [
							{type: "input", name: "orgnId", label: "조직ID/명", width: 120, validate:"NotEmpty", maxLength: 10, disabled:true},
				            {type: "newcolumn"},
				            {type: "input", name: "orgnNm", width: 120, offsetLeft: 10, validate: "NotEmpty", maxLength: 30, disabled:true}
						]},
						{type: "block", list: [
							{type: "input", name: "custName", label: "고객명", width: 120, validate:"NotEmpty", maxLength: 10, disabled:true}
						]},
						{type: "block", list: [
							{type: "input", name: "telNo", label: "연락처", width: 120, validate: "NotEmpty", maxLength: 11, disabled:true}
						]}
					]},
					{type: "fieldset", label: "파일정보", inputWidth:650, list:[
						{type:"settings", position:"label-left", labelWidth:90},
						{type:"block", list:[
							{type:"hidden",name:"fileId1"},
							{type : "input" , name:"fileName1", label:"파일명", width:370, disabled:true},
							{type : "newcolumn" },
							{type : "button" , name:"fileDown1", value: "다운로드"}
						]},
						{type:"block", list:[
							{type:"hidden",name:"fileId2"},
				  			{type : "input" , name:"fileName2", label:"파일명", width:370, disabled:true},
				  			{type : "newcolumn" },
				  			{type : "button" , name:"fileDown2", value: "다운로드"}
						]},
						{type:"block", list:[
							{type:"hidden",name:"fileId3"},
				  			{type : "input" , name:"fileName3", label:"파일명", width:370, disabled:true},
				  			{type : "newcolumn" },
				  			{type : "button" , name:"fileDown3", value: "다운로드"}
						]},
						{type:"block", list:[
							{type:"hidden",name:"fileId4"},
				  			{type : "input" , name:"fileName4", label:"파일명", width:370, disabled:true},
				  			{type : "newcolumn" },
				  			{type : "button" , name:"fileDown4", value: "다운로드"}
						]},
						{type:"block", list:[
				  			{type:"hidden",name:"fileId5"},
				  			{type : "input" , name:"fileName5", label:"파일명", width:370, disabled:true},
				  			{type : "newcolumn" },
				  			{type : "button" , name:"fileDown5", value: "다운로드"}
						]}
					]}
				],
				buttons : {
			    	center : {
			            btnClose : { label: "닫기" }
			        }
				},
				onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "fileDown1" :
							mvno.cmn.download('/cmn/board/downFile.json?fileId=' + PAGE.FORM3.getItemValue("fileId1"));
							break;
						case "fileDown2" :
							mvno.cmn.download('/cmn/board/downFile.json?fileId=' + PAGE.FORM3.getItemValue("fileId2"));
							break;
						case "fileDown3" :
							mvno.cmn.download('/cmn/board/downFile.json?fileId=' + PAGE.FORM3.getItemValue("fileId3"));
							break;
						case "fileDown4" :
							mvno.cmn.download('/cmn/board/downFile.json?fileId=' + PAGE.FORM3.getItemValue("fileId4"));
							break;
						case "fileDown5" :
							mvno.cmn.download('/cmn/board/downFile.json?fileId=' + PAGE.FORM3.getItemValue("fileId5"));
							break;
						case "btnClose" :
							mvno.ui.closeWindowById(form);
							break;
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
				mvno.ui.createGrid("GRID1");

				if("${info.userOrgnTypeCd}" != "10") {
					
					PAGE.FORM1.setItemValue("orgnId", "${info.orgnId}");
					PAGE.FORM1.setItemValue("orgnNm", "${info.orgnNm}");
					
					PAGE.FORM1.disableItem("btnOrgFind");
					PAGE.FORM1.setReadonly("orgnId",true);
					PAGE.FORM1.setReadonly("orgnNm", true);
				}
			}
		};
	</script>