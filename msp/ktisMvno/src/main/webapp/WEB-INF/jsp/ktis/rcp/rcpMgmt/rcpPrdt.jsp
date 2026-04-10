<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header -->
<div class="page-header">
	<h1>재고확인 조회</h1>
</div>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

var DHX = {
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:80}
				,{type: "block", list: [
					{type: 'input', name: 'prdtSrlNum', label: '단말일련번호', labelWidth:80, width: 120, }
					,{type: "newcolumn"}
					,{type:"select", label:"단말모델", name:"prdtId", labelWidth:60, width:230, offsetLeft:10}
					,{type:"hidden", label:"", name:"reqInDay"}
					,{type:"hidden", label:"", name:"orgnId"}
				]}
				,{ type : "button",name : "btnSearch", value : "조회", className:"btn-search1" }
			]
			,onButtonClick : function(btnId) {
				var form = this; // 혼란방지용으로 미리 설정 (권고)
				
				switch (btnId) {
					case "btnSearch" :
						PAGE.GRID1.list(ROOT + ROOT + "/rcp/rcpMgmt/getPrdtStatList.json", form);
						
						break;
				}
			}
		} /* End FORM1 */
		
		,GRID1 : {
			css : {
				height : "280px"
			},
			title : "조회결과",
			header : "단말상태,조직명,단말일련번호,단말코드,단말모델,단말색상,orgnId,prdtId,prdtColrCd,mnfctId",
			columnIds : "logisProcStatNm,orgnNm,prdtSrlNum,prdtCode,prdtNm,prdtColrNm,orgnId,prdtId,prdtColrCd,mnfctId",
			widths : "100,120,100,100,130,80,10,10,10,10",
			colAlign : "center,left,center,center,left,center,center,center,center,center",
			colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
			colSorting : "str,str,str,str,str,str,str,str,str,ro",
			hiddenColumns:[6,7,8,9],
			//paging:false,
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
}

var PAGE = {
		 onOpen:function() {
			
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");
			
			// 단말일련번호
			if("${param.reqPhoneSn}" != ''){
				PAGE.FORM1.setItemValue("prdtSrlNum","${param.reqPhoneSn}");
			}
			
			// 신청일자
			if("${param.reqInDay}" != ''){
				PAGE.FORM1.setItemValue("reqInDay","${param.reqInDay}");
			}
			
			// 조직ID
			if("${param.orgnId}" != ''){
				PAGE.FORM1.setItemValue("orgnId","${param.orgnId}");
			}
			
			mvno.cmn.ajax4lov( ROOT + "/rcp/rcpMgmt/getRcpCommon.json" 
								,{
									grpId:"PRDT"
									,orgnId:PAGE.FORM1.getItemValue("orgnId")
									,reqBuyType:"MM"
									,reqInDay:PAGE.FORM1.getItemValue("reqInDay")
								}
								,PAGE.FORM1 
								,"prdtId");
			
			// 단말모델
			if("${param.prdtId}" != ''){
				PAGE.FORM1.setItemValue("prdtId","${param.prdtId}");
				PAGE.FORM1.disableItem("prdtId");
			}
			
			PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
		}
};
</script>