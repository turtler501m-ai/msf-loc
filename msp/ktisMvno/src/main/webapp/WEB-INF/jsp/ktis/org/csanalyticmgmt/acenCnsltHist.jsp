<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    /**
     * @Class Name : acenCnsltHist.jsp
     * @Description : A'CEN 상담이력(SR)
     * @
     * @Create Date : 2025. 09. 02.
     */
%>

<!-- 화면 배치 -->
<div id="FORM1" class="section-search"></div>
<div id="GRID1"></div>
<div id="FORM2" class="section-search"></div>
<div id="GRID2"></div>

<!-- Script -->
<script type="text/javascript">

    var cnsltConSrExcelLimit = ${cnsltConSrExcelLimit}; // 상담내용포함 자료생성 기간제약
    var cnsltSrExcelLimit = ${cnsltSrExcelLimit}; // 자료생성 기간제약

    var DHX = {
        //------------------------------------------------------------
        FORM1 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left",  blockOffset:0},
                {type: "block", list: [
                        {type:"calendar", name:"callStrtDt", label:"통화연도", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", labelWidth: 60, required: true, width:100, offsetLeft:10, readonly: true},
                        {type:"newcolumn"},
                        {type:"calendar", name:"callEndDt", label:"~", labelWidth:20, labelAlign:"center", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", width:100, readonly: true},
                        {type: 'newcolumn'},
                        {type:"select", name:"searchCd", label:"검색구분", width:100, offsetLeft:20, labelWidth: 50},
                        {type:"newcolumn"},
                        {type:"input", name:"searchVal", width:140},
                        {type:"newcolumn"},
                        {type:"select", name:"searchTalkType", label:"통화유형", width:120, offsetLeft:20, labelWidth: 50}
                ]},
                {type: "block", list: [
                        {type:"input", name:"searchCnsltId", width:100, label:"상담사ID", labelWidth: 60, offsetLeft:10},
                        {type:"newcolumn"},
                        {type:"input", name:"searchSrFir", width:105, label:"SR(대)", offsetLeft:20},
                        {type:"newcolumn"},
                        {type:"input", name:"searchSrSec", width:105, label:"SR(중)", offsetLeft:15},
                        {type:"newcolumn"},
                        {type:"input", name:"searchSrThi", width:105, label:"SR(소)", offsetLeft:15},
                        {type:"newcolumn"},
                        {type:"input", name:"searchSrDtl", width:105, label:"상세", offsetLeft:15},
                        {type:"newcolumn"},
                        {type:"hidden", name: "DWNLD_RSN", value:""}, // 엑셀다운로드 사유
                    ]},
                 {type: "newcolumn"},
                 {type: "button", value: "조회", name: "btnSearch" , className:"btn-search2"}
            ],
            onValidateMore : function(data) {

                if (data.callStrtDt > data.callEndDt) {
                    this.pushError("callStrtDt", "통화연도", "시작일자가 종료일자보다 클 수 없습니다.");
                }

                if(!mvno.cmn.isEmpty(data.searchCd) && mvno.cmn.isEmpty(data.searchVal)) {
                    this.pushError("searchCd", "검색구분", "검색어를 입력하십시오.");
                }

                let monthDiff = getMonthsBetween(data.callStrtDt, data.callEndDt);
                if (monthDiff > 6) {
                    this.pushError("callEndDt", "통화연도", "통화연도는 최대 6개월까지 조회 가능합니다.");
                }
            },
            onChange : function(name, value){
                var form = this;
                switch (name){
                    case "searchCd" :
                        form.setItemValue("searchVal", "");
                        if(value == ""){
                            form.disableItem("searchVal");
                        }else{
                            form.enableItem("searchVal");
                        }

                        break;
                }
            },
            onButtonClick : function(btnId) {

                var form = this;

                switch (btnId) {

                    case "btnSearch":
                        PAGE.GRID1.list(ROOT + "/org/csAnalyticMgmt/getAcenCnsltSrList.json", form, {
                            callback: function () {
                                form.resetValidateCss();
                            }
                        });
                        break;
                }
            }
        },
        //-------------------------------------------------------------------------------------
        GRID1 : {
            css : {
                height : "200px"
            },
            title : "SR목록",
            header : "통화일자,통화유형,계약번호,고객명,상담유형(대),상담유형(중),상담유형(소),상담유형(상세),개통대리점,현재상태,개통일,업무구분,구매유형,상담사", //14
            columnIds : "talkDt,talkType,contractNum,customerNm,cnsltTypeFirNm,cnsltTypeSecNm,cnsltTypeThiNm,cnsltTypeDtlNm,openAgnt,subStatusNm,lstComActvDate,operType,reqBuyType,cnsltNm", //14
            colAlign : "center,center,center,center,center,center,center,center,center,center,center,center,center,center", //14
            colTypes : "ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro", //14
            colSorting : "str,str,str,str,str,str,str,str,str,str,str,str,str,str", //14
            widths : "120,100,120,120,120,120,120,150,200,100,120,120,120,120", //14
            paging: true,
            pageSize:10,
            showPagingAreaOnInit: true,
            buttons : {
                hright : {
                    btnDnCnsltConExcel : { label : "상담내용포함 자료생성" }
                    ,btnDnExcel : { label : "자료생성" }
                }
            },
            onButtonClick : function(btnId, selectedData) {
                var grid = this;

                switch (btnId) {

                    case "btnDnCnsltConExcel":

                        if(PAGE.GRID1.getRowsNum() == 0) {
                            mvno.ui.alert("데이터가 존재하지 않습니다.");
                            return;
                        }

                        var daysDiff = getDaysBetween(PAGE.FORM1.getItemValue("callStrtDt"), PAGE.FORM1.getItemValue("callEndDt"));

                        if(daysDiff > cnsltConSrExcelLimit){
                            mvno.ui.alert("최대 " + cnsltConSrExcelLimit +"일까지만 다운로드 가능합니다.");
                            return;
                        }

                        mvno.ui.prompt("다운로드 사유",  function(result) {
                            PAGE.FORM1.setItemValue("DWNLD_RSN",result);
                            mvno.cmn.ajax(ROOT + "/org/csAnalyticMgmt/getAcenCnsltConSrListExcelDown.json?menuId=MSP2002204", PAGE.FORM1.getFormData(true), function(result){
                                mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
                            });
                        }, { lines: 5,  maxLength : 80, minLength : 10 } );

                        break;

                    case "btnDnExcel":

                        if(PAGE.GRID1.getRowsNum() == 0) {
                            mvno.ui.alert("데이터가 존재하지 않습니다.");
                            return;
                        }

                        var daysDiff = getDaysBetween(PAGE.FORM1.getItemValue("callStrtDt"), PAGE.FORM1.getItemValue("callEndDt"));

                        if(daysDiff > cnsltSrExcelLimit){
                            mvno.ui.alert("최대 " + cnsltSrExcelLimit + "일까지만 다운로드 가능합니다.");
                            return;
                        }

                        mvno.ui.prompt("다운로드 사유",  function(result) {
                            PAGE.FORM1.setItemValue("DWNLD_RSN",result);
                            mvno.cmn.ajax(ROOT + "/org/csAnalyticMgmt/getAcenCnsltSrListExcelDown.json?menuId=MSP2002204", PAGE.FORM1.getFormData(true), function(result){
                                mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
                            });
                        }, { lines: 5,  maxLength : 80, minLength : 10 } );

                        break;
                }
            }
        },
        FORM2 : {
            items : [
                {type:"settings", position:"label-left", labelAlign:"left",  blockOffset:0},
                {type: "block", list: [
                        {type:"calendar", name:"strtDttm", label:"조회일", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", labelWidth: 50, required:true, width:100, offsetLeft:10, readonly: true},
                        {type:"newcolumn"},
                        {type:"calendar", name:"endDttm", label:"~", labelWidth:20, labelAlign:"center", dateFormat:"%Y-%m-%d", serverDateFormat:"%Y%m%d", calendarPosition:"bottom", width:100, readonly: true},
                        {type: 'newcolumn'},
                        {type:"calendar", name:"strtDt", label:"조회월", dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", labelWidth: 50, required:true, width:90, offsetLeft:70, readonly: true},
                        {type:"newcolumn"},
                        {type:"calendar", name:"endDt", label:"~", labelWidth:20, labelAlign:"center", dateFormat:"%Y-%m", serverDateFormat:"%Y%m", calendarPosition:"bottom", width:90, readonly: true},
                    ]},
                {type: "block", list: [
                        {type:"input", name:"searchSrFir", width:105, label:"SR(대)", labelWidth: 50, offsetLeft:10},
                        {type:"newcolumn"},
                        {type:"input", name:"searchSrSec", width:105, label:"SR(중)", labelWidth: 50, offsetLeft:15},
                        {type:"newcolumn"},
                        {type:"input", name:"searchSrThi", width:105, label:"SR(소)", labelWidth: 50, offsetLeft:15},
                        {type:"newcolumn"},
                        {type:"input", name:"searchSrDtl", width:105, label:"상세", labelWidth: 50, offsetLeft:15},
                        {type:"newcolumn"},
                        {type : 'hidden', name: "DWNLD_RSN", value:""}, //엑셀다운로드 사유
                    ]},
                {type: "newcolumn"},
                {type: "button", value: "조회", name: "btnSearch" , className:"btn-search2"}
            ],
            onValidateMore : function(data) {
                if (data.strtDttm > data.endDttm) {
                    this.pushError("strtDttm", "조회일", "시작일자가 종료일자보다 클 수 없습니다.");
                }
                if (data.strtDt > data.endDt) {
                    this.pushError("strtDt", "조회월", "시작월이 종료월보다 클 수 없습니다.");
                }
            },
            onButtonClick : function(btnId) {

                var form = this;

                switch (btnId) {

                    case "btnSearch":
                        PAGE.GRID2.list(ROOT + "/org/csAnalyticMgmt/getAcenCnsltSrStatsList.json", form, {
                            callback: function () {
                                form.resetValidateCss();
                            }
                        });
                        break;
                }
            }
        },
        GRID2 : {
            css: {
                height: "200px"
            },
            title: "SR 월 통계",
            header: "연도-월,상담유형(대),상담유형(중),상담유형(소),상담유형(상세),상담유형 건수", //6
            columnIds: "talkYm,cnsltTypeFirNm,cnsltTypeSecNm,cnsltTypeThiNm,cnsltTypeDtlNm,totalCnt", //6
            colAlign: "center,center,center,center,center,center", //6
            colTypes: "ro,ro,ro,ro,ro,ro", //6
            colSorting: "str,str,str,str,str,str", //6
            widths: "100,160,160,160,230,150", //6
            paging: true,
            pageSize:10,
            showPagingAreaOnInit: true,
            buttons: {
                hright: {
                    btnSrStatsDnExcel: {label: "자료생성"}
                }
            }
            , onButtonClick: function (btnId) {

                var form = this; // 혼란방지용으로 미리 설정 (권고)

                switch (btnId) {

                    case "btnSrStatsDnExcel":

                        if(PAGE.GRID2.getRowsNum() == 0) {
                            mvno.ui.alert("데이터가 존재하지 않습니다.");
                            return;
                        }

                        mvno.ui.prompt("다운로드 사유",  function(result) {
                            PAGE.FORM2.setItemValue("DWNLD_RSN",result);
                            mvno.cmn.ajax(ROOT + "/org/csAnalyticMgmt/getAcenCnsltSrStatsListExcelDown.json?menuId=MSP2002204", PAGE.FORM2.getFormData(true), function(result){
                                mvno.ui.alert("검색조건을 이용하여 엑셀자료를 생성합니다.");
                            });
                        }, { lines: 5,  maxLength : 80, minLength : 10 } );

                        break;
                }
            }
        }
    }

    var PAGE = {
        title : "${breadCrumb.title}",
        breadcrumb : " ${breadCrumb.breadCrumb}",
        buttonAuth: ${buttonAuth.jsonString},
        onOpen : function() {
            mvno.ui.createForm("FORM1");
            mvno.ui.createForm("FORM2");
            mvno.ui.createGrid("GRID1");
            mvno.ui.createGrid("GRID2");

            var dt = new Date();
            PAGE.FORM1.setItemValue("callEndDt", dt.format("yyyymmdd"));
            PAGE.FORM1.setItemValue("callStrtDt", new Date(dt.setDate(dt.getDate() - 1)).format("yyyymmdd"));

            const today = new Date();
            const firstDayOfMonth = new Date(today.getFullYear(), today.getMonth(), 1);
            PAGE.FORM2.setItemValue("endDttm", today.format("yyyymmdd"));
            PAGE.FORM2.setItemValue("strtDttm", firstDayOfMonth.format("yyyymmdd"));
            PAGE.FORM2.setItemValue("endDt", today.format("yyyymm"));
            PAGE.FORM2.setItemValue("strtDt", today.format("yyyymm"));

            PAGE.FORM1.disableItem("searchVal");

            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ACEN0014",orderBy:"etc1"}, PAGE.FORM1, "searchCd");	// 검색구분
            mvno.cmn.ajax4lov( ROOT + "/cmn/cmnCdMgmtService/getCommCombo.json", {grpId:"ACEN0010",orderBy:"etc1"}, PAGE.FORM1, "searchTalkType");	// 통화유형
        }
    };

    function getMonthsBetween(startDt, endDt){ //yyyymmdd
        var startDate = new Date(startDt.substring(0,4)+'-'+startDt.substring(4,6)+'-'+startDt.substring(6,8));
        var endDate = new Date(endDt.substring(0,4)+'-'+endDt.substring(4,6)+'-'+endDt.substring(6,8));
        const yearDiff = Math.abs(startDate.getFullYear() - endDate.getFullYear());
        const diffInMonths = yearDiff * 12 + Math.abs(endDate.getMonth() - startDate.getMonth());

        return diffInMonths;
    }

    function getDaysBetween(startDt, endDt){
        const start = new Date(startDt.getFullYear(), startDt.getMonth(), startDt.getDate());
        const end = new Date(endDt.getFullYear(), endDt.getMonth(), endDt.getDate());
        const diffInMilliseconds = Math.abs(start - end);
        const diffInDays = diffInMilliseconds / (1000 * 60 * 60 * 24);

        return diffInDays;
    }
</script>
