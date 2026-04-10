<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
	/**
	 * @Class Name : appFormMgmt.jsp
	 * @Description : 서식지함
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

// 초기값 세팅
var sOrgnId = "";
var sOrgnNm = "";
var typeCd = "${loginInfo.userOrgnTypeCd}";

if(typeCd == "10"){
	sOrgnId = "";
	sOrgnNm = "";
}else{
	sOrgnId = "${loginInfo.userOrgnId}";
	sOrgnNm = "${loginInfo.userOrgnNm}";
}

// 사용자ID
var usrId = "${loginInfo.userId}";
var usrName = '${loginInfo.userName}';

//조직 유형 - S:판매점, D:대리점, K:개통센터, "10:본사조직, 20:대리점, 30:판매점"
if(typeCd == "10") {
	orgType = "K";
}else if(typeCd == "20") {
	orgType = "D";
}else if(typeCd == "30") {
	orgType = "S";
}

//삭제권한 ( 본사사용자의 경우 Y )
var delAuthYn = "N";
if(typeCd == "10") {
	delAuthYn = "Y";
}

var strtDt = new Date(new Date().setDate(new Date().getDate() - 7)).format("yyyymmdd");
var endDt = new Date().format("yyyymmdd");


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


var DHX = {
	// 조회조건
	FORM1 : {
		items : [
			{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
			,{type:"block", list:[
				{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"strtDt", label:"신청일자", value:strtDt, calendarPosition: "bottom", width:100,offsetLeft:10}
				,{type:"newcolumn"}
				,{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"endDt", label:"~", value:endDt, calendarPosition: "bottom", readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
				,{type:"newcolumn"}
				,{type:"input", label:"대리점", name: "agntCd", value:sOrgnId, width:100, offsetLeft:40, required:true}
				,{type:"newcolumn"}
				,{type:"button", value:"찾기", name:"btnOrgFind", disabled:false}
				,{type:"newcolumn"}
				,{type:"input", name: "agntNm", value:sOrgnNm, width:150, readonly: true}
			]}
			,{type:"block", list:[
				{type:"input", name:"custNm", label:"고객명", width:219, offsetLeft:10, maxLength:60}
				,{type:"newcolumn"}
				,{type:"input", label:"판매점", name: "shopCd", width:100, offsetLeft:40}
				,{type:"newcolumn"}
				,{type:"button", value:"찾기", name:"btnShopFind", disabled:false}
				,{type:"newcolumn"}
				,{type:"input", name: "shopNm", width:150, readonly: true}
			]}
			,{type:"block", list:[
				{type:"label", label:"진행상태", offsetLeft:10}
				,{type:"newcolumn"}
				,{type:"radio", name:"procStatCd", value: "", label:"전체", position:"label-right", labelWidth:40}
				,{type:"newcolumn"}
				,{type:"radio", name:"procStatCd", value: "00", label:"접수", position:"label-right", labelWidth:40}
				,{type:"newcolumn"}
				,{type:"radio", name:"procStatCd", value: "10", label:"신청정보등록", position:"label-right", labelWidth:80}
				,{type:"newcolumn"}
				,{type:"radio", name:"procStatCd", value: "11", label:"신청완료", position:"label-right", labelWidth:60}
				,{type:"newcolumn"}
				,{type:"radio", name:"procStatCd", value: "20", label:"개통완료", position:"label-right", labelWidth:60}
				,{type:"newcolumn"}
				,{type:"radio", name:"procStatCd", value: "90", label:"개통보류", position:"label-right", labelWidth:60}
				,{type:"newcolumn"}
				,{type:"radio", name:"procStatCd", value: "99", label:"폐기", position:"label-right", labelWidth:40}
			]}
			, {type: "button", value: "조회", name: "btnSearch" , className:"btn-search3"}
		]
		,onButtonClick : function(btnId) {
			var form = this;

			switch (btnId) {
				case "btnSearch":
					PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getAppFormMgmtList.json", form, {
						callback:function(){
							PAGE.FORM2.clear();
						}
					});

					break;
				//대리점조회
				case "btnOrgFind":
					mvno.ui.codefinder("ORG", function(result) {
						form.setItemValue("agntCd", result.orgnId);
						form.setItemValue("agntNm", result.orgnNm);
					});
					break;
				//판매점검색
				case "btnShopFind":
					mvno.ui.codefinder("ORGSHOP", function(result) {
						form.setItemValue("shopCd", result.orgnId);
						form.setItemValue("shopNm", result.orgnNm);
					});
					break;
			}
		}
		,onValidateMore : function (data){
			if (this.getItemValue("strtDt", true) > this.getItemValue("endDt", true)) {
				this.pushError("endDt", "조회기간", "종료일자가 시작일자보다 클 수 없습니다.");
				this.resetValidateCss();
			}
		}
	}
	//목록
	,GRID1 : {
		css : {
			height : "350px"
		}
		,title : "조회결과"
		,header : "등록일시,대리점ID,대리점,판매점ID,판매점,고객명,업무구분,진행상태,파일갯수,등록자ID,등록자,처리자ID,처리자,처리일시" //14
		,columnIds : "regDttm,agencyId,agntNm,companyId,shopNm,custNm,workNm,procStatNm,scanFileCnt,rgstPrsnId,rgstPrsnNm,procPrsnId,procPrsnNm,procDttm"
		,colAlign : "center,center,left,center,left,left,center,center,right,center,center,center,center,center"
		,colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ron,ro,ro,ro,ro,ro" //14
		,colSorting : "str,str,str,str,str,str,str,str,int,str,str,str,str,str" //14
		,widths : "140,100,150,100,150,150,80,80,80,100,100,100,100,140" //14
		,paging: true
		,showPagingAreaOnInit: true
		,pageSize:20
		,buttons : {
			hright : {
				btnExcel:{ label:"엑셀다운로드"}
			}
		}
		,onRowSelect : function(rowId, celInd) {
			var rowData = this.getRowData(rowId);

			PAGE.FORM2.setFormData(rowData);
			if (maskingYn != "Y") {
				PAGE.FORM2.disableItem("custNm");
			}
		}
		,onButtonClick : function(btnId, selectedData) {
			var form = this;
			switch (btnId) {
				case "btnExcel" : //엑셀다운로드 버튼 클릭시
					if(PAGE.GRID1.getRowsNum() == 0) {
						mvno.ui.alert("데이터가 존재하지 않습니다.");
						return;
					}
					var searchData =  PAGE.FORM1.getFormData(true);
					mvno.cmn.download(ROOT + "/rcp/rcpMgmt/getAppFormMgmtListExcel.json?menuId=MSP1010100",true,{postData:searchData});

					break;
			}
		}
	}
	//상세보기
	,FORM2 : {
		items : [
			{type:"settings", position:"label-left", labelAlign:"left", labelWidth: "80"}
			,{type:"block", list:[
				{type:"input", name:"shopNm", label:"판매점",  width:200, offsetLeft:10, readonly:true}
				,{type:"newcolumn"}
// 				,{type:"select", name:"procStatCd", label:"진행상태", offsetLeft:20, userdata:{lov:"RCP0065"}}
				,{type:"select", name:"procStatCd", label:"진행상태", offsetLeft:20}
				,{type:"newcolumn"}
				,{type:"input", name:"custNm", label:"고객명",  width:150, offsetLeft:20}
				,{type:"newcolumn"}
			]}
			,{type:"block", list:[
				{type:"input", name:"cMemo", label: "메모", width:657, rows:5, offsetLeft:10}
			]}
			,{type:"hidden", name:"scanId"}
			,{type:"hidden", name:"resNo"}
			,{type:"hidden", name:"companyId"}
			,{type:"hidden", name:"agencyId"}
			,{type:"hidden", name:"agntNm"}
			,{type:"hidden", name:"rgstPrsnId"}
			,{type:"hidden", name:"rgstPrsnNm"}
			,{type:"hidden", name:"procPrsnId"}
			,{type:"hidden", name:"orgProcStatCd"}
			,{type:"hidden", name:"viewYn"}
		]
		,buttons : {
			left : {
				btnPaper:{label:"서식지보기"}
				,btnRcp:{label:"신청등록"}
			}
			,right : {
				btnSave:{label:"저장"}
			}
		}
		,onValidateMore : function (data) {
			var form = this;

		}
		,onButtonClick:function(btnId){
			var form = this;

			switch (btnId) {
				case "btnPaper":

					if(form.getItemValue("viewYn") != "Y"){
						mvno.ui.alert("서식지 조회가능기간이 종료되었습니다.");
						return;
					}

					var DBMSType = "C";
					var scanUserId = (mvno.cmn.isEmpty(form.getItemValue("rgstPrsnId"))) ? usrId : form.getItemValue("rgstPrsnId");
					var scanUserNm = (mvno.cmn.isEmpty(form.getItemValue("rgstPrsnNm"))) ? usrName : form.getItemValue("rgstPrsnNm");
					
					data = "AGENT_VERSION="+agentVersion+
						"&SERVER_URL="+serverUrl+
						"&VIEW_TYPE=MCPVIEW"+
						"&USE_DEL="+delAuthYn+
						"&USE_STAT=N"+
						"&USE_BM="+blackMakingYn+
						"&USE_DEL_BM="+blackMakingFlag+
						"&RGST_PRSN_ID="+scanUserId+
						"&RGST_PRSN_NM="+scanUserNm+
						"&ORG_TYPE="+orgType+
						"&ORG_ID="+form.getItemValue("agencyId")+
						"&ORG_NM="+form.getItemValue("agntNm")+
						"&RES_NO="+form.getItemValue("resNo")+
						"&PARENT_SCAN_ID="+form.getItemValue("scanId")+
						"&DBMSType="+DBMSType
						;
						
					callViewer(data);
					
					break;
					
				case "btnRcp":
					// 서식지상태체크
					mvno.cmn.ajax2(ROOT + "/rcp/rcpMgmt/getAppFormStatCd.json", form, function(result) {
						var procStatCd = result.result.data.rows[0].procStatCd;
						
						if(procStatCd != "00"){
							mvno.ui.alert("접수인 경우만 신청등록 가능합니다.");
							return;
						}
						
						// 팝업
						if(mvno.ui.confirm("신청하시겠습니까?", function(){
							// v2017.02 신청관리 간소화 신청등록(신규) 호출
							var url = "/rcp/rcpMgmt/getRcpNewMgmt.do";
							var menuId = "MSP1000014";
							var scanId = form.getItemValue("scanId");
							var shopCd = form.getItemValue("companyId"); // 판매점ID
							var shopNm = fncEnCode(form.getItemValue("shopNm")); // 판매점명
							var resNo = form.getItemValue("resNo"); // 접수번호

							var param = "?menuId=" + menuId + "&scanId=" + scanId + "&shopCd=" + shopCd + "&shopNm=" + shopNm+ "&resNo=" + resNo + "&trgtMenuId=MSP1010100";

							var myTabbar = window.parent.mainTabbar;

							if (myTabbar.tabs(url)) {
								myTabbar.tabs(url).setActive();
							}else{
								myTabbar.addTab(url, "신청등록", null, null, true);
							}

							myTabbar.tabs(url).attachURL(url + param);

							// 잘안보여서.. 일단 살짝 Delay 처리
							setTimeout(function() {
								//mainLayout.cells("b").progressOff();
							}, 100);
						}));
					});

					break;

				case "btnSave":
					// 상태변경체크
					var orgProcStatCd = form.getItemValue("orgProcStatCd");
					var procStatCd = form.getItemValue("procStatCd");

					// 개통완료건은 진행상태 변경 불가
					if(orgProcStatCd == "20"){
						mvno.ui.alert("개통완료된 서식지는 진행상태를 변경할 수 없습니다.");
						return;
					}

					// 개통보류건은 진행으로 변경 가능
					if(orgProcStatCd == "90" && procStatCd != "00"){
						mvno.ui.alert("개통보류건은 진행상태로만 변경가능합니다.");
						return;
					}

					// 폐기건은 본사권한에 의해 진행상태로 변경 가능
					// 최종 처리자가 본인일 경우 접수로 변경 가능 2016.07.29 JHY
					if(orgProcStatCd == "99"){
						if(typeCd == "10"){
							if(procStatCd != "00"){
								mvno.ui.alert("접수상태로 변경가능합니다.");
								return;
							}
						}else if(form.getItemValue("procPrsnId") == usrId){
							// 최종 처리자가 본인일 경우 접수로 변경 가능 2016.07.29 JHY
							if(procStatCd != "00"){
								mvno.ui.alert("접수상태로 변경가능합니다.");
								return;
							}
						}else{
							mvno.ui.alert("폐기건은 본사담당자에게 요청하세요.");
							return;
						}
					}

					if (maskingYn != "Y") {
						form.setItemValue("custNm","");
					}
					// 상태변경 권한 체크
					if(form.getItemValue("procPrsnId") == "" || form.getItemValue("procPrsnId") == usrId || typeCd == "10"){
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/updateAppFormData.json", form, function(result) {
							mvno.ui.notify("NCMN0002");
							PAGE.GRID1.refresh();
							PAGE.FORM2.clear();
						});

					}else{
						// 진행자 또는 본사권한자만 상태변경 가능
						mvno.ui.alert("상태변경 권한이 없습니다.");
					}

					break;
			}
		}
	}
}; // end dhx

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

var PAGE = {
	title: "${breadCrumb.title}",
	breadcrumb: "${breadCrumb.breadCrumb}",
	buttonAuth: ${buttonAuth.jsonString},
	onOpen:function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		mvno.ui.createForm("FORM2");

		//대리점찾기 버튼
		if(${loginInfo.userOrgnTypeCd} != 10) {
			PAGE.FORM1.disableItem("btnOrgFind");
			PAGE.FORM1.setReadonly("agntCd",true);
			PAGE.FORM1.setReadonly("agntNm",true);
		}

		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0065"}, PAGE.FORM2, "procStatCd"); // 진행상태
	}
};


function fncEnCode(param){
	var encode = '';

	for(i=0; i<param.length; i++){
		var len  = '' + param.charCodeAt(i);
		var token = '' + len.length;

		encode  += token + param.charCodeAt(i);
	}

	return encode;
}
</script>