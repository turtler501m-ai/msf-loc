<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name  : msp_cmn_batch_1001.jsp
	 * @Description : 배치정보
	 * @
	 * @  수정일	  	수정자			  수정내용
	 * @ ---------   ---------   -------------------------------
	 * @ 2016.06.27  	TREXSHIN		  최초생성
	 * @ 
	 * @author : TREXSHIN
	 * @Create Date : 2016.06.27
	 */
%>
	
	<div id="FORM1" class="section-search"></div>
	<div id="GRID1"></div>
	
	<div id="GROUP1" style="display:none;">
		<div id="FORM2" class="section-box"></div>
		<div id="FORM3" class="section-pagescope"></div>
	</div>
	
	<script type="text/javascript">
	var DHX = {
			FORM1 : {
				items : [
					 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					
					,{type:"block", list:[
						 {type:"select", name:"serverType", label:"배치서버", width:80 ,offsetLeft:10}
						,{type:"newcolumn"}
						,{type:"input", name:"searchBatchJobNm", label:"배치작업명", labelWidth:60, width:100, offsetLeft:20}
						,{type:"newcolumn"}
// 						,{type:"select", name:"searchDutySctn", label:"업무구분", width:80, userdata:{lov:"CMN0023"}, offsetLeft:30}
						,{type:"select", name:"searchDutySctn", label:"업무구분", width:80, offsetLeft:20}
						,{type:"newcolumn"}
						,{type:"select", name:"searchBatchTypeCd", label:"배치유형", width:80, offsetLeft:20}
						,{type:"newcolumn"}
// 						,{type:"select", name:"searchUsgYn", label:"사용여부", width:80, userdata:{lov:"CMN0011"}, offsetLeft:30}
						,{type:"select", name:"searchUsgYn", label:"사용여부", width:80, offsetLeft:20}
						
					]}
					
					,{type:"newcolumn", offset:130}
					,{type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}
				],
				
				onValidateMore : function(data) {
					
				},
				
				onButtonClick : function(btnId) {
					
					var form = this;
					
					switch(btnId) {
						
						case "btnSearch" :
							
							PAGE.GRID1.list(ROOT + "/cmn/batch/getBatchInfo.json", form);
							break;
					}
				}
				
			},
			
			GRID1 : {
				 title      : "배치정보"
				,css        : {height : "560px"}
				,header     : "배치서버,배치작업ID,배치작업명,업무구분,실행서비스,배치유형,실행Cron표현식,실행시간,사용여부,배치작업상세,업무구분코드,배치유형코드,사용여부코드"
				,columnIds  : "serverTypeNm,batchJobId,batchJobNm,dutySctnNm,execService,batchTypeNm,execCron,execTmCd,usgYnNm,batchJobDsc,dutySctn,batchTypeCd,usgYn"
				,widths     : "60,90,180,70,180,70,150,80,60,200,0,0,0"
				,colAlign   : "center,center,left,center,left,center,left,center,center,left,left,left,left"
				,colTypes   : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
				,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str"
				,hiddenColumns: [10,11,12]
				,paging     : true
				,showPagingAreaOnInit: true
				,pageSize: 20
				,buttons : {
					right : {
						btnReg : { label : "등록" },
						btnMod : { label : "수정" }
					}
				}
				, checkSelectedButtons : ["btnMod"]
				, onRowDblClicked : function(rowId, celInd) {
					this.callEvent('onButtonClick', ['btnMod']); // ROW 더블 클릭시 수정버튼과 같은 기능 처리함.
				}
				, onButtonClick : function(btnId, selectedData) {
					
					switch(btnId) {
						
						case "btnReg" :
							
							mvno.ui.createForm("FORM2");
							mvno.ui.createForm("FORM3");
							
							PAGE.FORM2.clear();

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0029"}, PAGE.FORM2, "serverType"); // 배치서버
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0023",etc2:"2"}, PAGE.FORM2, "dutySctn"); // 업무구분
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0024"}, PAGE.FORM2, "batchTypeCd"); // 배치유형
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM2, "usgYn"); // 사용여부
							
							PAGE.FORM2.setItemValue("saveType", "I");
							PAGE.FORM2.disableItem("batchJobId");
							
							mvno.ui.popupWindowById("GROUP1", "배치 등록", 580, 360, {
								onClose: function(win) {
									if (! PAGE.FORM2.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function() {
										win.closeForce();
									});
								}
							});
							
							break;
							
						case "btnMod":
							
							mvno.ui.createForm("FORM2");
							mvno.ui.createForm("FORM3");
							
							PAGE.FORM2.clear();


							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0029"}, PAGE.FORM2, "serverType"); // 배치서버
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0023",etc2:"2"}, PAGE.FORM2, "dutySctn"); // 업무구분
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0024"}, PAGE.FORM2, "batchTypeCd"); // 배치유형
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM2, "usgYn"); // 사용여부
							
							PAGE.FORM2.setFormData(selectedData); // 등록(btnReg) 인 경우에는 selectedData 는 empty(undefined)!!
							
							PAGE.FORM2.setItemValue("saveType", "U");
							
							PAGE.FORM2.disableItem("batchJobId");
							PAGE.FORM2.clearChanged();
							
							mvno.ui.popupWindowById("GROUP1", "배치 수정", 580, 360, {
								onClose: function(win) {
									if (! PAGE.FORM2.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
									});
								}
							});
							
							break;
					}
				}
			},
			
			
			FORM2 : {
				title : "배치정보상세",
				items : [
					 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, blockOffset:0}
					
					,{type:"hidden", name:"saveType"}
					,{type:"block", list:[
						,{type:"input" ,name:"batchJobId", label:"배치작업ID", labelWidth:70, width:90, readonly:true, offsetLeft:10}
						,{type:"newcolumn"}
						,{type:"input", name:"batchJobNm", label:"배치작업명", labelWidth:90, width:200, required:true, offsetLeft:30, maxLength:33}
					]}

					,{type:"block", list:[
						{type:"select", name:"serverType", label:"배치서버", labelWidth:70, width:90, offsetLeft:10, required:true}
					]}

					,{type:"block", list:[
						{type:"input", name:"batchJobDsc", label:"상세설명", labelWidth:70, width:414, offsetLeft:10, maxLength:333}
					]}
					
					,{type:"block", list:[
// 						 {type:"select", name:"dutySctn", label:"업무구분", labelWidth:70, width:90, offsetLeft:10, userdata:{lov:"CMN0023"}, required:true}
						 {type:"select", name:"dutySctn", label:"업무구분", labelWidth:70, width:90, offsetLeft:10, required:true}
						,{type:"newcolumn"}
						,{type:"input", name:"execService", label:"실행서비스", labelWidth:90, width:200, required:true, offsetLeft:30, maxLength:100, validate: 'NotEmpty,ValidAplhaNumeric,mvno.vxRule.excludeKorean'}
					]}
					
					,{type:"block", list:[
// 						 {type:"select", name:"batchTypeCd", label:"배치유형", labelWidth:70, width:90, offsetLeft:10, userdata:{lov:"CMN0024"}, required:true}
						 {type:"select", name:"batchTypeCd", label:"배치유형", labelWidth:70, width:90, offsetLeft:10, required:true}
						,{type:"newcolumn"}
						,{type:"input", name:"execCron", label:"실행Cron표현식", labelWidth:90, width:200, offsetLeft:30, maxLength:30}
					]}
					
					,{type:"block", list:[
// 						 {type:"select", name:"usgYn", label:"사용여부", labelWidth:70, width:90, offsetLeft:10, userdata:{lov:"CMN0011"}, required:true}
						 {type:"select", name:"usgYn", label:"사용여부", labelWidth:70, width:90, offsetLeft:10, required:true}
						,{type:"newcolumn"}
						,{type:"input", name:"execTmCd", label:"실행시간", labelWidth:90, width:200, offsetLeft:30, maxLength:6, validate: 'ValidNumeric,mvno.vxRule.excludeKorean', userdata: {align: "left"}}
					]}
					
				]
				
			},
			
			FORM3 : {
				buttons : {
					center : {
						 btnSave : {label : "저장"}
						,btnClose: {label : "닫기"}
					}
				},
				onButtonClick : function(btnId) {
					
					switch(btnId) {
					
						case "btnSave" :
							
							mvno.ui.confirm("CCMN0004", function(){
								mvno.cmn.ajax(ROOT + "/cmn/batch/savBatchInfo.json", PAGE.FORM2, function(result) {
									mvno.ui.closeWindowById("GROUP1", true);
									mvno.ui.notify("NCMN0004");
									PAGE.GRID1.refresh();
								});
							});
							
							break;
							
						case "btnClose" :
							
							mvno.ui.closeWindowById("GROUP1");
							break;
					}
				}
			}
			
		};
		
		var PAGE = {
				
			title : "${breadCrumb.title}",
			breadcrumb : " ${breadCrumb.breadCrumb}",
			
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0023",etc2:"2"}, PAGE.FORM1, "searchDutySctn"); // 업무구분
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0024"}, PAGE.FORM1, "searchBatchTypeCd"); // 배치유형
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM1, "searchUsgYn"); // 사용여부
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0029"}, PAGE.FORM1, "serverType"); // 배치서버

				var fixGrid = PAGE.GRID1.getColIndexById("batchJobNm");
				PAGE.GRID1.splitAt(fixGrid+1);

			}
			
		};
		
		
	</script>