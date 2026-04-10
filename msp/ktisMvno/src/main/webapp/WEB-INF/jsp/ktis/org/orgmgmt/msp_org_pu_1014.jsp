<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_pu_1014.jsp
	 * @Description : 대리점/판매점 조직팝업 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @
	 * @author : 박효진
	 * @Create Date : 2022.06.07.
	 */
%>

<!-- header -->
<div class="page-header">
	<h1>대리점/판매점 검색 팝업</h1>
</div>
<!-- //header -->

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

<%@ include file="org.formitems" %>

var DHX = {

	// 검색 조건
	FORM1 : {
		items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
					
					{type:"block", list:[
						{type:"input", label:"조직 명", name:"orgnNm", width:100, offsetLeft:10}, 
						{type:"newcolumn", offset:30},
						{type:"select", label:"조직 유형", name:"typeCd", width:100},
						{type:"newcolumn", offset:30}, 
					]},
					{type:"newcolumn", offset:21},
					{type:"button", name:"btnSearch", value:"조회", className:"btn-search1"} 
			],
		onButtonClick : function(btnId) {
			var form = this;
			switch (btnId) {
				case "btnSearch":
					PAGE.GRID1.list(ROOT + "/org/orgmgmt/getSalesOrgnNewList.json", form);
					break;
			}
		}
	},
	
	// 조직 리스트
	GRID1 : {
		
		css : {
			height : "300px"
		},
		title : "조회결과",
		header : "조직ID,조직명,조직유형,조직레벨,조직상태,상위조직ID,상위조직명,적용시작일,적용종료일",
		columnIds : "orgnId,orgnNm,typeNm,orgnLvlNm,orgnStatNm,hghrOrgnCd,hghrOrgnNm,applStrtDt,applCmpltDt",
		colAlign : "left,left,left,left,center,center,left,center,center",
		colTypes : "ro,ro,ro,ro,ro,ro,ro,roXd,roXd",
		colSorting : "str,str,str,str,str,str,str,str,str",
		paging:true,
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
			
			/* 20[대리점] , 30[판매점] 2022.06.02 추가 */
			mvno.cmn.ajax4lov( ROOT + "/cmn/login/getCommCombo.json", {grpId:"CMN0102"}, PAGE.FORM1, "typeCd");
						
		}
	
	};
	
</script>