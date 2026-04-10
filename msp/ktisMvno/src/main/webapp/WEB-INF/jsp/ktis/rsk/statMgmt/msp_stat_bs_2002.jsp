<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  /**
  * @Class Name : msp_stat_bs_2002.jsp
  * @Description : 사용량조회(대리점)
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2016.11.17            최초 생성
  *
  */
%>
<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
	var dt = new Date();
	var fstDt = new Date(dt.getFullYear(), dt.getMonth(), 1);
	var prvMnth = new Date(fstDt.setDate(fstDt.getDate() - 1)).format("yyyymm");
	
	var isCntpShop = false;
	var orgnId = '';
	var orgnNm = '';
	
	var typeCd = '${orgnInfo.typeCd}';
	
	if (typeCd == '20' || typeCd == '30' ){
		isCntpShop = true;
		
		if(typeCd == '20'){
			orgnId = '${orgnInfo.orgnId}';
			orgnNm = '${orgnInfo.orgnNm}';
		}else{
			orgnId = '${orgnInfo.hghrOrgnCd}';
			orgnNm = '${orgnInfo.hghrOrgnNm}';
		}
	}
	
	var DHX = {
			//------------------------------------------------------------
			FORM1 : {
				items : [
					{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
					, {type:"block", list:[
						{type:"calendar", label:"사용월", name:"usgYm", value:prvMnth, width:80, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:10, required:true}
						, {type:"newcolumn"}
						, {type:"calendar", label:"개통월", name:"openYm", value:prvMnth, width:80, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:50, required:true}
					]},
					, {type:"block", list:[
						{type:"input", label:"대리점", name:"orgnId", width:100, readonly: isCntpShop, value:orgnId, labelWidth: 60, offsetLeft:10, required:true}
						, {type:"newcolumn"}
						, {type:"button", value:"찾기", name:"btnOrgFind", disabled:isCntpShop}
						, {type:"newcolumn"}
						, {type:"input", label:"", name:"orgnNm", width:120, readonly:isCntpShop, value:orgnNm}
						, {type:"newcolumn"}
// 						, {type:"select", label:"검색구분", name:"searchType", userdata:{lov:"RSK0004"}, offsetLeft:20}
						, {type:"select", label:"검색구분", name:"searchType", offsetLeft:20}
						, {type:"newcolumn"}
						, {type:"input", label:"", name:"searchVal"}
					]},
					//조회 버튼
					, {type:"newcolumn"}
					, {type:'hidden', name: "DWNLD_RSN", value:""}, //엑셀다운로드 사유
					, {type:"button", name:"btnSearch", value:"조회", className:"btn-search1"}
				]
				,onChange : function(name, value){
					var form = this;
					
					switch(name){
						case "orgnId" :
							if(value == null || value == "") {
								form.setItemValue("orgnId", "");
								form.setItemValue("orgnNm", "");
							}
							break;
						case "searchType" :
							form.setItemValue("searchVal", "");
							break;
					}
				}
				, onValidateMore : function(data){
					if(data.searchType != "" && data.searchVal == "") this.pushError("data.searchVal", "검색어", "검색어를 입력하세요");
				}
				, onButtonClick : function(btnId){
					var form = this;
					switch (btnId) {
						case "btnSearch" :
							if (! this.validate())  return;
							var url = ROOT + "/rsk/statMgmt/getUsgAmntStatOrgList.json";
							PAGE.GRID1.list(url, form);
							break;
						case "btnOrgFind":
							mvno.ui.codefinder("ORG", function(result) {
								form.setItemValue("orgnId", result.orgnId);
								form.setItemValue("orgnNm", result.orgnNm);
							});
							break;
					}
				}
			},
			//-------------------------------------------------------------
			GRID1 : {
				css : {
					height : "570px"
				}
				, title : "조회결과"
				, header : "계약번호,고객명,CTN,계약상태,개통일자,해지일자,대리점,통화(초),#cspan,#cspan,문자(건),#cspan,#cspan,데이터(MB),#cspan,#cspan,로밍,#cspan,#cspan"
				, header2 : "#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,#rspan,전체,무료,초과,전체,무료,초과,전체,무료,초과,전체,무료,초과"
				, columnIds : "contractNum,custNm,subscriberNo,subStatusNm,openDt,tmntDt,openAgntNm,callUsgAmnt,callFreeAmnt,callOverAmnt,charUsgCnt,charFreeCnt,charOverCnt,dataUsgAmnt,dataFreeAmnt,dataOverAmnt,roamUsgAmnt,roamFreeAmnt,roamOverAmnt"
				, widths : "100,120,120,80,100,100,170,80,80,80,80,80,80,80,80,80,80,80,80,80"
				, colAlign : "center,left,center,center,center,center,left,right,right,right,right,right,right,right,right,right,right,right,right"
				, colTypes : "ro,ro,roXp,ro,roXd,roXd,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron"
				, colSorting : "str,str,str,str,str,str,str,int,int,int,int,int,int,int,int,int,int,int,int"
				, paging : true
				, pageSize : 50
				, buttons : {
					hright : {
						btnDnExcel : {label : "자료생성"}
					}
				}
				, onButtonClick : function(btnId, selectedData) {
					switch (btnId) {
						case "btnDnExcel":
							if(PAGE.GRID1.getRowsNum() == 0){
								mvno.ui.alert("데이터가 존재하지 않습니다");
								return;
							}
							
							var searchData =  PAGE.FORM1.getFormData(true);

							var orgnId = searchData.orgnId;
							var usgYm = searchData.usgYm;
							if((orgnId != null && orgnId != '') || (usgYm != null && usgYm != '')){
                                mvno.cmn.downloadCallback(function(result) {
                                    PAGE.FORM1.setItemValue("DWNLD_RSN",result);
                                    mvno.cmn.ajax(ROOT + "/rsk/statMgmt/getUsgAmntStatOrgListExcel.json?menuId=MSP5002003", PAGE.FORM1.getFormData(true), function(result){
                                        mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
                                    });
                                });
							}else{
								mvno.ui.alert("검색조건이 맞지 않습니다.");
								return;
							}
							
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

			mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1002"}, PAGE.FORM1, "searchType"); // 검색구분
		}
	};
	
</script>