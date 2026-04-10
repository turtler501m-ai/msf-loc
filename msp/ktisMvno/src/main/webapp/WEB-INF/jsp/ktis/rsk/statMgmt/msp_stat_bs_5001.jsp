<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  /**
  * @Class Name : msp_stat_bs_5001.jsp
  * @Description : 3G 유지가입자 현황
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2023.06.16    박준성     최초 생성
  *
  */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
	var curDt = new Date().format("yyyymmdd");
	
	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:70, blockOffset:0}
					, {type:"block", list:[
						{type:"calendar", label:"기준일자", name:"stdrDt", value:curDt, width:100, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", offsetLeft:10, required:true}
						, {type:"newcolumn"}
						, {type:"select", label:"계약상태", name:"subStatus", width:100, offsetLeft:20, labelWidth:80}
						, {type:"newcolumn"}
						, {type:"select", label:"검색구분", name: "searchCd", width:100, offsetLeft:20, options:[{value:"", text:"- 선택 -"}, {value:"1", text:"계약번호"}]}
						, {type:"newcolumn"}
						, {type:"input", label:"", name:"searchVal", width:115, offsetLeft:0, maxLength:60, disabled:true}
					]}
					, {type:"block", list:[
						, {type:"select", label:"최종단말망", name:"lstModelTp", width:100, offsetLeft:10}
						, {type:"newcolumn"}
						, {type:"select", label:"최종요금제망", name:"lstRateTp", width:100, offsetLeft:20, labelWidth:80}
						, {type:"newcolumn"}
						, {type:"select", label:"최종유심망", name:"lstUsimTp", width:100, offsetLeft:20}
						, {type:"newcolumn"}
						, {type:"select", label:"VOLTE지원여부", name:"volteYn", width:100, offsetLeft:20, labelWidth:90}
					]}
					, {type:"newcolumn"}
					, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"}
					
					//버튼 여러번 클릭 막기 변수
					, {type : 'hidden', name: "btnCount1", value:"0"} 
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}
					, {type : 'hidden', name: "DWNLD_RSN", value:""}//엑셀다운로드 사유  추가
				]
				, onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							if (! this.validate())  return;
							var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnCount1", btnCount2)
							if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기 by han

							PAGE.GRID1.list(ROOT + "/rsk/statMgmt/getDaily3gSubList.json", form, {callback : function() {
								PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
								PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
							}});
							break;
					}
				}
				, onValidateMore : function(data) {
					if(data.stdrDt == ""){
						this.pushError("stdrDt", "기준일자", "기준일자를 입력하세요.");
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}				
					if( data.searchCd != "" && data.searchVal.trim() == ""){
						this.pushError("searchVal", "검색어", "검색어를 입력하세요");
						PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
				}
				, onChange: function (name, value) {
					var form = this;
					
					switch(name) {
						case "searchCd":
							form.setItemValue("searchVal", "");
							
							if(mvno.cmn.isEmpty(value)) {
								form.disableItem("searchVal");
							} else {
								form.enableItem("searchVal");
							}
							
							break;
					}
				}
			}, //FORM1 End
			
			GRID1 : {
				css : {
					height : "540px"
				}
				, title : "조회결과"
				, header : "계약번호,구매유형,개통일자,생년월일,성별,상태,개통대리점코드,개통대리점,최종요금제코드,최종요금제명,최종요금제망,최종단말모델코드,최종단말명,최종단말망,단말VOLTE지원여부,단말NFC지원여부,최종유심모델,최종유심명,최종유심망,유심NFC지원여부,할인유형,약정시작일자,약정종료일자,정지일자,정지사유,명세서유형" //26
				, columnIds : "contractNum,reqBuyType,lstComActvDate,birthDt,gender,subStatus,openAgntCd,openAgntNm,lstRateCd,lstRateNm,lstRateTp,lstModelId,lstModelNm,lstModelTp,modelVolteYn,modelNfcYn,lstUsimId,lstUsimNm,lstUsimTp,usimNfcYn,blDcType,argmStrtDt,argmEndDt,stopDt,stopRsnNm,billSendNm"
				, widths : "100,100,100,100,80,100,100,180,100,180,100,100,150,100,150,150,100,150,100,150,100,100,100,100,200,150"
				, colAlign : "center,center,center,center,center,center,center,left,center,left,center,center,left,center,center,center,center,left,center,center,center,center,center,center,left,left"
				, colTypes : "ro,ro,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXd,roXd,roXd,ro,ro"
				, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
				, paging : true
				, showPagingAreaOnInit: true
				, pageSize:20
				, buttons : {
					hright : {
						btnExcel : { label : "자료생성" }
					}
				}
				, onButtonClick : function(btnId){
					var form = this;
					switch(btnId){
						case "btnExcel" :
							
							var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
							if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") {
								return; //버튼 최초 클릭일때만 조회가능하도록
							}
							
							if (PAGE.GRID1.getRowsNum() == 0) {
								mvno.ui.alert("데이터가 존재하지 않습니다.");
								return;
							} else {
                                mvno.cmn.downloadCallback(function(result) {
                                    PAGE.FORM1.setItemValue("DWNLD_RSN",result);
                                    mvno.cmn.ajax(ROOT + "/rsk/statMgmt/getDaily3gSubListByExcel.json?menuId=MSP5005001", PAGE.FORM1.getFormData(true), function(result){
                                        mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
                                    });
                                });
							}
							break;
					}
				}
			}	//GRID1 End
	}	//DHX End
	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : " ${breadCrumb.breadCrumb}",
			buttonAuth:${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0037"}, PAGE.FORM1, "subStatus"); // 계약상태
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0266"}, PAGE.FORM1, "lstModelTp"); // 최종단말망
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0266", etc6:"1"}, PAGE.FORM1, "lstRateTp"); // 최종요금제망
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0266", etc6:"1"}, PAGE.FORM1, "lstUsimTp"); // 최종유심망
				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005"}, PAGE.FORM1, "volteYn"); // VOLTE지원여부
// 				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005"}, PAGE.FORM1, "nfcYn"); // NFC지원여부
			}
		};
</script>