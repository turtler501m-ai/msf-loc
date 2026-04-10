<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : msp_org_bs_1028.jsp
     * @Description : 사용자 등록 화면
     * @
     * @ 수정일        수정자 수정내용
     * @ ---------- ------ -----------------------------
     * @ 2014.08.14 장익준 최초생성
     * @ 2014.08.26 고은정 수정
     * @author : 장익준
     * @Create Date : 2014. 8. 14.
     */
%>

<!-- header -->
<div class="page-header">
    <h1>사용자 찾기 팝업</h1>
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
               {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
               
               {type: 'block', list: [
               {type: 'input', name: 'usrId', label: '사용자ID', value: '', width:120, maxLength:10, offsetLeft:10},
               {type: 'newcolumn', offset:50},
               {type: 'input', name: 'usrNm', label: '사용자명', value: '', width:120, maxLength:50}]},
               
               
               {type: 'block', list: [
//                {type: 'select', name: 'attcSctnCd', label: '소속구분', width:120, userdata:{lov: 'CMN0003'}, offsetLeft:10},
               {type: 'select', name: 'attcSctnCd', label: '소속구분', width:120, offsetLeft:10},
               {type: 'newcolumn', offset:50},
//                {type: 'select', name: 'dept', label: '부서/업체', width:120, userdata:{lov: 'CMN0006'}}]},
               {type: 'select', name: 'dept', label: '부서/업체', width:120}]},

               {type: 'newcolumn', offset:200},
               {type: 'button', value: '조회', name: 'btnSearch', className:"btn-search2" }
               
           ],
           onButtonClick : function(btnId) {

               var form = this;

               switch (btnId) {

                   case "btnSearch":

                       PAGE.GRID1.list(ROOT + "/org/userinfomgmt/userInfoMgmtList.json", form);
                       break;

               }

           }
       },

    // ----------------------------------------
       GRID1 : {
           css : {
               height : "280px"
           },
           title : "조회결과",
           header : "사용자ID,사용자명,부서,소속구분,사번,전화번호,핸드폰번호,이메일,usrId,usrNm,telNum,mblphnNum,email",
           columnIds : "usrIdMsk,usrNmMsk,deptNm,attcSctnCdNm,presBiz,telNumMsk,mblphnNumMsk,emailMsk,usrId,usrNm,telNum,mblphnNum,email",
           widths : "120,120,90,90,80,100,100,120,0,0,0,0,0",
           colAlign : "left,left,left,left,left,left,left,left,left,left,left,left,left",
           colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro",
           colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str",
	       hiddenColumns : "8,9,10,11,12",
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

		 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0003"}, PAGE.FORM1, "attcSctnCd"); // 소속구분
		 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0006",orderBy:"etc1"}, PAGE.FORM1, "dept"); // 부서/업체
		 
         if("${attcSctnCd}" == "10") {
        	 PAGE.FORM1.setItemValue("attcSctnCd", "10");
        	 PAGE.FORM1.disableItem("attcSctnCd");
         }

     }

 };
</script>