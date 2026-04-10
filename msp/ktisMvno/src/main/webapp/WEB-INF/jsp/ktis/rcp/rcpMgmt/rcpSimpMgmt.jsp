<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	/**
	 * @Class Name : rcpSimpMgmt.jsp
	 * @Description : 신청등록(개통간소화)
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2018.03.28 강무성 최초생성
	 */
%>
<style>
	.prdt_block {
		margin-right: 15px;
	}
</style>

<!-- 신청등록 폼 -->
<div id="FORM1" class="section-box"></div>

<!-- 녹취파일 등록 -->
<div id="FORM2" class="section-box"></div>

<script type="text/javascript">
	var lbl="";

	var isCntpShop = false;	//대리점, 판매점 여부
	var hghrOrgnCd = "";

	var sOrgnId = "";
	var sOrgnNm = "";

	var sShopId = "";
	var sShopNm = "";

	var orgId = '${orgnInfo.orgnId}';
	var orgNm = '${orgnInfo.orgnNm}';

	var userId = '${loginInfo.userId}';
	var userName = '${loginInfo.userName}';

	var typeCd = '${orgnInfo.typeCd}';

	var orgType = "";
	if(typeCd == "10"){
		orgType = "K";
	}else{
		isCntpShop = true;

		sOrgnId = '${orgnInfo.orgnId}';
		sOrgnNm = '${orgnInfo.orgnNm}';

		orgType = "D";

		if(typeCd == '30'){
			hghrOrgnCd = '${orgnInfo.hghrOrgnCd}';

			orgType = "S";
		}
	}
	//조직 유형 - S:판매점, D:대리점, K:개통센터, "10:본사조직, 20:대리점, 30:판매점"
	/* if (typeCd == '20' || typeCd == '30' ) {
		isCntpShop = true;

		sOrgnId = '${orgnInfo.orgnId}';
		sOrgnNm = '${orgnInfo.orgnNm}';

		if(typeCd == '30'){
			hghrOrgnCd = '${orgnInfo.hghrOrgnCd}';
		}
	}

	if(typeCd == "10") {
		orgType = "K";
	}else if(typeCd == "20") {
		orgType = "D";
	}else if(typeCd == "30") {
		orgType = "S";
	} */


	// 삭제권한 ( 본사사용자의 경우 Y )
	var delAuthYn = "N";
	var userOrgnTypeCd = '${loginInfo.userOrgnTypeCd}';

	if(userOrgnTypeCd == "10") {
		delAuthYn = "Y";
	}

	var salePlcyData = "";	//정책선택-combobox

	var typeDtlCd2 = '${orgnInfo.typeDtlCd2}';
	var menuId = "";						//다른 화면에서 들어오는 경우 해당 MenuID

	// 스캔관련
	var blackMakingYn = "Y";					// 블랙마킹사용권한
	var blackMakingFlag = "N";					// 블랙마킹해제권한 (마스킹권한여부에 따라 변화)
	var maskingYn = '${maskingYn}';				// 마스킹권한
	if(maskingYn == "Y") {
		blackMakingFlag = "Y";
	}
	var agentVersion = '${agentVersion}';		// 스캔버전
	var serverUrl = '${serverUrl}';				// 서버환경 (로컬 : L, 개발 : D, 운영 : R)
	var scanSearchUrl = '${scanSearchUrl}';		// 스캔호출 url
	var scanDownloadUrl = '${scanDownloadUrl}';	// 스캔파일 download url

	// 렌탈신청(단말정보)
	var rntlPrdtInfo = "";

	// 주소 고객정보, 배송정보 구분
	var jusoGubun = "";

	// SRM18072675707, 단체보험
	var grpInsrYn = "${grpInsrYn}";

	var esimYn = "";

	// 20240326 Knote 사전승낙제 적용 대상 조직여부
	var knoteYn ='${knoteYn}';

	// 20250717 데이터쉐어링 요금제일 경우 체크
	var sharingYn = "";

	var sessionUserOrgnId = '${sessionUserOrgnId}';

	var DHX = {
		FORM1 : {
			items : [
				{type:"block",name:"BODY", list:[
						{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
						,{type: "fieldset", label: "신청서정보", name:"REQUEST", inputWidth:900, list:[
								,{type:"block", list:[
										{type:"hidden", name:"serviceType"}
										,{type:"select", label:"신청유형", name:"operType", width:100, required:true, validate:'NotEmpty'}
										,{type:"newcolumn"}
										,{type:"select", label:"고객구분", name:"cstmrType", width:100, required:true, offsetLeft:10, validate:'NotEmpty'}
										,{type:"newcolumn"}
										,{type:"select", label:"구매유형", name:"reqBuyType", width:100, required:true, offsetLeft:10}
										,{type:"newcolumn"}
										,{type:"select", label:"신청서상태", name:"pstate", width:90, offsetLeft:10, validate:'NotEmpty'}
										,{type:"newcolumn"}
										,{type:"select", label:"진행상태", name:"requestStateCode", width:100, offsetLeft:10, validate:'NotEmpty'}
									]}
								,{type:"block", list:[
										{type:"calendar", label:"신청일자", name:"reqInDay", width:100, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d"}
										,{type:"hidden", name:"reqInDayStr"}
										,{type:"newcolumn"}
										,{type:"select", label:"상품구분", name:"prodType", width:100, offsetLeft:10, required:true, disabled:true}
										,{type:"newcolumn"}
										,{type:"hidden", label:"", name:"pAgentCode", value:sOrgnId}
										,{type:"input", label:"대리점", name:"pAgentName", value:sOrgnNm, width:122, offsetLeft:10, readonly: true, validate:'NotEmpty', required:true}
										,{type:"newcolumn"}
										,{type:"button", value:"찾기", name:"btnOrgFind", disabled:isCntpShop}
										,{type:"newcolumn"}
										,{type:"hidden", label:"", name:"pShopCode", value:sShopId}
										,{type:"input", label:"판매점", name:"shopNm", value:sShopNm, width:122, offsetLeft:10, readonly: true, validate:'NotEmpty', required:true}
										,{type:"newcolumn"}
										,{type:"button", value:"찾기", name:"btnShopFind"}
									]}
								,{type:"block", name : "AUTH", list:[
										{type:"select", label:"본인인증방식", name:"onlineAuthType", width:150, validate:'NotEmpty', value:"", labelWidth:80}
										,{type:"newcolumn"}
										,{type:"input", label:"본인인증정보", name:"onlineAuthInfo", width:242, offsetLeft:24, readonly: false, value:"", labelWidth:80}
										,{type:"newcolumn"}
										,{type:"select", label:"모집경로", name:"onOffType", width:100, offsetLeft:17}
										,{type:"hidden", label:"매니저코드", name:"managerCode", width:130, offsetLeft:20, readonly: false, value:""}
									]}
								,{type:"block", list:[
										{type:"input", label:"예약번호", name:"resNo", width:100, readonly: false, value:""}
										,{type:"newcolumn"}
										,{type:"select", label:"서식지", name:"faxyn", width:100, offsetLeft:10, value:""}
										,{type:"newcolumn"}
										,{type:"block", name:"faxnum", offsetLeft:10, list:[
												{type:"select", label:"팩스번호", name:"faxnum1", width:60, labelWidth:50}
												,{type:"newcolumn"}
												,{type:"input", label:"-", name:"faxnum2", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
												,{type:"newcolumn"}
												,{type:"input", label:"-", name:"faxnum3", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
											]}
										,{type:"newcolumn"}
										,{type:"button", value:"스캔하기", name: "btnRegPaper", offsetLeft:0}
										,{type:"newcolumn"}
										,{type:"button", value:"스캔검색", name: "btnScnSarch", offsetLeft:0}
										,{type:"newcolumn"}
										,{type:"button", value:"녹취파일등록", name: "btnRecSarch", offsetLeft:0}
										,{type:"newcolumn"}
										,{type:"button", value:"K-Note조회", name: "btnKnote", offsetLeft:0}
										,{type:"newcolumn"}
										,{type:"button", value:"URL검색", name: "btnFaxSarch", offsetLeft:0}
										,{type:"newcolumn"}
										,{type:"button", value:"서식지 보기", name: "btnPaper", offsetLeft:0}
										,{type:"newcolumn"}
										,{type:"input", label:"배송방법", name:"dlvryType", width:90, disabled: true, offsetLeft:10}
										,{type:"newcolumn"}
										,{type:"input", label:"이벤트코드", name:"evntCdPrmt", width:90, disabled: true, offsetLeft:10}
										,{type:"newcolumn"}
										,{type:"input", label:"실 판매점", name: "realShopNm", width:90, offsetLeft:10, maxLength:25}
										,{type:"hidden", name:"scanId"} // 등록시에 새로 생성하되, 스캔검색을 하게 되면 검색창에서 넘오는 값으로 셋팅 되어야 한다.
										,{type:"hidden", name:"oriScanId"} // MCP_REQUEST에 등록된 scanId, 위의 scanId를 계속 변경해서 사용하고 있어서 미리 셋팅함.
										,{type:"hidden", name:"shopCd"} //판매점 ID : 스캔검색에서 전달 받는다.
										,{type:"hidden", name:"scanType"}//서식지 등록과 상세정보 구분
										,{type:"hidden", name:"btnType"}
										,{type:"hidden", name:"scanIdBySearch"}
										,{type:"hidden", name:"shopCdBySearch"}
										,{type:"hidden", name:"faxBySearch"}
									]}
							]}	//신청서정보 끝

						,{type: "fieldset", label: "판매정보(핸드폰대금)", inputWidth:900, name:"SALE", list:[
								,{type:"block", list:[
										{type:"input", label:"단말기일련번호", name:"reqPhoneSn", labelWidth:90, width:120, maxLength:30}
										,{type:"newcolumn"}
// 							,{type:"button", value: "재고확인", name:"btnCheckSn"}
// 							,{type:"hidden", name:"checkSn", value:"N"}
										,{type:"hidden", name:"prdtSrlNoOrgnId"}
										,{type:"newcolumn"}
										,{type:"select", label:"요금제", name:"socCode", labelWidth:40, width:220, offsetLeft:10, validate:'NotEmpty'}
										,{type:"newcolumn"}
										,{type :"select", label:"약정기간", name :"agrmTrm", labelWidth:50, width:80, offsetLeft:10, validate:'NotEmpty'}
										,{type:"newcolumn"}
										,{type:"select", label:"할부기간", name:"instNom", labelWidth:50, width:80, offsetLeft:10, validate:'NotEmpty'}
									]}
								,{type:"block", list:[
										{type:"input", label:"판매정책", name:"salePlcyCd", labelWidth:90, width:120, value:lbl, readonly:true}
										,{type:"newcolumn"}
										,{type:"input", label:"", name:"salePlcyNm", width:300, offsetLeft:0, labelAlign:"right", readonly:true }
										,{type:"select", label:"", name:"salePlcyCdList", width:300, offsetLeft:0, labelAlign:"right"}
										,{type:"hidden", name:"cntpntShopId"}
										,{type:"hidden", name:"modelId"}
										,{type:"hidden", name:"recycleYn"}
										,{type:"hidden", name:"modelDiscount1"}
										,{type:"hidden", name:"joinPrice"}
										,{type:"hidden", name:"usimPrice"}
										,{type:"hidden", name:"modelPrice"}
										,{type:"hidden", name:"modelPriceVat"}
										,{type:"hidden", name:"reqUsimName"}
										,{type:"newcolumn"}
										,{type:"button", value:"정책선택", name: "btnPlcFind"}
										,{type:"button", value:"다시선택", name: "btnPlcFind2"}
										,{type:"newcolumn"}
										,{type:"input", label:"휴대폰모델명", name:"reqModelName", labelWidth:90, width:144, readonly:true, disabled:true, offsetLeft:20}
									]}
								,{type:"block", list:[
										{type:"select", label:"제조사", name:"mnfctId", labelWidth:90, width:120}
										,{type:"newcolumn"}
										,{type:"select", label:"단말모델", name:"prdtId", labelWidth:60, width:100, offsetLeft:10, validate:'NotEmpty'}
										,{type:"newcolumn"}
										,{type:"input", label:"단말코드", name:"prdtCode", labelWidth:60, width:210, offsetLeft:10, readonly:true }
										,{type:"newcolumn"}
										,{type:"select", label:"단말색상", name:"reqModelColor", labelWidth:60, width:104, offsetLeft:10}
									]}
								,{type:"block", list:[
										{type:"input", label:"출고가", name:"hndstAmt", labelWidth:90, width:80, readonly:true, numberFormat:"0,000,000,000",  value:""}
										,{type:"newcolumn"}
										,{type:"input", label:"공시지원금", name:"modelDiscount2", labelWidth:80, width:70, offsetLeft:20, readonly:true, numberFormat:"0,000,000,000", validate:"ValidInteger", value:""}
										,{type:"newcolumn"}
										,{type:"input", label:"추가지원금(MAX)", name:"maxDiscount3", labelWidth:100, width:70, offsetLeft:10, readonly: true, numberFormat:"0,000,000,000", validate:"ValidInteger", value:""}
										,{type:"newcolumn"}
										,{type:"input", label:"(적용)", name:"modelDiscount3", labelWidth:40, width:75, offsetLeft:21, numberFormat:"0,000,000,000", validate:"ValidInteger", maxLength: 10, value:""}
										,{type:"newcolumn"}
										,{type :"select", label:"할인유형", name:"sprtTp", labelWidth:60, width:104, offsetLeft:10}
									]}
								,{type:"block", list:[
										{type:"input", label:"기본할인금액", name:"dcAmt", labelWidth:90, width:80, readonly: true, numberFormat:"0,000,000,000", validate:"ValidInteger", maxLength:10, value:""}
										,{type:"newcolumn"}
										,{type:"input", label:"추가할인금액", name:"addDcAmt", labelWidth:80, width:70, offsetLeft:20, readonly: true, numberFormat:"0,000,000,000", validate:"ValidInteger", maxLength:10, value:""}
										,{type:"newcolumn"}
										,{type:"input", label:"할부원금", name:"modelInstallment", labelWidth:70, width:70, offsetLeft:10, readonly:true, numberFormat:"0,000,000,000", value:""}
										,{type:"hidden", label:"실구매가", name:"realMdlInstamt", labelWidth:45, width:85, numberFormat:"0,000,000,000", validate:"ValidInteger", maxLength:10, value:""}
									]}
								,{type:"block", list:[
										,{type:"input", label:"포인트사용금액", name:"usePointAmt", labelWidth:90, width:120, readonly:true, numberFormat:"0,000,000,000", value:"", disabled:true}
									]}
								,{type:"block", list:[
										{type:"input", label:"유심일련번호", name:"reqUsimSn", labelWidth:90, width:150, maxLength:19 , validate:"ValidNumeric"}
										,{type:"newcolumn"}
										,{type:"select", label:"USIM구매", name:"usimPayMthdCd", labelWidth:80, width:80, offsetLeft:45, labelAlign:"right", required:true, validate:'NotEmpty', value:""}
										,{type:"newcolumn"}
										,{type:"select", label:"유심종류", name:"usimKindsCd", labelWidth:55, width:100, offsetLeft:90}

									]}
								,{type:"block", list:[
										{type:"input", label:"단말모델ID", name:"intmMdlId", labelWidth:90, width:150, maxLength:20}
										,{type:"newcolumn"}
										,{type:"input", label:"단말일련번호", name:"intmSrlNo",labelWidth:90, width:150, offsetLeft:60, maxLength:30}
										,{type:"newcolumn"}
										,{type:"input", label:"단말모델명", name:"eReqModelName",labelWidth:90, width:150, offsetLeft:20, maxLength:50, readonly:true, disabled:true}
									]}
								,{type:"block", list:[
										{type:"input", label:"EID", name:"eid", labelWidth:90, width:200, maxLength:32}
										,{type:"newcolumn"}
										,{type:"input", label:"IMEI1", name:"imei1", labelWidth:90 , width:150, offsetLeft:10, maxLength:15}
										,{type:"newcolumn"}
										,{type:"input", label:"IMEI2", name:"imei2", labelWidth:90 , width:150, offsetLeft:20, maxLength:15}
									]}
								,{type:"block", list:[
										{type:"select", label:"모회선", name:"prntsCtnFn", labelWidth:90, width:75}
										,{type:"newcolumn"}
										,{type:"input", label:"-", name:"prntsCtnMn", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
										,{type:"newcolumn"}
										,{type:"input", label:"-", name:"prntsCtnRn", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
										,{type:"newcolumn"}
										,{type:"input", label:"청구계정ID", name:"billAcntNo", labelWidth:90, width:150, offsetLeft:23, maxLength:20}
										,{type:"newcolumn"}
										,{type:"input", label:"모회선계약번호", name:"prntsContractNo", labelWidth:90, width:150, offsetLeft:20, maxLength:10}
									]}
								,{type:"block", list:[
										{type:"select", label:"특별판매코드", name:"spcCode", labelWidth:90, width:200}
										,{type:"newcolumn"}
										,{type:"input", label:"메모사항", name:"requestMemo", width:482, offsetLeft:10, maxLength:200}
									]}
								/* 프로모션  */
								,{type:"block", list:[
										,{type:"input", label:"평생할인프로모션ID", name:"disPrmtId", labelWidth:110, width:130, offsetLeft:0, readonly:true, disabled:true}
										,{type:"newcolumn"}
										,{type:"input", label:"평생할인프로모션명", name:"disPrmtNm", labelWidth:115, width:285, offsetLeft:5, readonly:true, disabled:true}
										,{type:"newcolumn"}
										,{type:"input", label:"총할인금액", name:"totalDiscount", width:120,  numberFormat:"0,000,000,000", validate:"ValidInteger", readonly:true, offsetLeft:10, disabled:true}
									]}
								,{type:"block", list:[
										{type:"select", label:"단체상해보험", name:"insrCd", labelWidth:90, width:150}
										,{type:"newcolumn"}
										,{type:"select", label:"단말보험", name:"insrProdCd", width:190, offsetLeft:60}
										,{type:"newcolumn"}
										,{type:"checkbox", width:50, label:"단말보험가입동의", labelWidth:200, position:"label-right", name:"clauseInsrProdFlag", value:"Y", checked:false, offsetLeft:30}
									]}
								,{type:"block", list:[
										{type:"select", label:"자급제 보상서비스", name:"rwdProdCd", labelWidth:110, width:190, disabled:true}
										,{type:"newcolumn"}
										,{type:"checkbox", width:50, label:"가입동의", labelWidth:90, position:"label-right", name:"clauseRwdFlag", value:"Y", checked:false, offsetLeft:10, disabled:true}
									]}
							]}//판매정보 끝
						// 2020-03-07 자급제 정보 추가 - 장익준
						,{type: "fieldset", label: "자급제정보", inputWidth:900, name:"SELPHONE", list:[
								,{type:"block", list:[
										{type:"select", label:"진행상태", name:"phoneStateCd", labelWidth:90, width:130}
										,{type:"newcolumn"}
										,{type:"input", label: "결재금액", name:"lastAmt", readonly:true, labelWidth:80, width:130, offsetLeft:40, numberFormat:"0,000,000,000", validate:"ValidInteger"}
									]}
								,{type:"block", list:[
										{type:"input", label:"단말모델", name:"sesplsPrdtNm", readonly:true, labelWidth:90, width:130}
										,{type:"newcolumn"}
										,{type:"input", label:"단말코드", name:"sesplsProdId", readonly:true, labelWidth:80, width:130, offsetLeft:40}
										,{type:"newcolumn"}
										,{type:"input", label:"단말색상", name:"sesplsProdCol", readonly:true, labelWidth:80, width:130, offsetLeft:40}
									]}
								,{type:"block", list:[
										{type:"input", label:"출고가", name:"hndsetSalePrice", readonly:true, labelWidth:90, width:100, numberFormat:"0,000,000,000", validate:"ValidInteger"}
										,{type:"newcolumn"}
										,{type:"input", label:"즉시할인", name:"cardTotDcAmt", readonly:true, labelWidth:70, width:100, offsetLeft:20,numberFormat:"0,000,000,000", validate:"ValidInteger"}
										,{type:"newcolumn"}
										,{type:"input", label:"포인트", name:"usePoint", readonly:true, labelWidth:70, width:100, offsetLeft:20, numberFormat:"0,000,000,000", validate:"ValidInteger"}
										,{type:"newcolumn"}
										,{type:"input", label:"포인트회선", name:"subscriberNo", readonly:true, labelWidth:80, width:120, offsetLeft:20}
									]}
							]}
						,{type: "fieldset", label: "고객정보", inputWidth:900, name:"REQINFO", list:[
								,{type:"block", list:[
										{type:"input", label:"고객명", name:"cstmrName", width:200, maxLength:60, validate:'NotEmpty'}
										,{type:"newcolumn"}
										,{type:"input", label:"주민번호", name:"cstmrNativeRrn1", width:80, offsetLeft:42, maxLength:6, validate:'ValidNumeric'}
										,{type:"newcolumn"}
										,{type:"input", label:"-", name:"cstmrNativeRrn2", labelWidth:15, width:80, labelAlign:"center", maxLength:7, validate:'ValidNumeric'}
										,{type:"newcolumn"}
										,{type:"button", value: "개통이력", name:"btnCheckCstmr"}
										,{type:"hidden", name:"checkCstmr", value:"N"}
									]}
								,{type:"block",name:"kNote", list:[
										,{type:"hidden", label:"서식지ID", name:"frmpapId", width:200, maxLength:60  /*,disabled: true*/}
										,{type:"newcolumn"}
										,{type:"hidden", label:"대리점코드", name:"mngmAgncId", width:150, offsetLeft:42, /*,disabled: true*/}
										,{type:"newcolumn"}
										,{type:"hidden", label:"접점코드", name:"cntpntCd", width:150, offsetLeft:10, /*,disabled: true*/}
									]}
								,{type:"block", list:[
										{type:"label", label:"본인조회동의", name:"selfInqryAgrmYnLabel", labelWidth:80}
										,{type:"newcolumn"}
										,{type:"checkbox", label:"동의", name:"selfInqryAgrmYn", width:100, value:"Y", position:"label-right"}
										,{type:"newcolumn"}
										,{type:"select", label:"본인인증방식", name:"selfCertType", labelWidth:90, width:130, offsetLeft:30}
										,{type:"newcolumn"}
										,{type:"input", label:"발급일자", name:"selfIssuExprDt", width:80, offsetLeft:30, maxLength:8}
										,{type:"newcolumn"}
										,{type:"input", label:"발급번호", name:"selfIssuNum", width:100, maxLength:50, offsetLeft:20}
									]}
								,{type:"block", name : "FATH", list:[
										{type:"checkbox", label:"안면인증대상", name:"fathTrgYn",labelWidth:80, width:50, value:"Y", position:"label-left", disabled:true}
										,{type:"newcolumn"}
										,{type:"button", value: "조회", name:"btnFT0"}
										,{type:"newcolumn"}
										,{type:"checkbox", width:50, labelWidth:80, label:"안면인증동의", offsetLeft:25, position:"label-left", name:"clauseFathFlag",value:"Y", disabled:true, checked:false}
										,{type:"newcolumn"}
										,{type:"input", label:"안면인증", name:"fathTransacId", width:130, offsetLeft:10, labelWidth:80, disabled:true}
										,{type:"newcolumn"}
										,{type:"button", value: "URL요청", name:"btnFS8"}
										,{type:"newcolumn"}
										,{type:"input", label:"안면인증일", name:"fathCmpltNtfyDt", width:80, offsetLeft:10, disabled:true}
										,{type:"newcolumn"}
										,{type:"button", value: "결과확인", name:"btnReqFathTxnRetv"}
										,{type:"newcolumn"}
										,{type:"select", label:"안면인증연락처", name:"fathMobileFn", width:70,labelWidth:85}
										,{type:"newcolumn"}
										,{type:"input", label:"-", name:"fathMobileMn", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
										,{type:"newcolumn"}
										,{type:"input", label:"-", name:"fathMobileRn", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
										,{type:"newcolumn"}
										,{type:"button", value: "셀프URL", name:"btnFath", offsetLeft:10}
										,{type:"newcolumn"}
										,{type:"button", value: "안면인증SKIP", name:"btnFT1", offsetLeft:385, hidden:${!isEnabledFT1}}
									]}
								,{type:"block", list:[
										{type:"select", label:"연락처", name:"cstmrTelFn", width:75}
										,{type:"newcolumn"}
										,{type:"input", label:"-", name:"cstmrTelMn", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
										,{type:"newcolumn"}
										,{type:"input", label:"-", name:"cstmrTelRn", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
										,{type:"newcolumn"}
										,{type:"checkbox", label:"배송정보입력", name:"dlvryYn", labelWidth:100, width:100, position:"label-right"}
										,{type:"newcolumn"}
										,{type:"select", label:"휴대폰", name:"cstmrMobileFn", width:75, offsetLeft:20}
										,{type:"newcolumn"}
										,{type:"input", label:"-", name:"cstmrMobileMn", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
										,{type:"newcolumn"}
										,{type:"input", label:"-", name:"cstmrMobileRn", labelWidth:10, width:40, labelAlign:"center", maxLength:4}
									]}
								,{type:"block", list:[
										{type:"input", label:"주소", name:"cstmrPost", width:75, readonly:true, maxLength:6}
										,{type:"newcolumn"}
										,{type:"button", value:"우편번호찾기", name:"btnCstmrPostFind"}
										,{type:"newcolumn"}
										,{type:"input", label:"", name:"cstmrAddr", labelWidth:0, width:609, readonly:true, maxLength:83}
										,{type:"newcolumn"}
									]},
								,{type:"block", list:[
										{type:"input", label:"상세주소", name:"cstmrAddrDtl", width:786, maxLength:83}
									]}
								,{type:"block", list:[
										{type:"select", label:"명세서수신유형", name:"cstmrBillSendCode", labelWidth:90, width:120}
										,{type:"newcolumn"}
										,{type:"input", label:"메일", name:"cstmrMail1", labelWidth:30, width:111, offsetLeft:10, maxLength:60}
										,{type:"newcolumn"}
										,{type:"input", label:"@",name:"cstmrMail2", labelWidth:10, width:110, offsetLeft:0, labelAlign:"center", maxLength:39}
										,{type:"newcolumn"}
										,{type:"select", name:"cstmrMail3", width:100, offsetLeft:0}
										,{type:"newcolumn"}
										,{type:"label", label:"수신여부", name:"cstmrMailReceiveFlagLabel", labelWidth:70, offsetLeft:10}
										,{type:"newcolumn"}
										,{type:"checkbox", label:"동의", name:"cstmrMailReceiveFlag", width:130, offsetLeft:10, value:"Y", position:"label-right"}
									]}
							]}

						,{type:"fieldset", label:"배송정보", inputWidth:900, name:"DLVRY", list:[
								{type:"settings"}
								,{type:"block", list:[
										{type:"input", width:75, name:"dlvryName", label:"받으시는 분" , labelWidth:70, maxLength:20}
										,{type:"newcolumn"}
										,{type:"checkbox", width:40, label:"고객상동",name:"cloneBase", position:"label-right", labelAlign:"left", labelWidth:60,checked:false}
										,{type:"newcolumn"}
										,{type:"select", width:75, label:"전화번호", name:"dlvryTelFn",offsetLeft:10}
										,{type:"newcolumn"}
										,{type:"input", width:40, label:"",name:"dlvryTelMn",maxLength:4}
										,{type:"newcolumn"}
										,{type:"input", width:40, label:"",name:"dlvryTelRn",maxLength:4}
										,{type:"newcolumn"}
										,{type:"select", width:75, label:"휴대폰",name:"dlvryMobileFn",offsetLeft:10,labelAlign:"right", labelWidth:97}
										,{type:"newcolumn"}
										,{type:"input", width:40, label:"",name:"dlvryMobileMn", maxLength:4}
										,{type:"newcolumn"}
										,{type:"input", width:40, label:"",name:"dlvryMobileRn", maxLength:4}
									]}
								,{type:"block", list:[
										{type:"input", label:"우편번호", name:"dlvryPost", labelWidth:70, width:75, readonly:true, maxLength:6}
										,{type:"newcolumn"}
										,{type:"button", value: "주소찾기", name :"btnDlvryPostFind"}
										,{type:"newcolumn"}
										,{type:"input", label:"", name:"dlvryAddr", width:623, readonly:true, maxLength:80}
									]}
								,{type:"block", list:[
										{type:"input", label:"상세주소", name:"dlvryAddrDtl", labelWidth:70, width:500, maxLength:50}
										,{type:"newcolumn"}
										,{type:"select", label:"택배사", name:"tbCd", labelWidth:60, width:88, offsetLeft:20, disabled:true}
										,{type:"newcolumn"}
										,{type:"input", label:"", name:"dlvryNo", width:100, maxLength:20, readonly:true}
									]}
								,{type:"block", list:[
										{type:"input", width:776, label:"요청사항",name:"dlvryMemo", labelWidth:70, maxLength:200}
									]}
							]}	//배송정보 끝

						//외국인정보
						,{type: "fieldset", label: "외국인정보", name:"FOREIGNER", inputWidth:900, list:[
								,{type:"block", list:[
										,{type:"input", width:60, label:"외국인등록번호", labelWidth:90, name:"cstmrForeignerRrn1", maxLength:6, validate:'ValidNumeric'}
										,{type:"newcolumn"}
										,{type:"input", width:80, label:"-",name:"cstmrForeignerRrn2",labelAlign:"center",labelWidth:15, maxLength:7, offsetLeft:5}
										,{type:"newcolumn"}
										,{type:"select", width:120, label:"국적", labelWidth:30, name:"cstmrForeignerNationSel", offsetLeft:30}
										,{type:"hidden", label:"", name:"cstmrForeignerNation"}
										,{type:"newcolumn"}
										,{type:"button", value: "검색", name:"btnNatnFind"}
										,{type:"newcolumn"}
										,{type:"input", width:130, label:"", labelWidth:80, name:"cstmrForeignerNationTxt", readonly:true}
										,{type:"newcolumn"}
										,{type:"input", width:130, label:"여권번호",name:"cstmrForeignerPn", maxLength:83, offsetLeft:30}
									]}
								,{type:"block", list:[
										{type:"calendar", width:100, label:"체류기간", labelWidth:90, name:"cstmrForeignerSdate", maxLength:10, readonly:true}
										,{type:"newcolumn"}
										,{type:"calendar", label:"~",name:"cstmrForeignerEdate", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100,maxLength:10, readonly:true}
										,{type:"newcolumn"}
										,{type:"input", width:130, label:"사회보장번호", labelWidth:80, name:"cstmrForeignerDod", maxLength:83, validate:'ValidAplhaNumeric', offsetLeft:15}
										,{type:"newcolumn"}
										,{type:"calendar", width:100, label:"생년월일", name:"cstmrForeignerBirth", maxLength:10, readonly:true, offsetLeft:50}
									]}
								,{type:"block", list:[
										{type:"select", label:"VISA 코드", name:"visaCd", labelWidth:90, width:120}
									]}
							]}

						// 납부방법
						,{type: "fieldset", label: "요금납부정보", name:"PAYTYPE1", inputWidth:900, list:[
								{type:"settings"}
								,{type:"select", width:130, label:"요금납부방법", labelWidth:80, name:"reqPayType"}
								,{type:"newcolumn"}
							]}

						// 카드정보
						,{type: "fieldset", label: "카드정보", name:"CARD", inputWidth:900, list:[
								{type:"block", list:[
										{type:"select", width:103, label:"카드정보",name:"reqCardCompany"}
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
										{type:"checkbox", width:130, label:"타인납부", name:"reqPayOtherFlag", position:"label-left", checked:false, value:"Y"}
										,{type:"newcolumn"}
										,{type:"input", width:130, label:"명의자정보", name:"reqCardName", maxLength:20, offsetLeft: 21}
										,{type:"newcolumn"}
										,{type:"input", width:60, labelWidth:90, label:"명의자주민번호", name:"reqCardRrn1", offsetLeft: 55, maxLength:6}
										,{type:"newcolumn"}
										,{type:"input", width:80, label:"-",name:"reqCardRrn2",labelAlign:"center",labelWidth:5 ,maxLength:7}
										,{type:"newcolumn"}
										,{type:"checkbox", width:40, label:"고객상동",name:"cloneBaseCard", position:"label-right", labelAlign:"left", labelWidth:60, checked:false, offsetLeft: 30}
									]}
							]}

						// 자동이체
						,{type: "fieldset", label: "은행정보", name:"BANK", inputWidth:900, list:[
								{type:"block", list:[
										{type:"select", width:130, label:"은행정보",name:"reqBank"}
										,{type:"newcolumn"}
										,{type:"input", width:150, label:"", name:"reqAccountNumber", maxLength:16}

									]},
								{type:"block", list:[
										{type:"checkbox", width:130, label:"타인납부", name:"othersPaymentAg", position:"label-left", checked:false, value:"Y"}
										,{type:"newcolumn"}
										,{type:"input", width:130, label:"예금주", name:"reqAccountName", maxLength:20, offsetLeft: 10}
										,{type:"newcolumn"}
										,{type:"input", width:60, labelWidth:90, label:"명의자주민번호", name:"reqAccountRrn1", offsetLeft: 80, maxLength:6}
										,{type:"newcolumn"}
										,{type:"input", width:80, label:"-",name:"reqAccountRrn2",labelAlign:"center",labelWidth:5,maxLength:7}
										,{type:"newcolumn"}
										,{type:"checkbox", width:40, label:"고객상동",name:"cloneBaseBank", position:"label-right", labelAlign:"left", labelWidth:60, checked:false, offsetLeft: 30}
									]}
							]}

						// 이용신청내용
						,{type: "fieldset", label: "이용신청내용", name:"REQADD", inputWidth:900, list:[
								{type:"settings"}
								,{type:"block", list:[
										{type:"input", label:"부가서비스명", name:"additionName", width:680, labelWidth:80, readonly:true}
										,{type:"hidden", label:"부가서비스", name:"additionKey"}
										,{type:"hidden", label:"부가서비스가격", name:"additionPrice"}
										,{type:"newcolumn"}
										,{type:"button", value: "부가서비스", name:"btnRateFind"}
									]}
								,{type:"block", list:[
										{type:"label", label:"휴대폰결제", name:"phonePaymentLabel", position:"label-right", list:[
												{type:"settings", labelWidth:35, offsetLeft:5, position:"label-right"}
												,{type:"radio", name:"phonePayment", value:"Y", label:"이용", checked:true, offsetLeft:20}
												,{type:"newcolumn"}
												,{type:"radio", name:"phonePayment", value:"N", label:"차단"}
											]}
										,{type:"newcolumn"}
										,{type:"select", label:"가입비", name:"joinPayMthdCd", labelWidth:60, width:100, offsetLeft:20, labelAlign:"right", required:true, validate:'NotEmpty', value:""}
									]}
								,{type:"block", name:"combineSoloBlock", list:[
										{type:"label", label:"아무나 SOLO(법인결합신청)", labelWidth:155, name:"combineSoloTypeLabel", position:"label-right", list:[
												{type:"settings", labelWidth:35, offsetLeft:5, position:"label-right"}
												,{type:"radio", name:"combineSoloType", value:"Y", label:"신청", offsetLeft:10}
												,{type:"newcolumn"}
												,{type:"radio", name:"combineSoloType", labelWidth:50, value:"N", label:"미신청", checked: true}
											]}
										,{type:"newcolumn"}
										,{type:"checkbox", label:"가입동의", name:"combineSoloFlag", labelWidth:55, width:4, position:"label-right", labelAlign:"left", disabled: true}
									]}
							]}

						// 신규
						,{type: "fieldset", label: "신규가입", name:"NEWREQ", inputWidth:900, list:[
								{type:"settings"}
								,{type:"block", list:[
										{type: "label", label: "가입희망번호", name:"reqWantNumberLabel", labelWidth: 80},
										,{type:"newcolumn"}
										,{type:"input", width:40, label:"1)", name:"reqWantNumber", labelWidth: 13, maxLength:4, validate:'ValidNumeric', offsetLeft: 0}
										,{type:"newcolumn"}
										,{type:"input", width:40, label:"2)", name:"reqWantNumber2", labelWidth: 13, maxLength:4, validate:'ValidNumeric', offsetLeft: 10}
										,{type:"newcolumn"}
										,{type:"input", width:40, label:"3)", name:"reqWantNumber3", labelWidth: 13, maxLength:4, validate:'ValidNumeric', offsetLeft: 10}
										,{type:"newcolumn"}
										,{type:"checkbox",width:10, label:"번호연결서비스",name:"reqGuideFlag", position:"label-left", checked:false, value:"N", labelWidth: 90, offsetLeft:10}
										,{type:"newcolumn"}
										,{type:"select", width:75, label:"",name:"reqGuideFn", labelAlign:"right", labelWidth:98}
										,{type:"newcolumn"}
										,{type:"input", width:40, label:"-", labelAlign:"center", labelWidth: 10, name:"reqGuideMn", maxLength:4}
										,{type:"newcolumn"}
										,{type:"input", width:40, label:"-", labelAlign:"center", labelWidth: 10, name:"reqGuideRn", maxLength:4}
										,{type:"newcolumn"}
										,{type:"input", width:100, label:"번호예약", name:"tlphNo", labelWidth: 60, maxLength:12, validate:'ValidNumeric', offsetLeft:20, readonly:true}
										,{type:"newcolumn"}
										,{type:"button", value: "번호조회", name:"btnInqrTlphNo"}
										,{type:"button", value: "예약취소", name:"btnCnclTlphNo"}
									]}
							]}

						// 번호이동
						,{type: "fieldset", label: "번호이동정보", inputWidth:900, name:"REQMOVE", list:[
								{type:"settings"}
								,{type:"block", list:[
										{type:"input", width:40, label:"이동번호", name:"moveMobileFn" , readonly : true}
										,{type:"newcolumn"}
										,{type:"input", width:40, label:"-", labelAlign:"center", labelWidth: 10, name:"moveMobileMn", maxLength:4}
										,{type:"newcolumn"}
										,{type:"input", width:40, label:"-", labelAlign:"center", labelWidth: 10, name:"moveMobileRn", maxLength:4}
										,{type:"newcolumn"}
										,{type:"select", width:130, label:"이동전통신사", name:"moveCompany", labelWidth: 80, labelAlign:"left", offsetLeft:50}
										,{type:"newcolumn"}
										,{type:"select", width:150, label:"인증유형", labelWidth:56, name:"moveAuthType", offsetLeft:43}
										,{type:"newcolumn"}
										,{type:"input", width:50,name:"moveAuthNumber", label:"", maxLength:4}
									]},
								,{type:"block", list:[
										{type:"select", width:123, label:"번호이동전 사용요금", name:"moveThismonthPayType", labelWidth: 120}
										,{type:"newcolumn"}
										,{type:"select", width:130, label:"휴대폰 할부금",name:"moveAllotmentStat", labelWidth: 80, offsetLeft: 50}
										,{type:"newcolumn"}
										,{type: "label", label:"미환급액상계", name:"moveRefundAgreeFlagLabel", labelWidth:90, position:"label-right", offsetLeft:44, list:[
												{type:"settings",offsetLeft:3, position:"label-left", labelWidth:35}
												,{type: "radio", name: "moveRefundAgreeFlag", value: "Y", label: "동의", labelWidth:30}
												,{type:"newcolumn"}
												,{type: "radio", name: "moveRefundAgreeFlag", value: "N", label: "미동의", labelWidth:40}

											]}
									]}
								  // 위약금 항목 추가
								 ,{type:"block", list:[
										{type:"input", width:90, label:"위약금", name:"movePenalty", labelWidth:60, numberFormat:"0,000,000,000", validate:"ValidInteger", maxLength:8}
									]}
							]}

						// 약관동의
						,{type: "fieldset", label: "약관동의정보", name:"TERMS", inputWidth:900, list:[
								{type:"settings"}
								,{type:"block", list:[
										{type: "label", label: "고객 혜택 제공을 위한 정보수집 이용동의 및 혜택 광고의 수신 위탁 동의",name:"personalAgreeTitle", labelWidth:400},
										{type:"newcolumn"},
										{type: "label", label: "혜택제공을 위한 제 3자 제공 광고 수신 동의", name:"othersAgreeTitle", labelWidth:300, offsetLeft:50}
									]},
								,{type:"block", list:[
										{type:"select", width:71, name:"personalInfoCollectAgree", label:"고객 혜택 제공을 위한 개인정보 수집 및 관련 동의", labelWidth:295, offsetLeft: 20, required:true, options:[
												{value:"", text:"- 선택 -"}
												, {value:"Y", text:"예"}
												, {value:"N", text:"아니오"}]},
										{type:"newcolumn"},
										{type:"select", width:71, name:"othersTrnsAgree", label:"혜택 제공을 위한 제 3자 제공 동의 : M모바일", labelWidth:280, offsetLeft: 80, required:true, options:[
												{value:"", text:"- 선택 -"}
												, {value:"Y", text:"예"}
												, {value:"N", text:"아니오"}]}
									]}
								,{type:"block", list:[
										{type:"select", width:71, name:"clausePriAdFlag", label:"개인정보 처리 위탁 및 혜택 제공 동의", labelWidth:295, offsetLeft: 20, required:true, options:[
												{value:"", text:"- 선택 -"}
												, {value:"Y", text:"예"}
												, {value:"N", text:"아니오"}]},
										{type:"newcolumn"},
										{type:"select", width:71, name:"othersTrnsKtAgree", label:"혜택 제공을 위한 제 3자 제공 동의 : KT", labelWidth:280, offsetLeft: 80, required:true, options:[
												{value:"", text:"- 선택 -"}
												, {value:"Y", text:"예"}
												, {value:"N", text:"아니오"}]}
									]}
								,{type:"block", list:[
										{type:"select", width:71, name:"othersAdReceiveAgree", label:"제 3자 제공관련 광고 수신 동의", labelWidth:280, offsetLeft: 472, required:true, options:[
												{value:"", text:"- 선택 -"}
												, {value:"Y", text:"예"}
												, {value:"N", text:"아니오"}]}
									]}
								,{type:"block", list:[
										{type:"checkbox", width:50, labelWidth:190, label:"고유식별정보 수집이용제공동의", position:"label-right",name:"clauseEssCollectFlag",value:"Y",checked:false}
										,{type:"newcolumn"}
										,{type:"checkbox", width:90,labelWidth:170, label:"개인/신용정보 수집동의", position:"label-right", name:"clausePriCollectFlag",value:"Y",checked:false}
										,{type:"newcolumn"}
										,{type:"checkbox", width:50, labelWidth:160, label:"개인정보 제3자 제공동의", position:"label-right", name:"clausePriOfferFlag",value:"Y",checked:false}
										,{type:"newcolumn"}
										,{type:"checkbox", width:50,labelWidth:150, label:"단체보험가입동의", position:"label-right",name:"clauseInsuranceFlag", value:"Y", checked:false}
										,{type: "hidden", name: "clausePriTrustFlag", label: "개인정보제휴제공동의여부" , value:"N"}
									]}
								,{type:"block", list:[
										{type:"checkbox", width:50,labelWidth:190, label:"민감정보 수집이용동의", position:"label-right", name:"clauseSensiCollectFlag", value:"Y", checked:false}
										,{type:"newcolumn"}
										,{type:"checkbox", width:50,labelWidth:170, label:"민감정보 제3자 제공동의", position:"label-right",name:"clauseSensiOfferFlag",value:"Y",checked:false}
										,{type:"newcolumn"}
										,{type:"checkbox", width:50,labelWidth:130, label:"제휴서비스를 위한동의", position:"label-right", name:"clauseJehuFlag", value:"Y", checked:false}
										,{type:"newcolumn"}
										,{type:"select", width:180, label:"", name:"jehuProdType"}
									]}
								,{type:"block", list:[
										{type:"checkbox", width:50,labelWidth:250, label:"개인(신용)정보 처리 및 보험가입 동의서", position:"label-right",name:"clauseFinanceFlag",value:"Y",checked:false}
										,{type:"newcolumn"}
										,{type:"checkbox", width:50,labelWidth:170, label:"제휴사 제공동의", position:"label-right",name:"clausePartnerOfferFlag",value:"Y",checked:false}
									]}
								,{type:"block", list:[
										{type:"checkbox", width:90, labelWidth:190, label:"네트워크차단동의", position:"label-right", name:"nwBlckAgrmYn", value:"Y", checked:false}
										,{type:"newcolumn"}
										,{type:"checkbox", width:50,labelWidth:170, label:"유해정보차단APP 설치동의", position:"label-right", name:"appBlckAgrmYn", value:"Y", checked:false}
										,{type:"newcolumn"}
										,{type:"select",  width:235, labelWidth:100, label:"유해정보차단APP", position:"label-left", name:"appCd"}
									]}
								,{type:"block", list:[
										{type:"hidden", width:60, label:"", name:"agentCode"}
										,{type:"hidden", width:60, label:"", name:"requestKey"}
										,{type:"hidden", width:60, label:"", name:"appEventCd"}
										,{type:"hidden", width:60, label:"", name:"osstMsg"}
										,{type:"hidden", width:60, label:"", name:"fathUseYn"}
										,{type:"hidden", width:60, label:"", name:"isFathTrgFlag"}
									]}
							]}

					]}
			]
			,buttons : {
				left : {
					btnNP1 : {label:"번호이동사전동의"}
					, btnPC0 : {label: "사전체크"}
					, btnNP2 : {label:"번호이동납부주장"}
					, btnOP0 : {label:"개통요청"}
					, btnMSG : {label:"연동결과조회"}
					, btnSMS : {label:"SMS전송"}
				},
				right : {
					btnNew : {label : "새로작성" }
					,btnCopy: {label:"예약번호변경" }
					,btnSave : {label : "저장" }
					,btnMod : {label : "수정" }
				}
			}
			,onChange : function(name, value) {
				var form=this;

				form1Change(form, 1, name);
			}
			,onKeyDown : function (inp, ev, name, value) {
				if(name == "cstmrNativeRrn2" && ev.keyCode == 13) {
					PAGE.FORM1.callEvent("onButtonClick", ["btnCheckCstmr"]);
				}
			}
			,onKeyUp : function (inp, ev, name, value){
				if(name == "cstmrNativeRrn1" || name == "cstmrNativeRrn2"){
					var keyId = ev.keyCode;
					if(!((keyId >= 48 && keyId <=57) ||( keyId >= 96 && keyId <= 105 ) || keyId == 8 || keyId == 46 || keyId == 37 || keyId == 39)){
						var val = PAGE.FORM1.getItemValue(name);
						PAGE.FORM1.setItemValue(name,val.replace(/[^0-9]/g,""));
					}
				}
			}
			,checkSelectedButtons : ["btnSave","btnMod"]
			,onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {
						//대리점 검색
					case "btnOrgFind":
						var target = "ORG";
						if(sessionUserOrgnId == "A30040999") {
							target = "REQORG";
						}
						mvno.ui.codefinder(target, function(result) {
							form.setItemValue("pAgentCode", result.orgnId);
							form.setItemValue("pAgentName", result.orgnNm);

							sOrgnId = result.orgnId;
							sOrgnNm = result.orgnNm;
							getChrgPrmtListCombo(PAGE.FORM1, "Y");
							PAGE.FORM1.callEvent('onButtonClick', ['btnPlcFind2']);
							form1Change(form, 0, "pAgentCode");
						});

						break;

						//판매점 검색
					case "btnShopFind":
						var target = "ORGSHOP";
						if(sessionUserOrgnId == "A30040999") {
							target = "REQORG";
						}
						mvno.ui.codefinder(target, function(result) {
							form.setItemValue("pShopCode", result.orgnId);
							form.setItemValue("shopNm", result.orgnNm);
							form.setItemValue("shopCd", result.orgnId); //판매점코드 set
							sShopId = result.orgnId;
							sShopNm = result.orgnNm;
						});
						break;

						// 개통이력확인
					case "btnCheckCstmr":
						if(form.getItemValue("cstmrNativeRrn1") == "" || form.getItemValue("cstmrNativeRrn2") == ""){
							mvno.ui.alert("주민번호를 입력하세요");
							return;
						}

						var params = {
							"cstmrNativeRrn" : form.getItemValue("cstmrNativeRrn1") + form.getItemValue("cstmrNativeRrn2")
						}

						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getCheckCstmrList.json", params, function(resultData) {
							form.setItemValue("checkCstmr", "Y");

							// 개통이력이 존재하면 팝업 오픈
							var len = resultData.result.data.rows.length;
							//console.log("length=" + len);

							if(len > 0){
								var params = "?ssn=" + form.getItemValue("cstmrNativeRrn1") + form.getItemValue("cstmrNativeRrn2");

								mvno.ui.popupWindowTop(ROOT + "/rcp/rcpMgmt/getCheckCstmr.do" + params, "개통이력조회", 400, 420);
							}else{
								mvno.ui.alert("개통이력이 없습니다");
							}
						}, {async:false, dimmed:true});

						break;

						// 재고확인 : [단말모델코드 + 단말일련번호(직접입력) + 조직ID]를 통해 확인
					case "btnCheckSn":

						form.setItemValue("checkSn", 'N');
						salePlcyCdListInit();

						var reqPhoneSn = form.getItemValue('reqPhoneSn');		// 단말기일련번호
						var reqModelColor = form.getItemValue('reqModelColor');	// 단말색상코드
						var plcyOrgnId = form.getItemValue('cntpntShopId');		// 정책으로부터 선택하여 받은 조직ID
						var prdtId = "";										// 단말모델코드
						var plcyOrgnYN = false;									// 판매정책 선택 여부
						var cntpntShopId = "";									// 단말일련번호 조직 ID

						if(mvno.cmn.isEmpty(plcyOrgnId)){
							cntpntShopId = sOrgnId;
						}else{
							prdtId = form.getItemValue('prdtId');
							cntpntShopId = plcyOrgnId;
							plcyOrgnYN = true;
						}

						var reqInDay = form.getItemValue('reqInDayStr');

						if(reqPhoneSn == ''){
							mvno.ui.alert('단말기일련번호를 입력하세요.');
							return;
						}

						var params = {
							'reqPhoneSn':reqPhoneSn			// 단말기일련번호
							,'prdtId':prdtId				// 단말모델코드
							,'cntpntShopId':cntpntShopId	// 조직 ID
							,'reqInDay':reqInDay			// 신청일자
						};

						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/checkNewPhoneSN.json", params, function(resultData) {
							var result = resultData.result.result.data;

							if(result == null || result.total == '' || result.total == null || result.total == undefined){
								mvno.ui.alert('해당 조직에 등록된 단말기일련번호가 아니거나 현재 재고가 없습니다.');
								return;
							}

							var cnt = Number(result.total);

							if(cnt == 0){
								mvno.ui.alert('해당 조직에 등록된 단말기일련번호가 아니거나 현재 재고가 없습니다.');
								return;
							}

							if(cnt > 1){

								var prdtParams = "?reqPhoneSn="+reqPhoneSn
										+ "&prdtId="+prdtId
										+ "&reqInDay="+reqInDay
										+ "&orgnId="+cntpntShopId
								;

								mvno.ui.popupWindowTop(ROOT + "/rcp/rcpMgmt/getRcpPrdt.do" + prdtParams, "재고확인", 700, 500, {
									param : {
										callback : function(result) {

											form.setItemValue('reqPhoneSn', result.prdtSrlNum);

											var params2 = {
												'reqPhoneSn':result.prdtSrlNum	// 단말기일련번호
												,'prdtId':result.prdtId			// 단말모델코드
												,'cntpntShopId':result.orgnId	// 조직 ID
												,'reqInDay':reqInDay			// 신청일자
											};

											mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/checkNewPhoneSN.json", params2, function(resultData) {
												var result2 = resultData.result.result.data;

												if(result2 == null || result2.total == '' || result2.total == null || result2.total == undefined){
													mvno.ui.alert('해당 조직에 등록된 단말기일련번호가 아니거나 현재 재고가 없습니다.');
													return;
												}

												var cnt2 = Number(result2.total);

												if(cnt2 == 0){
													mvno.ui.alert('해당 조직에 등록된 단말기일련번호가 아니거나 현재 재고가 없습니다.');
													return;
												}

												if(checkSnAfter(form, result2.rows[0], plcyOrgnYN)){
													var svcCtNum = Number(result2.rows[0].svcCtNum);
													var msg = "사용 가능한 단말기일련번호 입니다.";

													if(svcCtNum > 0){
														msg += "<br/>(기개통 이력이 있는 단말기일련번호입니다.)";
													}

													mvno.ui.alert(msg);
												}
											});
										}
									}
								});

							}else{

								if(checkSnAfter(form, result.rows[0], plcyOrgnYN)){
									var svcCtNum = Number(result.rows[0].svcCtNum);
									var msg = "사용 가능한 단말기일련번호 입니다.";

									if(svcCtNum > 0){
										msg += "<br/>(기개통 이력이 있는 단말기일련번호입니다.)";
									}

									mvno.ui.alert(msg);
								}
							}

						});

						break;

					case "btnPlcFind2":
						form.showItem("btnPlcFind");
						form.hideItem("btnPlcFind2");

						if(mvno.cmn.isEmpty(form.getItemValue("resNo"))){
							form.enableItem("operType");	// 신청유형
						}

						form.enableItem("mnfctId");			// 제조사
						form.enableItem("prdtId");			// 단말모델
						form.enableItem("socCode");			// 요금제
						form.enableItem("agrmTrm");			// 약정기간
						form.enableItem("instNom");			// 할부기간
						form.enableItem("sprtTp");			// 할인유형

						// 초기화
						form.setItemValue("mnfctId", "");			// 제조사
						form.setItemValue("prdtId", "");			// 단말모델
						form.setItemValue("socCode", "");			// 요금제
						form.setItemValue("agrmTrm", "");			// 약정기간
						form.setItemValue("instNom", "");			// 할부기간
						form.setItemValue("salePlcyCd", "");		// 판매정책ID
						form.setItemValue("salePlcyNm", "");		// 판매정책명
						form.setItemValue("realMdlInstamt", "");	// 할부원금(정책)
						form.setItemValue("modelInstallment", "");	// 할부원금(사용자)
						form.setItemValue("modelDiscount3", "");	// 대리점지원금
						form.setItemValue("cntpntShopId", "");		// 정책선택으로 얻은 조직ID
						form.setItemValue("prdtCode", "");			// 단말코드
						form.setItemValue("modelId", "");			// 모델ID
						form.setItemValue("reqUsimName", "");		// USIM모델코드
						form.setItemValue("reqModelName", "");		// 단말모델코드
						form.setItemValue("sprtTp", "");			// 할인유형
						form.setItemValue("hndstAmt", "");			// 출고가
						form.setItemValue("modelDiscount2", "");	// 공시지원금
						form.setItemValue("maxDiscount3", "");		// 대리점지원금(MAX)
						form.setItemValue("dcAmt", "");				// 할인금액
						form.setItemValue("addDcAmt", "");			// 추가할인금액
						form.setItemValue("eid", "");				// eid 값
						form.setItemValue("imei1", "");				// imei1
						form.setItemValue("imei2", "");				// imei2
						form.setItemValue("intmMdlId", "");			// 단말모델ID
						form.setItemValue("intmSrlNo", "");			// 단말일련번호
						form.setItemValue("eReqModelName", "");		// 단말모델명
						form.setItemValue("usimKindsCd", "");		// 추가할인금액
						form.setItemValue("prntsCtnFn", "");		// 모회선1
						form.setItemValue("prntsCtnMn", "");		// 모회선2
						form.setItemValue("prntsCtnRn", "");		// 모회선3
						form.setItemValue("billAcntNo", "");		// 청구계정ID
						form.setItemValue("prntsContractNo", "");	// 모회선계약번호
						form.hideItem("eid");
						form.hideItem("imei1");
						form.hideItem("imei2");
						form.hideItem("intmMdlId");
						form.hideItem("intmSrlNo");
						form.hideItem("eReqModelName");
						form.hideItem("prntsCtnFn");		// 모회선1
						form.hideItem("prntsCtnMn");		// 모회선2
						form.hideItem("prntsCtnRn");		// 모회선3
						form.hideItem("billAcntNo");		// 청구계정ID
						form.hideItem("prntsContractNo");	// 모회선계약번호

						form.enableItem("usimKindsCd");
						// 단말모델, 요금제 Reload
						form1Change(PAGE.FORM1, 0, "reqBuyType");
						// 단말모델색상 Reload
						form1Change(PAGE.FORM1, 0, "prdtId");

						salePlcyData = "";
// 						form.setItemValue("checkSn", 'N');
						form.setItemValue("reqPhoneSn", "");
						form.reloadOptions("salePlcyCdList", "");
						form.enableItem("salePlcyCdList");			// 정책선택(combo)
						form.showItem("salePlcyNm");				// 정책명
						form.hideItem("salePlcyCdList");			// 정책선택 combox

						if(typeCd == "10"){
							form.enableItem("pAgentName");				// 대리점
							form.enableItem("btnOrgFind");				// 대리점 찾기
						}

						/* v2018.01 KT판매코드 적용 */
						form.reloadOptions("spcCode", "");

						break;

						// 정책선택 버튼
					case "btnPlcFind":

						// 조직명 한글 깨짐 현상 방지를 위한 방법
						// 넘길 때 코드로 넘기고 받을 때 다시 변환하여 처리함
						var orgNmTmp = fncEnCode(sOrgnNm);

						// 판매정책 조회시 필요한 사항
						var params = "?reqBuyType="+form.getItemValue("reqBuyType")		// 구매유형 (신청서정보)
								+ "&serviceType="+form.getItemValue("serviceType")		// 서비스타입
								+ "&reqInDay="+form.getItemValue("reqInDayStr")			// 신청일자 (신청서정보)
								+ "&operType="+form.getItemValue("operType")			// 신청유형 (신청서정보)
								+ "&prdtId="+form.getItemValue("prdtId")				// 단말모델 (판매정보)
								+ "&socCode="+form.getItemValue("socCode")				// 요금제 (판매정보)
								+ "&agrmTrm="+form.getItemValue("agrmTrm")				// 약정기간 (판매정보)
								+ "&instNom="+form.getItemValue("instNom")				// 할인기간 (판매정보)
								+ "&sOrgnId="+sOrgnId									// 조직 ID
								+ "&sOrgnNm="+orgNmTmp									// 조직명
								+ "&typeCd="+typeCd										// 조직 형태 (typeCd = 10:본사조직, 20:대리점, 30:판매점)
								+ "&hghrOrgnCd="+hghrOrgnCd								// 상위 조직 코드
								+ "&sprtTp="+form.getItemValue("sprtTp")				// 할인유형
						;

						// 판매정책 조회
						mvno.ui.popupWindowTop(ROOT + "/rcp/rcpMgmt/getRcpPlc.do" + params, "판매정책", 880, 600, {
									param : {
										callback : function(result) {
											//console.log("result=" + result);



											if(result.plcyTypeCd == "R"){
												// 렌탈일경우 가입 유형 설정
												mvno.ui.alert('렌탈 정책은 선택 할 수 없습니다.');
												return;
											}else{
												form.setItemValue("prodType", "01");
											}

											if(form.getItemValue("operType") != result.operType){
												mvno.ui.alert('신청유형은 변경 할 수 없습니다.');
												return;
											}

											if(form.getItemValue("reqBuyType") != result.reqBuyType){
												mvno.ui.alert('구매유형은 변경 할 수 없습니다.');
												return;
											}

											var chckPrdtId = form.getItemValue("prdtId");					// 판매정책 선택 전 단말모델

											form.setItemValue("reqBuyType",result.reqBuyType);				// 구매유형
											form1Change(PAGE.FORM1, 0, "reqBuyType");
											form.setItemValue("operType", result.operType);					// 신청유형
											form1Change(PAGE.FORM1, operTypeInit, "operType");
											form.setItemValue("cntpntShopId", result.orgnId);				// 조직ID
											form.setItemValue("salePlcyCd", result.salePlcyCd);				// 판매정책코드
											form.setItemValue("mnfctId", result.mnfctId);					// 제조사 ID
											form1Change(form, 0, "mnfctId");
											form.setItemValue("prdtNm", result.prdtNm);						// 단말모델명
											form.setItemValue("socCode", result.rateCd);					// 요금제
											form.setItemValue("agrmTrm", result.agrmTrm);					// 약정기간
											form.setItemValue("salePlcyNm", result.salePlcyNm);				// 판매정책명
											form.setItemValue("modelDiscount2", result.modelDiscount2);		// 공시지원금
											form.setItemValue("modelDiscount3", result.modelDiscount3);		// 대리점지원금
											form.setItemValue("maxDiscount3", result.maxDiscount3);			// 대리점지원금(MAX)
											form.setItemValue("instNom", result.instNom);					// 할부기간

											// 요금제 값 변경에 따른 처리 (제휴처)
											form.enableItem("clauseJehuFlag")
											form.setItemValue("clauseJehuFlag", "");
											form.setItemValue("jehuProdType", "");

											// 대리점 변경에 따른 처리 (제휴사)
											form.enableItem("clausePartnerOfferFlag");
											form.setItemValue("clausePartnerOfferFlag", "");

											var prdtIndCd = result.prdtIndCd;
											if(prdtIndCd == "10" || prdtIndCd == "11"){
												PAGE.FORM1.setItemValue("usimKindsCd", "09");
												form1Change(PAGE.FORM1, 0, "usimKindsCd");
												form.disableItem("usimKindsCd");

											}

											form.setItemValue("prdtId", result.prdtId);						// 단말모델ID

											// 최초에는 할부원금이 정책과 사용자 모두 동일
											form.setItemValue("realMdlInstamt", result.realMdlInstamt);		// 할부원금(사용자)
											form.setItemValue("modelInstallment", result.realMdlInstamt);	// 할부원금(정책)

											// Hidden Value
											form.setItemValue("modelId", result.prdtId);					// 모델ID
											form.setItemValue("prdtCode", result.prdtCode);

											if(result.reqBuyType == "UU"){
												form.setItemValue("reqUsimName", 	result.prdtCode);			// USIM모델코드
											}
											else {
												form.setItemValue("reqModelName", 	result.prdtCode);			// 단말모델코드
											}
											// modelPrice : 정책설정시 단말구매시에만 값이 들어가도록 설정되어 있음.
											form.setItemValue("modelPrice", result.modelPrice);				// 출고단가(미포함)
											form.setItemValue("hndstAmt", result.hndstAmt);					// 출고단가(포함)
											form.setItemValue("usimPrice", result.usimPrice);				// USIM비
											form.setItemValue("recycleYn", result.oldYn);					// 중고여부
											form.setItemValue("joinPrice", result.joinPrice);				// 가입비
											//form.setItemValue("mnfctId", result.mnfctId);					// 제조사 ID
											form.setItemValue("sprtTp", result.sprtTp);						// 할인유형
											form.setItemValue("dcAmt", result.dcAmt);						// 할인금액
											form.setItemValue("addDcAmt", result.addDcAmt);					// 추가할인금액

											//-----------------------------------------------------------------------------------
											// 정책이 선택이 되면,
											// 1. 정책이 선택되면, 정책선택버튼은 사라지고, 다시선택버튼은 보임.
											form.showItem("btnPlcFind2");				// 정책 다시 선택 버튼
											form.hideItem("btnPlcFind");				// 정책 선택 버튼
											// 2. Disable 처리
											form.disableItem("reqBuyType");				// 구매유형
											form.disableItem("operType");				// 신청유형
											form.disableItem("mnfctId");				// 제조사
											form.disableItem("prdtId");					// 단말모델
											form.disableItem("socCode");				// 요금제
											form.disableItem("agrmTrm");				// 약정기간
											form.disableItem("instNom");				// 약정기간
											form.disableItem("sprtTp");					// 할인유형
											//form1Change(PAGE.FORM1, 0, "prdtId");
											form.disableItem("prodType");				//  상품구분
											form.disableItem("pAgentName");				// 대리점
											form.disableItem("btnOrgFind");				// 대리점 찾기
											form.disableItem("salePlcyCdList");			// 정책선택(combo)
											form.showItem("salePlcyNm");				// 정책명
											form.hideItem("salePlcyCdList");			// 정책선택 combox

											if(result.reqBuyType == "MM"){
												if('Y' == form.getItemValue('checkSn')){
													if(result.orgnId != form.getItemValue("prdtSrlNoOrgnId")){
														mvno.ui.alert('판매정책의 조직정보와<br>단말기의 조직정보가 일치 하지 않습니다.<br>단말기일련번호를 다시 등록하세요.');
														form.setItemValue('checkSn', 'N');
														form.setItemValue('reqPhoneSn', "");
														form1Change(PAGE.FORM1, 0, "prdtId");
													}else if(form.getItemValue("prdtId") != chckPrdtId){
														mvno.ui.alert('판매정책의 단말모델과 일치 하지 않습니다.<br>단말기일련번호를 다시 등록하세요.');
														form.setItemValue('checkSn', 'N');
														form.setItemValue('reqPhoneSn', "");
														form1Change(PAGE.FORM1, 0, "prdtId");
													}
												}else{
													form1Change(PAGE.FORM1, 0, "prdtId");
												}
											}

											/* v2018.01 KT판매코드 적용 */
											getNoAgrmTrtm(form);
											getKtPlcyListCombo(form, 'Y');
											updateCombineSoloVisible(form.getItemValue("socCode"));
										}
									}
								}
						);

						break;

					case "btnRateFind":

						if(mvno.cmn.isEmpty(form.getItemValue("pAgentCode"))){
							mvno.ui.alert("요금제를 선택하세요");
							return;
						}
						if(mvno.cmn.isEmpty(form.getItemValue("socCode"))){
							mvno.ui.alert("요금제를 선택하세요");
							return;
						}

						if(mvno.cmn.isEmpty(form.getItemValue("agrmTrm"))){
							mvno.ui.alert("약정개월을 선택하세요");
							return;
						}

						var params = "?reqBuyType="+form.getItemValue("reqBuyType")		// 구매유형
								+ "&reqInDay="+form.getItemValue("reqInDayStr").replace(/-/gi,"")			// 신청일자
								+ "&operType="+form.getItemValue("operType")			// 신청유형
								+ "&socCode="+form.getItemValue("socCode")				// 요금제
								+ "&agrmTrm="+form.getItemValue("agrmTrm")				// 약정기간
								+ "&cntpntShopId="+form.getItemValue("pAgentCode")		// 조직ID
								+ "&requestKey="+form.getItemValue("requestKey")
								+ "&onOffType="+form.getItemValue("onOffType")			// 모집경로
						;

						mvno.ui.popupWindowTop(ROOT + "/rcp/rcpMgmt/getRcpSimpRateView.do" + params, "부가서비스", 550, 400,
								{
									param : {
										callback : function(result) {
											form.setItemValue("additionName", result.additionName);
											form.setItemValue("additionKey", result.additionKey);
											form.setItemValue("additionPrice", result.rantal);
											syncCombineSoloAddition(form.getItemValue("combineSoloType"));
										}
									}
								}
						);

						break;

					case "btnSave":
						// validation Check
						if(!validationCheck(PAGE.FORM1)) return;


						// 직접개통시 판매정책이 없을 수 있으므로 대리점ID를 강제 세팅
						if((form.getItemValue("onOffType") == "1" || form.getItemValue("onOffType") == "5" || form.getItemValue("onOffType") == "6") && mvno.cmn.isEmpty(form.getItemValue("salePlcyCd"))){
							form.setItemValue("cntpntShopId", form.getItemValue("pAgentCode"));
							form.setItemValue("modelId", form.getItemValue("prdtId"));
						}

						if(!mvno.etc.checkRrNum(form.getItemValue("cstmrNativeRrn1")+form.getItemValue("cstmrNativeRrn2"))){
							mvno.ui.alert("올바르지 않은 주민번호입니다.");
							return;
						}

                        if (form.getItemValue("personalInfoCollectAgree") != "Y") {
                            if (form.getItemValue("clausePriAdFlag") == "Y") {
                                mvno.ui.alert("{개인정보 처리 위탁 및 혜택 제공 동의}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의}에 동의하여야 합니다.");
                                return;
                            }
                        }

						//광고수신동의추가
						if(form.getItemValue("personalInfoCollectAgree") != "Y" || form.getItemValue("clausePriAdFlag") != "Y"){
							if(form.getItemValue("othersTrnsAgree") == "Y"){
								mvno.ui.alert("{혜택 제공을 위한 제 3자 제공 동의 : M모바일}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
								return;
							}
							if(form.getItemValue("othersTrnsKtAgree") == "Y"){
								mvno.ui.alert("{혜택 제공을 위한 제 3자 제공 동의 : KT}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
								return;
							}
							if(form.getItemValue("othersAdReceiveAgree") == "Y"){
								mvno.ui.alert("{제 3자 제공관련 광고 수신 동의}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
								return;
							}
						}

						/* var blockVal = /[\u1100-\u11FF\u3130-\u318F\uA960-\uA97F\uD7B0-\uD7FF\u3000-\u303F\u2E80-\u2FDF\u3400-\u4DBF\u4E00-\u9FFF\u2500-\u27BF\uFF00-\uFFEF\u200B-\u200F\uFEFF]/g; */
						var blockVal = /[\u1100-\u11FF\u3130-\u318C\u318E-\u318F\uA960-\uA97F\uD7B0-\uD7FF\u3000-\u303F\u2E80-\u2FDF\u3400-\u4DBF\u4E00-\u9FFF\u2500-\u27BF\uFF00-\uFFEF\u200B-\u200F\uFEFF]/g;
						var cstmrAddrDtl = form.getItemValue("cstmrAddrDtl") || "";
						var badVal = cstmrAddrDtl.match(blockVal);

						if (badVal && badVal.length) {
						  // 중복 제거해서 보여주기
						  var uniq = [];
						  for (var i=0; i<badVal.length; i++) if (uniq.indexOf(badVal[i]) < 0) uniq.push(badVal[i]);
						  mvno.ui.alert("상세주소에 금지된 문자 포함: " + uniq.join(""));
						  return;
						}

						var url = "/rcp/rcpMgmt/updateRcpNewDetail.json";

						mvno.cmn.ajax4confirm("단체보험 등 입력한 내용을 확인부탁드립니다.<br />저장하시겠습니까?<br />완료시까지 잠시만 기다려주세요.", ROOT + url, form, function(result) {

							mvno.ui.alert("NCMN0004");

							form.clear();
							form.setItemValue("requestKey", result.result.requestKey);
							form1ReadInit();

						}, {async:false, dimmed:true});

						break;

					case "btnMod":
						// validation Check
						if(PAGE.FORM1.getItemValue("pstate") == "00" && !validationCheck(PAGE.FORM1)) return;

						/* var blockVal = /[\u1100-\u11FF\u3130-\u318F\uA960-\uA97F\uD7B0-\uD7FF\u3000-\u303F\u2E80-\u2FDF\u3400-\u4DBF\u4E00-\u9FFF\u2500-\u27BF\uFF00-\uFFEF\u200B-\u200F\uFEFF]/g; */
						var blockVal = /[\u1100-\u11FF\u3130-\u318C\u318E-\u318F\uA960-\uA97F\uD7B0-\uD7FF\u3000-\u303F\u2E80-\u2FDF\u3400-\u4DBF\u4E00-\u9FFF\u2500-\u27BF\uFF00-\uFFEF\u200B-\u200F\uFEFF]/g;
						var cstmrAddrDtl = form.getItemValue("cstmrAddrDtl") || "";
						var badVal = cstmrAddrDtl.match(blockVal);

						if (badVal && badVal.length) {
						  // 중복 제거해서 보여주기
						  var uniq = [];
						  for (var i=0; i<badVal.length; i++) if (uniq.indexOf(badVal[i]) < 0) uniq.push(badVal[i]);
						  mvno.ui.alert("상세주소에 금지된 문자 포함: " + uniq.join(""));
						  return;
						}
						
						var btnModFn= function(){
							var url = "/rcp/rcpMgmt/updateRcpNewDetail.json";

							mvno.cmn.ajax4confirm("단체보험 등 입력한 내용을 확인부탁드립니다.<br />수정하시겠습니까?", ROOT + url, form, function(result) {

								mvno.ui.alert("NCMN0002");

								form.clear();
								form.setItemValue("requestKey", result.result.requestKey);
								form1ReadInit();

							}, {async:false, dimmed:true});
						}

						// ACEN 번이 대상 신청서 - 위약금 입력 CONFIRM
						var penaltyConfirm= null;

						if(form.getItemValue("operType") == "MNP3" && mvno.cmn.isEmpty(form.getItemValue("movePenalty"))) {

							mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getAcenPenaltyStatus.json", form, function(resultData) {
								penaltyConfirm= resultData.result.status;
							}, {async:false, dimmed:true});

							// 신청서 필수값 입력 안한경우 penaltyConfirm == null
							if(penaltyConfirm == null) return;
						}

						if(penaltyConfirm == "Y"){
							mvno.ui.confirm("위약금 입력값이 없습니다.<br>그대로 진행 하시겠습니까?", function(){
								btnModFn();
							});
						}else{
							btnModFn();
						}

						break;

					case "btnCstmrPostFind":
						jusoGubun = "CSTMR";
						mvno.ui.codefinder4ZipCd("FORM1", "cstmrPost", "cstmrAddr", "cstmrAddrDtl");
						PAGE.FORM1.enableItem("cstmrAddrDtl");

						break;

					case "btnDlvryPostFind":
						jusoGubun = "DLVRY";
						mvno.ui.codefinder4ZipCd("FORM1", "dlvryPost", "dlvryAddr", "dlvryAddrDtl");
						PAGE.FORM1.enableItem("dlvryAddrDtl");

						break;

						// 서식지 보기
					case "btnPaper" :
						if(menuId == 'MSP1010100'){			//서식지함 메뉴를 통한 경우 scanID 자동 지정
							appFormView(form.getItemValue("scanId"));
						}else{
							var reqParam = {"resNo" : form.getItemValue("resNo")};
							mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getMcpRequestScanId.json", reqParam, function(resultData) {

								if(mvno.cmn.isEmpty(resultData.result.scanId)) {
									mvno.ui.alert("등록된 서식지가 없습니다.");
									return;
								}

								form.setItemValue("oriScanId", resultData.result.scanId);

								appFormView(form.getItemValue("oriScanId"));

								if(maskingYn == "Y" || maskingYn == "1"){
									mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"resNo":form.getItemValue("resNo"),"trgtMenuId":"MSP1000016","tabId":"paper"}, function(result){});
								}
							});
						}
						break;

						// 스캔하기
					case "btnRegPaper" :
						var form = this;
						var name = form.getItemValue("cstmrName");
						var scanId = null;
						var DBMSType = "C";
						var scanType = form.getItemValue("scanType");
						form.setItemValue("btnType","scan");

						//scanId 받아오기 (신규생성)
						mvno.cmn.ajax(ROOT + "/appForm/getScanId.json", "", function(resultData) {
							form.setItemValue("scanId", resultData.result.scanId);

							scanId = form.getItemValue("scanId");

							data = "AGENT_VERSION="+agentVersion+
									"&SERVER_URL="+serverUrl+
									"&VIEW_TYPE=SCAN"+
									"&USE_DEL="+delAuthYn+
									"&USE_STAT=N"+
									"&USE_BM="+blackMakingYn+
									"&USE_DEL_BM="+blackMakingFlag+
									"&RGST_PRSN_ID="+userId+
									"&RGST_PRSN_NM="+userName+
									"&ORG_TYPE="+orgType+
									"&ORG_ID="+orgId+
									"&ORG_NM="+orgNm+
									"&RES_NO="+
									"&PARENT_SCAN_ID="+scanId+
									"&DBMSType="+DBMSType+
									"&CUST_NM="+name
							;

							callViewer(data);


						});

						break;

						// 스캔검색
					case "btnScnSarch" :
						var form = this;
						var resNo = form.getItemValue("resNo");
						var scanId = form.getItemValue("scanId");
						var DBMSType = "C";
						var scanType = form.getItemValue("scanType");
						var data = null;
						form.setItemValue("btnType","sSearch");

						// 2016-06-24
						// 개통대기 이전 상태는 고객명 수정가능
						var modifyFlag = (form.getItemValue("requestStateCode") < "20") ? "Y" : "N";

						//scanId 받아오기
						mvno.cmn.ajax(ROOT + "/appForm/getScanId.json", "", function(resultData) {

							if(scanType == "new") {

								resNo = "";

								form.setItemValue("scanId", resultData.result.scanId);
								scanId = form.getItemValue("scanId");

								data = "AGENT_VERSION="+agentVersion+
										"&SERVER_URL="+serverUrl+
										"&VIEW_TYPE=SCANVIEW"+
										"&USE_DEL="+delAuthYn+
										"&USE_STAT="+modifyFlag+
										"&USE_BM="+blackMakingYn+
										"&USE_DEL_BM="+blackMakingFlag+
										"&RGST_PRSN_ID="+userId+
										"&RGST_PRSN_NM="+userName+
										"&ORG_TYPE="+orgType+
										"&ORG_ID="+orgId+
										"&ORG_NM="+orgNm+
										"&RES_NO="+resNo+
										"&PARENT_SCAN_ID="+scanId+
										"&DBMSType="+DBMSType
								;

								callViewer(data);


							} else {
								var reqParam = {"resNo" : form.getItemValue("resNo")};
								mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getMcpRequestScanId.json", reqParam, function(resultData2) {
									if(mvno.cmn.isEmpty(resultData2.result.scanId) || resultData2.result.scanId == null || resultData2.result.scanId == 'null') {
										form.setItemValue("scanId", resultData.result.scanId);
										scanId = form.getItemValue("scanId");
									}

									form.setItemValue("oriScanId", resultData2.result.scanId);

									data = "AGENT_VERSION="+agentVersion+
											"&SERVER_URL="+serverUrl+
											"&VIEW_TYPE=SCANVIEW"+
											"&USE_DEL="+delAuthYn+
											"&USE_STAT="+modifyFlag+
											"&USE_BM="+blackMakingYn+
											"&USE_DEL_BM="+blackMakingFlag+
											"&RGST_PRSN_ID="+userId+
											"&RGST_PRSN_NM="+userName+
											"&ORG_TYPE="+orgType+
											"&ORG_ID="+orgId+
											"&ORG_NM="+orgNm+
											"&RES_NO="+resNo+
											"&PARENT_SCAN_ID="+scanId+
											"&DBMSType="+DBMSType
									;

									callViewer(data);

								});
							}

						});

						break;

						// URL검색
					case "btnFaxSarch" :
						var form = this;
						var resNo = form.getItemValue("resNo");
						var scanId = form.getItemValue("scanId");
						var DBMSType = "C";
						var CID = null;

						var parameters = "";
						var scanType = form.getItemValue("scanType");

						form.setItemValue("btnType","fSearch");

						var data = null;
						//scanId 받아오기
						mvno.cmn.ajax(ROOT + "/appForm/getScanId.json", "", function(resultData) {

							if(scanType == "new") {
								CID = form.getItemValue("faxnum1") + form.getItemValue("faxnum2") + form.getItemValue("faxnum3");

								resNo = "";
								form.setItemValue("scanId", resultData.result.scanId);
								scanId = form.getItemValue("scanId");

								data = "AGENT_VERSION="+agentVersion+
										"&SERVER_URL="+serverUrl+
										"&VIEW_TYPE=URLVIEW"+
										"&USE_DEL="+delAuthYn+
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
										"&DBMSType="+DBMSType,
										"&CID="+CID
								;

								callViewer(data);


							} else {
								CID = form.getItemValue("faxBySearch");

								var reqParam = {"resNo" : form.getItemValue("resNo")};
								mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getMcpRequestScanId.json", reqParam, function(resultData2) {
									if(mvno.cmn.isEmpty(resultData2.result.scanId) || resultData2.result.scanId == null || resultData2.result.scanId == 'null') {
										form.setItemValue("scanId", resultData.result.scanId);
										scanId = form.getItemValue("scanId");
									}

									form.setItemValue("oriScanId", resultData2.result.scanId);

									data = "AGENT_VERSION="+agentVersion+
											"&SERVER_URL="+serverUrl+
											"&VIEW_TYPE=URLVIEW"+
											"&USE_DEL="+delAuthYn+
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
											"&DBMSType="+DBMSType,
											"&CID="+CID
									;

									callViewer(data);

								});

							}

						});
						break;

						// K-Note조회
					case "btnKnote" :
						var url = "/rcp/rcpMgmt/getKnotePop.do";
						mvno.ui.popupWindowTop(ROOT + url, "K-Note조회", 870, 600, {
							param : {
								callback : function(result) {
									form.setItemValue("frmpapId",			result.frmpapId);    			//서식지아이디
									form.setItemValue("mngmAgncId",			result.mngmAgncId);   			//관리대리점코드 (KT_ORG_ID)
									form.setItemValue("pAgentName",			result.mngmAgncNm);  			//관리대리점명
									form.setItemValue("pAgentCode",			result.orgnId);  				//대리점코드 (ORGN_ID)
									form.setItemValue("cntpntShopId",		result.orgnId);  				//대리점코드 (ORGN_ID)
									//신청유형
									if(result.onlineCustTrtSttusChgCd == "NAC"){
										form.setItemValue("operType", "NAC3");
									}
									else if(result.onlineCustTrtSttusChgCd == "MNP"){
										form.setItemValue("operType", "MNP3");
									}
									form1Change(PAGE.FORM1, 0, "operType");
									form1Change(PAGE.FORM1, 0, "pAgentCode");

									form.setItemValue("shopCd",				result.cntpntCd);						//판매점코드
									form.setItemValue("cntpntCd",			result.cntpntCd);						//접점코드
									form.setItemValue("cstmrName",			result.nflCustNm);						//명의자고객명
									form.setItemValue("cstmrNativeRrn1",	result.nflCustIdfyNo.substring(0,6));	//실명인증식별번호1 (주민번호)
									form.setItemValue("cstmrNativeRrn2",	result.nflCustIdfyNo.substring(6,13));	//실명인증식별번호2 (주민번호)
									form.setItemValue("selfCertType",		result.realEvdnDataCd);					//실명인증증빙자료구분 (01(주민등록증), 02(운전면허증)
									form.setItemValue("selfIssuExprDt",		result.realIssuDate);					//실명인증발급일자
									form.setItemValue("cstmrType",			result.simpCstmrType);					//개통간소화고객구분 (내국인/외국인)
									if(result.simpCstmrType == "FN"){			//외국인인 경우 외국인등록번호 SET
										form1Change(PAGE.FORM1, 0, "cstmrType");
										form.setItemValue("cstmrForeignerRrn1",	result.nflCustIdfyNo.substring(0,6));      //실명인증식별번호1
										form.setItemValue("cstmrForeignerRrn2",	result.nflCustIdfyNo.substring(6,13));     //실명인증식별번호2
										form.disableItem("cstmrForeignerRrn1");
										form.disableItem("cstmrForeignerRrn2");
									}
									if(result.realEvdnDataCd){
										form1Change(PAGE.FORM1, 0, "selfCertType");
										form.setItemValue("selfIssuNum", result.realCustIdntNo);
										form.disableItem("selfIssuNum");
										form.disableItem("selfCertType");
									}

									form.disableItem("cstmrName");
									form.disableItem("cstmrNativeRrn1");
									form.disableItem("cstmrNativeRrn2");
									form.disableItem("frmpapId");
									form.disableItem("mngmAgncId");
									form.disableItem("btnOrgFind");
									form.disableItem("operType");
									form.disableItem("cntpntCd");
									form.disableItem("selfIssuExprDt");
									form.disableItem("cstmrType");
									form.disableItem("faxyn");

									mvno.ui.notify("정상적으로 처리 되었습니다");
								}
							}
						});

						break;

					case "btnCopy" :
						// validation Check
// 						if(!validationCheck(PAGE.FORM1)) return;

						var url = "/rcp/rcpMgmt/updateRcpNewDetail.json";
						var befState= form.getItemValue("pstate");

						mvno.ui.confirm("예약번호를 변경하시겠습니까?", function() {

							form.setItemValue("pstate", "30");

							mvno.cmn.ajax(ROOT + url, form, function() {

								mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/copyRcpMgmt.json", {'trgtRequestKey':form.getItemValue("requestKey"), 'frmpapId':form.getItemValue("frmpapId")}, function(result) {

									var copyDoneMsg = "예약번호를 변경하였습니다.";
									if(result.result.isAcenTrg == "Y"){
										copyDoneMsg += "<br><br>[A'Cen 프로세스로 진행됩니다]";
									}

									mvno.ui.alert(copyDoneMsg);

									form.clear();
									form.setItemValue("requestKey", result.result.requestKey);
									form1ReadInit();
								});
							}, {errorCallback: function(){
									// 기존 신청서상태 select 유지
									form.setItemValue("pstate", befState);
								}});
						}, {async:false, dimmed:true});
						break;

					case "btnNew" :
						mvno.ui.confirm("새로 작성 하시겠습니까?", function() {

							var url = "/rcp/rcpMgmt/getRcpSimpMgmtView.do";
							var menuId = "MSP1000016";

							var param = "?menuId=" + menuId;

							var myTabbar = window.parent.mainTabbar;

							myTabbar.tabs(url).setActive();

							myTabbar.tabs(url).attachURL(url + param);
						});

						break;

					case "btnNatnFind":
						mvno.ui.popupWindowTop(ROOT + "/rcp/rcpNewMgmt/getRcpNatn.do", "국적검색", 500, 430,
								{
									param : {
										callback : function(result) {
											form.setItemValue("cstmrForeignerNation", result.natnCd);
											form.setItemValue("cstmrForeignerNationSel", result.natnCd);
											form.setItemValue("cstmrForeignerNationTxt", result.natnCd);
										}
									}
								}
						);

						break;

					//고객 안면인증 대상 여부 조회(FT0)
					case "btnFT0" :

						PAGE.FORM1.setItemValue("appEventCd", "FT0");
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstServiceCall.json", PAGE.FORM1, function(resultData) {
							if(resultData.result.code == "OK"){
								if(resultData.result.resltCd == "00000"){
									PAGE.FORM1.setItemValue("fathTrgYn", "1");
									PAGE.FORM1.setItemValue("isFathTrgFlag", "1");
								} else if(resultData.result.resltCd == "00001" || resultData.result.resltCd == "00002") {
									PAGE.FORM1.setItemValue("fathTrgYn", "0");
									PAGE.FORM1.setItemValue("isFathTrgFlag", "1");
								} else if(resultData.result.resltCd == "00003"){
									PAGE.FORM1.setItemValue("fathTrgYn", "0");
								}
								mvno.ui.alert(resultData.result.resltSbst);
							} else {
								mvno.ui.alert(resultData.result.msg);
							}
						});

						break;

					//고객 안면인증 URL 요청 (FS8)
					case "btnFS8" :
						
						if(!PAGE.FORM1.isItemChecked("fathTrgYn")){
							mvno.ui.alert("안면인증대상이 아닙니다.");
							return;
						}

						if(!PAGE.FORM1.isItemChecked("clauseFathFlag")){
							mvno.ui.alert("{안면인증동의}에 동의하여야 합니다.");
							return;
						}

						//고객 안면인증 URL 요청 (FS8)
						mvno.ui.confirm("안면인증 URL을 전송하시겠습니까?", function() {
							PAGE.FORM1.setItemValue("appEventCd", "FS8");
							mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstServiceCall.json", PAGE.FORM1, function(resultData) {

								if(resultData.result.code == "OK"){
									if(resultData.result.resltCd == "00000"){
										PAGE.FORM1.setItemValue("fathTransacId", resultData.result.fathTransacId);
									}
									mvno.ui.alert(resultData.result.resltSbst);
								} else {
									mvno.ui.alert(resultData.result.msg);
								}
							});
						});


						break;

					//고객 안면인증 내역 조회
					case "btnReqFathTxnRetv" :

						if(!PAGE.FORM1.isItemChecked("fathTrgYn")){
							mvno.ui.alert("안면인증대상이 아닙니다.");
							return;
						}

						if(!PAGE.FORM1.isItemChecked("clauseFathFlag")){
							mvno.ui.alert("{안면인증동의}에 동의하여야 합니다.");
							return;
						}

						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/reqFathTxnRetv.json", PAGE.FORM1, function(resultData) {
							if(resultData.result.code == "OK"){

								PAGE.FORM1.setItemValue("fathCmpltNtfyDt", resultData.result.fathCmpltNtfyDt);
								PAGE.FORM1.setItemValue("selfCertType", resultData.result.selfCertType);
								form1Change(PAGE.FORM1, 1, "selfCertType");
								
								PAGE.FORM1.setItemValue("selfIssuExprDt", resultData.result.selfIssuExprDt);
								PAGE.FORM1.setItemValue("selfIssuNum", resultData.result.driverSelfIssuNum);

							}

							mvno.ui.alert(resultData.result.msg);
						});

						break;

					//고객 셀프안면인증 URL 발급 
					case "btnFath" :

						if(!PAGE.FORM1.isItemChecked("fathTrgYn")){
							mvno.ui.alert("안면인증대상이 아닙니다.");
							return;
						}

						if(!PAGE.FORM1.isItemChecked("clauseFathFlag")){
							mvno.ui.alert("{안면인증동의}에 동의하여야 합니다.");
							return;
						}

						var smsRcvTelNo = form.getItemValue("fathMobileFn") + form.getItemValue("fathMobileMn") + form.getItemValue("fathMobileRn");						
						if(smsRcvTelNo.length != 11) {
							mvno.ui.alert("안면인증 연락처를 바르게 입력해주세요.");
							return;
						}
						
						var data = {
							resNo : form.getItemValue("resNo"),
							operType : form.getItemValue("operType"),
							smsRcvTelNo : smsRcvTelNo,
							cntpntShopId : form.getItemValue("cntpntShopId")
						}

						mvno.ui.confirm("문자를 전송하시겠습니까?", function() {
							mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/insertFathSelfUrl.json",data, function(resultData) {
								if (resultData.result.code === "OK") {
									mvno.ui.alert("문자 전송이 완료되었습니다.")
								} else {
									mvno.ui.alert("문자 전송에 실패했습니다.");
								}
							});
						});

						break;

					case "btnFT1":

						if(!PAGE.FORM1.isItemChecked("fathTrgYn")){
							mvno.ui.alert("안면인증대상이 아닙니다.");
							return;
						}

						if(!PAGE.FORM1.isItemChecked("clauseFathFlag")){
							mvno.ui.alert("{안면인증동의}에 동의하여야 합니다.");
							return;
						}

						if(!PAGE.FORM1.getItemValue("fathTransacId")){
							mvno.ui.alert("{URL요청}이나 {셀프URL}을 먼저 진행해야 합니다.");
							return;
						}

						mvno.ui.confirm("안면인증을 SKIP 하시겠습니까?", function() {
							mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/requestCustFathTxnSkip.json", PAGE.FORM1, function(resultData) {
								if(resultData.result.code == "0000"){
									mvno.ui.alert("안면인증 SKIP 성공");
								} else {
									mvno.ui.alert(resultData.result.msg);
								}
							});
						});
						break;
						
						//사전체크
					case "btnPC0":

						//안면인증 사용여부 공통코드
						if(PAGE.FORM1.getItemValue("fathUseYn") == "Y") {
							if(form.getItemValue("isFathTrgFlag") != "1") {
								mvno.ui.alert("안면인증 대상여부 확인 후 다시 시도해주세요.");
								return;
							}
						}

						
						
						var isAuthChk = true;
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/checkAuth.json", form, function(resultData) {
						}, {async:false, dimmed:true, errorCallback:function(){
							isAuthChk = false;
						}});
						if(!isAuthChk) return;

						// validation Check
						if(!validationCheck(PAGE.FORM1)) return;

						if(PAGE.FORM1.getItemValue("pstate") != "00"){
							mvno.ui.alert("[신청서상태] 정상인 경우 처리가능합니다.");
							return;
						}

						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/updateRcpNewDetail.json", form, function() {
							/*if (form.getItemValue("faxyn") == "K") {  //서식지가 Knote인 경우
								form.setItemValue("appEventCd", "FPC0");
							} else {
								form.setItemValue("appEventCd", "PC0");
							}*/
							form.setItemValue("appEventCd", "PC0");
							mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstServiceCall.json", form, function(resultData) {
								mvno.ui.alert(resultData.result.msg);
							});
						});

						break;

						//개통처리
					case "btnOP0":

						var isAuthChk = true;
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/checkAuth.json", form, function(resultData) {
						}, {async:false, dimmed:true, errorCallback:function(){
								isAuthChk = false;
							}});
						if(!isAuthChk) return;

						// validation Check
						if(!validationCheck(PAGE.FORM1)) return;
						if(PAGE.FORM1.getItemValue("pstate") != "00"){
							mvno.ui.alert("[신청서상태] 정상인 경우 처리가능합니다.");
							return;
						}

						// 신규인 경우 번호예약확인
						if(this.getItemValue("operType") == "NAC3" && mvno.cmn.isEmpty(this.getItemValue("tlphNo"))){
							mvno.ui.alert("신규가입시 번호예약 후 개통가능합니다.");
							return;
						}



						if(this.getItemValue('reqBuyType') == 'MM'){

							if(this.getItemValue("prodType") != "02") {
								if(this.getItemValue('reqPhoneSn')==''){
									mvno.ui.alert("단말기일련번호를 입력하세요.");
									return;
								}
							}
						}else{
							if(this.getItemValue("usimKindsCd") != "09" ){ //유심단독구매이면서 esim이 아닌경우 유심일련번호는 필수
								if(mvno.cmn.isEmpty(this.getItemValue("reqUsimSn"))){
									mvno.ui.alert('유심번호를 입력하세요.');
									return;
								}
							}else{ //esim 일 경우
								if(mvno.cmn.isEmpty(this.getItemValue("eid"))){
									mvno.ui.alert('EID를 입력하세요.');
									return;
								}
								/* if(mvno.cmn.isEmpty(this.getItemValue("imei1"))){
									mvno.ui.alert('IMEI1를 입력하세요.');
									return;
								} */
								if(mvno.cmn.isEmpty(this.getItemValue("imei2"))){
									mvno.ui.alert('IMEI2를 입력하세요.');
									return;
								}
								if(mvno.cmn.isEmpty(this.getItemValue("intmMdlId"))){
									mvno.ui.alert('단말모델ID를 입력하세요.');
									return;
								}
								if(mvno.cmn.isEmpty(this.getItemValue("intmSrlNo"))){
									mvno.ui.alert('단말일련번호를 입력하세요.');
									return;
								}
							}
							//유심종류  esim 의 경우 단말모델 필수
							if(this.getItemValue("usimKindsCd")  == "09" && (mvno.cmn.isEmpty(this.getItemValue('prdtId')) || this.getItemValue('prdtId') == "" )){
								this.pushError("prdtId", "단말모델", ["ICMN0001"]);
							}

						}

						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/updateRcpNewDetail.json", form, function() {
							/*if (form.getItemValue("faxyn") == "K") {  //서식지가 Knote인 경우
								form.setItemValue("appEventCd", "FOP0");
							} else {
								form.setItemValue("appEventCd", "OP0");
							}*/
							form.setItemValue("appEventCd", "OP0");
							mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstServiceCall.json", form, function(resultData) {
								mvno.ui.alert(resultData.result.msg);
							});
						});

						break;

						//연동결과조회
					case "btnMSG":
						if(mvno.cmn.isEmpty(form.getItemValue("resNo"))){
							mvno.ui.alert("예약번호가 존재하지 않습니다.");
							return;
						}

						var params = "?resNo="+ form.getItemValue("resNo");

						mvno.ui.popupWindowTop(ROOT + "/rcp/rcpMgmt/getRcpSimpMsgView.do" + params, "연동결과조회", 650, 360,
								{
									param : {
										callback : function(result) {

										}
									}
								}
						);

						break;

						//SMS전송
					case "btnSMS":
						var params = "?operType=" + form.getItemValue("operType")
								+ "&dstaddr=" + form.getItemValue("cstmrMobileFn") + form.getItemValue("cstmrMobileMn") + form.getItemValue("cstmrMobileRn")
								+ "&tlphNo=" + form.getItemValue("tlphNo")
								+ "&custNm=" + fncEnCode(form.getItemValue("cstmrName"))
								+ "&userName=" + fncEnCode(userName)
								+ "&resNo=" + form.getItemValue("resNo")
						;

						mvno.ui.popupWindowTop(ROOT + "/rcp/rcpMgmt/getRcpSmsSendView.do" + params, "SMS발송", 500, 800,
								{
									param : {
										callback : function(result) {

										}
									}
								}
						);

						break;

						//번호이동사전동의
					case "btnNP1":
						// validation Check
						if(!validationCheck(PAGE.FORM1)) return;

						form.setItemValue("appEventCd", "NP1");
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstServiceCall.json", form, function(resultData) {
							mvno.ui.alert(resultData.result.msg);
						});

						break;

						//납부주장
					case "btnNP2":
						if(mvno.cmn.isEmpty(form.getItemValue("resNo"))){
							mvno.ui.alert("예약번호가 존재하지 않습니다.");
							return;
						}

						var parms = "?resNo="+ form.getItemValue("resNo") + "&requestKey="+form.getItemValue("requestKey");

						mvno.ui.popupWindowTop(ROOT + "/rcp/rcpMgmt/getRcpSimpPayClaView.do" + parms, "납부주장", 530, 400,
								{
									param : {
										callback : function(result) {

										}
									}
								}
						);

						break;

					case "btnInqrTlphNo":
						if(mvno.cmn.isEmpty(form.getItemValue("resNo"))){
							mvno.ui.alert("예약번호가 존재하지 않습니다.");
							return;
						}

						var parms = "?resNo="+ form.getItemValue("resNo") + "&reqWantNumber="+form.getItemValue("reqWantNumber");

						mvno.ui.popupWindowTop(ROOT + "/rcp/rcpMgmt/getRcpSimpTlphNoView.do" + parms, "번호조회", 330, 520,
								{
									param : {
										callback : function(result) {

											form.setItemValue("tlphNo", result.tlphNo);

											form.showItem('btnCnclTlphNo');
											form.hideItem('btnInqrTlphNo');
										}
									}
								}
						);

						break;

					case "btnCnclTlphNo":
						if(mvno.cmn.isEmpty(form.getItemValue("resNo"))){
							mvno.ui.alert("예약번호가 존재하지 않습니다.");
							return;
						}

						var data = {
							appEventCd : "NU2"
							, gubun : "RRS"
							, resNo : form.getItemValue("resNo")
							, tlphNo : form.getItemValue("tlphNo")
						};

						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/setOsstSvcNoRsv.json", data, function(resultData) {

							mvno.ui.alert("예약 취소 처리 되었습니다.");

							form.setItemValue("tlphNo", "");

							form.showItem('btnInqrTlphNo');
							form.hideItem('btnCnclTlphNo');
						});

						break;
						// 녹취파일등록
					case "btnRecSarch" :

						var strRequestKey = form.getItemValue("requestKey");

						if(mvno.cmn.isEmpty(strRequestKey) || strRequestKey == null || strRequestKey == 'null' || strRequestKey == "") {
							mvno.ui.alert("신청서 저장 후 녹취파일 등록이 가능합니다.");
							return false;
						}

						var fileLimitCnt = 5;

						mvno.ui.createForm("FORM2");
						PAGE.FORM2.clear();

						mvno.cmn.ajax(ROOT + "/org/common/getRequestKey.json", {orgnId:strRequestKey}, function(resultData) {

							var totCnt = resultData.result.fileTotalCnt;

							if( parseInt(totCnt) > 0){
								PAGE.FORM2.setUserData("file_upload1","limitCount",fileLimitCnt-parseInt(totCnt));
								PAGE.FORM2.setUserData("file_upload1","limitsize",1000);
								PAGE.FORM2.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
							}

							if(parseInt(totCnt) == fileLimitCnt){
								PAGE.FORM2.hideItem("file_upload1");
							}

							if(!mvno.cmn.isEmpty(resultData.result.fileNm)){
								PAGE.FORM2.setItemValue("fileName1", resultData.result.fileNm);
								PAGE.FORM2.setItemValue("fileId1", resultData.result.fileId);
								PAGE.FORM2.enableItem("fileDown1");
								PAGE.FORM2.enableItem("fileDel1");
							} else {
								PAGE.FORM2.disableItem("fileDown1");
								PAGE.FORM2.disableItem("fileDel1");
							}

							if(!mvno.cmn.isEmpty(resultData.result.fileNm1)){
								PAGE.FORM2.setItemValue("fileName2", resultData.result.fileNm1);
								PAGE.FORM2.setItemValue("fileId2", resultData.result.fileId1);
								PAGE.FORM2.enableItem("fileDown2");
								PAGE.FORM2.enableItem("fileDel2");
							} else {
								PAGE.FORM2.disableItem("fileDown2");
								PAGE.FORM2.disableItem("fileDel2");
							}

							if(!mvno.cmn.isEmpty(resultData.result.fileNm2)){
								PAGE.FORM2.setItemValue("fileName3", resultData.result.fileNm2);
								PAGE.FORM2.setItemValue("fileId3", resultData.result.fileId2);
								PAGE.FORM2.enableItem("fileDown3");
								PAGE.FORM2.enableItem("fileDel3");
							} else {
								PAGE.FORM2.disableItem("fileDown3");
								PAGE.FORM2.disableItem("fileDel3");
							}

							if(!mvno.cmn.isEmpty(resultData.result.fileNm3)){
								PAGE.FORM2.setItemValue("fileName4", resultData.result.fileNm3);
								PAGE.FORM2.setItemValue("fileId4", resultData.result.fileId3);
								PAGE.FORM2.enableItem("fileDown4");
								PAGE.FORM2.enableItem("fileDel4");
							} else {
								PAGE.FORM2.disableItem("fileDown4");
								PAGE.FORM2.disableItem("fileDel4");
							}

							if(!mvno.cmn.isEmpty(resultData.result.fileNm4)){
								PAGE.FORM2.setItemValue("fileName5", resultData.result.fileNm4);
								PAGE.FORM2.setItemValue("fileId5", resultData.result.fileId4);
								PAGE.FORM2.enableItem("fileDown5");
								PAGE.FORM2.enableItem("fileDel5");
							} else {
								PAGE.FORM2.disableItem("fileDown5");
								PAGE.FORM2.disableItem("fileDel5");
							}
						});

						var uploader = PAGE.FORM2.getUploader("file_upload1");

						uploader.revive();

						lockscroll(true);

						mvno.ui.popupWindowById("FORM2", "파일관리", 600, 500, {
							onClose2: function(win) {
								uploader.reset();

								lockscroll(false);
							}
						});

						break;
				}
			}
		} /* End FORM1 */
		,FORM2 : {
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
								{type:"button", name: 'fileDel1', value:"삭제"},
							]},
						{type: "block", list: [
								{type: 'input', name: 'fileName2', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
								{type: 'hidden', name: 'fileId2', label: '파일ID'},
								{type: "newcolumn"},
								{type:"button", name: 'fileDown2', value:"다운로드"},
								{type: "newcolumn"},
								{type:"button", name: 'fileDel2', value:"삭제"},
							]},
						{type: "block", list: [
								{type: 'input', name: 'fileName3', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
								{type: 'hidden', name: 'fileId3', label: '파일ID'},
								{type: "newcolumn"},
								{type:"button", name: 'fileDown3', value:"다운로드"},
								{type: "newcolumn"},
								{type:"button", name: 'fileDel3', value:"삭제"},
							]},
						{type: "block", list: [
								{type: 'input', name: 'fileName4', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
								{type: 'hidden', name: 'fileId4', label: '파일ID'},
								{type: "newcolumn"},
								{type:"button", name: 'fileDown4', value:"다운로드"},
								{type: "newcolumn"},
								{type:"button", name: 'fileDel4', value:"삭제"},
							]},
						{type: "block", list: [
								{type: 'input', name: 'fileName5', label: '파일 명',  width: 230, value: '', disabled:true, offsetLeft:20},
								{type: 'hidden', name: 'fileId5', label: '파일ID'},
								{type: "newcolumn"},
								{type:"button", name: 'fileDown5', value:"다운로드"},
								{type: "newcolumn"},
								{type:"button", name: 'fileDel5', value:"삭제"},
							]},
						{type: "block", list: [
								{type: "upload", label:"파일업로드" ,name: "file_upload1",width: 384 ,inputWidth: 330 ,url: "/org/common/fileUpLoad4.do" ,autoStart: false, offsetLeft:20, userdata: { limitCount : 5, limitsize: 1000, delUrl:"/org/common/deleteFile2.json"} }
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
				var strRequestKey = PAGE.FORM1.getItemValue("requestKey");

				PAGE.FORM2.setItemValue("strRequestKey", strRequestKey);

				switch (btnId) {
					case "btnSave":
						mvno.cmn.ajax(ROOT + "/org/common/insertMgmt2.json", form, function(resultData) {
							PAGE.FORM2.setItemValue("fileId1", resultData.result.fileId1);
							PAGE.FORM2.setItemValue("fileId2", resultData.result.fileId2);
							PAGE.FORM2.setItemValue("fileId3", resultData.result.fileId3);
							PAGE.FORM2.setItemValue("fileId4", resultData.result.fileId4);
							PAGE.FORM2.setItemValue("fileId5", resultData.result.fileId5);
							mvno.ui.closeWindowById(form);
							mvno.ui.notify("NCMN0015");
							PAGE.FORM2.clear();
						});
						break;
					case "btnClose" :
						mvno.ui.closeWindowById(form);
						break;
					case "fileDown1" :
						mvno.cmn.download('/org/common/downFile2.json?fileId=' + PAGE.FORM2.getItemValue("fileId1"));
						break;
					case "fileDown2" :
						mvno.cmn.download('/org/common/downFile2.json?fileId=' + PAGE.FORM2.getItemValue("fileId2"));
						break;
					case "fileDown3" :
						mvno.cmn.download('/org/common/downFile2.json?fileId=' + PAGE.FORM2.getItemValue("fileId3"));
						break;
					case "fileDown4" :
						mvno.cmn.download('/org/common/downFile2.json?fileId=' + PAGE.FORM2.getItemValue("fileId4"));
						break;
					case "fileDown5" :
						mvno.cmn.download('/org/common/downFile2.json?fileId=' + PAGE.FORM1.getItemValue("fileId5"));
						break;
					case "fileDel1" :
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId1"),orgnId:strRequestKey,attSctn:'REC'} , function(resultData) {
							mvno.ui.notify("NCMN0014");
							PAGE.FORM2.setItemValue("fileName1", "");
							PAGE.FORM2.disableItem("fileDown1");
							PAGE.FORM2.disableItem("fileDel1");
							PAGE.FORM2.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
							PAGE.FORM2.setUserData("file_upload1","limitsize",1000);
							PAGE.FORM2.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
							PAGE.FORM2.showItem("file_upload1");
						});
						break;
					case "fileDel2" :
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId2"),orgnId:strRequestKey,attSctn:'REC'} , function(resultData) {
							mvno.ui.notify("NCMN0014");
							PAGE.FORM2.setItemValue("fileName2", "");
							PAGE.FORM2.disableItem("fileDown2");
							PAGE.FORM2.disableItem("fileDel2");
							PAGE.FORM2.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
							PAGE.FORM2.setUserData("file_upload1","limitsize",1000);
							PAGE.FORM2.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
							PAGE.FORM2.showItem("file_upload1");
						});
						break;
					case "fileDel3" :
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId3"),orgnId:strRequestKey,attSctn:'REC'} , function(resultData) {
							mvno.ui.notify("NCMN0014");
							PAGE.FORM2.setItemValue("fileName3", "");
							PAGE.FORM2.disableItem("fileDown3");
							PAGE.FORM2.disableItem("fileDel3");
							PAGE.FORM2.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
							PAGE.FORM2.setUserData("file_upload1","limitsize",1000);
							PAGE.FORM2.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
							PAGE.FORM2.showItem("file_upload1");
						});
						break;
					case "fileDel4" :
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId4"),orgnId:strRequestKey,attSctn:'REC'} , function(resultData) {
							mvno.ui.notify("NCMN0014");
							PAGE.FORM2.setItemValue("fileName4", "");
							PAGE.FORM2.disableItem("fileDown4");
							PAGE.FORM2.disableItem("fileDel4");
							PAGE.FORM2.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
							PAGE.FORM2.setUserData("file_upload1","limitsize",1000);
							PAGE.FORM2.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
							PAGE.FORM2.showItem("file_upload1");
						});
						break;
					case "fileDel5" :
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId5"),orgnId:strRequestKey,attSctn:'REC'} , function(resultData) {
							mvno.ui.notify("NCMN0014");
							PAGE.FORM2.setItemValue("fileName5", "");
							PAGE.FORM2.disableItem("fileDown5");
							PAGE.FORM2.disableItem("fileDel5");
							PAGE.FORM2.setUserData("file_upload1","limitCount",fileLimitCnt-(resultData.result.fileTotCnt));
							PAGE.FORM2.setUserData("file_upload1","limitsize",1000);
							PAGE.FORM2.setUserData("file_upload1","delUrl","/org/common/deleteFile2.json");
							PAGE.FORM2.showItem("file_upload1");
						});
						break;
				}
			}
		}
	}; /* End DHX */

	var PAGE = {
		title: " ",
		breadcrumb: "${breadCrumb.breadCrumb}",
		buttonAuth: ${buttonAuth.jsonString},
		onOpen:function() {
			$.when($.ajax(fn_loading())).done(function(){
				form1Init();
			});
		}
	};

	function form1RegInit(){
		mvno.ui.setTitle("신청등록");
		//신청등록 신규 신청서 작성 20231229

		if(typeCd != "10") {
			PAGE.FORM1.hideItem("AUTH");
			PAGE.FORM1.disableItem("prodType");
		}

		mvno.ui.showButton("FORM1" , 'btnSave');
		mvno.ui.hideButton("FORM1" , 'btnMod');
		mvno.ui.hideButton("FORM1" , "btnCopy");
		mvno.ui.hideButton("FORM1" , "btnPC0");
		mvno.ui.hideButton("FORM1" , "btnOP0");
		mvno.ui.hideButton("FORM1" , "btnNP1");
		mvno.ui.hideButton("FORM1" , "btnNP2");
		mvno.ui.hideButton("FORM1" , "btnMSG");
		mvno.ui.hideButton("FORM1" , "btnSMS");

		// 약정기간 콤보
		mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0053"}, PAGE.FORM1, "agrmTrm");

		//서식지 등록 스캔 타입 변경
		PAGE.FORM1.setItemValue("scanType","new");

		// 등록시 숨길 메뉴 설정
		hideItems = [
			"CARD"			// 카드정보
			,"BANK"			// 은행정보
			,"FOREIGNER"	// 외국인정보
			,"REQMOVE"		// 번호이동정보
			,"DLVRY"		// 배송정보
			,"faxnum"		// 팩스(신청서정보)
			,"dlvryType"
			,"btnPaper"		// 서식지보기(신청서정보)
			,"SELPHONE"			// 2022-03-07 자급제 폰 정보 추가
			,"moveAuthNumber"	// 20231229 인증항목코드
			,"moveAuthType"		// 20231229 인증처리코드
			,"evntCdPrmt" // 프로모션이벤트코드
			,"FATH" 			// 안면인증
		];

		// 등록시 보일 메뉴 설정
		showItems = [
			"NEWREQ"		// 신규가입
		];

		// 숨길 메뉴 설정
		for(var inx=0; inx < hideItems.length; inx++){
			PAGE.FORM1.hideItem(hideItems[inx]);
		}

		// 보일 메뉴 설정
		for(var inx=0; inx < showItems.length; inx++){
			PAGE.FORM1.showItem(showItems[inx]);
		}

		PAGE.FORM1.showItem("btnPlcFind");	// 정책선택 버튼
		PAGE.FORM1.hideItem("btnPlcFind2");	// 다시선택 버튼
		PAGE.FORM1.hideItem("usePointAmt"); // 포인트사용금액 hide 2022.02.21

		var enableItems = new Array();
		var disableItems = new Array();
		// 신규가입 20231229
		enableItems.push(
				// 신청서 정보
				"reqBuyType","operType","pstate","requestStateCode","reqInDay","shopNm",
				"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnRecSarch","btnFaxSarch","btnPaper","btnKnote",
				// 판매 정보
				"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","salePlcyCd","salePlcyNm","btnPlcFind","btnPlcFind2","prdtCode","hndstAmt","modelInstallment","modelDiscount2","maxDiscount3","modelDiscount3","dcAmt","addDcAmt","realMdlInstamt","reqModelColor","reqPhoneSn","reqUsimSn","usimPayMthdCd","spcCode","requestMemo",
// 						"btnCheckSn",
				// 고객 정보
				"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","selfInqryAgrmYn","selfCertType","selfIssuExprDt"
				,"selfIssuNum","cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl","cstmrTelFn","cstmrTelMn","cstmrTelRn","cstmrMobileFn","cstmrMobileMn","cstmrMobileRn", "btnCheckCstmr",
				// 배송 정보
				"dlvryName","cloneBase","dlvryTelFn","dlvryTelMn","dlvryTelRn","dlvryMobileFn","dlvryMobileMn","dlvryMobileRn","dlvryAddr","dlvryPost","dlvryAddrDtl","btnDlvryPostFind","dlvryMemo",
				// 외국인 정보
				"cstmrForeignerNationSel","btnNatnFind","cstmrForeignerNationTxt","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2","visaCd",
				// 요금납부 정보
				"reqPayType",
				// 카드 정보
				"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2","cloneBaseCard",
				// 은행 정보
				"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2","cloneBaseBank",
				// 이용신청내용
				"additionName","btnRateFind","phonePaymentLabel","phonePayment","joinPayMthdCd","combineSoloType","combineSoloTypeLabel",
				// 신규가입 정보
				"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3",
				// 번호연결서비스
				"reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
				// 번호이동 정보
				"moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","movePenalty", // 20231229 신규 등록 화면 인증항목 코드, 인증 처리코드 삭제
				// 약관동의 정보
				"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd","personalAgreeTitle","othersAgreeTitle","personalInfoCollectAgree","othersTrnsAgree","othersTrnsKtAgree","othersAdReceiveAgree","clauseSensiCollectFlag","clauseSensiOfferFlag", "clauseJehuFlag", "clausePartnerOfferFlag"
				// 단말보험
				, "insrProdCd", "clauseInsrProdFlag"
		);

		disableItems.push(
				"pstate","requestStateCode","cstmrMail2","reqInDay","onlineAuthInfo","onOffType", //,"onlineAuthType"
				"resNo","managerCode","clausePriCollectFlag",
				"clausePriOfferFlag","clauseEssCollectFlag",
				"btnInqrTlphNo", "moveMobileFn","jehuProdType",
		);

		// SRM18072675707, 단체보험
		if(grpInsrYn != "Y") {
			disableItems.push("insrCd");
			disableItems.push("clauseInsuranceFlag");
		}

		// Enable 처리
		for(var inx=0; inx < enableItems.length; inx++){
			PAGE.FORM1.enableItem(enableItems[inx]);
		}

		// Disable 처리
		for(var inx=0; inx < disableItems.length; inx++){
			PAGE.FORM1.disableItem(disableItems[inx]);
		}

		PAGE.FORM1.setItemValue("reqBuyType","UU");					// 구매유형
		PAGE.FORM1.setItemValue("serviceType","PO");				// 후불만 등록 (선불은 MCP에서)
		form1Change(PAGE.FORM1, 0, "init");							// 초기 설정
		// 신청서 정보
		var reqInDay = new Date().format('yyyymmdd');
		PAGE.FORM1.setItemValue("reqInDay", reqInDay);				// 신청일자
		PAGE.FORM1.setItemValue("onlineAuthType", "P");				// 본인인증방식
		PAGE.FORM1.setItemValue("onOffType","1");					// 모집경로       2019.08.20  6(온라인(해피콜)) ->> 1(오프라인) 으로 변경
		PAGE.FORM1.setItemValue("pstate","00");						// 신청서상태
		PAGE.FORM1.setItemValue("requestStateCode","00");			// 진행상태
		PAGE.FORM1.setItemValue("faxyn", "N");						// 팩스
		PAGE.FORM1.setItemValue("faxnum1", "${orgnInfo.faxnum1}");	// 팩스번호1
		PAGE.FORM1.setItemValue("faxnum2", "${orgnInfo.faxnum2}");	// 팩스번호2
		PAGE.FORM1.setItemValue("faxnum3", "${orgnInfo.faxnum3}");	// 팩스번호3
		PAGE.FORM1.setItemValue("cstmrMailReceiveFlag", "Y");		// Mail 수신여부

		PAGE.FORM1.setItemValue("usimPayMthdCd", "3");				// USIM 구매방법 코드
		PAGE.FORM1.setItemValue("joinPayMthdCd", "3");				// JOIN 구매방법 코드[2021.01.07 가입비 기본세팅값 원복 요청 면제 -> 3개월분납]
		//PAGE.FORM1.setItemValue("joinPayMthdCd", "1");				// JOIN 구매방법 코드 [2020.12.10 가입비 기본세팅값 변경 요청 3개월분납 -> 면제]

		PAGE.FORM1.setItemValue("moveMobileFn","010");				// 번호이동번호

		// 약관동의정보
		PAGE.FORM1.setItemValue("clausePriCollectFlag", "Y");		// 개인정보수집 동의
		PAGE.FORM1.setItemValue("clausePriOfferFlag", "Y");			// 개인정보제공 동의
		PAGE.FORM1.setItemValue("clauseEssCollectFlag", "Y");		// 고유식별정보 수집이용제공동의
		//PAGE.FORM1.setItemValue("clausePriTrustFlag", "Y");			// 개인정보 위탁동의
		//PAGE.FORM1.setItemValue("clauseConfidenceFlag", "Y");		// 신용정보 동의

		form1Change(PAGE.FORM1, 0, "reqBuyType");

		// 모델색상
		form1Change(PAGE.FORM1, 0, "prdtId");

		// 팩스 OR 스캔 구성(신청서정보-서식지)
		form1Change(PAGE.FORM1, 0, "faxyn");

		//유심종류 esim 추가
		form1Change(PAGE.FORM1, 0, "usimKindsCd");

		// 요금납부정보
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0076"}, PAGE.FORM1, "reqPayType");

// 		PAGE.FORM1.hideItem("salePlcyCdList");	// 정책선택 combox
		PAGE.FORM1.hideItem("selfIssuNum");		// 발급번호
		PAGE.FORM1.hideItem("reqModelName");
		PAGE.FORM1.hideItem("btnCnclTlphNo");	//번호예약취소
// 		PAGE.FORM1.hideItem("btnCheckSn");
		// 오프라인일때만 배송정보 체크박스 보이도록 수정 20170613 박준성
		var onOffType = PAGE.FORM1.getItemValue("onOffType");

		if(onOffType == "1" || onOffType == "6") {
			PAGE.FORM1.showItem("dlvryYn");
		} else {
			PAGE.FORM1.hideItem("dlvryYn");
		}

	}

	/* function chkMcpRequest() {
		var strRequestKey = "${param.requestKey}";

		mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/chkMcpRequest.json", {requestKey:strRequestKey}, function(resultData) {
			PAGE.FORM1.setItemValue("sysRdate", resultData.result.sysRdate);
		});
	} */

	function form1ReadInit(){
		mvno.ui.setTitle("신청조회");

		mvno.ui.showButton("FORM1" , "btnOP0");
		mvno.ui.showButton("FORM1" , "btnPC0");
		mvno.ui.showButton("FORM1" , "btnMSG");
		mvno.ui.showButton("FORM1" , "btnSMS");
		mvno.ui.showButton("FORM1" , "btnCopy");
		mvno.ui.hideButton("FORM1" , "btnSave");

		// 상품유형 추가
		if(typeCd != "10") PAGE.FORM1.disableItem("prodType");

		// 약정기간 콤보
		mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0053"}, PAGE.FORM1, "agrmTrm");

		var rowData = "";
		var strRequestKey = PAGE.FORM1.getItemValue("requestKey");

		// 조회
		mvno.cmn.ajax2(ROOT + "/rcp/rcpMgmt/rcpListAjax.json", {requestKey:strRequestKey}, function(resultData) {
			rowData = resultData.result.data;

			//20250715 데이터쉐어링 요금제일 경우 체크
			sharingYn = resultData.result.sharingYn;

			// SRM18072675707, 단체보험체크
			if(mvno.cmn.isEmpty(rowData.insrCd) && rowData.clauseInsuranceFlag != "Y") {
				grpInsrYn = "N";
			} else {
				grpInsrYn = "Y";
			}
			var strReqInDay = rowData.reqInDay.replace(/-/g, "");
			mvno.cmn.ajax4lov( ROOT + "/ptnr/grpinsrmgmt/getGrpInsrCombo.json", {reqInDay:strReqInDay}, PAGE.FORM1, "insrCd");

			// 초기 설정
			form1Change(PAGE.FORM1, 0, "init");

			PAGE.FORM1.setFormData(rowData);

			var aryAgent = rowData.agentName.split("/");

			PAGE.FORM1.setItemValue("pAgentCode", rowData.agentCode);
			PAGE.FORM1.setItemValue("pAgentName", aryAgent[0]);

			//PAGE.FORM1.setItemValue("shopCd", rowData.cntpntCd); //wooki

			if(rowData.agentCode == orgId || typeCd == "10"){
				mvno.ui.showButton("FORM1" , "btnMod");
			}else{
				mvno.ui.hideButton("FORM1" , "btnMod");
			}

			// 2016.03.24 FORM1의 scanId 는 계속 스캔하기시 계속 수정되므로 기존 scanId를 가지고 있을 필요 있음.
			PAGE.FORM1.setItemValue("oriScanId", rowData.scanId);

			//---------------------------------------------------------------------------------------------------------
			// 메뉴 숨김/보임 설정 START

			var hideItems = new Array();
			var showItems = new Array();

			// 등록시 숨길 메뉴 설정
			hideItems = [
				"CARD",			// 카드정보
				"BANK",			// 은행정보
				"FOREIGNER",	// 정보
				"REQMOVE",		// 번호이동정보
				"NEWREQ"		// 신규가입
			];

			showItems = [
				"faxnum",		// 팩스(신청서정보)
				"btnPaper",		// 서식지보기(신청서정보)
				"DLVRY",		// 배송정보
				"AUTH"			// 본인인증
			];

			for(var inx=0; inx < hideItems.length; inx++){
				PAGE.FORM1.hideItem(hideItems[inx]);
			}

			for(var inx=0; inx < showItems.length; inx++){
				PAGE.FORM1.showItem(showItems[inx]);
			}

			PAGE.FORM1.hideItem("btnPlcFind");		// 정책선택 버튼
			PAGE.FORM1.showItem("btnPlcFind2");		// 다시선택 버튼
			//PAGE.FORM1.showItem("btnCheckSn");		// 재고확인 버튼
			PAGE.FORM1.showItem("usePointAmt");     // 포인트사용금액 show 2022.02.21

			// 개통이력확인
			if(PAGE.FORM1.getItemValue("requestStateCode") != "00"){
				PAGE.FORM1.hideItem("btnCheckCstmr");
			}

			if(PAGE.FORM1.getItemValue("reqBuyType") == "MM"){
				PAGE.FORM1.hideItem("dlvryType");
			}

			// 메뉴 숨김/보임 설정 END
			//---------------------------------------------------------------------------------------------------------

			//---------------------------------------------------------------------------------------------------------
			// 항목별 Disable/Enable 처리 START

			var enableItems = new Array();
			var disableItems = new Array(); // 20231229 추가

			enableItems.push(
					// 신청서 정보
					"reqBuyType","operType","pstate","requestStateCode","reqInDay","shopNm",
					"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnRecSarch","btnFaxSarch","btnPaper","btnKnote",
					// 판매 정보
					"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","salePlcyCd","salePlcyNm","btnPlcFind","btnPlcFind2","prdtCode","hndstAmt","modelInstallment","modelDiscount2","maxDiscount3","modelDiscount3",
					"dcAmt","addDcAmt","realMdlInstamt","reqModelColor","reqPhoneSn","reqUsimSn","additionName","btnRateFind","phonePaymentLabel","phonePayment","usimPayMthdCd","joinPayMthdCd","spcCode","requestMemo","combineSoloType","combineSoloTypeLabel",
// 							"btnCheckSn",
					// 고객 정보
					"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","selfInqryAgrmYn","selfCertType","selfIssuExprDt"
					,"selfIssuNum","cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl","cstmrTelFn","cstmrTelMn","cstmrTelRn","cstmrMobileFn","cstmrMobileMn","cstmrMobileRn",
					// 배송 정보
					"dlvryName","cloneBase","dlvryTelFn","dlvryTelMn","dlvryTelRn","dlvryMobileFn","dlvryMobileMn","dlvryMobileRn","dlvryAddr","dlvryPost","dlvryAddrDtl","btnDlvryPostFind","dlvryMemo",
					// 외국인 정보
					"cstmrForeignerNationSel","btnNatnFind","cstmrForeignerNationTxt","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2","visaCd",
					// 요금납부 정보
					"reqPayType",
					// 카드 정보
					"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2","cloneBaseCard",
					// 은행 정보
					"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2","cloneBaseBank",
					// 신규가입 정보
					"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3",
					// 번호연결서비스
					"reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
					// 번호이동 정보
					"moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","moveAuthNumber","moveAuthType","movePenalty",
					// 약관동의 정보
					"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd","personalAgreeTitle","othersAgreeTitle","personalInfoCollectAgree","othersTrnsAgree","othersTrnsKtAgree","othersAdReceiveAgree","clauseSensiCollectFlag","clauseSensiOfferFlag",
					//번호조회
					"btnInqrTlphNo"
					// SRM18072675707, 단체보험
					, "insrCd"
			);
			disableItems.push(
					"moveAuthNumber","moveAuthType"
			);

			// Enable 처리
			for(var inx=0; inx < enableItems.length; inx++){
				PAGE.FORM1.enableItem(enableItems[inx]);
			}

			// Disable 처리
			for(var inx=0; inx < disableItems.length; inx++){
				PAGE.FORM1.disableItem(disableItems[inx]);
			}

			// 항목별 Disable/Enable 처리 END
			//---------------------------------------------------------------------------------------------------------

			//---------------------------------------------------------------------------------------------------------
			// 화면 구성 START

			// 팩스 OR 스캔 구성(신청서정보-서식지)
			form1Change(PAGE.FORM1, 0, "faxyn");

			// 신청유형에 따른 화면 구성(신규가입/번호이동정보)
			form1Change(PAGE.FORM1, 0, "operType");

			// 고객구분에 따른 화면 구성(내국인/미성년/외국인/사업자)
			form1Change(PAGE.FORM1, 0, "cstmrType");

			// 구매유형에 따른 화면 구성
			form1Change(PAGE.FORM1, 0, "reqBuyType");

			// 단말모델색상
			form1Change(PAGE.FORM1, 0, "reqModelColor");

			form1Change(PAGE.FORM1, 0, "combineSoloType");

			// 화면 구성 END
			//---------------------------------------------------------------------------------------------------------

			//---------------------------------------------------------------------------------------------------------
			// 추가 값 설정 START

			// 신청일자
			PAGE.FORM1.setItemValue("reqInDayStr", rowData.reqInDay);
			form1Change(PAGE.FORM1, 0, "reqInDay");

			// 요금납부정보
			// 서비스타입에 따라 요금납부정보 콤보 리스트가 달라짐
			form1Change(PAGE.FORM1, 0, "serviceType");
			PAGE.FORM1.setItemValue("reqPayType", rowData.reqPayType);
			form1Change(PAGE.FORM1, 0, "reqPayType");

			//20220905 신청서 읽어올때 유심종류가 esim 이 아닌 경우에는 esim으로 변경 못함
			if(rowData.usimKindsCd != "09"){
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2035" , etc1:"1"}, PAGE.FORM1, "usimKindsCd"); // 유심종류
				PAGE.FORM1.setItemValue("usimKindsCd", rowData.usimKindsCd );
			}else {
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2035"}, PAGE.FORM1, "usimKindsCd"); // 유심종류
				PAGE.FORM1.setItemValue("usimKindsCd", rowData.usimKindsCd );
				//유심종류
				form1Change(PAGE.FORM1, 0, "usimKindsCd");
				PAGE.FORM1.disableItem("usimKindsCd");
			}

			// 구매유형/신청유형/모집경로 에 따라 단말모델/요금제 값이 자동변경되기 때문에
			// 이곳에서 재설정 필요
			PAGE.FORM1.setItemValue("reqBuyType", rowData.reqBuyType);			// 구매유형
			PAGE.FORM1.setItemValue("operType", rowData.operType);				// 신규유형
			PAGE.FORM1.setItemValue("onOffType", rowData.onOffType);			// 모집경로
			PAGE.FORM1.setItemValue("mnfctId", rowData.mnfctId);				// 제조사
			PAGE.FORM1.setItemValue("prdtId", rowData.prdtId);					// 단말모델
			PAGE.FORM1.setItemValue("socCode", rowData.socCode);				// 요금제

			if("NAC3" == rowData.operType){
				mvno.ui.hideButton("FORM1" , "btnNP1");
				mvno.ui.hideButton("FORM1" , "btnNP2");
			}else if("MNP3" == rowData.operType){
				mvno.ui.hideButton("FORM1" , "btnNP1");
				mvno.ui.showButton("FORM1" , "btnNP2");
			}


			// 모델색상
			form1Change(PAGE.FORM1, 0, "prdtId");
			PAGE.FORM1.setItemValue("reqModelColor", rowData.reqModelColor);	// 단말모델색상

			if(rowData.reqBuyType == "UU"){
				PAGE.FORM1.setItemValue("prdtCode", rowData.reqUsimName);			// USIM모델코드
				if(rowData.onOffType == "0" || rowData.onOffType == "3" || rowData.onOffType == "5"){
					PAGE.FORM1.setItemValue("reqModelName", rowData.reqModelName);			// 단말모델명
					PAGE.FORM1.showItem("reqModelName");		// 단말모델명
				}else{
					PAGE.FORM1.hideItem("reqModelName");		// 단말모델명
				}
			}
			else {
				PAGE.FORM1.setItemValue("prdtCode", rowData.reqModelName);			// USIM모델코드
				PAGE.FORM1.hideItem("reqModelName");		// 단말모델명
			}

			// 단말기일련번호
			PAGE.FORM1.setItemValue("reqPhoneSn", rowData.reqPhoneSn);			// reqPhoneSn

			// 개통이력확인
			if(PAGE.FORM1.getItemValue("requestStateCode") != "00"){
				PAGE.FORM1.setItemValue("checkCstmr", "Y");
			}

			PAGE.FORM1.setItemValue("checkSn", "Y");
			// 온라인에서 받은 데이터를 수정할 수 있도록 하기 위해 이부분은 제거
			if( rowData.reqBuyType == "MM"){
				if(!rowData.reqPhoneSn || rowData.reqPhoneSn == '') {
					PAGE.FORM1.setItemValue("checkSn", "N");
				}
			}

			// 추가 값 설정 END
			//---------------------------------------------------------------------------------------------------------

			// Disable 처리

			var disableItems = new Array();

			// 접수 = 00:접수대기,01:접수,02:해피콜,09:배송대기,10:배송중,20:개통대기,21:개통완료
			/* if(rowData.requestStateCode == "00"){ */
			if(rowData.requestStateCode != "21"){
				disableItems.push(
						// 신청서 정보
						"reqBuyType","operType","reqInDay","shopNm","prodType","btnShopFind","shopNm",
// 								"pstate","requestStateCode",
						"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnKnote",
// 								"btnRegPaper","btnScnSarch","btnFaxSarch",
						// 판매 정보
						// "reqModelColor", 온라인에서 신청한 색상을 변경가능하게 함 by 2015-09-10
						"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","salePlcyCd","salePlcyNm","btnPlcFind","btnPlcFind2","prdtCode","hndstAmt","modelInstallment","modelDiscount2","maxDiscount3","modelDiscount3","dcAmt","addDcAmt","realMdlInstamt","phonePaymentLabel","phonePayment","combineSoloType","combineSoloTypeLabel",
// 								"btnRateFind","additionName",
						// 유심비/가입비
// 								"usimPayMthdCd","joinPayMthdCd",
						// 고객 정보
						"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","cstmrPost","cstmrAddr",
// 								"cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","btnCstmrPostFind","cstmrAddrDtl","cstmrTelFn","cstmrTelMn","cstmrTelRn","cstmrMobileFn","cstmrMobileMn","cstmrMobileRn",
						//"selfInqryAgrmYn","selfCertType","selfIssuExprDt","selfIssuNum",
						// 외국인 정보
// 								"cstmrForeignerNationSel","cstmrForeignerNationTxt","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2",
// 								// 요금납부 정보
// 								"reqPayType",
// 								// 카드 정보
// 								"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2","cloneBaseCard",
// 								// 은행 정보
// 								"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2","cloneBaseBank",
						// 신규가입 정보
						"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3",
						// 번호연결서비스
						"reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
						// 번호이동 정보
// 								"moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","moveAuthNumber","moveAuthType","movePenalty",
						// 약관동의 정보
						"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd","clauseJehuFlag","clauseFinanceFlag","personalAgreeTitle","othersAgreeTitle","personalInfoCollectAgree","othersTrnsAgree","othersTrnsKtAgree","othersAdReceiveAgree","clauseSensiCollectFlag","clauseSensiOfferFlag", "clausePartnerOfferFlag", "jehuProdType"
						// 단말보험
						, "insrProdCd", "clauseInsrProdFlag"
						// 자급제
						, "phoneStateCd", "lastAmt", "sesplsPrdtNm", "sesplsProdId", "sesplsProdCol", "hndsetSalePrice", "cardTotDcAmt", "usePoint", "subscriberNo"
				);
			}
			else if(rowData.requestStateCode == "21"){
				disableItems.push(
						// 신청서 정보
						"reqBuyType","operType","reqInDay","shopNm","prodType","btnShopFind",
						"pstate","requestStateCode",
						"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnFaxSarch","btnKnote",
						// 판매 정보
						"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","salePlcyCd","salePlcyNm","btnPlcFind","btnPlcFind2","prdtCode","hndstAmt","modelInstallment","modelDiscount2","maxDiscount3","modelDiscount3","dcAmt","addDcAmt","realMdlInstamt","reqModelColor","phonePaymentLabel","phonePayment",
						"usimPayMthdCd","joinPayMthdCd","spcCode","requestMemo","reqPhoneSn","reqUsimSn","additionName","btnRateFind","combineSoloType","combineSoloTypeLabel",
// 								"btnCheckSn",
						// 고객 정보
						"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl","cstmrTelFn","cstmrTelMn","cstmrTelRn","cstmrMobileFn","cstmrMobileMn","cstmrMobileRn",
						"selfInqryAgrmYnLabel","selfInqryAgrmYn","selfCertType","selfIssuExprDt","selfIssuNum",
						// 배송 정보
						"dlvryYn","dlvryName","cloneBase","dlvryTelFn","dlvryTelMn","dlvryTelRn","dlvryMobileFn","dlvryMobileMn","dlvryMobileRn","dlvryAddr","dlvryPost","dlvryAddrDtl","btnDlvryPostFind","dlvryMemo",
						// 외국인 정보
						"cstmrForeignerNationSel","btnNatnFind","cstmrForeignerNationTxt","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2","visaCd",
						// 요금납부 정보
						"reqPayType",
						// 카드 정보
						"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2","cloneBaseCard",
						// 은행 정보
						"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2","cloneBaseBank",
						// 신규가입 정보
						"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3","btnInqrTlphNo",
						// 번호연결서비스
						"reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
						// 번호이동 정보
						"moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","moveAuthNumber","moveAuthType","movePenalty",
						// 약관동의 정보
						"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd","clauseJehuFlag","clauseFinanceFlag","personalAgreeTitle","othersAgreeTitle","personalInfoCollectAgree","othersTrnsAgree","othersTrnsKtAgree","othersAdReceiveAgree","clauseSensiCollectFlag","clauseSensiOfferFlag","clausePartnerOfferFlag", "jehuProdType",
						// 번호예약
						"tlphNo","btnCnclTlphNo",
						// 유심종류
						"usimKindsCd"
						// SRM18072675707, 단체보험
						, "insrCd"
						// 단말보험
						, "insrProdCd", "clauseInsrProdFlag"
						// 자급제
						, "phoneStateCd", "lastAmt", "sesplsPrdtNm", "sesplsProdId", "sesplsProdCol", "hndsetSalePrice", "cardTotDcAmt", "usePoint", "subscriberNo"
						//20220914 esim관련 단말기 정보
						,"intmMdlId" , "intmSrlNo"
						,"eid", "imei1", "imei2", "prntsCtnFn", "prntsCtnMn", "prntsCtnRn", "billAcntNo", "prntsContractNo"
				);
			}

			// Disable 처리
			for(var inx=0; inx < disableItems.length; inx++){
				PAGE.FORM1.disableItem(disableItems[inx]);
			}

			var usimKindsCd = PAGE.FORM1.getItemValue("usimKindsCd");
			if(usimKindsCd == "09"){
				PAGE.FORM1.showItem("eid");
				PAGE.FORM1.showItem("imei1");
				PAGE.FORM1.showItem("imei2");
				PAGE.FORM1.showItem("intmMdlId");
				PAGE.FORM1.showItem("intmSrlNo");
				PAGE.FORM1.showItem("eReqModelName");

				PAGE.FORM1.showItem("prntsCtnFn");
				PAGE.FORM1.showItem("prntsCtnMn");
				PAGE.FORM1.showItem("prntsCtnRn");
				PAGE.FORM1.showItem("billAcntNo");
				PAGE.FORM1.showItem("prntsContractNo");
			}else{
				PAGE.FORM1.hideItem("eid");
				PAGE.FORM1.hideItem("imei1");
				PAGE.FORM1.hideItem("imei2");
				PAGE.FORM1.hideItem("intmMdlId");
				PAGE.FORM1.hideItem("intmSrlNo");
				PAGE.FORM1.hideItem("eReqModelName");

				PAGE.FORM1.hideItem("prntsCtnFn");
				PAGE.FORM1.hideItem("prntsCtnMn");
				PAGE.FORM1.hideItem("prntsCtnRn");
				PAGE.FORM1.hideItem("billAcntNo");
				PAGE.FORM1.hideItem("prntsContractNo");
			}

			// 렌탈상품인 경우 재고확인 버튼 disable 처리
			if(rowData.prodType == "02"){
				if(rowData.onOffType != "1" && rowData.onOffType != "6"){
					PAGE.FORM1.disableItem("reqPhoneSn");
					PAGE.FORM1.disableItem("reqUsimSn");
				}
// 				PAGE.FORM1.disableItem("btnCheckSn");
				PAGE.FORM1.disableItem("reqModelColor");
				PAGE.FORM1.showItem("DLVRY");
			}

			// 상품구분 "자급제" 인 경우
			if(rowData.prodType == "04") {
				PAGE.FORM1.showItem("SELPHONE");// 자급제 정보 표시
			} else {
				PAGE.FORM1.hideItem("SELPHONE");// 자급제 정보 표시
			}

			if(rowData.requestStateCode != "00") {
				PAGE.FORM1.hideItem("cloneBaseCard");
				PAGE.FORM1.hideItem("cloneBaseBank");
			}else{
				PAGE.FORM1.showItem("cloneBaseCard");
				PAGE.FORM1.showItem("cloneBaseBank");
			}

			PAGE.FORM1.hideItem("salePlcyCdList");	// 정책선택 combox
			PAGE.FORM1.disableItem("pAgentName");	// 대리점
			PAGE.FORM1.disableItem("btnOrgFind");	// 대리점 찾기
			PAGE.FORM1.hideItem("btnCnclTlphNo");	//번호예약취소
// 			PAGE.FORM1.hideItem("btnCheckSn");
			var selfCertType = PAGE.FORM1.getItemValue("selfCertType");

			if(selfCertType == "02" || selfCertType == "04"){
				PAGE.FORM1.showItem("selfIssuNum");
			}else{
				PAGE.FORM1.hideItem("selfIssuNum");
			}

			if(selfCertType == "05"){
				PAGE.FORM1.setItemLabel("selfIssuExprDt", "만료일자");
			}else{
				PAGE.FORM1.setItemLabel("selfIssuExprDt", "발급일자");
			}

			//번호예약 조회
			mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRcpSimpRsvTlphNoByInfo.json", {resNo:rowData.resNo}, function(resultData) {
				var rsltVo = resultData.result.data;

				if(rsltVo != null){
					PAGE.FORM1.setItemValue("tlphNo", rsltVo.tlphNo);

					PAGE.FORM1.showItem("btnCnclTlphNo");	//번호예약취소
					PAGE.FORM1.hideItem("btnInqrTlphNo");	//번호조회
				}else{
					PAGE.FORM1.setItemValue("tlphNo", "");

					PAGE.FORM1.hideItem("btnCnclTlphNo");	//번호예약취소
					PAGE.FORM1.showItem("btnInqrTlphNo");	//번호조회
				}
			});


		});

		//개통완료인 경우
		if(PAGE.FORM1.getItemValue("requestStateCode") == "21"){
			PAGE.FORM1.disableItem("fathMobileFn");
			PAGE.FORM1.disableItem("fathMobileMn");
			PAGE.FORM1.disableItem("fathMobileRn");
			PAGE.FORM1.disableItem("btnFT0");
			PAGE.FORM1.disableItem("btnFS8");
			PAGE.FORM1.disableItem("btnReqFathTxnRetv");
			PAGE.FORM1.disableItem("btnFath");
			PAGE.FORM1.disableItem("btnFT1");
		}

		// 오프라인일때만 배송정보 체크박스 보이도록 수정 20170613 박준성
		var onOffType = PAGE.FORM1.getItemValue("onOffType");

		if(onOffType == "1" || onOffType == "6") {
			PAGE.FORM1.showItem("dlvryYn");

			var dlvryName = PAGE.FORM1.getItemValue("dlvryName");

			if(dlvryName != null && dlvryName != ""){
				PAGE.FORM1.showItem("DLVRY");
				PAGE.FORM1.setItemValue("dlvryYn","1");
			}else{
				PAGE.FORM1.hideItem("DLVRY");
				PAGE.FORM1.setItemValue("dlvryYn","0");
			}
		} else {
			PAGE.FORM1.hideItem("dlvryYn");
		}

		/* v2018.01 KT판매코드 적용 */
		var useYn = 'Y';
		if(rowData.requestStateCode == "21"){
			useYn = '';
		}
		getKtPlcyListCombo(PAGE.FORM1, useYn);
		PAGE.FORM1.setItemValue("spcCode", rowData.spcCode);

		/* 프로모션명 적용 */

		PAGE.FORM1.setItemValue("disPrmtId", rowData.prmtId);
		PAGE.FORM1.setItemValue("disPrmtNm", rowData.prmtNm);
		PAGE.FORM1.setItemValue("totalDiscount",rowData.disCnt);


		//v2018.03 외국인 국가 설정
		PAGE.FORM1.setItemValue("cstmrForeignerNationSel" ,rowData.cstmrForeignerNation);
		PAGE.FORM1.setItemValue("cstmrForeignerNationTxt" ,rowData.cstmrForeignerNation);

		// 최종버튼처리
		if(rowData.requestStateCode == "21"){
			mvno.ui.hideButton("FORM1" , "btnNP1");
			mvno.ui.hideButton("FORM1" , "btnNP2");
			mvno.ui.hideButton("FORM1" , "btnPC0");
			mvno.ui.hideButton("FORM1" , "btnOP0");
			mvno.ui.hideButton("FORM1" , "btnMSG");
			mvno.ui.hideButton("FORM1" , "btnSMS");
			mvno.ui.hideButton("FORM1" , "btnCopy");
			mvno.ui.hideButton("FORM1" , "btnSave");
			mvno.ui.hideButton("FORM1" , "btnMod");
			mvno.ui.showButton("FORM1" , "btnNew");
		}

		//단말보험
		PAGE.FORM1.setItemValue("insrProdCd", rowData.insrProdCd);

		//20230718 자급제보상서비스
		PAGE.FORM1.setItemValue("rwdProdCd", rowData.rwdProdCd);
		PAGE.FORM1.setItemValue("clauseRwdFlag", rowData.clauseRwdFlag);

		setPrmtResultItem();

		// 상품구분 "자급제" 인 경우
		if(rowData.prodType == "04") {
			PAGE.FORM1.hideItem("hndstAmt"); // 출고가 // 판매정보(핸드폰대금) 단말 부분 안보이게 설정
			PAGE.FORM1.hideItem("modelDiscount2"); // 공시지원금
			PAGE.FORM1.hideItem("sprtTp"); // 할인유형
			PAGE.FORM1.hideItem("modelDiscount3"); // 추가지원금
			PAGE.FORM1.hideItem("maxDiscount3"); // 추가지원금(MAX)
			PAGE.FORM1.hideItem("drctMngmPrdcNm"); // 직영상품명 추가
			PAGE.FORM1.hideItem("dcAmt"); // 기본할인금액
			PAGE.FORM1.hideItem("addDcAmt"); // 추가할인금액
			PAGE.FORM1.hideItem("modelInstallment"); // 할부원금
			PAGE.FORM1.hideItem("reqModelName"); // 휴대폰모델명
		}
		//20240307 K-Note 추가
		PAGE.FORM1.setItemValue("frmpapId", rowData.frmpapId);		//서식지ID
		PAGE.FORM1.setItemValue("mngmAgncId", rowData.mngmAgncId);	//대리점코드
		PAGE.FORM1.setItemValue("cntpntCd",rowData.cntpntCd);		//접점코드

		//20250715 데이터쉐어링 요금제일 경우 필드 설정
		if(sharingYn == "Y"){
			PAGE.FORM1.showItem("prntsCtnFn");
			PAGE.FORM1.showItem("prntsCtnMn");
			PAGE.FORM1.showItem("prntsCtnRn");
			PAGE.FORM1.showItem("billAcntNo");
			PAGE.FORM1.showItem("prntsContractNo");

			PAGE.FORM1.disableItem("prntsCtnFn");
			PAGE.FORM1.disableItem("prntsCtnMn");
			PAGE.FORM1.disableItem("prntsCtnRn");
			PAGE.FORM1.disableItem("billAcntNo");
			PAGE.FORM1.disableItem("prntsContractNo");
		}
	}

	function form1Change(form, mFlag, name){

		var showItems = new Array();
		var hideItems = new Array();
		var enableItems = new Array();
		var disableItems = new Array();

		switch(name){
				// 단말보험
			case "insrProdCd":
				if(mvno.cmn.isEmpty(form.getItemValue("insrProdCd"))) {
					form.setItemValue("clauseInsrProdFlag", "");
				} else {
					form.setItemValue("clauseInsrProdFlag", "Y");
				}

				break;
				// 2019-01-31, [SRM19013055775] 신청등록(직접개통) 內 개인정보 정보/광고 전송 동의 선택 방식 변경 개발 요청
												
			case "clausePriAdFlag":
				mvno.ui.alert("서식지 내의 정보/광고 수신 위탁 동의 여부와 일치해야 합니다");
				break;

			case 'init' :
				// 제조사 리스트
				mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpCommon.json", {grpId:"MNFCT"}, form, "mnfctId");

				break;

			case 'reqPhoneSn' :
				form.setItemValue('checkSn', 'N');
				break;

			case 'reqBuyType' :
				//"MM:단말구매,UU:유심단독구매"
				var value = form.getItemValue(name);

				if(form.getItemValue("requestStateCode") != "21") {
					if(value == 'MM') {
						enableItems = ["reqModelColor", "reqPhoneSn", "sprtTp"];	// "btnCheckSn",
						//유심단독구매인 경우에만 유심종류에 esim 이 들어간다.
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2035" , etc1 :  "1"}, PAGE.FORM1, "usimKindsCd"); // 유심종류
						hideItems = ["eid", "imei1", "imei2", "intmMdlId" , "intmSrlNo" , "eReqModelName"];
						form.setItemValue("eid", "");
						form.setItemValue("imei1", "");
						form.setItemValue("imei2", "");
						form.setItemValue("intmMdlId", "");
						form.setItemValue("intmSrlNo", "");
						form.setItemValue("eReqModelName", "");

						// 데이터쉐어링 요금제가 아닐경우 필드 설정
						if(sharingYn != "Y"){
							hideItems = ["prntsCtnFn", "prntsCtnMn", "prntsCtnRn", "billAcntNo", "prntsContractNo"];
							form.setItemValue("prntsCtnFn", "");
							form.setItemValue("prntsCtnMn", "");
							form.setItemValue("prntsCtnRn", "");
							form.setItemValue("billAcntNo", "");
							form.setItemValue("prntsContractNo", "");
						}

					} else {
						disableItems = ["reqModelColor", "sprtTp"];	// "btnCheckSn",

						if(form.getItemValue("prodType") != "02" || (form.getItemValue("onOffType") != "1" && form.getItemValue("onOffType") != "6") ){
							disableItems.push("reqPhoneSn");
						}
						//유심단독구매인경우에만 유심종류가 esim 이 보임
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2035"}, PAGE.FORM1, "usimKindsCd"); // 유심종류
					}
				}

				// 단말모델 콤보박스 적용
				form1Polcy(form, "PRDT", "prdtId");

				// 요금제 적용
				form1Polcy(form, "RATE", "socCode");

				// 단말보험
				getIntmInsrComboList(form, form.getItemValue("prdtId"), form.getItemValue("reqBuyType"));

				// 프로모션명 / 총할인액
				getChrgPrmtListCombo(form, "Y");

				break;

			case 'operType' :
				var value = form.getItemValue(name);

				if(mFlag == 1) {
					// 초기화
					operTypeInit();
					salePlcyCdListInit();
				}

				hideItems = [
					"REQMOVE",	// 번호이동정보
					"NEWREQ"	// 신규가입
				];

				// "MNP3:번호이동,NAC3:개통,HCN3:기기변경"
				if(value == 'MNP3'){
					showItems = ["REQMOVE"];
				} else if(value == 'NAC3'){
					showItems = ["NEWREQ"];
				}

				if(form.getItemValue("prodType") == "02"){
					showItems.push("DLVRY");
				}

				// 단말모델 콤보박스 적용
				form1Polcy(form, "PRDT", "prdtId");

				// 요금제 적용
				form1Polcy(form, "RATE", "socCode");

				// 프로모션 적용
				getChrgPrmtListCombo(form, "Y");
				break;

			case 'mnfctId' :
				// 단말모델 콤보박스 적용
				form1Polcy(form, "PRDT", "prdtId");
				// 단말색상 적용
				form1Change(PAGE.FORM1, 0, "prdtId");

				break;

			case 'cstmrType' :
				var value = form.getItemValue(name);

				if(mFlag == 1) {
					// 초기화
					cstmrInit();
				}

				hideItems = [
					"FOREIGNER"	// 외국인정보
				];

				//"NA:내국인,NM:내국인(미성년자),FN:외국인,JP:법인,PP:개인사업자"
				switch (value) {

					case "FN" :	// 외국인
						showItems = ["FOREIGNER"];

						break;

					default :	// 내국인
						break;
				}

				break;

			case 'faxyn':
				var value = form.getItemValue(name);

				var onOffType = form.getItemValue("onOffType");

				// 숨김 항목 설정
				hideItems = [
					"DLVRY",
					"faxyn",		// 스캔/팩스 여부
					"btnRegPaper",	// 스캔버튼 (서식지)
					"faxnum",		// 팩스 번호 입력 부분 (서식지)
					"btnFaxSarch",	// URL검색
					"btnScnSarch",	// 스캔검색
					"btnRecSarch",	// 녹취파일등록
					"btnKnote"		// K-Note조회
				];

				// 보일 항목 설정
				// 팩스 선택
				if(value == 'Y') {
					if(onOffType == "1")
						showItems = ["faxyn", "faxnum", "btnFaxSarch"];	// 팩스 번호 입력 부분, 팩스 검색
					else
						showItems = ["DLVRY", "faxyn", "faxnum", "btnFaxSarch"];	// 팩스 번호 입력 부분, 팩스 검색

				}
				// K-Note 선택
				else if(value == "K"){
					// 기존 팩스 내역 삭제
					form.setItemValue("faxnum1", "");
					form.setItemValue("faxnum2", "");
					form.setItemValue("faxnum3", "");

					showItems = ["faxyn", "btnKnote"];			// K-note조회
				}

				// 스캔 선택
				else {
					// 기존 팩스 내역 삭제
					form.setItemValue("faxnum1", "");
					form.setItemValue("faxnum2", "");
					form.setItemValue("faxnum3", "");

					// 오프라인
					if(onOffType == "1" || onOffType == "6")
					{
						showItems = ["faxyn", "btnRegPaper", "btnScnSarch", "btnRecSarch"];	// 스캔버튼, 스캔검색
					}
					else
					{
						showItems = ["DLVRY", "btnRecSarch"];	// 녹취파일등록
					}
				}
				break;

				// 배송정보 입력가능 하도록 체크박스 추가 20170610 박준성
			case 'dlvryYn':
				var dlvryYn = form.getItemValue("dlvryYn");
				if(dlvryYn == '1'){
					showItems = ["DLVRY"];
				} else {
					hideItems = ["DLVRY"];
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
					"btnFaxSarch",	// URL검색
					"btnScnSarch",	// 스캔검색
					"btnRecSarch"	// 녹취파일등록
				];

				// 기존 팩스 내역 삭제
				form.setItemValue("faxnum1", "");
				form.setItemValue("faxnum2", "");
				form.setItemValue("faxnum3", "");

				// 오프라인
				if(onOffType == "1" || onOffType == "6")
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
// 					mvno.ui.reloadLOV(PAGE.FORM1, "reqPayType", "RCP0008");
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2015"}, PAGE.FORM1, "reqPayType");
				} else {
// 					mvno.ui.reloadLOV(PAGE.FORM1, "reqPayType", "RCP0029");
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2016"}, PAGE.FORM1, "reqPayType");
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
				];

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

				// 단말모델 콤보박스 적용
				form1Polcy(form, "PRDT", "prdtId");

				// 요금제 적용
				form1Polcy(form, "RATE", "socCode");

				// 프로모션 적용
				getChrgPrmtListCombo(form, "Y");

				break;

			case 'prdtId':
				// 단말모델 선택 시, 색상 리스트 가져오기
				if(form.getItemValue("reqBuyType") == "MM" && form.getItemValue("prodType") != "02") {
					mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpCommon.json",
							{
								grpId:"COLOR"
								,prdtId:form.getItemValue("prdtId")
								,mnfctId:form.getItemValue("mnfctId")
							},
							form,
							"reqModelColor");
				}

				salePlcyCdListInqr(form, "salePlcyCdList");	//정책 조회 combo-box

				// 단말보험
				getIntmInsrComboList(form, form.getItemValue("prdtId"), form.getItemValue("reqBuyType"));

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
					enableItems = ["cstmrMail2"];
				} else {
					disableItems = ["cstmrMail2"];
					form.setItemValue("cstmrMail2", value);
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

				if( mvno.cmn.isEmpty(form.getItemValue("salePlcyCd")) ){
					form.setItemValue("mnfctId", "");	// 제조사
					form.setItemValue("prdtId", "");	// 단말모델
					form.setItemValue("prdtCode", "");	// 단말코드
					form1Change(form, 0, "prdtId");		// 단말색상 초기화
					form.enableItem("mnfctId");
					form.enableItem("prdtId");

					salePlcyCdListInit();
				}

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
			case 'socCode' :
				salePlcyCdListInqr(form, "salePlcyCdList");	//정책 조회 combo-box

				getChrgPrmtListCombo(form, "Y"); // 프로모션 적용

				// 요금제 변경에 따른 처리 (제휴처)
				enableItems.push("clauseJehuFlag");
				form.setItemValue("clauseJehuFlag", "");
				form.setItemValue("jehuProdType", "");

				updateCombineSoloVisible(form.getItemValue(name));

				break;
			case 'agrmTrm' :
				salePlcyCdListInqr(form, "salePlcyCdList");	//정책 조회 combo-box

				getChrgPrmtListCombo(form, "Y"); // 프로모션 적용
				break;
			case 'instNom' :
				salePlcyCdListInqr(form, "salePlcyCdList");	//정책 조회 combo-box

				break;
			case 'salePlcyCdList' :
				if(mvno.cmn.isEmpty(salePlcyData)){
					return;
				}

				form.disableItem("salePlcyCdList");			// 정책선택(combo)
				form.showItem("salePlcyNm");				// 정책명
				form.hideItem("salePlcyCdList");			// 정책선택 combox

				var indexNo = Number(form.getItemValue(name));

				form.setItemValue("reqBuyType",salePlcyData.result.data.rows[indexNo].reqBuyType);				// 구매유형
				form1Change(PAGE.FORM1, 0, "reqBuyType");
				form.setItemValue("operType", salePlcyData.result.data.rows[indexNo].operType);					// 신청유형
				form1Change(PAGE.FORM1, 0, "operType");
				form.setItemValue("cntpntShopId", salePlcyData.result.data.rows[indexNo].orgnId);				// 조직ID
				form.setItemValue("salePlcyCd", salePlcyData.result.data.rows[indexNo].salePlcyCd);				// 판매정책코드
				form.setItemValue("prdtId", salePlcyData.result.data.rows[indexNo].prdtId);						// 단말모델ID
				form.setItemValue("prdtNm", salePlcyData.result.data.rows[indexNo].prdtNm);						// 단말모델명
				form.setItemValue("socCode", salePlcyData.result.data.rows[indexNo].rateCd);					// 요금제
				form.setItemValue("salePlcyNm", salePlcyData.result.data.rows[indexNo].salePlcyNm);				// 판매정책명
				form.setItemValue("modelDiscount2", salePlcyData.result.data.rows[indexNo].modelDiscount2);		// 공시지원금
				form.setItemValue("modelDiscount3", salePlcyData.result.data.rows[indexNo].modelDiscount3);		// 대리점지원금
				form.setItemValue("maxDiscount3", salePlcyData.result.data.rows[indexNo].maxDiscount3);			// 대리점지원금(MAX)

				// 요금제 값 변경에 따른 처리 (제휴처)
				form.enableItem("clauseJehuFlag")
				form.setItemValue("clauseJehuFlag", "");
				form.setItemValue("jehuProdType", "");

				// 대리점 변경에 따른 처리 (제휴사)
				form.enableItem("clausePartnerOfferFlag");
				form.setItemValue("clausePartnerOfferFlag", "");

				// 최초에는 할부원금이 정책과 사용자 모두 동일
				form.setItemValue("realMdlInstamt", salePlcyData.result.data.rows[indexNo].realMdlInstamt);		// 할부원금(사용자)
				form.setItemValue("modelInstallment", salePlcyData.result.data.rows[indexNo].realMdlInstamt);	// 할부원금(정책)

				// Hidden Value
				form.setItemValue("modelId", salePlcyData.result.data.rows[indexNo].prdtId);					// 모델ID

				if(salePlcyData.result.data.rows[indexNo].plcyTypeCd == "R"){
					form.setItemValue("prodType", "02");		// 렌탈일경우 가입 유형 설정
				}

				form.setItemValue("reqModelName", 	salePlcyData.result.data.rows[indexNo].prdtCode);			// 단말모델코드

				// modelPrice : 정책설정시 단말구매시에만 값이 들어가도록 설정되어 있음.
				form.setItemValue("modelPrice", salePlcyData.result.data.rows[indexNo].modelPrice);				// 출고단가(미포함)
				form.setItemValue("hndstAmt", salePlcyData.result.data.rows[indexNo].hndstAmt);					// 출고단가(포함)
				form.setItemValue("usimPrice", salePlcyData.result.data.rows[indexNo].usimPrice);				// USIM비
				form.setItemValue("recycleYn", salePlcyData.result.data.rows[indexNo].oldYn);					// 중고여부
				form.setItemValue("joinPrice", salePlcyData.result.data.rows[indexNo].joinPrice);				// 가입비
				form.setItemValue("sprtTp", salePlcyData.result.data.rows[indexNo].sprtTp);						// 할인유형
				form.setItemValue("dcAmt", salePlcyData.result.data.rows[indexNo].dcAmt);						// 할인금액
				form.setItemValue("addDcAmt", salePlcyData.result.data.rows[indexNo].addDcAmt);					// 추가할인금액

				//-----------------------------------------------------------------------------------
				// 정책이 선택이 되면,
				// 1. 정책이 선택되면, 정책선택버튼은 사라지고, 다시선택버튼은 보임.
				form.showItem("btnPlcFind2");				// 정책 다시 선택 버튼
				form.hideItem("btnPlcFind");				// 정책 선택 버튼
				// 2. Disable 처리
				form.disableItem("reqBuyType");				// 구매유형
				form.disableItem("operType");				// 신청유형
				form.disableItem("mnfctId");				// 제조사
				form.disableItem("prdtId");					// 단말모델
				form.disableItem("socCode");				// 요금제
				form.disableItem("agrmTrm");				// 약정기간
				form.disableItem("instNom");				// 약정기간
				form.disableItem("sprtTp");					// 할인유형

				/* v2018.01 KT판매코드 적용 */
				getNoAgrmTrtm(form);
				getKtPlcyListCombo(form, 'Y');
				updateCombineSoloVisible(form.getItemValue("socCode"));

				break;

			case 'selfCertType' :
				var selfCertType = form.getItemValue(name);

				if(selfCertType == "02" || selfCertType == "04"){
					form.showItem("selfIssuNum");
				}else{
					form.hideItem("selfIssuNum");
				}

				if(selfCertType == "05"){
					form.setItemLabel("selfIssuExprDt", "만료일자");
				}else{
					form.setItemLabel("selfIssuExprDt", "발급일자");
				}

				//안면인증 사용여부 공통코드
				if(mFlag === 0 && form.getItemValue("fathUseYn") === "Y"){
					//안면인증대상 조회 필요
					PAGE.FORM1.setItemValue("isFathTrgFlag", "0");
					mvno.ui.alert("안면인증대상여부를 확인하세요.");
				}

				break;

			case 'cloneBaseCard':
				var value = form.getItemValue(name);
				if (value == "1") {
					form.setItemValue("reqCardName",form.getItemValue("cstmrName"));
					form.setItemValue("reqCardRrn1",form.getItemValue("cstmrNativeRrn1"));
					form.setItemValue("reqCardRrn2",form.getItemValue("cstmrNativeRrn2"));

				}

				break;

			case 'cloneBaseBank':
				var value = form.getItemValue(name);
				if (value == "1") {
					form.setItemValue("reqAccountName",form.getItemValue("cstmrName"));
					form.setItemValue("reqAccountRrn1",form.getItemValue("cstmrNativeRrn1"));
					form.setItemValue("reqAccountRrn2",form.getItemValue("cstmrNativeRrn2"));
				}

				break;

			case 'prodType':

				var value = form.getItemValue(name);

				form.setItemValue("cloneBase", "");
				form.setItemValue("dlvryName", "");
				form.setItemValue("dlvryTelFn", "");
				form.setItemValue("dlvryTelMn", "");
				form.setItemValue("dlvryTelRn", "");
				form.setItemValue("dlvryMobileFn", "");
				form.setItemValue("dlvryMobileMn", "");
				form.setItemValue("dlvryMobileRn", "");
				form.setItemValue("dlvryPost", "");
				form.setItemValue("dlvryAddr", "");
				form.setItemValue("dlvryAddrDtl", "");
				form.setItemValue("tbCd", "");
				form.setItemValue("dlvryNo", "");
				form.setItemValue("dlvryMemo", "");

				enableItems = ["reqBuyType"];

				if(form.getItemValue("onOffType") == "1" || form.getItemValue("onOffType") == "6" ){
					hideItems.push("DLVRY");
				}

				if(form.getItemValue("reqBuyType") == "UU"){
					form.disableItem("reqPhoneSn");
					form.setItemValue("reqPhoneSn", "");
				}

				break;

			case 'cstmrForeignerNationSel' :

				var nation = form.getItemValue("cstmrForeignerNationSel");

				form.setItemValue("cstmrForeignerNation", nation);
				form.setItemValue("cstmrForeignerNationTxt", nation);

				break;
				/*유심종류 eSim 추가 */
			case "usimKindsCd":
				var usimKindsCd = form.getItemValue("usimKindsCd");
				var chgPrdt = "N";

				if(esimYn == "") {
					if(usimKindsCd == "09") esimYn = "Y";
					else esimYn = "N";
				}

				if(usimKindsCd == "09"){
					showItems = ["eid", "imei1" , "imei2" , "intmMdlId" , "intmSrlNo" , "eReqModelName", "prntsCtnFn", "prntsCtnMn", "prntsCtnRn", "billAcntNo", "prntsContractNo"];
					if(esimYn == "N") {
						chgPrdt = "Y";
						esimYn = "Y";
					}
				}else{
					hideItems = ["eid", "imei1", "imei2","intmMdlId" , "intmSrlNo" , "eReqModelName"];
					form.setItemValue("eid", "");
					form.setItemValue("imei1", "");
					form.setItemValue("imei2", "");
					form.setItemValue("intmMdlId", "");
					form.setItemValue("intmSrlNo", "");
					form.setItemValue("eReqModelName", "");

					if(esimYn == "Y") {
						chgPrdt = "Y";
						esimYn = "N";
					}

					// 데이터쉐어링 요금제가 아닐경우 필드 설정
					if(sharingYn != "Y"){
						hideItems = ["prntsCtnFn", "prntsCtnMn", "prntsCtnRn", "billAcntNo", "prntsContractNo"];
						form.setItemValue("prntsCtnFn", "");
						form.setItemValue("prntsCtnMn", "");
						form.setItemValue("prntsCtnRn", "");
						form.setItemValue("billAcntNo", "");
						form.setItemValue("prntsContractNo", "");
					}
				}

				if(chgPrdt == "Y") {
					//esim 단말모델 가져오기
					form1Polcy(form, "PRDT", "prdtId");
					PAGE.FORM1.enableItem("prdtId");
				}

				break;

			case "pAgentCode" :

				// 대리점 변경에 따른 처리 (제휴사)
				enableItems.push("clausePartnerOfferFlag");
				form.setItemValue("clausePartnerOfferFlag", "");
				break;

			case "clauseJehuFlag" :

				if(form.isItemChecked("clauseJehuFlag")){  // 체크

					if(form.getItemValue("socCode") == ""){
						form.setItemValue("jehuProdType", "");
						return;
					}

					// 제휴처 조회
					var jehuProdType = getJehuProdType(form.getItemValue("socCode"));
					if(!jehuProdType){
						form.setItemValue("jehuProdType", "");
					}else{
						form.setItemValue("jehuProdType", jehuProdType);
					}

				}else{  // 체크해제
					form.setItemValue("jehuProdType", "");
				}

				break;

			case "combineSoloType" :
				var combineSoloType = form.getItemValue(name);

				form.setItemValue("combineSoloFlag", combineSoloType === "Y" ? "1" : "0");
				syncCombineSoloAddition(combineSoloType);

				break;

		}/* End switch */

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
	}/* End form1Change() */

	function form1Polcy(form, gubun, name){
		if(gubun == "PRDT") {
			// 단말모델 콤보박스 적용
			if(form.getItemValue("prodType") == "02") gubun = "RENTAL";

			//유심종류가 esim의 경우 제품구분코드가 esim 이 모델만 조회
			var prdtIndCd = null;
			//if(form.getItemValue("usimKindsCd") == "09") prdtIndCd = "10";

			mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpSimPrdtInfo.json",
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
						,serviceType:form.getItemValue("serviceType")
						,reqInDay:form.getItemValue("reqInDayStr")
					},
					form,
					name);
		}
	}

	function cstmrInit(){
		// 외국인 정보
		// 국적
		PAGE.FORM1.setItemValue("cstmrForeignerNationSel", "");
		PAGE.FORM1.setItemValue("cstmrForeignerNationTxt", "");
		PAGE.FORM1.setItemValue("cstmrForeignerNation", "");
		// 여권번호
		PAGE.FORM1.setItemValue("cstmrForeignerPn", "");
		// 체류기간
		PAGE.FORM1.setItemValue("cstmrForeignerSdate", "");
		PAGE.FORM1.setItemValue("cstmrForeignerEdate", "");
		// 사회보장번호
		PAGE.FORM1.setItemValue("cstmrForeignerDod", "");
		// 생년월일
		PAGE.FORM1.setItemValue("cstmrForeignerBirth", "");
		// 외국인등록번호
		PAGE.FORM1.setItemValue("cstmrForeignerRrn1", "");
		PAGE.FORM1.setItemValue("cstmrForeignerRrn2", "");

		// 2020.12.14 VISA CODE
		PAGE.FORM1.setItemValue("visaCd", "");

		// 개통이력 조회
		PAGE.FORM1.setItemValue("checkCstmr", "N");
		PAGE.FORM1.showItem("btnCheckCstmr");
	}

	function operTypeInit()
	{
		// 신규가입
		// 가입희망번호
		PAGE.FORM1.setItemValue("reqWantNumber", "");
		PAGE.FORM1.setItemValue("reqWantNumber2", "");
		PAGE.FORM1.setItemValue("reqWantNumber3", "");
		PAGE.FORM1.setItemValue("reqGuideFlag", "N");
		PAGE.FORM1.setItemValue("reqGuideFn", "");
		PAGE.FORM1.setItemValue("reqGuideMn", "");
		PAGE.FORM1.setItemValue("reqGuideRn", "");

		// 번호이동
		// 이동번호
		if(PAGE.FORM1.getItemValue("operType") == "MNP3"){
			PAGE.FORM1.setItemValue("moveMobileFn", "010");
		}else{
			PAGE.FORM1.setItemValue("moveMobileFn", "");
		}

		PAGE.FORM1.setItemValue("moveMobileMn", "");
		PAGE.FORM1.setItemValue("moveMobileRn", "");
		// 이동전통신사
		PAGE.FORM1.setItemValue("moveCompany", "");
		// 번호이동전 통신요금
		PAGE.FORM1.setItemValue("moveThismonthPayType", "");
		// 휴대폰 할부금
		PAGE.FORM1.setItemValue("moveAllotmentStat", "");
		// 미환급액 상계
		PAGE.FORM1.setItemValue("moveRefundAgreeFlag", "N");
		// 인증유형
		PAGE.FORM1.setItemValue("moveAuthType", "");
		PAGE.FORM1.setItemValue("moveAuthNumber", "");
		// 위약금
		PAGE.FORM1.setItemValue("movePenalty", "");
	}

	function reqPayTypeInit()
	{
		// 카드
		// 카드정보
		PAGE.FORM1.setItemValue("reqCardCompany", "");
		PAGE.FORM1.setItemValue("reqCardNo1", "");
		PAGE.FORM1.setItemValue("reqCardNo2", "");
		PAGE.FORM1.setItemValue("reqCardNo3", "");
		PAGE.FORM1.setItemValue("reqCardNo4", "");
		// 월
		PAGE.FORM1.setItemValue("reqCardMm", "");
		// 년
		PAGE.FORM1.setItemValue("reqCardYy", "");
		// 명의자정보
		PAGE.FORM1.setItemValue("reqCardName", "");
		// 타인납부
		PAGE.FORM1.setItemValue("reqPayOtherFlag", "N");
		// 명의자 주민번호
		PAGE.FORM1.setItemValue("reqCardRrn1", "");
		PAGE.FORM1.setItemValue("reqCardRrn2", "");

		// 은행
		// 은행정보
		PAGE.FORM1.setItemValue("reqBank", "");
		PAGE.FORM1.setItemValue("reqAccountNumber", "");
		// 예금주
		PAGE.FORM1.setItemValue("reqAccountName", "");
		// 타인납부
		PAGE.FORM1.setItemValue("othersPaymentAg", "N");
		// 명의자주민번호
		PAGE.FORM1.setItemValue("reqAccountRrn1", "");
		PAGE.FORM1.setItemValue("reqAccountRrn2", "");

		//고객상동
		PAGE.FORM1.setItemValue("cloneBaseCard", "");
		PAGE.FORM1.setItemValue("cloneBaseBank", "");
	}

	function checkSnAfter(form, data, plcyOrgnYN){
		var plcOrgnId = form.getItemValue('cntpntShopId');	// 판매정책 조직ID

		if(!mvno.cmn.isEmpty(plcOrgnId) && (plcOrgnId != data.orgnId)){
			mvno.ui.alert('판매정책의 조직정보와<br>단말기의 조직정보가 일치 하지 않습니다.<br>단말기일련번호를 다시 등록하세요.');
			form.setItemValue('reqPhoneSn', "");
			return false;
		}

		form.setItemValue("mnfctId", data.mnfctId);
		form1Polcy(form, "PRDT", "prdtId");					// 단말모델 콤보박스 적용
		form.setItemValue("prdtId", data.prdtId);

		// 단말모델 선택 시, 색상 리스트 가져오기
		if(form.getItemValue("reqBuyType") == "MM" && form.getItemValue("prodType") != "02") {
			mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpCommon.json",
					{
						grpId:"COLOR"
						,prdtId:form.getItemValue("prdtId")
						,mnfctId:form.getItemValue("mnfctId")
					},
					form,
					"reqModelColor");
		}

		form.setItemValue("prdtCode", data.prdtCode);
		form.setItemValue("reqModelColor", data.prdtColrCd);
		form.setItemValue("prdtSrlNoOrgnId", data.orgnId);
		form.setItemValue('checkSn', 'Y');

		form.disableItem("mnfctId");
		form.disableItem("prdtId");

		if(!plcyOrgnYN){
			salePlcyCdListInqr(form, "salePlcyCdList");		//정책 조회 combo-box
		}

		return true;
	}

	function salePlcyCdListInqr(form, itemName){

		salePlcyCdListInit();

		var socCode = form.getItemValue("socCode");
		var agrmTrm = form.getItemValue("agrmTrm");
		var instNom = form.getItemValue("instNom");
		var prdtId = form.getItemValue("prdtId");

		if( mvno.cmn.isEmpty(socCode) || mvno.cmn.isEmpty(agrmTrm) ||
				mvno.cmn.isEmpty(instNom) || mvno.cmn.isEmpty(prdtId) || form.getItemValue('checkSn') != "Y" ){
			return;
		}

		form.hideItem("salePlcyNm");		// 정책명
		form.showItem("salePlcyCdList");	// 정책선택 combox

		var params = {
			'orgnId':form.getItemValue('prdtSrlNoOrgnId')
			,'reqInDay':form.getItemValue('reqInDayStr')
			,'serviceType':form.getItemValue("serviceType")
			,'reqBuyType':form.getItemValue("reqBuyType")
			,'sprtTp':form.getItemValue("sprtTp")
			,'prdtId':prdtId
			,'operType':form.getItemValue("operType")
			,'socCode':socCode
			,'agrmTrm':agrmTrm
			,'instNom':instNom
		};

		mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRcpPlc.json", params, function(resultData) {
			var totalCount = Number(resultData.result.data.rows.length);
			var saleData = "";

			if(totalCount > 0){
				form.disableItem("pAgentName");				// 대리점
				form.disableItem("btnOrgFind");				// 대리점 찾기

				salePlcyData = resultData;

				saleData = [{text:"- 선택 -", value:""}];

				for(var idx = 0; idx < totalCount; idx++){
					var salePlcyNm = resultData.result.data.rows[idx].salePlcyNm;
					var orgmNm = resultData.result.data.rows[idx].orgnNm;
					var data = {text:"["+orgmNm+"] "+salePlcyNm, value:idx};

					saleData.push(data);
				};


			}else{
				saleData = [{text:"- 없음 -", value:""}];
			}

			form.reloadOptions(itemName, saleData);
		});
	}

	function salePlcyCdListInit(){
		salePlcyData = "";

		var sOpt = PAGE.FORM1.getOptions("salePlcyCdList");

		var sCount = sOpt.length;

		for(var idx=0; idx < sCount; idx++){
			sOpt.remove(0);
		}

		PAGE.FORM1.showItem("salePlcyNm");
		PAGE.FORM1.hideItem("salePlcyCdList");

		var cntpntShopId = PAGE.FORM1.getItemValue('cntpntShopId');

		if(mvno.cmn.isEmpty(cntpntShopId) && typeCd == "10"){
			PAGE.FORM1.enableItem("pAgentName");
			PAGE.FORM1.enableItem("btnOrgFind");
		}
	}

	function appFormView(scanId){
		var DBMSType = "C";

		//console.log("1. RES_NO=" + PAGE.FORM1.getItemValue("resNo"));
		//console.log("1. PARENT_SCAN_ID=" + scanId);

		var data = null;
		// 2016-06-24
		// 개통대기 이전 상태는 고객명 수정가능
		var modifyFlag = (PAGE.FORM1.getItemValue("requestStateCode") < "20") ? "Y" : "N";

		data = "AGENT_VERSION="+agentVersion+
				"&SERVER_URL="+serverUrl+
				"&VIEW_TYPE=MCPVIEW"+
				"&USE_DEL="+delAuthYn+
				"&USE_STAT="+modifyFlag+
				"&USE_BM="+blackMakingYn+
				"&USE_DEL_BM="+blackMakingFlag+
				"&RGST_PRSN_ID="+userId+
				"&RGST_PRSN_NM="+userName+
				"&ORG_TYPE="+orgType+
				"&ORG_ID="+orgId+
				"&ORG_NM="+orgNm+
				"&RES_NO="+PAGE.FORM1.getItemValue("resNo")+
				"&PARENT_SCAN_ID="+scanId+
				"&DBMSType="+DBMSType
		;

		callViewer(data);

	}

	function fncEnCode(param){
		var encode = '';

		for(i=0; i<param.length; i++){
			var len  = '' + param.charCodeAt(i);
			var token = '' + len.length;

			encode  += token + param.charCodeAt(i);
		}

		return encode;
	}

	function lockscroll(lock){
		var eventName = 'scroll.lockscroll';
		var $el = $(this);
		var pos = { x: $el.scrollLeft(), y: 0 };

		if(lock) {
			$('html, body').animate({scrollTop : 0}, 400);

			$el.off(eventName);
			$el.on(eventName, function() {
				$el.scrollLeft(pos.x);
				$el.scrollTop(pos.y);
			});
		} else {
			$el.off(eventName);
		}
	}

	function checkDate(data){
		var regExp = /[1-2][0-9]{3}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/;

		if(!regExp.test(data)){

			return false;
		}

		return true;
	}

	function fn_loading(){
		$("body").append('<div id ="LOADING" style="z-index:999999;position:fixed;top:50%;left:40%"><img src=../../images/loading.gif ></div>');
	}

	function form1Init() {
		mvno.ui.createForm("FORM1");

		// 은행코드 셋팅
		mvno.cmn.ajax4lov( ROOT + "/cmn/payinfo/getReqBankComboList.json", "", PAGE.FORM1, "reqBank" );

		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0074",orderBy:"etc1"}, PAGE.FORM1, "operType"); // 신청유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0075",etc1:"1",orderBy:"etc6"}, PAGE.FORM1, "cstmrType"); // 고객구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9004"}, PAGE.FORM1, "reqBuyType"); // 구매유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0005"}, PAGE.FORM1, "pstate"); // 신청서상태
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM1, "requestStateCode"); // 진행상태
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0063"}, PAGE.FORM1, "prodType"); // 상품구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2001",orderBy:"etc6"}, PAGE.FORM1, "onlineAuthType"); // 본인인증방식
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM1, "onOffType"); // 모집경로
		if (knoteYn == 'Y') {
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2002",orderBy:"etc6"}, PAGE.FORM1, "faxyn"); // 서식지
		} else {
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2002",orderBy:"etc6",etc1:knoteYn}, PAGE.FORM1, "faxyn"); // k-note 서식지 미노출
		}
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2003"}, PAGE.FORM1, "faxnum1"); // 팩스번호
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2005"}, PAGE.FORM1, "instNom"); // 할부기간
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0058"}, PAGE.FORM1, "sprtTp"); // 할인유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0056"}, PAGE.FORM1, "usimPayMthdCd"); // USIM구매
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2006"}, PAGE.FORM1, "selfCertType"); // 본인인증방식
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM1, "cstmrTelFn"); // 고객연락처
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM1, "fathMobileFn"); // 안면인증연락처
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM1, "cstmrMobileFn"); // 고객휴대전화
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2008"}, PAGE.FORM1, "cstmrBillSendCode"); // 명세서수신유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2009",orderBy:"etc6"}, PAGE.FORM1, "cstmrMail3"); //  
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM1, "dlvryTelFn"); // 배송지전화번호
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM1, "dlvryMobileFn"); // 배송지휴대전화
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0025"}, PAGE.FORM1, "tbCd"); // 택배사
		mvno.cmn.ajax4lov( ROOT + "/cmn/payinfo/getReqCardComboList.json", {expnsnVal:"Y"}, PAGE.FORM1, "reqCardCompany"); // 카드정보
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0048"}, PAGE.FORM1, "joinPayMthdCd"); // 가입비
		//		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM1, "moveMobileFn"); // 이동번호
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"NSC"}, PAGE.FORM1, "moveCompany"); // 이동전통신사
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2011"}, PAGE.FORM1, "moveAuthType"); // 인증유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2012",orderBy:"etc6"}, PAGE.FORM1, "moveThismonthPayType"); // 번호이동전 사용요금
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2013",orderBy:"etc6"}, PAGE.FORM1, "moveAllotmentStat"); // 휴대폰 할부금
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9006",orderBy:"etc6"}, PAGE.FORM1, "appCd"); // 유해정보차단APP
		//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2035"}, PAGE.FORM1, "usimKindsCd"); // 유심종류
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2035" , etc1:"1"}, PAGE.FORM1, "usimKindsCd"); // 유심종류
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2038"}, PAGE.FORM1, "cstmrForeignerNationSel"); // 국적
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM1, "reqGuideFn"); //
		// SRM18072675707, 단체보험
		mvno.cmn.ajax4lov( ROOT + "/ptnr/grpinsrmgmt/getGrpInsrCombo.json", "", PAGE.FORM1, "insrCd");

		// 20201214 VISA CODE 추가
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2051"}, PAGE.FORM1, "visaCd"); // VISA CODE
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM1, "phoneStateCd"); // 자급제
		// 20230216 모회선 추가
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM1, "prntsCtnFn"); // 모회선
		// 20230718 자급제 보상 부가서비스 추가
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0258", orderBy:"etc5"}, PAGE.FORM1, "rwdProdCd");	// 보상서비스 상품
		// 20250304 요금제 제휴처
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"JehuProdType"}, PAGE.FORM1, "jehuProdType");

		// 신청서 수정 내역을 확인하기 위한 데이터 추출.
		//chkMcpRequest();

		menuId = "${param.trgtMenuId}";

		if(menuId == 'MSP1000015' || menuId == 'MSP1010089' || menuId == "MSP2002201" || menuId == "MSP1000018") {	//신청관리 New or A'Cen 자동개통 통계 or 신청관리(New)
			if("${param.requestKey}" != ''){
				PAGE.FORM1.setItemValue("requestKey", "${param.requestKey}");

				form1ReadInit();

			}else{
				form1RegInit();
			}
		}else{
			form1RegInit();
		}

		if('${authRealShopYn}' == "N") {
			PAGE.FORM1.hideItem("realShopNm");
		}

		var loading = document.all.LOADING;
		loading.style.display = "none";
	}

	//스캔 호출
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
			dataType : 'jsonp',
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

	/* 주소 setting */
	function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn) {
		if(jusoGubun == "CSTMR"){
			PAGE.FORM1.setItemValue("cstmrPost",zipNo);
			PAGE.FORM1.setItemValue("cstmrAddr",roadAddrPart1);
			PAGE.FORM1.setItemValue("cstmrAddrDtl",addrDetail);
		}else if(jusoGubun == "DLVRY"){
			PAGE.FORM1.setItemValue("dlvryPost",zipNo);
			PAGE.FORM1.setItemValue("dlvryAddr",roadAddrPart1);
			PAGE.FORM1.setItemValue("dlvryAddrDtl",addrDetail);
		}

		jusoGubun = "";
	}
	function getChrgPrmtListCombo(form, useYn){

		form.setItemValue("disPrmtId", "");
		form.setItemValue("disPrmtNm", "");
		form.setItemValue("totalDiscount", "");


		if(mvno.cmn.isEmpty(form.getItemValue("reqInDay")) || mvno.cmn.isEmpty(form.getItemValue("operType"))
				|| mvno.cmn.isEmpty(form.getItemValue("agrmTrm")) || mvno.cmn.isEmpty(form.getItemValue("pAgentCode")) || mvno.cmn.isEmpty(form.getItemValue("socCode")) || mvno.cmn.isEmpty(form.getItemValue("onOffType"))){

			return;
		}
		var data = {
			operType : form.getItemValue("operType")
			,agrmTrm  : form.getItemValue("agrmTrm")
			,reqBuyType : form.getItemValue("reqBuyType")
			,cntpntShopId : form.getItemValue("pAgentCode")
			,socCode : form.getItemValue("socCode")
			,onOffType : form.getItemValue("onOffType")
		}

		mvno.cmn.ajax(ROOT + "/org/prmtmgmt/getChrgPrmtListCombo.json", data, function(result) {

			if(result.result.code == "OK"){
				if(!mvno.cmn.isEmpty(result.result.prmtMap)){
					form.setItemValue("disPrmtId",result.result.prmtMap.prmtId);
					form.setItemValue("disPrmtNm",result.result.prmtMap.prmtNm);
					form.setItemValue("totalDiscount",result.result.prmtMap.disCnt);
				}else{
					form.setItemValue("disPrmtId", "");
					form.setItemValue("disPrmtNm", "");
					form.setItemValue("totalDiscount", "");
				}
			}

		}, {
			async: false
		});


	};

	function getKtPlcyListCombo(form, useYn){

		var plcyType;

		if(form.getItemValue("prodType") == '02'){
			plcyType = 'UU';								//상품구분이 렌탈인 경우 구매유형은 유심으로 설정
		}else{
			if(form.getItemValue("reqBuyType") == "UU" && form.getItemValue("usimKindsCd") == "09"){ //eSIM의 경우 특별판매코드 따로 가져오기
				plcyType = "03"
			}else{
				plcyType = form.getItemValue("reqBuyType");
			}
		}

		var data = {
			pppo : form.getItemValue("serviceType")		//선/후불구분
			,plcyType : plcyType						//정책구분
			,dcType : form.getItemValue("sprtTp")		//할인유형
			,operType : form.getItemValue("operType")	//개통유형
			,instCnt : form.getItemValue("instNom")		//할부개월수
			,enggCnt :form.getItemValue("agrmTrm")		//약정기간
			,useYn : useYn
		}

		mvno.cmn.ajax(ROOT + "/sale/ktPlcyMgmt/getKtPlcyListCombo.json", data, function(result) {
			var lov;

			if(result.result.data.rows.length > 0){
				lov = result.result.data.rows;
			}else{
				lov = [{text:"", value:""}];
			}

			form.reloadOptions("spcCode", lov);

		}, {
			async: false
		});
	}

	function getNoAgrmTrtm(form){

		if(form.getItemValue("reqBuyType") == 'MM'){
			if(form.getItemValue("agrmTrm") < 1 && form.getItemValue("instNom") > 0){

				mvno.ui.alert("단말할부, 무약정인 경우 할인이 적용되지 않습니다.");

				form.setItemValue("sprtTp", "");
			}
		}

	}

	function validationCheck(form){
		// 신청서상태 정상이 아닌 경우validation Check 제외
		if(form.getItemValue("operType") != "NAC3" && form.getItemValue("operType") != "MNP3"){
			mvno.ui.alert("[신청유형] 신규/번호이동만 신청가능합니다.");
			return false;
		}

		if(mvno.cmn.isEmpty(form.getItemValue("pAgentCode")) && (!mvno.cmn.isEmpty(form.getItemValue("salePlcyCd")) && mvno.cmn.isEmpty(form.getItemValue("cntpntShopId")))){
			mvno.ui.alert("[대리점] 필수 입력 항목입니다");
			return false;
		}

		//서식지
		if(form.getItemValue("faxyn") == "Y") {
			mvno.ui.alert("[서식지] 팩스는 선택할 수 없습니다.");
			return false;
		}

		//번호연결서비스
		if(form.getItemValue("reqGuideFlag") == "Y" && (mvno.cmn.isEmpty(form.getItemValue("reqGuideFn")) || mvno.cmn.isEmpty(form.getItemValue("reqGuideMn")) || mvno.cmn.isEmpty(form.getItemValue("reqGuideRn")))) {
			mvno.ui.alert("[번호연결서비스] 필수 입력 항목입니다.");
			return false;
		}

		//고객구분에 따른 체크 로직 : "NA:내국인,NM:내국인(미성년자),FN:외국인,JP:법인,PP:개인사업자"
		if(form.getItemValue("cstmrType") == 'NA' && (mvno.cmn.isEmpty(form.getItemValue("cstmrNativeRrn1")) || mvno.cmn.isEmpty(form.getItemValue("cstmrNativeRrn2")))){
			mvno.ui.alert("[주민번호] 필수 입력 항목입니다.");
			return false;
		}

		switch(form.getItemValue("cstmrType")){
			case "NA":
				if(mvno.cmn.isEmpty(form.getItemValue("cstmrNativeRrn1")) || mvno.cmn.isEmpty(form.getItemValue("cstmrNativeRrn2"))){
					mvno.ui.alert("[주민번호] 필수 입력 항목입니다.");
					return false;
				}

				break;
			case "FN":
				if(mvno.cmn.isEmpty(form.getItemValue("cstmrForeignerRrn1")) || mvno.cmn.isEmpty(form.getItemValue("cstmrForeignerRrn2"))){
					mvno.ui.alert("[외국인등록번호] 필수 입력 항목입니다.");
					return false;
				}

				if(mvno.cmn.isEmpty(form.getItemValue("cstmrForeignerNationSel"))){
					mvno.ui.alert("[국적] 필수 입력 항목입니다.");
					return false;
				}
				/* 기존의 외국인정보는 값이 없으므로 필수항목 할수 없음.20201229 송정섭
				if(mvno.cmn.isEmpty(form.getItemValue("visaCd"))){
					mvno.ui.alert("[VISA 코드] 필수 입력 항목입니다.");
					return false;
				}
				*/

				break;
		}

		// 2015-04-20 본인인증관련
		if(form.getItemValue("pstate") == "00" && form.getItemValue("requestStateCode") == "00"){
			var labelNm = form.getItemLabel("selfIssuExprDt");

			if(form.isItemChecked("selfInqryAgrmYn") == false){
				mvno.ui.alert("[본인조회동의] 부정가입방지를 위한 본인조회동의를 선택하세요.");
				return false;
			}

			if(form.getItemValue("cstmrType") != "NM" && mvno.cmn.isEmpty(form.getItemValue("selfCertType"))){
				mvno.ui.alert("[인증방식] 본인인증방식을 선택하세요.");
				return false;
			}

			switch(form.getItemValue("cstmrType")){
				case "NA":
					if(form.getItemValue("selfCertType") == "05"){
						mvno.ui.alert("[인증방식] 내국인은 여권을 선택할 수 없습니다.");
						return false;
					}

					if(form.getItemValue("selfCertType") == "06"){
						mvno.ui.alert("[인증방식] 내국인은 외국인등록증을 선택할 수 없습니다.");
						return false;
					}

					if(form.getItemValue("selfCertType") == "07"){
						mvno.ui.alert("[인증방식] 내국인은 국내거소신고증을 선택할 수 없습니다.");
						return false;
					}

					// 개통이력 확인
					if(form.getItemValue("reqBuyType") == "MM" && form.getItemValue("checkCstmr") != "Y"){
						mvno.ui.alert("[개통이력조회] 내국인인 경우 개통이력을 조회하세요.");
						return false;
					}

					if(mvno.cmn.isEmpty(form.getItemValue("selfIssuExprDt"))){
						if(form.getItemValue("selfCertType") == "05"){
							mvno.ui.alert("[" + labelNm + "] 만료일자를 선택하세요.");
							return false;
						}else{
							mvno.ui.alert("[" + labelNm + "] 발급일자를 선택하세요.");
							return false;
						}
					}else{
						if(!checkDate(form.getItemValue("selfIssuExprDt"))){
							mvno.ui.alert("[" + labelNm + "] 날짜형식이 유효하지 않습니다.");
							return false;
						}
						if(form.getItemValue("selfCertType") == "02" && mvno.cmn.isEmpty(form.getItemValue("selfIssuNum"))){
							mvno.ui.alert("[발급번호] 운전면허증 발급번호를 입력하세요.");
							return false;
						}
						if(form.getItemValue("selfCertType") == "04" && mvno.cmn.isEmpty("selfIssuNum")){
							mvno.ui.alert("[발급번호] 국가유공자증 발급번호를 입력하세요.");
							return false;
						}
					}
					break;
				case "FN":
					if(form.getItemValue("selfCertType") != "06"){
						mvno.ui.alert("[인증방식] 외국인은 외국인등록증을 선택하세요.");
						return false;
					}
					if(mvno.cmn.isEmpty(form.getItemValue("selfIssuExprDt"))){
						mvno.ui.alert("[" + labelNm + "] 만료일자를 선택하세요.");
						return false;
					}else{
						if(!checkDate(form.getItemValue("selfIssuExprDt"))){
							mvno.ui.alert("[" + labelNm + "] 날짜형식이 유효하지 않습니다.");
							return false;
						}
					}
					break;
			}
		}

		//연락처 필수 체크
		if(mvno.cmn.isEmpty(form.getItemValue("cstmrTelFn")) || mvno.cmn.isEmpty(form.getItemValue("cstmrTelMn")) || mvno.cmn.isEmpty(form.getItemValue("cstmrTelRn"))){
			//console.log("operType=" + data.operType);
			mvno.ui.alert("[연락처] 필수 입력 항목입니다");
			return false;
		}

		if(form.getItemValue("cstmrTelMn").length < 3 || form.getItemValue("cstmrTelRn").length < 4){
			mvno.ui.alert("[연락처] 형식에 맞지 않습니다.");
			return false;
		}

		// 연락처 체크
		if(form.getItemValue("cstmrTelMn") == "000" || form.getItemValue("cstmrTelMn") == "0000" || form.getItemValue("cstmrTelRn") == "0000"){
			mvno.ui.alert("[연락처] 등록불가로 관리되는 전화번호입니다.다른연락처로 등록바랍니다.");
			return false;
		}

		// 통합청구 + 청구계정ID 존재 시 주소 검증 SKIP
		if (form.getItemValue("reqPayType") !== "0" || mvno.cmn.isEmpty(form.getItemValue("billAcntNo"))) {
			if(mvno.cmn.isEmpty(form.getItemValue("cstmrPost"))){
				mvno.ui.alert("[주소] 필수 입력 항목입니다");
				return false;
			}
		}

		/* 상세주소 필수값 제외
		if(data.cstmrAddrDtl == ''){
			this.pushError("cstmrAddrDtl","상세주소",["ICMN0001"]);
		} */

		// 명세서
		if(mvno.cmn.isEmpty(form.getItemValue("cstmrBillSendCode"))){
			mvno.ui.alert("[명세서수신유형] 필수 입력 항목입니다");
			return false;
		}

		// Maile 수신여부에 동의 or 명세서수신유형(메일) 인 경우
		// CB:이메일,LX:우편
		if(form.isItemChecked("cstmrMailReceiveFlag") || form.getItemValue("cstmrBillSendCode") == 'CB') {
			var cstmrMail1 = '';
			var cstmrMail2 = '';

			try {
				cstmrMail1 = form.getItemValue("cstmrMail1").replace(/\s/g,'');
				cstmrMail2 = form.getItemValue("cstmrMail2").replace(/\s/g,'');
			}
			catch(e) {
				cstmrMail1 ='pass';
			}

			// 메일 미 작성시 Error
			if(cstmrMail1 == '' || cstmrMail2 == '') {
				mvno.ui.alert("[메일] 필수 입력 항목입니다");
				return false;
			}
		}

		// 납부방법
		if(form.getItemValue("pstate") == "00" && mvno.cmn.isEmpty(form.getItemValue("reqPayType"))){
			mvno.ui.alert("[요금납부방법] 필수 선택 항목입니다.");
			return false;
		}

		switch(form.getItemValue("reqPayType")){
			case "C":
				if(mvno.cmn.isEmpty(form.getItemValue("reqCardCompany"))){
					mvno.ui.alert("[카드사] 필수 선택 항목입니다.");
					return false;
				}
				if(mvno.cmn.isEmpty(form.getItemValue("reqCardNo1")) || mvno.cmn.isEmpty(form.getItemValue("reqCardNo2")) || mvno.cmn.isEmpty(form.getItemValue("reqCardNo3")) || mvno.cmn.isEmpty(form.getItemValue("reqCardNo4"))){
					mvno.ui.alert("[카드번호] 필수 입력 항목입니다.");
					return false;
				}
				if(mvno.cmn.isEmpty(form.getItemValue("reqCardMm")) || mvno.cmn.isEmpty(form.getItemValue("reqCardYy"))){
					mvno.ui.alert("[유효기간] 필수 입력 항목입니다.");
					return false;
				}
				if(form.getItemValue("reqCardMm").length != "2"|| form.getItemValue("reqCardYy").length != "4"){
					mvno.ui.alert("[유효기간] 필수 입력 항목입니다.");
					return false;
				}
				if(mvno.cmn.isEmpty(form.getItemValue("reqCardName"))){
					mvno.ui.alert("[명의자] 필수 입력 항목입니다.");
					return false;
				}
				if(mvno.cmn.isEmpty(form.getItemValue("reqCardRrn1")) || mvno.cmn.isEmpty(form.getItemValue("reqCardRrn2"))){
					mvno.ui.alert("[명의자 주민번호] 필수 입력 항목입니다.");
					return false;
				}
				break;
			case "D":
				if(mvno.cmn.isEmpty(form.getItemValue("reqBank"))){
					mvno.ui.alert("[은행] 필수 선택 항목입니다.");
					return false;
				}
				if(mvno.cmn.isEmpty(form.getItemValue("reqAccountNumber"))){
					mvno.ui.alert("[계좌번호] 필수 입력 항목입니다.");
					return false;
				}
				if(mvno.cmn.isEmpty(form.getItemValue("reqAccountName"))){
					mvno.ui.alert("[예금주] 필수 입력 항목입니다.");
					return false;
				}
				if(mvno.cmn.isEmpty(form.getItemValue("reqAccountRrn1")) || mvno.cmn.isEmpty(form.getItemValue("reqAccountRrn2"))){
					mvno.ui.alert("[예금주 주민번호] 필수 입력 항목입니다.");
					return false;
				}
				break;

		}

		//업무구분에 따른 체크 : "MNP3:번호이동,NAC3:개통"
		switch(form.getItemValue("operType")) {
			case "NAC3":
				//가입희망번호
				if(mvno.cmn.isEmpty(form.getItemValue("reqWantNumber")) || form.getItemValue("reqWantNumber").length != 4){
					mvno.ui.alert("[가입희망번호] 필수 입력 항목입니다.");
					return false;
				}
				break;
			case "MNP3":
				// 번호이동시 20231229 moveAuthType, moveAuthNumber 삭제
				if(mvno.cmn.isEmpty(form.getItemValue("moveMobileFn")) || mvno.cmn.isEmpty(form.getItemValue("moveMobileMn")) || mvno.cmn.isEmpty(form.getItemValue("moveMobileRn"))){
					mvno.ui.alert("[이동번호] 필수 입력 항목입니다.");
					return false;
				}
				if(form.getItemValue("moveMobileMn").length < 3 || form.getItemValue("moveMobileRn").length < 4){
					mvno.ui.alert("[이동번호] 형식에 맞지 않습니다.");
					return false;
				}
				if(mvno.cmn.isEmpty(form.getItemValue("moveCompany"))){
					mvno.ui.alert("[변경전통신사] 필수 선택 항목입니다.");
					return false;
				}
				if(mvno.cmn.isEmpty(form.getItemValue("moveThismonthPayType"))){
					mvno.ui.alert("[번호이동전 사용요금] 필수 선택 항목입니다.");
					return false;
				}
				if(mvno.cmn.isEmpty(form.getItemValue("moveAllotmentStat"))){
					mvno.ui.alert("[휴대폰 할부금] 필수 선택 항목입니다.");
					return false;
				}
				if(mvno.cmn.isEmpty(form.getItemValue("moveRefundAgreeFlag"))){
					mvno.ui.alert("[미환급액상계] 필수 선택 항목입니다.");
					return false;
				}
				break;

		}

		// 생활안심 요금제 동의 체크
		if((form.getItemValue("socCode") == "KISSAVE01" || form.getItemValue("socCode") == "KISSAVE02") && form.isItemChecked("clauseFinanceFlag") == false) {
			mvno.ui.alert("[개인(신용)정보 처리 및 보험가입 동의] 생활안심요금제 가입시 필수 선택항목입니다.");
			return false;
		}

		// SRM18072675707, 단체보험체크
		if(grpInsrYn == "Y"){
			if(mvno.cmn.isEmpty(form.getItemValue("insrCd")) && form.isItemChecked("clauseInsuranceFlag") == true){
				mvno.ui.alert("단체보험가입동의시 단체보험을 선택하세요.");
				return false;
			}

			if(!mvno.cmn.isEmpty(form.getItemValue("insrCd")) && form.isItemChecked("clauseInsuranceFlag") == false){
				mvno.ui.alert("단체보험 선택시 단체보험가입동의를 선택하세요.");
				return false;
			}

			// 국민키즈안심보험을 제외한 단체보험은 만 15세 이상 가입가능
			if(form.getItemValue("insrCd") != "" && form.isItemChecked("clauseInsuranceFlag") == true){
				if(form.getItemValue("insrCd") != "C0" && fnGetAge(form) < 15){
					mvno.ui.alert("만 15세 이상 가입 가능합니다.");
					return;
				}
			}
		}

		// 단말보험
		if(!mvno.cmn.isEmpty(form.getItemValue("insrProdCd")) && form.isItemChecked("clauseInsrProdFlag") == false) {
			mvno.ui.alert("단말보험가입동의를 체크하세요.");
			return;
		}

		// 제휴처 및 제휴사 약관 validation
		if(!termsValidationCheck(form)) return false;

		return true;
	}

	function fncEnCode(param){
		var encode = '';

		for(i=0; i<param.length; i++){
			var len  = '' + param.charCodeAt(i);
			var token = '' + len.length;

			encode  += token + param.charCodeAt(i);
		}

		return encode;
	}

	function fnGetAge(form){
		var date = new Date();
		var year = date.getFullYear();
		var month = (date.getMonth() + 1);
		var day = date.getDate();

		if (month < 10) month = "0" + month;
		if (day < 10) day = "0" + day;
		var monthDay = month + day;

		var yy = form.getItemValue("cstmrNativeRrn1").substr(0, 2);
		var mm = form.getItemValue("cstmrNativeRrn1").substr(2, 2);
		var dd = form.getItemValue("cstmrNativeRrn1").substr(4, 2);
		var flag = form.getItemValue("cstmrNativeRrn2").substr(0, 1);

		/* console.log("yy=" + yy);
		console.log("mm=" + mm);
		console.log("dd=" + dd);
		console.log("flag=" + flag); */

		if(flag == "3" || flag == "4" || flag == "7" || flag == "8"){
			yy = "20" + yy;
		}else{
			yy = "19" + yy;
		}

		var age = monthDay < mm + dd ? year - yy - 1 : year - yy;
		return Number(age);
	}

	function getIntmInsrComboList(form, prdtId, reqBuyType){

		if(form.getItemValue("serviceType") == "PP"){

			form.reloadOptions("insrProdCd", [{text:"- 없음 -", value:""}]);

		} else {

			var sData = {usgYn:"Y", rprsPrdtId:prdtId, reqBuyType:reqBuyType};

			mvno.cmn.ajax4lov(ROOT + "/insr/insrMgmt/getInsrCombo.json", sData, form, "insrProdCd"); // 단말보험상품

		}
	}

	// 사은품 프로모션 영역 setting하는 함수
	function setPrmtResultItem() {
		// 2024-03-26 대리점일때 alert 방지
		if (typeCd != "10") {
			return;
		}

		var strRequestKey = PAGE.FORM1.getItemValue("requestKey");
		if (strRequestKey == null || strRequestKey == '') {
			return;
		}

		mvno.cmn.ajax2(ROOT + "/gift/custgmt/custGiftPrmtPrdtResultList.json", {requestKey: strRequestKey, menuId: "${param.trgtMenuId}"}, function(resultData) {
			if (resultData == null || resultData.result.data.rows == null || resultData.result.data.rows.length == 0) {
				return;
			}

			var rootItem = {type: "fieldset", label: "사은품 프로모션", name:"PRMT_RESULT", inputWidth:900, disabled:true, list:[]};

			for (var i = 0; i < resultData.result.data.rows.length; i++) {
				var prmtInfo = resultData.result.data.rows[i];
				var subText = prmtInfo.prmtType == 'C' ? ' [선택사은품/총 선택 개수 제한: ' + prmtInfo.choiceLimit + '개]' : ' [기본사은품]';

				// FORM format
				var prmtItem  = {type:"fieldset", label: prmtInfo.prmtNm + subText};
				var prdtList = [{type:"settings", position: "label-right"}];

				prdtList.push({type:"block", list: []});
				for (var j = 0; j < prmtInfo.prdtList.length; j++) {
					var prdtInfo = prmtInfo.prdtList[j];
					var qntText = prdtInfo.rowCheck ? ' [수량: ' + prdtInfo.quantity + '개]' : '';
					if (j == 0) {
						prdtList[1].list.push({type:"checkbox", className: "prdt_block", label: prdtInfo.prdtNm + qntText, checked: prdtInfo.rowCheck});
					} else {
						prdtList[1].list.push({type:"newcolumn"});
						prdtList[1].list.push({type:"checkbox", className: "prdt_block", label:prdtInfo.prdtNm + qntText, checked: prdtInfo.rowCheck});
					}
				}
				prmtItem.list = prdtList;
				rootItem.list.push(prmtItem);
			}

			/* 최종 prmtItem 형태
			var prmtItem  = {type:"fieldset", label: "프로모션명"
							, list:[
									{type:"settings", position : "label-right", labelWidth : 184}
									,{type:"block", className: "prmt_block", list:[
										{type:"checkbox", label:"사은품1번", checked:false}
										, {type:"newcolumn"}
										, {type:"checkbox", label:"사은품2번", checked:true}
										, {type:"newcolumn"}
										, {type:"checkbox", label:"사은품3번", checked:false}
										, {type:"newcolumn"}
										, {type:"checkbox", label:"사은품4번", checked:false}
									]}
								]
							};
			*/
			PAGE.FORM1._removeItem('PRMT_RESULT');
			// 화면 노출
			PAGE.FORM1.addItem("FORM1", rootItem);
		});
	}

	/** 요금제 제휴처 조회 */
	function getJehuProdType(socCode){

		var jehuProdType = null;

		if(mvno.cmn.isEmpty(socCode)){
			return null;
		}

		mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getJehuProdType.json", {socCode}, function(resultData) {
			jehuProdType = resultData.result.jehuProdType
		},{async:false});

		return jehuProdType;
	}

	/** 제휴처 및 제휴사 약관 validation check */
	function termsValidationCheck(form){

		var isValidate = true;

		var socCode = form.getItemValue("socCode");
		var pAgentCode = form.getItemValue("pAgentCode") || form.getItemValue("cntpntShopId")
		var isJehuFlagChk = form.isItemChecked("clauseJehuFlag") ? "Y" : "N";
		var isPartnerFlagChk = form.isItemChecked("clausePartnerOfferFlag") ? "Y" : "N";

		/*
		if(mvno.cmn.isEmpty(pAgentCode)){
			mvno.ui.alert("[대리점] 필수 입력 항목입니다");
			return false;
		}

		if(mvno.cmn.isEmpty(socCode)){
			mvno.ui.alert("[요금제] 필수 입력 항목입니다");
			return false;
		}
		*/

		var reqParam = {socCode, pAgentCode, isJehuFlagChk, isPartnerFlagChk};

		mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/termsValidationCheck.json", reqParam, function(resultData) {

			var jehuChkMap = resultData.result.jehuChkMap;
			var partnerChkMap = resultData.result.partnerChkMap;
			var termAlertMsg = "";

			// 제휴처 약관 동의 필수
			if(jehuChkMap.resultCd != "00"){

				// 기존 신청서 고려
				if(form._idIndex.clauseJehuFlag.enabled){
					isValidate= false;
					termAlertMsg += "[제휴서비스를 위한 동의] 필수 동의 항목입니다";
				}
			}

			// 제휴사 약관 동의 필수
			if(partnerChkMap.resultCd != "00"){

				// 기존 신청서 고려
				if(form._idIndex.clausePartnerOfferFlag.enabled){
					isValidate= false;
					if(termAlertMsg != "") termAlertMsg += "<br/>";
					termAlertMsg += "[제휴사 제공동의] 필수 동의 항목입니다"
				}
			}

			if(termAlertMsg != "") mvno.ui.alert(termAlertMsg);


		},{async:false});

		return isValidate;
	}

	function syncCombineSoloAddition(combineSoloType) {
		var additionKeyArr = PAGE.FORM1.getItemValue("additionKey").split(",").filter(key => key !== "138");
		if (combineSoloType === "Y") {
			additionKeyArr.push("138");
		}
		PAGE.FORM1.setItemValue("additionKey", additionKeyArr.join());
	}

	function updateCombineSoloVisible(pRateCd) {
		mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/canRequestCombineSolo.json", {pRateCd : pRateCd}, function(resultData) {
			if (resultData.result.canRequestCombineSolo) {
				PAGE.FORM1.enableItem("combineSoloType");
				PAGE.FORM1.enableItem("combineSoloTypeLabel");
			} else {
				PAGE.FORM1.disableItem("combineSoloType");
				PAGE.FORM1.disableItem("combineSoloTypeLabel");
				PAGE.FORM1.setItemValue("combineSoloType", "N");
				PAGE.FORM1.setItemValue("combineSoloFlag", "0");
			}
			syncCombineSoloAddition(PAGE.FORM1.getItemValue("combineSoloType"));
		},{async:false});
	}

</script>