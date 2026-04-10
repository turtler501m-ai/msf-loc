<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : openMgmt.jsp
	 * @Description : 개통관리 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @
	 */
	
	/**
	 * 서식지 파라미터
	 * VIEW_TYPE		화면유형 ( SCAN(스캔하기), SCANVIEW(스캔검색), FAXVIEW(팩스검색), MCPIVEW(신청서식지보기), MSPVIEW(개통서식지보기))
	 * USE_DEL			삭제권한
	 * USE_STAT			서식지검증상태 ( SCAN, MCPVIEW 에서는 USE_STAT 값에 의해서 고객명 수정 가능 )
	 *						고객명수정시 사용 ( SCANVIEW, MCPVIEW, MSPVIEW )
	 * RGST_PRSN_ID 	사용자ID
	 * RGST_PRSN_NM		사용자명
	 * ORG_TYPE			사용자의 조직유형
	 * ORG_ID			사용자 조직ID
	 * ORG_NM			사용자 조직명
	 * RES_NO			예약번호
	 * PARENT_SCAN_ID	부모 스캔ID
	 * DBMSType			DB 접속유형
	 */
%>

<div id="FORM1" class="section-search"></div>
<div id="FORM99" class="section-application"></div><!-- 신청서정보 -->
<div id="FORM25" class="section-box"></div><!-- 파일첨부 -->
<div id="FORM93" class="section-box"></div><!-- 녹취파일 등록 폼 -->


<!-- 조직 리스트 그리드 -->
<div id="GRID1"></div>

<!-- 탭바 선언 -->
<div id="TABBAR1"></div>

<!-- 가입자 정보 -->
<div id="TABBAR1_a1" style="display:none">
	<div id="FORM2" class="section-box"></div>
</div>

<!-- 계약 이력 -->
<div id="TABBAR1_a4" style="display:none">
	<div id="GRID12"></div>
</div>

<!-- 단말 이력 -->
<div id="TABBAR1_a5" style="display:none">
	<div id="GRID9"></div>
</div>

<!-- 상품 이력 -->
<div id="TABBAR1_a6" style="display:none">
	<div id="GRID10"></div>
</div>

<!-- 납부 방법 -->
<div id="TABBAR1_a7" style="display:none">
	<div id="GRID11"></div>
</div>

<!-- 청구 수납 이력 -->
<div id="TABBAR1_a8" style="display:none">
	<div id="GRID13"></div>
</div>

<!-- 법정대리인 -->
<div id="TABBAR1_a9" style="display:none">
	<div id="FORM4" class="section-box"></div>
</div>

<!-- 할인유형이력 -->
<div id="TABBAR1_a10" style="display:none">
	<div id="GRID15"></div>
</div>

<!-- 기기변경이력 -->
<div id="TABBAR1_a11" style="display:none">
	<div id="GRID16"></div>
</div>


 <!-- Script -->
<script type = "text/javascript">
	<%@ include file = "cust.formitems" %>
	var isCntpShop = false;
	var isAgent = false;

	var cntpntShopId = '';
	var cntpntShopNm = '';
	var pAgentCode = '';
	var pAgentName = '';

	var typeCd = '${orgnInfo.typeCd}';
	if (typeCd == '20' || typeCd == '30' ) {
		isCntpShop = true;
		if(typeCd == '20'){
			cntpntShopId = '${orgnInfo.orgnId}';
			cntpntShopNm = '${orgnInfo.orgnNm}';
		}else{
			isAgent = true;
			cntpntShopId = '${orgnInfo.hghrOrgnCd}';
			cntpntShopNm = '${orgnInfo.hghrOrgnNm}';
			pAgentCode = '${orgnInfo.orgnId}';
			pAgentName = '${orgnInfo.orgnNm}';
		}
	}

	var userId = '${loginInfo.userId}';
	var userName = '${loginInfo.userName}';
	var orgId = '${orgnInfo.orgnId}';
	var orgNm = '${orgnInfo.orgnNm}';
	var orgType = "";

	//조직 유형 - S:판매점, D:대리점, K:개통센터, "10:본사조직, 20:대리점, 30:판매점"
	if(typeCd == "10") {
		orgType = "K";
	}else if(typeCd == "20") {
		orgType = "D";
	}else if(typeCd == "30") {
		orgType = "S";
	}
	
	//권한 정의
	var modifyFlag = "N";
	var userOrgnTypeCd = '${loginInfo.userOrgnTypeCd}';
	if(userOrgnTypeCd == "10") {
		modifyFlag = "Y";
	}

	// 스캔관련
	var blackMakingYn = "Y";					// 블랙마킹사용권한
	var blackMakingFlag = "N";					// 블랙마킹해제권한 (마스킹권한여부에 따라 변화)
	var maskingYn = '${maskingYn}';				// 마스킹권한
	if(maskingYn == "Y") {
		blackMakingFlag = "Y";
	}	
	var agentVersion = '${agentVersion}';		// 스캔버전
	var serverUrl = '${serverUrl}';				// 서버환경 (로컬 : L, 개발 : D, 운영 : R)
	//var serverUrl = 'S';				// 서버환경 (로컬 : L, 개발 : D, 스테이징 : S, 운영 : R)
	//var scanSearchUrl = '${scanSearchUrl}';		// 스캔호출 url
	var scanSearchUrl = "${scanSearchUrlT}";		// 스캔호출 url
	var scanDownloadUrl = '${scanDownloadUrl}';	// 스캔파일 download url
	
	var DHX = {
	// 검색 조건
		FORM1 : {
			items : [
				{type : "settings", position : "label-left", labelAlign : "left", labelWidth : 55, blockOffset : 0},
				{type : "block",
					list : [
						{type : "calendar",width : 100,label : "개통일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d", name : "lstComActvDateF", offsetLeft:10, readonly:true},
						{type : "newcolumn"},
						{type : "calendar",label : "~",name : "lstComActvDateT",labelAlign : "center",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true},
						{type : "newcolumn"},
						{type : "select",width : 150,offsetLeft : 50,label : "검색구분",name : "searchGbn"},
						{type : "newcolumn"},
						{type : "input",width : 100,name : "searchName"},
						{type : "newcolumn"},
						{type : "select",width : 115,offsetLeft : 15,label : "계약상태",name : "subStatus"}
					]
				},
				{type:"block", list:[
					{type : "input", label:"대리점", name: "cntpntShopId", width:100,readonly: isCntpShop,value:cntpntShopId, offsetLeft:10},
					{type : "newcolumn"},
					{type:"button", value:"찾기", name:"btnOrgFind2", disabled:isCntpShop },
					{type : "newcolumn"},
					{type:"input", name:"cntpntShopNm", width:100,readonly:isCntpShop, value:cntpntShopNm},
					{type : "newcolumn"},
					{type:"select", width:150 ,name:"pBirthDay", offsetLeft:70, options:[{text:"생년월일", value:"birthDay"}], disabled:true},
					{type:"newcolumn"},
					{type:"input", name: "pBirthDayVal", width:100, disabled:true, maxLength:6, validate:"ValidNumeric"},
					{type : "newcolumn"},
					{type : "select",width : 115,offsetLeft : 15,label : "구매유형",name : "reqBuyTypeS"}
				]},
				{type : "block",
					list : [
						{type : "input", label:"판매점", name: "pAgentCode", width:100,readonly: isAgent,value:pAgentCode, offsetLeft:10},
						{type : "newcolumn"},
						{type:"button", value:"찾기", name:"btnOrgFind", disabled:isAgent},
						{type : "newcolumn"},
						{type:"input", name: "pAgentName", width:100,readonly: isAgent,value:pAgentName},
						{type : "newcolumn"},
						{type : "select", label : "업무구분", width : 100, offsetLeft : 15, name : "operType"},
						{type : "newcolumn"},
						{type : "select", label : "상품구분", width : 85, offsetLeft : 10, name : "prodType"},
						{type : "newcolumn"},
						{type : "checkbox", label : "미성년자여부", width : 40, name : "minorYn", labelWidth : 80, offsetLeft : 15, value : "Y"}			
					]
				},
				{type : "block",
					list : [
						{type : "input", label:"유심접점", name: "usimOrgCd", width:100, offsetLeft:10},
						{type : "newcolumn"},
						{type:"button", value:"찾기", name:"btnUsimorgnm" },
						{type : "newcolumn"},
						{type:"input", name:"usimOrgNm", width:100},
						{type : "newcolumn"},
						{type : "select", label : "모집경로", width : 100, offsetLeft : 15, name : "onOffType"},
						{type : "newcolumn"},
						{type : "select", label : "할인유형", width : 85, offsetLeft : 10, name : "sprtTp"},
						{type : "newcolumn"},
						{type : "checkbox", label : "eSIM여부", width : 40, name : "esimYn", labelWidth : 80, offsetLeft : 15, value : "Y"}
					]
				},
				{type : 'hidden', name: "DWNLD_RSN", value:""}, //엑셀다운로드 사유 2017-03-15 박준성
				{type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
				{type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수 by han
				{type : 'hidden', name: "scanValidation", value:"0"}, //해지고객 체크
				{type : "newcolumn",offset : 50},
				{type : "button",name : "btnSearch",value : "조회",className : "btn-search4"}
			],
			onButtonClick : function (btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch":
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnCount1", btnCount2)
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han
						
						if (!this.validate()) return;
						var url = ROOT + "/rcp/openMgmt/getOpenMgmtListAjaxNew.json";
						
						PAGE.GRID1.list(url, form, {
							callback : function () {
								PAGE.FORM2.clear();
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								//PAGE.FORM4.clear();
							}
						});
						PAGE.TABBAR1.tabs("a1").setActive();
						break;
						
					case "btnOrgFind":
						mvno.ui.codefinder("ORGSHOP", function(result) {
							form.setItemValue("pAgentCode", result.orgnId);
							form.setItemValue("pAgentName", result.orgnNm);
						});
						break;
					case "btnOrgFind2":
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("cntpntShopId", result.orgnId);
							form.setItemValue("cntpntShopNm", result.orgnNm);
						});
						break;
					case "btnUsimorgnm":
						mvno.ui.codefinder("USIMORGNM", function(result) {
							form.setItemValue("usimOrgCd", result.orgnId);
							form.setItemValue("usimOrgNm", result.orgnNm);
						});
						break;
				
				}
			},
			onValidateMore : function (data) {
				if (this.getItemValue("lstComActvDateF", true) > this.getItemValue("lstComActvDateT", true)) {
					this.pushError("lstComActvDateF", "개통일자", "종료일자가 시작일보다 클수 없습니다.");
					this.resetValidateCss();
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				}
				
				if( data.searchGbn != "" && !(data.searchGbn == "5" || data.searchGbn == "6") && data.searchName.trim() == ""){
					this.pushError("searchName", "검색어", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				} 
				
				// 검색조건 없는 경우 개통일자 필수 체크
				if(data.searchGbn == "" || data.searchGbn == "5" || data.searchGbn == "6" || data.searchGbn == "7"){
					if(this.getItemValue("lstComActvDateF") == null || this.getItemValue("lstComActvDateF") == ""){
						this.pushError("lstComActvDateF","개통일자","시작일자를 선택하세요");
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
					
					if(this.getItemValue("lstComActvDateT") == null || this.getItemValue("lstComActvDateT") == ""){
						this.pushError("lstComActvDateT","개통일자","종료일자를 선택하세요");
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
				}
			},
			onChange : function(name, value) {
				var form = this;
					
				// 검색구분
				if(name == "searchGbn") {
					PAGE.FORM1.setItemValue("searchName", "");
					
					if(value == null || value == "" || value == "5" || value == "6" || value == "7") {
						var lstComActvDateT = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var lstComActvDateF = new Date(time);
						
						// 개통일자 Enable
						PAGE.FORM1.enableItem("lstComActvDateF");
						PAGE.FORM1.enableItem("lstComActvDateT");

						PAGE.FORM1.setItemValue("lstComActvDateF", lstComActvDateF);
						PAGE.FORM1.setItemValue("lstComActvDateT", lstComActvDateT);
						
						if(value == "7") {
							PAGE.FORM1.enableItem("searchName");
						} else {
							PAGE.FORM1.disableItem("searchName");	
						}

						// 생년월일 Disable
						PAGE.FORM1.disableItem("pBirthDay");
						PAGE.FORM1.disableItem("pBirthDayVal");
						PAGE.FORM1.setItemValue("pBirthDayVal","");
					}else if(value == "1"){
						PAGE.FORM1.setItemValue("lstComActvDateF", "");
						PAGE.FORM1.setItemValue("lstComActvDateT", "");
						
						// 신청일자 Disable
						PAGE.FORM1.disableItem("lstComActvDateF");
						PAGE.FORM1.disableItem("lstComActvDateT");

						PAGE.FORM1.enableItem("searchName");
						
						PAGE.FORM1.enableItem("pBirthDay");
						PAGE.FORM1.enableItem("pBirthDayVal");
					}else {
						PAGE.FORM1.setItemValue("lstComActvDateF", "");
						PAGE.FORM1.setItemValue("lstComActvDateT", "");
						
						// 개통일자 Disable
						PAGE.FORM1.disableItem("lstComActvDateF");
						PAGE.FORM1.disableItem("lstComActvDateT");

						// 생년월일 Disable
						PAGE.FORM1.disableItem("pBirthDay");
						PAGE.FORM1.disableItem("pBirthDayVal");
						PAGE.FORM1.setItemValue("pBirthDayVal","");
						
						PAGE.FORM1.enableItem("searchName");
					}
				}
			}
		},
		//리스트 - GRID1
		GRID1 : {
			css : {height : "160px"},
			title : "가입자관리",
			header : "상품구분,계약번호,고객명,생년월일,나이(만),휴대폰번호,요금제,할인유형,판매정책명,약정개월수,할부개월수,단말모델,단말일련번호,상태,해지사유,모집경로,유입경로,추천인구분,추천인정보,녹취여부,대리점,판매점명,판매자,판매자ID,기변유형,기변일자,기변모델명,기변대리점,프로모션명,평생할인 프로모션명,청구번호",
			columnIds : "prodTypeNm,contractNum,cstmrName,birth,age,subscriberNo,lstRateNm,sprtTpNm,salePlcyNm,enggMnthCnt,instMnthCnt,lstModelNm,lstModelSrlNum,subStatusNm,canRsn,onOffTypeNm,openMarketReferer,recommendFlagNm,recommendInfo,recYn,agentNm,shopNm,shopUsrNm,shopUsrId,dvcOperTypeNm,dvcChgDt,dvcModelNm,dvcAgntNm,promotionNm,disPrmtNm,ban",
			widths : "80,80,100,60,60,100,200,80,200,80,80,120,120,60,120,60,60,100,70,60,150,150,80,100,80,80,80,100,200,320,100",
			colAlign : "center,center,left,center,right,center,left,center,left,right,right,left,center,center,left,center,center,center,center,center,left,left,left,left,center,center,left,left,left,left,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ron,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXd,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,int,int,str,str,sr,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
			hiddenColumns : "30",
			paging : true,
			buttons : {
				hright : {
					btnDnExcel : {
						label : "자료생성"
					}
				}
			},
			checkSelectedButtons : ["btnDtl"],
			onRowDblClicked : function (rowId, celInd) {	// 더블클릭
				var grid = this;

				cmdFalg = "U";
				var rowData = PAGE.GRID1.getSelectedRowData();
				var requestKey = rowData.requestKey;
				var contractNum = rowData.contractNum
				var trnsYn = rowData.trnsYn;

				if (PAGE.GRID1.getSelectedRowData() == null) {
					mvno.ui.alert("ECMN0002");
				} else {
					if (requestKey == "" || requestKey == null) {
						mvno.ui.alert("NCMN0010");
					} else if(trnsYn == "Y"){
						mvno.ui.alert("해지이관된 신청정보입니다.");
					} else {
						// v2017.02 신청관리 간소화 신청등록(신규) 호출
						var url = "/rcp/rcpMgmt/getRcpNewMgmt.do";
						var menuId = "MSP1000014";
						var strRequestKey = requestKey;
						
						var param = "?menuId=" + menuId + "&requestKey=" + strRequestKey + "&trgtMenuId=MSP1000099";
						
						var myTabbar = window.parent.mainTabbar;
						
						if (myTabbar.tabs(url)) {
							myTabbar.tabs(url).setActive();
						}else{
							myTabbar.addTab(url, "신청등록", null, null, true);
						}
						
						myTabbar.tabs(url).attachURL(url + param);

						if(maskingYn == "Y" || maskingYn == "1"){
							mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"contractNum":contractNum,"requestKey":strRequestKey,"trgtMenuId":"MSP1000098"}, function(result){});
						}
						
					}
				}
			},
			onRowSelect : function (rowId, celInd) {

				var rowData = "";
				var Tid = PAGE.TABBAR1.getActiveTab();
					rowData = this.getRowData(rowId);
				console.log(rowId, rowData, this.getSelectedRowData());

				PAGE.TABBAR1.callEvent('onSelect', [Tid, null, true]);
			},

			onButtonClick : function (btnId, selectedData) {
				var form = this;
				switch (btnId) {
					case "btnDnExcel":
						var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
						PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
						if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록 by han
						
						if (PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						} else {
							var searchData =  PAGE.FORM1.getFormData(true);
							var lstComActvDateF = searchData.lstComActvDateF;
							var lstComActvDateT = searchData.lstComActvDateT;
							var searchGbn = searchData.searchGbn;
							var searchName = searchData.searchName;
							if((lstComActvDateF != null && lstComActvDateF != '' && lstComActvDateT != null && lstComActvDateT != '') || (searchGbn != null && searchGbn != '' && searchName != null && searchName != '')){
								mvno.cmn.downloadCallback(function(result) {
									PAGE.FORM1.setItemValue("DWNLD_RSN",result);
									mvno.cmn.ajax(ROOT + "/rcp/openMgmt/getOpenMgmtListExcelDownload.json?menuId=MSP1000098", PAGE.FORM1.getFormData(true), function(result){
										mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
									});
								});
							}else{
								mvno.ui.alert("검색조건이 맞지 않습니다.");
								return;
							}
						}
						
						break;
				}
			}

		}, // 리스트 - GRID1
		onButtonClick : function (btnId) {
			var grid = this;

			mvno.ui.createForm("FORM98");

			switch (btnId) {
				case "btnInptSms":
					mvno.ui.popupWindowById("FORM98", "문자메세지", 500, 450, {
						onClose : function (win) {
							if (!PAGE.FORM2.isChanged())
								return true;
							mvno.ui.confirm("CCMN0005", function () {
								win.closeForce();
							});
						}
					});

					break;
			}
		},

		//탭 구성
		TABBAR1 : {
			title : "상세내역",
			css : { height : "357px" },
			tabs : [
				{
					id : "a1",
					text : "가입자정보",
					active : true
				},
				{
					id : "a4",
					text : "계약이력"
				}, {
					id : "a5",
					text : "단말이력"
				}, {
					id : "a6",
					text : "상품이력"
				},
				{
					id : "a7",
					text : "납부방법"
				},
				{
					id : "a8",
					text : "청구수납이력"
				},
				{
					id : "a9",
					text : "법정대리인"
				},
				{
					id : "a10",
					text : "할인유형이력"
				},
				{
					id : "a11",
					text : "기기변경이력"
				}
			],
			sideArrow : false,
			onSelect : function (id, lastId, isFirst) {

				var rowData = PAGE.GRID1.getSelectedRowData();
				//console.log("rowData=" + rowData);

				switch (id) {

					//가입자정보
					case "a1":
						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();
						if (rowData == null) {
							break;
						} else {
							if(maskingYn == "Y" || maskingYn == "1"){
								mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"contractNum":rowData.contractNum,"trgtMenuId":"MSP1000098","tabId":"a1"}, function(result){});
							} 
							mvno.cmn.ajax(ROOT + "/rcp/openMgmt/getOpenMgmtListDtl.json", {"CONTRACT_NUM":rowData.contractNum}, function(resultData) {
								var data = resultData.result.data.rows[0];
								
								PAGE.FORM2.setFormData(data);
							});
						}
						break;
						
 					// 계약 이력
					case "a4":
						mvno.ui.createGrid("GRID12");
						PAGE.GRID12.clearAll();
						if (rowData == null) {
							break;
						} else {
							if(maskingYn == "Y" || maskingYn == "1"){
								mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"contractNum":rowData.contractNum,"trgtMenuId":"MSP1000098","tabId":"a4"}, function(result){});
							}
							PAGE.GRID12.list(ROOT + "/rcp/openMgmt/getSubInfoHist.json", {"CONTRACT_NUM":rowData.contractNum});
						}
						break;

					// 단말 정보
					case "a5":
						mvno.ui.createGrid("GRID9");
						PAGE.GRID9.clearAll();
						if (rowData == null) {
							break;
						} else {
							PAGE.GRID9.list(ROOT + "/rcp/openMgmt/getModelHist.json", {"CONTRACT_NUM":rowData.contractNum});
						}
						break;

					// 상품 이력
					case "a6":
						mvno.ui.createGrid("GRID10");
						PAGE.GRID10.clearAll();
						if (rowData == null) {
							break;
						} else {
							PAGE.GRID10.list(ROOT + "/rcp/openMgmt/getFeatureHist.json", {"CONTRACT_NUM":rowData.contractNum});
						}
						break;
							
					// 납부 방법
					case "a7":
						mvno.ui.createGrid("GRID11");
						PAGE.GRID11.clearAll();

						if (rowData == null) {
							break;
						} else {
							if(maskingYn == "Y" || maskingYn == "1"){
								mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"contractNum":rowData.contractNum,"trgtMenuId":"MSP1000098","tabId":"a7"}, function(result){});
							}
							PAGE.GRID11.list(ROOT + "/rcp/openMgmt/getBanHist.json", {"BAN":rowData.ban});
						}
						break;
							
					// 청구 수납 이력
					case "a8":
						mvno.ui.createGrid("GRID13");
						PAGE.GRID13.clearAll();

						if (rowData == null) {
							break;
						} else {
							
							PAGE.GRID13.list(ROOT + "/rcp/openMgmt/getInvPymList.json", {"CONTRACT_NUM":rowData.contractNum});
						}
						break;
							
					// 법정대리인
					case "a9":
						mvno.ui.createForm("FORM4");
						PAGE.FORM4.clear();
						if (rowData == null) {
							break;
						} else {
							if(maskingYn == "Y" || maskingYn == "1"){
								mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"contractNum":rowData.contractNum,"trgtMenuId":"MSP1000098","tabId":"a9"}, function(result){});
							}
							mvno.cmn.ajax(ROOT + "/rcp/openMgmt/getAgentInfo.json", {"CONTRACT_NUM":rowData.contractNum}, function(resultData) {
								PAGE.FORM4.setFormData(resultData.result.data.rows[0]);
							});
						}
						break;
						
					// 할인유형이력
					case "a10":
						mvno.ui.createGrid("GRID15");
						PAGE.GRID15.clearAll();

						if (rowData != null) {
							PAGE.GRID15.list(ROOT + "/rcp/openMgmt/getAddInfoList.json", {"CONTRACT_NUM":rowData.contractNum});
						}
						break;
					
					// 기기변경이력
					case "a11":
						mvno.ui.createGrid("GRID16");
						PAGE.GRID16.clearAll();
						
						if(rowData != null){
							PAGE.GRID16.list(ROOT + "/rcp/openMgmt/getDvcChgList.json", {"CONTRACT_NUM":rowData.contractNum});
						}
						break;
				}
			}
		},
		//법정대리인정보
		FORM4 : {
			items : _FORMITEMS_['form4'],
			buttons : {
				right: { btnSave4 : {label : "저장"}}
			},
			checkSelectedButtons : ["btnSave4"],
			onButtonClick : function(btnId, selectedData){
				var form = this;
				
				var data = PAGE.GRID1.getSelectedRowData();
				
				switch (btnId) {
					case "btnSave4":
						if(!this.isItemChecked("appInstYn")){
							mvno.ui.alert("APP설치 확인을 확인하세요");
							return false;
						}
						mvno.cmn.ajax(ROOT + "/rcp/openMgmt/updateAppInstCnfm.json", form, function(result) {
							mvno.ui.closeWindowById(form, true);
							mvno.ui.notify("NCMN0001");
							PAGE.GRID1.refresh();
						});
						break;
				}
			}
		},
		//가입자정보
		FORM2 : {
			items : _FORMITEMS_['form2'],
			buttons : {
                left : {
                    btnDocRcv : {
                        label : "서류접수문자발송"
                    }
                },
				right : {
					btnRec : {
						label : "녹취청취"
					},
					btnPaper : {
						label : "서식지보기"
					},
					btnScnStart : {
						label : "스캔하기"
					},
					btnScnSarch : {
						label : "스캔검색"
					},
					btnFaxSarch : {
						label : "URL검색"
					},
					btnReg : {
						label : "이전사용자이미지"
					}
				}
			},
			checkSelectedButtons : ["btnMod", "btnScnStart", "btnReg"],
			onButtonClick : function (btnId, selectedData) {
				var form = this;
				
				if (PAGE.GRID1.getSelectedRowData() == null) {
					mvno.ui.alert("ECMN0002");
				}
				
				var requestKey = PAGE.GRID1.getSelectedRowData().requestKey;
				var resNo = PAGE.GRID1.getSelectedRowData().resNo;
				var scanId = PAGE.GRID1.getSelectedRowData().scanId;

                if(btnId =="btnVoc"){ //상담접수버튼 클릭

                    //상담접수 메뉴 호출(전화번호, 고객명)
                    //goVocMgmt(PAGE.GRID1.getSelectedRowData().ctnVoc, PAGE.GRID1.getSelectedRowData().custNmVoc)

                } else {
					
					//신청 서식지가 없는 사용자들
					if ( requestKey == "" || resNo == null  ) {
						
						// 스캔하기
						if(btnId =="btnScnStart") {
							
							//20180809 MSP_CAN으로 이관된 고객은 사용x
							var cstmrName = PAGE.GRID1.getSelectedRowData().cstmrName;	//고객명
							
							if(cstmrName == "이관고객"){
								mvno.ui.alert("해지이관 고객은 스캔하기를 할 수 없습니다.");
								return;
							}
							
							//해지고객 validation 서비스 체크
							if(!ScanValidation("2")) return;
							
							var width = 415;
							var height = 237;
							var top  = 10;
							var left = 10;
							var form = this;
							var scanId = null;
							//var DBMSType = "C";
							var scanType = "new";
							form.setItemValue("btnType","scan");
							//scanId 받아오기
							mvno.cmn.ajax(ROOT + "/appForm/getScanId.json", "", function(resultData) {
								scanId = resultData.result.scanId;
								
								var data = null;
								
								data = "AGENT_VERSION="+agentVersion+
									"&SERVER_URL="+serverUrl+
									"&VIEW_TYPE=SCAN"+
									"&USE_DEL="+modifyFlag+
									"&USE_STAT="+modifyFlag+
									"&USE_BM="+blackMakingYn+
									"&USE_DEL_BM="+blackMakingFlag+
									"&RGST_PRSN_ID="+userId+
									"&RGST_PRSN_NM="+userName+
									"&ORG_TYPE="+orgType+
									"&ORG_ID="+orgId+
									"&ORG_NM="+orgNm+
									"&RES_NO="+
									"&PARENT_SCAN_ID="+scanId+
									//"&DBMSType="+DBMSType+
									"&CUST_NM="
									;

								callViewer(data,width,height,top,left);
							});
						}
						else if(btnId == "btnReg") {
							//20180809 MSP_CAN으로 이관된 고객은 사용x
							var cstmrName = PAGE.GRID1.getSelectedRowData().cstmrName;	//고객명
							
							if(cstmrName == "이관고객"){
								mvno.ui.alert("해지이관 고객은 이전사용자이미지를 등록 할 수 없습니다.");
								return;
							}
							
							//해지고객 validation 서비스 체크
							if(!ScanValidation("6")) return;
							
							var fileLimitCnt = 5;
							var contractNum = PAGE.GRID1.getSelectedRowData().contractNum;

							mvno.ui.createForm("FORM25");
							PAGE.FORM25.clear();

							var data = PAGE.GRID1.getSelectedRowData();
							PAGE.FORM25.setFormData(data);
							
							mvno.cmn.ajax(ROOT + "/rcp/openMgmt/getFile1.json", form, function(resultData) {
							var totCnt = resultData.result.fileTotalCnt;
							
							if( parseInt(totCnt) > 0){
								PAGE.FORM25.setUserData("file_upload1","limitCount",fileLimitCnt-parseInt(totCnt));
								PAGE.FORM25.setUserData("file_upload1","limitsize",1000);
								PAGE.FORM25.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
							}
							
							if(parseInt(totCnt) == fileLimitCnt){
								PAGE.FORM25.hideItem("file_upload1");
							}

							if(!mvno.cmn.isEmpty(resultData.result.fileNm)){
								PAGE.FORM25.setItemValue("fileName1", resultData.result.fileNm);
								PAGE.FORM25.setItemValue("fileId1", resultData.result.fileId);
								PAGE.FORM25.enableItem("fileDown1");
								PAGE.FORM25.enableItem("fileDel1");
							} else {
								PAGE.FORM25.disableItem("fileDown1");
								PAGE.FORM25.disableItem("fileDel1");
							}

							if(!mvno.cmn.isEmpty(resultData.result.fileNm1)){
								PAGE.FORM25.setItemValue("fileName2", resultData.result.fileNm1);
								PAGE.FORM25.setItemValue("fileId2", resultData.result.fileId1);
								PAGE.FORM25.enableItem("fileDown2");
								PAGE.FORM25.enableItem("fileDel2");
							} else {
								PAGE.FORM25.disableItem("fileDown2");
								PAGE.FORM25.disableItem("fileDel2");
							}

							if(!mvno.cmn.isEmpty(resultData.result.fileNm2)){
								PAGE.FORM25.setItemValue("fileName3", resultData.result.fileNm2);
								PAGE.FORM25.setItemValue("fileId3", resultData.result.fileId2);
								PAGE.FORM25.enableItem("fileDown3");
								PAGE.FORM25.enableItem("fileDel3");
							} else {
								PAGE.FORM25.disableItem("fileDown3");
								PAGE.FORM25.disableItem("fileDel3");
							}

							if(!mvno.cmn.isEmpty(resultData.result.fileNm3)){
								PAGE.FORM25.setItemValue("fileName4", resultData.result.fileNm3);
								PAGE.FORM25.setItemValue("fileId4", resultData.result.fileId3);
								PAGE.FORM25.enableItem("fileDown4");
								PAGE.FORM25.enableItem("fileDel4");
							} else {
								PAGE.FORM25.disableItem("fileDown4");
								PAGE.FORM25.disableItem("fileDel4");
							}

							if(!mvno.cmn.isEmpty(resultData.result.fileNm4)){
								PAGE.FORM25.setItemValue("fileName5", resultData.result.fileNm4);
								PAGE.FORM25.setItemValue("fileId5", resultData.result.fileId4);
								PAGE.FORM25.enableItem("fileDown5");
								PAGE.FORM25.enableItem("fileDel5");
							} else {
								PAGE.FORM25.disableItem("fileDown5");
								PAGE.FORM25.disableItem("fileDel5");
							}
						});

						PAGE.FORM25.setItemValue("contractNum", contractNum);

						var uploader = PAGE.FORM25.getUploader("file_upload1");

							uploader.revive();

							mvno.ui.popupWindowById("FORM25", "이미지관리", 600, 380, {
							onClose2: function(win) {
									uploader.reset();
								}
							});

							PAGE.FORM25.attachEvent("onBeforeFileAdd",function(realName, size){

								var uploaders = this.getUploader("file_upload1");
								var urls = "/rcp/openMgmt/fileUpLoad.do?contractNum="+contractNum;

								uploaders.setURL(urls);
								uploaders.setSLURL(urls);
								uploaders.setSWFURL(urls);

								console.log("uploaderss.... ", uploaders);

								return true;
							});

							PAGE.FORM25.attachEvent("onBeforeFileRemove",function(realName, serverName){
								console.log("onBeforeFileRemove", realName, serverName);
								return true;
							});

							PAGE.FORM25.attachEvent("onFileAdd",function(file){
								console.log("onFileAdd ", this.getFileExtension(file.name));
							});

							PAGE.FORM25.attachEvent("onFileRemove",function(realName, serverName){
								console.log("onFileRemove", realName, serverName);
								return true;
							});

							PAGE.FORM25.attachEvent("onUploadFile",function(realName, serverName){
								console.log("onUploadFile", realName, serverName);
							});

							PAGE.FORM25.attachEvent("onUploadFail",function(realName){
								console.log("onUploadFail", realName );
								return true;
							});

							PAGE.FORM25.attachEvent("onUploadComplete",function(count, data){
								console.log("onUploadComplete", count);
								return true;
							});

							PAGE.FORM25.attachEvent("onUploadCancel",function(realName){
								console.log("onUploadCancel", realName);
								return true;
							});
						}
						else if(btnId == "btnPaper"){
							// [SRM18113000814] ATM즉시개통고객대상 단체상해보험가입을 위한 서식지합본 전산개발요청
							var onOffType = PAGE.GRID1.getSelectedRowData().onOffType;
							//var scanId = PAGE.GRID1.getSelectedRowData().scanId;
							var scanFileCnt = PAGE.FORM2.getItemValue("scanFileCnt");
							var contractNum= PAGE.GRID1.getSelectedRowData().contractNum;
							
							if(scanId == null || scanId == "" || scanFileCnt == "0") {
								if(onOffType == "9"){
									mvno.ui.alert("ATM 개통고객의 경우 신청정보가 없어<br />서식지를 조회할 수 없습니다.");
								}else{
									mvno.ui.alert("서식지정보(SCAN_ID)가 존재하지 않습니다.");
								}
								return;
							}
							
							var cstmrName = PAGE.GRID1.getSelectedRowData().cstmrName;	//고객명
							
							if(cstmrName == "이관고객"){
							 	mvno.ui.alert("해지이관 고객은 서식지를 볼 수 없습니다.");
								return;
							}
							//validation 호출
							//3 : 서식지보기
							if(!ScanValidation("3")) return;
							
							if(maskingYn == "Y" || maskingYn == "1"){
								mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"contractNum":contractNum,"trgtMenuId":"MSP1000098","tabId":"paper"}, function(result){});
							}

							var width = 415;
							var height = 237;
							var top = 10;
							var left = 10;
							
							var form = this;
							
							//var DBMSType = "S";
							
							var data = null;
							
							data = "AGENT_VERSION="+agentVersion+
								"&SERVER_URL="+serverUrl+
								"&VIEW_TYPE=MSPVIEW"+
								"&USE_DEL="+modifyFlag+
								"&USE_STAT="+modifyFlag+
								"&USE_BM="+blackMakingYn+
								"&USE_DEL_BM="+blackMakingFlag+
								"&RGST_PRSN_ID="+userId+
								"&RGST_PRSN_NM="+userName+
								"&ORG_TYPE="+orgType+
								"&ORG_ID="+orgId+
								"&ORG_NM="+orgNm+
								"&RES_NO="+PAGE.GRID1.getSelectedRowData().resNo+
								"&PARENT_SCAN_ID="+PAGE.GRID1.getSelectedRowData().scanId
								//"&DBMSType="+DBMSType
								;
							
							callViewer(data,width,height,top,left);
						}
						else {
							mvno.ui.alert("NCMN0011");
						}

					// requestKey, resNo 가 있으면...
					} else {
						switch (btnId) {

							case "btnRec" :
								//20180809 MSP_CAN으로 이관된 고객은 사용x
								var cstmrName = PAGE.GRID1.getSelectedRowData().cstmrName;	//고객명
								
								if(cstmrName == "이관고객"){
									mvno.ui.alert("해지이관 고객은 녹취 청취를 할 수 없습니다.");
									return;
								}
								
								//해지고객 validation 서비스 체크
								if(!ScanValidation("1")) return;
								
								var fileLimitCnt = 5;

								mvno.ui.createForm("FORM93");
								PAGE.FORM93.clear();

								var strRequestKey = PAGE.GRID1.getSelectedRowData().requestKey;
								
								mvno.cmn.ajax(ROOT + "/org/common/getRequestKey.json", {orgnId:strRequestKey}, function(resultData) {
									var totCnt = resultData.result.fileTotalCnt;
									if( parseInt(totCnt) > 0){
										PAGE.FORM93.setUserData("file_upload1","limitCount",fileLimitCnt-parseInt(totCnt));
										PAGE.FORM93.setUserData("file_upload1","limitsize",1000);
										PAGE.FORM93.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
									}
									
									if(parseInt(totCnt) == fileLimitCnt){
										PAGE.FORM93.hideItem("file_upload1");
									}

									if(!mvno.cmn.isEmpty(resultData.result.fileNm)){
										PAGE.FORM93.setItemValue("fileName1", resultData.result.fileNm);
										PAGE.FORM93.setItemValue("fileId1", resultData.result.fileId);
										PAGE.FORM93.enableItem("fileDown1");
										PAGE.FORM93.enableItem("fileDel1");
									} else {
										PAGE.FORM93.disableItem("fileDown1");
										PAGE.FORM93.disableItem("fileDel1");
									}

									if(!mvno.cmn.isEmpty(resultData.result.fileNm1)){
										PAGE.FORM93.setItemValue("fileName2", resultData.result.fileNm1);
										PAGE.FORM93.setItemValue("fileId2", resultData.result.fileId1);
										PAGE.FORM93.enableItem("fileDown2");
										PAGE.FORM93.enableItem("fileDel2");
									} else {
										PAGE.FORM93.disableItem("fileDown2");
										PAGE.FORM93.disableItem("fileDel2");
									}

									if(!mvno.cmn.isEmpty(resultData.result.fileNm2)){
										PAGE.FORM93.setItemValue("fileName3", resultData.result.fileNm2);
										PAGE.FORM93.setItemValue("fileId3", resultData.result.fileId2);
										PAGE.FORM93.enableItem("fileDown3");
										PAGE.FORM93.enableItem("fileDel3");
									} else {
										PAGE.FORM93.disableItem("fileDown3");
										PAGE.FORM93.disableItem("fileDel3");
									}

									if(!mvno.cmn.isEmpty(resultData.result.fileNm3)){
										PAGE.FORM93.setItemValue("fileName4", resultData.result.fileNm3);
										PAGE.FORM93.setItemValue("fileId4", resultData.result.fileId3);
										PAGE.FORM93.enableItem("fileDown4");
										PAGE.FORM93.enableItem("fileDel4");
									} else {
										PAGE.FORM93.disableItem("fileDown4");
										PAGE.FORM93.disableItem("fileDel4");
									}

									if(!mvno.cmn.isEmpty(resultData.result.fileNm4)){
										PAGE.FORM93.setItemValue("fileName5", resultData.result.fileNm4);
										PAGE.FORM93.setItemValue("fileId5", resultData.result.fileId4);
										PAGE.FORM93.enableItem("fileDown5");
										PAGE.FORM93.enableItem("fileDel5");
									} else {
										PAGE.FORM93.disableItem("fileDown5");
										PAGE.FORM93.disableItem("fileDel5");
									}
								});


								var uploader = PAGE.FORM93.getUploader("file_upload1");

								uploader.revive();

								mvno.ui.popupWindowById("FORM93", "파일관리", 600, 500, {
							 		onClose2: function(win) {
							 			uploader.reset();
							 		}
								});

								break;
							
							// 스캔하기
							case "btnScnStart":
								
								//20180809 MSP_CAN으로 이관된 고객은 스캔하기를 할 수 없다.
								var cstmrName = PAGE.GRID1.getSelectedRowData().cstmrName;	//고객명
								
								if(cstmrName == "이관고객"){
									mvno.ui.alert("해지이관 고객은 스캔 할 수 없습니다.");
									return;
								}
								
								//해지고객 validation 서비스 체크
								if(!ScanValidation("2")) return;
								
								var width = 415;
								var height = 237;
								var top  = 10;
								var left = 10;
								var form = this;
								var scanId = null;
								//var DBMSType = "C";
								var scanType = "new";
								form.setItemValue("btnType","scan");
								//scanId 받아오기
								mvno.cmn.ajax(ROOT + "/appForm/getScanId.json", "", function(resultData) {
									scanId = resultData.result.scanId;
									
									data = "AGENT_VERSION="+agentVersion+
										"&SERVER_URL="+serverUrl+
										"&VIEW_TYPE=SCAN"+
										"&USE_DEL="+modifyFlag+
										"&USE_STAT="+modifyFlag+
										"&USE_BM="+blackMakingYn+
										"&USE_DEL_BM="+blackMakingFlag+
										"&RGST_PRSN_ID="+userId+
										"&RGST_PRSN_NM="+userName+
										"&ORG_TYPE="+orgType+
										"&ORG_ID="+orgId+
										"&ORG_NM="+orgNm+
										"&RES_NO="+
										"&PARENT_SCAN_ID="+scanId+
										//"&DBMSType="+DBMSType+
										"&CUST_NM="
										;

									callViewer(data,width,height,top,left);
									
								});
								
								break;
							
							// 서식지보기
							case "btnPaper":
								//20180809 MSP_CAN으로 이관된 고객은 서식지를 볼 수 없다.
								var cstmrName = PAGE.GRID1.getSelectedRowData().cstmrName;	//고객명
								var contractNum= PAGE.GRID1.getSelectedRowData().contractNum;
								
								if(cstmrName == "이관고객"){
								 	mvno.ui.alert("해지이관 고객은 서식지를 볼 수 없습니다.");
									return;
								}
								//validation 호출
								//3 : 서식지보기
								if(!ScanValidation("3")) return;
								
								if(maskingYn == "Y" || maskingYn == "1"){
									mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"contractNum":contractNum,"trgtMenuId":"MSP1000098","tabId":"paper"}, function(result){});
								}
								 
								var width = 415;
								var height = 237;
								var top = 10;
								var left = 10;
								
								var form = this;
								
								//var DBMSType = "S";
								
								var data = null;
								
								data = "AGENT_VERSION="+agentVersion+
									"&SERVER_URL="+serverUrl+
									"&VIEW_TYPE=MSPVIEW"+
									"&USE_DEL="+modifyFlag+
									"&USE_STAT="+modifyFlag+
									"&USE_BM="+blackMakingYn+
									"&USE_DEL_BM="+blackMakingFlag+
									"&RGST_PRSN_ID="+userId+
									"&RGST_PRSN_NM="+userName+
									"&ORG_TYPE="+orgType+
									"&ORG_ID="+orgId+
									"&ORG_NM="+orgNm+
									"&RES_NO="+PAGE.GRID1.getSelectedRowData().resNo+
									"&PARENT_SCAN_ID="+PAGE.GRID1.getSelectedRowData().scanId
									//"&DBMSType="+DBMSType
									;

								callViewer(data,width,height,top,left);
								
								break;
								
							// 스캔검색
							case "btnScnSarch":
								
								//20180809 MSP_CAN으로 이관된 고객은 사용x
								var cstmrName = PAGE.GRID1.getSelectedRowData().cstmrName;	//고객명
								
								if(cstmrName == "이관고객"){
									mvno.ui.alert("해지이관 고객은 스캔 검색을 할 수 없습니다.");
									return;
								}
								
								//해지고객 validation 서비스 체크
								if(!ScanValidation("4")) return;
								
								var width = 415;
								var height = 237;
								var top = 10;
								var left = 10;
								var form = this;
								
								//var DBMSType = "S";
								var data = null;
								
								data = "AGENT_VERSION="+agentVersion+
									"&SERVER_URL="+serverUrl+
									"&VIEW_TYPE=SCANVIEW"+
									"&USE_DEL="+modifyFlag+
									"&USE_STAT="+modifyFlag+
									"&USE_BM="+blackMakingYn+
									"&USE_DEL_BM="+blackMakingFlag+
									"&RGST_PRSN_ID="+userId+
									"&RGST_PRSN_NM="+userName+
									"&ORG_TYPE="+orgType+
									"&ORG_ID="+orgId+
									"&ORG_NM="+orgNm+
									"&RES_NO="+resNo+
									"&PARENT_SCAN_ID="+scanId
									//"&DBMSType="+DBMSType
									;	

								callViewer(data,width,height,top,left);
								
								break;
								
							// 팩스검색
							case "btnFaxSarch":
								
								//20180809 MSP_CAN으로 이관된 고객은 사용x
								var cstmrName = PAGE.GRID1.getSelectedRowData().cstmrName;	//고객명
								
								if(cstmrName == "이관고객"){
									mvno.ui.alert("해지이관 고객은 팩스 검색을 할 수 없습니다.");
									return;
								}
								
								//해지자 validation 서비스 체크
								if(!ScanValidation("5")) return;
								
								var width = 415;
								var height = 237;
								var top = 10;
								var left = 10;
								var form = this;
								
								//var DBMSType = "S";
								var data = null;
								
								data = "AGENT_VERSION="+agentVersion+
									"&SERVER_URL="+serverUrl+
									"&VIEW_TYPE=URLVIEW"+
									"&USE_DEL="+modifyFlag+
									"&USE_STAT="+modifyFlag+
									"&USE_BM="+blackMakingYn+
									"&USE_DEL_BM="+blackMakingFlag+
									"&RGST_PRSN_ID="+userId+
									"&RGST_PRSN_NM="+userName+
									"&ORG_TYPE="+orgType+
									"&ORG_ID="+orgId+
									"&ORG_NM="+orgNm+
									"&RES_NO="+resNo+
									"&PARENT_SCAN_ID="+scanId
									//"&DBMSType="+DBMSType
									;

								callViewer(data,width,height,top,left);
								
								break;

                            case "btnDocRcv":
                                var url = "/org/workMgmt/docRcvRequestView.do";
                                var param = "?resNo=" + PAGE.GRID1.getSelectedRowData().resNo + "&viewType=OPEN";

                                mvno.ui.popupWindowTop(ROOT + url + param, "서류접수문자발송", 1000, 770);
                                break;
						}
					}
				}

			}
		},
		//녹취파일 관리
		FORM93 : {
				items : [
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80},

						{type: 'fieldset', label: '녹취 파일 첨부',  inputWidth:500, offsetLeft:10, offsetTop:10, list:[
						{type: "block", list: [
							{type: 'input', name: 'fileName1', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
							{type: 'hidden', name: 'fileId1', label: '파일ID'},
							{type: 'hidden', name: 'strRequestKey', label: 'REQUEST_KEY'},
							{type: "newcolumn"},
							{type:"button", name: 'fileDown1', value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: 'fileDel1', value:"삭제"}
						]},

						{type: "block", list: [
							{type: 'input', name: 'fileName2', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
							{type: 'hidden', name: 'fileId2', label: '파일ID'},
							{type: "newcolumn"},
							{type:"button", name: 'fileDown2', value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: 'fileDel2', value:"삭제"}
						]},

						{type: "block", list: [
							{type: 'input', name: 'fileName3', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
							{type: 'hidden', name: 'fileId3', label: '파일ID'},
							{type: "newcolumn"},
							{type:"button", name: 'fileDown3', value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: 'fileDel3', value:"삭제"}
						]},

						{type: "block", list: [
							{type: 'input', name: 'fileName4', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
							{type: 'hidden', name: 'fileId4', label: '파일ID'},
							{type: "newcolumn"},
							{type:"button", name: 'fileDown4', value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: 'fileDel4', value:"삭제"}
						]},

						{type: "block", list: [
							{type: 'input', name: 'fileName5', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
							{type: 'hidden', name: 'fileId5', label: '파일ID'},
							{type: "newcolumn"},
							{type:"button", name: 'fileDown5', value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: 'fileDel5', value:"삭제"}
						]},

						{type: "block", list: [
							{type: "upload", label:"파일업로드" ,name: "file_upload1",width: 384 ,inputWidth: 330 ,url: "/org/common/fileUpLoad4.do" ,autoStart: false, offsetLeft:20, userdata: { limitCount : 3, limitsize: 1000, delUrl:"/org/common/deleteFile2.json"} }
						]}
					]}
				],
				buttons: {
					center: {
						btnSave: { label: "확인" },
						btnClose: { label: "닫기" }
					}
				},
				onChange : function (name, value){
					var form = this;
				},
				onButtonClick : function(btnId) {

					var form = this; // 혼란방지용으로 미리 설정 (권고)
					var fileLimitCnt = 5;
					var strRequestKey = PAGE.GRID1.getSelectedRowData().requestKey;

					PAGE.FORM93.setItemValue("strRequestKey", strRequestKey);

					switch (btnId) {
						case "btnSave":
							mvno.cmn.ajax(ROOT + "/org/common/insertMgmt2.json", form, function(resultData) {
								PAGE.FORM1.setItemValue("fileId1", resultData.result.fileId1);
								PAGE.FORM1.setItemValue("fileId2", resultData.result.fileId2);
								PAGE.FORM1.setItemValue("fileId3", resultData.result.fileId3);
								PAGE.FORM1.setItemValue("fileId4", resultData.result.fileId4);
								PAGE.FORM1.setItemValue("fileId5", resultData.result.fileId5);
								mvno.ui.closeWindowById(form);
								mvno.ui.notify("NCMN0015");
								PAGE.FORM93.clear();
							});
							break;
						case "btnClose" :
							mvno.ui.closeWindowById(form);
							break;
						case "fileDown1" :
							 mvno.cmn.download('/org/common/downFile2.json?fileId=' + PAGE.FORM93.getItemValue("fileId1"));
							 break;
						case "fileDown2" :
						   	 mvno.cmn.download('/org/common/downFile2.json?fileId=' + PAGE.FORM93.getItemValue("fileId2"));
								break;
						case "fileDown3" :
						   	 mvno.cmn.download('/org/common/downFile2.json?fileId=' + PAGE.FORM93.getItemValue("fileId3"));
								break;
						case "fileDown4" :
						   	 mvno.cmn.download('/org/common/downFile2.json?fileId=' + PAGE.FORM93.getItemValue("fileId4"));
								break;
						case "fileDown5" :
						   	 mvno.cmn.download('/org/common/downFile2.json?fileId=' + PAGE.FORM93.getItemValue("fileId5"));
								break;
						case "fileDel1" :
							 mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM93.getItemValue("fileId1"),orgnId:strRequestKey,attSctn:'REC'} , function(resultData) {
								mvno.ui.notify("NCMN0014");
								PAGE.FORM93.setItemValue("fileName1", "");
								PAGE.FORM93.disableItem("fileDown1");
								PAGE.FORM93.disableItem("fileDel1");

								PAGE.FORM93.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
								PAGE.FORM93.setUserData("file_upload1","limitsize",1000);
								PAGE.FORM93.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
								PAGE.FORM93.showItem("file_upload1");
							 });
								break;
						case "fileDel2" :
							mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM93.getItemValue("fileId2"),orgnId:strRequestKey,attSctn:'REC'} , function(resultData) {
								mvno.ui.notify("NCMN0014");
								PAGE.FORM93.setItemValue("fileName2", "");
								PAGE.FORM93.disableItem("fileDown2");
								PAGE.FORM93.disableItem("fileDel2");

								PAGE.FORM93.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
								PAGE.FORM93.setUserData("file_upload1","limitsize",1000);
								PAGE.FORM93.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
								PAGE.FORM93.showItem("file_upload1");

							 });
							break;
						case "fileDel3" :
							mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM93.getItemValue("fileId3"),orgnId:strRequestKey,attSctn:'REC'} , function(resultData) {
								mvno.ui.notify("NCMN0014");
								PAGE.FORM93.setItemValue("fileName3", "");
								PAGE.FORM93.disableItem("fileDown3");
								PAGE.FORM93.disableItem("fileDel3");

								PAGE.FORM93.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
								PAGE.FORM93.setUserData("file_upload1","limitsize",1000);
								PAGE.FORM93.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
								PAGE.FORM93.showItem("file_upload1");

							 });
							break;
						case "fileDel4" :
							mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM93.getItemValue("fileId4"),orgnId:strRequestKey,attSctn:'REC'} , function(resultData) {
								mvno.ui.notify("NCMN0014");
								PAGE.FORM93.setItemValue("fileName4", "");
								PAGE.FORM93.disableItem("fileDown4");
								PAGE.FORM93.disableItem("fileDel4");

								PAGE.FORM93.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
								PAGE.FORM93.setUserData("file_upload1","limitsize",1000);
								PAGE.FORM93.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
								PAGE.FORM93.showItem("file_upload1");

							 });
							break;
						case "fileDel5" :
							mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM93.getItemValue("fileId5"),orgnId:strRequestKey,attSctn:'REC'} , function(resultData) {
								mvno.ui.notify("NCMN0014");
								PAGE.FORM93.setItemValue("fileName5", "");
								PAGE.FORM93.disableItem("fileDown5");
								PAGE.FORM93.disableItem("fileDel5");

								PAGE.FORM93.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
								PAGE.FORM93.setUserData("file_upload1","limitsize",1000);
								PAGE.FORM93.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
								PAGE.FORM93.showItem("file_upload1");

							 });
							break;
					}
				},
				onValidateMore : function (data){
					//
				}
		},
		FORM3 : {
			items : _FORMITEMS_['form3'],
			buttons : {
				center : {
					btnSave : {
						label : "저장"
					},
					btnClose : {
						label : "닫기"
					}
				}
			},

			onButtonClick : function (btnId) {

				var form = this;

				switch (btnId) {
					case "btnSave":
						mvno.cmn.ajax(ROOT + "/org/orgmgmt/insertMgmt.json", form, function (result) {
							mvno.ui.closeWindowById(form, true);
							mvno.ui.notify("NCMN0001");
							PAGE.GRID1.refresh();
							PAGE.FORM2.clear();
							PAGE.FORM1.setItemValue("orgnNm", result.orgnNm);
						});
						break;
					case "btnClose":
						mvno.ui.closeWindowById(form);
						break;
					case "btnUseFind":
						mvno.ui.codefinder("USER", function (result) {
							form.setItemValue("respnPrsnId", result.usrNm);
							form.setItemValue("respnPrsnNum", result.usrId);
							form.setItemValue("respnPrsnMblphn", result.mblphnNum);
							form.setItemValue("respnPrsnEmail", result.email);
						});
						break;
					case "btnOrgFind":
						mvno.ui.codefinder("ORG", function (result) {
							form.setItemValue("hghrOrgnCd", result.orgnId);
							form.setItemValue("hghrOrgnNm", result.orgnNm);
						});
						break;
					case "btnPostFind":
						mvno.ui.alert("ORGN0001");
						break;
				}
			}
		},
		// 단말 이력 조회
		GRID9 : {
			css : { height : "250px", width : "951px" },
			header : "변경일시,변경작업,모델명,단말일련번호,USIM카드일련번호,WIFI_MAC-ID",
			columnIds : "evntStr,evntNm,modelName,intmSrlNo,iccId,wifiMacId",
			colAlign : "center,left,left,center,center,center",
			colTypes : "roXdt,ro,ro,ro,ro,ro",
			widths : "150,120,120,150,200,194",
			colSorting : "str,str,str,str,str,str",
			paging : true
		},

		// 상품 이력 조회
		GRID10 : {
			css : { height : "250px", width : "951px" },
			header : "변경일시,상품명,상품유형,시작일시,종료일시,상태",
			columnIds : "evntTrtmDt,socNm,serviceTypeNm,effectiveDate,expirationDate,status",
			colAlign : "center,left,left,center,center,center",
			colTypes : "roXdt,ro,ro,roXdt,roXdt,ro",
			widths : "150,194,190,150,150,100",
			colSorting : "str,str,str,str,str,str",
			paging : true
		},

		// 청구계정 이력 조회 -> 납부방법
		GRID11:{
			css : { height : "250px", width : "951px" },
			header : "변경일시,납부방법,BAN구분,미납상태,실납부자주민번호,납부자주소,납부자이름",
			columnIds : "evntTrtmDt,blBillingMethod,blSpclBanCode,colDelinqStatus,bankAcctHolderId,addr,banLinkName",
			colAlign : "center,center,center,center,center,left,left",
			colTypes : "ro,ro,ro,ro,ro,ro,ro",
			widths : "150,100,100,100,150,250,84",
			colSorting : "str,str,str,str,str,str,str",
			paging : true
		},

		// 계약이력 리스트
		GRID12:{
			css : { height : "250px", width : "951px" },
			header : "변경일시,변경작업,서비스계약번호,가입자,가입자상태,요금제,대리점",
			columnIds : "evntChangeDate,evntNm,svcCntrNo,subLinkName,subStatusNm,socNm,orgnNm",
			colAlign : "center,left,center,center,left,left,left",
			colTypes : "roXdt,ro,ro,ro,ro,ro,ro",
			widths : "134,90,110,150,100,200,150",
			colSorting : "str,str,str,str,str,str,str",
			paging : true
		},
		
		// 청구 수납 이력 조회
		GRID13:{
			css : { height : "250px", width : "951px" },
			header : "청구월,청구항목,청구금액,조정금액,납부방법,수납일자,수납금액",
			columnIds : "billYm,billItemNm,invAmnt,adjAmnt,pymnMthdNm,blpymDate,pymAmnt",
			colAlign : "center,left,right,right,left,center,right",
			colTypes : "roXdm,ro,ron,ron,ro,roXd,ron",
			widths : "130,160,120,120,160,120,124",
			colSorting : "str,str,int,int,str,str,int",
			paginsSize : 20,
			paging: true
		},
		
		// 할인유형이력 조회
		GRID15:{
			css : { height : "250px", width : "951px" },
			title : "",
			header : "변경일시,전문유형,할인유형,약정기간,보증보험가입번호,가입상태,SIM ID,시작일자,종료일자",
			columnIds : "evntTrtmDt,evntNm,sprtTpNm,enggMnthCnt,grntInsrMngmNo,statNm,simId,simStartDt,simEndDt",
			colAlign : "center,center,center,center,center,center,center,center,center",
			colTypes : "roXdt,ro,ro,ro,ro,ro,ro,roXd,roXd",
			widths : "150,100,80,80,120,100,100,100,104",
			colSorting : "str,str,str,str,str,str,str,str,str",
			paging : true
		},
		
		// 기기변경이력 조회
		GRID16:{
			css : { height : "250px", width : "951px" },
			title : "",
			header : "기변일시,기변유형,기변사유,모델명,모델ID,단말일련번호,할부개월,약정개월,출고가,할부원금,보증보험번호,판매정책",
			columnIds : "applStrtDttm,dvcChgTpNm,dvcChgRsnNm,modelNm,modelId,intmSrlNo,instMnthCnt,enggMnthCnt,outAmnt,instAmnt,grntInsrMngmNo,salePlcyNm",
			colAlign : "center,center,center,left,center,center,right,right,right,right,center,left",
			colTypes : "roXdt,ro,ro,ro,ro,ro,ro,ro,ron,ron,ro,ro",
			widths : "120,80,80,100,80,100,80,80,100,100,100,150",
			colSorting : "str,str,str,str,str,str,int,int,int,int,str,str",
			paging : true,
			showPagingAreaOnInit: true,
			onButtonClick : function(btnId, selectedData) {
				var form = this;
				switch (btnId) {
					case "btnExcel01" : 
						if(PAGE.GRID16.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}
						
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						mvno.cmn.download(ROOT + "/rcp/openMgmt/getDvcChgListExcel.json?menuId=MSP1000099",true, {"CONTRACT_NUM":rowData.contractNum});
						
						break;
				}
			}
		},
				
		FORM25 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80},
				{type: 'fieldset', label: '이전 사용자 이미지 첨부',  inputWidth:500, offsetLeft:10, list:[
					{type: "block",
						list: [
							{type: 'input', name: 'fileName1', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
							{type: 'hidden', name: 'fileId1', label: '파일ID'},
							{type: "newcolumn"},
							{type:"button", name: 'fileDown1', value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: 'fileDel1', value:"삭제"}
						]
					},
					{type: "block",
						list: [
							{type: 'input', name: 'fileName2', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
							{type: 'hidden', name: 'fileId2', label: '파일ID'},
							{type: "newcolumn"},
							{type:"button", name: 'fileDown2', value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: 'fileDel2', value:"삭제"}
						]
					},
					{type: "block",
						list: [
							{type: 'input', name: 'fileName3', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
							{type: 'hidden', name: 'fileId3', label: '파일ID'},
							{type: "newcolumn"},
							{type:"button", name: 'fileDown3', value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: 'fileDel3', value:"삭제"}
						]
					},
					{type: "block",
						list: [
							{type: 'input', name: 'fileName4', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
							{type: 'hidden', name: 'fileId4', label: '파일ID'},
							{type: "newcolumn"},
							{type:"button", name: 'fileDown4', value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: 'fileDel4', value:"삭제"}
						]
					},
					{type: "block",
						list: [
							{type: 'input', name: 'fileName5', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
							{type: 'hidden', name: 'fileId5', label: '파일ID'},
							{type: "newcolumn"},
							{type:"button", name: 'fileDown5', value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: 'fileDel5', value:"삭제"}
						]
					},
					{type: "block",
						list: [
							{type: "upload", label:"파일업로드", name: "file_upload1", width: 384, inputWidth: 330, url: "/rcp/custMgmt/fileUpLoad.do", autoStart: false, offsetLeft:20, userdata: { limitCount : 5, limitsize: 1000, delUrl:"/org/common/deleteFile2.json"} }
						]
					}
				]
			}
		],
		buttons: {
			center: {
				btnSave: { label: "확인" },
				btnClose: { label: "닫기" }
			}
		},
		onChange : function (name, value){
			var form = this;
		},
		onButtonClick : function(btnId) {

			var form = this; // 혼란방지용으로 미리 설정 (권고)
			var fileLimitCnt = 5;

				switch (btnId) {
					case "btnSave":
						mvno.ui.closeWindowById(form, true);
						mvno.ui.notify("NCMN0015");
						PAGE.FORM25.clear();
						break;
					case "btnClose" :
						mvno.ui.closeWindowById(form);
						break;
					case "fileDown1" :
						 mvno.cmn.download('/org/common/downFile.json?fileId=' + PAGE.FORM25.getItemValue("fileId1"));
						 break;
					case "fileDown2" :
						 mvno.cmn.download('/org/common/downFile.json?fileId=' + PAGE.FORM25.getItemValue("fileId2"));
							break;
					case "fileDown3" :
						 mvno.cmn.download('/org/common/downFile.json?fileId=' + PAGE.FORM25.getItemValue("fileId3"));
							break;
					case "fileDown4" :
						 mvno.cmn.download('/org/common/downFile.json?fileId=' + PAGE.FORM25.getItemValue("fileId4"));
							break;
					case "fileDown5" :
						 mvno.cmn.download('/org/common/downFile.json?fileId=' + PAGE.FORM25.getItemValue("fileId5"));
							break;
					case "fileDel1" :
						 mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM25.getItemValue("fileId1"),orgnId:form.getItemValue("orgnId"),attSctn:'RCP'} , function(resultData) {
							mvno.ui.notify("NCMN0014");
							PAGE.FORM25.setItemValue("fileName1", "");
							PAGE.FORM25.disableItem("fileDown1");
							PAGE.FORM25.disableItem("fileDel1");

							PAGE.FORM25.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
							PAGE.FORM25.setUserData("file_upload1","limitsize",1000);
							PAGE.FORM25.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
							PAGE.FORM25.showItem("file_upload1");
						 });
							break;
					case "fileDel2" :
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM25.getItemValue("fileId2"),orgnId:form.getItemValue("orgnId"),attSctn:'RCP'} , function(resultData) {
							mvno.ui.notify("NCMN0014");
							PAGE.FORM25.setItemValue("fileName2", "");
							PAGE.FORM25.disableItem("fileDown2");
							PAGE.FORM25.disableItem("fileDel2");

							PAGE.FORM25.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
							PAGE.FORM25.setUserData("file_upload1","limitsize",1000);
							PAGE.FORM25.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
							PAGE.FORM25.showItem("file_upload1");

						 });
						break;
					case "fileDel3" :
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM25.getItemValue("fileId3"),orgnId:form.getItemValue("orgnId"),attSctn:'RCP'} , function(resultData) {
							mvno.ui.notify("NCMN0014");
							PAGE.FORM25.setItemValue("fileName3", "");
							PAGE.FORM25.disableItem("fileDown3");
							PAGE.FORM25.disableItem("fileDel3");

							PAGE.FORM25.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
							PAGE.FORM25.setUserData("file_upload1","limitsize",1000);
							PAGE.FORM25.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
							PAGE.FORM25.showItem("file_upload1");

						 });
						break;

					case "fileDel4" :
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM25.getItemValue("fileId4"),orgnId:form.getItemValue("orgnId"),attSctn:'RCP'} , function(resultData) {
							mvno.ui.notify("NCMN0014");
							PAGE.FORM25.setItemValue("fileName4", "");
							PAGE.FORM25.disableItem("fileDown4");
							PAGE.FORM25.disableItem("fileDel4");

							PAGE.FORM25.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
							PAGE.FORM25.setUserData("file_upload1","limitsize",1000);
							PAGE.FORM25.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
							PAGE.FORM25.showItem("file_upload1");

						 });
						break;


					case "fileDel5" :
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM25.getItemValue("fileId5"),orgnId:form.getItemValue("orgnId"),attSctn:'RCP'} , function(resultData) {
							mvno.ui.notify("NCMN0014");
							PAGE.FORM25.setItemValue("fileName5", "");
							PAGE.FORM25.disableItem("fileDown5");
							PAGE.FORM25.disableItem("fileDel5");

							PAGE.FORM25.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
							PAGE.FORM25.setUserData("file_upload1","limitsize",1000);
							PAGE.FORM25.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
							PAGE.FORM25.showItem("file_upload1");

						 });
						break;
				}
			},
			onValidateMore : function (data){
				//
			}
		},
		FORM99 : {
			items : _FORMITEMS_['form99'],
			buttons : {
				center : {
					btnClose : {
						label : "닫기"
					}
				}
			},
			onButtonClick : function (btnId, selectedData) {
				var form = this;
				switch (btnId) {
				case "btnClose":
					mvno.ui.closeWindowById(PAGE.FORM99, true); // id 대신 form 도 가능
					break;
				}
			}
		}

};

var PAGE = {
	title : "${breadCrumb.title}",
	breadcrumb : ' ${breadCrumb.breadCrumb}',
	buttonAuth: ${buttonAuth.jsonString},
	onOpen : function () {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		mvno.ui.createTabbar("TABBAR1");

		var lstComActvDateT = new Date().format('yyyymmdd');
		var time = new Date().getTime();
		time = time - 1000 * 3600 * 24 * 7;
		var lstComActvDateF = new Date(time);

		PAGE.FORM1.setItemValue("lstComActvDateF", lstComActvDateF);
		PAGE.FORM1.setItemValue("lstComActvDateT", lstComActvDateT);
		
		PAGE.FORM1.disableItem("searchName");

		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2020"}, PAGE.FORM1, "searchGbn"); // 검색구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0037"}, PAGE.FORM1, "subStatus"); // 계약상태
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9004"}, PAGE.FORM1, "reqBuyTypeS"); // 구매유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0049",orderBy:"etc1"}, PAGE.FORM1, "operType"); // 업무구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0063"}, PAGE.FORM1, "prodType"); // 상품구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM1, "onOffType"); // 모집경로
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0058"}, PAGE.FORM1, "sprtTp"); // 할인유형

		// 평생할인 부가서비스 가입 검증 화면에서 넘어왔을때
		if ("${param.trgtMenuId}" == "MSP1010057") {
			// 신청일자 초기화, disabled
			PAGE.FORM1.setItemValue("lstComActvDateF", "");
			PAGE.FORM1.setItemValue("lstComActvDateT", "");
			PAGE.FORM1.disableItem("lstComActvDateF");
			PAGE.FORM1.disableItem("lstComActvDateT");

			// 검색조건 계약번호, 넘어온 계약번호 입력
			PAGE.FORM1.enableItem("searchName");
			PAGE.FORM1.setItemValue("searchGbn", "2");
			PAGE.FORM1.setItemValue("searchName", "${param.prmContNum}");
			PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
		}
	}
};

//상담접수 화면 호출
function goVocMgmt(subscriberNo, cstmrNm)
{
	var title = "상담접수"
	var menuId = "VOC1001010";
	var url = "/voc/vocMgmt/vocMgmt.do";
	var param = "?menuId=" + menuId + "&subscriberNo=" + subscriberNo + "&cstmrNm=" + cstmrNm;
	
	var myTabbar = window.parent.mainTabbar;
	
	if (myTabbar.tabs(url)) {
		myTabbar.tabs(url).setActive();
	}else{
		myTabbar.addTab(url, title, null, null, true);
	}
	
	myTabbar.tabs(url).attachURL(url + param);
	
	// 잘안보여서.. 일단 살짝 Delay 처리 
	setTimeout(function() {
		//mainLayout.cells("b").progressOff();
	}, 100);
	
}

//20180809 해지고객인지 체크 620440007
//gridRow.contractNum
function ScanValidation(div){
	var canChk = PAGE.FORM2.getItemValue("canChk");

	if(canChk != null && canChk != "0") {
		//1:녹취청취, 2:스캔하기, 3:서식지보기, 4:스캔검색, 5:팩스검색, 6:이전사용자이미지
		if(div == "1"){	
			mvno.ui.alert("해지이관 고객은 녹취청취를 할 수 없습니다.");
		}else if(div == "2"){
			mvno.ui.alert("해지이관 고객은 스캔하기를 할 수 없습니다.");
		}else if(div == "3"){
			mvno.ui.alert("해지이관 고객은 서식지를 볼 수 없습니다.");
		}else if(div == "4"){
			mvno.ui.alert("해지이관 고객은 스캔검색을 할 수 없습니다.");
		}else if(div == "5"){
			mvno.ui.alert("해지이관 고객은 팩스검색을 할 수 없습니다.");
		}else if(div == "6"){
			mvno.ui.alert("해지이관 고객은 이전사용자이미지를 올릴 수 없습니다.");
		}

		return false;
	}
	
	return true;
}


//스캔 호출
function callViewer(data,width,height,top,left){
	var url = scanSearchUrl + "?" + encodeURIComponent(data);
	
	// 타임아웃 설정 - 10초이내 응답없으면 서비스 실행상태가 아님.
	var timeOutTimer = window.setTimeout(function (){
		mvno.ui.confirm("이미지 프로그램이 설치되지 않았거나 실행중이 아닙니다. 설치페이지로 이동 하시겠습니까?",function(){
			window.open(scanDownloadUrl, "", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);         // 설치페이지로 이동
		});
	}, 10000);
	
	$.ajax({
		type : "GET", 
		url : url,
		crossDomain : true,
		dataType : 'jsonp',
		success : function(args){
			window.clearTimeout(timeOutTimer);
			if(args.RESULT == "SUCCESS") {
				console.log("SUCCESS");
			} else if(args.RESULT == "ERROR_TYPE2") {
				mvno.ui.alert("버전이 다릅니다. 설치페이지로 이동해주세요.");
				window.open(scanDownloadUrl, "", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);         // 설치페이지로 이동
			} else {
				mvno.ui.alert(args.RESULT + " : " + args.RESULT_MSG);
			}
		}
	});
}
</script>
