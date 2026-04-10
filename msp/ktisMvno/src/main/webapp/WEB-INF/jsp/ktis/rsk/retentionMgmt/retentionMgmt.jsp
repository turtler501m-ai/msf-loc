<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">

	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blackOffset:0}
					, {type:"block", list:[
					                	{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', label: '조회일자',  name: 'searchStartDt', value: "${startDate}", calendarPosition: 'bottom', width:100, offsetLeft:10}
					                	, {type: 'newcolumn'}
					                	, {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'searchEndDt', label: '~', value: "${endDate}", calendarPosition: 'bottom', labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
             	                        , {type:"newcolumn"}
// 										, {type : "select", label : "검색구분", name : "searchGbn", userdata: { lov: 'CMN0108'}, width:100,  offsetLeft:20}
										, {type : "select", label : "검색구분", name : "searchGbn", width:100,  offsetLeft:20}
										, {type:"newcolumn"}
										, {type:"input", width:100, name:"searchName",maxLength:30}
					                	]}

					//버튼 여러번 클릭 막기 변수
					, {type : 'hidden', name: "btnCount1", value:"0"} 
					, {type : 'hidden', name: "btnExcelCount1", value:"0"}
					                				
					, {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
				],
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							PAGE.GRID1.list(ROOT +  "/rsk/retentionMgmt/retentionMgmtList.json", form);
							break;
					}
				},
				
				onChange : function(name, value) {//onChange START
					// 검색구분
					if(name == "searchGbn") {
						PAGE.FORM1.setItemValue("searchName", "");

						if(value == null || value == "") {
							var searchEndDt = new Date().format('yyyymmdd');
							var time = new Date().getTime();
							time = time - 1000 * 3600 * 24 * 7;
							var searchStartDt = new Date(time);

							// 신청일자 Enable
							PAGE.FORM1.enableItem("searchStartDt");
							PAGE.FORM1.enableItem("searchEndDt");

							PAGE.FORM1.setItemValue("searchName", "");

							PAGE.FORM1.setItemValue("searchStartDt", searchStartDt);
							PAGE.FORM1.setItemValue("searchEndDt", searchEndDt);

							PAGE.FORM1.disableItem("searchName");
						}
						else {
							PAGE.FORM1.setItemValue("searchStartDt", "");
							PAGE.FORM1.setItemValue("searchEndDt", "");

							// 신청일자 Disable
							PAGE.FORM1.disableItem("searchStartDt");
							PAGE.FORM1.disableItem("searchEndDt");

							PAGE.FORM1.enableItem("searchName");
						}
					}

				},
				onValidateMore : function (data){
					if (this.getItemValue("searchStartDt", true) > this.getItemValue("searchEndDt", true)) {
						this.pushError("searchEndDt", "조회기간", "종료일자가 시작일자보다 클 수 없습니다.");
						this.resetValidateCss();
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}
					
					if( data.searchGbn != "" && data.searchName.trim() == ""){
						this.pushError("searchName", "검색어", "검색어를 입력하세요");
						PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
						PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
					}else{
						// 검색조건 없는 경우 조회기간 제한
						/* if(!mvno.cmn.checkAllowDate(PAGE.FORM1.getItemValue("searchEndDt", true), PAGE.FORM1.getItemValue("searchStartDt", true), 30)) {
							this.pushError("", "조회기간", "검색어가 없는 경우 조회기간은 30일이내로 선택하세요");
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
							PAGE.FORM1.setItemValue("btnExcelCount1", 0); //로딩 끝나면 초기화 - 엑셀다운로드버튼
						} */
					}
				}
			},
			
			GRID1 : {
				css : {
					height : "600px"
				},
				title : "조회결과",
				header : "계약번호,고객명,생년월일,구매유형,휴대폰번호,개통일자,개통대리점,모델명,일련번호,상태,해지사유,계약번호,고객명,생년월일,구매유형,휴대폰번호,개통일자,개통대리점,모델명,일련번호,해지일자,해지사유,사용기간",
				columnIds : "contractNum,subLinkName,userSsn,reqByType,subscriberNo,lstComActvDate,openAgntCd,modelName,intmSrlNo,subStatus,subStatusRsnCode,cContractNum,cSubLinkName,cUserSsn,cReqByType,cSubscriberNo,cLstComActvDate,cOpenAgntCd,cModelName,cIntmSrlNo,cSubStatusDate,cSubStatusRsnCode,cUseDay",
				widths : "100,150,100,100,130,100,150,100,150,80,200,120,150,100,100,130,100,150,100,150,100,200,60",
				colAlign : "center,left,center,center,center,center,left,center,left,center,left,center,left,center,center,center,center,left,center,left,center,left,right",
				colTypes : "ro,ro,ro,ro,roXp,roXd,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXp,roXd,ro,ro,ro,roXd,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
				paging : true,
				pageSize:20,
				buttons : {
					hright : {
						btnDnExcel : { label : "엑셀다운로드" }
					}
				},
				onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnDnExcel":
							
							var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
							PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
							if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") {
								return; //버튼 최초 클릭일때만 조회가능하도록
							}
							
							if(PAGE.GRID1.getRowsNum() == 0) {
								mvno.ui.alert("데이터가 존재하지 않습니다.");
								return;
							}
							
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/rsk/retentionMgmt/retentionMgmtEx.json?menuId=MSP1000322", true, {postData:searchData});
							break;
					}
				}
				
			}
			
	};

	var PAGE = {
			title: "${breadCrumb.title}",
			breadcrumb: "${breadCrumb.breadCrumb}",
		    buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9013"}, PAGE.FORM1, "searchGbn"); // 검색구분
			}
			
	};
    
</script>
