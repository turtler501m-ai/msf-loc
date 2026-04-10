<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	/**
	 * @Class Name : rcpNewMgmt.jsp
	 * @Description : 신청등록
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2017.02.01 강무성 최초생성
	 * @ 2018.05.08 이상직 주석처리 부분 삭제
	 * @ 2018.08.11 이상직 [SRM18072675707] 선택형 5종 단체보험 출시 관련 전산 프로세스 구축
	 * @ 2018.09.11 이상직 [SRM18091729199] M전산 기기변경 기능 대리점 확대 적용
	 * @ 2018.12.18 이상직 [SRM18120707112] KOS분실고객 기기변경 신청등록 시 SMS인증 Skip 및 녹취ID인증 메뉴 개발요청
	 */
%>
<style>
	.prdt_block {
		margin-right: 15px;
	}
</style>
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
	
	var orgId = "${orgnInfo.orgnId}";
	var orgNm = "${orgnInfo.orgnNm}";
	
	var userId = "${loginInfo.userId}";
	var userName = "${loginInfo.userName}";
	
	var typeCd = "${orgnInfo.typeCd}";
	
	//조직 유형 - S:판매점, D:대리점, K:개통센터, "10:본사조직, 20:대리점, 30:판매점"
	if (typeCd == "20" || typeCd == "30" ) {
		isCntpShop = true;
		
		sOrgnId = "${orgnInfo.orgnId}";
		sOrgnNm = "${orgnInfo.orgnNm}";
		
		if(typeCd == "30"){
			hghrOrgnCd = "${orgnInfo.hghrOrgnCd}";
		}
	}
	
	if(typeCd == "10") {
		orgType = "K";
	}else if(typeCd == "20") {
		orgType = "D";
	}else if(typeCd == "30") {
		orgType = "S";
	}
	
	// 삭제권한 ( 본사사용자의 경우 Y )
	var delAuthYn = "N";
	var userOrgnTypeCd = "${loginInfo.userOrgnTypeCd}";
	
	if(userOrgnTypeCd == "10") {
		delAuthYn = "Y";
	}
	
	var salePlcyData = "";	//정책선택-combobox
	var modYn = false;		//수정 여부(기기변경 최초 등록 후 수정 시 Validation 체크)	
	
	var typeDtlCd2 = "${orgnInfo.typeDtlCd2}";
	var dvcChgAuthYn = "${dvcChgAuthYn}";	//기기변경 가능여부
	var menuId = "";						//다른 화면에서 들어오는 경우 해당 MenuID
	
	// 스캔관련
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

	var hubOrderYn = "${hubOrderYn}";

	var DHX = {
		FORM1 : {
			items : [
				{type:"block",name:"BODY", list:[
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					,{type: "fieldset", label: "신청서정보", name:"REQUEST", inputWidth:900, list:[
						{type:"block", list:[
								,{type:"hidden", name:"hubOrderSeq"}
								,{type:"input",  label: "알뜰폰허브주문번호",labelWidth:110 , width: 130, name: "hubOrderNum" , maxLength:16} //알뜰폰허브 주문번호
								,{type:"newcolumn"}
								,{type:"button", value: "조회",  name: "btnHubOrder"} //알뜰폰허브 주문정보 조회
						]}
						,{type:"block", list:[
							{type:"hidden", name:"serviceType"}
							,{type:"select", label:"신청유형", name:"operType", width:100, required:true, validate:"NotEmpty"}
							,{type:"newcolumn"}
							,{type:"select", label:"고객구분", name:"cstmrType", width:100, required:true, offsetLeft:10, validate:"NotEmpty"}
							,{type:"newcolumn"}
							,{type:"select", label:"구매유형", name:"reqBuyType", width:100, required:true, offsetLeft:10}
							,{type:"newcolumn"}
							,{type:"select", label:"신청서상태", name:"pstate", width:90, offsetLeft:10, validate:"NotEmpty"}
							,{type:"newcolumn"}
							,{type:"select", label:"진행상태", name:"requestStateCode", width:100, offsetLeft:10, validate:"NotEmpty"}
						]}
						,{type:"block", list:[
							{type:"calendar", label:"신청일자", name:"reqInDay", width:100, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d"}
							,{type:"hidden", name:"reqInDayStr"}
							,{type:"newcolumn"}
							,{type:"select", label:"상품구분", name:"prodType", width:100, offsetLeft:10, required:true}
							,{type:"newcolumn"}
							,{type:"hidden", label:"", name:"pAgentCode", value:sOrgnId}
							,{type:"input", label:"대리점", name:"pAgentName", value:sOrgnNm, width:122, offsetLeft:10, readonly: true, required:true}
							,{type:"newcolumn"}
							,{type:"button", value:"찾기", name:"btnOrgFind", disabled:isCntpShop}
							,{type:"newcolumn"}
							,{type:"hidden", label:"", name:"pShopCode", value:sShopId}
							,{type:"input", label:"판매점", name:"shopNm", value:sShopNm, width:122, offsetLeft:10, readonly: true, validate:'NotEmpty', required:true}
							,{type:"newcolumn"}
							,{type:"button", value:"찾기", name:"btnShopFind"}
							,{type:"hidden", label:"접점코드", name:"cntpntCd"}
						]}
						,{type:"block", name : "AUTH", list:[
							 {type:"select", label:"본인인증방식", name:"onlineAuthType", width:150, validate:"NotEmpty", value:"", labelWidth:80}
							,{type:"newcolumn"}
							,{type:"input", label:"본인인증정보", name:"onlineAuthInfo", width:242, offsetLeft:24, readonly: false, value:"", labelWidth:80}
							,{type:"newcolumn"}
							,{type:"select", label:"모집경로", name:"onOffType", width:100, offsetLeft:17}
							,{type:"newcolumn"}
							,{type:"input", label:"SMS인증", name:"smsAuthInfo", width:100, offsetLeft:25, maxLength:20}
							,{type:"newcolumn"}
							,{type:"button", value:"인증요청", name: "btnSendOtp", offsetLeft:10}
							,{type:"newcolumn"}
							,{type:"button", value:"인증확인", name: "btnOtpConfirm", offsetLeft:10}
							,{type:"newcolumn"}
							,{type:"button", value:"재전송", name: "btnReSendOtp", offsetLeft:10}
							,{type:"hidden", label:"매니저코드", name:"managerCode", width:130, offsetLeft:20, readonly: false, value:""}
						]}
						,{type:"block", list:[
							 {type:"input", label:"예약번호", name:"resNo", width:100, readonly: false, value:""}
							,{type:"newcolumn"}
							,{type:"select", label:"서식지", name:"faxyn", width:100, offsetLeft:10, value:""}	//공통코드 만들어야함
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
					
					// 기변
					,{type: "fieldset", label: "기기변경", name:"DVCCHG", inputWidth:900, list:[
						{type:"block", name:"DVCREG", list:[
							{type:"block", list:[
								{type:"input", label:"고객명", name: "custNm", width:120, labelWidth:80, maxLength:60}
								,{type:"newcolumn"}
								,{type:"input", label:"CTN", name:"subscriberNo", width:120, labelWidth:80, offsetLeft:50, validate:"ValidNumeric", maxLength:11}
								,{type:"newcolumn"}
								,{type:"select", label:"", name:"idntCd", width:120, offsetLeft:35, required:true}
								,{type:"newcolumn"}
								,{type:"input", name:"idntNum", width:120, maxLength:10, validate:"ValidInteger", maxLength:10}
								,{type:"newcolumn"}
								,{type: "button", value: "조회", name: "btnSearch"}
							]}
							,{type:"block", list:[
								{type:"input", label:"계약번호", name:"contractNum", width:120, labelWidth:80, readonly:true}
								,{type:"newcolumn"}
								,{type:"input", label:"계약상태", name:"subStatusNm", width:120, labelWidth:80, offsetLeft:50, readonly:true}
								,{type:"newcolumn"}
								,{type:"input", label:"상태변경사유", name:"subStatusRsnNm", width:170, labelWidth:120, offsetLeft:39, readonly:true}
							]}
							,{type:"block", list:[
								{type:"input", label:"개통일", name:"lstComActvDate", width:120, labelWidth:80, readonly:true}
								,{type:"newcolumn"}
								,{type:"input", label:"가능여부", name:"dvcChgNm", width:120, labelWidth:80, offsetLeft:50, readonly:true}
								,{type:"newcolumn"}
								,{type:"input", label:"기변유형", name:"dvcChgTpNm", width:170, labelWidth:120, offsetLeft:39, readonly:true}
								//,{type:"input", label:"미납금액", name:"unpdAmnt", width:170, labelWidth:120, offsetLeft:39, readonly:true, numberFormat:"0,000,000,000", userdata:{align:"right"}}
							]}
						]}
						,{type:"block", list:[
							{type:"select", label:"기변상세사유", name:"dvcChgRsnDtlCd", width:120, labelWidth:80}
							,{type:"newcolumn"}
							,{type:"select", label:"기변유형", name:"dvcChgType", width:120, offsetLeft:10}
							,{type:"newcolumn"}
							,{type:"select", label:"기변사유", name:"dvcChgRsnCd", width:120, offsetLeft:10}
							,{type:"newcolumn"}
							,{type:"input", label:"녹취ID", name:"dvcChgRecId", width:120, offsetLeft:10}
						]}
						,{type:"hidden", name:"subStatus"}
						,{type:"hidden", name:"dvcChgYn"}
						,{type:"hidden", name:"dvcChgTp"}
						,{type:"hidden", name:"otpNo"}
						,{type:"hidden", name:"otpCheckYN"}
						,{type:"hidden", name:"lostYn"}
					]}
					
					// 판매정보
					,{type: "fieldset", label: "판매정보(핸드폰대금)", inputWidth:900, name:"SALE", list:[
						,{type:"block", list:[
							{type:"input", label:"단말기일련번호", name:"reqPhoneSn", labelWidth:90, width:120, maxLength:30}
							,{type:"newcolumn"}
							,{type:"newcolumn"}
							,{type:"select", label:"요금제", name:"socCode", labelWidth:60, width:200, offsetLeft:10, validate:"NotEmpty"}
							,{type:"newcolumn"}
							,{type :"select", label:"약정기간", name :"agrmTrm", labelWidth:60, width:80, offsetLeft:10, validate:"NotEmpty"}
							,{type:"newcolumn"}
							,{type:"select", label:"할부기간", name:"instNom", labelWidth:60, width:80, offsetLeft:10, validate:"NotEmpty"}
						]}
						,{type:"block", list:[
							{type:"hidden", name:"cntpntShopId"}
							,{type:"hidden", name:"modelId"}
							,{type:"hidden", name:"recycleYn"}
							,{type:"hidden", name:"joinPrice"}
							,{type:"hidden", name:"usimPrice"}
							,{type:"hidden", name:"modelPrice"}
							,{type:"hidden", name:"modelPriceVat"}
							,{type:"hidden", name:"reqUsimName"}
						]}
						,{type:"block", list:[
							 {type:"select", label:"제조사", name:"mnfctId", labelWidth:90, width:120}
							,{type:"newcolumn"}
							,{type:"select", label:"단말모델", name:"prdtId", labelWidth:60, width:200, offsetLeft:10}
							,{type:"newcolumn"}
							,{type:"input", label:"단말코드", name:"prdtCode", labelWidth:60, width:80, offsetLeft:10, readonly:true }
							,{type:"newcolumn"}
							,{type:"select", label:"단말색상", name:"reqModelColor", labelWidth:60, width:80, offsetLeft:10}
						]}
						,{type:"block", list:[
							{type:"input", label:"출고가", name:"hndstAmt", labelWidth:90, width:80, readonly:true, numberFormat:"0,000,000,000",  value:""}
							,{type:"newcolumn"}
							,{type:"input", label:"공시지원금", name:"modelDiscount2", labelWidth:80, width:70, offsetLeft:20, readonly:true, numberFormat:"0,000,000,000", validate:"ValidInteger", value:""}
							,{type:"newcolumn"}
							,{type :"select", label:"할인유형", name:"sprtTp", labelWidth:80, width:80, offsetLeft:20}
							,{type:"newcolumn"}
							,{type:"input", label:"추가지원금", name:"modelDiscount3", labelWidth:80, width:80, offsetLeft:20, numberFormat:"0,000,000,000", maxLength: 10}
							,{type:"newcolumn"}
							,{type:"input", label:"", name:"drctMngmPrdcNm", labelWidth:60, width:130, readonly:true, disabled:true}	//v2019.01 직영상품명 추가
						]}
						,{type:"block", list:[
							{type:"input", label:"기본할인금액", name:"dcAmt", labelWidth:90, width:80, readonly: true, numberFormat:"0,000,000,000", maxLength:10, value:""}
							,{type:"newcolumn"}
							,{type:"input", label:"추가할인금액", name:"addDcAmt", labelWidth:80, width:70, offsetLeft:20, readonly: true, numberFormat:"0,000,000,000", maxLength:10, value:""}
							,{type:"newcolumn"}
							,{type:"input", label:"할부원금", name:"modelInstallment", labelWidth:80, width:80, offsetLeft:20, readonly:true, numberFormat:"0,000,000,000",  value:""}
							,{type:"newcolumn"}
							,{type:"input", label:"휴대폰모델명", name:"reqModelName", labelWidth:80, width:80, readonly:true, disabled:true, offsetLeft:20}
						]}
						,{type:"block", list:[
							{type:"input", label:"포인트사용금액", name:"usePointAmt", labelWidth:90, width:120, readonly:true, numberFormat:"0,000,000,000",  value:"", disabled:true}
						]}
						,{type:"block", list:[
							{type:"input", width:80, labelWidth:90, label:"렌탈단말금액", name:"rentalModelCpAmt", numberFormat:"0,000,000,000", validate:"ValidInteger", reaonly:true}
							,{type:"newcolumn"}
							,{type:"input", width:70, labelWidth:80, label:"렌탈기본료", name:"rentalBaseAmt", numberFormat:"0,000,000,000", validate:"ValidInteger", reaonly:true, offsetLeft:20}
							,{type:"newcolumn"}
							,{type:"input", width:80, labelWidth:70, label:"렌탈료할인", name:"rentalBaseDcAmt", numberFormat:"0,000,000,000", validate:"ValidInteger", reaonly:true, offsetLeft:10}
							,{type:"newcolumn"}
							,{type:"select", label:"렌탈단말모델", name:"rntlPrdtMdl", labelWidth:80, width:100, offsetLeft:11}
							,{type:"hidden", name:"rntlPrdtId", value:""}
							,{type:"hidden", name:"rntlPrdtCode", value:""}
							
						]}
						,{type:"block", list:[
							{type:"input", label:"유심일련번호", name:"reqUsimSn", labelWidth:90, width:150, maxLength:19, value:"" , validate:"ValidNumeric"}
							,{type:"newcolumn"}
							,{type:"select", label:"USIM구매", name:"usimPayMthdCd", labelWidth:80, width:80, offsetLeft:40, labelAlign:"right", required:true, validate:"NotEmpty", value:""}
							,{type:"newcolumn"}
							,{type:"select", label:"유심종류", name:"usimKindsCd", labelWidth:55, width:100, offsetLeft:90}
							,{type:"hidden", name:"iccId"}	// 기기변경시 기존 USIM 일련번호 저장
							
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
							,{type:"input", label:"IMEI1", name:"imei1", labelWidth:90, width:150, offsetLeft:10, maxLength:15}
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
						
						// 프로모션 정책
						/* ,{type:"block", name:"PRMTREG", list:[
							 {type:"input", label:"프로모션정책", name:"promotionCd", labelWidth:90, width:100, readonly:true}
							,{type:"newcolumn"}
							,{type:"input", label:"", name:"promotionNm", width:200, offsetLeft:0, readonly:true}
							,{type:"newcolumn"}
							,{type:"button", value:"정책선택", name: "btnPrmtFind"}
							,{type:"newcolumn"}
							,{type:"input", label:"", name:"benefitCmmt", width:374, offsetLeft:0, readonly:true}
							,{type:"hidden", name:"prmtAgrmTrm"}
							,{type:"hidden", name:"prmtOperType"}
							,{type:"hidden", name:"prmtRateCd"}
						]} */
						/* 프로모션  */
						,{type:"block", name:"PRMTR", list:[
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
							,{type:"select", label:"추천인 정보", name:"recommendFlagCd", labelWidth:90, width:120}
							,{type:"newcolumn"}
							,{type:"input", label:"", name:"recommendInfo", labelWidth:90, width:150}
							,{type:"newcolumn"}
							,{type:"select", label:"자급제 보상서비스", name:"rwdProdCd", labelWidth:110, width:190, offsetLeft:20, disabled:true}
							,{type:"newcolumn"}
							,{type:"checkbox", width:50, label:"가입동의", labelWidth:90, position:"label-right", name:"clauseRwdFlag", value:"Y", checked:false, offsetLeft:10, disabled:true}
						]}
						,{type:"block", list:[
							{type:"input", label:"KT인터넷 ID", name:"ktInterSvcNo", labelWidth:90 , width:270, maxLength:13}
						]}
					]}//판매정보 끝
					
					//고객혜택
					//v2018.02 기기변경 시 고객혜택 추가
					/* ,{type: "fieldset", label: "고객혜택", name:"BNFT", inputWidth:900, list:[
						,{type:"block", list:[
							{type:"input", label:"고객혜택명", name:"bnftName", width:680, labelWidth:80, readonly:true}
							,{type:"hidden", label:"고객혜택", name:"bnftKey"}
							,{type:"newcolumn"}
							,{type:"button", value: "고객혜택", name:"btnBnftFind"}
						]}
					]} */
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
					
					,{type: "fieldset", label: "고객정보", inputWidth:900, name:"CSTMRINFO", list:[
						,{type:"block", list:[
							{type:"input", label:"고객명", name:"cstmrName", width:200, maxLength:60, validate:"NotEmpty"}
							,{type:"newcolumn"}
							,{type:"input", label:"주민번호", name:"cstmrNativeRrn1", width:80, offsetLeft:42, maxLength:6, validate:"ValidNumeric"}
							,{type:"newcolumn"}
							,{type:"input", label:"-", name:"cstmrNativeRrn2", labelWidth:15, width:80, labelAlign:"center", maxLength:7,validate:"ValidNumeric"}
							,{type:"newcolumn"}
							,{type:"button", value: "개통이력", name:"btnCheckCstmr"}
							,{type:"hidden", name:"checkCstmr", value:"N"}
							,{type:"newcolumn"}
							,{type:"hidden", label: "본인확인(CI)", name: "selfCstmrCi", labelWidth:15, width:80}
						]}
						,{type:"block", list:[
							{type:"label", label:"본인조회동의", name:"selfInqryAgrmYnLabel", labelWidth:80}
							,{type:"newcolumn"}
							,{type:"checkbox", label:"동의", name:"selfInqryAgrmYn", width:100, value:"Y", position:"label-right"}
							,{type:"newcolumn"}
							,{type:"select", label:"본인인증방식", name:"selfCertType", labelWidth:90, width:130, offsetLeft:40}
							,{type:"newcolumn"}
							,{type:"input", label:"발급일자", name:"selfIssuExprDt", width:80, offsetLeft:50, maxLength:8}
							,{type:"newcolumn"}
							,{type:"input", label:"발급번호", name:"selfIssuNum", width:120, offsetLeft:43, maxLength:50}
						]}
						,{type:"block", name : "FATH", list:[
								{type:"checkbox", label:"안면인증대상", name:"fathTrgYn",labelWidth:80, width:50, value:"Y", position:"label-left", disabled:true}
								,{type:"newcolumn"}
								,{type:"button", value: "조회", name:"btnFT0"}
								,{type:"newcolumn"}
								,{type:"checkbox", width:50, labelWidth:80, label:"안면인증동의", offsetLeft:25, position:"label-left", name:"clauseFathFlag",value:"Y", disabled:true, checked:false}
								,{type:"newcolumn"}
								,{type:"input", label:"안면인증정보", name:"fathTransacId", width:130, offsetLeft:10, labelWidth:80, disabled:true}
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
							// 배송정보 입력가능 하도록 체크박스 추가 20170610 박준성
							,{type:"newcolumn"}
							,{type:"checkbox", label:"배송정보입력", name:"dlvryYn", labelWidth:100, width:100, position:"label-right"}
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
							,{type:"input", width:60, label:"외국인등록번호", labelWidth:90, name:"cstmrForeignerRrn1", maxLength:6, validate:"ValidNumeric"}
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
							,{type:"input", width:130, label:"사회보장번호", labelWidth:80, name:"cstmrForeignerDod", maxLength:83, validate:"ValidAplhaNumeric", offsetLeft:15}
							,{type:"newcolumn"}
							,{type:"calendar", width:100, label:"생년월일", name:"cstmrForeignerBirth", maxLength:10, readonly:true, offsetLeft:50}
						]}
						,{type:"block", list:[
							{type:"select", label:"VISA 코드", name:"visaCd", labelWidth:90, width:120}
						]}
					]}
					
					// 사업자정보
					,{type: "fieldset", label: "사업자정보", name:"BUSINESS", inputWidth:900, list:[
						{type:"settings"}
						,{type:"input", width:130, label:"상호명", labelWidth:60, name:"cstmrPrivateCname", maxLength:20}
						,{type:"input", width:130, label:"법인명", labelWidth:60, name:"cstmrJuridicalCname", maxLength:20}
						,{type:"newcolumn"}
						,{type:"input", width:40, label:"사업자번호",name:"cstmrPrivateNumber1", offsetLeft:10, maxLength:3, validate:"ValidNumeric"}
						,{type:"newcolumn"}
						,{type:"input", width:30, label:" -",name:"cstmrPrivateNumber2", labelAlign:"center", labelWidth:5, maxLength:2,validate:"ValidNumeric"}
						,{type:"newcolumn"}
						,{type:"input", width:40, label:" -",name:"cstmrPrivateNumber3", labelAlign:"center", labelWidth:5, maxLength:5, validate:"ValidNumeric"}
						,{type:"newcolumn"}
						,{type:"input", width:40, label:"사업자번호",name:"cstmrJuridicalNumber1", offsetLeft:10, maxLength:3, validate:"ValidNumeric"}
						,{type:"newcolumn"}
						,{type:"input", width:30, label:" -",name:"cstmrJuridicalNumber2", labelAlign:"center", labelWidth:5, maxLength:2,validate:"ValidNumeric"}
						,{type:"newcolumn"}
						,{type:"input", width:40, label:" -",name:"cstmrJuridicalNumber3", labelAlign:"center", labelWidth:5, maxLength:5, validate:"ValidNumeric"}
						,{type:"newcolumn"}
						,{type:"input", width:60, label:"법인번호",name:"cstmrJuridicalRrn1", offsetLeft:10, maxLength:6, validate:"ValidNumeric"}
						,{type:"newcolumn"}
						,{type:"input", width:80, label:" -",name:"cstmrJuridicalRrn2",labelAlign:"center",labelWidth:5,maxLength:7,validate:"ValidNumeric"}
						,{type:"newcolumn"}
					]}
					
					// 대리인
					,{type: "fieldset", label: "법정대리인", name:"MINORINFO",  inputWidth:900, list:[
						,{type:"block", list:[
							{type:"input", width:80, label:"대리인성명", labelWidth:80, name:"minorAgentName", maxLength:20}
							,{type:"newcolumn"}
							,{type:"select", width:90, label:"관계", labelWidth:30, name:"minorAgentRelation", offsetLeft:20}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=AGR"
							,{type:"newcolumn"}
							,{type:"input", width:70, label:"대리인주민번호", labelWidth:90, name:"minorAgentRrn1", offsetLeft:19, maxLength:6}
							,{type:"newcolumn"}
							,{type:"input", width:80, label:"-",name:"minorAgentRrn2",labelAlign:"center",labelWidth:5,maxLength:7}
							,{type:"newcolumn"}
							,{type:"select", width:75, label:"연락처", name:"minorAgentTelFn", labelWidth:40, offsetLeft:20}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=TEL,HPN"
							,{type:"newcolumn"}
							,{type:"input", width:50, label:"-", labelAlign:"center", labelWidth: 5, name:"minorAgentTelMn",maxLength:4}
							,{type:"newcolumn"}
							,{type:"input", width:50, label:"-", labelAlign:"center", labelWidth: 5, name:"minorAgentTelRn",maxLength:4}
						]}
						// 부정가입방지조회
						,{type:"block", list:[
							{type:"checkbox", width:130, label:"본인조회동의", name:"minorSelfInqryAgrmYn", labelWidth:80, value:"Y"}
							,{type:"newcolumn"}
							,{type:"select", labelWidth:65, width:90, label:"인증방식", name:"minorSelfCertType", labelAlign:"right", offsetLeft:43}
							,{type:"newcolumn"}
							,{type:"input", label:"발급일자", name:"minorSelfIssuExprDt", labelWidth:54, width:80, offsetLeft:55, maxLength:8}
							,{type:"newcolumn"}
							,{type:"input", label:"발급번호", name:"minorSelfIssuNum", labelWidth:55, width:120, offsetLeft:20}
						]}
					]}
					
					// 납부방법
					,{type: "fieldset", label: "요금납부정보", name:"PAYINFO", inputWidth:900, list:[
						{type:"settings"}
						,{type:"select", width:130, label:"요금납부방법", labelWidth:80, name:"reqPayType"}
						,{type:"newcolumn"}
					]}
					
					// 카드정보
					,{type: "fieldset", label: "카드정보", name:"CARD", inputWidth:900, list:[
						{type:"block", list:[
							{type:"select", width:103, label:"카드정보",name:"reqCardCompany"}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=CRD"
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
							,{type:"label", label:"무선데이터", name:"lessData", labelWidth:75, offsetLeft:50}
							,{type:"newcolumn"}
							,{type:"checkbox", label:"이용", name:"reqWireType1", labelWidth:35, width:4, position:"label-right", labelAlign:"left", checked:false}
							,{type:"newcolumn"}
							,{type:"checkbox", label:"차단", name:"reqWireType2", labelWidth:35, width:4, position:"label-right", labelAlign:"left", checked:false}
							,{type:"newcolumn"}
							//,{type:"checkbox", label:"데이터로밍차단", name:"reqWireType3", labelWidth:90, width:4, position:"label-right", labelAlign:"left", checked:false}
							,{type:"checkbox", label:"데이터로밍 완전 차단(MMS가능)", name:"reqWireType3", labelWidth:120, width:4, position:"label-right", labelAlign:"left", checked:false}
							,{type:"newcolumn"}
							,{type:"select", label:"가입비", name:"joinPayMthdCd", labelWidth:60, width:100, offsetLeft:35, labelAlign:"right", required:true, validate:"NotEmpty", value:""}
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
						,{type: "label", label: "가입희망번호", name:"reqWantNumberLabel", labelWidth: 80},
						,{type:"newcolumn"}
						,{type:"input", width:40, label:"1)", name:"reqWantNumber", labelWidth: 13, maxLength:4, validate:"ValidNumeric", offsetLeft: 0}
						,{type:"newcolumn"}
						,{type:"input", width:40, label:"2)", name:"reqWantNumber2", labelWidth: 13, maxLength:4, validate:"ValidNumeric", offsetLeft: 10}
						,{type:"newcolumn"}
						,{type:"input", width:40, label:"3)", name:"reqWantNumber3", labelWidth: 13, maxLength:4, validate:"ValidNumeric", offsetLeft: 10}
						,{type:"newcolumn"}
						,{type:"checkbox",width:10, label:"번호연결서비스",name:"reqGuideFlag", position:"label-left", checked:false, value:"N", labelWidth: 90, offsetLeft: 170}
						,{type:"newcolumn"}
						,{type:"select", width:75, label:"",name:"reqGuideFn", labelAlign:"right", labelWidth:98}
						,{type:"newcolumn"}
						,{type:"input", width:40, label:"-", labelAlign:"center", labelWidth: 10, name:"reqGuideMn", maxLength:4}
						,{type:"newcolumn"}
						,{type:"input", width:40, label:"-", labelAlign:"center", labelWidth: 10, name:"reqGuideRn", maxLength:4}
					]}
					
					// 번호이동
					,{type: "fieldset", label: "번호이동정보", inputWidth:900, name:"REQMOVE", list:[
						{type:"settings"}
						,{type:"block", list:[
							{type:"input", width:40, label:"이동번호",name:"moveMobileFn" , readonly:true}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=HPN"
							,{type:"newcolumn"}
							,{type:"input", width:40, label:"-", labelAlign:"center", labelWidth: 10, name:"moveMobileMn", maxLength:4}
							,{type:"newcolumn"}
							,{type:"input", width:40, label:"-", labelAlign:"center", labelWidth: 10, name:"moveMobileRn", maxLength:4}
							,{type:"newcolumn"}
							,{type:"select", width:130, label:"이동전통신사", name:"moveCompany", labelWidth: 80, labelAlign:"left", offsetLeft:50}//connector:"/rcp/rcpMgmt/getRcpCommon.json?grpId=NSC"
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
								,{type:"hidden", width:60, label:"", name:"fathUseYn"}
								,{type:"hidden", width:60, label:"", name:"isFathTrgFlag"}
						]}
					]}
					
					// 렌탈
					,{type: "fieldset", label: "렌탈서비스", name:"RENTAL", inputWidth:900, list:[
						{type:"settings"}
						,{type:"block", list:[
								{type:"checkbox", width:50,labelWidth:200, label:"렌탈서비스 이용 동의", position:"label-right", name:"clauseRentalService", value:"Y", checked:false}
								, {type:"newcolumn"}
								, {type:"checkbox", width:50,labelWidth:200, label:"단말배상금 안내사항 동의", position:"label-right", name:"clauseRentalModelCp", value:"Y", checked:false}
								, {type:"newcolumn"}
								, {type:"checkbox", width:50,labelWidth:200, label:"단말배상금(부분파손) 안내사항 동의", position:"label-right", name:"clauseRentalModelCpPr", value:"Y", checked:false}
						]}
					]}
				]}
			]
			,buttons : {
				right : {
					btnNew : {label : "새로작성" }
					,btnSave : {label : "저장" }
					,btnMod : {label : "수정" }
					//,btnClose : {label : "닫기" } //닫기 버튼 기능 제공이 필요 한경우 주석 제거 하여 사용
				},
				left : {
					btnSend: {label:"N-STEP 전송", auth:"A-NSTEP" },
					btnCopy: {label:"신청서 복사" }
				}
			}
			,onKeyDown : function (inp, ev, name, value) {
				if(name == "cstmrNativeRrn2" && ev.keyCode == 13) {
					PAGE.FORM1.callEvent("onButtonClick", ["btnCheckCstmr"]);
				}
				if(name == "hubOrderNum" && ev.keyCode == 13) {
					PAGE.FORM1.callEvent("onButtonClick", ["btnHubOrder"]);
				}
			}
			,onKeyUp : function (inp, ev, name, value){
				if(name == "cstmrNativeRrn1" || name == "cstmrNativeRrn2" || name == "cstmrJuridicalRrn1" || name == "cstmrJuridicalRrn2" || name == "hubOrderNum"){
					var keyId = ev.keyCode;
					if(!((keyId >= 48 && keyId <=57) ||( keyId >= 96 && keyId <= 105 ) || keyId == 8 || keyId == 46 || keyId == 37 || keyId == 39)){
						var val = PAGE.FORM1.getItemValue(name);
						PAGE.FORM1.setItemValue(name,val.replace(/[^0-9]/g,""));
					}
				}
			} 
			,onValidateMore : function (data) {
				var form=this;
				
				// v2017.01 신청관리 간소화 신청 신청유형 조건 추가
				// 신규/번호이동 가입 체크
				if(data.operType == "NAC3" || data.operType == "MNP3"){
					//v217.01 신청관리 간소화 FORM1에서 validate 삭제 하여 onValidateMore에 추가(주소, 상세주소, 명세서수신유형)
					/* if(data.cstmrPost == ""){
						this.pushError("cstmrPost","주소",["ICMN0001"]);
					} */
					
					/* 상세주소 필수값 제외
					if(data.cstmrAddrDtl == ""){
						this.pushError("cstmrAddrDtl","상세주소",["ICMN0001"]);
					} */
					
					if(data.cstmrBillSendCode == ""){
						this.pushError("cstmrBillSendCode","명세서수신유형",["ICMN0001"]);
					}
					
					// Maile 수신여부에 동의 or 명세서수신유형(메일) 인 경우
					if(data.cstmrMailReceiveFlag == "Y" || data.cstmrBillSendCode == "CB") {	// CB:이메일,LX:우편
						var cstmrMail1 = "";
						var cstmrMail2 = "";
						
						try {
							cstmrMail1 = data.cstmrMail1.replace(/\s/g,"");
							cstmrMail2 = data.cstmrMail2.replace(/\s/g,"");
						}
						catch(e) {
							cstmrMail1 ="pass";
						}
						
						// 메일 미 작성시 Error
						if(cstmrMail1 == "" || cstmrMail2 == "") {
							this.pushError("cstmrMail1","메일",["ICMN0001"]);
						}
					}
					
					//연락처 필수 체크
					if(data.operType == "NAC3" || data.operType == "MNP3"){
						if(!data.cstmrTelFn || !data.cstmrTelMn || !data.cstmrTelRn){
							//console.log("operType=" + data.operType);
							this.pushError("cstmrTelFn","연락처",["ICMN0001"]);
						}
						else if((data.cstmrTelMn.length < 3) || (data.cstmrTelRn.length < 4)) {
							this.pushError("cstmrTelFn","연락처","형식에 맞지 않습니다.");
						}
					}
					
					/*v217.01 신청관리간소화(전화번호 제외) - 연락처 항목으로 통합
					//휴대폰 번호 필수 체크
					if(!data.cstmrMobileFn || !data.cstmrMobileMn || !data.cstmrMobileRn){
						this.pushError("cstmrMobileFn","휴대폰",["ICMN0001"]);
					}
					else if((data.cstmrMobileMn.length < 3) || (data.cstmrMobileRn.length < 4)) {
						this.pushError("cstmrMobileFn","휴대폰","형식에 맞지 않습니다.");
					}*/
					
					//고객구분에 따른 체크 로직 : "NA:내국인,NM:내국인(미성년자),FN:외국인,JP:법인,PP:개인사업자"
					var cstmrType = data.cstmrType;
					if(cstmrType == "NA" || cstmrType == "NM" || cstmrType == "JP"){
						if(!data.cstmrNativeRrn1 || !data.cstmrNativeRrn2){
							this.pushError("cstmrNativeRrn1","주민번호",["ICMN0001"]);
						}
					}
				}
				
				// 신규/번호이동 가입 체크
				if(data.operType == "NAC3" || data.operType == "MNP3"){
					
					switch(cstmrType){
						case "NM":
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
								if(data.nwBlckAgrmYn != "Y"){
									this.pushError("nwBlckAgrmYn", "네트워크차단동의", ["ICMN0001"]);
								}
								// 2015-05-11, 유해정보차단APP 설치동의 필수조건 변경
								if(data.appBlckAgrmYn != "Y"){
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
							
						case "FN":
							if(!data.cstmrForeignerRrn1 || !data.cstmrForeignerRrn2){
								this.pushError("cstmrForeignerRrn1","외국인번호",["ICMN0001"]);
							}
							if(!data.cstmrForeignerNation){
								this.pushError("cstmrForeignerNation","국적",["ICMN0001"]);
							}
							/* 기존의 외국인정보는 값이 없으므로 필수항목 할수 없음.20201229 송정섭
							if(!data.visaCd){
								this.pushError("visaCd","VISA 코드",["ICMN0001"]);
							}
							*/
							
							break;
							
						case "JP":
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
							
						case "PP":
							if(!data.cstmrPrivateCname){
								this.pushError("cstmrPrivateCname","상호명",["ICMN0001"]);
							}
							if(!data.cstmrPrivateNumber1 || !data.cstmrPrivateNumber2 || !data.cstmrPrivateNumber3){
								this.pushError("cstmrPrivateNumber1","사업자등록번호",["ICMN0001"]);
							}
							
							break;
					}
				}
					
				// 2015-04-20 본인인증관련
				// 신청서 정상, 접수대기 상태에서만 체크한다.
				if(data.operType == "NAC3" || data.operType == "MNP3"){
					if(data.pstate == "00" && data.requestStateCode == "00"){
						var labelNm = form.getItemLabel("selfIssuExprDt");
						
						if(!data.selfInqryAgrmYn || data.selfInqryAgrmYn == "" || data.selfInqryAgrmYn != "Y"){
							this.pushError("selfInqryAgrmYn", "본인조회동의", "부정가입방지를 위한 본인조회동의를 선택하세요");
						}
							
						if(data.cstmrType != "NM" && ( !data.selfCertType || data.selfCertType == "")){
							this.pushError("selfCertType", "인증방식", "본인인증방식을 선택하세요");
						}
						
						// 2016-10-06, 개통이력 확인
						if((data.cstmrType == "NA" || data.cstmrType == "NM") && data.reqBuyType == "MM" && data.checkCstmr != "Y"){
							this.pushError("checkCstmr","개통이력조회","내국인(미성년자)인 경우 개통이력을 조회하세요");
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
									this.pushError("minorSelfIssuExprDt", "법정대리인 만료일자", "여권 만료일자를 선택하세요");
								}else{
									this.pushError("minorSelfIssuExprDt", "법정대리인 발급일자", "발급일자를 선택하세요");
								}
							}else{
								if(!checkDate(data.minorSelfIssuExprDt)){
									if(data.minorSelfCertType == "05"){
										this.pushError("minorSelfIssuExprDt", "법정대리인 만료일자", "날짜형식이 유효하지 않습니다.");
									}else{
										this.pushError("minorSelfIssuExprDt", "법정대리인 발급일자", "날짜형식이 유효하지 않습니다.");
									}
								}
								
								if((data.minorSelfCertType == "02" || data.minorSelfCertType == "04") && (!data.minorSelfIssuNum || data.minorSelfIssuNum == "")) {
									this.pushError("minorSelfIssuNum", "법정대리인 발급번호", "운전면허증/국가유공자증 발급번호를 입력하세요");
								}
							}
						}else if(data.cstmrType == "FN"){
							if(data.selfCertType != "05" && data.selfCertType != "06" && data.selfCertType != "07"){
								this.pushError("selfCertType","인증방식","외국인의 경우 인증방식은 여권, 외국인등록증, 국내거소신고증을 선택하세요");
							}
							
							if(!data.selfIssuExprDt || data.selfIssuExprDt == ""){
								this.pushError("selfIssuExprDt", labelNm, "여권 만료일자를 선택하세요");
							}else{
								if(!checkDate(data.selfIssuExprDt)){
									this.pushError("selfIssuExprDt", labelNm, "날짜형식이 유효하지 않습니다.");
								}
							}
						}else if(data.cstmrType == "NA"){
							if(data.selfCertType == "06" || data.selfCertType == "07"){
								this.pushError("selfCertType","인증방식","내국인의 인증방식은 외국인등록증, 국내거소신고증을 선택할 수 없습니다.");
							}
							
							if(!data.selfIssuExprDt || data.selfIssuExprDt == ""){
								if(data.selfCertType == "05"){
									this.pushError("selfIssuExprDt", labelNm, "여권 만료일자를 선택하세요");
								}else{
									this.pushError("selfIssuExprDt", labelNm, "발급일자를 선택하세요");
								}
							}else{
								if(!checkDate(data.selfIssuExprDt)){
									this.pushError("selfIssuExprDt", labelNm, "날짜형식이 유효하지 않습니다.");
								}
								
								if((data.selfCertType == "02" || data.selfCertType == "04") && (!data.selfIssuNum || data.selfIssuNum == "")) {
									this.pushError("selfIssuNum", "발급번호", "운전면허증/국가유공자증 발급번호를 입력하세요");
								}
							}
						}
					}
				}
				
				//요금납부정보 : "C:신용카드,D:자동이체,R:지로","AA:자동충전(이체),VA:가상계좌"
				var reqPayType = data.reqPayType;
				if(data.operType == "NAC3" || data.operType == "MNP3"){
					if(data.pstate == "00" && !reqPayType){
						this.pushError("reqPayType","요금납부방법","필수 선택 항목입니다.");
					}
				}
				
				switch(reqPayType){
					case "C":
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
						
					case "D":
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
						
				}
				
				//구매유형에 따른 체크 : "MM:단말구매,UU:유심단독구매"
				var reqBuyType = data.reqBuyType;
				
				switch(reqBuyType){
				
					case "MM":
						// PROD_TYPE != "02" 렌탈이 아닌 경우 체크
						if(data.prodType != "02" && !data.reqModelColor) {
							this.pushError("reqModelColor","모델색상",["ICMN0001"]);
						}
						
						break;
				}
				
				//번호연결서비스
				if(data.reqGuideFlag == "Y" && (!data.reqGuideFn || !data.reqGuideMn || !data.reqGuideRn)){
					this.pushError("reqGuideFlag","번호연결서비스",["ICMN0001"]);
				}
				
				//부가서비스
				/* v217.01 신청관리 간소화 (필수 항목 제외)
				if(data.operType == "NAC3" || data.operType == "MNP3"){
					if(!data.additionName){
						this.pushError("additionName","부가서비스",["ICMN0001"]);
					}
				} */
				
				//업무구분에 따른 체크 : "MNP3:번호이동,NAC3:개통"
				var operType = data.operType;
				
				switch(operType) {
					case "NAC3":
						//가입희망번호
						if(!data.reqWantNumber){
							this.pushError("reqWantNumber","가입희망번호",["ICMN0001"]);
						}
						
						if(!mvno.cmn.isEmpty(data.reqWantNumber) && data.reqWantNumber.length != 4){
							this.pushError("reqWantNumber","1) 가입희망번호","4자리를 입력 해주세요.");
						}
						
						/* if(!mvno.cmn.isEmpty(data.reqWantNumber2) && data.reqWantNumber2.length != 4){
							this.pushError("reqWantNumber2","2) 가입희망번호","4자리를 입력 해주세요.");
						}
						
						if(!mvno.cmn.isEmpty(data.reqWantNumber3) && data.reqWantNumber3.length != 4){
							this.pushError("reqWantNumber3","3) 가입희망번호","4자리를 입력 해주세요.");
						} */
						
						break;
					
					case "MNP3":
						// 번호이동시 20231229 moveAuthType moveAuthNumber valid 삭제
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
						
						break;
						
				}
				
				if(data.faxyn == "Y" && ( !data.faxnum1 || !data.faxnum2 || !data.faxnum3 )) {
					this.pushError("faxnum1","팩스번호",["ICMN0001"]);
				}
				
				//v217.02 신청관리 간소화 기기변경 유효성 체크 추가 
				if(data.operType == "HDN3" || data.operType == "HCN3"){
					if(!modYn){
						// 계약정보 확인
						if(data.contractNum == ""){
							this.pushError("contractNum","계약번호","가입고객이 아닙니다.");
						}
						
						if(data.dvcChgYn != "Y"){
							this.pushError("dvcChgYn","가능여부","기기변경 신청을 할 수 없습니다.");
						}
						
						// SMS인증시 체크 추가
						if(data.onlineAuthType == "S" && (data.smsAuthInfo == "" || data.otpCheckYN != "Y")){
							this.pushError("smsAuthInfo","SMS인증","SMS 인증을 진행하세요.");
						}
						
						// [SRM18120707112] KOS분실고객 기기변경 신청등록 시 SMS인증 Skip 및 녹취ID인증 메뉴 개발요청
						// 분실정지고객 녹취ID 입력 확인
						if(data.onlineAuthType == "B" && data.lostYn == "Y" && data.smsAuthInfo == "") {
							this.pushError("smsAuthInfo","녹취ID","녹취ID를  입력하세요.");
						}
						// [SRM18120707112] KOS분실고객 기기변경 신청등록 시 SMS인증 Skip 및 녹취ID인증 메뉴 개발요청
						
						/* v2017.12 기기변경 신청시 미납여부 체크 조건 해제
						if(Number(data.unpdAmnt) > 100000){
							this.pushError("unpdAmnt","미납금액","미납금액이 10만원 이상입니다.");
						} */
					}
					
					// 기변사유 체크
					if(!data.dvcChgRsnDtlCd || data.dvcChgRsnDtlCd == ""){
						this.pushError("dvcChgRsnDtlCd","상세사유",["ICMN0001"]);
					}
					
					// 고객센터(TM)인 경우 배송정보 체크
					if(data.onOffType == "4"){
						if(!data.dlvryName || data.dlvryName == ""){
							this.pushError("dlvryName","받는분",["ICMN0001"]);
						}
						//if(!data.dlvryAddr || !data.dlvryAddrDtl){ 상세주소 필수값 제외
						if(!data.dlvryAddr){
							this.pushError("dlvryPost","배송주소",["ICMN0001"]);
						}
						if(!data.dlvryMobileFn || !data.dlvryMobileMn || !data.dlvryMobileRn){
							this.pushError("dlvryMobileFn","휴대폰",["ICMN0001"]);
						}
					}
					
					// 본사(고객센터)가 아닌 경우 서식지 체크
					if(typeCd != "10" && data.scanId == null && data.scanId == ""){
						this.pushError("scanId","서식지","서식지가 존재하지 않습니다.");
					}
				}
				
				//v2017.03 신청관리 간소화 요금제 필수 체크( 기존 disable 상태의 경우 필수 체크가 안됨 )
				if(!data.socCode || data.socCode == ""){
					this.pushError("socCode","요금제",["ICMN0001"]);
				}

				//v2017.03 신청관리 간소화 금용제휴 체크 (동부 관련)
				if(data.socCode == "KISSAVE01" || data.socCode == "KISSAVE02"){
					if(data.clauseFinanceFlag != "Y"){
						this.pushError("clauseFinanceFlag", "[생활안심요금제] 개인(신용)정보 처리 및 보험가입 동의", ["ICMN0001"]);
					}
				}
				
				if(data.prodType == "02" && data.reqBuyType == "UU"){
					if(!data.rntlPrdtId || data.rntlPrdtId == ""){
						this.pushError("rntlPrdtMdl", "렌탈단말모델", ["ICMN0001"]);
					}
				}
				
				// 구매유형에 따른 단말보험 validation
				if(!mvno.cmn.isEmpty(data.insrProdCd) && form.isItemChecked("clauseInsrProdFlag") == false) {
					this.pushError("clauseInsrProdFlag", "단말보험가입동의", ["ICMN0001"]);
				}
				
				//유심종류  esim 의 경우 단말모델 필수
				if(data.usimKindsCd == "09" && (!data.prdtId || data.prdtId == "" )){
					this.pushError("prdtId", "단말모델", ["ICMN0001"]);
				}

				// 제휴처 및 제휴사 약관 validation
				termsValidationCheck(form);

			}
			,onChange : function(name, value) {
				var form=this;
				
				form1Change(form, 1, name);
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
							if (result.orgnId === "1100017898" && hubOrderYn === "N") {
								mvno.ui.alert("현재 알뜰폰 허브 주문 등록을 진행할 수 없습니다.");
								return;
							}

							form.setItemValue("pAgentCode", result.orgnId);
							form.setItemValue("pAgentName", result.orgnNm);
							
							sOrgnId = result.orgnId;
							sOrgnNm = result.orgnNm;
							getDisPrmtListCombo(PAGE.FORM1, "Y");
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

					// 알뜰폰허브 주문정보 조회
					case "btnHubOrder" :
						if(form.getItemValue("hubOrderNum") == ""){
							mvno.ui.alert("알뜰폰허브 주문번호를 입력하세요.");
							return;
						}

						var param = {"orderSeq" : form.getItemValue("hubOrderNum") }

						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getHubOrder.do", param, function(result) {
							mvno.ui.alert("알뜰폰허브 주문정보 불러오기 성공");
							initHubOrder();
							form.setItemValue("hubOrderSeq", result.result.data.orderSeq);
							setHubOrder(result.result.data);
						});

						break;
					
					case "btnRateFind":
						mvno.ui.popupWindowTop(ROOT + "/rcp/rcpMgmt/getRcpRate.do?requestKey="+ form.getItemValue("requestKey"), "부가서비스", 790, 500,
							{
								param : {
									callback : function(result) {
										form.setItemValue("additionName", result.additionName);
										form.setItemValue("additionKey", result.additionKey);
										form.setItemValue("additionPrice", result.rantal);
										syncCombineSoloAddition(form.getItemValue("combineSoloType"));
										
										console.log("soc=" + result.soc);
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
								if(PAGE.FORM1.getItemValue("cstmrType") == "NM") { //미성년자
									PAGE.FORM1.setItemValue("minorSelfCertType", resultData.result.selfCertType);
									form1Change(PAGE.FORM1, 1, "minorSelfCertType");
									PAGE.FORM1.setItemValue("minorSelfIssuExprDt", resultData.result.selfIssuExprDt);
									PAGE.FORM1.setItemValue("minorSelfIssuNum", resultData.result.driverSelfIssuNum);
								} else {
									PAGE.FORM1.setItemValue("selfCertType", resultData.result.selfCertType);
									form1Change(PAGE.FORM1, 1, "selfCertType");
									PAGE.FORM1.setItemValue("selfIssuExprDt", resultData.result.selfIssuExprDt);
									PAGE.FORM1.setItemValue("selfIssuNum", resultData.result.driverSelfIssuNum);
								}
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
						
					case "btnSend": //nstep전송

						 
						//안면인증 사용여부 공통코드
						if(PAGE.FORM1.getItemValue("fathUseYn") == "Y") {
							if(form.getItemValue("isFathTrgFlag") != "1") {
								mvno.ui.alert("안면인증 대상여부 확인 후 다시 시도해주세요.");
								return;
							}
						}

						
						
						//Knote 사용 판매점인지 검사 시작//////////////////////////////////
						if(mvno.cmn.isEmpty(this.getItemValue("shopCd"))){ //shopCd가 없으면 cntpntCd(판매점코드) 넣어줌
							this.setItemValue("shopCd", this.getItemValue("cntpntCd"));
						}
						var isKnote = false;

						var param = {
							"cntpntShopId" : form.getItemValue("cntpntShopId")
							, "agentCode" : form.getItemValue("agentCode")
							, "shopCd" :  form.getItemValue("shopCd")
							, "requestKey" : form.getItemValue("requestKey")
						};
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/checkKnoteShop.json", param, function(resultData) {
						}, {async:false, dimmed:true, errorCallback:function(){
							isKnote = true;
						}});
						if(isKnote) return;
						//Knote 사용 판매점인지 검사 끝//////////////////////////////////

						var isAuthChk = true;
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/checkAuth.json", param, function(resultData) {
						}, {async:false, dimmed:true, errorCallback:function(){
							isAuthChk = false;
						}});
						if(!isAuthChk) return;

						if(this.getItemValue("usimKindsCd") != "09" ){
							if(this.getItemValue("reqUsimSn")==""){
								mvno.ui.alert("유심번호를 입력하세요.");
								return;
							}
						}

						if(this.getItemValue("reqBuyType") == "MM" && this.getItemValue("prodType") != "02" && this.getItemValue("reqPhoneSn") == ""){
							mvno.ui.alert("단말기일련번호를 입력하세요.");
							return;
						}

						var reg = /[`~@#$%^&*|\\\'\";:\/?^ㄱ-ㅎ가-힣]/gi;

						if(!mvno.cmn.isEmpty(this.getItemValue("ktInterSvcNo"))){
							if (reg.test(this.getItemValue("ktInterSvcNo"))) {
								mvno.ui.alert("KT인터넷 ID에 특수문자나 한글을 넣을 수 없습니다.");
								return ;
							}
						}

						var url = "/rcp/rcpMgmt/updateRcpNewDetail.json";

						// 기기변경 신청용 url 처리
						if(this.getItemValue("operType") == "HCN3" || this.getItemValue("operType") == "HDN3"){
							url = "/rcp/rcpMgmt/saveRcpRequest.json";
						}

						// 신청정보 UPDATE 후 N-STEP 전송으로 변경
						mvno.cmn.ajax(ROOT + url, form, function() {
							mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getNstepSendUrl.json", form, function(data) {
								form.setItemValue("appEventCd", "FS5");
								mvno.cmn.ajax(ROOT + data.result.sendUrl, form, function(resultData) {
									mvno.ui.alert(resultData.result.msg);
								});
							});
						});
						
						break;
						
					case "btnSave":
						
						var url = "";
						if( menuId == "MSP1010100" ||	//서식지함
							form.getItemValue("operType") == "HCN3" || form.getItemValue("operType") == "HDN3" ){
							url = "/rcp/rcpMgmt/insertRcpNewRequest.json";
						}else{
							url = "/rcp/rcpMgmt/updateRcpNewDetail.json";
						}
						
						// 2018.06.30 판매정책을 더 이상 관리하지 않으므로 히든 처리된 값들에 대하여 세팅한다.
						if(mvno.cmn.isEmpty(form.getItemValue("pAgentCode"))){
							mvno.ui.alert("대리점을 선택하세요");
							return;
						}
						
						if(mvno.cmn.isEmpty(form.getItemValue("cntpntShopId"))){
							form.setItemValue("cntpntShopId", form.getItemValue("pAgentCode"));
						}
						
						if(mvno.cmn.isEmpty(form.getItemValue("shopCd"))){
							form.setItemValue("shopCd", form.getItemValue("pAgentCode"));
						}

						// 본인확인 여부 체크
						if (form.getItemValue("pAgentCode") == "1100017898") {
							if (mvno.cmn.isEmpty(form.getItemValue("hubOrderSeq"))) {
								mvno.ui.alert("알뜰폰허브 주문번호 조회를 먼저 진행하세요.");
								return;
							}
							if (mvno.cmn.isEmpty(form.getItemValue("selfCstmrCi"))) {
								mvno.ui.alert("본인인증 정보(CI)가 존재하지 않습니다.");
								return;
							}
						} else {
							form.setItemValue("hubOrderSeq", "");
						}

                        if (form.getItemValue("personalInfoCollectAgree") != "Y") {
                            if (form.getItemValue("clausePriAdFlag") == "Y") {
                                mvno.ui.alert("{개인정보 처리 위탁 및 혜택 제공 동의}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의}에 동의하여야 합니다.");
                                return;
                            }
                        }

                        //광고수신동의추가
						if (form.getItemValue("personalInfoCollectAgree") != "Y" || form.getItemValue("clausePriAdFlag") != "Y") {
							if (form.getItemValue("othersTrnsAgree") == "Y") {
								mvno.ui.alert("{혜택 제공을 위한 제 3자 제공 동의 : M모바일}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
								return;
							}
							if (form.getItemValue("othersTrnsKtAgree") == "Y") {
								mvno.ui.alert("{혜택 제공을 위한 제 3자 제공 동의 : KT}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
								return;
							}
							if (form.getItemValue("othersAdReceiveAgree") == "Y") {
								mvno.ui.alert("{제 3자 제공관련 광고 수신 동의}를 위해서는 {고객 혜택 제공을 위한 개인정보 수집 및 관련 동의, 개인정보 처리 위탁 및 혜택 제공 동의}에 동의하여야 합니다.");
								return;
							}
						}
						
						// 기기변경인 경우 고객조회 체크
						if(form.getItemValue("operType") == "HCN3" || form.getItemValue("operType") == "HDN3"){
							if(form.getItemValue("contractNum") == "") {
								mvno.ui.alert("기기변경인 경우 고객조회를 먼저 진행하세요.");
								return;
							}
							
// 							if(form.getItemValue("smsAuthInfo") == "") {
// 								if(form.getItemValue("onlineAuthType") == "S") {
// 									mvno.ui.alert("SMS인증번호를 입력하세요.");
// 								}
// 								else if(form.getItemValue("onlineAuthType") == "B"){
// 									mvno.ui.alert("녹취ID를 입력하세요.");
// 								}
// 								return;
// 							}
						}
						
						
						
						if(form.getItemValue("reqBuyType") == "MM"){
							if(mvno.cmn.isEmpty(form.getItemValue("prdtId"))){
								mvno.ui.alert("단말모델을 선택하세요");
								return;
							}
							if(mvno.cmn.isEmpty(form.getItemValue("sprtTp"))){
								mvno.ui.alert("할인유형을 선택하세요");
								return;
							}
							if(mvno.cmn.isEmpty(form.getItemValue("reqModelName"))) form.setItemValue("reqModelName", form.getItemValue("prdtCode"));
						}
						
						if(form.getItemValue("reqBuyType") == "UU"){
							if(mvno.cmn.isEmpty(form.getItemValue("modelId"))) form.setItemValue("modelId", form.getItemValue("prdtId"));
							if(mvno.cmn.isEmpty(form.getItemValue("reqUsimName"))) form.setItemValue("reqUsimName", form.getItemValue("prdtCode"));
						}
						
						var reg = /[`~@#$%^&*|\\\'\";:\/?^ㄱ-ㅎ가-힣]/gi;
						
						if(!mvno.cmn.isEmpty(form.getItemValue("ktInterSvcNo"))){
							if (reg.test(form.getItemValue("ktInterSvcNo"))) {
								mvno.ui.alert("KT인터넷 ID에 특수문자나 한글을 넣을 수 없습니다.");
								return ;
							}	
						}
						// SRM18072675707, 단체보험체크
						if(grpInsrYn == "Y"){
							// 보험가입 동의하더라도 단체보험 미선택 처리
							if(mvno.cmn.isEmpty(form.getItemValue("insrCd")) && form.isItemChecked("clauseInsuranceFlag") == true){
								mvno.ui.alert("보험가입동의시 단체보험을 선택하세요.");
								return;
							}
							
							if(!mvno.cmn.isEmpty(form.getItemValue("insrCd")) && form.isItemChecked("clauseInsuranceFlag") == false){
								mvno.ui.alert("단체보험 선택시 단체보험가입동의를 선택하세요.");
								return;
							}
							
							// 국민키즈안심보험을 제외한 단체보험은 만 15세 이상 가입가능
							if(form.getItemValue("insrCd") != "" && form.isItemChecked("clauseInsuranceFlag") == true){
								if(form.getItemValue("insrCd") != "C0" && fnGetAge(form) < 15){
									mvno.ui.alert("만 15세 이상 가입 가능합니다.");
									return;
								}
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
						
						var msg = "단체보험 등 입력한 내용을 확인부탁드립니다.<br />";
						if(form.getItemValue("operType") == "HDN3" || form.getItemValue("operType") == "HCN3") {
							msg = "";
						}
						
						mvno.cmn.ajax4confirm(msg + "저장하시겠습니까?<br />완료시까지 잠시만 기다려주세요.", ROOT + url, form, function(result) {
							
							mvno.ui.alert("NCMN0004");
							
							form.clear();
							form.setItemValue("requestKey", result.result.requestKey);
							form1ReadInit();
							
						}, {async:false, dimmed:true});
						
						break;
						
					case "btnMod":
						// SRM18072675707, 단체보험체크
						console.log("grpInsrYn=" + grpInsrYn);
						if(grpInsrYn == "Y"){
							// 보험가입 동의하더라도 단체보험 미선택 처리
							if(mvno.cmn.isEmpty(form.getItemValue("insrCd")) && form.isItemChecked("clauseInsuranceFlag") == true){
								mvno.ui.alert("단체보험가입동의 선택시 단체보험을 선택하세요.");
								return;
							}
							
							if(!mvno.cmn.isEmpty(form.getItemValue("insrCd")) && form.isItemChecked("clauseInsuranceFlag") == false){
								mvno.ui.alert("단체보험 선택시 단체보험가입동의를 선택하세요.");
								return;
							}
							
							// 국민키즈안심보험을 제외한 단체보험은 만 15세 이상 가입가능
							if(form.getItemValue("insrCd") != "" && form.isItemChecked("clauseInsuranceFlag") == true){
								if(form.getItemValue("insrCd") != "C0" && fnGetAge(form) < 15){
									mvno.ui.alert("만 15세 이상 가입 가능합니다.");
									return;
								}
							}
						}
						
						var reg = /[`~@#$%^&*|\\\'\";:\/?^ㄱ-ㅎ가-힣]/gi;
						if(!mvno.cmn.isEmpty(form.getItemValue("ktInterSvcNo"))){
							if (reg.test(form.getItemValue("ktInterSvcNo"))) {
								mvno.ui.alert("KT인터넷 ID에 특수문자나 한글을 넣을 수 없습니다.");
								return ;
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

						var btnModFn= function(){
							var msg = "단체보험 등 입력한 내용을 확인부탁드립니다.<br />";
							if(form.getItemValue("operType") == "HDN3" || form.getItemValue("operType") == "HCN3") {
								msg = "";
							}

							mvno.cmn.ajax4confirm(msg + "수정하시겠습니까?", ROOT + "/rcp/rcpMgmt/updateRcpNewDetail.json", form, function(result) {
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
						//서식지함 메뉴를 통한 경우 scanID 자동 지정
						if(menuId == "MSP1010100"){
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
									mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"resNo":form.getItemValue("resNo"),"trgtMenuId":"MSP1000014","tabId":"paper"}, function(result){});
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
							
							var data = null;
							
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
						
					// 녹취파일등록
					case "btnRecSarch" :
						
						var strRequestKey = form.getItemValue("requestKey");
						
						if(mvno.cmn.isEmpty(strRequestKey) || strRequestKey == null || strRequestKey == "null" || strRequestKey == "") {
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
									if(mvno.cmn.isEmpty(resultData2.result.scanId) || resultData2.result.scanId == null || resultData2.result.scanId == "null") {
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
									if(mvno.cmn.isEmpty(resultData2.result.scanId) || resultData2.result.scanId == null || resultData2.result.scanId == "null") {
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
						
					//기기변경 대상 조회
					case "btnSearch" :
						
						form.setItemValue("contractNum", "");
						form.setItemValue("subStatusNm", "");
						form.setItemValue("lstComActvDate", "");
						form.setItemValue("dvcChgYn", "");
						form.setItemValue("dvcChgNm", "");
						form.setItemValue("dvcChgTp", "");
						form.setItemValue("dvcChgTpNm", "");
						//form.setItemValue("unpdAmnt", "");
						form.setItemValue("reqUsimSn", "");
						form.setItemValue("iccId", "");
						form.setItemValue("smsAuthInfo", "");
						form.setItemValue("otpCheckYN", "");
						// [SRM18120707112] KOS분실고객 기기변경 신청등록 시 SMS인증 Skip 및 녹취ID인증 메뉴 개발요청
						form.setItemValue("subStatus", "");
						form.setItemValue("subStatusRsnNm", "");
						form.setItemValue("lostYn", "");
						form.setItemValue("dvcChgRecId", "");
						// [SRM18120707112] KOS분실고객 기기변경 신청등록 시 SMS인증 Skip 및 녹취ID인증 메뉴 개발요청
						
						if(form.getItemValue("onlineAuthType") == "S"){
							form.showItem("btnSendOtp");
							form.hideItem("btnOtpConfirm");
							form.hideItem("btnReSendOtp");
						}
						
						form.setItemValue("cstmrTelFn", "");
						form.setItemValue("cstmrTelMn", "");
						form.setItemValue("cstmrTelRn", "");
						form.setItemValue("cstmrName", "");
						form.setItemValue("cstmrNativeRrn1", "");
						form.setItemValue("cstmrNativeRrn2", "");
						
						if(form.getItemValue("custNm") == null || form.getItemValue("custNm") == ""){
							mvno.ui.alert("고객명을 입력하세요.");
							return;
						}
						
						var strTelNo = form.getItemValue("subscriberNo");
						
						if(strTelNo == null || strTelNo == "" || strTelNo.length != 11){
							mvno.ui.alert("전화번호 11자리를 입력하세요.");
							return;
						}
						
						var strIdntNum = form.getItemValue("idntNum");
						
						if(form.getItemValue("idntCd") == "10"){
							if(strIdntNum == null || strIdntNum == "" || strIdntNum.length != 6){
								mvno.ui.alert("생년월일 6자리를 입력하세요.");
								return;
							}
						}else{
							if(strIdntNum == null || strIdntNum == "" || strIdntNum.length != 10){
								mvno.ui.alert("사업자번호 10자리를 입력하세요.");
								return;
							}
						}
						
						var formData = {
								"custNm":form.getItemValue("custNm")
								,"subscriberNo":strTelNo
								,"idntCd":form.getItemValue("idntCd")
								,"idntNum":strIdntNum
						}
						
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getDvcChgPsblCheck.json", formData, function(data) {
							var result= data.result.data.rows[0];
							
							if(result == null){
								mvno.ui.alert("M모바일 고객이 아니거나 검색조건이 부정확합니다.");
								
								return;
							}else{

								// [SRM18120707112] KOS분실고객 기기변경 신청등록 시 SMS인증 Skip 및 녹취ID인증 메뉴 개발요청
								if(result.subStatus == "S") {
									// 분실여부
									form.setItemValue("lostYn", result.lostYn);
									form.setItemValue("subStatusRsnNm", result.subStatusRsnNm);
									if(result.lostYn == "Y") {
										mvno.ui.alert("분실정지 고객으로 납부방법 확인 및 녹취ID 입력을 통한 기기변경 신청이 가능합니다.");
										form.setItemLabel("smsAuthInfo", "녹취ID");
										form.hideItem("btnSendOtp");
										form.setItemValue("onlineAuthType", "B");
									}else{
										mvno.ui.alert("정지상태 고객으로 SMS수신 가능여부를 확인하세요");
										form.setItemLabel("smsAuthInfo", "SMS인증");
										form.showItem("btnSendOtp");
										form.setItemValue("onlineAuthType", "S");
									}
								}
// 								else{
// 									form.setItemLabel("smsAuthInfo", "SMS인증");
// 									form.showItem("btnSendOtp");
// 									form.setItemValue("onlineAuthType", "S");
// 								}
								// [SRM18120707112] KOS분실고객 기기변경 신청등록 시 SMS인증 Skip 및 녹취ID인증 메뉴 개발요청
								
								// 기변유형 선택
								if(result.dvcChgYn == "Y" && result.dvcChgTp != ""){
									form.setItemValue("operType", result.dvcChgTp);
									
									if(result.dvcChgTp == "HDN3"){
										form.setItemValue("dvcChgRsnCd", "20");
									}else{
										form.setItemValue("dvcChgRsnCd", "10");
									}
								}else{
									mvno.ui.alert("기기변경 불가 고객입니다.");
									return;
								}
								
								// 계약정보
								form.setItemValue("contractNum", result.contractNum);
								form.setItemValue("subStatus", result.subStatus);
								form.setItemValue("subStatusNm", result.subStatusNm);
								form.setItemValue("lstComActvDate", result.lstComActvDate);
								// 기변유형
								form.setItemValue("dvcChgYn", result.dvcChgYn);
								form.setItemValue("dvcChgNm", result.dvcChgNm);
								form.setItemValue("dvcChgTp", result.dvcChgTp);
								form.setItemValue("dvcChgTpNm", result.dvcChgTpNm);
								//form.setItemValue("unpdAmnt", result.unpdAmnt);
								form.setItemValue("reqUsimSn", result.iccId);	// 기존 유심 일련번호
								form.setItemValue("iccId", result.iccId);	// 기존 유심 일련번호
								// 고객휴대폰번호
								form.setItemValue("cstmrTelFn", result.cstmrMobileFn);
								form.setItemValue("cstmrTelMn", result.cstmrMobileMn);
								form.setItemValue("cstmrTelRn", result.cstmrMobileRn);
								
								form.setItemValue("cstmrName", result.customerLinkName);
								
								// 유심비납부방법 초기화
								form.setItemValue("usimPayMthdCd", "1");
								// 기기변경유형
								form.setItemValue("dvcChgType", "10");
							}
						});
						
						break;
					
					//SMS 인증
					case "btnSendOtp" :
						
						if(form.getItemValue("subscriberNo") == ""){
							mvno.ui.alert("CTN을 입력하세요");
							return;
						}
						
						if(form.getItemValue("contractNum") == ""){
							mvno.ui.alert("계약정보 조회후 인증요청 하세요");
							return;
						}
						
						var formData = {"subscriberNo":form.getItemValue("subscriberNo")};
						
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/sendSmsAuth.json", formData, function(resultData) {
							
							var result = resultData.result;
							
							form.setItemValue("otpNo", result.otpNo);
							
							form.hideItem("btnSendOtp");
							form.showItem("btnOtpConfirm");
							form.showItem("btnReSendOtp");
						});
						
						break;
						
					case "btnReSendOtp":
						
						if(form.getItemValue("subscriberNo") == ""){
							mvno.ui.alert("CTN을 입력하세요");
							return;
						}
						
						if(form.getItemValue("contractNum") == ""){
							mvno.ui.alert("계약정보 조회후 인증요청 하세요");
							return;
						}
						
						// 인증정보 초기화
						form.setItemValue("smsAuthInfo", "");
						form.setItemValue("otpCheckYN", "");
						
						var formData = {"subscriberNo":form.getItemValue("subscriberNo")};
						
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/sendSmsAuth.json", formData, function(resultData) {
							
							var result = resultData.result;
							
							form.setItemValue("otpNo", result.otpNo);
							
							form.hideItem("btnSendOtp");
							form.showItem("btnOtpConfirm");
							form.showItem("btnReSendOtp");
						});
						
						break;
						
					case "btnOtpConfirm":
						if(form.getItemValue("smsAuthInfo") == form.getItemValue("otpNo")){
							mvno.ui.alert("인증되었습니다.");
							form.setItemValue("otpCheckYN", "Y");
							form.hideItem("btnOtpConfirm");
							form.hideItem("btnReSendOtp");
						}else{
							mvno.ui.alert("인증이 실패하였습니다.");
							form.setItemValue("otpCheckYN", "N");
						}
						
						break;
					
					case "btnCopy" :
						
						var url = "/rcp/rcpMgmt/copyRcpMgmt.json";
						
						mvno.cmn.ajax4confirm("복사하시겠습니까?", ROOT + url, {"trgtRequestKey":form.getItemValue("requestKey")}, function(result) {

							var copyDoneMsg = "복사되었습니다.";
							if(result.result.isAcenTrg == "Y"){
								copyDoneMsg += "<br><br>[A'Cen 프로세스로 진행됩니다]";
							}

							mvno.ui.alert(copyDoneMsg);
							
							form.clear();
							form.setItemValue("requestKey", result.result.requestKey);
							form1ReadInit();
							
						}, {async:false, dimmed:true});
						
						break;
						
					case "btnNew" :
						mvno.ui.confirm("새로 작성 하시겠습니까?", function() {
							
							var url = "/rcp/rcpMgmt/getRcpNewMgmt.do";
							var menuId = "MSP1000014";
							
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
					/* case "btnPrmtFind" :
						if(mvno.cmn.isEmpty(form.getItemValue("salePlcyCd"))){
							mvno.ui.alert("판매정책 선택 후 가능합니다.");
							return;
						}
						
						var reqInDayFM = new Date(form.getItemValue("reqInDay")).format("yyyymmdd");
						
						var params = "?reqInDay="+reqInDayFM						// 신청일자 (신청서정보)
						+ "&operType="+form.getItemValue("operType")				// 신청유형 (신청서정보)
						+ "&onOffType="+form.getItemValue("onOffType")				// 모집경로
						+ "&orgnId="+form.getItemValue("cntpntShopId")				// 조직정보
						;
						
						if(!mvno.cmn.isEmpty(form.getItemValue("salePlcyCd"))){
							params = params + "&agrmTrm="+form.getItemValue("agrmTrm")	// 약정기간 (판매정보)
							+ "&rateCd="+form.getItemValue("socCode")					// 요금제 (판매정보)
						}
						
						mvno.ui.popupWindowTop(ROOT + "/rcp/rcpMgmt/getRcpPrmt.do" + params, "프로모션정책", 880, 600, {
							param : {
									callback : function(result) {
										form.setItemValue("promotionCd", result.promotionCd);
										form.setItemValue("promotionNm", result.promotionNm);
										form.setItemValue("benefitCmmt", result.benefitCmmt);
										form.setItemValue("prmtAgrmTrm", result.agrmTrm);
										form.setItemValue("prmtOperType", result.operType);
										form.setItemValue("prmtRateCd", result.rateCd);
									}
							}
						});
						
						break; */
						
					//v2018.02 기기변경 시 고객혜택 추가
					/* case "btnBnftFind":
						mvno.ui.popupWindowTop(ROOT + "/rcp/rcpNewMgmt/getRcpBnft.do?requestKey="+ form.getItemValue("requestKey"), "고객혜택", 380, 330,
							{
								param : {
									callback : function(result) {
										form.setItemValue("bnftName", result.bnftNm);
										form.setItemValue("bnftKey", result.bnftKey);
									}
								}
							}
						);
						
						break; */
				}
			}
		} /* End FORM1 */
		,FORM2 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80},
				{type: "fieldset", label: "녹취 파일 첨부",  inputWidth:500, offsetLeft:10, offsetTop:10, list:[
					{type: "block", list: [
							{type: "input", name: "fileName1", label: "파일 명",  width: 230, value: "", disabled:true, offsetLeft:20},
							{type: "hidden", name: "fileId1", label: "파일ID"},
							{type: "hidden", name: "strRequestKey", label: "REQUEST_KEY"},
							{type: "newcolumn"},
							{type:"button", name: "fileDown1", value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: "fileDel1", value:"삭제"},
					]},
					{type: "block", list: [
							{type: "input", name: "fileName2", label: "파일 명",  width: 230, value: "", disabled:true, offsetLeft:20},
							{type: "hidden", name: "fileId2", label: "파일ID"},
							{type: "newcolumn"},
							{type:"button", name: "fileDown2", value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: "fileDel2", value:"삭제"},
					]},
					{type: "block", list: [
							{type: "input", name: "fileName3", label: "파일 명",  width: 230, value: "", disabled:true, offsetLeft:20},
							{type: "hidden", name: "fileId3", label: "파일ID"},
							{type: "newcolumn"},
							{type:"button", name: "fileDown3", value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: "fileDel3", value:"삭제"},
					]},
					{type: "block", list: [
						{type: "input", name: "fileName4", label: "파일 명",  width: 230, value: "", disabled:true, offsetLeft:20},
							{type: "hidden", name: "fileId4", label: "파일ID"},
							{type: "newcolumn"},
							{type:"button", name: "fileDown4", value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: "fileDel4", value:"삭제"},
					]},
					{type: "block", list: [
							{type: "input", name: "fileName5", label: "파일 명",  width: 230, value: "", disabled:true, offsetLeft:20},
							{type: "hidden", name: "fileId5", label: "파일ID"},
							{type: "newcolumn"},
							{type:"button", name: "fileDown5", value:"다운로드"},
							{type: "newcolumn"},
							{type:"button", name: "fileDel5", value:"삭제"},
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
						 mvno.cmn.download("/org/common/downFile2.json?fileId=" + PAGE.FORM2.getItemValue("fileId1"));
						 break;
					case "fileDown2" :
						mvno.cmn.download("/org/common/downFile2.json?fileId=" + PAGE.FORM2.getItemValue("fileId2"));
						 break;
					case "fileDown3" :
						mvno.cmn.download("/org/common/downFile2.json?fileId=" + PAGE.FORM2.getItemValue("fileId3"));
						 break;
					case "fileDown4" :
						mvno.cmn.download("/org/common/downFile2.json?fileId=" + PAGE.FORM2.getItemValue("fileId4"));
						 break;
					case "fileDown5" :
						mvno.cmn.download("/org/common/downFile2.json?fileId=" + PAGE.FORM1.getItemValue("fileId5"));
						 break;
					case "fileDel1" :
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId1"),orgnId:strRequestKey,attSctn:"REC"} , function(resultData) {
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
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId2"),orgnId:strRequestKey,attSctn:"REC"} , function(resultData) {
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
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId3"),orgnId:strRequestKey,attSctn:"REC"} , function(resultData) {
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
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId4"),orgnId:strRequestKey,attSctn:"REC"} , function(resultData) {
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
						mvno.cmn.ajax(ROOT + "/org/common/deleteFile.json",  {fileId:PAGE.FORM2.getItemValue("fileId5"),orgnId:strRequestKey,attSctn:"REC"} , function(resultData) {
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
		} /* End FORM2 */
		
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
		
		modYn = false;
		
		if(typeCd != "10") {
			PAGE.FORM1.hideItem("AUTH");
			PAGE.FORM1.disableItem("prodType");
		}
		
		mvno.ui.showButton("FORM1", "btnSave");
		mvno.ui.hideButton("FORM1", "btnMod");
		
		// 약정기간 콤보
		mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0053"}, PAGE.FORM1, "agrmTrm");
		
		//서식지 등록 스캔 타입 변경
		PAGE.FORM1.setItemValue("scanType","new");
		
		// 등록시 숨길 메뉴 설정
		hideItems = [
						"CARD"			// 카드정보
						,"BANK"			// 은행정보
						,"FOREIGNER"	// 외국인정보
						,"BUSINESS"		// 사업자정보
						,"MINORINFO"		// 법정대리인
						,"REQMOVE"		// 번호이동정보
						,"DVCCHG"		// 기기변경정보
						,"DLVRY"		// 배송정보
						,"faxnum"		// 팩스(신청서정보)
						,"dlvryType"
						,"btnPaper"		// 서식지보기(신청서정보)
						,"RENTAL"		// 렌탈서비스
						,"DVCREG"		// 기변대상조회
						,"BNFT"			// v2018.02 기기변경 시 고객혜택 추가
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
		
		PAGE.FORM1.hideItem("smsAuthInfo");		// SMS인증
		PAGE.FORM1.hideItem("btnSendOtp");		// 인증요청
		PAGE.FORM1.hideItem("btnOtpConfirm");	// 인증확인
		PAGE.FORM1.hideItem("btnReSendOtp");	// 재전송
		PAGE.FORM1.hideItem("usePointAmt");     // 포인트사용금액 hide 2022.02.21
		
		var enableItems = new Array();
		var disableItems = new Array();
		// 신규가입 20231229
		enableItems.push(
						// 신청서 정보
						"reqBuyType","operType","pstate","requestStateCode","reqInDay","shopNm",
						"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnRecSarch","btnFaxSarch","btnPaper",
						// 판매 정보
						"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","prdtCode","hndstAmt","modelInstallment","modelDiscount2","modelDiscount3",
						"dcAmt","addDcAmt","reqModelColor","reqPhoneSn","reqUsimSn","usimPayMthdCd","spcCode","requestMemo",
						// 고객혜택
						"bnftName", "btnBnftFind",
						// 고객 정보
						"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","selfInqryAgrmYnLabel","selfInqryAgrmYn","selfCertType","selfIssuExprDt","selfIssuNum","onlineAuthType",
						"cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag",
						"cstmrBillSendCode",
						"cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl","cstmrTelFn","cstmrTelMn","cstmrTelRn","cstmrMobileFn","cstmrMobileMn","cstmrMobileRn", "btnCheckCstmr",
						// 배송 정보
						"dlvryName","cloneBase","dlvryTelFn","dlvryTelMn","dlvryTelRn","dlvryMobileFn","dlvryMobileMn","dlvryMobileRn","dlvryAddr","dlvryPost","dlvryAddrDtl","btnDlvryPostFind","dlvryMemo",
						// 외국인 정보
						"cstmrForeignerNationSel","btnNatnFind","cstmrForeignerNationTxt","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2","visaCd",
						// 개인사업자 정보
						"cstmrPrivateCname","cstmrPrivateNumber1","cstmrPrivateNumber2","cstmrPrivateNumber3",
						// 법인사업자
						"cstmrJuridicalCname","cstmrJuridicalNumber1","cstmrJuridicalNumber2","cstmrJuridicalNumber3","cstmrJuridicalRrn1","cstmrJuridicalRrn2",
						// 요금납부 정보
						"reqPayType",
						// 카드 정보
						"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2","cloneBaseCard",
						// 은행 정보
						"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2","cloneBaseBank",
						// 이용신청내용
						"additionName","btnRateFind","phonePaymentLabel","phonePayment","lessData","reqWireType1","reqWireType2","reqWireType3","joinPayMthdCd","combineSoloType","combineSoloTypeLabel",
						// 신규가입 정보
						"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3","reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
						// 번호이동 정보
						"moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","movePenalty", // 20231229 신규 등록 화면 인증항목 코드, 인증 처리코드 삭제
						// 기기변경 정보
						"dvcChgType","dvcChgRsnCd","dvcChgRsnDtlCd","dvcChgRecId",
						// 법정대리인 정보
						"minorAgentName","minorAgentRelation","minorAgentRrn1","minorAgentRrn2","minorAgentTelFn","minorAgentTelMn","minorAgentTelRn","minorSelfInqryAgrmYn","minorSelfCertType","minorSelfIssuExprDt","minorSelfIssuNum",
						// 약관동의 정보
						"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd", "personalAgreeTitle", "othersAgreeTitle", "personalInfoCollectAgree","othersTrnsAgree","othersTrnsKtAgree","othersAdReceiveAgree","clauseSensiCollectFlag","clauseSensiOfferFlag", "clauseJehuFlag", "clausePartnerOfferFlag"
						);
		
		disableItems.push(
						"pstate","requestStateCode","cstmrMail2","reqInDay","onlineAuthInfo", "onOffType", //"onlineAuthType",
						"resNo","managerCode","clausePriCollectFlag","jehuProdType",
						"clausePriOfferFlag","clauseEssCollectFlag","moveMobileFn"
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
		
		// N-STEP 전송 버튼 숨김
		mvno.ui.hideButton("FORM1" , "btnSend");
		// 신청서 복사 버튼 숨김
		mvno.ui.hideButton("FORM1" , "btnCopy");

		//알뜰폰허브 관련 숨김
		PAGE.FORM1.hideItem("hubOrderNum");
		PAGE.FORM1.hideItem("btnHubOrder");


		PAGE.FORM1.setItemValue("reqBuyType","MM");					// 구매유형
		PAGE.FORM1.setItemValue("serviceType","PO");				// 후불만 등록 (선불은 MCP에서)
		form1Change(PAGE.FORM1, 0, "init");							// 초기 설정
		
		var reqInDay = new Date().format("yyyymmdd");
		PAGE.FORM1.setItemValue("reqInDay", reqInDay);				// 신청일자
		PAGE.FORM1.setItemValue("onlineAuthType", "P");				// 본인인증방식
		PAGE.FORM1.setItemValue("onOffType","1");					// 모집경로
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
		
		//모집경로에 따른 화면 구성
		form1Change(PAGE.FORM1, 0, "onOffType");
		
		// 요금납부정보
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2015"}, PAGE.FORM1, "reqPayType");
		
		PAGE.FORM1.hideItem("selfIssuNum");		// 발급번호

		// 렌탈정보
		PAGE.FORM1.hideItem("rntlPrdtMdl");
		PAGE.FORM1.hideItem("rentalModelCpAmt");
		PAGE.FORM1.hideItem("rentalBaseAmt");
		PAGE.FORM1.hideItem("rentalBaseDcAmt");
		PAGE.FORM1.hideItem("reqModelName");
		
		
		// 오프라인일때만 배송정보 체크박스 보이도록 수정 20170613 박준성
		var onOffType = PAGE.FORM1.getItemValue("onOffType");
		
		if(onOffType == "1") {
			PAGE.FORM1.showItem("dlvryYn");
		} else {
			PAGE.FORM1.hideItem("dlvryYn");
		}
		
		//v2017.08 프로모션 정책 등록 본사조직만 가능
		if(typeCd != "10"){
			PAGE.FORM1.hideItem("PRMTREG");
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
		
		modYn = true;
		
		mvno.ui.showButton("FORM1" , "btnSend");
		mvno.ui.hideButton("FORM1" , "btnSave");
		//유심종류 esim 추가에 따른 레이아웃 변경
		
		
		//도매대리점 또는 엠모바일 조직인 경우만 신청서 복사 가능
		if(typeDtlCd2 == "CS4" || orgId == "1"){
			mvno.ui.showButton("FORM1", "btnCopy");
		}else{
			mvno.ui.hideButton("FORM1", "btnCopy");
		}

		// 본사 사용자 경우 렌탈 활성화
		if(typeCd != "10") {
			PAGE.FORM1.disableItem("prodType");
		}
		
		// 콤보세팅
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0053"}, PAGE.FORM1, "agrmTrm"); // 약정기간
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2005"}, PAGE.FORM1, "instNom"); // 할부기간
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0058"}, PAGE.FORM1, "sprtTp"); // 할인유형
		
		var rowData = "";
		var strRequestKey = PAGE.FORM1.getItemValue("requestKey");
		
		// 조회
		mvno.cmn.ajax2(ROOT + "/rcp/rcpMgmt/rcpListAjax.json", {requestKey:strRequestKey}, function(resultData) {
			rowData = resultData.result.data;
			
			//20250715 데이터쉐어링 요금제일 경우 체크
			sharingYn = resultData.result.sharingYn;

			// SRM18072675707, 단체보험체크
			if(mvno.cmn.isEmpty(rowData.insrCd) && grpInsrYn != "Y") {
				grpInsrYn = "N";
			} else {
				grpInsrYn = "Y";
			}
			console.log("grpInsrYn=" + grpInsrYn);
			
			var strReqInDay = rowData.reqInDay.replace(/-/g, "");
			mvno.cmn.ajax4lov( ROOT + "/ptnr/grpinsrmgmt/getGrpInsrCombo.json", {reqInDay:strReqInDay}, PAGE.FORM1, "insrCd");
			
			// 초기 설정
			form1Change(PAGE.FORM1, 0, "init");
			
			// N-STEP 전송 버튼 Hide/Show
			// 하단으로 이동
			/* if(rowData.requestStateCode == "21") {
				mvno.ui.hideButton("FORM1" , "btnSend");
			} */
			
			PAGE.FORM1.setFormData(rowData);
			
			
			var aryAgent = rowData.agentName.split("/");
			
			PAGE.FORM1.setItemValue("pAgentCode", rowData.agentCode);
			PAGE.FORM1.setItemValue("pAgentName", aryAgent[0]);
			PAGE.FORM1.setItemValue("hubOrderNum", rowData.hubOrderSeq);
			form1Change(PAGE.FORM1, 0, "pAgentCode");
			// 대리점 change 이벤트 호출에 따라 초기화되는 값 재세팅
			PAGE.FORM1.setItemValue("clausePartnerOfferFlag", rowData.clausePartnerOfferFlag);

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

			// 조회시 숨길 필드 설정
			hideItems = [
							"CARD"			// 카드정보
							,"BANK"			// 은행정보
							,"FOREIGNER"	// 외국인정보
							,"BUSINESS"		// 사업자정보
							,"MINORINFO"		// 법정대리인
							,"REQMOVE"		// 번호이동정보
							,"NEWREQ"		// 신규가입
							,"DVCCHG"		// 기기변경정보
							,"DVCREG"		// 기변대상조회
							,"BNFT"			// v2018.02 기기변경 시 고객혜택 추가
							,"RENTAL"		// 렌탈
						];
			
			if(rowData.operType == "HCN3" || rowData.operType == "HDN3") {
				if(PAGE.FORM1.getItemValue("fathUseYn") != "Y"){
					hideItems.push("CSTMRINFO");
				}
				hideItems.push("PAYINFO");
				hideItems.push("REQADD");
			}
			
			if(rowData.reqBuyType == "MM") hideItems.push("dlvryType");
			
			for(var inx=0; inx < hideItems.length; inx++){
				PAGE.FORM1.hideItem(hideItems[inx]);
			}
			
			showItems = [
							"faxnum",		// 팩스(신청서정보)
							"btnPaper",		// 서식지보기(신청서정보)
							"DLVRY",		// 배송정보
							//"RENTAL",		// 렌탈
							"AUTH"			// 본인인증
						];
			
			// 상품구분 "렌탈" 인 경우
			if(rowData.prodType == "02") showItems.push("RENTAL");
			
			// 상품구분 "자급제" 인 경우
			if(rowData.prodType == "04") {
				PAGE.FORM1.showItem("SELPHONE");// 자급제 정보 표시
			} else {
				PAGE.FORM1.hideItem("SELPHONE");// 자급제 정보 숨기기
			}
			
			for(var inx=0; inx < showItems.length; inx++){
				PAGE.FORM1.showItem(showItems[inx]);
			}
			
			PAGE.FORM1.hideItem("smsAuthInfo");		// SMS인증
			PAGE.FORM1.hideItem("btnSendOtp");		// 인증요청
			PAGE.FORM1.hideItem("btnOtpConfirm");	// 인증확인
			PAGE.FORM1.hideItem("btnReSendOtp");	// 재전송
			
			PAGE.FORM1.showItem("dvcChgType");		// 기변유형
			PAGE.FORM1.showItem("dvcChgRsnCd");		// 기변사유
			PAGE.FORM1.showItem("dvcChgRecId");		// 기변녹취ID
			PAGE.FORM1.showItem("usePointAmt");     // 포인트사용금액 show 2022.02.21 
			
			// 개통이력확인
			if(PAGE.FORM1.getItemValue("requestStateCode") != "00"){
				PAGE.FORM1.hideItem("btnCheckCstmr");
			}
			
			// 메뉴 숨김/보임 설정 END
			//---------------------------------------------------------------------------------------------------------

			//---------------------------------------------------------------------------------------------------------
			// 항목별 Disable/Enable 처리 START

			var enableItems = new Array();
			var disableItems = new Array(); //20231229 추가

			enableItems.push(
							// 신청서 정보
							"reqBuyType","operType","pstate","requestStateCode","reqInDay","shopNm",
							"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnRecSarch","btnFaxSarch","btnPaper",
							// 판매 정보
							"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","prdtCode","hndstAmt","modelInstallment","modelDiscount2","modelDiscount3",
							"dcAmt","addDcAmt","reqModelColor","reqPhoneSn","reqUsimSn","usimPayMthdCd","spcCode","requestMemo",
							// 이용신청내용
							"additionName","btnRateFind","phonePaymentLabel","phonePayment","lessData","reqWireType1","reqWireType2","reqWireType3","joinPayMthdCd","combineSoloType","combineSoloTypeLabel",
							// 프로모션
							"promotionCd","promotionNm","btnPrmtFind","benefitCmmt"
							// 고객혜택
							,"bnftName", "btnBnftFind",
							// 고객 정보
							"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","selfInqryAgrmYnLabel","selfInqryAgrmYn","selfCertType","selfIssuExprDt","selfIssuNum",
							"cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl","cstmrTelFn","cstmrTelMn","cstmrTelRn","cstmrMobileFn","cstmrMobileMn","cstmrMobileRn",
							// 배송 정보
							"dlvryName","cloneBase","dlvryTelFn","dlvryTelMn","dlvryTelRn","dlvryMobileFn","dlvryMobileMn","dlvryMobileRn","dlvryAddr","dlvryPost","dlvryAddrDtl","btnDlvryPostFind","dlvryMemo",
							// 외국인 정보
							"cstmrForeignerNationSel","btnNatnFind","cstmrForeignerNationTxt","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2","visaCd",
							// 개인사업자
							"cstmrPrivateCname","cstmrPrivateNumber1","cstmrPrivateNumber2","cstmrPrivateNumber3",
							// 법인사업자
							"cstmrJuridicalCname","cstmrJuridicalNumber1","cstmrJuridicalNumber2","cstmrJuridicalNumber3","cstmrJuridicalRrn1","cstmrJuridicalRrn2",
							// 요금납부 정보
							"reqPayType",
							// 카드 정보
							"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2","cloneBaseCard",
							// 은행 정보
							"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2","cloneBaseBank",
							// 신규가입 정보
							"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3","reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
							// 번호이동 정보
							"moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","moveAuthNumber","moveAuthType","movePenalty",
							// 기기변경 정보
							"dvcChgType","dvcChgRsnCd","dvcChgRsnDtlCd","dvcChgRecId",
							// 법정대리인 정보
							"minorAgentName","minorAgentRelation","minorAgentRrn1","minorAgentRrn2","minorAgentTelFn","minorAgentTelMn","minorAgentTelRn","minorSelfInqryAgrmYn","minorSelfCertType","minorSelfIssuExprDt","minorSelfIssuNum",
							// 약관동의 정보
							"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd", "personalAgreeTitle", "othersAgreeTitle","personalInfoCollectAgree","othersTrnsAgree","othersTrnsKtAgree","othersAdReceiveAgree","clauseSensiCollectFlag","clauseSensiOfferFlag"
							// SRM18072675707, 단체보험
							, "insrCd"
							// 단말보험
							, "insrProdCd", "clauseInsrProdFlag"
							);
			disableItems.push(
							"moveAuthNumber","moveAuthType", "hubOrderNum", "btnHubOrder"
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

			// 신청유형에 따른 화면 구성(신규가입/번호이동정보/기기변경정보)
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
			
			//20220805 신청서 읽어올때 유심종류가 esim 이 아닌 경우에는 esim으로 변경 못함
			if(rowData.usimKindsCd != "09"){
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2035" , etc1:"1"}, PAGE.FORM1, "usimKindsCd"); // 유심종류
				PAGE.FORM1.setItemValue("usimKindsCd", rowData.usimKindsCd );
			}else {
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2035"}, PAGE.FORM1, "usimKindsCd"); // 유심종류
				PAGE.FORM1.setItemValue("usimKindsCd", rowData.usimKindsCd );
				//유심종류
				form1Change(PAGE.FORM1, 0, "usimKindsCd");
			}
						
			// 구매유형/신청유형/모집경로 에 따라 단말모델/요금제 값이 자동변경되기 때문에
			// 이곳에서 재설정 필요
			PAGE.FORM1.setItemValue("reqBuyType", rowData.reqBuyType);			// 구매유형
			PAGE.FORM1.setItemValue("operType", rowData.operType);				// 신규유형
			PAGE.FORM1.setItemValue("onOffType", rowData.onOffType);			// 모집경로
			PAGE.FORM1.setItemValue("mnfctId", rowData.mnfctId);				// 제조사
			PAGE.FORM1.setItemValue("prdtId", rowData.prdtId);					// 단말모델
			PAGE.FORM1.setItemValue("socCode", rowData.socCode);				// 요금제
			
			var onOffType = PAGE.FORM1.getItemValue("onOffType");

			if(onOffType == "0" || onOffType == "3" || onOffType == "6" || onOffType == "8"){
						PAGE.FORM1.showItem("ktInterSvcNo");
			}else{
						PAGE.FORM1.hideItem("ktInterSvcNo");
			}
			
			// 모델색상
			form1Change(PAGE.FORM1, 0, "prdtId");
			PAGE.FORM1.setItemValue("reqModelColor", rowData.reqModelColor);	// 단말모델색상

			if(rowData.reqBuyType == "UU"){
				PAGE.FORM1.setItemValue("prdtCode", rowData.reqUsimName);			// USIM모델코드
								
				if(rowData.onOffType == "0" || rowData.onOffType == "3"){
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
			
			// 2016.06.30 추가
			PAGE.FORM1.setItemValue("cntpntShopId", rowData.cntpntShopId);
			PAGE.FORM1.setItemValue("mnfctId", rowData.mnf);
			PAGE.FORM1.setItemValue("modelId", rowData.modelId);
			PAGE.FORM1.setItemValue("reqModelName", rowData.reqModelName);
			PAGE.FORM1.setItemValue("reqUsimName", rowData.reqUsimName);
			PAGE.FORM1.setItemValue("recycleYn", rowData.recycleYn);
			PAGE.FORM1.setItemValue("agrmTrm", rowData.agrmTrm);
			PAGE.FORM1.setItemValue("instNom", rowData.instNom);
			PAGE.FORM1.setItemValue("modelPrice", rowData.modelPrice);
			PAGE.FORM1.setItemValue("modelPriceVat", rowData.modelPriceVat);
			PAGE.FORM1.setItemValue("hndstAmt", rowData.hndstAmt);
			PAGE.FORM1.setItemValue("modelDiscount2", rowData.modelDiscount2);
			PAGE.FORM1.setItemValue("modelDiscount3", rowData.modelDiscount3);
			PAGE.FORM1.setItemValue("sprtTp", rowData.sprtTp);
			PAGE.FORM1.setItemValue("dcAmt", rowData.dcAmt);
			PAGE.FORM1.setItemValue("addDcAmt", rowData.addDcAmt);
			PAGE.FORM1.setItemValue("joinPrice", rowData.joinPrice);
			PAGE.FORM1.setItemValue("usimPrice", rowData.usimPrice);
			PAGE.FORM1.setItemValue("modelInstallment", rowData.modelInstallment);	// 할부원금
			// 추가 값 설정 END
			//---------------------------------------------------------------------------------------------------------
			
			// Disable 처리
			
			var disableItems = new Array();
			
			// 접수 = 00:접수대기,01:접수,02:해피콜,09:배송대기,10:배송중,20:개통대기,21:개통완료
			if(rowData.requestStateCode == "21"){
				disableItems.push(
								// 신청서 정보
								"reqBuyType","operType","pstate","requestStateCode","reqInDay","shopNm","prodType","btnShopFind",
								"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnFaxSarch",
								// 판매 정보
								"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","prdtCode","hndstAmt","modelInstallment","modelDiscount2","modelDiscount3",
								"dcAmt","addDcAmt","reqModelColor","reqPhoneSn","reqUsimSn","usimPayMthdCd","spcCode","requestMemo",
								// 이용신청내용
								"additionName","btnRateFind","phonePaymentLabel","phonePayment","lessData","reqWireType1","reqWireType2","reqWireType3","joinPayMthdCd","combineSoloType","combineSoloTypeLabel",
								// 프로모션
								"promotionCd","promotionNm","btnPrmtFind","benefitCmmt",
								// 고객혜택
								"bnftName", "btnBnftFind",
								// 고객 정보
								"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl","cstmrTelFn","cstmrTelMn","cstmrTelRn","cstmrMobileFn","cstmrMobileMn","cstmrMobileRn",
								"selfInqryAgrmYnLabel","selfInqryAgrmYn","selfCertType","selfIssuExprDt","selfIssuNum",
								// 배송 정보
								"dlvryYn","dlvryName","cloneBase","dlvryTelFn","dlvryTelMn","dlvryTelRn","dlvryMobileFn","dlvryMobileMn","dlvryMobileRn","dlvryAddr","dlvryPost","dlvryAddrDtl","btnDlvryPostFind","dlvryMemo",
								// 외국인 정보
								"cstmrForeignerNationSel","btnNatnFind","cstmrForeignerNationTxt","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2","visaCd",
								// 개인사업자 정보
								"cstmrPrivateCname","cstmrPrivateNumber1","cstmrPrivateNumber2","cstmrPrivateNumber3",
								// 법인사업자
								"cstmrJuridicalCname","cstmrJuridicalNumber1","cstmrJuridicalNumber2","cstmrJuridicalNumber3","cstmrJuridicalRrn1","cstmrJuridicalRrn2",
								// 요금납부 정보
								"reqPayType",
								// 카드 정보
								"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2","cloneBaseCard",
								// 은행 정보
								"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2","cloneBaseBank",
								// 신규가입 정보
								"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3","reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
								// 번호이동 정보
								"moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","moveAuthNumber","moveAuthType","movePenalty",
								// 기기변경 정보
								"dvcChgType","dvcChgRsnCd","dvcChgRsnDtlCd","dvcChgRecId",
								// 법정대리인 정보
								"minorAgentName","minorAgentRelation","minorAgentRrn1","minorAgentRrn2","minorAgentTelFn","minorAgentTelMn","minorAgentTelRn","minorSelfInqryAgrmYn","minorSelfCertType","minorSelfIssuExprDt","minorSelfIssuNum",
								// 약관동의 정보
								"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd","clauseJehuFlag","clauseFinanceFlag","personalAgreeTitle","othersAgreeTitle","personalInfoCollectAgree","othersTrnsAgree","othersTrnsKtAgree","othersAdReceiveAgree","clauseSensiCollectFlag","clauseSensiOfferFlag", "clausePartnerOfferFlag", "jehuProdType"
								// 렌탈서비스정보
								,"clauseRentalService","clauseRentalModelCp","clauseRentalModelCpPr","rentalBaseAmt","rentalBaseDcAmt","rentalModelCpAmt"
								// SRM18072675707, 단체보험
								, "insrCd", "clauseInsuranceFlag"
								// 단말보험
								, "insrProdCd", "clauseInsrProdFlag"
								// 추천인 정보
								, "recommendFlagCd", "recommendInfo"
								//KT 인터넷ID
								, "ktInterSvcNo"
								// 자급제
								, "phoneStateCd", "lastAmt", "sesplsPrdtNm", "sesplsProdId", "sesplsProdCol", "hndsetSalePrice", "cardTotDcAmt", "usePoint", "subscriberNo"
								// 20220914 esim관련 단말기 정보 
								, "intmMdlId", "intmSrlNo"
								);
			}
			else {
				// 모집유형 = 0:온라인,1:오프라인,2:홈쇼핑(TM)
				// 온라인/홈쇼핑 경우
				if(rowData.onOffType != "1"){
					if(rowData.requestStateCode == "00"){
							disableItems.push(
								// 신청서 정보
								"reqBuyType","operType","reqInDay","prodType","btnShopFind","shopNm",
								"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnFaxSarch",
								// 판매 정보
								// "reqModelColor", 온라인에서 신청한 색상을 변경가능하게 함 by 2015-09-10
								"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","prdtCode","hndstAmt","modelInstallment","modelDiscount2","modelDiscount3",
								"dcAmt","addDcAmt","usimPayMthdCd",
								// 이용신청내용
								"additionName","btnRateFind","phonePaymentLabel","phonePayment","lessData","reqWireType1","reqWireType2","reqWireType3","joinPayMthdCd","combineSoloType","combineSoloTypeLabel",
								// 고객 정보
								"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl",
								// 외국인 정보
								"cstmrForeignerNationSel","btnNatnFind","cstmrForeignerNationTxt","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2","visaCd",
								// 개인사업자 정보
								"cstmrPrivateCname","cstmrPrivateNumber1","cstmrPrivateNumber2","cstmrPrivateNumber3",
								// 법인사업자
								"cstmrJuridicalCname","cstmrJuridicalNumber1","cstmrJuridicalNumber2","cstmrJuridicalNumber3","cstmrJuridicalRrn1","cstmrJuridicalRrn2",
								// 요금납부 정보
								"reqPayType",
								// 카드 정보
								"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2","cloneBaseCard",
								// 은행 정보
								"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2","cloneBaseBank",
								// 신규가입 정보
								"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3","reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
								// 기기변경 정보
								"dvcChgType","dvcChgRsnCd","dvcChgRsnDtlCd","dvcChgRecId",
								// 약관동의 정보
								"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd","clauseJehuFlag","clauseFinanceFlag","personalAgreeTitle","othersAgreeTitle","personalInfoCollectAgree","othersTrnsAgree","othersTrnsKtAgree","othersAdReceiveAgree","clauseSensiCollectFlag","clauseSensiOfferFlag", "clausePartnerOfferFlag", "jehuProdType"
								// 렌탈서비스 (온라인신청만 접수)
								,"clauseRentalService","clauseRentalModelCp","clauseRentalModelCpPr","rentalBaseAmt","rentalBaseDcAmt","rentalModelCpAmt"
								// 단말보험
								, "insrProdCd", "clauseInsrProdFlag"
								// 자급제
								, "phoneStateCd", "lastAmt", "sesplsPrdtNm", "sesplsProdId", "sesplsProdCol", "hndsetSalePrice", "cardTotDcAmt", "usePoint", "subscriberNo"
							);
					}
					else if(rowData.requestStateCode == "01"){
							// 접수상태
							disableItems.push(
								// 신청서 정보
								"reqBuyType","operType","reqInDay","prodType","btnShopFind","shopNm",
								"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnFaxSarch",
								// 판매 정보
								"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","prdtCode","hndstAmt","modelInstallment","modelDiscount2","modelDiscount3",
								"dcAmt","addDcAmt","usimPayMthdCd",
								// 이용신청내용
								"additionName","btnRateFind","phonePaymentLabel","phonePayment","lessData","reqWireType1","reqWireType2","reqWireType3","joinPayMthdCd","combineSoloType","combineSoloTypeLabel",
								// 고객 정보
								"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl",
								// 외국인 정보
								"cstmrForeignerNationSel","btnNatnFind","cstmrForeignerNationTxt","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2","visaCd",
								// 사업자 정보
								"cstmrPrivateCname","cstmrPrivateNumber1","cstmrPrivateNumber2","cstmrPrivateNumber3",
								// 법인사업자
								"cstmrJuridicalCname","cstmrJuridicalNumber1","cstmrJuridicalNumber2","cstmrJuridicalNumber3","cstmrJuridicalRrn1","cstmrJuridicalRrn2",
								// 요금납부 정보
								"reqPayType",
								// 카드 정보
								"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2","cloneBaseCard",
								// 은행 정보
								"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2","cloneBaseBank",
								// 신규가입 정보
								"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3","reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
								// 번호이동 정보
								"moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","moveAuthNumber","moveAuthType",
								// 기기변경 정보
								"dvcChgType","dvcChgRsnCd","dvcChgRsnDtlCd","dvcChgRecId",
								// 법정대리인 정보
								"minorAgentName","minorAgentRelation","minorAgentRrn1","minorAgentRrn2","minorAgentTelFn","minorAgentTelMn","minorAgentTelRn","minorSelfInqryAgrmYn","minorSelfCertType","minorSelfIssuExprDt","minorSelfIssuNum",
								// 약관동의 정보
								"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd","clauseJehuFlag","clauseFinanceFlag","personalAgreeTitle","othersAgreeTitle","personalInfoCollectAgree","othersTrnsAgree","othersTrnsKtAgree","othersAdReceiveAgree","clauseSensiCollectFlag","clauseSensiOfferFlag", "clausePartnerOfferFlag", "jehuProdType"
								// 렌탈서비스
								,"clauseRentalService","clauseRentalModelCp","clauseRentalModelCpPr","rentalBaseAmt","rentalBaseDcAmt","rentalModelCpAmt"
								// 단말보험
								, "insrProdCd", "clauseInsrProdFlag"
								// 자급제
								, "phoneStateCd", "lastAmt", "sesplsPrdtNm", "sesplsProdId", "sesplsProdCol", "hndsetSalePrice", "cardTotDcAmt", "usePoint", "subscriberNo"
							);
					
					}
					else {
							disableItems.push(
								// 신청서 정보
								"reqBuyType","operType","reqInDay","prodType","btnShopFind","shopNm",
								"onOffType","onlineAuthType","onlineAuthInfo","managerCode","resNo","faxyn","faxnum","faxnum1","faxnum2","faxnum3","btnRegPaper","btnScnSarch","btnFaxSarch",
								// 판매 정보
								"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","prdtCode","hndstAmt","modelInstallment","modelDiscount2","modelDiscount3",
								"dcAmt","addDcAmt","reqModelColor","usimPayMthdCd",
								// 이용신청내용
								"additionName","btnRateFind","phonePaymentLabel","phonePayment","lessData","reqWireType1","reqWireType2","reqWireType3","joinPayMthdCd","combineSoloType","combineSoloTypeLabel",
								// 고객 정보
								"cstmrType","cstmrName","cstmrNativeRrn1","cstmrNativeRrn2","cstmrMail1","cstmrMail2","cstmrMail3","cstmrMailReceiveFlagLabel","cstmrMailReceiveFlag","cstmrBillSendCode","cstmrPost","btnCstmrPostFind","cstmrAddr","cstmrAddrDtl",
								// 외국인 정보
								"cstmrForeignerNationSel","btnNatnFind","cstmrForeignerNationTxt","cstmrForeignerPn","cstmrForeignerSdate","cstmrForeignerEdate","cstmrForeignerDod","cstmrForeignerBirth","cstmrForeignerRrn1","cstmrForeignerRrn2","visaCd",
								// 개인사업자 정보
								"cstmrPrivateCname","cstmrPrivateNumber1","cstmrPrivateNumber2","cstmrPrivateNumber3",
								// 법인사업자
								"cstmrJuridicalCname","cstmrJuridicalNumber1","cstmrJuridicalNumber2","cstmrJuridicalNumber3","cstmrJuridicalRrn1","cstmrJuridicalRrn2",
								// 요금납부 정보
								"reqPayType",
								// 카드 정보
								"reqCardCompany","reqCardNo1","reqCardNo2","reqCardNo3","reqCardNo4","reqCardMm","reqCardYy","reqCardName","reqPayOtherFlag","reqCardRrn1","reqCardRrn2","cloneBaseCard",
								// 은행 정보
								"reqBank","reqAccountNumber","reqAccountName","othersPaymentAg","reqAccountRrn1","reqAccountRrn2","cloneBaseBank",
								// 신규가입 정보
								"reqWantNumberLabel","reqWantNumber","reqWantNumber2","reqWantNumber3","reqGuideFlag","reqGuideFn","reqGuideMn","reqGuideRn",
								// 번호이동 정보
								"moveMobileFn","moveMobileMn","moveMobileRn","moveCompany","moveThismonthPayType","moveAllotmentStat","moveRefundAgreeFlagLabel","moveRefundAgreeFlag","moveAuthNumber","moveAuthType",
								// 기기변경 정보
								"dvcChgType","dvcChgRsnCd","dvcChgRsnDtlCd","dvcChgRecId",
								// 법정대리인 정보
								"minorAgentName","minorAgentRelation","minorAgentRrn1","minorAgentRrn2","minorAgentTelFn","minorAgentTelMn","minorAgentTelRn","minorSelfInqryAgrmYn","minorSelfCertType","minorSelfIssuExprDt","minorSelfIssuNum",
								// 약관동의 정보
								"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag","clausePriAdFlag","nwBlckAgrmYn","appBlckAgrmYn","appCd","clauseJehuFlag","clauseFinanceFlag","personalAgreeTitle","othersAgreeTitle","personalInfoCollectAgree","othersTrnsAgree","othersTrnsKtAgree","othersAdReceiveAgree","clauseSensiCollectFlag","clauseSensiOfferFlag", "clausePartnerOfferFlag", "jehuProdType"
								// 렌탈서비스
								,"clauseRentalService","clauseRentalModelCp","clauseRentalModelCpPr","rentalBaseAmt","rentalBaseDcAmt","rentalModelCpAmt"
								// SRM18072675707, 단체보험
								, "insrCd", "clauseInsuranceFlag"
								// 단말보험
								, "insrProdCd", "clauseInsrProdFlag"
								// 자급제
								, "phoneStateCd", "lastAmt", "sesplsPrdtNm", "sesplsProdId", "sesplsProdCol", "hndsetSalePrice", "cardTotDcAmt", "usePoint", "subscriberNo"
							);
					}
				}
				else {
					// 오프라인 신청
					disableItems.push(
								// 신청서 정보
								"operType","reqInDay","onlineAuthType","onlineAuthInfo","onOffType","resNo","managerCode","prodType",
								// 판매 정보
								"mnfctId","prdtId","socCode","sprtTp","agrmTrm","instNom","prdtCode","modelDiscount3",
								// 고객정보
								"cstmrMail2",
								// 약관동의 정보
								"clausePriCollectFlag","clausePriOfferFlag","clauseEssCollectFlag", "clauseJehuFlag", "clausePartnerOfferFlag", "jehuProdType",
								// 렌탈서비스
								"clauseRentalService","clauseRentalModelCp","clauseRentalModelCpPr","rentalBaseAmt","rentalBaseDcAmt","rentalModelCpAmt"
							);
					
				}
			}
			
			// Disable 처리
			for(var inx=0; inx < disableItems.length; inx++){
				PAGE.FORM1.disableItem(disableItems[inx]);
			}
			
			var reqStatCd = PAGE.FORM1.getItemValue("requestStateCode");
			
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
			
			// 08:배송대기(소화물),09:배송대기(택배),10:배송중,20:개통대기,21:개통완료
			if ( reqStatCd == "08" || reqStatCd == "09" || reqStatCd == "10" || reqStatCd == "20" || reqStatCd == "21" ) {
				PAGE.FORM1.disableItem("usimKindsCd");
				PAGE.FORM1.disableItem("eid");
				PAGE.FORM1.disableItem("imei1");
				PAGE.FORM1.disableItem("imei2");
				PAGE.FORM1.disableItem("intmMdlId");
				PAGE.FORM1.disableItem("intmSrlNo");
				PAGE.FORM1.disableItem("eReqModelName");
				
				PAGE.FORM1.disableItem("prntsCtnFn");
				PAGE.FORM1.disableItem("prntsCtnMn");
				PAGE.FORM1.disableItem("prntsCtnRn");
				PAGE.FORM1.disableItem("billAcntNo");
				PAGE.FORM1.disableItem("prntsContractNo");
				
			// 00:접수대기,01:접수,02:해피콜,03:신청서배송,04:신청서배송완료
			} else {
				
				if(usimKindsCd == "09"){ //유심종류가 esim 인 경우에는 유심종류는 수정 불가
					PAGE.FORM1.disableItem("usimKindsCd");
					PAGE.FORM1.enableItem("eid");
					PAGE.FORM1.enableItem("imei1");
					PAGE.FORM1.enableItem("imei2");
					PAGE.FORM1.enableItem("intmMdlId");
					PAGE.FORM1.enableItem("intmSrlNo");
					PAGE.FORM1.disableItem("eReqModelName");
					
					PAGE.FORM1.enableItem("prntsCtnFn");
					PAGE.FORM1.enableItem("prntsCtnMn");
					PAGE.FORM1.enableItem("prntsCtnRn");
					PAGE.FORM1.enableItem("billAcntNo");
					PAGE.FORM1.enableItem("prntsContractNo");
				}else{
					PAGE.FORM1.enableItem("usimKindsCd");
					PAGE.FORM1.enableItem("eid");
					PAGE.FORM1.enableItem("imei1");
					PAGE.FORM1.enableItem("imei2");
					PAGE.FORM1.disableItem("intmMdlId");
					PAGE.FORM1.disableItem("intmSrlNo");
					PAGE.FORM1.disableItem("eReqModelName");
					
					PAGE.FORM1.enableItem("prntsCtnFn");
					PAGE.FORM1.enableItem("prntsCtnMn");
					PAGE.FORM1.enableItem("prntsCtnRn");
					PAGE.FORM1.enableItem("billAcntNo");
					PAGE.FORM1.enableItem("prntsContractNo");
				}			
			}
			
			// 렌탈상품인 경우 재고확인 버튼 disable 처리
			if(rowData.prodType == "02"){
				if(rowData.onOffType != "1"){
					PAGE.FORM1.disableItem("reqPhoneSn");
					PAGE.FORM1.disableItem("reqUsimSn");
				}
				PAGE.FORM1.disableItem("reqModelColor");
				PAGE.FORM1.showItem("DLVRY");
			}else{
				PAGE.FORM1.hideItem("rentalModelCpAmt");
				PAGE.FORM1.hideItem("rentalBaseAmt");
				PAGE.FORM1.hideItem("rentalBaseDcAmt");
			}
			PAGE.FORM1.hideItem("rntlPrdtMdl");
			
			if(rowData.requestStateCode != "00") {
				PAGE.FORM1.hideItem("cloneBaseCard");
				PAGE.FORM1.hideItem("cloneBaseBank");
			}else{
				PAGE.FORM1.showItem("cloneBaseCard");
				PAGE.FORM1.showItem("cloneBaseBank");
			}
			
			PAGE.FORM1.disableItem("pAgentName");	// 대리점
			PAGE.FORM1.disableItem("btnOrgFind");	// 대리점 찾기

			PAGE.FORM1.disableItem("shopNm");	// 판매점
			PAGE.FORM1.disableItem("btnShopFind");	// 판매점 찾기
			
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
			
			var minorSelfCertType = PAGE.FORM1.getItemValue("minorSelfCertType");
			
			if(minorSelfCertType == "02" || minorSelfCertType == "04"){
				PAGE.FORM1.showItem("minorSelfIssuNum");
			}else{
				PAGE.FORM1.hideItem("minorSelfIssuNum");
			}
			
			if(minorSelfCertType == "05"){
				PAGE.FORM1.setItemLabel("minorSelfIssuExprDt", "만료일자");
			}else{
				PAGE.FORM1.setItemLabel("minorSelfIssuExprDt", "발급일자");
			}
			
			// 2024-04-30 아래 조건일때 판매점값 초기화, 판매점 찾기버튼 활성화
			// 1. 위탁온라인 신청서
			// 2. 판매점에 대리점이 셋팅
			// 3. 접수대기
			if(rowData.plcyTypeCd == "N" && rowData.cntpntTypeCd == "20" && rowData.requestStateCode == "00"){
				//판매점 정보 초기화
				sShopId = "";
				sShopNm = "";
				PAGE.FORM1.setItemValue("pShopCode", "");
				PAGE.FORM1.setItemValue("shopNm", "");
				PAGE.FORM1.setItemValue("shopCd", "");
				PAGE.FORM1.setItemValue("cntpntCd", "");
				PAGE.FORM1.enableItem("btnShopFind");
				PAGE.FORM1.enableItem("shopNm");
			}
			
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
		
		if(onOffType == "1") {

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
		
		//v2017.08 프로모션 정책 등록 본사조직만 가능
		if(typeCd != "10"){
			PAGE.FORM1.hideItem("PRMTREG");
		}
		
		/* v2018.01 KT판매코드 적용 */
		var useYn = "Y";
		if(rowData.requestStateCode == "21"){
			useYn = "";
		}
		getKtPlcyListCombo(PAGE.FORM1, useYn);
		PAGE.FORM1.setItemValue("spcCode", rowData.spcCode);
		
		/* 프로모션명 적용 */
		PAGE.FORM1.setItemValue("disPrmtId", rowData.prmtId);
		PAGE.FORM1.setItemValue("disPrmtNm", rowData.prmtNm);
		PAGE.FORM1.setItemValue("totalDiscount",rowData.disCnt);

		// 외국인 국가 설정
		PAGE.FORM1.setItemValue("cstmrForeignerNationSel" ,rowData.cstmrForeignerNation);
		PAGE.FORM1.setItemValue("cstmrForeignerNationTxt" ,rowData.cstmrForeignerNation);

		// [SRM18091729199] M전산 기기변경 기능 대리점 확대 적용
		if(rowData.operType == "HCN3" || rowData.operType == "HDN3") {
			// 기존 USIM 일련번호
			PAGE.FORM1.setItemValue("iccId", rowData.reqUsimSn);
			
			// 기기변경시 변경 불가 항목
			PAGE.FORM1.disableItem("operType");
			PAGE.FORM1.disableItem("cstmrType");
			PAGE.FORM1.disableItem("reqBuyType");
			PAGE.FORM1.disableItem("dvcChgRsnDtlCd");
			PAGE.FORM1.disableItem("dvcChgType");
			PAGE.FORM1.disableItem("dvcChgRsnCd");
			PAGE.FORM1.disableItem("dvcChgRecId");
			 
			// 대리점 오프라인 기기변경 접수시 변경가능 항목
			// 단말기일련번호, 약정기간, 할부기간, 단말색상, 추가지원금, 유심일련번호, USIM구매, 유심종류, 특별판매코드, 메모사항
			if(onOffType == "1") {
				// 활성화
				PAGE.FORM1.enableItem("agrmTrm");
				PAGE.FORM1.enableItem("instNom");
				PAGE.FORM1.enableItem("modelDiscount3");
				// 비활성화
				PAGE.FORM1.disableItem("shopNm");
				
			}
		}
		
		// 개통완료인 경우 수정버튼 숨김
		if(PAGE.FORM1.getItemValue("requestStateCode") == "21"){
			mvno.ui.hideButton("FORM1" , "btnSend");
			mvno.ui.hideButton("FORM1" , "btnSave");
			mvno.ui.hideButton("FORM1" , "btnMod");
			mvno.ui.hideButton("FORM1" , "btnCopy");
		}
		
		//단말보험
		PAGE.FORM1.setItemValue("insrProdCd",rowData.insrProdCd);
		PAGE.FORM1.setItemValue("clauseInsrProdFlag", rowData.clauseInsrProdFlag);

		//20230718 자급제보상서비스
		PAGE.FORM1.setItemValue("rwdProdCd",rowData.rwdProdCd);
		PAGE.FORM1.setItemValue("clauseRwdFlag", rowData.clauseRwdFlag);
		
		setPrmtResultItem();
		
		// 상품구분 "자급제" 인 경우
		if(rowData.prodType == "04") {
			PAGE.FORM1.hideItem("hndstAmt"); // 출고가 // 판매정보(핸드폰대금) 단말 부분 안보이게 설정
			PAGE.FORM1.hideItem("modelDiscount2"); // 공시지원금 
			PAGE.FORM1.hideItem("sprtTp"); // 할인유형 
			PAGE.FORM1.hideItem("modelDiscount3"); // 추가지원금 
			PAGE.FORM1.hideItem("drctMngmPrdcNm"); // 직영상품명 추가 
			PAGE.FORM1.hideItem("dcAmt"); // 기본할인금액 
			PAGE.FORM1.hideItem("addDcAmt"); // 추가할인금액 
			PAGE.FORM1.hideItem("modelInstallment"); // 할부원금 
			PAGE.FORM1.hideItem("reqModelName"); // 휴대폰모델명 
		}

		// 데이터쉐어링 요금제일 경우 필드 설정
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
	
	function form1OpenInit(){
		mvno.ui.setTitle("신청조회");
		
		mvno.ui.hideButton("FORM1" , "btnMod");
		mvno.ui.hideButton("FORM1" , "btnSend");
		mvno.ui.hideButton("FORM1" , "btnSave");
		mvno.ui.hideButton("FORM1" , "btnCopy");
		mvno.ui.hideButton("FORM1" , "btnNew");
		
		var rowData = "";
		var strRequestKey = PAGE.FORM1.getItemValue("requestKey");
		
		mvno.cmn.ajax2(ROOT + "/rcp/rcpMgmt/rcpListAjax.json", {requestKey:strRequestKey}, function(resultData) {
			rowData = resultData.result.data;

			//20250715 데이터쉐어링 요금제일 경우 체크
			sharingYn = resultData.result.sharingYn;
			
			// SRM18072675707, 단체보험체크
			var strReqInDay = rowData.reqInDay.replace(/-/g, "");
			mvno.cmn.ajax4lov( ROOT + "/ptnr/grpinsrmgmt/getGrpInsrCombo.json", {reqInDay:strReqInDay}, PAGE.FORM1, "insrCd");
			
			form1Change(PAGE.FORM1, 0, "init");
			
			PAGE.FORM1.setFormData(rowData);
			
			var aryAgent = rowData.agentName.split("/");
			
			PAGE.FORM1.setItemValue("pAgentCode", rowData.agentCode);
			PAGE.FORM1.setItemValue("pAgentName", aryAgent[0]);
			
			//---------------------------------------------------------------------------------------------------------
			// 메뉴 숨김/보임 설정 START
			
			var hideItems = new Array();
			var showItems = new Array();
			
			// 등록시 숨길 메뉴 설정
			hideItems = [
							"CARD",			// 카드정보
							,"BANK"			// 은행정보
							,"FOREIGNER"	// 외국인정보
							,"BUSINESS"		// 사업자정보
							,"MINORINFO"		// 법정대리인
							,"REQMOVE"		// 번호이동정보
							,"NEWREQ"		// 신규가입
							,"DVCCHG"		// 기기변경정보
							,"DVCREG"		// 기변대상조회
							,"BNFT"			// v2018.02 기기변경 시 고객혜택 추가
							,"SELPHONE"			// 2022-03-07 자급제 폰 정보 추가
						];

			showItems = [
							"faxnum",		// 팩스(신청서정보)
							"btnPaper",		// 서식지보기(신청서정보)
							"DLVRY",		// 배송정보
							"RENTAL",		// 렌탈
							"AUTH"			// 본인인증
						];
			
			for(var inx=0; inx < hideItems.length; inx++){
				PAGE.FORM1.hideItem(hideItems[inx]);
			}

			for(var inx=0; inx < showItems.length; inx++){
				PAGE.FORM1.showItem(showItems[inx]);
			}
			
			PAGE.FORM1.hideItem("smsAuthInfo");		// SMS인증
			PAGE.FORM1.hideItem("btnSendOtp");		// 인증요청
			PAGE.FORM1.hideItem("btnOtpConfirm");	// 인증확인
			PAGE.FORM1.hideItem("btnReSendOtp");	// 재전송
			
			PAGE.FORM1.showItem("dvcChgType");		// 기변유형
			PAGE.FORM1.showItem("dvcChgRsnCd");		// 기변사유
			PAGE.FORM1.showItem("dvcChgRecId");		// 기변녹취ID
			PAGE.FORM1.showItem("usePointAmt");     //  포인트사용금액 show 2022.02.21 
			
			// 개통이력확인
			if(PAGE.FORM1.getItemValue("requestStateCode") != "00"){
				PAGE.FORM1.hideItem("btnCheckCstmr");
			}
			
			if(PAGE.FORM1.getItemValue("reqBuyType") == "MM"){
				PAGE.FORM1.hideItem("dlvryType");
			}
			
			// 팩스 OR 스캔 구성(신청서정보-서식지)
			form1Change(PAGE.FORM1, 0, "faxyn");
			
			// 신청유형에 따른 화면 구성(신규가입/번호이동정보/기기변경정보)
			form1Change(PAGE.FORM1, 0, "operType");
			
			// 고객구분에 따른 화면 구성(내국인/미성년/외국인/사업자)
			form1Change(PAGE.FORM1, 0, "cstmrType");
			
			// 구매유형에 따른 화면 구성
			form1Change(PAGE.FORM1, 0, "reqBuyType");
			
			// 단말모델색상
			form1Change(PAGE.FORM1, 0, "reqModelColor");

			form1Change(PAGE.FORM1, 0, "combineSoloType");
			
			// 신청일자
			PAGE.FORM1.setItemValue("reqInDayStr", rowData.reqInDay);
			form1Change(PAGE.FORM1, 0, "reqInDay");
			
			//20220805 신청서 읽어올때 유심종류가 esim 이 아닌 경우에는 esim으로 변경 못함
			if(rowData.usimKindsCd != "09"){
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2035" , etc1:"1"}, PAGE.FORM1, "usimKindsCd"); // 유심종류
				PAGE.FORM1.setItemValue("usimKindsCd", rowData.usimKindsCd );
			}else {
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2035"}, PAGE.FORM1, "usimKindsCd"); // 유심종류
				PAGE.FORM1.setItemValue("usimKindsCd", rowData.usimKindsCd );
				//유심종류
				form1Change(PAGE.FORM1, 0, "usimKindsCd");
			}
			
			// 요금납부정보
			// 서비스타입에 따라 요금납부정보 콤보 리스트가 달라짐
			form1Change(PAGE.FORM1, 0, "serviceType");
			PAGE.FORM1.setItemValue("reqPayType", rowData.reqPayType);
			form1Change(PAGE.FORM1, 0, "reqPayType");
			
			// 구매유형/신청유형/모집경로 에 따라 단말모델/요금제 값이 자동변경되기 때문에
			// 이곳에서 재설정 필요
			PAGE.FORM1.setItemValue("reqBuyType", rowData.reqBuyType);			// 구매유형
			PAGE.FORM1.setItemValue("operType", rowData.operType);				// 신규유형
			PAGE.FORM1.setItemValue("onOffType", rowData.onOffType);			// 모집경로
			PAGE.FORM1.setItemValue("mnfctId", rowData.mnfctId);				// 제조사
			PAGE.FORM1.setItemValue("prdtId", rowData.prdtId);					// 단말모델
			PAGE.FORM1.setItemValue("socCode", rowData.socCode);				// 요금제
			
			// 모델색상
			form1Change(PAGE.FORM1, 0, "prdtId");
			PAGE.FORM1.setItemValue("reqModelColor", rowData.reqModelColor);	// 단말모델색상
			
			if(rowData.reqBuyType == "UU"){
				PAGE.FORM1.setItemValue("prdtCode", rowData.reqUsimName);			// USIM모델코드
			}
			else {
				PAGE.FORM1.setItemValue("prdtCode", rowData.reqModelName);			// USIM모델코드
			}
			
			// 단말기일련번호
			PAGE.FORM1.setItemValue("reqPhoneSn", rowData.reqPhoneSn);			// reqPhoneSn
			
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
			
			var minorSelfCertType = PAGE.FORM1.getItemValue("minorSelfCertType");
			
			if(minorSelfCertType == "02" || minorSelfCertType == "04"){
				PAGE.FORM1.showItem("minorSelfIssuNum");
			}else{
				PAGE.FORM1.hideItem("minorSelfIssuNum");
			}
			
			if(minorSelfCertType == "05"){
				PAGE.FORM1.setItemLabel("minorSelfIssuExprDt", "만료일자");
			}else{
				PAGE.FORM1.setItemLabel("minorSelfIssuExprDt", "발급일자");
			}
			
			// 상품구분 "자급제" 인 경우
			if(rowData.prodType == "04") {
				PAGE.FORM1.showItem("SELPHONE");// 자급제 정보 표시
			} else {
				PAGE.FORM1.hideItem("SELPHONE");// 자급제 정보 숨기기
			}
			
			PAGE.FORM1.disableItem("BODY");
		});
		
		// 오프라인일때만 배송정보 체크박스 보이도록 수정 20170613 박준성
		var onOffType = PAGE.FORM1.getItemValue("onOffType");
		
		if(onOffType == "1") {
			PAGE.FORM1.showItem("dlvryYn");
		} else {
			PAGE.FORM1.hideItem("dlvryYn");
		}
		
		if(onOffType == "0" || onOffType == "3" || onOffType == "6" || onOffType == "8"){
			PAGE.FORM1.showItem("ktInterSvcNo");
		}else{
			PAGE.FORM1.hideItem("ktInterSvcNo");
		}
		
		//v2017.08 프로모션 정책 등록 본사조직만 가능
		if(typeCd != "10"){
			PAGE.FORM1.hideItem("PRMTREG");
		}

		//알뜰폰허브 관련 숨김
		PAGE.FORM1.hideItem("hubOrderNum");
		PAGE.FORM1.hideItem("btnHubOrder");

		var usimKindsCd = PAGE.FORM1.getItemValue("usimKindsCd");
		
		/*유심종류 eSim추가 */
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
		
		/* v2018.01 KT판매코드 적용 */
		getKtPlcyListCombo(PAGE.FORM1, "");
		PAGE.FORM1.setItemValue("spcCode", rowData.spcCode);
		
		//단말보험
		PAGE.FORM1.setItemValue("insrProdCd", rowData.insrProdCd);
		PAGE.FORM1.setItemValue("clauseInsrProdFlag", rowData.clauseInsrProdFlag);

		//20230718 자급제보상서비스
		PAGE.FORM1.setItemValue("rwdProdCd",rowData.rwdProdCd);
		PAGE.FORM1.setItemValue("clauseRwdFlag", rowData.clauseRwdFlag);
		
		setPrmtResultItem();

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
					form.setItemValue("clauseInsrProdFlag", "1");
				}
				
				break;		
			
			// 2019-01-24, [SRM19012347942] 신청관리 통한 개인정보 광고전송 동의 선택 방식 변경 요청
			case "clausePriAdFlag":
				mvno.ui.alert("서식지 내의 정보/광고 수신 위탁 동의 여부와 일치해야 합니다");
				break;
			// 2018.07.05 추가
			case "modelDiscount3" :
				if(mvno.cmn.isEmpty(form.getItemValue("hndstAmt")) || form.getItemValue("hndstAmt") == "0"){
					return;
				}
				var modelInstallment = Number(form.getItemValue("hndstAmt")) - Number(form.getItemValue("modelDiscount2"))  - Number(form.getItemValue("modelDiscount3"));
				if(modelInstallment < 0) modelInstallment = 0;
				form.setItemValue("modelInstallment", modelInstallment);
				
				break;
				
			case "init" :
				// 제조사 리스트
				mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpCommon.json", {grpId:"MNFCT"}, form, "mnfctId");
				
				break;
				
			/* case "reqPhoneSn" :
				form.setItemValue("checkSn", "N");
				break; */
				
			case "reqBuyType" :
				//"MM:단말구매,UU:유심단독구매"
				var value = form.getItemValue(name);

				if(form.getItemValue("requestStateCode") != "21") {
					if(value == "MM") {
						enableItems = ["reqModelColor", "reqPhoneSn", "sprtTp"];
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
						disableItems = ["reqModelColor", "sprtTp"];
						if(form.getItemValue("prodType") != "02" || form.getItemValue("onOffType") != "1"){
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
				
				// 판매정보
				getSaleinfo(form, name);
				
				// KT판매코드
				getKtPlcyListCombo(form, "Y");
				
				// 판매정보 초기화
				form.setItemValue("socCode", "");
				form.setItemValue("agrmTrm", "");
				form.setItemValue("instNom", "");
				form.setItemValue("mnfctId", "");
				form.setItemValue("prdtId", "");
				form.setItemValue("prdtCode", "");
				form.setItemValue("reqModelColor", "");
				form.setItemValue("sprtTp", "");
				form.setItemValue("spcCode", "");
				
				// 단말보험
				getIntmInsrComboList(form, form.getItemValue("prdtId"), form.getItemValue("reqBuyType"));
				
				// 프로모션명 / 총할인액
				getDisPrmtListCombo(form, "Y");

				break;
				
			case "operType" :
				var value = form.getItemValue(name);
				
				if(mFlag == 1) {
					// 초기화
					dvcChngRegInit(value);
					operTypeInit();
				}
				
				hideItems = [
								"REQMOVE"	// 번호이동정보
								,"NEWREQ"	// 신규가입
								,"DVCCHG"	// 기기변경정보
								,"BNFT"		// v2018.02 기기변경 시 고객혜택 추가
							];
				
				// [SRM18091729199] M전산 기기변경 기능 대리점 확대 적용
				// "MNP3:번호이동,NAC3:개통,HCN3:기기변경"
				if(value == "MNP3"){
					showItems = ["REQMOVE"];
					
					enableItems.push("insrCd");
					enableItems.push("clauseInsuranceFlag");
					enableItems.push("insrProdCd");
					enableItems.push("clauseInsrProdFlag");
				} else if(value == "NAC3"){
					showItems = ["NEWREQ"];
					
					enableItems.push("insrCd");
					enableItems.push("clauseInsuranceFlag");
					enableItems.push("insrProdCd");
					enableItems.push("clauseInsrProdFlag");
				} else {
					showItems = ["DVCCHG"];
					
					// v2018.02 기기변경 시 고객혜택 추가
					if(typeCd == "10") showItems.push("BNFT");
					
					// 기기변경인 경우 단체보험 선택 제외
					PAGE.FORM1.setItemValue("insrCd", "");
					PAGE.FORM1.setItemValue("clauseInsuranceFlag", "0");
					disableItems.push("insrCd");
					disableItems.push("clauseInsuranceFlag");
					// [SRM18120707112] KOS분실고객 기기변경 신청등록 시 SMS인증 Skip 및 녹취ID인증 메뉴 개발요청
					PAGE.FORM1.setItemLabel("smsAuthInfo", "SMS인증");
					PAGE.FORM1.setItemValue("subStatus", "");
					PAGE.FORM1.setItemValue("subStatusRsnNm", "");
					PAGE.FORM1.setItemValue("lostYn", "");
					if(form.getItemValue("resNo") == "") {
						hideItems.push("dvcChgRecId");
					}
					// [SRM18120707112] KOS분실고객 기기변경 신청등록 시 SMS인증 Skip 및 녹취ID인증 메뉴 개발요청
					
					//단말보험
					PAGE.FORM1.setItemValue("insrProdCd", "");
					PAGE.FORM1.setItemValue("clauseInsrProdFlag", "0");
					disableItems.push("insrProdCd");
					disableItems.push("clauseInsrProdFlag");
					
				}
				
				if(form.getItemValue("prodType") == "02"){
					showItems.push("DLVRY");
				}
				
				// 단말모델 콤보박스 적용
				form1Polcy(form, "PRDT", "prdtId");
				
				// 요금제 적용
				form1Polcy(form, "RATE", "socCode");
				
				// 판매정보
				getSaleinfo(form, name);
				
				// 프로모션 적용
				getDisPrmtListCombo(form, "Y");

				break;

			case "pAgentCode" :
				var value = form.getItemValue(name);

				if(value == "1100017898") {
					showItems.push("hubOrderNum");
					showItems.push("btnHubOrder");
				} else {
					hideItems.push("hubOrderNum");
					hideItems.push("btnHubOrder");
					form.setItemValue("hubOrderNum", "");
					form.setItemValue("hubOrderSeq", "");
				}

				// 대리점 변경에 따른 처리 (제휴사)
				enableItems.push("clausePartnerOfferFlag");
				form.setItemValue("clausePartnerOfferFlag", "");
				break;

			case "mnfctId" :
				// 단말모델 콤보박스 적용
				form1Polcy(form, "PRDT", "prdtId");
				// 단말색상 적용
				form1Change(PAGE.FORM1, 0, "prdtId");
				
				break;
				
			case "cstmrType" :
				var value = form.getItemValue(name);
				
				if(mFlag == 1) {
					// 초기화
					cstmrInit();
				}
				
				hideItems = [
								"FOREIGNER",	// 외국인정보
								"BUSINESS",		// 사업자정보
								"MINORINFO"			// 법정대리인
							];
				
				var operType = PAGE.FORM1.getItemValue("operType");
				//"NA:내국인,NM:내국인(미성년자),FN:외국인,JP:법인,PP:개인사업자"
				switch (value) {
					case "PP" : // 개인사업자
						if(operType == "HCN3" || operType == "HDN3") break;
						
						showItems = [ "BUSINESS" ];
					
						// 법인 관련 필드 숨기기 및 초기화
						// 법인명
						PAGE.FORM1.hideItem("cstmrJuridicalCname");
						
						// 사업자번호
						PAGE.FORM1.hideItem("cstmrJuridicalNumber1");
						PAGE.FORM1.hideItem("cstmrJuridicalNumber2");
						PAGE.FORM1.hideItem("cstmrJuridicalNumber3");
						
						// 법인 번호
						PAGE.FORM1.hideItem("cstmrJuridicalRrn1");
						PAGE.FORM1.hideItem("cstmrJuridicalRrn2");
						
						// 개인사업자 관련 필드 보이기
						// 개인사업자명
						PAGE.FORM1.showItem("cstmrPrivateCname");
						// 사업자번호
						PAGE.FORM1.showItem("cstmrPrivateNumber1");
						PAGE.FORM1.showItem("cstmrPrivateNumber2");
						PAGE.FORM1.showItem("cstmrPrivateNumber3");
						
						break;
						
					case "JP" : // 법인사업자
						if(operType == "HCN3" || operType == "HDN3") break;
						
						showItems = [ "BUSINESS" ];
					
						// 개인사업자 관련 필드 숨기기 및 초기화
						// 개인사업자명
						PAGE.FORM1.hideItem("cstmrPrivateCname");
						
						// 사업자번호
						PAGE.FORM1.hideItem("cstmrPrivateNumber1");
						PAGE.FORM1.hideItem("cstmrPrivateNumber2");
						PAGE.FORM1.hideItem("cstmrPrivateNumber3");
						
						// 법인사업자 관련 필드 보이기
						// 법인명
						PAGE.FORM1.showItem("cstmrJuridicalCname");
						// 사업자번호
						PAGE.FORM1.showItem("cstmrJuridicalNumber1");
						PAGE.FORM1.showItem("cstmrJuridicalNumber2");
						PAGE.FORM1.showItem("cstmrJuridicalNumber3");
						// 법인번호
						PAGE.FORM1.showItem("cstmrJuridicalRrn1");
						PAGE.FORM1.showItem("cstmrJuridicalRrn2");
						
						break;
						
					case "FN" : // 외국인
						if(operType == "HCN3" || operType == "HDN3") break;
						
						showItems = ["FOREIGNER"];
						
						break;
						
					case "NM" : // 미성년자
						showItems = ["MINORINFO"];
						
						break;
						
					default : // 내국인
						break;
				}
				
				// SRM18072675707, 단체보험 추가
				if(grpInsrYn == "Y" && (operType == "NAC3" || operType == "MNP3")){
					if(value == "JP" || value == "PP"){
						disableItems.push("insrCd");
						disableItems.push("clauseInsuranceFlag");
						form.setItemValue("insrCd", "");
						form.setItemValue("clauseInsuranceFlag", "0");
					}else{
						enableItems.push("insrCd");
						enableItems.push("clauseInsuranceFlag");
					}
				}
				
				break;
				
			case "faxyn":
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
								"btnRecSarch"	// 녹취파일등록
							];
				
				// 보일 항목 설정
				// 팩스 선택
				if(value == "Y") {
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

					showItems = ["faxyn"];
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
				
			// 배송정보 입력가능 하도록 체크박스 추가 20170610 박준성
			case "dlvryYn":
				var dlvryYn = form.getItemValue("dlvryYn");
				if(dlvryYn == "1"){
					showItems = ["DLVRY"];
				} else {
					hideItems = ["DLVRY"];
				}
				
				break;
				
			case "onOffType":
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
				if(onOffType == "1")
				{
					form.setItemValue("faxyn", "N");
					showItems = ["faxyn", "btnScnSarch", "btnRecSarch"];	// 스캔을 기본으로
				}
				else
				{
					showItems = ["DLVRY", "btnRecSarch"];	// 녹취파일등록
				}
				
		 		if(onOffType == "0"|| onOffType == "3"|| onOffType == "6" || onOffType == "8"){
					showItems = ["ktInterSvcNo"];
				}else{
					hideItems = ["ktInterSvcNo"];
				} 
			
				
				
				break;
				
			case "serviceType":
				var value = form.getItemValue(name);
				
				if(value == "PO"){
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2015"}, PAGE.FORM1, "reqPayType");
				} else {
					mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2016"}, PAGE.FORM1, "reqPayType");
				}
				
				break;
				
			case "reqPayType":
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
				
			case "reqAcType":
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
				
			case "reqInDay":
				
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
				getDisPrmtListCombo(form, "Y");

				break;
				
			case "prdtId":
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
				
				// 판매정보
				getSaleinfo(form, name);
				
				// 단말보험
				getIntmInsrComboList(form, form.getItemValue("prdtId"), form.getItemValue("reqBuyType"));
				
				// 프로모션 적용
				getDisPrmtListCombo(form, "Y");

				break;
				
			case "cloneBase":
				var value = form.getItemValue(name);
				if (value == "1") {
					form.setItemValue("dlvryName",form.getItemValue("cstmrName"));
					form.setItemValue("dlvryTelFn",form.getItemValue("cstmrTelFn"));
					form.setItemValue("dlvryTelMn",form.getItemValue("cstmrTelMn"));
					form.setItemValue("dlvryTelRn",form.getItemValue("cstmrTelRn"));
					form.setItemValue("dlvryMobileFn",form.getItemValue("cstmrTelFn"));
					form.setItemValue("dlvryMobileMn",form.getItemValue("cstmrTelMn"));
					form.setItemValue("dlvryMobileRn",form.getItemValue("cstmrTelRn"));
					/* form.setItemValue("dlvryMobileFn",form.getItemValue("cstmrMobileFn"));
					form.setItemValue("dlvryMobileMn",form.getItemValue("cstmrMobileMn"));
					form.setItemValue("dlvryMobileRn",form.getItemValue("cstmrMobileRn")); */
					form.setItemValue("dlvryAddr",form.getItemValue("cstmrAddr"));
					form.setItemValue("dlvryPost",form.getItemValue("cstmrPost"));
					form.setItemValue("dlvryAddrDtl",form.getItemValue("cstmrAddrDtl"));
				}
				
				break;
				
			case "cstmrMail3":
				var value = form.getItemValue(name);
				if(value == "직접입력") {
					enableItems = ["cstmrMail2"];
				} else {
					disableItems = ["cstmrMail2"];
					form.setItemValue("cstmrMail2", value);
				}
				
				break;
				
			case "reqWireType1" :
				var value = form.getItemValue(name);
				if ( value != "") {
					disableItems = ["reqWireType2"];
				} else {
					enableItems = ["reqWireType2"];
				}
				
				break;
				
			case "reqWireType2" :
				var value = form.getItemValue(name);
				if ( value != "") {
					disableItems = ["reqWireType1"];
				} else {
					enableItems = ["reqWireType1"];
				}
				
				break;
				
			case "requestStateCode" :
				var value = form.getItemValue(name);
				hideItems = ["tbCd", "dlvryNo"];
				if ( value == "10" || value == "20" || value == "21") {
					showItems = ["tbCd", "dlvryNo"];
				}
				
				break;
				
			/* case "reqModelColor" :
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
				
				break; */
				
			case "usimPayMthdCd" :
				// [SRM18091729199] M전산 기기변경 기능 대리점 확대 적용
				var usimPayMthdCd = form.getItemValue("usimPayMthdCd");
				var operType = form.getItemValue("operType");
				
				if(usimPayMthdCd == "1") {
					form.setItemValue("usimPrice", "0");				// USIM비
				}
				
				// 기기변경인 경우 USIM 구매여부에 따라서 값 세팅
				if(operType == "HCN3" || operType == "HDN3") {
					if(usimPayMthdCd != "1") {
						form.setItemValue("reqUsimSn", "");
					} else {
						form.setItemValue("reqUsimSn", form.getItemValue("iccId"));
					}
				}
				
				break;
				
			case "joinPayMthdCd" :
				if(form.getItemValue("joinPayMthdCd") == "1") {
					form.setItemValue("joinPrice", "0");				// 가입비
				}
				
				break;
				
			case "socCode" :
				// 판매정보
				getSaleinfo(form, name);
				
				// 프로모션 적용
				getDisPrmtListCombo(form, "Y");

				// 요금제 변경에 따른 처리 (제휴처)
				enableItems.push("clauseJehuFlag");
				form.setItemValue("clauseJehuFlag", "");
				form.setItemValue("jehuProdType", "");

				updateCombineSoloVisible(form.getItemValue(name));

				break;
				
			case "agrmTrm" :
				// 판매정보
				getSaleinfo(form, name);
				
				// KT판매코드
				getKtPlcyListCombo(form, "Y");
				
				// 프로모션 적용
				getDisPrmtListCombo(form, "Y");
				break;
				
			case "sprtTp" :
				// 판매정보
				getSaleinfo(form, name);
				
				// KT판매코드
				getKtPlcyListCombo(form, "Y");
				
				// 프로모션 적용
				getDisPrmtListCombo(form, "Y");

				break;
				
			case "instNom" :
				// KT판매코드
				getKtPlcyListCombo(form, "Y");
				
				// 프로모션 적용
				getDisPrmtListCombo(form, "Y");

				break;
				
			case "selfCertType" :
				var selfCertType = form.getItemValue(name);
				
				if(selfCertType == "02" || selfCertType == "04"){
					form.showItem("selfIssuNum");
				}else{
					form.hideItem("selfIssuNum");
					form.setItemValue("selfIssuNum", "");
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
				
			case "minorSelfCertType" :
				var minorSelfCertType = form.getItemValue(name);
				
				if(minorSelfCertType == "02" || minorSelfCertType == "04"){
					form.showItem("minorSelfIssuNum");
				}else{
					form.hideItem("minorSelfIssuNum");
					form.setItemValue("minorSelfIssuNum", "");
				}
				
				if(minorSelfCertType == "05"){
					form.setItemLabel("minorSelfIssuExprDt", "만료일자");
				}else{
					form.setItemLabel("minorSelfIssuExprDt", "발급일자");
				}

				//안면인증 사용여부 공통코드
				if(mFlag === 0 && form.getItemValue("fathUseYn") === "Y"){
					//안면인증대상 조회 필요
					PAGE.FORM1.setItemValue("isFathTrgFlag", "0");
					mvno.ui.alert("안면인증대상여부를 확인하세요.");
				}
				
				break;
				
			case "cloneBaseCard":
				var value = form.getItemValue(name);
				if (value == "1") {
					form.setItemValue("reqCardName",form.getItemValue("cstmrName"));
					form.setItemValue("reqCardRrn1",form.getItemValue("cstmrNativeRrn1"));
					form.setItemValue("reqCardRrn2",form.getItemValue("cstmrNativeRrn2"));
					
				}
				
				break;
				
			case "cloneBaseBank":
				var value = form.getItemValue(name);
				if (value == "1") {
					form.setItemValue("reqAccountName",form.getItemValue("cstmrName"));
					form.setItemValue("reqAccountRrn1",form.getItemValue("cstmrNativeRrn1"));
					form.setItemValue("reqAccountRrn2",form.getItemValue("cstmrNativeRrn2"));
				}
				
				break;
				
			case "prodType":
				
				var value = form.getItemValue(name);
				
				if (value == "04") {
					mvno.ui.alert("자급제는 선택할 수 없습니다.");
					form.setItemValue("prodType", "01");
					return;
				}
				
				if(value == "02"){
					
					form.setItemValue("clauseRentalService", "Y");
					form.setItemValue("clauseRentalModelCp", "Y");
					form.setItemValue("clauseRentalModelCpPr", "Y");
					
					showItems = ["RENTAL", "DLVRY", "rntlPrdtMdl", "rentalModelCpAmt", "rentalBaseAmt", "rentalBaseDcAmt"];
					disableItems = ["reqBuyType", "clauseRentalService", "clauseRentalModelCp", "clauseRentalModelCpPr", "rentalModelCpAmt", "rentalBaseAmt", "rentalBaseDcAmt"];
					
					if(mFlag == 1){
						form.setItemValue("reqBuyType", "UU");
						form1Change(PAGE.FORM1, 0, "reqBuyType");
						
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2014",orderBy:"etc6"}, PAGE.FORM1, "operType"); // 신청유형
						
						form1Change(PAGE.FORM1, 1, "operType");
					}else{
						getRntlPrdtInfo("rntlPrdtMdl");
					}
					
					if(form.getItemValue("onOffType") == "1"){
						form.enableItem("reqPhoneSn");
					}
					
				}else{
					
					form.setItemValue("clauseRentalService", "");
					form.setItemValue("clauseRentalModelCp", "");
					form.setItemValue("clauseRentalModelCpPr", "");
					
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
					
					hideItems = ["RENTAL", "rntlPrdtMdl", "rentalModelCpAmt", "rentalBaseAmt", "rentalBaseDcAmt"];
					
					enableItems = ["reqBuyType"];
					
					if(form.getItemValue("onOffType") == "1"){
						hideItems.push("DLVRY");
					}
					
					if(mFlag == 1 && menuId != "MSP1010100" && dvcChgAuthYn == "Y"){
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0049",orderBy:"etc1"}, PAGE.FORM1, "operType");
						form1Change(PAGE.FORM1, 1, "operType");
					}
					
					if(form.getItemValue("reqBuyType") == "UU"){
						form.disableItem("reqPhoneSn");
						form.setItemValue("reqPhoneSn", "");
					}
				}
				
				break;
				
			case "rntlPrdtMdl":
				
				if(mvno.cmn.isEmpty(rntlPrdtInfo)){
					return;
				}
				
				if(mvno.cmn.isEmpty(form.getItemValue(name))){
					form.setItemValue("rentalModelCpAmt", "");
					form.setItemValue("rentalBaseAmt", "");
					form.setItemValue("rentalBaseDcAmt", "");
					form.setItemValue("rntlPrdtId", "");
					form.setItemValue("rntlPrdtCode", "");
					
					return;
				}
				
				var idx = Number(form.getItemValue(name));
				
				form.setItemValue("rentalModelCpAmt", rntlPrdtInfo.rows[idx].buyAmnt);
				form.setItemValue("rentalBaseAmt", rntlPrdtInfo.rows[idx].rentalCost);
				form.setItemValue("rentalBaseDcAmt", rntlPrdtInfo.rows[idx].rentalCost);
				form.setItemValue("rntlPrdtId", rntlPrdtInfo.rows[idx].prdtId);
				form.setItemValue("rntlPrdtCode", rntlPrdtInfo.rows[idx].prdtCode);
				
				break;
			
			case "insrCd" :
				if(mvno.cmn.isEmpty(form.getItemValue(name))) {
					form.setItemValue("clauseInsuranceFlag", "");
				} else {
					form.setItemValue("clauseInsuranceFlag", "Y");
				}
				
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
					showItems = ["eid", "imei1" , "imei2", "intmMdlId" , "intmSrlNo" , "eReqModelName", "prntsCtnFn", "prntsCtnMn", "prntsCtnRn", "billAcntNo", "prntsContractNo"];
					if(esimYn == "N") {
						chgPrdt = "Y";
						esimYn = "Y";
					}else{
						chgPrdt = "Y";
					}
				}else{
					hideItems = ["eid", "imei1", "imei2", "intmMdlId" , "intmSrlNo" , "eReqModelName"];
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
					
					getKtPlcyListCombo(form, "Y");
				}

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

			case 'cstmrForeignerNationSel' :

				var nation = form.getItemValue("cstmrForeignerNationSel");

				form.setItemValue("cstmrForeignerNation", nation);
				form.setItemValue("cstmrForeignerNationTxt", nation);

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
			if(form.getItemValue("usimKindsCd") == "09") prdtIndCd = "10";
			
			mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpCommon.json",
								{
									grpId:gubun
									,orgnId:sOrgnId
									,reqBuyType:form.getItemValue("reqBuyType")
									,mnfctId:form.getItemValue("mnfctId")
									,reqInDay:form.getItemValue("reqInDayStr")
									,prdtIndCd:prdtIndCd
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
		// VISA 코드
		PAGE.FORM1.setItemValue("visaCd", "");
		
		// 사업자정보
		// 상호명
		PAGE.FORM1.setItemValue("cstmrPrivateCname", "");
		// 법인명
		PAGE.FORM1.setItemValue("cstmrJuridicalCname", "");
		// 사업자번호(개인)
		PAGE.FORM1.setItemValue("cstmrPrivateNumber1", "");
		PAGE.FORM1.setItemValue("cstmrPrivateNumber2", "");
		PAGE.FORM1.setItemValue("cstmrPrivateNumber3", "");
		// 사업자번호(법인) 
		PAGE.FORM1.setItemValue("cstmrJuridicalNumber1", "");
		PAGE.FORM1.setItemValue("cstmrJuridicalNumber2", "");
		PAGE.FORM1.setItemValue("cstmrJuridicalNumber3", "");
		// 법인번호
		PAGE.FORM1.setItemValue("cstmrJuridicalRrn1", "");
		PAGE.FORM1.setItemValue("cstmrJuridicalRrn2", "");
		
		// 법정대리인
		// 대리인성명
		PAGE.FORM1.setItemValue("minorAgentName", "");
		// 관계
		PAGE.FORM1.setItemValue("minorAgentRelation", "");
		// 대리인주민번호
		PAGE.FORM1.setItemValue("minorAgentRrn1", "");
		PAGE.FORM1.setItemValue("minorAgentRrn2", "");
		// 연락처
		PAGE.FORM1.setItemValue("minorAgentTelFn", "");
		PAGE.FORM1.setItemValue("minorAgentTelMn", "");
		PAGE.FORM1.setItemValue("minorAgentTelRn", "");
		// 본인조회동의
		PAGE.FORM1.setItemValue("minorSelfInqryAgrmYn", "");
		// 인증방식
		PAGE.FORM1.setItemValue("minorSelfCertType", "");
		// 발급/만료일자
		PAGE.FORM1.setItemValue("minorSelfIssuExprDt", "");
		// 발급번호
		PAGE.FORM1.setItemValue("minorSelfIssuNum", "");
		
		// 2016-10-11 개통이력 조회
		PAGE.FORM1.setItemValue("checkCstmr", "N");
		PAGE.FORM1.showItem("btnCheckCstmr");
		
		PAGE.FORM1.hideItem("minorSelfIssuNum");
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

		/* 기기변경 ***********************************************************/
		// 기변상세사유
		PAGE.FORM1.setItemValue("dvcChgRsnDtlCd", "");
		// 기변유형
		PAGE.FORM1.setItemValue("dvcChgType", "");
		// 기변사유
		PAGE.FORM1.setItemValue("dvcChgRsnCd", "");
		// 기변녹취ID
		PAGE.FORM1.setItemValue("dvcChgRecId", "");
		/* 기기변경 ***********************************************************/
		
		PAGE.FORM1.setItemValue("custNm", "");
		PAGE.FORM1.setItemValue("subscriberNo", "");
		PAGE.FORM1.setItemValue("idntCd", "10");
		PAGE.FORM1.setItemValue("idntNum", "");
		PAGE.FORM1.setItemValue("contractNum", "");
		PAGE.FORM1.setItemValue("subStatusNm", "");
		PAGE.FORM1.setItemValue("lstComActvDate", "");
		PAGE.FORM1.setItemValue("dvcChgYn", "");
		PAGE.FORM1.setItemValue("dvcChgNm", "");
		PAGE.FORM1.setItemValue("dvcChgTp", "");
		PAGE.FORM1.setItemValue("dvcChgTpNm", "");
		//PAGE.FORM1.setItemValue("unpdAmnt", "");
		PAGE.FORM1.setItemValue("reqUsimSn", "");
		PAGE.FORM1.setItemValue("iccId", "");
		
		PAGE.FORM1.setItemValue("smsAuthInfo", "");
		PAGE.FORM1.setItemValue("otpCheckYN", "");
		
		if(PAGE.FORM1.getItemValue("onlineAuthType") == "S"){
			PAGE.FORM1.showItem("btnSendOtp");
			PAGE.FORM1.hideItem("btnOtpConfirm");
			PAGE.FORM1.hideItem("btnReSendOtp");
		}
		
		//v2018.02 기기변경 시 고객혜택 추가
		PAGE.FORM1.setItemValue("bnftName", "");
		PAGE.FORM1.setItemValue("bnftKey", "");
		
		// [SRM18120707112] KOS분실고객 기기변경 신청등록 시 SMS인증 Skip 및 녹취ID인증 메뉴 개발요청
		PAGE.FORM1.setItemValue("subStatus", "");
		PAGE.FORM1.setItemValue("subStatusRsnNm", "");
		PAGE.FORM1.setItemValue("dvcChgRecId", "");
		PAGE.FORM1.setItemValue("lostYn", "");
		// [SRM18120707112] KOS분실고객 기기변경 신청등록 시 SMS인증 Skip 및 녹취ID인증 메뉴 개발요청
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
	
	// 기기변경 권한
	/* function getDvcChngAuth(){
		if(dvcChgAuthYn != "Y"){
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2014",orderBy:"etc6"}, PAGE.FORM1, "operType"); //권한이 없는 경우 신청유형에 우수기변, 일반기변 제외
		}
	} */
	
	function dvcChngRegInit(operType){
		var dvcChgYn = PAGE.FORM1.getItemValue("dvcChgYn");

		// 2018-09-13, 본사가 아닌 경우 고객혜택 숨김
		if(typeCd != "10") {
			PAGE.FORM1.hideItem("BNFT");
		}
		
		if("MNP3" == operType || "NAC3" == operType){
			PAGE.FORM1.showItem("CSTMRINFO");
			PAGE.FORM1.showItem("PAYINFO");
			PAGE.FORM1.showItem("REQADD");
			
			PAGE.FORM1.showItem("onlineAuthInfo");
			PAGE.FORM1.showItem("managerCode");
			
			PAGE.FORM1.hideItem("smsAuthInfo");
			PAGE.FORM1.hideItem("btnSendOtp");
			PAGE.FORM1.hideItem("btnOtpConfirm");
			PAGE.FORM1.hideItem("btnReSendOtp");
			PAGE.FORM1.hideItem("smsAuthInfo");
			PAGE.FORM1.hideItem("btnSendOtp");
			PAGE.FORM1.hideItem("btnOtpConfirm");
			PAGE.FORM1.hideItem("btnReSendOtp");
			
			PAGE.FORM1.setItemValue("onOffType", "1");
			PAGE.FORM1.setItemValue("onlineAuthType","P");
			
			if(dvcChgYn == "Y"){
				PAGE.FORM1.setItemValue("cstmrTelFn", "");
				PAGE.FORM1.setItemValue("cstmrTelMn", "");
				PAGE.FORM1.setItemValue("cstmrTelRn", "");
				PAGE.FORM1.setItemValue("cstmrName", "");
				PAGE.FORM1.setItemValue("cstmrNativeRrn1", "");
				PAGE.FORM1.setItemValue("cstmrNativeRrn2", "");
			}
			
			PAGE.FORM1.enableItem("reqBuyType");
			
			form1Change(PAGE.FORM1, 0, "cstmrType");
			form1Change(PAGE.FORM1, 0, "reqPayType");
		}else{
			
			if(typeCd == "10"){
				PAGE.FORM1.setItemValue("onOffType", "4");
				PAGE.FORM1.setItemValue("onlineAuthType","S");
				
				PAGE.FORM1.showItem("smsAuthInfo");
				PAGE.FORM1.showItem("btnSendOtp");
				
				PAGE.FORM1.hideItem("onlineAuthInfo");
				PAGE.FORM1.hideItem("managerCode");
			}else{
				PAGE.FORM1.setItemValue("onOffType", "1");
				PAGE.FORM1.setItemValue("onlineAuthType","P")
				
				PAGE.FORM1.hideItem("smsAuthInfo");
				PAGE.FORM1.hideItem("btnSendOtp");
				
				PAGE.FORM1.showItem("onlineAuthInfo");
				PAGE.FORM1.showItem("managerCode");
			}
			
			PAGE.FORM1.setItemValue("reqPayType", "");
			
			form1Change(PAGE.FORM1, 1, "cstmrType");
			form1Change(PAGE.FORM1, 1, "reqPayType");
			
			PAGE.FORM1.setItemValue("cstmrTelFn", "");
			PAGE.FORM1.setItemValue("cstmrTelMn", "");
			PAGE.FORM1.setItemValue("cstmrTelRn", "");
			PAGE.FORM1.setItemValue("cstmrName", "");
			PAGE.FORM1.setItemValue("cstmrNativeRrn1", "");
			PAGE.FORM1.setItemValue("cstmrNativeRrn2", "");
			PAGE.FORM1.setItemValue("cstmrJejuId", "");
			PAGE.FORM1.setItemValue("cstmrPost", "");
			PAGE.FORM1.setItemValue("cstmrAddr", "");
			PAGE.FORM1.setItemValue("cstmrAddrDtl", "");
			PAGE.FORM1.setItemValue("cstmrMail1", "");
			PAGE.FORM1.setItemValue("cstmrMail2", "");
			
			PAGE.FORM1.showItem("DVCREG");
			
			PAGE.FORM1.hideItem("FOREIGNER");
			PAGE.FORM1.hideItem("BUSINESS");
			PAGE.FORM1.hideItem("MINORINFO");
			PAGE.FORM1.hideItem("CSTMRINFO");
			PAGE.FORM1.hideItem("PAYINFO");
			PAGE.FORM1.hideItem("REQADD");
			
			PAGE.FORM1.hideItem("dvcChgType");
			PAGE.FORM1.hideItem("dvcChgRsnCd");
			
			PAGE.FORM1.hideItem("btnOtpConfirm");
			PAGE.FORM1.hideItem("btnReSendOtp");
			
			PAGE.FORM1.setItemValue("reqBuyType", "MM");
			form1Change(PAGE.FORM1, 0, "reqBuyType");
			PAGE.FORM1.disableItem("reqBuyType");
			
		}
		
		//서식지함 메뉴를 통한 등록인 경우 인증방식은 고정
		if(menuId != "MSP1010100"){
			PAGE.FORM1.setItemValue("faxyn", "N");
			form1Change(PAGE.FORM1, 0, "faxyn");
		}
	}
	
	function appFormView(scanId){
		var DBMSType = "C";
		
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
		var encode = "";
		
		for(i=0; i<param.length; i++){
			var len  = "" + param.charCodeAt(i);
			var token = "" + len.length;
			
			encode  += token + param.charCodeAt(i);
		}
		
		return encode;
	}
	
	function lockscroll(lock){
		var eventName = "scroll.lockscroll";
		var $el = $(this);
		var pos = { x: $el.scrollLeft(), y: 0 };
		
		if(lock) {
			$("html, body").animate({scrollTop : 0}, 400);
			
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

		if(dvcChgAuthYn == "Y") {
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0049",orderBy:"etc1"}, PAGE.FORM1, "operType"); // 신청유형
		} else {
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2014",orderBy:"etc6"}, PAGE.FORM1, "operType"); // 신청유형
		}
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9007",etc1:"1",orderBy:"etc6"}, PAGE.FORM1, "cstmrType"); // 고객구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9004"}, PAGE.FORM1, "reqBuyType"); // 구매유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0005"}, PAGE.FORM1, "pstate"); // 신청서상태
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM1, "requestStateCode"); // 진행상태
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0063"}, PAGE.FORM1, "prodType"); // 상품구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2001",orderBy:"etc6"}, PAGE.FORM1, "onlineAuthType"); // 본인인증방식
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM1, "onOffType"); // 모집경로
		if (knoteYn == 'Y') {
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2002",orderBy:"etc6"}, PAGE.FORM1, "faxyn"); // 서식지
		} else {
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2002",orderBy:"etc6",etc1:knoteYn}, PAGE.FORM1, "faxyn"); // etc1 : "N" ==> k-note 서식지 미노출
		}
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2003"}, PAGE.FORM1, "faxnum1"); // 팩스번호
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2004"}, PAGE.FORM1, "idntCd"); // 기변 - 고객검색조건
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0051"}, PAGE.FORM1, "dvcChgRsnDtlCd"); // 기변상세사유
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0049"}, PAGE.FORM1, "dvcChgType"); // 기변유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0050"}, PAGE.FORM1, "dvcChgRsnCd"); // 기변사유
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0053"}, PAGE.FORM1, "agrmTrm"); // 약정기간
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2005"}, PAGE.FORM1, "instNom"); // 할부기간
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0058"}, PAGE.FORM1, "sprtTp"); // 할인유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0056"}, PAGE.FORM1, "usimPayMthdCd"); // USIM구매
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2006"}, PAGE.FORM1, "selfCertType"); // 본인인증방식
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM1, "cstmrTelFn"); // 연락처
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM1, "fathMobileFn"); // 안면인증연락처
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM1, "cstmrMobileFn"); // 고객휴대전화
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2008"}, PAGE.FORM1, "cstmrBillSendCode"); // 명세서수신유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2009",orderBy:"etc6"}, PAGE.FORM1, "cstmrMail3"); //
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM1, "dlvryTelFn"); // 전화번호
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM1, "dlvryMobileFn"); // 휴대폰
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0025"}, PAGE.FORM1, "tbCd"); // 택배사
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9003"}, PAGE.FORM1, "minorAgentRelation"); // 관계
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2007"}, PAGE.FORM1, "minorAgentTelFn"); // 연락처
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2006"}, PAGE.FORM1, "minorSelfCertType"); // 인증방식
		mvno.cmn.ajax4lov( ROOT + "/cmn/payinfo/getReqCardComboList.json", "", PAGE.FORM1, "reqCardCompany"); // 카드정보
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0048"}, PAGE.FORM1, "joinPayMthdCd"); // 가입비
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM1, "reqGuideFn"); //
		//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM1, "moveMobileFn"); // 이동번호
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"NSC"}, PAGE.FORM1, "moveCompany"); // 이동전통신사
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2011"}, PAGE.FORM1, "moveAuthType"); // 인증유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2012",orderBy:"etc6"}, PAGE.FORM1, "moveThismonthPayType"); // 번호이동전 사용요금
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2013",orderBy:"etc6"}, PAGE.FORM1, "moveAllotmentStat"); // 휴대폰 할부금
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9006",orderBy:"etc6"}, PAGE.FORM1, "appCd"); // 유해정보차단APP
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2035" , etc1:"1"}, PAGE.FORM1, "usimKindsCd"); // 유심종류
		// SRM18072675707, 단체보험
		mvno.cmn.ajax4lov( ROOT + "/ptnr/grpinsrmgmt/getGrpInsrCombo.json", "", PAGE.FORM1, "insrCd");
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9011"}, PAGE.FORM1, "recommendFlagCd"); // 추천인구분
		
		// 2020.12.14 VISA CODE 추가
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2051"}, PAGE.FORM1, "visaCd"); // VISA CODE
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM1, "phoneStateCd"); // 자급제
		// 20230216 모회선 추가
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2010"}, PAGE.FORM1, "prntsCtnFn"); // 모회선
		// 20230718 자급제 보상 부가서비스 추가
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0258", orderBy:"etc5"}, PAGE.FORM1, "rwdProdCd");	// 보상서비스 상품
		// 20250304 요금제 제휴처
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"JehuProdType"}, PAGE.FORM1, "jehuProdType");

		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2038"}, PAGE.FORM1, "cstmrForeignerNationSel"); // 국적

		// 신청서 수정 내역을 확인하기 위한 데이터 추출.
		//chkMcpRequest();
		
		menuId = "${param.trgtMenuId}";
		
		if(menuId == "MSP1000015" || menuId == "MSP1006002" || menuId == "MSP1010089" || menuId == "MSP2002201" || menuId == "MSP1000018"){	//신청관리 New or 신청관리(상조업무) or A'Cen 자동개통 통계 or 신청관리(New)
			if("${param.requestKey}" != ""){
				PAGE.FORM1.setItemValue("requestKey", "${param.requestKey}");
				
				form1ReadInit();
				
				if(menuId == "MSP1006002"){	//신청관리(상조업무) 새로작성 버튼 숨김
					mvno.ui.hideButton("FORM1" , "btnNew");
				}
				
			}else{
				form1RegInit();
				
				//getDvcChngAuth();	//기기변경 권한 체크
			}
		}else if(menuId == "MSP1003020"){	//기기변경TM
			form1RegInit();
			
			//getDvcChngAuth();	//기기변경 권한 체크
			
			if(dvcChgAuthYn == "Y"){
				if("${param.custNm}" != "" && "${param.subscriberNo}" != ""){
					
					PAGE.FORM1.setItemValue("operType", "HDN3");
					
					form1Change(PAGE.FORM1, 1, "operType");
					
					PAGE.FORM1.setItemValue("custNm", "${param.custNm}");
					PAGE.FORM1.setItemValue("subscriberNo", "${param.subscriberNo}");
				}
			}
			
		}else if(menuId == "MSP1010100"){	//서식지함
			form1RegInit();
			
			//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2014",orderBy:"etc6"}, PAGE.FORM1, "operType"); // 신청유형
			
			PAGE.FORM1.setItemValue("scanId", "${param.scanId}");
			PAGE.FORM1.setItemValue("shopCd", "${param.shopCd}");
			PAGE.FORM1.setItemValue("shopNm", "${param.shopCd}" + "/" + fncDeCode("${param.shopNm}"));
			PAGE.FORM1.setItemValue("resNo", "${param.resNo}");
			
			PAGE.FORM1.hideItem("faxyn");
			PAGE.FORM1.hideItem("btnRegPaper");
			PAGE.FORM1.hideItem("btnScnSarch");
			PAGE.FORM1.hideItem("btnRecSarch");
			
			PAGE.FORM1.showItem("btnPaper");

			if(PAGE.FORM1.getItemValue("reqbuyType") == "MM") PAGE.FORM1.hideItem("dlvryType");
			
			//기기변경 권한 체크
			//getDvcChngAuth();
		}else if(menuId == "MSP1000099"){	//개통관리
			if("${param.requestKey}" != ""){
				PAGE.FORM1.setItemValue("requestKey", "${param.requestKey}");

				form1OpenInit();
				
			}else{
				form1RegInit();
				
				//getDvcChngAuth();	//기기변경 권한 체크
			}
		}else{
			form1RegInit();
			
			//getDvcChngAuth();	//기기변경 권한 체크
		}
		
		if("${authRealShopYn}" == "N") {
			PAGE.FORM1.hideItem("realShopNm");
		}
		
		var loading = document.all.LOADING;
		loading.style.display = "none";
	}
	
	function getRntlPrdtInfo(itemName){
		
		var params = {
				"orgnId":PAGE.FORM1.getItemValue("cntpntShopId")
				,"baseYm":new Date(PAGE.FORM1.getItemValue("reqInDay")).format("yyyymm")
			};
		
		mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRntlPrdtInfo.json", params, function(resultData) {
			var totalCount = Number(resultData.result.data.rows.length);
			var rntlPrdcData = "";
			
			if(totalCount > 0){
				
				rntlPrdtInfo = resultData.result.data;
				
				rntlPrdcData = [{text:"- 선택 -", value:""}];
				
				for(var idx = 0; idx < totalCount; idx++){
					var prdtNm = resultData.result.data.rows[idx].prdtNm;
					var prdtCode = resultData.result.data.rows[idx].prdtCode;
					var data = {text:prdtNm +"["+prdtCode+"]", value:idx};
					
					rntlPrdcData.push(data);
				};
	 			
			}else{
				rntlPrdcData = [{text:"- 없음 -", value:""}];
			}
			
			PAGE.FORM1.reloadOptions(itemName, rntlPrdcData);
 		});
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
	
	function initPrmtInfo(){
		if(mvno.cmn.isEmpty(PAGE.FORM1.getItemValue("promotionCd"))){
			return;
		}
		
		if( (PAGE.FORM1.getItemValue("prmtAgrmTrm") != PAGE.FORM1.getItemValue("agrmTrm")) ||
			(PAGE.FORM1.getItemValue("prmtOperType") != PAGE.FORM1.getItemValue("operType")) ||
			(PAGE.FORM1.getItemValue("prmtRateCd") != PAGE.FORM1.getItemValue("socCode")) ){
			
			mvno.ui.alert("프로모션 정책을 다시 선택 하세요.");
			
			PAGE.FORM1.setItemValue("promotionCd", "");
			PAGE.FORM1.setItemValue("promotionNm", "");
			PAGE.FORM1.setItemValue("benefitCmmt", "");
			PAGE.FORM1.setItemValue("prmtAgrmTrm", "");
			PAGE.FORM1.setItemValue("prmtOperType", "");
			PAGE.FORM1.setItemValue("prmtRateCd", "");
		}
	}
	
	function getDisPrmtListCombo(form, useYn){

		form.setItemValue("disPrmtId", "");
		form.setItemValue("disPrmtNm", "");
		form.setItemValue("totalDiscount", "");

		if(mvno.cmn.isEmpty(form.getItemValue("operType")) || mvno.cmn.isEmpty(form.getItemValue("spcCode")) || form.getItemValue("spcCode")==-1
		|| mvno.cmn.isEmpty(form.getItemValue("agrmTrm")) || mvno.cmn.isEmpty(form.getItemValue("pAgentCode")) || mvno.cmn.isEmpty(form.getItemValue("socCode"))){
			return;
		}

		var data = {
				operType : form.getItemValue("operType")
 				,slsTp :  form.getItemValue("spcCode")
				,reqBuyType : form.getItemValue("reqBuyType")
				,ModelId : form.getItemValue("prdtId")
				,enggSrl : form.getItemValue("agrmTrm")
				,orgnId : form.getItemValue("pAgentCode")
				,rateCd : form.getItemValue("socCode")
		}
		mvno.cmn.ajax(ROOT + "/org/prmtmgmt/getDisPrmtListCombo.json", data, function(result) {
			var data = result.result.prmtMap
			if(result.result.code == "OK"){
				form.setItemValue("disPrmtId",data.prmtId);
				form.setItemValue("disPrmtNm",data.prmtNm);
				form.setItemValue("totalDiscount",data.disCnt);
			}else{
				form.setItemValue("disPrmtId", "");
				form.setItemValue("disPrmtNm", "");
				form.setItemValue("totalDiscount", "");
			}
		}, {
			async: false
		});
	};

	function getKtPlcyListCombo(form, useYn){
		var plcyType;

		//상품구분이 렌탈인 경우 구매유형은 유심으로 설정
		if(form.getItemValue("prodType") == "02"){
			plcyType = "UU";
		}else{
			if(form.getItemValue("reqBuyType") == "UU" && form.getItemValue("usimKindsCd") == "09"){ //eSIM의 경우 특별판매코드 따로 가져오기 
				plcyType = "03"
			}else{
				plcyType = form.getItemValue("reqBuyType");	
				}
		}
		
		if(mvno.cmn.isEmpty(form.getItemValue("serviceType")) || mvno.cmn.isEmpty(form.getItemValue("reqBuyType")) || mvno.cmn.isEmpty(form.getItemValue("sprtTp"))
				|| mvno.cmn.isEmpty(form.getItemValue("operType")) || mvno.cmn.isEmpty(form.getItemValue("instNom")) || mvno.cmn.isEmpty(form.getItemValue("agrmTrm"))){
			form.setItemValue("spcCode", "");
			if(plcyType == "MM"){
				return;
			}else{
				if(mvno.cmn.isEmpty(form.getItemValue("instNom")) || mvno.cmn.isEmpty(form.getItemValue("agrmTrm"))){
					return;
				}
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
		
		if(form.getItemValue("reqBuyType") == "MM"){
			if(form.getItemValue("agrmTrm") < 1 && form.getItemValue("instNom") > 0){
				
				mvno.ui.alert("단말할부, 무약정인 경우 할인이 적용되지 않습니다.");
				
				form.setItemValue("sprtTp", "");
			}
		}
		
	}
	
	// 2018.06.30 판매정책 제외로 단말판매정보 조회
	function getSaleinfo(form, name){
		console.log("resNo=" + form.getItemValue("resNo"));
		
		// 수정인 경우 구매유형 변경시 판매정보 활성화 처리
		if(form.getItemValue("resNo") != "" && name == "reqBuyType") {
			form.enableItem("socCode");
			form.enableItem("agrmTrm");
			form.enableItem("instNom");
			form.enableItem("mnfctId");
			form.enableItem("prdtId");
			form.enableItem("sprtTp");
			// 값 초기화
			form.setItemValue("agrmTrm", "");
			form.setItemValue("instNom", "");
			form.setItemValue("mnfctId", "");
			form.setItemValue("reqModelColor", "");
			form.setItemValue("sprtTp", "");
			form.setItemValue("modelInstallment", "");
			form.reloadOptions("spcCode", {text:"", value:""});
		}
		
		if(form.getItemValue("reqBuyType") == "MM" &&
			(mvno.cmn.isEmpty(form.getItemValue("serviceType")) || mvno.cmn.isEmpty(form.getItemValue("reqBuyType")) || mvno.cmn.isEmpty(form.getItemValue("sprtTp"))
			|| mvno.cmn.isEmpty(form.getItemValue("socCode")) || mvno.cmn.isEmpty(form.getItemValue("operType")) || mvno.cmn.isEmpty(form.getItemValue("prdtId"))
			|| mvno.cmn.isEmpty(form.getItemValue("agrmTrm")))){
				form.setItemValue("modelId", "");
				form.setItemValue("recycleYn", "");
				form.setItemValue("modelPrice", "");
				form.setItemValue("modelPriceVat", "");
				form.setItemValue("hndstAmt", "");
				form.setItemValue("modelDiscount2", "");
				form.setItemValue("dcAmt", "");
				form.setItemValue("addDcAmt", "");
				form.setItemValue("prdtCode", "");
				form.setItemValue("reqUsimName", "");
				
				return;
		}
		
		if(form.getItemValue("reqBuyType") == "UU" &&
			(mvno.cmn.isEmpty(form.getItemValue("serviceType")) || mvno.cmn.isEmpty(form.getItemValue("reqBuyType")) || mvno.cmn.isEmpty(form.getItemValue("agrmTrm"))
			|| mvno.cmn.isEmpty(form.getItemValue("socCode")) || mvno.cmn.isEmpty(form.getItemValue("operType")) || mvno.cmn.isEmpty(form.getItemValue("prdtId")))){
				form.setItemValue("modelId", "");
				form.setItemValue("recycleYn", "");
				form.setItemValue("modelPrice", "");
				form.setItemValue("modelPriceVat", "");
				form.setItemValue("hndstAmt", "");
				form.setItemValue("modelDiscount2", "");
				form.setItemValue("dcAmt", "");
				form.setItemValue("addDcAmt", "");
				form.setItemValue("prdtCode", "");
				form.setItemValue("reqUsimName", "");
				
				return;
		}
		
		var sdata = {
			operType : form.getItemValue("operType")
			,agrmTrm : form.getItemValue("agrmTrm")
			,socCode : form.getItemValue("socCode")
			,sprtTp : form.getItemValue("sprtTp")
			,prdtId : form.getItemValue("prdtId")
			,reqBuyType : form.getItemValue("reqBuyType")
			,reqInDay : form.getItemValue("reqInDayStr")
		}
		
		mvno.cmn.ajax(ROOT + "/rcp/rcpNewMgmt/getSaleinfo.json", sdata, function(result) {
			
			var data = result.result.data.rows[0];
			
			if(data != null){
				console.log("data=" + data.rprsPrdtId);
				form.setItemValue("modelId", data.rprsPrdtId);
				form.setItemValue("recycleYn", data.oldYn);
				form.setItemValue("modelPrice", data.modelPrice);
				form.setItemValue("modelPriceVat", data.modelPriceVat);
				form.setItemValue("hndstAmt", data.hndstAmt);
				form.setItemValue("modelDiscount2", data.subsdAmt);
				form.setItemValue("dcAmt", data.dcAmt);
				form.setItemValue("addDcAmt", data.addDcAmt);
				form.setItemValue("modelInstallment", data.modelInstallment);
				form.setItemValue("joinPrice", data.joinPrice);
				form.setItemValue("usimPrice", data.usimPrice);
				
				if(form.getItemValue("operType") == "UU"){
					form.setItemValue("reqUsimName", data.prdtCode);
				}else{
					form.setItemValue("prdtCode", data.prdtCode);
				}
			}
			
		}, {async: false});
	}
	
	function fncDeCode(param){
		var sb = "";
		var pos = 0;
		var flg = true;

		if(param != null && param.length>1){
			while(flg){
				var sLen = param.substring(pos,++pos);
				var nLen = 0;
				try{
					nLen = parseInt(sLen);
				}catch(e){
					nLen = 0;
				}
					
				var code = "";
				
				if((pos+nLen)>param.length){
					code = param.substring(pos);
				}else{
					code = param.substring(pos,(pos+nLen));
				}
				pos  += nLen;
				sb += String.fromCharCode(code);
					
				if(pos >= param.length) flg = false;
			}
		}
		
		return sb;
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
		var strMenuId = "${param.trgtMenuId}";
		if (strRequestKey == null || strRequestKey == '' || strMenuId == null || strMenuId == '') {
			return;
		}
		
		mvno.cmn.ajax2(ROOT + "/gift/custgmt/custGiftPrmtPrdtResultList.json", {requestKey: strRequestKey, menuId: strMenuId}, function(resultData) {
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

	// 알뜰폰허브 주문정보 초기화
	function initHubOrder() {
		var form = PAGE.FORM1;

		form.setItemValue("operType",			"NAC3");
		form.setItemValue("cstmrType",			"NA");
		form.setItemValue("reqBuyType",			"MM");
		form.setItemValue("onlineAuthType",		"");

		form.setItemValue("agrmTrm",			"");
		form.setItemValue("instNom",			"");
		form.setItemValue("hndstAmt",			"");
		form.setItemValue("sprtTp",				"");
		form.setItemValue("modelDiscount2",		"");
		form.setItemValue("modelDiscount3",		"");
		form.setItemValue("modelInstallment",	"");

		form.setItemValue("cstmrName",			"");
		form.setItemValue("cstmrNativeRrn1",	"");
		form.setItemValue("cstmrNativeRrn2",	"");
		form.setItemValue("selfCstmrCi",		"");
		form.setItemValue("selfInqryAgrmYn",	"");
		form.setItemValue("selfCertType",		"");
		form.setItemValue("selfIssuExprDt",		"");
		form.setItemValue("cstmrTelFn",			"");
		form.setItemValue("cstmrTelMn",			"");
		form.setItemValue("cstmrTelRn",			"");
		form.setItemValue("cstmrPost",			"");
		form.setItemValue("cstmrAddr",			"");
		form.setItemValue("cstmrAddrDtl",		"");
		form.setItemValue("cstmrBillSendCode",	"");
		form.setItemValue("cstmrMail1",			"");
		form.setItemValue("cstmrMail2",			"");
		form.setItemValue("cstmrMail3",			"");

		form.setItemValue("dlvryName",			"");
		form.setItemValue("dlvryTelFn",			"");
		form.setItemValue("dlvryTelMn", 		"");
		form.setItemValue("dlvryTelRn",			"");
		form.setItemValue("dlvryMobileFn",		"");
		form.setItemValue("dlvryMobileMn",		"");
		form.setItemValue("dlvryMobileRn",		"");
		form.setItemValue("dlvryPost",			"");
		form.setItemValue("dlvryAddr",			"");
		form.setItemValue("dlvryAddrDtl",		"");
		form.setItemValue("dlvryMemo",			"");
		form.setItemValue("dlvryYn",			"");

		form.setItemValue("reqPayType",			"");

		form.setItemValue("appCd",				"");

		form1Change(form, 1, "operType");
		form1Change(form, 1, "cstmrType");
		form1Change(form, 1, "selfCertType");
		form1Change(form, 1, "dlvryYn");
		form1Change(form, 1, "reqPayType");
	}

	// 알뜰폰허브 주문정보 매핑
	function setHubOrder(result) {
		var form = PAGE.FORM1;

		/* 신청서정보 시작 */
		var entrType = result.entrType;	// 신청유형
		switch (entrType) {
			case "01": // 신규
				form.setItemValue("operType", "NAC3");
				break;
			case "03": // 번호이동
				form.setItemValue("operType", "MNP3");
				break;
		}
		form1Change(form, 0, "operType");

		var customerType = result.customerType;	// 고객구분
		switch (customerType) {
			case "G": // 내국인
				form.setItemValue("cstmrType", "NA");
				break;
			case "J": // 내국인 미성년자
				form.setItemValue("cstmrType", "NM");
				break;
			case "F": // 외국인
				form.setItemValue("cstmrType", "FN");
				break;
		}
		form1Change(form, 0, "cstmrType");

		var prdtDiv = result.prdtDiv;	// 구매유형
		switch (prdtDiv) {
			case "P": // 단말구매
				form.setItemValue("reqBuyType", "MM");
				form1Change(form, 1, "reqBuyType");
				form.setItemValue("sprtTp", "KD");	// 할인유형
				break;
			case "U": // 유심구매
				form.setItemValue("reqBuyType", "UU");
				form1Change(form, 1, "reqBuyType");
				break;
		}

		var identityMethod = result.identityMethod;	// 본인인증방식
		switch (identityMethod) {
			case "C":	// 신용카드
			case "K":	// 카카오
			case "T": 	// 토스
			case "S": 	// 본인인증
				form.setItemValue("onlineAuthType", identityMethod);
				break;
			case "P":	// 패스
				form.setItemValue("onlineAuthType", "A");
				break;
			default:	// 기타
				form.setItemValue("onlineAuthType", "ETC");
				break;
		}
		form.disableItem("onlineAuthType");	// 본인인증방식 disabled 처리
		/* 신청서정보 종료 */

		/* 판매정보 시작 */
		form.setItemValue("agrmTrm", result.contractMmCnt);				// 약정기간
		form.setItemValue("instNom", result.instmntMmCnt);				// 할부기간
		form.setItemValue("hndstAmt", result.modelPrce);				// 출고가
		form.setItemValue("modelDiscount2", result.modelSuprtPrce);		// 공시지원금
		form.setItemValue("modelDiscount3", result.modelAddSuprtPrce);	// 추가지원금
		form.setItemValue("modelInstallment", result.instmntOrgPrce);	// 할부원금
		/* 판매정보 종료 */

		/* 고객정보 시작 */
		form.setItemValue("cstmrName", result.orderNm);	// 고객명

		if (result.userSsn) {
			var userSsn = result.userSsn.split("-");	// 주민번호
			if (!(userSsn.length < 2)) {
				form.setItemValue("cstmrNativeRrn1", userSsn[0]);
				form.setItemValue("cstmrNativeRrn2", userSsn[1]);
			}
		}

		var disAuthJoinDiv = result.disAuthJoinDiv;	// 본인인증방식
		switch (disAuthJoinDiv) {
			case "03":	// 여권
				disAuthJoinDiv = "05";
				break;
			case "04":	// 외국인등록증
				disAuthJoinDiv = "06";
				break;
			default:
				break;
		}
		var authOrgDt = result.authOrgDt;															// 발급일자
		var disAuthJoinNum = result.disAuthJoinNum ? result.disAuthJoinNum.replace(/-/g, "") : "";	// 발급번호
		var userCI = result.userCI;						// CI
		var guardCI = result.guardCI;					// 법정대리인 CI
		if (customerType != "J") {
			form.setItemValue("selfCstmrCi", userCI);	// 내국인 CI

			if(userCI) {
				form.setItemValue("selfInqryAgrmYn", "1");			// 본인조회동의
			}

			form.setItemValue("selfCertType", disAuthJoinDiv);		// 본인인증방식
			form.setItemValue("selfIssuExprDt", authOrgDt);			// 발급일자 or 만료일자
			if(disAuthJoinDiv == "02") {
				form.setItemValue("selfIssuNum", disAuthJoinNum);	// 발급번호
			}
			form1Change(form, 1, "selfCertType");
		} else {
			form.setItemValue("selfCstmrCi", guardCI);	// 법정대리인 CI
		}

		var cstmrTel = ["cstmrTelFn", "cstmrTelMn", "cstmrTelRn"];	// 연락처
		setPhoneNumSplit(form, cstmrTel, result.orderPhone);

		form.setItemValue("cstmrPost", result.orderZipCd);			// 우편번호
		form.setItemValue("cstmrAddr", result.orderAddr1);			// 주소
		form.setItemValue("cstmrAddrDtl", result.orderAddr2);		// 상세주소

		var billCd = result.billCd;	// 명세서수신유형
		switch (billCd) {
			case "01":	// 우편
				form.setItemValue("cstmrBillSendCode", "LX");
				break;
			case "02":	// 메일
				form.setItemValue("cstmrBillSendCode", "CB");
				break;
			case "03":	// 모바일
				form.setItemValue("cstmrBillSendCode", "MB");
				break;
			case "04":	// 해피콜에서 확인
				break;
		}

		// 이메일
		if (result.orderEmail) {
			var orderEmail = result.orderEmail.split("@");
			if (!(orderEmail.length < 2)) {
				form.setItemValue("cstmrMail1", orderEmail[0]);
				form.setItemValue("cstmrMail2", orderEmail[1]);
				form.setItemValue("cstmrMail3", "직접입력");
			}
		}
		/* 고객정보 종료 */

		/* 배송정보 시작 */
		form.setItemValue("dlvryName", result.recvNm);	// 받으시는 분

		var dlvryTel = ["dlvryTelFn", "dlvryTelMn", "dlvryTelRn"];	// 전화번호
		setPhoneNumSplit(form, dlvryTel, result.recvTel);

		var dlvryMobile = ["dlvryMobileFn", "dlvryMobileMn", "dlvryMobileRn"];	// 휴대폰번호
		setPhoneNumSplit(form, dlvryMobile, result.recvPhone);

		form.setItemValue("dlvryPost", result.recvZipCd);		// 우편번호
		form.setItemValue("dlvryAddr", result.recvAddr1);		// 배송주소
		form.setItemValue("dlvryAddrDtl", result.recvAddr2);	// 상세주소
		form.setItemValue("dlvryMemo", result.tranReqCont);		// 요청사항

		var dlvryFields = ["dlvryName", "dlvryTelFn", "dlvryTelMn", "dlvryTelRn", "dlvryMobileFn", "dlvryMobileMn", "dlvryMobileRn", "dlvryPost", "dlvryAddr", "dlvryAddrDtl", "dlvryMemo"];
		for (i = 0; i < dlvryFields.length; i++) {
			var dlvryField = form.getItemValue(dlvryFields[i]);
			if (dlvryField) {
				form.setItemValue("dlvryYn", "1");
				break;
			}
		}
		form1Change(form, 1, "dlvryYn");
		/* 배송정보 종료 */

		/* 법정대리인 시작 */
		if (customerType == "J") {
			form.setItemValue("minorAgentName", result.guardNm);		// 대리인성명

			if (result.guardSsn) {
				var guardSsn = result.guardSsn.split("-");				// 대리인주민번호
				if (!(guardSsn.length < 2)) {
					form.setItemValue("minorAgentRrn1", guardSsn[0]);
					form.setItemValue("minorAgentRrn2", guardSsn[1]);
				}
			}

			var minorAgentTel = ["minorAgentTelFn", "minorAgentTelMn", "minorAgentTelRn"];	// 연락처(=법정대리인)
			setPhoneNumSplit(form, minorAgentTel, result.orderPhone);

			if(guardCI) {
				form.setItemValue("minorSelfInqryAgrmYn", "1");			// 본인조회동의
			}

			form.setItemValue("minorSelfCertType", disAuthJoinDiv);		// 본인인증방식
			form.setItemValue("minorSelfIssuExprDt", authOrgDt);		// 발급일자 or 만료일자
			if(disAuthJoinDiv == "02") {
				form.setItemValue("minorSelfIssuNum", disAuthJoinNum);	// 발급번호
			}
			form1Change(form, 1, "minorSelfCertType");
		}
		/* 법정대리인 종료 */

		/* 요금납부정보 시작 */
		var settleMethod = result.settleMethod;	// 요금납부방법

		switch (settleMethod) {
			case "01": 	// 자동이체
				form.setItemValue("reqPayType", "D");
				break;
			case "02":	// 신용카드
				form.setItemValue("reqPayType", "C");
				break;
		}
		form1Change(form, 0, "reqPayType");
		/* 요금납부정보 종료 */

		/* 은행정보 시작 */
		if (settleMethod == "01") {
			form.setItemValue("reqAccountName", result.ownerNm);		// 예금주
			form.setItemValue("reqBank", result.bankCd);				// 은행코드
			form.setItemValue("reqAccountNumber", result.bankAcctNo);	// 계좌번호
		}
		/* 은행정보 종료 */

		/* 카드정보 시작 */
		if (settleMethod == "02") {
			var cardNum = result.cardNum;	// 카드정보
			if (cardNum) {
				if (!(cardNum.length < 16)) {
					form.setItemValue("reqCardNo1", cardNum.substring(0, 4));
					form.setItemValue("reqCardNo2", cardNum.substring(4, 8));
					form.setItemValue("reqCardNo3", cardNum.substring(8, 12));
					form.setItemValue("reqCardNo4", cardNum.substring(12, 16));
				}
			}

			var cardValidDt = result.cardValidDt;	// 카드 유효기간
			if (cardValidDt) {
				if (!(cardValidDt.length < 4)) {
					form.setItemValue("reqCardMm", cardValidDt.substring(0, 2));
					form.setItemValue("reqCardYy", "20"+cardValidDt.substring(2, 4));
				}
			}

			form.setItemValue("reqCardName", result.ownerNm);	// 명의자정보
		}
		/* 카드정보 종료 */

		/* 신규가입 시작 */
		form.setItemValue("reqWantNumber", result.newPhoneNum1);	// 가입희망번호1
		form.setItemValue("reqWantNumber2", result.newPhoneNum2);	// 가입희망번호2
		/* 신규가입 종료 */

		/* 번호이동정보 시작 */
		var moveMobile = ["moveMobileFn", "moveMobileMn", "moveMobileRn"];	// 이동번호
		setPhoneNumSplit(form, moveMobile,  result.prePhoneNum);

		var preComuCd = result.preComuCd;	// 이동전통신사
		switch (preComuCd) {
			case "KT":	// KT
				form.setItemValue("moveCompany", "KTF");
				break;
			case "LG": 	// LGU+
				form.setItemValue("moveCompany", "LGT");
				break;
			case "SK":	// SKT
				form.setItemValue("moveCompany", "SKT");
				break;
		}
		/* 번호이동정보 종료 */

		/* 약관동의정보 시작 */
		// for (i = 0; i < result.agrees.length; i++) {
		// 	var agreeSortNum = result.agrees[i].agreeSortNum;
		// 	switch (agreeSortNum) {
		// 		case "5":	// 개인정보 수집이용 동의(혜택)
		// 			form.setItemValue("personalInfoCollectAgree", result.agrees[i].agreeYn);
		// 			break;
		// 		case "6":	// 정보/광고 수신 위탁 동의
		// 			form.setItemValue("clausePriAdFlag", result.agrees[i].agreeYn);
		// 			break;
		// 		case "7":	// 제 3자 제공 동의
		// 			form.setItemValue("othersTrnsAgree", result.agrees[i].agreeYn);
		// 			break;
		// 	}
		// }

		if (customerType == "J") {
			form.setItemValue("appCd", "15");	// 엑스키퍼가드
		}
		/* 약관동의정보 종료 */
	}

	function setPhoneNumSplit(form, fields, phoneNum) {
		if (!fields || fields.length != 3)
			return;

		if (phoneNum)
			phoneNum.replace(/-/g, "");

		if (phoneNum) {
			if (phoneNum.startsWith("02")) {
				if (phoneNum.length == 9) {
					form.setItemValue(fields[0], phoneNum.substring(0, 2));
					form.setItemValue(fields[1], phoneNum.substring(2, 5));
					form.setItemValue(fields[2], phoneNum.substring(5, 9));
				} else if (phoneNum.length == 10) {
					form.setItemValue(fields[0], phoneNum.substring(0, 2));
					form.setItemValue(fields[1], phoneNum.substring(2, 6));
					form.setItemValue(fields[2], phoneNum.substring(6, 10));
				}
			} else {
				if (phoneNum.length == 10) {
					form.setItemValue(fields[0], phoneNum.substring(0, 3));
					form.setItemValue(fields[1], phoneNum.substring(3, 6));
					form.setItemValue(fields[2], phoneNum.substring(6, 10));
				} else if (phoneNum.length == 11) {
					form.setItemValue(fields[0], phoneNum.substring(0, 3));
					form.setItemValue(fields[1], phoneNum.substring(3, 7));
					form.setItemValue(fields[2], phoneNum.substring(7, 11));
				}
			}
		}
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
		var pAgentCode = form.getItemValue("pAgentCode");
		var isJehuFlagChk = form.isItemChecked("clauseJehuFlag") ? "Y" : "N";
		var isPartnerFlagChk = form.isItemChecked("clausePartnerOfferFlag") ? "Y" : "N";

		/*
		if(mvno.cmn.isEmpty(pAgentCode)){
			form.pushError("pAgentCode", "대리점", "필수 입력 항목입니다");
			return false;
		}

		if(mvno.cmn.isEmpty(socCode)){
			return false;
		}
		*/

		var reqParam = {socCode, pAgentCode, isJehuFlagChk, isPartnerFlagChk};

		mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/termsValidationCheck.json", reqParam, function(resultData) {

			var jehuChkMap = resultData.result.jehuChkMap;
			var partnerChkMap = resultData.result.partnerChkMap;

			// 제휴처 약관 동의 필수
			if(jehuChkMap.resultCd != "00"){

				// 기존 신청서 고려
				if(form._idIndex.clauseJehuFlag.enabled){
					isValidate= false;
					form.pushError("clauseJehuFlag", "제휴서비스를 위한동의", jehuChkMap.resultMsg);
				}
			}

			// 제휴사 약관 동의 필수
			if(partnerChkMap.resultCd != "00"){

				// 기존 신청서 고려
				if(form._idIndex.clausePartnerOfferFlag.enabled){
					isValidate= false;
					form.pushError("clausePartnerOfferFlag", "제휴사 제공 동의", partnerChkMap.resultMsg);
				}
			}

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
				syncCombineSoloAddition(PAGE.FORM1.getItemValue("combineSoloType"));
			}
		},{async:false});
	}

</script>