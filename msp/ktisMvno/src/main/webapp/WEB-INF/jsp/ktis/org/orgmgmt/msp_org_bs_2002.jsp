<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_2002.jsp
	 * @Description : 조직변경이력관리 화면
	 * @
	 * @ 수정일        수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2019.02.28 김민지 최초생성
	 * @
	 * @author : 김민지
	 * @Create Date : 2019.02.28
	 */
%>

<!-- 조직 조회 폼 -->
<div id="FORM1" class="section-search"></div>

<!-- 조직 리스트 그리드 -->
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

var DHX = {
	// 검색 조건
	FORM1 : {
		items : [ 
			{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
			,{type:"block", labelWidth:30 , list:[    
				{type : "calendar",width : 100,label : "변경일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d", name : "sysRdateS", offsetLeft:10},
				{type : "newcolumn"},
				{type : "calendar",label : "~",name : "sysRdateE",labelAlign : "center",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100},
				{type : "newcolumn"},
				{type:"select", width:80, label:"검색구분",name:"searchCd", offsetLeft:10},
				{type:"newcolumn"},
				{type:"input", width:100, name:"searchVal"},
				{type:"newcolumn"},
				{type : "select",label : "조직 유형",name : "typeCd", width:90, offsetLeft:10},
				{type:"newcolumn", offset:20}, 
				{type : "select",label : "상태",name : "orgnStatCd", width:90},
				{type:"newcolumn", offset:20},
			]}
			,{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
			//버튼 여러번 클릭 막기 변수
			, {type : 'hidden', name: "btnCount1", value:"0"}
		],
		onButtonClick : function(btnId) {
			var form = this;
			switch (btnId) {
				case "btnSearch":
					var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
					PAGE.FORM1.setItemValue("btnCount1", btnCount2);
					if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") {
						return; //버튼 최초 클릭일때만 조회가능하도록, 로딩중 여러번 클릭 막기
					}
					PAGE.GRID1.list(ROOT + "/org/orgmgmt/listOrgChgHist.json", form,  {
						callback : function(){
							PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
						}
					});
					break;
			}
		},
		onChange : function(name, value) {
			var form = this;
					
			// 검색구분
			if(name == "searchCd") {
				PAGE.FORM1.setItemValue("searchVal", "");
				if(value == null || value == "" ){
					var searchEndDt = new Date().format('yyyymmdd');
					var time = new Date().getTime();
					time = time - 1000 * 3600 * 24 * 3;
					var searchStartDt = new Date(time);
					
					// 변경일자 Enable
					PAGE.FORM1.enableItem("sysRdateS");
					PAGE.FORM1.enableItem("sysRdateE");
					
					PAGE.FORM1.setItemValue("sysRdateS", searchStartDt);
					PAGE.FORM1.setItemValue("sysRdateE", searchEndDt);
					
				} else {
					// 변경일자 Disable
					PAGE.FORM1.disableItem("sysRdateS");
					PAGE.FORM1.disableItem("sysRdateE");
					
					PAGE.FORM1.setItemValue("sysRdateS", "");
					PAGE.FORM1.setItemValue("sysRdateE", "");
					
				}
			}
		},		
		onValidateMore : function (data){
			if (this.getItemValue("sysRdateS", true) > this.getItemValue("sysRdateE", true)) {
				this.pushError("sysRdateE", "조회기간", "종료일자가 시작일자보다 클 수 없습니다.");
				this.resetValidateCss();
				PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
			}
			if( data.searchCd != "" && data.searchVal.trim() == ""){
				this.pushError("searchCd", "검색어", "검색어를 입력하세요");
				PAGE.FORM1.setItemValue("searchVal", ""); // 검색어 초기화
				PAGE.FORM1.setItemValue("btnCount1", 0); //로딩 끝나면 초기화 - 조회 버튼
			}
		}
	}
	// 조직 리스트 - GRID1
	,GRID1 : {
		css : {
			height : "575px"
		},
		title : "조회결과",
		header : "변경일자,조직ID,조직명,조직유형,KT조직ID,상위조직,상태,사용유무,적용종료일,지번주소,도로명주소,마케팅매니저ID,MVNO조직ID", //13
		columnIds : "orgChgDate,orgnId,orgnNm,typeNm,ktOrgId,hghrOrgnNm,orgnStatNm,useYn,applCmpltDt,arnoAdr,roadnAdr,admUserId,mvnoOrgId",
		colAlign : "center,center,left,center,center,center,center,center,center,left,left,center,center",
		colTypes : "roXd,ro,ro,ro,ro,ro,ro,ro,roXd,ro,ro,ro,ro",
		colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str",
		widths : "100,100,150,80,80,150,80,70,100,300,300,90,90",
		paging : true,
		pageSize : 20,
		buttons : {
			hright : {
				btnExcel : { label : "엑셀다운로드" }
			}
		},
		onButtonClick : function(btnId, selectedData) {
			switch (btnId) {
				case "btnExcel":

					if(PAGE.GRID1.getRowsNum() == 0){
						mvno.ui.alert("데이터가 존재하지 않습니다");
						return;
					}
					
					var searchData =  PAGE.FORM1.getFormData(true);
					mvno.cmn.download(ROOT + "/org/orgmgmt/listOrgChgHistExcel.json?menuId=ORG1000011", true, {
						postData:searchData
					});				
					break;	
			}
		}
	}
};

var PAGE = {
	title : "${breadCrumb.title}",
	breadcrumb : " ${breadCrumb.breadCrumb}",
	//buttonAuth: ${buttonAuth.jsonString},
	onOpen : function() {
		mvno.ui.createForm("FORM1");
		mvno.ui.createGrid("GRID1");
		
		//검색날짜
		var sysRdateE = new Date().format('yyyymmdd');
		var time = new Date().getTime();
		time = time - 1000 * 3600 * 24 * 3;
		var sysRdateS = new Date(time);
		
		PAGE.FORM1.setItemValue("sysRdateS", sysRdateS);
		PAGE.FORM1.setItemValue("sysRdateE", sysRdateE);

		//검색구분
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0060"}, PAGE.FORM1, "searchCd");
		
		//조직유형
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0003"}, PAGE.FORM1, "typeCd");
		
		//상태
		mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0005"}, PAGE.FORM1, "orgnStatCd");
		
	}
};
</script>
