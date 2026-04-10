<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>



<!-- Script -->
<script type="text/javascript">

var maskingYn = "${maskingYn}";				// 마스킹권한

	var DHX = {
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"openEndDate", label:"개통마감일자", labelWidth:80, calendarPosition:"bottom", inputWidth : 100, value: "${openEndDate}"} 
				    , {type:"newcolumn"}
					, {type:"select", label:"계약상태", name:"subStatus", width:110, offsetLeft:57, options:[{text: "사용중 + 정지", value: "01"},{text: "사용중", value: "02"},{text: "정지", value: "03"},{text: "해지", value: "04"}]}
					, {type:"newcolumn"}
					, {type : "input", label:"모델명", width : 200, offsetLeft:57,  name : "modelName"}
				]},
				  {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}, 
				  {type : 'hidden', name: "DWNLD_RSN", value:""}
			]
			, onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getModelOpenList.json", form);
					break;
				}
			}
			, onValidateMore : function(data){
				
					if(data.openEndDate == ''){
						this.pushError("openEndDate", "개통마감일자", "개통마감일자는 필수 입력 항목입니다.");
					}
					
					if(data.modelName == ''){
						this.pushError("modelName", "모델명", "모델명은 필수 입력 항목입니다.");
					}
					
					if(data.modelName.length < 3 ){
						this.pushError("modelName", "모델명", "모델명은 3자리 이상 입력해야 합니다.");
					}
			}
			, onChange : function(name, value) {
				var form = this;
					
			}
		}
		, GRID1 : {
			css : {
				height : "570px"
			}
			, title : "조회결과",
				header : "상품구분,계약번호,고객명,생년월일,나이(만),휴대폰번호,요금제,할인유형,판매정책명,약정개월수,할부개월수,단말모델,단말일련번호,상태,해지사유,모집경로,유입경로,추천인구분,추천인정보,녹취여부,대리점,판매점명,판매자명,판매자ID,기변유형,기변일자,기변모델명,기변대리점,프로모션명",
				columnIds : "prodTypeNm,contractNum,cstmrNm,birth,age,subscriberNo,lstRateNm,sprtTpNm,salePlcyNm,enggMnthCnt,instMnthCnt,lstModelNm,lstModelSrlNum,subStatusNm,canRsn,onOffTypeNm,openMarketReferer,recommendFlagNm,recommendInfo,recYn,agentNm,shopNm,shopUsrNm,shopUsrId,dvcOperTypeNm,dvcChgDt,dvcModelNm,dvcAgntNm,promotionNm",
				widths : "80,80,100,60,60,100,200,80,200,80,80,120,120,60,120,60,60,100,70,60,150,80,100,150,80,80,80,100,200",
				colAlign : "center,center,left,center,right,center,left,center,left,right,right,left,center,center,left,center,center,center,center,center,left,left,left,left,center,center,left,left,left",
				colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ron,ron,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXd,ro,ro,ro,ro",
				colSorting : "str,str,str,str,str,str,str,str,str,int,int,str,str,sr,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, paging : true
			, pageSize:20
			, showPagingAreaOnInit: true
			, buttons : {
				hright : {
					btnDnExcel : { label : "자료생성" }
				}
			},
			onButtonClick : function(btnId , selectedData) {
				var form = this;
				switch (btnId) {
					case "btnDnExcel":
						
						if (PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						} else {
							var searchData =  PAGE.FORM1.getFormData(true);
							var openEndDate = searchData.openEndDate;
							var modelName = searchData.modelName;
							if((openEndDate != null && openEndDate != '' ) || (modelName != null && modelName != '' )){
                                mvno.cmn.downloadCallback(function(result) {
									PAGE.FORM1.setItemValue("DWNLD_RSN",result);
									mvno.cmn.ajax(ROOT + "/stats/statsMgmt/getModelOpenListExcel.json?menuId=MSP1000110", PAGE.FORM1.getFormData(true), function(result){
										mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
									});
								});
							}else{
								mvno.ui.alert("검색조건이 맞지 않습니다.");
								return;
							}
						}
						
					break;
				}
			}
		}
	}
	;
	
	var PAGE = {
		title: "${breadCrumb.title}"
		, breadcrumb: "${breadCrumb.breadCrumb}"
		, buttonAuth: ${buttonAuth.jsonString}
		, onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
		}
	};
</script>