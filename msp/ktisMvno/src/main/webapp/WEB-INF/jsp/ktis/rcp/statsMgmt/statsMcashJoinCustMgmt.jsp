<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : statsMcashJoinCustMgmt.jsp
   * @Description : M쇼핑할인 가입신청고객 관리 현황
   * @
   * @ 수정일	    수정자 수정내용
   * @ ---------- ------ -----------------------------
   * @ 2024.08.06       최초생성
   * @Create Date : 2024.08.06
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
            {type:"calendar", width:100, label:"가입신청일자", name:"srchStrtDt", value:"${srchStrtDt}", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:80, offsetLeft:10, readonly:true},
            {type:"newcolumn"},
            {type:"calendar", label:"~", name:"srchEndDt", value:"${srchEndDt}", labelAlign:"center", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", labelWidth:10, offsetLeft:5, width:100, readonly:true},
            {type:"newcolumn"},
            {type:"select", name:"subStatus", label:"회원상태", width:100, offsetLeft:20},
            {type:"newcolumn"},
            {type:"select", name:"searchGbn", label:"검색구분", width:120, offsetLeft:20},
            {type:"newcolumn"},
            {type:"input", width:100, name:"searchName"}
          ]},
        {type:"button", value:"조회", name:"btnSearch", className:"btn-search1"},
      ],
      onValidateMore : function(data) {
        if (data.srchStrtDt > data.srchEndDt) {
          this.pushError("srchStrtDt", "조회기간", "시작일자가 종료일자보다 클 수 없습니다.");
        }
        if(data.srchStrtDt != "" && data.srchEndDt == ""){
          this.pushError("srchStrtDt", "조회기간", "조회기간을 입력하세요.");
        }
        if(data.srchEndDt != "" && data.srchStrtDt == ""){
          this.pushError("srchEndDt", "조회기간", "조회기간을 입력하세요.");
        }

        if (data.searchGbn != "" && data.searchName.trim() == "") {
          this.pushError("searchName", "검색구분", "검색어를 입력하세요");
          PAGE.FORM1.setItemValue("searchName", ""); // 검색어 초기화
        }
      },
      onButtonClick : function(btnId) {
        var form = this;

        switch (btnId) {
          case "btnSearch":
            PAGE.GRID1.list(ROOT + "/stats/statsMgmt/getMcashJoinCustMgmtList.do", form);
            PAGE.GRID2.clearAll();
            break;
        }
      },
      onChange : function(name, value) {
        // 검색구분
        if (name == "searchGbn") {
          PAGE.FORM1.setItemValue("searchName", "");

          if (value == null || value == "") {
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

            // 가입신청일자 Disable
            PAGE.FORM1.disableItem("srchStrtDt");
            PAGE.FORM1.disableItem("srchEndDt");

            PAGE.FORM1.enableItem("searchName");
          }
        }
      }
    },
    // ----------------------------------------
    //M캐시 회원정보 리스트 조회
    GRID1 : {
      css : {
        height : "250px"
      },
      title : "조회결과",
      header : "가입신청일자,포털ID,포털ID,고객명,생년월일,계약번호,서비스계약번호,휴대폰번호,회원상태,M쇼핑할인 가입 최초 요금제,현재요금제,성별,업무구분,상태,대리점,유심접점,개통일자,해지사유,해지일자,이동전 통신사,해지후이동사업자정보",  // 21
      columnIds : "strtDttm,maskUserId,userId,custNm,userSsn,contractNum,svcCntrNo,mobileNo,status,fstRateNm,lstRateNm,gender,operTypeNm,subStatusNm,openAgntNm,usimOrgNm,openDt,canRsn,tmntDt,moveCompanyNm,cmpnNm",  // 21
      colAlign : "center,center,center,center,center,center,center,center,center,left,left,center,center,center,left,left,center,center,center,center,center",  // 21
      colTypes : "roXd,ro,ro,ro,ro,ro,ro,roXp,ro,ro,ro,ro,ro,ro,ro,ro,roXd,ro,roXd,ro,ro",  // 21
      colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str,str",  // 21
      widths : "110,100,10,90,90,100,100,100,80,260,260,50,100,80,150,150,100,150,100,120,150",  // 21
      hiddenColumns : [2],
      paging: true,
      pageSize:20,
      showPagingAreaOnInit: true,
      buttons : {
        hright : {
          btnExcel : { label : "엑셀다운로드" }
        }
      },
      onRowSelect:function(rId, cId){
        PAGE.GRID2.clearAll();
        PAGE.GRID2.list(ROOT + "/stats/statsMgmt/getMcashJoinCustMgmtHist.do", this.getSelectedRowData());
      },
      onButtonClick : function(btnId, selectedData) {
        var grid = this;
        switch (btnId) {
          case "btnExcel" :

            if(PAGE.GRID1.getRowsNum() == 0){
              mvno.ui.alert("데이터가 존재하지 않습니다");
              return;
            }

            var searchData =  PAGE.FORM1.getFormData(true);

            mvno.cmn.download(ROOT + "/stats/statsMgmt/getMcashJoinCustMgmtListExcel.json?menuId=MSP1010047", true, {postData:searchData});

            break;
        }
      }
    },
    // ----------------------------------------
    //프로모션 가입이력 상세 리스트
    GRID2 : {
      css : {
        height : "250px"
      },
      title : "상세이력",
      header : "변경일시,변경작업,변경작업 상세,포털ID,서비스계약번호,휴대폰번호",  // 6
      columnIds : "rvisnDttm,evntType,evntTypeDtl,maskUserId,svcCntrNo,mobileNo",  // 6
      colAlign : "center,center,center,center,center,center",  // 6
      colTypes : "roXdt,ro,ro,ro,ro,roXp",  // 6
      colSorting : "str,str,str,str,str,str", // 6
      widths : "180,140,140,150,170,150", // 6
      paging: true,
      pageSize:20,
      showPagingAreaOnInit: true
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

      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"McashUserStatus"}, PAGE.FORM1, "subStatus"); // 회원상태
      mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9044", orderBy:"etc1"}, PAGE.FORM1, "searchGbn"); // 검색구분
      PAGE.FORM1.disableItem("searchName");
    }
  };

</script>