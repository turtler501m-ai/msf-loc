<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1050_notice.jsp
	 * @Description : 공지사항 관리(조회, 등록, 수정) 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.10.29     임지혜           최초 생성
	 * @
	 * @author : 임지혜
	 * @Create Date : 2014. 10. 29.
	 */
%>

	<!-- 화면 배치 -->
	<div id="FORM0" class="section-search"></div>
	<div id="FORM1" class="section-full"></div>
	<div id="FORM2" class="section-full"></div>
	<div id="GRID1" class="section-full" ></div>
	
    <!-- Script -->
    <script type="text/javascript">
    
	var vRules = {
			NotEmpty: {rule: "NotEmpty", note: "NotEmpty validation is set"}
	};
    
    var DHX = {

		GRID1 : {
			css : {
				height : "575px"
			},

//			title : "공지사항",
			header : "번호,제목,첨부,게시일자,소속,작성자,NOTICE_ID", 
			columnIds : "SEQ,LIST_TITLE,FILE_YN,POST_STRT_DT,REGST_ORGN_NM,REGST_USR_NM,NOTICE_ID",
			colAlign : "center,left,center,center,center,center,center",
			colTypes : "ro,ro,ch,roXd,ro,ro,ro",
			widths : "80,480,30,106,120,120,30",
			colSorting : "str,str,str,str,str,str,str",
			hiddenColumns : "6",
			paging : true,
			pageSize : 20,
			
			
			buttons : {
				right : {
					btnReg : { label : "답글등록" },
					btnMod : { label : "답글수정" },
					btnDel : { label : "답글삭제" },
					btnRead : { label : "상세조회" }
				}
			},
			checkSelectedButtons : ["btnRead","btnReg","btnMod","btnDel"],
 			onRowDblClicked : function(rowId, celInd) {
 				this.callEvent('onButtonClick', ['btnRead']); // 읽기 폼 호출
				
             },
//			checkSelectedButtons : [ "btnMod"],
			onButtonClick : function(btnId, selectedData) {

        		var fileLimitCnt = 3;
        		
				switch (btnId) {

                    case "btnReg":
                    	if(selectedData.P_NOTICE_ID != null && selectedData.P_NOTICE_ID != ''){
   							mvno.ui.alert("답글에 답글를 등록할 수 없습니다.");
   							return;
                    		
                    	}

                        mvno.ui.createForm("FORM1");
                        PAGE.FORM1.clear();
                        
                        PAGE.FORM1.setItemValue('inputMod', 'R' );
                        PAGE.FORM1.setItemValue('POST_STRT_DT', '${startDate}' );
                        //PAGE.FORM1.setItemValue('POST_END_DT', '${endDate}' );
                        
                        PAGE.FORM1.disableItem("fileDown1");
                        PAGE.FORM1.disableItem("fileDel1");
                        PAGE.FORM1.disableItem("fileDown2");
                        PAGE.FORM1.disableItem("fileDel2");
                        PAGE.FORM1.disableItem("fileDown3");
                        PAGE.FORM1.disableItem("fileDel3");
                        
                        PAGE.FORM1.clearChanged();
                                                   
                        var uploader = PAGE.FORM1.getUploader("file_upload1");
                        uploader.revive();
                        
                        PAGE.FORM1.showItem("file_upload1");
                        PAGE.FORM1.setUserData("file_upload1","limitCount",fileLimitCnt);
                        PAGE.FORM1.setUserData("file_upload1","limitsize",1000);
                        PAGE.FORM1.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");

                        PAGE.FORM1.setItemValue('P_NOTICE_ID', selectedData.NOTICE_ID );
                        	
                        mvno.ui.popupWindowById("FORM1", "1:1문의 답글 등록", 730, 620, {

	                           onClose2: function(win) {
	                        	   uploader.reset();
	                           }
                        });   

                        break;
                        
                    case "btnMod":

                    	if(selectedData.P_NOTICE_ID == null || selectedData.P_NOTICE_ID == ''){
   							mvno.ui.alert("답글만 수정할 수 있습니다.");
   							return;
                    		
                    	}
                        mvno.ui.createForm("FORM1");
                        PAGE.FORM1.clear();
                        
                 	   	var data = PAGE.GRID1.getSelectedRowData();
                        PAGE.FORM1.setFormData(data);
                        

                        PAGE.FORM1.setItemValue('inputMod', 'M' );
                        PAGE.FORM1.setItemValue('P_NOTICE_ID', data.NOTICE_ID );
                        
                        
                        mvno.cmn.ajax(ROOT + "/m2m/usimReturn/getNoticeFile.json", PAGE.GRID1.getSelectedRowData(), 
                        		function(resultData) {
                        	
	                        var totCnt = resultData.result.fileTotalCnt;
	                        
                            PAGE.FORM1.setUserData("file_upload1","limitCount",fileLimitCnt-parseInt(totCnt));
                            PAGE.FORM1.setUserData("file_upload1","limitsize",1000);
                            PAGE.FORM1.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
	                           	
//                                     PAGE.FORM1.hideItem("file_upload1");
                            
                            if(parseInt(totCnt) == fileLimitCnt){
                                PAGE.FORM1.hideItem("file_upload1");
	                           	}
	                           	else
	                           	{
	                           		PAGE.FORM1.showItem("file_upload1");
	                           	}
	                               

	                           if(!mvno.cmn.isEmpty(resultData.result.fileNm)){
	                        	   
		                           	PAGE.FORM1.setItemValue("fileName1", resultData.result.fileNm);
		                           	PAGE.FORM1.setItemValue("fileId1", resultData.result.fileId); 
		                           	PAGE.FORM1.enableItem("fileDown1");
	                               PAGE.FORM1.enableItem("fileDel1");
	                           } else {
		                           	PAGE.FORM1.disableItem("fileDown1");
		                           	PAGE.FORM1.disableItem("fileDel1");
	                           }
	                    	   
	                           if(!mvno.cmn.isEmpty(resultData.result.fileNm1)){
	                               PAGE.FORM1.setItemValue("fileName2", resultData.result.fileNm1);
	                               PAGE.FORM1.setItemValue("fileId2", resultData.result.fileId1);
	                               PAGE.FORM1.enableItem("fileDown2");
	                               PAGE.FORM1.enableItem("fileDel2");
	                           } else {
	                               PAGE.FORM1.disableItem("fileDown2");
	                               PAGE.FORM1.disableItem("fileDel2");
	                           }
	                           
	                           if(!mvno.cmn.isEmpty(resultData.result.fileNm2)){
	                               PAGE.FORM1.setItemValue("fileName3", resultData.result.fileNm2);
	                               PAGE.FORM1.setItemValue("fileId3", resultData.result.fileId2);
	                               PAGE.FORM1.enableItem("fileDown3");
	                               PAGE.FORM1.enableItem("fileDel3");
	                           } else {
	                               PAGE.FORM1.disableItem("fileDown3");
	                               PAGE.FORM1.disableItem("fileDel3");
	                           }
                            
                        });
                        
                        var uploader = PAGE.FORM1.getUploader("file_upload1");
                        uploader.revive();
                        PAGE.FORM1.clearChanged();
                        
                        mvno.ui.popupWindowById("FORM1", "1:1문의 답글 수정", 730, 620, {
                            onClose2: function(win) {
                         	   uploader.reset();
                            }
                        });  
                        
                        break;
                    	    
                    case "btnDel":
                    	if(selectedData.P_NOTICE_ID == null || selectedData.P_NOTICE_ID == ''){
   							mvno.ui.alert("답글만 삭제할 수 있습니다.");
   							return;
                    		
                    	}
                    	
   						mvno.cmn.ajax4confirm("CCMN0003", ROOT + "/m2m/usimReturn/noticeDelete.json"
   								, selectedData , function(result) {
   							mvno.ui.notify("NCMN0003");
   							PAGE.GRID1.refresh();
   						});

                        break;

                    case "btnRead":
                   	 
                   	   mvno.ui.createForm("FORM2");
                       PAGE.FORM2.clear();
                       PAGE.FORM2.setFormData(PAGE.GRID1.getSelectedRowData());

                          
                   	   fileLimitCnt = 3;
                   	   
                   	   if (PAGE.GRID1.getSelectedRowId() == null)
                   	   {
                   		   	mvno.ui.alert("ECMN0002");
  						   	return;
                   	   }

                       mvno.ui.createForm("FORM2");
                       PAGE.FORM2.clear();
                          
                   	   var data = PAGE.GRID1.getSelectedRowData();
                       PAGE.FORM2.setFormData(data);
                          
                          
                       mvno.cmn.ajax(ROOT + "/m2m/usimReturn/getNoticeFile.json", PAGE.GRID1.getSelectedRowData(), function(resultData) {
	                       var totCnt = resultData.result.fileTotalCnt;
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
	                           
                       });
                          mvno.ui.popupWindowById("FORM2", "1:1문의조회", 730, 480);
                          
                          break;
                   }
            }
		},
		
		FORM0 : {
    		items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
				,{type:"block", labelWidth:30 , list:[    
					{type : "input", label : "제목", name : "TITLE" , width:280, offsetLeft:10, offsetLeft : 10, maxLength:60}
					,{ type : "newcolumn" , offset:20} 
					,{type : "input", label : "작성자", name : "REGST_USR_NM", width:100, offsetLeft:0 , offsetLeft : 10, maxLength:20}
					,{type : "newcolumn" , offset:20} 
//					,{type : "input", label : "작성부서", name : "REGST_ORGN_NM", width:80, offsetLeft:10 , offsetLeft : 10, maxLength:20}
//					,{type : "newcolumn" , offset:20} 
					,{ type : "calendar" , name:"SRCH_STRT_DT", label:"게시일자", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:50, width:100, calendarPosition: 'bottom' } 
//						, value: "${startDate}"} // 조회 부분에 자동으로 날짜 입력, 게시 일자와 맞지 않는 경우 헷갈림.
					,{ type : "newcolumn" }
					,{ type : "calendar" , name:"SRCH_END_DT", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", label:"~", labelWidth:30, labelAlign:"center", width:100 , calendarPosition: 'bottom'} 
//						, value: "${startDate}"}// 조회 부분에 자동으로 날짜 입력, 게시 일자와 맞지 않는 경우 헷갈림.
					]}
				,{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
			],
		
		  onButtonClick : function(btnId) {
		
		      var form = this;
		
		      switch (btnId) {
		
		          case "btnSearch":
	        		
	  				var url = ROOT + "/m2m/usimReturn/noticeList.json";
	  				PAGE.GRID1.list(url, form, { callback : gridOnLoad });

					break; 
		
				}
			}
			
			,onValidateMore : function (data){
		         if(data.POST_STRT_DT > data.POST_END_DT){
		             this.pushError("data.POST_STRT_DT","게시일자",["ICMN0004"]);
		         }
		         
		         if(data.POST_STRT_DT < '${startDate}' && PAGE.FORM1.getItemValue("inputMod") == 'R'){
		             this.pushError("data.POST_STRT_DT","게시일자",["ICMN0013"]);
		         }
		         
		         // 검색조건 날짜 검증
		         if(data.SRCH_STRT_DT > data.SRCH_END_DT && data.SRCH_END_DT != ''){
		             this.pushError("data.SRCH_STRT_DT","게시일자",["ICMN0004"]);
		         }
		         
		         
		     }
		},

		FORM1 : {
//			title : """,
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
				
				,{type:"input", label:"제목", width:500, name:"TITLE", maxLength:60, required:true}
				,{type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
				
				,{type:"block", list:[
					{type : "calendar" , name:"POST_STRT_DT", label:"게시일자", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", width:100
						, value: "${startDate}", calendarPosition: 'bottom'}
				
				]}
		
				,{type:"input",name:"CONTENTS",label:"내용",width:500, maxLength:1300, rows:15, required:true}
				,{type:"hidden",name:"inputMod"}
				,{type:"hidden",name:"P_NOTICE_ID"}
				
				
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
//					{type: "upload", label:"파일업로드" ,name: "file_upload1", width:535, url: "/m2m/usimReturn/fileUpLoad.do" ,autoStart: false, userdata: { limitCount : 3, limitsize: 1000, delUrl:"/org/common/deleteFile2.json"} }
					{type: "upload", label:"파일업로드" ,name: "file_upload1", width:535, url: "/m2m/usimReturn/fileUpLoad.do" ,autoStart: false, userdata: { limitCount : 3, limitsize: 1000, delUrl:"/org/common/deleteFile2.json"} }
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
				var fileLimitCnt = 3;

				switch (btnId) {

					case "btnSave":
						if(form.getItemValue('TITLE').indexOf("<") < 0 && form.getItemValue('TITLE').indexOf(">") < 0 && form.getItemValue('CONTENTS').indexOf("<") < 0 && form.getItemValue('CONTENTS').indexOf(">") < 0) { // 재목, 본문 특수문자(<, >) 체크
							if ( form.getItemValue('inputMod') == "R"){
	                            mvno.cmn.ajax(ROOT + "/m2m/usimReturn/noticeInsert.json", form, function(result) {
	                                mvno.ui.closeWindowById(form, true);  
	                                mvno.ui.notify("NCMN0001");
	                                
	                                PAGE.GRID1.refresh();
	                                
	                            });
	                        }
							else if ( form.getItemValue('inputMod') == "M") { // 수정 처리
	                            mvno.cmn.ajax(ROOT + "/m2m/usimReturn/noticeUpdate.json", form, function(result) {
	                                mvno.ui.closeWindowById(form, true);  
	                                mvno.ui.notify("NCMN0002");
	                                PAGE.GRID1.refresh();
	                                
	                            });
	                            
	                        }
						} else {
							mvno.ui.alert("< 또는 > 특수문자는 사용이 불가능 합니다.");
						}
						break;	
					case "fileDown1" :
						mvno.cmn.download('/m2m/usimReturn/downFile.json?fileId=' + PAGE.FORM1.getItemValue("fileId1"));
						break;
                        
					case "fileDel1" :
                        mvno.cmn.ajax(ROOT + "/m2m/usimReturn/deleteFile.json",  {fileId:PAGE.FORM1.getItemValue("fileId1"),noticeId:PAGE.GRID1.getSelectedRowData().NOTICE_ID} , function(resultData) {
//						mvno.cmn.ajax(ROOT + "/m2m/usimReturn/deleteFile.json",  form , function(resultData) {
                        
                           mvno.ui.notify("NCMN0014");
                           PAGE.FORM1.setItemValue("fileId1", "");
                           PAGE.FORM1.setItemValue("fileName1", "");
                           PAGE.FORM1.disableItem("fileDown1");
                        	PAGE.FORM1.disableItem("fileDel1");
                        	PAGE.FORM1.showItem("file_upload1");
                        	
                        	PAGE.FORM1.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
                            PAGE.FORM1.setUserData("file_upload1","limitsize",1000);
                            PAGE.FORM1.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");

                        });
                           break;
                           
					case "fileDown2" :
						mvno.cmn.download('/m2m/usimReturn/downFile.json?fileId=' + PAGE.FORM1.getItemValue("fileId2"));
						break;
                        
					case "fileDel2" :
                        mvno.cmn.ajax(ROOT + "/m2m/usimReturn/deleteFile.json",  {fileId:PAGE.FORM1.getItemValue("fileId2"),noticeId:PAGE.GRID1.getSelectedRowData().NOTICE_ID} , function(resultData) {
//						mvno.cmn.ajax(ROOT + "/m2m/usimReturn/deleteFile.json",  form , function(resultData) {
                           mvno.ui.notify("NCMN0014");
                           PAGE.FORM1.setItemValue("fileId2", "");
                           PAGE.FORM1.setItemValue("fileName2", "");
                           PAGE.FORM1.disableItem("fileDown2");
                        	PAGE.FORM1.disableItem("fileDel2");
                        	PAGE.FORM1.showItem("file_upload1");
                        	
                        	PAGE.FORM1.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
                            PAGE.FORM1.setUserData("file_upload1","limitsize",1000);
                            PAGE.FORM1.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
                        });
                           break;
                           
					case "fileDown3" :
						mvno.cmn.download('/m2m/usimReturn/downFile.json?fileId=' + PAGE.FORM1.getItemValue("fileId3"));
						break;
                        
					case "fileDel3" :
                        mvno.cmn.ajax(ROOT + "/m2m/usimReturn/deleteFile.json",  {fileId:PAGE.FORM1.getItemValue("fileId3"),noticeId:PAGE.GRID1.getSelectedRowData().NOTICE_ID} , function(resultData) {
//						mvno.cmn.ajax(ROOT + "/m2m/usimReturn/deleteFile.json",  form , function(resultData) {
                           mvno.ui.notify("NCMN0014");
                           PAGE.FORM1.setItemValue("fileId3", "");
                           PAGE.FORM1.setItemValue("fileName3", "");
                           PAGE.FORM1.disableItem("fileDown3");
                        	PAGE.FORM1.disableItem("fileDel3");
                        	PAGE.FORM1.showItem("file_upload1");
                        	
                        	PAGE.FORM1.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
                            PAGE.FORM1.setUserData("file_upload1","limitsize",1000);
                            PAGE.FORM1.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
                        });
                           break;
						
					case "btnClose":
						
						mvno.ui.closeWindowById(form);
						break;
				}
			}
			,onChange : function(name, value){
           
			}
			
			,onValidateMore : function (data){

			}
		}
		
		,FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
				
				,{type:"input", label:"제목", width:500, name:"TITLE", maxLength:70, readonly:true}
				,{type:"settings", position:"label-left", labelAlign:"left", labelWidth:90, blockOffset:0}
				
				,{type:"block", list:[
					{type : "calendar" , name:"POST_STRT_DT", label:"게시일자", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", width:100
						, value: "${startDate}", calendarPosition: 'bottom', disabled:true}
				]}
				
				,{type:"input",name:"CONTENTS",label:"내용",width:500, rows:15, maxLength:1300, readonly:true}
				,{type:"hidden",name:"inputMod"}
				,{type:"hidden",name:"P_NOTICE_ID"}
				
				,{type:"block", list:[
				    {type:"hidden",name:"fileId1"}
					,{type : "input" , name:"fileName1", label:"파일명", width:420, readonly:true}
					,{type : "newcolumn" }
					,{type : "button" , name:"fileDown1", value: "다운로드"}
				]}
				,{type:"block", list:[
  				    {type:"hidden",name:"fileId2"}
  					,{type : "input" , name:"fileName2", label:"파일명", width:420, readonly:true}
  					,{type : "newcolumn" }
  					,{type : "button" , name:"fileDown2", value: "다운로드"}
				]}
				,{type:"block", list:[
  				    {type:"hidden",name:"fileId3"}
  					,{type : "input" , name:"fileName3", label:"파일명", width:420, readonly:true}
  					,{type : "newcolumn" }
  					,{type : "button" , name:"fileDown3", value: "다운로드"}
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
						mvno.cmn.download('/m2m/usimReturn/downFile.json?fileId=' + PAGE.FORM2.getItemValue("fileId1"));
						break;
                           
					case "fileDown2" :
						mvno.cmn.download('/m2m/usimReturn/downFile.json?fileId=' + PAGE.FORM2.getItemValue("fileId2"));
						break;
                           
					case "fileDown3" :
						mvno.cmn.download('/m2m/usimReturn/downFile.json?fileId=' + PAGE.FORM2.getItemValue("fileId3"));
						break;
						
					case "btnClose":
						
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
			mvno.ui.createForm("FORM0");
			
			mvno.ui.createGrid("GRID1");
			var url = ROOT + "/m2m/usimReturn/noticeList.json";
			PAGE.GRID1.list(url, null, { callback : gridOnLoad });
	    }
	};
	
	function gridOnLoad  ()
	{
		PAGE.GRID1.forEachRow(function(id){
			PAGE.GRID1.cells(id,2).setDisabled(true);
			
			if (PAGE.GRID1.cellById(id,3).getValue().substring(0,8) == '${startDate}' )
			{
				PAGE.GRID1.setRowTextStyle(id,"color: blue");
				PAGE.GRID1.setRowTextBold(id);
			}
		});
	};
	
    </script>

	
	
