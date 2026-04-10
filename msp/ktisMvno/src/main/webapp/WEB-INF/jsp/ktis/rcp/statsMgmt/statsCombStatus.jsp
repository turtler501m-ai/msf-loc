<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : statsCombStatus.jsp
   * @Description : 결합현황
   * @
   * @ 수정일	    수정자 수정내용
   * @ ---------- ------ -----------------------------
   * @ 2025.10.28      최초생성
   * @Create Date : 2025.10.28
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
            {type:"calendar",width : 100,label:"결합시작일",dateFormat : "%Y-%m-%d",serverDateFormat : "%Y%m%d",  value:"${srchStrtDt}",name : "srchStrtDt", offsetLeft:10, readonly:true, labelWidth: 70},
            {type:"newcolumn"},
            {type:"calendar",label : "~",name:"srchEndDt",labelAlign : "center",dateFormat : "%Y-%m-%d",  value:"${srchEndDt}",serverDateFormat : "%Y%m%d",labelWidth : 10,offsetLeft : 5,width : 100, readonly:true},
            {type:"newcolumn"},
            {type:"select", label:"검색구분", name:"searchGbn",  width:100, offsetLeft:45},
            {type:"newcolumn"},
            {type:"input", name:"searchName", width:157}
          ]
        },
        {type : "block", list : [
            {type:"input", label:"대리점", name: "openAgntCd",width:100, offsetLeft:10 ,labelWidth: 70},
            {type:"newcolumn"},
            {type:"button", value:"찾기", name:"btnOrgFind",disabled:false},
            {type:"newcolumn"},
            {type:"input", name: "openAgntNm", width:100},
            {type:"newcolumn"},
            {type:"select", name:"subStatus", label:"계약상태", width:100, offsetLeft:10},
            {type:"newcolumn"},
            {type:"select", name:"combSvcContSttusCd", label:"결합상태", width:100, offsetLeft:10},
            {type:"newcolumn"},
            {type:"select", name:"apiTarget", label:"연동결과", width:100, offsetLeft:10}
          ]
        },
        {type:"newcolumn", offset:150},
        {type : 'hidden', name: "DWNLD_RSN", value:""},
        {type:"button", value:"조회", name:"btnSearch", className:"btn-search2"},
      ],
      onValidateMore : function(data) {

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
            PAGE.GRID1.list(ROOT + "/stats/statsMgmt/selectCombStatusList.json", form);
            break;

          case "btnOrgFind":
            mvno.ui.codefinder("ORG", function(result) {
                  form.setItemValue("openAgntCd", result.orgnId);
                  form.setItemValue("openAgntNm", result.orgnNm);
            });
            break;
        }
      },
      onChange : function(name, value) {
        // 검색구분
        if(name == "searchGbn") {
          PAGE.FORM1.setItemValue("searchName", "");

          if(value == null || value == "" ) {

              var endDate = new Date().format('yyyymmdd');
              var time = new Date().getTime();
              time = time - 1000 * 3600 * 24 * 7;
              var startDate = new Date(time);

              PAGE.FORM1.enableItem("srchStrtDt");
              PAGE.FORM1.enableItem("srchEndDt");

              PAGE.FORM1.setItemValue("srchStrtDt", startDate);
              PAGE.FORM1.setItemValue("srchEndDt", endDate);
              PAGE.FORM1.disableItem("searchName");

          } else {
              PAGE.FORM1.setItemValue("srchStrtDt", "");
              PAGE.FORM1.setItemValue("srchEndDt", "");

              PAGE.FORM1.disableItem("srchStrtDt");
              PAGE.FORM1.disableItem("srchEndDt");

              PAGE.FORM1.enableItem("searchName");
          }
        }
      }
    },
    // ----------------------------------------
    //프로모션 자동 가입 검증 목록
    GRID1 : {
      css : {
        height : "550px"
      },
      title : "조회결과",
      header : "계약번호,고객명,휴대폰번호,요금제코드,요금제명,부가서비스코드,부가서비스명,가입일,상태,결합유형,결합시작일,결합종료일,결합상태,개통대리점,결합상품,KT구분,상품코드,상품명,서비스번호,(결합)시작일,(결합)만료예정일,(결합)약정기간,연동결과,연동메세지", //24
      columnIds : "contractNum,subLinkName,subscriberNo,pRateCd,pRateNm,rRateCd,rRateNm,lstComActvDate,subStatusNm,combTypeNm,combEfctStDt,combEfctFnsDt,combSvcContSttusNm,openAgntNm,combProdNm,svcContDivNm,prodCd,prodNm,svcNo,combEngtStDt,combEngtExpirDt,combEngtPerdMonsNum,apiTarget,prcsSbst", // 24
      colAlign : "center,left,center,left,left,left,left,center,center,center,center,center,center,left,left,center,left,left,left,center,center,center,center,left", //24
      colTypes : "ro,ro,ro,ro,ro,ro,ro,roXd,ro,ro,roXd,roXd,ro,ro,ro,ro,ro,ro,ro,roXd,roXd,ro,ro,ro",  //24
      colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",  //24
      widths : "80,100,100,100,200,100,200,80,80,100,80,80,80,120,120,100,100,200,100,100,100,100,100,200",  //24
      // hiddenColumns : [0],
      paging: true,
      pageSize:20,
      showPagingAreaOnInit: true,
      buttons : {
        hright : {
          btnExcel : { label : "자료생성" }
        }
      },
      onRowDblClicked : function(rowId, celInd) {
        this.callEvent('onButtonClick', ['btnDtl']);
      },
      onButtonClick : function(btnId, selectedData) {

        switch (btnId) {
          case "btnExcel" :

            if(PAGE.GRID1.getRowsNum() == 0){
              mvno.ui.alert("데이터가 존재하지 않습니다");
              return;
            }
            mvno.cmn.downloadCallback(function(result) {
                PAGE.FORM1.setItemValue("DWNLD_RSN",result);
                mvno.cmn.ajax(ROOT + "/stats/statsMgmt/selectCombStatusListExcel.json?menuId=MSP1000112", PAGE.FORM1.getFormData(true), function(result){
                    mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
                });
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

      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0037"}, PAGE.FORM1, "subStatus");                  //계약상태
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0080"}, PAGE.FORM1, "combSvcContSttusCd");         //결합상태
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0081"}, PAGE.FORM1, "searchGbn");                  //검색구분
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0085",orderBy:"etc1"}, PAGE.FORM1, "apiTarget");   //연동결과

      PAGE.FORM1.disableItem("searchName");
    }
  };


</script>