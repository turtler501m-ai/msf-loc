<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>
<div id="FORM2" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

  var isCntpShop = false;  // 대리점여부
  var cntpntShopId = '';
  var cntpntShopNm = '';

  var typeCd = '${orgnInfo.typeCd}';

  if(typeCd == '20'){
    isCntpShop = true;
    cntpntShopId = '${orgnInfo.orgnId}';
    cntpntShopNm = '${orgnInfo.orgnNm}';
  }

  var DHX = {
    FORM1 : {
      items : [
         {type: "settings", position: "label-left", labelAlign: "left", labelWidth: 50, blockOffset: 0}
        ,{type: "block", list: [
            {type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "srchStrtDt", label: "신청일자", value: "${srchStrtDt}", calendarPosition: "bottom", readonly: true, width: 100, offsetLeft: 10}
           ,{type: "newcolumn"}
           ,{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "srchEndDt", label: "~", value: "${srchEndDt}", calendarPosition: "bottom", readonly: true, labelAlign: "center", labelWidth: 10, offsetLeft: 5, width:100}
           ,{type: "newcolumn"}
           ,{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "compStrtDt", label: "검수일자", calendarPosition: "bottom", readonly: true, disabled:true, width: 100, offsetLeft: 50}
           ,{type: "newcolumn"}
           ,{type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "compEndDt", label: "~", calendarPosition: "bottom", readonly: true, disabled:true, labelAlign: "center", labelWidth: 10, offsetLeft: 5, width: 100}
           ,{type: "newcolumn"}
           ,{type: "checkbox", name: "compUseYn", label: "검수일자 사용", position: "label-right", labelAlign: "left", labelWidth: 100, offsetLeft: 20, width: 5, checked: false}
         ]}
        ,{type: "block", list: [
            {type: "input", label: "대리점", name: "cntpntShopId", value: cntpntShopId, readonly: isCntpShop, width: 100, offsetLeft: 10}
           ,{type: "newcolumn"}
           ,{type: "button", value: "찾기", name: "btnOrgFind", disabled: isCntpShop}
           ,{type: "newcolumn"}
           ,{type: "input", name: "cntpntShopNm", width: 100, value: cntpntShopNm, readonly: true}
           ,{type: "newcolumn"}
           ,{type: "select", label: "검색구분", name: "searchGbn", width: 100, offsetLeft: 15}
           ,{type: "newcolumn"}
           ,{type: "input", name: "searchName", disabled: true, width: 115}
					 ,{type: "newcolumn"}
           ,{type: "select", label: "현재상태", name: "status", width: 120, offsetLeft: 20}
         ]}
        ,{type: "hidden", name: "btnCount1", value: "0"}       // 조회 버튼 다중클릭 방지
        ,{type: "hidden", name: "btnExcelCount1", value: "0"}  // 다운로드 버튼 다중클릭 방지
        ,{type: "button", name: "btnSearch", value: "조회", className: "btn-search2"}
     ]
		 ,onValidateMore: function(data){
        var form = this;

        if(data.srchStrtDt > data.srchEndDt){
          this.pushError("srchEndDt", "신청일자", "시작일자가 종료일자보다 클 수 없습니다.");
        }

        if(data.compStrtDt > data.compEndDt){
          this.pushError("compEndDt", "검수일자", "시작일자가 종료일자보다 클 수 없습니다.");
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

            PAGE.GRID1.list(ROOT + "/voc/vocMgmt/getAcenVocList.json", form, {
              callback: function () {
                form.resetValidateCss();

                // 초기화
                PAGE.FORM2.disableItem("vocContent");
                PAGE.FORM2.disableItem("ansContent");
                PAGE.FORM2.disableItem("callContent");
                PAGE.FORM2.setItemValue("vocContent", "");
                PAGE.FORM2.setItemValue("ansContent", "");
                PAGE.FORM2.setItemValue("callContent", "");
                PAGE.FORM2.setItemValue("reqSeq", "");
                PAGE.FORM2.setItemValue("modType", "");
                PAGE.FORM2.setReadonly("ansContent", true);
                PAGE.FORM2.setReadonly("callContent", true);

                mvno.ui.hideButton("FORM2" , "btnComplete");
                mvno.ui.hideButton("FORM2" , "btnReject");
                mvno.ui.hideButton("FORM2" , "btnCompleteCheck");
                mvno.ui.hideButton("FORM2" , "btnRejectCheck");
                mvno.ui.hideButton("FORM2" , "btnEtcCheck");
                mvno.ui.hideButton("FORM2" , "btnModTemp");

                mvno.ui.showButton("FORM2" , "btnModAns");
                mvno.ui.showButton("FORM2" , "btnModCall");
                if(isCntpShop) mvno.ui.hideButton("FORM2" , "btnModCall");
              }
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

          case "compUseYn" : // 검수일자 사용여부

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
       css: {height : "300px"}
      ,title: "VOC 목록"
      ,header: "상담일련번호,계약번호,개통상태,고객명,대리점,VOC유형,접수일시,현재상태,접수자,처리기한,수정일시,수정자,요청키" // 13
      ,columnIds: "vocSeq,contractNum,subStatusNm,cstmrName,vocAgntCdName,vocSubCtgCdName,vocDttm,statusName,vocRcpName,dueDt,rvisnDttm,rvisnName,reqSeq" // 13
      ,widths: "100,100,100,120,200,200,140,120,120,100,140,120,0" // 13
			,colAlign: "center,center,center,left,left,left,center,center,left,center,center,left,center" // 13
      ,colTypes: "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" // 13
      ,colSorting: "str,str,str,str,str,str,str,str,str,str,str,str,str" // 13
      ,hiddenColumns: [12]
      ,paging: true
      ,pageSize: 20
      ,buttons: {
         hright: {
           btnExcel: {label: "엑셀다운로드"}
         }
        ,right: {
           btnHist: {label: "수정이력"}
          ,btnExcelDetail: {label: "개별엑셀다운로드"}
        }
      }
      ,checkSelectedButtons: ["btnHist", "btnExcelDetail"]
			,onRowSelect: function(rId, cId){
         var grid = this;
         var sdata = grid.getSelectedRowData();

         // 더블클릭 시 rowSelect 두번 호출 방지
         if(sdata.reqSeq == PAGE.FORM2.getItemValue("reqSeq")){
             return;
         }

         // 초기화
         PAGE.FORM2.disableItem("vocContent");
         PAGE.FORM2.disableItem("ansContent");
         PAGE.FORM2.disableItem("callContent");
         PAGE.FORM2.setItemValue("vocContent", "");
         PAGE.FORM2.setItemValue("ansContent", "");
         PAGE.FORM2.setItemValue("callContent", "");
         PAGE.FORM2.setItemValue("reqSeq", "");
         PAGE.FORM2.setItemValue("modType", "");
         PAGE.FORM2.setReadonly("ansContent", true);
         PAGE.FORM2.setReadonly("callContent", true);

         mvno.ui.hideButton("FORM2" , "btnComplete");
         mvno.ui.hideButton("FORM2" , "btnReject");
         mvno.ui.hideButton("FORM2" , "btnCompleteCheck");
         mvno.ui.hideButton("FORM2" , "btnRejectCheck");
         mvno.ui.hideButton("FORM2" , "btnEtcCheck");
         mvno.ui.hideButton("FORM2" , "btnModTemp");

         mvno.ui.showButton("FORM2" , "btnModAns");
         mvno.ui.showButton("FORM2" , "btnModCall");
         if(isCntpShop) mvno.ui.hideButton("FORM2" , "btnModCall");

         mvno.cmn.ajax(ROOT + "/voc/vocMgmt/getAcenVocInfo.json", {reqSeq : sdata.reqSeq}, function(resultData) {
           var vocInfo = resultData.result.vocInfo;
           if(vocInfo != null){
             PAGE.FORM2.setItemValue("vocContent", vocInfo.vocContent);
             PAGE.FORM2.setItemValue("ansContent", vocInfo.ansContent);
             PAGE.FORM2.setItemValue("callContent", vocInfo.callContent);
             PAGE.FORM2.setItemValue("reqSeq", vocInfo.reqSeq);

             PAGE.FORM2.enableItem("vocContent");
             PAGE.FORM2.enableItem("ansContent");
             PAGE.FORM2.enableItem("callContent");
           }
         });
			}
      ,onRowDblClicked: function(rowId, celInd){
         this.callEvent('onButtonClick', ['btnMod']);
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
             mvno.cmn.download(ROOT + "/voc/vocMgmt/getAcenVocListExcel.json?menuId=VOC1001060", true, {postData: searchData});

             PAGE.FORM1.setItemValue("btnCount1", 0);      // 초기화
             PAGE.FORM1.setItemValue("btnExcelCount1", 0); // 초기화
				     break;

           case "btnHist" : // 수정이력

             var sdata = grid.getSelectedRowData();
             if(!sdata){
               mvno.ui.alert("선택한 데이터가 없습니다.");
               return;
             }

             mvno.ui.createGrid("GRID2");
             PAGE.GRID2.clearAll();
             PAGE.GRID2.list(ROOT + "/voc/vocMgmt/getAcenVocHistList.json", {reqSeq: sdata.reqSeq});

             mvno.ui.popupWindowById("GRID2", "수정이력", 860, 320, {
               onClose: function(win){win.closeForce();}
             });
             break;

           case "btnExcelDetail" : // 개별엑셀다운로드

		         var sdata = grid.getSelectedRowData();
             if(!sdata){
               mvno.ui.alert("선택한 데이터가 없습니다.");
               return;
             }

             var searchData = {reqSeq: sdata.reqSeq};
             mvno.cmn.download(ROOT + "/voc/vocMgmt/getAcenVocHistListExcel.json?menuId=VOC1001060", true, {postData: searchData});
             break;

		       case "btnMod" : // 수정

             if(isCntpShop) return;

             var sdata = grid.getSelectedRowData();
             if(!sdata){
               mvno.ui.alert("선택한 데이터가 없습니다.");
               return;
             }

             var modEnableStatus = ["00", "01", "02"];
             if(!modEnableStatus.includes(sdata.status)){
               mvno.ui.alert("해당건은 VOC 처리점 등록이<br>불가능한 상태입니다.");
               return;
		         }

             mvno.ui.createForm("FORM3");
             mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ACEN0005", etc1: "Y"}, PAGE.FORM3, "status"); // 진행상태

             PAGE.FORM3.clear();
             PAGE.FORM3.getInput("vocAgntCd").setAttribute("autocomplete","off");
             PAGE.FORM3.setFormData(sdata);
             PAGE.FORM3.setItemValue("dueDt", sdata.dueDt.replaceAll("-", ""));
             PAGE.FORM3.clearChanged();

             mvno.ui.popupWindowById("FORM3", "VOC 처리점 등록", 350, 230, {
               onClose: function(win){win.closeForce();}
             });

				     break;
         }
			}
		} // end of GRID1 ------------------------------------
	 ,FORM2 : {
       title: "VOC 처리내용"
      ,items: [
          {type: 'settings', position: 'label-top', labelAlign: "left", labelWidth: 100}
         ,{type: "block", list: [
             {type: 'input', name: 'vocContent', label: '상담내용', rows: '8', width: 290, offsetLeft: 10, maxLength: 500, disabled: true, readonly: true}
            ,{type: "newcolumn"}
            ,{type: 'input', name: 'ansContent', label: '처리내용', rows: '8', width: 290, offsetLeft: 15, maxLength: 500, disabled: true, readonly: true}
            ,{type: "newcolumn"}
            ,{type: 'input', name: 'callContent', label: '해피콜결과', rows: '8', width: 290, offsetLeft: 15, maxLength: 500, disabled: true, readonly: true}
          ]}
         ,{type: 'hidden', name: "reqSeq"}
         ,{type: 'hidden', name: "modType"}
      ]
      ,buttons: {
          right: {
             btnModAns: {label: "처리내용 수정"}
            ,btnModCall: {label: "해피콜 결과 수정"}
            ,btnComplete: {label: "처리완료"}
            ,btnReject: {label: "처리불가"}
            ,btnCompleteCheck: {label: "검수완료(적격)"}
            ,btnRejectCheck: {label: "검수완료(부적격)"}
            ,btnEtcCheck: {label: "검수완료(기타)"}
            ,btnModTemp: {label: "임시저장"}
          }
      }
      ,onButtonClick: function(btnId){
         var form = this;

         switch(btnId) {

		       case "btnModAns" : // 처리내용 수정

             var sdata = PAGE.GRID1.getSelectedRowData();
             if(!sdata || form.getItemValue("reqSeq") == ""){
               mvno.ui.alert("선택한 데이터가 없습니다.");
               return;
             }

             var modEnableStatus = ["00", "01"];
             if(!modEnableStatus.includes(sdata.status)){
               mvno.ui.alert("해당건은 수정이 불가능한 상태입니다.");
               return;
             }

             mvno.ui.hideButton("FORM2" , "btnModAns");
             mvno.ui.hideButton("FORM2" , "btnModCall");
             mvno.ui.hideButton("FORM2" , "btnCompleteCheck");
             mvno.ui.hideButton("FORM2" , "btnRejectCheck");
             mvno.ui.hideButton("FORM2" , "btnEtcCheck");

             mvno.ui.showButton("FORM2" , "btnComplete");
             mvno.ui.showButton("FORM2" , "btnReject");
             mvno.ui.showButton("FORM2" , "btnModTemp");

             form.setReadonly("ansContent", false);
             form.setReadonly("callContent", true);
             form.setItemValue("modType", "ANS");
             form.setItemFocus("ansContent");
             break;

           case "btnModCall" : // 해피콜 결과 수정

             var sdata = PAGE.GRID1.getSelectedRowData();
             if(!sdata || form.getItemValue("reqSeq") == ""){
               mvno.ui.alert("선택한 데이터가 없습니다.");
               return;
             }

             var modDisableStatus = ["05", "06", "07"];
             if(modDisableStatus.includes(sdata.status)){
               mvno.ui.alert("검수완료된 건으로 수정이 불가합니다.");
               return;
             }

             var modEnableStatus = ["03", "04"];
             if(!modEnableStatus.includes(sdata.status)){
               mvno.ui.alert("미완료 건으로 검수가 불가능합니다.");
               return;
             }

             mvno.ui.hideButton("FORM2" , "btnModAns");
             mvno.ui.hideButton("FORM2" , "btnModCall");

             mvno.ui.hideButton("FORM2" , "btnComplete");
             mvno.ui.hideButton("FORM2" , "btnReject");
             mvno.ui.showButton("FORM2" , "btnCompleteCheck");
             mvno.ui.showButton("FORM2" , "btnRejectCheck");
             mvno.ui.showButton("FORM2" , "btnEtcCheck");
             mvno.ui.showButton("FORM2" , "btnModTemp");

             form.setReadonly("ansContent", true);
             form.setReadonly("callContent", false);
             form.setItemValue("modType", "CALL");
             form.setItemFocus("callContent");
             break;

		       case "btnComplete" :      // 처리완료
           case "btnCompleteCheck" : // 적격

             var sdata = PAGE.GRID1.getSelectedRowData();
             if(!sdata){
               mvno.ui.alert("선택한 데이터가 없습니다.");
               return;
             }

             var modType = form.getItemValue("modType");
             var content = (modType == "ANS") ? form.getItemValue("ansContent") : form.getItemValue("callContent");

             if(mvno.cmn.isEmpty(content.trim())){
               mvno.ui.alert("입력된 내용이 없습니다.");
               return;
             }

             var idata = form.getFormData(true);
             idata.status = (modType == "ANS") ? "03" : "05";

             mvno.cmn.ajax(ROOT + "/voc/vocMgmt/updateAcenVocContent.json", idata, function(resultData) {
               form.setReadonly("ansContent", true);
               form.setReadonly("callContent", true);
               form.setItemValue("modType", "");
               mvno.ui.notify("NCMN0001");
               PAGE.GRID1.refresh();
             });
             break;

		       case "btnReject" :      // 처리불가
           case "btnRejectCheck" : // 부적격

             var sdata = PAGE.GRID1.getSelectedRowData();
             if(!sdata){
               mvno.ui.alert("선택한 데이터가 없습니다.");
               return;
             }

             var modType = form.getItemValue("modType");
             var content = (modType == "ANS") ? form.getItemValue("ansContent") : form.getItemValue("callContent");

             if(mvno.cmn.isEmpty(content.trim())){
               mvno.ui.alert("입력된 내용이 없습니다.");
               return;
             }

             var idata = form.getFormData(true);
             idata.status = (modType == "ANS") ? "04" : "06";

             mvno.cmn.ajax(ROOT + "/voc/vocMgmt/updateAcenVocContent.json", idata, function(resultData) {
               form.setReadonly("ansContent", true);
               form.setReadonly("callContent", true);
               form.setItemValue("modType", "");
               mvno.ui.notify("NCMN0001");
               PAGE.GRID1.refresh();
             });
             break;

		       case "btnEtcCheck" : // 기타

             var sdata = PAGE.GRID1.getSelectedRowData();
             if(!sdata){
               mvno.ui.alert("선택한 데이터가 없습니다.");
               return;
             }

             var modType = form.getItemValue("modType");
             var content = form.getItemValue("callContent");

             if(mvno.cmn.isEmpty(content.trim())){
               mvno.ui.alert("입력된 내용이 없습니다.");
               return;
             }

             var idata = form.getFormData(true);
             idata.status = "07";

             mvno.cmn.ajax(ROOT + "/voc/vocMgmt/updateAcenVocContent.json", idata, function(resultData) {
               form.setReadonly("callContent", true);
               form.setItemValue("modType", "");
               mvno.ui.notify("NCMN0001");
               PAGE.GRID1.refresh();
             });
             break;

           case "btnModTemp" : // 임시저장

             var sdata = PAGE.GRID1.getSelectedRowData();
             if(!sdata){
               mvno.ui.alert("선택한 데이터가 없습니다.");
               return;
             }

             var modType = form.getItemValue("modType");
             var content = (modType == "ANS") ? form.getItemValue("ansContent") : form.getItemValue("callContent");

             if(mvno.cmn.isEmpty(content.trim())){
               mvno.ui.alert("입력된 내용이 없습니다.");
               return;
             }

             var idata = form.getFormData(true);

             mvno.cmn.ajax(ROOT + "/voc/vocMgmt/updateAcenVocContent.json", idata, function(resultData) {
               form.setReadonly("ansContent", true);
               form.setReadonly("callContent", true);
               form.setItemValue("modType", "");
               mvno.ui.notify("NCMN0001");
               PAGE.GRID1.refresh();
             });
             break;
         }
			}
		} // end of FORM2 ------------------------------------
   ,GRID2 : {
       css: {height: "200px", width: "785px"}
      ,header: "수정일시,대리점,VOC유형,처리기한,현재상태,상담내용,처리내용,해피콜 결과,수정자" // 9
      ,columnIds: "regstDttm,vocAgntCdName,vocSubCtgCdName,dueDt,statusName,vocContent,ansContent,callContent,regstName" // 9
      ,widths: "140,180,200,100,120,200,200,200,120" // 9
      ,colAlign: "center,left,left,center,center,left,left,left,left" // 9
      ,colTypes: "ro,ro,ro,ro,ro,ro,ro,ro,ro" // 9
		  ,colSorting: "str,str,str,str,str,str,str,str,str" // 9
      ,paging: true
      ,pageSize: 10
    } // end of GRID2 ------------------------------------
	 ,FORM3 : {
      items: [
          {type: "settings", position: "label-left", labelAlign: "left", labelWidth: 60, offsetLeft: 15}
         ,{type: "block", list : [
             {type: "input", label: "대리점", name: "vocAgntCd", width: 100}
            ,{type: "newcolumn"}
            ,{type: "button", value:"찾기", name: "btnOrgFind", disabled: isCntpShop, offsetLeft: 0}
          ]}
         ,{type: "block", list: [
             {type: "input", name: "orgnName", width: 150, offsetLeft: 75, readonly: true}
          ]}
         ,{type: "block", list: [
             {type: 'calendar', dateFormat: '%Y-%m-%d', serverDateFormat: '%Y%m%d', name: 'dueDt', label: '처리기한', calendarPosition: 'bottom', readonly: true, width: 150}
          ]}
         ,{type: "block", list: [
             {type: "select", label: "진행상태", name: "status", width:150}
          ]}
         ,{type: 'hidden', name: "reqSeq"}
       ]
      ,buttons: {
         center: {
            btnSave: {label: "저장"}
           ,btnClose: {label: "닫기"}
         }
      }
      ,onValidateMore: function(data){

         var form = this;

         if(data.vocAgntCd == ""){
           this.pushError("vocAgntCd", "대리점", "대리점을 선택하세요.");
         }

         if(data.dueDt < new Date().format("yyyymmdd")){
           this.pushError("dueDt", "처리기한", "오늘 이전 날짜를 선택할 수 없습니다.");
         }

         if(data.status == ""){
           this.pushError("status", "진행상태", "진행상태를 선택하세요.");
         }else if(data.status != "01"){
           this.pushError("status", "진행상태", "진행중 상태로만 변경 가능합니다.");
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

             case "btnSave" : // 등록

               var sdata = PAGE.GRID1.getSelectedRowData();
               var confirmMsg = "수정하시겠습니까?";
               var url = "/voc/vocMgmt/updateAcenVocInfo.json";

               if((form.getItemValue("vocAgntCd") != "" && sdata.vocAgntCd != form.getItemValue("vocAgntCd"))
                  || (form.getItemValue("status") != "" && sdata.status != form.getItemValue("status"))){
                 confirmMsg = "해당 대리점 담당자가 지정되어 있는 경우<br>문자가 발송됩니다. 수정하시겠습니까?";
               }

               mvno.cmn.ajax4confirm(confirmMsg, ROOT + url, form, function(resultData) {
                 mvno.ui.closeWindowById(form);
                 mvno.ui.notify("NCMN0001");
                 PAGE.GRID1.refresh();
               }, {dimmed:true});
               break;

             case "btnClose" : // 닫기
               mvno.ui.closeWindowById(form);
               break;
         }
			}
		} // end of FORM3 ------------------------------------
  }; // end of DHX -------------------------------------------------------------

  var PAGE = {
     title : "${breadCrumb.title}"
    ,breadcrumb : "${breadCrumb.breadCrumb}"
    ,buttonAuth: ${buttonAuth.jsonString}
    ,onOpen : function(){

       mvno.ui.createForm("FORM1");
       mvno.ui.createGrid("GRID1");
       mvno.ui.createForm("FORM2");

       mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ACEN0005"}, PAGE.FORM1, "status");     // 현재상태
       mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ACEN0007"}, PAGE.FORM1, "searchGbn");  // 검색구분

			 mvno.ui.hideButton("FORM2" , "btnComplete");
       mvno.ui.hideButton("FORM2" , "btnReject");
       mvno.ui.hideButton("FORM2" , "btnCompleteCheck");
       mvno.ui.hideButton("FORM2" , "btnRejectCheck");
       mvno.ui.hideButton("FORM2" , "btnEtcCheck");
       mvno.ui.hideButton("FORM2" , "btnModTemp");

			 if(isCntpShop){
         mvno.ui.hideButton("FORM2" , "btnModCall");
			 }
    }
  }

</script>
