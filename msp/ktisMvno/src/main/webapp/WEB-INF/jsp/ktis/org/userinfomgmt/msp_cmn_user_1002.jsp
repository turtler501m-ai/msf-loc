<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_cmn_user_1002.jsp
	 * @Description : 대리점사용자계정관리
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>
<div id="GRID2"></div>
<!-- Script -->
<script type="text/javascript">
	var appUsrId = '${appUsrId}';
	var appUsrNm = '${appUsrNm}';
	var usrFindAuth = '${usrFindAuth}';
	
	 var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			 items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
					{type: 'block', list: [
						{type: "calendar", name: "searchStartDt", label: "신청일자", id: "searchStartDt", dateFormat: "%Y-%m-%d",serverDateFormat: "%Y%m%d", offsetLeft:10, width: 100},
						{type: "newcolumn"},
						{type: "calendar", name: "searchEndDt", id: "searchEndDt", label : "~", labelAlign: "center", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", labelWidth: 10, offsetLeft: 5, width: 100},
						{type: 'newcolumn'},
  	                    {type: "input", label: "승인자", name: "appUsrNm", width:100, offsetLeft:36, disabled:true, required:true, validate:"NotEmpty",value:appUsrNm}, 
  	                    {type: "newcolumn" }, 
  	                    {type: "input", label: "", name: "appUsrId" , width:60, disabled:true, required:true, validate:"NotEmpty",value:appUsrId},
  	                    {type: "newcolumn" },
  	                    {type: "button", value: "찾기", name: "btnUseFind"},
						{type: 'newcolumn'},
						{type: 'select', name: 'appYn', label: '승인여부' ,width:100,offsetLeft:40}
					]},
					{type: 'block', list: [
						{type: "input", name:"orgnId", label:"조직", value: "", width:100, offsetLeft:10},
						{type: "newcolumn", offsetLeft:3},
						{type: "button", value:"검색", name:"btnOrgFind"},
						{type: "newcolumn", offsetLeft:3},
						{type: "input", name:"orgnNm", value:"", width:145, readonly: true},
						{type: "newcolumn"},
						{type: 'input', name: 'usrId', label: '사용자ID', value: '', maxLength:10,width:100, offsetLeft:70},
						{type: 'newcolumn'},
						{type: 'input', name: 'usrNm', label: '사용자명', value: '', maxLength:10,width:100,offsetLeft:40}
					]},
					{type: 'newcolumn', offset:150},
					{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"},
				],
				onButtonClick : function(btnId) {
				
					var form = this;
					
					switch (btnId) {
					
						case "btnSearch":
							PAGE.GRID1.list(ROOT + "/org/userinfomgmt/userApprovalList.json", form);
							break;
								
						case "btnOrgFind":
							mvno.ui.codefinder("APPRORGAGENCY", function(result) {
								form.setItemValue("orgnId", result.orgnId);
								form.setItemValue("orgnNm", result.orgnNm);
							});
							break;
							
						case "btnUseFind" :
							mvno.ui.codefinder("USER", function(result) {
								form.setItemValue("appUsrNm", result.usrNm);
						        form.setItemValue("appUsrId", result.usrId);
							}); 
							break;  
					}
				},
				onValidateMore : function (data) {
					if (this.getItemValue("searchStartDt", true) > this.getItemValue("searchEndDt", true)) {
						this.pushError("searchStartDt", "신청일자", "종료일자가 시작일보다 클수 없습니다.");
						this.resetValidateCss();
					}
						
					if(this.getItemValue("searchStartDt") == null || this.getItemValue("searchStartDt") == ""){
						this.pushError("searchStartDt","신청일자","시작일자를 선택하세요");
					}
					
					if(this.getItemValue("searchEndDt") == null || this.getItemValue("searchEndDt") == ""){
						this.pushError("searchEndDt","신청일자","종료일자를 선택하세요");
					}
					
					if(this.getItemValue("appUsrId") == null || this.getItemValue("appUsrId") == ""){
						this.pushError("appUsrId","승인자","승인자를 선택해주세요");
					}
				}
		},
		// ----------------------------------------
		//대리점사용자계정 요청 리스트
		GRID1 : {
			css : {
					height : "320px"
			},
			title : "조회결과",
			header : "신청일자,사용자ID,사용자명,조직명,소속구분,핸드폰번호,신청파일1,신청파일2,modChk,seqNum,승인자,승인여부,처리일자,반려사유,usrId", //14
			columnIds : "aplDt,usrIdMsk,usrNm,orgnNm,attcSctnCdNm,mblphnNum,appFileId1,appFileId2,modChk,seqNum,appUsrNm,appYn,appDt,refRsn,usrId",
			colAlign : "center,left,left,left,left,center,left,left,left,left,center,center,center,left,left",
			colTypes : "ro,ro,ro,ro,ro,roXp,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
			widths : "130,100,110,150,80,130,0,0,0,0,100,100,130,300,0",
			hiddenColumns:[6,7,8,9,14],
			paging: true,
			pageSize:20,
			buttons : {
				right : {
					btnMod : { label : "상세" },
				}
			},
			checkSelectedButtons : ["btnMod"],
			onRowDblClicked : function(rowId, celInd) {
				// 수정버튼 누른것과 같이 처리?
				this.callEvent('onButtonClick', ['btnMod']);
			},
			onRowSelect : function(rowId) {
				
				var rowData = this.getSelectedRowData();
				
				PAGE.GRID2.clearAll();
				PAGE.GRID2.list(ROOT + "/org/userinfomgmt/userApprovalHist.json", rowData);
			},
			onButtonClick : function(btnId, selectedData) {
				switch (btnId) {
					case "btnMod":
						if(selectedData.modChk != "Y"){
							mvno.ui.alert("이미 처리된 사용자입니다.");
							break;
						}
						
						mvno.ui.createForm("FORM2");
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM2, "mblphnNum1"); // 핸드폰번호
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM2, "fax1"); // FAX
						
						var data = selectedData;
						
						PAGE.FORM2.setFormData(data); // 등록(btnReg) 인 경우에는 selectedData 는 empty(undefined)!!

						/* 핸드폰 번호, 전화번호, Fax 셋팅 */
						setInputDataAddHyphen(PAGE.FORM2, data, "mblphnNum", "tel");
						setInputDataAddHyphen(PAGE.FORM2, data, "fax", "tel");

						PAGE.FORM2.clearChanged();

						mvno.ui.popupWindowById("FORM2", "사용자", 720, 310, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
								});
							}
						});
						
						mvno.cmn.ajax(ROOT + "/org/userinfomgmt/getFile.json", PAGE.GRID1.getSelectedRowData(), function(resultData) {
							if(!mvno.cmn.isEmpty(resultData.result.appFileNm1)){
								PAGE.FORM2.setItemValue("appFileId1", PAGE.GRID1.getSelectedRowData().appFileId1); 
								PAGE.FORM2.setItemValue("appFileNm1", resultData.result.appFileNm1);
								PAGE.FORM2.enableItem("fileDown1");
							} else {
								PAGE.FORM2.disableItem("fileDown1");
							}
							
							if(!mvno.cmn.isEmpty(resultData.result.appFileNm2)){
								PAGE.FORM2.setItemValue("appFileId2", PAGE.GRID1.getSelectedRowData().appFileId2);
								PAGE.FORM2.setItemValue("appFileNm2", resultData.result.appFileNm2);
								PAGE.FORM2.enableItem("fileDown2");
							} else {
								PAGE.FORM2.disableItem("fileDown2");
							}
                           
						});
						
						break;

				}
			}
		},
		// ----------------------------------------
		//대리점사용자계정 요청 히스토리
		GRID2 : {
			css : {
					height : "140px"
			},
			title : "이력조회",
			header : "신청일자,사용자ID,사용자명,조직명,소속구분,핸드폰번호,승인자,승인여부,처리일자,반려사유", //10
			columnIds : "aplDt,usrId,usrNm,orgnNm,attcSctnCdNm,mblphnNum,appUsrNm,appYn,appDt,refRsn",
			colAlign : "center,left,left,left,left,center,center,center,center,left",
			colTypes : "ro,ro,ro,ro,ro,roXp,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str",
			widths : "130,100,110,150,80,130,100,100,130,300",
			paging: true,
			pageSize:5
		},
		 // --------------------------------------------------------------------------
		 //대리점사용자계정 요청 상세
		 FORM2 : {
			title : "",
			items : [
				{type: 'settings', position: 'label-left', labelWidth: '100', inputWidth: 'auto'},
				{type: "block", blockOffset: 0, offsetLeft: 30,list: [
					{type: 'input', name: 'usrIdMsk', label: '사용자ID',  width: 150,  value: '' , disabled:true},
					{type: 'hidden', name: 'usrId'},
					{type: "newcolumn"},
					{type: 'input', name: 'usrNm', label: '사용자명',  width: 150, offsetLeft: 70, value: '' , maxLength:10, required: true, disabled:true}
				]},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'orgnId', label: '조직ID',  width: 150, offsetLeft: 0,  value: '' , disabled:true},
					{type: "newcolumn"},
					{type: 'input', name: 'orgnNm', label: '조직명',  width: 150, offsetLeft: 70, value: '' , disabled:true, required: true},
				]},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: "block", blockOffset: 0, list: [
						{type: 'select', name: 'mblphnNum1', label: '핸드폰번호', width: 62, required: true, disabled:true},
						{type: "newcolumn"},
						{type: 'input', name: 'mblphnNum2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4, disabled:true},
						{type: "newcolumn"},
						{type: 'input', name: 'mblphnNum3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4, disabled:true},
					]},
					{type: "newcolumn"},
					{type: "block", blockOffset: 0, list: [
						{type: 'select', name: 'fax1', label: 'FAX',  offsetLeft: 70,  width: 62, disabled:true},
						{type: "newcolumn"},
						{type: 'input', name: 'fax2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4, disabled:true},
						{type: "newcolumn"},
						{type: 'input', name: 'fax3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4, disabled:true},
					]}
				]},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'calendar', name: 'usgStrtDt' , label: '적용시작일자',  dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d',   value:'',  calendarPosition: 'right' ,width:150, readonly:true,required: true, disabled:true},
					{type: "newcolumn"},
					{type: 'calendar', name: 'usgEndDt', label: '적용종료일자',  dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', offsetLeft: 70, value: '', calendarPosition: 'right',width:150, readonly:true,required: true, disabled:true},
				]},
				{type:"block", blockOffset: 0,offsetLeft: 30, list:[
				    {type:"hidden",name:"appFileId1"}
					,{type : "input" , name:"appFileNm1", label:"파일명", width:400, readonly:true, disabled:true}
					,{type : "newcolumn" }
					,{type : "button" , name:"fileDown1", value: "다운로드"}
				]},
				{type:"block", blockOffset: 0,offsetLeft: 30, list:[
  				    {type:"hidden",name:"appFileId2"}
  					,{type : "input" , name:"appFileNm2", label:"파일명", width:400, readonly:true, disabled:true}
  					,{type : "newcolumn" }
  					,{type : "button" , name:"fileDown2", value: "다운로드"}
				]}
			],

			buttons : {
				center : {
					btnSave : { label : "승인" },
					btnDel : { label: "반려" }
				},
				right : {
					btnClose : { label : "닫기" }
				}
			},

			onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				switch (btnId) {

					case "btnDel":
						var pUsrId = PAGE.GRID1.getSelectedRowData().usrId;
						var pMblphnNum = PAGE.GRID1.getSelectedRowData().mblphnNum;
						
						mvno.ui.prompt("반려 사유", function(pRefRsn) {
							mvno.cmn.ajax(ROOT + "/org/userinfomgmt/deleteUserApproval.json", {usrId:pUsrId, refRsn:pRefRsn, mblphnNum:pMblphnNum}, function(result){
								if(result.result.code == "OK"){
									mvno.ui.closeWindowById(form, true);
									mvno.ui.notify("반려 처리 되었습니다.");
									PAGE.GRID1.refresh();									
								} else {
									mvno.ui.alert(result.result.msg);									
								}
							});
						}, { lines: 5,  maxLength : 80} );
						
						break;
						
					case "btnSave":
						mvno.cmn.ajax(ROOT + "/org/userinfomgmt/updateUserApproval.json", form, function(result) {
							if (result.result.code == "OK") {
								mvno.ui.closeWindowById(form, true);
								mvno.ui.notify("승인 처리 되었습니다.");
								PAGE.GRID1.refresh();
							} else {
								mvno.ui.alert(result.result.msg);
							}
						});

						break;
						
					case "fileDown1" :
						mvno.cmn.download('/cmn/board/downFile.json?fileId=' + PAGE.FORM2.getItemValue("appFileId1"));
						break;
                           
					case "fileDown2" :
						mvno.cmn.download('/cmn/board/downFile.json?fileId=' + PAGE.FORM2.getItemValue("appFileId2"));
						break;
                           
					case "btnClose" :
						mvno.ui.closeWindowById(form);
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
			mvno.ui.createGrid("GRID2");
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0256"}, PAGE.FORM1, "appYn"); // 사용여부
			
			var searchEndDt = new Date().format('yyyymmdd');
			var time = new Date().getTime();
			time = time - 1000 * 3600 * 24 * 7;
			var searchStartDt = new Date(time);
			
			PAGE.FORM1.setItemValue("searchStartDt", searchStartDt);
			PAGE.FORM1.setItemValue("searchEndDt", searchEndDt);
			if(usrFindAuth == "N"){
				PAGE.FORM1.disableItem("btnUseFind");				
			} else {
				PAGE.FORM1.enableItem("btnUseFind");					
			}
		}
		
	};

	function setInputDataAddHyphen(form, data, itemname, type){
		 if(!form) return null;
		 if(data == null) return null;
		 if(!itemname) return data;

		var retValue  = "";
		 if(type == "rrnum"){
			 retValue = mvno.etc.setRrnumHyphen(data[itemname]);
		 } else if(type == "bizRegNum"){
			 retValue = mvno.etc.setBizRegNumHyphen(data[itemname]);
		 } else if(type == "tel"){
			 retValue = mvno.etc.setTelNumHyphen(data[itemname]);
		 }
		 
		var arrStr = retValue.split("-");
		var cnt = 1;
		for(i = 0; i < arrStr.length; i++){
			form.setItemValue(itemname+cnt,arrStr[i]);
			cnt++;
		}
	}

	</script>