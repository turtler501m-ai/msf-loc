<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  /**
   * @Class Name : docRcvList.jsp
   * @Description : 서류요청 접수현황 화면
   * @
   * @ 수정일	    수정자 수정내용
   * @ ---------- ------ -----------------------------
   * @ 2025.09.08      최초생성
   * @Create Date : 2025.09.08
   */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search" ></div>
<!-- 그리드영역 -->
<div id="GRID1"></div>
</div>

<!-- Script -->
<script type="text/javascript">
    var date = new Date();
    var endDate = date.format('yyyymmdd');
    var time = date.getTime();
    time = time - 1000 * 3600 * 24 * 6;
    var startDate = new Date(time);

    var DHX = {
        // 검색
        FORM1 : {
            items : [
                { type : "settings", position : "label-left", labelAlign : "left", labelWidth : 55, blockOffset : 0 }
                ,{ type : "block", list : [
                        {type: "calendar", name: "searchStartDate", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", calendarPosition: "bottom", validate: "NotEmpty", label: "요청일자", offsetLeft: 10, width: 90, readonly: true}
                        , {type: "newcolumn"}
                        , {type: "calendar", name: "searchEndDate", dateFormat: "%Y-%m-%d", serverDateFormat: "%Y%m%d", calendarPosition: "bottom", validate: "NotEmpty", label: "~", labelAlign: "center", labelWidth: 10, offsetLeft: 5, width: 90, readonly: true},
                        , {type: "newcolumn"}
                        , {type:"input", width:301, label:"업무명", name: "workTmplNm", offsetLeft:20}
                        , {type: "newcolumn"}
                        , {type:"select", width:100, label:"접수상태", name:"rcvYn", offsetLeft:20, options:[{value:"", text:"- 전체 -"}, {value:"Y", text:"접수"}, {value:"N", text:"미접수"}]}
                    ]}
                ,{ type: "block", list : [
                        {type:"select", width:90, label:"검색구분", name:"searchType", offsetLeft:10}
                        , {type: "newcolumn"}
                        , {type : "input", name : "searchVal", width:105, maxLength:20 }
                        , {type: "newcolumn"}
                        , {type:"select", width:150, label:"업무유형", name:"workId", offsetLeft:20}
                        , {type: "newcolumn"}
                        , {type:"select", width:70, label:"URL상태", name:"urlStatus", offsetLeft:20}
                        , {type: "newcolumn"}
                        , {type:"select", width:100, label:"처리상태", name:"procStatus", offsetLeft:20}
                    ]}
                ,{ type : "button", name: "btnSearch",value : "조회", className:"btn-search2" }
            ]
            , onValidateMore : function(data){
                if (mvno.cmn.isEmpty(data.searchType)) {
                    if (mvno.cmn.isEmpty(data.searchStartDate)) {
                        this.pushError("searchStartDate","요청시작일자","요청시작일자를 입력해주세요.");
                        return;
                    }
                    if (mvno.cmn.isEmpty(data.searchEndDate)) {
                        this.pushError("searchEndDate","요청종료일자","요청종료일자를 입력해주세요.");
                        return;
                    }
                    if (data.searchStartDate > data.searchEndDate){
                        this.pushError("searchEndDate","요청종료일자",["ICMN0004"]);
                    }
                }
            }
            , onChange:function(name, value) {
                var form = this;
                switch (name) {
                    case "searchType" :
                        form.setItemValue("searchVal", "");

                        if (mvno.cmn.isEmpty(value)) {
                            form.setItemValue("searchStartDate", startDate);
                            form.setItemValue("searchEndDate", endDate);

                            form.enableItem("searchStartDate");
                            form.enableItem("searchEndDate");
                            form.disableItem("searchVal");

                        } else {
                            form.setItemValue("searchStartDate", "");
                            form.setItemValue("searchEndDate", "");

                            form.disableItem("searchStartDate");
                            form.disableItem("searchEndDate");
                            form.enableItem("searchVal");
                        }
                        break;
                }
            }
            , onButtonClick : function(btnId) {

                var form = this;

                switch (btnId) {
                    case "btnSearch":
                        if (!mvno.cmn.isEmpty(form.getItemValue("searchType")) && mvno.cmn.isEmpty(form.getItemValue("searchVal"))) {
                            mvno.ui.alert("검색할 내용이 없습니다.");
                            return;
                        }

                        PAGE.GRID1.list(ROOT + "/org/workMgmt/getDocRcvList.json", form);
                        break;
                }
            }
        }//FORM1 End
        , GRID1 : {
            css : {
                height : "572px"
            }
            ,title : "서류요청 접수목록"
            ,header : "요청번호,업무명,업무유형,요청일자,URL 상태,고객명,문자수신번호,접수상태,접수일,처리상태,처리일,접수자,처리상태코드" // 13
            ,columnIds :"docRcvId,workTmplNm,workNm,requestDay,urlStatusNm,cstmrName,mobileNo,rcvYnNm,rcvDt,procStatusNm,procDt,cretNm,procStatus" // 13
            ,widths :"135,190,140,85,70,90,100,65,85,83,85,90,10" // 13
            ,colAlign :"center,left,center,center,center,center,center,center,center,center,center,center,center" // 13
            ,colTypes :"ro,ro,ro,ro,ro,ro,roXp,ro,ro,ro,ro,ro,ro" // 13
            ,colSorting :"str,str,str,str,str,str,str,str,str,str,str,str" // 13
            ,paging : true
            ,pageSize : 20
            ,showPagingAreaOnInit : true
            ,hiddenColumns : [12]
            ,buttons : {
                hright : {
                    btnExcel : { label : "엑셀다운로드" }
                }
            }
            ,onRowDblClicked : function(rowId) {
                var rowData = this.getRowData(rowId);

                var url = "/org/workMgmt/docRcvVerifyView.do";
                var param = "?docRcvId=" + rowData.docRcvId;

                mvno.ui.popupWindowTop(ROOT + url + param, "접수확인검수", 1000, 690);
            }
            ,onButtonClick : function(btnId) {
                switch(btnId) {
                    case "btnExcel":
                        if (PAGE.GRID1.getRowsNum() === 0) {
                            mvno.ui.alert("데이터가 존재하지 않습니다.");
                            return;
                        }

                        var searchData =  PAGE.FORM1.getFormData(true);
                        mvno.cmn.download(ROOT + "/org/workMgmt/getDocRcvListByExcel.json?menuId=MSP2002302", true, {postData:searchData});

                        break;
                }
            }
        }
    }
    var PAGE = {
        title : "${breadCrumb.title}",
        breadcrumb : " ${breadCrumb.breadCrumb}",
        buttonAuth: ${buttonAuth.jsonString},
        onOpen:function() {
            mvno.ui.createForm("FORM1");
            mvno.ui.createGrid("GRID1");

            PAGE.FORM1.setItemValue("searchStartDate", startDate);
            PAGE.FORM1.setItemValue("searchEndDate", endDate);
            mvno.cmn.ajax4lov( ROOT + "/org/workMgmt/getWorkIdList.json", {}, PAGE.FORM1, "workId");
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0081",orderBy:"etc1"}, PAGE.FORM1, "procStatus");
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0082",orderBy:"etc1"}, PAGE.FORM1, "urlStatus");
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ORG0083",orderBy:"etc1"}, PAGE.FORM1, "searchType");
        }
    };
</script>