<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : 
 * @Description : 수납내역서
 * @
 * @ 수정일     수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 
 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

var DHX = {
      //------------------------------------------------------------
      FORM1 : {
         items : [
            {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
            , {type:"block", labelWidth:40 , list:[
               {type:"select", name:"bondSeqNo", label:"판매회차", width:100, offsetLeft:10, labelWidth:50}
               ,{type:"newcolumn"}
               ,{type:"select", name:"billYm", label:"기준월", width:100, offsetLeft:20, labelWidth:40}
            ]}
            , {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"}
         ]
         ,onChange : function(name, value){
            var form = this;
            if(name == "bondSeqNo"){
               mvno.cmn.ajax4lov(ROOT+"/bnd/bondsalemgmt/getBillYm.json", form, PAGE.FORM1, "billYm");
            }
         }
         ,onButtonClick : function(btnId) {
            var form = this;
            switch (btnId) {
               case "btnSearch" :
                  
                  if(form.getItemValue("bondSeqNo") == null || form.getItemValue("bondSeqNo") == "") {
                     this.pushError("bondSeqNo", "", ""); 
                     mvno.ui.alert('[판매회차] 판매회차를 선택하세요');
                     return;
                  }
                  
                  if(form.getItemValue("billYm") == null || form.getItemValue("billYm") == "") {
                     this.pushError("billYm", "", ""); 
                     mvno.ui.alert('[기준월] 기준월을 선택하세요');
                     return;
                  }
                  
                  var formData = {bondSeqNo : form.getItemValue("bondSeqNo")
                     , billYm : form.getItemValue("billYm")};
                  
                  mvno.cmn.ajax2(ROOT + "/bond/bondsalemgmt/selectSoldReceipt.json", formData, function(resultData) {
                     
                     var rData = resultData.result.data;
                     
                     if(rData.rows.length > 0){
                        PAGE.FORM2.setFormData(rData.rows[0]);
                        
                        PAGE.FORM2.setItemValue("totalAdjsAmnt", rData.rows[0].adjsAmnt);
                        
                        PAGE.FORM2.setItemValue("insrBillYm", rData.rows[0].billYm);
                        PAGE.FORM2.setItemValue("insrPymnAmnt2", rData.rows[0].insrPymnAmnt);
                        
                        PAGE.FORM2.setItemValue("adjsBillYm", rData.rows[0].billYm);
                        PAGE.FORM2.setItemValue("adjsBillAmt", rData.rows[0].adjsAmnt);
                        
                        PAGE.FORM2.setItemValue("mtgBillYm", rData.rows[0].billYm);
                        PAGE.FORM2.setItemValue("mtgMtgBondAmt", rData.rows[0].nrRtnAmnt);
                        PAGE.FORM2.setItemValue("mtgBillAmt", rData.rows[0].nrRtnAmnt);
                        PAGE.FORM2.setItemValue("mtgPayAmt", rData.rows[0].runAmnt);
                        
                        PAGE.FORM2.setItemValue("mnthFstCnt", rData.rows[0].bondCnt);
                        PAGE.FORM2.setItemValue("insrDesCnt", rData.rows[0].insrPymnCnt);
                        PAGE.FORM2.setItemValue("insrDesAmt", rData.rows[0].insrPymnAmnt);
                        PAGE.FORM2.setItemValue("mtgDesCnt", rData.rows[0].nrRtnCnt);
                        PAGE.FORM2.setItemValue("mtgDesAmt", rData.rows[0].nrRtnAmnt);
                        PAGE.FORM2.setItemValue("mnthLstAmt", rData.rows[0].yetPymnAmnt);
                        
                        if(rData.rows[0].mtgCfmYn == "N"){
                           PAGE.FORM2.enableItem("runDt");
                           PAGE.FORM2.enableItem("mtgPayAmt");
                           PAGE.FORM2.enableItem("btnApply");
                           PAGE.FORM2.enableItem("btnConfirm");
                           
                           //mvno.ui.disableButton("FORM2", "btnPrnt");
                        }else{
                           PAGE.FORM2.disableItem("runDt");
                           PAGE.FORM2.disableItem("mtgPayAmt");
                           PAGE.FORM2.disableItem("btnApply");
                           PAGE.FORM2.disableItem("btnConfirm");
                           
                           //mvno.ui.enableButton("FORM2", "btnPrnt");
                        }
                        
                     }
                  });
                  break;
            }
         }
      },
      
      FORM2 : {
         items : [
            {type:"block",name:"BODY", list:[
               {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
               ,{type: "fieldset", label: "1.수납내역", inputWidth:930, list:[
                  ,{type:"block", list:[
                      {type:"calendar", label:"회계일자", name:"runDt", width:110, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:100, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"청구상환", name:"pymnAmnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"조기상환", name:"midPrfpayAmnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"연체상환", name:"unpayAmnt", width:110, offsetLeft:10, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                  ]}
                  ,{type:"block", list:[
                       {type:"input", label:"연체수수료", name:"unpayArrsFeeAmnt", width:110, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                      ,{type:"newcolumn"}
                      ,{type:"input", label:"보험수납금", name:"insrPymnAmnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                      ,{type:"newcolumn"}
                      ,{type:"input", label:"감액(합계)", name:"totalAdjsAmnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                      ,{type:"newcolumn"}
                      ,{type:"input", label:"하자담보(합계)", name:"mtgTotalAmt", width:110, offsetLeft:10, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                  ]}
                  ,{type:"block", list:[
                      {type:"input", label:"원금상환 소계", name:"subSumAmt", width:110, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"수납합계", name:"totalSumAmt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                  ]}
               ]}
               ,{type: "fieldset", label: "2.보험청구내역", inputWidth:930, list:[
                  ,{type:"block", list:[
                      {type:"input", label:"청구월", name:"insrBillYm", width:110, labelWidth:100, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"청구건수", name:"insrBillCnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"청구금액", name:"insrBillAmnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"보험수납금(원금)", name:"insrPymnAmnt2", width:110, offsetLeft:10, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                  ]}
                  ,{type:"block", list:[
                       {type:"input", label:"보험수납금(차액)", name:"insrMtgAmnt", width:110, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                  ]}
               ]}
               ,{type: "fieldset", label: "3-1.감액(합계)", inputWidth:930, list:[
                  ,{type:"block", list:[
                      {type:"input", label:"청구월", name:"adjsBillYm", width:110, labelWidth:100, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"건수", name:"adjCnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"할인금액", name:"adjsAmnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"청구금액", name:"adjsBillAmt", width:110, offsetLeft:10, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                  ]}
               ]}
               ,{type: "fieldset", label: "4.하자담보(판매회수)", inputWidth:930, list:[
                  ,{type:"block", list:[
                      {type:"input", label:"회수월", name:"mtgBillYm", width:110, labelWidth:100, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"건수", name:"nrRtnCnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"할인금액", name:"mtgMtgBondAmt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"청구금액", name:"mtgBillAmt", width:110, offsetLeft:10, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                  ]}
                  ,{type:"block", list:[
                      {type:"input", label:"이행금액", name:"mtgPayAmt", width:110, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"button", value:"화면적용", name: "btnApply", offsetLeft:0, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"button", value:"금액확정", name: "btnConfirm", offsetLeft:0, disabled:true}
                  ]}
               ]}
               ,{type: "fieldset", label: "5.채권감소내역", inputWidth:930, list:[
                  ,{type:"block", list:[
                      {type:"input", label:"전기간채권건수", name:"mnthFstCnt", width:110, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"전기간금액", name:"mnthFstAmt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"정상감소건수", name:"nrPymnCnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                     ,{type:"newcolumn"}
                     ,{type:"input", label:"정상감소금액", name:"nrPymnAmnt", width:110, offsetLeft:10, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                  ]}
                  ,{type:"block", list:[
                     {type:"input", label:"조기상환(완납)감소건수", name:"nrFullPrfpayCnt", width:110, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                    ,{type:"newcolumn"}
                    ,{type:"input", label:"조기상환(완납)감소금액", name:"nrFullPrfpayAmnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                    ,{type:"newcolumn"}
                    ,{type:"input", label:"보험청구감소건수", name:"insrDesCnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                    ,{type:"newcolumn"}
                    ,{type:"input", label:"보험청구감소금액", name:"insrDesAmt", width:110, offsetLeft:10, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                  ]}
                  ,{type:"block", list:[
                     {type:"input", label:"조기상환(부분납)건수", name:"nrMidPrfpayCnt", width:110, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                    ,{type:"newcolumn"}
                    ,{type:"input", label:"조기상환(부분납)금액", name:"nrMidPrfpayAmnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                    ,{type:"newcolumn"}
                    ,{type:"input", label:"하자담보(판매회수)감소건수", name:"mtgDesCnt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                    ,{type:"newcolumn"}
                    ,{type:"input", label:"하자담보(판매회수)감소금액", name:"mtgDesAmt", width:110, offsetLeft:10, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                  ]}
                  ,{type:"block", list:[
                     {type:"input", label:"기간말채권건수", name:"mnthLstCnt", width:110, labelWidth:100, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                    ,{type:"newcolumn"}
                    ,{type:"input", label:"기간말금액", name:"mnthLstAmt", width:110, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
                  ]}
               ]}
            ]}
         ]
         ,buttons : {
            center : {
               btnPrnt : {label : "출력" }
            }
         }
         ,onButtonClick : function(btnId) {
            var form = this;
            switch (btnId) {
               case "btnApply" :
                  
                  var mtgPayAmt = Number(form.getItemValue("mtgPayAmt"));
                  
                  var subSumAmt = Number(form.getItemValue("pymnAmnt"))
                                 + Number(form.getItemValue("midPrfpayAmnt"))
                                 + Number(form.getItemValue("unpayAmnt"))
                                 + Number(form.getItemValue("mtgBillAmt"))
                                 + mtgPayAmt;
                  
                  var totalSumAmt = subSumAmt + Number(form.getItemValue("unpayArrsFeeAmnt"));
                  
                  form.setItemValue("mtgTotalAmt", mtgPayAmt);
                  form.setItemValue("subSumAmt", subSumAmt);
                  form.setItemValue("totalSumAmt", totalSumAmt);
                  
                  break;
               
               case "btnConfirm" :
                  
                  if(form.getItemValue("runDt") == null || form.getItemValue("runDt") == "") {
                     this.pushError("runDt", "", ""); 
                     mvno.ui.alert('[회계일자] 회계일자를 선택하세요');
                     return;
                  }
                  
                  var data = {bondSeqNo : PAGE.FORM1.getItemValue("bondSeqNo")
                        , billYm : PAGE.FORM1.getItemValue("billYm")
                        , mtgPayAmt : PAGE.FORM2.getItemValue("mtgPayAmt")
                        , runDt : new Date(PAGE.FORM2.getItemValue("runDt")).format("yyyymmdd")
                        };
                  
                  mvno.cmn.ajax(ROOT + "/bond/bondsalemgmt/confirmReceipt.json", data, function() {
                     
                     PAGE.FORM2.disableItem("runDt");
                     PAGE.FORM2.disableItem("mtgPayAmt");
                     PAGE.FORM2.disableItem("btnApply");
                     PAGE.FORM2.disableItem("btnConfirm");
                     
                     mvno.ui.alert("확정처리되었습니다.");
                     
                     PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
                     
                  });
                  
                  break;
                  
               case "btnPrnt" :
                  
                  if(PAGE.FORM1.getItemValue("bondSeqNo") == null || PAGE.FORM1.getItemValue("bondSeqNo") == "") {
                     PAGE.FORM1.pushError("bondSeqNo", "", ""); 
                     mvno.ui.alert('[판매회차] 판매회차를 선택하세요');
                     return;
                  }
                  
                  if(PAGE.FORM1.getItemValue("billYm") == null || PAGE.FORM1.getItemValue("billYm") == "") {
                     PAGE.FORM1.pushError("billYm", "", ""); 
                     mvno.ui.alert('[기준월] 기준월을 선택하세요');
                     return;
                  }
                  
                  var param = "?wgap=" + "10" + "&hgap=" + "10" + "&jrf=" + "/bond/soldReceiptReport.jrf" + "&arg=";
                  param = encodeURI(param);
                  
                  var arg = "BILL_YM#" + PAGE.FORM1.getItemValue("billYm",true) + "#BOND_SEQ_NO#" + PAGE.FORM1.getItemValue("bondSeqNo",true) + "#";
                  
                  arg = encodeURIComponent(arg);
                  
                  param = param + arg;
                  console.log("param=" + param);
                  var msg = "수납내역서를 출력하시겠습니까?";
                  mvno.ui.confirm(msg, function() {
                     mvno.ui.popupWindow("/cmn/report/report.do"+param, "수납내역서", 900, 700, {
                        onClose: function(win) {
                           win.closeForce();
                        }
                     });
                  });
                  
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
      mvno.ui.createForm("FORM2");
      
      mvno.cmn.ajax4lov(ROOT+"/bnd/bondmgmt/getSaleNumCombo.json", {"pSearchGbn":"A"}, PAGE.FORM1, "bondSeqNo");
      
      PAGE.FORM2.hideItem("btnApply");
      PAGE.FORM2.hideItem("btnConfirm");
      
      //mvno.ui.disableButton("FORM2", "btnPrnt");
      mvno.ui.enableButton("FORM2", "btnPrnt");
   }
};

</script>