<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- 팝업영역 -->
<div id="POPUP1">
	<div id="POPUP1TOP"  class="section-full"></div>
	<div id="POPUP1MID" class="section-box"></div>
</div>
<div id="POPUP2">
	<div id="POPUP2TOP"  class="section-full"></div>
	<div id="POPUP2MID" class="section-box"></div>
</div>
<!-- 마케팅 동의 수정 -->
<div id="FORM2" class="section-box"></div>
<!-- Script -->
<script type="text/javascript">

	var curMnth = new Date().format("yyyymm");

	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blackOffset:0},
				{type:"block", list: [
						{type:'select', name:'searchCd', label:'검색구분', width:100},
						{type:"newcolumn"},
						{type:"input", name:"searchVal", width:100, disabled:true},
						{type:"newcolumn"},
						{type:'select', name:'searchAgrYn', label:'개인정보 처리 위탁 및 혜택 제공 동의', offsetLeft:25 ,labelWidth:220, width:60, options:[{value:"Y", text:"동의", selected:true},{value:"N", text:"미동의"}]}
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

						PAGE.GRID1.list(ROOT + "/rcp/mrktMgmt/getMarketingHistoryList.json", form);

						break;
				}
			},

			onChange : function(name, value) {//onChange START
				var form = this;
				if (name == "searchCd") {
					if (value == "") {
						form.disableItem("searchVal");
					} else {
						form.enableItem("searchVal");
					}
				}
			},
			onValidateMore : function (data){
				if( data.searchCd != "" && data.searchVal.trim() == ""){
					this.pushError("searchVal", "검색어", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				}

				if (data.searchCd == "" && data.searchAgrYn != "Y") {
					this.pushError("searchCd", "검색구분", "미동의 시 검색 필수");
					PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				}

				if (data.searchVal.trim() == "" && data.searchAgrYn != "Y") {
					this.pushError("searchVal", "검색어", "미동의 시 검색 필수");
					PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				}
			}
		},

		GRID1 : {
			css : {
				height : "550px"
			},
			title : "조회결과",
			header : "계약번호,고객명,휴대폰번호,동의일시,고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의, 혜택 제공을 위한 제 3자 제공 동의 : M모바일, 혜택 제공을 위한 제 3자 제공 동의 : KT, 제 3자 제공관련 광고 수신 동의",
			columnIds : "contractNum,custNm,mobileNo,strtDttm,personalInfoCollectAgree,agrYn,othersTrnsAgree,othersTrnsKtAgree,othersAdReceiveAgree",
			widths : "130,280,180,180,320,330,300,300,200",
			colAlign : "center,left,center,center,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str",
			paging : true,
			pageSize:20,
			buttons : {
				left : {
					btnDnExcel : { label : "마케팅동의등록양식" }
				},
				right : {
					btnReg : { label : "마케팅동의등록" }
				}
			},
			onRowDblClicked : function(rowId, celInd) {
				// 수정버튼 누른것과 같이 처리?
				this.callEvent('onButtonClick', ['btnMod']);
			},
			onButtonClick : function(btnId, selectedData) {
				switch (btnId) {
					case "btnReg":

						mvno.ui.createGrid("POPUP1TOP");
						mvno.ui.createForm("POPUP1MID");

						PAGE.POPUP1TOP.clearAll();
						PAGE.POPUP1MID.clear();

						PAGE.POPUP1MID.setFormData({});

						var fileName;

						/* PAGE.POPUP1MID.attachEvent("onUploadFile", function(realName, serverName) {
                            fileName = serverName;
                        });

                        PAGE.POPUP1MID.attachEvent("onUploadComplete", function(count){

                            var url = ROOT + "/rcp/mrktMgmt/regMarketingUpList.json";
                            var userOptions = {timeout:180000};

                            mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
                                var rData = resultData.result;

                                PAGE.POPUP1TOP.clearAll();

                                PAGE.POPUP1TOP.parse(rData.data.rows, "js");

                            }, userOptions);
                        }); */

						PAGE.POPUP1MID.attachEvent("onFileRemove",function(realName, serverName){
							PAGE.POPUP1TOP.clearAll();

							console.log("onFileRemove", realName, serverName);
							return true;
						});

						var uploader = PAGE.POPUP1MID.getUploader("file_upload1");
						uploader.revive();

						mvno.ui.popupWindowById("POPUP1", "마케팅동의등록", 850, 490, {
							onClose2: function(win) {
								uploader.reset();
							}
						});


						break;
					case "btnDnExcel":

						mvno.cmn.download("/rcp/mrktMgmt/getMarketingHistoryTempExcelDown.json");

						break;

					case "btnMod":
						mvno.ui.createForm("FORM2");

						var rowData = PAGE.GRID1.getSelectedRowData();
						var contractNum = rowData.contractNum;
						var agrYn = rowData.agrYn;
						var personalInfoCollectAgree = rowData.personalInfoCollectAgree;
						var othersTrnsAgree = rowData.othersTrnsAgree;
						var othersTrnsKtAgree = rowData.othersTrnsKtAgree;
						var othersAdReceiveAgree = rowData.othersAdReceiveAgree;

						agrYn = agrYn === "동의" ? "Y" : "N";
						personalInfoCollectAgree = personalInfoCollectAgree === "동의" ? "Y" : "N";
						othersTrnsAgree = othersTrnsAgree === "동의" ? "Y" : "N";
						othersTrnsKtAgree = othersTrnsKtAgree === "동의" ? "Y" : "N";
						othersAdReceiveAgree = othersAdReceiveAgree === "동의" ? "Y" : "N";

						PAGE.FORM2.setItemValue("agrYn", agrYn);
						PAGE.FORM2.setItemValue("personalInfoCollectAgree", personalInfoCollectAgree);
						PAGE.FORM2.setItemValue("othersTrnsAgree", othersTrnsAgree);
						PAGE.FORM2.setItemValue("othersTrnsKtAgree", othersTrnsKtAgree);
						PAGE.FORM2.setItemValue("othersAdReceiveAgree", othersAdReceiveAgree);
						PAGE.FORM2.setItemValue("contractNum",contractNum);
						PAGE.FORM2.clearChanged();

						mvno.ui.popupWindowById("FORM2", "마케팅동의수정", 500, 350, {
							onClose: function(win) {
								win.closeForce();
							}
						});
				}

			}

		},

		POPUP1TOP : {
			css : { height : "250px" },
			title : "마케팅동의등록상세",
			header : "계약번호,고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의, 혜택 제공을 위한 제 3자 제공 동의 : M모바일, 혜택 제공을 위한 제 3자 제공 동의 : KT, 제 3자 제공관련 광고 수신 동의",
			columnIds : "contractNum,personalInfoCollectAgree,agrYn,othersTrnsAgree,othersTrnsKtAgree,othersAdReceiveAgree",
			widths : "120,320,330,300,300,200",
			colAlign : "center,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro"
		},	//POPUP1TOP End

		POPUP1MID : {
			title : "엑셀업로드",
			items : [
				{ type : "block",
					list : [
						{ type : "newcolumn", offset : 10 },
						{ type : "upload",
							label : "마케팅동의 등록요청 엑셀파일",
							name : "file_upload1",
							inputWidth : 260,
							url : "/rcp/dlvyMgmt/regParExcelUp.do",
							userdata : { limitSize:10000 },
							autoStart : true }
					]},
				{type:"input", width:100, name:"rowData", hidden:true}
			],

			buttons : {
				center : {
					btnSave : { label : "저장" },
					btnClose : { label : "닫기" }
				}
			},

			onButtonClick : function(btnId) {

				var form = this; // 혼란방지용으로 미리 설정 (권고)

				switch (btnId) {

					case "btnSave" :

						if(PAGE.POPUP1TOP.getRowsNum() == 0) {
							mvno.ui.alert("파일을 첨부해 주세요.");
							break;
						}

						var arrObj = [];

						PAGE.POPUP1TOP.forEachRow(function(id){
							var arrData = PAGE.POPUP1TOP.getRowData(id);

							arrObj.push(arrData);
						});

						var sData = {};

						sData.items = arrObj;

						var userOptions = {timeout:180000};

						mvno.cmn.ajax4json(ROOT + "/rcp/mrktMgmt/regMarketingHistoryList.json", sData, function(result) {
							mvno.ui.closeWindowById("POPUP1", true);
							mvno.ui.notify("NCMN0004");
						}, userOptions);

						break;

					case "btnClose":
						mvno.ui.closeWindowById("POPUP1");
						break;
				}
			},
			onUploadFile : function(realName, serverName) {
				fileName = serverName;
			},

			onUploadComplete : function(count) {

				var url = ROOT + "/rcp/mrktMgmt/regMarketingUpList.json";
				var userOptions = {timeout:180000};

				mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
					var rData = resultData.result;

					PAGE.POPUP1TOP.clearAll();

					PAGE.POPUP1TOP.parse(rData.data.rows, "js");

				}, userOptions);
			}

		},	//POPUP1MID End

		FORM2 : {
			title : "마케팅 동의 수정",
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:20}
				,{type:"block", offsetLeft:15, list:[
						{type:"input", name:"contractNum", label:"계약번호", width:120, maxLength:10, disabled:true}
					]}
				,{type:"block", offsetLeft:15, offsetTop:3, list:[
						{type:"select", name:"personalInfoCollectAgree", label:"고객 혜택 제공을 위한 개인정보 수집 및 관련 동의", labelWidth:330, value:"", options:[{value:"Y", text:"동의"}, {value:"N", text:"미동의"}]}
					]}
				,{type:"block", offsetLeft:15, offsetTop:3, list:[
						{type:"select", name:"agrYn", label:"개인정보 처리 위탁 및 혜택 제공 동의", labelWidth:330, value:"", options:[{value:"Y", text:"동의"}, {value:"N", text:"미동의"}]}
						/* {type:"checkbox", label:"정보/광고 수신 위탁 동의", name:"agrYn", labelWidth:168, value:"Y", checked:false} */
					]}
				,{type:"block", offsetLeft:15, offsetTop:3, list:[
						{type:"select", name:"othersTrnsAgree", label:"혜택 제공을 위한 제 3자 제공 동의 : M모바일", labelWidth:330, value:"", options:[{value:"Y", text:"동의"}, {value:"N", text:"미동의"}]}
					]}
				,{type:"block", offsetLeft:15, offsetTop:3, list:[
						{type:"select", name:"othersTrnsKtAgree", label:"혜택 제공을 위한 제 3자 제공 동의 : KT", labelWidth:330, value:"", options:[{value:"Y", text:"동의"}, {value:"N", text:"미동의"}]}
					]}
				,{type:"block", offsetLeft:15, offsetTop:3, list:[
						{type:"select", name:"othersAdReceiveAgree", label:"제 3자 제공관련 광고 수신 동의", labelWidth:330, value:"", options:[{value:"Y", text:"동의"}, {value:"N", text:"미동의"}]}
					]}
			]
			, buttons : {
				center : {
					btnMod : { label : "수정" }
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnMod":

						mvno.cmn.ajax(ROOT + "/rcp/mrktMgmt/modMarketingHistory.json", form, function(result) {
							if(result.result.code == "NOK") {
								alert(result.result.code);
								mvno.ui.alert(result.result.msg);
								return;
							}
							mvno.ui.notify("NCMN0004");
							mvno.ui.closeWindowById(form, true);
							PAGE.GRID1.refresh();
						});
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

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2047",etc1:"1"}, PAGE.FORM1, "searchCd"); // 검색구분
		}

	};

</script>
