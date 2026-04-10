<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>

<!-- Script -->
<script type="text/javascript">
  var DHX = {
    FORM1 : {
      items : [
         {type: "settings", position: "label-left", labelAlign: "left", labelWidth: 60, blockOffset: 0}
        ,{type: "block", list: [
            {type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "srchStrtDt", label: "신청일자", value: "${srchStrtDt}", calendarPosition: "bottom", readonly: true, width: 100, offsetLeft: 10}
           ,{type: "newcolumn"}
           ,{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "srchEndDt", label: "~", value: "${srchEndDt}", calendarPosition: "bottom", readonly: true, labelAlign: "center", labelWidth: 10, offsetLeft: 5, width:100}
           ,{type: "newcolumn"}
           ,{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "compStrtDt", label: "완료일자", calendarPosition: "bottom", readonly: true, disabled:true, width: 100, offsetLeft: 75}
           ,{type: "newcolumn"}
           ,{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "compEndDt", label: "~", calendarPosition: "bottom", readonly: true, disabled:true, labelAlign: "center", labelWidth: 10, offsetLeft: 5, width: 100}
           ,{type: "newcolumn"}
           ,{type: "checkbox", name: "compUseYn", label: "완료일자 사용", position: "label-right", labelAlign: "left", labelWidth: 100, offsetLeft: 20, width: 5, checked: false}
         ]}
        ,{type: "block", list: [
            {type: "input", label: "대리점", name: "cntpntShopId", width: 100, offsetLeft: 10}
           ,{type: "newcolumn"}
           ,{type: "button", value: "찾기", name: "btnOrgFind"}
           ,{type: "newcolumn"}
           ,{type: "input", name: "cntpntShopNm", width: 100, readonly: true}
           ,{type: "newcolumn"}
           ,{type: "select", label: "서류유형", name: "docType", width: 110, offsetLeft: 40}
         ]}
        ,{type:"block", list: [
            {type: "select", label: "검색구분", name: "searchGbn", width: 100, offsetLeft: 10}
           ,{type: "newcolumn"}
           ,{type: "input", name: "searchName", disabled: true, width: 150}
           ,{type: "newcolumn"}
           ,{type: "select", label: "수신방법", name: "receiveType", width: 110, offsetLeft: 40}
           ,{type: "newcolumn"}
           ,{type: "select", label: "처리상태", name: "status", width: 100, offsetLeft: 40}
         ]}
        ,{type: "hidden", name: "btnCount1", value: "0"}       // 조회 버튼 다중클릭 방지
        ,{type: "hidden", name: "btnExcelCount1", value: "0"}  // 다운로드 버튼 다중클릭 방지
        ,{type: "button", name: "btnSearch", value: "조회", className: "btn-search3"}
      ]
      ,onValidateMore: function(data){
         var form = this;

         if(data.srchStrtDt > data.srchEndDt){
           this.pushError("srchEndDt", "신청일자", "시작일자가 종료일자보다 클 수 없습니다.");
         }

         if(data.compStrtDt > data.compEndDt){
           this.pushError("compEndDt", "완료일자", "시작일자가 종료일자보다 클 수 없습니다.");
         }

				 if(data.searchGbn != "" && data.searchName.trim() == ""){
           this.pushError("searchName", "검색어", "검색어를 입력하세요.");
         }

         form.setItemValue("btnCount1", 0);      // 초기화
         form.setItemValue("btnExcelCount1", 0); // 초기화
      }
      ,onButtonClick: function(btnId){
         var form = this;

         switch(btnId){

           case "btnOrgFind" : // 대리점 찾기

             mvno.ui.codefinder("ORG", function(result) {
               form.setItemValue("cntpntShopId", result.orgnId);
               form.setItemValue("cntpntShopNm", result.orgnNm);
             });
             break;

           case "btnSearch" : // 조회

		         var btnCount2 = parseInt(form.getItemValue("btnCount1", "")) + 1;
             form.setItemValue("btnCount1", btnCount2);
             if(form.getItemValue("btnCount1", "") != "1") return;

             PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getAcenReceptionList.json", form, {
               callback: function () {form.resetValidateCss();}
             });
             break;
         }
      }
      ,onChange: function(name, value){
         var form = this;

         var endDt = new Date().format("yyyymmdd");
         var time = new Date().getTime() - 1000 * 3600 * 24 * 7; // 7일전
         var strtDt = new Date(time);

         switch(name){

           case "compUseYn" : // 완료일자 사용여부

		         if(form.isItemChecked("compUseYn")){
               form.enableItem("compStrtDt");
               form.enableItem("compEndDt");
               form.setItemValue("compStrtDt", strtDt);
               form.setItemValue("compEndDt", endDt);
             }else{
               form.disableItem("compStrtDt");
               form.disableItem("compEndDt");
               form.setItemValue("compStrtDt", "");
               form.setItemValue("compEndDt", "");
             }
				     break;

           case "searchGbn" : // 검색구분

             form.setItemValue("searchName", "");

             if(mvno.cmn.isEmpty(value) || value == "03"){
							 // 검색구분 [전체] 또는 [요금제]
               form.enableItem("srchStrtDt");
               form.enableItem("srchEndDt");
               form.setItemValue("srchStrtDt", strtDt);
               form.setItemValue("srchEndDt", endDt);

               if(value == "03") form.enableItem("searchName");
               else form.disableItem("searchName");

             }else{
               form.disableItem("srchStrtDt");
               form.disableItem("srchEndDt");
               form.setItemValue("srchStrtDt", "");
               form.setItemValue("srchEndDt", "");
               form.enableItem("searchName");
             }
             break;
         }
      }
    } // end of FORM1 ------------------------------------
   ,GRID1 : {
       css: {height : "500px"}
      ,title: "접수목록"
      ,header: "선택,접수일시,계약번호,개통상태,청구계정번호,고객명,서류유형,수신방법,FAX번호,시작년월,종료년월,처리상태,처리일시,처리자,신청키" // 15
      ,columnIds: "rowCheck,regstDttm,contractNum,subStatusNm,ban,cstmrName,docTypeName,receiveTypeName,faxNo,startYm,endYm,statusName,procDttm,procName,seq" // 15
      ,widths: "50,140,100,100,120,120,120,100,120,90,90,90,140,120,0" // 15
      ,colAlign: "center,center,center,center,center,left,center,center,center,center,center,center,center,left,center"  // 15
      ,colTypes: "ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" // 15
      ,colSorting: "str,str,str,str,str,str,str,str,str,str,str,str,str,str" // 15
      ,hiddenColumns: [14]
      ,paging: true
      ,pageSize: 20
      ,buttons: {
         hright: {
           btnExcel: {label: "엑셀다운로드"}
         }
        ,right: {
           btnEmail: {label: "e-Mail 확인"}
          ,btnComplete: {label: "처리완료"}
          ,btnCancel: {label: "완료취소"}
        }
      }
      ,checkSelectedButtons: ["btnEmail"]
      ,onRowDblClicked: function(rowId, celInd){
        this.callEvent('onButtonClick', ['btnEmail']);
      }
      ,onButtonClick: function(btnId){
         var grid = this;

         switch (btnId) {

		       case "btnExcel" : // 엑셀다운로드

             if(grid.getRowsNum() == 0) {
               mvno.ui.alert("데이터가 존재하지 않습니다.");
               return;
             }

             var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
             PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
             if(PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return;

             var searchData = PAGE.FORM1.getFormData(true);
             mvno.cmn.download(ROOT + "/stats/statsMgmt/getAcenReceptionListExcel.json?menuId=MSP1010055", true, {postData: searchData});

             PAGE.FORM1.setItemValue("btnCount1", 0);      // 초기화
             PAGE.FORM1.setItemValue("btnExcelCount1", 0); // 초기화
             break;

           case "btnEmail" : // e-Mail 확인

             var sData = grid.getSelectedRowData();
             if(sData == null){
               mvno.ui.alert("선택된 데이터가 없습니다.");
               return;
             }

             if(sData.receiveType != "E"){
               mvno.ui.alert("수신방법이 e-Mail이 아닙니다.");
               return;
             }

             if(sData.subStatus == "C"){
                 mvno.ui.alert("해지고객은 e-Mail 조회가 불가합니다.");
                 return;
             }

             mvno.cmn.ajax(ROOT + "/stats/statsMgmt/getEmail.json", {"contractNum" : sData.contractNum}, function(resultData){
               var email = resultData.result.email || "연동결과 없음";
               mvno.ui.alert("[e-Mail 주소] " + email);
             });
             break;

           case "btnComplete" : // 처리완료

             var rowIds = grid.getCheckedRows(0);
             if (!rowIds) {
               mvno.ui.alert("ECMN0003");  // 선택한(체크된) 데이터가 없습니다.
               return;
             }

             mvno.ui.confirm("[ " + grid.getCheckedRowData().length + " ]건 완료 처리하시겠습니까?", function(){
               var sData = grid.classifyRowsFromIds(rowIds, "seq,status");
               var compData = sData.ALL.find(item => item.status == "01");

               if(compData){
                 mvno.ui.alert("처리상태가 완료인 건이 존재합니다.");
                 return;
               }

               mvno.cmn.ajax4json(ROOT + "/stats/statsMgmt/completeAcenReception.json", sData, function(){
                 mvno.ui.alert("변경사항이 저장되었습니다.");
                 grid.refresh();
               });
             });
             break;

           case "btnCancel" : // 완료취소

             var rowIds = grid.getCheckedRows(0);
             if (!rowIds) {
               mvno.ui.alert("ECMN0003");  // 선택한(체크된) 데이터가 없습니다.
               return;
             }

             mvno.ui.confirm("[ " + grid.getCheckedRowData().length + " ]건 완료 취소 처리하시겠습니까?", function(){
               var sData = grid.classifyRowsFromIds(rowIds, "seq,status");
               var notCompData = sData.ALL.find(item => item.status == "00");

               if(notCompData){
                 mvno.ui.alert("처리상태가 접수인 건이 존재합니다.");
                 return;
               }

               mvno.cmn.ajax4json(ROOT + "/stats/statsMgmt/cancelAcenReception.json", sData, function(){
                 mvno.ui.alert("변경사항이 저장되었습니다.");
                 grid.refresh();
               });
             });
             break;
         }
      }
    } // end of GRID1 ------------------------------------
  }; // end of DHX -------------------------------------------------------------

  var PAGE = {
     title : "${breadCrumb.title}"
    ,breadcrumb : "${breadCrumb.breadCrumb}"
    ,buttonAuth: ${buttonAuth.jsonString}
    ,onOpen : function(){

      mvno.ui.createForm("FORM1");
      mvno.ui.createGrid("GRID1");

      mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ACEN0001", orderBy:"etc1"}, PAGE.FORM1, "docType");     // 서류유형
      mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ACEN0002", orderBy:"etc1"}, PAGE.FORM1, "receiveType");	// 수신방법
      mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ACEN0003"}, PAGE.FORM1, "status");			                // 처리상태
      mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ACEN0004"}, PAGE.FORM1, "searchGbn");		                // 검색구분
    }
  }

</script>