<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-application"></div>

<!-- 녹취파일 등록 폼 -->
<div id="FORM3" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

var saleItmCdValue = '';
var lbl="";
var isReg = true;

var reqFlag = false;
var sOrgnId="";
var sOrgnNm="";
var hghrOrgnCd = "";
var typeCd = '${orgnInfo.typeCd}';

var userId = '${loginInfo.userId}';
var userName = '${loginInfo.userName}';
var orgId = '${orgnInfo.orgnId}';
var orgNm = '${orgnInfo.orgnNm}';

//로그인한 사용자의 조직 유형값 셋팅(총판,일반,특수)
var typeDtlCd2 = '${orgnInfo.typeDtlCd2}';

var orgType = "";

if (typeCd == '20' || typeCd == '30' ) {
	reqFlag = true;
	sOrgnId = '${orgnInfo.orgnId}';
	sOrgnNm = '${orgnInfo.orgnNm}';
	if(typeCd == '30'){
		hghrOrgnCd = '${orgnInfo.hghrOrgnCd}';
	}
}
//조직 유형 - S:판매점, D:대리점, K:개통센터, "10:본사조직, 20:대리점, 30:판매점"
if(typeCd == "10") {
	orgType = "K";
}else if(typeCd == "20") {
	orgType = "D";
}else if(typeCd == "30") {
	orgType = "S";
}

var DHX = {
	FORM1 : {
		items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:70, blockOffset:0},
					{type:"block", list:[
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchStartDt', label: '신청일자', value: "${startDate}", calendarPosition: 'bottom', readonly:true ,width:85,offsetLeft:10},
					{type: 'newcolumn'},
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', value: "${endDate}", calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:85},
					{type: 'newcolumn'},
					{type:"select", width:85, offsetLeft:71, label:"검색구분",name:"pSearchGbn", userdata:{lov:"RCP0043"}},
					{type:"newcolumn"},
					{type:"input", width:85, name:"pSearchName",value:""},
					{type: 'newcolumn'},
					{type:"select", width:85,offsetLeft:26, label:"신청취소",name:"pPstate", userdata:{lov:"RCP0005"}}]},
					
					{type:"block", list:[
					{type:"input", label:"대리점", name: "pAgentCode",value:sOrgnId, width:85,readonly: reqFlag, offsetLeft:10},
					{type:"newcolumn"},
					{type:"button", value:"찾기", name:"btnOrgFind",disabled:false},
					{type:"newcolumn"},
					{type:"input", name: "pAgentName",value:sOrgnNm, width:100,readonly: true},
					{type:"newcolumn"},
					{type:"select", width:85, offsetLeft:21, label:"진행상태",name:"pRequestStateCode",userdata:{lov:"RCP0006"}},
					{type:"newcolumn"},
					{type:"select", width:85, offsetLeft:115, label:"구매유형",name:"reqBuyType", userdata:{lov:"RCP0009"}}]},

					{type:"block", list:[
					{type:"newcolumn"},
					{type:"newcolumn"},
					//조직 관련 히든 컬럼
					{type:"hidden",name: "hghrOrgnCd",value:hghrOrgnCd},{type:"hidden",name: "typeCd",value:typeCd},
					//녹취 파일용 히든 컬럼
					{type:"hidden",name: "fileId1"},{type:"hidden",name: "fileId2"},{type:"hidden",name: "fileId3"},{type:"hidden",name: "fileId4"},{type:"hidden",name: "fileId5"}]},

					{type: 'newcolumn', offset:150},
					{type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수
					{type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수
					{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search2"}
				],
				onXLE : function() {},
				onButtonClick : function(btnId) {
					var form = this;

					switch (btnId) {
						case "btnSearch":
							
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") {
								return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기
							}
							
							PAGE.GRID1.list(ROOT + "/pps/rcpmgmt/rcpListAjax2.json", form, {
								callback : function () {
									PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
									PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								}
							});
							
							break;
						case "btnOrgFind":
							mvno.ui.codefinder("ORG", function(result) {
								form.setItemValue("pAgentCode", result.orgnId);
								form.setItemValue("pAgentName", result.orgnNm);
							});
						break;
					}
				},
				onChange : function(name, value) {//onChange START
					// 검색구분
					if(name == "pSearchGbn") {
						PAGE.FORM1.setItemValue("pSearchName", "");

						if(value == null || value == "") {
							var searchEndDt = new Date().format('yyyymmdd');
							var time = new Date().getTime();
							time = time - 1000 * 3600 * 24 * 7;
							var searchStartDt = new Date(time);

							// 신청일자 Enable
							PAGE.FORM1.enableItem("searchStartDt");
							PAGE.FORM1.enableItem("searchEndDt");

							PAGE.FORM1.setItemValue("pSearchName", "");

							PAGE.FORM1.setItemValue("searchStartDt", searchStartDt);
							PAGE.FORM1.setItemValue("searchEndDt", searchEndDt);

							PAGE.FORM1.disableItem("pSearchName");
						}
						else {
							PAGE.FORM1.setItemValue("searchStartDt", "");
							PAGE.FORM1.setItemValue("searchEndDt", "");

							// 신청일자 Disable
							PAGE.FORM1.disableItem("searchStartDt");
							PAGE.FORM1.disableItem("searchEndDt");

							PAGE.FORM1.enableItem("pSearchName");
						}
					}

				},
				onValidateMore : function (data){
					if (this.getItemValue("searchStartDt", true) > this.getItemValue("searchEndDt", true)) {
						this.pushError("searchEndDt", "조회기간", "종료일자가 시작일자보다 클 수 없습니다.");
						this.resetValidateCss();
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
					
					if( data.pSearchGbn != "" && data.pSearchName.trim() == ""){
						this.pushError("pSearchName", "검색어", "검색어를 입력하세요");
						PAGE.FORM1.setItemValue("pSearchName", ""); // 검색어 초기화
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}else{
						// 검색조건 없는 경우 조회기간 제한
						/* if(!mvno.cmn.checkAllowDate(PAGE.FORM1.getItemValue("searchEndDt", true), PAGE.FORM1.getItemValue("searchStartDt", true), 30)) {
							this.pushError("", "조회기간", "검색어가 없는 경우 조회기간은 30일이내로 선택하세요");
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						} */
					}
				}
	},
	//신청관리 리스트
	GRID1 : {
		css : {
				height : "565px"
		},
		title : "조회결과",
		header : "구분,예약번호,고객명,생년월일,성별,휴대폰번호,대리점,요금제,업무구분,신청일자,진행상태,신청서상태,모집경로,유입경로,녹취여부,판매점명,판매자명", //17개
		columnIds : "serviceName,resNo,cstmrName,birthDay,cstmrGenderStr,cstmrMobile,agentName,socName,operName,reqInDay,requestStateName,pstateName,onOffName,openMarketReferer,recYn,shopNm,usrNm",
		colAlign : "center,center,left,center,center,center,left,left,center,center,center,center,center,center,center,left,left",
		colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
		colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
		widths : "50,70,80,80,40,120,200,250,70,80,70,70,70,70,50,200,180",
		paging: true,
		pageSize:20,
		buttons : {
			hright : {
				btnDnExcel : { label : "엑셀다운로드" }
			}
		},
		checkSelectedButtons : ["btnDtl"],
		onRowDblClicked : function(rowId, celInd) {
			this.callEvent('onButtonClick', ['btnDtl']);
		},
		onButtonClick : function(btnId, selectedData) {
			var form = this;
			switch (btnId) {
				case 'btnDnExcel' : //엑셀다운로드 버튼 클릭시
					
					var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
					PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
					if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") {
						return; //버튼 최초 클릭일때만 조회가능하도록
					}
					
					if(PAGE.GRID1.getRowsNum() == 0) {
						mvno.ui.alert("데이터가 존재하지 않습니다.");
						return;
					}
					
					var searchData =  PAGE.FORM1.getFormData(true);
					mvno.cmn.download('/pps/rcpmgmt/rcpExcelDownNew.json'+"?menuId=PPS1100010",true,{postData:searchData});
					
					break;

				case 'btnDtl' : //Row 더블클릭시 Start
					mvno.ui.createForm("FORM2");
					PAGE.FORM2.clear();

					mvno.ui.showButton("FORM2" , "btnMod");
					mvno.ui.showButton("FORM2" , "btnSend");
					mvno.ui.hideButton("FORM2" , "btnSave");

					//GRID로부터 한건의 데이터를 가져온다.
					var rowData = PAGE.GRID1.getSelectedRowData();
					var strRequestKey = PAGE.GRID1.getSelectedRowData().requestKey;

					// 조회
					mvno.cmn.ajax2(ROOT + "/pps/rcpmgmt/rcpListAjax.json", {requestKey:strRequestKey}, function(resultData) {
						rowData = resultData.result.data;
						
						console.log("date=" + new Date());
						
						// 초기 설정
						form2Change(PAGE.FORM2, 0, "init");
						
						// N-STEP 전송 버튼 Hide/Show
						if(rowData.requestStateCode == "21") {
							mvno.ui.hideButton("FORM2" , "btnSend");
						}
						
						PAGE.FORM2.setFormData(rowData);

						//---------------------------------------------------------------------------------------------------------
						// 메뉴 숨김/보임 설정 START

						var hideItems = new Array();
						var showItems = new Array();

						// 등록시 숨길 메뉴 설정
						hideItems = [
									 	"CARD",			// 카드정보
									 	"BANK",			// 은행정보
									 	"FOREIGNER",	// 외국인정보
									 	"BUSINESS",		// 사업자정보
									 	"MINOR",		// 법정대리인
									 	"REQMOVE",		// 번호이동정보
									 	"NEWREQ",		// 신규가입
//									 	"DLVRY",		// 배송정보
									 	"DVCCHG"		// 기기변경정보
									 ];

						showItems = [
								 	"faxnum",		// 팩스(신청서정보)
								 	"btnPaper",		// 서식지보기(신청서정보)
								 	"DLVRY"
									 ];
						
						for(var inx=0; inx < hideItems.length; inx++){
							PAGE.FORM2.hideItem(hideItems[inx]);
						}

						for(var inx=0; inx < showItems.length; inx++){
							PAGE.FORM2.showItem(showItems[inx]);
						}

						PAGE.FORM2.hideItem("btnPlcFind");	// 정책선택 버튼
						PAGE.FORM2.showItem("btnPlcFind2");	// 다시선택 버튼
						PAGE.FORM2.showItem("btnCheckSn");	// 재고확인 버튼

						// 메뉴 숨김/보임 설정 END
						//---------------------------------------------------------------------------------------------------------

						//---------------------------------------------------------------------------------------------------------
						// 항목별 Disable/Enable 처리 START

						var enableItems = new Array();

						enableItems.push(
										// 신청서 정보
										"reqBuyType","operType","pstate","requestStateCode","reqInDay","shopNm",
										"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnRecSarch","btnFaxSarch","btnPaper",
										// 판매 정보
										"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","salePlcyCd","salePlcyNm","btnPlcFind","btnPlcFind2","prdtCode","hndstAmt","modelInstallment","modelDiscount2","maxDiscount3","modelDiscount3","dcAmt","addDcAmt","realMdlInstamt","reqModelColor","reqPhoneSn","btnCheckSn","reqUsimSn","additionName","btnRateFind","phonePaymentLabel","phonePayment","lessData","reqWireType1","reqWireType2","reqWireType3","usimPayMthdCd","joinPayMthdCd","spcCode","requestMemo",
										// 고객 정보
										"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","selfInqryAgrmYnLabel","selfInqryAgrmYn","selfCertType","selfIssuExprDt","selfIssuNum","cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl","cstmrTelFn","cstmrTelMn","cstmrTelRn","cstmrMobileFn","cstmrMobileMn","cstmrMobileRn",
										// 배송 정보
										"dlvryName","cloneBase","dlvryTelFn","dlvryTelMn","dlvryTelRn","dlvryMobileFn","dlvryMobileMn","dlvryMobileRn","dlvryAddr","dlvryPost","dlvryAddrDtl","btnDlvryPostFind","dlvryMemo",
										// 외국인 정보
										"cstmrForeignerNation","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2",
										// 사업자 정보
										"cstmrPrivateCname","cstmrJuridicalCname","cstmrPrivateNumber1","cstmrPrivateNumber2","cstmrPrivateNumber3","cstmrJuridicalNumber1","cstmrJuridicalNumber2","cstmrJuridicalNumber3","cstmrJuridicalRrn1","cstmrJuridicalRrn2",
										// 요금납부 정보
										"reqPayType",
										// 카드 정보
										"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2",
										// 은행 정보
										"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2",
										// 신규가입 정보
										"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3","reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
										// 번호이동 정보
										"moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","moveAuthNumber",
										// 기기변경 정보
										"dvcChgType","dvcChgRsnCd","dvcChgRsnDtlCd",
										// 법정대리인 정보
										"minorAgentName","minorAgentRelation","minorAgentRrn1","minorAgentRrn2","minorAgentTelFn","minorAgentTelMn","minorAgentTelRn","minorSelfInqryAgrmYn","minorSelfCertType","minorSelfIssuExprDt","minorSelfIssuNum",
										// 약관동의 정보
										"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriTrustFlag","clauseConfidenceFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd"
										);
							
						// Enable 처리
						for(var inx=0; inx < enableItems.length; inx++){
							PAGE.FORM2.enableItem(enableItems[inx]);
						}
							
						// 항목별 Disable/Enable 처리 END
						//---------------------------------------------------------------------------------------------------------

						//---------------------------------------------------------------------------------------------------------
						// 화면 구성 START

						// 팩스 OR 스캔 구성(신청서정보-서식지)
						form2Change(PAGE.FORM2, 0, "faxyn");

						// 신청유형에 따른 화면 구성(신규가입/번호이동정보/기기변경정보)
						form2Change(PAGE.FORM2, 0, "operType");

						// 고객구분에 따른 화면 구성(내국인/미성년/외국인/사업자)
						form2Change(PAGE.FORM2, 0, "cstmrType");

						// 구매유형에 따른 화면 구성
						form2Change(PAGE.FORM2, 0, "reqBuyType");

						// 단말모델색상
						form2Change(PAGE.FORM2, 0, "reqModelColor");

						// 화면 구성 END
						//---------------------------------------------------------------------------------------------------------

						//---------------------------------------------------------------------------------------------------------
						// 추가 값 설정 START

						// 신청일자
						PAGE.FORM2.setItemValue("reqInDayStr", rowData.reqInDay);
						form2Change(PAGE.FORM2, 0, "reqInDay");
						
						//  기변상세사유
						if(!rowData.dvcChgRsnDtlCd){
							PAGE.FORM2.setItemValue("dvcChgRsnDtlCd", "");
						}
						else {
							form2Change(PAGE.FORM2, 0, "dvcChgRsnCd");
							PAGE.FORM2.setItemValue("dvcChgRsnDtlCd", rowData.dvcChgRsnDtlCd);
						}

						// 요금납부정보
						// 서비스타입에 따라 요금납부정보 콤보 리스트가 달라짐
						form2Change(PAGE.FORM2, 0, "serviceType");
						PAGE.FORM2.setItemValue("reqPayType", rowData.reqPayType);
						form2Change(PAGE.FORM2, 0, "reqPayType");


						// 구매유형/신청유형/모집경로 에 따라 단말모델/요금제 값이 자동변경되기 때문에
						// 이곳에서 재설정 필요
						PAGE.FORM2.setItemValue("reqBuyType", rowData.reqBuyType);			// 구매유형
						PAGE.FORM2.setItemValue("operType", rowData.operType);				// 신규유형
						PAGE.FORM2.setItemValue("onOffType", rowData.onOffType);			// 모집경로
						PAGE.FORM2.setItemValue("mnfctId", rowData.mnfctId);				// 제조사
						PAGE.FORM2.setItemValue("prdtId", rowData.prdtId);					// 단말모델
						PAGE.FORM2.setItemValue("socCode", rowData.socCode);				// 요금제

						// 모델색상
						form2Change(PAGE.FORM2, 0, "prdtId");
						PAGE.FORM2.setItemValue("reqModelColor", rowData.reqModelColor);	// 단말모델색상
						
						if(rowData.reqBuyType == "UU"){
							PAGE.FORM2.setItemValue("prdtCode", 	rowData.reqUsimName);			// USIM모델코드
						}
						else {
							PAGE.FORM2.setItemValue("prdtCode", 	rowData.reqModelName);			// USIM모델코드
						}

						// 단말기일련번호
						PAGE.FORM2.setItemValue("reqPhoneSn", rowData.reqPhoneSn);			// reqPhoneSn
						
						PAGE.FORM2.setItemValue("checkSn", "Y");
						// 온라인에서 받은 데이터를 수정할 수 있도록 하기 위해 이부분은 제거
						if( rowData.reqBuyType == "MM"){
							if(!rowData.reqPhoneSn || rowData.reqPhoneSn == '') {
								PAGE.FORM2.setItemValue("checkSn", "N");
							}
						}
						

						var disableItems;

						disableItems = new Array();
						
						// 접수 = 00:접수대기,01:접수,02:해피콜,09:배송대기,10:배송중,20:개통대기,21:개통완료
						if(rowData.requestStateCode == "21"){
							disableItems.push(
											// 신청서 정보
											"reqBuyType","operType","pstate","requestStateCode","reqInDay","shopNm",
											"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnFaxSarch",
											// 판매 정보
											"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","salePlcyCd","salePlcyNm","btnPlcFind","btnPlcFind2","prdtCode","hndstAmt","modelInstallment","modelDiscount2","maxDiscount3","modelDiscount3","dcAmt","addDcAmt","realMdlInstamt","reqModelColor","reqPhoneSn","btnCheckSn","reqUsimSn","additionName","btnRateFind","phonePaymentLabel","phonePayment","lessData","reqWireType1","reqWireType2","reqWireType3","usimPayMthdCd","joinPayMthdCd","spcCode","requestMemo",
											// 고객 정보
											"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl","cstmrTelFn","cstmrTelMn","cstmrTelRn","cstmrMobileFn","cstmrMobileMn","cstmrMobileRn",
											// 배송 정보
											"dlvryName","cloneBase","dlvryTelFn","dlvryTelMn","dlvryTelRn","dlvryMobileFn","dlvryMobileMn","dlvryMobileRn","dlvryAddr","dlvryPost","dlvryAddrDtl","btnDlvryPostFind","dlvryMemo",
											// 외국인 정보
											"cstmrForeignerNation","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2",
											// 사업자 정보
											"cstmrPrivateCname","cstmrJuridicalCname","cstmrPrivateNumber1","cstmrPrivateNumber2","cstmrPrivateNumber3","cstmrJuridicalNumber1","cstmrJuridicalNumber2","cstmrJuridicalNumber3","cstmrJuridicalRrn1","cstmrJuridicalRrn2",
											// 요금납부 정보
											"reqPayType",
											// 카드 정보
											"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2",
											// 은행 정보
											"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2",
											// 신규가입 정보
											"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3","reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
											// 번호이동 정보
											"moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","moveAuthNumber",
											// 기기변경 정보
											"dvcChgType","dvcChgRsnCd","dvcChgRsnDtlCd",
											// 법정대리인 정보
											"minorAgentName","minorAgentRelation","minorAgentRrn1","minorAgentRrn2","minorAgentTelFn","minorAgentTelMn","minorAgentTelRn","minorSelfInqryAgrmYn","minorSelfCertType","minorSelfIssuExprDt","minorSelfIssuNum",
											// 약관동의 정보
											"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriTrustFlag","clauseConfidenceFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd"
											);
						}
						else {
							// 모집유형 = 0:온라인,1:오프라인,2:홈쇼핑(TM)
							// 온라인/홈쇼핑 경우
							if(rowData.onOffType != "1"){
								if(rowData.requestStateCode == "00"){
										disableItems.push(
														// 신청서 정보
														"reqBuyType","operType","reqInDay",
														"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnFaxSarch",
														// 판매 정보
														// "reqModelColor", 온라인에서 신청한 색상을 변경가능하게 함 by 2015-09-10
														"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","salePlcyCd","salePlcyNm","btnPlcFind","btnPlcFind2","prdtCode","hndstAmt","modelInstallment","modelDiscount2","maxDiscount3","modelDiscount3","dcAmt","addDcAmt","realMdlInstamt","additionName","btnRateFind","phonePaymentLabel","phonePayment","lessData","reqWireType1","reqWireType2","reqWireType3","usimPayMthdCd","joinPayMthdCd",
														// 고객 정보
														"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl",
														// 외국인 정보
														"cstmrForeignerNation","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2",
														// 사업자 정보
														"cstmrPrivateCname","cstmrJuridicalCname","cstmrPrivateNumber1","cstmrPrivateNumber2","cstmrPrivateNumber3","cstmrJuridicalNumber1","cstmrJuridicalNumber2","cstmrJuridicalNumber3","cstmrJuridicalRrn1","cstmrJuridicalRrn2",
														// 요금납부 정보
														"reqPayType",
														// 카드 정보
														"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2",
														// 은행 정보
														"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2",
														// 신규가입 정보
														"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3","reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
														// 번호이동 정보
														//수정할 수 있도록 Disable 제외
														//"moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","moveAuthNumber",
														// 기기변경 정보
														"dvcChgType","dvcChgRsnCd","dvcChgRsnDtlCd",
														// 법정대리인 정보
														//법정대리인을 부->모, 모->부로 변경할 수도 있기에 Disable 제외
														//"minorAgentName","minorAgentRelation","minorAgentRrn1","minorAgentRrn2","minorAgentTelFn","minorAgentTelMn","minorAgentTelRn","minorSelfInqryAgrmYn","minorSelfCertType","minorSelfIssuExprDt","minorSelfIssuNum",
														// 약관동의 정보
														"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriTrustFlag","clauseConfidenceFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd"
														);
								} else if(rowData.requestStateCode == "01"){
										disableItems.push(
			                                            // 신청서 정보
			                                            "reqBuyType","operType","reqInDay",
			                                            "onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnFaxSarch",
			                                            // 판매 정보
			                                            // "reqModelColor", 온라인에서 신청한 색상을 변경가능하게 함 by 2015-11-16
// 			                                            "mnfctId","prdtId","socCode","agrmTrm","instNom","salePlcyCd","salePlcyNm","btnPlcFind","btnPlcFind2","prdtCode","modelInstallment","maxDiscount3","modelDiscount3","realMdlInstamt","reqModelColor","additionName","btnRateFind","phonePaymentLabel","phonePayment","lessData","reqWireType1","reqWireType2","reqWireType3","usimPayMthdCd","joinPayMthdCd",
			                                            "mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","salePlcyCd","salePlcyNm","btnPlcFind","btnPlcFind2","prdtCode","hndstAmt","modelInstallment","modelDiscount2","maxDiscount3","modelDiscount3","dcAmt","addDcAmt","realMdlInstamt","additionName","btnRateFind","phonePaymentLabel","phonePayment","lessData","reqWireType1","reqWireType2","reqWireType3","usimPayMthdCd","joinPayMthdCd",
			                                            // 고객 정보
			                                            "cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl",
			                                            // 외국인 정보
			                                            "cstmrForeignerNation","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2",
			                                            // 사업자 정보
			                                            "cstmrPrivateCname","cstmrJuridicalCname","cstmrPrivateNumber1","cstmrPrivateNumber2","cstmrPrivateNumber3","cstmrJuridicalNumber1","cstmrJuridicalNumber2","cstmrJuridicalNumber3","cstmrJuridicalRrn1","cstmrJuridicalRrn2",
			                                            // 요금납부 정보
			                                            "reqPayType",
			                                            // 카드 정보
			                                            "reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2",
			                                            // 은행 정보
			                                            "reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2",
			                                            // 신규가입 정보
			                                            "reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3","reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
			                                            // 번호이동 정보
			                                            "moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","moveAuthNumber",
			                                            // 기기변경 정보
			                                            "dvcChgType","dvcChgRsnCd","dvcChgRsnDtlCd",
			                                            // 법정대리인 정보
			                                            "minorAgentName","minorAgentRelation","minorAgentRrn1","minorAgentRrn2","minorAgentTelFn","minorAgentTelMn","minorAgentTelRn","minorSelfInqryAgrmYn","minorSelfCertType","minorSelfIssuExprDt","minorSelfIssuNum",
			                                            // 약관동의 정보
			                                            "clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriTrustFlag","clauseConfidenceFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd"
	                                            );
								
								} else {
										disableItems.push(
														// 신청서 정보
														"reqBuyType","operType","reqInDay",
														"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnFaxSarch",
														// 판매 정보
														"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","salePlcyCd","salePlcyNm","btnPlcFind","btnPlcFind2","prdtCode","hndstAmt","modelInstallment","modelDiscount2","maxDiscount3","modelDiscount3","dcAmt","addDcAmt","realMdlInstamt","reqModelColor","additionName","btnRateFind","phonePaymentLabel","phonePayment","lessData","reqWireType1","reqWireType2","reqWireType3","usimPayMthdCd","joinPayMthdCd",
														// 고객 정보
														"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl",
														// 외국인 정보
														"cstmrForeignerNation","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2",
														// 사업자 정보
														"cstmrPrivateCname","cstmrJuridicalCname","cstmrPrivateNumber1","cstmrPrivateNumber2","cstmrPrivateNumber3","cstmrJuridicalNumber1","cstmrJuridicalNumber2","cstmrJuridicalNumber3","cstmrJuridicalRrn1","cstmrJuridicalRrn2",
														// 요금납부 정보
														"reqPayType",
														// 카드 정보
														"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2",
														// 은행 정보
														"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2",
														// 신규가입 정보
														"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3","reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
														// 번호이동 정보
														"moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","moveAuthNumber",
														// 기기변경 정보
														"dvcChgType","dvcChgRsnCd","dvcChgRsnDtlCd",
														// 법정대리인 정보
														"minorAgentName","minorAgentRelation","minorAgentRrn1","minorAgentRrn2","minorAgentTelFn","minorAgentTelMn","minorAgentTelRn","minorSelfInqryAgrmYn","minorSelfCertType","minorSelfIssuExprDt","minorSelfIssuNum",
														// 약관동의 정보
														"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriTrustFlag","clauseConfidenceFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd"
														);
								}
							}
							else {
								disableItems.push(
														// 신청서 정보
														"cstmrMail2","reqInDay","onlineAuthType","onlineAuthInfo","onOffType",
														"resNo","managerCode","clausePriCollectFlag",
														// 판매 정보
														"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","prdtCode",
														// 약관동의 정보
														"clausePriOfferFlag","clauseEssCollectFlag","clausePriTrustFlag","clauseConfidenceFlag"
												);
							}
						}

						// Disable 처리
						for(var inx=0; inx < disableItems.length; inx++){
							PAGE.FORM2.disableItem(disableItems[inx]);
						}

						
						//
						//---------------------------------------------------------------------------------------------------------
						console.log("date=" + new Date());
						
						mvno.ui.popupWindowById("FORM2", "상세화면", 870, 780, {
							onClose: function(win) {
								win.closeForce();
							},maximized:true}
			 			);
					});

					break;	//Row 더블클릭시 End
			}//Switch End
		}// onButtonClick
	},
	FORM2 : {
		items : [
			{type:"block",name:"BODY", list:[
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:70, blockOffset:0}
				,{type: "fieldset", label: "신청서정보", name:"REQUEST", inputWidth:910, list:[
					,{type:"block", list:[
						 {type:"hidden", name:"serviceType"}
						,{type:"select", label:"구매유형", name:"reqBuyType", width:100, required:true, userdata:{lov:"RCP0009"}}
						,{type:"newcolumn"}
						,{type:"select", label:"신청유형", name:"operType", width:100, offsetLeft:20, required:true, userdata:{lov:"RCP0015"}, validate:'NotEmpty'}
						,{type:"newcolumn"}
						,{type:"select", label:"신청서상태", name:"pstate", width:100, offsetLeft:20, userdata:{lov:"RCP0005"}, validate:'NotEmpty'}
						,{type:"newcolumn"}
						,{type:"select", label:"진행상태", name:"requestStateCode", width:100, offsetLeft:20, userdata:{lov:"RCP0006"}, validate:'NotEmpty'}
					]}
					,{type:"block", list:[
						 {type:"calendar", label:"신청일자", name:"reqInDay", width:100, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d"}
						,{type:"hidden", name:"reqInDayStr"}
						,{type:"newcolumn"}
						,{type:"select", label:"모집경로", name:"onOffType", width:100, offsetLeft:20, userdata:{lov:"RCP0007"}}
						,{type:"newcolumn"}
						,{type:"input", label:"판매점", name:"shopNm", width:295, offsetLeft:20, maxLength: 30}
					]}
					,{type:"block", list:[
						 {type:"select", label:"본인인증방식", name:"onlineAuthType", width:150, userdata:{lov:"RCP0036"}, validate:'NotEmpty', value:""}
						,{type:"newcolumn"}
						,{type:"input", label:"본인인증정보", name:"onlineAuthInfo", width:150, offsetLeft:20, readonly: false, value:""}
						,{type:"newcolumn"}
						,{type:"input", label:"매니저코드", name:"managerCode", width:130, offsetLeft:20, readonly: false, value:""}
					]}
					,{type:"block", list:[
						 {type:"input", label:"예약번호", name:"resNo", width:150, readonly: false, value:""}
						,{type:"newcolumn"}
						,{type:"select", label:"서식지", name:"faxyn", width:150, offsetLeft:20, userdata:{lov:"RCP0052"}, value:""}	//공통코드 만들어야함
						,{type:"newcolumn"}
						,{type:"block", name:"faxnum", offsetLeft:20, list:[
							 {type:"select", label:"팩스번호", name:"faxnum1", width:60, userdata:{lov:"RCP0034"}}
							,{type:"newcolumn"}
							,{type:"input", label:"-", name:"faxnum2", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
							,{type:"newcolumn"}
							,{type:"input", label:"-", name:"faxnum3", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
						]}
						,{type:"newcolumn"}
						,{type:"button", value:"스캔하기", name: "btnRegPaper", offsetLeft:10}
						,{type:"newcolumn"}
						,{type:"button", value:"스캔검색", name: "btnScnSarch", offsetLeft:10}
// 						,{type:"newcolumn"}
// 						,{type:"button", value:"녹취파일등록", name: "btnRecSarch", offsetLeft:10}
						,{type:"newcolumn"}
						,{type:"button", value:"팩스검색", name: "btnFaxSarch", offsetLeft:10}
						,{type:"newcolumn"}
						/* ,{type:"button", value:"서식지 보기", name: "btnPaper", offsetLeft:10} */
						,{type:"hidden", name:"scanId"} // 등록시에 새로 생성하되, 스캔검색을 하게 되면 검색창에서 넘오는 값으로 셋팅 되어야 한다.
						,{type:"hidden", name:"shopCd"} //판매점 ID : 스캔검색에서 전달 받는다.
						,{type:"hidden", name:"scanType"}//서식지 등록과 상세정보 구분
						,{type:"hidden", name:"btnType"}
						,{type:"hidden", name:"scanIdBySearch"}
						,{type:"hidden", name:"shopCdBySearch"}
						,{type:"hidden", name:"faxBySearch"}
					]}
				]}	//신청서정보 끝

				,{type: "fieldset", label: "판매정보", inputWidth:910, name:"SALE", list:[
					,{type:"block", list:[
						 {type:"select", label:"제조사", name:"mnfctId", labelWidth:70, width:130}
						,{type:"newcolumn"}
						,{type:"select", label:"단말모델", name:"prdtId", labelWidth:60, width:200, offsetLeft:20}
						,{type:"newcolumn"}
						,{type:"input", label:"단말코드", name:"prdtCode", labelWidth:60, width:100, offsetLeft:20, readonly:true }
						,{type:"newcolumn"}
						,{type:"select", label:"단말색상", name:"reqModelColor", labelWidth:60, width:100, offsetLeft:20}
					]}
					,{type:"block", list:[
						 {type:"select", label:"요금제", name:"socCode", labelWidth:70, width:220, validate:'NotEmpty'}
						,{type:"newcolumn"}
						,{type :"select", label:"할인유형", name:"sprtTp", labelWidth:60, width:90, offsetLeft:20, userdata:{lov:"CMN0058"}}
						,{type:"newcolumn"}
						,{type :"select", label:"약정기간", name :"agrmTrm", labelWidth:60, width:90, offsetLeft:20, userdata:{lov:"CMN0053"}}
						,{type:"newcolumn"}
						,{type:"select", label:"할부기간", name:"instNom", labelWidth:60, width:90, offsetLeft:20, userdata:{lov:"RCP0047"}, validate:'NotEmpty'}
					]}
					,{type:"block", list:[
						 {type:"input", label:"판매정책", name:"salePlcyCd", width:120, value:lbl, readonly:true}
						,{type:"newcolumn"}
						,{type:"input", label:"", name:"salePlcyNm", width:300, offsetLeft:0, labelAlign:"right", readonly:true }
						,{type:"hidden", name:"cntpntShopId"}
						,{type:"hidden", name:"modelId"}
						,{type:"hidden", name:"recycleYn"}
						,{type:"hidden", name:"modelDiscount1"}
						//,{type:"hidden", name:"modelDiscount2"}
						,{type:"hidden", name:"joinPrice"}
						,{type:"hidden", name:"usimPrice"}
						,{type:"hidden", name:"modelPrice"}
						,{type:"hidden", name:"modelPriceVat"}
						,{type:"hidden", name:"reqModelName"}
						,{type:"hidden", name:"reqUsimName"}
						,{type:"newcolumn"}
						,{type:"button", value:"정책선택", name: "btnPlcFind"}
						,{type:"button", value:"다시선택", name: "btnPlcFind2"}
						,{type:"newcolumn"}
						,{type:"input", label:"출고가", name:"hndstAmt", labelWidth:40, width:80, offsetLeft:20, readonly:true, numberFormat:"0,000,000,000", validate:"ValidInteger", value:""}
					]}
					,{type:"block", list:[
						 {type:"input", label:"할부원금", name:"modelInstallment", labelWidth:45, width:80, readonly:true, numberFormat:"0,000,000,000", validate:"ValidInteger", value:""}
						,{type:"newcolumn"}
						,{type:"input", label:"공시지원금", name:"modelDiscount2", labelWidth:55, width:70, offsetLeft:10, readonly:true, numberFormat:"0,000,000,000", validate:"ValidInteger", value:""}
						,{type:"newcolumn"}
						,{type:"input", label:"추가지원금(MAX)", name:"maxDiscount3", labelWidth:90, width:70, offsetLeft:10, readonly: true, numberFormat:"0,000,000,000", validate:"ValidInteger", value:""}
						,{type:"newcolumn"}
						,{type:"input", label:"(적용)", name:"modelDiscount3", labelWidth:30, width:70, offsetLeft:10, numberFormat:"0,000,000,000", validate:"ValidInteger", maxLength: 10, value:""}
						,{type:"newcolumn"}
						,{type:"input", label:"기본할인금액", name:"dcAmt", labelWidth:70, width:70, offsetLeft:10, readonly: true, numberFormat:"0,000,000,000", validate:"ValidInteger", maxLength:10, value:""}
						,{type:"newcolumn"}
						,{type:"input", label:"추가할인금액", name:"addDcAmt", labelWidth:70, width:70, offsetLeft:10, readonly: true, numberFormat:"0,000,000,000", validate:"ValidInteger", maxLength:10, value:""}
						,{type:"hidden", label:"실구매가", name:"realMdlInstamt", labelWidth:45, width:85, offsetLeft:10, numberFormat:"0,000,000,000", validate:"ValidInteger", maxLength:10, value:""}
					]}
					,{type:"block", list:[
						 {type:"input", label:"단말기일련번호", name:"reqPhoneSn", labelWidth:80, width:120, maxLength:30}
						,{type:"newcolumn"}
						,{type:"button", value: "재고확인", name:"btnCheckSn"}
						,{type:"hidden", name:"checkSn", value:"N"}
						,{type:"newcolumn"}
						,{type:"input", label:"유심일련번호", name:"reqUsimSn", width:150, offsetLeft:15, maxLength:20, value:""}
						,{type:"newcolumn"}
						,{type:"select", label:"USIM구매", name:"usimPayMthdCd", labelWidth:80, width:80, offsetLeft:10, labelAlign:"right", required:true, validate:'NotEmpty', userdata:{lov:"RCP0056"}, value:""}
						,{type:"newcolumn"}
						,{type:"select", label:"가입비", name:"joinPayMthdCd", labelWidth:60, width:100, offsetLeft:10, labelAlign:"right", required:true, validate:'NotEmpty', userdata:{lov:"RCP0048"}, value:""}
					]}
					,{type:"block", list:[
						 {type:"input", label:"부가서비스명", name:"additionName", width:705, readonly:true}
						,{type:"hidden", label:"부가서비스", name:"additionKey"}
						,{type:"hidden", label:"부가서비스가격", name:"additionPrice"}
						,{type:"newcolumn"}
						,{type:"button", value: "부가서비스", name:"btnRateFind"}
					]}
					,{type:"block", list:[
						 {type:"label", label:"휴대폰결제", name:"phonePaymentLabel", position:"label-right", list:[
							 {type:"settings", labelWidth:35, offsetLeft:5, position:"label-right"}
							,{type:"radio", name:"phonePayment", value:"Y", label:"이용", checked:true}
							,{type:"newcolumn"}
							,{type:"radio", name:"phonePayment", value:"N", label:"차단"}
						]}
						,{type:"newcolumn"}
						,{type:"label", label:"무선데이터", name:"lessData", labelWidth:74, offsetLeft:93}
						,{type:"newcolumn"}
						,{type:"checkbox", label:"이용", name:"reqWireType1", labelWidth:35, width:4, position:"label-right", labelAlign:"left", checked:false}
						,{type:"newcolumn"}
						,{type:"checkbox", label:"차단", name:"reqWireType2", labelWidth:35, width:4, position:"label-right", labelAlign:"left", checked:false}
						,{type:"newcolumn"}
						,{type:"checkbox", label:"데이터로밍차단", name:"reqWireType3", labelWidth:80, width:4, position:"label-right", labelAlign:"left", checked:false}
					]}
					,{type:"block", list:[
						 {type:"input", label:"특별판매코드", name:"spcCode", width:200, maxLength:200, validate:'ValidAplhaNumeric'}
						,{type:"newcolumn"}
						,{type:"input", label:"메모사항", name:"requestMemo", width:490, offsetLeft:20, maxLength:200}
					]}
				]}//판매정보 끝

				,{type: "fieldset", label: "고객정보", inputWidth:910, name:"REQINFO", list:[
					,{type:"block", list:[
						 {type:"select", label:"고객구분", name:"cstmrType", width:130, userdata:{lov:"RCP0001"},validate:'NotEmpty'}
						,{type:"newcolumn"}
						,{type:"input", label:"고객명", name:"cstmrName", width:230, offsetLeft:10, maxLength:60, validate:'NotEmpty'}
						,{type:"newcolumn"}
						,{type:"input", label:"주민번호", name:"cstmrNativeRrn1", width:80, offsetLeft:40, maxLength:6, validate:'ValidNumeric'}
						,{type:"newcolumn"}
						,{type:"input", label:"-", name:"cstmrNativeRrn2", labelWidth:15, width:80, labelAlign:"center", maxLength:7}
					]}
					,{type:"block", list:[
						 {type:"label", label:"본인조회동의", name:"selfInqryAgrmYnLabel", labelWidth:80}
						,{type:"newcolumn"}
						,{type:"checkbox", label:"동의", name:"selfInqryAgrmYn", width:130, value:"Y", position:"label-right"}
						,{type:"newcolumn"}
						,{type:"select", label:"본인인증방식", name:"selfCertType", width:130, offsetLeft:40, userdata:{lov:"RCP0045"}}
						,{type:"newcolumn"}
						,{type:"calendar", label:"발급일자", name:"selfIssuExprDt", width:130, offsetLeft:10, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d"}
						,{type:"newcolumn"}
						,{type:"input", label:"발급번호", name:"selfIssuNum", labelWidth:80, width:120, offsetLeft:10, maxLength:50}
					]}
					,{type:"block", list:[
						 {type:"input", label:"메일", name:"cstmrMail1", width:111, maxLength:60}
						,{type:"newcolumn"}
						,{type:"input", label:"@",name:"cstmrMail2", labelWidth:10, width:110, offsetLeft:0, labelAlign:"center", maxLength:39}
						,{type:"newcolumn"}
						,{type:"select", name:"cstmrMail3", width:100, offsetLeft:0, userdata:{lov:"RCP0042"}}
						,{type:"newcolumn"}
						,{type:"label", label:"수신여부", name:"cstmrMailReceiveFlagLabel", labelWidth:70, offsetLeft:10}
						,{type:"newcolumn"}
						,{type:"checkbox", label:"동의", name:"cstmrMailReceiveFlag", width:130, offsetLeft:10, value:"Y", position:"label-right"}
						,{type:"newcolumn"}
						,{type:"select", label:"명세서수신유형", name:"cstmrBillSendCode", labelWidth:80, width:120, offsetLeft:44, userdata:{lov:"RCP0028"}, validate:'NotEmpty'}
					]}
					,{type:"block", list:[
						 {type:"input", label:"주소", name:"cstmrPost", width:75, readonly:true, maxLength:6, validate:'NotEmpty'}
						,{type:"newcolumn"}
						,{type:"button", value:"우편번호찾기", name:"btnCstmrPostFind"}
						,{type:"newcolumn"}
						,{type:"input", label:"", name:"cstmrAddr", labelWidth:0, width:605, readonly:true, maxLength:83}
						,{type:"newcolumn"}
					]},
					,{type:"block", list:[
						 {type:"input", label:"상세주소", name:"cstmrAddrDtl", width:776, maxLength:83, validate:'NotEmpty'}
					]}
					,{type:"block", list:[
						 {type:"select", label:"전화번호", name:"cstmrTelFn", width:60, userdata:{lov:"RCP0034"}}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=TEL"
						,{type:"newcolumn"}
						,{type:"input", label:"-", name:"cstmrTelMn", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
						,{type:"newcolumn"}
						,{type:"input", label:"-", name:"cstmrTelRn", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
						,{type:"newcolumn"}
						,{type:"select", label:"휴대폰", name:"cstmrMobileFn", labelWidth:50, width:60, offsetLeft:80, userdata:{lov:"RCP0035"}}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=HPN"
						,{type:"newcolumn"}
						,{type:"input", label:"-", name:"cstmrMobileMn", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
						,{type:"newcolumn"}
						,{type:"input", label:"-", name:"cstmrMobileRn", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
						,{type:"newcolumn"}
						,{type:"input", label:"제주항공회원번호", name:"cstmrJejuId", labelWidth: 90, width:132, offsetLeft:80, maxLength:10, validate:'ValidAplhaNumeric'}
					]}
				]}
				,{type:"fieldset", label:"배송정보", inputWidth:910, name:"DLVRY", list:[
					 {type:"settings"}
					,{type:"block", list:[
							 {type:"input", width:60, name:"dlvryName", label:"받으시는 분" , maxLength:20}
							,{type:"newcolumn"}
							,{type:"checkbox", width:40, label:"고객상동",name:"cloneBase", position:"label-right", labelAlign:"left", labelWidth:60,checked:false}
							,{type:"newcolumn"}
							,{type:"select", width:60, label:"전화번호", name:"dlvryTelFn",offsetLeft:10, userdata:{lov:"RCP0034"}}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=TEL"
							,{type:"newcolumn"}
							,{type:"input", width:40, label:"",name:"dlvryTelMn",maxLength:4}
							,{type:"newcolumn"}
							,{type:"input", width:40, label:"",name:"dlvryTelRn",maxLength:4}
							,{type:"newcolumn"}
							,{type:"select", width:60, label:"휴대폰",name:"dlvryMobileFn",offsetLeft:10,labelAlign:"right", labelWidth:97, userdata:{lov:"RCP0035"}}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=HPN"
							,{type:"newcolumn"}
							,{type:"input", width:40, label:"",name:"dlvryMobileMn", maxLength:4}
							,{type:"newcolumn"}
							,{type:"input", width:40, label:"",name:"dlvryMobileRn", maxLength:4}
		   			]}
					,{type:"block", list:[
							{type:"input",  label:"주소",name:"dlvryAddr",width:585,readonly:true,maxLength:83}
							,{type:"newcolumn"}
							,{type:"input",  label:"우편번호",name:"dlvryPost",labelWidth:50, labelAlign:"right",width:75,readonly:true,maxLength:6}
					]}
					,{type:"block", list:[
							{type:"input", label:"상세주소",name:"dlvryAddrDtl",width:645,maxLength:83}
							,{type:"newcolumn"}
							,{type:"button", value: "주소찾기", name :"btnDlvryPostFind"}
					]}
					,{type:"block", list:[
							{type:"input", width:590, label:"요청사항",name:"dlvryMemo", maxLength:200}
					]}
					,{type:"block", list:[
							{type:"label",readonly:true}
					]}
				]}	//배송정보 끝
				,{type: "fieldset", label: "외국인정보", name:"FOREIGNER", inputWidth:910, list:[
					,{type:"block", list:[
						{type:"input", width:130, label:"국적",name:"cstmrForeignerNation", maxLength:33}
						,{type:"newcolumn"}
						,{type:"input", width:130, label:"여권번호",name:"cstmrForeignerPn", maxLength:83, offsetLeft:10}
						,{type:"newcolumn"}
						,{type:"calendar", width:100, label:"체류기간", labelWidth:80, name:"cstmrForeignerSdate",maxLength:10, readonly:true, offsetLeft:10}
						,{type:"newcolumn"}
						,{type:"calendar", label:"~",name:"cstmrForeignerEdate", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100,maxLength:10, readonly:true}
					]}
					,{type:"block", list:[
						{type:"input", width:130, label:"사회보장번호", name:"cstmrForeignerDod", maxLength:83, validate:'ValidAplhaNumeric'}
						,{type:"newcolumn"}
						,{type:"calendar", width:80, label:"생년월일", name:"cstmrForeignerBirth", maxLength:10, readonly:true, offsetLeft:10}
						,{type:"newcolumn"}
						,{type:"input", width:60, label:"외국인등록번호", labelWidth:80, offsetLeft:60, name:"cstmrForeignerRrn1", maxLength:6,validate:'ValidNumeric'}
						,{type:"newcolumn"}
						,{type:"input", width:80, label:"-",name:"cstmrForeignerRrn2",labelAlign:"center",labelWidth:15,maxLength:7, offsetLeft:5}
					]}
				]}

				,{type: "fieldset", label: "사업자정보", name:"BUSINESS", inputWidth:910, list:[
					{type:"settings"}
					,{type:"input", width:130, label:"상호명",name:"cstmrPrivateCname", maxLength:20}
					,{type:"input", width:130, label:"법인명",name:"cstmrJuridicalCname", maxLength:20, offsetLeft: 10}
					,{type:"newcolumn"}
					,{type:"input", width:40, label:"사업자번호",name:"cstmrPrivateNumber1", offsetLeft:10, maxLength:3, validate:'ValidNumeric'}
					,{type:"newcolumn"}
					,{type:"input", width:30, label:" -",name:"cstmrPrivateNumber2", labelAlign:"center", labelWidth:5, maxLength:2,validate:'ValidNumeric'}
					,{type:"newcolumn"}
					,{type:"input", width:40, label:" -",name:"cstmrPrivateNumber3", labelAlign:"center", labelWidth:5, maxLength:5, validate:'ValidNumeric'}
					,{type:"newcolumn"}
					,{type:"input", width:40, label:"사업자번호",name:"cstmrJuridicalNumber1", offsetLeft:10, maxLength:3, validate:'ValidNumeric'}
					,{type:"newcolumn"}
					,{type:"input", width:30, label:" -",name:"cstmrJuridicalNumber2", labelAlign:"center", labelWidth:5, maxLength:2,validate:'ValidNumeric'}
					,{type:"newcolumn"}
					,{type:"input", width:40, label:" -",name:"cstmrJuridicalNumber3", labelAlign:"center", labelWidth:5, maxLength:5, validate:'ValidNumeric'}
					,{type:"newcolumn"}
					,{type:"input", width:60, label:"법인번호",name:"cstmrJuridicalRrn1", offsetLeft:10, maxLength:6, validate:'ValidNumeric'}
					,{type:"newcolumn"}
					,{type:"input", width:80, label:" -",name:"cstmrJuridicalRrn2",labelAlign:"center",labelWidth:5,maxLength:7}
					,{type:"newcolumn"}
				]}

				,{type: "fieldset", label: "요금납부정보", name:"PAYTYPE1", inputWidth:910, list:[
					{type:"settings"}
					,{type:"select", width:130, label:"요금납부방법", name:"reqPayType"}
					,{type:"newcolumn"}

				]}

				,{type: "fieldset", label: "카드정보", name:"CARD", inputWidth:910, list:[
					{type:"block", list:[
						{type:"select", width:103, label:"카드정보",name:"reqCardCompany", userdata:{lov:"RCP0038"}}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=CRD"
						,{type:"newcolumn"}
						,{type:"input", width:35, label:"",name:"reqCardNo1",labelAlign:"center",labelWidth:5,maxLength:4}
						,{type:"newcolumn"}
						,{type:"input", width:35, label:"-",name:"reqCardNo2",labelAlign:"center",labelWidth:5,maxLength:4}
						,{type:"newcolumn"}
						,{type:"input", width:35, label:"-",name:"reqCardNo3",labelAlign:"center",labelWidth:5,maxLength:4}
						,{type:"newcolumn"}
						,{type:"input", width:35, label:"-",name:"reqCardNo4",labelAlign:"center",labelWidth:5,maxLength:4}
						,{type:"newcolumn"}
						,{type:"input",  label:"월",name:"reqCardMm", labelWidth:12, width:20, maxLength:2, offsetLeft:20}
						,{type:"newcolumn"}
						,{type:"input",  label:"년",name:"reqCardYy", labelWidth:12, width:40, maxLength:4, offsetLeft:10}
					]}
					,{type:"block", list:[
						{type:"input", width:130, label:"명의자정보", name:"reqCardName", maxLength:20}
						,{type:"newcolumn"}
						,{type:"checkbox", width:130, label:"타인납부", name:"reqPayOtherFlag", position:"label-left", checked:false, value:"Y", offsetLeft: 10}
						,{type:"newcolumn"}
						,{type:"input", width:60, labelWidth:90, label:"명의자주민번호", name:"reqCardRrn1", offsetLeft: 125, maxLength:6}
						,{type:"newcolumn"}
						,{type:"input", width:80, label:"-",name:"reqCardRrn2",labelAlign:"center",labelWidth:5,maxLength:7}
					]}
				]}

				,{type: "fieldset", label: "은행정보", name:"BANK", inputWidth:910, list:[
					{type:"block", list:[
						{type:"select", width:130, label:"은행정보",name:"reqBank", userdata:{lov:"RCP0037"}}
						,{type:"newcolumn"}
						,{type:"input", width:150, label:"", name:"reqAccountNumber", maxLength:16}

					]},
					{type:"block", list:[
						{type:"input", width:130, label:"예금주", name:"reqAccountName", maxLength:20}
						,{type:"newcolumn"}
						,{type:"checkbox", width:130, label:"타인납부", name:"othersPaymentAg", position:"label-left", checked:false, value:"Y", offsetLeft: 10}
						,{type:"newcolumn"}
						,{type:"input", width:60, labelWidth:90, label:"명의자주민번호", name:"reqAccountRrn1", offsetLeft: 125, maxLength:6}
						,{type:"newcolumn"}
						,{type:"input", width:80, label:"-",name:"reqAccountRrn2",labelAlign:"center",labelWidth:5,maxLength:7}
					]}
				]}

				,{type: "fieldset", label: "신규가입", name:"NEWREQ", inputWidth:910, list:[
					{type:"settings"}
					,{type: "label", label: "가입희망번호", name:"reqWantNumberLabel", labelWidth: 80},
					,{type:"newcolumn"}
					,{type:"input", width:40, label:"1)", name:"reqWantNumber", labelWidth: 10, maxLength:4, validate:'ValidNumeric', offsetLeft: 0}
					,{type:"newcolumn"}
					,{type:"input", width:40, label:"2)", name:"reqWantNumber2", labelWidth: 10, maxLength:4, validate:'ValidNumeric', offsetLeft: 10}
					,{type:"newcolumn"}
					,{type:"input", width:40, label:"3)", name:"reqWantNumber3", labelWidth: 10, maxLength:4, validate:'ValidNumeric', offsetLeft: 10}
					,{type:"newcolumn"}
					,{type:"checkbox",width:10, label:"번호연결서비스",name:"reqGuideFlag", position:"label-left", checked:false, value:"Y", labelWidth: 80, offsetLeft: 170}
					,{type:"newcolumn"}
					,{type:"select", width:60, label:"",name:"reqGuideFn", labelAlign:"right", labelWidth:98, userdata:{lov:"RCP0035"}}
					,{type:"newcolumn"}
					,{type:"input", width:40, label:"-", labelAlign:"center", labelWidth: 10, name:"reqGuideMn", maxLength:4}
					,{type:"newcolumn"}
					,{type:"input", width:40, label:"-", labelAlign:"center", labelWidth: 10, name:"reqGuideRn", maxLength:4}
				]}

				,{type: "fieldset", label: "번호이동정보", inputWidth:910, name:"REQMOVE", list:[
					{type:"settings"}
					,{type:"block", list:[
						{type:"select", width:60, label:"이동번호",name:"moveMobileFn", userdata:{lov:"RCP0035"}}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=HPN"
						,{type:"newcolumn"}
						,{type:"input", width:40, label:"-", labelAlign:"center", labelWidth: 10, name:"moveMobileMn", maxLength:4}
						,{type:"newcolumn"}
						,{type:"input", width:40, label:"-", labelAlign:"center", labelWidth: 10, name:"moveMobileRn", maxLength:4}
						,{type:"newcolumn"}
						,{type:"select", width:130, label:"이동전통신사", name:"moveCompany", labelAlign:"right", offsetLeft:50, userdata:{lov:"RCP0039"}}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=NSC"
						,{type:"newcolumn"}
						,{type:"select", width:130, label:"번호이동전 사용요금", name:"moveThismonthPayType", labelWidth: 110, offsetLeft: 50, userdata:{lov:"RCP0010"}}
					]},
					,{type:"block", list:[
						{type:"select", width:130, label:"휴대폰 할부금",name:"moveAllotmentStat", userdata:{lov:"RCP0011"}}
						,{type:"newcolumn"}
						,{type: "label", label:"미환급액상계", name:"moveRefundAgreeFlagLabel", labelWidth:75, position:"label-right", offsetLeft:97, list:[
							{type:"settings",offsetLeft:5, position:"label-left", labelWidth:35}
							,{type: "radio", name: "moveRefundAgreeFlag", value: "Y", label: "동의"}
							,{type:"newcolumn"}
							,{type: "radio", name: "moveRefundAgreeFlag", value: "N", label: "미동의"}
							,{type:"newcolumn"}
							,{type:"select", width:150, label:"인증유형", labelWidth:70, name:"moveAuthType", userdata:{lov:"RCP0012"}, offsetLeft:43}
							,{type:"newcolumn"}
							,{type:"input", width:50,name:"moveAuthNumber", label:"", maxLength:4}
						]}
					]}
				]}

				,{type: "fieldset", label: "기기변경정보", name:"DVCCHG", inputWidth:910, list:[
					{type:"settings"}
					,{type:"select", width:130, label:"기변유형", name:"dvcChgType", userdata:{lov:"RCP0049"}}
					,{type:"newcolumn"}
					,{type:"select", width:130, label:"기변사유", name:"dvcChgRsnCd", offsetLeft:10,userdata:{lov:"RCP0050"}}
					,{type:"newcolumn"}
					,{type:"select", width:130, label:"기변상세사유", name:"dvcChgRsnDtlCd", offsetLeft:10}
					,{type:"newcolumn"}
				]}

				,{type: "fieldset", label: "법정대리인", name:"MINOR",  inputWidth:910, list:[
					,{type:"block", list:[
						{type:"input", width:80, label:"대리인성명", labelWidth:80, name:"minorAgentName", maxLength:20}
						,{type:"newcolumn"}
						,{type:"select", width:80, label:"관계", labelWidth:40, name:"minorAgentRelation", offsetLeft:20, userdata:{lov:"RCP0040"}}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=AGR"
						,{type:"newcolumn"}
						,{type:"input", width:70, label:"대리인주민번호", labelWidth:80, name:"minorAgentRrn1", offsetLeft:20, maxLength:6}
						,{type:"newcolumn"}
						,{type:"input", width:80, label:"-",name:"minorAgentRrn2",labelAlign:"center",labelWidth:5,maxLength:7}
						,{type:"newcolumn"}
						,{type:"select", width:60, label:"연락처", name:"minorAgentTelFn", labelWidth:40, userdata:{lov:"RCP0041"}, offsetLeft:20}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=TEL,HPN"
						,{type:"newcolumn"}
						,{type:"input", width:50, label:"-", labelAlign:"center", labelWidth: 5, name:"minorAgentTelMn",maxLength:4}
						,{type:"newcolumn"}
						,{type:"input", width:50, label:"-", labelAlign:"center", labelWidth: 5, name:"minorAgentTelRn",maxLength:4}
					]}
	 	   			// 부정가입방지조회
					,{type:"block", list:[
						{type:"checkbox", width:130, label:"본인조회동의", name:"minorSelfInqryAgrmYn", value:"Y"}
						,{type:"newcolumn"}
						,{type:"select", labelWidth:65, width:90, label:"인증방식", name:"minorSelfCertType", labelAlign:"right", userdata:{lov:"RCP0045"}, offsetLeft:25}
						,{type:"newcolumn"}
						,{type:"calendar", label:"발급/만료일자", name:"minorSelfIssuExprDt", labelAlign:"right", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", offsetLeft:10}
						,{type:"newcolumn"}
						,{type:"input", label:"발급번호", name:"minorSelfIssuNum", width:150}
					]}
 				]}

				,{type: "fieldset", label: "약관동의정보", name:"TERMS", inputWidth:910, list:[
					{type:"settings"}
					,{type:"block", list:[
							{type:"checkbox",width:90,labelWidth:200, label:"개인정보 수집동의", position:"label-right", name:"clausePriCollectFlag",value:"Y",checked:false}
							,{type:"newcolumn"}
							,{type:"checkbox", width:50, labelWidth:200, label:"개인정보 제공동의", position:"label-right", name:"clausePriOfferFlag",value:"Y",checked:false}
							,{type:"newcolumn"}
							,{type:"checkbox", width:50, labelWidth:200, label:"고유식별정보 수집이용제공동의", position:"label-right",name:"clauseEssCollectFlag",value:"Y",checked:false}
							,{type:"newcolumn"}
							,{type:"checkbox", width:90,labelWidth:150, label:"개인정보 위탁동의", position:"label-right",name:"clausePriTrustFlag",value:"Y",checked:false}
		  	   		]}
					,{type:"block", list:[
							{type:"checkbox", width:50,labelWidth:200, label:"신용정보동의", position:"label-right",name:"clauseConfidenceFlag",value:"Y",checked:false}
							,{type:"newcolumn"}
							,{type:"checkbox", width:50,labelWidth:200, label:"개인정보 광고전송동의", position:"label-right",name:"clausePriAdFlag",value:"Y",checked:false}
							,{type:"newcolumn"}
							,{type:"checkbox", width:50,labelWidth:200, label:"유해정보차단APP 설치동의", position:"label-right", name:"appBlckAgrmYn", value:"Y", checked:false}
							,{type:"newcolumn"}
							,{type:"checkbox", width:90, labelWidth:150, label:"네트워크차단동의", position:"label-right", name:"nwBlckAgrmYn", value:"Y", checked:false}
					]}
					,{type:"block", list:[
							{type:"checkbox", width:50,labelWidth:200, label:"제휴서비스를 위한동의", position:"label-right", name:"clauseJehuFlag", value:"Y", checked:false}
							,{type:"newcolumn"}
							,{type:"select", offsetLeft:225, width:250, labelWidth:100, label:"유해정보차단APP", position:"label-left", name:"appCd", userdata:{lov:"RCP0044"}}
	  	   			]}
					,{type:"block", list:[
							{type:"hidden", width:60, label:"", name:"agentCode"}
							,{type:"hidden", width:60, label:"", name:"requestKey"}
					]}
				]}
			]}
		],
		buttons : {
			right : {
// 				btnSave : {label : "저장" },
// 				btnMod : {label : "수정" },
				btnClose : { label: "닫기" }
			},
			left : {
				btnSend: {label:"N-STEP 전송", auth:"A-NSTEP" }
			}
		},
		onValidateMore : function (data) {
			var form=this;

			//-------------------------------------------------
			// 고객정보

			//		Maile 수신여부에 동의 or 명세서수신유형(메일) 인 경우
			if(data.cstmrMailReceiveFlag == 'Y' || data.cstmrBillSendCode == 'CB') {	// CB:이메일,LX:우편
				var cstmrMail1 = '';
				var cstmrMail2 = '';
				try {
					cstmrMail1 = data.cstmrMail1.replace(/\s/g,'');
					cstmrMail2 = data.cstmrMail2.replace(/\s/g,'');
				}
				catch(e) {
					cstmrMail1 ='pass';
				}

				// 메일 미 작성시 Error
				if(cstmrMail1 == '' || cstmrMail2 == '') {
					this.pushError("cstmrMail1","메일",["ICMN0001"]);
				}
			}

			//전화번호, 휴대폰 번호 필수 체크
			if(!data.cstmrTelFn || !data.cstmrTelMn || !data.cstmrTelRn){
				this.pushError("cstmrTelFn","전화번호",["ICMN0001"]);
			}
			else if((data.cstmrTelMn.length < 3) || (data.cstmrTelRn.length < 4)) {
				this.pushError("cstmrTelFn","전화번호","형식에 맞지 않습니다.");
			}

			//휴대폰 번호 필수 체크
			if(!data.cstmrMobileFn || !data.cstmrMobileMn || !data.cstmrMobileRn){
				this.pushError("cstmrMobileFn","휴대폰",["ICMN0001"]);
			}
			else if((data.cstmrMobileMn.length < 3) || (data.cstmrMobileRn.length < 4)) {
				this.pushError("cstmrMobileFn","휴대폰","형식에 맞지 않습니다.");
			}

			//고객구분에 따른 체크 로직 : "NA:내국인,NM:내국인(미성년자),FN:외국인,JP:법인,PP:개인사업자"
			var cstmrType = data.cstmrType;
			if(cstmrType == 'NA' || cstmrType == 'NM' || cstmrType == 'JP'){
				if(!data.cstmrNativeRrn1 || !data.cstmrNativeRrn2){
					this.pushError("cstmrNativeRrn1","주민번호",["ICMN0001"]);
				}
			}
			switch(cstmrType){
				case 'NM':
					if(!data.minorAgentName){
						this.pushError("minorAgentName","대리인성명",["ICMN0001"]);
					}
					if(!data.minorAgentRelation){
						this.pushError("minorAgentRelation","관계",["ICMN0001"]);
					}
					if(!data.minorAgentRrn1 || !data.minorAgentRrn2){
						this.pushError("minorAgentRrn1","대리인 주민번호",["ICMN0001"]);
					}
					if(!data.minorAgentTelFn || !data.minorAgentTelMn || !data.minorAgentTelRn){
						this.pushError("minorAgentTelFn","연락처",["ICMN0001"]);
					}

					// 청소년유해정보차단 추가( 신청서 정상, 접수대기 상태에서만 체크 )
					if(data.pstate == "00" && data.requestStateCode == "00"){
						if(data.nwBlckAgrmYn != 'Y'){
							this.pushError("nwBlckAgrmYn", "네트워크차단동의", ["ICMN0001"]);
						}
						// 2015-05-11, 유해정보차단APP 설치동의 필수조건 변경
						if(data.appBlckAgrmYn != 'Y'){
							this.pushError("appBlckAgrmYn", "유해정보차단APP 설치동의", ["ICMN0001"]);
						}
						//if(data.appBlckAgrmYn == "Y" && data.appCd == ""){
						if(data.appCd == ""){
							this.pushError("appCd", "유해정보차단APP", "설치하실 APP을 선택하세요");
						}
					}
						
					//연락처 번호 필수 체크
					if(data.minorAgentTelMn || data.minorAgentTelRn){
						if((data.minorAgentTelMn.length < 3) || (data.minorAgentTelRn.length < 4)) {
							this.pushError("minorAgentTelFn","연락처","형식에 맞지 않습니다.");
						}
					}

					break;
				case 'FN':
					if(!data.cstmrForeignerRrn1 || !data.cstmrForeignerRrn2){
						this.pushError("cstmrForeignerRrn1","외국인번호",["ICMN0001"]);
					}
					if(!data.cstmrForeignerNation){
						this.pushError("cstmrForeignerNation","국적",["ICMN0001"]);
					}
					//외국인 생년월일 필수값 제외
					/* if(!data.cstmrForeignerBirth){
						this.pushError("cstmrForeignerBirth","외국인생년월일",["ICMN0001"]);
					} */
					break;
				case 'JP':
					if(!data.cstmrJuridicalCname){
						this.pushError("cstmrJuridicalCname","법인명",["ICMN0001"]);
					}
					if(!data.cstmrJuridicalRrn1 || !data.cstmrJuridicalRrn2){
						this.pushError("cstmrJuridicalRrn1","법인번호",["ICMN0001"]);
					}
					if(!data.cstmrJuridicalNumber1 || !data.cstmrJuridicalNumber2 || !data.cstmrJuridicalNumber3){
						this.pushError("cstmrJuridicalNumber1","사업자등록번호",["ICMN0001"]);
					}
					break;
				case 'PP':
					if(!data.cstmrPrivateCname){
						this.pushError("cstmrPrivateCname","상호명",["ICMN0001"]);
					}
					if(!data.cstmrPrivateNumber1 || !data.cstmrPrivateNumber2 || !data.cstmrPrivateNumber3){
						this.pushError("cstmrPrivateNumber1","사업자등록번호",["ICMN0001"]);
					}
					break;
			}

			// 2015-04-20 본인인증관련
			// 신청서 정상, 접수대기 상태에서만 체크한다.
			if(data.pstate == "00" && data.requestStateCode == "00"){
				if(!data.selfInqryAgrmYn || data.selfInqryAgrmYn == "" || data.selfInqryAgrmYn != "Y"){
					this.pushError("selfInqryAgrmYn", "본인조회동의", "부정가입방지를 위한 본인조회동의를 선택하세요");
				}

				if(data.cstmrType != "NM" && ( !data.selfCertType || data.selfCertType == "")){
					this.pushError("selfCertType", "인증방식", "본인인증방식을 선택하세요");
				}

				if(data.cstmrType == "NM"){
					if(data.minorSelfInqryAgrmYn == "" || data.minorSelfInqryAgrmYn != "Y"){
						this.pushError("minorSelfInqryAgrmYn", "법정대리인 본인조회동의", "본인조회동의를 선택하세요");
					}

					if(!data.minorSelfCertType || data.minorSelfCertType == ""){
						this.pushError("minorSelfCertType", "법정대리인 인증방식", "본인인증방식을 선택하세요");
					}

					if(!data.minorSelfIssuExprDt || data.minorSelfIssuExprDt == ""){
						if(data.minorSelfCertType == "05"){
							this.pushError("minorSelfIssuExprDt", "법정대리인 발급/만료일자", "여권 만료일자를 선택하세요");
						}else{
							this.pushError("minorSelfIssuExprDt", "법정대리인 발급/만료일자", "발급일자를 선택하세요");
						}
					}else{
						if((data.minorSelfCertType == "02" || data.minorSelfCertType == "04") && (!data.minorSelfIssuNum || data.minorSelfIssuNum == "")) {
							this.pushError("minorSelfIssuNum", "법정대리인 발급번호", "운전면허증/국가유공자증 발급번호를 입력하세요");
						}
					}
				}else if(data.cstmrType == "FN"){
					if(data.selfCertType != "05" && data.selfCertType != "06" && data.selfCertType != "07"){
						this.pushError("selfCertType","인증방식","외국인의 경우 인증방식은 여권, 외국인등록증, 국내거소신고증을 선택하세요");
					}

					if(!data.selfIssuExprDt || data.selfIssuExprDt == ""){
						this.pushError("selfIssuExprDt", "발급/만료일자", "여권 만료일자를 선택하세요");
					}
				}else if(data.cstmrType == "NA"){
					if(data.selfCertType == "06" && data.selfCertType == "07"){
						this.pushError("selfCertType","인증방식","내국인의 인증방식은 외국인등록증, 국내거소신고증을 선택할 수 없습니다.");
					}

					if(!data.selfIssuExprDt || data.selfIssuExprDt == ""){
						if(data.selfCertType == "05"){
							this.pushError("selfIssuExprDt", "발급/만료일자", "여권 만료일자를 선택하세요");
						}else{
							this.pushError("selfIssuExprDt", "발급/만료일자", "발급일자를 선택하세요");
						}
					}else{
						if((data.selfCertType == "02" || data.selfCertType == "04") && (!data.selfIssuNum || data.selfIssuNum == "")) {
							this.pushError("selfIssuNum", "발급번호", "운전면허증/국가유공자증 발급번호를 입력하세요");
						}
					}
				}
			}

			//요금납부정보 : "C:신용카드,D:자동이체,R:지로","AA:자동충전(이체),VA:가상계좌"
			var reqPayType = data.reqPayType;

			if(data.pstate == "00" && !reqPayType){
				this.pushError("reqPayType","요금납부방법","필수 선택 항목입니다.");
			}

			switch(reqPayType){
				case 'C':
					if(!data.reqCardCompany || !data.reqCardNo1 || !data.reqCardNo2 || !data.reqCardNo3  || !data.reqCardNo4 || !data.reqCardMm || !data.reqCardYy){
						this.pushError("reqCardCompany","카드사 및 카드번호",["ICMN0001"]);
					}

					if(data.reqCardYy.length != 4 || data.reqCardMm.length != 2){
						this.pushError("reqCardYy","카드사 및 카드번호", "카드유효기간을 입력하세요.");
					}
					if(!data.reqCardName){
						this.pushError("reqCardName","카드명의자 성명",["ICMN0001"]);
					}
					if(!data.reqCardRrn1 || !data.reqCardRrn2){
						this.pushError("reqCardRrn1","카드명의자 주민번호",["ICMN0001"]);
					}
					break;
				case 'D':
					if(!data.reqBank || !data.reqAccountNumber){
						this.pushError("reqBank","은행명 및 계좌번호",["ICMN0001"]);
					}
					if(!data.reqAccountName){
						this.pushError("reqAccountName","예금주 성명",["ICMN0001"]);
					}
					if(!data.reqAccountRrn1 || !data.reqAccountRrn2){
						this.pushError("reqAccountRrn1","예금주 주민번호",["ICMN0001"]);
					}
					break;
				case 'AA':
					var value = data.reqAcType;

					if(!data.reqAcType){
						this.pushError("reqAcType","충전방법",["ICMN0001"]);
					}
					if(!data.reqAcAmount){
  						this.pushError("reqAcAmount","충전금액",["ICMN0001"]);
  					}

					if(value == "01") {
	  					if(!data.reqAc01Balance){
	  						this.pushError("reqAc01Balance","충전잔액",["ICMN0001"]);
	  					}

					}else if(value == "02") {
					  if(!data.reqAc02Day){
						  this.pushError("reqAc02Day","충전일",["ICMN0001"]);
	  					}
					}
					break;
			}

			//구매유형에 따른 체크 : "MM:단말구매,UU:유심단독구매"
			var reqBuyType = data.reqBuyType;
			switch(reqBuyType){
			/*
				case 'UU':
					if(!data.reqUsimName){
						this.pushError("reqUsimName","휴대폰모델/색상",["ICMN0001"]);
					}
					break;
			*/
				case 'MM':
					if(!data.reqModelColor) {
						this.pushError("reqModelColor","모델색상",["ICMN0001"]);
					}
					
					if(data.checkSn == 'N' || data.checkSn == ''){
						//진행상태가 접수대기, 접수 상태일때는 체크 제외(단말기일련번호 입력값이 있으면 체크)
						// 2015-11-25, 해피콜(02) 상태 추가
						if(data.requestStateCode != "00" && data.requestStateCode != "01" && data.requestStateCode != "09" && data.requestStateCode != "02") {
							this.pushError("reqPhoneSn","단말기일련번호", "단말기일련번호 재고확인을 해주세요.");
						} else {
							if(data.reqPhoneSn){
								this.pushError("reqPhoneSn","단말기일련번호", "단말기일련번호 재고확인을 해주세요.");
							}
						}
					};
					break;
			}

			//번호연결서비스
			if(data.reqGuideFlag == 'Y' &&
				( !data.reqGuideFn || !data.reqGuideMn || !data.reqGuideRn )
			){
				this.pushError("reqGuideFlag","번호연결서비스",["ICMN0001"]);
			}

			//부가서비스
			if(!data.additionName){
				this.pushError("additionName","부가서비스",["ICMN0001"]);
			}

			//업무구분에 따른 체크 : "MNP3:번호이동,NAC3:개통"
			var operType = data.operType;

			switch(operType) {
				case 'NAC3':
					//가입희망번호
					if(!data.reqWantNumber){
						this.pushError("reqWantNumber","가입희망번호",["ICMN0001"]);
					}

				break;

				case 'MNP3':
					if(!data.moveMobileFn || !data.moveMobileMn || !data.moveMobileRn){
						this.pushError("moveMobileFn","이동번호",["ICMN0001"]);
					}
					else if((data.moveMobileMn.length < 3) || (data.moveMobileRn.length < 4)) {
							this.pushError("moveMobileFn","이동번호","형식에 맞지 않습니다.");
					}
					if(!data.moveCompany){
						this.pushError("moveCompany","변경전통신사",["ICMN0001"]);
					}
					if(!data.moveThismonthPayType){
						this.pushError("moveThismonthPayType","이달 사용요금",["ICMN0001"]);
					}
					if(!data.moveAllotmentStat){
						this.pushError("moveAllotmentStat","휴대폰 할부금",["ICMN0001"]);
					}
					if(!data.moveRefundAgreeFlag){
						this.pushError("moveRefundAgreeFlag","미환급액상계",["ICMN0001"]);
					}
					if(!data.moveAuthType || !data.moveAuthNumber ){
						this.pushError("moveAuthType","인증유형",["ICMN0001"]);
					}

				break;
				case 'HCN3':
					if(!data.dvcChgType){
						this.pushError("dvcChgType","기변유형",["ICMN0001"]);
					}
					if(!data.dvcChgRsnCd){
						this.pushError("dvcChgRsnCd","기변사유",["ICMN0001"]);
					}
					if(!data.dvcChgRsnDtlCd){
						this.pushError("dvcChgRsnDtlCd","기변상세사유",["ICMN0001"]);
					}
				break;
			}
			if(data.faxyn == 'Y' && ( !data.faxnum1 || !data.faxnum2 || !data.faxnum3 )) {
				this.pushError("faxnum1","팩스번호",["ICMN0001"]);
			}
			
			// 오프라인 판매정책 체크
			if(data.onOffType == '1'){
				if(!data.salePlcyCd) this.pushError("salePlcyCd","판매정책/명",["ICMN0001"]);

				// 대리점 보조금 체크
				if(Number(data.modelDiscount3) < 0 || Number(data.modelDiscount3) > Number(data.maxDiscount3)){
					this.pushError("modelDiscount3", "판매사보조금", "판매사보조금은 0 ~ 판매사보조금(MAX) 사이의 값을 입력하세요.");
				}

			}
			
			/*
			// by 2015-09-09
			// 온라인 경우, 배송정보의 전화번호 및 휴대폰 번호는 필수가 아니기에 제외
			// 배송지 체크
			if(data.dlvryName) {
				//전화번호, 휴대폰 번호 필수 체크
				if(!data.dlvryTelFn || !data.dlvryTelMn || !data.dlvryTelRn){
					this.pushError("dlvryTelFn","전화번호",["ICMN0001"]);
				}
				else if((data.dlvryTelMn.length < 3) || (data.dlvryTelRn.length < 4)) {
					this.pushError("dlvryTelFn","전화번호","형식에 맞지 않습니다.");
				}
	
				//휴대폰 번호 필수 체크
				if(!data.dlvryMobileFn || !data.dlvryMobileMn || !data.dlvryMobileRn){
					this.pushError("dlvryMobileFn","휴대폰",["ICMN0001"]);
				}
				else if((data.dlvryMobileMn.length < 3) || (data.dlvryMobileRn.length < 4)) {
					this.pushError("dlvryMobileFn","휴대폰","형식에 맞지 않습니다.");
				}
			}
			*/
		},
		onChange : function(name) {
			var form=this;
			form2Change(form, 1, name);
		},
		checkSelectedButtons : ["btnSave","btnMod"],
		onButtonClick : function(btnId) {
			var form = this;

			switch (btnId) {

			case "btnClose" :
				mvno.ui.closeWindowById(PAGE.FORM2, true);   // id 대신 form 도 가능
				break;
				
			case "btnCstmrPostFind":
				var form=this;
				mvno.ui.codefinder4ZipCd("FORM2", "cstmrPost", "cstmrAddr", "cstmrAddrDtl");
				PAGE.FORM2.enableItem("cstmrAddrDtl");
				break;
				
			case "btnDlvryPostFind":
				var form=this;
				mvno.ui.codefinder4ZipCd("FORM2", "dlvryPost", "dlvryAddr", "dlvryAddrDtl");
				PAGE.FORM2.enableItem("dlvryAddrDtl");
				break;
				
			case "btnPaper" :
			   	var width = 900;
			   	var height = 700;
			   	var top  = 10;
			   	var left = 10;
			   	var url = "${scnUrl}" + "/scannerViewer.do?callType=C&resNo="+form.getItemValue("requestKey");
			   //	var url = "http://msplocal.ktmmobile.com:8080" + "/scannerViewer.do?callType=C&resNo="+form.getItemValue("requestKey");
			   	window.open(url, "_popup1", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);
				break;
				
			case "btnRegPaper" :
			 	var width = 900;
			   	var height = 700;
			   	var top  = 10;
			   	var left = 10;
			   	var form = this;
				var name = form.getItemValue("cstmrName");
				var enUserName = encodeURI(encodeURIComponent(name));
				var scanId = null;
				var DBMSType = "C";
				var scanType = form.getItemValue("scanType");
				form.setItemValue("btnType","scan");

				if(scanType == "edit") {
					  enUserName = "";
				}

				//scanId 받아오기
		 		mvno.cmn.ajax(ROOT + "/appForm/getScanId.json", "", function(resultData) {
					form.setItemValue("scanId", resultData.result.scanId);

					scanId = form.getItemValue("scanId");
					//var url = "${scnUrl}" + "/appForm/scannerAppliaction.do";
				   	//var url = "http://msplocal.ktmmobile.com:8080" + "/appForm/scannerAppliaction.do?scanId="+scanId+"&userName="+enUserName+"&DBMSType="+DBMSType+"&scanType="+scanType;
				   	var url = "${scnUrl}" + "/appForm/scannerAppliaction.do?scanId="+scanId+"&userName="+enUserName+"&DBMSType="+DBMSType+"&scanType="+scanType;
				   	window.open(url, "_popup1", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);

				});

				break;


			case "btnScnSarch" :
			 	var width = 1200;
			   	var height = 700;
			   	var top  = 10;
			   	var left = 10;
			   	var form = this;
			   	var resNo = form.getItemValue("resNo");
			   	var scanId = form.getItemValue("scanId");
			   	var DBMSType = "C";
			   	var scanType = form.getItemValue("scanType");
				var data = null;
				form.setItemValue("btnType","sSearch");

				//scanId 받아오기
		 		mvno.cmn.ajax(ROOT + "/appForm/getScanId.json", "", function(resultData) {
					form.setItemValue("scanId", resultData.result.scanId);
					scanId = form.getItemValue("scanId");
				});
								
			   	if(scanType == "new") {
			   	   data = {"DBMSType":DBMSType,"RGST_PRSN_ID":userId,"RGST_PRSN_NM":userName,"ORG_TYPE":orgType,"ORG_NM":orgNm,"ORG_ID":orgId};
			   	}else {
			   	   data = {"DBMSType":DBMSType,"RGST_PRSN_ID":userId,"RGST_PRSN_NM":userName,"ORG_TYPE":orgType,"ORG_NM":orgNm,"ORG_ID":orgId,"PARENT_SCAN_ID":scanId,"RES_NO":resNo};
			   	}
			   	var url = "${scanSearchUrl}";
			   	var options = {"target":"_popup1"};

			   	window.open("", "_popup1", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);
			   	mvno.cmn.submitDynamicForm(url, data, options);

				break;

			case "btnFaxSarch" :
				var width = 1200;
			   	var height = 700;
			   	var top  = 10;
			   	var left = 10;
			   	var form = this;
			   	var resNo = form.getItemValue("resNo");
			   	var scanId = form.getItemValue("scanId");
			  	var DBMSType = "C";
			   	var CID = null;
			   	//var CID = form.getItemValue("faxBySearch");

			   	var parameters = "";
				var scanType = form.getItemValue("scanType");

				form.setItemValue("btnType","fSearch");

		   	   var data = null;
			  	if(scanType == "new") {
			  	 CID = form.getItemValue("faxnum1") + form.getItemValue("faxnum2") + form.getItemValue("faxnum3");
			  	 data = {"DBMSType":DBMSType,"RGST_PRSN_ID":userId,"RGST_PRSN_NM":userName,"ORG_TYPE":orgType,"ORG_NM":orgNm,"ORG_ID":orgId,"CID":CID};
			  	}else {
			  		CID = form.getItemValue("faxBySearch");
			  	   data = {"DBMSType":DBMSType,"RGST_PRSN_ID":userId,"RGST_PRSN_NM":userName,"ORG_TYPE":orgType,"ORG_NM":orgNm,"ORG_ID":orgId,"PARENT_SCAN_ID":scanId,"CID":CID,"RES_NO":resNo};
			  	}
			  	var url = "${faxSearchUrl}";
			  	var options = {"target":"_popup1"};

			 	window.open("", "_popup1", "width="+width+",height="+height+",resizable=no,scrollbars=yes,top="+top+",left="+left);
			  	mvno.cmn.submitDynamicForm(url, data, options);

				break;
			case "btnSend":
				
				if(this.getItemValue('reqUsimSn')==''){
					mvno.ui.alert('유심번호를 입력하세요.');
					return;
				}

				var url = "/rcp/rcpMgmt/updateRcpDetail.json";
				mvno.cmn.ajax(ROOT + url, form, function(result) {
				});
				
				url = "/pps/rcpmgmt/updateRcpSend.json";
				mvno.cmn.ajax(ROOT + url, form, function(result) {
					mvno.ui.closeWindowById(PAGE.FORM2, true);
					PAGE.GRID1.refresh();
				});
				break;
			}
		}
	}
}; // end dhx

var PAGE = {
		 title : "${breadCrumb.title}",
		 breadcrumb : "${breadCrumb.breadCrumb}", 
		 buttonAuth: ${buttonAuth.jsonString},
		 onOpen:function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");

			PAGE.FORM1.disableItem("pSearchName");
		}
	};

function form2Change(form, mFlag, name){
	var showItems = new Array();
	var hideItems = new Array();
	var enableItems = new Array();
	var disableItems = new Array();
	//alert(name);
	switch(name){

			case 'init' :
				// 제조사 리스트
				mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpCommon.json",
									{
										grpId:"MNFCT"
									},
									form,
									"mnfctId");

				break;

		case 'reqPhoneSn' :
			form.setItemValue('checkSn', 'N');
			break;

		case 'reqBuyType' :
			//"MM:단말구매,UU:유심단독구매"
			var value = form.getItemValue(name);
			//if(form.isItemEnabled("requestStateCode")) {
			if(form.getItemValue("requestStateCode") != "21") {
				if(value == 'MM') {
					enableItems = ["reqModelColor", "reqPhoneSn", "btnCheckSn", "sprtTp"];
				} else {
					disableItems = ["reqModelColor", "reqPhoneSn", "btnCheckSn", "sprtTp"];
				}
			}

			break;

		case 'operType' :
			var value = form.getItemValue(name);
			
			if(mFlag == 1) {
				// 초기화
				operTypeInit();
			}

			hideItems = [
							"REQMOVE",	// 번호이동정보
							"NEWREQ",	// 신규가입
							"DVCCHG"	// 기기변경정보
						];

			// "MNP3:번호이동,NAC3:개통,HCN3:기기변경"
			if(value == 'MNP3'){
				showItems = ["REQMOVE"];
			} else if(value == 'NAC3'){
				showItems = ["NEWREQ"];
			} else {
				showItems = ["DVCCHG"];
			}

			break;

		case 'cstmrType' :
			var value = form.getItemValue(name);
			
			if(mFlag == 1) {
				// 초기화
				cstmrInit();
			}

			hideItems = [
							"FOREIGNER",	// 외국인정보
							"BUSINESS",		// 사업자정보
							"MINOR"			// 법정대리인
						];

			//"NA:내국인,NM:내국인(미성년자),FN:외국인,JP:법인,PP:개인사업자"
			switch (value) {
				case "PP" :	// 개인사업자
					showItems = [
					             "BUSINESS"
					             ];
				
					// 법인 관련 필드 숨기기 및 초기화
					// 법인명
					PAGE.FORM2.hideItem("cstmrJuridicalCname");
					
					// 사업자번호
					PAGE.FORM2.hideItem("cstmrJuridicalNumber1");
					PAGE.FORM2.hideItem("cstmrJuridicalNumber2");
					PAGE.FORM2.hideItem("cstmrJuridicalNumber3");
					
					// 법인 번호
					PAGE.FORM2.hideItem("cstmrJuridicalRrn1");
					PAGE.FORM2.hideItem("cstmrJuridicalRrn2");
					
					// 개인사업자 관련 필드 보이기
					// 개인사업자명
					PAGE.FORM2.showItem("cstmrPrivateCname");
					// 사업자번호
					PAGE.FORM2.showItem("cstmrPrivateNumber1");
					PAGE.FORM2.showItem("cstmrPrivateNumber2");
					PAGE.FORM2.showItem("cstmrPrivateNumber3");

					break;

				case "JP" :	// 법인사업자
					showItems = [
					             "BUSINESS"
					             ];
				
					// 개인사업자 관련 필드 숨기기 및 초기화
					// 개인사업자명
					PAGE.FORM2.hideItem("cstmrPrivateCname");
					
					// 사업자번호
					PAGE.FORM2.hideItem("cstmrPrivateNumber1");
					PAGE.FORM2.hideItem("cstmrPrivateNumber2");
					PAGE.FORM2.hideItem("cstmrPrivateNumber3");

					// 개인사업자 관련 필드 보이기
					// 개인사업자명
					PAGE.FORM2.showItem("cstmrJuridicalCname");
					// 사업자번호
					PAGE.FORM2.showItem("cstmrJuridicalNumber1");
					PAGE.FORM2.showItem("cstmrJuridicalNumber2");
					PAGE.FORM2.showItem("cstmrJuridicalNumber3");
					// 법인번호
					PAGE.FORM2.showItem("cstmrJuridicalRrn1");
					PAGE.FORM2.showItem("cstmrJuridicalRrn2");
					
					break;

				case "FN" :	// 외국인
					showItems = ["FOREIGNER"];
					break;

				case "NM" :	// 미성년자
					showItems = ["MINOR"];
					break;

				default :	// 내국인
					break;
			}

			break;

		case 'faxyn':
			var value = form.getItemValue(name);

			//var rowData = PAGE.GRID1.getSelectedRowData();
			var onOffType = form.getItemValue("onOffType");

			// 숨김 항목 설정
			hideItems = [
			             	"DLVRY",
			             	"faxyn",		// 스캔/팩스 여부
						 	"btnRegPaper",	// 스캔버튼 (서식지)
						 	"faxnum",		// 팩스 번호 입력 부분 (서식지)
						 	"btnFaxSarch",	// 팩스검색
						 	"btnScnSarch",	// 스캔검색
						 	"btnRecSarch"	// 녹취파일등록
						 ];

			// 보일 항목 설정
			// 팩스 선택
			if(value == 'Y') {
				if(onOffType == "1")
					showItems = ["faxyn", "faxnum", "btnFaxSarch"];	// 팩스 번호 입력 부분, 팩스 검색
				else
					showItems = ["DLVRY", "faxyn", "faxnum", "btnFaxSarch"];	// 팩스 번호 입력 부분, 팩스 검색
					
			}
			// 스캔 선택
			else{
				// 기존 팩스 내역 삭제
				form.setItemValue("faxnum1", "");
				form.setItemValue("faxnum2", "");
				form.setItemValue("faxnum3", "");

				// 오프라인
				if(onOffType == "1")
				{
					showItems = ["faxyn", "btnRegPaper", "btnScnSarch", "btnRecSarch"];	// 스캔버튼, 스캔검색
				}
				else
				{
					showItems = ["DLVRY", "btnRecSarch"];	// 녹취파일등록
				}
			}

			break;

		case 'onOffType':
			var onOffType = form.getItemValue("onOffType");
			
			// 숨김 항목 설정
			hideItems = [
			             	"DLVRY",
			             	"faxyn",		// 스캔/팩스 여부
						 	"btnRegPaper",	// 스캔버튼 (서식지)
						 	"faxnum",		// 팩스 번호 입력 부분 (서식지)
						 	"btnFaxSarch",	// 팩스검색
						 	"btnScnSarch",	// 스캔검색
						 	"btnRecSarch"	// 녹취파일등록
						 ];
			// 기존 팩스 내역 삭제
			form.setItemValue("faxnum1", "");
			form.setItemValue("faxnum2", "");
			form.setItemValue("faxnum3", "");

			// 오프라인
			if(onOffType == "1")
			{
				form.setItemValue("faxyn", "N");
				showItems = ["faxyn", "btnScnSarch", "btnRecSarch"];	// 스캔을 기본으로
			}
			else
			{
				showItems = ["DLVRY", "btnRecSarch"];	// 녹취파일등록
			}
			break;

		case 'serviceType':
			var value = form.getItemValue(name);

			if(value == 'PO'){
				mvno.ui.reloadLOV(PAGE.FORM2, "reqPayType", "RCP0008");
			} else {
				mvno.ui.reloadLOV(PAGE.FORM2, "reqPayType", "RCP0029");
			}

			break;

		case 'reqPayType':
			var value = form.getItemValue(name);
			
			if(mFlag == 1) {
				// 초기화
				reqPayTypeInit();
			}

			hideItems = [
							"CARD",	// 카드
							"BANK"	// 은행
//							"AUTO"	// 지로
						];
			
			// 단말모델 콤보박스 적용
			form2Polcy(form, "PRDT", "prdtId");

			// 요금제 적용
			form2Polcy(form, "RATE", "socCode");

			switch (value) {
				case "C" :	// 카드
					showItems = ["CARD"];
					break;

				case "D" :	// 은행
					showItems = ["BANK"];
					break;

				default :
					break;
			}

			break;

		case 'reqAcType':
			var value = form.getItemValue(name);
			hideItems = ["reqAc01Balance", "reqAc02Day", "reqAcAmount"];
			switch (value) {
				case "01" :
					showItems = ["reqAc01Balance", "reqAcAmount"];
					break;
				case "02" :
					showItems = ["reqAc02Day", "reqAcAmount"];
					break;
			}

			form.setItemValue("reqAc02Day","");
			form.setItemValue("reqAcAmount","");

			break;

		case 'reqInDay':
			
			var strDate = form.getItemValue("reqInDayStr");
			var reqInDayYy = strDate.substr(0,4);
			var reqInDayMm = strDate.substr(5,2) - 1;
			var reqInDayDd = strDate.substr(8,2);
			
			if(reqInDayMm == 0) {
				reqInDayMm = 00;
			}

			form.setItemValue("reqInDay", new Date(reqInDayYy, reqInDayMm, reqInDayDd));


			break;

		case 'prdtId':	// 단말모델 선택 시, 색상 리스트 가져오기

			if(form.getItemValue("reqBuyType") == "MM") {
				mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpCommon.json",
									{
										grpId:"COLOR"
										,prdtId:form.getItemValue("prdtId")
										,mnfctId:form.getItemValue("mnfctId")
									},
									form,
									"reqModelColor");
			}

			break;

		case 'cloneBase':
			var value = form.getItemValue(name);
			if (value == "1") {
				form.setItemValue("dlvryName",form.getItemValue("cstmrName"));
				form.setItemValue("dlvryTelFn",form.getItemValue("cstmrTelFn"));
				form.setItemValue("dlvryTelMn",form.getItemValue("cstmrTelMn"));
				form.setItemValue("dlvryTelRn",form.getItemValue("cstmrTelRn"));
				form.setItemValue("dlvryMobileFn",form.getItemValue("cstmrMobileFn"));
				form.setItemValue("dlvryMobileMn",form.getItemValue("cstmrMobileMn"));
				form.setItemValue("dlvryMobileRn",form.getItemValue("cstmrMobileRn"));
				form.setItemValue("dlvryAddr",form.getItemValue("cstmrAddr"));
				form.setItemValue("dlvryPost",form.getItemValue("cstmrPost"));
				form.setItemValue("dlvryAddrDtl",form.getItemValue("cstmrAddrDtl"));
			}

			break;

		case 'cstmrMail3':
			var value = form.getItemValue(name);
			if(value == "직접입력") {
   				//$("input[name=cstmrMail2]").attr("readonly",false);
   				enableItems = ["cstmrMail2"];
			} else {
				//$("input[name=cstmrMail2]").attr("readonly",true);
				disableItems = ["cstmrMail2"];
				form.setItemValue("cstmrMail2", value);
			}

			break;

		case 'reqWireType1' :
			var value = form.getItemValue(name);
			if ( value != "") {
				disableItems = ["reqWireType2"];
			} else {
				enableItems = ["reqWireType2"];
			}

			break;

		case 'reqWireType2' :
			var value = form.getItemValue(name);
			if ( value != "") {
				disableItems = ["reqWireType1"];
			} else {
				enableItems = ["reqWireType1"];
			}

			break;

		case 'requestStateCode' :
			var value = form.getItemValue(name);
			hideItems = ["tbCd", "dlvryNo"];
			if ( value == "10" || value == "20" || value == "21") {
				showItems = ["tbCd", "dlvryNo"];
			}

			break;

		case "reqModelColor" :
			// 단말모델색상 변경은 단말일련번호와 무관하여 제거
			form.setItemValue("reqPhoneSn", "");
			form.setItemValue("checkSn", "N");

			break;

		case 'usimPayMthdCd' :
			if(form.getItemValue("usimPayMthdCd") == "1") {
				form.setItemValue("usimPrice", "0");				// USIM비
			}

			break;

		case 'joinPayMthdCd' :
			if(form.getItemValue("joinPayMthdCd") == "1") {
				form.setItemValue("joinPrice", "0");				// 가입비
			}

			break;


		case "dvcChgRsnCd" :	// 기변사유

			var cdIdVal = "";

			var value = form.getItemValue(name);

			if(value == "1") {	// 일반
				cdIdVal = "RCP0054";

			}
			if(value == "2") {	// 불량
				cdIdVal = "RCP0055";
			}

			mvno.ui.reloadLOV(form, "dvcChgRsnDtlCd", cdIdVal);

			break;
	}

	for(var inx=0; inx < hideItems.length; inx++){
		form.hideItem(hideItems[inx]);
	}

	for(var inx=0; inx < showItems.length; inx++){
		form.showItem(showItems[inx]);
	}

	for(var inx=0; inx < disableItems.length; inx++){
		form.disableItem(disableItems[inx]);
	}

	for(var inx=0; inx < enableItems.length; inx++){
		form.enableItem(enableItems[inx]);
	}
}


function cstmrInit(){
	// 외국인 정보
	// 국적
	PAGE.FORM2.setItemValue("cstmrForeignerNation", "");
	// 여권번호
	PAGE.FORM2.setItemValue("cstmrForeignerPn", "");
	// 체류기간
	PAGE.FORM2.setItemValue("cstmrForeignerSdate", "");
	PAGE.FORM2.setItemValue("cstmrForeignerEdate", "");
	// 사회보장번호
	PAGE.FORM2.setItemValue("cstmrForeignerDod", "");
	// 생년월일
	PAGE.FORM2.setItemValue("cstmrForeignerBirth", "");
	// 외국인등록번호
	PAGE.FORM2.setItemValue("cstmrForeignerRrn1", "");
	PAGE.FORM2.setItemValue("cstmrForeignerRrn2", "");
	
	// 사업자정보
	// 상호명
	PAGE.FORM2.setItemValue("cstmrPrivateCname", "");
	// 법인명
	PAGE.FORM2.setItemValue("cstmrJuridicalCname", "");
	// 사업자번호(개인)
	PAGE.FORM2.setItemValue("cstmrPrivateNumber1", "");
	PAGE.FORM2.setItemValue("cstmrPrivateNumber2", "");
	PAGE.FORM2.setItemValue("cstmrPrivateNumber3", "");
	// 사업자번호(법인) 
	PAGE.FORM2.setItemValue("cstmrJuridicalNumber1", "");
	PAGE.FORM2.setItemValue("cstmrJuridicalNumber2", "");
	PAGE.FORM2.setItemValue("cstmrJuridicalNumber3", "");
	// 법인번호
	PAGE.FORM2.setItemValue("cstmrJuridicalRrn1", "");
	PAGE.FORM2.setItemValue("cstmrJuridicalRrn2", "");
	
	// 법정대리인
	// 대리인성명
	PAGE.FORM2.setItemValue("minorAgentName", "");
	// 관계
	PAGE.FORM2.setItemValue("minorAgentRelation", "");
	// 대리인주민번호
	PAGE.FORM2.setItemValue("minorAgentRrn1", "");
	PAGE.FORM2.setItemValue("minorAgentRrn2", "");
	// 연락처
	PAGE.FORM2.setItemValue("minorAgentTelFn", "");
	PAGE.FORM2.setItemValue("minorAgentTelMn", "");
	PAGE.FORM2.setItemValue("minorAgentTelRn", "");
	// 본인조회동의
	PAGE.FORM2.setItemValue("minorSelfInqryAgrmYn", "");
	// 인증방식
	PAGE.FORM2.setItemValue("minorSelfCertType", "");
	// 발급/만료일자
	PAGE.FORM2.setItemValue("minorSelfIssuExprDt", "");
	// 발급번호
	PAGE.FORM2.setItemValue("minorSelfIssuNum", "");

}

function operTypeInit()
{
	// 신규가입
	// 가입희망번호
	PAGE.FORM2.setItemValue("reqWantNumber", "");
	PAGE.FORM2.setItemValue("reqWantNumber2", "");
	PAGE.FORM2.setItemValue("reqWantNumber3", "");
	PAGE.FORM2.setItemValue("reqGuideFlag", "N");
	PAGE.FORM2.setItemValue("reqGuideFn", "");
	PAGE.FORM2.setItemValue("reqGuideMn", "");
	PAGE.FORM2.setItemValue("reqGuideRn", "");
	
	// 번호이동
	// 이동번호
	PAGE.FORM2.setItemValue("moveMobileFn", "");
	PAGE.FORM2.setItemValue("moveMobileMn", "");
	PAGE.FORM2.setItemValue("moveMobileRn", "");
	// 이동전통신사
	PAGE.FORM2.setItemValue("moveCompany", "");
	// 번호이동전 통신요금
	PAGE.FORM2.setItemValue("moveThismonthPayType", "");
	// 휴대폰 할부금
	PAGE.FORM2.setItemValue("moveAllotmentStat", "");
	// 미환급액 상계
	PAGE.FORM2.setItemValue("moveRefundAgreeFlag", "N");
	// 인증유형
	PAGE.FORM2.setItemValue("moveAuthType", "");
	PAGE.FORM2.setItemValue("moveAuthNumber", "");
	
	// 기기변경
	// 기변유형
	PAGE.FORM2.setItemValue("dvcChgType", "");
	// 기변사유
	PAGE.FORM2.setItemValue("dvcChgRsnCd", "");
	// 기변상세사유
	PAGE.FORM2.setItemValue("dvcChgRsnDtlCd", "");
}

function reqPayTypeInit()
{
	// 카드
	// 카드정보
	PAGE.FORM2.setItemValue("reqCardCompany", "");
	PAGE.FORM2.setItemValue("reqCardNo1", "");
	PAGE.FORM2.setItemValue("reqCardNo2", "");
	PAGE.FORM2.setItemValue("reqCardNo3", "");
	PAGE.FORM2.setItemValue("reqCardNo4", "");
	// 월
	PAGE.FORM2.setItemValue("reqCardMm", "");
	// 년
	PAGE.FORM2.setItemValue("reqCardYy", "");
	// 명의자정보
	PAGE.FORM2.setItemValue("reqCardName", "");
	// 타인납부
	PAGE.FORM2.setItemValue("reqPayOtherFlag", "N");
	// 명의자 주민번호
	PAGE.FORM2.setItemValue("reqCardRrn1", "");
	PAGE.FORM2.setItemValue("reqCardRrn2", "");
	
	// 은행
	// 은행정보
	PAGE.FORM2.setItemValue("reqBank", "");
	PAGE.FORM2.setItemValue("reqAccountNumber", "");
	// 예금주
	PAGE.FORM2.setItemValue("reqAccountName", "");
	// 타인납부
	PAGE.FORM2.setItemValue("othersPaymentAg", "N");
	// 명의자주민번호
	PAGE.FORM2.setItemValue("reqAccountRrn1", "");
	PAGE.FORM2.setItemValue("reqAccountRrn2", "");
}
function form2Polcy(form, gubun, name){

	if(gubun == "PRDT") {
		// 단말모델 콤보박스 적용
		mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpCommon.json",
							{
								grpId:gubun
								,orgnId:sOrgnId
								,reqBuyType:form.getItemValue("reqBuyType")
								,mnfctId:form.getItemValue("mnfctId")
								,reqInDay:form.getItemValue("reqInDayStr")
							},
							form,
							name);
	}

	if(gubun == "RATE") {
		// 요금제 콤보박스 적용
		mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpCommon.json",
							{
								grpId:gubun
/*
								,operType:form.getItemValue("operType")
								,reqBuyType:form.getItemValue("reqBuyType")
*/
								,serviceType:form.getItemValue("serviceType")
								,reqInDay:form.getItemValue("reqInDayStr")
							},
							form,
							name);
	}

}
function fncEnCode(param)
{
    // sjisbmoc 
    var encode = '';


    for(i=0; i<param.length; i++)
    {
        var len  = ''+param.charCodeAt(i);
        var token = '' + len.length;
        encode  += token + param.charCodeAt(i);
    }


    return encode;
}
</script>