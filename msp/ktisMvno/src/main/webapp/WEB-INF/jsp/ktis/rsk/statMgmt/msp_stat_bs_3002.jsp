<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  /**
  * @Class Name : msp_stat_bs_3002.jsp
  * @Description : 청구항목별자료 데이터유형 추가
  * @Modification Information
  *
  *   수정일		수정자		수정내용
  *  ------------------------------------------
  *  2018.03.23		권오승		최초 생성
  *
  */
%>
<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
	var today = new Date();
	var thisMonth = today.getFullYear()+""+fnMonth(today.getMonth() -1);
	var DHX = {
			//------------------------------------------------------------
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					, {type:"block", list:[
						{type:"calendar", label:"기준월", name:"stdrDt", value:thisMonth, width:70, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:10, required:true}
						,{type: "newcolumn"}
						,{type: "label", label: " ", labelWidth: 20}
						, {type:"newcolumn"}
						,{type:"label", labelWidth:500, name:"pointLabel"}
					]},
						
					//조회 버튼
					, {type:"newcolumn"}
					, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}
				]
				, onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							if (! this.validate())  return;
							var url = ROOT + "/rsk/statMgmt/getInvItemDataList.json";
							PAGE.GRID1.list(url, form);
							break;
					}
				}
			},
			//-------------------------------------------------------------
			GRID1 : {
				css : {
					height : "600px"
				}
				, title : "조회결과"
				, header : "청구년월,최근파일명,청구항목코드,청구항목명,즉납분,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan" //11
							     +",#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,정기청구,#cspan" //9
							     + ",#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan"    //8
							     + ",차액정산금(즉납분),#cspan,#cspan,#cspan,#cspan,#cspan,#cspan,#cspan"    //9
							     + ",차액정산금(정기청구),#cspan,#cspan,#cspan,#cspan,#cspan"    //7
				, header2 : "#rspan,#rspan,#rspan,#rspan,수납(+),수납(+)부가세,수납취소(-),수납취소(-)부가세,조정(-),조정(-)부가세,조정취소(+)"
				 				  + ",조정취소(+)부가세,청구,청구부가세,환불(-),환불(-)부가세,환불취소(+),환불취소(+)부가세,수납(+),수납(+)부가세"
								  +",수납취소(-),수납취소(-)부가세,조정(-),조정(-)부가세,조정취소(+),조정취소(+)부가세,청구,청구부가세"	//8
								  +",수납(+),수납(+)부가세,수납취소(-),수납취소(-)부가세,환불(-),환불(-)부가세,환불취소(+),환불취소(+)부가세"	//8
								  +",수납(+),수납(+)부가세,수납취소(-),수납취소(-)부가세"	//4
				, columnIds : "billym,newfilenm,billcd,itemnm,ipymnpym,ipymnpymvat,ipymnbck,ipymnbckvat,ipymnadj,ipymnadjvat,ipymnadjr"
									+ ",ipymnadjrvat,ipymninv,ipymninvvat,ipymnrfn,ipymnrfnvat,ipymnrfnr,ipymnrfnrvat,bpymnpym,bpymnpymvat"
									+",bpymnbck,bpymnbckvat,bpymnadj,bpymnadjvat,bpymnadjr,bpymnadjrvat,bpymninv,bpymninvvat"
									+",iadpym,iadpymvat,iadbck,iadbckvat,iadrfn,iadrfnvat,iadrfnr,iadrfnrvat"
									+",badpym,badpymvat,badbck,badbckvat"
				, widths : "80,120,80,150,80,120,80,120,80,120,80" 
								+",120,80,120,80,120,120,80,80,120"
								+ ",80,120,80,120,80,120,80,120"
								+ ",80,120,80,120,80,120,80,120"
								+ ",80,120,80,120"
				, colAlign : "center,left,left,left,right,right,right,right,right,right,right" 
								+ ",right,right,right,right,right,right,right,right,right"
								+ ",right,right,right,right,right,right,right,right"
								+ ",right,right,right,right,right,right,right,right,right"
								+ ",right,right,right,right"
				, colTypes : "ro,ro,ro,ro,ron,ron,ron,ron,ron,ron,ron"
								+",ron,ron,ron,ron,ron,ron,ron,ron,ron"
								+",ron,ron,ron,ron,ron,ron,ron,ron,ron,ron"
								+",ron,ron,ron,ron,ron,ron,ron,ron,ron"
								+",ron,ron,ron,ron"
				, colSorting : "str,str,str,str,int,int,int,int,int,int,int"
								+",int,int,int,int,int,int,int,int,int" 
								+ ",int,int,int,int,int,int,int,int"
								+ ",int,int,int,int,int,int,int,int,int"
								+ ",int,int,int,int"
				, paging : false
				, onRowDblClicked : function(rowId, celInd) {
				}
				, buttons : {
					hright : {
						btnExcel : { label : "엑셀다운로드" }
					}
				}
				, onButtonClick : function(btnId){
					var form = this;
					switch(btnId){
						case "btnExcel" :
							if(PAGE.GRID1.getRowsNum() == 0){
								mvno.ui.alert("데이터가 존재하지 않습니다");
								return;
							}
							
							var searchData =  PAGE.FORM1.getFormData(true);
							mvno.cmn.download(ROOT + "/rsk/statMgmt/getInvItemDataListExcel.json?menuId=MSP5004002", true, {postData:searchData}); 
							
							break;
					}
				}
			}
	};
	
	function fnMonth(mon) {
		var month;
		mon = Number(mon);
		mon++;
		
		if(Number(mon) < 10) {
			month = "0"+mon;
		} else {
			month = mon;
		}
		
		return month;
	}
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		buttonAuth:${buttonAuth.jsonString},
		onOpen : function() {
			
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");

			//mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2027"}, PAGE.FORM1, "stdrTm"); // 기준시간
			var fixGrid = PAGE.GRID1.getColIndexById("itemnm");
			// IE 인 경우만 SPLIT 실행
			if(mvno.cmn.isIE()) PAGE.GRID1.splitAt(fixGrid+1);
			PAGE.FORM1.setItemLabel("pointLabel", "<span style='color:red;'> ** 정기수납내역은 매월 6일과 21일에 갱신됩니다.</span>");
		}
		
	};
	
</script>