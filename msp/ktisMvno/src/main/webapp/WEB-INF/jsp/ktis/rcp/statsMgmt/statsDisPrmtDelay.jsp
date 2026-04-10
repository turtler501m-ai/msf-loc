<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : statsDisPrmtDelay.jsp
   * @Description : 평생할인 프로모션 지연 현황
   * @
   * @ 수정일	    수정자 수정내용
   * @ ---------- ------ -----------------------------
   * @ 2024.01.31       최초생성
   * @Create Date : 2024.01.31
   */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div> <!-- 메인 -->
<!-- Script -->
<script type="text/javascript">

  var DHX = {
    //------------------------------------------------------------
    FORM1 : {
      items : [
        {type:"settings", position:"label-left", labelAlign:"left", blockOffset:0},
        {type:"block", list:[
            {type:"calendar", width:100, label:"적용 일시", name:"srchStrtDt", value:"${srchStrtDt}", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", offsetLeft:15, readonly:true},
            {type:"newcolumn"},
            {type:"select", name:"evntCd", label:"업무구분", width:100, offsetLeft:175}
          ]
        },
        {type : "block", list : [
            {type:"newcolumn"},
            {type:"input", label:"대리점", name:"cntpntShopId", width:100, maxLength:10, offsetLeft:15, labelWidth:52},
            {type:"newcolumn"},
            {type:"button", name:"btnOrgFind", value:"찾기"},
            {type:"newcolumn"},
            {type:"input", name:"cntpntShopNm", width:100, readonly:true},
            {type:"newcolumn"},
            {type:"select", label:"검색구분", name:"searchGbn",  width:100, offsetLeft:20,
              options:[{value:"", text:"- 전체 -", selected:true}, {value:"1", text:"고객명"},{value:"2", text:"계약번호"},{value:"3", text:"휴대폰번호"}]},
            {type:"newcolumn"},
            {type:"input", width:100, name:"searchName", width:130},
            {type:"newcolumn"},
            {type:"select", name:"procYn", label:"처리여부", width:100, offsetLeft:20}
          ]
        },
        {type:"newcolumn", offset:150},
        {type:"button", value:"조회", name:"btnSearch", className:"btn-search2"},
      ],
      onValidateMore : function(data) {

        if(data.srchStrtDt == ""){
          this.pushError("srchStrtDt", "조회기간", "조회기간을 입력하세요.");
          return;
        }

        if (data.srchStrtDt >= ${srchToday}) {
          this.pushError("srchStrtDt", "조회기간", "오늘보다 이전인 경우만 조회 가능합니다.");
        }

        if(data.searchGbn != "" && data.searchName.trim() == ""){
          this.pushError("searchName", "검색구분", "검색어를 입력하세요");
          PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
        }

      },
      onButtonClick : function(btnId) {
        var form = this;

        switch (btnId) {
          case "btnSearch":
            if(form.getItemValue("cntpntShopId") == null || form.getItemValue("cntpntShopId") == "") {
              form.setItemValue("cntpntShopNm","");
            }
            PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getDisPrmtDelayMst.json", form);
            PAGE.GRID2.clearAll();
            break;

          case "btnOrgFind":
            mvno.ui.codefinder("ORG", function(result) {
              form.setItemValue("cntpntShopId", result.orgnId);
              form.setItemValue("cntpntShopNm", result.orgnNm);
            });
            break;
        }
      },
      onChange : function(name, value) {
        // 검색구분
        if(name == "searchGbn") {
          PAGE.FORM1.setItemValue("searchName", "");
          if(value == null || value == "" ) PAGE.FORM1.disableItem("searchName");
          else PAGE.FORM1.enableItem("searchName");
        }
      }
    },
    // ----------------------------------------
    //프로모션 지연 리스트
    GRID1 : {
      css : {
        height : "230px"
      },
      title : "조회결과",
      header : "마스터 시퀀스,프로모션 적용 일시,프로모션명,고객명,계약번호,휴대폰번호,업무구분,수동 처리 여부,수동 처리 일시,프로모션 적용 대리점", //10
      columnIds : "prcMstSeq,effectiveDate,prmtNm,custNm,contractNum,subscriberNo,cdDsc,procYn,procDate,prmtAgnt",  // 10
      colAlign : "center,center,left,center,center,center,center,center,center,center",  // 10
      colTypes : "ro,roXdt,ro,ro,ro,ro,ro,ro,roXdt,ro",  // 10
      colSorting : "str,str,str,str,str,str,str,str,str,str",  // 10
      widths : "130,130,180,100,130,130,80,100,130,130",  // 10
      hiddenColumns : [0],
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
          prcMstSeq : sdata.prcMstSeq
        }
        PAGE.GRID2.list(ROOT + "/stats/statsMgmt/getDisPrmtDelayDtl.json", data, {
          callback: gridOnLoad
        });
      },
      onButtonClick : function(btnId, selectedData) {

        switch (btnId) {

          case "btnExcel" :

            if(PAGE.GRID1.getRowsNum() == 0){
              mvno.ui.alert("데이터가 존재하지 않습니다");
              return;
            }

            var searchData =  PAGE.FORM1.getFormData(true);
            mvno.cmn.download(ROOT + "/stats/statsMgmt/getDisPrmtDelayMstExcel.json?menuId=MSP1010049", true, {postData:searchData});
            break;
        }
      }
    },
  // ----------------------------------------
  //프로모션 지연 상세 리스트
    GRID2 : {
      css : {
        height : "230px"
      },
      title : "프로모션 지연 상세 이력",
      header : "dtl시퀀스,성공여부코드,지연일수,가입 일시,부가서비스,부가서비스명,성공여부,실패사유,처리자 ID,처리자 이름",  // 10
      columnIds : "prcDtlSeq,succYn,delayDt,regstDttm,soc,socNm,succYnNm,failRsNm,procId,procNm",  // 10
      colAlign : "center,center,center,center,center,left,center,left,center,center",  // 10
      colTypes : "ro,ro,ro,roXdt,ro,ro,ro,ro,ro,ro",  // 10
      colSorting : "str,str,str,str,str,str,str,str,str,str", // 10
      widths : "150,130,100,130,100,250,80,350,100,100", // 10
      hiddenColumns:[0,1]
    }
  };

  var PAGE = {
    title : "${breadCrumb.title}",
    breadcrumb : " ${breadCrumb.breadCrumb}",
    buttonAuth: ${buttonAuth.jsonString},

    onOpen : function() {
      mvno.ui.createForm("FORM1");
      mvno.ui.createGrid("GRID1");
      mvno.ui.createGrid("GRID2");
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005" ,orderBy:"etc1" }, PAGE.FORM1, "procYn" ); // 처리여부
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0091" ,orderBy:"etc3"}, PAGE.FORM1, "evntCd" );  //업무구분
      PAGE.FORM1.disableItem("searchName");

      var typeCd = "${loginInfo.userOrgnTypeCd}";
      var orgnId = "${loginInfo.userOrgnId}";
      var orgnNm = "${loginInfo.userOrgnNm}";

      if (typeCd != '10'){ // 대리점인 경우
        PAGE.FORM1.setItemValue("cntpntShopId", orgnId);
        PAGE.FORM1.setItemValue("cntpntShopNm", orgnNm);
        PAGE.FORM1.disableItem("cntpntShopId");
        PAGE.FORM1.disableItem("cntpntShopNm");
        PAGE.FORM1.disableItem("btnOrgFind");
      }
    }
  };

  //최종 부가서비스 가입 실패인 경우 빨간색 출력
  function gridOnLoad(){
    PAGE.GRID2.forEachRow(function(id){
      if(PAGE.GRID2.cellById(id,2).getValue() != "0일"){
          PAGE.GRID2.setRowTextStyle(id,"color: red");
      }
    });
  };

</script>