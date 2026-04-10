<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : agency_agentstmmgmt_0010.jsp
 * @Description : 대리점 정산 내역
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
			header : "정산월,기본,#cspan,유심,#cspan,GRADE,#cspan,우량,#cspan,명변,#cspan,재충전,#cspan,자동이체,#cspan,무료충전환수액,환수,#cspan,조정,#cspan,수수료조정,#cspan,합계,#cspan,메모,등록관리자,등록일자,agentId",
			header2 :"#rspan,카드,현금,카드,현금,카드,현금,카드,현금,카드,현금,카드,현금,카드,현금,#rspan,카드,현금,카드,현금,카드,현금,카드,현금,#rspan,#rspan,#rspan,#rspan",
			columnIds : "billMonth,basicCardSd,basicCashSd,usimCardSd,usimCashSd,gradeCardSd,gradeCashSd,ulCardSd,ulCashSd,mbCardSd,mbCashSd,rcgCardSd,rcgCashSd,cmsCardSd,cmsCashSd,refundFreeRcgAmt,refundCardSd,refundCashSd,modCardSd,modCashSd,modAgentCardSd,modAgentCashSd,cardSd,cashSd,remark,regAdminNm,regDt,agentId",
			widths : "90,100,100,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,110,0",
			colTypes : "ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ro,ro,ro,ro",
			colSorting : "str,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,int,str,str,str,str",
			colAlign : "center,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,right,center,center,center,center",
			paging : true,
			pageSize :15,
			pagingStyle :2,
			/*
			buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				}
			},
			*/
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
				var url = ROOT + "/pps/agency/agentstmmgmt/agentAgentStmHistoryList.json";
//	            PAGE.GRID1.list(url);
				PAGE.GRID1.list(url, null, { callback : gridOnLoad });

			

			}

		};
	
	function gridOnLoad  ()
	{
		
	};
		
		
	</script>