<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  /**
  * @Class Name : rcpCombMgmt.jsp
  * @Description : 신청관리(결합서비스)
  * @Modification Information
  *
  *   수정일		수정자		수정내용
  *  ------------------------------------------
  *  2021.11.21		권오승		최초 생성
  *
  */
%>
<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>
<div id="POPUP1"><!-- 수정화면 (1) -->
	<div id="POPUP1FORMTOP"  class="section-box"></div>
	<div id="POPUP1FORM1" class="section-box"></div>
	<div id="POPUP1FORM2" class="section-box"></div>
	<div id="POPUP1FORMBOTTOM" class="section-full"></div>
</div>
<div id="POPUP2"><!-- 등록화면 (2) -->
	<div id="POPUP2FORMTOP"  class="section-box"></div>
	<div id="POPUP2FORM1" class="section-box"></div>
	<div id="POPUP2FORM2" class="section-box"></div>
	<div id="POPUP2FORMBOTTOM" class="section-full"></div>
</div>

<!-- Script -->
<script type="text/javascript">
	
	var orgId = "${orgnInfo.orgnId}";
	var orgNm = "${orgnInfo.orgnNm}";
	var userId = "${loginInfo.userId}";
	var userName = "${loginInfo.userName}";
	var typeCd = "${orgnInfo.typeCd}";
	var orgType = "";
	if(typeCd == "10") {
		orgType = "K";
	}else if(typeCd == "20") {
		orgType = "D";
	}else if(typeCd == "30") {
		orgType = "S";
	}
	var blackMakingYn = "Y";					// 블랙마킹사용권한
	var blackMakingFlag = "N";					// 블랙마킹해제권한 (마스킹권한여부에 따라 변화)
	var maskingYn = "${maskingYn}";				// 마스킹권한
	if(maskingYn == "Y") {
		blackMakingFlag = "Y";
	}
	var agentVersion = "${agentVersion}";		// 스캔버전
	var serverUrl = "${serverUrl}";				// 서버환경 (로컬 : L, 개발 : D, 운영 : R)
	var scanSearchUrl = "${scanSearchUrl}";		// 스캔호출 url
	var scanDownloadUrl = "${scanDownloadUrl}";	// 스캔파일 download url

	var DHX = {
			//------------------------------------------------------------
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:65, blockOffset:0},
					{type:"block", list:[
						{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"신청일자", calendarPosition:"bottom", readonly:true, width:100, offsetLeft:10, required:true},
						{type:"newcolumn"},
						{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~",  calendarPosition:"bottom", readonly:true, labelAlign:"center", labelWidth:10, width:100, offsetLeft:5},
						{type:'newcolumn'},
	  					{type:"select", label:"결합유형",name:"combTypeCd", width:100, offsetLeft:40, labelWidth:65},
						{type:"newcolumn"},
						{type:"select", label:"승인상태",name:"rsltCd", width:100, offsetLeft:40, labelWidth:65}
					]},
					{type:"block", list:[
						{type:"block", list:[
							{type:"select", label:"검색구분", name:"searchCd", width:120, offsetLeft:10 , options:[{value:'', text:'- 전체 -', selected:true}, {value:'01', text:'결합부가서비스명'}, {value:'02', text:'상품명'}, {value:'03', text:'상품코드'}, {value:'04', text:'CTN'} , {value:'05', text:'고객명'}]},
		  					{type:"newcolumn"},
		  					{type:"input", name:"searchVal", width:150}
		  				]}
					]},
					{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"},
					//버튼 여러번 클릭 막기 변수
					{type : 'hidden', name: "btnCount1", value:"0"},
					{type : 'hidden', name: "btnExcelCount1", value:"0"},
					{type : 'hidden', name: "DWNLD_RSN", value:""} //엑셀다운로드 사유  추가
					, {type : 'hidden', name: "btnPrintCount1", value:"0"}
				]
				, onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
							
							if (! this.validate())  return;
							var url = ROOT + "/rcp/rcpMgmt/getRcpCombMgmtList.json";
							PAGE.GRID1.list(url, form, {
								callback : function () {
									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
									PAGE.FORM1.setItemValue("btnPrintCount1", 0); //로딩 끝나면 초기화 - Print버튼
								}
							});
							break;
					}
				},
				onValidateMore : function (data) {
					if (this.getItemValue("strtDt", true) > this.getItemValue("endDt", true)) {
						this.pushError("strtDt", "신청일자", "종료일자가 시작일보다 클수 없습니다.");
						this.resetValidateCss();
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
					
					// 검색조건 없는 경우 개통일자 필수 체크
					if(this.getItemValue("strtDt") == null || this.getItemValue("endDt") == ""){
							this.pushError("strtDt","신청일자","신청일자를 선택하세요");
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						}
				},
				onChange : function(name, value) {
					var form = this;
					
					// 검색구분
					if(name == "searchCd") {
						PAGE.FORM1.setItemValue("searchVal", "");
					
					}
					
				}
				
			},
			//-------------------------------------------------------------
			GRID1 : {
				css : {
					height : "500px"
				}
				, title : "조회결과"
				, header : "SEQ,결합유형,결합A[ktM모바일],#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan" //9
							     + ",결합B[KT회선 or ktM모바일],#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan"    //9
							     + ",신청일,결합대상,승인상태,승인상태코드,비고,scanId,resNo"    //6
				, header2 : "#rspan,#rspan,고객명,성별,생년월일,CTN,개통일자,상품명,상품코드,결합부가서비스명,결합부가서비스코드" //9
								  + ",고객명,성별,생년월일,CTN(서비스코드),개통일자,상품구분,상품명,상품코드,결합부가서비스명,결합부가서비스코드" //9
								  +",#rspan,#rspan,#rspan,#rspan,#rspan,#rspan" //6
				, columnIds : "reqSeq,combTypeNm,cstmrName,mSexNm,mCustBirth,tel1,mOpenDt,mRateNm,mRateCd,mRateAdsvcNm,mRateAdsvcCd"
									+ ",cstmrName2,combSexNm,combBirth,tel2,combOpenDt,combDtlTypeNm,combSocNm,combSocCd,combRateAdsvcNm,combRateAdsvcCd"
									+",sysDt,combTgtTypeNm,rsltNm,rsltCd,rsltMemo,scanId,resNo"
				, widths : "100,90,150,60,70,100,80,250,100,200,120" 
								+",150,60,70,100,80,80,250,100,200,120"
								+ ",80,80,80,80,80,200,120"
				, colAlign : "center,left,left,center,center,center,center,left,center,left,center" 
								+ ",left,center,center,center,center,center,left,center,left,center"
								+ ",center,center,center,center,left,center,center"
				, colTypes : "ro,ro,ro,ro,ro,ro,roXd,ro,ro,ro,ro"
								+",ro,ro,ro,ro,roXd,ro,ro,ro,ro,ro"
								+",roXd,ro,ro,ro,ro,ro,ro"
				, colSorting : "str,str,str,str,str,str,str,str,str,str,str"
								+",str,str,str,str,str,str,str,str,str,str" 
								+ ",str,str,str,str,str,str,str"
				, hiddenColumns: [0 , 24,  25 , 26 , 27]
				, paging : true
				, pageSize:20
				, onRowDblClicked : function(rowId, celInd) {
					//this.callEvent('onButtonClick', ['btnRslt']);
					this.callEvent('onButtonClick', ['btnCombMod']);
				}
				, buttons : {
					hright : {
						btnDnExcel : { label : "자료생성" }
					},
					left : {
						btnPaper : { label : "서식지보기"}
					  , btnPrint : { label : "신청서보기"}
					}
					,right : {
						btnCombMod : { label : "결합수정"},
						btnCombReg : { label : "결합등록"},
						btnRslt : { label : "승인여부등록"}
					}
				}
				, onButtonClick : function(btnId){
					var form = this;
					
					switch(btnId){
					
					case "btnDnExcel":
						var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
						if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
						
						if (PAGE.GRID1.getRowsNum() == 0) {
							
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
							
						} else {
							var searchData =  PAGE.FORM1.getFormData(true);
							var strtDt = searchData.strtDt;
							var endDt = searchData.endDt;
							var searchGbn = searchData.searchGbn;
							var searchName = searchData.searchName;
							if(strtDt != null && strtDt != '' && endDt != null && endDt != ''){
								mvno.cmn.downloadCallback(function(result) {
									PAGE.FORM1.setItemValue("DWNLD_RSN",result);
									mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRcpCombMgmtListExcelDownload.json?menuId=MSP1010021", PAGE.FORM1.getFormData(true), function(result){
										mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
									});
								});
							}else{
								mvno.ui.alert("검색조건이 맞지 않습니다.");
								return;
							}
						}
						
					break;
					case "btnRslt" : 
						
						if(PAGE.GRID1.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
					
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.clear();
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9037" , orderBy:"etc1"}, PAGE.FORM2, "rsltCd");
						
						PAGE.FORM2.setFormData(PAGE.GRID1.getSelectedRowData(), true);	
						
						//승인상태가 승인완료/신청취소의 경우 수정 가능X
						if (PAGE.FORM2.getItemValue("rsltCd") == "C" || PAGE.FORM2.getItemValue("rsltCd") == "S") {
							mvno.ui.hideButton("FORM2" , "btnSave");
							PAGE.FORM2.disableItem("rsltCd");
							PAGE.FORM2.disableItem("rsltMemo");
						} else {
							mvno.ui.showButton("FORM2" , "btnSave");
							PAGE.FORM2.enableItem("rsltCd");
							PAGE.FORM2.enableItem("rsltMemo");
						}
						
						mvno.ui.popupWindowById("FORM2", "승인여부등록", 590, 200, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
					break;
					case "btnCombMod" :
						var data = PAGE.GRID1.getSelectedRowData();

		                if(data == null) {
		                   mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
		                   return;
		                }
		                //결합 데이터  idx : data.reqSeq 		                
						
						mvno.ui.createForm("POPUP1FORMTOP");
						mvno.ui.createForm("POPUP1FORM1");
						mvno.ui.createForm("POPUP1FORM2");
						mvno.ui.createForm("POPUP1FORMBOTTOM");
						
						PAGE.POPUP1FORMTOP.clear();
						PAGE.POPUP1FORM1.clear();
						PAGE.POPUP1FORM2.clear();
						PAGE.POPUP1FORMBOTTOM.clear();

						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRcpCombMgmtInfo.json", {"reqSeq":data.reqSeq}, function(resultData) {
							var data = resultData.result.data.rows[0];

							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9036"}, PAGE.POPUP1FORMTOP, "combTypeCd"); // 결합유형(팝업콤보박스)
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9039"}, PAGE.POPUP1FORMTOP, "combTgtTypeCd"); // 결합대상(팝업콤보박스)
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.POPUP1FORM1, "tel1Fn"); // 결합A 고객휴대전화
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.POPUP1FORM2, "tel2Fn"); // 결합B 고객휴대전화
							
							// ktm결합
							if(data.combTypeCd=="01"){
								PAGE.POPUP1FORM2.enableItem("combSvcCntrNo");	
								PAGE.POPUP1FORM2.enableItem("btnCombSvcCntrNo");
							}else{
								// ktm+kt 결합
								PAGE.POPUP1FORM2.disableItem("combSvcCntrNo");	
								PAGE.POPUP1FORM2.disableItem("btnCombSvcCntrNo");
							}
							
							PAGE.POPUP1FORMTOP.setFormData(data);
							PAGE.POPUP1FORM1.setFormData(data);
							PAGE.POPUP1FORM2.setFormData(data);							
							PAGE.POPUP1FORM1.setItemValue("tel1Mn",data.tel1.substr(3,4));
							PAGE.POPUP1FORM2.setItemValue("tel2Mn",data.tel2.substr(3,4));
							
						});
						
					
						mvno.ui.popupWindowById("POPUP1", "결합수정", 950, 550, {
							onClose: function(win) {
								win.closeForce();
							}
						});
						break;
					case "btnCombReg" :
						// 결합 등록 팝업 열기
						mvno.ui.createForm("POPUP2FORMTOP");
						mvno.ui.createForm("POPUP2FORM1");
						mvno.ui.createForm("POPUP2FORM2");
						mvno.ui.createForm("POPUP2FORMBOTTOM");
						
						PAGE.POPUP2FORMTOP.clear();
						PAGE.POPUP2FORM1.clear();
						PAGE.POPUP2FORM2.clear();
						PAGE.POPUP2FORMBOTTOM.clear();
						
						PAGE.POPUP2FORM2.enableItem("combSvcCntrNo");	
						PAGE.POPUP2FORM2.enableItem("btnCombSvcCntrNo");

						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9036"}, PAGE.POPUP2FORMTOP, "combTypeCd"); // 결합유형(팝업콤보박스)
						// combTypeCd 01: ktm+ktm : 동일+타인 02: ktm+kt무선  : 동일+가족 03: ktm+kt유선 : 동일+가족
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9039"}, PAGE.POPUP2FORMTOP, "combTgtTypeCd"); // 결합대상(팝업콤보박스)
						// combTgtTypeCd 01: 본인명의  02:가족명의  03: 타인명의
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.POPUP2FORM1, "tel1Fn"); // 결합A 고객휴대전화
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.POPUP2FORM2, "tel2Fn"); // 결합B 고객휴대전화
						
						mvno.ui.popupWindowById("POPUP2", "결합등록", 950, 550, {
							onClose: function(win) {
								win.closeForce();
							}
						});
						break;

					case "btnPaper" :
						
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}
						var data = PAGE.GRID1.getSelectedRowData();
		                if(data == null) {
		                   mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
		                   return;
		                }
		                if(!PAGE.GRID1.getSelectedRowData().scanId){
		                	mvno.ui.alert("해당 계약번호의 서식지가 없습니다.");
			                return;	                	
		                }else{
		                	appFormView(PAGE.GRID1.getSelectedRowData().scanId, PAGE.GRID1.getSelectedRowData().resNo);
		                }
		                
						break;
						
					case "btnPrint" : 
						var data = this.getSelectedRowData();
						/*
						if(data == null) {
							mvno.ui.alert("ECMN0002");
							return;
						}
						*/
						var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/rcp/combRcpReport.jrf" + "&arg=";
						param = encodeURI(param);

						var arg = "reqSeq#" + data.reqSeq + "#";
						arg += "mskYn#" + maskingYn + "#";		
						arg += "usrId#" + userId + "#";
						arg += "orgnId#" +orgId + "#"; 
						
						arg = encodeURIComponent(arg);
						
						param = param + arg;
						
						
						var msg = "신청서 출력하시겠습니까?";
						mvno.ui.confirm(msg, function() {
							
								mvno.ui.popupWindow("/cmn/report/report.do"+param, "신청서", 900, 700, {
									onClose: function(win) {
										PAGE.FORM1.setItemValue("btnPrintCount1", 0); //로딩 끝나면 초기화 - Print버튼
										win.closeForce();
									}
								});
							
						});
						
						break;
					}
				}
			},
			FORM2 : {
				items : [
					{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"right", labelWidth:80, blockOffset:0},
							{type:"settings"},
							{type:"block", list:[
								{type:"select", label:"승인단계", name:"rsltCd", width:100, offsetLeft:10 , labelWidth:80},
								{type:"newcolumn"},
								{type:"hidden", label:"seq", name:"reqSeq"}
							]},
							{type:"block", list:[
								{type:"input", label:"비고", name:"rsltMemo", width:372, maxLength:200 , offsetLeft:10 , labelWidth:80}
							]}
						]}
				],
				buttons : {
					center : {
						btnSave : {label : "저장" },
						btnClose : { label: "닫기" }
					}
				},
				onValidateMore : function (data){
				},
				onChange : function(name, value) {//onChange START
					// 검색구분
				},
				onButtonClick : function(btnId) {
					var form = this;

					switch (btnId) {
						
					    case "btnSave":
					    	var url = "/rcp/rcpMgmt/updateRcpCombRslt.json";
					    	
					    	if(PAGE.FORM2.getItemValue("rsltCd") == "B"){
						    	mvno.ui.confirm("승인상태가 부적격의 경우 고객에게 부적격문자가 발송됩니다."+  '<br/>' + "승인상태를 수정하시겠습니까?" , function(){
		                      		mvno.cmn.ajax(ROOT + url, form, function(result){
		                      			mvno.ui.closeWindowById(PAGE.FORM2, true);
										mvno.ui.notify("NCMN0001");
										
										PAGE.GRID1.refresh();
										
		                      		}, {aysnc:false});
		                    	});
					    	}else{
								mvno.cmn.ajax(ROOT + url, form, function(result) {
								mvno.ui.closeWindowById(PAGE.FORM2, true);
								mvno.ui.notify("NCMN0001");
								
								PAGE.GRID1.refresh();
								
								});
					    	}
							break;
												
						case "btnClose" :
							mvno.ui.closeWindowById(PAGE.FORM2, true);
							break;
					}
				}
			},
			POPUP1FORMTOP : {
				title : "결합신청서(고객센터/대리점용)"
				,items : [
					{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:100, blockOffset:0},
						{type:"settings"},
						{type:"block", list:[
							{type:"select", label:"결합유형", name:"combTypeCd", width:186, offsetLeft:10 , labelWidth:70}
						]},
						{type:"block", list:[
							{type:"select", label:"결합대상", name:"combTgtTypeCd", width:186, offsetLeft:10 , labelWidth:70}
						]}
					]}
				],
				onChange : function(name, value) {
					//결합신고서 결합유형, 결합대상 선택따라서 세팅값, UI 변경 이벤트
					switch(name){
						//combTypeCd:결합유형 combTgtTypeCd:결합대상
						case "combTypeCd":
							setCombForm(PAGE.POPUP1FORMTOP, PAGE.POPUP1FORM2, value);
							
							break;
					}
					
				}
			},
			POPUP1FORM1 : {
				title : "<font size='2' >결합A [ktM모바일] </font>",
				items : [
					{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:70, blockOffset:0},
						{type:"block", list:[
							{type:"input", label:"<font color='red'>*</font> 계약번호", name:"mSvcCntrNo", width:137, offsetLeft:10 , labelWidth:60, maxLength:20}
							,{type:"newcolumn"}
		                    ,{type:"button", value:"조회", name: "btnMSvcCntrNo", offsetLeft:0}
							,{type:"newcolumn"}
							,{type:"input", label:"<font color='red'>*</font> 고객명", name:"cstmrName", width:142, offsetLeft:20 , labelWidth:95, maxLength:50}
							,{type:"newcolumn"}
							,{type:"calendar", label:"생년월일", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"mCustBirth", calendarPosition: 'bottom', width:100, offsetLeft:20 , labelWidth:85}
						]},
						{type:"block", list:[
							{type:"select", label:"CTN", name:"tel1Fn", width:65, offsetLeft:10, labelWidth:60}
							,{type:"newcolumn"}
							,{type:"input", label:"-", name:"tel1Mn", width:40, offsetLeft:10 , labelWidth:5, labelAlign:"left",validate:"ValidNumeric", maxLength:4}
							,{type:"newcolumn"}
							,{type:"input", label:"-", name:"tel1Rn", width:40, offsetLeft:10 , labelWidth:5, labelAlign:"left",validate:"ValidNumeric", maxLength:4}
							,{type:"newcolumn"}
							,{type:"input", label:"상품코드", name:"mRateCd", width:92, offsetLeft:20 , labelWidth:95}
							,{type:"newcolumn"}
		                    ,{type:"button", value:"조회", name: "btnRateCd", offsetLeft:0}
							,{type:"newcolumn"}
							,{type:"input", label:"상품명", name:"mRateNm", width:230, offsetLeft:20 , labelWidth:85 , disabled:true}
						]},
						{type:"block", list:[
							{type:"block", list:[
								,{type:"label", label:"성별", labelAlign:"left", offsetLeft:10, labelWidth:60}
								,{type:"newcolumn"}
								,{type:"radio", name:"mSexCd", value:"01", label:"남", labelWidth:12 , checked:true}
								,{type:"newcolumn"}
								,{type:"radio", name:"mSexCd", value:"02", label:"여", labelWidth:12, offsetLeft:20}
								,{type:"newcolumn"}
								,{type:"radio", name:"mSexCd", value:"03", label:"법인", labelWidth:25, offsetLeft:20}
							]}
							,{type:"newcolumn"}
							,{type:"input", label:"부가서비스코드", name:"mRateAdsvcCd", width:92, offsetLeft:46 , labelWidth:95}
							,{type:"newcolumn"}
		                    ,{type:"button", value:"조회", name: "btnMRateAdsvcCd", offsetLeft:0}
							,{type:"newcolumn"}
							,{type:"input", label:"부가서비스명", name:"mRateAdsvcNm", width:230, offsetLeft:20, labelWidth:85, disabled:true}
						]}
					]}
				]
				,onButtonClick : function(btnId) {
					switch (btnId) {
						case "btnMSvcCntrNo" :
							getSvcCntrNo(PAGE.POPUP1FORM1,true);
							
							break;

						case "btnRateCd" :
							// 결합상품 코드로 조회
							getRateCdInfo(PAGE.POPUP1FORM1, true);
							
							break;
						case "btnMRateAdsvcCd" :
							// 부가 상품 코드로 조회
							getRateAdsvcCdInfo(PAGE.POPUP1FORM1, true);
							
							break;
					}
				}
			},
			POPUP1FORM2 : {
				title : "<font size='2' >결합B [KT회선 or ktM모바일] </font>",
				items : [
					{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						{type:"block", list:[
							{type:"input", label:"계약번호", name:"combSvcCntrNo", width:137, offsetLeft:10, labelWidth:60, maxLength:20}
							,{type:"newcolumn"}
		                    ,{type:"button", value:"조회", name: "btnCombSvcCntrNo", offsetLeft:0}
							,{type:"newcolumn"}
							,{type:"input", label:"<font color='red'>*</font> 고객명", name:"cstmrName2", width:142, offsetLeft:20, labelWidth:95, maxLength:50}
							,{type:"newcolumn"}
							,{type:"calendar", label:"생년월일", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"combCustBirth", width:100,calendarPosition: 'bottom', offsetLeft:20 , labelWidth:85}
						]},
						{type:"block", list:[
							{type:"select", label:"CTN", name:"tel2Fn", width:65, offsetLeft:10, labelWidth:60}
							,{type:"newcolumn"}
							,{type:"input", label:"-", name:"tel2Mn", width:40, offsetLeft:10 , labelWidth:5, labelAlign:"center",validate:"ValidNumeric", maxLength:4}
							,{type:"newcolumn"}
							,{type:"input", label:"-", name:"tel2Rn", width:40, offsetLeft:10 , labelWidth:5, labelAlign:"center",validate:"ValidNumeric", maxLength:4}
							,{type:"newcolumn"}
							,{type:"input", label:"상품코드", name:"combSocCd", width:92, offsetLeft:20, labelWidth:95}
							,{type:"newcolumn"}
		                    ,{type:"button", value:"조회", name: "btnCombSocCd", offsetLeft:0}
							,{type:"newcolumn"}
							,{type:"input", label:"상품명", name:"combSocNm", width:230, offsetLeft:20 , labelWidth:85, disabled:true}
						]},
						{type:"block", list:[
							{type:"block", list:[
								,{type:"label", label:"성별", labelAlign:"left", offsetLeft:10, labelWidth:60}
								,{type:"newcolumn"}
								,{type:"radio", name:"combSexCd", value:"01", label:"남", labelWidth:12, checked:true}
								,{type:"newcolumn"}
								,{type:"radio", name:"combSexCd", value:"02", label:"여", labelWidth:12, offsetLeft:20}
								,{type:"newcolumn"}
								,{type:"radio", name:"combSexCd", value:"03", label:"법인", labelWidth:25, offsetLeft:20}
							]}
							,{type:"newcolumn"}
							,{type:"input", label:"부가서비스코드", name:"combRateAdsvcCd", width:92, offsetLeft:46, labelWidth:95}
							,{type:"newcolumn"}
		                    ,{type:"button", value:"조회", name: "btnCombRateAdsvcCd", offsetLeft:0}
							,{type:"newcolumn"}
							,{type:"input", label:"부가서비스명", name:"combRateAdsvcNm", width:230, offsetLeft:20 , labelWidth:85, disabled:true}
							,{type:"newcolumn"}
							,{type:"hidden", label:"idx", name:"reqSeq", width:137, offsetLeft:10 , labelWidth:70, maxLength:50}
						]}
					]}
				],onButtonClick : function(btnId) {

					switch (btnId) {
						case "btnCombSvcCntrNo" :
							getSvcCntrNo(PAGE.POPUP1FORM2,false);
							
							break;
							
						case "btnCombSocCd" :
							// 상품 코드로 조회
							getRateCdInfo(PAGE.POPUP1FORM2, false);
							
							break;

						case "btnCombRateAdsvcCd" :
							// 상품 코드로 조회
							getRateAdsvcCdInfo(PAGE.POPUP1FORM2, false);
							
							break;
					}
				}
			},
			POPUP1FORMBOTTOM : {
				buttons : {
					center : {
						btnUpdate : {label : "저장" },
						btnClose : { label: "닫기" }
					}
				},
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
					    case "btnUpdate":
							var sData = {
								combTypeCd : PAGE.POPUP1FORMTOP.getItemValue("combTypeCd") // 결합유형
								, combTgtTypeCd : PAGE.POPUP1FORMTOP.getItemValue("combTgtTypeCd") // 결합대상										
								, mSvcCntrNo : PAGE.POPUP1FORM1.getItemValue("mSvcCntrNo")  // 모계약 계약번호
								, cstmrName : PAGE.POPUP1FORM1.getItemValue("cstmrName")  // 모계약 고객명
								, mCustBirth : setDateYYYYMMDD(PAGE.POPUP1FORM1.getItemValue("mCustBirth"))  // 모계약 생년월일		
								, tel1 : PAGE.POPUP1FORM1.getItemValue("tel1Fn")+PAGE.POPUP1FORM1.getItemValue("tel1Mn")+PAGE.POPUP1FORM1.getItemValue("tel1Rn") // 모계약 전화1 모계약 전화2 모계약 전화3
								, mRateCd : PAGE.POPUP1FORM1.getItemValue("mRateCd")  // 모계약 상품코드
								, mRateNm : !PAGE.POPUP1FORM1.getItemValue("mRateCd")? "" : PAGE.POPUP1FORM1.getItemValue("mRateNm") // 모계약상품명
								, mSexCd : PAGE.POPUP1FORM1.getItemValue("mSexCd")  // 모계약 성별
								, mRateAdsvcCd : PAGE.POPUP1FORM1.getItemValue("mRateAdsvcCd")  // 모계약 부가서비스코드
								, mRateAdsvcNm : !PAGE.POPUP1FORM1.getItemValue("mRateAdsvcCd")? "" : PAGE.POPUP1FORM1.getItemValue("mRateAdsvcNm") // 모계약 부가서비스코명								
								, combSvcCntrNo : PAGE.POPUP1FORM2.getItemValue("combSvcCntrNo")  // 결합 계약번호
								, cstmrName2 : PAGE.POPUP1FORM2.getItemValue("cstmrName2")   // 결합 고객명
								, combCustBirth : setDateYYYYMMDD(PAGE.POPUP1FORM2.getItemValue("combCustBirth"))   // 결합 생년월일
								, tel2 : PAGE.POPUP1FORM2.getItemValue("tel2Fn") + PAGE.POPUP1FORM2.getItemValue("tel2Mn") + PAGE.POPUP1FORM2.getItemValue("tel2Rn") //결합  전화1 결합  전화2 결합  전화3
								, combSocCd : PAGE.POPUP1FORM2.getItemValue("combSocCd")  // 결합 상품코드
								, combSocNm : !PAGE.POPUP1FORM2.getItemValue("combSocCd")? "" : PAGE.POPUP1FORM2.getItemValue("combSocNm") // 결합 상품명
								, combSexCd : PAGE.POPUP1FORM2.getItemValue("combSexCd") // 결합 성별
								, combRateAdsvcCd : PAGE.POPUP1FORM2.getItemValue("combRateAdsvcCd") // 결합 부가서비스코드
								, combRateAdsvcNm : !PAGE.POPUP1FORM2.getItemValue("combRateAdsvcCd")? "" : PAGE.POPUP1FORM2.getItemValue("combRateAdsvcNm") // 결합 부가서비스명
								, reqSeq : PAGE.POPUP1FORM2.getItemValue("reqSeq") // idx
								
							};
							mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/updateRcpCombMgmtInfo.json", sData, function(result) {
								// 저장 후 팝업 안닫히는 부분 확인 필요
								mvno.ui.closeWindowById("POPUP1", true);
								mvno.ui.notify("NCMN0002");
								PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
							});
							break;
												
						case "btnClose" :
							mvno.ui.closeWindowById("POPUP1");
							break;
					}
				}
				
			}
			,POPUP2FORMTOP : {
				title : "결합신청서(고객센터/대리점용)"
				,items : [
					{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:100, blockOffset:0},
						{type:"settings"},
						{type:"block", list:[
							{type:"select", label:"결합유형", name:"combTypeCd", width:186, offsetLeft:10 , labelWidth:70}
						]},
						{type:"block", list:[
							{type:"select", label:"결합대상", name:"combTgtTypeCd", width:186, offsetLeft:10 , labelWidth:70}
						]}
					]}
				],
				onChange : function(name, value) {
					//결합신고서 결합유형, 결합대상 선택따라서 세팅값, UI 변경 이벤트

					switch(name){
						//combTypeCd:결합유형 combTgtTypeCd:결합대상
						case "combTypeCd":
							setCombForm(PAGE.POPUP2FORMTOP, PAGE.POPUP2FORM2, value);
							
							break;
					}
					
				}
			},
			POPUP2FORM1 : {
				title : "<font size='2' >결합A [ktM모바일] </font>",
				items : [
					{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:70, blockOffset:0},
						{type:"block", list:[
							{type:"input", label:"<font color='red'>*</font> 계약번호", name:"mSvcCntrNo", width:137, offsetLeft:10 , labelWidth:60, maxLength:20, validate:"ValidNumeric"}
							,{type:"newcolumn"}
		                    ,{type:"button", value:"조회", name: "btnMSvcCntrNo", offsetLeft:0}
							,{type:"newcolumn"}
							,{type:"input", label:"<font color='red'>*</font> 고객명", name:"cstmrName", width:142, offsetLeft:20 , labelWidth:95, maxLength:50}
							,{type:"newcolumn"}
							,{type:"calendar", label:"생년월일", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"mCustBirth", calendarPosition: 'bottom', width:100, offsetLeft:20 , labelWidth:85, validate: "NotEmpty"}
							
							//,{type:"input", label:"성별", name:"combSexNm", width:100, offsetLeft:10 , labelWidth:110}
							//,{type:"newcolumn"}
							//,{type : "button",name : "btnSearch",value : "조회", className:"btn-search2"}
						]},
						{type:"block", list:[
							{type:"select", label:"CTN", name:"tel1Fn", width:65, offsetLeft:10, labelWidth:60}
							,{type:"newcolumn"}
							,{type:"input", label:"-", name:"tel1Mn", width:40, offsetLeft:10 , labelWidth:5, labelAlign:"left",validate:"ValidNumeric", maxLength:4}
							,{type:"newcolumn"}
							,{type:"input", label:"-", name:"tel1Rn", width:40, offsetLeft:10 , labelWidth:5, labelAlign:"left",validate:"ValidNumeric", maxLength:4}
							,{type:"newcolumn"}
							,{type:"input", label:"상품코드", name:"mRateCd", width:92, offsetLeft:20 , labelWidth:95}
							,{type:"newcolumn"}
		                    ,{type:"button", value:"조회", name: "btnMRateCd", offsetLeft:0}
							,{type:"newcolumn"}
							,{type:"input", label:"상품명", name:"mRateNm", width:230, offsetLeft:20 , labelWidth:85 , disabled:true}
						]},
						{type:"block", list:[
							{type:"block", list:[
								,{type:"label", label:"성별", labelAlign:"left", offsetLeft:10, labelWidth:60}
								,{type:"newcolumn"}
								,{type:"radio", name:"mSexCd", value:"01", label:"남", labelWidth:12, checked:true}
								,{type:"newcolumn"}
								,{type:"radio", name:"mSexCd", value:"02", label:"여", labelWidth:12, offsetLeft:20}
								,{type:"newcolumn"}
								,{type:"radio", name:"mSexCd", value:"03", label:"법인", labelWidth:25, offsetLeft:20}
							]}
							,{type:"newcolumn"}
							,{type:"input", label:"부가서비스코드", name:"mRateAdsvcCd", width:92, offsetLeft:46 , labelWidth:95}
							,{type:"newcolumn"}
		                    ,{type:"button", value:"조회", name: "btnMRateAdsvcCd", offsetLeft:0}
							,{type:"newcolumn"}
							,{type:"input", label:"부가서비스명", name:"mRateAdsvcNm", width:230, offsetLeft:20 , labelWidth:85 , disabled:true}
						]}
					]}
				],onButtonClick : function(btnId) {
					switch (btnId) {
						case "btnMSvcCntrNo" : // 결합 모회선 계약번호로 조회 
							getSvcCntrNo(PAGE.POPUP2FORM1,true);
						
							break;
						
						case "btnMRateCd" :
							// 상품 코드로 조회
							getRateCdInfo(PAGE.POPUP2FORM1, true);
							
							break;

						case "btnMRateAdsvcCd" :
							// 부가서비스 코드로 조회
							getRateAdsvcCdInfo(PAGE.POPUP2FORM1, true);
							
							break;
					}
				}
			},
			POPUP2FORM2 : {
				title : "<font size='2' >결합B [KT회선 or ktM모바일] </font>",
				items : [
					{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
						{type:"block", list:[
							{type:"input", label:"계약번호", name:"combSvcCntrNo", width:137, offsetLeft:10 , labelWidth:60, maxLength:20, validate:"NotEmpty,ValidNumeric"}
							,{type:"newcolumn"}
		                    ,{type:"button", value:"조회", name: "btnCombSvcCntrNo", offsetLeft:0}
							,{type:"newcolumn"}
							,{type:"input", label:"<font color='red'>*</font> 고객명", name:"cstmrName2", width:142, offsetLeft:20 , labelWidth:95, maxLength:50}
							,{type:"newcolumn"}
							,{type:"calendar", label:"생년월일", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"combCustBirth", width:100,calendarPosition: 'bottom', offsetLeft:20 , labelWidth:85}
							,{type:"newcolumn"}
						]},
						{type:"block", list:[
							{type:"select", label:"CTN", name:"tel2Fn", width:65, offsetLeft:10, labelWidth:60}
							,{type:"newcolumn"}
							,{type:"input", label:"-", name:"tel2Mn", width:40, offsetLeft:10 , labelWidth:5, labelAlign:"center",validate:"ValidNumeric", maxLength:4}
							,{type:"newcolumn"}
							,{type:"input", label:"-", name:"tel2Rn", width:40, offsetLeft:10 , labelWidth:5, labelAlign:"center",validate:"ValidNumeric", maxLength:4}
							,{type:"newcolumn"}
							,{type:"input", label:"상품코드", name:"combSocCd", width:92, offsetLeft:20 , labelWidth:95}
							,{type:"newcolumn"}
		                    ,{type:"button", value:"조회", name: "btnCombSocCd", offsetLeft:0}
							,{type:"newcolumn"}
							,{type:"input", label:"상품명", name:"combSocNm", width:230, offsetLeft:20, labelWidth:85, disabled:true}
						]},
						{type:"block", list:[
							{type:"block", list:[
								,{type:"label", label:"성별", labelAlign:"left", offsetLeft:10, labelWidth:60}
								,{type:"newcolumn"}
								,{type:"radio", name:"combSexCd", value:"01", label:"남", labelWidth:12 , checked:true}
								,{type:"newcolumn"}
								,{type:"radio", name:"combSexCd", value:"02", label:"여", labelWidth:12, offsetLeft:20}
								,{type:"newcolumn"}
								,{type:"radio", name:"combSexCd", value:"03", label:"법인", labelWidth:25, offsetLeft:20}
							]}
							,{type:"newcolumn"}
							,{type:"input", label:"부가서비스코드", name:"combRateAdsvcCd", width:92, offsetLeft:46 , labelWidth:95}
							,{type:"newcolumn"}
		                    ,{type:"button", value:"조회", name: "btnCombRateAdsvcCd", offsetLeft:0}
							,{type:"newcolumn"}
							,{type:"input", label:"부가서비스명", name:"combRateAdsvcNm", width:230, offsetLeft:20 , labelWidth:85, disabled:true}
						]}
					]}
				],onButtonClick : function(btnId) {

					switch (btnId) {
						case "btnCombSvcCntrNo" :
							getSvcCntrNo(PAGE.POPUP2FORM2,false);
							
							break;
							
						case "btnCombSocCd" :
							// 상품 코드로 조회
							getRateCdInfo(PAGE.POPUP2FORM2, false);
							
							break;

						case "btnCombRateAdsvcCd" :
							// 부가서비스 코드로 조회
							getRateAdsvcCdInfo(PAGE.POPUP2FORM2, false);
							
							break;
						
					}
				}
			},
			POPUP2FORMBOTTOM : {
				buttons : {
					center : {
						btnSave : {label : "저장" },
						btnClose : { label: "닫기" }
					}
				},
				onButtonClick : function(btnId) {
					var form = this;

					switch (btnId) {
						
					    case "btnSave":

							var sData = {
								combTypeCd : PAGE.POPUP2FORMTOP.getItemValue("combTypeCd") // 결합유형
								, combTgtTypeCd : PAGE.POPUP2FORMTOP.getItemValue("combTgtTypeCd") // 결합대상										
								, mSvcCntrNo : PAGE.POPUP2FORM1.getItemValue("mSvcCntrNo")  // 모계약 계약번호
								, cstmrName : PAGE.POPUP2FORM1.getItemValue("cstmrName")  // 모계약 고객명
								, mCustBirth : setDateYYYYMMDD(PAGE.POPUP2FORM1.getItemValue("mCustBirth"))  // 모계약 생년월일		
								, tel1 : PAGE.POPUP2FORM1.getItemValue("tel1Fn")+PAGE.POPUP2FORM1.getItemValue("tel1Mn")+PAGE.POPUP2FORM1.getItemValue("tel1Rn") // 모계약 전화1 모계약 전화2 모계약 전화3
								, mRateCd : PAGE.POPUP2FORM1.getItemValue("mRateCd")  // 모계약 상품코드
								, mRateNm : !PAGE.POPUP2FORM1.getItemValue("mRateCd")? "" : PAGE.POPUP2FORM1.getItemValue("mRateNm") // 모계약상품명
								, mSexCd : PAGE.POPUP2FORM1.getItemValue("mSexCd")  // 모계약 성별
								, mRateAdsvcCd : PAGE.POPUP2FORM1.getItemValue("mRateAdsvcCd")  // 모계약 부가서비스코드
								, mRateAdsvcNm : !PAGE.POPUP2FORM1.getItemValue("mRateAdsvcCd")? "" : PAGE.POPUP2FORM1.getItemValue("mRateAdsvcNm")  // 모계약 부가서비스코명								
								, combSvcCntrNo : PAGE.POPUP2FORM2.getItemValue("combSvcCntrNo")  // 결합 계약번호
								, cstmrName2 : PAGE.POPUP2FORM2.getItemValue("cstmrName2")   // 결합 고객명
								, combCustBirth : setDateYYYYMMDD(PAGE.POPUP2FORM2.getItemValue("combCustBirth"))   // 결합 생년월일
								, tel2 : PAGE.POPUP2FORM2.getItemValue("tel2Fn") + PAGE.POPUP2FORM2.getItemValue("tel2Mn") + PAGE.POPUP2FORM2.getItemValue("tel2Rn") //결합  전화1 결합  전화2 결합  전화3
								, combSocCd : PAGE.POPUP2FORM2.getItemValue("combSocCd")  // 결합 상품코드
								, combSocNm : !PAGE.POPUP2FORM2.getItemValue("combSocCd")? "" : PAGE.POPUP2FORM2.getItemValue("combSocNm") // 결합 상품명
								, combSexCd : PAGE.POPUP2FORM2.getItemValue("combSexCd") // 결합 성별
								, combRateAdsvcCd : PAGE.POPUP2FORM2.getItemValue("combRateAdsvcCd") // 결합 부가서비스코드
								, combRateAdsvcNm : !PAGE.POPUP2FORM2.getItemValue("combRateAdsvcCd")? "" : PAGE.POPUP2FORM2.getItemValue("combRateAdsvcNm") // 결합 부가서비스명
							};
							mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/insertRcpCombMgmtInfo.json", sData, function(result) {
								// 저장 후 팝업 안닫히는 부분 확인 필요
								mvno.ui.closeWindowById("POPUP2", true);
								mvno.ui.notify("NCMN0001");
								PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
							});
							break;
												
						case "btnClose" :
							mvno.ui.closeWindowById("POPUP2");
							break;
					}
				}
				
			}
	}
		
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		buttonAuth:${buttonAuth.jsonString},
		onOpen : function() {
			
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			var endDt = new Date().format('yyyymmdd');
			var time = new Date().getTime();
			time = time - 1000 * 3600 * 24 * 7;
			var strtDt = new Date(time);

			PAGE.FORM1.setItemValue("strtDt", strtDt);
			PAGE.FORM1.setItemValue("endDt", endDt);

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9036"}, PAGE.FORM1, "combTypeCd"); // 결합유형
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9037" , orderBy:"etc1"}, PAGE.FORM1, "rsltCd"); // 승인상태
			
			var fixGrid = PAGE.GRID1.getColIndexById("combTypeCd");
			// IE 인 경우만 SPLIT 실행
			if(mvno.cmn.isIE()) PAGE.GRID1.splitAt(fixGrid+1);
			
		}
		
	};
	
	/** 날짜형식 YYYYMMDD 형식으로 return */
	function setDateYYYYMMDD(date){

		var strDate = new Date(date)	
		var YYYYMMDD = ""; 
		
		if(date!=null){
			YYYYMMDD = strDate.getFullYear() +
			'' + ( (strDate.getMonth()+1) < 10 ? "0" + (strDate.getMonth()+1) : (strDate.getMonth()+1) )+
			'' + ( (strDate.getDate()) < 10 ? "0" + (strDate.getDate()) : (strDate.getDate()) );
		}

		return YYYYMMDD;

	}
	
	/** 서식지 보기 */
	function appFormView(scanId, resNo){
		
		var data = null;
		
		data = "AGENT_VERSION="+agentVersion+
			"&SERVER_URL="+serverUrl+
			"&VIEW_TYPE=MSPVIEW"+
			"&USE_DEL=N"+
			"&USE_STAT=N"+
			"&USE_BM="+blackMakingYn+
			"&USE_DEL_BM="+blackMakingFlag+
			"&RGST_PRSN_ID="+userId+
			"&RGST_PRSN_NM="+userName+
			"&ORG_TYPE="+orgType+
			"&ORG_ID="+orgId+
			"&ORG_NM="+orgNm+
			"&RES_NO="+resNo+
			"&PARENT_SCAN_ID="+scanId+
			"&DBMSType=S"
			;
		
		callViewer(data);
	}
	

	function callViewer(data){
		var url = scanSearchUrl + "?" + encodeURIComponent(data);
		
		// 타임아웃 설정 - 10초이내 응답없으면 서비스 실행상태가 아님.
		var timeOutTimer = window.setTimeout(function (){
			mvno.ui.confirm("이미지 프로그램이 설치되지 않았거나 실행중이 아닙니다. 설치페이지로 이동 하시겠습니까?",function(){
				window.open(scanDownloadUrl, "", "width=750,height=650,resizable=no,scrollbars=yes,top=10,left=10");		// 설치페이지로 이동
			});
		}, 10000);
		
		$.ajax({
			type : "GET",
			url : url,
			crossDomain : true,
			dataType : "jsonp",
			success : function(args){
				window.clearTimeout(timeOutTimer);
				if(args.RESULT == "SUCCESS") {
					console.log("SUCCESS");
				} else if(args.RESULT == "ERROR_TYPE2") {
					mvno.ui.alert("버전이 다릅니다. 설치페이지로 이동해주세요.");
					window.open(scanDownloadUrl, "", "width=750,height=650,resizable=no,scrollbars=yes,top=10,left=10");		// 설치페이지로 이동
				} else {
					mvno.ui.alert(args.RESULT + " : " + args.RESULT_MSG);
				}
			}
		});
	}
	
	function getSvcCntrNo(form, type){
		var svcCntrNo = type ? form.getItemValue("mSvcCntrNo") : form.getItemValue("combSvcCntrNo");
		
		if(!svcCntrNo){
			mvno.ui.alert("결합할 회선 계약번호를 입력하세요.");
		}else{
			mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRcpCombCntrInfo.json", {"contractNum":svcCntrNo}, function(resultData) {
				var data = resultData.result.data.rows;
				
				if(type){
					if(data.length > 0){
						form.setItemValue("cstmrName",data[0].subLinkName);
						form.setItemValue("mCustBirth",data[0].birth)
						form.setItemValue("mRateNm",data[0].lstRateNm);
						form.setItemValue("mRateCd",data[0].lstRateCd);
						form.setItemValue("mSexCd",data[0].combSexCd);
						form.setItemValue("tel1Fn",data[0].cstmrTelFn);
						form.setItemValue("tel1Mn",data[0].cstmrTelMn);
						form.setItemValue("tel1Rn",data[0].cstmrTelRn);
						form.setItemValue("mRateAdsvcCd",data[0].rRateCd);
						form.setItemValue("mRateAdsvcNm",data[0].rRateNm);										
						if(!data[0].lstRateCd){
							mvno.ui.alert("조회한 고객의 현재 사용중인 요금제가 결합 서비스 대상 요금제가 아닙니다.");
						}
					}else{
						form.setItemValue("mSvcCntrNo","")
						form.setItemValue("cstmrName","");
						form.setItemValue("mCustBirth","")
						form.setItemValue("mRateNm","");
						form.setItemValue("mRateCd","");
						form.setItemValue("mSexCd","01");
						form.setItemValue("tel1Fn","");
						form.setItemValue("tel1Mn","");
						form.setItemValue("tel1Rn","");
						form.setItemValue("mRateAdsvcCd","");
						form.setItemValue("mRateAdsvcNm","");
						mvno.ui.alert("계약번호에 해당하는 데이터가 업습니다.");
					}
				} else {
					if(data.length > 0){
						form.setItemValue("cstmrName2",data[0].subLinkName);
						form.setItemValue("combCustBirth",data[0].birth)
						form.setItemValue("combSocNm",data[0].lstRateNm);
						form.setItemValue("combSocCd",data[0].lstRateCd);
						form.setItemValue("combSexCd",data[0].combSexCd);
						form.setItemValue("tel2Fn",data[0].cstmrTelFn);
						form.setItemValue("tel2Mn",data[0].cstmrTelMn);
						form.setItemValue("tel2Rn",data[0].cstmrTelRn);
						form.setItemValue("combRateAdsvcCd",data[0].rRateCd);
						form.setItemValue("combRateAdsvcNm",data[0].rRateNm);
						if(!data[0].lstRateCd){
							mvno.ui.alert("조회한 고객의 현재 사용중인 요금제가 결합 서비스 대상 요금제가 아닙니다.");
						}
					}else{
						form.setItemValue("combSvcCntrNo","")
						form.setItemValue("cstmrName2","");
						form.setItemValue("combCustBirth","")
						form.setItemValue("combSocNm","");
						form.setItemValue("combSocCd","");
						form.setItemValue("combSexCd","01");
						form.setItemValue("tel2Fn","");
						form.setItemValue("tel2Mn","");
						form.setItemValue("tel2Rn","");
						form.setItemValue("combRateAdsvcCd","");
						form.setItemValue("combRateAdsvcNm","");
						mvno.ui.alert("계약번호에 해당하는 데이터가 업습니다.");
					}
				}
			});	
		}
	}
	
	function getRateCdInfo(form, type){
		var rateCd = type ? form.getItemValue("mRateCd") : form.getItemValue("combSocCd");
		if(!rateCd){
			if(type){
				form.setItemValue("mRateNm","");
			} else {
				form.setItemValue("combSocNm","");
			}
			mvno.ui.alert("등록할 상품코드를 입력하세요.");
		}else{
			mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRcpCombParentRateCdInfo.json", {"rateCd":rateCd}, function(resultData) {
				var data = resultData.result.data.rows;
				if(type){
					if(data.length > 0){
						form.setItemValue("mRateNm",data[0].rateNm);
						form.setItemValue("mRateAdsvcCd",data[0].rateAdsvcCd);
						form.setItemValue("mRateAdsvcNm",data[0].rateAdsvcNm);										
					}else{
						form.setItemValue("mRateCd","");
						form.setItemValue("mRateNm","");
						form.setItemValue("mRateAdsvcCd","");
						form.setItemValue("mRateAdsvcNm","");
						mvno.ui.alert("해당하는 결합상품 데이터가 업습니다.");
					}
				} else {
					if(data.length > 0){
						form.setItemValue("combSocNm",data[0].rateNm);
						form.setItemValue("combRateAdsvcCd",data[0].rateAdsvcCd);
						form.setItemValue("combRateAdsvcNm",data[0].rateAdsvcNm);								
						
					}else{
						form.setItemValue("combSocCd","");
						form.setItemValue("combSocNm","");
						form.setItemValue("combRateAdsvcCd","");
						form.setItemValue("combRateAdsvcNm","");
						mvno.ui.alert("상품코드에 해당하는 결합상품 데이터가 업습니다.");
					}
				}
			});
		}
	}
	
	function getRateAdsvcCdInfo(form, type){
		var rateAdsvcCd = type ? form.getItemValue("mRateAdsvcCd") : form.getItemValue("combRateAdsvcCd");
		if(!rateAdsvcCd){
			if(type){
				form.setItemValue("mRateAdsvcNm","");
			} else {
				form.setItemValue("combRateAdsvcNm","");
			}
			mvno.ui.alert("등록할 부가서비스 코드를 입력하세요.");
		}else{
			mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRcpCombRelationRateCdInfo.json", {"rateCd":rateAdsvcCd}, function(resultData) {
				var data = resultData.result.data.rows;
				if(type){
					if(data.length > 0){
						form.setItemValue("mRateAdsvcNm",data[0].rateNm);
					}else{
						form.setItemValue("mRateAdsvcCd","");
						form.setItemValue("mRateAdsvcNm","");
						mvno.ui.alert("부가서비스코드에 해당하는 데이터가 업습니다.");
					}
				} else {
					if(data.length > 0){
						form.setItemValue("combRateAdsvcNm",data[0].rateNm);
					}else{
						form.setItemValue("combRateAdsvcCd","");
						form.setItemValue("combRateAdsvcNm","");
						mvno.ui.alert("부가서비스코드에 해당하는 데이터가 업습니다.");
					}
				}
			});
		}
	}
	
	/** FORM2 초기화 */
	function setCombForm(top, form, value){
		form.enableItem("combSvcCntrNo");
		form.enableItem("btnCombSvcCntrNo");
		
		form.setItemValue("combSvcCntrNo","")
		form.setItemValue("combSvcCntrNo","")
		form.setItemValue("cstmrName2","");
		form.setItemValue("combCustBirth","")
		form.setItemValue("combSocNm","");
		form.setItemValue("combSocCd","");
		form.setItemValue("combSexCd","01");
		form.setItemValue("tel2Fn","");
		form.setItemValue("tel2Mn","");
		form.setItemValue("tel2Rn","");						
		form.setItemValue("combRateAdsvcCd","");
		form.setItemValue("combRateAdsvcNm","");
		
		// combTypeCd 01: ktm+ktm : 동일+타인 02: ktm+kt무선  : 동일+가족 03: ktm+kt유선 : 동일+가족
		if(value=="01"){
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9039" ,etc1:"ktM"}, top, "combTgtTypeCd"); // 결합대상(팝업콤보박스)
			
		} else if(value=="02" || value=="03"){
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9039" ,etc2:"other"}, top, "combTgtTypeCd"); // 결합대상(팝업콤보박스)
			form.disableItem("combSvcCntrNo");	
			form.disableItem("btnCombSvcCntrNo");
			
		} else if(value=='04'){
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9039"}, top, "combTgtTypeCd"); // 결합대상(팝업콤보박스)

			top.setItemValue("combTgtTypeCd","03");
            
            mvno.cmn.ajax(ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"MasterCombineLineInfo"}, function(resultData) {
            	if(!resultData.result.data.rows[0]) {
            		mvno.ui.alert("ktM(법인) 결합 대상 계약번호가 없습니다. 관리자에게 문의 바랍니다.");
            	} else {
            		var masterContractNum = resultData.result.data.rows[0].value;
                	
                	mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRcpCombCntrInfo.json", {contractNum:masterContractNum}, function(resultData) {
						
						var data = resultData.result.data.rows;
						if(data.length > 0){
							form.setItemValue("combSvcCntrNo",data[0].contractNum);
							form.setItemValue("cstmrName2",data[0].subLinkName);
							form.setItemValue("combCustBirth",data[0].birth)
							form.setItemValue("combSocNm",data[0].lstRateNm);
							form.setItemValue("combSocCd",data[0].lstRateCd);
							form.setItemValue("combSexCd",data[0].combSexCd);
							form.setItemValue("tel2Fn",data[0].cstmrTelFn);
							form.setItemValue("tel2Mn",data[0].cstmrTelMn);
							form.setItemValue("tel2Rn",data[0].cstmrTelRn);									
							form.setItemValue("combRateAdsvcCd",data[0].rRateCd);
							form.setItemValue("combRateAdsvcNm",data[0].rRateNm);									
																	
							if(!data[0].lstRateCd){
								mvno.ui.alert("조회한 고객의 현재 사용중인 요금제가 결합 서비스 대상 요금제가 아닙니다.");
							}
							
						}else{
							mvno.ui.alert("ktM(법인) 결합에 해당하는 데이터가 업습니다. 관리자에게 문의 바랍니다.");
						}
					});
            	}
            });
            
		} else {
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9039"}, top, "combTgtTypeCd"); // 결합대상(팝업콤보박스)
			form.enableItem("combSvcCntrNo");		
			form.enableItem("btnCombSvcCntrNo");
		}
	}
	
</script>