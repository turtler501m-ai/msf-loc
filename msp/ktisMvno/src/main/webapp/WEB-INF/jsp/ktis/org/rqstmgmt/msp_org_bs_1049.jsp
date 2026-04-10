<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
   /**
    * @Class Name : msp_org_bs_1049.jsp
    * @Description : 청구 상세(TAX&VAT용) 조회 화면
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
   var DHX = {
      //------------------------------------------------------------
      FORM1 : {
         items : [
            {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},
            {type:"block", labelWidth:10, list:[
               {type: 'calendar', dateFormat: '%Y-%m', serverDateFormat: '%Y%m', name: 'pblsDate', enabelDay:false, label: '작성월', value: "${currentMonth}", calendarPosition: 'bottom', readonly:true, width:80, offsetLeft:10}]},
            {type:"newcolumn", offset:20},
            {type:"block", labelWidth:90 , list:[
//                {type : "select",label : "서비스구분",name : "gubunCode", userdata: { lov: 'ORG0049'}, width:80 },
               {type : "select",label : "서비스구분",name : "gubunCode", width:80 },
               {type:"newcolumn", offset:20},
//                {type : "select",label : "계산서종류",name : "eseroType", userdata: { lov: 'ORG0050'}, width:80 },
               {type : "select",label : "계산서종류",name : "eseroType", width:80 },
               {type:"newcolumn", offset:20},
            ]},
            {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"}
         ],
         onButtonClick : function(btnId) {
            var form = this;
            switch (btnId) {
               case "btnSearch" :
                  PAGE.GRID1.list(ROOT + "/org/rqstmgmt/rqstMgmtDetailList.json", form);
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
         header : "작성일자,등록번호,청구계정,판매자코드,서비스구분,계산서종류,대표번호,공급가액,세액,사업자명,대표자명",
         columnIds : "pblsDate,bizrRgstNo,billAcntNo,soCd,gubunCode,eseroType,tlphNo,splyPricAmt,vatAmt,vndrNm,rprsPrsnNm",
         widths : "100,100,100,100,100,115,120,100,100,100,100",
         colAlign : "center,center,center,center,center,center,center,right,right,left,left",
         colTypes : "roXd,roXr,ro,ro,ro,ro,roXp,ron,ron,ro,ro",
         colSorting : "str,str,str,str,str,str,str,str,str,str,str",
         paging : true,
         pageSize : 20,
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
                     mvno.cmn.download('/org/rqstmgmt/rqstMgmtDetailListEx.json?pblsDate=' + PAGE.FORM1.getItemValue("pblsDate",true) + "&gubunCode=" + PAGE.FORM1.getItemValue("gubunCode",true) + "&eseroType=" + PAGE.FORM1.getItemValue("eseroType",true)+"&menuId=MSP1000011",true);
                     
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

		 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1004"}, PAGE.FORM1, "gubunCode"); // 서비스구분
		 mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG1005"}, PAGE.FORM1, "eseroType"); // 계산서종류
      }
   };
</script>