<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : 
 * @Description : STATIC DATA
 * @
 * @ 수정일     수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 
 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">

var DHX = {
      //------------------------------------------------------------
      FORM1 : {
         items : [
            {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
            , {type:"block", labelWidth:40 , list:[
               {type:"select", name:"bondSeqNo", label:"판매회차", width:100, labelWidth:60}
            ]}
            , {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"}
         ]
         ,onValidateMore : function (data) {
            if(data.bondSeqNo == null || data.bondSeqNo == "") this.pushError("bondSeqNo", "판매회차", "판매회차를 선택하세요");
         }
         ,onButtonClick : function(btnId) {
            var form = this;
            switch (btnId) {
               case "btnSearch" :
                  PAGE.GRID1.list(ROOT + "/bond/bondsalemgmt/getSoldBondStaticInfoList.json", form);
                  break;
            }
         }
      }
      //-------------------------------------------------------------
      ,GRID1 : {
         css : {
            height : "630px"
         },
         title : "회차별상세내역",
         header : "판매회차,청구월,월초채권잔액,월초잔존건수,정상청구,연체청구,정상입금,연체입금,조기상환(완납),조기상환(부분납),수수료/기타연체 가산금,보증보험 청구건수,보증보험 청구금액,보증보험 수납금액,보증보험차액,보증환급이자,하자담보(채권회수),하자담보(조정금액),월말채권잔액,월말건수,0개월,1개월,2개월,3개월,4개월,5개월,6개월,7개월,8개월,9개월,10개월,11개월,12개월이상",
         columnIds : "saleNo,billYm,mnthFstAmt,bondCnt,billAmnt,dlyBillAmt,pymnAmnt,unpayAmnt,nrFullPrfpayAmnt,nrMidPrfpayAmnt,arrsFeeAmnt,insrBillCnt,insrBillAmnt,insrPymnAmnt,insrMtgAmt,insrBckIntAmt,nrRtnAmnt,adjsAmnt,yetPymnAmnt,mnthLstCnt,yetPymnAmnt00,yetPymnAmnt01,yetPymnAmnt02,yetPymnAmnt03,yetPymnAmnt04,yetPymnAmnt05,yetPymnAmnt06,yetPymnAmnt07,yetPymnAmnt08,yetPymnAmnt09,yetPymnAmnt10,yetPymnAmnt11,yetPymnAmnt12",
         widths : "100,100,100,100,100,100,100,100,100,100,140,140,140,140,100,100,140,140,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100",
         colAlign : "Center,Center,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right",
         colTypes : "ro,roXdom,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron",
         colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
         paging : false,
         pageSize:20,
         buttons : {
            hright : {
               btnDnExcel : { label : "엑셀다운로드" }
            }
         }
         ,onButtonClick : function(btnId, selectedData) {
            var grid = this;
            switch (btnId) {
               case "btnDnExcel" :
                  if(PAGE.GRID1.getRowsNum() == 0){
                     mvno.ui.alert("데이터가 존재하지 않습니다");
                     return;
                  }else{
                     var searchData =  PAGE.FORM1.getFormData(true);
                     mvno.cmn.download(ROOT + "/bond/bondsalemgmt/getSoldBondStaticInfoListByExcel.json?menuId=BON2001004", true, {postData:searchData}); 
                  }
                  
                  break;
            }
         }
      }
};
      
var PAGE = {
   title : "${breadCrumb.title}",
   breadcrumb : "${breadCrumb.breadCrumb}", 
   
   buttonAuth: ${buttonAuth.jsonString},
   
   onOpen : function() {
      mvno.ui.createForm("FORM1");
      mvno.ui.createGrid("GRID1");
      
      mvno.cmn.ajax4lov(ROOT+"/bnd/bondmgmt/getSaleNumCombo.json", {"pSearchGbn":"A"}, PAGE.FORM1, "bondSeqNo");
   }
};

</script>