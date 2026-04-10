<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_rcp_bs_0206.jsp
	 * @Description : 기기변경목록
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
	var isCntpShop = false;
	var cntpntShopId = '';
	var cntpntShopNm = '';
	
	var typeCd = '${orgnInfo.typeCd}';
	
	if (typeCd == '20' || typeCd == '30' ){
		isCntpShop = true;
		
		if(typeCd == '20'){
			cntpntShopId = '${orgnInfo.orgnId}';
			cntpntShopNm = '${orgnInfo.orgnNm}';
		}else{
			cntpntShopId = '${orgnInfo.hghrOrgnCd}';
			cntpntShopNm = '${orgnInfo.hghrOrgnNm}';
		}
	}
	
	var DHX = {
		// 검색 조건
		FORM1 : {
			items : [
				{type : "settings", position : "label-left", labelAlign : "left", labelWidth : 55, blockOffset : 0},
				{type : "block",
					list : [
						{type : "calendar",width : 100,label : "기변일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d", name : "applDateFrom", offsetLeft:10},
						{type : "newcolumn"},
						{type : "calendar",label : "~",name : "applDateTo",labelAlign : "center",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100},
						{type : "newcolumn"},
// 						{type : "select",width : 100,offsetLeft : 50,label : "검색구분",name : "searchGbn",userdata : {lov : "RCP0033"}},
						{type : "select",width : 100,offsetLeft : 50,label : "검색구분",name : "searchGbn"},
						{type : "newcolumn"},
						{type : "input",width : 100,name : "searchName"},
						{type : "newcolumn"},
// 						{type : "select",width : 100,offsetLeft : 30,label : "계약상태",name : "subStatus",userdata : {lov : "RCP0020"}}
						{type : "select",width : 100,offsetLeft : 30,label : "계약상태",name : "subStatus"}
					]
				},
				{type : "block",
					list : [
						{type : "input", label:"대리점", name: "cntpntShopId", width:100 ,readonly: isCntpShop ,value:cntpntShopId, offsetLeft:10},
						{type : "newcolumn"},
						{type : "button", value:"찾기", name:"btnOrgFind", disabled:isCntpShop },
						{type : "newcolumn"},
						{type : "input", name:"cntpntShopNm", width:100 ,readonly:isCntpShop, value:cntpntShopNm},
						{type : "newcolumn"},
						{type : "select", label : "업무구분", width : 100, offsetLeft : 15, name : "operType", options:[{value:"", text:"- 전체 -", selected:true}, {value:"HCN3", text:"일반기변"}, {value:"HDN3", text:"우수기변"}]},
						{type : "newcolumn"},
// 						{type : "select", label : "모집경로", width : 100, offsetLeft : 134, name : "onOffType", userdata : {lov : "RCP0007"}},
						{type : "select", label : "모집경로", width : 100, offsetLeft : 134, name : "onOffType"},
					]
				},
				{type : 'hidden', name : "btnCount1", value:"0"},
				{type : 'hidden', name : "btnExcelCount1", value:"0"},
				{type : "newcolumn", offset : 30},
				{type : "button", name : "btnSearch", value : "조회", className : "btn-search2"}
			],
			onButtonClick : function (btnId){
				var form = this;
				switch (btnId) {
					case "btnSearch":
						var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
						
						PAGE.FORM1.setItemValue("btnCount1", btnCount2);
						
						if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return;
						
						if (!this.validate()) return;
						
						var url = ROOT + "/rcp/rcpMgmt/getDvcChgListAjax.json";
						
						PAGE.GRID1.list(url, form, {
							callback : function (){
								PAGE.FORM1.setItemValue("btnCount1", 0);
								PAGE.FORM1.setItemValue("btnExcelCount1", 0);
							}
						});
						break;
						
					case "btnOrgFind":
						mvno.ui.codefinder("ORG", function(result){
							form.setItemValue("cntpntShopId", result.orgnId);
							form.setItemValue("cntpntShopNm", result.orgnNm);
						});
						break;
				}
			},
			onValidateMore : function (data){
				if (this.getItemValue("applDateFrom", true) > this.getItemValue("applDateTo", true)){
					this.pushError("applDateFrom", "기변일자", "시작일이 종료일보다 클수 없습니다.");
					
					this.resetValidateCss();
					
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				}
				
				if( data.searchGbn != "" && data.searchName.trim() == ""){
					this.pushError("searchName", "검색어", "검색어를 입력하세요");
					
					PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
					PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
					PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
				} 
				
				// 검색조건 없는 경우 기변일자 필수 체크
				if(data.searchGbn == ""){
					if(this.getItemValue("applDateFrom") == null || this.getItemValue("applDateFrom") == ""){
						this.pushError("applDateFrom","기변일자","시작일자를 선택하세요");
						
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
					
					if(this.getItemValue("applDateTo") == null || this.getItemValue("applDateTo") == ""){
						this.pushError("applDateTo","기변일자","종료일자를 선택하세요");
						
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
				}
			},
			onChange : function(name, value){
				var form = this;
					
				// 검색구분
				if(name == "searchGbn"){
					PAGE.FORM1.setItemValue("searchName", "");
					
					if(value == null || value == ""){
						var applDateTo = new Date().format('yyyymmdd');
							
						var time = new Date().getTime();
						
						time = time - 1000 * 3600 * 24 * 7;
						
						var applDateFrom = new Date(time);
							
						// 기변일자 Enable
						PAGE.FORM1.enableItem("applDateFrom");
						PAGE.FORM1.enableItem("applDateTo");
						
						PAGE.FORM1.setItemValue("applDateFrom", applDateFrom);
						PAGE.FORM1.setItemValue("applDateTo", applDateTo);
							
						PAGE.FORM1.disableItem("searchName");
					}else{
						PAGE.FORM1.setItemValue("applDateFrom", "");
						PAGE.FORM1.setItemValue("applDateTo", "");
						
						// 기변일자 Disable
						PAGE.FORM1.disableItem("applDateFrom");
						PAGE.FORM1.disableItem("applDateTo");
						
						PAGE.FORM1.enableItem("searchName");
					}
				}
			}
		}, //FROM1
			
		//리스트 - GRID1
		//2020.12.04 조회 시 그리드에 할인유형컬럼 추가
		//[SRM20112729346] M전산 기기변경목록 항목(할인유형) 추가 요청 		
		GRID1 : {
			css : {height : "550px"},
			title : "조회결과",
			header : "계약번호,계약상태,휴대폰번호,고객명,생년월일,업무구분,구매유형,모집경로,할인유형,신청일시,기변일자,최초개통일자,해지일자,해지사유,기변요금제,현재요금제,약정개월수,할부개월수,기변개통단말,기변단말일련번호,현재단말명,현재단말일련번호,개통대리점,개통대리점코드,기변대리점,기변대리점코드,할부원금,기변단말출고가,보증보험관리번호,보험가입상태",
			columnIds : "contractNum,subStatusNm,subscriberNoMask,cstmrName,birthDt,operTypeNm,reqBuyTypeNm,onOffTypeNm,sprtTpNm,reqInDay,dvcChgDt,lstComActvDate,dvcChgTmntDt,dvcChgTmntRsnNm,dvcRateNm,rateNm,instMnthCnt,enggMnthCnt,dvcModelNm,dvcIntmSrlNo,modelNm,intmSrlNo,openAgntNm,openAgntCd,agntNm,agntCd,instOrginAmnt,modelPrice,grntInsrMngmNo,grntInsrStatNm",
			widths : "100,80,100,100,80,80,80,80,80,120,80,80,100,100,100,100,100,80,100,100,100,100,120,100,120,100,100,100,120,100",
			colAlign : "center,center,center,left,center,center,center,center,center,center,center,center,center,left,left,left,right,right,left,center,left,center,left,center,left,center,right,right,center,center",
			colTypes : "ro,ro,roXp,ro,roXd,ro,ro,ro,ro,roXdt,roXd,roXd,roXd,ro,ro,ro,ron,ron,ro,ro,ro,ro,ro,ro,ro,ro,ron,ron,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,int,int,str,str,str,str,str,str,str,str,int,int,str,str",
			paging : true,
			pageSize : 20,
			buttons : {
				hright : {
					btnDnExcel : {
						label : "엑셀다운로드"
					}
				}
			},
			onButtonClick : function (btnId, selectedData){
				
				var form = this;
					
				switch (btnId){
					case "btnDnExcel":
						var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
						
						PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
						
						if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return;
						
						if (PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						}
						
						var searchData =  PAGE.FORM1.getFormData(true);
						
						mvno.cmn.download(ROOT + "/rcp/rcpMgmt/getDvcChgListExcel.json?menuId=MSP1003040", true, {postData:searchData});
						
						break;
				}
			}
		}, //GRID1
	}; //DHX
	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}",
			buttonAuth: ${buttonAuth.jsonString},
			
			onOpen : function (){
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2021"}, PAGE.FORM1, "searchGbn"); // 검색구분
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0037"}, PAGE.FORM1, "subStatus"); // 계약상태
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM1, "onOffType"); // 모집경로
								
				var applDateTo = new Date().format('yyyymmdd');
				
				var time = new Date().getTime();
				
				time = time - 1000 * 3600 * 24 * 7;
				
				var applDateFrom = new Date(time);
				
				PAGE.FORM1.setItemValue("applDateFrom", applDateFrom);
				PAGE.FORM1.setItemValue("applDateTo", applDateTo);
				
				PAGE.FORM1.disableItem("searchName");
				
			}
		};
</script>