<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : statsDisPrmtTrgMgmt.jsp
   * @Description : 평생할인 프로모션 적용 대상 조회
   * @
   * @ 수정일	    수정자 수정내용
   * @ ---------- ------ -----------------------------
   * @ 2023.09.27       최초생성
   * @Create Date : 2023.10.25.
   */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div><!-- 메인 -->
<div id="GRID2" class="section-full"></div><!-- 상세 -->

<div id="POPUP1">
  <div id="FORM2" class="section-search"></div>
  <div id="GRID3" class="section-full"></div>
</div>
<!-- Script -->
<script type="text/javascript">

  var DHX = {
    //------------------------------------------------------------
    FORM1 : {
      items : [
        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
        {type:"block", list:[
            {type:"select", label:"조회 기간", name:"searchGbn",  width:110, offsetLeft:10,
              options:[{value:"1", text:"등록일시", selected:true},{value:"2", text:"이벤트적용일시"}]},
            {type:"newcolumn"},
            {type:"calendar",width:100, name:"srchStrtDt", value:"${srchStrtDt}", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", offsetLeft:10},
            {type:"newcolumn"},
            {type:"calendar", label:"~", name:"srchEndDt", value:"${srchEndDt}", labelAlign:"center", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:10, offsetLeft:5, width:100, readonly:true},
            {type:"newcolumn"},
            {type:"checkbox", name:"lateChk", label:"지연여부", value:"Y", offsetLeft: 10, checked:false, width:100},
            {type:"newcolumn"},
            {type:"input", width:100, label:"계약번호", name:"contractNum", maxLength:10, width:100, offsetLeft:30}
          ]
        },
        {type:"block",
          list:[
            {type:"select", label:"업무구분", name:"evntCd", width:110, offsetLeft:10},
            {type:"newcolumn"},
            {type:"select", label: "처리여부", name:"procYn", width:100, offsetLeft:76,
              options:[{value:"", text:"- 전체 -", selected:true},{value:"Y", text:"Y"},{value:"T", text:"T"},{value:"N", text:"N"}]}
          ]
        },
        {type:"newcolumn", offset:150},
        {type:"button", name:"btnSearch", value:"조회", className:"btn-search2"},
      ],

      onChange : function(name, value) {
        var form = this;

        switch (name) {
          case "lateChk" :
            if (form.isItemChecked(name)) {
              form.setItemValue("searchGbn", "1");
              form.disableItem("searchGbn");
            }else{
              form.enableItem("searchGbn");
            }
        }
      },

      onValidateMore : function(data) {

        if(data.srchStrtDt == "" || data.srchEndDt == ""){
          this.pushError("srchStrtDt", "조회기간", "조회기간을 입력하세요.");
          return;
        }

        if (data.srchStrtDt > data.srchEndDt) {
          this.pushError("srchStrtDt", "조회기간", "시작일자가 종료일자보다 클 수 없습니다.");
        }

      },
      onButtonClick : function(btnId) {

        var form = this;

        switch (btnId) {
          case "btnSearch":

            PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getDisPrmtTrgMgmtList.json", form);
            PAGE.GRID2.clearAll();
            break;
        }
      }
    },
    // ----------------------------------------
    //사용자리스트
    GRID1 : {
      css : {
        height : "240px"
      },
      title : "조회결과",
      header : "마스터시퀀스,상세시퀀스,등록일자,처리여부,처리자ID, 처리일시, 계약번호,업무구분,처리번호,전문발생일시,이벤트적용일시,등록자ID, 등록일시",
      columnIds : "trgMstSeq,trgDtlSeq,regstDt,procYn,procId,procDttm,contractNum,evntCd,evntTrtmNo,evntTrtmDt,effectiveDate,regstId,regstDttm",
      colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center",
      colTypes : "ro,ro,ro,ro,ro,roXdt,ro,ro,ro,roXdt,roXdt,ro,roXdt",
      colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str",
      widths : "150,150,90,60,90,130,100,70,100,130,130,80,130",
      paging: true,
      pageSize:20,
      buttons : {
        hright : {
          btnExcel : { label : "엑셀다운로드" }
        }
      },
      onRowSelect:function(rId, cId){
        var grid = this;

        var sdata = grid.getSelectedRowData();
        var data = {
          contractNum : sdata.contractNum,
          regstDt : sdata.regstDt
        }

        PAGE.GRID2.list(ROOT + "/stats/statsMgmt/getDisPrmtTrgMgmtDtl.json", data);
      },
      onButtonClick : function(btnId, selectedData) {
        switch (btnId) {

          case "btnExcel" :
            if(PAGE.GRID1.getRowsNum() == 0){
              mvno.ui.alert("데이터가 존재하지 않습니다");
              return;
            }
            var searchData =  PAGE.FORM1.getFormData(true);
            mvno.cmn.download(ROOT + "/stats/statsMgmt/getDisPrmtTrgMgmtListExcel.json?menuId=MSP1010048", true, {postData:searchData});
            break;
        }
      }
    },
    GRID2 : {
      css : {
        height : "250px"
      },
      title : "업무구분별 평생할인 정책 적용 대상 상세 이력",
      header : "상세시퀀스,등록일자,처리여부, 처리자ID,처리일시,계약번호,업무구분,처리번호,전문발생일시,이벤트적용일시,등록자ID,등록일시",
      columnIds : "trgDtlSeq,regstDt,procYn,procId,procDttm,contractNum,evntCd,evntTrtmNo,evntTrtmDt,effectiveDate,regstId,regstDttm",
      colAlign : "center,center,center,center,center,center,center,center,center,center,center,center",
      colTypes : "ro,roXdt,ro,ro,roXdt,ro,ro,ro,roXdt,roXdt,ro,roXdt",
      colSorting : "str,str,str,str,str,str,str,str,str,str,str,str",
      widths : "150,130,60,70,130,90,70,90,130,130,90,130",
      paging : true,
      pageSize : 20,
      buttons : {
        hright : {
          btnPop : { label : "상세 이력 화면 보기" }
        }
      },
      onButtonClick : function(btnId, data) {
        switch (btnId) {
          case "btnPop":

            mvno.ui.createForm("FORM2");
            mvno.ui.createGrid("GRID3");
            PAGE.FORM2.clear();
            PAGE.FORM2.setItemValue("srchStrtDt", "${srchStrtDt}");
            PAGE.FORM2.setItemValue("srchEndDt", "${srchEndDt}");
            PAGE.GRID3.clearAll();

            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0091" ,orderBy:"etc3" }, PAGE.FORM2, "evntCd" ); //업무유형

            mvno.ui.popupWindowById("POPUP1", "상세 이력 팝업", 900, 700);
            break;
        }
      }
    },
    FORM2 : {
      items : [
        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
        {type:"block", list:[
            {type:"select", label:"조회 기간", name:"searchGbn", width:110, offsetLeft:10,
              options:[{value:"1", text:"등록일시", selected:true},{value:"2", text:"이벤트적용일시"}]},
            {type:"newcolumn"},
            {type:"calendar", width:100, name:"srchStrtDt", value:"${srchStrtDt}", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", offsetLeft:10},
            {type:"newcolumn"},
            {type:"calendar", label:"~", name:"srchEndDt", value:"${srchEndDt}", labelAlign:"center", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:10, offsetLeft:5, width:100, readonly:true},
            {type:"newcolumn"},
            {type:"input", width:100, label:"계약번호", name:"contractNum", maxLength:10, width:100, offsetLeft:30}
          ]
        },
        {type:"block",
          list:[
            {type:"select", label:"업무구분", width:110, offsetLeft:10, name:"evntCd"},
            {type:"newcolumn"},
            {type:"select", label: "처리여부", name:"procYn", width:100, offsetLeft:76,
              options:[{value:"", text:"- 전체 -", selected:true},{value:"Y", text:"Y"},{value:"T", text:"T"},{value:"N", text:"N"}]}
          ]
        },
        {type:"newcolumn", offset:150},
        {type:"button", value:"조회", name:"btnSearch", className:"btn-search2"},
      ],
      onValidateMore : function(data) {

        if(data.srchStrtDt == "" || data.srchEndDt == ""){
          this.pushError("srchStrtDt", "조회기간", "조회기간을 입력하세요.");
          return;
        }

        if (data.srchStrtDt > data.srchEndDt) {
          this.pushError("srchStrtDt", "조회기간", "시작일자가 종료일자보다 클 수 없습니다.");
        }
      },
      onButtonClick : function(btnId) {

        var form = this;

        switch (btnId) {
          case "btnSearch":
            PAGE.GRID3.list(ROOT + "/stats/statsMgmt/getDisPrmtTrgMgmtDtlPop.json", form);
            break;
        }
      }
    },
    GRID3 : {
      css : {
        height : "480px"
      },
      title : "업무구분별 평생할인 정책 적용 대상 상세 이력",
      header : "상세시퀀스,등록일자,처리여부,처리자ID,처리일시,계약번호,업무구분,처리번호,전문발생일시,이벤트적용일시,등록자ID,등록일시",
      columnIds : "trgDtlSeq,regstDt,procYn,procId,procDttm,contractNum,evntCd,evntTrtmNo,evntTrtmDt,effectiveDate,regstId,regstDttm",
      colAlign : "center,center,center,center,center,center,center,center,center,center,center,center",
      colTypes : "ro,ro,ro,ro,roXdt,ro,ro,ro,roXdt,roXdt,ro,roXdt",
      colSorting : "str,str,str,str,str,str,str,str,str,str,str,str",
      widths : "150,100,60,70,130,90,70,90,130,130,90,130",
      paging: true,
      pageSize:20,
      buttons : {
        hright : {
          btnExcel : { label : "엑셀다운로드" }
        }
      },
      onButtonClick : function(btnId, data) {
        switch (btnId) {
          case "btnExcel" :
            if(PAGE.GRID3.getRowsNum() == 0){
              mvno.ui.alert("데이터가 존재하지 않습니다");
              return;
            }
            var searchData =  PAGE.FORM2.getFormData(true);
            mvno.cmn.download(ROOT + "/stats/statsMgmt/getDisPrmtTrgMgmtDtlPopExcel.json?menuId=MSP1010048", true, {postData:searchData});
            break;
        }
      }
    }
  };

  var PAGE = {
    title : "${breadCrumb.title}",
    breadcrumb : " ${breadCrumb.breadCrumb}",
    buttonAuth : ${buttonAuth.jsonString},

    onOpen : function() {
      mvno.ui.createForm("FORM1");
      mvno.ui.createGrid("GRID1");
      mvno.ui.createGrid("GRID2");
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0091" ,orderBy:"etc3" }, PAGE.FORM1, "evntCd" ); //업무유형
    }
  };

</script>