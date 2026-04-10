<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  /**
  * @Class Name : msp_stat_bs_4006.jsp
  * @Description : 부가서비스실적(현재)
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2017.03.22    강무성     최초 생성
  *
  */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
	var curDt = new Date().format("yyyymmdd");
	
	var adminId = "${info.sessionUserId}";
	
	var DHX = {
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					, {type:"block", list:[
						{type:"calendar", label:"기준일자", name:"stdrDt", value:curDt, width:100, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", offsetLeft:10, required:true}
						, {type:"newcolumn"}
// 						, {type:"select", label:"기준시간", name:"stdrTm", userdata:{lov:"RSK0005"}, offsetLeft:20, required:true}
						, {type:"select", label:"기준시간", name:"stdrTm", offsetLeft:20, required:true}
// 						, {type:"newcolumn"}
// 						, {type:"select", label:"채널", name:"chnlTp", userdata:{lov:"CMN0114"}, labelWidth:35, offsetLeft:20}
// 						, {type:"newcolumn"}
// 						, {type:"input", label:"대리점", name: "orgnId", value:"", labelWidth:40, width:100, offsetLeft:20, readonly:true}
// 						, {type:"newcolumn"}
// 						, {type:"button", value:"찾기", name:"btnOrgFind"}
// 						, {type:"newcolumn"}
// 						, {type:"input", name: "orgnNm",value:"", width:150}
					]}
					, {type:"newcolumn"}
					, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}
				]
				, onButtonClick : function(btnId) {
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							if (! this.validate())  return;
							
							if(adminId != "SJLEE74" && new Date(PAGE.FORM1.getItemValue("stdrDt")).format("yyyymmdd") < "20170801"){
								mvno.ui.alert("2017년 8월 1일 이전 자료는<br />조회할 수 없습니다.");
								return;
							}
							
							var url = ROOT + "/rsk/statMgmt/getAddProdSttcList.json";
							PAGE.GRID1.list(url, form);
							break;
// 						case "btnOrgFind":
// 							mvno.ui.codefinder("ORG", function(result) {
// 								form.setItemValue("orgnId", result.orgnId);
// 								form.setItemValue("orgnNm", result.orgnNm);
// 							});
// 							break;
					}
				}
// 				, onChange : function(name, value) {
// 					var form = this;
// 					switch(name){
// 						case "orgnId" :
// 							form.setItemValue("orgnId", "");
// 							form.setItemValue("orgnNm", "");
// 							break;
// 						case "orgnNm" :
// 							form.setItemValue("orgnId", "");
// 							form.setItemValue("orgnNm", "");
// 							break;
// 					}
// 				}
			}, //FORM1 End
			
			GRID1 : {
				css : {
					height : "600px"
				}
				, title : "조회결과"
				, header : "부가서비스코드,부가서비스명,기본료,유지건수,월,#cspan,#cspan,일,#cspan,#cspan"
				, header2 : "#rspan,#rspan,#rspan,#rspan,신규가입,해지,순증,신규가입,해지,순증"
				, columnIds : "rateCd,rateNm,baseAmt,keepCnt,mmOpenCnt,mmTmntCnt,mmNetCnt,openCnt,tmntCnt,netCnt"
				, widths : "110,180,90,90,80,80,80,80,80,80"
				, colAlign : "left,left,right,right,right,right,right,right,right,right"
				, colTypes : "ro,ro,ron,ron,ron,ron,ron,ron,ron,ron"
				, colSorting : "str,str,int,int,int,int,int,int,int,int"
				, paging : false
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
							mvno.cmn.download(ROOT + "/rsk/statMgmt/getAddProdSttcListExcel.json?menuId=MSP5003009", true, {postData:searchData}); 
							
							break;
					}
				}
			}	//GRID1 End
	}	//DHX End
	
	var PAGE = {
			title : "${breadCrumb.title}",
			breadcrumb : " ${breadCrumb.breadCrumb}",
			buttonAuth:${buttonAuth.jsonString},
			onOpen : function() {
				mvno.ui.createForm("FORM1");
				mvno.ui.createGrid("GRID1");

				mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2027"}, PAGE.FORM1, "stdrTm"); // 기준시간	
			}
		};
</script>