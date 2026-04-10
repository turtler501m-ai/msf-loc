<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : statsDisPrmtChk.jsp
   * @Description : 평생할인 프로모션 자동 가입 검증 화면
   * @
   * @ 수정일	    수정자 수정내용
   * @ ---------- ------ -----------------------------
   * @ 2024.01.31       최초생성
   * @Create Date : 2025.03.25
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
            {type : "calendar",width : 100,label : "조회기간",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",  value:"${srchStrtDt}",name : "srchStrtDt", offsetLeft:10, readonly:true},
            {type : "newcolumn"},
            {type : "calendar",label : "~",name : "srchEndDt",labelAlign : "center",dateFormat : "%Y-%m-%d",  value:"${srchEndDt}",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true},
            {type : "newcolumn"},
            {type:"select", name:"chkType", label:"검증유형", width:100, offsetLeft:55,
              options:[{value:"", text:"- 전체 -", selected:true}, {value:"DUP", text:"중복"},{value:"UNP", text:"미처리"}]},
            {type : "newcolumn"},
            {type:"select", name:"rsltCd", label:"미처리사유", width:100, offsetLeft:35, disabled:true}
          ]
        },
        {type : "block", list : [
            {type:"select", label:"검색구분", name:"searchGbn",  width:100, offsetLeft:10,
              options:[{value:"", text:"- 전체 -", selected:true}, {value:"custNm", text:"고객명"},{value:"contractNum", text:"계약번호"},{value:"subscriberNo", text:"휴대폰번호"}]},
            {type:"newcolumn"},
            {type:"input", width:100, name:"searchName", width:130},
            {type : "newcolumn"},
            {type:"select", name:"evntCd", label:"업무구분", width:100, offsetLeft:40},
            {type:"newcolumn"},
            {type:"select", name:"procYn", label:"처리여부", width:100, offsetLeft:35}
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

        if(data.searchGbn != "" && data.searchName.trim() == ""){
          this.pushError("searchName", "검색구분", "검색어를 입력하세요");
          PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
        }

      },
      onButtonClick : function(btnId) {
        var form = this;

        switch (btnId) {
          case "btnSearch":
            PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getDisPrmtChkList.json", form);
            PAGE.GRID2.clearAll();
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
        // 검증유형
        if(name == "chkType") {
          PAGE.FORM1.setItemValue("rsltCd", "");
          if(value == "UNP"){
            PAGE.FORM1.enableItem("rsltCd");
          }else{
            PAGE.FORM1.disableItem("rsltCd");
          }
        }
      }
    },
    // ----------------------------------------
    //프로모션 자동 가입 검증 목록
    GRID1 : {
      css : {
        height : "230px"
      },
      title : "조회결과",
      header : "시퀀스,프로모션 적용일시,검증유형,계약번호,업무구분,프로모션ID,프로모션명,실패사유,처리여부,처리메모,처리자,처리일시", //11
      columnIds : "regSeq,effectiveDate,chkType,contractNum,evntCd,prmtId,prmtNm,rsltDesc,procYn,procMemo,procId,procDate",  //11
      colAlign : "center,center,center,center,center,left,left,left,center,left,center,center",  //11
      colTypes : "ro,roXdt,ro,ro,ro,ro,ro,ro,ro,ro,ro,roXdt",  //11
      colSorting : "str,str,str,str,str,str,str,str,str,str,str,str",  //11
      widths : "0,130,80,80,80,130,200,200,80,200,80,130",  //11
      hiddenColumns : [0],
      paging: true,
      pageSize:20,
      showPagingAreaOnInit: true,
      buttons : {
        right : {
          btnMod : { label : "처리상태변경" }
        },
        hright : {
          btnExcel : { label : "엑셀다운로드" }
        }
      },
      onRowSelect:function(rId, cId){
        var grid = this;
        var sdata = grid.getSelectedRowData();
        var data = {
          CONTRACT_NUM : sdata.contractNum
        }
        PAGE.GRID2.list(ROOT + "/rcp/openMgmt/getFeatureHist.json", data, {
          callback: gridOnLoad
        });
      },
      onButtonClick : function(btnId, selectedData) {

        switch (btnId) {
          case "btnMod":
            var grid = this;
            var sdata = grid.getSelectedRowData();

            if(sdata == null){
              mvno.ui.alert("선택된 데이터가 없습니다.<br> 데이터를 선택하고 처리해주시기 바랍니다.");
              return;
            }

            // if(sdata.succYn == "Y" || sdata.fnlSuccYn == "Y"){
            //   mvno.ui.alert("프로모션 가입 성공한 건은 처리상태 변경을 할 수 없습니다.");
            //   return;
            // }

            mvno.ui.prompt("처리메모", function(result) {
              var url = "/stats/statsMgmt/updatePrmtChkProc.json";

              var params = {
                "regSeq" : sdata.regSeq
                ,"procMemo" : result
              };

              mvno.cmn.ajax(url, params, function(data) {
                if(data.result.code == "OK"){
                  mvno.ui.notify("NCMN0002");
                  mvno.ui.alert("처리가 완료되었습니다.");
                }else{
                  mvno.ui.alert(data.result.msg);
                }

                PAGE.GRID1.refresh();
                PAGE.GRID2.clearAll();
              });
            }, {lines:10, maxLength:3000});

            break;

          case "btnExcel" :

            if(PAGE.GRID1.getRowsNum() == 0){
              mvno.ui.alert("데이터가 존재하지 않습니다");
              return;
            }

            var searchData =  PAGE.FORM1.getFormData(true);
            mvno.cmn.download(ROOT + "/stats/statsMgmt/getDisPrmtChkListExcel.json?menuId=MSP1010054", true, {postData:searchData});
            break;
        }
      }
    },
  // ----------------------------------------
  //상품 변경 이력
    GRID2 : {
      css : {
        height : "230px"
      },
      header : "변경일시,상품명,상품유형,시작일시,종료일시,상태",
      columnIds : "evntTrtmDt,socNm,serviceTypeNm,effectiveDate,expirationDate,status",
      colAlign : "center,left,left,center,center,center",
      colTypes : "roXdt,ro,ro,roXdt,roXdt,ro",
      widths : "150,194,190,150,150,100",
      colSorting : "str,str,str,str,str,str",
      paging: true,
      pageSize:20
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
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0095"}, PAGE.FORM1, "rsltCd" );  //미처리사유
      PAGE.FORM1.disableItem("searchName");
    }
  };

  //해당 프로모션의 부가서비스들을 빨강으로 표시
  function gridOnLoad(){
    var prmtId = PAGE.GRID1.getSelectedRowData().prmtId;
    var prmtAdds = [];
    mvno.cmn.ajax(ROOT + "/stats/statsMgmt/getDisPrmtAddList.json", {prmtId:prmtId}, function(result){
      var adds = result.result.data;
      for (var i = 0; i < adds.length; i++) {
        prmtAdds.push(adds[i]);
      }
      PAGE.GRID2.forEachRow(function(id){
        if(prmtAdds.includes(PAGE.GRID2.getRowData(id).socCode)){
          if(PAGE.GRID2.getRowData(id).expirationDate == '99991231000000' || PAGE.GRID2.getRowData(id).expirationDate == '77771231000000'){
            PAGE.GRID2.setRowTextStyle(id,"color: red");
          }
        }
      });
    });
  }

</script>