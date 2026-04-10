<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : rcpMgmtUsimChangeDlvry.jsp
	 * @Description : 신청정보(유심교체_
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2025.10.28      최초생성
	 * @Create Date : 2025.10.28
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>
<div id="GRID2" class="section-full"></div>
<div id="POPUP1">
	<div id="POPUP1TOP"  class="section-full"></div>
	<div id="POPUP1MID" class="section-box"></div>
</div>
<div id="POPUP2">
	<div id="POPUP2TOP"  class="section-full"></div>
	<div id="POPUP2MID" class="section-box"></div>
</div>
<div id="POPUP3">
	<div id="POPUP3TOP"  class="section-full"></div>
	<div id="POPUP3MID" class="section-box"></div>
</div>
<div id="FORM2" class="section-box"></div>
<div id="FORM3" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
	var isResetFlag = "Y";
	var maskingYn = "${maskingYn}";

	var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},
				{type:"block", list:[
						{type:"calendar",width : 100,label:"신청일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",  value:"${srchStrtDt}",name : "srchStrtDt", offsetLeft:10, readonly:true, labelWidth: 70, required:true},
						{type:"newcolumn"},
						{type:"calendar",label : "~",name:"srchEndDt",labelAlign : "center",dateFormat : "%Y-%m-%d",  value:"${srchEndDt}",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true},
						{type:"newcolumn"},
						{type:"select", label:"검색구분", name:"searchGbn",  width:100, offsetLeft:53},
						{type:"newcolumn"},
						{type:"input", name:"searchName", width:130},
					]
				},
				{type : "block", list : [
						{type:"select", name:"chnlCd", label:"신청경로", width:100, offsetLeft:10, labelWidth: 70},
						{type:"newcolumn"},
						{type:"select", name:"dlvryYn", label:"송장번호", width:100, offsetLeft:10},
						{type:"newcolumn"},
						{type:"select", name:"procYn", label:"처리여부", width:100, offsetLeft:10},
						{type:"newcolumn"},
						{type:"select", name:"status", label:"진행상태", width:100, offsetLeft:10},
						{type:"newcolumn"},
						{type:"select", name:"nfcUsimYn", label:"유심종류", width:100, offsetLeft:10}
					]
				},
				{type:"newcolumn", offset:150},
				{type : 'hidden', name: "DWNLD_RSN", value:""},
				{type : 'hidden', name: "btnCount1", value:"0"},
				{type:"button", value:"조회", name:"btnSearch", className:"btn-search2"},
			],
			onValidateMore : function(data) {

				if (data.srchStrtDt > data.srchEndDt) {
					this.pushError("srchStrtDt", "조회기간", "시작일자가 종료일자보다 클 수 없습니다.");
				}

				if(data.searchGbn != "" && data.searchName.trim() == ""){
					this.pushError("searchName", "검색구분", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
				}

			},
			onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/selectUsimChangeMstList.json", form);

						PAGE.GRID2.clearAll();

						break;
				}
			},
			onChange : function(name, value) {
				// 검색구분
				if(name == "searchGbn") {
					PAGE.FORM1.setItemValue("searchName", "");

					if(value == null || value == "" ) {

						var endDate = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var startDate = new Date(time);

						PAGE.FORM1.enableItem("srchStrtDt");
						PAGE.FORM1.enableItem("srchEndDt");

						PAGE.FORM1.setItemValue("srchStrtDt", startDate);
						PAGE.FORM1.setItemValue("srchEndDt", endDate);
						PAGE.FORM1.disableItem("searchName");

					} else {
						PAGE.FORM1.setItemValue("srchStrtDt", "");
						PAGE.FORM1.setItemValue("srchEndDt", "");

						PAGE.FORM1.disableItem("srchStrtDt");
						PAGE.FORM1.disableItem("srchEndDt");

						PAGE.FORM1.enableItem("searchName");
					}
				}
			}
		},
		// ----------------------------------------
		GRID1 : {
			css : {
				height : "320px"
			},
			title : "조회결과",
			header : "주문번호,신청경로,고객명,수량,신청일자,접수시간,배송연락처,상태,처리여부,메모,처리일시,송장번호",
			columnIds : "seq,chnlNm,customerLinkName,reqQnty,reqInDay,reqInTime,dlvryTel,statusNm,procYnNm,procMemo,procDttm,dlvryNo",
			colAlign : "center,center,left,right,center,center,center,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,roXd,ro,ro,ro,ro,ro,roXdt,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str",
			widths : "70,70,100,40,80,80,90,60,60,100,120,100",
			// hiddenColumns : [0],
			paging: true,
			pageSize:20,
			multiCheckbox : true,
			showPagingAreaOnInit: true,
			buttons : {
				left : {
					btnDnExcelUp : { label : "엑셀업로드양식"}
					,btnSmsSendDlvry : { label : "SMS(배송안내)" }
					,btnSmsSendChange : { label : "SMS(교체독려)" }
				}
				,hright : {
					btnExcel : { label : "엑셀다운로드" }
					,btnExcelDtl : { label : "엑셀상세다운로드" }
				}
				,right : {
					btnReg : { label : "직접등록" }
					,btnDlvryUpload : { label : "일련번호/송장업로드" }
					,btnProcUpload : { label : "처리여부/메모등록" }
					/*  2025-11-10 해당기능 미사용 (ESIM 직접등록으로 처리) - 추후 다시 정리되면 진행 
					 ,btnEsimUpload : { label : "ESIM 엑셀등록" }
					 */ 
				}
			},
			onRowDblClicked : function(rowId, celInd) {
				this.callEvent('onButtonClick', ['btnMod']);
			},
			onRowSelect:function(rId, cId){
				var grid = this;
				var sdata = grid.getSelectedRowData();
				var data = {
					seq : sdata.seq
				}
				PAGE.GRID2.list(ROOT + "/rcp/rcpMgmt/selectUsimChangeDtlList.json", data);
			},
			onButtonClick : function(btnId, selectedData) {

				switch (btnId) {

					case "btnDnExcelUp":

						mvno.ui.createForm("FORM3");
						PAGE.FORM3.clear();

						mvno.ui.popupWindowById("FORM3", "엑셀업로드양식", 400, 300, {
							onClose: function(win) {
								if (! PAGE.FORM3.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});

						break;
					
					case "btnSmsSendDlvry" :

						var rowIds = PAGE.GRID1.getCheckedRows(0);
						if(!rowIds) {
							mvno.ui.alert("ECMN0003");
							return;
						}

						var now = new Date();
						var hour = Number(now.getHours());
						var min = now.getMinutes();
						var sec = now.getSeconds();
						if(min < 10) {
							min = "0" + min;
						}
						if(sec < 10) {
							sec = "0" + sec;
						}
						var time = Number(now.getHours() + min + sec);
						if(hour < 8 || time > 210000) {
							mvno.ui.alert("문자발송은 오전 8시에서 오후 9시까지 가능합니다.");
							return;
						}

						var confirmMsg = "[ " + PAGE.GRID1.getCheckedRowData().length + " ] 건을 발송 하시겠습니까?";
						var sdata = PAGE.GRID1.classifyRowsFromIds(rowIds);
						for(var i=0; i<sdata.ALL.length; i++){
							sdata.ALL[i]["templateId"] = "423";
						}

						mvno.ui.confirm(confirmMsg, function() {
							mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/usimChangeSmsSend.json", sdata, function() {
								mvno.ui.notify("SMS를 발송하였습니다.");
								PAGE.GRID1.refresh();
							});
						});

						break;

					case "btnSmsSendChange" :

						var rowIds = PAGE.GRID1.getCheckedRows(0);
						if(!rowIds) {
							mvno.ui.alert("ECMN0003");
							return;
						}

						var now = new Date();
						var hour = Number(now.getHours());
						var min = now.getMinutes();
						var sec = now.getSeconds();
						if(min < 10) {
							min = "0" + min;
						}
						if(sec < 10) {
							sec = "0" + sec;
						}
						var time = Number(now.getHours() + min + sec);
						if(hour < 8 || time > 210000) {
							mvno.ui.alert("문자발송은 오전 8시에서 오후 9시까지 가능합니다.");
							return;
						}

						var confirmMsg = "[ " + PAGE.GRID1.getCheckedRowData().length + " ] 건을 발송 하시겠습니까?";
						var sdata = PAGE.GRID1.classifyRowsFromIds(rowIds);
						for(var i=0; i<sdata.ALL.length; i++){
							sdata.ALL[i]["templateId"] = "424";
						}

						mvno.ui.confirm(confirmMsg, function() {
							mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/usimChangeSmsSend.json", sdata, function() {
								mvno.ui.notify("SMS를 발송하였습니다.");
								PAGE.GRID1.refresh();
							});
						});

						break;

					case "btnExcel" :

						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}

						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download(ROOT + "/rcp/rcpMgmt/selectUsimChangeMstListExcel.json?menuId=MSP1010029", true, {postData:searchData});
						break;

					case "btnExcelDtl" :

						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}

						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download(ROOT + "/rcp/rcpMgmt/selectUsimChangeDtlListExcel.json?menuId=MSP1010029", true, {postData:searchData});
						break;
						
					case "btnReg" :

						isResetFlag = "Y";
						mvno.ui.createForm("FORM2");
						//핸드폰번호 text 왼쪽정렬
						PAGE.FORM2.getInput("searchSubscriberNo").style.textAlign = "left";

						PAGE.FORM2.showItem("btnT01");
						mvno.ui.showButton("FORM2" , 'btnSave');
						mvno.ui.hideButton("FORM2" , 'btnMod');
						
						PAGE.FORM2.enableItem("searchSubscriberNo");
						PAGE.FORM2.enableItem("chnlCd");
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"UsimReqChnlCd" ,etc1:"Y"}, PAGE.FORM2, "chnlCd");
						
						//초기화 
						PAGE.FORM2.setFormData(true);
						var deleteTargets = [];
						PAGE.FORM2.forEachItem(function(name) {
							if (name.startsWith("newBlock_") ||
								name.startsWith("contractNum_") ||
								name.startsWith("subscriberNo_") ||
								name.startsWith("preChkRsltCd_") ||
								name.startsWith("preChkRsltMsg_") ||
								name.startsWith("usimModelCd_") ||
								name.startsWith("nfcUsimType_") ||
								name.startsWith("usimModelId_") ||
								name.startsWith("prdtCode_") ||
								name.startsWith("usimSn_") ||
								name.startsWith("btnDelete_")) {
								deleteTargets.push(name);
							}
						});

						deleteTargets.forEach(function(name) {
							PAGE.FORM2.removeItem(name);
						});
						
						PAGE.FORM2.clearChanged();
						
						mvno.ui.popupWindowById("FORM2", "직접등록", 940, 600, {
							onClose: function (win) {
								win.closeForce();
							}
						});

						var currentChnlCd = PAGE.FORM2.getItemValue("chnlCd");
						PAGE.FORM2.callEvent("onChange", ["chnlCd", currentChnlCd]);
						
						break;

					case "btnMod" :
						
						var sData = PAGE.GRID1.getSelectedRowData();

						isResetFlag = "N";
						if(sData == null){
							mvno.ui.alert("ECMN0003");
							return;
						}
						
						mvno.ui.createForm("FORM2");
						//핸드폰번호 text 왼쪽정렬
						PAGE.FORM2.getInput("searchSubscriberNo").style.textAlign = "left";
						
						PAGE.FORM2.showItem("btnMod");
						//초기화 
						PAGE.FORM2.clear();
						PAGE.FORM2.setFormData(true);
						PAGE.FORM2.clearChanged();

						mvno.ui.showButton("FORM2" , 'btnMod');
						mvno.ui.hideButton("FORM2" , 'btnSave');
						PAGE.FORM2.hideItem("btnT01");						
						
						PAGE.FORM2.setItemValue("seq", sData.seq);

						mvno.ui.popupWindowById("FORM2", "수정", 940, 600, {
							onClose: function (win) {								
								win.closeForce();
							}
						});
						
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/selectUsimChgInfo.json", PAGE.FORM2, function(resultData) {
							if (resultData.result.code === "OK") {
								var rows = resultData.result.data.rows;
								if (!rows || rows.length === 0) {
									mvno.ui.alert("조회된 고객정보가 없습니다.");
									return;
								}

								PAGE.FORM2.disableItem("searchSubscriberNo");
								PAGE.FORM2.disableItem("chnlCd");
								
								//첫번째 조회한 핸드폰번호의 정보로 세팅
								var first = rows[0];
								PAGE.FORM2.setItemValue("searchSubscriberNo", first.subscriberNo);

								
								
								mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"UsimReqChnlCd"}, PAGE.FORM2, "chnlCd", {callback: function(){
										PAGE.FORM2.setItemValue("chnlCd", first.chnlCd);
									}});

								PAGE.FORM2.setItemValue("customerLinkName", first.customerLinkName);
								PAGE.FORM2.setItemValue("contractNum", first.contractNum);
								PAGE.FORM2.setItemValue("subscriberNo", first.subscriberNo);
								PAGE.FORM2.setItemValue("preChkRsltCd", first.preChkRsltCd);
								PAGE.FORM2.setItemValue("preChkRsltMsg", first.preChkRsltMsg);
								PAGE.FORM2.setItemValue("usimModelCd", first.usimModelCd);
								PAGE.FORM2.setItemValue("nfcUsimType", first.nfcUsimType);
								PAGE.FORM2.setItemValue("usimModelId", first.usimModelId);
								PAGE.FORM2.setItemValue("prdtCode", first.prdtCode);
								PAGE.FORM2.setItemValue("usimSn", first.usimSn);
								PAGE.FORM2.setItemValue("dlvryName", first.dlvryName);
								PAGE.FORM2.setItemValue("dlvryTelFn", first.dlvryTelFn);
								PAGE.FORM2.setItemValue("dlvryTelMn", first.dlvryTelMn);
								PAGE.FORM2.setItemValue("dlvryTelRn", first.dlvryTelRn);
								PAGE.FORM2.setItemValue("dlvryPost", first.dlvryPost);
								PAGE.FORM2.setItemValue("dlvryAddr", first.dlvryAddr);
								PAGE.FORM2.setItemValue("dlvryAddrDtl", first.dlvryAddrDtl);
								PAGE.FORM2.setItemValue("dlvryMemo", first.dlvryMemo);
								PAGE.FORM2.setItemValue("procMemo", first.procMemo);
								PAGE.FORM2.setItemValue("procYn", first.procYn == "Y" ? 1 : 0);

								//기존 추가등록 블록 제거
								var deleteTargets = [];
								PAGE.FORM2.forEachItem(function(name) {
									if (name.startsWith("newBlock_") ||
										name.startsWith("contractNum_") ||
										name.startsWith("subscriberNo_") ||
										name.startsWith("preChkRsltCd_") ||
										name.startsWith("preChkRsltMsg_") ||
										name.startsWith("usimModelCd_") ||
										name.startsWith("nfcUsimType_") ||
										name.startsWith("usimModelId_") ||
										name.startsWith("prdtCode_") ||
										name.startsWith("usimSn_") ||
										name.startsWith("btnDelete_")) {
										deleteTargets.push(name);
									}
								});

								deleteTargets.forEach(function(name) {
									PAGE.FORM2.removeItem(name);
								});

								// 신규 rows 데이터로 첫번째 조회데이터를 제외한 추가등록 블록 생성 
								for (let i = 1; i < rows.length; i++) {
									const info = rows[i];

									const newBlock1 = 
										{ type : "block", list : [
											, {type:"label", width:70, label:"추가등록(계약번호)", name:"newBlock_", labelWidth: 120, disabled:true}
										]};
									const newBlock2 = 
										{ type: "block", list: [
											, { type: "input", width: 60, name: "contractNum_" + i, value: info.contractNum, disabled: true}
											, { type: "newcolumn" }
											, { type: "input", width: 70, name: "subscriberNo_" + i, value: info.subscriberNo || "", disabled: true }
											, { type: "newcolumn" }
											, { type: "hidden", width:100, name: "preChkRsltCd_" + i,value: info.preChkRsltCd || "", disabled:true}
											, { type: "newcolumn" }
											, { type: "input", width: 190, name: "preChkRsltMsg_" + i, value: info.preChkRsltMsg || "", disabled: true }
											, { type:"newcolumn"}
											, { type:"input", width:70, name: "usimModelCd_" + i, value: info.usimModelCd,  disabled:true}
											, { type:"newcolumn"}
											, { type:"input", width:60, name: "nfcUsimType_" + i, value: info.nfcUsimType, disabled:true}
											, { type:"newcolumn"}
											, { type:"hidden", width:0, name: "usimModelId_" + i, value: info.usimModelId, disabled:true}
											, { type: "newcolumn" }
											, { type: "input", width: 70, label: "유심등록", name: "prdtCode_" + i, value: info.prdtCode || "", offsetLeft: 10, disabled: true, labelWidth:50, maxLength:30, validate: 'ValidAplhaNumeric' }
											, { type: "newcolumn" }
											, { type: "input", width: 110, name: "usimSn_" + i, value: info.usimSn || "", disabled: true, validate:"ValidAplhaNumeric", style:"text-align:right;", maxLength:20}
										]};
									
									if(i == 1){
										PAGE.FORM2.addItem("CUSTOMERINFO", newBlock1);
									}
									PAGE.FORM2.addItem("CUSTOMERINFO", newBlock2);
								}

								var currentChnlCd = PAGE.FORM2.getItemValue("chnlCd");
								PAGE.FORM2.callEvent("onChange", ["chnlCd", currentChnlCd]);
								

							} else {
								mvno.ui.alert(resultData.result.msg);
							}
						});
						
						break;
						
						
					case "btnDlvryUpload" :

						if(maskingYn != "Y" && maskingYn != "1") {
							mvno.ui.alert("업로드 권한이 없습니다.");
							return;
						}

						mvno.ui.createGrid("POPUP1TOP");
						mvno.ui.createForm("POPUP1MID");

						PAGE.POPUP1TOP.clearAll();
						PAGE.POPUP1MID.clear();
						PAGE.POPUP1MID.setFormData({});

						PAGE.POPUP1MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP1TOP.clearAll();

							return true;
						});

						var uploader = PAGE.POPUP1MID.getUploader("file_upload1");
						uploader.revive();

						mvno.ui.popupWindowById("POPUP1", "일련번호/송장업로드", 950, 500, {
							onClose2: function(win) {
								uploader.reset();
							}
						});


						break;

					case "btnProcUpload" :

						mvno.ui.createGrid("POPUP2TOP");
						mvno.ui.createForm("POPUP2MID");

						PAGE.POPUP2TOP.clearAll();
						PAGE.POPUP2MID.clear();
						PAGE.POPUP2MID.setFormData({});

						PAGE.POPUP2MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP2TOP.clearAll();

							return true;
						});

						var uploader = PAGE.POPUP2MID.getUploader("file_upload2");
						uploader.revive();

						mvno.ui.popupWindowById("POPUP2", "처리여부/메모등록", 450, 500, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
						break;

					case "btnEsimUpload" :

						mvno.ui.createGrid("POPUP3TOP");
						mvno.ui.createForm("POPUP3MID");

						PAGE.POPUP3TOP.clearAll();
						PAGE.POPUP3MID.clear();
						PAGE.POPUP3MID.setFormData({});

						PAGE.POPUP3MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP3TOP.clearAll();

							return true;
						});

						var uploader = PAGE.POPUP3MID.getUploader("file_upload3");
						uploader.revive();

						mvno.ui.popupWindowById("POPUP3", "ESIM 업로드 등록", 940, 600, {
							onClose2: function(win) {
								uploader.reset();
							}
						});
						break;	
				}
			}
		}
		, POPUP1TOP : {
			css : { height : "255px" }
			,title : "일련번호/송장업로드"
			,header : "주문번호,신청경로,계약번호,고객명,주문수량,신청일자,접수시간,메모,수령인,우편번호,배송주소,배송연락처,배송요청사항,현유심,현유심종류,제품명,일련번호,택배사,송장번호"
			,columnIds : "seq,chnlNm,contractNum,customerLinkName,reqQnty,reqInDay,reqInTime,procMemo,dlvryName,dlvryPost,dlvryAddr,dlvryTel,dlvryMemo,usimModelCd,nfcUsimType,prdtCode,usimSn,tbNm,dlvryNo"
			,widths : "100,100,100,100,100,100,100,150,100,100,200,100,150,100,100,100,100,100,100"
			,colAlign : "center,center,center,left,center,center,center,left,left,center,left,center,left,center,center,center,center,center,center"
			,colTypes : "ro,ro,ro,ro,ro,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro"
		}	//POPUP1TOP End

		, POPUP1MID : {
			title : "엑셀업로드"
			,items : [
				{ type : "block"
					,list : [
						{ type : "newcolumn", offset : 20 }
						,{ type : "upload"
							,label : "일련번호/송장업로드 일괄등록 엑셀파일"
							,name : "file_upload1"
							,inputWidth : 830
							,url : "/rcp/dlvyMgmt/regParExcelUp.do"
							,userdata : { limitSize:10000 }
							,autoStart : true }
					]},
				{type:"input", width:100, name : "rowData", hidden:true}
			]

			,buttons : {
				center : {
					btnSave : {label : "저장" },
					btnClose : { label : "닫기" }
				}
			}
			,onUploadFile : function(realName, serverName) {
				fileName = serverName;
			}
			,onUploadComplete : function(count){

				var url = ROOT + "/rcp/rcpMgmt/readUsimChgUsimDlvryExcel.json";
				var userOptions = {timeout:180000};

				mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
					var rData = resultData.result;

					PAGE.POPUP1TOP.clearAll();

					PAGE.POPUP1TOP.parse(rData.data.rows, "js");

					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				}, userOptions);
			}
			,onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {

					case "btnSave" :

						if(PAGE.POPUP1TOP.getRowsNum() == 0) {
							mvno.ui.alert("파일을 첨부해 주세요.");
							return;
						}

						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)

						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") {
							return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기
						}

						var arrObj = [];

						PAGE.POPUP1TOP.forEachRow(function(id){
							var arrData = PAGE.POPUP1TOP.getRowData(id);
							arrObj.push(arrData);
						});

						var sData = { items : arrObj };

						var userOptions = {timeout:180000
							, errorCallback : function(result){
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							}
						};

						mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/updateUsimChgUsimDlvryExcel.json", sData, function(result) {
							mvno.ui.alert(result.result.msg);
							PAGE.GRID1.refresh();
							mvno.ui.closeWindowById("POPUP1");
						}, userOptions);

						break;

					case "btnClose":
						mvno.ui.closeWindowById("POPUP1");

						break;
				}
			}
		}
		, POPUP2TOP : {
			css : { height : "255px" }
			,title : "처리여부/메모등록"
			,header : "주문번호,처리여부,처리메모"
			,columnIds : "seq,procYnNm,procMemo"
			,widths : "100,100,180"
			,colAlign : "center,center,left"
			,colTypes : "ro,ro,ro"
		}	//POPUP2TOP End

		, POPUP2MID : {
			title : "엑셀업로드"
			,items : [
				{ type : "block"
					,list : [
						{ type : "newcolumn", offset : 20 }
						,{ type : "upload"
							,label : "처리여부/메모등록 엑셀파일"
							,name : "file_upload2"
							,inputWidth : 350
							,url : "/rcp/dlvyMgmt/regParExcelUp.do"
							,userdata : { limitSize:10000 }
							,autoStart : true }
					]},
				{type:"input", width:100, name : "rowData", hidden:true}
			]

			,buttons : {
				center : {
					btnSave : {label : "저장" },
					btnClose : { label : "닫기" }
				}
			}
			,onUploadFile : function(realName, serverName) {
				fileName = serverName;
			}
			,onUploadComplete : function(count){

				var url = ROOT + "/rcp/rcpMgmt/readUsimChgProcExcel.json";
				var userOptions = {timeout:180000};

				mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
					var rData = resultData.result;

					PAGE.POPUP2TOP.clearAll();

					PAGE.POPUP2TOP.parse(rData.data.rows, "js");

					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				}, userOptions);
			}
			,onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {

					case "btnSave" :

						if(PAGE.POPUP2TOP.getRowsNum() == 0) {
							mvno.ui.alert("파일을 첨부해 주세요.");
							return;
						}

						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)

						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") {
							return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기
						}

						var arrObj = [];

						PAGE.POPUP2TOP.forEachRow(function(id){
							var arrData = PAGE.POPUP2TOP.getRowData(id);
							arrObj.push(arrData);
						});

						var sData = { items : arrObj };

						var userOptions = {timeout:180000
							, errorCallback : function(result){
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							}
						};

						mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/updateUsimChgProcExcel.json", sData, function(result) {
							mvno.ui.alert(result.result.msg);
							PAGE.GRID1.refresh();
							mvno.ui.closeWindowById("POPUP2");
						}, userOptions);

						break;

					case "btnClose":
						mvno.ui.closeWindowById("POPUP2");

						break;
				}
			}
		}
		, POPUP3TOP : {
			css : { height : "350px" }
			,title : "ESIM 엑셀업로드"
			,header : "신청경로,계약번호,고객명,주문수량,신청일시,수령인,우편번호,배송주소,주소상세,배송연락처,배송요청사항,처리메모"
			,columnIds : "chnlNm,contractNum,customerLinkName,reqQnty,reqInDay,dlvryName,dlvryPost,dlvryAddr,dlvryAddrDtl,dlvryTel,dlvryMemo,procMemo"
			,widths : "100,100,100,100,100,100,100,200,100,100,150,100"
			,colAlign : "center,center,left,center,center,left,center,left,left,center,left,left"
			,colTypes : "ro,ro,ro,ro,roXd,ro,ro,ro,ro,ro,ro,ro"
		}	//POPUP3TOP End

		, POPUP3MID : {
			title : "엑셀업로드"
			,items : [
				{ type : "block"
					,list : [
						{ type : "newcolumn", offset : 20 }
						,{ type : "upload"
							,label : "일괄등록 엑셀파일"
							,name : "file_upload3"
							,inputWidth : 830
							,url : "/rcp/dlvyMgmt/regParExcelUp.do"
							,userdata : { limitSize:10000 }
							,autoStart : true }
					]},
				{type:"input", width:100, name : "rowData", hidden:true}
			]

			,buttons : {
				center : {
					btnSave : {label : "저장" },
					btnClose : { label : "닫기" }
				}
			}
			,onUploadFile : function(realName, serverName) {
				fileName = serverName;
			}
			,onUploadComplete : function(count){

				var url = ROOT + "/rcp/rcpMgmt/readUsimChgInfoExcel.json";
				var userOptions = {timeout:180000};

				mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
					var rData = resultData.result;

					PAGE.POPUP3TOP.clearAll();

					PAGE.POPUP3TOP.parse(rData.data.rows, "js");

					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
				}, userOptions);
			}
			,onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {

					case "btnSave" :

						if(PAGE.POPUP3TOP.getRowsNum() == 0) {
							mvno.ui.alert("파일을 첨부해 주세요.");
							return;
						}

						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)

						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") {
							return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기
						}

						var arrObj = [];

						PAGE.POPUP3TOP.forEachRow(function(id){
							var arrData = PAGE.POPUP3TOP.getRowData(id);
							arrObj.push(arrData);
						});

						var sData = { items : arrObj };

						var userOptions = {timeout:180000
							, errorCallback : function(result){
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							}
						};

						mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/insertUsimChgInfoExcel.json", sData, function(result) {
							mvno.ui.alert(result.result.msg);
							PAGE.GRID1.refresh();
							mvno.ui.closeWindowById("POPUP3");
						}, userOptions);

						break;

					case "btnClose":
						mvno.ui.closeWindowById("POPUP3");

						break;
				}
			}
		}

		,GRID2 : {
			css : {
				height : "140px"
			},
			title : "일련번호 상세",
			header : "주문번호,주문수량,제품명,일련번호,계약번호,유심기변,기변일시,현재유심일련번호,연동결과,사유",
			columnIds : "seq,reqQnty,prdtCode,usimSn,contractNum,chgYn,chgDttm,iccId,applyRsltCd,applyRsltMsg",
			colAlign : "center,center,center,center,center,center,center,center,center,left",
			colTypes : "ro,ro,ro,ro,ro,ro,roXdt,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str",
			widths : "70,70,80,170,80,80,120,170,70,190",
			// hiddenColumns : [0],
			paging: true,
			pageSize:20,
			showPagingAreaOnInit: true,
		}
		,FORM2 : {
			title : "신청정보(유심교체)",
			items : [
				{ type : "settings", position : "label-left", labelAlign : "left", labelWidth : 70, blockOffset : 0 }
				
				,{ type : "block", list : [
						, {type:"input", width:100, label:"휴대폰번호", name: "searchSubscriberNo", offsetLeft:10, maxLength:11, validate:"ValidInteger"} 
						, {type: "newcolumn",	 offset:10}
						, {type: "button", value:"조회", name:"btnT01", disabled:false}
						, {type:"newcolumn"},
						, {type:"select", width:100, label:"신청경로", name:"chnlCd", offsetLeft:10, labelWidth:70, required:true }
					]}
				,{ type: "block", list: [], offsetTop: 5 }
				,{ type: "fieldset", label: "고객정보", inputWidth:860, name:"CUSTOMERINFO",  list:[
						,{ type : "block", list : [
								, {type:"input", width:70, label:"고객명", name: "customerLinkName", disabled:true}
						]}
						,{ type : "block", list : [
								, {type:"label", width:70, label:"계약번호", disabled:true}
						]}
						,{ type : "block", list : [
							, {type:"input", width:60, name: "contractNum", disabled:true}
								, {type:"newcolumn"}
								, {type:"input", width:70, name: "subscriberNo",  disabled:true}
								, {type:"newcolumn"}
								, {type:"hidden", width:100, name: "preChkRsltCd", disabled:true}
								, {type:"newcolumn"}
								, {type:"input", width:190, name: "preChkRsltMsg", disabled:true}
								, {type:"newcolumn"}
								, {type:"input", width:70, name: "usimModelCd",  disabled:true}
								, {type:"newcolumn"}
								, {type:"input", width:60, name: "nfcUsimType", disabled:true}
								, {type:"newcolumn"}
								, {type:"hidden", width:60, name: "usimModelId", disabled:true}
								, {type:"newcolumn"}
								, {type:"input", width:70, label: "유심등록", name: "prdtCode", offsetLeft:10, labelWidth:50, disabled:true, maxLength:30, validate: 'ValidAplhaNumeric'}
								, {type:"newcolumn"}
								, {type:"input", width:110, name: "usimSn", disabled:true, validate:"ValidAplhaNumeric", maxLength:20, style:"text-align:right;"}
						]}
					]}
				,{ type: "fieldset", label: "배송정보", inputWidth:860, name:"DLVRYINFO", list:[
					,{ type : "block", list : [
							, {type:"input", width:80, label:"받으시는분", name: "dlvryName", maxLength: 30}
							, {type: "newcolumn", offset:20}
							, {type:"input", width:40, label:"연락처", name: "dlvryTelFn", labelWidth:50, maxLength:3, validate:"ValidInteger"}
							, {type: "newcolumn"}
							, {type:"input", width:40, label:"-", name: "dlvryTelMn", labelAlign:"center", labelWidth:10, maxLength:4, validate:"ValidInteger"}
							, {type: "newcolumn"}
							, {type:"input", width:40, label:"-", name: "dlvryTelRn", labelAlign:"center", labelWidth:10, maxLength:4, validate:"ValidInteger"}
						]}
					,{ type : "block", list : [
							, {type:"input", width:80, label:"우편번호", name: "dlvryPost", readonly:true, maxLength: 6}
							, {type: "newcolumn"}
							, {type:"button", value:"주소찾기", name:"btnDlvryPostFind"}
							, {type: "newcolumn"}
							, {type:"input", width:580, name: "dlvryAddr", labelWidth:50, readonly:true, maxLength: 80}
						]}
					,{ type : "block", list : [
							, {type:"input", width:740, label:"상세주소", name: "dlvryAddrDtl", maxLength: 80}
						]}
					,{ type : "block", list : [
							, {type:"input", width:740, label:"요청사항", name: "dlvryMemo", maxLength: 200}
						]}
					,{ type : "block", list : [
							, {type:"input", width:650, label:"메모", name: "procMemo", maxLength: 200}
							, {type: "newcolumn"}
							, {type:"checkbox",width:5,  label:"처리여부",labelWidth:50, name:"procYn", offsetLeft:10, labelAlign:"center"}
							, {type:"hidden", width:50, name: "seq"}
							, {type:"hidden", width:50, name: "osstNo"}
							, {type:"hidden", width:50, name: "customerId"}
						]}
					]}
			]
			,buttons : {
				right : {
					btnSave : {label : "저장" }
					,btnMod : { label : "수정" }
					,btnClose : { label : "닫기" }
				}
			}
			,onButtonClick : function(btnId) {
			switch (btnId) {

				case "btnT01":

					PAGE.FORM2.setItemValue("osstNo", "");
					PAGE.FORM2.setItemValue("customerId", "");
					PAGE.FORM2.setItemValue("customerLinkName", "");
					PAGE.FORM2.setItemValue("contractNum", "");
					PAGE.FORM2.setItemValue("subscriberNo", "");
					PAGE.FORM2.setItemValue("preChkRsltCd", "");
					PAGE.FORM2.setItemValue("preChkRsltMsg", "");
					PAGE.FORM2.setItemValue("usimModelCd", "");
					PAGE.FORM2.setItemValue("nfcUsimType", "");
					PAGE.FORM2.setItemValue("usimModelId", "");
					PAGE.FORM2.setItemValue("prdtCode", "");
					PAGE.FORM2.setItemValue("usimSn", "");

					//배송정보 초기화
					PAGE.FORM2.setItemValue("dlvryName", "");
					PAGE.FORM2.setItemValue("dlvryTelFn", "");
					PAGE.FORM2.setItemValue("dlvryTelMn", "");
					PAGE.FORM2.setItemValue("dlvryTelRn", "");
					PAGE.FORM2.setItemValue("dlvryPost", "");
					PAGE.FORM2.setItemValue("dlvryAddr", "");
					PAGE.FORM2.setItemValue("dlvryAddrDtl", "");
					PAGE.FORM2.setItemValue("dlvryMemo", "");
					PAGE.FORM2.setItemValue("procMemo", "");
					PAGE.FORM2.setItemValue("procYn", "0");


					//기존 추가등록 블록 제거
					var deleteTargets = [];
					PAGE.FORM2.forEachItem(function(name) {
						if (name.startsWith("newBlock_") ||
							name.startsWith("contractNum_") ||
							name.startsWith("subscriberNo_") ||
							name.startsWith("preChkRsltCd_") ||
							name.startsWith("preChkRsltMsg_") ||
							name.startsWith("usimModelCd_") ||
							name.startsWith("nfcUsimType_") ||
							name.startsWith("usimModelId_") ||
							name.startsWith("prdtCode_") ||
							name.startsWith("usimSn_") ||
							name.startsWith("btnDelete_")) {
							deleteTargets.push(name);
						}
					});

					deleteTargets.forEach(function(name) {
						PAGE.FORM2.removeItem(name);
					});
					
					var searchSubscriberNo = PAGE.FORM2.getItemValue("searchSubscriberNo");
					
					if(mvno.cmn.isEmpty(searchSubscriberNo)){
						mvno.ui.alert("핸드폰 번호를 입력하세요");
						return;
					}
					mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/usimChgAccept.json", PAGE.FORM2, function(resultData) {

						if (resultData.result.code === "OK") {
							var rows = resultData.result.data.rows;

							if (!rows || rows.length === 0) {
								mvno.ui.alert("조회된 고객정보가 없습니다.");
								return;
							}
							
							//첫번째 조회한 핸드폰번호의 정보로 세팅
							var first = rows[0];
							PAGE.FORM2.setItemValue("osstNo", first.osstNo);
							PAGE.FORM2.setItemValue("customerId", first.customerId);
							PAGE.FORM2.setItemValue("customerLinkName", first.customerLinkName);
							PAGE.FORM2.setItemValue("contractNum", first.contractNum);
							PAGE.FORM2.setItemValue("subscriberNo", first.subscriberNo);
							PAGE.FORM2.setItemValue("preChkRsltCd", first.preChkRsltCd);
							PAGE.FORM2.setItemValue("preChkRsltMsg", first.preChkRsltMsg);
							PAGE.FORM2.setItemValue("usimModelCd", first.usimModelCd);
							PAGE.FORM2.setItemValue("nfcUsimType", first.nfcUsimType);
							PAGE.FORM2.setItemValue("usimModelId", first.usimModelId);
							PAGE.FORM2.setItemValue("prdtCode", first.prdtCode);
							PAGE.FORM2.setItemValue("usimSn", first.usimSn);
							
							//배송정보 세팅
/*							PAGE.FORM2.setItemValue("dlvryName", first.customerLinkName);
							PAGE.FORM2.setItemValue("dlvryTelFn", searchSubscriberNo.substring(0,3));
							PAGE.FORM2.setItemValue("dlvryTelMn", searchSubscriberNo.substring(3,7));
							PAGE.FORM2.setItemValue("dlvryTelRn", searchSubscriberNo.substring(7,11));*/
							PAGE.FORM2.setItemValue("dlvryPost", first.dlvryPost);
							PAGE.FORM2.setItemValue("dlvryAddr", first.dlvryAddr);
							PAGE.FORM2.setItemValue("dlvryAddrDtl", first.dlvryAddrDtl);
							
							
							// 신규 rows 데이터로 첫번째 조회데이터를 제외한 추가등록 블록 생성 
							for (let i = 1; i < rows.length; i++) {
								const info = rows[i];

								const newBlock1 = 
								{ type : "block", list : [
									, {type:"label", width:70, label:"추가등록(계약번호)", name:"newBlock_", labelWidth: 120, disabled:true}
								]};
								const newBlock2 = {
									type: "block",
									list: [
										, { type: "input", width: 60, name: "contractNum_" + i, value: info.contractNum, disabled: true}
										, { type: "newcolumn" }
										, { type: "input", width: 70, name: "subscriberNo_" + i, value: info.subscriberNo || "", disabled: true }
										, { type: "newcolumn" }
										, { type: "hidden", width:100, name: "preChkRsltCd_" + i,value: info.preChkRsltCd || "", disabled:true}
										, { type: "newcolumn" }
										, { type: "input", width: 190, name: "preChkRsltMsg_" + i, value: info.preChkRsltMsg || "", disabled: true }
										, { type:"newcolumn"}
										, { type:"input", width:70, name: "usimModelCd_" + i, value: info.usimModelCd,  disabled:true}
										, { type:"newcolumn"}
										, { type:"input", width:60, name: "nfcUsimType_" + i, value: info.nfcUsimType, disabled:true}
										, { type: "newcolumn" }
										, { type:"hidden", width:0, name: "usimModelId_" + i, value: info.usimModelId, disabled:true}
										, { type: "newcolumn" }
										, { type: "input", width: 70, label: "유심등록", name: "prdtCode_" + i, value: info.prdtCode || "", offsetLeft: 10, disabled: true, labelWidth:50, maxLength:30, validate: 'ValidAplhaNumeric' }
										, { type: "newcolumn" }
										, { type: "input", width: 110, name: "usimSn_" + i, value: info.usimSn || "", disabled: true, validate:"ValidAplhaNumeric", style:"text-align:right;", maxLength:20}
										, { type: "newcolumn" }
										, { type: "button", value: "삭제", name: "btnDelete_" + i }
									]
								};

								if(i == 1){
									PAGE.FORM2.addItem("CUSTOMERINFO", newBlock1);
								}
								PAGE.FORM2.addItem("CUSTOMERINFO", newBlock2);

								//삭제이벤트
								PAGE.FORM2.attachEvent("onButtonClick", function(name) {
									if (name === "btnDelete_" + i) {
										[
											"contractNum_",
											"subscriberNo_",
											"preChkRsltCd_",
											"preChkRsltMsg_",
											"usimModelCd_",
											"nfcUsimType_",
											"usimModelId_",
											"prdtCode_",
											"usimSn_",
											"btnDelete_"
										].forEach(prefix => {
											const target = prefix + i;
											if (PAGE.FORM2.isItem(target)) {
												PAGE.FORM2.removeItem(target);
											}
										});
									}
								});
							}
							
							var currentChnlCd = PAGE.FORM2.getItemValue("chnlCd");
							PAGE.FORM2.callEvent("onChange", ["chnlCd", currentChnlCd]);

						} else {
							mvno.ui.alert(resultData.result.msg);
						}
					});

					break;
				
				case "btnSave":

					if (!this.validate()) return;

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryName"))) {
						mvno.ui.alert("받으시는분을 입력하세요.");
						return;
					}

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryTelFn"))) {
						mvno.ui.alert("[연락처1]을 입력하세요.");
						return;
					}

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryTelMn"))) {
						mvno.ui.alert("[연락처2]를 입력하세요.");
						return;
					}

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryTelRn"))) {
						mvno.ui.alert("[연락처3]을 입력하세요.");
						return;
					}

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryPost"))) {
						mvno.ui.alert("[우편번호]를 입력하세요.");
						return;
					}

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryAddr"))) {
						mvno.ui.alert("[주소]를 입력하세요.");
						return;
					}

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryAddrDtl"))) {
						mvno.ui.alert("[상세주소]를 입력하세요.");
						return;
					}
					
					var items = [];
					
					if (PAGE.FORM2.getItemValue("preChkRsltCd") === "00") {
						items.push({
							contractNum: PAGE.FORM2.getItemValue("contractNum"),
							subscriberNo: PAGE.FORM2.getItemValue("subscriberNo"),
							prdtCode: PAGE.FORM2.getItemValue("prdtCode"),
							usimSn: PAGE.FORM2.getItemValue("usimSn"),
							usimModelCd: PAGE.FORM2.getItemValue("usimModelCd"),
							nfcUsimType: PAGE.FORM2.getItemValue("nfcUsimType"),
							usimModelId: PAGE.FORM2.getItemValue("usimModelId")
						});
					}
					
					PAGE.FORM2.forEachItem(function(name) {
						if (name.startsWith("contractNum_")) {
							const idx = name.replace("contractNum", ""); // 
							const preChkRsltCdName = "preChkRsltCd" + idx;
							const preChkRsltCdValue = PAGE.FORM2.getItemValue(preChkRsltCdName);

							if (preChkRsltCdValue === "00") {
								items.push({
									contractNum: PAGE.FORM2.getItemValue("contractNum" + idx),
									subscriberNo: PAGE.FORM2.getItemValue("subscriberNo" + idx),
									prdtCode: PAGE.FORM2.getItemValue("prdtCode" + idx),
									usimSn: PAGE.FORM2.getItemValue("usimSn" + idx),
									usimModelCd: PAGE.FORM2.getItemValue("usimModelCd" + idx),
									nfcUsimType: PAGE.FORM2.getItemValue("nfcUsimType" + idx),
									usimModelId: PAGE.FORM2.getItemValue("usimModelId" + idx)
								});
							}
						}
					});

					
					if (items.length === 0) {
						mvno.ui.alert("신청 가능한 항목이 없습니다. ");
						return;
					}

					var sData = { 
						items : items,
						osstNo : PAGE.FORM2.getItemValue("osstNo"),
						customerId : PAGE.FORM2.getItemValue("customerId"),
						chnlCd: PAGE.FORM2.getItemValue("chnlCd"),
						dlvryName: PAGE.FORM2.getItemValue("dlvryName"),
						dlvryTelFn: PAGE.FORM2.getItemValue("dlvryTelFn"),
						dlvryTelMn: PAGE.FORM2.getItemValue("dlvryTelMn"),
						dlvryTelRn: PAGE.FORM2.getItemValue("dlvryTelRn"),
						dlvryPost: PAGE.FORM2.getItemValue("dlvryPost"),
						dlvryAddr: PAGE.FORM2.getItemValue("dlvryAddr"),
						dlvryAddrDtl: PAGE.FORM2.getItemValue("dlvryAddrDtl"),
						dlvryMemo: PAGE.FORM2.getItemValue("dlvryMemo"),
						procMemo: PAGE.FORM2.getItemValue("procMemo"),
						procYn: PAGE.FORM2.getItemValue("procYn") == "1" ? "Y" : "N"
					};

					mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/insertUsimChg.json", sData, function (result) {
						if (result.result.code === "OK") {
							mvno.ui.closeWindowById(PAGE.FORM2);
							mvno.ui.notify("저장 완료되었습니다.");
							PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/selectUsimChangeMstList.json", PAGE.FORM1);
						}else {
							mvno.ui.alert(result.result.msg);
						}
					});
					
					break;
					
				case "btnMod":

					if (!this.validate()) return;

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryName"))) {
						mvno.ui.alert("받으시는분을 입력하세요.");
						return;
					}

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryTelFn"))) {
						mvno.ui.alert("[연락처1]을 입력하세요.");
						return;
					}

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryTelMn"))) {
						mvno.ui.alert("[연락처2]를 입력하세요.");
						return;
					}

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryTelRn"))) {
						mvno.ui.alert("[연락처3]을 입력하세요.");
						return;
					}

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryPost"))) {
						mvno.ui.alert("[우편번호]를 입력하세요.");
						return;
					}

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryAddr"))) {
						mvno.ui.alert("[주소]를 입력하세요.");
						return;
					}

					if (mvno.cmn.isEmpty(PAGE.FORM2.getItemValue("dlvryAddrDtl"))) {
						mvno.ui.alert("[상세주소]를 입력하세요.");
						return;
					}
					
					var items = [];
					items.push({
						contractNum: PAGE.FORM2.getItemValue("contractNum"),
						subscriberNo: PAGE.FORM2.getItemValue("subscriberNo"),
						prdtCode: PAGE.FORM2.getItemValue("prdtCode"),
						usimSn: PAGE.FORM2.getItemValue("usimSn"),
						usimModelCd: PAGE.FORM2.getItemValue("usimModelCd"),
						nfcUsimType: PAGE.FORM2.getItemValue("nfcUsimType"),
						usimModelId: PAGE.FORM2.getItemValue("usimModelId")
					});
					
					
					PAGE.FORM2.forEachItem(function(name) {
						if (name.startsWith("contractNum_")) {
							const idx = name.replace("contractNum", ""); // 
						
							items.push({
								contractNum: PAGE.FORM2.getItemValue("contractNum" + idx),
								subscriberNo: PAGE.FORM2.getItemValue("subscriberNo" + idx),
								prdtCode: PAGE.FORM2.getItemValue("prdtCode" + idx),
								usimSn: PAGE.FORM2.getItemValue("usimSn" + idx),
								usimModelCd: PAGE.FORM2.getItemValue("usimModelCd" + idx),
								nfcUsimType: PAGE.FORM2.getItemValue("nfcUsimType" + idx),
								usimModelId: PAGE.FORM2.getItemValue("usimModelId" + idx)
							});
						}
					});

					if (items.length === 0) {
						mvno.ui.alert("수정 가능한 항목이 없습니다. ");
						return;
					}

					var sData = { 
						items : items,
						seq: PAGE.FORM2.getItemValue("seq"),
						dlvryName: PAGE.FORM2.getItemValue("dlvryName"),
						dlvryTelFn: PAGE.FORM2.getItemValue("dlvryTelFn"),
						dlvryTelMn: PAGE.FORM2.getItemValue("dlvryTelMn"),
						dlvryTelRn: PAGE.FORM2.getItemValue("dlvryTelRn"),
						dlvryPost: PAGE.FORM2.getItemValue("dlvryPost"),
						dlvryAddr: PAGE.FORM2.getItemValue("dlvryAddr"),
						dlvryAddrDtl: PAGE.FORM2.getItemValue("dlvryAddrDtl"),
						dlvryMemo: PAGE.FORM2.getItemValue("dlvryMemo"),
						procMemo: PAGE.FORM2.getItemValue("procMemo"),
						procYn: PAGE.FORM2.getItemValue("procYn") == 1 ? "Y" : "N"
					};

					mvno.cmn.ajax4json(ROOT + "/rcp/rcpMgmt/updateUsimChg.json", sData, function (result) {
						if (result.result.code === "OK") {
							mvno.ui.closeWindowById(PAGE.FORM2);
							mvno.ui.notify("저장 완료되었습니다.");
							PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/selectUsimChangeMstList.json", PAGE.FORM1);
							PAGE.GRID2.clearAll();
						}else {
							mvno.ui.alert(result.result.msg);
						}
					});
					
					break;

				case "btnClose":
					mvno.ui.closeWindowById("FORM2");

					break;
					

				case "btnDlvryPostFind":
					mvno.ui.codefinder4ZipCd("FORM1", "dlvryPost", "dlvryAddr", "dlvryAddrDtl");
					PAGE.FORM2.enableItem("dlvryAddrDtl");

					break;
				}
			}
			,onChange : function(name, value) {
				// 신청경로
				if (name == "chnlCd") {
					PAGE.FORM2.forEachItem(function(itemName) {
						if (itemName.startsWith("prdtCode") || itemName.startsWith("usimSn")) {
							if (value == "VISIT") {
								PAGE.FORM2.enableItem(itemName);
							} else {
								PAGE.FORM2.disableItem(itemName);
								if(isResetFlag == "Y"){
									PAGE.FORM2.setItemValue(itemName, "");		
								}
							}
						}
					});
				}
			}
		}
		,FORM3 : {
			title : "엑셀업로드 양식",
			items : [
				{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"right", labelWidth:180, labelHeight:30, blockOffset:10},
						{type:"block", list:[
								{type:"label", label:"일련번호/송장양식", offsetLeft:30},
								{type:"newcolumn"},
								{type:"button", value:"다운로드", name :"btnDlvryExcelTemp"}
							]}
					]},
				{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"right", labelWidth:180, labelHeight:30, blockOffset:10},
						{type:"block", list:[
								{type:"label", label:"처리여부/메모양식", offsetLeft:30},
								{type:"newcolumn"},
								{type:"button", value:"다운로드", name :"btnProcExcelTemp"}
							]}
					]}/*,  2025-11-10 해당기능 미사용 (ESIM 직접등록으로 처리) - 추후 다시 정리되면 진행 
				{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"right", labelWidth:180, labelHeight:30, blockOffset:0},
						{type:"block", list:[
								{type:"label", label:"ESIM양식", offsetLeft:30},
								{type:"newcolumn"},
								{type:"button", value:"다운로드", name :"btnInfoExcelTemp"}
							]}
					]}*/
			],
			buttons : {
				center : {
					btnClose : { label: "닫기" }
				}
			},
			onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {

					case "btnDlvryExcelTemp" :

						var searchData;

						if (PAGE.GRID1.getRowsNum() == 0) {
							searchData = { reqMode: "NEW" };  //신규등록 엑셀업로드 양식 다운로드
						} else {
							searchData = PAGE.FORM1.getFormData(true);
						}

						mvno.cmn.download(ROOT + "/rcp/rcpMgmt/selectUsimChgDlvryExcelTemp.json?menuId=MSP1010029", true, {postData:searchData});
						break;

					case "btnProcExcelTemp" :
						mvno.cmn.download(ROOT + "/rcp/rcpMgmt/usimChgProcExcelTemp.json?menuId=MSP1010029");
						break;

					case "btnInfoExcelTemp" :
						mvno.cmn.download(ROOT + "/rcp/rcpMgmt/usimChgInfoExcelTemp.json?menuId=MSP1010029");
						break;

					case "btnClose" :
						mvno.ui.closeWindowById(PAGE.FORM3, true);
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

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0083"}, PAGE.FORM1, "searchGbn");   			//검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"UsimReqChnlCd"}, PAGE.FORM1, "chnlCd");   //신청경로
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM1, "dlvryYn");  					//송장번호여부
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0084"}, PAGE.FORM1, "procYn");   					//처리여부
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"UsimReqStatus"}, PAGE.FORM1, "status");  	//진행상태
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9056", orderBy:"etc1"}, PAGE.FORM1, "nfcUsimYn");  	//유심종류

			PAGE.FORM1.disableItem("searchName");
		}
	};

	function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
		PAGE.FORM2.setItemValue("dlvryPost",zipNo);
		PAGE.FORM2.setItemValue("dlvryAddr",roadAddrPart1);
		PAGE.FORM2.setItemValue("dlvryAddrDtl",addrDetail);
	}
	
</script>