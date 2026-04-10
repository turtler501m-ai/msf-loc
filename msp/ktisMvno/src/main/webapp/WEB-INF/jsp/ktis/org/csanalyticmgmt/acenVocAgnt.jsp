<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>

<!-- Script -->
<script type="text/javascript">

  var maskingYn = "${maskingYn}"; // 마스킹권한

  var DHX = {
    FORM1 : {
      items : [
         {type: "settings", position: "label-left", labelAlign: "left", labelWidth: 50, blockOffset: 0}
        ,{type: "block", list: [
            {type: "input", label: "대리점", name: "cntpntShopId", width: 100, offsetLeft: 10}
           ,{type: "newcolumn"}
           ,{type: "button", value: "찾기", name: "btnOrgFind"}
           ,{type: "newcolumn"}
           ,{type: "input", name: "cntpntShopNm", width: 100, readonly: true}
           ,{type: "newcolumn"}
           ,{type: "select", label: "검색구분", name: "searchGbn", width: 100, offsetLeft: 30}
           ,{type: "newcolumn"}
           ,{type: "input", name: "searchName", disabled: true, width: 150}
         ]}
         ,{type: "hidden", name: "btnCount1", value: "0"}       // 조회 버튼 다중클릭 방지
         ,{type: "hidden", name: "btnExcelCount1", value: "0"}  // 다운로드 버튼 다중클릭 방지
         ,{type: "button", name: "btnSearch", value: "조회", className: "btn-search1"}
      ]
      ,onValidateMore: function(data){

         var form = this;
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

             PAGE.GRID1.list(ROOT + "/org/csAnalyticMgmt/getAcenVocAgntList.json", form, {
               callback: function(){
                 form.resetValidateCss();
                 PAGE.GRID1.forEachRow(function(id){
                   if(PAGE.GRID1.cellById(id, 5).getValue() == "N") {
                     PAGE.GRID1.setRowTextStyle(id, "color: red");
                   }
                 });
               }
             });
             break;
         }
		  }
      ,onChange: function(name, value){
         var form = this;

         switch(name){

           case "searchGbn" : // 검색구분

		         form.setItemValue("searchName", "");
             if(mvno.cmn.isEmpty(value)) form.disableItem("searchName");
             else form.enableItem("searchName");
				     break;
         }
		  }
    } // end of FORM1 ------------------------------------
   ,GRID1 : {
       css: {height : "560px"}
      ,title: "조회결과"
      ,header: "대리점ID,대리점명,담당자ID,담당자명,휴대폰번호,사용여부,등록일시,등록자,수정일시,수정자,순번"  // 11
      ,columnIds: "vocAgntCd,orgnName,vocAgntId,vocAgntName,mobileNum,useYn,regstDttm,regstName,rvisnDttm,rvisnName,seq" // 11
      ,widths: "100,150,100,120,100,60,140,120,140,120,0" // 11
      ,colAlign: "center,left,left,left,center,center,center,left,center,left,center"  // 11
      ,colTypes: "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" // 11
      ,colSorting: "str,str,str,str,str,str,str,str,str,str,str" // 11
      ,hiddenColumns: [10]
      ,paging: true
      ,pageSize: 20
      ,buttons: {
          hright: {
            btnExcel: {label: "엑셀다운로드"}
          }
         ,right: {
             btnAdd: {label: "담당자 등록"}
            ,btnMod: {label: "담당자 수정"}
          }
      }
      ,checkSelectedButtons: ["btnMod"]
      ,onRowDblClicked : function(rowId, celInd){
         this.callEvent("onButtonClick", ["btnMod"]);
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
             mvno.cmn.download(ROOT + "/org/csAnalyticMgmt/getAcenVocAgntListExcel.json?menuId=MSP2002203", true, {postData: searchData});

             PAGE.FORM1.setItemValue("btnCount1", 0);      // 초기화
             PAGE.FORM1.setItemValue("btnExcelCount1", 0); // 초기화
				     break;

           case "btnAdd" : // 담당자 등록

             mvno.ui.createForm("FORM2");
             mvno.ui.showButton("FORM2" , "btnSave");
             mvno.ui.hideButton("FORM2" , "btnMod");

             var enableItems = ["vocAgntCd", "orgnName", "vocAgntId", "vocAgntName", "mobileNum", "btnOrgFind", "btnUserFind"];
             for(var inx=0; inx < enableItems.length; inx++){
               PAGE.FORM2.enableItem(enableItems[inx]);
             }

             PAGE.FORM2.clear();
             PAGE.FORM2.getInput("vocAgntCd").setAttribute("autocomplete","off");
             PAGE.FORM2.setItemValue("seq", "0");
             PAGE.FORM2.clearChanged();

             mvno.ui.popupWindowById("FORM2", "VOC 담당자 등록", 300, 275, {
               onClose: function(win){win.closeForce();}
             });
				     break;

		       case "btnMod" : // 담당자 수정

             var sData = grid.getSelectedRowData();
             if(sData == null){
               mvno.ui.alert("선택된 데이터가 없습니다.");
               return;
             }

             mvno.ui.createForm("FORM2");
             mvno.ui.showButton("FORM2" , "btnMod");
             mvno.ui.hideButton("FORM2" , "btnSave");

             var disableItems = ["vocAgntCd", "orgnName", "vocAgntId", "vocAgntName", "mobileNum", "btnOrgFind", "btnUserFind"];
             for(var inx=0; inx < disableItems.length; inx++){
               PAGE.FORM2.disableItem(disableItems[inx]);
             }

             PAGE.FORM2.clear();
             PAGE.FORM2.getInput("vocAgntCd").setAttribute("autocomplete","off");
             PAGE.FORM2.setFormData(sData);
             PAGE.FORM2.clearChanged();

             mvno.ui.popupWindowById("FORM2", "VOC 담당자 수정", 300, 275, {
               onClose: function(win){win.closeForce();}
             });
             break;
         }
			}
		} // end of GRID1 ------------------------------------
   ,FORM2 : {
      items: [
         {type: "settings", position: "label-left", labelAlign: "left", labelWidth: 60, offsetLeft: 5}
        ,{type: "block", list : [
            {type: "input", label: "대리점", name: "vocAgntCd", width: 100, readonly: true}
           ,{type: "newcolumn"}
           ,{type: "button", value:"찾기", name: "btnOrgFind", offsetLeft: 0}
         ]}
        ,{type: "block", list: [
            {type: "input", name: "orgnName", width: 150, offsetLeft: 65, readonly: true}
         ]}
        ,{type: "block", list : [
            {type: "input", label: "담당자ID", name: "vocAgntId", width: 100, readonly: true}
           ,{type: "newcolumn"}
           ,{type: "button", value:"찾기", name: "btnUserFind", offsetLeft: 0}
         ]}
        ,{type: "block", list: [
            {type: "input", name: "vocAgntName", width: 150, offsetLeft: 65, readonly: true}
         ]}
        ,{type: "block", list: [
            {type: "input", name: "mobileNum", width: 150, offsetLeft: 65, readonly: true}
         ]}
        ,{type: "block", list: [
            {type: "select", label: "사용여부", name: "useYn", width:150, options:[{value: "", text: "- 전체 -"}, {value: "Y", text: "사용(Y)"}, {value: "N", text: "미사용(N)"}]}
         ]}
        ,{type: 'hidden', name: "seq"}
        ,{type: 'hidden', name: "usrId"}
      ]
     ,buttons: {
        center: {
           btnSave: {label: "등록"}
          ,btnMod: {label: "수정"}
          ,btnClose: {label: "닫기"}
        }
     }
     ,onValidateMore: function(data){

        var form = this;

        if(data.vocAgntCd == ""){
          this.pushError("vocAgntCd", "대리점", "대리점을 선택하세요.");
        }

        if(data.vocAgntId == ""){
          this.pushError("vocAgntId", "담당자ID", "담당자를 선택하세요.");
        }

        if(data.useYn == ""){
            this.pushError("useYn", "사용여부", "사용여부를 선택하세요.");
        }
		 }
     ,onButtonClick: function(btnId){
        var form = this;

        switch(btnId) {

				  case "btnOrgFind" : // 대리점 찾기

            mvno.ui.codefinder("ORG", function(result) {
              form.setItemValue("vocAgntCd", result.orgnId);
              form.setItemValue("orgnName", result.orgnNm);
            });
            break;

          case "btnUserFind" : // 사용자 찾기

            mvno.ui.codefinder("USER", function(result) {

              form.setItemValue("usrId", result.usrId);

              if(maskingYn == "Y" || maskingYn == "1"){
                form.setItemValue("vocAgntId", result.usrId);
                form.setItemValue("vocAgntName", result.usrNm);
                form.setItemValue("mobileNum", result.mblphnNum);
              }else{
                form.setItemValue("vocAgntId", result.usrIdMsk);
                form.setItemValue("vocAgntName", result.usrNmMsk);
                form.setItemValue("mobileNum", result.mblphnNumMsk);
              }
            });
            break;

		      case "btnSave" : // 등록

            mvno.cmn.ajax(ROOT + "/org/csAnalyticMgmt/mergeAcenVocAgnt.json", form, function(resultData) {
              mvno.ui.closeWindowById(form);
              mvno.ui.notify("NCMN0001");
              PAGE.GRID1.refresh();
            });

				    break;

          case "btnMod" : // 수정

            mvno.cmn.ajax(ROOT + "/org/csAnalyticMgmt/mergeAcenVocAgnt.json", form, function(resultData) {
              mvno.ui.closeWindowById(form);
              mvno.ui.notify("NCMN0002");
              PAGE.GRID1.refresh();
            });
            break;

          case "btnClose" : // 닫기
            mvno.ui.closeWindowById(form);
            break;
        }
		 }
	  } // end of FORM2 ------------------------------------
  }; // end of DHX ----------------------------------------

  var PAGE = {
     title : "${breadCrumb.title}"
    ,breadcrumb : "${breadCrumb.breadCrumb}"
    ,buttonAuth: ${buttonAuth.jsonString}
    ,onOpen : function(){

       mvno.ui.createForm("FORM1");
       mvno.ui.createGrid("GRID1");

       mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ACEN0008"}, PAGE.FORM1, "searchGbn"); // 검색구분
     }
  }

</script>