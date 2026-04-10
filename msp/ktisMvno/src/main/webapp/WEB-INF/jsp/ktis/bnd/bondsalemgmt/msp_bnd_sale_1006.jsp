<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : 
 * @Description : 회수채권관리
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
                  PAGE.GRID1.list(ROOT + "/bond/bondsalemgmt/getCanBondList.json", form, {callback: function() {
                     mvno.cmn.ajax(ROOT + "/bond/bondsalemgmt/getCanBondListBySum.json", form, function(resultData) {
                        if(resultData.result.data.rows.length > 0){
                           PAGE.FORM2.setItemValue("rtnAmtSum",resultData.result.data.rows[0].rtnAmtSum);
                        }else{
                           PAGE.FORM2.setItemValue("rtnAmtSum",0);
                        }
                     });
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
         title : "회수채권목록",
         header : "판매회차,회수월,계약번호,보증보험번호,전화번호,회수금액,회수처리자,회수이유",
         columnIds : "saleNo,rtnYm,contractNum,grntInsrMngmNo,subscriberNo,rtnAmt,rtnId,rtnRsn",
         widths : "100,100,100,150,120,110,100,155",
         colAlign : "Center,Center,Center,Center,Center,Right,Center,Left",
         colTypes : "ro,roXdom,ro,ro,roXp,ron,ro,ro",
         colSorting : "str,str,str,str,str,str,str,str",
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
                     mvno.cmn.download(ROOT + "/bond/bondsalemgmt/getCanBondListByExcel.json?menuId=BON2001006", true, {postData:searchData}); 
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
               {type:"input", name:"rtnAmtSum", label:"회수금액합계", width:150, offsetLeft:10, labelWidth:80, numberFormat:"0,000,000,000", validate:"ValidInteger", readonly: true}
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