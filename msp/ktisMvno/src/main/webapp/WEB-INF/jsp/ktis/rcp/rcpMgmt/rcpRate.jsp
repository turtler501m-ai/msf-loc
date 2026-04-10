<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- header -->
<div class="page-header">
	<h1>부가서비스선택 팝업</h1>
</div>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
var DHX = {
	//------------------------------------------------------------
	FORM1 : {
		title : "조회조건",
		items : [
			{type: 'input', name: 'requestKey', label: '', value: '', width:120, maxLength:10}
// 			,{type: 'newcolumn', offset:50}
// 			,{type: 'input', name: 'usrNm', label: '', value: '', width:120, maxLength:50}
			, {type: 'button', value: '조회', name: 'btnSearch' , className:"btn-search1"}
		]
		/* ,onButtonClick : function(btnId) {
			var form = this;
			
			switch (btnId) {
				case "btnSearch":
					PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpRateList.json", form);
					break;
			}
		} */
	},

	// ----------------------------------------
	GRID1 : {
		css : {
			height : "280px"
		},
		title : "",
		header : "사용여부,구분,부가서비스,금액,0,0,0",
		columnIds : "additionChecked,grpAddition,additionName,rantal,additionKey,soc,insrProdYn,",
		widths : "100,200,200,130,0,0,0",
		colAlign : "center,center,left,right,center,right,center",
		colTypes : "ch,ro,ro,ron,ro,ro,ro",
		colSorting : "str,str,str,int,str,str,str",
		buttons : {
			center : {
				btnApply : { label : "확인" },
				btnClose : { label: "닫기" }
			}
		},
		checkSelectedButtons : [],
		onRowSelect : function(rowId, celInd) {
		},

		onXLE : function() {
			PAGE.GRID1.groupBy(1);
		},

		onButtonClick : function(btnId) {
			var grid = this;
		
			var codeArray = new Array();
			var nameArray = new Array();
			var rantal = 0;
			// 단말보험
			/*var socArray = new Array();
			var insrProdArray = new Array();*/
			
			var arrData = PAGE.GRID1.getCheckedRowData();
			
			if(arrData.length == 0){
				mvno.ui.alert("ECMN0003");
				return;
			}

			// 단말보험은 하나만 선택하도록 체크
			//var insrProdCheck = "N";
			for (var i=0; i < arrData.length; i++) {
				rantal = rantal + Number(arrData[i].rantal);
				codeArray.push(arrData[i].additionKey);
				nameArray.push(arrData[i].additionName);
				/* socArray.push(arrData[i].soc);
				insrProdArray.push(arrData[i].insrProdYn);
				
				if(insrProdCheck == "N") {
					if(arrData[i].insrProdYn == "Y") insrProdCheck = "Y";
				} else {
					if(arrData[i].insrProdYn == "Y"){
						mvno.ui.alert("단말보험은 하나만 선택할 수 있습니다.");
						return;
					}
				}*/
			}
			//var selectedData = { usrId: codeArray.join() , usrNm:nameArray.join(), rantal:rantal};
			var selectedData = { additionKey: codeArray.join() , additionName:nameArray.join(), rantal:rantal}; //, soc:socArray.join(), insrProdYn:insrProdArray.join()
			
			switch (btnId) {
				case "btnApply":
					mvno.ui.closeWindow(selectedData, true);
					break;

				case "btnClose" :
					mvno.ui.closeWindow();
					break;

			}
		},
		onCheck : function(rId)  {
			var grid = this;
			grid.selectRow(rId-1);
		}
	}
};

var PAGE = {
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		PAGE.FORM1.setItemValue("requestKey","${param.requestKey}");
		
		PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpRateList.json", PAGE.FORM1);
		$("#FORM1").hide();
	}
};
</script>