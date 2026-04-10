<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : msp_rcp_bs_0003.jsp
     * @Description : 해지상담 신청 화면
     * @
     * @ 수정일     수정자 수정내용
     * @ ------------ -------- -----------------------------
     * @ 2025.09.22 이승국 최초생성
     * @
     * @author : 이승국
     * @Create Date : 2025. 09. 22.
     */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
var orgId = "${orgnInfo.orgnId}";
var orgNm = "${orgnInfo.orgnNm}";

var userId = "${loginInfo.userId}";
var userName = "${loginInfo.userName}";

var typeCd = "${orgnInfo.typeCd}";

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
            //------------------------------------------------------------
            FORM1 : {
                items : [
                         	{type:"block", list:[
								{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', label: '신청일자', name: 'searchStartDt', value: "${startDate}", calendarPosition: 'bottom', readonly:true ,width:90,offsetLeft:10, labelWidth:55}
								, {type: 'newcolumn'}
								, {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', value: "${endDate}", calendarPosition: 'bottom', readonly:true, labelAlign:"center", labelWidth:10, offsetLeft:5, width:90}
								, {type:"newcolumn"}
								, {type:"select", width:90, label:"검색구분",name:"searchGbn", offsetLeft:15, labelWidth:55}
								, {type:"newcolumn"}
								, {type:"input", width:90, name:"searchName",maxLength:30}
								, {type:"newcolumn"}
								, {type:"select", width:90, label:"처리결과",name:"searchStatus", offsetLeft:15, labelWidth:55}
							]},
                         	{type : 'hidden', name: "DWNLD_RSN", value:""}, //엑셀다운로드 사유 2017-03-15 박준성
            				{type : 'hidden', name: "btnCount1", value:"0"}, //버튼 여러번 클릭 막기 변수
            				{type : 'hidden', name: "btnExcelCount1", value:"0"}, //버튼 여러번 클릭 막기 변수
                         	{type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"},
               ],
				onButtonClick : function(btnId) {
                   var form = this;
                   switch (btnId) {
						case "btnSearch":
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return;//버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기
							PAGE.GRID1.list(ROOT + "/rcp/canCanCslMgmt/selectCanCslList.do", form,{callback: function() {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
								form.resetValidateCss();
							}})
						break;
					}
				},
				onValidateMore : function (data) {
					if (this.getItemValue("searchStartDt", true) > this.getItemValue("searchEndDt", true)) {
						this.pushError("searchStartDt", "신청일자", "종료일자가 시작일보다 클수 없습니다.");
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
					if( data.searchGbn != "" && data.searchName.trim() == ""){
						this.pushError("searchName", "검색구분", "검색어를 입력하세요");
						PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					} 
				},
				onChange : function(name, value) {
					var form = this;
							
					// 검색구분
					if(name == "searchGbn") {
						PAGE.FORM1.setItemValue("searchName", "");
						if(value == null || value == "" ){
							var searchEndDt = new Date().format('yyyymmdd');
							var time = new Date().getTime();
							time = time - 1000 * 3600 * 24 * 7;
							var searchStartDt = new Date(time);
							
							// 신청일자 Enable
							PAGE.FORM1.enableItem("searchStartDt");
							PAGE.FORM1.enableItem("searchEndDt");
							
							PAGE.FORM1.setItemValue("searchStartDt", searchStartDt);
							PAGE.FORM1.setItemValue("searchEndDt", searchEndDt);
							
						} else {
							// 신청일자 Disable
							PAGE.FORM1.disableItem("searchStartDt");
							PAGE.FORM1.disableItem("searchEndDt");
							
							PAGE.FORM1.setItemValue("searchStartDt", "");
							PAGE.FORM1.setItemValue("searchEndDt", "");
							
						}
					}
				}
			},
		
			GRID1 : {
				css : {
					height : "600px"
				},
				title : "조회결과",
				header : "계약번호,고객명,생년월일,성별,해지신청번호,연락가능 연락처,현재요금제코드,현재요금제,단말모델,단말일련번호,해지사유,신청일자,개통일자,개통대리점,해지일자,처리결과,메모,수정자,수정일시,custReqSeq,oldScanId,newScanId", //22
				columnIds : "contractNum,cstmrName,birth,gender,cancelMobileNo,receiveMobileNo,lstRateCd,lstRateNm,lstModelNm,lstModelSrlNum,canRsn,regstDttm,openDt,openAgntNm,canDt,procCdNm,memo,rvisnNm,rvisnDttm,custReqSeq,oldScanId,newScanId",//22
				colAlign : "center,left,center,center,center,center,center,left,left,center,left,center,center,left,center,center,left,center,center,center,center,center",//22
				colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",//22
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",//22
				widths : "100,100,100,40,120,120,120,250,120,100,150,150,100,150,100,80,250,100,150,0,0,0",//22
				hiddenColumns:[19,20,21],
				paging:true,
				pageSize:20,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀 다운로드" }
						,btnPaper : { label : "합본 전 서식지보기" }
					},
				},
				onRowDblClicked : function(rowId, celInd) {
					this.callEvent('onButtonClick', ['btnDtl']);
				},
	            onButtonClick : function(btnId) {
					var form = this; // 혼란방지용으로 미리 설정 (권고)
	              		
					switch (btnId) {
						case "btnDtl":
							if(form.getSelectedRowData() == null){
								mvno.ui.alert("선택된 데이터가 없습니다");
								return;
							}
							mvno.ui.createForm("FORM2");
							
							PAGE.FORM2.clear();		
							mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"CL05"}, PAGE.FORM2, "procCd"); // 처리결과	
							PAGE.FORM2.setFormData(form.getSelectedRowData(), true);							
							
							// 결과가 처리 인 경우 
							if(PAGE.FORM2.getItemValue("procCd") == "CP" ) {
								PAGE.FORM2.disableItem("procCd");
								PAGE.FORM2.disableItem("memo");
								mvno.ui.hideButton("FORM2", "btnSave");
							}else{
								PAGE.FORM2.enableItem("procCd");
								PAGE.FORM2.enableItem("memo");
								mvno.ui.showButton("FORM2", "btnSave");
							}
							
							mvno.ui.popupWindowById("FORM2", "상세화면", 850, 280, {
								onClose: function(win) {
									if (! PAGE.FORM2.isChanged()) return true;
									mvno.ui.confirm("CCMN0005", function(){
										win.closeForce();
									});
								}
							});
									
						break;
						
	                    case "btnDnExcel":
	                        var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
	                        PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
	                        if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록
	
	                        if (PAGE.GRID1.getRowsNum() == 0) {
	                            mvno.ui.alert("데이터가 존재하지 않습니다.");
	                            return;
	                        } else {
	                        	var searchData =  PAGE.FORM1.getFormData(true);
		                        mvno.cmn.download(ROOT + "/rcp/canCanCslMgmt/selectCanCslListByExcel.json?menuId=MSP1010038", true, {postData:searchData});
	                        }
						break;
	
	                    case "btnPaper":
	                    	if(form.getSelectedRowData() == null){
								mvno.ui.alert("선택된 데이터가 없습니다");
								return;
							}
	                    	if((form.getSelectedRowData().newScanId == "" || form.getSelectedRowData().newScanId == null)){
								mvno.ui.alert("등록된 서식지를 찾을 수 없습니다.");
								return;
							}
							
							appFormView(PAGE.GRID1.getSelectedRowData().newScanId); 
							if(maskingYn == "Y" || maskingYn == "1"){
								mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json", {"resNo":PAGE.GRID1.getSelectedRowData().resNo,"trgtMenuId":"MSP1010038","tabId":"paper"}, function(result){});
							}
						break;
	                }
	            }
	        }
			,FORM2 : {
				items : [
					{type:"block", list:[
						{type:"settings", position:"label-left", labelAlign:"right", labelWidth:80, blockOffset:0},
						// 고객정보
						{type: "fieldset", label: "신청자 정보", list:[
							{type:"settings"},
							{type:"block", list:[
								{type:"input", label:"고객명", name:"cstmrName", width:80, readonly:true, disabled:true},
								{type:"newcolumn"},
								{type:"input", label:"신청일자", name:"regstDttm", width:130, readonly:true, disabled:true},
								{type:"newcolumn"},
								{type:"input", label:"전화번호", name:"cancelMobileNo", width:90, disabled:true},
								{type:"newcolumn"},
								{type:"input", label:"개통일자", name:"openDt", width:80, disabled:true}
							]}
						]},
						{type: "fieldset", label: "처리결과", list:[
							{type:"settings"},
							{type:"block", list:[
								{type:"input", label:"메모", name:"memo", width:350, maxLength:300},
								{type:"newcolumn"},
								{type:"select", label:"처리결과", name:"procCd", width:100},
								{type:"hidden", name:"custReqSeq"},
								{type:"hidden", name:"oldScanId"},
								{type:"hidden", name:"newScanId"}
							]}
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

					    	if(PAGE.FORM2.getItemValue("procCd") == "" || PAGE.FORM2.getItemValue("procCd") == null){
					    		mvno.ui.alert("처리결과를 선택하여야 합니다.");
								return;
					    	}
						    
					    	if((PAGE.FORM2.getItemValue("oldScanId") == "" || PAGE.FORM2.getItemValue("newScanId") == "") && PAGE.FORM2.getItemValue("procCd") == 'CP'){
								mvno.ui.alert("등록된 서식지를 찾을 수 없어 저장할 수 없습니다.");
								return;
							}

					    	mvno.cmn.ajax(ROOT + "/rcp/canCanCslMgmt/getCustStatus.json", form, function(resultData) {
						    	if(resultData.result.msg == "NOK"){
							    	mvno.ui.alert("이미 처리가 완료된 데이터입니다.");
							    }else{
									mvno.cmn.ajax(ROOT + "/rcp/canCanCslMgmt/updateCanCsl.json", form, function(result) {
										mvno.ui.closeWindowById(PAGE.FORM2, true);
										mvno.ui.notify("NCMN0001");
									
										PAGE.GRID1.refresh();
									});
								}
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
	        title: "${breadCrumb.title}",
	        breadcrumb: "${breadCrumb.breadCrumb}",
	        buttonAuth: ${buttonAuth.jsonString},
	        onOpen : function() {
		        mvno.ui.createForm("FORM1");
		        mvno.ui.createGrid("GRID1");
	
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9054"}, PAGE.FORM1, "searchGbn"); // 검색구분
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"CL05"}, PAGE.FORM1, "searchStatus"); // 처리결과
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