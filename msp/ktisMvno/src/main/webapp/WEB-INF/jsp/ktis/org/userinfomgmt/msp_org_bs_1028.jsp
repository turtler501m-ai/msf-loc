<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1028.jsp
	 * @Description : 사용자 관리 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.08.14 장익준 최초생성
	 * @ 2014.08.26 고은정 수정
	 * @author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
	 <div id="FORM2" class="section-box"></div>
	 <div id="FORM3" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
	 var maskingYn = "${maskingYn}";				// 마스킹권한
	 var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			 items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
						{type: 'block', list: [
						{type: 'input', name: 'usrId', label: '사용자ID', value: '', maxLength:10,width:100, offsetLeft:10},
						{type: 'newcolumn'},
						{type: 'input', name: 'usrNm', label: '사용자명', value: '', maxLength:10,width:100,offsetLeft:40},
						{type: 'newcolumn'},
						{type: 'select', name: 'usgYn', label: '사용여부' ,width:100,offsetLeft:20},
						{type: 'newcolumn'},
						{type: 'select', name: 'attcSctnCd', label: '소속구분',width:100, offsetLeft:20}
					]},
					{type: 'block', list: [
						{type:"input", name:"orgnId", label:"조직", value: "", width:100, offsetLeft:10},
						{type:"newcolumn", offsetLeft:3},
						{type:"button", value:"검색", name:"btnOrgFind"},
						{type:"newcolumn", offsetLeft:3},
						{type:"input", name:"orgnNm", value:"", width:145, readonly: true},
						{type: 'newcolumn'},
						{type: 'select', name: 'dept', label: '부서/업체',width:100,offsetLeft:20}
					]},
					{type: 'newcolumn', offset:150},
					{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"},
				],
				onButtonClick : function(btnId) {
				
					var form = this;
					
					switch (btnId) {
					
						case "btnSearch":
						
								PAGE.GRID1.list(ROOT + "/org/userinfomgmt/userInfoMgmtList.json", form);
								break;
								
						case "btnFind":
								//사용자 찾기팝업(외부호출) 테스트 코드
								mvno.ui.codefinder("USER", function(result) {
									form.setItemValue("usrId",       result.usrId);
									form.setItemValue("usrNm",     result.usrNm);
								});
								break;
								
						case "btnOrgFind":
							mvno.ui.codefinder("ORG", function(result) {
								form.setItemValue("orgnId", result.orgnId);
								form.setItemValue("orgnNm", result.orgnNm);
							});
							break;
				}
			}
		},
		// ----------------------------------------
		//사용자리스트
		GRID1 : {
			css : {
					height : "530px"
			},
			title : "조회결과",
			header : "사용자ID,사용자명,조직명,사번,소속구분,부서,상태,마스킹권한,관리자롤,핸드폰번호,이메일,KOS_ID,MAC체크여부,lockYn",
			columnIds : "usrIdMsk,usrNmMsk,orgnNm,presBiz,attcSctnCdNm,deptNm,usgNm,mskAuthYn,mngrRule,mblphnNumMsk,emailMsk,kosIdMsk,macChkYn,lockYn",
			colAlign : "left,left,left,left,left,left,center,center,center,left,left,left,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,roXp,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str",
			widths : "120,120,150,130,80,100,90,80,90,130,120,120,120,0",
			hiddenColumns:[10,13],
			paging: true,
			pageSize:20,
			buttons : {
				right : {
					btnReg : { label : "등록" },
					btnMod : { label : "수정" },
					btnDel : { label : "삭제" }
				}
			},
			checkSelectedButtons : ["btnMod", "btnDel"],
			onRowDblClicked : function(rowId, celInd) {
				// 수정버튼 누른것과 같이 처리?
				this.callEvent('onButtonClick', ['btnMod']);
			},
			onButtonClick : function(btnId, selectedData) {
				switch (btnId) {
					case "btnReg":
						var today = new Date().format("yyyymmdd");
						mvno.ui.createForm("FORM2");
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0003"}, PAGE.FORM2, "attcSctnCd"); // 소속구분
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0006",orderBy:"etc1"}, PAGE.FORM2, "dept"); // 부서/업체명
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM2, "usgYn"); // 사용여부
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM2, "telNum1"); // 전화번호
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM2, "mblphnNum1"); // 핸드폰번호
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM2, "fax1"); // FAX
						
						var data = {
							usgStrtDt : today,
							usgEndDt : "99991231"
						}
						
						PAGE.FORM2.setFormData(data, true);
						
						mvno.ui.popupWindowById("FORM2", "사용자", 810, 430, {
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
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0003"}, PAGE.FORM3, "attcSctnCd"); // 소속구분
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0006",orderBy:"etc1"}, PAGE.FORM3, "dept"); // 부서/업체명
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0011",orderBy:"etc6"}, PAGE.FORM3, "usgYn"); // 사용여부
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0100",orderBy:"etc1"}, PAGE.FORM3, "usgYn2"); // 사용여부
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM3, "telNum1"); // 전화번호
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM3, "mblphnNum1"); // 핸드폰번호
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0014"}, PAGE.FORM3, "fax1"); // FAX
						
						var data = selectedData;
						data["oldPass"] = selectedData.pass;
						data["passChk"] = selectedData.pass;
						
						PAGE.FORM3.setFormData(data); // 등록(btnReg) 인 경우에는 selectedData 는 empty(undefined)!!
						
						if(selectedData.usgYn == "S") {
							PAGE.FORM3.hideItem("usgYn");
							PAGE.FORM3.showItem("usgYn2");
							PAGE.FORM3.setItemValue("usgYn2", "S");
						} else {
							PAGE.FORM3.hideItem("usgYn2");
							PAGE.FORM3.showItem("usgYn");
						}

						/* 핸드폰 번호, 전화번호, Fax 셋팅 */
						setInputDataAddHyphen(PAGE.FORM3, data, "mblphnNum", "tel");
						setInputDataAddHyphen(PAGE.FORM3, data, "telNum", "tel");
						setInputDataAddHyphen(PAGE.FORM3, data, "fax", "tel");

						PAGE.FORM3.clearChanged();

						mvno.ui.popupWindowById("FORM3", "사용자", 810, 450, {
							onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
								});
							}
						});
						
						if (maskingYn != "Y") {
							PAGE.FORM3.setItemValue("kosId", selectedData.kosIdMsk);
							PAGE.FORM3.setItemValue("usrNm", selectedData.usrNmMsk);
							PAGE.FORM3.setItemValue("email", selectedData.emailMsk);
							/* 핸드폰 번호, 전화번호, Fax 셋팅 */
							setInputDataAddHyphen(PAGE.FORM3, data, "mblphnNumMsk", "tel");
							setInputDataAddHyphen(PAGE.FORM3, data, "telNumMsk", "tel");
							setInputDataAddHyphen(PAGE.FORM3, data, "faxMsk", "tel");
							
							PAGE.FORM3.disableItem("kosId");
							PAGE.FORM3.disableItem("usrNm");
							PAGE.FORM3.disableItem("telNum1");
							PAGE.FORM3.disableItem("telNum2");
							PAGE.FORM3.disableItem("telNum3");
							PAGE.FORM3.disableItem("mblphnNum1");
							PAGE.FORM3.disableItem("mblphnNum2");
							PAGE.FORM3.disableItem("mblphnNum3");
							PAGE.FORM3.disableItem("fax1");
							PAGE.FORM3.disableItem("fax2");
							PAGE.FORM3.disableItem("fax3");
							PAGE.FORM3.disableItem("email");							
						}
						
						break;

					case "btnDel":
						var pUsrId = PAGE.GRID1.getSelectedRowData().usrId;
						
						mvno.ui.prompt("삭제 사유", function(pDelRsn) {
							mvno.cmn.ajax(ROOT + "/org/userinfomgmt/deleteUserInfoMgmt.json", {usrId:pUsrId, delRsn:pDelRsn}, function(result){
								mvno.ui.notify("NCMN0003");
								PAGE.GRID1.refresh();
							});
						}, { lines: 5,  maxLength : 80} );
						
						break;
				}
			}
		},

		// --------------------------------------------------------------------------
		//사용자 등록화면
		FORM2 : {
			title : "",
			items : [
				 {type: 'settings', position: 'label-left', labelWidth: '120', inputWidth: 'auto'},
				 {type: "block", blockOffset: 0, offsetLeft: 30, list: [
					 {type: 'input', name: 'usrId', label: '사용자ID',  width: 150,  value: '' , required: true, maxLength:10 ,validate: 'ValidAplhaNumeric'},
					 {type: "newcolumn"},
					 {type: 'input', name: 'usrNm', label: '사용자명',  width: 150, offsetLeft: 90, value: '' , maxLength:15, required: true}
				 ]},
				 {type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'orgnId', label: '조직ID',  width: 150, offsetLeft: 0,  value: '' , disabled:true},
					{type: "newcolumn"},
					{type:"button", name: 'btnOrgFind', value:"찾기"},
					{type: "newcolumn"},
					{type: 'input', name: 'orgnNm', label: '조직명',  width: 150, offsetLeft: 40, value: '' , disabled:true, required: true,validate: 'NotEmpty'},
				 ]},
				 {type: "block", blockOffset: 0,offsetLeft: 30, list: [
					 {type: 'select', name: 'attcSctnCd', label: '소속구분',  width: 150},
					 {type: "newcolumn"},
					 {type: 'select', name: 'dept', label: '부서/업체명',  width: 150, offsetLeft: 90}
				 ]},
				 {type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'presBiz', label: '사번',  width: 150,  value: '' , maxLength:20,validate: 'ValidAplhaNumeric'},
					{type: "newcolumn"},
					{type: 'select', name: 'usgYn', label: '사용여부',  width: 150, offsetLeft: 90, required: true},
				 ]},
				 {type: "block", blockOffset: 0, offsetLeft: 30,list: [
					 {type: 'input', name: 'pos', label: '직위',  width: 150,  value: '' , maxLength:30},
					 {type: "newcolumn"},
					 {type: 'input', name: 'odty', label: '직책',  width: 150, offsetLeft: 90, value: '' , maxLength:30}
				 ]},
				 {type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: "block", blockOffset: 0, list: [
						 {type: 'select', name: 'telNum1', label: '전화번호',  width: 62},
						 {type: "newcolumn"},
						 {type: 'input', name: 'telNum2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
						 {type: "newcolumn"},
						 {type: 'input', name: 'telNum3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
					 ]},
					 {type: "newcolumn"},
					 {type: "block", blockOffset: 0, list: [
						 {type: 'select', name: 'mblphnNum1', label: '핸드폰번호',  offsetLeft: 90, width: 62, required: true},
						 {type: "newcolumn"},
						 {type: 'input', name: 'mblphnNum2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
						 {type: "newcolumn"},
						 {type: 'input', name: 'mblphnNum3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
					 ]},
				 ]},
				 {type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: "block", blockOffset: 0, list: [
						 {type: 'select', name: 'fax1', label: 'FAX',  width: 62},
						 {type: "newcolumn"},
						 {type: 'input', name: 'fax2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
						 {type: "newcolumn"},
						 {type: 'input', name: 'fax3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
					 ]},
					 {type: "newcolumn"},
					 {type: 'input', name: 'email', label: '이메일',  width: 150, offsetLeft: 90, value: '' , maxLength:50, validate:"ValidEmail"}
				 ]},
				 {type: "block", blockOffset: 0, offsetLeft: 30,list: [
					 {type: 'calendar', name: 'usgStrtDt' , label: '적용시작일자',  dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d',   value:'',  calendarPosition: 'right' ,width:150, readonly:true, required: true},
					 {type: "newcolumn"},
					 {type: 'calendar', name: 'usgEndDt', label: '적용종료일자',  dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', offsetLeft: 90, value: '', calendarPosition: 'right',width:150, disabled:true}
				 ]},
				 {type: "block", blockOffset: 0,offsetLeft: 30, list: [
					 {type: 'input', name: 'kosId', label: 'KOS ID',  width: 150,  value: '' , maxLength:20,validate: 'ValidAplhaNumeric'},
					 {type: "newcolumn"},
					 {type: 'checkbox', name: 'macChkYn', label: 'MAC체크여부',    value:'Y',   offsetLeft: 90,  checked:false,  width: 150},
				 ]},
				 {type: "block", blockOffset: 0, offsetLeft: 30,list: [
					 {type: 'checkbox', name: 'mngrRule', label: '관리자롤',  checked: false},
					 {type: "newcolumn"},
					 {type: 'checkbox', name: 'mskAuthYn', label: '마스킹권한여부',   offsetLeft: 219,  checked:false},
				 ]},
			],
			buttons : {
				left : {
					btnInit : { label : "초기화" }
				},
				center : {
					btnSave : { label : "저장" },
					btnClose : { label: "닫기" }
				}
			},
			onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {
					case "btnInit":
						PAGE.FORM2.clear();
						break;

					case "btnSave":
						mvno.cmn.ajax(ROOT + "/org/userinfomgmt/isExistUsrId.json", {usrId:PAGE.FORM2.getItemValue("usrId")}, function(resultData) {
							if(resultData.result.resultCnt > 0){
								mvno.ui.alert("ICMN0008");
							} else {
								mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertUserInfoMgmt.json", form, function(result) {
									if (result.result.code == "OK") {
										mvno.ui.closeWindowById(form, true);
											mvno.ui.notify("NCMN0001");
											PAGE.GRID1.refresh();
									} else {
										mvno.ui.alert(result.result.msg);
									}
								});
							}
						});
						break;

					case "btnClose" :
						mvno.ui.closeWindowById(form);
						break;

					case "btnOrgFind":
						//상위조직검색
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("orgnId", result.orgnId);
							form.setItemValue("orgnNm", result.orgnNm);
							form.setItemValue("attcSctnCd",result.typeCd);
							form.disableItem("attcSctnCd");
						});
						break;

					case "btnFindEmp":
						mvno.ui.popupWindow(ROOT + "/org/userinfomgmt/searchEmpInfo.do", "직원찾기", '710', '570', {
							param : {
								callback : function(result) {
									form.setItemValue("usrId", result.usrId);
									form.setItemValue("usrNm", result.usrNm);
									form.setItemValue("presBiz",result.presBiz);
									form.setItemValue("pos",result.pos);
									form.setItemValue("orgnId",result.orgnId);
									form.setItemValue("orgnNm", result.orgnNm);
									form.enableItem("attcSctnCd");
									form.setItemValue("attcSctnCd","");
									setInputDataAddHyphen(PAGE.FORM2, result, "mblphnNum", "tel");
									}
							}
						});
						break;
				}
			},
			onValidateMore : function (data){
				if(data.btnExistFlag == "0"){
					this.pushError("data.usrId","사용자ID",["ICMN0010"]);
				}
				else if(!data.orgnId){
					this.pushError("data.orgnId","조직ID",["ORGN0006"]);
				}
				else if(!mvno.etc.checkPackNum(data.telNum1, data.telNum2 , data.telNum3)){
					this.pushError("data.telNum1","전화번호",["ICMN0011"]);
				}
				else if(data.telNum1 && !mvno.etc.checkPhoneNumber(data.telNum1+data.telNum2+data.telNum3)){
					this.pushError("data.telNum1","전화번호",["ICMN0012"]);
				}
				else if(!mvno.etc.checkPackNum(data.mblphnNum1, data.mblphnNum2 , data.mblphnNum3)){
					this.pushError("data.mblphnNum2","핸드폰번호",["ICMN0001"]);
				}
				else if(!mvno.etc.checkPhoneNumber(data.mblphnNum1+data.mblphnNum2+data.mblphnNum3)){
					this.pushError("data.mblphnNum2","핸드폰번호",["ICMN0012"]);
				}
				else if(!mvno.etc.checkPackNum(data.fax1, data.fax2 , data.fax3)){
					this.pushError("data.fax2","FAX",["ICMN0011"]);
				}
				else if(data.fax1 && !mvno.etc.checkPhoneNumber(data.fax1+data.fax2+data.fax3)){
					this.pushError("data.fax2","FAX",["ICMN0012"]);
				}
				else if(data.macAddr && !mvno.etc.checkMacNum(data.macAddr)){
					this.pushError("data.macAddr","MAC",["ICMN0012"]);
				}
				else if(data.usgStrtDt > data.usgEndDt){
					this.pushError("data.startDate","적용시작일자","적용종료일자보다 작아야합니다.");
				}
			}
		},

		 // --------------------------------------------------------------------------
		 //사용자수정화면
		 FORM3 : {
			title : "",
			items : [
				{type: 'settings', position: 'label-left', labelWidth: '100', inputWidth: 'auto'},
				{type: "block", blockOffset: 0, offsetLeft: 30,list: [
					{type: 'input', name: 'usrIdMsk', label: '사용자ID',  width: 150,  value: '' , disabled:true},
					{type: 'hidden', name: 'usrId'},
					{type: "newcolumn"},
					{type: 'input', name: 'usrNm', label: '사용자명',  width: 150, offsetLeft: 140, value: '' , maxLength:10, required: true},
				]},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'orgnId', label: '조직ID',  width: 150, offsetLeft: 0,  value: '' , disabled:true},
					{type: "newcolumn"},
					{type:"button", name: 'btnOrgFind', width:'40', value:"찾기"},
					{type: "newcolumn"},
					{type: 'input', name: 'orgnNm', label: '조직명',  width: 150, offsetLeft: 74, value: '' , disabled:true, required: true},
				]},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					 {type: 'select', name: 'attcSctnCd', label: '소속구분',  width: 150},
					 {type: "newcolumn"},
					 {type: 'select', name: 'dept', label: '부서/업체명',  width: 150, offsetLeft: 140}
				]},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					 {type: "block", blockOffset: 0, list: [
						 {type: 'password', name: 'pass', label: '비밀번호',  width: 150,  value: '' , maxLength:22},
						 {type: 'hidden', name: 'oldPass', label: '', value: '', width:100},
						 {type: "newcolumn"},
						 {type: 'button', value: '초기화', width:'40', name: 'btnInit' }
					 ]},
					 {type: "newcolumn"},
					 {type: 'password', name: 'passChk', label: '비밀번호확인',  width: 150, offsetLeft: 74, value: '' , maxLength:22},
				]},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'presBiz', label: '사번',  width: 150,  value: '' , maxLength:20,validate: 'ValidAplhaNumeric'},
					{type: "newcolumn"},
					{type: 'select', name: 'usgYn', label: '사용여부',  width: 150, offsetLeft: 140, required: true},
					 {type: 'select', name: 'usgYn2', label: '사용여부',  width: 150, offsetLeft: 140, disabled:true}
				]},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'pos', label: '직위',  width: 150,  value: '' , maxLength:30},
					{type: "newcolumn"},
					{type: 'input', name: 'odty', label: '직책',  width: 150, offsetLeft: 140, value: '' , maxLength:30}
				]},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: "block", blockOffset: 0, list: [
						{type: 'select', name: 'telNum1', label: '전화번호',  width: 62},
						{type: "newcolumn"},
						{type: 'input', name: 'telNum2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
						{type: "newcolumn"},
						{type: 'input', name: 'telNum3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
					]},
					{type: "newcolumn"},
					{type: "block", blockOffset: 0, list: [
							{type: 'select', name: 'mblphnNum1', label: '핸드폰번호',  offsetLeft: 140, width: 62, required: true},
							{type: "newcolumn"},
							{type: 'input', name: 'mblphnNum2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
							{type: "newcolumn"},
							{type: 'input', name: 'mblphnNum3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
					]},
				]},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: "block", blockOffset: 0, list: [
						{type: 'select', name: 'fax1', label: 'FAX',  width: 62},
						{type: "newcolumn"},
						{type: 'input', name: 'fax2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
						{type: "newcolumn"},
						{type: 'input', name: 'fax3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
					]},
					{type: "newcolumn"},
					{type: 'input', name: 'email', label: '이메일',  width: 150, offsetLeft: 140, value: '' , maxLength:50, validate:"ValidEmail"}
				]},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'calendar', name: 'usgStrtDt' , label: '적용시작일자',  dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d',   value:'',  calendarPosition: 'right' ,width:150, readonly:true,required: true},
					{type: "newcolumn"},
					{type: 'calendar', name: 'usgEndDt', label: '적용종료일자',  dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', offsetLeft: 140, value: '', calendarPosition: 'right',width:150, readonly:true,required: true},
				]},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'kosId', label: 'KOS ID',  width: 150,  value: '' , maxLength:20,validate: 'ValidAplhaNumeric'},
					{type: "newcolumn"},
					{type: 'checkbox', name: 'macChkYn', label: 'MAC체크여부',    value:'Y',   offsetLeft: 140,  width: 150},
				]},
				{type: "block", blockOffset: 0, offsetLeft: 30,list: [
					{type: 'checkbox', name: 'mngrRule', label: '관리자롤',    value:'Y'},
					{type: "newcolumn"},
					{type: 'checkbox', name: 'mskAuthYn', label: '마스킹권한여부',   offsetLeft: 268, value:'Y'},
				]},
				{type: 'hidden', name: 'lockYn'},
			],

			buttons : {
				left : {
					btnMacDel: { label : "MAC삭제" },
					btnLock : { label : "잠금상태해제" }
				},
				center : {
					btnSave : { label : "저장" },
					btnClose : { label: "닫기" }
				},
			},

			onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				switch (btnId) {

					case "btnMacDel":
						var usrId = PAGE.FORM3.getItemValue("usrId");
						if( !usrId ){
							mvno.ui.alert("사용자ID를 확인해주세요.");
							return;
						}
						
						mvno.cmn.ajax(ROOT + "/org/userinfomgmt/macAddressDelete.json", {usrId:PAGE.FORM3.getItemValue("usrId")}, function(resultData) {
							if(resultData.result.resultCnt > 0){
								mvno.ui.alert("등록된 MAC 정보를 삭제 했습니다.");
							}else{
								mvno.ui.alert("등록된 MAC 정보가 없습니다.");
							}
						});
						break;
						
					case "btnInit":
						//비밀번호 초기화
						mvno.cmn.ajax4confirm("CCMN0006", ROOT + "/org/userinfomgmt/initUserPassword.json", {usrId:form.getItemValue("usrId")},
							function(result) {
									mvno.ui.closeWindowById(form, true);
									mvno.ui.notify("NCMN0005");
							});
							break;
							
					case "btnSave":
						mvno.cmn.ajax(ROOT + "/org/userinfomgmt/updateUserInfoMgmtAdm.json", form, function(result) {
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

					case "btnOrgFind":
						//상위조직검색
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("orgnId", result.orgnId);
							form.setItemValue("orgnNm", result.orgnNm);
							form.setItemValue("attcSctnCd",result.typeCd);
							form.disableItem("attcSctnCd");
						});
						break;

					case "btnLock":
						var lockYn = PAGE.FORM3.getItemValue("lockYn");
						if(lockYn != "Y" ){
							mvno.ui.alert("잠금상태가 되어있지 않습니다.");
							return;
						}
						
						mvno.cmn.ajax(ROOT + "/org/userinfomgmt/updateLockStatus.json", {usrId:PAGE.FORM3.getItemValue("usrId")}, function(resultData) {
							if (resultData.result.code == "OK") {
								mvno.ui.closeWindowById(form, true);
								mvno.ui.alert("잠금상태가 해지되었습니다.");
								PAGE.GRID1.refresh();
							} else {
								mvno.ui.alert(resultData.result.msg);
							}
						});
					break;
				}
			},
			onValidateMore : function (data){
				if(!data.orgnId){
					this.pushError("data.orgnId","조직ID",["ORGN0006"]);
				}
				else if(data.pass != data.passChk){
					this.pushError("data.pass","비밀번호","일치하지 않습니다.");
				}
				else if(!mvno.etc.checkPackNum(data.telNum1, data.telNum2 , data.telNum3)){
					this.pushError("data.telNum1","전화번호",["ICMN0011"]);
				}
				else if(data.telNum1 && !mvno.etc.checkPhoneNumber(data.telNum1+data.telNum2+data.telNum3)){
					this.pushError("data.telNum1","전화번호",["ICMN0012"]);
				}
				else if(!mvno.etc.checkPackNum(data.mblphnNum1, data.mblphnNum2 , data.mblphnNum3)){
					this.pushError("data.mblphnNum2","핸드폰번호",["ICMN0001"]);
				}
				else if(!mvno.etc.checkPhoneNumber(data.mblphnNum1+data.mblphnNum2+data.mblphnNum3)){
					this.pushError("data.mblphnNum2","핸드폰번호",["ICMN0012"]);
				}
				else if(!mvno.etc.checkPackNum(data.fax1, data.fax2 , data.fax3)){
					this.pushError("data.telNum2","FAX",["ICMN0011"]);
				}
				else if(data.fax1 && !mvno.etc.checkPhoneNumber(data.fax1+data.fax2+data.fax3)){
					this.pushError("data.telNum1","FAX",["ICMN0012"]);
				}
				else if(data.macAddr && !mvno.etc.checkMacNum(data.macAddr)){
					this.pushError("data.macAddr","MAC",["ICMN0012"]);
				}
				else if(data.usgStrtDt > data.usgEndDt){
					this.pushError("data.startDate","적용시작일자","적용종료일자보다 작아야합니다.");
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
			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0100",orderBy:"etc1"}, PAGE.FORM1, "usgYn"); // 사용여부
			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0003"}, PAGE.FORM1, "attcSctnCd"); // 소속구분
			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0006",orderBy:"etc1"}, PAGE.FORM1, "dept"); // 부서/업체
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
		 
		var _itemname = itemname.replaceAll('Msk','');
		var arrStr = retValue.split("-");
		var cnt = 1;
		for(i = 0; i < arrStr.length; i++){
			form.setItemValue(_itemname+cnt,arrStr[i]);
			cnt++;
		}
	}

	function getDateDiff(date1, date2){
		var getDate1 = new Date(date1.substr(0,4), date1.substr(4,2)-1, date1.substr(6,2));
		var getDate2 = new Date(date2.substr(0,4), date2.substr(4,2)-1, date2.substr(6,2));
		
		var getDiffTime = getDate2.getTime() - getDate1.getTime();
		
		return Math.floor(getDiffTime / (1000 * 60 * 60 * 24));
	}
</script>