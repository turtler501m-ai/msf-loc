<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section"></div>

<!-- Script -->
<script type="text/javascript">
   var curMnth = new Date().format("yyyymm");
   
   var DHX = {
         FORM1 : {
            items : [
               {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blackOffset:0}
               , {type:"block", list:[
                              {type:"calendar", label:"연동년월", name:"fromSearchYm", width:80, value:curMnth, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:10}
                              , {type:"newcolumn"}
                              , {type:"calendar", label:"~", name:"toSearchYm", width:80, value:curMnth, dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", offsetLeft:10, labelWidth:15}
                              , {type:"newcolumn"}
                              , {type:"input", label:"전화번호", name:"subscriberNo", width:100, offsetLeft:20}
                              , {type:"newcolumn"}
                              , {type:"select", label:"보험상태", name:"insrStatCd", width:100, offsetLeft:20}
               ]}
               , {type : "button",name : "btnSearch",value : "조회", className:"btn-search1"} 
            ],
            onButtonClick : function(btnId) {
               var form = this;
               switch (btnId) {
                  case "btnSearch" :
                     
                     PAGE.GRID1.list(ROOT + "/ptnr/ptnrCalc/getPtnrCalcMrAsstList.json", form);
                     
                     break;
               }
            },
            
            onValidateMore : function (data){
               if( (data.fromSearchYm == "" || data.fromSearchYm.trim() == "") && 
                   (data.toSearchYm == "" || data.toSearchYm.trim() == "") && 
                   (data.subscriberNo == "" || data.subscriberNo.trim() == "") ){
                  this.pushError("fromSearchYm", "연동년월, 전화번호", "검색할 값을 입력하세요");
               }
               
               if(data.subscriberNo == "" || data.subscriberNo.trim() == ""){
                  if( data.fromSearchYm == "" || data.fromSearchYm.trim() == "") {
                     this.pushError("fromSearchYm", "연동년월", "날짜를 입력하세요");
                  }
                  if( data.toSearchYm == "" || data.toSearchYm.trim() == "") {
                     this.pushError("toSearchYm", "연동년월", "날짜를 입력하세요");
                  }
                  
                  if(data.fromSearchYm > data.toSearchYm){
                     this.pushError("fromSearchYm", "연동년월", "날짜를 변경하세요");
                  }
               }
            }
         },
         
         GRID1 : {
            css : {
               height : "580px"
            },
            title : "조회결과",
            header : "연동년월,계약번호,고객명,선후불,데이터타입,전화번호,보험상태,보험가입일,보험해지일",
            columnIds : "ifYm,contractNum,subLinkName,serviceTypeNm,dataType,subscriberNo,insrStatNm,insrJoinDt,insrTmntDt,ktmAdFee",
            widths : "100,120,150,80,80,120,100,120,120",
            colAlign : "center,center,left,center,center,center,center,center,center",
            colTypes : "roXdm,ro,ro,ro,ro,roXp,ro,roXd,roXd",
            colSorting : "str,str,str,str,str,str,str,str,str",
            paging : true,
            pageSize:20,
            showPagingAreaOnInit: true,
            buttons : {
               hright : {
                  btnDnExcel : { label : "엑셀다운로드" }
               }
            },
            onButtonClick : function(btnId) {
               var form = this;
               switch (btnId) {
                  case "btnDnExcel":
                     
                     if(PAGE.GRID1.getRowsNum() == 0) {
                        mvno.ui.alert("데이터가 존재하지 않습니다.");
                        return;
                     }
                     
                     var searchData =  PAGE.FORM1.getFormData(true);
                     mvno.cmn.download(ROOT + "/ptnr/ptnrCalc/getPtnrCalcMrAsstListByExcel.json?menuId=MSP6000030", true, {postData:searchData});
                     break;
               }
            }
            
         },
         
         
   };
   
   var PAGE = {
		 title : "${breadCrumb.title}",
		 breadcrumb : "${breadCrumb.breadCrumb}", 
         buttonAuth: ${buttonAuth.jsonString},
         onOpen : function() {
            mvno.ui.createForm("FORM1");
            mvno.ui.createGrid("GRID1");
            
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0200",etc1:"miraeasset"}, PAGE.FORM1, "insrStatCd"); // 검색구분
         }
         
   };
   
</script>
