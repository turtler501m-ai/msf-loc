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
				]},
					, {type : "button", name: "btnSearch", value:"조회", className:"btn-search1"}	
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch(btnId) {
				case "btnSearch" :
					PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getReqUsimPukList.json", form);
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
		// 유심PUK번호 열람신청 리스트
		, GRID1 : {
			css : {
				height : "590px"
			}
			, title : "조회결과"
			, header : "계약번호,고객명,전화번호,성별,나이(만),개통일,개통 대리점,모집유형,유심접점,조회일시" //11
			, columnIds : "svcContId,subLinkName,ctn,gender,age,lstComActvDate,openAgntNm,onOffTypeNm,usimOrgNm,sysRdate" //11
			, widths : "100,80,100,60,60,100,130,100,130,140,80" //11
			, colAlign : "center,left,center,center,center,center,center,center,center,center,center" //11
			, colTypes : "ro,ro,roXp,ro,ro,ro,ro,ro,ro,ro,ro" //11
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str" //11
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
						mvno.cmn.download(ROOT + "/stats/statsMgmt/getReqUsimPukListExcel.json?menuId=MSP1010041", true, {postData:searchData}); 
		
					break;
				}
			}
	  },
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