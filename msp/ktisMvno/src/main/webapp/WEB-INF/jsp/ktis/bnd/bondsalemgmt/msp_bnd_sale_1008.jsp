<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : 
 * @Description : 공시용자산명세서
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

var bSumSearchYN = true;

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
                  bSumSearchYN = true;
                  
                  PAGE.GRID1.list(ROOT + "/bond/bondsalemgmt/soldAssetList2.json", form, {timeout:180000, callback: function() {
                     if(bSumSearchYN){
                        mvno.cmn.ajax(ROOT + "/bond/bondsalemgmt/soldAssetList2BySum.json", form, function(resultData) {
                           
                           if(resultData.result.data.rows.length > 0){
                              PAGE.FORM2.setItemValue("totalInstAmt",resultData.result.data.rows[0].totalInstAmt);
                              PAGE.FORM2.setItemValue("totalRemainAmt",resultData.result.data.rows[0].totalRemainAmt);
                           }else{
                              PAGE.FORM2.setItemValue("totalInstAmt",0);
                              PAGE.FORM2.setItemValue("totalRemainAmt",0);
                           }
                           
                           bSumSearchYN = false;
                        });
                     }
                  }});
                  break;
            }
         }
      }
      //-------------------------------------------------------------
      ,GRID1 : {
         css : {
            height : "520px"
         },
         title : "공시용 자산명세<span style='margin-left:15px;font-size:12px;color:#aaa;font-weight:normal;''>| 저장된 엑셀은 엑셀다운로드 메뉴에서 다운받으실 수 있습니다</span>",
         header : "일련번호,계약번호,보증보험관리번호,고객명,생년월일,할부시작일자,할부종료일자,할부개월,잔여개월,할부금액,채권잔액,주소",
         columnIds : "rowNum,contractNum,grntInsrMngmNo,subLinkName,userSsn,payStrtDt,payEndDt,ttlInstMnthCnt,rmnInstCnt,ttlInstAmnt,yetPymnAmnt,subAdrPrimaryLn",
         widths : "80,100,120,80,100,100,100,100,100,100,100,150",
         colAlign : "Center,Center,Center,Center,Center,Center,Center,Center,Center,Right,Right,Left",
         colTypes : "ro,ro,ro,ro,ro,roXdom,roXdom,ron,ron,ron,ron,ro",
         colSorting : "str,str,str,str,str,str,str,str,str,str,str,str",
         paging : true,
         pageSize:20,
         showPagingAreaOnInit: true,
         buttons : {
            hright : {
               btnDnExcel2 : { label : "공시용제출용" },
               btnDnExcel : { label : "엑셀저장" }
            }
         }
         ,onButtonClick : function(btnId, selectedData) {
            var grid = this;
            switch (btnId) {
               case "btnDnExcel" :
                  if(this.getRowsNum()== 0) {
                     mvno.ui.alert("데이터가 존재하지 않습니다.");
                     return;
                  }else{
                      mvno.cmn.downloadCallback(function(result) {
                        var url = "/cmn/btchmgmt/saveBatchRequest2.json";
                        
                        var sData = {
                              'bondSeqNo'        : PAGE.FORM1.getItemValue("bondSeqNo")
                              , 'menuId'         : "BON2001008"
                              , 'menuNm'         : "공시용자산명세서"
                              , 'dwnldRsn'       : result
                        };
                        
                        var sSearchVal = {
                              '판매회차'         : PAGE.FORM1.getItemValue("bondSeqNo")
                        };
                        
                        var params = {
                              "batchJobId" : "BATCH00064"
                              , "dutyNm" : "BND"
                              , "menuNm" : "공시용자산명세서"
                              , "menuId" : "BON2001008"
                              , "execParam" : JSON.stringify(sData)
                              , "searchVal" : JSON.stringify(sSearchVal)
                        };
                        
                        mvno.cmn.ajax(url, params, function(result) {
                           mvno.ui.notify("NCMN0001");
                           mvno.ui.alert("검색조건을 이용하여<br>엑셀자료를 생성합니다.");
                        });
                        
                     });
                  }
                  
                  break;
                  
               case "btnDnExcel2" :
                  if(this.getRowsNum()== 0) {
                     mvno.ui.alert("데이터가 존재하지 않습니다.");
                     return;
                  }else{
                      mvno.cmn.downloadCallback(function(result) {
                        var url = "/cmn/btchmgmt/saveBatchRequest2.json";
                        
                        var sData = {
                              'bondSeqNo'        : PAGE.FORM1.getItemValue("bondSeqNo")
                              , 'menuId'         : "BON2001008"
                              , 'menuNm'         : "공시용자산명세서"
                              , 'dwnldRsn'       : result
                        };
                        
                        var sSearchVal = {
                              '판매회차'         : PAGE.FORM1.getItemValue("bondSeqNo")
                        };
                        
                        var params = {
                              "batchJobId" : "BATCH00106"
                              , "dutyNm" : "BND"
                              , "menuNm" : "공시용자산명세서"
                              , "menuId" : "BON2001008"
                              , "execParam" : JSON.stringify(sData)
                              , "searchVal" : JSON.stringify(sSearchVal)
                        };
                        
                        mvno.cmn.ajax(url, params, function(result) {
                           mvno.ui.notify("NCMN0001");
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
                {type:"input", name:"totalInstAmt", label:"할부금액합계", width:150, offsetLeft:10, labelWidth:90, numberFormat:"0,000,000,000", validate:"ValidInteger", readonly: true}
               ,{type:"newcolumn"}
               ,{type:"input", name:"totalRemainAmt", label:"채권잔액합계", width:150, offsetLeft:30, labelWidth:90, numberFormat:"0,000,000,000", validate:"ValidInteger", readonly: true}
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