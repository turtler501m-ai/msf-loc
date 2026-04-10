<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
/**
 * @Class Name : rcpMgmt.jsp
 * @Description : 신청 관리 화면
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015.01.28 장익준 최초생성
 *   2018.03.28 이상직 불필요한코드정리
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

var maskingYn = '${maskingYn}';

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

// 삭제권한 ( 본사사용자의 경우 Y )
var delAuthYn = "N";
var userOrgnTypeCd = '${loginInfo.userOrgnTypeCd}';
if(userOrgnTypeCd == "10") {
	delAuthYn = "Y";
}

// 스캔관련
var blackMakingYn = "Y";					// 블랙마킹사용권한
var blackMakingFlag = "N";					// 블랙마킹해제권한 (마스킹권한여부에 따라 변화)
var maskingYn = "${maskingYn}";				// 마스킹권한
if(maskingYn == "Y") {
	blackMakingFlag = "Y";
}
var agentVersion = "${agentVersion}";		// 스캔버전
var serverUrl = '${serverUrl}';				// 서버환경 (로컬 : L, 개발 : D, 스테이징 : S, 운영 : R)
//var scanSearchUrl = "${scanSearchUrl}";	// 스캔호출 url
var scanSearchUrl = "${scanSearchUrlT}";	// 스캔호출 url
var scanDownloadUrl = "${scanDownloadUrl}";	// 스캔파일 download url

var DHX = {
	FORM1 : {
		items : [
			{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
			, {type:"block", list:[
				{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchStartDt', label: '신청일자', value: "${startDate}", calendarPosition: 'bottom', readonly:true ,width:100,offsetLeft:10}
				, {type: 'newcolumn'}
				, {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', value: "${endDate}", calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
				, {type: 'newcolumn'}
				, {type:"select", width:100, offsetLeft:55, label:"서비스구분",name:"pServiceType"}
				, {type: 'newcolumn'}
				, {type:"select", width:100, offsetLeft:20, label:"업무구분",name:"pOperType"}
				,{type : "newcolumn"}
				,{type : "checkbox", label : "eSIM여부", width : 40, name : "esimYn", labelWidth : 80, offsetLeft : 20, value : "Y"}
			]}
			, {type:"block", list:[
				{type:"input", label:"대리점", name: "pAgentCode",value:sOrgnId, width:100,readonly: reqFlag, offsetLeft:10}
				, {type:"newcolumn"}
				, {type:"button", value:"찾기", name:"btnOrgFind",disabled:false}
				, {type:"newcolumn"}
				, {type:"input", name: "pAgentName",value:sOrgnNm, width:100,readonly: true}
				, {type:"newcolumn"}
				, {type:"select", width:100, offsetLeft:20, label:"진행상태",name:"pRequestStateCode"}
				, {type:"newcolumn"}
				, {type:"select", width:100,offsetLeft:20, label:"신청취소",name:"pPstate"}
				, {type : "newcolumn"}
                , {type : "checkbox", label : "A'CEN여부", width : 40, name : "acenYn", labelWidth : 80, offsetLeft : 20, value : "Y"}
			]}
			, {type:"block", list:[
				{type:"select", width:100, label:"검색구분",name:"pSearchGbn", offsetLeft:10}
				, {type:"newcolumn"}
				, {type:"input", width:120, name:"pSearchName"}
				, {type:"newcolumn"}
				, {type:"select", width:100, offsetLeft:50, label:"구매유형",name:"reqBuyType"}
				, {type:"newcolumn"}
				, {type:"select", width:100, offsetLeft:20, label:"할인유형",name:"sprtTp"}
				,{type : "newcolumn"}
                ,{type : "checkbox", label : "셀프개통 제외", width : 40, name : "selfYn", labelWidth : 80, offsetLeft : 20, value : "Y"}
			]}
			, {type:"block", list:[
				{type:"select", width:100 ,name:"pBirthDay", offsetLeft:70, options:[{text:"생년월일", value:"birthDay"}], disabled:true}
				, {type:"newcolumn"}
				, {type:"input", name: "pBirthDayVal", width:120, disabled:true, maxLength:6, validate:"ValidNumeric"}
				, {type:"newcolumn"}
				, {type:"select", width:100, label:"모집경로",name:"onOffType", offsetLeft:50}
				, {type:"newcolumn"}
				, {type:"select", width:100, offsetLeft:20, label:"상품구분",name:"prodType"}
			]}
			//조직 관련 히든 컬럼
			, {type:"hidden",name: "hghrOrgnCd",value:hghrOrgnCd}
			, {type:"hidden",name: "typeCd",value:typeCd}
			//버튼 여러번 클릭 막기 변수
			, {type : 'hidden', name: "btnCount1", value:"0"}
			, {type : 'hidden', name: "btnExcelCount1", value:"0"}
			, {type : 'hidden', name: "DWNLD_RSN", value:""}, //엑셀다운로드 사유
			// 조회버튼
			, {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search4"}
		]
		,onXLE : function() {}
		,onButtonClick : function(btnId) {
			var form = this;
			
			switch (btnId) {
				case "btnSearch":
					var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
					PAGE.FORM1.setItemValue("btnCount1", btnCount2)
					if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") {
						return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기
					}
					
					PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpNewMgmtList.json", form, {
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
		}
		,onChange : function(name, value) {//onChange START
			// 검색구분
			if(name == "pSearchGbn") {
				PAGE.FORM1.setItemValue("pSearchName", "");
				
				if(value == null || value == "" || value == "8") {
					var searchEndDt = new Date().format('yyyymmdd');
					var time = new Date().getTime();
					time = time - 1000 * 3600 * 24 * 7;
					var searchStartDt = new Date(time);

					// 신청일자 Enable
					PAGE.FORM1.enableItem("searchStartDt");
					PAGE.FORM1.enableItem("searchEndDt");

					PAGE.FORM1.setItemValue("pSearchName", "");

					// 생년월일 Disable
					PAGE.FORM1.disableItem("pBirthDay");
					PAGE.FORM1.disableItem("pBirthDayVal");

					PAGE.FORM1.setItemValue("pBirthDayVal", "");

					PAGE.FORM1.setItemValue("searchStartDt", searchStartDt);
					PAGE.FORM1.setItemValue("searchEndDt", searchEndDt);
					
					if(value == "8") PAGE.FORM1.enableItem("pSearchName");
					else PAGE.FORM1.disableItem("pSearchName");
				} else if(value == "1") {
					PAGE.FORM1.setItemValue("searchStartDt", "");
					PAGE.FORM1.setItemValue("searchEndDt", "");
					
					// 신청일자 Disable
					PAGE.FORM1.disableItem("searchStartDt");
					PAGE.FORM1.disableItem("searchEndDt");

					PAGE.FORM1.enableItem("pSearchName");
					
					PAGE.FORM1.enableItem("pBirthDay");
					PAGE.FORM1.enableItem("pBirthDayVal");
				} else if(value != "8"){
					PAGE.FORM1.setItemValue("searchStartDt", "");
					PAGE.FORM1.setItemValue("searchEndDt", "");
					
					// 신청일자 Disable
					PAGE.FORM1.disableItem("searchStartDt");
					PAGE.FORM1.disableItem("searchEndDt");

					// 생년월일 Disable
					PAGE.FORM1.disableItem("pBirthDay");
					PAGE.FORM1.disableItem("pBirthDayVal");

					PAGE.FORM1.setItemValue("pBirthDayVal", "");
					
					PAGE.FORM1.enableItem("pSearchName");
				}
			}

		}
		,onValidateMore : function (data){
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
			}
			
			// 검색조건 없는 경우 신청일자 필수 체크
			if(data.pSearchGbn == ""){
				if(this.getItemValue("searchStartDt", true) == null || this.getItemValue("searchStartDt", true) == ""){
					this.pushError("searchStartDt","신청일자","시작일자를 선택하세요");
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				}
				
				if(this.getItemValue("searchEndDt", true) == null || this.getItemValue("searchEndDt", true) == ""){
					this.pushError("searchEndDt","신청일자","종료일자를 선택하세요");
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				}
			}
			
		}
	}
	//신청관리 리스트
	,GRID1 : {
		css : {
				height : "485px"
		},
		title : "조회결과",
 		header : "상품구분,서비스구분,예약번호,할인유형,고객명,생년월일,성별,연락처,대리점,요금제,평생할인프로모션명,업무구분,신청일자,진행상태,신청서상태,모집경로,유입경로,추천인구분,추천인정보,녹취여부,판매점명,판매자,판매자ID,수정자,수정일자", // 25개
		columnIds : "prodTypeNm,serviceName,resNo,sprtTpNm,cstmrName,birthDay,cstmrGenderStr,cstmrMobile,agentName,socName,disPrmtNm,operName,reqInDay,requestStateName,pstateName,onOffName,openMarketReferer,recommendFlagNm,recommendInfo,recYn,shopNm,usrNm,usrId,rvisnNm,rvisnDttm",
		colAlign : "center,center,center,center,left,center,center,center,left,left,left,center,center,center,center,center,center,center,center,center,left,left,left,center",
		colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
		colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
		widths : "60,80,80,80,120,80,40,120,200,250,250,70,80,70,70,70,70,100,70,50,200,80,100,80,150",
		paging: true,
		pageSize:20,
		buttons : {
			right : {
				btnDtl: { label : "서식지 보기" }
			}
		}
		,checkSelectedButtons : ["btnDtl"]
		,onRowDblClicked : function(rowId, celInd) {
			this.callEvent('onButtonClick', ['btnDtl']);
		}
		,onButtonClick : function(btnId, selectedData) {
			var form = this;
			switch (btnId) {
				
				case "btnDtl" : //Row 더블클릭시 Start
					var sdata = PAGE.GRID1.getSelectedRowData();
				    var reqParam = {"resNo" : sdata.resNo};
					mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getMcpRequestScanId.json", reqParam, function(resultData) {
						
						if(mvno.cmn.isEmpty(resultData.result.scanId)) {
							mvno.ui.alert("등록된 서식지가 없습니다.");
							return;
						}
						
						//form.setItemValue("oriScanId", resultData.result.scanId);
						
						appFormView(resultData.result.scanId, sdata.resNo); 
						
						if(maskingYn == "Y" || maskingYn == "1"){
							mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"resNo":sdata.resNo,"trgtMenuId":"MSP1000014","tabId":"paper"}, function(result){});
						}
					});
					
					break;	//Row 더블클릭시 End
				
			}//Switch End
		}// onButtonClick
	}
};

function appFormView(scanId, resNo){
	var width = 415;
	var height = 237;
	var top  = 10;
	var left = 10;
	//var DBMSType = "C";
	
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
		"&RES_NO="+resNo+
		"&PARENT_SCAN_ID="+scanId
		//"&DBMSType="+DBMSType
		;

	callViewer(data,width,height,top,left);

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
		dataType : "jsonp",
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

var PAGE = {
	 title: "${breadCrumb.title}",
	 breadcrumb: "${breadCrumb.breadCrumb}", 
	 buttonAuth: ${buttonAuth.jsonString},
	 onOpen:function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");

		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0032",etc1:"1"}, PAGE.FORM1, "pServiceType");		// 서비스구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0049",orderBy:"etc1"}, PAGE.FORM1, "pOperType");	// 업무구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM1, "pRequestStateCode");			// 진행상태
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0005"}, PAGE.FORM1, "pPstate");						// 신청취소
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2017"}, PAGE.FORM1, "pSearchGbn");					// 검색구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0063"}, PAGE.FORM1, "prodType");					// 상품구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9004"}, PAGE.FORM1, "reqBuyType");					// 구매유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0058"}, PAGE.FORM1, "sprtTp");						// 할인유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM1, "onOffType");					// 모집경로
		
		if(typeCd != "10"){
			PAGE.FORM1.disableItem("btnOrgFind");
		}
		
		PAGE.FORM1.disableItem("pSearchName");
	}
};

</script>