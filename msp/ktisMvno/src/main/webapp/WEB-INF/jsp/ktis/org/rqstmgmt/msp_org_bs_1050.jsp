<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
   /**
    * @Class Name : msp_org_bs_1050.jsp
    * @Description : 미납자료조회화면
    * @
    * @ 수정일     수정자 수정내용
    * @ ---------- ------ -----------------------------
    * @ 2015.08.31 장익준 최초생성
    * @
    * @author : 장익준
    * @Create Date : 2015. 8. 31.
    */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">

var cntpntShopId = '';
var cntpntShopNm = '';

var DHX = {
		//------------------------------------------------------------
		FORM1 : {
			items : [
				{type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0}
 				,{type:"block", list:[
					{type : 'calendar', dateFormat: '%Y-%m', serverDateFormat: '%Y%m', name: 'billYm', enabelDay:false, label: '청구월', value: "${currentMonth}", calendarPosition: 'bottom', readonly:true, width:100, offsetLeft:10, required:true}
					,{type : "newcolumn"}
// 					,{type : "select", label: "검색구분", name : "searchType", userdata: { lov: 'MSP0002'}, width:100, offsetLeft:200}
					,{type : "select", label: "검색구분", name : "searchType", width:100, offsetLeft:200}
					,{type : "newcolumn"}
					,{type : "input", name : "searchVal", width:120, maxLength:20 }
				]}
				,{type:"block", list:[
					{type : "input", label:"대리점", name: "cntpntShopId", width:100, offsetLeft:10}  
 					,{type : "newcolumn"},{type:"button", value:"찾기", name:"btnOrgFind"}
					,{type : "newcolumn"},{type:"input", name:"cntpntShopNm", width:120}
					,{type : "newcolumn"}
// 					,{type : "select", label: "계약상태", name : "subStatus", userdata: { lov: 'RCP0020'}, width:100, offsetLeft:26}
					,{type : "select", label: "계약상태", name : "subStatus", width:100, offsetLeft:26}
					,{type : "newcolumn"}
// 					,{type : "select", label: "명변여부", name : "mcnYn", userdata: { lov: 'ORG0017'}, width:100, offsetLeft:25}
					,{type : "select", label: "명변여부", name : "mcnYn", width:100, offsetLeft:25}
					/* ,{type : "newcolumn", offset:20}
					,{type : "select", label: "청구항목", name : "billItemCd", width:80 } */
				]}
				,{type : "button",name : "btnSearch",value : "조회", className:"btn-search2"}
			]
			,onButtonClick : function(btnId) {
				var form = this;
				
				switch (btnId) {
					case "btnSearch" :
						if(form.getItemValue("searchType") != "" && form.getItemValue("searchVal") == ""){
							mvno.ui.alert("검색어를 입력하세요");
							return;
						}
						
						PAGE.GRID1.list(ROOT + "/org/unpdmgmt/getUnpdMgmtList.json", form);
						break;
						
					case "btnOrgFind":
						mvno.ui.codefinder("ORG", function(result) {
							form.setItemValue("cntpntShopId", result.orgnId);
							form.setItemValue("cntpntShopNm", result.orgnNm);
						});
						break;
				}
			}
			,onChange:function(name, value){
				var form = this;
				
				switch(name){
					case "searchType" :
						form.setItemValue("searchVal", "");
						break;
				}
			}
		},
		//-------------------------------------------------------------
		GRID1 : {
			 css : {
				height : "560px"
			 },
			 title : "조회결과",
			 header : "청구월,작성일자,계약번호,서비스계약번호,고객명,전화번호,계약상태,개통일자,해지일자,청구항목,미납금액,개통대리점,명변여부",
			 columnIds : "billYm,workDt,contractNum,svcCntrNum,custNm,svcTelNum,subStatusNm,openDt,tmntDt,billItemNm,unpayAmnt,openAgntNm,mcnYn",
			 widths : "80,100,100,100,100,120,80,100,100,150,100,120,80",
			 colAlign : "center,center,center,center,left,center,center,center,center,left,right,left,center",
			 colTypes : "roXdm,roXd,ro,ro,ro,roXp,ro,roXd,roXd,ro,ron,ro,ro",
			 colSorting : "str,str,str,str,str,str,str,str,str,str,int,str,str",
			 paging : true,
			 pageSize : 100,
			 buttons : {
				hright : {
					btnDnExcel : { label : "엑셀다운로드" }
				}
			},
			onButtonClick : function(btnId, selectedData) {
				var form = this;
				switch (btnId) {
					case "btnDnExcel":
						if(PAGE.GRID1.getRowsNum() == 0) {
							mvno.ui.alert("데이터가 존재하지 않습니다.");
							return;
						} else {
							var searchData =  PAGE.FORM1.getFormData(true);
							if(PAGE.GRID1.getRowsNum() > 10000){
								if(PAGE.GRID1.getRowsNum() > 30000){
									mvno.ui.alert("30,000건 이상의 데이터는 받을 수 없습니다.<br/>ITSM 으로 데이터 요청 바랍니다.");
								}else{
									mvno.ui.confirm("조회 건수가 많은 경우<br/>시간이 오래 걸릴 수 있습니다.<br/>다운로드 받으시겠습니까?", function(){
										mvno.cmn.download(ROOT + "/org/unpdmgmt/getUnpdMgmtListExcel.json?menuId=MSP1001009", true, {postData:searchData});
									});
								}
							}else{
								mvno.cmn.download(ROOT + "/org/unpdmgmt/getUnpdMgmtListExcel.json?menuId=MSP1001009", true, {postData:searchData});
							}
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

			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1003"}, PAGE.FORM1, "searchType"); // 검색구분
			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0037"}, PAGE.FORM1, "subStatus"); // 계약상태
			 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005",orderBy:"etc1"}, PAGE.FORM1, "mcnYn"); // 명변여부
		}
	};
</script>