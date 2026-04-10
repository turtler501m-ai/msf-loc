<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : bpaView.jsp
   * @Description : 정산 데이터 다운로드 화면
   * @
   * @ 수정일      수정자 수정내용
   * @ ---------- ------ -----------------------------
   * @ 2026.02.19      최초생성
   * @Create Date : 2026.02.19
   */
%>

<!-- 검색 -->
<div id="FORM1" class="section-search"></div>

<!-- 조회결과 + 설명 -->
<div class="row">
  <div id="GRID1" class="c5"></div>
  <div id="FORM2" class="c5 section-box"></div>
</div>

<!-- 기간유형 -->
<div id="FORM3" class="section-search"></div>
<div id="FORM4" class="section-search"></div>

<!-- 파일목록 -->
<div id="GRID2"></div>

<!-- 업무 등록/수정 팝업 컨테이너 -->
<div id="FORM5" class="section-box"></div>

<!-- 파일관리 팝업 컨테이너 (컨테이너만 유지: createForm("POPUP1") 호출 금지) -->
<div id="POPUP1">
  <div id="POPUP1_FORM" class=" section-box"></div>
  <div class="row">
    <div id="GRID4" class="c5"></div>
    <div id="FORM6" class="c5 section-box"></div>
  </div>
</div>

<!-- 엑셀 업로드 팝업 컨테이너 -->
<div id="POPUP2">
  <div id="POPUP2TOP"  class="section-full"></div>
  <div id="POPUP2MID" class="section-box"></div>
</div>

<script type="text/javascript">
  var isAdminDev = "${isAdminDev}";
  var bpaTargetCnt = "1000";
  var fileName = "";

  var DHX = {

    // ===================== 검색 =====================
    FORM1: {
      items: [
        { type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0 },
        { type:"select", width:300, label:"업무명", name:"bpaId" },
        { type:"button", name:"btnSearch", value:"조회", className:"btn-search1" },
      ],
      onButtonClick: function(btnId) {
        var form = this;

        switch (btnId) {
          case "btnSearch":
            PAGE.GRID1.list(ROOT + "/org/bpaMgmt/getDataTaskList.json", form, {
              callback: function() {

                // validation 초기화
                form.resetValidateCss();

                // 초기화
                PAGE.GRID2.clearAll();
                PAGE.FORM2.setItemValue("description", "");
                $("#FORM3").hide();
                $("#FORM4").hide();

                // 버튼 초기화
                mvno.ui.hideButton("GRID2", "btnExcelUpload");
                mvno.ui.hideButton("GRID2", "btnExcelUpleadTempDn");
                mvno.ui.hideButton("GRID2", "btnDnExcel");

                // useYn === "N"일 경우, 색상 red
                PAGE.GRID1.forEachRow(function(id){
                  if(PAGE.GRID1.cellById(id, 3).getValue() === "N") {
                    PAGE.GRID1.setRowTextStyle(id, "color: red");
                  }
                });
              }
            });

            break;
        }
      }
    },

    // ===================== 조회결과 (GRID1) =====================
    GRID1: {
      css: { height: "300px" },
      title: "조회결과",
      header: "업무코드,업무명,등록일자,사용여부",
      columnIds: "bpaId,taskNm,regstDttm,usgYn",
      colAlign: "center,left,center,center",
      widths: "85,280,85,0",
      colTypes: "ro,ro,ro,ro",
      colSorting: "str,str,str,str",
      hiddenColumns: [3],
      onRowSelect: function(rowId, celInd) {
        var grid = this;
        var sdata = grid.getSelectedRowData();

        // 설명 (html)
        var description = sdata.description || "";
        description = description.replace(/\r\n|\n/g, "<br/>")
        PAGE.FORM2.setItemValue("description", description);

        // 기간유형 별 화면 세팅
        if (sdata.periodCd === "01") {
          // 기준일자
          $("#FORM4").hide();
          $("#FORM3").show();
        } else if (sdata.periodCd === "02") {
          // 기간조회(시작일자+종료일자)
          $("#FORM3").hide();
          $("#FORM4").show();
        } else {
          $("#FORM3").hide();
          $("#FORM4").hide();
        }

        // excelYn에 따라 버튼 표시/숨김
        mvno.ui.showButton("GRID2", "btnDnExcel");
        var excelYn = sdata.excelYn;
        if (excelYn === "Y") {
          mvno.ui.showButton("GRID2", "btnExcelUpload");
          mvno.ui.showButton("GRID2", "btnExcelUpleadTempDn");
        } else {
          mvno.ui.hideButton("GRID2", "btnExcelUpload");
          mvno.ui.hideButton("GRID2", "btnExcelUpleadTempDn");
        }

        // 날짜 선택 초기화
        PAGE.FORM3.setItemValue("startDt", "");
        PAGE.FORM4.setItemValue("startDt", "");
        PAGE.FORM4.setItemValue("endDt", "");

        // 파일목록 조회
        PAGE.GRID2.list(ROOT + "/org/bpaMgmt/getDataTaskFileList.json", { bpaId : sdata.bpaId }, {
          callback: function () {
            // useYn === "N"일 경우, 색상 red
            PAGE.GRID2.forEachRow(function(id){
              if(PAGE.GRID2.cellById(id, 3).getValue() === "N") {
                PAGE.GRID2.setRowTextStyle(id, "color: red");
              }
            });
          }
        });
      }
    },

    // ===================== 설명 (FORM2) =====================
    FORM2: {
      css: { height: "273px", marginTop: "25px" },
      title: "",
      items: [
        { type:"settings", position:"label-left", labelAlign:"left", labelWidth: 0, blockOffset: 0 },
        { type: "template", name: "description", offsetLeft: 10, width: 415, value: "",}
      ]
      ,buttons: {
        hright: {
          btnReg:  { label: "업무등록" },
          btnMod:  { label: "업무수정" },
          btnFile: { label: "파일관리" }
        }
      }
      ,onButtonClick: function(btnId) {
        var form = this;

        switch (btnId) {
          case "btnReg":
            // 업무등록 버튼 클릭 시, 폼 생성
            mvno.ui.createForm("FORM5");

            // 기간유형 selectBox (공통코드)
            mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", { grpId:"BPA0003" }, PAGE.FORM5, "periodCd");
            // 업무명/업무코드 selectBox (공통코드)
            mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", { grpId:"BPA0001" }, PAGE.FORM5, "bpaId");

            PAGE.FORM5.clear();
            PAGE.FORM5.enableItem("bpaId");
            PAGE.FORM5.setItemValue("btnMode", "I");

            // 등록 팝업
            mvno.ui.popupWindowById("FORM5", "정산 업무 등록", 480, 480, {
              onClose: function(win) {
                win.closeForce();
              }
            });

            break;

          case "btnMod":
            var selected = PAGE.GRID1.getSelectedRowData();
            if (!selected) {
              mvno.ui.alert("선택된 데이터가 없습니다.");
              return;
            }

            mvno.ui.createForm("FORM5");

            mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", { grpId:"BPA0001" }, PAGE.FORM5, "bpaId");
            mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", { grpId:"BPA0003" }, PAGE.FORM5, "periodCd");

            PAGE.FORM5.clear();
            PAGE.FORM5.disableItem("bpaId");
            PAGE.FORM5.setFormData(selected);
            PAGE.FORM5.setItemValue("btnMode", "U");

            // 수정 팝업
            mvno.ui.popupWindowById("FORM5", "정산 업무 수정", 480, 480, {
              onClose: function(win) {
                win.closeForce();
              }
            });

            break;

          case "btnFile":
            var selected = PAGE.GRID1.getSelectedRowData();
            if (!selected) {
              mvno.ui.alert("선택된 데이터가 없습니다.");
              return;
            }

            // 파일관리 팝업 상단 (업무코드, 업무명)
            mvno.ui.createForm("POPUP1_FORM");
            mvno.ui.createGrid("GRID4");
            mvno.ui.createForm("FORM6");
            PAGE.FORM6.clear();
            PAGE.POPUP1_FORM.clear();
            PAGE.POPUP1_FORM.setFormData(selected);
            PAGE.FORM6.setItemValue("bpaId", selected.bpaId);

            // 배치코드 selectBox (공통코드)
            mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"BPA0002", orderBy: "etc1"}, PAGE.FORM6, "batchJobId");

            //  File 목록 조회
            PAGE.GRID4.list(ROOT + "/org/bpaMgmt/getDataTaskFileList.json", { bpaId: selected.bpaId }, {
              callback: function () {
                PAGE.GRID4.forEachRow(function(id){
                  if(PAGE.GRID4.cellById(id, 2).getValue() === "N") {
                    PAGE.GRID4.setRowTextStyle(id, "color: red");
                  }
                });
              }
            });

            // 파일관리 팝업
            mvno.ui.popupWindowById("POPUP1", "파일관리", 850, 500, {
              onClose: function(win) {
                win.closeForce();
              }});

            break;
        }
      }
    },

    // ===================== 기준일자 (FORM3) =====================
    FORM3: {
      items: [
        { type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0 },
        { type:"block", list:[
            { type : "calendar", name:"startDt", label:"기준일자", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:50, width:100, calendarPosition: "bottom", validate: "NotEmpty", readonly: true }
          ]}
      ]
    },

    // ===================== 기간조회 (FORM4) =====================
    FORM4: {
      items: [
        { type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0 },
        { type:"block", list:[
            { type : "calendar", name:"startDt", label:"기간조회", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:50, width:100, calendarPosition: "bottom", validate: "NotEmpty", readonly: true },
            { type : "newcolumn" },
            { type : "calendar", name:"endDt", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", label:"~", labelWidth:30, labelAlign:"center", width:100, calendarPosition: "bottom", validate: "NotEmpty", readonly: true },
          ]}
      ]
    },

    // ===================== 파일목록 (GRID2) =====================
    GRID2: {
      css: { height: "200px" },
      title: "파일목록",
      header: "파일명,비고,파일코드,사용여부" ,
      columnIds: "fileNm,rmk,fileId,useYn",
      colAlign: "left,left,center,center",
      widths: "510,440,0,0",
      colTypes: "ro,ro,ro,ro",
      colSorting: "str,str,str,str",
      hiddenColumns: [2,3],
      buttons: {
        hright: {
          btnExcelUpload: { label : "엑셀업로드" },
          btnExcelUpleadTempDn : { label : "엑셀업로드양식" },
          btnDnExcel: { label: "자료생성" }
        }
      },
      onButtonClick: function(btnId) {
        var grid = this;

        switch (btnId) {

          case "btnDnExcel":
            var sdata = PAGE.GRID1.getSelectedRowData();
            if (!sdata) {
              mvno.ui.alert("업무를 선택해주세요.");
              return;
            }

            var fileRow = this.getSelectedRowData();
            if (!fileRow) {
              mvno.ui.alert("파일을 선택해주세요.");
              return;
            }

            var periodCd = sdata.periodCd;
            var excelYn = sdata.excelYn;
            var baseDt = PAGE.FORM3.getItemValue("startDt", true);
            var startDt = PAGE.FORM4.getItemValue("startDt", true);
            var endDt   = PAGE.FORM4.getItemValue("endDt", true);

            if (periodCd === "01") {
              if (!baseDt) {
                mvno.ui.alert("기준일자를 선택해주세요.");
                return;
              }
            } else if (periodCd === "02") {
              if (!startDt || !endDt) {
                mvno.ui.alert("시작일자 또는 종료일자를 선택해주세요.");
                return;
              }
              if (startDt > endDt){
                mvno.ui.alert("시작일자가 종료일자보다 클 수 없습니다.");
                return;
              }
            }

            // 자료생성 시, 등록된 엑셀 리스트가 존재하는지 확인
            if (excelYn === "Y") {
              var hasUploadData = true;
              mvno.cmn.ajax("/org/bpaMgmt/getDataExcelList.json", {bpaId : sdata.bpaId}, function(resultData) {
                var rData = resultData.result.data.rows;
                if (rData.length === 0) {
                  hasUploadData = false;
                  mvno.ui.alert("업로드된 데이터가 없습니다.")
                }
              }, {async: false});

              if (!hasUploadData) {
                return;
              }
            }

            // 자료생성 > 다운로드 사유
            mvno.cmn.downloadCallback(function(rsn) {
              var params = {
                DWNLD_RSN : rsn,
                bpaId : sdata.bpaId,
                batchJobId : fileRow.batchJobId,
                startDt : startDt,
                endDt : endDt,
                periodCd : periodCd,
                excelYn : excelYn,
                etc1 : fileRow.etc1
              };

              if (periodCd === "01") {
                params.startDt = baseDt;
              }

              mvno.cmn.ajax(ROOT + "/org/bpaMgmt/getDataTaskFileExcelDown.json?menuId=MSP2002401", params, function() {
                mvno.ui.alert("검색조건 및 선택한 날짜를 기준으로 엑셀자료를 생성합니다.");
              });

            });

            break;

          case "btnExcelUpleadTempDn" :
            var sdata = PAGE.GRID1.getSelectedRowData();
            if (!sdata) {
              mvno.ui.alert("선택된 데이터가 없습니다.");
              return;
            }

            // 엑셀 템플릿 다운로드
            var bpaId = sdata.bpaId;
            mvno.cmn.download("/org/bpaMgmt/getDataTaskExcelTempDown.json?bpaId=" + bpaId);
            break;

          case "btnExcelUpload":
            var sdata = PAGE.GRID1.getSelectedRowData();
            if (!sdata) {
              mvno.ui.alert("선택된 데이터가 없습니다.");
              return;
            }

            mvno.ui.createGrid("POPUP2TOP");
            mvno.ui.createForm("POPUP2MID");
            PAGE.POPUP2TOP.clearAll();
            PAGE.POPUP2MID.clear();
            PAGE.POPUP2MID.setFormData({});

            PAGE.POPUP2MID.attachEvent("onFileRemove",function(realName, serverName){
              PAGE.POPUP2TOP.clearAll();
              return true;
            });

            var params = {
              bpaId: sdata.bpaId,
            }
            PAGE.POPUP2TOP.list(ROOT + "/org/bpaMgmt/getDataExcelList.json", params);

            // 전역에 선언된 파일명 초기화 (파일 선택 후, 업로드하지 않고 팝업을 닫은 경우 >> fileName이 전역변수에 세팅되어있어 저장될 수 있음)
            fileName = "";

            var uploader = PAGE.POPUP2MID.getUploader("file_upload1");
            uploader.revive();

            // 엑셀 업로드 팝업
            mvno.ui.popupWindowById("POPUP2", "엑셀업로드", 450, 500, {
              onClose2: function() {
                uploader.reset();
              }
            });

            break;
        }
      }
    },

    // ===================== 업무 등록/수정 팝업 폼 (FORM5) =====================
    FORM5: {
      items : [
        {type: "settings", position:"label-left", labelAlign:"left", labelWidth:80, blockOffset:0},
        {type:"block", list: [
            {type: "select",  name: "bpaId", label: "업무명", width: 315, value: ""}]
        },
        {type:"block", list: [
            {type: "select", name: "periodCd", label: "기간유형", width: 130,},
            {type: 'newcolumn'},
            {type: "select", offsetLeft: 30, label: "엑셀업로드", name: "excelYn", width: 69
              , options: [{value: "", text: "- 선택 -"}, {value: "N", text: "미사용"}, {value: "Y", text: "사용"}]}
          ]
        },
        {type:"block", list: [
            {type: "input", name: "description", label: "설명", width: 315, value: "", maxLength: 330, rows: '20'}]
        },
        {type: "hidden", name: "btnMode", value: "I"}, // 등록/수정 분기
      ],
      buttons : {
        center : {
          btnSave :  { label : "저장" },
          btnClose : { label : "닫기" }
        }
      },

      onButtonClick: function(btnId) {
        var form = this;

        switch (btnId) {
          case "btnSave":
            form.resetValidateCss();

            var url = "/org/bpaMgmt/insertDataTask.do";
            if(form.getItemValue("btnMode") === "U"){
              url = "/org/bpaMgmt/updateDataTask.do";
            }

            // 등록/수정
            mvno.cmn.ajax(ROOT + url , form, function(result) {
              mvno.ui.closeWindowById(form, true);
              mvno.ui.notify("NCMN0004"); // 저장되었습니다.
              PAGE.GRID1.refresh();
            });

            break;

          case "btnClose":
            mvno.ui.closeWindowById("FORM5");
            break;
        }
      }
      ,onValidateMore: function(data){
        var form = this;

        if(data.bpaId === ""){
          this.pushError("bpaId", "업무명", "업무명을 선택하세요.");
        }

        if(data.periodCd === ""){
          this.pushError("periodCd", "기간유형", "기간유형을 선택하세요.");
        }

        if(data.excelYn === ""){
          this.pushError("excelYn", "엑셀업로드", "엑셀업로드 여부를 선택하세요.");
        }
      }},

    // ===================== 파일관리 상단 (POPUP1_FORM) =====================
    POPUP1_FORM : {
      items: [
        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
        {type:"block", blockOffset:0,  list: [
            {type:"input", name:"bpaId", label:"업무코드", width:120, value:"", readonly:true, disabled:true},
            {type: "newcolumn"},
            {type:"input", name:"taskNm", label:"업무명", offsetLeft:30, labelWidth:50, width:300, value:"", readonly: true, disabled: true},
        ]},
      ]
    },

    // ===================== 파일관리 목록 (GRID4) =====================
    GRID4: {
      css: { height: "280px", width: "400px" },
      title: "파일목록",
      header: "파일명,배치코드,사용여부",
      columnIds: "fileNm,batchJobId,useYn",
      colAlign: "left,center,center",
      widths: "*,0,0",
      colTypes: "ro,ro,ro",
      colSorting: "str,str,str",
      hiddenColumns: [1,2],
      onRowSelect: function(rowId, celInd) {
        var grid = this;
        var sdata = grid.getSelectedRowData();

        PAGE.FORM6.clear();
        PAGE.FORM6.setFormData(sdata);
      }
    },

    // ===================== 파일관리 FORM (FORM6) =====================
    FORM6: {
      css: { height: "255px", marginTop: "25px" },
      title: "",
      items: [
        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:50, blockOffset:0, offsetLeft: 5 },
        { type: "block", blockOffset: 0, list: [
          {type: "input", name: "fileId", label: "파일코드", width: 250, value: "",  maxLength: 20, disabled: true},
        ]},
        { type: "block", blockOffset: 0, list: [
          {type: "input", name: "fileNm", label: "파일명", width: 250, value: "", maxLength: 100},
        ]},
        { type: "block", blockOffset: 0, list: [
          {type: "select", name: "batchJobId", label:"배치명", width: 250,},
        ]},
        { type: "block", blockOffset: 0, list: [
          {type: "input", name: "rmk", label: "비고", width: 250, value: "", maxLength: 50},
        ]},
        { type: "block", blockOffset: 0, list: [
            {type: "input", name: "sortNo", width: 80, label: "정렬순서", value: "", maxLength: 20},
            {type: "newcolumn"},
            {type: "select", name: "useYn", label: "사용여부", offsetLeft:17, width: 95,
              options: [{value:"Y", text:"사용", selected:true}, {value:"N", text:"미사용"}],},
        ]},
        { type: "block", blockOffset: 0, list: [
          {type: "input", name: "etc1", label: "기타1", width: 250, value: "", maxLength: 50},
        ]},
        { type: "block", blockOffset: 0, list: [
            {type: "input", name: "etc2", label: "기타2", width: 250, value: "", maxLength: 50},
        ]},
        { type: "block", blockOffset: 0, list: [
          {type: "input", name: "etc3", label: "기타3", width: 250, value: "", maxLength: 50},
        ]},
        {type: "hidden", name: "bpaId", value: ""},
      ],
      buttons: {
        left:  { btnInit: {label: "초기화"} },
        right: { btnReg:  {label: "등록"}, btnMod: {label: "수정"} }
      },
      onButtonClick: function(btnId) {
        var form = this;

        switch (btnId) {
          case "btnInit":
            form.clear();
            form.setItemValue("bpaId", PAGE.POPUP1_FORM.getItemValue("bpaId"));
            PAGE.GRID4.clearSelection(); // 그리드 내 선택한 데이터 취소
            break;

          case "btnReg":
            form.resetValidateCss();

            var fileId = form.getItemValue("fileId");

            if (fileId !== "") {
              mvno.ui.alert("NCMN0018"); // 이미 등록된 파일입니다
              return;
            }

            mvno.cmn.ajax(ROOT + "/org/bpaMgmt/insertDataTaskFile.do", form, function() {
              mvno.ui.notify("NCMN0001"); // 등록되었습니다.
              PAGE.GRID4.refresh();
              form.clear();
              form.setItemValue("bpaId", PAGE.POPUP1_FORM.getItemValue("bpaId"));
              PAGE.GRID2.refresh();
            });

            break;

          case "btnMod":
            form.resetValidateCss();

            var fileId = form.getItemValue("fileId");
            if (fileId === "") {
              mvno.ui.alert("수정할 파일을 선택해주세요.");
              return;
            }

            mvno.cmn.ajax(ROOT + "/org/bpaMgmt/updateDataTaskFile.do", form, function() {
              mvno.ui.notify("NCMN0004"); // 수정되었습니다.
              PAGE.GRID4.refresh();
              form.clear();
              form.setItemValue("bpaId", PAGE.POPUP1_FORM.getItemValue("bpaId"));
              PAGE.GRID2.refresh();
            });

            break;
        }
      },
      onValidateMore: function(data){
        var form = this;

        if(data.fileNm === ""){
          this.pushError("fileNm", "파일명", "파일명을 입력해주세요.");
        }

        if(data.batchJobId === ""){
          this.pushError("batchJobId", "배치명", "배치명을 선택하세요.");
        }
      }
    }

    // ===================== 엑셀업로드 리스트 GRID (POPUP2TOP) =====================
    , POPUP2TOP: {
      css : { height : "270px" }
      ,header : "서비스계약번호"
      ,columnIds : "svcCntrNo"
      ,widths : "390"
      ,colAlign : "center"
      ,colTypes : "ro"
    }

    // ===================== 엑셀업로드 파일업로드 FORM (POPUP2MID) =====================
    , POPUP2MID: {
      title : "엑셀업로드"
      ,items : [
        {type : "block", list : [
          {type:"upload", label:"정산데이터 엑셀파일", name:"file_upload1", offsetLeft: 10, inputWidth:350, url:"/rcp/dlvyMgmt/regParExcelUp.do", userdata: {limitSize:Number(bpaTargetCnt)}, autoStart:true}
        ]},
      ]
      ,buttons : {
        right : {
          btnSave : { label : "저장" },
          btnClose1 : { label : "닫기" }
        }
        ,left : {
          btnInit : { label : "초기화" }
        }
      }
      ,onUploadFile : function(realName, serverName) {
        fileName = serverName;
      }
      ,onUploadComplete : function(count){
        var userOptions = {timeout:180000};

        // 엑셀 업로드 양식 읽기
        mvno.cmn.ajax(ROOT + "/org/bpaMgmt/getDataTaskExcelTempList.json", { uploadFileNm : fileName }, function(resultData) {
            var rData = resultData.result.data;
            PAGE.POPUP2TOP.clearAll();
            PAGE.POPUP2TOP.parse(rData.rows, "js");
          }, userOptions);
      }
      ,onButtonClick : function(btnId) {
        var form = this; // 혼란방지용으로 미리 설정 (권고)

        switch (btnId) {

          case "btnSave" :
            if(PAGE.POPUP2TOP.getRowsNum() === 0) {
              mvno.ui.alert("파일을 첨부해 주세요.");
              break;
            }

            var selectedGrid1 = PAGE.GRID1.getSelectedRowData();
            var arrObj = [];

            PAGE.POPUP2TOP.forEachRow(function(id){
              var row = PAGE.POPUP2TOP.getRowData(id);
              row["bpaId"] = selectedGrid1.bpaId;
              row["uploadFileNm"] = fileName;
              arrObj.push(row);
            });

            if(arrObj.length > bpaTargetCnt){
              mvno.ui.alert("엑셀업로드는 " + bpaTargetCnt + "건 이하로 등록하세요.");
              return;
            }

            var userOptions = {timeout:180000};

            // 업로드한 엑셀 양식 내 데이터 저장
            mvno.cmn.ajax4json(ROOT + "regDataTaskExcelTempComplete.json", { items : arrObj }, function(result) {
              mvno.ui.closeWindowById("POPUP2", true);
              mvno.ui.notify("NCMN0004"); // 저장되었습니다.
            }, userOptions);

            break;

          case "btnInit":
            if(PAGE.POPUP2TOP.getRowsNum() < 0) {
              mvno.ui.alert("등록된 데이터가 없습니다.");
              return;
            }

            // 초기화 버튼 클릭 시 bpaId에 해당하는 모든 계약번호 사용여부 N
            mvno.cmn.ajax(ROOT + "/org/bpaMgmt/getDataExcelListUseYn.do", { bpaId : PAGE.GRID1.getSelectedRowData().bpaId }, function(result) {
              mvno.ui.notify("NCMN0002"); // 수정되었습니다.
              PAGE.POPUP2TOP.refresh();
            });

            break;

          case "btnClose1":
            PAGE.POPUP2MID.getUploader("file_upload1").reset();
            mvno.ui.closeWindowById("POPUP2");
            break;
        }
      }
    }

  };

  var PAGE = {
    title: "${breadCrumb.title}",
    breadcrumb: "${breadCrumb.breadCrumb}",
    buttonAuth: ${buttonAuth.jsonString},
    onOpen: function() {

      mvno.ui.createForm("FORM1"); // 검색조건
      mvno.ui.createGrid("GRID1"); // 조회결과
      mvno.ui.createForm("FORM2"); // description
      mvno.ui.createGrid("GRID2"); // 파일목록

      // 기간유형
      mvno.ui.createForm("FORM3");
      $("#FORM3").hide();
      mvno.ui.createForm("FORM4");
      $("#FORM4").hide();

      // 페이지 진입 시, 버튼 숨기기
      mvno.ui.hideButton("GRID2", "btnExcelUpload");
      mvno.ui.hideButton("GRID2", "btnExcelUpleadTempDn");
      mvno.ui.hideButton("GRID2", "btnDnExcel");

      // admin / DEV 권한일 경우에 버튼 노출 (업무등록/업무수정/파일관리)
      if (isAdminDev !== "Y") {
        mvno.ui.hideButton("FORM2", "btnReg");
        mvno.ui.hideButton("FORM2", "btnMod");
        mvno.ui.hideButton("FORM2", "btnFile");
      }

      // 업무 selectBox >> 검색조건 세팅 (공통코드)
      mvno.cmn.ajax4lov(ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"BPA0001", orderBy: "etc1"}, PAGE.FORM1, "bpaId");

      // bpaTargetCnt 엑셀 업로드 가능 갯수 (공통코드)
      mvno.cmn.ajax(ROOT + "/org/grpcdmgmt/listGrpCdMgmt.json", {grpId:"DEV0003"}, function(resultData) {
        var cdList = resultData.result.data.rows;
        for (var i = 0; i < cdList.length; i++) {
          if (cdList[i].cdVal === "BPA_TARGET_CNT") {
            bpaTargetCnt = cdList[i].etc1 || 1000;
            break;
          }
        }
      });
    }
  };
</script>