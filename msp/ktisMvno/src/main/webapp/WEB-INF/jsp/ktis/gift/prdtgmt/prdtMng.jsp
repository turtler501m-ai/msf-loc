<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>
<div id="GRID2" class="section"></div>


<div id="POPUP1">
	<div id="FORM2"  class="section-box"></div>
</div>

<div id="POPUP2" >
	<div id="FORM3"  class="section-box"></div>
 	<div id="IMAGE3" ></div> 
	<div id="FORM3btn"  class="section-full"></div>
</div>

<!-- 제품이미지 -->
<div id="POPUP_IMAGE" >
 	<div id="IMAGE5" style="text-align:center;"></div> 
	<div id="FORM5btn"  class="section-full"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	// 파일갯수
	var maxFileCnt = 2;
	var maxFileSize = 1000000;


	var today = new Date().format("yyyymmdd");
	
	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blackOffset:0}
					, {type:"block", list:[
										{type:"input", label:"제품ID", name:"prdtId", width:100, offsetLeft:10},
										{type:"newcolumn"},
										{type:"input", label:"제조사", name:"maker", width:120, offsetLeft:20},
										{type:"newcolumn"},
										{type:"input", label:"제품명", name:"prdtNm", width:120, offsetLeft:20},
										{type:"newcolumn"},
										{type:"select", label:"사용여부", name:"useYn", width:100, offsetLeft:20}
					                	]}

					//버튼 여러번 클릭 막기 변수
					, {type : 'hidden', name: "btnCount1", value:"0"} 
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}
					                				
					, {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							

							PAGE.GRID1.list(ROOT + "/gift/prdtMng/prdtMngList.json", form, {callback : function() {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}});

							PAGE.GRID2.clearAll();
							
							
							break;
					}
				},
				
				onChange : function(name, value) {//onChange START
					
				},
				onValidateMore : function (data){

				}
			},
			
			GRID1 : {
				css : {
					height : "300px"
				},
				title : "사은품",
				header : "제품ID,제조사,제품명,단가,이미지파일명,사용여부,이미지WEB_URL,등록자",	//8
				columnIds : "prdtId,maker,prdtNm,outUnitPric,imgFile,useYn,webUrl,usrNm",
				widths : "100,100,190,80,100,80,200,150",
				colAlign : "center,center,center,right,center,center,center,center",
				colTypes : "ro,ro,ro,ron,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str",
				//hiddenColumns:[10,11,12],
				paging : true,
				pageSize:20,
				buttons : {
					right : {
						btnReg : { label : "등록" },
						btnMod : { label : "수정" }
					},
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					}
				},
				checkSelectedButtons : ["btnMod"],
				onRowDblClicked : function(rowId, celInd) {
					// 수정버튼 누른것과 같이 처리?
					this.callEvent('onButtonClick', ['btnMod']);
				},
				onRowSelect : function (rowId, celInd) {
					
					PAGE.GRID2.list(ROOT + "/gift/prdtMng/prdtMngHist.json", this.getRowData(rowId));
					
				},
				onButtonClick : function(btnId, selectedData) {
					switch (btnId) {
						case "btnReg":
							mvno.ui.createForm("FORM2");
							
							PAGE.FORM2.clear();

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0242", orderBy:"etc1"}, PAGE.FORM2, "useYn"); // 사용여부
							
							//PAGE.FORM2.setItemValue("unitPricApplDttm",today);
							//PAGE.FORM2.setItemValue("unitPricExprDttm",today);

							
							mvno.ui.popupWindowById("POPUP1", "등록화면", 350, 450, {
								onClose: function(win) {
									if (! PAGE.FORM2.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
											win.closeForce();
									});
								}
							});
							
							break;
							
						case "btnMod":
							mvno.ui.createForm("FORM3");
							mvno.ui.createForm("FORM3btn");

 
							PAGE.FORM3.clear();

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0242", orderBy:"etc1"}, PAGE.FORM3, "useYn"); // 사용여부

							var data = selectedData;
							PAGE.FORM3.setFormData(data); 
							
							
							


                            var uploader = PAGE.FORM3.getUploader("file_upload1");

                       	    uploader.reset();
                            
							
							var fileName = "";

							// 첨부파일조회
							var fileLimitCnt = maxFileCnt;
							mvno.cmn.ajax(ROOT + "/gift/prdtMng/getImageFile.json", data, function(resultData) {
								
								var totCnt = resultData.result.fileTotalCnt;
																
								PAGE.FORM3.setUserData("file_upload1", "limitCount", fileLimitCnt-parseInt(totCnt));
								PAGE.FORM3.setUserData("file_upload1", "limitsize", maxFileSize);
								PAGE.FORM3.setUserData("file_upload1", "delUrl", "/org/common/deleteFile2.json");
								
								if(parseInt(totCnt) == 1){
									PAGE.FORM3.hideItem("file_upload1");
								}else{
									PAGE.FORM3.showItem("file_upload1");
								}
								//PAGE.FORM3.showItem("file_upload1");
								
								
								// 첫번째파일
								
								if(!mvno.cmn.isEmpty(resultData.result.fileNm)){
									fileName = resultData.result.fileNm;

									PAGE.FORM3.setItemValue("fileName1", fileName);
									PAGE.FORM3.setItemValue("fileId1", resultData.result.fileId);
									//PAGE.FORM3.enableItem("fileDown1");
									PAGE.FORM3.enableItem("fileDel1");

									fileName = PAGE.FORM3.getItemValue("fileName1","");
									
									$("#IMAGE3").css("width","220px"); 
									$("#IMAGE3").css("height","190px"); 
									$("#IMAGE3").css("overflow","hidden"); 

									//var img = '<img id="img_usim" src="/images/m2mtmp/' + fileName + '" style="width:auto; height:auto: margin-left:-30px">';//width:auto; height:90px: margin-left:-30px
									var img = '<img id="img_usim" src="/gift/prdtMng/getImageFile.do?prdtId=' + data.prdtId + '" style="width:auto; height:auto: margin-left:-30px">';//width:auto; height:90px: margin-left:-30px
									
									$("#IMAGE3").html(img);
									
									$("#img_usim").on("load", function(){
									
										var divObject = $("#IMAGE4");
										var imgObject = $("#img_usim");
										var divAspect = 190 / 220; // div의가로세로비는알고있는값이다
										var imgAspect = imgObject.height() / imgObject.width();
										
										if (imgAspect <= divAspect) {
										    // 이미지가 div보다 납작한 경우 세로를 div에 맞추고 가로는 잘라낸다
										    var imgWidthActual = divObject.offsetHeight / imgAspect;
										    
										    var imgWidthToBe = divObject.offsetHeight / divAspect;
										    var marginLeft = -Math.round((imgWidthActual - imgWidthToBe) / 2);

										    $("#img_usim").css("width","96%");
										    $("#img_usim").css("height","auto");
										    $("#img_usim").css("margin-left",marginLeft + 'px;');
										} else {
										    // 이미지가 div보다 길쭉한 경우 가로를 div에 맞추고 세로를 잘라낸다
										    $("#img_usim").css("width","auto");
										    $("#img_usim").css("height","96%");
										    $("#img_usim").css("margin-left","0");
										    
										}
									    $("#img_usim").css("border","2px solid lightgray");
									    
										$("#IMAGE3").css("height",imgObject.height() + 12); 

									});
																		

									$("#FORM3btn_btnImage").show();
									
								}else{
									//PAGE.FORM3.disableItem("fileDown1");
									PAGE.FORM3.disableItem("fileDel1");
									
									$("#IMAGE3").css("width","0px"); 
									$("#IMAGE3").css("height","0px"); 

									$("#IMAGE3").html("");

									$("#FORM3btn_btnImage").hide();
								}
								
								
							});
							
							
							

                            var uploader = PAGE.FORM3.getUploader("file_upload1");
                            
                            uploader.revive();
                            
							PAGE.FORM3.clearChanged();
							
							mvno.ui.popupWindowById("POPUP2", "수정화면", 360, 590, {  //350, 330
								onClose: function(win) {
									if (! PAGE.FORM3.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
			                           	    uploader.reset();
											win.closeForce();
									});
								}
							
							});
							
							break;
							
						case "btnDnExcel":
							
							var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
							if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") {
								return; //버튼 최초 클릭일때만 조회가능하도록
							}
							
							if(PAGE.GRID1.getRowsNum() == 0) {
								mvno.ui.alert("데이터가 존재하지 않습니다.");
								return;
							}
							
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/gift/prdtMng/prdtMngListEx.json?menuId=GIFT100011", true, {postData:searchData});
							break;							
	
					}
				
				}
			},

			// --------------------------------------------------------------------------
			// 등록화면
			FORM2 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', labelWidth:'100', inputWidth:'auto'},
					 {type:"block", blockOffset:0, list: [
						//{type:'input', name:'prdtId', label:'대표제품ID', width:160, required: true, maxLength:10},
						{type:'input', name:'maker', label:'제조사', width:160, required: true, maxLength:30},
						
						{type:'input', name:'prdtNm', label:'제품명', width:160, required: true, maxLength:50},

						{type:'input', name:'outUnitPric', label:'단가', width:160, /* required: true, */ numberFormat:"0,000,000,000", validate:"ValidInteger", maxLength:15},
					 ]},
					 {type:"block", blockOffset:0, list: [
							{type:'select', name:'useYn', label:'사용여부', width:160, required: true},
							{type:'input', name:'prdtDesc', label:'상품설명', width:160, maxLength:100},
					 ]},
					 {type:"block", blockOffset:0, list:[
						{type:"hidden",name:"fileId1", width:100}
						,{type : "input" , name:"fileName1", label:"파일명", width:160, disabled:true}
					 ]},
					 {type: "block", blockOffset:0, list: [
							{type: "upload", label:"파일업로드" ,name: "file_upload1", width:285, url:"/gift/prdtMng/fileUpLoad.do", autoStart: false, userdata: { limitCount : maxFileCnt, limitsize: maxFileSize, delUrl:"/org/common/deleteFile2.json"} }
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

					switch (btnId) {
						case "btnSave":
							mvno.cmn.ajax(ROOT + "/gift/prdtMng/prdtInsert.json", form, function(result) {
								if (result.result.code == "OK") {
									mvno.ui.closeWindowById(form, true);
									mvno.ui.notify("NCMN0001");
									PAGE.GRID1.refresh();
								} else {
									mvno.ui.alert(result.result.msg);
								}
							});
							break;

						case "btnClose" :
							mvno.ui.closeWindowById(form);
							break;
					}
				},
				onValidateMore : function (data){

				}
			},	
			
			
			// --------------------------------------------------------------------------
			//수정화면
			FORM3 : {
				title : "",
				items : [
					 {type:'settings', position:'label-left', labelWidth:'100', inputWidth:'auto'},
					 {type:"block", blockOffset:0, list: [
						{type:'input', name:'prdtId', label:'제품ID', width:160, disabled:true, required: true, maxLength:10},
						{type:'input', name:'maker', label:'제조사', width:160, required: true, maxLength:30},

						{type:'input', name:'prdtNm', label:'제품명', width:160, required: true, maxLength:50},
						
						{type:'input', name:'outUnitPric', label:'단가', width:160, /* required: true, */ numberFormat:"0,000,000,000", validate:"ValidInteger", maxLength:15},
					 ]},
					 {type:"block", blockOffset:0, list: [
						{type:'select', name:'useYn', label:'사용여부', width:160, required: true},
						{type:'input', name:'prdtDesc', label:'상품설명', width:160, maxLength:100}
					 ]},
					 {type:"block", blockOffset:0, list:[
							{type:"hidden",name:"fileId1", width:100}
							,{type : "input" , name:"fileName1", label:"파일명", width:120, disabled:true}
							,{type : "newcolumn" }
							,{type : "button" , name:"fileDel1", value: "삭제"}
					 ]},
					 {type: "block", blockOffset:0, list: [
							{type: "upload", label:"파일업로드" ,name: "file_upload1", width:285, url:"/gift/prdtMng/fileUpLoad.do", autoStart: false, userdata: { limitCount : maxFileCnt, limitsize: maxFileSize, delUrl:"/org/common/deleteFile2.json"} }
					 ]}
				],
				buttons : {
// 					center : {
// 						btnSave : { label : "수정" },
// 						btnClose : { label: "닫기" }
// 					}
				},
				
				onButtonClick : function(btnId) {
 					var form = this; // 혼란방지용으로 미리 설정 (권고)
					var fileLimitCnt = maxFileCnt;

 					switch (btnId) {
// 						case "btnSave":
// 							mvno.cmn.ajax(ROOT + "/m2m/usimPrdtMng/pusimPrdtUpdate.json", form, function(result) {
// 								if (result.result.code == "OK") {
// 									mvno.ui.closeWindowById(form, true);
// 										mvno.ui.notify("NCMN0002");
// 										PAGE.GRID1.refresh();
// 								} else {
// 									mvno.ui.alert(result.result.msg);
// 								}
// 							});
// 							break;

// 						case "btnClose" :
// 							mvno.ui.closeWindowById(form);
// 							break;
					case "fileDel1" :
						mvno.cmn.ajax(ROOT + "/gift/prdtMng/deleteFile.json", form, function(resultData) {
							mvno.ui.notify("NCMN0014");
							
							form.setItemValue("fileId1", "");
							form.setItemValue("fileName1", "");
							form.disableItem("fileDown1");
							form.disableItem("fileDel1");
							
							form.showItem("file_upload1");
							
							form.setUserData("file_upload1", "limitCount", maxFileSize);//fileLimitCnt-(resultData.result.fileTotCnt)
							form.setUserData("file_upload1", "limitsize", maxFileSize);
							form.setUserData("file_upload1", "delUrl", "/org/common/deleteFile2.json");
							
							
							$("#IMAGE3").css("width","0px"); 
							$("#IMAGE3").css("height","0px"); 
							
							$("#IMAGE3").html("");
							
						});
						break;
 					}
				},
				onValidateMore : function (data){

				}
			},	
			
			FORM3btn : {
				buttons : {
					center : {
						btnImage : {label : "이미지확대" },
						btnSave : { label : "수정" },
						btnClose : { label: "닫기" }
					}
				},
				
				onButtonClick : function(btnId) {
					var form = PAGE.FORM3; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnSave":
							mvno.cmn.ajax(ROOT + "/gift/prdtMng/prdtUpdate.json", form, function(result) {
								if (result.result.code == "OK") {
									mvno.ui.closeWindowById(form, true);
										mvno.ui.notify("NCMN0002");
										PAGE.GRID1.refresh();
								} else {
									mvno.ui.alert(result.result.msg);
								}
							});
							break;

						case "btnClose" :
							mvno.ui.closeWindowById(form);
							break;

						case "btnImage" :
							

							mvno.ui.createForm("FORM5btn");
							
							
							var prdtId = PAGE.FORM3.getItemValue("prdtId");


							$("#IMAGE5").css("width","700px"); 
							$("#IMAGE5").css("height","640px"); 
							$("#IMAGE5").css("overflow","hidden"); 

							//var img = '<img id="img_usim" src="/images/m2mtmp/' + fileName + '" style="width:auto; height:90px: margin-left:-30px">';
							var img = '<img id="img_usim_big" src="/gift/prdtMng/getImageFile.do?prdtId=' + prdtId + '" style="width:auto; height:auto: margin-left:-30px">';//width:auto; height:90px: margin-left:-30px
							
							$("#IMAGE5").html(img);

							var myImg = document.getElementById("img_usim_big");
							myImg.onload = function () // 이미지 로딩 완료 시 실행되는 함수
							{
								
								var divObject = $("#IMAGE5");
								var imgObject = $("#img_usim_big");
								var divAspect = 640 / 700; // height/width   div의가로세로비는알고있는값이다
								var imgAspect = imgObject.height() / imgObject.width();
								
								if (imgAspect <= divAspect) {
								    // 이미지가 div보다 납작한 경우 세로를 div에 맞추고 가로는 잘라낸다
								    
								    $("#img_usim_big").css("width","96%");
								    $("#img_uimg_usim_bigsim").css("height","auto");
								    $("#img_usim_big").css("margin-left", '0px;');
								} else {
								    // 이미지가 div보다 길쭉한 경우 가로를 div에 맞추고 세로를 잘라낸다
								    
								    $("#img_usim_big").css("width","auto");
								    $("#img_usim_big").css("height","96%");
								    $("#img_usim_big").css("margin-left",'0px;');
								    
								}
							    $("#img_usim_big").css("border","2px solid lightgray");
							    
								$("#IMAGE5").css("height",imgObject.height() + 12); 

							}
							
							
							mvno.ui.popupWindowById("POPUP_IMAGE", "이미지확대", 750, 750, {
								onClose: function(win) {

									win.closeForce();
									
								}
							});
							
							break;

					}
				},
				onValidateMore : function (data){

				}
			},	
						
			GRID2 : {
				css : {
					height : "180px"
				},
				title : "사은품 이력",
				header : "제품ID,제조사,제품명,단가,이미지파일명,이미지WEB URL,사용여부,수정일시,수정자",	//9
				columnIds : "prdtId,maker,prdtNm,outUnitPric,imgFile,webUrl,useYn,rvisnDttm,usrNm",
				widths : "100,100,190,80,80,100,80,100,100",
				colAlign : "center,center,center,right,center,center,center,center,center",
				colTypes : "ro,ro,ro,ron,ro,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str",
				paging : true,
				pageSize:20,
				buttons : {

				},
				onButtonClick : function(btnId, selectedData) {

				}	
			},

			//-이미지확대 화면 버튼-----------------------------------------
			FORM5btn : {
				title : "",
				items : [
				],
				buttons : {
					center : {
						btnClose : { label: "닫기" }
					}
				},
				onChange : function(name, value) {//onChange START

				},
				onButtonClick : function(btnId) {
					var form = PAGE.FORM5btn; // 혼란방지용으로 미리 설정 (권고)

					switch (btnId) {
						case "btnClose" :
							mvno.ui.closeWindowById(form);
							break;
					}
				},
				onValidateMore : function (data){

				}
			},			
			
			

	};

	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
		    buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.ui.createGrid("GRID2");

				//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0243"}, PAGE.FORM1, "ptnrId"); // 제휴사
				//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0060"}, PAGE.FORM1, "pointType"); // 지급유형
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0242", orderBy:"etc1"}, PAGE.FORM1, "useYn"); // 사용여부
			}
			
	};
	
	function myCallBack( state, fileName , fileSize){
		console.log("------------myCallBack---------");
		
	}
	
	
</script>
