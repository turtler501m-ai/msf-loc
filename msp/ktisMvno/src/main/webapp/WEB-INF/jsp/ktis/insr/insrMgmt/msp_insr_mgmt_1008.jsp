<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">

	var fromDt = new Date(new Date().setDate(new Date().getDate() - 6)).format("yyyymmdd");
	var toDt = new Date().format("yyyymmdd");

	var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type:"block", list:[
					{type:"select", width:80, label:"", name:"searchGb", options:[{value:"reqinday", text:"신청일자", selected:true},{value:"strtDttm", text:"가입일자"}]}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchFromDt", label:"", value:fromDt, calendarPosition: "bottom", width:100, offsetLeft:10, readonly:true}
					, {type:"newcolumn"}
					, {type:"calendar", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", name:"searchToDt", label:"~", value:toDt, calendarPosition: "bottom", labelAlign:"center", labelWidth:10, offsetLeft:5, width:100, readonly:true}
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
							form.enableItem("searchFromDt");
							form.enableItem("searchToDt");
							form.enableItem("searchGb");
							
							form.setItemValue("searchFromDt", fromDt);
							form.setItemValue("searchToDt", toDt);

							form.setItemValue("searchVal", "");
							form.disableItem("searchVal");
						} else {
							form.setItemValue("searchFromDt", "");
							form.setItemValue("searchToDt", "");

							form.disableItem("searchFromDt");
							form.disableItem("searchToDt");
							form.disableItem("searchGb");
							
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

						PAGE.GRID1.list(ROOT + "/insr/insrMgmt/getInsrReadyList.json", form);

						break;
				}
			}
			, onValidateMore : function (data) {
				if (this.getItemValue("searchFromDt", true) > this.getItemValue("searchToDt", true)) {
					this.pushError("searchFromDt", "요청일자", "조회 시작일이 종료일 보다 클수 없습니다.");
				}
			}
		}

		, GRID1 : {
			css : {
				height : "600px"
			}
			, title : "보험신청목록"
			, header : "전화번호,고객명,계약번호,계약상태,개통대리점,보험상품명,신청일자,가입일자,종료일자"
			, columnIds : "subscriberNo,subLinkName,contractNum,subStatusNm,openAgntNm,insrProdNm,reqinday,strtDttm,endDttm"
			, widths : "120,100,100,80,130,200,130,130,130"
			, colAlign : "center,left,center,center,left,left,center,center,center"
			, colTypes : "roXp,ro,ro,ro,ro,ro,roXdt,roXdt,roXdt"
			, colSorting : "str,str,str,str,str,str,str,str,str"
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

						mvno.cmn.download(ROOT + "/insr/insrMgmt/getInsrReadyListByExcel.json?menuId=MSP9008001", true, {postData:searchData});

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

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchCd");					// 검색구분
			PAGE.FORM1.disableItem("searchVal");
		}
	};
</script>
