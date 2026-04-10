<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : statsDisAddChk.jsp
   * @Description : 평생할인 부가서비스 가입 검증 화면
   * @
   * @ 수정일	    수정자 수정내용
   * @ ---------- ------ -----------------------------
   * @ 2025.07.08      최초생성
   * @Create Date : 2025.07.08
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
            {type : "calendar",width : 100,label : "검증일자",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",  value:"${srchStrtDt}",name : "srchStrtDt", offsetLeft:10, readonly:true},
            {type : "newcolumn"},
            {type : "calendar",label : "~",name : "srchEndDt",labelAlign : "center",dateFormat : "%Y-%m-%d",  value:"${srchEndDt}",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true},
            {type : "newcolumn"},
            {type:"select", name:"chkRslt", label:"검증결과", width:130, offsetLeft:55},
            {type : "newcolumn"},
            {type:"select", name:"evntCd", label:"업무구분", width:130, offsetLeft:47}
          ]
        },
        {type : "block", list : [
            {type:"select", label:"검색구분", name:"searchGbn",  width:100, offsetLeft:10},
            {type:"newcolumn"},
            {type:"input", name:"searchName", width:130},
            {type : "newcolumn"},
            {type:"select", name:"compYn", label:"완료여부", width:130, offsetLeft:40},
            {type : "newcolumn"},
            {type:"select", name:"rtyYn", label:"재처리여부", width:130, offsetLeft:35}
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
            PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getDisAddChkList.json", form);
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
      }
    },
    // ----------------------------------------
    //프로모션 자동 가입 검증 목록
    GRID1 : {
      css : {
        height : "230px"
      },
      title : "조회결과",
      header : "검증일자,계약번호,청구번호,개통일자,업무구분,구매유형,적용일시,대리점,접점,최종요금제코드,최종요금제명,요금제 월정액,M전산 정책 요금제 할인금액,KT시스템 요금제 할인 금액,검증결과,재처리여부,재처리결과,재처리자,재처리일시,완료여부,완료메모,완료자,완료일시", //23
      columnIds : "chkDt,contractNum,ban,openDt,evntCd,reqBuyTypeNm,effectiveDate,agentNm,shopNm,lstRateCd,lstRateNm,lstRateAmt,disAmt,ktDisAmt,chkRslt,rtyYn,rtyRslt,rtyId,rtyDate,compYn,compMemo,compId,compDate",  //23
      colAlign : "center,center,center,center,center,center,center,left,left,center,left,right,right,right,left,center,left,center,center,center,left,center,center",  //23
      colTypes : "roXd,ro,ro,roXd,ro,roXdt,ro,ro,ro,ro,ro,ron,ron,ron,ro,ro,ro,ro,roXdt,ro,ro,ro,roXdt",  //23
      colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",  //23
      widths : "90,90,90,90,90,150,150,150,150,100,220,100,180,180,150,90,200,100,150,90,200,100,150",  //23
      // hiddenColumns : [0],
      paging: true,
      pageSize:20,
      showPagingAreaOnInit: true,
      buttons : {
        right : {
          btnRty : { label : "재처리" },
          btnMod : { label : "완료처리" }
        },
        hright : {
          btnExcel : { label : "엑셀다운로드" }
        }
      },
      onRowDblClicked : function(rowId, celInd) {
        this.callEvent('onButtonClick', ['btnDtl']);
      },
      onRowSelect:function(rId, cId){
        var grid = this;
        var sdata = grid.getSelectedRowData();
        var data = {
          contractNum : sdata.contractNum
        }
        PAGE.GRID2.list(ROOT + "/stats/statsMgmt/getDisAddList.json", data, {
          callback: gridOnLoad
        });
      },
      onButtonClick : function(btnId, selectedData) {

        switch (btnId) {
          case "btnDtl":
            var grid = this;
            var sdata = grid.getSelectedRowData();
            // 개통관리 화면 호출
            var url = "/rcp/openMgmt/getOpenMgmtListNew.do";
            var menuId = "MSP1000098";

            var param = "?menuId=" + menuId + "&trgtMenuId=MSP1010057&prmContNum=" + sdata.contractNum;

            var myTabbar = window.parent.mainTabbar;

            if (myTabbar.tabs(url)) {
              myTabbar.tabs(url).setActive();
            }else{
              myTabbar.addTab(url, "개통관리", null, null, true);
            }

            myTabbar.tabs(url).attachURL(url + param);

            break;

          case "btnRty":
            var grid = this;
            var sdata = grid.getSelectedRowData();

            if(sdata == null){
              mvno.ui.alert("선택된 데이터가 없습니다.<br> 데이터를 선택하고 처리해주시기 바랍니다.");
              return;
            }

            if(sdata.compYn == "Y"){
              mvno.ui.alert("완료된 건은 재처리 할수 없습니다.");
              return;
            }

            var url = "/stats/statsMgmt/disAddRty.json";

            var params = {
              "chkDt" : sdata.chkDt
              ,"contractNum" : sdata.contractNum
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
            }, {errorCallback : function() {
                PAGE.GRID1.refresh();
                PAGE.GRID2.clearAll();
              }
            });

            break;

          case "btnMod":
            var grid = this;
            var sdata = grid.getSelectedRowData();

            if(sdata == null){
              mvno.ui.alert("선택된 데이터가 없습니다.<br> 데이터를 선택하고 처리해주시기 바랍니다.");
              return;
            }

            if(sdata.compYn == "Y"){
              mvno.ui.alert("이미 완료된 건입니다.");
              return;
            }

            mvno.ui.prompt("완료메모", function(result) {
              var url = "/stats/statsMgmt/updateDisAddChkComp.json";

              var params = {
                "chkDt" : sdata.chkDt
                ,"contractNum" : sdata.contractNum
                ,"compYn" : "Y"
                ,"compMemo" : result
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
              }, {errorCallback : function() {
                  PAGE.GRID1.refresh();
                  PAGE.GRID2.clearAll();
                }
              });
            }, {lines:10, maxLength:3000});

            break;

          case "btnExcel" :

            if(PAGE.GRID1.getRowsNum() == 0){
              mvno.ui.alert("데이터가 존재하지 않습니다");
              return;
            }

            var searchData =  PAGE.FORM1.getFormData(true);
            mvno.cmn.download(ROOT + "/stats/statsMgmt/getDisAddChkListExcel.json?menuId=MSP1010057", true, {postData:searchData});
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
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005", orderBy:"etc1" }, PAGE.FORM1, "compYn" ); // 완료여부
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMS0005", orderBy:"etc1" }, PAGE.FORM1, "rtyYn" ); // 재처리여부
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9052", orderBy:"etc1"}, PAGE.FORM1, "evntCd" );  //업무구분
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9053", orderBy:"etc1"}, PAGE.FORM1, "searchGbn" );  //검색구분
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0097", orderBy:"etc1"}, PAGE.FORM1, "chkRslt" );  //검증결과
      PAGE.FORM1.disableItem("searchName");
    }
  };

  function gridOnLoad(){
    PAGE.GRID2.forEachRow(function(id){
      if (PAGE.GRID2.getRowData(id).status == '만료') {
        PAGE.GRID2.setRowTextStyle(id,"color: red");
      }
    });
  }

</script>