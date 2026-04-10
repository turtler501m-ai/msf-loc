<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name  : msp_cmn_sms_1001.jsp
	 * @Description : SMS 템플릿 관리
	 * @
	 * @  수정일	  	수정자			  수정내용
	 * @ ---------   ---------   -------------------------------
	 * @ 2017.01.11  	TREXSHIN		  최초생성
	 * @ 
	 * @author : TREXSHIN
	 * @Create Date : 2017.01.11
	 */
%>
	
	<div id="FORM1" class="section-search"></div>
	<div id="GRID1"></div>
	
	<div id="FORM2" class="section-box"></div>
	
	<script type="text/javascript">
	
	var EXPIRE_HOUR = [{value:'1', text:'1시간'}, {value:'2', text:'2시간', selected:true}, {value:'3', text:'3시간'}];
	
	var DHX = {
			FORM1 : {
				items : [
					 {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					
					,{type:"block", list:[
						 {type:"input", name:"searchOrgnId", label:"관리부서", width:100, offsetLeft:10}
						,{type:"newcolumn", offsetLeft:3}
						,{type:"button", value:"검색", name:"btnOrgFind"}
						,{type:"newcolumn", offsetLeft:3}
						,{type:"input", name:"searchOrgnNm", width:150}
						,{type:"newcolumn"}
						,{type:"select", name:"searchWorkType", label:"업무구분", width:80, offsetLeft:20}
						,{type:"newcolumn"}
						,{type:"input", name:"searchTemplateNm", label:"템플릿명", labelWidth:70, width:180, offsetLeft:20}
					]}
					
					,{type:"newcolumn", offset:130}
					,{type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}
				],
				
				onValidateMore : function(data) {
					
				},
				
				onChange : function(name, value) {
					
					var form = this;
					
					if(name == "searchOrgnId") {
						if(mvno.cmn.isEmpty(value)) {
							form.setItemValue("searchOrgnNm","");
						}
					} else if(name == "searchOrgnNm") {
						if(mvno.cmn.isEmpty(value)) {
							form.setItemValue("searchOrgnId","");
						}
					}
				},
				
				onButtonClick : function(btnId) {
					
					var form = this;
					
					switch(btnId) {
						
						case "btnOrgFind":
							mvno.ui.codefinder("ORGHEAD", function(result) {
								form.setItemValue("searchOrgnId", result.orgnId);
								form.setItemValue("searchOrgnNm", result.orgnNm);
							});
							break;
							
						case "btnSearch" :
							
							PAGE.GRID1.list(ROOT + "/cmn/smsmgmt/getSmsTemplateList.json", form);
							break;
					}
				}
				
			},
			
			GRID1 : {
				 title      : "SMS 템플릿 목록"
				,css        : {height : "290px"}
				,header     : "템플릿ID,템플릿명,관리부서,업무구분,발신자번호,재발송횟수,등록자,등록일시,수정자,수정일시,관리부서ID,업무구분코드,발신자번호,만료시간,제목,내용,등록자ID,수정자ID,템플릿ID" // 10/
				,columnIds  : "templateId,templateNm,mgmtOrgnNm,workTypeNm,callbackNm,retry,regstNm,regstDttm,rvisnNm,rvisnDttm,mgmtOrgnId,workType,callback,expireHour,subject,text,regstId,rvisnId,templateId"
				,widths     : "70,230,120,90,90,80,80,140,80,140,0,0,0,0,0,0,0,0,0"
				,colAlign   : "center,left,center,center,center,right,center,center,center,center,left,left,left,left,left,left,left,left,left"
				,colTypes   : "ro,ro,ro,ro,ro,ron,ro,roXdt,ro,roXdt,ro,ro,ro,ro,ro,ro,ro,ro,ro"
				,colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
				,hiddenColumns: [10,11,12,13,14,15,16,17,18]
				,paging     : true
				,showPagingAreaOnInit: true
				,pageSize: 20
				,onRowSelect : function(rowId, celInd) {
					var rowData = this.getRowData(rowId);
					rowData.text = fn_replaceTextarea(rowData.text);
					PAGE.FORM2.setFormData(rowData);
				}
				
			},
			
			FORM2 : {
				 title : "SMS 템플릿 상세"
				,items : [
					 {type: 'settings', position: 'label-left', labelAlign:"left", labelWidth: '70'}
					,{type: 'hidden', name: 'saveType'}
					,{type: 'hidden', name: 'msgType'}
					,{type: "block", list: [
						 {type: 'input', name: 'templateId', label: '템플릿ID', width:80, offsetLeft:10, value: '' , readonly:true, disabled:true}
						,{type: "newcolumn"}
						,{type: 'input', name: 'templateNm', label: '템플릿명', width:650, offsetLeft:20, value: '', maxLength:50, required:true, validate:'NotEmpty'}
					]}
					,{type: "block", list: [
						 {type:"input" ,name:"mgmtOrgnId", label:"관리부서", width:80, offsetLeft:10, readonly:true, required:true, validate:'NotEmpty'}
						,{type:"newcolumn"}
						,{type:"button", value:"검색", name:"btnOrgFind"}
						,{type:"newcolumn"}
						,{type:"input", name:"mgmtOrgnNm", width:150, readonly:true}
						,{type:"newcolumn"}
						,{type:"select", name:"workType", label:"업무구분", width:84, offsetLeft:20, required:true, validate:'NotEmpty'}
						,{type:"newcolumn"}
						,{type:"select", name:"callback", label:"발신자번호", width:100, offsetLeft:20, labelWidth:80, required:true, validate:'NotEmpty'}
						,{type:"newcolumn"}
						,{type:"select", name:"expireHour", label:"만료시간", width:58, offsetLeft:20, required:true, options:EXPIRE_HOUR, validate:'NotEmpty'}
					]}
					,{type: "block", list: [
						 {type: 'input', name: 'subject', label: '문자제목', width:446, offsetLeft:10, value: '', maxLength:40, required:true, validate:'NotEmpty'}
						,{type:"newcolumn"}
						,{type: 'input', name: 'retry', label: '재발송횟수', width:58, offsetLeft:20, labelWidth:70, value: '0', maxLength:2, numberFormat:'00', validate:'ValidInteger'}
						,{type: "newcolumn"}
						,{type:"input", name:"kTemplateCode", label:"카카오톡 발송코드", width:100, offsetLeft:20, labelWidth:100}
					]}
					,{type: "block", list: [
						 {type: 'input', name: 'text', label: '문자내용', rows:'8', width:826, offsetLeft:10, value: '', required:true, maxLength:1300, validate:'NotEmpty'}
					]}
				]
				,buttons : {
					 left : {
						 btnInit : { label : "초기화" }
					}
					,right : {
						 btnReg : { label : "등록" }
						,btnMod : { label : "수정" }
					}
				}
				,onButtonClick : function(btnId, selectedData) {
					var form = this;
					switch (btnId) {
						case "btnOrgFind":
							mvno.ui.codefinder("ORGHEAD", function(result) {
								form.setItemValue("mgmtOrgnId", result.orgnId);
								form.setItemValue("mgmtOrgnNm", result.orgnNm);
							});
							break;
						
						case "btnInit" :
							PAGE.GRID1.clearSelection();
							PAGE.FORM2.clear();
							form.setItemValue("expireHour", "2");
							form.setItemValue("retry", "0");
							break;
							
						case "btnReg" :
							mvno.ui.confirm("새로운 템플릿을 등록하시겠습니까?", function(){
							// textarea maxlength 값이 IE9에서 동작하지 않아 validation 추가.
								var inputSize = ($('textarea[name="text"]').val()).length;
								var maxLength = ($('textarea[name="text"]').attr('maxLength'));
								if (inputSize > 0 && inputSize > maxLength) {
									mvno.ui.alert("문자내용은 "+maxLength+"자까지 입력가능합니다.");
									return;
								}
								
								form.setItemValue("msgType", "3");
								form.setItemValue("saveType", "I");
								
								mvno.cmn.ajax(ROOT + "/cmn/smsmgmt/saveSmsTemplate.json", form, function(result) {
									mvno.ui.notify("NCMN0001");
									PAGE.GRID1.refresh();
									PAGE.FORM2.clear();
								});
							});
							
							break;
							
						case "btnMod" :
							if(PAGE.GRID1.getSelectedRowData() != null) {
								mvno.ui.confirm("템플릿을 수정하시겠습니까?", function(){
									// textarea maxlength 값이 IE9에서 동작하지 않아 validation 추가.
									var inputSize = ($('textarea[name="text"]').val()).length;
									var maxLength = ($('textarea[name="text"]').attr('maxLength'));
									if (inputSize > 0 && inputSize > maxLength) {
										mvno.ui.alert("문자내용은 "+maxLength+"자까지 입력가능합니다.");
										return;
									}
									
									form.setItemValue("msgType", "3");
									form.setItemValue("saveType", "U");
									
									mvno.cmn.ajax(ROOT + "/cmn/smsmgmt/saveSmsTemplate.json", form, function(result) {
										mvno.ui.notify("NCMN0002");
										PAGE.GRID1.refresh();
										PAGE.FORM2.clear();
									});
								});
							} else {
								mvno.ui.notify("ECMN0002");
							}
							break;
					}
				}
			}
		};
		
		var PAGE = {

		   title : "${breadCrumb.title}",
		   breadcrumb : "${breadCrumb.breadCrumb}",  
		   onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
				mvno.ui.createForm("FORM2");
				
				// 조회조건 처리상태 combo 셋팅
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0070",orderBy:"etc6"}, PAGE.FORM2, "callback");
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0069",orderBy:"etc6"}, PAGE.FORM1, "searchWorkType"); // 업무구분
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0069",orderBy:"etc6"}, PAGE.FORM2, "workType"); // 업무구분
			}
			
		};
		
		
		function fn_replaceTextarea(s) { //textarea 에서 &가 &amp;로 자동 변경되어 수정함.
			var s = new String(s);
			s = s.replace(/&amp;/ig, "&");
			s = s.replace(/&lt;/ig, "<");
			s = s.replace(/&gt;/ig, ">");
			s = s.replace(/&quot;/ig, "\"");
			s = s.replace(/&#39;/ig, "'");
			s = s.replace(/<br>/ig, "\r\n");
			return s;
		}
	
	</script>