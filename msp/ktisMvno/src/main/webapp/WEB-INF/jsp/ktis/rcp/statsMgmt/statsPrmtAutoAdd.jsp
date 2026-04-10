<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : statsPrmtAutoAdd.jsp
   * @Description : 평생할인 프로모션 현황
   * @
   * @ 수정일	    수정자 수정내용
   * @ ---------- ------ -----------------------------
   * @ 2023.09.27       최초생성
   * @Create Date : 2023.09.27.
   */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div> <!-- 메인 -->
<div id="GRID2" class="section-full"></div> <!-- 상세 -->
<!-- Script -->
<script type="text/javascript">

  var DHX = {
    //------------------------------------------------------------
    FORM1 : {
      items : [
        {type:"settings", position:"label-left", labelAlign:"left", labelWidth:55, blockOffset:0},
        {type:"block", list:[
            {type:"calendar", width:100, label:"조회기간", name:"srchStrtDt", value:"${srchStrtDt}", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", offsetLeft:10, readonly:true},
            {type:"newcolumn"},
            {type:"calendar", label:"~", name:"srchEndDt", value:"${srchEndDt}", labelAlign:"center", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:10, offsetLeft:5, width:100, readonly:true},
            {type:"newcolumn"},
            {type:"select", name:"succYn", label:"성공여부", width:100, offsetLeft:60},
            {type:"newcolumn"},
            {type:"select", name:"procYn", label:"처리여부", width:100, offsetLeft:30}
          ]
        },
        {type : "block",
          list : [
            {type:"input", label:"대리점", name:"cntpntShopId", width:100, offsetLeft:10, maxLength:10},
            {type:"newcolumn"},
            {type:"button", name:"btnOrgFind", value:"찾기"},
            {type:"newcolumn"},
            {type:"input", name:"cntpntShopNm", width:100, readonly:true},
            {type:"newcolumn"},
            {type:"select", name:"evntCd", label:"업무구분", width:100, offsetLeft:25},
            {type:"newcolumn"},
            {type:"input", width:100, name:"contractNum", label:"계약번호", maxLength:10, width:100, offsetLeft:30},
            {type:"newcolumn"}
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
            if(form.getItemValue("cntpntShopId") == null || form.getItemValue("cntpntShopId") == "") {
              form.setItemValue("cntpntShopNm","");
            }

            PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getPrmtAutoAddList.json", form);
            PAGE.GRID2.clearAll();
            break;

          case "btnOrgFind":
            mvno.ui.codefinder("ORG", function(result) {
              form.setItemValue("cntpntShopId", result.orgnId);
              form.setItemValue("cntpntShopNm", result.orgnNm);
            });
            break;
        }
      }
    },
    // ----------------------------------------
    //프로모션 가입이력 리스트
    GRID1 : {
      css : {
        height : "200px"
      },
      title : "조회결과",
      header : "정책 적용 이력 시퀀스,최초 성공여부 코드,재처리 성공여부 코드,프로모션 가입 요청일,업무구분,프로모션명,최초 성공여부,재처리 성공여부,계약번호,고객명,처리여부,처리일시,처리자ID,처리자 이름,처리메모",  // 15
      columnIds : "prcMstSeq,succYn,fnlSuccYn,effectiveDate,cdDsc,prmtNm,succYnNm,fnlSuccYnNm,contractNum,custNm,procYn,procDate,procId,procNm,procMemo",  // 15
      colAlign : "center,center,center,center,center,left,center,center,center,center,center,center,center,center,left",  // 15
      colTypes : "ro,ro,ro,roXdt,ro,ro,ro,ro,ro,ro,ro,roXdt,ro,ro,ro",  // 15
      colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",  // 15
      widths : "100,100,100,160,90,180,90,100,120,90,100,160,100,90,250",  // 15
      hiddenColumns : [0,1,2],
      paging: true,
      pageSize:20,
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
          prcMstSeq : sdata.prcMstSeq
        }

        if(sdata.succYn == "Y" || sdata.fnlSuccYn == "Y" || sdata.procYn == "Y"){
          mvno.ui.disableButton("GRID1", "btnMod");
        }else {
          mvno.ui.enableButton("GRID1", "btnMod");
        }

        PAGE.GRID2.list(ROOT + "/stats/statsMgmt/getPrmtAutoAddDetail.json", data);
      },
      onButtonClick : function(btnId, selectedData) {
        var grid = this;
        switch (btnId) {
          case "btnMod":
            var sdata = grid.getSelectedRowData();

            if(sdata == null){
              mvno.ui.alert("선택된 데이터가 없습니다.<br> 데이터를 선택하고 처리해주시기 바랍니다.");
              return;
            }

            if(sdata.succYn == "Y" || sdata.fnlSuccYn == "Y"){
              mvno.ui.alert("프로모션 가입 성공한 건은 처리상태 변경을 할 수 없습니다.");
              return;
            }

            mvno.ui.prompt("처리메모", function(result) {
              var url = "/stats/statsMgmt/updatePrmtAutoAddProc.json";

              var params = {
                "prcMstSeq" : sdata.prcMstSeq
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
            mvno.cmn.download(ROOT + "/stats/statsMgmt/getPrmtAutoAddListExcel.json?menuId=MSP1010047", true, {postData:searchData});
            break;
        }
      }
    },
    // ----------------------------------------
    //프로모션 가입이력 상세 리스트
    GRID2 : {
      css : {
        height : "200px"
      },
      title : "프로모션처리이력",
      header : "프로모션처리이력시퀀스,프로모션 마스터 시퀀스,성공여부 코드,계약번호,가입 일자,부가서비스,부가서비스명,성공여부,실패사유,처리자 ID,처리자 이름",  // 11
      columnIds : "prcDtlSeq,prcMstSeq,succYn,contractNum,regstDttm,soc,socNm,succYnNm,failRsNm,procId,procNm",  // 11
      colAlign : "center,center,center,center,center,center,left,center,left,center,center",  // 11
      colTypes : "ro,ro,ro,ro,roXdt,ro,ro,ro,ro,ro,ro",  // 11
      colSorting : "str,str,str,str,str,str,str,str,str,str,str", // 11
      widths : "100,100,100,120,180,100,250,80,350,100,100", // 11
      hiddenColumns:[0,1,2],
      paging: true,
      pageSize:20,
      buttons : {
        right : {
          btnRty : { label : "재처리" }
        }
      },
      onRowSelect:function(rId, cId){
        var sData1 = PAGE.GRID1.getSelectedRowData();
        var sData2 = this.getSelectedRowData();

        var diableBtn = false;

        if(sData2.succYn == "0000") diableBtn= true;
        if(sData1 != null && (sData1.succYn == "Y" || sData1.fnlSuccYn == "Y" || sData1.procYn == "Y")) diableBtn= true;

        if(diableBtn)  mvno.ui.disableButton("GRID2", "btnRty");
        else  mvno.ui.enableButton("GRID2", "btnRty");
      },
      onButtonClick : function(btnId, data) {
        switch (btnId) {
          case "btnRty":
            var grid2 = this;
            var sdata2 = grid2.getSelectedRowData();

            if(sdata2 == null){
              mvno.ui.alert("선택된 데이터가 없습니다.<br> 데이터를 선택하고 처리해주시기 바랍니다.");
              return;
            }

            mvno.ui.confirm("재처리 이후 수정할 수 없습니다. <br> 재처리하시겠습니까?",function(){

              var data = {
                prcDtlSeq : sdata2.prcDtlSeq
                ,prcMstSeq : sdata2.prcMstSeq
              }

              mvno.cmn.ajax(ROOT + "/stats/statsMgmt/retryPrmtAutoAdd.json", data, function(resultData) {
                var rData = resultData.result;
                var alertMsg = "";

                if(rData.code == 'NOK'){
                  alertMsg = rData.msg
                }else{
                  if(rData.resultCd == "Y") alertMsg = "재처리가 완료 되었습니다.";
                  else alertMsg = "재처리 실패 했습니다." ;
                }
                mvno.ui.alert(alertMsg);
                PAGE.GRID2.refresh();
                if(rData.fnlSuccYN == "Y") PAGE.GRID1.refresh();

              },{async:false, dimmed:true});
            });

            break;
        }
      }
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
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9033" ,orderBy:"etc1" }, PAGE.FORM1, "succYn" ); // 성공여부
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0091" ,orderBy:"etc3"}, PAGE.FORM1, "evntCd" );  //업무유형

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

</script>