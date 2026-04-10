<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1" class="section-full"></div>

<!-- 탭바 선언 -->
<div id="TABBAR1"></div>

<!-- A'Cen 실패이력 -->
<div id="TABBAR1_a1" style="display:none">
  <div id="GRID2"></div>
</div>

<!-- A'Cen 연동이력 -->
<div id="TABBAR1_a2" style="display:none">
  <div id="GRID3"></div>
</div>

<!-- A'Cen 재실행이력 -->
<div id="TABBAR1_a3" style="display:none">
  <div id="GRID4"></div>
</div>

<!-- Script -->
<script type="text/javascript">
    var maskingYn = "${maskingYn}"; // 마스킹권한

    var DHX = {
      FORM1 : {
        items : [
            {type:"settings", position:"label-left", labelAlign:"left", labelWidth:60, blockOffset:0}
          , {type:"block", list:[
                {type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "srchStrtDt", label: "신청일자", value: "${srchStrtDt}", calendarPosition: 'bottom', readonly: true, width: 100, offsetLeft: 10}
              , {type: "newcolumn"}
              , {type: "calendar", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", name: "srchEndDt", label: "~", value: "${srchEndDt}", calendarPosition: "bottom", readonly: true, labelAlign: "center", labelWidth: 10, offsetLeft: 5, width: 100}
              , {type: "newcolumn"}
              , {type: "select", width: 100, offsetLeft: 65, label: "업무구분", name: "pOperType"}
              , {type: "newcolumn"}
              , {type: "select", width: 100, offsetLeft: 30, label: "구매유형", name: "pReqBuyType"}
            ]}
          , {type:"block", list:[
                {type: "input", label: "대리점", name: "pAgentCode", width: 100, offsetLeft: 10}
              , {type: "newcolumn"}
              , {type: "button", value: "찾기", name: "btnOrgFind"}
              , {type: "newcolumn"}
              , {type: "input", name: "pAgentName", width: 100, readonly: true}
              , {type: "newcolumn"}
              , {type: "select", width: 100, offsetLeft: 30, label: "진행상태", name: "pRequestStateCode"}
              , {type: "newcolumn"}
              , {type: "select", width: 100, offsetLeft: 30, label: "신청취소", name: "pPstate"}
            ]}
          , {type:"block", list:[
                {type: "select", width: 150, offsetLeft: 10, label: "시나리오", name: "pAcenEvntGrp"}
              , {type: "newcolumn"}
              , {type: "select", width: 100, label: "성공여부", offsetLeft: 135, labelWidth: 60, labelAlign: "left", name: "pSuccYn", options:[{value: "", text: "- 전체 -"}, {value: "Y", text: "성공"}, {value: "N", text: "실패"}]}
              , {type: "newcolumn"}
              , {type: "select", width: 100, offsetLeft: 30, label: "실패사유", name: "pAcenErrCd"}
            ]}
          , {type:"block", list:[
                {type: "select", width: 100, label: "검색구분",name: "pSearchGbn", offsetLeft: 10}
              , {type: "newcolumn"}
              , {type: "input", width: 120, name: "pSearchName", disabled: true}
              , {type: "newcolumn"}
              , {type: "select", width: 100, offsetLeft: 60, label: "모집경로", name: "pOnOffType"}
              , {type: "newcolumn"}
              , {type: "checkbox", label: "eSIM여부", width: 40, name: "pEsimYn", labelWidth: 60, offsetLeft: 30, value: "Y"}
              , {type : "newcolumn"}
              , {type : "checkbox", label : "셀프개통 제외", width : 40, name : "pSelfYn", labelWidth : 80, offsetLeft : 25, value : "Y"}
            ]}
          , {type: "hidden", name: "DWNLD_RSN", value: ""}        // 엑셀다운로드 사유
          , {type: "hidden", name: "btnCount1", value: "0"}       // 조회 버튼 다중클릭 방지
          , {type: "hidden", name: "btnExcelCount1", value: "0"}, // 다운로드 버튼 다중클릭 방지
          , {type: "button", name: "btnSearch", value: "조회", className: "btn-search4"}
        ]
        ,onXLE : function() {}
        ,onButtonClick : function(btnId) {
          var form = this;

          switch (btnId) {
            case "btnSearch":  // 조회

              var btnCount2 = parseInt(PAGE.FORM1.getItemValue("btnCount1", "")) + 1;
              PAGE.FORM1.setItemValue("btnCount1", btnCount2);

              if (PAGE.FORM1.getItemValue("btnCount1", "") != "1") {
                return; //버튼 최초 클릭일때만 조회가능하도록. 로딩중 여러번 클릭 막기
              }

              PAGE.GRID1.list(ROOT + "/org/csAnalyticMgmt/getAcenRcpMgmtList.json", form, {
                callback : function () {
                  form.resetValidateCss();
                  PAGE.FORM1.setItemValue("btnCount1", 0);
                  PAGE.FORM1.setItemValue("btnExcelCount1", 0);

                  // 탭 초기화
                  PAGE.TABBAR1.tabs("a1").setActive();
                  var Tid = PAGE.TABBAR1.getActiveTab();
                  PAGE.TABBAR1.callEvent("onSelect", [Tid, null, true]);
                }
              });

              break;

            case "btnOrgFind":
              mvno.ui.codefinder("ORG", function(result) {
                form.setItemValue("pAgentCode", result.orgnId);
                form.setItemValue("pAgentName", result.orgnNm);
              });
              break;
          }
        }
        ,onChange : function(name, value) {
          // 검색구분
          if(name == "pSearchGbn") {

            PAGE.FORM1.setItemValue("pSearchName", "");  // 검색어 초기화

            if(mvno.cmn.isEmpty(value) || value == "8") {
              // 검색구분 [전체] 또는 [요금제] 선택 시 : 신청일자 선택 가능
              var srchEndDt = new Date().format("yyyymmdd");
              var time = new Date().getTime();
              time = time - 1000 * 3600 * 24 * 7;
              var srchStrtDt = new Date(time);

              // 신청일자 Enable
              PAGE.FORM1.enableItem("srchStrtDt");
              PAGE.FORM1.enableItem("srchEndDt");

              PAGE.FORM1.setItemValue("srchStrtDt", srchStrtDt);
              PAGE.FORM1.setItemValue("srchEndDt", srchEndDt);

              if(value == "8") PAGE.FORM1.enableItem("pSearchName");
              else PAGE.FORM1.disableItem("pSearchName");

            } else{
              // 신청일자 선택 불가
              PAGE.FORM1.disableItem("srchStrtDt");
              PAGE.FORM1.disableItem("srchEndDt");

              PAGE.FORM1.setItemValue("srchStrtDt", "");
              PAGE.FORM1.setItemValue("srchEndDt", "");

              PAGE.FORM1.enableItem("pSearchName");
            }
          }
        }
        ,onValidateMore : function(data) {

          if(data.srchStrtDt > data.srchEndDt) {
            this.pushError("srchEndDt", "신청일자", "종료일자가 시작일자보다 클 수 없습니다.");
          }

          if(data.pSearchGbn == "") {
            // 검색어 미존재시 조회기간 필수
            if(mvno.cmn.isEmpty(data.srchStrtDt)){
              this.pushError("srchStrtDt", "신청일자", "조회 시작일자를 선택하세요");
            }

            if(mvno.cmn.isEmpty(data.srchEndDt)){
              this.pushError("srchEndDt", "신청일자", "조회 종료일자를 선택하세요");
            }
          }
          else if(data.pSearchName.trim() == "") {
            this.pushError("pSearchName", "검색어", "검색어를 입력하세요");
          }

          PAGE.FORM1.setItemValue("btnCount1", 0);      // 초기화
          PAGE.FORM1.setItemValue("btnExcelCount1", 0); // 초기화
        }
      } // end of FORM1 -----------------------------------
      , GRID1 : {  // A'Cen 신청 리스트
          css : {
              height : "160px"
            }
          , title : "조회결과"
          , header : "신청일자,예약번호,고객명,요금제,업무구분,진행상태,신청서상태,배송방법,시나리오,성공여부,실패사유,처리메모,수정자,수정일시,주문번호" // 15
          , columnIds : "reqInDay,resNo,cstmrNm,socNm,operNm,requestStateNm,pstateNm,dlvryTypeNm,acenEvntGrpNm,acenStatus,acenFailRmk,requestMemo,rvisnNm,rvisnDttm,requestKey"  // 15
          , colAlign : "center,center,left,left,center,center,center,center,center,center,center,center,left,center,center" // 15
          , colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro" // 15
          , colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str,str" // 15
          , widths : "130,100,120,250,70,100,100,100,150,100,150,200,120,130,0" // 15
          , hiddenColumns : [14]
          , paging : true
          , pageSize : 10
          , buttons : {
              hright : {
                btnFailDetailExcel : {label : "실패상세 자료생성"}
               ,btnExcel : {label : "자료생성"}
              }
              ,right : {
                btnReProc : {label : "A'Cen 재실행"}
               ,btnDtl : {label : "신청조회"}
              }
            }
          , checkSelectedButtons : ["btnReProc", "btnDtl"]
          , onRowDblClicked : function(rowId, celInd) {
              this.callEvent('onButtonClick', ['btnDtl']);
            }
          , onRowSelect : function (rowId, celInd) {
              var Tid = PAGE.TABBAR1.getActiveTab();
              PAGE.TABBAR1.callEvent("onSelect", [Tid, null, true]);
            }
          , onButtonClick : function(btnId) {
              var grid = this;

              switch (btnId) {
                // 자료생성 & 실패상세 다운로드
                case "btnExcel" : case "btnFailDetailExcel" :
                  if(PAGE.GRID1.getRowsNum() == 0){
                      mvno.ui.alert("데이터가 존재하지 않습니다");
                      return;
                  }

                  var btnExcelCount2 = parseInt(PAGE.FORM1.getItemValue("btnExcelCount1", "")) + 1;
                  PAGE.FORM1.setItemValue("btnExcelCount1", btnExcelCount2);
                  if (PAGE.FORM1.getItemValue("btnExcelCount1", "") != "1") {
                      return; //버튼 최초 클릭일때만 조회가능하도록. 로딩중 여러번 클릭 막기
                  }

                  var downloadUrl = "/org/csAnalyticMgmt/acenRcpMgmtListExcelDownload.json?menuId=MSP2002201";

                  if(btnId == "btnFailDetailExcel"){
                    downloadUrl = "/org/csAnalyticMgmt/acenRcpMgmtFailListExcelDownload.json?menuId=MSP2002201";
                  }

                  mvno.cmn.downloadCallback(function(result) {
                    PAGE.FORM1.setItemValue("DWNLD_RSN", result);

                    var searchData =  PAGE.FORM1.getFormData(true);
                    mvno.cmn.ajax(ROOT + downloadUrl, searchData, function(result){
                        mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
                    });
                  });

                  PAGE.FORM1.setItemValue("btnCount1", 0);      // 초기화
                  PAGE.FORM1.setItemValue("btnExcelCount1", 0); // 초기화

                  break;

                // 신청조회
                case "btnDtl" :

                  var sdata = PAGE.GRID1.getSelectedRowData();
                  if(sdata == null){
                      mvno.ui.alert("선택된 데이터가 없습니다.");
                      return;
                  }

                  var url = "/rcp/rcpMgmt/getRcpNewMgmt.do";
                  var menuId = "MSP1000014";      //신청등록
                  var trgtMenuId = "MSP2002201";
                  var tabTitle = "신청등록";

                    if(sdata.osstChk > 0 && (sdata.cstmrType == "NA" || sdata.cstmrType == "FN")){
                        if(confirm("직접개통을 진행하시겠습니까?")){
                            url = "/rcp/rcpMgmt/getRcpSimpMgmtView.do";
                            menuId = "MSP1000016";
                            tabTitle = "신청등록(직접개통)";
                        }
                    }

                  var param = "?menuId=" + menuId + "&requestKey=" + sdata.requestKey + "&trgtMenuId=" + trgtMenuId;

                  var myTabbar = window.parent.mainTabbar;

                  if(myTabbar.tabs(url)) {
                    myTabbar.tabs(url).setActive();
                  }else{
                    myTabbar.addTab(url, tabTitle, null, null, true);
                  }

                  myTabbar.tabs(url).attachURL(url + param);

                  if(maskingYn == "Y" || maskingYn == "1"){
                      mvno.cmn.ajax(ROOT + "/org/userinfomgmt/insertSearchLog.json"
                                   ,{"requestKey": sdata.requestKey, "trgtMenuId": "MSP2002201"}
                                   ,function(result){});
                  }

                  break;

                  // 재실행
                  case "btnReProc" :

                  var sdata = PAGE.GRID1.getSelectedRowData();
                  if(sdata == null){
                      mvno.ui.alert("선택된 데이터가 없습니다.");
                      return;
                  }

                  // 재실행 가능 여부 확인
                  mvno.cmn.ajax(ROOT + "/org/csAnalyticMgmt/acenRtyPreChk.json",{"requestKey": sdata.requestKey},function(result){

                      var preChkMap = result.result.preChkMap;
                      var rtyEvntTypeNm = preChkMap.rtyEvntTypeNm;
                      var pstateNm = preChkMap.pstatNm;
                      var requestStateCodeNm = preChkMap.requestStateCodeNm;
                      var mnpPreChkYn = preChkMap.mnpPreChkYn;
                      var isSameStatus = preChkMap.isSameStatus;

                      var rtyAlertMsg = "["+rtyEvntTypeNm+"]<br>부터 재실행하시겠습니까?";

                      if(isSameStatus != "Y"){
                          rtyAlertMsg += "<br><br>신청서 상태는 "+pstateNm+"/"+requestStateCodeNm+"(으)로 변경됩니다.";
                      }

                      if(mnpPreChkYn == "Y"){
                          rtyAlertMsg += "<br><br>(사전인증 처리여부를 확인해주세요)";
                      }

                      mvno.ui.confirm(rtyAlertMsg, function(){
                          mvno.cmn.ajax(ROOT + "/org/csAnalyticMgmt/updateAcenWithRty.json",{"requestKey": sdata.requestKey},function(result){
                              mvno.ui.alert("재실행되었습니다.");

                              PAGE.GRID1.refresh();

                              var Tid = PAGE.TABBAR1.getActiveTab();
                              PAGE.TABBAR1.callEvent("onSelect", [Tid, null, true]);
                          });
                      })
                  });
              }
            }
      }  // end of GRID1 -------------------------------
      , TABBAR1 : {
          title : "상세내역"
          , css : {height : "300px"}
          , tabs : [
              {id : "a1", text: "실패이력"}
             ,{id : "a2", text: "연동이력"}
             ,{id : "a3", text: "재실행이력"}
            ]
          , sideArrow : false
          , onSelect : function (id, lastId, isFirst) {

              var rowData = PAGE.GRID1.getSelectedRowData();

              switch (id) {
                // 실패이력
                case "a1":
                  mvno.ui.createGrid("GRID2");
                  PAGE.GRID2.clearAll();

                  if(rowData != null){
                      PAGE.GRID2.list(ROOT + "/org/csAnalyticMgmt/getAcenFailHist.json", {"requestKey" : rowData.requestKey});
                  }

                  break;

                // 연동이력
                case "a2":
                  mvno.ui.createGrid("GRID3");
                  PAGE.GRID3.clearAll();

                  if(rowData != null){
                      PAGE.GRID3.list(ROOT + "/org/csAnalyticMgmt/getAcenHist.json", {"requestKey" : rowData.requestKey});
                  }

                  break;

                // 재실행이력
                case "a3":
                  mvno.ui.createGrid("GRID4");
                  PAGE.GRID4.clearAll();

                  if(rowData != null){
                    PAGE.GRID4.list(ROOT + "/org/csAnalyticMgmt/getAcenRetryHist.json", {"requestKey" : rowData.requestKey});
                  }

                  break;
              }
            }
        } // end of TABBAR1 ---------------------------------
        , GRID2: {  // A'Cen 실패이력
            css : {height : "200px", width : "951px"}
            , header : "고객명,예약번호,실패일시,실패 시나리오(상세),요청여부,실패사유"  // 6개
            , columnIds : "cstmrNm,resNo,regstDttm,evntTypeNm,reqStatusNm,acenFailRmk"  // 6개
            , colAlign : "center,center,center,center,center,left"  // 6개
            , colTypes : "ro,ro,ro,ro,ro,ro"   // 6개
            , widths : "120,100,130,200,80,320"  // 6개
            , colSorting : "str,str,str,str,str,str"  // 6개
            , paging : true
            , pageSize: 10
        }
        , GRID3: {  // A'Cen 연동이력
             css : {height : "200px", width : "951px"}
           , header : "예약번호,연동일시,시나리오(상세),연동유형,연동결과,연동실패상세"  // 6개
           , columnIds : "resNo,rsltDttm,evntTypeNm,chnlCdNm,vRsltMsg,vErrMsg" // 6개
           , colAlign : "center,center,center,center,center,left"   // 6개
           , colTypes : "ro,ro,ro,ro,ro,ro"   // 6개
           , widths : "100,130,200,80,80,350"   // 6개
           , colSorting : "str,str,str,str,str,str"  // 6개
           , paging : true
           , pageSize: 10
        }
        , GRID4: {  // A'Cen 재실행이력
              css : {height : "200px", width : "951px"}
            , header : "예약번호,재실행일시,재실행 시나리오(상세),요청자"  // 4개
            , columnIds : "resNo,regstDttm,evntTypeNm,regstNm"  // 4개
            , colAlign : "center,center,center,center"  // 4개
            , colTypes : "ro,ro,ro,ro"   // 4개
            , widths : "100,130,200,150"    // 4개
            , colSorting : "str,str,str,str"  // 4개
            , paging : true
            , pageSize: 10
        }
    };  // end of DHX -------------------------------------------------------------

    var PAGE = {
         title : "${breadCrumb.title}"
        ,breadcrumb : "${breadCrumb.breadCrumb}"
        ,buttonAuth: ${buttonAuth.jsonString}
        ,onOpen : function(){
            mvno.ui.createForm("FORM1");
            mvno.ui.createGrid("GRID1");
            mvno.ui.createTabbar("TABBAR1");

            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"CMN0049",orderBy:"etc1"}, PAGE.FORM1, "pOperType");	// 업무구분
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0006"}, PAGE.FORM1, "pRequestStateCode");			    // 진행상태
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0005"}, PAGE.FORM1, "pPstate");						        // 신청취소
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP2017"}, PAGE.FORM1, "pSearchGbn");					      // 검색구분
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP9004"}, PAGE.FORM1, "pReqBuyType");					      // 구매유형
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"RCP0007"}, PAGE.FORM1, "pOnOffType");					      // 모집경로
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"AcenEvntGrp"}, PAGE.FORM1, "pAcenEvntGrp");       // 시나리오
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getMcpCommCombo.json", {grpId:"AcenErrCd"}, PAGE.FORM1, "pAcenErrCd");           // 실패사유
        }
    }

</script>