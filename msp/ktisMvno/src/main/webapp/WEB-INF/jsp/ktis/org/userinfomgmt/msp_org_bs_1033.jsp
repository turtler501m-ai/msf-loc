<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1033.jsp
	 * @Description : 사용자맥주소 관리 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2018.09.28 권성광 최초생성
	 * @author : 권성광
	 * @Create Date : 2018. 9. 28.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
	 <div id="FORM2" class="section-box"></div>
	 <div id="FORM3" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

function getMsgByte(msg_str){
	var stringByteLength = 0;

	for(var i=0; i<msg_str.length; i++)
	{
		if(escape(msg_str.charAt(i)).length >= 4)
			stringByteLength += 3;
		else if(escape(msg_str.charAt(i)) == "%A7")
			stringByteLength += 3;
		else
			if(escape(msg_str.charAt(i)) != "%0D")
				stringByteLength++;
	}
	
	return stringByteLength;
}

	 var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			 items : [
				     {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
						{type: 'block', list: [
						    {type:"input", name:"orgnId", label:"조직", value: "", width:80, offsetLeft:10},
							{type:"newcolumn", offsetLeft:3},
							{type:"button", value:"검색", name:"btnOrgFind"},
							{type:"newcolumn", offsetLeft:3},
							{type:"input", name:"orgnNm", value:"", width:100, readonly: true},
							{type: 'newcolumn'},
							{type: 'select', name: 'orgType', label: '조직유형' ,width:80, labelWidth:60, offsetLeft:20},
							{type: 'newcolumn'},
							{type: 'input', name: 'usrId', label: '사용자ID', value: '', maxLength:10,width:90, offsetLeft:20},
							{type: 'newcolumn'},
							{type: 'input', name: 'usrNm', label: '사용자명', value: '', maxLength:10,width:90, offsetLeft:20},
						]
					},
					{type: 'newcolumn', offset:150},
					{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"},
				],
				onButtonClick : function(btnId) {
				
					var form = this;
					
					switch (btnId) {
					
						case "btnSearch":
							PAGE.GRID1.list(ROOT + "/org/userinfomgmt/userMacAddressMgmtList.json", form);
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
		//사용자맥주소리스트
		GRID1 : {
			css : {
					height : "560px"
			},
			title : "조회결과",
			header : "조직유형,조직명,사용자명,사용자ID,MAC ADDRESS,MAC NAME,MAC DESCRIPTION,usrId",
			columnIds : "orgTypeNm,orgnNm,usrNm,usrIdMsk,macAddr,macNm,macDesc,usrId",
			colAlign : "center,left,center,left,left,left,left,left",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str",
			widths : "80,100,80,80,150,150,300,0",
			hiddenColumns:[7],
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
					case "btnReg": // 등록버튼
						mvno.ui.createForm("FORM2");
					
						PAGE.FORM2.setFormData(data, true);
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("FORM2", "사용자맥주소 등록", 500, 280, {
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
						
						var data = selectedData;
						data["oldMacAddr"] = selectedData.macAddr;
						PAGE.FORM3.setFormData(data); // 등록(btnReg) 인 경우에는 selectedData 는 empty(undefined)!!
						PAGE.FORM3.clearChanged();
						
						mvno.ui.popupWindowById("FORM3", "사용자맥주소 수정", 500, 280, {
							onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
								});
							}
						});
						
						break;

					case "btnDel":
						var pUsrId = PAGE.GRID1.getSelectedRowData().usrId;
						var pMacAddr = PAGE.GRID1.getSelectedRowData().macAddr;
						
						mvno.cmn.ajax4confirm("CCMN0003", ROOT + "/org/userinfomgmt/deleteUserMacAddressMgmt.json", {usrId:pUsrId, macAddr:pMacAddr}, function(result){
						    if (result.result.code == "OK") {
								mvno.ui.notify("NCMN0003");
								PAGE.GRID1.refresh();
							} else {
								mvno.ui.alert(result.result.msg);
							}
						});
						
						break;
				}
			}
		},

		// --------------------------------------------------------------------------
		//사용자맥주소 등록 화면
		FORM2 : {
			title : "",
			items : [
				 {type: 'settings', position: 'label-left', labelWidth: '120', inputWidth: 'auto'},
				 {type: "block", blockOffset: 0, offsetLeft: 30, list: [
					 {type: 'input', name: 'usrId', label: '사용자ID',  width: 150,  value: '' , required: true, disabled:true, maxLength:10 ,validate: 'ValidAplhaNumeric'},
					 {type:"newcolumn"},
					 {type:"button", value:"찾기", name:"btnFindUser"}
				 ]},
				 {type: "block", blockOffset: 0,offsetLeft: 30, list: [
				     {type:"input", name:"usrNm", label: '사용자명', width:150, value:"", required: true, disabled:true}
				 ]},
				 {type: "block", blockOffset: 0,offsetLeft: 30, list: [
					 {type: 'input', name: 'macAddr', label: 'MAC ADDRESS',  width: 150, offsetLeft: 0, value: '' , maxLength:17, required: true}
				 ]},
				 {type: "block", blockOffset: 0,offsetLeft: 30, list: [
					 {type: 'input', name: 'macNm', label: 'MAC NAME',  width: 150, offsetLeft: 0, value: '', maxLength:100 }
				 ]},
				 {type: "block", blockOffset: 0, offsetLeft: 30,list: [
					 {type: 'input', name: 'macDesc', label: 'MAC DESCRIPTION',  width: 150, offsetLeft: 0, value: '', maxLength:100 }
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
					    var pUsrId = PAGE.FORM2.getItemValue("usrId");
						var pMacAddr = PAGE.FORM2.getItemValue("macAddr");
					 	
						// macAddr을 oldMacAddr로 줘서 변경하려는 값이 있는지 체크
					    mvno.cmn.ajax(ROOT + "/org/userinfomgmt/isExistMacAddress.json", {usrId:pUsrId, oldMacAddr:pMacAddr}, function(resultData) {
							if(resultData.result.resultCnt > 0){
								mvno.ui.alert("MAC 주소 중복입니다.");
							} else {
							    mvno.cmn.ajax4confirm("CCMN0001", ROOT + "/org/userinfomgmt/insertUserMacAddressMgmt.json", form, function(result) {
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

					case "btnFindUser":
						mvno.ui.popupWindow(ROOT+"/org/userinfomgmt/searchUserInfo.do", "사용자찾기", 860, 620, {
							param : {
								callback : function(result) {
									form.setItemValue("usrId", result.usrId);
									form.setItemValue("usrNm", result.usrNm);
								}
							}
						});
						break;
				}
			},
			onValidateMore : function (data){
			    if (!data.usrId){
				    this.pushError("data.usrId","사용자ID",["ICMN0001"]);
				    this.pushError("data.usrNm","사용자명",["ICMN0001"]);
				} else if(data.macAddr && !mvno.etc.checkMacNum(data.macAddr)){
					this.pushError("data.macAddr","MAC ADDRESS",["ICMN0012"]);
				} else if ( getMsgByte(data.macNm) > 100){
				    this.pushError("data.macNm", "MAC NAME은 100Byte 이내로 입력하시기 바랍니다.<br />MAC NAME : " + getMsgByte(data.macNm) + " Byte", "");
				} else if ( getMsgByte(data.macDesc) > 100){
				    this.pushError("data.macDesc", "MAC DESCRIPTION은 100Byte 이내로 <br />입력하시기 바랍니다.<br />MAC DESCRIPTION : " + getMsgByte(data.macDesc) + " Byte", "");
				}
			}
		},
		// --------------------------------------------------------------------------
		//사용자맥주소 수정 화면
		FORM3 : {
		    title : "",
			items : [
				 {type: 'settings', position: 'label-left', labelWidth: '120', inputWidth: 'auto'},
				 {type: "block", blockOffset: 0, offsetLeft: 30, list: [
					 {type: 'input', name: 'usrIdMsk', label: '사용자ID',  width: 150,  value: '' , required: true, disabled:true, maxLength:10 ,validate: 'ValidAplhaNumeric'},
					 {type: 'hidden', name: 'usrId'},
					 {type:"newcolumn"},
					 {type:"button", value:"찾기", name:"btnFindUser", disabled:true}
				 ]},
				 {type: "block", blockOffset: 0,offsetLeft: 30, list: [
				     {type:"input", name:"usrNm", label: '사용자명', width:150, value:"", required: true, disabled:true}
				 ]},
				 {type: "block", blockOffset: 0,offsetLeft: 30, list: [
					 {type: 'input', name: 'macAddr', label: 'MAC ADDRESS',  width: 150, offsetLeft: 0, value: '' , maxLength:17, required: true}
					 , {type : 'hidden', name: "oldMacAddr", value:""}
				 ]},
				 {type: "block", blockOffset: 0,offsetLeft: 30, list: [
					 {type: 'input', name: 'macNm', label: 'MAC NAME',  width: 150, offsetLeft: 0, value: '', maxLength:100 }
				 ]},
				 {type: "block", blockOffset: 0, offsetLeft: 30,list: [
					 {type: 'input', name: 'macDesc', label: 'MAC DESCRIPTION',  width: 150, offsetLeft: 0, value: '', maxLength:100 }
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
					    var pUsrId = PAGE.FORM3.getItemValue("usrId");
						var pMacAddr = PAGE.FORM3.getItemValue("macAddr");
						var pOldMacAddr = PAGE.FORM3.getItemValue("oldMacAddr");
						
						// macAddr을 oldMacAddr로 줘서 변경하려는 값이 있는지 체크
					    mvno.cmn.ajax(ROOT + "/org/userinfomgmt/isExistMacAddress.json", {usrId:pUsrId, oldMacAddr:pMacAddr}, function(resultData) {
							if(resultData.result.resultCnt > 0 && pMacAddr.toUpperCase() != pOldMacAddr.toUpperCase()){
								mvno.ui.alert("MAC 주소 중복입니다.");
							} else {
							    mvno.cmn.ajax4confirm("CCMN0002", ROOT + "/org/userinfomgmt/updatUserMacAddressMgmt.json", form, function(result) {
									if (result.result.code == "OK") {
										mvno.ui.closeWindowById(form, true);
											mvno.ui.notify("NCMN0002");
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

					case "btnFindUser":
						mvno.ui.popupWindow(ROOT+"/org/userinfomgmt/searchUserInfo.do", "사용자찾기", 860, 620, {
							param : {
								callback : function(result) {
									form.setItemValue("usrId", result.usrId);
									form.setItemValue("usrNm", result.usrNm);
								}
							}
						});
						break;
				}
			},
			onValidateMore : function (data){
			    if (!data.usrId){
				    this.pushError("data.usrId","사용자ID",["ICMN0001"]);
				    this.pushError("data.usrNm","사용자명",["ICMN0001"]);
				} else if(data.macAddr && !mvno.etc.checkMacNum(data.macAddr)){
					this.pushError("data.macAddr","MAC ADDRESS",["ICMN0012"]);
				} else if ( getMsgByte(data.macNm) > 100){
				    this.pushError("data.macNm", "MAC NAME은 100Byte 이내로 입력하시기 바랍니다.<br />MAC NAME : " + getMsgByte(data.macNm) + " Byte", "");
				} else if ( getMsgByte(data.macDesc) > 100){
				    this.pushError("data.macDesc", "MAC DESCRIPTION은 100Byte 이내로 <br />입력하시기 바랍니다.<br />MAC DESCRIPTION : " + getMsgByte(data.macDesc) + " Byte", "");
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
			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0003"}, PAGE.FORM1, "orgType"); // 조직유형
		 }
		
	};

</script>