<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1032.jsp
	 * @Description : 판매점 사용자 관리 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2018.06.14 장익준 최초생성
	 * @author : 장익준
	 * @Create Date : 2018. 6. 14.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
	 <div id="FORM2" class="section-box"></div>
	 <div id="FORM3" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
	 var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			 items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
						{type: 'block', list: [
							{type:"input", name:"orgnId", label:"판매점", labelWidth:45, value: "", width:90, offsetLeft:10},
							{type:"newcolumn", offsetLeft:3},
							{type:"button", value:"검색", name:"btnOrgFind"},
							{type:"newcolumn", offsetLeft:3},
							{type:"input", name:"orgnNm", value:"", width:125, readonly: true},
							{type: 'newcolumn'},
							{type: 'input', name: 'usrId', label: '사용자ID', value: '', maxLength:10,width:90, offsetLeft:20},
							{type: 'newcolumn'},
							{type: 'input', name: 'usrNm', label: '사용자명', value: '', maxLength:10,width:90, offsetLeft:20},
							{type: 'newcolumn'},
							{type: 'select', name: 'usgYn', label: '사용자상태' ,width:70, labelWidth:60, offsetLeft:20}
						]
					},
					{type: 'newcolumn', offset:150},
					{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"},
				],
				onButtonClick : function(btnId) {
				
					var form = this;
					
					switch (btnId) {
					
						case "btnSearch":
								PAGE.GRID1.list(ROOT + "/org/userinfomgmt/shopUserInfoMgmtList.json", form);
								break;
								
						case "btnOrgFind":
							mvno.ui.codefinder("ORGSHOPNEW", function(result) {
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
					height : "564px"
			},
			title : "조회결과",
			header : "대리점ID,대리점명,판매점ID,판매점명,사용자ID,사용자명,핸드폰번호,사용자상태,패스워드상태,최종로그인,usrId",
			columnIds : "orgnHgId,orgnHgNm,orgnId,orgnNm,usrIdMsk,usrNm,mblphnNum,usgNm,passInit,lastLoginDt,usrId",
			colAlign : "left,left,left,left,left,left,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str",
			widths : "80,150,80,150,80,130,110,80,75,129,0",
			hiddenColumns:[5,10],
			paging: true,
			pageSize:20,
			buttons : {
				left : {
					btnStatusInit : { label : "정지상태해제" },
					btnPassInit: { label : "패스워드초기화" }
				},
				right : {
					btnReg : { label : "등록"},
					btnMod : { label : "수정"},
					btnDel : { label : "삭제"}
				}
			},
			checkSelectedButtons : ["btnStatusInit","btnPassInit","btnMod", "btnDel"],
			onRowDblClicked : function(rowId, celInd) {
				// 수정버튼 누른것과 같이 처리?
				/* this.callEvent('onButtonClick', ['btnMod']); */
			},
			onButtonClick : function(btnId, selectedData) {
				switch (btnId) {
					case "btnStatusInit" :
						if(selectedData.usgYn == "S") {
							mvno.cmn.ajax4confirm("CCMN0007", ROOT + "/org/userinfomgmt/updateStopStatusInit.json", selectedData, 
									function(resultData) {
										if (resultData.result.code == "OK") {
											mvno.ui.notify("NCMN0002");
											PAGE.GRID1.refresh();
										} else {
											mvno.ui.alert(resultData.result.msg);
										}		
							});
						} else {
							mvno.ui.alert("ICMN0016");
						}
						break;
					case "btnPassInit" :
						if(selectedData.passInit == "N") {
							mvno.cmn.ajax4confirm("CCMN0006", ROOT + "/org/userinfomgmt/initShopUserPassword.json", {usrId:selectedData.usrId},
									function(resultData) {
										if (resultData.result.code == "OK") {
											mvno.ui.notify("NCMN0005");
											PAGE.GRID1.refresh();
										} else {
											mvno.ui.alert(resultData.result.msg);
										}
							});
						} else {
							mvno.ui.alert("ICMN0017");
						}
						
						break;
					case "btnReg":
						mvno.ui.createForm("FORM2");
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM2, "mblphnNum1"); // 핸드폰번호
																		
						mvno.ui.popupWindowById("FORM2", "사용자", 460, 300, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
								});
							}
						});
						
						break;
					case "btnMod":
						if(selectedData.usgYn == "N") {
							mvno.ui.alert("ICMN0015");
						} else {
							mvno.ui.createForm("FORM3");
							
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM3, "mblphnNum1"); // 핸드폰번호
							
							PAGE.FORM3.setFormData(selectedData); // 등록(btnReg) 인 경우에는 selectedData 는 empty(undefined)!!
							
							setInputDataAddHyphen(PAGE.FORM3, selectedData, "mblphnNum", "tel");

							PAGE.FORM3.clearChanged();

							mvno.ui.popupWindowById("FORM3", "사용자", 460, 300, {
								onClose: function(win) {
									if (! PAGE.FORM3.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
											win.closeForce();
									});
								}
							});
						}
						
						break;

					case "btnDel":
						var pUsrId = PAGE.GRID1.getSelectedRowData().usrId;
						
						mvno.ui.prompt("삭제 사유", function(pDelRsn) {
							mvno.cmn.ajax(ROOT + "/org/userinfomgmt/deleteShopUserInfoMgmt.json", {usrId:pUsrId, delRsn:pDelRsn}, function(result){
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
					{type: 'input', name: 'usrId', label: '사용자ID',  width: 150,  value: '' , required: true, maxLength:10 ,validate: 'ValidAplhaNumeric'}
				]},
				{type: "block", blockOffset: 0, offsetLeft: 30, list: [
   					{type: 'input', name: 'usrNm', label: '사용자명',  width: 150, value: '' , maxLength:15, required: true}
   				]},
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
					{type: 'input', name: 'orgnId', label: '판매점ID',  width: 150, offsetLeft: 0,  value: '' , disabled:true},
					{type: "newcolumn"},
					{type:"button", name: 'btnOrgFind', value:"찾기"}
				]},
				{type: "block", blockOffset: 0, offsetLeft: 30, list: [
   					{type: 'input', name: 'orgnNm', label: '판매점명',  width: 150, value: '' , disabled:true, required: true,validate: 'NotEmpty'}
   				]},				
				{type: "block", blockOffset: 0,offsetLeft: 30, list: [	
					{type: "block", blockOffset: 0, list: [
						 {type: 'select', name: 'mblphnNum1', label: '핸드폰번호',  width: 62, required: true},
						 {type: "newcolumn"},
						 {type: 'input', name: 'mblphnNum2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
						 {type: "newcolumn"},
						 {type: 'input', name: 'mblphnNum3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4}
					]},
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
						mvno.cmn.ajax(ROOT + "/org/userinfomgmt/isExistShopUsrId.json", {usrId:PAGE.FORM2.getItemValue("usrId")}, function(resultData) {
							if(resultData.result.resultCnt > 0){
								mvno.ui.alert("ICMN0014");
							} else {
								mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertShopUserInfoMgmt.json", form, function(result) {
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
						mvno.ui.codefinder("ORGSHOPNEW", function(result) {
							form.setItemValue("orgnId", result.orgnId);
							form.setItemValue("orgnNm", result.orgnNm);
						});
						break;
				}
			},
			onValidateMore : function (data){
				if(!data.orgnId){
					this.pushError("data.orgnId","판매점ID",["ORGN0006"]);
				}
				else if(!mvno.etc.checkPackNum(data.mblphnNum1, data.mblphnNum2 , data.mblphnNum3)){
					this.pushError("data.mblphnNum2","핸드폰번호",["ICMN0001"]);
				}
				else if(!mvno.etc.checkPhoneNumber(data.mblphnNum1+data.mblphnNum2+data.mblphnNum3)){
					this.pushError("data.mblphnNum2","핸드폰번호",["ICMN0012"]);
				}
			}
		},

		 // --------------------------------------------------------------------------
		 //사용자수정화면
		 FORM3 : {
			title : "",
			items : [
				{type: 'settings', position: 'label-left', labelWidth: '100', inputWidth: 'auto'},
				{type: "block", blockOffset: 0, offsetLeft: 30, list: [
   					{type: 'input', name: 'usrId', label: '사용자ID',  width: 150,  value: '' , disabled:true}
   				]},
   				{type: "block", blockOffset: 0, offsetLeft: 30, list: [
      				{type: 'input', name: 'usrNm', label: '사용자명',  width: 150, value: '' , maxLength:15, required: true}
      			]},
   				{type: "block", blockOffset: 0,offsetLeft: 30, list: [
   					{type: 'input', name: 'orgnId', label: '판매점ID',  width: 150, offsetLeft: 0,  value: '' , disabled:true},
   					{type: "newcolumn"},
   					{type:"button", name: 'btnOrgFind', value:"찾기"}
   				]},
   				{type: "block", blockOffset: 0, offsetLeft: 30, list: [
      				{type: 'input', name: 'orgnNm', label: '판매점명', width: 150, value: '' , disabled:true, required: true}
      			]},				
   				{type: "block", blockOffset: 0,offsetLeft: 30, list: [	
   					{type: 'select', name: 'mblphnNum1', label: '핸드폰번호',  width: 62, required: true},
					{type: "newcolumn"},
				 	{type: 'input', name: 'mblphnNum2', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4},
				 	{type: "newcolumn"},
				 	{type: 'input', name: 'mblphnNum3', label: '',  width: 40, offsetLeft: 0, value: '' ,validate: 'ValidInteger', maxLength:4}
   				]}
      				
			],

			buttons : {
				left : {
				},
				center : {
					btnSave : { label : "저장" },
					btnClose : { label: "닫기" }
				}
			},

			onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				switch (btnId) {
							
					case "btnSave":
						mvno.cmn.ajax(ROOT + "/org/userinfomgmt/updateShopUserInfoMgmt.json", form, function(result) {
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
						mvno.ui.codefinder("ORGSHOPNEW", function(result) {
							form.setItemValue("orgnId", result.orgnId);
							form.setItemValue("orgnNm", result.orgnNm);
						});
						break; 
				}
			},
			onValidateMore : function (data){
				if(!data.orgnId){
					this.pushError("data.orgnId","조직ID",["ORGN0006"]);
				}
				else if(!mvno.etc.checkPackNum(data.mblphnNum1, data.mblphnNum2 , data.mblphnNum3)){
					this.pushError("data.mblphnNum2","핸드폰번호",["ICMN0001"]);
				}
				else if(!mvno.etc.checkPhoneNumber(data.mblphnNum1+data.mblphnNum2+data.mblphnNum3)){
					this.pushError("data.mblphnNum2","핸드폰번호",["ICMN0012"]);
				}
			}
		}
	};

	var PAGE = {
		 title : "${breadCrumb.title}",
		 breadcrumb : " ${breadCrumb.breadCrumb}",
		 buttonAuth: ${buttonAuth.jsonString},
		 onOpen: function() {
			 mvno.ui.createForm("FORM1");
			 mvno.ui.createGrid("GRID1");
			 
			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0100",orderBy:"etc1"}, PAGE.FORM1, "usgYn"); // 사용여부
			 
			 if('${authModYn}' == "N") {
				 mvno.ui.hideButton("GRID1" , "btnReg");
				 mvno.ui.hideButton("GRID1" , "btnMod");
				 mvno.ui.hideButton("GRID1" , "btnDel");
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