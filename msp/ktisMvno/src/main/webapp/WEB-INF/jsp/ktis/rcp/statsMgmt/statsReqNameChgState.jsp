<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

var lbl="";
 
var isCntpShop = false;	//대리점, 판매점 여부
var hghrOrgnCd = "";

var sOrgnId = "";
var sOrgnNm = "";

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

	var DHX = {
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					 {type : "calendar",width : 100,label : "조회기간",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",  value:"${srchStrtDt}",name : "srchStrtDt", offsetLeft:10, readonly:true}
					, {type : "newcolumn"}
					, {type : "calendar",label : "~",name : "srchEndDt",labelAlign : "center",dateFormat : "%Y-%m-%d",  value:"${srchEndDt}",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true}
					, {type : "newcolumn"}
					, {type : "select",width : 100 , label : "검색구분",name : "searchGbn" ,  offsetLeft:10, options:[{value:"", text:"- 전체 -"}, {value:"1", text:"계약번호"}, {value:"2", text:"CTN"}]}
					, {type : "newcolumn"}
					, {type : "input",width : 100,name : "searchName", disabled:true}
					, {type : "newcolumn"}
					, {type : "checkbox", label : "미성년자여부", width : 40, name : "minorYn", labelWidth : 80, offsetLeft : 15, value : "Y"}
					, {type : "hidden", name : "resNo" }
					, {type : "hidden", name : "scanId" }
				]}
				, {type : "button", name: "btnSearch", value:"조회", className:"btn-search1"}	
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId) {
				case "btnSearch" :
					PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getNameChgStateList.json", form);
					break;
				}
			}
			
			, 	onValidateMore : function(data) {
				if (data.srchStrtDt > data.srchEndDt) {
					this.pushError("srchStrtDt", "조회기간", "시작일자가 종료일자보다 클 수 없습니다.");
				}
				if(data.srchStrtDt != "" && data.srchEndDt == ""){
					this.pushError("srchStrtDt", "조회기간", "조회기간을 입력하세요.");
				}
				if(data.srchEndDt != "" && data.srchStrtDt == ""){
					this.pushError("srchEndDt", "조회기간", "조회기간을 입력하세요.");
				}				
				if( data.searchGbn != "" && data.searchName.trim() == ""){
					this.pushError("searchName", "검색어", "검색어를 입력하세요");
					PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
				}
			}
			, onChange : function(name, value) {
				var form = this;
					
				// 검색구분
				if(name == "searchGbn") {
					PAGE.FORM1.setItemValue("searchName", "");
					
					if(value == null || value == "") {

						var endDate = new Date().format('yyyymmdd');
						var time = new Date().getTime();
						time = time - 1000 * 3600 * 24 * 7;
						var startDate = new Date(time);
						
						PAGE.FORM1.enableItem("srchStrtDt");
						PAGE.FORM1.enableItem("srchEndDt");
						
						PAGE.FORM1.setItemValue("srchStrtDt", startDate);
						PAGE.FORM1.setItemValue("srchEndDt", endDate);
						
						PAGE.FORM1.disableItem("searchName");
					}
					else {
						PAGE.FORM1.setItemValue("srchStrtDt", "");
						PAGE.FORM1.setItemValue("srchEndDt", "");
						
						// 개통일자 Disable
						PAGE.FORM1.disableItem("srchStrtDt");
						PAGE.FORM1.disableItem("srchEndDt");
						
						PAGE.FORM1.enableItem("searchName");
					}
				}
			}
		}
		// ----------------------------------------
		// 명의변경현황
		, GRID1 : {
			css : {
				height : "560px"
			}
			, title : "조회결과"
			, header : "계약번호,서비스계약번호,상태,CTN,고객명,나이,개통일자,대리점,최초명의변경일자,최종명의변경일자,대리인(신청서),대리인연락처(신청서),대리인(현시점),대리인연락처(현시점),orgScanId,resNo,cstmrFnNm" // 17
			, columnIds : "contractNum,svcCntrNo,subStatus,ctn,subLinkName,age,lstComActvDate,openAgntCd,fstChangeDate,lstChangeDate,minorAgentName,minorAgentTel,lglAgntNm,lglAgntNo,orgScanId,resNo,cstmrFnNm" // 17
			, widths : "80,100,60,100,120,50,80,150,100,100,100,120,100,120,1,1,1" // 17
			, colAlign : "center,center,center,center,left,center,center,left,center,center,left,center,left,center,center,center,center" //17
			, colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" //17
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" // 17
			, hiddenColumns:[14,15,16]
			, paging : true
			, pageSize:20
			, showPagingAreaOnInit: true
			, buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드" },
				},
				right : {
					btnPaper : { label : "서식지보기" },
				}
			},
			onButtonClick : function(btnId) {
				var grid = this;
				switch (btnId) {
					case "btnExcel":
						
						if (PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						} 
						var searchData =  PAGE.FORM1.getFormData(true);
						mvno.cmn.download(ROOT + "/stats/statsMgmt/getNameChgStateListExcel.json?menuId=MSP1010058", true, {postData:searchData}); 
		
					break;
					
					case "btnPaper":
						PAGE.FORM1.setItemValue("resNo",PAGE.GRID1.getSelectedRowData().resNo);
						PAGE.FORM1.setItemValue("scanId",PAGE.GRID1.getSelectedRowData().scanId);
						appFormView(PAGE.GRID1.getSelectedRowData().scanId); 
							
						if(maskingYn == "Y" || maskingYn == "1"){
							mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"resNo":form.getItemValue("resNo"),"trgtMenuId":"MSP1010058","tabId":"paper"}, function(result){});
						}
					break;
				}
			}
		}
	}
	var PAGE = {
			title: "${breadCrumb.title}"
			, breadcrumb: "${breadCrumb.breadCrumb}"
			, buttonAuth: ${buttonAuth.jsonString}
			, onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");
			}
		};
	
	function appFormView(scanId){
		var DBMSType = "C";
		
		var data = null;
		var modifyFlag = "N";
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
</script>