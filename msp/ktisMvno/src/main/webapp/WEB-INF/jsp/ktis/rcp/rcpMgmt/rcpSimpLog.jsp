<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1"></div>

<!-- Script -->
<script type="text/javascript">

	var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [ 
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
				,{type:"label", name:"formLabel", labelWidth:500}
				,{type:"newcolumn"}
				,{type:"input", name:"cntt", width: 570, offsetLeft:5, rows: 14, readonly:true}
			]
			,buttons : {
				center : {
					btnClose : {label : "닫기" }
				}
			}
			,onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					
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
			PAGE.FORM1.setItemLabel("formLabel", "<span style='color:red;'> * 10초마다 연동결과를 조회합니다.</span>");
			
			var sdata = {resNo:"${param.resNo}"}
			
			// 예약번호
			if(sdata.resNo == ""){
				mvno.ui.alert("예약번호가 존재하지 않습니다.");
				mvno.ui.closeWindow();
			}else{
				// 최초 실행
				mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getOsstRsltMsg.json", sdata, function(resultData) {
					var data = resultData.result.data;
					
					if(data == null){
						mvno.ui.alert("연동결과가 없습니다.");
					}else{
						PAGE.FORM1.setItemValue("cntt", data.rsltMsg);
					}
					
					// 이후 5초마다 연동결과 조회
					setInterval(function() {
						mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getOsstRsltMsg.json", sdata, function(resultData) {
							var data = resultData.result.data;
							
							if(data == null){
								mvno.ui.alert("연동결과가 없습니다.");
							}else{
								PAGE.FORM1.setItemValue("cntt", data.rsltMsg);
							}
						});
					}, 10000);
				});
			}
			
		}
	};
	
</script>