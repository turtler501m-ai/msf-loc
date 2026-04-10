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
					PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getReqJoinFormList.json", form);
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
		// 가입신청서 출력요청 리스트
		, GRID1 : {
			css : {
				height : "590px"
			}
			, title : "조회결과"
			, header : "계약번호,고객명,전화번호,성별,나이(만),개통일,개통 대리점,모집유형,유심접점,신청일시,수신방법,결과,메모,수정자,수정일시,시퀀스" //18
			, columnIds : "svcContId,subLinkName,ctn,gender,age,lstComActvDate,openAgntNm,onOffTypeNm,usimOrgNm,sysRdate,recvType,procCdNm,clMemo,updtId,sysUdate,custReqSeq" // 18 
			, widths : "100,80,100,60,60,100,130,100,130,140,80,80,200,100,120,10" // 18
			, colAlign : "center,left,center,center,center,center,center,center,center,center,center,center,left,center,center,center" //18
			, colTypes : "ro,ro,roXp,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" //18
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" // 18
			, hiddenColumns:[15]
			, paging : true
			, pageSize:20
			, showPagingAreaOnInit: true
			, buttons : {
				hright : {
					btnExcel : { label : "엑셀다운로드" },
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
						mvno.cmn.download(ROOT + "/stats/statsMgmt/getReqJoinFormListExcel.json?menuId=MSP1010040", true, {postData:searchData}); 
		
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
				}
			}
	  },
	  //상세화면
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
							{type:"input", label:"전화번호", name:"ctn", width:100, offsetLeft:20, disabled:true},
						]}
					]},
					// 가입신청서 출력요청 정보
					{type: "fieldset", label: "가입신청서 출력요청 정보", list:[
						{type:"settings"},
						{type:"block", list:[
							{type:"input", label:"수신방법", name:"recvType", width:50, disabled:true}
						]},
						{type:"block", list:[
							{type:"input", label:"팩스번호", name:"faxNo", width:100, disabled:true}							
						]},
						{type:"block", list:[
							{type:"input", label:"우편번호", name:"cstmrPost", labelWidth:80,width:60, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"주소", name:"cstmrAddr", labelWidth:80, width:230, offsetLeft:10, disabled:true},
							{type:"newcolumn"},
							{type:"input", label:"상세주소", name:"cstmrAddrDtl", labelWidth:80, width:110, offsetLeft:20, disabled:true},
						]},
					]},
					//처리결과
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
						mvno.cmn.ajax(ROOT + "/stats/statsMgmt/updateReqJoinForm.json", form, function(result) {
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
	
</script>