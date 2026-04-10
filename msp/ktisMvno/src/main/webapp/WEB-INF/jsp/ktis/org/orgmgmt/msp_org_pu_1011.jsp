<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_pu_1011.jsp
	 * @Description : 판매점 조직팝업 화면
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2016.03.03 신희경 최초생성
	 * @
	 * @author : 신희경
	 * @Create Date : 2016.03.03.
	 */
%>

<!-- header -->
<div class="page-header">
	<h1>판매점 조직 검색 팝업</h1>
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
						{type : "input",label : "조직 ID",name : "orgnId", width:100, offsetLeft:10},
						{type:"newcolumn", offset:30},
						{type : "input",label : "조직 명",name : "orgnNm", width:100, offsetLeft:10},
						{type:"newcolumn", offset:30},
// 						{type : "select",label : "조직 상태",name : "orgnStatCd", width:100,userdata: { lov: 'CMN0005'}},
						{type : "select",label : "조직 상태",name : "orgnStatCd", width:100},
						{type:"newcolumn", offset:30},
					]},
					{type:"newcolumn", offset:21},
					{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
			],
		onButtonClick : function(btnId) {
			var form = this;
			switch (btnId) {
				case "btnSearch":
					PAGE.GRID1.list(ROOT + "/org/orgmgmt/getShopOrgnList.json", form);
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
		header : "조직ID,조직명,사업자등록번호,조직상태,상위조직ID,상위조직명,적용시작일,적용종료일",
		columnIds : "orgnId,orgnNm,bizRegNum,orgnStatNm,hghrOrgnCd,hghrOrgnNm,applStrtDt,applCmpltDt",
		colAlign : "center,left,center,center,center,left,center,center",
		colTypes : "ro,ro,roXr,ro,ro,roXd,roXd,roXd",
		colSorting : "str,str,str,str,str,str,str,str",
		widths : "120,120,120,95,120,120,120,120",
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
						break
				}
			}
	}
};

var PAGE = {
		
		onOpen : function() {
			
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0005"}, PAGE.FORM1, "orgnStatCd"); // 조직상태
		}
		
	};
	
</script>