<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- header -->
<div class="page-header">
	<h1>부가서비스선택 팝업</h1>
</div>

<!-- 화면 배치 -->
<!-- <div id="FORM1" class="section-search"></div> -->
<div id="GRID1"></div>
<div id="GRID2"></div>

<script type="text/javascript">
	
	var DHX = {
			
		/* FORM1 : {
			items : [
				{type: "input", name: "requestKey"}
				,{type: "input", name: "cntpntShopId"}
				,{type: "input", name: "reqBuyType"}
				,{type: "input", name: "operType"}
				,{type: "input", name: "rateCd"}
				,{type: "input", name: "agrmTrm"}
				,{type: "input", name: "reqInDay"}
				
			]
		}, */
		
		GRID1 : {
			css : {
				height : "220px"
			},
			title : "",
			header : "사용여부,구분,부가서비스,금액,부가서비스코드",
			columnIds : "additionChecked,grpNm,additionName,rantal,additionKey",
			widths : "80,120,180,100,100",
			colAlign : "center,center,left,right,right",
			colTypes : "ch,ro,ro,ron,ro",
			colSorting : "str,str,str,int,str",
			hiddenColumns:[4],
			buttons : {
				center : {
					btnApply : { label : "확인" },
					btnClose : { label: "닫기" }
				}
			},
			onXLE : function() {
				PAGE.GRID1.groupBy(1);
			},
			onButtonClick : function(btnId) {
				
				switch (btnId) {
				
					case "btnApply":
						
						// 선택된 부가서비스
						var arrData = PAGE.GRID1.getCheckedRowData();
						if(arrData.length == 0){
							mvno.ui.alert("ECMN0003");
							return;
						}
						
						// 배타관계
						var eData = PAGE.GRID2.getCheckedRowData();
						
						var codeArray = new Array();
						var nameArray = new Array();
						var rantal = 0;
						
						var check1 = false;
						for (var i=0; i < arrData.length; i++) {
							// 선택한 부가서비스 코드
							var sKey = arrData[i].additionKey;
							
							for(var k=0; k<eData.length; k++){
								// 배타관계 체크대상 부가서비스
								var aKey = eData[k].additionKey;
								var eKey = eData[k].exclusiveKey;
								var msg = eData[k].msg;
								if(sKey == aKey){
									// 선택한 부가서비스 중 배타관계 체크
									for (var j=0; j < arrData.length; j++) {
										if(arrData[j].additionKey == eKey){
											mvno.ui.alert(msg);
											return;
										}
									}
								}
							}
							
							rantal = rantal + Number(arrData[i].rantal);
							codeArray.push(arrData[i].additionKey);
							nameArray.push(arrData[i].additionName);
						}
						
						var selectedData = { additionKey : codeArray.join() , additionName : nameArray.join(), rantal : rantal};
						
						mvno.ui.closeWindow(selectedData, true);
						
						break; 
						
					case "btnClose" :
						
						mvno.ui.closeWindow();
						
						break;
				}
			}
		}
		,GRID2 : {
			css : {
				height : "200px"
			},
			title : "",
			header : "선택,부가서비스코드,배타서비스코드,메세지",
			columnIds : "additionChecked,additionKey,exclusiveKey,msg",
			widths : "80,100,120,200",
			colAlign : "center,center,center,left",
			colTypes : "ro,ro,ro,ro",
			colSorting : "str,str,str,str",
		}
	};
	
	var PAGE = {
		onOpen : function() {
// 			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			mvno.ui.createGrid("GRID2");
			
			/* PAGE.FORM1.setItemValue("requestKey","${param.requestKey}");
			PAGE.FORM1.setItemValue("cntpntShopId","${param.cntpntShopId}");
			PAGE.FORM1.setItemValue("reqBuyType","${param.reqBuyType}");
			PAGE.FORM1.setItemValue("operType","${param.operType}");
			PAGE.FORM1.setItemValue("rateCd","${param.socCode}");
			PAGE.FORM1.setItemValue("agrmTrm","${param.agrmTrm}");
			PAGE.FORM1.setItemValue("reqInDay","${param.reqInDay}"); */
			
			var sdata = {
					requestKey: "${param.requestKey}"
					, cntpntShopId : "${param.cntpntShopId}"
					, reqBuyType : "${param.reqBuyType}"
					, operType : "${param.operType}"
					, rateCd : "${param.socCode}"
					, agrmTrm : "${param.agrmTrm}"
					, reqInDay : "${param.reqInDay}"
					, onOffType : "${param.onOffType}"
			}
			
			// 신청가능 부가서비스 목록
			PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpSimpRateList.json", sdata);
			
			// 부가서비스배타관계
			PAGE.GRID2.list(ROOT + "/rcp/rcpMgmt/getExclusiveRateList.json");
// 			$("#FORM1").hide();
			$("#GRID2").hide();
		}
	};
</script>