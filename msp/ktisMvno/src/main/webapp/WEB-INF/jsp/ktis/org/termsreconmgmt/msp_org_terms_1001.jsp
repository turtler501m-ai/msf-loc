<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_terms_1001.jsp
	 * @Description : 약관발송대상자
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2016.03.17 이춘수 최초생성
	 * @
	 * @author : 이춘수
	 * @Create Date : 2016. 3. 17.
	 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

 var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},

				{type:"block", list:[ 
					{type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'trgtYm', label: '검색일', calendarPosition: 'bottom', readonly:true ,width:100,offsetLeft:10},
					{type:"newcolumn", offset:15},
					{type : "input",label : "사용자명", width:80,name : "name"},
					{type:"newcolumn", offset:15},
					{type : "select",label : "사용자구분", width:80,name : "typeCd"}, 
					{type:"newcolumn", offset:15},
					{type : "select",label : "LMS처리여부", width:80,name : "sendYn"}, 
					{type:"newcolumn", offset:15},
					{type : "select",label : "LMS처리결과", width:120,name : "sendRsn"}, 
					{type:"newcolumn", offset:15},
				]},

				{type:"newcolumn", offset:270},
					{type : "button",name : "btnSearch",value : "조회", className:"btn-search1"}  
				],
	
				onButtonClick : function(btnId) {
					var form = this;

					switch (btnId) {
						case "btnSearch" :
							PAGE.GRID1.list(ROOT + "/org/termssendmgmt/getTermsSendTrgt.json", form);
						break;
					}
				}
		},
			//-------------------------------------------------------------
		GRID1 : {
			css : {
				height : "600px"
			},
			title : "조회결과",
			header : "대상일,메일전송여부,LMS처리여부,LMS처리결과,사용자구분,계약번호,사용자명,사용자상태,개통일,해지일,핸드폰번호,E-mail,수정자,수정일자",  //12
			columnIds : "trgtYm,resultYn,sendYn,sendRsn,typeCdNm,contractNum,name,subStatNm,openDt,tmntDt,mobileNo,email,rvisnNm,rvisnDttm",
			widths : "100,90,90,150,100,100,80,80,100,100,100,140,80,100",
			colAlign : "center,center,center,left,center,center,center,center,center,center,center,left,center,center",
			colTypes : "roXd,ro,ro,ro,ro,ro,ro,ro,roXd,roXd,roXp,ro,ro,roXd",
			colSorting : "str,str,str,str,str,str,str,str,str,str,,str,str,str,str,str",
			paging : true,
// 			multiCheckbox: true,
			pageSize : 20,
			buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				},
// 				right : {
// 					btnReg : { label : "메일처리실행" },
// 					btnDel : { label : "메일미처리실행" }
// 				}
			},
   			onButtonClick : function(btnId, selectedData) {
  				var form = this;
  				switch (btnId) {
					case "btnDnExcel":

					if(PAGE.GRID1.getRowsNum() == 0)
					{
						mvno.ui.alert("데이터가 존재하지 않습니다.");
						return;
						
					}
					else
					{
						mvno.cmn.download('/org/termssendmgmt/termsSendListEx.json?trgtYm=' + PAGE.FORM1.getItemValue("trgtYm",true) + "&name=" + PAGE.FORM1.getItemValue("name",true) + "&resultYn=" + PAGE.FORM1.getItemValue("resultYn",true) +  "&typeCd=" + PAGE.FORM1.getItemValue("typeCd",true) + "&menuId=MSP1030003",true);
						break; 
					}
					case "btnReg" ://일괄저장
						var arrData = form.getCheckedRowData();
						
						if(arrData == null || arrData == '')
						{
							mvno.ui.alert("ECMN0003");//선택한(체크된) 데이터가 없습니다.
							return;
						}
						else
						{
							var sData = {
							};
							
							sData.items = arrData;
							
							mvno.ui.confirm("CCMN0015", function(){
								 mvno.cmn.ajax4json(ROOT + "/org/termssendmgmt/updateResultY.json", sData, function(result) {
									mvno.ui.notify("NCMN0001");
									PAGE.GRID1.refresh();
								});
							});
							break;
						}
					case "btnDel" :
						var arrData = form.getCheckedRowData();
						
						if(arrData == null || arrData == '')
						{
							mvno.ui.alert("ECMN0003");//선택한(체크된) 데이터가 없습니다.
							return;
						}
						else
						{
							var sData = {
							};
							
							sData.items = arrData;
							
							mvno.ui.confirm("CCMN0015", function(){
								 mvno.cmn.ajax4json(ROOT + "/org/termssendmgmt/updateResultN.json", sData, function(result) {
									mvno.ui.notify("NCMN0001");
									PAGE.GRID1.refresh();
								});
							});
							
							break;
						}
					}
				}
			}
	};
			
var PAGE = {
		title: "${breadCrumb.title}",
		breadcrumb: "${breadCrumb.breadCrumb}",

		buttonAuth: ${buttonAuth.jsonString},

		onOpen : function() {
	
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");

			var trgtYm = new Date().format('yyyymmdd');
			
			PAGE.FORM1.setItemValue("trgtYm", trgtYm);
			
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0105",orderBy:"etc6"}, PAGE.FORM1, "typeCd"); // 사용자구분
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0237",orderBy:"etc6"}, PAGE.FORM1, "sendYn"); // 처리여부
			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0236"}, PAGE.FORM1, "sendRsn"); // 처리결과
	   }

};
</script>