<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>
<div id="FORM2" class="section-box"></div>

<div id="POPUP1">
  <div id="POPUP1TOP" class="section-full"></div>
  <div id="POPUP1MID" class="section-box"></div>
</div>

<!-- Script -->
<script type="text/javascript">
  var startDate = '${srchStrtDt}';
  var endDate = '${srchEndDt}';

  var DHX = {
    FORM1 : {
      items : [
        {type: "settings", position: "label-left", labelAlign: "left", labelWidth: 50, blockOffset: 0}
        ,{type: "block", list: [
          {type: "calendar", dateFormat: "%Y-%m", serverDateFormat: "%Y%m", name: "srchStrtDt", label: "지급월", value: startDate, calendarPosition: "bottom", width: 100, offsetLeft: 10}
          ,{type: "newcolumn"}
          ,{type: "calendar", dateFormat: "%Y-%m", serverDateFormat: "%Y%m", name: "srchEndDt", label: "~", value: endDate, calendarPosition: "bottom", labelAlign: "center", labelWidth: 10, offsetLeft: 5, width:100}
          ,{type: "newcolumn"}
          ,{type: "input", label: "사은품종류", name: "giftType", width: 120, offsetLeft: 40, labelWidth: 60}
          ,{type: "newcolumn"}
          ,{type: "input", label: "프로모션명", name: "promNm", width: 120, offsetLeft: 40, labelWidth: 60}
        ]}
        ,{type: "block", list: [
          {type: "select", label: "검색구분", name: "searchGbn", width: 100, offsetLeft: 10}
          ,{type: "newcolumn"}
          ,{type: "input", name: "searchName", disabled: true, width: 115}
        ]}
        ,{type: "hidden", name: "DWNLD_RSN", value:""} //엑셀다운로드 사유
        ,{type: "hidden", name: "btnCount1", value: "0"}       // 조회 버튼 다중클릭 방지
        ,{type: "hidden", name: "btnExcelCount1", value: "0"}  // 다운로드 버튼 다중클릭 방지
        ,{type: "button", name: "btnSearch", value: "조회", className: "btn-search2"}
      ]
		  ,onValidateMore: function(data){
        var form = this;

        if(data.srchStrtDt > data.srchEndDt){
          this.pushError("srchEndDt", "지급월", "시작일자가 종료일자보다 클 수 없습니다.");
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

          case "btnSearch" : // 조회

            var btnCount2 = parseInt(form.getItemValue("btnCount1", "")) + 1;
            form.setItemValue("btnCount1", btnCount2);
            if (form.getItemValue("btnCount1", "") != "1") return;

            PAGE.GRID1.list(ROOT + "/voc/giftvoc/giftPayStatList.json", form, {
              callback: function () {
                form.resetValidateCss();
              }
            });
            break;
        }
      }
      ,onChange: function(name, value){
        var form = this;

        switch(name) {
          case "searchGbn" : // 검색구분
            form.setItemValue("searchName", "");
            if (mvno.cmn.isEmpty(value)) {
              form.setItemValue("srchStrtDt", startDate);
              form.setItemValue("srchEndDt", endDate);
              form.enableItem("srchStrtDt");
              form.enableItem("srchEndDt");

              form.disableItem("searchName");
            } else {
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
      ,title: "사은품 지급 리스트"
      ,header: "지급월,계약번호,고객명,개통상태,CTN,대리점,제세공과금 회신 여부,프로모션명,사은품,금액" // 10
      ,columnIds: "payMon,contractNum,custNm,subStatus,ctn,openAgntNm,taxRplyYn,promNm,giftType,giftAmt" // 10
      ,widths: "100,100,100,100,100,150,150,150,100,100" // 10
			,colAlign: "center,center,left,center,center,left,center,left,center,right" // 10
      ,colTypes: "roXdm,ro,ro,ro,roXp,ro,ro,ro,ro,ron" // 10
      ,colSorting: "str,str,str,str,str,str,str,str,str,str" // 10
      // ,hiddenColumns: [12]
      ,paging: true
      ,pageSize: 20
      ,buttons: {
        hright: {
          btnExcel: {label: "자료생성"}
        }
        ,right: {
          btnUpload: {label: "엑셀등록"}
        }
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
             if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") return; //버튼 최초 클릭일때만 조회가능하도록

             mvno.cmn.downloadCallback(function(result) {
               PAGE.FORM1.setItemValue("DWNLD_RSN",result);
               mvno.cmn.ajax(ROOT + "/voc/giftvoc/getGiftPayStatExcelDownload.json?menuId=VOC1001070", PAGE.FORM1.getFormData(true), function(result){
                 mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
               });
             });
             PAGE.FORM1.setItemValue("btnCount1", 0);      // 초기화
             PAGE.FORM1.setItemValue("btnExcelCount1", 0); // 초기화

             break;

           case "btnUpload" : // 엑셀업로드

             mvno.ui.createGrid("POPUP1TOP");
             mvno.ui.createForm("POPUP1MID");

             PAGE.POPUP1TOP.clearAll();
             PAGE.POPUP1MID.clear();
             PAGE.POPUP1MID.setFormData({});

             PAGE.POPUP1MID.attachEvent("onFileRemove",function(realName, serverName){
               PAGE.POPUP1TOP.clearAll();

               return true;
             });

             var uploader = PAGE.POPUP1MID.getUploader("file_upload1");
             uploader.revive();

             mvno.ui.popupWindowById("POPUP1", "사은품 지급 리스트 엑셀등록", 950, 720, {
               onClose2: function(win) {
                 uploader.reset();
               }
             });

             break;
         }
			}
		} // end of GRID1 ------------------------------------
    // 엑셀 등록 화면
    ,POPUP1TOP : {
      css : { height : "465px" }
      ,title : "사은품 지급 리스트 엑셀등록"
      ,header : "지급월,계약번호,고객명,CTN,제세공과금 회신 여부,프로모션명,사은품종류,금액" // 8
      ,columnIds : "payMon,contractNum,custNm,ctn,taxRplyYn,promNm,giftType,giftAmt" // 8
      ,widths : "100,100,100,100,150,150,100,100"  // 8
      ,colAlign : "center,center,left,center,center,left,center,right" // 8
      ,colTypes : "roXdm,ro,ro,roXp,ro,ro,ro,ron" // 8
    }   //POPUP1TOP End
    ,POPUP1MID : {
      title : "엑셀업로드"
      ,items : [
        { type : "block"
          ,list : [
            { type : "newcolumn", offset : 20 }
            ,{ type : "upload"
              ,label : "사은품 지급 리스트 엑셀등록파일"
              ,name : "file_upload1"
              ,inputWidth : 830
              ,url : "/rcp/dlvyMgmt/regParExcelUp.do"
              ,userdata : { limitSize:10000 }
              ,autoStart : true }
          ]},
        {type:"input", width:100, name : "rowData", hidden:true}
      ]

      ,buttons : {
        center : {
          btnSave : {label : "저장" },
          btnClose : { label : "닫기" }
        }
      }
      ,onUploadFile : function(realName, serverName) {
        fileName = serverName;
      }
      ,onUploadComplete : function(count){

        var url = ROOT + "/voc/giftvoc/readGiftPayStatExcelUpList.json";
        var userOptions = {timeout:180000};

        mvno.cmn.ajax(url, {fileName : fileName}, function(resultData) {
            var rData = resultData.result;

            PAGE.POPUP1TOP.clearAll();

            PAGE.POPUP1TOP.parse(rData.data.rows, "js");
        }, userOptions);
      }
      ,onButtonClick : function(btnId) {
        var form = this; // 혼란방지용으로 미리 설정 (권고)

        switch (btnId) {

          case "btnSave" :

            if(PAGE.POPUP1TOP.getRowsNum() == 0) {
                mvno.ui.alert("파일을 첨부해 주세요.");
                break;
            }

            var arrObj = [];

            PAGE.POPUP1TOP.forEachRow(function(id){
              var arrData = PAGE.POPUP1TOP.getRowData(id);
              arrObj.push(arrData);
            });

            var sData = { items : arrObj };

            var userOptions = {timeout:180000};

            mvno.cmn.ajax4json(ROOT + "/voc/giftvoc/regGiftPayStatExcel.json", sData, function(result) {
              mvno.ui.alert(result.result.msg);
              PAGE.GRID1.refresh();
              mvno.ui.closeWindowById("POPUP1");
            }, userOptions);

            break;

          case "btnClose":
            mvno.ui.closeWindowById("POPUP1");

            break;
        }
      }
    }
  }; // end of DHX -------------------------------------------------------------

  var PAGE = {
     title : "${breadCrumb.title}"
    ,breadcrumb : "${breadCrumb.breadCrumb}"
    ,buttonAuth: ${buttonAuth.jsonString}
    ,onOpen : function(){

       mvno.ui.createForm("FORM1");
       mvno.ui.createGrid("GRID1");

       mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"VOC0035"}, PAGE.FORM1, "searchGbn");  // 검색구분
    }
  }

</script>
