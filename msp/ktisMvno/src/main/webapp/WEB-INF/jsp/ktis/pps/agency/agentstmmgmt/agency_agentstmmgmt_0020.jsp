<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : agency_agentstmmgmt_0020.jsp
 * @Description : 대리점정산관리
 * @Modification Information
 *
 *   수정일 			수정자				 수정내용
 *  -------		--------		---------------------------
 *  2017.05.01	김 웅				최초 생성
 *
 * author 김 웅
 * since 2017.05.01
 */
 %>
	<!-- 화면 배치 -->
	
	<div id="GRID1" class="section-full"></div>
			
	<!-- Script -->
	<script type="text/javascript">
	
	function setCurrentDate(form, name)
	{
		form.setItemValue(name, new Date());
	}
	
	var DHX = {
		GRID1 : {
			css : {
				height : "540px"
			},
			title : "조회결과",
			header : "정산월,정산액,#cspan,실적,#cspan,#cspan,위탁수수료,#cspan,#cspan,유지수수료,#cspan,#cspan,프로모션수수료,#cspan,#cspan,환수,#cspan,#cspan,전월미정산,#cspan,기타정산,#cspan,지급수수료,#cspan,등록일자,agentId",
			header2 :"#rspan,카드,현금,개통,해지,실정산,건수,카드,현금,건수,카드,현금,건수,카드,현금,건수,카드,현금,카드,현금,카드,현금,카드,현금,#rspan,#rspan",
			columnIds : "billMonth,opPps,opOpenAmt,opOpenCnt,opCanCnt,opRealCnt,psdCnt,psdPps,psdAmt,mngCnt,mngPps,mngAmt,prmCnt,prmPps,prmAmt,rfCnt,rfPps,rfAmt,lastPps,lastAmt,etcPps,etcAmt,totPps,totAmt,regDt,agentId",
			widths : "90,70,100,70,70,70,70,70,100,70,70,100,70,70,100,70,70,100,70,100,70,100,70,100,110,0",
			colTypes : "ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ro,ro",
			colSorting : "str,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,str,str",
			colAlign : "center,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,center,center",
			paging : true,
			pageSize :15,
			pagingStyle :2,
			
			buttons : {
				/*
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				},*/
				right :{
					btnReg :{label :"보기"}
				}
			},
			onButtonClick : function(btnId) {

				switch (btnId) {
					case "btnDnExcel":
						
						var totCnt = PAGE.GRID1.getRowsNum();
						if(totCnt <= 0){
							mvno.ui.notify("출력건수가 없습니다.");
						   return;
						}
						//alert(totCnt);
						if(totCnt>5000)
						{
							mvno.ui.notify("엑셀출력건수가 5,000건이상이면 시간(수분~수십분)이 걸릴수 있습니다.\n 잠시 기다려주시기 바랍니다.");
						   
						}
						var url = "/pps/agency/agentstmmgmt/agentAgentStmHistoryListExcel.json";

						//var searchData =  PAGE.FORM1.getFormData(true);
						var searchData =  null;
						
						//console.log("searchData",searchData);
							
						mvno.cmn.download(url+"?menuId=PPS2700001",true,{postData:searchData});
						break;
						
					case "btnReg":
						
						var rowData = PAGE.GRID1.getSelectedRowData();
						
						if(rowData == null){
							mvno.ui.notify("선택된 Data가 없습니다.");
							return;
						}
						
						var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/pps/ppsStmSelfForm.jrf" + "&arg=";
						param = encodeURI(param);
						
						var arg = "agentId#" + rowData.agentId + "#billMonth#" + rowData.billMonth + "#";
						arg = encodeURIComponent(arg);
						
						param = param + arg;
						
						var msg = "대리점수수료내역서를 출력하시겠습니까?";
						mvno.ui.confirm(msg, function() {
							mvno.ui.popupWindow("/cmn/report/report.do"+param, "대리점수수료내역서 ", 900, 700, {
								onClose: function(win) {
									win.closeForce();
								}
							});
						});
						
						break;
				}
			}
		} // GRID1 End

	};
	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : "${breadCrumb.breadCrumb}", 
			buttonAuth: ${buttonAuth.jsonString},
			onOpen : function() {

				mvno.ui.createGrid("GRID1");
				//setCurrentDate(PAGE.FORM1, "endDt");
				var url = ROOT + "/pps/agency/agentstmmgmt/agentAgentStmSelfList.json";
//	            PAGE.GRID1.list(url);
				PAGE.GRID1.list(url, null, { callback : gridOnLoad });

			

			}

		};
	
	function gridOnLoad  ()
	{
		
	};
		
		
	</script>