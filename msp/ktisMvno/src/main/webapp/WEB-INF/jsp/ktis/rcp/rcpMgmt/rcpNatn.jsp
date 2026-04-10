<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- header -->
<div class="page-header">
	<h1>국가선택 팝업</h1>
</div>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<script type="text/javascript">
	
	var DHX = {
			
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
				{type: 'block', list: [
					{type:"input", label:"국가명", name:"searchNm", width:100, offsetLeft:0}
				]},
				{type : "newcolumn"}, 
				{type : "button", name : "btnSearch", value : "조회", className:"btn-search1"} 
			],
			onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/rcpNewMgmt/getRcpNatnList.json", form);
						break;
				}
			}
		},
		
		GRID1 : {
			css : {
				height : "200px"
			},
			title : "조회결과",
			header : "국가코드,국가명",
			columnIds : "natnCd,natnNm",
			widths : "100,325",
			colAlign : "center,left",
			colTypes : "ro,ro",
			colSorting : "str,str",
			buttons : {
				center : {
					btnApply : { label : "확인" },
					btnClose : { label: "닫기" }
				}
			},
			checkSelectedButtons : ["btnApply"],
			onRowDblClicked : function(rowId, celInd) {
				// 선택버튼 누른것과 같이 처리
				this.callEvent('onButtonClick', ['btnApply']);
			},
			onButtonClick : function(btnId, selectedData) {
				
				switch (btnId) {
				
					case "btnApply":
						console.log(selectedData);
						mvno.ui.closeWindow(selectedData, true);
						
						break; 
						W
					case "btnClose" :
						
						mvno.ui.closeWindow();
						
						break;
				}
			},
		}
	};
	
	var PAGE = {
		onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
		}
	};
</script>