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
					, {type : "select", label : "처리결과", width : 100, offsetLeft : 20, name : "successYn" }
					, {type : "hidden", name : "resNo" }
					, {type : "hidden", name : "scanId" }
				]},
					, {type : "button", name: "btnSearch", value:"조회", className:"btn-search1"}	
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId) {
				case "btnSearch" :
					PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getCallList.json", form);
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
		// 통화내역열람
		, GRID1 : {
			css : {
				height : "590px"
			}
			, title : "조회결과"
			, header : "계약번호,고객명,전화번호,성별,나이(만),개통일,개통 대리점,모집유형,신청일시,요청시작일자,요청종료일자,수신방법,결과,시퀀스,메모,수정자,수정일시,orgScanId,resNo" // 16
			, columnIds : "svcContId,subLinkName,ctn,gender,age,lstComActvDate,openAgntNm,onOffTypeNm,sysRdate,startDate,endDate,recvType,procCdNm,custReqSeq,clMemo,updtId,sysUdate,orgScanId,resNo" // 16 
			, widths : "100,80,100,60,60,90,130,100,130,130,130,100,80,100,120,100,120,10,10" // 16
			, colAlign : "center,left,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center,center" //16
			, colTypes : "ro,ro,roXp,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" //16
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" // 16
			, hiddenColumns:[13,17,18]
			, paging : true
			, pageSize:20
			, showPagingAreaOnInit: true
			, buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드" },
					btnPaper : { label : "합본 전 서식지보기" },
				},
			},
			onRowDblClicked : function(rowId, celInd) {
				this.callEvent('onButtonClick', ['btnDtl']);
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
						mvno.cmn.download(ROOT + "/stats/statsMgmt/getCallListExcel.json?menuId=MSP1010038", true, {postData:searchData}); 
		
					break;
					
					case "btnDtl":
						if(grid.getSelectedRowData() == null){
							mvno.ui.alert("선택된 데이터가 없습니다");
							return;
						}
						mvno.ui.createForm("FORM2");
						
						PAGE.FORM2.clear();		
						mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"CL01"}, PAGE.FORM2, "procCd"); // 처리결과	
						PAGE.FORM2.setFormData(grid.getSelectedRowData(), true);							
						
						// 결과가 처리 인 경우 
						if(PAGE.FORM2.getItemValue("procCd") == "CP" ) {
							PAGE.FORM2.disableItem("procCd");
						}else{
							PAGE.FORM2.enableItem("procCd");
						}
						
						mvno.ui.popupWindowById("FORM2", "상세화면", 820, 410, {
							onClose: function(win) {
								if (! PAGE.FORM2.isChanged()) return true;
								mvno.ui.confirm("CCMN0005", function(){
									win.closeForce();
								});
							}
						});
									
						break;
						
					case "btnPaper":
						PAGE.FORM1.setItemValue("resNo",PAGE.GRID1.getSelectedRowData().resNo);
						PAGE.FORM1.setItemValue("scanId",PAGE.GRID1.getSelectedRowData().orgScanId);
						appFormView(PAGE.GRID1.getSelectedRowData().orgScanId); 
							
						if(maskingYn == "Y" || maskingYn == "1"){
							mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"resNo":form.getItemValue("resNo"),"trgtMenuId":"MSP1000014","tabId":"paper"}, function(result){});
						}
					break;
				}
			}
	  },
		FORM2 : {
			items : [
				{type:"block", list:[
					{type:"settings", position:"label-left", labelAlign:"right", labelWidth:80, blockOffset:0},
					// 고객정보
					{type: "fieldset", label: "고객정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"고객명", name:"subLinkName", width:80, readonly:true, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"신청일자", name:"sysRdate", width:140, offsetLeft:10, readonly:true, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"전화번호", name:"ctn", width:80, offsetLeft:20, disabled:true},
						]}
					]},
					// 고객정보
					{type: "fieldset", label: "통화내역정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"열람항목", name:"callType", width:80, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"음성", name:"typeVoice", width:80, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"문자", name:"typeText", width:50, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"데이터", name:"typeData", width:50, disabled:true}
						]},
						{type:"block", list:[
							{type:"input", label:"음성+문자", name:"typeVoiceText", width:50, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"음성+문자+데이터", name:"typeAll",offsetLeft:10, labelWidth:100, width:80, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"신청사유", name:"reqRsn", width:260, disabled:true}
						]},
						{type:"block", list:[
							{type:"input", label:"수신방법", name:"recvType", width:50, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"연락가능연락처", name:"callNum",offsetLeft:10, labelWidth:100, width:120, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"세부입력", name:"recvText", width:220, disabled:true}							
						]},
						{type:"block", list:[
							{type:"input", label:"기타사항", name:"etcMemo", width:592, disabled:true}
						]}
					]},
					{type: "fieldset", label: "처리결과", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"메모", name:"clMemo", width:350, maxLength:300},
							{type:"newcolumn"},
							{type:"select", label:"처리결과", name:"procCd", width:100},
							{type:"hidden", name:"custReqSeq"}
						]},
					]}
				]}
			],
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
						mvno.cmn.ajax(ROOT + "/stats/statsMgmt/updateCall.json", form, function(result) {
							mvno.ui.closeWindowById(PAGE.FORM2, true);
						mvno.ui.notify("NCMN0001");
						
						PAGE.GRID1.refresh();
						});
						break;
											
					case "btnClose" :
						mvno.ui.closeWindowById(PAGE.FORM2, true);
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
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"CL01"  }, PAGE.FORM1, "successYn" ); // 성공여부
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
	function callViewer(data,width,height,top,left){
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