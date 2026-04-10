<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  /**
  * @Class Name : msp_stat_bs_4008.jsp
  * @Description : 채널별영업실적 데이터유형 추가
  * @Modification Information
  *
  *   수정일		수정자		수정내용
  *  ------------------------------------------
  *  2017.12.29		이상직		최초 생성
  *
  */
%>
<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
	var curDt = new Date().format("yyyymmdd");
	
	var DHX = {
			//------------------------------------------------------------
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					, {type:"block", list:[
						{type:"calendar", label:"기준일자", name:"stdrDt", value:curDt, width:100, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", offsetLeft:10, required:true}
						, {type:"newcolumn"}
						, {type:"select", label:"기준시간", name:"stdrTm", offsetLeft:20, required:true}
					]}
					//조회 버튼
					, {type:"newcolumn"}
					, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}/* btn-search(열갯수1~4) */
				]
				, onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							if (! this.validate())  return;
							
							if(new Date(PAGE.FORM1.getItemValue("stdrDt")).format("yyyymmdd") < "20180201"){
								mvno.ui.alert("2018년 2월 1일 이전 자료는 조회할 수 없습니다.");
								return;
							}
							
							var url = ROOT + "/rsk/statMgmt/getChnlDataSttcList.json";
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
				, header : "선후불,상품구분,채널유형,데이터유형,유지건수,월,#cspan,#cspan,#cspan,#cspan,#cspan,일,#cspan,#cspan,#cspan,#cspan,#cspan"
				, header2 : "#rspan,#rspan,#rspan,#rspan,#rspan,개통,취소,신규,해지,해지복구,순증,개통,취소,신규,해지,해지복구,순증"
				, columnIds : "pppo,prodNm,chnlTpNm,dataType,keepCnt,mmOpenCnt,mmCanCnt,mmNewCnt,mmTmntCnt,mmTmntRclCnt,mmNetCnt,openCnt,canCnt,newCnt,tmntCnt,tmntRclCnt,netCnt"
				, widths : "80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80"
				, colAlign : "center,center,center,center,right,right,right,right,right,right,right,right,right,right,right,right,right"
				, colTypes : "ro,ro,ro,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron"
				, colSorting : "str,str,str,str,int,int,int,int,int,int,int,int,int,int,int,int,int"
				, paging : false
				, onRowDblClicked : function(rowId, celInd) {
					//this.callEvent('onButtonClick', ['btnDtl']);
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
							mvno.cmn.download(ROOT + "/rsk/statMgmt/getChnlDataSttcListExcel.json?menuId=MSP5003011", true, {postData:searchData}); 
							
							break;
					}
				}
			}
	};
	
	var PAGE = {
		title : "${breadCrumb.title}",
		breadcrumb : " ${breadCrumb.breadCrumb}",
		buttonAuth:${buttonAuth.jsonString},
		onOpen : function() {
			mvno.ui.createForm("FORM1");
			mvno.ui.createGrid("GRID1");

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2027"}, PAGE.FORM1, "stdrTm"); // 기준시간
			
			var fixGrid = PAGE.GRID1.getColIndexById("keepCnt");
			// IE 인 경우만 SPLIT 실행
			if(mvno.cmn.isIE()) PAGE.GRID1.splitAt(fixGrid+1);
		}
		
	};
	
</script>