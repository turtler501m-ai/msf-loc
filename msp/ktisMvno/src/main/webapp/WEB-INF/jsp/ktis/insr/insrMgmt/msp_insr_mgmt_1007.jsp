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
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80, blockOffset:0}
				, {type:"block", list:[
					{type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchFromDt", label:"해지요청일자", value:fromDt, calendarPosition: "bottom", width:100, offsetLeft:10}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchToDt", label:"~", value:toDt, calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100}
					, {type:"newcolumn"}
					, {type:"select", label:"검색구분", name: "searchCd", labelWidth:60 , width:100, offsetLeft:50}
					, {type:"newcolumn"}
					, {type:"input", label:"", name:"searchVal", width:130, offsetLeft:0, maxLength:60}
					, {type:"newcolumn"}
				]}
				, {type:"newcolumn"}
				, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}
			]
			, onChange: function (name, value) {
				var form = this;

				switch(name) {
					case "searchCd":
						PAGE.FORM1.setItemValue("searchVal", "");
						
						if(mvno.cmn.isEmpty(value)) {
							form.setItemValue("searchFromDt", fromDt);
							form.setItemValue("searchToDt", toDt);

							form.setItemValue("searchVal", "");

							form.enableItem("searchFromDt");
							form.enableItem("searchToDt");
							
							form.disableItem("searchVal");
						} else {
							form.setItemValue("searchFromDt", "");
							form.setItemValue("searchToDt", "");

							form.disableItem("searchFromDt");
							form.disableItem("searchToDt");
							
							form.enableItem("searchVal");
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

						PAGE.GRID1.list(ROOT + "/insr/insrMgmt/getInsrCanList.json", form);

						break;
				}
			}
			, onValidateMore : function (data) {
				if (this.getItemValue("searchFromDt", true) > this.getItemValue("searchToDt", true)) {
					this.pushError("searchFromDt", "해지요청일자", "조회 시작일이 종료일 보다 클수 없습니다.");
				}
			}
		}

		, GRID1 : {
			css : {
				height : "600px"
			}
			, title : "해지미처리목록"
			, header : "전화번호,고객명,계약번호,계약상태,보험상품코드,보험상품명,가입일자,해지요청일자,해지요청사유,해지요청결과,메모"
			, columnIds : "subscriberNo,subLinkName,contractNum,subStatusNm,insrProdCd,insrProdNm,strtDttm,procDt,canRsltCd,rsltMsg,rmk"
			, widths : "120,80,100,80,100,170,130,130,100,180,500"
			, colAlign : "center,center,center,center,center,center,center,center,Left,Left,Left"
			, colTypes : "roXp,ro,ro,ro,ro,ro,roXdt,roXdt,ro,ro,ro"
			, colSorting : "str,str,str,str,str,str,str,str,str,str,str"
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

						mvno.cmn.download(ROOT + "/insr/insrMgmt/getInsrCanListByExcel.json?menuId=MSP9007001", true, {postData:searchData});

						break;

				}
			},
		}

	}

	var PAGE = {
	      title : "${breadCrumb.title}",
	      breadcrumb : "${breadCrumb.breadCrumb}",
		  buttonAuth: ${buttonAuth.jsonString} ,
		  onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2021"}, PAGE.FORM1, "searchCd");					// 검색구분
			PAGE.FORM1.disableItem("searchVal");
		}
	};
</script>
