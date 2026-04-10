<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">

	var fromDt = new Date(new Date().setDate(new Date().getDate() - 6)).format("yyyymmdd");
	var toDt = new Date().format("yyyymmdd");
	var optYN = [{value:"", text:"- 전체 -", selected:true}, {value:"Y", text:"Y"}, {value:"N", text:"N"}];

	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchFromDt", label:"보상승인일자", labelWidth:80, value:fromDt, calendarPosition: "bottom", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchToDt", label:"~", value:toDt, calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type:"newcolumn"}
					, {type:"select", label:"보상유형", name:"cmpnType", width:110, offsetLeft:20}
					, {type:"newcolumn"}
					, {type:"select", label:"보험상품", name: "insrProdCd", width:190, offsetLeft:20}
				]}
				, {type:"block", list:[
					{type:"select", label:"검색구분", name: "searchCd", labelWidth:80, width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchVal", width:115, offsetLeft:0, maxLength:60}
					, {type:"newcolumn"}
					, {type:"select", label:"결제여부", name: "payYn", width:110, offsetLeft:20, options:optYN}
				]}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"}
			]
			, onChange: function (name, value) {
				var form = this;

				switch(name) {
					case "searchCd":
						if(mvno.cmn.isEmpty(value)) {
							form.setItemValue("searchFromDt", fromDt);
							form.setItemValue("searchToDt", toDt);

							form.setItemValue("searchVal", "");

							form.enableItem("searchFromDt");
							form.enableItem("searchToDt");
						} else {
							form.setItemValue("searchFromDt", "");
							form.setItemValue("searchToDt", "");

							form.disableItem("searchFromDt");
							form.disableItem("searchToDt");
						}

						break;
				}
			}
			, onButtonClick : function(btnId) {
				var form = this;

				switch (btnId) {

					case "btnSearch":

						if( !mvno.cmn.isEmpty(form.getItemValue("searchCd")) && mvno.cmn.isEmpty(form.getItemValue("searchVal")) ) {
							mvno.ui.alert("검색 할 내용이 없습니다.");
							return;
						}

						PAGE.GRID1.list(ROOT + "/insr/insrMgmt/getInsrCmpnMbsList.json", form);

						break;
				}
			}
		}

		, GRID1 : {
			css : {
				height : "530px"
			}
			, title : "보험보상목록"
			, header : "전화번호,고객명,계약번호,보험상품명,보상유형,단말모델ID,단말모델명,단말일련번호,보험관리번호,사고번호,사고등록일자,사고일자,보상승인일자,보상한도금액,잔여보상금액,보상누적금액,잔여보상한도금액,영수증금액,실보상금액,고객부담금,초과금,유심비용,보상단말모델,단말일련번호,택배사,송장번호,결제여부,결제일시,처리자,처리일시"
			, columnIds : "subscriberNo,subLinkName,contractNum,insrProdNm,cmpnTypeNm,modelId,modelNm,intmSrlNo,insrMngmNo,acdntNo,acdntRegDt,acdntDt,cmpnCnfmDt,cmpnLmtAmt,rmnCmpnAmt,cmpnAcmlAmt,rmnCmpnLmtAmt,rcptAmt,realCmpnAmt,custBrdnAmt,overAmt,usimAmt,cmpnModelNm,reqPhoneSn,tbNm,dlvryNo,payYn,payDttm,rvisnId,rvisnDttm"
			, widths : "120,100,100,180,80,100,100,100,120,120,100,100,100,100,100,100,120,100,100,100,100,100,130,100,100,100,100,130,100,130"
			, colAlign : "center,center,center,Left,center,center,Left,Left,center,center,center,center,center,Right,Right,Right,Right,Right,Right,Right,Right,Right,Left,center,center,center,center,center,center,center"
			, colTypes : "roXp,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXd,roXd,roXd,roXns,roXns,roXns,roXns,roXns,roXns,roXns,roXns,roXns,ro,ro,ro,ro,ro,roXdt,ro,roXdt"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str"
			, paging: true
			, showPagingAreaOnInit: true
			, pageSize:20
			, buttons : {
				hright : {
					btnExcel:{ label:"엑셀다운로드"}
				}
			}
			, onButtonClick : function(btnId) {
				var grid = this;

				switch (btnId) {
					case "btnExcel":

						if(PAGE.GRID1.getRowsNum() == 0){
							mvno.ui.alert("데이터가 존재하지 않습니다");
							return;
						}

						var searchData =  PAGE.FORM1.getFormData(true);

						mvno.cmn.download(ROOT + "/insr/insrMgmt/getInsrCmpnMbsListByExcel.json?menuId=MSP9006001", true, {postData:searchData});

						break;

				}
			}
		}

	}

	var PAGE = {
	      title : "${breadCrumb.title}",
	      breadcrumb : "${breadCrumb.breadCrumb}",
		  buttonAuth: ${buttonAuth.jsonString} ,
		  onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchCd");					// 검색구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0253", orderBy:"etc1"}, PAGE.FORM1, "cmpnType");	// 사고유형
			mvno.cmn.ajax4lov( ROOT + "/insr/insrMgmt/getInsrCombo.json", "", PAGE.FORM1, "insrProdCd");										// 단말보험상품

		}
	};
</script>
