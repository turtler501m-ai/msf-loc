<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : 
 * @Description : 판매채권자산명세서
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
<div id="GRID2" class="section"></div>

<!-- Script -->
<script type="text/javascript">

var optYN = [{value:"", text:"- 전체 -", selected:true}, {value:"Y", text:"Y"}, {value:"N", text:"N"}];
var bSumSearchYN = true;

var DHX = {
      //------------------------------------------------------------
      FORM1 : {
         items : [
            {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
            , {type:"block", labelWidth:40 , list:[
               {type:"select", name:"bondSeqNo", label:"판매회차", width:100, offsetLeft:10, labelWidth:110}
               ,{type:"newcolumn"}
               ,{type:"select", name:"billYm", label:"기준월", width:100, offsetLeft:20, labelWidth:120}
               ,{type:"newcolumn"}
               ,{type:"select", name:"billArrAmtYn", label:"연체가산금여부", width:100, offsetLeft:20, labelWidth:110, options:optYN}
            ]}
            , {type:"block", labelWidth:40 , list:[
               {type:"select", name:"midPrfpayAmtYn", label:"조기상환(완납)여부", width:100, offsetLeft:10, labelWidth:110, options:optYN}
               ,{type:"newcolumn"}
               ,{type:"select", name:"midPayAmtYn", label:"조기상환(부분납)여부", width:100, offsetLeft:20, labelWidth:120, options:optYN}
               ,{type:"newcolumn"}
               ,{type:"select", name:"insrBillYn", label:"보험청구여부", width:100, offsetLeft:20, labelWidth:110, options:optYN}
            ]}
            , {type:"block", labelWidth:40 , list:[
               {type:"select", name:"adjsAmtYn", label:"감액여부", width:100, offsetLeft:10, labelWidth:110, options:optYN}
            ]}
            , {type : "button",name : "btnSearch",value : "조회", className:"btn-search3"}
         ]
         ,onValidateMore : function (data) {
            if(data.bondSeqNo == null || data.bondSeqNo == "") this.pushError("bondSeqNo", "판매회차", "판매회차를 선택하세요");
            if(data.billYm == null || data.billYm == "") this.pushError("billYm", "기준월", "기준월을 선택하세요");
         }
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
                  
                  bSumSearchYN = true;
                  
                  PAGE.GRID1.list(ROOT + "/bond/bondsalemgmt/soldAssetList.json", form, {timeout:180000, callback: function() {
                     if(bSumSearchYN){
                        mvno.cmn.ajax(ROOT + "/bond/bondsalemgmt/soldAssetListBySum.json", form, function(resultData) {
                           
                           if(resultData.result.data.rows.length > 0){
                              PAGE.FORM2.setItemValue("totalBondSaleAmt",resultData.result.data.rows[0].totalBondSaleAmt);
                              PAGE.FORM2.setItemValue("totalInstAmt",resultData.result.data.rows[0].totalInstAmt);
                              PAGE.FORM2.setItemValue("totalRmnInstAmt",resultData.result.data.rows[0].totalRmnInstAmt);
                           }else{
                              PAGE.FORM2.setItemValue("totalBondSaleAmt",0);
                              PAGE.FORM2.setItemValue("totalInstAmt",0);
                              PAGE.FORM2.setItemValue("totalRmnInstAmt",0);
                           }
                           
                           bSumSearchYN = false;
                        });
                     }
                     
                     PAGE.GRID2.clearAll();
                     
                  }});
                  break;
            }
         }
      }
      //-------------------------------------------------------------
      ,GRID1 : {
         css : {
            height : "230px"
         },
         title : "채권정보<span style='margin-left:15px;font-size:12px;color:#aaa;font-weight:normal;''>| 저장된 엑셀은 엑셀다운로드 메뉴에서 다운받으실 수 있습니다</span>",
         header : "보증보험관리번호,계약번호,고객명,고객구분,생년월일,사업자번호,개통대리점명,고객주소,전화번호,할부시작일자,할부종료일자,상환일,결제방식,(최초)채권원금실행액,(최초)채권이자실행액,(최초)기타실행액,(최초)총할부금액,(매각시)채권원금실행액,(매각시)채권이자실행액,(매각시)기타실행액,(매각시)총할부금액,(기준월)할부잔액,월할부납입액,할부개월,잔여할부개월수,채권판매시퀀스번호",
         columnIds : "grntInsrMngmNo,contractNum,subLinkName,customerType,userSsn,taxId,orgnNm,subAdrPrimaryLn,subscriberNo,payStrtDt,payEndDt,redeemDt,blBillingMethod,ttlInstAmnt,instIntAmt,instEtcAmt,instTotalAmt,bondSaleAmt,bondSaleIntAmt,bondSaleEtcAmt,bondSaleTotalAmt,yetPymnAmnt,monBill,ttlInstMnthCnt,rmnInstCnt,bondSeqNo",
         widths : "120,120,120,120,120,120,100,100,100,100,100,100,100,140,140,100,100,140,140,120,120,100,100,100,100,100",
         colAlign : "Center,Center,Center,Center,Center,Center,Left,Left,Center,Center,Center,Center,Center,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Center,Center,Right",
         colTypes : "ro,ro,ro,ro,ro,roXr,ro,ro,roXp,roXdom,roXdom,ro,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron",
         colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
         hiddenColumns : [25],
         paging : true,
         pageSize:20,
         showPagingAreaOnInit: true,
         buttons : {
            hright : {
               btnExcel : { label : "엑셀저장" }
            }
         }
         ,onRowSelect:function(rId, cId){
            var grid = this;
            
            var sdata = grid.getSelectedRowData();
            var data = {
                  grntInsrMngmNo : sdata.grntInsrMngmNo,
                  bondSeqNo : sdata.bondSeqNo,
                  billYm : PAGE.FORM1.getItemValue("billYm")
            }
            
            PAGE.GRID2.list(ROOT + "/bond/bondsalemgmt/soldAssetDtlInfo.json", data);
         }
         ,onButtonClick : function(btnId, selectedData) {
            var grid = this;
            switch (btnId) {
               case "btnExcel":
                  if(this.getRowsNum()== 0) {
                     mvno.ui.alert("데이터가 존재하지 않습니다.");
                     return;
                  }else{
                      mvno.cmn.downloadCallback(function(result) {
                        var url = "/cmn/btchmgmt/saveBatchRequest2.json";
                        
                        var sData = {
                              'bondSeqNo'        : PAGE.FORM1.getItemValue("bondSeqNo")
                              , 'billYm'         : PAGE.FORM1.getItemValue("billYm")
                              , 'billArrAmtYn'   : PAGE.FORM1.getItemValue("billArrAmtYn")
                              , 'midPrfpayAmtYn' : PAGE.FORM1.getItemValue("midPrfpayAmtYn")
                              , 'midPayAmtYn'    : PAGE.FORM1.getItemValue("midPayAmtYn")
                              , 'insrBillYn'     : PAGE.FORM1.getItemValue("insrBillYn")
                              , 'adjsAmtYn'      : PAGE.FORM1.getItemValue("adjsAmtYn")
                              , 'menuId'         : "BON2001003"
                              , 'menuNm'         : "판매채권자산명세서"
                              , 'dwnldRsn'       : result
                        };
                        
                        var sSearchVal = {
                              '판매회차'               : PAGE.FORM1.getItemValue("bondSeqNo")
                              , '기준월'               : PAGE.FORM1.getItemValue("billYm")
                              , '연체가산금여부'       : PAGE.FORM1.getItemValue("billArrAmtYn")
                              , '조기상환(완납)여부'   : PAGE.FORM1.getItemValue("midPrfpayAmtYn")
                              , '조기상환(부분납)여부' : PAGE.FORM1.getItemValue("midPayAmtYn")
                              , '보험금청구여부'       : PAGE.FORM1.getItemValue("insrBillYn")
                              , '감액여부'             : PAGE.FORM1.getItemValue("adjsAmtYn")
                        };
                        
                        var params = {
                              "batchJobId" : "BATCH00059"
                              , "dutyNm" : "BND"
                              , "menuNm" : "판매채권자산명세서"
                              , "menuId" : "BON2001003"
                              , "execParam" : JSON.stringify(sData)
                              , "searchVal" : JSON.stringify(sSearchVal)
                        };
                        
                        
                        mvno.cmn.ajax(url, params, function(result) {
                           mvno.ui.notify("NCMN0001");
                           //PAGE.GRID1.refresh();
                           mvno.ui.alert("검색조건을 이용하여<br>엑셀자료를 생성합니다.");
                        });
                        
                        
                     });
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
               {type:"input", name:"totalInstAmt", label:"채권원금실행액합계", width:150, offsetLeft:10, labelWidth:110, numberFormat:"0,000,000,000", validate:"ValidInteger", readonly: true}
               ,{type:"newcolumn"}
               ,{type:"input", name:"totalBondSaleAmt", label:"채원원금잔액합계", width:150, offsetLeft:30, labelWidth:100, numberFormat:"0,000,000,000", validate:"ValidInteger", readonly: true}
               ,{type:"newcolumn"}
               ,{type:"input", name:"totalRmnInstAmt", label:"기분할부잔액합계", width:150, offsetLeft:30, labelWidth:100, numberFormat:"0,000,000,000", validate:"ValidInteger", readonly: true}
            ]}
         ]
      }
      //-------------------------------------------------------------
      ,GRID2 : {
         css : {
            height : "200px"
         },
         title : "채권상세정보",
         header : "보증보험관리번호,청구월,할부회차,잔여채권금액,청구금액,수납금액,미수납금액,미납금액,미납회차,조정금액,조기상환(완납),조기상환(부분납),연체가산금청구,연체가산금수납,마스터여부",
         columnIds : "grntInsrMngmNo,billYm,bpymnCnt,yetPymnAmnt,billAmnt,pymnAmnt,unpayAmnt,unpayArrsAmnt,unpayArrsTmscnt,adjsAmnt,fullPrfpayAmnt,midPrfpayAmnt,unpayArrsFeeAmnt,arrsFeeAmnt,mstYn",
         widths : "100,100,100,100,100,100,100,100,100,100,100,100,100,100,100",
         colAlign : "Center,Center,Center,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Center",
         colTypes : "ro,roXdom,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron",
         colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
         hiddenColumns : [14],
         paging : false,
      }
};
      
var PAGE = {
   title : "${breadCrumb.title}",
   breadcrumb : "${breadCrumb.breadCrumb}", 
   
   buttonAuth: ${buttonAuth.jsonString},
   
   onOpen : function() {
      mvno.ui.createForm("FORM1");
      mvno.ui.createForm("FORM2");
      mvno.ui.createGrid("GRID1");
      mvno.ui.createGrid("GRID2");
      
      mvno.cmn.ajax4lov(ROOT+"/bnd/bondmgmt/getSaleNumCombo.json", {"pSearchGbn":"A"}, PAGE.FORM1, "bondSeqNo");
   }
};

</script>