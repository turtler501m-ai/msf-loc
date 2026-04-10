<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : 
 * @Description : Scheduled CF
 * @
 * @ 수정일     수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 
 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>
<div id="FORM2" class="section-box"></div>

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
                  PAGE.GRID1.list(ROOT + "/bond/bondsalemgmt/selectScheduledCfList.json", form, {timeout:180000, callback: function(resultData) {
                     PAGE.FORM2.setItemValue("expBondAmtSum",resultData.result.expBondAmtSum);
                  }});
                  break;
            }
         }
      }
      //-------------------------------------------------------------
      ,GRID1 : {
         css : {
            height : "560px"
         },
         title : "Scheduled CF",
         header : "납입월,예상납입금액",
         columnIds : "saleYm,expBondAmt",
         widths : "300,630",
         colAlign : "Center,Right",
         colTypes : "roXdom,ron",
         colSorting : "str,str",
         paging : false,
         pageSize:10,
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
                     mvno.cmn.download(ROOT + "/bond/bondsalemgmt/selectScheduledCfListByExcel.json?menuId=BON2001007", true, {postData:searchData}); 
                  }
                  
                  break;
            }
         }
      }
      //------------------------------------------------------------
      , FORM2 : {
         items : [
            {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},
            {type:"block", labelWidth:40 , list:[
               {type:"input", name:"expBondAmtSum", label:"예상납입금액합계", width:150, offsetLeft:10, labelWidth:110, numberFormat:"0,000,000,000", validate:"ValidInteger", readonly: true}
            ]}
         ]
      }
};
      
var PAGE = {
   title : "${breadCrumb.title}",
   breadcrumb : "${breadCrumb.breadCrumb}", 
   
   buttonAuth: ${buttonAuth.jsonString},
   
   onOpen : function() {
      mvno.ui.createForm("FORM1");
      mvno.ui.createGrid("GRID1");
      mvno.ui.createForm("FORM2");
      
      mvno.cmn.ajax4lov(ROOT+"/bnd/bondmgmt/getSaleNumCombo.json", {"pSearchGbn":"A"}, PAGE.FORM1, "bondSeqNo");
   }
};

</script>