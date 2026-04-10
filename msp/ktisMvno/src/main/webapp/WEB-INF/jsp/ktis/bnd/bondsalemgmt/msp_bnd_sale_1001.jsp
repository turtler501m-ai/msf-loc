<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : 
 * @Description : 채권판매대상조회
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
<div id="FORM3" class="section-box"></div>

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
               {type:"calendar", label:"매입일자", name:"trgtStrtDt", width:110, labelWidth:90, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d"}
               ,{type:"newcolumn"}
               ,{type:"calendar", label:"~", name:"trgtEndDt", width:110, offsetLeft:5, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d"}
            ]}
            , {type:"block", labelWidth:40 , list:[
               {type:"input", name:"trgtRmnAmt", label:"할부잔액(이상)", width:100, labelWidth:90, validate:"ValidInteger", numberFormat:"0,000,000,000", userdata:{align:"right"}}
               ,{type:"newcolumn"}
               ,{type:"input", name:"trgtRmnCnt", label:"할부잔여개월(이상)", width:100, offsetLeft:20, labelWidth:110, validate:"ValidInteger", numberFormat:"0,000,000,000", userdata:{align:"right"}}
               ,{type:"newcolumn"}
               ,{type:"input", name:"trgtPymCnt", label:"수납개월(이상)", width:100, offsetLeft:20, labelWidth:90, validate:"ValidInteger", numberFormat:"0,000,000,000", userdata:{align:"right"}}
               ,{type:"newcolumn"}
               ,{type:"select", name:"trgtUnpaidYn", label:"미납여부", width:100, offsetLeft:20, labelWidth:50, options:optYN}
            ]}
            , {type : "button",name : "btnSearch",value : "조회", className:"btn-search2"}
         ]
         ,onValidateMore : function (data) {
            if( (data.trgtStrtDt == null || data.trgtStrtDt == "") || (data.trgtEndDt == null || data.trgtEndDt == "") ) {
               this.pushError("trgtStrtDt", "매입일자", "매입일자를 선택하세요");
            }
         }
         ,onButtonClick : function(btnId) {
            var form = this;
            switch (btnId) {
               case "btnSearch" :
                  
                  bSumSearchYN = true;
                  
                  PAGE.GRID1.list(ROOT + "/bond/bondsalemgmt/getBondSaleObjectLstInfo.json", form, {timeout:180000, callback: function() {
                     if(bSumSearchYN){
                        mvno.cmn.ajax(ROOT + "/bond/bondsalemgmt/getBondSaleObjectHap.json", form, function(resultData) {
                           
                           if(resultData.result.data.rows.length > 0){
                              PAGE.FORM2.setItemValue("totalCnt",resultData.result.data.rows[0].totalCnt);
                              PAGE.FORM2.setItemValue("totalYetPymnAmnt",resultData.result.data.rows[0].totalYetPymnAmnt);
                              PAGE.FORM2.setItemValue("bpymnPer",resultData.result.data.rows[0].bpymnPer);
                              PAGE.FORM2.setItemValue("unpayPer",resultData.result.data.rows[0].unpayPer);
                              PAGE.FORM2.setItemValue("rtnPer",resultData.result.data.rows[0].rtnPer);
                           }else{
                              PAGE.FORM2.setItemValue("totalCnt",0);
                              PAGE.FORM2.setItemValue("totalYetPymnAmnt",0);
                              PAGE.FORM2.setItemValue("bpymnPer",0);
                              PAGE.FORM2.setItemValue("unpayPer",0);
                              PAGE.FORM2.setItemValue("rtnPer",0);
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
            height : "480px"
         },
         title : "판매대상상세내역<span style='margin-left:15px;font-size:12px;color:#aaa;font-weight:normal;''>| 저장된 엑셀은 엑셀다운로드 메뉴에서 다운받으실 수 있습니다</span>",
         header : "보증보험번호,계약번호,고객명,할부개월,할부원금,월납입액,잔여할부개월수,잔여채권,납입금액,미납여부,할부시작일자,할부종료일자,보증보험상태,보험개시일자,보험종료일자",
         columnIds : "grntInsrMngmNo,contractNum,subLinkName,ttlInstMnthCnt,ttlInstAmnt,mntInstAmt,rmnInstCnt,yetPymnAmnt,payAmt,unpaidYn,payStrtDt,payEndDt,grntInsrStatChngRsnCd,instStrtDate,insrTrmnDate",
         widths : "120,100,80,100,100,100,100,100,100,100,100,100,100,100,100",
         colAlign : "Center,Center,Center,Center,Right,Right,Center,Right,Right,Center,Center,Center,Center,Center,Center",
         colTypes : "ro,ro,ro,ro,ron,ron,ro,ron,ron,ro,roXdom,roXdom,ro,roXdom,roXdom",
         colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
         paging : true,
         pageSize:20,
         showPagingAreaOnInit: true,
         buttons : {
            hright : {
               btnExcel : { label : "엑셀저장" }
            }
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
                              'trgtStrtDt'     : new Date(PAGE.FORM1.getItemValue("trgtStrtDt")).format("yyyymmdd")
                              , 'trgtEndDt'    : new Date(PAGE.FORM1.getItemValue("trgtEndDt")).format("yyyymmdd")
                              , 'trgtRmnAmt'   : PAGE.FORM1.getItemValue("trgtRmnAmt")
                              , 'trgtRmnCnt'   : PAGE.FORM1.getItemValue("trgtRmnCnt")
                              , 'trgtPymCnt'   : PAGE.FORM1.getItemValue("trgtPymCnt")
                              , 'trgtUnpaidYn' : PAGE.FORM1.getItemValue("trgtUnpaidYn")
                              , 'menuId'       : "BON2001001"
                              , 'menuNm'       : "채권판매대상조회"
                              , 'dwnldRsn'     : result
                        };
                        
                        var sSearchVal = {
                              '매입일자'             : new Date(PAGE.FORM1.getItemValue("trgtStrtDt")).format("yyyy-mm-dd") + "~" 
                                                       + new Date(PAGE.FORM1.getItemValue("trgtEndDt")).format("yyyy-mm-dd")
                              , '할부잔액(이상)'     : PAGE.FORM1.getItemValue("trgtRmnAmt")
                              , '할부잔여개월(이상)' : PAGE.FORM1.getItemValue("trgtRmnCnt")
                              , '수납개월(이상)'     : PAGE.FORM1.getItemValue("trgtPymCnt")
                              , '미납여부'           : PAGE.FORM1.getItemValue("trgtUnpaidYn")
                        };
                        
                        var params = {
                              "batchJobId" : "BATCH00063"
                              , "dutyNm" : "BND"
                              , "menuNm" : "채권판매대상조회"
                              , "menuId" : "BON2001001"
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
               {type:"input", name:"totalCnt", label:"건수", width:80, offsetLeft:10, labelWidth:30, numberFormat:"0,000,000,000", validate:"ValidInteger", readonly: true}
               ,{type:"newcolumn"}
               ,{type:"input", name:"totalYetPymnAmnt", label:"총금액", width:100, offsetLeft:20, labelWidth:40, numberFormat:"0,000,000,000", validate:"ValidInteger", readonly: true}
               ,{type:"newcolumn"}
               ,{type:"input", name:"bpymnPer", label:"수납율", width:100, offsetLeft:20, labelWidth:40, validate:"ValidNumeric", readonly: true}
               ,{type:"newcolumn"}
               ,{type:"input", name:"unpayPer", label:"미납율", width:100, offsetLeft:20, labelWidth:40, validate:"ValidNumeric", readonly: true}
               ,{type:"newcolumn"}
               ,{type:"input", name:"rtnPer", label:"미납회수율", width:100, offsetLeft:20, labelWidth:60, validate:"ValidNumeric", readonly: true}
            ]}
         ]
         ,buttons : {
            right : {
               //btnPop : {label : "채권판매생성" }
            }
         }
         ,onButtonClick : function(btnId) {
            var form = this;
            switch (btnId) {
               case "btnPop" :
                  
                  mvno.ui.createForm("FORM3");
                  
                  PAGE.FORM3.setItemValue("trgtStrtDt", PAGE.FORM1.getItemValue("trgtStrtDt"));
                  PAGE.FORM3.setItemValue("trgtEndDt", PAGE.FORM1.getItemValue("trgtEndDt"));
                  PAGE.FORM3.setItemValue("trgtRmnAmt", PAGE.FORM1.getItemValue("trgtRmnAmt"));
                  PAGE.FORM3.setItemValue("trgtRmnCnt", PAGE.FORM1.getItemValue("trgtRmnCnt"));
                  PAGE.FORM3.setItemValue("trgtPymCnt", PAGE.FORM1.getItemValue("trgtPymCnt"));
                  PAGE.FORM3.setItemValue("trgtUnpaidYn", PAGE.FORM1.getItemValue("trgtUnpaidYn"));
                  
                  PAGE.FORM3.clearChanged();
                  
                  mvno.ui.popupWindowById("FORM3", "채권판매생성", 550, 270, {
                     onClose: function(win) {
                        if (!PAGE.FORM3.isChanged()) {
                           return true;
                        }
                        mvno.ui.confirm("CCMN0005", function() {
                           win.closeForce();
                        });
                     }
                  });
                  
                  break;
            }
         }
      }
      //------------------------------------------------------------
      , FORM3 : {
         items : [
            {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},
            , {type:"block", labelWidth:40 , list:[
               {type:"calendar", label:"매입일자", name:"trgtStrtDt", width:110, labelWidth:90, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", disabled:true}
               ,{type:"newcolumn"}
               ,{type:"calendar", label:"~", name:"trgtEndDt", width:110, offsetLeft:5, dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", disabled:true}
            ]}
            , {type:"block", labelWidth:40 , list:[
               {type:"input", name:"trgtRmnAmt", label:"할부잔액(이상)", width:100, labelWidth:90, validate:"ValidInteger", numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
               ,{type:"newcolumn"}
               ,{type:"input", name:"trgtRmnCnt", label:"할부잔여개월(이상)", width:100, offsetLeft:55, labelWidth:110, validate:"ValidInteger", numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
            ]}
            , {type:"block", labelWidth:40 , list:[
               ,{type:"input", name:"trgtPymCnt", label:"수납개월(이상)", width:100, labelWidth:90, validate:"ValidInteger", numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
               ,{type:"newcolumn"}
               ,{type:"select", name:"trgtUnpaidYn", label:"미납여부", width:100, offsetLeft:55, labelWidth:110, options:optYN, disabled:true}
            ]}
            , {type:"block", labelWidth:40 , list:[
               {type:"input", name:"saleNo", label:"판매회차", width:100, labelWidth:90, validate:"ValidInteger", numberFormat:"0,000,000,000", userdata:{align:"right"}, maxLength: 6}
            ]}
            , {type:"block", labelWidth:40 , list:[
               {type:"input", name:"title", label:"제목", width:370, labelWidth:90, maxLength: 30}
            ]}
         ]
         ,buttons : {
            center : {
               btnReg : {label : "채권판매생성" }
               ,btnClose : {label : "닫기" }
            }
         }
         ,onButtonClick : function(btnId) {
            var form = this;
            switch (btnId) {
               case "btnReg" :
                  
                  if(form.getItemValue("saleNo") == null || form.getItemValue("saleNo") == "") {
                     this.pushError("saleNo", "", ""); 
                     mvno.ui.alert('[판매회차] 판매회차는 필수 입니다.');
                     return;
                  }
                  
                  if(form.getItemValue("title") == null || form.getItemValue("title") == "") {
                     this.pushError("title", "", ""); 
                     mvno.ui.alert('[제목] 제목은 필수 입니다.');
                     return;
                  }
                  
                  mvno.cmn.ajax2(ROOT + "/bond/bondsalemgmt/inserBondSaleInfo.json", form, function(resultData) {
                     mvno.ui.alert("생성되었습니다.");
                     mvno.ui.closeWindowById("FORM3");
                  });
                  
                  
                  break;
                  
               case "btnClose" :
                  
                  mvno.ui.closeWindowById("FORM3");
                  
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
      mvno.ui.createForm("FORM2");
      
      PAGE.FORM1.setItemValue("trgtStrtDt",'${trgtStrtDt}');
      PAGE.FORM1.setItemValue("trgtEndDt",'${trgtEndDt}');
   }
};

</script>