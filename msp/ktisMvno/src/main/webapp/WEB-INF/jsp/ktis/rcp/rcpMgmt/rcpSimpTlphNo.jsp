<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header -->
<div class="page-header">
	<h1>번호조회 팝업</h1>
</div>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

	var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				, {type: 'block', list: [
					{type:"input", label:"희망번호", name:"tlphNo", labelWidth:60, width:100, offsetLeft:0, validate:'ValidNumeric', maxLength:4, required:true}
					, {type:'newcolumn'}
					, {type:"hidden", label:"예약번호", name:"resNo"}
					, {type:"hidden", label:"이벤트코드", name:"appEventCd"}
				]}
				, {type : "newcolumn"} 
				, {type : "button", name : "btnSearch", value : "조회", className:"btn-search"} 
			],
			onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch":
						
						if(form.getItemValue("tlphNo").length < 4 ){
							mvno.ui.alert("희망번호 4자리를 입력 해주세요.");
							return;
						}
						
						form.setItemValue("appEventCd", "NU1");
						PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getOsstSvcNoList.json", form)
						break;
				}
			}
		}
		, GRID1 : {
			css : {
					height : "280px"
			},
			title : "조회결과",
			header : "전화번호",
			columnIds : "tlphNo",
			colAlign : "center",
			colTypes : "roXp",
			colSorting : "str",
			widths : "250",
			//paging: true,
			//pageSize:20,
			buttons : {
				center : {
					btnRsv : { label : "번호예약" }
					, btnClose : { label: "닫기" }
				}
			},
			onButtonClick : function(btnId, selectedData) {
				var grid = this;
				switch (btnId) {
					case "btnRsv" :
						var sdata = grid.getSelectedRowData();
						if(sdata == null){
							mvno.ui.alert("선택된 번호가 없습니다");
							return;
						}
						
						var data = {
								appEventCd : "NU2"
								, gubun : "RSV"
								, resNo : PAGE.FORM1.getItemValue("resNo")
								, tlphNo : sdata.tlphNo
								, encdTlphNo : sdata.encdTlphNo
								, tlphNoStatCd : sdata.tlphNoStatCd
								, asgnAgncId : sdata.asgnAgncId
								, tlphNoOwnCmncCmpnCd : sdata.tlphNoOwnCmncCmpnCd
								, openSvcIndCd : sdata.openSvcIndCd
						};
						
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/setOsstSvcNoRsv.json", data, function(resultData) {
							mvno.ui.alert("번호예약 되었습니다.");
							
							var selectedData = { tlphNo : sdata.tlphNo};
							
							mvno.ui.closeWindow(selectedData, true);
						});
						
						break;
						
					case "btnClose" :
						
						mvno.ui.closeWindow();   
						
						break;
				}
			}
		}
	};

var PAGE = {
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		
		// 예약번호
		if("${param.resNo}" != ''){
			PAGE.FORM1.setItemValue("resNo","${param.resNo}");
		}
		
		// 선호번호
		if("${param.reqWantNumber}" != ''){
			PAGE.FORM1.setItemValue("tlphNo","${param.reqWantNumber}");
		}
		
	}
};

</script>