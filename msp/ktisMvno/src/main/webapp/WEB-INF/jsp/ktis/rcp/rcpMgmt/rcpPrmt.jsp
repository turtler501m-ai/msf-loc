<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
%>

<!-- header -->
<div class="page-header">
	<h1>프로모션정책 팝업</h1>
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
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
				{type: 'block', list: [
					{type:"hidden", label:"", name:"reqInDay"},
					{type:"hidden", label:"", name:"orgnId"},
// 					{type:"select", label:"신청유형", name:"operType", labelWidth:60, width:100, offsetLeft:0, userdata:{lov:"RCP0003"}, required:true},
					{type:"select", label:"신청유형", name:"operType", labelWidth:60, width:100, offsetLeft:0, required:true},
					{type:'newcolumn'},
					{type:"select", label:"약정기간", name:"agrmTrm", labelWidth:60, width:100, offsetLeft:20, required:true},
					{type:'newcolumn'},
					{type:"select", label:"요금제", name:"rateCd", labelWidth:60, width:180, offsetLeft:10}
				]},
				{type: 'block', list: [
// 					{type:"select", label:"가입경로", name:"onOffType", width:100, offsetLeft:0, userdata:{lov:"RCP0007"}}
					{type:"select", label:"가입경로", name:"onOffType", width:100, offsetLeft:0}
				]},
				{type : "newcolumn"}, 
				{type : "button", name : "btnSearch", value : "조회", className:"btn-search2"} 
			],
			onButtonClick : function(btnId) {
				var form = this;
				switch (btnId) {
					case "btnSearch":
						PAGE.GRID1.list(ROOT + "/rcp/rcpMgmt/getRcpPrmtList.json", form);
						break;
				}
			}
		},

		// ----------------------------------------
		GRID1 : {
			css : {
				height : "300px"
			},
			title : "조회결과",
			header : "프로모션코드,프로모션명,신청유형,가입경로,요금제,요금제코드,프로모션혜택,약정기간,신청유형",
			columnIds : "promotionCd,promotionNm,joinTypeNm,joinRouteNm,rateNm,rateCd,benefitCmmt,agrmTrm,operType",
			widths : "100,220,80,80,150,100,250,100,100",
			colAlign : "center,left,center,center,left,center,left,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str",
			hiddenColumns:[5,7,8],
			paging:true,
			pageSize:20,
			buttons : {
				center : {
					btnApply : { label: "확인" },
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
					mvno.ui.closeWindow(selectedData, true);
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
		
		mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0053"}, PAGE.FORM1, "agrmTrm");
		
		mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0049",orderBy:"etc1"}, PAGE.FORM1, "operType"); // 신청유형
		mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM1, "onOffType"); // 가입경로
		
		// 신청일자
		if("${param.reqInDay}" != ''){
			PAGE.FORM1.setItemValue("reqInDay","${param.reqInDay}");
		}
		
		//조직정보
		if("${param.orgnId}" != ''){
			PAGE.FORM1.setItemValue("orgnId","${param.orgnId}");
		}
		
		// 신청유형
		if("${param.operType}" != ''){
			PAGE.FORM1.setItemValue("operType","${param.operType}"); 
			PAGE.FORM1.disableItem("operType");
		}
		
		// 약정기간
		if("${param.agrmTrm}" != ''){
			PAGE.FORM1.setItemValue("agrmTrm","${param.agrmTrm}");
			PAGE.FORM1.disableItem("agrmTrm");
		}
		
		// 가입경로
		if("${param.onOffType}" != ''){
			PAGE.FORM1.setItemValue("onOffType","${param.onOffType}");
			PAGE.FORM1.disableItem("onOffType");
		}
		
		getPrmtPrdcInfo();
	}
};

	function getPrmtPrdcInfo(){
		
		var paramRateCd = "${param.rateCd}";
		
		var params = {
				'reqInDay':PAGE.FORM1.getItemValue('reqInDay')
				,'rateCd':paramRateCd
			};
		
		mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/getRcpPrmtPrdcList.json", params, function(resultData) {
			var totalCount = Number(resultData.result.data.rows.length);
			var prdcData = "";
			
			if(totalCount > 0){
				
				prdcData = [{text:"- 전체 -", value:""}];
				
				for(var idx = 0; idx < totalCount; idx++){
					var rateNm = resultData.result.data.rows[idx].rateNm;
					var rateCd = resultData.result.data.rows[idx].rateCd;
					var data = {text:rateNm +"["+rateCd+"]", value:rateCd};
					
					prdcData.push(data);
				};
	 			
			}else{
				prdcData = [{text:"- 없음 -", value:paramRateCd}];
			}
			
			PAGE.FORM1.reloadOptions("rateCd", prdcData);
			
			// 요금제
			if(paramRateCd != ''){
				PAGE.FORM1.setItemValue("rateCd",paramRateCd);
				PAGE.FORM1.disableItem("rateCd");
			}
		});
	}
</script>