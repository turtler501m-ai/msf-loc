<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : noScanOpenMgmt.jsp
   * @Description : 무서식지 개통 관리
   * @
   * @ 수정일	    수정자 수정내용
   * @ ---------- ------ -----------------------------
   * @ 2024.05.07       최초생성
   * @Create Date : 2024.05.07.
   */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div> <!-- FS3 -->
<div id="FORM2" class="section-search"></div>
<div id="GRID2" class="section-full"></div> <!-- FS0 -->
<div id="FORM3" class="section-box"></div>

<!-- Script -->
<script type="text/javascript">

  var typeCd = "${loginInfo.userOrgnTypeCd}";
  var orgnId = "${loginInfo.userOrgnId}";
  var orgnNm = "${loginInfo.userOrgnNm}";
  var kosId  = "${kosId}";

  var today = new Date().format("yyyymmdd");
  var baseDt = mvno.cmn.getAddDay(today, -7);

  var DHX = {
    FORM1 : {
      items : [
        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
        {type:"block", list:[
            {type:"calendar", width:100, label:"등록일시", name:"retvStDt",  dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", offsetLeft:10, readonly:true},
            {type:"newcolumn"},
            {type:"calendar", width:100, label:"~", name:"retvFnsDt",   dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:10, offsetLeft:5,  readonly:true},
            {type:"newcolumn"},
            {type:"input", label:"KOS ID",  name:"oderTrtId", width:80,  value: kosId, offsetLeft:60, required: true},   // 조회처리자 (kosId)
            {type:"newcolumn"},
            {type:"input", label:"조회시작번호", name:"retvSeq", width:40, labelWidth:80,  offsetLeft:60, value: 0},
            {type:"newcolumn"},
            {type:"input", label:"조회건수", name:"retvCascnt", width:40,  offsetLeft:50, value: 30},
            {type:"newcolumn"}
          ]
        },
        {type : "block",
          list : [
            {type:"input", label:"접점", name:"oderTrtOrgId", width:100, offsetLeft:10, maxLength:10, readonly: true, required: true},
            {type:"newcolumn"},
            {type:"button", name:"btnOrgFind", value:"찾기"},
            {type:"button", name:"btnOrgFind2", value:"찾기"},
            {type:"newcolumn"},
            {type:"input", name:"oderTrtOrgNm", width:100, readonly:true},
            {type:"newcolumn"},
            {type:"select", name:"oderTypeCd", label:"업무구분", width:80, offsetLeft:25,  required: true},
            {type:"newcolumn"},
            {type:"input", width:100, name:"tlphNo", label:"전화번호", maxLength:11, width:100, offsetLeft:20, validate: "ValidNumeric"},
            {type:"newcolumn"}
          ]
        },
        {type:"newcolumn", offset:100},
        {type :"button", name :"btnSearch", value : "조회", className:"btn-search2"},
        {type:"hidden", width:60, label:"", name:"appEventCd"}  // 이벤트코드
      ],
      onValidateMore : function(data) {
        if (mvno.validator.dateCompare(data.retvStDt, data.retvFnsDt) > 30) this.pushError("data.retvStDt", "조회기간", "조회기간은 30일 이내만 가능합니다.");
        if (data.retvStDt > data.retvFnsDt) this.pushError("data.retvStDt", "조회기간", "조회시작일자가 종료일자보다 클 수 없습니다.");
        if (data.retvStDt > today) this.pushError("data.retvStDt", "조회시작일자", "오늘 이전 날짜만 허용됩니다");
        if (data.retvFnsDt > today) this.pushError("data.retvFnsDt", "조회종료일자", "오늘 이전 날짜만 허용됩니다");
      },
      onButtonClick : function(btnId) {

        var form = this;

        switch (btnId) {
          case "btnSearch":

            form.setItemValue("appEventCd", "FS3"); //서식지 목록 조회
            PAGE.GRID1.list("/rcp/rcpMgmt/osstNoScanCall.json", form);

            break;

          case "btnOrgFind": // 접점 찾기
            mvno.ui.codefinder("ORG", function(result) {
              form.setItemValue("oderTrtOrgId", result.orgnId);
              form.setItemValue("oderTrtOrgNm", result.orgnNm);
              PAGE.FORM2.setItemValue("cntpntCd", result.orgnId);
              PAGE.FORM2.setItemValue("cntpntNm", result.orgnNm);
            });

            break;

          case "btnOrgFind2": // 접점 찾기
            mvno.ui.codefinder("KNOTEORG", function(result) {
              form.setItemValue("oderTrtOrgId", result.orgnId);
              form.setItemValue("oderTrtOrgNm", result.orgnNm);
              PAGE.FORM2.setItemValue("cntpntCd", result.orgnId);
              PAGE.FORM2.setItemValue("cntpntNm", result.orgnNm);
            });

            break;
        }
      },
    },
    // ----------------------------------------
    GRID1 : {
      css : {
        height : "200px"
      },
      title : "무서식지 개통 목록 조회",
      header : "통합서식지ID,업무구분,업무구분코드,고객명,전화번호,계약번호,등록일시,KOS ID,noMask전화번호", // 9
      columnIds : "itgFrmpapSeq,oderTypeNm,oderTypeCd,custNm,tlphNo,svcContId,oderCretDt,sysTrtrId,noMaskTlphNo",  // 9
      colAlign : "center,center,center,center,center,center,center,center,center",  // 9
      colTypes : "ro,ro,ro,ro,ro,ro,roXdt,ro,ro",  // 9
      colSorting : "str,str,str,str,str,str,str,str,str",  // 9
      widths : "300,80,100,120,90,80,120,130,100",  // 9
      hiddenColumns : "2,8",
      paging: false,
      buttons : {
        right: { btnUse : {label : "사용하기"}}
      },
      onButtonClick : function(btnId) {
        var data = PAGE.GRID1.getSelectedRowData();
        var grid = this;

        switch (btnId) {
          case "btnUse" :

            if(data == null){                      //대상 Row 선택유무
              mvno.ui.alert("ECMN0002");
              return;
            }

            mvno.ui.confirm("해당 서식지를 사용하시겠습니까?", function () {
              PAGE.FORM3.setItemValue("itgFrmpapSeq", data.itgFrmpapSeq);
              PAGE.FORM3.setItemValue("oderTypeCd", data.oderTypeCd);
              PAGE.FORM3.setItemValue("oderTypeNm", data.oderTypeNm);
              PAGE.FORM3.setItemValue("tlphNo", data.noMaskTlphNo); //마스킹 X
              PAGE.FORM3.setItemValue("maskTlphNo", data.tlphNo); //마스킹 O
              PAGE.FORM3.setItemValue("oderTrtId", data.sysTrtrId);
              PAGE.FORM3.setItemValue("svcContId", data.svcContId);
            });

            break;
        }

      },
      onRowDblClicked : function(rowId, celInd) {
        this.callEvent('onButtonClick', ['btnUse']);
      },
    },
    //------------------------------------------------------------
    FORM2 : {
      items : [
        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0},
        {type:"block", list:[
            {type:"calendar", width:100, label:"등록일시", labelWidth:60, name:"retvStrtDt",  dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", offsetLeft:10, readonly:true},
            {type:"newcolumn"},
            {type:"calendar", width:100, label:"~", name:"retvEndDt",   dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:10, offsetLeft:5,  readonly:true},
            {type:"newcolumn"},
            {type:"input", label:"조회시작번호", name:"retvSeq", width:40, labelWidth:80,  offsetLeft:60, value: 0},
            {type:"newcolumn"},
            {type:"input", label:"조회건수", name:"retvCascnt", width:40,  offsetLeft:50, value: 30},
            {type:"newcolumn"}
          ]
        },
        {type : "block", list : [
            {type:"input", label:"접점", name:"cntpntCd", width:100, labelWidth:60, offsetLeft:10, maxLength:10, readonly: true, required: true},
            {type:"newcolumn"},
            {type:"button", name:"btnOrgFind", value:"찾기"},
            {type:"button", name:"btnOrgFind2", value:"찾기"},
            {type:"newcolumn"},
            {type:"input", name:"cntpntNm", width:100, readonly:true},
            {type:"newcolumn"},
            {type:"select", name:"svcApyTrtStatCd", label:"처리상태", width:80, offsetLeft:25},
            {type:"newcolumn"}
          ]
        },
        {type:"newcolumn", offset:100},
        {type :"button", name :"btnSearch", value : "조회", className:"btn-search2"},
        {type:"hidden", width:60, label:"", name:"appEventCd"},  // 이벤트코드
      ],
      onValidateMore : function(data) {
        if(mvno.validator.dateCompare(data.retvStrtDt, data.retvEndDt) > 15) this.pushError("data.retvStrtDt", "조회기간", "조회기간은 15일 이내만 가능합니다.");
        if(data.retvStrtDt > data.retvEndDt) this.pushError("data.retvStrtDt","조회기간","조회시작일자가 종료일자보다 클 수 없습니다.");
        if(data.retvStrtDt > today) this.pushError("data.retvStrtDt","조회시작일자","오늘 이전 날짜만 허용됩니다");
        if(data.retvEndDt > today) this.pushError("data.retvEndDt","조회종료일자","오늘 이전 날짜만 허용됩니다");
      },
      onButtonClick : function(btnId) {

        var form = this;

        switch (btnId) {
          case "btnSearch": //조회

            form.setItemValue("appEventCd", "FS0"); //서식지 목록 조회
            PAGE.GRID2.list("/rcp/rcpMgmt/osstKnoteCall.json", form);

            break;

          case "btnOrgFind": // 접점 찾기
            mvno.ui.codefinder("ORG", function(result) {
              form.setItemValue("cntpntCd", result.orgnId);
              form.setItemValue("cntpntNm", result.orgnNm);
              PAGE.FORM1.setItemValue("oderTrtOrgId", result.orgnId);
              PAGE.FORM1.setItemValue("oderTrtOrgNm", result.orgnNm);
            });

            break;

          case "btnOrgFind2": // 접점 찾기
            mvno.ui.codefinder("KNOTEORG", function(result) {
              form.setItemValue("cntpntCd", result.orgnId);
              form.setItemValue("cntpntNm", result.orgnNm);
              PAGE.FORM1.setItemValue("oderTrtOrgId", result.orgnId);
              PAGE.FORM1.setItemValue("oderTrtOrgNm", result.orgnNm);
            });

            break;
        }
      },
    },
    // ----------------------------------------
    // K-Note이력조회(FS0)
    GRID2 : {
      css : {
        height : "200px"
      },
      title : "K-Note이력조회",
      header : "신청서ID,업무구분,고객명,처리상태코드,처리상태,고객유형코드,고객유형,등록일시,대리점코드,접점코드,접점명", // 11
      columnIds : "frmpapId,operTypeNm,custNm,svcApyTrtStatCd,svcApyTrtStatNm,custTypeNm,custTypeNm2,wapplRegDate,mngmAgncId,cntpntCd,cntpntNm", // 11
      widths : "300,80,120,80,90,100,90,120,100,100,130", // 11
      colAlign : "center,center,center,center,center,center,center,center,center,center,center", // 11
      colTypes : "ro,ro,ro,ro,ro,ro,ro,roXdt,ro,ro,ro", // 11
      colSorting : "str,str,str,str,str,str,str,str,str,str,str", // 11
      paging : false,
      hiddenColumns: "3,5,8,9",
      buttons : {
        right: { btnUse : {label : "사용하기"}}
      },
      onButtonClick : function(btnId) {
        var data = PAGE.GRID2.getSelectedRowData();
        var grid = this;

        switch (btnId) {
          case "btnUse" :

            if(data == null){                      //대상 Row 선택유무
              mvno.ui.alert("ECMN0002");
              return;
            }

            // 3: 완료, 4: 취소
            if (data.svcApyTrtStatCd == 3 || data.svcApyTrtStatCd == 4) {
              mvno.ui.alert("처리상태가 완료 또는 취소된 서식지는 사용이 불가합니다.");
              return;
            }

            mvno.ui.confirm("해당 서식지를 사용하시겠습니까?", function () {
              PAGE.FORM3.setItemValue("frmpapId", data.frmpapId);
              PAGE.FORM3.setItemValue("scanDt", data.wapplRegDate.substring(0,8));
              PAGE.FORM3.setItemValue("oderTrtOrgId", data.cntpntCd);
            });

            break;
        }
      },
      onRowDblClicked: function (rowId, celInd) {
        this.callEvent('onButtonClick', ['btnUse']);
      },
    },
    // ----------------------------------------
    FORM3 : {
      title: "무서식지 오더 후첨부 처리",
      items : [
        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
        {type:"block", list:[
            {type:"input", label:"통합서식지ID", name:"itgFrmpapSeq", width:210, offsetLeft:20, labelWidth:100, readonly: true, required: true},
            {type:"newcolumn"},
            {type:"input", label:"서식지ID", name:"frmpapId", width:416, offsetLeft:40, labelWidth:80, readonly: true, required: true},
            {type:"newcolumn"}
          ]},
        {type:"block", list:[
            {type:"input", label:"업무구분", name:"oderTypeNm", width:100, offsetLeft:20, labelWidth:100, readonly: true, required: true},
            {type:"newcolumn"},
            {type:"input", label:"전화번호", name: "maskTlphNo", width:100, offsetLeft:150, labelWidth:80, readonly: true, required: true}, // 마스킹 O
            {type:"newcolumn"},
            {type:"input", label:"스캔일자", name:"scanDt", width:100, offsetLeft:120, labelWidth:90, readonly: true, required: true},
            {type:"newcolumn"}
          ]},
        {type:"block", list:[
            {type:"input", label:"접점", name:"oderTrtOrgId", width:100, offsetLeft:20, labelWidth:100, readonly: true, required: true},
            {type:"newcolumn"},
            {type:"input", label:"KOS ID", name: "oderTrtId", width:100, offsetLeft:150, labelWidth:80, readonly: true, required: true},
            {type:"newcolumn"},
            {type:"input", label:"계약번호", name: "svcContId", width:100, offsetLeft:120, labelWidth:90, readonly: true, required: true},
            {type:"newcolumn"}
          ]},
        {type:"hidden", width:60, label:"", name:"appEventCd"},  // 이벤트코드
        {type:"hidden", width:60, label:"", name:"tlphNo"},      // 마스킹 X 전화번호 (FS4호출)
        {type:"hidden", width:60, label:"", name:"oderTypeCd"}   // 업무구분코드
      ],
      buttons : {
        right: { btnSend : {label : "전송하기"}}
      },
      onButtonClick : function(btnId) {

        var form = this;

        switch (btnId) {
          case "btnSend": // FS4 전송

            mvno.ui.confirm("선택한 데이터를 전송하시겠습니까?", function () {
                form.setItemValue("appEventCd", "FS4"); //서식지 목록 조회
                mvno.cmn.ajax(ROOT + "/rcp/rcpMgmt/osstNoScanCall.json", form, function (resultData) {
                    mvno.ui.alert(resultData.result.msg);
                    if(resultData.result.code == "OK") {
                        PAGE.FORM1.callEvent('onButtonClick', ['btnSearch']);
                        PAGE.FORM2.callEvent('onButtonClick', ['btnSearch']);
                    }
                });
            });

            break;
        }
      }
    }
  } // end dhx


  var PAGE = {
    title : "${breadCrumb.title}",
    breadcrumb : " ${breadCrumb.breadCrumb}",
    buttonAuth: ${buttonAuth.jsonString},

    onOpen : function() {
      mvno.ui.createForm("FORM1");
      mvno.ui.createGrid("GRID1");
      mvno.ui.createForm("FORM2");
      mvno.ui.createGrid("GRID2");
      mvno.ui.createForm("FORM3");

      // 조회시작번호 hide 처리
      PAGE.FORM1.hideItem("retvSeq");
      PAGE.FORM2.hideItem("retvSeq");
      // 조회건수 hide처리
      PAGE.FORM1.hideItem("retvCascnt");
      PAGE.FORM2.hideItem("retvCascnt");

      // 사용하기 버튼 hide 처리
      mvno.ui.hideButton("GRID1", "btnUse");
      mvno.ui.hideButton("GRID2", "btnUse");

      //날짜 세팅
      PAGE.FORM1.setItemValue("retvStDt", baseDt);
      PAGE.FORM1.setItemValue("retvFnsDt", today);
      PAGE.FORM2.setItemValue("retvStrtDt", baseDt);
      PAGE.FORM2.setItemValue("retvEndDt", today);

      if (typeCd != '10'){ // 대리점인 경우
        PAGE.FORM1.hideItem("btnOrgFind");
        PAGE.FORM1.hideItem("oderTrtId")  //KOSID 본사만 노출
        PAGE.FORM2.hideItem("btnOrgFind");

      } else {
        PAGE.FORM1.hideItem("btnOrgFind2");
        PAGE.FORM2.hideItem("btnOrgFind2");
      }

      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0082", orderBy:"etc1"}, PAGE.FORM1, "oderTypeCd" );  //업무유형
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0272"}, PAGE.FORM2, "svcApyTrtStatCd"); // 처리상태
    }
  };

</script>