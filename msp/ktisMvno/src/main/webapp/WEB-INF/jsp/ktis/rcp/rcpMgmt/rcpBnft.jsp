<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- header -->
<div class="page-header">
	<h1>고객혜택선택 팝업</h1>
</div>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<script type="text/javascript">
	
	var DHX = {
			
		FORM1 : {
			items : [
				{type: 'input', name: 'requestKey', label: '', value: ''}
			]
		},
		
		GRID1 : {
			css : {
				height : "200px"
			},
			title : "",
			header : "혜택여부,혜택명,혜택코드",
			columnIds : "chk,bnftNm,bnftCd",
			widths : "100,200,100",
			colAlign : "center,center,center",
			colTypes : "ch,ro,ro",
			colSorting : "str,str,str",
			hiddenColumns:[2],
			buttons : {
				center : {
					btnApply : { label : "확인" },
					btnClose : { label: "닫기" }
				}
			},
			onButtonClick : function(btnId) {
				
				switch (btnId) {
				
					case "btnApply":
						
						var arrData = PAGE.GRID1.getCheckedRowData();
// 						if(arrData.length == 0){
// 							mvno.ui.alert("ECMN0003");
// 							return;
// 						}
						
						var codeArray = new Array();
						var nameArray = new Array();
						for (var i=0; i < arrData.length; i++) {
							codeArray.push(arrData[i].bnftCd);
							nameArray.push(arrData[i].bnftNm);
						}
						
						var selectedData = { bnftKey : codeArray.join() , bnftNm : nameArray.join()};
						
						mvno.ui.closeWindow(selectedData, true);
						
						break; 
						
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
			
			if("${param.requestKey}" != ''){
				PAGE.FORM1.setItemValue("requestKey","${param.requestKey}");
			}
			
			PAGE.GRID1.list(ROOT + "/rcp/rcpNewMgmt/getRcpBnftList.json", PAGE.FORM1);
			$("#FORM1").hide();
		}
	};
</script>