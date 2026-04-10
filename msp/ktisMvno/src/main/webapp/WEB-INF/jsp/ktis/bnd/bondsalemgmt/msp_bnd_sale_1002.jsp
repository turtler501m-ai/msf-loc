<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/**
 * @Class Name : 
 * @Description : 채권판매관리
 * @
 * @ 수정일     수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 
 */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>
<div id="GRID2" class="section"></div>
<div id="GRID3" class="section"></div>
<div id="GROUP1" style="display:none;">
   <div id="GRID4" class="section"></div>
</div>
<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">
   var optYN = [{value:"", text:"- 전체 -", selected:true}, {value:"Y", text:"Y"}, {value:"N", text:"N"}];
   var GUBUN = [{value:"", text:"- 전체 -", selected:true}, {value:"1", text:"보증보험관리번호"}, {value:"2", text:"계약번호"}, {value:"3", text:"전화번호"}, {value:"4", text:"고객명"}];
   
   var DHX = {
      //------------------------------------------------------------
      FORM1 : {
         items : [
            {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0}
            , {type:"block", labelWidth:40 , list:[
               {type:"select", name:"bondSeqNo", label:"판매회차", width:100, offsetLeft:10, labelWidth:50}
               ,{type:"newcolumn"}
               ,{type:"select", name:"insrPayYn", label:"보증보험금수납여부", width:80, offsetLeft:10, labelWidth:110, options:optYN}
               ,{type:"newcolumn"}
               ,{type:"select", name:"pSearchGbn", label:"검색구분", width:100, offsetLeft:10, labelWidth:50, options:GUBUN}
               ,{type:"newcolumn"}
               ,{type:"input", name:"pSearchName", label:"", width:100}
               ,{type:"newcolumn"}
               ,{type:"select", name:"rtnYn", label:"회수여부", width:80, offsetLeft:10, labelWidth:50, options:optYN}
               
            ]}
            , {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"}
         ]
         ,onButtonClick : function(btnId) {
            var form = this;
            switch (btnId) {
               case "btnSearch" :
                  
                  if(form.getItemValue("bondSeqNo") == null || form.getItemValue("bondSeqNo") == "") {
                     this.pushError("bondSeqNo", "", ""); 
                     mvno.ui.alert('[판매회차] 판매회차를 선택하세요.');
                     return;
                  }
                  
                  PAGE.GRID1.clearAll();
                  PAGE.GRID2.clearAll();
                  PAGE.GRID3.clearAll();
                  
                  PAGE.GRID1.list(ROOT + "/bond/bondsalemgmt/getBondSaleInfo.json", form, {callback: function(resultData) {
                     
                     var rData = resultData.result.data;
                     
                     if(rData.rows.length > 0){
                        if(rData.rows[0].saleStatCd == "S"){
                           mvno.ui.enableButton("GRID3", "btnSaleCan");
                           mvno.ui.enableButton("GRID3", "btnScfInq");
                           mvno.ui.enableButton("GRID3", "btnSaleProc");
                           
                           mvno.ui.disableButton("GRID3", "btnSaleBondBack");
                        } else if(rData.rows[0].saleStatCd == "A") {
                           mvno.ui.disableButton("GRID3", "btnSaleCan");
                           mvno.ui.disableButton("GRID3", "btnScfInq");
                           mvno.ui.disableButton("GRID3", "btnSaleProc");
                           
                           if(rData.rows[0].rtnYn == "Y"){
                              mvno.ui.enableButton("GRID3", "btnSaleBondBack");
                           } else {
                              mvno.ui.disableButton("GRID3", "btnSaleBondBack");
                           }
                        } else {
                           mvno.ui.disableButton("GRID3", "btnSaleCan");
                           mvno.ui.disableButton("GRID3", "btnScfInq");
                           mvno.ui.disableButton("GRID3", "btnSaleProc");
                           mvno.ui.disableButton("GRID3", "btnSaleBondBack");
                        }
                     }
                     
                     
                  }});
                  
                  PAGE.GRID2.list(ROOT + "/bond/bondsalemgmt/getBondSaleCntrList.json", form, {timeout:180000, callback: function() {
                     PAGE.GRID3.clearAll();
                  }});
                  
                  break;
            }
         }
      }
      //-------------------------------------------------------------
      ,GRID1 : {
         css : {
            height : "60px"
         },
         title : "채권판매내역<span style='margin-left:15px;font-size:12px;color:#aaa;font-weight:normal;''>| 저장된 엑셀은 엑셀다운로드 메뉴에서 다운받으실 수 있습니다</span>",
         header : "판매회차,제목,대상기간,판매상태,판매확정자,판매확정일자,건수,총금액,회수여부",
         columnIds : "saleNo,title,trgtPeriod,saleStatCd,saleCnfmId,saleCnfmDttm,totalCnt,totalRmnAmt,rtnYn",
         widths : "100,200,170,80,100,100,80,120,100",
         colAlign : "Center,Left,Center,Center,Center,Center,Right,Right,Center",
         colTypes : "ro,ro,ro,coro,ro,roXdom,ron,ron,ro",
         colSorting : "str,str,str,str,str,str,str,str,str",
         hiddenColumns : [8],
         paging : false,
      }
      //-------------------------------------------------------------
      ,GRID2 : {
         css : {
            height : "300px"
         },
         title : "채권판매상세내역",
         header : "보증보험관리번호,계약번호,고객명,전화번호,고객구분,생년월일,사업자번호,주소,개통대리점,할부원금,할부기간,월납입액,잔여할부개월수,채권판매금액,할부시작일자,할부종료일자,보증보험상태,회수여부,채권판매시퀀스번호",
         columnIds : "grntInsrMngmNo,contractNum,subLinkName,subscriberNo,customerType,userSsn,taxId,subAdrPrimaryLn,openAgntCd,ttlInstAmnt,ttlInstMnthCnt,mntInstAmt,rmnInstCnt,yetPymnAmnt,payStrtDt,payEndDt,grntInsrStatChngRsnCd,rtnYn,bondSeqNo",
         widths : "120,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100",
         colAlign : "Center,Center,Center,Center,Center,Center,Center,Left,Left,Right,Center,Right,Center,Right,Center,Center,Center,Center,Right",
         colTypes : "ro,ro,ro,roXp,ro,ro,roXr,ro,ro,ron,ro,ron,ro,ron,roXdom,roXdom,ro,ro,ro",
         colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
         hiddenColumns : [18],
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
                  bondSeqNo : sdata.bondSeqNo
            }
            
            PAGE.GRID3.list(ROOT + "/bond/bondsalemgmt/getBondSaleAssetDtlInfo.json", data);
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
                              'bondSeqNo'       : PAGE.FORM1.getItemValue("bondSeqNo")
                              , 'insrPayYn'     : PAGE.FORM1.getItemValue("insrPayYn")
                              , 'pSearchGbn'    : PAGE.FORM1.getItemValue("pSearchGbn")
                              , 'pSearchName'   : PAGE.FORM1.getItemValue("pSearchName")
                              , 'rtnYn'         : PAGE.FORM1.getItemValue("rtnYn")
                              , 'menuId'        : "BON2001002"
                              , 'menuNm'        : "채권판매관리"
                              , 'dwnldRsn'      : result
                        };
                        
                        var sSearchVal = {
                              '판매회차'               : PAGE.FORM1.getItemValue("bondSeqNo")
                              , '보증보험금수납여부'   : PAGE.FORM1.getItemValue("insrPayYn")
                              , '검색구분'             : PAGE.FORM1.getItemValue("pSearchGbn")
                              , '검색어'               : PAGE.FORM1.getItemValue("pSearchName")
                              , '회수여부'             : PAGE.FORM1.getItemValue("rtnYn")
                        };
                        
                        var params = {
                              "batchJobId" : "BATCH00060"
                              , "dutyNm" : "BND"
                              , "menuNm" : "채권판매관리"
                              , "menuId" : "BON2001002"
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
      //-------------------------------------------------------------
      ,GRID3 : {
         css : {
            height : "120px"
         },
         title : "채권상세정보",
         header : "보증보험관리번호,청구월,할부회차,잔여채권금액,청구금액,수납금액,미수납금액,미납금액,미납회차,조정금액,조기상환(완납),조기상환(부분납),연체가산금청구,연체가산금수납,마스터여부",
         columnIds : "grntInsrMngmNo,billYm,bpymnCnt,yetPymnAmnt,billAmnt,pymnAmnt,unpayAmnt,unpayArrsAmnt,unpayArrsTmscnt,adjsAmnt,fullPrfpayAmnt,midPrfpayAmnt,unpayArrsFeeAmnt,arrsFeeAmnt,mstYn",
         widths : "100,100,100,100,100,100,100,100,100,100,100,100,100,100,100",
         colAlign : "Center,Center,Center,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Right,Center",
         colTypes : "ro,roXdom,ro,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ron,ro",
         colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",
         //hiddenColumns : [9,14],
         paging : false,
         buttons : {
            left : {
               btnSaleCan : {label : "판매제외" }
               , btnScfInq : {label : "SCF조회" }
            },
            right : {
               btnSaleProc : {label : "판매처리" }
               , btnSaleBondBack : {label : "회수처리" }
            }
         }
         ,onButtonClick : function(btnId) {
            var form = this;
            switch (btnId) {
               case "btnSaleCan" :
                  
                  var data = PAGE.GRID2.getSelectedRowData();
                  
                  if(data == null) {
                     mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
                     return;
                  }
                  
                  var rData = {
                        bondSeqNo : PAGE.FORM1.getItemValue("bondSeqNo")
                        , grntInsrMngmNo : data.grntInsrMngmNo
                  };
                  
                  mvno.cmn.ajax2(ROOT + "/bond/bondsalemgmt/soldBondCan.json", rData, function(resultData) {
                     mvno.ui.alert("판매제외 되었습니다.");
                     PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
                  });
                  
                  break;
               
               case "btnScfInq" :
                  
                  mvno.ui.createGrid("GRID4");
                  
                  PAGE.GRID4.list(ROOT + "/bond/bondsalemgmt/selectScheduledCfList.json", PAGE.FORM1);
                  
                  mvno.ui.popupWindowById("GROUP1", "Scheduled CF", 330, 500, {
                     onClose: function(win) {
                        win.closeForce();
                     }
                  });
                  
                  break;
                  
               case "btnSaleProc" :
                  
                  mvno.cmn.ajax2(ROOT + "/bond/bondsalemgmt/saleBondCfm.json", {bondSeqNo : PAGE.FORM1.getItemValue("bondSeqNo")}, function(resultData) {
                     mvno.ui.alert("판매처리 되었습니다.");
                     PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
                  });
                  
                  break;
                  
               case "btnSaleBondBack" :
                  
                  var data = PAGE.GRID2.getSelectedRowData();
                  
                  if(data == null) {
                     mvno.ui.alert("선택된 데이터가 존재하지 않습니다");
                     return;
                  }
                  
                  if(data.rtnYn == "Y"){
                     mvno.ui.alert("이미 회수되었습니다.");
                     return;
                  }
                  
                  var getYetPymnAmnt = 0;
                  
                  PAGE.GRID3.forEachRow(function(id){
                     
                     var rData = PAGE.GRID3.getRowData(id);
                     
                     if(rData.mstYn == "Y"){
                        getYetPymnAmnt = rData.yetPymnAmnt;
                     }
                  });
                  
                  mvno.ui.createForm("FORM2");
                  
                  PAGE.FORM2.setItemValue("bondSeqNo", PAGE.FORM2.getItemValue("bondSeqNo"));
                  PAGE.FORM2.setItemValue("grntInsrMngmNo", data.grntInsrMngmNo);
                  PAGE.FORM2.setItemValue("subLinkName", data.subLinkName);
                  PAGE.FORM2.setItemValue("contractNum", data.contractNum);
                  PAGE.FORM2.setItemValue("rtnAmt", getYetPymnAmnt);
                  
                  PAGE.FORM2.clearChanged();
                  
                  mvno.ui.popupWindowById("FORM2", "판매회수 처리", 600, 250, {
                     onClose: function(win) {
                        if (!PAGE.FORM2.isChanged()) {
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
      //-------------------------------------------------------------
      ,GRID4 : {
         css : {
            height : "350px"
         },
         title : "Scheduled CF",
         header : "납입월,예상납입금액",
         columnIds : "saleYm,expBondAmt",
         widths : "100,150",
         colAlign : "Center,Right",
         colTypes : "roXdom,ron",
         colSorting : "str,str",
         paging : false,
         buttons : {
            center : {
               btnClose : {label : "닫기" }
            }
         }
         ,onButtonClick : function(btnId) {
            var form = this;
            switch (btnId) {
               case "btnClose" :
                  
                  mvno.ui.closeWindowById("GRID4");
                  
                  break;
            }
         }
      }
      //------------------------------------------------------------
      , FORM2 : {
         items : [
            {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},
            , {type:"block", labelWidth:40 , list:[
               {type:"input", name:"contractNum", label:"계약번호", width:100, labelWidth:60, disabled:true}
            ]}
            , {type:"block", labelWidth:40 , list:[
               {type:"input", name:"subLinkName", label:"고객명", width:100, labelWidth:60, disabled:true}
            ]}
            , {type:"block", labelWidth:40 , list:[
               ,{type:"input", name:"rtnAmt", label:"회수금액", width:100, labelWidth:60, validate:"ValidInteger", numberFormat:"0,000,000,000", userdata:{align:"right"}, disabled:true}
            ]}
            , {type:"block", labelWidth:40 , list:[
               {type:"input", name:"rtnRsn", label:"회수사유", width:400, labelWidth:60}
            ]}
            , {type:"hidden", name:"bondSeqNo" }
            , {type:"hidden", name:"grntInsrMngmNo" }
         ]
         ,buttons : {
            center : {
               btnReg : {label : "판매회수" }
               ,btnClose : {label : "닫기" }
            }
         }
         ,onButtonClick : function(btnId) {
            var form = this;
            switch (btnId) {
               case "btnReg" :
                  
                  if(form.getItemValue("rtnRsn") == null || form.getItemValue("rtnRsn") == "") {
                     this.pushError("rtnRsn", "", ""); 
                     mvno.ui.alert('[회수사유] 회수사유는 필수 입니다.');
                     return;
                  }
                  
                  mvno.cmn.ajax2(ROOT + "/bond/bondsalemgmt/bckSoldBond.json", form, function(resultData) {
                     mvno.ui.alert("회수되었습니다.");
                     mvno.ui.closeWindowById("FORM2");
                     PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
                  });
                  
                  
                  break;
                  
               case "btnClose" :
                  
                  mvno.ui.closeWindowById("FORM2");
                  
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
      mvno.ui.createGrid("GRID2");
      mvno.ui.createGrid("GRID3");
      
      var combobox = PAGE.GRID1.getCombo(3);
      
      combobox.put("A", "확정");
      combobox.put("S", "대기");
      combobox.put("C", "취소");
      
      PAGE.GRID1.setEditable(false);
      
      mvno.ui.disableButton("GRID3", "btnSaleCan");
      mvno.ui.disableButton("GRID3", "btnScfInq");
      mvno.ui.disableButton("GRID3", "btnSaleProc");
      mvno.ui.disableButton("GRID3", "btnSaleBondBack");
      
      mvno.ui.hideButton("GRID3" , "btnSaleCan");
      mvno.ui.hideButton("GRID3" , "btnSaleProc");
      mvno.ui.hideButton("GRID3" , "btnSaleBondBack");
      
      mvno.cmn.ajax4lov(ROOT+"/bnd/bondmgmt/getSaleNumCombo.json", {"pSearchGbn":""}, PAGE.FORM1, "bondSeqNo");
   }
};

</script>