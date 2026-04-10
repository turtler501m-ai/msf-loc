<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	/**
	 * @Class Name : msp_org_bs_1019.jsp
	 * @Description : 제조사/공급사 관리
	 * @
	 * @ 수정일	    수정자 수정내용
	 * @ ---------- ------ -----------------------------
	 * @ 2014.08.14 장익준 최초생성
	 * @ 2014.08.27 고은정 수정
	 * @author : 장익준
	 * @Create Date : 2014. 8. 14.
	 */
%>

<!-- header -->
<div class="page-header">
	<h1>업체 찾기 팝업</h1>
</div>

   <!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>

<!-- Script -->
<script type="text/javascript">
var DHX = {
        //------------------------------------------------------------
        FORM1 : {
            items : [ 
		 	{type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
			{type: 'block', list: [            			 
			{type : "input",label : "업체ID",name : "mnfctId" ,width:100 ,offsetLeft:10 },
			{type: 'newcolumn'},
			{type : "input",label : "업체명",name : "mnfctNm" ,width:100 ,offsetLeft:30 },
			{type: 'newcolumn'},
			{type : "select",label : "업체유형",name : "mnfctYn",width:100 ,offsetLeft:30},
			]},
            {type : "newcolumn", offset:100}, 
            {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
           ],

           onButtonClick : function(btnId) {

               var form = this;

               switch (btnId) {

                   case "btnSearch":

                       PAGE.GRID1.list(ROOT + "/org/mnfctmgmt/mcfctMgmtList.json", form);
                       break;
               }
           }
       },

		// ----------------------------------------
       GRID1 : {
           css : {
               height : "200px"
           },
           title : "조회결과",
           header : "업체ID,업체명,업체유형,대표자명,전화번호,FAX번호,E-MAIL",
           columnIds : "mnfctId,mnfctNm,mnfctYn,rprsenNm,telnum,fax,email",               
           colAlign : "center,left,left,left,left,left,left",
           colTypes : "ro,ro,ro,ro,roXp,roXp,ro",
           colSorting : "str,str,str,str,str,str,str",
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

         /* 업체유형Combo */
         mvno.cmn.ajax4lov(ROOT+"/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0010",etc5:"UI",orderBy:"etc6"}, PAGE.FORM1, "mnfctYn");
     }
 };
</script>